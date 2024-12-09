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
package jp.mosp.time.dto.settings;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;

/**
 * 部下一覧DTOインターフェース
 */
public interface SubordinateListDtoInterface extends PersonalIdDtoInterface, EmployeeCodeDtoInterface,
		EmployeeNameDtoInterface, SectionCodeDtoInterface, BaseDtoInterface {
	
	/**
	 * @return 対象年
	 */
	int getTargetYear();
	
	/**
	 * @return 対象月
	 */
	int getTargetMonth();
	
	/**
	 * @return 出勤日数。
	 */
	Double getWorkDate();
	
	/**
	 * @return 勤務時間。
	 */
	Integer getWorkTime();
	
	/**
	 * @return 休憩時間。
	 */
	Integer getRestTime();
	
	/**
	 * @return 私用外出時間。
	 */
	Integer getPrivateTime();
	
	/**
	 * @return 遅刻時間。
	 */
	Integer getLateTime();
	
	/**
	 * @return 早退時間。
	 */
	Integer getLeaveEarlyTime();
	
	/**
	 * @return 遅刻早退時間。
	 */
	Integer getLateLeaveEarlyTime();
	
	/**
	 * @return 残業時間。
	 */
	Integer getOverTimeIn();
	
	/**
	 * @return 法定外残業時間。
	 */
	Integer getOverTimeOut();
	
	/**
	 * @return 法定外残業時間スタイル。
	 */
	String getOvertimeOutStyle();
	
	/**
	 * @return 休出時間。
	 */
	Integer getWorkOnHolidayTime();
	
	/**
	 * @return 深夜時間。
	 */
	Integer getLateNightTime();
	
	/**
	 * @return 有給休暇日数。
	 */
	Double getPaidHoliday();
	
	/**
	 * @return 有給休暇時間数。
	 */
	int getPaidHolidayHour();
	
	/**
	 * @return 特別休暇合計日数。
	 */
	double getTotalSpecialHoliday();
	
	/**
	 * @return その他休暇合計日数。
	 */
	double getTotalOtherHoliday();
	
	/**
	 * @return 代休日数。
	 */
	double getTimesCompensation();
	
	/**
	 * @return 休暇日数。
	 */
	Double getAllHoliday();
	
	/**
	 * @return 欠勤日数。
	 */
	Double getAbsence();
	
	/**
	 * @return 出勤回数。
	 */
	int getTimesWork();
	
	/**
	 * @return 遅刻回数。
	 */
	int getTimesLate();
	
	/**
	 * @return 早退回数。
	 */
	int getTimesLeaveEarly();
	
	/**
	 * @return 残業回数。
	 */
	int getTimesOvertime();
	
	/**
	 * @return 休日出勤回数。
	 */
	int getTimesWorkingHoliday();
	
	/**
	 * @return 法定休日日数。
	 */
	int getTimesLegalHoliday();
	
	/**
	 * @return 所定休日日数。
	 */
	int getTimesSpecificHoliday();
	
	/**
	 * @return 振替休日日数
	 */
	double getTimesHolidaySubstitute();
	
	/**
	 * @return 未承認有無(表示名称)
	 */
	String getApproval();
	
	/**
	 * @return 未集計有無(表示名称)
	 */
	String getCalc();
	
	/**
	 * @return 修正履歴
	 */
	String getCorrection();
	
	/**
	 * @return 締状態
	 */
	int getCutoffState();
	
	/**
	 * @return 未承認フラグ(true：未承認有、false：未承認無)
	 */
	boolean isApprovableExist();
	
	/**
	 * @return 未申請フラグ(true：勤怠未申請有、false：勤怠未申請無)
	 */
	boolean isAppliableExist();
	
	/**
	 * @return 締状態表示クラス
	 */
	String getCutoffStateClass();
	
	/**
	 * @return 承認状態表示クラス
	 */
	String getApprovalStateClass();
	
	/**
	 * @param isApprovableExist セットする 未承認フラグ(true：未承認有、false：未承認無)
	 */
	void setApprovableExist(boolean isApprovableExist);
	
	/**
	 * @param isAppliableExist セットする 未申請フラグ(true：勤怠未申請有、false：勤怠未申請無)
	 */
	void setAppliableExist(boolean isAppliableExist);
	
	/**
	 * @param cutoffState セットする 締状態
	 */
	void setCutoffState(int cutoffState);
	
	/**
	 * @param cutoffStateClass セットする 締状態表示クラス
	 */
	void setCutoffStateClass(String cutoffStateClass);
	
	/**
	 * @param approvalStateClass セットする 承認状態表示クラス
	 */
	void setApprovalStateClass(String approvalStateClass);
	
	/**
	 * @param targetYear 対象年
	 */
	void setTargetYear(int targetYear);
	
	/**
	 * @param targetMonth 対象月
	 */
	void setTargetMonth(int targetMonth);
	
	/**
	 * @param workDate セットする 出勤日数。
	 */
	void setWorkDate(Double workDate);
	
	/**
	 * @param workTime セットする 勤務時間。
	 */
	void setWorkTime(Integer workTime);
	
	/**
	 * @param restTime セットする 休憩時間。
	 */
	void setRestTime(Integer restTime);
	
	/**
	 * @param privateTime セットする 私用外出時間。
	 */
	void setPrivateTime(Integer privateTime);
	
	/**
	 * @param lateTime セットする 遅刻時間。
	 */
	void setLateTime(Integer lateTime);
	
	/**
	 * @param leaveEarlyTime セットする 早退時間。
	 */
	void setLeaveEarlyTime(Integer leaveEarlyTime);
	
	/**
	 * @param lateLeaveEarlyTime セットする 早退時間。
	 */
	void setLateLeaveEarlyTime(Integer lateLeaveEarlyTime);
	
	/**
	 * @param overTimeIn セットする 残業時間。
	 */
	void setOverTimeIn(Integer overTimeIn);
	
	/**
	 * @param overTimeOut セットする 法定外残業時間。
	 */
	void setOverTimeOut(Integer overTimeOut);
	
	/**
	 * @param overtimeOutStyle セットする 法定外残業時間スタイル。
	 */
	void setOvertimeOutStyle(String overtimeOutStyle);
	
	/**
	 * @param workOnHolidayTime セットする 休出時間。
	 */
	void setWorkOnHolidayTime(Integer workOnHolidayTime);
	
	/**
	 * @param lateNightTime セットする 深夜時間。
	 */
	void setLateNightTime(Integer lateNightTime);
	
	/**
	 * @param paidHoliday セットする 有給休暇日数。
	 */
	void setPaidHoliday(Double paidHoliday);
	
	/**
	 * @param paidHolidayHour セットする 有給休暇時間数。
	 */
	void setPaidHolidayHour(int paidHolidayHour);
	
	/**
	 * @param totalSpecialHoliday セットする 特別休暇合計日数。
	 */
	void setTotalSpecialHoliday(double totalSpecialHoliday);
	
	/**
	 * @param totalOtherHoliday セットする その他休暇合計日数。
	 */
	void setTotalOtherHoliday(double totalOtherHoliday);
	
	/**
	 * @param timesCompensation セットする 代休日数。
	 */
	void setTimesCompensation(double timesCompensation);
	
	/**
	 * @param allHoliday セットする 休暇日数。
	 */
	void setAllHoliday(Double allHoliday);
	
	/**
	 * @param absence セットする 欠勤日数。
	 */
	void setAbsence(Double absence);
	
	/**
	 * @param timesWork セットする 出勤回数。
	 */
	void setTimesWork(int timesWork);
	
	/**
	 * @param timesLate セットする 遅刻回数。
	 */
	void setTimesLate(int timesLate);
	
	/**
	 * @param timesLeaveEarly セットする 早退回数。
	 */
	void setTimesLeaveEarly(int timesLeaveEarly);
	
	/**
	 * @param timesOvertime セットする 残業回数。
	 */
	void setTimesOvertime(int timesOvertime);
	
	/**
	 * @param timesWorkingHoliday セットする 休日出勤回数。
	 */
	void setTimesWorkingHoliday(int timesWorkingHoliday);
	
	/**
	 * @param timesLegalHoliday セットする 法定休日日数。
	 */
	void setTimesLegalHoliday(int timesLegalHoliday);
	
	/**
	 * @param timesSpecificHoliday セットする 所定休日日数。
	 */
	void setTimesSpecificHoliday(int timesSpecificHoliday);
	
	/**
	 * @param timesHolidaySubstitute セットする 振替休日日数。
	 */
	void setTimesHolidaySubstitute(double timesHolidaySubstitute);
	
	/**
	 * @param approval セットする 未承認有無。
	 */
	void setApproval(String approval);
	
	/**
	 * @param calc セットする 未集計有無。
	 */
	void setCalc(String calc);
	
	/**
	 * @param correction セットする 修正履歴。
	 */
	void setCorrection(String correction);
}
