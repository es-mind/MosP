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
 * 有給休暇手動確認情報を格納する。
 */
public class PaidHolidayManagementVo extends TimeVo {
	
	private static final long	serialVersionUID	= 1995476447953140669L;
	
	private String				txtSearchEmployeeName;
	private String				pltSearchWorkPlace;
	private String				pltSearchEmployment;
	private String				pltSearchSection;
	private String				pltSearchPosition;
	private String				csvActivateDate;
	
	private String[]			aryLblActivateDate;
	private String[]			aryLblEmployeeName;
	private String[]			aryLblEmployeeCode;
	private String[]			aryLblSection;
	private String[]			aryLblFormerDate;
	private String[]			aryLblFormerTime;
	private String[]			aryLblDate;
	private String[]			aryLblTime;
	private String[]			aryLblStockDate;
	private String[]			aryLblInactivate;
	
	private String[][]			aryPltSearchWorkPlace;
	private String[][]			aryPltSearchEmployment;
	private String[][]			aryPltSearchSection;
	private String[][]			aryPltSearchPosition;
	
	/**
	 * 個人ID配列。<br>
	 * 一覧に表示されている個人IDを格納する。<br>
	 * 画面遷移に用いる。<br>
	 */
	private String[]			aryPersonalId;
	
	private boolean				jsSearchConditionRequired;
	
	
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
	 * @return aryLblFormerDate
	 */
	public String[] getAryLblFormerDate() {
		return getStringArrayClone(aryLblFormerDate);
	}
	
	/**
	 * @param aryLblFormerDate セットする aryLblFormerDate
	 */
	public void setAryLblFormerDate(String[] aryLblFormerDate) {
		this.aryLblFormerDate = getStringArrayClone(aryLblFormerDate);
	}
	
	/**
	 * @return aryLblFormerTime
	 */
	public String[] getAryLblFormerTime() {
		return getStringArrayClone(aryLblFormerTime);
	}
	
	/**
	 * @param aryLblFormerTime セットする aryLblFormerTime
	 */
	public void setAryLblFormerTime(String[] aryLblFormerTime) {
		this.aryLblFormerTime = getStringArrayClone(aryLblFormerTime);
	}
	
	/**
	 * @return aryLblDate
	 */
	public String[] getAryLblDate() {
		return getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @param aryLblDate セットする aryLblDate
	 */
	public void setAryLblDate(String[] aryLblDate) {
		this.aryLblDate = getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @return aryLblTime
	 */
	public String[] getAryLblTime() {
		return getStringArrayClone(aryLblTime);
	}
	
	/**
	 * @param aryLblTime セットする aryLblTime
	 */
	public void setAryLblTime(String[] aryLblTime) {
		this.aryLblTime = getStringArrayClone(aryLblTime);
	}
	
	/**
	 * @return aryLblStockDate
	 */
	public String[] getAryLblStockDate() {
		return getStringArrayClone(aryLblStockDate);
	}
	
	/**
	 * @param aryLblStockDate セットする aryLblStockDate
	 */
	public void setAryLblStockDate(String[] aryLblStockDate) {
		this.aryLblStockDate = getStringArrayClone(aryLblStockDate);
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
	 * @param aryPersonalId セットする aryPersonalId
	 */
	public void setAryPersonalId(String[] aryPersonalId) {
		this.aryPersonalId = getStringArrayClone(aryPersonalId);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryPersonalId
	 */
	public String getAryPersonalId(int idx) {
		return aryPersonalId[idx];
	}
	
	/**
	 * @return jsSearchConditionRequired
	 */
	public boolean isJsSearchConditionRequired() {
		return jsSearchConditionRequired;
	}
	
	/**
	 * @param jsSearchConditionRequired セットする jsSearchConditionRequired
	 */
	public void setJsSearchConditionRequired(boolean jsSearchConditionRequired) {
		this.jsSearchConditionRequired = jsSearchConditionRequired;
	}

	/**
	 * @return csvActivateDate
	 */
	public String getCsvActivateDate() {
		return csvActivateDate;
	}

	/**
	 * @param csvActivateDate セットする csvActivateDate
	 */
	public void setCsvActivateDate(String csvActivateDate) {
		this.csvActivateDate = csvActivateDate;
	}
	
}
