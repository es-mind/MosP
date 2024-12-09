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

import java.util.Date;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.ValidateUtility;

/**
 * 入力チェックに有効な共通機能を提供する。<br>
 */
public class InputCheckUtility {
	
	/**
	 * 必須入力確認。
	 * @param mospParams  共通引数
	 * @param targetValue 対象値
	 * @param fieldName   フィールド名
	 */
	public static void checkRequired(MospParams mospParams, Object targetValue, String fieldName) {
		if (!ValidateUtility.chkRequired(targetValue)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, fieldName);
		}
	}
	
	/**
	 * 対象文字列が半角英数字であることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param mospParams 共通設定
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	public static void checkTypeCodeGeneral(MospParams mospParams, String value, String fieldName, Integer row) {
		// 文字列(半角英数字)を確認
		if (ValidateUtility.chkRegex("[A-Za-z0-9]*", value) == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorCheckAlpNum(mospParams, fieldName, row);
		}
	}
	
	/**
	 * 対象文字列がカナであることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param mospParams 共通設定
	 * @param value     確認対象文字列
	 * @param fieldName 確認対象フィールド名
	 * @param row       行インデックス
	 */
	public static void checkTypeKanaGeneral(MospParams mospParams, String value, String fieldName, Integer row) {
		// 文字列長(カナ)を確認
		if (ValidateUtility.chkRegex("[｡-ﾟ -~]*", value) == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorCheckForm(mospParams, fieldName, row);
		}
	}
	
	/**
	 * 数値妥当性確認。
	 * @param mospParams MosP処理情報
	 * @param value      対象値
	 * @param fieldName  フィールド名
	 */
	public static void checkNumber(MospParams mospParams, String value, String fieldName) {
		String reqValue = value;
		if (reqValue == null) {
			reqValue = String.valueOf(0);
		}
		if (!chkNumber(reqValue)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorCheckNumeric(mospParams, fieldName);
		}
	}
	
	/**
	 * コード妥当性確認。
	 * @param mospParams MosP処理情報
	 * @param value      対象値
	 * @param fieldName  フィールド名
	 */
	public static void checkCode(MospParams mospParams, String value, String fieldName) {
		String reqValue = value;
		if (reqValue == null) {
			reqValue = MospConst.STR_EMPTY;
		}
		if (!chkCode(reqValue)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorCheckAlpNum(mospParams, fieldName);
		}
	}
	
	/**
	 * 日付妥当性確認
	 * @param mospParams MosP処理情報
	 * @param year       年
	 * @param month      月
	 * @param day        日
	 * @param fieldName  フィールド名
	 */
	public static void checkDate(MospParams mospParams, int year, int month, int day, String fieldName) {
		if (!ValidateUtility.chkDate(year, month - 1, day)) {
			// 日付の妥当性チェックメッセージを設定
			PfMessageUtility.addErrorCheckDate(mospParams, fieldName);
		}
	}
	
	/**
	 * 日付妥当性確認。
	 * @param mospParams MosP処理情報
	 * @param year       年
	 * @param month      月
	 * @param day        日
	 * @param fieldName  フィールド名
	 */
	public static void checkDate(MospParams mospParams, String year, String month, String day, String fieldName) {
		try {
			checkDate(mospParams, Integer.parseInt(year), Integer.parseInt(month), Integer.parseInt(day), fieldName);
		} catch (NumberFormatException e) {
			// 日付の妥当性チェックメッセージを設定
			PfMessageUtility.addErrorCheckDate(mospParams, fieldName);
		}
	}
	
	/**
	 * 日付妥当性確認(人事汎用機能用)。
	 * @param mospParams MosP処理情報
	 * @param year       年
	 * @param month      月
	 * @param day        日
	 * @param fieldName  フィールド名
	 */
	public static void checkDateGeneral(MospParams mospParams, String year, String month, String day,
			String[] fieldName) {
		// 空の場合はチェックしない
		if (year == null || month == null || day == null) {
			return;
		}
		if (year.isEmpty() && month.isEmpty() && day.isEmpty()) {
			return;
		}
		
		try {
			// 勤務形態インポートでは対象(年)、人事汎用では項目名1つのため
			String fieldName1 = fieldName[0];
			String fieldName2 = fieldName[0];
			String fieldName3 = fieldName[0];
			if (fieldName.length == 3) {
				fieldName2 = fieldName[1];
				fieldName3 = fieldName[2];
			}
			// 年妥当性確認
			checkYear(mospParams, year, fieldName1);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 月妥当性確認
			checkMonth(mospParams, month, fieldName2);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			if (!chkNumber(day)) {
				// 形式チェック(数値)
				PfMessageUtility.addErrorCheckForm(mospParams, fieldName3);
				return;
			}
			
			// 日付チェック
			if (!ValidateUtility.chkDate(Integer.parseInt(year), Integer.parseInt(month) - 1, Integer.parseInt(day))) {
				// 日付形式チェック
				PfMessageUtility.addErrorCheckForm(mospParams, fieldName);
				return;
				
			}
		} catch (NumberFormatException e) {
			// 日付形式チェック
			PfMessageUtility.addErrorCheckForm(mospParams, fieldName);
		}
		
	}
	
	/**
	 * 年妥当性確認。<br>
	 * @param mospParams MosP処理情報
	 * @param year 年
	 * @param fieldName 項目名(月)
	 */
	public static void checkYear(MospParams mospParams, String year, String fieldName) {
		try {
			if (!chkNumber(year)) {
				// 形式チェック(数値)
				PfMessageUtility.addErrorCheckForm(mospParams, fieldName);
				return;
			}
			// 年の超過確認
			if (!ValidateUtility.chkLength(year, 4)) {
				// 桁数エラー
				PfMessageUtility.addErrorCheckDigit(mospParams, fieldName, 4);
				return;
			}
			// 1000年未満のチェック
			if (Integer.parseInt(year) < 1000) {
				// 桁数エラー
				PfMessageUtility.addErrorCheckYesr(mospParams, 1000);
				return;
			}
		} catch (NumberFormatException e) {
			// 日付形式チェック
			PfMessageUtility.addErrorCheckForm(mospParams, fieldName);
		}
	}
	
	/**
	 * 年妥当性確認。<br>
	 * @param mospParams MosP処理情報
	 * @param month 月
	 * @param fieldName 名称(月)
	 */
	public static void checkMonth(MospParams mospParams, String month, String fieldName) {
		try {
			if (!chkNumber(month)) {
				// 形式チェック(数値)
				PfMessageUtility.addErrorCheckForm(mospParams, fieldName);
				return;
			}
			if (!ValidateUtility.chkLength(month, 2)) {
				// 桁数エラー
				PfMessageUtility.addErrorCheckDigit(mospParams, fieldName, 2);
				return;
			}
			// 1以上チェック
			if (Integer.parseInt(month) < 1) {
				// エラーメッセージ追加
				PfMessageUtility.addErrorUnderLimit(mospParams, fieldName, 1, null);
			}
			// 12以下チェック
			if (Integer.parseInt(month) > 12) {
				// エラーメッセージ追加
				PfMessageUtility.addErrorOverLimit(mospParams, fieldName, 12, null);
			}
		} catch (NumberFormatException e) {
			// 日付形式チェック
			PfMessageUtility.addErrorCheckForm(mospParams, fieldName);
		}
	}
	
	/**
	 * 日付の順序を確認する。<br>
	 * 同じ日付の場合は、正しいと判断する。<br>
	 * @param mospParams MosP処理情報
	 * @param beforeDate 前にくるべき日付
	 * @param afterDate  後にくるべき日付
	 * @param beforName  前にくるべき日付の名称
	 * @param afterName  後にくるべき日付の名称
	 */
	public static void checkDateOrder(MospParams mospParams, Date beforeDate, Date afterDate, String beforName,
			String afterName) {
		// 日付にnullが含まれる場合
		if (MospUtility.isEmpty(beforeDate, afterDate)) {
			// 処理終了(確認不要)
			return;
		}
		// 期間妥当性エラー
		if (beforeDate.after(afterDate)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorDateOrder(mospParams, afterName, beforName);
		}
	}
	
	/**
	 * 桁数(最大桁数)を確認する。<br>
	 * @param mospParams MosP処理情報
	 * @param value      確認対象
	 * @param maxDigit   最大桁数
	 * @param filedName  確認対象フィールド名
	 * @param row        行インデックス
	 */
	public static void checkMaxDigit(MospParams mospParams, int value, int maxDigit, String filedName, Integer row) {
		// 桁数が最大桁数以内である場合
		if (ValidateUtility.chkLength(MospUtility.getString(value), maxDigit)) {
			// 処理終了
			return;
		}
		// エラーメッセージを設定(桁数(最大桁数)が妥当でない場合)
		PfMessageUtility.addErrorOverDigit(mospParams, filedName, maxDigit, row);
	}
	
	/**
	 * 文字列長確認(最大文字数)。<br>
	 * @param mospParams 共通引数
	 * @param value     確認対象文字列
	 * @param maxLength 最大文字数
	 * @param targetName 対象項目名
	 */
	public static void checkLength(MospParams mospParams, String value, int maxLength, String targetName) {
		if (value == null) {
			return;
		}
		if (!ValidateUtility.chkLength(value, maxLength)) {
			// 桁数エラー
			PfMessageUtility.addErrorCheckDigit(mospParams, targetName, maxLength);
		}
	}
	
	/**
	 * 対象値が小数であることを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param mospParams   MosP処理情報
	 * @param value        確認対象値
	 * @param integerDigit 整数部桁数
	 * @param decimalDigit 小数部桁数
	 * @param fieldName    確認対象フィールド名
	 * @param row          行インデックス
	 */
	public static void checDecimal(MospParams mospParams, double value, int integerDigit, int decimalDigit,
			String fieldName, Integer row) {
		// 妥当な小数である場合
		if (ValidateUtility.chkDecimal(value, integerDigit, decimalDigit)) {
			// 処理終了
			return;
		}
		// エラーメッセージを設定(妥当な少数でない場合)
		PfMessageUtility.addErrorInvalidDecimal(mospParams, fieldName, integerDigit, decimalDigit, row);
	}
	
	private static boolean chkCode(String value) {
		return ValidateUtility.chkRegex("\\w*", value);
	}
	
	private static boolean chkNumber(String value) {
		return ValidateUtility.chkRegex("\\d*", value);
	}
	
}
