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
package jp.mosp.platform.system.vo;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * 雇用契約マスタ画面の情報を格納する。
 */
public class EmploymentMasterVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= -3908698848630150189L;
	
	private String				txtEditEmploymentCode;
	private String				txtEditEmploymentName;
	private String				txtEditEmploymentAbbr;
	
	private String				txtSearchEmploymentCode;
	private String				txtSearchEmploymentName;
	private String				txtSearchEmploymentAbbr;
	
	private String[]			aryLblEmploymentCode;
	private String[]			aryLblEmploymentName;
	private String[]			aryLblEmploymentAbbr;
	
	
	/**
	 * @param txtEditEmploymentCode セットする txtEditEmploymentCode
	 */
	public void setTxtEditEmploymentCode(String txtEditEmploymentCode) {
		this.txtEditEmploymentCode = txtEditEmploymentCode;
	}
	
	/**
	 * @return txtEditEmploymentCode
	 */
	public String getTxtEditEmploymentCode() {
		return txtEditEmploymentCode;
	}
	
	/**
	 * @param txtEditEmploymentName セットする txtEditEmploymentName
	 */
	public void setTxtEditEmploymentName(String txtEditEmploymentName) {
		this.txtEditEmploymentName = txtEditEmploymentName;
	}
	
	/**
	 * @return txtEditEmploymentName
	 */
	public String getTxtEditEmploymentName() {
		return txtEditEmploymentName;
	}
	
	/**
	 * @param txtEditEmploymentAbbr セットする txtEditEmploymentAbbr
	 */
	public void setTxtEditEmploymentAbbr(String txtEditEmploymentAbbr) {
		this.txtEditEmploymentAbbr = txtEditEmploymentAbbr;
	}
	
	/**
	 * @return txtEditEmploymentAbbr
	 */
	public String getTxtEditEmploymentAbbr() {
		return txtEditEmploymentAbbr;
	}
	
	/**
	 * @param txtSearchEmploymentCode セットする txtSearchEmploymentCode
	 */
	public void setTxtSearchEmploymentCode(String txtSearchEmploymentCode) {
		this.txtSearchEmploymentCode = txtSearchEmploymentCode;
	}
	
	/**
	 * @return txtSearchEmploymentCode
	 */
	public String getTxtSearchEmploymentCode() {
		return txtSearchEmploymentCode;
	}
	
	/**
	 * @param txtSearchEmploymentName セットする txtSearchEmploymentName
	 */
	public void setTxtSearchEmploymentName(String txtSearchEmploymentName) {
		this.txtSearchEmploymentName = txtSearchEmploymentName;
	}
	
	/**
	 * @return txtSearchEmploymentName
	 */
	public String getTxtSearchEmploymentName() {
		return txtSearchEmploymentName;
	}
	
	/**
	 * @param txtSearchEmploymentAbbr セットする txtSearchEmploymentAbb
	 */
	public void setTxtSearchEmploymentAbbr(String txtSearchEmploymentAbbr) {
		this.txtSearchEmploymentAbbr = txtSearchEmploymentAbbr;
	}
	
	/**
	 * @return txtSearchEmploymentAbb
	 */
	public String getTxtSearchEmploymentAbbr() {
		return txtSearchEmploymentAbbr;
	}
	
	/**
	 * @return aryLblEmploymentCode
	 */
	public String[] getAryLblEmploymentCode() {
		return getStringArrayClone(aryLblEmploymentCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEmploymentCode
	 */
	public String getAryLblEmploymentCode(int idx) {
		return aryLblEmploymentCode[idx];
	}
	
	/**
	 * @param aryLblEmploymentCode セットする aryLblEmploymentCode
	 */
	public void setAryLblEmploymentCode(String[] aryLblEmploymentCode) {
		this.aryLblEmploymentCode = getStringArrayClone(aryLblEmploymentCode);
	}
	
	/**
	 * @param aryLblEmploymentName セットする aryLblEmploymentName
	 */
	public void setAryLblEmploymentName(String[] aryLblEmploymentName) {
		this.aryLblEmploymentName = getStringArrayClone(aryLblEmploymentName);
	}
	
	/**
	 * @return aryLblEmploymentName
	 */
	public String[] getAryLblEmploymentName() {
		return getStringArrayClone(aryLblEmploymentName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEmploymentName
	 */
	public String getAryLblEmploymentName(int idx) {
		return aryLblEmploymentName[idx];
	}
	
	/**
	 * @param aryLblEmploymentAbbr セットする aryLblEmploymentAbbr
	 */
	public void setAryLblEmploymentAbbr(String[] aryLblEmploymentAbbr) {
		this.aryLblEmploymentAbbr = getStringArrayClone(aryLblEmploymentAbbr);
	}
	
	/**
	 * @return aryLblEmploymentAbbr
	 */
	public String[] getAryLblEmploymentAbbr() {
		return getStringArrayClone(aryLblEmploymentAbbr);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEmploymentAbbr
	 */
	public String getAryLblEmploymentAbbr(int idx) {
		return aryLblEmploymentAbbr[idx];
	}
	
}
