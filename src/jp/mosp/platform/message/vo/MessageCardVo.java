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
package jp.mosp.platform.message.vo;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * メッセージ設定詳細画面の情報を格納する。
 */
public class MessageCardVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -989315305341182154L;
	
	/**
	 * 公開終了日(年)。
	 */
	private String				txtEndYear;
	
	/**
	 * 公開終了日(月)。
	 */
	private String				txtEndMonth;
	
	/**
	 * 公開終了日(日)。
	 */
	private String				txtEndDay;
	
	/**
	 * メッセージ区分。
	 */
	private String				pltMessageType;
	
	/**
	 * 重要度。
	 */
	private String				pltImportance;
	
	/**
	 * 勤務地。
	 */
	private String				pltWorkPlace;
	
	/**
	 * 雇用契約。
	 */
	private String				pltEmployment;
	
	/**
	 * 所属。
	 */
	private String				pltSection;
	
	/**
	 * 職位。
	 */
	private String				pltPosition;
	
	/**
	 * 個人指定欄。
	 */
	private String				txtEmployeeCode;
	
	/**
	 * メッセージタイトル。
	 */
	private String				txtMessageTitle;
	
	/**
	 * メッセージ本文。
	 */
	private String				txtMessage;
	
	/**
	 * メッセージNo。
	 */
	private String				lblMessageNo;
	
	/**
	 * 登録者。
	 */
	private String				lblRegistUser;
	
	/**
	 * 社員名表示。
	 */
	private String				lblEmployeeName;
	
	/**
	 * 適用範囲区分ラジオボタン。<br>
	 */
	private String				radApplicationType;
	
	/**
	 * 勤務地プルダウン。
	 */
	private String[][]			aryPltWorkPlace;
	
	/**
	 * 雇用契約プルダウン。
	 */
	private String[][]			aryPltEmployment;
	
	/**
	 * 所属プルダウン。
	 */
	private String[][]			aryPltSection;
	
	/**
	 * 職位プルダウン。
	 */
	private String[][]			aryPltPosition;
	
	
	/**
	 * @return txtEndYear
	 */
	public String getTxtEndYear() {
		return txtEndYear;
	}
	
	/**
	 * @param txtEndYear セットする txtEndYear
	 */
	public void setTxtEndYear(String txtEndYear) {
		this.txtEndYear = txtEndYear;
	}
	
	/**
	 * @return txtEndMonth
	 */
	public String getTxtEndMonth() {
		return txtEndMonth;
	}
	
	/**
	 * @param txtEndMonth セットする txtEndMonth
	 */
	public void setTxtEndMonth(String txtEndMonth) {
		this.txtEndMonth = txtEndMonth;
	}
	
	/**
	 * @return txtEndDay
	 */
	public String getTxtEndDay() {
		return txtEndDay;
	}
	
	/**
	 * @param txtEndDay セットする txtEndDay
	 */
	public void setTxtEndDay(String txtEndDay) {
		this.txtEndDay = txtEndDay;
	}
	
	/**
	 * @return pltMessageType
	 */
	public String getPltMessageType() {
		return pltMessageType;
	}
	
	/**
	 * @param pltMessageType セットする pltMessageType
	 */
	public void setPltMessageType(String pltMessageType) {
		this.pltMessageType = pltMessageType;
	}
	
	/**
	 * @return pltImportance
	 */
	public String getPltImportance() {
		return pltImportance;
	}
	
	/**
	 * @param pltImportance セットする pltImportance
	 */
	public void setPltImportance(String pltImportance) {
		this.pltImportance = pltImportance;
	}
	
	/**
	 * @return pltWorkPlace
	 */
	public String getPltWorkPlace() {
		return pltWorkPlace;
	}
	
	/**
	 * @param pltWorkPlace セットする pltWorkPlace
	 */
	public void setPltWorkPlace(String pltWorkPlace) {
		this.pltWorkPlace = pltWorkPlace;
	}
	
	/**
	 * @return pltEmployment
	 */
	public String getPltEmployment() {
		return pltEmployment;
	}
	
	/**
	 * @param pltEmployment セットする pltEmployment
	 */
	public void setPltEmployment(String pltEmployment) {
		this.pltEmployment = pltEmployment;
	}
	
	/**
	 * @return pltSection
	 */
	public String getPltSection() {
		return pltSection;
	}
	
	/**
	 * @param pltSection セットする pltSection
	 */
	public void setPltSection(String pltSection) {
		this.pltSection = pltSection;
	}
	
	/**
	 * @return pltPosition
	 */
	public String getPltPosition() {
		return pltPosition;
	}
	
	/**
	 * @param pltPosition セットする pltPosition
	 */
	public void setPltPosition(String pltPosition) {
		this.pltPosition = pltPosition;
	}
	
	/**
	 * @return txtEmployeeCode
	 */
	public String getTxtEmployeeCode() {
		return txtEmployeeCode;
	}
	
	/**
	 * @param txtEmployeeCode セットする txtEmployeeCode
	 */
	public void setTxtEmployeeCode(String txtEmployeeCode) {
		this.txtEmployeeCode = txtEmployeeCode;
	}
	
	/**
	 * @return txtMessageTitle
	 */
	public String getTxtMessageTitle() {
		return txtMessageTitle;
	}
	
	/**
	 * @param txtMessageTitle セットする txtMessageTitle
	 */
	public void setTxtMessageTitle(String txtMessageTitle) {
		this.txtMessageTitle = txtMessageTitle;
	}
	
	/**
	 * @return txtMessage
	 */
	public String getTxtMessage() {
		return txtMessage;
	}
	
	/**
	 * @param txtMessage セットする txtMessage
	 */
	public void setTxtMessage(String txtMessage) {
		this.txtMessage = txtMessage;
	}
	
	/**
	 * @return lblMessageNo
	 */
	public String getLblMessageNo() {
		return lblMessageNo;
	}
	
	/**
	 * @param lblMessageNo セットする lblMessageNo
	 */
	public void setLblMessageNo(String lblMessageNo) {
		this.lblMessageNo = lblMessageNo;
	}
	
	/**
	 * @return lblRegistUser
	 */
	public String getLblRegistUser() {
		return lblRegistUser;
	}
	
	/**
	 * @param lblRegistUser セットする lblRegistUser
	 */
	public void setLblRegistUser(String lblRegistUser) {
		this.lblRegistUser = lblRegistUser;
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
	 * @return radApplicationType
	 */
	public String getRadApplicationType() {
		return radApplicationType;
	}
	
	/**
	 * @param radApplicationType セットする radApplicationType
	 */
	public void setRadApplicationType(String radApplicationType) {
		this.radApplicationType = radApplicationType;
	}
	
	/**
	 * @return aryPltWorkPlace
	 */
	public String[][] getAryPltWorkPlace() {
		return getStringArrayClone(aryPltWorkPlace);
	}
	
	/**
	 * @param aryPltWorkPlace セットする aryPltWorkPlace
	 */
	public void setAryPltWorkPlace(String[][] aryPltWorkPlace) {
		this.aryPltWorkPlace = getStringArrayClone(aryPltWorkPlace);
	}
	
	/**
	 * @return aryPltEmployment
	 */
	public String[][] getAryPltEmployment() {
		return getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @param aryPltEmployment セットする aryPltEmployment
	 */
	public void setAryPltEmployment(String[][] aryPltEmployment) {
		this.aryPltEmployment = getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @return aryPltSection
	 */
	public String[][] getAryPltSection() {
		return getStringArrayClone(aryPltSection);
	}
	
	/**
	 * @param aryPltSection セットする aryPltSection
	 */
	public void setAryPltSection(String[][] aryPltSection) {
		this.aryPltSection = getStringArrayClone(aryPltSection);
	}
	
	/**
	 * @return aryPltPosition
	 */
	public String[][] getAryPltPosition() {
		return getStringArrayClone(aryPltPosition);
	}
	
	/**
	 * @param aryPltPosition セットする aryPltPosition
	 */
	public void setAryPltPosition(String[][] aryPltPosition) {
		this.aryPltPosition = getStringArrayClone(aryPltPosition);
	}
	
}
