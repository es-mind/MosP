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
 * ルート適用参照画面VOクラス。
 */
public class RouteApplicationReferenceVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -2138099048985253327L;
	// 検索
	/**
	 * 社員コード。
	 */
	private String				txtSearchEmployeeCode;
	
	/**
	 * 氏名。
	 */
	private String				txtSearchEmployeeName;
	
	/**
	 * 勤務地。
	 */
	private String				pltSearchWorkPlace;
	
	/**
	 * 雇用契約。
	 */
	private String				pltSearchEmployment;
	
	/**
	 * 所属。
	 */
	private String				pltSearchSection;
	
	/**
	 * 職位。
	 */
	private String				pltSearchPosition;
	
	/**
	 * フロー区分。
	 */
	private String				pltSearchFlowType;
	
	/**
	 * ルート適用コード。
	 */
	private String				txtSearchRouteApplicationCode;
	
	/**
	 * ルート適用名称。
	 */
	private String				txtSearchRouteApplicationName;
	
	/**
	 * ルートコード。
	 */
	private String				txtSearchRouteCode;
	
	/**
	 * ルート名称。
	 */
	private String				txtSearchRouteName;
	
	/**
	 * 承認者社員コード。
	 */
	private String				txtSearchApproverCode;
	
	/**
	 * 承認社氏名
	 */
	private String				txtSearchApproverName;
	
	// 出力
	/**
	 * 社員コード
	 */
	private String[]			aryLblEmployeeCode;
	
	/**
	 * 氏名。
	 */
	private String[]			aryLblEmployeeName;
	
	/**
	 * ルート適用コード。
	 */
	private String[]			aryLblRouteApplicationCode;
	
	/**
	 * ルート適用名称。
	 */
	private String[]			aryLblRouteApplicationName;
	
	/**
	 * ルートコード。
	 */
	private String[]			aryLblRouteCode;
	
	/**
	 * ルート名称。
	 */
	private String[]			aryLblRouteName;
	
	/**
	 * 承認者社員コード
	 */
	private String[]			aryLblApproverCode;
	
	/**
	 * 承認者氏名
	 */
	private String[]			aryLblApproverName;
	
	/**
	 * 承認階層。
	 */
	private String[]			aryLblRouteStage;
	
	/**
	 * 一次承認者。
	 */
	private String[]			aryLblFirstApprovalName;
	
	/**
	 * 最終承認者。
	 */
	private String[]			aryLblEndApprovalName;
	
	// 検索プルダウン
	private String[][]			aryPltSearchWorkPlaceMaster;
	private String[][]			aryPltSearchEmploymentMaster;
	private String[][]			aryPltSearchSectionMaster;
	private String[][]			aryPltSearchPositionMaster;
	
	
	/**
	 * @return txtSearchEmployeeCode
	 */
	public String getTxtSearchEmployeeCode() {
		return txtSearchEmployeeCode;
	}
	
	/**
	 * @param txtSearchEmployeeCode セットする txtSearchEmployeeCode
	 */
	public void setTxtSearchEmployeeCode(String txtSearchEmployeeCode) {
		this.txtSearchEmployeeCode = txtSearchEmployeeCode;
	}
	
	/**
	 * @return txtSearchEmployeeName
	 */
	public String getTxtSearchEmployeeName() {
		return txtSearchEmployeeName;
	}
	
	/**
	 * @param txtSearchEmployeeName セットする txtSearchEmployeeName
	 */
	public void setTxtSearchEmployeeName(String txtSearchEmployeeName) {
		this.txtSearchEmployeeName = txtSearchEmployeeName;
	}
	
	/**
	 * @return pltSearchWorkPlace
	 */
	public String getPltSearchWorkPlace() {
		return pltSearchWorkPlace;
	}
	
	/**
	 * @param pltSearchWorkPlace セットする pltSearchWorkPlace
	 */
	public void setPltSearchWorkPlace(String pltSearchWorkPlace) {
		this.pltSearchWorkPlace = pltSearchWorkPlace;
	}
	
	/**
	 * @return pltSearchEmployment
	 */
	public String getPltSearchEmployment() {
		return pltSearchEmployment;
	}
	
	/**
	 * @param pltSearchEmployment セットする pltSearchEmployment
	 */
	public void setPltSearchEmployment(String pltSearchEmployment) {
		this.pltSearchEmployment = pltSearchEmployment;
	}
	
	/**
	 * @return pltSearchSection
	 */
	public String getPltSearchSection() {
		return pltSearchSection;
	}
	
	/**
	 * @param pltSearchSection セットする pltSearchSection
	 */
	public void setPltSearchSection(String pltSearchSection) {
		this.pltSearchSection = pltSearchSection;
	}
	
	/**
	 * @return pltSearchPosition
	 */
	public String getPltSearchPosition() {
		return pltSearchPosition;
	}
	
	/**
	 * @param pltSearchPosition セットする pltSearchPosition
	 */
	public void setPltSearchPosition(String pltSearchPosition) {
		this.pltSearchPosition = pltSearchPosition;
	}
	
	/**
	 * @return pltSearchFlowType
	 */
	public String getPltSearchFlowType() {
		return pltSearchFlowType;
	}
	
	/**
	 * @param pltSearchFlowType セットする pltSearchFlowType
	 */
	public void setPltSearchFlowType(String pltSearchFlowType) {
		this.pltSearchFlowType = pltSearchFlowType;
	}
	
	/**
	 * @return txtSearchRouteApplicationCode
	 */
	public String getTxtSearchRouteApplicationCode() {
		return txtSearchRouteApplicationCode;
	}
	
	/**
	 * @param txtSearchRouteApplicationCode セットする txtSearchRouteApplicationCode
	 */
	public void setTxtSearchRouteApplicationCode(String txtSearchRouteApplicationCode) {
		this.txtSearchRouteApplicationCode = txtSearchRouteApplicationCode;
	}
	
	/**
	 * @return txtSearchRouteApplicationName
	 */
	public String getTxtSearchRouteApplicationName() {
		return txtSearchRouteApplicationName;
	}
	
	/**
	 * @param txtSearchRouteApplicationName セットする txtSearchRouteApplicationName
	 */
	public void setTxtSearchRouteApplicationName(String txtSearchRouteApplicationName) {
		this.txtSearchRouteApplicationName = txtSearchRouteApplicationName;
	}
	
	/**
	 * @return txtSearchRouteCode
	 */
	public String getTxtSearchRouteCode() {
		return txtSearchRouteCode;
	}
	
	/**
	 * @param txtSearchRouteCode セットする txtSearchRouteCode
	 */
	public void setTxtSearchRouteCode(String txtSearchRouteCode) {
		this.txtSearchRouteCode = txtSearchRouteCode;
	}
	
	/**
	 * @return txtSearchRouteName
	 */
	public String getTxtSearchRouteName() {
		return txtSearchRouteName;
	}
	
	/**
	 * @param txtSearchRouteName セットする txtSearchRouteName
	 */
	public void setTxtSearchRouteName(String txtSearchRouteName) {
		this.txtSearchRouteName = txtSearchRouteName;
	}
	
	/**
	 * @return txtSearchApproverCode
	 */
	public String getTxtSearchApproverCode() {
		return txtSearchApproverCode;
	}
	
	/**
	 * @param txtSearchApproverCode セットする txtSearchApproverCode
	 */
	public void setTxtSearchApproverCode(String txtSearchApproverCode) {
		this.txtSearchApproverCode = txtSearchApproverCode;
	}
	
	/**
	 * @return txtSearchApproverName
	 */
	public String getTxtSearchApproverName() {
		return txtSearchApproverName;
	}
	
	/**
	 * @param txtSearchApproverName セットする txtSearchApproverName
	 */
	public void setTxtSearchApproverName(String txtSearchApproverName) {
		this.txtSearchApproverName = txtSearchApproverName;
	}
	
	/**
	 * @return aryLblEmployeeCode
	 */
	public String[] getAryLblEmployeeCode() {
		return getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @param aryLblEmployeeCode セットする aryLblEmployeeCode
	 */
	public void setAryLblEmployeeCode(String[] aryLblEmployeeCode) {
		this.aryLblEmployeeCode = getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEmployeeCode
	 */
	public String getAryLblEmployeeCode(int idx) {
		return aryLblEmployeeCode[idx];
	}
	
	/**
	 * @return aryLblEmployeeName
	 */
	public String[] getAryLblEmployeeName() {
		return getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @param aryLblEmployeeName セットする aryLblEmployeeName
	 */
	public void setAryLblEmployeeName(String[] aryLblEmployeeName) {
		this.aryLblEmployeeName = getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEmployeeName
	 */
	public String getAryLblEmployeeName(int idx) {
		return aryLblEmployeeName[idx];
	}
	
	/**
	 * @return aryLblRouteApplicationCode
	 */
	public String[] getAryLblRouteApplicationCode() {
		return getStringArrayClone(aryLblRouteApplicationCode);
	}
	
	/**
	 * @param aryLblRouteApplicationCode セットする aryLblRouteApplicationCode
	 */
	public void setAryLblRouteApplicationCode(String[] aryLblRouteApplicationCode) {
		this.aryLblRouteApplicationCode = getStringArrayClone(aryLblRouteApplicationCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRouteApplicationCode
	 */
	public String getAryLblRouteApplicationCode(int idx) {
		return aryLblRouteApplicationCode[idx];
	}
	
	/**
	 * @return aryLblRouteApplicationName
	 */
	public String[] getRouteAryLblRouteApplicationName() {
		return getStringArrayClone(aryLblRouteApplicationName);
	}
	
	/**
	 * @param aryLblRouteApplicationName セットする aryLblRouteApplicationName
	 */
	public void setAryLblRouteApplicationName(String[] aryLblRouteApplicationName) {
		this.aryLblRouteApplicationName = getStringArrayClone(aryLblRouteApplicationName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRouteApplicationName
	 */
	public String getAryLblRouteApplicationName(int idx) {
		return aryLblRouteApplicationName[idx];
	}
	
	/**
	 * @return aryLblRouteCode
	 */
	public String[] getAryLblRouteCode() {
		return getStringArrayClone(aryLblRouteCode);
	}
	
	/**
	 * @param aryLblRouteCode セットする aryLblRouteCode
	 */
	public void setAryLblRouteCode(String[] aryLblRouteCode) {
		this.aryLblRouteCode = getStringArrayClone(aryLblRouteCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRouteName
	 */
	public String getAryLblRouteCode(int idx) {
		return aryLblRouteCode[idx];
	}
	
	/**
	 * @return aryLblRouteName
	 */
	public String[] getAryLblRouteName() {
		return getStringArrayClone(aryLblRouteName);
	}
	
	/**
	 * @param aryLblRouteName セットする aryLblRouteName
	 */
	public void setAryLblRouteName(String[] aryLblRouteName) {
		this.aryLblRouteName = getStringArrayClone(aryLblRouteName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRouteName
	 */
	public String getAryLblRouteName(int idx) {
		return aryLblRouteName[idx];
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
	 * @param idx インデックス
	 * @return aryLblApproverName
	 */
	public String getAryLblApproverName(int idx) {
		return aryLblApproverName[idx];
	}
	
	/**
	 * @return aryLblApproverCode
	 */
	public String[] getAryLblApproverCode() {
		return getStringArrayClone(aryLblApproverCode);
	}
	
	/**
	 * @param aryLblApproverCode セットする aryLblEmployeeName
	 */
	public void setAryLblApproverCode(String[] aryLblApproverCode) {
		this.aryLblApproverCode = getStringArrayClone(aryLblApproverCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblApproverCode
	 */
	public String getAryLblApproverCode(int idx) {
		return aryLblApproverCode[idx];
	}
	
	/**
	 * @return aryLblRouteStage
	 */
	public String[] getAryLblRouteStage() {
		return getStringArrayClone(aryLblRouteStage);
	}
	
	/**
	 * @param aryLblRouteStage セットする aryLblRouteStage
	 */
	public void setAryLblRouteStage(String[] aryLblRouteStage) {
		this.aryLblRouteStage = getStringArrayClone(aryLblRouteStage);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRouteStage
	 */
	public String getAryLblRouteStage(int idx) {
		return aryLblRouteStage[idx];
	}
	
	/**
	 * @return aryLblFirstApprovalName
	 */
	public String[] getAryLblFirstApprovalName() {
		return getStringArrayClone(aryLblFirstApprovalName);
	}
	
	/**
	 * @param aryLblFirstApprovalName セットする aryLblFirstApprovalName
	 */
	public void setAryLblFirstApprovalName(String[] aryLblFirstApprovalName) {
		this.aryLblFirstApprovalName = getStringArrayClone(aryLblFirstApprovalName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblFirstApprovalName
	 */
	public String getAryLblFirstApprovalName(int idx) {
		return aryLblFirstApprovalName[idx];
	}
	
	/**
	 * @return aryLblEndApprovalName
	 */
	public String[] getAryLblEndApprovalName() {
		return getStringArrayClone(aryLblEndApprovalName);
	}
	
	/**
	 * @param aryLblEndApprovalName セットする aryLblEndApprovalName
	 */
	public void setAryLblEndApprovalName(String[] aryLblEndApprovalName) {
		this.aryLblEndApprovalName = getStringArrayClone(aryLblEndApprovalName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEndApprovalName
	 */
	public String getAryLblEndApprovalName(int idx) {
		return aryLblEndApprovalName[idx];
	}
	
	/**
	 * @return aryPltSearchWorkPlaceMaster
	 */
	public String[][] getAryPltSearchWorkPlaceMaster() {
		return getStringArrayClone(aryPltSearchWorkPlaceMaster);
	}
	
	/**
	 * @param aryPltSearchWorkPlaceMaster セットする aryPltSearchWorkPlaceMaster
	 */
	public void setAryPltSearchWorkPlaceMaster(String[][] aryPltSearchWorkPlaceMaster) {
		this.aryPltSearchWorkPlaceMaster = getStringArrayClone(aryPltSearchWorkPlaceMaster);
	}
	
	/**
	 * @return aryPltSearchEmploymentMaster
	 */
	public String[][] getAryPltSearchEmploymentMaster() {
		return getStringArrayClone(aryPltSearchEmploymentMaster);
	}
	
	/**
	 * @param aryPltSearchEmploymentMaster セットする aryPltSearchEmploymentMaster
	 */
	public void setAryPltSearchEmploymentMaster(String[][] aryPltSearchEmploymentMaster) {
		this.aryPltSearchEmploymentMaster = getStringArrayClone(aryPltSearchEmploymentMaster);
	}
	
	/**
	 * @return aryPltSearchSectionMaster
	 */
	public String[][] getAryPltSearchSectionMaster() {
		return getStringArrayClone(aryPltSearchSectionMaster);
	}
	
	/**
	 * @param aryPltSearchSectionMaster セットする aryPltSearchSectionMaster
	 */
	public void setAryPltSearchSectionMaster(String[][] aryPltSearchSectionMaster) {
		this.aryPltSearchSectionMaster = getStringArrayClone(aryPltSearchSectionMaster);
	}
	
	/**
	 * @return aryPltSearchPositionMaster
	 */
	public String[][] getAryPltSearchPositionMaster() {
		return getStringArrayClone(aryPltSearchPositionMaster);
	}
	
	/**
	 * @param aryPltSearchPositionMaster セットする aryPltSearchPositionMaster
	 */
	public void setAryPltSearchPositionMaster(String[][] aryPltSearchPositionMaster) {
		this.aryPltSearchPositionMaster = getStringArrayClone(aryPltSearchPositionMaster);
	}
	
	/**
	 * @return serialversionuid
	 */
	public static long getSerialversionuid() {
		return serialVersionUID;
	}
	
}
