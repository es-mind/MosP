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
package jp.mosp.platform.human.vo;

import jp.mosp.platform.human.base.PlatformHumanVo;

/**
 * 人事汎用バイナリ履歴情報登録画面の情報を格納する。
 */
public class HumanBinaryHistoryCardVo extends PlatformHumanVo {
	
	private static final long	serialVersionUID	= 6165422292502251282L;
	
	/**
	 * 有効日
	 */
	private String				txtActivateYear;
	private String				txtActivateMonth;
	private String				txtActivateDay;
	
	/**
	 * ファイル拡張子(プルダウン)
	 */
	private String[][]			aryPltFileType;
	
	/**
	 * ファイル拡張子
	 */
	private String				pltFileType;
	
	/**
	 * ファイル名
	 */
	private String				fileBinaryHistory;
	
	/**
	 * ファイル備考
	 */
	private String				txtFileRemark;
	
	/**
	 * レコード識別ID
	 */
	private Long				hidRecordId;
	
	
	/**
	 * @param txtActivateYear セットする txtActivateYear
	 */
	public void setTxtActivateYear(String txtActivateYear) {
		this.txtActivateYear = txtActivateYear;
	}
	
	/**
	 * @return txtActivateYear
	 */
	public String getTxtActivateYear() {
		return txtActivateYear;
	}
	
	/**
	 * @param txtActivateMonth セットする txtActivateMonth
	 */
	public void setTxtActivateMonth(String txtActivateMonth) {
		this.txtActivateMonth = txtActivateMonth;
	}
	
	/**
	 * @return txtActivateMonth
	 */
	public String getTxtActivateMonth() {
		return txtActivateMonth;
	}
	
	/**
	 * @param txtActivateDay セットする txtActivateDay
	 */
	public void setTxtActivateDay(String txtActivateDay) {
		this.txtActivateDay = txtActivateDay;
	}
	
	/**
	 * @return txtActivateDay
	 */
	public String getTxtActivateDay() {
		return txtActivateDay;
	}
	
	/**
	 * @param aryPltFileType セットする aryPltFileType
	 */
	public void setAryPltFileType(String[][] aryPltFileType) {
		this.aryPltFileType = getStringArrayClone(aryPltFileType);
	}
	
	/**
	 * @return aryPltFileType
	 */
	public String[][] getAryPltFileType() {
		return getStringArrayClone(aryPltFileType);
	}
	
	/**
	 * @param pltFileType セットする pltFileType
	 */
	public void setPltFileType(String pltFileType) {
		this.pltFileType = pltFileType;
	}
	
	/**
	 * @return pltFileType
	 */
	public String getPltFileType() {
		return pltFileType;
	}
	
	/**
	 * @param fileBinaryHistory セットする fileBinaryHistory
	 */
	public void setFileBinaryHistory(String fileBinaryHistory) {
		this.fileBinaryHistory = fileBinaryHistory;
	}
	
	/**
	 * @return fileBinaryHistory
	 */
	public String getFileBinaryHistory() {
		return fileBinaryHistory;
	}
	
	/**
	 * @param txtFileRemark セットする txtFileRemark
	 */
	public void setTxtFileRemark(String txtFileRemark) {
		this.txtFileRemark = txtFileRemark;
	}
	
	/**
	 * @return txtFileRemark
	 */
	public String getTxtFileRemark() {
		return txtFileRemark;
	}
	
	/**
	 * @return hidRecordId
	 */
	public Long getHidRecordId() {
		return hidRecordId;
	}
	
	/**
	 * @param hidRecordId セットする hidRecordId
	 */
	public void setHidRecordId(Long hidRecordId) {
		this.hidRecordId = hidRecordId;
	}
	
}
