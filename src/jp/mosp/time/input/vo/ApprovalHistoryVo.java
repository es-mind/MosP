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

import jp.mosp.time.base.TimeVo;

/**
 * 承認履歴参照情報を格納する。
 */
public class ApprovalHistoryVo extends TimeVo {
	
	private static final long	serialVersionUID	= 5447755835420607098L;
	
	/**
	 * ワークフロー番号。<br>
	 */
	private long				workflow;
	
	private String				lblAttendanceDate;
	private String				lblAttendanceWorkType;
	private String				lblAttendanceStartDate;
	private String				lblAttendanceEndDate;
	private String				lblAttendanceWorkTime;
	private String				lblAttendanceRestTime;
	private String				lblAttendancePrivateTime;
	private String				lblAttendanceLateTime;
	private String				lblAttendanceLeaveEarly;
	private String				lblAttendanceLateLeaveEarly;
	private String				lblAttendanceOverTimeIn;
	private String				lblAttendanceOverTimeOut;
	private String				lblAttendanceWorkOnHoliday;
	private String				lblAttendanceLateNight;
	private String				lblAttendanceState;
	private String				lblAttendanceRemark;
	private String				lblAttendanceCorrection;
	
	private String				lblOverTimeDate;
	private String				lblOverTimeType;
	private String				lblOverTimeSchedule;
	private String				lblOverTimeResultTime;
	private String				lblOverTimeRequestReason;
	private String				lblOverTimeState;
	private String				lblOverTimeApprover;
	
	private String				lblHolidayDate;
	private String				lblHolidayType1;
	private String				lblHolidayType2;
	private String				lblHolidayLength;
	private String				lblHolidayRequestReason;
	private String				lblHolidayState;
	private String				lblHolidayApprover;
	
	private String				lblWorkOnHolidayWorkDate;
	private String				lblWorkOnHolidayTime;
	private String				lblWorkOnHolidayReason;
	private String				lblWorkOnHolidayDate1;
	private String				lblWorkOnHolidayDate2;
	private String				lblWorkOnHolidayState;
	private String				lblWorkOnHolidayApprover;
	
	private String				lblSubHolidayDate;
	private String				lblSubHolidayWorkDate;
	private String				lblSubHolidayState;
	private String				lblSubHolidayComment;
	private String				lblSubHolidayApprover;
	
	private String				lblWorkTypeChangeDate;
	private String				lblWorkTypeChangeWorkType;
	private String				lblWorkTypeChangeReason;
	private String				lblWorkTypeChangeState;
	private String				lblWorkTypeChangeApprover;
	
	private String				lblDifferenceDate;
	private String				lblDifferenceType;
	private String				lblDifferenceTime;
	private String				lblDifferenceReason;
	private String				lblDifferenceState;
	private String				lblDifferenceApprover;
	
	private String[]			lblApprovalState;
	private String[]			lblApprovalResult;
	private String[]			lblApprovalApprover;
	private String[]			lblApprovalComment;
	private String[]			lblApprovalDate;
	
	
	/**
	 * @return workflow
	 */
	public long getWorkflow() {
		return workflow;
	}
	
	/**
	 * @param workflow セットする workflow
	 */
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
	/**
	 * @return lblAttendanceDate
	 */
	public String getLblAttendanceDate() {
		return lblAttendanceDate;
	}
	
	/**
	 * @param lblAttendanceDate セットする lblAttendanceDate
	 */
	public void setLblAttendanceDate(String lblAttendanceDate) {
		this.lblAttendanceDate = lblAttendanceDate;
	}
	
	/**
	 * @return lblAttendanceWorkType
	 */
	public String getLblAttendanceWorkType() {
		return lblAttendanceWorkType;
	}
	
	/**
	 * @param lblAttendanceWorkType セットする lblAttendanceWorkType
	 */
	public void setLblAttendanceWorkType(String lblAttendanceWorkType) {
		this.lblAttendanceWorkType = lblAttendanceWorkType;
	}
	
	/**
	 * @return lblAttendanceStartDate
	 */
	public String getLblAttendanceStartDate() {
		return lblAttendanceStartDate;
	}
	
	/**
	 * @param lblAttendanceStartDate セットする lblAttendanceStartDate
	 */
	public void setLblAttendanceStartDate(String lblAttendanceStartDate) {
		this.lblAttendanceStartDate = lblAttendanceStartDate;
	}
	
	/**
	 * @return lblAttendanceEndDate
	 */
	public String getLblAttendanceEndDate() {
		return lblAttendanceEndDate;
	}
	
	/**
	 * @param lblAttendanceEndDate セットする lblAttendanceEndDate
	 */
	public void setLblAttendanceEndDate(String lblAttendanceEndDate) {
		this.lblAttendanceEndDate = lblAttendanceEndDate;
	}
	
	/**
	 * @return lblAttendanceWorkTime
	 */
	public String getLblAttendanceWorkTime() {
		return lblAttendanceWorkTime;
	}
	
	/**
	 * @param lblAttendanceWorkTime セットする lblAttendanceWorkTime
	 */
	public void setLblAttendanceWorkTime(String lblAttendanceWorkTime) {
		this.lblAttendanceWorkTime = lblAttendanceWorkTime;
	}
	
	/**
	 * @return lblAttendanceRestTime
	 */
	public String getLblAttendanceRestTime() {
		return lblAttendanceRestTime;
	}
	
	/**
	 * @param lblAttendanceRestTime セットする lblAttendanceRestTime
	 */
	public void setLblAttendanceRestTime(String lblAttendanceRestTime) {
		this.lblAttendanceRestTime = lblAttendanceRestTime;
	}
	
	/**
	 * @return lblAttendancePrivateTime
	 */
	public String getLblAttendancePrivateTime() {
		return lblAttendancePrivateTime;
	}
	
	/**
	 * @param lblAttendancePrivateTime セットする lblAttendancePrivateTime
	 */
	public void setLblAttendancePrivateTime(String lblAttendancePrivateTime) {
		this.lblAttendancePrivateTime = lblAttendancePrivateTime;
	}
	
	/**
	 * @return lblAttendanceLateTime
	 */
	public String getLblAttendanceLateTime() {
		return lblAttendanceLateTime;
	}
	
	/**
	 * @param lblAttendanceLateTime セットする lblAttendanceLateTime
	 */
	public void setLblAttendanceLateTime(String lblAttendanceLateTime) {
		this.lblAttendanceLateTime = lblAttendanceLateTime;
	}
	
	/**
	 * @return lblAttendanceLeaveEarly
	 */
	public String getLblAttendanceLeaveEarly() {
		return lblAttendanceLeaveEarly;
	}
	
	/**
	 * @param lblAttendanceLeaveEarly セットする lblAttendanceLeaveEarly
	 */
	public void setLblAttendanceLeaveEarly(String lblAttendanceLeaveEarly) {
		this.lblAttendanceLeaveEarly = lblAttendanceLeaveEarly;
	}
	
	/**
	 * @return lblAttendanceLateLeaveEarly
	 */
	public String getLblAttendanceLateLeaveEarly() {
		return lblAttendanceLateLeaveEarly;
	}
	
	/**
	 * @param lblAttendanceLateLeaveEarly セットする lblAttendanceLateLeaveEarly
	 */
	public void setLblAttendanceLateLeaveEarly(String lblAttendanceLateLeaveEarly) {
		this.lblAttendanceLateLeaveEarly = lblAttendanceLateLeaveEarly;
	}
	
	/**
	 * @return lblAttendanceOverTimeIn
	 */
	public String getLblAttendanceOverTimeIn() {
		return lblAttendanceOverTimeIn;
	}
	
	/**
	 * @param lblAttendanceOverTimeIn セットする lblAttendanceOverTimeIn
	 */
	public void setLblAttendanceOverTimeIn(String lblAttendanceOverTimeIn) {
		this.lblAttendanceOverTimeIn = lblAttendanceOverTimeIn;
	}
	
	/**
	 * @return lblAttendanceOverTimeOut
	 */
	public String getLblAttendanceOverTimeOut() {
		return lblAttendanceOverTimeOut;
	}
	
	/**
	 * @param lblAttendanceOverTimeOut セットする lblAttendanceOverTimeOut
	 */
	public void setLblAttendanceOverTimeOut(String lblAttendanceOverTimeOut) {
		this.lblAttendanceOverTimeOut = lblAttendanceOverTimeOut;
	}
	
	/**
	 * @return lblAttendanceWorkOnHoliday
	 */
	public String getLblAttendanceWorkOnHoliday() {
		return lblAttendanceWorkOnHoliday;
	}
	
	/**
	 * @param lblAttendanceWorkOnHoliday セットする lblAttendanceWorkOnHoliday
	 */
	public void setLblAttendanceWorkOnHoliday(String lblAttendanceWorkOnHoliday) {
		this.lblAttendanceWorkOnHoliday = lblAttendanceWorkOnHoliday;
	}
	
	/**
	 * @return lblAttendanceLateNight
	 */
	public String getLblAttendanceLateNight() {
		return lblAttendanceLateNight;
	}
	
	/**
	 * @param lblAttendanceLateNight セットする lblAttendanceLateNight
	 */
	public void setLblAttendanceLateNight(String lblAttendanceLateNight) {
		this.lblAttendanceLateNight = lblAttendanceLateNight;
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
	 * @return lblAttendanceRemark
	 */
	public String getLblAttendanceRemark() {
		return lblAttendanceRemark;
	}
	
	/**
	 * @param lblAttendanceRemark セットする lblAttendanceRemark
	 */
	public void setLblAttendanceRemark(String lblAttendanceRemark) {
		this.lblAttendanceRemark = lblAttendanceRemark;
	}
	
	/**
	 * @return lblAttendanceCorrection
	 */
	public String getLblAttendanceCorrection() {
		return lblAttendanceCorrection;
	}
	
	/**
	 * @param lblAttendanceCorrection セットする lblAttendanceCorrection
	 */
	public void setLblAttendanceCorrection(String lblAttendanceCorrection) {
		this.lblAttendanceCorrection = lblAttendanceCorrection;
	}
	
	/**
	 * @return lblOverTimeDate
	 */
	public String getLblOverTimeDate() {
		return lblOverTimeDate;
	}
	
	/**
	 * @param lblOverTimeDate セットする lblOverTimeDate
	 */
	public void setLblOverTimeDate(String lblOverTimeDate) {
		this.lblOverTimeDate = lblOverTimeDate;
	}
	
	/**
	 * @return lblOverTimeType
	 */
	public String getLblOverTimeType() {
		return lblOverTimeType;
	}
	
	/**
	 * @param lblOverTimeType セットする lblOverTimeType
	 */
	public void setLblOverTimeType(String lblOverTimeType) {
		this.lblOverTimeType = lblOverTimeType;
	}
	
	/**
	 * @return lblOverTimeSchedule
	 */
	public String getLblOverTimeSchedule() {
		return lblOverTimeSchedule;
	}
	
	/**
	 * @param lblOverTimeSchedule セットする lblOverTimeSchedule
	 */
	public void setLblOverTimeSchedule(String lblOverTimeSchedule) {
		this.lblOverTimeSchedule = lblOverTimeSchedule;
	}
	
	/**
	 * @return lblOverTimeResultTime
	 */
	public String getLblOverTimeResultTime() {
		return lblOverTimeResultTime;
	}
	
	/**
	 * @param lblOverTimeResultTime セットする lblOverTimeResultTime
	 */
	public void setLblOverTimeResultTime(String lblOverTimeResultTime) {
		this.lblOverTimeResultTime = lblOverTimeResultTime;
	}
	
	/**
	 * @return lblOverTimeRequestReason
	 */
	public String getLblOverTimeRequestReason() {
		return lblOverTimeRequestReason;
	}
	
	/**
	 * @param lblOverTimeRequestReason セットする lblOverTimeRequestReason
	 */
	public void setLblOverTimeRequestReason(String lblOverTimeRequestReason) {
		this.lblOverTimeRequestReason = lblOverTimeRequestReason;
	}
	
	/**
	 * @return lblOverTimeState
	 */
	public String getLblOverTimeState() {
		return lblOverTimeState;
	}
	
	/**
	 * @param lblOverTimeState セットする lblOverTimeState
	 */
	public void setLblOverTimeState(String lblOverTimeState) {
		this.lblOverTimeState = lblOverTimeState;
	}
	
	/**
	 * @return lblOverTimeApprover
	 */
	public String getLblOverTimeApprover() {
		return lblOverTimeApprover;
	}
	
	/**
	 * @param lblOverTimeApprover セットする lblOverTimeApprover
	 */
	public void setLblOverTimeApprover(String lblOverTimeApprover) {
		this.lblOverTimeApprover = lblOverTimeApprover;
	}
	
	/**
	 * @return lblHolidayDate
	 */
	public String getLblHolidayDate() {
		return lblHolidayDate;
	}
	
	/**
	 * @param lblHolidayDate セットする lblHolidayDate
	 */
	public void setLblHolidayDate(String lblHolidayDate) {
		this.lblHolidayDate = lblHolidayDate;
	}
	
	/**
	 * @return lblHolidayType1
	 */
	public String getLblHolidayType1() {
		return lblHolidayType1;
	}
	
	/**
	 * @param lblHolidayType1 セットする lblHolidayType1
	 */
	public void setLblHolidayType1(String lblHolidayType1) {
		this.lblHolidayType1 = lblHolidayType1;
	}
	
	/**
	 * @return lblHolidayType2
	 */
	public String getLblHolidayType2() {
		return lblHolidayType2;
	}
	
	/**
	 * @param lblHolidayType2 セットする lblHolidayType2
	 */
	public void setLblHolidayType2(String lblHolidayType2) {
		this.lblHolidayType2 = lblHolidayType2;
	}
	
	/**
	 * @return lblHolidayLength
	 */
	public String getLblHolidayLength() {
		return lblHolidayLength;
	}
	
	/**
	 * @param lblHolidayLength セットする lblHolidayLength
	 */
	public void setLblHolidayLength(String lblHolidayLength) {
		this.lblHolidayLength = lblHolidayLength;
	}
	
	/**
	 * @return lblHolidayRequestReason
	 */
	public String getLblHolidayRequestReason() {
		return lblHolidayRequestReason;
	}
	
	/**
	 * @param lblHolidayRequestReason セットする lblHolidayRequestReason
	 */
	public void setLblHolidayRequestReason(String lblHolidayRequestReason) {
		this.lblHolidayRequestReason = lblHolidayRequestReason;
	}
	
	/**
	 * @return lblHolidayState
	 */
	public String getLblHolidayState() {
		return lblHolidayState;
	}
	
	/**
	 * @param lblHolidayState セットする lblHolidayState
	 */
	public void setLblHolidayState(String lblHolidayState) {
		this.lblHolidayState = lblHolidayState;
	}
	
	/**
	 * @return lblHolidayApprover
	 */
	public String getLblHolidayApprover() {
		return lblHolidayApprover;
	}
	
	/**
	 * @param lblHolidayApprover セットする lblHolidayApprover
	 */
	public void setLblHolidayApprover(String lblHolidayApprover) {
		this.lblHolidayApprover = lblHolidayApprover;
	}
	
	/**
	 * @return lblWorkOnHolidayWorkDate
	 */
	public String getLblWorkOnHolidayWorkDate() {
		return lblWorkOnHolidayWorkDate;
	}
	
	/**
	 * @param lblWorkOnHolidayWorkDate セットする lblWorkOnHolidayWorkDate
	 */
	public void setLblWorkOnHolidayWorkDate(String lblWorkOnHolidayWorkDate) {
		this.lblWorkOnHolidayWorkDate = lblWorkOnHolidayWorkDate;
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
	 * @return lblWorkOnHolidayDate1
	 */
	public String getLblWorkOnHolidayDate1() {
		return lblWorkOnHolidayDate1;
	}
	
	/**
	 * @param lblWorkOnHolidayDate1 セットする lblWorkOnHolidayDate1
	 */
	public void setLblWorkOnHolidayDate1(String lblWorkOnHolidayDate1) {
		this.lblWorkOnHolidayDate1 = lblWorkOnHolidayDate1;
	}
	
	/**
	 * @return lblWorkOnHolidayDate2
	 */
	public String getLblWorkOnHolidayDate2() {
		return lblWorkOnHolidayDate2;
	}
	
	/**
	 * @param lblWorkOnHolidayDate2 セットする lblWorkOnHolidayDate2
	 */
	public void setLblWorkOnHolidayDate2(String lblWorkOnHolidayDate2) {
		this.lblWorkOnHolidayDate2 = lblWorkOnHolidayDate2;
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
	 * @return lblWorkTypeChangeWorkType
	 */
	public String getLblWorkTypeChangeWorkType() {
		return lblWorkTypeChangeWorkType;
	}
	
	/**
	 * @param lblWorkTypeChangeWorkType セットする lblWorkTypeChangeWorkType
	 */
	public void setLblWorkTypeChangeWorkType(String lblWorkTypeChangeWorkType) {
		this.lblWorkTypeChangeWorkType = lblWorkTypeChangeWorkType;
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
	 * @return lblDifferenceType
	 */
	public String getLblDifferenceType() {
		return lblDifferenceType;
	}
	
	/**
	 * @param lblDifferenceType セットする lblDifferenceType
	 */
	public void setLblDifferenceType(String lblDifferenceType) {
		this.lblDifferenceType = lblDifferenceType;
	}
	
	/**
	 * @return lblDifferenceTime
	 */
	public String getLblDifferenceTime() {
		return lblDifferenceTime;
	}
	
	/**
	 * @param lblDifferenceTime セットする lblDifferenceTime
	 */
	public void setLblDifferenceTime(String lblDifferenceTime) {
		this.lblDifferenceTime = lblDifferenceTime;
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
	 * @return lblApprovalState
	 */
	public String[] getLblApprovalState() {
		return getStringArrayClone(lblApprovalState);
	}
	
	/**
	 * @param lblApprovalState セットする lblApprovalState
	 */
	public void setLblApprovalState(String[] lblApprovalState) {
		this.lblApprovalState = getStringArrayClone(lblApprovalState);
	}
	
	/**
	 * @return lblApprovalResult
	 */
	public String[] getLblApprovalResult() {
		return getStringArrayClone(lblApprovalResult);
	}
	
	/**
	 * @param lblApprovalResult セットする lblApprovalResult
	 */
	public void setLblApprovalResult(String[] lblApprovalResult) {
		this.lblApprovalResult = getStringArrayClone(lblApprovalResult);
	}
	
	/**
	 * @return lblApprovalApprover
	 */
	public String[] getLblApprovalApprover() {
		return getStringArrayClone(lblApprovalApprover);
	}
	
	/**
	 * @param lblApprovalApprover セットする lblApprovalApprover
	 */
	public void setLblApprovalApprover(String[] lblApprovalApprover) {
		this.lblApprovalApprover = getStringArrayClone(lblApprovalApprover);
	}
	
	/**
	 * @return lblApprovalComment
	 */
	public String[] getLblApprovalComment() {
		return getStringArrayClone(lblApprovalComment);
	}
	
	/**
	 * @param lblApprovalComment セットする lblApprovalComment
	 */
	public void setLblApprovalComment(String[] lblApprovalComment) {
		this.lblApprovalComment = getStringArrayClone(lblApprovalComment);
	}
	
	/**
	 * @return lblApprovalDate
	 */
	public String[] getLblApprovalDate() {
		return getStringArrayClone(lblApprovalDate);
	}
	
	/**
	 * @param lblApprovalDate セットする lblApprovalDate
	 */
	public void setLblApprovalDate(String[] lblApprovalDate) {
		this.lblApprovalDate = getStringArrayClone(lblApprovalDate);
	}
	
}
