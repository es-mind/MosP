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
 * 未承認情報一覧の情報を格納する。
 */
public class ApprovalListVo extends TimeVo {
	
	private static final long	serialVersionUID	= 666754658231966199L;
	
	private String				lblAttendance;
	private String				lblOverTime;
	private String				lblHoliday;
	private String				lblWorkOnHoliday;
	private String				lblSubHoliday;
	private String				lblWorkTypeChange;
	private String				lblDifference;
	private String				lblTotalApproval;
	private String				lblTotalCancel;
	
	private long[]				aryCkbApprovalListId;
	private String[]			aryLblEmployeeCode;
	private String[]			aryLblEmployeeName;
	private String[]			aryLblSection;
	private String[]			aryLblRequestType;
	private String[]			aryLblRequestDate;
	private String[]			aryLblRequestInfo;
	private String[]			aryLblState;
	private String[]			aryStateStyle;
	private String[]			aryLblRequestTypeCmd;
	private String[]			aryLblRequestTypeHistoryCmd;
	private String[]			aryLblRequestFunctionCode;
	private String[]			aryStage;
	private String[]			aryState;
	private long[]				aryWorkflow;
	private String[]			aryBackColor;
	
	private String[]			aryRequestDate;
	private String[]			aryClasOverTimeIn;
	
	/**
	 * 機能コード。<br>
	 */
	private String				functionCode;
	private String				approvalType;
	
	
	/**
	 * @return lblAttendance
	 */
	public String getLblAttendance() {
		return lblAttendance;
	}
	
	/**
	 * @param lblAttendance セットする lblAttendance
	 */
	public void setLblAttendance(String lblAttendance) {
		this.lblAttendance = lblAttendance;
	}
	
	/**
	 * @return lblOverTime
	 */
	public String getLblOverTime() {
		return lblOverTime;
	}
	
	/**
	 * @param lblOverTime セットする lblOverTime
	 */
	public void setLblOverTime(String lblOverTime) {
		this.lblOverTime = lblOverTime;
	}
	
	/**
	 * @return lblHoliday
	 */
	public String getLblHoliday() {
		return lblHoliday;
	}
	
	/**
	 * @param lblHoliday セットする lblHoliday
	 */
	public void setLblHoliday(String lblHoliday) {
		this.lblHoliday = lblHoliday;
	}
	
	/**
	 * @return lblWorkOnHoliday
	 */
	public String getLblWorkOnHoliday() {
		return lblWorkOnHoliday;
	}
	
	/**
	 * @param lblWorkOnHoliday セットする lblWorkOnHoliday
	 */
	public void setLblWorkOnHoliday(String lblWorkOnHoliday) {
		this.lblWorkOnHoliday = lblWorkOnHoliday;
	}
	
	/**
	 * @return lblSubHoliday
	 */
	public String getLblSubHoliday() {
		return lblSubHoliday;
	}
	
	/**
	 * @param lblSubHoliday セットする lblSubHoliday
	 */
	public void setLblSubHoliday(String lblSubHoliday) {
		this.lblSubHoliday = lblSubHoliday;
	}
	
	/**
	 * @return lblWorkTypeChange
	 */
	public String getLblWorkTypeChange() {
		return lblWorkTypeChange;
	}
	
	/**
	 * @param lblWorkTypeChange セットする lblWorkTypeChange
	 */
	public void setLblWorkTypeChange(String lblWorkTypeChange) {
		this.lblWorkTypeChange = lblWorkTypeChange;
	}
	
	/**
	 * @return lblDifference
	 */
	public String getLblDifference() {
		return lblDifference;
	}
	
	/**
	 * @param lblDifference セットする lblDifference
	 */
	public void setLblDifference(String lblDifference) {
		this.lblDifference = lblDifference;
	}
	
	/**
	 * @return lblTotalApproval
	 */
	public String getLblTotalApproval() {
		return lblTotalApproval;
	}
	
	/**
	 * @param lblTotalApproval セットする lblTotalApproval
	 */
	public void setLblTotalApproval(String lblTotalApproval) {
		this.lblTotalApproval = lblTotalApproval;
	}
	
	/**
	 * @return lblTotalCancel
	 */
	public String getLblTotalCancel() {
		return lblTotalCancel;
	}
	
	/**
	 * @param lblTotalCancel セットする lblTotalCancel
	 */
	public void setLblTotalCancel(String lblTotalCancel) {
		this.lblTotalCancel = lblTotalCancel;
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
	 * @param idx インデックス
	 * @return aryLblRequestTypeCmd
	 */
	public String getAryLblRequestTypeCmd(int idx) {
		return aryLblRequestTypeCmd[idx];
	}
	
	/**
	 * @param aryLblRequestTypeCmd セットする aryLblRequestTypeCmd
	 */
	public void setAryLblRequestTypeCmd(String[] aryLblRequestTypeCmd) {
		this.aryLblRequestTypeCmd = getStringArrayClone(aryLblRequestTypeCmd);
	}
	
	/**
	 * @return aryCkbApprovalListId
	 */
	public long[] getAryCkbApprovalListId() {
		return getLongArrayClone(aryCkbApprovalListId);
	}
	
	/**
	 * @param aryCkbApprovalListId セットする aryCkbApprovalListId
	 */
	public void setAryCkbApprovalListId(long[] aryCkbApprovalListId) {
		this.aryCkbApprovalListId = getLongArrayClone(aryCkbApprovalListId);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRequestTypeHistoryCmd
	 */
	public String getAryLblRequestTypeHistoryCmd(int idx) {
		return aryLblRequestTypeHistoryCmd[idx];
	}
	
	/**
	 * @return aryStage
	 */
	public String[] getAryStage() {
		return getStringArrayClone(aryStage);
	}
	
	/**
	 * @return aryState
	 */
	public String[] getAryState() {
		return getStringArrayClone(aryState);
	}
	
	/**
	 * @param aryLblRequestTypeHistoryCmd セットする aryLblRequestTypeHistoryCmd
	 */
	public void setAryLblRequestTypeHistoryCmd(String[] aryLblRequestTypeHistoryCmd) {
		this.aryLblRequestTypeHistoryCmd = getStringArrayClone(aryLblRequestTypeHistoryCmd);
	}
	
	/**
	 * @param aryStage セットする aryStage
	 */
	public void setAryStage(String[] aryStage) {
		this.aryStage = getStringArrayClone(aryStage);
	}
	
	/**
	 * @param aryState セットする aryState
	 */
	public void setAryState(String[] aryState) {
		this.aryState = getStringArrayClone(aryState);
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
	 * @return aryLblRequestFunctionCode
	 */
	public String[] getAryLblRequestFunctionCode() {
		return getStringArrayClone(aryLblRequestFunctionCode);
	}
	
	/**
	 * @param aryLblRequestFunctionCode セットする aryLblRequestFunctionCode
	 */
	public void setAryLblRequestFunctionCode(String[] aryLblRequestFunctionCode) {
		this.aryLblRequestFunctionCode = getStringArrayClone(aryLblRequestFunctionCode);
	}
	
	/**
	 * @return aryRequestDate
	 */
	public String[] getAryRequestDate() {
		return getStringArrayClone(aryRequestDate);
	}
	
	/**
	 * @param aryRequestDate セットする aryRequestDate
	 */
	public void setAryRequestDate(String[] aryRequestDate) {
		this.aryRequestDate = getStringArrayClone(aryRequestDate);
	}
	
	/**
	 * @return aryClasOverTimeIn
	 */
	public String[] getAryClasOverTimeIn() {
		return getStringArrayClone(aryClasOverTimeIn);
	}
	
	/**
	 * @param aryClasOverTimeIn セットする aryClasOverTimeIn
	 */
	public void setAryClasOverTimeIn(String[] aryClasOverTimeIn) {
		this.aryClasOverTimeIn = getStringArrayClone(aryClasOverTimeIn);
	}
	
	/**
	 * @return functionCode
	 */
	public String getFunctionCode() {
		return functionCode;
	}
	
	/**
	 * @param functionCode セットする functionCode
	 */
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	
	/**
	 * @return approvalType
	 */
	public String getApprovalType() {
		return approvalType;
	}
	
	/**
	 * @param approvalType セットする approvalType
	 */
	public void setApprovalType(String approvalType) {
		this.approvalType = approvalType;
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
	 * @return aryWorkflow
	 */
	public long[] getAryWorkflow() {
		return getLongArrayClone(aryWorkflow);
	}
	
}
