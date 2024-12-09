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
 * 名称区分マスタ詳細画面の情報を格納する。
 */
public class NamingMasterVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -7069387880170547345L;
	
	/**
	 * 名称区分名称を格納する。
	 */
	private String				stringNamingType;
	
	/**
	 * 名称区分コードを格納する。
	 */
	private String				codeNamingType;
	
	/**
	 * 登録・編集
	 */
	private String				txtEditNamingItemCode;
	private String				txtEditNamingItemName;
	private String				txtEditNamingItemAbbr;
	
	/**
	 * 検索
	 */
	private String				txtSearchNamingItemCode;
	private String				txtSearchNamingItemName;
	private String				txtSearchNamingItemAbbr;
	
	/**
	 * 検索結果
	 */
	private String[]			aryLblNamingItemCode;
	private String[]			aryLblNamingItemName;
	private String[]			aryLblNamingItemAbbr;
	
	/**
	 * 表示コマンド。<br>
	 * コードからパンクズのメニューに表示する名称区分を取得するのに用いる。
	 */
	private String				showCommand;
	
	
	/**
	 * @return codeNamingType
	 */
	public String getCodeNamingType() {
		return codeNamingType;
	}
	
	/**
	 * @param codeNamingType セットする codeNamingType
	 */
	public void setCodeNamingType(String codeNamingType) {
		this.codeNamingType = codeNamingType;
	}
	
	/**
	 * @return stringNamingType
	 */
	public String getStringNamingType() {
		return stringNamingType;
	}
	
	/**
	 * @param stringNamingType セットする stringNamingType
	 */
	public void setStringNamingType(String stringNamingType) {
		this.stringNamingType = stringNamingType;
	}
	
	/**
	 * @return txtEditNamingItemCode
	 */
	public String getTxtEditNamingItemCode() {
		return txtEditNamingItemCode;
	}
	
	/**
	 * @param txtEditNamingItemCode セットする txtEditNamingItemCode
	 */
	public void setTxtEditNamingItemCode(String txtEditNamingItemCode) {
		this.txtEditNamingItemCode = txtEditNamingItemCode;
	}
	
	/**
	 * @return txtEditNamingItemName
	 */
	public String getTxtEditNamingItemName() {
		return txtEditNamingItemName;
	}
	
	/**
	 * @param txtEditNamingItemName セットする txtEditNamingItemName
	 */
	public void setTxtEditNamingItemName(String txtEditNamingItemName) {
		this.txtEditNamingItemName = txtEditNamingItemName;
	}
	
	/**
	 * @return txtEditNamingItemAbbr
	 */
	public String getTxtEditNamingItemAbbr() {
		return txtEditNamingItemAbbr;
	}
	
	/**
	 * @param txtEditNamingItemAbbr セットする txtEditNamingItemAbbr
	 */
	public void setTxtEditNamingItemAbbr(String txtEditNamingItemAbbr) {
		this.txtEditNamingItemAbbr = txtEditNamingItemAbbr;
	}
	
	/**
	 * @return txtSearchNamingItemCode
	 */
	public String getTxtSearchNamingItemCode() {
		return txtSearchNamingItemCode;
	}
	
	/**
	 * @param txtSearchNamingItemCode セットする txtSearchNamingItemCode
	 */
	public void setTxtSearchNamingItemCode(String txtSearchNamingItemCode) {
		this.txtSearchNamingItemCode = txtSearchNamingItemCode;
	}
	
	/**
	 * @return txtSearchNamingItemName
	 */
	public String getTxtSearchNamingItemName() {
		return txtSearchNamingItemName;
	}
	
	/**
	 * @param txtSearchNamingItemName セットする txtSearchNamingItemName
	 */
	public void setTxtSearchNamingItemName(String txtSearchNamingItemName) {
		this.txtSearchNamingItemName = txtSearchNamingItemName;
	}
	
	/**
	 * @return txtSearchNamingItemAbbr
	 */
	public String getTxtSearchNamingItemAbbr() {
		return txtSearchNamingItemAbbr;
	}
	
	/**
	 * @param txtSearchNamingItemAbbr セットする txtSearchNamingItemAbbr
	 */
	public void setTxtSearchNamingItemAbbr(String txtSearchNamingItemAbbr) {
		this.txtSearchNamingItemAbbr = txtSearchNamingItemAbbr;
	}
	
	/**
	 * @return aryLblNamingItemCode
	 */
	public String[] getAryLblNamingItemCode() {
		return getStringArrayClone(aryLblNamingItemCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblNamingItemCode
	 */
	public String getAryLblNamingItemCode(int idx) {
		return aryLblNamingItemCode[idx];
	}
	
	/**
	 * @param aryLblNamingItemCode セットする aryLblNamingItemCode
	 */
	public void setAryLblNamingItemCode(String[] aryLblNamingItemCode) {
		this.aryLblNamingItemCode = getStringArrayClone(aryLblNamingItemCode);
	}
	
	/**
	 * @return aryLblNamingItemName
	 */
	public String[] getAryLblNamingItemName() {
		return getStringArrayClone(aryLblNamingItemName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblNamingItemName
	 */
	public String getAryLblNamingItemName(int idx) {
		return aryLblNamingItemName[idx];
	}
	
	/**
	 * @param aryLblNamingItemName セットする aryLblNamingItemName
	 */
	public void setAryLblNamingItemName(String[] aryLblNamingItemName) {
		this.aryLblNamingItemName = getStringArrayClone(aryLblNamingItemName);
	}
	
	/**
	 * @return aryLblNamingItemAbbr
	 */
	public String[] getAryLblNamingItemAbbr() {
		return getStringArrayClone(aryLblNamingItemAbbr);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblNamingItemAbbr
	 */
	public String getAryLblNamingItemAbbr(int idx) {
		return aryLblNamingItemAbbr[idx];
	}
	
	/**
	 * @param aryLblNamingItemAbbr セットする aryLblNamingItemAbbr
	 */
	public void setAryLblNamingItemAbbr(String[] aryLblNamingItemAbbr) {
		this.aryLblNamingItemAbbr = getStringArrayClone(aryLblNamingItemAbbr);
	}
	
	/**
	 * @return showCommand
	 */
	public String getShowCommand() {
		return showCommand;
	}
	
	/**
	 * @param showCommand セットする showCommand
	 */
	public void setShowCommand(String showCommand) {
		this.showCommand = showCommand;
	}
}
