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
package jp.mosp.platform.utils;

import java.text.DecimalFormat;
import java.util.Date;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.NameUtility;

/**
 * 文字列変換に関するユーティリティクラス。
 * <br>
 * 日付や数値を文字列に変換する際に用いる。<br>
 * プラットフォームにおいて変換される文字列は、
 * 全てこのクラスを通じて変換される(予定)。<br>
 * <br>
 */
public class TransStringUtility {
	
	/**
	 * 日付を文字列に変換する。<br>
	 * @param date   日付オブジェクト
	 * @param format フォーマット
	 * @return 日付文字列
	 */
	public static String getStringDate(Date date, String format) {
		// 日付を文字列に変換
		return DateUtility.getStringDate(date, format);
	}
	
	/**
	 * 日付を文字列(HH:mm)に変換する。<br>
	 * 基準日付との差を文字列にして取得する。<br>
	 * <br>
	 * @param mospParams       MosP処理情報
	 * @param date             対象日付
	 * @param standardDate     基準日付
	 * @param isHyphenWhenNull null時ハイフン表示要否(true：null時ハイフン、false：null時空白)
	 * @return 日付文字列(HH:mm)
	 */
	public static String getHourColonMinute(MospParams mospParams, Date date, Date standardDate,
			boolean isHyphenWhenNull) {
		// 区切文字を準備
		String separator = PfNameUtility.singleColon(mospParams);
		// 日付文字列(時間.分)を取得
		return getHourSeparatorMinute(mospParams, date, standardDate, separator, true, isHyphenWhenNull);
	}
	
	/**
	 * 日付を文字列(HH.mm)に変換する。<br>
	 * 基準日付との差を文字列にして取得する。<br>
	 * <br>
	 * @param mospParams       MosP処理情報
	 * @param date             対象日付
	 * @param standardDate     基準日付
	 * @param isZeroPadded     時間前ゼロ要否(true：時間2桁前ゼロ要、false：前ゼロ不要)
	 * @param isHyphenWhenNull null時ハイフン表示要否(true：null時ハイフン、false：null時空白)
	 * @return 日付文字列(HH.mm)
	 */
	public static String getHourPeriodMinute(MospParams mospParams, Date date, Date standardDate, boolean isZeroPadded,
			boolean isHyphenWhenNull) {
		// 区切文字を準備
		String separator = PfNameUtility.period(mospParams);
		// 日付文字列(時間.分)を取得
		return getHourSeparatorMinute(mospParams, date, standardDate, separator, isZeroPadded, isHyphenWhenNull);
	}
	
	/**
	 * 日付を文字列(HH.mm)に変換する。<br>
	 * 基準日付との差を文字列にして取得する。<br>
	 * <br>
	 * 例：<br>
	 * 基準時刻が2020/01/01 0:00、日付が2020/01/02 9:00、区切文字が:である場合、
	 * 結果は33:00。<br>
	 * <br>
	 * @param mospParams       MosP処理情報
	 * @param date             対象日付
	 * @param standardDate     基準日付
	 * @param separator        区切文字
	 * @param isZeroPadded     時間前ゼロ要否(true：時間2桁前ゼロ要、false：前ゼロ不要)
	 * @param isHyphenWhenNull null時ハイフン表示要否(true：null時ハイフン、false：null時空白)
	 * @return 日付文字列(HH区切文字mm)
	 */
	protected static String getHourSeparatorMinute(MospParams mospParams, Date date, Date standardDate,
			String separator, boolean isZeroPadded, boolean isHyphenWhenNull) {
		// nullがある場合
		if (MospUtility.isEmpty(date, standardDate)) {
			// ハイフンか空白を取得
			return getHyphenOrBlank(mospParams, isHyphenWhenNull);
		}
		// 文字列を準備
		StringBuffer sb = new StringBuffer();
		// 時間前ゼロ要否を確認
		if (isZeroPadded) {
			sb.append(DateUtility.getStringHour(date, standardDate));
		} else {
			sb.append(DateUtility.getStringHourH(date, standardDate));
		}
		sb.append(separator);
		sb.append(DateUtility.getStringMinute(date));
		// 文字列を取得
		return sb.toString();
	}
	
	/**
	 * 日付(期間)を文字列(HH:mm～HH:mm)に変換する。<br>
	 * @param mospParams   MosP処理情報
	 * @param fromDate     日付オブジェクト(From)
	 * @param toDate       日付オブジェクト(To)
	 * @param standardDate 基準日付
	 * @return 日付文字列(HH:mm～HH:mm)
	 */
	public static String getHourColonMinuteTerm(MospParams mospParams, Date fromDate, Date toDate, Date standardDate) {
		// 引数が不足している場合
		if (MospUtility.isEmpty(fromDate, toDate, standardDate)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 日付文字列を準備
		StringBuilder sb = new StringBuilder();
		// 日付オブジェクト(From)を設定
		sb.append(getHourColonMinute(mospParams, fromDate, standardDate, false));
		// ～を設定
		sb.append(NameUtility.wave(mospParams));
		// 日付オブジェクト(To)を設定
		sb.append(getHourColonMinute(mospParams, toDate, standardDate, false));
		// 日付文字列を取得
		return sb.toString();
	}
	
	/**
	 * 日付(期間)を文字列(yyyy年MM月～yyyy年MM月)に変換する。<br>
	 * @param mospParams MosP処理情報
	 * @param fromDate   日付オブジェクト(From)
	 * @param toDate     日付オブジェクト(To)
	 * @return 日付文字列(yyyy年MM月～yyyy年MM月)
	 */
	public static String getJapaneseMonthTerm(MospParams mospParams, Date fromDate, Date toDate) {
		// 日付文字列を準備
		StringBuilder sb = new StringBuilder();
		// 日付オブジェクト(From)を設定
		sb.append(DateUtility.getStringJapaneseMonth(fromDate));
		// ～を設定
		sb.append(NameUtility.wave(mospParams));
		// 日付オブジェクト(To)を設定
		sb.append(DateUtility.getStringJapaneseMonth(toDate));
		// 日付文字列を取得
		return sb.toString();
	}
	
	/**
	 * 日付(期間)を文字列(yyyy年MM月dd日～yyyy年MM月dd日)に変換する。<br>
	 * @param mospParams MosP処理情報
	 * @param fromDate   日付オブジェクト(From)
	 * @param toDate     日付オブジェクト(To)
	 * @return 日付文字列(yyyy年MM月dd日～yyyy年MM月dd日)
	 */
	public static String getJapaneseDateTerm(MospParams mospParams, Date fromDate, Date toDate) {
		// 日付文字列を準備
		StringBuilder sb = new StringBuilder();
		// 日付オブジェクト(From)を設定
		sb.append(DateUtility.getStringJapaneseDate(fromDate));
		// ～を設定
		sb.append(NameUtility.wave(mospParams));
		// 日付オブジェクト(To)を設定
		sb.append(DateUtility.getStringJapaneseDate(toDate));
		// 日付文字列を取得
		return sb.toString();
	}
	
	/**
	 * 日付を文字列(yyyy年MM月dd日(E))に変換する。<br>
	 * @param date   日付オブジェクト
	 * @return 日付文字列(yyyy年MM月dd日(E))
	 */
	public static String getJapaneseDateAndDay(Date date) {
		// 日付を文字列(yyyy年MM月dd日(E))に変換
		return getStringDate(date, "yyyy年MM月dd日(E)");
	}
	
	/**
	 * 日と時間を文字列(days日hours時間)に変換する。<br>
	 * 時間は、0より大きい場合のみ変換する。<br>
	 * @param mospParams MosP処理情報
	 * @param days       日数
	 * @param hours      時間数
	 * @return 文字列(days日hours時間)
	 */
	public static String getJapaneaseDaysAndHours(MospParams mospParams, int days, int hours) {
		// 文字列(days日hours時間)を準備
		StringBuilder sb = new StringBuilder();
		sb.append(days);
		sb.append(PfNameUtility.day(mospParams));
		// 時間数が0より大きい場合
		if (hours > 0) {
			sb.append(hours);
			sb.append(PfNameUtility.time(mospParams));
		}
		// 文字列(days日hours時間)を取得
		return sb.toString();
	}
	
	/**
	 * 数値(時間か分)を文字列(前ゼロ付きの2桁)に変換する。<br>
	 * @param time 時間か分
	 * @return 時間か分の文字列(前ゼロ付きの2桁)
	 */
	public static String getPaddingTime(int time) {
		// フォーマット準備
		DecimalFormat format = new DecimalFormat("00");
		// フォーマット
		return format.format(time);
	}
	
	/**
	 * 数値オブジェクト(回数等)を文字列に変換する。<br>
	 * @param mospParams       MosP処理情報
	 * @param times            回数
	 * @param isHyphenWhenNull null時ハイフン表示要否(true：null時ハイフン、false：null時空白)
	 * @return 数値オブジェクト(回数等)文字列
	 */
	public static String getIntegerTimes(MospParams mospParams, Integer times, boolean isHyphenWhenNull) {
		// 回数がnullである場合
		if (MospUtility.isEmpty(times)) {
			// ハイフンか空白を取得
			return getHyphenOrBlank(mospParams, isHyphenWhenNull);
		}
		// 回数文字列を取得
		return times.toString();
	}
	
	/**
	 * 数値オブジェクト(回数等)を文字列に変換する。<br>
	 * @param mospParams       MosP処理情報
	 * @param times            回数
	 * @param isHyphenWhenNull null時ハイフン表示要否(true：null時ハイフン、false：null時空白)
	 * @param isDecimal        小数点要否(true：小数点要(1の場合に1.0となる)、false：小数点不要)
	 * @return 数値オブジェクト(回数等)文字列
	 */
	public static String getFloatTimes(MospParams mospParams, Float times, boolean isHyphenWhenNull,
			boolean isDecimal) {
		// 回数がnullである場合
		if (MospUtility.isEmpty(times)) {
			// ハイフンか空白を取得
			return getHyphenOrBlank(mospParams, isHyphenWhenNull);
		}
		// フォーマットを準備
		DecimalFormat df = new DecimalFormat("#.#");
		// 小数点要である場合
		if (isDecimal) {
			df = new DecimalFormat("0.0");
		}
		// 数値オブジェクト(回数)を文字列に変換
		return df.format(times);
	}
	
	/**
	 * 数値オブジェクト(回数等)を文字列に変換する。<br>
	 * @param mospParams       MosP処理情報
	 * @param times            回数
	 * @param isHyphenWhenNull null時ハイフン表示要否(true：null時ハイフン、false：null時空白)
	 * @param isDecimal        小数点要否(true：小数点要(1の場合に1.0となる)、false：小数点不要)
	 * @return 数値オブジェクト(回数等)文字列
	 */
	public static String getDoubleTimes(MospParams mospParams, Double times, boolean isHyphenWhenNull,
			boolean isDecimal) {
		// 回数がnullである場合
		if (MospUtility.isEmpty(times)) {
			// ハイフンか空白を取得
			return getHyphenOrBlank(mospParams, isHyphenWhenNull);
		}
		// フォーマットを準備
		DecimalFormat df = new DecimalFormat("#.#");
		// 小数点要である場合
		if (isDecimal) {
			df = new DecimalFormat("0.0");
		}
		// 数値オブジェクト(回数)を文字列に変換
		return df.format(times);
	}
	
	/**
	 * ハイフンか空白を取得する。<br>
	 * @param mospParams       MosP処理情報
	 * @param isHyphenWhenNull null時ハイフン表示要否(true：null時ハイフン、false：null時空白)
	 * @return ハイフンか空白
	 */
	protected static String getHyphenOrBlank(MospParams mospParams, boolean isHyphenWhenNull) {
		// ハイフンか空白を取得
		return isHyphenWhenNull ? PfNameUtility.hyphen(mospParams) : MospConst.STR_EMPTY;
	}
	
}
