/*
 * MosP - Mind Open Source Project    http://www.mosp.jp/
 * Copyright (C) MIND Co., Ltd.       http://www.e-mind.co.jp/
 * 
 * This program is free software: you can redistribute it and/or
 * modify it under the terms of the GNU Affero General Public License
 * as published by the Free Software Foundation, either version 3
 * of the License, or (at your option) any later version.
 * 
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU Affero General Public License for more details.
 * 
 * You should have received a copy of the GNU Affero General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */
package jp.mosp.framework.base;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;

import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.utils.LogUtility;

/**
 * DBとの接続を管理する。<br>
 */
public class DBConnBean {
	
	/**
	 * MosPアプリケーション設定キー(DB接続情報：データソース利用設定)。
	 */
	public static final String	APP_JDNI_DATA_SOURCE	= "JndiDataSource";
	
	/**
	 * MosPアプリケーション設定キー(DB接続情報：ドライバ)。
	 */
	public static final String	APP_DB_DRIVER			= "DbDriver";
	
	/**
	 * MosPアプリケーション設定キー(DB接続情報：URL)。
	 */
	public static final String	APP_DB_URL				= "DbUrl";
	
	/**
	 * MosPアプリケーション設定キー(DB接続情報：ユーザ)。
	 */
	public static final String	APP_DB_USER				= "DbUser";
	
	/**
	 * MosPアプリケーション設定キー(DB接続情報：パスワード)。
	 */
	public static final String	APP_DB_PASS				= "DbPass";
	
	/**
	 * コネクション。<br>
	 * {@link #createConnection(String, String, String, String)}等により設定される。
	 */
	private Connection			connection;
	
	
	/**
	 * DBコネクションを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @throws MospException DBコネクションの取得に失敗した場合
	 */
	public DBConnBean(MospParams mospParams) throws MospException {
		// コネクション初期化
		connection = null;
		// MosPユーザを取得
		MospUser user = mospParams.getUser();
		// SaaSの場合(MosPユーザからASPユーザIDが取得できた場合)
		if (user != null && user.getAspUserId() != null && user.getAspUserId().isEmpty() == false) {
			// MosPユーザからDB接続設定を取得してコネクションを生成
			createConnection(user.getDbDriver(), user.getDbUrl(), user.getDbUser(), user.getDbPass());
			// DB接続ログ出力
			LogUtility.dbConnect(mospParams, user.getAspUserId() + connection.toString());
			return;
		}
		// JNDI名(データソース)取得及び確認
		String jndiName = mospParams.getApplicationProperty(APP_JDNI_DATA_SOURCE);
		if (jndiName != null && jndiName.isEmpty() == false) {
			// データソース準備
			DataSource dataSource = null;
			try {
				// データソース取得
				dataSource = getDataSource(jndiName);
			} catch (MospException e) {
				// DB接続ログ(データソース取得失敗)出力
				LogUtility.dbConnect(mospParams,
						mospParams.getMessage(ExceptionConst.EX_FAIL_DATA_SOURCE, new String[]{ jndiName }));
			}
			// データソース取得に成功した場合
			if (dataSource != null) {
				// データソースからコネクションを生成
				createConnection(dataSource);
				// DB接続ログ出力
				LogUtility.dbConnect(mospParams, dataSource.toString() + connection.toString());
				return;
			}
		}
		// DB接続情報準備
		String rdbdriver = "";
		String rdbname = "";
		String userid = "";
		String password = "";
		// MosPユーザが取得できた場合
		if (user != null) {
			rdbdriver = user.getDbDriver() == null ? "" : user.getDbDriver();
			rdbname = user.getDbUrl() == null ? "" : user.getDbUrl();
			userid = user.getDbUser() == null ? "" : user.getDbUser();
			password = user.getDbPass() == null ? "" : user.getDbPass();
		}
		if (rdbdriver.isEmpty() || rdbname.isEmpty() || userid.isEmpty() || password.isEmpty()) {
			rdbdriver = mospParams.getApplicationProperty(APP_DB_DRIVER);
			rdbname = mospParams.getApplicationProperty(APP_DB_URL);
			userid = mospParams.getApplicationProperty(APP_DB_USER);
			password = mospParams.getApplicationProperty(APP_DB_PASS);
		}
		// DB接続設定を用いてコネクションを生成
		createConnection(rdbdriver, rdbname, userid, password);
		// DB接続ログ出力
		LogUtility.dbConnect(mospParams, connection.toString());
	}
	
	/**
	 * DBコネクションを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param rdbDriver  JDBCドライバ名
	 * @param rdbName    DBのURL
	 * @param userId     DB接続ユーザーID
	 * @param password   パスワード
	 * @throws MospException DBコネクションの取得に失敗した場合
	 */
	public DBConnBean(MospParams mospParams, String rdbDriver, String rdbName, String userId, String password)
			throws MospException {
		// コネクション初期化
		connection = null;
		// DB接続設定を用いてコネクションを生成
		createConnection(rdbDriver, rdbName, userId, password);
		// DB接続ログ出力
		LogUtility.dbConnect(mospParams, connection.toString());
	}
	
	/**
	 * データソースを取得する。
	 * @param jndiName JNDI名
	 * @return データソース
	 * @throws MospException JNDIのLookupに失敗した場合
	 */
	protected DataSource getDataSource(String jndiName) throws MospException {
		try {
			// コンテキスト取得
			Context ctx = new InitialContext();
			// データソース取得
			return (DataSource)ctx.lookup(jndiName);
		} catch (NamingException e) {
			// JNDIのLookupに失敗した場合
			throw new MospException(e, ExceptionConst.EX_FAIL_DB_CONNECT, null);
		}
	}
	
	/**
	 * DBコネクションを取得する。<br>
	 * JDBCを用いてDBコネクションを取得し、{@link #connection}に割当てる。<br>
	 * 取得したコネクションはAutoCommit機能を利用しない。<br>
	 * @param rdbdriver JDBC名
	 * @param rdbname DBのURL
	 * @param userid DB接続ユーザーID
	 * @param password パスワード
	 * @throws MospException ドライバクラスが見つからない場合、或いはSQL例外が発生した場合
	 */
	protected void createConnection(String rdbdriver, String rdbname, String userid, String password)
			throws MospException {
		try {
			Class.forName(rdbdriver);
			connection = DriverManager.getConnection(rdbname, userid, password);
			connection.setAutoCommit(false);
		} catch (ClassNotFoundException e) {
			// ドライバクラスが見つからない場合
			throw new MospException(e, ExceptionConst.EX_FAIL_DB_CONNECT, null);
		} catch (SQLException e) {
			// SQL例外が発生した場合
			throw new MospException(e, ExceptionConst.EX_FAIL_DB_CONNECT, null);
		}
	}
	
	/**
	 * DBコネクションを取得する。<br>
	 * DataSourceを用いてDBコネクションを取得し、{@link #connection}に割当てる。<br>
	 * 取得したコネクションはAutoCommit機能を利用しない。<br>
	 * @param dataSource データソース
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void createConnection(DataSource dataSource) throws MospException {
		try {
			// コネクション取得
			connection = dataSource.getConnection();
			connection.setAutoCommit(false);
		} catch (SQLException e) {
			// SQL例外が発生した場合
			throw new MospException(e, ExceptionConst.EX_FAIL_DB_CONNECT, null);
		}
	}
	
	/**
	 * トランザクションをコミットする。<br>
	 * {@link #connection}のトランザクションをコミットする。
	 * @throws MospException コミット時にSQL例外が発生した場合
	 */
	public void commit() throws MospException {
		try {
			if (connection != null && !connection.isClosed()) {
				// コミット
				connection.commit();
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * トランザクションをロールバックする。<br>
	 * {@link #connection}のトランザクションをロールバックする。
	 * @throws MospException ロールバック時にSQL例外が発生した場合
	 */
	public void rollback() throws MospException {
		try {
			if (connection != null && !connection.isClosed()) {
				// ロールバック
				connection.rollback();
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * DBコネクションを開放する。<br>
	 * {@link #connection}を開放する。
	 * @throws MospException DBコネクション解放時にSQL例外が発生した場合
	 */
	public void releaseConnection() throws MospException {
		try {
			if (connection != null && !connection.isClosed()) {
				// ロールバック
				connection.rollback();
				// 解除
				connection.close();
			}
			if (connection != null) {
				connection = null;
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * DBコネクションインスタンス取得。
	 * @return DBコネクションインスタンス。
	 */
	public Connection getConnection() {
		return connection;
	}
	
}
