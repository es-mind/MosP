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
package jp.mosp.time.base;

import java.util.Date;

import jp.mosp.platform.base.PlatformVo;

/**
 * MosP勤怠管理におけるVOの基本となる情報を格納する。<br>
 */
public abstract class TimeVo extends PlatformVo {
	
	private static final long	serialVersionUID	= -296203972754983338L;
	
	/**
	 * 勤怠管理共通情報個人ID。<br>
	 */
	private String				personalId;
	
	/**
	 * 勤怠管理共通情報対象日。<br>
	 */
	private Date				targetDate;
	
	/**
	 * 勤怠管理共通情報社員コード。<br>
	 */
	private String				lblEmployeeCode;
	
	/**
	 * 勤怠管理共通情報社員名。<br>
	 */
	private String				lblEmployeeFirstName;
	
	/**
	 * 勤怠管理共通情報社員名。<br>
	 */
	private String				lblEmployeeLastName;
	
	/**
	 * 勤怠管理共通情報社員名。<br>
	 */
	private String				lblEmployeeName;
	
	/**
	 * 勤怠管理共通情報所属。<br>
	 */
	private String				lblSectionName;
	
	/**
	 * 勤怠管理共通情報検索有効年。<br>
	 */
	private String				txtSearchActivateYear;
	
	/**
	 * 勤怠管理共通情報検索有効月。<br>
	 */
	private String				txtSearchActivateMonth;
	
	/**
	 * 勤怠管理共通情報検索有効日。<br>
	 */
	private String				txtSearchActivateDay;
	
	/**
	 * 勤怠管理共通情報有効日。<br>
	 */
	private String				txtActivateYear;
	
	/**
	 * 勤怠管理共通情報有効日。<br>
	 */
	private String				txtActivateMonth;
	
	/**
	 * 勤怠管理共通情報有効日。<br>
	 */
	private String				txtActivateDay;
	
	/**
	 * 勤怠管理共通情報検索社員コード。<br>
	 */
	private String				txtSearchEmployeeCode;
	
	/**
	 * 勤怠管理共通情報編集有効年。<br>
	 */
	private String				txtEditActivateYear;
	
	/**
	 * 勤怠管理共通情報編集有効月。<br>
	 */
	private String				txtEditActivateMonth;
	
	/**
	 * 勤怠管理共通情報編集有効日。<br>
	 */
	private String				txtEditActivateDay;
	
	/**
	 * 勤怠管理共通情報更新有効年。<br>
	 */
	private String				txtUpdateActivateYear;
	
	/**
	 * 勤怠管理共通情報更新有効月。<br>
	 */
	private String				txtUpdateActivateMonth;
	
	/**
	 * 勤怠管理共通情報更新有効日。<br>
	 */
	private String				txtUpdateActivateDay;
	
	/**
	 * 勤怠管理共通時差出勤有無領域1。<br>
	 */
	private String				jsModeDifferenceRequest1;
	
	/**
	 * 勤怠管理共通時差出勤日1。<br>
	 */
	private String				jsModeRequestDate1;
	
	
	/**
	 * VOの初期設定を行う。<br>
	 * <br>
	 */
	public TimeVo() {
		// 処理無し
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
	 * @return targetDate
	 */
	public Date getTargetDate() {
		return getDateClone(targetDate);
	}
	
	/**
	 * @param targetDate セットする targetDate
	 */
	public void setTargetDate(Date targetDate) {
		this.targetDate = getDateClone(targetDate);
	}
	
	/**
	 * @return lblEmployeeCode
	 */
	public String getLblEmployeeCode() {
		return lblEmployeeCode;
	}
	
	/**
	 * @return lblEmployeeFirstName
	 */
	public String getLblEmployeeFirstName() {
		return lblEmployeeFirstName;
	}
	
	/**
	 * @return lblEmployeeLastName
	 */
	public String getLblEmployeeLastName() {
		return lblEmployeeLastName;
	}
	
	/**
	 * @return lblSectionName
	 */
	public String getLblSectionName() {
		return lblSectionName;
	}
	
	/**
	 * @param lblEmployeeCode セットする lblEmployeeCode
	 */
	public void setLblEmployeeCode(String lblEmployeeCode) {
		this.lblEmployeeCode = lblEmployeeCode;
	}
	
	/**
	 * @param lblEmployeeFirstName セットする lblEmployeeFirstName
	 */
	public void setLblEmployeeFirstName(String lblEmployeeFirstName) {
		this.lblEmployeeFirstName = lblEmployeeFirstName;
	}
	
	/**
	 * @param lblEmployeeLastName セットする lblEmployeeLastName
	 */
	public void setLblEmployeeLastName(String lblEmployeeLastName) {
		this.lblEmployeeLastName = lblEmployeeLastName;
	}
	
	/**
	 * @param lblSectionName セットする lblSectionName
	 */
	public void setLblSectionName(String lblSectionName) {
		this.lblSectionName = lblSectionName;
	}
	
	/**
	 * @return txtSearchActivateYear
	 */
	public String getTxtSearchActivateYear() {
		return txtSearchActivateYear;
	}
	
	/**
	 * @return txtSearchActivateMonth
	 */
	public String getTxtSearchActivateMonth() {
		return txtSearchActivateMonth;
	}
	
	/**
	 * @return txtSearchActivateDay
	 */
	public String getTxtSearchActivateDay() {
		return txtSearchActivateDay;
	}
	
	/**
	 * @return txtSearchEmployeeCode
	 */
	public String getTxtSearchEmployeeCode() {
		return txtSearchEmployeeCode;
	}
	
	/**
	 * @param txtSearchActivateYear セットする txtSearchActivateYear
	 */
	public void setTxtSearchActivateYear(String txtSearchActivateYear) {
		this.txtSearchActivateYear = txtSearchActivateYear;
	}
	
	/**
	 * @param txtSearchActivateMonth セットする txtSearchActivateMonth
	 */
	public void setTxtSearchActivateMonth(String txtSearchActivateMonth) {
		this.txtSearchActivateMonth = txtSearchActivateMonth;
	}
	
	/**
	 * @param txtSearchActivateDay セットする txtSearchActivateDay
	 */
	public void setTxtSearchActivateDay(String txtSearchActivateDay) {
		this.txtSearchActivateDay = txtSearchActivateDay;
	}
	
	/**
	 * @param txtSearchEmployeeCode セットする txtSearchEmployeeCode
	 */
	public void setTxtSearchEmployeeCode(String txtSearchEmployeeCode) {
		this.txtSearchEmployeeCode = txtSearchEmployeeCode;
	}
	
	/**
	 * @return lblEmployeeName
	 */
	public String getLblEmployeeName() {
		return lblEmployeeName;
	}
	
	/**
	 * @param lblEmployeeName セットする lblEmployeeName
	 */
	public void setLblEmployeeName(String lblEmployeeName) {
		this.lblEmployeeName = lblEmployeeName;
	}
	
	/**
	 * @return txtEditActivateYear
	 */
	public String getTxtEditActivateYear() {
		return txtEditActivateYear;
	}
	
	/**
	 * @return txtEditActivateMonth
	 */
	public String getTxtEditActivateMonth() {
		return txtEditActivateMonth;
	}
	
	/**
	 * @return txtEditActivateDay
	 */
	public String getTxtEditActivateDay() {
		return txtEditActivateDay;
	}
	
	/**
	 * @return txtUpdateActivateYear
	 */
	public String getTxtUpdateActivateYear() {
		return txtUpdateActivateYear;
	}
	
	/**
	 * @return txtUpdateActivateMonth
	 */
	public String getTxtUpdateActivateMonth() {
		return txtUpdateActivateMonth;
	}
	
	/**
	 * @return txtUpdateActivateDay
	 */
	public String getTxtUpdateActivateDay() {
		return txtUpdateActivateDay;
	}
	
	/**
	 * @param txtEditActivateYear セットする txtEditActivateYear
	 */
	public void setTxtEditActivateYear(String txtEditActivateYear) {
		this.txtEditActivateYear = txtEditActivateYear;
	}
	
	/**
	 * @param txtEditActivateMonth セットする txtEditActivateMonth
	 */
	public void setTxtEditActivateMonth(String txtEditActivateMonth) {
		this.txtEditActivateMonth = txtEditActivateMonth;
	}
	
	/**
	 * @param txtEditActivateDay セットする txtEditActivateDay
	 */
	public void setTxtEditActivateDay(String txtEditActivateDay) {
		this.txtEditActivateDay = txtEditActivateDay;
	}
	
	/**
	 * @param txtUpdateActivateYear セットする txtUpdateActivateYear
	 */
	public void setTxtUpdateActivateYear(String txtUpdateActivateYear) {
		this.txtUpdateActivateYear = txtUpdateActivateYear;
	}
	
	/**
	 * @param txtUpdateActivateMonth セットする txtUpdateActivateMonth
	 */
	public void setTxtUpdateActivateMonth(String txtUpdateActivateMonth) {
		this.txtUpdateActivateMonth = txtUpdateActivateMonth;
	}
	
	/**
	 * @param txtUpdateActivateDay セットする txtUpdateActivateDay
	 */
	public void setTxtUpdateActivateDay(String txtUpdateActivateDay) {
		this.txtUpdateActivateDay = txtUpdateActivateDay;
	}
	
	/**
	 * @return txtActivateYear
	 */
	public String getTxtActivateYear() {
		return txtActivateYear;
	}
	
	/**
	 * @return txtActivateMonth
	 */
	public String getTxtActivateMonth() {
		return txtActivateMonth;
	}
	
	/**
	 * @return txtActivateDay
	 */
	public String getTxtActivateDay() {
		return txtActivateDay;
	}
	
	/**
	 * @param txtActivateYear セットする txtActivateYear
	 */
	public void setTxtActivateYear(String txtActivateYear) {
		this.txtActivateYear = txtActivateYear;
	}
	
	/**
	 * @param txtActivateMonth セットする txtActivateMonth
	 */
	public void setTxtActivateMonth(String txtActivateMonth) {
		this.txtActivateMonth = txtActivateMonth;
	}
	
	/**
	 * @param txtActivateDay セットする txtActivateDay
	 */
	public void setTxtActivateDay(String txtActivateDay) {
		this.txtActivateDay = txtActivateDay;
	}
	
	/**
	 * @return jsModeDifferenceRequest1
	 */
	public String getJsModeDifferenceRequest1() {
		return jsModeDifferenceRequest1;
	}
	
	/**
	 * @param jsModeDifferenceRequest1 セットする jsModeDifferenceRequest1
	 */
	public void setJsModeDifferenceRequest1(String jsModeDifferenceRequest1) {
		this.jsModeDifferenceRequest1 = jsModeDifferenceRequest1;
	}
	
	/**
	 * @return jsModeRequestDate1
	 */
	public String getJsModeRequestDate1() {
		return jsModeRequestDate1;
	}
	
	/**
	 * @param jsModeRequestDate1 セットする jsModeRequestDate1
	 */
	public void setJsModeRequestDate1(String jsModeRequestDate1) {
		this.jsModeRequestDate1 = jsModeRequestDate1;
	}
	
}
