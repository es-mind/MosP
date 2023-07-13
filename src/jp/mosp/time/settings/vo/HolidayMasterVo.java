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

import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.js.DirectJs;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.settings.action.HolidayMasterAction;
import jp.mosp.time.settings.base.TimeSettingVo;

/**
 * 休暇種類管理情報を格納する。
 */
public class HolidayMasterVo extends TimeSettingVo {
	
	private static final long	serialVersionUID			= -5307653458155393367L;
	
	private String				txtEditHolidayCode;
	private String				pltEditHolidayType;
	private String				txtEditHolidayName;
	private String				txtEditHolidayAbbr;
	private String				txtEditHolidayGiving;
	private String				txtEditHolidayLimitMonth;
	private String				txtEditHolidayLimitDay;
	private String				pltEditHalfHolidayRequest;
	private String				pltEditContinue;
	private String				pltEditHourlyHoliday;
	private String				pltEditSalary;
	private String				pltEditReasonType;
	private String				pltEditPaidHolidayCalc;
	private String				ckbNoLimit;
	
	private String				txtSearchHolidayCode;
	private String				txtSearchHolidayName;
	private String				txtSearchHolidayAbbr;
	private String				txtSearchHolidayGiving;
	private String				txtSearchHolidayLimit;
	private String				pltSearchHolidayType;
	
	private String[]			aryLblHolidayCode;
	private String[]			aryLblHolidayType;
	private String[]			aryLblHolidayTypeName;
	private String[]			aryLblHolidayName;
	private String[]			aryLblHolidayAbbr;
	private String[]			aryLblHolidayGiving;
	private String[]			aryLblHolidayLimit;
	private String[]			aryLblHolidayContinue;
	private String[]			aryLblTimelyHoliday;
	private String[]			aryLblHolidaySalary;
	
	/**
	 * 休暇連続取得区分(不要)。<br>
	 */
	@DirectJs
	private static final int	TYPE_HOLIDAY_ABSENCE		= TimeConst.CODE_HOLIDAYTYPE_ABSENCE;
	
	/**
	 * 休暇連続取得区分(不要)。<br>
	 */
	@DirectJs
	private static final int	TYPE_CONTINUOUS_UNNECESSARY	= TimeConst.TYPE_CONTINUOUS_UNNECESSARY;
	
	/**
	 * 時間単位取得フラグ(有効)。<br>
	 */
	@DirectJs
	private static final int	FLAG_HOURLY_VALID			= MospConst.INACTIVATE_FLAG_OFF;
	
	/**
	 * 休暇連続取得区分(不要)。<br>
	 */
	@DirectJs
	private static final int	TYPE_SALARY_PAY_NONE		= HolidayMasterAction.TYPE_SALARY_PAY_NONE;
	
	
	/**
	 * @return pltEditHolidayType
	 */
	public String getPltEditHolidayType() {
		return pltEditHolidayType;
	}
	
	/**
	 * @param pltEditHolidayType セットする pltEditHolidayType
	 */
	public void setPltEditHolidayType(String pltEditHolidayType) {
		this.pltEditHolidayType = pltEditHolidayType;
	}
	
	/**
	 * @return txtEditHolidayName
	 */
	public String getTxtEditHolidayName() {
		return txtEditHolidayName;
	}
	
	/**
	 * @param txtEditHolidayName セットする txtEditHolidayName
	 */
	public void setTxtEditHolidayName(String txtEditHolidayName) {
		this.txtEditHolidayName = txtEditHolidayName;
	}
	
	/**
	 * @return txtEditHolidayAbbr
	 */
	public String getTxtEditHolidayAbbr() {
		return txtEditHolidayAbbr;
	}
	
	/**
	 * @param txtEditHolidayAbbr セットする txtEditHolidayAbbr
	 */
	public void setTxtEditHolidayAbbr(String txtEditHolidayAbbr) {
		this.txtEditHolidayAbbr = txtEditHolidayAbbr;
	}
	
	/**
	 * @return txtEditHolidayGiving
	 */
	public String getTxtEditHolidayGiving() {
		return txtEditHolidayGiving;
	}
	
	/**
	 * @param txtEditHolidayGiving セットする txtEditHolidayGiving
	 */
	public void setTxtEditHolidayGiving(String txtEditHolidayGiving) {
		this.txtEditHolidayGiving = txtEditHolidayGiving;
	}
	
	/**
	 * @return txtEditHolidayLimitDay
	 */
	public String getTxtEditHolidayLimitDay() {
		return txtEditHolidayLimitDay;
	}
	
	/**
	 * @param txtEditHolidayLimitDay セットする txtEditHolidayLimitDay
	 */
	public void setTxtEditHolidayLimitDay(String txtEditHolidayLimitDay) {
		this.txtEditHolidayLimitDay = txtEditHolidayLimitDay;
	}
	
	/**
	 * @return pltEditHalfHolidayRequest
	 */
	public String getPltEditHalfHolidayRequest() {
		return pltEditHalfHolidayRequest;
	}
	
	/**
	 * @param pltEditHalfHolidayRequest セットする pltEditHalfHolidayRequest
	 */
	public void setPltEditHalfHolidayRequest(String pltEditHalfHolidayRequest) {
		this.pltEditHalfHolidayRequest = pltEditHalfHolidayRequest;
	}
	
	/**
	 * @return pltEditContinue
	 */
	public String getPltEditContinue() {
		return pltEditContinue;
	}
	
	/**
	 * @param pltEditContinue セットする pltEditContinue
	 */
	public void setPltEditContinue(String pltEditContinue) {
		this.pltEditContinue = pltEditContinue;
	}
	
	/**
	 * @return pltEditSalary
	 */
	public String getPltEditSalary() {
		return pltEditSalary;
	}
	
	/**
	 * @param pltEditSalary セットする pltEditSalary
	 */
	public void setPltEditSalary(String pltEditSalary) {
		this.pltEditSalary = pltEditSalary;
	}
	
	/**
	 * @return pltEditReasonType
	 */
	public String getPltEditReasonType() {
		return pltEditReasonType;
	}
	
	/**
	 * @param pltEditReasonType セットする pltEditReasonType
	 */
	public void setPltEditReasonType(String pltEditReasonType) {
		this.pltEditReasonType = pltEditReasonType;
	}
	
	/**
	 * @return txtSearchHolidayCode
	 */
	public String getTxtSearchHolidayCode() {
		return txtSearchHolidayCode;
	}
	
	/**
	 * @param txtSearchHolidayCode セットする txtSearchHolidayCode
	 */
	public void setTxtSearchHolidayCode(String txtSearchHolidayCode) {
		this.txtSearchHolidayCode = txtSearchHolidayCode;
	}
	
	/**
	 * @return txtSearchHolidayName
	 */
	public String getTxtSearchHolidayName() {
		return txtSearchHolidayName;
	}
	
	/**
	 * @param txtSearchHolidayName セットする txtSearchHolidayName
	 */
	public void setTxtSearchHolidayName(String txtSearchHolidayName) {
		this.txtSearchHolidayName = txtSearchHolidayName;
	}
	
	/**
	 * @return txtSearchHolidayAbbr
	 */
	public String getTxtSearchHolidayAbbr() {
		return txtSearchHolidayAbbr;
	}
	
	/**
	 * @param txtSearchHolidayAbbr セットする txtSearchHolidayAbbr
	 */
	public void setTxtSearchHolidayAbbr(String txtSearchHolidayAbbr) {
		this.txtSearchHolidayAbbr = txtSearchHolidayAbbr;
	}
	
	/**
	 * @return txtSearchHolidayGiving
	 */
	public String getTxtSearchHolidayGiving() {
		return txtSearchHolidayGiving;
	}
	
	/**
	 * @param txtSearchHolidayGiving セットする txtSearchHolidayGiving
	 */
	public void setTxtSearchHolidayGiving(String txtSearchHolidayGiving) {
		this.txtSearchHolidayGiving = txtSearchHolidayGiving;
	}
	
	/**
	 * @return txtSearchHolidayLimit
	 */
	public String getTxtSearchHolidayLimit() {
		return txtSearchHolidayLimit;
	}
	
	/**
	 * @param txtSearchHolidayLimit セットする txtSearchHolidayLimit
	 */
	public void setTxtSearchHolidayLimit(String txtSearchHolidayLimit) {
		this.txtSearchHolidayLimit = txtSearchHolidayLimit;
	}
	
	/**
	 * @return aryLblHolidayCode
	 */
	public String[] getAryLblHolidayCode() {
		return getStringArrayClone(aryLblHolidayCode);
	}
	
	/**
	 * @param aryLblHolidayCode セットする aryLblHolidayCode
	 */
	public void setAryLblHolidayCode(String[] aryLblHolidayCode) {
		this.aryLblHolidayCode = getStringArrayClone(aryLblHolidayCode);
	}
	
	/**
	 * @return aryLblHolidayType
	 */
	public String[] getAryLblHolidayType() {
		return getStringArrayClone(aryLblHolidayType);
	}
	
	/**
	 * @param aryLblHolidayType セットする aryLblHolidayType
	 */
	public void setAryLblHolidayType(String[] aryLblHolidayType) {
		this.aryLblHolidayType = getStringArrayClone(aryLblHolidayType);
	}
	
	/**
	 * @return aryLblHolidayTypeName
	 */
	public String[] getAryLblHolidayTypeName() {
		return getStringArrayClone(aryLblHolidayTypeName);
	}
	
	/**
	 * @param aryLblHolidayTypeName セットする aryLblHolidayTypeName
	 */
	public void setAryLblHolidayTypeName(String[] aryLblHolidayTypeName) {
		this.aryLblHolidayTypeName = getStringArrayClone(aryLblHolidayTypeName);
	}
	
	/**
	 * @return aryLblHolidayName
	 */
	public String[] getAryLblHolidayName() {
		return getStringArrayClone(aryLblHolidayName);
	}
	
	/**
	 * @param aryLblHolidayName セットする aryLblHolidayName
	 */
	public void setAryLblHolidayName(String[] aryLblHolidayName) {
		this.aryLblHolidayName = getStringArrayClone(aryLblHolidayName);
	}
	
	/**
	 * @return aryLblHolidayAbbr
	 */
	public String[] getAryLblHolidayAbbr() {
		return getStringArrayClone(aryLblHolidayAbbr);
	}
	
	/**
	 * @param aryLblHolidayAbbr セットする aryLblHolidayAbbr
	 */
	public void setAryLblHolidayAbbr(String[] aryLblHolidayAbbr) {
		this.aryLblHolidayAbbr = getStringArrayClone(aryLblHolidayAbbr);
	}
	
	/**
	 * @return aryLblHolidayGiving
	 */
	public String[] getAryLblHolidayGiving() {
		return getStringArrayClone(aryLblHolidayGiving);
	}
	
	/**
	 * @param aryLblHolidayGiving セットする aryLblHolidayGiving
	 */
	public void setAryLblHolidayGiving(String[] aryLblHolidayGiving) {
		this.aryLblHolidayGiving = getStringArrayClone(aryLblHolidayGiving);
	}
	
	/**
	 * @return aryLblHolidayLimit
	 */
	public String[] getAryLblHolidayLimit() {
		return getStringArrayClone(aryLblHolidayLimit);
	}
	
	/**
	 * @param aryLblHolidayLimit セットする aryLblHolidayLimit
	 */
	public void setAryLblHolidayLimit(String[] aryLblHolidayLimit) {
		this.aryLblHolidayLimit = getStringArrayClone(aryLblHolidayLimit);
	}
	
	/**
	 * @return aryLblHolidayContinue
	 */
	public String[] getAryLblHolidayContinue() {
		return getStringArrayClone(aryLblHolidayContinue);
	}
	
	/**
	 * @param aryLblHolidayContinue セットする aryLblHolidayContinue
	 */
	public void setAryLblHolidayContinue(String[] aryLblHolidayContinue) {
		this.aryLblHolidayContinue = getStringArrayClone(aryLblHolidayContinue);
	}
	
	/**
	 * @return aryLblTimelyHoliday
	 */
	public String[] getAryLblTimelyHoliday() {
		return getStringArrayClone(aryLblTimelyHoliday);
	}
	
	/**
	 * @param aryLblTimelyHoliday セットする aryLblTimelyHoliday
	 */
	public void setAryLblTimelyHoliday(String[] aryLblTimelyHoliday) {
		this.aryLblTimelyHoliday = getStringArrayClone(aryLblTimelyHoliday);
	}
	
	/**
	 * @return aryLblHolidaySalary
	 */
	public String[] getAryLblHolidaySalary() {
		return getStringArrayClone(aryLblHolidaySalary);
	}
	
	/**
	 * @param aryLblHolidaySalary セットする aryLblHolidaySalary
	 */
	public void setAryLblHolidaySalary(String[] aryLblHolidaySalary) {
		this.aryLblHolidaySalary = getStringArrayClone(aryLblHolidaySalary);
	}
	
	/**
	 * @return pltSearchHolidayType
	 */
	public String getPltSearchHolidayType() {
		return pltSearchHolidayType;
	}
	
	/**
	 * @param pltSearchHolidayType セットする pltSearchHolidayType
	 */
	public void setPltSearchHolidayType(String pltSearchHolidayType) {
		this.pltSearchHolidayType = pltSearchHolidayType;
	}
	
	/**
	 * @return ckbNoLimit
	 */
	public String getCkbNoLimit() {
		return ckbNoLimit;
	}
	
	/**
	 * @param ckbNoLimit セットする ckbNoLimit
	 */
	public void setCkbNoLimit(String ckbNoLimit) {
		this.ckbNoLimit = ckbNoLimit;
	}
	
	/**
	 * @return txtEditHolidayCode
	 */
	public String getTxtEditHolidayCode() {
		return txtEditHolidayCode;
	}
	
	/**
	 * @param txtEditHolidayCode セットする txtEditHolidayCode
	 */
	public void setTxtEditHolidayCode(String txtEditHolidayCode) {
		this.txtEditHolidayCode = txtEditHolidayCode;
	}
	
	/**
	 * @return txtEditHolidayLimitMonth
	 */
	public String getTxtEditHolidayLimitMonth() {
		return txtEditHolidayLimitMonth;
	}
	
	/**
	 * @param txtEditHolidayLimitMonth セットする txtEditHolidayLimitMonth
	 */
	public void setTxtEditHolidayLimitMonth(String txtEditHolidayLimitMonth) {
		this.txtEditHolidayLimitMonth = txtEditHolidayLimitMonth;
	}
	
	/**
	 * @return pltEditPaidHolidayCalc
	 */
	public String getPltEditPaidHolidayCalc() {
		return pltEditPaidHolidayCalc;
	}
	
	/**
	 * @param pltEditPaidHolidayCalc セットする pltEditPaidHolidayCalc
	 */
	public void setPltEditPaidHolidayCalc(String pltEditPaidHolidayCalc) {
		this.pltEditPaidHolidayCalc = pltEditPaidHolidayCalc;
	}
	
	/**
	 * @return pltEditHourlyHoliday
	 */
	public String getPltEditHourlyHoliday() {
		return pltEditHourlyHoliday;
	}
	
	/**
	 * @param pltEditHourlyHoliday セットする pltEditHourlyHoliday
	 */
	public void setPltEditHourlyHoliday(String pltEditHourlyHoliday) {
		this.pltEditHourlyHoliday = pltEditHourlyHoliday;
	}
	
}
