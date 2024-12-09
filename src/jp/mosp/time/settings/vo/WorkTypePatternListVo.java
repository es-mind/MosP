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
package jp.mosp.time.settings.vo;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 勤務形態パターン一覧の情報を格納する。
 */
public class WorkTypePatternListVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= -8615872868381345964L;
	
	private String				txtSearchPatternCode;
	private String				txtSearchPatternName;
	private String				txtSearchPatternAbbr;
	
	private String[]			aryLblActivateMonth;
	private String[]			aryLblPatternCode;
	private String[]			aryLblPatternName;
	private String[]			aryLblPatternAbbr;
	
	
	/**
	 * @return aryLblActivateMonth
	 */
	public String[] getAryLblActivateMonth() {
		return getStringArrayClone(aryLblActivateMonth);
	}
	
	/**
	 * @param aryLblActivateMonth セットする aryLblActivateMonth
	 */
	public void setAryLblActivateMonth(String[] aryLblActivateMonth) {
		this.aryLblActivateMonth = getStringArrayClone(aryLblActivateMonth);
	}
	
	/**
	 * @return txtSearchPatternCode
	 */
	public String getTxtSearchPatternCode() {
		return txtSearchPatternCode;
	}
	
	/**
	 * @param txtSearchPatternCode セットする txtSearchPatternCode
	 */
	public void setTxtSearchPatternCode(String txtSearchPatternCode) {
		this.txtSearchPatternCode = txtSearchPatternCode;
	}
	
	/**
	 * @return txtSearchPatternName
	 */
	public String getTxtSearchPatternName() {
		return txtSearchPatternName;
	}
	
	/**
	 * @param txtSearchPatternName セットする txtSearchPatternName
	 */
	public void setTxtSearchPatternName(String txtSearchPatternName) {
		this.txtSearchPatternName = txtSearchPatternName;
	}
	
	/**
	 * @return txtSearchPatternAbbr
	 */
	public String getTxtSearchPatternAbbr() {
		return txtSearchPatternAbbr;
	}
	
	/**
	 * @param txtSearchPatternAbbr セットする txtSearchPatternAbbr
	 */
	public void setTxtSearchPatternAbbr(String txtSearchPatternAbbr) {
		this.txtSearchPatternAbbr = txtSearchPatternAbbr;
	}
	
	/**
	 * @return aryLblPatternCode
	 */
	public String[] getAryLblPatternCode() {
		return getStringArrayClone(aryLblPatternCode);
	}
	
	/**
	 * @param aryLblPatternCode セットする aryLblPatternCode
	 */
	public void setAryLblPatternCode(String[] aryLblPatternCode) {
		this.aryLblPatternCode = getStringArrayClone(aryLblPatternCode);
	}
	
	/**
	 * @return aryLblPatternName
	 */
	public String[] getAryLblPatternName() {
		return getStringArrayClone(aryLblPatternName);
	}
	
	/**
	 * @param aryLblPatternName セットする aryLblPatternName
	 */
	public void setAryLblPatternName(String[] aryLblPatternName) {
		this.aryLblPatternName = getStringArrayClone(aryLblPatternName);
	}
	
	/**
	 * @return aryLblPatternAbbr
	 */
	public String[] getAryLblPatternAbbr() {
		return getStringArrayClone(aryLblPatternAbbr);
	}
	
	/**
	 * @param aryLblPatternAbbr セットする aryLblPatternAbbr
	 */
	public void setAryLblPatternAbbr(String[] aryLblPatternAbbr) {
		this.aryLblPatternAbbr = getStringArrayClone(aryLblPatternAbbr);
	}
	
}
