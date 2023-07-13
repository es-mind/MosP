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

import java.sql.Time;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdAttendanceDto;

/**
 * 勤怠データDAOクラス。
 */
public class TmdAttendanceDao extends PlatformDao implements AttendanceDaoInterface {
	
	/**
	 * 勤怠データマスタ。
	 */
	public static final String		TABLE														= "tmd_attendance";
	
	/**
	 * レコード識別ID。
	 */
	public static final String		COL_TMD_ATTENDANCE_ID										= "tmd_attendance_id";
	
	/**
	 * 個人ID。
	 */
	public static final String		COL_PERSONAL_ID												= "personal_id";
	
	/**
	 * 勤務日。
	 */
	public static final String		COL_WORK_DATE												= "work_date";
	
	/**
	 * 勤務回数。
	 */
	public static final String		COL_TIMES_WORK												= "times_work";
	
	/**
	 * 勤務形態コード。
	 */
	public static final String		COL_WORK_TYPE_CODE											= "work_type_code";
	
	/**
	 * 直行。
	 */
	public static final String		COL_DIRECT_START											= "direct_start";
	
	/**
	 * 直帰。
	 */
	public static final String		COL_DIRECT_END												= "direct_end";
	
	/**
	 * 始業忘れ。
	 */
	public static final String		COL_FORGOT_RECORD_WORK_START								= "forgot_record_work_start";
	
	/**
	 * その他の始業できなかった場合。
	 */
	public static final String		COL_NOT_RECORD_WORK_START									= "not_record_work_start";
	
	/**
	 * 始業時刻。
	 */
	public static final String		COL_START_TIME												= "start_time";
	
	/**
	 * 実始業時刻。
	 */
	public static final String		COL_ACTUAL_START_TIME										= "actual_start_time";
	
	/**
	 * 終業時刻。
	 */
	public static final String		COL_END_TIME												= "end_time";
	
	/**
	 * 実終業時刻。
	 */
	public static final String		COL_ACTUAL_END_TIME											= "actual_end_time";
	
	/**
	 * 遅刻日数。
	 */
	public static final String		COL_LATE_DAYS												= "late_days";
	
	/**
	 * 遅刻30分以上日数。
	 */
	public static final String		COL_LATE_THIRTY_MINUTES_OR_MORE								= "late_thirty_minutes_or_more";
	
	/**
	 * 遅刻30分未満日数。
	 */
	public static final String		COL_LATE_LESS_THAN_THIRTY_MINUTES							= "late_less_than_thirty_minutes";
	
	/**
	 * 遅刻時間。
	 */
	public static final String		COL_LATE_TIME												= "late_time";
	
	/**
	 * 実遅刻時間。
	 */
	public static final String		COL_ACTUAL_LATE_TIME										= "actual_late_time";
	
	/**
	 * 遅刻30分以上時間。
	 */
	public static final String		COL_LATE_THIRTY_MINUTES_OR_MORE_TIME						= "late_thirty_minutes_or_more_time";
	
	/**
	 * 遅刻30分未満時間。
	 */
	public static final String		COL_LATE_LESS_THAN_THIRTY_MINUTES_TIME						= "late_less_than_thirty_minutes_time";
	
	/**
	 * 遅刻理由。
	 */
	public static final String		COL_LATE_REASON												= "late_reason";
	
	/**
	 * 遅刻証明書。
	 */
	public static final String		COL_LATE_CERTIFICATE										= "late_certificate";
	
	/**
	 * 遅刻コメント。
	 */
	public static final String		COL_LATE_COMMENT											= "late_comment";
	
	/**
	 * 早退日数。
	 */
	public static final String		COL_LEAVE_EARLY_DAYS										= "leave_early_days";
	
	/**
	 * 早退30分以上日数。
	 */
	public static final String		COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE						= "leave_early_thirty_minutes_or_more";
	
	/**
	 * 早退30分未満日数。
	 */
	public static final String		COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES					= "leave_early_less_than_thirty_minutes";
	
	/**
	 * 早退時間。
	 */
	public static final String		COL_LEAVE_EARLY_TIME										= "leave_early_time";
	
	/**
	 * 実早退時間。
	 */
	public static final String		COL_ACTUAL_LEAVE_EARLY_TIME									= "actual_leave_early_time";
	
	/**
	 * 早退30分以上時間。
	 */
	public static final String		COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME					= "leave_early_thirty_minutes_or_more_time";
	
	/**
	 * 早退30分未満時間。
	 */
	public static final String		COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME				= "leave_early_less_than_thirty_minutes_time";
	
	/**
	 * 早退理由。
	 */
	public static final String		COL_LEAVE_EARLY_REASON										= "leave_early_reason";
	
	/**
	 * 早退証明書。
	 */
	public static final String		COL_LEAVE_EARLY_CERTIFICATE									= "leave_early_certificate";
	
	/**
	 * 早退コメント。
	 */
	public static final String		COL_LEAVE_EARLY_COMMENT										= "leave_early_comment";
	
	/**
	 * 勤務時間。
	 */
	public static final String		COL_WORK_TIME												= "work_time";
	
	/**
	 * 所定労働時間。
	 */
	public static final String		COL_GENERAL_WORK_TIME										= "general_work_time";
	
	/**
	 * 所定労働時間内労働時間。
	 */
	public static final String		COL_WORK_TIME_WITHIN_PRESCRIBED_WORK_TIME					= "work_time_within_prescribed_work_time";
	
	/**
	 * 契約勤務時間。
	 */
	public static final String		COL_CONTRACT_WORK_TIME										= "contract_work_time";
	
	/**
	 * 無給時短時間。
	 */
	public static final String		COL_SHORT_UNPAID											= "short_unpaid";
	
	/**
	 * 休憩時間。
	 */
	public static final String		COL_REST_TIME												= "rest_time";
	
	/**
	 * 法定外休憩時間。
	 */
	public static final String		COL_OVER_REST_TIME											= "over_rest_time";
	
	/**
	 * 深夜休憩時間。
	 */
	public static final String		COL_NIGHT_REST_TIME											= "night_rest_time";
	
	/**
	 * 法定休出休憩時間。
	 */
	public static final String		COL_LEGAL_HOLIDAY_REST_TIME									= "legal_holiday_rest_time";
	
	/**
	 * 所定休出休憩時間。
	 */
	public static final String		COL_PRESCRIBED_HOLIDAY_REST_TIME							= "prescribed_holiday_rest_time";
	
	/**
	 * 公用外出時間。
	 */
	public static final String		COL_PUBLIC_TIME												= "public_time";
	
	/**
	 * 私用外出時間。
	 */
	public static final String		COL_PRIVATE_TIME											= "private_time";
	
	/**
	 * 分単位休暇A時間。
	 */
	public static final String		COL_MINUTELY_HOLIDAY_A_TIME									= "minutely_holiday_a_time";
	
	/**
	 * 分単位休暇B時間。
	 */
	public static final String		COL_MINUTELY_HOLIDAY_B_TIME									= "minutely_holiday_b_time";
	
	/**
	 * 分単位休暇A全休。
	 */
	public static final String		COL_MINUTELY_HOLIDAY_A										= "minutely_holiday_a";
	
	/**
	 * 分単位休暇B全休。
	 */
	public static final String		COL_MINUTELY_HOLIDAY_B										= "minutely_holiday_b";
	
	/**
	 * 残業回数。
	 */
	public static final String		COL_TIMES_OVERTIME											= "times_overtime";
	
	/**
	 * 残業時間。
	 */
	public static final String		COL_OVERTIME												= "overtime";
	
	/**
	 * 前残業時間。
	 */
	public static final String		COL_OVERTIME_BEFORE											= "overtime_before";
	
	/**
	 * 後残業時間。
	 */
	public static final String		COL_OVERTIME_AFTER											= "overtime_after";
	
	/**
	 * 法定内残業時間。
	 */
	public static final String		COL_OVERTIME_IN												= "overtime_in";
	
	/**
	 * 法定外残業時間。
	 */
	public static final String		COL_OVERTIME_OUT											= "overtime_out";
	
	/**
	 * 平日法定時間内残業時間。
	 */
	public static final String		COL_WORKDAY_OVERTIME_IN										= "workday_overtime_in";
	
	/**
	 * 平日法定時間外残業時間。
	 */
	public static final String		COL_WORKDAY_OVERTIME_OUT									= "workday_overtime_out";
	
	/**
	 * 所定休日法定時間内残業時間。
	 */
	public static final String		COL_PRESCRIBED_HOLIDAY_OVERTIME_IN							= "prescribed_holiday_overtime_in";
	
	/**
	 * 所定休日法定時間外残業時間。
	 */
	public static final String		COL_PRESCRIBED_HOLIDAY_OVERTIME_OUT							= "prescribed_holiday_overtime_out";
	
	/**
	 * 深夜勤務時間。
	 */
	public static final String		COL_LATE_NIGHT_TIME											= "late_night_time";
	
	/**
	 * 深夜所定労働時間内時間。
	 */
	public static final String		COL_NIGHT_WORK_WITHIN_PRESCRIBED_WORK						= "night_work_within_prescribed_work";
	
	/**
	 * 深夜時間外時間。
	 */
	public static final String		COL_NIGHT_OVERTIME_WORK										= "night_overtime_work";
	
	/**
	 * 深夜休日労働時間。
	 */
	public static final String		COL_NIGHT_WORK_ON_HOLIDAY									= "night_work_on_holiday";
	
	/**
	 * 所定休日勤務時間。
	 */
	public static final String		COL_SPECIFIC_WORK_TIME										= "specific_work_time";
	
	/**
	 * 法定休日勤務時間。
	 */
	public static final String		COL_LEGAL_WORK_TIME											= "legal_work_time";
	
	/**
	 * 減額対象時間。
	 */
	public static final String		COL_DECREASE_TIME											= "decrease_time";
	
	/**
	 * 勤怠コメント。
	 */
	public static final String		COL_TIME_COMMENT											= "time_comment";
	
	/**
	 * 備考。
	 */
	public static final String		COL_REMARKS													= "remarks";
	
	/**
	 * 出勤日数。
	 */
	public static final String		COL_WORK_DAYS												= "work_days";
	
	/**
	 * 有給休暇用出勤日数。
	 */
	public static final String		COL_WORK_DAYS_FOR_PAID_LEAVE								= "work_days_for_paid_leave";
	
	/**
	 * 有給休暇用全労働日。
	 */
	public static final String		COL_TOTAL_WORK_DAYS_FOR_PAID_LEAVE							= "total_work_days_for_paid_leave";
	
	/**
	 * 休日出勤回数。
	 */
	public static final String		COL_TIMES_HOLIDAY_WORK										= "times_holiday_work";
	
	/**
	 * 法定休日出勤回数。
	 */
	public static final String		COL_TIMES_LEGAL_HOLIDAY_WORK								= "times_legal_holiday_work";
	
	/**
	 * 所定休日出勤回数。
	 */
	public static final String		COL_TIMES_PRESCRIBED_HOLIDAY_WORK							= "times_prescribed_holiday_work";
	
	/**
	 * 有給休暇日数。
	 */
	public static final String		COL_PAID_LEAVE_DAYS											= "paid_leave_days";
	
	/**
	 * 有給休暇時間数。
	 */
	public static final String		COL_PAID_LEAVE_HOURS										= "paid_leave_hours";
	
	/**
	 * ストック休暇日数。
	 */
	public static final String		COL_STOCK_LEAVE_DAYS										= "stock_leave_days";
	
	/**
	 * 代休日数。
	 */
	public static final String		COL_COMPENSATION_DAYS										= "compensation_days";
	
	/**
	 * 法定代休日数。
	 */
	public static final String		COL_LEGAL_COMPENSATION_DAYS									= "legal_compensation_days";
	
	/**
	 * 所定代休日数。
	 */
	public static final String		COL_PRESCRIBED_COMPENSATION_DAYS							= "prescribed_compensation_days";
	
	/**
	 * 深夜代休日数。
	 */
	public static final String		COL_NIGHT_COMPENSATION_DAYS									= "night_compensation_days";
	
	/**
	 * 特別休暇日数。
	 */
	public static final String		COL_SPECIAL_LEAVE_DAYS										= "special_leave_days";
	
	/**
	 * 特別休暇時間数。
	 */
	public static final String		COL_SPECIAL_LEAVE_HOURS										= "special_leave_hours";
	
	/**
	 * その他休暇日数。
	 */
	public static final String		COL_OTHER_LEAVE_DAYS										= "other_leave_days";
	
	/**
	 * その他休暇時間数。
	 */
	public static final String		COL_OTHER_LEAVE_HOURS										= "other_leave_hours";
	
	/**
	 * 欠勤日数。
	 */
	public static final String		COL_ABSENCE_DAYS											= "absence_days";
	
	/**
	 * 欠勤時間数。
	 */
	public static final String		COL_ABSENCE_HOURS											= "absence_hours";
	
	/**
	 * 法定代休発生日数。
	 */
	public static final String		COL_GRANTED_LEGAL_COMPENSATION_DAYS							= "granted_legal_compensation_days";
	
	/**
	 * 所定代休発生日数。
	 */
	public static final String		COL_GRANTED_PRESCRIBED_COMPENSATION_DAYS					= "granted_prescribed_compensation_days";
	
	/**
	 * 深夜代休発生日数。
	 */
	public static final String		COL_GRANTED_NIGHT_COMPENSATION_DAYS							= "granted_night_compensation_days";
	
	/**
	 * 法定休出時間(代休あり)。
	 */
	public static final String		COL_LEGAL_HOLIDAY_WORK_TIME_WITH_COMPENSATION_DAY			= "legal_holiday_work_time_with_compensation_day";
	
	/**
	 * 法定休出時間(代休なし)。
	 */
	public static final String		COL_LEGAL_HOLIDAY_WORK_TIME_WITHOUT_COMPENSATION_DAY		= "legal_holiday_work_time_without_compensation_day";
	
	/**
	 * 所定休出時間(代休あり)。
	 */
	public static final String		COL_PRESCRIBED_HOLIDAY_WORK_TIME_WITH_COMPENSATION_DAY		= "prescribed_holiday_work_time_with_compensation_day";
	
	/**
	 * 所定休出時間(代休なし)。
	 */
	public static final String		COL_PRESCRIBED_HOLIDAY_WORK_TIME_WITHOUT_COMPENSATION_DAY	= "prescribed_holiday_work_time_without_compensation_day";
	
	/**
	 * 法定労働時間内残業時間(代休あり)。
	 */
	public static final String		COL_OVERTIME_IN_WITH_COMPENSATION_DAY						= "overtime_in_with_compensation_day";
	
	/**
	 * 法定労働時間内残業時間(代休なし)。
	 */
	public static final String		COL_OVERTIME_IN_WITHOUT_COMPENSATION_DAY					= "overtime_in_without_compensation_day";
	
	/**
	 * 法定労働時間外残業時間(代休あり)。
	 */
	public static final String		COL_OVERTIME_OUT_WITH_COMPENSATION_DAY						= "overtime_out_with_compensation_day";
	
	/**
	 * 法定労働時間外残業時間(代休なし)。
	 */
	public static final String		COL_OVERTIME_OUT_WITHOUT_COMPENSATION_DAY					= "overtime_out_without_compensation_day";
	
	/**
	 * 所定労働時間内法定休日労働時間。
	 */
	public static final String		COL_STATUTORY_HOLIDAY_WORK_TIME_IN							= "statutory_holiday_work_time_in";
	
	/**
	 * 所定労働時間外法定休日労働時間。
	 */
	public static final String		COL_STATUTORY_HOLIDAY_WORK_TIME_OUT							= "statutory_holiday_work_time_out";
	
	/**
	 * 所定労働時間内所定休日労働時間。
	 */
	public static final String		COL_PRESCRIBED_HOLIDAY_WORK_TIME_IN							= "prescribed_holiday_work_time_in";
	
	/**
	 * 所定労働時間外所定休日労働時間。
	 */
	public static final String		COL_PRESCRIBED_HOLIDAY_WORK_TIME_OUT						= "prescribed_holiday_work_time_out";
	
	/**
	 * ワークフロー番号。
	 */
	public static final String		COL_WORKFLOW												= "workflow";
	
	/**
	 * キー。
	 */
	public static final String		KEY_1														= COL_TMD_ATTENDANCE_ID;
	
	/**
	 * ワークフローDAOインターフェース。
	 */
	private WorkflowDaoInterface	workflowDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdAttendanceDao() {
	}
	
	@Override
	public void initDao() throws MospException {
		workflowDao = (WorkflowDaoInterface)loadDao(WorkflowDaoInterface.class);
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdAttendanceDto dto = new TmdAttendanceDto();
		dto.setTmdAttendanceId(getLong(COL_TMD_ATTENDANCE_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setWorkDate(getDate(COL_WORK_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setWorkTypeCode(getString(COL_WORK_TYPE_CODE));
		dto.setDirectStart(getInt(COL_DIRECT_START));
		dto.setDirectEnd(getInt(COL_DIRECT_END));
		dto.setForgotRecordWorkStart(getInt(COL_FORGOT_RECORD_WORK_START));
		dto.setNotRecordWorkStart(getInt(COL_NOT_RECORD_WORK_START));
		dto.setStartTime(getTimestamp(COL_START_TIME));
		dto.setActualStartTime(getTimestamp(COL_ACTUAL_START_TIME));
		dto.setEndTime(getTimestamp(COL_END_TIME));
		dto.setActualEndTime(getTimestamp(COL_ACTUAL_END_TIME));
		dto.setLateDays(getInt(COL_LATE_DAYS));
		dto.setLateThirtyMinutesOrMore(getInt(COL_LATE_THIRTY_MINUTES_OR_MORE));
		dto.setLateLessThanThirtyMinutes(getInt(COL_LATE_LESS_THAN_THIRTY_MINUTES));
		dto.setLateTime(getInt(COL_LATE_TIME));
		dto.setActualLateTime(getInt(COL_ACTUAL_LATE_TIME));
		dto.setLateThirtyMinutesOrMoreTime(getInt(COL_LATE_THIRTY_MINUTES_OR_MORE_TIME));
		dto.setLateLessThanThirtyMinutesTime(getInt(COL_LATE_LESS_THAN_THIRTY_MINUTES_TIME));
		dto.setLateReason(getString(COL_LATE_REASON));
		dto.setLateCertificate(getString(COL_LATE_CERTIFICATE));
		dto.setLateComment(getString(COL_LATE_COMMENT));
		dto.setLeaveEarlyDays(getInt(COL_LEAVE_EARLY_DAYS));
		dto.setLeaveEarlyThirtyMinutesOrMore(getInt(COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE));
		dto.setLeaveEarlyLessThanThirtyMinutes(getInt(COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES));
		dto.setLeaveEarlyTime(getInt(COL_LEAVE_EARLY_TIME));
		dto.setActualLeaveEarlyTime(getInt(COL_ACTUAL_LEAVE_EARLY_TIME));
		dto.setLeaveEarlyThirtyMinutesOrMoreTime(getInt(COL_LEAVE_EARLY_THIRTY_MINUTES_OR_MORE_TIME));
		dto.setLeaveEarlyLessThanThirtyMinutesTime(getInt(COL_LEAVE_EARLY_LESS_THAN_THIRTY_MINUTES_TIME));
		dto.setLeaveEarlyReason(getString(COL_LEAVE_EARLY_REASON));
		dto.setLeaveEarlyCertificate(getString(COL_LEAVE_EARLY_CERTIFICATE));
		dto.setLeaveEarlyComment(getString(COL_LEAVE_EARLY_COMMENT));
		dto.setWorkTime(getInt(COL_WORK_TIME));
		dto.setGeneralWorkTime(getInt(COL_GENERAL_WORK_TIME));
		dto.setWorkTimeWithinPrescribedWorkTime(getInt(COL_WORK_TIME_WITHIN_PRESCRIBED_WORK_TIME));
		dto.setContractWorkTime(getInt(COL_CONTRACT_WORK_TIME));
		dto.setShortUnpaid(getInt(COL_SHORT_UNPAID));
		dto.setRestTime(getInt(COL_REST_TIME));
		dto.setOverRestTime(getInt(COL_OVER_REST_TIME));
		dto.setNightRestTime(getInt(COL_NIGHT_REST_TIME));
		dto.setLegalHolidayRestTime(getInt(COL_LEGAL_HOLIDAY_REST_TIME));
		dto.setPrescribedHolidayRestTime(getInt(COL_PRESCRIBED_HOLIDAY_REST_TIME));
		dto.setPublicTime(getInt(COL_PUBLIC_TIME));
		dto.setPrivateTime(getInt(COL_PRIVATE_TIME));
		dto.setMinutelyHolidayATime(getInt(COL_MINUTELY_HOLIDAY_A_TIME));
		dto.setMinutelyHolidayBTime(getInt(COL_MINUTELY_HOLIDAY_B_TIME));
		dto.setMinutelyHolidayA(getInt(COL_MINUTELY_HOLIDAY_A));
		dto.setMinutelyHolidayB(getInt(COL_MINUTELY_HOLIDAY_B));
		dto.setTimesOvertime(getInt(COL_TIMES_OVERTIME));
		dto.setOvertime(getInt(COL_OVERTIME));
		dto.setOvertimeBefore(getInt(COL_OVERTIME_BEFORE));
		dto.setOvertimeAfter(getInt(COL_OVERTIME_AFTER));
		dto.setOvertimeIn(getInt(COL_OVERTIME_IN));
		dto.setOvertimeOut(getInt(COL_OVERTIME_OUT));
		dto.setWorkdayOvertimeIn(getInt(COL_WORKDAY_OVERTIME_IN));
		dto.setWorkdayOvertimeOut(getInt(COL_WORKDAY_OVERTIME_OUT));
		dto.setPrescribedHolidayOvertimeIn(getInt(COL_PRESCRIBED_HOLIDAY_OVERTIME_IN));
		dto.setPrescribedHolidayOvertimeOut(getInt(COL_PRESCRIBED_HOLIDAY_OVERTIME_OUT));
		dto.setLateNightTime(getInt(COL_LATE_NIGHT_TIME));
		dto.setNightWorkWithinPrescribedWork(getInt(COL_NIGHT_WORK_WITHIN_PRESCRIBED_WORK));
		dto.setNightOvertimeWork(getInt(COL_NIGHT_OVERTIME_WORK));
		dto.setNightWorkOnHoliday(getInt(COL_NIGHT_WORK_ON_HOLIDAY));
		dto.setSpecificWorkTime(getInt(COL_SPECIFIC_WORK_TIME));
		dto.setLegalWorkTime(getInt(COL_LEGAL_WORK_TIME));
		dto.setDecreaseTime(getInt(COL_DECREASE_TIME));
		dto.setTimeComment(getString(COL_TIME_COMMENT));
		dto.setRemarks(getString(COL_REMARKS));
		dto.setWorkDays(getDouble(COL_WORK_DAYS));
		dto.setWorkDaysForPaidLeave(getInt(COL_WORK_DAYS_FOR_PAID_LEAVE));
		dto.setTotalWorkDaysForPaidLeave(getInt(COL_TOTAL_WORK_DAYS_FOR_PAID_LEAVE));
		dto.setTimesHolidayWork(getInt(COL_TIMES_HOLIDAY_WORK));
		dto.setTimesLegalHolidayWork(getInt(COL_TIMES_LEGAL_HOLIDAY_WORK));
		dto.setTimesPrescribedHolidayWork(getInt(COL_TIMES_PRESCRIBED_HOLIDAY_WORK));
		dto.setPaidLeaveDays(getDouble(COL_PAID_LEAVE_DAYS));
		dto.setPaidLeaveHours(getInt(COL_PAID_LEAVE_HOURS));
		dto.setStockLeaveDays(getDouble(COL_STOCK_LEAVE_DAYS));
		dto.setCompensationDays(getDouble(COL_COMPENSATION_DAYS));
		dto.setLegalCompensationDays(getDouble(COL_LEGAL_COMPENSATION_DAYS));
		dto.setPrescribedCompensationDays(getDouble(COL_PRESCRIBED_COMPENSATION_DAYS));
		dto.setNightCompensationDays(getDouble(COL_NIGHT_COMPENSATION_DAYS));
		dto.setSpecialLeaveDays(getDouble(COL_SPECIAL_LEAVE_DAYS));
		dto.setSpecialLeaveHours(getInt(COL_SPECIAL_LEAVE_HOURS));
		dto.setOtherLeaveDays(getDouble(COL_OTHER_LEAVE_DAYS));
		dto.setOtherLeaveHours(getInt(COL_OTHER_LEAVE_HOURS));
		dto.setAbsenceDays(getDouble(COL_ABSENCE_DAYS));
		dto.setAbsenceHours(getInt(COL_ABSENCE_HOURS));
		dto.setGrantedLegalCompensationDays(getDouble(COL_GRANTED_LEGAL_COMPENSATION_DAYS));
		dto.setGrantedPrescribedCompensationDays(getDouble(COL_GRANTED_PRESCRIBED_COMPENSATION_DAYS));
		dto.setGrantedNightCompensationDays(getDouble(COL_GRANTED_NIGHT_COMPENSATION_DAYS));
		dto.setLegalHolidayWorkTimeWithCompensationDay(getInt(COL_LEGAL_HOLIDAY_WORK_TIME_WITH_COMPENSATION_DAY));
		dto.setLegalHolidayWorkTimeWithoutCompensationDay(getInt(COL_LEGAL_HOLIDAY_WORK_TIME_WITHOUT_COMPENSATION_DAY));
		dto.setPrescribedHolidayWorkTimeWithCompensationDay(
				getInt(COL_PRESCRIBED_HOLIDAY_WORK_TIME_WITH_COMPENSATION_DAY));
		dto.setPrescribedHolidayWorkTimeWithoutCompensationDay(
				getInt(COL_PRESCRIBED_HOLIDAY_WORK_TIME_WITHOUT_COMPENSATION_DAY));
		dto.setOvertimeInWithCompensationDay(getInt(COL_OVERTIME_IN_WITH_COMPENSATION_DAY));
		dto.setOvertimeInWithoutCompensationDay(getInt(COL_OVERTIME_IN_WITHOUT_COMPENSATION_DAY));
		dto.setOvertimeOutWithCompensationDay(getInt(COL_OVERTIME_OUT_WITH_COMPENSATION_DAY));
		dto.setOvertimeOutWithoutCompensationDay(getInt(COL_OVERTIME_OUT_WITHOUT_COMPENSATION_DAY));
		dto.setStatutoryHolidayWorkTimeIn(getInt(COL_STATUTORY_HOLIDAY_WORK_TIME_IN));
		dto.setStatutoryHolidayWorkTimeOut(getInt(COL_STATUTORY_HOLIDAY_WORK_TIME_OUT));
		dto.setPrescribedHolidayWorkTimeIn(getInt(COL_PRESCRIBED_HOLIDAY_WORK_TIME_IN));
		dto.setPrescribedHolidayWorkTimeOut(getInt(COL_PRESCRIBED_HOLIDAY_WORK_TIME_OUT));
		dto.setWorkflow(getLong(COL_WORKFLOW));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<AttendanceDtoInterface> mappingAll() throws MospException {
		List<AttendanceDtoInterface> all = new ArrayList<AttendanceDtoInterface>();
		while (next()) {
			all.add((AttendanceDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			AttendanceDtoInterface dto = (AttendanceDtoInterface)baseDto;
			setParam(index++, dto.getTmdAttendanceId());
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
	public int delete(BaseDtoInterface baseDto) {
		// 処理無し
		return 0;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		AttendanceDtoInterface dto = (AttendanceDtoInterface)baseDto;
		setParam(index++, dto.getTmdAttendanceId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getWorkDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getWorkTypeCode());
		setParam(index++, dto.getDirectStart());
		setParam(index++, dto.getDirectEnd());
		setParam(index++, dto.getForgotRecordWorkStart());
		setParam(index++, dto.getNotRecordWorkStart());
		setParam(index++, dto.getStartTime() == null ? null : new Time(dto.getStartTime().getTime()), true);
		setParam(index++, dto.getActualStartTime(), true);
		setParam(index++, dto.getEndTime() == null ? null : new Time(dto.getEndTime().getTime()), true);
		setParam(index++, dto.getActualEndTime(), true);
		setParam(index++, dto.getLateDays());
		setParam(index++, dto.getLateThirtyMinutesOrMore());
		setParam(index++, dto.getLateLessThanThirtyMinutes());
		setParam(index++, dto.getLateTime());
		setParam(index++, dto.getActualLateTime());
		setParam(index++, dto.getLateThirtyMinutesOrMoreTime());
		setParam(index++, dto.getLateLessThanThirtyMinutesTime());
		setParam(index++, dto.getLateReason());
		setParam(index++, dto.getLateCertificate());
		setParam(index++, dto.getLateComment());
		setParam(index++, dto.getLeaveEarlyDays());
		setParam(index++, dto.getLeaveEarlyThirtyMinutesOrMore());
		setParam(index++, dto.getLeaveEarlyLessThanThirtyMinutes());
		setParam(index++, dto.getLeaveEarlyTime());
		setParam(index++, dto.getActualLeaveEarlyTime());
		setParam(index++, dto.getLeaveEarlyThirtyMinutesOrMoreTime());
		setParam(index++, dto.getLeaveEarlyLessThanThirtyMinutesTime());
		setParam(index++, dto.getLeaveEarlyReason());
		setParam(index++, dto.getLeaveEarlyCertificate());
		setParam(index++, dto.getLeaveEarlyComment());
		setParam(index++, dto.getWorkTime());
		setParam(index++, dto.getGeneralWorkTime());
		setParam(index++, dto.getWorkTimeWithinPrescribedWorkTime());
		setParam(index++, dto.getContractWorkTime());
		setParam(index++, dto.getShortUnpaid());
		setParam(index++, dto.getRestTime());
		setParam(index++, dto.getOverRestTime());
		setParam(index++, dto.getNightRestTime());
		setParam(index++, dto.getLegalHolidayRestTime());
		setParam(index++, dto.getPrescribedHolidayRestTime());
		setParam(index++, dto.getPublicTime());
		setParam(index++, dto.getPrivateTime());
		setParam(index++, dto.getMinutelyHolidayATime());
		setParam(index++, dto.getMinutelyHolidayBTime());
		setParam(index++, dto.getMinutelyHolidayA());
		setParam(index++, dto.getMinutelyHolidayB());
		setParam(index++, dto.getTimesOvertime());
		setParam(index++, dto.getOvertime());
		setParam(index++, dto.getOvertimeBefore());
		setParam(index++, dto.getOvertimeAfter());
		setParam(index++, dto.getOvertimeIn());
		setParam(index++, dto.getOvertimeOut());
		setParam(index++, dto.getWorkdayOvertimeIn());
		setParam(index++, dto.getWorkdayOvertimeOut());
		setParam(index++, dto.getPrescribedHolidayOvertimeIn());
		setParam(index++, dto.getPrescribedHolidayOvertimeOut());
		setParam(index++, dto.getLateNightTime());
		setParam(index++, dto.getNightWorkWithinPrescribedWork());
		setParam(index++, dto.getNightOvertimeWork());
		setParam(index++, dto.getNightWorkOnHoliday());
		setParam(index++, dto.getSpecificWorkTime());
		setParam(index++, dto.getLegalWorkTime());
		setParam(index++, dto.getDecreaseTime());
		setParam(index++, dto.getTimeComment());
		setParam(index++, dto.getRemarks());
		setParam(index++, dto.getWorkDays());
		setParam(index++, dto.getWorkDaysForPaidLeave());
		setParam(index++, dto.getTotalWorkDaysForPaidLeave());
		setParam(index++, dto.getTimesHolidayWork());
		setParam(index++, dto.getTimesLegalHolidayWork());
		setParam(index++, dto.getTimesPrescribedHolidayWork());
		setParam(index++, dto.getPaidLeaveDays());
		setParam(index++, dto.getPaidLeaveHours());
		setParam(index++, dto.getStockLeaveDays());
		setParam(index++, dto.getCompensationDays());
		setParam(index++, dto.getLegalCompensationDays());
		setParam(index++, dto.getPrescribedCompensationDays());
		setParam(index++, dto.getNightCompensationDays());
		setParam(index++, dto.getSpecialLeaveDays());
		setParam(index++, dto.getSpecialLeaveHours());
		setParam(index++, dto.getOtherLeaveDays());
		setParam(index++, dto.getOtherLeaveHours());
		setParam(index++, dto.getAbsenceDays());
		setParam(index++, dto.getAbsenceHours());
		setParam(index++, dto.getGrantedLegalCompensationDays());
		setParam(index++, dto.getGrantedPrescribedCompensationDays());
		setParam(index++, dto.getGrantedNightCompensationDays());
		setParam(index++, dto.getLegalHolidayWorkTimeWithCompensationDay());
		setParam(index++, dto.getLegalHolidayWorkTimeWithoutCompensationDay());
		setParam(index++, dto.getPrescribedHolidayWorkTimeWithCompensationDay());
		setParam(index++, dto.getPrescribedHolidayWorkTimeWithoutCompensationDay());
		setParam(index++, dto.getOvertimeInWithCompensationDay());
		setParam(index++, dto.getOvertimeInWithoutCompensationDay());
		setParam(index++, dto.getOvertimeOutWithCompensationDay());
		setParam(index++, dto.getOvertimeOutWithoutCompensationDay());
		setParam(index++, dto.getStatutoryHolidayWorkTimeIn());
		setParam(index++, dto.getStatutoryHolidayWorkTimeOut());
		setParam(index++, dto.getPrescribedHolidayWorkTimeIn());
		setParam(index++, dto.getPrescribedHolidayWorkTimeOut());
		setParam(index++, dto.getWorkflow());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public List<AttendanceDtoInterface> findForHistory(String personalId, Date workDate, int timesWork)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			sb.append(and());
			sb.append(equal(COL_TIMES_WORK));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, timesWork);
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
	public AttendanceDtoInterface findForKey(String personalId, Date workDate, int timesWork) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			sb.append(and());
			sb.append(equal(COL_TIMES_WORK));
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualWithdrawn());
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, timesWork);
			executeQuery();
			AttendanceDtoInterface dto = null;
			if (next()) {
				dto = (AttendanceDtoInterface)mapping();
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
	public List<AttendanceDtoInterface> findForList(String personalId, Date startDate, Date endDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(greaterEqual(COL_WORK_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, startDate);
			setParam(index++, endDate);
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
	public List<AttendanceDtoInterface> findForPersonalIds(Collection<String> personalIds, Date workDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(in(COL_PERSONAL_ID, personalIds.size()));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParamsStringIn(personalIds);
			setParam(index++, workDate);
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
	public List<AttendanceDtoInterface> findForWorkDates(String personalId, Set<Date> workDates, boolean isCompleted)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(in(COL_WORK_DATE, workDates.size()));
			sb.append(and());
			// 承認済フラグを確認
			if (isCompleted) {
				sb.append(workflowDao.getSubQueryForCompleted(COL_WORKFLOW));
			} else {
				sb.append(workflowDao.getSubQueryForApplied(COL_WORKFLOW));
			}
			sb.append(getOrderByColumns(COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParamsDateIn(workDates);
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
	public AttendanceDtoInterface findForWorkflow(long workflow) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORKFLOW));
			prepareStatement(sb.toString());
			setParam(index++, workflow);
			executeQuery();
			AttendanceDtoInterface dto = null;
			if (next()) {
				dto = (AttendanceDtoInterface)mapping();
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
	public List<AttendanceDtoInterface> findForWorkflowStatus(String personalId, int workflowStage,
			String workflowStatus, String routeCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			boolean useRoute = routeCode != null && routeCode.length() != 0;
			sb.append(workflowDao.getSubQueryForSetting(true, true, useRoute, true));
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workflowStage);
			setParam(index++, workflowStatus);
			if (useRoute) {
				setParam(index++, routeCode);
			}
			setParam(index++, TimeConst.CODE_FUNCTION_WORK_MANGE);
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
	public List<AttendanceDtoInterface> findForReapplicationExport(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(greaterEqual(COL_WORK_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_WORK_DATE));
			sb.append(and());
			sb.append(leftParenthesis());
			sb.append(leftParenthesis());
			sb.append(greater(COL_LEGAL_WORK_TIME));
			sb.append(and());
			sb.append(notEqual(COL_WORK_TYPE_CODE, TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY));
			sb.append(rightParenthesis());
			sb.append(or());
			sb.append(leftParenthesis());
			sb.append(greater(COL_SPECIFIC_WORK_TIME));
			sb.append(and());
			sb.append(notEqual(COL_WORK_TYPE_CODE, TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY));
			sb.append(rightParenthesis());
			sb.append(or());
			sb.append(leftParenthesis());
			sb.append(equal(COL_LEGAL_WORK_TIME, 0));
			sb.append(and());
			sb.append(COL_END_TIME);
			sb.append(greater());
			sb.append(COL_WORK_DATE);
			sb.append(" + 1");
			sb.append(rightParenthesis());
			sb.append(or());
			sb.append(leftParenthesis());
			sb.append(equal(COL_SPECIFIC_WORK_TIME, 0));
			sb.append(and());
			sb.append(COL_END_TIME);
			sb.append(greater());
			sb.append(COL_WORK_DATE);
			sb.append(" + 1");
			sb.append(rightParenthesis());
			sb.append(rightParenthesis());
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualDraft());
			sb.append(rightParenthesis());
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualWithdrawn());
			sb.append(rightParenthesis());
			sb.append(getOrderByColumns(COL_PERSONAL_ID, COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, firstDate);
			setParam(index++, lastDate);
			setParam(index++, 0);
			setParam(index++, 0);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
