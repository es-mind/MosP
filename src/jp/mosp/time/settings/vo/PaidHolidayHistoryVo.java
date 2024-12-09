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
 * 有給休暇手動付与情報を格納する。
 */
public class PaidHolidayHistoryVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= 1995476447953140669L;
	
	private String				txtEditEmployeeCode;
	private String				lblEditEmployeeName;
	
	private String				pltEditFormerGivingType;
	private String				pltEditGivingType;
	private String				pltEditStockType;
	private String				txtEditFormerGivingDay;
	private String				txtEditFormerGivingTime;
	private String				txtEditGivingDay;
	private String				txtEditGivingTime;
	private String				txtEditGivingStockDay;
	
	private String				txtSearchEmployeeCode;
	private String				txtSearchEmployeeName;
	private String				pltSearchWorkPlace;
	private String				pltSearchEmployment;
	private String				pltSearchSection;
	private String				pltSearchPosition;
	
	private String				lblBeforeTotalDate;
	private String				lblBeforeTotalTime;
	private String				lblBeforeFormerDate;
	private String				lblBeforeFormerTime;
	private String				lblBeforeDate;
	private String				lblBeforeTime;
	private String				lblBeforeStockDate;
	
	private String				lblAfterTotalDate;
	private String				lblAfterTotalTime;
	private String				lblAfterFormerDate;
	private String				lblAfterFormerTime;
	private String				lblAfterDate;
	private String				lblAfterTime;
	private String				lblAfterStockDate;
	
	private String[]			aryLblActivateDate;
	private String[]			aryLblEmployeeCode;
	private String[]			aryLblEmployeeName;
	private String[]			aryLblSection;
	private String[]			aryLblFormerDate;
	private String[]			aryLblFormerTime;
	private String[]			aryLblDate;
	private String[]			aryLblTime;
	private String[]			aryLblStockDate;
	
	private String[]			aryClaFormerDate;
	private String[]			aryClaFormerTime;
	private String[]			aryClaDate;
	private String[]			aryClaTime;
	private String[]			aryClaStockDate;
	
	private String[][]			aryPltFormerGivingType;
	private String[][]			aryPltGivingType;
	private String[][]			aryPltStockType;
	private String[][]			aryPltSearchWorkPlace;
	private String[][]			aryPltSearchEmployment;
	private String[][]			aryPltSearchSection;
	private String[][]			aryPltSearchPosition;
	
	private long[]				aryCkbRecordId;
	private long				tmtPaidHolidayId;
	private long				tmtPaidHolidayFormerId;
	private long				tmtStockHolidayId;
	
	// 保有数の文字色設定用領域
	private String				claBeforeTotalDate;
	private String				claBeforeTotalTime;
	private String				claBeforeFormerDate;
	private String				claBeforeFormerTime;
	private String				claBeforeDate;
	private String				claBeforeTime;
	private String				claBeforeStockDate;
	private String				claAfterTotalDate;
	private String				claAfterTotalTime;
	private String				claAfterFormerDate;
	private String				claAfterFormerTime;
	private String				claAfterDate;
	private String				claAfterTime;
	private String				claAfterStockDate;
	
	// 時間設定有効無効判定
	private Boolean				jsModeGivingtime;
	
	/**
	 * 社員コード決定モード。<br>
	 */
	private String				jsModeEmployeeCode;
	
	/**
	 * 個人ID配列。<br> 
	 * 一覧に表示されている個人IDを格納する。<br>
	 * 画面遷移に用いる。<br>
	 */
	private String[]			aryPersonalId;
	
	
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
	 * @return lblEditEmployeeName
	 */
	public String getLblEditEmployeeName() {
		return lblEditEmployeeName;
	}
	
	/**
	 * @param lblEditEmployeeName セットする lblEditEmployeeName
	 */
	public void setLblEditEmployeeName(String lblEditEmployeeName) {
		this.lblEditEmployeeName = lblEditEmployeeName;
	}
	
	/**
	 * @return pltEditFormerGivingType
	 */
	public String getPltEditFormerGivingType() {
		return pltEditFormerGivingType;
	}
	
	/**
	 * @param pltEditFormerGivingType セットする pltEditFormerGivingType
	 */
	public void setPltEditFormerGivingType(String pltEditFormerGivingType) {
		this.pltEditFormerGivingType = pltEditFormerGivingType;
	}
	
	/**
	 * @return pltEditGivingType
	 */
	public String getPltEditGivingType() {
		return pltEditGivingType;
	}
	
	/**
	 * @param pltEditGivingType セットする pltEditGivingType
	 */
	public void setPltEditGivingType(String pltEditGivingType) {
		this.pltEditGivingType = pltEditGivingType;
	}
	
	/**
	 * @return txtEditFormerGivingDay
	 */
	public String getTxtEditFormerGivingDay() {
		return txtEditFormerGivingDay;
	}
	
	/**
	 * @param txtEditFormerGivingDay セットする txtEditFormerGivingDay
	 */
	public void setTxtEditFormerGivingDay(String txtEditFormerGivingDay) {
		this.txtEditFormerGivingDay = txtEditFormerGivingDay;
	}
	
	/**
	 * @return txtEditFormerGivingTime
	 */
	public String getTxtEditFormerGivingTime() {
		return txtEditFormerGivingTime;
	}
	
	/**
	 * @param txtEditFormerGivingTime セットする txtEditFormerGivingTime
	 */
	public void setTxtEditFormerGivingTime(String txtEditFormerGivingTime) {
		this.txtEditFormerGivingTime = txtEditFormerGivingTime;
	}
	
	/**
	 * @return txtEditGivingDay
	 */
	public String getTxtEditGivingDay() {
		return txtEditGivingDay;
	}
	
	/**
	 * @param txtEditGivingDay セットする txtEditGivingDay
	 */
	public void setTxtEditGivingDay(String txtEditGivingDay) {
		this.txtEditGivingDay = txtEditGivingDay;
	}
	
	/**
	 * @return txtEditGivingTime
	 */
	public String getTxtEditGivingTime() {
		return txtEditGivingTime;
	}
	
	/**
	 * @param txtEditGivingTime セットする txtEditGivingTime
	 */
	public void setTxtEditGivingTime(String txtEditGivingTime) {
		this.txtEditGivingTime = txtEditGivingTime;
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
	 * @return lblBeforeTotalDate
	 */
	public String getLblBeforeTotalDate() {
		return lblBeforeTotalDate;
	}
	
	/**
	 * @param lblBeforeTotalDate セットする lblBeforeTotalDate
	 */
	public void setLblBeforeTotalDate(String lblBeforeTotalDate) {
		this.lblBeforeTotalDate = lblBeforeTotalDate;
	}
	
	/**
	 * @return lblBeforeTotalTime
	 */
	public String getLblBeforeTotalTime() {
		return lblBeforeTotalTime;
	}
	
	/**
	 * @param lblBeforeTotalTime セットする lblBeforeTotalTime
	 */
	public void setLblBeforeTotalTime(String lblBeforeTotalTime) {
		this.lblBeforeTotalTime = lblBeforeTotalTime;
	}
	
	/**
	 * @return lblBeforeFormerDate
	 */
	public String getLblBeforeFormerDate() {
		return lblBeforeFormerDate;
	}
	
	/**
	 * @param lblBeforeFormerDate セットする lblBeforeFormerDate
	 */
	public void setLblBeforeFormerDate(String lblBeforeFormerDate) {
		this.lblBeforeFormerDate = lblBeforeFormerDate;
	}
	
	/**
	 * @return lblBeforeFormerTime
	 */
	public String getLblBeforeFormerTime() {
		return lblBeforeFormerTime;
	}
	
	/**
	 * @param lblBeforeFormerTime セットする lblBeforeFormerTime
	 */
	public void setLblBeforeFormerTime(String lblBeforeFormerTime) {
		this.lblBeforeFormerTime = lblBeforeFormerTime;
	}
	
	/**
	 * @return lblBeforeDate
	 */
	public String getLblBeforeDate() {
		return lblBeforeDate;
	}
	
	/**
	 * @param lblBeforeDate セットする lblBeforeDate
	 */
	public void setLblBeforeDate(String lblBeforeDate) {
		this.lblBeforeDate = lblBeforeDate;
	}
	
	/**
	 * @return lblBeforeTime
	 */
	public String getLblBeforeTime() {
		return lblBeforeTime;
	}
	
	/**
	 * @param lblBeforeTime セットする lblBeforeTime
	 */
	public void setLblBeforeTime(String lblBeforeTime) {
		this.lblBeforeTime = lblBeforeTime;
	}
	
	/**
	 * @return lblAfterTotalDate
	 */
	public String getLblAfterTotalDate() {
		return lblAfterTotalDate;
	}
	
	/**
	 * @param lblAfterTotalDate セットする lblAfterTotalDate
	 */
	public void setLblAfterTotalDate(String lblAfterTotalDate) {
		this.lblAfterTotalDate = lblAfterTotalDate;
	}
	
	/**
	 * @return lblAfterTotalTime
	 */
	public String getLblAfterTotalTime() {
		return lblAfterTotalTime;
	}
	
	/**
	 * @param lblAfterTotalTime セットする lblAfterTotalTime
	 */
	public void setLblAfterTotalTime(String lblAfterTotalTime) {
		this.lblAfterTotalTime = lblAfterTotalTime;
	}
	
	/**
	 * @return lblAfterFormerDate
	 */
	public String getLblAfterFormerDate() {
		return lblAfterFormerDate;
	}
	
	/**
	 * @param lblAfterFormerDate セットする lblAfterFormerDate
	 */
	public void setLblAfterFormerDate(String lblAfterFormerDate) {
		this.lblAfterFormerDate = lblAfterFormerDate;
	}
	
	/**
	 * @return lblAfterFormerTime
	 */
	public String getLblAfterFormerTime() {
		return lblAfterFormerTime;
	}
	
	/**
	 * @param lblAfterFormerTime セットする lblAfterFormerTime
	 */
	public void setLblAfterFormerTime(String lblAfterFormerTime) {
		this.lblAfterFormerTime = lblAfterFormerTime;
	}
	
	/**
	 * @return lblAfterDate
	 */
	public String getLblAfterDate() {
		return lblAfterDate;
	}
	
	/**
	 * @param lblAfterDate セットする lblAfterDate
	 */
	public void setLblAfterDate(String lblAfterDate) {
		this.lblAfterDate = lblAfterDate;
	}
	
	/**
	 * @return lblAfterTime
	 */
	public String getLblAfterTime() {
		return lblAfterTime;
	}
	
	/**
	 * @param lblAfterTime セットする lblAfterTime
	 */
	public void setLblAfterTime(String lblAfterTime) {
		this.lblAfterTime = lblAfterTime;
	}
	
	/**
	 * @return aryLblActivateDate
	 */
	@Override
	public String[] getAryLblActivateDate() {
		return getStringArrayClone(aryLblActivateDate);
	}
	
	/**
	 * @param aryLblActivateDate セットする aryLblActivateDate
	 */
	@Override
	public void setAryLblActivateDate(String[] aryLblActivateDate) {
		this.aryLblActivateDate = getStringArrayClone(aryLblActivateDate);
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
	 * @return aryLblEmployereturn getStringArrayClone(a */
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
	 * @return aryPltGivingType
	 */
	public String[][] getAryPltGivingType() {
		return getStringArrayClone(aryPltGivingType);
	}
	
	/**
	 * @param aryPltGivingType セットする aryPltGivingType
	 */
	public void setAryPltGivingType(String[][] aryPltGivingType) {
		this.aryPltGivingType = getStringArrayClone(aryPltGivingType);
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
	 * @return lblBeforeStockDate
	 */
	public String getLblBeforeStockDate() {
		return lblBeforeStockDate;
	}
	
	/**
	 * @return lblAfterStockDate
	 */
	public String getLblAfterStockDate() {
		return lblAfterStockDate;
	}
	
	/**
	 * @param lblBeforeStockDate セットする lblBeforeStockDate
	 */
	public void setLblBeforeStockDate(String lblBeforeStockDate) {
		this.lblBeforeStockDate = lblBeforeStockDate;
	}
	
	/**
	 * @param lblAfterStockDate セットする lblAfterStockDate
	 */
	public void setLblAfterStockDate(String lblAfterStockDate) {
		this.lblAfterStockDate = lblAfterStockDate;
	}
	
	/**
	 * @return aryLblFormerDate
	 */
	public String[] getAryLblFormerDate() {
		return getStringArrayClone(aryLblFormerDate);
	}
	
	/**
	 * @return aryLblFormerTime
	 */
	public String[] getAryLblFormerTime() {
		return getStringArrayClone(aryLblFormerTime);
	}
	
	/**
	 * @return aryLblDate
	 */
	public String[] getAryLblDate() {
		return getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @return aryLblTime
	 */
	public String[] getAryLblTime() {
		return getStringArrayClone(aryLblTime);
	}
	
	/**
	 * @return aryLblStockDate
	 */
	public String[] getAryLblStockDate() {
		return getStringArrayClone(aryLblStockDate);
	}
	
	/**
	 * @return aryClaFormerDate
	 */
	public String[] getAryClaFormerDate() {
		return getStringArrayClone(aryClaFormerDate);
	}
	
	/**
	 * @return aryClaFormerTime
	 */
	public String[] getAryClaFormerTime() {
		return getStringArrayClone(aryClaFormerTime);
	}
	
	/**
	 * @return aryClaDate
	 */
	public String[] getAryClaDate() {
		return getStringArrayClone(aryClaDate);
	}
	
	/**
	 * @return aryClaTime
	 */
	public String[] getAryClaTime() {
		return getStringArrayClone(aryClaTime);
	}
	
	/**
	 * @return aryClaStockDate
	 */
	public String[] getAryClaStockDate() {
		return getStringArrayClone(aryClaStockDate);
	}
	
	/**
	 * @param aryLblFormerDate セットする aryLblFormerDate
	 */
	public void setAryLblFormerDate(String[] aryLblFormerDate) {
		this.aryLblFormerDate = getStringArrayClone(aryLblFormerDate);
	}
	
	/**
	 * @param aryLblFormerTime セットする aryLblFormerTime
	 */
	public void setAryLblFormerTime(String[] aryLblFormerTime) {
		this.aryLblFormerTime = getStringArrayClone(aryLblFormerTime);
	}
	
	/**
	 * @param aryLblDate セットする aryLblDate
	 */
	public void setAryLblDate(String[] aryLblDate) {
		this.aryLblDate = getStringArrayClone(aryLblDate);
	}
	
	/**
	 * @param aryLblTime セットする aryLblTime
	 */
	public void setAryLblTime(String[] aryLblTime) {
		this.aryLblTime = getStringArrayClone(aryLblTime);
	}
	
	/**
	 * @param aryLblStockDate セットする aryLblStockDate
	 */
	public void setAryLblStockDate(String[] aryLblStockDate) {
		this.aryLblStockDate = getStringArrayClone(aryLblStockDate);
	}
	
	/**
	 * @param aryClaFormerDate セットする aryClaFormerDate
	 */
	public void setAryClaFormerDate(String[] aryClaFormerDate) {
		this.aryClaFormerDate = getStringArrayClone(aryClaFormerDate);
	}
	
	/**
	 * @param aryClaFormerTime セットする aryClaFormerTime
	 */
	public void setAryClaFormerTime(String[] aryClaFormerTime) {
		this.aryClaFormerTime = getStringArrayClone(aryClaFormerTime);
	}
	
	/**
	 * @param aryClaDate セットする aryClaDate
	 */
	public void setAryClaDate(String[] aryClaDate) {
		this.aryClaDate = getStringArrayClone(aryClaDate);
	}
	
	/**
	 * @param aryClaTime セットする aryClaTime
	 */
	public void setAryClaTime(String[] aryClaTime) {
		this.aryClaTime = getStringArrayClone(aryClaTime);
	}
	
	/**
	 * @param aryClaStockDate セットする aryClaStockDate
	 */
	public void setAryClaStockDate(String[] aryClaStockDate) {
		this.aryClaStockDate = getStringArrayClone(aryClaStockDate);
	}
	
	/**
	 * @return pltEditStockType
	 */
	public String getPltEditStockType() {
		return pltEditStockType;
	}
	
	/**
	 * @return aryPltFormerGivingType
	 */
	public String[][] getAryPltFormerGivingType() {
		return getStringArrayClone(aryPltFormerGivingType);
	}
	
	/**
	 * @return aryPltStockType
	 */
	public String[][] getAryPltStockType() {
		return getStringArrayClone(aryPltStockType);
	}
	
	/**
	 * @param pltEditStockType セットする pltEditStockType
	 */
	public void setPltEditStockType(String pltEditStockType) {
		this.pltEditStockType = pltEditStockType;
	}
	
	/**
	 * @param aryPltFormerGivingType セットする aryPltFormerGivingType
	 */
	public void setAryPltFormerGivingType(String[][] aryPltFormerGivingType) {
		this.aryPltFormerGivingType = getStringArrayClone(aryPltFormerGivingType);
	}
	
	/**
	 * @param aryPltStockType セットする aryPltStockType
	 */
	public void setAryPltStockType(String[][] aryPltStockType) {
		this.aryPltStockType = getStringArrayClone(aryPltStockType);
	}
	
	/**
	 * @return aryCkbRecordId
	 */
	@Override
	public long[] getAryCkbRecordId() {
		return getLongArrayClone(aryCkbRecordId);
	}
	
	/**
	 * @param aryCkbRecordId セットする aryCkbRecordId
	 */
	@Override
	public void setAryCkbRecordId(long[] aryCkbRecordId) {
		this.aryCkbRecordId = getLongArrayClone(aryCkbRecordId);
	}
	
	/**
	 * @return txtEditGivingStockDay
	 */
	public String getTxtEditGivingStockDay() {
		return txtEditGivingStockDay;
	}
	
	/**
	 * @param txtEditGivingStockDay セットする txtEditGivingStockDay
	 */
	public void setTxtEditGivingStockDay(String txtEditGivingStockDay) {
		this.txtEditGivingStockDay = txtEditGivingStockDay;
	}
	
	/**
	 * @return jsModeEmployeeCode
	 */
	public String getJsModeEmployeeCode() {
		return jsModeEmployeeCode;
	}
	
	/**
	 * @param jsModeEmployeeCode セットする jsModeEmployeeCode
	 */
	public void setJsModeEmployeeCode(String jsModeEmployeeCode) {
		this.jsModeEmployeeCode = jsModeEmployeeCode;
	}
	
	/**
	 * @return tmtPaidHolidayId
	 */
	public long getTmtPaidHolidayId() {
		return tmtPaidHolidayId;
	}
	
	/**
	 * @param tmtPaidHolidayId セットする tmtPaidHolidayId
	 */
	public void setTmtPaidHolidayId(long tmtPaidHolidayId) {
		this.tmtPaidHolidayId = tmtPaidHolidayId;
	}
	
	/**
	 * @return tmtPaidHolidayFormerId
	 */
	public long getTmtPaidHolidayFormerId() {
		return tmtPaidHolidayFormerId;
	}
	
	/**
	 * @param tmtPaidHolidayFormerId セットする tmtPaidHolidayFormerId
	 */
	public void setTmtPaidHolidayFormerId(long tmtPaidHolidayFormerId) {
		this.tmtPaidHolidayFormerId = tmtPaidHolidayFormerId;
	}
	
	/**
	 * @return tmtStockHolidayId
	 */
	public long getTmtStockHolidayId() {
		return tmtStockHolidayId;
	}
	
	/**
	 * @param tmtStockHolidayId セットする tmtStockHolidayId
	 */
	public void setTmtStockHolidayId(long tmtStockHolidayId) {
		this.tmtStockHolidayId = tmtStockHolidayId;
	}
	
	/**
	 * @return claBeforeTotalDate
	 */
	public String getClaBeforeTotalDate() {
		return claBeforeTotalDate;
	}
	
	/**
	 * @param claBeforeTotalDate セットする claBeforeTotalDate
	 */
	public void setClaBeforeTotalDate(String claBeforeTotalDate) {
		this.claBeforeTotalDate = claBeforeTotalDate;
	}
	
	/**
	 * @return claBeforeTotalTime
	 */
	public String getClaBeforeTotalTime() {
		return claBeforeTotalTime;
	}
	
	/**
	 * @param claBeforeTotalTime セットする claBeforeTotalTime
	 */
	public void setClaBeforeTotalTime(String claBeforeTotalTime) {
		this.claBeforeTotalTime = claBeforeTotalTime;
	}
	
	/**
	 * @return claBeforeFormerDate
	 */
	public String getClaBeforeFormerDate() {
		return claBeforeFormerDate;
	}
	
	/**
	 * @param claBeforeFormerDate セットする claBeforeFormerDate
	 */
	public void setClaBeforeFormerDate(String claBeforeFormerDate) {
		this.claBeforeFormerDate = claBeforeFormerDate;
	}
	
	/**
	 * @return claBeforeFormerTime
	 */
	public String getClaBeforeFormerTime() {
		return claBeforeFormerTime;
	}
	
	/**
	 * @param claBeforeFormerTime セットする claBeforeFormerTime
	 */
	public void setClaBeforeFormerTime(String claBeforeFormerTime) {
		this.claBeforeFormerTime = claBeforeFormerTime;
	}
	
	/**
	 * @return claBeforeDate
	 */
	public String getClaBeforeDate() {
		return claBeforeDate;
	}
	
	/**
	 * @param claBeforeDate セットする claBeforeDate
	 */
	public void setClaBeforeDate(String claBeforeDate) {
		this.claBeforeDate = claBeforeDate;
	}
	
	/**
	 * @return claBeforeTime
	 */
	public String getClaBeforeTime() {
		return claBeforeTime;
	}
	
	/**
	 * @param claBeforeTime セットする claBeforeTime
	 */
	public void setClaBeforeTime(String claBeforeTime) {
		this.claBeforeTime = claBeforeTime;
	}
	
	/**
	 * @return claBeforeStockDate
	 */
	public String getClaBeforeStockDate() {
		return claBeforeStockDate;
	}
	
	/**
	 * @param claBeforeStockDate セットする claBeforeStockDate
	 */
	public void setClaBeforeStockDate(String claBeforeStockDate) {
		this.claBeforeStockDate = claBeforeStockDate;
	}
	
	/**
	 * @return claAfterTotalDate
	 */
	public String getClaAfterTotalDate() {
		return claAfterTotalDate;
	}
	
	/**
	 * @param claAfterTotalDate セットする claAfterTotalDate
	 */
	public void setClaAfterTotalDate(String claAfterTotalDate) {
		this.claAfterTotalDate = claAfterTotalDate;
	}
	
	/**
	 * @return claAfterTotalTime
	 */
	public String getClaAfterTotalTime() {
		return claAfterTotalTime;
	}
	
	/**
	 * @param claAfterTotalTime セットする claAfterTotalTime
	 */
	public void setClaAfterTotalTime(String claAfterTotalTime) {
		this.claAfterTotalTime = claAfterTotalTime;
	}
	
	/**
	 * @return claAfterFormerDate
	 */
	public String getClaAfterFormerDate() {
		return claAfterFormerDate;
	}
	
	/**
	 * @param claAfterFormerDate セットする claAfterFormerDate
	 */
	public void setClaAfterFormerDate(String claAfterFormerDate) {
		this.claAfterFormerDate = claAfterFormerDate;
	}
	
	/**
	 * @return claAfterFormerTime
	 */
	public String getClaAfterFormerTime() {
		return claAfterFormerTime;
	}
	
	/**
	 * @param claAfterFormerTime セットする claAfterFormerTime
	 */
	public void setClaAfterFormerTime(String claAfterFormerTime) {
		this.claAfterFormerTime = claAfterFormerTime;
	}
	
	/**
	 * @return claAfterDate
	 */
	public String getClaAfterDate() {
		return claAfterDate;
	}
	
	/**
	 * @param claAfterDate セットする claAfterDate
	 */
	public void setClaAfterDate(String claAfterDate) {
		this.claAfterDate = claAfterDate;
	}
	
	/**
	 * @return claAfterTime
	 */
	public String getClaAfterTime() {
		return claAfterTime;
	}
	
	/**
	 * @param claAfterTime セットする claAfterTime
	 */
	public void setClaAfterTime(String claAfterTime) {
		this.claAfterTime = claAfterTime;
	}
	
	/**
	 * @return claAfterStockDate
	 */
	public String getClaAfterStockDate() {
		return claAfterStockDate;
	}
	
	/**
	 * @param claAfterStockDate セットする claAfterStockDate
	 */
	public void setClaAfterStockDate(String claAfterStockDate) {
		this.claAfterStockDate = claAfterStockDate;
	}
	
	/**
	 * @return jsModeGivingtime
	 */
	public Boolean getJsModeGivingtime() {
		return jsModeGivingtime;
	}
	
	/**
	 * @param jsModeGivingtime セットする jsModeGivingtime
	 */
	public void setJsModeGivingtime(Boolean jsModeGivingtime) {
		this.jsModeGivingtime = jsModeGivingtime;
	}
	
	/**
	 * @param aryPersonalId セットする aryPersonalId
	 */
	public void setAryPersonalId(String[] aryPersonalId) {
		this.aryPersonalId = getStringArrayClone(aryPersonalId);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryPersonalId
	 */
	public String getAryPersonalId(int idx) {
		return aryPersonalId[idx];
	}
}
