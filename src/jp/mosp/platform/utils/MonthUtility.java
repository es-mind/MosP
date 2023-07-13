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
package jp.mosp.platform.utils;

import java.util.Calendar;
import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;

/**
 * 年月に関する有用なメソッドを提供する。<br>
 * <br>
 * 年月を指定して日付等の値を取得する場合、日付を指定して年月等の
 * 値を取得する場合は、全て当クラスのメソッドを用いて実施する。<br>
 * <br>
 * XMLファイルに記載されている基準日は、デフォルトを末日とする。<br>
 * <br>
 */
public class MonthUtility {
	
	/**
	 * MosPアプリケーション設定キー(年月指定時の基準日)。<br>
	 */
	public static final String	APP_YEAR_MONTH_TARGET_DATE	= "YearMonthTargetDate";
	
	/**
	 * MosPアプリケーション設定キー(年月指定時の期間の最終日)。<br>
	 */
	public static final String	APP_YEAR_MONTH_TERM			= "YearMonthTerm";
	
	/**
	 * 翌月の基準日判断係数。<br>
	 */
	public static final int		TARGET_DATE_NEXT_MONTH		= 100;
	
	/**
	 * 年月指定の期間最終日(月末)。<br>
	 */
	public static final int		TERM_DATE_LAST_DAY			= 0;
	
	/**
	 * 年度の開始月のデフォルト値(4月)。<br>
	 */
	public static final int		DEFAULT_FISCAL_START_MONTH	= 4;
	
	/**
	 * MosPアプリケーション設定キー(年度の開始月)。<br>
	 */
	public static final String	APP_FISCAL_START_MONTH		= "FiscalStartMonth";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MonthUtility() {
		// 処理無し
	}
	
	/**
	 * 年月指定時の基準日を取得する。<br>
	 * <br>
	 * 年月指定時に、有効日を指定して情報(人事情報等)を取得する場合の
	 * 基準日として、用いる。<br>
	 * <br>
	 * @param targetYear          指定年
	 * @param targetMonth         指定月
	 * @param yearMonthTargetDate 年月指定時の基準日
	 * @return 年月指定日の基準日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getYearMonthTargetDate(int targetYear, int targetMonth, int yearMonthTargetDate)
			throws MospException {
		// 基準日が末日か判断
		if (yearMonthTargetDate == TERM_DATE_LAST_DAY) {
			return getLastDateOfMonth(targetYear, targetMonth);
		}
		// 基準月用日付準備(指定年月の初日)
		Date date = getFirstDateOfMonth(targetYear, targetMonth);
		// 翌月判断
		if (yearMonthTargetDate > TARGET_DATE_NEXT_MONTH) {
			// 翌月にする
			date = DateUtility.addMonth(date, 1);
			// 基準日再取得
			yearMonthTargetDate = yearMonthTargetDate % TARGET_DATE_NEXT_MONTH;
		}
		// 年月基準日を取得する
		return DateUtility.getDate(DateUtility.getYear(date), DateUtility.getMonth(date), yearMonthTargetDate);
	}
	
	/**
	 * 年月指定時の基準日を取得する。<br>
	 * XMLファイルから基準日を取得する。<br>
	 * <br>
	 * @param targetYear  指定年
	 * @param targetMonth 指定月
	 * @param mospParams  MosP処理情報
	 * @return 年月指定日の基準日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getYearMonthTargetDate(int targetYear, int targetMonth, MospParams mospParams)
			throws MospException {
		// MosP処理情報から基準日を取得
		int yearMonthTargetDate = getYearMonthTargetDate(mospParams);
		// 年月指定時の基準日を取得
		return getYearMonthTargetDate(targetYear, targetMonth, yearMonthTargetDate);
	}
	
	/**
	 * 年月を表す日付(年月の初日)を取得する。<br>
	 * <br>
	 * DBに年月を登録する場合の日付等に、用いる。<br>
	 * <br>
	 * @param targetYear  指定年
	 * @param targetMonth 指定月
	 * @return 年月を表す日付(年月の初日)
	 */
	public static Date getYearMonthDate(int targetYear, int targetMonth) {
		return getFirstDateOfMonth(targetYear, targetMonth);
	}
	
	/**
	 * 指定年月における最終日を取得する。<br>
	 * @param targetYear  指定年
	 * @param targetMonth 指定月
	 * @return 指定年月における最終日
	 */
	public static Date getYearMonthLastDate(int targetYear, int targetMonth) {
		return getLastDateOfMonth(targetYear, targetMonth);
	}
	
	/**
	 * 年月指定時の期間の初日を取得する。<br>
	 * @param targetYear 指定年
	 * @param targetMonth 指定月
	 * @param yearMonthTerm 期間の最終日
	 * @return 年月を表す日付(年月の初日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getYearMonthTermFirstDate(int targetYear, int targetMonth, int yearMonthTerm)
			throws MospException {
		// 月末の場合
		if (yearMonthTerm == TERM_DATE_LAST_DAY) {
			// 期間最終日取得(対象年月の最終日)
			return getFirstDateOfMonth(targetYear, targetMonth);
		}
		// 当月期間開始日判断
		if (yearMonthTerm < TARGET_DATE_NEXT_MONTH) {
			// 当月の場合
			// 対象年月、期間最終日で日付を取得(期間初日作成準備)
			Date date = DateUtility.getDate(targetYear, targetMonth, yearMonthTerm);
			// 月を戻す
			date = DateUtility.addMonth(date, -1);
			// 日を加算(期間の翌日が初日)
			return DateUtility.addDay(date, 1);
		} else {
			// 翌月期間の最終日を取得
			yearMonthTerm = yearMonthTerm % TARGET_DATE_NEXT_MONTH;
			// 年、月、期間終了日で日付を取得(期間最終日作成準備)
			Date date = DateUtility.getDate(targetYear, targetMonth, yearMonthTerm);
			// 期間最終日設定(翌月の期間開始日)
			return DateUtility.addDay(date, 1);
		}
	}
	
	/**
	 * 年月指定時の期間の最終日を取得する。<br>
	 * XMLファイルから対象期間最終日を取得する。<br>
	 * <br>
	 * 年月指定時に、有効日を指定して情報(人事情報等)を取得する場合の
	 * 対象期間最終日として、用いる。<br>
	 * @param targetYear 指定年
	 * @param targetMonth 指定月
	 * @param yearMonthTerm 期間の最終日
	 * @return 年月を表す日付(年月の最終日付)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getYearMonthTermLastDate(int targetYear, int targetMonth, int yearMonthTerm)
			throws MospException {
		// 月末締の場合
		if (yearMonthTerm == TERM_DATE_LAST_DAY) {
			// 期間最終日取得(対象年月の最終日)
			return getLastDateOfMonth(targetYear, targetMonth);
		}
		// 当月判断
		if (yearMonthTerm < TARGET_DATE_NEXT_MONTH) {
			// 当月締の場合
			// 期間終了日を取得
			return DateUtility.getDate(targetYear, targetMonth, yearMonthTerm);
		} else {
			// 翌月締の場合
			// 翌月期間の最終日を取得
			yearMonthTerm = yearMonthTerm % TARGET_DATE_NEXT_MONTH;
			// 年、月、期間終了日で日付を取得
			Date date = DateUtility.getDate(targetYear, targetMonth, yearMonthTerm);
			// 期間最終日設定
			return DateUtility.addMonth(date, 1);
		}
	}
	
	/**
	 * 年月指定時の期間の初日を取得する。<br>
	 * <br>
	 * @param targetYear  指定年
	 * @param targetMonth 指定月
	 * @param mospParams  MosP処理情報
	 * @return 年月指定日の基準日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getYearMonthTermFirstDate(int targetYear, int targetMonth, MospParams mospParams)
			throws MospException {
		// MosP処理情報から期間の最終日を取得
		int yearMonthTerm = getYearMonthTerm(mospParams);
		// 年月指定時の基準日を取得
		return getYearMonthTermFirstDate(targetYear, targetMonth, yearMonthTerm);
	}
	
	/**
	 * 年月指定時の期間の最終日を取得する。<br>
	 * XMLファイルから対象期間最終日を取得する。<br>
	 * <br>
	 * 年月指定時に、有効日を指定して情報(人事情報等)を取得する場合の
	 * 対象期間最終日として、用いる。<br>
	 * @param targetYear  指定年
	 * @param targetMonth 指定月
	 * @param mospParams  MosP処理情報
	 * @return 年月指定日の基準日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getYearMonthTermLastDate(int targetYear, int targetMonth, MospParams mospParams)
			throws MospException {
		// MosP処理情報から期間の最終日を取得
		int yearMonthTerm = getYearMonthTerm(mospParams);
		// 年月指定時の基準日を取得
		return getYearMonthTermLastDate(targetYear, targetMonth, yearMonthTerm);
	}
	
	/**
	 * 日付指定時の基準年月(年月の初日)を取得する。<br>
	 * <br>
	 * 基準日から、指定日付が含まれる年月(基準年月)を算出する。<br>
	 * 検索プルダウンを設定する際など、基準日と指定日時により基準年月が変化する。<br>
	 * <br>
	 * 日付を指定して年月を表示する場合(メニューからメッセージ設定一覧を表示した際の
	 * 検索表示期間(システム日付→年月)等)に用いる。
	 * <br>
	 * @param targetDate          指定日付
	 * @param yearMonthTargetDate 年月指定時の基準日
	 * @return 日付指定日の基準年月(年月の初日)
	 */
	public static Date getTargetYearMonth(Date targetDate, int yearMonthTargetDate) {
		// 基準日が末日か判断
		if (yearMonthTargetDate == TERM_DATE_LAST_DAY) {
			// 指定日時の月の初日を取得
			return getFirstDateOfMonth(targetDate);
		}
		// 指定日時の日を取得
		int targetDay = DateUtility.getDay(targetDate);
		// 基準月用日付準備(指定日付の年月の初日)
		Date date = getFirstDateOfMonth(targetDate);
		// 翌月(翌1日～15日)判断
		if (yearMonthTargetDate > TARGET_DATE_NEXT_MONTH) {
			// 基準日再取得
			yearMonthTargetDate = yearMonthTargetDate % TARGET_DATE_NEXT_MONTH;
			// 指定日付の日が基準日以下の場合
			if (yearMonthTargetDate >= targetDay) {
				// 指定日付の年月の前月を取得
				date = DateUtility.addMonth(date, -1);
			}
			return date;
		}
		// 指定日付の日が基準日より大きい場合(翌1日～15日でなく)
		if (targetDay > yearMonthTargetDate) {
			// 指定日付の年月の翌月を取得
			date = DateUtility.addMonth(date, 1);
		}
		return date;
	}
	
	/**
	 * 日付指定時の基準年月(年月の初日)を取得する。<br>
	 * <br>
	 * XMLファイルから基準日を取得し、指定日付が含まれる年月(基準年月)を算出する。<br>
	 * <br>
	 * @param targetDate 指定日付
	 * @param mospParams MosP処理情報
	 * @return 日付指定日の基準年月(年月の初日)
	 */
	public static Date getTargetYearMonth(Date targetDate, MospParams mospParams) {
		// MosP処理情報から基準日を取得
		int yearMonthTargetDate = getYearMonthTargetDate(mospParams);
		// 日付指定時の基準年月(年月の初日)を取得
		return getTargetYearMonth(targetDate, yearMonthTargetDate);
	}
	
	/**
	 * 指定年月における初日を取得する。<br>
	 * @param targetYear  指定年
	 * @param targetMonth 指定月
	 * @return 指定年月における初日
	 */
	private static Date getFirstDateOfMonth(int targetYear, int targetMonth) {
		Calendar cal = getInitCalendar();
		cal.set(Calendar.YEAR, targetYear);
		cal.set(Calendar.MONTH, targetMonth - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMinimum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	/**
	 * 指定年月における初日を取得する。<br>
	 * @param targetDate 指定年月
	 * @return 対象年月における初日
	 */
	private static Date getFirstDateOfMonth(Date targetDate) {
		int targetYear = DateUtility.getYear(targetDate);
		int targetMonth = DateUtility.getMonth(targetDate);
		return getFirstDateOfMonth(targetYear, targetMonth);
	}
	
	/**
	 * 指定年月における最終日を取得する。<br>
	 * @param targetYear  指定年
	 * @param targetMonth 指定月
	 * @return 指定年月における最終日
	 */
	private static Date getLastDateOfMonth(int targetYear, int targetMonth) {
		Calendar cal = getInitCalendar();
		cal.set(Calendar.YEAR, targetYear);
		cal.set(Calendar.MONTH, targetMonth - 1);
		cal.set(Calendar.DAY_OF_MONTH, cal.getActualMaximum(Calendar.DAY_OF_MONTH));
		return cal.getTime();
	}
	
	/**
	 * 初期化されたカレンダオブジェクトを取得する。<br>
	 * @return 初期化されたカレンダオブジェクト
	 */
	private static Calendar getInitCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.setLenient(false);
		cal.clear();
		return cal;
	}
	
	/**
	 * 年月指定時の基準日を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 年月指定時の基準日
	 */
	private static int getYearMonthTargetDate(MospParams mospParams) {
		return mospParams.getApplicationProperty(APP_YEAR_MONTH_TARGET_DATE, TERM_DATE_LAST_DAY);
	}
	
	/**
	 * 年月指定時の期間の最終日を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 年月指定時の期間の最終日
	 */
	private static int getYearMonthTerm(MospParams mospParams) {
		return mospParams.getApplicationProperty(APP_YEAR_MONTH_TERM, TERM_DATE_LAST_DAY);
	}
	
	/**
	 * 年度の開始月を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 年度の開始月
	 */
	private static int getFicalStartMonth(MospParams mospParams) {
		return mospParams.getApplicationProperty(APP_FISCAL_START_MONTH, DEFAULT_FISCAL_START_MONTH);
	}
	
	/**
	 * 年度と月を指定して実際の年月(年月の初日)を取得する。<br>
	 * 例)対象年度：2011、対象月：3とした場合、2012年3月1日になる。<br>
	 * @param fiscalYear 対象年度
	 * @param month 対象月
	 * @param mospParams MosP処理情報
	 * @return 実際の年月(年月の初日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getFiscalYearMonth(int fiscalYear, int month, MospParams mospParams) throws MospException {
		// MosP処理情報から年度の開始月を取得
		int fiscalStartMonth = getFicalStartMonth(mospParams);
		// 実際の年を準備
		int year = fiscalYear;
		// 年度の開始月確認
		if (month < fiscalStartMonth) {
			year++;
		}
		// 年月の初日を取得
		return getYearMonthDate(year, month);
	}
	
	/**
	 * 年度を指定して対象年度の初日を取得する。<br>
	 * 例)対象年度：2011とした場合、2011年4月1日になる。<br>
	 * @param fiscalYear 対象年度
	 * @param mospParams MosP処理情報
	 * @return 対象年度の初日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getFiscalYearFirstDate(int fiscalYear, MospParams mospParams) throws MospException {
		// MosP処理情報から年度の開始月を取得
		int fiscalStartMonth = getFicalStartMonth(mospParams);
		// 実際の年を準備
		int year = fiscalYear;
		// 対象年度の初日を取得
		return getYearMonthTermFirstDate(year, fiscalStartMonth, mospParams);
	}
	
	/**
	 * 年度を指定して対象年度の最終日を取得する。<br>
	 * 例)対象年度：2011とした場合、2012年3月31日になる。<br>
	 * @param fiscalYear 対象年度
	 * @param mospParams MosP処理情報
	 * @return 対象年度の最終日
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getFiscalYearLastDate(int fiscalYear, MospParams mospParams) throws MospException {
		// 対象年に+1をした来年度の初日(4月1)を取得
		Date fiscalYearFirstDate = getFiscalYearFirstDate(fiscalYear + 1, mospParams);
		// 来年度の初日の前日
		return DateUtility.addDay(fiscalYearFirstDate, -1);
	}
	
	/**
	 * 対象日付が含まれる年度を取得する。
	 * @param date 対象日付
	 * @param mospParams MosP処理情報
	 * @return 対象日付が含まれる年度
	 */
	public static int getFiscalYear(Date date, MospParams mospParams) {
		// MosP処理情報から年度の開始月を取得
		int fiscalStartMonth = getFicalStartMonth(mospParams);
		// 基準年月(年月の初日)を取得
		Date targetYearMonth = getTargetYearMonth(date, mospParams);
		// 年月を取得
		int year = DateUtility.getYear(targetYearMonth);
		int month = DateUtility.getMonth(targetYearMonth);
		// 対象年月が含まれる年度を取得
		return getFiscalYear(fiscalStartMonth, year, month);
	}
	
	/**
	 * 対象年月が含まれる年度を取得する。
	 * @param fiscalStartMonth 年度の開始月
	 * @param year             対象年
	 * @param month            対象月
	 * @return 対象年月が含まれる年度
	 */
	public static int getFiscalYear(int fiscalStartMonth, int year, int month) {
		// MosP処理情報から年度の開始月を取得
		if (month < fiscalStartMonth) {
			year--;
		}
		return year;
	}
	
	/**
	 * 年度開始月(年度開始月の初日)を取得する。<br>
	 * <br>
	 * @param fiscalYear 対象年度
	 * @param mospParams MosP処理情報
	 * @return 年を表す日付(年度の初日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getFiscalStartMonth(int fiscalYear, MospParams mospParams) throws MospException {
		// MosP処理情報から年度の開始月を取得
		int fiscalStartMonth = getFicalStartMonth(mospParams);
		// 年度と月を指定して実際の年月(年月の初日)を取得
		return getFiscalYearMonth(fiscalYear, fiscalStartMonth, mospParams);
	}
	
	/**
	 * 年を表す日付(年度開始月の初日)を取得する。<br>
	 * <br>
	 * DBに年を登録する場合の日付等に、用いる。<br>
	 * <br>
	 * @param fiscalYear 対象年度
	 * @param mospParams MosP処理情報
	 * @return 年を表す日付(年度の初日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Date getYearDate(int fiscalYear, MospParams mospParams) throws MospException {
		// 年度開始月(年度開始月の初日)を取得
		return getFiscalStartMonth(fiscalYear, mospParams);
	}
	
}
