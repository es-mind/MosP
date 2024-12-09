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
package jp.mosp.platform.file.vo;

import jp.mosp.platform.file.base.ExportListVo;

/**
 * 人事エクスポート一覧の情報を格納する。<br>
 */
public class HumanExportListVo extends ExportListVo {
	
	private static final long	serialVersionUID	= -2408392651438058654L;
	
	/**
	 * 有効日(年)。
	 */
	private String				txtActivateYear;
	
	/**
	 * 有効日(月)。
	 */
	private String				txtActivateMonth;
	
	/**
	 * 有効日(日)。
	 */
	private String				txtActivateDay;
	
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
	 * @return txtActivateYear
	 */
	public String getTxtActivateYear() {
		return txtActivateYear;
	}
	
	/**
	 * @param txtActivateYear セットする txtActivateYear
	 */
	public void setTxtActivateYear(String txtActivateYear) {
		this.txtActivateYear = txtActivateYear;
	}
	
	/**
	 * @return txtActivateMonth
	 */
	public String getTxtActivateMonth() {
		return txtActivateMonth;
	}
	
	/**
	 * @param txtActivateMonth セットする txtActivateMonth
	 */
	public void setTxtActivateMonth(String txtActivateMonth) {
		this.txtActivateMonth = txtActivateMonth;
	}
	
	/**
	 * @return txtActivateDay
	 */
	public String getTxtActivateDay() {
		return txtActivateDay;
	}
	
	/**
	 * @param txtActivateDay セットする txtActivateDay
	 */
	public void setTxtActivateDay(String txtActivateDay) {
		this.txtActivateDay = txtActivateDay;
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
	
}
