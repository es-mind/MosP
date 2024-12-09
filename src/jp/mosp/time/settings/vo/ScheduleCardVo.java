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
package jp.mosp.time.settings.vo;

import java.util.Date;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * カレンダ情報の個別詳細情報の確認、編集を行う。
 */
public class ScheduleCardVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= 4390062924818299873L;
	
	private String				modePattern;
	
	private String				pltFiscalYear;
	private String				pltPattern;
	private String				txtScheduleCode;
	private String				txtScheduleName;
	private String				txtScheduleAbbr;
	private String				pltWorkTypeChange;
	
	private String				pltWorkType;
	private String				radioWeek;
	private String				radioPeriod;
	private String				ckbMonday;
	private String				ckbTuesday;
	private String				ckbWednesday;
	private String				ckbThursday;
	private String				ckbFriday;
	private String				ckbSatureday;
	private String				ckbSunday;
	private String				ckbNationalHoliday;
	private String				pltScheduleStartDay;
	private String				pltScheduleEndDay;
	private String				radioCheck;
	private String				radioSelect;
	
	private String				lblFiscalYear;
	
	private String[]			pltWorkTypeMonth;
	private String[]			txtRemarkMonth;
	private String[]			aryLblMonth;
	private String[]			aryLblStartMonth;
	private String[]			aryLblEndMonth;
	private String[]			aryLblWorkMonth;
	
	private String[][]			aryPltFiscalYear;
	private String[][]			aryPltPattern;
	private String[][]			aryPltWorkType;
	private String[][]			aryPltScheduleDay;
	private String[][]			aryPltWorkTypeMonth;
	
	private String				buttonBackColorJanuary;
	private String				buttonBackColorFebruary;
	private String				buttonBackColorMarch;
	private String				buttonBackColorApril;
	private String				buttonBackColorMay;
	private String				buttonBackColorJune;
	private String				buttonBackColorJuly;
	private String				buttonBackColorAugust;
	private String				buttonBackColorSeptember;
	private String				buttonBackColorOctorber;
	private String				buttonBackColorNovember;
	private String				buttonBackColorDecember;
	
	private String				jsCopyModeEdit;
	private String				copyFiscalYear;
	private String				copyScheduleCode;
	
	/**
	 * 対象年月(編集中の年月)。<br>
	 */
	private Date				targetMonth;
	
	
	/**
	 * @return modePattern
	 */
	public String getModePattern() {
		return modePattern;
	}
	
	/**
	 * @param modePattern セットする modePattern
	 */
	public void setModePattern(String modePattern) {
		this.modePattern = modePattern;
	}
	
	/**
	 * @return pltFiscalYear
	 */
	public String getPltFiscalYear() {
		return pltFiscalYear;
	}
	
	/**
	 * @param pltFiscalYear セットする pltFiscalYear
	 */
	public void setPltFiscalYear(String pltFiscalYear) {
		this.pltFiscalYear = pltFiscalYear;
	}
	
	/**
	 * @return pltPattern
	 */
	public String getPltPattern() {
		return pltPattern;
	}
	
	/**
	 * @param pltPattern セットする pltPattern
	 */
	public void setPltPattern(String pltPattern) {
		this.pltPattern = pltPattern;
	}
	
	/**
	 * @return txtScheduleCode
	 */
	public String getTxtScheduleCode() {
		return txtScheduleCode;
	}
	
	/**
	 * @param txtScheduleCode セットする txtScheduleCode
	 */
	public void setTxtScheduleCode(String txtScheduleCode) {
		this.txtScheduleCode = txtScheduleCode;
	}
	
	/**
	 * @return txtScheduleName
	 */
	public String getTxtScheduleName() {
		return txtScheduleName;
	}
	
	/**
	 * @param txtScheduleName セットする txtScheduleName
	 */
	public void setTxtScheduleName(String txtScheduleName) {
		this.txtScheduleName = txtScheduleName;
	}
	
	/**
	 * @return txtScheduleAbbr
	 */
	public String getTxtScheduleAbbr() {
		return txtScheduleAbbr;
	}
	
	/**
	 * @param txtScheduleAbbr セットする txtScheduleAbbr
	 */
	public void setTxtScheduleAbbr(String txtScheduleAbbr) {
		this.txtScheduleAbbr = txtScheduleAbbr;
	}
	
	/**
	 * @return pltWorkTypeChange
	 */
	public String getPltWorkTypeChange() {
		return pltWorkTypeChange;
	}
	
	/**
	 * @param pltWorkTypeChange セットする pltWorkTypeChange
	 */
	public void setPltWorkTypeChange(String pltWorkTypeChange) {
		this.pltWorkTypeChange = pltWorkTypeChange;
	}
	
	/**
	 * @return pltWorkType
	 */
	public String getPltWorkType() {
		return pltWorkType;
	}
	
	/**
	 * @param pltWorkType セットする pltWorkType
	 */
	public void setPltWorkType(String pltWorkType) {
		this.pltWorkType = pltWorkType;
	}
	
	/**
	 * @return radioWeek
	 */
	public String getRadioWeek() {
		return radioWeek;
	}
	
	/**
	 * @param radioWeek セットする radioWeek
	 */
	public void setRadioWeek(String radioWeek) {
		this.radioWeek = radioWeek;
	}
	
	/**
	 * @return radioPeriod
	 */
	public String getRadioPeriod() {
		return radioPeriod;
	}
	
	/**
	 * @param radioPeriod セットする radioPeriod
	 */
	public void setRadioPeriod(String radioPeriod) {
		this.radioPeriod = radioPeriod;
	}
	
	/**
	 * @return ckbMonday
	 */
	public String getCkbMonday() {
		return ckbMonday;
	}
	
	/**
	 * @param ckbMonday セットする ckbMonday
	 */
	public void setCkbMonday(String ckbMonday) {
		this.ckbMonday = ckbMonday;
	}
	
	/**
	 * @return ckbTuesday
	 */
	public String getCkbTuesday() {
		return ckbTuesday;
	}
	
	/**
	 * @param ckbTuesday セットする ckbTuesday
	 */
	public void setCkbTuesday(String ckbTuesday) {
		this.ckbTuesday = ckbTuesday;
	}
	
	/**
	 * @return ckbWednesday
	 */
	public String getCkbWednesday() {
		return ckbWednesday;
	}
	
	/**
	 * @param ckbWednesday セットする ckbWednesday
	 */
	public void setCkbWednesday(String ckbWednesday) {
		this.ckbWednesday = ckbWednesday;
	}
	
	/**
	 * @return ckbThursday
	 */
	public String getCkbThursday() {
		return ckbThursday;
	}
	
	/**
	 * @param ckbThursday セットする ckbThursday
	 */
	public void setCkbThursday(String ckbThursday) {
		this.ckbThursday = ckbThursday;
	}
	
	/**
	 * @return ckbFriday
	 */
	public String getCkbFriday() {
		return ckbFriday;
	}
	
	/**
	 * @param ckbFriday セットする ckbFriday
	 */
	public void setCkbFriday(String ckbFriday) {
		this.ckbFriday = ckbFriday;
	}
	
	/**
	 * @return ckbSatureday
	 */
	public String getCkbSatureday() {
		return ckbSatureday;
	}
	
	/**
	 * @param ckbSatureday セットする ckbSatureday
	 */
	public void setCkbSatureday(String ckbSatureday) {
		this.ckbSatureday = ckbSatureday;
	}
	
	/**
	 * @return ckbSunday
	 */
	public String getCkbSunday() {
		return ckbSunday;
	}
	
	/**
	 * @param ckbSunday セットする ckbSunday
	 */
	public void setCkbSunday(String ckbSunday) {
		this.ckbSunday = ckbSunday;
	}
	
	/**
	 * @return ckbNationalHoliday
	 */
	public String getCkbNationalHoliday() {
		return ckbNationalHoliday;
	}
	
	/**
	 * @param ckbNationalHoliday セットする ckbNationalHoliday
	 */
	public void setCkbNationalHoliday(String ckbNationalHoliday) {
		this.ckbNationalHoliday = ckbNationalHoliday;
	}
	
	/**
	 * @return pltScheduleStartDay
	 */
	public String getPltScheduleStartDay() {
		return pltScheduleStartDay;
	}
	
	/**
	 * @param pltScheduleStartDay セットする pltScheduleStartDay
	 */
	public void setPltScheduleStartDay(String pltScheduleStartDay) {
		this.pltScheduleStartDay = pltScheduleStartDay;
	}
	
	/**
	 * @return pltScheduleEndDay
	 */
	public String getPltScheduleEndDay() {
		return pltScheduleEndDay;
	}
	
	/**
	 * @param pltScheduleEndDay セットする pltScheduleEndDay
	 */
	public void setPltScheduleEndDay(String pltScheduleEndDay) {
		this.pltScheduleEndDay = pltScheduleEndDay;
	}
	
	/**
	 * @return radioCheck
	 */
	public String getRadioCheck() {
		return radioCheck;
	}
	
	/**
	 * @param radioCheck セットする radioCheck
	 */
	public void setRadioCheck(String radioCheck) {
		this.radioCheck = radioCheck;
	}
	
	/**
	 * @return lblFiscalYear
	 */
	public String getLblFiscalYear() {
		return lblFiscalYear;
	}
	
	/**
	 * @param lblFiscalYear セットする lblFiscalYear
	 */
	public void setLblFiscalYear(String lblFiscalYear) {
		this.lblFiscalYear = lblFiscalYear;
	}
	
	/**
	 * @return pltWorkTypeMonth
	 */
	public String[] getPltWorkTypeMonth() {
		return getStringArrayClone(pltWorkTypeMonth);
	}
	
	/**
	 * @param pltWorkTypeMonth セットする pltWorkTypeMonth
	 */
	public void setPltWorkTypeMonth(String[] pltWorkTypeMonth) {
		this.pltWorkTypeMonth = getStringArrayClone(pltWorkTypeMonth);
	}
	
	/**
	 * @return txtRemarkMonth
	 */
	public String[] getTxtRemarkMonth() {
		return getStringArrayClone(txtRemarkMonth);
	}
	
	/**
	 * @param txtRemarkMonth セットする txtRemarkMonth
	 */
	public void setTxtRemarkMonth(String[] txtRemarkMonth) {
		this.txtRemarkMonth = getStringArrayClone(txtRemarkMonth);
	}
	
	/**
	 * @return aryLblMonth
	 */
	public String[] getAryLblMonth() {
		return getStringArrayClone(aryLblMonth);
	}
	
	/**
	 * @param aryLblMonth セットする aryLblMonth
	 */
	public void setAryLblMonth(String[] aryLblMonth) {
		this.aryLblMonth = getStringArrayClone(aryLblMonth);
	}
	
	/**
	 * @return aryLblStartMonth
	 */
	public String[] getAryLblStartMonth() {
		return getStringArrayClone(aryLblStartMonth);
	}
	
	/**
	 * @param aryLblStartMonth セットする aryLblStartMonth
	 */
	public void setAryLblStartMonth(String[] aryLblStartMonth) {
		this.aryLblStartMonth = getStringArrayClone(aryLblStartMonth);
	}
	
	/**
	 * @return aryLblEndMonth
	 */
	public String[] getAryLblEndMonth() {
		return getStringArrayClone(aryLblEndMonth);
	}
	
	/**
	 * @param aryLblEndMonth セットする aryLblEndMonth
	 */
	public void setAryLblEndMonth(String[] aryLblEndMonth) {
		this.aryLblEndMonth = getStringArrayClone(aryLblEndMonth);
	}
	
	/**
	 * @return aryLblWorkMonth
	 */
	public String[] getAryLblWorkMonth() {
		return getStringArrayClone(aryLblWorkMonth);
	}
	
	/**
	 * @param aryLblWorkMonth セットする aryLblWorkMonth
	 */
	public void setAryLblWorkMonth(String[] aryLblWorkMonth) {
		this.aryLblWorkMonth = getStringArrayClone(aryLblWorkMonth);
	}
	
	/**
	 * @return aryPltFiscalYear
	 */
	public String[][] getAryPltFiscalYear() {
		return getStringArrayClone(aryPltFiscalYear);
	}
	
	/**
	 * @param aryPltFiscalYear セットする aryPltFiscalYear
	 */
	public void setAryPltFiscalYear(String[][] aryPltFiscalYear) {
		this.aryPltFiscalYear = getStringArrayClone(aryPltFiscalYear);
	}
	
	/**
	 * @return aryPltPattern
	 */
	public String[][] getAryPltPattern() {
		return getStringArrayClone(aryPltPattern);
	}
	
	/**
	 * @param aryPltPattern セットする aryPltPattern
	 */
	public void setAryPltPattern(String[][] aryPltPattern) {
		this.aryPltPattern = getStringArrayClone(aryPltPattern);
	}
	
	/**
	 * @return aryPltWorkType
	 */
	public String[][] getAryPltWorkType() {
		return getStringArrayClone(aryPltWorkType);
	}
	
	/**
	 * @param aryPltWorkType セットする aryPltWorkType
	 */
	public void setAryPltWorkType(String[][] aryPltWorkType) {
		this.aryPltWorkType = getStringArrayClone(aryPltWorkType);
	}
	
	/**
	 * @return aryPltScheduleDay
	 */
	public String[][] getAryPltScheduleDay() {
		return getStringArrayClone(aryPltScheduleDay);
	}
	
	/**
	 * @param aryPltScheduleDay セットする aryPltScheduleDay
	 */
	public void setAryPltScheduleDay(String[][] aryPltScheduleDay) {
		this.aryPltScheduleDay = getStringArrayClone(aryPltScheduleDay);
	}
	
	/**
	 * @return aryPltWorkTypeMonth
	 */
	public String[][] getAryPltWorkTypeMonth() {
		return getStringArrayClone(aryPltWorkTypeMonth);
	}
	
	/**
	 * @param aryPltWorkTypeMonth セットする aryPltWorkTypeMonth
	 */
	public void setAryPltWorkTypeMonth(String[][] aryPltWorkTypeMonth) {
		this.aryPltWorkTypeMonth = getStringArrayClone(aryPltWorkTypeMonth);
	}
	
	/**
	 * @return radioSelect
	 */
	public String getRadioSelect() {
		return radioSelect;
	}
	
	/**
	 * @param radioSelect セットする radioSelect
	 */
	public void setRadioSelect(String radioSelect) {
		this.radioSelect = radioSelect;
	}
	
	/**
	 * @return buttonBackColorJanuary
	 */
	public String getButtonBackColorJanuary() {
		return buttonBackColorJanuary;
	}
	
	/**
	 * @param buttonBackColorJanuary セットする buttonBackColorJanuary
	 */
	public void setButtonBackColorJanuary(String buttonBackColorJanuary) {
		this.buttonBackColorJanuary = buttonBackColorJanuary;
	}
	
	/**
	 * @return buttonBackColorFebruary
	 */
	public String getButtonBackColorFebruary() {
		return buttonBackColorFebruary;
	}
	
	/**
	 * @param buttonBackColorFebruary セットする buttonBackColorFebruary
	 */
	public void setButtonBackColorFebruary(String buttonBackColorFebruary) {
		this.buttonBackColorFebruary = buttonBackColorFebruary;
	}
	
	/**
	 * @return buttonBackColorMarch
	 */
	public String getButtonBackColorMarch() {
		return buttonBackColorMarch;
	}
	
	/**
	 * @param buttonBackColorMarch セットする buttonBackColorMarch
	 */
	public void setButtonBackColorMarch(String buttonBackColorMarch) {
		this.buttonBackColorMarch = buttonBackColorMarch;
	}
	
	/**
	 * @return buttonBackColorApril
	 */
	public String getButtonBackColorApril() {
		return buttonBackColorApril;
	}
	
	/**
	 * @param buttonBackColorApril セットする buttonBackColorApril
	 */
	public void setButtonBackColorApril(String buttonBackColorApril) {
		this.buttonBackColorApril = buttonBackColorApril;
	}
	
	/**
	 * @return buttonBackColorMay
	 */
	public String getButtonBackColorMay() {
		return buttonBackColorMay;
	}
	
	/**
	 * @param buttonBackColorMay セットする buttonBackColorMay
	 */
	public void setButtonBackColorMay(String buttonBackColorMay) {
		this.buttonBackColorMay = buttonBackColorMay;
	}
	
	/**
	 * @return buttonBackColorJune
	 */
	public String getButtonBackColorJune() {
		return buttonBackColorJune;
	}
	
	/**
	 * @param buttonBackColorJune セットする buttonBackColorJune
	 */
	public void setButtonBackColorJune(String buttonBackColorJune) {
		this.buttonBackColorJune = buttonBackColorJune;
	}
	
	/**
	 * @return buttonBackColorJuly
	 */
	public String getButtonBackColorJuly() {
		return buttonBackColorJuly;
	}
	
	/**
	 * @param buttonBackColorJuly セットする buttonBackColorJuly
	 */
	public void setButtonBackColorJuly(String buttonBackColorJuly) {
		this.buttonBackColorJuly = buttonBackColorJuly;
	}
	
	/**
	 * @return buttonBackColorAugust
	 */
	public String getButtonBackColorAugust() {
		return buttonBackColorAugust;
	}
	
	/**
	 * @param buttonBackColorAugust セットする buttonBackColorAugust
	 */
	public void setButtonBackColorAugust(String buttonBackColorAugust) {
		this.buttonBackColorAugust = buttonBackColorAugust;
	}
	
	/**
	 * @return buttonBackColorSeptember
	 */
	public String getButtonBackColorSeptember() {
		return buttonBackColorSeptember;
	}
	
	/**
	 * @param buttonBackColorSeptember セットする buttonBackColorSeptember
	 */
	public void setButtonBackColorSeptember(String buttonBackColorSeptember) {
		this.buttonBackColorSeptember = buttonBackColorSeptember;
	}
	
	/**
	 * @return buttonBackColorOctorber
	 */
	public String getButtonBackColorOctorber() {
		return buttonBackColorOctorber;
	}
	
	/**
	 * @param buttonBackColorOctorber セットする buttonBackColorOctorber
	 */
	public void setButtonBackColorOctorber(String buttonBackColorOctorber) {
		this.buttonBackColorOctorber = buttonBackColorOctorber;
	}
	
	/**
	 * @return buttonBackColorNovember
	 */
	public String getButtonBackColorNovember() {
		return buttonBackColorNovember;
	}
	
	/**
	 * @param buttonBackColorNovember セットする buttonBackColorNovember
	 */
	public void setButtonBackColorNovember(String buttonBackColorNovember) {
		this.buttonBackColorNovember = buttonBackColorNovember;
	}
	
	/**
	 * @return buttonBackColorDecember
	 */
	public String getButtonBackColorDecember() {
		return buttonBackColorDecember;
	}
	
	/**
	 * @param buttonBackColorDecember セットする buttonBackColorDecember
	 */
	public void setButtonBackColorDecember(String buttonBackColorDecember) {
		this.buttonBackColorDecember = buttonBackColorDecember;
	}
	
	/**
	 * @return jsCopyModeEdit
	 */
	public String getJsCopyModeEdit() {
		return jsCopyModeEdit;
	}
	
	/**
	 * @param jsCopyModeEdit セットする jsCopyModeEdit
	 */
	public void setJsCopyModeEdit(String jsCopyModeEdit) {
		this.jsCopyModeEdit = jsCopyModeEdit;
	}
	
	/**
	 * @return copyFiscalYear
	 */
	public String getCopyFiscalYear() {
		return copyFiscalYear;
	}
	
	/**
	 * @param copyFiscalYear セットする copyFiscalYear
	 */
	public void setCopyFiscalYear(String copyFiscalYear) {
		this.copyFiscalYear = copyFiscalYear;
	}
	
	/**
	 * @return copyScheduleCode
	 */
	public String getCopyScheduleCode() {
		return copyScheduleCode;
	}
	
	/**
	 * @param copyScheduleCode セットする copyScheduleCode
	 */
	public void setCopyScheduleCode(String copyScheduleCode) {
		this.copyScheduleCode = copyScheduleCode;
	}
	
	/**
	 * @return targetMonth
	 */
	public Date getTargetMonth() {
		return getDateClone(targetMonth);
	}
	
	/**
	 * @param targetMonth セットする targetMonth
	 */
	public void setTargetMonth(Date targetMonth) {
		this.targetMonth = getDateClone(targetMonth);
	}
	
}
