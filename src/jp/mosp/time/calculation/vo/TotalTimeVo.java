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
package jp.mosp.time.calculation.vo;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 勤怠集計管理の情報を格納する。
 */
public class TotalTimeVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= -2758120486437367858L;
	
	private String				pltEditRequestYear;
	private String				pltEditRequestMonth;
	private String				pltEditCutoffDate;
	private String				txtEditCutoffCode;
	private String				txtEditCutoffName;
	private String				pltEditCutoffState;
	private String[]			aryLblCutoffCode;
	private String[]			aryLblCutoffName;
	private String[]			aryLblCutoffDate;
	private String[]			aryLblCutoffState;
	
	private String				txtLblRequestYear;
	private String				txtLblRequestMonth;
	
	private String[]			aryCkbTotalTimeListId;
	
	private String[][]			aryPltEditRequestYear;
	private String[][]			aryPltEditRequestMonth;
	
	private String				totalTimeRequestYear;
	private String				totalTimeRequestMonth;
	
	/**
	 * 締状態配列。
	 */
	private int[]				aryCutoffState;
	
	
	/**
	 * @return pltEditRequestYear
	 */
	public String getPltEditRequestYear() {
		return pltEditRequestYear;
	}
	
	/**
	 * @return pltEditRequestMonth
	 */
	public String getPltEditRequestMonth() {
		return pltEditRequestMonth;
	}
	
	/**
	 * @return pltEditCutoffDate
	 */
	public String getPltEditCutoffDate() {
		return pltEditCutoffDate;
	}
	
	/**
	 * @return txtEditCutoffCode
	 */
	public String getTxtEditCutoffCode() {
		return txtEditCutoffCode;
	}
	
	/**
	 * @return txtEditCutoffName
	 */
	public String getTxtEditCutoffName() {
		return txtEditCutoffName;
	}
	
	/**
	 * @return pltEditCutoffState
	 */
	public String getPltEditCutoffState() {
		return pltEditCutoffState;
	}
	
	/**
	 * @return aryLblCutoffCode
	 */
	public String[] getAryLblCutoffCode() {
		return getStringArrayClone(aryLblCutoffCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblCutoffCode
	 */
	public String getAryLblCutoffCode(int idx) {
		return aryLblCutoffCode[idx];
	}
	
	/**
	 * @return aryLblCutoffName
	 */
	public String[] getAryLblCutoffName() {
		return getStringArrayClone(aryLblCutoffName);
	}
	
	/**
	 * @param idx インデックス
	 * @return getAryLblCutoffName
	 */
	public String getAryLblCutoffName(int idx) {
		return aryLblCutoffName[idx];
	}
	
	/**
	 * @return aryLblCutoffDate
	 */
	public String[] getAryLblCutoffDate() {
		return getStringArrayClone(aryLblCutoffDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblCutoffDate
	 */
	public String getAryLblCutoffDate(int idx) {
		return aryLblCutoffDate[idx];
	}
	
	/**
	 * @return aryLblCutoffState
	 */
	public String[] getAryLblCutoffState() {
		return getStringArrayClone(aryLblCutoffState);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblCutoffState
	 */
	public String getAryLblCutoffState(int idx) {
		return aryLblCutoffState[idx];
	}
	
	/**
	 * @return txtLblRequestYear
	 */
	public String getTxtLblRequestYear() {
		return txtLblRequestYear;
	}
	
	/**
	 * @return txtLblRequestMonth
	 */
	public String getTxtLblRequestMonth() {
		return txtLblRequestMonth;
	}
	
	/**
	 * @return aryCkbTotalTimeListId
	 */
	public String[] getAryCkbTotalTimeListId() {
		return getStringArrayClone(aryCkbTotalTimeListId);
	}
	
	/**
	 * @param idx インデックス
	 * @return ckbSelect
	 */
	public String getAryCkbTotalTimeListId(int idx) {
		return aryCkbTotalTimeListId[idx];
	}
	
	/**
	 * @return aryPltEditRequestYear
	 */
	public String[][] getAryPltEditRequestYear() {
		return getStringArrayClone(aryPltEditRequestYear);
	}
	
	/**
	 * @return aryPltEditRequestMonth
	 */
	public String[][] getAryPltEditRequestMonth() {
		return getStringArrayClone(aryPltEditRequestMonth);
	}
	
	/**
	 * @param pltEditRequestYear セットする pltEditRequestYear
	 */
	public void setPltEditRequestYear(String pltEditRequestYear) {
		this.pltEditRequestYear = pltEditRequestYear;
	}
	
	/**
	 * @param pltEditRequestMonth セットする pltEditRequestMonth
	 */
	public void setPltEditRequestMonth(String pltEditRequestMonth) {
		this.pltEditRequestMonth = pltEditRequestMonth;
	}
	
	/**
	 * @param pltEditCutoffDate セットする pltEditCutoffDate
	 */
	public void setPltEditCutoffDate(String pltEditCutoffDate) {
		this.pltEditCutoffDate = pltEditCutoffDate;
	}
	
	/**
	 * @param txtEditCutoffCode セットする txtEditCutoffCode
	 */
	public void setTxtEditCutoffCode(String txtEditCutoffCode) {
		this.txtEditCutoffCode = txtEditCutoffCode;
	}
	
	/**
	 * @param txtEditCutoffName セットする txtEditCutoffName
	 */
	public void setTxtEditCutoffName(String txtEditCutoffName) {
		this.txtEditCutoffName = txtEditCutoffName;
	}
	
	/**
	 * @param pltEditCutoffState セットする pltEditCutoffState
	 */
	public void setPltEditCutoffState(String pltEditCutoffState) {
		this.pltEditCutoffState = pltEditCutoffState;
	}
	
	/**
	 * @param aryLblCutoffCode セットする aryLblCutoffCode
	 */
	public void setAryLblCutoffCode(String[] aryLblCutoffCode) {
		this.aryLblCutoffCode = getStringArrayClone(aryLblCutoffCode);
	}
	
	/**
	 * @param aryLblCutoffName セットする aryLblCutoffName
	 */
	public void setAryLblCutoffName(String[] aryLblCutoffName) {
		this.aryLblCutoffName = getStringArrayClone(aryLblCutoffName);
	}
	
	/**
	 * @param aryLblCutoffDate セットする aryLblCutoffDate
	 */
	public void setAryLblCutoffDate(String[] aryLblCutoffDate) {
		this.aryLblCutoffDate = getStringArrayClone(aryLblCutoffDate);
	}
	
	/**
	 * @param aryLblCutoffState セットする aryLblCutoffState
	 */
	public void setAryLblCutoffState(String[] aryLblCutoffState) {
		this.aryLblCutoffState = getStringArrayClone(aryLblCutoffState);
	}
	
	/**
	 * @param txtLblRequestYear セットする txtLblRequestYear
	 */
	public void setTxtLblRequestYear(String txtLblRequestYear) {
		this.txtLblRequestYear = txtLblRequestYear;
	}
	
	/**
	 * @param txtLblRequestMonth セットする txtLblRequestMonth
	 */
	public void setTxtLblRequestMonth(String txtLblRequestMonth) {
		this.txtLblRequestMonth = txtLblRequestMonth;
	}
	
	/**
	 * @param aryCkbTotalTimeListId セットする aryCkbTotalTimeListId
	 */
	public void setAryCkbTotalTimeListId(String[] aryCkbTotalTimeListId) {
		this.aryCkbTotalTimeListId = getStringArrayClone(aryCkbTotalTimeListId);
	}
	
	/**
	 * @param aryPltEditRequestYear セットする aryPltEditRequestYear
	 */
	public void setAryPltEditRequestYear(String[][] aryPltEditRequestYear) {
		this.aryPltEditRequestYear = getStringArrayClone(aryPltEditRequestYear);
	}
	
	/**
	 * @param aryPltEditRequestMonth セットする aryPltEditRequestMonth
	 */
	public void setAryPltEditRequestMonth(String[][] aryPltEditRequestMonth) {
		this.aryPltEditRequestMonth = getStringArrayClone(aryPltEditRequestMonth);
	}
	
	/**
	 * @return totalTimeRequestYear
	 */
	public String getTotalTimeRequestYear() {
		return totalTimeRequestYear;
	}
	
	/**
	 * @return totalTimeRequestMonth
	 */
	public String getTotalTimeRequestMonth() {
		return totalTimeRequestMonth;
	}
	
	/**
	 * @param totalTimeRequestYear セットする totalTimeRequestYear
	 */
	public void setTotalTimeRequestYear(String totalTimeRequestYear) {
		this.totalTimeRequestYear = totalTimeRequestYear;
	}
	
	/**
	 * @param totalTimeRequestMonth セットする totalTimeRequestMonth
	 */
	public void setTotalTimeRequestMonth(String totalTimeRequestMonth) {
		this.totalTimeRequestMonth = totalTimeRequestMonth;
	}
	
	/**
	 * @param aryCutoffState セットする aryCutoffState
	 */
	public void setAryCutoffState(int[] aryCutoffState) {
		this.aryCutoffState = getIntArrayClone(aryCutoffState);
	}
	
	/**
	 * @return aryCutoffState
	 */
	public int[] getAryCutoffState() {
		return getIntArrayClone(aryCutoffState);
	}
	
	/**
	 * @param idx インデックス
	 * @return getAryCutoffState
	 */
	public int getAryCutoffState(int idx) {
		return aryCutoffState[idx];
	}
	
}
