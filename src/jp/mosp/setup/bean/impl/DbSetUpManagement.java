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
package jp.mosp.setup.bean.impl;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.FilenameFilter;
import java.io.IOException;
import java.io.InputStreamReader;
import java.io.UnsupportedEncodingException;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;
import java.text.MessageFormat;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.mosp.framework.base.BaseDao;
import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.DBConnBean;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.LogUtility;
import jp.mosp.framework.utils.ValidateUtility;
import jp.mosp.platform.bean.system.UserMasterSearchBeanInterface;
import jp.mosp.platform.utils.InputCheckUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.setup.base.ConnectionXmlManager;
import jp.mosp.setup.base.DbSetUpException;
import jp.mosp.setup.bean.InitialAccountCreateBeanInterface;
import jp.mosp.setup.constant.Command;
import jp.mosp.setup.constant.SetUpConst;
import jp.mosp.setup.constant.SetUpStatus;
import jp.mosp.setup.dto.DbSetUpParameterInterface;
import jp.mosp.setup.dto.InitialAccountParameterInterface;
import jp.mosp.setup.dto.impl.DbSetUpParameter;

/**
 * データベースセットアップクラス。
 */
public class DbSetUpManagement {
	
	/**
	 * MosP処理情報
	 */
	private MospParams					mospParams;
	
	/**
	 * セットアップパラメータ
	 */
	private DbSetUpParameterInterface	parameter;
	
	
	/**
	 * コンストラクタ。
	 */
	DbSetUpManagement() {
	}
	
	/**
	 * @param mospParams セットする MosP処理情報
	 */
	public void setMospParams(MospParams mospParams) {
		this.mospParams = mospParams;
	}
	
	/**
	 * @param parameter セットする セットアップパラメータ
	 */
	public void setParameter(DbSetUpParameterInterface parameter) {
		this.parameter = parameter;
	}
	
	private DbSetUpManagement(MospParams mospParams, DbSetUpParameterInterface parameter) {
		this.mospParams = mospParams;
		this.parameter = parameter;
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param parameter セットアップパラメータ
	 * @return {@link DbSetUpManagement}インスタンス
	 */
	public static DbSetUpManagement getInstance(MospParams mospParams, DbSetUpParameterInterface parameter) {
		return new DbSetUpManagement(mospParams, parameter);
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 初期化済みセットアップパラメータ
	 */
	public static DbSetUpParameterInterface initParameter(MospParams mospParams) {
		DbSetUpParameter parameter = new DbSetUpParameter();
		// IPまたはサーバー名
		parameter.setServerName(mospParams.getApplicationProperty(SetUpConst.APP_DEFAULT_SERVER_NAME));
		// ポート
		parameter.setPort(mospParams.getApplicationProperty(SetUpConst.APP_DEFAULT_PORT, 5432));
		// postgres
		parameter.setPostgresDb(mospParams.getApplicationProperty(SetUpConst.APP_POSTGRES_DATA_BASE));
		// スーパユーザ
		parameter.setSuperUser(mospParams.getApplicationProperty(SetUpConst.APP_SUPER_USER_NAME));
		// パスワード
		parameter.setSuperPassword(mospParams.getApplicationProperty(SetUpConst.APP_SUPER_USER_PASS));
		// デフォルトユーザ名
		parameter.setDefaultDbUser(mospParams.getApplicationProperty(SetUpConst.APP_DEFAULT_DB_USER));
		// 対象SQLディレクトリ
		parameter.setDirs(mospParams.getApplicationProperties("SetUpDatabase"));
		// デフォルトロールコード
		parameter.setRoleCode(mospParams.getApplicationProperty(SetUpConst.APP_INIT_USER_ROLE_CODE));
		parameter.setCommand(Command.DEFAULT);
		return parameter;
	}
	
	/**
	 * 接続確認
	 * @return {@link SetUpStatus}
	 */
	public SetUpStatus confirm() {
		SuperUserManager manager = null;
		try {
			if (parameter != null) {
				manager = createManager(parameter);
				return SetUpStatus.NULL;
			} else {
				manager = createManager();
				// 既存の接続で出来た場合に有効なユーザがいるか確認。
				if (confirmAccount(manager.getConnection())) {
					// 存在する場合
					return SetUpStatus.ALREADY;
				}
				// 有効なユーザが存在しない場合
				return SetUpStatus.EMPTY;
			}
		} catch (DbSetUpException e) {
			mospParams.addErrorMessage(SetUpConst.MSG_CONECTION);
			if (parameter != null) {
				return SetUpStatus.ERROR;
			}
			return SetUpStatus.NULL;
		} catch (MospException e) {
			// リリース
			manager.release();
			// 有効なユーザが存在しない場合
			return SetUpStatus.EMPTY;
		} finally {
			if (manager != null) {
				// リリース
				manager.release();
			}
		}
	}
	
	/**
	 * 一括処理
	 * @throws MospException 例外処理発生
	 */
	public void execute() throws MospException {
		// 妥当性確認
		validate();
		// DDLリストの作成
		SqlHolder holder = loadFiles();
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// superユーザ
		SuperUserManager manager = null;
		try {
			// 重複確認
			checkDuplicate();
			if (mospParams.hasErrorMessage()) {
				return;
			}
			manager = createManager(parameter);
			// DBの生成
			manager.create(parameter);
			// テーブルの設定
			prepareTable(holder);
			// 初期ユーザの登録
			initialize();
			// エラー発生時DBの削除をする。
			if (mospParams.hasErrorMessage()) {
				manager.destory(parameter);
			}
		} catch (MospException e) {
			if (manager != null) {
				// 例外発生時DBの削除をする。
				manager.destory(parameter);
				// リリース
				manager.release();
			}
			throw e;
		} finally {
			if (manager != null) {
				// リリース
				manager.release();
			}
		}
	}
	
	/**
	 * データベースセットアップ処理
	 * @throws MospException 例外処理発生
	 */
	public void createDataBase() throws MospException {
		// 必須条件
		checkRequired();
		// DDLリストの作成
		SqlHolder holder = loadFiles();
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// XMLの生成
		createConnectionXml();
		if (mospParams.hasErrorMessage()) {
			return;
		}
		SuperUserManager manager = null;
		try {
			// 重複確認
			checkDuplicate();
			if (mospParams.hasErrorMessage()) {
				return;
			}
			manager = createManager(parameter);
			// DBの生成
			manager.create(parameter);
			// テーブルの設定
			prepareTable(holder);
		} catch (MospException e) {
			if (manager != null) {
				// 例外発生時DBの削除をする。
				manager.destory(parameter);
				// リリース
				manager.release();
			}
			throw e;
		} finally {
			if (manager != null) {
				// リリース
				manager.release();
			}
		}
		// XMLの読込
		loadConnectionXml();
	}
	
	/**
	 * データベース及びロールを作成する。<br>
	 * XMLの作成及び初期ユーザの登録は行わない。<br>
	 * <br>
	 * @throws MospException 例外処理発生
	 */
	public void createDataBaseAndRole() throws MospException {
		// 必須条件
		checkRequired();
		// DDLリストの作成
		SqlHolder holder = loadFiles();
		if (mospParams.hasErrorMessage()) {
			return;
		}
		SuperUserManager manager = null;
		try {
			// 重複確認
			checkDuplicate();
			if (mospParams.hasErrorMessage()) {
				return;
			}
			manager = createManager(parameter);
			// DBの生成
			manager.create(parameter);
			// テーブルの設定
			prepareTable(holder);
		} catch (MospException e) {
			if (manager != null) {
				// 例外発生時DBの削除をする。
				manager.destory(parameter);
				// リリース
				manager.release();
			}
			throw e;
		} finally {
			if (manager != null) {
				// リリース
				manager.release();
			}
		}
	}
	
	/**
	 * XMLファイルの生成
	 * @throws MospException XMLファイルを読み込めなかった場合
	 */
	public void createXml() throws MospException {
		// XMLの生成
		createConnectionXml();
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// XMLの読込
		loadConnectionXml();
	}
	
	/**
	 * データベース削除処理
	 * @throws MospException 例外処理発生
	 */
	public void dropDatabase() throws MospException {
		// 必須条件
		checkRequired();
		if (mospParams.hasErrorMessage()) {
			return;
		}
		SuperUserManager manager = null;
		try {
			manager = createManager(parameter);
			// DBの削除。
			manager.destory(parameter);
		} catch (MospException e) {
			// リリース
			manager.release();
			throw e;
		} finally {
			if (manager != null) {
				// リリース
				manager.release();
			}
		}
	}
	
	/**
	 * 初期アカウント登録
	 * @param individual 個別処理フラグ
	 * @throws MospException 例外処理発生
	 */
	public void initialize(boolean... individual) throws MospException {
		boolean isIndividual = individual != null && individual.length > 0 && individual[0];
		parameter.setCommand(Command.AS_SUPER);
		// 接続先をユーザのDBに設定する。
		SuperUserManager manager = null;
		try {
			manager = isIndividual ? createManager() : createManager(parameter);
			// トランザクションを有効にする。
			manager.setAutoCommit(false);
			Connection connection = manager.getConnection();
			InitialAccountCreateBeanInterface create = loadBean(InitialAccountCreateBeanInterface.class, connection);
			
			// パラメータセット
			InitialAccountParameterInterface accountParameter = create.getInitParameter();
			accountParameter.setUserId(parameter.getUserId());
			accountParameter.setEmployeeCode(parameter.getEmployeeCode());
			accountParameter.setLastName(parameter.getLastName());
			accountParameter.setFirstName(parameter.getFirstName());
			accountParameter.setLastKana(parameter.getLastKana());
			accountParameter.setFirstKana(parameter.getFirstKana());
			accountParameter.setEntranceDate(parameter.getEntranceDate());
			accountParameter.setActivateDate(parameter.getActivateDate());
			accountParameter.setRoleCode(parameter.getRoleCode());
			// 登録
			create.execute(accountParameter);
			if (mospParams.hasErrorMessage()) {
				// 登録失敗メッセージ設定
				PfMessageUtility.addMessageInsertFailed(mospParams);
				// リリース
				manager.release();
				return;
			}
			// コミット
			manager.commit();
		} catch (MospException e) {
			// リリース
			manager.release();
			throw e;
		} finally {
			if (manager != null) {
				// リリース
				manager.release();
			}
		}
	}
	
	/**
	 * テーブルセットアップ処理
	 * @param holder クエリ保持オブジェクト
	 * @throws MospException 例外処理発生
	 */
	protected void prepareTable(SqlHolder holder) throws MospException {
		// 接続先をユーザのDBに設定する。
		parameter.setCommand(Command.AS_SUPER);
		SuperUserManager manager = null;
		try {
			manager = createManager(parameter);
			// トランザクションを有効にする。
			manager.setAutoCommit(false);
			// DDLを実行する。
			manager.executeBatch(holder.getQueryList());
			// GRANTを実行する
			manager.grant(holder.getGrantList(), parameter.getDefaultDbUser(), parameter.getUserName());
			// コミット
			manager.commit();
		} catch (DbSetUpException e) {
			if (manager != null) {
				// リリース
				manager.release();
			}
			throw new MospException(e.getCause());
		} finally {
			if (manager != null) {
				// リリース
				manager.release();
			}
		}
	}
	
	/**
	 * @param parameter セットアップパラメータ
	 * @return 処理インスタンス
	 */
	protected SuperUserManager createManager(DbSetUpParameterInterface parameter) {
		String driver = mospParams.getApplicationProperty(DBConnBean.APP_DB_DRIVER);
		// IPまたはサーバー名
		String serverName = parameter.getServerName();
		if (serverName == null || serverName.isEmpty()) {
			serverName = mospParams.getApplicationProperty(SetUpConst.APP_DEFAULT_SERVER_NAME);
		}
		// ポート
		int port = parameter.getPort();
		if (port == 0) {
			port = mospParams.getApplicationProperty(SetUpConst.APP_DEFAULT_PORT, 5432);
		}
		// URL
		String url = MessageFormat.format(mospParams.getApplicationProperty(SetUpConst.APP_DATABASE_URL_PATTERN),
				serverName, String.valueOf(port));
		// データベース
		String dbName = parameter.getPostgresDb();
		// ユーザ
		String user = parameter.getSuperUser();
		if (user == null || user.isEmpty()) {
			user = mospParams.getApplicationProperty("SuperUserName");
		}
		// パスワード
		String password = parameter.getSuperPassword();
		if (password == null || password.isEmpty()) {
			password = mospParams.getApplicationProperty("SuperUserPassword");
		}
		Command command = parameter.getCommand();
		if (Command.AS_USER.equals(command)) {
			dbName = parameter.getDbName();
			user = parameter.getUserName();
			password = parameter.getUserPassword();
		}
		if (Command.AS_SUPER.equals(command)) {
			dbName = parameter.getDbName();
		}
		return SuperUserManager.getInstance(mospParams, driver, url, dbName, user, password);
	}
	
	/**
	 * @return 処理インスタンス
	 */
	protected SuperUserManager createManager() {
		String driver = mospParams.getApplicationProperty(DBConnBean.APP_DB_DRIVER);
		String url = mospParams.getApplicationProperty(DBConnBean.APP_DB_URL);
		String dbName = "";
		String user = mospParams.getApplicationProperty(DBConnBean.APP_DB_USER);
		String password = mospParams.getApplicationProperty(DBConnBean.APP_DB_PASS);
		return SuperUserManager.getInstance(mospParams, driver, url, dbName, user, password);
	}
	
	/**
	 * 重複確認
	 * @throws MospException 例外処理発生
	 */
	protected void checkDuplicate() throws MospException {
		SuperUserManager manager = null;
		try {
			manager = createManager(parameter);
			// 重複確認
			Set<String> set = manager.checkDuplicate(parameter);
			for (String key : set) {
				// 登録失敗メッセージ設定
				mospParams.addErrorMessage(SetUpConst.MSG_YET, mospParams.getName(key));
			}
		} catch (DbSetUpException e) {
			if (manager != null) {
				// リリース
				manager.release();
			}
			throw new MospException(e.getCause());
		} catch (MospException e) {
			// リリース
			manager.release();
			throw new MospException(e.getCause());
		} finally {
			if (manager != null) {
				// リリース
				manager.release();
			}
		}
	}
	
	/**
	 * 妥当性確認
	 */
	protected void validate() {
		// 必須項目の妥当性
		checkRequired();
		// 文字列長の妥当性
		checkLength();
		// 文字種の妥当性
		checkCharacterType();
	}
	
	/**
	 * 必須項目の確認
	 */
	protected void checkRequired() {
		// サーバー名
		String serverName = mospParams.getName("Server");
		InputCheckUtility.checkRequired(mospParams, parameter.getServerName(), serverName);
		// スーパユーザパスワード
		String superUserPassword = mospParams.getName("PosgrePass");
		InputCheckUtility.checkRequired(mospParams, parameter.getSuperPassword(), superUserPassword);
		// DB名
		String dbName = mospParams.getName("DbName");
		InputCheckUtility.checkRequired(mospParams, parameter.getDbName(), dbName);
		// ユーザ名(ロール名)
		String userNamea = mospParams.getName("RoleName");
		String userId = parameter.getUserName();
		InputCheckUtility.checkRequired(mospParams, userId, userNamea);
		if ("user".equals(userId) || "role".equals(userId)) {
			// userやroleは使えない為。
			mospParams.addErrorMessage(SetUpConst.MSG_INPUT_ROLE);
		}
		// パスワード
		String userPassword = mospParams.getName("RolePass");
		InputCheckUtility.checkRequired(mospParams, parameter.getUserPassword(), userPassword);
		
		// SQLインジェクション対策
		checkSQLInjection();
	}
	
	/**
	 * シングルコーテーション(')、行コメント(--)、 セミコロン(;)、バックスラッシュ(\)が含まれる場合、<br>
	 * メッセージを追加する。
	 */
	protected void checkSQLInjection() {
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// パターン
		Pattern pattern = Pattern.compile("'+|--|;+|\\\\+");
		// DB名
		String[] repDbName = { mospParams.getName("DbName") };
		if (pattern.matcher(parameter.getDbName()).find()) {
			// エラーメッセージ追加
			mospParams.addErrorMessage("SUW002", repDbName);
		}
		// ユーザ名(ロール名)
		String[] repUserName = { mospParams.getName("RoleName") };
		if (pattern.matcher(parameter.getUserName()).find()) {
			// エラーメッセージ追加
			mospParams.addErrorMessage("SUW002", repUserName);
		}
		// パスワード
		String[] repUserPassword = { mospParams.getName("RolePass") };
		if (pattern.matcher(parameter.getUserPassword()).find()) {
			// エラーメッセージ追加
			mospParams.addErrorMessage("SUW002", repUserPassword);
		}
	}
	
	/**
	 * 文字列長の妥当性
	 */
	protected void checkLength() {
		// ユーザID
		String userId = mospParams.getName("MospLoginUser");
		InputCheckUtility.checkLength(mospParams, parameter.getUserId(), 50, userId);
		// 社員コード
		String employeeCode = PfNameUtility.employeeCode(mospParams);
		InputCheckUtility.checkLength(mospParams, parameter.getEmployeeCode(), 10, employeeCode);
		// 姓
		String lastName = mospParams.getName("LastName");
		InputCheckUtility.checkLength(mospParams, parameter.getLastName(), 50, lastName);
		// 名
		String firstName = mospParams.getName("FirstName");
		InputCheckUtility.checkLength(mospParams, parameter.getFirstName(), 50, firstName);
		// カナ姓
		String lastKana = mospParams.getName("LastName", "FrontParentheses", "Kana", "BackParentheses");
		InputCheckUtility.checkLength(mospParams, parameter.getLastKana(), 50, lastKana);
		// カナ名
		String firstKana = mospParams.getName("FirstName", "FrontParentheses", "Kana", "BackParentheses");
		InputCheckUtility.checkLength(mospParams, parameter.getFirstKana(), 50, firstKana);
	}
	
	/**
	 * 文字種の妥当性
	 */
	protected void checkCharacterType() {
		// 文字列長(最大文字数)確認
		if (ValidateUtility.chkRegex("[._@A-Za-z0-9-]*", parameter.getUserId()) == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorCheckAlpNumSign(mospParams, mospParams.getName("MospLoginUser"));
		}
		// 社員コード
		String employeeCodeName = PfNameUtility.employeeCode(mospParams);
		InputCheckUtility.checkCode(mospParams, parameter.getEmployeeCode(), employeeCodeName);
	}
	
	/**
	 * @return クエリ保持オブジェクト
	 */
	protected SqlHolder loadFiles() {
		// sqlディレクトリ
		File sql = new File(mospParams.getApplicationProperty(MospConst.APP_DOCBASE), SetUpConst.SQL_DIR);
		List<File> dirList = new ArrayList<File>();
		dirList.add(sql);
		// sqlディレクトリ配下のディレクトリを追加。
		for (String childName : parameter.getDirs()) {
			File child = new File(sql, childName);
			if (child.exists() && child.isDirectory()) {
				dirList.add(child);
			}
		}
		// SQLファイルリストを準備
		List<String> queryList = new LinkedList<String>();
		// GRANTファイルリストを準備
		List<String> grantList = new LinkedList<String>();
		for (File dir : dirList) {
			// SQLファイル
			File[] sqlFiles = dir.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					File file = new File(name);
					if (file.isDirectory()) {
						return false;
					}
					
					// GRANTファイルを除く
					if (name.toLowerCase(Locale.JAPAN).contains(SetUpConst.FILE_GRANT)) {
						return false;
					}
					return Pattern.matches(".*" + "\\" + SetUpConst.SUFFIX_SQL_FILE + "$", name);
				}
			});
			// SQLファイル配列が取得できた場合
			if (sqlFiles != null) {
				// SQLファイルリストに追加
				queryList.addAll(parse(sqlFiles));
			}
			// GRANTファイル
			File[] grantFiles = dir.listFiles(new FilenameFilter() {
				
				@Override
				public boolean accept(File dir, String name) {
					File file = new File(name);
					if (file.isDirectory()) {
						return false;
					}
					// GRANT用SQLファイル
					if (Pattern.matches(".*" + "\\" + SetUpConst.SUFFIX_SQL_FILE + "$", name)
							&& name.toLowerCase(Locale.JAPAN).contains(SetUpConst.FILE_GRANT)) {
						return true;
					}
					return false;
				}
			});
			// GRANTファイル配列が取得できた場合
			if (grantFiles != null) {
				// GRANTファイルリストに追加
				grantList.addAll(parse(grantFiles));
			}
		}
		return new SqlHolder(queryList, grantList);
	}
	
	/**
	 * @param files SQLファイル
	 * @return クエリリスト
	 */
	protected List<String> parse(File... files) {
		List<String> list = new LinkedList<String>();
		for (File sql : files) {
			List<String> queryList = trim(sql);
			if (queryList.isEmpty()) {
				mospParams.addErrorMessage(ExceptionConst.EX_FAIL_INPUT_FILE);
				continue;
			}
			list.addAll(queryList);
		}
		return list;
	}
	
	/**
	 * データベース接続情報のファイルを出力する。<br>
	 * @throws MospException XMLファイルの操作に失敗した場合
	 */
	protected void createConnectionXml() throws MospException {
		// ファイルを初期化
		File xml = new File(mospParams.getApplicationProperty(MospConst.APP_DOCBASE), SetUpConst.PATH_XML_FILE);
		Set<Boolean> overWriteSet = new HashSet<Boolean>();
		if (xml.exists() == false) {
			try {
				overWriteSet.add(xml.createNewFile());
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		overWriteSet.add(xml.isFile());
		overWriteSet.add(xml.canWrite());
		if (overWriteSet.contains(Boolean.FALSE)) {
			// 作成に失敗するとメッセージを返す
			mospParams.addErrorMessage("SUE007");
			return;
		}
		String url = MessageFormat.format(mospParams.getApplicationProperty(SetUpConst.APP_DATABASE_URL_PATTERN),
				parameter.getServerName(), String.valueOf(parameter.getPort()));
		String dbName = parameter.getDbName();
		String user = parameter.getUserName();
		String password = parameter.getUserPassword();
		// 出力
		ConnectionXmlManager.export(xml, url + dbName, user, password);
	}
	
	/**
	 * 接続情報の設定
	 * @throws MospException XMLファイルを読み込めなかった場合
	 */
	protected void loadConnectionXml() throws MospException {
		Map<String, String> map = ConnectionXmlManager
			.load(new File(mospParams.getApplicationProperty(MospConst.APP_DOCBASE), SetUpConst.PATH_XML_FILE));
		mospParams.getProperties().setApplicationProperty(DBConnBean.APP_DB_URL, map.get(DBConnBean.APP_DB_URL));
		mospParams.getProperties().setApplicationProperty(DBConnBean.APP_DB_USER, map.get(DBConnBean.APP_DB_USER));
		mospParams.getProperties().setApplicationProperty(DBConnBean.APP_DB_PASS, map.get(DBConnBean.APP_DB_PASS));
	}
	
	/**
	 * アカウント確認
	 * @param connection コネクション
	 * @return 存在する場合true、存在しない場合false
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	protected boolean confirmAccount(Connection connection) throws MospException {
		UserMasterSearchBeanInterface search = loadBean(UserMasterSearchBeanInterface.class, connection);
		search.setUserId("");
		search.setActivateDate(DateUtility.getSystemDate());
		search.setEmployeeCode("");
		search.setEmployeeName("");
		search.setRoleCode(mospParams.getApplicationProperty(SetUpConst.APP_INIT_USER_ROLE_CODE));
		search.setInactivateFlag("");
		return search.getSearchList().isEmpty() == false;
	}
	
	/**
	 * @param <T> 対象Beanインターフェース
	 * @param cls 対象Beanインターフェース
	 * @param connection コネクション
	 * @return 初期化済みBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	@SuppressWarnings("unchecked")
	protected <T> T loadBean(Class<T> cls, Connection connection) throws MospException {
		return (T)InstanceFactory.loadBean(cls, mospParams, connection);
	}
	
	/**
	 * @param file 対象ファイル
	 * @return コメントを削除したクエリリスト
	 */
	protected List<String> trim(File file) {
		List<String> lineList = read(file);
		Pattern pattern = Pattern.compile("--+");
		List<String> mergeList = new ArrayList<String>();
		for (String line : lineList) {
			// 一行コメントの除去
			Matcher matcher = pattern.matcher(line);
			String value = line;
			if (matcher.find()) {
				value = line.substring(0, matcher.start());
			}
			if (value != null && value.isEmpty() == false) {
				mergeList.add(value);
			}
		}
		StringBuffer sb = new StringBuffer();
		for (String value : mergeList) {
			sb.append(value);
		}
		// 複数行コメントの除去
		String middle = sb.toString().replaceAll("/\\*([^*]|\\*[^/])*\\*/", "");
		
		List<String> queryList = new LinkedList<String>();
		String[] splits = Pattern.compile(";").split(middle);
		for (String query : splits) {
			query = query.replaceAll("\\n+", "");
			if (query.isEmpty() == false) {
				queryList.add(query + ";");
			}
		}
		return queryList;
	}
	
	/**
	 * @param file 対象ファイル
	 * @return 行リスト
	 */
	protected List<String> read(File file) {
		BufferedReader reader = null;
		List<String> list = new ArrayList<String>();
		try {
			reader = new BufferedReader(new InputStreamReader(new FileInputStream(file), SetUpConst.ENCODE_UTF_8));
			String line;
			while ((line = reader.readLine()) != null) {
				list.add(line);
			}
		} catch (UnsupportedEncodingException e) {
			e.printStackTrace();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				if (reader != null) {
					reader.close();
				}
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return list;
	}
	
	
	/**
	 * クエリ保持
	 */
	static class SqlHolder {
		
		private final List<String>	queryList;
		
		private final List<String>	grantList;
		
		
		/**
		 * コンストラクタ。
		 * @param queryList クエリリスト
		 * @param grantList GRANTクエリリスト
		 */
		public SqlHolder(List<String> queryList, List<String> grantList) {
			super();
			this.queryList = queryList;
			this.grantList = grantList;
		}
		
		/**
		 * @return queryList クエリリスト
		 */
		public List<String> getQueryList() {
			return queryList;
		}
		
		/**
		 * @return grantList GRANTクエリリスト
		 */
		public List<String> getGrantList() {
			return grantList;
		}
		
	}
	
	/**
	 * スーパユーザでDBにログインしてセットアップ処理を実行する。
	 */
	static class SuperUserManager extends BaseDao {
		
		/**
		 * コンストラクタ。<br>
		 */
		SuperUserManager() {
		}
		
		@Override
		public void initDao() {
		}
		
		/**
		 * インスタンス取得
		 * @param mospParams MosP処理情報
		 * @param driver     DBドライバ
		 * @param url        URL
		 * @param dbName     DB名
		 * @param user       ユーザID
		 * @param password   パスワード
		 * @return インスタンス
		 */
		public static SuperUserManager getInstance(MospParams mospParams, String driver, String url, String dbName,
				String user, String password) {
			SuperUserManager manager = new SuperUserManager();
			// connectionの生成
			manager.setInitParams(mospParams, manager.connect(driver, url, dbName, user, password));
			return manager;
		}
		
		/**
		 * コネクションを取得する。<br>
		 * @param driver     DBドライバ
		 * @param url        URL
		 * @param dbName     DB名
		 * @param user       ユーザID
		 * @param password   パスワード
		 * @return コネクション
		 */
		Connection connect(String driver, String url, String dbName, String user, String password) {
			try {
				Class.forName(driver);
				return DriverManager.getConnection(url + dbName, user, password);
			} catch (ClassNotFoundException e) {
				throw new DbSetUpException(e);
			} catch (SQLException e) {
				throw new DbSetUpException(e);
			}
		}
		
		/**
		 * {@link Connection#setAutoCommit(boolean)}
		 * @param autoCommit 自動コミットモードを有効にする場合は true、無効にする場合は false
		 */
		public void setAutoCommit(boolean autoCommit) {
			if (connection == null) {
				return;
			}
			try {
				if (connection.isClosed()) {
					return;
				}
				connection.setAutoCommit(autoCommit);
			} catch (SQLException e) {
				throw new DbSetUpException(e);
			}
		}
		
		/**
		 * @return connection コネクション
		 */
		public Connection getConnection() {
			return connection;
		}
		
		/**
		 * {@link Connection#commit()}
		 */
		public void commit() {
			if (connection == null) {
				return;
			}
			try {
				if (connection.isClosed()) {
					return;
				}
				connection.commit();
			} catch (SQLException e) {
				throw new DbSetUpException(e);
			}
		}
		
		/**
		 * {@link Connection#rollback()}<br>
		 * {@link Connection#close()}
		 */
		public void release() {
			if (connection == null) {
				return;
			}
			try {
				if (connection.isClosed()) {
					return;
				}
				if (connection.getAutoCommit() == false) {
					// ロールバック
					connection.rollback();
				}
				// close
				connection.close();
			} catch (SQLException e) {
				throw new DbSetUpException(e);
			}
		}
		
		/**
		 * データベース生成、ロール生成
		 * @param parameter セットアップパラメータ
		 * @throws MospException SQLException
		 */
		public void create(DbSetUpParameterInterface parameter) throws MospException {
			try {
				// 接続ユーザを追加
				createRole(parameter.getUserName(), parameter.getUserPassword());
				// データベースを追加
				createDatabase(parameter.getDbName());
			} catch (DbSetUpException e) {
				// 失敗した場合、削除を試みる。
				destory(parameter);
				throw new MospException(e.getCause());
			}
		}
		
		/**
		 * データベース削除、ロール削除
		 * @param parameter セットアップパラメータ
		 * @throws MospException SQLException
		 */
		public void destory(DbSetUpParameterInterface parameter) throws MospException {
			try {
				// データベースを削除
				dropDatabase(parameter.getDbName());
				// 接続ユーザを削除
				dropRole(parameter.getUserName());
			} catch (DbSetUpException e) {
				throw new MospException(e.getCause());
			}
		}
		
		private void createDatabase(String dbName) {
			String sql = "CREATE DATABASE " + dbName + " ENCODING 'UTF8' TEMPLATE template0";
			executeUpdate(sql);
		}
		
		private void createRole(String user, String password) {
			String sql = "CREATE ROLE " + user + " WITH LOGIN PASSWORD '" + password + "'";
			executeUpdate(sql);
		}
		
		private void dropDatabase(String dbName) {
			String sql = "DROP DATABASE IF EXISTS " + dbName;
			executeUpdate(sql);
		}
		
		private void dropRole(String user) {
			String sql = "DROP ROLE IF EXISTS " + user;
			executeUpdate(sql);
		}
		
		private void executeUpdate(String sql) {
			try {
				prepareStatement(sql);
				executeUpdate();
			} catch (MospException e) {
				try {
					releasePreparedStatement();
				} catch (MospException ex) {
					throw new DbSetUpException(ex.getCause());
				}
				throw new DbSetUpException(e.getCause());
			} finally {
				try {
					releasePreparedStatement();
				} catch (MospException ex) {
					throw new DbSetUpException(ex.getCause());
				}
			}
		}
		
		/**
		 * バッチ処理
		 * @param queryList クエリリスト
		 * @return {@link Statement#executeBatch()}
		 */
		public int[] executeBatch(List<String> queryList) {
			Statement st = null;
			try {
				st = connection.createStatement();
				for (String sql : queryList) {
					st.addBatch(sql);
					// ログ出力
					LogUtility.sqlRegist(mospParams, sql);
				}
				return st.executeBatch();
			} catch (SQLException e) {
				try {
					if (st != null) {
						st.close();
					}
				} catch (SQLException ex) {
					ex.printStackTrace();
				}
				throw new DbSetUpException(e);
			} finally {
				try {
					if (st != null) {
						st.close();
					}
				} catch (SQLException e) {
					e.printStackTrace();
				}
			}
		}
		
		/**
		 * @param queryList GRANTクエリリスト
		 * @param defaultDbUser デフォルトユーザ
		 * @param user 対象ユーザ
		 */
		public void grant(List<String> queryList, String defaultDbUser, String user) {
			List<String> list = new ArrayList<String>();
			for (String sql : queryList) {
				// デフォルトユーザを置換
				list.add(sql.replaceAll(defaultDbUser, user));
			}
			// バッチ処理
			executeBatch(list);
		}
		
		/**
		 * 重複確認
		 * @param parameter セットアップパラメータ
		 * @return 重複が存在する場合、それぞれのnamingキーが含まれる。
		 * @throws MospException SQLException
		 */
		public Set<String> checkDuplicate(DbSetUpParameterInterface parameter) throws MospException {
			Set<String> set = new HashSet<String>();
			try {
				// DB重複確認
				if (duplicateDatabase(parameter.getDbName())) {
					set.add("DbName");
				}
				// ユーザ重複確認
				if (duplicateRole(parameter.getUserName())) {
					set.add("RoleName");
				}
			} catch (DbSetUpException e) {
				throw new MospException(e.getCause());
			}
			return set;
		}
		
		/**
		 * DB重複確認
		 * @param dbName 対象DB名
		 * @return 対象DB名が既に存在する場合true
		 */
		private boolean duplicateDatabase(String dbName) {
			String sql = "SELECT datname FROM pg_database where datname = ?";
			return booleanResult(sql, dbName);
		}
		
		/**
		 * ユーザ重複確認
		 * @param user 対象ユーザ名
		 * @return 対象のユーザ名が存在する場合true
		 */
		private boolean duplicateRole(String user) {
			String sql = "SELECT usename FROM pg_user where usename = ?";
			return booleanResult(sql, user);
		}
		
		private boolean booleanResult(String sql, Object... params) {
			try {
				// パラメータインデックス準備
				int index = 1;
				// ステートメント生成
				prepareStatement(sql);
				// パラメータ設定
				for (Object param : params) {
					ps.setObject(index++, param);
				}
				// SQL実行
				executeQuery();
				// 検索結果確認
				return next();
			} catch (Throwable e) {
				try {
					releaseResultSet();
					releasePreparedStatement();
				} catch (MospException ex) {
					throw new DbSetUpException(ex.getCause());
				}
				throw new DbSetUpException(e.getCause());
			} finally {
				try {
					releaseResultSet();
					releasePreparedStatement();
				} catch (MospException e) {
					throw new DbSetUpException(e.getCause());
				}
			}
		}
		
		@Override
		@Deprecated
		public int delete(BaseDtoInterface baseDto) {
			throw new IllegalArgumentException();
		}
		
		@Override
		@Deprecated
		public BaseDto mapping() {
			throw new IllegalArgumentException();
		}
		
		@Override
		@Deprecated
		public List<?> mappingAll() {
			throw new IllegalArgumentException();
		}
		
		@Override
		@Deprecated
		public void setParams(BaseDtoInterface baseDto, boolean isInsert) {
			throw new IllegalArgumentException();
		}
		
		@Override
		@Deprecated
		public int update(BaseDtoInterface baseDto) {
			throw new IllegalArgumentException();
		}
	}
	
}
