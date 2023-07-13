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
package jp.mosp.platform.workflow.vo;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * 代理登録画面の情報を格納する。
 */
public class SubApproverSettingVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -4762056069147769103L;
	
	/**
	 * 代理承認者登録No。
	 */
	private String				subApproverNo;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 代理終了日(年)。
	 */
	private String				txtEditEndYear;
	
	/**
	 * 代理終了日(月)。
	 */
	private String				txtEditEndMonth;
	
	/**
	 * 代理終了日(日)。
	 */
	private String				txtEditEndDay;
	
	/**
	 * 代理人所属。
	 */
	private String				pltEditSection;
	
	/**
	 * 代理人職位。
	 */
	private String				pltEditPosition;
	
	/**
	 * 社員コード。
	 */
	private String				txtEditEmployeeCode;
	
	/**
	 * 代理人。
	 */
	private String				pltEditEmployeeName;
	
	/**
	 * フロー区分。
	 */
	private String				pltEditWorkflowType;
	
	/**
	 * 所属名称。
	 */
	private String				txtSearchSectionName;
	
	/**
	 * 代理人。
	 */
	private String				txtSearchEmployeeName;
	
	/**
	 * 一覧項目(代理承認者登録No)。<br>
	 */
	private String[]			aryLblSubApproverNo;
	
	/**
	 * 一覧項目(終了日)。<br>
	 */
	private String[]			aryLblEndDate;
	
	/**
	 * 一覧項目(フロー区分)。<br>
	 */
	private String[]			aryLblWorkflowType;
	
	/**
	 * 一覧項目(フロー名称)。<br>
	 */
	private String[]			aryLblWorkflowName;
	
	/**
	 * 一覧項目(社員コード)。<br>
	 */
	private String[]			aryLblEmployeeCode;
	
	/**
	 * 一覧項目(代理人)。<br>
	 */
	private String[]			aryLblEmployeeName;
	
	/**
	 * 一覧項目(所属略称)。<br>
	 */
	private String[]			aryLblSection;
	
	/**
	 * プルダウンリスト(所属)。<br>
	 */
	private String[][]			aryPltEditSection;
	
	/**
	 * プルダウンリスト(職位)。<br>
	 */
	private String[][]			aryPltEditPosition;
	
	/**
	 * プルダウンリスト(代理人)。<br>
	 */
	private String[][]			aryPltEditEmployee;
	
	
	/**
	 * @return subApproverNo
	 */
	public String getSubApproverNo() {
		return subApproverNo;
	}
	
	/**
	 * @param subApproverNo セットする subApproverNo
	 */
	public void setSubApproverNo(String subApproverNo) {
		this.subApproverNo = subApproverNo;
	}
	
	/**
	 * @return personalId
	 */
	public String getPersonalId() {
		return personalId;
	}
	
	/**
	 * @param personalId セットする personalId
	 */
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	/**
	 * @return txtEditEndYear
	 */
	public String getTxtEditEndYear() {
		return txtEditEndYear;
	}
	
	/**
	 * @param txtEditEndYear セットする txtEditEndYear
	 */
	public void setTxtEditEndYear(String txtEditEndYear) {
		this.txtEditEndYear = txtEditEndYear;
	}
	
	/**
	 * @return txtEditEndMonth
	 */
	public String getTxtEditEndMonth() {
		return txtEditEndMonth;
	}
	
	/**
	 * @param txtEditEndMonth セットする txtEditEndMonth
	 */
	public void setTxtEditEndMonth(String txtEditEndMonth) {
		this.txtEditEndMonth = txtEditEndMonth;
	}
	
	/**
	 * @return txtEditEndDay
	 */
	public String getTxtEditEndDay() {
		return txtEditEndDay;
	}
	
	/**
	 * @param txtEditEndDay セットする txtEditEndDay
	 */
	public void setTxtEditEndDay(String txtEditEndDay) {
		this.txtEditEndDay = txtEditEndDay;
	}
	
	/**
	 * @return pltEditSection
	 */
	public String getPltEditSection() {
		return pltEditSection;
	}
	
	/**
	 * @param pltEditSection セットする pltEditSection
	 */
	public void setPltEditSection(String pltEditSection) {
		this.pltEditSection = pltEditSection;
	}
	
	/**
	 * @return pltEditPosition
	 */
	public String getPltEditPosition() {
		return pltEditPosition;
	}
	
	/**
	 * @param pltEditPosition セットする pltEditPosition
	 */
	public void setPltEditPosition(String pltEditPosition) {
		this.pltEditPosition = pltEditPosition;
	}
	
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
	 * @return pltEditEmployeeName
	 */
	public String getPltEditEmployeeName() {
		return pltEditEmployeeName;
	}
	
	/**
	 * @param pltEditEmployeeName セットする pltEditEmployeeName
	 */
	public void setPltEditEmployeeName(String pltEditEmployeeName) {
		this.pltEditEmployeeName = pltEditEmployeeName;
	}
	
	/**
	 * @return pltEditWorkflowType
	 */
	public String getPltEditWorkflowType() {
		return pltEditWorkflowType;
	}
	
	/**
	 * @param pltEditWorkflowType セットする pltEditWorkflowType
	 */
	public void setPltEditWorkflowType(String pltEditWorkflowType) {
		this.pltEditWorkflowType = pltEditWorkflowType;
	}
	
	/**
	 * @return txtSearchSectionName
	 */
	public String getTxtSearchSectionName() {
		return txtSearchSectionName;
	}
	
	/**
	 * @param txtSearchSectionName セットする txtSearchSectionName
	 */
	public void setTxtSearchSectionName(String txtSearchSectionName) {
		this.txtSearchSectionName = txtSearchSectionName;
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
	 * @param idx インデックス
	 * @return aryLblSubApproverNo
	 */
	public String getAryLblSubApproverNo(int idx) {
		return aryLblSubApproverNo[idx];
	}
	
	/**
	 * @param aryLblSubApproverNo セットする aryLblSubApproverNo
	 */
	public void setAryLblSubApproverNo(String[] aryLblSubApproverNo) {
		this.aryLblSubApproverNo = getStringArrayClone(aryLblSubApproverNo);
	}
	
	/**
	 * @return aryLblEndDate
	 */
	public String[] getAryLblEndDate() {
		return getStringArrayClone(aryLblEndDate);
	}
	
	/**
	 * @param aryLblEndDate セットする aryLblEndDate
	 */
	public void setAryLblEndDate(String[] aryLblEndDate) {
		this.aryLblEndDate = getStringArrayClone(aryLblEndDate);
	}
	
	/**
	 * @return aryLblWorkflowType
	 */
	public String[] getAryLblWorkflowType() {
		return getStringArrayClone(aryLblWorkflowType);
	}
	
	/**
	 * @param aryLblWorkflowType セットする aryLblWorkflowType
	 */
	public void setAryLblWorkflowType(String[] aryLblWorkflowType) {
		this.aryLblWorkflowType = getStringArrayClone(aryLblWorkflowType);
	}
	
	/**
	 * @return aryLblWorkflowName
	 */
	public String[] getAryLblWorkflowName() {
		return getStringArrayClone(aryLblWorkflowName);
	}
	
	/**
	 * @param aryLblWorkflowName セットする aryLblWorkflowName
	 */
	public void setAryLblWorkflowName(String[] aryLblWorkflowName) {
		this.aryLblWorkflowName = getStringArrayClone(aryLblWorkflowName);
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
	 * @return aryPltEditSection
	 */
	public String[][] getAryPltEditSection() {
		return getStringArrayClone(aryPltEditSection);
	}
	
	/**
	 * @param aryPltEditSection セットする aryPltEditSection
	 */
	public void setAryPltEditSection(String[][] aryPltEditSection) {
		this.aryPltEditSection = getStringArrayClone(aryPltEditSection);
	}
	
	/**
	 * @return aryPltEditPosition
	 */
	public String[][] getAryPltEditPosition() {
		return getStringArrayClone(aryPltEditPosition);
	}
	
	/**
	 * @param aryPltEditPosition セットする aryPltEditPosition
	 */
	public void setAryPltEditPosition(String[][] aryPltEditPosition) {
		this.aryPltEditPosition = getStringArrayClone(aryPltEditPosition);
	}
	
	/**
	 * @return aryPltEditEmployee
	 */
	public String[][] getAryPltEditEmployee() {
		return getStringArrayClone(aryPltEditEmployee);
	}
	
	/**
	 * @param aryPltEditEmployee セットする aryPltEditEmployee
	 */
	public void setAryPltEditEmployee(String[][] aryPltEditEmployee) {
		this.aryPltEditEmployee = getStringArrayClone(aryPltEditEmployee);
	}
	
}
