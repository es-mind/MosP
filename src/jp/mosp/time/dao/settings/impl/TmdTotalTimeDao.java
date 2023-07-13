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
package jp.mosp.time.dao.settings.impl;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.TotalTimeDataDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeDataDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTotalTimeDataDto;

/**
 * 勤怠集計データDAOクラス。
 */
public class TmdTotalTimeDao extends PlatformDao implements TotalTimeDataDaoInterface {
	
	/**
	 * 勤怠集計データ。
	 */
	public static final String	TABLE											= "tmd_total_time";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_TOTAL_TIME_ID							= "tmd_total_time_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID									= "personal_id";
	
	/**
	 * 年。
	 */
	public static final String	COL_CALCULATION_YEAR							= "calculation_year";
	
	/**
	 * 月。
	 */
	public static final String	COL_CALCULATION_MONTH							= "calculation_month";
	
	/**
	 * 集計日。
	 */
	public static final String	COL_CALCULATION_DATE							= "calculation_date";
	
	/**
	 * 勤務時間。
	 */
	public static final String	COL_WORK_TIME									= "work_time";
	
	/**
	 * 所定勤務時間。
	 */
	public static final String	COL_SPECIFIC_WORK_TIME							= "specific_work_time";
	
	/**
	 * 契約勤務時間。
	 */
	public static final String	COL_CONTRACT_WORK_TIME							= "contract_work_time";
	
	/**
	 * 無給時短時間。
	 */
	public static final String	COL_SHORT_UNPAID								= "short_unpaid";
	
	/**
	 * 出勤日数。
	 */
	public static final String	COL_TIMES_WORK_DATE								= "times_work_date";
	
	/**
	 * 出勤回数。
	 */
	public static final String	COL_TIMES_WORK									= "times_work";
	
	/**
	 * 法定休日出勤日数。
	 */
	public static final String	COL_LEGAL_WORK_ON_HOLIDAY						= "legal_work_on_holiday";
	
	/**
	 * 所定休日出勤日数。
	 */
	public static final String	COL_SPECIFIC_WORK_ON_HOLIDAY					= "specific_work_on_holiday";
	
	/**
	 * 出勤実績日数。
	 */
	public static final String	COL_TIMES_ACHIEVEMENT							= "times_achievement";
	
	/**
	 * 出勤対象日数。
	 */
	public static final String	COL_TIMES_TOTAL_WORK_DATE						= "times_total_work_date";
	
	/**
	 * 直行回数。
	 */
	public static final String	COL_DIRECT_START								= "direct_start";
	
	/**
	 * 直帰回数。
	 */
	public static final String	COL_DIRECT_END									= "direct_end";
	
	/**
	 * 休憩時間。
	 */
	public static final String	COL_REST_TIME									= "rest_time";
	
	/**
	 * 深夜休憩時間。
	 */
	public static final String	COL_REST_LATE_NIGHT								= "rest_late_night";
	
	/**
	 * 所定休出休憩時間。
	 */
	public static final String	COL_REST_WORK_ON_SPECIFIC_HOLIDAY				= "rest_work_on_specific_holiday";
	
	/**
	 * 法定休出休憩時間。
	 */
	public static final String	COL_REST_WORK_ON_HOLIDAY						= "rest_work_on_holiday";
	
	/**
	 * 公用外出時間。
	 */
	public static final String	COL_PUBLIC_TIME									= "public_time";
	
	/**
	 * 私用外出時間。
	 */
	public static final String	COL_PRIVATE_TIME								= "private_time";
	
	/**
	 * 分単位休暇1時間。
	 */
	public static final String	COL_MINUTELY_HOLIDAY_A_TIME						= "minutely_holiday_a_time";
	
	/**
	 * 分単位休暇2時間。
	 */
	public static final String	COL_MINUTELY_HOLIDAY_B_TIME						= "minutely_holiday_b_time";
	
	/**
	 * 残業時間。
	 */
	public static final String	COL_OVERTIME									= "overtime";
	
	/**
	 * 法定内残業時間。
	 */
	public static final String	COL_OVERTIME_IN									= "overtime_in";
	
	/**
	 * 法定外残業時間。
	 */
	public static final String	COL_OVERTIME_OUT								= "overtime_out";
	
	/**
	 * 深夜時間。
	 */
	public static final String	COL_LATE_NIGHT									= "late_night";
	
	/**
	 * 深夜所定労働時間内時間。
	 */
	public static final String	COL_NIGHT_WORK_WITHIN_PRESCRIBED_WORK			= "night_work_within_prescribed_work";
	
	/**
	 * 深夜時間外時間。
	 */
	public static final String	COL_NIGHT_OVERTIME_WORK							= "night_overtime_work";
	
	/**
	 * 深夜休日労働時間。
	 */
	public static final String	COL_NIGHT_WORK_ON_HOLIDAY						= "night_work_on_holiday";
	
	/**
	 * 所定休出時間。
	 */
	public static final String	COL_WORK_ON_SPECIFIC_HOLIDAY					= "work_on_specific_holiday";
	
	/**
	 * 法定休出時間。
	 */
	public static final String	COL_WORK_ON_HOLIDAY								= "work_on_holiday";
	
	/**
	 * 減額対象時間。
	 */
	public static final String	COL_DECREASE_TIME								= "decrease_time";
	
	/**
	 * 45時間超残業時間。
	 */
	public static final String	COL_FORTY_FIVE_HOUR_OVERTIME					= "forty_five_hour_overtime";
	
	/**
	 * 残業回数。
	 */
	public static final String	COL_TIMES_OVERTIME								= "times_overtime";
	
	/**
	 * 休日出勤回数。
	 */
	public static final String	COL_TIMES_WORKING_HOLIDAY						= "times_working_holiday";
	
	/**
	 * 合計遅刻日数。
	 */
	public static final String	COL_LATE_DAYS									= "late_days";
	
	/**
	 * 遅刻30分以上日数。
	 */
	public static final String	COL_LATE_THIRTY_MINUTES_OR_MORE					= "late_thirty_minutes_or_more";
	
	/**
	 * 遅刻30分未満日数。
	 */
	public static final String	COL_LATE_LESS_THAN_THIRTY_MINUTES				= "late_less_than_thirty_minutes";
	
	/**
	 * 合計遅刻時間。
	 */
	public static final String	COL_LATE_TIME									= "late_time";
	
	/**
	 * 遅刻30分以上時間。
	 */
	public static final String	COL_LATE_THIRTY_MINUTES_OR_MORE_TIME			= "late_thirty_minutes_or_more_time";
	
	/**
	 * 遅刻30分未満時間。
	 */
	public static final String	COL_LATE_LESS_THAN_THIRTY_MINUTES_TIME			= "late_less_than_thirty_minutes_time";
	
	/**
	 * 合計遅刻回数。
	 */
	public static final String	COL_TIMES_LATE									= "times_late";
	
	/**
	 * 合計早退日数。
	 */
	public static final String	COL_LEAVE_EARLY_DAYS							= "leave_early_days";
	
	/**
	 * 早退30分以上日数。
	 */
	public static final String	COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE			= "leave_early_thirty_minutes_or_more";
	
	/**
	 * 早退30分未満日数。
	 */
	public static final String	COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES		= "leave_early_less_than_thirty_minutes";
	
	/**
	 * 合計早退時間。
	 */
	public static final String	COL_LEAVE_EARLY_TIME							= "leave_early_time";
	
	/**
	 * 早退30分以上時間。
	 */
	public static final String	COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME		= "leave_early_thirty_minutes_or_more_time";
	
	/**
	 * 早退30分未満時間。
	 */
	public static final String	COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME	= "leave_early_less_than_thirty_minutes_time";
	
	/**
	 * 合計早退回数。
	 */
	public static final String	COL_TIMES_LEAVE_EARLY							= "times_leave_early";
	
	/**
	 * 休日日数。
	 */
	public static final String	COL_TIMES_HOLIDAY								= "times_holiday";
	
	/**
	 * 法定休日日数。
	 */
	public static final String	COL_TIMES_LEGAL_HOLIDAY							= "times_legal_holiday";
	
	/**
	 * 所定休日日数。
	 */
	public static final String	COL_TIMES_SPECIFIC_HOLIDAY						= "times_specific_holiday";
	
	/**
	 * 有給休暇日数。
	 */
	public static final String	COL_TIMES_PAID_HOLIDAY							= "times_paid_holiday";
	
	/**
	 * 有給休暇時間。
	 */
	public static final String	COL_PAID_HOLIDAY_HOUR							= "paid_holiday_hour";
	
	/**
	 * ストック休暇日数。
	 */
	public static final String	COL_TIMES_STOCK_HOLIDAY							= "times_stock_holiday";
	
	/**
	 * 代休日数。
	 */
	public static final String	COL_TIMES_COMPENSATION							= "times_compensation";
	
	/**
	 * 法定代休日数。
	 */
	public static final String	COL_TIMES_LEGAL_COMPENSATION					= "times_legal_compensation";
	
	/**
	 * 所定代休日数。
	 */
	public static final String	COL_TIMES_SPECIFIC_COMPENSATION					= "times_specific_compensation";
	
	/**
	 * 深夜代休日数。
	 */
	public static final String	COL_TIMES_LATE_COMPENSATION						= "times_late_compensation";
	
	/**
	 * 振替休日日数。
	 */
	public static final String	COL_TIMES_HOLIDAY_SUBSTITUTE					= "times_holiday_substitute";
	
	/**
	 * 法定振替休日日数。
	 */
	public static final String	COL_TIMES_LEGAL_HOLIDAY_SUBSTITUTE				= "times_legal_holiday_substitute";
	
	/**
	 * 所定振替休日日数。
	 */
	public static final String	COL_TIMES_SPECIFIC_HOLIDAY_SUBSTITUTE			= "times_specific_holiday_substitute";
	
	/**
	 * 特別休暇合計日数。
	 */
	public static final String	COL_TOTAL_SPECIAL_HOLIDAY						= "total_special_holiday";
	
	/**
	 * 特別休暇合計時間数。
	 */
	public static final String	COL_SPECIAL_HOLIDAY_HOUR						= "special_holiday_hour";
	
	/**
	 * その他休暇合計日数。
	 */
	public static final String	COL_TOTAL_OTHER_HOLIDAY							= "total_other_holiday";
	
	/**
	 * その他休暇合計時間数。
	 */
	public static final String	COL_OTHER_HOLIDAY_HOUR							= "other_holiday_hour";
	
	/**
	 * 欠勤合計日数。
	 */
	public static final String	COL_TOTAL_ABSENCE								= "total_absence";
	
	/**
	 * 欠勤合計時間数。
	 */
	public static final String	COL_ABSENCE_HOUR								= "absence_hour";
	
	/**
	 * 手当合計。
	 */
	public static final String	COL_TOTAL_ALLOWANCE								= "total_allowance";
	
	/**
	 * 60時間超残業時間。
	 */
	public static final String	COL_SIXTY_HOUR_OVERTIME							= "sixty_hour_overtime";
	
	/**
	 * 平日時間外時間。
	 */
	public static final String	COL_WEEK_DAY_OVERTIME							= "week_day_overtime";
	
	/**
	 * 所定休日時間外時間。
	 */
	public static final String	COL_SPECIFIC_OVERTIME							= "specific_overtime";
	
	/**
	 * 代替休暇日数。
	 */
	public static final String	COL_TIMES_ALTERNATIVE							= "times_alternative";
	
	/**
	 * 法定代休発生日数。
	 */
	public static final String	COL_LEGAL_COMPENSATION_OCCURRED					= "legal_compensation_occurred";
	
	/**
	 * 所定代休発生日数。
	 */
	public static final String	COL_SPECIFIC_COMPENSATION_OCCURRED				= "specific_compensation_occurred";
	
	/**
	 * 深夜代休発生日数。
	 */
	public static final String	COL_LATE_COMPENSATION_OCCURRED					= "late_compensation_occurred";
	
	/**
	 * 法定代休未使用日数。
	 */
	public static final String	COL_LEGAL_COMPENSATION_UNUSED					= "legal_compensation_unused";
	
	/**
	 * 所定代休未使用日数。
	 */
	public static final String	COL_SPECIFIC_COMPENSATION_UNUSED				= "specific_compensation_unused";
	
	/**
	 * 深夜代休未使用日数。
	 */
	public static final String	COL_LATE_COMPENSATION_UNUSED					= "late_compensation_unused";
	
	/**
	 * 所定労働時間内法定休日労働時間。
	 */
	public static final String	COL_STATUTORY_HOLIDAY_WORK_TIME_IN				= "statutory_holiday_work_time_in";
	
	/**
	 * 所定労働時間外法定休日労働時間。
	 */
	public static final String	COL_STATUTORY_HOLIDAY_WORK_TIME_OUT				= "statutory_holiday_work_time_out";
	
	/**
	 * 所定労働時間内所定休日労働時間。
	 */
	public static final String	COL_PRESCRIBED_HOLIDAY_WORK_TIME_IN				= "prescribed_holiday_work_time_in";
	
	/**
	 * 所定労働時間外所定休日労働時間。
	 */
	public static final String	COL_PRESCRIBED_HOLIDAY_WORK_TIME_OUT			= "prescribed_holiday_work_time_out";
	
	/**
	 * 週40時間超勤務時間。
	 */
	public static final String	COL_WEEKLY_OVER_FORTY_HOUR_WORK_TIME			= "weekly_over_forty_hour_work_time";
	
	/**
	 * 法定内残業時間(週40時間超除く)。
	 */
	public static final String	COL_OVERTIME_IN_NO_WEEKLY_FORTY					= "overtime_in_no_weekly_forty";
	
	/**
	 * 法定外残業時間(週40時間超除く)。
	 */
	public static final String	COL_OVERTIME_OUT_NO_WEEKLY_FORTY				= "overtime_out_no_weekly_forty";
	
	/**
	 * 平日残業合計時間。
	 */
	public static final String	COL_WEEK_DAY_OVERTIME_TOTAL						= "week_day_overtime_total";
	
	/**
	 * 平日時間内時間(週40時間超除く)。
	 */
	public static final String	COL_WEEK_DAY_OVERTIME_IN_NO_WEEKLY_FORTY		= "week_day_overtime_in_no_weekly_forty";
	
	/**
	 * 平日時間外時間(週40時間超除く)。
	 */
	public static final String	COL_WEEK_DAY_OVERTIME_OUT_NO_WEEKLY_FORTY		= "week_day_overtime_out_no_weekly_forty";
	
	/**
	 * 平日時間内時間。
	 */
	public static final String	COL_WEEK_DAY_OVERTIME_IN						= "week_day_overtime_in";
	/**
	 * 汎用項目1(数値)
	 */
	public static final String	COL_GENERAL_INT_ITEM1							= "general_int_item1";
	/**
	 * 汎用項目2(数値)
	 */
	public static final String	COL_GENERAL_INT_ITEM2							= "general_int_item2";
	/**
	 * 汎用項目3(数値)
	 */
	public static final String	COL_GENERAL_INT_ITEM3							= "general_int_item3";
	/**
	 * 汎用項目4(数値)
	 */
	public static final String	COL_GENERAL_INT_ITEM4							= "general_int_item4";
	/**
	 * 汎用項目5(数値)
	 */
	public static final String	COL_GENERAL_INT_ITEM5							= "general_int_item5";
	/**
	 * 汎用項目1(浮動小数点数)
	 */
	public static final String	COL_GENERAL_DOUBLE_ITEM1						= "general_double_item1";
	/**
	 * 汎用項目2(浮動小数点数)
	 */
	public static final String	COL_GENERAL_DOUBLE_ITEM2						= "general_double_item2";
	/**
	 * 汎用項目3(浮動小数点数)
	 */
	public static final String	COL_GENERAL_DOUBLE_ITEM3						= "general_double_item3";
	/**
	 * 汎用項目4(浮動小数点数)
	 */
	public static final String	COL_GENERAL_DOUBLE_ITEM4						= "general_double_item4";
	/**
	 * 汎用項目5(浮動小数点数)
	 */
	public static final String	COL_GENERAL_DOUBLE_ITEM5						= "general_double_item5";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1											= COL_TMD_TOTAL_TIME_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdTotalTimeDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdTotalTimeDataDto dto = new TmdTotalTimeDataDto();
		dto.setTmdTotalTimeId(getLong(COL_TMD_TOTAL_TIME_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setCalculationYear(getInt(COL_CALCULATION_YEAR));
		dto.setCalculationMonth(getInt(COL_CALCULATION_MONTH));
		dto.setCalculationDate(getDate(COL_CALCULATION_DATE));
		dto.setWorkTime(getInt(COL_WORK_TIME));
		dto.setSpecificWorkTime(getInt(COL_SPECIFIC_WORK_TIME));
		dto.setContractWorkTime(getInt(COL_CONTRACT_WORK_TIME));
		dto.setShortUnpaid(getInt(COL_SHORT_UNPAID));
		dto.setTimesWorkDate(getDouble(COL_TIMES_WORK_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setLegalWorkOnHoliday(getDouble(COL_LEGAL_WORK_ON_HOLIDAY));
		dto.setSpecificWorkOnHoliday(getDouble(COL_SPECIFIC_WORK_ON_HOLIDAY));
		dto.setTimesAchievement(getInt(COL_TIMES_ACHIEVEMENT));
		dto.setTimesTotalWorkDate(getInt(COL_TIMES_TOTAL_WORK_DATE));
		dto.setDirectStart(getInt(COL_DIRECT_START));
		dto.setDirectEnd(getInt(COL_DIRECT_END));
		dto.setRestTime(getInt(COL_REST_TIME));
		dto.setRestLateNight(getInt(COL_REST_LATE_NIGHT));
		dto.setRestWorkOnSpecificHoliday(getInt(COL_REST_WORK_ON_SPECIFIC_HOLIDAY));
		dto.setRestWorkOnHoliday(getInt(COL_REST_WORK_ON_HOLIDAY));
		dto.setPublicTime(getInt(COL_PUBLIC_TIME));
		dto.setPrivateTime(getInt(COL_PRIVATE_TIME));
		dto.setMinutelyHolidayATime(getInt(COL_MINUTELY_HOLIDAY_A_TIME));
		dto.setMinutelyHolidayBTime(getInt(COL_MINUTELY_HOLIDAY_B_TIME));
		dto.setOvertime(getInt(COL_OVERTIME));
		dto.setOvertimeIn(getInt(COL_OVERTIME_IN));
		dto.setOvertimeOut(getInt(COL_OVERTIME_OUT));
		dto.setLateNight(getInt(COL_LATE_NIGHT));
		dto.setNightWorkWithinPrescribedWork(getInt(COL_NIGHT_WORK_WITHIN_PRESCRIBED_WORK));
		dto.setNightOvertimeWork(getInt(COL_NIGHT_OVERTIME_WORK));
		dto.setNightWorkOnHoliday(getInt(COL_NIGHT_WORK_ON_HOLIDAY));
		dto.setWorkOnSpecificHoliday(getInt(COL_WORK_ON_SPECIFIC_HOLIDAY));
		dto.setWorkOnHoliday(getInt(COL_WORK_ON_HOLIDAY));
		dto.setDecreaseTime(getInt(COL_DECREASE_TIME));
		dto.setFortyFiveHourOvertime(getInt(COL_FORTY_FIVE_HOUR_OVERTIME));
		dto.setTimesOvertime(getInt(COL_TIMES_OVERTIME));
		dto.setTimesWorkingHoliday(getInt(COL_TIMES_WORKING_HOLIDAY));
		dto.setLateDays(getInt(COL_LATE_DAYS));
		dto.setLateThirtyMinutesOrMore(getInt(COL_LATE_THIRTY_MINUTES_OR_MORE));
		dto.setLateLessThanThirtyMinutes(getInt(COL_LATE_LESS_THAN_THIRTY_MINUTES));
		dto.setLateTime(getInt(COL_LATE_TIME));
		dto.setLateThirtyMinutesOrMoreTime(getInt(COL_LATE_THIRTY_MINUTES_OR_MORE_TIME));
		dto.setLateLessThanThirtyMinutesTime(getInt(COL_LATE_LESS_THAN_THIRTY_MINUTES_TIME));
		dto.setTimesLate(getInt(COL_TIMES_LATE));
		dto.setLeaveEarlyDays(getInt(COL_LEAVE_EARLY_DAYS));
		dto.setLeaveEarlyThirtyMinutesOrMore(getInt(COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE));
		dto.setLeaveEarlyLessThanThirtyMinutes(getInt(COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES));
		dto.setLeaveEarlyTime(getInt(COL_LEAVE_EARLY_TIME));
		dto.setLeaveEarlyThirtyMinutesOrMoreTime(getInt(COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME));
		dto.setLeaveEarlyLessThanThirtyMinutesTime(getInt(COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME));
		dto.setTimesLeaveEarly(getInt(COL_TIMES_LEAVE_EARLY));
		dto.setTimesHoliday(getInt(COL_TIMES_HOLIDAY));
		dto.setTimesLegalHoliday(getInt(COL_TIMES_LEGAL_HOLIDAY));
		dto.setTimesSpecificHoliday(getInt(COL_TIMES_SPECIFIC_HOLIDAY));
		dto.setTimesPaidHoliday(getDouble(COL_TIMES_PAID_HOLIDAY));
		dto.setPaidHolidayHour(getInt(COL_PAID_HOLIDAY_HOUR));
		dto.setTimesStockHoliday(getDouble(COL_TIMES_STOCK_HOLIDAY));
		dto.setTimesCompensation(getDouble(COL_TIMES_COMPENSATION));
		dto.setTimesLegalCompensation(getDouble(COL_TIMES_LEGAL_COMPENSATION));
		dto.setTimesSpecificCompensation(getDouble(COL_TIMES_SPECIFIC_COMPENSATION));
		dto.setTimesLateCompensation(getDouble(COL_TIMES_LATE_COMPENSATION));
		dto.setTimesHolidaySubstitute(getDouble(COL_TIMES_HOLIDAY_SUBSTITUTE));
		dto.setTimesLegalHolidaySubstitute(getDouble(COL_TIMES_LEGAL_HOLIDAY_SUBSTITUTE));
		dto.setTimesSpecificHolidaySubstitute(getDouble(COL_TIMES_SPECIFIC_HOLIDAY_SUBSTITUTE));
		dto.setTotalSpecialHoliday(getDouble(COL_TOTAL_SPECIAL_HOLIDAY));
		dto.setSpecialHolidayHour(getInt(COL_SPECIAL_HOLIDAY_HOUR));
		dto.setTotalOtherHoliday(getDouble(COL_TOTAL_OTHER_HOLIDAY));
		dto.setOtherHolidayHour(getInt(COL_OTHER_HOLIDAY_HOUR));
		dto.setTotalAbsence(getDouble(COL_TOTAL_ABSENCE));
		dto.setAbsenceHour(getInt(COL_ABSENCE_HOUR));
		dto.setTotalAllowance(getInt(COL_TOTAL_ALLOWANCE));
		dto.setSixtyHourOvertime(getInt(COL_SIXTY_HOUR_OVERTIME));
		dto.setWeekDayOvertime(getInt(COL_WEEK_DAY_OVERTIME));
		dto.setSpecificOvertime(getInt(COL_SPECIFIC_OVERTIME));
		dto.setTimesAlternative(getDouble(COL_TIMES_ALTERNATIVE));
		dto.setLegalCompensationOccurred(getDouble(COL_LEGAL_COMPENSATION_OCCURRED));
		dto.setSpecificCompensationOccurred(getDouble(COL_SPECIFIC_COMPENSATION_OCCURRED));
		dto.setLateCompensationOccurred(getDouble(COL_LATE_COMPENSATION_OCCURRED));
		dto.setLegalCompensationUnused(getDouble(COL_LEGAL_COMPENSATION_UNUSED));
		dto.setSpecificCompensationUnused(getDouble(COL_SPECIFIC_COMPENSATION_UNUSED));
		dto.setLateCompensationUnused(getDouble(COL_LATE_COMPENSATION_UNUSED));
		dto.setStatutoryHolidayWorkTimeIn(getInt(COL_STATUTORY_HOLIDAY_WORK_TIME_IN));
		dto.setStatutoryHolidayWorkTimeOut(getInt(COL_STATUTORY_HOLIDAY_WORK_TIME_OUT));
		dto.setPrescribedHolidayWorkTimeIn(getInt(COL_PRESCRIBED_HOLIDAY_WORK_TIME_IN));
		dto.setPrescribedHolidayWorkTimeOut(getInt(COL_PRESCRIBED_HOLIDAY_WORK_TIME_OUT));
		dto.setWeeklyOverFortyHourWorkTime(getInt(COL_WEEKLY_OVER_FORTY_HOUR_WORK_TIME));
		dto.setOvertimeInNoWeeklyForty(getInt(COL_OVERTIME_IN_NO_WEEKLY_FORTY));
		dto.setOvertimeOutNoWeeklyForty(getInt(COL_OVERTIME_OUT_NO_WEEKLY_FORTY));
		dto.setWeekDayOvertimeTotal(getInt(COL_WEEK_DAY_OVERTIME_TOTAL));
		dto.setWeekDayOvertimeInNoWeeklyForty(getInt(COL_WEEK_DAY_OVERTIME_IN_NO_WEEKLY_FORTY));
		dto.setWeekDayOvertimeOutNoWeeklyForty(getInt(COL_WEEK_DAY_OVERTIME_OUT_NO_WEEKLY_FORTY));
		dto.setWeekDayOvertimeIn(getInt(COL_WEEK_DAY_OVERTIME_IN));
		dto.setGeneralIntItem1(getInt(COL_GENERAL_INT_ITEM1));
		dto.setGeneralIntItem2(getInt(COL_GENERAL_INT_ITEM2));
		dto.setGeneralIntItem3(getInt(COL_GENERAL_INT_ITEM3));
		dto.setGeneralIntItem4(getInt(COL_GENERAL_INT_ITEM4));
		dto.setGeneralIntItem5(getInt(COL_GENERAL_INT_ITEM5));
		dto.setGeneralDoubleItem1(getInt(COL_GENERAL_DOUBLE_ITEM1));
		dto.setGeneralDoubleItem2(getInt(COL_GENERAL_DOUBLE_ITEM2));
		dto.setGeneralDoubleItem3(getInt(COL_GENERAL_DOUBLE_ITEM3));
		dto.setGeneralDoubleItem4(getInt(COL_GENERAL_DOUBLE_ITEM4));
		dto.setGeneralDoubleItem5(getInt(COL_GENERAL_DOUBLE_ITEM5));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<TotalTimeDataDtoInterface> mappingAll() throws MospException {
		List<TotalTimeDataDtoInterface> all = new ArrayList<TotalTimeDataDtoInterface>();
		while (next()) {
			all.add((TotalTimeDataDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public TotalTimeDataDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_CALCULATION_YEAR));
			sb.append(and());
			sb.append(equal(COL_CALCULATION_MONTH));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, calculationYear);
			setParam(index++, calculationMonth);
			executeQuery();
			TotalTimeDataDtoInterface dto = null;
			if (next()) {
				dto = (TotalTimeDataDtoInterface)mapping();
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			TotalTimeDataDtoInterface dto = (TotalTimeDataDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalTimeId());
			executeUpdate();
			chkUpdate(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			TotalTimeDataDtoInterface dto = (TotalTimeDataDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalTimeId());
			executeUpdate();
			chkDelete(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		TotalTimeDataDtoInterface dto = (TotalTimeDataDtoInterface)baseDto;
		setParam(index++, dto.getTmdTotalTimeId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getCalculationYear());
		setParam(index++, dto.getCalculationMonth());
		setParam(index++, dto.getCalculationDate());
		setParam(index++, dto.getWorkTime());
		setParam(index++, dto.getSpecificWorkTime());
		setParam(index++, dto.getContractWorkTime());
		setParam(index++, dto.getShortUnpaid());
		setParam(index++, dto.getTimesWorkDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getLegalWorkOnHoliday());
		setParam(index++, dto.getSpecificWorkOnHoliday());
		setParam(index++, dto.getTimesAchievement());
		setParam(index++, dto.getTimesTotalWorkDate());
		setParam(index++, dto.getDirectStart());
		setParam(index++, dto.getDirectEnd());
		setParam(index++, dto.getRestTime());
		setParam(index++, dto.getRestLateNight());
		setParam(index++, dto.getRestWorkOnSpecificHoliday());
		setParam(index++, dto.getRestWorkOnHoliday());
		setParam(index++, dto.getPublicTime());
		setParam(index++, dto.getPrivateTime());
		setParam(index++, dto.getMinutelyHolidayATime());
		setParam(index++, dto.getMinutelyHolidayBTime());
		setParam(index++, dto.getOvertime());
		setParam(index++, dto.getOvertimeIn());
		setParam(index++, dto.getOvertimeOut());
		setParam(index++, dto.getLateNight());
		setParam(index++, dto.getNightWorkWithinPrescribedWork());
		setParam(index++, dto.getNightOvertimeWork());
		setParam(index++, dto.getNightWorkOnHoliday());
		setParam(index++, dto.getWorkOnSpecificHoliday());
		setParam(index++, dto.getWorkOnHoliday());
		setParam(index++, dto.getDecreaseTime());
		setParam(index++, dto.getFortyFiveHourOvertime());
		setParam(index++, dto.getTimesOvertime());
		setParam(index++, dto.getTimesWorkingHoliday());
		setParam(index++, dto.getLateDays());
		setParam(index++, dto.getLateThirtyMinutesOrMore());
		setParam(index++, dto.getLateLessThanThirtyMinutes());
		setParam(index++, dto.getLateTime());
		setParam(index++, dto.getLateThirtyMinutesOrMoreTime());
		setParam(index++, dto.getLateLessThanThirtyMinutesTime());
		setParam(index++, dto.getTimesLate());
		setParam(index++, dto.getLeaveEarlyDays());
		setParam(index++, dto.getLeaveEarlyThirtyMinutesOrMore());
		setParam(index++, dto.getLeaveEarlyLessThanThirtyMinutes());
		setParam(index++, dto.getLeaveEarlyTime());
		setParam(index++, dto.getLeaveEarlyThirtyMinutesOrMoreTime());
		setParam(index++, dto.getLeaveEarlyLessThanThirtyMinutesTime());
		setParam(index++, dto.getTimesLeaveEarly());
		setParam(index++, dto.getTimesHoliday());
		setParam(index++, dto.getTimesLegalHoliday());
		setParam(index++, dto.getTimesSpecificHoliday());
		setParam(index++, dto.getTimesPaidHoliday());
		setParam(index++, dto.getPaidHolidayHour());
		setParam(index++, dto.getTimesStockHoliday());
		setParam(index++, dto.getTimesCompensation());
		setParam(index++, dto.getTimesLegalCompensation());
		setParam(index++, dto.getTimesSpecificCompensation());
		setParam(index++, dto.getTimesLateCompensation());
		setParam(index++, dto.getTimesHolidaySubstitute());
		setParam(index++, dto.getTimesLegalHolidaySubstitute());
		setParam(index++, dto.getTimesSpecificHolidaySubstitute());
		setParam(index++, dto.getTotalSpecialHoliday());
		setParam(index++, dto.getSpecialHolidayHour());
		setParam(index++, dto.getTotalOtherHoliday());
		setParam(index++, dto.getOtherHolidayHour());
		setParam(index++, dto.getTotalAbsence());
		setParam(index++, dto.getAbsenceHour());
		setParam(index++, dto.getTotalAllowance());
		setParam(index++, dto.getSixtyHourOvertime());
		setParam(index++, dto.getWeekDayOvertime());
		setParam(index++, dto.getSpecificOvertime());
		setParam(index++, dto.getTimesAlternative());
		setParam(index++, dto.getLegalCompensationOccurred());
		setParam(index++, dto.getSpecificCompensationOccurred());
		setParam(index++, dto.getLateCompensationOccurred());
		setParam(index++, dto.getLegalCompensationUnused());
		setParam(index++, dto.getSpecificCompensationUnused());
		setParam(index++, dto.getLateCompensationUnused());
		setParam(index++, dto.getStatutoryHolidayWorkTimeIn());
		setParam(index++, dto.getStatutoryHolidayWorkTimeOut());
		setParam(index++, dto.getPrescribedHolidayWorkTimeIn());
		setParam(index++, dto.getPrescribedHolidayWorkTimeOut());
		setParam(index++, dto.getWeeklyOverFortyHourWorkTime());
		setParam(index++, dto.getOvertimeInNoWeeklyForty());
		setParam(index++, dto.getOvertimeOutNoWeeklyForty());
		setParam(index++, dto.getWeekDayOvertimeTotal());
		setParam(index++, dto.getWeekDayOvertimeInNoWeeklyForty());
		setParam(index++, dto.getWeekDayOvertimeOutNoWeeklyForty());
		setParam(index++, dto.getWeekDayOvertimeIn());
		setParam(index++, dto.getGeneralIntItem1());
		setParam(index++, dto.getGeneralIntItem2());
		setParam(index++, dto.getGeneralIntItem3());
		setParam(index++, dto.getGeneralIntItem4());
		setParam(index++, dto.getGeneralIntItem5());
		setParam(index++, dto.getGeneralDoubleItem1());
		setParam(index++, dto.getGeneralDoubleItem2());
		setParam(index++, dto.getGeneralDoubleItem3());
		setParam(index++, dto.getGeneralDoubleItem4());
		setParam(index++, dto.getGeneralDoubleItem5());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public List<TotalTimeDataDtoInterface> findForTerm(String personalId, int startYear, int startMonth, int endYear,
			int endMonth) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(leftParenthesis());
			sb.append(leftParenthesis());
			sb.append(equal(COL_CALCULATION_YEAR));
			sb.append(and());
			sb.append(greaterEqual(COL_CALCULATION_MONTH));
			sb.append(rightParenthesis());
			sb.append(or());
			sb.append(equal(COL_CALCULATION_YEAR));
			sb.append(and());
			sb.append(lessEqual(COL_CALCULATION_MONTH));
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, startYear);
			setParam(index++, startMonth);
			setParam(index++, endYear);
			setParam(index++, endMonth);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public int getMinYear() throws MospException {
		try {
			// SQLを作成
			StringBuilder sb = new StringBuilder(select());
			sb.append(min(COL_CALCULATION_YEAR));
			sb.append(from(TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			prepareStatement(sb.toString());
			// SQLを実行
			executeQuery();
			// 結果を取得
			next();
			return rs.getInt(1);
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
