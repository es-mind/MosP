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
package jp.mosp.time.input.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.time.base.TimeVo;

/**
 * 勤怠詳細の情報を格納する。
 */
public class AttendanceCardVo extends TimeVo {
	
	private static final long		serialVersionUID	= -1437347353335444758L;
	
	/**
	 * 対象日ラベル。<br>
	 */
	private String					lblYear;
	private String					lblMonth;
	private String					lblDay;
	private String					lblDayOfTheWeek;
	// 曜日スタイル。
	private String					lblWorkDayOfWeekStyle;
	
	private String					pltWorkType;
	private String					txtStartTimeHour;
	private String					txtStartTimeMinute;
	private String					lblStartTime;
	private String					txtEndTimeHour;
	private String					txtEndTimeMinute;
	private String					lblEndTime;
	private String					ckbDirectStart;
	private String					ckbDirectEnd;
	private String					ckbForgotRecordWorkStart;
	private String					ckbNotRecordWorkStart;
	private String					lblWorkTime;
	private String					lblUnpaidShortTime;
	private String					lblApprovalState;
	private String					lblCorrectionHistory;
	private String					txtCorrectionReason;
	private String					txtTimeComment;
	private String					txtRemarks;
	
	private String					lblRestTime;
	private String					lblOverRestTime;
	private String					lblNightRestTime;
	private String					lblPublicTime;
	private String					lblPrivateTime;
	
	private String					txtRestStartHour1;
	private String					txtRestStartMinute1;
	private String					txtRestEndHour1;
	private String					txtRestEndMinute1;
	private String					txtRestStartHour2;
	private String					txtRestStartMinute2;
	private String					txtRestEndHour2;
	private String					txtRestEndMinute2;
	private String					txtRestStartHour3;
	private String					txtRestStartMinute3;
	private String					txtRestEndHour3;
	private String					txtRestEndMinute3;
	private String					txtRestStartHour4;
	private String					txtRestStartMinute4;
	private String					txtRestEndHour4;
	private String					txtRestEndMinute4;
	private String					txtRestStartHour5;
	private String					txtRestStartMinute5;
	private String					txtRestEndHour5;
	private String					txtRestEndMinute5;
	private String					txtRestStartHour6;
	private String					txtRestStartMinute6;
	private String					txtRestEndHour6;
	private String					txtRestEndMinute6;
	
	private String					txtPublicStartHour1;
	private String					txtPublicStartMinute1;
	private String					txtPublicEndHour1;
	private String					txtPublicEndMinute1;
	private String					txtPublicStartHour2;
	private String					txtPublicStartMinute2;
	private String					txtPublicEndHour2;
	private String					txtPublicEndMinute2;
	
	private String					txtPrivateStartHour1;
	private String					txtPrivateStartMinute1;
	private String					txtPrivateEndHour1;
	private String					txtPrivateEndMinute1;
	private String					txtPrivateStartHour2;
	private String					txtPrivateStartMinute2;
	private String					txtPrivateEndHour2;
	private String					txtPrivateEndMinute2;
	
	private String					lblLateTime;
	private String					pltLateReason;
	private String					pltLateCertificate;
	private String					txtLateComment;
	
	private String					lblLeaveEarlyTime;
	private String					pltLeaveEarlyReason;
	private String					pltLeaveEarlyCertificate;
	private String					txtLeaveEarlyComment;
	
	private String					lblOvertime;
	private String					lblOvertimeIn;
	private String					lblOvertimeOut;
	private String					lblLateNightTime;
	private String					lblSpecificWorkTimeIn;
	private String					lblSpecificWorkTimeOver;
	private String					lblLegalWorkTime;
	private String					lblHolidayWorkTime;
	private String					lblDecreaseTime;
	
	private String					pltAllowance1;
	private String					pltAllowance2;
	private String					pltAllowance3;
	private String					pltAllowance4;
	private String					pltAllowance5;
	private String					pltAllowance6;
	private String					pltAllowance7;
	private String					pltAllowance8;
	private String					pltAllowance9;
	private String					pltAllowance10;
	
	private String					lblAttendanceState;
	private String					lblAttendanceApprover;
	private String					lblAttendanceComment;
	
	private String[]				lblOvertimeType;
	private String[]				lblOvertimeSchedule;
	private String[]				lblOvertimeResult;
	private String[]				lblOvertimeReason;
	private String[]				lblOvertimeState;
	private String[]				lblOvertimeApprover;
	private String[]				lblOvertimeComment;
	
	private String					lblOvertimeTransferParams;
	private String					lblOvertimeCmd;
	
	private String[]				lblHolidayType;
	private String[]				lblHolidayLength;
	private String[]				lblHolidayTime;
	private String[]				lblHolidayReason;
	private String[]				lblHolidayWorkType;
	private String[]				lblHolidayState;
	private String[]				lblHolidayApprover;
	private String[]				lblHolidayComment;
	
	private String					lblHolidayTransferParams;
	private String					lblHolidayCmd;
	
	private String					lblWorkOnHolidayDate;
	private String					lblWorkOnHolidayTime;
	private String					lblSubStituteDate;
	private String					lblWorkOnHolidayReason;
	private String					lblWorkOnHolidayState;
	private String					lblWorkOnHolidayApprover;
	private String					lblWorkOnHolidayComment;
	
	private String					lblWorkOnHolidayTransferParams;
	private String					lblWorkOnHolidayCmd;
	
	private String[]				lblSubHolidayDate;
	private String[]				lblSubHolidayLength;
	private String[]				lblSubHolidayWorkDate;
	private String[]				lblSubHolidayState;
	private String[]				lblSubHolidayApprover;
	private String[]				lblSubHolidayComment;
	
	private String					lblSubHolidayTransferParams;
	private String					lblSubHolidayCmd;
	
	private String					lblWorkTypeChangeDate;
	private String					lblWorkTypeChangeBeforeWorkType;
	private String					lblWorkTypeChangeAfterWorkType;
	private String					lblWorkTypeChangeReason;
	private String					lblWorkTypeChangeState;
	private String					lblWorkTypeChangeComment;
	private String					lblWorkTypeChangeApprover;
	
	private String					lblDifferenceDate;
	private String					lblDifferenceWorkType;
	private String					lblDifferenceReason;
	private String					lblDifferenceWorkTime;
	private String					lblDifferenceApprover;
	private String					lblDifferenceState;
	private String					lblDifferenceComment;
	
	private String					lblDifferenceTransferParams;
	private String					lblDifferenceCmd;
	
	private String[][]				aryPltAllowance;
	private String[][]				aryPltWorkType;
	
	private String[]				aryPltLblApproverSetting;
	private String[]				aryPltApproverSetting;
	private String[][][]			aryApproverInfo;
	private String					pltApproverSetting1;
	private String					pltApproverSetting2;
	private String					pltApproverSetting3;
	private String					pltApproverSetting4;
	private String					pltApproverSetting5;
	private String					pltApproverSetting6;
	private String					pltApproverSetting7;
	private String					pltApproverSetting8;
	private String					pltApproverSetting9;
	private String					pltApproverSetting10;
	private String[]				pltApproverSetting;
	
	private String					txtAttendanceComment;
	private String					lblGeneralWorkTime;
	private String					tmdAttendanceId;
	
	private String[][]				aryPltLateReason;
	private String[][]				aryPltLeaveEarlyReason;
	private String[][]				aryPltLateCertificate;
	
	/**
	 * 勤怠設定追加JSPリスト。<br>
	 */
	private List<String>			addonJsps;
	
	/**
	 * 勤怠設定追加パラメータ群(キー：パラメータ名)。<br>
	 */
	private Map<String, String[]>	addonParameters;
	
	/**
	 * 勤怠設定追加プルダウン群(キー：パラメータ名)。<br>
	 */
	private Map<String, String[][]>	addonPulldowns;
	
	/**
	 * 追加フィールド。<br>
	 * アドオン機能で利用する。<br>
	 */
	private String					ckbExtraFiled;
	
	
	/**
	 * コンストラクタ
	 * {@link TimeVo#TimeVo()}を実行する。<br>
	 */
	public AttendanceCardVo() {
		super();
		addonJsps = new ArrayList<String>();
		addonParameters = new HashMap<String, String[]>();
		addonPulldowns = new HashMap<String, String[][]>();
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
	 * @return lblDayOfTheWeek
	 */
	public String getLblDayOfTheWeek() {
		return lblDayOfTheWeek;
	}
	
	/**
	 * @param lblDayOfTheWeek セットする lblDayOfTheWeek
	 */
	public void setLblDayOfTheWeek(String lblDayOfTheWeek) {
		this.lblDayOfTheWeek = lblDayOfTheWeek;
	}
	
	/**
	 * @return pltWorkType
	 */
	public String getPltWorkType() {
		return pltWorkType;
	}
	
	/**
	 * @param pltWorkType セットする pltWorkType
	 */
	public void setPltWorkType(String pltWorkType) {
		this.pltWorkType = pltWorkType;
	}
	
	/**
	 * @return txtStartTimeHour
	 */
	public String getTxtStartTimeHour() {
		return txtStartTimeHour;
	}
	
	/**
	 * @param txtStartTimeHour セットする txtStartTimeHour
	 */
	public void setTxtStartTimeHour(String txtStartTimeHour) {
		this.txtStartTimeHour = txtStartTimeHour;
	}
	
	/**
	 * @return txtStartTimeMinute
	 */
	public String getTxtStartTimeMinute() {
		return txtStartTimeMinute;
	}
	
	/**
	 * @param txtStartTimeMinute セットする txtStartTimeMinute
	 */
	public void setTxtStartTimeMinute(String txtStartTimeMinute) {
		this.txtStartTimeMinute = txtStartTimeMinute;
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
	 * @return txtEndTimeHour
	 */
	public String getTxtEndTimeHour() {
		return txtEndTimeHour;
	}
	
	/**
	 * @param txtEndTimeHour セットする txtEndTimeHour
	 */
	public void setTxtEndTimeHour(String txtEndTimeHour) {
		this.txtEndTimeHour = txtEndTimeHour;
	}
	
	/**
	 * @return txtEndTimeMinute
	 */
	public String getTxtEndTimeMinute() {
		return txtEndTimeMinute;
	}
	
	/**
	 * @param txtEndTimeMinute セットする txtEndTimeMinute
	 */
	public void setTxtEndTimeMinute(String txtEndTimeMinute) {
		this.txtEndTimeMinute = txtEndTimeMinute;
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
	 * @return ckbDirectStart
	 */
	public String getCkbDirectStart() {
		return ckbDirectStart;
	}
	
	/**
	 * @param ckbDirectStart セットする ckbDirectStart
	 */
	public void setCkbDirectStart(String ckbDirectStart) {
		this.ckbDirectStart = ckbDirectStart;
	}
	
	/**
	 * @return ckbDirectEnd
	 */
	public String getCkbDirectEnd() {
		return ckbDirectEnd;
	}
	
	/**
	 * @param ckbDirectEnd セットする ckbDirectEnd
	 */
	public void setCkbDirectEnd(String ckbDirectEnd) {
		this.ckbDirectEnd = ckbDirectEnd;
	}
	
	/**
	 * @return ckbForgotRecordWorkStart
	 */
	public String getCkbForgotRecordWorkStart() {
		return ckbForgotRecordWorkStart;
	}
	
	/**
	 * @param ckbForgotRecordWorkStart セットする ckbForgotRecordWorkStart
	 */
	public void setCkbForgotRecordWorkStart(String ckbForgotRecordWorkStart) {
		this.ckbForgotRecordWorkStart = ckbForgotRecordWorkStart;
	}
	
	/**
	 * @return ckbNotRecordWorkStart
	 */
	public String getCkbNotRecordWorkStart() {
		return ckbNotRecordWorkStart;
	}
	
	/**
	 * @param ckbNotRecordWorkStart セットする ckbNotRecordWorkStart
	 */
	public void setCkbNotRecordWorkStart(String ckbNotRecordWorkStart) {
		this.ckbNotRecordWorkStart = ckbNotRecordWorkStart;
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
	 * @return lblApprovalState
	 */
	public String getLblApprovalState() {
		return lblApprovalState;
	}
	
	/**
	 * @param lblApprovalState セットする lblApprovalState
	 */
	public void setLblApprovalState(String lblApprovalState) {
		this.lblApprovalState = lblApprovalState;
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
	 * @return txtCorrectionReason
	 */
	public String getTxtCorrectionReason() {
		return txtCorrectionReason;
	}
	
	/**
	 * @param txtCorrectionReason セットする txtCorrectionReason
	 */
	public void setTxtCorrectionReason(String txtCorrectionReason) {
		this.txtCorrectionReason = txtCorrectionReason;
	}
	
	/**
	 * @return txtTimeComment
	 */
	public String getTxtTimeComment() {
		return txtTimeComment;
	}
	
	/**
	 * @param txtTimeComment セットする txtTimeComment
	 */
	public void setTxtTimeComment(String txtTimeComment) {
		this.txtTimeComment = txtTimeComment;
	}
	
	/**
	 * @return txtRemarks
	 */
	public String getTxtRemarks() {
		return txtRemarks;
	}
	
	/**
	 * @param txtRemarks セットする txtRemarks
	 */
	public void setTxtRemarks(String txtRemarks) {
		this.txtRemarks = txtRemarks;
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
	 * @return lblOverRestTime
	 */
	public String getLblOverRestTime() {
		return lblOverRestTime;
	}
	
	/**
	 * @param lblOverRestTime セットする lblOverRestTime
	 */
	public void setLblOverRestTime(String lblOverRestTime) {
		this.lblOverRestTime = lblOverRestTime;
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
	 * @return txtRestStartHour1
	 */
	public String getTxtRestStartHour1() {
		return txtRestStartHour1;
	}
	
	/**
	 * @param txtRestStartHour1 セットする txtRestStartHour1
	 */
	public void setTxtRestStartHour1(String txtRestStartHour1) {
		this.txtRestStartHour1 = txtRestStartHour1;
	}
	
	/**
	 * @return txtRestStartMinute1
	 */
	public String getTxtRestStartMinute1() {
		return txtRestStartMinute1;
	}
	
	/**
	 * @param txtRestStartMinute1 セットする txtRestStartMinute1
	 */
	public void setTxtRestStartMinute1(String txtRestStartMinute1) {
		this.txtRestStartMinute1 = txtRestStartMinute1;
	}
	
	/**
	 * @return txtRestEndHour1
	 */
	public String getTxtRestEndHour1() {
		return txtRestEndHour1;
	}
	
	/**
	 * @param txtRestEndHour1 セットする txtRestEndHour1
	 */
	public void setTxtRestEndHour1(String txtRestEndHour1) {
		this.txtRestEndHour1 = txtRestEndHour1;
	}
	
	/**
	 * @return txtRestEndMinute1
	 */
	public String getTxtRestEndMinute1() {
		return txtRestEndMinute1;
	}
	
	/**
	 * @param txtRestEndMinute1 セットする txtRestEndMinute1
	 */
	public void setTxtRestEndMinute1(String txtRestEndMinute1) {
		this.txtRestEndMinute1 = txtRestEndMinute1;
	}
	
	/**
	 * @return txtRestStartHour2
	 */
	public String getTxtRestStartHour2() {
		return txtRestStartHour2;
	}
	
	/**
	 * @param txtRestStartHour2 セットする txtRestStartHour2
	 */
	public void setTxtRestStartHour2(String txtRestStartHour2) {
		this.txtRestStartHour2 = txtRestStartHour2;
	}
	
	/**
	 * @return txtRestStartMinute2
	 */
	public String getTxtRestStartMinute2() {
		return txtRestStartMinute2;
	}
	
	/**
	 * @param txtRestStartMinute2 セットする txtRestStartMinute2
	 */
	public void setTxtRestStartMinute2(String txtRestStartMinute2) {
		this.txtRestStartMinute2 = txtRestStartMinute2;
	}
	
	/**
	 * @return txtRestEndHour2
	 */
	public String getTxtRestEndHour2() {
		return txtRestEndHour2;
	}
	
	/**
	 * @param txtRestEndHour2 セットする txtRestEndHour2
	 */
	public void setTxtRestEndHour2(String txtRestEndHour2) {
		this.txtRestEndHour2 = txtRestEndHour2;
	}
	
	/**
	 * @return txtRestEndMinute2
	 */
	public String getTxtRestEndMinute2() {
		return txtRestEndMinute2;
	}
	
	/**
	 * @param txtRestEndMinute2 セットする txtRestEndMinute2
	 */
	public void setTxtRestEndMinute2(String txtRestEndMinute2) {
		this.txtRestEndMinute2 = txtRestEndMinute2;
	}
	
	/**
	 * @return txtRestStartHour3
	 */
	public String getTxtRestStartHour3() {
		return txtRestStartHour3;
	}
	
	/**
	 * @param txtRestStartHour3 セットする txtRestStartHour3
	 */
	public void setTxtRestStartHour3(String txtRestStartHour3) {
		this.txtRestStartHour3 = txtRestStartHour3;
	}
	
	/**
	 * @return txtRestStartMinute3
	 */
	public String getTxtRestStartMinute3() {
		return txtRestStartMinute3;
	}
	
	/**
	 * @param txtRestStartMinute3 セットする txtRestStartMinute3
	 */
	public void setTxtRestStartMinute3(String txtRestStartMinute3) {
		this.txtRestStartMinute3 = txtRestStartMinute3;
	}
	
	/**
	 * @return txtRestEndHour3
	 */
	public String getTxtRestEndHour3() {
		return txtRestEndHour3;
	}
	
	/**
	 * @param txtRestEndHour3 セットする txtRestEndHour3
	 */
	public void setTxtRestEndHour3(String txtRestEndHour3) {
		this.txtRestEndHour3 = txtRestEndHour3;
	}
	
	/**
	 * @return txtRestEndMinute3
	 */
	public String getTxtRestEndMinute3() {
		return txtRestEndMinute3;
	}
	
	/**
	 * @param txtRestEndMinute3 セットする txtRestEndMinute3
	 */
	public void setTxtRestEndMinute3(String txtRestEndMinute3) {
		this.txtRestEndMinute3 = txtRestEndMinute3;
	}
	
	/**
	 * @return txtRestStartHour4
	 */
	public String getTxtRestStartHour4() {
		return txtRestStartHour4;
	}
	
	/**
	 * @param txtRestStartHour4 セットする txtRestStartHour4
	 */
	public void setTxtRestStartHour4(String txtRestStartHour4) {
		this.txtRestStartHour4 = txtRestStartHour4;
	}
	
	/**
	 * @return txtRestStartMinute4
	 */
	public String getTxtRestStartMinute4() {
		return txtRestStartMinute4;
	}
	
	/**
	 * @param txtRestStartMinute4 セットする txtRestStartMinute4
	 */
	public void setTxtRestStartMinute4(String txtRestStartMinute4) {
		this.txtRestStartMinute4 = txtRestStartMinute4;
	}
	
	/**
	 * @return txtRestEndHour4
	 */
	public String getTxtRestEndHour4() {
		return txtRestEndHour4;
	}
	
	/**
	 * @param txtRestEndHour4 セットする txtRestEndHour4
	 */
	public void setTxtRestEndHour4(String txtRestEndHour4) {
		this.txtRestEndHour4 = txtRestEndHour4;
	}
	
	/**
	 * @return txtRestEndMinute4
	 */
	public String getTxtRestEndMinute4() {
		return txtRestEndMinute4;
	}
	
	/**
	 * @param txtRestEndMinute4 セットする txtRestEndMinute4
	 */
	public void setTxtRestEndMinute4(String txtRestEndMinute4) {
		this.txtRestEndMinute4 = txtRestEndMinute4;
	}
	
	/**
	 * @return txtRestStartHour5
	 */
	public String getTxtRestStartHour5() {
		return txtRestStartHour5;
	}
	
	/**
	 * @param txtRestStartHour5 セットする txtRestStartHour5
	 */
	public void setTxtRestStartHour5(String txtRestStartHour5) {
		this.txtRestStartHour5 = txtRestStartHour5;
	}
	
	/**
	 * @return txtRestStartMinute5
	 */
	public String getTxtRestStartMinute5() {
		return txtRestStartMinute5;
	}
	
	/**
	 * @param txtRestStartMinute5 セットする txtRestStartMinute5
	 */
	public void setTxtRestStartMinute5(String txtRestStartMinute5) {
		this.txtRestStartMinute5 = txtRestStartMinute5;
	}
	
	/**
	 * @return txtRestEndHour5
	 */
	public String getTxtRestEndHour5() {
		return txtRestEndHour5;
	}
	
	/**
	 * @param txtRestEndHour5 セットする txtRestEndHour5
	 */
	public void setTxtRestEndHour5(String txtRestEndHour5) {
		this.txtRestEndHour5 = txtRestEndHour5;
	}
	
	/**
	 * @return txtRestEndMinute5
	 */
	public String getTxtRestEndMinute5() {
		return txtRestEndMinute5;
	}
	
	/**
	 * @param txtRestEndMinute5 セットする txtRestEndMinute5
	 */
	public void setTxtRestEndMinute5(String txtRestEndMinute5) {
		this.txtRestEndMinute5 = txtRestEndMinute5;
	}
	
	/**
	 * @return txtRestStartHour6
	 */
	public String getTxtRestStartHour6() {
		return txtRestStartHour6;
	}
	
	/**
	 * @param txtRestStartHour6 セットする txtRestStartHour6
	 */
	public void setTxtRestStartHour6(String txtRestStartHour6) {
		this.txtRestStartHour6 = txtRestStartHour6;
	}
	
	/**
	 * @return txtRestStartMinute6
	 */
	public String getTxtRestStartMinute6() {
		return txtRestStartMinute6;
	}
	
	/**
	 * @param txtRestStartMinute6 セットする txtRestStartMinute6
	 */
	public void setTxtRestStartMinute6(String txtRestStartMinute6) {
		this.txtRestStartMinute6 = txtRestStartMinute6;
	}
	
	/**
	 * @return txtRestEndHour6
	 */
	public String getTxtRestEndHour6() {
		return txtRestEndHour6;
	}
	
	/**
	 * @param txtRestEndHour6 セットする txtRestEndHour6
	 */
	public void setTxtRestEndHour6(String txtRestEndHour6) {
		this.txtRestEndHour6 = txtRestEndHour6;
	}
	
	/**
	 * @return txtRestEndMinute6
	 */
	public String getTxtRestEndMinute6() {
		return txtRestEndMinute6;
	}
	
	/**
	 * @param txtRestEndMinute6 セットする txtRestEndMinute6
	 */
	public void setTxtRestEndMinute6(String txtRestEndMinute6) {
		this.txtRestEndMinute6 = txtRestEndMinute6;
	}
	
	/**
	 * @return txtPublicStartHour1
	 */
	public String getTxtPublicStartHour1() {
		return txtPublicStartHour1;
	}
	
	/**
	 * @param txtPublicStartHour1 セットする txtPublicStartHour1
	 */
	public void setTxtPublicStartHour1(String txtPublicStartHour1) {
		this.txtPublicStartHour1 = txtPublicStartHour1;
	}
	
	/**
	 * @return txtPublicStartMinute1
	 */
	public String getTxtPublicStartMinute1() {
		return txtPublicStartMinute1;
	}
	
	/**
	 * @param txtPublicStartMinute1 セットする txtPublicStartMinute1
	 */
	public void setTxtPublicStartMinute1(String txtPublicStartMinute1) {
		this.txtPublicStartMinute1 = txtPublicStartMinute1;
	}
	
	/**
	 * @return txtPublicEndHour1
	 */
	public String getTxtPublicEndHour1() {
		return txtPublicEndHour1;
	}
	
	/**
	 * @param txtPublicEndHour1 セットする txtPublicEndHour1
	 */
	public void setTxtPublicEndHour1(String txtPublicEndHour1) {
		this.txtPublicEndHour1 = txtPublicEndHour1;
	}
	
	/**
	 * @return txtPublicEndMinute1
	 */
	public String getTxtPublicEndMinute1() {
		return txtPublicEndMinute1;
	}
	
	/**
	 * @param txtPublicEndMinute1 セットする txtPublicEndMinute1
	 */
	public void setTxtPublicEndMinute1(String txtPublicEndMinute1) {
		this.txtPublicEndMinute1 = txtPublicEndMinute1;
	}
	
	/**
	 * @return txtPublicStartHour2
	 */
	public String getTxtPublicStartHour2() {
		return txtPublicStartHour2;
	}
	
	/**
	 * @param txtPublicStartHour2 セットする txtPublicStartHour2
	 */
	public void setTxtPublicStartHour2(String txtPublicStartHour2) {
		this.txtPublicStartHour2 = txtPublicStartHour2;
	}
	
	/**
	 * @return txtPublicStartMinute2
	 */
	public String getTxtPublicStartMinute2() {
		return txtPublicStartMinute2;
	}
	
	/**
	 * @param txtPublicStartMinute2 セットする txtPublicStartMinute2
	 */
	public void setTxtPublicStartMinute2(String txtPublicStartMinute2) {
		this.txtPublicStartMinute2 = txtPublicStartMinute2;
	}
	
	/**
	 * @return txtPublicEndHour2
	 */
	public String getTxtPublicEndHour2() {
		return txtPublicEndHour2;
	}
	
	/**
	 * @param txtPublicEndHour2 セットする txtPublicEndHour2
	 */
	public void setTxtPublicEndHour2(String txtPublicEndHour2) {
		this.txtPublicEndHour2 = txtPublicEndHour2;
	}
	
	/**
	 * @return txtPublicEndMinute2
	 */
	public String getTxtPublicEndMinute2() {
		return txtPublicEndMinute2;
	}
	
	/**
	 * @param txtPublicEndMinute2 セットする txtPublicEndMinute2
	 */
	public void setTxtPublicEndMinute2(String txtPublicEndMinute2) {
		this.txtPublicEndMinute2 = txtPublicEndMinute2;
	}
	
	/**
	 * @return txtPrivateStartHour1
	 */
	public String getTxtPrivateStartHour1() {
		return txtPrivateStartHour1;
	}
	
	/**
	 * @param txtPrivateStartHour1 セットする txtPrivateStartHour1
	 */
	public void setTxtPrivateStartHour1(String txtPrivateStartHour1) {
		this.txtPrivateStartHour1 = txtPrivateStartHour1;
	}
	
	/**
	 * @return txtPrivateStartMinute1
	 */
	public String getTxtPrivateStartMinute1() {
		return txtPrivateStartMinute1;
	}
	
	/**
	 * @param txtPrivateStartMinute1 セットする txtPrivateStartMinute1
	 */
	public void setTxtPrivateStartMinute1(String txtPrivateStartMinute1) {
		this.txtPrivateStartMinute1 = txtPrivateStartMinute1;
	}
	
	/**
	 * @return txtPrivateEndHour1
	 */
	public String getTxtPrivateEndHour1() {
		return txtPrivateEndHour1;
	}
	
	/**
	 * @param txtPrivateEndHour1 セットする txtPrivateEndHour1
	 */
	public void setTxtPrivateEndHour1(String txtPrivateEndHour1) {
		this.txtPrivateEndHour1 = txtPrivateEndHour1;
	}
	
	/**
	 * @return txtPrivateEndMinute1
	 */
	public String getTxtPrivateEndMinute1() {
		return txtPrivateEndMinute1;
	}
	
	/**
	 * @param txtPrivateEndMinute1 セットする txtPrivateEndMinute1
	 */
	public void setTxtPrivateEndMinute1(String txtPrivateEndMinute1) {
		this.txtPrivateEndMinute1 = txtPrivateEndMinute1;
	}
	
	/**
	 * @return txtPrivateStartHour2
	 */
	public String getTxtPrivateStartHour2() {
		return txtPrivateStartHour2;
	}
	
	/**
	 * @param txtPrivateStartHour2 セットする txtPrivateStartHour2
	 */
	public void setTxtPrivateStartHour2(String txtPrivateStartHour2) {
		this.txtPrivateStartHour2 = txtPrivateStartHour2;
	}
	
	/**
	 * @return txtPrivateStartMinute2
	 */
	public String getTxtPrivateStartMinute2() {
		return txtPrivateStartMinute2;
	}
	
	/**
	 * @param txtPrivateStartMinute2 セットする txtPrivateStartMinute2
	 */
	public void setTxtPrivateStartMinute2(String txtPrivateStartMinute2) {
		this.txtPrivateStartMinute2 = txtPrivateStartMinute2;
	}
	
	/**
	 * @return txtPrivateEndHour2
	 */
	public String getTxtPrivateEndHour2() {
		return txtPrivateEndHour2;
	}
	
	/**
	 * @param txtPrivateEndHour2 セットする txtPrivateEndHour2
	 */
	public void setTxtPrivateEndHour2(String txtPrivateEndHour2) {
		this.txtPrivateEndHour2 = txtPrivateEndHour2;
	}
	
	/**
	 * @return txtPrivateEndMinute2
	 */
	public String getTxtPrivateEndMinute2() {
		return txtPrivateEndMinute2;
	}
	
	/**
	 * @param txtPrivateEndMinute2 セットする txtPrivateEndMinute2
	 */
	public void setTxtPrivateEndMinute2(String txtPrivateEndMinute2) {
		this.txtPrivateEndMinute2 = txtPrivateEndMinute2;
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
	 * @return pltLateReason
	 */
	public String getPltLateReason() {
		return pltLateReason;
	}
	
	/**
	 * @param pltLateReason セットする pltLateReason
	 */
	public void setPltLateReason(String pltLateReason) {
		this.pltLateReason = pltLateReason;
	}
	
	/**
	 * @return pltLateCertificate
	 */
	public String getPltLateCertificate() {
		return pltLateCertificate;
	}
	
	/**
	 * @param pltLateCertificate セットする pltLateCertificate
	 */
	public void setPltLateCertificate(String pltLateCertificate) {
		this.pltLateCertificate = pltLateCertificate;
	}
	
	/**
	 * @return txtLateComment
	 */
	public String getTxtLateComment() {
		return txtLateComment;
	}
	
	/**
	 * @param txtLateComment セットする txtLateComment
	 */
	public void setTxtLateComment(String txtLateComment) {
		this.txtLateComment = txtLateComment;
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
	 * @return pltLeaveEarlyReason
	 */
	public String getPltLeaveEarlyReason() {
		return pltLeaveEarlyReason;
	}
	
	/**
	 * @param pltLeaveEarlyReason セットする pltLeaveEarlyReason
	 */
	public void setPltLeaveEarlyReason(String pltLeaveEarlyReason) {
		this.pltLeaveEarlyReason = pltLeaveEarlyReason;
	}
	
	/**
	 * @return pltLeaveEarlyCertificate
	 */
	public String getPltLeaveEarlyCertificate() {
		return pltLeaveEarlyCertificate;
	}
	
	/**
	 * @param pltLeaveEarlyCertificate セットする pltLeaveEarlyCertificate
	 */
	public void setPltLeaveEarlyCertificate(String pltLeaveEarlyCertificate) {
		this.pltLeaveEarlyCertificate = pltLeaveEarlyCertificate;
	}
	
	/**
	 * @return txtLeaveEarlyComment
	 */
	public String getTxtLeaveEarlyComment() {
		return txtLeaveEarlyComment;
	}
	
	/**
	 * @param txtLeaveEarlyComment セットする txtLeaveEarlyComment
	 */
	public void setTxtLeaveEarlyComment(String txtLeaveEarlyComment) {
		this.txtLeaveEarlyComment = txtLeaveEarlyComment;
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
	 * @return lblOvertimeIn
	 */
	public String getLblOvertimeIn() {
		return lblOvertimeIn;
	}
	
	/**
	 * @param lblOvertimeIn セットする lblOvertimeIn
	 */
	public void setLblOvertimeIn(String lblOvertimeIn) {
		this.lblOvertimeIn = lblOvertimeIn;
	}
	
	/**
	 * @return lblOvertimeOut
	 */
	public String getLblOvertimeOut() {
		return lblOvertimeOut;
	}
	
	/**
	 * @param lblOvertimeOut セットする lblOvertimeOut
	 */
	public void setLblOvertimeOut(String lblOvertimeOut) {
		this.lblOvertimeOut = lblOvertimeOut;
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
	 * @param lblHolidayWorkTime セットする lblHolidayWorkTime
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
	 * @return pltAllowance1
	 */
	public String getPltAllowance1() {
		return pltAllowance1;
	}
	
	/**
	 * @param pltAllowance1 セットする pltAllowance1
	 */
	public void setPltAllowance1(String pltAllowance1) {
		this.pltAllowance1 = pltAllowance1;
	}
	
	/**
	 * @return pltAllowance2
	 */
	public String getPltAllowance2() {
		return pltAllowance2;
	}
	
	/**
	 * @param pltAllowance2 セットする pltAllowance2
	 */
	public void setPltAllowance2(String pltAllowance2) {
		this.pltAllowance2 = pltAllowance2;
	}
	
	/**
	 * @return pltAllowance3
	 */
	public String getPltAllowance3() {
		return pltAllowance3;
	}
	
	/**
	 * @param pltAllowance3 セットする pltAllowance3
	 */
	public void setPltAllowance3(String pltAllowance3) {
		this.pltAllowance3 = pltAllowance3;
	}
	
	/**
	 * @return pltAllowance4
	 */
	public String getPltAllowance4() {
		return pltAllowance4;
	}
	
	/**
	 * @param pltAllowance4 セットする pltAllowance4
	 */
	public void setPltAllowance4(String pltAllowance4) {
		this.pltAllowance4 = pltAllowance4;
	}
	
	/**
	 * @return pltAllowance5
	 */
	public String getPltAllowance5() {
		return pltAllowance5;
	}
	
	/**
	 * @param pltAllowance5 セットする pltAllowance5
	 */
	public void setPltAllowance5(String pltAllowance5) {
		this.pltAllowance5 = pltAllowance5;
	}
	
	/**
	 * @return pltAllowance6
	 */
	public String getPltAllowance6() {
		return pltAllowance6;
	}
	
	/**
	 * @param pltAllowance6 セットする pltAllowance6
	 */
	public void setPltAllowance6(String pltAllowance6) {
		this.pltAllowance6 = pltAllowance6;
	}
	
	/**
	 * @return pltAllowance7
	 */
	public String getPltAllowance7() {
		return pltAllowance7;
	}
	
	/**
	 * @param pltAllowance7 セットする pltAllowance7
	 */
	public void setPltAllowance7(String pltAllowance7) {
		this.pltAllowance7 = pltAllowance7;
	}
	
	/**
	 * @return pltAllowance8
	 */
	public String getPltAllowance8() {
		return pltAllowance8;
	}
	
	/**
	 * @param pltAllowance8 セットする pltAllowance8
	 */
	public void setPltAllowance8(String pltAllowance8) {
		this.pltAllowance8 = pltAllowance8;
	}
	
	/**
	 * @return pltAllowance9
	 */
	public String getPltAllowance9() {
		return pltAllowance9;
	}
	
	/**
	 * @param pltAllowance9 セットする pltAllowance9
	 */
	public void setPltAllowance9(String pltAllowance9) {
		this.pltAllowance9 = pltAllowance9;
	}
	
	/**
	 * @return pltAllowance10
	 */
	public String getPltAllowance10() {
		return pltAllowance10;
	}
	
	/**
	 * @param pltAllowance10 セットする pltAllowance10
	 */
	public void setPltAllowance10(String pltAllowance10) {
		this.pltAllowance10 = pltAllowance10;
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
	 * @return lblOvertimeTransferParams
	 */
	public String getLblOvertimeTransferParams() {
		return lblOvertimeTransferParams;
	}
	
	/**
	 * @param lblOvertimeTransferParams セットする lblOvertimeTransferParams
	 */
	public void setLblOvertimeTransferParams(String lblOvertimeTransferParams) {
		this.lblOvertimeTransferParams = lblOvertimeTransferParams;
	}
	
	/**
	 * @return lblOvertimeCmd
	 */
	public String getLblOvertimeCmd() {
		return lblOvertimeCmd;
	}
	
	/**
	 * @param lblOvertimeCmd セットする lblOvertimeCmd
	 */
	public void setLblOvertimeCmd(String lblOvertimeCmd) {
		this.lblOvertimeCmd = lblOvertimeCmd;
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
	 * @return lblHolidayWorkType
	 */
	public String[] getLblHolidayWorkType() {
		return getStringArrayClone(lblHolidayWorkType);
	}
	
	/**
	 * @param lblHolidayWorkType セットする lblHolidayWorkType
	 */
	public void setLblHolidayWorkType(String[] lblHolidayWorkType) {
		this.lblHolidayWorkType = getStringArrayClone(lblHolidayWorkType);
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
	 * @return lblHolidayTransferParams
	 */
	public String getLblHolidayTransferParams() {
		return lblHolidayTransferParams;
	}
	
	/**
	 * @param lblHolidayTransferParams セットする lblHolidayTransferParams
	 */
	public void setLblHolidayTransferParams(String lblHolidayTransferParams) {
		this.lblHolidayTransferParams = lblHolidayTransferParams;
	}
	
	/**
	 * @return lblHolidayCmd
	 */
	public String getLblHolidayCmd() {
		return lblHolidayCmd;
	}
	
	/**
	 * @param lblHolidayCmd セットする lblHolidayCmd
	 */
	public void setLblHolidayCmd(String lblHolidayCmd) {
		this.lblHolidayCmd = lblHolidayCmd;
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
	 * @return lblSubStituteDate
	 */
	public String getLblSubStituteDate() {
		return lblSubStituteDate;
	}
	
	/**
	 * @param lblSubStituteDate セットする lblSubStituteDate
	 */
	public void setLblSubStituteDate(String lblSubStituteDate) {
		this.lblSubStituteDate = lblSubStituteDate;
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
	 * @return lblWorkOnHolidayTransferParams
	 */
	public String getLblWorkOnHolidayTransferParams() {
		return lblWorkOnHolidayTransferParams;
	}
	
	/**
	 * @param lblWorkOnHolidayTransferParams セットする lblWorkOnHolidayTransferParams
	 */
	public void setLblWorkOnHolidayTransferParams(String lblWorkOnHolidayTransferParams) {
		this.lblWorkOnHolidayTransferParams = lblWorkOnHolidayTransferParams;
	}
	
	/**
	 * @return lblWorkOnHolidayCmd
	 */
	public String getLblWorkOnHolidayCmd() {
		return lblWorkOnHolidayCmd;
	}
	
	/**
	 * @param lblWorkOnHolidayCmd セットする lblWorkOnHolidayCmd
	 */
	public void setLblWorkOnHolidayCmd(String lblWorkOnHolidayCmd) {
		this.lblWorkOnHolidayCmd = lblWorkOnHolidayCmd;
	}
	
	/**
	 * @return lblSubHolidayDate
	 */
	public String[] getLblSubHolidayDate() {
		return getStringArrayClone(lblSubHolidayDate);
	}
	
	/**
	 * @param lblSubHolidayDate セットする lblSubHolidayDate
	 */
	public void setLblSubHolidayDate(String[] lblSubHolidayDate) {
		this.lblSubHolidayDate = getStringArrayClone(lblSubHolidayDate);
	}
	
	/**
	 * @return lblSubHolidayLength
	 */
	public String[] getLblSubHolidayLength() {
		return getStringArrayClone(lblSubHolidayLength);
	}
	
	/**
	 * @param lblSubHolidayLength セットする lblSubHolidayLength
	 */
	public void setLblSubHolidayLength(String[] lblSubHolidayLength) {
		this.lblSubHolidayLength = getStringArrayClone(lblSubHolidayLength);
	}
	
	/**
	 * @return lblSubHolidayWorkDate
	 */
	public String[] getLblSubHolidayWorkDate() {
		return getStringArrayClone(lblSubHolidayWorkDate);
	}
	
	/**
	 * @param lblSubHolidayWorkDate セットする lblSubHolidayWorkDate
	 */
	public void setLblSubHolidayWorkDate(String[] lblSubHolidayWorkDate) {
		this.lblSubHolidayWorkDate = getStringArrayClone(lblSubHolidayWorkDate);
	}
	
	/**
	 * @return lblSubHolidayState
	 */
	public String[] getLblSubHolidayState() {
		return getStringArrayClone(lblSubHolidayState);
	}
	
	/**
	 * @param lblSubHolidayState セットする lblSubHolidayState
	 */
	public void setLblSubHolidayState(String[] lblSubHolidayState) {
		this.lblSubHolidayState = getStringArrayClone(lblSubHolidayState);
	}
	
	/**
	 * @return lblSubHolidayApprover
	 */
	public String[] getLblSubHolidayApprover() {
		return getStringArrayClone(lblSubHolidayApprover);
	}
	
	/**
	 * @param lblSubHolidayApprover セットする lblSubHolidayApprover
	 */
	public void setLblSubHolidayApprover(String[] lblSubHolidayApprover) {
		this.lblSubHolidayApprover = getStringArrayClone(lblSubHolidayApprover);
	}
	
	/**
	 * @return lblSubHolidayComment
	 */
	public String[] getLblSubHolidayComment() {
		return getStringArrayClone(lblSubHolidayComment);
	}
	
	/**
	 * @param lblSubHolidayComment セットする lblSubHolidayComment
	 */
	public void setLblSubHolidayComment(String[] lblSubHolidayComment) {
		this.lblSubHolidayComment = getStringArrayClone(lblSubHolidayComment);
	}
	
	/**
	 * @return lblSubHolidayTransferParams
	 */
	public String getLblSubHolidayTransferParams() {
		return lblSubHolidayTransferParams;
	}
	
	/**
	 * @param lblSubHolidayTransferParams セットする lblSubHolidayTransferParams
	 */
	public void setLblSubHolidayTransferParams(String lblSubHolidayTransferParams) {
		this.lblSubHolidayTransferParams = lblSubHolidayTransferParams;
	}
	
	/**
	 * @return lblSubHolidayCmd
	 */
	public String getLblSubHolidayCmd() {
		return lblSubHolidayCmd;
	}
	
	/**
	 * @param lblSubHolidayCmd セットする lblSubHolidayCmd
	 */
	public void setLblSubHolidayCmd(String lblSubHolidayCmd) {
		this.lblSubHolidayCmd = lblSubHolidayCmd;
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
	 * @return lblDifferenceTransferParams
	 */
	public String getLblDifferenceTransferParams() {
		return lblDifferenceTransferParams;
	}
	
	/**
	 * @param lblDifferenceTransferParams セットする lblDifferenceTransferParams
	 */
	public void setLblDifferenceTransferParams(String lblDifferenceTransferParams) {
		this.lblDifferenceTransferParams = lblDifferenceTransferParams;
	}
	
	/**
	 * @return lblDifferenceCmd
	 */
	public String getLblDifferenceCmd() {
		return lblDifferenceCmd;
	}
	
	/**
	 * @param lblDifferenceCmd セットする lblDifferenceCmd
	 */
	public void setLblDifferenceCmd(String lblDifferenceCmd) {
		this.lblDifferenceCmd = lblDifferenceCmd;
	}
	
	/**
	 * @return aryPltAllowance
	 */
	public String[][] getAryPltAllowance() {
		return getStringArrayClone(aryPltAllowance);
	}
	
	/**
	 * @param aryPltAllowance セットする aryPltAllowance
	 */
	public void setAryPltAllowance(String[][] aryPltAllowance) {
		this.aryPltAllowance = getStringArrayClone(aryPltAllowance);
	}
	
	/**
	 * @return aryPltWorkType
	 */
	public String[][] getAryPltWorkType() {
		return getStringArrayClone(aryPltWorkType);
	}
	
	/**
	 * @param aryPltWorkType セットする aryPltWorkType
	 */
	public void setAryPltWorkType(String[][] aryPltWorkType) {
		this.aryPltWorkType = getStringArrayClone(aryPltWorkType);
	}
	
	/**
	 * @return aryPltLblApproverSetting
	 */
	@Override
	public String[] getAryPltLblApproverSetting() {
		return getStringArrayClone(aryPltLblApproverSetting);
	}
	
	/**
	 * @param aryPltLblApproverSetting セットする aryPltLblApproverSetting
	 */
	@Override
	public void setAryPltLblApproverSetting(String[] aryPltLblApproverSetting) {
		this.aryPltLblApproverSetting = getStringArrayClone(aryPltLblApproverSetting);
	}
	
	/**
	 * @return aryPltApproverSetting
	 */
	@Override
	public String[] getAryPltApproverSetting() {
		return getStringArrayClone(aryPltApproverSetting);
	}
	
	/**
	 * @param aryPltApproverSetting セットする aryPltApproverSetting
	 */
	@Override
	public void setAryPltApproverSetting(String[] aryPltApproverSetting) {
		this.aryPltApproverSetting = getStringArrayClone(aryPltApproverSetting);
	}
	
	/**
	 * @return aryApproverInfo
	 */
	@Override
	public String[][][] getAryApproverInfo() {
		return getStringArrayClone(aryApproverInfo);
	}
	
	/**
	 * @param aryApproverInfo セットする aryApproverInfo
	 */
	@Override
	public void setAryApproverInfo(String[][][] aryApproverInfo) {
		this.aryApproverInfo = getStringArrayClone(aryApproverInfo);
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
	 * @return pltApproverSetting1
	 */
	@Override
	public String getPltApproverSetting1() {
		return pltApproverSetting1;
	}
	
	/**
	 * @param pltApproverSetting1 セットする pltApproverSetting1
	 */
	@Override
	public void setPltApproverSetting1(String pltApproverSetting1) {
		this.pltApproverSetting1 = pltApproverSetting1;
	}
	
	/**
	 * @return pltApproverSetting2
	 */
	@Override
	public String getPltApproverSetting2() {
		return pltApproverSetting2;
	}
	
	/**
	 * @param pltApproverSetting2 セットする pltApproverSetting2
	 */
	@Override
	public void setPltApproverSetting2(String pltApproverSetting2) {
		this.pltApproverSetting2 = pltApproverSetting2;
	}
	
	/**
	 * @return pltApproverSetting3
	 */
	@Override
	public String getPltApproverSetting3() {
		return pltApproverSetting3;
	}
	
	/**
	 * @param pltApproverSetting3 セットする pltApproverSetting3
	 */
	@Override
	public void setPltApproverSetting3(String pltApproverSetting3) {
		this.pltApproverSetting3 = pltApproverSetting3;
	}
	
	/**
	 * @return pltApproverSetting4
	 */
	@Override
	public String getPltApproverSetting4() {
		return pltApproverSetting4;
	}
	
	/**
	 * @param pltApproverSetting4 セットする pltApproverSetting4
	 */
	@Override
	public void setPltApproverSetting4(String pltApproverSetting4) {
		this.pltApproverSetting4 = pltApproverSetting4;
	}
	
	/**
	 * @return pltApproverSetting5
	 */
	@Override
	public String getPltApproverSetting5() {
		return pltApproverSetting5;
	}
	
	/**
	 * @param pltApproverSetting5 セットする pltApproverSetting5
	 */
	@Override
	public void setPltApproverSetting5(String pltApproverSetting5) {
		this.pltApproverSetting5 = pltApproverSetting5;
	}
	
	/**
	 * @return pltApproverSetting6
	 */
	@Override
	public String getPltApproverSetting6() {
		return pltApproverSetting6;
	}
	
	/**
	 * @param pltApproverSetting6 セットする pltApproverSetting6
	 */
	@Override
	public void setPltApproverSetting6(String pltApproverSetting6) {
		this.pltApproverSetting6 = pltApproverSetting6;
	}
	
	/**
	 * @return pltApproverSetting7
	 */
	@Override
	public String getPltApproverSetting7() {
		return pltApproverSetting7;
	}
	
	/**
	 * @param pltApproverSetting7 セットする pltApproverSetting7
	 */
	@Override
	public void setPltApproverSetting7(String pltApproverSetting7) {
		this.pltApproverSetting7 = pltApproverSetting7;
	}
	
	/**
	 * @return pltApproverSetting8
	 */
	@Override
	public String getPltApproverSetting8() {
		return pltApproverSetting8;
	}
	
	/**
	 * @param pltApproverSetting8 セットする pltApproverSetting8
	 */
	@Override
	public void setPltApproverSetting8(String pltApproverSetting8) {
		this.pltApproverSetting8 = pltApproverSetting8;
	}
	
	/**
	 * @return pltApproverSetting9
	 */
	@Override
	public String getPltApproverSetting9() {
		return pltApproverSetting9;
	}
	
	/**
	 * @param pltApproverSetting9 セットする pltApproverSetting9
	 */
	@Override
	public void setPltApproverSetting9(String pltApproverSetting9) {
		this.pltApproverSetting9 = pltApproverSetting9;
	}
	
	/**
	 * @return pltApproverSetting10
	 */
	@Override
	public String getPltApproverSetting10() {
		return pltApproverSetting10;
	}
	
	/**
	 * @param pltApproverSetting10 セットする pltApproverSetting10
	 */
	@Override
	public void setPltApproverSetting10(String pltApproverSetting10) {
		this.pltApproverSetting10 = pltApproverSetting10;
	}
	
	/**
	 * @return pltApproverSetting
	 */
	@Override
	public String[] getPltApproverSetting() {
		return getStringArrayClone(pltApproverSetting);
	}
	
	/**
	 * @param pltApproverSetting セットする pltApproverSetting
	 */
	@Override
	public void setPltApproverSetting(String[] pltApproverSetting) {
		this.pltApproverSetting = getStringArrayClone(pltApproverSetting);
	}
	
	/**
	 * @return lblGeneralWorkTime
	 */
	public String getLblGeneralWorkTime() {
		return lblGeneralWorkTime;
	}
	
	/**
	 * @param lblGeneralWorkTime セットする lblGeneralWorkTime
	 */
	public void setLblGeneralWorkTime(String lblGeneralWorkTime) {
		this.lblGeneralWorkTime = lblGeneralWorkTime;
	}
	
	/**
	 * @return tmdAttendanceId
	 */
	public String getTmdAttendanceId() {
		return tmdAttendanceId;
	}
	
	/**
	 * @param tmdAttendanceId セットする tmdAttendanceId
	 */
	public void setTmdAttendanceId(String tmdAttendanceId) {
		this.tmdAttendanceId = tmdAttendanceId;
	}
	
	/**
	 * @return aryPltLateReason
	 */
	public String[][] getAryPltLateReason() {
		return getStringArrayClone(aryPltLateReason);
	}
	
	/**
	 * @param aryPltLateReason セットする aryPltLateReason
	 */
	public void setAryPltLateReason(String[][] aryPltLateReason) {
		this.aryPltLateReason = getStringArrayClone(aryPltLateReason);
	}
	
	/**
	 * @return aryPltLeaveEarlyReason
	 */
	public String[][] getAryPltLeaveEarlyReason() {
		return getStringArrayClone(aryPltLeaveEarlyReason);
	}
	
	/**
	 * @param aryPltLeaveEarlyReason セットする aryPltLeaveEarlyReason
	 */
	public void setAryPltLeaveEarlyReason(String[][] aryPltLeaveEarlyReason) {
		this.aryPltLeaveEarlyReason = getStringArrayClone(aryPltLeaveEarlyReason);
	}
	
	/**
	 * @return aryPltLateCertificate
	 */
	public String[][] getAryPltLateCertificate() {
		return getStringArrayClone(aryPltLateCertificate);
	}
	
	/**
	 * @param aryPltLateCertificate セットする aryPltLateCertificate
	 */
	public void setAryPltLateCertificate(String[][] aryPltLateCertificate) {
		this.aryPltLateCertificate = getStringArrayClone(aryPltLateCertificate);
	}
	
	/**
	 * @param ckbExtraFiled セットする ckbExtraFiled
	 */
	public void setCkbExtraFiled(String ckbExtraFiled) {
		this.ckbExtraFiled = ckbExtraFiled;
	}
	
	/**
	 * @return ckbExtraFiled
	 */
	public String getCkbExtraFiled() {
		return ckbExtraFiled;
	}
	
	/**
	 * @return lblWorkDayOfWeekStyle
	 */
	public String getLblWorkDayOfWeekStyle() {
		return lblWorkDayOfWeekStyle;
	}
	
	/**
	 * @param lblWorkDayOfWeekStyle セットする lblWorkDayOfWeekStyle
	 */
	public void setLblWorkDayOfWeekStyle(String lblWorkDayOfWeekStyle) {
		this.lblWorkDayOfWeekStyle = lblWorkDayOfWeekStyle;
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
	
	/**
	 * @return addonPulldowns
	 */
	public Map<String, String[][]> getAddonPulldowns() {
		return addonPulldowns;
	}
	
	/**
	 * @param addonPulldowns セットする addonPulldowns
	 */
	public void setAddonPulldowns(Map<String, String[][]> addonPulldowns) {
		this.addonPulldowns = addonPulldowns;
	}
	
	/**
	 * 追加プルダウンを設定する。<br>
	 * @param key   キー
	 * @param value 値
	 */
	public void putAddonPulldown(String key, String[][] value) {
		addonPulldowns.put(key, value);
	}
	
	/**
	 * 追加プルダウンを取得する。<br>
	 * @param key キー
	 * @return 追加プルダウン
	 */
	public String[][] getAddonPulldown(String key) {
		return addonPulldowns.get(key);
	}
	
}
