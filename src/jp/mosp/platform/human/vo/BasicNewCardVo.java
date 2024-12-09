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
package jp.mosp.platform.human.vo;

import java.util.Date;
import java.util.List;

import jp.mosp.platform.base.PlatformVo;

/**
 * 新規社員登録画面の情報を格納する。
 */
public class BasicNewCardVo extends PlatformVo {
	
	private static final long	serialVersionUID	= 5822920430694164621L;
	
	private String				txtActivateYear;
	private String				txtActivateMonth;
	private String				txtActivateDay;
	private String				txtEmployeeCode;
	private String				txtLastName;
	private String				txtFirstName;
	private String				txtLastKana;
	private String				txtFirstKana;
	private String				pltSectionName;
	private String				pltPositionName;
	private String				pltEmploymentName;
	private String				pltWorkPlaceName;
	private String				pltPostName;
	private String				txtUserId;
	private String				txtEntranceYear;
	private String				txtEntranceMonth;
	private String				txtEntranceDay;
	
	/**
	 * 人事一覧遷移用個人ID。
	 */
	private String				personalId;
	
	/**
	 * 人事一覧遷移用対象日。
	 */
	private Date				targetDate;
	
	// プルダウンリスト
	private String[][]			aryPltSection;
	private String[][]			aryPltPosition;
	private String[][]			aryPltEmployment;
	private String[][]			aryPltWorkPlaceName;
	private String[][]			aryPltPostName;
	
	// 名称区分：役職追加判断
	private boolean				needPost;
	
	/**
	 * 追加JSPリスト。<br>
	 */
	private List<String>		extraJspList;
	
	/**
	 * 自動設定ボタン要否。<br>
	 */
	private boolean				needNumberingButton;
	
	
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
	 * @param pltSectionName セットする pltSectionName
	 */
	public void setPltSectionName(String pltSectionName) {
		this.pltSectionName = pltSectionName;
	}
	
	/**
	 * @return pltSectionName
	 */
	public String getPltSectionName() {
		return pltSectionName;
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
	 * @return pltWorkPlaceName
	 */
	public String getPltWorkPlaceName() {
		return pltWorkPlaceName;
	}
	
	/**
	 * @param pltWorkPlaceName セットする pltWorkPlaceName
	 */
	public void setPltWorkPlaceName(String pltWorkPlaceName) {
		this.pltWorkPlaceName = pltWorkPlaceName;
	}
	
	/**
	 * @return pltPostName
	 */
	public String getPltPostName() {
		return pltPostName;
	}
	
	/**
	 * @param pltPostName セットする pltPostName
	 */
	public void setPltPostName(String pltPostName) {
		this.pltPostName = pltPostName;
	}
	
	/**
	 * @param txtUserId セットする txtUserId
	 */
	public void setTxtUserId(String txtUserId) {
		this.txtUserId = txtUserId;
	}
	
	/**
	 * @return txtUserId
	 */
	public String getTxtUserId() {
		return txtUserId;
	}
	
	/**
	 * @param txtEntranceYear セットする txtEntranceYear
	 */
	public void setTxtEntranceYear(String txtEntranceYear) {
		this.txtEntranceYear = txtEntranceYear;
	}
	
	/**
	 * @return txtEntranceYear
	 */
	public String getTxtEntranceYear() {
		return txtEntranceYear;
	}
	
	/**
	 * @param txtEntranceMonth セットする txtEntranceMonth
	 */
	public void setTxtEntranceMonth(String txtEntranceMonth) {
		this.txtEntranceMonth = txtEntranceMonth;
	}
	
	/**
	 * @return txtEntranceMonth
	 */
	public String getTxtEntranceMonth() {
		return txtEntranceMonth;
	}
	
	/**
	 * @param txtEntranceDay セットする txtEntranceDay
	 */
	public void setTxtEntranceDay(String txtEntranceDay) {
		this.txtEntranceDay = txtEntranceDay;
	}
	
	/**
	 * @return txtEntranceDay
	 */
	public String getTxtEntranceDay() {
		return txtEntranceDay;
	}
	
	/**
	 * @param aryPltSection セットする aryPltSection
	 */
	public void setAryPltSection(String[][] aryPltSection) {
		this.aryPltSection = getStringArrayClone(aryPltSection);
	}
	
	/**
	 * @return aryPltSection
	 */
	public String[][] getAryPltSection() {
		return getStringArrayClone(aryPltSection);
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
	 * @return aryPltWorkPlaceName
	 */
	public String[][] getAryPltWorkPlaceName() {
		return getStringArrayClone(aryPltWorkPlaceName);
	}
	
	/**
	 * @param aryPltWorkPlaceName セットする aryPltWorkPlaceName
	 */
	public void setAryPltWorkPlaceName(String[][] aryPltWorkPlaceName) {
		this.aryPltWorkPlaceName = getStringArrayClone(aryPltWorkPlaceName);
	}
	
	/**
	 * @return aryPltPostName
	 */
	public String[][] getAryPltPostName() {
		return getStringArrayClone(aryPltPostName);
	}
	
	/**
	 * @param aryPltPostName セットする aryPltPostName
	 */
	public void setAryPltPostName(String[][] aryPltPostName) {
		this.aryPltPostName = getStringArrayClone(aryPltPostName);
	}
	
	/**
	 * @param personalId セットする personalId
	 */
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	/**
	 * @return personalId
	 */
	public String getPersonalId() {
		return personalId;
	}
	
	/**
	 * @param targetDate セットする targetDate
	 */
	public void setTargetDate(Date targetDate) {
		this.targetDate = getDateClone(targetDate);
	}
	
	/**
	 * @return targetDate
	 */
	public Date getTargetDate() {
		return getDateClone(targetDate);
	}
	
	/**
	 * @param needPost セットする needPost
	 */
	public void setNeedPost(boolean needPost) {
		this.needPost = needPost;
	}
	
	/**
	 * @return needPost
	 */
	public boolean getNeedPost() {
		return needPost;
	}
	
	/**
	 * @return extraJspList
	 */
	public List<String> getExtraJspList() {
		return extraJspList;
	}
	
	/**
	 * @param extraJspList セットする extraJspList
	 */
	public void setExtraJspList(List<String> extraJspList) {
		this.extraJspList = extraJspList;
	}
	
	/**
	 * @return needNumberingButton
	 */
	public boolean isNeedNumberingButton() {
		return needNumberingButton;
	}
	
	/**
	 * @param needNumberingButton セットする needNumberingButton
	 */
	public void setNeedNumberingButton(boolean needNumberingButton) {
		this.needNumberingButton = needNumberingButton;
	}
	
}
