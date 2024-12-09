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
package jp.mosp.platform.file.vo;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * インポート一覧の情報を格納する。
 */
public class ImportListVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -8594907014973010619L;
	
	/**
	 * 再表示コマンド。<br>
	 * インポート一覧JSP内で用いられる。<br>
	 */
	private String				reShowCommand;
	
	/**
	 * 検索コマンド。<br>
	 * インポート一覧JSP内で用いられる。<br>
	 */
	private String				searchCommand;
	
	/**
	 * 並び替えコマンド。<br>
	 * インポート一覧JSP内で用いられる。<br>
	 */
	private String				sortCommand;
	
	/**
	 * 実行コマンド。<br>
	 * インポート一覧JSP内で用いられる。<br>
	 */
	private String				executeCommand;
	
	/**
	 * テンプレート出力コマンド。<br>
	 * インポート一覧JSP内で用いられる。<br>
	 */
	private String				templateOutputCommand;
	
	/**
	 * データ区分コードキー。<br>
	 */
	private String				tableTypeCodeKey;
	
	/**
	 * エクスポートコード。
	 */
	private String				txtSearchCode;
	
	/**
	 * エクスポート名称。
	 */
	private String				txtSearchName;
	
	/**
	 * データ区分。
	 */
	private String				pltSearchTable;
	
	/**
	 * データ型。
	 */
	private String				pltSearchType;
	
	/**
	 * ヘッダ有無。
	 */
	private String				pltSearchHeader;
	
	/**
	 * 検索無効フラグ。<br>
	 */
	private String				pltSearchInactivate;
	
	/**
	 * 選択ラジオボタン。
	 */
	private String				radSelect;
	
	/**
	 * 一覧表示内容(エクスポートコード)。
	 */
	private String[]			aryLblCode;
	
	/**
	 * 一覧表示内容(エクスポート名称)。
	 */
	private String[]			aryLblName;
	
	/**
	 * 一覧表示内容(データ区分)。
	 */
	private String[]			aryLblTable;
	
	/**
	 * 一覧表示内容(データ型)。
	 */
	private String[]			aryLblType;
	
	/**
	 * 一覧表示内容(ヘッダ有無)。
	 */
	private String[]			aryLblHeader;
	
	/**
	 * 一覧項目(無効フラグ)。<br>
	 */
	private String[]			aryLblInactivate;
	
	/**
	 * データ区分プルダウン。
	 */
	private String[][]			aryPltTableType;
	
	
	/**
	 * @return reShowCommand
	 */
	public String getReShowCommand() {
		return reShowCommand;
	}
	
	/**
	 * @param reShowCommand セットする reShowCommand
	 */
	public void setReShowCommand(String reShowCommand) {
		this.reShowCommand = reShowCommand;
	}
	
	/**
	 * @return searchCommand
	 */
	public String getSearchCommand() {
		return searchCommand;
	}
	
	/**
	 * @param searchCommand セットする searchCommand
	 */
	public void setSearchCommand(String searchCommand) {
		this.searchCommand = searchCommand;
	}
	
	/**
	 * @return sortCommand
	 */
	public String getSortCommand() {
		return sortCommand;
	}
	
	/**
	 * @param sortCommand セットする sortCommand
	 */
	public void setSortCommand(String sortCommand) {
		this.sortCommand = sortCommand;
	}
	
	/**
	 * @return executeCommand
	 */
	public String getExecuteCommand() {
		return executeCommand;
	}
	
	/**
	 * @param executeCommand セットする executeCommand
	 */
	public void setExecuteCommand(String executeCommand) {
		this.executeCommand = executeCommand;
	}
	
	/**
	 * @return templateOutputCommand
	 */
	public String getTemplateOutputCommand() {
		return templateOutputCommand;
	}
	
	/**
	 * @param templateOutputCommand セットする templateOutputCommand
	 */
	public void setTemplateOutputCommand(String templateOutputCommand) {
		this.templateOutputCommand = templateOutputCommand;
	}
	
	/**
	 * @return txtSearchCode
	 */
	public String getTxtSearchCode() {
		return txtSearchCode;
	}
	
	/**
	 * @param txtSearchCode セットする txtSearchCode
	 */
	public void setTxtSearchCode(String txtSearchCode) {
		this.txtSearchCode = txtSearchCode;
	}
	
	/**
	 * @return txtSearchName
	 */
	public String getTxtSearchName() {
		return txtSearchName;
	}
	
	/**
	 * @param txtSearchName セットする txtSearchName
	 */
	public void setTxtSearchName(String txtSearchName) {
		this.txtSearchName = txtSearchName;
	}
	
	/**
	 * @return pltSearchTable
	 */
	public String getPltSearchTable() {
		return pltSearchTable;
	}
	
	/**
	 * @param pltSearchTable セットする pltSearchTable
	 */
	public void setPltSearchTable(String pltSearchTable) {
		this.pltSearchTable = pltSearchTable;
	}
	
	/**
	 * @return pltSearchType
	 */
	public String getPltSearchType() {
		return pltSearchType;
	}
	
	/**
	 * @param pltSearchType セットする pltSearchType
	 */
	public void setPltSearchType(String pltSearchType) {
		this.pltSearchType = pltSearchType;
	}
	
	/**
	 * @return pltSearchHeader
	 */
	public String getPltSearchHeader() {
		return pltSearchHeader;
	}
	
	/**
	 * @param pltSearchHeader セットする pltSearchHeader
	 */
	public void setPltSearchHeader(String pltSearchHeader) {
		this.pltSearchHeader = pltSearchHeader;
	}
	
	/**
	 * @return pltSearchInactivate
	 */
	@Override
	public String getPltSearchInactivate() {
		return pltSearchInactivate;
	}
	
	/**
	 * @param pltSearchInactivate セットする pltSearchInactivate
	 */
	@Override
	public void setPltSearchInactivate(String pltSearchInactivate) {
		this.pltSearchInactivate = pltSearchInactivate;
	}
	
	/**
	 * @return radSelect
	 */
	public String getRadSelect() {
		return radSelect;
	}
	
	/**
	 * @param radSelect セットする radSelect
	 */
	public void setRadSelect(String radSelect) {
		this.radSelect = radSelect;
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblCode
	 */
	public String getAryLblCode(int idx) {
		return aryLblCode[idx];
	}
	
	/**
	 * @param aryLblCode セットする aryLblCode
	 */
	public void setAryLblCode(String[] aryLblCode) {
		this.aryLblCode = getStringArrayClone(aryLblCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblName
	 */
	public String getAryLblName(int idx) {
		return aryLblName[idx];
	}
	
	/**
	 * @param aryLblName セットする aryLblName
	 */
	public void setAryLblName(String[] aryLblName) {
		this.aryLblName = getStringArrayClone(aryLblName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblTable
	 */
	public String getAryLblTable(int idx) {
		return aryLblTable[idx];
	}
	
	/**
	 * @param aryLblTable セットする aryLblTable
	 */
	public void setAryLblTable(String[] aryLblTable) {
		this.aryLblTable = getStringArrayClone(aryLblTable);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblType
	 */
	public String getAryLblType(int idx) {
		return aryLblType[idx];
	}
	
	/**
	 * @param aryLblType セットする aryLblType
	 */
	public void setAryLblType(String[] aryLblType) {
		this.aryLblType = getStringArrayClone(aryLblType);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblHeader
	 */
	public String getAryLblHeader(int idx) {
		return aryLblHeader[idx];
	}
	
	/**
	 * @param aryLblHeader セットする aryLblHeader
	 */
	public void setAryLblHeader(String[] aryLblHeader) {
		this.aryLblHeader = getStringArrayClone(aryLblHeader);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblInactivate
	 */
	@Override
	public String getAryLblInactivate(int idx) {
		return aryLblInactivate[idx];
	}
	
	/**
	 * @return aryLblInactivate
	 */
	@Override
	public String[] getAryLblInactivate() {
		return getStringArrayClone(aryLblInactivate);
	}
	
	/**
	 * @param aryLblInactivate セットする aryLblInactivate
	 */
	@Override
	public void setAryLblInactivate(String[] aryLblInactivate) {
		this.aryLblInactivate = getStringArrayClone(aryLblInactivate);
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
