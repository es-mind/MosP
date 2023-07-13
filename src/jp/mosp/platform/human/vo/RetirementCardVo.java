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
 * 退職情報詳細画面の情報を格納する。
 */
public class RetirementCardVo extends PlatformHumanVo {
	
	private static final long	serialVersionUID	= -3892085892595057827L;
	
	/**
	 * レコードID。<br>
	 * 編集対象のレコードIDを保持しておく。<br>
	 */
	private long				recordId;
	
	private String				txtRetirementYear;
	private String				txtRetirementMonth;
	private String				txtRetirementDay;
	private String				pltRetirementReason;
	private String				txtRetirementDetail;
	
	/**
	 * 退職理由プルダウンリスト。
	 */
	private String[][]			aryPltRetirementReason;
	
	
	/**
	 * @return recordId
	 */
	public long getRecordId() {
		return recordId;
	}
	
	/**
	 * @param recordId セットする recordId
	 */
	public void setRecordId(long recordId) {
		this.recordId = recordId;
	}
	
	/**
	 * @param txtRetirementYear セットする txtRetirementYear
	 */
	public void setTxtRetirementYear(String txtRetirementYear) {
		this.txtRetirementYear = txtRetirementYear;
	}
	
	/**
	 * @return txtRetirementYear
	 */
	public String getTxtRetirementYear() {
		return txtRetirementYear;
	}
	
	/**
	 * @param txtRetirementMonth セットする txtRetirementMonth
	 */
	public void setTxtRetirementMonth(String txtRetirementMonth) {
		this.txtRetirementMonth = txtRetirementMonth;
	}
	
	/**
	 * @return txtRetirementMonth
	 */
	public String getTxtRetirementMonth() {
		return txtRetirementMonth;
	}
	
	/**
	 * @param txtRetirementDay セットする txtRetirementDay
	 */
	public void setTxtRetirementDay(String txtRetirementDay) {
		this.txtRetirementDay = txtRetirementDay;
	}
	
	/**
	 * @return txtRetirementDay
	 */
	public String getTxtRetirementDay() {
		return txtRetirementDay;
	}
	
	/**
	 * @param pltRetirementReason セットする pltRetirementReason
	 */
	public void setPltRetirementReason(String pltRetirementReason) {
		this.pltRetirementReason = pltRetirementReason;
	}
	
	/**
	 * @return pltRetirementReason
	 */
	public String getPltRetirementReason() {
		return pltRetirementReason;
	}
	
	/**
	 * @param txtRetirementDetail セットする txtRetirementDetail
	 */
	public void setTxtRetirementDetail(String txtRetirementDetail) {
		this.txtRetirementDetail = txtRetirementDetail;
	}
	
	/**
	 * @return txtRetirementDetail
	 */
	public String getTxtRetirementDetail() {
		return txtRetirementDetail;
	}
	
	/**
	 * @param aryPltRetirementReason セットする aryPltRetirementReason
	 */
	public void setAryPltRetirementReason(String[][] aryPltRetirementReason) {
		this.aryPltRetirementReason = getStringArrayClone(aryPltRetirementReason);
	}
	
	/**
	 * @return aryPltRetirementReason
	 */
	public String[][] getAryPltRetirementReason() {
		return getStringArrayClone(aryPltRetirementReason);
	}
	
}
