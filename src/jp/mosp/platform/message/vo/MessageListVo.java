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
package jp.mosp.platform.message.vo;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * メッセージ設定一覧画面の情報を格納する。
 */
public class MessageListVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= 7677681488775642819L;
	
	/**
	 * メッセージNo。
	 */
	private String				txtSearchMessageNo;
	
	/**
	 * メッセージ区分。
	 */
	private String				pltSearchMessageType;
	
	/**
	 * メッセージタイトル。
	 */
	private String				txtSearchMessageTitle;
	
	/**
	 * 登録者氏名。
	 */
	private String				txtSearchEmployeeName;
	
	/**
	 * 重要度。
	 */
	private String				pltSearchImportance;
	
	/**
	 * 一覧表示内容(表示期間(年))。
	 */
	private String[]			aryLblStartDate;
	
	/**
	 * 一覧表示内容(表示期間(月))。
	 */
	private String[]			aryLblEndDate;
	
	/**
	 * 一覧表示内容(メッセージNo)。
	 */
	private String[]			aryLblMessageNo;
	
	/**
	 * 一覧表示内容(メッセージ区分)。
	 */
	private String[]			aryLblMessageType;
	
	/**
	 * 一覧表示内容(メッセージタイトル)。
	 */
	private String[]			aryLblMessageTitle;
	
	/**
	 * 一覧表示内容(登録者氏名)。
	 */
	private String[]			aryLblEmployeeName;
	
	/**
	 * 一覧表示内容(重要度)。
	 */
	private String[]			aryLblImportance;
	
	
	/**
	 * @return txtSearchMessageNo
	 */
	public String getTxtSearchMessageNo() {
		return txtSearchMessageNo;
	}
	
	/**
	 * @param txtSearchMessageNo セットする txtSearchMessageNo
	 */
	public void setTxtSearchMessageNo(String txtSearchMessageNo) {
		this.txtSearchMessageNo = txtSearchMessageNo;
	}
	
	/**
	 * @return pltSearchMessageType
	 */
	public String getPltSearchMessageType() {
		return pltSearchMessageType;
	}
	
	/**
	 * @param pltSearchMessageType セットする pltSearchMessageType
	 */
	public void setPltSearchMessageType(String pltSearchMessageType) {
		this.pltSearchMessageType = pltSearchMessageType;
	}
	
	/**
	 * @return txtSearchMessageTitle
	 */
	public String getTxtSearchMessageTitle() {
		return txtSearchMessageTitle;
	}
	
	/**
	 * @param txtSearchMessageTitle セットする txtSearchMessageTitle
	 */
	public void setTxtSearchMessageTitle(String txtSearchMessageTitle) {
		this.txtSearchMessageTitle = txtSearchMessageTitle;
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
	 * @return pltSearchImportance
	 */
	public String getPltSearchImportance() {
		return pltSearchImportance;
	}
	
	/**
	 * @param pltSearchImportance セットする pltSearchImportance
	 */
	public void setPltSearchImportance(String pltSearchImportance) {
		this.pltSearchImportance = pltSearchImportance;
	}
	
	/**
	 * @return aryLblStartDate
	 */
	public String[] getAryLblStartDate() {
		return getStringArrayClone(aryLblStartDate);
	}
	
	/**
	 * @param aryLblStartDate セットする aryLblStartDate
	 */
	public void setAryLblStartDate(String[] aryLblStartDate) {
		this.aryLblStartDate = getStringArrayClone(aryLblStartDate);
	}
	
	/**
	 * @return aryLblEndDate
	 */
	public String[] getAryLblEndDate() {
		return getStringArrayClone(aryLblEndDate);
	}
	
	/**
	 * @param aryLblEndDate セットする aryLblEndDate
	 */
	public void setAryLblEndDate(String[] aryLblEndDate) {
		this.aryLblEndDate = getStringArrayClone(aryLblEndDate);
	}
	
	/**
	 * @return aryLblMessageNo
	 */
	public String[] getAryLblMessageNo() {
		return getStringArrayClone(aryLblMessageNo);
	}
	
	/**
	 * @param aryLblMessageNo セットする aryLblMessageNo
	 */
	public void setAryLblMessageNo(String[] aryLblMessageNo) {
		this.aryLblMessageNo = getStringArrayClone(aryLblMessageNo);
	}
	
	/**
	 * @return aryLblMessageType
	 */
	public String[] getAryLblMessageType() {
		return getStringArrayClone(aryLblMessageType);
	}
	
	/**
	 * @param aryLblMessageType セットする aryLblMessageType
	 */
	public void setAryLblMessageType(String[] aryLblMessageType) {
		this.aryLblMessageType = getStringArrayClone(aryLblMessageType);
	}
	
	/**
	 * @return aryLblMessageTitle
	 */
	public String[] getAryLblMessageTitle() {
		return getStringArrayClone(aryLblMessageTitle);
	}
	
	/**
	 * @param aryLblMessageTitle セットする aryLblMessageTitle
	 */
	public void setAryLblMessageTitle(String[] aryLblMessageTitle) {
		this.aryLblMessageTitle = getStringArrayClone(aryLblMessageTitle);
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
	 * @return aryLblImportance
	 */
	public String[] getAryLblImportance() {
		return getStringArrayClone(aryLblImportance);
	}
	
	/**
	 * @param aryLblImportance セットする aryLblImportance
	 */
	public void setAryLblImportance(String[] aryLblImportance) {
		this.aryLblImportance = getStringArrayClone(aryLblImportance);
	}
	
}
