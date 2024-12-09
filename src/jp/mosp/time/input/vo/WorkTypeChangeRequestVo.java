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
package jp.mosp.time.input.vo;

import jp.mosp.time.base.TimeVo;

/**
 * 勤務形態変更申請情報を格納する。
 */
public class WorkTypeChangeRequestVo extends TimeVo {
	
	private static final long	serialVersionUID	= 549919157079361168L;
	
	private long				recordId;
	
	private String				pltEditRequestYear;
	private String				pltEditRequestMonth;
	private String				pltEditRequestDay;
	private String				pltEditEndYear;
	private String				pltEditEndMonth;
	private String				pltEditEndDay;
	private String				pltEditWorkType;
	private String				txtEditRequestReason;
	private String				ckbEndDate;
	
	private String				lblWorkTypeName;
	
	private String				pltSearchState;
	private String				pltSearchWorkType;
	private String				pltSearchRequestYear;
	private String				pltSearchRequestMonth;
	
	private String[]			aryCkbWorkTypeChangeRequestId;
	private String[]			aryDate;
	private String[]			aryLblDateAndDay;
	private String[]			aryLblWorkType;
	private String[]			aryLblRequestReason;
	private String[]			aryLblState;
	private String[]			aryStateStyle;
	private String[]			aryLblApprover;
	
	private String[]			aryWorkflowStatus;
	private long[]				aryWorkflow;
	
	private String[]			aryOnOff;
	
	private String[][]			aryPltEditRequestYear;
	private String[][]			aryPltEditRequestMonth;
	private String[][]			aryPltEditRequestDay;
	private String[][]			aryPltEditEndYear;
	private String[][]			aryPltEditEndMonth;
	private String[][]			aryPltEditEndDay;
	private String[][]			aryPltEditWorkType;
	
	private String[][]			aryPltSearchWorkType;
	private String[][]			aryPltSearchRequestYear;
	private String[][]			aryPltSearchRequestMonth;
	
	private String				modeSearchActivateDate;
	
	
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
	 * @return pltEditWorkType
	 */
	public String getPltEditWorkType() {
		return pltEditWorkType;
	}
	
	/**
	 * @param pltEditWorkType セットする pltEditWorkType
	 */
	public void setPltEditWorkType(String pltEditWorkType) {
		this.pltEditWorkType = pltEditWorkType;
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
	 * @return aryCkbWorkTypeChangeRequestId
	 */
	public String[] getAryCkbWorkTypeChangeRequestId() {
		return getStringArrayClone(aryCkbWorkTypeChangeRequestId);
	}
	
	/**
	 * @param aryCkbWorkTypeChangeRequestId セットする aryCkbWorkTypeChangeRequestId
	 */
	public void setAryCkbWorkTypeChangeRequestId(String[] aryCkbWorkTypeChangeRequestId) {
		this.aryCkbWorkTypeChangeRequestId = getStringArrayClone(aryCkbWorkTypeChangeRequestId);
	}
	
	/**
	 * @return aryDate
	 */
	public String[] getAryDate() {
		return getStringArrayClone(aryDate);
	}
	
	/**
	 * @param aryDate セットする aryDate
	 */
	public void setAryDate(String[] aryDate) {
		this.aryDate = getStringArrayClone(aryDate);
	}
	
	/**
	 * @return aryLblDateAndDay
	 */
	public String[] getAryLblDateAndDay() {
		return getStringArrayClone(aryLblDateAndDay);
	}
	
	/**
	 * @param aryLblDateAndDay セットする aryLblDateAndDay
	 */
	public void setAryLblDateAndDay(String[] aryLblDateAndDay) {
		this.aryLblDateAndDay = getStringArrayClone(aryLblDateAndDay);
	}
	
	/**
	 * @return aryLblWorkType
	 */
	public String[] getAryLblWorkType() {
		return getStringArrayClone(aryLblWorkType);
	}
	
	/**
	 * @param aryLblWorkType セットする aryLblWorkType
	 */
	public void setAryLblWorkType(String[] aryLblWorkType) {
		this.aryLblWorkType = getStringArrayClone(aryLblWorkType);
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
	 * @return aryWorkflow
	 */
	public long[] getAryWorkflow() {
		return getLongArrayClone(aryWorkflow);
	}
	
	/**
	 * @param aryWorkflow セットする aryWorkflow
	 */
	public void setAryWorkflow(long[] aryWorkflow) {
		this.aryWorkflow = getLongArrayClone(aryWorkflow);
	}
	
	/**
	 * @return aryOnOff
	 */
	public String[] getAryOnOff() {
		return getStringArrayClone(aryOnOff);
	}
	
	/**
	 * @param aryOnOff セットする aryOnOff
	 */
	public void setAryOnOff(String[] aryOnOff) {
		this.aryOnOff = getStringArrayClone(aryOnOff);
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
	 * @return aryPltEditWorkType
	 */
	public String[][] getAryPltEditWorkType() {
		return getStringArrayClone(aryPltEditWorkType);
	}
	
	/**
	 * @param aryPltEditWorkType セットする aryPltEditWorkType
	 */
	public void setAryPltEditWorkType(String[][] aryPltEditWorkType) {
		this.aryPltEditWorkType = getStringArrayClone(aryPltEditWorkType);
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
	 * @return modeSearchActivateDate
	 */
	public String getModeSearchActivateDate() {
		return modeSearchActivateDate;
	}
	
	/**
	 * @param modeSearchActivateDate セットする modeSearchActivateDate
	 */
	public void setModeSearchActivateDate(String modeSearchActivateDate) {
		this.modeSearchActivateDate = modeSearchActivateDate;
	}
	
}
