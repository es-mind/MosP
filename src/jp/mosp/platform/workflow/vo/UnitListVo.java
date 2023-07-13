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
 * ユニット一覧画面の情報を格納する。
 */
public class UnitListVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= 4757211172747015061L;
	
	/**
	 * ユニットコード。
	 */
	private String				txtSearchUnitCode;
	
	/**
	 * ユニット名称。
	 */
	private String				txtSearchUnitName;
	
	/**
	 * ユニット区分。
	 */
	private String				pltSearchUnitType;
	
	/**
	 * 所属名称。
	 */
	private String				txtSearchSectionName;
	
	/**
	 * 職位名称。
	 */
	private String				txtSearchPositoinName;
	
	/**
	 * 社員コード。
	 */
	private String				txtSearchEmployeeCode;
	
	/**
	 * 承認者。
	 */
	private String				txtSearchApprover;
	
	// 情報一覧
	/**
	 * 有効日。
	 */
	private String[]			aryLblActivateDate;
	/**
	 * ユニットコード。
	 */
	private String[]			aryLblUnitCode;
	/**
	 * ユニット名称。
	 */
	private String[]			aryLblUnitName;
	/**
	 * ユニット区分。
	 */
	private String[]			aryLblUnitType;
	/**
	 * 承認者。
	 */
	private String[]			aryLblApproval;
	/**
	 * 有効/無効。
	 */
	private String[]			aryLblInactivate;
	/**
	 * レコード識別ID。
	 */
	private String[]			aryCkbUnitListId;
	
	
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
	
	/**
	 * @return pltSearchUnitType
	 */
	public String getPltSearchUnitType() {
		return pltSearchUnitType;
	}
	
	/**
	 * @param pltSearchUnitType セットする pltSearchUnitType
	 */
	public void setPltSearchUnitType(String pltSearchUnitType) {
		this.pltSearchUnitType = pltSearchUnitType;
	}
	
	/**
	 * @return txtSearchSectionName
	 */
	public String getTxtSearchSectionName() {
		return txtSearchSectionName;
	}
	
	/**
	 * @param txtSearchSectionName セットする txtSearchSectionName
	 */
	public void setTxtSearchSectionName(String txtSearchSectionName) {
		this.txtSearchSectionName = txtSearchSectionName;
	}
	
	/**
	 * @return txtSearchPositoinName
	 */
	public String getTxtSearchPositoinName() {
		return txtSearchPositoinName;
	}
	
	/**
	 * @param txtSearchPositoinName セットする txtSearchPositoinName
	 */
	public void setTxtSearchPositoinName(String txtSearchPositoinName) {
		this.txtSearchPositoinName = txtSearchPositoinName;
	}
	
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
	 * @return txtSearchApprover
	 */
	public String getTxtSearchApprover() {
		return txtSearchApprover;
	}
	
	/**
	 * @param txtSearchApprover セットする txtSearchApprover
	 */
	public void setTxtSearchApprover(String txtSearchApprover) {
		this.txtSearchApprover = txtSearchApprover;
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
	 * @return aryLblUnitCode
	 */
	public String[] getAryLblUnitCode() {
		return getStringArrayClone(aryLblUnitCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblUnitCode
	 */
	public String getAryLblUnitCode(int idx) {
		return aryLblUnitCode[idx];
	}
	
	/**
	 * @param aryLblUnitCode セットする aryLblUnitCode
	 */
	public void setAryLblUnitCode(String[] aryLblUnitCode) {
		this.aryLblUnitCode = getStringArrayClone(aryLblUnitCode);
	}
	
	/**
	 * @return aryLblUnitName
	 */
	public String[] getAryLblUnitName() {
		return getStringArrayClone(aryLblUnitName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblUnitName
	 */
	public String getAryLblUnitName(int idx) {
		return aryLblUnitName[idx];
	}
	
	/**
	 * @param aryLblUnitName セットする aryLblUnitName
	 */
	public void setAryLblUnitName(String[] aryLblUnitName) {
		this.aryLblUnitName = getStringArrayClone(aryLblUnitName);
	}
	
	/**
	 * @return aryLblUnitType
	 */
	public String[] getAryLblUnitType() {
		return getStringArrayClone(aryLblUnitType);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblUnitType
	 */
	public String getAryLblUnitType(int idx) {
		return aryLblUnitType[idx];
	}
	
	/**
	 * @param aryLblUnitType セットする aryLblUnitType
	 */
	public void setAryLblUnitType(String[] aryLblUnitType) {
		this.aryLblUnitType = getStringArrayClone(aryLblUnitType);
	}
	
	/**
	 * @return aryLblApproval
	 */
	public String[] getAryLblApproval() {
		return getStringArrayClone(aryLblApproval);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblApproval
	 */
	public String getAryLblApproval(int idx) {
		return aryLblApproval[idx];
	}
	
	/**
	 * @param aryLblApproval セットする aryLblApproval
	 */
	public void setAryLblApproval(String[] aryLblApproval) {
		this.aryLblApproval = getStringArrayClone(aryLblApproval);
	}
	
	@Override
	public String[] getAryLblInactivate() {
		return getStringArrayClone(aryLblInactivate);
	}
	
	@Override
	public void setAryLblInactivate(String[] aryLblInactivate) {
		this.aryLblInactivate = getStringArrayClone(aryLblInactivate);
	}
	
	/**
	 * @return aryCkbUnitListId
	 */
	public String[] getAryCkbUnitListId() {
		return getStringArrayClone(aryCkbUnitListId);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryCkbUnitListId
	 */
	public String getAryCkbUnitListId(int idx) {
		return aryCkbUnitListId[idx];
	}
	
	/**
	 * @param aryCkbUnitListId セットする aryCkbUnitListId
	 */
	public void setAryCkbUnitListId(String[] aryCkbUnitListId) {
		this.aryCkbUnitListId = getStringArrayClone(aryCkbUnitListId);
	}
	
}
