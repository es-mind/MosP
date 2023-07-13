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
package jp.mosp.time.dto.settings.impl;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.SubordinateListDtoInterface;

/**
 * 部下一覧DTO
 */
public class SubordinateListDto extends BaseDto implements SubordinateListDtoInterface {
	
	private static final long	serialVersionUID	= -4678694643567936891L;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 対象年。<br>
	 */
	private int					targetYear;
	
	/**
	 * 対象月。<br>
	 */
	private int					targetMonth;
	
	/**
	 * 社員コード。
	 */
	private String				employeeCode;
	/**
	 * 社員氏名(姓)。
	 */
	private String				lastName;
	/**
	 * 社員氏名(名)。
	 */
	private String				firstName;
	/**
	 * 所属コード。
	 */
	private String				sectionCode;
	/**
	 * 出勤日数。
	 */
	private Double				workDate;
	/**
	 * 勤務時間。
	 */
	private Integer				workTime;
	/**
	 * 休憩時間。
	 */
	private Integer				restTime;
	/**
	 * 私用外出時間。
	 */
	private Integer				privateTime;
	/**
	 * 遅刻時間。
	 */
	private Integer				lateTime;
	/**
	 * 早退時間。
	 */
	private Integer				leaveEarlyTime;
	/**
	 * 遅刻早退時間。
	 */
	private Integer				lateLeaveEarlyTime;
	/**
	 * 残業時間。
	 */
	private Integer				overTimeIn;
	/**
	 * 法定外残業時間。
	 */
	private Integer				overTimeOut;
	/**
	 * 法定外残業時間スタイル。
	 */
	private String				overtimeOutStyle;
	/**
	 * 休出時間。
	 */
	private Integer				workOnHolidayTime;
	/**
	 * 深夜時間。
	 */
	private Integer				lateNightTime;
	/**
	 * 有給休暇日数。
	 */
	private Double				paidHoliday;
	/**
	 * 有給休暇時間数。
	 */
	private int					paidHolidayHour;
	/**
	 * 特別休暇合計日数。
	 */
	private double				totalSpecialHoliday;
	/**
	 * その他休暇合計日数。
	 */
	private double				totalOtherHoliday;
	/**
	 * 代休日数。
	 */
	private double				timesCompensation;
	/**
	 * 休暇日数。
	 */
	private Double				allHoliday;
	/**
	 * 欠勤日数。
	 */
	private Double				absence;
	/**
	 * 出勤回数。
	 */
	private int					timesWork;
	/**
	 * 遅刻回数。
	 */
	private int					timesLate;
	/**
	 * 早退回数。
	 */
	private int					timesLeaveEarly;
	/**
	 * 残業回数。
	 */
	private int					timesOvertime;
	/**
	 * 休日出勤回数。
	 */
	private int					timesWorkingHoliday;
	/**
	 * 法定休日日数。
	 */
	private int					timesLegalHoliday;
	/**
	 * 所定休日日数。
	 */
	private int					timesSpecificHoliday;
	/**
	 * 振替休日日数。
	 */
	private double				timesHolidaySubstitute;
	/**
	 * 未承認有無。
	 */
	private String				approval;
	/**
	 * 未集計有無。
	 */
	private String				calc;
	/**
	 * 修正履歴。
	 */
	private String				correction;
	
	/**
	 * 締状態。
	 */
	private int					cutoffState;
	
	/**
	 * 未承認フラグ(true：未承認有、false：未承認無)。<br>
	 */
	private boolean				isApprovableExist;
	
	/**
	 * 未申請フラグ(true：勤怠未申請有、false：勤怠未申請無)。<br>
	 */
	private boolean				isAppliableExist;
	
	/**
	 * 締状態表示クラス。
	 */
	private String				cutoffStateClass;
	
	/**
	 * 承認状態表示クラス。
	 */
	private String				approvalStateClass;
	
	
	@Override
	public String getApproval() {
		return approval;
	}
	
	@Override
	public String getCalc() {
		return calc;
	}
	
	@Override
	public String getCorrection() {
		return correction;
	}
	
	@Override
	public Integer getLateNightTime() {
		return lateNightTime;
	}
	
	@Override
	public Integer getLateTime() {
		return lateTime;
	}
	
	@Override
	public Integer getLeaveEarlyTime() {
		return leaveEarlyTime;
	}
	
	@Override
	public Integer getLateLeaveEarlyTime() {
		return lateLeaveEarlyTime;
	}
	
	@Override
	public Integer getOverTimeIn() {
		return overTimeIn;
	}
	
	@Override
	public Integer getOverTimeOut() {
		return overTimeOut;
	}
	
	@Override
	public String getOvertimeOutStyle() {
		return overtimeOutStyle;
	}
	
	@Override
	public Double getPaidHoliday() {
		return paidHoliday;
	}
	
	@Override
	public int getPaidHolidayHour() {
		return paidHolidayHour;
	}
	
	@Override
	public double getTotalSpecialHoliday() {
		return totalSpecialHoliday;
	}
	
	@Override
	public double getTotalOtherHoliday() {
		return totalOtherHoliday;
	}
	
	@Override
	public double getTimesCompensation() {
		return timesCompensation;
	}
	
	@Override
	public Double getAllHoliday() {
		return allHoliday;
	}
	
	@Override
	public Double getAbsence() {
		return absence;
	}
	
	@Override
	public int getTimesWork() {
		return timesWork;
	}
	
	@Override
	public int getTimesLate() {
		return timesLate;
	}
	
	@Override
	public int getTimesLeaveEarly() {
		return timesLeaveEarly;
	}
	
	@Override
	public int getTimesOvertime() {
		return timesOvertime;
	}
	
	@Override
	public int getTimesWorkingHoliday() {
		return timesWorkingHoliday;
	}
	
	@Override
	public int getTimesLegalHoliday() {
		return timesLegalHoliday;
	}
	
	@Override
	public int getTimesSpecificHoliday() {
		return timesSpecificHoliday;
	}
	
	@Override
	public double getTimesHolidaySubstitute() {
		return timesHolidaySubstitute;
	}
	
	@Override
	public Integer getRestTime() {
		return restTime;
	}
	
	@Override
	public Integer getPrivateTime() {
		return privateTime;
	}
	
	@Override
	public Double getWorkDate() {
		return workDate;
	}
	
	@Override
	public Integer getWorkOnHolidayTime() {
		return workOnHolidayTime;
	}
	
	@Override
	public Integer getWorkTime() {
		return workTime;
	}
	
	@Override
	public void setApproval(String approval) {
		this.approval = approval;
	}
	
	@Override
	public void setCalc(String calc) {
		this.calc = calc;
	}
	
	@Override
	public void setCorrection(String correction) {
		this.correction = correction;
	}
	
	@Override
	public void setLateNightTime(Integer lateNightTime) {
		this.lateNightTime = lateNightTime;
	}
	
	@Override
	public void setLateTime(Integer lateTime) {
		this.lateTime = lateTime;
	}
	
	@Override
	public void setLeaveEarlyTime(Integer leaveEarlyTime) {
		this.leaveEarlyTime = leaveEarlyTime;
	}
	
	@Override
	public void setLateLeaveEarlyTime(Integer lateLeaveEarlyTime) {
		this.lateLeaveEarlyTime = lateLeaveEarlyTime;
	}
	
	@Override
	public void setOverTimeIn(Integer overTimeIn) {
		this.overTimeIn = overTimeIn;
	}
	
	@Override
	public void setOverTimeOut(Integer overTimeOut) {
		this.overTimeOut = overTimeOut;
	}
	
	@Override
	public void setOvertimeOutStyle(String overtimeOutStyle) {
		this.overtimeOutStyle = overtimeOutStyle;
	}
	
	@Override
	public void setPaidHoliday(Double paidHoliday) {
		this.paidHoliday = paidHoliday;
	}
	
	@Override
	public void setPaidHolidayHour(int paidHolidayHour) {
		this.paidHolidayHour = paidHolidayHour;
	}
	
	@Override
	public void setTotalSpecialHoliday(double totalSpecialHoliday) {
		this.totalSpecialHoliday = totalSpecialHoliday;
	}
	
	@Override
	public void setTotalOtherHoliday(double totalOtherHoliday) {
		this.totalOtherHoliday = totalOtherHoliday;
	}
	
	@Override
	public void setTimesCompensation(double timesCompensation) {
		this.timesCompensation = timesCompensation;
	}
	
	@Override
	public void setAllHoliday(Double allHoliday) {
		this.allHoliday = allHoliday;
	}
	
	@Override
	public void setAbsence(Double absence) {
		this.absence = absence;
	}
	
	@Override
	public void setTimesWork(int timesWork) {
		this.timesWork = timesWork;
	}
	
	@Override
	public void setTimesLate(int timesLate) {
		this.timesLate = timesLate;
	}
	
	@Override
	public void setTimesLeaveEarly(int timesLeaveEarly) {
		this.timesLeaveEarly = timesLeaveEarly;
	}
	
	@Override
	public void setTimesOvertime(int timesOvertime) {
		this.timesOvertime = timesOvertime;
	}
	
	@Override
	public void setTimesWorkingHoliday(int timesWorkingHoliday) {
		this.timesWorkingHoliday = timesWorkingHoliday;
	}
	
	@Override
	public void setTimesLegalHoliday(int timesLegalHoliday) {
		this.timesLegalHoliday = timesLegalHoliday;
	}
	
	@Override
	public void setTimesSpecificHoliday(int timesSpecificHoliday) {
		this.timesSpecificHoliday = timesSpecificHoliday;
	}
	
	@Override
	public void setTimesHolidaySubstitute(double timesHolidaySubstitute) {
		this.timesHolidaySubstitute = timesHolidaySubstitute;
	}
	
	@Override
	public void setRestTime(Integer restTime) {
		this.restTime = restTime;
	}
	
	@Override
	public void setPrivateTime(Integer privateTime) {
		this.privateTime = privateTime;
	}
	
	@Override
	public void setWorkDate(Double workDate) {
		this.workDate = workDate;
	}
	
	@Override
	public void setWorkOnHolidayTime(Integer workOnHolidayTime) {
		this.workOnHolidayTime = workOnHolidayTime;
	}
	
	@Override
	public void setWorkTime(Integer workTime) {
		this.workTime = workTime;
	}
	
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public int getTargetYear() {
		return targetYear;
	}
	
	@Override
	public void setTargetYear(int targetYear) {
		this.targetYear = targetYear;
	}
	
	@Override
	public int getTargetMonth() {
		return targetMonth;
	}
	
	@Override
	public void setTargetMonth(int targetMonth) {
		this.targetMonth = targetMonth;
	}
	
	@Override
	public int getCutoffState() {
		return cutoffState;
	}
	
	@Override
	public void setCutoffState(int cutoffState) {
		this.cutoffState = cutoffState;
		
	}
	
	@Override
	public boolean isApprovableExist() {
		return isApprovableExist;
	}
	
	@Override
	public void setApprovableExist(boolean isApprovableExist) {
		this.isApprovableExist = isApprovableExist;
	}
	
	@Override
	public boolean isAppliableExist() {
		return isAppliableExist;
	}
	
	@Override
	public void setAppliableExist(boolean isAppliableExist) {
		this.isAppliableExist = isAppliableExist;
	}
	
	@Override
	public String getApprovalStateClass() {
		return approvalStateClass;
	}
	
	@Override
	public String getCutoffStateClass() {
		return cutoffStateClass;
	}
	
	@Override
	public void setApprovalStateClass(String approvalStateClass) {
		this.approvalStateClass = approvalStateClass;
		
	}
	
	@Override
	public void setCutoffStateClass(String cutoffStateClass) {
		this.cutoffStateClass = cutoffStateClass;
	}
	
}
