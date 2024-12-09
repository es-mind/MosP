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
package jp.mosp.platform.workflow.vo;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * ルート適用詳細画面の情報を格納する。
 */
public class RouteApplicationCardVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -1806160334035807759L;
	
	/**
	 * ルート適用コード。
	 */
	private String				txtApplicationCode;
	/**
	 * ルート適用名称。
	 */
	private String				txtApplicationName;
	/**
	 * ルート。
	 */
	private String				pltRouteName;
	/**
	 * フロー区分。
	 */
	private String				pltFlowType;
	
	/**
	 * 勤務地プルダウン。
	 */
	private String				pltWorkPlace;
	/**
	 * 雇用契約プルダウン。
	 */
	private String				pltEmployment;
	/**
	 * 所属プルダウン。
	 */
	private String				pltSection;
	/**
	 * 職位プルダウン。
	 */
	private String				pltPosition;
	/**
	 * 個人指定欄。
	 */
	private String				txtEmployeeCode;
	/**
	 * 所属指定承認部署。
	 */
	private String				lblSectionPosition;
	
	/**
	 * ルート。
	 */
	private String[][]			aryPltRouteName;
	/**
	 * フロー区分。
	 */
	private String[][]			aryPltFlowType;
	/**
	 * 無効フラグ。
	 */
	private String[][]			aryPltEditInactivate;
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
	private String[][]			aryPltSectionMaster;
	/**
	 * 職位プルダウン。
	 */
	private String[][]			aryPltPositionMaster;
	/**
	 * ルート社員コード。
	 */
	private String[][]			aryTxtEmployeeCode;
	/**
	 * ラジオボタン。
	 */
	private String				radioSelect;
	
	
	/**
	 * @return txtApplicationCode
	 */
	public String getTxtApplicationCode() {
		return txtApplicationCode;
	}
	
	/**
	 * @param txtApplicationCode セットする txtApplicationCode
	 */
	public void setTxtApplicationCode(String txtApplicationCode) {
		this.txtApplicationCode = txtApplicationCode;
	}
	
	/**
	 * @return txtApplicationName
	 */
	public String getTxtApplicationName() {
		return txtApplicationName;
	}
	
	/**
	 * @param txtApplicationName セットする txtApplicationName
	 */
	public void setTxtApplicationName(String txtApplicationName) {
		this.txtApplicationName = txtApplicationName;
	}
	
	/**
	 * @return pltRouteName
	 */
	public String getPltRouteName() {
		return pltRouteName;
	}
	
	/**
	 * @param pltRouteName セットする pltRouteName
	 */
	public void setPltRouteName(String pltRouteName) {
		this.pltRouteName = pltRouteName;
	}
	
	/**
	 * @return pltFlowType
	 */
	public String getPltFlowType() {
		return pltFlowType;
	}
	
	/**
	 * @param pltFlowType セットする pltFlowType
	 */
	public void setPltFlowType(String pltFlowType) {
		this.pltFlowType = pltFlowType;
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
	 * @return lblSectionPosition
	 */
	public String getLblSectionPosition() {
		return lblSectionPosition;
	}
	
	/**
	 * @param lblSectionPosition セットする lblSectionPosition
	 */
	public void setLblSectionPosition(String lblSectionPosition) {
		this.lblSectionPosition = lblSectionPosition;
	}
	
	/**
	 * @return aryPltRouteName
	 */
	public String[][] getAryPltRouteName() {
		return getStringArrayClone(aryPltRouteName);
	}
	
	/**
	 * @param aryPltRouteName セットする aryPltRouteName
	 */
	public void setAryPltRouteName(String[][] aryPltRouteName) {
		this.aryPltRouteName = getStringArrayClone(aryPltRouteName);
	}
	
	/**
	 * @return aryPltFlowType
	 */
	public String[][] getAryPltFlowType() {
		return getStringArrayClone(aryPltFlowType);
	}
	
	/**
	 * @param aryPltFlowType セットする aryPltFlowType
	 */
	public void setAryPltFlowType(String[][] aryPltFlowType) {
		this.aryPltFlowType = getStringArrayClone(aryPltFlowType);
	}
	
	/**
	 * @return aryPltEditInactivate
	 */
	public String[][] getAryPltEditInactivate() {
		return getStringArrayClone(aryPltEditInactivate);
	}
	
	/**
	 * @param aryPltEditInactivate セットする aryPltEditInactivate
	 */
	public void setAryPltEditInactivate(String[][] aryPltEditInactivate) {
		this.aryPltEditInactivate = getStringArrayClone(aryPltEditInactivate);
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
	 * @return aryPltSectionMaster
	 */
	public String[][] getAryPltSectionMaster() {
		return getStringArrayClone(aryPltSectionMaster);
	}
	
	/**
	 * @param aryPltSectionMaster セットする aryPltSectionMaster
	 */
	public void setAryPltSectionMaster(String[][] aryPltSectionMaster) {
		this.aryPltSectionMaster = getStringArrayClone(aryPltSectionMaster);
	}
	
	/**
	 * @return aryPltPositionMaster
	 */
	public String[][] getAryPltPositionMaster() {
		return getStringArrayClone(aryPltPositionMaster);
	}
	
	/**
	 * @param aryPltPositionMaster セットする aryPltPositionMaster
	 */
	public void setAryPltPositionMaster(String[][] aryPltPositionMaster) {
		this.aryPltPositionMaster = getStringArrayClone(aryPltPositionMaster);
	}
	
	/**
	 * @return aryTxtEmployeeCode
	 */
	public String[][] getAryTxtEmployeeCode() {
		return getStringArrayClone(aryTxtEmployeeCode);
	}
	
	/**
	 * @param aryTxtEmployeeCode セットする aryTxtEmployeeCode
	 */
	public void setAryTxtEmployeeCode(String[][] aryTxtEmployeeCode) {
		this.aryTxtEmployeeCode = getStringArrayClone(aryTxtEmployeeCode);
	}
	
	/**
	 * @return radioSelect
	 */
	public String getRadioSelect() {
		return radioSelect;
	}
	
	/**
	 * @param radioSelect セットする radioSelect
	 */
	public void setRadioSelect(String radioSelect) {
		this.radioSelect = radioSelect;
	}
	
}
