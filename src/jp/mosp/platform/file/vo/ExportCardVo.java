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
package jp.mosp.platform.file.vo;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * エクスポート詳細の情報を格納する。
 */
public class ExportCardVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -3283087002163171654L;
	
	/**
	 * 一覧表示コマンド。<br>
	 * エクスポート詳細画面JSP内で用いられる。<br>
	 */
	private String				showListCommand;
	
	/**
	 * データ区分コードキー。<br>
	 */
	private String				tableTypeCodeKey;
	
	/**
	 * データ区分。
	 */
	private String				pltEditTable;
	
	/**
	 * エクスポートコード。
	 */
	private String				txtEditCode;
	
	/**
	 * エクスポート名称。
	 */
	private String				txtEditName;
	
	/**
	 * データ型。
	 */
	private String				pltEditType;
	
	/**
	 * ヘッダ有無。
	 */
	private String				pltEditHeader;
	
	/**
	 * データ区分名表示。
	 */
	private String				lblTableName;
	
	/**
	 * テーブル内容表示欄。
	 */
	private String[][]			jsPltSelectTable;
	
	/**
	 * テーブル内容選択欄。
	 */
	private String[]			jsPltSelectSelected;
	
	/**
	 * データ区分プルダウン。
	 */
	private String[][]			aryPltTableType;
	
	
	/**
	 * @return showListCommand
	 */
	public String getShowListCommand() {
		return showListCommand;
	}
	
	/**
	 * @param showListCommand セットする showListCommand
	 */
	public void setShowListCommand(String showListCommand) {
		this.showListCommand = showListCommand;
	}
	
	/**
	 * @return pltEditTable
	 */
	public String getPltEditTable() {
		return pltEditTable;
	}
	
	/**
	 * @param pltEditTable セットする pltEditTable
	 */
	public void setPltEditTable(String pltEditTable) {
		this.pltEditTable = pltEditTable;
	}
	
	/**
	 * @return txtEditCode
	 */
	public String getTxtEditCode() {
		return txtEditCode;
	}
	
	/**
	 * @param txtEditCode セットする txtEditCode
	 */
	public void setTxtEditCode(String txtEditCode) {
		this.txtEditCode = txtEditCode;
	}
	
	/**
	 * @return txtEditName
	 */
	public String getTxtEditName() {
		return txtEditName;
	}
	
	/**
	 * @param txtEditName セットする txtEditName
	 */
	public void setTxtEditName(String txtEditName) {
		this.txtEditName = txtEditName;
	}
	
	/**
	 * @return pltEditType
	 */
	public String getPltEditType() {
		return pltEditType;
	}
	
	/**
	 * @param pltEditType セットする pltEditType
	 */
	public void setPltEditType(String pltEditType) {
		this.pltEditType = pltEditType;
	}
	
	/**
	 * @return pltEditHeader
	 */
	public String getPltEditHeader() {
		return pltEditHeader;
	}
	
	/**
	 * @param pltEditHeader セットする pltEditHeader
	 */
	public void setPltEditHeader(String pltEditHeader) {
		this.pltEditHeader = pltEditHeader;
	}
	
	/**
	 * @return lblTableName
	 */
	public String getLblTableName() {
		return lblTableName;
	}
	
	/**
	 * @param lblTableName セットする lblTableName
	 */
	public void setLblTableName(String lblTableName) {
		this.lblTableName = lblTableName;
	}
	
	/**
	 * @return jsPltSelectTable
	 */
	public String[][] getJsPltSelectTable() {
		return getStringArrayClone(jsPltSelectTable);
	}
	
	/**
	 * @param jsPltSelectTable セットする jsPltSelectTable
	 */
	public void setJsPltSelectTable(String[][] jsPltSelectTable) {
		this.jsPltSelectTable = getStringArrayClone(jsPltSelectTable);
	}
	
	/**
	 * @return jsPltSelectSelected
	 */
	public String[] getJsPltSelectSelected() {
		return getStringArrayClone(jsPltSelectSelected);
	}
	
	/**
	 * @param jsPltSelectSelected セットする jsPltSelectSelected
	 */
	public void setJsPltSelectSelected(String[] jsPltSelectSelected) {
		this.jsPltSelectSelected = getStringArrayClone(jsPltSelectSelected);
	}
	
	/**
	 * @return aryPltTableType
	 */
	public String[][] getAryPltTableType() {
		return getStringArrayClone(aryPltTableType);
	}
	
	/**
	 * @param aryPltTableType セットする aryPltTableType
	 */
	public void setAryPltTableType(String[][] aryPltTableType) {
		this.aryPltTableType = getStringArrayClone(aryPltTableType);
	}
	
	/**
	 * @return tableTypeCodeKey
	 */
	public String getTableTypeCodeKey() {
		return tableTypeCodeKey;
	}
	
	/**
	 * @param tableTypeCodeKey セットする tableTypeCodeKey
	 */
	public void setTableTypeCodeKey(String tableTypeCodeKey) {
		this.tableTypeCodeKey = tableTypeCodeKey;
	}
	
}
