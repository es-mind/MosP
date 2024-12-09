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
package jp.mosp.time.management.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.time.base.TimeVo;

/**
 * 未承認情報を格納する。
 */
public class ApprovalCardVo extends TimeVo {
	
	private static final long		serialVersionUID	= -4422335989484227811L;
	
	// ワークフローコメント
	private String					txtAttendanceComment;
	private String					txtOverTimeComment;
	private String					txtHolidayComment;
	private String					txtWorkOnHolidayComment;
	private String					txtCompensationComment;
	private String					txtWorkTypeChangeComment;
	private String					txtDifferenceComment;
	private String					txtCancelComment;
	
	// 日付情報
	private String					lblYear;
	private String					lblMonth;
	private String					lblDay;
	
	// 勤怠データ修正情報
	private String					lblCorrectionHistory;
	
	// 出退勤情報
	private String					lblStartTime;
	private String					lblEndTime;
	private String					lblWorkTime;
	private String					lblWorkType;
	private String					lblDirectWorkManage;
	private String					lblCheckWorkStart;
	private String					lblUnpaidShortTime;
	private String					lblTimeComment;
	private String					lblRemarks;
	
	// 勤怠休憩情報
	private String					lblRestTime;
	private String					lblNightRestTime;
	private String					lblRestTime1;
	private String					lblRestTime2;
	private String					lblRestTime3;
	private String					lblRestTime4;
	private String					lblRestTime5;
	private String					lblRestTime6;
	private String					lblPublicTime;
	private String					lblPublicTime1;
	private String					lblPublicTime2;
	private String					lblPrivateTime;
	private String					lblPrivateTime1;
	private String					lblPrivateTime2;
	
	// 勤怠遅刻早退情報
	private String					lblLateTime;
	private String					lblLateReason;
	private String					lblLateCertificate;
	private String					lblLateComment;
	private String					lblLeaveEarlyTime;
	private String					lblLeaveEarlyReason;
	private String					lblLeaveEarlyCertificate;
	private String					lblLeaveEarlyComment;
	
	// 勤怠残業情報
	private String					lblOvertime;
	private String					lblOverTimeIn;
	private String					lblOverTimeOut;
	private String					lblLateNightTime;
	private String					lblSpecificWorkTimeIn;
	private String					lblSpecificWorkTimeOver;
	private String					lblLegalWorkTime;
	private String					lblHolidayWorkTime;
	private String					lblDecreaseTime;
	
	// 勤怠情報承認状況
	private String					lblAttendanceState;
	private String					lblAttendanceApprover;
	private String					lblAttendanceComment;
	
	// 残業申請関連情報
	private String[]				lblOvertimeType;
	private String[]				lblOvertimeSchedule;
	private String[]				lblOvertimeResult;
	private String[]				lblOvertimeReason;
	private String[]				lblOvertimeState;
	private String[]				lblOvertimeApprover;
	private String[]				lblOvertimeComment;
	
	// 休暇申請関連情報
	private String[]				lblHolidayDate;
	private String[]				lblHolidayType;
	private String[]				lblHolidayLength;
	private String[]				lblHolidayTime;
	private String[]				lblHolidayReason;
	private String[]				lblHolidayState;
	private String[]				lblHolidayApprover;
	private String[]				lblHolidayComment;
	
	// 休出振休申請関連情報
	private String					lblWorkOnHolidayDate;
	private String					lblWorkOnHolidayTime;
	private String					lblWorkOnHolidayTransferDate;
	private String					lblWorkOnHolidayReason;
	private String					lblWorkOnHolidayState;
	private String					lblWorkOnHolidayApprover;
	private String					lblWorkOnHolidayComment;
	
	// 代休申請関連情報
	private String					lblSubHolidayDate;
	private String					lblSubHolidayWorkDate;
	private String					lblSubHolidayState;
	private String					lblSubHolidayApprover;
	private String					lblSubHolidayComment;
	
	// 勤務形態変更申請関連情報
	private String					lblWorkTypeChangeDate;
	private String					lblWorkTypeChangeBeforeWorkType;
	private String					lblWorkTypeChangeAfterWorkType;
	private String					lblWorkTypeChangeReason;
	private String					lblWorkTypeChangeState;
	private String					lblWorkTypeChangeComment;
	private String					lblWorkTypeChangeApprover;
	
	// 時差出勤申請関連情報
	private String					lblDifferenceDate;
	private String					lblDifferenceState;
	private String					lblDifferenceWorkType;
	private String					lblDifferenceReason;
	private String					lblDifferenceWorkTime;
	private String					lblDifferenceApprover;
	private String					lblDifferenceComment;
	
	// 承認解除申請関連情報
	private String					lblCancelState;
	private String					lblCancelApprover;
	
	/**
	 * 申請確認詳細フラグ。<br>
	 * true：申請確認詳細、false：未承認情報詳細。<br>
	 */
	private boolean					isConfirmation;
	
	/**
	 * 申請カテゴリフラグ(勤怠)。
	 */
	private boolean					isAttendance;
	
	/**
	 * 申請カテゴリフラグ(残業)。
	 */
	private boolean					isOvertime;
	
	/**
	 * 申請カテゴリフラグ(休暇)。
	 */
	private boolean					isHoliday;
	
	/**
	 * 申請カテゴリフラグ(休出)。
	 */
	private boolean					isWorkOnHoliday;
	
	/**
	 * 申請カテゴリフラグ(代休)。
	 */
	private boolean					isSubHoliday;
	
	/**
	 * 申請カテゴリフラグ(勤務形態変更申請)。
	 */
	private boolean					isWorkTypeChange;
	
	/**
	 * 申請カテゴリフラグ(時差出勤)。
	 */
	private boolean					isDifference;
	
	/**
	 * ワークフロー番号。<br>
	 * 承認、差戻、解除の対象となるワークフローを保持する。<br>
	 */
	private Long					workflow;
	
	/**
	 * 承認、差戻ボタン要否。<br>
	 */
	private boolean					needApproveButton;
	
	/**
	 * 承認解除ボタン要否。<br>
	 */
	private boolean					needCancelButton;
	
	/**
	 * 承認解除、差戻ボタン要否。<br>
	 */
	private boolean					needCancelApproveButton;
	
	/**
	 * 前ワークフロー。
	 */
	private long					prevWorkflow;
	
	/**
	 * 次ワークフロー。
	 */
	private long					nextWorkflow;
	
	/**
	 * 前コマンド。
	 */
	private String					prevCommand;
	
	/**
	 * 次コマンド。
	 */
	private String					nextCommand;
	
	/**
	 * 遷移DTO配列。<br>
	 */
	private BaseDtoInterface[]		rollArray;
	
	/**
	 * 勤怠設定追加JSPリスト。<br>
	 */
	private List<String>			addonJsps;
	
	/**
	 * 勤怠設定追加パラメータ群(キー：パラメータ名)。<br>
	 */
	private Map<String, String[]>	addonParameters;
	
	
	/**
	 * コンストラクタ
	 * {@link TimeVo#TimeVo()}を実行する。<br>
	 */
	public ApprovalCardVo() {
		super();
		addonJsps = new ArrayList<String>();
		addonParameters = new HashMap<String, String[]>();
	}
	
	/**
	 * @return txtAttendanceComment
	 */
	public String getTxtAttendanceComment() {
		return txtAttendanceComment;
	}
	
	/**
	 * @param txtAttendanceComment セットする txtAttendanceComment
	 */
	public void setTxtAttendanceComment(String txtAttendanceComment) {
		this.txtAttendanceComment = txtAttendanceComment;
	}
	
	/**
	 * @return txtOverTimeComment
	 */
	public String getTxtOverTimeComment() {
		return txtOverTimeComment;
	}
	
	/**
	 * @param txtOverTimeComment セットする txtOverTimeComment
	 */
	public void setTxtOverTimeComment(String txtOverTimeComment) {
		this.txtOverTimeComment = txtOverTimeComment;
	}
	
	/**
	 * @return txtHolidayComment
	 */
	public String getTxtHolidayComment() {
		return txtHolidayComment;
	}
	
	/**
	 * @param txtHolidayComment セットする txtHolidayComment
	 */
	public void setTxtHolidayComment(String txtHolidayComment) {
		this.txtHolidayComment = txtHolidayComment;
	}
	
	/**
	 * @return txtWorkOnHolidayComment
	 */
	public String getTxtWorkOnHolidayComment() {
		return txtWorkOnHolidayComment;
	}
	
	/**
	 * @param txtWorkOnHolidayComment セットする txtWorkOnHolidayComment
	 */
	public void setTxtWorkOnHolidayComment(String txtWorkOnHolidayComment) {
		this.txtWorkOnHolidayComment = txtWorkOnHolidayComment;
	}
	
	/**
	 * @return txtCompensationComment
	 */
	public String getTxtCompensationComment() {
		return txtCompensationComment;
	}
	
	/**
	 * @param txtCompensationComment セットする txtCompensationComment
	 */
	public void setTxtCompensationComment(String txtCompensationComment) {
		this.txtCompensationComment = txtCompensationComment;
	}
	
	/**
	 * @return txtWorkTypeChangeComment
	 */
	public String getTxtWorkTypeChangeComment() {
		return txtWorkTypeChangeComment;
	}
	
	/**
	 * @param txtWorkTypeChangeComment セットする txtWorkTypeChangeComment
	 */
	public void setTxtWorkTypeChangeComment(String txtWorkTypeChangeComment) {
		this.txtWorkTypeChangeComment = txtWorkTypeChangeComment;
	}
	
	/**
	 * @return lblStartTime
	 */
	public String getLblStartTime() {
		return lblStartTime;
	}
	
	/**
	 * @param lblStartTime セットする lblStartTime
	 */
	public void setLblStartTime(String lblStartTime) {
		this.lblStartTime = lblStartTime;
	}
	
	/**
	 * @return lblEndTime
	 */
	public String getLblEndTime() {
		return lblEndTime;
	}
	
	/**
	 * @param lblEndTime セットする lblEndTime
	 */
	public void setLblEndTime(String lblEndTime) {
		this.lblEndTime = lblEndTime;
	}
	
	/**
	 * @return lblWorkTime
	 */
	public String getLblWorkTime() {
		return lblWorkTime;
	}
	
	/**
	 * @param lblWorkTime セットする lblWorkTime
	 */
	public void setLblWorkTime(String lblWorkTime) {
		this.lblWorkTime = lblWorkTime;
	}
	
	/**
	 * @return lblWorkType
	 */
	public String getLblWorkType() {
		return lblWorkType;
	}
	
	/**
	 * @param lblWorkType セットする lblWorkType
	 */
	public void setLblWorkType(String lblWorkType) {
		this.lblWorkType = lblWorkType;
	}
	
	/**
	 * @return lblCorrectionHistory
	 */
	public String getLblCorrectionHistory() {
		return lblCorrectionHistory;
	}
	
	/**
	 * @param lblCorrectionHistory セットする lblCorrectionHistory
	 */
	public void setLblCorrectionHistory(String lblCorrectionHistory) {
		this.lblCorrectionHistory = lblCorrectionHistory;
	}
	
	/**
	 * @return lblRestTime
	 */
	public String getLblRestTime() {
		return lblRestTime;
	}
	
	/**
	 * @param lblRestTime セットする lblRestTime
	 */
	public void setLblRestTime(String lblRestTime) {
		this.lblRestTime = lblRestTime;
	}
	
	/**
	 * @return lblNightRestTime
	 */
	public String getLblNightRestTime() {
		return lblNightRestTime;
	}
	
	/**
	 * @param lblNightRestTime セットする lblNightRestTime
	 */
	public void setLblNightRestTime(String lblNightRestTime) {
		this.lblNightRestTime = lblNightRestTime;
	}
	
	/**
	 * @return lblRestTime1
	 */
	public String getLblRestTime1() {
		return lblRestTime1;
	}
	
	/**
	 * @param lblRestTime1 セットする lblRestTime1
	 */
	public void setLblRestTime1(String lblRestTime1) {
		this.lblRestTime1 = lblRestTime1;
	}
	
	/**
	 * @return lblRestTime2
	 */
	public String getLblRestTime2() {
		return lblRestTime2;
	}
	
	/**
	 * @param lblRestTime2 セットする lblRestTime2
	 */
	public void setLblRestTime2(String lblRestTime2) {
		this.lblRestTime2 = lblRestTime2;
	}
	
	/**
	 * @return lblRestTime3
	 */
	public String getLblRestTime3() {
		return lblRestTime3;
	}
	
	/**
	 * @param lblRestTime3 セットする lblRestTime3
	 */
	public void setLblRestTime3(String lblRestTime3) {
		this.lblRestTime3 = lblRestTime3;
	}
	
	/**
	 * @return lblRestTime4
	 */
	public String getLblRestTime4() {
		return lblRestTime4;
	}
	
	/**
	 * @param lblRestTime4 セットする lblRestTime4
	 */
	public void setLblRestTime4(String lblRestTime4) {
		this.lblRestTime4 = lblRestTime4;
	}
	
	/**
	 * @return lblRestTime5
	 */
	public String getLblRestTime5() {
		return lblRestTime5;
	}
	
	/**
	 * @param lblRestTime5 セットする lblRestTime5
	 */
	public void setLblRestTime5(String lblRestTime5) {
		this.lblRestTime5 = lblRestTime5;
	}
	
	/**
	 * @return lblRestTime6
	 */
	public String getLblRestTime6() {
		return lblRestTime6;
	}
	
	/**
	 * @param lblRestTime6 セットする lblRestTime6
	 */
	public void setLblRestTime6(String lblRestTime6) {
		this.lblRestTime6 = lblRestTime6;
	}
	
	/**
	 * @return lblPublicTime
	 */
	public String getLblPublicTime() {
		return lblPublicTime;
	}
	
	/**
	 * @param lblPublicTime セットする lblPublicTime
	 */
	public void setLblPublicTime(String lblPublicTime) {
		this.lblPublicTime = lblPublicTime;
	}
	
	/**
	 * @return lblPublicTime1
	 */
	public String getLblPublicTime1() {
		return lblPublicTime1;
	}
	
	/**
	 * @param lblPublicTime1 セットする lblPublicTime1
	 */
	public void setLblPublicTime1(String lblPublicTime1) {
		this.lblPublicTime1 = lblPublicTime1;
	}
	
	/**
	 * @return lblPublicTime2
	 */
	public String getLblPublicTime2() {
		return lblPublicTime2;
	}
	
	/**
	 * @param lblPublicTime2 セットする lblPublicTime2
	 */
	public void setLblPublicTime2(String lblPublicTime2) {
		this.lblPublicTime2 = lblPublicTime2;
	}
	
	/**
	 * @return lblPrivateTime
	 */
	public String getLblPrivateTime() {
		return lblPrivateTime;
	}
	
	/**
	 * @param lblPrivateTime セットする lblPrivateTime
	 */
	public void setLblPrivateTime(String lblPrivateTime) {
		this.lblPrivateTime = lblPrivateTime;
	}
	
	/**
	 * @return lblPrivateTime1
	 */
	public String getLblPrivateTime1() {
		return lblPrivateTime1;
	}
	
	/**
	 * @param lblPrivateTime1 セットする lblPrivateTime1
	 */
	public void setLblPrivateTime1(String lblPrivateTime1) {
		this.lblPrivateTime1 = lblPrivateTime1;
	}
	
	/**
	 * @return lblPrivateTime2
	 */
	public String getLblPrivateTime2() {
		return lblPrivateTime2;
	}
	
	/**
	 * @param lblPrivateTime2 セットする lblPrivateTime2
	 */
	public void setLblPrivateTime2(String lblPrivateTime2) {
		this.lblPrivateTime2 = lblPrivateTime2;
	}
	
	/**
	 * @return lblLateTime
	 */
	public String getLblLateTime() {
		return lblLateTime;
	}
	
	/**
	 * @param lblLateTime セットする lblLateTime
	 */
	public void setLblLateTime(String lblLateTime) {
		this.lblLateTime = lblLateTime;
	}
	
	/**
	 * @return lblLateReason
	 */
	public String getLblLateReason() {
		return lblLateReason;
	}
	
	/**
	 * @param lblLateReason セットする lblLateReason
	 */
	public void setLblLateReason(String lblLateReason) {
		this.lblLateReason = lblLateReason;
	}
	
	/**
	 * @return lblLateCertificate
	 */
	public String getLblLateCertificate() {
		return lblLateCertificate;
	}
	
	/**
	 * @param lblLateCertificate セットする lblLateCertificate
	 */
	public void setLblLateCertificate(String lblLateCertificate) {
		this.lblLateCertificate = lblLateCertificate;
	}
	
	/**
	 * @return lblLateComment
	 */
	public String getLblLateComment() {
		return lblLateComment;
	}
	
	/**
	 * @param lblLateComment セットする lblLateComment
	 */
	public void setLblLateComment(String lblLateComment) {
		this.lblLateComment = lblLateComment;
	}
	
	/**
	 * @return lblLeaveEarlyTime
	 */
	public String getLblLeaveEarlyTime() {
		return lblLeaveEarlyTime;
	}
	
	/**
	 * @param lblLeaveEarlyTime セットする lblLeaveEarlyTime
	 */
	public void setLblLeaveEarlyTime(String lblLeaveEarlyTime) {
		this.lblLeaveEarlyTime = lblLeaveEarlyTime;
	}
	
	/**
	 * @return lblLeaveEarlyReason
	 */
	public String getLblLeaveEarlyReason() {
		return lblLeaveEarlyReason;
	}
	
	/**
	 * @param lblLeaveEarlyReason セットする lblLeaveEarlyReason
	 */
	public void setLblLeaveEarlyReason(String lblLeaveEarlyReason) {
		this.lblLeaveEarlyReason = lblLeaveEarlyReason;
	}
	
	/**
	 * @return lblLeaveEarlyCertificate
	 */
	public String getLblLeaveEarlyCertificate() {
		return lblLeaveEarlyCertificate;
	}
	
	/**
	 * @param lblLeaveEarlyCertificate セットする lblLeaveEarlyCertificate
	 */
	public void setLblLeaveEarlyCertificate(String lblLeaveEarlyCertificate) {
		this.lblLeaveEarlyCertificate = lblLeaveEarlyCertificate;
	}
	
	/**
	 * @return lblLeaveEarlyComment
	 */
	public String getLblLeaveEarlyComment() {
		return lblLeaveEarlyComment;
	}
	
	/**
	 * @param lblLeaveEarlyComment セットする lblLeaveEarlyComment
	 */
	public void setLblLeaveEarlyComment(String lblLeaveEarlyComment) {
		this.lblLeaveEarlyComment = lblLeaveEarlyComment;
	}
	
	/**
	 * @return lblOvertime
	 */
	public String getLblOvertime() {
		return lblOvertime;
	}
	
	/**
	 * @param lblOvertime セットする lblOvertime
	 */
	public void setLblOvertime(String lblOvertime) {
		this.lblOvertime = lblOvertime;
	}
	
	/**
	 * @return lblOverTimeIn
	 */
	public String getLblOverTimeIn() {
		return lblOverTimeIn;
	}
	
	/**
	 * @param lblOverTimeIn セットする lblOverTimeIn
	 */
	public void setLblOverTimeIn(String lblOverTimeIn) {
		this.lblOverTimeIn = lblOverTimeIn;
	}
	
	/**
	 * @return lblOverTimeOut
	 */
	public String getLblOverTimeOut() {
		return lblOverTimeOut;
	}
	
	/**
	 * @param lblOverTimeOut セットする lblOverTimeOut
	 */
	public void setLblOverTimeOut(String lblOverTimeOut) {
		this.lblOverTimeOut = lblOverTimeOut;
	}
	
	/**
	 * @return lblLateNightTime
	 */
	public String getLblLateNightTime() {
		return lblLateNightTime;
	}
	
	/**
	 * @param lblLateNightTime セットする lblLateNightTime
	 */
	public void setLblLateNightTime(String lblLateNightTime) {
		this.lblLateNightTime = lblLateNightTime;
	}
	
	/**
	 * @return lblSpecificWorkTimeIn
	 */
	public String getLblSpecificWorkTimeIn() {
		return lblSpecificWorkTimeIn;
	}
	
	/**
	 * @param lblSpecificWorkTimeIn セットする lblSpecificWorkTimeIn
	 */
	public void setLblSpecificWorkTimeIn(String lblSpecificWorkTimeIn) {
		this.lblSpecificWorkTimeIn = lblSpecificWorkTimeIn;
	}
	
	/**
	 * @return lblSpecificWorkTimeOver
	 */
	public String getLblSpecificWorkTimeOver() {
		return lblSpecificWorkTimeOver;
	}
	
	/**
	 * @param lblSpecificWorkTimeOver セットする lblSpecificWorkTimeOver
	 */
	public void setLblSpecificWorkTimeOver(String lblSpecificWorkTimeOver) {
		this.lblSpecificWorkTimeOver = lblSpecificWorkTimeOver;
	}
	
	/**
	 * @return lblLegalWorkTime
	 */
	public String getLblLegalWorkTime() {
		return lblLegalWorkTime;
	}
	
	/**
	 * @param lblLegalWorkTime セットする lblLegalWorkTime
	 */
	public void setLblLegalWorkTime(String lblLegalWorkTime) {
		this.lblLegalWorkTime = lblLegalWorkTime;
	}
	
	/**
	 * @return lblHolidayWorkTime
	 */
	public String getLblHolidayWorkTime() {
		return lblHolidayWorkTime;
	}
	
	/**
	 * @param lblHolidayWorkTime セットする。 lblHolidayWorkTime
	 */
	public void setLblHolidayWorkTime(String lblHolidayWorkTime) {
		this.lblHolidayWorkTime = lblHolidayWorkTime;
	}
	
	/**
	 * @return lblDecreaseTime
	 */
	public String getLblDecreaseTime() {
		return lblDecreaseTime;
	}
	
	/**
	 * @param lblDecreaseTime セットする lblDecreaseTime
	 */
	public void setLblDecreaseTime(String lblDecreaseTime) {
		this.lblDecreaseTime = lblDecreaseTime;
	}
	
	/**
	 * @return lblAttendanceState
	 */
	public String getLblAttendanceState() {
		return lblAttendanceState;
	}
	
	/**
	 * @param lblAttendanceState セットする lblAttendanceState
	 */
	public void setLblAttendanceState(String lblAttendanceState) {
		this.lblAttendanceState = lblAttendanceState;
	}
	
	/**
	 * @return lblAttendanceApprover
	 */
	public String getLblAttendanceApprover() {
		return lblAttendanceApprover;
	}
	
	/**
	 * @param lblAttendanceApprover セットする lblAttendanceApprover
	 */
	public void setLblAttendanceApprover(String lblAttendanceApprover) {
		this.lblAttendanceApprover = lblAttendanceApprover;
	}
	
	/**
	 * @return lblAttendanceComment
	 */
	public String getLblAttendanceComment() {
		return lblAttendanceComment;
	}
	
	/**
	 * @param lblAttendanceComment セットする lblAttendanceComment
	 */
	public void setLblAttendanceComment(String lblAttendanceComment) {
		this.lblAttendanceComment = lblAttendanceComment;
	}
	
	/**
	 * @return lblOvertimeType
	 */
	public String[] getLblOvertimeType() {
		return getStringArrayClone(lblOvertimeType);
	}
	
	/**
	 * @param lblOvertimeType セットする lblOvertimeType
	 */
	public void setLblOvertimeType(String[] lblOvertimeType) {
		this.lblOvertimeType = getStringArrayClone(lblOvertimeType);
	}
	
	/**
	 * @return lblOvertimeSchedule
	 */
	public String[] getLblOvertimeSchedule() {
		return getStringArrayClone(lblOvertimeSchedule);
	}
	
	/**
	 * @param lblOvertimeSchedule セットする lblOvertimeSchedule
	 */
	public void setLblOvertimeSchedule(String[] lblOvertimeSchedule) {
		this.lblOvertimeSchedule = getStringArrayClone(lblOvertimeSchedule);
	}
	
	/**
	 * @return lblOvertimeResult
	 */
	public String[] getLblOvertimeResult() {
		return getStringArrayClone(lblOvertimeResult);
	}
	
	/**
	 * @param lblOvertimeResult セットする lblOvertimeResult
	 */
	public void setLblOvertimeResult(String[] lblOvertimeResult) {
		this.lblOvertimeResult = getStringArrayClone(lblOvertimeResult);
	}
	
	/**
	 * @return lblOvertimeReason
	 */
	public String[] getLblOvertimeReason() {
		return getStringArrayClone(lblOvertimeReason);
	}
	
	/**
	 * @param lblOvertimeReason セットする lblOvertimeReason
	 */
	public void setLblOvertimeReason(String[] lblOvertimeReason) {
		this.lblOvertimeReason = getStringArrayClone(lblOvertimeReason);
	}
	
	/**
	 * @return lblOvertimeState
	 */
	public String[] getLblOvertimeState() {
		return getStringArrayClone(lblOvertimeState);
	}
	
	/**
	 * @param lblOvertimeState セットする lblOvertimeState
	 */
	public void setLblOvertimeState(String[] lblOvertimeState) {
		this.lblOvertimeState = getStringArrayClone(lblOvertimeState);
	}
	
	/**
	 * @return lblOvertimeApprover
	 */
	public String[] getLblOvertimeApprover() {
		return getStringArrayClone(lblOvertimeApprover);
	}
	
	/**
	 * @param lblOvertimeApprover セットする lblOvertimeApprover
	 */
	public void setLblOvertimeApprover(String[] lblOvertimeApprover) {
		this.lblOvertimeApprover = getStringArrayClone(lblOvertimeApprover);
	}
	
	/**
	 * @return lblOvertimeComment
	 */
	public String[] getLblOvertimeComment() {
		return getStringArrayClone(lblOvertimeComment);
	}
	
	/**
	 * @param lblOvertimeComment セットする lblOvertimeComment
	 */
	public void setLblOvertimeComment(String[] lblOvertimeComment) {
		this.lblOvertimeComment = getStringArrayClone(lblOvertimeComment);
	}
	
	/**
	 * @return lblHolidayDate
	 */
	public String[] getLblHolidayDate() {
		return getStringArrayClone(lblHolidayDate);
	}
	
	/**
	 * @param lblHolidayDate セットする lblHolidayDate
	 */
	public void setLblHolidayDate(String[] lblHolidayDate) {
		this.lblHolidayDate = getStringArrayClone(lblHolidayDate);
	}
	
	/**
	 * @return lblHolidayType
	 */
	public String[] getLblHolidayType() {
		return getStringArrayClone(lblHolidayType);
	}
	
	/**
	 * @param lblHolidayType セットする lblHolidayType
	 */
	public void setLblHolidayType(String[] lblHolidayType) {
		this.lblHolidayType = getStringArrayClone(lblHolidayType);
	}
	
	/**
	 * @return lblHolidayLength
	 */
	public String[] getLblHolidayLength() {
		return getStringArrayClone(lblHolidayLength);
	}
	
	/**
	 * @param lblHolidayLength セットする lblHolidayLength
	 */
	public void setLblHolidayLength(String[] lblHolidayLength) {
		this.lblHolidayLength = getStringArrayClone(lblHolidayLength);
	}
	
	/**
	 * @return lblHolidayTime
	 */
	public String[] getLblHolidayTime() {
		return getStringArrayClone(lblHolidayTime);
	}
	
	/**
	 * @param lblHolidayTime セットする lblHolidayTime
	 */
	public void setLblHolidayTime(String[] lblHolidayTime) {
		this.lblHolidayTime = getStringArrayClone(lblHolidayTime);
	}
	
	/**
	 * @return lblHolidayReason
	 */
	public String[] getLblHolidayReason() {
		return getStringArrayClone(lblHolidayReason);
	}
	
	/**
	 * @param lblHolidayReason セットする lblHolidayReason
	 */
	public void setLblHolidayReason(String[] lblHolidayReason) {
		this.lblHolidayReason = getStringArrayClone(lblHolidayReason);
	}
	
	/**
	 * @return lblHolidayState
	 */
	public String[] getLblHolidayState() {
		return getStringArrayClone(lblHolidayState);
	}
	
	/**
	 * @param lblHolidayState セットする lblHolidayState
	 */
	public void setLblHolidayState(String[] lblHolidayState) {
		this.lblHolidayState = getStringArrayClone(lblHolidayState);
	}
	
	/**
	 * @return lblHolidayApprover
	 */
	public String[] getLblHolidayApprover() {
		return getStringArrayClone(lblHolidayApprover);
	}
	
	/**
	 * @param lblHolidayApprover セットする lblHolidayApprover
	 */
	public void setLblHolidayApprover(String[] lblHolidayApprover) {
		this.lblHolidayApprover = getStringArrayClone(lblHolidayApprover);
	}
	
	/**
	 * @return lblHolidayComment
	 */
	public String[] getLblHolidayComment() {
		return getStringArrayClone(lblHolidayComment);
	}
	
	/**
	 * @param lblHolidayComment セットする lblHolidayComment
	 */
	public void setLblHolidayComment(String[] lblHolidayComment) {
		this.lblHolidayComment = getStringArrayClone(lblHolidayComment);
	}
	
	/**
	 * @return lblWorkOnHolidayDate
	 */
	public String getLblWorkOnHolidayDate() {
		return lblWorkOnHolidayDate;
	}
	
	/**
	 * @param lblWorkOnHolidayDate セットする lblWorkOnHolidayDate
	 */
	public void setLblWorkOnHolidayDate(String lblWorkOnHolidayDate) {
		this.lblWorkOnHolidayDate = lblWorkOnHolidayDate;
	}
	
	/**
	 * @return lblWorkOnHolidayTime
	 */
	public String getLblWorkOnHolidayTime() {
		return lblWorkOnHolidayTime;
	}
	
	/**
	 * @param lblWorkOnHolidayTime セットする lblWorkOnHolidayTime
	 */
	public void setLblWorkOnHolidayTime(String lblWorkOnHolidayTime) {
		this.lblWorkOnHolidayTime = lblWorkOnHolidayTime;
	}
	
	/**
	 * @return lblWorkOnHolidayTransferDate
	 */
	public String getLblWorkOnHolidayTransferDate() {
		return lblWorkOnHolidayTransferDate;
	}
	
	/**
	 * @param lblWorkOnHolidayTransferDate セットする lblWorkOnHolidayTransferDate
	 */
	public void setLblWorkOnHolidayTransferDate(String lblWorkOnHolidayTransferDate) {
		this.lblWorkOnHolidayTransferDate = lblWorkOnHolidayTransferDate;
	}
	
	/**
	 * @return lblWorkOnHolidayReason
	 */
	public String getLblWorkOnHolidayReason() {
		return lblWorkOnHolidayReason;
	}
	
	/**
	 * @param lblWorkOnHolidayReason セットする lblWorkOnHolidayReason
	 */
	public void setLblWorkOnHolidayReason(String lblWorkOnHolidayReason) {
		this.lblWorkOnHolidayReason = lblWorkOnHolidayReason;
	}
	
	/**
	 * @return lblWorkOnHolidayState
	 */
	public String getLblWorkOnHolidayState() {
		return lblWorkOnHolidayState;
	}
	
	/**
	 * @param lblWorkOnHolidayState セットする lblWorkOnHolidayState
	 */
	public void setLblWorkOnHolidayState(String lblWorkOnHolidayState) {
		this.lblWorkOnHolidayState = lblWorkOnHolidayState;
	}
	
	/**
	 * @return lblWorkOnHolidayApprover
	 */
	public String getLblWorkOnHolidayApprover() {
		return lblWorkOnHolidayApprover;
	}
	
	/**
	 * @param lblWorkOnHolidayApprover セットする lblWorkOnHolidayApprover
	 */
	public void setLblWorkOnHolidayApprover(String lblWorkOnHolidayApprover) {
		this.lblWorkOnHolidayApprover = lblWorkOnHolidayApprover;
	}
	
	/**
	 * @return lblWorkOnHolidayComment
	 */
	public String getLblWorkOnHolidayComment() {
		return lblWorkOnHolidayComment;
	}
	
	/**
	 * @param lblWorkOnHolidayComment セットする lblWorkOnHolidayComment
	 */
	public void setLblWorkOnHolidayComment(String lblWorkOnHolidayComment) {
		this.lblWorkOnHolidayComment = lblWorkOnHolidayComment;
	}
	
	/**
	 * @return lblSubHolidayDate
	 */
	public String getLblSubHolidayDate() {
		return lblSubHolidayDate;
	}
	
	/**
	 * @param lblSubHolidayDate セットする lblSubHolidayDate
	 */
	public void setLblSubHolidayDate(String lblSubHolidayDate) {
		this.lblSubHolidayDate = lblSubHolidayDate;
	}
	
	/**
	 * @return lblSubHolidayWorkDate
	 */
	public String getLblSubHolidayWorkDate() {
		return lblSubHolidayWorkDate;
	}
	
	/**
	 * @param lblSubHolidayWorkDate セットする lblSubHolidayWorkDate
	 */
	public void setLblSubHolidayWorkDate(String lblSubHolidayWorkDate) {
		this.lblSubHolidayWorkDate = lblSubHolidayWorkDate;
	}
	
	/**
	 * @return lblSubHolidayState
	 */
	public String getLblSubHolidayState() {
		return lblSubHolidayState;
	}
	
	/**
	 * @param lblSubHolidayState セットする lblSubHolidayState
	 */
	public void setLblSubHolidayState(String lblSubHolidayState) {
		this.lblSubHolidayState = lblSubHolidayState;
	}
	
	/**
	 * @return lblSubHolidayApprover
	 */
	public String getLblSubHolidayApprover() {
		return lblSubHolidayApprover;
	}
	
	/**
	 * @param lblSubHolidayApprover セットする lblSubHolidayApprover
	 */
	public void setLblSubHolidayApprover(String lblSubHolidayApprover) {
		this.lblSubHolidayApprover = lblSubHolidayApprover;
	}
	
	/**
	 * @return lblSubHolidayComment
	 */
	public String getLblSubHolidayComment() {
		return lblSubHolidayComment;
	}
	
	/**
	 * @param lblSubHolidayComment セットする lblSubHolidayComment
	 */
	public void setLblSubHolidayComment(String lblSubHolidayComment) {
		this.lblSubHolidayComment = lblSubHolidayComment;
	}
	
	/**
	 * @return lblWorkTypeChangeDate
	 */
	public String getLblWorkTypeChangeDate() {
		return lblWorkTypeChangeDate;
	}
	
	/**
	 * @param lblWorkTypeChangeDate セットする lblWorkTypeChangeDate
	 */
	public void setLblWorkTypeChangeDate(String lblWorkTypeChangeDate) {
		this.lblWorkTypeChangeDate = lblWorkTypeChangeDate;
	}
	
	/**
	 * @return lblWorkTypeChangeBeforeWorkType
	 */
	public String getLblWorkTypeChangeBeforeWorkType() {
		return lblWorkTypeChangeBeforeWorkType;
	}
	
	/**
	 * @param lblWorkTypeChangeBeforeWorkType セットする lblWorkTypeChangeBeforeWorkType
	 */
	public void setLblWorkTypeChangeBeforeWorkType(String lblWorkTypeChangeBeforeWorkType) {
		this.lblWorkTypeChangeBeforeWorkType = lblWorkTypeChangeBeforeWorkType;
	}
	
	/**
	 * @return lblWorkTypeChangeAfterWorkType
	 */
	public String getLblWorkTypeChangeAfterWorkType() {
		return lblWorkTypeChangeAfterWorkType;
	}
	
	/**
	 * @param lblWorkTypeChangeAfterWorkType セットする lblWorkTypeChangeAfterWorkType
	 */
	public void setLblWorkTypeChangeAfterWorkType(String lblWorkTypeChangeAfterWorkType) {
		this.lblWorkTypeChangeAfterWorkType = lblWorkTypeChangeAfterWorkType;
	}
	
	/**
	 * @return lblWorkTypeChangeReason
	 */
	public String getLblWorkTypeChangeReason() {
		return lblWorkTypeChangeReason;
	}
	
	/**
	 * @param lblWorkTypeChangeReason セットする lblWorkTypeChangeReason
	 */
	public void setLblWorkTypeChangeReason(String lblWorkTypeChangeReason) {
		this.lblWorkTypeChangeReason = lblWorkTypeChangeReason;
	}
	
	/**
	 * @return lblWorkTypeChangeState
	 */
	public String getLblWorkTypeChangeState() {
		return lblWorkTypeChangeState;
	}
	
	/**
	 * @param lblWorkTypeChangeState セットする lblWorkTypeChangeState
	 */
	public void setLblWorkTypeChangeState(String lblWorkTypeChangeState) {
		this.lblWorkTypeChangeState = lblWorkTypeChangeState;
	}
	
	/**
	 * @return lblWorkTypeChangeComment
	 */
	public String getLblWorkTypeChangeComment() {
		return lblWorkTypeChangeComment;
	}
	
	/**
	 * @param lblWorkTypeChangeComment セットする lblWorkTypeChangeComment
	 */
	public void setLblWorkTypeChangeComment(String lblWorkTypeChangeComment) {
		this.lblWorkTypeChangeComment = lblWorkTypeChangeComment;
	}
	
	/**
	 * @return lblWorkTypeChangeApprover
	 */
	public String getLblWorkTypeChangeApprover() {
		return lblWorkTypeChangeApprover;
	}
	
	/**
	 * @param lblWorkTypeChangeApprover セットする lblWorkTypeChangeApprover
	 */
	public void setLblWorkTypeChangeApprover(String lblWorkTypeChangeApprover) {
		this.lblWorkTypeChangeApprover = lblWorkTypeChangeApprover;
	}
	
	/**
	 * @return lblDifferenceDate
	 */
	public String getLblDifferenceDate() {
		return lblDifferenceDate;
	}
	
	/**
	 * @param lblDifferenceDate セットする lblDifferenceDate
	 */
	public void setLblDifferenceDate(String lblDifferenceDate) {
		this.lblDifferenceDate = lblDifferenceDate;
	}
	
	/**
	 * @return lblDifferenceState
	 */
	public String getLblDifferenceState() {
		return lblDifferenceState;
	}
	
	/**
	 * @param lblDifferenceState セットする lblDifferenceState
	 */
	public void setLblDifferenceState(String lblDifferenceState) {
		this.lblDifferenceState = lblDifferenceState;
	}
	
	/**
	 * @return lblDifferenceWorkType
	 */
	public String getLblDifferenceWorkType() {
		return lblDifferenceWorkType;
	}
	
	/**
	 * @param lblDifferenceWorkType セットする lblDifferenceWorkType
	 */
	public void setLblDifferenceWorkType(String lblDifferenceWorkType) {
		this.lblDifferenceWorkType = lblDifferenceWorkType;
	}
	
	/**
	 * @return lblDifferenceReason
	 */
	public String getLblDifferenceReason() {
		return lblDifferenceReason;
	}
	
	/**
	 * @param lblDifferenceReason セットする lblDifferenceReason
	 */
	public void setLblDifferenceReason(String lblDifferenceReason) {
		this.lblDifferenceReason = lblDifferenceReason;
	}
	
	/**
	 * @return lblDifferenceWorkTime
	 */
	public String getLblDifferenceWorkTime() {
		return lblDifferenceWorkTime;
	}
	
	/**
	 * @param lblDifferenceWorkTime セットする lblDifferenceWorkTime
	 */
	public void setLblDifferenceWorkTime(String lblDifferenceWorkTime) {
		this.lblDifferenceWorkTime = lblDifferenceWorkTime;
	}
	
	/**
	 * @return lblDifferenceApprover
	 */
	public String getLblDifferenceApprover() {
		return lblDifferenceApprover;
	}
	
	/**
	 * @param lblDifferenceApprover セットする lblDifferenceApprover
	 */
	public void setLblDifferenceApprover(String lblDifferenceApprover) {
		this.lblDifferenceApprover = lblDifferenceApprover;
	}
	
	/**
	 * @return lblDifferenceComment
	 */
	public String getLblDifferenceComment() {
		return lblDifferenceComment;
	}
	
	/**
	 * @param lblDifferenceComment セットする lblDifferenceComment
	 */
	public void setLblDifferenceComment(String lblDifferenceComment) {
		this.lblDifferenceComment = lblDifferenceComment;
	}
	
	/**
	 * @return lblCancelState
	 */
	public String getLblCancelState() {
		return lblCancelState;
	}
	
	/**
	 * @param lblCancelState セットする lblCancelState
	 */
	public void setLblCancelState(String lblCancelState) {
		this.lblCancelState = lblCancelState;
	}
	
	/**
	 * @return lblCancelApprover
	 */
	public String getLblCancelApprover() {
		return lblCancelApprover;
	}
	
	/**
	 * @param lblCancelApprover セットする lblCancelApprover
	 */
	public void setLblCancelApprover(String lblCancelApprover) {
		this.lblCancelApprover = lblCancelApprover;
	}
	
	/**
	 * @return isConfirmation
	 */
	public boolean isConfirmation() {
		return isConfirmation;
	}
	
	/**
	 * @param isConfirmation セットする isConfirmation
	 */
	public void setConfirmation(boolean isConfirmation) {
		this.isConfirmation = isConfirmation;
	}
	
	/**
	 * @return isAttendance
	 */
	public boolean isAttendance() {
		return isAttendance;
	}
	
	/**
	 * @param isAttendance セットする isAttendance
	 */
	public void setAttendance(boolean isAttendance) {
		this.isAttendance = isAttendance;
	}
	
	/**
	 * @return isOvertime
	 */
	public boolean isOvertime() {
		return isOvertime;
	}
	
	/**
	 * @param isOvertime セットする isOvertime
	 */
	public void setOvertime(boolean isOvertime) {
		this.isOvertime = isOvertime;
	}
	
	/**
	 * @return isHoliday
	 */
	public boolean isHoliday() {
		return isHoliday;
	}
	
	/**
	 * @param isHoliday セットする isHoliday
	 */
	public void setHoliday(boolean isHoliday) {
		this.isHoliday = isHoliday;
	}
	
	/**
	 * @return isWorkOnHoliday
	 */
	public boolean isWorkOnHoliday() {
		return isWorkOnHoliday;
	}
	
	/**
	 * @param isWorkOnHoliday セットする isWorkOnHoliday
	 */
	public void setWorkOnHoliday(boolean isWorkOnHoliday) {
		this.isWorkOnHoliday = isWorkOnHoliday;
	}
	
	/**
	 * @return isSubHoliday
	 */
	public boolean isSubHoliday() {
		return isSubHoliday;
	}
	
	/**
	 * @param isSubHoliday セットする isSubHoliday
	 */
	public void setSubHoliday(boolean isSubHoliday) {
		this.isSubHoliday = isSubHoliday;
	}
	
	/**
	 * @return isWorkTypeChange
	 */
	public boolean isWorkTypeChange() {
		return isWorkTypeChange;
	}
	
	/**
	 * @param isWorkTypeChange セットする isWorkTypeChange
	 */
	public void setWorkTypeChange(boolean isWorkTypeChange) {
		this.isWorkTypeChange = isWorkTypeChange;
	}
	
	/**
	 * @return isDifference
	 */
	public boolean isDifference() {
		return isDifference;
	}
	
	/**
	 * @param isDifference セットする isDifference
	 */
	public void setDifference(boolean isDifference) {
		this.isDifference = isDifference;
	}
	
	/**
	 * @return txtDifferenceComment
	 */
	public String getTxtDifferenceComment() {
		return txtDifferenceComment;
	}
	
	/**
	 * @param txtDifferenceComment セットする txtDifferenceComment
	 */
	public void setTxtDifferenceComment(String txtDifferenceComment) {
		this.txtDifferenceComment = txtDifferenceComment;
	}
	
	/**
	 * @return txtCancelComment
	 */
	public String getTxtCancelComment() {
		return txtCancelComment;
	}
	
	/**
	 * @param txtCancelComment セットする txtCancelComment
	 */
	public void setTxtCancelComment(String txtCancelComment) {
		this.txtCancelComment = txtCancelComment;
	}
	
	/**
	 * @return lblDirectWorkManage
	 */
	public String getLblDirectWorkManage() {
		return lblDirectWorkManage;
	}
	
	/**
	 * @param lblDirectWorkManage セットする lblDirectWorkManage
	 */
	public void setLblDirectWorkManage(String lblDirectWorkManage) {
		this.lblDirectWorkManage = lblDirectWorkManage;
	}
	
	/**
	 * @return lblUnpaidShortTime
	 */
	public String getLblUnpaidShortTime() {
		return lblUnpaidShortTime;
	}
	
	/**
	 * @param lblUnpaidShortTime セットする lblUnpaidShortTime
	 */
	public void setLblUnpaidShortTime(String lblUnpaidShortTime) {
		this.lblUnpaidShortTime = lblUnpaidShortTime;
	}
	
	/**
	 * @return lblTimeComment
	 */
	public String getLblTimeComment() {
		return lblTimeComment;
	}
	
	/**
	 * @param lblTimeComment セットする lblTimeComment
	 */
	public void setLblTimeComment(String lblTimeComment) {
		this.lblTimeComment = lblTimeComment;
	}
	
	/**
	 * @return lblYear
	 */
	public String getLblYear() {
		return lblYear;
	}
	
	/**
	 * @param lblYear セットする lblYear
	 */
	public void setLblYear(String lblYear) {
		this.lblYear = lblYear;
	}
	
	/**
	 * @return lblMonth
	 */
	public String getLblMonth() {
		return lblMonth;
	}
	
	/**
	 * @param lblMonth セットする lblMonth
	 */
	public void setLblMonth(String lblMonth) {
		this.lblMonth = lblMonth;
	}
	
	/**
	 * @return lblDay
	 */
	public String getLblDay() {
		return lblDay;
	}
	
	/**
	 * @param lblDay セットする lblDay
	 */
	public void setLblDay(String lblDay) {
		this.lblDay = lblDay;
	}
	
	/**
	 * @return workflow
	 */
	public Long getWorkflow() {
		return workflow;
	}
	
	/**
	 * @param workflow セットする workflow
	 */
	public void setWorkflow(Long workflow) {
		this.workflow = workflow;
	}
	
	/**
	 * @return needApproveButton
	 */
	public boolean isNeedApproveButton() {
		return needApproveButton;
	}
	
	/**
	 * @param needApproveButton セットする needApproveButton
	 */
	public void setNeedApproveButton(boolean needApproveButton) {
		this.needApproveButton = needApproveButton;
	}
	
	/**
	 * @return needCancelButton
	 */
	public boolean isNeedCancelButton() {
		return needCancelButton;
	}
	
	/**
	 * @param needCancelButton セットする needCancelButton
	 */
	public void setNeedCancelButton(boolean needCancelButton) {
		this.needCancelButton = needCancelButton;
	}
	
	/**
	 * @return needCancelApproveButton
	 */
	public boolean isNeedCancelApproveButton() {
		return needCancelApproveButton;
	}
	
	/**
	 * @param needCancelApproveButton セットする needCancelApproveButton
	 */
	public void setNeedCancelApproveButton(boolean needCancelApproveButton) {
		this.needCancelApproveButton = needCancelApproveButton;
	}
	
	/**
	 * @return lblRemarks
	 */
	public String getLblRemarks() {
		return lblRemarks;
	}
	
	/**
	 * @param lblRemarks セットする lblRemarks
	 */
	public void setLblRemarks(String lblRemarks) {
		this.lblRemarks = lblRemarks;
	}
	
	/**
	 * @return lblCheckWorkStart
	 */
	public String getLblCheckWorkStart() {
		return lblCheckWorkStart;
	}
	
	/**
	 * @param lblCheckWorkStart セットする lblCheckWorkStart
	 */
	public void setLblCheckWorkStart(String lblCheckWorkStart) {
		this.lblCheckWorkStart = lblCheckWorkStart;
	}
	
	/**
	 * @return prevWorkflow
	 */
	public long getPrevWorkflow() {
		return prevWorkflow;
	}
	
	/**
	 * @param prevWorkflow セットする prevWorkflow
	 */
	public void setPrevWorkflow(long prevWorkflow) {
		this.prevWorkflow = prevWorkflow;
	}
	
	/**
	 * @return nextWorkflow
	 */
	public long getNextWorkflow() {
		return nextWorkflow;
	}
	
	/**
	 * @param nextWorkflow セットする nextWorkflow
	 */
	public void setNextWorkflow(long nextWorkflow) {
		this.nextWorkflow = nextWorkflow;
	}
	
	/**
	 * @return prevCommand
	 */
	public String getPrevCommand() {
		return prevCommand;
	}
	
	/**
	 * @param prevCommand セットする prevCommand
	 */
	public void setPrevCommand(String prevCommand) {
		this.prevCommand = prevCommand;
	}
	
	/**
	 * @return nextCommand
	 */
	public String getNextCommand() {
		return nextCommand;
	}
	
	/**
	 * @param nextCommand セットする nextCommand
	 */
	public void setNextCommand(String nextCommand) {
		this.nextCommand = nextCommand;
	}
	
	/**
	 * @return rollArray
	 */
	public BaseDtoInterface[] getRollArray() {
		return getDtoArrayClone(rollArray);
	}
	
	/**
	 * @param rollArray セットする rollArray
	 */
	public void setRollArray(BaseDtoInterface[] rollArray) {
		this.rollArray = getDtoArrayClone(rollArray);
	}
	
	/**
	 * @return addonJsps
	 */
	public List<String> getAddonJsps() {
		return addonJsps;
	}
	
	/**
	 * @param addonJsps セットする addonJsps
	 */
	public void setAddonJsps(List<String> addonJsps) {
		this.addonJsps = addonJsps;
	}
	
	/**
	 * @return addonParameters
	 */
	public Map<String, String[]> getAddonParameters() {
		return addonParameters;
	}
	
	/**
	 * @param addonParameters セットする addonParameters
	 */
	public void setAddonParameters(Map<String, String[]> addonParameters) {
		this.addonParameters = addonParameters;
	}
	
	/**
	 * 追加パラメータを設定する。<br>
	 * @param key    キー
	 * @param values 値
	 */
	public void putAddonParameters(String key, String[] values) {
		addonParameters.put(key, values);
	}
	
	/**
	 * 追加パラメータを設定する。<br>
	 * @param key   キー
	 * @param value 値
	 */
	public void putAddonParameters(String key, String value) {
		String[] values = { value };
		addonParameters.put(key, values);
	}
	
	/**
	 * 追加パラメータを取得する。<br>
	 * @param key キー
	 * @return 追加パラメータ
	 */
	public String[] getAddonParameters(String key) {
		return addonParameters.get(key);
	}
	
	/**
	 * 追加パラメータを取得する。<br>
	 * @param key キー
	 * @return 追加パラメータ
	 */
	public String getAddonParameter(String key) {
		String[] addonParameter = addonParameters.get(key);
		if (addonParameter == null || addonParameter.length == 0) {
			return null;
		}
		return addonParameter[0];
	}
	
}
