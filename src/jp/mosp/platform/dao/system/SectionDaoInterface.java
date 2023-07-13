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
package jp.mosp.platform.dao.system;

import java.sql.PreparedStatement;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.SectionDtoInterface;

/**
 * 所属マスタDAOインターフェース
 */
public interface SectionDaoInterface extends BaseDaoInterface {
	
	/**
	 * 検索条件(対象日)。
	 */
	String	SEARCH_TARGET_DATE	= "targetDate";
	
	/**
	 * 検索条件(検索対象)。
	 */
	String	SEARCH_SECTION_TYPE	= "sectionType";
	
	/**
	 * 検索条件(所属コード)。
	 */
	String	SEARCH_SECTION_CODE	= "sectionCode";
	
	/**
	 * 検索条件(所属名称)。
	 */
	String	SEARCH_SECTION_NAME	= "sectionName";
	
	/**
	 * 検索条件(所属略称)。
	 */
	String	SEARCH_SECTION_ABBR	= "sectionAbbr";
	
	/**
	 * 検索条件(無効フラグ)。
	 */
	String	SEARCH_CLOSE_FLAG	= "closeFlag";
	
	
	/**
	 * 所属コードと有効日から所属情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param sectionCode  所属コード
	 * @param activateDate 有効日
	 * @return 所属マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	SectionDtoInterface findForKey(String sectionCode, Date activateDate) throws MospException;
	
	/**
	 * 所属マスタ取得。
	 * <p>
	 * 所属コードと有効日から所属マスタを取得する。
	 * </p>
	 * @param sectionCode 所属コード
	 * @param activateDate 有効日
	 * @return 所属マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	SectionDtoInterface findForInfo(String sectionCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧を取得する。<br>
	 * 所属コードから所属マスタリストを取得する。<br>
	 * 取得したリストは、有効日の昇順で並べられる。<br>
	 * @param sectionCode 所属コード
	 * @return 所属マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SectionDtoInterface> findForHistory(String sectionCode) throws MospException;
	
	/**
	 * 有効日マスタ一覧を取得する。<br>
	 * 有効日から所属マスタリストを取得する。<br>
	 * 操作範囲配列が空(0行)の場合は、範囲による制限は無し。<br>
	 * 取得したリストは、経路及び所属コードの昇順で並べられる。<br>
	 * @param activateDate 有効日
	 * @param rangeArray   操作範囲配列
	 * @return 所属マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SectionDtoInterface> findForActivateDate(Date activateDate, String[] rangeArray) throws MospException;
	
	/**
	 * 所属マスタリストを取得する。<br>
	 * 削除フラグが立っていないものを対象とする。<br>
	 * 有効日の範囲で検索する。但し、有効日From及び有効日Toは、検索対象に含まれない。<br>
	 * マスタ類無効時の確認等に用いる。<br>
	 * @param fromActivateDate 有効日From
	 * @param toActivateDate   有効日To
	 * @return 所属マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SectionDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate) throws MospException;
	
	/**
	 * 所属情報リストを取得する。<br>
	 * <br>
	 * 所属コードFromと所属コードToで、所属情報リストを取得する。<br>
	 * (section_code >= sectionCodeFrom AND section_code <= sectionCodeTo)<br>
	 * <br>
	 * @param sectionCodeFrom 所属コードFrom
	 * @param sectionCodeTo   所属コードTo
	 * @param activateDate    有効日
	 * @return 所属情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SectionDtoInterface> findForCodeRange(String sectionCodeFrom, String sectionCodeTo, Date activateDate)
			throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件による所属マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 所属マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SectionDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件による所属マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 所属マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SectionDtoInterface> findForExport(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 所属マスタ検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * サブクエリ。
	 * <p>
	 * 所属名称と有効日による所属コードを取得するSQL。
	 * LIKE検索はあいまい検索にて
	 * </p>
	 * @return
	 * <pre>
	 * SELECT 
	 * 	所属コード 
	 * FROM 所属マスタ 
	 * WHERE 有効日以前で最新
	 * AND 削除フラグ = 0
	 * AND (
	 *  所属名称 like ?
	 *  OR 所属略称 like ?
	 *  OR 所属表示名称 like ?
	 * )
	 * </pre>
	 */
	String getQueryForSectionName();
	
	/**
	 * 下位所属条件SQLを作成する。<br>
	 * @param targetColumn 対象所属コード列名
	 * @return 下位所属条件SQL
	 */
	StringBuffer getQueryForLowerSection(String targetColumn);
	
	/**
	 * 下位所属条件パラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index       パラメータインデックス
	 * @param sectionCode 所属コード
	 * @param targetDate  対象日
	 * @param ps          ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForLowerSection(int index, String sectionCode, Date targetDate, PreparedStatement ps)
			throws MospException;
	
	/**
	 * 操作範囲条件SQLを作成する。<br>
	 * @param rangeArray   操作範囲配列
	 * @param targetColumn 操作範囲制限対象列名
	 * @return 操作範囲条件SQL
	 */
	String getQueryForRange(String[] rangeArray, String targetColumn);
	
	/**
	 * 操作範囲条件パラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index      パラメータインデックス
	 * @param rangeArray 操作範囲配列
	 * @param targetDate 対象日
	 * @param ps         ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForRange(int index, String[] rangeArray, Date targetDate, PreparedStatement ps) throws MospException;
	
	/**
	 * 経路条件パラメータ(,sectionCode,)を取得する。<br>
	 * @param sectionCode 所属コード
	 * @return 経路条件パラメータ
	 */
	String getClassRouteParam(String sectionCode);
	
	/**
	 * 所属コード範囲指定での下位所属条件SQLを作成する。<br>
	 * @param targetColumn 対象所属コード列名
	 * @param fromExist    所属コード範囲指定From有無フラグ
	 * @param toExist      所属コード範囲指定From有無フラグ
	 * @return 下位所属条件SQL
	 */
	StringBuffer getQueryForLowerSectionRange(String targetColumn, boolean fromExist, boolean toExist);
	
	/**
	 * 所属コード範囲指定での下位所属条件パラメータを設定する。<br>
	 * 設定したパラメータの数だけ、パラメータインデックスが加算される。<br>
	 * @param index           パラメータインデックス
	 * @param sectionCodeFrom 所属コードFrom
	 * @param sectionCodeTo   所属コードTo
	 * @param targetDate      対象日
	 * @param ps              ステートメント
	 * @return 加算されたパラメータインデックス
	 * @throws MospException SQL例外が発生した場合
	 */
	int setParamsForLowerSectionRange(int index, String sectionCodeFrom, String sectionCodeTo, Date targetDate,
			PreparedStatement ps) throws MospException;
	
}
