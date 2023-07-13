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
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.base.TimeAction;
import jp.mosp.time.bean.AttendanceCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.TotalTimeCorrectionReferenceBeanInterface;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeCorrectionDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.input.vo.AttendanceHistoryVo;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 選択したレコードの勤怠申請承認情報の修正履歴情報を取得し、表示する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SELECT_SHOW}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_TRANSFER}
 * </li><li>
 * {@link #CMD_SELECT_SHOW_TOTAL}
 * </li></ul>
 */
public class AttendanceHistoryAction extends TimeAction {
	
	/**
	 * 勤怠情報修正履歴の選択表示コマンド。<br>
	 * <br>
	 * 勤怠情報テーブルより取得した勤怠情報を勤怠情報欄に、その修正履歴を修正履歴欄にそれぞれ表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW			= "TM1911";
	
	/**
	 * 勤怠情報修正履歴の再表示コマンド<br>
	 * <br>
	 * この画面よりも奥の階層にあたる画面からこの画面に再び遷移した際に編集内容を反映させる。<br>
	 */
	public static final String	CMD_RE_SHOW				= "TM1912";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 現在表示している画面から、ワークフロー番号をMosP処理情報に設定し、画面遷移する。<br>
	 * <br>
	 */
	public static final String	CMD_TRANSFER			= "TM1916";
	
	/**
	 * 勤怠集計情報修正履歴の選択表示コマンド<br>
	 * <br>
	 * 勤怠集計情報修正履歴を修正履歴欄にそれぞれ表示する。<br>
	 */
	public static final String	CMD_SELECT_SHOW_TOTAL	= "TM1921";
	
	
	/**
	 * {@link TimeAction#TimeAction()}を実行する。<br>
	 */
	public AttendanceHistoryAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new AttendanceHistoryVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SELECT_SHOW)) {
			// 選択表示
			prepareVo(false, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示
			prepareVo(true, false);
			search();
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 遷移
			prepareVo(true, false);
			transfer();
		} else if (mospParams.getCommand().equals(CMD_SELECT_SHOW_TOTAL)) {
			// 選択表示(勤怠集計修正情報)
			prepareVo(false, false);
			selectTotal();
		}
	}
	
	/**
	 * リクエストされた個人ID及び対象日に対して、
	 * 勤怠情報及び勤怠修正履歴を取得し、VOに設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void select() throws MospException {
		// リクエストから個人ID及び対象日を取得
		String personalId = getTargetPersonalId();
		Date targetDate = getTargetDate();
		// 人事情報をVOに設定
		setEmployeeInfo(personalId, targetDate);
		// 勤怠情報及び勤怠修正履歴を取得しVOに設定
		search();
	}
	
	/**
	 * リクエストされた個人ID及び対象日に対して、
	 * 勤怠集計修正情報を取得し、VOに設定する。<br>
	 * @throws MospException 例外発生時
	 */
	protected void selectTotal() throws MospException {
		// リクエストから個人ID及び対象日を取得
		String personalId = getTargetPersonalId();
		Date targetDate = getTargetDate();
		// 人事情報をVOに設定
		setEmployeeInfo(personalId, targetDate);
		// 勤怠情報及び勤怠修正履歴を取得しVOに設定
		getAttendanceTotal();
	}
	
	/**
	 * 勤怠情報及び勤怠修正履歴を取得し、VOに設定する。
	 * @throws MospException 情報の取得に失敗した場合
	 */
	protected void search() throws MospException {
		// 初期値設定
		setDefaultValues();
		// 勤怠情報を取得しVOに設定
		getAttendanceInfo();
		// 勤怠修正履歴を取得しVOに設定
		getAttendanceHistory();
	}
	
	/**
	 * 初期値を設定する。
	 */
	protected void setDefaultValues() {
		// VO取得
		AttendanceHistoryVo vo = (AttendanceHistoryVo)mospParams.getVo();
		vo.setLblAttendanceDate("");
		vo.setAttendanceDate("");
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
		vo.setLblAttendanceState("");
		vo.setLblAttendanceCorrection("");
		vo.setWorkflow(0);
		vo.setLblAttendanceRemark("");
	}
	
	/**
	 * 勤怠情報を取得しVOに設定する。<br>
	 * @throws MospException 勤怠情報の取得に失敗した場合
	 */
	protected void getAttendanceInfo() throws MospException {
		// VO準備
		AttendanceHistoryVo vo = (AttendanceHistoryVo)mospParams.getVo();
		// 対象個人ID及び対象日を取得
		Date targetDate = vo.getTargetDate();
		String personalId = vo.getPersonalId();
		// DTO準備
		AttendanceListDto dto = timeReference().attendanceList().getAttendanceListDto(personalId, targetDate);
		if (dto == null) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoItem(mospParams, TimeNamingUtility.attendanceRequest(mospParams));
			return;
		}
		WorkflowDtoInterface workflowDto = reference().workflow().getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoItem(mospParams, TimeNamingUtility.attendanceRequest(mospParams));
			return;
		}
		vo.setLblAttendanceDate(DateUtility.getStringDateAndDay(dto.getWorkDate()));
		vo.setAttendanceDate(DateUtility.getStringDate(dto.getWorkDate()));
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
		vo.setLblAttendanceState(
				getStatusStageValueView(workflowDto.getWorkflowStatus(), workflowDto.getWorkflowStage()));
		vo.setLblAttendanceCorrection(dto.getCorrectionInfo());
		vo.setWorkflow(dto.getWorkflow());
		vo.setLblAttendanceRemark(MospUtility.concat(dto.getRemark(), dto.getTimeComment()));
	}
	
	/**
	 * 勤怠修正履歴を取得しVOに設定する。<br>
	 * @throws MospException 勤怠修正情報の取得に失敗した場合
	 */
	public void getAttendanceHistory() throws MospException {
		// VO準備
		AttendanceHistoryVo vo = (AttendanceHistoryVo)mospParams.getVo();
		// 対象個人ID及び対象日を取得
		Date targetDate = vo.getTargetDate();
		String personalId = vo.getPersonalId();
		// 一覧表示項目設定
		AttendanceCorrectionReferenceBeanInterface correction = timeReference().attendanceCorrection();
		List<AttendanceCorrectionDtoInterface> list = correction.getAttendanceCorrectionHistory(personalId, targetDate,
				1);
		// 該当社員欄設定
		String[] aryLblNumber = new String[list.size()];
		String[] aryLblDate = new String[list.size()];
		String[] aryLblEmployee = new String[list.size()];
		String[] aryLblType = new String[list.size()];
		String[] aryLblBefore = new String[list.size()];
		String[] aryLblAfter = new String[list.size()];
		String[] aryLblComment = new String[list.size()];
		// データ作成
		int i = 0;
		for (AttendanceCorrectionDtoInterface dto : list) {
			// リストから情報を取得
			// 配列に情報を設定
			aryLblNumber[i] = String.valueOf(i + 1);
			// 修正日時
			aryLblDate[i] = DateUtility.getStringDateAndDayAndTime(dto.getCorrectionDate());
			// 修正者
			aryLblEmployee[i] = getCorrector(dto);
			// 修正箇所
			aryLblType[i] = correction.getHistoryAttendanceMoreName(dto.getCorrectionType());
			// 修正前
			aryLblBefore[i] = correction.getCorrectionValue(dto.getCorrectionType(), dto.getCorrectionBefore(),
					targetDate);
			// 修正後
			aryLblAfter[i] = correction.getCorrectionValue(dto.getCorrectionType(), dto.getCorrectionAfter(),
					targetDate);
			// コメント
			aryLblComment[i] = dto.getCorrectionReason();
			i++;
		}
		vo.setAryLblCorrectionNumber(aryLblNumber);
		vo.setAryLblCorrectionDate(aryLblDate);
		vo.setAryLblCorrectionEmployee(aryLblEmployee);
		vo.setAryLblCorrectionType(aryLblType);
		vo.setAryLblCorrectionBefore(aryLblBefore);
		vo.setAryLblCorrectionAfter(aryLblAfter);
		vo.setAryLblCorrectionComment(aryLblComment);
	}
	
	/**
	 * @throws MospException 例外発生時
	 */
	public void getAttendanceTotal() throws MospException {
		// VO準備
		AttendanceHistoryVo vo = (AttendanceHistoryVo)mospParams.getVo();
		TotalTimeCorrectionReferenceBeanInterface totalTime = timeReference().totalTimeCorrection();
		AttendanceCorrectionReferenceBeanInterface attendance = timeReference().attendanceCorrection();
		// 対象個人ID及び対象日を取得
		Date targetDate = vo.getTargetDate();
		String personalId = vo.getPersonalId();
		// 一覧表示項目設定
		List<TotalTimeCorrectionDtoInterface> list = timeReference().totalTimeCorrection()
			.getTotalTimeCorrectionHistory(personalId, DateUtility.getYear(targetDate),
					DateUtility.getMonth(targetDate));
		// 該当社員欄設定
		String[] aryLblNumber = new String[list.size()];
		String[] aryLblDate = new String[list.size()];
		String[] aryLblEmployee = new String[list.size()];
		String[] aryLblType = new String[list.size()];
		String[] aryLblBefore = new String[list.size()];
		String[] aryLblAfter = new String[list.size()];
		String[] aryLblComment = new String[list.size()];
		// データ作成
		int i = 0;
		for (TotalTimeCorrectionDtoInterface dto : list) {
			aryLblNumber[i] = String.valueOf(i + 1);
			aryLblDate[i] = DateUtility.getStringDateAndDayAndTime(dto.getCorrectionDate());
			aryLblEmployee[i] = getCorrector(dto);
			aryLblType[i] = attendance.getHistoryAttendanceAggregateName(dto);
			aryLblBefore[i] = totalTime.getCorrectionValue(dto.getCorrectionType(), dto.getCorrectionBefore());
			aryLblAfter[i] = totalTime.getCorrectionValue(dto.getCorrectionType(), dto.getCorrectionAfter());
			aryLblComment[i] = dto.getCorrectionReason();
			i++;
		}
		vo.setAryLblCorrectionNumber(aryLblNumber);
		vo.setAryLblCorrectionDate(aryLblDate);
		vo.setAryLblCorrectionEmployee(aryLblEmployee);
		vo.setAryLblCorrectionType(aryLblType);
		vo.setAryLblCorrectionBefore(aryLblBefore);
		vo.setAryLblCorrectionAfter(aryLblAfter);
		vo.setAryLblCorrectionComment(aryLblComment);
	}
	
	/**
	 * 勤務形態略称を取得する。<br>
	 * @param dto 対象DTO
	 * @return 勤務形態略称
	 * @throws MospException 例外発生時
	 */
	protected String getWorkTypeAbbr(AttendanceDtoInterface dto) throws MospException {
		DifferenceRequestReferenceBeanInterface differenceRequestReference = timeReference().differenceRequest();
		DifferenceRequestDtoInterface differenceRequestDto = differenceRequestReference
			.findForKeyOnWorkflow(dto.getPersonalId(), dto.getWorkDate());
		if (differenceRequestDto != null) {
			WorkflowDtoInterface workflowDto = reference().workflow()
				.getLatestWorkflowInfo(differenceRequestDto.getWorkflow());
			if (workflowDto != null && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				return differenceRequestReference.getDifferenceAbbr(differenceRequestDto.getDifferenceType());
			}
		}
		return timeReference().workType().getWorkTypeAbbr(dto.getWorkTypeCode(), dto.getWorkDate());
	}
	
	/**
	 * 修正者を取得する。<br>
	 * @param dto 対象DTO
	 * @return 修正者
	 * @throws MospException 例外発生時
	 */
	protected String getCorrector(AttendanceCorrectionDtoInterface dto) throws MospException {
		if (dto == null) {
			return PfNameUtility.hyphen(mospParams);
		}
		return getCorrector(dto.getPersonalId(), dto.getCorrectionPersonalId(), dto.getCorrectionDate());
	}
	
	/**
	 * 修正者を取得する。<br>
	 * @param dto 対象DTO
	 * @return 修正者
	 * @throws MospException 例外発生時
	 */
	protected String getCorrector(TotalTimeCorrectionDtoInterface dto) throws MospException {
		if (dto == null) {
			return PfNameUtility.hyphen(mospParams);
		}
		return getCorrector(dto.getPersonalId(), dto.getCorrectionPersonalId(), dto.getCorrectionDate());
	}
	
	private String getCorrector(String personalId, String correctionPersonalId, Date correctionDate)
			throws MospException {
		if (personalId.equals(correctionPersonalId)) {
			// 本人の場合
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("FrontWithCornerParentheses"));
			sb.append(mospParams.getName("Myself"));
			sb.append(mospParams.getName("BackWithCornerParentheses"));
			return sb.toString();
		}
		HumanDtoInterface humanDto = reference().human().getHumanInfo(correctionPersonalId, correctionDate);
		if (humanDto == null) {
			return PfNameUtility.hyphen(mospParams);
		}
		return getLastFirstName(humanDto.getLastName(), humanDto.getFirstName());
	}
	
	/**
	 * ワークフロー番号をMosP処理情報に設定し、
	 * 連続実行コマンドを設定する。<br>
	 */
	protected void transfer() {
		// VO準備
		AttendanceHistoryVo vo = (AttendanceHistoryVo)mospParams.getVo();
		// MosP処理情報に対象ワークフローを設定
		setTargetWorkflow(vo.getWorkflow());
		// 勤怠詳細画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(ApprovalHistoryAction.CMD_TIME_APPROVAL_HISTORY_SELECT_SHOW);
	}
	
}
