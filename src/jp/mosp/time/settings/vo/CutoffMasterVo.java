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
 * 締日情報を格納する。
 */
public class CutoffMasterVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= 1555808244670160972L;
	
	private String				txtEditCutoffCode;
	private String				txtEditCutoffName;
	private String				txtEditCutoffAbbr;
	private String				pltEditCutoffDate;
	private String				pltEditNoApproval;
	
	/**
	 * 自己月締(編集)。
	 */
	private String				pltEditSelfTightening;
	
	private String				txtSearchCutoffCode;
	private String				txtSearchCutoffName;
	private String				txtSearchCutoffAbbr;
	private String				pltSearchCutoffDate;
	private String				pltSearchNoApproval;
	
	/**
	 * 自己月締(検索)。
	 */
	private String				pltSearchSelfTightening;
	
	private String[]			aryLblCutoffCode;
	private String[]			aryLblCutoffName;
	private String[]			aryLblCutoffAbbr;
	private String[]			aryLblCutoffDate;
	private String[]			aryLblNoApproval;
	
	/**
	 * 自己月締(一覧)。
	 */
	private String[]			aryLblSelfTightening;
	
	
	/**
	 * @return txtEditCutoffCode
	 */
	public String getTxtEditCutoffCode() {
		return txtEditCutoffCode;
	}
	
	/**
	 * @param txtEditCutoffCode セットする txtEditCutoffCode
	 */
	public void setTxtEditCutoffCode(String txtEditCutoffCode) {
		this.txtEditCutoffCode = txtEditCutoffCode;
	}
	
	/**
	 * @return txtEditCutoffName
	 */
	public String getTxtEditCutoffName() {
		return txtEditCutoffName;
	}
	
	/**
	 * @param txtEditCutoffName セットする txtEditCutoffName
	 */
	public void setTxtEditCutoffName(String txtEditCutoffName) {
		this.txtEditCutoffName = txtEditCutoffName;
	}
	
	/**
	 * @return txtEditCutoffAbbr
	 */
	public String getTxtEditCutoffAbbr() {
		return txtEditCutoffAbbr;
	}
	
	/**
	 * @param txtEditCutoffAbbr セットする txtEditCutoffAbbr
	 */
	public void setTxtEditCutoffAbbr(String txtEditCutoffAbbr) {
		this.txtEditCutoffAbbr = txtEditCutoffAbbr;
	}
	
	/**
	 * @return pltEditCutoffDate
	 */
	public String getPltEditCutoffDate() {
		return pltEditCutoffDate;
	}
	
	/**
	 * @param pltEditCutoffDate セットする pltEditCutoffDate
	 */
	public void setPltEditCutoffDate(String pltEditCutoffDate) {
		this.pltEditCutoffDate = pltEditCutoffDate;
	}
	
	/**
	 * @return txtSearchCutoffCode
	 */
	public String getTxtSearchCutoffCode() {
		return txtSearchCutoffCode;
	}
	
	/**
	 * @param txtSearchCutoffCode セットする txtSearchCutoffCode
	 */
	public void setTxtSearchCutoffCode(String txtSearchCutoffCode) {
		this.txtSearchCutoffCode = txtSearchCutoffCode;
	}
	
	/**
	 * @return txtSearchCutoffName
	 */
	public String getTxtSearchCutoffName() {
		return txtSearchCutoffName;
	}
	
	/**
	 * @param txtSearchCutoffName セットする txtSearchCutoffName
	 */
	public void setTxtSearchCutoffName(String txtSearchCutoffName) {
		this.txtSearchCutoffName = txtSearchCutoffName;
	}
	
	/**
	 * @return txtSearchCutoffAbbr
	 */
	public String getTxtSearchCutoffAbbr() {
		return txtSearchCutoffAbbr;
	}
	
	/**
	 * @param txtSearchCutoffAbbr セットする txtSearchCutoffAbbr
	 */
	public void setTxtSearchCutoffAbbr(String txtSearchCutoffAbbr) {
		this.txtSearchCutoffAbbr = txtSearchCutoffAbbr;
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
	 * @return aryLblCutoffCode
	 */
	public String[] getAryLblCutoffCode() {
		return getStringArrayClone(aryLblCutoffCode);
	}
	
	/**
	 * @param aryLblCutoffCode セットする aryLblCutoffCode
	 */
	public void setAryLblCutoffCode(String[] aryLblCutoffCode) {
		this.aryLblCutoffCode = getStringArrayClone(aryLblCutoffCode);
	}
	
	/**
	 * @return aryLblCutoffName
	 */
	public String[] getAryLblCutoffName() {
		return getStringArrayClone(aryLblCutoffName);
	}
	
	/**
	 * @param aryLblCutoffName セットする aryLblCutoffName
	 */
	public void setAryLblCutoffName(String[] aryLblCutoffName) {
		this.aryLblCutoffName = getStringArrayClone(aryLblCutoffName);
	}
	
	/**
	 * @return aryLblCutoffAbbr
	 */
	public String[] getAryLblCutoffAbbr() {
		return getStringArrayClone(aryLblCutoffAbbr);
	}
	
	/**
	 * @param aryLblCutoffAbbr セットする aryLblCutoffAbbr
	 */
	public void setAryLblCutoffAbbr(String[] aryLblCutoffAbbr) {
		this.aryLblCutoffAbbr = getStringArrayClone(aryLblCutoffAbbr);
	}
	
	/**
	 * @return aryLblCutoffDate
	 */
	public String[] getAryLblCutoffDate() {
		return getStringArrayClone(aryLblCutoffDate);
	}
	
	/**
	 * @param aryLblCutoffDate セットする aryLblCutoffDate
	 */
	public void setAryLblCutoffDate(String[] aryLblCutoffDate) {
		this.aryLblCutoffDate = getStringArrayClone(aryLblCutoffDate);
	}
	
	/**
	 * @return pltEditNoApproval
	 */
	public String getPltEditNoApproval() {
		return pltEditNoApproval;
	}
	
	/**
	 * @param pltEditNoApproval セットする pltEditNoApproval
	 */
	public void setPltEditNoApproval(String pltEditNoApproval) {
		this.pltEditNoApproval = pltEditNoApproval;
	}
	
	/**
	 * @return aryLblNoApproval
	 */
	public String[] getAryLblNoApproval() {
		return getStringArrayClone(aryLblNoApproval);
	}
	
	/**
	 * @param aryLblNoApproval セットする aryLblNoApproval
	 */
	public void setAryLblNoApproval(String[] aryLblNoApproval) {
		this.aryLblNoApproval = getStringArrayClone(aryLblNoApproval);
	}
	
	/**
	 * @return pltSearchNoApproval
	 */
	public String getPltSearchNoApproval() {
		return pltSearchNoApproval;
	}
	
	/**
	 * @param pltSearchNoApproval セットする pltSearchNoApproval
	 */
	public void setPltSearchNoApproval(String pltSearchNoApproval) {
		this.pltSearchNoApproval = pltSearchNoApproval;
	}
	
	/**
	 * @return pltEditSelfTightening
	 */
	public String getPltEditSelfTightening() {
		return pltEditSelfTightening;
	}
	
	/**
	 * @param pltEditSelfTightening セットする pltEditSelfTightening
	 */
	public void setPltEditSelfTightening(String pltEditSelfTightening) {
		this.pltEditSelfTightening = pltEditSelfTightening;
	}
	
	/**
	 * @return pltSearchSelfTightening
	 */
	public String getPltSearchSelfTightening() {
		return pltSearchSelfTightening;
	}
	
	/**
	 * @param pltSearchSelfTightening セットする pltSearchSelfTightening
	 */
	public void setPltSearchSelfTightening(String pltSearchSelfTightening) {
		this.pltSearchSelfTightening = pltSearchSelfTightening;
	}
	
	/**
	 * @return aryLblSelfTightening
	 */
	public String[] getAryLblSelfTightening() {
		return getStringArrayClone(aryLblSelfTightening);
	}
	
	/**
	 * @param aryLblSelfTightening セットする aryLblSelfTightening
	 */
	public void setAryLblSelfTightening(String[] aryLblSelfTightening) {
		this.aryLblSelfTightening = getStringArrayClone(aryLblSelfTightening);
	}
	
}
