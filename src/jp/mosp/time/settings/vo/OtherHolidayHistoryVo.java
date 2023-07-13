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
 * その他休暇付与情報を格納する。
 */
public class OtherHolidayHistoryVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= -2039427592523412318L;
	
	private String				txtEditEmployeeCode;
	private String				pltEditHolidayType;
	private String				txtEditHolidayGiving;
	private String				txtEditHolidayLimitMonth;
	private String				txtEditHolidayLimitDay;
	private String				pltEditInactivate;
	
	private String				txtSearchEmployeeName;
	private String				pltSearchWorkPlace;
	private String				pltSearchEmployment;
	private String				pltSearchSection;
	private String				pltSearchPosition;
	private String				pltSearchInactivate;
	
	private String				pltUpdateInactivate;
	
	private String[]			aryLblActivateDate;
	private String[]			aryLblEmployeeName;
	private String[]			aryLblSection;
	private String[]			aryLblHolidayType;
	private String[]			aryLblHolidayCodeName;
	private String[]			aryLblHolidayGiving;
	private String[]			aryLblHolidayLimit;
	private String[]			aryLblInactivate;
	
	private String[]			aryLblEmployeeCode;
	private String[]			aryLblHolidayCode;
	
	private String[][]			aryPltEditHolidayType;
	private String[][]			aryPltSearchWorkPlace;
	private String[][]			aryPltSearchEmployment;
	private String[][]			aryPltSearchSection;
	private String[][]			aryPltSearchPosition;
	
	private String				jsEditActivateDate;
	private String				jsSearchActivateDate;
	private String				jsEditHistoryMode;
	private Boolean				jsEditNoLimit;
	
	private long				tmdHolidayId;
	
	
	/**
	 * @return txtEditEmployeeCode
	 */
	public String getTxtEditEmployeeCode() {
		return txtEditEmployeeCode;
	}
	
	/**
	 * @param txtEditEmployeeCode セットする txtEditEmployeeCode
	 */
	public void setTxtEditEmployeeCode(String txtEditEmployeeCode) {
		this.txtEditEmployeeCode = txtEditEmployeeCode;
	}
	
	/**
	 * @return pltEditHolidayType
	 */
	public String getPltEditHolidayType() {
		return pltEditHolidayType;
	}
	
	/**
	 * @param pltEditHolidayType セットする pltEditHolidayType
	 */
	public void setPltEditHolidayType(String pltEditHolidayType) {
		this.pltEditHolidayType = pltEditHolidayType;
	}
	
	/**
	 * @return txtEditHolidayGiving
	 */
	public String getTxtEditHolidayGiving() {
		return txtEditHolidayGiving;
	}
	
	/**
	 * @param txtEditHolidayGiving セットする txtEditHolidayGiving
	 */
	public void setTxtEditHolidayGiving(String txtEditHolidayGiving) {
		this.txtEditHolidayGiving = txtEditHolidayGiving;
	}
	
	/**
	 * @return txtEditHolidayLimitMonth
	 */
	public String getTxtEditHolidayLimitMonth() {
		return txtEditHolidayLimitMonth;
	}
	
	/**
	 * @param txtEditHolidayLimitMonth セットする txtEditHolidayLimitMonth
	 */
	public void setTxtEditHolidayLimitMonth(String txtEditHolidayLimitMonth) {
		this.txtEditHolidayLimitMonth = txtEditHolidayLimitMonth;
	}
	
	/**
	 * @return txtEditHolidayLimitDay
	 */
	public String getTxtEditHolidayLimitDay() {
		return txtEditHolidayLimitDay;
	}
	
	/**
	 * @param txtEditHolidayLimitDay セットする txtEditHolidayLimitDay
	 */
	public void setTxtEditHolidayLimitDay(String txtEditHolidayLimitDay) {
		this.txtEditHolidayLimitDay = txtEditHolidayLimitDay;
	}
	
	/**
	 * @return pltEditInactivate
	 */
	@Override
	public String getPltEditInactivate() {
		return pltEditInactivate;
	}
	
	/**
	 * @param pltEditInactivate セットする pltEditInactivate
	 */
	@Override
	public void setPltEditInactivate(String pltEditInactivate) {
		this.pltEditInactivate = pltEditInactivate;
	}
	
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
	 * @return aryLblHolidayType
	 */
	public String[] getAryLblHolidayType() {
		return getStringArrayClone(aryLblHolidayType);
	}
	
	/**
	 * @param aryLblHolidayType セットする aryLblHolidayType
	 */
	public void setAryLblHolidayType(String[] aryLblHolidayType) {
		this.aryLblHolidayType = getStringArrayClone(aryLblHolidayType);
	}
	
	/**
	 * @return aryLblHolidayCodeName
	 */
	public String[] getAryLblHolidayCodeName() {
		return getStringArrayClone(aryLblHolidayCodeName);
	}
	
	/**
	 * @param aryLblHolidayCodeName セットする aryLblHolidayCodeName
	 */
	public void setAryLblHolidayCodeName(String[] aryLblHolidayCodeName) {
		this.aryLblHolidayCodeName = getStringArrayClone(aryLblHolidayCodeName);
	}
	
	/**
	 * @return aryLblHolidayGiving
	 */
	public String[] getAryLblHolidayGiving() {
		return getStringArrayClone(aryLblHolidayGiving);
	}
	
	/**
	 * @param aryLblHolidayGiving セットする aryLblHolidayGiving
	 */
	public void setAryLblHolidayGiving(String[] aryLblHolidayGiving) {
		this.aryLblHolidayGiving = getStringArrayClone(aryLblHolidayGiving);
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
	
	/**
	 * @return aryPltEditHolidayType
	 */
	public String[][] getAryPltEditHolidayType() {
		return getStringArrayClone(aryPltEditHolidayType);
	}
	
	/**
	 * @param aryPltEditHolidayType セットする aryPltEditHolidayType
	 */
	public void setAryPltEditHolidayType(String[][] aryPltEditHolidayType) {
		this.aryPltEditHolidayType = getStringArrayClone(aryPltEditHolidayType);
	}
	
	/**
	 * @return jsEditActivateDate
	 */
	public String getJsEditActivateDate() {
		return jsEditActivateDate;
	}
	
	/**
	 * @param jsEditActivateDate セットする jsEditActivateDate
	 */
	public void setJsEditActivateDate(String jsEditActivateDate) {
		this.jsEditActivateDate = jsEditActivateDate;
	}
	
	/**
	 * @return jsSearchActivateDate
	 */
	public String getJsSearchActivateDate() {
		return jsSearchActivateDate;
	}
	
	/**
	 * @param jsSearchActivateDate セットする jsSearchActivateDate
	 */
	public void setJsSearchActivateDate(String jsSearchActivateDate) {
		this.jsSearchActivateDate = jsSearchActivateDate;
	}
	
	/**
	 * @return tmdHolidayId
	 */
	public long getTmdHolidayId() {
		return tmdHolidayId;
	}
	
	/**
	 * @param tmdHolidayId セットする tmdHolidayId
	 */
	public void setTmdHolidayId(long tmdHolidayId) {
		this.tmdHolidayId = tmdHolidayId;
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
	 * @return jsEditHistoryMode
	 */
	public String getJsEditHistoryMode() {
		return jsEditHistoryMode;
	}
	
	/**
	 * @param jsEditHistoryMode セットする jsEditHistoryMode
	 */
	public void setJsEditHistoryMode(String jsEditHistoryMode) {
		this.jsEditHistoryMode = jsEditHistoryMode;
	}
	
	/**
	 * @return jsEditNoLimit
	 */
	public Boolean getJsEditNoLimit() {
		return jsEditNoLimit;
	}
	
	/**
	 * @param jsEditNoLimit セットする jsEditNoLimit
	 */
	public void setJsEditNoLimit(Boolean jsEditNoLimit) {
		this.jsEditNoLimit = jsEditNoLimit;
	}
}
