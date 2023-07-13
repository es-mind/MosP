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
/**
 * 
 */
package jp.mosp.time.dao.settings;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.CutoffDtoInterface;

/**
 * 締日管理DAOインターフェース
 */
public interface CutoffDaoInterface extends BaseDaoInterface {
	
	/**
	 * 検索条件(対象日)。
	 */
	String	SEARCH_TARGET_DATE		= "targetDate";
	
	/**
	 * 検索条件(締日コード)。
	 */
	String	SEARCH_CUTOFF_CODE		= "cutoffCode";
	
	/**
	 * 検索条件(締日名称)。
	 */
	String	SEARCH_CUTOFF_NAME		= "cutoffName";
	
	/**
	 * 検索条件(締日略称)。
	 */
	String	SEARCH_CUTOFF_ABBR		= "cutoffAbbr";
	
	/**
	 * 検索条件(締日)。
	 */
	String	SEARCH_CUTOFF_DATE		= "cutoffDate";
	
	/**
	 * 検索条件(未承認仮締)。
	 */
	String	SEARCH_NO_APPROVAL		= "noApproval";
	
	/**
	 * 検索条件(自己月締)。
	 */
	String	SEARCH_SELF_TIGHTENING	= "selfTightening";
	
	/**
	 * 検索条件(無効フラグ)。
	 */
	String	SEARCH_INACTIVATE_FLAG	= "inactivateFlag";
	
	
	/**
	 * 締日コードと有効日から締日情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param cutoffCode 締日コード
	 * @param activateDate 有効日
	 * @return 締日マスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	CutoffDtoInterface findForKey(String cutoffCode, Date activateDate) throws MospException;
	
	/**
	 * 締日マスタ取得。
	 * <p>
	 * 締日コードと有効日から締日マスタを取得する。
	 * </p>
	 * @param cutoffCode 締日コード
	 * @param activateDate 有効日
	 * @return 締日管理DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	CutoffDtoInterface findForInfo(String cutoffCode, Date activateDate) throws MospException;
	
	/**
	 * 履歴一覧。
	 * <p>
	 * 締日コードから締日マスタリストを取得する。
	 * </p>
	 * @param cutoffCode 締日コード。
	 * @return 締日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<CutoffDtoInterface> findForHistory(String cutoffCode) throws MospException;
	
	/**
	 * 有効日マスタ一覧。
	 * <p>
	 * 有効日から締日マスタリストを取得する。
	 * </p>
	 * @param activateDate 有効日
	 * @return 締日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<CutoffDtoInterface> findForActivateDate(Date activateDate) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から締日マスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 締日マスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<CutoffDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 締日管理検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 条件による検索のための文字列。
	 * <p>
	 * 最大有効日レコードのクエリを取得する。
	 * </p>
	 * @return 締日コードに紐づく有効日が最大であるレコード取得クエリ
	 */
	StringBuffer getQueryForMaxActivateDate();
	
}
