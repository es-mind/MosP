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
 * 残業申請情報を格納する。
 */
public class OvertimeRequestVo extends TimeVo {
	
	private static final long	serialVersionUID	= -8430301907353630624L;
	
	private long				recordId;
	
	private String				pltEditRequestYear;
	private String				pltEditRequestMonth;
	private String				pltEditRequestDay;
	private String				pltEditRequestHour;
	private String				pltEditRequestMinute;
	private String				pltEditOverTimeType;
	private String				txtEditRequestReason;
	private String				lblEditState;
	
	private String				pltSearchState;
	private String				pltSearchScheduleOver;
	private String				pltSearchOverTimeType;
	private String				pltSearchRequestYear;
	private String				pltSearchRequestMonth;
	private String				lblRemainderWeek;
	private String				lblRemainderMonth;
	private String[]			aryLblDate;
	private String[]			aryLblOverTimeTypeName;
	private String[]			aryLblOverTimeTypeCode;
	private String[]			aryLblScheduleTime;
	private String[]			aryLblResultTime;
	private String[]			aryLblRequestReason;
	private String[]			aryLblState;
	private String[]			aryStateStyle;
	private String[]			aryLblApprover;
	
	private String[]			aryCkbOvertimeRequestListId;
	
	private String[][]			aryPltEditRequestYear;
	private String[][]			aryPltEditRequestMonth;
	private String[][]			aryPltEditRequestDay;
	private String[][]			aryPltEditRequestHour;
	private String[][]			aryPltEditRequestMinute;
	private String[][]			aryPltEditOverTimeType;
	private String[][]			aryPltSearchState;
	private String[][]			aryPltSearchScheduleOver;
	private String[][]			aryPltSearchOverTimeType;
	private String[][]			aryPltSearchRequestYear;
	private String[][]			aryPltSearchRequestMonth;
	
	private long				tmdOvertimeRequestId;
	private String[]			aryLblOnOff;
	private String				jsBeforeOvertimeFlag;
	
	/**
	 * ワークフロー番号。<br>
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
	 * @return pltEditOverTimeType
	 */
	public String getPltEditOverTimeType() {
		return pltEditOverTimeType;
	}
	
	/**
	 * @param pltEditOverTimeType セットする pltEditOverTimeType
	 */
	public void setPltEditOverTimeType(String pltEditOverTimeType) {
		this.pltEditOverTimeType = pltEditOverTimeType;
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
	 * @return lblEditState
	 */
	public String getLblEditState() {
		return lblEditState;
	}
	
	/**
	 * @param lblEditState セットする lblEditState
	 */
	public void setLblEditState(String lblEditState) {
		this.lblEditState = lblEditState;
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
	 * @return pltSearchScheduleOver
	 */
	public String getPltSearchScheduleOver() {
		return pltSearchScheduleOver;
	}
	
	/**
	 * @param pltSearchScheduleOver セットする pltSearchScheduleOver
	 */
	public void setPltSearchScheduleOver(String pltSearchScheduleOver) {
		this.pltSearchScheduleOver = pltSearchScheduleOver;
	}
	
	/**
	 * @return pltSearchOverTimeType
	 */
	public String getPltSearchOverTimeType() {
		return pltSearchOverTimeType;
	}
	
	/**
	 * @param pltSearchOverTimeType セットする pltSearchOverTimeType
	 */
	public void setPltSearchOverTimeType(String pltSearchOverTimeType) {
		this.pltSearchOverTimeType = pltSearchOverTimeType;
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
	 * @return lblRemainderWeek
	 */
	public String getLblRemainderWeek() {
		return lblRemainderWeek;
	}
	
	/**
	 * @param lblRemainderWeek セットする lblRemainderWeek
	 */
	public void setLblRemainderWeek(String lblRemainderWeek) {
		this.lblRemainderWeek = lblRemainderWeek;
	}
	
	/**
	 * @return lblRemainderMonth
	 */
	public String getLblRemainderMonth() {
		return lblRemainderMonth;
	}
	
	/**
	 * @param lblRemainderMonth セットする lblRemainderMonth
	 */
	public void setLblRemainderMonth(String lblRemainderMonth) {
		this.lblRemainderMonth = lblRemainderMonth;
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
	 * @return aryLblOverTimeTypeName
	 */
	public String[] getAryLblOverTimeTypeName() {
		return getStringArrayClone(aryLblOverTimeTypeName);
	}
	
	/**
	 * @param aryLblOverTimeTypeName セットする aryLblOverTimeTypeName
	 */
	public void setAryLblOverTimeTypeName(String[] aryLblOverTimeTypeName) {
		this.aryLblOverTimeTypeName = getStringArrayClone(aryLblOverTimeTypeName);
	}
	
	/**
	 * @return aryLblOverTimeTypeCode
	 */
	public String[] getAryLblOverTimeTypeCode() {
		return getStringArrayClone(aryLblOverTimeTypeCode);
	}
	
	/**
	 * @param aryLblOverTimeTypeCode セットする aryLblOverTimeTypeCode
	 */
	public void setAryLblOverTimeTypeCode(String[] aryLblOverTimeTypeCode) {
		this.aryLblOverTimeTypeCode = getStringArrayClone(aryLblOverTimeTypeCode);
	}
	
	/**
	 * @return aryLblScheduleTime
	 */
	public String[] getAryLblScheduleTime() {
		return getStringArrayClone(aryLblScheduleTime);
	}
	
	/**
	 * @param aryLblScheduleTime セットする aryLblScheduleTime
	 */
	public void setAryLblScheduleTime(String[] aryLblScheduleTime) {
		this.aryLblScheduleTime = getStringArrayClone(aryLblScheduleTime);
	}
	
	/**
	 * @return aryLblResultTime
	 */
	public String[] getAryLblResultTime() {
		return getStringArrayClone(aryLblResultTime);
	}
	
	/**
	 * @param aryLblResultTime セットする aryLblResultTime
	 */
	public void setAryLblResultTime(String[] aryLblResultTime) {
		this.aryLblResultTime = getStringArrayClone(aryLblResultTime);
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
	 * @return aryCkbOvertimeRequestListId
	 */
	public String[] getAryCkbOvertimeRequestListId() {
		return getStringArrayClone(aryCkbOvertimeRequestListId);
	}
	
	/**
	 * @param aryCkbOvertimeRequestListId セットする aryCkbOvertimeRequestListId
	 */
	public void setAryCkbOvertimeRequestListId(String[] aryCkbOvertimeRequestListId) {
		this.aryCkbOvertimeRequestListId = getStringArrayClone(aryCkbOvertimeRequestListId);
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
	 * @return aryPltEditOverTimeType
	 */
	public String[][] getAryPltEditOverTimeType() {
		return getStringArrayClone(aryPltEditOverTimeType);
	}
	
	/**
	 * @param aryPltEditOverTimeType セットする aryPltEditOverTimeType
	 */
	public void setAryPltEditOverTimeType(String[][] aryPltEditOverTimeType) {
		this.aryPltEditOverTimeType = getStringArrayClone(aryPltEditOverTimeType);
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
	 * @return aryPltSearchScheduleOver
	 */
	public String[][] getAryPltSearchScheduleOver() {
		return getStringArrayClone(aryPltSearchScheduleOver);
	}
	
	/**
	 * @param aryPltSearchScheduleOver セットする aryPltSearchScheduleOver
	 */
	public void setAryPltSearchScheduleOver(String[][] aryPltSearchScheduleOver) {
		this.aryPltSearchScheduleOver = getStringArrayClone(aryPltSearchScheduleOver);
	}
	
	/**
	 * @return aryPltSearchOverTimeType
	 */
	public String[][] getAryPltSearchOverTimeType() {
		return getStringArrayClone(aryPltSearchOverTimeType);
	}
	
	/**
	 * @param aryPltSearchOverTimeType セットする aryPltSearchOverTimeType
	 */
	public void setAryPltSearchOverTimeType(String[][] aryPltSearchOverTimeType) {
		this.aryPltSearchOverTimeType = getStringArrayClone(aryPltSearchOverTimeType);
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
	 * @return tmdOvertimeRequestId
	 */
	public long getTmdOvertimeRequestId() {
		return tmdOvertimeRequestId;
	}
	
	/**
	 * @param tmdOvertimeRequestId セットする tmdOvertimeRequestId
	 */
	public void setTmdOvertimeRequestId(long tmdOvertimeRequestId) {
		this.tmdOvertimeRequestId = tmdOvertimeRequestId;
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
	 * @return jsBeforeOvertimeFlag
	 */
	public String getJsBeforeOvertimeFlag() {
		return jsBeforeOvertimeFlag;
	}
	
	/**
	 * @param jsBeforeOvertimeFlag セットする jsBeforeOvertimeFlag
	 */
	public void setJsBeforeOvertimeFlag(String jsBeforeOvertimeFlag) {
		this.jsBeforeOvertimeFlag = jsBeforeOvertimeFlag;
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
