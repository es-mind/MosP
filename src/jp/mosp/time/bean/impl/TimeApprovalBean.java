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
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceListRegistBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.DifferenceRequestRegistBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.OvertimeRequestRegistBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠関連申請承認クラス。<br>
 * 勤怠関連の申請に対して承認、差戻などの機能を提供する。<br>
 */
public class TimeApprovalBean extends TimeBean implements TimeApprovalBeanInterface {
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	protected ApprovalInfoReferenceBeanInterface		approvalInfo;
	
	/**
	 * ワークフロー参照クラス。<br>
	 */
	protected WorkflowReferenceBeanInterface			workflowRefer;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	protected WorkflowRegistBeanInterface				workflowRegist;
	
	/**
	 * 勤怠一覧登録クラス。<br>
	 */
	protected AttendanceListRegistBeanInterface			attendanceListRegist;
	
	/**
	 * 勤怠データ登録クラス。<br>
	 */
	private AttendanceRegistBeanInterface				attendanceRegist;
	
	/**
	 * 時差出勤申請登録クラス。
	 */
	private DifferenceRequestRegistBeanInterface		differenceRequestRegist;
	
	/**
	 * 勤務形態変更申請登録クラス。
	 */
	private WorkTypeChangeRequestRegistBeanInterface	workTypeChangeRequestRegist;
	
	/**
	 * 代休申請登録クラス。
	 */
	private SubHolidayRequestRegistBeanInterface		subHolidayRequestRegist;
	
	/**
	 * 休日出勤申請登録クラス。
	 */
	private WorkOnHolidayRequestRegistBeanInterface		workOnHolidayRequestRegist;
	
	/**
	 * 休暇申請登録クラス。
	 */
	private HolidayRequestRegistBeanInterface			holidayRequestRegist;
	
	/**
	 * 残業申請登録クラス。
	 */
	private OvertimeRequestRegistBeanInterface			overtimeRequestRegist;
	
	/**
	 * 勤怠トランザクション登録クラス。
	 */
	protected AttendanceTransactionRegistBeanInterface	attendanceTransactionRegist;
	
	/**
	 * 振替休日データ参照クラス。
	 */
	protected SubstituteReferenceBeanInterface			substituteReference;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface					timeMaster;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public TimeApprovalBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		approvalInfo = createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
		workflowRefer = createBeanInstance(WorkflowReferenceBeanInterface.class);
		workflowRegist = createBeanInstance(WorkflowRegistBeanInterface.class);
		attendanceListRegist = createBeanInstance(AttendanceListRegistBeanInterface.class);
		attendanceRegist = createBeanInstance(AttendanceRegistBeanInterface.class);
		differenceRequestRegist = createBeanInstance(DifferenceRequestRegistBeanInterface.class);
		workTypeChangeRequestRegist = createBeanInstance(WorkTypeChangeRequestRegistBeanInterface.class);
		subHolidayRequestRegist = createBeanInstance(SubHolidayRequestRegistBeanInterface.class);
		overtimeRequestRegist = createBeanInstance(OvertimeRequestRegistBeanInterface.class);
		holidayRequestRegist = createBeanInstance(HolidayRequestRegistBeanInterface.class);
		workOnHolidayRequestRegist = createBeanInstance(WorkOnHolidayRequestRegistBeanInterface.class);
		attendanceTransactionRegist = createBeanInstance(AttendanceTransactionRegistBeanInterface.class);
		substituteReference = createBeanInstance(SubstituteReferenceBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		// 勤怠関連マスタ参照処理を設定
		attendanceRegist.setTimeMaster(timeMaster);
		attendanceTransactionRegist.setTimeMaster(timeMaster);
		overtimeRequestRegist.setTimeMaster(timeMaster);
		holidayRequestRegist.setTimeMaster(timeMaster);
		subHolidayRequestRegist.setTimeMaster(timeMaster);
		workOnHolidayRequestRegist.setTimeMaster(timeMaster);
	}
	
	@Override
	public void approve(long workflow, String workflowComment) throws MospException {
		// 申請情報の取得
		BaseDtoInterface requestDto = approvalInfo.getRequestDtoForWorkflow(workflow, true);
		// ワークフロー情報の取得
		WorkflowDtoInterface dto = workflowRefer.getLatestWorkflowInfo(workflow);
		// 承認時の確認処理
		checkApproval(requestDto);
		// ワークフロー情報の排他確認
		checkExclusive(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 承認処理
		workflowRegist.approve(dto, PlatformConst.WORKFLOW_TYPE_TIME, workflowComment);
		if (PlatformConst.CODE_STATUS_COMPLETE.equals(dto.getWorkflowStatus())) {
			// 承認済の場合は勤怠を下書し直す
			reDraftAttendance(dto.getPersonalId(), dto.getWorkflowDate(), requestDto);
		}
		// 勤怠トランザクション登録
		registAttendanceTransaction(dto.getPersonalId(), dto.getWorkflowDate(), requestDto);
	}
	
	@Override
	public void revert(long workflow, String workflowComment) throws MospException {
		// DTOの準備
		WorkflowDtoInterface dto = workflowRefer.getLatestWorkflowInfo(workflow);
		// ワークフロー情報の排他確認
		checkExclusive(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 差戻処理
		workflowRegist.revert(dto, PlatformConst.WORKFLOW_TYPE_TIME, workflowComment);
	}
	
	@Override
	public void cancelRevert(long workflow, String workflowComment) throws MospException {
		// 申請情報の取得
		BaseDtoInterface requestDto = approvalInfo.getRequestDtoForWorkflow(workflow, true);
		// DTOの準備
		WorkflowDtoInterface dto = workflowRefer.getLatestWorkflowInfo(workflow);
		// 承認時の確認処理
		checkApproval(requestDto);
		// ワークフロー情報の排他確認
		checkExclusive(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 差戻処理
		workflowRegist.cancelRevert(dto, workflowComment);
		if (PlatformConst.CODE_STATUS_COMPLETE.equals(dto.getWorkflowStatus())) {
			// 承認済の場合は勤怠を下書し直す
			reDraft(dto.getPersonalId(), dto.getWorkflowDate(), false, false, false);
		}
	}
	
	@Override
	public void approve(long[] aryWorkflow, String workflowComment) throws MospException {
		// ワークフロー毎に処理
		for (long workflow : aryWorkflow) {
			// 承認処理
			approve(workflow, workflowComment);
		}
	}
	
	@Override
	public void cancel(long workflow, String workflowComment) throws MospException {
		// 申請情報の取得
		BaseDtoInterface requestDto = approvalInfo.getRequestDtoForWorkflow(workflow, true);
		// 承認解除時の確認処理
		checkCancel(requestDto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// DTOの準備
		WorkflowDtoInterface dto = workflowRefer.getLatestWorkflowInfo(workflow);
		// 承認解除処理
		workflowRegist.cancel(dto, PlatformConst.WORKFLOW_TYPE_TIME, workflowComment);
		if (TimeConst.CODE_FUNCTION_WORK_HOLIDAY.equals(dto.getFunctionCode())) {
			// 振出・休出申請の場合は勤怠を削除する
			attendanceRegist.delete(dto.getPersonalId(), dto.getWorkflowDate());
		} else if (TimeConst.CODE_FUNCTION_OVER_WORK.equals(dto.getFunctionCode())
				|| TimeConst.CODE_FUNCTION_VACATION.equals(dto.getFunctionCode())
				|| TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY.equals(dto.getFunctionCode())
				|| TimeConst.CODE_FUNCTION_DIFFERENCE.equals(dto.getFunctionCode())) {
			// 残業申請・休暇申請・代休申請・時差出勤申請の場合は勤怠を下書し直す
			reDraft(dto.getPersonalId(), dto.getWorkflowDate(), false, false, false);
		} else if (TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE.equals(dto.getFunctionCode())) {
			// 勤務形態変更申請の場合は勤怠を下書し直す
			reDraft(dto.getPersonalId(), dto.getWorkflowDate(), false, false, true);
		}
		// 勤怠トランザクション登録
		registAttendanceTransaction(dto.getPersonalId(), dto.getWorkflowDate(), requestDto);
	}
	
	@Override
	public void cancelApprove(long workflow, String workflowComment) throws MospException {
		// 申請情報の取得
		BaseDtoInterface requestDto = approvalInfo.getRequestDtoForWorkflow(workflow, true);
		// 承認解除時の確認処理
		checkCancelApproval(requestDto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// DTOの準備
		WorkflowDtoInterface dto = workflowRefer.getLatestWorkflowInfo(workflow);
		if (dto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY)) {
			// 解除申請(取下希望)の場合
			if (TimeConst.CODE_FUNCTION_WORK_MANGE.equals(dto.getFunctionCode())) {
				// 勤怠申請の場合は勤怠を削除する
				attendanceRegist.delete(dto.getPersonalId(), dto.getWorkflowDate());
			} else {
				// 解除承認・取下処理
				workflowRegist.cancelWithdrawnApprove(dto, workflowComment);
			}
		} else {
			// 解除承認処理
			workflowRegist.cancelApprove(dto, workflowComment);
		}
		if (TimeConst.CODE_FUNCTION_WORK_HOLIDAY.equals(dto.getFunctionCode())) {
			// 振出・休出申請の場合は勤怠を削除する
			attendanceRegist.delete(dto.getPersonalId(), dto.getWorkflowDate());
		} else if (TimeConst.CODE_FUNCTION_OVER_WORK.equals(dto.getFunctionCode())
				|| TimeConst.CODE_FUNCTION_VACATION.equals(dto.getFunctionCode())
				|| TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY.equals(dto.getFunctionCode())
				|| TimeConst.CODE_FUNCTION_DIFFERENCE.equals(dto.getFunctionCode())) {
			// 残業申請・休暇申請・代休申請・時差出勤申請の場合は勤怠を下書し直す
			reDraft(dto.getPersonalId(), dto.getWorkflowDate(), false, false, false);
		} else if (TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE.equals(dto.getFunctionCode())) {
			// 勤務形態変更申請の場合は勤怠を下書し直す
			reDraft(dto.getPersonalId(), dto.getWorkflowDate(), false, false, true);
		}
		// 勤怠トランザクション登録
		registAttendanceTransaction(dto.getPersonalId(), dto.getWorkflowDate(), requestDto);
	}
	
	@Override
	public void cancelApprove(long[] aryWorkflow, String workflowComment) throws MospException {
		// ワークフロー毎に処理
		for (long workflow : aryWorkflow) {
			// 承認処理
			cancelApprove(workflow, workflowComment);
		}
	}
	
	@Override
	public void checkCancelAppli(BaseDtoInterface dto) throws MospException {
		// 勤怠データ
		if (dto instanceof AttendanceDtoInterface) {
			attendanceRegist.checkCancelAppli((AttendanceDtoInterface)dto);
		}
		// 残業申請
		if (dto instanceof OvertimeRequestDtoInterface) {
			overtimeRequestRegist.checkCancelAppli((OvertimeRequestDtoInterface)dto);
		}
		// 休暇申請
		if (dto instanceof HolidayRequestDtoInterface) {
			holidayRequestRegist.checkCancelAppli((HolidayRequestDtoInterface)dto);
		}
		// 休日出勤
		if (dto instanceof WorkOnHolidayRequestDtoInterface) {
			workOnHolidayRequestRegist.checkCancelAppli((WorkOnHolidayRequestDtoInterface)dto);
		}
		// 代休申請
		if (dto instanceof SubHolidayRequestDtoInterface) {
			subHolidayRequestRegist.checkCancelAppli((SubHolidayRequestDtoInterface)dto);
		}
		// 勤務形態変更申請
		if (dto instanceof WorkTypeChangeRequestDtoInterface) {
			workTypeChangeRequestRegist.checkCancelAppli((WorkTypeChangeRequestDtoInterface)dto);
		}
		// 時差出勤
		if (dto instanceof DifferenceRequestDtoInterface) {
			differenceRequestRegist.checkCancelAppli((DifferenceRequestDtoInterface)dto);
		}
	}
	
	@Override
	public void checkApproval(BaseDtoInterface dto) throws MospException {
		// 勤怠データ
		if (dto instanceof AttendanceDtoInterface) {
			attendanceRegist.checkApproval((AttendanceDtoInterface)dto);
		}
		// 残業申請
		if (dto instanceof OvertimeRequestDtoInterface) {
			overtimeRequestRegist.checkApproval((OvertimeRequestDtoInterface)dto);
		}
		// 休暇申請
		if (dto instanceof HolidayRequestDtoInterface) {
			holidayRequestRegist.checkApproval((HolidayRequestDtoInterface)dto);
		}
		// 休日出勤
		if (dto instanceof WorkOnHolidayRequestDtoInterface) {
			workOnHolidayRequestRegist.checkApproval((WorkOnHolidayRequestDtoInterface)dto);
		}
		// 代休申請
		if (dto instanceof SubHolidayRequestDtoInterface) {
			subHolidayRequestRegist.checkApproval((SubHolidayRequestDtoInterface)dto);
		}
		// 勤務形態変更申請
		if (dto instanceof WorkTypeChangeRequestDtoInterface) {
			workTypeChangeRequestRegist.checkApproval((WorkTypeChangeRequestDtoInterface)dto);
		}
		// 時差出勤
		if (dto instanceof DifferenceRequestDtoInterface) {
			differenceRequestRegist.checkApproval((DifferenceRequestDtoInterface)dto);
		}
	}
	
	@Override
	public void checkCancel(BaseDtoInterface dto) throws MospException {
		// 勤怠データ
		if (dto instanceof AttendanceDtoInterface) {
			attendanceRegist.checkCancel((AttendanceDtoInterface)dto);
		}
		// 残業申請
		if (dto instanceof OvertimeRequestDtoInterface) {
			overtimeRequestRegist.checkCancel((OvertimeRequestDtoInterface)dto);
		}
		// 休暇申請
		if (dto instanceof HolidayRequestDtoInterface) {
			holidayRequestRegist.checkCancel((HolidayRequestDtoInterface)dto);
		}
		// 休日出勤
		if (dto instanceof WorkOnHolidayRequestDtoInterface) {
			workOnHolidayRequestRegist.checkCancel((WorkOnHolidayRequestDtoInterface)dto);
		}
		// 代休申請
		if (dto instanceof SubHolidayRequestDtoInterface) {
			subHolidayRequestRegist.checkCancel((SubHolidayRequestDtoInterface)dto);
		}
		// 勤務形態変更申請
		if (dto instanceof WorkTypeChangeRequestDtoInterface) {
			workTypeChangeRequestRegist.checkCancel((WorkTypeChangeRequestDtoInterface)dto);
		}
		// 時差出勤
		if (dto instanceof DifferenceRequestDtoInterface) {
			differenceRequestRegist.checkCancel((DifferenceRequestDtoInterface)dto);
		}
	}
	
	/**
	 * 解除承認時の確認処理を行う。<br>
	 * 申請カテゴリに応じた解除承認時の確認処理を行う。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void checkCancelApproval(BaseDtoInterface dto) throws MospException {
		// 勤怠データ
		if (dto instanceof AttendanceDtoInterface) {
			attendanceRegist.checkCancelApproval((AttendanceDtoInterface)dto);
		}
		// 残業申請
		if (dto instanceof OvertimeRequestDtoInterface) {
			overtimeRequestRegist.checkCancelApproval((OvertimeRequestDtoInterface)dto);
		}
		// 休暇申請
		if (dto instanceof HolidayRequestDtoInterface) {
			holidayRequestRegist.checkCancelApproval((HolidayRequestDtoInterface)dto);
		}
		// 休日出勤
		if (dto instanceof WorkOnHolidayRequestDtoInterface) {
			workOnHolidayRequestRegist.checkCancelApproval((WorkOnHolidayRequestDtoInterface)dto);
		}
		// 代休申請
		if (dto instanceof SubHolidayRequestDtoInterface) {
			subHolidayRequestRegist.checkCancelApproval((SubHolidayRequestDtoInterface)dto);
		}
		// 勤務形態変更申請
		if (dto instanceof WorkTypeChangeRequestDtoInterface) {
			workTypeChangeRequestRegist.checkCancelApproval((WorkTypeChangeRequestDtoInterface)dto);
		}
		// 時差出勤
		if (dto instanceof DifferenceRequestDtoInterface) {
			differenceRequestRegist.checkCancelApproval((DifferenceRequestDtoInterface)dto);
		}
	}
	
	@Override
	public void registAttendanceTransaction(String personalId, Date workDate, BaseDtoInterface requestDto)
			throws MospException {
		// 休暇申請である場合
		if (requestDto instanceof HolidayRequestDtoInterface) {
			HolidayRequestDtoInterface holidayRequestDto = MospUtility.castObject(requestDto);
			List<Date> list = TimeUtility.getDateList(holidayRequestDto.getRequestStartDate(),
					holidayRequestDto.getRequestEndDate());
			for (Date date : list) {
				attendanceTransactionRegist.regist(personalId, date);
			}
			return;
		}
		// 振出・休出申請の場合
		if (requestDto instanceof WorkOnHolidayRequestDtoInterface) {
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = MospUtility.castObject(requestDto);
			// 振出・休出申請が振替出勤である場合
			if (TimeRequestUtility.isWorkOnHolidaySubstitute(workOnHolidayRequestDto)) {
				// ワークフロー番号を取得
				long workflow = workOnHolidayRequestDto.getWorkflow();
				// 振替休日の勤怠トランザクション情報を登録
				for (SubstituteDtoInterface substitute : substituteReference.getSubstituteList(workflow)) {
					attendanceTransactionRegist.regist(substitute.getPersonalId(), substitute.getSubstituteDate());
				}
			}
		}
		// 休暇申請でない場合
		attendanceTransactionRegist.regist(personalId, workDate);
	}
	
	/**
	 * 勤怠を下書し直す。
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param requestDto 申請DTO
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void reDraftAttendance(String personalId, Date workDate, BaseDtoInterface requestDto)
			throws MospException {
		boolean deleteRest = false;
		boolean useWorkTypeChangeRequest = false;
		if (requestDto instanceof HolidayRequestDtoInterface) {
			// 休暇申請の場合
			HolidayRequestDtoInterface holidayRequestDto = (HolidayRequestDtoInterface)requestDto;
			int holidayRange = holidayRequestDto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休または午後休の場合
				deleteRest = true;
			}
		} else if (requestDto instanceof SubHolidayRequestDtoInterface) {
			// 代休申請の場合
			SubHolidayRequestDtoInterface subHolidayRequestDto = (SubHolidayRequestDtoInterface)requestDto;
			int holidayRange = subHolidayRequestDto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休または午後休の場合
				deleteRest = true;
			}
		}
		if (requestDto instanceof WorkTypeChangeRequestDtoInterface) {
			// 勤務形態変更申請の場合
			useWorkTypeChangeRequest = true;
		}
		reDraft(personalId, workDate, deleteRest, useWorkTypeChangeRequest, false);
	}
	
	@Override
	public void reDraft(String personalId, Date workDate, boolean deleteRest, boolean useWorkTypeChangeRequest,
			boolean useSchedule) throws MospException {
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, workDate);
		AttendanceDtoInterface dto = requestUtil.getDraftAttendance();
		if (dto == null) {
			return;
		}
		String actualStartTime = "";
		if (dto.getActualStartTime() != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(DateUtility.getStringHour(dto.getActualStartTime(), dto.getWorkDate()));
			sb.append(DateUtility.getStringMinute(dto.getActualStartTime()));
			actualStartTime = sb.toString();
		}
		String actualEndTime = "";
		if (dto.getActualEndTime() != null) {
			StringBuffer sb = new StringBuffer();
			sb.append(DateUtility.getStringHour(dto.getActualEndTime(), dto.getWorkDate()));
			sb.append(DateUtility.getStringMinute(dto.getActualEndTime()));
			actualEndTime = sb.toString();
		}
		// 勤怠データ下書
		attendanceListRegist.draft(dto.getPersonalId(), new String[]{ getStringDate(dto.getWorkDate()) },
				new String[]{ actualStartTime }, new String[]{ actualEndTime }, deleteRest, useWorkTypeChangeRequest,
				useSchedule);
	}
	
}
