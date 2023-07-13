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
 * 特別休暇確認情報を格納する。
 */
public class SpecialHolidayManagementVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= 8358388664461618458L;
	
	private String				txtSearchEmployeeName;
	private String				pltSearchWorkPlace;
	private String				pltSearchEmployment;
	private String				pltSearchSection;
	private String				pltSearchPosition;
	
	private String[]			aryLblActivateDate;
	private String[]			aryLblEmployeeName;
	private String[]			aryLblSection;
	private String[]			aryLblHolidayCode;
	private String[]			aryLblHolidayCodeName;
	private String[]			aryLblHolidayRemainder;
	private String[]			aryLblHolidayLimit;
	
	private String[]			aryLblEmployeeCode;
	
	private String[][]			aryPltSearchWorkPlace;
	private String[][]			aryPltSearchEmployment;
	private String[][]			aryPltSearchSection;
	private String[][]			aryPltSearchPosition;
	
	
	/**
	 * @return txtSearchEmployeeName
	 */
	public String getTxtSearchEmployeeName() {
		return txtSearchEmployeeName;
	}
	
	/**
	 * @param txtSearchEmployeeName セットする txtSearchEmployeeName
	 */
	public void setTxtSearchEmployeeName(String txtSearchEmployeeName) {
		this.txtSearchEmployeeName = txtSearchEmployeeName;
	}
	
	/**
	 * @return pltSearchWorkPlace
	 */
	public String getPltSearchWorkPlace() {
		return pltSearchWorkPlace;
	}
	
	/**
	 * @param pltSearchWorkPlace セットする pltSearchWorkPlace
	 */
	public void setPltSearchWorkPlace(String pltSearchWorkPlace) {
		this.pltSearchWorkPlace = pltSearchWorkPlace;
	}
	
	/**
	 * @return pltSearchEmployment
	 */
	public String getPltSearchEmployment() {
		return pltSearchEmployment;
	}
	
	/**
	 * @param pltSearchEmployment セットする pltSearchEmployment
	 */
	public void setPltSearchEmployment(String pltSearchEmployment) {
		this.pltSearchEmployment = pltSearchEmployment;
	}
	
	/**
	 * @return pltSearchSection
	 */
	public String getPltSearchSection() {
		return pltSearchSection;
	}
	
	/**
	 * @param pltSearchSection セットする pltSearchSection
	 */
	public void setPltSearchSection(String pltSearchSection) {
		this.pltSearchSection = pltSearchSection;
	}
	
	/**
	 * @return pltSearchPosition
	 */
	public String getPltSearchPosition() {
		return pltSearchPosition;
	}
	
	/**
	 * @param pltSearchPosition セットする pltSearchPosition
	 */
	public void setPltSearchPosition(String pltSearchPosition) {
		this.pltSearchPosition = pltSearchPosition;
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
	 * @return aryLblEmployeeName
	 */
	public String[] getAryLblEmployeeName() {
		return getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @param aryLblEmployeeName セットする aryLblEmployeeName
	 */
	public void setAryLblEmployeeName(String[] aryLblEmployeeName) {
		this.aryLblEmployeeName = getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @return aryLblSection
	 */
	public String[] getAryLblSection() {
		return getStringArrayClone(aryLblSection);
	}
	
	/**
	 * @param aryLblSection セットする aryLblSection
	 */
	public void setAryLblSection(String[] aryLblSection) {
		this.aryLblSection = getStringArrayClone(aryLblSection);
	}
	
	/**
	 * @return aryLblHolidayCode
	 */
	public String[] getAryLblHolidayCode() {
		return getStringArrayClone(aryLblHolidayCode);
	}
	
	/**
	 * @param aryLblHolidayCode セットする aryLblHolidayCode
	 */
	public void setAryLblHolidayCode(String[] aryLblHolidayCode) {
		this.aryLblHolidayCode = getStringArrayClone(aryLblHolidayCode);
	}
	
	/**
	 * @return aryLblHolidayTypeName
	 */
	public String[] getAryLblHolidayCodeName() {
		return getStringArrayClone(aryLblHolidayCodeName);
	}
	
	/**
	 * @param aryLblHolidayTypeName セットする aryLblHolidayTypeName
	 */
	public void setAryLblHolidayCodeName(String[] aryLblHolidayTypeName) {
		aryLblHolidayCodeName = getStringArrayClone(aryLblHolidayTypeName);
	}
	
	/**
	 * @return aryLblHolidayRemainder
	 */
	public String[] getAryLblHolidayRemainder() {
		return getStringArrayClone(aryLblHolidayRemainder);
	}
	
	/**
	 * @param aryLblHolidayRemainder セットする aryLblHolidayRemainder
	 */
	public void setAryLblHolidayRemainder(String[] aryLblHolidayRemainder) {
		this.aryLblHolidayRemainder = getStringArrayClone(aryLblHolidayRemainder);
	}
	
	/**
	 * @return aryLblHolidayLimit
	 */
	public String[] getAryLblHolidayLimit() {
		return getStringArrayClone(aryLblHolidayLimit);
	}
	
	/**
	 * @param aryLblHolidayLimit セットする aryLblHolidayLimit
	 */
	public void setAryLblHolidayLimit(String[] aryLblHolidayLimit) {
		this.aryLblHolidayLimit = getStringArrayClone(aryLblHolidayLimit);
	}
	
	/**
	 * @return aryLblEmployeeCode
	 */
	public String[] getAryLblEmployeeCode() {
		return getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @param aryLblEmployeeCode セットする aryLblEmployeeCode
	 */
	public void setAryLblEmployeeCode(String[] aryLblEmployeeCode) {
		this.aryLblEmployeeCode = getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @return aryPltSearchWorkPlace
	 */
	public String[][] getAryPltSearchWorkPlace() {
		return getStringArrayClone(aryPltSearchWorkPlace);
	}
	
	/**
	 * @param aryPltSearchWorkPlace セットする aryPltSearchWorkPlace
	 */
	public void setAryPltSearchWorkPlace(String[][] aryPltSearchWorkPlace) {
		this.aryPltSearchWorkPlace = getStringArrayClone(aryPltSearchWorkPlace);
	}
	
	/**
	 * @return aryPltSearchEmployment
	 */
	public String[][] getAryPltSearchEmployment() {
		return getStringArrayClone(aryPltSearchEmployment);
	}
	
	/**
	 * @param aryPltSearchEmployment セットする aryPltSearchEmployment
	 */
	public void setAryPltSearchEmployment(String[][] aryPltSearchEmployment) {
		this.aryPltSearchEmployment = getStringArrayClone(aryPltSearchEmployment);
	}
	
	/**
	 * @return aryPltSearchSection
	 */
	public String[][] getAryPltSearchSection() {
		return getStringArrayClone(aryPltSearchSection);
	}
	
	/**
	 * @param aryPltSearchSection セットする aryPltSearchSection
	 */
	public void setAryPltSearchSection(String[][] aryPltSearchSection) {
		this.aryPltSearchSection = getStringArrayClone(aryPltSearchSection);
	}
	
	/**
	 * @return aryPltSearchPosition
	 */
	public String[][] getAryPltSearchPosition() {
		return getStringArrayClone(aryPltSearchPosition);
	}
	
	/**
	 * @param aryPltSearchPosition セットする aryPltSearchPosition
	 */
	public void setAryPltSearchPosition(String[][] aryPltSearchPosition) {
		this.aryPltSearchPosition = getStringArrayClone(aryPltSearchPosition);
	}
}
