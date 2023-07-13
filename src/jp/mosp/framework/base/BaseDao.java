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

import java.lang.reflect.Field;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.SQLTransientException;
import java.sql.Time;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Calendar;
import java.util.Collection;
import java.util.Date;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.utils.DatabaseUtility;
import jp.mosp.framework.utils.LogUtility;

/**
 * DAOの基本機能を提供する。<br><br>
 * Statement取得やSQL実行等、DB操作関連メソッドを有する。<br>
 */
public abstract class BaseDao implements BaseDaoInterface {
	
	// 定数
	/**
	 * 削除フラグ列名<br>
	 * {@link #setCommonParams(BaseDtoInterface, boolean)}、
	 * {@link #mappingCommonInfo(BaseDto)}で用いられる。<br>
	 */
	protected String			colDeleteFlag	= "delete_flag";
	
	/**
	 * デフォルト列名(作成日)。<br>
	 * {@link #setCommonParams(BaseDtoInterface, boolean)}、
	 * {@link #mappingCommonInfo(BaseDto)}で用いられる。<br>
	 * 不要な場合は、""(空文字列)を設定する。
	 */
	protected String			colInsertDate	= "insert_date";
	
	/**
	 * デフォルト列名(作成者)。<br>
	 * {@link #setCommonParams(BaseDtoInterface, boolean)}、
	 * {@link #mappingCommonInfo(BaseDto)}で用いられる。<br>
	 * 不要な場合は、""(空文字列)を設定する。
	 */
	protected String			colInsertUser	= "insert_user";
	
	/**
	 * デフォルト列名(更新日)。<br>
	 * {@link #setCommonParams(BaseDtoInterface, boolean)}、
	 * {@link #mappingCommonInfo(BaseDto)}で用いられる。<br>
	 * 不要な場合は、""(空文字列)を設定する。
	 */
	protected String			colUpdateDate	= "update_date";
	
	/**
	 * デフォルト列名(更新者)。<br>
	 * {@link #setCommonParams(BaseDtoInterface, boolean)}、
	 * {@link #mappingCommonInfo(BaseDto)}で用いられる。<br>
	 * 不要な場合は、""(空文字列)を設定する。
	 */
	protected String			colUpdateUser	= "update_user";
	
	// フィールド
	/**
	 * ログインユーザーID。<br>
	 */
	protected String			userId;
	
	/**
	 * MosP処理情報。
	 */
	protected MospParams		mospParams;
	
	/**
	 * DBコネクション。<br>
	 */
	protected Connection		connection;
	
	/**
	 * 各種SQL実行の際に利用するステートメント。<br>
	 */
	protected PreparedStatement	ps;
	
	/**
	 * 各種検索SQL実行結果。<br>
	 */
	protected ResultSet			rs;
	
	/**
	 * 各種更新SQL実行件数。<br>
	 */
	protected int				cnt;
	
	/**
	 * パラメーターインデックス。<br>
	 */
	protected int				index;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	protected BaseDao() {
		// 処理無し
	}
	
	/**
	 * MosP処理情報とDBコネクションを設定する。<br>
	 * @param mospParams MosP処理情報
	 * @param connection DBコネクション
	 */
	@Override
	public void setInitParams(MospParams mospParams, Connection connection) {
		this.mospParams = mospParams;
		MospUser user = mospParams.getUser();
		if (user != null) {
			userId = user.getUserId();
		}
		this.connection = connection;
	}
	
	/**
	 * DAOインスタンスを生成し、初期化する。<br>
	 * 但し、コネクションは設定しない。<br>
	 * サブクエリを取得したい場合等、DAOインスタンスのみが必要でコネクションは不要な際に用いる。<br>
	 * @param cls 対象DAOインターフェース
	 * @return 初期化されたDAOインスタンス
	 * @throws MospException DAOインスタンスの生成及び初期化に失敗した場合
	 */
	protected BaseDaoInterface loadDao(Class<?> cls) throws MospException {
		// インスタンス生成クラスを用いてDAOインスタンスを生成し初期化
		return InstanceFactory.loadDao(cls, mospParams, null);
	}
	
	/**
	 * Beanインスタンスを生成し、初期化する。<br>
	 * 但し、コネクションは設定しない。<br>
	 * 暗号化したい場合等、Beanインスタンスのみが必要でコネクションは不要な際に用いる。<br>
	 * @param <T> 対象Beanインターフェース
	 * @param cls 対象Beanインターフェース
	 * @return 初期化されたBeanインスタンス
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	@SuppressWarnings("unchecked")
	protected <T extends BaseBeanInterface> T loadBean(Class<T> cls) throws MospException {
		// Beanインスタンスを生成し初期化
		return (T)InstanceFactory.loadBean(cls, mospParams, connection);
	}
	
	/**
	 * ResultSet開放。<br>
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void releaseResultSet() throws MospException {
		try {
			if (rs != null) {
				rs.close();
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * PreparedStatement取得。<br>
	 * @param sql 実行SQL
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void prepareStatement(String sql) throws MospException {
		try {
			ps = connection.prepareStatement(sql);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * PreparedStatement開放。<br>
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void releasePreparedStatement() throws MospException {
		try {
			if (ps != null) {
				ps.close();
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	// パラメータ設定メソッド
	/**
	 * パラメータを設定する(String)。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @param ps    ステートメント
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, String param, PreparedStatement ps) throws MospException {
		try {
			ps.setString(index, param);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * パラメータを設定する。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @param ps    ステートメント
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, int param, PreparedStatement ps) throws MospException {
		try {
			ps.setInt(index, param);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * パラメータを設定する(String)。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, String param) throws MospException {
		setParam(index, param, ps);
	}
	
	/**
	 * パラメータ設定(int)。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, int param) throws MospException {
		try {
			if (ps != null) {
				ps.setInt(index, param);
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * パラメータ設定(long)。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, long param) throws MospException {
		try {
			if (ps != null) {
				ps.setLong(index, param);
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * パラメータ設定(float)。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, float param) throws MospException {
		try {
			if (ps != null) {
				ps.setFloat(index, param);
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * パラメータ設定(double)。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, double param) throws MospException {
		try {
			if (ps != null) {
				ps.setDouble(index, param);
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * パラメータ設定(InputStream)。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, byte[] param) throws MospException {
		try {
			if (ps != null) {
				ps.setBytes(index, param);
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * パラメータを設定する(Date)。<br>
	 * @param index       インデックス
	 * @param param       パラメータ
	 * @param isTimeStamp 設定オブジェクトフラグ(true：java.sql.Timestamp、false：java.sql.Date)
	 * @param ps          ステートメント
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, Date param, boolean isTimeStamp, PreparedStatement ps) throws MospException {
		try {
			if (ps != null) {
				if (param == null) {
					if (isTimeStamp) {
						ps.setTimestamp(index, null);
					} else {
						ps.setDate(index, null);
					}
					
				} else {
					if (isTimeStamp) {
						ps.setTimestamp(index, new Timestamp(param.getTime()));
					} else {
						Calendar cal = Calendar.getInstance();
						cal.setTime(param);
						cal.set(Calendar.HOUR_OF_DAY, 0);
						cal.set(Calendar.MINUTE, 0);
						cal.set(Calendar.SECOND, 0);
						cal.set(Calendar.MILLISECOND, 0);
						ps.setDate(index, new java.sql.Date(cal.getTimeInMillis()));
					}
				}
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * パラメータを設定する(Date)。<br>
	 * @param index       インデックス
	 * @param param       パラメータ
	 * @param isTimeStamp 設定オブジェクトフラグ(true：java.sql.Timestamp、false：java.sql.Date)
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, Date param, boolean isTimeStamp) throws MospException {
		setParam(index, param, isTimeStamp, ps);
	}
	
	/**
	 * パラメータ設定(Date)。<br>
	 * java.sql.Dateとして設定する。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, Date param) throws MospException {
		setParam(index, param, false);
	}
	
	/**
	 * パラメータ設定(Time)。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParam(int index, Time param) throws MospException {
		try {
			if (ps != null) {
				ps.setTime(index, param);
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * パラメータ消去。<br>
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void clearParams() throws MospException {
		try {
			if (ps != null) {
				ps.clearParameters();
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	// 結果取得メソッド
	/**
	 * ResultSetの現在行にある指定された列の値をStringとして取得する。
	 * @param columnLabel 取得対象列名
	 * @return 列値
	 * @throws MospException SQL例外が発生した場合
	 */
	protected String getString(String columnLabel) throws MospException {
		try {
			return rs.getString(columnLabel);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * ResultSetの現在行にある指定された列の値を数値として取得する。
	 * @param columnLabel 取得対象列名
	 * @return 列値
	 * @throws MospException SQL例外が発生した場合
	 */
	protected int getInt(String columnLabel) throws MospException {
		try {
			return rs.getInt(columnLabel);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * ResultSetの現在行にある指定された列の値を数値として取得する。
	 * @param columnLabel 取得対象列名
	 * @return 列値
	 * @throws MospException SQL例外が発生した場合
	 */
	protected long getLong(String columnLabel) throws MospException {
		try {
			return rs.getLong(columnLabel);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * ResultSetの現在行にある指定された列の値を数値として取得する。
	 * @param columnLabel 取得対象列名
	 * @return 列値
	 * @throws MospException SQL例外が発生した場合
	 */
	protected double getDouble(String columnLabel) throws MospException {
		try {
			return rs.getDouble(columnLabel);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * ResultSetカーソルを現在の位置から1行順方向に移動する。
	 * @return 行の有無(true：新しい現在の行が有効、false：それ以上行がない)
	 * @throws MospException SQL例外が発生した場合
	 */
	protected boolean next() throws MospException {
		try {
			return rs.next();
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * ResultSetの現在行にある指定された列の値をDateとして取得する。
	 * @param columnLabel 取得対象列名
	 * @return 列値
	 * @throws MospException SQL例外が発生した場合
	 */
	protected Date getDate(String columnLabel) throws MospException {
		try {
			return rs.getDate(columnLabel);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * ResultSetの現在行にある指定された列の値をTimeとして取得する。
	 * @param columnLabel 取得対象列名
	 * @return 列値
	 * @throws MospException SQL例外が発生した場合
	 */
	protected Date getTime(String columnLabel) throws MospException {
		try {
			return rs.getTime(columnLabel);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * ResultSetの現在行にある指定された列の値をTimestampとして取得する。
	 * @param columnLabel 取得対象列名
	 * @return 列値
	 * @throws MospException SQL例外が発生した場合
	 */
	protected Date getTimestamp(String columnLabel) throws MospException {
		try {
			return rs.getTimestamp(columnLabel);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * ResultSetの現在行にある指定された列の値をbyteの配列として取得する。
	 * @param columnLabel 取得対象列名
	 * @return 列値
	 * @throws MospException SQL例外が発生した場合
	 */
	protected byte[] getBytes(String columnLabel) throws MospException {
		try {
			return rs.getBytes(columnLabel);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	// 共通情報取得及び設定メソッド
	/**
	 * 共通情報取得。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void mappingCommonInfo(BaseDto dto) throws MospException {
		try {
			if (!colDeleteFlag.isEmpty()) {
				dto.setDeleteFlag(rs.getInt(colDeleteFlag));
			}
			if (!colInsertDate.isEmpty()) {
				Date insertDate = new Date(rs.getTimestamp(colInsertDate).getTime());
				dto.setInsertDate(insertDate);
			}
			if (!colInsertUser.isEmpty()) {
				dto.setInsertUser(rs.getString(colInsertUser));
			}
			if (!colUpdateDate.isEmpty()) {
				Date updateDate = new Date(rs.getTimestamp(colUpdateDate).getTime());
				dto.setUpdateDate(updateDate);
			}
			if (!colUpdateUser.isEmpty()) {
				dto.setUpdateUser(rs.getString(colUpdateUser));
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * 共通情報設定。<br>
	 * @param baseDto 対象DTO
	 * @param isInsert 挿入文フラグ(true：挿入文、false：挿入文)
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setCommonParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		Date date = new Date();
		if (!colDeleteFlag.isEmpty()) {
			setParam(index++, baseDto.getDeleteFlag());
		}
		if (isInsert) {
			if (!colInsertDate.isEmpty()) {
				setParam(index++, date, true);
			}
			if (!colInsertUser.isEmpty()) {
				setParam(index++, userId);
			}
		}
		if (!colUpdateDate.isEmpty()) {
			setParam(index++, date, true);
		}
		if (!colUpdateUser.isEmpty()) {
			setParam(index++, userId);
		}
	}
	
	// 確認メソッド
	/**
	 * 挿入件数確認。<br>
	 * @param expectedCount 想定される挿入件数
	 * @throws MospException 想定される件数と異なる場合
	 */
	protected void chkInsert(int expectedCount) throws MospException {
		if (cnt != expectedCount) {
			mospParams.setErrorViewUrl();
			throw new MospException(ExceptionConst.EX_FAIL_INSERT);
		}
	}
	
	/**
	 * 更新件数確認。<br>
	 * @param expectedCount 想定される更新件数
	 * @throws MospException 想定される件数と異なる場合
	 */
	protected void chkUpdate(int expectedCount) throws MospException {
		if (cnt != expectedCount) {
			mospParams.setErrorViewUrl();
			throw new MospException(ExceptionConst.EX_FAIL_UPDATE);
		}
	}
	
	/**
	 * 削除件数確認。<br>
	 * @param expectedCount 想定される削除件数
	 * @throws MospException 想定される件数と異なる場合
	 */
	protected void chkDelete(int expectedCount) throws MospException {
		if (cnt != expectedCount) {
			mospParams.setErrorViewUrl();
			throw new MospException(ExceptionConst.EX_FAIL_DELETE);
		}
	}
	
	// シーケンス取得メソッド
	/**
	 * シーケンスの次の値を取得する。<br>
	 * @param sequence シーケンス名
	 * @return シーケンスの次の値
	 * @throws MospException シーケンス操作に失敗した場合
	 */
	protected long nextValue(String sequence) throws MospException {
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		query.append(select());
		query.append("NEXTVAL(?)");
		// ステートメント生成
		prepareStatement(query.toString());
		// パラメータ設定
		index = 1;
		setParam(index, sequence);
		executeQuery();
		next();
		try {
			return rs.getLong(1);
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	@Override
	public long nextRecordId() throws MospException {
		// シーケンス名を取得
		String sequence = getTable(getClass()) + "_id_seq";
		// シーケンスの次の値を取得
		return nextValue(sequence);
	}
	
	// SQL実行メソッド
	/**
	 * 検索系SQL実行。
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void executeQuery() throws MospException {
		try {
			if (ps != null) {
				rs = ps.executeQuery();
				// ログ出力
				LogUtility.sqlSelect(mospParams, ps.toString());
			}
		} catch (SQLException e) {
			// 一時的な例外の場合
			if (e instanceof SQLTransientException) {
				throw new MospException(e);
			}
			throw new MospException(e);
		}
	}
	
	/**
	 * 更新系SQLを実行する。<br>
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void executeUpdate() throws MospException {
		executeUpdate(true);
	}
	
	/**
	 * 更新系SQL実行(ログ出力制御付)。<br>
	 * @param needLog ログ出力要否
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void executeUpdate(boolean needLog) throws MospException {
		if (ps != null) {
			try {
				cnt = ps.executeUpdate();
				if (needLog) {
					// ログ出力
					LogUtility.sqlRegist(mospParams, ps.toString());
				}
			} catch (SQLException e) {
				// 一時的な例外の場合
				if (e instanceof SQLTransientException) {
					throw new MospException(e);
				}
				throw new MospException(e);
			}
		}
	}
	
	/**
	 * 更新系SQLを実行する。<br>
	 * 処理インデックスが挿入レコード上限数か挿入対象件数に達した場合、
	 * 挿入処理を行う。<br>
	 * {@link #getInsertQuery(Class)}と併せて用いる。
	 * 大量のデータを挿入する場合、パフォーマンスの向上が見込める。<br>
	 * @param cls   DTOクラス
	 * @param size  挿入対象件数
	 * @param max   挿入レコード上限数(一度に挿入する上限)
	 * @param idx   処理インデックス
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void executeUpdate(Class<?> cls, int size, int max, int idx) throws MospException {
		// インデックスを実際の件数に合わせる
		int executeIndex = idx + 1;
		// 挿入レコード上限数か挿入対象件数に達した場合
		if (executeIndex % max == 0 || executeIndex == size) {
			executeUpdate(false);
			clearParams();
			index = 1;
			if (size - executeIndex < max && executeIndex != size) {
				releasePreparedStatement();
				prepareStatement(getInsertQuery(cls, size - executeIndex, max));
			}
		} else {
			cnt = 0;
		}
	}
	
	// SQL作成及び実行メソッド
	/**
	 * 全レコードを取得する。<br>
	 * 但し、削除フラグが立っているものは対象外。<br>
	 * @return DTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	@Override
	public List<?> findAll() throws MospException {
		try {
			StringBuffer sb = getSelectQuery(getClass());
			if (!colDeleteFlag.isEmpty()) {
				sb.append(where());
				sb.append(deleteFlagOff());
			}
			prepareStatement(sb.toString());
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	/**
	 * レコードIDでレコードを取得する。<br>
	 * @param isUpdate アップデートフラグ(true：for update有り、false：for update無し)
	 * @return 取得レコードDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	@Override
	public BaseDto findForKey(long id, boolean isUpdate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			sb.append(getSelectQuery(getClass()));
			sb.append(getConditionForKey(getClass()));
			if (isUpdate) {
				sb.append(getForUpdate());
			}
			prepareStatement(sb.toString());
			setParam(index++, id);
			executeQuery();
			BaseDto dto = null;
			if (rs.next()) {
				dto = mapping();
			}
			return dto;
		} catch (SQLException e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	/**
	 * 挿入SQLを実行する。
	 * @param baseDto 挿入対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	@Override
	public int insert(BaseDtoInterface baseDto) throws MospException {
		index = 1;
		prepareStatement(getInsertQuery(getClass()));
		setParams(baseDto, true);
		executeUpdate();
		chkInsert(1);
		return cnt;
	}
	
	// SQL作成メソッド
	/**
	 * 全件検索SQLを取得する。<br>
	 * @param cls DAOクラス
	 * @return 全件検索SQL文字列(SELECT 列名 FROM テーブル名)
	 * @throws MospException テーブル名、フィールド値の取得に失敗した場合
	 */
	protected StringBuffer getSelectQuery(Class<?> cls) throws MospException {
		// テーブル名取得
		String table = getTable(cls);
		// 列名リスト取得
		List<String> columnList = getColumnList(cls);
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		// 検索SQLを取得
		query.append(getSelectStatement(columnList));
		query.append(from(table));
		return query;
	}
	
	/**
	 * 検索SQLを取得する。<br>
	 * @param table テーブル名
	 * @param column 列名
	 * @return 重複行をまとめる検索SQL文字列(SELECT DISTINCT 列名 FROM テーブル名)
	 * @throws MospException フィールド値の取得に失敗した場合
	 */
	protected StringBuffer getSelectDistinctQuery(String table, String column) throws MospException {
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		// 検索SQLを取得
		query.append(getSelectDistinctStatement(column));
		query.append(from(table));
		return query;
	}
	
	/**
	 * 検索SQLを取得する。<br>
	 * @param columnList 列名リスト
	 * @return 全件検索SQL文字列(SELECT 列名)
	 * @throws MospException フィールド値の取得に失敗した場合
	 */
	protected String getSelectStatement(List<String> columnList) throws MospException {
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		query.append(select());
		// 列名毎に処理
		for (String column : columnList) {
			query.append(column);
			query.append(comma());
		}
		query.append(getCommonColumn());
		query.delete(query.length() - 2, query.length() - 1);
		return query.toString();
	}
	
	/**
	 * 検索SQLを取得する。<br>
	 * @param column 列名
	 * @return 重複行をまとめる検索SQL文字列(SELECT DISTINCT 列名)
	 * @throws MospException フィールド値の取得に失敗した場合
	 */
	protected String getSelectDistinctStatement(String column) throws MospException {
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		query.append(select());
		query.append(distinct());
		// 列名
		query.append(column);
		return query.toString();
	}
	
	/**
	 * 検索SQL文を取得する。<br>
	 * @param cls DAOクラス
	 * @param needTableName テーブル名追加フラグ
	 * @return 全件検索SQL文字列
	 * @throws MospException テーブル名、フィールド値の取得に失敗した場合
	 */
	protected String getSelectStatement(Class<?> cls, boolean needTableName) throws MospException {
		// テーブル名取得
		String table = getTable(cls);
		// 列名リスト取得
		List<String> columnList = getColumnList(cls);
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		query.append(select());
		// 列名毎に処理
		for (String column : columnList) {
			if (needTableName) {
				query.append(table + ".");
			}
			query.append(column);
			query.append(comma());
		}
		query.append(getCommonColumn(cls, needTableName));
		query.delete(query.length() - 2, query.length() - 1);
		return query.toString();
	}
	
	/**
	 * 件数検索SQLを取得する。<br>
	 * @param cls DAOクラス
	 * @return 全件検索SQL文字列
	 * @throws MospException テーブル名の取得に失敗した場合
	 */
	protected String getSelectCountQuery(Class<?> cls) throws MospException {
		StringBuffer query = new StringBuffer();
		query.append(getSelectCountStatement());
		query.append(from(getTable(cls)));
		return query.toString();
	}
	
	/**
	 * 件数検索SQLを取得する。<br>
	 * @return 件数検索SQL文字列(SELECT COUNT(*))
	 */
	protected String getSelectCountStatement() {
		StringBuffer query = new StringBuffer();
		query.append(select());
		query.append(" COUNT(*) ");
		return query.toString();
	}
	
	/**
	 * 挿入SQLを取得する。<br>
	 * @param cls DTOクラス
	 * @return 挿入SQL文字列
	 * @throws MospException テーブル名、フィールド値の取得に失敗した場合
	 */
	protected String getInsertQuery(Class<?> cls) throws MospException {
		// テーブル名取得
		String table = getTable(cls);
		// 列名リスト取得
		List<String> columnList = getColumnList(cls);
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		// 挿入SQLのベースを取得
		query.append(getInsertQueryBase(table, columnList));
		// 挿入SQLのパラメータ部を取得
		query.append(getInsertQueryParams(columnList));
		return query.toString();
	}
	
	/**
	 * 挿入SQLのパラメータ部を取得する。<br>
	 * @param columnList 列名リスト
	 * @return 挿入SQLパラメータ部文字列
	 */
	protected String getInsertQueryParams(List<String> columnList) {
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		query.append(leftParenthesis());
		// 列名の数だけパラメータを追加
		for (int i = 0; i < columnList.size(); i++) {
			query.append("?, ");
		}
		query.append(getCommonParams());
		query.delete(query.length() - 2, query.length());
		query.append(rightParenthesis());
		return query.toString();
	}
	
	/**
	 * 挿入SQLのベースを取得する。<br>
	 * @param table      テーブル名
	 * @param columnList 列名リスト
	 * @return 挿入SQLベース文字列
	 */
	protected String getInsertQueryBase(String table, List<String> columnList) {
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		query.append("INSERT INTO ");
		query.append(table);
		query.append(leftParenthesis());
		// 列名毎に処理
		for (String column : columnList) {
			query.append(column);
			query.append(", ");
		}
		query.append(getCommonColumn());
		query.delete(query.length() - 2, query.length() - 1);
		query.append(rightParenthesis());
		query.append(" VALUES");
		return query.toString();
	}
	
	/**
	 * 更新SQLを取得する。<br>
	 * @param cls DTOクラス
	 * @return 更新SQL文字列
	 * @throws MospException テーブル名、フィールド値の取得に失敗した場合
	 */
	protected String getUpdateQuery(Class<?> cls) throws MospException {
		// テーブル名取得
		String table = getTable(cls);
		// 列名リスト取得
		List<String> columnList = getColumnList(cls);
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		query.append("UPDATE ");
		query.append(table);
		query.append(" SET ");
		// 列名毎に処理
		for (String column : columnList) {
			query.append(equal(column));
			query.append(comma());
		}
		if (!colDeleteFlag.isEmpty()) {
			query.append(equal(colDeleteFlag));
			query.append(comma());
		}
		if (!colUpdateDate.isEmpty()) {
			query.append(equal(colUpdateDate));
			query.append(comma());
		}
		if (!colUpdateUser.isEmpty()) {
			query.append(equal(colUpdateUser));
			query.append(comma());
		}
		query.delete(query.length() - 2, query.length() - 1);
		query.append(getConditionForKey(cls));
		return query.toString();
	}
	
	/**
	 * 物理削除SQLを取得する。<br>
	 * @param cls DTOクラス
	 * @return 物理削除SQL文字列
	 * @throws MospException テーブル名、フィールド値の取得に失敗した場合
	 */
	protected String getPhysicalDeleteQuery(Class<?> cls) throws MospException {
		// テーブル名取得
		String table = getTable(cls);
		StringBuffer query = new StringBuffer();
		query.append("DELETE FROM ");
		query.append(table);
		query.append(getConditionForKey(cls));
		return query.toString();
	}
	
	/**
	 * 削除SQLを取得する。<br>
	 * @param cls DTOクラス
	 * @return 削除SQL文字列
	 * @throws MospException テーブル名、フィールド値の取得に失敗した場合
	 */
	protected String getDeleteQuery(Class<?> cls) throws MospException {
		StringBuffer query = new StringBuffer();
		query.append("DELETE ");
		query.append(from(getTable(cls)));
		query.append(getConditionForKey(cls));
		return query.toString();
	}
	
	/**
	 * キーによる条件SQLを取得する。<br>
	 * @param cls DTOクラス
	 * @return キーによる条件SQL文字列
	 * @throws MospException テーブル名、フィールド値の取得に失敗した場合
	 */
	protected String getConditionForKey(Class<?> cls) throws MospException {
		// キー列名リスト取得
		List<String> keyList = getKeyList(cls);
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		query.append(where());
		// 列名毎に処理
		for (Iterator<String> it = keyList.iterator(); it.hasNext();) {
			query.append(equal(it.next()));
			if (it.hasNext()) {
				query.append(and());
			}
		}
		query.append(" ");
		return query.toString();
	}
	
	/**
	 * キーによるソートSQL取得。<br>
	 * @param cls DAOクラス
	 * @return キーによるソートSQL文字列
	 * @throws MospException フィールド値の取得に失敗した場合
	 */
	protected String getOrderForKey(Class<?> cls) throws MospException {
		// キー列名リスト取得
		List<String> keyList = getKeyList(cls);
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		query.append(getOrderBy());
		// 列名毎に処理
		for (Iterator<String> it = keyList.iterator(); it.hasNext();) {
			query.append(it.next());
			if (it.hasNext()) {
				query.append(comma());
			}
		}
		query.append(" ");
		return query.toString();
	}
	
	/**
	 * 挿入SQLを取得する。<br>
	 * 挿入レコード数分を一度に挿入するSQLを作成する。<br>
	 * 但し、挿入レコード最大数以上はSQLを作成しない。<br>
	 * @param cls  DAOクラス
	 * @param size 挿入レコード数
	 * @param max  挿入レコード最大数
	 * @return 挿入SQL文字列
	 * @throws MospException テーブル名、フィールド値の取得に失敗した場合
	 */
	protected String getInsertQuery(Class<?> cls, int size, int max) throws MospException {
		// テーブル名取得
		String table = getTable(cls);
		// 列名リスト(挿入文)取得
		List<String> columnList = getColumnList(cls);
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		// 挿入SQLのベースを取得
		query.append(getInsertQueryBase(table, columnList));
		// 挿入SQLのパラメータ部を取得
		StringBuffer sb = new StringBuffer(getInsertQueryParams(columnList));
		sb.append("), ");
		// 件数分作成(但しmaxを上限とする)
		for (int i = 0; i < size; i++) {
			if (i == max) {
				break;
			}
			query.append(sb);
		}
		query.delete(query.length() - 2, query.length());
		return query.toString();
	}
	
	/**
	 * 頁操作用SQL取得。<br>
	 * @param sortKey     ソートキー
	 * @param isAscending 昇順フラグ
	 * @return 頁操作用SQL文字列
	 */
	protected String getPageStatement(String sortKey, boolean isAscending) {
		StringBuffer query = new StringBuffer();
		query.append(getOrderBy() + sortKey + " ");
		if (!isAscending) {
			query.append(getDesc());
		}
		query.append(getLimit() + "? ");
		query.append(getOffset() + "? ");
		return query.toString();
	}
	
	/**
	 * {@link #colInsertUser}、{@link #colInsertDate}、
	 * {@link #colUpdateUser}、{@link #colUpdateDate}
	 * のSQL文字列を取得する。<br>
	 * @return 作成者、作成日、更新者、更新日列SQL文字列
	 */
	private String getCommonColumn() {
		StringBuffer query = new StringBuffer();
		if (!colDeleteFlag.isEmpty()) {
			query.append(colDeleteFlag);
			query.append(comma());
		}
		if (!colInsertDate.isEmpty()) {
			query.append(colInsertDate);
			query.append(comma());
		}
		if (!colInsertUser.isEmpty()) {
			query.append(colInsertUser);
			query.append(comma());
		}
		if (!colUpdateDate.isEmpty()) {
			query.append(colUpdateDate);
			query.append(comma());
		}
		if (!colUpdateUser.isEmpty()) {
			query.append(colUpdateUser);
			query.append(comma());
		}
		return query.toString();
	}
	
	/**
	 * {@link #colInsertUser}、{@link #colInsertDate}、
	 * {@link #colUpdateUser}、{@link #colUpdateDate}
	 * のSQL文字列を取得する。<br>
	 * @param cls DAOクラス
	 * @param needTableName テーブル名追加フラグ
	 * @return 作成者、作成日、更新者、更新日列SQL文字列
	 * @throws MospException テーブル名の取得に失敗した場合
	 */
	private String getCommonColumn(Class<?> cls, boolean needTableName) throws MospException {
		// テーブル名取得
		String table = getTable(cls);
		// SQL文字列準備
		StringBuffer query = new StringBuffer();
		if (!colDeleteFlag.isEmpty()) {
			if (needTableName) {
				query.append(table + ".");
			}
			query.append(colDeleteFlag);
			query.append(comma());
		}
		if (!colInsertDate.isEmpty()) {
			if (needTableName) {
				query.append(table + ".");
			}
			query.append(colInsertDate);
			query.append(comma());
		}
		if (!colInsertUser.isEmpty()) {
			if (needTableName) {
				query.append(table + ".");
			}
			query.append(colInsertUser);
			query.append(comma());
		}
		if (!colUpdateDate.isEmpty()) {
			if (needTableName) {
				query.append(table + ".");
			}
			query.append(colUpdateDate);
			query.append(comma());
		}
		if (!colUpdateUser.isEmpty()) {
			if (needTableName) {
				query.append(table + ".");
			}
			query.append(colUpdateUser);
			query.append(comma());
		}
		return query.toString();
	}
	
	/**
	 * {@link #colInsertUser}、{@link #colInsertDate}、
	 * {@link #colUpdateUser}、{@link #colUpdateDate}
	 * のパラメータ設定SQL文字列を取得する。<br>
	 * @return 作成者、作成日、更新者、更新日列パラメータ設定SQL文字列
	 */
	private String getCommonParams() {
		StringBuffer query = new StringBuffer();
		if (!colDeleteFlag.isEmpty()) {
			query.append("?, ");
		}
		if (!colInsertDate.isEmpty()) {
			query.append("?, ");
		}
		if (!colInsertUser.isEmpty()) {
			query.append("?, ");
		}
		if (!colUpdateDate.isEmpty()) {
			query.append("?, ");
		}
		if (!colUpdateUser.isEmpty()) {
			query.append("?, ");
		}
		return query.toString();
	}
	
	/**
	 * @param colDeleteFlag 対象削除フラグカラム
	 * @return colDeleteFlag = {@link MospConst#DELETE_FLAG_OFF}
	 */
	protected static String deleteFlagOff(String colDeleteFlag) {
		if (colDeleteFlag.isEmpty()) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(colDeleteFlag);
		sb.append(" = ");
		sb.append(MospConst.DELETE_FLAG_OFF);
		sb.append(" ");
		return sb.toString();
	}
	
	/**
	 * @param colFlag 対象フラグカラム
	 * @return colFlag = {@link MospConst#FLAG_OFF}
	 */
	protected static String flagOff(String colFlag) {
		if (colFlag.isEmpty()) {
			return "";
		}
		return equal(colFlag, MospConst.FLAG_OFF);
	}
	
	/**
	 * @return delete_flag = {@link MospConst#DELETE_FLAG_OFF}
	 */
	protected String deleteFlagOff() {
		return deleteFlagOff(colDeleteFlag);
	}
	
	// SQL作成補助メソッド
	/**
	 * DAOの列名リストを取得する。<br>
	 * @param cls DAOクラス
	 * @return 列名リスト
	 * @throws MospException テーブル名、フィールド値の取得に失敗した場合
	 */
	protected List<String> getColumnList(Class<?> cls) throws MospException {
		return getFieldList(cls, "COL_");
	}
	
	/**
	 * DAOのキー列名リストを取得する。<br>
	 * @param cls DAOクラス
	 * @return キー列名リスト
	 * @throws MospException テーブル名、フィールド値の取得に失敗した場合
	 */
	protected List<String> getKeyList(Class<?> cls) throws MospException {
		return getFieldList(cls, "KEY_");
	}
	
	/**
	 * DAOのフィールドリストを取得する。<br>
	 * @param cls    DAOクラス
	 * @param prefix 取得対象フィールド名のプレフィックス
	 * @return フィールドリスト
	 * @throws MospException テーブル名、フィールド値の取得に失敗した場合
	 */
	protected List<String> getFieldList(Class<?> cls, String prefix) throws MospException {
		// 列名リスト準備
		List<String> list = new ArrayList<String>();
		// クラスのフィールド配列を取得
		Field[] fields = cls.getFields();
		// フィールド毎に処理
		for (Field field : fields) {
			// フィールド名確認
			if (field.getName().indexOf(prefix) == 0) {
				// 列名リストに追加
				list.add(getFieldValue(field));
			}
		}
		return list;
	}
	
	/**
	 * フィールド値を取得する。<br>
	 * @param field フィールド
	 * @return フィールド値
	 * @throws MospException フィールド値の取得に失敗した場合
	 */
	protected String getFieldValue(Field field) throws MospException {
		try {
			return (String)field.get(null);
		} catch (IllegalAccessException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * テーブル名取得。<br>
	 * @param cls DAOクラス
	 * @return テーブル名文字列
	 * @throws MospException テーブル名の取得に失敗した場合
	 */
	@Override
	public String getTable(Class<?> cls) throws MospException {
		try {
			Field table = cls.getField("TABLE");
			return (String)table.get(null);
		} catch (IllegalAccessException e) {
			throw new MospException(e);
		} catch (NoSuchFieldException e) {
			throw new MospException(e);
		}
	}
	
	// SQL文字列取得メソッド
	/**
	 * 明示的にテーブルカラム名を取得。<br>
	 * @param tableName テーブル名
	 * @param column 対象カラム
	 * @return テーブル名.対象カラム
	 */
	protected static String getExplicitTableColumn(String tableName, String column) {
		return tableName + "." + column;
	}
	
	/**
	 * 一時テーブル名宣言。<br>
	 * @param tableName テーブル名
	 * @return as tmp_テーブル名
	 */
	protected static String asTmpTable(String tableName) {
		return " AS " + getTmpTable(tableName);
	}
	
	/**
	 * 一時テーブル名取得。<br>
	 * @param tableName テーブル名
	 * @return tmp_テーブル名
	 */
	protected static String getTmpTable(String tableName) {
		return "tmp_" + tableName;
	}
	
	/**
	 * 一時カラム名取得。
	 * @param column 対象カラム
	 * @return tmp_対象カラム
	 */
	protected static String getTmpColumn(String column) {
		return "tmp_" + column;
	}
	
	/**
	 * 一時テーブルカラム名取得。
	 * @param tableName テーブル名
	 * @param column 対象カラム
	 * @return tmp_テーブル名.対象カラム
	 */
	protected static String getTmpTableColumn(String tableName, String column) {
		return getExplicitTableColumn(getTmpTable(tableName), column);
	}
	
	/**
	 * 一時テーブルカラムとテーブルカラムの一致。
	 * @param tableName テーブル名
	 * @param column 対象カラム
	 * @return テーブル名.対象カラム = tmp_テーブル名.対象カラム
	 */
	protected static String equalTmpColumn(String tableName, String column) {
		StringBuffer sb = new StringBuffer();
		sb.append(getExplicitTableColumn(tableName, column));
		sb.append(equal());
		sb.append(getTmpTableColumn(tableName, column));
		return sb.toString();
	}
	
	/**
	 * FOR UPDATE SQL取得。<br>
	 * @return FOR UPDATE 文字列
	 */
	protected static String getForUpdate() {
		return " FOR UPDATE";
	}
	
	/**
	 * ORDER BY SQL取得。<br>
	 * @return ORDER BY 文字列
	 */
	protected static String getOrderBy() {
		return " ORDER BY ";
	}
	
	/**
	 * DESC SQL取得。<br>
	 * @return DESC 文字列
	 */
	protected static String getDesc() {
		return " DESC ";
	}
	
	/**
	 * @return	DESC LIMIT 1
	 */
	protected static String getDescLimit1() {
		return " DESC LIMIT 1";
	}
	
	/**
	 * LIMIT SQL取得。<br>
	 * @return LIMIT 文字列
	 */
	protected static String getLimit() {
		return " LIMIT ";
	}
	
	/**
	 * OFFSET SQL取得。<br>
	 * @return OFFSET 文字列
	 */
	protected static String getOffset() {
		return " OFFSET ";
	}
	
	/**
	 * @return WHERE 文字列
	 */
	protected static String where() {
		return " WHERE ";
	}
	
	/**
	 * @return JOIN 文字列
	 */
	protected static String join() {
		return " JOIN ";
	}
	
	/**
	 * @return LEFT JOIN 文字列
	 */
	protected static String leftJoin() {
		return " LEFT JOIN ";
	}
	
	/**
	 * @return DISTINCT 文字列
	 */
	protected static String distinct() {
		return " DISTINCT ";
	}
	
	/**
	 * 前方一致
	 * @param param 対象パラメータ
	 * @return param + %
	 */
	protected static String startWithParam(String param) {
		return param + "%";
	}
	
	/**
	 * 後方一致
	 * @param param 対象パラメータ
	 * @return % + param
	 */
	protected static String endWithParam(String param) {
		return "%" + param;
	}
	
	/**
	 * 部分一致
	 * @param param 対象パラメータ
	 * @return % + param + %
	 */
	protected static String containsParam(String param) {
		return "%" + param + "%";
	}
	
	/**
	 * カンマ区切り<br>
	 * @param param パラメータ
	 * @param isContain 部分一致フラグ
	 * @return , + param + , 部分一致の場合、%, + param + ,%
	 * @throws MospException SQL例外が発生した場合
	 */
	protected static String boxedByCommaParam(String param, boolean isContain) throws MospException {
		StringBuffer sb = new StringBuffer();
		if (param != null) {
			if (!param.startsWith(",")) {
				sb.append(",");
			}
			sb.append(param);
			if (!param.endsWith(",")) {
				sb.append(",");
			}
			if (isContain) {
				return containsParam(sb.toString());
			}
		}
		return sb.toString();
	}
	
	/**
	 * @param column 対象カラム
	 * @return column IS NULL
	 */
	protected static String isNull(String column) {
		return " " + column + " IS NULL ";
	}
	
	/**
	 * @param column 対象カラム
	 * @return column IS NOT NULL
	 */
	protected static String isNotNull(String column) {
		return " " + column + " IS NOT NULL ";
	}
	
	/**
	 * @param column 対象カラム
	 * @return SELECT MAX(column)
	 */
	protected static String selectMax(String column) {
		StringBuffer sb = new StringBuffer();
		sb.append("SELECT ");
		sb.append(max(column));
		return sb.toString();
	}
	
	/**
	 * @param column 対象カラム
	 * @return SELECT MAX(column)
	 */
	protected static String max(String column) {
		StringBuffer sb = new StringBuffer();
		sb.append("MAX(");
		sb.append(column);
		sb.append(") ");
		return sb.toString();
	}
	
	/**
	 * @param column 対象カラム
	 * @return MAX(column) AS max_column
	 */
	protected static String maxAs(String column) {
		StringBuffer sb = new StringBuffer();
		sb.append(max(column));
		sb.append(" AS ");
		sb.append(getMaxColumn(column));
		return sb.toString();
	}
	
	/**
	 * 一時カラム名取得。
	 * @param column 対象カラム
	 * @return tmp_対象カラム
	 */
	protected static String getMaxColumn(String column) {
		return "max_" + column;
	}
	
	/**
	 * @param column 対象カラム
	 * @return MIN(column)
	 */
	protected static String min(String column) {
		StringBuilder sb = new StringBuilder();
		sb.append("MIN(");
		sb.append(column);
		sb.append(") ");
		return sb.toString();
	}
	
	/**
	 * @param tableName テーブル名
	 * @return FROM テーブル名
	 */
	protected static String from(String tableName) {
		StringBuffer sb = new StringBuffer();
		sb.append(" FROM ");
		sb.append(tableName);
		sb.append(" ");
		return sb.toString();
	}
	
	/**
	 * @return AND
	 */
	protected static String and() {
		return " AND ";
	}
	
	/**
	 * @return OR
	 */
	protected static String or() {
		return " OR ";
	}
	
	/**
	 * @return ON
	 */
	protected static String on() {
		return " ON ";
	}
	
	/**
	 * @return IN
	 */
	protected static String in() {
		return " IN ";
	}
	
	/**
	 * @return NOT IN
	 */
	protected static String notIn() {
		return " NOT IN ";
	}
	
	/**
	 * @return (
	 */
	protected static String leftParenthesis() {
		return " ( ";
	}
	
	/**
	 * @return )
	 */
	protected static String rightParenthesis() {
		return " ) ";
	}
	
	/**
	 * @return SELECT
	 */
	protected static String select() {
		return " SELECT ";
	}
	
	/**
	 * @return ' '
	 */
	protected static String blank() {
		return "' '";
	}
	
	/**
	 * @return ,
	 */
	protected static String comma() {
		return ", ";
	}
	
	/**
	 * @param column1 対象カラム1
	 * @param column2 対象カラム2
	 * @return column1 || column2
	 */
	protected static String concat(String column1, String column2) {
		return column1 + " || " + column2;
	}
	
	/**
	 * @param column1 対象カラム1
	 * @param column2 対象カラム2
	 * @param column3 対象カラム3
	 * @return column1 || column2 || column3
	 */
	protected static String concat(String column1, String column2, String column3) {
		return column1 + " || " + column2 + " || " + column3;
	}
	
	/**
	 * @return =
	 */
	protected static String equal() {
		return " = ";
	}
	
	/**
	 * @param column 対象カラム
	 * @return column = ?
	 */
	protected static String equal(String column) {
		return column + " = ? ";
	}
	
	/**
	 * @param column 対象カラム
	 * @param value  対象値
	 * @return column = value
	 */
	protected static String equal(String column, long value) {
		return column + " = " + value + " ";
	}
	
	/**
	 * @param column 対象カラム
	 * @param value  対象値
	 * @return column = 'value'
	 */
	protected static String equal(String column, String value) {
		return column + " = '" + value + "' ";
	}
	
	/**
	 * @return  LIKE
	 */
	protected static String like() {
		return " LIKE ";
	}
	
	/**
	 * @param column 対象カラム
	 * @return column LIKE ?
	 */
	protected static String like(String column) {
		return column + like() + " ? ";
	}
	
	/**
	 * @param column 対象カラム
	 * @return column > ?
	 */
	protected static String greater(String column) {
		return column + greater() + " ? ";
	}
	
	/**
	 * @return >
	 */
	protected static String greater() {
		return " > ";
	}
	
	/**
	 * @return >=
	 */
	protected static String greaterEqual() {
		return " >= ";
	}
	
	/**
	 * @param column 対象カラム
	 * @return column >= ?
	 */
	protected static String greaterEqual(String column) {
		return column + " >= ? ";
	}
	
	/**
	 * @return <
	 */
	protected static String less() {
		return " < ";
	}
	
	/**
	 * @param column 対象カラム
	 * @return column < ?
	 */
	protected static String less(String column) {
		return column + less() + " ? ";
	}
	
	/**
	 * @return <=
	 */
	protected static String lessEqual() {
		return " <= ";
	}
	
	/**
	 * @param column 対象カラム
	 * @return column <= ?
	 */
	protected static String lessEqual(String column) {
		return column + " <= ? ";
	}
	
	/**
	 * @param column 対象カラム
	 * @return column <> ?
	 */
	protected static String notEqual(String column) {
		return column + " <> ? ";
	}
	
	/**
	 * @param column 対象カラム
	 * @param value  対象値
	 * @return column <> value
	 */
	protected static String notEqual(String column, long value) {
		return column + " <> " + value + " ";
	}
	
	/**
	 * @param column 対象カラム
	 * @param value  対象値
	 * @return column <> 'value'
	 */
	protected static String notEqual(String column, String value) {
		return column + " <> '" + value + "' ";
	}
	
	/**
	 * @param column 対象カラム
	 * @return ORDER BY column
	 */
	protected static String getOrderByColumn(String column) {
		StringBuffer sb = new StringBuffer();
		sb.append(getOrderBy());
		sb.append(column);
		sb.append(" ");
		return sb.toString();
	}
	
	/**
	 * @param column1 対象カラム1
	 * @param column2 対象カラム2
	 * @return ORDER BY column
	 */
	protected static String getOrderByColumn(String column1, String column2) {
		StringBuffer sb = new StringBuffer();
		sb.append(getOrderBy());
		sb.append(column1);
		sb.append(", ");
		sb.append(column2);
		sb.append(" ");
		return sb.toString();
	}
	
	/**
	 * @param columns 対象カラム
	 * @return ORDER BY columns
	 */
	protected static String getOrderByColumns(String... columns) {
		if (columns.length == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(getOrderBy());
		for (String column : columns) {
			if (column.isEmpty()) {
				continue;
			}
			sb.append(column);
			sb.append(", ");
		}
		if (sb.length() > 0) {
			sb.delete(sb.length() - 2, sb.length() - 1);
		}
		return sb.toString();
	}
	
	/**
	 * @param column 対象カラム
	 * @return ORDER BY column DESC LIMIT 1
	 */
	protected static String getOrderByColumnDescLimit1(String column) {
		StringBuffer sb = new StringBuffer();
		sb.append(getOrderByColumn(column));
		sb.append(getDescLimit1());
		return sb.toString();
	}
	
	/**
	 * @param column1 対象カラム1
	 * @param column2 対象カラム2
	 * @return ORDER BY column1 DESC, column2 DESC
	 */
	protected static String getOrderByColumnDesc(String column1, String column2) {
		StringBuffer sb = new StringBuffer();
		sb.append(getOrderBy());
		sb.append(column1);
		sb.append(getDesc());
		sb.append(", ");
		sb.append(column2);
		sb.append(getDesc());
		return sb.toString();
	}
	
	/**
	 * @return EXISTS
	 */
	public static String exists() {
		return " EXISTS ";
	}
	
	/**
	 * @return NOT EXISTS
	 */
	public static String notExists() {
		return " NOT EXISTS ";
	}
	
	/**
	 * @return GROUP BY
	 */
	protected static String groupBy() {
		return " GROUP BY ";
	}
	
	/**
	 * @return LIMIT 1
	 */
	protected static String limitOne() {
		return " LIMIT 1 ";
	}
	
	/**
	 * @param columns 対象列名
	 * @return GROUP BY column
	 */
	protected static String groupBy(String... columns) {
		// SQL文字列準備
		StringBuffer sb = new StringBuffer();
		// GROUP BY
		sb.append(groupBy());
		// 対象列名毎に処理
		for (Iterator<String> it = Arrays.asList(columns).iterator(); it.hasNext();) {
			sb.append(it.next());
			if (it.hasNext()) {
				sb.append(comma());
			}
		}
		sb.append(" ");
		return sb.toString();
	}
	
	/**
	 * RDBMS種類を取得する。<br>
	 * {@link #connection}の情報からRDBMSの種類を特定する。<br>
	 * @return RDBMS種類
	 * @throws MospException SQL例外が発生した場合、或いはMosPが扱えないRDBMSを取得した場合
	 */
	protected RDBMSType getRdbmsType() throws MospException {
		RDBMSType type = DatabaseUtility.getRDBMS(connection);
		if (type != null) {
			return type;
		}
		// 例外発行
		mospParams.setErrorViewUrl();
		throw new MospException(ExceptionConst.EX_UNKNOWN_RDBMS);
	}
	
	/**
	 * @param <T> 対象DTOインターフェース
	 * @param baseDto 対象DTO
	 * @return キャストされたオブジェクト
	 */
	@SuppressWarnings("unchecked")
	protected <T> T cast(BaseDtoInterface baseDto) {
		return (T)baseDto;
	}
	
	/**
	 * @param <T> 対象DTOインターフェース
	 * @param list 対象DTOリスト
	 * @return キャストされたリスト
	 */
	@SuppressWarnings("unchecked")
	protected <T> List<T> cast(List<?> list) {
		return (List<T>)list;
	}
	
	/**
	 * @return inactivate_flag = 0
	 */
	protected static String inactivateFlagOff() {
		final String COL_INACTIVATE_FLAG = "inactivate_flag";
		return equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF);
	}
	
	/**
	 * @param phrases 括弧の中身
	 * @return (・・・)
	 */
	public static String parenthesis(Object... phrases) {
		StringBuffer sb = new StringBuffer();
		if (phrases != null) {
			sb.append(leftParenthesis());
			for (Object phrase : phrases) {
				if (phrase != null) {
					sb.append(phrase);
				}
			}
			sb.append(rightParenthesis());
		}
		return sb.toString();
	}
	
	/**
	 * @param phrase ASの後ろ
	 * @return AS ・・・
	 */
	public static String as(String phrase) {
		return " AS " + phrase;
	}
	
	/**
	 * @param columns 列
	 * @return USING ・,・,・
	 */
	public static String using(String... columns) {
		StringBuffer sb = new StringBuffer();
		if (columns != null) {
			sb.append(" USING ");
			sb.append(leftParenthesis());
			for (Iterator<String> iterator = Arrays.asList(columns).iterator(); iterator.hasNext();) {
				sb.append(iterator.next());
				if (iterator.hasNext()) {
					sb.append(",");
				}
			}
			sb.append(rightParenthesis());
		}
		return sb.toString();
	}
	
	/**
	 * @param leftTable   左テーブル
	 * @param leftColumn  左列
	 * @param rightTable  右テーブル
	 * @param rightColumn 右列
	 * @return leftTable.leftColumn = rightTable.rightColumn
	 */
	public static String eq(String leftTable, String leftColumn, String rightTable, String rightColumn) {
		StringBuffer sb = new StringBuffer();
		sb.append(getExplicitTableColumn(leftTable, leftColumn));
		sb.append(" = ");
		sb.append(getExplicitTableColumn(rightTable, rightColumn));
		return sb.toString();
	}
	
	/**
	 * SQLのインフレーズを返す
	 *
	 * @param column カラム名
	 * @param size パラメータサイズ
	 * @return SQLのINフレーズ　"AND column IN ( ?, ... ? )"
	 */
	public static String in(String column, int size) {
		if (size == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(and());
		sb.append(column);
		sb.append(in());
		sb.append(leftParenthesis());
		for (int i = 0; i < size - 1; i++) {
			sb.append(" ?,");
		}
		sb.append(" ? ");
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	/**
	 * SQLのインフレーズを返す
	 *
	 * @param column カラム名
	 * @param size パラメータサイズ
	 * @return SQLのNOT INフレーズ　"AND column NOT IN ( ?, ... ? )"
	 */
	public static String notIn(String column, int size) {
		if (size == 0) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(and());
		sb.append(column);
		sb.append(" NOT ");
		sb.append(in());
		sb.append(leftParenthesis());
		for (int i = 0; i < size - 1; i++) {
			sb.append(" ?,");
		}
		sb.append(" ? ");
		sb.append(rightParenthesis());
		return sb.toString();
	}
	
	/**
	 * @param condition 条件式
	 * @param columnName カラム名
	 * @return SUM ( 条件式 ) AS カラム名
	 */
	protected static String sum(String condition, String columnName) {
		return " SUM (" + condition + ") AS " + columnName;
	}
	
	/**
	 * SQLのインフレーズにパラメータをセットする。
	 * @param contents セットする内容(文字列の配列)
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParamsIn(String... contents) throws MospException {
		for (String content : contents) {
			setParam(index++, content);
		}
	}
	
	/**
	 * SQLのインフレーズにパラメータをセットする。
	 * @param contents セットする内容(文字列の配列)
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParamsStringIn(Collection<String> contents) throws MospException {
		for (String content : contents) {
			setParam(index++, content);
		}
	}
	
	/**
	 * SQLのインフレーズにパラメータをセットする。
	 * @param set セットする内容(longのセット)
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParamsIn(Set<Long> set) throws MospException {
		for (long id : set) {
			setParam(index++, id);
		}
	}
	
	/**
	 * SQLのインフレーズにパラメータをセットする。
	 * @param set セットする内容(Dateのセット)
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParamsDateIn(Set<Date> set) throws MospException {
		for (Date param : set) {
			setParam(index++, param);
		}
	}
	
	/**
	 * SQLのインフレーズにパラメータをセットする
	 * @param contents セットする内容(intの配列)
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setParamsIn(int... contents) throws MospException {
		for (int content : contents) {
			setParam(index++, content);
		}
	}
	
}
