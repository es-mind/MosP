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

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.base.WorkflowNumberDtoInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.base.HolidayRangeDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.entity.TimeDuration;
import jp.mosp.time.entity.WorkTypeEntityInterface;

/**
 * 申請における有用なメソッドを提供する。<br>
 */
public class TimeRequestUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TimeRequestUtility() {
		// 処理無し
	}
	
	/**
	 * 休暇申請情報が有給休暇であるかを確認する。<br>
	 * @param dto 休暇申請情報
	 * @return 確認結果(true：有給休暇である、false：有給休暇でない)
	 */
	public static final boolean isPaidHoliday(HolidayRequestDtoInterface dto) {
		// 休暇申請情報が有給休暇であるかを確認
		return isTheHolidayType(dto, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, TimeConst.CODE_HOLIDAYTYPE2_PAID);
	}
	
	/**
	 * 休暇申請情報がストック休暇であるかを確認する。<br>
	 * @param dto 休暇申請情報
	 * @return 確認結果(true：ストック休暇である、false：ストック休暇でない)
	 */
	public static final boolean isStockHoliday(HolidayRequestDtoInterface dto) {
		// 休暇申請情報がストック休暇であるかを確認
		return isTheHolidayType(dto, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, TimeConst.CODE_HOLIDAYTYPE2_STOCK);
	}
	
	/**
	 * 休暇申請情報が特別休暇であるかを確認する。<br>
	 * @param dto 休暇申請情報
	 * @return 確認結果(true：特別休暇である、false：特別休暇でない)
	 */
	public static final boolean isSpecialHoliday(HolidayRequestDtoInterface dto) {
		// 休暇申請情報が有給休暇であるかを確認
		return isTheHolidayType1(dto, TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
	}
	
	/**
	 * 休暇申請情報がその他休暇であるかを確認する。<br>
	 * @param dto 休暇申請情報
	 * @return 確認結果(true：その他休暇である、false：その他休暇でない)
	 */
	public static final boolean isOtherHoliday(HolidayRequestDtoInterface dto) {
		// 休暇申請情報が有給休暇であるかを確認
		return isTheHolidayType1(dto, TimeConst.CODE_HOLIDAYTYPE_OTHER);
	}
	
	/**
	 * 休暇申請情報が欠勤であるかを確認する。<br>
	 * @param dto 休暇申請情報
	 * @return 確認結果(true：欠勤である、false：欠勤でない)
	 */
	public static final boolean isAbsenece(HolidayRequestDtoInterface dto) {
		// 休暇申請情報が有給休暇であるかを確認
		return isTheHolidayType1(dto, TimeConst.CODE_HOLIDAYTYPE_ABSENCE);
	}
	
	/**
	 * 休暇申請情報がその休暇種別1であるかを確認する。<br>
	 * @param dto          休暇申請情報
	 * @param holidayType1 休暇種別1
	 * @return 確認結果(true：その休暇種別1である、false：その休暇種別1でない)
	 */
	protected static final boolean isTheHolidayType1(HolidayRequestDtoInterface dto, int holidayType1) {
		// 休暇申請情報が存在しない場合
		if (dto == null) {
			// その休暇種別1でないと判断
			return false;
		}
		// 休暇申請情報がその休暇種別1であるかを確認
		return dto.getHolidayType1() == holidayType1;
	}
	
	/**
	 * 休暇申請情報がその休暇種別2であるかを確認する。<br>
	 * @param dto          休暇申請情報
	 * @param holidayType2 休暇種別2
	 * @return 確認結果(true：その休暇種別2である、false：その休暇種別2でない)
	 */
	protected static final boolean isTheHolidayType2(HolidayRequestDtoInterface dto, String holidayType2) {
		// 休暇申請情報が存在しない場合
		if (dto == null || MospUtility.isEmpty(dto.getHolidayType2())) {
			// その休暇種別2でないと判断
			return false;
		}
		// 休暇申請情報がその休暇種別2であるかを確認
		return dto.getHolidayType2().equals(holidayType2);
	}
	
	/**
	 * 休暇申請情報がその休暇種別であるかを確認する。<br>
	 * @param dto          休暇申請情報
	 * @param holidayType1 休暇種別1
	 * @param holidayType2 休暇種別2
	 * @return 確認結果(true：その休暇種別1である、false：その休暇種別1でない)
	 */
	protected static final boolean isTheHolidayType(HolidayRequestDtoInterface dto, int holidayType1,
			String holidayType2) {
		// 休暇種別2が空白である場合
		if (MospUtility.isEmpty(holidayType2)) {
			// 休暇申請情報がその休暇種別1であるかを確認
			return isTheHolidayType1(dto, holidayType1);
		}
		// 休暇申請情報がその休暇種別であるかを確認
		return isTheHolidayType1(dto, holidayType1) && isTheHolidayType2(dto, holidayType2);
	}
	
	/**
	 * 休暇(範囲)情報が全休であるかを確認する。<br>
	 * @param dto 休暇(範囲)情報
	 * @return 確認結果(true：全休である、false：全休でない)
	 */
	public static boolean isHolidayRangeAll(HolidayRangeDtoInterface dto) {
		// 休暇(範囲)情報が全休であるかを確認
		return isTheHolidayRange(dto, TimeConst.CODE_HOLIDAY_RANGE_ALL);
	}
	
	/**
	 * 休暇(範囲)情報が前半休であるかを確認する。<br>
	 * @param dto 休暇(範囲)情報
	 * @return 確認結果(true：前半休である、false：前半休でない)
	 */
	public static boolean isHolidayRangeAm(HolidayRangeDtoInterface dto) {
		// 休暇(範囲)情報が前半休であるかを確認
		return isTheHolidayRange(dto, TimeConst.CODE_HOLIDAY_RANGE_AM);
	}
	
	/**
	 * 休暇(範囲)情報が後半休であるかを確認する。<br>
	 * @param dto 休暇(範囲)情報
	 * @return 確認結果(true：後半休である、false：後半休でない)
	 */
	public static boolean isHolidayRangePm(HolidayRangeDtoInterface dto) {
		// 休暇(範囲)情報が後半休であるかを確認
		return isTheHolidayRange(dto, TimeConst.CODE_HOLIDAY_RANGE_PM);
	}
	
	/**
	 * 休暇(範囲)情報が半休であるかを確認する。<br>
	 * @param dto 休暇(範囲)情報
	 * @return 確認結果(true：半休である、false：半休でない)
	 */
	public static boolean isHolidayRangeHalf(HolidayRangeDtoInterface dto) {
		// 休暇(範囲)情報が半休であるかを確認
		return isHolidayRangeAm(dto) || isHolidayRangePm(dto);
	}
	
	/**
	 * 休暇(範囲)情報が時間休であるかを確認する。<br>
	 * @param dto 休暇(範囲)情報
	 * @return 確認結果(true：時間休である、false：時間休でない)
	 */
	public static boolean isHolidayRangeHour(HolidayRangeDtoInterface dto) {
		// 休暇(範囲)情報が時間休であるかを確認
		return isTheHolidayRange(dto, TimeConst.CODE_HOLIDAY_RANGE_TIME);
	}
	
	/**
	 * 休暇(範囲)情報群に全休があるかを確認する。<br>
	 * @param dtos 休暇(範囲)情報群
	 * @return 確認結果(true：全休がある、false：全休がない)
	 */
	public static boolean hasHolidayRangeAll(Collection<? extends HolidayRangeDtoInterface> dtos) {
		// 休暇(範囲)情報が全休であるかを確認
		return hasTheHolidayRange(dtos, TimeConst.CODE_HOLIDAY_RANGE_ALL);
	}
	
	/**
	 * 休暇(範囲)情報群に前半休があるかを確認する。<br>
	 * @param dtos 休暇(範囲)情報群
	 * @return 確認結果(true：前半休がある、false：前半休がない)
	 */
	public static boolean hasHolidayRangeAm(Collection<? extends HolidayRangeDtoInterface> dtos) {
		// 休暇(範囲)情報に前半休があるかを確認
		return hasTheHolidayRange(dtos, TimeConst.CODE_HOLIDAY_RANGE_AM);
	}
	
	/**
	 * 休暇(範囲)情報群に後半休があるかを確認する。<br>
	 * @param dtos 休暇(範囲)情報群
	 * @return 確認結果(true：後半休がある、false：後半休がない)
	 */
	public static boolean hasHolidayRangePm(Collection<? extends HolidayRangeDtoInterface> dtos) {
		// 休暇(範囲)情報に後半休があるかを確認
		return hasTheHolidayRange(dtos, TimeConst.CODE_HOLIDAY_RANGE_PM);
	}
	
	/**
	 * 休暇(範囲)情報群に半休があるかを確認する。<br>
	 * @param dtos 休暇(範囲)情報群
	 * @return 確認結果(true：半休がある、false：半休がない)
	 */
	public static boolean hasHolidayRangeHalf(Collection<? extends HolidayRangeDtoInterface> dtos) {
		// 休暇(範囲)情報に半休があるかを確認
		return hasTheHolidayRange(dtos, TimeConst.CODE_HOLIDAY_RANGE_AM)
				|| hasTheHolidayRange(dtos, TimeConst.CODE_HOLIDAY_RANGE_PM);
	}
	
	/**
	 * 休暇(範囲)情報群に時間休があるかを確認する。<br>
	 * @param dtos 休暇(範囲)情報群
	 * @return 確認結果(true：時間休がある、false：時間休がない)
	 */
	public static boolean hasHolidayRangeHour(Collection<? extends HolidayRangeDtoInterface> dtos) {
		// 休暇(範囲)情報に時間休があるかを確認
		return hasTheHolidayRange(dtos, TimeConst.CODE_HOLIDAY_RANGE_TIME);
	}
	
	/**
	 * 休暇(範囲)情報群に全休(前半休+後半休も含む)があるかを確認する。<br>
	 * @param dtos 休暇(範囲)情報群
	 * @return 確認結果(true：全休(前半休+後半休も含む)がある、false：全休(前半休+後半休も含む)がない)
	 */
	public static boolean isAllRangeHoliday(Collection<? extends HolidayRangeDtoInterface> dtos) {
		// 休暇(範囲)情報群に全休がある場合
		if (hasHolidayRangeAll(dtos)) {
			// 全休(前半休+後半休も含む)があると判断
			return true;
		}
		// 休暇(範囲)情報群に前半休と後半休がある場合
		if (hasHolidayRangeAm(dtos) && hasHolidayRangePm(dtos)) {
			// 全休(前半休+後半休も含む)があると判断
			return true;
		}
		// 全休(前半休+後半休も含む)がないと判断
		return false;
	}
	
	/**
	 * 休暇(範囲)情報がその休暇範囲であるかを確認する。<br>
	 * @param dto          休暇(範囲)情報
	 * @param holidayRange 休暇範囲
	 * @return 確認結果(true：その休暇範囲である、false：その休暇範囲でない)
	 */
	protected static boolean isTheHolidayRange(HolidayRangeDtoInterface dto, int holidayRange) {
		// 休暇(範囲)情報が存在しない場合
		if (dto == null) {
			// その休暇範囲でないと判断
			return false;
		}
		// 休暇(範囲)情報がその休暇範囲であるかを確認
		return dto.getHolidayRange() == holidayRange;
	}
	
	/**
	 * 休暇(範囲)情報群にその休暇範囲があるかを確認する。<br>
	 * @param dtos         休暇(範囲)情報群
	 * @param holidayRange 休暇範囲
	 * @return 確認結果(true：その休暇範囲がある、false：ない)
	 */
	protected static boolean hasTheHolidayRange(Collection<? extends HolidayRangeDtoInterface> dtos, int holidayRange) {
		// 休暇(範囲)情報毎に処理
		for (HolidayRangeDtoInterface dto : dtos) {
			// 休暇(範囲)情報がその休暇範囲である場合
			if (isTheHolidayRange(dto, holidayRange)) {
				// その休暇範囲があると判断
				return true;
			}
		}
		// その休暇範囲がないと判断
		return false;
	}
	
	/**
	 * 全休の休暇略称を取得する。<br>
	 * 但し、複数ある場合は、最初に見つかった休暇申請情報から略称を取得するものとする。<br>
	 * また、存在しない場合は、空文字を取得する。<br>
	 * @param dtos       休暇申請情報群
	 * @param holidays   休暇種別情報群
	 * @param mospParams MosP処理情報
	 * @return 休暇略称
	 */
	public static String getAllHolidayAbbr(Collection<HolidayRequestDtoInterface> dtos,
			Collection<HolidayDtoInterface> holidays, MospParams mospParams) {
		// 全休の休暇略称を取得
		return getTheHolidayRangeAbbr(dtos, TimeConst.CODE_HOLIDAY_RANGE_ALL, holidays, mospParams);
	}
	
	/**
	 * 前半休の休暇略称を取得する。<br>
	 * 但し、複数ある場合は、最初に見つかった休暇申請情報から略称を取得するものとする。<br>
	 * また、存在しない場合は、空文字を取得する。<br>
	 * @param dtos       休暇申請情報群
	 * @param holidays   休暇種別情報群
	 * @param mospParams MosP処理情報
	 * @return 休暇略称
	 */
	public static String getAmHolidayAbbr(Collection<HolidayRequestDtoInterface> dtos,
			Collection<HolidayDtoInterface> holidays, MospParams mospParams) {
		// 前半休の休暇略称を取得
		return getTheHolidayRangeAbbr(dtos, TimeConst.CODE_HOLIDAY_RANGE_AM, holidays, mospParams);
	}
	
	/**
	 * 後半休の休暇略称を取得する。<br>
	 * 但し、複数ある場合は、最初に見つかった休暇申請情報から略称を取得するものとする。<br>
	 * また、存在しない場合は、空文字を取得する。<br>
	 * @param dtos       休暇申請情報群
	 * @param holidays   休暇種別情報群
	 * @param mospParams MosP処理情報
	 * @return 休暇略称
	 */
	public static String getPmHolidayAbbr(Collection<HolidayRequestDtoInterface> dtos,
			Collection<HolidayDtoInterface> holidays, MospParams mospParams) {
		// 後半休の休暇略称を取得
		return getTheHolidayRangeAbbr(dtos, TimeConst.CODE_HOLIDAY_RANGE_PM, holidays, mospParams);
	}
	
	/**
	 * 対象休暇範囲の休暇(範囲)情報のワークフロー番号を取得する。<br>
	 * 但し、複数ある場合は、最初に見つかった休暇(範囲)情報のものを取得する。<br>
	 * また、存在しない場合は、nullを取得する。<br>
	 * @param dtos         休暇(範囲)情報群
	 * @param holidayRange 休暇範囲
	 * @return 対象休暇範囲の休暇(範囲)情報のワークフロー番号
	 */
	public static <T extends HolidayRangeDtoInterface & WorkflowNumberDtoInterface> Long getTheHolidayRangeWorkflow(
			Collection<T> dtos, int holidayRange) {
		// 対象休暇範囲の休暇(範囲)情報を取得
		T dto = getTheHolidayRangeDto(dtos, holidayRange);
		// 対象休暇範囲の休暇(範囲)情報を取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// nullを取得
			return null;
		}
		// 対象休暇範囲の休暇(範囲)情報のワークフロー番号を取得
		return dto.getWorkflow();
	}
	
	/**
	 * 対象休暇範囲の休暇(範囲)情報を取得する。<br>
	 * 但し、複数ある場合は、最初に見つかった休暇(範囲)情報を取得するものとする。<br>
	 * また、存在しない場合は、nullを取得する。<br>
	 * @param dtos         休暇(範囲)情報群
	 * @param holidayRange 休暇範囲
	 * @return 休暇(範囲)情報
	 */
	public static <T extends HolidayRangeDtoInterface> T getTheHolidayRangeDto(Collection<T> dtos, int holidayRange) {
		// 休暇(範囲)情報毎に処理
		for (T dto : dtos) {
			// 休暇(範囲)情報がその休暇範囲である場合
			if (isTheHolidayRange(dto, holidayRange)) {
				// 休暇(範囲)情報を取得
				return dto;
			}
		}
		// nullを取得(存在しない場合)
		return null;
	}
	
	/**
	 * 対象休暇範囲の休暇略称を取得する。<br>
	 * 但し、複数ある場合は、最初に見つかった休暇申請情報から略称を取得するものとする。<br>
	 * また、存在しない場合は、空文字を取得する。<br>
	 * @param dtos         休暇申請情報群
	 * @param holidayRange 休暇範囲
	 * @param holidays     休暇種別情報群
	 * @param mospParams   MosP処理情報
	 * @return 休暇略称
	 */
	protected static String getTheHolidayRangeAbbr(Collection<HolidayRequestDtoInterface> dtos, int holidayRange,
			Collection<HolidayDtoInterface> holidays, MospParams mospParams) {
		// 対象休暇範囲の休暇申請情報を取得
		HolidayRequestDtoInterface dto = getTheHolidayRangeDto(dtos, holidayRange);
		// 休暇申請情報を取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 休暇区分及び休暇コードを取得
		int holidayType = dto.getHolidayType1();
		String holidayCode = dto.getHolidayType2();
		// 休暇略称を取得
		return TimeUtility.getHolidayAbbr(holidays, holidayCode, holidayType, mospParams);
	}
	
	/**
	 * 休暇範囲の日数を取得する。<br>
	 * 全休と前半休及び後半休を対象とし、時間休は0とする。<br>
	 * @param dto 休暇(範囲)情報
	 * @return 休暇範囲の日数
	 */
	public static float getDays(HolidayRangeDtoInterface dto) {
		// 引数が不足する場合
		if (MospUtility.isEmpty(dto)) {
			// 0を取得
			return 0F;
		}
		// 休暇範囲の日数を取得
		return getDays(dto.getHolidayRange());
	}
	
	/**
	 * 休暇範囲の日数を取得する。<br>
	 * 全休と前半休及び後半休を対象とし、時間休は0とする。<br>
	 * @param holidayRange 休暇範囲
	 * @return 休暇範囲の日数
	 */
	public static float getDays(int holidayRange) {
		// 全休である場合
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 休暇日数(1)を取得
			return TimeConst.HOLIDAY_TIMES_ALL;
		}
		// 前半休か後半休である場合
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 休暇日数(0.5)を取得
			return TimeConst.HOLIDAY_TIMES_HALF;
		}
		// それ以外の場合(0を取得)
		return 0F;
	}
	
	/**
	 * 全休の代休略称を取得する。<br>
	 * 但し、複数ある場合は、最初に見つかった代休申請情報から略称を取得するものとする。<br>
	 * また、存在しない場合は、空文字を取得する。<br>
	 * @param dtos       代休申請情報群
	 * @param mospParams MosP処理情報
	 * @return 代休略称
	 */
	public static String getAllSubHolidayAbbr(Collection<SubHolidayRequestDtoInterface> dtos, MospParams mospParams) {
		// 対象休暇範囲の代休申請情報を取得
		SubHolidayRequestDtoInterface dto = getTheHolidayRangeDto(dtos, TimeConst.CODE_HOLIDAY_RANGE_ALL);
		// 代休申請情報を取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 代休種別を取得
		int subHolidayType = dto.getWorkDateSubHolidayType();
		// 代休略称を取得
		return getSubHolidayAbbr(subHolidayType, mospParams);
	}
	
	/**
	 * 代休種別から代休略称を取得する。<br>
	 * @param subHolidayType 代休種別
	 * @param mospParams     MosP処理情報
	 * @return 代休略称
	 */
	public static String getSubHolidayAbbr(int subHolidayType, MospParams mospParams) {
		// 代休種別確認
		switch (subHolidayType) {
			case TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE:
			case TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE:
				// 所定代休或いは深夜代休
				return TimeNamingUtility.prescribedSubHolidayAbbr(mospParams);
			case TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE:
				// 法定代休
				return TimeNamingUtility.legalSubHolidayAbbr(mospParams);
			default:
				return MospConst.STR_EMPTY;
		}
	}
	
	/**
	 * 代休種別名称を取得する。<br>
	 * @param subHolidayType 代休種別
	 * @param mospParams     MosP処理情報
	 * @return 代休種別名称
	 */
	public static String getSubHolidayTypeName(int subHolidayType, MospParams mospParams) {
		// 代休種別を確認
		switch (subHolidayType) {
			// 所定代休の場合
			case TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE:
				// 所定代休を取得
				return TimeNamingUtility.prescribedSubHoliday(mospParams);
			// 法定代休の場合
			case TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE:
				// 法定代休を取得
				return TimeNamingUtility.legalSubHoliday(mospParams);
			// 深夜代休の場合
			case TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE:
				// 深夜代休を取得
				return TimeNamingUtility.midnightSubHoliday(mospParams);
			// それ以外の場合
			default:
				// 空文字を取得
				return MospConst.STR_EMPTY;
		}
	}
	
	/**
	 * 代休種別略称を取得する。<br>
	 * @param subHolidayType 代休種別
	 * @param mospParams     MosP処理情報
	 * @return 代休種別略称
	 */
	public static String getSubHolidayTypeAbbr(int subHolidayType, MospParams mospParams) {
		// 代休種別を確認
		switch (subHolidayType) {
			// 所定代休の場合
			case TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE:
				// 所定を取得
				return TimeNamingUtility.prescribed(mospParams);
			// 法定代休の場合
			case TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE:
				// 法定を取得
				return TimeNamingUtility.legal(mospParams);
			// 深夜代休の場合
			case TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE:
				// 深夜を取得
				return TimeNamingUtility.midnight(mospParams);
			// それ以外の場合
			default:
				// 空文字を取得
				return MospConst.STR_EMPTY;
		}
	}
	
	/**
	 * 代休日数略称を取得する。<br>
	 * @param subHolidayDays 代休日数
	 * @param mospParams     MosP処理情報
	 * @return 代休日数略称
	 */
	public static String getSubHolidayDaysAbbr(double subHolidayDays, MospParams mospParams) {
		// 代休日数が1の場合
		if (MospUtility.compare(subHolidayDays, TimeConst.HOLIDAY_TIMES_ALL) == 0) {
			// 全休を取得
			return TimeNamingUtility.holidayRangeAll(mospParams);
		}
		// 代休日数が0.5の場合
		if (MospUtility.compare(subHolidayDays, TimeConst.HOLIDAY_TIMES_HALF) == 0) {
			// 半休を取得
			return TimeNamingUtility.holidayHalf(mospParams);
		}
		// 空文字を取得
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * 休暇種別(振替休日)から振替休日略称を取得する。<br>
	 * @param substituteType 休暇種別(振替休日)
	 * @param mospParams     MosP処理情報
	 * @return 振替休日略称
	 */
	public static String getSubstituteAbbr(String substituteType, MospParams mospParams) {
		// 法定休日である場合
		if (TimeUtility.isLegalHoliday(substituteType)) {
			// 法振休を取得
			return TimeNamingUtility.legalSubstituteAbbr(mospParams);
		}
		// 所定休日である場合
		if (TimeUtility.isPrescribedHoliday(substituteType)) {
			// 所振休を取得
			return TimeNamingUtility.prescribedSubstituteAbbr(mospParams);
		}
		// 空文字を取得
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * 振替休日範囲名称を取得する。<br>
	 * @param dto        振替休日情報
	 * @param mospParams MosP処理情報
	 * @return 振替休日範囲名称
	 */
	public static String getSubstituteRangeName(SubstituteDtoInterface dto, MospParams mospParams) {
		// 引数が不足する場合
		if (MospUtility.isEmpty(dto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 振替休日範囲を取得
		int ranget = dto.getSubstituteRange();
		// 全日である場合
		if (isHolidayRangeAll(dto)) {
			// 全日を取得
			return MospUtility.getCodeName(mospParams, ranget, TimeConst.CODE_SUBSTITUTE_WORK_RANGE);
		}
		// 半日である場合
		if (isHolidayRangeHalf(dto)) {
			// 午前か午後を取得
			return MospUtility.getCodeName(mospParams, ranget, TimeConst.CODE_SUBSTITUTE_HOLIDAY_RANGE);
		}
		// 空文字を取得
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * 有給休暇時間数(時間)を集計する。<br>
	 * @param dtos 休暇申請情報群
	 * @return 有給休暇時間数(時間)
	 */
	public static int totalPaidHolidayHours(Collection<HolidayRequestDtoInterface> dtos) {
		// 有給休暇時間数(時間)を集計
		return totalHolidayHours(dtos, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, TimeConst.CODE_HOLIDAYTYPE2_PAID);
	}
	
	/**
	 * 特別休暇時間数(時間)を集計する。<br>
	 * @param dtos 休暇申請情報群
	 * @return 特別休暇時間数(時間)
	 */
	public static int totalSpecialHolidayHours(Collection<HolidayRequestDtoInterface> dtos) {
		// 特別休暇時間数(時間)を集計
		return totalHolidayHours(dtos, TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
	}
	
	/**
	 * その他休暇時間数(時間)を集計する。<br>
	 * @param dtos 休暇申請情報群
	 * @return その他休暇時間数(時間)
	 */
	public static int totalOtherHolidayHours(Collection<HolidayRequestDtoInterface> dtos) {
		// その他休暇時間数(時間)を集計
		return totalHolidayHours(dtos, TimeConst.CODE_HOLIDAYTYPE_OTHER);
	}
	
	/**
	 * 欠勤時間数(時間)を集計する。<br>
	 * @param dtos 休暇申請情報群
	 * @return 欠勤時間数(時間)
	 */
	public static int totalAbsenceHolidayHours(Collection<HolidayRequestDtoInterface> dtos) {
		// 欠勤時間数(時間)を集計
		return totalHolidayHours(dtos, TimeConst.CODE_HOLIDAYTYPE_ABSENCE);
	}
	
	/**
	 * 休暇時間数(時間)を集計する。<br>
	 * @param dtos         休暇申請情報群
	 * @param holidayType1 休暇区分1
	 * @param holidayType2 休暇区分2
	 * @return 休暇時間数(時間)
	 */
	protected static int totalHolidayHours(Collection<HolidayRequestDtoInterface> dtos, int holidayType1,
			String holidayType2) {
		// 休暇時間数(時間)を準備
		int holidayHours = 0;
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// その休暇区分でない場合
			if (isTheHolidayType(dto, holidayType1, holidayType2) == false) {
				// 次の休暇申請情報へ
				continue;
			}
			// 時間単位でない場合
			if (isHolidayRangeHour(dto) == false) {
				// 次の休暇申請情報へ
				continue;
			}
			// 休暇時間数(時間)を加算
			holidayHours += dto.getUseHour();
		}
		// 休暇時間数(時間)を取得
		return holidayHours;
	}
	
	/**
	 * 休暇時間数(時間)を集計する。<br>
	 * @param dtos         休暇申請情報群
	 * @param holidayType1 休暇区分1
	 * @return 休暇時間数(時間)
	 */
	protected static int totalHolidayHours(Collection<HolidayRequestDtoInterface> dtos, int holidayType1) {
		// 休暇時間数(時間)を取得
		return totalHolidayHours(dtos, holidayType1, MospConst.STR_EMPTY);
	}
	
	/**
	 * 有給休暇日数を集計する。<br>
	 * @param dtos 休暇申請情報群
	 * @return 有給休暇日数
	 */
	public static float totalPaidHolidayDays(Collection<HolidayRequestDtoInterface> dtos) {
		// 有給休暇日数を集計
		return totalHolidayDays(dtos, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, TimeConst.CODE_HOLIDAYTYPE2_PAID);
	}
	
	/**
	 * ストック休暇日数を集計する。<br>
	 * @param dtos 休暇申請情報群
	 * @return ストック休暇日数
	 */
	public static float totalStockHolidayDays(Collection<HolidayRequestDtoInterface> dtos) {
		// ストック休暇日数を集計
		return totalHolidayDays(dtos, TimeConst.CODE_HOLIDAYTYPE_HOLIDAY, TimeConst.CODE_HOLIDAYTYPE2_STOCK);
	}
	
	/**
	 * 特別休暇日数を集計する。<br>
	 * @param dtos 休暇申請情報群
	 * @return 特別休暇日数
	 */
	public static float totalSpecialHolidayDays(Collection<HolidayRequestDtoInterface> dtos) {
		// 特別休暇日数を集計
		return totalHolidayDays(dtos, TimeConst.CODE_HOLIDAYTYPE_SPECIAL);
	}
	
	/**
	 * その他休暇日数を集計する。<br>
	 * @param dtos 休暇申請情報群
	 * @return その他休暇日数
	 */
	public static float totalOtherHolidayDays(Collection<HolidayRequestDtoInterface> dtos) {
		// その他休暇日数を集計
		return totalHolidayDays(dtos, TimeConst.CODE_HOLIDAYTYPE_OTHER);
	}
	
	/**
	 * 欠勤日数を集計する。<br>
	 * @param dtos 休暇申請情報群
	 * @return 欠勤日数
	 */
	public static float totalAbsenceDays(Collection<HolidayRequestDtoInterface> dtos) {
		// 欠勤日数を集計
		return totalHolidayDays(dtos, TimeConst.CODE_HOLIDAYTYPE_ABSENCE);
	}
	
	/**
	 * 休暇日数を集計する。<br>
	 * @param dtos         休暇申請情報群
	 * @param holidayType1 休暇区分1
	 * @param holidayType2 休暇区分2
	 * @return 休暇日数
	 */
	protected static float totalHolidayDays(Collection<HolidayRequestDtoInterface> dtos, int holidayType1,
			String holidayType2) {
		// 休暇日数を準備
		float holidayDays = 0F;
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// その休暇区分でない場合
			if (isTheHolidayType(dto, holidayType1, holidayType2) == false) {
				// 次の休暇申請情報へ
				continue;
			}
			// 休暇範囲の日数を加算
			holidayDays += getDays(dto);
		}
		// 休暇日数を取得
		return holidayDays;
	}
	
	/**
	 * 休暇日数を集計する。<br>
	 * @param dtos         休暇申請情報群
	 * @param holidayType1 休暇区分1
	 * @return 休暇日数
	 */
	protected static float totalHolidayDays(Collection<HolidayRequestDtoInterface> dtos, int holidayType1) {
		// 休暇日数を取得
		return totalHolidayDays(dtos, holidayType1, "");
	}
	
	/**
	 * 時間休時間間隔情報群を取得する。<br>
	 * @param dtos 休暇申請情報群
	 * @return 時間休時間間隔情報群
	 */
	public static Set<TimeDuration> getHourlyHolidayTimes(Collection<HolidayRequestDtoInterface> dtos) {
		// 時間休時間間隔情報群を準備
		Set<TimeDuration> durations = new LinkedHashSet<TimeDuration>();
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// 時間休でない場合
			if (isHolidayRangeHour(dto) == false) {
				// 次の休暇申請情報へ
				continue;
			}
			// 時間休時間間隔情報群に設定
			durations.add(getHourlyHolidayTime(dto));
		}
		// 時間休時間間隔情報群を取得
		return durations;
	}
	
	/**
	 * 時間休時間間隔情報を取得する。<br>
	 * @param dto 休暇申請情報
	 * @return 時間休時間間隔情報
	 */
	public static TimeDuration getHourlyHolidayTime(HolidayRequestDtoInterface dto) {
		// 開始時刻及び終了時刻を準備
		int startTime = TimeUtility.getMinutes(dto.getStartTime(), dto.getRequestStartDate());
		int endTime = TimeUtility.getMinutes(dto.getEndTime(), dto.getRequestEndDate());
		// 時間間隔を取得
		return TimeDuration.getInstance(startTime, endTime);
	}
	
	/**
	 * 半休時始終業時間間隔情報を取得する。<br>
	 * @param dto      休暇申請情報
	 * @param workType 勤務形態エンティティ
	 * @return 半休時始終業時間間隔情報
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static TimeDuration getWorkTimeForHalfHoliday(HolidayRequestDtoInterface dto,
			WorkTypeEntityInterface workType) throws MospException {
		// 開始時刻及び終了時刻を準備
		int startTime = 0;
		int endTime = 0;
		// 前半休である場合
		if (isHolidayRangeAm(dto)) {
			// 開始時刻及び終了時刻を取得
			startTime = TimeUtility.getMinutes(workType.getBackStartTime());
			endTime = TimeUtility.getMinutes(workType.getBackEndTime());
		}
		// 後半休である場合
		if (isHolidayRangePm(dto)) {
			// 開始時刻及び終了時刻を取得
			startTime = TimeUtility.getMinutes(workType.getFrontStartTime());
			endTime = TimeUtility.getMinutes(workType.getFrontEndTime());
		}
		// 時間間隔を取得
		return TimeDuration.getInstance(startTime, endTime);
	}
	
	/**
	 * 代休申請情報がその代休種別であるかを確認する。<br>
	 * @param dto            代休申請情報
	 * @param subHolidayType 代休種別
	 * @return 確認結果(true：その代休種別である、false：その代休種別でない)
	 */
	protected static final boolean isTheSubHolidayType(SubHolidayRequestDtoInterface dto, int subHolidayType) {
		// 休暇申請情報が存在しない場合
		if (dto == null) {
			// その代休種別でないと判断
			return false;
		}
		// 代休申請情報がその代休種別であるかを確認
		return dto.getWorkDateSubHolidayType() == subHolidayType;
	}
	
	/**
	 * 代休日数(法定+所定+深夜)を集計する。<br>
	 * @param dtos 代休申請情報群
	 * @return 代休日数
	 */
	public static float totalSubHolidayDays(Collection<SubHolidayRequestDtoInterface> dtos) {
		// 代休日数を集計
		return totalSubHolidayDays(dtos, null);
	}
	
	/**
	 * 法定代休日数を集計する。<br>
	 * @param dtos 代休申請情報群
	 * @return 法定代休日数
	 */
	public static float totalLegalSubHolidayDays(Collection<SubHolidayRequestDtoInterface> dtos) {
		// 法定代休日数を集計
		return totalSubHolidayDays(dtos, TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE);
	}
	
	/**
	 * 所定代休日数を集計する。<br>
	 * @param dtos 代休申請情報群
	 * @return 所定代休日数
	 */
	public static float totalPrescribedSubHolidayDays(Collection<SubHolidayRequestDtoInterface> dtos) {
		// 所定代休日数を集計
		return totalSubHolidayDays(dtos, TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE);
	}
	
	/**
	 * 深夜代休日数を集計する。<br>
	 * @param dtos 代休申請情報群
	 * @return 深夜代休日数
	 */
	public static float totalNightSubHolidayDays(Collection<SubHolidayRequestDtoInterface> dtos) {
		// 深夜代休日数を集計
		return totalSubHolidayDays(dtos, TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE);
	}
	
	/**
	 * 代休日数を集計する。<br>
	 * @param dtos           代休申請情報群
	 * @param subHolidayType 代休種別
	 * @return 代休日数
	 */
	protected static float totalSubHolidayDays(Collection<SubHolidayRequestDtoInterface> dtos, Integer subHolidayType) {
		// 代休日数を準備
		float subHolidayDays = 0F;
		// 代休申請情報毎に処理
		for (SubHolidayRequestDtoInterface dto : dtos) {
			// その代休種別でない場合
			if (subHolidayType != null && isTheSubHolidayType(dto, subHolidayType) == false) {
				// 次の代休申請情報へ
				continue;
			}
			// 休暇範囲の日数を加算
			subHolidayDays += getDays(dto);
		}
		// 代休日数を取得
		return subHolidayDays;
	}
	
	/**
	 * 振出・休出申請が休日出勤(振替申請しない)であるかを確認する。<br>
	 * @param dto 振出・休出申請情報
	 * @return 確認結果(true：休日出勤(振替申請しない)である、false：そうでない)
	 */
	public static boolean isWorkOnHolidaySubstituteOff(WorkOnHolidayRequestDtoInterface dto) {
		// 振出・休出申請が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 休日出勤(振替申請しない)でないと判断
			return false;
		}
		// 振出・休出申請が休日出勤(振替申請しない)であるかを確認
		return dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF;
	}
	
	/**
	 * 振出・休出申請が振替出勤であるかを確認する。<br>
	 * 以下の振替申請の場合に振替出勤であると判断する。<br>
	 * ・振替出勤(勤務形態変更なし：全日)<br>
	 * ・振替出勤(勤務形態変更なし：午前)<br>
	 * ・振替出勤(勤務形態変更なし：午後)<br>
	 * ・振替出勤(勤務形態変更あり)<br>
	 * @param dto 振出・休出申請情報
	 * @return 確認結果(true：振替出勤である、false：そうでない)
	 */
	public static boolean isWorkOnHolidaySubstitute(WorkOnHolidayRequestDtoInterface dto) {
		// 振出・休出申請が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 振替出勤でないと判断
			return false;
		}
		// 振替出勤であるかを確認
		return MospUtility.isContain(dto.getSubstitute(), TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON,
				TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM, TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM,
				TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE);
	}
	
	/**
	 * 振出・休出申請が半日振替出勤であるかを確認する。<br>
	 * 以下の振替申請の場合に半日振替出勤であると判断する。<br>
	 * ・振替出勤(勤務形態変更なし：午前)<br>
	 * ・振替出勤(勤務形態変更なし：午後)<br>
	 * @param dto 振出・休出申請情報
	 * @return 確認結果(true：半日振替出勤である、false：そうでない)
	 */
	public static boolean isWorkOnHolidayHalfSubstitute(WorkOnHolidayRequestDtoInterface dto) {
		// 振出・休出申請が前半日振替出勤か後半日振替出勤であるかを確認
		return isWorkOnHolidayAnteSubstitute(dto) || isWorkOnHolidayPostSubstitute(dto);
	}
	
	/**
	 * 振出・休出申請が前半日振替出勤であるかを確認する。<br>
	 * 以下の振替申請の場合に前半日振替出勤であると判断する。<br>
	 * ・振替出勤(勤務形態変更なし：午前)<br>
	 * @param dto 振出・休出申請情報
	 * @return 確認結果(true：前半日振替出勤である、false：そうでない)
	 */
	public static boolean isWorkOnHolidayAnteSubstitute(WorkOnHolidayRequestDtoInterface dto) {
		// 振出・休出申請が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 半日振替出勤でないと判断
			return false;
		}
		// 前半日振替出勤であるかを確認
		return dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM;
	}
	
	/**
	 * 振出・休出申請が後半日振替出勤であるかを確認する。<br>
	 * 以下の振替申請の場合に後半日振替出勤であると判断する。<br>
	 * ・振替出勤(勤務形態変更なし：午後)<br>
	 * @param dto 振出・休出申請情報
	 * @return 確認結果(true：後半日振替出勤である、false：そうでない)
	 */
	public static boolean isWorkOnHolidayPostSubstitute(WorkOnHolidayRequestDtoInterface dto) {
		// 振出・休出申請が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 半日振替出勤でないと判断
			return false;
		}
		// 後半日振替出勤であるかを確認
		return dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM;
	}
	
	/**
	 * 休暇申請が連続休暇であるかを確認する。<br>
	 * @param dto 休暇申請情報
	 * @return 確認結果(true：連続休暇である、false：そうでない)
	 */
	public static boolean isConsecutiveHolidays(HolidayRequestDtoInterface dto) {
		// 休暇申請が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 連続休暇でないと判断
			return false;
		}
		// 連続休暇であるかを確認
		return DateUtility.isSame(dto.getRequestStartDate(), dto.getRequestEndDate()) == false;
	}
	
	/**
	 * 勤務形態コードが連続休暇中の休暇対象でないかを確認する。<br>
	 * 勤務形態コードが休日か休日出勤日である場合は、連続休暇中の休暇対象でないと判断する。<br>
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：勤務形態コードが連続休暇中の休暇対象でない、false：休暇対象)
	 */
	public static boolean isNotHolidayForConsecutiveHolidays(String workTypeCode) {
		// 勤務形態コードが休日か休日出勤日であるかを確認
		return TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalOrPrescribedHoliday(workTypeCode);
	}
	
	/**
	 * 連続休暇である休暇申請が存在するかを確認する。<br>
	 * @param dtos 休暇申請情報群
	 * @return 確認結果(true：連続休暇である休暇申請が存在する、false：そうでない)
	 */
	public static boolean isConsecutiveHolidaysExist(Collection<HolidayRequestDtoInterface> dtos) {
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// 休暇申請が連続休暇である場合
			if (isConsecutiveHolidays(dto)) {
				// 連続休暇である休暇申請が存在すると判断
				return true;
			}
		}
		// 連続休暇である休暇申請が存在しないと判断
		return false;
	}
	
	/**
	 * 時差出勤区分略称を取得する。<br>
	 * @param dto        時差出勤申請情報
	 * @param mospParams MosP処理情報
	 * @return 時差出勤区分略称
	 */
	public static String getDifferenceTypeAbbr(DifferenceRequestDtoInterface dto, MospParams mospParams) {
		// 引数が不足する場合
		if (MospUtility.isEmpty(dto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 時差出勤区分略称を取得
		return getDifferenceTypeAbbr(dto.getDifferenceType(), mospParams);
	}
	
	/**
	 * 時差出勤区分略称を取得する。<br>
	 * @param differenceType 時差出勤区分
	 * @param mospParams     MosP処理情報
	 * @return 時差出勤区分略称
	 */
	public static String getDifferenceTypeAbbr(String differenceType, MospParams mospParams) {
		// 引数が不足する場合
		if (MospUtility.isEmpty(differenceType)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 時差出勤区分がAである場合
		if (MospUtility.isEqual(differenceType, TimeConst.CODE_DIFFERENCE_TYPE_A)) {
			// Aを取得
			return TimeNamingUtility.charaA(mospParams);
		}
		// 時差出勤区分がBである場合
		if (MospUtility.isEqual(differenceType, TimeConst.CODE_DIFFERENCE_TYPE_B)) {
			// Bを取得
			return TimeNamingUtility.charaB(mospParams);
		}
		// 時差出勤区分がCである場合
		if (MospUtility.isEqual(differenceType, TimeConst.CODE_DIFFERENCE_TYPE_C)) {
			// Cを取得
			return TimeNamingUtility.charaC(mospParams);
		}
		// 時差出勤区分がDである場合
		if (MospUtility.isEqual(differenceType, TimeConst.CODE_DIFFERENCE_TYPE_D)) {
			// Dを取得
			return TimeNamingUtility.charaD(mospParams);
		}
		// 時差出勤区分がSである場合
		if (MospUtility.isEqual(differenceType, TimeConst.CODE_DIFFERENCE_TYPE_S)) {
			// Sを取得
			return TimeNamingUtility.charaS(mospParams);
		}
		// 時差出勤区分を取得
		return differenceType;
	}
	
	/**
	 * 申請開始日が対象日以前である休暇申請情報群を取得する。<br>
	 * @param dtos       休暇申請情報群
	 * @param targetDate 対象日
	 * @return 申請開始日が対象日以前である休暇申請情報群
	 */
	public static Set<HolidayRequestDtoInterface> getStartedHolidayRequests(Collection<HolidayRequestDtoInterface> dtos,
			Date targetDate) {
		// 申請開始日が対象日以前である休暇申請情報群を準備
		Set<HolidayRequestDtoInterface> startedRequests = new LinkedHashSet<HolidayRequestDtoInterface>();
		// 引数の休暇申請情報群が存在しない場合
		if (MospUtility.isEmpty(dtos)) {
			// 休暇申請情報群を取得
			return startedRequests;
		}
		// 対象日が存在しない場合
		if (MospUtility.isEmpty(targetDate)) {
			// 休暇申請情報群に引数の休暇申請情報群を設定
			startedRequests.addAll(dtos);
			// 休暇申請情報群をそのまま取得
			return startedRequests;
		}
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// 申請開始日が対象日よりも後である場合
			if (dto.getRequestStartDate().after(targetDate)) {
				// 次の休暇申請へ
				continue;
			}
			// 申請開始日が対象日以前である休暇申請情報群に設定
			startedRequests.add(dto);
		}
		// 申請開始日が対象日以前である休暇申請情報群を取得
		return startedRequests;
	}
	
	/**
	 * 情報群にある同一ワークフロー番号の情報を入れ替える。<br>
	 * ワークフロー番号を有する情報群に
	 * ワークフロー番号を有する情報と同じワークフロー番号の情報が存在する場合、
	 * ワークフロー番号を有する情報群からその情報を除去する。<br>
	 * ワークフロー番号を有する情報群に、引数の情報を足す。<br>
	 * 同じワークフロー番号の情報が無い場合でも、引数の情報は足す。<br>
	 * @param dtos ワークフロー番号を有する情報群
	 * @param dto  ワークフロー番号を有する情報
	 */
	public static <T extends WorkflowNumberDtoInterface> void replaceWorkflowDto(Collection<T> dtos, T dto) {
		// ワークフロー番号を取得
		long workflow = dto.getWorkflow();
		// ワークフロー番号を有する情報毎に処理
		for (T workflowDto : dtos) {
			// ワークフロー番号が異なる場合
			if (workflowDto.getWorkflow() != workflow) {
				// 次の情報へ
				continue;
			}
			// ワークフロー番号を有する情報群からその情報を除去(ワークフロー番号が同じ場合)
			dtos.remove(workflowDto);
			// 処理終了
			break;
		}
		// ワークフロー番号を有する情報群に引数の情報を設定
		dtos.add(dto);
	}
	
}
