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

import java.util.Date;
import java.util.HashMap;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;

/**
 * 勤怠集計データDTO。
 */
public class TmdTotalTimeDataDto extends BaseDto implements TotalTimeDataDtoInterface {
	
	private static final long	serialVersionUID	= 7389058681167622376L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdTotalTimeId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 年。
	 */
	private int					calculationYear;
	/**
	 * 月。
	 */
	private int					calculationMonth;
	/**
	 * 集計日。
	 */
	private Date				calculationDate;
	/**
	 * 勤務時間。
	 */
	private int					workTime;
	/**
	 * 所定勤務時間。
	 */
	private int					specificWorkTime;
	/**
	 * 契約勤務時間。
	 */
	private int					contractWorkTime;
	/**
	 * 無給時短時間。
	 */
	private int					shortUnpaid;
	/**
	 * 出勤日数。
	 */
	private double				timesWorkDate;
	/**
	 * 出勤回数。
	 */
	private int					timesWork;
	/**
	 * 法定休日出勤日数。
	 */
	private double				legalWorkOnHoliday;
	/**
	 * 所定休日出勤日数。
	 */
	private double				specificWorkOnHoliday;
	/**
	 * 出勤実績日数。
	 */
	private int					timesAchievement;
	/**
	 * 出勤対象日数。
	 */
	private int					timesTotalWorkDate;
	/**
	 * 直行回数。
	 */
	private int					directStart;
	/**
	 * 直帰回数。
	 */
	private int					directEnd;
	/**
	 * 休憩時間。
	 */
	private int					restTime;
	/**
	 * 深夜休憩時間。
	 */
	private int					restLateNight;
	/**
	 * 所定休出休憩時間。
	 */
	private int					restWorkOnSpecificHoliday;
	/**
	 * 法定休出休憩時間。
	 */
	private int					restWorkOnHoliday;
	/**
	 * 公用外出時間。
	 */
	private int					publicTime;
	/**
	 * 私用外出時間。
	 */
	private int					privateTime;
	/**
	 * 分単位休暇A時間。
	 */
	private int					minutelyHolidayATime;
	/**
	 * 分単位休暇B時間。
	 */
	private int					minutelyHolidayBTime;
	/**
	 * 残業時間。
	 */
	private int					overtime;
	/**
	 * 法定内残業時間。
	 */
	private int					overtimeIn;
	/**
	 * 法定外残業時間。
	 */
	private int					overtimeOut;
	/**
	 * 深夜時間。
	 */
	private int					lateNight;
	/**
	 * 深夜所定労働時間内時間。
	 */
	private int					nightWorkWithinPrescribedWork;
	/**
	 * 深夜時間外時間。
	 */
	private int					nightOvertimeWork;
	/**
	 * 深夜休日労働時間。
	 */
	private int					nightWorkOnHoliday;
	/**
	 * 所定休出時間。
	 */
	private int					workOnSpecificHoliday;
	/**
	 * 法定休出時間。
	 */
	private int					workOnHoliday;
	/**
	 * 減額対象時間。
	 */
	private int					decreaseTime;
	/**
	 * 45時間超残業時間。
	 */
	private int					fortyFiveHourOvertime;
	/**
	 * 残業回数。
	 */
	private int					timesOvertime;
	/**
	 * 休日出勤回数。
	 */
	private int					timesWorkingHoliday;
	/**
	 * 合計遅刻日数。
	 */
	private int					lateDays;
	/**
	 * 遅刻30分以上日数。
	 */
	private int					lateThirtyMinutesOrMore;
	/**
	 * 遅刻30分未満日数。
	 */
	private int					lateLessThanThirtyMinutes;
	/**
	 * 合計遅刻時間。
	 */
	private int					lateTime;
	/**
	 * 遅刻30分以上時間。
	 */
	private int					lateThirtyMinutesOrMoreTime;
	/**
	 * 遅刻30分未満時間。
	 */
	private int					lateLessThanThirtyMinutesTime;
	/**
	 * 合計遅刻回数。
	 */
	private int					timesLate;
	/**
	 * 合計早退日数。
	 */
	private int					leaveEarlyDays;
	/**
	 * 早退30分以上日数。
	 */
	private int					leaveEarlyThirtyMinutesOrMore;
	/**
	 * 早退30分未満日数。
	 */
	private int					leaveEarlyLessThanThirtyMinutes;
	/**
	 * 合計早退時間。
	 */
	private int					leaveEarlyTime;
	/**
	 * 早退30分以上時間。
	 */
	private int					leaveEarlyThirtyMinutesOrMoreTime;
	/**
	 * 早退30分未満時間。
	 */
	private int					leaveEarlyLessThanThirtyMinutesTime;
	/**
	 * 合計早退回数。
	 */
	private int					timesLeaveEarly;
	/**
	 * 休日日数。
	 */
	private int					timesHoliday;
	/**
	 * 法定休日日数。
	 */
	private int					timesLegalHoliday;
	/**
	 * 所定休日日数。
	 */
	private int					timesSpecificHoliday;
	/**
	 * 有給休暇日数。
	 */
	private double				timesPaidHoliday;
	/**
	 * 有給休暇時間。
	 */
	private int					paidHolidayHour;
	/**
	 * ストック休暇日数。
	 */
	private double				timesStockHoliday;
	/**
	 * 代休日数。
	 */
	private double				timesCompensation;
	/**
	 * 法定代休日数。
	 */
	private double				timesLegalCompensation;
	/**
	 * 所定代休日数。
	 */
	private double				timesSpecificCompensation;
	/**
	 * 深夜代休日数。
	 */
	private double				timesLateCompensation;
	/**
	 * 振替休日日数。
	 */
	private double				timesHolidaySubstitute;
	/**
	 * 法定振替休日日数。
	 */
	private double				timesLegalHolidaySubstitute;
	/**
	 * 所定振替休日日数。
	 */
	private double				timesSpecificHolidaySubstitute;
	/**
	 * 特別休暇合計日数。
	 */
	private double				totalSpecialHoliday;
	/**
	 * 特別休暇時間。
	 */
	private int					specialHolidayHour;
	/**
	 * その他休暇合計日数。
	 */
	private double				totalOtherHoliday;
	/**
	 * その他休暇時間。
	 */
	private int					otherHolidayHour;
	/**
	 * 欠勤合計日数。
	 */
	private double				totalAbsence;
	/**
	 * 欠勤時間。
	 */
	private int					absenceHour;
	/**
	 * 手当合計。
	 */
	private int					totalAllowance;
	
	/**
	 * 60時間超残業時間。
	 */
	private int					sixtyHourOvertime;
	/**
	 * 平日時間外時間。
	 */
	private int					weekDayOvertime;
	/**
	 * 所定休日時間外時間。
	 */
	private int					specificOvertime;
	/**
	 * 代替休暇日数。
	 */
	private double				timesAlternative;
	
	/**
	 * 法定代休発生日数。
	 */
	private double				legalCompensationOccurred;
	
	/**
	 * 所定代休発生日数。
	 */
	private double				specificCompensationOccurred;
	
	/**
	 * 深夜代休発生日数。
	 */
	private double				lateCompensationOccurred;
	
	/**
	 * 法定代休未使用日数。
	 */
	private double				legalCompensationUnused;
	
	/**
	 * 所定代休未使用日数。
	 */
	private double				specificCompensationUnused;
	
	/**
	 * 深夜代休未使用日数。
	 */
	private double				lateCompensationUnused;
	
	/**
	 * 所定労働時間内法定休日労働時間。
	 */
	private int					statutoryHolidayWorkTimeIn;
	
	/**
	 * 所定労働時間外法定休日労働時間。
	 */
	private int					statutoryHolidayWorkTimeOut;
	
	/**
	 * 所定労働時間内所定休日労働時間。
	 */
	private int					prescribedHolidayWorkTimeIn;
	
	/**
	 * 所定労働時間外所定休日労働時間。
	 */
	private int					prescribedHolidayWorkTimeOut;
	
	/**
	 * 週40時間超勤務時間。
	 */
	private int					weeklyOverFortyHourWorkTime;
	
	/**
	 * 法定内残業時間(週40時間超除く)。
	 */
	private int					overtimeInNoWeeklyForty;
	
	/**
	 * 法定外残業時間(週40時間超除く)。
	 */
	private int					overtimeOutNoWeeklyForty;
	
	/**
	 * 平日残業合計時間。
	 */
	private int					weekDayOvertimeTotal;
	
	/**
	 * 平日時間内時間(週40時間超除く)。
	 */
	private int					weekDayOvertimeInNoWeeklyForty;
	
	/**
	 * 平日時間外時間(週40時間超除く)。
	 */
	private int					weekDayOvertimeOutNoWeeklyForty;
	
	/**
	 * 平日時間内時間。
	 */
	private int					weekDayOvertimeIn;
	
	/**
	 * 所定休日時間内時間(所定休日法定労働時間内残業時間)。<br>
	 * <br>
	 * 当項目は、DAOには設定されておらず、DBに登録されない。<br>
	 * <br>
	 */
	private int					prescribedHolidayOvertimeIn;
	
	/**
	 * 汎用項目1(数値)。
	 */
	private int					generalIntItem1;
	/**
	 * 汎用項目2(数値)。
	 */
	private int					generalIntItem2;
	/**
	 * 汎用項目3(数値)。
	 */
	private int					generalIntItem3;
	/**
	 * 汎用項目4(数値)。
	 */
	private int					generalIntItem4;
	/**
	 * 汎用項目5(数値)。
	 */
	private int					generalIntItem5;
	/**
	 * 汎用項目1(浮動小数点数)。
	 */
	private double				generalDoubleItem1;
	/**
	 * 汎用項目1(浮動小数点数)。
	 */
	private double				generalDoubleItem2;
	/**
	 * 汎用項目1(浮動小数点数)。
	 */
	private double				generalDoubleItem3;
	/**
	 * 汎用項目1(浮動小数点数)。
	 */
	private double				generalDoubleItem4;
	/**
	 * 汎用項目1(浮動小数点数)。
	 */
	private double				generalDoubleItem5;
	
	/**
	 * 汎用項目群。
	 */
	private Map<String, Object>	generalMap;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdTotalTimeDataDto() {
		super();
		generalMap = new HashMap<String, Object>();
	}
	
	@Override
	public int getCalculationMonth() {
		return calculationMonth;
	}
	
	@Override
	public int getCalculationYear() {
		return calculationYear;
	}
	
	@Override
	public int getDecreaseTime() {
		return decreaseTime;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public int getFortyFiveHourOvertime() {
		return fortyFiveHourOvertime;
	}
	
	@Override
	public int getLateNight() {
		return lateNight;
	}
	
	@Override
	public int getNightWorkWithinPrescribedWork() {
		return nightWorkWithinPrescribedWork;
	}
	
	@Override
	public int getNightOvertimeWork() {
		return nightOvertimeWork;
	}
	
	@Override
	public int getNightWorkOnHoliday() {
		return nightWorkOnHoliday;
	}
	
	@Override
	public int getLateTime() {
		return lateTime;
	}
	
	@Override
	public int getLateThirtyMinutesOrMoreTime() {
		return lateThirtyMinutesOrMoreTime;
	}
	
	@Override
	public int getLateLessThanThirtyMinutesTime() {
		return lateLessThanThirtyMinutesTime;
	}
	
	@Override
	public double getLegalWorkOnHoliday() {
		return legalWorkOnHoliday;
	}
	
	@Override
	public int getOvertime() {
		return overtime;
	}
	
	@Override
	public int getOvertimeIn() {
		return overtimeIn;
	}
	
	@Override
	public int getOvertimeOut() {
		return overtimeOut;
	}
	
	@Override
	public int getPaidHolidayHour() {
		return paidHolidayHour;
	}
	
	@Override
	public int getPrivateTime() {
		return privateTime;
	}
	
	@Override
	public int getPublicTime() {
		return publicTime;
	}
	
	@Override
	public int getMinutelyHolidayATime() {
		return minutelyHolidayATime;
	}
	
	@Override
	public int getMinutelyHolidayBTime() {
		return minutelyHolidayBTime;
	}
	
	@Override
	public int getRestLateNight() {
		return restLateNight;
	}
	
	@Override
	public int getRestTime() {
		return restTime;
	}
	
	@Override
	public int getRestWorkOnHoliday() {
		return restWorkOnHoliday;
	}
	
	@Override
	public int getRestWorkOnSpecificHoliday() {
		return restWorkOnSpecificHoliday;
	}
	
	@Override
	public int getSixtyHourOvertime() {
		return sixtyHourOvertime;
	}
	
	@Override
	public int getSpecificOvertime() {
		return specificOvertime;
	}
	
	@Override
	public double getSpecificWorkOnHoliday() {
		return specificWorkOnHoliday;
	}
	
	@Override
	public int getSpecificWorkTime() {
		return specificWorkTime;
	}
	
	@Override
	public int getContractWorkTime() {
		return contractWorkTime;
	}
	
	@Override
	public int getShortUnpaid() {
		return shortUnpaid;
	}
	
	@Override
	public double getTimesWorkDate() {
		return timesWorkDate;
	}
	
	@Override
	public double getTimesAlternative() {
		return timesAlternative;
	}
	
	@Override
	public double getTimesCompensation() {
		return timesCompensation;
	}
	
	@Override
	public int getTimesHoliday() {
		return timesHoliday;
	}
	
	@Override
	public int getTimesLate() {
		return timesLate;
	}
	
	@Override
	public double getTimesLateCompensation() {
		return timesLateCompensation;
	}
	
	@Override
	public int getTimesLeaveEarly() {
		return timesLeaveEarly;
	}
	
	@Override
	public double getTimesLegalCompensation() {
		return timesLegalCompensation;
	}
	
	@Override
	public int getTimesLegalHoliday() {
		return timesLegalHoliday;
	}
	
	@Override
	public double getTimesPaidHoliday() {
		return timesPaidHoliday;
	}
	
	@Override
	public double getTimesSpecificCompensation() {
		return timesSpecificCompensation;
	}
	
	@Override
	public int getTimesSpecificHoliday() {
		return timesSpecificHoliday;
	}
	
	@Override
	public double getTimesSpecificHolidaySubstitute() {
		return timesSpecificHolidaySubstitute;
	}
	
	@Override
	public double getTimesStockHoliday() {
		return timesStockHoliday;
	}
	
	@Override
	public double getTimesHolidaySubstitute() {
		return timesHolidaySubstitute;
	}
	
	@Override
	public int getTimesWork() {
		return timesWork;
	}
	
	@Override
	public long getTmdTotalTimeId() {
		return tmdTotalTimeId;
	}
	
	@Override
	public int getWeekDayOvertime() {
		return weekDayOvertime;
	}
	
	@Override
	public int getWorkOnHoliday() {
		return workOnHoliday;
	}
	
	@Override
	public int getWorkOnSpecificHoliday() {
		return workOnSpecificHoliday;
	}
	
	@Override
	public int getWorkTime() {
		return workTime;
	}
	
	@Override
	public int getTotalAllowance() {
		return totalAllowance;
	}
	
	@Override
	public double getLegalCompensationOccurred() {
		return legalCompensationOccurred;
	}
	
	@Override
	public double getSpecificCompensationOccurred() {
		return specificCompensationOccurred;
	}
	
	@Override
	public double getLateCompensationOccurred() {
		return lateCompensationOccurred;
	}
	
	@Override
	public double getLegalCompensationUnused() {
		return legalCompensationUnused;
	}
	
	@Override
	public double getSpecificCompensationUnused() {
		return specificCompensationUnused;
	}
	
	@Override
	public double getLateCompensationUnused() {
		return lateCompensationUnused;
	}
	
	@Override
	public int getStatutoryHolidayWorkTimeIn() {
		return statutoryHolidayWorkTimeIn;
	}
	
	@Override
	public int getStatutoryHolidayWorkTimeOut() {
		return statutoryHolidayWorkTimeOut;
	}
	
	@Override
	public int getPrescribedHolidayWorkTimeIn() {
		return prescribedHolidayWorkTimeIn;
	}
	
	@Override
	public int getPrescribedHolidayWorkTimeOut() {
		return prescribedHolidayWorkTimeOut;
	}
	
	@Override
	public int getWeeklyOverFortyHourWorkTime() {
		return weeklyOverFortyHourWorkTime;
	}
	
	@Override
	public int getOvertimeInNoWeeklyForty() {
		return overtimeInNoWeeklyForty;
	}
	
	@Override
	public int getOvertimeOutNoWeeklyForty() {
		return overtimeOutNoWeeklyForty;
	}
	
	@Override
	public int getWeekDayOvertimeTotal() {
		return weekDayOvertimeTotal;
	}
	
	@Override
	public int getWeekDayOvertimeInNoWeeklyForty() {
		return weekDayOvertimeInNoWeeklyForty;
	}
	
	@Override
	public int getWeekDayOvertimeOutNoWeeklyForty() {
		return weekDayOvertimeOutNoWeeklyForty;
	}
	
	@Override
	public int getWeekDayOvertimeIn() {
		return weekDayOvertimeIn;
	}
	
	@Override
	public void setCalculationMonth(int calculationMonth) {
		this.calculationMonth = calculationMonth;
	}
	
	@Override
	public void setCalculationYear(int calculationYear) {
		this.calculationYear = calculationYear;
	}
	
	@Override
	public void setDecreaseTime(int decreaseTime) {
		this.decreaseTime = decreaseTime;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setFortyFiveHourOvertime(int fortyFiveHourOvertime) {
		this.fortyFiveHourOvertime = fortyFiveHourOvertime;
	}
	
	@Override
	public void setLateNight(int lateNight) {
		this.lateNight = lateNight;
	}
	
	@Override
	public void setNightWorkWithinPrescribedWork(int nightWorkWithinPrescribedWork) {
		this.nightWorkWithinPrescribedWork = nightWorkWithinPrescribedWork;
	}
	
	@Override
	public void setNightOvertimeWork(int nightOvertimeWork) {
		this.nightOvertimeWork = nightOvertimeWork;
	}
	
	@Override
	public void setNightWorkOnHoliday(int nightWorkOnHoliday) {
		this.nightWorkOnHoliday = nightWorkOnHoliday;
	}
	
	@Override
	public void setLateTime(int lateTime) {
		this.lateTime = lateTime;
	}
	
	@Override
	public void setLateThirtyMinutesOrMoreTime(int lateThirtyMinutesOrMoreTime) {
		this.lateThirtyMinutesOrMoreTime = lateThirtyMinutesOrMoreTime;
	}
	
	@Override
	public void setLeaveEarlyLessThanThirtyMinutes(int leaveEarlyLessThanThirtyMinutes) {
		this.leaveEarlyLessThanThirtyMinutes = leaveEarlyLessThanThirtyMinutes;
	}
	
	@Override
	public void setLateLessThanThirtyMinutesTime(int lateLessThanThirtyMinutesTime) {
		this.lateLessThanThirtyMinutesTime = lateLessThanThirtyMinutesTime;
	}
	
	@Override
	public void setLegalWorkOnHoliday(double legalWorkOnHoliday) {
		this.legalWorkOnHoliday = legalWorkOnHoliday;
	}
	
	@Override
	public void setOvertime(int overtime) {
		this.overtime = overtime;
	}
	
	@Override
	public void setOvertimeIn(int overtimeIn) {
		this.overtimeIn = overtimeIn;
	}
	
	@Override
	public void setOvertimeOut(int overtimeOut) {
		this.overtimeOut = overtimeOut;
	}
	
	@Override
	public void setPaidHolidayHour(int paidHolidayHour) {
		this.paidHolidayHour = paidHolidayHour;
	}
	
	@Override
	public void setPrivateTime(int privateTime) {
		this.privateTime = privateTime;
	}
	
	@Override
	public void setPublicTime(int publicTime) {
		this.publicTime = publicTime;
	}
	
	@Override
	public void setMinutelyHolidayATime(int minutelyHolidayATime) {
		this.minutelyHolidayATime = minutelyHolidayATime;
	}
	
	@Override
	public void setMinutelyHolidayBTime(int minutelyHolidayBTime) {
		this.minutelyHolidayBTime = minutelyHolidayBTime;
	}
	
	@Override
	public void setRestLateNight(int restLateNight) {
		this.restLateNight = restLateNight;
	}
	
	@Override
	public void setRestTime(int restTime) {
		this.restTime = restTime;
	}
	
	@Override
	public void setRestWorkOnHoliday(int restWorkOnHoliday) {
		this.restWorkOnHoliday = restWorkOnHoliday;
	}
	
	@Override
	public void setRestWorkOnSpecificHoliday(int restWorkOnSpecificHoliday) {
		this.restWorkOnSpecificHoliday = restWorkOnSpecificHoliday;
	}
	
	@Override
	public void setSixtyHourOvertime(int sixtyHourOvertime) {
		this.sixtyHourOvertime = sixtyHourOvertime;
	}
	
	@Override
	public void setSpecificOvertime(int specificOvertime) {
		this.specificOvertime = specificOvertime;
	}
	
	@Override
	public void setSpecificWorkOnHoliday(double specificWorkOnHoliday) {
		this.specificWorkOnHoliday = specificWorkOnHoliday;
	}
	
	@Override
	public void setSpecificWorkTime(int specificWorkTime) {
		this.specificWorkTime = specificWorkTime;
	}
	
	@Override
	public void setContractWorkTime(int contractWorkTime) {
		this.contractWorkTime = contractWorkTime;
	}
	
	@Override
	public void setShortUnpaid(int shortUnpaid) {
		this.shortUnpaid = shortUnpaid;
	}
	
	@Override
	public void setTimesWorkDate(double timesWorkDate) {
		this.timesWorkDate = timesWorkDate;
	}
	
	@Override
	public void setTimesAlternative(double timesAlternative) {
		this.timesAlternative = timesAlternative;
	}
	
	@Override
	public void setTimesCompensation(double timesCompensation) {
		this.timesCompensation = timesCompensation;
	}
	
	@Override
	public void setTimesHoliday(int timesHoliday) {
		this.timesHoliday = timesHoliday;
	}
	
	@Override
	public void setTimesLate(int timesLate) {
		this.timesLate = timesLate;
	}
	
	@Override
	public void setTimesLateCompensation(double timesLateCompensation) {
		this.timesLateCompensation = timesLateCompensation;
	}
	
	@Override
	public void setTimesLeaveEarly(int timesLeaveEarly) {
		this.timesLeaveEarly = timesLeaveEarly;
	}
	
	@Override
	public void setTimesLegalCompensation(double timesLegalCompensation) {
		this.timesLegalCompensation = timesLegalCompensation;
	}
	
	@Override
	public void setTimesLegalHoliday(int timesLegalHoliday) {
		this.timesLegalHoliday = timesLegalHoliday;
	}
	
	@Override
	public void setTimesPaidHoliday(double timesPaidHoliday) {
		this.timesPaidHoliday = timesPaidHoliday;
	}
	
	@Override
	public void setTimesSpecificCompensation(double timesSpecificCompensation) {
		this.timesSpecificCompensation = timesSpecificCompensation;
	}
	
	@Override
	public void setTimesSpecificHoliday(int timesSpecificHoliday) {
		this.timesSpecificHoliday = timesSpecificHoliday;
	}
	
	@Override
	public void setTimesSpecificHolidaySubstitute(double timesSpecificHolidaySubstitute) {
		this.timesSpecificHolidaySubstitute = timesSpecificHolidaySubstitute;
	}
	
	@Override
	public void setTimesStockHoliday(double timesStockHoliday) {
		this.timesStockHoliday = timesStockHoliday;
	}
	
	@Override
	public void setTimesHolidaySubstitute(double timesHolidaySubstitute) {
		this.timesHolidaySubstitute = timesHolidaySubstitute;
	}
	
	@Override
	public void setTimesWork(int timesWork) {
		this.timesWork = timesWork;
	}
	
	@Override
	public void setTmdTotalTimeId(long tmdTotalTimeId) {
		this.tmdTotalTimeId = tmdTotalTimeId;
	}
	
	@Override
	public void setWeekDayOvertime(int weekDayOvertime) {
		this.weekDayOvertime = weekDayOvertime;
	}
	
	@Override
	public void setWorkOnHoliday(int workOnHoliday) {
		this.workOnHoliday = workOnHoliday;
	}
	
	@Override
	public void setWorkOnSpecificHoliday(int workOnSpecificHoliday) {
		this.workOnSpecificHoliday = workOnSpecificHoliday;
	}
	
	@Override
	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}
	
	@Override
	public int getLeaveEarlyTime() {
		return leaveEarlyTime;
	}
	
	@Override
	public int getLeaveEarlyThirtyMinutesOrMoreTime() {
		return leaveEarlyThirtyMinutesOrMoreTime;
	}
	
	@Override
	public int getLeaveEarlyLessThanThirtyMinutesTime() {
		return leaveEarlyLessThanThirtyMinutesTime;
	}
	
	@Override
	public void setLeaveEarlyTime(int leaveEarlyTime) {
		this.leaveEarlyTime = leaveEarlyTime;
	}
	
	@Override
	public void setLeaveEarlyThirtyMinutesOrMoreTime(int leaveEarlyThirtyMinutesOrMoreTime) {
		this.leaveEarlyThirtyMinutesOrMoreTime = leaveEarlyThirtyMinutesOrMoreTime;
	}
	
	@Override
	public void setLeaveEarlyLessThanThirtyMinutesTime(int leaveEarlyLessThanThirtyMinutesTime) {
		this.leaveEarlyLessThanThirtyMinutesTime = leaveEarlyLessThanThirtyMinutesTime;
	}
	
	@Override
	public double getTotalOtherHoliday() {
		return totalOtherHoliday;
	}
	
	@Override
	public double getTotalSpecialHoliday() {
		return totalSpecialHoliday;
	}
	
	@Override
	public int getSpecialHolidayHour() {
		return specialHolidayHour;
	}
	
	@Override
	public int getOtherHolidayHour() {
		return otherHolidayHour;
	}
	
	@Override
	public void setTotalOtherHoliday(double totalOtherHoliday) {
		this.totalOtherHoliday = totalOtherHoliday;
	}
	
	@Override
	public void setOtherHolidayHour(int otherHolidayHour) {
		this.otherHolidayHour = otherHolidayHour;
	}
	
	@Override
	public void setTotalSpecialHoliday(double totalSpecialHoliday) {
		this.totalSpecialHoliday = totalSpecialHoliday;
	}
	
	@Override
	public void setSpecialHolidayHour(int specialHolidayHour) {
		this.specialHolidayHour = specialHolidayHour;
	}
	
	@Override
	public double getTotalAbsence() {
		return totalAbsence;
	}
	
	@Override
	public int getAbsenceHour() {
		return absenceHour;
	}
	
	@Override
	public void setTotalAbsence(double totalAbsence) {
		this.totalAbsence = totalAbsence;
	}
	
	@Override
	public void setAbsenceHour(int absenceHour) {
		this.absenceHour = absenceHour;
	}
	
	@Override
	public int getDirectEnd() {
		return directEnd;
	}
	
	@Override
	public int getDirectStart() {
		return directStart;
	}
	
	@Override
	public void setDirectEnd(int directEnd) {
		this.directEnd = directEnd;
	}
	
	@Override
	public void setDirectStart(int directStart) {
		this.directStart = directStart;
	}
	
	@Override
	public int getTimesOvertime() {
		return timesOvertime;
	}
	
	@Override
	public void setTimesOvertime(int timesOvertime) {
		this.timesOvertime = timesOvertime;
	}
	
	@Override
	public double getTimesLegalHolidaySubstitute() {
		return timesLegalHolidaySubstitute;
	}
	
	@Override
	public void setTimesLegalHolidaySubstitute(double timesLegalHolidaySubstitute) {
		this.timesLegalHolidaySubstitute = timesLegalHolidaySubstitute;
	}
	
	@Override
	public void setTotalAllowance(int totalAllowance) {
		this.totalAllowance = totalAllowance;
	}
	
	@Override
	public int getLateDays() {
		return lateDays;
	}
	
	@Override
	public int getLateThirtyMinutesOrMore() {
		return lateThirtyMinutesOrMore;
	}
	
	@Override
	public int getLateLessThanThirtyMinutes() {
		return lateLessThanThirtyMinutes;
	}
	
	@Override
	public int getLeaveEarlyDays() {
		return leaveEarlyDays;
	}
	
	@Override
	public int getLeaveEarlyThirtyMinutesOrMore() {
		return leaveEarlyThirtyMinutesOrMore;
	}
	
	@Override
	public int getLeaveEarlyLessThanThirtyMinutes() {
		return leaveEarlyLessThanThirtyMinutes;
	}
	
	@Override
	public int getTimesWorkingHoliday() {
		return timesWorkingHoliday;
	}
	
	@Override
	public void setTimesWorkingHoliday(int timesWorkingHoliday) {
		this.timesWorkingHoliday = timesWorkingHoliday;
	}
	
	@Override
	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}
	
	@Override
	public void setLateThirtyMinutesOrMore(int lateThirtyMinutesOrMore) {
		this.lateThirtyMinutesOrMore = lateThirtyMinutesOrMore;
	}
	
	@Override
	public void setLateLessThanThirtyMinutes(int lateLessThanThirtyMinutes) {
		this.lateLessThanThirtyMinutes = lateLessThanThirtyMinutes;
	}
	
	@Override
	public void setLeaveEarlyDays(int leaveEarlyDays) {
		this.leaveEarlyDays = leaveEarlyDays;
	}
	
	@Override
	public void setLeaveEarlyThirtyMinutesOrMore(int leaveEarlyThirtyMinutesOrMore) {
		this.leaveEarlyThirtyMinutesOrMore = leaveEarlyThirtyMinutesOrMore;
	}
	
	@Override
	public int getTimesAchievement() {
		return timesAchievement;
	}
	
	@Override
	public int getTimesTotalWorkDate() {
		return timesTotalWorkDate;
	}
	
	@Override
	public void setTimesAchievement(int timesAchievement) {
		this.timesAchievement = timesAchievement;
	}
	
	@Override
	public void setTimesTotalWorkDate(int timesTotalWorkDate) {
		this.timesTotalWorkDate = timesTotalWorkDate;
	}
	
	@Override
	public Date getCalculationDate() {
		return getDateClone(calculationDate);
	}
	
	@Override
	public void setCalculationDate(Date calculationDate) {
		this.calculationDate = getDateClone(calculationDate);
	}
	
	@Override
	public void setLegalCompensationOccurred(double legalCompensationOccurred) {
		this.legalCompensationOccurred = legalCompensationOccurred;
	}
	
	@Override
	public void setSpecificCompensationOccurred(double specificCompensationOccurred) {
		this.specificCompensationOccurred = specificCompensationOccurred;
	}
	
	@Override
	public void setLateCompensationOccurred(double lateCompensationOccurred) {
		this.lateCompensationOccurred = lateCompensationOccurred;
	}
	
	@Override
	public void setLegalCompensationUnused(double legalCompensationUnused) {
		this.legalCompensationUnused = legalCompensationUnused;
	}
	
	@Override
	public void setSpecificCompensationUnused(double specificCompensationUnused) {
		this.specificCompensationUnused = specificCompensationUnused;
	}
	
	@Override
	public void setLateCompensationUnused(double lateCompensationUnused) {
		this.lateCompensationUnused = lateCompensationUnused;
	}
	
	@Override
	public void setStatutoryHolidayWorkTimeIn(int statutoryHolidayWorkTimeIn) {
		this.statutoryHolidayWorkTimeIn = statutoryHolidayWorkTimeIn;
	}
	
	@Override
	public void setStatutoryHolidayWorkTimeOut(int statutoryHolidayWorkTimeOut) {
		this.statutoryHolidayWorkTimeOut = statutoryHolidayWorkTimeOut;
	}
	
	@Override
	public void setPrescribedHolidayWorkTimeIn(int prescribedHolidayWorkTimeIn) {
		this.prescribedHolidayWorkTimeIn = prescribedHolidayWorkTimeIn;
	}
	
	@Override
	public void setPrescribedHolidayWorkTimeOut(int prescribedHolidayWorkTimeOut) {
		this.prescribedHolidayWorkTimeOut = prescribedHolidayWorkTimeOut;
	}
	
	@Override
	public void setWeeklyOverFortyHourWorkTime(int weeklyOverFortyHourWorkTime) {
		this.weeklyOverFortyHourWorkTime = weeklyOverFortyHourWorkTime;
	}
	
	@Override
	public void setOvertimeInNoWeeklyForty(int overtimeInNoWeeklyForty) {
		this.overtimeInNoWeeklyForty = overtimeInNoWeeklyForty;
	}
	
	@Override
	public void setOvertimeOutNoWeeklyForty(int overtimeOutNoWeeklyForty) {
		this.overtimeOutNoWeeklyForty = overtimeOutNoWeeklyForty;
	}
	
	@Override
	public void setWeekDayOvertimeTotal(int weekDayOvertimeTotal) {
		this.weekDayOvertimeTotal = weekDayOvertimeTotal;
	}
	
	@Override
	public void setWeekDayOvertimeInNoWeeklyForty(int weekDayOvertimeInNoWeeklyForty) {
		this.weekDayOvertimeInNoWeeklyForty = weekDayOvertimeInNoWeeklyForty;
	}
	
	@Override
	public void setWeekDayOvertimeOutNoWeeklyForty(int weekDayOvertimeOutNoWeeklyForty) {
		this.weekDayOvertimeOutNoWeeklyForty = weekDayOvertimeOutNoWeeklyForty;
	}
	
	@Override
	public void setWeekDayOvertimeIn(int weekDayOvertimeIn) {
		this.weekDayOvertimeIn = weekDayOvertimeIn;
	}
	
	@Override
	public int getPrescribedHolidayOvertimeIn() {
		return prescribedHolidayOvertimeIn;
	}
	
	@Override
	public void setPrescribedHolidayOvertimeIn(int prescribedHolidayOvertimeIn) {
		this.prescribedHolidayOvertimeIn = prescribedHolidayOvertimeIn;
	}
	
	@Override
	public int getGeneralIntItem1() {
		return generalIntItem1;
	}
	
	@Override
	public void setGeneralIntItem1(int generalIntItem1) {
		this.generalIntItem1 = generalIntItem1;
	}
	
	@Override
	public int getGeneralIntItem2() {
		return generalIntItem2;
	}
	
	@Override
	public void setGeneralIntItem2(int generalIntItem2) {
		this.generalIntItem2 = generalIntItem2;
	}
	
	@Override
	public int getGeneralIntItem3() {
		return generalIntItem3;
	}
	
	@Override
	public void setGeneralIntItem3(int generalIntItem3) {
		this.generalIntItem3 = generalIntItem3;
	}
	
	@Override
	public int getGeneralIntItem4() {
		return generalIntItem4;
	}
	
	@Override
	public void setGeneralIntItem4(int generalIntItem4) {
		this.generalIntItem4 = generalIntItem4;
	}
	
	@Override
	public int getGeneralIntItem5() {
		return generalIntItem5;
	}
	
	@Override
	public void setGeneralIntItem5(int generalIntItem5) {
		this.generalIntItem5 = generalIntItem5;
	}
	
	@Override
	public double getGeneralDoubleItem1() {
		return generalDoubleItem1;
	}
	
	@Override
	public void setGeneralDoubleItem1(double generalDoubleItem1) {
		this.generalDoubleItem1 = generalDoubleItem1;
	}
	
	@Override
	public double getGeneralDoubleItem2() {
		return generalDoubleItem2;
	}
	
	@Override
	public void setGeneralDoubleItem2(double generalDoubleItem2) {
		this.generalDoubleItem2 = generalDoubleItem2;
	}
	
	@Override
	public double getGeneralDoubleItem3() {
		return generalDoubleItem3;
	}
	
	@Override
	public void setGeneralDoubleItem3(double generalDoubleItem3) {
		this.generalDoubleItem3 = generalDoubleItem3;
	}
	
	@Override
	public double getGeneralDoubleItem4() {
		return generalDoubleItem4;
	}
	
	@Override
	public void setGeneralDoubleItem4(double generalDoubleItem4) {
		this.generalDoubleItem4 = generalDoubleItem4;
	}
	
	@Override
	public double getGeneralDoubleItem5() {
		return generalDoubleItem5;
	}
	
	@Override
	public void setGeneralDoubleItem5(double generalDoubleItem5) {
		this.generalDoubleItem5 = generalDoubleItem5;
	}
	
	@Override
	public Map<String, Object> getGeneralValues() {
		return generalMap;
	}
	
	@Override
	public void setGeneralValues(Map<String, Object> generalMap) {
		this.generalMap = generalMap;
	}
	
}
