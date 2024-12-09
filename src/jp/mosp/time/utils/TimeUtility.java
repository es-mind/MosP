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
package jp.mosp.time.utils;

import java.math.BigDecimal;
import java.math.RoundingMode;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.AbstractMap.SimpleEntry;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.HashSet;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MenuUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.base.WorkflowNumberDtoInterface;
import jp.mosp.platform.dto.file.ExportDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.MonthUtility;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeVo;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.base.RequestDateDtoInterface;
import jp.mosp.time.dto.base.WorkTypeCodeDtoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.StockHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayRemainDto;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.entity.TimeDuration;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;

/**
 * 勤怠管理における有用なメソッドを提供する。<br><br>
 */
public class TimeUtility {
	
	/**
	 * 日付休暇設定における取得期間(無期限)の年。<br>
	 * Date型の最大年。<br>
	 */
	public static final int		DATE_UNLIMITED_YEAR				= 5874897;
	
	/**
	 * 日付休暇設定における取得期間(無期限)の日。<br>
	 */
	public static final int		DATE_YEAR_LAST_MONTH			= 31;
	
	/**
	 * 正規表現(時刻)。<br>
	 */
	public static final String	REG_ATTENDANCE_TIME				= "([0-3][0-9]|[4][0-7])[0-5][0-9]";
	
	/**
	 * メインメニューキー(勤怠報告)。<br>
	 */
	public static final String	MAIN_MENU_TIME_INMPUT			= "menuTimeInput";
	
	/**
	 * メニューキー(勤怠一覧)。<br>
	 */
	public static final String	MENU_ATTENDANCE_LIST			= "AttendanceList";
	
	/**
	 * メニューキー(残業申請)。<br>
	 */
	public static final String	MENU_OVERTIME_REQUEST			= "OvertimeRequest";
	
	/**
	 * メニューキー(休暇申請)。<br>
	 */
	public static final String	MENU_HOLIDAY_REQUEST			= "HolidayRequest";
	
	/**
	 * メニューキー(振出・休出申請)。<br>
	 */
	public static final String	MENU_WORK_ON_HOLIDAY_REQUEST	= "WorkOnHolidayRequest";
	
	/**
	 * メニューキー(代休申請)。<br>
	 */
	public static final String	MENU_SUB_HOLIDAY_REQUEST		= "SubHolidayRequest";
	
	/**
	 * メニューキー(勤務形態変更申請)。<br>
	 */
	public static final String	MENU_WORK_TYPE_CHANGE_REQUEST	= "WorkTypeChangeRequest";
	
	/**
	 * メニューキー(時差出勤申請)。<br>
	 */
	public static final String	MENU_DIFFERENCE_REQUEST			= "DifferenceRequest";
	
	/**
	 * メニューキー(承認解除申請)。<br>
	 */
	public static final String	MENU_CANCELLATION_REQUEST		= "CancellationRequest";
	
	/**
	 * 勤怠計算用の時間(分)の上限(47時間59分→2879分)。<br>
	 */
	public static final int		MAX_ATTENDANCE_MINUTES			= 2879;
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeUtility() {
		// 処理無し
	}
	
	/**
	 * 対象日における時刻を取得する。<br>
	 * 時刻は、デフォルト時刻(1970/01/01 00:00:00)を基準として取得する。<br>
	 * @param date 対象日
	 * @param time 時刻
	 * @return 対象日における時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDateTime(Date date, Date time) throws MospException {
		// 時刻を確認
		if (time == null) {
			return null;
		}
		// 時分を取得
		int hour = DateUtility.getHour(time, DateUtility.getDefaultTime());
		int minute = DateUtility.getMinute(time);
		// 対象日における時刻を取得
		return getDateTime(date, hour, minute);
	}
	
	/**
	 * デフォルト日付における時刻を取得する。<br>
	 * @param time         時刻
	 * @param standardDate 基準日(引数：timeの基準となる日)
	 * @return デフォルト日付における時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDefaultDateTime(Date time, Date standardDate) throws MospException {
		// 時刻を確認
		if (MospUtility.isEmpty(time, standardDate)) {
			// nullを取得
			return null;
		}
		// 時分を取得
		int hour = DateUtility.getHour(time, standardDate);
		int minute = DateUtility.getMinute(time);
		// 対象日における時刻を取得
		return getDateTime(DateUtility.getDefaultTime(), hour, minute);
	}
	
	/**
	 * デフォルト日時における時刻を取得する。<br>
	 * <br>
	 * @param hour   時間
	 * @param minute 分
	 * @return デフォルト日時の時刻
	 * @throws MospException MospUtility.getInt(minute)
	 */
	public static Date getDefaultTime(String hour, String minute) throws MospException {
		// デフォルト日時の時刻を取得
		return getDateTime(DateUtility.getDefaultTime(), hour, minute);
	}
	
	/**
	 * 対象日における時刻を取得する。<br>
	 * <br>
	 * @param date   対象日
	 * @param hour   時(24時間制)
	 * @param minute 分
	 * @return 対象日における時刻
	 */
	public static Date getDateTime(Date date, String hour, String minute) {
		// 対象日における時刻を取得
		return getDateTime(date, MospUtility.getInt(hour), MospUtility.getInt(minute));
	}
	
	/**
	 * 対象日における時刻を取得する。<br>
	 * <br>
	 * {@link DateUtility#getTime(String, String)}と異なり、
	 * 24時を超えた場合は翌日の時刻として返す。<br>
	 * <br>
	 * 例：<br>
	 * 対象日が2013/04/01で時刻が9:00だった場合、2013/04/01 09:00を返す。
	 * 対象日が2013/04/01で時刻が25:00だった場合、2013/04/02 01:00を返す。
	 * <br>
	 * @param date   対象日
	 * @param hour   時(24時間制)
	 * @param minute 分
	 * @return 対象日における時刻
	 */
	public static Date getDateTime(Date date, int hour, int minute) {
		// 対象日がnullである場合
		if (date == null) {
			// nullを取得
			return null;
		}
		// 対象日から時間以下の情報を除いた日付を取得
		Date dateTime = DateUtility.getDate(date);
		// 時間を加算
		dateTime = DateUtility.addHour(dateTime, hour);
		// 分を加算
		dateTime = DateUtility.addMinute(dateTime, minute);
		// 対象日における時刻を取得
		return dateTime;
	}
	
	/**
	 * デフォルト日時からの差を分で取得する。<br>
	 * @param targetTime 対象時刻
	 * @return デフォルト日時からの差(分)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static int getMinutes(Date targetTime) throws MospException {
		// 対象日(デフォルト日時)からの差を分で取得
		return getMinutes(targetTime, DateUtility.getDefaultTime());
	}
	
	/**
	 * 対象日からの差を分で取得する。<br>
	 * @param targetTime 対象時刻
	 * @param targetDate 対象日
	 * @return 対象日からの差(分)
	 */
	public static int getMinutes(Date targetTime, Date targetDate) {
		// 日付オブジェクトの差を時間(分)で取得
		return getDifferenceMinutes(targetDate, targetTime);
	}
	
	/**
	 * 日付オブジェクトの差を時間(分)で取得する。<br>
	 * @param startTime 開始時刻
	 * @param endTime   終了時刻
	 * @return 日付オブジェクトの差(分)
	 */
	public static int getDifferenceMinutes(Date startTime, Date endTime) {
		// 開始時刻、終了時刻確認
		if (startTime == null || endTime == null) {
			return 0;
		}
		// ミリ秒で差を取得
		long defference = endTime.getTime() - startTime.getTime();
		// 時間に変換
		return (int)(defference / TimeConst.CODE_DEFINITION_MINUTE_MILLI_SEC);
	}
	
	/**
	 * 勤怠時刻文字列を日付オブジェクトに変換し取得する。<br>
	 * 勤怠時刻文字列は4文字の数字(HHmm)とする。<br>
	 * 基準日は、変換の際に基準として用いる。<br>
	 * @param date       基準日
	 * @param time       勤怠時刻文字列
	 * @param mospParams MosP処理情報(エラーメッセージ設定用)
	 * @return 日付オブジェクト
	 */
	public static Date getAttendanceTime(Date date, String time, MospParams mospParams) {
		// フォーマット確認
		if (time.matches(REG_ATTENDANCE_TIME) == false) {
			// エラーメッセージ設定
			mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_FORMAT_CHECK, DateUtility.getStringDate(date));
			return null;
		}
		// 時間取得
		String hour = time.substring(0, 2);
		String minute = time.substring(2, 4);
		// 基準日における時刻を取得
		return TimeUtility.getDateTime(date, hour, minute);
	}
	
	/**
	 * 勤怠時刻文字列を日付オブジェクトに変換し取得する。<br>
	 * 基準日は、変換の際に基準として用いる。<br>
	 * @param date       基準日
	 * @param hour       時間文字列
	 * @param minute     分文字列
	 * @param mospParams MosP処理情報(エラーメッセージ設定用)
	 * @return 日付オブジェクト
	 */
	public static Date getAttendanceTime(Date date, String hour, String minute, MospParams mospParams) {
		StringBuffer sb = new StringBuffer();
		if (hour.length() == 1) {
			sb.append(0);
		}
		sb.append(hour);
		if (minute.length() == 1) {
			sb.append(0);
		}
		sb.append(minute);
		return getAttendanceTime(date, sb.toString(), mospParams);
	}
	
	/**
	 * 空の勤務形態エンティティを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 締日エンティティ
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static WorkTypeEntityInterface getBareWorkTypeEntity(MospParams mospParams) throws MospException {
		// 空の勤務形態エンティティを取得
		WorkTypeEntityInterface entity = MospUtility.createObject(WorkTypeEntityInterface.class, mospParams);
		// 空の勤務形態項目情報リストを設定
		entity.setWorkTypeItemList(Collections.emptyList());
		// 空の勤務形態エンティティを取得
		return entity;
	}
	
	/**
	 * 空の勤怠設定エンティティを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 勤怠設定エンティティ
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static TimeSettingEntityInterface getBareTimeSettingEntity(MospParams mospParams) throws MospException {
		// 空の勤怠設定エンティティを取得
		TimeSettingEntityInterface entity = MospUtility.createObject(TimeSettingEntityInterface.class, mospParams);
		// 空の限度基準情報群を設定
		entity.setLimitStandardDtos(Collections.emptyMap());
		// 空の勤怠設定エンティティを取得
		return entity;
	}
	
	/**
	 * 空の締日エンティティを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 締日エンティティ
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static CutoffEntityInterface getBareCutoffEntity(MospParams mospParams) throws MospException {
		// 空の締日エンティティを取得
		return MospUtility.createObject(CutoffEntityInterface.class, mospParams);
	}
	
	/**
	 * 対象年月及び締日から締期間初日を取得する。<br>
	 * @param cutoffDate  締日
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param mospParams  MosP処理情報
	 * @return 締期間初日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffFirstDate(int cutoffDate, int targetYear, int targetMonth, MospParams mospParams)
			throws MospException {
		// 締日の調整
		int cutoffDay = adjustCutoffDay(cutoffDate, mospParams);
		// 締期間初日設定
		return MonthUtility.getYearMonthTermFirstDate(targetYear, targetMonth, cutoffDay);
	}
	
	/**
	 * 対象日が含まれる締期間の初日を取得する。<br>
	 * @param cutoffDate  締日
	 * @param targetDate  対象日
	 * @param mospParams  MosP処理情報
	 * @return 締期間初日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffFirstDate(int cutoffDate, Date targetDate, MospParams mospParams) throws MospException {
		// 対象日及び締日から対象日が含まれる締月を取得
		Date cutffMonth = getCutoffMonth(cutoffDate, targetDate, mospParams);
		// 締年月を取得
		int targetYear = DateUtility.getYear(cutffMonth);
		int targetMonth = DateUtility.getMonth(cutffMonth);
		// 対象年月及び締日から締期間初日を取得
		return getCutoffFirstDate(cutoffDate, targetYear, targetMonth, mospParams);
	}
	
	/**
	 * 対象年月及び締日から締期間最終日を取得する。<br>
	 * @param cutoffDate  締日
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param mospParams  MosP処理情報
	 * @return 締期間最終日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffLastDate(int cutoffDate, int targetYear, int targetMonth, MospParams mospParams)
			throws MospException {
		// 締日の調整
		int cutoffDay = adjustCutoffDay(cutoffDate, mospParams);
		// 締期間最終日設定
		return MonthUtility.getYearMonthTermLastDate(targetYear, targetMonth, cutoffDay);
	}
	
	/**
	 * 対象日が含まれる締期間の最終日を取得する。<br>
	 * @param cutoffDate  締日
	 * @param targetDate  対象日
	 * @param mospParams  MosP処理情報
	 * @return 締期間最終日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffLastDate(int cutoffDate, Date targetDate, MospParams mospParams) throws MospException {
		// 対象日及び締日から対象日が含まれる締月を取得
		Date cutffMonth = getCutoffMonth(cutoffDate, targetDate, mospParams);
		// 締年月を取得
		int targetYear = DateUtility.getYear(cutffMonth);
		int targetMonth = DateUtility.getMonth(cutffMonth);
		// 対象年月及び締日から締期間初日を取得
		return getCutoffLastDate(cutoffDate, targetYear, targetMonth, mospParams);
	}
	
	/**
	 * 調整した締日を取得する。<br>
	 * 翌月締の場合、締日+100を取得する。<br>
	 * @param cutoffDay  締日
	 * @param mospParams MosP処理情報
	 * @return 調整した締日
	 */
	protected static int adjustCutoffDay(int cutoffDay, MospParams mospParams) {
		// 月末締の場合
		if (cutoffDay == TimeConst.CUTOFF_DATE_LAST_DAY) {
			// 0を(締日をそのまま)取得
			return cutoffDay;
		}
		// 翌月締日の最大値を取得
		int cutoffDateNextManthMax = mospParams.getApplicationProperty(TimeConst.APP_CUTOFF_DATE_NEXT_MONTH_MAX,
				TimeConst.CUTOFF_DATE_NEXT_MONTH_MAX);
		// 翌月締日の最大値よりも大きい(当月である)場合
		if (cutoffDay > cutoffDateNextManthMax) {
			// 締日をそのまま取得
			return cutoffDay;
		}
		// 締日+100を取得(翌月である場合)
		return cutoffDay + MonthUtility.TARGET_DATE_NEXT_MONTH;
	}
	
	/**
	 * 対象年月における締期間の基準日を取得する。<br>
	 * @param cutoffDate  締日
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param mospParams  MosP処理情報
	 * @return 締期間基準日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffTermTargetDate(int cutoffDate, int targetYear, int targetMonth, MospParams mospParams)
			throws MospException {
		// 対象年月及び締日から締期間最終日を取得
		return getCutoffLastDate(cutoffDate, targetYear, targetMonth, mospParams);
	}
	
	/**
	 * 対象年月における締期間の集計日を取得する。<br>
	 * @param cutoffDate  締日
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param mospParams  MosP処理情報
	 * @return 締期間集計日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffCalculationDate(int cutoffDate, int targetYear, int targetMonth, MospParams mospParams)
			throws MospException {
		// 対象年月及び締日から締期間最終日を取得
		return getCutoffLastDate(cutoffDate, targetYear, targetMonth, mospParams);
	}
	
	/**
	 * 対象日及び締日から対象日が含まれる締月を取得する。<br>
	 * @param cutoffDate  締日
	 * @param targetDate  対象日
	 * @param mospParams  MosP処理情報
	 * @return 締月(締月初日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getCutoffMonth(int cutoffDate, Date targetDate, MospParams mospParams) throws MospException {
		// 締日の調整
		int cutoffDay = adjustCutoffDay(cutoffDate, mospParams);
		// 対象日が含まれる締月を取得
		return MonthUtility.getTargetYearMonth(targetDate, cutoffDay);
	}
	
	/**
	 * 対象日付及び締日から対象年月日が含まれる締月の期間を取得する。<br>
	 * @param cutoffDate  締日
	 * @param targetDate  対象日付
	 * @param mospParams  MosP処理情報
	 * @return 締月の期間(日付のリスト)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static List<Date> getCutoffTerm(int cutoffDate, Date targetDate, MospParams mospParams)
			throws MospException {
		// 対象日付及び締日から対象年月日が含まれる締月を取得
		Date cutoffMonth = getCutoffMonth(cutoffDate, targetDate, mospParams);
		// 締月の年月を取得
		int targetYear = DateUtility.getYear(cutoffMonth);
		int targetMonth = DateUtility.getMonth(cutoffMonth);
		// 締月の期間を取得
		return getCutoffTerm(cutoffDate, targetYear, targetMonth, mospParams);
	}
	
	/**
	 * 対象年月及び締日から対象年月日が含まれる締月の期間を取得する。<br>
	 * @param cutoffDate  締日
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param mospParams  MosP処理情報
	 * @return 締月の期間(日付のリスト)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static List<Date> getCutoffTerm(int cutoffDate, int targetYear, int targetMonth, MospParams mospParams)
			throws MospException {
		// 締月の初日及び最終日を取得
		Date firstDate = getCutoffFirstDate(cutoffDate, targetYear, targetMonth, mospParams);
		Date lastDate = getCutoffLastDate(cutoffDate, targetYear, targetMonth, mospParams);
		// 締月の期間を取得
		return getDateList(firstDate, lastDate);
	}
	
	/**
	 * 社員勤怠集計管理情報が締められている(仮締か確定)かを確認する。<br>
	 * @param dto 社員勤怠集計管理情報
	 * @return 確認結果(true：締められている(仮締か確定)、false：締められていない)
	 */
	public static boolean isTightend(TotalTimeEmployeeDtoInterface dto) {
		// 社員勤怠集計管理情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 締められていないと判断
			return false;
		}
		// 未締でない場合は締められていると判断
		return dto.getCutoffState() != TimeConst.CODE_CUTOFF_STATE_NOT_TIGHT;
	}
	
	/**
	 * 勤怠管理用機能コードセットを取得する。<br>
	 * 承認解除は含まれない。<br>
	 * @return 勤怠管理用機能コードセット
	 */
	public static Set<String> getTimeFunctionSet() {
		// 勤怠管理用機能コードセット準備
		Set<String> set = new HashSet<String>();
		set.add(TimeConst.CODE_FUNCTION_WORK_MANGE);
		set.add(TimeConst.CODE_FUNCTION_OVER_WORK);
		set.add(TimeConst.CODE_FUNCTION_VACATION);
		set.add(TimeConst.CODE_FUNCTION_WORK_HOLIDAY);
		set.add(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY);
		set.add(TimeConst.CODE_FUNCTION_DIFFERENCE);
		set.add(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE);
		return set;
	}
	
	/**
	 * ワークフロー情報が勤怠であるかを確認する。<br>
	 * @param dto          ワークフロー情報
	 * @return 確認結果(true：その機能である、false：そうでない)
	 */
	public static boolean isAttendance(WorkflowDtoInterface dto) {
		// ワークフロー情報が勤怠であるかを確認
		return PlatformUtility.isTheFunction(dto, TimeConst.CODE_FUNCTION_WORK_MANGE);
	}
	
	/**
	 * 機能名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param function   機能コード
	 * @return 機能名称
	 */
	public static String getFunctionName(MospParams mospParams, String function) {
		// 機能名称を取得
		String functionName = MospUtility.getCodeName(mospParams, function, TimeConst.CODE_APPROVAL_TYPE);
		// 承認解除の場合
		if (MospUtility.isEqual(functionName, TimeConst.CODE_FUNCTION_CANCELLATION)) {
			// 機能名称を取得(承認カテゴリには含まれないため)
			functionName = TimeNamingUtility.cancellationRequest(mospParams);
		}
		// 機能名称を取得
		return functionName;
	}
	
	/**
	 * 休暇設定における取得期間(無期限)を取得する。
	 * @return 無期限
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getUnlimitedDate() throws MospException {
		return DateUtility.getDate(DATE_UNLIMITED_YEAR, TimeConst.CODE_DEFINITION_YEAR, DATE_YEAR_LAST_MONTH);
	}
	
	/**
	 * 対象休暇付与情報が無期限(無制限)であるかを確認する。<br>
	 * @param dto 休暇付与情報
	 * @return 確認結果(true：対象休暇付与情報が無期限(無制限)である、false：そうでない)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static boolean isUnlimited(HolidayDataDtoInterface dto) throws MospException {
		// 休暇付与情報が存在しない場合
		if (dto == null) {
			// 無制限でないと判断
			return false;
		}
		// 取得期限が無期限と同じであるかを確認
		return isUnlimited(dto.getHolidayLimitDate());
	}
	
	/**
	 * 取得期限が無期限(無制限)であるかを確認する。<br>
	 * @param limitDate 取得期限
	 * @return 確認結果(true：対象休暇付与情報が無期限(無制限)である、false：そうでない)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static boolean isUnlimited(Date limitDate) throws MospException {
		// 取得期限が無期限と同じであるかを確認
		return DateUtility.isSame(getUnlimitedDate(), limitDate);
	}
	
	/**
	 * 年月指定時の基準日を取得する。<br>
	 * 対象月(文字列)から対象月を取得できない場合は、12として取得する。<br>
	 * @param targetYear  対象年(文字列)
	 * @param targetMonth 対象月(文字列)
	 * @param mospParams  MosP処理情報
	 * @return 年月指定時の基準日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getYearMonthTargetDate(String targetYear, String targetMonth, MospParams mospParams)
			throws MospException {
		// 対象年月(数値)を取得
		int year = MospUtility.getInt(targetYear);
		int month = getTargetMonth(targetMonth);
		// 年月指定時の基準日を取得
		return MonthUtility.getYearMonthTargetDate(year, month, mospParams);
	}
	
	/**
	 * 対象月を取得する。<br>
	 * 対象月(文字列)から対象月を取得できない場合は、12を取得する。<br>
	 * @param targetMonth 対象月(文字列)
	 * @return 対象月
	 */
	public static int getTargetMonth(String targetMonth) {
		// 対象月を数値に変換
		int month = MospUtility.getIntOrZero(MospUtility.getInt(targetMonth));
		// 対象月を取得できなかった場合
		if (month == 0) {
			// 12を取得
			month = TimeConst.CODE_DEFINITION_YEAR;
		}
		// 対象月を取得
		return month;
	}
	
	/**
	 * 初日から最終日の日リストを取得する。<br>
	 * @param firstDate 初日
	 * @param lastDate  最終日
	 * @return 日リスト
	 */
	public static List<Date> getDateList(Date firstDate, Date lastDate) {
		// 日リストを準備
		List<Date> list = new ArrayList<Date>();
		// 日の確認
		if (MospUtility.isEmpty(firstDate, lastDate)) {
			// 空のリストを取得
			return list;
		}
		// 初日を基に対象日を準備
		Date date = CapsuleUtility.getDateClone(firstDate);
		// 対象日毎に処理
		while (date.after(lastDate) == false) {
			// リストに追加
			list.add(date);
			// 日を加算
			date = DateUtility.addDay(date, 1);
		}
		// 日リストを取得
		return list;
	}
	
	/**
	 * 開始年月から終了年月の年月を表す日付(年月の初日)リストを取得する。<br>
	 * @param firstMonth 開始年月
	 * @param lastMonth  終了年月
	 * @return 開始年月から終了年月の年月を表す日付(年月の初日)リスト
	 */
	public static List<Date> getMonthList(Date firstMonth, Date lastMonth) {
		// 開始年月から終了年月の年月を表す日付(年月の初日)リストを準備
		List<Date> list = new ArrayList<Date>();
		// 年月の確認
		if (MospUtility.isEmpty(firstMonth, lastMonth)) {
			// 空のリストを取得
			return list;
		}
		// 開始年月を基に年月を表す日付(年月の初日)を準備
		Date date = MonthUtility.getYearMonthDate(DateUtility.getYear(firstMonth), DateUtility.getMonth(firstMonth));
		// 対象日毎に処理
		while (date.after(lastMonth) == false) {
			// リストに追加
			list.add(date);
			// 月を加算
			date = DateUtility.addMonth(date, 1);
		}
		// 開始年月から終了年月の年月を表す日付(年月の初日)リストを取得
		return list;
	}
	
	/**
	 * 丸めた時間を取得する。<br>
	 * @param time 対象時間
	 * @param type 丸め種別
	 * @param unit 丸め単位
	 * @return 丸めた時間
	 */
	public static int getRoundMinute(int time, int type, int unit) {
		if (time <= 0 || type == 0 || unit <= 0) {
			return time;
		}
		if (type == 1 || type == 2) {
			// 余り
			int remainder = time % unit;
			if (remainder == 0) {
				// 余りが0の場合はそのまま返す
				return time;
			}
			int rounded = time - remainder;
			if (type == 1) {
				// 切捨て
				return rounded;
			} else if (type == 2) {
				// 切上げ
				return rounded + unit;
			}
		}
		return time;
	}
	
	/**
	 * 丸めた時間を取得する。<br>
	 * @param time 対象時間
	 * @param type 丸め種別
	 * @param unit 丸め単位
	 * @return 丸めた時間
	 */
	public static Date getRoundMinute(Date time, int type, int unit) {
		if (time == null) {
			return null;
		}
		long milliseconds = time.getTime();
		// 丸めが不要である場合
		if (milliseconds == 0 || type == TimeConst.TYPE_ROUND_NONE || unit <= 0) {
			return time;
		}
		// 丸め区分が切捨か切上である場合
		if (type == TimeConst.TYPE_ROUND_DOWN || type == TimeConst.TYPE_ROUND_UP) {
			// 丸め単位を分単位からミリ秒単位に変換
			int millisecondsUnit = unit * TimeConst.CODE_DEFINITION_HOUR * 1000;
			// 余り
			long remainder = milliseconds % millisecondsUnit;
			if (remainder == 0) {
				// 余りが0の場合はそのまま返す
				return time;
			}
			long rounded = milliseconds - remainder;
			// 切捨である場合
			if (type == TimeConst.TYPE_ROUND_DOWN) {
				return new Date(rounded);
			}
			// 切上である場合
			if (type == TimeConst.TYPE_ROUND_UP) {
				return new Date(rounded + millisecondsUnit);
			}
		}
		return time;
	}
	
	/**
	 * 勤務形態コードが所定休日であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：所定休日である、false：所定休日でない)
	 */
	public static boolean isPrescribedHoliday(String workTypeCode) {
		// 所定休日が勤務形態コードと一致するかを確認
		return TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 勤務形態コードが法定休日であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：法定休日である、false：法定休日でない)
	 */
	public static boolean isLegalHoliday(String workTypeCode) {
		// 法定休日が勤務形態コードと一致するかを確認
		return TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 勤務形態コードが所定休日又は法定休日であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：所定休日又は法定休日である、false：そうでない)
	 */
	public static boolean isHoliday(String workTypeCode) {
		// 所定休日又は法定休日であるかを確認
		return isPrescribedHoliday(workTypeCode) || isLegalHoliday(workTypeCode);
	}
	
	/**
	 * 勤務形態コードが所定休日出勤であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：所定休日出勤である、false：所定休日出勤でない)
	 */
	public static boolean isWorkOnPrescribedHoliday(String workTypeCode) {
		// 所定休日出勤が勤務形態コードと一致するかを確認
		return TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 勤務形態コードが法定休日出勤であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：法定休日出勤である、false：法定休日出勤でない)
	 */
	public static boolean isWorkOnLegalHoliday(String workTypeCode) {
		// 所定休日出勤が勤務形態コードと一致するかを確認
		return TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 勤務形態コードが法定休日出勤か所定休日出勤であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：法定休日出勤か所定休日出勤である、false：そうでない)
	 */
	public static boolean isWorkOnLegalOrPrescribedHoliday(String workTypeCode) {
		// 勤務形態コードが法定休日出勤か所定休日出勤であるかを確認
		return isWorkOnLegalHoliday(workTypeCode) || isWorkOnPrescribedHoliday(workTypeCode);
	}
	
	/**
	 * 法定休日(法定休日出勤含む)であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：法定休日(法定休日出勤含む)である、false：そうでない)
	 */
	public static boolean isLegal(String workTypeCode) {
		// 法定休日(法定休日出勤含む)であるかを確認
		return isLegalHoliday(workTypeCode) || isWorkOnLegalHoliday(workTypeCode);
	}
	
	/**
	 * 所定休日(所定休日出勤含む)であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：所定休日(所定休日出勤含む)である、false：そうでない)
	 */
	public static boolean isPrescribed(String workTypeCode) {
		// 所定休日(所定休日出勤含む)であるかを確認
		return isPrescribedHoliday(workTypeCode) || isWorkOnPrescribedHoliday(workTypeCode);
	}
	
	/**
	 * 法定休日(法定休日出勤含む)か所定休日(所定休日出勤含む)であるかを確認する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：法定休日か所定休日である、false：そうでない)
	 */
	public static boolean isLegalOrPrescribed(String workTypeCode) {
		// 法定休日(法定休日出勤含む)か所定休日(所定休日出勤含む)であるかを確認
		return isLegal(workTypeCode) || isPrescribed(workTypeCode);
	}
	
	/**
	 * コレクションから勤務形態コード群を取得する。<br>
	 * コレクションの要素は{@link WorkTypeCodeDtoInterface }を実装している必要がある。<br>
	 * @param collections コレクション配列
	 * @return 勤務形態コード群
	 */
	public static Set<String> getWorkTypeCodes(Collection<?>... collections) {
		// 勤務形態コード群を準備
		Set<String> set = new TreeSet<String>();
		// コレクション毎に処理
		for (Collection<?> collection : collections) {
			// コレクションの要素毎に処理
			for (Object obj : collection) {
				// コレクションの要素がnullである場合
				if (MospUtility.isEmpty(obj)) {
					// 次の要素へ
					continue;
				}
				// キャスト 
				WorkTypeCodeDtoInterface dto = PlatformUtility.castObject(obj);
				// 勤務形態コードを取得
				String workTypeCode = dto.getWorkTypeCode();
				// 勤務形態コードが設定されていない場合
				if (MospUtility.isEmpty(workTypeCode)) {
					// 次の要素へ
					continue;
				}
				// 勤務形態コード群に勤務形態コードを設定
				set.add(workTypeCode);
			}
		}
		// 勤務形態コード群を取得
		return set;
	}
	
	/**
	 * 申請日情報群(キー：申請日)(キー昇順)を取得する。<br>
	 * @param dtos 申請日情報群
	 * @return 申請日情報群(キー：申請日)(キー昇順)
	 */
	public static <T extends RequestDateDtoInterface> Map<Date, T> getRequestDateMap(Collection<T> dtos) {
		// 申請日群を準備
		Map<Date, T> map = new TreeMap<Date, T>();
		// 申請日情報毎に処理
		for (T dto : dtos) {
			// 申請日情報群に設定
			map.put(dto.getRequestDate(), dto);
		}
		// 申請日情報群を取得
		return map;
	}
	
	/**
	 * 申請日情報群(キー：申請日)(キー昇順)を取得する。<br>
	 * 但し、承認情報群に含まれないワークフロー番号を持つ情報は対象外とする。<br>
	 * @param dtos      申請日情報群
	 * @param workflows ワークフロー情報群(キー：ワークフロー番号)
	 * @param statuses  承認状況群
	 * @return 申請日情報群(キー：申請日)(キー昇順)
	 */
	public static <T extends RequestDateDtoInterface & WorkflowNumberDtoInterface> Map<Date, T> getRequestDateMap(
			Collection<T> dtos, Map<Long, WorkflowDtoInterface> workflows, Set<String> statuses) {
		// 申請日情報群を取得(承認情報群に含まれないワークフロー番号を持つ情報は対象外)
		return getRequestDateMap(WorkflowUtility.getStatusMatchedDtos(dtos, workflows, statuses));
	}
	
	/**
	 * 申請日情報群(キー：申請日)(キー昇順)を取得する。<br>
	 * @param dtos 申請日情報群
	 * @return 申請日情報群(キー：申請日)(キー昇順)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static <T extends RequestDateDtoInterface> Map<Date, List<T>> getRequestDatesMap(Collection<T> dtos)
			throws MospException {
		// 申請日群を準備
		Map<Date, List<T>> map = new TreeMap<Date, List<T>>();
		// 申請日情報毎に処理
		for (T dto : dtos) {
			// マップからキーで値(List)を取得
			List<T> value = MospUtility.getListValue(map, dto.getRequestDate());
			// リストに設定
			value.add(dto);
		}
		// 申請日情報群を取得
		return map;
	}
	
	/**
	 * 振替休日情報群(キー：振替日)(キー昇順)を取得する。<br>
	 * 半日振替をした場合に、同一振替日で2つ振替休日情報が作成されることがある。<br>
	 * @param dtos 振替休日情報群
	 * @return 振替休日情報群(キー：振替日)(キー昇順)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static Map<Date, List<SubstituteDtoInterface>> getSubstitutesMap(List<SubstituteDtoInterface> dtos)
			throws MospException {
		// 振替休日情報群を準備
		Map<Date, List<SubstituteDtoInterface>> map = new TreeMap<Date, List<SubstituteDtoInterface>>();
		// 振替休日情報毎に処理
		for (SubstituteDtoInterface dto : dtos) {
			// マップからキーで値(List)を取得
			List<SubstituteDtoInterface> value = MospUtility.getListValue(map, dto.getSubstituteDate());
			// リストに設定
			value.add(dto);
		}
		// 振替休日情報群を取得
		return map;
	}
	
	/**
	 * 振替日群(キー：出勤日)(キー昇順)を取得する。<br>
	 * 同一日に振出・休出申請は一つしかできないため、出勤日に対しては振休日は一意に決まる。<br>
	 * @param dtos 振替休日情報群
	 * @return 振替日群(キー：出勤日)(キー昇順)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static Map<Date, Date> getSubstitutDates(List<SubstituteDtoInterface> dtos) throws MospException {
		// 振替日群(キー：出勤日)(キー昇順)を準備
		Map<Date, Date> map = new TreeMap<Date, Date>();
		// 振替休日情報毎に処理
		for (SubstituteDtoInterface dto : dtos) {
			// 振替日群(キー：出勤日)(キー昇順)に設定
			map.put(dto.getWorkDate(), dto.getSubstituteDate());
		}
		// 振替日群(キー：出勤日)(キー昇順)を取得
		return map;
	}
	
	/**
	 * 休暇申請情報群(キー：申請日)(キー昇順)を取得する。<br>
	 * 期間で申請している場合は、期間中の全ての日(キー)に対して休暇申請情報が設定される。<br>
	 * @param dtos 休暇申請情報群
	 * @return 休暇申請情報群(キー：申請日)(キー昇順)
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	public static Map<Date, List<HolidayRequestDtoInterface>> getHolidayRequestsMap(
			List<HolidayRequestDtoInterface> dtos) throws MospException {
		// 休暇申請情報群を準備
		Map<Date, List<HolidayRequestDtoInterface>> map = new TreeMap<Date, List<HolidayRequestDtoInterface>>();
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// 申請開始日から申請終了日まで日毎に処理
			for (Date date : TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate())) {
				// マップからキーで値(List)を取得
				List<HolidayRequestDtoInterface> value = MospUtility.getListValue(map, date);
				// リストに設定
				value.add(dto);
			}
		}
		// 振替休日情報群を取得
		return map;
	}
	
	/**
	 * 勤怠一覧が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：勤怠一覧利用可、false：不可)
	 */
	public static boolean isAttendanceListAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_ATTENDANCE_LIST);
	}
	
	/**
	 * 残業申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：残業申請が有効、false：無効)
	 */
	public static boolean isOvertimeRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_OVERTIME_REQUEST);
	}
	
	/**
	 * 残業申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：残業申請利用可、false：不可)
	 */
	public static boolean isOvertimeRequestAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_OVERTIME_REQUEST);
	}
	
	/**
	 * 休暇申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：休暇申請が有効、false：無効)
	 */
	public static boolean isHolidayRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_HOLIDAY_REQUEST);
	}
	
	/**
	 * 休暇申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：休暇申請利用可、false：不可)
	 */
	public static boolean isHolidayRequestAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_HOLIDAY_REQUEST);
	}
	
	/**
	 * 振出・休出申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：振出・休出申請が有効、false：無効)
	 */
	public static boolean isWorkOnHolidayRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_ON_HOLIDAY_REQUEST);
	}
	
	/**
	 * 振出・休出申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：振出・休出申請利用可、false：不可)
	 */
	public static boolean isWorkOnHolidayRequestAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_ON_HOLIDAY_REQUEST);
	}
	
	/**
	 * 代休申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：代休申請が有効、false：無効)
	 */
	public static boolean isSubHolidayRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_SUB_HOLIDAY_REQUEST);
	}
	
	/**
	 * 代休申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：代休申請利用可、false：不可)
	 */
	public static boolean isSubHolidayRequestAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_SUB_HOLIDAY_REQUEST);
	}
	
	/**
	 * 勤務形態変更申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：勤務形態変更申請が有効、false：無効)
	 */
	public static boolean isWorkTypeChangeRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_TYPE_CHANGE_REQUEST);
	}
	
	/**
	 * 勤務形態変更申請が利用できるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * MosP処理情報に設定されたユーザが対象メニューを利用できるかで、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：勤務形態変更申請利用可、false：不可)
	 */
	public static boolean isWorkTypeChangeRequestAvailable(MospParams mospParams) {
		return MenuUtility.isTheMenuAvailable(mospParams, MAIN_MENU_TIME_INMPUT, MENU_WORK_TYPE_CHANGE_REQUEST);
	}
	
	/**
	 * 時差出勤申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：時差出勤申請が有効、false：無効)
	 */
	public static boolean isDifferenceRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_DIFFERENCE_REQUEST);
	}
	
	/**
	 * 承認解除申請が有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：承認解除申請が有効、false：無効)
	 */
	public static boolean isCancellationRequestValid(MospParams mospParams) {
		return MenuUtility.isTheMenuValid(mospParams, MAIN_MENU_TIME_INMPUT, MENU_CANCELLATION_REQUEST);
	}
	
	/**
	 * double型の値を丸める。
	 * @param value        値
	 * @param scale        丸めのスケール
	 * @param roundingMode 適用する丸めモード
	 * 
	 * @return 少数点第三位で四捨五入した値
	 */
	public static double round(double value, int scale, RoundingMode roundingMode) {
		// 数値を準備
		BigDecimal bi = new BigDecimal(String.valueOf(value));
		// 丸め
		return bi.setScale(scale, roundingMode).doubleValue();
	}
	
	/**
	 * double型の値を少数点第三位で四捨五入する。
	 * @param value 値
	 * @return 少数点第三位で四捨五入した値
	 */
	public static double getRoundHalfUp2(double value) {
		// 小数点第三位で四捨五入
		return round(value, 2, RoundingMode.HALF_UP);
	}
	
	/**
	 * 時間を分単位から一時間単位に表記変更し、少数点第三位で四捨五入する。
	 * @param minutes 分
	 * @return 十進法に表記かつ少数点第三位で四捨五入した値
	 */
	public static double getMinuteToHour(double minutes) {
		double dotMinutes = minutes / TimeConst.CODE_DEFINITION_HOUR;
		// 小数点第三位で四捨五入
		return getRoundHalfUp2(dotMinutes);
	}
	
	/**
	 * 回数を取得する。<br>
	 * 時間がnull、或いは0の場合は0を返す。<br>
	 * それ以外の場合は1を返す。<br>
	 * 遅刻時間から遅刻回数を数える場合等に用いる。<br>
	 * @param minutes 時間(分)
	 * @return 回数
	 */
	public static int count(Integer minutes) {
		// 時間がnullか0の場合
		if (MospUtility.isEmpty(minutes) || minutes.intValue() == 0) {
			// 0を取得
			return 0;
		}
		// 1を取得
		return 1;
	}
	
	/**
	 * 時間単位休暇の時間数を取得する。<br>
	 * @param dto 休暇申請情報
	 * @return 時間単位休暇の時間数
	 */
	public static int getHolidayHours(HolidayRequestDtoInterface dto) {
		// 時間単位休暇の分数を取得
		int minutes = TimeUtility.getDuration(dto.getStartTime(), dto.getEndTime()).getMinutes();
		// 時間単位休暇の時間数を取得
		return MospUtility.divide(minutes, TimeConst.CODE_DEFINITION_HOUR);
	}
	
	/**
	 * 休暇回数が半日であるかを確認する。
	 * @param holidayTimes 休暇回数
	 * @return 確認結果(true：休暇回数が半日である、false：そうでない)
	 */
	public static boolean isHolidayTimesHalf(double holidayTimes) {
		return holidayTimes == TimeConst.HOLIDAY_TIMES_HALF;
	}
	
	/**
	 * 休暇が残っているかを確認する。<br>
	 * @param dto           休暇残情報
	 * @param useDays       利用日数
	 * @param useHours      利用時間数
	 * @param useMinutes    利用分数
	 * @param hoursPerDay   1日の時間数
	 * @param minutesPerDay 1日の分数
	 * @param isZeroRemain  ゼロ判断フラグ(true：全て0なら残っていると判断、false：全て0なら残っていないと判断)
	 * @return 確認結果(true：休暇が残っている、false：休暇が残っていない)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static boolean isHolidayRemain(HolidayRemainDto dto, double useDays, int useHours, int useMinutes,
			int hoursPerDay, int minutesPerDay, boolean isZeroRemain) throws MospException {
		// 休暇残情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 休暇が残っていないと判断
			return false;
		}
		// 休暇残情報(残日数と残時間数と残分数)を計算
		calcHolidayRemains(dto, useDays, useHours, useMinutes, hoursPerDay, minutesPerDay);
		// 休暇が残っているかを確認(日数と時間数と分数が全てに0以上である場合に残っていると判断)
		return isHolidayRemain(dto, isZeroRemain);
	}
	
	/**
	 * 休暇が残っているかを確認する。<br>
	 * @param dto          休暇残情報
	 * @param isZeroRemain ゼロ判断フラグ(true：全て0なら残っていると判断、false：全て0なら残っていないと判断)
	 * @return 確認結果(true：休暇が残っている、false：休暇が残っていない)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static boolean isHolidayRemain(HolidayRemainDto dto, boolean isZeroRemain) throws MospException {
		// 取得期限が無制限である場合
		if (TimeUtility.isUnlimited(dto.getHolidayLimitDate())) {
			// 休暇が残っていると判断
			return true;
		}
		// 値を準備
		double remainDays = dto.getRemainDays();
		int remainHours = dto.getRemainHours();
		int remainMinutes = dto.getRemainMinutes();
		// 全て0なら残っていない判断する場合
		if (isZeroRemain == false) {
			// 全て0である場合
			if (remainDays == 0 && remainHours == 0 && remainMinutes == 0) {
				// 休暇が残っていないと判断
				return false;
			}
		}
		// 休暇が残っているかを確認
		return remainDays >= 0 && remainHours >= 0 && remainMinutes >= 0;
	}
	
	/**
	 * 残数が残っている休暇残情報群を取得する。<br>
	 * @param dtos         休暇残情報群
	 * @param isZeroRemain ゼロ判断フラグ(true：全て0なら残っていると判断、false：全て0なら残っていないと判断)
	 * @return 残数が残っている休暇残情報群
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Set<HolidayRemainDto> getRemainHolidays(Set<HolidayRemainDto> dtos, boolean isZeroRemain)
			throws MospException {
		// 残数のある休暇残情報群を準備
		Set<HolidayRemainDto> remains = new LinkedHashSet<HolidayRemainDto>();
		// 休暇残情報毎に処理
		for (HolidayRemainDto dto : dtos) {
			// 休暇が残っている場合(全て0なら残っていないと判断)
			if (TimeUtility.isHolidayRemain(dto, false)) {
				// 休暇残情報を休暇残情報群(キー：休暇コード、値：休暇残情報)に設定
				remains.add(dto);
			}
		}
		// 残数のある休暇残情報群を取得
		return remains;
	}
	
	/**
	 * 休暇残情報(残日数と残時間数と残分数)を取得する。<br>
	 * @param dto           休暇付与情報
	 * @param holiday       休暇種別情報
	 * @param useDays       利用日数
	 * @param useHours      利用時間数
	 * @param useMinutes    利用分数
	 * @param hoursPerDay   1日の時間数
	 * @param minutesPerDay 1日の分数
	 * @return 休暇残情報
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static HolidayRemainDto getHolidayRemains(HolidayDataDtoInterface dto, HolidayDtoInterface holiday,
			double useDays, int useHours, int useMinutes, int hoursPerDay, int minutesPerDay) throws MospException {
		// 休暇残情報を準備
		HolidayRemainDto remain = new HolidayRemainDto();
		remain.setHolidayCode(dto.getHolidayCode());
		remain.setHolidayType(dto.getHolidayType());
		remain.setAcquisitionDate(dto.getActivateDate());
		remain.setHolidayLimitDate(dto.getHolidayLimitDate());
		remain.setGivenDays(getCurrentDays(dto));
		remain.setGivenHours(getCurrentHours(dto));
		remain.setHolidayName(holiday.getHolidayName());
		remain.setHolidayAbbr(holiday.getHolidayAbbr());
		// 対象休暇付与情報が無期限(無制限)である場合
		if (TimeUtility.isUnlimited(dto)) {
			// 休暇残情報を取得(残数は不要(0))
			return remain;
		}
		// 休暇残情報に現在の残数を設定
		remain.setRemainDays(remain.getGivenDays());
		remain.setRemainHours(remain.getGivenHours());
		remain.setRemainMinutes(0);
		// 休暇残情報(残日数と残時間数と残分数)を計算
		calcHolidayRemains(remain, useDays, useHours, useMinutes, hoursPerDay, minutesPerDay);
		// 休暇残情報を取得
		return remain;
	}
	
	/**
	 * 有給休暇残情報(残日数と残時間数)を取得する。<br>
	 * @param dto           有給休暇付与情報
	 * @param manualDays    手動付与日数
	 * @param manualHours   手動付与時間数
	 * @param useDays       利用日数
	 * @param useHours      利用時間数
	 * @param hoursPerDay   1日の時間数
	 * @return 休暇残情報
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static HolidayRemainDto getPaidHolidayRemain(PaidHolidayDataDtoInterface dto, double manualDays,
			int manualHours, double useDays, int useHours, int hoursPerDay) throws MospException {
		// 休暇残情報を準備
		HolidayRemainDto remain = new HolidayRemainDto();
		remain.setHolidayCode(TimeConst.CODE_HOLIDAYTYPE2_PAID);
		remain.setHolidayType(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY);
		remain.setAcquisitionDate(dto.getAcquisitionDate());
		remain.setHolidayLimitDate(dto.getLimitDate());
		remain.setGivenDays(dto.getHoldDay() + manualDays);
		remain.setGivenHours(dto.getHoldHour() + manualHours);
		remain.setHolidayName(MospConst.STR_EMPTY);
		remain.setHolidayAbbr(MospConst.STR_EMPTY);
		// 休暇残情報に現在の残数を設定
		remain.setRemainDays(remain.getGivenDays());
		remain.setRemainHours(remain.getGivenHours());
		remain.setRemainMinutes(0);
		// 休暇残情報(残日数と残時間数)を計算
		calcHolidayRemains(remain, useDays, useHours, 0, hoursPerDay, 0);
		// 休暇残情報を取得
		return remain;
	}
	
	/**
	 * 空の有給休暇残情報を取得する。<br>
	 * @param acquisitionDate  休暇取得日
	 * @param holidayLimitDate 取得期限
	 * @return 休暇残情報
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static HolidayRemainDto getBarePaidHolidayRemain(Date acquisitionDate, Date holidayLimitDate)
			throws MospException {
		// 休暇残情報を準備
		HolidayRemainDto remain = new HolidayRemainDto();
		remain.setHolidayCode(TimeConst.CODE_HOLIDAYTYPE2_PAID);
		remain.setHolidayType(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY);
		remain.setAcquisitionDate(acquisitionDate);
		remain.setHolidayLimitDate(holidayLimitDate);
		remain.setGivenDays(0);
		remain.setGivenHours(0);
		remain.setRemainDays(0);
		remain.setRemainHours(0);
		remain.setRemainMinutes(0);
		remain.setHolidayName(MospConst.STR_EMPTY);
		remain.setHolidayAbbr(MospConst.STR_EMPTY);
		// 休暇残情報を取得
		return remain;
	}
	
	/**
	 * ストック休暇残情報(残日数)を取得する。<br>
	 * @param dto           ストック休暇付与情報
	 * @param manualDays    手動付与日数
	 * @param useDays       利用日数
	 * @return 休暇残情報
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static HolidayRemainDto getStockHolidayRemain(StockHolidayDataDtoInterface dto, double manualDays,
			double useDays) throws MospException {
		// 休暇残情報を準備
		HolidayRemainDto remain = new HolidayRemainDto();
		remain.setHolidayCode(TimeConst.CODE_HOLIDAYTYPE2_STOCK);
		remain.setHolidayType(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY);
		remain.setAcquisitionDate(dto.getAcquisitionDate());
		remain.setHolidayLimitDate(dto.getLimitDate());
		remain.setGivenDays(dto.getHoldDay() + manualDays);
		remain.setGivenHours(0);
		remain.setHolidayName(MospConst.STR_EMPTY);
		remain.setHolidayAbbr(MospConst.STR_EMPTY);
		// 休暇残情報に現在の残数を設定
		remain.setRemainDays(remain.getGivenDays());
		remain.setRemainHours(0);
		remain.setRemainMinutes(0);
		// 休暇残情報(残日数と残時間数)を計算
		calcHolidayRemains(remain, useDays, 0, 0, 0, 0);
		// 休暇残情報を取得
		return remain;
	}
	
	/**
	 * 空のストック休暇残情報を取得する。<br>
	 * @param acquisitionDate  休暇取得日
	 * @param holidayLimitDate 取得期限
	 * @return 休暇残情報
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static HolidayRemainDto getBareStockHolidayRemain(Date acquisitionDate, Date holidayLimitDate)
			throws MospException {
		// 休暇残情報を準備
		HolidayRemainDto remain = new HolidayRemainDto();
		remain.setHolidayCode(TimeConst.CODE_HOLIDAYTYPE2_STOCK);
		remain.setHolidayType(TimeConst.CODE_HOLIDAYTYPE_HOLIDAY);
		remain.setAcquisitionDate(acquisitionDate);
		remain.setHolidayLimitDate(holidayLimitDate);
		remain.setGivenDays(0);
		remain.setGivenHours(0);
		remain.setRemainDays(0);
		remain.setRemainHours(0);
		remain.setRemainMinutes(0);
		remain.setHolidayName(MospConst.STR_EMPTY);
		remain.setHolidayAbbr(MospConst.STR_EMPTY);
		// 休暇残情報を取得
		return remain;
	}
	
	/**
	 * 休暇残情報(残日数と残時間数と残分数)を計算する。<br>
	 * <br>
	 * 休暇残情報に利用分をマイナスする前の残数が設定されているものとする。<br>
	 * 計算結果は休暇残情報に反映させる。<br>
	 * <br>
	 * 残時間(分)数が1日の時間(分)数より大きい場合は日数に繰り上げ、
	 * 残時間(分)数がマイナスである場合は日数から取り崩す。<br>
	 * <br>
	 * そのため、残数が足りない場合は残日数がマイナスとなり、
	 * 残時間(分)数は0から1日の時間(分)数の間の数値となる。<br>
	 * <br>
	 * 但し、1日の時間(分)数が0(取り崩し不能)である場合は、この限りではない。
	 * <br>
	 * @param dto           休暇残情報(利用前)
	 * @param useDays       利用日数
	 * @param useHours      利用時間数
	 * @param useMinutes    利用分数
	 * @param hoursPerDay   1日の時間数
	 * @param minutesPerDay 1日の分数
	 */
	public static void calcHolidayRemains(HolidayRemainDto dto, double useDays, int useHours, int useMinutes,
			int hoursPerDay, int minutesPerDay) {
		// 残日数及び残時間数及び残分数を準備
		double remainDays = dto.getRemainDays();
		int remainHours = dto.getRemainHours();
		int remainMinutes = dto.getRemainMinutes();
		// 利用数を減算
		remainDays -= useDays;
		remainHours -= useHours;
		remainMinutes -= useMinutes;
		// 残日数から取り崩し(時間)
		SimpleEntry<Double, Integer> reduceDaysToHours = reduceDays(remainDays, remainHours, hoursPerDay);
		// 取り崩し結果を取得
		remainDays = reduceDaysToHours.getKey();
		remainHours = reduceDaysToHours.getValue();
		// 残日数から取り崩し(分)
		SimpleEntry<Double, Integer> reduceDaysToMinutes = reduceDays(remainDays, remainMinutes, minutesPerDay);
		// 取り崩し結果を取得
		remainDays = reduceDaysToMinutes.getKey();
		remainMinutes = reduceDaysToMinutes.getValue();
		// 計算結果を休暇残情報に反映
		dto.setRemainDays(remainDays);
		dto.setRemainHours(remainHours);
		dto.setRemainMinutes(remainMinutes);
	}
	
	/**
	 * 残日数から取り崩しをする。<br>
	 * 但し、残数が0より大きい場合は、繰り上げる。<br>
	 * @param remainDays   残日数
	 * @param remainAmount 残数(残時間数or残分数)
	 * @param amountPerDay 1日の数(1日の時間数or1日の分数)
	 * @return 取り崩し結果(キー：残日数、値：残数)
	 */
	protected static SimpleEntry<Double, Integer> reduceDays(double remainDays, int remainAmount, int amountPerDay) {
		// 1日の数が0(取り崩し不能)である場合
		if (amountPerDay == 0) {
			// 残日数及び残数をそのまま取得
			return new SimpleEntry<Double, Integer>(remainDays, remainAmount);
		}
		// 残数を確認(0の場合は取り崩しも繰り上げも無し)
		if (remainAmount > 0) {
			// 残数が正である場合(繰り上げ)
			// 繰り上げ日数を残日数に加算
			remainDays += Math.abs(remainAmount / amountPerDay);
			// 残数(繰り上げた残り)を設定
			remainAmount = remainAmount % amountPerDay;
		} else if (remainAmount < 0) {
			// 残数が負である場合(取り崩し)
			// 取り崩し日数を取得
			int breakDays = Math.abs(remainAmount / amountPerDay);
			// 取り崩し日数を加算(1日の数以下の時間)
			breakDays += remainAmount % amountPerDay == 0 ? 0 : 1;
			// 残数から取り崩し日数を減算
			remainDays -= breakDays;
			// 残数に取り崩し分を加算
			remainAmount += breakDays * amountPerDay;
		}
		// 残日数及び残数を取得
		return new SimpleEntry<Double, Integer>(remainDays, remainAmount);
	}
	
	/**
	 * 現在の残日数を取得する。<br>
	 * @param dto 休暇付与情報
	 * @return 現在の残日数
	 */
	public static double getCurrentDays(HolidayDataDtoInterface dto) {
		// 現在の残日数を準備
		double currentDays = 0D;
		// 休暇付与情報が存在しない場合
		if (dto == null) {
			// 現在の残日数を取得
			return currentDays;
		}
		// 付与数を加算し破棄数を減算
		currentDays += dto.getGivingDay();
		currentDays -= dto.getCancelDay();
		// 現在の残日数を取得
		return currentDays;
	}
	
	/**
	 * 現在の残時間数を取得する。<br>
	 * @param dto 休暇付与情報
	 * @return 現在の残時間数
	 */
	public static int getCurrentHours(HolidayDataDtoInterface dto) {
		// 現在の残時間数を準備
		int currentHours = 0;
		// 休暇付与情報が存在しない場合
		if (dto == null) {
			// 現在の残時間数を取得
			return currentHours;
		}
		// 付与数を加算し破棄数を減算
		currentHours += dto.getGivingHour();
		currentHours -= dto.getCancelHour();
		// 現在の残時間数を取得
		return currentHours;
	}
	
	/**
	 * 残日数を取得する。<br>
	 * @param dtos 休暇残情報群
	 * @return 残日数
	 */
	public static double getRemainDays(Collection<HolidayRemainDto> dtos) {
		// 残日数を準備
		double days = 0D;
		// 休暇残情報毎に処理
		for (HolidayRemainDto dto : dtos) {
			// 残日数を計算
			days += dto.getRemainDays();
		}
		// 残日数を取得
		return days;
	}
	
	/**
	 * 残時間数を取得する。<br>
	 * @param dtos 休暇残情報群
	 * @return 残時間数
	 */
	public static int getRemainHours(Collection<HolidayRemainDto> dtos) {
		// 残時間数を準備
		int hours = 0;
		// 休暇残情報毎に処理
		for (HolidayRemainDto dto : dtos) {
			// 残時間数を計算
			hours += dto.getRemainHours();
		}
		// 残時間数を取得
		return hours;
	}
	
	/**
	 * 休暇残情報群の残日数及び残時間を合計した休暇残情報を取得する。<br>
	 * 残日数及び残時間以外の情報は、正しく設定されていないため、利用できない。<br>
	 * 有給休暇及びストック休暇の残数確認の際に用いる。<br>
	 * @param remains 休暇残情報群
	 * @return 休暇残情報群の残日数及び残時間を合計した休暇残情報
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static HolidayRemainDto getTotalHolidayRemain(Collection<HolidayRemainDto> remains) throws MospException {
		// 残日数及び残時間を準備
		double remainDays = 0D;
		int remainHours = 0;
		// 休暇残情報毎に処理
		for (HolidayRemainDto remain : remains) {
			// 残日数及び残時間を計算
			remainDays += remain.getRemainDays();
			remainHours += remain.getRemainHours();
		}
		// 休暇残情報群の残日数及び残時間を合計した休暇残情報を準備
		HolidayRemainDto totalRemain = getBareStockHolidayRemain(null, null);
		// 残日数及び残時間を設定
		totalRemain.setRemainDays(remainDays);
		totalRemain.setRemainHours(remainHours);
		// 休暇残情報群の残日数及び残時間を合計した休暇残情報を取得
		return totalRemain;
	}
	
	/**
	 * 有給休暇手動付与日数を取得する。<br>
	 * @param dtos 有給休暇手動付与情報
	 * @return 有給休暇手動付与日数
	 */
	public static double getPaidHolidayManualGivingDays(Collection<PaidHolidayTransactionDtoInterface> dtos) {
		// 手動付与日数を準備
		double days = 0D;
		// 有給休暇手動付与情報毎に処理
		for (PaidHolidayTransactionDtoInterface dto : dtos) {
			// 付与日数を設定
			days += dto.getGivingDay();
		}
		// 手動付与日数を取得
		return days;
	}
	
	/**
	 * 有給休暇手動付与時間数を取得する。<br>
	 * @param dtos 有給休暇手動付与情報
	 * @return 有給休暇手動付与時間数
	 */
	public static int getPaidHolidayManualGivingHours(Collection<PaidHolidayTransactionDtoInterface> dtos) {
		// 手動付与時間数を準備
		int hours = 0;
		// 有給休暇手動付与情報毎に処理
		for (PaidHolidayTransactionDtoInterface dto : dtos) {
			// 付与時間数を設定
			hours += dto.getGivingHour();
		}
		// 手動付与時間数を取得
		return hours;
	}
	
	/**
	 * 有給休暇手動廃棄日数を取得する。<br>
	 * @param dtos 有給休暇手動付与情報
	 * @return 有給休暇手動廃棄日数
	 */
	public static double getPaidHolidayManualCancelDays(Collection<PaidHolidayTransactionDtoInterface> dtos) {
		// 手動廃棄日数を準備
		double days = 0D;
		// 有給休暇手動付与情報毎に処理
		for (PaidHolidayTransactionDtoInterface dto : dtos) {
			// 廃棄日数を設定
			days += dto.getCancelDay();
		}
		// 手動廃棄日数を取得
		return days;
	}
	
	/**
	 * 有給休暇手動廃棄時間数を取得する。<br>
	 * @param dtos 有給休暇手動付与情報
	 * @return 有給休暇手動廃棄時間数
	 */
	public static int getPaidHolidayManualCancelHours(Collection<PaidHolidayTransactionDtoInterface> dtos) {
		// 手動廃棄時間数を準備
		int hours = 0;
		// 有給休暇手動付与情報毎に処理
		for (PaidHolidayTransactionDtoInterface dto : dtos) {
			// 廃棄時間数を設定
			hours += dto.getCancelHour();
		}
		// 手動廃棄時間数を取得
		return hours;
	}
	
	/**
	 * 有給休暇手動付与日数(廃棄を考慮)を取得する。<br>
	 * @param dtos 有給休暇手動付与情報
	 * @return 有給休暇手動付与日数
	 */
	public static double getPaidHolidayManualDays(Collection<PaidHolidayTransactionDtoInterface> dtos) {
		// 手動付与日数(廃棄を考慮)を取得
		return getPaidHolidayManualGivingDays(dtos) - getPaidHolidayManualCancelDays(dtos);
	}
	
	/**
	 * 有給休暇手動付与時間数(廃棄を考慮)を取得する。<br>
	 * @param dtos 有給休暇手動付与情報
	 * @return 有給休暇手動付与時間数
	 */
	public static int getPaidHolidayManualHours(Collection<PaidHolidayTransactionDtoInterface> dtos) {
		// 手動付与時間数(廃棄を考慮)を取得
		return getPaidHolidayManualGivingHours(dtos) - getPaidHolidayManualCancelHours(dtos);
	}
	
	/**
	 * ストック休暇手動付与日数を取得する。<br>
	 * @param dtos ストック休暇手動付与情報
	 * @return ストック休暇手動付与日数
	 */
	public static double getStockHolidayManualGivingDays(Collection<StockHolidayTransactionDtoInterface> dtos) {
		// 手動付与日数を準備
		double days = 0D;
		// ストック休暇手動付与情報毎に処理
		for (StockHolidayTransactionDtoInterface dto : dtos) {
			// 付与日数を設定
			days += dto.getGivingDay();
		}
		// 手動付与日数を取得
		return days;
	}
	
	/**
	 * ストック休暇手動廃棄日数を取得する。<br>
	 * @param dtos ストック休暇手動付与情報
	 * @return ストック休暇手動廃棄日数
	 */
	public static double getStockHolidayManualCancelDays(Collection<StockHolidayTransactionDtoInterface> dtos) {
		// 手動廃棄日数を準備
		double days = 0D;
		// ストック休暇手動付与情報毎に処理
		for (StockHolidayTransactionDtoInterface dto : dtos) {
			// 廃棄日数を設定
			days += dto.getCancelDay();
		}
		// 手動廃棄日数を取得
		return days;
	}
	
	/**
	 * ストック休暇手動付与日数(廃棄を考慮)を取得する。<br>
	 * @param dtos 有給休暇手動付与情報
	 * @return ストック休暇手動付与日数
	 */
	public static double getStockHolidayManualDays(Collection<StockHolidayTransactionDtoInterface> dtos) {
		// 手動付与日数(廃棄を考慮)を取得
		return getStockHolidayManualGivingDays(dtos) - getStockHolidayManualCancelDays(dtos);
	}
	
	/**
	 * 有給休暇情報が標準のもの(有効日と取得日が引数の付与日と同じ)であるかを確認する。<br>
	 * @param dto       有給休暇情報
	 * @param grantDate 付与日
	 * @return 確認結果(true：標準のもの(有効日と取得日が引数の付与日と同じ)である、false：そうでない)
	 */
	public static boolean isRegularPaidHoliday(PaidHolidayDataDtoInterface dto, Date grantDate) {
		// 引数が不足する場合
		if (MospUtility.isEmpty(dto, grantDate)) {
			// 標準のものでないと判断
			return false;
		}
		// 有効日と引数の付与日が同じでない場合
		if (DateUtility.isSame(dto.getActivateDate(), grantDate) == false) {
			// 標準のものでないと判断
			return false;
		}
		// 取得日と引数の付与日が同じであるかを確認
		return DateUtility.isSame(dto.getAcquisitionDate(), grantDate);
	}
	
	/**
	 * 有給休暇情報群に標準の有給休暇情報(有効日と取得日が引数の付与日と同じ)があるかを確認する。<br>
	 * @param dtos      有給休暇情報群
	 * @param grantDate 付与日
	 * @return 確認結果(true：標準の有給休暇情報(有効日と取得日が引数の付与日と同じ)がある、false：そうでない)
	 */
	public static boolean hasRegularPaidHoliday(Collection<PaidHolidayDataDtoInterface> dtos, Date grantDate) {
		// 有給休暇情報毎に処理
		for (PaidHolidayDataDtoInterface dto : dtos) {
			// 有給休暇情報が標準のもの(有効日と取得日が引数の付与日と同じ)である場合
			if (isRegularPaidHoliday(dto, grantDate)) {
				// 標準の有給休暇情報(有効日と取得日が引数の付与日と同じ)があると判断
				return true;
			}
		}
		// 標準の有給休暇情報(有効日と取得日が引数の付与日と同じ)が無いと判断
		return false;
	}
	
	/**
	 * 休暇使用日数を取得する。<br>
	 * @param dtos 休暇申請情報群
	 * @return 休暇使用日数
	 */
	public static double getHolidayUseDays(Collection<HolidayRequestDtoInterface> dtos) {
		// 休暇使用日数を準備
		double days = 0D;
		// 休暇申請情報情報毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// 休暇使用日数を計算
			days += dto.getUseDay();
		}
		// 休暇使用日数を取得
		return days;
	}
	
	/**
	 * 休暇使用時間数を取得する。<br>
	 * @param dtos 有給休暇手動付与情報
	 * @return 休暇使用時間数
	 */
	public static int getHolidayUseHours(Collection<HolidayRequestDtoInterface> dtos) {
		// 休暇使用時間数を準備
		int hours = 0;
		// 休暇申請情報情報毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// 休暇使用時間数を加算
			hours += dto.getUseHour();
		}
		// 休暇使用時間数を取得
		return hours;
	}
	
	/**
	 * 休暇種別情報の給与区分が有給であるかを確認する。<br>
	 * <br>
	 * @param dto 休暇種別情報
	 * @return 確認結果(true：休暇種別情報の給与区分が有給である、false：そうでない)
	 */
	public static boolean isSalaryTypePay(HolidayDtoInterface dto) {
		// 休暇種別情報の給与区分が有給であるかを確認
		return MospUtility.isEmpty(dto) == false && dto.getSalary() == TimeConst.TYPE_SALARY_PAY;
	}
	
	/**
	 * 休暇種別情報の付与日数無制限が無制限であるかを確認する。<br>
	 * <br>
	 * @param dto 休暇種別情報
	 * @return 確認結果(true：休暇種別情報の付与日数無制限が無制限である、false：そうでない)
	 */
	public static boolean isUnlimited(HolidayDtoInterface dto) {
		// 休暇種別情報の付与日数無制限が無制限であるかを確認
		return MospUtility.isEmpty(dto) == false && MospUtility.isChecked(dto.getNoLimit());
	}
	
	/**
	 * 休暇種別情報の連続取得が必須であるかを確認する。<br>
	 * <br>
	 * @param dto 休暇種別情報
	 * @return 確認結果(true：休暇種別情報の連続取得が必須である、false：そうでない)
	 */
	public static boolean isForcedConsecutive(HolidayDtoInterface dto) {
		// 休暇種別情報の連続取得が必須であるかを確認
		return MospUtility.isEmpty(dto) == false
				&& dto.getContinuousAcquisition() == TimeConst.TYPE_CONTINUOUS_NECESSARY;
	}
	
	/**
	 * 休暇種別管理情報群から対象の休暇種別管理情報を取得する。<br>
	 * <br>
	 * 対象の休暇種別管理情報が存在しない場合はnullを返す。<br>
	 * <br>
	 * @param holidays    休暇種別管理情報群
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 休暇種別管理情報
	 */
	public static HolidayDtoInterface getHolidayDto(Collection<HolidayDtoInterface> holidays, String holidayCode,
			int holidayType) {
		// 休暇種別管理情報毎に処理
		for (HolidayDtoInterface dto : holidays) {
			// 休暇種別管理情報が対象の休暇コード及び休暇区分のものである場合
			if (isTheHoliday(dto, holidayCode, holidayType)) {
				// 休暇種別管理情報を取得
				return dto;
			}
		}
		// nullを取得(対象の休暇種別管理情報が存在しない場合)
		return null;
	}
	
	/**
	 * 休暇種別管理情報が対象の休暇コード及び休暇区分のものであるかを確認する。<br>
	 * @param dto         休暇種別管理情報
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 確認結果(true：対象のものである、false：そうでない)
	 */
	public static boolean isTheHoliday(HolidayDtoInterface dto, String holidayCode, int holidayType) {
		// 休暇種別管理情報が存在しない場合
		if (dto == null) {
			// そうでないと判断
			return false;
		}
		// 休暇種別管理情報が対象の休暇コード及び休暇区分のものであるかを確認
		return MospUtility.isEqual(dto.getHolidayCode(), holidayCode) && dto.getHolidayType() == holidayType;
	}
	
	/**
	 * 対象の休暇コード及び休暇区分が有給休暇であるかを確認する。<br>
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 確認結果(true：有給休暇である、false：そうでない)
	 */
	public static boolean isPaidHoliday(String holidayCode, int holidayType) {
		// 対象の休暇コード及び休暇区分が有給休暇であるかを確認
		return MospUtility.isEqual(TimeConst.CODE_HOLIDAYTYPE2_PAID, holidayCode)
				&& TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == holidayType;
	}
	
	/**
	 * 対象の休暇コード及び休暇区分がストック休暇であるかを確認する。<br>
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @return 確認結果(true：ストックである、false：そうでない)
	 */
	public static boolean isStockHoliday(String holidayCode, int holidayType) {
		// 対象の休暇コード及び休暇区分が有給休暇であるかを確認
		return MospUtility.isEqual(TimeConst.CODE_HOLIDAYTYPE2_STOCK, holidayCode)
				&& TimeConst.CODE_HOLIDAYTYPE_HOLIDAY == holidayType;
	}
	
	/**
	 * 対象の休暇種別の時間単位取得が有効であるかを確認する。<br>
	 * @param dto 休暇種別管理情報
	 * @return 確認結果(true：時間単位取得が有効である、false：そうでない)
	 */
	public static boolean isHourlyHolidayAvailable(HolidayDtoInterface dto) {
		// 休暇種別管理情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 時間単位取得が有効でないと判断
			return false;
		}
		// 時間単位取得が有効であるかを確認
		return PlatformUtility.isActivate(dto.getTimelyHolidayFlag());
	}
	
	/**
	 * 休暇略称を取得する。<br>
	 * @param holidays    休暇種別情報群
	 * @param holidayCode 休暇コード
	 * @param holidayType 休暇区分
	 * @param mospParams  MosP処理情報
	 * @return 休暇略称
	 */
	public static String getHolidayAbbr(Collection<HolidayDtoInterface> holidays, String holidayCode, int holidayType,
			MospParams mospParams) {
		// 休暇種別管理情報郡から対象の休暇種別管理情報を取得
		HolidayDtoInterface dto = getHolidayDto(holidays, holidayCode, holidayType);
		// 休暇種別管理情報が存在する場合
		if (dto != null) {
			// 休暇略称を取得
			return dto.getHolidayAbbr();
		}
		// 有給休暇である場合
		if (isPaidHoliday(holidayCode, holidayType)) {
			// 有給休暇名称(略称)を取得
			return TimeNamingUtility.paidHolidayAbbr(mospParams);
		}
		// ストック休暇である場合
		if (isStockHoliday(holidayCode, holidayType)) {
			// ストック休暇名称(略称)を取得
			return TimeNamingUtility.getStockHolidayAbbr(mospParams);
		}
		// 空文字を取得
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * 休暇範囲略称を取得する。<br>
	 * @param mospParams   MosP処理情報
	 * @param holidayRange 休暇範囲
	 * @return 休暇範囲略称
	 */
	public static String getHolidayRangeAbbr(MospParams mospParams, int holidayRange) {
		// 休暇範囲略称を準備
		String holidayRangeAbbr = MospConst.STR_EMPTY;
		// 休暇範囲に応じて略称を設定
		switch (holidayRange) {
			case TimeConst.CODE_HOLIDAY_RANGE_ALL:
				// 全休の場合
				holidayRangeAbbr = TimeNamingUtility.holidayRangeAll(mospParams);
				break;
			case TimeConst.CODE_HOLIDAY_RANGE_AM:
				// 前休の場合
				holidayRangeAbbr = TimeNamingUtility.holidayRangeFrontAbbr(mospParams);
				break;
			case TimeConst.CODE_HOLIDAY_RANGE_PM:
				// 後休の場合
				holidayRangeAbbr = TimeNamingUtility.holidayRangeBackAbbr(mospParams);
				break;
			case TimeConst.CODE_HOLIDAY_RANGE_TIME:
				// 時休の場合
				holidayRangeAbbr = TimeNamingUtility.holidayRangeHourAbbr(mospParams);
				break;
			default:
				holidayRangeAbbr = MospConst.STR_EMPTY;
		}
		// 休暇範囲略称を取得
		return holidayRangeAbbr;
	}
	
	/**
	 * 休暇数を取得する。<br>
	 * @param holidayRange 休暇範囲
	 * @return 休暇数
	 */
	public static float getHolidayTimes(int holidayRange) {
		// 休暇数を準備
		float holidayTimes = 0F;
		// 休暇範囲に応じて略称を設定
		switch (holidayRange) {
			case TimeConst.CODE_HOLIDAY_RANGE_ALL:
				// 全休の場合
				holidayTimes = TimeConst.HOLIDAY_TIMES_ALL;
				break;
			case TimeConst.CODE_HOLIDAY_RANGE_AM:
			case TimeConst.CODE_HOLIDAY_RANGE_PM:
				// 半休の場合
				holidayTimes = TimeConst.HOLIDAY_TIMES_HALF;
				break;
			default:
				holidayTimes = 0F;
		}
		// 休暇数を取得
		return holidayTimes;
	}
	
	/**
	 * 文字列(日時間分)を取得する。<br>
	 * 休暇残数等に用いる。<br>
	 * @param mospParams   MosP処理情報
	 * @param days         日数
	 * @param hours        時間数
	 * @param minutes      分数
	 * @param isZeroHyphen ハイフン変換フラグ(true：0日0時間を-に変換、false：変換しない)
	 * @return 文字列(日時間分)
	 */
	public static String getStringDaysHoursMinutes(MospParams mospParams, double days, int hours, int minutes,
			boolean isZeroHyphen) {
		// 0日0時間で-に変換する場合
		if (isZeroHyphen && days == 0D && hours == 0 && minutes == 0) {
			// -を取得
			return PfNameUtility.hyphen(mospParams);
		}
		// 文字列を作成
		StringBuilder sb = new StringBuilder();
		// 日を設定
		sb.append(getStringNumber(mospParams, days, 1, RoundingMode.HALF_UP));
		sb.append(PfNameUtility.day(mospParams));
		// 時間数が0でない場合
		if (hours != 0) {
			// 時間数を追加
			sb.append(hours);
			sb.append(PfNameUtility.time(mospParams));
		}
		// 分数が0でない場合
		if (minutes != 0) {
			// 時間数を追加
			sb.append(minutes);
			sb.append(PfNameUtility.minutes(mospParams));
		}
		// 文字列を取得
		return sb.toString();
	}
	
	/**
	 * VOから有効日(一括更新)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 有効日(検索)
	 */
	public static Date getUpdateActivateDate(MospParams mospParams) {
		// VO準備
		TimeVo vo = (TimeVo)mospParams.getVo();
		// 有効日取得
		return getDate(mospParams, vo.getTxtUpdateActivateYear(), vo.getTxtUpdateActivateMonth(),
				vo.getTxtUpdateActivateDay());
	}
	
	/**
	 * 日付オブジェクトを取得する(String→Date)。<br>
	 * 日付オブジェクトの取得に失敗した場合は、
	 * mospParamsにエラーメッセージを追加する。<br>
	 * @param mospParams MosP処理情報
	 * @param year 年
	 * @param month 月
	 * @param day 日
	 * @return 日付
	 */
	public static Date getDate(MospParams mospParams, String year, String month, String day) {
		try {
			return DateUtility.getDate(year, month, day);
		} catch (Throwable e) {
			PfMessageUtility.addErrorCheckDate(mospParams);
			return null;
		}
	}
	
	/**
	 * 時間文字列(時separator分suffix)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param time       時間(分)
	 * @param separator  区切り文字
	 * @param suffix     接尾文字
	 * @return 時間文字列(時separator分suffix)
	 */
	public static String getStringTime(MospParams mospParams, int time, String separator, String suffix) {
		// 時間文字列を準備
		StringBuilder sb = new StringBuilder();
		// 時及び分を取得
		int hours = getHours(time);
		int minutes = getMinutes(time);
		// 時間(分)が負であり時が0である場合
		if (time < 0 && hours == 0) {
			// マイナスを付加
			sb.append(PfNameUtility.hyphen(mospParams));
		}
		// 時(時間(分)/60の商)を追加
		sb.append(hours);
		// 区切り文字を追加
		sb.append(separator);
		// 数値フォーマットを取得
		NumberFormat format = NumberFormat.getNumberInstance();
		// 整数部分最小桁数を設定
		format.setMinimumIntegerDigits(2);
		// 分(時間(分)/60の余り)を追加
		sb.append(format.format(minutes));
		// 接尾文字を追加
		sb.append(suffix);
		// 時間文字列(時separator分suffix)を取得
		return sb.toString();
	}
	
	/**
	 * 時間文字列(時separator分)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param time      時間(分)
	 * @param separator 区切り文字
	 * @return 時間文字列(時separator分)
	 */
	public static String getStringTime(MospParams mospParams, int time, String separator) {
		// 時間文字列(時separator分)を取得
		return getStringTime(mospParams, time, separator, MospConst.STR_EMPTY);
	}
	
	/**
	 * 時間文字列(時.分)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param time       時間(分)
	 * @return 時間文字列(時.分)
	 */
	public static String getStringPeriodTime(MospParams mospParams, int time) {
		//  時間文字列(時.分)を取得
		return getStringTime(mospParams, time, PfNameUtility.period(mospParams));
	}
	
	/**
	 * 時間文字列(時.分)を取得する。<br>
	 * 勤怠一覧等で用いられる。<br>
	 * @param mospParams MosP処理情報
	 * @param time       時間(分)
	 * @param needHyphen ゼロ時ハイフン表示要否(true：ゼロ時ハイフン、false：ゼロ時はゼロ)
	 * @return 時間文字列(時.分)
	 */
	public static String getStringPeriodTimeOrHyphen(MospParams mospParams, Integer time, boolean needHyphen) {
		// 時間がnllである場合
		if (time == null) {
			// 時間がnullならハイフン
			return PfNameUtility.hyphen(mospParams);
		}
		// ゼロ時ハイフン表示要でゼロである場合
		if (needHyphen && time.intValue() == 0) {
			// ハイフンを取得
			return PfNameUtility.hyphen(mospParams);
		}
		//  時間文字列(時.分)を取得
		return getStringPeriodTime(mospParams, time.intValue());
	}
	
	/**
	 * 時間文字列(時:分)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param time       時間(分)
	 * @return 時間文字列(時:分)
	 */
	public static String getStringColonTime(MospParams mospParams, int time) {
		//  時間文字列(時:分)を取得
		return getStringTime(mospParams, time, PfNameUtility.singleColon(mospParams));
	}
	
	/**
	 * 時間文字列(時時間分分)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param time       時間(分)
	 * @return 時間文字列(時時間分分)
	 */
	public static String getStringJpTime(MospParams mospParams, int time) {
		// 時間文字列(時時間分分)を取得
		return getStringTime(mospParams, time, PfNameUtility.time(mospParams), PfNameUtility.minutes(mospParams));
	}
	
	/**
	 * 時間文字列(時時間分分)を取得する。<br>
	 * 但し分が0である場合は、時間文字列(時時間)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param time       時間(分)
	 * @return 時間文字列(時時間分分)
	 */
	public static String getStringHourJpTime(MospParams mospParams, int time) {
		// 時間文字列(時時間分分)を取得
		StringBuilder sb = new StringBuilder(getStringJpTime(mospParams, time));
		// 分が0である場合
		if (getMinutes(time) == 0) {
			// 00分を除去
			sb.delete(sb.length() - 2 - PfNameUtility.minutes(mospParams).length(), sb.length());
		}
		// 時間文字列を取得
		return sb.toString();
	}
	
	/**
	 * 時間文字列(時.分(回数))を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param time       時間(分)
	 * @param times      回数
	 * @param fraction   小数点以下の桁数(回数用)
	 * @return 時間文字列(時.分(回数))
	 */
	public static String getStringPeriodTimeAndTimes(MospParams mospParams, int time, double times, int fraction) {
		// 時間文字列(時.分(回数))を準備
		StringBuilder sb = new StringBuilder(getStringPeriodTime(mospParams, time));
		// 回数を追加
		sb.append(PfNameUtility.frontParentheses(mospParams));
		sb.append(TimeUtility.getStringNumber(mospParams, times, fraction, RoundingMode.HALF_UP));
		sb.append(PfNameUtility.backParentheses(mospParams));
		//  時間文字列(時.分(回数))を取得
		return sb.toString();
	}
	
	/**
	 * 数値文字列を取得する。<br>
	 * @param mospParams   MosP処理情報
	 * @param number       数値
	 * @param fraction     小数点以下の桁数
	 * @param roundingMode 適用する丸めモード
	 * @return 数値(文字列)
	 */
	public static String getStringNumber(MospParams mospParams, double number, int fraction,
			RoundingMode roundingMode) {
		// フォーマットを準備
		StringBuilder sb = new StringBuilder("0");
		// 小数点以下も表示する場合
		if (fraction > 0) {
			// 小数点を追加
			sb.append(PfNameUtility.period(mospParams));
		}
		// 小数点以下のフォーマット文字列を作成
		for (int i = 0; i < fraction; i++) {
			sb.append("0");
		}
		// フォーマット準備
		DecimalFormat format = new DecimalFormat(sb.toString());
		// フォーマット
		return format.format(round(number, fraction, roundingMode));
	}
	
	/**
	 * 数値文字列を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param number     数値
	 * @param fraction   小数点以下の桁数
	 * @param needHyphen ゼロ時ハイフン表示要否(true：ゼロ時ハイフン、false：ゼロ時はゼロ)
	 * @return 時間文字列(時.分)
	 */
	public static String getStringNumberOrHyphen(MospParams mospParams, Double number, int fraction,
			boolean needHyphen) {
		// 数値がnllである場合
		if (number == null) {
			// 数値がnullならハイフン
			return PfNameUtility.hyphen(mospParams);
		}
		// ゼロ時ハイフン表示要でゼロである場合
		if (needHyphen && number.doubleValue() == 0D) {
			// ハイフンを取得
			return PfNameUtility.hyphen(mospParams);
		}
		//  時間文字列(時.分)を取得
		return getStringNumber(mospParams, number.doubleValue(), fraction, RoundingMode.HALF_UP);
	}
	
	/**
	 * 勤怠計算用の時刻(0:00からの分)を取得する。<br>
	 * <br>
	 * 時刻は0分から2879分(47時間59分)とする。<br>
	 * <br>
	 * @param minutes 時刻(0:00からの分)
	 * @return 時刻(0:00からの分)(0から)
	 */
	public static int getAttendanceMinutes(int minutes) {
		// 時刻(0:00からの分)が0よりも小さい場合
		if (minutes < 0) {
			// 0を取得
			return 0;
		}
		// 時刻(0:00からの分)が勤怠計算用の時間(分)の上限よりも大きい場合
		if (MAX_ATTENDANCE_MINUTES < minutes) {
			// 勤怠計算用の時間(分)の上限を取得
			return MAX_ATTENDANCE_MINUTES;
		}
		// 時刻(0:00からの分)を取得
		return minutes;
	}
	
	/**
	 * 勤怠計算用の時刻(0:00からの分)を取得する。<br>
	 * 時刻(日時)がnullである場合は、負の値(最小値)を返す。<br>
	 * @param time     時刻(日時)
	 * @param workDate 勤務日
	 * @return 時刻(0:00からの分)
	 */
	public static int getAttendMinutes(Date time, Date workDate) {
		// 時刻(日時)がnullである場合
		if (time == null) {
			// 負の値(最小値)を取得
			return Integer.MIN_VALUE;
		}
		// 時刻(0:00からの分)を取得
		return TimeUtility.getMinutes(time, workDate);
	}
	
	/**
	 * 時間(分)から時(時間(分)/60の商)を取得する。<br>
	 * @param time 時間(分)
	 * @return 時
	 */
	public static int getHours(int time) {
		// 時間(分)を60で割った値を取得(余りは切り捨て)
		return time / TimeConst.CODE_DEFINITION_HOUR;
	}
	
	/**
	 * 時間(分)から分(時間(分)/60の余り)を取得する。<br>
	 * @param time 時間(分)
	 * @return 分
	 */
	public static int getMinutes(int time) {
		// 時間(分)を60で割った値を取得(余りは切り捨て)
		return Math.abs(time) % TimeConst.CODE_DEFINITION_HOUR;
	}
	
	/**
	 * 時(文字列)及び分(文字列)から分を取得する。<br>
	 * @param hours   時(文字列)
	 * @param minutes 分(文字列)
	 * @return 時分から取得した分
	 */
	public static int getTime(int hours, int minutes) {
		// 分を取得
		return hours * TimeConst.CODE_DEFINITION_HOUR + minutes;
	}
	
	/**
	 * 時(文字列)及び分(文字列)から分を取得する。<br>
	 * @param hours   時(文字列)
	 * @param minutes 分(文字列)
	 * @return 時分から取得した分
	 */
	public static int getTime(String hours, String minutes) {
		// 時(文字列)及び分(文字列)を数値に変換
		int intHours = MospUtility.getInt(hours);
		int intMinutes = MospUtility.getInt(minutes);
		// 分を取得
		return getTime(intHours, intMinutes);
	}
	
	/**
	 * 時(文字列)及び分(文字列)から分を取得する。<br>
	 * @param hours   時(文字列)
	 * @return 時分から取得した分
	 */
	public static int getTime(String hours) {
		// 分を取得
		return getTime(hours, MospConst.STR_EMPTY);
	}
	
	/**
	 * 時刻が未設定か否かを判定する。
	 * @param time 時刻
	 * @return true:時刻が未設定、false:時刻が設定されている
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static boolean isNullTime(Date time) throws MospException {
		return isNullTime(time, DateUtility.getDefaultTime());
	}
	
	/**
	 * 時刻が未設定か否かを判定する。
	 * @param time 時刻
	 * @param targetDate 基準日
	 * @return true:時刻が未設定、false:時刻が設定されている
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static boolean isNullTime(Date time, Date targetDate) throws MospException {
		// equals()は、java.sql.TimeStampのequals()の実装に問題があるので、使わないこと
		return (time == null || time.getTime() == targetDate.getTime());
	}
	
	/**
	 * 時間間隔を取得する。<br>
	 * 開始時刻の日付を対象日(基準)とする。<br>
	 * <br>
	 * @param startTime 開始時刻
	 * @param endTime   終了時刻
	 * @return 時間間隔
	 */
	public static TimeDuration getDuration(Date startTime, Date endTime) {
		// 対象日(基準)を準備
		Date targetDate = DateUtility.getDate(startTime);
		// 時間間隔を取得
		return TimeDuration.getInstance(getMinutes(startTime, targetDate), getMinutes(endTime, targetDate));
	}
	
	/**
	 * 時間間隔を取得する。<br>
	 * 開始時刻をデフォルト日付とする。<br>
	 * <br>
	 * @param time 時刻
	 * @return 時間間隔
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static TimeDuration getDuration(Date time) throws MospException {
		// 時間間隔を取得
		return getDuration(DateUtility.getDefaultTime(), time);
	}
	
	/**
	 * 時間間隔群を統合する。<br>
	 * 妥当でない時間間隔は、省かれる。<br>
	 * 重複している箇所は、省かれる。<br>
	 * 開始時間と終了時間が接している時間間隔は、一つに結合する。<br>
	 * <br>
	 * @param durations1 時間間隔群1(キー：開始時刻)
	 * @param durations2 時間間隔群2(キー：開始時刻)
	 * @return 統合した時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> mergeDurations(Map<Integer, TimeDuration> durations1,
			Map<Integer, TimeDuration> durations2) {
		// 時間間隔1及び2を結合
		Map<Integer, TimeDuration> combine1 = combineDurations(durations1);
		Map<Integer, TimeDuration> combine2 = combineDurations(durations2);
		// 時間間隔群1か時間間隔群2が空である場合
		if (combine1.isEmpty() || combine2.isEmpty()) {
			// 時間間隔群1に時間間隔群2を統合して取得
			combine1.putAll(combine2);
			return combine1;
		}
		// 時間間隔(時間間隔群2を結合したもの)毎に処理
		for (TimeDuration duration2 : combineDurations(durations2).values()) {
			// 時間間隔1と重複していない時間間隔群を取得して時間間隔1に追加
			combine1.putAll(duration2.getNotOverlap(combine1));
		}
		// 統合した時間間隔群(キー：開始時刻(キー順))を取得(再度結合)
		return combineDurations(combine1);
	}
	
	/**
	 * 時間間隔群を統合する。<br>
	 * {@link TimeUtility#mergeDurations(Map, Map)}を参照。<br>
	 * <br>
	 * @param durations1 時間間隔群1(キー：開始時刻)
	 * @param durations2 時間間隔群2(キー：開始時刻)
	 * @param durations3 時間間隔群3(キー：開始時刻)
	 * @return 統合した時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> mergeDurations(Map<Integer, TimeDuration> durations1,
			Map<Integer, TimeDuration> durations2, Map<Integer, TimeDuration> durations3) {
		// まずは時間間隔群1と時間間隔群2を統合
		Map<Integer, TimeDuration> durations12 = mergeDurations(durations1, durations2);
		// さらに時間間隔3と統合
		return mergeDurations(durations12, durations3);
	}
	
	/**
	 * 時間間隔群を統合する。<br>
	 * {@link TimeUtility#mergeDurations(Map, Map)}を参照。<br>
	 * <br>
	 * @param durations 時間間隔群(キー：開始時刻)
	 * @param duration  時間間隔
	 * @return 統合した時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> mergeDurations(Map<Integer, TimeDuration> durations,
			TimeDuration duration) {
		// 時間間隔を時間間隔群として取得
		Map<Integer, TimeDuration> durations2 = new TreeMap<Integer, TimeDuration>();
		durations2.put(duration.getStartTime(), duration);
		// 統合
		return mergeDurations(durations, durations2);
	}
	
	/**
	 * 時間間隔群を統合する。<br>
	 * {@link TimeUtility#mergeDurations(Map, TimeDuration)}を参照。<br>
	 * <br>
	 * @param durations 時間間隔群
	 * @return 統合した時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> mergeDurations(Collection<TimeDuration> durations) {
		// 統合した時間間隔群(キー：開始時刻(キー順))を準備
		Map<Integer, TimeDuration> merged = new TreeMap<Integer, TimeDuration>();
		// 時間間隔毎に処理
		for (TimeDuration duration : durations) {
			// 統合した時間間隔群(キー：開始時刻(キー順))に統合
			merged = mergeDurations(merged, duration);
		}
		// 統合した時間間隔群(キー：開始時刻(キー順))を取得
		return merged;
	}
	
	/**
	 * 時間間隔群を開始時刻で並べ替える。<br>
	 * 妥当でない時間間隔は、省かれる。<br>
	 * <br>
	 * @param durations 時間間隔群(キー：開始時刻)
	 * @return 時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> sortDurations(Map<Integer, TimeDuration> durations) {
		// 時間間隔群(キー：開始時刻(キー順))を準備
		Map<Integer, TimeDuration> map = new TreeMap<Integer, TimeDuration>();
		// 時間間隔群が空である場合
		if (MospUtility.isEmpty(durations)) {
			// 空の時間間隔群を取得
			return map;
		}
		
		// 時間間隔毎に処理
		for (TimeDuration duration : durations.values()) {
			// 時間間隔が妥当でない場合
			if (duration.isValid() == false) {
				// 次の時間間隔へ
				continue;
			}
			// 時間間隔群(キー：開始時刻(キー順))へ追加
			map.put(duration.getStartTime(), duration);
		}
		// 時間間隔群(キー：開始時刻(キー順))を取得
		return map;
	}
	
	/**
	 * 時間間隔を結合する。<br>
	 * 開始時間と終了時間が接している時間間隔を一つに結合する。<br>
	 * 開始時間と終了時間が重複している時間間隔を一つに結合する。<br>
	 * 時間間隔群を開始時刻で並べ替える。<br>
	 * 妥当でない時間間隔は、省かれる。<br>
	 * <br>
	 * @param durations 時間間隔群(キー：開始時刻)
	 * @return 結合した時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> combineDurations(Map<Integer, TimeDuration> durations) {
		// 時間間隔群(キー：開始時刻(キー順))を準備
		Map<Integer, TimeDuration> map = new TreeMap<Integer, TimeDuration>();
		// 時間間隔(並べ替え後)毎に処理
		for (TimeDuration duration : sortDurations(durations).values()) {
			// 開始時刻及び終了時刻を準備
			int startTime = duration.getStartTime();
			int endTime = duration.getEndTime();
			// 最後の時間間隔を取得
			TimeDuration lastDuration = MospUtility.getLastValue(map.values());
			// 最後の時間間隔が存在しない場合
			if (lastDuration == null) {
				// 時間間隔群に時間間隔を追加
				map.put(startTime, TimeDuration.getInstance(startTime, endTime));
				// 次の時間間隔へ
				continue;
			}
			// 連続する場合
			if (startTime == lastDuration.getEndTime()) {
				// 時間間隔群に時間間隔(連続)を追加
				map.put(lastDuration.getStartTime(), TimeDuration.getInstance(lastDuration.getStartTime(), endTime));
				// 次の時間間隔へ
				continue;
			}
			// 重複する場合
			if (lastDuration.getOverlap(duration).isValid()) {
				// 終了時刻を調整
				endTime = endTime < lastDuration.getEndTime() ? lastDuration.getEndTime() : endTime;
				// 時間間隔群に時間間隔を追加
				map.put(lastDuration.getStartTime(), TimeDuration.getInstance(lastDuration.getStartTime(), endTime));
				// 次の時間間隔へ
				continue;
			}
			// 連続も重複もしない場合(時間間隔群に時間間隔を追加)
			map.put(startTime, TimeDuration.getInstance(startTime, endTime));
		}
		// 時間間隔群(キー：開始時刻(キー順))を取得
		return map;
	}
	
	/**
	 * 時間間隔群の総時間(分)を取得する。<br>
	 * @param durations 時間間隔群(キー：開始時刻)
	 * @return 総時間(分)
	 */
	public static int getMinutes(Map<Integer, TimeDuration> durations) {
		// 総時間(分)を準備
		int minutes = 0;
		// 時間間隔毎に処理
		for (TimeDuration duration : combineDurations(durations).values()) {
			// 時間を加算
			minutes += duration.getMinutes();
		}
		// 総時間(分)を取得
		return minutes;
	}
	
	/**
	 * 到達時刻(0:00からの分)を取得する。<br>
	 * 開始時刻から除外時間を除いて、
	 * 到達時間(分)が経った時刻(0:00からの分)を取得する。<br>
	 * <br>
	 * @param startTime 開始時刻(0:00からの分)
	 * @param minutes   到達時間(分)
	 * @param excludes  除外時間(0:00からの分)(キー：開始時刻)
	 * @return 到達時刻(0:00からの分)
	 */
	public static int getReachTime(int startTime, int minutes, Map<Integer, TimeDuration> excludes) {
		// 現在時刻及び経過時間(分)を準備(開始時刻からスタート)
		int current = startTime;
		int progress = 0;
		// 到達時間(分)が0以下である場合
		if (minutes <= 0) {
			// 開始時刻を取得
			return startTime;
		}
		// 除外時間(並べ替え)毎に処理
		for (TimeDuration duration : combineDurations(excludes).values()) {
			// 現在時刻が除外時間に含まれる場合
			if (duration.isContain(current)) {
				// 現在時刻を加算
				current = duration.getEndTime();
				// 次の除外時間へ
				continue;
			}
			// 現在時刻が除外開始時刻より前である場合
			if (current < duration.getStartTime()) {
				// 経過時間及び現在時刻(分)を加算
				progress += duration.getStartTime() - current;
				current = duration.getEndTime();
			}
			// 経過時間(分)が到達時間(分)に達しない場合
			if (progress < minutes) {
				// 次の除外時間へ
				continue;
			}
			// 経過時間(分)が到達時間(分)に達した場合
			// 到達時刻(0:00からの分)を取得
			return duration.getStartTime() - (progress - minutes);
		}
		// 到達時刻(0:00からの分)を取得
		return current + (minutes - progress);
	}
	
	/**
	 * 対象時刻より前の時間間隔群を取得する。<br>
	 * <br>
	 * @param durations  時間間隔群
	 * @param targetTime 対象時刻(0:00からの分)
	 * @return 対象時刻より前の時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> getBeforeTimes(Map<Integer, TimeDuration> durations, int targetTime) {
		// 対象時刻より前の時間間隔群を準備
		Map<Integer, TimeDuration> beforeTimes = new TreeMap<Integer, TimeDuration>();
		// 時間間隔毎に処理
		for (TimeDuration duration : combineDurations(durations).values()) {
			// 対象時刻より前の時間間隔を取得
			TimeDuration beforeTime = duration.getBeforeTime(targetTime);
			// 対象時刻より前の時間間隔群に追加
			beforeTimes.put(beforeTime.getStartTime(), beforeTime);
		}
		// 対象時刻より前の時間間隔群を取得
		return combineDurations(beforeTimes);
	}
	
	/**
	 * 対象時刻より後の時間間隔群を取得する。<br>
	 * <br>
	 * @param durations  時間間隔群
	 * @param targetTime 対象時刻(0:00からの分)
	 * @return 対象時刻より後の時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> getAfterTimes(Map<Integer, TimeDuration> durations, int targetTime) {
		// 対象時刻より後の時間間隔群を準備
		Map<Integer, TimeDuration> afterTimes = new TreeMap<Integer, TimeDuration>();
		// 時間間隔毎に処理
		for (TimeDuration duration : combineDurations(durations).values()) {
			// 対象時刻より後の時間間隔を取得
			TimeDuration afterTime = duration.getAfterTime(targetTime);
			// 対象時刻より後の時間間隔群に追加
			afterTimes.put(afterTime.getStartTime(), afterTime);
		}
		// 対象時刻より後の時間間隔群を取得
		return combineDurations(afterTimes);
	}
	
	/**
	 * 時間間隔群から対象時刻を含む時間間隔を取得する。<br>
	 * 取得できなかった場合は、0-0の(妥当でない)時間間隔を返す。<br>
	 * <br>
	 * @param durations  時間間隔群
	 * @param targetTime 対象時刻(0:00からの分)
	 * @return 対象時刻を含む時間間隔
	 */
	public static TimeDuration getContainTime(Map<Integer, TimeDuration> durations, int targetTime) {
		// 時間間隔毎に処理
		for (TimeDuration duration : combineDurations(durations).values()) {
			// 時間間隔の中に対象時刻がある場合
			if (duration.isContain(targetTime)) {
				// 時間間隔を取得
				return duration;
			}
		}
		// 0-0の(妥当でない)時間間隔を取得
		return TimeDuration.getInvalid();
	}
	
	/**
	 * 時間間隔群の前方から除去時間分を除去する。<br>
	 * <br>
	 * @param durations 時間間隔群
	 * @param time      除去時間(分)
	 * @return 除去後の時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> removeTime(Map<Integer, TimeDuration> durations, int time) {
		// 除去後の時間間隔群を準備
		Map<Integer, TimeDuration> removedTimes = new TreeMap<Integer, TimeDuration>();
		// 残時間を準備
		int remain = time;
		// 時間間隔毎に処理
		for (TimeDuration duration : combineDurations(durations).values()) {
			// 残時間が0である場合
			if (remain == 0) {
				// 除去後の時間間隔群に追加
				removedTimes.put(duration.getStartTime(), duration);
				// 次の時間間隔へ
				continue;
			}
			// 時間間隔の時間(分)を取得
			int minutes = duration.getMinutes();
			// 時間間隔の時間が残時間以下である場合
			if (minutes <= remain) {
				// 除去(残時間を減算)
				remain -= minutes;
				// 次の時間間隔へ
				continue;
			}
			// 時間間隔の時間が残時間より大きい場合
			// 時間間隔の開始時刻を調整
			int startTime = duration.getStartTime() + remain;
			// 除去(残時間を減算)
			remain = 0;
			// 時間間隔を再作成し除去後の時間間隔群に追加
			removedTimes.put(startTime, TimeDuration.getInstance(startTime, duration.getEndTime()));
		}
		// 除去後の時間間隔群を取得
		return combineDurations(removedTimes);
	}
	
	/**
	 * 時間間隔群から到達時間までの時間を取得する。<br>
	 * <br>
	 * @param durations 時間間隔群
	 * @param time      到達時間(分)
	 * @return 到達時間までの時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> getReachTimes(Map<Integer, TimeDuration> durations, int time) {
		// 時間間隔群の前方から到達時間分を除去
		Map<Integer, TimeDuration> removeBefore = removeTime(durations, time);
		// 元の時間間隔群と重複しない部分を取得
		return getNotOverlap(durations, removeBefore);
	}
	
	/**
	 * 重複していない時間間隔群を取得する。<br>
	 * <br>
	 * 時間間隔群の各時間間隔に対して、
	 * 対象時間間隔と重複していない時間間隔を取得する。<br>
	 * {@link TimeDuration#getNotOverlap(Map)}を参照。<br>
	 * <br>
	 * @param durations      時間間隔群
	 * @param targetDuration 対象時間間隔
	 * @return 重複していない時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> getNotOverlap(Map<Integer, TimeDuration> durations,
			TimeDuration targetDuration) {
		// 重複していない時間間隔群(キー：開始時刻)(キー順)を準備
		Map<Integer, TimeDuration> notOverlaps = new TreeMap<Integer, TimeDuration>();
		// 時間間隔毎に処理
		for (TimeDuration duration : combineDurations(durations).values()) {
			// 対象時間間隔と重複していない時間間隔を取得し追加
			notOverlaps.putAll(duration.getNotOverlap(targetDuration));
		}
		// 重複していない時間間隔群(キー：開始時刻)(キー順)を取得
		return notOverlaps;
	}
	
	/**
	 * 重複していない時間間隔群(キー：開始時刻(キー順))を取得する。<br>
	 * <br>
	 * 時間間隔群の各時間間隔に対して、
	 * 対象時間間隔と重複していない時間間隔を取得する。<br>
	 * <br>
	 * @param durations        時間間隔群
	 * @param targetDurations  対象時間間隔群
	 * @return 重複していない時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> getNotOverlap(Map<Integer, TimeDuration> durations,
			Map<Integer, TimeDuration> targetDurations) {
		// 重複していない時間間隔群を準備
		Map<Integer, TimeDuration> notOverlaps = new TreeMap<Integer, TimeDuration>();
		// 時間間隔毎に処理
		for (TimeDuration duration : combineDurations(durations).values()) {
			// 対象時間間隔と重複していない時間間隔を取得し追加
			notOverlaps.putAll(duration.getNotOverlap(targetDurations));
		}
		// 重複していない時間間隔群(キー：開始時刻)(キー順)を取得
		return notOverlaps;
	}
	
	/**
	 * 重複している時間間隔群(キー：開始時刻(キー順))を取得する。<br>
	 * <br>
	 * 時間間隔群の各時間間隔に対して、
	 * 対象時間間隔と重複している時間間隔を取得する。<br>
	 * <br>
	 * @param durations        時間間隔群
	 * @param targetDurations  対象時間間隔群
	 * @return 重複している時間間隔群(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> getOverlap(Map<Integer, TimeDuration> durations,
			Map<Integer, TimeDuration> targetDurations) {
		// 重複している時間間隔群を準備
		Map<Integer, TimeDuration> overlaps = new TreeMap<Integer, TimeDuration>();
		// 時間間隔毎に処理
		for (TimeDuration duration : combineDurations(durations).values()) {
			// 対象時間間隔と重複している時間間隔を取得し追加
			overlaps.putAll(duration.getOverlap(targetDurations));
		}
		// 重複している時間間隔群(キー：開始時刻)(キー順)を取得
		return overlaps;
	}
	
	/**
	 * 時間間隔群の中に重複している時間間隔があるかを確認する。<br>
	 * <br>
	 * @param durations 時間間隔群
	 * @return 確認結果(true：時間間隔群の中に重複している時間間隔がある、false：無い)
	 */
	public static boolean isOverlap(Collection<TimeDuration> durations) {
		// 時間間隔群がnullである場合
		if (durations == null) {
			// 時間間隔群の中に重複している時間間隔は無いと判断
			return false;
		}
		// 時間間隔の総時間を準備
		int minutes = 0;
		// 時間間隔毎に処理
		for (TimeDuration duration : durations) {
			// 時間を加算
			minutes += duration.getMinutes();
		}
		// 時間間隔を結合した場合の総時間よりも大きいかどうかを確認
		return getMinutes(mergeDurations(durations)) < minutes;
	}
	
	/**
	 * 時間間隔群(キー：開始時刻(キー順))に対象時間間隔と離れた時間間隔があるかを確認する。<br>
	 * <br>
	 * @param durations 時間間隔群(キー：開始時刻)
	 * @param duration  対象時間間隔
	 * @return 確認結果(true：離れた時間間隔が有る、false：離れた時間間隔が無い)
	 */
	public static boolean isApart(Map<Integer, TimeDuration> durations, TimeDuration duration) {
		// 重複していない時間間隔毎に処理
		for (TimeDuration notOverlap : getNotOverlap(durations, duration).values()) {
			// 接していない場合
			if (duration.isLink(notOverlap) == false) {
				// 離れた時間間隔が有ると判断
				return true;
			}
		}
		// 離れた時間間隔が無いと判断
		return false;
	}
	
	/**
	 * 時間間隔群のすき間を取得する。<br>
	 * <br>
	 * 時間間隔群の間隔の間に存在する埋められていない時間間隔を取得する。<br>
	 * <br>
	 * 時間間隔群の間隔は、{@link TimeUtility#getDuration(Map) }を参照。<br>
	 * <br>
	 * @param durations 時間間隔群
	 * @return 時間間隔群のすき間(キー：開始時刻(キー順))
	 */
	public static Map<Integer, TimeDuration> getGap(Map<Integer, TimeDuration> durations) {
		// 時間間隔群の間隔を取得
		TimeDuration duration = getDuration(durations);
		// 時間間隔群の間隔と重複していない時間間隔群を取得
		return duration.getNotOverlap(durations);
	}
	
	/**
	 * 時間間隔群の間隔を取得する。<br>
	 * <br>
	 * 時間間隔群の最初の時刻と最後の時刻を取得する。<br>
	 * <br>
	 * @param durations 時間間隔群
	 * @return 時間間隔群の間隔
	 */
	public static TimeDuration getDuration(Map<Integer, TimeDuration> durations) {
		// 時間間隔群が空である場合
		if (durations.isEmpty()) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 最初の時刻と最後の時刻を準備
		int startTime = Integer.MAX_VALUE;
		int endTime = 0;
		// 時間間隔毎に処理
		for (TimeDuration duration : combineDurations(durations).values()) {
			// 開始時刻が最初の時刻よりも小さい場合
			if (duration.getStartTime() < startTime) {
				// 最初の時刻を設定
				startTime = duration.getStartTime();
			}
			// 最後の時刻が終了時刻より小さい場合
			if (endTime < duration.getEndTime()) {
				// 最後の時刻を設定
				endTime = duration.getEndTime();
			}
		}
		// 時間間隔群の間隔を取得
		return TimeDuration.getInstance(startTime, endTime);
	}
	
	/**
	 * 汎用パラメータ群から時間間隔情報群を取得する。<br>
	 * @param params 汎用パラメータ群
	 * @param key    キー
	 * @return 時間間隔情報群
	 */
	public static Map<Integer, TimeDuration> getDurations(Map<String, Object> params, String key) {
		// キーで値を取得
		Map<Integer, TimeDuration> obj = MospUtility.getValue(params, key);
		// キーで値を取得できなかった場合
		if (obj == null) {
			// 空の時間間隔情報群を取得
			return Collections.emptyMap();
		}
		
		// 時間間隔情報群を取得
		return obj;
	}
	
	/**
	 * 汎用パラメータ群に時間間隔情報群を追加する。<br>
	 * @param params    汎用パラメータ群
	 * @param key       キー
	 * @param durations 時間間隔群
	 */
	public static void addDurations(Map<String, Object> params, String key, Map<Integer, TimeDuration> durations) {
		// 汎用パラメータ群から時間間隔情報群を取得
		Map<Integer, TimeDuration> current = getDurations(params, key);
		// 時間間隔群を統合
		Map<Integer, TimeDuration> merged = mergeDurations(current, durations);
		// 汎用パラメータ群に時間間隔群を設定
		params.put(key, merged);
	}
	
	/**
	 * 法定労働時間(分)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 法定労働時間(分)
	 */
	public static int getLegalWorkTime(MospParams mospParams) {
		// 法定労働時間(デフォルトは8時間：480分)を取得
		return mospParams.getApplicationProperty(TimeConst.APP_LEGAL_WORK_TIME,
				TimeConst.TIME_WORKING_HOUR * TimeConst.CODE_DEFINITION_HOUR);
	}
	
	/**
	 * 週の法定労働時間(分)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 週の法定労働時間(分)
	 */
	public static int getLegalWeeklyWorkTime(MospParams mospParams) {
		// 週の法定労働時間(デフォルトは40時間：2400分)を取得
		return mospParams.getApplicationProperty(TimeConst.APP_LEGAL_WEEKLY_WORK_TIME,
				TimeConst.TIME_LEGAL_WEEKLY_WORK_TIME);
	}
	
	/**
	 * 翌日の開始時刻(0:00からの分)を取得する。<br>
	 * @return 翌日の開始時刻(0:00からの分)
	 */
	public static int getNextDayStart() {
		// 翌日の開始時刻(0:00からの分)を取得(24時間：1440分)
		return TimeConst.TIME_DAY_ALL_HOUR * TimeConst.CODE_DEFINITION_HOUR;
	}
	
	/**
	 * 翌日の終了時刻(0:00からの分)を取得する。<br>
	 * @return 翌日の終了時刻(0:00からの分)
	 */
	public static int getNextDayEnd() {
		// 翌日の終了時刻(0:00からの分)を取得(48時間：2880分)
		return getNextDayStart() * 2;
	}
	
	/**
	 * 深夜の開始時刻(0:00からの分)を取得する。<br>
	 * <br>
	 * インデックスによって、値が変わる。
	 * ・indexが0の場合：00:00<br>
	 * ・indexが1の場合：22:00<br>
	 * ・indexが2の場合：46:00<br>
	 * <br>
	 * @param idx インデックス
	 * @return 深夜の開始時刻(0:00からの分)
	 */
	public static int getNightStart(int idx) {
		// 深夜開始時刻(22:00)を取得
		int nightStart = TimeConst.TIME_NIGHT_WORK_START * TimeConst.CODE_DEFINITION_HOUR;
		// インデックスによる調整
		nightStart = (idx - 1) * getNextDayStart() + nightStart;
		// 深夜の開始時刻(0:00からの分)を取得(最小で00:00)
		return nightStart < 0 ? 0 : nightStart;
	}
	
	/**
	 * 深夜の終了時刻(0:00からの分)を取得する。<br>
	 * <br>
	 * インデックスによって、値が変わる。
	 * ・indexが0の場合：05:00<br>
	 * ・indexが1の場合：29:00<br>
	 * ・indexが2の場合：48:00<br>
	 * <br>
	 * @param idx インデックス
	 * @return 深夜の終了時刻(0:00からの分)
	 */
	public static int getNightEnd(int idx) {
		// 深夜終了時刻(29:00)を取得
		int nightEnd = TimeConst.TIME_NIGHT_WORK_END * TimeConst.CODE_DEFINITION_HOUR;
		// インデックスによる調整
		nightEnd = (idx - 1) * getNextDayStart() + nightEnd;
		// 深夜の終了時刻(0:00からの分)を取得(最大で48：00)
		return nightEnd < getNextDayEnd() ? nightEnd : getNextDayEnd();
	}
	
	/**
	 * エクスポート時間フォーマット設定情報を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return エクスポート時間フォーマット設定情報
	 */
	public static int getExportTimeFormat(MospParams mospParams) {
		return mospParams.getApplicationProperty(TimeConst.APP_EXPORT_TIME_FORMAT, 0);
	}
	
	/**
	 * エクスポート時間を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param minute     分
	 * @param format     エクスポート時間フォーマット
	 * @return エクスポート時間
	 */
	public static String getExportTime(MospParams mospParams, int minute, int format) {
		if (format == TimeFileConst.CODE_EXPORT_TIME_FORMAT_MINUTES) {
			// 分単位の場合
			return Integer.toString(minute);
		} else if (format == TimeFileConst.CODE_EXPORT_TIME_FORMAT_HOURS) {
			// 時間単位の場合
			return getExportTimeHour(minute);
		} else if (format == TimeFileConst.CODE_EXPORT_TIME_FORMAT_COLON_SEPARATED) {
			// コロン区切の場合
			return getStringColonTime(mospParams, minute);
		} else if (format == TimeFileConst.CODE_EXPORT_TIME_FORMAT_DOT_SEPARATED) {
			// ドット区切の場合
			return getStringPeriodTime(mospParams, minute);
		}
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * 時間を時間単位で取得する。<br>
	 * @param minute 分
	 * @return 時間(時間単位)
	 */
	public static String getExportTimeHour(int minute) {
		// 分単位を時間単位に変換して小数点以下第2位までの値を返す
		DecimalFormat df = new DecimalFormat("0.00");
		return df.format(TimeUtility.getRoundHalfUp2(TimeUtility.getMinuteToHour(minute)));
	}
	
	/**
	 * 送出ファイル名を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param dto        エクスポート情報
	 * @param firstDate  出力期間初日
	 * @param lastDate   出力期間最終日
	 * @return 送出ファイル名
	 */
	public static String getExportFileName(MospParams mospParams, ExportDtoInterface dto, Date firstDate,
			Date lastDate) {
		// 送出ファイル名を作成
		StringBuilder sb = new StringBuilder();
		// エクスポートコード
		sb.append(dto.getExportCode());
		// ハイフン
		sb.append(PfNameUtility.hyphen(mospParams));
		// 締期間初日
		sb.append(DateUtility.getStringDateNoSeparator(firstDate));
		// ハイフン
		sb.append(PfNameUtility.hyphen(mospParams));
		// 締期間最終日
		sb.append(DateUtility.getStringDateNoSeparator(lastDate));
		// 拡張子
		sb.append(PlatformUtility.getFileExtension(dto));
		// 送出ファイル名を取得
		return sb.toString();
	}
	
	/**
	 * 休憩情報が登録されているかを確認する。<br>
	 * @param rests    休憩情報群
	 * @param workDate 勤務日
	 * @return 確認結果(true：休憩情報が登録されている、false：されていない)
	 */
	public static boolean isRestRegistered(Collection<RestDtoInterface> rests, Date workDate) {
		// 基準となる時刻(勤務日の0:00)を取得
		Date standardTime = DateUtility.getDate(workDate);
		// 休憩情報毎に処理
		for (RestDtoInterface dto : rests) {
			// 休憩開始時刻が基準と異なる場合
			if (DateUtility.isSame(standardTime, dto.getRestStart()) == false) {
				// 休憩情報が登録されていると判断
				return true;
			}
			// 休憩終了時刻が基準と異なる場合
			if (DateUtility.isSame(standardTime, dto.getRestEnd()) == false) {
				// 休憩情報が登録されていると判断
				return true;
			}
		}
		// 休憩情報が登録されていないと判断(全ての休憩開始終了時刻が基準と同じである場合)
		return false;
	}
	
	/**
	 * 休日出勤申請情報から休日出勤時の予定勤務形態を取得する。<br>
	 * 但し、振替出勤(勤務形態変更無し)の場合は、空文字を取得する(別途取得する必要がある)。<br>
	 * @param dto 休日出勤申請情報
	 * @return 休日出勤時の予定勤務形態
	 */
	public static String getWorkOnHolidayWorkType(WorkOnHolidayRequestDtoInterface dto) {
		// 勤務形態を準備
		String workTypeCode = MospConst.STR_EMPTY;
		// 振替申請及び休出種別を取得
		int substitute = dto.getSubstitute();
		String workOnHolidayType = dto.getWorkOnHolidayType();
		// 振替出勤(勤務形態変更あり)の場合
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
			// 変更勤務形態を取得
			workTypeCode = dto.getWorkTypeCode();
		}
		// 休日出勤の場合
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 休出種別が所定休日の場合
			if (MospUtility.isEqual(workOnHolidayType, TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
				// 所定休日出勤を取得
				workTypeCode = TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY;
			}
			// 休出種別が法定休日の場合
			if (MospUtility.isEqual(workOnHolidayType, TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
				// 法定休日出勤を取得
				workTypeCode = TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY;
			}
		}
		// 勤務形態を取得
		return workTypeCode;
	}
	
	/**
	 * 休日出勤申請情報(振替申請(勤務形態変更なし)のみ)(下書及び取下以外)から出勤日群を取得する。<br>
	 * 休日出勤申請情報から勤務形態が不明である出勤日を取得する。<br>
	 * <br>
	 * 次の振替申請を対象とする。<br>
	 * ・振替申請する(全日)<br>
	 * ・振替申請する(午前)<br>
	 * ・振替申請する(午後)<br>
	 * @param workOnHolidayRequests 休出申請情報群(キー：出勤日)
	 * @param workflows             ワークフロー情報群
	 * @return 出勤日群
	 */
	public static Set<Date> getSubstituteWorkDates(Map<Date, WorkOnHolidayRequestDtoInterface> workOnHolidayRequests,
			Map<Long, WorkflowDtoInterface> workflows) {
		// 出勤日群を準備
		Set<Date> substituteWorkDates = new TreeSet<Date>();
		// 休日出勤申請情報毎に処理
		for (Entry<Date, WorkOnHolidayRequestDtoInterface> entry : workOnHolidayRequests.entrySet()) {
			// 出勤日及び休日出勤申請情報を取得
			Date workDate = entry.getKey();
			WorkOnHolidayRequestDtoInterface dto = entry.getValue();
			// ワークフロー情報を取得
			WorkflowDtoInterface workflow = workflows.get(dto.getWorkflow());
			// 下書か取下である場合
			if (WorkflowUtility.isMatch(workflow, WorkflowUtility.getEffectiveStatuses()) == false) {
				// 次の休日出勤申請情報へ
				continue;
			}
			// 休日出勤申請情報から勤務形態が不明である場合
			if (isTheSubstitute(dto, TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON,
					TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM, TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM)) {
				// 出勤日群に設定
				substituteWorkDates.add(workDate);
			}
		}
		// 出勤日群を取得
		return substituteWorkDates;
	}
	
	/**
	 * 休日出勤申請情報の振替申請が指定された振替申請のいずれかであるかを確認する。<br>
	 * @param dto         休出申請情報
	 * @param substitutes 振替申請配列
	 * @return 確認結果(true：指定された振替申請のいずれかである、false：そうでない)
	 */
	public static boolean isTheSubstitute(WorkOnHolidayRequestDtoInterface dto, int... substitutes) {
		// 休出申請情報か振替申請配列がnullである場合
		if (MospUtility.isEmpty(dto, substitutes)) {
			// 指定された振替申請のいずれかでないと判断
			return false;
		}
		// 振替申請毎に処理
		for (int substitute : substitutes) {
			// 休日出勤申請情報の振替申請が指定された振替申請と合致する場合
			if (dto.getSubstitute() == substitute) {
				// 指定された振替申請のいずれかであると判断
				return true;
			}
		}
		// 指定された振替申請のいずれかでないと判断
		return false;
	}
	
	/**
	 * 限度基準情報群(キー：期間)を取得する。<br>
	 * @param dtos 限度基準情報群
	 * @return 限度基準情報群(キー：期間)
	 */
	public static Map<String, LimitStandardDtoInterface> getLimitStandards(Collection<LimitStandardDtoInterface> dtos) {
		// 限度基準情報群(キー：期間)を準備
		Map<String, LimitStandardDtoInterface> map = new TreeMap<String, LimitStandardDtoInterface>();
		// 限度基準情報群が存在しない場合
		if (MospUtility.isEmpty(dtos)) {
			// 空の限度基準情報群(キー：期間)を取得
			return map;
		}
		// 限度基準情報毎に処理
		for (LimitStandardDtoInterface dto : dtos) {
			// 限度基準情報群(キー：期間)に設定
			map.put(dto.getTerm(), dto);
		}
		// 限度基準情報群(キー：期間)を取得
		return map;
	}
	
	/**
	 * 週40時間超勤務時間(法定内発生分)を計算する。<br>
	 * @param overtimeIn              法定内残業時間
	 * @param overtimeInNoWeeklyForty 法定内残業時間(週40時間超除く)
	 * @return 週40時間超勤務時間(法定内発生分)
	 */
	public static int calcWeeklyOverFortyIn(int overtimeIn, int overtimeInNoWeeklyForty) {
		// 週40時間超勤務時間(法定内発生分)を計算
		return overtimeInNoWeeklyForty - overtimeIn;
	}
	
	/**
	 * 週40時間超勤務時間(勤務内発生分)を計算する。<br>
	 * @param weeklyOverForty         週40時間超勤務時間
	 * @param overtimeIn              法定内残業時間
	 * @param overtimeInNoWeeklyForty 法定内残業時間(週40時間超除く)
	 * @return 週40時間超勤務時間(勤務内発生分)
	 */
	public static int calcWeeklyOverFortyNormal(int weeklyOverForty, int overtimeIn, int overtimeInNoWeeklyForty) {
		// 週40時間超勤務時間(勤務内発生分)を計算
		return weeklyOverForty - calcWeeklyOverFortyIn(overtimeIn, overtimeInNoWeeklyForty);
	}
	
	/**
	 * 日の値が月末であるかを確認する。<br>
	 * @param day 日の値
	 * @return 確認結果(true：月末である、false：そうでない)
	 */
	public static boolean isEndOfMonthDay(int day) {
		return day == TimeConst.DAY_END_OF_MONTH;
	}
	
	/**
	 * 期限日を取得する。<br>
	 * @param date  起算日
	 * @param month 月の値
	 * @param day   日の値
	 * @return 期限日
	 */
	public static Date getLimitDate(Date date, int month, int day) {
		// 期限日を準備(基準日のmonthヶ月後)
		Date limitDate = DateUtility.addMonth(date, month);
		// 日の値が月末でない場合
		if (isEndOfMonthDay(day) == false) {
			// 期限日に日を設定し取得
			return DateUtility.addDay(limitDate, day - 1);
		}
		// 年月を取得
		int targetYear = DateUtility.getYear(limitDate);
		int targetMonth = DateUtility.getMonth(limitDate);
		// 期限日を取得
		return MonthUtility.getYearMonthLastDate(targetYear, targetMonth);
	}
	
}
