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
package jp.mosp.time.input.vo;

import jp.mosp.time.base.TimeVo;

/**
 * 勤怠修正履歴参照の情報を格納する。
 */
public class AttendanceHistoryVo extends TimeVo {
	
	private static final long	serialVersionUID	= 6202066939776825388L;
	
	private String				lblAttendanceDate;
	private String				lblAttendanceWeek;
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
	
	private String[]			aryLblCorrectionNumber;
	private String[]			aryLblCorrectionDate;
	private String[]			aryLblCorrectionEmployee;
	private String[]			aryLblCorrectionType;
	private String[]			aryLblCorrectionBefore;
	private String[]			aryLblCorrectionAfter;
	private String[]			aryLblCorrectionComment;
	
	private String				lblTxtEmployeeCode;
	private String				lblTxtEmployeeName;
	private String				lblTxtSection;
	private String				attendanceDate;
	
	/**
	 * ワークフロー番号。<br>
	 */
	private long				workflow;
	
	
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
	 * @return lblAttendanceWeek
	 */
	public String getLblAttendanceWeek() {
		return lblAttendanceWeek;
	}
	
	/**
	 * @param lblAttendanceWeek セットする lblAttendanceWeek
	 */
	public void setLblAttendanceWeek(String lblAttendanceWeek) {
		this.lblAttendanceWeek = lblAttendanceWeek;
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
	 * @return aryLblCorrectionNumber
	 */
	public String[] getAryLblCorrectionNumber() {
		return getStringArrayClone(aryLblCorrectionNumber);
	}
	
	/**
	 * @return aryLblCorrectionDate
	 */
	public String[] getAryLblCorrectionDate() {
		return getStringArrayClone(aryLblCorrectionDate);
	}
	
	/**
	 * @return aryLblCorrectionEmployee
	 */
	public String[] getAryLblCorrectionEmployee() {
		return getStringArrayClone(aryLblCorrectionEmployee);
	}
	
	/**
	 * @return aryLblCorrectionType
	 */
	public String[] getAryLblCorrectionType() {
		return getStringArrayClone(aryLblCorrectionType);
	}
	
	/**
	 * @return aryLblCorrectionBefore
	 */
	public String[] getAryLblCorrectionBefore() {
		return getStringArrayClone(aryLblCorrectionBefore);
	}
	
	/**
	 * @return aryLblCorrectionAfter
	 */
	public String[] getAryLblCorrectionAfter() {
		return getStringArrayClone(aryLblCorrectionAfter);
	}
	
	/**
	 * @return aryLblCorrectionComment
	 */
	public String[] getAryLblCorrectionComment() {
		return getStringArrayClone(aryLblCorrectionComment);
	}
	
	/**
	 * @param aryLblCorrectionNumber セットする aryLblCorrectionNumber
	 */
	public void setAryLblCorrectionNumber(String[] aryLblCorrectionNumber) {
		this.aryLblCorrectionNumber = getStringArrayClone(aryLblCorrectionNumber);
	}
	
	/**
	 * @param aryLblCorrectionDate セットする aryLblCorrectionDate
	 */
	public void setAryLblCorrectionDate(String[] aryLblCorrectionDate) {
		this.aryLblCorrectionDate = getStringArrayClone(aryLblCorrectionDate);
	}
	
	/**
	 * @param aryLblCorrectionEmployee セットする aryLblCorrectionEmployee
	 */
	public void setAryLblCorrectionEmployee(String[] aryLblCorrectionEmployee) {
		this.aryLblCorrectionEmployee = getStringArrayClone(aryLblCorrectionEmployee);
	}
	
	/**
	 * @param aryLblCorrectionType セットする aryLblCorrectionType
	 */
	public void setAryLblCorrectionType(String[] aryLblCorrectionType) {
		this.aryLblCorrectionType = getStringArrayClone(aryLblCorrectionType);
	}
	
	/**
	 * @param aryLblCorrectionBefore セットする aryLblCorrectionBefore
	 */
	public void setAryLblCorrectionBefore(String[] aryLblCorrectionBefore) {
		this.aryLblCorrectionBefore = getStringArrayClone(aryLblCorrectionBefore);
	}
	
	/**
	 * @param aryLblCorrectionAfter セットする aryLblCorrectionAfter
	 */
	public void setAryLblCorrectionAfter(String[] aryLblCorrectionAfter) {
		this.aryLblCorrectionAfter = getStringArrayClone(aryLblCorrectionAfter);
	}
	
	/**
	 * @param aryLblCorrectionComment セットする aryLblCorrectionComment
	 */
	public void setAryLblCorrectionComment(String[] aryLblCorrectionComment) {
		this.aryLblCorrectionComment = getStringArrayClone(aryLblCorrectionComment);
	}
	
	/**
	 * @return lblTxtEmployeeCode
	 */
	public String getLblTxtEmployeeCode() {
		return lblTxtEmployeeCode;
	}
	
	/**
	 * @return lblTxtEmployeeName
	 */
	public String getLblTxtEmployeeName() {
		return lblTxtEmployeeName;
	}
	
	/**
	 * @return lblTxtPosition
	 */
	public String getLblTxtSection() {
		return lblTxtSection;
	}
	
	/**
	 * @param lblTxtEmployeeCode セットする lblTxtEmployeeCode
	 */
	public void setLblTxtEmployeeCode(String lblTxtEmployeeCode) {
		this.lblTxtEmployeeCode = lblTxtEmployeeCode;
	}
	
	/**
	 * @param lblTxtEmployeeName セットする lblTxtEmployeeName
	 */
	public void setLblTxtEmployeeName(String lblTxtEmployeeName) {
		this.lblTxtEmployeeName = lblTxtEmployeeName;
	}
	
	/**
	 * @param lblTxtSection セットする lblTxtSection
	 */
	public void setLblTxtSection(String lblTxtSection) {
		this.lblTxtSection = lblTxtSection;
	}
	
	/**
	 * @return attendanceDate
	 */
	public String getAttendanceDate() {
		return attendanceDate;
	}
	
	/**
	 * @param attendanceDate セットする attendanceDate
	 */
	public void setAttendanceDate(String attendanceDate) {
		this.attendanceDate = attendanceDate;
	}
	
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
	
}
