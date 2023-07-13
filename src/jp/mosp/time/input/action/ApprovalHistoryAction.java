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
package jp.mosp.time.input.action;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.input.vo.ApprovalHistoryVo;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 各申請区分毎に承認履歴情報を判別して表示する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_TIME_APPROVAL_HISTORY_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_TIME_APPROVAL_HISTORY_RE_SEARCH}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_OVERTIME_APPROVAL_HISTORY_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_LEAVE_APPROVAL_HISTORY_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_APPROVAL_HISTORY_HOLIDAY_WORK_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_APPROVAL_HISTORY_LIEU_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_DIFFERENCE_WORK_APPROVAL_HISTORY_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_WORK_TYPE_CHANGE_APPROVAL_HISTORY_SELECT_SHOW}
 * </li></ul>
 */
public class ApprovalHistoryAction extends TimeAction {
	
	/**
	 * 勤怠承認履歴選択表示コマンド。<br>
	 * <br>
	 * 勤怠一覧画面から遷移する際に実行される。画面上部には勤怠一覧画面遷移ボタンを表示させる。<br>
	 * 勤怠情報テーブルより取得した申請承認情報とその履歴を勤怠情報表示欄と承認履歴欄に表示する。<br>
	 */
	public static final String	CMD_TIME_APPROVAL_HISTORY_SELECT_SHOW				= "TM1811";
	
	/**
	 * 再表示コマンド。<br>
	 */
	public static final String	CMD_TIME_APPROVAL_HISTORY_RE_SEARCH					= "TM1813";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、対象個人ID及び対象日をMosP処理情報に設定し、画面遷移する。<br>
	 * <br>
	 */
	public static final String	CMD_TRANSFER										= "TM1816";
	
	/**
	 * 残業承認履歴選択表示コマンド。<br>
	 * <br>
	 * 残業申請画面から遷移する際に実行される。画面上部には残業申請画面遷移ボタンを表示させる。<br>
	 * 残業情報テーブルより取得した申請承認情報とその履歴を残業情報表示欄と承認履歴欄に表示する。<br>
	 */
	public static final String	CMD_OVERTIME_APPROVAL_HISTORY_SELECT_SHOW			= "TM1821";
	
	/**
	 * 休暇承認履歴選択表示コマンド。<br>
	 * <br>
	 * 休暇申請画面から遷移する際に実行される。画面上部には休暇申請画面遷移ボタンを表示させる。<br>
	 * 休暇情報テーブルより取得した申請承認情報とその履歴を休暇情報表示欄と承認履歴欄に表示する。<br>
	 */
	public static final String	CMD_LEAVE_APPROVAL_HISTORY_SELECT_SHOW				= "TM1831";
	
	/**
	 * 休日出勤承認履歴選択表示コマンド。<br>
	 * <br>
	 * 休日出勤申請画面から遷移する際に実行される。画面上部には休日出勤申請画面遷移ボタンを表示させる。<br>
	 * 休出情報テーブルより取得した申請承認情報とその履歴を休出情報表示欄と承認履歴欄に表示する。<br>
	 */
	public static final String	CMD_APPROVAL_HISTORY_HOLIDAY_WORK_SELECT_SHOW		= "TM1841";
	
	/**
	 * 代休承認履歴選択表示コマンド。<br>
	 * <br>
	 * 代休申請画面から遷移する際に実行される。画面上部には代休申請画面遷移ボタンを表示させる。<br>
	 * 代休情報テーブルより取得した申請承認情報とその履歴を代休情報表示欄と承認履歴欄に表示する。<br>
	 */
	public static final String	CMD_APPROVAL_HISTORY_LIEU_SELECT_SHOW				= "TM1851";
	
	/**
	 * 時差出勤承認履歴選択表示コマンド。<br>
	 * <br>
	 * 時差出勤申請画面から遷移する際に実行される。画面上部には時差出勤申請画面遷移ボタンを表示させる。<br>
	 * 時差出勤情報テーブルより取得した申請承認情報とその履歴を時差出勤情報表示欄と承認履歴欄に表示する。<br>
	 */
	public static final String	CMD_DIFFERENCE_WORK_APPROVAL_HISTORY_SELECT_SHOW	= "TM1861";
	
	/**
	 * 勤務形態変更承認履歴選択表示コマンド。<br>
	 * <br>
	 */
	public static final String	CMD_WORK_TYPE_CHANGE_APPROVAL_HISTORY_SELECT_SHOW	= "TM1871";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public ApprovalHistoryAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_TIME_APPROVAL_HISTORY_RE_SEARCH;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new ApprovalHistoryVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_TIME_APPROVAL_HISTORY_SELECT_SHOW)) {
			// 勤怠承認履歴選択表示
			prepareVo();
			timeApprovalHistorySelectShow(true);
		} else if (mospParams.getCommand().equals(CMD_TIME_APPROVAL_HISTORY_RE_SEARCH)) {
			// 勤怠承認履歴再表示
			prepareVo(true, false);
			timeApprovalHistorySelectShow(false);
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_OVERTIME_APPROVAL_HISTORY_SELECT_SHOW)) {
			// 残業承認履歴選択表示
			prepareVo();
			overtimeApprovalHistorySelectShow();
		} else if (mospParams.getCommand().equals(CMD_LEAVE_APPROVAL_HISTORY_SELECT_SHOW)) {
			// 休暇承認履歴選択表示
			prepareVo();
			leaveApprovalHistorySelectShow();
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_HISTORY_HOLIDAY_WORK_SELECT_SHOW)) {
			// 振出・休出承認履歴選択表示
			prepareVo();
			approvalHistoryHolidayWorkSelectShow();
		} else if (mospParams.getCommand().equals(CMD_APPROVAL_HISTORY_LIEU_SELECT_SHOW)) {
			// 代休承認履歴選択表示
			prepareVo();
			approvalHistoryLieuSelectShow();
		} else if (mospParams.getCommand().equals(CMD_DIFFERENCE_WORK_APPROVAL_HISTORY_SELECT_SHOW)) {
			// 時差出勤承認履歴選択表示
			prepareVo();
			differenceWorkApprovalHistorySelectShow();
		} else if (mospParams.getCommand().equals(CMD_WORK_TYPE_CHANGE_APPROVAL_HISTORY_SELECT_SHOW)) {
			// 勤務形態変更承認履歴選択表示
			prepareVo();
			workTypeChangeApprovalHistorySelectShow();
		}
	}
	
	/**
	 * 初期値を設定する。
	 */
	protected void setDefaultValues() {
		// VO取得
		ApprovalHistoryVo vo = (ApprovalHistoryVo)mospParams.getVo();
		vo.setLblAttendanceDate("");
		vo.setLblAttendanceWorkType("");
		vo.setLblAttendanceStartDate("");
		vo.setLblAttendanceEndDate("");
		vo.setLblAttendanceWorkTime("");
		vo.setLblAttendanceRestTime("");
		vo.setLblAttendancePrivateTime("");
		vo.setLblAttendanceLateTime("");
		vo.setLblAttendanceLeaveEarly("");
		vo.setLblAttendanceLateLeaveEarly("");
		vo.setLblAttendanceOverTimeIn("");
		vo.setLblAttendanceOverTimeOut("");
		vo.setLblAttendanceWorkOnHoliday("");
		vo.setLblAttendanceLateNight("");
		vo.setLblAttendanceRemark("");
		vo.setLblAttendanceCorrection("");
		vo.setLblAttendanceState("");
		vo.setLblHolidayType2("");
		vo.setLblApprovalState(new String[0]);
		vo.setLblApprovalResult(new String[0]);
		vo.setLblApprovalApprover(new String[0]);
		vo.setLblApprovalComment(new String[0]);
		vo.setLblApprovalDate(new String[0]);
	}
	
	/**
	 * 勤怠情報及び勤怠承認履歴を取得し、VOに設定する。<br>
	 * @param needWorkflow ワークフロー番号要否(true：リクエストから取得、false：VOから取得)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public void timeApprovalHistorySelectShow(boolean needWorkflow) throws MospException {
		// 初期値設定
		setDefaultValues();
		// 承認履歴を取得しVOに設定
		setApprovalHistory(needWorkflow);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// VO準備
		ApprovalHistoryVo vo = (ApprovalHistoryVo)mospParams.getVo();
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(vo.getWorkflow());
		// 存在確認
		checkSelectedDataExist(workflowDto);
		// 人事情報をVOに設定
		setEmployeeInfo(workflowDto.getPersonalId(), workflowDto.getWorkflowDate());
		// ワークフロー状態をVOに設定
		vo.setLblAttendanceState(
				getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		// 勤怠情報を取得
		AttendanceListDto dto = timeReference().attendanceList().getAttendanceListDto(workflowDto.getPersonalId(),
				workflowDto.getWorkflowDate());
		// 存在確認
		checkSelectedDataExist(dto);
		// 勤怠情報をVOに設定
		vo.setLblAttendanceDate(DateUtility.getStringDateAndDay(dto.getWorkDate()));
		vo.setLblAttendanceWorkType(dto.getWorkTypeAbbr());
		vo.setLblAttendanceStartDate(dto.getStartTimeString());
		vo.setLblAttendanceEndDate(dto.getEndTimeString());
		vo.setLblAttendanceWorkTime(dto.getWorkTimeString());
		vo.setLblAttendanceRestTime(dto.getRestTimeString());
		vo.setLblAttendancePrivateTime(dto.getPrivateTimeString());
		vo.setLblAttendanceLateTime(dto.getLateTimeString());
		vo.setLblAttendanceLeaveEarly(dto.getLeaveEarlyTimeString());
		vo.setLblAttendanceLateLeaveEarly(dto.getLateLeaveEarlyTimeString());
		vo.setLblAttendanceOverTimeIn(dto.getOvertimeInString());
		vo.setLblAttendanceOverTimeOut(dto.getOvertimeOutString());
		vo.setLblAttendanceWorkOnHoliday(dto.getHolidayWorkTimeString());
		vo.setLblAttendanceLateNight(dto.getLateNightTimeString());
		vo.setLblAttendanceRemark(MospUtility.concat(dto.getRemark(), dto.getTimeComment()));
		vo.setLblAttendanceCorrection(dto.getCorrectionInfo());
	}
	
	/**
	 * 残業承認履歴選択表示設定<br>
	 * @throws MospException 例外処理発生時
	 */
	public void overtimeApprovalHistorySelectShow() throws MospException {
		// 承認履歴を取得しVOに設定
		setApprovalHistory(true);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// VO準備
		ApprovalHistoryVo vo = (ApprovalHistoryVo)mospParams.getVo();
		// DTO準備
		OvertimeRequestDtoInterface dto = timeReference().overtimeRequest().findForWorkflow(vo.getWorkflow());
		// 存在確認
		checkSelectedDataExist(dto);
		// 2010/10/21 現在仕様では勤務回数は固定で「1」
		// 勤怠データ
		AttendanceDtoInterface attendanceDto = timeReference().attendance().findForKey(dto.getPersonalId(),
				dto.getRequestDate());
		WorkflowDtoInterface attendanceWorkflowDto = null;
		if (attendanceDto != null) {
			attendanceWorkflowDto = reference().workflow().getLatestWorkflowInfo(attendanceDto.getWorkflow());
		}
		// 残業承認履歴
		vo.setLblOverTimeDate(DateUtility.getStringDateAndDay(dto.getRequestDate()));
		vo.setLblOverTimeType(getOvertimeTypeName(dto.getOvertimeType()));
		vo.setLblOverTimeSchedule(getTimeColonFormat(dto.getRequestTime()));
		if (attendanceWorkflowDto != null
				&& attendanceWorkflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_COMPLETE)) {
			if (dto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_BEFORE) {
				vo.setLblOverTimeResultTime(getTimeColonFormat(attendanceDto.getOvertimeBefore()));
			} else {
				vo.setLblOverTimeResultTime(getTimeColonFormat(attendanceDto.getOvertimeAfter()));
			}
		} else {
			vo.setLblOverTimeResultTime(mospParams.getName("Hyphen"));
		}
		vo.setLblOverTimeRequestReason(dto.getRequestReason());
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(vo.getWorkflow());
		// ワークフロー状態をVOに設定
		vo.setLblOverTimeState(
				getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		vo.setLblOverTimeApprover(getWorkflowCommentDtoLastFirstName(workflowDto,
				reference().workflowComment().getLatestWorkflowCommentInfo(dto.getWorkflow())));
	}
	
	/**
	 * 休暇承認履歴選択表示
	 * @throws MospException 例外処理発生時
	 */
	public void leaveApprovalHistorySelectShow() throws MospException {
		// 初期値設定
		setDefaultValues();
		// 承認履歴を取得しVOに設定
		setApprovalHistory(true);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// VO準備
		ApprovalHistoryVo vo = (ApprovalHistoryVo)mospParams.getVo();
		// 初期化
		vo.setLblHolidayDate("");
		vo.setLblHolidayType1("");
		vo.setLblHolidayType2("");
		vo.setLblHolidayLength("");
		vo.setLblHolidayRequestReason("");
		vo.setLblHolidayState("");
		vo.setLblHolidayApprover("");
		// 休暇申請DTO取得
		HolidayRequestDtoInterface dto = timeReference().holidayRequest().findForWorkflow(vo.getWorkflow());
		// 存在確認
		checkSelectedDataExist(dto);
		// 休暇日の設定
		vo.setLblHolidayDate(DateUtility.getStringDateAndDay(dto.getRequestStartDate()) + mospParams.getName("Wave")
				+ DateUtility.getStringDateAndDay(dto.getRequestEndDate()));
		// 区分の設定
		vo.setLblHolidayType1(getHolidayType1Name(dto.getHolidayType1(), dto.getHolidayType2()));
		// 種別の設定
		vo.setLblHolidayType2(
				getHolidayType2Abbr(dto.getHolidayType1(), dto.getHolidayType2(), dto.getRequestStartDate()));
		// 範囲の設定
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			vo.setLblHolidayLength(mospParams.getName("AllTime"));
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前休の場合
			vo.setLblHolidayLength(mospParams.getName("AmRest"));
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後休の場合
			vo.setLblHolidayLength(mospParams.getName("PmRest"));
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 時休の場合
			vo.setLblHolidayLength(getTimeWaveFormat(dto.getStartTime(), dto.getEndTime(), dto.getRequestStartDate()));
		}
		// 理由の設定
		vo.setLblHolidayRequestReason(dto.getRequestReason());
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(vo.getWorkflow());
		// ワークフローの設定
		vo.setLblHolidayState(getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		vo.setLblHolidayApprover(getWorkflowCommentDtoLastFirstName(workflowDto,
				reference().workflowComment().getLatestWorkflowCommentInfo(dto.getWorkflow())));
	}
	
	/**
	 * 振出・休出承認履歴選択表示
	 * @throws MospException 例外処理発生時
	 */
	protected void approvalHistoryHolidayWorkSelectShow() throws MospException {
		// 承認履歴を取得しVOに設定
		setApprovalHistory(true);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// VO準備
		ApprovalHistoryVo vo = (ApprovalHistoryVo)mospParams.getVo();
		// DTO準備
		WorkOnHolidayRequestDtoInterface dto = timeReference().workOnHolidayRequest().findForWorkflow(vo.getWorkflow());
		// 存在確認
		checkSelectedDataExist(dto);
		List<SubstituteDtoInterface> substituteList = timeReference().substitute().getSubstituteList(dto.getWorkflow());
		// 休日出勤承認履歴
		vo.setLblWorkOnHolidayWorkDate(DateUtility.getStringDateAndDay(dto.getRequestDate()));
		vo.setLblWorkOnHolidayTime(getWorkOnHolidaySchedule(dto));
		vo.setLblWorkOnHolidayReason(dto.getRequestReason());
		vo.setLblWorkOnHolidayDate1("");
		vo.setLblWorkOnHolidayDate2("");
		int i = 0;
		for (SubstituteDtoInterface substituteDto : substituteList) {
			Date substituteDate = substituteDto.getSubstituteDate();
			int substituteRange = substituteDto.getSubstituteRange();
			if (i == 0) {
				vo.setLblWorkOnHolidayDate1(getHolidayDate(substituteDate, substituteRange));
			}
			i++;
		}
		// ワークフロー
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		vo.setLblWorkOnHolidayState(
				getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		vo.setLblWorkOnHolidayApprover(getWorkflowCommentDtoLastFirstName(workflowDto,
				reference().workflowComment().getLatestWorkflowCommentInfo(dto.getWorkflow())));
	}
	
	/**
	 * 代休承認履歴選択表示
	 * @throws MospException 例外処理発生時
	 */
	protected void approvalHistoryLieuSelectShow() throws MospException {
		// 承認履歴を取得しVOに設定
		setApprovalHistory(true);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		ApprovalHistoryVo vo = (ApprovalHistoryVo)mospParams.getVo();
		// DTO準備
		SubHolidayRequestDtoInterface dto = timeReference().subHolidayRequest().findForWorkflow(vo.getWorkflow());
		// 存在確認
		checkSelectedDataExist(dto);
		// 代休承認履歴
		StringBuilder sb = new StringBuilder();
		sb.append(DateUtility.getStringDateAndDay(dto.getRequestDate())).append(MospConst.STR_SB_SPACE)
			.append(TimeUtility.getHolidayRangeAbbr(mospParams, dto.getHolidayRange()));
		vo.setLblSubHolidayDate(sb.toString());
		sb = new StringBuilder();
		sb.append(setWorkDateSubHolidayType(dto.getWorkDateSubHolidayType())).append(MospConst.STR_SB_SPACE);
		sb.append(DateUtility.getStringDateAndDay(dto.getWorkDate()));
		vo.setLblSubHolidayWorkDate(sb.toString());
		// ワークフロー
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		vo.setLblSubHolidayState(
				getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		vo.setLblSubHolidayApprover(getWorkflowCommentDtoLastFirstName(workflowDto,
				reference().workflowComment().getLatestWorkflowCommentInfo(dto.getWorkflow())));
	}
	
	/**
	 * 時差出勤承認履歴選択表示
	 * @throws MospException 例外処理発生時
	 */
	protected void differenceWorkApprovalHistorySelectShow() throws MospException {
		// 承認履歴を取得しVOに設定
		setApprovalHistory(true);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// VO準備
		ApprovalHistoryVo vo = (ApprovalHistoryVo)mospParams.getVo();
		// DTO準備
		DifferenceRequestDtoInterface dto = timeReference().differenceRequest().findForWorkflow(vo.getWorkflow());
		// 存在確認
		checkSelectedDataExist(dto);
		// 時差出勤承認履歴
		vo.setLblDifferenceDate(DateUtility.getStringDateAndDay(dto.getRequestDate()));
		// 時差出勤区分
		vo.setLblDifferenceType(TimeRequestUtility.getDifferenceTypeAbbr(dto, mospParams));
		vo.setLblDifferenceTime(TransStringUtility.getHourColonMinuteTerm(mospParams, dto.getRequestStart(),
				dto.getRequestEnd(), dto.getRequestDate()));
		vo.setLblDifferenceReason(dto.getRequestReason());
		// ワークフロー
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		vo.setLblDifferenceState(
				getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		vo.setLblDifferenceApprover(getWorkflowCommentDtoLastFirstName(workflowDto,
				reference().workflowComment().getLatestWorkflowCommentInfo(dto.getWorkflow())));
	}
	
	/**
	 * 勤務形態変更承認履歴選択表示
	 * @throws MospException 例外処理発生時
	 */
	protected void workTypeChangeApprovalHistorySelectShow() throws MospException {
		// 承認履歴を取得しVOに設定
		setApprovalHistory(true);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// VO準備
		ApprovalHistoryVo vo = (ApprovalHistoryVo)mospParams.getVo();
		// DTO準備
		WorkTypeChangeRequestDtoInterface dto = timeReference().workTypeChangeRequest()
			.findForWorkflow(vo.getWorkflow());
		// 存在確認
		checkSelectedDataExist(dto);
		// 出勤日
		vo.setLblWorkTypeChangeDate(DateUtility.getStringDateAndDay(dto.getRequestDate()));
		// 変更後勤務形態
		vo.setLblWorkTypeChangeWorkType(
				timeReference().workType().getWorkTypeAbbrAndTime(dto.getWorkTypeCode(), dto.getRequestDate()));
		// 理由
		vo.setLblWorkTypeChangeReason(dto.getRequestReason());
		// ワークフロー
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		vo.setLblWorkTypeChangeState(
				getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		vo.setLblWorkTypeChangeApprover(getWorkflowCommentDtoLastFirstName(workflowDto,
				reference().workflowComment().getLatestWorkflowCommentInfo(dto.getWorkflow())));
	}
	
	/**
	 * 承認履歴を取得し、VOに設定する。
	 * @param needWorkflow ワークフロー番号要否(true：リクエストから取得、false：VOから取得)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void setApprovalHistory(boolean needWorkflow) throws MospException {
		// VO準備
		ApprovalHistoryVo vo = (ApprovalHistoryVo)mospParams.getVo();
		// ワークフロー番号要否確認
		if (needWorkflow) {
			// リクエストからワークフロー番号を取得
			long workflow = getTargetWorkflow();
			// ワークフロー番号をVOに設定
			vo.setWorkflow(workflow);
		}
		// ワークフロー番号でワークフロー情報を取得
		WorkflowDtoInterface dto = reference().workflow().getLatestWorkflowInfo(vo.getWorkflow());
		// 存在確認
		if (dto == null) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoItem(mospParams, PfNameUtility.applicationInfo(mospParams));
			return;
		}
		checkSelectedDataExist(dto);
		// 人事情報をVOに設定
		setEmployeeInfo(dto.getPersonalId(), dto.getWorkflowDate());
		// DTO準備
		List<WorkflowCommentDtoInterface> list = reference().workflowComment()
			.getWorkflowCommentHistory(dto.getWorkflow());
		// データ配列初期化
		String[] aryLblApprovalState = new String[list.size()];
		String[] aryLblApprovalResult = new String[list.size()];
		String[] aryLblApprovalApprover = new String[list.size()];
		String[] aryLblApprovalComment = new String[list.size()];
		String[] aryLblApproverDate = new String[list.size()];
		// データ作成
		int i = 0;
		for (WorkflowCommentDtoInterface comment : list) {
			// 配列に情報を設定
			aryLblApprovalState[i] = getStatusStageValueView(comment.getWorkflowStatus(), comment.getWorkflowStage());
			aryLblApprovalResult[i] = reference().workflowIntegrate().getWorkflowOperation(comment.getWorkflowStatus());
			aryLblApprovalApprover[i] = getEmployeeName(comment.getPersonalId(), comment.getWorkflowDate());
			aryLblApprovalComment[i] = comment.getWorkflowComment();
			aryLblApproverDate[i] = DateUtility.getStringDateAndDayAndTime(comment.getWorkflowDate());
			i++;
		}
		// データをVOに設定
		vo.setLblApprovalState(aryLblApprovalState);
		vo.setLblApprovalResult(aryLblApprovalResult);
		vo.setLblApprovalApprover(aryLblApprovalApprover);
		vo.setLblApprovalComment(aryLblApprovalComment);
		vo.setLblApprovalDate(aryLblApproverDate);
	}
	
	/**
	 * @param date 振替日
	 * @param range 振替範囲
	 * @return 一覧に表示する振替日の文字列
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected String getHolidayDate(Date date, int range) throws MospException {
		// 半日の振替休日が実装された場合は振替休日の範囲も表示する
		String base = DateUtility.getStringDateAndDay(date);
		// 半日振替の場合
		if (timeReference().workOnHolidayRequest().useHalfSubstitute()) {
			// 全休の場合
			if (range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				return base + MospConst.STR_SB_SPACE + getCodeName(range, TimeConst.CODE_SUBSTITUTE_WORK_RANGE);
			}
			// 午前・午後の場合
			if (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				return base + MospConst.STR_SB_SPACE + getCodeName(range, TimeConst.CODE_SUBSTITUTE_HOLIDAY_RANGE);
			}
		}
		return base;
	}
	
	/**
	 * 対象個人ID及び対象日をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO準備
		ApprovalHistoryVo vo = (ApprovalHistoryVo)mospParams.getVo();
		// MosP処理情報に対象個人IDを設定
		setTargetPersonalId(vo.getPersonalId());
		// MosP処理情報に対象日を設定
		setTargetDate(vo.getTargetDate());
		// 勤怠詳細画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(AttendanceHistoryAction.CMD_SELECT_SHOW);
	}
	
}
