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
package jp.mosp.platform.base;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDao;
import jp.mosp.framework.base.MospException;

/**
 * プラットフォームにおけるDAOの基本機能を提供する。<br>
 * <br>
 */
public abstract class PlatformDao extends BaseDao {
	
	/**
	 * 検索条件を取得する。<br>
	 * キーの検索条件が設定されていない場合、空文字列を返す。<br>
	 * @param searchParams 検索条件群
	 * @param key          検索条件キー
	 * @return 検索条件
	 */
	protected String getSearchParam(Map<String, Object> searchParams, String key) {
		Object obj = searchParams.get(key);
		if (obj instanceof String) {
			return (String)obj;
		}
		return "";
	}
	
	/**
	 * 検索条件を取得する。<br>
	 * キーの検索条件が設定されていない場合、空文字列を返す。<br>
	 * @param searchParams 検索条件群
	 * @param key          検索条件キー
	 * @return 検索条件
	 */
	protected boolean getSearchBoolParam(Map<String, Object> searchParams, String key) {
		Object obj = searchParams.get(key);
		if (obj instanceof Boolean) {
			return ((Boolean)obj).booleanValue();
		}
		return false;
	}
	
	/**
	 * 検索条件を取得する。<br>
	 * キーの検索条件が設定されていない場合、空の配列を返す。<br>
	 * @param searchParams 検索条件群
	 * @param key          検索条件キー
	 * @return 検索条件
	 */
	protected String[] getSearchParams(Map<String, Object> searchParams, String key) {
		Object obj = searchParams.get(key);
		if (obj instanceof String[]) {
			return (String[])obj;
		}
		return new String[0];
	}
	
	/**
	 * @param <T> 対象クラス
	 * @param params 検索条件群
	 * @param key 検索条件キー
	 * @param cls 対象クラス
	 * @return 検索条件
	 */
	public static <T> T getSearchParam(Map<String, Object> params, String key, Class<T> cls) {
		Object obj = params.get(key);
		if (obj != null) {
			return cls.cast(obj);
		}
		return null;
	}
	
	/**
	 * 有効日における最新の情報を抽出する条件SQLを取得する。<br>
	 * 一覧検索時等にFROM句の後で利用される。<br>
	 * @param table テーブル名
	 * @param codeColumn キーとなるコード列名
	 * @param activateDateColumn 有効日列名
	 * @return 有効日における最新の情報を抽出する条件SQL
	 */
	protected String getQueryForMaxActivateDate(String table, String codeColumn, String activateDateColumn) {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(join());
		sb.append(leftParenthesis());
		sb.append(select());
		sb.append(codeColumn);
		sb.append(asTmpTable(codeColumn));
		sb.append(comma());
		sb.append(maxAs(activateDateColumn));
		sb.append(from(table));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(lessEqual(activateDateColumn));
		sb.append(groupBy(codeColumn));
		sb.append(rightParenthesis());
		sb.append(asTmpTable(table));
		sb.append(on());
		sb.append(codeColumn);
		sb.append(equal());
		sb.append(getExplicitTableColumn(getTmpTable(table), getTmpColumn(codeColumn)));
		sb.append(and());
		sb.append(activateDateColumn);
		sb.append(equal());
		sb.append(getExplicitTableColumn(getTmpTable(table), getMaxColumn(activateDateColumn)));
		return sb.toString();
	}
	
	/**
	 * 有効日における最新の情報を抽出する条件SQLを取得する。<br>
	 * 一覧検索時等にFROM句の後で利用される。<br>
	 * @param table 対象テーブル名
	 * @param activateDateColumn 有効日列名
	 * @param codeColumns キーとなるコード列名配列
	 * @return 有効日における最新の情報を抽出する条件SQL
	 */
	protected String getQueryForMaxActivateDate(String table, String activateDateColumn, String... codeColumns) {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(join());
		sb.append(leftParenthesis());
		sb.append(select());
		// コード列名毎に処理
		for (String codeColumn : codeColumns) {
			sb.append(codeColumn);
			sb.append(asTmpTable(codeColumn));
			sb.append(comma());
		}
		sb.append(maxAs(activateDateColumn));
		sb.append(from(table));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(lessEqual(activateDateColumn));
		sb.append(groupBy(codeColumns));
		sb.append(rightParenthesis());
		sb.append(asTmpTable(table));
		sb.append(on());
		// コード列名毎に処理
		for (String codeColumn : codeColumns) {
			sb.append(codeColumn);
			sb.append(equal());
			sb.append(getExplicitTableColumn(getTmpTable(table), getTmpColumn(codeColumn)));
			sb.append(and());
		}
		sb.append(activateDateColumn);
		sb.append(equal());
		sb.append(getExplicitTableColumn(getTmpTable(table), getMaxColumn(activateDateColumn)));
		return sb.toString();
	}
	
	/**
	 * 有効日における最新の情報を抽出する条件のパラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index      パラメータインデックス
	 * @param targetDate 対象日
	 * @param ps         ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	protected int setParamsForMaxActivateDate(int index, Date targetDate, PreparedStatement ps) throws MospException {
		// パラメータインデックス準備
		int idx = index;
		// 有効日における最新の情報を抽出する条件のパラメータを設定
		setParam(idx++, targetDate, false, ps);
		// インデックス返却
		return idx;
	}
	
	/**
	 * ResultSetの内容から対象列の値を文字列として取得し、セットにして返す。<br>
	 * @param column 対象列
	 * @return 文字列群
	 * @throws MospException SQL例外が発生した場合
	 */
	protected Set<String> getResultAsSet(String column) throws MospException {
		Set<String> set = new HashSet<String>();
		while (next()) {
			set.add(getString(column));
		}
		return set;
	}
	
}
