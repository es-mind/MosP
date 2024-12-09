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
package jp.mosp.time.dao.settings.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.PaidHolidayProportionallyDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayProportionallyDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmPaidHolidayProportionallyDto;

/**
 * 有給休暇比例付与DAO
 */
public class TmmPaidHolidayProportionallyDao extends PlatformDao implements PaidHolidayProportionallyDaoInterface {
	
	/**
	 * 有給休暇比例付与マスタ。
	 */
	public static final String	TABLE															= "tmm_paid_holiday_proportionally";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_PAID_HOLIDAY_PROPORTIONALLY_ID							= "tmm_paid_holiday_proportionally_id";
	
	/**
	 * 有休コード。
	 */
	public static final String	COL_PAID_HOLIDAY_CODE											= "paid_holiday_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE												= "activate_date";
	
	/**
	 * 週所定労働日数。
	 */
	public static final String	COL_PRESCRIBED_WEEKLY_WORKING_DAYS								= "prescribed_weekly_working_days";
	
	/**
	 * 雇入れの日から起算した継続勤務期間。
	 */
	public static final String	COL_CONTINUOUS_SERVICE_TERMS_COUNTING_FROM_THE_EMPLOYMENT_DAY	= "continuous_service_terms_counting_from_the_employment_day";
	
	/**
	 * 日数。
	 */
	public static final String	COL_DAYS														= "days";
	
//	/**
//	 * 一日/六箇月。
//	 */
//	public static final String	COL_ONE_DAY_AND_SIX_MONTHS									= "one_day_and_six_months";
//	
//	/**
//	 * 一日/一年六箇月。
//	 */
//	public static final String	COL_ONE_DAY_AND_ONE_YEAR_AND_SIX_MONTHS						= "one_day_and_one_year_and_six_months";
//	
//	/**
//	 * 一日/二年六箇月。
//	 */
//	public static final String	COL_ONE_DAY_AND_TWO_YEARS_AND_SIX_MONTHS					= "one_day_and_two_years_and_six_months";
//	
//	/**
//	 * 一日/三年六箇月。
//	 */
//	public static final String	COL_ONE_DAY_AND_THREE_YEARS_AND_SIX_MONTHS					= "one_day_and_three_years_and_six_months";
//	
//	/**
//	 * 一日/四年六箇月。
//	 */
//	public static final String	COL_ONE_DAY_AND_FOUR_YEARS_AND_SIX_MONTHS					= "one_day_and_four_years_and_six_months";
//	
//	/**
//	 * 一日/五年六箇月。
//	 */
//	public static final String	COL_ONE_DAY_AND_FIVE_YEARS_AND_SIX_MONTHS					= "one_day_and_five_years_and_six_months";
//	
//	/**
//	 * 一日/六年六箇月以上。
//	 */
//	public static final String	COL_ONE_DAY_AND_SIX_YEARS_AND_SIX_MONTHS_OR_MORE			= "one_day_and_six_years_and_six_months_or_more";
//	
//	/**
//	 * 二日/六箇月。
//	 */
//	public static final String	COL_TWO_DAYS_AND_SIX_MONTHS									= "two_days_and_six_months";
//	
//	/**
//	 * 二日/一年六箇月。
//	 */
//	public static final String	COL_TWO_DAYS_AND_ONE_YEAR_AND_SIX_MONTHS					= "two_days_and_one_year_and_six_months";
//	
//	/**
//	 * 二日/二年六箇月。
//	 */
//	public static final String	COL_TWO_DAYS_AND_TWO_YEARS_AND_SIX_MONTHS					= "two_days_and_two_years_and_six_months";
//	
//	/**
//	 * 二日/三年六箇月。
//	 */
//	public static final String	COL_TWO_DAYS_AND_THREE_YEARS_AND_SIX_MONTHS					= "two_days_and_three_years_and_six_months";
//	
//	/**
//	 * 二日/四年六箇月。
//	 */
//	public static final String	COL_TWO_DAYS_AND_FOUR_YEARS_AND_SIX_MONTHS					= "two_days_and_four_years_and_six_months";
//	
//	/**
//	 * 二日/五年六箇月。
//	 */
//	public static final String	COL_TWO_DAYS_AND_FIVE_YEARS_AND_SIX_MONTHS					= "two_days_and_five_years_and_six_months";
//	
//	/**
//	 * 二日/六年六箇月以上。
//	 */
//	public static final String	COL_TWO_DAYS_AND_SIX_YEARS_AND_SIX_MONTHS_OR_MORE			= "two_days_and_six_years_and_six_months_or_more";
//	
//	/**
//	 * 三日/六箇月。
//	 */
//	public static final String	COL_THREE_DAYS_AND_SIX_MONTHS								= "three_days_and_six_months";
//	
//	/**
//	 * 三日/一年六箇月。
//	 */
//	public static final String	COL_THREE_DAYS_AND_ONE_YEAR_AND_SIX_MONTHS					= "three_days_and_one_year_and_six_months";
//	
//	/**
//	 * 三日/二年六箇月。
//	 */
//	public static final String	COL_THREE_DAYS_AND_TWO_YEARS_AND_SIX_MONTHS					= "three_days_and_two_years_and_six_months";
//	
//	/**
//	 * 三日/三年六箇月。
//	 */
//	public static final String	COL_THREE_DAYS_AND_THREE_YEARS_AND_SIX_MONTHS				= "three_days_and_three_years_and_six_months";
//	
//	/**
//	 * 三日/四年六箇月。
//	 */
//	public static final String	COL_THREE_DAYS_AND_FOUR_YEARS_AND_SIX_MONTHS				= "three_days_and_four_years_and_six_months";
//	
//	/**
//	 * 三日/五年六箇月。
//	 */
//	public static final String	COL_THREE_DAYS_AND_FIVE_YEARS_AND_SIX_MONTHS				= "three_days_and_five_years_and_six_months";
//	
//	/**
//	 * 三日/六年六箇月以上。
//	 */
//	public static final String	COL_THREE_DAYS_AND_SIX_YEARS_AND_SIX_MONTHS_OR_MORE			= "three_days_and_six_years_and_six_months_or_more";
//	
//	/**
//	 * 四日/六箇月。
//	 */
//	public static final String	COL_FOUR_DAYS_AND_SIX_MONTHS								= "four_days_and_six_months";
//	
//	/**
//	 * 四日/一年六箇月。
//	 */
//	public static final String	COL_FOUR_DAYS_AND_ONE_YEAR_AND_SIX_MONTHS					= "four_days_and_one_year_and_six_months";
//	
//	/**
//	 * 四日/二年六箇月。
//	 */
//	public static final String	COL_FOUR_DAYS_AND_TWO_YEARS_AND_SIX_MONTHS					= "four_days_and_two_years_and_six_months";
//	
//	/**
//	 * 四日/三年六箇月。
//	 */
//	public static final String	COL_FOUR_DAYS_AND_THREE_YEARS_AND_SIX_MONTHS				= "four_days_and_three_years_and_six_months";
//	
//	/**
//	 * 四日/四年六箇月。
//	 */
//	public static final String	COL_FOUR_DAYS_AND_FOUR_YEARS_AND_SIX_MONTHS					= "four_days_and_four_years_and_six_months";
//	
//	/**
//	 * 四日/五年六箇月。
//	 */
//	public static final String	COL_FOUR_DAYS_AND_FIVE_YEARS_AND_SIX_MONTHS					= "four_days_and_five_years_and_six_months";
//	
//	/**
//	 * 四日/六年六箇月以上。
//	 */
//	public static final String	COL_FOUR_DAYS_AND_SIX_YEARS_AND_SIX_MONTHS_OR_MORE			= "four_days_and_six_years_and_six_months_or_more";
//	
//	/**
//	 * 五日以上/六箇月。
//	 */
//	public static final String	COL_FIVE_DAYS_OR_MORE_AND_SIX_MONTHS						= "five_days_or_more_and_six_months";
//	
//	/**
//	 * 五日以上/一年六箇月。
//	 */
//	public static final String	COL_FIVE_DAYS_OR_MORE_AND_ONE_YEAR_AND_SIX_MONTHS			= "five_days_or_more_and_one_year_and_six_months";
//	
//	/**
//	 * 五日以上/二年六箇月。
//	 */
//	public static final String	COL_FIVE_DAYS_OR_MORE_AND_TWO_YEARS_AND_SIX_MONTHS			= "five_days_or_more_and_two_years_and_six_months";
//	
//	/**
//	 * 五日以上/三年六箇月。
//	 */
//	public static final String	COL_FIVE_DAYS_OR_MORE_AND_THREE_YEARS_AND_SIX_MONTHS		= "five_days_or_more_and_three_years_and_six_months";
//	
//	/**
//	 * 五日以上/四年六箇月。
//	 */
//	public static final String	COL_FIVE_DAYS_OR_MORE_AND_FOUR_YEARS_AND_SIX_MONTHS			= "five_days_or_more_and_four_years_and_six_months";
//	
//	/**
//	 * 五日以上/五年六箇月。
//	 */
//	public static final String	COL_FIVE_DAYS_OR_MORE_AND_FIVE_YEARS_AND_SIX_MONTHS			= "five_days_or_more_and_five_years_and_six_months";
//	
//	/**
//	 * 五日以上/六年六箇月以上。
//	 */
//	public static final String	COL_FIVE_DAYS_OR_MORE_AND_SIX_YEARS_AND_SIX_MONTHS_OR_MORE	= "five_days_or_more_and_six_years_and_six_months_or_more";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG												= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1															= COL_TMM_PAID_HOLIDAY_PROPORTIONALLY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmPaidHolidayProportionallyDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmPaidHolidayProportionallyDto dto = new TmmPaidHolidayProportionallyDto();
		dto.setTmmPaidHolidayProportionallyId(getLong(COL_TMM_PAID_HOLIDAY_PROPORTIONALLY_ID));
		dto.setPaidHolidayCode(getString(COL_PAID_HOLIDAY_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setPrescribedWeeklyWorkingDays(getInt(COL_PRESCRIBED_WEEKLY_WORKING_DAYS));
		dto.setContinuousServiceTermsCountingFromTheEmploymentDay(
				getInt(COL_CONTINUOUS_SERVICE_TERMS_COUNTING_FROM_THE_EMPLOYMENT_DAY));
		dto.setDays(getInt(COL_DAYS));
//		dto.setOneDayAndSixMonths(getInt(COL_ONE_DAY_AND_SIX_MONTHS));
//		dto.setOneDayAndOneYearAndSixMonths(getInt(COL_ONE_DAY_AND_ONE_YEAR_AND_SIX_MONTHS));
//		dto.setOneDayAndTwoYearsAndSixMonths(getInt(COL_ONE_DAY_AND_TWO_YEARS_AND_SIX_MONTHS));
//		dto.setOneDayAndThreeYearsAndSixMonths(getInt(COL_ONE_DAY_AND_THREE_YEARS_AND_SIX_MONTHS));
//		dto.setOneDayAndFourYearsAndSixMonths(getInt(COL_ONE_DAY_AND_FOUR_YEARS_AND_SIX_MONTHS));
//		dto.setOneDayAndFiveYearsAndSixMonths(getInt(COL_ONE_DAY_AND_FIVE_YEARS_AND_SIX_MONTHS));
//		dto.setOneDayAndSixYearsAndSixMonthsOrMore(getInt(COL_ONE_DAY_AND_SIX_YEARS_AND_SIX_MONTHS_OR_MORE));
//		dto.setTwoDaysAndSixMonths(getInt(COL_TWO_DAYS_AND_SIX_MONTHS));
//		dto.setTwoDaysAndOneYearAndSixMonths(getInt(COL_TWO_DAYS_AND_ONE_YEAR_AND_SIX_MONTHS));
//		dto.setTwoDaysAndTwoYearsAndSixMonths(getInt(COL_TWO_DAYS_AND_TWO_YEARS_AND_SIX_MONTHS));
//		dto.setTwoDaysAndThreeYearsAndSixMonths(getInt(COL_TWO_DAYS_AND_THREE_YEARS_AND_SIX_MONTHS));
//		dto.setTwoDaysAndFourYearsAndSixMonths(getInt(COL_TWO_DAYS_AND_FOUR_YEARS_AND_SIX_MONTHS));
//		dto.setTwoDaysAndFiveYearsAndSixMonths(getInt(COL_TWO_DAYS_AND_FIVE_YEARS_AND_SIX_MONTHS));
//		dto.setTwoDaysAndSixYearsAndSixMonthsOrMore(getInt(COL_TWO_DAYS_AND_SIX_YEARS_AND_SIX_MONTHS_OR_MORE));
//		dto.setThreeDaysAndSixMonths(getInt(COL_THREE_DAYS_AND_SIX_MONTHS));
//		dto.setThreeDaysAndOneYearAndSixMonths(getInt(COL_THREE_DAYS_AND_ONE_YEAR_AND_SIX_MONTHS));
//		dto.setThreeDaysAndTwoYearsAndSixMonths(getInt(COL_THREE_DAYS_AND_TWO_YEARS_AND_SIX_MONTHS));
//		dto.setThreeDaysAndThreeYearsAndSixMonths(getInt(COL_THREE_DAYS_AND_THREE_YEARS_AND_SIX_MONTHS));
//		dto.setThreeDaysAndFourYearsAndSixMonths(getInt(COL_THREE_DAYS_AND_FOUR_YEARS_AND_SIX_MONTHS));
//		dto.setThreeDaysAndFiveYearsAndSixMonths(getInt(COL_THREE_DAYS_AND_FIVE_YEARS_AND_SIX_MONTHS));
//		dto.setThreeDaysAndSixYearsAndSixMonthsOrMore(getInt(COL_THREE_DAYS_AND_SIX_YEARS_AND_SIX_MONTHS_OR_MORE));
//		dto.setFourDaysAndSixMonths(getInt(COL_FOUR_DAYS_AND_SIX_MONTHS));
//		dto.setFourDaysAndOneYearAndSixMonths(getInt(COL_FOUR_DAYS_AND_ONE_YEAR_AND_SIX_MONTHS));
//		dto.setFourDaysAndTwoYearsAndSixMonths(getInt(COL_FOUR_DAYS_AND_TWO_YEARS_AND_SIX_MONTHS));
//		dto.setFourDaysAndThreeYearsAndSixMonths(getInt(COL_FOUR_DAYS_AND_THREE_YEARS_AND_SIX_MONTHS));
//		dto.setFourDaysAndFourYearsAndSixMonths(getInt(COL_FOUR_DAYS_AND_FOUR_YEARS_AND_SIX_MONTHS));
//		dto.setFourDaysAndFiveYearsAndSixMonths(getInt(COL_FOUR_DAYS_AND_FIVE_YEARS_AND_SIX_MONTHS));
//		dto.setFourDaysAndSixYearsAndSixMonthsOrMore(getInt(COL_FOUR_DAYS_AND_SIX_YEARS_AND_SIX_MONTHS_OR_MORE));
//		dto.setFiveDaysOrMoreAndSixMonths(getInt(COL_FIVE_DAYS_OR_MORE_AND_SIX_MONTHS));
//		dto.setFiveDaysOrMoreAndOneYearAndSixMonths(getInt(COL_FIVE_DAYS_OR_MORE_AND_ONE_YEAR_AND_SIX_MONTHS));
//		dto.setFiveDaysOrMoreAndTwoYearsAndSixMonths(getInt(COL_FIVE_DAYS_OR_MORE_AND_TWO_YEARS_AND_SIX_MONTHS));
//		dto.setFiveDaysOrMoreAndThreeYearsAndSixMonths(getInt(COL_FIVE_DAYS_OR_MORE_AND_THREE_YEARS_AND_SIX_MONTHS));
//		dto.setFiveDaysOrMoreAndFourYearsAndSixMonths(getInt(COL_FIVE_DAYS_OR_MORE_AND_FOUR_YEARS_AND_SIX_MONTHS));
//		dto.setFiveDaysOrMoreAndFiveYearsAndSixMonths(getInt(COL_FIVE_DAYS_OR_MORE_AND_FIVE_YEARS_AND_SIX_MONTHS));
//		dto.setFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore(getInt(COL_FIVE_DAYS_OR_MORE_AND_SIX_YEARS_AND_SIX_MONTHS_OR_MORE));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<PaidHolidayProportionallyDtoInterface> mappingAll() throws MospException {
		List<PaidHolidayProportionallyDtoInterface> all = new ArrayList<PaidHolidayProportionallyDtoInterface>();
		while (next()) {
			all.add((PaidHolidayProportionallyDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			PaidHolidayProportionallyDtoInterface dto = (PaidHolidayProportionallyDtoInterface)baseDto;
			setParam(index++, dto.getTmmPaidHolidayProportionallyId());
			executeUpdate();
			chkUpdate(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			PaidHolidayProportionallyDtoInterface dto = (PaidHolidayProportionallyDtoInterface)baseDto;
			setParam(index++, dto.getTmmPaidHolidayProportionallyId());
			executeUpdate();
			chkDelete(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		PaidHolidayProportionallyDtoInterface dto = (PaidHolidayProportionallyDtoInterface)baseDto;
		setParam(index++, dto.getTmmPaidHolidayProportionallyId());
		setParam(index++, dto.getPaidHolidayCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getPrescribedWeeklyWorkingDays());
		setParam(index++, dto.getContinuousServiceTermsCountingFromTheEmploymentDay());
		setParam(index++, dto.getDays());
//		setParam(index++, dto.getOneDayAndSixMonths());
//		setParam(index++, dto.getOneDayAndOneYearAndSixMonths());
//		setParam(index++, dto.getOneDayAndTwoYearsAndSixMonths());
//		setParam(index++, dto.getOneDayAndThreeYearsAndSixMonths());
//		setParam(index++, dto.getOneDayAndFourYearsAndSixMonths());
//		setParam(index++, dto.getOneDayAndFiveYearsAndSixMonths());
//		setParam(index++, dto.getOneDayAndSixYearsAndSixMonthsOrMore());
//		setParam(index++, dto.getTwoDaysAndSixMonths());
//		setParam(index++, dto.getTwoDaysAndOneYearAndSixMonths());
//		setParam(index++, dto.getTwoDaysAndTwoYearsAndSixMonths());
//		setParam(index++, dto.getTwoDaysAndThreeYearsAndSixMonths());
//		setParam(index++, dto.getTwoDaysAndFourYearsAndSixMonths());
//		setParam(index++, dto.getTwoDaysAndFiveYearsAndSixMonths());
//		setParam(index++, dto.getTwoDaysAndSixYearsAndSixMonthsOrMore());
//		setParam(index++, dto.getThreeDaysAndSixMonths());
//		setParam(index++, dto.getThreeDaysAndOneYearAndSixMonths());
//		setParam(index++, dto.getThreeDaysAndTwoYearsAndSixMonths());
//		setParam(index++, dto.getThreeDaysAndThreeYearsAndSixMonths());
//		setParam(index++, dto.getThreeDaysAndFourYearsAndSixMonths());
//		setParam(index++, dto.getThreeDaysAndFiveYearsAndSixMonths());
//		setParam(index++, dto.getThreeDaysAndSixYearsAndSixMonthsOrMore());
//		setParam(index++, dto.getFourDaysAndSixMonths());
//		setParam(index++, dto.getFourDaysAndOneYearAndSixMonths());
//		setParam(index++, dto.getFourDaysAndTwoYearsAndSixMonths());
//		setParam(index++, dto.getFourDaysAndThreeYearsAndSixMonths());
//		setParam(index++, dto.getFourDaysAndFourYearsAndSixMonths());
//		setParam(index++, dto.getFourDaysAndFiveYearsAndSixMonths());
//		setParam(index++, dto.getFourDaysAndSixYearsAndSixMonthsOrMore());
//		setParam(index++, dto.getFiveDaysOrMoreAndSixMonths());
//		setParam(index++, dto.getFiveDaysOrMoreAndOneYearAndSixMonths());
//		setParam(index++, dto.getFiveDaysOrMoreAndTwoYearsAndSixMonths());
//		setParam(index++, dto.getFiveDaysOrMoreAndThreeYearsAndSixMonths());
//		setParam(index++, dto.getFiveDaysOrMoreAndFourYearsAndSixMonths());
//		setParam(index++, dto.getFiveDaysOrMoreAndFiveYearsAndSixMonths());
//		setParam(index++, dto.getFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public PaidHolidayProportionallyDtoInterface findForKey(String paidHolidayCode, Date activateDate,
			int prescribedWeeklyWorkingDays, int continuousServiceTermsCountingFromTheEmploymentDay)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_PRESCRIBED_WEEKLY_WORKING_DAYS));
			sb.append(and());
			sb.append(equal(COL_CONTINUOUS_SERVICE_TERMS_COUNTING_FROM_THE_EMPLOYMENT_DAY));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, prescribedWeeklyWorkingDays);
			setParam(index++, continuousServiceTermsCountingFromTheEmploymentDay);
			setParam(index++, activateDate);
			executeQuery();
			PaidHolidayProportionallyDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayProportionallyDtoInterface)mapping();
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public PaidHolidayProportionallyDtoInterface findForInfo(String paidHolidayCode, Date activateDate,
			int prescribedWeeklyWorkingDays, int continuousServiceTermsCountingFromTheEmploymentDay)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_PRESCRIBED_WEEKLY_WORKING_DAYS));
			sb.append(and());
			sb.append(equal(COL_CONTINUOUS_SERVICE_TERMS_COUNTING_FROM_THE_EMPLOYMENT_DAY));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, prescribedWeeklyWorkingDays);
			setParam(index++, continuousServiceTermsCountingFromTheEmploymentDay);
			setParam(index++, activateDate);
			executeQuery();
			PaidHolidayProportionallyDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayProportionallyDtoInterface)mapping();
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<PaidHolidayProportionallyDtoInterface> findForList(String paidHolidayCode, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, activateDate);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<PaidHolidayProportionallyDtoInterface> findForHistory(String paidHolidayCode,
			int prescribedWeeklyWorkingDays, int continuousServiceTermsCountingFromTheEmploymentDay)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_PRESCRIBED_WEEKLY_WORKING_DAYS));
			sb.append(and());
			sb.append(equal(COL_CONTINUOUS_SERVICE_TERMS_COUNTING_FROM_THE_EMPLOYMENT_DAY));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, prescribedWeeklyWorkingDays);
			setParam(index++, continuousServiceTermsCountingFromTheEmploymentDay);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
