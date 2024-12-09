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
package jp.mosp.time.entity;

import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;

/**
 * 勤怠計算(日々)エンティティインターフェース。<br>
 */
public interface AttendCalcEntityInterface {
	
	/**
	 * 勤怠(日々)エンティティを取得する。<br>
	 * <br>
	 * @return 勤怠(日々)エンティティ
	 */
	AttendanceEntityInterface getAttendance();
	
	/**
	 * 勤務形態エンティティを取得する。<br>
	 * <br>
	 * @return 勤務形態エンティティ
	 */
	WorkTypeEntityInterface getWorkType();
	
	/**
	 * 申請エンティティを取得する。<br>
	 * <br>
	 * @return 申請エンティティ
	 */
	RequestEntityInterface getRequest();
	
	/**
	 * 勤怠(日々)エンティティを設定する。<br>
	 * <br>
	 * 勤怠計算(日々)エンティティでは、
	 * ここで設定された勤怠(日々)情報にを設定することは無い。<br>
	 * <br>
	 * @param attendance 勤怠(日々)エンティティ
	 */
	void setAttendance(AttendanceEntityInterface attendance);
	
	/**
	 * 勤怠設定エンティティを設定する。<br>
	 * <br>
	 * @param timeSetting 勤怠設定エンティティ
	 */
	void setTimeSetting(TimeSettingEntityInterface timeSetting);
	
	/**
	 * 勤務形態エンティティを設定する。<br>
	 * <br>
	 * @param workType 勤務形態エンティティ
	 */
	void setWorkType(WorkTypeEntityInterface workType);
	
	/**
	 * 申請エンティティを設定する。<br>
	 * <br>
	 * @param request 申請エンティティ
	 */
	void setRequest(RequestEntityInterface request);
	
	/**
	 * 勤務形態(翌日)コードを設定する。<br>
	 * <br>
	 * @param nextWorkType 勤務形態(翌日)コード
	 */
	void setNextWorkType(String nextWorkType);
	
	/**
	 * MosP処理情報を設定する。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	void setMospParams(MospParams mospParams);
	
	/**
	 * 汎用パラメータ群に値を設定する。<br>
	 * <br>
	 * @param key   キー
	 * @param value 値
	 */
	void putParam(String key, Object value);
	
	/**
	 * 汎用パラメータ群に値(時間間隔群)を追加する。<br>
	 * 既に値が設定されている場合は、時間間隔群を統合する。<br>
	 * <br>
	 * @param key       キー
	 * @param durations 値(時間間隔群)
	 */
	void addDurationsParam(String key, Map<Integer, TimeDuration> durations);
	
	/**
	 * 汎用パラメータ群に値(数値)を設定する。<br>
	 * 既に値が設定されている場合は、値(数値)を加算する。<br>
	 * <br>
	 * @param key   キー
	 * @param value 値(数値)
	 */
	void addIntParam(String key, int value);
	
	/**
	 * 汎用パラメータ群から時間間隔情報群を取得する。<br>
	 * <br>
	 * @param key キー
	 * @return 時間間隔情報群
	 */
	Map<Integer, TimeDuration> getDurationsParam(String key);
	
	/**
	 * 始業時刻(勤怠計算上)(0:00からの分)を計算する。<br>
	 * 勤怠情報(日々)の始業時刻(start_time)に設定される値を計算する。<br>
	 * <br>
	 * @param inputStartTime 入力始業時刻(0:00からの分)
	 * @return 始業時刻(勤怠計算上)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcStartTime(int inputStartTime) throws MospException;
	
	/**
	 * 実始業時刻(0:00からの分)を計算する。<br>
	 * 勤怠情報(日々)の実始業時刻(actual_start_time)に設定される値を計算する。<br>
	 * <br>
	 * @param inputStartTime 入力始業時刻(0:00からの分)
	 * @return 実始業時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcActualStartTime(int inputStartTime) throws MospException;
	
	/**
	 * 終業時刻(勤怠計算上)(0:00からの分)を計算する。<br>
	 * 勤怠情報(日々)の終業時刻(end_time)に設定される値を計算する。<br>
	 * <br>
	 * @param inputEndTime 入力終業時刻(0:00からの分)
	 * @return 終業時刻(勤怠計算上)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcEndTime(int inputEndTime) throws MospException;
	
	/**
	 * 実終業時刻(0:00からの分)を計算する。<br>
	 * 勤怠情報(日々)の実終業時刻(actual_end_time)に設定される値を計算する。<br>
	 * <br>
	 * @param inputEndTime 入力終業時刻(日時)
	 * @return 実終業時刻(日時)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcActualEndTime(int inputEndTime) throws MospException;
	
	/**
	 * 遅刻時間(分)を計算する。<br>
	 * 勤怠情報(日々)の遅刻時間(late_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 遅刻時刻(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcLateTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 実遅刻時間(分)を計算する。<br>
	 * 勤怠情報(日々)の実遅刻時間(actual_late_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 実遅刻時刻(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcActualLateTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 早退時間(分)を計算する。<br>
	 * 勤怠情報(日々)の早退時間(leave_early_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 早退時刻(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcLeaveEarlyTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 実早退時間(分)を計算する。<br>
	 * 勤怠情報(日々)の実早退時間(actual_leave_early_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 実早退時刻(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcActualLeaveEarlyTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 勤務時間(分)を計算する。<br>
	 * 勤怠情報(日々)の勤務時間(work_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcWorkTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 所定労働時間(分)を計算する。<br>
	 * 勤怠情報(日々)の所定労働(general_work_time)に設定される値を計算する。<br>
	 * <br>
	 * @return 所定労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcPrescribedWorkTime() throws MospException;
	
	/**
	 * 所定労働時間内労働時間(分)を計算する。<br>
	 * 勤怠情報(日々)の所定労働時間内労働時間(work_time_within_prescribed_work_time)
	 * に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 所定労働時間内労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcWorkTimeWithinPrescribed(int startTime, int endTime) throws MospException;
	
	/**
	 * 無給時短時間(分)を計算する。<br>
	 * 勤怠情報(日々)の無給時短時間(short_unpaid)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 無給時短時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcShortUnpaid(int startTime, int endTime) throws MospException;
	
	/**
	 * 休憩時間(分)を計算する。<br>
	 * 勤怠情報(日々)の休憩時間(rest_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 休憩時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcRestTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 深夜休憩時間(分)を計算する。<br>
	 * 勤怠情報(日々)の深夜休憩時間(night_rest_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 深夜休憩時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcNightRestTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 法定休出休憩時間(分)を計算する。<br>
	 * 勤怠情報(日々)の法定休出休憩時間(legal_holiday_rest_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 法定休出休憩時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcLegalHolidayRestTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 所定休出休憩時間(分)を計算する。<br>
	 * 勤怠情報(日々)の所定休出休憩時間(prescribed_holiday_rest_time)
	 * に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 所定休出休憩時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcPrescribedHolidayRestTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 公用外出時間(分)を計算する。<br>
	 * 勤怠情報(日々)の公用外出時間(public_time)に設定される値を計算する。<br>
	 * <br>
	 * @return 公用外出時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcPublicTime() throws MospException;
	
	/**
	 * 私用外出時間(分)を計算する。<br>
	 * 勤怠情報(日々)の私用外出時間(private_time)に設定される値を計算する。<br>
	 * <br>
	 * @return 私用外出時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcPrivateTime() throws MospException;
	
	/**
	 * 前残業時間(分)を計算する。<br>
	 * 勤怠情報(日々)の前残業時間(overtime_before)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 前残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcOvertimeBefore(int startTime, int endTime) throws MospException;
	
	/**
	 * 後残業時間(分)を計算する。<br>
	 * 勤怠情報(日々)の後残業時間(overtime_after)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 後残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcOvertimeAfter(int startTime, int endTime) throws MospException;
	
	/**
	 * 法定内残業時間(分)を計算する。<br>
	 * 勤怠情報(日々)の法定内残業時間(overtime_in)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 法定内残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcOvertimeIn(int startTime, int endTime) throws MospException;
	
	/**
	 * 法定外残業時間(分)を計算する。<br>
	 * 勤怠情報(日々)の法定外残業時間(overtime_out)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 法定外残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcOvertimeOut(int startTime, int endTime) throws MospException;
	
	/**
	 * 平日法定時間内残業時間(分)を計算する。<br>
	 * 勤怠情報(日々)の平日法定時間内残業時間(workday_overtime_in)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 平日法定時間内残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcWorkdayOvertimeIn(int startTime, int endTime) throws MospException;
	
	/**
	 * 平日法定時間外残業時間(分)を計算する。<br>
	 * 勤怠情報(日々)の平日法定時間外残業時間(workday_overtime_out)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 平日法定時間外残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcWorkdayOvertimeOut(int startTime, int endTime) throws MospException;
	
	/**
	 * 所定休日法定時間内残業時間(分)を計算する。<br>
	 * 勤怠情報(日々)の所定休日法定時間内残業時間(prescribed_holiday_overtime_in)
	 * に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 所定休日法定時間内残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcPrescribedHolidayOvertimeIn(int startTime, int endTime) throws MospException;
	
	/**
	 * 所定休日法定時間外残業時間(分)を計算する。<br>
	 * 勤怠情報(日々)の所定休日法定時間外残業時間(prescribed_holiday_overtime_out)
	 * に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 所定休日法定時間外残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcPrescribedHolidayOvertimeOut(int startTime, int endTime) throws MospException;
	
	/**
	 * 深夜勤務時間(分)を計算する。<br>
	 * 勤怠情報(日々)の深夜勤務時間(late_night_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 深夜勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcNightWorkTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 深夜所定労働時間内時間(分)を計算する。<br>
	 * 勤怠情報(日々)の深夜所定労働時間内時間(night_work_within_prescribed_work)
	 * に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 深夜所定労働時間内時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcNightWorkWithinPrescribed(int startTime, int endTime) throws MospException;
	
	/**
	 * 深夜時間外時間(分)を計算する。<br>
	 * 勤怠情報(日々)の深夜時間外時間(night_overtime_work)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 深夜時間外時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcNightWorkOvertime(int startTime, int endTime) throws MospException;
	
	/**
	 * 深夜休日労働時間(分)を計算する。<br>
	 * 勤怠情報(日々)の深夜休日労働時間(night_work_on_holiday)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 深夜休日労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcNightWorkOnLegalHoliday(int startTime, int endTime) throws MospException;
	
	/**
	 * 所定休日勤務時間(分)を計算する。<br>
	 * 勤怠情報(日々)の所定休日勤務時間(specific_work_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 所定休日勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcPrescribedHolidayWorkTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 法定休日勤務時間(分)を計算する。<br>
	 * 勤怠情報(日々)の法定休日勤務時間(legal_work_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 法定休日勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcLegalHolidayWorkTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 減額対象時間(分)を計算する。<br>
	 * 勤怠情報(日々)の減額対象時間(decrease_time)に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 減額対象時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcDecreaseTime(int startTime, int endTime) throws MospException;
	
	/**
	 * 所定労働時間内法定休日労働時間(分)を計算する。<br>
	 * 勤怠情報(日々)の所定労働時間内法定休日労働時間(statutory_holiday_work_time_in)
	 * に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 所定労働時間内法定休日労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcLegalHolidayWorkTimeIn(int startTime, int endTime) throws MospException;
	
	/**
	 * 所定労働時間外法定休日労働時間(分)を計算する。<br>
	 * 勤怠情報(日々)の所定労働時間外法定休日労働時間(statutory_holiday_work_time_out)
	 * に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 所定労働時間外法定休日労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcLegalHolidayWorkTimeOut(int startTime, int endTime) throws MospException;
	
	/**
	 * 所定労働時間内所定休日労働時間(分)を計算する。<br>
	 * 勤怠情報(日々)の所定労働時間内所定休日労働時間(prescribed_holiday_work_time_in)
	 * に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 所定労働時間内所定休日労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcPrescribedHolidayWorkTimeIn(int startTime, int endTime) throws MospException;
	
	/**
	 * 所定労働時間外所定休日労働時間(分)を計算する。<br>
	 * 勤怠情報(日々)の所定労働時間外所定休日労働時間(prescribed_holiday_work_time_out)
	 * に設定される値を計算する。<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @return 所定労働時間外所定休日労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	int calcPrescribedHolidayWorkTimeOut(int startTime, int endTime) throws MospException;
	
	/**
	 * 残前休憩時間(0:00からの分)を取得する。<br>
	 * <br>
	 * @param startTime  始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime    終業時刻(勤怠計算上)(0:00からの分)
	 * @return 残前休憩時間(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Map<Integer, TimeDuration> getOvertimeBeforeRest(int startTime, int endTime) throws MospException;
	
	/**
	 * 残業休憩時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * @param startTime          始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime            終業時刻(勤怠計算上)(0:00からの分)
	 * @return 残業前休憩時間(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	Map<Integer, TimeDuration> getOvertimeRest(int startTime, int endTime) throws MospException;
	
}
