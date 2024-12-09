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
 * 代休申請情報を格納する。
 */
public class SubHolidayRequestVo extends TimeVo {
	
	private static final long	serialVersionUID	= 1695607628279125809L;
	
	private long				recordId;
	
	private String				pltEditRequestYear;
	private String				pltEditRequestMonth;
	private String				pltEditRequestDay;
	private String				pltEditHolidayRange;
	private String				pltEditWorkDate;
	private String				pltSearchRequestYear;
	private String				pltSearchRequestMonth;
	private String				pltSearchWorkYear;
	private String				pltSearchWorkMonth;
	private String				pltSearchState;
	
	private String[]			aryLblRequestDate;
	private String[]			aryLblWorkDate;
	private String[]			aryLblState;
	private String[]			aryStateStyle;
	private String[]			aryLblApprover;
	private String[]			aryLblWorkflow;
	
	private String[]			aryLblDate;
	private String[]			aryLblRange;
	private String[]			aryLblOnOff;
	
	private String[]			aryCkbSubHolidayRequestListId;
	
	private String[][]			aryPltEditRequestYear;
	private String[][]			aryPltEditRequestMonth;
	private String[][]			aryPltEditRequestDay;
	private String[][]			aryPltEditHolidayRange;
	private String[][]			aryPltEditWorkDate;
	private String[][]			aryPltSearchRequestYear;
	private String[][]			aryPltSearchRequestMonth;
	private String[][]			aryPltSearchWorkYear;
	private String[][]			aryPltSearchWorkMonth;
	private String[][]			aryPltSearchState;
	
	private String				lblEmployeeCode;
	private String				lblEmployeeName;
	private String				lblSection;
	
	private String[]			aryLblCompensationWorkDate;
	private String[]			aryLblCompensationExpirationDate;
	private String[]			aryLblCompensationType;
	private String[]			aryLblCompensationRange;
	
	private String[]			aryLblCompensationDayTh;
	private String[]			aryLblCompensationDayForWorkOnDayOff;
	private String[]			aryLblCompensationDayForNightWork;
	
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
	 * @return pltEditHolidayType
	 */
	public String getPltEditHolidayRange() {
		return pltEditHolidayRange;
	}
	
	/**
	 * @param pltEditHolidayRange セットする pltEditHolidayRange
	 */
	public void setPltEditHolidayRange(String pltEditHolidayRange) {
		this.pltEditHolidayRange = pltEditHolidayRange;
	}
	
	/**
	 * @return pltEditWorkDate
	 */
	public String getPltEditWorkDate() {
		return pltEditWorkDate;
	}
	
	/**
	 * @param pltEditWorkDate セットする pltEditWorkDate
	 */
	public void setPltEditWorkDate(String pltEditWorkDate) {
		this.pltEditWorkDate = pltEditWorkDate;
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
	 * @return pltSearchWorkYear
	 */
	public String getPltSearchWorkYear() {
		return pltSearchWorkYear;
	}
	
	/**
	 * @param pltSearchWorkYear セットする pltSearchWorkYear
	 */
	public void setPltSearchWorkYear(String pltSearchWorkYear) {
		this.pltSearchWorkYear = pltSearchWorkYear;
	}
	
	/**
	 * @return pltSearchWorkMonth
	 */
	public String getPltSearchWorkMonth() {
		return pltSearchWorkMonth;
	}
	
	/**
	 * @param pltSearchWorkMonth セットする pltSearchWorkMonth
	 */
	public void setPltSearchWorkMonth(String pltSearchWorkMonth) {
		this.pltSearchWorkMonth = pltSearchWorkMonth;
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
	 * @return aryLblWorkDate
	 */
	public String[] getAryLblWorkDate() {
		return getStringArrayClone(aryLblWorkDate);
	}
	
	/**
	 * @param aryLblWorkDate セットする aryLblWorkDate
	 */
	public void setAryLblWorkDate(String[] aryLblWorkDate) {
		this.aryLblWorkDate = getStringArrayClone(aryLblWorkDate);
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
	 * @return aryLblWorkflow
	 */
	public String[] getAryLblWorkflow() {
		return getStringArrayClone(aryLblWorkflow);
	}
	
	/**
	 * @param aryLblWorkflow セットする aryLblWorkflow
	 */
	public void setAryLblWorkflow(String[] aryLblWorkflow) {
		this.aryLblWorkflow = getStringArrayClone(aryLblWorkflow);
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
	 * @return aryLblRange
	 */
	public String[] getAryLblRange() {
		return getStringArrayClone(aryLblRange);
	}
	
	/**
	 * @param aryLblRange セットする aryLblRange
	 */
	public void setAryLblRange(String[] aryLblRange) {
		this.aryLblRange = getStringArrayClone(aryLblRange);
	}
	
	/**
	 * @return aryCkbSubHolidayRequestListId
	 */
	public String[] getAryCkbSubHolidayRequestListId() {
		return getStringArrayClone(aryCkbSubHolidayRequestListId);
	}
	
	/**
	 * @return aryPltEditRequestYear
	 */
	public String[][] getAryPltEditRequestYear() {
		return getStringArrayClone(aryPltEditRequestYear);
	}
	
	/**
	 * @return aryPltEditRequestMonth
	 */
	public String[][] getAryPltEditRequestMonth() {
		return getStringArrayClone(aryPltEditRequestMonth);
	}
	
	/**
	 * @return aryPltEditRequestDay
	 */
	public String[][] getAryPltEditRequestDay() {
		return getStringArrayClone(aryPltEditRequestDay);
	}
	
	/**
	 * @return aryPltEditHolidayRange
	 */
	public String[][] getAryPltEditHolidayRange() {
		return getStringArrayClone(aryPltEditHolidayRange);
	}
	
	/**
	 * @return aryPltEditWorkDate
	 */
	public String[][] getAryPltEditWorkDate() {
		return getStringArrayClone(aryPltEditWorkDate);
	}
	
	/**
	 * @return aryPltSearchRequestYear
	 */
	public String[][] getAryPltSearchRequestYear() {
		return getStringArrayClone(aryPltSearchRequestYear);
	}
	
	/**
	 * @return aryPltSearchRequestMonth
	 */
	public String[][] getAryPltSearchRequestMonth() {
		return getStringArrayClone(aryPltSearchRequestMonth);
	}
	
	/**
	 * @return aryPltSearchWorkYear
	 */
	public String[][] getAryPltSearchWorkYear() {
		return getStringArrayClone(aryPltSearchWorkYear);
	}
	
	/**
	 * @return aryPltSearchWorkMonth
	 */
	public String[][] getAryPltSearchWorkMonth() {
		return getStringArrayClone(aryPltSearchWorkMonth);
	}
	
	/**
	 * @return aryPltSearchState
	 */
	public String[][] getAryPltSearchState() {
		return getStringArrayClone(aryPltSearchState);
	}
	
	/**
	 * @param aryCkbSubHolidayRequestListId セットする aryCkbSubHolidayRequestListId
	 */
	public void setAryCkbSubHolidayRequestListId(String[] aryCkbSubHolidayRequestListId) {
		this.aryCkbSubHolidayRequestListId = getStringArrayClone(aryCkbSubHolidayRequestListId);
	}
	
	/**
	 * @param aryPltEditRequestYear セットする aryPltEditRequestYear
	 */
	public void setAryPltEditRequestYear(String[][] aryPltEditRequestYear) {
		this.aryPltEditRequestYear = getStringArrayClone(aryPltEditRequestYear);
	}
	
	/**
	 * @param aryPltEditRequestMonth セットする aryPltEditRequestMonth
	 */
	public void setAryPltEditRequestMonth(String[][] aryPltEditRequestMonth) {
		this.aryPltEditRequestMonth = getStringArrayClone(aryPltEditRequestMonth);
	}
	
	/**
	 * @param aryPltEditRequestDay セットする aryPltEditRequestDay
	 */
	public void setAryPltEditRequestDay(String[][] aryPltEditRequestDay) {
		this.aryPltEditRequestDay = getStringArrayClone(aryPltEditRequestDay);
	}
	
	/**
	 * @param aryPltEditHolidayRange セットする aryPltEditHolidayRange
	 */
	public void setAryPltEditHolidayType(String[][] aryPltEditHolidayRange) {
		this.aryPltEditHolidayRange = getStringArrayClone(aryPltEditHolidayRange);
	}
	
	/**
	 * @param aryPltEditWorkDate セットする aryPltEditWorkDate
	 */
	public void setAryPltEditWorkDate(String[][] aryPltEditWorkDate) {
		this.aryPltEditWorkDate = getStringArrayClone(aryPltEditWorkDate);
	}
	
	/**
	 * @param aryPltSearchRequestYear セットする aryPltSearchRequestYear
	 */
	public void setAryPltSearchRequestYear(String[][] aryPltSearchRequestYear) {
		this.aryPltSearchRequestYear = getStringArrayClone(aryPltSearchRequestYear);
	}
	
	/**
	 * @param aryPltSearchRequestMonth セットする aryPltSearchRequestMonth
	 */
	public void setAryPltSearchRequestMonth(String[][] aryPltSearchRequestMonth) {
		this.aryPltSearchRequestMonth = getStringArrayClone(aryPltSearchRequestMonth);
	}
	
	/**
	 * @param aryPltSearchWorkYear セットする aryPltSearchWorkYear
	 */
	public void setAryPltSearchWorkYear(String[][] aryPltSearchWorkYear) {
		this.aryPltSearchWorkYear = getStringArrayClone(aryPltSearchWorkYear);
	}
	
	/**
	 * @param aryPltSearchWorkMonth セットする aryPltSearchWorkMonth
	 */
	public void setAryPltSearchWorkMonth(String[][] aryPltSearchWorkMonth) {
		this.aryPltSearchWorkMonth = getStringArrayClone(aryPltSearchWorkMonth);
	}
	
	/**
	 * @param aryPltSearchState セットする aryPltSearchState
	 */
	public void setAryPltSearchState(String[][] aryPltSearchState) {
		this.aryPltSearchState = getStringArrayClone(aryPltSearchState);
	}
	
	/**
	 * @return lblEmployeeCode
	 */
	@Override
	public String getLblEmployeeCode() {
		return lblEmployeeCode;
	}
	
	/**
	 * @return lblEmployeeName
	 */
	@Override
	public String getLblEmployeeName() {
		return lblEmployeeName;
	}
	
	/**
	 * @return lblSection
	 */
	public String getLblSection() {
		return lblSection;
	}
	
	/**
	 * @param lblEmployeeCode セットする lblEmployeeCode
	 */
	@Override
	public void setLblEmployeeCode(String lblEmployeeCode) {
		this.lblEmployeeCode = lblEmployeeCode;
	}
	
	/**
	 * @param lblEmployeeName セットする lblEmployeeName
	 */
	@Override
	public void setLblEmployeeName(String lblEmployeeName) {
		this.lblEmployeeName = lblEmployeeName;
	}
	
	/**
	 * @param lblSection セットする lblSection
	 */
	public void setLblSection(String lblSection) {
		this.lblSection = lblSection;
	}
	
	/**
	 * @return aryLblCompensationWorkDate
	 */
	public String[] getAryLblCompensationWorkDate() {
		return getStringArrayClone(aryLblCompensationWorkDate);
	}
	
	/**
	 * @return aryLblCompensationExpirationDate
	 */
	public String[] getAryLblCompensationExpirationDate() {
		return getStringArrayClone(aryLblCompensationExpirationDate);
	}
	
	/**
	 * @return aryLblCompensationType
	 */
	public String[] getAryLblCompensationType() {
		return getStringArrayClone(aryLblCompensationType);
	}
	
	/**
	 * @return aryLblCompensationRange
	 */
	public String[] getAryLblCompensationRange() {
		return getStringArrayClone(aryLblCompensationRange);
	}
	
	/**
	 * @param aryLblCompensationWorkDate セットする aryLblCompensationWorkDate
	 */
	public void setAryLblCompensationWorkDate(String[] aryLblCompensationWorkDate) {
		this.aryLblCompensationWorkDate = getStringArrayClone(aryLblCompensationWorkDate);
	}
	
	/**
	 * @param aryLblCompensationExpirationDate セットする aryLblCompensationExpirationDate
	 */
	public void setAryLblCompensationExpirationDate(String[] aryLblCompensationExpirationDate) {
		this.aryLblCompensationExpirationDate = getStringArrayClone(aryLblCompensationExpirationDate);
	}
	
	/**
	 * @param aryLblCompensationType セットする aryLblCompensationType
	 */
	public void setAryLblCompensationType(String[] aryLblCompensationType) {
		this.aryLblCompensationType = getStringArrayClone(aryLblCompensationType);
	}
	
	/**
	 * @param aryLblCompensationRange セットする aryLblCompensationRange
	 */
	public void setAryLblCompensationRange(String[] aryLblCompensationRange) {
		this.aryLblCompensationRange = getStringArrayClone(aryLblCompensationRange);
	}
	
	/**
	 * @return aryLblCompensationDayTh
	 */
	public String[] getAryLblCompensationDayTh() {
		return getStringArrayClone(aryLblCompensationDayTh);
	}
	
	/**
	 * @return aryLblCompensationDayForWorkOnDayOff
	 */
	public String[] getAryLblCompensationDayForWorkOnDayOff() {
		return getStringArrayClone(aryLblCompensationDayForWorkOnDayOff);
	}
	
	/**
	 * @return aryLblCompensationDayForNightWork
	 */
	public String[] getAryLblCompensationDayForNightWork() {
		return getStringArrayClone(aryLblCompensationDayForNightWork);
	}
	
	/**
	 * @param aryLblCompensationDayTh セットする aryLblCompensationDayTh
	 */
	public void setAryLblCompensationDayTh(String[] aryLblCompensationDayTh) {
		this.aryLblCompensationDayTh = getStringArrayClone(aryLblCompensationDayTh);
	}
	
	/**
	 * @param aryLblCompensationDayForWorkOnDayOff セットする aryLblCompensationDayForWorkOnDayOff
	 */
	public void setAryLblCompensationDayForWorkOnDayOff(String[] aryLblCompensationDayForWorkOnDayOff) {
		this.aryLblCompensationDayForWorkOnDayOff = getStringArrayClone(aryLblCompensationDayForWorkOnDayOff);
	}
	
	/**
	 * @param aryLblCompensationDayForNightWork セットする aryLblCompensationDayForNightWork
	 */
	public void setAryLblCompensationDayForNightWork(String[] aryLblCompensationDayForNightWork) {
		this.aryLblCompensationDayForNightWork = getStringArrayClone(aryLblCompensationDayForNightWork);
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
	
}
