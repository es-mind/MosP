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
 * 設定適用管理参照一覧画面の情報を格納する。
 */
public class ApplicationReferenceVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= -5309750805033770099L;
	
	// 入力項目社員検索欄
	/**
	 * 社員名。
	 */
	private String				txtSearchEmployeeName;
	
	/**
	 * 勤務地(略称)。
	 */
	private String				pltSearchWorkPlace;
	
	/**
	 * 雇用契約（略称）。
	 */
	private String				pltSearchEmployment;
	
	/**
	 * 所属。
	 */
	private String				pltSearchSection;
	
	/**
	 * 職位（略称）
	 */
	private String				pltSearchPosition;
	
	// 設定検索欄
	/**
	 * 設定適用コード。
	 */
	private String				txtSearchApplicationCode;
	
	/**
	 * 設定適用名称。
	 */
	private String				txtSearchApplicationName;
	
	/**
	 * 勤怠設定略称。
	 */
	private String				pltSearchTimeSetting;
	
	/**
	 * 締日コード略称。
	 */
	private String				pltSearchCutoff;
	
	/**
	 * カレンダ略称。
	 */
	private String				pltSearchSchedule;
	
	/**
	 * 有休設定略称。
	 */
	private String				pltSearchPaidHoliday;
	
	private boolean				jsSearchConditionRequired;
	
	// 出力項目
	/**
	 * 有効日。
	 */
	private String[]			aryLblActivateDate;
	
	/**
	 * 社員コード。
	 */
	private String[]			aryLblEmployeeCode;
	
	/**
	 * 社員名。
	 */
	private String[]			aryLblEmployeeName;
	
	/**
	 * 設定適用コード。
	 */
	private String[]			aryLblApplicationCode;
	
	/**
	 * 設定適用。
	 */
	private String[]			aryLblApplication;
	
	/**
	 * 勤怠設定。
	 */
	private String[]			aryLblTimeSetting;
	
	/**
	 * 締日。
	 */
	private String[]			aryLblCutoff;
	
	/**
	 * カレンダ。
	 */
	private String[]			aryLblSchadeule;
	
	/**
	 * 有休設定。
	 */
	private String[]			aryLblPaidHoliday;
	
	
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
	
	
	private String[][]	aryPltSearchWorkPlaceMaster;
	private String[][]	aryPltSearchEmploymentMaster;
	private String[][]	aryPltSearchSectionMaster;
	private String[][]	aryPltSearchPositionMaster;
	private String[][]	aryPltSearchTimeSetting;
	private String[][]	aryPltSearchSchedule;
	private String[][]	aryPltSearchPaidHoliday;
	private String[][]	aryPltSearchCutoff;
	
	
	/**
	 * @return txtSearchEmployeeName
	 */
	public String getTxtSearchEmployeeName() {
		return txtSearchEmployeeName;
	}
	
	/**
	 * @param txtSearchEmployeeName セットする txtSearchEmployeeName
	 */
	public void setTxtSearchEmployeeName(String txtSearchEmployeeName) {
		this.txtSearchEmployeeName = txtSearchEmployeeName;
	}
	
	/**
	 * @return pltSearchWorkPlace
	 */
	public String getPltSearchWorkPlace() {
		return pltSearchWorkPlace;
	}
	
	/**
	 * @param pltSearchWorkPlace セットする pltSearchWorkPlace
	 */
	public void setPltSearchWorkPlace(String pltSearchWorkPlace) {
		this.pltSearchWorkPlace = pltSearchWorkPlace;
	}
	
	/**
	 * @return pltSearchEmployment
	 */
	public String getPltSearchEmployment() {
		return pltSearchEmployment;
	}
	
	/**
	 * @param pltSearchEmployment セットする pltSearchEmployment
	 */
	public void setPltSearchEmployment(String pltSearchEmployment) {
		this.pltSearchEmployment = pltSearchEmployment;
	}
	
	/**
	 * @return pltSearchSection
	 */
	public String getPltSearchSection() {
		return pltSearchSection;
	}
	
	/**
	 * @param pltSearchSection セットする pltSearchSection
	 */
	public void setPltSearchSection(String pltSearchSection) {
		this.pltSearchSection = pltSearchSection;
	}
	
	/**
	 * @return pltSearchPosition
	 */
	public String getPltSearchPosition() {
		return pltSearchPosition;
	}
	
	/**
	 * @param pltSearchPosition セットする pltSearchPosition
	 */
	public void setPltSearchPosition(String pltSearchPosition) {
		this.pltSearchPosition = pltSearchPosition;
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
	 * @return pltSearchTimeSetting
	 */
	public String getPltSearchTimeSetting() {
		return pltSearchTimeSetting;
	}
	
	/**
	 * @param pltSearchTimeSetting セットする pltSearchTimeSetting
	 */
	public void setPltSearchTimeSetting(String pltSearchTimeSetting) {
		this.pltSearchTimeSetting = pltSearchTimeSetting;
	}
	
	/**
	 * @return pltSearchCutoff
	 */
	public String getPltSearchCutoff() {
		return pltSearchCutoff;
	}
	
	/**
	 * @param pltSearchCutoff セットする pltSearchCutoff
	 */
	public void setPltSearchCutoff(String pltSearchCutoff) {
		this.pltSearchCutoff = pltSearchCutoff;
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
	 * @param idx インデックス
	 * @return aryLblEmployeeCode
	 */
	public String getAryLblEmployeeCode(int idx) {
		return aryLblEmployeeCode[idx];
	}
	
	/**
	 * @return aryLblEmployeeName
	 */
	public String[] getAryLblEmployeeName() {
		return getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @param aryLblEmployeeName セットする aryLblEmployeeName
	 */
	public void setAryLblEmployeeName(String[] aryLblEmployeeName) {
		this.aryLblEmployeeName = getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEmployeeName
	 */
	public String getAryLblEmployeeName(int idx) {
		return aryLblEmployeeName[idx];
	}
	
	/**
	 * @return aryLblApplication
	 */
	public String[] getAryLblApplication() {
		return getStringArrayClone(aryLblApplication);
	}
	
	/**
	 * @param aryLblApplication セットする aryLblApplication
	 */
	public void setAryLblApplication(String[] aryLblApplication) {
		this.aryLblApplication = getStringArrayClone(aryLblApplication);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblApplication
	 */
	public String getAryLblApplication(int idx) {
		return aryLblApplication[idx];
	}
	
	/**
	 * @return aryLblTimeSettingAbbr
	 */
	public String[] getAryLblTimeSetting() {
		return getStringArrayClone(aryLblTimeSetting);
	}
	
	/**
	 * @param aryLblTimeSetting セットする aryLblTimeSettingAbbr
	 */
	public void setAryLblTimeSetting(String[] aryLblTimeSetting) {
		this.aryLblTimeSetting = getStringArrayClone(aryLblTimeSetting);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblTimeSetting
	 */
	public String getAryLblTimeSetting(int idx) {
		return aryLblTimeSetting[idx];
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
	 * @param idx インデックス
	 * @return aryLblCutoff
	 */
	public String getAryLblCutoff(int idx) {
		return aryLblCutoff[idx];
	}
	
	/**
	 * @return aryLblSchadeule
	 */
	public String[] getAryLblSchadeule() {
		return getStringArrayClone(aryLblSchadeule);
	}
	
	/**
	 * @param aryLblSchadeule セットする aryLblSchadeule
	 */
	public void setAryLblSchadeule(String[] aryLblSchadeule) {
		this.aryLblSchadeule = getStringArrayClone(aryLblSchadeule);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblSchadeule
	 */
	public String getAryLblSchadeule(int idx) {
		return aryLblSchadeule[idx];
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
	 * @param idx インデックス
	 * @return aryLblPaidHoliday
	 */
	public String getAryLblPaidHoliday(int idx) {
		return aryLblPaidHoliday[idx];
	}
	
	/**
	 * @param aryPltSearchTimeSetting セットする aryPltSearchTimeSetting
	 */
	public void setAryPltSearchTimeSetting(String[][] aryPltSearchTimeSetting) {
		this.aryPltSearchTimeSetting = getStringArrayClone(aryPltSearchTimeSetting);
	}
	
	/**
	 * @return aryPltSearchTimeSetting
	 */
	public String[][] getAryPltSearchTimeSetting() {
		return getStringArrayClone(aryPltSearchTimeSetting);
	}
	
	/**
	 * @param aryPltSearchCutoff セットする aryPltSearchCutoff
	 */
	public void setAryPltSearchCutoff(String[][] aryPltSearchCutoff) {
		this.aryPltSearchCutoff = getStringArrayClone(aryPltSearchCutoff);
	}
	
	/**
	 * @return aryPltSearchCutoff
	 */
	public String[][] getAryPltSearchCutoff() {
		return getStringArrayClone(aryPltSearchCutoff);
	}
	
	/**
	 * @param aryLblApplicationCode セットする aryLblApplicationCode
	 */
	public void setAryLblApplicationCode(String[] aryLblApplicationCode) {
		this.aryLblApplicationCode = getStringArrayClone(aryLblApplicationCode);
	}
	
	/**
	 * @return aryLblApplicationCode
	 */
	public String[] getAryLblApplicationCode() {
		return getStringArrayClone(aryLblApplicationCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblApplicationCode
	 */
	public String getAryLblApplicationCode(int idx) {
		return aryLblApplicationCode[idx];
	}
	
	@Override
	public void setAryLblActivateDate(String[] aryLblActivateDate) {
		this.aryLblActivateDate = getStringArrayClone(aryLblActivateDate);
	}
	
	@Override
	public String[] getAryLblActivateDate() {
		return getStringArrayClone(aryLblActivateDate);
	}
	
	@Override
	public String getAryLblActivateDate(int idx) {
		return aryLblActivateDate[idx];
	}
	
	/**
	 * @return jsSearchConditionRequired
	 */
	public boolean isJsSearchConditionRequired() {
		return jsSearchConditionRequired;
	}
	
	/**
	 * @param jsSearchConditionRequired セットする jsSearchConditionRequired
	 */
	public void setJsSearchConditionRequired(boolean jsSearchConditionRequired) {
		this.jsSearchConditionRequired = jsSearchConditionRequired;
	}
	
}
