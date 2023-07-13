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
 * 承認解除申請情報を格納する。
 */
public class CancellationRequestVo extends TimeVo {
	
	private static final long	serialVersionUID	= 4015483998388544714L;
	
	private long				workflow;
	private String				lblRequestDate;
	private String				lblRequestType;
	private String				lblRequestInfo;
	private String				lblState;
	private String				txtEditRequestReason;
	private String				ckbWithdrawn;
	private String				jsRequestType;
	
	private String				pltSearchApprovalType;
	private String				pltSearchRequestYear;
	private String				pltSearchRequestMonth;
	private String				pltSearchRequestDay;
	
	private String[]			aryLblRequestDate;
	private String[]			aryLblRequestType;
	private String[]			aryLblRequestInfo;
	private String[]			aryLblState;
	private String[]			aryLblApproverName;
	private String[]			aryBackColor;
	private String[]			aryHistoryCmd;
	private long[]				aryWorkflow;
	
	private String[][]			aryPltSearchRequestYear;
	private String[][]			aryPltSearchRequestMonth;
	private String[][]			aryPltSearchRequestDay;
	
	
	/**
	 * @return workflow
	 */
	public long getWorkflow() {
		return workflow;
	}
	
	/**
	 * @param workflow セットする workflow
	 */
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
	/**
	 * @return lblRequestDate
	 */
	public String getLblRequestDate() {
		return lblRequestDate;
	}
	
	/**
	 * @param lblRequestDate セットする lblRequestDate
	 */
	public void setLblRequestDate(String lblRequestDate) {
		this.lblRequestDate = lblRequestDate;
	}
	
	/**
	 * @return lblRequestType
	 */
	public String getLblRequestType() {
		return lblRequestType;
	}
	
	/**
	 * @param lblRequestType セットする lblRequestType
	 */
	public void setLblRequestType(String lblRequestType) {
		this.lblRequestType = lblRequestType;
	}
	
	/**
	 * @return lblRequestInfo
	 */
	public String getLblRequestInfo() {
		return lblRequestInfo;
	}
	
	/**
	 * @param lblRequestInfo セットする lblRequestInfo
	 */
	public void setLblRequestInfo(String lblRequestInfo) {
		this.lblRequestInfo = lblRequestInfo;
	}
	
	/**
	 * @return lblState
	 */
	public String getLblState() {
		return lblState;
	}
	
	/**
	 * @param lblState セットする lblState
	 */
	public void setLblState(String lblState) {
		this.lblState = lblState;
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
	 * @return aryLblApproverName
	 */
	public String[] getAryLblApproverName() {
		return getStringArrayClone(aryLblApproverName);
	}
	
	/**
	 * @param aryLblApproverName セットする aryLblApproverName
	 */
	public void setAryLblApproverName(String[] aryLblApproverName) {
		this.aryLblApproverName = getStringArrayClone(aryLblApproverName);
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
	 * @return aryHistoryCmd
	 */
	public String[] getAryHistoryCmd() {
		return getStringArrayClone(aryHistoryCmd);
	}
	
	/**
	 * @param aryHistoryCmd セットする aryHistoryCmd
	 */
	public void setAryHistoryCmd(String[] aryHistoryCmd) {
		this.aryHistoryCmd = getStringArrayClone(aryHistoryCmd);
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
	 * @return ckbWithdrawn
	 */
	public String getCkbWithdrawn() {
		return ckbWithdrawn;
	}
	
	/**
	 * @param ckbWithdrawn セットする ckbWithdrawn
	 */
	public void setCkbWithdrawn(String ckbWithdrawn) {
		this.ckbWithdrawn = ckbWithdrawn;
	}
	
	/**
	 * @return jsRequestType
	 */
	public String getJsRequestType() {
		return jsRequestType;
	}
	
	/**
	 * @param jsRequestType セットする jsRequestType
	 */
	public void setJsRequestType(String jsRequestType) {
		this.jsRequestType = jsRequestType;
	}
}
