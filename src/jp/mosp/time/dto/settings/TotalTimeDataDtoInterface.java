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

import java.util.Date;
import java.util.Map;

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * 勤怠集計データDTOインターフェース
 */
public interface TotalTimeDataDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdTotalTimeId();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @return 年。
	 */
	int getCalculationYear();
	
	/**
	 * @return 月。
	 */
	int getCalculationMonth();
	
	/**
	 * @return 集計日。
	 */
	Date getCalculationDate();
	
	/**
	 * @return 勤務時間。
	 */
	int getWorkTime();
	
	/**
	 * @return 所定勤務時間。
	 */
	int getSpecificWorkTime();
	
	/**
	 * @return 契約勤務時間。
	 */
	int getContractWorkTime();
	
	/**
	 * @return 無給時短時間。
	 */
	int getShortUnpaid();
	
	/**
	 * @return 出勤日数。
	 */
	double getTimesWorkDate();
	
	/**
	 * @return 出勤回数。
	 */
	int getTimesWork();
	
	/**
	 * @return 法定休日出勤日数。
	 */
	double getLegalWorkOnHoliday();
	
	/**
	 * @return 所定休日出勤日数。
	 */
	double getSpecificWorkOnHoliday();
	
	/**
	 * @return 出勤実績日数。
	 */
	int getTimesAchievement();
	
	/**
	 * @return 出勤対象日数。
	 */
	int getTimesTotalWorkDate();
	
	/**
	 * @return 直行回数。
	 */
	int getDirectStart();
	
	/**
	 * @return 直帰回数。
	 */
	int getDirectEnd();
	
	/**
	 * @return 休憩時間。
	 */
	int getRestTime();
	
	/**
	 * @return 深夜休憩時間。
	 */
	int getRestLateNight();
	
	/**
	 * @return 所定休出休憩時間。
	 */
	int getRestWorkOnSpecificHoliday();
	
	/**
	 * @return 法定休出休憩時間。
	 */
	int getRestWorkOnHoliday();
	
	/**
	 * @return 公用外出時間。
	 */
	int getPublicTime();
	
	/**
	 * @return 私用外出時間。
	 */
	int getPrivateTime();
	
	/**
	 * @return 分単位休暇A時間。
	 */
	int getMinutelyHolidayATime();
	
	/**
	 * @return 分単位休暇B時間。
	 */
	int getMinutelyHolidayBTime();
	
	/**
	 * @return 残業時間。
	 */
	int getOvertime();
	
	/**
	 * @return 法定内残業時間。
	 */
	int getOvertimeIn();
	
	/**
	 * @return 法定外残業時間。
	 */
	int getOvertimeOut();
	
	/**
	 * @return 深夜時間。
	 */
	int getLateNight();
	
	/**
	 * @return 深夜所定労働時間内時間。
	 */
	int getNightWorkWithinPrescribedWork();
	
	/**
	 * @return 深夜時間外時間。
	 */
	int getNightOvertimeWork();
	
	/**
	 * @return 深夜休日労働時間。
	 */
	int getNightWorkOnHoliday();
	
	/**
	 * @return 所定休出時間。
	 */
	int getWorkOnSpecificHoliday();
	
	/**
	 * @return 法定休出時間。
	 */
	int getWorkOnHoliday();
	
	/**
	 * @return 減額対象時間。
	 */
	int getDecreaseTime();
	
	/**
	 * @return 45時間超残業時間。
	 */
	int getFortyFiveHourOvertime();
	
	/**
	 * @return 残業回数。
	 */
	int getTimesOvertime();
	
	/**
	 * @return 休日出勤回数。
	 */
	int getTimesWorkingHoliday();
	
	/**
	 * @return 合計遅刻日数。
	 */
	int getLateDays();
	
	/**
	 * @return 遅刻30分以上日数。
	 */
	int getLateThirtyMinutesOrMore();
	
	/**
	 * @return 遅刻30分未満日数。
	 */
	int getLateLessThanThirtyMinutes();
	
	/**
	 * @return 合計遅刻時間。
	 */
	int getLateTime();
	
	/**
	 * @return 遅刻30分以上時間。
	 */
	int getLateThirtyMinutesOrMoreTime();
	
	/**
	 * @return 遅刻30分未満時間。
	 */
	int getLateLessThanThirtyMinutesTime();
	
	/**
	 * @return 合計遅刻回数。
	 */
	int getTimesLate();
	
	/**
	 * @return 合計早退日数。
	 */
	int getLeaveEarlyDays();
	
	/**
	 * @return 早退30分以上日数。
	 */
	int getLeaveEarlyThirtyMinutesOrMore();
	
	/**
	 * @return 早退30分未満日数。
	 */
	int getLeaveEarlyLessThanThirtyMinutes();
	
	/**
	 * @return 合計早退時間。
	 */
	int getLeaveEarlyTime();
	
	/**
	 * @return 早退30分以上時間。
	 */
	int getLeaveEarlyThirtyMinutesOrMoreTime();
	
	/**
	 * @return 早退30分未満時間。
	 */
	int getLeaveEarlyLessThanThirtyMinutesTime();
	
	/**
	 * @return 合計早退回数。
	 */
	int getTimesLeaveEarly();
	
	/**
	 * @return 休日日数。
	 */
	int getTimesHoliday();
	
	/**
	 * @return 法定休日日数。
	 */
	int getTimesLegalHoliday();
	
	/**
	 * @return 所定休日日数。
	 */
	int getTimesSpecificHoliday();
	
	/**
	 * @return 有給休暇日数。
	 */
	double getTimesPaidHoliday();
	
	/**
	 * @return 有給休暇時間。
	 */
	int getPaidHolidayHour();
	
	/**
	 * @return ストック休暇日数。
	 */
	double getTimesStockHoliday();
	
	/**
	 * @return 代休日数。
	 */
	double getTimesCompensation();
	
	/**
	 * @return 法定代休日数。
	 */
	double getTimesLegalCompensation();
	
	/**
	 * @return 所定代休日数。
	 */
	double getTimesSpecificCompensation();
	
	/**
	 * @return 深夜代休日数。
	 */
	double getTimesLateCompensation();
	
	/**
	 * @return 振替休日日数。
	 */
	double getTimesHolidaySubstitute();
	
	/**
	 * @return 法定振替休日日数。
	 */
	double getTimesLegalHolidaySubstitute();
	
	/**
	 * @return 所定振替休日日数。
	 */
	double getTimesSpecificHolidaySubstitute();
	
	/**
	 * @return 特別休暇合計日数。
	 */
	double getTotalSpecialHoliday();
	
	/**
	 * @return 特別休暇時間。
	 */
	int getSpecialHolidayHour();
	
	/**
	 * @return その他休暇合計日数。
	 */
	double getTotalOtherHoliday();
	
	/**
	 * @return その他休暇時間。
	 */
	int getOtherHolidayHour();
	
	/**
	 * @return 欠勤合計日数。
	 */
	double getTotalAbsence();
	
	/**
	 * @return 欠勤時間。
	 */
	int getAbsenceHour();
	
	/**
	 * @return 手当合計。
	 */
	int getTotalAllowance();
	
	/**
	 * @return 週40時間超勤務時間
	 */
	int getWeeklyOverFortyHourWorkTime();
	
	/**
	 * @return 60時間超残業時間。
	 */
	int getSixtyHourOvertime();
	
	/**
	 * @return 平日時間外時間。
	 */
	int getWeekDayOvertime();
	
	/**
	 * @return 所定休日時間外時間。
	 */
	int getSpecificOvertime();
	
	/**
	 * @return 代替休暇日数。
	 */
	double getTimesAlternative();
	
	/**
	 * @return 法定代休発生日数。
	 */
	double getLegalCompensationOccurred();
	
	/**
	 * @return 所定代休発生日数。
	 */
	double getSpecificCompensationOccurred();
	
	/**
	 * @return 深夜代休発生日数。
	 */
	double getLateCompensationOccurred();
	
	/**
	 * @return 法定代休未使用日数。
	 */
	double getLegalCompensationUnused();
	
	/**
	 * @return 所定代休未使用日数。
	 */
	double getSpecificCompensationUnused();
	
	/**
	 * @return 深夜代休未使用日数。
	 */
	double getLateCompensationUnused();
	
	/**
	 * @return 所定労働時間内法定休日労働時間。
	 */
	int getStatutoryHolidayWorkTimeIn();
	
	/**
	 * @return 所定労働時間外法定休日労働時間。
	 */
	int getStatutoryHolidayWorkTimeOut();
	
	/**
	 * @return 所定労働時間内所定休日労働時間。
	 */
	int getPrescribedHolidayWorkTimeIn();
	
	/**
	 * @return 所定労働時間外所定休日労働時間。
	 */
	int getPrescribedHolidayWorkTimeOut();
	
	/**
	 * @param tmdTotalTimeId セットする レコード識別ID。
	 */
	void setTmdTotalTimeId(long tmdTotalTimeId);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param calculationYear セットする 年。
	 */
	void setCalculationYear(int calculationYear);
	
	/**
	 * @param calculationMonth セットする 月。
	 */
	void setCalculationMonth(int calculationMonth);
	
	/**
	 * @param calculationDate セットする 集計日。
	 */
	void setCalculationDate(Date calculationDate);
	
	/**
	 * @param workTime セットする 勤務時間。
	 */
	void setWorkTime(int workTime);
	
	/**
	 * @param specificWorkTime セットする 所定勤務時間。
	 */
	void setSpecificWorkTime(int specificWorkTime);
	
	/**
	 * @param contractWorkTime セットする 契約勤務時間。
	 */
	void setContractWorkTime(int contractWorkTime);
	
	/**
	 * @param shortUnpaid セットする 無給時短時間。
	 */
	void setShortUnpaid(int shortUnpaid);
	
	/**
	 * @param timesWorkDate セットする 出勤日数。
	 */
	void setTimesWorkDate(double timesWorkDate);
	
	/**
	 * @param timesWork セットする 出勤回数。
	 */
	void setTimesWork(int timesWork);
	
	/**
	 * @param legalWorkOnHoliday セットする 法定休日出勤日数。
	 */
	void setLegalWorkOnHoliday(double legalWorkOnHoliday);
	
	/**
	 * @param specificWorkOnHoliday セットする 所定休日出勤日数。
	 */
	void setSpecificWorkOnHoliday(double specificWorkOnHoliday);
	
	/**
	 * @param timesAchievement セットする 出勤実績日数。
	 */
	void setTimesAchievement(int timesAchievement);
	
	/**
	 * @param timesTotalWorkDate セットする 出勤対象日数。
	 */
	void setTimesTotalWorkDate(int timesTotalWorkDate);
	
	/**
	 * @param directStart セットする 直行回数。
	 */
	void setDirectStart(int directStart);
	
	/**
	 * @param directEnd セットする 直帰回数。
	 */
	void setDirectEnd(int directEnd);
	
	/**
	 * @param restTime セットする 休憩時間。
	 */
	void setRestTime(int restTime);
	
	/**
	 * @param restLateNight セットする 深夜休憩時間。
	 */
	void setRestLateNight(int restLateNight);
	
	/**
	 * @param restWorkOnSpecificHoliday セットする 所定休出休憩時間。
	 */
	void setRestWorkOnSpecificHoliday(int restWorkOnSpecificHoliday);
	
	/**
	 * @param restWorkOnHoliday セットする 法定休出休憩時間。
	 */
	void setRestWorkOnHoliday(int restWorkOnHoliday);
	
	/**
	 * @param publicTime セットする 公用外出時間。
	 */
	void setPublicTime(int publicTime);
	
	/**
	 * @param privateTime セットする 私用外出時間。
	 */
	void setPrivateTime(int privateTime);
	
	/**
	 * @param minutelyHolidayATime セットする 分単位休暇A時間。
	 */
	void setMinutelyHolidayATime(int minutelyHolidayATime);
	
	/**
	 * @param minutelyHolidayBTime セットする 分単位休暇B時間。
	 */
	void setMinutelyHolidayBTime(int minutelyHolidayBTime);
	
	/**
	 * @param overtime セットする 残業時間。
	 */
	void setOvertime(int overtime);
	
	/**
	 * @param overtimeIn セットする 法定内残業時間。
	 */
	void setOvertimeIn(int overtimeIn);
	
	/**
	 * @param overtimeOut セットする 法定外残業時間。
	 */
	void setOvertimeOut(int overtimeOut);
	
	/**
	 * @param lateNight セットする 深夜時間。
	 */
	void setLateNight(int lateNight);
	
	/**
	 * @param nightWorkWithinPrescribedWork セットする 深夜所定労働時間内時間。
	 */
	void setNightWorkWithinPrescribedWork(int nightWorkWithinPrescribedWork);
	
	/**
	 * @param nightOvertimeWork セットする 深夜時間外時間。
	 */
	void setNightOvertimeWork(int nightOvertimeWork);
	
	/**
	 * @param nightWorkOnHoliday セットする 深夜休日労働時間。
	 */
	void setNightWorkOnHoliday(int nightWorkOnHoliday);
	
	/**
	 * @param workOnSpecificHoliday セットする 所定休出時間。
	 */
	void setWorkOnSpecificHoliday(int workOnSpecificHoliday);
	
	/**
	 * @param workOnHoliday セットする 法定休出時間。
	 */
	void setWorkOnHoliday(int workOnHoliday);
	
	/**
	 * @param decreaseTime セットする 減額対象時間。
	 */
	void setDecreaseTime(int decreaseTime);
	
	/**
	 * @param fortyFiveHourOvertime セットする 45時間超残業時間。
	 */
	void setFortyFiveHourOvertime(int fortyFiveHourOvertime);
	
	/**
	 * @param timesOvertime セットする 残業回数。
	 */
	void setTimesOvertime(int timesOvertime);
	
	/**
	 * @param timesWorkingHoliday セットする 休日出勤回数。
	 */
	void setTimesWorkingHoliday(int timesWorkingHoliday);
	
	/**
	 * @param lateDays セットする 合計遅刻日数。
	 */
	void setLateDays(int lateDays);
	
	/**
	 * @param lateThirtyMinutesOrMore セットする 遅刻30分以上日数。
	 */
	void setLateThirtyMinutesOrMore(int lateThirtyMinutesOrMore);
	
	/**
	 * @param lateLessThanThirtyMinutes セットする 遅刻30分未満日数。
	 */
	void setLateLessThanThirtyMinutes(int lateLessThanThirtyMinutes);
	
	/**
	 * @param lateTime セットする 合計遅刻時間。
	 */
	void setLateTime(int lateTime);
	
	/**
	 * @param lateThirtyMinutesOrMoreTime セットする 遅刻30分以上時間。
	 */
	void setLateThirtyMinutesOrMoreTime(int lateThirtyMinutesOrMoreTime);
	
	/**
	 * @param lateLessThanThirtyMinutesTime セットする 遅刻30分未満時間。
	 */
	void setLateLessThanThirtyMinutesTime(int lateLessThanThirtyMinutesTime);
	
	/**
	 * @param timesLate セットする 合計遅刻回数。
	 */
	void setTimesLate(int timesLate);
	
	/**
	 * @param leaveEarlyDays セットする 合計早退日数。
	 */
	void setLeaveEarlyDays(int leaveEarlyDays);
	
	/**
	 * @param leaveEarlyThirtyMinutesOrMore セットする 早退30分以上日数。
	 */
	void setLeaveEarlyThirtyMinutesOrMore(int leaveEarlyThirtyMinutesOrMore);
	
	/**
	 * @param leaveEarlyLessThanThirtyMinutes セットする 早退30分未満日数。
	 */
	void setLeaveEarlyLessThanThirtyMinutes(int leaveEarlyLessThanThirtyMinutes);
	
	/**
	 * @param leaveEarlyTime セットする 合計早退時間。
	 */
	void setLeaveEarlyTime(int leaveEarlyTime);
	
	/**
	 * @param leaveEarlyThirtyMinutesOrMoreTime セットする 早退30分以上時間。
	 */
	void setLeaveEarlyThirtyMinutesOrMoreTime(int leaveEarlyThirtyMinutesOrMoreTime);
	
	/**
	 * @param leaveEarlyLessThanThirtyMinutesTime セットする 早退30分未満時間。
	 */
	void setLeaveEarlyLessThanThirtyMinutesTime(int leaveEarlyLessThanThirtyMinutesTime);
	
	/**
	 * @param timesLeaveEarly セットする 合計早退回数。
	 */
	void setTimesLeaveEarly(int timesLeaveEarly);
	
	/**
	 * @param timesHoliday セットする 休日日数。
	 */
	void setTimesHoliday(int timesHoliday);
	
	/**
	 * @param timesLegalHoliday セットする 法定休日日数。
	 */
	void setTimesLegalHoliday(int timesLegalHoliday);
	
	/**
	 * @param timesSpecificHoliday セットする 所定休日日数。
	 */
	void setTimesSpecificHoliday(int timesSpecificHoliday);
	
	/**
	 * @param timesPaidHoliday セットする 有給休暇日数。
	 */
	void setTimesPaidHoliday(double timesPaidHoliday);
	
	/**
	 * @param paidHolidayHour セットする 有給休暇時間。
	 */
	void setPaidHolidayHour(int paidHolidayHour);
	
	/**
	 * @param timesStockHoliday セットする ストック休暇日数。
	 */
	void setTimesStockHoliday(double timesStockHoliday);
	
	/**
	 * @param timesCompensation セットする 代休日数。
	 */
	void setTimesCompensation(double timesCompensation);
	
	/**
	 * @param timesLegalCompensation セットする 法定代休日数。
	 */
	void setTimesLegalCompensation(double timesLegalCompensation);
	
	/**
	 * @param timesSpecificCompensation セットする 所定代休日数。
	 */
	void setTimesSpecificCompensation(double timesSpecificCompensation);
	
	/**
	 * @param timesLateCompensation セットする 深夜代休日数。
	 */
	void setTimesLateCompensation(double timesLateCompensation);
	
	/**
	 * @param timesHolidaySubstitute セットする 振替休日日数。
	 */
	void setTimesHolidaySubstitute(double timesHolidaySubstitute);
	
	/**
	 * @param timesLegalHolidaySubstitute セットする 法定振替休日日数。
	 */
	void setTimesLegalHolidaySubstitute(double timesLegalHolidaySubstitute);
	
	/**
	 * @param timesSpecificHolidaySubstitute セットする 所定振替休日日数。
	 */
	void setTimesSpecificHolidaySubstitute(double timesSpecificHolidaySubstitute);
	
	/**
	 * @param totalSpecialHoliday セットする 特別休暇合計日数。
	 */
	void setTotalSpecialHoliday(double totalSpecialHoliday);
	
	/**
	 * @param specialHolidayHour セットする 特別休暇時間。
	 */
	void setSpecialHolidayHour(int specialHolidayHour);
	
	/**
	 * @param totalOtherHoliday セットする その他休暇合計日数。
	 */
	void setTotalOtherHoliday(double totalOtherHoliday);
	
	/**
	 * @param otherHolidayHour セットする その他休暇時間。
	 */
	void setOtherHolidayHour(int otherHolidayHour);
	
	/**
	 * @param totalAbsence セットする 欠勤合計日数。
	 */
	void setTotalAbsence(double totalAbsence);
	
	/**
	 * @param absenceHour セットする 欠勤時間。
	 */
	void setAbsenceHour(int absenceHour);
	
	/**
	 * @param totalAllowance セットする 手当合計。
	 */
	void setTotalAllowance(int totalAllowance);
	
	/**
	 * @param sixtyHourOvertime セットする 60時間超残業時間。
	 */
	void setSixtyHourOvertime(int sixtyHourOvertime);
	
	/**
	 * @param weekDayOvertime セットする 平日時間外時間。
	 */
	void setWeekDayOvertime(int weekDayOvertime);
	
	/**
	 * @param specificOvertime セットする 所定休日時間外時間。
	 */
	void setSpecificOvertime(int specificOvertime);
	
	/**
	 * @param timesAlternative セットする 代替休暇日数。
	 */
	void setTimesAlternative(double timesAlternative);
	
	/**
	 * @param legalCompensationOccurred セットする 法定代休発生日数。
	 */
	void setLegalCompensationOccurred(double legalCompensationOccurred);
	
	/**
	 * @param specificCompensationOccurred セットする 所定代休発生日数。
	 */
	void setSpecificCompensationOccurred(double specificCompensationOccurred);
	
	/**
	 * @param lateCompensationOccurred セットする 深夜代休発生日数。
	 */
	void setLateCompensationOccurred(double lateCompensationOccurred);
	
	/**
	 * @param legalCompensationUnused セットする 法定代休未使用日数。
	 */
	void setLegalCompensationUnused(double legalCompensationUnused);
	
	/**
	 * @param specificCompensationUnused セットする 所定代休未使用日数。
	 */
	void setSpecificCompensationUnused(double specificCompensationUnused);
	
	/**
	 * @param lateCompensationUnused セットする 深夜代休未使用日数。
	 */
	void setLateCompensationUnused(double lateCompensationUnused);
	
	/**
	 * @param statutoryHolidayWorkTimeIn セットする 所定労働時間内法定休日労働時間。
	 */
	void setStatutoryHolidayWorkTimeIn(int statutoryHolidayWorkTimeIn);
	
	/**
	 * @param statutoryHolidayWorkTimeOut セットする 所定労働時間外法定休日労働時間。
	 */
	void setStatutoryHolidayWorkTimeOut(int statutoryHolidayWorkTimeOut);
	
	/**
	 * @param prescribedHolidayWorkTimeIn セットする 所定労働時間内所定休日労働時間。
	 */
	void setPrescribedHolidayWorkTimeIn(int prescribedHolidayWorkTimeIn);
	
	/**
	 * @param prescribedHolidayWorkTimeOut セットする 所定労働時間外所定休日労働時間。
	 */
	void setPrescribedHolidayWorkTimeOut(int prescribedHolidayWorkTimeOut);
	
	/**
	 * @param weeklyOverFortyHourWorkTime セットする 週40時間超勤務時間
	 */
	void setWeeklyOverFortyHourWorkTime(int weeklyOverFortyHourWorkTime);
	
	/**
	 * @return 法定内残業時間(週40時間超除く)
	 */
	public int getOvertimeInNoWeeklyForty();
	
	/**
	 * @param overtimeInNoWeeklyForty セットする 法定内残業時間(週40時間超除く)
	 */
	public void setOvertimeInNoWeeklyForty(int overtimeInNoWeeklyForty);
	
	/**
	 * @return 法定外残業時間(週40時間超除く)
	 */
	public int getOvertimeOutNoWeeklyForty();
	
	/**
	 * @param overtimeOutNoWeeklyForty セットする 法定外残業時間(週40時間超除く)
	 */
	public void setOvertimeOutNoWeeklyForty(int overtimeOutNoWeeklyForty);
	
	/**
	 * @return 平日残業合計時間
	 */
	public int getWeekDayOvertimeTotal();
	
	/**
	 * @param weekDayOvertimeTotal セットする 平日残業合計時間
	 */
	public void setWeekDayOvertimeTotal(int weekDayOvertimeTotal);
	
	/**
	 * @return 平日時間内時間(週40時間超除く)
	 */
	public int getWeekDayOvertimeInNoWeeklyForty();
	
	/**
	 * @param weekDayOvertimeInNoWeeklyForty セットする 平日時間内時間(週40時間超除く)
	 */
	public void setWeekDayOvertimeInNoWeeklyForty(int weekDayOvertimeInNoWeeklyForty);
	
	/**
	 * @return 平日時間外時間(週40時間超除く)
	 */
	public int getWeekDayOvertimeOutNoWeeklyForty();
	
	/**
	 * @param weekDayOvertimeOutNoWeeklyForty セットする 平日時間外時間(週40時間超除く)
	 */
	public void setWeekDayOvertimeOutNoWeeklyForty(int weekDayOvertimeOutNoWeeklyForty);
	
	/**
	 * @return 平日時間内時間
	 */
	public int getWeekDayOvertimeIn();
	
	/**
	 * @param weekDayOvertimeIn セットする 平日時間内時間
	 */
	public void setWeekDayOvertimeIn(int weekDayOvertimeIn);
	
	/**
	 * 当項目は、DAOには設定されておらず、DBに登録されない。<br>
	 * @return 所定休日時間内時間(所定休日法定労働時間内残業時間)
	 */
	public int getPrescribedHolidayOvertimeIn();
	
	/**
	 * 当項目は、DAOには設定されておらず、DBに登録されない。<br>
	 * @param prescribedHolidayOvertimeIn セットする 所定休日時間内時間(所定休日法定労働時間内残業時間)
	 */
	public void setPrescribedHolidayOvertimeIn(int prescribedHolidayOvertimeIn);
	
	/**
	 * @return 汎用項目1(数値)
	 */
	int getGeneralIntItem1();
	
	/**
	 * @param generalIntItem1 汎用項目1(数値)
	 */
	void setGeneralIntItem1(int generalIntItem1);
	
	/**
	 * @return 汎用項目2(数値)
	 */
	int getGeneralIntItem2();
	
	/**
	 * @param generalIntItem2 汎用項目2(数値)
	 */
	void setGeneralIntItem2(int generalIntItem2);
	
	/**
	 * @return 汎用項目3(数値)
	 */
	int getGeneralIntItem3();
	
	/**
	 * @param generalIntItem3 汎用項目3(数値)
	 */
	void setGeneralIntItem3(int generalIntItem3);
	
	/**
	 * @return 汎用項目4(数値)
	 */
	int getGeneralIntItem4();
	
	/**
	 * @param generalIntItem4 汎用項目4(数値)
	 */
	void setGeneralIntItem4(int generalIntItem4);
	
	/**
	 * @return 汎用項目5(数値)
	 */
	int getGeneralIntItem5();
	
	/**
	 * @param generalIntItem5 汎用項目5(数値)
	 */
	void setGeneralIntItem5(int generalIntItem5);
	
	/**
	 * @return 汎用項目1(浮動小数点数)
	 */
	double getGeneralDoubleItem1();
	
	/**
	 * @param generalDoubleItem1 汎用項目1(浮動小数点数)
	 */
	void setGeneralDoubleItem1(double generalDoubleItem1);
	
	/**
	 * @return 汎用項目2(浮動小数点数)
	 */
	double getGeneralDoubleItem2();
	
	/**
	 * @param generalDoubleItem2 汎用項目2(浮動小数点数)
	 */
	void setGeneralDoubleItem2(double generalDoubleItem2);
	
	/**
	 * @return 汎用項目3(浮動小数点数)
	 */
	double getGeneralDoubleItem3();
	
	/**
	 * @param generalDoubleItem3 汎用項目3(浮動小数点数)
	 */
	void setGeneralDoubleItem3(double generalDoubleItem3);
	
	/**
	 * @return 汎用項目4(浮動小数点数)
	 */
	double getGeneralDoubleItem4();
	
	/**
	 * @param generalDoubleItem4 汎用項目4(浮動小数点数)
	 */
	void setGeneralDoubleItem4(double generalDoubleItem4);
	
	/**
	 * @return 汎用項目5(浮動小数点数)
	 */
	double getGeneralDoubleItem5();
	
	/**
	 * @param generalDoubleItem5 汎用項目5(浮動小数点数)
	 */
	void setGeneralDoubleItem5(double generalDoubleItem5);
	
	/**
	 * 汎用項目群の取得<br>
	 * TODO 暫定処置
	 * <br>
	 * @return 汎用項目群(キー：汎用コード)
	 */
	Map<String, Object> getGeneralValues();
	
	/**
	 * 汎用項目群の設定<br>
	 * TODO 暫定処置
	 * @param generalMap 汎用項目群
	 */
	void setGeneralValues(Map<String, Object> generalMap);
	
}
