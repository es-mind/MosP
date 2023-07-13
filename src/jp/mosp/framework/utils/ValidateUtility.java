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
package jp.mosp.framework.utils;

import java.io.UnsupportedEncodingException;
import java.math.BigDecimal;
import java.util.Calendar;
import java.util.Date;

import jp.mosp.framework.constant.MospConst;

/**
 * 妥当性確認に有用なメソッドを提供する。<br>
 * 確認結果を返すメソッドを集めている。<br>
 * MosP処理情報にエラーメッセージを設定する場合は、jp.mosp.platform.utils.InputCheckUtilityを利用する。<br>
 */
public final class ValidateUtility {
	
	/**
	 * 正規表現(小数)。<br>
	 */
	protected static final String REG_DECIMAL = "[0-9]{0,%1%}(\\.([0-9]{1,%2%}))?";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private ValidateUtility() {
		// 処理無し
	}
	
	/**
	 * 必須確認。<br>
	 * @param value 確認対象
	 * @return 確認結果（true：入力がある、false：入力がない）
	 */
	public static boolean chkRequired(Object value) {
		if (value instanceof String) {
			return !value.equals(MospConst.STR_EMPTY);
		}
		return value != null;
	}
	
	/**
	 * 正規表現による文字列パターン確認。<br>
	 * @param regex 正規表現
	 * @param value 確認対象
	 * @return 確認結果（true：同じである、false：同じでない）
	 */
	public static boolean chkRegex(String regex, String value) {
		if (value.matches(regex)) {
			return true;
		}
		return false;
	}
	
	/**
	 * 数値(double)確認。<br>
	 * @param value 確認対象
	 * @return 確認結果（true：doubleにできる、false：doubleにできない）
	 */
	public static boolean chkNumeric(String value) {
		try {
			Double.parseDouble(value);
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	/**
	 * 小数を確認する。<br>
	 * @param value        確認対象
	 * @param integerDigit 整数部桁数
	 * @param decimalDigit 小数部桁数
	 * @return 確認結果（true：超えていない、false：超えている）
	 */
	public static boolean chkDecimal(double value, int integerDigit, int decimalDigit) {
		// 正規表現取得
		String regex = getReplacedString(REG_DECIMAL, String.valueOf(integerDigit), String.valueOf(decimalDigit));
		// 正規表現による文字列パターン確認
		return chkRegex(regex, MospUtility.getString(value));
	}
	
	/**
	 * 置換した文字列を取得する。<br>
	 * <br>
	 * %i%(iは置換文字列の数)を置換文字列に置き換える。<br>
	 * <br>
	 * @param value 文字列
	 * @param rep   置換文字列
	 * @return 置換した文字列
	 */
	protected static String getReplacedString(String value, String... rep) {
		// 文字列準備
		String replaced = value;
		// 置換文字列が無い場合
		if (rep == null) {
			// 文字列をそのまま取得
			return value;
		}
		// 置換文字列毎に処理
		for (int i = 0; i < rep.length; i++) {
			// 置換
			replaced = replaced.replaceAll("%" + String.valueOf(i + 1) + "%", rep[i]);
		}
		// 置換した文字列を取得
		return replaced;
	}
	
	/**
	 * 割り切れるかを確認する。<br>
	 * @param value   確認対象
	 * @param divisor 除数
	 * @return 確認結果（true：割り切れる、false：割り切れない）
	 */
	public static boolean chkIndivisible(double value, float divisor) {
		// 確認対象を取得
		BigDecimal bd = BigDecimal.valueOf(value);
		// 剰余を計算
		BigDecimal remainder = bd.remainder(BigDecimal.valueOf(divisor));
		// 割り切れるかを確認
		return remainder.compareTo(BigDecimal.ZERO) == 0;
	}
	
	/**
	 * 日付妥当性確認。<br>
	 * @param year   年
	 * @param month  月(0～11)
	 * @param day    日
	 * @return 確認結果（true：日付である、false：日付でない）
	 */
	public static boolean chkDate(int year, int month, int day) {
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.set(year, month, day);
		try {
			calendar.getTime();
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	/**
	 * 時間妥当性確認。<br>
	 * @param hour   時
	 * @param minute 分
	 * @param second 秒
	 * @return 確認結果（true：時間である、false：時間でない）
	 */
	public static boolean chkTime(int hour, int minute, int second) {
		Calendar calendar = Calendar.getInstance();
		calendar.setLenient(false);
		calendar.set(MospConst.DEFAULT_YEAR, 0, 1, hour, minute, second);
		try {
			calendar.getTime();
			return true;
		} catch (IllegalArgumentException e) {
			return false;
		}
	}
	
	/**
	 * 期間妥当性確認。<br>
	 * @param date      確認対象日
	 * @param startDate 開始日
	 * @param endDate   終了日
	 * @return 確認結果（true：期間である、false：期間でない）
	 */
	public static boolean chkTerm(Date date, Date startDate, Date endDate) {
		if (startDate.compareTo(date) > 0 || endDate.compareTo(date) < 0) {
			return false;
		}
		return true;
	}
	
	/**
	 * 文字列長確認(合致)。<br>
	 * @param value     確認対象文字列
	 * @param length 入力文字数
	 * @return 確認結果（true：合致する、false：合致しない）
	 */
	public static boolean chkInputLength(String value, int length) {
		int valueLength = value.length();
		if (valueLength == length) {
			return true;
		}
		return false;
	}
	
	/**
	 * 文字列長確認(最大文字数)。<br>
	 * @param value     確認対象文字列
	 * @param maxLength 最大文字数
	 * @return 確認結果（true：超えていない、false：超えている）
	 */
	public static boolean chkLength(String value, int maxLength) {
		String regex = "(?s).{0," + String.valueOf(maxLength) + "}";
		if (!ValidateUtility.chkRegex(regex, value)) {
			return false;
		}
		return true;
	}
	
	/**
	 * バイト数(表示上)確認。<br>
	 * @param value     確認対象文字列
	 * @param maxLength 最大バイト数(表示上)
	 * @return 確認結果（true：超えていない、false：超えている）
	 */
	public static boolean chkByteLength(String value, int maxLength) {
		// 値確認
		if (value == null || value.isEmpty()) {
			return true;
		}
		// バイト数確認(日本語を2バイトとして計算するためShift-JISでバイト数を取得)
		try {
			return value.getBytes("Shift-JIS").length <= maxLength;
		} catch (UnsupportedEncodingException e) {
			return MospUtility.getBytes(value).length <= maxLength;
		}
	}
	
	/**
	 * 時間の重複チェック
	 * @param bStart 対象の開始時間
	 * @param bEnd 対象の終了時間
	 * @param aStart 比べる先の開始時間
	 * @param aEnd 比べる先の終了時間
	 * @return 確認結果（true：時間の重複あり、false：時間の重複無し）
	 */
	public static boolean chkDuplTime(Date bStart, Date bEnd, Date aStart, Date aEnd) {
		if (bStart == null || bEnd == null) {
			return false;
		}
		if (aStart != null && aEnd != null) {
			return bEnd.after(aStart) && bStart.before(aEnd);
		}
		return false;
	}
	
	/**
	 * 休憩が勤務時間内に取得しているか確認を行う。
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param restStartTime 休憩開始時間
	 * @param restEndTime 休憩終了時間
	 * @return 確認結果(true：勤務時間内でない、false：勤務時間内)
	 */
	public static boolean chkRestTime(Date startTime, Date endTime, Date restStartTime, Date restEndTime) {
		// 休憩時刻がある場合
		if (restStartTime != null && restEndTime != null) {
			// 始業時刻が休憩開始時刻より後の場合
			if (startTime != null && startTime.after(restStartTime)) {
				return true;
			}
			// 終業時刻が休憩終了時刻より前の場合
			if (endTime != null && endTime.before(restEndTime)) {
				return true;
			}
		}
		return false;
	}
}
