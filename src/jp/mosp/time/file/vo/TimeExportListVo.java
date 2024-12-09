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
package jp.mosp.time.file.vo;

import jp.mosp.platform.file.base.ExportListVo;

/**
 * 勤怠エクスポート一覧の情報を格納する。
 */
public class TimeExportListVo extends ExportListVo {
	
	private static final long	serialVersionUID	= -3783832847050920990L;
	
	/**
	 * 出力期間開始年。
	 */
	private String				txtStartYear;
	
	/**
	 * 出力期間開始月。
	 */
	private String				txtStartMonth;
	
	/**
	 * 出力期間終了年。
	 */
	private String				txtEndYear;
	
	/**
	 * 出力期間終了月。
	 */
	private String				txtEndMonth;
	
	/**
	 * 勤務地。
	 */
	private String				pltWorkPlace;
	
	/**
	 * 雇用契約。
	 */
	private String				pltEmployment;
	
	/**
	 * 所属。
	 */
	private String				pltSection;
	
	/**
	 * 職位。
	 */
	private String				pltPosition;
	
	/**
	 * 締日。
	 */
	private String				pltCutoff;
	
	/**
	 * 勤務地プルダウン。
	 */
	private String[][]			aryPltWorkPlace;
	
	/**
	 * 雇用契約プルダウン。
	 */
	private String[][]			aryPltEmployment;
	
	/**
	 * 所属プルダウン。
	 */
	private String[][]			aryPltSection;
	
	/**
	 * 職位プルダウン。
	 */
	private String[][]			aryPltPosition;
	
	/**
	 * 締日プルダウン。
	 */
	private String[][]			aryPltCutoff;
	
	/**
	 * 下位所属含むチェックボックス。
	 */
	private String				ckbNeedLowerSection;
	
	
	/**
	 * @return txtStartYear
	 */
	public String getTxtStartYear() {
		return txtStartYear;
	}
	
	/**
	 * @param txtStartYear セットする txtStartYear
	 */
	public void setTxtStartYear(String txtStartYear) {
		this.txtStartYear = txtStartYear;
	}
	
	/**
	 * @return txtStartMonth
	 */
	public String getTxtStartMonth() {
		return txtStartMonth;
	}
	
	/**
	 * @param txtStartMonth セットする txtStartMonth
	 */
	public void setTxtStartMonth(String txtStartMonth) {
		this.txtStartMonth = txtStartMonth;
	}
	
	/**
	 * @return txtEndYear
	 */
	public String getTxtEndYear() {
		return txtEndYear;
	}
	
	/**
	 * @param txtEndYear セットする txtEndYear
	 */
	public void setTxtEndYear(String txtEndYear) {
		this.txtEndYear = txtEndYear;
	}
	
	/**
	 * @return txtEndMonth
	 */
	public String getTxtEndMonth() {
		return txtEndMonth;
	}
	
	/**
	 * @param txtEndMonth セットする txtEndMonth
	 */
	public void setTxtEndMonth(String txtEndMonth) {
		this.txtEndMonth = txtEndMonth;
	}
	
	/**
	 * @return pltWorkPlace
	 */
	public String getPltWorkPlace() {
		return pltWorkPlace;
	}
	
	/**
	 * @param pltWorkPlace セットする pltWorkPlace
	 */
	public void setPltWorkPlace(String pltWorkPlace) {
		this.pltWorkPlace = pltWorkPlace;
	}
	
	/**
	 * @return pltEmployment
	 */
	public String getPltEmployment() {
		return pltEmployment;
	}
	
	/**
	 * @param pltEmployment セットする pltEmployment
	 */
	public void setPltEmployment(String pltEmployment) {
		this.pltEmployment = pltEmployment;
	}
	
	/**
	 * @return pltSection
	 */
	public String getPltSection() {
		return pltSection;
	}
	
	/**
	 * @param pltSection セットする pltSection
	 */
	public void setPltSection(String pltSection) {
		this.pltSection = pltSection;
	}
	
	/**
	 * @return pltPosition
	 */
	public String getPltPosition() {
		return pltPosition;
	}
	
	/**
	 * @param pltPosition セットする pltPosition
	 */
	public void setPltPosition(String pltPosition) {
		this.pltPosition = pltPosition;
	}
	
	/**
	 * @return pltCutoff
	 */
	public String getPltCutoff() {
		return pltCutoff;
	}
	
	/**
	 * @param pltCutoff セットする pltCutoff
	 */
	public void setPltCutoff(String pltCutoff) {
		this.pltCutoff = pltCutoff;
	}
	
	/**
	 * @return aryPltWorkPlace
	 */
	public String[][] getAryPltWorkPlace() {
		return getStringArrayClone(aryPltWorkPlace);
	}
	
	/**
	 * @param aryPltWorkPlace セットする aryPltWorkPlace
	 */
	public void setAryPltWorkPlace(String[][] aryPltWorkPlace) {
		this.aryPltWorkPlace = getStringArrayClone(aryPltWorkPlace);
	}
	
	/**
	 * @return aryPltEmployment
	 */
	public String[][] getAryPltEmployment() {
		return getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @param aryPltEmployment セットする aryPltEmployment
	 */
	public void setAryPltEmployment(String[][] aryPltEmployment) {
		this.aryPltEmployment = getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @return aryPltSection
	 */
	public String[][] getAryPltSection() {
		return getStringArrayClone(aryPltSection);
	}
	
	/**
	 * @param aryPltSection セットする aryPltSection
	 */
	public void setAryPltSection(String[][] aryPltSection) {
		this.aryPltSection = getStringArrayClone(aryPltSection);
	}
	
	/**
	 * @return aryPltPosition
	 */
	public String[][] getAryPltPosition() {
		return getStringArrayClone(aryPltPosition);
	}
	
	/**
	 * @param aryPltPosition セットする aryPltPosition
	 */
	public void setAryPltPosition(String[][] aryPltPosition) {
		this.aryPltPosition = getStringArrayClone(aryPltPosition);
	}
	
	/**
	 * @return aryPltCutoff
	 */
	public String[][] getAryPltCutoff() {
		return getStringArrayClone(aryPltCutoff);
	}
	
	/**
	 * @param aryPltCutoff セットする aryPltCutoff
	 */
	public void setAryPltCutoff(String[][] aryPltCutoff) {
		this.aryPltCutoff = getStringArrayClone(aryPltCutoff);
	}
	
	/**
	 * @return ckbNeedLowerSection
	 */
	public String getCkbNeedLowerSection() {
		return ckbNeedLowerSection;
	}
	
	/**
	 * @param ckbNeedLowerSection セットする ckbNeedLowerSection
	 */
	public void setCkbNeedLowerSection(String ckbNeedLowerSection) {
		this.ckbNeedLowerSection = ckbNeedLowerSection;
	}
	
}
