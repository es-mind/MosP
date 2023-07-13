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
 * ルート一覧画面の情報を格納する。
 */
public class RouteListVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= 5901885144179004720L;
	
	/**
	 * 承認階層。
	 */
	private String				pltSearchRouteStage;
	
	/**
	 * ルートコード。
	 */
	private String				txtSearchRouteCode;
	
	/**
	 * ルート名称。
	 */
	private String				txtSearchRouteName;
	
	/**
	 * ユニットコード。
	 */
	private String				txtSearchUnitCode;
	
	/**
	 * ユニット名称。
	 */
	private String				txtSearchUnitName;
	
	/**
	 * 有効日。
	 */
	private String[]			aryLblActivateDate;
	/**
	 * ルートコード。
	 */
	private String[]			aryLblRouteCode;
	/**
	 * ルート名称。
	 */
	private String[]			aryLblRouteName;
	/**
	 * 承認階層。
	 */
	private String[]			aryLblRouteStage;
	/**
	 * 一次ユニット。
	 */
	private String[]			aryLblFirstUnit;
	/**
	 * 最終ユニット。
	 */
	private String[]			aryLblEndUnit;
	
	/**
	 * レコード識別ID。
	 */
	private String[]			aryCkbRouteListId;
	
	
	/**
	 * @return pltSearchRouteStage
	 */
	public String getPltSearchRouteStage() {
		return pltSearchRouteStage;
	}
	
	/**
	 * @param pltSearchRouteStage セットする pltSearchRouteStage
	 */
	public void setPltSearchRouteStage(String pltSearchRouteStage) {
		this.pltSearchRouteStage = pltSearchRouteStage;
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
	 * @return txtSearchUnitCode
	 */
	public String getTxtSearchUnitCode() {
		return txtSearchUnitCode;
	}
	
	/**
	 * @param txtSearchUnitCode セットする txtSearchUnitCode
	 */
	public void setTxtSearchUnitCode(String txtSearchUnitCode) {
		this.txtSearchUnitCode = txtSearchUnitCode;
	}
	
	/**
	 * @return txtSearchUnitName
	 */
	public String getTxtSearchUnitName() {
		return txtSearchUnitName;
	}
	
	/**
	 * @param txtSearchUnitName セットする txtSearchUnitName
	 */
	public void setTxtSearchUnitName(String txtSearchUnitName) {
		this.txtSearchUnitName = txtSearchUnitName;
	}
	
	@Override
	public String[] getAryLblActivateDate() {
		return getStringArrayClone(aryLblActivateDate);
	}
	
	@Override
	public void setAryLblActivateDate(String[] aryLblActivateDate) {
		this.aryLblActivateDate = getStringArrayClone(aryLblActivateDate);
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
	 * @return aryLblRouteName
	 */
	public String[] getAryLblRouteName() {
		return getStringArrayClone(aryLblRouteName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRouteStage
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
	 * @return aryLblRouteStage
	 */
	public String[] getAryLblRouteStage() {
		return getStringArrayClone(aryLblRouteStage);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRouteStage
	 */
	public String getAryLblRouteStage(int idx) {
		return aryLblRouteStage[idx];
	}
	
	/**
	 * @param aryLblRouteStage セットする aryLblRouteStage
	 */
	public void setAryLblRouteStage(String[] aryLblRouteStage) {
		this.aryLblRouteStage = getStringArrayClone(aryLblRouteStage);
	}
	
	/**
	 * @return aryLblFirstUnit
	 */
	public String[] getAryLblFirstUnit() {
		return getStringArrayClone(aryLblFirstUnit);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblFirstUnit
	 */
	public String getAryLblFirstUnit(int idx) {
		return aryLblFirstUnit[idx];
	}
	
	/**
	 * @param aryLblFirstUnit セットする aryLblFirstUnit
	 */
	public void setAryLblFirstUnit(String[] aryLblFirstUnit) {
		this.aryLblFirstUnit = getStringArrayClone(aryLblFirstUnit);
	}
	
	/**
	 * @return aryLblEndUnit
	 */
	public String[] getAryLblEndUnit() {
		return getStringArrayClone(aryLblEndUnit);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEndUnit
	 */
	public String getAryLblEndUnit(int idx) {
		return aryLblEndUnit[idx];
	}
	
	/**
	 * @param aryLblEndUnit セットする aryLblEndUnit
	 */
	public void setAryLblEndUnit(String[] aryLblEndUnit) {
		this.aryLblEndUnit = getStringArrayClone(aryLblEndUnit);
	}
	
	/**
	 * @return aryCkbRouteListId
	 */
	public String[] getAryCkbRouteListId() {
		return getStringArrayClone(aryCkbRouteListId);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryCkbRouteListId
	 */
	public String getAryCkbRouteListId(int idx) {
		return aryCkbRouteListId[idx];
	}
	
	/**
	 * @param aryCkbRouteListId セットする aryCkbRouteListId
	 */
	public void setAryCkbRouteListId(String[] aryCkbRouteListId) {
		this.aryCkbRouteListId = getStringArrayClone(aryCkbRouteListId);
	}
	
}
