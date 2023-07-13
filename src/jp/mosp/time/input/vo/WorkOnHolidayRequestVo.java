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
package jp.mosp.time.input.vo;

import jp.mosp.time.base.TimeVo;

/**
 * 休日出勤申請情報を格納する。
 */
public class WorkOnHolidayRequestVo extends TimeVo {
	
	private static final long	serialVersionUID	= 7970620763742236881L;
	
	private long				recordId;
	
	private String				pltEditRequestYear;
	private String				pltEditRequestMonth;
	private String				pltEditRequestDay;
	private String				pltEditStartHour;
	private String				pltEditStartMinute;
	private String				pltEditEndHour;
	private String				pltEditEndMinute;
	private String				pltEditSubstitute;
	private String				pltEditSubstituteWorkRange;
	private String				pltEditSubstitute1Range;
	private String				pltEditSubstitute2Range;
	private String				txtEditRequestReason;
	private String				pltEditSubstitute1Year;
	private String				pltEditSubstitute1Month;
	private String				pltEditSubstitute1Day;
	private String				pltEditSubstitute2Year;
	private String				pltEditSubstitute2Month;
	private String				pltEditSubstitute2Day;
	private String				pltEditWorkType;
	
	private String				pltSearchSubstitute;
	private String				pltSearchSubstituteRange;
	private String				pltSearchState;
	private String				pltSearchRequestYear;
	private String				pltSearchRequestMonth;
	
	private String[]			aryCkbWorkOnHolidayRequestListId;
	private String[]			aryLblWorkDate;
	private String[]			aryLblRequestTime;
	private String[]			aryLblRequestReason;
	private String[]			aryLblSubstitute;
	private String[]			aryLblSubstituteDate1;
	private String[]			aryLblSubstituteDate2;
	private String[]			aryLblState;
	private String[]			aryStateStyle;
	private String[]			aryLblApprover;
	private String[]			aryLblOnOff;
	
	private String[][]			aryPltEditRequestYear;
	private String[][]			aryPltEditRequestMonth;
	private String[][]			aryPltEditRequestDay;
	private String[][]			aryPltEditStartHour;
	private String[][]			aryPltEditStartMinute;
	private String[][]			aryPltEditEndHour;
	private String[][]			aryPltEditEndMinute;
	private String[][]			aryPltEditSubstitute1Year;
	private String[][]			aryPltEditSubstitute1Month;
	private String[][]			aryPltEditSubstitute1Day;
	private String[][]			aryPltEditSubstitute1Range;
	private String[][]			aryPltEditSubstitute2Year;
	private String[][]			aryPltEditSubstitute2Month;
	private String[][]			aryPltEditSubstitute2Day;
	private String[][]			aryPltEditSubstitute2Range;
	private String[][]			aryPltEditWorkType;
	
	private String[][]			aryPltSearchSubstituteRange;
	private String[][]			aryPltSearchState;
	private String[][]			aryPltSearchRequestYear;
	private String[][]			aryPltSearchRequestMonth;
	
	private String				jsModeLegalHoliday;
	private String				jsModeWorkPlanFlag;
	
	/**
	 * ワークフロー番号。<br>
	 */
	private long[]				aryWorkflow;
	private String[]			aryWorkflowStatus;
	
	/**
	 * 半日振替出勤利用設定(true：利用、false：利用しない)。<br>
	 */
	private boolean				modeHalfSubstitute;
	
	/**
	 * 追加フィールド。
	 * アドオンで処理をする。
	 */
	private String				jsAddExtraField;
	
	
	/**
	 * @return pltEditRequestYear
	 */
	public String getPltEditRequestYear() {
		return pltEditRequestYear;
	}
	
	/**
	 * @param pltEditRequestYear セットする pltEditRequestYear
	 */
	public void setPltEditRequestYear(String pltEditRequestYear) {
		this.pltEditRequestYear = pltEditRequestYear;
	}
	
	/**
	 * @return pltEditRequestMonth
	 */
	public String getPltEditRequestMonth() {
		return pltEditRequestMonth;
	}
	
	/**
	 * @param pltEditRequestMonth セットする pltEditRequestMonth
	 */
	public void setPltEditRequestMonth(String pltEditRequestMonth) {
		this.pltEditRequestMonth = pltEditRequestMonth;
	}
	
	/**
	 * @return pltEditRequestDay
	 */
	public String getPltEditRequestDay() {
		return pltEditRequestDay;
	}
	
	/**
	 * @param pltEditRequestDay セットする pltEditRequestDay
	 */
	public void setPltEditRequestDay(String pltEditRequestDay) {
		this.pltEditRequestDay = pltEditRequestDay;
	}
	
	/**
	 * @return pltEditStartHour
	 */
	public String getPltEditStartHour() {
		return pltEditStartHour;
	}
	
	/**
	 * @param pltEditStartHour セットする pltEditStartHour
	 */
	public void setPltEditStartHour(String pltEditStartHour) {
		this.pltEditStartHour = pltEditStartHour;
	}
	
	/**
	 * @return pltEditStartMinute
	 */
	public String getPltEditStartMinute() {
		return pltEditStartMinute;
	}
	
	/**
	 * @param pltEditStartMinute セットする pltEditStartMinute
	 */
	public void setPltEditStartMinute(String pltEditStartMinute) {
		this.pltEditStartMinute = pltEditStartMinute;
	}
	
	/**
	 * @return pltEditEndHour
	 */
	public String getPltEditEndHour() {
		return pltEditEndHour;
	}
	
	/**
	 * @param pltEditEndHour セットする pltEditEndHour
	 */
	public void setPltEditEndHour(String pltEditEndHour) {
		this.pltEditEndHour = pltEditEndHour;
	}
	
	/**
	 * @return pltEditEndMinute
	 */
	public String getPltEditEndMinute() {
		return pltEditEndMinute;
	}
	
	/**
	 * @param pltEditEndMinute セットする pltEditEndMinute
	 */
	public void setPltEditEndMinute(String pltEditEndMinute) {
		this.pltEditEndMinute = pltEditEndMinute;
	}
	
	/**
	 * @return pltEditSubstitute
	 */
	public String getPltEditSubstitute() {
		return pltEditSubstitute;
	}
	
	/**
	 * @param pltEditSubstitute セットする pltEditSubstitute
	 */
	public void setPltEditSubstitute(String pltEditSubstitute) {
		this.pltEditSubstitute = pltEditSubstitute;
	}
	
	/**
	 * @return pltEditSubstituteWorkRange
	 */
	public String getPltEditSubstituteWorkRange() {
		return pltEditSubstituteWorkRange;
	}
	
	/**
	 * @param pltEditSubstituteWorkRange セットする pltEditSubstituteWorkRange
	 */
	public void setPltEditSubstituteWorkRange(String pltEditSubstituteWorkRange) {
		this.pltEditSubstituteWorkRange = pltEditSubstituteWorkRange;
	}
	
	/**
	 * @return pltEditSubstitute1Range
	 */
	public String getPltEditSubstitute1Range() {
		return pltEditSubstitute1Range;
	}
	
	/**
	 * @param pltEditSubstitute1Range セットする pltEditSubstitute1Range
	 */
	public void setPltEditSubstitute1Range(String pltEditSubstitute1Range) {
		this.pltEditSubstitute1Range = pltEditSubstitute1Range;
	}
	
	/**
	 * @return pltEditSubstitute2Range
	 */
	public String getPltEditSubstitute2Range() {
		return pltEditSubstitute2Range;
	}
	
	/**
	 * @param pltEditSubstitute2Range セットする pltEditSubstitute2Range
	 */
	public void setPltEditSubstitute2Range(String pltEditSubstitute2Range) {
		this.pltEditSubstitute2Range = pltEditSubstitute2Range;
	}
	
	/**
	 * @return txtEditRequestReason
	 */
	public String getTxtEditRequestReason() {
		return txtEditRequestReason;
	}
	
	/**
	 * @param txtEditRequestReason セットする txtEditRequestReason
	 */
	public void setTxtEditRequestReason(String txtEditRequestReason) {
		this.txtEditRequestReason = txtEditRequestReason;
	}
	
	/**
	 * @return pltEditSubstitute1Year
	 */
	public String getPltEditSubstitute1Year() {
		return pltEditSubstitute1Year;
	}
	
	/**
	 * @param pltEditSubstitute1Year セットする pltEditSubstitute1Year
	 */
	public void setPltEditSubstitute1Year(String pltEditSubstitute1Year) {
		this.pltEditSubstitute1Year = pltEditSubstitute1Year;
	}
	
	/**
	 * @return pltEditSubstitute1Month
	 */
	public String getPltEditSubstitute1Month() {
		return pltEditSubstitute1Month;
	}
	
	/**
	 * @param pltEditSubstitute1Month セットする pltEditSubstitute1Month
	 */
	public void setPltEditSubstitute1Month(String pltEditSubstitute1Month) {
		this.pltEditSubstitute1Month = pltEditSubstitute1Month;
	}
	
	/**
	 * @return pltEditSubstitute1Day
	 */
	public String getPltEditSubstitute1Day() {
		return pltEditSubstitute1Day;
	}
	
	/**
	 * @param pltEditSubstitute1Day セットする pltEditSubstitute1Day
	 */
	public void setPltEditSubstitute1Day(String pltEditSubstitute1Day) {
		this.pltEditSubstitute1Day = pltEditSubstitute1Day;
	}
	
	/**
	 * @return pltEditSubstitute2Year
	 */
	public String getPltEditSubstitute2Year() {
		return pltEditSubstitute2Year;
	}
	
	/**
	 * @param pltEditSubstitute2Year セットする pltEditSubstitute2Year
	 */
	public void setPltEditSubstitute2Year(String pltEditSubstitute2Year) {
		this.pltEditSubstitute2Year = pltEditSubstitute2Year;
	}
	
	/**
	 * @return pltEditSubstitute2Month
	 */
	public String getPltEditSubstitute2Month() {
		return pltEditSubstitute2Month;
	}
	
	/**
	 * @param pltEditSubstitute2Month セットする pltEditSubstitute2Month
	 */
	public void setPltEditSubstitute2Month(String pltEditSubstitute2Month) {
		this.pltEditSubstitute2Month = pltEditSubstitute2Month;
	}
	
	/**
	 * @return pltEditSubstitute2Day
	 */
	public String getPltEditSubstitute2Day() {
		return pltEditSubstitute2Day;
	}
	
	/**
	 * @param pltEditSubstitute2Day セットする pltEditSubstitute2Day
	 */
	public void setPltEditSubstitute2Day(String pltEditSubstitute2Day) {
		this.pltEditSubstitute2Day = pltEditSubstitute2Day;
	}
	
	/**
	 * @return pltEditWorkType
	 */
	public String getPltEditWorkType() {
		return pltEditWorkType;
	}
	
	/**
	 * @param pltEditWorkType セットする pltEditWorkType
	 */
	public void setPltEditWorkType(String pltEditWorkType) {
		this.pltEditWorkType = pltEditWorkType;
	}
	
	/**
	 * @return pltSearchSubstitute
	 */
	public String getPltSearchSubstitute() {
		return pltSearchSubstitute;
	}
	
	/**
	 * @param pltSearchSubstitute セットする pltSearchSubstitute
	 */
	public void setPltSearchSubstitute(String pltSearchSubstitute) {
		this.pltSearchSubstitute = pltSearchSubstitute;
	}
	
	/**
	 * @return pltSearchSubstituteRange
	 */
	public String getPltSearchSubstituteRange() {
		return pltSearchSubstituteRange;
	}
	
	/**
	 * @param pltSearchSubstituteRange セットする pltSearchSubstituteRange
	 */
	public void setPltSearchSubstituteRange(String pltSearchSubstituteRange) {
		this.pltSearchSubstituteRange = pltSearchSubstituteRange;
	}
	
	/**
	 * @return pltSearchState
	 */
	public String getPltSearchState() {
		return pltSearchState;
	}
	
	/**
	 * @param pltSearchState セットする pltSearchState
	 */
	public void setPltSearchState(String pltSearchState) {
		this.pltSearchState = pltSearchState;
	}
	
	/**
	 * @return pltSearchRequestYear
	 */
	public String getPltSearchRequestYear() {
		return pltSearchRequestYear;
	}
	
	/**
	 * @param pltSearchRequestYear セットする pltSearchRequestYear
	 */
	public void setPltSearchRequestYear(String pltSearchRequestYear) {
		this.pltSearchRequestYear = pltSearchRequestYear;
	}
	
	/**
	 * @return pltSearchRequestMonth
	 */
	public String getPltSearchRequestMonth() {
		return pltSearchRequestMonth;
	}
	
	/**
	 * @param pltSearchRequestMonth セットする pltSearchRequestMonth
	 */
	public void setPltSearchRequestMonth(String pltSearchRequestMonth) {
		this.pltSearchRequestMonth = pltSearchRequestMonth;
	}
	
	/**
	 * @return aryLblWorkDate
	 */
	public String[] getAryLblWorkDate() {
		return getStringArrayClone(aryLblWorkDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkDate
	 */
	public String getAryLblWorkDate(int idx) {
		return aryLblWorkDate[idx];
	}
	
	/**
	 * @param aryLblWorkDate セットする aryLblWorkDate
	 */
	public void setAryLblWorkDate(String[] aryLblWorkDate) {
		this.aryLblWorkDate = getStringArrayClone(aryLblWorkDate);
	}
	
	/**
	 * @return aryLblRequestTime
	 */
	public String[] getAryLblRequestTime() {
		return getStringArrayClone(aryLblRequestTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRequestTime
	 */
	public String getAryLblRequestTime(int idx) {
		return aryLblRequestTime[idx];
	}
	
	/**
	 * @param aryLblRequestTime セットする aryLblRequestTime
	 */
	public void setAryLblRequestTime(String[] aryLblRequestTime) {
		this.aryLblRequestTime = getStringArrayClone(aryLblRequestTime);
	}
	
	/**
	 * @return aryLblRequestReason
	 */
	public String[] getAryLblRequestReason() {
		return getStringArrayClone(aryLblRequestReason);
	}
	
	/**
	 * @param idx インデックス
	 * @return getAryLblCutoffName
	 */
	public String getAryLblRequestReason(int idx) {
		return aryLblRequestReason[idx];
	}
	
	/**
	 * @param aryLblRequestReason セットする aryLblRequestReason
	 */
	public void setAryLblRequestReason(String[] aryLblRequestReason) {
		this.aryLblRequestReason = getStringArrayClone(aryLblRequestReason);
	}
	
	/**
	 * @return aryLblSubstitute
	 */
	public String[] getAryLblSubstitute() {
		return getStringArrayClone(aryLblSubstitute);
	}
	
	/**
	 * @param aryLblSubstitute セットする aryLblSubstitute
	 */
	public void setAryLblSubstitute(String[] aryLblSubstitute) {
		this.aryLblSubstitute = getStringArrayClone(aryLblSubstitute);
	}
	
	/**
	 * @return aryLblSubstituteDate1
	 */
	public String[] getAryLblSubstituteDate1() {
		return getStringArrayClone(aryLblSubstituteDate1);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblSubstituteDate1
	 */
	public String getAryLblSubstituteDate1(int idx) {
		return aryLblSubstituteDate1[idx];
	}
	
	/**
	 * @param aryLblSubstituteDate1 セットする aryLblSubstituteDate1
	 */
	public void setAryLblSubstituteDate1(String[] aryLblSubstituteDate1) {
		this.aryLblSubstituteDate1 = getStringArrayClone(aryLblSubstituteDate1);
	}
	
	/**
	 * @return aryLblSubstituteDate2
	 */
	public String[] getAryLblSubstituteDate2() {
		return getStringArrayClone(aryLblSubstituteDate2);
	}
	
	/**
	 * @param aryLblSubstituteDate2 セットする aryLblSubstituteDate2
	 */
	public void setAryLblSubstituteWeek(String[] aryLblSubstituteDate2) {
		this.aryLblSubstituteDate2 = getStringArrayClone(aryLblSubstituteDate2);
	}
	
	/**
	 * @return aryLblState
	 */
	public String[] getAryLblState() {
		return getStringArrayClone(aryLblState);
	}
	
	/**
	 * @param aryLblState セットする aryLblState
	 */
	public void setAryLblState(String[] aryLblState) {
		this.aryLblState = getStringArrayClone(aryLblState);
	}
	
	/**
	 * @return aryStateStyle
	 */
	public String[] getAryStateStyle() {
		return getStringArrayClone(aryStateStyle);
	}
	
	/**
	 * @param aryStateStyle セットする aryStateStyle
	 */
	public void setAryStateStyle(String[] aryStateStyle) {
		this.aryStateStyle = getStringArrayClone(aryStateStyle);
	}
	
	/**
	 * @return aryLblApprover
	 */
	public String[] getAryLblApprover() {
		return getStringArrayClone(aryLblApprover);
	}
	
	/**
	 * @param aryLblApprover セットする aryLblApprover
	 */
	public void setAryLblApprover(String[] aryLblApprover) {
		this.aryLblApprover = getStringArrayClone(aryLblApprover);
	}
	
	/**
	 * @return aryPltEditRequestYear
	 */
	public String[][] getAryPltEditRequestYear() {
		return getStringArrayClone(aryPltEditRequestYear);
	}
	
	/**
	 * @param aryPltEditRequestYear セットする aryPltEditRequestYear
	 */
	public void setAryPltEditRequestYear(String[][] aryPltEditRequestYear) {
		this.aryPltEditRequestYear = getStringArrayClone(aryPltEditRequestYear);
	}
	
	/**
	 * @return aryPltEditRequestMonth
	 */
	public String[][] getAryPltEditRequestMonth() {
		return getStringArrayClone(aryPltEditRequestMonth);
	}
	
	/**
	 * @param aryPltEditRequestMonth セットする aryPltEditRequestMonth
	 */
	public void setAryPltEditRequestMonth(String[][] aryPltEditRequestMonth) {
		this.aryPltEditRequestMonth = getStringArrayClone(aryPltEditRequestMonth);
	}
	
	/**
	 * @return aryPltEditRequestDay
	 */
	public String[][] getAryPltEditRequestDay() {
		return getStringArrayClone(aryPltEditRequestDay);
	}
	
	/**
	 * @param aryPltEditRequestDay セットする aryPltEditRequestDay
	 */
	public void setAryPltEditRequestDay(String[][] aryPltEditRequestDay) {
		this.aryPltEditRequestDay = getStringArrayClone(aryPltEditRequestDay);
	}
	
	/**
	 * @return aryPltEditStartHour
	 */
	public String[][] getAryPltEditStartHour() {
		return getStringArrayClone(aryPltEditStartHour);
	}
	
	/**
	 * @param aryPltEditStartHour セットする aryPltEditStartHour
	 */
	public void setAryPltEditStartHour(String[][] aryPltEditStartHour) {
		this.aryPltEditStartHour = getStringArrayClone(aryPltEditStartHour);
	}
	
	/**
	 * @return aryPltEditStartMinute
	 */
	public String[][] getAryPltEditStartMinute() {
		return getStringArrayClone(aryPltEditStartMinute);
	}
	
	/**
	 * @param aryPltEditStartMinute セットする aryPltEditStartMinute
	 */
	public void setAryPltEditStartMinute(String[][] aryPltEditStartMinute) {
		this.aryPltEditStartMinute = getStringArrayClone(aryPltEditStartMinute);
	}
	
	/**
	 * @return aryPltEditEndHour
	 */
	public String[][] getAryPltEditEndHour() {
		return getStringArrayClone(aryPltEditEndHour);
	}
	
	/**
	 * @param aryPltEditEndHour セットする aryPltEditEndHour
	 */
	public void setAryPltEditEndHour(String[][] aryPltEditEndHour) {
		this.aryPltEditEndHour = getStringArrayClone(aryPltEditEndHour);
	}
	
	/**
	 * @return aryPltEditEndMinute
	 */
	public String[][] getAryPltEditEndMinute() {
		return getStringArrayClone(aryPltEditEndMinute);
	}
	
	/**
	 * @param aryPltEditEndMinute セットする aryPltEditEndMinute
	 */
	public void setAryPltEditEndMinute(String[][] aryPltEditEndMinute) {
		this.aryPltEditEndMinute = getStringArrayClone(aryPltEditEndMinute);
	}
	
	/**
	 * @return aryPltEditSubstitute1Year
	 */
	public String[][] getAryPltEditSubstitute1Year() {
		return getStringArrayClone(aryPltEditSubstitute1Year);
	}
	
	/**
	 * @param aryPltEditSubstitute1Year セットする aryPltEditSubstitute1Year
	 */
	public void setAryPltEditSubstitute1Year(String[][] aryPltEditSubstitute1Year) {
		this.aryPltEditSubstitute1Year = getStringArrayClone(aryPltEditSubstitute1Year);
	}
	
	/**
	 * @return aryPltEditSubstitute1Month
	 */
	public String[][] getAryPltEditSubstitute1Month() {
		return getStringArrayClone(aryPltEditSubstitute1Month);
	}
	
	/**
	 * @param aryPltEditSubstitute1Month セットする aryPltEditSubstitute1Month
	 */
	public void setAryPltEditSubstitute1Month(String[][] aryPltEditSubstitute1Month) {
		this.aryPltEditSubstitute1Month = getStringArrayClone(aryPltEditSubstitute1Month);
	}
	
	/**
	 * @return aryPltEditSubstitute1Day
	 */
	public String[][] getAryPltEditSubstitute1Day() {
		return getStringArrayClone(aryPltEditSubstitute1Day);
	}
	
	/**
	 * @param aryPltEditSubstitute1Day セットする aryPltEditSubstitute1Day
	 */
	public void setAryPltEditSubstitute1Day(String[][] aryPltEditSubstitute1Day) {
		this.aryPltEditSubstitute1Day = getStringArrayClone(aryPltEditSubstitute1Day);
	}
	
	/**
	 * @return aryPltEditSubstitute1Range
	 */
	public String[][] getAryPltEditSubstitute1Range() {
		return getStringArrayClone(aryPltEditSubstitute1Range);
	}
	
	/**
	 * @param aryPltEditSubstitute1Range セットする aryPltEditSubstitute1Range
	 */
	public void setAryPltEditSubstitute1Range(String[][] aryPltEditSubstitute1Range) {
		this.aryPltEditSubstitute1Range = getStringArrayClone(aryPltEditSubstitute1Range);
	}
	
	/**
	 * @return aryPltEditSubstitute2Year
	 */
	public String[][] getAryPltEditSubstitute2Year() {
		return getStringArrayClone(aryPltEditSubstitute2Year);
	}
	
	/**
	 * @param aryPltEditSubstitute2Year セットする aryPltEditSubstitute2Year
	 */
	public void setAryPltEditSubstitute2Year(String[][] aryPltEditSubstitute2Year) {
		this.aryPltEditSubstitute2Year = getStringArrayClone(aryPltEditSubstitute2Year);
	}
	
	/**
	 * @return aryPltEditSubstitute2Month
	 */
	public String[][] getAryPltEditSubstitute2Month() {
		return getStringArrayClone(aryPltEditSubstitute2Month);
	}
	
	/**
	 * @param aryPltEditSubstitute2Month セットする aryPltEditSubstitute2Month
	 */
	public void setAryPltEditSubstitute2Month(String[][] aryPltEditSubstitute2Month) {
		this.aryPltEditSubstitute2Month = getStringArrayClone(aryPltEditSubstitute2Month);
	}
	
	/**
	 * @return aryPltEditSubstitute2Day
	 */
	public String[][] getAryPltEditSubstitute2Day() {
		return getStringArrayClone(aryPltEditSubstitute2Day);
	}
	
	/**
	 * @param aryPltEditSubstitute2Day セットする aryPltEditSubstitute2Day
	 */
	public void setAryPltEditSubstitute2Day(String[][] aryPltEditSubstitute2Day) {
		this.aryPltEditSubstitute2Day = getStringArrayClone(aryPltEditSubstitute2Day);
	}
	
	/**
	 * @return aryPltEditSubstitute2Range
	 */
	public String[][] getAryPltEditSubstitute2Range() {
		return getStringArrayClone(aryPltEditSubstitute2Range);
	}
	
	/**
	 * @param aryPltEditSubstitute2Range セットする aryPltEditSubstitute2Range
	 */
	public void setAryPltEditSubstitute2Range(String[][] aryPltEditSubstitute2Range) {
		this.aryPltEditSubstitute2Range = getStringArrayClone(aryPltEditSubstitute2Range);
	}
	
	/**
	 * @return aryPltEditWorkType
	 */
	public String[][] getAryPltEditWorkType() {
		return getStringArrayClone(aryPltEditWorkType);
	}
	
	/**
	 * @param aryPltEditWorkType セットする aryPltEditWorkType
	 */
	public void setAryPltEditWorkType(String[][] aryPltEditWorkType) {
		this.aryPltEditWorkType = getStringArrayClone(aryPltEditWorkType);
	}
	
	/**
	 * @return aryPltSearchSubstituteRange
	 */
	public String[][] getAryPltSearchSubstituteRange() {
		return getStringArrayClone(aryPltSearchSubstituteRange);
	}
	
	/**
	 * @param aryPltSearchSubstituteRange セットする aryPltSearchSubstituteRange
	 */
	public void setAryPltSearchSubstituteRange(String[][] aryPltSearchSubstituteRange) {
		this.aryPltSearchSubstituteRange = getStringArrayClone(aryPltSearchSubstituteRange);
	}
	
	/**
	 * @return aryPltSearchState
	 */
	public String[][] getAryPltSearchState() {
		return getStringArrayClone(aryPltSearchState);
	}
	
	/**
	 * @param aryPltSearchState セットする aryPltSearchState
	 */
	public void setAryPltSearchState(String[][] aryPltSearchState) {
		this.aryPltSearchState = getStringArrayClone(aryPltSearchState);
	}
	
	/**
	 * @return aryPltSearchRequestYear
	 */
	public String[][] getAryPltSearchRequestYear() {
		return getStringArrayClone(aryPltSearchRequestYear);
	}
	
	/**
	 * @param aryPltSearchRequestYear セットする aryPltSearchRequestYear
	 */
	public void setAryPltSearchRequestYear(String[][] aryPltSearchRequestYear) {
		this.aryPltSearchRequestYear = getStringArrayClone(aryPltSearchRequestYear);
	}
	
	/**
	 * @return aryPltSearchRequestMonth
	 */
	public String[][] getAryPltSearchRequestMonth() {
		return getStringArrayClone(aryPltSearchRequestMonth);
	}
	
	/**
	 * @param aryPltSearchRequestMonth セットする aryPltSearchRequestMonth
	 */
	public void setAryPltSearchRequestMonth(String[][] aryPltSearchRequestMonth) {
		this.aryPltSearchRequestMonth = getStringArrayClone(aryPltSearchRequestMonth);
	}
	
	/**
	 * @return aryCkbWorkOnHolidayRequestListId
	 */
	public String[] getAryCkbWorkOnHolidayRequestListId() {
		return getStringArrayClone(aryCkbWorkOnHolidayRequestListId);
	}
	
	/**
	 * @param aryCkbWorkOnHolidayRequestListId セットする aryCkbOvertimeRequestListId
	 */
	public void setAryCkbWorkOnHolidayRequestListId(String[] aryCkbWorkOnHolidayRequestListId) {
		this.aryCkbWorkOnHolidayRequestListId = getStringArrayClone(aryCkbWorkOnHolidayRequestListId);
	}
	
	/**
	 * @param aryLblSubstituteDate2 セットする aryLblSubstituteDate2
	 */
	public void setAryLblSubstituteDate2(String[] aryLblSubstituteDate2) {
		this.aryLblSubstituteDate2 = getStringArrayClone(aryLblSubstituteDate2);
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
	 * @return jsModeLegalHoliday
	 */
	public String getJsModeLegalHoliday() {
		return jsModeLegalHoliday;
	}
	
	/**
	 * @param jsModeLegalHoliday セットする jsModeLegalHoliday
	 */
	public void setJsModeLegalHoliday(String jsModeLegalHoliday) {
		this.jsModeLegalHoliday = jsModeLegalHoliday;
	}
	
	/**
	 * @return jsModeWorkPlanFlag
	 */
	public String getJsModeWorkPlanFlag() {
		return jsModeWorkPlanFlag;
	}
	
	/**
	 * @param jsModeWorkPlanFlag セットする jsModeWorkPlanFlag
	 */
	public void setJsModeWorkPlanFlag(String jsModeWorkPlanFlag) {
		this.jsModeWorkPlanFlag = jsModeWorkPlanFlag;
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
	 * @return modeHalfSubstitute
	 */
	public boolean isModeHalfSubstitute() {
		return modeHalfSubstitute;
	}
	
	/**
	 * @param modeHalfSubstitute セットする modeHalfSubstitute
	 */
	public void setModeHalfSubstitute(boolean modeHalfSubstitute) {
		this.modeHalfSubstitute = modeHalfSubstitute;
	}
	
	/**
	 * @return jsModeHoliday
	 */
	public String getJsAddExtraField() {
		return jsAddExtraField;
	}
	
	/**
	 * @param jsFirstWorkDate セットする jsFirstWorkDate
	 */
	public void setJsAddExtraField(String jsFirstWorkDate) {
		jsAddExtraField = jsFirstWorkDate;
	}
	
}
