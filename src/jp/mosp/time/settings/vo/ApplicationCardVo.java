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
 * 設定適用管理情報を格納する。
 */
public class ApplicationCardVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= -5770274801554160624L;
	
	private String				txtEditApplicationCode;
	private String				txtEditApplicationName;
	private String				txtEditApplicationAbbr;
	private String				pltEditWorkPlaceMaster;
	private String				pltEditEmploymentMaster;
	private String				pltEditSectionMaster;
	private String				pltEditPositionMaster;
	private String				txtEditEmployeeCode;
	private String				pltEditWorkSetting;
	private String				pltEditSchedule;
	private String				pltEditPaidHoliday;
	private String				lblEmployeeName;
	
	private String[][]			aryPltEditWorkPlaceMaster;
	private String[][]			aryPltEditEmploymentMaster;
	private String[][]			aryPltEditSectionMaster;
	private String[][]			aryPltEditPositionMaster;
	private String[][]			aryPltEditEmployeeCode;
	private String[][]			aryPltEditWorkSetting;
	private String[][]			aryPltEditSchedule;
	private String[][]			aryPltEditPaidHoliday;
	
	/**
	 * 勤怠設定適用区分ラジオボタン。<br>
	 */
	private String				radApplicationType;
	
	
	/**
	 * @return txtEditApplicationCode
	 */
	public String getTxtEditApplicationCode() {
		return txtEditApplicationCode;
	}
	
	/**
	 * @param txtEditApplicationCode セットする txtEditApplicationCode
	 */
	public void setTxtEditApplicationCode(String txtEditApplicationCode) {
		this.txtEditApplicationCode = txtEditApplicationCode;
	}
	
	/**
	 * @return txtEditApplicationName
	 */
	public String getTxtEditApplicationName() {
		return txtEditApplicationName;
	}
	
	/**
	 * @param txtEditApplicationName セットする txtEditApplicationName
	 */
	public void setTxtEditApplicationName(String txtEditApplicationName) {
		this.txtEditApplicationName = txtEditApplicationName;
	}
	
	/**
	 * @return txtEditApplicationAbbr
	 */
	public String getTxtEditApplicationAbbr() {
		return txtEditApplicationAbbr;
	}
	
	/**
	 * @param txtEditApplicationAbbr セットする txtEditApplicationAbbr
	 */
	public void setTxtEditApplicationAbbr(String txtEditApplicationAbbr) {
		this.txtEditApplicationAbbr = txtEditApplicationAbbr;
	}
	
	/**
	 * @return pltEditWorkPlaceMaster
	 */
	public String getPltEditWorkPlaceMaster() {
		return pltEditWorkPlaceMaster;
	}
	
	/**
	 * @param pltEditWorkPlaceMaster セットする pltEditWorkPlaceMaster
	 */
	public void setPltEditWorkPlaceMaster(String pltEditWorkPlaceMaster) {
		this.pltEditWorkPlaceMaster = pltEditWorkPlaceMaster;
	}
	
	/**
	 * @return pltEditEmploymentMaster
	 */
	public String getPltEditEmploymentMaster() {
		return pltEditEmploymentMaster;
	}
	
	/**
	 * @param pltEditEmploymentMaster セットする pltEditEmploymentMaster
	 */
	public void setPltEditEmploymentMaster(String pltEditEmploymentMaster) {
		this.pltEditEmploymentMaster = pltEditEmploymentMaster;
	}
	
	/**
	 * @return pltEditSectionMaster
	 */
	public String getPltEditSectionMaster() {
		return pltEditSectionMaster;
	}
	
	/**
	 * @param pltEditSectionMaster セットする pltEditSectionMaster
	 */
	public void setPltEditSectionMaster(String pltEditSectionMaster) {
		this.pltEditSectionMaster = pltEditSectionMaster;
	}
	
	/**
	 * @return pltEditPositionMaster
	 */
	public String getPltEditPositionMaster() {
		return pltEditPositionMaster;
	}
	
	/**
	 * @param pltEditPositionMaster セットする pltEditPositionMaster
	 */
	public void setPltEditPositionMaster(String pltEditPositionMaster) {
		this.pltEditPositionMaster = pltEditPositionMaster;
	}
	
	/**
	 * @return txtEditEmployeeCode
	 */
	public String getTxtEditEmployeeCode() {
		return txtEditEmployeeCode;
	}
	
	/**
	 * @param txtEditEmployeeCode セットする txtEditEmployeeCode
	 */
	public void setTxtEditEmployeeCode(String txtEditEmployeeCode) {
		this.txtEditEmployeeCode = txtEditEmployeeCode;
	}
	
	/**
	 * @return pltEditWorkSetting
	 */
	public String getPltEditWorkSetting() {
		return pltEditWorkSetting;
	}
	
	/**
	 * @param pltEditWorkSetting セットする pltEditWorkSetting
	 */
	public void setPltEditWorkSetting(String pltEditWorkSetting) {
		this.pltEditWorkSetting = pltEditWorkSetting;
	}
	
	/**
	 * @return pltEditSchedule
	 */
	public String getPltEditSchedule() {
		return pltEditSchedule;
	}
	
	/**
	 * @param pltEditSchedule セットする pltEditSchedule
	 */
	public void setPltEditSchedule(String pltEditSchedule) {
		this.pltEditSchedule = pltEditSchedule;
	}
	
	/**
	 * @return pltEditPaidHoliday
	 */
	public String getPltEditPaidHoliday() {
		return pltEditPaidHoliday;
	}
	
	/**
	 * @param pltEditPaidHoliday セットする pltEditPaidHoliday
	 */
	public void setPltEditPaidHoliday(String pltEditPaidHoliday) {
		this.pltEditPaidHoliday = pltEditPaidHoliday;
	}
	
	/**
	 * @return lblEmployeeName
	 */
	@Override
	public String getLblEmployeeName() {
		return lblEmployeeName;
	}
	
	/**
	 * @param lblEmployeeName セットする lblEmployeeName
	 */
	@Override
	public void setLblEmployeeName(String lblEmployeeName) {
		this.lblEmployeeName = lblEmployeeName;
	}
	
	/**
	 * @return aryPltEditWorkPlaceMaster
	 */
	public String[][] getAryPltEditWorkPlaceMaster() {
		return getStringArrayClone(aryPltEditWorkPlaceMaster);
	}
	
	/**
	 * @param aryPltEditWorkPlaceMaster セットする aryPltEditWorkPlaceMaster
	 */
	public void setAryPltEditWorkPlaceMaster(String[][] aryPltEditWorkPlaceMaster) {
		this.aryPltEditWorkPlaceMaster = getStringArrayClone(aryPltEditWorkPlaceMaster);
	}
	
	/**
	 * @return aryPltEditEmploymentMaster
	 */
	public String[][] getAryPltEditEmploymentMaster() {
		return getStringArrayClone(aryPltEditEmploymentMaster);
	}
	
	/**
	 * @param aryPltEditEmploymentMaster セットする aryPltEditEmploymentMaster
	 */
	public void setAryPltEditEmploymentMaster(String[][] aryPltEditEmploymentMaster) {
		this.aryPltEditEmploymentMaster = getStringArrayClone(aryPltEditEmploymentMaster);
	}
	
	/**
	 * @return aryPltEditSectionMaster
	 */
	public String[][] getAryPltEditSectionMaster() {
		return getStringArrayClone(aryPltEditSectionMaster);
	}
	
	/**
	 * @param aryPltEditSectionMaster セットする aryPltEditSectionMaster
	 */
	public void setAryPltEditSectionMaster(String[][] aryPltEditSectionMaster) {
		this.aryPltEditSectionMaster = getStringArrayClone(aryPltEditSectionMaster);
	}
	
	/**
	 * @return aryPltEditPositionMaster
	 */
	public String[][] getAryPltEditPositionMaster() {
		return getStringArrayClone(aryPltEditPositionMaster);
	}
	
	/**
	 * @param aryPltEditPositionMaster セットする aryPltEditPositionMaster
	 */
	public void setAryPltEditPositionMaster(String[][] aryPltEditPositionMaster) {
		this.aryPltEditPositionMaster = getStringArrayClone(aryPltEditPositionMaster);
	}
	
	/**
	 * @return aryPltEditEmployeeCode
	 */
	public String[][] getAryPltEditEmployeeCode() {
		return getStringArrayClone(aryPltEditEmployeeCode);
	}
	
	/**
	 * @param aryPltEditEmployeeCode セットする aryPltEditEmployeeCode
	 */
	public void setAryPltEditEmployeeCode(String[][] aryPltEditEmployeeCode) {
		this.aryPltEditEmployeeCode = getStringArrayClone(aryPltEditEmployeeCode);
	}
	
	/**
	 * @return aryPltEditWorkSetting
	 */
	public String[][] getAryPltEditWorkSetting() {
		return getStringArrayClone(aryPltEditWorkSetting);
	}
	
	/**
	 * @param aryPltEditWorkSetting セットする aryPltEditWorkSetting
	 */
	public void setAryPltEditWorkSetting(String[][] aryPltEditWorkSetting) {
		this.aryPltEditWorkSetting = getStringArrayClone(aryPltEditWorkSetting);
	}
	
	/**
	 * @return aryPltEditSchedule
	 */
	public String[][] getAryPltEditSchedule() {
		return getStringArrayClone(aryPltEditSchedule);
	}
	
	/**
	 * @param aryPltEditSchedule セットする aryPltEditSchedule
	 */
	public void setAryPltEditSchedule(String[][] aryPltEditSchedule) {
		this.aryPltEditSchedule = getStringArrayClone(aryPltEditSchedule);
	}
	
	/**
	 * @return aryPltEditPaidHoliday
	 */
	public String[][] getAryPltEditPaidHoliday() {
		return getStringArrayClone(aryPltEditPaidHoliday);
	}
	
	/**
	 * @param aryPltEditPaidHoliday セットする aryPltEditPaidHoliday
	 */
	public void setAryPltEditPaidHoliday(String[][] aryPltEditPaidHoliday) {
		this.aryPltEditPaidHoliday = getStringArrayClone(aryPltEditPaidHoliday);
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
