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
package jp.mosp.time.input.vo;

import jp.mosp.time.base.TimeVo;

/**
 * 休暇申請情報を格納する。
 */
public class HolidayRequestVo extends TimeVo {
	
	private static final long	serialVersionUID	= 5650562424014702579L;
	
	private long				recordId;
	
	private String[]			aryLblStyle;
	private String[]			aryLblPaidLeaveFiscalYear;
	private String[]			aryLblPaidLeaveGrantDate;
	private String[]			aryLblPaidLeaveExpirationDate;
	private String[]			aryLblPaidLeaveRemainDays;
	private String[]			aryLblPaidLeaveGrantDays;
	
	private String				lblNextGivingDate;
	private String				lblNextGivingAmount;
	// 次回予定期限
	private String				lblNextLimitDate;
	// 次回予定付与時間
	
	private String				lblNextManualGivingDate;
	private String				lblNextManualGivingAmount;
	
	// 時間単位申請限度
	private String				lblHolidayTimeUnitLimit;
	
	private boolean				isPaidLeaveByHour;
	
	private String				lblPaidHolidayStock;
	private String				lblSpecialHoliday1;
	private String				lblSpecialHoliday2;
	private String				lblSpecialHoliday3;
	private String				lblSpecialHoliday4;
	private String				lblSpecialHoliday5;
	private String				lblSpecialHoliday6;
	private String				lblSpecialHoliday7;
	private String				lblSpecialHoliday8;
	private String				lblSpecialHoliday9;
	private String				lblSpecialHoliday10;
	private String				lblSpecialHolidayLimit1;
	private String				lblSpecialHolidayLimit2;
	private String				lblSpecialHolidayLimit3;
	private String				lblSpecialHolidayLimit4;
	private String				lblSpecialHolidayLimit5;
	private String				lblSpecialHolidayLimit6;
	private String				lblSpecialHolidayLimit7;
	private String				lblSpecialHolidayLimit8;
	private String				lblSpecialHolidayLimit9;
	private String				lblSpecialHolidayLimit10;
	private String				lblOtherHoliday1;
	private String				lblOtherHoliday2;
	private String				lblOtherHoliday3;
	private String				lblOtherHoliday4;
	private String				lblOtherHoliday5;
	private String				lblOtherHoliday6;
	private String				lblOtherHoliday7;
	private String				lblOtherHoliday8;
	private String				lblOtherHoliday9;
	private String				lblOtherHoliday10;
	private String				lblOtherHolidayLimit1;
	private String				lblOtherHolidayLimit2;
	private String				lblOtherHolidayLimit3;
	private String				lblOtherHolidayLimit4;
	private String				lblOtherHolidayLimit5;
	private String				lblOtherHolidayLimit6;
	private String				lblOtherHolidayLimit7;
	private String				lblOtherHolidayLimit8;
	private String				lblOtherHolidayLimit9;
	private String				lblOtherHolidayLimit10;
	
	private String				pltEditStartYear;
	private String				pltEditStartMonth;
	private String				pltEditStartDay;
	private String				pltEditEndYear;
	private String				pltEditEndMonth;
	private String				pltEditEndDay;
	private String				pltEditHolidayType1;
	private String				pltEditHolidayRange;
	private String				pltEditHolidayRangePaidHoliday;
	private String				pltEditStartHour;
	private String				pltEditStartMinute;
	private String				pltEditEndTime;
	private String				txtEditRequestReason;
	
	private String				pltSearchHolidayType1;
	private String				pltSearchHolidayType2;
	private String				pltSearchHolidayType3;
	private String				pltSearchState;
	private String				pltSearchYear;
	private String				pltSearchMonth;
	
	private String[]			aryLblDate;
	private String[]			aryLblHolidayType1;
	private String[]			aryLblHolidayType2;
	private String[]			aryLblHolidayType3;
	private String[]			aryLblRequestReason;
	private String[]			aryLblState;
	private String[]			aryStateStyle;
	private String[]			aryLblApprover;
	private String[]			aryLblWorkflow;
	private String[]			aryHolidayType1;
	private String[]			aryHolidayType2;
	private String[]			aryHolidayType3;
	private String[]			aryStartTime;
	private String[]			aryLblOnOff;
	
	private String[]			aryCkbHolidayRequestListId;
	
	private String[][]			aryPltEditStartYear;
	private String[][]			aryPltEditStartMonth;
	private String[][]			aryPltEditStartDay;
	private String[][]			aryPltEditEndYear;
	private String[][]			aryPltEditEndMonth;
	private String[][]			aryPltEditEndDay;
	private String[][]			aryPltEditHolidayType1;
	private String[][]			aryPltEditHolidayType2WithPay;
	private String[][]			aryPltEditHolidayType2Special;
	private String[][]			aryPltEditHolidayType2Other;
	private String[][]			aryPltEditHolidayType2Absence;
	private String[][]			aryPltEditHolidayRange;
	private String[][]			aryPltEditHolidayRangePaidHoliday;
	private String[][]			aryPltEditStartHour;
	private String[][]			aryPltEditStartMinute;
	private String[][]			aryPltEditEndTime;
	private String[][]			aryPltSearchHolidayType2Special;
	private String[][]			aryPltSearchHolidayType2Other;
	private String[][]			aryPltSearchHolidayType2Absence;
	private String[][]			aryPltSearchHolidayRangePaidHoliday;
	private String[][]			aryPltSearchYear;
	private String[][]			aryPltSearchMonth;
	
	private String				lblEmployeeCode;
	private String				lblEmployeeName;
	private String				lblSection;
	
	private String				jsSearchModeActivateDate;
	
	private String				jsHolidayContinue;
	private String				jsHolidayRemainDay;
	private String				jsHolidayTerm;
	
	private String				jsPaidHolidayStock;
	private String				jsHolidayTotalTime;
	private String				jsHolidayTotalDay;
	
	private String[]			aryLblGivingDate;
	private String[]			aryLblSpecialHolidayType;
	private String[]			aryLblSpecialHolidayName;
	private String[]			aryLblRemainder;
	private String[]			aryLblLimit;
	
	private String				lblTotalDay;
	private String				lblTotalTime;
	
	private String				pltSearchHolidayType;
	
	private String				pltSearchStatusWithPay;
	private String				pltSearchStatusSpecial;
	private String				pltSearchSpecialOther;
	private String				pltSearchSpecialAbsence;
	
	private String				pltSearchHolidayRange1;
	
	private String				pltEditStatusWithPay;
	private String				pltEditStatusSpecial;
	private String				pltEditSpecialOther;
	private String				pltEditSpecialAbsence;
	
	/**
	 * ワークフロー番号。<br>
	 */
	private long[]				aryWorkflow;
	private String[]			aryWorkflowStatus;
	
	private boolean				jsPaidHolidayReasonRequired;
	
	/**
	 * 追加フィールド。
	 * アドオンで処理をする。
	 */
	private String				jsAddExtraField;
	
	
	/**
	 * @return aryLblPaidLeaveFiscalYear
	 */
	public String[] getAryLblPaidLeaveFiscalYear() {
		return getStringArrayClone(aryLblPaidLeaveFiscalYear);
	}
	
	/**
	 * @param aryLblPaidLeaveFiscalYear セットする aryLblPaidLeaveFiscalYear
	 */
	public void setAryLblPaidLeaveFiscalYear(String[] aryLblPaidLeaveFiscalYear) {
		this.aryLblPaidLeaveFiscalYear = getStringArrayClone(aryLblPaidLeaveFiscalYear);
	}
	
	/**
	 * @return aryLblPaidLeaveGrantDate
	 */
	public String[] getAryLblPaidLeaveGrantDate() {
		return getStringArrayClone(aryLblPaidLeaveGrantDate);
	}
	
	/**
	 * @param aryLblPaidLeaveGrantDate セットする aryLblPaidLeaveGrantDate
	 */
	public void setAryLblPaidLeaveGrantDate(String[] aryLblPaidLeaveGrantDate) {
		this.aryLblPaidLeaveGrantDate = getStringArrayClone(aryLblPaidLeaveGrantDate);
	}
	
	/**
	 * @return aryLblPaidLeaveExpirationDate
	 */
	public String[] getAryLblPaidLeaveExpirationDate() {
		return getStringArrayClone(aryLblPaidLeaveExpirationDate);
	}
	
	/**
	 * @param aryLblPaidLeaveExpirationDate セットする aryLblPaidLeaveExpirationDate
	 */
	public void setAryLblPaidLeaveExpirationDate(String[] aryLblPaidLeaveExpirationDate) {
		this.aryLblPaidLeaveExpirationDate = getStringArrayClone(aryLblPaidLeaveExpirationDate);
	}
	
	/**
	 * @return aryLblPaidLeaveRemainDays
	 */
	public String[] getAryLblPaidLeaveRemainDays() {
		return getStringArrayClone(aryLblPaidLeaveRemainDays);
	}
	
	/**
	 * @param aryLblPaidLeaveRemainDays セットする aryLblPaidLeaveRemainDays
	 */
	public void setAryLblPaidLeaveRemainDays(String[] aryLblPaidLeaveRemainDays) {
		this.aryLblPaidLeaveRemainDays = getStringArrayClone(aryLblPaidLeaveRemainDays);
	}
	
	/**
	 * @return aryLblPaidLeaveGrantDays
	 */
	public String[] getAryLblPaidLeaveGrantDays() {
		return getStringArrayClone(aryLblPaidLeaveGrantDays);
	}
	
	/**
	 * @param aryLblPaidLeaveGrantDays セットする aryLblPaidLeaveGrantDays
	 */
	public void setAryLblPaidLeaveGrantDays(String[] aryLblPaidLeaveGrantDays) {
		this.aryLblPaidLeaveGrantDays = getStringArrayClone(aryLblPaidLeaveGrantDays);
	}
	
	/**
	 * @return pltEditStartYear
	 */
	public String getPltEditStartYear() {
		return pltEditStartYear;
	}
	
	/**
	 * @return pltEditStartMonth
	 */
	public String getPltEditStartMonth() {
		return pltEditStartMonth;
	}
	
	/**
	 * @return pltEditStartDay
	 */
	public String getPltEditStartDay() {
		return pltEditStartDay;
	}
	
	/**
	 * @return pltEditEndYear
	 */
	public String getPltEditEndYear() {
		return pltEditEndYear;
	}
	
	/**
	 * @return pltEditEndMonth
	 */
	public String getPltEditEndMonth() {
		return pltEditEndMonth;
	}
	
	/**
	 * @return pltEditEndDay
	 */
	public String getPltEditEndDay() {
		return pltEditEndDay;
	}
	
	/**
	 * @return pltEditHolidayType1
	 */
	public String getPltEditHolidayType1() {
		return pltEditHolidayType1;
	}
	
	/**
	 * @return pltEditStartHour
	 */
	public String getPltEditStartHour() {
		return pltEditStartHour;
	}
	
	/**
	 * @return pltEditStartMinute
	 */
	public String getPltEditStartMinute() {
		return pltEditStartMinute;
	}
	
	/**
	 * @return pltEditEndTime
	 */
	public String getPltEditEndTime() {
		return pltEditEndTime;
	}
	
	/**
	 * @return txtEditRequestReason
	 */
	public String getTxtEditRequestReason() {
		return txtEditRequestReason;
	}
	
	/**
	 * @return pltSearchHolidayType1
	 */
	public String getPltSearchHolidayType1() {
		return pltSearchHolidayType1;
	}
	
	/**
	 * @return pltSearchHolidayType2
	 */
	public String getPltSearchHolidayType2() {
		return pltSearchHolidayType2;
	}
	
	/**
	 * @return pltSearchHolidayType3
	 */
	public String getPltSearchHolidayType3() {
		return pltSearchHolidayType3;
	}
	
	/**
	 * @return pltSearchState
	 */
	public String getPltSearchState() {
		return pltSearchState;
	}
	
	/**
	 * @return pltSearchYear
	 */
	public String getPltSearchYear() {
		return pltSearchYear;
	}
	
	/**
	 * @return pltSearchMonth
	 */
	public String getPltSearchMonth() {
		return pltSearchMonth;
	}
	
	/**
	 * @return lblNextGivingDate
	 */
	public String getLblNextGivingDate() {
		return lblNextGivingDate;
	}
	
	/**
	 * @return lblNextGivingAmount
	 */
	public String getLblNextGivingAmount() {
		return lblNextGivingAmount;
	}
	
	/**
	 * @return lblNextLimitDate
	 */
	public String getLblNextLimitDate() {
		return lblNextLimitDate;
	}
	
	/**
	 * @return lblNextManualGivingDate
	 */
	public String getLblNextManualGivingDate() {
		return lblNextManualGivingDate;
	}
	
	/**
	 * @return lblNextManualGivingAmount
	 */
	public String getLblNextManualGivingAmount() {
		return lblNextManualGivingAmount;
	}
	
	/**
	 * @return lblHolidayTimeUnitLimit
	 */
	public String getLblHolidayTimeUnitLimit() {
		return lblHolidayTimeUnitLimit;
	}
	
	/**
	 * @return lblPaidHolidayStock
	 */
	public String getLblPaidHolidayStock() {
		return lblPaidHolidayStock;
	}
	
	/**
	 * @return aryLblDate
	 */
	public String[] getAryLblDate() {
		return getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @return aryLblHolidayType1
	 */
	public String[] getAryLblHolidayType1() {
		return getStringArrayClone(aryLblHolidayType1);
	}
	
	/**
	 * @return aryLblHolidayType2
	 */
	public String[] getAryLblHolidayType2() {
		return getStringArrayClone(aryLblHolidayType2);
	}
	
	/**
	 * @return aryLblHolidayType3
	 */
	public String[] getAryLblHolidayType3() {
		return getStringArrayClone(aryLblHolidayType3);
	}
	
	/**
	 * @return aryLblRequestReason
	 */
	public String[] getAryLblRequestReason() {
		return getStringArrayClone(aryLblRequestReason);
	}
	
	/**
	 * @return aryLblState
	 */
	public String[] getAryLblState() {
		return getStringArrayClone(aryLblState);
	}
	
	/**
	 * @return aryStateStyle
	 */
	public String[] getAryStateStyle() {
		return getStringArrayClone(aryStateStyle);
	}
	
	/**
	 * @return aryLblApprover
	 */
	public String[] getAryLblApprover() {
		return getStringArrayClone(aryLblApprover);
	}
	
	/**
	 * @return aryLblWorkflow
	 */
	public String[] getAryLblWorkflow() {
		return getStringArrayClone(aryLblWorkflow);
	}
	
	/**
	 * @return aryCkbHolidayRequestListId
	 */
	public String[] getAryCkbHolidayRequestListId() {
		return getStringArrayClone(aryCkbHolidayRequestListId);
	}
	
	/**
	 * @return aryPltEditStartYear
	 */
	public String[][] getAryPltEditStartYear() {
		return getStringArrayClone(aryPltEditStartYear);
	}
	
	/**
	 * @return aryPltEditStartMonth
	 */
	public String[][] getAryPltEditStartMonth() {
		return getStringArrayClone(aryPltEditStartMonth);
	}
	
	/**
	 * @return aryPltEditStartDay
	 */
	public String[][] getAryPltEditStartDay() {
		return getStringArrayClone(aryPltEditStartDay);
	}
	
	/**
	 * @return aryPltEditEndYear
	 */
	public String[][] getAryPltEditEndYear() {
		return getStringArrayClone(aryPltEditEndYear);
	}
	
	/**
	 * @return aryPltEditEndMonth
	 */
	public String[][] getAryPltEditEndMonth() {
		return getStringArrayClone(aryPltEditEndMonth);
	}
	
	/**
	 * @return aryPltEditEndDay
	 */
	public String[][] getAryPltEditEndDay() {
		return getStringArrayClone(aryPltEditEndDay);
	}
	
	/**
	 * @return aryPltEditHolidayType1
	 */
	public String[][] getAryPltEditHolidayType1() {
		return getStringArrayClone(aryPltEditHolidayType1);
	}
	
	/**
	 * @return aryPltEditHolidayType2WithPay
	 */
	public String[][] getAryPltEditHolidayType2WithPay() {
		return getStringArrayClone(aryPltEditHolidayType2WithPay);
	}
	
	/**
	 * @return aryPltEditHolidayType2Special
	 */
	public String[][] getAryPltEditHolidayType2Special() {
		return getStringArrayClone(aryPltEditHolidayType2Special);
	}
	
	/**
	 * @return aryPltEditHolidayType2Other
	 */
	public String[][] getAryPltEditHolidayType2Other() {
		return getStringArrayClone(aryPltEditHolidayType2Other);
	}
	
	/**
	 * @return aryPltEditHolidayType2Absence
	 */
	public String[][] getAryPltEditHolidayType2Absence() {
		return getStringArrayClone(aryPltEditHolidayType2Absence);
	}
	
	/**
	 * @return aryPltEditStartHour
	 */
	public String[][] getAryPltEditStartHour() {
		return getStringArrayClone(aryPltEditStartHour);
	}
	
	/**
	 * @return aryPltEditStartMinute
	 */
	public String[][] getAryPltEditStartMinute() {
		return getStringArrayClone(aryPltEditStartMinute);
	}
	
	/**
	 * @return aryPltEditEndTime
	 */
	public String[][] getAryPltEditEndTime() {
		return getStringArrayClone(aryPltEditEndTime);
	}
	
	/**
	 * @return aryPltSearchHolidayType2Special
	 */
	public String[][] getAryPltSearchHolidayType2Special() {
		return getStringArrayClone(aryPltSearchHolidayType2Special);
	}
	
	/**
	 * @return aryPltSearchHolidayType2Other
	 */
	public String[][] getAryPltSearchHolidayType2Other() {
		return getStringArrayClone(aryPltSearchHolidayType2Other);
	}
	
	/**
	 * @return aryPltSearchHolidayType2Absence
	 */
	public String[][] getAryPltSearchHolidayType2Absence() {
		return getStringArrayClone(aryPltSearchHolidayType2Absence);
	}
	
	/**
	 * @return aryPltSearchYear
	 */
	public String[][] getAryPltSearchYear() {
		return getStringArrayClone(aryPltSearchYear);
	}
	
	/**
	 * @return aryPltSearchMonth
	 */
	public String[][] getAryPltSearchMonth() {
		return getStringArrayClone(aryPltSearchMonth);
	}
	
	/**
	 * @param pltEditStartYear セットする pltEditStartYear
	 */
	public void setPltEditStartYear(String pltEditStartYear) {
		this.pltEditStartYear = pltEditStartYear;
	}
	
	/**
	 * @param pltEditStartMonth セットする pltEditStartMonth
	 */
	public void setPltEditStartMonth(String pltEditStartMonth) {
		this.pltEditStartMonth = pltEditStartMonth;
	}
	
	/**
	 * @param pltEditStartDay セットする pltEditStartDay
	 */
	public void setPltEditStartDay(String pltEditStartDay) {
		this.pltEditStartDay = pltEditStartDay;
	}
	
	/**
	 * @param pltEditEndYear セットする pltEditEndYear
	 */
	public void setPltEditEndYear(String pltEditEndYear) {
		this.pltEditEndYear = pltEditEndYear;
	}
	
	/**
	 * @param pltEditEndMonth セットする pltEditEndMonth
	 */
	public void setPltEditEndMonth(String pltEditEndMonth) {
		this.pltEditEndMonth = pltEditEndMonth;
	}
	
	/**
	 * @param pltEditEndDay セットする pltEditEndDay
	 */
	public void setPltEditEndDay(String pltEditEndDay) {
		this.pltEditEndDay = pltEditEndDay;
	}
	
	/**
	 * @param pltEditHolidayType1 セットする pltEditHolidayType1
	 */
	public void setPltEditHolidayType1(String pltEditHolidayType1) {
		this.pltEditHolidayType1 = pltEditHolidayType1;
	}
	
	/**
	 * @param pltEditStartHour セットする pltEditStartHour
	 */
	public void setPltEditStartHour(String pltEditStartHour) {
		this.pltEditStartHour = pltEditStartHour;
	}
	
	/**
	 * @param pltEditStartMinute セットする pltEditStartMinute
	 */
	public void setPltEditStartMinute(String pltEditStartMinute) {
		this.pltEditStartMinute = pltEditStartMinute;
	}
	
	/**
	 * @param pltEditEndTime セットする pltEditEndTime
	 */
	public void setPltEditEndTime(String pltEditEndTime) {
		this.pltEditEndTime = pltEditEndTime;
	}
	
	/**
	 * @param txtEditRequestReason セットする txtEditRequestReason
	 */
	public void setTxtEditRequestReason(String txtEditRequestReason) {
		this.txtEditRequestReason = txtEditRequestReason;
	}
	
	/**
	 * @param pltSearchHolidayType1 セットする pltSearchHolidayType1
	 */
	public void setPltSearchHolidayType1(String pltSearchHolidayType1) {
		this.pltSearchHolidayType1 = pltSearchHolidayType1;
	}
	
	/**
	 * @param pltSearchHolidayType2 セットする pltSearchHolidayType2
	 */
	public void setPltSearchHolidayType2(String pltSearchHolidayType2) {
		this.pltSearchHolidayType2 = pltSearchHolidayType2;
	}
	
	/**
	 * @param pltSearchHolidayType3 セットする pltSearchHolidayType3
	 */
	public void setPltSearchHolidayType3(String pltSearchHolidayType3) {
		this.pltSearchHolidayType3 = pltSearchHolidayType3;
	}
	
	/**
	 * @param pltSearchState セットする pltSearchState
	 */
	public void setPltSearchState(String pltSearchState) {
		this.pltSearchState = pltSearchState;
	}
	
	/**
	 * @param pltSearchYear セットする pltSearchYear
	 */
	public void setPltSearchYear(String pltSearchYear) {
		this.pltSearchYear = pltSearchYear;
	}
	
	/**
	 * @param pltSearchMonth セットする pltSearchMonth
	 */
	public void setPltSearchMonth(String pltSearchMonth) {
		this.pltSearchMonth = pltSearchMonth;
	}
	
	/**
	 * @param lblNextGivingDate セットする lblNextGivingDate
	 */
	public void setLblNextGivingDate(String lblNextGivingDate) {
		this.lblNextGivingDate = lblNextGivingDate;
	}
	
	/**
	 * @param lblNextGivingAmount セットする lblNextGivingAmount
	 */
	public void setLblNextGivingAmount(String lblNextGivingAmount) {
		this.lblNextGivingAmount = lblNextGivingAmount;
	}
	
	/**
	 * @param lblNextLimitDate セットする lblNextLimitDate
	 */
	public void setLblNextLimitDate(String lblNextLimitDate) {
		this.lblNextLimitDate = lblNextLimitDate;
	}
	
	/**
	 * @param lblNextManualGivingDate セットする lblNextManualGivingDate
	 */
	public void setLblNextManualGivingDate(String lblNextManualGivingDate) {
		this.lblNextManualGivingDate = lblNextManualGivingDate;
	}
	
	/**
	 * @param lblNextManualGivingAmount セットする lblNextManualGivingAmount
	 */
	public void setLblNextManualGivingAmount(String lblNextManualGivingAmount) {
		this.lblNextManualGivingAmount = lblNextManualGivingAmount;
	}
	
	/**
	 * @param lblHolidayTimeUnitLimit セットする lblHolidayTimeUnitLimit
	 */
	public void setLblHolidayTimeUnitLimit(String lblHolidayTimeUnitLimit) {
		this.lblHolidayTimeUnitLimit = lblHolidayTimeUnitLimit;
	}
	
	/**
	 * @param lblPaidHolidayStock セットする lblPaidHolidayStock
	 */
	public void setLblPaidHolidayStock(String lblPaidHolidayStock) {
		this.lblPaidHolidayStock = lblPaidHolidayStock;
	}
	
	/**
	 * @param aryLblDate セットする aryLblDate
	 */
	public void setAryLblDate(String[] aryLblDate) {
		this.aryLblDate = getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @param aryLblHolidayType1 セットする aryLblHolidayType1
	 */
	public void setAryLblHolidayType1(String[] aryLblHolidayType1) {
		this.aryLblHolidayType1 = getStringArrayClone(aryLblHolidayType1);
	}
	
	/**
	 * @param aryLblHolidayType2 セットする aryLblHolidayType2
	 */
	public void setAryLblHolidayType2(String[] aryLblHolidayType2) {
		this.aryLblHolidayType2 = getStringArrayClone(aryLblHolidayType2);
	}
	
	/**
	 * @param aryLblHolidayType3 セットする aryLblHolidayType
	 */
	public void setAryLblHolidayType3(String[] aryLblHolidayType3) {
		this.aryLblHolidayType3 = getStringArrayClone(aryLblHolidayType3);
	}
	
	/**
	 * @param aryLblRequestReason セットする aryLblRequestReason
	 */
	public void setAryLblRequestReason(String[] aryLblRequestReason) {
		this.aryLblRequestReason = getStringArrayClone(aryLblRequestReason);
	}
	
	/**
	 * @param aryLblState セットする aryLblState
	 */
	public void setAryLblState(String[] aryLblState) {
		this.aryLblState = getStringArrayClone(aryLblState);
	}
	
	/**
	 * @param aryStateStyle セットする aryStateStyle
	 */
	public void setAryStateStyle(String[] aryStateStyle) {
		this.aryStateStyle = getStringArrayClone(aryStateStyle);
	}
	
	/**
	 * @param aryLblApprover セットする aryLblApprover
	 */
	public void setAryLblApprover(String[] aryLblApprover) {
		this.aryLblApprover = getStringArrayClone(aryLblApprover);
	}
	
	/**
	 * @param aryLblWorkflow セットする aryLblWorkflow
	 */
	public void setAryLblWorkflow(String[] aryLblWorkflow) {
		this.aryLblWorkflow = getStringArrayClone(aryLblWorkflow);
	}
	
	/**
	 * @param aryCkbHolidayRequestListId セットする aryCkbHolidayRequestListId
	 */
	public void setAryCkbHolidayRequestListId(String[] aryCkbHolidayRequestListId) {
		this.aryCkbHolidayRequestListId = getStringArrayClone(aryCkbHolidayRequestListId);
	}
	
	/**
	 * @param aryPltEditStartYear セットする aryPltEditStartYear
	 */
	public void setAryPltEditStartYear(String[][] aryPltEditStartYear) {
		this.aryPltEditStartYear = getStringArrayClone(aryPltEditStartYear);
	}
	
	/**
	 * @param aryPltEditStartMonth セットする aryPltEditStartMonth
	 */
	public void setAryPltEditStartMonth(String[][] aryPltEditStartMonth) {
		this.aryPltEditStartMonth = getStringArrayClone(aryPltEditStartMonth);
	}
	
	/**
	 * @param aryPltEditStartDay セットする aryPltEditStartDay
	 */
	public void setAryPltEditStartDay(String[][] aryPltEditStartDay) {
		this.aryPltEditStartDay = getStringArrayClone(aryPltEditStartDay);
	}
	
	/**
	 * @param aryPltEditEndYear セットする aryPltEditEndYear
	 */
	public void setAryPltEditEndYear(String[][] aryPltEditEndYear) {
		this.aryPltEditEndYear = getStringArrayClone(aryPltEditEndYear);
	}
	
	/**
	 * @param aryPltEditEndMonth セットする aryPltEditEndMonth
	 */
	public void setAryPltEditEndMonth(String[][] aryPltEditEndMonth) {
		this.aryPltEditEndMonth = getStringArrayClone(aryPltEditEndMonth);
	}
	
	/**
	 * @param aryPltEditEndDay セットする aryPltEditEndDay
	 */
	public void setAryPltEditEndDay(String[][] aryPltEditEndDay) {
		this.aryPltEditEndDay = getStringArrayClone(aryPltEditEndDay);
	}
	
	/**
	 * @param aryPltEditHolidayType1 セットする aryPltEditHolidayType1
	 */
	public void setAryPltEditHolidayType1(String[][] aryPltEditHolidayType1) {
		this.aryPltEditHolidayType1 = getStringArrayClone(aryPltEditHolidayType1);
	}
	
	/**
	 * @param aryPltEditHolidayType2WithPay セットする aryPltEditHolidayType2WithPay
	 */
	public void setAryPltEditHolidayType2WithPay(String[][] aryPltEditHolidayType2WithPay) {
		this.aryPltEditHolidayType2WithPay = getStringArrayClone(aryPltEditHolidayType2WithPay);
	}
	
	/**
	 * @param aryPltEditHolidayType2Special セットする aryPltEditHolidayType2Special
	 */
	public void setAryPltEditHolidayType2Special(String[][] aryPltEditHolidayType2Special) {
		this.aryPltEditHolidayType2Special = getStringArrayClone(aryPltEditHolidayType2Special);
	}
	
	/**
	 * @param aryPltEditHolidayType2Other セットする aryPltEditHolidayType2Other
	 */
	public void setAryPltEditHolidayType2Other(String[][] aryPltEditHolidayType2Other) {
		this.aryPltEditHolidayType2Other = getStringArrayClone(aryPltEditHolidayType2Other);
	}
	
	/**
	 * @param aryPltEditHolidayType2Absence セットする aryPltEditHolidayType2Absence
	 */
	public void setAryPltEditHolidayType2Absence(String[][] aryPltEditHolidayType2Absence) {
		this.aryPltEditHolidayType2Absence = getStringArrayClone(aryPltEditHolidayType2Absence);
	}
	
	/**
	 * @param aryPltEditStartHour セットする aryPltEditStartHour
	 */
	public void setAryPltEditStartHour(String[][] aryPltEditStartHour) {
		this.aryPltEditStartHour = getStringArrayClone(aryPltEditStartHour);
	}
	
	/**
	 * @param aryPltEditStartMinute セットする aryPltEditStartMinute
	 */
	public void setAryPltEditStartMinute(String[][] aryPltEditStartMinute) {
		this.aryPltEditStartMinute = getStringArrayClone(aryPltEditStartMinute);
	}
	
	/**
	 * @param aryPltEditEndTime セットする aryPltEditEndTime
	 */
	public void setAryPltEditEndTime(String[][] aryPltEditEndTime) {
		this.aryPltEditEndTime = getStringArrayClone(aryPltEditEndTime);
	}
	
	/**
	 * @param aryPltSearchHolidayType2Special セットする aryPltSearchHolidayType2Special
	 */
	public void setAryPltSearchHolidayType2Special(String[][] aryPltSearchHolidayType2Special) {
		this.aryPltSearchHolidayType2Special = getStringArrayClone(aryPltSearchHolidayType2Special);
	}
	
	/**
	 * @param aryPltSearchHolidayType2Other セットする aryPltSearchHolidayType2Other
	 */
	public void setAryPltSearchHolidayType2Other(String[][] aryPltSearchHolidayType2Other) {
		this.aryPltSearchHolidayType2Other = getStringArrayClone(aryPltSearchHolidayType2Other);
	}
	
	/**
	 * @param aryPltSearchHolidayType2Absence セットする aryPltSearchHolidayType2Absence
	 */
	public void setAryPltSearchHolidayType2Absence(String[][] aryPltSearchHolidayType2Absence) {
		this.aryPltSearchHolidayType2Absence = getStringArrayClone(aryPltSearchHolidayType2Absence);
	}
	
	/**
	 * @param aryPltSearchYear セットする aryPltSearchYear
	 */
	public void setAryPltSearchYear(String[][] aryPltSearchYear) {
		this.aryPltSearchYear = getStringArrayClone(aryPltSearchYear);
	}
	
	/**
	 * @param aryPltSearchMonth セットする aryPltSearchMonth
	 */
	public void setAryPltSearchMonth(String[][] aryPltSearchMonth) {
		this.aryPltSearchMonth = getStringArrayClone(aryPltSearchMonth);
	}
	
	/**
	 * @return lblEmployeeCode
	 */
	@Override
	public String getLblEmployeeCode() {
		return lblEmployeeCode;
	}
	
	/**
	 * @return lblEmployeeName
	 */
	@Override
	public String getLblEmployeeName() {
		return lblEmployeeName;
	}
	
	/**
	 * @return lblSection
	 */
	public String getLblSection() {
		return lblSection;
	}
	
	/**
	 * @param lblEmployeeCode セットする lblEmployeeCode
	 */
	@Override
	public void setLblEmployeeCode(String lblEmployeeCode) {
		this.lblEmployeeCode = lblEmployeeCode;
	}
	
	/**
	 * @param lblEmployeeName セットする lblEmployeeName
	 */
	@Override
	public void setLblEmployeeName(String lblEmployeeName) {
		this.lblEmployeeName = lblEmployeeName;
	}
	
	/**
	 * @param lblSection セットする lblSection
	 */
	public void setLblSection(String lblSection) {
		this.lblSection = lblSection;
	}
	
	/**
	 * @return lblSpecialHoliday1
	 */
	public String getLblSpecialHoliday1() {
		return lblSpecialHoliday1;
	}
	
	/**
	 * @return lblSpecialHoliday2
	 */
	public String getLblSpecialHoliday2() {
		return lblSpecialHoliday2;
	}
	
	/**
	 * @return lblSpecialHoliday3
	 */
	public String getLblSpecialHoliday3() {
		return lblSpecialHoliday3;
	}
	
	/**
	 * @return lblSpecialHoliday4
	 */
	public String getLblSpecialHoliday4() {
		return lblSpecialHoliday4;
	}
	
	/**
	 * @return lblSpecialHoliday5
	 */
	public String getLblSpecialHoliday5() {
		return lblSpecialHoliday5;
	}
	
	/**
	 * @return lblSpecialHoliday6
	 */
	public String getLblSpecialHoliday6() {
		return lblSpecialHoliday6;
	}
	
	/**
	 * @return lblSpecialHoliday7
	 */
	public String getLblSpecialHoliday7() {
		return lblSpecialHoliday7;
	}
	
	/**
	 * @return lblSpecialHoliday8
	 */
	public String getLblSpecialHoliday8() {
		return lblSpecialHoliday8;
	}
	
	/**
	 * @return lblSpecialHoliday9
	 */
	public String getLblSpecialHoliday9() {
		return lblSpecialHoliday9;
	}
	
	/**
	 * @return lblSpecialHoliday10
	 */
	public String getLblSpecialHoliday10() {
		return lblSpecialHoliday10;
	}
	
	/**
	 * @return lblOtherHoliday1
	 */
	public String getLblOtherHoliday1() {
		return lblOtherHoliday1;
	}
	
	/**
	 * @return lblOtherHoliday2
	 */
	public String getLblOtherHoliday2() {
		return lblOtherHoliday2;
	}
	
	/**
	 * @return lblOtherHoliday3
	 */
	public String getLblOtherHoliday3() {
		return lblOtherHoliday3;
	}
	
	/**
	 * @return lblOtherHoliday4
	 */
	public String getLblOtherHoliday4() {
		return lblOtherHoliday4;
	}
	
	/**
	 * @return lblOtherHoliday5
	 */
	public String getLblOtherHoliday5() {
		return lblOtherHoliday5;
	}
	
	/**
	 * @return lblOtherHoliday6
	 */
	public String getLblOtherHoliday6() {
		return lblOtherHoliday6;
	}
	
	/**
	 * @return lblOtherHoliday7
	 */
	public String getLblOtherHoliday7() {
		return lblOtherHoliday7;
	}
	
	/**
	 * @return lblOtherHoliday8
	 */
	public String getLblOtherHoliday8() {
		return lblOtherHoliday8;
	}
	
	/**
	 * @return lblOtherHoliday9
	 */
	public String getLblOtherHoliday9() {
		return lblOtherHoliday9;
	}
	
	/**
	 * @return lblOtherHoliday10
	 */
	public String getLblOtherHoliday10() {
		return lblOtherHoliday10;
	}
	
	/**
	 * @param lblSpecialHoliday1 セットする lblSpecialHoliday1
	 */
	public void setLblSpecialHoliday1(String lblSpecialHoliday1) {
		this.lblSpecialHoliday1 = lblSpecialHoliday1;
	}
	
	/**
	 * @param lblSpecialHoliday2 セットする lblSpecialHoliday2
	 */
	public void setLblSpecialHoliday2(String lblSpecialHoliday2) {
		this.lblSpecialHoliday2 = lblSpecialHoliday2;
	}
	
	/**
	 * @param lblSpecialHoliday3 セットする lblSpecialHoliday3
	 */
	public void setLblSpecialHoliday3(String lblSpecialHoliday3) {
		this.lblSpecialHoliday3 = lblSpecialHoliday3;
	}
	
	/**
	 * @param lblSpecialHoliday4 セットする lblSpecialHoliday4
	 */
	public void setLblSpecialHoliday4(String lblSpecialHoliday4) {
		this.lblSpecialHoliday4 = lblSpecialHoliday4;
	}
	
	/**
	 * @param lblSpecialHoliday5 セットする lblSpecialHoliday5
	 */
	public void setLblSpecialHoliday5(String lblSpecialHoliday5) {
		this.lblSpecialHoliday5 = lblSpecialHoliday5;
	}
	
	/**
	 * @param lblSpecialHoliday6 セットする lblSpecialHoliday6
	 */
	public void setLblSpecialHoliday6(String lblSpecialHoliday6) {
		this.lblSpecialHoliday6 = lblSpecialHoliday6;
	}
	
	/**
	 * @param lblSpecialHoliday7 セットする lblSpecialHoliday7
	 */
	public void setLblSpecialHoliday7(String lblSpecialHoliday7) {
		this.lblSpecialHoliday7 = lblSpecialHoliday7;
	}
	
	/**
	 * @param lblSpecialHoliday8 セットする lblSpecialHoliday8
	 */
	public void setLblSpecialHoliday8(String lblSpecialHoliday8) {
		this.lblSpecialHoliday8 = lblSpecialHoliday8;
	}
	
	/**
	 * @param lblSpecialHoliday9 セットする lblSpecialHoliday9
	 */
	public void setLblSpecialHoliday9(String lblSpecialHoliday9) {
		this.lblSpecialHoliday9 = lblSpecialHoliday9;
	}
	
	/**
	 * @param lblSpecialHoliday10 セットする lblSpecialHoliday10
	 */
	public void setLblSpecialHoliday10(String lblSpecialHoliday10) {
		this.lblSpecialHoliday10 = lblSpecialHoliday10;
	}
	
	/**
	 * @param lblOtherHoliday1 セットする lblOtherHoliday1
	 */
	public void setLblOtherHoliday1(String lblOtherHoliday1) {
		this.lblOtherHoliday1 = lblOtherHoliday1;
	}
	
	/**
	 * @param lblOtherHoliday2 セットする lblOtherHoliday2
	 */
	public void setLblOtherHoliday2(String lblOtherHoliday2) {
		this.lblOtherHoliday2 = lblOtherHoliday2;
	}
	
	/**
	 * @param lblOtherHoliday3 セットする lblOtherHoliday3
	 */
	public void setLblOtherHoliday3(String lblOtherHoliday3) {
		this.lblOtherHoliday3 = lblOtherHoliday3;
	}
	
	/**
	 * @param lblOtherHoliday4 セットする lblOtherHoliday4
	 */
	public void setLblOtherHoliday4(String lblOtherHoliday4) {
		this.lblOtherHoliday4 = lblOtherHoliday4;
	}
	
	/**
	 * @param lblOtherHoliday5 セットする lblOtherHoliday5
	 */
	public void setLblOtherHoliday5(String lblOtherHoliday5) {
		this.lblOtherHoliday5 = lblOtherHoliday5;
	}
	
	/**
	 * @param lblOtherHoliday6 セットする lblOtherHoliday6
	 */
	public void setLblOtherHoliday6(String lblOtherHoliday6) {
		this.lblOtherHoliday6 = lblOtherHoliday6;
	}
	
	/**
	 * @param lblOtherHoliday7 セットする lblOtherHoliday7
	 */
	public void setLblOtherHoliday7(String lblOtherHoliday7) {
		this.lblOtherHoliday7 = lblOtherHoliday7;
	}
	
	/**
	 * @param lblOtherHoliday8 セットする lblOtherHoliday8
	 */
	public void setLblOtherHoliday8(String lblOtherHoliday8) {
		this.lblOtherHoliday8 = lblOtherHoliday8;
	}
	
	/**
	 * @param lblOtherHoliday9 セットする lblOtherHoliday9
	 */
	public void setLblOtherHoliday9(String lblOtherHoliday9) {
		this.lblOtherHoliday9 = lblOtherHoliday9;
	}
	
	/**
	 * @param lblOtherHoliday10 セットする lblOtherHoliday10
	 */
	public void setLblOtherHoliday10(String lblOtherHoliday10) {
		this.lblOtherHoliday10 = lblOtherHoliday10;
	}
	
	/**
	 * @return lblSpecialHolidayLimit1
	 */
	public String getLblSpecialHolidayLimit1() {
		return lblSpecialHolidayLimit1;
	}
	
	/**
	 * @return lblSpecialHolidayLimit2
	 */
	public String getLblSpecialHolidayLimit2() {
		return lblSpecialHolidayLimit2;
	}
	
	/**
	 * @return lblSpecialHolidayLimit3
	 */
	public String getLblSpecialHolidayLimit3() {
		return lblSpecialHolidayLimit3;
	}
	
	/**
	 * @return lblSpecialHolidayLimit4
	 */
	public String getLblSpecialHolidayLimit4() {
		return lblSpecialHolidayLimit4;
	}
	
	/**
	 * @return lblSpecialHolidayLimit5
	 */
	public String getLblSpecialHolidayLimit5() {
		return lblSpecialHolidayLimit5;
	}
	
	/**
	 * @return lblSpecialHolidayLimit6
	 */
	public String getLblSpecialHolidayLimit6() {
		return lblSpecialHolidayLimit6;
	}
	
	/**
	 * @return lblSpecialHolidayLimit7
	 */
	public String getLblSpecialHolidayLimit7() {
		return lblSpecialHolidayLimit7;
	}
	
	/**
	 * @return lblSpecialHolidayLimit8
	 */
	public String getLblSpecialHolidayLimit8() {
		return lblSpecialHolidayLimit8;
	}
	
	/**
	 * @return lblSpecialHolidayLimit9
	 */
	public String getLblSpecialHolidayLimit9() {
		return lblSpecialHolidayLimit9;
	}
	
	/**
	 * @return lblSpecialHolidayLimit10
	 */
	public String getLblSpecialHolidayLimit10() {
		return lblSpecialHolidayLimit10;
	}
	
	/**
	 * @return lblOtherHolidayLimit1
	 */
	public String getLblOtherHolidayLimit1() {
		return lblOtherHolidayLimit1;
	}
	
	/**
	 * @return lblOtherHolidayLimit2
	 */
	public String getLblOtherHolidayLimit2() {
		return lblOtherHolidayLimit2;
	}
	
	/**
	 * @return lblOtherHolidayLimit3
	 */
	public String getLblOtherHolidayLimit3() {
		return lblOtherHolidayLimit3;
	}
	
	/**
	 * @return lblOtherHolidayLimit4
	 */
	public String getLblOtherHolidayLimit4() {
		return lblOtherHolidayLimit4;
	}
	
	/**
	 * @return lblOtherHolidayLimit5
	 */
	public String getLblOtherHolidayLimit5() {
		return lblOtherHolidayLimit5;
	}
	
	/**
	 * @return lblOtherHolidayLimit6
	 */
	public String getLblOtherHolidayLimit6() {
		return lblOtherHolidayLimit6;
	}
	
	/**
	 * @return lblOtherHolidayLimit7
	 */
	public String getLblOtherHolidayLimit7() {
		return lblOtherHolidayLimit7;
	}
	
	/**
	 * @return lblOtherHolidayLimit8
	 */
	public String getLblOtherHolidayLimit8() {
		return lblOtherHolidayLimit8;
	}
	
	/**
	 * @return lblOtherHolidayLimit9
	 */
	public String getLblOtherHolidayLimit9() {
		return lblOtherHolidayLimit9;
	}
	
	/**
	 * @return lblOtherHolidayLimit10
	 */
	public String getLblOtherHolidayLimit10() {
		return lblOtherHolidayLimit10;
	}
	
	/**
	 * @param lblSpecialHolidayLimit1 セットする lblSpecialHolidayLimit1
	 */
	public void setLblSpecialHolidayLimit1(String lblSpecialHolidayLimit1) {
		this.lblSpecialHolidayLimit1 = lblSpecialHolidayLimit1;
	}
	
	/**
	 * @param lblSpecialHolidayLimit2 セットする lblSpecialHolidayLimit2
	 */
	public void setLblSpecialHolidayLimit2(String lblSpecialHolidayLimit2) {
		this.lblSpecialHolidayLimit2 = lblSpecialHolidayLimit2;
	}
	
	/**
	 * @param lblSpecialHolidayLimit3 セットする lblSpecialHolidayLimit3
	 */
	public void setLblSpecialHolidayLimit3(String lblSpecialHolidayLimit3) {
		this.lblSpecialHolidayLimit3 = lblSpecialHolidayLimit3;
	}
	
	/**
	 * @param lblSpecialHolidayLimit4 セットする lblSpecialHolidayLimit4
	 */
	public void setLblSpecialHolidayLimit4(String lblSpecialHolidayLimit4) {
		this.lblSpecialHolidayLimit4 = lblSpecialHolidayLimit4;
	}
	
	/**
	 * @param lblSpecialHolidayLimit5 セットする lblSpecialHolidayLimit5
	 */
	public void setLblSpecialHolidayLimit5(String lblSpecialHolidayLimit5) {
		this.lblSpecialHolidayLimit5 = lblSpecialHolidayLimit5;
	}
	
	/**
	 * @param lblSpecialHolidayLimit6 セットする lblSpecialHolidayLimit6
	 */
	public void setLblSpecialHolidayLimit6(String lblSpecialHolidayLimit6) {
		this.lblSpecialHolidayLimit6 = lblSpecialHolidayLimit6;
	}
	
	/**
	 * @param lblSpecialHolidayLimit7 セットする lblSpecialHolidayLimit7
	 */
	public void setLblSpecialHolidayLimit7(String lblSpecialHolidayLimit7) {
		this.lblSpecialHolidayLimit7 = lblSpecialHolidayLimit7;
	}
	
	/**
	 * @param lblSpecialHolidayLimit8 セットする lblSpecialHolidayLimit8
	 */
	public void setLblSpecialHolidayLimit8(String lblSpecialHolidayLimit8) {
		this.lblSpecialHolidayLimit8 = lblSpecialHolidayLimit8;
	}
	
	/**
	 * @param lblSpecialHolidayLimit9 セットする lblSpecialHolidayLimit9
	 */
	public void setLblSpecialHolidayLimit9(String lblSpecialHolidayLimit9) {
		this.lblSpecialHolidayLimit9 = lblSpecialHolidayLimit9;
	}
	
	/**
	 * @param lblSpecialHolidayLimit10 セットする lblSpecialHolidayLimit10
	 */
	public void setLblSpecialHolidayLimit10(String lblSpecialHolidayLimit10) {
		this.lblSpecialHolidayLimit10 = lblSpecialHolidayLimit10;
	}
	
	/**
	 * @param lblOtherHolidayLimit1 セットする lblOtherHolidayLimit1
	 */
	public void setLblOtherHolidayLimit1(String lblOtherHolidayLimit1) {
		this.lblOtherHolidayLimit1 = lblOtherHolidayLimit1;
	}
	
	/**
	 * @param lblOtherHolidayLimit2 セットする lblOtherHolidayLimit2
	 */
	public void setLblOtherHolidayLimit2(String lblOtherHolidayLimit2) {
		this.lblOtherHolidayLimit2 = lblOtherHolidayLimit2;
	}
	
	/**
	 * @param lblOtherHolidayLimit3 セットする lblOtherHolidayLimit3
	 */
	public void setLblOtherHolidayLimit3(String lblOtherHolidayLimit3) {
		this.lblOtherHolidayLimit3 = lblOtherHolidayLimit3;
	}
	
	/**
	 * @param lblOtherHolidayLimit4 セットする lblOtherHolidayLimit4
	 */
	public void setLblOtherHolidayLimit4(String lblOtherHolidayLimit4) {
		this.lblOtherHolidayLimit4 = lblOtherHolidayLimit4;
	}
	
	/**
	 * @param lblOtherHolidayLimit5 セットする lblOtherHolidayLimit5
	 */
	public void setLblOtherHolidayLimit5(String lblOtherHolidayLimit5) {
		this.lblOtherHolidayLimit5 = lblOtherHolidayLimit5;
	}
	
	/**
	 * @param lblOtherHolidayLimit6 セットする lblOtherHolidayLimit6
	 */
	public void setLblOtherHolidayLimit6(String lblOtherHolidayLimit6) {
		this.lblOtherHolidayLimit6 = lblOtherHolidayLimit6;
	}
	
	/**
	 * @param lblOtherHolidayLimit7 セットする lblOtherHolidayLimit7
	 */
	public void setLblOtherHolidayLimit7(String lblOtherHolidayLimit7) {
		this.lblOtherHolidayLimit7 = lblOtherHolidayLimit7;
	}
	
	/**
	 * @param lblOtherHolidayLimit8 セットする lblOtherHolidayLimit8
	 */
	public void setLblOtherHolidayLimit8(String lblOtherHolidayLimit8) {
		this.lblOtherHolidayLimit8 = lblOtherHolidayLimit8;
	}
	
	/**
	 * @param lblOtherHolidayLimit9 セットする lblOtherHolidayLimit9
	 */
	public void setLblOtherHolidayLimit9(String lblOtherHolidayLimit9) {
		this.lblOtherHolidayLimit9 = lblOtherHolidayLimit9;
	}
	
	/**
	 * @param lblOtherHolidayLimit10 セットする lblOtherHolidayLimit10
	 */
	public void setLblOtherHolidayLimit10(String lblOtherHolidayLimit10) {
		this.lblOtherHolidayLimit10 = lblOtherHolidayLimit10;
	}
	
	/**
	 * @return jsSearchModeActivateDate
	 */
	public String getJsSearchModeActivateDate() {
		return jsSearchModeActivateDate;
	}
	
	/**
	 * @param jsSearchModeActivateDate セットする jsSearchModeActivateDate
	 */
	public void setJsSearchModeActivateDate(String jsSearchModeActivateDate) {
		this.jsSearchModeActivateDate = jsSearchModeActivateDate;
	}
	
	/**
	 * @return aryLblGivingDate
	 */
	public String[] getAryLblGivingDate() {
		return getStringArrayClone(aryLblGivingDate);
	}
	
	/**
	 * @param aryLblGivingDate セットする aryLblGivingDate
	 */
	public void setAryLblGivingDate(String[] aryLblGivingDate) {
		this.aryLblGivingDate = getStringArrayClone(aryLblGivingDate);
	}
	
	/**
	 * @return aryLblSpecialHolidayType
	 */
	public String[] getAryLblSpecialHolidayType() {
		return getStringArrayClone(aryLblSpecialHolidayType);
	}
	
	/**
	 * @param aryLblSpecialHolidayType セットする aryLblSpecialHolidayType
	 */
	public void setAryLblSpecialHolidayType(String[] aryLblSpecialHolidayType) {
		this.aryLblSpecialHolidayType = getStringArrayClone(aryLblSpecialHolidayType);
	}
	
	/**
	 * @return aryLblRemainder
	 */
	public String[] getAryLblRemainder() {
		return getStringArrayClone(aryLblRemainder);
	}
	
	/**
	 * @param aryLblRemainder セットする aryLblRemainder
	 */
	public void setAryLblRemainder(String[] aryLblRemainder) {
		this.aryLblRemainder = getStringArrayClone(aryLblRemainder);
	}
	
	/**
	 * @return aryLblLimit
	 */
	public String[] getAryLblLimit() {
		return getStringArrayClone(aryLblLimit);
	}
	
	/**
	 * @param aryLblLimit セットする aryLblLimit
	 */
	public void setAryLblLimit(String[] aryLblLimit) {
		this.aryLblLimit = getStringArrayClone(aryLblLimit);
	}
	
	/**
	 * @return aryLblSpecialHolidayName
	 */
	public String[] getAryLblSpecialHolidayName() {
		return getStringArrayClone(aryLblSpecialHolidayName);
	}
	
	/**
	 * @param aryLblSpecialHolidayName セットする aryLblSpecialHolidayName
	 */
	public void setAryLblSpecialHolidayName(String[] aryLblSpecialHolidayName) {
		this.aryLblSpecialHolidayName = getStringArrayClone(aryLblSpecialHolidayName);
	}
	
	/**
	 * @return lblTotalDay
	 */
	public String getLblTotalDay() {
		return lblTotalDay;
	}
	
	/**
	 * @param lblTotalDay セットする lblTotalDay
	 */
	public void setLblTotalDay(String lblTotalDay) {
		this.lblTotalDay = lblTotalDay;
	}
	
	/**
	 * @return lblTotalTime
	 */
	public String getLblTotalTime() {
		return lblTotalTime;
	}
	
	/**
	 * @param lblTotalTime セットする lblTotalTime
	 */
	public void setLblTotalTime(String lblTotalTime) {
		this.lblTotalTime = lblTotalTime;
	}
	
	/**
	 * @return pltSearchHolidayType
	 */
	public String getPltSearchHolidayType() {
		return pltSearchHolidayType;
	}
	
	/**
	 * @param pltSearchHolidayType セットする pltSearchHolidayType
	 */
	public void setPltSearchHolidayType(String pltSearchHolidayType) {
		this.pltSearchHolidayType = pltSearchHolidayType;
	}
	
	/**
	 * @return aryHolidayType1
	 */
	public String[] getAryHolidayType1() {
		return getStringArrayClone(aryHolidayType1);
	}
	
	/**
	 * @param aryHolidayType1 セットする aryHolidayType1
	 */
	public void setAryHolidayType1(String[] aryHolidayType1) {
		this.aryHolidayType1 = getStringArrayClone(aryHolidayType1);
	}
	
	/**
	 * @return aryHolidayType2
	 */
	public String[] getAryHolidayType2() {
		return getStringArrayClone(aryHolidayType2);
	}
	
	/**
	 * @param aryHolidayType2 セットする aryHolidayType2
	 */
	public void setAryHolidayType2(String[] aryHolidayType2) {
		this.aryHolidayType2 = getStringArrayClone(aryHolidayType2);
	}
	
	/**
	 * @return aryHolidayType3
	 */
	public String[] getAryHolidayType3() {
		return getStringArrayClone(aryHolidayType3);
	}
	
	/**
	 * @param aryHolidayType3 セットする aryHolidayType3
	 */
	public void setAryHolidayType3(String[] aryHolidayType3) {
		this.aryHolidayType3 = getStringArrayClone(aryHolidayType3);
	}
	
	/**
	 * @return aryStartTime
	 */
	public String[] getAryStartTime() {
		return getStringArrayClone(aryStartTime);
	}
	
	/**
	 * @param aryStartTime セットする aryStartTime
	 */
	public void setAryStartTime(String[] aryStartTime) {
		this.aryStartTime = getStringArrayClone(aryStartTime);
	}
	
	/**
	 * @return aryLblOnOff
	 */
	public String[] getAryLblOnOff() {
		return getStringArrayClone(aryLblOnOff);
	}
	
	/**
	 * @param aryLblOnOff セットする aryLblOnOff
	 */
	public void setAryLblOnOff(String[] aryLblOnOff) {
		this.aryLblOnOff = getStringArrayClone(aryLblOnOff);
	}
	
	/**
	 * @return recordId
	 */
	public long getRecordId() {
		return recordId;
	}
	
	/**
	 * @param recordId セットする recordId
	 */
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}
	
	/**
	 * @return pltEditStatusWithPay
	 */
	public String getPltEditStatusWithPay() {
		return pltEditStatusWithPay;
	}
	
	/**
	 * @param pltEditStatusWithPay セットする pltEditStatusWithPay
	 */
	public void setPltEditStatusWithPay(String pltEditStatusWithPay) {
		this.pltEditStatusWithPay = pltEditStatusWithPay;
	}
	
	/**
	 * @return pltEditStatusSpecial
	 */
	public String getPltEditStatusSpecial() {
		return pltEditStatusSpecial;
	}
	
	/**
	 * @param pltEditStatusSpecial セットする pltEditStatusSpecial
	 */
	public void setPltEditStatusSpecial(String pltEditStatusSpecial) {
		this.pltEditStatusSpecial = pltEditStatusSpecial;
	}
	
	/**
	 * @return pltEditSpecialOther
	 */
	public String getPltEditSpecialOther() {
		return pltEditSpecialOther;
	}
	
	/**
	 * @param pltEditSpecialOther セットする pltEditSpecialOther
	 */
	public void setPltEditSpecialOther(String pltEditSpecialOther) {
		this.pltEditSpecialOther = pltEditSpecialOther;
	}
	
	/**
	 * @return pltEditSpecialAbsence
	 */
	public String getPltEditSpecialAbsence() {
		return pltEditSpecialAbsence;
	}
	
	/**
	 * @param pltEditSpecialAbsence セットする pltEditSpecialAbsence
	 */
	public void setPltEditSpecialAbsence(String pltEditSpecialAbsence) {
		this.pltEditSpecialAbsence = pltEditSpecialAbsence;
	}
	
	/**
	 * @return pltEditHolidayRange
	 */
	public String getPltEditHolidayRange() {
		return pltEditHolidayRange;
	}
	
	/**
	 * @param pltEditHolidayRange セットする pltEditHolidayRange
	 */
	public void setPltEditHolidayRange(String pltEditHolidayRange) {
		this.pltEditHolidayRange = pltEditHolidayRange;
	}
	
	/**
	 * @return pltEditHolidayRangePaidHoliday
	 */
	public String getPltEditHolidayRangePaidHoliday() {
		return pltEditHolidayRangePaidHoliday;
	}
	
	/**
	 * @param pltEditHolidayRangePaidHoliday セットする pltEditHolidayRangePaidHoliday
	 */
	public void setPltEditHolidayRangePaidHoliday(String pltEditHolidayRangePaidHoliday) {
		this.pltEditHolidayRangePaidHoliday = pltEditHolidayRangePaidHoliday;
	}
	
	/**
	 * @return aryPltEditHolidayRange
	 */
	public String[][] getAryPltEditHolidayRange() {
		return getStringArrayClone(aryPltEditHolidayRange);
	}
	
	/**
	 * @param aryPltEditHolidayRange セットする aryPltEditHolidayRange
	 */
	public void setAryPltEditHolidayRange(String[][] aryPltEditHolidayRange) {
		this.aryPltEditHolidayRange = getStringArrayClone(aryPltEditHolidayRange);
	}
	
	/**
	 * @return aryPltEditHolidayRangePaidHoliday
	 */
	public String[][] getAryPltEditHolidayRangePaidHoliday() {
		return getStringArrayClone(aryPltEditHolidayRangePaidHoliday);
	}
	
	/**
	 * @param aryPltEditHolidayRangePaidHoliday セットする aryPltEditHolidayRangePaidHoliday
	 */
	public void setAryPltEditHolidayRangePaidHoliday(String[][] aryPltEditHolidayRangePaidHoliday) {
		this.aryPltEditHolidayRangePaidHoliday = getStringArrayClone(aryPltEditHolidayRangePaidHoliday);
	}
	
	/**
	 * @return aryPltSearchHolidayRangePaidHoliday
	 */
	public String[][] getAryPltSearchHolidayRangePaidHoliday() {
		return getStringArrayClone(aryPltSearchHolidayRangePaidHoliday);
	}
	
	/**
	 * @param aryPltSearchHolidayRangePaidHoliday セットする aryPltSearchHolidayRangePaidHoliday
	 */
	public void setAryPltSearchHolidayRangePaidHoliday(String[][] aryPltSearchHolidayRangePaidHoliday) {
		this.aryPltSearchHolidayRangePaidHoliday = getStringArrayClone(aryPltSearchHolidayRangePaidHoliday);
	}
	
	/**
	 * @param jsHolidayContinue セットする jsHolidayContinue
	 */
	public void setJsHolidayContinue(String jsHolidayContinue) {
		this.jsHolidayContinue = jsHolidayContinue;
	}
	
	/**
	 * @return jsHolidayContinue
	 */
	public String getJsHolidayContinue() {
		return jsHolidayContinue;
	}
	
	/**
	 * @param jsHolidayRemainDay セットする jsHolidayRemainDay
	 */
	public void setJsHolidayRemainDay(String jsHolidayRemainDay) {
		this.jsHolidayRemainDay = jsHolidayRemainDay;
	}
	
	/**
	 * @return jsHolidayRemainDay
	 */
	public String getJsHolidayRemainDay() {
		return jsHolidayRemainDay;
	}
	
	/**
	 * @param jsHolidayTerm セットする jsHolidayTerm
	 */
	public void setJsHolidayTerm(String jsHolidayTerm) {
		this.jsHolidayTerm = jsHolidayTerm;
	}
	
	/**
	 * @return jsHolidayTerm
	 */
	public String getJsHolidayTerm() {
		return jsHolidayTerm;
	}
	
	/**
	 * @param jsPaidHolidayStock セットする jsPaidHolidayStock
	 */
	public void setJsPaidHolidayStock(String jsPaidHolidayStock) {
		this.jsPaidHolidayStock = jsPaidHolidayStock;
	}
	
	/**
	 * @return jsPaidHolidayStock
	 */
	public String getJsPaidHolidayStock() {
		return jsPaidHolidayStock;
	}
	
	/**
	 * @param jsHolidayTotalTime セットする jsHolidayTotalTime
	 */
	public void setJsHolidayTotalTime(String jsHolidayTotalTime) {
		this.jsHolidayTotalTime = jsHolidayTotalTime;
	}
	
	/**
	 * @return jsHolidayTotalTime
	 */
	public String getJsHolidayTotalTime() {
		return jsHolidayTotalTime;
	}
	
	/**
	 * @param jsHolidayTotalDay セットする jsHolidayTotalDay
	 */
	public void setJsHolidayTotalDay(String jsHolidayTotalDay) {
		this.jsHolidayTotalDay = jsHolidayTotalDay;
	}
	
	/**
	 * @return jsHolidayTotalDay
	 */
	public String getJsHolidayTotalDay() {
		return jsHolidayTotalDay;
	}
	
	/**
	 * @param idx インデックス
	 * @return aryWorkflow
	 */
	public long getAryWorkflow(int idx) {
		return aryWorkflow[idx];
	}
	
	/**
	 * @param aryWorkflow セットする aryWorkflow
	 */
	public void setAryWorkflow(long[] aryWorkflow) {
		this.aryWorkflow = getLongArrayClone(aryWorkflow);
	}
	
	/**
	 * @return aryWorkflowStatus
	 */
	public String[] getAryWorkflowStatus() {
		return getStringArrayClone(aryWorkflowStatus);
	}
	
	/**
	 * @param aryWorkflowStatus セットする aryWorkflowStatus
	 */
	public void setAryWorkflowStatus(String[] aryWorkflowStatus) {
		this.aryWorkflowStatus = getStringArrayClone(aryWorkflowStatus);
	}
	
	/**
	 * @return pltSearchStatusWithPay
	 */
	public String getPltSearchStatusWithPay() {
		return pltSearchStatusWithPay;
	}
	
	/**
	 * @param pltSearchStatusWithPay セットする pltSearchStatusWithPay
	 */
	public void setPltSearchStatusWithPay(String pltSearchStatusWithPay) {
		this.pltSearchStatusWithPay = pltSearchStatusWithPay;
	}
	
	/**
	 * @return pltSearchStatusSpecial
	 */
	public String getPltSearchStatusSpecial() {
		return pltSearchStatusSpecial;
	}
	
	/**
	 * @param pltSearchStatusSpecial セットする pltSearchStatusSpecial
	 */
	public void setPltSearchStatusSpecial(String pltSearchStatusSpecial) {
		this.pltSearchStatusSpecial = pltSearchStatusSpecial;
	}
	
	/**
	 * @return pltSearchSpecialOther
	 */
	public String getPltSearchSpecialOther() {
		return pltSearchSpecialOther;
	}
	
	/**
	 * @param pltSearchSpecialOther セットする pltSearchSpecialOther
	 */
	public void setPltSearchSpecialOther(String pltSearchSpecialOther) {
		this.pltSearchSpecialOther = pltSearchSpecialOther;
	}
	
	/**
	 * @return pltSearchSpecialAbsence
	 */
	public String getPltSearchSpecialAbsence() {
		return pltSearchSpecialAbsence;
	}
	
	/**
	 * @param pltSearchSpecialAbsence セットする pltSearchSpecialAbsence
	 */
	public void setPltSearchSpecialAbsence(String pltSearchSpecialAbsence) {
		this.pltSearchSpecialAbsence = pltSearchSpecialAbsence;
	}
	
	/**
	 * @return pltSearchHolidayRange1
	 */
	public String getPltSearchHolidayRange1() {
		return pltSearchHolidayRange1;
	}
	
	/**
	 * @param pltSearchHolidayRange1 セットする pltSearchHolidayRange1
	 */
	public void setPltSearchHolidayRange1(String pltSearchHolidayRange1) {
		this.pltSearchHolidayRange1 = pltSearchHolidayRange1;
	}
	
	/**
	 * @return isPaidLeaveByHour
	 */
	public boolean isPaidLeaveByHour() {
		return isPaidLeaveByHour;
	}
	
	/**
	 * @param isPaidLeaveByHour セットする isPaidLeaveByHour
	 */
	public void setPaidLeaveByHour(boolean isPaidLeaveByHour) {
		this.isPaidLeaveByHour = isPaidLeaveByHour;
	}
	
	/**
	 * @return jsPaidHolidayReasonRequired
	 */
	public boolean isJsPaidHolidayReasonRequired() {
		return jsPaidHolidayReasonRequired;
	}
	
	/**
	 * @param jsPaidHolidayReasonRequired セットする jsPaidHolidayReasonRequired
	 */
	public void setJsPaidHolidayReasonRequired(boolean jsPaidHolidayReasonRequired) {
		this.jsPaidHolidayReasonRequired = jsPaidHolidayReasonRequired;
	}
	
	/**
	 * @return jsAddExtraField
	 */
	public String getJsAddExtraField() {
		return jsAddExtraField;
	}
	
	/**
	 * @param jsAddExtraField セットする jsAddExtraField
	 */
	public void setJsAddExtraField(String jsAddExtraField) {
		this.jsAddExtraField = jsAddExtraField;
	}
	
	/**
	 * @return aryLblStyle
	 */
	public String[] getAryLblStyle() {
		return getStringArrayClone(aryLblStyle);
	}
	
	/**
	 * @param aryLblStyle セットする aryLblStyle
	 */
	public void setAryLblStyle(String[] aryLblStyle) {
		this.aryLblStyle = getStringArrayClone(aryLblStyle);
	}
	
}
