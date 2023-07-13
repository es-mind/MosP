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

import java.sql.SQLException;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmTimeSettingDto;

/**
 * 勤怠設定管理DAOクラス
 */
public class TmmTimeSettingDao extends PlatformDao implements TimeSettingDaoInterface {
	
	/**
	 * 締日マスタ。
	 */
	public static final String	TABLE								= "tmm_time_setting";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_TIME_SETTING_ID				= "tmm_time_setting_id";
	
	/**
	 * 勤怠設定コード。
	 */
	public static final String	COL_WORK_SETTING_CODE				= "work_setting_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE					= "activate_date";
	
	/**
	 * 勤怠設定名称。
	 */
	public static final String	COL_WORK_SETTING_NAME				= "work_setting_name";
	
	/**
	 * 勤怠設定略称。
	 */
	public static final String	COL_WORK_SETTING_ABBR				= "work_setting_abbr";
	
	/**
	 * 締日コード。
	 */
	public static final String	COL_CUTOFF_CODE						= "cutoff_code";
	
	/**
	 * 勤怠管理対象フラグ。
	 */
	public static final String	COL_TIME_MANAGEMENT_FLAG			= "time_management_flag";
	
	/**
	 * 日々申請対象フラグ。
	 */
	public static final String	COL_DAILY_APPROVAL_FLAG				= "daily_approval_flag";
	
	/**
	 * 勤務前残業フラグ。
	 */
	public static final String	COL_BEFORE_OVERTIME_FLAG			= "before_overtime_flag";
	
	/**
	 * 所定休日取扱。
	 */
	public static final String	COL_SPECIFIC_HOLIDAY_HANDLING		= "specific_holiday_handling";
	
	/**
	 * ポータル出退勤ボタン表示。
	 */
	public static final String	COL_PORTAL_TIME_BUTTONS				= "portal_time_buttons";
	
	/**
	 * ポータル休憩ボタン表示。
	 */
	public static final String	COL_PORTAL_REST_BUTTONS				= "portal_rest_buttons";
	
	/**
	 * 勤務予定時間表示。
	 */
	public static final String	COL_USE_SCHEDULED_TIME				= "use_scheduled_time";
	
	/**
	 * 日出勤丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_START_UNIT			= "round_daily_start_unit";
	
	/**
	 * 日出勤丸め。
	 */
	public static final String	COL_ROUND_DAILY_START				= "round_daily_start";
	
	/**
	 * 日退勤丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_END_UNIT			= "round_daily_end_unit";
	
	/**
	 * 日退勤丸め。
	 */
	public static final String	COL_ROUND_DAILY_END					= "round_daily_end";
	
	/**
	 * 日勤務時間丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_TIME_WORK			= "round_daily_time_work";
	
	/**
	 * 日勤務時間丸め。
	 */
	public static final String	COL_ROUND_DAILY_WORK				= "round_daily_work";
	
	/**
	 * 日休憩入丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_REST_START_UNIT		= "round_daily_rest_start_unit";
	
	/**
	 * 日休憩入り丸め。
	 */
	public static final String	COL_ROUND_DAILY_REST_START			= "round_daily_rest_start";
	
	/**
	 * 日休憩戻丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_REST_END_UNIT		= "round_daily_rest_end_unit";
	
	/**
	 * 日休憩戻り丸め。
	 */
	public static final String	COL_ROUND_DAILY_REST_END			= "round_daily_rest_end";
	
	/**
	 * 日休憩時間丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_REST_TIME_UNIT		= "round_daily_rest_time_unit";
	
	/**
	 * 日休憩時間丸め。
	 */
	public static final String	COL_ROUND_DAILY_REST_TIME			= "round_daily_rest_time";
	
	/**
	 * 日遅刻丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_LATE_UNIT			= "round_daily_late_unit";
	
	/**
	 * 日遅刻丸め。
	 */
	public static final String	COL_ROUND_DAILY_LATE				= "round_daily_late";
	
	/**
	 * 日早退丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_LEAVE_EARLY_UNIT	= "round_daily_leave_early_unit";
	
	/**
	 * 日早退丸め。
	 */
	public static final String	COL_ROUND_DAILY_LEAVE_EARLY			= "round_daily_leave_early";
	
	/**
	 * 日私用外出入丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_PRIVATE_TIME_START	= "round_daily_private_time_start";
	
	/**
	 * 日私用外出入丸め。
	 */
	public static final String	COL_ROUND_DAILY_PRIVATE_IN			= "round_daily_private_in";
	
	/**
	 * 日私用外出戻丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_PRIVATE_TIME_END	= "round_daily_private_time_end";
	
	/**
	 * 日私用外出戻丸め。
	 */
	public static final String	COL_ROUND_DAILY_PRIVATE_OUT			= "round_daily_private_out";
	
	/**
	 * 日公用外出入丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_PUBLIC_TIME_START	= "round_daily_public_time_start";
	
	/**
	 * 日公用外出入丸め。
	 */
	public static final String	COL_ROUND_DAILY_PUBLIC_IN			= "round_daily_public_in";
	
	/**
	 * 日公用外出戻丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_PUBLIC_TIME_END		= "round_daily_public_time_end";
	
	/**
	 * 日公用外出戻丸め。
	 */
	public static final String	COL_ROUND_DAILY_PUBLIC_OUT			= "round_daily_public_out";
	
	/**
	 * 日減額対象丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_DECREASE_TIME_UNIT	= "round_daily_decrease_time_unit";
	
	/**
	 * 日減額対象時間丸め。
	 */
	public static final String	COL_ROUND_DAILY_DECREASE_TIME		= "round_daily_decrease_time";
	
	/**
	 * 日残業時間丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_OVERTIME_UNIT		= "round_daily_overtime_unit";
	
	/**
	 * 日残業時間丸め。
	 */
	public static final String	COL_ROUND_DAILY_OVERTIME			= "round_daily_overtime";
	
	/**
	 * 日無給時短時間丸め単位。
	 */
	public static final String	COL_ROUND_DAILY_SHORT_UNPAID_UNIT	= "round_daily_short_unpaid_unit";
	
	/**
	 * 日無給時短時間丸め。
	 */
	public static final String	COL_ROUND_DAILY_SHORT_UNPAID		= "round_daily_short_unpaid";
	
	/**
	 * 月勤務時間丸め単位。
	 */
	public static final String	COL_ROUND_MONTHLY_WORK_UNIT			= "round_monthly_work_unit";
	
	/**
	 * 月勤務時間丸め。
	 */
	public static final String	COL_ROUND_MONTHLY_WORK				= "round_monthly_work";
	
	/**
	 * 月休憩時間丸め単位。
	 */
	public static final String	COL_ROUND_MONTHLY_REST_UNIT			= "round_monthly_rest_unit";
	
	/**
	 * 月休憩時間丸め。
	 */
	public static final String	COL_ROUND_MONTHLY_REST				= "round_monthly_rest";
	
	/**
	 * 月遅刻丸め単位。
	 */
	public static final String	COL_ROUND_MONTHLY_LATE_UNIT			= "round_monthly_late_unit";
	
	/**
	 * 月遅刻時間丸め。
	 */
	public static final String	COL_ROUND_MONTHLY_LATE				= "round_monthly_late";
	
	/**
	 * 月早退丸め単位。
	 */
	public static final String	COL_ROUND_MONTHLY_EARLY_UNIT		= "round_monthly_early_unit";
	
	/**
	 * 月早退時間丸め。
	 */
	public static final String	COL_ROUND_MONTHLY_EARLY				= "round_monthly_early";
	
	/**
	 * 月私用外出丸め単位。
	 */
	public static final String	COL_ROUND_MONTHLY_PRIVATE_TIME		= "round_monthly_private_time";
	
	/**
	 * 月私用外出時間丸め。
	 */
	public static final String	COL_ROUND_MONTHLY_PRIVATE			= "round_monthly_private";
	
	/**
	 * 月公用外出丸め単位。
	 */
	public static final String	COL_ROUND_MONTHLY_PUBLIC_TIME		= "round_monthly_public_time";
	
	/**
	 * 月公用外出時間丸め。
	 */
	public static final String	COL_ROUND_MONTHLY_PUBLIC			= "round_monthly_public";
	
	/**
	 * 月減額対象丸め単位。
	 */
	public static final String	COL_ROUND_MONTHLY_DECREASE_TIME		= "round_monthly_decrease_time";
	
	/**
	 * 月減額対象時間丸め。
	 */
	public static final String	COL_ROUND_MONTHLY_DECREASE			= "round_monthly_decrease";
	
	/**
	 * 月残業時間丸め単位。
	 */
	public static final String	COL_ROUND_MONTHLY_OVERTIME_UNIT		= "round_monthly_overtime_unit";
	
	/**
	 * 月残業時間丸め。
	 */
	public static final String	COL_ROUND_MONTHLY_OVERTIME			= "round_monthly_overtime";
	
	/**
	 * 月無給時短時間丸め単位。
	 */
	public static final String	COL_ROUND_MONTHLY_SHORT_UNPAID_UNIT	= "round_monthly_short_unpaid_unit";
	
	/**
	 * 月無給時短時間丸め。
	 */
	public static final String	COL_ROUND_MONTHLY_SHORT_UNPAID		= "round_monthly_short_unpaid";
	
	/**
	 * 週の起算曜日。
	 */
	public static final String	COL_START_WEEK						= "start_week";
	
	/**
	 * 月の起算日。
	 */
	public static final String	COL_START_MONTH						= "start_month";
	
	/**
	 * 年の起算月。
	 */
	public static final String	COL_START_YEAR						= "start_year";
	
	/**
	 * 所定労働時間。
	 */
	public static final String	COL_GENERAL_WORK_TIME				= "general_work_time";
	
	/**
	 * 一日の起算時。
	 */
	public static final String	COL_START_DAY_TIME					= "start_day_time";
	
	/**
	 * 遅刻早退限度時間(全日)。
	 */
	public static final String	COL_LATE_EARLY_FULL					= "late_early_full";
	
	/**
	 * 遅刻早退限度時間(半日)。
	 */
	public static final String	COL_LATE_EARLY_HALF					= "late_early_half";
	
	/**
	 * 振休取得期限月(休出前)。
	 */
	public static final String	COL_TRANSFER_AHEAD_LIMIT_MONTH		= "transfer_ahead_limit_month";
	
	/**
	 * 振休取得期限日(休出前)。
	 */
	public static final String	COL_TRANSFER_AHEAD_LIMIT_DATE		= "transfer_ahead_limit_date";
	
	/**
	 * 振休取得期限月(休出後)。
	 */
	public static final String	COL_TRANSFER_LATER_LIMIT_MONTH		= "transfer_later_limit_month";
	
	/**
	 * 振休取得期限日(休出後)。
	 */
	public static final String	COL_TRANSFER_LATER_LIMIT_DATE		= "transfer_later_limit_date";
	
	/**
	 * 代休取得期限月。
	 */
	public static final String	COL_SUB_HOLIDAY_LIMIT_MONTH			= "sub_holiday_limit_month";
	
	/**
	 * 代休取得期限日。
	 */
	public static final String	COL_SUB_HOLIDAY_LIMIT_DATE			= "sub_holiday_limit_date";
	
	/**
	 * 半休入替取得(振休)。
	 */
	public static final String	COL_TRANSFER_EXCHANGE				= "transfer_exchange";
	
	/**
	 * 半休入替取得(代休)。
	 */
	public static final String	COL_SUB_HOLIDAY_EXCHANGE			= "sub_holiday_exchange";
	
	/**
	 * 代休基準時間(全休)。
	 */
	public static final String	COL_SUB_HOLIDAY_ALL_NORM			= "sub_holiday_all_norm";
	
	/**
	 * 代休基準時間(半休)。
	 */
	public static final String	COL_SUB_HOLIDAY_HALF_NORM			= "sub_holiday_half_norm";
	
	/**
	 * 60時間超割増機能。
	 */
	public static final String	COL_SIXTY_HOUR_FUNCTION_FLAG		= "sixty_hour_function_flag";
	
	/**
	 * 60時間超代替休暇。
	 */
	public static final String	COL_SIXTY_HOUR_ALTERNATIVE_FLAG		= "sixty_hour_alternative_flag";
	
	/**
	 * 月60時間超割増。
	 */
	public static final String	COL_MONTH_SIXTY_HOUR_SURCHARGE		= "month_sixty_hour_surcharge";
	
	/**
	 * 平日残業割増。
	 */
	public static final String	COL_WEEKDAY_OVER					= "weekday_over";
	
	/**
	 * 代替休暇平日。
	 */
	public static final String	COL_WEEKDAY_ALTERNATIVE				= "weekday_alternative";
	
	/**
	 * 代替休暇放棄。
	 */
	public static final String	COL_ALTERNATIVE_CANCEL				= "alternative_cancel";
	
	/**
	 * 代替休暇所定休日。
	 */
	public static final String	COL_ALTERNATIVE_SPECIFIC			= "alternative_specific";
	
	/**
	 * 代替休暇法定休日。
	 */
	public static final String	COL_ALTERNATIVE_LEGAL				= "alternative_legal";
	
	/**
	 * 所定休日割増率。
	 */
	public static final String	COL_SPECIFIC_HOLIDAY				= "specific_holiday";
	
	/**
	 * 法定休日割増率。
	 */
	public static final String	COL_LEGAL_HOLIDAY					= "legal_holiday";
	
	/**
	 * 見込月。
	 */
	public static final String	COL_PROSPECTS_MONTHS				= "prospects_months";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG					= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1								= COL_TMM_TIME_SETTING_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmTimeSettingDao() {
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmTimeSettingDto dto = new TmmTimeSettingDto();
		dto.setTmmTimeSettingId(getLong(COL_TMM_TIME_SETTING_ID));
		dto.setWorkSettingCode(getString(COL_WORK_SETTING_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setWorkSettingName(getString(COL_WORK_SETTING_NAME));
		dto.setWorkSettingAbbr(getString(COL_WORK_SETTING_ABBR));
		dto.setCutoffCode(getString(COL_CUTOFF_CODE));
		dto.setTimeManagementFlag(getInt(COL_TIME_MANAGEMENT_FLAG));
		dto.setDailyApprovalFlag(getInt(COL_DAILY_APPROVAL_FLAG));
		dto.setBeforeOvertimeFlag(getInt(COL_BEFORE_OVERTIME_FLAG));
		dto.setSpecificHolidayHandling(getInt(COL_SPECIFIC_HOLIDAY_HANDLING));
		dto.setPortalTimeButtons(getInt(COL_PORTAL_TIME_BUTTONS));
		dto.setPortalRestButtons(getInt(COL_PORTAL_REST_BUTTONS));
		dto.setUseScheduledTime(getInt(COL_USE_SCHEDULED_TIME));
		dto.setRoundDailyStartUnit(getInt(COL_ROUND_DAILY_START_UNIT));
		dto.setRoundDailyStart(getInt(COL_ROUND_DAILY_START));
		dto.setRoundDailyEndUnit(getInt(COL_ROUND_DAILY_END_UNIT));
		dto.setRoundDailyEnd(getInt(COL_ROUND_DAILY_END));
		dto.setRoundDailyTimeWork(getInt(COL_ROUND_DAILY_TIME_WORK));
		dto.setRoundDailyWork(getInt(COL_ROUND_DAILY_WORK));
		dto.setRoundDailyRestStartUnit(getInt(COL_ROUND_DAILY_REST_START_UNIT));
		dto.setRoundDailyRestStart(getInt(COL_ROUND_DAILY_REST_START));
		dto.setRoundDailyRestEndUnit(getInt(COL_ROUND_DAILY_REST_END_UNIT));
		dto.setRoundDailyRestEnd(getInt(COL_ROUND_DAILY_REST_END));
		dto.setRoundDailyRestTimeUnit(getInt(COL_ROUND_DAILY_REST_TIME_UNIT));
		dto.setRoundDailyRestTime(getInt(COL_ROUND_DAILY_REST_TIME));
		dto.setRoundDailyLateUnit(getInt(COL_ROUND_DAILY_LATE_UNIT));
		dto.setRoundDailyLate(getInt(COL_ROUND_DAILY_LATE));
		dto.setRoundDailyLeaveEarlyUnit(getInt(COL_ROUND_DAILY_LEAVE_EARLY_UNIT));
		dto.setRoundDailyLeaveEarly(getInt(COL_ROUND_DAILY_LEAVE_EARLY));
		dto.setRoundDailyPrivateStartUnit(getInt(COL_ROUND_DAILY_PRIVATE_TIME_START));
		dto.setRoundDailyPrivateStart(getInt(COL_ROUND_DAILY_PRIVATE_IN));
		dto.setRoundDailyPrivateEndUnit(getInt(COL_ROUND_DAILY_PRIVATE_TIME_END));
		dto.setRoundDailyPrivateEnd(getInt(COL_ROUND_DAILY_PRIVATE_OUT));
		dto.setRoundDailyPublicStartUnit(getInt(COL_ROUND_DAILY_PUBLIC_TIME_START));
		dto.setRoundDailyPublicStart(getInt(COL_ROUND_DAILY_PUBLIC_IN));
		dto.setRoundDailyPublicEndUnit(getInt(COL_ROUND_DAILY_PUBLIC_TIME_END));
		dto.setRoundDailyPublicEnd(getInt(COL_ROUND_DAILY_PUBLIC_OUT));
		dto.setRoundDailyDecreaseTimeUnit(getInt(COL_ROUND_DAILY_DECREASE_TIME_UNIT));
		dto.setRoundDailyDecreaseTime(getInt(COL_ROUND_DAILY_DECREASE_TIME));
		dto.setRoundDailyOvertimeUnit(getInt(COL_ROUND_DAILY_OVERTIME_UNIT));
		dto.setRoundDailyOvertime(getInt(COL_ROUND_DAILY_OVERTIME));
		dto.setRoundDailyShortUnpaidUnit(getInt(COL_ROUND_DAILY_SHORT_UNPAID_UNIT));
		dto.setRoundDailyShortUnpaid(getInt(COL_ROUND_DAILY_SHORT_UNPAID_UNIT));
		dto.setRoundMonthlyWorkUnit(getInt(COL_ROUND_MONTHLY_WORK_UNIT));
		dto.setRoundMonthlyWork(getInt(COL_ROUND_MONTHLY_WORK));
		dto.setRoundMonthlyRestUnit(getInt(COL_ROUND_MONTHLY_REST_UNIT));
		dto.setRoundMonthlyRest(getInt(COL_ROUND_MONTHLY_REST));
		dto.setRoundMonthlyLateUnit(getInt(COL_ROUND_MONTHLY_LATE_UNIT));
		dto.setRoundMonthlyLate(getInt(COL_ROUND_MONTHLY_LATE));
		dto.setRoundMonthlyEarlyUnit(getInt(COL_ROUND_MONTHLY_EARLY_UNIT));
		dto.setRoundMonthlyEarly(getInt(COL_ROUND_MONTHLY_EARLY));
		dto.setRoundMonthlyPrivateTime(getInt(COL_ROUND_MONTHLY_PRIVATE_TIME));
		dto.setRoundMonthlyPrivate(getInt(COL_ROUND_MONTHLY_PRIVATE));
		dto.setRoundMonthlyPublicTime(getInt(COL_ROUND_MONTHLY_PUBLIC_TIME));
		dto.setRoundMonthlyPublic(getInt(COL_ROUND_MONTHLY_PUBLIC));
		dto.setRoundMonthlyDecreaseTime(getInt(COL_ROUND_MONTHLY_DECREASE_TIME));
		dto.setRoundMonthlyDecrease(getInt(COL_ROUND_MONTHLY_DECREASE));
		dto.setRoundMonthlyOvertimeUnit(getInt(COL_ROUND_MONTHLY_OVERTIME_UNIT));
		dto.setRoundMonthlyOvertime(getInt(COL_ROUND_MONTHLY_OVERTIME));
		dto.setRoundMonthlyShortUnpaidUnit(getInt(COL_ROUND_MONTHLY_SHORT_UNPAID_UNIT));
		dto.setRoundMonthlyShortUnpaid(getInt(COL_ROUND_MONTHLY_SHORT_UNPAID_UNIT));
		dto.setStartWeek(getInt(COL_START_WEEK));
		dto.setStartMonth(getInt(COL_START_MONTH));
		dto.setStartYear(getInt(COL_START_YEAR));
		dto.setGeneralWorkTime(getTime(COL_GENERAL_WORK_TIME));
		dto.setStartDayTime(getTime(COL_START_DAY_TIME));
		dto.setLateEarlyFull(getTime(COL_LATE_EARLY_FULL));
		dto.setLateEarlyHalf(getTime(COL_LATE_EARLY_HALF));
		dto.setTransferAheadLimitMonth(getInt(COL_TRANSFER_AHEAD_LIMIT_MONTH));
		dto.setTransferAheadLimitDate(getInt(COL_TRANSFER_AHEAD_LIMIT_DATE));
		dto.setTransferLaterLimitMonth(getInt(COL_TRANSFER_LATER_LIMIT_MONTH));
		dto.setTransferLaterLimitDate(getInt(COL_TRANSFER_LATER_LIMIT_DATE));
		dto.setSubHolidayLimitMonth(getInt(COL_SUB_HOLIDAY_LIMIT_MONTH));
		dto.setSubHolidayLimitDate(getInt(COL_SUB_HOLIDAY_LIMIT_DATE));
		dto.setTransferExchange(getInt(COL_TRANSFER_EXCHANGE));
		dto.setSubHolidayExchange(getInt(COL_SUB_HOLIDAY_EXCHANGE));
		dto.setSubHolidayAllNorm(getTime(COL_SUB_HOLIDAY_ALL_NORM));
		dto.setSubHolidayHalfNorm(getTime(COL_SUB_HOLIDAY_HALF_NORM));
		dto.setSixtyHourFunctionFlag(getInt(COL_SIXTY_HOUR_FUNCTION_FLAG));
		dto.setSixtyHourAlternativeFlag(getInt(COL_SIXTY_HOUR_ALTERNATIVE_FLAG));
		dto.setMonthSixtyHourSurcharge(getInt(COL_MONTH_SIXTY_HOUR_SURCHARGE));
		dto.setWeekdayOver(getInt(COL_WEEKDAY_OVER));
		dto.setWeekdayAlternative(getInt(COL_WEEKDAY_ALTERNATIVE));
		dto.setAlternativeCancel(getInt(COL_ALTERNATIVE_CANCEL));
		dto.setAlternativeSpecific(getInt(COL_ALTERNATIVE_SPECIFIC));
		dto.setAlternativeLegal(getInt(COL_ALTERNATIVE_LEGAL));
		dto.setSpecificHoliday(getInt(COL_SPECIFIC_HOLIDAY));
		dto.setLegalHoliday(getInt(COL_LEGAL_HOLIDAY));
		dto.setProspectsMonths(getString(COL_PROSPECTS_MONTHS));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<TimeSettingDtoInterface> mappingAll() throws MospException {
		List<TimeSettingDtoInterface> all = new ArrayList<TimeSettingDtoInterface>();
		while (next()) {
			all.add((TimeSettingDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<TimeSettingDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.DELETE_FLAG_OFF));
			sb.append(and());
			sb.append(getQueryForMaxActivateDate());
			sb.append(getOrderByColumn(COL_WORK_SETTING_CODE));
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
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
	public TimeSettingDtoInterface findForKey(String workSettingCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_SETTING_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workSettingCode);
			setParam(index++, activateDate);
			executeQuery();
			TimeSettingDtoInterface dto = null;
			if (next()) {
				dto = (TimeSettingDtoInterface)mapping();
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
	public TimeSettingDtoInterface findForInfo(String workSettingCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_SETTING_CODE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workSettingCode);
			setParam(index++, activateDate);
			executeQuery();
			TimeSettingDtoInterface dto = null;
			if (next()) {
				dto = (TimeSettingDtoInterface)mapping();
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
	public List<TimeSettingDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			Date activateDate = (Date)param.get("activateDate");
			String workSettingCode = String.valueOf(param.get("workSettingCode"));
			String workSettingName = String.valueOf(param.get("workSettingName"));
			String workSettingAbbr = String.valueOf(param.get("workSettingAbbr"));
			String cutoffCode = String.valueOf(param.get("cutoffCode"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_WORK_SETTING_CODE));
			sb.append(and());
			sb.append(like(COL_WORK_SETTING_NAME));
			sb.append(and());
			sb.append(like(COL_WORK_SETTING_ABBR));
			if (!cutoffCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_CUTOFF_CODE));
			}
			if (!inactivateFlag.equals("")) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			if (activateDate != null) {
				sb.append(and());
				sb.append(getQueryForMaxActivateDate());
			}
			prepareStatement(sb.toString());
			setParam(index++, startWithParam(workSettingCode));
			setParam(index++, containsParam(workSettingName));
			setParam(index++, containsParam(workSettingAbbr));
			if (!cutoffCode.equals("")) {
				setParam(index++, cutoffCode);
			}
			if (!inactivateFlag.equals("")) {
				setParam(index++, Integer.parseInt(inactivateFlag));
			}
			if (activateDate != null) {
				setParam(index++, activateDate);
			}
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
	public List<TimeSettingDtoInterface> findForHistory(String workSettingCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_SETTING_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workSettingCode);
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
	public List<TimeSettingDtoInterface> findForInfoList(String cutoffCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.DELETE_FLAG_OFF));
			sb.append(and());
			sb.append(equal(COL_CUTOFF_CODE));
			sb.append(and());
			sb.append(getQueryForMaxActivateDate());
			prepareStatement(sb.toString());
			setParam(index++, cutoffCode);
			setParam(index++, activateDate);
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			TimeSettingDtoInterface dto = (TimeSettingDtoInterface)baseDto;
			setParam(index++, dto.getTmmTimeSettingId());
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
			TimeSettingDtoInterface dto = (TimeSettingDtoInterface)baseDto;
			setParam(index++, dto.getTmmTimeSettingId());
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
		TimeSettingDtoInterface dto = (TimeSettingDtoInterface)baseDto;
		setParam(index++, dto.getTmmTimeSettingId());
		setParam(index++, dto.getWorkSettingCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getWorkSettingName());
		setParam(index++, dto.getWorkSettingAbbr());
		setParam(index++, dto.getCutoffCode());
		setParam(index++, dto.getTimeManagementFlag());
		setParam(index++, dto.getDailyApprovalFlag());
		setParam(index++, dto.getBeforeOvertimeFlag());
		setParam(index++, dto.getSpecificHolidayHandling());
		setParam(index++, dto.getPortalTimeButtons());
		setParam(index++, dto.getPortalRestButtons());
		setParam(index++, dto.getUseScheduledTime());
		setParam(index++, dto.getRoundDailyStartUnit());
		setParam(index++, dto.getRoundDailyStart());
		setParam(index++, dto.getRoundDailyEndUnit());
		setParam(index++, dto.getRoundDailyEnd());
		setParam(index++, dto.getRoundDailyTimeWork());
		setParam(index++, dto.getRoundDailyWork());
		setParam(index++, dto.getRoundDailyRestStartUnit());
		setParam(index++, dto.getRoundDailyRestStart());
		setParam(index++, dto.getRoundDailyRestEndUnit());
		setParam(index++, dto.getRoundDailyRestEnd());
		setParam(index++, dto.getRoundDailyRestTimeUnit());
		setParam(index++, dto.getRoundDailyRestTime());
		setParam(index++, dto.getRoundDailyLateUnit());
		setParam(index++, dto.getRoundDailyLate());
		setParam(index++, dto.getRoundDailyLeaveEarlyUnit());
		setParam(index++, dto.getRoundDailyLeaveEarly());
		setParam(index++, dto.getRoundDailyPrivateStartUnit());
		setParam(index++, dto.getRoundDailyPrivateStart());
		setParam(index++, dto.getRoundDailyPrivateEndUnit());
		setParam(index++, dto.getRoundDailyPrivateEnd());
		setParam(index++, dto.getRoundDailyPublicStartUnit());
		setParam(index++, dto.getRoundDailyPublicStart());
		setParam(index++, dto.getRoundDailyPublicEndUnit());
		setParam(index++, dto.getRoundDailyPublicEnd());
		setParam(index++, dto.getRoundDailyDecreaseTimeUnit());
		setParam(index++, dto.getRoundDailyDecreaseTime());
		setParam(index++, dto.getRoundDailyOvertimeUnit());
		setParam(index++, dto.getRoundDailyOvertime());
		setParam(index++, dto.getRoundDailyShortUnpaidUnit());
		setParam(index++, dto.getRoundDailyShortUnpaid());
		setParam(index++, dto.getRoundMonthlyWorkUnit());
		setParam(index++, dto.getRoundMonthlyWork());
		setParam(index++, dto.getRoundMonthlyRestUnit());
		setParam(index++, dto.getRoundMonthlyRest());
		setParam(index++, dto.getRoundMonthlyLateUnit());
		setParam(index++, dto.getRoundMonthlyLate());
		setParam(index++, dto.getRoundMonthlyEarlyUnit());
		setParam(index++, dto.getRoundMonthlyEarly());
		setParam(index++, dto.getRoundMonthlyPrivateTime());
		setParam(index++, dto.getRoundMonthlyPrivate());
		setParam(index++, dto.getRoundMonthlyPublicTime());
		setParam(index++, dto.getRoundMonthlyPublic());
		setParam(index++, dto.getRoundMonthlyDecreaseTime());
		setParam(index++, dto.getRoundMonthlyDecrease());
		setParam(index++, dto.getRoundMonthlyOvertimeUnit());
		setParam(index++, dto.getRoundMonthlyOvertime());
		setParam(index++, dto.getRoundMonthlyShortUnpaidUnit());
		setParam(index++, dto.getRoundMonthlyShortUnpaid());
		setParam(index++, dto.getStartWeek());
		setParam(index++, dto.getStartMonth());
		setParam(index++, dto.getStartYear());
		setTime(index++, dto.getGeneralWorkTime());
		setTime(index++, dto.getStartDayTime());
		setTime(index++, dto.getLateEarlyFull());
		setTime(index++, dto.getLateEarlyHalf());
		setParam(index++, dto.getTransferAheadLimitMonth());
		setParam(index++, dto.getTransferAheadLimitDate());
		setParam(index++, dto.getTransferLaterLimitMonth());
		setParam(index++, dto.getTransferLaterLimitDate());
		setParam(index++, dto.getSubHolidayLimitMonth());
		setParam(index++, dto.getSubHolidayLimitDate());
		setParam(index++, dto.getTransferExchange());
		setParam(index++, dto.getSubHolidayExchange());
		setTime(index++, dto.getSubHolidayAllNorm());
		setTime(index++, dto.getSubHolidayHalfNorm());
		setParam(index++, dto.getSixtyHourFunctionFlag());
		setParam(index++, dto.getSixtyHourAlternativeFlag());
		setParam(index++, dto.getMonthSixtyHourSurcharge());
		setParam(index++, dto.getWeekdayOver());
		setParam(index++, dto.getWeekdayAlternative());
		setParam(index++, dto.getAlternativeCancel());
		setParam(index++, dto.getAlternativeSpecific());
		setParam(index++, dto.getAlternativeLegal());
		setParam(index++, dto.getSpecificHoliday());
		setParam(index++, dto.getLegalHoliday());
		setParam(index++, dto.getProspectsMonths());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	private StringBuffer getQueryForMaxActivateDate() {
		StringBuffer sb = new StringBuffer();
		sb.append(COL_ACTIVATE_DATE);
		sb.append(in());
		sb.append("(SELECT MAX(");
		sb.append(COL_ACTIVATE_DATE);
		sb.append(")");
		sb.append(from(TABLE));
		sb.append("AS A ");
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(TABLE + "." + COL_WORK_SETTING_CODE);
		sb.append(" = A." + COL_WORK_SETTING_CODE);
		sb.append(and());
		sb.append(COL_ACTIVATE_DATE);
		sb.append(" <= ?)");
		return sb;
	}
	
	/**
	 * パラメータ設定(Date)。<br>
	 * java.sql.Timeとして設定する。<br>
	 * @param index インデックス
	 * @param param パラメータ
	 * @throws MospException SQL例外が発生した場合
	 */
	protected void setTime(int index, Date param) throws MospException {
		try {
			if (ps != null) {
				if (param == null) {
					ps.setTime(index, null);
				} else {
					Calendar cal = Calendar.getInstance();
					cal.setTime(param);
					ps.setTime(index, new java.sql.Time(cal.getTimeInMillis()));
					
				}
			}
		} catch (SQLException e) {
			throw new MospException(e);
		}
	}
	
	@Override
	public List<TimeSettingDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate) throws MospException {
		try {
			index = 1;
			// SELECT文追加
			StringBuffer sb = getSelectQuery(getClass());
			// WHERE句追加
			sb.append(where());
			// 削除されていない情報を取得
			sb.append(deleteFlagOff());
			// 有効日範囲による条件
			if (fromActivateDate != null) {
				sb.append(and());
				sb.append(greater(COL_ACTIVATE_DATE));
			}
			if (toActivateDate != null) {
				sb.append(and());
				sb.append(less(COL_ACTIVATE_DATE));
			}
			// ソート
			sb.append(getOrderByColumn(COL_CUTOFF_CODE, COL_ACTIVATE_DATE));
			// ステートメント準備
			prepareStatement(sb.toString());
			// パラメータ設定(有効日範囲による条件)
			if (fromActivateDate != null) {
				setParam(index++, fromActivateDate);
			}
			if (toActivateDate != null) {
				setParam(index++, toActivateDate);
			}
			// SQL実行
			executeQuery();
			// 結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
