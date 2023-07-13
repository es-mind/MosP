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
package jp.mosp.time.settings.vo;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 有給休暇設定情報の情報を格納する。
 */
public class PaidHolidayCardVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= 3562196007204395576L;
	
	private String				txtPaidHolidayCode;
	private String				txtPaidHolidayName;
	private String				txtPaidHolidayAbbr;
	private String				txtWorkRatio;
	private String				pltPaidHolidayType;
	private String				pltTimelyPaidHoliday;
	// 有休単位時間(画面上で表示せず必ず1を登録)
	private String				pltTimelyPaidHolidayTime;
	// 有休時間取得限度日数
	private String				pltTimeAcquisitionLimitDays;
	// 有休時間取得限度時間
	private String				pltTimeAcquisitionLimitTimes;
	// 申請時間間隔
	private String				pltAppliTimeInterval;
	private String				txtMaxCarryOverAmount;
	private String				txtTotalMaxAmount;
	private String				pltMaxCarryOverYear;
	// 時間単位繰越
	private String				pltMaxCarryOverTimes;
	// 半日単位取得
	private String				pltHalfDayUnit;
	// TODO
	private String				pltWorkOnHolidayCalc;
	
	private String				txtProportionallyOneDayAndSixMonths;
	private String				txtProportionallyOneDayAndOneYearAndSixMonths;
	private String				txtProportionallyOneDayAndTwoYearsAndSixMonths;
	private String				txtProportionallyOneDayAndThreeYearsAndSixMonths;
	private String				txtProportionallyOneDayAndFourYearsAndSixMonths;
	private String				txtProportionallyOneDayAndFiveYearsAndSixMonths;
	private String				txtProportionallyOneDayAndSixYearsAndSixMonthsOrMore;
	private String				txtProportionallyTwoDaysAndSixMonths;
	private String				txtProportionallyTwoDaysAndOneYearAndSixMonths;
	private String				txtProportionallyTwoDaysAndTwoYearsAndSixMonths;
	private String				txtProportionallyTwoDaysAndThreeYearsAndSixMonths;
	private String				txtProportionallyTwoDaysAndFourYearsAndSixMonths;
	private String				txtProportionallyTwoDaysAndFiveYearsAndSixMonths;
	private String				txtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore;
	private String				txtProportionallyThreeDaysAndSixMonths;
	private String				txtProportionallyThreeDaysAndOneYearAndSixMonths;
	private String				txtProportionallyThreeDaysAndTwoYearsAndSixMonths;
	private String				txtProportionallyThreeDaysAndThreeYearsAndSixMonths;
	private String				txtProportionallyThreeDaysAndFourYearsAndSixMonths;
	private String				txtProportionallyThreeDaysAndFiveYearsAndSixMonths;
	private String				txtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore;
	private String				txtProportionallyFourDaysAndSixMonths;
	private String				txtProportionallyFourDaysAndOneYearAndSixMonths;
	private String				txtProportionallyFourDaysAndTwoYearsAndSixMonths;
	private String				txtProportionallyFourDaysAndThreeYearsAndSixMonths;
	private String				txtProportionallyFourDaysAndFourYearsAndSixMonths;
	private String				txtProportionallyFourDaysAndFiveYearsAndSixMonths;
	private String				txtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore;
	private String				txtProportionallyFiveDaysOrMoreAndSixMonths;
	private String				txtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths;
	private String				txtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths;
	private String				txtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths;
	private String				txtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths;
	private String				txtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths;
	private String				txtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore;
	
	private String				txtGivingTimingJanuary;
	private String				txtGivingTimingFebruary;
	private String				txtGivingTimingMarch;
	private String				txtGivingTimingApril;
	private String				txtGivingTimingMay;
	private String				txtGivingTimingJune;
	private String				txtGivingTimingJuly;
	private String				txtGivingTimingAugust;
	private String				txtGivingTimingSeptember;
	private String				txtGivingTimingOctober;
	private String				txtGivingTimingNovember;
	private String				txtGivingTimingDecember;
	
	private String				txtGivingAmountJanuary;
	private String				txtGivingAmountFebruary;
	private String				txtGivingAmountMarch;
	private String				txtGivingAmountApril;
	private String				txtGivingAmountMay;
	private String				txtGivingAmountJune;
	private String				txtGivingAmountJuly;
	private String				txtGivingAmountAugust;
	private String				txtGivingAmountSeptember;
	private String				txtGivingAmountOctober;
	private String				txtGivingAmountNovember;
	private String				txtGivingAmountDecember;
	
	private String				txtGivingLimitJanuary;
	private String				txtGivingLimitFebruary;
	private String				txtGivingLimitMarch;
	private String				txtGivingLimitApril;
	private String				txtGivingLimitMay;
	private String				txtGivingLimitJune;
	private String				txtGivingLimitJuly;
	private String				txtGivingLimitAugust;
	private String				txtGivingLimitSeptember;
	private String				txtGivingLimitOctober;
	private String				txtGivingLimitNovember;
	private String				txtGivingLimitDecember;
	
	private String				txtPointDateMonth;
	private String				txtPointDateDay;
	
	private String				txtTimesPointDate1;
	private String				txtTimesPointDate2;
	private String				txtTimesPointDate3;
	private String				txtTimesPointDate4;
	private String				txtTimesPointDate5;
	private String				txtTimesPointDate6;
	private String				txtTimesPointDate7;
	private String				txtTimesPointDate8;
	private String				txtTimesPointDate9;
	private String				txtTimesPointDate10;
	private String				txtTimesPointDate11;
	private String				txtTimesPointDate12;
	
	private String				txtPointDateAmount1;
	private String				txtPointDateAmount2;
	private String				txtPointDateAmount3;
	private String				txtPointDateAmount4;
	private String				txtPointDateAmount5;
	private String				txtPointDateAmount6;
	private String				txtPointDateAmount7;
	private String				txtPointDateAmount8;
	private String				txtPointDateAmount9;
	private String				txtPointDateAmount10;
	private String				txtPointDateAmount11;
	private String				txtPointDateAmount12;
	
	private String				txtGeneralPointAmount;
	
	private String				txtWorkYear1;
	private String				txtWorkYear2;
	private String				txtWorkYear3;
	private String				txtWorkYear4;
	private String				txtWorkYear5;
	private String				txtWorkYear6;
	private String				txtWorkYear7;
	private String				txtWorkYear8;
	private String				txtWorkYear9;
	private String				txtWorkYear10;
	private String				txtWorkYear11;
	private String				txtWorkYear12;
	private String				txtWorkMonth1;
	private String				txtWorkMonth2;
	private String				txtWorkMonth3;
	private String				txtWorkMonth4;
	private String				txtWorkMonth5;
	private String				txtWorkMonth6;
	private String				txtWorkMonth7;
	private String				txtWorkMonth8;
	private String				txtWorkMonth9;
	private String				txtWorkMonth10;
	private String				txtWorkMonth11;
	private String				txtWorkMonth12;
	private String				txtJoiningDateAmount1;
	private String				txtJoiningDateAmount2;
	private String				txtJoiningDateAmount3;
	private String				txtJoiningDateAmount4;
	private String				txtJoiningDateAmount5;
	private String				txtJoiningDateAmount6;
	private String				txtJoiningDateAmount7;
	private String				txtJoiningDateAmount8;
	private String				txtJoiningDateAmount9;
	private String				txtJoiningDateAmount10;
	private String				txtJoiningDateAmount11;
	private String				txtJoiningDateAmount12;
	
	private String				txtGeneralJoiningMonth;
	private String				txtGeneralJoiningAmount;
	
	private String				txtStockYearAmount;
	private String				txtStockTotalAmount;
	private String				txtStockLimitDate;
	
	private long				tmmStockHolidayId;
	private long[]				tmmPaidHolidayFirstYearId;
	private long[]				tmmPaidHolidayPointDateId;
	private long[]				tmmPaidHolidayEntranceDateId;
	
	
	/**
	 * @return txtPaidHolidayCode
	 */
	public String getTxtPaidHolidayCode() {
		return txtPaidHolidayCode;
	}
	
	/**
	 * @param txtPaidHolidayCode セットする txtPaidHolidayCode
	 */
	public void setTxtPaidHolidayCode(String txtPaidHolidayCode) {
		this.txtPaidHolidayCode = txtPaidHolidayCode;
	}
	
	/**
	 * @return txtPaidHolidayName
	 */
	public String getTxtPaidHolidayName() {
		return txtPaidHolidayName;
	}
	
	/**
	 * @param txtPaidHolidayName セットする txtPaidHolidayName
	 */
	public void setTxtPaidHolidayName(String txtPaidHolidayName) {
		this.txtPaidHolidayName = txtPaidHolidayName;
	}
	
	/**
	 * @return txtPaidHolidayAbbr
	 */
	public String getTxtPaidHolidayAbbr() {
		return txtPaidHolidayAbbr;
	}
	
	/**
	 * @param txtPaidHolidayAbbr セットする txtPaidHolidayAbbr
	 */
	public void setTxtPaidHolidayAbbr(String txtPaidHolidayAbbr) {
		this.txtPaidHolidayAbbr = txtPaidHolidayAbbr;
	}
	
	/**
	 * @return txtWorkRatio
	 */
	public String getTxtWorkRatio() {
		return txtWorkRatio;
	}
	
	/**
	 * @param txtWorkRatio セットする txtWorkRatio
	 */
	public void setTxtWorkRatio(String txtWorkRatio) {
		this.txtWorkRatio = txtWorkRatio;
	}
	
	/**
	 * @return pltPaidHolidayType
	 */
	public String getPltPaidHolidayType() {
		return pltPaidHolidayType;
	}
	
	/**
	 * @param pltPaidHolidayType セットする pltPaidHolidayType
	 */
	public void setPltPaidHolidayType(String pltPaidHolidayType) {
		this.pltPaidHolidayType = pltPaidHolidayType;
	}
	
	/**
	 * @return pltTimelyPaidHoliday
	 */
	public String getPltTimelyPaidHoliday() {
		return pltTimelyPaidHoliday;
	}
	
	/**
	 * @param pltTimelyPaidHoliday セットする pltTimelyPaidHoliday
	 */
	public void setPltTimelyPaidHoliday(String pltTimelyPaidHoliday) {
		this.pltTimelyPaidHoliday = pltTimelyPaidHoliday;
	}
	
	/**
	 * @return pltTimelyPaidHolidayTime
	 */
	public String getPltTimelyPaidHolidayTime() {
		return pltTimelyPaidHolidayTime;
	}
	
	/**
	 * @param pltTimelyPaidHolidayTime セットする pltTimelyPaidHolidayTime
	 */
	public void setPltTimelyPaidHolidayTime(String pltTimelyPaidHolidayTime) {
		this.pltTimelyPaidHolidayTime = pltTimelyPaidHolidayTime;
	}
	
	/**
	 * @return pltTimeAcquisitionLimitDays
	 */
	public String getPltTimeAcquisitionLimitDays() {
		return pltTimeAcquisitionLimitDays;
	}
	
	/**
	 * @param pltTimeAcquisitionLimitDays セットする pltTimeAcquisitionLimitDays
	 */
	public void setPltTimeAcquisitionLimitDays(String pltTimeAcquisitionLimitDays) {
		this.pltTimeAcquisitionLimitDays = pltTimeAcquisitionLimitDays;
	}
	
	/**
	 * @return pltTimeAcquisitionLimitTimes
	 */
	public String getPltTimeAcquisitionLimitTimes() {
		return pltTimeAcquisitionLimitTimes;
	}
	
	/**
	 * @param pltTimeAcquisitionLimitTimes セットする pltTimeAcquisitionLimitTimes
	 */
	public void setPltTimeAcquisitionLimitTimes(String pltTimeAcquisitionLimitTimes) {
		this.pltTimeAcquisitionLimitTimes = pltTimeAcquisitionLimitTimes;
	}
	
	/**
	 * @return pltAppliTimeInterval
	 */
	public String getPltAppliTimeInterval() {
		return pltAppliTimeInterval;
	}
	
	/**
	 * @param pltAppliTimeInterval セットする pltAppliTimeInterval
	 */
	public void setPltAppliTimeInterval(String pltAppliTimeInterval) {
		this.pltAppliTimeInterval = pltAppliTimeInterval;
	}
	
	/**
	 * @return txtMaxCarryOverAmount
	 */
	public String getTxtMaxCarryOverAmount() {
		return txtMaxCarryOverAmount;
	}
	
	/**
	 * @param txtMaxCarryOverAmount セットする txtMaxCarryOverAmount
	 */
	public void setTxtMaxCarryOverAmount(String txtMaxCarryOverAmount) {
		this.txtMaxCarryOverAmount = txtMaxCarryOverAmount;
	}
	
	/**
	 * @return txtTotalMaxAmount
	 */
	public String getTxtTotalMaxAmount() {
		return txtTotalMaxAmount;
	}
	
	/**
	 * @param txtTotalMaxAmount セットする txtTotalMaxAmount
	 */
	public void setTxtTotalMaxAmount(String txtTotalMaxAmount) {
		this.txtTotalMaxAmount = txtTotalMaxAmount;
	}
	
	/**
	 * @return pltMaxCarryOverYear
	 */
	public String getPltMaxCarryOverYear() {
		return pltMaxCarryOverYear;
	}
	
	/**
	 * @param pltMaxCarryOverYear セットする pltMaxCarryOverYear
	 */
	public void setPltMaxCarryOverYear(String pltMaxCarryOverYear) {
		this.pltMaxCarryOverYear = pltMaxCarryOverYear;
	}
	
	/**
	 * @return pltMaxCarryOverTimes
	 */
	public String getPltMaxCarryOverTimes() {
		return pltMaxCarryOverTimes;
	}
	
	/**
	 * @param pltMaxCarryOverTimes セットする pltMaxCarryOverTimes
	 */
	public void setPltMaxCarryOverTimes(String pltMaxCarryOverTimes) {
		this.pltMaxCarryOverTimes = pltMaxCarryOverTimes;
	}
	
	/**
	 * @return pltHalfDayUnit
	 */
	public String getPltHalfDayUnit() {
		return pltHalfDayUnit;
	}
	
	/**
	 * @param pltHalfDayUnit セットする pltHalfDayUnit
	 */
	public void setPltHalfDayUnit(String pltHalfDayUnit) {
		this.pltHalfDayUnit = pltHalfDayUnit;
	}
	
	/**
	 * @return pltWorkOnHolidayCalc
	 */
	public String getPltWorkOnHolidayCalc() {
		return pltWorkOnHolidayCalc;
	}
	
	/**
	 * @param pltWorkOnHolidayCalc セットする pltWorkOnHolidayCalc
	 */
	public void setPltWorkOnHolidayCalc(String pltWorkOnHolidayCalc) {
		this.pltWorkOnHolidayCalc = pltWorkOnHolidayCalc;
	}
	
	/**
	 * @return txtProportionallyOneDayAndSixMonths
	 */
	public String getTxtProportionallyOneDayAndSixMonths() {
		return txtProportionallyOneDayAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyOneDayAndSixMonths セットする txtProportionallyOneDayAndSixMonths
	 */
	public void setTxtProportionallyOneDayAndSixMonths(String txtProportionallyOneDayAndSixMonths) {
		this.txtProportionallyOneDayAndSixMonths = txtProportionallyOneDayAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyOneDayAndOneYearAndSixMonths
	 */
	public String getTxtProportionallyOneDayAndOneYearAndSixMonths() {
		return txtProportionallyOneDayAndOneYearAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyOneDayAndOneYearAndSixMonths セットする txtProportionallyOneDayAndOneYearAndSixMonths
	 */
	public void setTxtProportionallyOneDayAndOneYearAndSixMonths(String txtProportionallyOneDayAndOneYearAndSixMonths) {
		this.txtProportionallyOneDayAndOneYearAndSixMonths = txtProportionallyOneDayAndOneYearAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyOneDayAndTwoYearsAndSixMonths
	 */
	public String getTxtProportionallyOneDayAndTwoYearsAndSixMonths() {
		return txtProportionallyOneDayAndTwoYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyOneDayAndTwoYearsAndSixMonths セットする txtProportionallyOneDayAndTwoYearsAndSixMonths
	 */
	public void setTxtProportionallyOneDayAndTwoYearsAndSixMonths(
			String txtProportionallyOneDayAndTwoYearsAndSixMonths) {
		this.txtProportionallyOneDayAndTwoYearsAndSixMonths = txtProportionallyOneDayAndTwoYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyOneDayAndThreeYearsAndSixMonths
	 */
	public String getTxtProportionallyOneDayAndThreeYearsAndSixMonths() {
		return txtProportionallyOneDayAndThreeYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyOneDayAndThreeYearsAndSixMonths セットする txtProportionallyOneDayAndThreeYearsAndSixMonths
	 */
	public void setTxtProportionallyOneDayAndThreeYearsAndSixMonths(
			String txtProportionallyOneDayAndThreeYearsAndSixMonths) {
		this.txtProportionallyOneDayAndThreeYearsAndSixMonths = txtProportionallyOneDayAndThreeYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyOneDayAndFourYearsAndSixMonths
	 */
	public String getTxtProportionallyOneDayAndFourYearsAndSixMonths() {
		return txtProportionallyOneDayAndFourYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyOneDayAndFourYearsAndSixMonths セットする txtProportionallyOneDayAndFourYearsAndSixMonths
	 */
	public void setTxtProportionallyOneDayAndFourYearsAndSixMonths(
			String txtProportionallyOneDayAndFourYearsAndSixMonths) {
		this.txtProportionallyOneDayAndFourYearsAndSixMonths = txtProportionallyOneDayAndFourYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyOneDayAndFiveYearsAndSixMonths
	 */
	public String getTxtProportionallyOneDayAndFiveYearsAndSixMonths() {
		return txtProportionallyOneDayAndFiveYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyOneDayAndFiveYearsAndSixMonths セットする txtProportionallyOneDayAndFiveYearsAndSixMonths
	 */
	public void setTxtProportionallyOneDayAndFiveYearsAndSixMonths(
			String txtProportionallyOneDayAndFiveYearsAndSixMonths) {
		this.txtProportionallyOneDayAndFiveYearsAndSixMonths = txtProportionallyOneDayAndFiveYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyOneDayAndSixYearsAndSixMonthsOrMore
	 */
	public String getTxtProportionallyOneDayAndSixYearsAndSixMonthsOrMore() {
		return txtProportionallyOneDayAndSixYearsAndSixMonthsOrMore;
	}
	
	/**
	 * @param txtProportionallyOneDayAndSixYearsAndSixMonthsOrMore セットする txtProportionallyOneDayAndSixYearsAndSixMonthsOrMore
	 */
	public void setTxtProportionallyOneDayAndSixYearsAndSixMonthsOrMore(
			String txtProportionallyOneDayAndSixYearsAndSixMonthsOrMore) {
		this.txtProportionallyOneDayAndSixYearsAndSixMonthsOrMore = txtProportionallyOneDayAndSixYearsAndSixMonthsOrMore;
	}
	
	/**
	 * @return txtProportionallyTwoDaysAndSixMonths
	 */
	public String getTxtProportionallyTwoDaysAndSixMonths() {
		return txtProportionallyTwoDaysAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyTwoDaysAndSixMonths セットする txtProportionallyTwoDaysAndSixMonths
	 */
	public void setTxtProportionallyTwoDaysAndSixMonths(String txtProportionallyTwoDaysAndSixMonths) {
		this.txtProportionallyTwoDaysAndSixMonths = txtProportionallyTwoDaysAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyTwoDaysAndOneYearAndSixMonths
	 */
	public String getTxtProportionallyTwoDaysAndOneYearAndSixMonths() {
		return txtProportionallyTwoDaysAndOneYearAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyTwoDaysAndOneYearAndSixMonths セットする txtProportionallyTwoDaysAndOneYearAndSixMonths
	 */
	public void setTxtProportionallyTwoDaysAndOneYearAndSixMonths(
			String txtProportionallyTwoDaysAndOneYearAndSixMonths) {
		this.txtProportionallyTwoDaysAndOneYearAndSixMonths = txtProportionallyTwoDaysAndOneYearAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyTwoDaysAndTwoYearsAndSixMonths
	 */
	public String getTxtProportionallyTwoDaysAndTwoYearsAndSixMonths() {
		return txtProportionallyTwoDaysAndTwoYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyTwoDaysAndTwoYearsAndSixMonths セットする txtProportionallyTwoDaysAndTwoYearsAndSixMonths
	 */
	public void setTxtProportionallyTwoDaysAndTwoYearsAndSixMonths(
			String txtProportionallyTwoDaysAndTwoYearsAndSixMonths) {
		this.txtProportionallyTwoDaysAndTwoYearsAndSixMonths = txtProportionallyTwoDaysAndTwoYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyTwoDaysAndThreeYearsAndSixMonths
	 */
	public String getTxtProportionallyTwoDaysAndThreeYearsAndSixMonths() {
		return txtProportionallyTwoDaysAndThreeYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyTwoDaysAndThreeYearsAndSixMonths セットする txtProportionallyTwoDaysAndThreeYearsAndSixMonths
	 */
	public void setTxtProportionallyTwoDaysAndThreeYearsAndSixMonths(
			String txtProportionallyTwoDaysAndThreeYearsAndSixMonths) {
		this.txtProportionallyTwoDaysAndThreeYearsAndSixMonths = txtProportionallyTwoDaysAndThreeYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyTwoDaysAndFourYearsAndSixMonths
	 */
	public String getTxtProportionallyTwoDaysAndFourYearsAndSixMonths() {
		return txtProportionallyTwoDaysAndFourYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyTwoDaysAndFourYearsAndSixMonths セットする txtProportionallyTwoDaysAndFourYearsAndSixMonths
	 */
	public void setTxtProportionallyTwoDaysAndFourYearsAndSixMonths(
			String txtProportionallyTwoDaysAndFourYearsAndSixMonths) {
		this.txtProportionallyTwoDaysAndFourYearsAndSixMonths = txtProportionallyTwoDaysAndFourYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyTwoDaysAndFiveYearsAndSixMonths
	 */
	public String getTxtProportionallyTwoDaysAndFiveYearsAndSixMonths() {
		return txtProportionallyTwoDaysAndFiveYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyTwoDaysAndFiveYearsAndSixMonths セットする txtProportionallyTwoDaysAndFiveYearsAndSixMonths
	 */
	public void setTxtProportionallyTwoDaysAndFiveYearsAndSixMonths(
			String txtProportionallyTwoDaysAndFiveYearsAndSixMonths) {
		this.txtProportionallyTwoDaysAndFiveYearsAndSixMonths = txtProportionallyTwoDaysAndFiveYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore
	 */
	public String getTxtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore() {
		return txtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore;
	}
	
	/**
	 * @param txtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore セットする txtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore
	 */
	public void setTxtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore(
			String txtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore) {
		this.txtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore = txtProportionallyTwoDaysAndSixYearsAndSixMonthsOrMore;
	}
	
	/**
	 * @return txtProportionallyThreeDaysAndSixMonths
	 */
	public String getTxtProportionallyThreeDaysAndSixMonths() {
		return txtProportionallyThreeDaysAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyThreeDaysAndSixMonths セットする txtProportionallyThreeDaysAndSixMonths
	 */
	public void setTxtProportionallyThreeDaysAndSixMonths(String txtProportionallyThreeDaysAndSixMonths) {
		this.txtProportionallyThreeDaysAndSixMonths = txtProportionallyThreeDaysAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyThreeDaysAndOneYearAndSixMonths
	 */
	public String getTxtProportionallyThreeDaysAndOneYearAndSixMonths() {
		return txtProportionallyThreeDaysAndOneYearAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyThreeDaysAndOneYearAndSixMonths セットする txtProportionallyThreeDaysAndOneYearAndSixMonths
	 */
	public void setTxtProportionallyThreeDaysAndOneYearAndSixMonths(
			String txtProportionallyThreeDaysAndOneYearAndSixMonths) {
		this.txtProportionallyThreeDaysAndOneYearAndSixMonths = txtProportionallyThreeDaysAndOneYearAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyThreeDaysAndTwoYearsAndSixMonths
	 */
	public String getTxtProportionallyThreeDaysAndTwoYearsAndSixMonths() {
		return txtProportionallyThreeDaysAndTwoYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyThreeDaysAndTwoYearsAndSixMonths セットする txtProportionallyThreeDaysAndTwoYearsAndSixMonths
	 */
	public void setTxtProportionallyThreeDaysAndTwoYearsAndSixMonths(
			String txtProportionallyThreeDaysAndTwoYearsAndSixMonths) {
		this.txtProportionallyThreeDaysAndTwoYearsAndSixMonths = txtProportionallyThreeDaysAndTwoYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyThreeDaysAndThreeYearsAndSixMonths
	 */
	public String getTxtProportionallyThreeDaysAndThreeYearsAndSixMonths() {
		return txtProportionallyThreeDaysAndThreeYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyThreeDaysAndThreeYearsAndSixMonths セットする txtProportionallyThreeDaysAndThreeYearsAndSixMonths
	 */
	public void setTxtProportionallyThreeDaysAndThreeYearsAndSixMonths(
			String txtProportionallyThreeDaysAndThreeYearsAndSixMonths) {
		this.txtProportionallyThreeDaysAndThreeYearsAndSixMonths = txtProportionallyThreeDaysAndThreeYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyThreeDaysAndFourYearsAndSixMonths
	 */
	public String getTxtProportionallyThreeDaysAndFourYearsAndSixMonths() {
		return txtProportionallyThreeDaysAndFourYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyThreeDaysAndFourYearsAndSixMonths セットする txtProportionallyThreeDaysAndFourYearsAndSixMonths
	 */
	public void setTxtProportionallyThreeDaysAndFourYearsAndSixMonths(
			String txtProportionallyThreeDaysAndFourYearsAndSixMonths) {
		this.txtProportionallyThreeDaysAndFourYearsAndSixMonths = txtProportionallyThreeDaysAndFourYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyThreeDaysAndFiveYearsAndSixMonths
	 */
	public String getTxtProportionallyThreeDaysAndFiveYearsAndSixMonths() {
		return txtProportionallyThreeDaysAndFiveYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyThreeDaysAndFiveYearsAndSixMonths セットする txtProportionallyThreeDaysAndFiveYearsAndSixMonths
	 */
	public void setTxtProportionallyThreeDaysAndFiveYearsAndSixMonths(
			String txtProportionallyThreeDaysAndFiveYearsAndSixMonths) {
		this.txtProportionallyThreeDaysAndFiveYearsAndSixMonths = txtProportionallyThreeDaysAndFiveYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore
	 */
	public String getTxtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore() {
		return txtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore;
	}
	
	/**
	 * @param txtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore セットする txtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore
	 */
	public void setTxtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore(
			String txtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore) {
		this.txtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore = txtProportionallyThreeDaysAndSixYearsAndSixMonthsOrMore;
	}
	
	/**
	 * @return txtProportionallyFourDaysAndSixMonths
	 */
	public String getTxtProportionallyFourDaysAndSixMonths() {
		return txtProportionallyFourDaysAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFourDaysAndSixMonths セットする txtProportionallyFourDaysAndSixMonths
	 */
	public void setTxtProportionallyFourDaysAndSixMonths(String txtProportionallyFourDaysAndSixMonths) {
		this.txtProportionallyFourDaysAndSixMonths = txtProportionallyFourDaysAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFourDaysAndOneYearAndSixMonths
	 */
	public String getTxtProportionallyFourDaysAndOneYearAndSixMonths() {
		return txtProportionallyFourDaysAndOneYearAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFourDaysAndOneYearAndSixMonths セットする txtProportionallyFourDaysAndOneYearAndSixMonths
	 */
	public void setTxtProportionallyFourDaysAndOneYearAndSixMonths(
			String txtProportionallyFourDaysAndOneYearAndSixMonths) {
		this.txtProportionallyFourDaysAndOneYearAndSixMonths = txtProportionallyFourDaysAndOneYearAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFourDaysAndTwoYearsAndSixMonths
	 */
	public String getTxtProportionallyFourDaysAndTwoYearsAndSixMonths() {
		return txtProportionallyFourDaysAndTwoYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFourDaysAndTwoYearsAndSixMonths セットする txtProportionallyFourDaysAndTwoYearsAndSixMonths
	 */
	public void setTxtProportionallyFourDaysAndTwoYearsAndSixMonths(
			String txtProportionallyFourDaysAndTwoYearsAndSixMonths) {
		this.txtProportionallyFourDaysAndTwoYearsAndSixMonths = txtProportionallyFourDaysAndTwoYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFourDaysAndThreeYearsAndSixMonths
	 */
	public String getTxtProportionallyFourDaysAndThreeYearsAndSixMonths() {
		return txtProportionallyFourDaysAndThreeYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFourDaysAndThreeYearsAndSixMonths セットする txtProportionallyFourDaysAndThreeYearsAndSixMonths
	 */
	public void setTxtProportionallyFourDaysAndThreeYearsAndSixMonths(
			String txtProportionallyFourDaysAndThreeYearsAndSixMonths) {
		this.txtProportionallyFourDaysAndThreeYearsAndSixMonths = txtProportionallyFourDaysAndThreeYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFourDaysAndFourYearsAndSixMonths
	 */
	public String getTxtProportionallyFourDaysAndFourYearsAndSixMonths() {
		return txtProportionallyFourDaysAndFourYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFourDaysAndFourYearsAndSixMonths セットする txtProportionallyFourDaysAndFourYearsAndSixMonths
	 */
	public void setTxtProportionallyFourDaysAndFourYearsAndSixMonths(
			String txtProportionallyFourDaysAndFourYearsAndSixMonths) {
		this.txtProportionallyFourDaysAndFourYearsAndSixMonths = txtProportionallyFourDaysAndFourYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFourDaysAndFiveYearsAndSixMonths
	 */
	public String getTxtProportionallyFourDaysAndFiveYearsAndSixMonths() {
		return txtProportionallyFourDaysAndFiveYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFourDaysAndFiveYearsAndSixMonths セットする txtProportionallyFourDaysAndFiveYearsAndSixMonths
	 */
	public void setTxtProportionallyFourDaysAndFiveYearsAndSixMonths(
			String txtProportionallyFourDaysAndFiveYearsAndSixMonths) {
		this.txtProportionallyFourDaysAndFiveYearsAndSixMonths = txtProportionallyFourDaysAndFiveYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore
	 */
	public String getTxtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore() {
		return txtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore;
	}
	
	/**
	 * @param txtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore セットする txtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore
	 */
	public void setTxtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore(
			String txtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore) {
		this.txtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore = txtProportionallyFourDaysAndSixYearsAndSixMonthsOrMore;
	}
	
	/**
	 * @return txtProportionallyFiveDaysOrMoreAndSixMonths
	 */
	public String getTxtProportionallyFiveDaysOrMoreAndSixMonths() {
		return txtProportionallyFiveDaysOrMoreAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFiveDaysOrMoreAndSixMonths セットする txtProportionallyFiveDaysOrMoreAndSixMonths
	 */
	public void setTxtProportionallyFiveDaysOrMoreAndSixMonths(String txtProportionallyFiveDaysOrMoreAndSixMonths) {
		this.txtProportionallyFiveDaysOrMoreAndSixMonths = txtProportionallyFiveDaysOrMoreAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths
	 */
	public String getTxtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths() {
		return txtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths セットする txtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths
	 */
	public void setTxtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths(
			String txtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths) {
		this.txtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths = txtProportionallyFiveDaysOrMoreAndOneYearAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths
	 */
	public String getTxtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths() {
		return txtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths セットする txtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths
	 */
	public void setTxtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths(
			String txtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths) {
		this.txtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths = txtProportionallyFiveDaysOrMoreAndTwoYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths
	 */
	public String getTxtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths() {
		return txtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths セットする txtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths
	 */
	public void setTxtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths(
			String txtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths) {
		this.txtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths = txtProportionallyFiveDaysOrMoreAndThreeYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths
	 */
	public String getTxtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths() {
		return txtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths セットする txtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths
	 */
	public void setTxtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths(
			String txtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths) {
		this.txtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths = txtProportionallyFiveDaysOrMoreAndFourYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths
	 */
	public String getTxtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths() {
		return txtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths;
	}
	
	/**
	 * @param txtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths セットする txtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths
	 */
	public void setTxtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths(
			String txtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths) {
		this.txtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths = txtProportionallyFiveDaysOrMoreAndFiveYearsAndSixMonths;
	}
	
	/**
	 * @return txtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore
	 */
	public String getTxtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore() {
		return txtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore;
	}
	
	/**
	 * @param txtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore セットする txtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore
	 */
	public void setTxtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore(
			String txtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore) {
		this.txtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore = txtProportionallyFiveDaysOrMoreAndSixYearsAndSixMonthsOrMore;
	}
	
	/**
	 * @return txtGivingTimingJanuary
	 */
	public String getTxtGivingTimingJanuary() {
		return txtGivingTimingJanuary;
	}
	
	/**
	 * @param txtGivingTimingJanuary セットする txtGivingTimingJanuary
	 */
	public void setTxtGivingTimingJanuary(String txtGivingTimingJanuary) {
		this.txtGivingTimingJanuary = txtGivingTimingJanuary;
	}
	
	/**
	 * @return txtGivingTimingFebruary
	 */
	public String getTxtGivingTimingFebruary() {
		return txtGivingTimingFebruary;
	}
	
	/**
	 * @param txtGivingTimingFebruary セットする txtGivingTimingFebruary
	 */
	public void setTxtGivingTimingFebruary(String txtGivingTimingFebruary) {
		this.txtGivingTimingFebruary = txtGivingTimingFebruary;
	}
	
	/**
	 * @return txtGivingTimingMarch
	 */
	public String getTxtGivingTimingMarch() {
		return txtGivingTimingMarch;
	}
	
	/**
	 * @param txtGivingTimingMarch セットする txtGivingTimingMarch
	 */
	public void setTxtGivingTimingMarch(String txtGivingTimingMarch) {
		this.txtGivingTimingMarch = txtGivingTimingMarch;
	}
	
	/**
	 * @return txtGivingTimingApril
	 */
	public String getTxtGivingTimingApril() {
		return txtGivingTimingApril;
	}
	
	/**
	 * @param txtGivingTimingApril セットする txtGivingTimingApril
	 */
	public void setTxtGivingTimingApril(String txtGivingTimingApril) {
		this.txtGivingTimingApril = txtGivingTimingApril;
	}
	
	/**
	 * @return txtGivingTimingMay
	 */
	public String getTxtGivingTimingMay() {
		return txtGivingTimingMay;
	}
	
	/**
	 * @param txtGivingTimingMay セットする txtGivingTimingMay
	 */
	public void setTxtGivingTimingMay(String txtGivingTimingMay) {
		this.txtGivingTimingMay = txtGivingTimingMay;
	}
	
	/**
	 * @return txtGivingTimingJune
	 */
	public String getTxtGivingTimingJune() {
		return txtGivingTimingJune;
	}
	
	/**
	 * @param txtGivingTimingJune セットする txtGivingTimingJune
	 */
	public void setTxtGivingTimingJune(String txtGivingTimingJune) {
		this.txtGivingTimingJune = txtGivingTimingJune;
	}
	
	/**
	 * @return txtGivingTimingJuly
	 */
	public String getTxtGivingTimingJuly() {
		return txtGivingTimingJuly;
	}
	
	/**
	 * @param txtGivingTimingJuly セットする txtGivingTimingJuly
	 */
	public void setTxtGivingTimingJuly(String txtGivingTimingJuly) {
		this.txtGivingTimingJuly = txtGivingTimingJuly;
	}
	
	/**
	 * @return txtGivingTimingAugust
	 */
	public String getTxtGivingTimingAugust() {
		return txtGivingTimingAugust;
	}
	
	/**
	 * @param txtGivingTimingAugust セットする txtGivingTimingAugust
	 */
	public void setTxtGivingTimingAugust(String txtGivingTimingAugust) {
		this.txtGivingTimingAugust = txtGivingTimingAugust;
	}
	
	/**
	 * @return txtGivingTimingSeptember
	 */
	public String getTxtGivingTimingSeptember() {
		return txtGivingTimingSeptember;
	}
	
	/**
	 * @param txtGivingTimingSeptember セットする txtGivingTimingSeptember
	 */
	public void setTxtGivingTimingSeptember(String txtGivingTimingSeptember) {
		this.txtGivingTimingSeptember = txtGivingTimingSeptember;
	}
	
	/**
	 * @return txtGivingTimingOctober
	 */
	public String getTxtGivingTimingOctober() {
		return txtGivingTimingOctober;
	}
	
	/**
	 * @param txtGivingTimingOctober セットする txtGivingTimingOctober
	 */
	public void setTxtGivingTimingOctober(String txtGivingTimingOctober) {
		this.txtGivingTimingOctober = txtGivingTimingOctober;
	}
	
	/**
	 * @return txtGivingTimingNovember
	 */
	public String getTxtGivingTimingNovember() {
		return txtGivingTimingNovember;
	}
	
	/**
	 * @param txtGivingTimingNovember セットする txtGivingTimingNovember
	 */
	public void setTxtGivingTimingNovember(String txtGivingTimingNovember) {
		this.txtGivingTimingNovember = txtGivingTimingNovember;
	}
	
	/**
	 * @return txtGivingTimingDecember
	 */
	public String getTxtGivingTimingDecember() {
		return txtGivingTimingDecember;
	}
	
	/**
	 * @param txtGivingTimingDecember セットする txtGivingTimingDecember
	 */
	public void setTxtGivingTimingDecember(String txtGivingTimingDecember) {
		this.txtGivingTimingDecember = txtGivingTimingDecember;
	}
	
	/**
	 * @return txtGivingAmountJanuary
	 */
	public String getTxtGivingAmountJanuary() {
		return txtGivingAmountJanuary;
	}
	
	/**
	 * @param txtGivingAmountJanuary セットする txtGivingAmountJanuary
	 */
	public void setTxtGivingAmountJanuary(String txtGivingAmountJanuary) {
		this.txtGivingAmountJanuary = txtGivingAmountJanuary;
	}
	
	/**
	 * @return txtGivingAmountFebruary
	 */
	public String getTxtGivingAmountFebruary() {
		return txtGivingAmountFebruary;
	}
	
	/**
	 * @param txtGivingAmountFebruary セットする txtGivingAmountFebruary
	 */
	public void setTxtGivingAmountFebruary(String txtGivingAmountFebruary) {
		this.txtGivingAmountFebruary = txtGivingAmountFebruary;
	}
	
	/**
	 * @return txtGivingAmountMarch
	 */
	public String getTxtGivingAmountMarch() {
		return txtGivingAmountMarch;
	}
	
	/**
	 * @param txtGivingAmountMarch セットする txtGivingAmountMarch
	 */
	public void setTxtGivingAmountMarch(String txtGivingAmountMarch) {
		this.txtGivingAmountMarch = txtGivingAmountMarch;
	}
	
	/**
	 * @return txtGivingAmountApril
	 */
	public String getTxtGivingAmountApril() {
		return txtGivingAmountApril;
	}
	
	/**
	 * @param txtGivingAmountApril セットする txtGivingAmountApril
	 */
	public void setTxtGivingAmountApril(String txtGivingAmountApril) {
		this.txtGivingAmountApril = txtGivingAmountApril;
	}
	
	/**
	 * @return txtGivingAmountMay
	 */
	public String getTxtGivingAmountMay() {
		return txtGivingAmountMay;
	}
	
	/**
	 * @param txtGivingAmountMay セットする txtGivingAmountMay
	 */
	public void setTxtGivingAmountMay(String txtGivingAmountMay) {
		this.txtGivingAmountMay = txtGivingAmountMay;
	}
	
	/**
	 * @return txtGivingAmountJune
	 */
	public String getTxtGivingAmountJune() {
		return txtGivingAmountJune;
	}
	
	/**
	 * @param txtGivingAmountJune セットする txtGivingAmountJune
	 */
	public void setTxtGivingAmountJune(String txtGivingAmountJune) {
		this.txtGivingAmountJune = txtGivingAmountJune;
	}
	
	/**
	 * @return txtGivingAmountJuly
	 */
	public String getTxtGivingAmountJuly() {
		return txtGivingAmountJuly;
	}
	
	/**
	 * @param txtGivingAmountJuly セットする txtGivingAmountJuly
	 */
	public void setTxtGivingAmountJuly(String txtGivingAmountJuly) {
		this.txtGivingAmountJuly = txtGivingAmountJuly;
	}
	
	/**
	 * @return txtGivingAmountAugust
	 */
	public String getTxtGivingAmountAugust() {
		return txtGivingAmountAugust;
	}
	
	/**
	 * @param txtGivingAmountAugust セットする txtGivingAmountAugust
	 */
	public void setTxtGivingAmountAugust(String txtGivingAmountAugust) {
		this.txtGivingAmountAugust = txtGivingAmountAugust;
	}
	
	/**
	 * @return txtGivingAmountSeptember
	 */
	public String getTxtGivingAmountSeptember() {
		return txtGivingAmountSeptember;
	}
	
	/**
	 * @param txtGivingAmountSeptember セットする txtGivingAmountSeptember
	 */
	public void setTxtGivingAmountSeptember(String txtGivingAmountSeptember) {
		this.txtGivingAmountSeptember = txtGivingAmountSeptember;
	}
	
	/**
	 * @return txtGivingAmountOctober
	 */
	public String getTxtGivingAmountOctober() {
		return txtGivingAmountOctober;
	}
	
	/**
	 * @param txtGivingAmountOctober セットする txtGivingAmountOctober
	 */
	public void setTxtGivingAmountOctober(String txtGivingAmountOctober) {
		this.txtGivingAmountOctober = txtGivingAmountOctober;
	}
	
	/**
	 * @return txtGivingAmountNovember
	 */
	public String getTxtGivingAmountNovember() {
		return txtGivingAmountNovember;
	}
	
	/**
	 * @param txtGivingAmountNovember セットする txtGivingAmountNovember
	 */
	public void setTxtGivingAmountNovember(String txtGivingAmountNovember) {
		this.txtGivingAmountNovember = txtGivingAmountNovember;
	}
	
	/**
	 * @return txtGivingAmountDecember
	 */
	public String getTxtGivingAmountDecember() {
		return txtGivingAmountDecember;
	}
	
	/**
	 * @param txtGivingAmountDecember セットする txtGivingAmountDecember
	 */
	public void setTxtGivingAmountDecember(String txtGivingAmountDecember) {
		this.txtGivingAmountDecember = txtGivingAmountDecember;
	}
	
	/**
	 * @return txtGivingLimitJanuary
	 */
	public String getTxtGivingLimitJanuary() {
		return txtGivingLimitJanuary;
	}
	
	/**
	 * @param txtGivingLimitJanuary セットする txtGivingLimitJanuary
	 */
	public void setTxtGivingLimitJanuary(String txtGivingLimitJanuary) {
		this.txtGivingLimitJanuary = txtGivingLimitJanuary;
	}
	
	/**
	 * @return txtGivingLimitFebruary
	 */
	public String getTxtGivingLimitFebruary() {
		return txtGivingLimitFebruary;
	}
	
	/**
	 * @param txtGivingLimitFebruary セットする txtGivingLimitFebruary
	 */
	public void setTxtGivingLimitFebruary(String txtGivingLimitFebruary) {
		this.txtGivingLimitFebruary = txtGivingLimitFebruary;
	}
	
	/**
	 * @return txtGivingLimitMarch
	 */
	public String getTxtGivingLimitMarch() {
		return txtGivingLimitMarch;
	}
	
	/**
	 * @param txtGivingLimitMarch セットする txtGivingLimitMarch
	 */
	public void setTxtGivingLimitMarch(String txtGivingLimitMarch) {
		this.txtGivingLimitMarch = txtGivingLimitMarch;
	}
	
	/**
	 * @return txtGivingLimitApril
	 */
	public String getTxtGivingLimitApril() {
		return txtGivingLimitApril;
	}
	
	/**
	 * @param txtGivingLimitApril セットする txtGivingLimitApril
	 */
	public void setTxtGivingLimitApril(String txtGivingLimitApril) {
		this.txtGivingLimitApril = txtGivingLimitApril;
	}
	
	/**
	 * @return txtGivingLimitMay
	 */
	public String getTxtGivingLimitMay() {
		return txtGivingLimitMay;
	}
	
	/**
	 * @param txtGivingLimitMay セットする txtGivingLimitMay
	 */
	public void setTxtGivingLimitMay(String txtGivingLimitMay) {
		this.txtGivingLimitMay = txtGivingLimitMay;
	}
	
	/**
	 * @return txtGivingLimitJune
	 */
	public String getTxtGivingLimitJune() {
		return txtGivingLimitJune;
	}
	
	/**
	 * @param txtGivingLimitJune セットする txtGivingLimitJune
	 */
	public void setTxtGivingLimitJune(String txtGivingLimitJune) {
		this.txtGivingLimitJune = txtGivingLimitJune;
	}
	
	/**
	 * @return txtGivingLimitJuly
	 */
	public String getTxtGivingLimitJuly() {
		return txtGivingLimitJuly;
	}
	
	/**
	 * @param txtGivingLimitJuly セットする txtGivingLimitJuly
	 */
	public void setTxtGivingLimitJuly(String txtGivingLimitJuly) {
		this.txtGivingLimitJuly = txtGivingLimitJuly;
	}
	
	/**
	 * @return txtGivingLimitAugust
	 */
	public String getTxtGivingLimitAugust() {
		return txtGivingLimitAugust;
	}
	
	/**
	 * @param txtGivingLimitAugust セットする txtGivingLimitAugust
	 */
	public void setTxtGivingLimitAugust(String txtGivingLimitAugust) {
		this.txtGivingLimitAugust = txtGivingLimitAugust;
	}
	
	/**
	 * @return txtGivingLimitSeptember
	 */
	public String getTxtGivingLimitSeptember() {
		return txtGivingLimitSeptember;
	}
	
	/**
	 * @param txtGivingLimitSeptember セットする txtGivingLimitSeptember
	 */
	public void setTxtGivingLimitSeptember(String txtGivingLimitSeptember) {
		this.txtGivingLimitSeptember = txtGivingLimitSeptember;
	}
	
	/**
	 * @return txtGivingLimitOctober
	 */
	public String getTxtGivingLimitOctober() {
		return txtGivingLimitOctober;
	}
	
	/**
	 * @param txtGivingLimitOctober セットする txtGivingLimitOctober
	 */
	public void setTxtGivingLimitOctober(String txtGivingLimitOctober) {
		this.txtGivingLimitOctober = txtGivingLimitOctober;
	}
	
	/**
	 * @return txtGivingLimitNovember
	 */
	public String getTxtGivingLimitNovember() {
		return txtGivingLimitNovember;
	}
	
	/**
	 * @param txtGivingLimitNovember セットする txtGivingLimitNovember
	 */
	public void setTxtGivingLimitNovember(String txtGivingLimitNovember) {
		this.txtGivingLimitNovember = txtGivingLimitNovember;
	}
	
	/**
	 * @return txtGivingLimitDecember
	 */
	public String getTxtGivingLimitDecember() {
		return txtGivingLimitDecember;
	}
	
	/**
	 * @param txtGivingLimitDecember セットする txtGivingLimitDecember
	 */
	public void setTxtGivingLimitDecember(String txtGivingLimitDecember) {
		this.txtGivingLimitDecember = txtGivingLimitDecember;
	}
	
	/**
	 * @return txtPointDateMonth
	 */
	public String getTxtPointDateMonth() {
		return txtPointDateMonth;
	}
	
	/**
	 * @param txtPointDateMonth セットする txtPointDateMonth
	 */
	public void setTxtPointDateMonth(String txtPointDateMonth) {
		this.txtPointDateMonth = txtPointDateMonth;
	}
	
	/**
	 * @return txtPointDateDay
	 */
	public String getTxtPointDateDay() {
		return txtPointDateDay;
	}
	
	/**
	 * @param txtPointDateDay セットする txtPointDateDay
	 */
	public void setTxtPointDateDay(String txtPointDateDay) {
		this.txtPointDateDay = txtPointDateDay;
	}
	
	/**
	 * @return txtTimesPointDate1
	 */
	public String getTxtTimesPointDate1() {
		return txtTimesPointDate1;
	}
	
	/**
	 * @param txtTimesPointDate1 セットする txtTimesPointDate1
	 */
	public void setTxtTimesPointDate1(String txtTimesPointDate1) {
		this.txtTimesPointDate1 = txtTimesPointDate1;
	}
	
	/**
	 * @return txtTimesPointDate2
	 */
	public String getTxtTimesPointDate2() {
		return txtTimesPointDate2;
	}
	
	/**
	 * @param txtTimesPointDate2 セットする txtTimesPointDate2
	 */
	public void setTxtTimesPointDate2(String txtTimesPointDate2) {
		this.txtTimesPointDate2 = txtTimesPointDate2;
	}
	
	/**
	 * @return txtTimesPointDate3
	 */
	public String getTxtTimesPointDate3() {
		return txtTimesPointDate3;
	}
	
	/**
	 * @param txtTimesPointDate3 セットする txtTimesPointDate3
	 */
	public void setTxtTimesPointDate3(String txtTimesPointDate3) {
		this.txtTimesPointDate3 = txtTimesPointDate3;
	}
	
	/**
	 * @return txtTimesPointDate4
	 */
	public String getTxtTimesPointDate4() {
		return txtTimesPointDate4;
	}
	
	/**
	 * @param txtTimesPointDate4 セットする txtTimesPointDate4
	 */
	public void setTxtTimesPointDate4(String txtTimesPointDate4) {
		this.txtTimesPointDate4 = txtTimesPointDate4;
	}
	
	/**
	 * @return txtTimesPointDate5
	 */
	public String getTxtTimesPointDate5() {
		return txtTimesPointDate5;
	}
	
	/**
	 * @param txtTimesPointDate5 セットする txtTimesPointDate5
	 */
	public void setTxtTimesPointDate5(String txtTimesPointDate5) {
		this.txtTimesPointDate5 = txtTimesPointDate5;
	}
	
	/**
	 * @return txtTimesPointDate6
	 */
	public String getTxtTimesPointDate6() {
		return txtTimesPointDate6;
	}
	
	/**
	 * @param txtTimesPointDate6 セットする txtTimesPointDate6
	 */
	public void setTxtTimesPointDate6(String txtTimesPointDate6) {
		this.txtTimesPointDate6 = txtTimesPointDate6;
	}
	
	/**
	 * @return txtTimesPointDate7
	 */
	public String getTxtTimesPointDate7() {
		return txtTimesPointDate7;
	}
	
	/**
	 * @param txtTimesPointDate7 セットする txtTimesPointDate7
	 */
	public void setTxtTimesPointDate7(String txtTimesPointDate7) {
		this.txtTimesPointDate7 = txtTimesPointDate7;
	}
	
	/**
	 * @return txtTimesPointDate8
	 */
	public String getTxtTimesPointDate8() {
		return txtTimesPointDate8;
	}
	
	/**
	 * @param txtTimesPointDate8 セットする txtTimesPointDate8
	 */
	public void setTxtTimesPointDate8(String txtTimesPointDate8) {
		this.txtTimesPointDate8 = txtTimesPointDate8;
	}
	
	/**
	 * @return txtTimesPointDate9
	 */
	public String getTxtTimesPointDate9() {
		return txtTimesPointDate9;
	}
	
	/**
	 * @param txtTimesPointDate9 セットする txtTimesPointDate9
	 */
	public void setTxtTimesPointDate9(String txtTimesPointDate9) {
		this.txtTimesPointDate9 = txtTimesPointDate9;
	}
	
	/**
	 * @return txtTimesPointDate10
	 */
	public String getTxtTimesPointDate10() {
		return txtTimesPointDate10;
	}
	
	/**
	 * @param txtTimesPointDate10 セットする txtTimesPointDate10
	 */
	public void setTxtTimesPointDate10(String txtTimesPointDate10) {
		this.txtTimesPointDate10 = txtTimesPointDate10;
	}
	
	/**
	 * @return txtTimesPointDate11
	 */
	public String getTxtTimesPointDate11() {
		return txtTimesPointDate11;
	}
	
	/**
	 * @param txtTimesPointDate11 セットする txtTimesPointDate11
	 */
	public void setTxtTimesPointDate11(String txtTimesPointDate11) {
		this.txtTimesPointDate11 = txtTimesPointDate11;
	}
	
	/**
	 * @return txtTimesPointDate12
	 */
	public String getTxtTimesPointDate12() {
		return txtTimesPointDate12;
	}
	
	/**
	 * @param txtTimesPointDate12 セットする txtTimesPointDate12
	 */
	public void setTxtTimesPointDate12(String txtTimesPointDate12) {
		this.txtTimesPointDate12 = txtTimesPointDate12;
	}
	
	/**
	 * @return txtPointDateAmount1
	 */
	public String getTxtPointDateAmount1() {
		return txtPointDateAmount1;
	}
	
	/**
	 * @param txtPointDateAmount1 セットする txtPointDateAmount1
	 */
	public void setTxtPointDateAmount1(String txtPointDateAmount1) {
		this.txtPointDateAmount1 = txtPointDateAmount1;
	}
	
	/**
	 * @return txtPointDateAmount2
	 */
	public String getTxtPointDateAmount2() {
		return txtPointDateAmount2;
	}
	
	/**
	 * @param txtPointDateAmount2 セットする txtPointDateAmount2
	 */
	public void setTxtPointDateAmount2(String txtPointDateAmount2) {
		this.txtPointDateAmount2 = txtPointDateAmount2;
	}
	
	/**
	 * @return txtPointDateAmount3
	 */
	public String getTxtPointDateAmount3() {
		return txtPointDateAmount3;
	}
	
	/**
	 * @param txtPointDateAmount3 セットする txtPointDateAmount3
	 */
	public void setTxtPointDateAmount3(String txtPointDateAmount3) {
		this.txtPointDateAmount3 = txtPointDateAmount3;
	}
	
	/**
	 * @return txtPointDateAmount4
	 */
	public String getTxtPointDateAmount4() {
		return txtPointDateAmount4;
	}
	
	/**
	 * @param txtPointDateAmount4 セットする txtPointDateAmount4
	 */
	public void setTxtPointDateAmount4(String txtPointDateAmount4) {
		this.txtPointDateAmount4 = txtPointDateAmount4;
	}
	
	/**
	 * @return txtPointDateAmount5
	 */
	public String getTxtPointDateAmount5() {
		return txtPointDateAmount5;
	}
	
	/**
	 * @param txtPointDateAmount5 セットする txtPointDateAmount5
	 */
	public void setTxtPointDateAmount5(String txtPointDateAmount5) {
		this.txtPointDateAmount5 = txtPointDateAmount5;
	}
	
	/**
	 * @return txtPointDateAmount6
	 */
	public String getTxtPointDateAmount6() {
		return txtPointDateAmount6;
	}
	
	/**
	 * @param txtPointDateAmount6 セットする txtPointDateAmount6
	 */
	public void setTxtPointDateAmount6(String txtPointDateAmount6) {
		this.txtPointDateAmount6 = txtPointDateAmount6;
	}
	
	/**
	 * @return txtPointDateAmount7
	 */
	public String getTxtPointDateAmount7() {
		return txtPointDateAmount7;
	}
	
	/**
	 * @param txtPointDateAmount7 セットする txtPointDateAmount7
	 */
	public void setTxtPointDateAmount7(String txtPointDateAmount7) {
		this.txtPointDateAmount7 = txtPointDateAmount7;
	}
	
	/**
	 * @return txtPointDateAmount8
	 */
	public String getTxtPointDateAmount8() {
		return txtPointDateAmount8;
	}
	
	/**
	 * @param txtPointDateAmount8 セットする txtPointDateAmount8
	 */
	public void setTxtPointDateAmount8(String txtPointDateAmount8) {
		this.txtPointDateAmount8 = txtPointDateAmount8;
	}
	
	/**
	 * @return txtPointDateAmount9
	 */
	public String getTxtPointDateAmount9() {
		return txtPointDateAmount9;
	}
	
	/**
	 * @param txtPointDateAmount9 セットする txtPointDateAmount9
	 */
	public void setTxtPointDateAmount9(String txtPointDateAmount9) {
		this.txtPointDateAmount9 = txtPointDateAmount9;
	}
	
	/**
	 * @return txtPointDateAmount10
	 */
	public String getTxtPointDateAmount10() {
		return txtPointDateAmount10;
	}
	
	/**
	 * @param txtPointDateAmount10 セットする txtPointDateAmount10
	 */
	public void setTxtPointDateAmount10(String txtPointDateAmount10) {
		this.txtPointDateAmount10 = txtPointDateAmount10;
	}
	
	/**
	 * @return txtPointDateAmount11
	 */
	public String getTxtPointDateAmount11() {
		return txtPointDateAmount11;
	}
	
	/**
	 * @param txtPointDateAmount11 セットする txtPointDateAmount11
	 */
	public void setTxtPointDateAmount11(String txtPointDateAmount11) {
		this.txtPointDateAmount11 = txtPointDateAmount11;
	}
	
	/**
	 * @return txtPointDateAmount12
	 */
	public String getTxtPointDateAmount12() {
		return txtPointDateAmount12;
	}
	
	/**
	 * @param txtPointDateAmount12 セットする txtPointDateAmount12
	 */
	public void setTxtPointDateAmount12(String txtPointDateAmount12) {
		this.txtPointDateAmount12 = txtPointDateAmount12;
	}
	
	/**
	 * @return txtGeneralPointAmount
	 */
	public String getTxtGeneralPointAmount() {
		return txtGeneralPointAmount;
	}
	
	/**
	 * @param txtGeneralPointAmount セットする txtGeneralPointAmount
	 */
	public void setTxtGeneralPointAmount(String txtGeneralPointAmount) {
		this.txtGeneralPointAmount = txtGeneralPointAmount;
	}
	
	/**
	 * @return txtWorkYear1
	 */
	public String getTxtWorkYear1() {
		return txtWorkYear1;
	}
	
	/**
	 * @param txtWorkYear1 セットする txtWorkYear1
	 */
	public void setTxtWorkYear1(String txtWorkYear1) {
		this.txtWorkYear1 = txtWorkYear1;
	}
	
	/**
	 * @return txtWorkYear2
	 */
	public String getTxtWorkYear2() {
		return txtWorkYear2;
	}
	
	/**
	 * @param txtWorkYear2 セットする txtWorkYear2
	 */
	public void setTxtWorkYear2(String txtWorkYear2) {
		this.txtWorkYear2 = txtWorkYear2;
	}
	
	/**
	 * @return txtWorkYear3
	 */
	public String getTxtWorkYear3() {
		return txtWorkYear3;
	}
	
	/**
	 * @param txtWorkYear3 セットする txtWorkYear3
	 */
	public void setTxtWorkYear3(String txtWorkYear3) {
		this.txtWorkYear3 = txtWorkYear3;
	}
	
	/**
	 * @return txtWorkYear4
	 */
	public String getTxtWorkYear4() {
		return txtWorkYear4;
	}
	
	/**
	 * @param txtWorkYear4 セットする txtWorkYear4
	 */
	public void setTxtWorkYear4(String txtWorkYear4) {
		this.txtWorkYear4 = txtWorkYear4;
	}
	
	/**
	 * @return txtWorkYear5
	 */
	public String getTxtWorkYear5() {
		return txtWorkYear5;
	}
	
	/**
	 * @param txtWorkYear5 セットする txtWorkYear5
	 */
	public void setTxtWorkYear5(String txtWorkYear5) {
		this.txtWorkYear5 = txtWorkYear5;
	}
	
	/**
	 * @return txtWorkYear6
	 */
	public String getTxtWorkYear6() {
		return txtWorkYear6;
	}
	
	/**
	 * @param txtWorkYear6 セットする txtWorkYear6
	 */
	public void setTxtWorkYear6(String txtWorkYear6) {
		this.txtWorkYear6 = txtWorkYear6;
	}
	
	/**
	 * @return txtWorkYear7
	 */
	public String getTxtWorkYear7() {
		return txtWorkYear7;
	}
	
	/**
	 * @param txtWorkYear7 セットする txtWorkYear7
	 */
	public void setTxtWorkYear7(String txtWorkYear7) {
		this.txtWorkYear7 = txtWorkYear7;
	}
	
	/**
	 * @return txtWorkYear8
	 */
	public String getTxtWorkYear8() {
		return txtWorkYear8;
	}
	
	/**
	 * @param txtWorkYear8 セットする txtWorkYear8
	 */
	public void setTxtWorkYear8(String txtWorkYear8) {
		this.txtWorkYear8 = txtWorkYear8;
	}
	
	/**
	 * @return txtWorkYear9
	 */
	public String getTxtWorkYear9() {
		return txtWorkYear9;
	}
	
	/**
	 * @param txtWorkYear9 セットする txtWorkYear9
	 */
	public void setTxtWorkYear9(String txtWorkYear9) {
		this.txtWorkYear9 = txtWorkYear9;
	}
	
	/**
	 * @return txtWorkYear10
	 */
	public String getTxtWorkYear10() {
		return txtWorkYear10;
	}
	
	/**
	 * @param txtWorkYear10 セットする txtWorkYear10
	 */
	public void setTxtWorkYear10(String txtWorkYear10) {
		this.txtWorkYear10 = txtWorkYear10;
	}
	
	/**
	 * @return txtWorkYear11
	 */
	public String getTxtWorkYear11() {
		return txtWorkYear11;
	}
	
	/**
	 * @param txtWorkYear11 セットする txtWorkYear11
	 */
	public void setTxtWorkYear11(String txtWorkYear11) {
		this.txtWorkYear11 = txtWorkYear11;
	}
	
	/**
	 * @return txtWorkYear12
	 */
	public String getTxtWorkYear12() {
		return txtWorkYear12;
	}
	
	/**
	 * @param txtWorkYear12 セットする txtWorkYear12
	 */
	public void setTxtWorkYear12(String txtWorkYear12) {
		this.txtWorkYear12 = txtWorkYear12;
	}
	
	/**
	 * @return txtWorkMonth1
	 */
	public String getTxtWorkMonth1() {
		return txtWorkMonth1;
	}
	
	/**
	 * @param txtWorkMonth1 セットする txtWorkMonth1
	 */
	public void setTxtWorkMonth1(String txtWorkMonth1) {
		this.txtWorkMonth1 = txtWorkMonth1;
	}
	
	/**
	 * @return txtWorkMonth2
	 */
	public String getTxtWorkMonth2() {
		return txtWorkMonth2;
	}
	
	/**
	 * @param txtWorkMonth2 セットする txtWorkMonth2
	 */
	public void setTxtWorkMonth2(String txtWorkMonth2) {
		this.txtWorkMonth2 = txtWorkMonth2;
	}
	
	/**
	 * @return txtWorkMonth3
	 */
	public String getTxtWorkMonth3() {
		return txtWorkMonth3;
	}
	
	/**
	 * @param txtWorkMonth3 セットする txtWorkMonth3
	 */
	public void setTxtWorkMonth3(String txtWorkMonth3) {
		this.txtWorkMonth3 = txtWorkMonth3;
	}
	
	/**
	 * @return txtWorkMonth4
	 */
	public String getTxtWorkMonth4() {
		return txtWorkMonth4;
	}
	
	/**
	 * @param txtWorkMonth4 セットする txtWorkMonth4
	 */
	public void setTxtWorkMonth4(String txtWorkMonth4) {
		this.txtWorkMonth4 = txtWorkMonth4;
	}
	
	/**
	 * @return txtWorkMonth5
	 */
	public String getTxtWorkMonth5() {
		return txtWorkMonth5;
	}
	
	/**
	 * @param txtWorkMonth5 セットする txtWorkMonth5
	 */
	public void setTxtWorkMonth5(String txtWorkMonth5) {
		this.txtWorkMonth5 = txtWorkMonth5;
	}
	
	/**
	 * @return txtWorkMonth6
	 */
	public String getTxtWorkMonth6() {
		return txtWorkMonth6;
	}
	
	/**
	 * @param txtWorkMonth6 セットする txtWorkMonth6
	 */
	public void setTxtWorkMonth6(String txtWorkMonth6) {
		this.txtWorkMonth6 = txtWorkMonth6;
	}
	
	/**
	 * @return txtWorkMonth7
	 */
	public String getTxtWorkMonth7() {
		return txtWorkMonth7;
	}
	
	/**
	 * @param txtWorkMonth7 セットする txtWorkMonth7
	 */
	public void setTxtWorkMonth7(String txtWorkMonth7) {
		this.txtWorkMonth7 = txtWorkMonth7;
	}
	
	/**
	 * @return txtWorkMonth8
	 */
	public String getTxtWorkMonth8() {
		return txtWorkMonth8;
	}
	
	/**
	 * @param txtWorkMonth8 セットする txtWorkMonth8
	 */
	public void setTxtWorkMonth8(String txtWorkMonth8) {
		this.txtWorkMonth8 = txtWorkMonth8;
	}
	
	/**
	 * @return txtWorkMonth9
	 */
	public String getTxtWorkMonth9() {
		return txtWorkMonth9;
	}
	
	/**
	 * @param txtWorkMonth9 セットする txtWorkMonth9
	 */
	public void setTxtWorkMonth9(String txtWorkMonth9) {
		this.txtWorkMonth9 = txtWorkMonth9;
	}
	
	/**
	 * @return txtWorkMonth10
	 */
	public String getTxtWorkMonth10() {
		return txtWorkMonth10;
	}
	
	/**
	 * @param txtWorkMonth10 セットする txtWorkMonth10
	 */
	public void setTxtWorkMonth10(String txtWorkMonth10) {
		this.txtWorkMonth10 = txtWorkMonth10;
	}
	
	/**
	 * @return txtWorkMonth11
	 */
	public String getTxtWorkMonth11() {
		return txtWorkMonth11;
	}
	
	/**
	 * @param txtWorkMonth11 セットする txtWorkMonth11
	 */
	public void setTxtWorkMonth11(String txtWorkMonth11) {
		this.txtWorkMonth11 = txtWorkMonth11;
	}
	
	/**
	 * @return txtWorkMonth12
	 */
	public String getTxtWorkMonth12() {
		return txtWorkMonth12;
	}
	
	/**
	 * @param txtWorkMonth12 セットする txtWorkMonth12
	 */
	public void setTxtWorkMonth12(String txtWorkMonth12) {
		this.txtWorkMonth12 = txtWorkMonth12;
	}
	
	/**
	 * @return txtJoiningDateAmount1
	 */
	public String getTxtJoiningDateAmount1() {
		return txtJoiningDateAmount1;
	}
	
	/**
	 * @param txtJoiningDateAmount1 セットする txtJoiningDateAmount1
	 */
	public void setTxtJoiningDateAmount1(String txtJoiningDateAmount1) {
		this.txtJoiningDateAmount1 = txtJoiningDateAmount1;
	}
	
	/**
	 * @return txtJoiningDateAmount2
	 */
	public String getTxtJoiningDateAmount2() {
		return txtJoiningDateAmount2;
	}
	
	/**
	 * @param txtJoiningDateAmount2 セットする txtJoiningDateAmount2
	 */
	public void setTxtJoiningDateAmount2(String txtJoiningDateAmount2) {
		this.txtJoiningDateAmount2 = txtJoiningDateAmount2;
	}
	
	/**
	 * @return txtJoiningDateAmount3
	 */
	public String getTxtJoiningDateAmount3() {
		return txtJoiningDateAmount3;
	}
	
	/**
	 * @param txtJoiningDateAmount3 セットする txtJoiningDateAmount3
	 */
	public void setTxtJoiningDateAmount3(String txtJoiningDateAmount3) {
		this.txtJoiningDateAmount3 = txtJoiningDateAmount3;
	}
	
	/**
	 * @return txtJoiningDateAmount4
	 */
	public String getTxtJoiningDateAmount4() {
		return txtJoiningDateAmount4;
	}
	
	/**
	 * @param txtJoiningDateAmount4 セットする txtJoiningDateAmount4
	 */
	public void setTxtJoiningDateAmount4(String txtJoiningDateAmount4) {
		this.txtJoiningDateAmount4 = txtJoiningDateAmount4;
	}
	
	/**
	 * @return txtJoiningDateAmount5
	 */
	public String getTxtJoiningDateAmount5() {
		return txtJoiningDateAmount5;
	}
	
	/**
	 * @param txtJoiningDateAmount5 セットする txtJoiningDateAmount5
	 */
	public void setTxtJoiningDateAmount5(String txtJoiningDateAmount5) {
		this.txtJoiningDateAmount5 = txtJoiningDateAmount5;
	}
	
	/**
	 * @return txtJoiningDateAmount6
	 */
	public String getTxtJoiningDateAmount6() {
		return txtJoiningDateAmount6;
	}
	
	/**
	 * @param txtJoiningDateAmount6 セットする txtJoiningDateAmount6
	 */
	public void setTxtJoiningDateAmount6(String txtJoiningDateAmount6) {
		this.txtJoiningDateAmount6 = txtJoiningDateAmount6;
	}
	
	/**
	 * @return txtJoiningDateAmount7
	 */
	public String getTxtJoiningDateAmount7() {
		return txtJoiningDateAmount7;
	}
	
	/**
	 * @param txtJoiningDateAmount7 セットする txtJoiningDateAmount7
	 */
	public void setTxtJoiningDateAmount7(String txtJoiningDateAmount7) {
		this.txtJoiningDateAmount7 = txtJoiningDateAmount7;
	}
	
	/**
	 * @return txtJoiningDateAmount8
	 */
	public String getTxtJoiningDateAmount8() {
		return txtJoiningDateAmount8;
	}
	
	/**
	 * @param txtJoiningDateAmount8 セットする txtJoiningDateAmount8
	 */
	public void setTxtJoiningDateAmount8(String txtJoiningDateAmount8) {
		this.txtJoiningDateAmount8 = txtJoiningDateAmount8;
	}
	
	/**
	 * @return txtJoiningDateAmount9
	 */
	public String getTxtJoiningDateAmount9() {
		return txtJoiningDateAmount9;
	}
	
	/**
	 * @param txtJoiningDateAmount9 セットする txtJoiningDateAmount9
	 */
	public void setTxtJoiningDateAmount9(String txtJoiningDateAmount9) {
		this.txtJoiningDateAmount9 = txtJoiningDateAmount9;
	}
	
	/**
	 * @return txtJoiningDateAmount10
	 */
	public String getTxtJoiningDateAmount10() {
		return txtJoiningDateAmount10;
	}
	
	/**
	 * @param txtJoiningDateAmount10 セットする txtJoiningDateAmount10
	 */
	public void setTxtJoiningDateAmount10(String txtJoiningDateAmount10) {
		this.txtJoiningDateAmount10 = txtJoiningDateAmount10;
	}
	
	/**
	 * @return txtJoiningDateAmount11
	 */
	public String getTxtJoiningDateAmount11() {
		return txtJoiningDateAmount11;
	}
	
	/**
	 * @param txtJoiningDateAmount11 セットする txtJoiningDateAmount11
	 */
	public void setTxtJoiningDateAmount11(String txtJoiningDateAmount11) {
		this.txtJoiningDateAmount11 = txtJoiningDateAmount11;
	}
	
	/**
	 * @return txtJoiningDateAmount12
	 */
	public String getTxtJoiningDateAmount12() {
		return txtJoiningDateAmount12;
	}
	
	/**
	 * @param txtJoiningDateAmount12 セットする txtJoiningDateAmount12
	 */
	public void setTxtJoiningDateAmount12(String txtJoiningDateAmount12) {
		this.txtJoiningDateAmount12 = txtJoiningDateAmount12;
	}
	
	/**
	 * @return txtGeneralJoiningMonth
	 */
	public String getTxtGeneralJoiningMonth() {
		return txtGeneralJoiningMonth;
	}
	
	/**
	 * @param txtGeneralJoiningMonth セットする txtGeneralJoiningMonth
	 */
	public void setTxtGeneralJoiningMonth(String txtGeneralJoiningMonth) {
		this.txtGeneralJoiningMonth = txtGeneralJoiningMonth;
	}
	
	/**
	 * @return txtGeneralJoiningAmount
	 */
	public String getTxtGeneralJoiningAmount() {
		return txtGeneralJoiningAmount;
	}
	
	/**
	 * @param txtGeneralJoiningAmount セットする txtGeneralJoiningAmount
	 */
	public void setTxtGeneralJoiningAmount(String txtGeneralJoiningAmount) {
		this.txtGeneralJoiningAmount = txtGeneralJoiningAmount;
	}
	
	/**
	 * @return txtStockYearAmount
	 */
	public String getTxtStockYearAmount() {
		return txtStockYearAmount;
	}
	
	/**
	 * @param txtStockYearAmount セットする txtStockYearAmount
	 */
	public void setTxtStockYearAmount(String txtStockYearAmount) {
		this.txtStockYearAmount = txtStockYearAmount;
	}
	
	/**
	 * @return txtStockTotalAmount
	 */
	public String getTxtStockTotalAmount() {
		return txtStockTotalAmount;
	}
	
	/**
	 * @param txtStockTotalAmount セットする txtStockTotalAmount
	 */
	public void setTxtStockTotalAmount(String txtStockTotalAmount) {
		this.txtStockTotalAmount = txtStockTotalAmount;
	}
	
	/**
	 * @return txtStockLimitDate
	 */
	public String getTxtStockLimitDate() {
		return txtStockLimitDate;
	}
	
	/**
	 * @param txtStockLimitDate セットする txtStockLimitDate
	 */
	public void setTxtStockLimitDate(String txtStockLimitDate) {
		this.txtStockLimitDate = txtStockLimitDate;
	}
	
	/**
	 * @return tmmStockHolidayId
	 */
	public long getTmmStockHolidayId() {
		return tmmStockHolidayId;
	}
	
	/**
	 * @param tmmStockHolidayId セットする tmmStockHolidayId
	 */
	public void setTmmStockHolidayId(long tmmStockHolidayId) {
		this.tmmStockHolidayId = tmmStockHolidayId;
	}
	
	/**
	 * @return tmmPaidHolidayFirstYearId
	 */
	public long[] getTmmPaidHolidayFirstYearId() {
		return getLongArrayClone(tmmPaidHolidayFirstYearId);
	}
	
	/**
	 * @param tmmPaidHolidayFirstYearId セットする tmmPaidHolidayFirstYearId
	 */
	public void setTmmPaidHolidayFirstYearId(long[] tmmPaidHolidayFirstYearId) {
		this.tmmPaidHolidayFirstYearId = getLongArrayClone(tmmPaidHolidayFirstYearId);
	}
	
	/**
	 * @param i 添え字
	 * @return tmmPaidHolidayFirstYearId
	 */
	public long getTmmPaidHolidayFirstYearId(int i) {
		return tmmPaidHolidayFirstYearId[i];
	}
	
	/**
	 * @param tmmPaidHolidayFirstYearId セットする tmmPaidHolidayFirstYearId
	 * @param i 添え字
	 */
	public void setTmmPaidHolidayFirstYearId(long tmmPaidHolidayFirstYearId, int i) {
		this.tmmPaidHolidayFirstYearId[i] = tmmPaidHolidayFirstYearId;
	}
	
	/**
	 * @return tmmPaidHolidayPointDateId
	 */
	public long[] getTmmPaidHolidayPointDateId() {
		return getLongArrayClone(tmmPaidHolidayPointDateId);
	}
	
	/**
	 * @param tmmPaidHolidayPointDateId セットする tmmPaidHolidayPointDateId
	 */
	public void setTmmPaidHolidayPointDateId(long[] tmmPaidHolidayPointDateId) {
		this.tmmPaidHolidayPointDateId = getLongArrayClone(tmmPaidHolidayPointDateId);
	}
	
	/**
	 * @param i 添え字
	 * @return tmmPaidHolidayPointDateId
	 */
	public long getTmmPaidHolidayPointDateId(int i) {
		return tmmPaidHolidayPointDateId[i];
	}
	
	/**
	 * @param tmmPaidHolidayPointDateId セットする tmmPaidHolidayPointDateId
	 * @param i 添え字
	 */
	public void setTmmPaidHolidayPointDateId(long tmmPaidHolidayPointDateId, int i) {
		this.tmmPaidHolidayPointDateId[i] = tmmPaidHolidayPointDateId;
	}
	
	/**
	 * @return tmmPaidHolidayEntranceDateId
	 */
	public long[] getTmmPaidHolidayEntranceDateId() {
		return getLongArrayClone(tmmPaidHolidayEntranceDateId);
	}
	
	/**
	 * @param tmmPaidHolidayEntranceDateId セットする tmmPaidHolidayEntranceDateId
	 */
	public void setTmmPaidHolidayEntranceDateId(long[] tmmPaidHolidayEntranceDateId) {
		this.tmmPaidHolidayEntranceDateId = getLongArrayClone(tmmPaidHolidayEntranceDateId);
	}
	
	/**
	 * @param i 添え字
	 * @return tmmPaidHolidayEntranceDateId
	 */
	public long getTmmPaidHolidayEntranceDateId(int i) {
		return tmmPaidHolidayEntranceDateId[i];
	}
	
	/**
	 * @param tmmPaidHolidayEntranceDateId セットする tmmPaidHolidayEntranceDateId
	 * @param i 添え字
	 */
	public void setTmmPaidHolidayEntranceDateId(long tmmPaidHolidayEntranceDateId, int i) {
		this.tmmPaidHolidayEntranceDateId[i] = tmmPaidHolidayEntranceDateId;
	}
	
}
