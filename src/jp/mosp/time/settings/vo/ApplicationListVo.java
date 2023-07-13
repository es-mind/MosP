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
package jp.mosp.time.settings.vo;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 設定適用管理一覧画面の情報を格納する。
 */
public class ApplicationListVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= -4070960776987444860L;
	
	private String				txtSearchApplicationCode;
	private String				txtSearchApplicationName;
	private String				txtSearchApplicationAbbr;
	private String				pltSearchWorkPlaceMaster;
	private String				pltSearchEmploymentMaster;
	private String				pltSearchSectionMaster;
	private String				pltSearchPositionMaster;
	private String				txtSearchEmployeeCode;
	private String				pltSearchWorkSetting;
	private String				pltSearchSchedule;
	private String				pltSearchPaidHoliday;
	
	private String[]			aryLblApplicationCode;
	private String[]			aryLblApplicationAbbr;
	private String[]			aryLblWorkPlace;
	private String[]			aryLblEmployment;
	private String[]			aryLblSection;
	private String[]			aryLblPosition;
	private String[]			aryLblEmployeeCode;
	private String[]			aryLblWorkSetting;
	private String[]			aryLblSchedule;
	private String[]			aryLblPaidHoliday;
	
	private String[][]			aryPltSearchWorkPlaceMaster;
	private String[][]			aryPltSearchEmploymentMaster;
	private String[][]			aryPltSearchSectionMaster;
	private String[][]			aryPltSearchPositionMaster;
	private String[][]			aryPltSearchWorkSetting;
	private String[][]			aryPltSearchSchedule;
	private String[][]			aryPltSearchPaidHoliday;
	
	/**
	 * 勤怠設定適用区分ラジオボタン。<br>
	 */
	private String				radApplicationType;
	
	
	/**
	 * @return pltSearchWorkPlaceMaster
	 */
	public String getPltSearchWorkPlaceMaster() {
		return pltSearchWorkPlaceMaster;
	}
	
	/**
	 * @param pltSearchWorkPlaceMaster セットする pltSearchWorkPlaceMaster
	 */
	public void setPltSearchWorkPlaceMaster(String pltSearchWorkPlaceMaster) {
		this.pltSearchWorkPlaceMaster = pltSearchWorkPlaceMaster;
	}
	
	/**
	 * @return pltSearchEmploymentMaster
	 */
	public String getPltSearchEmploymentMaster() {
		return pltSearchEmploymentMaster;
	}
	
	/**
	 * @param pltSearchEmploymentMaster セットする pltSearchEmploymentMaster
	 */
	public void setPltSearchEmploymentMaster(String pltSearchEmploymentMaster) {
		this.pltSearchEmploymentMaster = pltSearchEmploymentMaster;
	}
	
	/**
	 * @return pltSearchSectionMaster
	 */
	public String getPltSearchSectionMaster() {
		return pltSearchSectionMaster;
	}
	
	/**
	 * @param pltSearchSectionMaster セットする pltSearchSectionMaster
	 */
	public void setPltSearchSectionMaster(String pltSearchSectionMaster) {
		this.pltSearchSectionMaster = pltSearchSectionMaster;
	}
	
	/**
	 * @return pltSearchPositionMaster
	 */
	public String getPltSearchPositionMaster() {
		return pltSearchPositionMaster;
	}
	
	/**
	 * @param pltSearchPositionMaster セットする pltSearchPositionMaster
	 */
	public void setPltSearchPositionMaster(String pltSearchPositionMaster) {
		this.pltSearchPositionMaster = pltSearchPositionMaster;
	}
	
	/**
	 * @return txtSearchEmployeeCode
	 */
	@Override
	public String getTxtSearchEmployeeCode() {
		return txtSearchEmployeeCode;
	}
	
	/**
	 * @param txtSearchEmployeeCode セットする txtSearchEmployeeCode
	 */
	@Override
	public void setTxtSearchEmployeeCode(String txtSearchEmployeeCode) {
		this.txtSearchEmployeeCode = txtSearchEmployeeCode;
	}
	
	/**
	 * @return pltSearchWorkSetting
	 */
	public String getPltSearchWorkSetting() {
		return pltSearchWorkSetting;
	}
	
	/**
	 * @param pltSearchWorkSetting セットする pltSearchWorkSetting
	 */
	public void setPltSearchWorkSetting(String pltSearchWorkSetting) {
		this.pltSearchWorkSetting = pltSearchWorkSetting;
	}
	
	/**
	 * @return pltSearchSchedule
	 */
	public String getPltSearchSchedule() {
		return pltSearchSchedule;
	}
	
	/**
	 * @param pltSearchSchedule セットする pltSearchSchedule
	 */
	public void setPltSearchSchedule(String pltSearchSchedule) {
		this.pltSearchSchedule = pltSearchSchedule;
	}
	
	/**
	 * @return pltSearchPaidHoliday
	 */
	public String getPltSearchPaidHoliday() {
		return pltSearchPaidHoliday;
	}
	
	/**
	 * @param pltSearchPaidHoliday セットする pltSearchPaidHoliday
	 */
	public void setPltSearchPaidHoliday(String pltSearchPaidHoliday) {
		this.pltSearchPaidHoliday = pltSearchPaidHoliday;
	}
	
	/**
	 * @return aryLblWorkPlace
	 */
	public String[] getAryLblWorkPlace() {
		return getStringArrayClone(aryLblWorkPlace);
	}
	
	/**
	 * @param aryLblWorkPlace セットする aryLblWorkPlace
	 */
	public void setAryLblWorkPlace(String[] aryLblWorkPlace) {
		this.aryLblWorkPlace = getStringArrayClone(aryLblWorkPlace);
	}
	
	/**
	 * @return aryLblEmployment
	 */
	public String[] getAryLblEmployment() {
		return getStringArrayClone(aryLblEmployment);
	}
	
	/**
	 * @param aryLblEmployment セットする aryLblEmployment
	 */
	public void setAryLblEmployment(String[] aryLblEmployment) {
		this.aryLblEmployment = getStringArrayClone(aryLblEmployment);
	}
	
	/**
	 * @return aryLblSection
	 */
	public String[] getAryLblSection() {
		return getStringArrayClone(aryLblSection);
	}
	
	/**
	 * @param aryLblSection セットする aryLblSection
	 */
	public void setAryLblSection(String[] aryLblSection) {
		this.aryLblSection = getStringArrayClone(aryLblSection);
	}
	
	/**
	 * @return aryLblPosition
	 */
	public String[] getAryLblPosition() {
		return getStringArrayClone(aryLblPosition);
	}
	
	/**
	 * @param aryLblPosition セットする aryLblPosition
	 */
	public void setAryLblPosition(String[] aryLblPosition) {
		this.aryLblPosition = getStringArrayClone(aryLblPosition);
	}
	
	/**
	 * @return aryLblEmployeeCode
	 */
	public String[] getAryLblEmployeeCode() {
		return getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @param aryLblEmployeeCode セットする aryLblEmployeeCode
	 */
	public void setAryLblEmployeeCode(String[] aryLblEmployeeCode) {
		this.aryLblEmployeeCode = getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @return aryLblWorkSetting
	 */
	public String[] getAryLblWorkSetting() {
		return getStringArrayClone(aryLblWorkSetting);
	}
	
	/**
	 * @param aryLblWorkSetting セットする aryLblWorkSetting
	 */
	public void setAryLblWorkSetting(String[] aryLblWorkSetting) {
		this.aryLblWorkSetting = getStringArrayClone(aryLblWorkSetting);
	}
	
	/**
	 * @return aryLblSchedule
	 */
	public String[] getAryLblSchedule() {
		return getStringArrayClone(aryLblSchedule);
	}
	
	/**
	 * @param aryLblSchedule セットする aryLblSchedule
	 */
	public void setAryLblSchadeule(String[] aryLblSchedule) {
		this.aryLblSchedule = getStringArrayClone(aryLblSchedule);
	}
	
	/**
	 * @return aryLblPaidHoliday
	 */
	public String[] getAryLblPaidHoliday() {
		return getStringArrayClone(aryLblPaidHoliday);
	}
	
	/**
	 * @param aryLblPaidHoliday セットする aryLblPaidHoliday
	 */
	public void setAryLblPaidHoliday(String[] aryLblPaidHoliday) {
		this.aryLblPaidHoliday = getStringArrayClone(aryLblPaidHoliday);
	}
	
	/**
	 * @return aryPltSearchWorkPlaceMaster
	 */
	public String[][] getAryPltSearchWorkPlaceMaster() {
		return getStringArrayClone(aryPltSearchWorkPlaceMaster);
	}
	
	/**
	 * @param aryPltSearchWorkPlaceMaster セットする aryPltSearchWorkPlaceMaster
	 */
	public void setAryPltSearchWorkPlaceMaster(String[][] aryPltSearchWorkPlaceMaster) {
		this.aryPltSearchWorkPlaceMaster = getStringArrayClone(aryPltSearchWorkPlaceMaster);
	}
	
	/**
	 * @return aryPltSearchEmploymentMaster
	 */
	public String[][] getAryPltSearchEmploymentMaster() {
		return getStringArrayClone(aryPltSearchEmploymentMaster);
	}
	
	/**
	 * @param aryPltSearchEmploymentMaster セットする aryPltSearchEmploymentMaster
	 */
	public void setAryPltSearchEmploymentMaster(String[][] aryPltSearchEmploymentMaster) {
		this.aryPltSearchEmploymentMaster = getStringArrayClone(aryPltSearchEmploymentMaster);
	}
	
	/**
	 * @return aryPltSearchSectionMaster
	 */
	public String[][] getAryPltSearchSectionMaster() {
		return getStringArrayClone(aryPltSearchSectionMaster);
	}
	
	/**
	 * @param aryPltSearchSectionMaster セットする aryPltSearchSectionMaster
	 */
	public void setAryPltSearchSectionMaster(String[][] aryPltSearchSectionMaster) {
		this.aryPltSearchSectionMaster = getStringArrayClone(aryPltSearchSectionMaster);
	}
	
	/**
	 * @return aryPltSearchPositionMaster
	 */
	public String[][] getAryPltSearchPositionMaster() {
		return getStringArrayClone(aryPltSearchPositionMaster);
	}
	
	/**
	 * @param aryPltSearchPositionMaster セットする aryPltSearchPositionMaster
	 */
	public void setAryPltSearchPositionMaster(String[][] aryPltSearchPositionMaster) {
		this.aryPltSearchPositionMaster = getStringArrayClone(aryPltSearchPositionMaster);
	}
	
	/**
	 * @return aryPltSearchWorkSetting
	 */
	public String[][] getAryPltSearchWorkSetting() {
		return getStringArrayClone(aryPltSearchWorkSetting);
	}
	
	/**
	 * @param aryPltSearchWorkSetting セットする aryPltSearchWorkSetting
	 */
	public void setAryPltSearchWorkSetting(String[][] aryPltSearchWorkSetting) {
		this.aryPltSearchWorkSetting = getStringArrayClone(aryPltSearchWorkSetting);
	}
	
	/**
	 * @return aryPltSearchSchedule
	 */
	public String[][] getAryPltSearchSchedule() {
		return getStringArrayClone(aryPltSearchSchedule);
	}
	
	/**
	 * @param aryPltSearchSchedule セットする aryPltSearchSchedule
	 */
	public void setAryPltSearchSchedule(String[][] aryPltSearchSchedule) {
		this.aryPltSearchSchedule = getStringArrayClone(aryPltSearchSchedule);
	}
	
	/**
	 * @return aryPltSearchPaidHoliday
	 */
	public String[][] getAryPltSearchPaidHoliday() {
		return getStringArrayClone(aryPltSearchPaidHoliday);
	}
	
	/**
	 * @param aryPltSearchPaidHoliday セットする aryPltSearchPaidHoliday
	 */
	public void setAryPltSearchPaidHoliday(String[][] aryPltSearchPaidHoliday) {
		this.aryPltSearchPaidHoliday = getStringArrayClone(aryPltSearchPaidHoliday);
	}
	
	/**
	 * @return txtSearchApplicationCode
	 */
	public String getTxtSearchApplicationCode() {
		return txtSearchApplicationCode;
	}
	
	/**
	 * @param txtSearchApplicationCode セットする txtSearchApplicationCode
	 */
	public void setTxtSearchApplicationCode(String txtSearchApplicationCode) {
		this.txtSearchApplicationCode = txtSearchApplicationCode;
	}
	
	/**
	 * @return txtSearchApplicationName
	 */
	public String getTxtSearchApplicationName() {
		return txtSearchApplicationName;
	}
	
	/**
	 * @param txtSearchApplicationName セットする txtSearchApplicationName
	 */
	public void setTxtSearchApplicationName(String txtSearchApplicationName) {
		this.txtSearchApplicationName = txtSearchApplicationName;
	}
	
	/**
	 * @return txtSearchApplicationAbbr
	 */
	public String getTxtSearchApplicationAbbr() {
		return txtSearchApplicationAbbr;
	}
	
	/**
	 * @param txtSearchApplicationAbbr セットする txtSearchApplicationAbbr
	 */
	public void setTxtSearchApplicationAbbr(String txtSearchApplicationAbbr) {
		this.txtSearchApplicationAbbr = txtSearchApplicationAbbr;
	}
	
	/**
	 * @return aryLblApplicationCode
	 */
	public String[] getAryLblApplicationCode() {
		return getStringArrayClone(aryLblApplicationCode);
	}
	
	/**
	 * @param aryLblApplicationCode セットする aryLblApplicationCode
	 */
	public void setAryLblApplicationCode(String[] aryLblApplicationCode) {
		this.aryLblApplicationCode = getStringArrayClone(aryLblApplicationCode);
	}
	
	/**
	 * @return aryLblApplicationAbbr
	 */
	public String[] getAryLblApplicationAbbr() {
		return getStringArrayClone(aryLblApplicationAbbr);
	}
	
	/**
	 * @param aryLblApplicationAbbr セットする aryLblApplicationAbbr
	 */
	public void setAryLblApplicationAbbr(String[] aryLblApplicationAbbr) {
		this.aryLblApplicationAbbr = getStringArrayClone(aryLblApplicationAbbr);
	}
	
	/**
	 * @param aryLblSchedule セットする aryLblSchedule
	 */
	public void setAryLblSchedule(String[] aryLblSchedule) {
		this.aryLblSchedule = getStringArrayClone(aryLblSchedule);
	}
	
	/**
	 * @return radApplicationType
	 */
	public String getRadApplicationType() {
		return radApplicationType;
	}
	
	/**
	 * @param radApplicationType セットする radApplicationType
	 */
	public void setRadApplicationType(String radApplicationType) {
		this.radApplicationType = radApplicationType;
	}
	
}
