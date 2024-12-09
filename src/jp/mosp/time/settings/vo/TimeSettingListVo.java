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
 * 勤怠設定一覧の情報を格納する。
 */
public class TimeSettingListVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= -8388985897089477812L;
	
	private String				txtSearchTimeSettingCode;
	private String				txtSearchTimeSettingName;
	private String				txtSearchTimeSettingAbbr;
	private String				pltSearchCutoffDate;
	
	private String[]			aryLblSettingCode;
	private String[]			aryLblSettingName;
	private String[]			aryLblSettingAbbr;
	private String[]			aryLblCutoff;
	
	private String[][]			aryPltSearchCutoffDate;
	
	
	/**
	 * @return txtSearchTimeSettingCode
	 */
	public String getTxtSearchTimeSettingCode() {
		return txtSearchTimeSettingCode;
	}
	
	/**
	 * @param txtSearchTimeSettingCode セットする txtSearchTimeSettingCode
	 */
	public void setTxtSearchTimeSettingCode(String txtSearchTimeSettingCode) {
		this.txtSearchTimeSettingCode = txtSearchTimeSettingCode;
	}
	
	/**
	 * @return txtSearchTimeSettingName
	 */
	public String getTxtSearchTimeSettingName() {
		return txtSearchTimeSettingName;
	}
	
	/**
	 * @param txtSearchTimeSettingName セットする txtSearchTimeSettingName
	 */
	public void setTxtSearchTimeSettingName(String txtSearchTimeSettingName) {
		this.txtSearchTimeSettingName = txtSearchTimeSettingName;
	}
	
	/**
	 * @return txtSearchTimeSettingAbbr
	 */
	public String getTxtSearchTimeSettingAbbr() {
		return txtSearchTimeSettingAbbr;
	}
	
	/**
	 * @param txtSearchTimeSettingAbbr セットする txtSearchTimeSettingAbbr
	 */
	public void setTxtSearchTimeSettingAbbr(String txtSearchTimeSettingAbbr) {
		this.txtSearchTimeSettingAbbr = txtSearchTimeSettingAbbr;
	}
	
	/**
	 * @return pltSearchCutoffDate
	 */
	public String getPltSearchCutoffDate() {
		return pltSearchCutoffDate;
	}
	
	/**
	 * @param pltSearchCutoffDate セットする pltSearchCutoffDate
	 */
	public void setPltSearchCutoffDate(String pltSearchCutoffDate) {
		this.pltSearchCutoffDate = pltSearchCutoffDate;
	}
	
	/**
	 * @return aryLblSettingCode
	 */
	public String[] getAryLblSettingCode() {
		return getStringArrayClone(aryLblSettingCode);
	}
	
	/**
	 * @param aryLblSettingCode セットする aryLblSettingCode
	 */
	public void setAryLblSettingCode(String[] aryLblSettingCode) {
		this.aryLblSettingCode = getStringArrayClone(aryLblSettingCode);
	}
	
	/**
	 * @return aryLblSettingName
	 */
	public String[] getAryLblSettingName() {
		return getStringArrayClone(aryLblSettingName);
	}
	
	/**
	 * @param aryLblSettingName セットする aryLblSettingName
	 */
	public void setAryLblSettingName(String[] aryLblSettingName) {
		this.aryLblSettingName = getStringArrayClone(aryLblSettingName);
	}
	
	/**
	 * @return aryLblSettingAbbr
	 */
	public String[] getAryLblSettingAbbr() {
		return getStringArrayClone(aryLblSettingAbbr);
	}
	
	/**
	 * @param aryLblSettingAbbr セットする aryLblSettingAbbr
	 */
	public void setAryLblSettingAbbr(String[] aryLblSettingAbbr) {
		this.aryLblSettingAbbr = getStringArrayClone(aryLblSettingAbbr);
	}
	
	/**
	 * @return aryLblCutoff
	 */
	public String[] getAryLblCutoff() {
		return getStringArrayClone(aryLblCutoff);
	}
	
	/**
	 * @param aryLblCutoff セットする aryLblCutoff
	 */
	public void setAryLblCutoff(String[] aryLblCutoff) {
		this.aryLblCutoff = getStringArrayClone(aryLblCutoff);
	}
	
	/**
	 * @return aryPltSearchCutoffDate
	 */
	public String[][] getAryPltSearchCutoffDate() {
		return getStringArrayClone(aryPltSearchCutoffDate);
	}
	
	/**
	 * @param aryPltSearchCutoffDate セットする aryPltSearchCutoffDate
	 */
	public void setAryPltSearchCutoffDate(String[][] aryPltSearchCutoffDate) {
		this.aryPltSearchCutoffDate = getStringArrayClone(aryPltSearchCutoffDate);
	}
	
}
