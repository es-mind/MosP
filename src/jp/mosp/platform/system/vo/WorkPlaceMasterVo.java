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
package jp.mosp.platform.system.vo;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * 勤務地マスタ画面の情報を格納する。
 */
public class WorkPlaceMasterVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -9195392506546882488L;
	
	private String				txtEditWorkPlaceCode;
	private String				txtEditWorkPlaceName;
	private String				txtEditWorkPlaceKana;
	private String				txtEditWorkPlaceAbbr;
	private String				txtEditPostalCode1;
	private String				txtEditPostalCode2;
	private String				pltEditPrefecture;
	private String				txtEditAddress1;
	private String				txtEditAddress2;
	private String				txtEditAddress3;
	private String				txtEditPhoneNumber1;
	private String				txtEditPhoneNumber2;
	private String				txtEditPhoneNumber3;
	
	private String				txtSearchWorkPlaceCode;
	private String				txtSearchWorkPlaceName;
	private String				txtSearchWorkPlaceKana;
	private String				txtSearchWorkPlaceAbbr;
	private String				txtSearchPostalCode1;
	private String				txtSearchPostalCode2;
	private String				pltSearchPrefecture;
	private String				txtSearchAddress1;
	private String				txtSearchAddress2;
	private String				txtSearchAddress3;
	private String				txtSearchPhoneNumber1;
	private String				txtSearchPhoneNumber2;
	private String				txtSearchPhoneNumber3;
	
	private String[]			aryLblWorkPlaceCode;
	private String[]			aryLblWorkPlaceName;
	private String[]			aryLblWorkPlaceAbbr;
	private String[]			aryLblPostalCode;
	private String[]			aryLblPhoneNumber;
	
	private String[][]			aryPltEditPrefecture;
	private String[][]			aryPltSearchPrefecture;
	
	
	/**
	 * @return txtEditWorkPlaceCode
	 */
	public String getTxtEditWorkPlaceCode() {
		return txtEditWorkPlaceCode;
	}
	
	/**
	 * @param txtEditWorkPlaceCode セットする txtEditWorkPlaceCode
	 */
	public void setTxtEditWorkPlaceCode(String txtEditWorkPlaceCode) {
		this.txtEditWorkPlaceCode = txtEditWorkPlaceCode;
	}
	
	/**
	 * @return txtEditWorkPlaceName
	 */
	public String getTxtEditWorkPlaceName() {
		return txtEditWorkPlaceName;
	}
	
	/**
	 * @param txtEditWorkPlaceName セットする txtEditWorkPlaceName
	 */
	public void setTxtEditWorkPlaceName(String txtEditWorkPlaceName) {
		this.txtEditWorkPlaceName = txtEditWorkPlaceName;
	}
	
	/**
	 * @return txtEditWorkPlaceKana
	 */
	public String getTxtEditWorkPlaceKana() {
		return txtEditWorkPlaceKana;
	}
	
	/**
	 * @param txtEditWorkPlaceKana セットする txtEditWorkPlaceKana
	 */
	public void setTxtEditWorkPlaceKana(String txtEditWorkPlaceKana) {
		this.txtEditWorkPlaceKana = txtEditWorkPlaceKana;
	}
	
	/**
	 * @return txtEditWorkPlaceAbbr
	 */
	public String getTxtEditWorkPlaceAbbr() {
		return txtEditWorkPlaceAbbr;
	}
	
	/**
	 * @param txtEditWorkPlaceAbbr セットする txtEditWorkPlaceAbbr
	 */
	public void setTxtEditWorkPlaceAbbr(String txtEditWorkPlaceAbbr) {
		this.txtEditWorkPlaceAbbr = txtEditWorkPlaceAbbr;
	}
	
	/**
	 * @return txtEditPostalCode1
	 */
	public String getTxtEditPostalCode1() {
		return txtEditPostalCode1;
	}
	
	/**
	 * @param txtEditPostalCode1 セットする txtEditPostalCode1
	 */
	public void setTxtEditPostalCode1(String txtEditPostalCode1) {
		this.txtEditPostalCode1 = txtEditPostalCode1;
	}
	
	/**
	 * @return txtEditPostalCode2
	 */
	public String getTxtEditPostalCode2() {
		return txtEditPostalCode2;
	}
	
	/**
	 * @param txtEditPostalCode2 セットする txtEditPostalCode2
	 */
	public void setTxtEditPostalCode2(String txtEditPostalCode2) {
		this.txtEditPostalCode2 = txtEditPostalCode2;
	}
	
	/**
	 * @return pltEditPrefecture
	 */
	public String getPltEditPrefecture() {
		return pltEditPrefecture;
	}
	
	/**
	 * @param pltEditPrefecture セットする pltEditPrefecture
	 */
	public void setPltEditPrefecture(String pltEditPrefecture) {
		this.pltEditPrefecture = pltEditPrefecture;
	}
	
	/**
	 * @return txtEditAddress1
	 */
	public String getTxtEditAddress1() {
		return txtEditAddress1;
	}
	
	/**
	 * @param txtEditAddress1 セットする txtEditAddress1
	 */
	public void setTxtEditAddress1(String txtEditAddress1) {
		this.txtEditAddress1 = txtEditAddress1;
	}
	
	/**
	 * @return txtEditAddress2
	 */
	public String getTxtEditAddress2() {
		return txtEditAddress2;
	}
	
	/**
	 * @param txtEditAddress2 セットする txtEditAddress2
	 */
	public void setTxtEditAddress2(String txtEditAddress2) {
		this.txtEditAddress2 = txtEditAddress2;
	}
	
	/**
	 * @return txtEditAddress3
	 */
	public String getTxtEditAddress3() {
		return txtEditAddress3;
	}
	
	/**
	 * @param txtEditAddress3 セットする txtEditAddress3
	 */
	public void setTxtEditAddress3(String txtEditAddress3) {
		this.txtEditAddress3 = txtEditAddress3;
	}
	
	/**
	 * @return txtEditPhoneNumber1
	 */
	public String getTxtEditPhoneNumber1() {
		return txtEditPhoneNumber1;
	}
	
	/**
	 * @param txtEditPhoneNumber1 セットする txtEditPhoneNumber1
	 */
	public void setTxtEditPhoneNumber1(String txtEditPhoneNumber1) {
		this.txtEditPhoneNumber1 = txtEditPhoneNumber1;
	}
	
	/**
	 * @return txtEditPhoneNumber2
	 */
	public String getTxtEditPhoneNumber2() {
		return txtEditPhoneNumber2;
	}
	
	/**
	 * @param txtEditPhoneNumber2 セットする txtEditPhoneNumber2
	 */
	public void setTxtEditPhoneNumber2(String txtEditPhoneNumber2) {
		this.txtEditPhoneNumber2 = txtEditPhoneNumber2;
	}
	
	/**
	 * @return txtEditPhoneNumber3
	 */
	public String getTxtEditPhoneNumber3() {
		return txtEditPhoneNumber3;
	}
	
	/**
	 * @param txtEditPhoneNumber3 セットする txtEditPhoneNumber3
	 */
	public void setTxtEditPhoneNumber3(String txtEditPhoneNumber3) {
		this.txtEditPhoneNumber3 = txtEditPhoneNumber3;
	}
	
	/**
	 * @return txtSearchWorkPlaceCode
	 */
	public String getTxtSearchWorkPlaceCode() {
		return txtSearchWorkPlaceCode;
	}
	
	/**
	 * @param txtSearchWorkPlaceCode セットする txtSearchWorkPlaceCode
	 */
	public void setTxtSearchWorkPlaceCode(String txtSearchWorkPlaceCode) {
		this.txtSearchWorkPlaceCode = txtSearchWorkPlaceCode;
	}
	
	/**
	 * @return txtSearchWorkPlaceName
	 */
	public String getTxtSearchWorkPlaceName() {
		return txtSearchWorkPlaceName;
	}
	
	/**
	 * @param txtSearchWorkPlaceName セットする txtSearchWorkPlaceName
	 */
	public void setTxtSearchWorkPlaceName(String txtSearchWorkPlaceName) {
		this.txtSearchWorkPlaceName = txtSearchWorkPlaceName;
	}
	
	/**
	 * @return txtSearchWorkPlaceKana
	 */
	public String getTxtSearchWorkPlaceKana() {
		return txtSearchWorkPlaceKana;
	}
	
	/**
	 * @param txtSearchWorkPlaceKana セットする txtSearchWorkPlaceKana
	 */
	public void setTxtSearchWorkPlaceKana(String txtSearchWorkPlaceKana) {
		this.txtSearchWorkPlaceKana = txtSearchWorkPlaceKana;
	}
	
	/**
	 * @return txtSearchWorkPlaceAbbr
	 */
	public String getTxtSearchWorkPlaceAbbr() {
		return txtSearchWorkPlaceAbbr;
	}
	
	/**
	 * @param txtSearchWorkPlaceAbbr セットする txtSearchWorkPlaceAbbr
	 */
	public void setTxtSearchWorkPlaceAbbr(String txtSearchWorkPlaceAbbr) {
		this.txtSearchWorkPlaceAbbr = txtSearchWorkPlaceAbbr;
	}
	
	/**
	 * @return txtSearchPostalCode1
	 */
	public String getTxtSearchPostalCode1() {
		return txtSearchPostalCode1;
	}
	
	/**
	 * @param txtSearchPostalCode1 セットする txtSearchPostalCode1
	 */
	public void setTxtSearchPostalCode1(String txtSearchPostalCode1) {
		this.txtSearchPostalCode1 = txtSearchPostalCode1;
	}
	
	/**
	 * @return txtSearchPostalCode2
	 */
	public String getTxtSearchPostalCode2() {
		return txtSearchPostalCode2;
	}
	
	/**
	 * @param txtSearchPostalCode2 セットする txtSearchPostalCode2
	 */
	public void setTxtSearchPostalCode2(String txtSearchPostalCode2) {
		this.txtSearchPostalCode2 = txtSearchPostalCode2;
	}
	
	/**
	 * @return pltSearchPrefecture
	 */
	public String getPltSearchPrefecture() {
		return pltSearchPrefecture;
	}
	
	/**
	 * @param pltSearchPrefecture セットする pltSearchPrefecture
	 */
	public void setPltSearchPrefecture(String pltSearchPrefecture) {
		this.pltSearchPrefecture = pltSearchPrefecture;
	}
	
	/**
	 * @return txtSearchAddress1
	 */
	public String getTxtSearchAddress1() {
		return txtSearchAddress1;
	}
	
	/**
	 * @param txtSearchAddress1 セットする txtSearchAddress1
	 */
	public void setTxtSearchAddress1(String txtSearchAddress1) {
		this.txtSearchAddress1 = txtSearchAddress1;
	}
	
	/**
	 * @return txtSearchAddress2
	 */
	public String getTxtSearchAddress2() {
		return txtSearchAddress2;
	}
	
	/**
	 * @param txtSearchAddress2 セットする txtSearchAddress2
	 */
	public void setTxtSearchAddress2(String txtSearchAddress2) {
		this.txtSearchAddress2 = txtSearchAddress2;
	}
	
	/**
	 * @return txtSearchAddress3
	 */
	public String getTxtSearchAddress3() {
		return txtSearchAddress3;
	}
	
	/**
	 * @param txtSearchAddress3 セットする txtSearchAddress3
	 */
	public void setTxtSearchAddress3(String txtSearchAddress3) {
		this.txtSearchAddress3 = txtSearchAddress3;
	}
	
	/**
	 * @return txtSearchPhoneNumber1
	 */
	public String getTxtSearchPhoneNumber1() {
		return txtSearchPhoneNumber1;
	}
	
	/**
	 * @param txtSearchPhoneNumber1 セットする txtSearchPhoneNumber1
	 */
	public void setTxtSearchPhoneNumber1(String txtSearchPhoneNumber1) {
		this.txtSearchPhoneNumber1 = txtSearchPhoneNumber1;
	}
	
	/**
	 * @return txtSearchPhoneNumber2
	 */
	public String getTxtSearchPhoneNumber2() {
		return txtSearchPhoneNumber2;
	}
	
	/**
	 * @param txtSearchPhoneNumber2 セットする txtSearchPhoneNumber2
	 */
	public void setTxtSearchPhoneNumber2(String txtSearchPhoneNumber2) {
		this.txtSearchPhoneNumber2 = txtSearchPhoneNumber2;
	}
	
	/**
	 * @return txtSearchPhoneNumber3
	 */
	public String getTxtSearchPhoneNumber3() {
		return txtSearchPhoneNumber3;
	}
	
	/**
	 * @param txtSearchPhoneNumber3 セットする txtSearchPhoneNumber3
	 */
	public void setTxtSearchPhoneNumber3(String txtSearchPhoneNumber3) {
		this.txtSearchPhoneNumber3 = txtSearchPhoneNumber3;
	}
	
	/**
	 * @return aryLblWorkPlaceCode
	 */
	public String[] getAryLblWorkPlaceCode() {
		return getStringArrayClone(aryLblWorkPlaceCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkPlaceCode
	 */
	public String getAryLblWorkPlaceCode(int idx) {
		return aryLblWorkPlaceCode[idx];
	}
	
	/**
	 * @param aryLblWorkPlaceCode セットする aryLblWorkPlaceCode
	 */
	public void setAryLblWorkPlaceCode(String[] aryLblWorkPlaceCode) {
		this.aryLblWorkPlaceCode = getStringArrayClone(aryLblWorkPlaceCode);
	}
	
	/**
	 * @return aryLblWorkPlaceName
	 */
	public String[] getAryLblWorkPlaceName() {
		return getStringArrayClone(aryLblWorkPlaceName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkPlaceName
	 */
	public String getAryLblWorkPlaceName(int idx) {
		return aryLblWorkPlaceName[idx];
	}
	
	/**
	 * @param aryLblWorkPlaceName セットする aryLblWorkPlaceName
	 */
	public void setAryLblWorkPlaceName(String[] aryLblWorkPlaceName) {
		this.aryLblWorkPlaceName = getStringArrayClone(aryLblWorkPlaceName);
	}
	
	/**
	 * @return aryLblWorkPlaceAbbr
	 */
	public String[] getAryLblWorkPlaceAbbr() {
		return getStringArrayClone(aryLblWorkPlaceAbbr);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkPlaceAbbr
	 */
	public String getAryLblWorkPlaceAbbr(int idx) {
		return aryLblWorkPlaceAbbr[idx];
	}
	
	/**
	 * @param aryLblWorkPlaceAbbr セットする aryLblWorkPlaceAbbr
	 */
	public void setAryLblWorkPlaceAbbr(String[] aryLblWorkPlaceAbbr) {
		this.aryLblWorkPlaceAbbr = getStringArrayClone(aryLblWorkPlaceAbbr);
	}
	
	/**
	 * @return aryLblPostalCode
	 */
	public String[] getAryLblPostalCode() {
		return getStringArrayClone(aryLblPostalCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblPostalCode
	 */
	public String getAryLblPostalCode(int idx) {
		return aryLblPostalCode[idx];
	}
	
	/**
	 * @param aryLblPostalCode セットする aryLblPostalCode
	 */
	public void setAryLblPostalCode(String[] aryLblPostalCode) {
		this.aryLblPostalCode = getStringArrayClone(aryLblPostalCode);
	}
	
	/**
	 * @return aryLblPhoneNumber
	 */
	public String[] getAryLblPhoneNumber() {
		return getStringArrayClone(aryLblPhoneNumber);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblPhoneNumber
	 */
	public String getAryLblPhoneNumber(int idx) {
		return aryLblPhoneNumber[idx];
	}
	
	/**
	 * @param aryLblPhoneNumber セットする aryLblPhoneNumber
	 */
	public void setAryLblPhoneNumber(String[] aryLblPhoneNumber) {
		this.aryLblPhoneNumber = getStringArrayClone(aryLblPhoneNumber);
	}
	
	/**
	 * @param aryPltEditPrefecture セットする aryPltEditPrefecture
	 */
	public void setAryPltEditPrefecture(String[][] aryPltEditPrefecture) {
		this.aryPltEditPrefecture = getStringArrayClone(aryPltEditPrefecture);
	}
	
	/**
	 * @return aryPltSectionCode
	 */
	public String[][] getAryPltEditPrefecture() {
		return getStringArrayClone(aryPltEditPrefecture);
	}
	
	/**
	 * @param aryPltSearchPrefecture セットする aryPltSearchPrefecture
	 */
	public void setAryPltSearchPrefecture(String[][] aryPltSearchPrefecture) {
		this.aryPltSearchPrefecture = getStringArrayClone(aryPltSearchPrefecture);
	}
	
	/**
	 * @return aryPltSectionCode
	 */
	public String[][] getAryPltSearchPrefecture() {
		return getStringArrayClone(aryPltSearchPrefecture);
	}
	
}
