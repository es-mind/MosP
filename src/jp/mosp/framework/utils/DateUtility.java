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
package jp.mosp.framework.utils;

import java.text.DateFormat;
import java.text.DecimalFormat;
import java.text.Format;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;

/**
 * 日付操作に有用なメソッドを提供する。<br><br>
 */
public final class DateUtility {
	
	/**
	 * 1時間を秒に直した数値(1000 * 60 * 60)。<br>
	 */
	public static final int			TIME_HOUR_MILLI_SEC	= 3600000;
	
	/**
	 * 1日を秒に直した数値(1000 * 60 * 60 * 24)。<br>
	 */
	public static final int			TIME_DAY_MILLI_SEC	= 86400000;
	
	/**
	 * 日付文字列配列。<br>
	 * {@link #getVariousDate(String)}で利用する。<br>
	 * 時や分などの細かいフォーマットを年月日のみのフォーマットよりも先に設定する必要がある。<br>
	 */
	private static final String[]	FORMAT_VARIOUS_DATE	= { "yyyyMMddHHmmss", "yyyy/MM/dd HH:mm", "yyyy/MM/dd",
		"yyyyMMdd", "yyyy-MM-dd" };
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private DateUtility() {
		// 処理無し
	}
	
	/**
	 * システム日付を取得する。<br>
	 * @return システム日付
	 */
	public static Date getSystemDate() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * システム日時を取得する。<br>
	 * @return システム日時
	 */
	public static Date getSystemTime() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * システム日時(秒含む)を取得する。<br>
	 * @return システム日時
	 */
	public static Date getSystemTimeAndSecond() {
		Calendar cal = Calendar.getInstance();
		return cal.getTime();
	}
	
	/**
	 * デフォルト日時を取得する。<br>
	 * @return デフォルト日時
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDefaultTime() throws MospException {
		return getTime(0, 0);
	}
	
	/**
	 * カレンダーによるデータ取得。<br>
	 * @param date  日付
	 * @param field フィールド
	 * @return date
	 */
	public static int getCalendarValue(Date date, int field) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		return calendar.get(field);
	}
	
	/**
	 * 年取得。<br>
	 * @param date 日付
	 * @return 年
	 */
	public static int getYear(Date date) {
		return getCalendarValue(date, Calendar.YEAR);
	}
	
	/**
	 * 月取得。<br>
	 * @param date 日付
	 * @return 月
	 */
	public static int getMonth(Date date) {
		return getCalendarValue(date, Calendar.MONTH) + 1;
	}
	
	/**
	 * 日取得。<br>
	 * @param date 日付
	 * @return 日
	 */
	public static int getDay(Date date) {
		return getCalendarValue(date, Calendar.DAY_OF_MONTH);
	}
	
	/**
	 * 時間取得。<br>
	 * @param date 日付
	 * @return 時間
	 */
	public static int getHour(Date date) {
		return getCalendarValue(date, Calendar.HOUR_OF_DAY);
	}
	
	/**
	 * 時間取得。<br>
	 * @param date 日付
	 * @param standardDate 基準日付
	 * @return 時間
	 */
	public static int getHour(Date date, Date standardDate) {
		// 基準日日付がnullである場合
		if (standardDate == null) {
			return getHour(date);
		}
		return (int)((date.getTime() - standardDate.getTime()) / TIME_HOUR_MILLI_SEC);
	}
	
	/**
	 * 分取得。<br>
	 * @param date 日付
	 * @return 分
	 */
	public static int getMinute(Date date) {
		return getCalendarValue(date, Calendar.MINUTE);
	}
	
	/**
	 * 曜日取得。<br>
	 * @param date 日付
	 * @return 曜日を示すフィールド値
	 */
	public static int getDayOfWeek(Date date) {
		return getCalendarValue(date, Calendar.DAY_OF_WEEK);
	}
	
	/**
	 * 年を取得する。<br>
	 * @param date 日付
	 * @return 年
	 */
	public static String getStringYear(Date date) {
		return getStringDate(date, "yyyy");
	}
	
	/**
	 * 月を取得する。<br>
	 * @param date 日付
	 * @return 月
	 */
	public static String getStringMonth(Date date) {
		return getStringDate(date, "MM");
	}
	
	/**
	 * 月(前ゼロ無し)を取得する。<br>
	 * @param date 日付
	 * @return 月(前ゼロ無し)
	 */
	public static String getStringMonthM(Date date) {
		return getStringDate(date, "M");
	}
	
	/**
	 * 日を取得する。<br>
	 * @param date 日付
	 * @return 日
	 */
	public static String getStringDay(Date date) {
		return getStringDate(date, "dd");
	}
	
	/**
	 * 日(前ゼロ無し)を取得する。<br>
	 * @param date 日付
	 * @return 日(前ゼロ無し)
	 */
	public static String getStringDayD(Date date) {
		return getStringDate(date, "d");
	}
	
	/**
	 * 時間を取得する。<br>
	 * @param date 日付
	 * @return 時間(0～23)
	 */
	public static String getStringHour(Date date) {
		return getStringDate(date, "HH");
	}
	
	/**
	 * 時間(前ゼロ無し)を取得する。<br>
	 * @param date 日付
	 * @return 時間(0～23)(前ゼロ無し)
	 */
	public static String getStringHourH(Date date) {
		return getStringDate(date, "H");
	}
	
	/**
	 * 基準日付と日付の差を時間で取得する。<br>
	 * @param date 日付
	 * @param standardDate 基準日付
	 * @return 時間
	 */
	public static String getStringHour(Date date, Date standardDate) {
		return getStringHour(date, standardDate, "#00");
	}
	
	/**
	 * 基準日付と日付の差を時間(前ゼロ無し)で取得する。<br>
	 * @param date 日付
	 * @param standardDate 基準日付
	 * @return 時間
	 */
	public static String getStringHourH(Date date, Date standardDate) {
		return getStringHour(date, standardDate, "#0");
	}
	
	/**
	 * 時間を取得する。<br>
	 * @param date 日付
	 * @param standardDate 基準日付
	 * @param pattern パターン
	 * @return 時間
	 */
	private static String getStringHour(Date date, Date standardDate, String pattern) {
		if (date == null || standardDate == null) {
			return getStringHour(date);
		}
		Format format = new DecimalFormat(pattern);
		return format.format((date.getTime() - standardDate.getTime()) / TIME_HOUR_MILLI_SEC);
	}
	
	/**
	 * 分を取得する。<br>
	 * @param date 日付
	 * @return 分
	 */
	public static String getStringMinute(Date date) {
		return getStringDate(date, "mm");
	}
	
	/**
	 * 分(前ゼロ無し)を取得する。<br>
	 * @param date 日付
	 * @return 分(前ゼロ無し)
	 */
	public static String getStringMinuteM(Date date) {
		return getStringDate(date, "m");
	}
	
	/**
	 * 曜日を取得する。<br>
	 * @param date 日付
	 * @return 曜日
	 */
	public static String getStringDayOfWeek(Date date) {
		return getStringDate(date, "E");
	}
	
	/**
	 * 日付オブジェクト(日付)を取得する。<br>
	 * 対象日付オブジェクトから、時間以下の情報を除いた日付を取得する。<br>
	 * @param date 対象日付オブジェクト
	 * @return 日付オブジェクト(日付)
	 */
	public static Date getDate(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}
	
	/**
	 * 日付変換(int→Date)
	 * @param year		対象年
	 * @param month		対象月
	 * @param day		対象日
	 * @return Date	対象年月日
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDate(int year, int month, int day) throws MospException {
		return getDateTime(year, month, day, 0, 0);
	}
	
	/**
	 * 日付オブジェクトを取得する。<br>
	 * 年、月、日がいずれも空白の場合、nullを返す。<br>
	 * 引数にnullが含まれる場合は、例外を発行する。<br>
	 * @param year	対象年
	 * @param month	対象月
	 * @param day	対象日
	 * @return 日付オブジェクト
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDate(String year, String month, String day) throws MospException {
		try {
			if (year.isEmpty() || month.isEmpty() || day.isEmpty()) {
				return null;
			}
			return getDate(Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day));
		} catch (NullPointerException e) {
			throw new MospException(e);
		} catch (NumberFormatException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * 日付オブジェクト(時間)を取得する。<br>
	 * @param minute 対象分
	 * @return 日付オブジェクト(時間)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getTime(int minute) throws MospException {
		return addMinute(getDefaultTime(), minute);
	}
	
	/**
	 * 日付オブジェクト(時間)を取得する。<br>
	 * @param hourOfDay 対象時間
	 * @param minute 対象分
	 * @return 日付オブジェクト(時間)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getTime(int hourOfDay, int minute) throws MospException {
		return getDateTime(MospConst.DEFAULT_YEAR, 1, 1, hourOfDay, minute);
	}
	
	/**
	 * 日付オブジェクト(時間)を取得する。<br>
	 * 時、分がいずれも空白の場合、nullを返す。<br>
	 * 引数にnullが含まれる場合は、例外を発行する。<br>
	 * @param hour   時間
	 * @param minute 分
	 * @return 日付オブジェクト(時間)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getTime(String hour, String minute) throws MospException {
		try {
			if (hour.isEmpty() && minute.isEmpty()) {
				return null;
			}
			return getTime(Integer.parseInt(hour), Integer.parseInt(minute));
		} catch (NullPointerException e) {
			throw new MospException(e);
		} catch (NumberFormatException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * 日付オブジェクト(日時)を取得する。<br>
	 * @param date 日付文字列(yyyyMMddHHmmss)
	 * @return 日付オブジェクト(日時)
	 */
	public static Date getTime(String date) {
		return getDate(date, "yyyyMMddHHmmss");
	}
	
	/**
	 * 日付オブジェクト(日時)を取得する。<br>
	 * 基準日付のHH時mm分として取得する。<br>
	 * @param time 時間文字列(HH:mm)
	 * @param standardDate 基準日付
	 * @return 日付オブジェクト(日時)
	 */
	public static Date getTime(String time, Date standardDate) {
		// 日付文字列を準備
		StringBuilder sb = new StringBuilder();
		sb.append(getStringDateNoSeparator(standardDate));
		sb.append(time);
		// 基準日付のHH時mm分として取得
		return getDate(sb.toString(), "yyyyMMddHH:mm");
	}
	
	/**
	 * 時刻を取得する。<br>
	 * デフォルト基準日付の時刻を、基準日付における時刻に変換する。<br>
	 * @param time         デフォルト基準日付の時刻
	 * @param standardDate 基準日付
	 * @return 基準日付における時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getTime(Date time, Date standardDate) throws MospException {
		// デフォルト基準日付との差を取得
		long deference = time.getTime() - getDefaultTime().getTime();
		// 基準日付における時刻を取得
		return new Date(standardDate.getTime() + deference);
	}
	
	/**
	 * 日付オブジェクトを取得する(String→Date)。<br>
	 * @param date 日付文字列(yyyy/MM/dd)
	 * @return 日付
	 */
	public static Date getDate(String date) {
		return getDate(date, "yyyy/MM/dd");
	}
	
	/**
	 * 日付オブジェクトを取得する(String→Date)。<br>
	 * <br>
	 * 次の順で解析し、日付として取得できたものを返す。<br>
	 * ・yyyyMMddHHmmss<br>
	 * ・yyyy/MM/dd HH:mm<br>
	 * ・yyyy/MM/dd<br>
	 * ・yyyyMMdd<br>
	 * ・yyyy-MM-dd<br>
	 * <br>
	 * 月日の前ゼロは、無くても良い。<br>
	 * 但し、yyyyMMddの場合は、前ゼロが無いと
	 * 思った通りの日付が取得できないことがある。<br>
	 * <br>
	 * @param date 日付文字列
	 * @return 日付
	 */
	public static Date getVariousDate(String date) {
		// フォーマット文字列毎に処理
		for (String format : FORMAT_VARIOUS_DATE) {
			// フォーマットで日付オブジェクトを取得
			Date objDate = getDate(date, format);
			// フォーマットで日付オブジェクトを取得できた場合
			if (MospUtility.isEmpty(objDate) == false) {
				// 日付オブジェクトを取得
				return objDate;
			}
		}
		// nullを取得
		return null;
	}
	
	/**
	 * 日付変換（String→Date）
	 * @since	0.0.2
	 * @param date		対象年月日
	 * @param format	日付フォーマット
	 * @return			Date型対象年月日
	 */
	public static Date getDate(String date, String format) {
		DateFormat df = new SimpleDateFormat(format);
		df.setLenient(false);
		try {
			return df.parse(date);
		} catch (ParseException e) {
			return null;
		}
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd)
	 */
	public static String getStringDate(Date date) {
		return getStringDate(date, "yyyy/MM/dd");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。<br>
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyyMMdd)
	 */
	public static String getStringDateNoSeparator(Date date) {
		return getStringDate(date, "yyyyMMdd");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd(E))
	 */
	public static String getStringDateAndDay(Date date) {
		return getStringDate(date, "yyyy/MM/dd(E)");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(MM/dd)
	 */
	public static String getStringMonthAndDate(Date date) {
		return getStringDate(date, "MM/dd");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(MM/dd(E))
	 */
	public static String getStringMonthAndDay(Date date) {
		return getStringDate(date, "MM/dd(E)");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd HH:mm)
	 */
	public static String getStringDateAndTime(Date date) {
		return getStringDate(date, "yyyy/MM/dd HH:mm");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd(E) HH:mm)
	 */
	public static String getStringDateAndDayAndTime(Date date) {
		return getStringDate(date, "yyyy/MM/dd(E) HH:mm");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM)
	 */
	public static String getStringYearMonth(Date date) {
		return getStringDate(date, "yyyy/MM");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy/MM/dd HH:mm:ss.SSS)
	 */
	public static String getStringDateMilli(Date date) {
		return getStringDate(date, "yyyy/MM/dd HH:mm:ss.SSS");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(HH:mm)
	 */
	public static String getStringTime(Date date) {
		return getStringDate(date, "HH:mm");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(HH:mm:ss)
	 */
	public static String getStringTimeAndSecond(Date date) {
		return getStringDate(date, "HH:mm:ss");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列( yyyy年MM月dd日)
	 */
	public static String getStringJapaneseDate(Date date) {
		return getStringDate(date, "yyyy年MM月dd日");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @return 日付文字列(yyyy年MM月)
	 */
	public static String getStringJapaneseMonth(Date date) {
		return getStringDate(date, "yyyy年MM月");
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @param standardDate 基準日付
	 * @return 日付文字列(HH:mm)
	 */
	public static String getStringTime(Date date, Date standardDate) {
		if (date == null || standardDate == null) {
			return getStringTime(date);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(getStringHour(date, standardDate));
		sb.append(":");
		sb.append(getStringMinute(date));
		return sb.toString();
	}
	
	/**
	 * 日付文字列を取得する(Date→String)。
	 * @param date 対象日付オブジェクト
	 * @param standardDate 基準日付
	 * @return 日付文字列(HH:mm:ss)
	 */
	public static String getStringTimeAndSecond(Date date, Date standardDate) {
		if (date == null || standardDate == null) {
			return getStringTimeAndSecond(date);
		}
		StringBuffer sb = new StringBuffer();
		sb.append(getStringHour(date, standardDate));
		sb.append(getStringDate(date, ":mm:ss"));
		return sb.toString();
	}
	
	/**
	 * 日付文字列取得。<br>
	 * @param date 日付
	 * @param format 時間
	 * @return 日付文字列
	 */
	public static String getStringDate(Date date, String format) {
		if (date == null) {
			return MospConst.STR_EMPTY;
		}
		SimpleDateFormat sdf = new SimpleDateFormat(format);
		return sdf.format(date);
	}
	
	/**
	 * 年操作。<br>
	 * @param date   操作日付
	 * @param amount 増減年数
	 * @return 操作後日付
	 */
	public static Date addYear(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.YEAR, amount);
		return cal.getTime();
	}
	
	/**
	 * 月操作。<br>
	 * @param date   操作日付
	 * @param amount 増減月数
	 * @return 操作後日付
	 */
	public static Date addMonth(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MONTH, amount);
		return cal.getTime();
	}
	
	/**
	 * 日操作。<br>
	 * @param date   操作日付
	 * @param amount 増減日数
	 * @return 操作後日付
	 */
	public static Date addDay(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.DAY_OF_MONTH, amount);
		return cal.getTime();
	}
	
	/**
	 * 時間操作。<br>
	 * @param date 操作日付
	 * @param amount 増減時間数
	 * @return 操作後日付
	 */
	public static Date addHour(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.HOUR_OF_DAY, amount);
		return cal.getTime();
	}
	
	/**
	 * 分操作。<br>
	 * @param date 操作日付
	 * @param amount 増減分数
	 * @return 操作後日付
	 */
	public static Date addMinute(Date date, int amount) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		cal.add(Calendar.MINUTE, amount);
		return cal.getTime();
	}
	
	/**
	 * 日付に年と日を足す。<br>
	 * @param date 操作日付
	 * @param year 年
	 * @param day  日
	 * @return 操作後日付
	 */
	public static Date addYearAndDay(Date date, int year, int day) {
		return addDay(addYear(date, year), day);
	}
	
	/**
	 * 日付に月と日を足す。<br>
	 * @param date  操作日付
	 * @param month 月
	 * @param day   日
	 * @return 操作後日付
	 */
	public static Date addMonthAndDay(Date date, int month, int day) {
		return addDay(addMonth(date, month), day);
	}
	
	/**
	 * 日付変換(int→Date)
	 * @param year		対象年
	 * @param month		対象月
	 * @param day		対象日
	 * @param hour		対象時
	 * @param minute	対象分
	 * @return Date	対象年月日
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDateTime(int year, int month, int day, int hour, int minute) throws MospException {
		try {
			Calendar cal = Calendar.getInstance();
			cal.setLenient(false);
			cal.set(year, month - 1, day, hour, minute, 0);
			cal.set(Calendar.MILLISECOND, 0);
			return cal.getTime();
		} catch (IllegalArgumentException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * 指定曜日可否を確認する。
	 * @param date 対象年月日
	 * @param dayOfWeek 対象曜日
	 * @return 対象の曜日の場合true、そうでない場合false
	 */
	public static boolean isDayOfWeek(Date date, int dayOfWeek) {
		return getDayOfWeek(date) == dayOfWeek;
	}
	
	/**
	 * 日曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 日曜日の場合true、そうでない場合false
	 */
	public static boolean isSunday(Date date) {
		return isDayOfWeek(date, Calendar.SUNDAY);
	}
	
	/**
	 * 月曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 月曜日の場合true、そうでない場合false
	 */
	public static boolean isMonday(Date date) {
		return isDayOfWeek(date, Calendar.MONDAY);
	}
	
	/**
	 * 火曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 火曜日の場合true、そうでない場合false
	 */
	public static boolean isTuesday(Date date) {
		return isDayOfWeek(date, Calendar.TUESDAY);
	}
	
	/**
	 * 水曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 水曜日の場合true、そうでない場合false
	 */
	public static boolean isWednesday(Date date) {
		return isDayOfWeek(date, Calendar.WEDNESDAY);
	}
	
	/**
	 * 木曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 木曜日の場合true、そうでない場合false
	 */
	public static boolean isThursday(Date date) {
		return isDayOfWeek(date, Calendar.THURSDAY);
	}
	
	/**
	 * 金曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 金曜日の場合true、そうでない場合false
	 */
	public static boolean isFriday(Date date) {
		return isDayOfWeek(date, Calendar.FRIDAY);
	}
	
	/**
	 * 土曜日かどうかを確認する。
	 * @param date 対象年月日
	 * @return 土曜日の場合true、そうでない場合false
	 */
	public static boolean isSaturday(Date date) {
		return isDayOfWeek(date, Calendar.SATURDAY);
	}
	
	/**
	 * 対象日を含む週の初日(週の起算曜日)を取得する。<br>
	 * @param startDayOfWeek 週の起算曜日
	 * @param targetDate     対象日
	 * @return 対象日を含む週の初日(週の起算曜日)
	 */
	public static Date getFirstDateOfWeek(int startDayOfWeek, Date targetDate) {
		// 日付を準備
		Date firstDate = targetDate;
		// 週の起算曜日であるかを確認
		while (DateUtility.isDayOfWeek(firstDate, startDayOfWeek) == false) {
			// 対象日を減算
			firstDate = addDay(firstDate, -1);
		}
		// 対象日を含む週の初日(週の起算曜日)を取得
		return firstDate;
	}
	
	/**
	 * 対象日を含む週の最終日を取得する。<br>
	 * @param startDayOfWeek 週の起算曜日
	 * @param targetDate     対象日
	 * @return 対象日を含む週の最終日
	 */
	public static Date getLastDateOfWeek(int startDayOfWeek, Date targetDate) {
		// 日付を準備(対象日が週の初日である場合を考慮して1日後から開始)
		Date lastDate = addDay(targetDate, 1);
		// 週の起算曜日であるかを確認
		while (DateUtility.isDayOfWeek(lastDate, startDayOfWeek) == false) {
			// 対象日を加算
			lastDate = addDay(lastDate, 1);
		}
		// 対象日を含む週の最終日(翌週の週の初日の一日前)を取得
		return addDay(lastDate, -1);
	}
	
	/**
	 * 開始日から対象日までの日数を取得する。<br>
	 * @param startDate  開始日
	 * @param targetDate 対象日
	 * @return 日数
	 */
	public static int getDayDifference(Date startDate, Date targetDate) {
		// 日付がnullである場合
		if (startDate == null || targetDate == null) {
			return 0;
		}
		// 差を取得
		long diff = targetDate.getTime() - startDate.getTime();
		// 日数に変換
		return (int)(diff / TIME_DAY_MILLI_SEC);
	}
	
	/**
	 * 開始日から対象日まで経過月を取得する。
	 * @param startDate 開始日
	 * @param targetDate 対象日
	 * @return 経過月数
	 */
	public static int getMonthDifference(Date startDate, Date targetDate) {
		// 年、月を取得
		int startYear = getYear(startDate);
		int startMonth = getMonth(startDate);
		int targetYear = getYear(targetDate);
		int targetMonth = getMonth(targetDate);
		// 月数を取得
		int startMonthMin = startYear * 12 + startMonth;
		int targetMonthMin = targetYear * 12 + targetMonth;
		// 経過月
		return targetMonthMin - startMonthMin;
	}
	
	/**
	 * 期間に対象日が含まれているか確認する。<br>
	 * 開始日nullの場合：対象日が終了日以前であるかを確認。<br>
	 * 終了日nullの場合：対象日が開始日以後であるかを確認。<br>
	 * @param targetDate 対象日
	 * @param startDate 開始日
	 * @param endDate   終了日
	 * @return 確認結果(true：含まれている、false：含んでない)
	 */
	public static boolean isTermContain(Date targetDate, Date startDate, Date endDate) {
		// 開始日がnullの場合
		if (startDate == null) {
			return targetDate.compareTo(endDate) <= 0;
		}
		// 終了日がnullの場合
		if (endDate == null) {
			return targetDate.compareTo(startDate) >= 0;
		}
		// 対象日が開始日以降であり、対象日が終了日以前であるかを確認
		return targetDate.compareTo(startDate) >= 0 && targetDate.compareTo(endDate) <= 0;
	}
	
	/**
	 * 対象開始日から対象終了日が期間(期間開始日から期間終了日)と重複しているかを確認する。<br>
	 * @param termStart 期間開始日
	 * @param termEnd   期間終了日
	 * @param startDate 対象開始日
	 * @param endDate   対象終了日
	 * @return 確認結果(true：期間と重複する、false：期間と重複しない)
	 */
	public static boolean isOnTheTerm(Date termStart, Date termEnd, Date startDate, Date endDate) {
		// nullを含む場合
		if (MospUtility.isEmpty(termStart, termEnd, startDate, endDate)) {
			// 重複しないと判断
			return false;
		}
		// 開始と終了の順序が逆である場合
		if (termStart.after(termEnd) || startDate.after(endDate)) {
			// 重複しないと判断
			return false;
		}
		// 期間開始日が対象終了日より後にある場合
		if (termStart.after(endDate)) {
			// 重複しないと判断
			return false;
		}
		// 期間終了日が対象開始日より前にある場合
		if (termEnd.before(startDate)) {
			// 重複しないと判断
			return false;
		}
		// 重複すると判断
		return true;
	}
	
	/**
	 * 同じ日時であるかを確認する。<br>
	 * どちらか一方でもnullである場合には、同じ日時でないと判断する。<br>
	 * @param date1 日時1
	 * @param date2 日時2
	 * @return 確認結果(true：同じ日時である、false：同じ日時でない)
	 */
	public static boolean isSame(Date date1, Date date2) {
		// どちらか一方でもnullである場合
		if (date1 == null || date2 == null) {
			// 同じ日時でないと判断
			return false;
		}
		// 同じ日時であるかを確認
		return date1.compareTo(date2) == 0;
	}
	
	/**
	 * 後(何年何か月何日後)の日付を取得する。<br>
	 * <br>
	 * 年、月、日の順で加算していく。<br>
	 * 例：2013/02/28の1年1か月1日後は、2014/03/29。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @param year       年(何年後)
	 * @param month      月(何か月後)
	 * @param day        日(何日後)
	 * @return 後(何年何か月何日後)の日付
	 */
	public static Date getDateAfter(Date targetDate, int year, int month, int day) {
		// 対象日確認
		if (targetDate == null) {
			return null;
		}
		// 年を加算
		Date after = addYear(targetDate, year);
		// 月を加算
		after = addMonth(after, month);
		// 日を加算
		return addDay(after, day);
	}
	
	/**
	 * 対象日が開始日から後(何年何か月何日後)の日付に含まれるかを確認する。<br>
	 * <br>
	 * 対象日がnullの場合、含まれないと判断する。<br>
	 * 最終日可否フラグがfalseである場合は、最終日は期間に含めない。<br>
	 * <br>
	 * @param targetDate       対象日
	 * @param startDate        開始日
	 * @param year             年(何年後)
	 * @param month            月(何か月後)
	 * @param day              日(何日後)
	 * @param isContainLastDay 最終日可否フラグ
	 * @return 確認結果(true：含まれる、false：含まれない)
	 */
	public static boolean isInDateAfter(Date targetDate, Date startDate, int year, int month, int day,
			boolean isContainLastDay) {
		// 対象日がnullの場合
		if (targetDate == null) {
			// 含まれないと判断
			return false;
		}
		// 後の日付を取得
		Date endDate = getDateAfter(startDate, year, month, day);
		// 最終日を期間に含めない場合
		if (isContainLastDay == false) {
			// 後の日付を一日減
			endDate = addDay(endDate, -1);
			
		}
		// 期間に対象日が含まれているか確認
		return isTermContain(targetDate, startDate, endDate);
	}
	
	/**
	 * 対象日時が有効期限を超えているかを確認する。<br>
	 * 基準日時+有効期限が対象日時と同じ場合は、超えていないと判断する。<br>
	 * 基準日時+有効期限の秒以下は切り捨てられる。<br>
	 * <br>
	 * @param targetDate    対象日時
	 * @param referenceDate 基準日時
	 * @param expiration    有効期限(分)
	 * @return 確認結果(true：超えている、false：超えていない)
	 */
	public static boolean isExpireMinute(Date targetDate, Date referenceDate, int expiration) {
		return targetDate.after(addMinute(referenceDate, expiration));
	}
	
	/**
	 * 対象日が有効期限を超えているかを確認する。<br>
	 * 基準日+有効期限が対象日と同じ場合は、超えていないと判断する。<br>
	 * 基準日+有効期限の時間以下は切り捨てられる。<br>
	 * <br>
	 * @param targetDate    対象日
	 * @param referenceDate 基準日
	 * @param expiration    有効期限(日)
	 * @return 確認結果(true：超えている、false：超えていない)
	 */
	public static boolean isExpireDay(Date targetDate, Date referenceDate, int expiration) {
		return targetDate.after(addDay(referenceDate, expiration));
	}
	
	/**
	 * 期間最終日を取得する。<br>
	 * システム日付が対象期間に含まれる場合はシステム日付を取得する。<br>
	 * @param startDate 対象期間開始日
	 * @param endDate   対象期間終了日
	 * @return 期間最終日
	 */
	public static Date getEndTargetDate(Date startDate, Date endDate) {
		// システム日付を取得
		Date systemDate = getSystemDate();
		// 期間内の場合
		if (isTermContain(systemDate, startDate, endDate)) {
			return systemDate;
		}
		// 期間開始より前の場合
		if (systemDate.compareTo(startDate) < 0) {
			return startDate;
		}
		// 期間終了より後の場合
		if (systemDate.compareTo(endDate) > 0) {
			return endDate;
		}
		return systemDate;
	}
	
	/**
	 * デフォルト日時かnullを取得する。<br>
	 * <br>
	 * @param isNullAvailable null可フラグ(true：nullを取得、false：デフォルト日時を取得)
	 * @return デフォルト日時かnull
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getDefaultTimeOrNull(boolean isNullAvailable) throws MospException {
		return isNullAvailable ? null : getDefaultTime();
	}
	
}
