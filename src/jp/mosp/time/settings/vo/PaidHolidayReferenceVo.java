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
 * 個別有給休暇確認情報を格納する。
 */
public class PaidHolidayReferenceVo extends TimeVo {
	
	private static final long	serialVersionUID	= -4704386519167555580L;
	
	private String				lblSystemDate;
	private String				pltSelectYear;
	private String				lblFormerDate;
	private String				lblFormerTime;
	private String				lblDate;
	private String				lblTime;
	private String				lblGivingDate;
	private String				lblGivingTime;
	private String				lblCancelDate;
	private String				lblCancelTime;
	private String				lblUseDate;
	private String				lblUseTime;
	
	private String[]			aryCkbPaidHolidayReferenceId;
	
	private String				lblEmployeeCode;
	private String				lblEmployeeName;
	private String				lblSectionName;
	private String				txtLblPreviousYear;
	
	private String				txtActiveDate;
	
	private String[]			aryLblViewYearMonth;
	private String[]			aryLblFormerDate;
	private String[]			aryLblFormerTime;
	private String[]			aryLblDate;
	private String[]			aryLblTime;
	private String[]			aryLblGivingDate;
	private String[]			aryLblGivingTime;
	private String[]			aryLblCancelDate;
	private String[]			aryLblCancelTime;
	private String[]			aryLblUseDate;
	private String[]			aryLblUseTime;
	private String[][]			aryPltSelectYear;
	
	
	/**
	 * @return lblFormerDate
	 */
	public String getLblFormerDate() {
		return lblFormerDate;
	}
	
	/**
	 * @param lblFormerDate セットする lblFormerDate
	 */
	public void setLblFormerDate(String lblFormerDate) {
		this.lblFormerDate = lblFormerDate;
	}
	
	/**
	 * @return lblFormerTime
	 */
	public String getLblFormerTime() {
		return lblFormerTime;
	}
	
	/**
	 * @param lblFormerTime セットする lblFormerTime
	 */
	public void setLblFormerTime(String lblFormerTime) {
		this.lblFormerTime = lblFormerTime;
	}
	
	/**
	 * @return lblDate
	 */
	public String getLblDate() {
		return lblDate;
	}
	
	/**
	 * @param lblDate セットする lblDate
	 */
	public void setLblDate(String lblDate) {
		this.lblDate = lblDate;
	}
	
	/**
	 * @return lblTime
	 */
	public String getLblTime() {
		return lblTime;
	}
	
	/**
	 * @param lblTime セットする lblTime
	 */
	public void setLblTime(String lblTime) {
		this.lblTime = lblTime;
	}
	
	/**
	 * @return lblGivingDate
	 */
	public String getLblGivingDate() {
		return lblGivingDate;
	}
	
	/**
	 * @param lblGivingDate セットする lblGivingDate
	 */
	public void setLblGivingDate(String lblGivingDate) {
		this.lblGivingDate = lblGivingDate;
	}
	
	/**
	 * @return lblGivingTime
	 */
	public String getLblGivingTime() {
		return lblGivingTime;
	}
	
	/**
	 * @param lblGivingTime セットする lblGivingTime
	 */
	public void setLblGivingTime(String lblGivingTime) {
		this.lblGivingTime = lblGivingTime;
	}
	
	/**
	 * @return lblCancelDate
	 */
	public String getLblCancelDate() {
		return lblCancelDate;
	}
	
	/**
	 * @param lblCancelDate セットする lblCancelDate
	 */
	public void setLblCancelDate(String lblCancelDate) {
		this.lblCancelDate = lblCancelDate;
	}
	
	/**
	 * @return lblCancelTime
	 */
	public String getLblCancelTime() {
		return lblCancelTime;
	}
	
	/**
	 * @param lblCancelTime セットする lblCancelTime
	 */
	public void setLblCancelTime(String lblCancelTime) {
		this.lblCancelTime = lblCancelTime;
	}
	
	/**
	 * @return lblUseDate
	 */
	public String getLblUseDate() {
		return lblUseDate;
	}
	
	/**
	 * @param lblUseDate セットする lblUseDate
	 */
	public void setLblUseDate(String lblUseDate) {
		this.lblUseDate = lblUseDate;
	}
	
	/**
	 * @return lblUseTime
	 */
	public String getLblUseTime() {
		return lblUseTime;
	}
	
	/**
	 * @param lblUseTime セットする lblUseTime
	 */
	public void setLblUseTime(String lblUseTime) {
		this.lblUseTime = lblUseTime;
	}
	
	/**
	 * @return aryCkbPaidHolidayReferenceId
	 */
	public String[] getAryCkbPaidHolidayReferenceId() {
		return getStringArrayClone(aryCkbPaidHolidayReferenceId);
	}
	
	/**
	 * @param aryCkbPaidHolidayReferenceId セットする aryCkbPaidHolidayReferenceId
	 */
	public void setAryCkbPaidHolidayReferenceId(String[] aryCkbPaidHolidayReferenceId) {
		this.aryCkbPaidHolidayReferenceId = getStringArrayClone(aryCkbPaidHolidayReferenceId);
	}
	
	/**
	 * @return lblEmployeeCode
	 */
	@Override
	public String getLblEmployeeCode() {
		return lblEmployeeCode;
	}
	
	/**
	 * @param lblEmployeeCode セットする lblEmployeeCode
	 */
	@Override
	public void setLblEmployeeCode(String lblEmployeeCode) {
		this.lblEmployeeCode = lblEmployeeCode;
	}
	
	/**
	 * @return lblEmployeeName
	 */
	@Override
	public String getLblEmployeeName() {
		return lblEmployeeName;
	}
	
	/**
	 * @param lblEmployeeName セットする lblEmployeeName
	 */
	@Override
	public void setLblEmployeeName(String lblEmployeeName) {
		this.lblEmployeeName = lblEmployeeName;
	}
	
	/**
	 * @return lblSection
	 */
	@Override
	public String getLblSectionName() {
		return lblSectionName;
	}
	
	/**
	 * @param lblSection セットする lblSection
	 */
	@Override
	public void setLblSectionName(String lblSection) {
		lblSectionName = lblSection;
	}
	
	/**
	 * @return txtLblPreviousYear
	 */
	public String getTxtLblPreviousYear() {
		return txtLblPreviousYear;
	}
	
	/**
	 * @param txtLblPreviousYear セットする txtLblPreviousYear
	 */
	public void setTxtLblPreviousYear(String txtLblPreviousYear) {
		this.txtLblPreviousYear = txtLblPreviousYear;
	}
	
	/**
	 * @return txtActiveDate
	 */
	public String getTxtActiveDate() {
		return txtActiveDate;
	}
	
	/**
	 * @param txtActiveDate セットする txtActiveDate
	 */
	public void setTxtActiveDate(String txtActiveDate) {
		this.txtActiveDate = txtActiveDate;
	}
	
	/**
	 * @return aryLblViewYearMonth
	 */
	public String[] getAryLblViewYearMonth() {
		return getStringArrayClone(aryLblViewYearMonth);
	}
	
	/**
	 * @return aryLblFormerDate
	 */
	public String[] getAryLblFormerDate() {
		return getStringArrayClone(aryLblFormerDate);
	}
	
	/**
	 * @return aryLblFormerTime
	 */
	public String[] getAryLblFormerTime() {
		return getStringArrayClone(aryLblFormerTime);
	}
	
	/**
	 * @return aryLblDate
	 */
	public String[] getAryLblDate() {
		return getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @return aryLblTime
	 */
	public String[] getAryLblTime() {
		return getStringArrayClone(aryLblTime);
	}
	
	/**
	 * @return aryLblGivingDate
	 */
	public String[] getAryLblGivingDate() {
		return getStringArrayClone(aryLblGivingDate);
	}
	
	/**
	 * @return aryLblGivingTime
	 */
	public String[] getAryLblGivingTime() {
		return getStringArrayClone(aryLblGivingTime);
	}
	
	/**
	 * @return aryLblCancelDate
	 */
	public String[] getAryLblCancelDate() {
		return getStringArrayClone(aryLblCancelDate);
	}
	
	/**
	 * @return aryLblCancelTime
	 */
	public String[] getAryLblCancelTime() {
		return getStringArrayClone(aryLblCancelTime);
	}
	
	/**
	 * @return aryLblUseDate
	 */
	public String[] getAryLblUseDate() {
		return getStringArrayClone(aryLblUseDate);
	}
	
	/**
	 * @return aryLblUseTime
	 */
	public String[] getAryLblUseTime() {
		return getStringArrayClone(aryLblUseTime);
	}
	
	/**
	 * @param aryLblViewYearMonth セットする aryLblViewYearMonth
	 */
	public void setAryLblViewYearMonth(String[] aryLblViewYearMonth) {
		this.aryLblViewYearMonth = getStringArrayClone(aryLblViewYearMonth);
	}
	
	/**
	 * @param aryLblFormerDate セットする aryLblFormerDate
	 */
	public void setAryLblFormerDate(String[] aryLblFormerDate) {
		this.aryLblFormerDate = getStringArrayClone(aryLblFormerDate);
	}
	
	/**
	 * @param aryLblFormerTime セットする aryLblFormerTime
	 */
	public void setAryLblFormerTime(String[] aryLblFormerTime) {
		this.aryLblFormerTime = getStringArrayClone(aryLblFormerTime);
	}
	
	/**
	 * @param aryLblDate セットする aryLblDate
	 */
	public void setAryLblDate(String[] aryLblDate) {
		this.aryLblDate = getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @param aryLblTime セットする aryLblTime
	 */
	public void setAryLblTime(String[] aryLblTime) {
		this.aryLblTime = getStringArrayClone(aryLblTime);
	}
	
	/**
	 * @param aryLblGivingDate セットする aryLblGivingDate
	 */
	public void setAryLblGivingDate(String[] aryLblGivingDate) {
		this.aryLblGivingDate = getStringArrayClone(aryLblGivingDate);
	}
	
	/**
	 * @param aryLblGivingTime セットする aryLblGivingTime
	 */
	public void setAryLblGivingTime(String[] aryLblGivingTime) {
		this.aryLblGivingTime = getStringArrayClone(aryLblGivingTime);
	}
	
	/**
	 * @param aryLblCancelDate セットする aryLblCancelDate
	 */
	public void setAryLblCancelDate(String[] aryLblCancelDate) {
		this.aryLblCancelDate = getStringArrayClone(aryLblCancelDate);
	}
	
	/**
	 * @param aryLblCancelTime セットする aryLblCancelTime
	 */
	public void setAryLblCancelTime(String[] aryLblCancelTime) {
		this.aryLblCancelTime = getStringArrayClone(aryLblCancelTime);
	}
	
	/**
	 * @param aryLblUseDate セットする aryLblUseDate
	 */
	public void setAryLblUseDate(String[] aryLblUseDate) {
		this.aryLblUseDate = getStringArrayClone(aryLblUseDate);
	}
	
	/**
	 * @param aryLblUseTime セットする aryLblUseTime
	 */
	public void setAryLblUseTime(String[] aryLblUseTime) {
		this.aryLblUseTime = getStringArrayClone(aryLblUseTime);
	}
	
	/**
	 * @return pltSelectYear
	 */
	public String getPltSelectYear() {
		return pltSelectYear;
	}
	
	/**
	 * @return aryPltSelectYear
	 */
	public String[][] getAryPltSelectYear() {
		return getStringArrayClone(aryPltSelectYear);
	}
	
	/**
	 * @param pltSelectYear セットする pltSelectYear
	 */
	public void setPltSelectYear(String pltSelectYear) {
		this.pltSelectYear = pltSelectYear;
	}
	
	/**
	 * @param aryPltSelectYear セットする aryPltSelectYear
	 */
	public void setAryPltSelectYear(String[][] aryPltSelectYear) {
		this.aryPltSelectYear = getStringArrayClone(aryPltSelectYear);
	}
	
	/**
	 * @return lblSystemDate
	 */
	public String getLblSystemDate() {
		return lblSystemDate;
	}
	
	/**
	 * @param lblSystemDate セットする lblSystemDate
	 */
	public void setLblSystemDate(String lblSystemDate) {
		this.lblSystemDate = lblSystemDate;
	}
	
}
