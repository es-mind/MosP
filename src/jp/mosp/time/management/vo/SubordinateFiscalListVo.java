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
package jp.mosp.time.management.vo;

import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 部下年度一覧VOクラス。<br>
 */
public class SubordinateFiscalListVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= 8428897943002313833L;
	
	// 表示年度
	private String				pltSearchDisplayYear;
	// 表示年度モード
	private String				modeDisplayYear;
	// 申請者・部下検索プルダウン
	private String				pltSearchHumanType;
	// 検索年月
	private String				pltSearchRequestYear;
	private String				pltSearchRequestMonth;
	// 社員番号
	private String				txtSearchEmployeeCode;
	// 社員名
	private String				txtSearchEmployeeName;
	// 勤務地
	private String				pltSearchWorkPlace;
	// 雇用契約
	private String				pltSearchEmployment;
	// 所属
	private String				pltSearchSection;
	// 職位
	private String				pltSearchPosition;
	
	// 季休項目名称
	private String				lblSeasonHolidayItem;
	
	// 社員コード
	private String[]			aryLblEmployeeCode;
	// 社員名
	private String[]			aryLblEmployeeName;
	// 所属
	private String[]			aryLblSection;
	// 残業
	private String[]			aryLblOverTime;
	// 普休始
	private String[]			aryLblPaidHolidayDays;
	// 普休残
	private String[]			aryLblPaidHolidayRestDays;
	// 保休始
	private String[]			aryLblStockHolidayDays;
	// 保休残
	private String[]			aryLblStockHolidayRestDays;
	// 季休始
	private String[]			aryLblSeasonHolidayDays;
	// 季休残
	private String[]			aryLblSeasonHolidayRestDays;
	
	// 検索プルダウン
	private String[][]			aryPltDisplayYear;
	private String[][]			aryPltRequestYear;
	private String[][]			aryPltRequestMonth;
	private String[][]			aryPltWorkPlace;
	private String[][]			aryPltEmployment;
	private String[][]			aryPltSection;
	private String[][]			aryPltPosition;
	private String[][]			aryPltHumanType;
	
	// 検索デフォルト設定
	private boolean				jsSearchConditionRequired;
	
	
	/**
	 * @return aryLblOverTime
	 */
	public String[] getAryLblOverTime() {
		return getStringArrayClone(aryLblOverTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblOverTime
	 */
	public String getAryLblOverTime(int idx) {
		return aryLblOverTime[idx];
	}
	
	/**
	 * @param aryLblOverTime セットする aryLblOverTime
	 */
	public void setAryLblOverTime(String[] aryLblOverTime) {
		this.aryLblOverTime = getStringArrayClone(aryLblOverTime);
	}
	
	/**
	 * @return aryLblPaidHolidayDays
	 */
	public String[] getAryLblPaidHolidayDays() {
		return getStringArrayClone(aryLblPaidHolidayDays);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblPaidHolidayDays
	 */
	public String getAryLblPaidHolidayDays(int idx) {
		return aryLblPaidHolidayDays[idx];
	}
	
	/**
	 * @param aryLblPaidHolidayDays セットする aryLblPaidHolidayDays
	 */
	public void setAryLblPaidHolidayDays(String[] aryLblPaidHolidayDays) {
		this.aryLblPaidHolidayDays = getStringArrayClone(aryLblPaidHolidayDays);
	}
	
	/**
	 * @return aryLblPaidHolidayRestDays
	 */
	public String[] getAryLblPaidHolidayRestDays() {
		return getStringArrayClone(aryLblPaidHolidayRestDays);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblPaidHolidayRestDays
	 */
	public String getAryLblPaidHolidayRestDays(int idx) {
		return aryLblPaidHolidayRestDays[idx];
	}
	
	/**
	 * @param aryLblPaidHolidayRestDays セットする aryLblPaidHolidayRestDays
	 */
	public void setAryLblPaidHolidayRestDays(String[] aryLblPaidHolidayRestDays) {
		this.aryLblPaidHolidayRestDays = getStringArrayClone(aryLblPaidHolidayRestDays);
	}
	
	/**
	 * @return aryLblStockHolidayDays
	 */
	public String[] getAryLblStockHolidayDays() {
		return getStringArrayClone(aryLblStockHolidayDays);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblStockHolidayDays
	 */
	public String getAryLblStockHolidayDays(int idx) {
		return aryLblStockHolidayDays[idx];
	}
	
	/**
	 * @param aryLblStockHolidayDays セットする aryLblStockHolidayDays
	 */
	public void setAryLblStockHolidayDays(String[] aryLblStockHolidayDays) {
		this.aryLblStockHolidayDays = getStringArrayClone(aryLblStockHolidayDays);
	}
	
	/**
	 * @return aryLblSeasonHolidayDays
	 */
	public String[] getAryLblSeasonHolidayDays() {
		return getStringArrayClone(aryLblSeasonHolidayDays);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblSeasonHolidayDays
	 */
	public String getAryLblSeasonHolidayDays(int idx) {
		return aryLblSeasonHolidayDays[idx];
	}
	
	/**
	 * @param aryLblSeasonHolidayDays セットする aryLblSeasonHolidayDays
	 */
	public void setAryLblSeasonHolidayDays(String[] aryLblSeasonHolidayDays) {
		this.aryLblSeasonHolidayDays = getStringArrayClone(aryLblSeasonHolidayDays);
	}
	
	/**
	 * @return aryLblSeasonHolidayRestDays
	 */
	public String[] getAryLblSeasonHolidayRestDays() {
		return getStringArrayClone(aryLblSeasonHolidayRestDays);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblSeasonHolidayRestDays
	 */
	public String getAryLblSeasonHolidayRestDays(int idx) {
		return aryLblSeasonHolidayRestDays[idx];
	}
	
	/**
	 * @param aryLblSeasonHolidayRestDays セットする aryLblSeasonHolidayRestDays
	 */
	public void setAryLblSeasonHolidayRestDays(String[] aryLblSeasonHolidayRestDays) {
		this.aryLblSeasonHolidayRestDays = getStringArrayClone(aryLblSeasonHolidayRestDays);
	}
	
	/**
	 * @return aryLblStockHolidayRestDays
	 */
	public String[] getAryLblStockHolidayRestDays() {
		return getStringArrayClone(aryLblStockHolidayRestDays);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblStockHolidayRestDays
	 */
	public String getAryLblStockHolidayRestDays(int idx) {
		return aryLblStockHolidayRestDays[idx];
	}
	
	/**
	 * @param aryLblStockHolidayRestDays セットする aryLblStockHolidayRestDays
	 */
	public void setAryLblStockHolidayRestDays(String[] aryLblStockHolidayRestDays) {
		this.aryLblStockHolidayRestDays = getStringArrayClone(aryLblStockHolidayRestDays);
	}
	
	/**
	 * @return pltSearchDisplayYear
	 */
	public String getPltSearchDisplayYear() {
		return pltSearchDisplayYear;
	}
	
	/**
	 * @param pltSearchDisplayYear セットする pltSearchDisplayYear
	 */
	public void setPltSearchDisplayYear(String pltSearchDisplayYear) {
		this.pltSearchDisplayYear = pltSearchDisplayYear;
	}
	
	/**
	 * @return pltSearchHumanType
	 */
	public String getPltSearchHumanType() {
		return pltSearchHumanType;
	}
	
	/**
	 * @param pltSearchHumanType セットする pltSearchHumanType
	 */
	public void setPltSearchHumanType(String pltSearchHumanType) {
		this.pltSearchHumanType = pltSearchHumanType;
	}
	
	/**
	 * @return pltSearchRequestYear
	 */
	public String getPltSearchRequestYear() {
		return pltSearchRequestYear;
	}
	
	/**
	 * @param pltSearchRequestYear セットする pltSearchRequestYear
	 */
	public void setPltSearchRequestYear(String pltSearchRequestYear) {
		this.pltSearchRequestYear = pltSearchRequestYear;
	}
	
	/**
	 * @return pltSearchRequestMonth
	 */
	public String getPltSearchRequestMonth() {
		return pltSearchRequestMonth;
	}
	
	/**
	 * @param pltSearchRequestMonth セットする pltSearchRequestMonth
	 */
	public void setPltSearchRequestMonth(String pltSearchRequestMonth) {
		this.pltSearchRequestMonth = pltSearchRequestMonth;
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
	
	/**
	 * @return aryPltRequestYear
	 */
	public String[][] getAryPltRequestYear() {
		return getStringArrayClone(aryPltRequestYear);
	}
	
	/**
	 * @return aryPltRequestMonth
	 */
	public String[][] getAryPltRequestMonth() {
		return getStringArrayClone(aryPltRequestMonth);
	}
	
	/**
	 * @return aryPltWorkPlace
	 */
	public String[][] getAryPltWorkPlace() {
		return getStringArrayClone(aryPltWorkPlace);
	}
	
	/**
	 * @return aryPltEmployment
	 */
	public String[][] getAryPltEmployment() {
		return getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @return aryPltSection
	 */
	public String[][] getAryPltSection() {
		return getStringArrayClone(aryPltSection);
	}
	
	/**
	 * @return aryPltPosition
	 */
	public String[][] getAryPltPosition() {
		return getStringArrayClone(aryPltPosition);
	}
	
	/**
	 * @return aryPltHumanType
	 */
	public String[][] getAryPltHumanType() {
		return getStringArrayClone(aryPltHumanType);
	}
	
	/**
	 * @param aryPltRequestYear セットする aryPltRequestYear
	 */
	public void setAryPltRequestYear(String[][] aryPltRequestYear) {
		this.aryPltRequestYear = getStringArrayClone(aryPltRequestYear);
	}
	
	/**
	 * @param aryPltRequestMonth セットする aryPltRequestMonth
	 */
	public void setAryPltRequestMonth(String[][] aryPltRequestMonth) {
		this.aryPltRequestMonth = getStringArrayClone(aryPltRequestMonth);
	}
	
	/**
	 * @param aryPltWorkPlace セットする aryPltWorkPlace
	 */
	public void setAryPltWorkPlace(String[][] aryPltWorkPlace) {
		this.aryPltWorkPlace = getStringArrayClone(aryPltWorkPlace);
	}
	
	/**
	 * @param aryPltEmployment セットする aryPltEmployment
	 */
	public void setAryPltEmployment(String[][] aryPltEmployment) {
		this.aryPltEmployment = getStringArrayClone(aryPltEmployment);
	}
	
	/**
	 * @param aryPltSection セットする aryPltSection
	 */
	public void setAryPltSection(String[][] aryPltSection) {
		this.aryPltSection = getStringArrayClone(aryPltSection);
	}
	
	/**
	 * @param aryPltPosition セットする aryPltPosition
	 */
	public void setAryPltPosition(String[][] aryPltPosition) {
		this.aryPltPosition = getStringArrayClone(aryPltPosition);
	}
	
	/**
	 * @param aryPltHumanType セットする aryPltHumanType
	 */
	public void setAryPltHumanType(String[][] aryPltHumanType) {
		this.aryPltHumanType = getStringArrayClone(aryPltHumanType);
	}
	
	/**
	 * @return aryLblEmployeeCode
	 */
	public String[] getAryLblEmployeeCode() {
		return getStringArrayClone(aryLblEmployeeCode);
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
	 * @return aryLblEmployeeName
	 */
	public String[] getAryLblEmployeeName() {
		return getStringArrayClone(aryLblEmployeeName);
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
	 * @return aryLblSection
	 */
	public String[] getAryLblSection() {
		return getStringArrayClone(aryLblSection);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblSection
	 */
	public String getAryLblSection(int idx) {
		return aryLblSection[idx];
	}
	
	/**
	 * @param aryLblSection セットする aryLblSection
	 */
	public void setAryLblSection(String[] aryLblSection) {
		this.aryLblSection = getStringArrayClone(aryLblSection);
	}
	
	/**
	 * @return aryPltDisplayYear
	 */
	public String[][] getAryPltDisplayYear() {
		return getStringArrayClone(aryPltDisplayYear);
	}
	
	/**
	 * @param aryPltDisplayYear セットする aryPltDisplayYear
	 */
	public void setAryPltDisplayYear(String[][] aryPltDisplayYear) {
		this.aryPltDisplayYear = getStringArrayClone(aryPltDisplayYear);
	}
	
	/**
	 * @return modeDisplayYear
	 */
	public String getModeDisplayYear() {
		return modeDisplayYear;
	}
	
	/**
	 * @param modeDisplayYear セットする modeDisplayYear
	 */
	public void setModeDisplayYear(String modeDisplayYear) {
		this.modeDisplayYear = modeDisplayYear;
	}
	
	/**
	 * @return lblSeasonHolidayItem
	 */
	public String getLblSeasonHolidayItem() {
		return lblSeasonHolidayItem;
	}
	
	/**
	 * @param lblSeasonHolidayItem セットする lblSeasonHolidayItem
	 */
	public void setLblSeasonHolidayItem(String lblSeasonHolidayItem) {
		this.lblSeasonHolidayItem = lblSeasonHolidayItem;
	}
}
