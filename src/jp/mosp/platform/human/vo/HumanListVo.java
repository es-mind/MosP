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
package jp.mosp.platform.human.vo;

import java.util.Date;

import jp.mosp.platform.base.PlatformVo;

/**
 * 社員一覧画面の情報を格納する。
 */
public class HumanListVo extends PlatformVo {
	
	private static final long	serialVersionUID	= -5594351800558462224L;
	
	private String				txtActivateYear;
	private String				txtActivateMonth;
	private String				txtActivateDay;
	private String				txtEmployeeCode;
	private String				txtLastName;
	private String				pltWorkPlaceAbbr;
	private String				pltSectionAbbr;
	private String				pltPositionName;
	private String				pltEmploymentName;
	private String				txtFirstName;
	private String				txtLastKana;
	private String				txtFirstKana;
	private String				pltInfoType;
	private String				txtSearchWord;
	private String				pltState;
	private String				pltPfmHumanId;
	private String				pltEmployeeCode;
	private String				pltLastName;
	private String				pltFirstName;
	private String				pltLastKana;
	private String				pltFirstKana;
	private String[]			aryEmployeeCode;
	private String[]			aryEmployeeName;
	private String[]			aryEmployeeKana;
	private String[]			aryWorkPlaceAbbr;
	private String[]			arySection;
	private String[]			aryPositionAbbr;
	private String[]			aryEmploymentAbbr;
	private String[]			aryState;
	private String				modeSearchExpansion;
	
	private boolean				jsSearchConditionRequired;
	
	/**
	 * 個人ID配列。<br>
	 * 一覧に表示されている個人IDを格納する。<br>
	 * 画面遷移に用いる。<br>
	 */
	private String[]			aryPersonalId;
	
	/**
	 * 検索有効日。<br>
	 * 人事情報一覧画面等へ遷移する際に送信される有効日。<br>
	 */
	private Date				activateDate;
	
	// プルダウンリスト
	/**
	 * 勤務地プルダウンリスト
	 */
	private String[][]			aryPltWorkPlace;
	/**
	 * 所属(略称)プルダウンリスト
	 */
	private String[][]			aryPltSectionAbbr;
	/**
	 * 職位プルダウンリスト
	 */
	private String[][]			aryPltPosition;
	/**
	 * 雇用契約プルダウンリスト
	 */
	private String[][]			aryPltEmployment;
	
	/**
	 * フリーワード検索プルダウンリスト
	 */
	private String[][]			aryPltFreeWordTypes;
	
	
	/**
	 * @param txtActivateYear セットする txtActivateYear
	 */
	public void setTxtActivateYear(String txtActivateYear) {
		this.txtActivateYear = txtActivateYear;
	}
	
	/**
	 * @return txtActivateYear
	 */
	public String getTxtActivateYear() {
		return txtActivateYear;
	}
	
	/**
	 * @param txtActivateMonth セットする txtActivateMonth
	 */
	public void setTxtActivateMonth(String txtActivateMonth) {
		this.txtActivateMonth = txtActivateMonth;
	}
	
	/**
	 * @return txtActivateMonth
	 */
	public String getTxtActivateMonth() {
		return txtActivateMonth;
	}
	
	/**
	 * @param txtActivateDay セットする txtActivateDay
	 */
	public void setTxtActivateDay(String txtActivateDay) {
		this.txtActivateDay = txtActivateDay;
	}
	
	/**
	 * @return txtActivateDay
	 */
	public String getTxtActivateDay() {
		return txtActivateDay;
	}
	
	/**
	 * @param txtEmployeeCode セットする txtEmployeeCode
	 */
	public void setTxtEmployeeCode(String txtEmployeeCode) {
		this.txtEmployeeCode = txtEmployeeCode;
	}
	
	/**
	 * @return txtEmployeeCode
	 */
	public String getTxtEmployeeCode() {
		return txtEmployeeCode;
	}
	
	/**
	 * @param txtLastName セットする txtLastName
	 */
	public void setTxtLastName(String txtLastName) {
		this.txtLastName = txtLastName;
	}
	
	/**
	 * @return txtLastName
	 */
	public String getTxtLastName() {
		return txtLastName;
	}
	
	/**
	 * @param pltSectionAbbr セットする pltSectionAbbr
	 */
	public void setPltSectionAbbr(String pltSectionAbbr) {
		this.pltSectionAbbr = pltSectionAbbr;
	}
	
	/**
	 * @return pltSectionAbbr
	 */
	public String getPltSectionAbbr() {
		return pltSectionAbbr;
	}
	
	/**
	 * @return pltWorkPlaceAbbr
	 */
	public String getPltWorkPlaceAbbr() {
		return pltWorkPlaceAbbr;
	}
	
	/**
	 * @param pltWorkPlaceAbbr セットする pltWorkPlaceAbbr
	 */
	public void setPltWorkPlaceAbbr(String pltWorkPlaceAbbr) {
		this.pltWorkPlaceAbbr = pltWorkPlaceAbbr;
	}
	
	/**
	 * @param pltPositionName セットする pltPositionName
	 */
	public void setPltPositionName(String pltPositionName) {
		this.pltPositionName = pltPositionName;
	}
	
	/**
	 * @return pltPositionName
	 */
	public String getPltPositionName() {
		return pltPositionName;
	}
	
	/**
	 * @param pltEmploymentName セットする pltEmploymentName
	 */
	public void setPltEmploymentName(String pltEmploymentName) {
		this.pltEmploymentName = pltEmploymentName;
	}
	
	/**
	 * @return pltEmploymentName
	 */
	public String getPltEmploymentName() {
		return pltEmploymentName;
	}
	
	/**
	 * @param txtFirstName セットする txtFirstName
	 */
	public void setTxtFirstName(String txtFirstName) {
		this.txtFirstName = txtFirstName;
	}
	
	/**
	 * @return txtFirstName
	 */
	public String getTxtFirstName() {
		return txtFirstName;
	}
	
	/**
	 * @param txtLastKana セットする txtLastKana
	 */
	public void setTxtLastKana(String txtLastKana) {
		this.txtLastKana = txtLastKana;
	}
	
	/**
	 * @return txtLastKana
	 */
	public String getTxtLastKana() {
		return txtLastKana;
	}
	
	/**
	 * @param txtFirstKana セットする txtFirstKana
	 */
	public void setTxtFirstKana(String txtFirstKana) {
		this.txtFirstKana = txtFirstKana;
	}
	
	/**
	 * @return txtFirstKana
	 */
	public String getTxtFirstKana() {
		return txtFirstKana;
	}
	
	/**
	 * @param pltInfoType セットする pltInfoType
	 */
	public void setPltInfoType(String pltInfoType) {
		this.pltInfoType = pltInfoType;
	}
	
	/**
	 * @return pltInfoType
	 */
	public String getPltInfoType() {
		return pltInfoType;
	}
	
	/**
	 * @param txtSearchWord セットする txtSearchWord
	 */
	public void setTxtSearchWord(String txtSearchWord) {
		this.txtSearchWord = txtSearchWord;
	}
	
	/**
	 * @return txtSearchWord
	 */
	public String getTxtSearchWord() {
		return txtSearchWord;
	}
	
	/**
	 * @param pltState セットする pltState
	 */
	public void setPltState(String pltState) {
		this.pltState = pltState;
	}
	
	/**
	 * @return pltState
	 */
	public String getPltState() {
		return pltState;
	}
	
	/**
	 * @param pltEmployeeCode セットする pltEmployeeCode
	 */
	public void setPltEmployeeCode(String pltEmployeeCode) {
		this.pltEmployeeCode = pltEmployeeCode;
	}
	
	/**
	 * @return pltEmployeeCode
	 */
	public String getPltEmployeeCode() {
		return pltEmployeeCode;
	}
	
	/**
	 * @param pltLastName セットする pltLastName
	 */
	public void setPltLastName(String pltLastName) {
		this.pltLastName = pltLastName;
	}
	
	/**
	 * @return pltLastName
	 */
	public String getPltLastName() {
		return pltLastName;
	}
	
	/**
	 * @param pltFirstName セットする pltFirstName
	 */
	public void setPltFirstName(String pltFirstName) {
		this.pltFirstName = pltFirstName;
	}
	
	/**
	 * @return pltFirstName
	 */
	public String getPltFirstName() {
		return pltFirstName;
	}
	
	/**
	 * @param pltLastKana セットする pltLastKana
	 */
	public void setPltLastKana(String pltLastKana) {
		this.pltLastKana = pltLastKana;
	}
	
	/**
	 * @return pltLastKana
	 */
	public String getPltLastKana() {
		return pltLastKana;
	}
	
	/**
	 * @param pltFirstKana セットする pltFirstKana
	 */
	public void setPltFirstKana(String pltFirstKana) {
		this.pltFirstKana = pltFirstKana;
	}
	
	/**
	 * @return pltFirstKana
	 */
	public String getPltFirstKana() {
		return pltFirstKana;
	}
	
	/**
	 * @param aryEmployeeCode セットする aryEmployeeCode
	 */
	public void setAryEmployeeCode(String[] aryEmployeeCode) {
		this.aryEmployeeCode = getStringArrayClone(aryEmployeeCode);
	}
	
	/**
	 * @return aryEmployeeCode
	 */
	public String[] getAryEmployeeCode() {
		return getStringArrayClone(aryEmployeeCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryEmployeeCode
	 */
	public String getAryEmployeeCode(int idx) {
		return aryEmployeeCode[idx];
	}
	
	/**
	 * @param aryEmployeeName セットする aryEmployeeName
	 */
	public void setAryEmployeeName(String[] aryEmployeeName) {
		this.aryEmployeeName = getStringArrayClone(aryEmployeeName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryEmployeeName
	 */
	public String getAryEmployeeName(int idx) {
		return aryEmployeeName[idx];
	}
	
	/**
	 * @param aryEmployeeKana セットする aryEmployeeKana
	 */
	public void setAryEmployeeKana(String[] aryEmployeeKana) {
		this.aryEmployeeKana = getStringArrayClone(aryEmployeeKana);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryEmployeeKana
	 */
	public String getAryEmployeeKana(int idx) {
		return aryEmployeeKana[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryWorkPlaceAbbr
	 */
	public String getAryWorkPlaceAbbr(int idx) {
		return aryWorkPlaceAbbr[idx];
	}
	
	/**
	 * @param aryWorkPlaceAbbr セットする aryWorkPlaceAbbr
	 */
	public void setAryWorkPlaceAbbr(String[] aryWorkPlaceAbbr) {
		this.aryWorkPlaceAbbr = getStringArrayClone(aryWorkPlaceAbbr);
	}
	
	/**
	 * @param arySectionAbbr セットする arySectionAbbr
	 */
	public void setArySection(String[] arySectionAbbr) {
		arySection = getStringArrayClone(arySectionAbbr);
	}
	
	/**
	 * @param idx インデックス
	 * @return arySectionAbbr
	 */
	public String getArySection(int idx) {
		return arySection[idx];
	}
	
	/**
	 * @param aryPosition セットする aryPosition
	 */
	public void setAryPositionAbbr(String[] aryPosition) {
		aryPositionAbbr = getStringArrayClone(aryPosition);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryPosition
	 */
	public String getAryPositionAbbr(int idx) {
		return aryPositionAbbr[idx];
	}
	
	/**
	 * @param aryEmployment セットする aryEmployment
	 */
	public void setAryEmploymentAbbr(String[] aryEmployment) {
		aryEmploymentAbbr = getStringArrayClone(aryEmployment);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryEmployment
	 */
	public String getAryEmploymentAbbr(int idx) {
		return aryEmploymentAbbr[idx];
	}
	
	/**
	 * @param aryState セットする aryState
	 */
	public void setAryState(String[] aryState) {
		this.aryState = getStringArrayClone(aryState);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryState
	 */
	public String getAryState(int idx) {
		return aryState[idx];
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
	 * @param aryPltSectionAbbr セットする aryPltSectionAbbr
	 */
	public void setAryPltSectionAbbr(String[][] aryPltSectionAbbr) {
		this.aryPltSectionAbbr = getStringArrayClone(aryPltSectionAbbr);
	}
	
	/**
	 * @return aryPltSectionAbbr
	 */
	public String[][] getAryPltSectionAbbr() {
		return getStringArrayClone(aryPltSectionAbbr);
	}
	
	/**
	 * @param aryPltPosition セットする aryPltPosition
	 */
	public void setAryPltPosition(String[][] aryPltPosition) {
		this.aryPltPosition = getStringArrayClone(aryPltPosition);
	}
	
	/**
	 * @return aryPltPosition
	 */
	public String[][] getAryPltPosition() {
		return getStringArrayClone(aryPltPosition);
	}
	
	/**
	 * @param aryPltEmployment セットする aryPltEmployment
	 */
	public void setAryPltEmployment(String[][] aryPltEmployment) {
		this.aryPltEmployment = getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @return aryPltEmployment
	 */
	public String[][] getAryPltEmployment() {
		return getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @param modeSearchExpansion セットする modeSearchExpansion
	 */
	public void setModeSearchExpansion(String modeSearchExpansion) {
		this.modeSearchExpansion = modeSearchExpansion;
	}
	
	/**
	 * @return modeSearchExpansion
	 */
	public String getModeSearchExpansion() {
		return modeSearchExpansion;
	}
	
	/**
	 * @param pltPfmHumanId セットする pltPfmHumanId
	 */
	public void setPltPfmHumanId(String pltPfmHumanId) {
		this.pltPfmHumanId = pltPfmHumanId;
	}
	
	/**
	 * @return pltPfmHumanId
	 */
	public String getPltPfmHumanId() {
		return pltPfmHumanId;
	}
	
	/**
	 * @return activateDate
	 */
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	/**
	 * @param activateDate セットする activateDate
	 */
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	/**
	 * @param aryPersonalId セットする aryPersonalId
	 */
	public void setAryPersonalId(String[] aryPersonalId) {
		this.aryPersonalId = getStringArrayClone(aryPersonalId);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryPersonalId
	 */
	public String getAryPersonalId(int idx) {
		return aryPersonalId[idx];
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
	 * @param aryPltFreeWordTypes セットする aryPltFreeWordTypes
	 */
	public void setAryPltFreeWordTypes(String[][] aryPltFreeWordTypes) {
		this.aryPltFreeWordTypes = getStringArrayClone(aryPltFreeWordTypes);
	}
	
	/**
	 * @return aryPltFreeWordTypes
	 */
	public String[][] getAryPltFreeWordTypes() {
		return getStringArrayClone(aryPltFreeWordTypes);
	}
	
}
