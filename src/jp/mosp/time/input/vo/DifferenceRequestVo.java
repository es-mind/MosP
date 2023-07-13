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
package jp.mosp.time.input.vo;

import jp.mosp.time.base.TimeVo;

/**
 * 時差出勤申請情報を格納する。
 */
public class DifferenceRequestVo extends TimeVo {
	
	private static final long	serialVersionUID	= 8819500905394012454L;
	
	private long				recordId;
	
	private String				pltEditRequestYear;
	private String				pltEditRequestMonth;
	private String				pltEditRequestDay;
	private String				pltEditRequestType;
	private String				pltEditRequestHour;
	private String				pltEditRequestMinute;
	private String				txtEditRequestReason;
	private String				pltSearchState;
	private String				pltSearchRequestHour;
	private String				pltSearchRequestMinute;
	private String				pltSearchRequestYear;
	private String				pltSearchRequestMonth;
	private String				pltSearchWorkType;
	private String				pltEditDifferenceType;
	private String				pltEditEndYear;
	private String				pltEditEndMonth;
	private String				pltEditEndDay;
	
	private String				lblWorkType;
	private String				lblWorkTypeName;
	private String				lblEndTimeHour;
	private String				lblEndTimeMinute;
	
	private String[]			aryCkbDifferenceRequestListId;
	private String[]			aryLblDate;
	private String[]			aryLblRequestType;
	private String[]			aryLblWorkTime;
	private String[]			aryLblRequestReason;
	private String[]			aryLblState;
	private String[]			aryStateStyle;
	private String[]			aryLblApprover;
	
	private String[][]			aryPltEditRequestYear;
	private String[][]			aryPltEditRequestMonth;
	private String[][]			aryPltEditRequestDay;
	private String[][]			aryPltEditRequestHour;
	private String[][]			aryPltEditRequestMinute;
	private String[][]			aryPltSearchState;
	private String[][]			aryPltSearchWorkType;
	private String[][]			aryPltSearchRequestYear;
	private String[][]			aryPltSearchRequestMonth;
	private String[][]			aryPltEditDifferenceType;
	private String[][]			aryPltEditEndYear;
	private String[][]			aryPltEditEndMonth;
	private String[][]			aryPltEditEndDay;
	
	private String				ckbEndDate;
	
	private String				jsSearchActivateDateMode;
	private String				jsEditDifferenceTypeMode;
	
	private String[]			aryLblOnOff;
	
	/**
	 * ワークフロー番号。
	 */
	private long[]				aryWorkflow;
	private String[]			aryWorkflowStatus;
	
	
	/**
	 * @return pltEditRequestYear
	 */
	public String getPltEditRequestYear() {
		return pltEditRequestYear;
	}
	
	/**
	 * @param pltEditRequestYear セットする pltEditRequestYear
	 */
	public void setPltEditRequestYear(String pltEditRequestYear) {
		this.pltEditRequestYear = pltEditRequestYear;
	}
	
	/**
	 * @return pltEditRequestMonth
	 */
	public String getPltEditRequestMonth() {
		return pltEditRequestMonth;
	}
	
	/**
	 * @param pltEditRequestMonth セットする pltEditRequestMonth
	 */
	public void setPltEditRequestMonth(String pltEditRequestMonth) {
		this.pltEditRequestMonth = pltEditRequestMonth;
	}
	
	/**
	 * @return pltEditRequestDay
	 */
	public String getPltEditRequestDay() {
		return pltEditRequestDay;
	}
	
	/**
	 * @param pltEditRequestDay セットする pltEditRequestDay
	 */
	public void setPltEditRequestDay(String pltEditRequestDay) {
		this.pltEditRequestDay = pltEditRequestDay;
	}
	
	/**
	 * @return pltEditRequestType
	 */
	public String getPltEditRequestType() {
		return pltEditRequestType;
	}
	
	/**
	 * @param pltEditRequestType セットする pltEditRequestType
	 */
	public void setPltEditRequestType(String pltEditRequestType) {
		this.pltEditRequestType = pltEditRequestType;
	}
	
	/**
	 * @return pltEditRequestHour
	 */
	public String getPltEditRequestHour() {
		return pltEditRequestHour;
	}
	
	/**
	 * @param pltEditRequestHour セットする pltEditRequestHour
	 */
	public void setPltEditRequestHour(String pltEditRequestHour) {
		this.pltEditRequestHour = pltEditRequestHour;
	}
	
	/**
	 * @return pltEditRequestMinute
	 */
	public String getPltEditRequestMinute() {
		return pltEditRequestMinute;
	}
	
	/**
	 * @param pltEditRequestMinute セットする pltEditRequestMinute
	 */
	public void setPltEditRequestMinute(String pltEditRequestMinute) {
		this.pltEditRequestMinute = pltEditRequestMinute;
	}
	
	/**
	 * @return txtEditRequestReason
	 */
	public String getTxtEditRequestReason() {
		return txtEditRequestReason;
	}
	
	/**
	 * @param txtEditRequestReason セットする txtEditRequestReason
	 */
	public void setTxtEditRequestReason(String txtEditRequestReason) {
		this.txtEditRequestReason = txtEditRequestReason;
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
	 * @return pltSearchRequestHour
	 */
	public String getPltSearchRequestHour() {
		return pltSearchRequestHour;
	}
	
	/**
	 * @param pltSearchRequestHour セットする pltSearchRequestHour
	 */
	public void setPltSearchRequestHour(String pltSearchRequestHour) {
		this.pltSearchRequestHour = pltSearchRequestHour;
	}
	
	/**
	 * @return pltSearchRequestMinute
	 */
	public String getPltSearchRequestMinute() {
		return pltSearchRequestMinute;
	}
	
	/**
	 * @param pltSearchRequestMinute セットする pltSearchRequestMinute
	 */
	public void setPltSearchRequestMinute(String pltSearchRequestMinute) {
		this.pltSearchRequestMinute = pltSearchRequestMinute;
	}
	
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
	 * @return pltSearchWorkType
	 */
	public String getPltSearchWorkType() {
		return pltSearchWorkType;
	}
	
	/**
	 * @param pltSearchWorkType セットする pltSearchWorkType
	 */
	public void setPltSearchWorkType(String pltSearchWorkType) {
		this.pltSearchWorkType = pltSearchWorkType;
	}
	
	/**
	 * @return lblWorkType
	 */
	public String getLblWorkType() {
		return lblWorkType;
	}
	
	/**
	 * @param lblWorkType セットする lblWorkType
	 */
	public void setLblWorkType(String lblWorkType) {
		this.lblWorkType = lblWorkType;
	}
	
	/**
	 * @return lblWorkTypeName
	 */
	public String getLblWorkTypeName() {
		return lblWorkTypeName;
	}
	
	/**
	 * @param lblWorkTypeName セットする lblWorkTypeName
	 */
	public void setLblWorkTypeName(String lblWorkTypeName) {
		this.lblWorkTypeName = lblWorkTypeName;
	}
	
	/**
	 * @return lblEndTimeHour
	 */
	public String getLblEndTimeHour() {
		return lblEndTimeHour;
	}
	
	/**
	 * @param lblEndTimeHour セットする lblEndTimeHour
	 */
	public void setLblEndTimeHour(String lblEndTimeHour) {
		this.lblEndTimeHour = lblEndTimeHour;
	}
	
	/**
	 * @return lblEndTimeMinutes
	 */
	public String getLblEndTimeMinute() {
		return lblEndTimeMinute;
	}
	
	/**
	 * @param lblEndTimeMinute セットする lblEndTimeMinute
	 */
	public void setLblEndTimeMinute(String lblEndTimeMinute) {
		this.lblEndTimeMinute = lblEndTimeMinute;
	}
	
	/**
	 * @return aryCkbDifferenceRequestListId
	 */
	public String[] getAryCkbDifferenceRequestListId() {
		return getStringArrayClone(aryCkbDifferenceRequestListId);
	}
	
	/**
	 * @param aryCkbDifferenceRequestListId セットする aryCkbDifferenceRequestListId
	 */
	public void setAryCkbDifferenceRequestListId(String[] aryCkbDifferenceRequestListId) {
		this.aryCkbDifferenceRequestListId = getStringArrayClone(aryCkbDifferenceRequestListId);
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
	 * @return aryLblWorkTime
	 */
	public String[] getAryLblWorkTime() {
		return getStringArrayClone(aryLblWorkTime);
	}
	
	/**
	 * @param aryLblWorkTime セットする aryLblWorkTime
	 */
	public void setAryLblWorkTime(String[] aryLblWorkTime) {
		this.aryLblWorkTime = getStringArrayClone(aryLblWorkTime);
	}
	
	/**
	 * @return aryLblRequestReason
	 */
	public String[] getAryLblRequestReason() {
		return getStringArrayClone(aryLblRequestReason);
	}
	
	/**
	 * @param aryLblRequestReason セットする aryLblRequestReason
	 */
	public void setAryLblRequestReason(String[] aryLblRequestReason) {
		this.aryLblRequestReason = getStringArrayClone(aryLblRequestReason);
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
	 * @return aryLblApprover
	 */
	public String[] getAryLblApprover() {
		return getStringArrayClone(aryLblApprover);
	}
	
	/**
	 * @param aryLblApprover セットする aryLblApprover
	 */
	public void setAryLblApprover(String[] aryLblApprover) {
		this.aryLblApprover = getStringArrayClone(aryLblApprover);
	}
	
	/**
	 * @return jsSearchActivateDateMode
	 */
	public String getJsSearchActivateDateMode() {
		return jsSearchActivateDateMode;
	}
	
	/**
	 * @param jsSearchActivateDateMode セットする jsSearchActivateDateMode
	 */
	public void setJsSearchActivateDateMode(String jsSearchActivateDateMode) {
		this.jsSearchActivateDateMode = jsSearchActivateDateMode;
	}
	
	/**
	 * @return aryPltEditRequestYear
	 */
	public String[][] getAryPltEditRequestYear() {
		return getStringArrayClone(aryPltEditRequestYear);
	}
	
	/**
	 * @param aryPltEditRequestYear セットする aryPltEditRequestYear
	 */
	public void setAryPltEditRequestYear(String[][] aryPltEditRequestYear) {
		this.aryPltEditRequestYear = getStringArrayClone(aryPltEditRequestYear);
	}
	
	/**
	 * @return aryPltEditRequestMonth
	 */
	public String[][] getAryPltEditRequestMonth() {
		return getStringArrayClone(aryPltEditRequestMonth);
	}
	
	/**
	 * @param aryPltEditRequestMonth セットする aryPltEditRequestMonth
	 */
	public void setAryPltEditRequestMonth(String[][] aryPltEditRequestMonth) {
		this.aryPltEditRequestMonth = getStringArrayClone(aryPltEditRequestMonth);
	}
	
	/**
	 * @return aryPltEditRequestDay
	 */
	public String[][] getAryPltEditRequestDay() {
		return getStringArrayClone(aryPltEditRequestDay);
	}
	
	/**
	 * @param aryPltEditRequestDay セットする aryPltEditRequestDay
	 */
	public void setAryPltEditRequestDay(String[][] aryPltEditRequestDay) {
		this.aryPltEditRequestDay = getStringArrayClone(aryPltEditRequestDay);
	}
	
	/**
	 * @return aryPltEditRequestHour
	 */
	public String[][] getAryPltEditRequestHour() {
		return getStringArrayClone(aryPltEditRequestHour);
	}
	
	/**
	 * @param aryPltEditRequestHour セットする aryPltEditRequestHour
	 */
	public void setAryPltEditRequestHour(String[][] aryPltEditRequestHour) {
		this.aryPltEditRequestHour = getStringArrayClone(aryPltEditRequestHour);
	}
	
	/**
	 * @return aryPltEditRequestMinute
	 */
	public String[][] getAryPltEditRequestMinute() {
		return getStringArrayClone(aryPltEditRequestMinute);
	}
	
	/**
	 * @param aryPltEditRequestMinute セットする aryPltEditRequestMinute
	 */
	public void setAryPltEditRequestMinute(String[][] aryPltEditRequestMinute) {
		this.aryPltEditRequestMinute = getStringArrayClone(aryPltEditRequestMinute);
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
	 * @return aryPltSearchWorkType
	 */
	public String[][] getAryPltSearchWorkType() {
		return getStringArrayClone(aryPltSearchWorkType);
	}
	
	/**
	 * @param aryPltSearchWorkType セットする aryPltSearchWorkType
	 */
	public void setAryPltSearchWorkType(String[][] aryPltSearchWorkType) {
		this.aryPltSearchWorkType = getStringArrayClone(aryPltSearchWorkType);
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
	 * @return pltEditDifferenceType
	 */
	public String getPltEditDifferenceType() {
		return pltEditDifferenceType;
	}
	
	/**
	 * @param pltEditDifferenceType セットする pltEditDifferenceType
	 */
	public void setPltEditDifferenceType(String pltEditDifferenceType) {
		this.pltEditDifferenceType = pltEditDifferenceType;
	}
	
	/**
	 * @return aryPltEditDifferenceType
	 */
	public String[][] getAryPltEditDifferenceType() {
		return getStringArrayClone(aryPltEditDifferenceType);
	}
	
	/**
	 * @param aryPltEditDifferenceType セットする aryPltEditDifferenceType
	 */
	public void setAryPltEditDifferenceType(String[][] aryPltEditDifferenceType) {
		this.aryPltEditDifferenceType = getStringArrayClone(aryPltEditDifferenceType);
	}
	
	/**
	 * @return pltEditEndYear
	 */
	public String getPltEditEndYear() {
		return pltEditEndYear;
	}
	
	/**
	 * @param pltEditEndYear セットする pltEditEndYear
	 */
	public void setPltEditEndYear(String pltEditEndYear) {
		this.pltEditEndYear = pltEditEndYear;
	}
	
	/**
	 * @return pltEditEndMonth
	 */
	public String getPltEditEndMonth() {
		return pltEditEndMonth;
	}
	
	/**
	 * @param pltEditEndMonth セットする pltEditEndMonth
	 */
	public void setPltEditEndMonth(String pltEditEndMonth) {
		this.pltEditEndMonth = pltEditEndMonth;
	}
	
	/**
	 * @return pltEditEndDay
	 */
	public String getPltEditEndDay() {
		return pltEditEndDay;
	}
	
	/**
	 * @param pltEditEndDay セットする pltEditEndDay
	 */
	public void setPltEditEndDay(String pltEditEndDay) {
		this.pltEditEndDay = pltEditEndDay;
	}
	
	/**
	 * @return aryPltEditEndYear
	 */
	public String[][] getAryPltEditEndYear() {
		return getStringArrayClone(aryPltEditEndYear);
	}
	
	/**
	 * @param aryPltEditEndYear セットする aryPltEditEndYear
	 */
	public void setAryPltEditEndYear(String[][] aryPltEditEndYear) {
		this.aryPltEditEndYear = getStringArrayClone(aryPltEditEndYear);
	}
	
	/**
	 * @return aryPltEditEndMonth
	 */
	public String[][] getAryPltEditEndMonth() {
		return getStringArrayClone(aryPltEditEndMonth);
	}
	
	/**
	 * @param aryPltEditEndMonth セットする aryPltEditEndMonth
	 */
	public void setAryPltEditEndMonth(String[][] aryPltEditEndMonth) {
		this.aryPltEditEndMonth = getStringArrayClone(aryPltEditEndMonth);
	}
	
	/**
	 * @return aryPltEditEndDay
	 */
	public String[][] getAryPltEditEndDay() {
		return getStringArrayClone(aryPltEditEndDay);
	}
	
	/**
	 * @param aryPltEditEndDay セットする aryPltEditEndDay
	 */
	public void setAryPltEditEndDay(String[][] aryPltEditEndDay) {
		this.aryPltEditEndDay = getStringArrayClone(aryPltEditEndDay);
	}
	
	/**
	 * @return ckbEndDate
	 */
	public String getCkbEndDate() {
		return ckbEndDate;
	}
	
	/**
	 * @param ckbEndDate セットする ckbEndDate
	 */
	public void setCkbEndDate(String ckbEndDate) {
		this.ckbEndDate = ckbEndDate;
	}
	
	/**
	 * @return aryLblOnOff
	 */
	public String[] getAryLblOnOff() {
		return getStringArrayClone(aryLblOnOff);
	}
	
	/**
	 * @param aryLblOnOff セットする aryLblOnOff
	 */
	public void setAryLblOnOff(String[] aryLblOnOff) {
		this.aryLblOnOff = getStringArrayClone(aryLblOnOff);
	}
	
	/**
	 * @return recordId
	 */
	public long getRecordId() {
		return recordId;
	}
	
	/**
	 * @param recordId セットする recordId
	 */
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}
	
	/**
	 * @param aryWorkflow セットする aryWorkflow
	 */
	public void setAryWorkflow(long[] aryWorkflow) {
		this.aryWorkflow = getLongArrayClone(aryWorkflow);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryWorkflow
	 */
	public long getAryWorkflow(int idx) {
		return aryWorkflow[idx];
	}
	
	/**
	 * @return aryWorkflowStatus
	 */
	public String[] getAryWorkflowStatus() {
		return getStringArrayClone(aryWorkflowStatus);
	}
	
	/**
	 * @param aryWorkflowStatus セットする aryWorkflowStatus
	 */
	public void setAryWorkflowStatus(String[] aryWorkflowStatus) {
		this.aryWorkflowStatus = getStringArrayClone(aryWorkflowStatus);
	}
	
	/**
	 * @return jsEditDifferenceTypeMode
	 */
	public String getJsEditDifferenceTypeMode() {
		return jsEditDifferenceTypeMode;
	}
	
	/**
	 * @param jsEditDifferenceTypeMode セットする jsEditDifferenceTypeMode
	 */
	public void setJsEditDifferenceTypeMode(String jsEditDifferenceTypeMode) {
		this.jsEditDifferenceTypeMode = jsEditDifferenceTypeMode;
	}
}
