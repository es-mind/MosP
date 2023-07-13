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
package jp.mosp.time.management.vo;

import jp.mosp.time.base.TimeVo;

/**
 * 申請情報確認の情報を格納する。
 */
public class RequestListVo extends TimeVo {
	
	private static final long	serialVersionUID	= 9031899031652897863L;
	
	private String				pltSearchRequestYear;
	private String				pltSearchRequestMonth;
	private String				pltSearchRequestDay;
	private String				pltSearchApprovalType;
	private String				pltSearchState;
	private String				txtSearchEmployeeCode;
	private String				txtSearchEmployeeName;
	private String				pltSearchWorkPlace;
	private String				pltSearchEmployment;
	private String				pltSearchSection;
	private String				pltSearchPosition;
	
	private String[]			aryLblEmployeeCode;
	private String[]			aryLblEmployeeName;
	private String[]			aryLblSection;
	private String[]			aryLblRequestType;
	private String[]			aryLblRequestDate;
	private String[]			aryLblRequestInfo;
	private String[]			aryLblState;
	private String[]			aryStateStyle;
	private String[]			aryRequestTypeCmd;
	private String[]			aryHistoryCmd;
	private String[]			aryRequestFunctionCode;
	private String[]			aryWorkflow;
	private String[]			aryStage;
	private String[]			aryState;
	private String[]			aryBackColor;
	
	private String[][]			aryPltSearchRequestYear;
	private String[][]			aryPltSearchRequestMonth;
	private String[][]			aryPltSearchRequestDay;
	private String[][]			aryPltSearchState;
	private String[][]			aryPltSearchWorkPlace;
	private String[][]			aryPltSearchEmployment;
	private String[][]			aryPltSearchSection;
	private String[][]			aryPltSearchPosition;
	
	private boolean				jsSearchConditionRequired;
	
	
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
	 * @return pltSearchRequestDay
	 */
	public String getPltSearchRequestDay() {
		return pltSearchRequestDay;
	}
	
	/**
	 * @param pltSearchRequestDay セットする pltSearchRequestDay
	 */
	public void setPltSearchRequestDay(String pltSearchRequestDay) {
		this.pltSearchRequestDay = pltSearchRequestDay;
	}
	
	/**
	 * @return pltSearchApprovalType
	 */
	public String getPltSearchApprovalType() {
		return pltSearchApprovalType;
	}
	
	/**
	 * @param pltSearchApprovalType セットする pltSearchApprovalType
	 */
	public void setPltSearchApprovalType(String pltSearchApprovalType) {
		this.pltSearchApprovalType = pltSearchApprovalType;
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
	 * @return txtSearchEmployeeCode
	 */
	@Override
	public String getTxtSearchEmployeeCode() {
		return txtSearchEmployeeCode;
	}
	
	/**
	 * @param txtSearchEmployeeCode セットする txtSearchEmployeeCode
	 */
	@Override
	public void setTxtSearchEmployeeCode(String txtSearchEmployeeCode) {
		this.txtSearchEmployeeCode = txtSearchEmployeeCode;
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
	 * @return aryLblRequestType
	 */
	public String[] getAryLblRequestType() {
		return getStringArrayClone(aryLblRequestType);
	}
	
	/**
	 * @param aryLblRequestType セットする aryLblRequestType
	 */
	public void setAryLblRequestType(String[] aryLblRequestType) {
		this.aryLblRequestType = getStringArrayClone(aryLblRequestType);
	}
	
	/**
	 * @return aryLblRequestDate
	 */
	public String[] getAryLblRequestDate() {
		return getStringArrayClone(aryLblRequestDate);
	}
	
	/**
	 * @param aryLblRequestDate セットする aryLblRequestDate
	 */
	public void setAryLblRequestDate(String[] aryLblRequestDate) {
		this.aryLblRequestDate = getStringArrayClone(aryLblRequestDate);
	}
	
	/**
	 * @return aryLblRequestInfo
	 */
	public String[] getAryLblRequestInfo() {
		return getStringArrayClone(aryLblRequestInfo);
	}
	
	/**
	 * @param aryLblRequestInfo セットする aryLblRequestInfo
	 */
	public void setAryLblRequestInfo(String[] aryLblRequestInfo) {
		this.aryLblRequestInfo = getStringArrayClone(aryLblRequestInfo);
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
	 * @return aryPltSearchRequestDay
	 */
	public String[][] getAryPltSearchRequestDay() {
		return getStringArrayClone(aryPltSearchRequestDay);
	}
	
	/**
	 * @param aryPltSearchRequestDay セットする aryPltSearchRequestDay
	 */
	public void setAryPltSearchRequestDay(String[][] aryPltSearchRequestDay) {
		this.aryPltSearchRequestDay = getStringArrayClone(aryPltSearchRequestDay);
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
	 * @param aryPltSearchPosition セットする arySearchPltPosition
	 */
	public void setAryPltSearchPosition(String[][] aryPltSearchPosition) {
		this.aryPltSearchPosition = getStringArrayClone(aryPltSearchPosition);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryRequestTypeCmd
	 */
	public String getAryRequestTypeCmd(int idx) {
		return aryRequestTypeCmd[idx];
	}
	
	/**
	 * @param aryRequestTypeCmd セットする aryRequestTypeCmd
	 */
	public void setAryRequestTypeCmd(String[] aryRequestTypeCmd) {
		this.aryRequestTypeCmd = getStringArrayClone(aryRequestTypeCmd);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryHistoryCmd
	 */
	public String getAryHistoryCmd(int idx) {
		return aryHistoryCmd[idx];
	}
	
	/**
	 * @param aryHistoryCmd セットする aryHistoryCmd
	 */
	public void setAryHistoryCmd(String[] aryHistoryCmd) {
		this.aryHistoryCmd = getStringArrayClone(aryHistoryCmd);
	}
	
	/**
	 * @return aryRequestFunctionCode
	 */
	public String[] getAryRequestFunctionCode() {
		return getStringArrayClone(aryRequestFunctionCode);
	}
	
	/**
	 * @param aryRequestFunctionCode セットする aryRequestFunctionCode
	 */
	public void setAryRequestFunctionCode(String[] aryRequestFunctionCode) {
		this.aryRequestFunctionCode = getStringArrayClone(aryRequestFunctionCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryWorkflow
	 */
	public String getAryWorkflow(int idx) {
		return aryWorkflow[idx];
	}
	
	/**
	 * @param aryWorkflow セットする aryWorkflow
	 */
	public void setAryWorkflow(String[] aryWorkflow) {
		this.aryWorkflow = getStringArrayClone(aryWorkflow);
	}
	
	/**
	 * @return aryStage
	 */
	public String[] getAryStage() {
		return getStringArrayClone(aryStage);
	}
	
	/**
	 * @param aryStage セットする aryStage
	 */
	public void setAryStage(String[] aryStage) {
		this.aryStage = getStringArrayClone(aryStage);
	}
	
	/**
	 * @return aryState
	 */
	public String[] getAryState() {
		return getStringArrayClone(aryState);
	}
	
	/**
	 * @param aryState セットする aryState
	 */
	public void setAryState(String[] aryState) {
		this.aryState = getStringArrayClone(aryState);
	}
	
	/**
	 * @return aryBackColor
	 */
	public String[] getAryBackColor() {
		return getStringArrayClone(aryBackColor);
	}
	
	/**
	 * @param aryBackColor セットする aryBackColor
	 */
	public void setAryBackColor(String[] aryBackColor) {
		this.aryBackColor = getStringArrayClone(aryBackColor);
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
	
}
