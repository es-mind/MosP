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
 * 職位マスタ画面の情報を格納する。
 */
public class PositionMasterVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= 9161302234958581089L;
	
	private String				txtEditPositionCode;
	private String				txtEditPositionName;
	private String				txtEditPositionAbbr;
	private String				txtEditPositionGrade;
	private String				txtEditPositionLevel;
	
	private String				txtSearchPositionCode;
	private String				txtSearchPositionName;
	private String				txtSearchPositionAbbr;
	private String				txtSearchPositionGrade;
	private String				txtSearchPositionLevel;
	
	private String[]			aryLblActivateDate;
	private String[]			aryLblPositionCode;
	private String[]			aryLblPositionName;
	private String[]			aryLblPositionAbbr;
	private String[]			aryLblGrade;
	private String[]			aryLblLevel;
	private String[]			aryLblInactivate;
	
	
	/**
	 * @param txtEditPositionCode セットする txtEditPositionCode
	 */
	public void setTxtEditPositionCode(String txtEditPositionCode) {
		this.txtEditPositionCode = txtEditPositionCode;
	}
	
	/**
	 * @return txtEditPositionCode
	 */
	public String getTxtEditPositionCode() {
		return txtEditPositionCode;
	}
	
	/**
	 * @param txtEditPositionName セットする txtEditPositionName
	 */
	public void setTxtEditPositionName(String txtEditPositionName) {
		this.txtEditPositionName = txtEditPositionName;
	}
	
	/**
	 * @return txtEditPositionNamer
	 */
	public String getTxtEditPositionName() {
		return txtEditPositionName;
	}
	
	/**
	 * @param txtEditPositionAbbr セットする txtEditPositionAbbr
	 */
	public void setTxtEditPositionAbbr(String txtEditPositionAbbr) {
		this.txtEditPositionAbbr = txtEditPositionAbbr;
	}
	
	/**
	 * @return txtEditPositionAbbr
	 */
	public String getTxtEditPositionAbbr() {
		return txtEditPositionAbbr;
	}
	
	/**
	 * @param txtEditPositionGrade セットする txtEditPositionGrade
	 */
	public void setTxtEditPositionGrade(String txtEditPositionGrade) {
		this.txtEditPositionGrade = txtEditPositionGrade;
	}
	
	/**
	 * @return txtEditPositionGrade
	 */
	public String getTxtEditPositionGrade() {
		return txtEditPositionGrade;
	}
	
	/**
	 * @param txtEditPositionLevel セットする txtEditPositionLevel
	 */
	public void setTxtEditPositionLevel(String txtEditPositionLevel) {
		this.txtEditPositionLevel = txtEditPositionLevel;
	}
	
	/**
	 * @return txtEditPositionLevel
	 */
	public String getTxtEditPositionLevel() {
		return txtEditPositionLevel;
	}
	
	/**
	 * @param txtSearchPositionCode セットする txtSearchPositionCode
	 */
	public void setTxtSearchPositionCode(String txtSearchPositionCode) {
		this.txtSearchPositionCode = txtSearchPositionCode;
	}
	
	/**
	 * @return txtSearchPositionCode
	 */
	public String getTxtSearchPositionCode() {
		return txtSearchPositionCode;
	}
	
	/**
	 * @param txtSearchPositionName セットする txtSearchPositionName
	 */
	public void setTxtSearchPositionName(String txtSearchPositionName) {
		this.txtSearchPositionName = txtSearchPositionName;
	}
	
	/**
	 * @return txtSearchPositionName
	 */
	public String getTxtSearchPositionName() {
		return txtSearchPositionName;
	}
	
	/**
	 * @param txtSearchPositionAbbr セットする txtSearchPositionAbbr
	 */
	public void setTxtSearchPositionAbbr(String txtSearchPositionAbbr) {
		this.txtSearchPositionAbbr = txtSearchPositionAbbr;
	}
	
	/**
	 * @return txtSearchPositionAbbr
	 */
	public String getTxtSearchPositionAbbr() {
		return txtSearchPositionAbbr;
	}
	
	/**
	 * @param txtSearchPositionGrade セットする txtSearchPositionGrade
	 */
	public void setTxtSearchPositionGrade(String txtSearchPositionGrade) {
		this.txtSearchPositionGrade = txtSearchPositionGrade;
	}
	
	/**
	 * @return txtSearchPositionGrade
	 */
	public String getTxtSearchPositionGrade() {
		return txtSearchPositionGrade;
	}
	
	/**
	 * @param txtSearchPositionLevel セットする txtSearchPositionLevel
	 */
	public void setTxtSearchPositionLevel(String txtSearchPositionLevel) {
		this.txtSearchPositionLevel = txtSearchPositionLevel;
	}
	
	/**
	 * @return txtSearchPositionLevel
	 */
	public String getTxtSearchPositionLevel() {
		return txtSearchPositionLevel;
	}
	
	/**
	 * @param aryLblActivateDate セットする aryLblActivateDate
	 */
	@Override
	public void setAryLblActivateDate(String[] aryLblActivateDate) {
		this.aryLblActivateDate = getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @return aryLblActivateDate
	 */
	@Override
	public String[] getAryLblActivateDate() {
		return getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @param aryLblPositionCode セットする aryLblPositionCode
	 */
	public void setAryLblPositionCode(String[] aryLblPositionCode) {
		this.aryLblPositionCode = getStringArrayClone(aryLblPositionCode);
	}
	
	/**
	 * @return aryLblPositionCode
	 */
	public String[] getAryLblPositionCode() {
		return getStringArrayClone(aryLblPositionCode);
	}
	
	/**
	 * @param aryLblPositionName セットする aryLblPositionName
	 */
	public void setAryLblPositionName(String[] aryLblPositionName) {
		this.aryLblPositionName = getStringArrayClone(aryLblPositionName);
	}
	
	/**
	 * @return aryLblPositionName
	 */
	public String[] getAryLblPositionName() {
		return getStringArrayClone(aryLblPositionName);
	}
	
	/**
	 * @param aryLblPositionAbbr セットする aryLblPositionAbbr
	 */
	public void setAryLblPositionAbbr(String[] aryLblPositionAbbr) {
		this.aryLblPositionAbbr = getStringArrayClone(aryLblPositionAbbr);
	}
	
	/**
	 * @return aryLblPositionAbbr
	 */
	public String[] getAryLblPositionAbbr() {
		return getStringArrayClone(aryLblPositionAbbr);
	}
	
	/**
	 * @param aryLblGrade セットする aryLblGrade
	 */
	public void setAryLblGrade(String[] aryLblGrade) {
		this.aryLblGrade = getStringArrayClone(aryLblGrade);
	}
	
	/**
	 * @return aryLblGrade
	 */
	public String[] getAryLblGrade() {
		return getStringArrayClone(aryLblGrade);
	}
	
	/**
	 * @param aryLblLevel セットする aryLblLevel
	 */
	public void setAryLblLevel(String[] aryLblLevel) {
		this.aryLblLevel = getStringArrayClone(aryLblLevel);
	}
	
	/**
	 * @return aryLblLevel
	 */
	public String[] getAryLblLevel() {
		return getStringArrayClone(aryLblLevel);
	}
	
	/**
	 * @param aryLblInactivate セットする aryLblInactivate
	 */
	@Override
	public void setAryLblInactivate(String[] aryLblInactivate) {
		this.aryLblInactivate = getStringArrayClone(aryLblInactivate);
	}
	
	/**
	 * @return aryLblInactivate
	 */
	@Override
	public String[] getAryLblInactivate() {
		return getStringArrayClone(aryLblInactivate);
	}
	
	/**
	 * ソートマーク出力。<br>
	 * @param key ソートキー
	 * @return HTMLソートマークの文字列
	 */
	public String getSortMark(String key) {
		String sortMark = "";
		if (key.equals(getComparatorName())) {
			if (isAscending()) {
				sortMark = "▲";
			} else {
				sortMark = "▼";
			}
		}
		return sortMark;
	}
}
