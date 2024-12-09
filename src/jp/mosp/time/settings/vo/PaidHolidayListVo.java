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

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 有給休暇一覧情報を格納する。
 */
public class PaidHolidayListVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= 3280170926646591508L;
	
	private String				txtSearchActivateYear;
	private String				txtSearchActivateMonth;
	private String				txtSearchActivateDay;
	private String				pltSearchPaidHolidayType;
	private String				txtSearchPaidHolidayCode;
	private String				txtSearchPaidHolidayName;
	private String				txtSearchPaidHolidayAbbr;
	private String				pltSearchInactivate;
	
	private String				txtUpdateActivateYear;
	private String				txtUpdateActivateMonth;
	private String				txtUpdateActivateDay;
	private String				pltUpdateInactivate;
	
	private String[]			aryLblActivateDate;
	private String[]			aryLblPaidHolidayType;
	private String[]			aryLblPaidHolidayCode;
	private String[]			aryLblPaidHolidayName;
	private String[]			aryLblPaidHolidayAbbr;
	private String[]			aryLblInactivate;
	private String[]			aryCkbPaidHolidayListId;
	
	private String[][]			aryPltSearchPaidHolidayType;
	
	
	/**
	 * @return txtSearchActivateYear
	 */
	@Override
	public String getTxtSearchActivateYear() {
		return txtSearchActivateYear;
	}
	
	/**
	 * @param txtSearchActivateYear セットする txtSearchActivateYear
	 */
	@Override
	public void setTxtSearchActivateYear(String txtSearchActivateYear) {
		this.txtSearchActivateYear = txtSearchActivateYear;
	}
	
	/**
	 * @return txtSearchActivateMonth
	 */
	@Override
	public String getTxtSearchActivateMonth() {
		return txtSearchActivateMonth;
	}
	
	/**
	 * @param txtSearchActivateMonth セットする txtSearchActivateMonth
	 */
	@Override
	public void setTxtSearchActivateMonth(String txtSearchActivateMonth) {
		this.txtSearchActivateMonth = txtSearchActivateMonth;
	}
	
	/**
	 * @return txtSearchActivateDay
	 */
	@Override
	public String getTxtSearchActivateDay() {
		return txtSearchActivateDay;
	}
	
	/**
	 * @param txtSearchActivateDay セットする txtSearchActivateDay
	 */
	@Override
	public void setTxtSearchActivateDay(String txtSearchActivateDay) {
		this.txtSearchActivateDay = txtSearchActivateDay;
	}
	
	/**
	 * @return pltSearchPaidHolidayType
	 */
	public String getPltSearchPaidHolidayType() {
		return pltSearchPaidHolidayType;
	}
	
	/**
	 * @param pltSearchPaidHolidayType セットする pltSearchPaidHolidayType
	 */
	public void setPltSearchPaidHolidayType(String pltSearchPaidHolidayType) {
		this.pltSearchPaidHolidayType = pltSearchPaidHolidayType;
	}
	
	/**
	 * @return txtSearchPaidHolidayCode
	 */
	public String getTxtSearchPaidHolidayCode() {
		return txtSearchPaidHolidayCode;
	}
	
	/**
	 * @param txtSearchPaidHolidayCode セットする txtSearchPaidHolidayCode
	 */
	public void setTxtSearchPaidHolidayCode(String txtSearchPaidHolidayCode) {
		this.txtSearchPaidHolidayCode = txtSearchPaidHolidayCode;
	}
	
	/**
	 * @return txtSearchPaidHolidayName
	 */
	public String getTxtSearchPaidHolidayName() {
		return txtSearchPaidHolidayName;
	}
	
	/**
	 * @param txtSearchPaidHolidayName セットする txtSearchPaidHolidayName
	 */
	public void setTxtSearchPaidHolidayName(String txtSearchPaidHolidayName) {
		this.txtSearchPaidHolidayName = txtSearchPaidHolidayName;
	}
	
	/**
	 * @return txtSearchPaidHolidayAbbr
	 */
	public String getTxtSearchPaidHolidayAbbr() {
		return txtSearchPaidHolidayAbbr;
	}
	
	/**
	 * @param txtSearchPaidHolidayAbbr セットする txtSearchPaidHolidayAbbr
	 */
	public void setTxtSearchPaidHolidayAbbr(String txtSearchPaidHolidayAbbr) {
		this.txtSearchPaidHolidayAbbr = txtSearchPaidHolidayAbbr;
	}
	
	/**
	 * @return pltSearchInactivate
	 */
	@Override
	public String getPltSearchInactivate() {
		return pltSearchInactivate;
	}
	
	/**
	 * @param pltSearchInactivate セットする pltSearchInactivate
	 */
	@Override
	public void setPltSearchInactivate(String pltSearchInactivate) {
		this.pltSearchInactivate = pltSearchInactivate;
	}
	
	/**
	 * @return txtUpdateActivateYear
	 */
	@Override
	public String getTxtUpdateActivateYear() {
		return txtUpdateActivateYear;
	}
	
	/**
	 * @param txtUpdateActivateYear セットする txtUpdateActivateYear
	 */
	@Override
	public void setTxtUpdateActivateYear(String txtUpdateActivateYear) {
		this.txtUpdateActivateYear = txtUpdateActivateYear;
	}
	
	/**
	 * @return txtUpdateActivateMonth
	 */
	@Override
	public String getTxtUpdateActivateMonth() {
		return txtUpdateActivateMonth;
	}
	
	/**
	 * @param txtUpdateActivateMonth セットする txtUpdateActivateMonth
	 */
	@Override
	public void setTxtUpdateActivateMonth(String txtUpdateActivateMonth) {
		this.txtUpdateActivateMonth = txtUpdateActivateMonth;
	}
	
	/**
	 * @return txtUpdateActivateDay
	 */
	@Override
	public String getTxtUpdateActivateDay() {
		return txtUpdateActivateDay;
	}
	
	/**
	 * @param txtUpdateActivateDay セットする txtUpdateActivateDay
	 */
	@Override
	public void setTxtUpdateActivateDay(String txtUpdateActivateDay) {
		this.txtUpdateActivateDay = txtUpdateActivateDay;
	}
	
	/**
	 * @return pltUpdateInactivate
	 */
	@Override
	public String getPltUpdateInactivate() {
		return pltUpdateInactivate;
	}
	
	/**
	 * @param pltUpdateInactivate セットする pltUpdateInactivate
	 */
	@Override
	public void setPltUpdateInactivate(String pltUpdateInactivate) {
		this.pltUpdateInactivate = pltUpdateInactivate;
	}
	
	/**
	 * @return aryLblActivateDate
	 */
	@Override
	public String[] getAryLblActivateDate() {
		return getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @param aryLblActivateDate セットする aryLblActivateDate
	 */
	@Override
	public void setAryLblActivateDate(String[] aryLblActivateDate) {
		this.aryLblActivateDate = getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @return aryLblPaidHolidayType
	 */
	public String[] getAryLblPaidHolidayType() {
		return getStringArrayClone(aryLblPaidHolidayType);
	}
	
	/**
	 * @param aryLblPaidHolidayType セットする aryLblPaidHolidayType
	 */
	public void setAryLblPaidHolidayType(String[] aryLblPaidHolidayType) {
		this.aryLblPaidHolidayType = getStringArrayClone(aryLblPaidHolidayType);
	}
	
	/**
	 * @return aryLblPaidHolidayCode
	 */
	public String[] getAryLblPaidHolidayCode() {
		return getStringArrayClone(aryLblPaidHolidayCode);
	}
	
	/**
	 * @param aryLblPaidHolidayCode セットする aryLblPaidHolidayCode
	 */
	public void setAryLblPaidHolidayCode(String[] aryLblPaidHolidayCode) {
		this.aryLblPaidHolidayCode = getStringArrayClone(aryLblPaidHolidayCode);
	}
	
	/**
	 * @return aryLblPaidHolidayName
	 */
	public String[] getAryLblPaidHolidayName() {
		return getStringArrayClone(aryLblPaidHolidayName);
	}
	
	/**
	 * @param aryLblPaidHolidayName セットする aryLblPaidHolidayName
	 */
	public void setAryLblPaidHolidayName(String[] aryLblPaidHolidayName) {
		this.aryLblPaidHolidayName = getStringArrayClone(aryLblPaidHolidayName);
	}
	
	/**
	 * @return aryLblPaidHolidayAbbr
	 */
	public String[] getAryLblPaidHolidayAbbr() {
		return getStringArrayClone(aryLblPaidHolidayAbbr);
	}
	
	/**
	 * @param aryLblPaidHolidayAbbr セットする aryLblPaidHolidayAbbr
	 */
	public void setAryLblPaidHolidayAbbr(String[] aryLblPaidHolidayAbbr) {
		this.aryLblPaidHolidayAbbr = getStringArrayClone(aryLblPaidHolidayAbbr);
	}
	
	/**
	 * @return aryLblInactivate
	 */
	@Override
	public String[] getAryLblInactivate() {
		return getStringArrayClone(aryLblInactivate);
	}
	
	/**
	 * @param aryLblInactivate セットする aryLblInactivate
	 */
	@Override
	public void setAryLblInactivate(String[] aryLblInactivate) {
		this.aryLblInactivate = getStringArrayClone(aryLblInactivate);
	}
	
	/**
	 * @return aryCkbPaidHolidayListId
	 */
	public String[] getAryCkbPaidHolidayListId() {
		return getStringArrayClone(aryCkbPaidHolidayListId);
	}
	
	/**
	 * @param aryCkbPaidHolidayListId セットする aryCkbPaidHolidayListId
	 */
	public void setAryCkbPaidHolidayListId(String[] aryCkbPaidHolidayListId) {
		this.aryCkbPaidHolidayListId = getStringArrayClone(aryCkbPaidHolidayListId);
	}
	
	/**
	 * @return aryPltSearchPaidHolidayType
	 */
	public String[][] getAryPltSearchPaidHolidayType() {
		return getStringArrayClone(aryPltSearchPaidHolidayType);
	}
	
	/**
	 * @param aryPltSearchPaidHolidayType セットする aryPltSearchPaidHolidayType
	 */
	public void setAryPltSearchPaidHolidayType(String[][] aryPltSearchPaidHolidayType) {
		this.aryPltSearchPaidHolidayType = getStringArrayClone(aryPltSearchPaidHolidayType);
	}
	
}
