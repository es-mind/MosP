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

import jp.mosp.time.base.TimeVo;

/**
 * カレンダ管理一覧情報を格納する。
 */
public class ScheduleListVo extends TimeVo {
	
	private static final long	serialVersionUID	= 8184282607148224322L;
	
	private String				txtSearchActivateYear;
	private String				txtSearchActivateMonth;
	private String				txtSearchActivateDay;
	private String				pltSearchFiscalYear;
	private String				txtSearchScheduleCode;
	private String				txtSearchScheduleName;
	private String				txtSearchScheduleAbbr;
	private String				pltSearchInactivate;
	
	private String[]			aryLblActivateDate;
	private String[]			aryLblFiscalYear;
	private String[]			aryLblScheduleCode;
	private String[]			aryLblScheduleName;
	private String[]			aryLblScheduleAbbr;
	private String[]			aryLblInactivate;
	private String[]			aryCkbScheduleListId;
	
	private String[][]			aryPltSearchFiscalYear;
	
	
	@Override
	public String getTxtSearchActivateYear() {
		return txtSearchActivateYear;
	}
	
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
	 * @return pltSearchFiscalYear
	 */
	public String getPltSearchFiscalYear() {
		return pltSearchFiscalYear;
	}
	
	/**
	 * @param pltSearchFiscalYear セットする pltSearchFiscalYear
	 */
	public void setPltSearchFiscalYear(String pltSearchFiscalYear) {
		this.pltSearchFiscalYear = pltSearchFiscalYear;
	}
	
	/**
	 * @return txtSearchScheduleCode
	 */
	public String getTxtSearchScheduleCode() {
		return txtSearchScheduleCode;
	}
	
	/**
	 * @param txtSearchScheduleCode セットする txtSearchScheduleCode
	 */
	public void setTxtSearchScheduleCode(String txtSearchScheduleCode) {
		this.txtSearchScheduleCode = txtSearchScheduleCode;
	}
	
	/**
	 * @return txtSearchScheduleName
	 */
	public String getTxtSearchScheduleName() {
		return txtSearchScheduleName;
	}
	
	/**
	 * @param txtSearchScheduleName セットする txtSearchScheduleName
	 */
	public void setTxtSearchScheduleName(String txtSearchScheduleName) {
		this.txtSearchScheduleName = txtSearchScheduleName;
	}
	
	/**
	 * @return txtSearchScheduleAbbr
	 */
	public String getTxtSearchScheduleAbbr() {
		return txtSearchScheduleAbbr;
	}
	
	/**
	 * @param txtSearchScheduleAbbr セットする txtSearchScheduleAbbr
	 */
	public void setTxtSearchScheduleAbbr(String txtSearchScheduleAbbr) {
		this.txtSearchScheduleAbbr = txtSearchScheduleAbbr;
	}
	
	/**
	 * @return pltSearchInactivate
	 */
	public String getPltSearchInactivate() {
		return pltSearchInactivate;
	}
	
	/**
	 * @param pltSearchInactivate セットする pltSearchInactivate
	 */
	public void setPltSearchInactivate(String pltSearchInactivate) {
		this.pltSearchInactivate = pltSearchInactivate;
	}
	
	/**
	 * @return aryLblActivateDate
	 */
	public String[] getAryLblActivateDate() {
		return getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @param aryLblActivateDate セットする aryLblActivateDate
	 */
	public void setAryLblActivateDate(String[] aryLblActivateDate) {
		this.aryLblActivateDate = getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @return aryLblFiscalYear
	 */
	public String[] getAryLblFiscalYear() {
		return getStringArrayClone(aryLblFiscalYear);
	}
	
	/**
	 * @param aryLblFiscalYear セットする aryLblFiscalYear
	 */
	public void setAryLblFiscalYear(String[] aryLblFiscalYear) {
		this.aryLblFiscalYear = getStringArrayClone(aryLblFiscalYear);
	}
	
	/**
	 * @return aryLblScheduleCode
	 */
	public String[] getAryLblScheduleCode() {
		return getStringArrayClone(aryLblScheduleCode);
	}
	
	/**
	 * @param aryLblScheduleCode セットする aryLblScheduleCode
	 */
	public void setAryLblScheduleCode(String[] aryLblScheduleCode) {
		this.aryLblScheduleCode = getStringArrayClone(aryLblScheduleCode);
	}
	
	/**
	 * @return aryLblScheduleName
	 */
	public String[] getAryLblScheduleName() {
		return getStringArrayClone(aryLblScheduleName);
	}
	
	/**
	 * @param aryLblScheduleName セットする aryLblScheduleName
	 */
	public void setAryLblScheduleName(String[] aryLblScheduleName) {
		this.aryLblScheduleName = getStringArrayClone(aryLblScheduleName);
	}
	
	/**
	 * @return aryLblScheduleAbbr
	 */
	public String[] getAryLblScheduleAbbr() {
		return getStringArrayClone(aryLblScheduleAbbr);
	}
	
	/**
	 * @param aryLblScheduleAbbr セットする aryLblScheduleAbbr
	 */
	public void setAryLblScheduleAbbr(String[] aryLblScheduleAbbr) {
		this.aryLblScheduleAbbr = getStringArrayClone(aryLblScheduleAbbr);
	}
	
	/**
	 * @return aryLblInactivate
	 */
	public String[] getAryLblInactivate() {
		return getStringArrayClone(aryLblInactivate);
	}
	
	/**
	 * @param aryLblInactivate セットする aryLblInactivate
	 */
	public void setAryLblInactivate(String[] aryLblInactivate) {
		this.aryLblInactivate = getStringArrayClone(aryLblInactivate);
	}
	
	/**
	 * @return aryCkbScheduleListId
	 */
	public String[] getAryCkbScheduleListId() {
		return getStringArrayClone(aryCkbScheduleListId);
	}
	
	/**
	 * @param aryCkbScheduleListId セットする aryCkbScheduleListId
	 */
	public void setAryCkbScheduleListId(String[] aryCkbScheduleListId) {
		this.aryCkbScheduleListId = getStringArrayClone(aryCkbScheduleListId);
	}
	
	/**
	 * @return aryPltSearchFiscalYear
	 */
	public String[][] getAryPltSearchFiscalYear() {
		return getStringArrayClone(aryPltSearchFiscalYear);
	}
	
	/**
	 * @param aryPltSearchFiscalYear セットする aryPltSearchFiscalYear
	 */
	public void setAryPltSearchFiscalYear(String[][] aryPltSearchFiscalYear) {
		this.aryPltSearchFiscalYear = getStringArrayClone(aryPltSearchFiscalYear);
	}
}
