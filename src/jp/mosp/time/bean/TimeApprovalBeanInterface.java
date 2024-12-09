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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;

/**
 * 勤怠関連申請承認インターフェース。<br>
 */
public interface TimeApprovalBeanInterface extends BaseBeanInterface {
	
	/**
	 * 承認処理を行う。<br>
	 * @param workflow ワークフロー番号
	 * @param workflowComment ワークフローコメント
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void approve(long workflow, String workflowComment) throws MospException;
	
	/**
	 * 一括承認処理を行う。<br>
	 * @param aryWorkflow ワークフロー番号配列
	 * @param workflowComment ワークフローコメント
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void approve(long[] aryWorkflow, String workflowComment) throws MospException;
	
	/**
	 * 差戻処理を行う。<br>
	 * @param workflow ワークフロー番号
	 * @param workflowComment ワークフローコメント
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void revert(long workflow, String workflowComment) throws MospException;
	
	/**
	 * 解除申請差戻処理を行う。<br>
	 * @param workflow ワークフロー番号
	 * @param workflowComment ワークフローコメント
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void cancelRevert(long workflow, String workflowComment) throws MospException;
	
	/**
	 * 承認解除処理を行う。<br>
	 * @param workflow ワークフロー番号
	 * @param workflowComment ワークフローコメント
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void cancel(long workflow, String workflowComment) throws MospException;
	
	/**
	 * 解除承認処理を行う。<br>
	 * @param workflow ワークフロー番号
	 * @param workflowComment ワークフローコメント
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void cancelApprove(long workflow, String workflowComment) throws MospException;
	
	/**
	 * 一括解除承認処理を行う。<br>
	 * @param aryWorkflow ワークフロー番号配列
	 * @param workflowComment ワークフローコメント
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void cancelApprove(long[] aryWorkflow, String workflowComment) throws MospException;
	
	/**
	 * 解除申請時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void checkCancelAppli(BaseDtoInterface dto) throws MospException;
	
	/**
	 * 承認時の確認処理を行う。<br>
	 * 申請カテゴリに応じた承認時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void checkApproval(BaseDtoInterface dto) throws MospException;
	
	/**
	 * 解除時の確認処理を行う。<br>
	 * 申請カテゴリに応じた解除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void checkCancel(BaseDtoInterface dto) throws MospException;
	
	/**
	 * 勤怠を下書し直す。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param deleteRest 休憩を削除する場合はtrue、しない場合はfalse
	 * @param useWorkTypeChangeRequest 勤務形態変更申請を利用する場合true、そうでない場合false
	 * @param useSchedule カレンダを利用する場合true、そうでない場合false
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void reDraft(String personalId, Date workDate, boolean deleteRest, boolean useWorkTypeChangeRequest,
			boolean useSchedule) throws MospException;
	
	/**
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param requestDto 申請DTO
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	void registAttendanceTransaction(String personalId, Date workDate, BaseDtoInterface requestDto)
			throws MospException;
	
}
