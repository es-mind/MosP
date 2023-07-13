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

import jp.mosp.time.base.TimeVo;

/**
 * 有給休暇データ一覧情報を格納する。
 */
public class PaidHolidayDataGrantListVo extends TimeVo {
	
	private static final long	serialVersionUID	= 2440053688510104556L;
	
	private String				txtSearchEntrance;
	private String				txtSearchEntranceMonth;
	private String				txtSearchEntranceDay;
	private String				txtSearchEmployeeName;
	private String				pltSearchWorkPlace;
	private String				pltSearchEmployment;
	private String				pltSearchSection;
	private String				pltSearchPosition;
	private String				pltSearchPaidHoliday;
	private String				pltSearchGrant;
	
	private String[][]			aryPltSearchWorkPlace;
	private String[][]			aryPltSearchEmployment;
	private String[][]			aryPltSearchSection;
	private String[][]			aryPltSearchPosition;
	private String[][]			aryPltSearchPaidHoliday;
	
	private String[]			aryLblEmployeeCode;
	private String[]			aryLblEmployeeName;
	private String[]			aryLblAttendanceRate;
	private String[]			aryLblAccomplish;
	private String[]			aryLblGrant;
	private String[]			aryLblGrantDate;
	private String[]			aryLblGrantDays;
	
	private String[]			aryPersonalId;
	
	// 出勤率計算確認フラグ
	private String				jsCalcAttendanceRate;
	
	private boolean				jsSearchConditionRequired;
	
	
	/**
	 * @return txtSearchEntrance
	 */
	public String getTxtSearchEntrance() {
		return txtSearchEntrance;
	}
	
	/**
	 * @param txtSearchEntrance セットする txtSearchEntrance
	 */
	public void setTxtSearchEntrance(String txtSearchEntrance) {
		this.txtSearchEntrance = txtSearchEntrance;
	}
	
	/**
	 * @return txtSearchEntranceMonth
	 */
	public String getTxtSearchEntranceMonth() {
		return txtSearchEntranceMonth;
	}
	
	/**
	 * @param txtSearchEntranceMonth セットする txtSearchEntranceMonth
	 */
	public void setTxtSearchEntranceMonth(String txtSearchEntranceMonth) {
		this.txtSearchEntranceMonth = txtSearchEntranceMonth;
	}
	
	/**
	 * @return txtSearchEntranceDay
	 */
	public String getTxtSearchEntranceDay() {
		return txtSearchEntranceDay;
	}
	
	/**
	 * @param txtSearchEntranceDay セットする txtSearchEntranceDay
	 */
	public void setTxtSearchEntranceDay(String txtSearchEntranceDay) {
		this.txtSearchEntranceDay = txtSearchEntranceDay;
	}
	
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
	 * @return pltSearchGrant
	 */
	public String getPltSearchGrant() {
		return pltSearchGrant;
	}
	
	/**
	 * @param pltSearchGrant セットする pltSearchGrant
	 */
	public void setPltSearchGrant(String pltSearchGrant) {
		this.pltSearchGrant = pltSearchGrant;
	}
	
	/**
	 * @return aryPltSearchWorkPlace
	 */
	public String[][] getAryPltSearchWorkPlace() {
		return getStringArrayClone(aryPltSearchWorkPlace);
	}
	
	/**
	 * @param aryPltSearchWorkPlace セットする aryPltSearchWorkPlace
	 */
	public void setAryPltSearchWorkPlace(String[][] aryPltSearchWorkPlace) {
		this.aryPltSearchWorkPlace = getStringArrayClone(aryPltSearchWorkPlace);
	}
	
	/**
	 * @return aryPltSearchEmployment
	 */
	public String[][] getAryPltSearchEmployment() {
		return getStringArrayClone(aryPltSearchEmployment);
	}
	
	/**
	 * @param aryPltSearchEmployment セットする aryPltSearchEmployment
	 */
	public void setAryPltSearchEmployment(String[][] aryPltSearchEmployment) {
		this.aryPltSearchEmployment = getStringArrayClone(aryPltSearchEmployment);
	}
	
	/**
	 * @return aryPltSearchSection
	 */
	public String[][] getAryPltSearchSection() {
		return getStringArrayClone(aryPltSearchSection);
	}
	
	/**
	 * @param aryPltSearchSection セットする aryPltSearchSection
	 */
	public void setAryPltSearchSection(String[][] aryPltSearchSection) {
		this.aryPltSearchSection = getStringArrayClone(aryPltSearchSection);
	}
	
	/**
	 * @return aryPltSearchPosition
	 */
	public String[][] getAryPltSearchPosition() {
		return getStringArrayClone(aryPltSearchPosition);
	}
	
	/**
	 * @param aryPltSearchPosition セットする aryPltSearchPosition
	 */
	public void setAryPltSearchPosition(String[][] aryPltSearchPosition) {
		this.aryPltSearchPosition = getStringArrayClone(aryPltSearchPosition);
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
	 * @param idx インデックス
	 * @return aryLblEmployeeCode
	 */
	public String getAryLblEmployeeCode(int idx) {
		return aryLblEmployeeCode[idx];
	}
	
	/**
	 * @param aryLblEmployeeCode セットする aryLblEmployeeCode
	 */
	public void setAryLblEmployeeCode(String[] aryLblEmployeeCode) {
		this.aryLblEmployeeCode = getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblEmployeeName
	 */
	public String getAryLblEmployeeName(int idx) {
		return aryLblEmployeeName[idx];
	}
	
	/**
	 * @param aryLblEmployeeName セットする aryLblEmployeeName
	 */
	public void setAryLblEmployeeName(String[] aryLblEmployeeName) {
		this.aryLblEmployeeName = getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblAttendanceRate
	 */
	public String getAryLblAttendanceRate(int idx) {
		return aryLblAttendanceRate[idx];
	}
	
	/**
	 * @param aryLblAttendanceRate セットする aryLblAttendanceRate
	 */
	public void setAryLblAttendanceRate(String[] aryLblAttendanceRate) {
		this.aryLblAttendanceRate = getStringArrayClone(aryLblAttendanceRate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblAccomplish
	 */
	public String getAryLblAccomplish(int idx) {
		return aryLblAccomplish[idx];
	}
	
	/**
	 * @param aryLblAccomplish セットする aryLblAccomplish
	 */
	public void setAryLblAccomplish(String[] aryLblAccomplish) {
		this.aryLblAccomplish = getStringArrayClone(aryLblAccomplish);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblGrant
	 */
	public String getAryLblGrant(int idx) {
		return aryLblGrant[idx];
	}
	
	/**
	 * @param aryLblGrant セットする aryLblGrant
	 */
	public void setAryLblGrant(String[] aryLblGrant) {
		this.aryLblGrant = getStringArrayClone(aryLblGrant);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblGrantDate
	 */
	public String getAryLblGrantDate(int idx) {
		return aryLblGrantDate[idx];
	}
	
	/**
	 * @param aryLblGrantDate セットする aryLblGrantDate
	 */
	public void setAryLblGrantDate(String[] aryLblGrantDate) {
		this.aryLblGrantDate = getStringArrayClone(aryLblGrantDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblGrantDays
	 */
	public String getAryLblGrantDays(int idx) {
		return aryLblGrantDays[idx];
	}
	
	/**
	 * @param aryLblGrantDays セットする aryLblGrantDays
	 */
	public void setAryLblGrantDays(String[] aryLblGrantDays) {
		this.aryLblGrantDays = getStringArrayClone(aryLblGrantDays);
	}
	
	/**
	 * @return aryPersonalId
	 */
	public String[] getAryPersonalId() {
		return getStringArrayClone(aryPersonalId);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryPersonalId
	 */
	public String getAryPersonalId(int idx) {
		return aryPersonalId[idx];
	}
	
	/**
	 * @param aryPersonalId セットする aryPersonalId
	 */
	public void setAryPersonalId(String[] aryPersonalId) {
		this.aryPersonalId = getStringArrayClone(aryPersonalId);
	}
	
	/**
	 * @return jsCalcAttendanceRate
	 */
	public String getjsCalcAttendanceRate() {
		return jsCalcAttendanceRate;
	}
	
	/**
	 * @param jsCalcAttendanceRate セットする jsCalcAttendanceRate
	 */
	public void setJsCalcAttendanceRate(String jsCalcAttendanceRate) {
		this.jsCalcAttendanceRate = jsCalcAttendanceRate;
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
