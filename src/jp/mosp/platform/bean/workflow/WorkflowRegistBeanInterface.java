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
package jp.mosp.platform.bean.workflow;

import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;

/**
 * ワークフロー登録インターフェース。<br>
 */
public interface WorkflowRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	WorkflowDtoInterface getInitDto();
	
	/**
	 * 機能コードを設定した登録用のワークフロー情報を取得する。<br>
	 * 但し、ワークフロー番号で情報が取得できた場合は、そのワークフロー情報を取得する。<br>
	 * @param workflow     ワークフロー番号
	 * @param functionCode 機能コード
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface getInitDto(long workflow, String functionCode) throws MospException;
	
	/**
	 * 下書を行う。<br>
	 * 対象となるワークフロー情報を返す。<br>
	 * 下書に失敗した場合は、nullを返す。<br>
	 * @param dto 対象DTO
	 * @param personalId 申請者個人ID
	 * @param targetDate 申請対象日
	 * @param workflowType フロー区分
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface draft(WorkflowDtoInterface dto, String personalId, Date targetDate, int workflowType)
			throws MospException;
	
	/**
	 * 取下を行う。<br>
	 * @param dto 対象DTO
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface withdrawn(WorkflowDtoInterface dto) throws MospException;
	
	/**
	 * 申請を行う。<br>
	 * @param dto             対象DTO
	 * @param personalId      申請者個人ID
	 * @param targetDate      申請対象日
	 * @param workflowType    フロー区分
	 * @param workflowComment ワークフローコメント
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface appli(WorkflowDtoInterface dto, String personalId, Date targetDate, int workflowType,
			String workflowComment) throws MospException;
	
	/**
	 * 承認解除申請を行う。<br>
	 * @param dto 対象DTO
	 * @param workflowComment ワークフローコメント
	 * @param ckbWithdrawn 取下げ希望チェックボックス
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface cancelAppli(WorkflowDtoInterface dto, String workflowComment, String ckbWithdrawn)
			throws MospException;
	
	/**
	 * 承認を行う。<br>
	 * @param dto             対象DTO
	 * @param workflowType    フロー区分
	 * @param workflowComment ワークフローコメント
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface approve(WorkflowDtoInterface dto, int workflowType, String workflowComment)
			throws MospException;
	
	/**
	 * 差戻を行う。<br>
	 * @param dto             対象DTO
	 * @param workflowType    フロー区分
	 * @param workflowComment ワークフローコメント
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface revert(WorkflowDtoInterface dto, int workflowType, String workflowComment)
			throws MospException;
	
	/**
	 * 解除承認差戻を行う。<br>
	 * @param dto 対象DTO
	 * @param workflowComment ワークフローコメント
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface cancelRevert(WorkflowDtoInterface dto, String workflowComment) throws MospException;
	
	/**
	 * 承認解除を行う。<br>
	 * @param dto             対象DTO
	 * @param workflowType    フロー区分
	 * @param workflowComment ワークフローコメント
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface cancel(WorkflowDtoInterface dto, int workflowType, String workflowComment)
			throws MospException;
	
	/**
	 * 解除承認を行う。<br>
	 * @param dto 対象DTO
	 * @param workflowComment ワークフローコメント
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface cancelApprove(WorkflowDtoInterface dto, String workflowComment) throws MospException;
	
	/**
	 * 解除承認(取下希望)を行う。
	 * @param dto 対象DTO
	 * @param workflowComment ワークフローコメント
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface cancelWithdrawnApprove(WorkflowDtoInterface dto, String workflowComment) throws MospException;
	
	/**
	 * 承認者個人IDを設定する。<br>
	 * @param dto 対象DTO
	 * @param aryApproverId 承認者個人ID配列
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void setDtoApproverIds(WorkflowDtoInterface dto, String[] aryApproverId) throws MospException;
	
	/**
	 * 自己承認を設定する。<br>
	 * @param dto 対象DTO
	 */
	void setSelfApproval(WorkflowDtoInterface dto);
	
	/**
	 * 削除を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(WorkflowDtoInterface dto) throws MospException;
	
	/**
	 * 削除を行う。<br>
	 * <br>
	 * 対象ワークフロー番号のワークフロー情報
	 * 及びワークフローコメント情報を、削除する。<br>
	 * <br>
	 * @param workflow ワークフロー番号
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void delete(long workflow) throws MospException;
	
	/**
	 * 承認解除申請を行う。<br>
	 * @param dto 対象DTO
	 * @param workflowComment ワークフローコメント
	 * @param ckbWithdrawn 取下げ希望チェックボックス
	 * @return ワークフロー情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	WorkflowDtoInterface cancelAttendanceAppli(WorkflowDtoInterface dto, String workflowComment, String ckbWithdrawn)
			throws MospException;
	
}
