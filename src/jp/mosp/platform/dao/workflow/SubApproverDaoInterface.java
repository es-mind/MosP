/*
 * MosP - Mind Open Source Project
 * Copyright (C) esMind, LLC  https://www.e-s-mind.com/
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
package jp.mosp.platform.dao.workflow;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.SubApproverDtoInterface;

/**
 * 代理承認者テーブルDAOインターフェース。
 */
public interface SubApproverDaoInterface extends BaseDaoInterface {
	
	/**
	 * 検索条件(代理元個人ID)。
	 */
	String	SEARCH_PERSONAL_ID		= "personalId";
	
	/**
	 * 検索条件(公開開始日)。
	 */
	String	SEARCH_START_DATE		= "startDate";
	
	/**
	 * 検索条件(公開終了日)。
	 */
	String	SEARCH_END_DATE			= "endDate";
	
	/**
	 * 検索条件(代理人所属名)。
	 */
	String	SEARCH_SECTION_NAME		= "sectionName";
	
	/**
	 * 検索条件(代理人氏名)。
	 */
	String	SEARCH_EMPLOYEE_NAME	= "employeeName";
	
	/**
	 * 検索条件(有効無効フラグ)。
	 */
	String	SEARCH_INACTIVATE_FLAG	= "inactivateFlag";
	
	
	/**
	 * 代理承認者登録Noから代理承認者情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param subApproverNo 代理承認者登録No
	 * @return 代理承認者テーブルDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	SubApproverDtoInterface findForKey(String subApproverNo) throws MospException;
	
	/**
	 * 最大メッセージNoを取得する。<br>
	 * @return 最大メッセージNo
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	long getMaxMessageNo() throws MospException;
	
	/**
	 * 代理元個人ID、フロー区分で、対象期間内における代理承認者情報を取得する。<br>
	 * 無効な情報は取得対象外とする。<br>
	 * @param personalId   代理元個人ID
	 * @param workflowType フロー区分
	 * @param termStart    期間開始日
	 * @param termEnd      期間終了日
	 * @return 代理承認者リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubApproverDtoInterface> findForTerm(String personalId, int workflowType, Date termStart, Date termEnd)
			throws MospException;
	
	/**
	 * 代理承認者個人ID、フロー区分で、対象期間内における代理承認者情報を取得する。<br>
	 * 無効な情報は取得対象外とする。<br>
	 * @param subApproverId 代理承認者個人ID
	 * @param workflowType  フロー区分
	 * @param termStart     期間開始日
	 * @param termEnd       期間終了日
	 * @return 代理承認者リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubApproverDtoInterface> findForSubApproverId(String subApproverId, int workflowType, Date termStart,
			Date termEnd) throws MospException;
	
	/**
	 * 条件による検索を行う。<br>
	 * 検索条件群から代理承認者リストを取得する。<br>
	 * @param param 検索条件群
	 * @return 代理承認者リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubApproverDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件群を取得する。
	 * @return 検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
	/**
	 * 対象期間において、対象個人IDが代理承認者或いは代理元として
	 * 設定されている代理承認者情報のリストを取得する。<br>
	 * 検索結果に、開始日または終了日と同日の情報も含まれる。<br>
	 * 削除時の整合性確認等で用いる。<br>
	 * @param startDate  開始日(有効日)
	 * @param endDate    終了日(有効日)
	 * @param personalId 個人ID
	 * @param workflowType フロー区分
	 * @return 代理承認者リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubApproverDtoInterface> findSubApproverForTerm(String personalId, Date startDate, Date endDate,
			int workflowType) throws MospException;
}
