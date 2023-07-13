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
package jp.mosp.platform.workflow.vo;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * ユニット詳細画面の情報を格納する。
 */
public class UnitCardVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -1400905617157875943L;
	
	private String				txtUnitCode;								// ユニットコード
	private String				txtUnitName;								// ユニット名称
	private String				pltRouteStage;								// 複数決済
	private String				pltSectionMaster;							// 所属プルダウン
	private String				pltPositionMaster;							// 職位プルダウン
	private String				pltPositionGradeRange;
	private String				txtEmployeeCode;							// 個人指定欄
	private String				lblSectionPosition;							// 所属指定承認部署
	private String				lblEmployeeCode;							// 個人指定承認者
	
	private String[][]			aryPltRouteStage;
	private String[][]			aryPltEditInactivate;
	private String[][]			aryPltSectionMaster;
	private String[][]			aryPltPositionMaster;
	private String[][]			aryTxtEmployeeCode;
	
	private String				radUnitType;
	
	
	/**
	 * @return txtUnitCode
	 */
	public String getTxtUnitCode() {
		return txtUnitCode;
	}
	
	/**
	 * @param txtUnitCode セットする txtUnitCode
	 */
	public void setTxtUnitCode(String txtUnitCode) {
		this.txtUnitCode = txtUnitCode;
	}
	
	/**
	 * @return txtUnitName
	 */
	public String getTxtUnitName() {
		return txtUnitName;
	}
	
	/**
	 * @param txtUnitName セットする txtUnitName
	 */
	public void setTxtUnitName(String txtUnitName) {
		this.txtUnitName = txtUnitName;
	}
	
	/**
	 * @return pltRouteStage
	 */
	public String getPltRouteStage() {
		return pltRouteStage;
	}
	
	/**
	 * @param pltRouteStage セットする pltRouteStage
	 */
	public void setPltRouteStage(String pltRouteStage) {
		this.pltRouteStage = pltRouteStage;
	}
	
	/**
	 * @return pltSectionMaster
	 */
	public String getPltSectionMaster() {
		return pltSectionMaster;
	}
	
	/**
	 * @param pltSectionMaster セットする pltSectionMaster
	 */
	public void setPltSectionMaster(String pltSectionMaster) {
		this.pltSectionMaster = pltSectionMaster;
	}
	
	/**
	 * @return pltPositionMaster
	 */
	public String getPltPositionMaster() {
		return pltPositionMaster;
	}
	
	/**
	 * @param pltPositionMaster セットする pltPositionMaster
	 */
	public void setPltPositionMaster(String pltPositionMaster) {
		this.pltPositionMaster = pltPositionMaster;
	}
	
	/**
	 * @return pltPositionGradeRange
	 */
	public String getPltPositionGradeRange() {
		return pltPositionGradeRange;
	}
	
	/**
	 * @param pltPositionGradeRange セットする pltPositionGradeRange
	 */
	public void setPltPositionGradeRange(String pltPositionGradeRange) {
		this.pltPositionGradeRange = pltPositionGradeRange;
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
	 * @return lblEmployeeCode
	 */
	public String getLblEmployeeCode() {
		return lblEmployeeCode;
	}
	
	/**
	 * @param lblEmployeeCode セットする lblEmployeeCode
	 */
	public void setLblEmployeeCode(String lblEmployeeCode) {
		this.lblEmployeeCode = lblEmployeeCode;
	}
	
	/**
	 * @return aryPltRouteStage
	 */
	public String[][] getAryPltRouteStage() {
		return getStringArrayClone(aryPltRouteStage);
	}
	
	/**
	 * @param aryPltRouteStage セットする aryPltRouteStage
	 */
	public void setAryPltRouteStage(String[][] aryPltRouteStage) {
		this.aryPltRouteStage = getStringArrayClone(aryPltRouteStage);
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
	 * @return radUnitType
	 */
	public String getRadUnitType() {
		return radUnitType;
	}
	
	/**
	 * @param radUnitType セットする radUnitType
	 */
	public void setRadUnitType(String radUnitType) {
		this.radUnitType = radUnitType;
	}
	
}
