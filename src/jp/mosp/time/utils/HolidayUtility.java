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
package jp.mosp.time.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.utils.MonthUtility;

/**
 * 祝日クラス。<br>
 */
public class HolidayUtility {
	
	/**
	 * 文言コード(成人の日)。
	 */
	protected static final String			NAM_COMING_OF_AGE_DAY			= "ComingOfAgeDay";
	
	/**
	 * 文言コード(海の日)。
	 */
	protected static final String			NAM_MARINE_DAY					= "MarineDay";
	
	/**
	 * 文言コード(山の日)。
	 */
	protected static final String			NAM_MOUNT_DAY					= "MountDay";
	
	/**
	 * 文言コード(敬老の日)。
	 */
	protected static final String			NAM_RESPECT_FOR_THE_AGED_DAY	= "RespectForTheAgedDay";
	
	/**
	 * 文言コード(体育の日)。
	 */
	protected static final String			NAM_SPORTS_DAY					= "SportsDay";
	
	/**
	 * 文言コード(体育の日)。
	 */
	protected static final String			NAM_SPORTS_DAY_OLD				= "SportsDayOld";
	
	/**
	 * 文言コード(春分の日)。
	 */
	protected static final String			NAM_VERNAL_EQUINOX_DAY			= "VernalEquinoxDay";
	
	/**
	 * 文言コード(秋分の日)。
	 */
	protected static final String			NAM_AUTUMNAL_EQUINOX_DAY		= "AutumnalEquinoxDay";
	
	/**
	 * 文言コード(元日)。
	 */
	protected static final String			NAM_NEW_YEARS_DAY				= "NewYearsDay";
	
	/**
	 * 文言コード(建国記念の日)。
	 */
	protected static final String			NAM_NATIONAL_FOUNDATION_DAY		= "NationalFoundationDay";
	
	/**
	 * 文言コード(昭和の日)。
	 */
	protected static final String			NAM_SHOWA_DAY					= "ShowaDay";
	
	/**
	 * 文言コード(憲法記念日)。
	 */
	protected static final String			NAM_CONSTITUTION_DAY			= "ConstitutionDay";
	
	/**
	 * 文言コード(みどりの日)。
	 */
	protected static final String			NAM_GREENERY_DAY				= "GreeneryDay";
	
	/**
	 * 文言コード(こどもの日)。
	 */
	protected static final String			NAM_CHILDRENS_DAY				= "ChildrensDay";
	
	/**
	 * 文言コード(文化の日)。
	 */
	protected static final String			NAM_CULTURE_DAY					= "CultureDay";
	
	/**
	 * 文言コード(勤労感謝の日)。
	 */
	protected static final String			NAM_LABOR_THANKSGIVING_DAY		= "LaborThanksgivingDay";
	
	/**
	 * 文言コード(天皇誕生日)。
	 */
	protected static final String			NAM_EMPERORS_BIRTHDAY			= "EmperorsBirthday";
	
	/**
	 * 文言コード(国民の休日)。
	 */
	protected static final String			NAM_PEOPLES_DAY					= "PeoplesDay";
	
	/**
	 * 文言コード(振替休日)。
	 */
	protected static final String			NAM_OBSERVED_HOLIDAY			= "ObservedHoliday";
	
	/**
	 * 文言コード(天皇即位の礼)
	 */
	protected static final String			NAM_ACCESSION_DAY				= "AccessionDay";
	
	/**
	 * 文言コード(即位礼正殿の儀)
	 */
	protected static final String			NAM_CORONATION_CEREMONY			= "CoronationCeremony";
	
	/**
	 * 年度毎の祝日マップ。<br>
	 * key:年度<br>
	 * value:祝日マップ<br>
	 * valueの祝日マップの中身は次の通り。<br>
	 * key:祝日<br>
	 * value:祝日名<br>
	 * <br>
	 * 他パッケージに変更されないよう、パッケージプロテクテッドとする。<br>
	 */
	static Map<Integer, Map<Date, String>>	holidayMap;
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private HolidayUtility() {
		// 処理無し
	}
	
	/**
	 * 対象日の祝日確認を行う。<br>
	 * @param targetDate 対象日
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：祝日、false：祝日でない)
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static boolean isHoliday(Date targetDate, MospParams mospParams) throws MospException {
		// 対象日付の年度を取得
		int fisicalYear = MonthUtility.getFiscalYear(targetDate, mospParams);
		// 祝日マップ取得
		Map<Date, String> map = getHolidayMap(fisicalYear, mospParams);
		// 祝日名取得
		return map.get(targetDate) != null;
	}
	
	/**
	 * 対象日の年における祝日マップを取得する。<br>
	 * 但し、既に当該年の祝日マップが取得されている場合は、何もしない。<br>
	 * <br>
	 * 祝日マップは静的に保持されているものであり取得先で変更してはならない。<br>
	 * <br>
	 * @param fiscalYear 対象年度
	 * @param mospParams MosP処理情報
	 * @return 祝日マップ
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static Map<Date, String> getHolidayMap(int fiscalYear, MospParams mospParams) throws MospException {
		// 年度毎の祝日マップが取得できなかった場合
		if (holidayMap == null) {
			// 年度毎の祝日マップ作成
			holidayMap = new HashMap<Integer, Map<Date, String>>();
		}
		// 対象年度の祝日マップが取得できなかった場合
		if (holidayMap.get(fiscalYear) != null) {
			// 何もしない
			return holidayMap.get(fiscalYear);
		}
		
		// 祝日マップ作成
		createHolidayMap(fiscalYear, mospParams);
		// 祝日マップ取得
		return holidayMap.get(fiscalYear);
	}
	
	/**
	 * 対象日勤務曜日のスタイル文字列を取得する。<br>
	 * @param targetDate 対象日
	 * @param mospParams MosP処理情報
	 * @return 対象日勤務曜日のスタイル文字列
	 * @throws MospException 日付操作に失敗した場合
	 */
	public static String getWorkDayOfWeekStyle(Date targetDate, MospParams mospParams) throws MospException {
		// 祝日判定
		if (isHoliday(targetDate, mospParams)) {
			return PlatformConst.STYLE_RED;
		}
		// 土曜日判定
		if (DateUtility.isSaturday(targetDate)) {
			return PlatformConst.STYLE_BLUE;
		}
		// 日曜日判定
		if (DateUtility.isSunday(targetDate)) {
			return PlatformConst.STYLE_RED;
		}
		return "";
	}
	
	/**
	 * Calendarインスタンスを取得する。
	 * @return	Calendarインスタンス
	 */
	public static Calendar getCalendar() {
		Calendar cal = Calendar.getInstance();
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal;
	}
	
	/**
	 * 祝日Mapを生成する。
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static void createHolidayMap(int year, MospParams mospParams) throws MospException {
		// 祝日マップ作成
		TreeMap<Date, String> map = new TreeMap<Date, String>();
		// 元日
		map.put(getNewYearsDay(year, mospParams), mospParams.getName(NAM_NEW_YEARS_DAY));
		// 成人の日
		map.put(getComingOfAgeDay(year, mospParams), mospParams.getName(NAM_COMING_OF_AGE_DAY));
		// 建国記念の日
		map.put(getNationalFoundationDay(year, mospParams), mospParams.getName(NAM_NATIONAL_FOUNDATION_DAY));
		// 春分の日
		map.put(getVernalEquinoxDay(year, mospParams), mospParams.getName(NAM_VERNAL_EQUINOX_DAY));
		// 昭和の日
		map.put(getShowaDay(year, mospParams), mospParams.getName(NAM_SHOWA_DAY));
		// 憲法記念日
		map.put(getConstitutionDay(year, mospParams), mospParams.getName(NAM_CONSTITUTION_DAY));
		// みどりの日
		map.put(getGreeneryDay(year, mospParams), mospParams.getName(NAM_GREENERY_DAY));
		// こどもの日
		map.put(getChildrensDay(year, mospParams), mospParams.getName(NAM_CHILDRENS_DAY));
		// 海の日
		map.put(getMarineDay(year, mospParams), mospParams.getName(NAM_MARINE_DAY));
		// 山の日
		// 山の日導入年
		final int mountainSwitchYear = 2016;
		if (getYear(year, Calendar.AUGUST, mospParams) >= mountainSwitchYear) {
			map.put(getMountDay(year, mospParams), mospParams.getName(NAM_MOUNT_DAY));
		}
		// 敬老の日
		map.put(getRespectForTheAgedDay(year, mospParams), mospParams.getName(NAM_RESPECT_FOR_THE_AGED_DAY));
		// 秋分の日
		map.put(getAutumnalEquinoxDay(year, mospParams), mospParams.getName(NAM_AUTUMNAL_EQUINOX_DAY));
		// 体育の日
		map.put(getSportsDay(year, mospParams), getSportsDayName(year, mospParams));
		// 文化の日
		map.put(getCultureDay(year, mospParams), mospParams.getName(NAM_CULTURE_DAY));
		// 勤労感謝の日
		map.put(getLaborThanksgivingDay(year, mospParams), mospParams.getName(NAM_LABOR_THANKSGIVING_DAY));
		// 天皇誕生日
		map.put(getEmperorsBirthday(year, mospParams), mospParams.getName(NAM_EMPERORS_BIRTHDAY));
		// 平成終了年
		final int heiseiEndYear = 2019;
		// 2019年限定の祝日
		if (getYear(year, Calendar.MAY, mospParams) == heiseiEndYear) {
			// 天皇即位の礼
			map.put(getAccessionDay(year, mospParams), mospParams.getName(NAM_ACCESSION_DAY));
		}
		if (getYear(year, Calendar.OCTOBER, mospParams) == heiseiEndYear) {
			// 即位礼正殿の儀
			map.put(getCoronationCeremony(year, mospParams), mospParams.getName(NAM_CORONATION_CEREMONY));
		}
		
		// 対象年度の祝日分を祝日マップに追加
		holidayMap.put(year, map);
		// 振替休日を追加
		addSubstituteDate(year, map, mospParams);
	}
	
	/**
	 * 振替休日を追加する。
	 * @param year 対象年
	 * @param map 祝日マップ
	 * @param mospParams MosP処理情報
	 */
	protected static void addSubstituteDate(int year, Map<Date, String> map, MospParams mospParams) {
		Calendar cal = getCalendar();
		Set<Date> keySet = map.keySet();
		HashMap<Date, String> addMap = new HashMap<Date, String>();
		Date formerHoliday = null;
		for (Date date : keySet) {
			// 第三条第二項
			// 「国民の祝日」が日曜日に当たるときは、その日後においてその日に最も近い「国民の祝日」でない日を休日とする。
			// 日曜だった場合、最も近い「国民の祝日」でない日に「振替休日」を追加する。
			if (DateUtility.isSunday(date)) {
				cal.setTime(date);
				cal.add(Calendar.DAY_OF_MONTH, 1);
				// 既に登録されている休日と重複した場合、一日ずらす。
				while (map.containsKey(cal.getTime())) {
					cal.add(Calendar.DAY_OF_MONTH, 1);
				}
				if (!map.containsKey(cal.getTime())) {
					addMap.put(cal.getTime(), mospParams.getName(NAM_OBSERVED_HOLIDAY));
				}
			}
			// 第三条第三項
			// その前日及び翌日が「国民の祝日」である日（「国民の祝日」でない日に限る。）は、休日とする。
			if (formerHoliday != null) {
				cal.setTime(formerHoliday);
				cal.add(Calendar.DAY_OF_MONTH, 2);
				if (cal.getTime().compareTo(date) == 0) {
					cal.add(Calendar.DAY_OF_MONTH, -1);
					addMap.put(cal.getTime(), mospParams.getName(NAM_PEOPLES_DAY));
				}
			}
			formerHoliday = date;
		}
		map.putAll(addMap);
	}
	
	/**
	 * 成人の日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	成人の日(1月第2月曜日)
	 * <p>
	 * 1999年以前は(1月15日)
	 * </p>
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getComingOfAgeDay(int year, MospParams mospParams) throws MospException {
		int targetYear = getYear(year, Calendar.JANUARY, mospParams);
		final int switchYear = 2000;
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, targetYear);
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		if (targetYear < switchYear) {
			cal.set(Calendar.DAY_OF_MONTH, 15);
		} else {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 2);
		}
		return cal.getTime();
	}
	
	/**
	 * 海の日を取得する。<br>
	 * 2002年以前は7月20日。<br>
	 * 2020年のみ7月23日。<br>
	 * 2021年のみ7月22日。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	海の日(7月第3月曜日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getMarineDay(int year, MospParams mospParams) throws MospException {
		int targetYear = getYear(year, Calendar.JULY, mospParams);
		final int switchYear = 2003;
		// 2020年のみ7月23日
		final int specialYear2020 = 2020;
		// 2021年のみ7月22日
		final int specialYear2021 = 2021;
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, targetYear);
		cal.set(Calendar.MONTH, Calendar.JULY);
		if (targetYear < switchYear) {
			cal.set(Calendar.DAY_OF_MONTH, 20);
		} else if (targetYear == specialYear2020) {
			// 2020年のみ7月23日
			cal.set(Calendar.DAY_OF_MONTH, 23);
		} else if (targetYear == specialYear2021) {
			// 2021年のみ7月22日
			cal.set(Calendar.DAY_OF_MONTH, 22);
		} else {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
		}
		return cal.getTime();
	}
	
	/**
	 * 山の日を取得する。<br>
	 * 2016年以降適用。<br>
	 * 2020年のみ8月10日。<br>
	 * 2021年のみ8月8日。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	山の日(8月11日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getMountDay(int year, MospParams mospParams) throws MospException {
		// 対象年度における対象月が含まれる年を取得
		int targetYear = getYear(year, Calendar.SEPTEMBER, mospParams);
		// 2020年のみ8月10日
		final int specialYear2020 = 2020;
		// 2021年のみ8月8日
		final int specialYear2021 = 2021;
		// カレンダ(山の日)を準備
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, targetYear);
		cal.set(Calendar.MONTH, Calendar.AUGUST);
		// 2020年のみ8月10日
		if (targetYear == specialYear2020) {
			cal.set(Calendar.DAY_OF_MONTH, 10);
		} else if (targetYear == specialYear2021) {
			// 2021年のみ8月8日
			cal.set(Calendar.DAY_OF_MONTH, 8);
		} else {
			cal.set(Calendar.DAY_OF_MONTH, 11);
		}
		return cal.getTime();
	}
	
	/**
	 * 敬老の日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	敬老の日(9月第3月曜日)
	 * <p>
	 * 2002年以前は(9月15日)
	 * </p>
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getRespectForTheAgedDay(int year, MospParams mospParams) throws MospException {
		int targetYear = getYear(year, Calendar.SEPTEMBER, mospParams);
		final int switchYear = 2003;
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, targetYear);
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		if (targetYear < switchYear) {
			cal.set(Calendar.DAY_OF_MONTH, 15);
		} else {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 3);
		}
		return cal.getTime();
	}
	
	/**
	 * 体育の日を取得する。<br>
	 * 1999年以前は10月10日。<br>
	 * 2020年のみ7月24日。<br>
	 * 2021年のみ7月23日。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	体育の日(10月第2月曜日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getSportsDay(int year, MospParams mospParams) throws MospException {
		int targetYear = getYear(year, Calendar.OCTOBER, mospParams);
		final int switchYear = 2000;
		// 2020年のみ7月24日
		final int specialYear2020 = 2020;
		// 2020年のみ7月24日
		final int specialYear2021 = 2021;
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, targetYear);
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		if (targetYear < switchYear) {
			cal.set(Calendar.DAY_OF_MONTH, 10);
		} else if (targetYear == specialYear2020) {
			cal.set(Calendar.MONTH, Calendar.JULY);
			cal.set(Calendar.DAY_OF_MONTH, 24);
		} else if (targetYear == specialYear2021) {
			// 2020年のみ7月24日
			cal.set(Calendar.MONTH, Calendar.JULY);
			cal.set(Calendar.DAY_OF_MONTH, 23);
		} else {
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			cal.set(Calendar.DAY_OF_WEEK_IN_MONTH, 2);
		}
		return cal.getTime();
	}
	
	/**
	 * スポーツの日(名称)を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	スポーツの日
	 */
	protected static String getSportsDayName(int year, MospParams mospParams) {
		// 切替年を準備
		final int switchYear = 2020;
		// 2020年より前である場合
		if (year < switchYear) {
			// 体育の日(名称)を取得
			return mospParams.getName(NAM_SPORTS_DAY_OLD);
		}
		// スポーツの日(名称)を取得
		return mospParams.getName(NAM_SPORTS_DAY);
	}
	
	/**
	 * 春分の日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	春分の日(3月xx日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getVernalEquinoxDay(int year, MospParams mospParams) throws MospException {
		int targetYear = getYear(year, Calendar.MARCH, mospParams);
		final double param1 = 21.4471d;
		final double param2 = 0.242377d;
		final double param3 = 1900d;
		final double param4 = 4.0d;
		Calendar cal = getCalendar();
		double date = param1 + (param2 * (targetYear - param3)) - Math.floor((targetYear - param3) / param4);
		int dd = (int)Math.floor(date);
		cal.set(Calendar.YEAR, targetYear);
		cal.set(Calendar.MONTH, Calendar.MARCH);
		cal.set(Calendar.DAY_OF_MONTH, dd);
		return cal.getTime();
	}
	
	/**
	 * 秋分の日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return 秋分の日(9月xx日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getAutumnalEquinoxDay(int year, MospParams mospParams) throws MospException {
		int targetYear = getYear(year, Calendar.SEPTEMBER, mospParams);
		final double param1 = 23.8896d;
		final double param2 = 0.242032d;
		final double param3 = 1900d;
		final double param4 = 4.0d;
		Calendar cal = getCalendar();
		final double date = param1 + (param2 * (targetYear - param3)) - Math.floor((targetYear - param3) / param4);
		int dd = (int)Math.floor(date);
		cal.set(Calendar.YEAR, targetYear);
		cal.set(Calendar.MONTH, Calendar.SEPTEMBER);
		cal.set(Calendar.DAY_OF_MONTH, dd);
		return cal.getTime();
	}
	
	/**
	 * 元日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	元日(1月1日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getNewYearsDay(int year, MospParams mospParams) throws MospException {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, getYear(year, Calendar.JANUARY, mospParams));
		cal.set(Calendar.MONTH, Calendar.JANUARY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	/**
	 * 建国記念の日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	建国記念の日(2月11日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getNationalFoundationDay(int year, MospParams mospParams) throws MospException {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, getYear(year, Calendar.FEBRUARY, mospParams));
		cal.set(Calendar.MONTH, Calendar.FEBRUARY);
		cal.set(Calendar.DAY_OF_MONTH, 11);
		return cal.getTime();
	}
	
	/**
	 * 憲法記念日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	憲法記念日(5月3日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getConstitutionDay(int year, MospParams mospParams) throws MospException {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, getYear(year, Calendar.MAY, mospParams));
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 3);
		return cal.getTime();
	}
	
	/**
	 * こどもの日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	こどもの日(5月5日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getChildrensDay(int year, MospParams mospParams) throws MospException {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, getYear(year, Calendar.MAY, mospParams));
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 5);
		return cal.getTime();
	}
	
	/**
	 * 文化の日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	文化の日(11月3日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getCultureDay(int year, MospParams mospParams) throws MospException {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, getYear(year, Calendar.NOVEMBER, mospParams));
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 3);
		return cal.getTime();
	}
	
	/**
	 * 勤労感謝の日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	勤労感謝の日(11月23日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getLaborThanksgivingDay(int year, MospParams mospParams) throws MospException {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, getYear(year, Calendar.NOVEMBER, mospParams));
		cal.set(Calendar.MONTH, Calendar.NOVEMBER);
		cal.set(Calendar.DAY_OF_MONTH, 23);
		return cal.getTime();
	}
	
	/**
	 * 天皇誕生日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	天皇誕生日(12月23日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getEmperorsBirthday(int year, MospParams mospParams) throws MospException {
		int heiseiEndYear = 2019;
		int birthMonth;
		int birthDate;
		if (getYear(year, Calendar.MAY, mospParams) < heiseiEndYear) {
			// 2018年度以前(平成天皇誕生日)の場合12月23日
			birthMonth = Calendar.DECEMBER;
			birthDate = 23;
		} else {
			birthMonth = Calendar.FEBRUARY;
			birthDate = 23;
		}
		
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, getYear(year, birthMonth, mospParams));
		cal.set(Calendar.MONTH, birthMonth);
		cal.set(Calendar.DAY_OF_MONTH, birthDate);
		return cal.getTime();
	}
	
	/**
	 * 昭和の日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	昭和の日(4月29日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getShowaDay(int year, MospParams mospParams) throws MospException {
		final int dayOfMonth = 29;
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, getYear(year, Calendar.APRIL, mospParams));
		cal.set(Calendar.MONTH, Calendar.APRIL);
		cal.set(Calendar.DAY_OF_MONTH, dayOfMonth);
		return cal.getTime();
	}
	
	/**
	 * みどりの日を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	みどりの日(5月4日)
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getGreeneryDay(int year, MospParams mospParams) throws MospException {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, getYear(year, Calendar.MAY, mospParams));
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 4);
		return cal.getTime();
	}
	
	/**
	 * 対象年度における対象月が含まれる年を取得する。<br>
	 * @param fiscalYear 対象年度
	 * @param targetMonth 対象月(0:1月、1：2月…)
	 * @param mospParams MosP処理情報
	 * @return 対象年度における対象月が含まれる年
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static int getYear(int fiscalYear, int targetMonth, MospParams mospParams) throws MospException {
		return DateUtility.getYear(MonthUtility.getFiscalYearMonth(fiscalYear, targetMonth + 1, mospParams));
	}
	
	/**
	 * 天皇即位の礼を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	 天皇即位の礼(5月1日)
	 * <p>
	 * 2019年のみ適用。<br>
	 * </p>
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getAccessionDay(int year, MospParams mospParams) throws MospException {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, getYear(year, Calendar.MAY, mospParams));
		cal.set(Calendar.MONTH, Calendar.MAY);
		cal.set(Calendar.DAY_OF_MONTH, 1);
		return cal.getTime();
	}
	
	/**
	 * 即位礼正殿の儀を取得する。<br>
	 * @param year 対象年
	 * @param mospParams MosP処理情報
	 * @return	 即位礼正殿の儀(10月22日)
	 * <p>
	 * 2019年のみ適用。<br>
	 * </p>
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected static Date getCoronationCeremony(int year, MospParams mospParams) throws MospException {
		Calendar cal = getCalendar();
		cal.set(Calendar.YEAR, getYear(year, Calendar.OCTOBER, mospParams));
		cal.set(Calendar.MONTH, Calendar.OCTOBER);
		cal.set(Calendar.DAY_OF_MONTH, 22);
		return cal.getTime();
	}
}
