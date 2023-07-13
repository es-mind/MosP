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

import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.platform.human.base.PlatformHumanVo;

/**
 * 個人基本情報詳細画面の情報を格納する。
 */
public class BasicCardVo extends PlatformHumanVo {
	
	private static final long	serialVersionUID	= 5822920430694164621L;
	
	/**
	 * レコードID。<br>
	 * 履歴編集対象のレコードIDを保持しておく。<br>
	 */
	private long				recordId;
	
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
	
	// 名称区分：役職追加判断
	private boolean				needPost;
	
	// プルダウンリスト
	private String[][]			aryPltSection;
	private String[][]			aryPltPosition;
	private String[][]			aryPltEmployment;
	private String[][]			aryPltWorkPlaceName;
	private String[][]			aryPltPostName;
	
	// 社員コード編集
	private Boolean				jsChangeEmployCode;
	
	
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
	 * @param pltWorkPlaceName セットする pltWorkPlaceName
	 */
	public void setPltWorkPlaceName(String pltWorkPlaceName) {
		this.pltWorkPlaceName = pltWorkPlaceName;
	}
	
	/**
	 * @return pltWorkPlaceName
	 */
	public String getPltWorkPlaceName() {
		return pltWorkPlaceName;
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
	 * @param aryPltSection セットする aryPltSection
	 */
	public void setAryPltSection(String[][] aryPltSection) {
		this.aryPltSection = CapsuleUtility.getStringArrayClone(aryPltSection);
	}
	
	/**
	 * @return aryPltSection
	 */
	public String[][] getAryPltSection() {
		return CapsuleUtility.getStringArrayClone(aryPltSection);
	}
	
	/**
	 * @param aryPltPosition セットする aryPltPosition
	 */
	public void setAryPltPosition(String[][] aryPltPosition) {
		this.aryPltPosition = CapsuleUtility.getStringArrayClone(aryPltPosition);
	}
	
	/**
	 * @return aryPltPosition
	 */
	public String[][] getAryPltPosition() {
		return CapsuleUtility.getStringArrayClone(aryPltPosition);
	}
	
	/**
	 * @param aryPltEmployment セットする aryPltEmployment
	 */
	public void setAryPltEmployment(String[][] aryPltEmployment) {
		this.aryPltEmployment = CapsuleUtility.getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @return aryPltEmployment
	 */
	public String[][] getAryPltEmployment() {
		return CapsuleUtility.getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @return aryPltWorkPlaceName
	 */
	public String[][] getAryPltWorkPlaceName() {
		return CapsuleUtility.getStringArrayClone(aryPltWorkPlaceName);
	}
	
	/**
	 * @param aryPltWorkPlaceName セットする aryPltWorkPlaceName
	 */
	public void setAryPltWorkPlaceName(String[][] aryPltWorkPlaceName) {
		this.aryPltWorkPlaceName = CapsuleUtility.getStringArrayClone(aryPltWorkPlaceName);
	}
	
	/**
	 * @return aryPltPostName
	 */
	public String[][] getAryPltPostName() {
		return CapsuleUtility.getStringArrayClone(aryPltPostName);
	}
	
	/**
	 * @param aryPltPostName セットする aryPltPostName
	 */
	public void setAryPltPostName(String[][] aryPltPostName) {
		this.aryPltPostName = CapsuleUtility.getStringArrayClone(aryPltPostName);
	}
	
	/**
	 * @param jsChangeEmployCode セットする jsChangeEmployCode
	 */
	public void setJsChangeEmployCode(Boolean jsChangeEmployCode) {
		this.jsChangeEmployCode = jsChangeEmployCode;
	}
	
	/**
	 * @return jsChangeEmployCode
	 */
	public Boolean getJsChangeEmployCode() {
		return jsChangeEmployCode;
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
}
