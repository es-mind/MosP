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
 * ルート適用一覧画面の情報を格納する。
 */
public class RouteApplicationListVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -3229571080474603190L;
	
	/**
	 * フロー区分。
	 */
	private String				pltSearchFlowType;
	
	/**
	 * ルート適用コード。
	 */
	private String				txtSearchApplicationCode;
	
	/**
	 * ルート適用名称。
	 */
	private String				txtSearchApplicationName;
	
	/**
	 * ルート適用社員コード。
	 */
	private String				txtSearchApplicationEmployee;
	
	/**
	 * ルートコード。
	 */
	private String				txtSearchRouteCode;
	
	/**
	 * ルート名称。
	 */
	private String				txtSearchRouteName;
	
	/**
	 * ルート社員コード。
	 */
	private String				txtSearchRouteEmployee;
	
	/**
	 * ルート適用コード。
	 */
	private String[]			aryLblApplicationCode;
	
	/**
	 * ルート適用名称。
	 */
	private String[]			aryLblApplicationName;
	
	/**
	 * ルート名称。
	 */
	private String[]			aryLblRouteName;
	
	/**
	 * ルート有効日。
	 */
	private String[]			aryLblRouteActivateDate;
	
	/**
	 * ルートコード。
	 */
	private String[]			aryLblRouteCode;
	
	/**
	 * フロー区分。
	 */
	private String[]			aryLblFlowType;
	
	/**
	 * 適用範囲。
	 */
	private String[]			aryLblApplicationLength;
	
	/**
	 * レコード識別ID。
	 */
	private String[]			aryCkbRouteApplicationListId;
	
	
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
	 * @return txtSearchApplicationCode
	 */
	public String getTxtSearchApplicationCode() {
		return txtSearchApplicationCode;
	}
	
	/**
	 * @param txtSearchApplicationCode セットする txtSearchApplicationCode
	 */
	public void setTxtSearchApplicationCode(String txtSearchApplicationCode) {
		this.txtSearchApplicationCode = txtSearchApplicationCode;
	}
	
	/**
	 * @return txtSearchApplicationName
	 */
	public String getTxtSearchApplicationName() {
		return txtSearchApplicationName;
	}
	
	/**
	 * @param txtSearchApplicationName セットする txtSearchApplicationName
	 */
	public void setTxtSearchApplicationName(String txtSearchApplicationName) {
		this.txtSearchApplicationName = txtSearchApplicationName;
	}
	
	/**
	 * @return txtSearchApplicationEmployee
	 */
	public String getTxtSearchApplicationEmployee() {
		return txtSearchApplicationEmployee;
	}
	
	/**
	 * @param txtSearchApplicationEmployee セットする txtSearchApplicationEmployee
	 */
	public void setTxtSearchApplicationEmployee(String txtSearchApplicationEmployee) {
		this.txtSearchApplicationEmployee = txtSearchApplicationEmployee;
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
	 * @return txtSearchRouteEmployee
	 */
	public String getTxtSearchRouteEmployee() {
		return txtSearchRouteEmployee;
	}
	
	/**
	 * @param txtSearchRouteEmployee セットする txtSearchRouteEmployee
	 */
	public void setTxtSearchRouteEmployee(String txtSearchRouteEmployee) {
		this.txtSearchRouteEmployee = txtSearchRouteEmployee;
	}
	
	/**
	 * @return aryLblApplicationCode
	 */
	public String[] getAryLblApplicationCode() {
		return getStringArrayClone(aryLblApplicationCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblApplicationCode
	 */
	public String getAryLblApplicationCode(int idx) {
		return aryLblApplicationCode[idx];
	}
	
	/**
	 * @param aryLblApplicationCode セットする aryLblApplicationCode
	 */
	public void setAryLblApplicationCode(String[] aryLblApplicationCode) {
		this.aryLblApplicationCode = getStringArrayClone(aryLblApplicationCode);
	}
	
	/**
	 * @return aryLblApplicationName
	 */
	public String[] getAryLblApplicationName() {
		return getStringArrayClone(aryLblApplicationName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblApplicationName
	 */
	public String getAryLblApplicationName(int idx) {
		return aryLblApplicationName[idx];
	}
	
	/**
	 * @param aryLblApplicationName セットする aryLblApplicationName
	 */
	public void setAryLblApplicationName(String[] aryLblApplicationName) {
		this.aryLblApplicationName = getStringArrayClone(aryLblApplicationName);
	}
	
	/**
	 * @return aryLblRouteName
	 */
	public String[] getAryLblRouteName() {
		return getStringArrayClone(aryLblRouteName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRouteName
	 */
	public String getAryLblRouteName(int idx) {
		return aryLblRouteName[idx];
	}
	
	/**
	 * @param aryLblRouteName セットする aryLblRouteName
	 */
	public void setAryLblRouteName(String[] aryLblRouteName) {
		this.aryLblRouteName = getStringArrayClone(aryLblRouteName);
	}
	
	/**
	 * @return aryLblRouteActivateDate
	 */
	public String[] getAryLblRouteActivateDate() {
		return getStringArrayClone(aryLblRouteActivateDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRouteActivateDate
	 */
	public String getAryLblRouteActivateDate(int idx) {
		return aryLblRouteActivateDate[idx];
	}
	
	/**
	 * @param aryLblRouteActivateDate セットする aryLblRouteActivateDate
	 */
	public void setAryLblRouteActivateDate(String[] aryLblRouteActivateDate) {
		this.aryLblRouteActivateDate = getStringArrayClone(aryLblRouteActivateDate);
	}
	
	/**
	 * @return aryLblRouteCode
	 */
	public String[] getAryLblRouteCode() {
		return getStringArrayClone(aryLblRouteCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRouteCode
	 */
	public String getAryLblRouteCode(int idx) {
		return aryLblRouteCode[idx];
	}
	
	/**
	 * @param aryLblRouteCode セットする aryLblRouteCode
	 */
	public void setAryLblRouteCode(String[] aryLblRouteCode) {
		this.aryLblRouteCode = getStringArrayClone(aryLblRouteCode);
	}
	
	/**
	 * @return aryLblFlowType
	 */
	public String[] getAryLblFlowType() {
		return getStringArrayClone(aryLblFlowType);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblFlowType
	 */
	public String getAryLblFlowType(int idx) {
		return aryLblFlowType[idx];
	}
	
	/**
	 * @param aryLblFlowType セットする aryLblFlowType
	 */
	public void setAryLblFlowType(String[] aryLblFlowType) {
		this.aryLblFlowType = getStringArrayClone(aryLblFlowType);
	}
	
	/**
	 * @return aryLblApplicationLength
	 */
	public String[] getAryLblApplicationLength() {
		return getStringArrayClone(aryLblApplicationLength);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblApplicationLength
	 */
	public String getAryLblApplicationLength(int idx) {
		return aryLblApplicationLength[idx];
	}
	
	/**
	 * @param aryLblApplicationLength セットする aryLblApplicationLength
	 */
	public void setAryLblApplicationLength(String[] aryLblApplicationLength) {
		this.aryLblApplicationLength = getStringArrayClone(aryLblApplicationLength);
	}
	
	/**
	 * @return aryCkbRouteApplicationListId
	 */
	public String[] getAryCkbRouteApplicationListId() {
		return getStringArrayClone(aryCkbRouteApplicationListId);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryCkbRouteApplicationListId
	 */
	public String getAryCkbRouteApplicationListId(int idx) {
		return aryCkbRouteApplicationListId[idx];
	}
	
	/**
	 * @param aryCkbRouteApplicationListId セットする aryCkbRouteApplicationListId
	 */
	public void setAryCkbRouteApplicationListId(String[] aryCkbRouteApplicationListId) {
		this.aryCkbRouteApplicationListId = getStringArrayClone(aryCkbRouteApplicationListId);
	}
	
}
