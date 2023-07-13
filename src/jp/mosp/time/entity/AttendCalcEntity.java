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
package jp.mosp.time.entity;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠計算(日々)エンティティ。<br>
 */
public class AttendCalcEntity implements AttendCalcEntityInterface {
	
	/**
	 * 汎用パラメータキー(法定労働時間(分))。<br>
	 */
	public static final String				KEY_LEGAL_WORK_TIME					= "LegalWorkTime";
	
	/**
	 * 汎用パラメータキー(追加休暇時間(0:00からの分)群)。<br>
	 * 時間単位休暇と同じ扱いをする時間間隔群を設定する際に用いる。<br>
	 */
	public static final String				KEY_ADD_HOLIDAY_TIMES				= "AdditionalHolidayTimes";
	
	/**
	 * 汎用パラメータキー(追加勤務時間(0:00からの分)群)。<br>
	 */
	public static final String				KEY_ADD_WORK_TIMES					= "AdditionalWorkTimes";
	
	/**
	 * 汎用パラメータキー(追加法定内残業可能時間(分))。<br>
	 */
	public static final String				KEY_ADD_OVERTIME_IN_POSSIBLE		= "AdditionalOvertimeInPossible";
	
	/**
	 * 汎用パラメータキー(追加分単位休暇A時間(0:00からの分)群)。<br>
	 * 分単位休暇A時間を設定する際に用いる。<br>
	 */
	public static final String				KEY_ADD_MINUTELY_HOLIDAY_A_TIMES	= "AdditionalMinutelyHolidayATime";
	
	/**
	 * 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)。<br>
	 * 申請エンティティを使う際に用いる。<br>
	 * 勤怠計算時には承認済でないものは除去されているため、
	 * ここではtrueで固定とする。<br>
	 */
	protected static final boolean			IS_COMPLETED						= true;
	
	/**
	 * 勤怠(日々)エンティティ。<br>
	 */
	protected AttendanceEntityInterface		attendance;
	
	/**
	 * 勤怠設定エンティティ。<br>
	 */
	protected TimeSettingEntityInterface	timeSetting;
	
	/**
	 * 勤務形態エンティティ。<br>
	 */
	protected WorkTypeEntityInterface		workType;
	
	/**
	 * 申請エンティティ。<br>
	 */
	protected RequestEntityInterface		request;
	
	/**
	 * 勤務形態(翌日)コード。<br>
	 */
	protected String						nextWorkType;
	
	/**
	 * MosP処理情報。<br>
	 */
	protected MospParams					mospParams;
	
	/**
	 * 汎用パラメータ群。<br>
	 * 法定労働時間の変更等、アドオンで用いることを想定している。<br>
	 */
	protected Map<String, Object>			params;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	public AttendCalcEntity() {
		// 汎用パラメータ群を初期化
		params = new HashMap<String, Object>();
	}
	
	@Override
	public int calcStartTime(int inputStartTime) throws MospException {
		// 始業時刻(勤怠計算上)(0:00からの分)を計算
		return calcStartTime(inputStartTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcActualStartTime(int inputStartTime) throws MospException {
		// 実始業時刻(0:00からの分)を計算
		return calcActualStartTime(inputStartTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcEndTime(int inputEndTime) throws MospException {
		// 終業時刻(勤怠計算上)(0:00からの分)を計算
		return calcEndTime(inputEndTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcActualEndTime(int inputEndTime) throws MospException {
		// 実終業時刻(0:00からの分)を計算
		return calcActualEndTime(inputEndTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcLateTime(int startTime, int endTime) throws MospException {
		// 遅刻時間(分)を計算
		return calcLateTime(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcActualLateTime(int startTime, int endTime) throws MospException {
		// 実遅刻時間(分)を計算
		return calcActualLateTime(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcLeaveEarlyTime(int startTime, int endTime) throws MospException {
		// 早退時間(分)を計算
		return calcLeaveEarlyTime(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcActualLeaveEarlyTime(int startTime, int endTime) throws MospException {
		// 実早退時間(分)を計算
		return calcActualLeaveEarlyTime(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcWorkTime(int startTime, int endTime) throws MospException {
		// 勤務時間(分)を計算
		return calcWorkTime(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcPrescribedWorkTime() throws MospException {
		// 所定労働時間(分)を計算
		return calcPrescribedWorkTime(workType, request);
	}
	
	@Override
	public int calcWorkTimeWithinPrescribed(int startTime, int endTime) throws MospException {
		// 所定労働時間内労働時間(分)を計算
		return calcWorkTimeWithinPrescribed(startTime, endTime, attendance, workType, request, timeSetting,
				nextWorkType);
	}
	
	@Override
	public int calcShortUnpaid(int startTime, int endTime) throws MospException {
		// 無給時短時間(分)を計算
		return calcShortUnpay(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcRestTime(int startTime, int endTime) throws MospException {
		// 休憩時間(分)を計算
		return calcRestTime(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcNightRestTime(int startTime, int endTime) throws MospException {
		// 深夜休憩時間(分)を計算
		return calcNightRestTime(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcLegalHolidayRestTime(int startTime, int endTime) throws MospException {
		// 法定休出休憩時間(分)を計算
		return calcLegalHolidayRestTime(startTime, endTime, attendance, workType, request, timeSetting, nextWorkType);
	}
	
	@Override
	public int calcPrescribedHolidayRestTime(int startTime, int endTime) throws MospException {
		// 所定休出休憩時間(分)を計算
		return calcPrescribedHolidayRestTime(startTime, endTime, attendance, workType, request, timeSetting,
				nextWorkType);
	}
	
	@Override
	public int calcPublicTime() {
		// 公用外出時間(分)を計算
		return calcPublicTime(attendance, timeSetting);
	}
	
	@Override
	public int calcPrivateTime() {
		// 私用外出時間(分)を計算
		return calcPrivateTime(attendance, timeSetting);
	}
	
	@Override
	public int calcOvertimeBefore(int startTime, int endTime) throws MospException {
		// 前残業時間(分)を計算
		return calcOvertimeBefore(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcOvertimeAfter(int startTime, int endTime) throws MospException {
		// 後残業時間(分)を計算
		return calcOvertimeAfter(startTime, endTime, attendance, workType, request, timeSetting, nextWorkType);
	}
	
	@Override
	public int calcOvertimeIn(int startTime, int endTime) throws MospException {
		// 法定内残業時間(分)を計算
		return calcOvertimeIn(startTime, endTime, attendance, workType, request, timeSetting, nextWorkType);
	}
	
	@Override
	public int calcOvertimeOut(int startTime, int endTime) throws MospException {
		// 法定外残業時間(分)を計算
		return calcOvertimeOut(startTime, endTime, attendance, workType, request, timeSetting, nextWorkType);
	}
	
	@Override
	public int calcWorkdayOvertimeIn(int startTime, int endTime) throws MospException {
		// 平日法定時間内残業時間(分)を計算
		return calcWorkdayOvertimeIn(startTime, endTime, attendance, workType, request, timeSetting, nextWorkType);
	}
	
	@Override
	public int calcWorkdayOvertimeOut(int startTime, int endTime) throws MospException {
		// 平日法定時間外残業時間(分)を計算
		return calcWorkdayOvertimeOut(startTime, endTime, attendance, workType, request, timeSetting, nextWorkType);
	}
	
	@Override
	public int calcPrescribedHolidayOvertimeIn(int startTime, int endTime) throws MospException {
		// 所定休日法定時間内残業時間(分)を計算
		return calcPrescribedHolidayOvertimeIn(startTime, endTime, attendance, workType, request, timeSetting,
				nextWorkType);
	}
	
	@Override
	public int calcPrescribedHolidayOvertimeOut(int startTime, int endTime) throws MospException {
		// 所定休日法定時間外残業時間(分)を計算
		return calcPrescribedHolidayOvertimeOut(startTime, endTime, attendance, workType, request, timeSetting,
				nextWorkType);
	}
	
	@Override
	public int calcNightWorkTime(int startTime, int endTime) throws MospException {
		// 深夜勤務時間(分)を計算
		return calcNightWorkTime(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcNightWorkWithinPrescribed(int startTime, int endTime) throws MospException {
		// 深夜所定労働時間内時間(分)を計算
		return calcNightWorkWithinPrescribed(startTime, endTime, attendance, workType, request, timeSetting,
				nextWorkType);
	}
	
	@Override
	public int calcNightWorkOvertime(int startTime, int endTime) throws MospException {
		// 深夜時間外時間(分)を計算
		return calcNightWorkOvertime(startTime, endTime, attendance, workType, request, timeSetting, nextWorkType);
	}
	
	@Override
	public int calcNightWorkOnLegalHoliday(int startTime, int endTime) throws MospException {
		// 深夜休日労働時間(分)を計算
		return calcNightWorkOnLegalHoliday(startTime, endTime, attendance, workType, request, timeSetting,
				nextWorkType);
	}
	
	@Override
	public int calcPrescribedHolidayWorkTime(int startTime, int endTime) throws MospException {
		// 所定休日勤務時間(分)を計算
		return calcPrescribedHolidayWorkTime(startTime, endTime, attendance, workType, request, timeSetting,
				nextWorkType);
	}
	
	@Override
	public int calcLegalHolidayWorkTime(int startTime, int endTime) throws MospException {
		// 法定休日勤務時間(分)を計算
		return calcLegalHolidayWorkTime(startTime, endTime, attendance, workType, request, timeSetting, nextWorkType);
	}
	
	@Override
	public int calcDecreaseTime(int startTime, int endTime) throws MospException {
		// 減額対象時間(分)を計算
		return calcDecreaseTime(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public int calcLegalHolidayWorkTimeIn(int startTime, int endTime) throws MospException {
		// 所定労働時間内法定休日労働時間(分)を計算
		return calcLegalHolidayWorkTimeIn(startTime, endTime, attendance, workType, request, timeSetting, nextWorkType);
	}
	
	@Override
	public int calcLegalHolidayWorkTimeOut(int startTime, int endTime) throws MospException {
		// 所定労働時間外法定休日労働時間(分)を計算
		return calcLegalHolidayWorkTimeOut(startTime, endTime, attendance, workType, request, timeSetting,
				nextWorkType);
	}
	
	@Override
	public int calcPrescribedHolidayWorkTimeIn(int startTime, int endTime) throws MospException {
		// 所定労働時間内所定休日労働時間(分)を計算
		return calcPrescribedHolidayWorkTimeIn(startTime, endTime, attendance, workType, request, timeSetting,
				nextWorkType);
	}
	
	@Override
	public int calcPrescribedHolidayWorkTimeOut(int startTime, int endTime) throws MospException {
		// 所定労働時間外所定休日労働時間(分)を計算
		return calcPrescribedHolidayWorkTimeOut(startTime, endTime, attendance, workType, request, timeSetting,
				nextWorkType);
	}
	
	@Override
	public Map<Integer, TimeDuration> getOvertimeBeforeRest(int startTime, int endTime) throws MospException {
		// 残前休憩時間(0:00からの分)を取得
		return getOvertimeBeforeRest(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	@Override
	public Map<Integer, TimeDuration> getOvertimeRest(int startTime, int endTime) throws MospException {
		// 前残休憩時間(0:00からの分)を取得
		Map<Integer, TimeDuration> overtimeBeforeRest = getOvertimeBeforeRest(startTime, endTime, attendance, workType,
				request, timeSetting);
		// 残業休憩時間(0:00からの分)群を取得
		return getOvertimeRest(startTime, endTime, overtimeBeforeRest, workType, request);
	}
	
	/**
	 * 始業時刻(勤怠計算上)(0:00からの分)を計算する。<br>
	 * <br>
	 * 入力始業時刻(0:00からの分)を丸めた時刻を元に計算する。<br>
	 * <br>
	 * 計算方法は、次の通り。<br>
	 * <br>
	 *  1.勤務前残業開始時刻よりも前に入力始業時刻(丸め)がある場合：勤務前残業開始時刻<br>
	 *  2.勤務前残業時間に入力始業時刻(丸め)が含まれる場合：入力始業時刻(丸め)<br>
	 *  3.時短時間1(有給)の間に入力始業時刻(丸め)がある場合：時短時間1(有給)開始時刻<br>
	 *  4.時短時間2(有給)の間に入力始業時刻(丸め)がある場合：時短時間2(有給)開始時刻<br>
	 *  5.時短時間1(無給)の間に入力始業時刻(丸め)がある場合：時短時間1(無給)終了時刻<br>
	 *  6.時短時間2(無給)の間に入力始業時刻(丸め)がある場合：時短時間2(無給)終了時刻<br>
	 *  7.規定始業時刻が入力始業時刻(丸め)よりも前であり直行である場合：規定始業時刻<br>
	 *  8.時間単位休暇の間に入力始業時刻がある場合：時間単位等休暇終了時刻<br>
	 *  9.入力始業時刻(丸め)が規定始業時刻よりも前であり休日出勤でない場合：規定始業時刻<br>
	 * 10.それ以外の場合：入力始業時刻(丸め)<br>
	 * <br>
	 * 補足：変更前のプログラムには6.時短時間2(無給)・・・は無い。<br>
	 * <br>
	 * @param inputStart  入力始業時刻(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 始業時刻(勤怠計算上)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcStartTime(int inputStart, AttendanceEntityInterface attendance, WorkTypeEntityInterface workType,
			RequestEntityInterface request, TimeSettingEntityInterface timeSetting) throws MospException {
		// 入力始業時刻が妥当でない場合(下書時等)
		if (isTimeValid(inputStart) == false) {
			// 妥当でない時刻を取得
			return inputStart;
		}
		// 規定時間(時間単位休暇含む)から開始時刻を取得
		int regularStart = getRegularAndHourlyHolidayTime(workType, request).getStartTime();
		// 勤務前残業時間(開始時刻及び終了時刻)(0:00からの分)を取得
		TimeDuration beforeOvertime = getBeforeOvertime(workType, request, timeSetting);
		// 時短時間(0:00からの分)を取得
		TimeDuration short1Pay = getShort1PayTime(workType, request);
		TimeDuration short2Pay = getShort2PayTime(workType, request);
		TimeDuration short1Unpay = getShort1UnpayTime(workType, request);
		TimeDuration short2Unpay = getShort2UnpayTime(workType, request);
		// 入力始業時刻(0:00からの分)を丸めた時刻を取得
		int roundedStart = timeSetting.roundDailyStart(inputStart);
		// 入力始業時刻(丸め)が含まれる時間単位休暇時間(0:00からの分)を取得
		TimeDuration holiday = TimeUtility.getContainTime(request.getHourlyHolidayTimes(IS_COMPLETED), roundedStart);
		// 1.勤務前残業開始時刻よりも前に入力始業時刻(丸め)がある場合
		if (beforeOvertime.isValid() && roundedStart < beforeOvertime.getStartTime()) {
			// 勤務前残業開始時刻を取得
			return beforeOvertime.getStartTime();
		}
		// 2.勤務前残業時間に入力始業時刻(丸め)が含まれる場合
		if (beforeOvertime.isValid() && beforeOvertime.isContain(roundedStart, true, false)) {
			// 入力始業時刻(丸め)を取得
			return roundedStart;
		}
		// 3.時短時間1(有給)の間に入力始業時刻(丸め)がある場合
		if (short1Pay.isValid() && short1Pay.isContain(roundedStart)) {
			// 時短時間1(有給)開始時刻を取得
			return short1Pay.getStartTime();
		}
		// 4.時短時間2(有給)の間に入力始業時刻(丸め)がある場合
		if (short2Pay.isValid() && short2Pay.isContain(roundedStart)) {
			// 時短時間2(有給)開始時刻を取得
			return short2Pay.getStartTime();
		}
		// 5.時短時間1(無給)の間に入力始業時刻(丸め)がある場合
		if (short1Unpay.isValid() && short1Unpay.isContain(roundedStart)) {
			// 時短時間1(無給)終了時刻を取得
			return short1Unpay.getEndTime();
		}
		// 6.時短時間2(無給)の間に入力始業時刻(丸め)がある場合
		if (short2Unpay.isValid() && short2Unpay.isContain(roundedStart)) {
			// 時短時間2(無給)終了時刻を取得
			return short2Unpay.getEndTime();
		}
		// 7.規定始業時刻が入力始業時刻(丸め)よりも前であり直行である場合
		if (regularStart < roundedStart && attendance.isAttendanceDirectStart()) {
			// 規定始業時刻を取得
			return regularStart;
		}
		// 8.時間単位等休暇の間に入力始業時刻(丸め)がある場合
		if (holiday.isValid()) {
			// 時間単位等休暇終了時刻を取得
			return holiday.getEndTime();
		}
		// 9.入力始業時刻(丸め)が規定始業時刻よりも前であり休日出勤でない場合
		if (roundedStart < regularStart && workType.isWorkOnHoliday() == false) {
			// 規定始業時刻を取得
			return regularStart;
		}
		// 10.それ以外の場合(入力始業時刻(丸め)を取得)
		return roundedStart;
	}
	
	/**
	 * 実始業時刻(0:00からの分)を計算する。<br>
	 * <br>
	 * 入力始業時刻(0:00からの分)を元に計算する。<br>
	 * 丸めは、必要に応じて行う。<br>
	 * <br>
	 * 計算方法は、次の通り。<br>
	 * <br>
	 * 1.勤務予定表示設定(勤怠設定情報)が無効である場合：入力始業時刻(丸め無し)<br>
	 * 2.勤務前残業実績登録(勤務形態情報)が有効である場合：入力始業時刻(丸め無し)<br>
	 * 3.時短時間1(有給)の間に入力始業時刻(丸め)がある場合：入力始業時刻(丸め)<br>
	 * 4.時間単位休暇の間に入力始業時刻(丸め)がある場合：入力始業時刻(丸め)<br>
	 * 5.それ以外の場合：始業時刻(勤怠計算上)(丸め)<br>
	 * <br>
	 * @param inputStart  入力始業時刻(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 実始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcActualStartTime(int inputStart, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 入力始業時刻が妥当でない場合(下書時等)
		if (isTimeValid(inputStart) == false) {
			// 妥当でない時刻を取得
			return inputStart;
		}
		// 1.勤務予定表示設定(勤怠設定情報)が無効である場合
		if (timeSetting.isScheduledTimeAvailable() == false) {
			// 入力始業時刻(丸め無し)を取得
			return inputStart;
		}
		// 2.勤務前残業実績登録(勤務形態情報)が有効である場合
		if (workType.isAutoBeforeOvertimeAvailable()) {
			// 入力始業時刻(丸め無し)を取得
			return inputStart;
		}
		// 入力始業時刻(丸め)を取得
		int roundedStart = timeSetting.roundDailyStart(inputStart);
		// 入力始業時刻(丸め)が含まれる時間単位休暇時間(0:00からの分)を取得
		TimeDuration holiday = TimeUtility.getContainTime(request.getHourlyHolidayTimes(IS_COMPLETED), roundedStart);
		// 3.時短時間1(有給)の間に入力始業時刻(丸め)がある場合
		if (getShort1PayTime(workType, request).isContain(roundedStart)) {
			// 入力始業時刻(丸め)を取得
			return roundedStart;
		}
		// 4.時間単位等休暇の間に入力始業時刻(丸め)がある場合
		if (holiday.isValid()) {
			// 入力始業時刻(丸め)を取得
			return roundedStart;
		}
		// 5.それ以外の場合(始業時刻(勤怠計算上)(丸め)を取得)
		return timeSetting.roundDailyStart(calcStartTime(inputStart, attendance, workType, request, timeSetting));
	}
	
	/**
	 * 終業時刻(勤怠計算上)(0:00からの分)を計算する。<br>
	 * <br>
	 * 入力終業時刻(0:00からの分)を丸めた時刻を元に計算する。<br>
	 * <br>
	 * 計算方法は、次の通り。<br>
	 * <br>
	 * 1.半休間の後残業時間終了時刻よりも後に入力終業時刻(丸め)がある場合：半休間の後残業時間終了時刻<br>
	 * 2.半休間の後残業時間に入力終業時刻(丸め)が含まれる場合：入力終業時刻(丸め)<br>
	 * 3.後半休であり入力終業時刻(丸め)が規定終業時刻よりも後である場合：規定終業時刻<br>
	 * 4.時短時間1(有給)の間に入力終業時刻(丸め)がある場合：時短時間1(有給)終了時刻<br>
	 * 5.時短時間2(有給)の間に入力終業時刻(丸め)がある場合：時短時間2(有給)終了時刻<br>
	 * 6.入力終業時刻(丸め)が時短時間2(無給)よりも前であり直帰である場合：時短時間2(無給)開始時刻<br>
	 * 7.入力終業時刻(丸め)が規定終業時刻よりも前であり直帰である場合：規定終業時刻<br>
	 * 8.時間単位休暇の間に入力終業時刻(丸め)がある場合：時間単位等休暇開始時刻<br>
	 * 9.それ以外の場合：入力終業時刻(丸め)<br>
	 * <br>
	 * @param inputEnd    入力終業時刻(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 終業時刻(勤怠計算上)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcEndTime(int inputEnd, AttendanceEntityInterface attendance, WorkTypeEntityInterface workType,
			RequestEntityInterface request, TimeSettingEntityInterface timeSetting) throws MospException {
		// 入力終業時刻が妥当でない場合(下書時等)
		if (isTimeValid(inputEnd) == false) {
			// 妥当でない時刻を取得
			return inputEnd;
		}
		// 規定時間(時間単位休暇含む)から開始時刻を取得
		int regularEnd = getRegularAndHourlyHolidayTime(workType, request).getEndTime();
		// 半休間の後残業時間(0:00からの分)を取得
		TimeDuration bitweenAfterOvertime = getBitweenAfterOvertime(workType, request);
		// 時短時間(0:00からの分)を取得
		TimeDuration short1Pay = getShort1PayTime(workType, request);
		TimeDuration short2Pay = getShort2PayTime(workType, request);
		TimeDuration short2Unpay = getShort2UnpayTime(workType, request);
		// 入力終業時刻(0:00からの分)を丸めた時刻を取得
		int roundedEnd = timeSetting.roundDailyEnd(inputEnd);
		// 入力終業時刻(丸め)が含まれる時間単位休暇時間(0:00からの分)を取得
		TimeDuration holiday = TimeUtility.getContainTime(request.getHourlyHolidayTimes(IS_COMPLETED), roundedEnd);
		// 1.半休間の後残業時間終了時刻よりも後に入力終業時刻(丸め)がある場合
		if (bitweenAfterOvertime.isValid() && bitweenAfterOvertime.getEndTime() < roundedEnd) {
			// 半休間の後残業時間終了時刻を取得
			return bitweenAfterOvertime.getEndTime();
		}
		// 2.半休間の後残業時間に入力終業時刻(丸め)が含まれる場合
		if (bitweenAfterOvertime.isValid() && bitweenAfterOvertime.isContain(roundedEnd)) {
			// 入力終業時刻(丸め)を取得
			return roundedEnd;
		}
		// 3.後半休であり入力終業時刻(丸め)が規定終業時刻よりも後である場合
		if (request.isPmHoliday(IS_COMPLETED) && regularEnd < roundedEnd) {
			// 規定終業時刻を取得
			return regularEnd;
		}
		// 4.時短時間1(有給)の間に入力終業時刻(丸め)がある場合
		if (short1Pay.isValid() && short1Pay.isContain(roundedEnd)) {
			// 時短時間1(有給)終了時刻を取得
			return short1Pay.getEndTime();
		}
		// 5.時短時間2(有給)の間に入力終業時刻(丸め)がある場合
		if (short2Pay.isValid() && short2Pay.isContain(roundedEnd)) {
			// 時短時間2(有給)終了時刻を取得
			return short2Pay.getEndTime();
		}
		// 6.入力終業時刻(丸め)が時短時間2(無給)よりも前であり直帰である場合
		if (roundedEnd < short2Unpay.getEndTime() && attendance.isAttendanceDirectEnd()) {
			// 時短時間2(無給)開始時刻を取得
			return short2Unpay.getStartTime();
		}
		// 7.入力終業時刻(丸め)が規定終業時刻よりも前であり直帰である場合
		if (roundedEnd < regularEnd && attendance.isAttendanceDirectEnd()) {
			// 規定終業時刻を取得
			return regularEnd;
		}
		// 8.時間単位等休暇の間に入力終業時刻(丸め)がある場合
		if (holiday.isValid()) {
			// 時間単位等休暇開始時刻を取得
			return holiday.getStartTime();
		}
		// 9.それ以外の場合(入力終業時刻(丸め)を取得)
		return roundedEnd;
	}
	
	/**
	 * 実終業時刻(0:00からの分)を計算する。<br>
	 * <br>
	 * 入力終業時刻(0:00からの分)を元に計算する。<br>
	 * 丸めは、必要に応じて行う。<br>
	 * <br>
	 * 計算方法は、次の通り。<br>
	 * <br>
	 * 1.勤務予定表示設定(勤怠設定情報)が無効である場合：入力終業時刻(丸め無し)<br>
	 * 2.時短時間2(有給)の間に入力終業時刻(丸め)がある場合：入力終業時刻(丸め)<br>
	 * 3.時間単位休暇の間に入力終業時刻(丸め)がある場合：入力終業時刻(丸め)<br>
	 * 4.それ以外の場合：終業時刻(勤怠計算上)(丸め)<br>
	 * <br>
	 * @param inputEnd    入力終業時刻(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 実始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcActualEndTime(int inputEnd, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 入力終業時刻が妥当でない場合(下書時等)
		if (isTimeValid(inputEnd) == false) {
			// 妥当でない時刻を取得
			return inputEnd;
		}
		// 1.勤務予定表示設定(勤怠設定情報)が無効である場合
		if (timeSetting.isScheduledTimeAvailable() == false) {
			// 入力終業時刻(丸め無し)を取得
			return inputEnd;
		}
		// 入力終業時刻(丸め)を取得
		int roundedEnd = timeSetting.roundDailyEnd(inputEnd);
		// 入力終業時刻(丸め)が含まれる時間単位休暇時間(0:00からの分)を取得
		TimeDuration holiday = TimeUtility.getContainTime(request.getHourlyHolidayTimes(IS_COMPLETED), roundedEnd);
		// 2.時短時間2(有給)の間に入力終業時刻(丸め)がある場合
		if (getShort2PayTime(workType, request).isContain(roundedEnd)) {
			// 入力終業時刻(丸め)を取得
			return roundedEnd;
		}
		// 3.時間単位等休暇の間に入力終業時刻(丸め)がある場合
		if (holiday.isValid()) {
			// 入力終業時刻(丸め)を取得
			return roundedEnd;
		}
		// 4.それ以外の場合(終業時刻(勤怠計算上)(丸め)を取得)
		return timeSetting.roundDailyEnd(calcEndTime(inputEnd, attendance, workType, request, timeSetting));
	}
	
	/**
	 * 遅刻時間(分)を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#calcActualLateTime(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface)}
	 * を参照。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 実遅刻時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcLateTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 遅刻理由が電車遅延か会社指示である場合
		if (attendance.isLateReasonTrain() || attendance.isLateReasonCompany()) {
			// 0を取得
			return 0;
		}
		// 実遅刻時間(分)(丸め)を取得
		return calcActualLateTime(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	/**
	 * 実遅刻時間(分)を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getLateTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface)}
	 * を参照。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 実遅刻時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcActualLateTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 遅刻時間群を取得
		Map<Integer, TimeDuration> lates = getLateTimes(startTime, endTime, attendance, workType, request, timeSetting);
		// 実遅刻時間(分)(丸め)を取得
		return timeSetting.roundDailyLate(TimeUtility.getMinutes(lates));
	}
	
	/**
	 * 早退時間(分)を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#calcActualLeaveEarlyTime(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface)}
	 * を参照。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 始業時刻(勤怠計算上)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcLeaveEarlyTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 早退理由が会社指示である場合
		if (attendance.isLeaveEarlyReasonCompany()) {
			// 0を取得
			return 0;
		}
		// 実早退時間(分)(丸め)を取得
		return calcActualLeaveEarlyTime(startTime, endTime, attendance, workType, request, timeSetting);
	}
	
	/**
	 * 実早退時間(分)を計算する。<br>
	 * <br>
	 * 終業時刻(勤怠計算上)(0:00からの分)を元に計算する。<br>
	 * <br>
	 * 計算方法は、次の通り。<br>
	 * <br>
	 * 1.休日出勤である場合：0<br>
	 * 2.終業時刻(勤怠計算上)が規定終業時刻(時短時間含む)以降である場合：0<br>
	 * 3.それ以外の場合：
	 * 　終業時刻(勤怠計算上)から規定時間(時短時間含む)終了時刻までの時間
	 * 　(規定休憩時間及び時間単位休暇時間を除く)<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 始業時刻(勤怠計算上)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcActualLeaveEarlyTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 0を取得
			return 0;
		}
		// 規定時間(時短時間含む)を取得
		TimeDuration regularTime = getRegularAndShortTime(workType, request);
		// 1.休日出勤である場合
		if (workType.isWorkOnHoliday()) {
			// 0を取得
			return 0;
		}
		// 2.終業時刻(勤怠計算上)が規定終業時刻(時短時間含む)以降である場合
		if (regularTime.getEndTime() <= endTime) {
			// 0を取得
			return 0;
		}
		// 3.それ以外の場合
		// 早退カウント開始時刻(規定始業時刻)を準備(早退は最大でも規定始業時刻から)
		int leaveEarlyStartTime = regularTime.getStartTime();
		// 早退カウント開始時刻が終業時刻(勤怠計算上)より前である場合
		if (leaveEarlyStartTime < endTime) {
			// 早退カウント開始時刻を終業時刻(勤怠計算上)に設定
			leaveEarlyStartTime = endTime;
		}
		// 早退時間間隔(早退カウント開始時刻から規定終業時刻まで)を準備
		TimeDuration leaveEarlyTime = TimeDuration.getInstance(leaveEarlyStartTime, regularTime.getEndTime());
		// 実早退時間(分)を準備(早退カウント開始時刻から規定終業時刻までの分)
		int leaveEarlyMinutes = leaveEarlyTime.getMinutes();
		// 規定休憩時間及び時間単位休暇時間群(キー：開始時刻(キー順))を作成
		Map<Integer, TimeDuration> restHolidays = getRestAndHourlyHolidayTimes(startTime, endTime, workType, request);
		// 規定休憩時間及び時間単位休暇時間と重複する時間(分)を減算
		leaveEarlyMinutes -= leaveEarlyTime.getOverlapMinutes(restHolidays);
		// 実早退時間(分)(丸め)を取得
		return timeSetting.roundDailyLeaveEarly(leaveEarlyMinutes);
	}
	
	/**
	 * 勤務時間(分)を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getWorkTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface) }
	 * を参照。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcWorkTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 勤務時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> times = getWorkTimes(startTime, endTime, attendance, workType, request, timeSetting);
		// 勤務時間(分)を取得
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(times));
	}
	
	/**
	 * 所定労働時間(分)を計算する。<br>
	 * <br>
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @return 所定労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcPrescribedWorkTime(WorkTypeEntityInterface workType, RequestEntityInterface request)
			throws MospException {
		// 休日出勤である場合
		if (workType.isWorkOnHoliday()) {
			// 0を取得
			return 0;
		}
		// 所定労働時間を勤務形態エンティティから取得
		int prescribedWorkTime = workType.getWorkTime();
		// 前半休か後半休である場合
		if (request.isAmHoliday(IS_COMPLETED) || request.isPmHoliday(IS_COMPLETED)) {
			// 所定労働時間を再設定
			prescribedWorkTime = getRegularTime(workType, request).getMinutes();
		}
		// 法定労働時間を取得
		int legalWorkTime = getLegalWorkTime();
		// 所定労働時間を取得(最大で法定労働時間)
		return prescribedWorkTime < legalWorkTime ? prescribedWorkTime : legalWorkTime;
	}
	
	/**
	 * 所定労働時間内労働時間(分)を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getWorkTimeWithinPrescribed(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 所定労働時間内労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcWorkTimeWithinPrescribed(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 所定労働時間内労働時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> times = getWorkTimeWithinPrescribed(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 所定労働時間内労働時間(分)を取得
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(times));
	}
	
	/**
	 * 無給時短時間(分)を計算する。<br>
	 * <br>
	 * 後残業用勤務時間及び後残業で消化されずに残っている無給時短時間を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getConsumedShortUnpay(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface)}
	 * を参照。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcShortUnpay(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 0を取得
			return 0;
		}
		// 時短時間(無給)(分)を取得
		int unpay = TimeUtility.getMinutes(workType.getShortUnpayTimes());
		// 勤務時間(後残業より前)に含まれる無給時短時間(分)を取得
		int contained = getContainedShortUnpay(startTime, endTime, attendance, workType, request, timeSetting);
		// 消化無給時短時間(分)を取得
		int consumed = getConsumedShortUnpay(startTime, endTime, attendance, workType, request, timeSetting);
		// 無給時短時間(分)を取得(消化されずに残っている無給時短時間)
		return timeSetting.roundDailyShortUnpaid(unpay - contained - consumed);
	}
	
	/**
	 * 休憩時間(分)を計算する。<br>
	 * <br>
	 * 次の休憩時間を統合したものから休憩時間(分)を取得する。<br>
	 * ・入力休憩時間<br>
	 * ・残前休憩時間<br>
	 * ・残業休憩時間<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 休憩時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcRestTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 0を取得
			return 0;
		}
		// 全休憩時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> rests = getAllRests(startTime, endTime, attendance, workType, request, timeSetting);
		// 休憩時間(分)を取得
		return timeSetting.roundDailyRest(TimeUtility.getMinutes(rests));
	}
	
	/**
	 * 深夜休憩時間(分)を計算する。<br>
	 * <br>
	 * 休憩時間と深夜時間が重複する時間を取得する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getAllRests(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface) }
	 * を参照。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 深夜休憩時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcNightRestTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 0を取得
			return 0;
		}
		// 全休憩時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> rests = getAllRests(startTime, endTime, attendance, workType, request, timeSetting);
		// 深夜時間を取得
		Map<Integer, TimeDuration> nightTimes = getNightTimes();
		// 深夜休憩時間(分)を取得(休憩時間と深夜時間が重複する時間)
		return timeSetting.roundDailyRest(TimeUtility.getMinutes(TimeUtility.getOverlap(rests, nightTimes)));
	}
	
	/**
	 * 法定休出休憩時間(分)を計算する。<br>
	 * <br>
	 * 法定休日時間と休憩時間が重複している時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 法定休出休憩時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcLegalHolidayRestTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 0を取得
			return 0;
		}
		// 全休憩時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> rests = getAllRests(startTime, endTime, attendance, workType, request, timeSetting);
		// 法定休日時間を取得
		TimeDuration legalTime = getLegalTime(workType, timeSetting, nextWorkType);
		// 法定休日時間と休憩時間が重複している時間を取得
		return timeSetting.roundDailyRest(TimeUtility.getMinutes(legalTime.getOverlap(rests)));
	}
	
	/**
	 * 所定休出休憩時間(分)を計算する。<br>
	 * <br>
	 * 所定休日時間と休憩時間が重複している時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 所定休出休憩時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcPrescribedHolidayRestTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 0を取得
			return 0;
		}
		// 全休憩時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> rests = getAllRests(startTime, endTime, attendance, workType, request, timeSetting);
		// 所定休日時間を取得
		TimeDuration prescribedTime = getPrescribedTime(workType, timeSetting, nextWorkType);
		// 所定休日時間と休憩時間が重複している時間を取得
		return timeSetting.roundDailyRest(TimeUtility.getMinutes(prescribedTime.getOverlap(rests)));
	}
	
	/**
	 * 公用外出時間(分)を計算する。<br>
	 * @param attendance  勤怠(日々)エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 公用外出時間(分)
	 */
	protected int calcPublicTime(AttendanceEntityInterface attendance, TimeSettingEntityInterface timeSetting) {
		// 公用外出時間(分)を計算
		return TimeUtility.getMinutes(attendance.getPublicGoOutTimes(timeSetting));
	}
	
	/**
	 * 私用外出時間(分)を計算する。<br>
	 * @param attendance 勤怠(日々)エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 私用外出時間(分)
	 */
	protected int calcPrivateTime(AttendanceEntityInterface attendance, TimeSettingEntityInterface timeSetting) {
		// 私用外出時間(分)を計算
		return TimeUtility.getMinutes(attendance.getPrivateGoOutTimes(timeSetting));
	}
	
	/**
	 * 前残業時間(分)を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getOvertimeBeforeTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface)}
	 * を参照。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 前残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcOvertimeBefore(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 前残業時間群を計算
		Map<Integer, TimeDuration> overtimeBeforeTimes = getOvertimeBeforeTimes(startTime, endTime, attendance,
				workType, request, timeSetting);
		// 前残業時間(分)を取得
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(overtimeBeforeTimes));
	}
	
	/**
	 * 後残業時間(分)を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getOvertimeAfterTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 後残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcOvertimeAfter(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 後残業時間群を計算
		Map<Integer, TimeDuration> overtimeAfterTimes = getOvertimeAfterTimes(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 後残業時間(分)を取得
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(overtimeAfterTimes));
	}
	
	/**
	 * 法定内残業時間(分)を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getOvertimeInTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 後残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcOvertimeIn(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 法定内残業時間を計算
		Map<Integer, TimeDuration> overtimeInTimes = getOvertimeInTimes(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 法定内残業時間(分)を取得
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(overtimeInTimes));
	}
	
	/**
	 * 法定外残業時間(分)を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getOvertimeOutTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 法定外残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcOvertimeOut(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 法定外残業時間を計算
		Map<Integer, TimeDuration> overtimeOutTimes = getOvertimeOutTimes(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 法定外残業時間(分)を取得
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(overtimeOutTimes));
	}
	
	/**
	 * 平日法定時間内残業時間(分)を計算する。<br>
	 * <br>
	 * 平日時間と法定内残業時間が重複している時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 平日法定時間内残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcWorkdayOvertimeIn(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 法定内残業時間を取得
		Map<Integer, TimeDuration> overtimeInTimes = getOvertimeInTimes(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 平日時間を取得
		TimeDuration workDay = getWorkdayTime(workType, timeSetting, nextWorkType);
		// 平日時間と法定内残業時間が重複している時間を取得
		return timeSetting.roundDailyWork(workDay.getOverlapMinutes(overtimeInTimes));
	}
	
	/**
	 * 平日法定時間外残業時間(分)を計算する。<br>
	 * <br>
	 * 平日時間と法定外残業時間が重複している時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 平日法定時間外残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcWorkdayOvertimeOut(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 法定外残業時間を取得
		Map<Integer, TimeDuration> overtimeOutTimes = getOvertimeOutTimes(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 平日時間を取得
		TimeDuration workDay = getWorkdayTime(workType, timeSetting, nextWorkType);
		// 平日時間と法定外残業時間が重複している時間を取得
		return timeSetting.roundDailyWork(workDay.getOverlapMinutes(overtimeOutTimes));
	}
	
	/**
	 * 所定休日法定時間内残業時間(分)を計算する。<br>
	 * <br>
	 * 所定休日時間と法定内残業時間が重複している時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 所定休日法定時間内残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcPrescribedHolidayOvertimeIn(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 法定内残業時間を取得
		Map<Integer, TimeDuration> overtimeInTimes = getOvertimeInTimes(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 所定休日(所定休日出勤含む)時間を取得を取得
		TimeDuration prescribed = getPrescribedTime(workType, timeSetting, nextWorkType);
		// 所定休日時間と法定内残業時間が重複している時間を取得
		return timeSetting.roundDailyWork(prescribed.getOverlapMinutes(overtimeInTimes));
	}
	
	/**
	 * 所定休日法定時間外残業時間(分)を計算する。<br>
	 * <br>
	 * 所定休日時間と法定外残業時間が重複している時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 所定休日法定時間外残業時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcPrescribedHolidayOvertimeOut(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 法定外残業時間を取得
		Map<Integer, TimeDuration> overtimeOutTimes = getOvertimeOutTimes(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 所定休日(所定休日出勤含む)時間を取得を取得
		TimeDuration prescribed = getPrescribedTime(workType, timeSetting, nextWorkType);
		// 所定休日時間と法定外残業時間が重複している時間を取得
		return timeSetting.roundDailyWork(prescribed.getOverlapMinutes(overtimeOutTimes));
	}
	
	/**
	 * 深夜勤務時間(分)を計算する。<br>
	 * <br>
	 * 勤務時間と深夜時間が重複する時間を取得する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getNightWorkTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @return 深夜勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcNightWorkTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 0を取得
			return 0;
		}
		// 深夜勤務時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> nightWorkTimes = getNightWorkTimes(startTime, endTime, attendance, workType, request,
				timeSetting);
		// 深夜勤務時間(分)を取得
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(nightWorkTimes));
	}
	
	/**
	 * 深夜所定労働時間内時間(分)を計算する。<br>
	 * <br>
	 * 所定労働時間内労働時間と深夜勤務時間が重複する時間を取得する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getWorkTimeWithinPrescribed(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 深夜勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcNightWorkWithinPrescribed(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 0を取得
			return 0;
		}
		// 所定労働時間内労働時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> times = getWorkTimeWithinPrescribed(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 深夜勤務時間群を取得
		Map<Integer, TimeDuration> nightWorkTimes = getNightWorkTimes(startTime, endTime, attendance, workType, request,
				timeSetting);
		// 深夜所定労働時間内時間(分)を取得(所定労働時間内労働時間と深夜勤務時間が重複する時間)
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(TimeUtility.getOverlap(times, nightWorkTimes)));
	}
	
	/**
	 * 深夜時間外時間(分)を計算する。<br>
	 * <br>
	 * 時間外時間と深夜勤務時間が重複する時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 深夜時間外時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcNightWorkOvertime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 0を取得
			return 0;
		}
		// 残業時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> overtimeTimes = getOvertimeTimes(startTime, endTime, attendance, workType, request,
				timeSetting, nextWorkType);
		// 深夜勤務時間群を取得
		Map<Integer, TimeDuration> nightTimes = getNightWorkTimes(startTime, endTime, attendance, workType, request,
				timeSetting);
		// 深夜時間外時間(分)を取得(残業時間と深夜勤務時間が重複する時間)
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(TimeUtility.getOverlap(overtimeTimes, nightTimes)));
	}
	
	/**
	 * 深夜休日労働時間(分)を計算する。<br>
	 * <br>
	 * 法定休日勤務時間と深夜時間が重複する時間を取得する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getWorkOnLegalTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 深夜休日労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcNightWorkOnLegalHoliday(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 0を取得
			return 0;
		}
		// 法定休日勤務時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> times = getWorkOnLegalTimes(startTime, endTime, attendance, workType, request,
				timeSetting, nextWorkType);
		// 深夜時間を取得
		Map<Integer, TimeDuration> nightTimes = getNightTimes();
		// 深夜休日労働時間(分)を取得(法定休日勤務時間と深夜時間が重複する時間)
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(TimeUtility.getOverlap(times, nightTimes)));
	}
	
	/**
	 * 所定休日勤務時間(分)を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getWorkOnPrescribedTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 所定休日勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcPrescribedHolidayWorkTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 所定休日勤務時間を計算
		Map<Integer, TimeDuration> times = getWorkOnPrescribedTimes(startTime, endTime, attendance, workType, request,
				timeSetting, nextWorkType);
		// 所定休日勤務時間(分)を取得
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(times));
	}
	
	/**
	 * 法定休日勤務時間(分)を計算する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getWorkOnLegalTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 法定休日勤務時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcLegalHolidayWorkTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 法定休日勤務時間を計算
		Map<Integer, TimeDuration> times = getWorkOnLegalTimes(startTime, endTime, attendance, workType, request,
				timeSetting, nextWorkType);
		// 法定休日勤務時間(分)を取得
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(times));
	}
	
	/**
	 * 減額対象時間(分)を計算する。<br>
	 * <br>
	 * 次の時間を加算する。<br>
	 * ・遅刻時間<br>
	 * ・早退時間<br>
	 * ・私用外出時間<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @return 減額対象時間((分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcDecreaseTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 減額対象時間(分)を準備
		int decrease = 0;
		// 遅刻時間を加算
		decrease += calcLateTime(startTime, endTime, attendance, workType, request, timeSetting);
		// 早退時間を加算
		decrease += calcLeaveEarlyTime(startTime, endTime, attendance, workType, request, timeSetting);
		// 私用外出時間を加算
		decrease += calcPrivateTime(attendance, timeSetting);
		// 減額対象時間(分)を取得
		return timeSetting.roundDailyDecrease(decrease);
	}
	
	/**
	 * 所定労働時間内法定休日労働時間(分)を計算する。<br>
	 * <br>
	 * 翌日が法定休日(法定休日出勤含む)で、日を跨ぐ勤務形態で勤務した場合に、
	 * 所定労働時間内で法定休日労働をすることがある。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 所定労働時間内法定休日労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcLegalHolidayWorkTimeIn(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 残業開始時刻(0:00からの分)を取得
		int overtimeStartTime = getOvertimeStartTime(startTime, endTime, attendance, workType, request, timeSetting);
		// 所定労働時間内法定休日労働時間(分)の対象となる時間(24:00～残業開始時刻)を取得
		TimeDuration duration = TimeDuration.getInstance(TimeUtility.getNextDayStart(), overtimeStartTime);
		// 残業開始時刻(0:00からの分)が翌日の開始時刻(24:00)以前である場合
		if (duration.isValid() == false) {
			// 所定労働時間内法定休日労働時間は0
			return 0;
		}
		// 法定休日勤務時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> workOnLegalTimes = getWorkOnLegalTimes(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 対象時間と法定休日勤務時間が重複する時間を取得
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(duration.getOverlap(workOnLegalTimes)));
	}
	
	/**
	 * 所定労働時間外法定休日労働時間(分)を計算する。<br>
	 * <br>
	 * 法定休日勤務時間(分)と所定労働時間内法定休日労働時間(分)の差。<br>
	 * <br>
	 * {@link AttendCalcEntity#calcLegalHolidayWorkTime(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * 及び
	 * {@link AttendCalcEntity#calcLegalHolidayWorkTimeIn(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 所定労働時間外法定休日労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcLegalHolidayWorkTimeOut(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 法定休日勤務時間(分)を計算
		int legalHolidayWorkTime = calcLegalHolidayWorkTime(startTime, endTime, attendance, workType, request,
				timeSetting, nextWorkType);
		// 所定労働時間内法定休日労働時間(分)を計算
		int legalHolidayWorkTimeIn = calcLegalHolidayWorkTimeIn(startTime, endTime, attendance, workType, request,
				timeSetting, nextWorkType);
		// 法定休日勤務時間(分)と所定労働時間内法定休日労働時間(分)の差を取得
		return timeSetting.roundDailyWork(legalHolidayWorkTime - legalHolidayWorkTimeIn);
	}
	
	/**
	 * 所定労働時間内所定休日労働時間(分)を計算する。<br>
	 * <br>
	 * 翌日が所定休日(所定休日出勤含む)で、日を跨ぐ勤務形態で勤務した場合に、
	 * 所定労働時間内で所定休日労働をすることがある。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 所定労働時間内所定休日労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcPrescribedHolidayWorkTimeIn(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 残業開始時刻(0:00からの分)を取得
		int overtimeStartTime = getOvertimeStartTime(startTime, endTime, attendance, workType, request, timeSetting);
		// 所定労働時間内所定休日労働時間(分)の対象となる時間(24:00～残業開始時刻)を取得
		TimeDuration duration = TimeDuration.getInstance(TimeUtility.getNextDayStart(), overtimeStartTime);
		// 残業開始時刻(0:00からの分)が翌日の開始時刻(24:00)以前である場合
		if (duration.isValid() == false) {
			// 所定労働時間内法定休日労働時間は0
			return 0;
		}
		// 所定休日勤務時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> workOnPrescribedTimes = getWorkOnPrescribedTimes(startTime, endTime, attendance,
				workType, request, timeSetting, nextWorkType);
		// 対象時間と所定休日勤務時間が重複する時間を取得
		return timeSetting.roundDailyWork(TimeUtility.getMinutes(duration.getOverlap(workOnPrescribedTimes)));
	}
	
	/**
	 * 所定労働時間外所定休日労働時間(分)を計算する。<br>
	 * <br>
	 * 所定休日勤務時間(分)と所定労働時間内所定休日労働時間(分)の差。<br>
	 * <br>
	 * {@link AttendCalcEntity#calcPrescribedHolidayWorkTime(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * 及び
	 * {@link AttendCalcEntity#calcPrescribedHolidayWorkTimeIn(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 所定労働時間外所定休日労働時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int calcPrescribedHolidayWorkTimeOut(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting,
			String nextWorkType) throws MospException {
		// 所定休日勤務時間(分)を計算
		int prescribedHolidayWorkTime = calcPrescribedHolidayWorkTime(startTime, endTime, attendance, workType, request,
				timeSetting, nextWorkType);
		// 所定労働時間内所定休日労働時間(分)を計算
		int prescribedHolidayWorkTimeIn = calcPrescribedHolidayWorkTimeIn(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 所定休日勤務時間(分)と所定労働時間内所定休日労働時間(分)の差を取得
		return timeSetting.roundDailyWork(prescribedHolidayWorkTime - prescribedHolidayWorkTimeIn);
	}
	
	/**
	 * 規定時間(0:00からの分)を取得する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#getRegularTime(RequestEntityInterface, boolean)}
	 * を参照。<br>
	 * <br>
	 * @param workType 勤務形態エンティティ
	 * @param request  申請エンティティ
	 * @return 規定時間(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getRegularTime(WorkTypeEntityInterface workType, RequestEntityInterface request)
			throws MospException {
		// 規定時間(0:00からの分)を取得
		return workType.getRegularTime(request, IS_COMPLETED);
	}
	
	/**
	 * 規定時間(時間単位休暇含む)(0:00からの分)を取得する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#getRegularAndHourlyHolidayTime(RequestEntityInterface, boolean)}
	 * を参照。<br>
	 * <br>
	 * @param workType 勤務形態エンティティ
	 * @param request  申請エンティティ
	 * @return 規定時間(時間単位休暇含む)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getRegularAndHourlyHolidayTime(WorkTypeEntityInterface workType,
			RequestEntityInterface request) throws MospException {
		// 規定時間(0:00からの分)を取得
		return workType.getRegularAndHourlyHolidayTime(request, IS_COMPLETED);
	}
	
	/**
	 * 規定時間(時短時間含む)(0:00からの分)を取得する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#getRegularAndShortTime(RequestEntityInterface, boolean)}
	 * を参照。<br>
	 * <br>
	 * @param workType 勤務形態エンティティ
	 * @param request  申請エンティティ
	 * @return 規定時間(時短時間含む)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getRegularAndShortTime(WorkTypeEntityInterface workType, RequestEntityInterface request)
			throws MospException {
		// 規定時間(0:00からの分)を取得
		return workType.getRegularAndShortTime(request, IS_COMPLETED);
	}
	
	/**
	 * 勤務前残業時間(0:00からの分)を取得する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#getBeforeOvertime(TimeSettingEntityInterface, RequestEntityInterface, boolean)}
	 * を参照。<br>
	 * <br>
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 勤務前残業時間(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getBeforeOvertime(WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting) throws MospException {
		// 勤務前残業時間(開始時刻及び終了時刻)(0:00からの分)を取得
		return workType.getBeforeOvertime(timeSetting, request, IS_COMPLETED);
	}
	
	/**
	 * 半休間の後残業時間(0:00からの分)を取得する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#getBitweenAfterOvertime(RequestEntityInterface, boolean)}
	 * を参照。<br>
	 * <br>
	 * @param workType 勤務形態エンティティ
	 * @param request  申請エンティティ
	 * @return 半休間の後残業時間(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getBitweenAfterOvertime(WorkTypeEntityInterface workType, RequestEntityInterface request)
			throws MospException {
		// 半休間の後残業時間(0:00からの分)を取得
		return workType.getBitweenAfterOvertime(request, IS_COMPLETED);
	}
	
	/**
	 * 時短時間1(有給)(0:00からの分)を取得する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#getShort1PayTime(RequestEntityInterface, boolean)}
	 * を参照。<br>
	 * <br>
	 * @param workType 勤務形態エンティティ
	 * @param request  申請エンティティ
	 * @return 時短時間1(有給)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getShort1PayTime(WorkTypeEntityInterface workType, RequestEntityInterface request)
			throws MospException {
		// 時短時間1(有給)(0:00からの分)を取得
		return workType.getShort1PayTime(request, IS_COMPLETED);
	}
	
	/**
	 * 時短時間2(有給)(0:00からの分)を取得する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#getShort2PayTime(RequestEntityInterface, boolean)}
	 * を参照。<br>
	 * <br>
	 * @param workType 勤務形態エンティティ
	 * @param request  申請エンティティ
	 * @return 時短時間2(有給)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getShort2PayTime(WorkTypeEntityInterface workType, RequestEntityInterface request)
			throws MospException {
		// 時短時間2(有給)(0:00からの分)を取得
		return workType.getShort2PayTime(request, IS_COMPLETED);
	}
	
	/**
	 * 時短時間1(無給)(0:00からの分)を取得する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#getShort1UnpayTime(RequestEntityInterface, boolean)}
	 * を参照。<br>
	 * <br>
	 * @param workType 勤務形態エンティティ
	 * @param request  申請エンティティ
	 * @return 時短時間1(無給)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getShort1UnpayTime(WorkTypeEntityInterface workType, RequestEntityInterface request)
			throws MospException {
		// 時短時間1(無給)(0:00からの分)を取得
		return workType.getShort1UnpayTime(request, IS_COMPLETED);
	}
	
	/**
	 * 時短時間2(無給)(0:00からの分)を取得する。<br>
	 * <br>
	 * {@link WorkTypeEntityInterface#getShort2UnpayTime(RequestEntityInterface, boolean)}
	 * を参照。<br>
	 * <br>
	 * @param workType 勤務形態エンティティ
	 * @param request  申請エンティティ
	 * @return 時短時間2(無給)(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected TimeDuration getShort2UnpayTime(WorkTypeEntityInterface workType, RequestEntityInterface request)
			throws MospException {
		// 時短時間2(無給)(0:00からの分)を取得
		return workType.getShort2UnpayTime(request, IS_COMPLETED);
	}
	
	/**
	 * 時間単位等休暇時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * 次の休憩時間を統合したものを取得する。<br>
	 * ・時間単位休暇時間<br>
	 * ・追加休暇時間<br>
	 * <br>
	 * @param request 申請エンティティ
	 * @return 休暇時間(0:00からの分)群
	 */
	protected Map<Integer, TimeDuration> getHolidayTimes(RequestEntityInterface request) {
		// 時間単位休暇時間間隔群を取得
		Map<Integer, TimeDuration> hourlies = request.getHourlyHolidayTimes(IS_COMPLETED);
		// 追加休暇時間間隔群を取得
		Map<Integer, TimeDuration> additionals = TimeUtility.getDurations(params, KEY_ADD_HOLIDAY_TIMES);
		// 統合した時間間隔群を取得
		return TimeUtility.mergeDurations(hourlies, additionals);
	}
	
	/**
	 * 全休憩時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * 次の休憩時間を統合したものを取得する。<br>
	 * ・入力休憩時間<br>
	 * ・残前休憩時間<br>
	 * ・残業休憩時間<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 残業前休憩時間(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getAllRests(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 全入力休憩時間(丸め)群を勤怠(日々)エンティティから取得
		Map<Integer, TimeDuration> inputRests = attendance.getRestTimes(timeSetting);
		// 残前休憩時間(0:00からの分)を取得
		Map<Integer, TimeDuration> overtimeBeforeRest = getOvertimeBeforeRest(startTime, endTime, attendance, workType,
				request, timeSetting);
		// 残業休憩時間群を取得
		Map<Integer, TimeDuration> otRests = getOvertimeRest(startTime, endTime, overtimeBeforeRest, workType, request);
		// 残業休憩時間群と前残休憩時間とを統合
		Map<Integer, TimeDuration> overRests = TimeUtility.mergeDurations(otRests, overtimeBeforeRest);
		// 更に全入力休憩時間(丸め)群を統合し取得
		return TimeUtility.mergeDurations(inputRests, overRests);
	}
	
	/**
	 * 残業開始時刻(0:00からの分)を取得する。<br>
	 * <br>
	 * 無給時短時間の消化分は、ここでは考慮しない。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 残業開始時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int getOvertimeStartTime(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 法定労働時間を取得
		int legalWorkTime = getLegalWorkTime();
		// 残業開始までの勤務時間(分)を取得
		int overtimeBeforeRestStart = workType.getWorkTimeBeforeOvertime(request, legalWorkTime, IS_COMPLETED);
		// 規定始業時刻を取得
		int regularStart = getRegularTime(workType, request).getStartTime();
		// 休日出勤である場合
		if (workType.isWorkOnHoliday()) {
			// 規定始業時刻に始業時刻を再設定(予定が入っているので実績を設定)
			regularStart = startTime;
		}
		// 全入力休憩時間群を勤怠(日々)エンティティから取得
		Map<Integer, TimeDuration> inputRests = attendance.getRestTimes(timeSetting);
		// 遅刻早退休憩時間群を取得
		Map<Integer, TimeDuration> lateRests = getLateAndLeaveEarlyRestTimes(startTime, endTime, workType, request);
		// 全入力休憩時間群と遅刻早退休憩時間群を統合
		Map<Integer, TimeDuration> rests = TimeUtility.mergeDurations(inputRests, lateRests);
		// 到達時刻(0:00からの分)を取得
		return TimeUtility.getReachTime(regularStart, overtimeBeforeRestStart, rests);
	}
	
	/**
	 * 法定内残業可能時間(分)を取得する。<br>
	 * <br>
	 * 法定外残業が始まるまでの勤務時間(分)から、所定労働時間(分)を引く。<br>
	 * 上記分数に、次の分数を足して求める。<br>
	 * ・時間単位等休暇時間<br>
	 * ・遅刻時間<br>
	 * ・早退時間<br>
	 * ・私用外出時間<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 残業開始時刻(0:00からの分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int getOvertimeInPossible(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 法定外残業が始まるまでの勤務時間(分)を取得
		int workTimeToOvertimeOut = getWorkTimeToOvertimeOut();
		// 所定労働時間(分)を計算
		int prescribedWorkTime = calcPrescribedWorkTime(workType, request);
		// 法定内残業可能時間(分)を準備
		int overtimeInPossible = workTimeToOvertimeOut - prescribedWorkTime;
		// 各時間単位等休暇時間を加算
		overtimeInPossible += TimeUtility.getMinutes(getHolidayTimes(request));
		// 遅刻時間及び早退時間を加算
		overtimeInPossible += calcActualLateTime(startTime, endTime, attendance, workType, request, timeSetting);
		overtimeInPossible += calcActualLeaveEarlyTime(startTime, endTime, attendance, workType, request, timeSetting);
		// 私用外出時間を加算
		overtimeInPossible += calcPrivateTime(attendance, timeSetting);
		// 追加法定内残業可能時間(分)(汎用パラメータ)を加算
		overtimeInPossible += MospUtility.getIntValue(params, KEY_ADD_OVERTIME_IN_POSSIBLE);
		// 法定内残業可能時間(分)を取得(0以上)
		return MospUtility.getIntOrZero(overtimeInPossible);
	}
	
	/**
	 * 残前休憩時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * 規定始業時刻から残前休憩開始勤務時間(分)を超えて勤務する場合、
	 * 勤務形態で設定された残前休憩が発生する。<br>
	 * <br>
	 * ここでは、次の時間は勤務時間に含めない。<br>
	 * ・入力休憩時間<br>
	 * ・遅刻早退休憩時間<br>
	 * <br>
	 * 但し、残前休憩時間が時間単位等休暇と重複する場合、
	 * 残前休憩時間の重複分を後ろへずらす。<br>
	 * そのため、残前休憩時間は複数に分断されることがある。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 残前休憩時間(0:00からの分)群
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getOvertimeBeforeRest(int startTime, int endTime,
			AttendanceEntityInterface attendance, WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting) throws MospException {
		// 残前休憩時間群を準備
		Map<Integer, TimeDuration> overtimeBeforeRests = new TreeMap<Integer, TimeDuration>();
		// 残前休憩時間(勤務形態)を取得
		int overtimeBeforeRest = workType.getOvertimeBeforeRest();
		// 法定労働時間を取得
		int legal = getLegalWorkTime();
		// 残前休憩開始勤務時間(分)を取得
		int beforeOvertimeBeforeRest = workType.getWorkTimeBeforeOvertimeBeforeRest(request, legal, IS_COMPLETED);
		// 規定始業時刻を取得
		int regularStart = getRegularTime(workType, request).getStartTime();
		// 全入力休憩時間群を勤怠(日々)エンティティから取得
		Map<Integer, TimeDuration> inputRests = attendance.getRestTimes(timeSetting);
		// 遅刻早退休憩時間群を取得
		Map<Integer, TimeDuration> lateRests = getLateAndLeaveEarlyRestTimes(startTime, endTime, workType, request);
		// 全入力休憩時間群と遅刻早退休憩時間群を統合
		Map<Integer, TimeDuration> rests = TimeUtility.mergeDurations(inputRests, lateRests);
		// 前残休憩開始時刻(到達時刻)(0:00からの分)を取得
		int overtimeBeforeRestStart = TimeUtility.getReachTime(regularStart, beforeOvertimeBeforeRest, rests);
		// 残前休憩終了時刻(残業開始時刻+前残休憩時間)を取得
		int overtimeBeforeRestEnd = overtimeBeforeRestStart + overtimeBeforeRest;
		// 終業時刻が残前休憩開始時刻より前であるか残前休憩終了時刻が始業時刻より前である場合
		if (endTime < overtimeBeforeRestStart || overtimeBeforeRestEnd < startTime) {
			// 空の残前休憩時間群
			return overtimeBeforeRests;
		}
		// 前残休憩時間開始時刻及び終了時刻を始業及び終業時刻で調整
		int restStartTime = overtimeBeforeRestStart < startTime ? startTime : overtimeBeforeRestStart;
		int restEndTime = overtimeBeforeRestEnd < endTime ? overtimeBeforeRestEnd : endTime;
		// 前残休憩時間(0:00からの分)を取得
		TimeDuration duration = TimeDuration.getInstance(restStartTime, restEndTime);
		// 時間単位等休暇時間群を取得
		Map<Integer, TimeDuration> holidayTimes = getHolidayTimes(request);
		// 重複してる箇所を後へずらした時間間隔群を取得
		return duration.getPostponed(holidayTimes);
	}
	
	/**
	 * 残業休憩時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * 規定終業時刻(残前休憩がある場合は残前休憩終了時刻)から終業時刻(勤怠計算上)まで
	 * の時間を残業休憩時間(毎)で割った回数だけ、残業休憩を取得する。<br>
	 * <br>
	 * 残業休憩カウント時間内に取得した休憩(入力休憩含む)は、
	 * 残業休憩の計算には影響しない。<br>
	 * (残業時間に休憩を取っても、終業時間が同じなら残業休憩は同じ。)<br>
	 * <br>
	 * @param startTime          始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime            終業時刻(勤怠計算上)(0:00からの分)
	 * @param overtimeBeforeRest 前残休憩時間(0:00からの分)
	 * @param workType           勤務形態エンティティ
	 * @param request            申請エンティティ
	 * @return 残業前休憩時間(0:00からの分)群
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getOvertimeRest(int startTime, int endTime,
			Map<Integer, TimeDuration> overtimeBeforeRest, WorkTypeEntityInterface workType,
			RequestEntityInterface request) throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 空の遅刻時間群を取得
			return Collections.emptyMap();
		}
		// 残業休憩時間(0:00からの分)群を準備
		Map<Integer, TimeDuration> overtimeRests = new TreeMap<Integer, TimeDuration>();
		// 残業休憩時間が有効でない場合
		if (workType.isOvertimeRestValid() == false) {
			// 空の残業前休憩時間を取得
			return Collections.emptyMap();
		}
		// 残業休憩時間(分)を取得
		int overtimeRest = workType.getOvertimeRest();
		// 残業休憩時間(毎)(分)を取得
		int overtimeRestPer = workType.getOvertimeRestPer();
		// 残業休憩カウント開始時刻(0:00からの分)を準備(規定終業時刻)
		int countStart = getRegularTime(workType, request).getEndTime();
		// 残前休憩時間がある場合
		if (overtimeBeforeRest.isEmpty() == false) {
			// 残業休憩カウント開始時刻(0:00からの分)を再設定(残前休憩終了時刻)
			countStart = TimeUtility.getDuration(overtimeBeforeRest).getEndTime();
		}
		// 残業休憩カウント開始時刻より始業時刻(勤怠計算上)が後である場合
		if (countStart < startTime) {
			// 残業休憩カウント開始時刻を再設定(始業時刻(勤怠計算上))
			countStart = startTime;
		}
		// 残業休憩回数を計算
		int overtimeRestCount = (endTime - countStart) / overtimeRestPer;
		// 残業休憩毎に処理
		for (int i = 0; i < overtimeRestCount; i++) {
			// 残業休憩開始時刻を計算
			int overtimeRestStart = countStart + overtimeRestPer * (i + 1) - overtimeRest;
			// 残業休憩時間を取得
			TimeDuration duration = TimeDuration.getInstance(overtimeRestStart, overtimeRestStart + overtimeRest);
			// 残業休憩時間を取得し残業休憩時間(0:00からの分)群に追加
			overtimeRests.put(overtimeRestStart, duration);
		}
		// 残業休憩時間(0:00からの分)群を取得
		return TimeUtility.combineDurations(overtimeRests);
	}
	
	/**
	 * 規定休憩時間及び時間単位休暇時間群(キー：開始時刻(キー順))を作成する。<br>
	 * <br>
	 * 実遅刻時間(分)及び実早退時間(分)を計算するために、次の時間を纏める。<br>
	 * ・規定休憩<br>
	 * ・時間単位等休暇<br>
	 * <br>
	 * @param startTime 始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime   終業時刻(勤怠計算上)(0:00からの分)
	 * @param workType  勤務形態エンティティ
	 * @param request   申請エンティティ
	 * @return 休憩休暇時間群(キー：開始時刻、値：終了時刻)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getRestAndHourlyHolidayTimes(int startTime, int endTime,
			WorkTypeEntityInterface workType, RequestEntityInterface request) throws MospException {
		// 規定休憩時間群を取得
		Map<Integer, TimeDuration> restTimes = workType.getRestTimes(startTime, endTime, request, IS_COMPLETED);
		// 時間単位等休暇群を取得
		Map<Integer, TimeDuration> holidayTimes = getHolidayTimes(request);
		// 規定休憩時間間隔群を取得
		return TimeUtility.mergeDurations(restTimes, holidayTimes);
	}
	
	/**
	 * 遅刻早退休憩時間群(キー：開始時刻(キー順))を取得する。<br>
	 * <br>
	 * 遅刻及び早退により取得できなかった規定休憩時間(勤務形態の休憩時間)を取得する。<br>
	 * <br>
	 * 但し、時間単位等休暇と重複する時間は、遅刻早退休憩時間に含めない。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @return 遅刻早退休憩時間群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getLateAndLeaveEarlyRestTimes(int startTime, int endTime,
			WorkTypeEntityInterface workType, RequestEntityInterface request) throws MospException {
		// 始業時刻及び終業時刻(勤怠計算上)(0:00からの分)の時間間隔を取得
		TimeDuration startEndTime = TimeDuration.getInstance(startTime, endTime);
		// 規定休憩時間群を取得
		Map<Integer, TimeDuration> restTimes = workType.getRestTimes(startTime, endTime, request, IS_COMPLETED);
		// 時間単位等休暇時間群を取得
		Map<Integer, TimeDuration> holidayTimes = getHolidayTimes(request);
		// 遅刻及び早退により取得できなかった規定休憩時間(勤務形態の休憩時間)を取得
		restTimes = TimeUtility.getNotOverlap(restTimes, startEndTime);
		// 時間単位等休暇と重複する時間を除去
		restTimes = TimeUtility.getNotOverlap(restTimes, holidayTimes);
		// 遅刻早退休憩時間群(キー：開始時刻(キー順))を取得
		return restTimes;
	}
	
	/**
	 * 遅刻時間群を取得する。<br>
	 * <br>
	 * 始業時刻(勤怠計算上)(0:00からの分)を元に計算する。<br>
	 * <br>
	 * 計算方法は、次の通り。<br>
	 * <br>
	 * 1.休日出勤である場合：0<br>
	 * 2.始業時刻(勤怠計算上)が規定始業時刻(時短時間含む)以前である場合：0<br>
	 * 3.それ以外の場合：
	 * 　規定時間(時短時間含む)開始時刻から始業時刻(勤怠計算上)までの時間
	 * 　(規定休憩時間及び時間単位休暇時間を除く)<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 遅刻時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getLateTimes(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 空の遅刻時間群を取得
			return Collections.emptyMap();
		}
		// 規定時間(時短時間含む)を取得
		TimeDuration regularTime = getRegularAndShortTime(workType, request);
		// 1.休日出勤である場合
		if (workType.isWorkOnHoliday()) {
			// 空の遅刻時間群を取得
			return Collections.emptyMap();
		}
		// 2.始業時刻(勤怠計算上)が規定始業時刻以前である場合
		if (startTime <= regularTime.getStartTime()) {
			// 空の遅刻時間群を取得
			return Collections.emptyMap();
		}
		// 3.それ以外の場合
		// 遅刻カウント終了時刻(規定終業時刻)を準備(遅刻は最大でも規定終業時刻まで)
		int lateEndTime = regularTime.getEndTime();
		// 始業時刻(勤怠計算上)が遅刻カウント終了時刻より前である場合
		if (startTime < lateEndTime) {
			// 遅刻カウント終了時刻を始業時刻(勤怠計算上)に設定
			lateEndTime = startTime;
		}
		// 遅刻時間間隔(規定始業時刻から遅刻カウント終了時刻まで)を準備
		TimeDuration lateTime = TimeDuration.getInstance(regularTime.getStartTime(), lateEndTime);
		// 規定休憩時間及び時間単位休暇時間群(キー：開始時刻(キー順))を作成
		Map<Integer, TimeDuration> restHolidays = getRestAndHourlyHolidayTimes(startTime, endTime, workType, request);
		// 規定休憩時間及び時間単位休暇時間と重複する時間(分)を除去
		Map<Integer, TimeDuration> lateTimes = lateTime.getNotOverlap(restHolidays);
		// 遅刻時間群を取得
		return lateTimes;
	}
	
	/**
	 * 勤務時間群を取得する。<br>
	 * <br>
	 * 始業時刻及び終業時刻の差から、次の時間を引く。<br>
	 * 但し、始業時刻～終業時刻に重複しない時間は引かない。<br>
	 * ・時間単位等休暇時間<br>
	 * ・休憩時間<br>
	 * ・外出時間<br>
	 * <br>
	 * 時短時間(有給)がある場合は、その時間を加算する。<br>
	 * 但し、始業時刻と終業時刻に重複する時間は加算しない。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 勤務時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getWorkTimes(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 空の勤務時間群を取得
			return Collections.emptyMap();
		}
		// 始業終業時間を取得
		TimeDuration workTime = TimeDuration.getInstance(startTime, endTime);
		// 時短時間(有給)を取得
		TimeDuration short1Pay = getShort1PayTime(workType, request);
		TimeDuration short2Pay = getShort2PayTime(workType, request);
		// 時間単位等休暇時間群を取得
		Map<Integer, TimeDuration> holidayTimes = getHolidayTimes(request);
		// 全休憩時間群を取得
		Map<Integer, TimeDuration> rests = getAllRests(startTime, endTime, attendance, workType, request, timeSetting);
		// 全外出時間群を取得
		Map<Integer, TimeDuration> goOuts = attendance.getAllGoOutTimes(timeSetting);
		// 時間単位等休暇と全休憩時間と全外出時間の時間間隔を統合
		Map<Integer, TimeDuration> durations = TimeUtility.mergeDurations(holidayTimes, rests, goOuts);
		// 休憩等と重複していない勤務時間群を取得
		Map<Integer, TimeDuration> workTimes = workTime.getNotOverlap(durations);
		// 時短時間1(有給)を統合
		workTimes = TimeUtility.mergeDurations(workTimes, short1Pay);
		// 時短時間2(有給)を統合
		workTimes = TimeUtility.mergeDurations(workTimes, short2Pay);
		// 追加勤務時間(時間間隔)群)を統合
		workTimes = TimeUtility.mergeDurations(workTimes, TimeUtility.getDurations(params, KEY_ADD_WORK_TIMES));
		// 勤務時間(0:00からの分)群を取得
		return workTimes;
	}
	
	/**
	 * 所定労働時間内労働時間群を取得する。<br>
	 * <br>
	 * 勤務時間 - 残業時間 - 法定休日勤務時間。<br>
	 * <br>
	 * {@link AttendCalcEntity#getWorkTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface) }
	 * と
	 * {@link AttendCalcEntity#getOvertimeTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * と<br>
	 * {@link AttendCalcEntity#getWorkOnLegalTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 所定労働時間内労働時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getWorkTimeWithinPrescribed(int startTime, int endTime,
			AttendanceEntityInterface attendance, WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting, String nextWorkType) throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 空の勤務時間群を取得
			return Collections.emptyMap();
		}
		// 勤務時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> workTimes = getWorkTimes(startTime, endTime, attendance, workType, request,
				timeSetting);
		// 残業時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> overtimeTimes = getOvertimeTimes(startTime, endTime, attendance, workType, request,
				timeSetting, nextWorkType);
		// 法定休日勤務時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> workOnLegalTimes = getWorkOnLegalTimes(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 勤務時間群から残業時間群を除去
		Map<Integer, TimeDuration> times = TimeUtility.getNotOverlap(workTimes, overtimeTimes);
		// 所定労働時間内労働時間群を取得(更に法定休日勤務時間群を除去)
		return TimeUtility.getNotOverlap(times, workOnLegalTimes);
	}
	
	/**
	 * 後残業用勤務時間群を取得する。<br>
	 * <br>
	 * 1.始業時刻及び終業時刻の差から、次の時間を引く。<br>
	 * 但し、始業時刻～終業時刻に重複しない時間は引かない。<br>
	 * ・休憩時間<br>
	 * ・前残業時間<br>
	 * <br>
	 * 2.上記時間に、次の時間を統合する。<br>
	 * ・時間単位等休暇時間<br>
	 * ・遅刻時間<br>
	 * ・時短時間(有給)<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 後残業用勤務時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getWorkTimesForOvertimeAfter(int startTime, int endTime,
			AttendanceEntityInterface attendance, WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting) throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 空の勤務時間群を取得
			return Collections.emptyMap();
		}
		// 始業終業時間を取得
		TimeDuration workTime = TimeDuration.getInstance(startTime, endTime);
		// 全休憩時間群を取得
		Map<Integer, TimeDuration> rests = getAllRests(startTime, endTime, attendance, workType, request, timeSetting);
		// 前残業時間を取得
		Map<Integer, TimeDuration> overtimeBefores = getOvertimeBeforeTimes(startTime, endTime, attendance, workType,
				request, timeSetting);
		// 時間単位等休暇時間群を取得
		Map<Integer, TimeDuration> holidayTimes = getHolidayTimes(request);
		// 遅刻時間を取得
		Map<Integer, TimeDuration> lates = getLateTimes(startTime, endTime, attendance, workType, request, timeSetting);
		// 時短時間(有給)を取得
		TimeDuration short1Pay = getShort1PayTime(workType, request);
		TimeDuration short2Pay = getShort2PayTime(workType, request);
		// 全休憩時間群と前残業時間を統合
		Map<Integer, TimeDuration> subtracts = TimeUtility.mergeDurations(rests, overtimeBefores);
		// 時間単位等休暇時間群と遅刻時間と時短時間(有給)を統合
		Map<Integer, TimeDuration> adds = TimeUtility.mergeDurations(holidayTimes, lates);
		adds = TimeUtility.mergeDurations(adds, short1Pay);
		adds = TimeUtility.mergeDurations(adds, short2Pay);
		// 1.始業終業時間から全休憩時間群と前残業時間を除去
		Map<Integer, TimeDuration> workTimes = workTime.getNotOverlap(subtracts);
		// 2.その時間に時間単位休暇時間群と遅刻時間と時短時間(有給)を統合
		return TimeUtility.mergeDurations(workTimes, adds);
	}
	
	/**
	 * 勤務時間(後残業より前)に含まれる無給時短時間(分)を取得する。<br>
	 * <br>
	 * 時短時間1(無給)は勤務時間に含まれないため、時短時間2(無給)のみを考慮する。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 勤務時間(後残業より前)に含まれる無給時短時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int getContainedShortUnpay(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 時短時間2(無給)を取得
		TimeDuration short2Unpay = workType.getShort2UnpayTime(request, IS_COMPLETED);
		// 時短時間2(無給)が妥当でない場合
		if (short2Unpay.isValid() == false) {
			// 0を取得
			return 0;
		}
		// 後残業用勤務時間群を取得
		Map<Integer, TimeDuration> workTimes = getWorkTimesForOvertimeAfter(startTime, endTime, attendance, workType,
				request, timeSetting);
		// 残業開始時刻を取得
		int overtimeStartTime = getOvertimeStartTime(startTime, endTime, attendance, workType, request, timeSetting);
		// 後残業用勤務時間群の残業開始時刻より前の時間を取得
		workTimes = TimeUtility.getBeforeTimes(workTimes, overtimeStartTime);
		// 勤務時間(後残業より前)に含まれる無給時短時間を取得
		int contained = short2Unpay.getOverlapMinutes(workTimes);
		// 時短時間2(無給)が全て含まれる場合
		if (short2Unpay.getMinutes() == contained) {
			// 勤務時間(後残業より前)に含まれる無給時短時間を取得
			return contained;
		}
		// 時短時間2(無給)が全て含まれない場合(時短時間2(無給)中に休憩を取った場合等)
		// 時短時間2(無給)後残業開始前の勤務時間を取得
		Map<Integer, TimeDuration> unpay2ToOvertime = TimeUtility.getAfterTimes(workTimes, short2Unpay.getEndTime());
		// 時短時間2(無給)後残業開始前の勤務時間の時間を加算
		contained += TimeUtility.getMinutes(unpay2ToOvertime);
		// 勤務時間(後残業より前)に含まれる無給時短時間(分)を取得(最大で時短時間2(無給))
		return contained < short2Unpay.getMinutes() ? contained : short2Unpay.getMinutes();
	}
	
	/**
	 * 消化無給時短時間(分)を取得する。<br>
	 * 後残業で消化される無給時短時間を計算する。<br>
	 * <br>
	 * 前残業からは無給時短時間は消化しない。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 消化無給時短時間(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected int getConsumedShortUnpay(int startTime, int endTime, AttendanceEntityInterface attendance,
			WorkTypeEntityInterface workType, RequestEntityInterface request, TimeSettingEntityInterface timeSetting)
			throws MospException {
		// 時短時間(無給)(分)を取得
		int unpay = TimeUtility.getMinutes(workType.getShortUnpayTimes());
		// 勤務時間(後残業より前)に含まれる無給時短時間(分)を取得
		int containd = getContainedShortUnpay(startTime, endTime, attendance, workType, request, timeSetting);
		// 未消化の時短時間(無給)(分)を取得
		int remain = unpay - containd;
		// 後残業用勤務時間群を取得
		Map<Integer, TimeDuration> workTimes = getWorkTimesForOvertimeAfter(startTime, endTime, attendance, workType,
				request, timeSetting);
		// 残業開始時刻を取得
		int overtimeStartTime = getOvertimeStartTime(startTime, endTime, attendance, workType, request, timeSetting);
		// 後残業用勤務時間群の残業開始時刻より後の時間を取得
		workTimes = TimeUtility.getAfterTimes(workTimes, overtimeStartTime);
		// 後残業時間を取得
		int overtime = TimeUtility.getMinutes(workTimes);
		// 未消化の時短時間(無給)(分)から後残業分を消化
		int notConsumed = remain - overtime;
		// 全て消化した場合は未消化の時短時間に0を設定
		notConsumed = notConsumed < 0 ? 0 : notConsumed;
		// 消化無給時短時間(分)を取得
		return remain - notConsumed;
	}
	
	/**
	 * 残業時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * 前残業時間群と後残業時間群を統合する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getOvertimeBeforeTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface) }
	 * 及び
	 * {@link AttendCalcEntity#getOvertimeAfterTimes(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface, String) }
	 * を参照。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 残業時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getOvertimeTimes(int startTime, int endTime,
			AttendanceEntityInterface attendance, WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting, String nextWorkType) throws MospException {
		// 前残業時間群を取得
		Map<Integer, TimeDuration> befores = getOvertimeBeforeTimes(startTime, endTime, attendance, workType, request,
				timeSetting);
		// 後残業時間群を取得
		Map<Integer, TimeDuration> afters = getOvertimeAfterTimes(startTime, endTime, attendance, workType, request,
				timeSetting, nextWorkType);
		// 前残業時間群と後残業時間群を統合
		return TimeUtility.mergeDurations(befores, afters);
	}
	
	/**
	 * 前残業時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * 始業時刻(勤怠計算上)～規定始業時刻から次の時間の重複する部分を引き、
	 * 前残業時間(分)とする。<br>
	 * ・入力休憩時間<br>
	 * <br>
	 * 但し、休日出勤である場合は0とする。<br>
	 * <br>
	 * @param startTime   始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime     終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance  勤怠(日々)エンティティ
	 * @param workType    勤務形態エンティティ
	 * @param request     申請エンティティ
	 * @param timeSetting 勤怠設定エンティティ
	 * @return 前残業時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getOvertimeBeforeTimes(int startTime, int endTime,
			AttendanceEntityInterface attendance, WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting) throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 空の前残業時間群を取得
			return Collections.emptyMap();
		}
		// 休日出勤である場合
		if (workType.isWorkOnHoliday()) {
			// 空の前残業時間群を取得
			return Collections.emptyMap();
		}
		// 前残業終了時刻を準備(規定始業時刻)
		int overtimeBeforeEnd = getRegularTime(workType, request).getStartTime();
		// 前残業終了時刻を終業時刻で調整
		overtimeBeforeEnd = overtimeBeforeEnd < endTime ? overtimeBeforeEnd : endTime;
		// 前残業時間を取得
		TimeDuration overtimeBefore = TimeDuration.getInstance(startTime, overtimeBeforeEnd);
		// 入力休憩時間を取得
		Map<Integer, TimeDuration> rests = attendance.getRestTimes(timeSetting);
		// 休憩と重複していない前残業時間群を取得
		return overtimeBefore.getNotOverlap(rests);
	}
	
	/**
	 * 後残業時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getWorkTimesForOvertimeAfter(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface) }、
	 * {@link AttendCalcEntity#getOvertimeStartTime(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface) }、
	 * {@link AttendCalcEntity#getConsumedShortUnpay(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface) }
	 * を参照。<br>
	 * <br>
	 * 後残業用勤務時間群の残業開始時刻以降の時間をから、
	 * 消化無給時短時間分を除去したものを取得する。<br>
	 * <br>
	 * 但し、法定休日(法定休日出勤含む)時間にかかる時間は除く。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 後残業時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getOvertimeAfterTimes(int startTime, int endTime,
			AttendanceEntityInterface attendance, WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting, String nextWorkType) throws MospException {
		// 始業終業時刻(勤怠計算上)が妥当でない場合(下書時等)
		if (isTimeValid(startTime, endTime) == false) {
			// 空の前残業時間群を取得
			return Collections.emptyMap();
		}
		// 後残業用勤務時間群を取得
		Map<Integer, TimeDuration> workTimes = getWorkTimesForOvertimeAfter(startTime, endTime, attendance, workType,
				request, timeSetting);
		// 残業開始時刻を取得
		int overtimeStartTime = getOvertimeStartTime(startTime, endTime, attendance, workType, request, timeSetting);
		// 後残業用勤務時間群の残業開始時刻以降の時間を取得
		workTimes = TimeUtility.getAfterTimes(workTimes, overtimeStartTime);
		// 消化無給時短時間を取得
		int consumedShort = getConsumedShortUnpay(startTime, endTime, attendance, workType, request, timeSetting);
		// 後残業勤務時間群(後残業用勤務時間群から消化無給時短時間分を除去)を取得
		Map<Integer, TimeDuration> overtimeAfterTimes = TimeUtility.removeTime(workTimes, consumedShort);
		// 後残業勤務時間群のうち法定休日時間と重複しない部分を取得
		return TimeUtility.getNotOverlap(overtimeAfterTimes, getLegalTime(workType, timeSetting, nextWorkType));
	}
	
	/**
	 * 法定内残業時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * {@link AttendCalcEntity#getOvertimeInPossible(int, int, AttendanceEntityInterface, WorkTypeEntityInterface, RequestEntityInterface, TimeSettingEntityInterface) }
	 * を参照。<br>
	 * <br>
	 * 残業時間群から法定内残業可能時間(分)までの時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 後残業時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getOvertimeInTimes(int startTime, int endTime,
			AttendanceEntityInterface attendance, WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting, String nextWorkType) throws MospException {
		// 残業時間群を取得
		Map<Integer, TimeDuration> overtimeTimes = getOvertimeTimes(startTime, endTime, attendance, workType, request,
				timeSetting, nextWorkType);
		// 法定内残業可能時間(分)を取得
		int possible = getOvertimeInPossible(startTime, endTime, attendance, workType, request, timeSetting);
		// 残業時間群から法定内残業可能時間(分)までの時間を取得
		return TimeUtility.getReachTimes(overtimeTimes, possible);
	}
	
	/**
	 * 法定外残業時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * 残業時間群から法定内残業時間群を除いた時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 後残業時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getOvertimeOutTimes(int startTime, int endTime,
			AttendanceEntityInterface attendance, WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting, String nextWorkType) throws MospException {
		// 残業時間群を取得
		Map<Integer, TimeDuration> overtimeTimes = getOvertimeTimes(startTime, endTime, attendance, workType, request,
				timeSetting, nextWorkType);
		// 法定内残業時間群を取得
		Map<Integer, TimeDuration> overtimeInTimes = getOvertimeInTimes(startTime, endTime, attendance, workType,
				request, timeSetting, nextWorkType);
		// 残業時間群から法定内残業時間群を除いた時間を取得
		return TimeUtility.getNotOverlap(overtimeTimes, overtimeInTimes);
	}
	
	/**
	 * 所定休日勤務時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * 勤務時間と所定休日時間が重複する時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 後残業時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getWorkOnPrescribedTimes(int startTime, int endTime,
			AttendanceEntityInterface attendance, WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting, String nextWorkType) throws MospException {
		// 勤務時間群を取得を取得
		Map<Integer, TimeDuration> times = getWorkTimes(startTime, endTime, attendance, workType, request, timeSetting);
		// 所定休日時間を取得
		TimeDuration prescribedTime = getPrescribedTime(workType, timeSetting, nextWorkType);
		// 重複する時間群を取得
		return prescribedTime.getOverlap(times);
	}
	
	/**
	 * 法定休日勤務時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * 勤務時間と法定休日時間が重複する時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 法定休日勤務時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getWorkOnLegalTimes(int startTime, int endTime,
			AttendanceEntityInterface attendance, WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting, String nextWorkType) throws MospException {
		// 勤務時間群を取得を取得
		Map<Integer, TimeDuration> times = getWorkTimes(startTime, endTime, attendance, workType, request, timeSetting);
		// 法定休日時間を取得
		TimeDuration legalTime = getLegalTime(workType, timeSetting, nextWorkType);
		// 重複する時間群を取得
		return legalTime.getOverlap(times);
	}
	
	/**
	 * 深夜勤務時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * 勤務時間と深夜時間が重複する時間を取得する。<br>
	 * <br>
	 * @param startTime    始業時刻(勤怠計算上)(0:00からの分)
	 * @param endTime      終業時刻(勤怠計算上)(0:00からの分)
	 * @param attendance   勤怠(日々)エンティティ
	 * @param workType     勤務形態エンティティ
	 * @param request      申請エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @return 深夜勤務時間(0:00からの分)群(キー：開始時刻(キー順))
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Map<Integer, TimeDuration> getNightWorkTimes(int startTime, int endTime,
			AttendanceEntityInterface attendance, WorkTypeEntityInterface workType, RequestEntityInterface request,
			TimeSettingEntityInterface timeSetting) throws MospException {
		// 勤務時間(0:00からの分)群を取得
		Map<Integer, TimeDuration> times = getWorkTimes(startTime, endTime, attendance, workType, request, timeSetting);
		// 深夜時間を取得
		Map<Integer, TimeDuration> nightTimes = getNightTimes();
		// 深夜勤務時間群を取得(勤務時間と深夜時間が重複する時間)
		return TimeUtility.getOverlap(times, nightTimes);
	}
	
	/**
	 * 平日時間を取得する。<br>
	 * <br>
	 * @param workType     勤務形態エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 平日時間
	 */
	protected TimeDuration getWorkdayTime(WorkTypeEntityInterface workType, TimeSettingEntityInterface timeSetting,
			String nextWorkType) {
		// 所定休日出勤で所定休日取扱が暦日でない場合
		if (workType.isWorkOnPrescribed() && timeSetting.isPrescribedHolidayBasedOnCalendar() == false) {
			// 平日時間は無し
			return TimeDuration.getInvalid();
		}
		// 休日出勤で翌日も休日である場合
		if (workType.isWorkOnHoliday() && TimeUtility.isLegalOrPrescribed(nextWorkType)) {
			// 平日時間は無し
			return TimeDuration.getInvalid();
		}
		// 開始時刻及び終了時刻を準備
		int startTime = 0;
		int endTime = TimeUtility.getNextDayEnd();
		// 休日出勤である場合
		if (workType.isWorkOnHoliday()) {
			// 開始時刻を翌日開始時刻に設定
			startTime = TimeUtility.getNextDayStart();
		}
		// 翌日が法定休日(法定休日出勤含む)である場合
		if (TimeUtility.isLegal(nextWorkType)) {
			// 終了時刻を翌日開始時刻に設定
			endTime = TimeUtility.getNextDayStart();
		}
		// 翌日が所定休日(所定休日出勤含む)で所定休日取扱が暦日である場合
		if (TimeUtility.isPrescribed(nextWorkType) && timeSetting.isPrescribedHolidayBasedOnCalendar()) {
			// 終了時刻を翌日開始時刻に設定
			endTime = TimeUtility.getNextDayStart();
		}
		// 平日時間を取得
		return TimeDuration.getInstance(startTime, endTime);
	}
	
	/**
	 * 所定休日(所定休日出勤含む)時間を取得する。<br>
	 * <br>
	 * @param workType     勤務形態エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 所定休日時間
	 */
	protected TimeDuration getPrescribedTime(WorkTypeEntityInterface workType, TimeSettingEntityInterface timeSetting,
			String nextWorkType) {
		// 当日も翌日も所定休日(所定休日出勤含む)でない場合
		if (workType.isPrescribed() == false && TimeUtility.isPrescribed(nextWorkType) == false) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 当日が平日で所定休日取扱が暦日でない場合
		if (workType.isWorkDay() && timeSetting.isPrescribedHolidayBasedOnCalendar() == false) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 開始時刻及び終了時刻を準備
		int startTime = 0;
		int endTime = TimeUtility.getNextDayEnd();
		// 所定休日(所定休日出勤含む)であるかどうかを確認
		if (workType.isPrescribed()) {
			// 終了時刻を翌日開始時刻に設定(所定休日である場合)
			endTime = TimeUtility.getNextDayStart();
		} else {
			// 開始時刻を翌日開始時刻に設定(所定休日でない場合)
			startTime = TimeUtility.getNextDayStart();
		}
		// 翌日が所定休日(所定休日出勤含む)である場合
		if (TimeUtility.isPrescribed(nextWorkType)) {
			// 終了時刻を翌日開始時刻に設定
			endTime = TimeUtility.getNextDayEnd();
		}
		// 当日が所定休日(所定休日出勤含む)で所定休日取扱が暦日でない場合
		if (workType.isPrescribed() && timeSetting.isPrescribedHolidayBasedOnCalendar() == false) {
			// 終了時刻を翌日開始時刻に設定
			endTime = TimeUtility.getNextDayEnd();
		}
		// 翌日が法定休日(所定休日出勤含む)である場合
		if (TimeUtility.isLegal(nextWorkType)) {
			// 終了時刻を翌日開始時刻に設定
			endTime = TimeUtility.getNextDayStart();
		}
		// 所定休日(所定休日出勤含む)時間を取得
		return TimeDuration.getInstance(startTime, endTime);
	}
	
	/**
	 * 法定休日(法定休日出勤含む)時間を取得する。<br>
	 * <br>
	 * @param workType     勤務形態エンティティ
	 * @param timeSetting  勤怠設定エンティティ
	 * @param nextWorkType 勤務形態(翌日)コード
	 * @return 法定休日時間
	 */
	protected TimeDuration getLegalTime(WorkTypeEntityInterface workType, TimeSettingEntityInterface timeSetting,
			String nextWorkType) {
		// 開始時刻及び終了時刻を準備
		int startTime = 0;
		int endTime = TimeUtility.getNextDayEnd();
		// 法定休日(法定休日出勤含む)でない場合
		if (workType.isLegal() == false) {
			// 開始時刻を翌日開始時刻に設定
			startTime = TimeUtility.getNextDayStart();
		}
		// 翌日が法定休日(法定休日出勤含む)でない場合
		if (TimeUtility.isLegal(nextWorkType) == false) {
			// 終了時刻を翌日開始時刻に設定
			endTime = TimeUtility.getNextDayStart();
		}
		// 法定休日(法定休日出勤含む)時間を取得
		return TimeDuration.getInstance(startTime, endTime);
	}
	
	/**
	 * 深夜時間(0:00からの分)群を取得する。<br>
	 * <br>
	 * 次の時間間隔を取得する。<br>
	 * ・00:00～05:00<br>
	 * ・22:00～29:00<br>
	 * ・46:00～48:00<br>
	 * <br>
	 * @return 深夜時間群(キー：開始時刻(キー順))
	 */
	protected Map<Integer, TimeDuration> getNightTimes() {
		// 深夜時間(0:00からの分)群を準備
		Map<Integer, TimeDuration> nightTimes = new TreeMap<Integer, TimeDuration>();
		// 3つの時間間隔を取得
		for (int i = 0; i < 3; i++) {
			// 深夜時間間隔を取得
			TimeDuration duration = TimeDuration.getInstance(TimeUtility.getNightStart(i), TimeUtility.getNightEnd(i));
			// 深夜時間群に追加
			nightTimes.put(duration.getStartTime(), duration);
		}
		// 深夜時間(0:00からの分)群を取得
		return nightTimes;
	}
	
	/**
	 * 法定労働時間(分)を取得する。<br>
	 * <br>
	 * @return 法定労働時間(分)
	 */
	protected int getLegalWorkTime() {
		// 法定労働時間(分)を取得
		int legalWorkTime = TimeUtility.getLegalWorkTime(mospParams);
		// 汎用パラメータ群から法定労働時間(分)を取得
		int prior = MospUtility.getIntValue(params, KEY_LEGAL_WORK_TIME);
		// 法定労働時間(分)を取得(汎用パラメータ群に設定されている方を優先)
		return prior == 0 ? legalWorkTime : prior;
	}
	
	/**
	 * 法定外残業が始まるまでの勤務時間(分)を取得する。<br>
	 * <br>
	 * @return 法定外残業が始まるまでの勤務時間(分)
	 */
	protected int getWorkTimeToOvertimeOut() {
		// 法定労働時間(分)を取得
		return getLegalWorkTime();
	}
	
	/**
	 * 時刻(0:00からの分)が妥当であるかを確認する。<br>
	 * <br>
	 * いずれかの時刻が負の値(最小値)である場合に、妥当でないと判断する。<br>
	 * <br>
	 * @param times 時刻(0:00からの分)
	 * @return 確認結果(true：入力時刻が妥当である、false：そうでない)
	 */
	protected boolean isTimeValid(int... times) {
		// 時刻毎に処理
		for (int time : times) {
			// 時刻が負の値(最小値)である場合
			if (time == Integer.MIN_VALUE) {
				// 妥当でないと判断
				return false;
			}
		}
		// 時刻が妥当であると判断
		return true;
	}
	
	@Override
	public AttendanceEntityInterface getAttendance() {
		return attendance;
	}
	
	@Override
	public void setAttendance(AttendanceEntityInterface attendance) {
		this.attendance = attendance;
	}
	
	@Override
	public void setTimeSetting(TimeSettingEntityInterface timeSetting) {
		this.timeSetting = timeSetting;
	}
	
	@Override
	public void setWorkType(WorkTypeEntityInterface workType) {
		this.workType = workType;
	}
	
	@Override
	public WorkTypeEntityInterface getWorkType() {
		return workType;
	}
	
	@Override
	public void setRequest(RequestEntityInterface request) {
		this.request = request;
	}
	
	@Override
	public RequestEntityInterface getRequest() {
		return request;
	}
	
	@Override
	public void setNextWorkType(String nextWorkType) {
		this.nextWorkType = nextWorkType;
	}
	
	@Override
	public void setMospParams(MospParams mospParams) {
		this.mospParams = mospParams;
	}
	
	@Override
	public void putParam(String key, Object value) {
		// 汎用パラメータ群に値を設定
		params.put(key, value);
	}
	
	@Override
	public void addDurationsParam(String key, Map<Integer, TimeDuration> durations) {
		// 汎用パラメータ群に時間間隔情報群を追加
		TimeUtility.addDurations(params, key, durations);
	}
	
	@Override
	public void addIntParam(String key, int value) {
		// 汎用パラメータ群に値(Integer)を追加
		MospUtility.addValue(params, key, value);
	}
	
	@Override
	public Map<Integer, TimeDuration> getDurationsParam(String key) {
		// 汎用パラメータ群から時間間隔情報群を取得
		return TimeUtility.getDurations(params, key);
	}
	
}
