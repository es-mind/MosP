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
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.ManagementRequestListDtoInterface;
import jp.mosp.time.dto.settings.RequestListDtoInterface;

/**
 * 承認情報参照インターフェース。
 */
public interface ApprovalInfoReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 承認可能ワークフロー情報マップ(勤怠)を取得する。<br>
	 * @param personalId 承認者個人ID
	 * @return 承認可能勤怠申請情報群(勤怠)(キー：機能コード)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Map<Long, WorkflowDtoInterface>> getApprovableMap(String personalId) throws MospException;
	
	/**
	 * 代理承認可能ワークフロー情報マップ(勤怠)を取得する。<br>
	 * 但し、承認可能ワークフロー情報マップに含まれるワークフローは、除外する。<br>
	 * @param personalId    承認者個人ID
	 * @param approvableMap 承認可能ワークフロー情報マップ
	 * @return 承認可能勤怠申請情報マップ(勤怠)(キー：機能コード)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Map<Long, WorkflowDtoInterface>> getSubApprovableMap(String personalId,
			Map<String, Map<Long, WorkflowDtoInterface>> approvableMap) throws MospException;
	
	/**
	 * 解除承認可能ワークフロー情報マップ(勤怠)を取得する。<br>
	 * @param personalId 承認者個人ID
	 * @return 解除承認可能勤怠申請情報群(勤怠)(キー：機能コード)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Map<Long, WorkflowDtoInterface>> getCancelableMap(String personalId) throws MospException;
	
	/**
	 * 代理解除承認可能ワークフロー情報マップ(勤怠)を取得する。<br>
	 * 但し、解除承認可能ワークフロー情報マップに含まれるワークフローは、除外する。<br>
	 * @param personalId 承認者個人ID
	 * @param approvableMap 解除承認可能ワークフロー情報マップ
	 * @return 解除承認可能勤怠申請情報マップ(勤怠)(キー：機能コード)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Map<Long, WorkflowDtoInterface>> getSubCancelableMap(String personalId,
			Map<String, Map<Long, WorkflowDtoInterface>> approvableMap) throws MospException;
	
	/**
	 * 承認可能勤怠申請情報リストを取得する。<br>
	 * @param approvableMap    承認可能ワークフロー情報マップ(勤怠)
	 * @param subApprovableMap 代理承認可能ワークフロー情報マップ(勤怠)
	 * @param functionCode     対象機能コード
	 * @return 承認可能勤怠申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ManagementRequestListDtoInterface> getApprovableList(
			Map<String, Map<Long, WorkflowDtoInterface>> approvableMap,
			Map<String, Map<Long, WorkflowDtoInterface>> subApprovableMap, String functionCode) throws MospException;
	
	/**
	 * 対象期間における有効ワークフロー情報一覧を取得する。<br>
	 * 申請者が対象申請者個人IDセットに含まれるもの、
	 * 或いは対象個人IDが承認者として含まれるものを抽出する。<br>
	 * @param personalId       対象個人ID
	 * @param fromDate         対象期間自
	 * @param toDate           対象期間至
	 * @param functionCodeSet  対象機能コードセット
	 * @param state            対象ワークフロー状態
	 * @param personalIdSet    対象申請者個人IDセット(検索条件による)
	 * @param subordinateIdSet 対象申請者個人IDセット(部下)
	 * @return 有効ワークフロー情報一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ManagementRequestListDtoInterface> getEffectiveList(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet, String state, Set<String> personalIdSet, Set<String> subordinateIdSet)
			throws MospException;
	
	/**
	 * 対象期間における承認済ワークフロー情報一覧を取得する。<br>
	 * @param personalId 個人ID
	 * @param fromDate 対象期間自
	 * @param toDate 対象期間至
	 * @param functionCodeSet 対象機能コードセット
	 * @return 承認済ワークフロー情報一覧
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ManagementRequestListDtoInterface> getCompletedList(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet) throws MospException;
	
	/**
	 * 対象ワークフロー情報から、勤怠申請一覧情報を取得する。<br>
	 * @param workflowDto 対象ワークフロー情報
	 * @param isSubApprove 代理承認フラグ(true：代理承認、false：代理承認でない)
	 * @return 承認可能勤怠申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	ManagementRequestListDtoInterface getManagementRequestListDto(WorkflowDtoInterface workflowDto,
			boolean isSubApprove) throws MospException;
	
	/**
	 * ワークフロー番号から申請情報を取得する。
	 * @param workflow ワークフロー番号
	 * @param isApproval 承認利用フラグ、申請で利用する場合はfalse
	 * @return 各種申請DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	BaseDtoInterface getRequestDtoForWorkflow(long workflow, boolean isApproval) throws MospException;
	
	/**
	 * @param dto 申請一覧用DTO
	 * @param workflowDto ワークフローDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void setWorkflowInfo(RequestListDtoInterface dto, WorkflowDtoInterface workflowDto) throws MospException;
	
	/**
	 * ワークフロー情報から次回承認予定者社員名を取得する。
	 * ワークフロー状態が未承認又はn次済の場合、承認予定者。<br>
	 * 他状態の場合は操作者の社員名を取得する。<br>
	 * @param workflowDto ワークフロー情報
	 * @return 承認予定者社員名
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getNextApprover(WorkflowDtoInterface workflowDto) throws MospException;
	
	/**
	 * 対象個人IDと対象日から勤怠の申請が存在するか確認する。
	 * 存在する勤怠申請状態は取下、下書、一次戻以外の申請状態である。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 確認結果(true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean isExistAttendanceTargetDate(String personalId, Date targetDate) throws MospException;
}
