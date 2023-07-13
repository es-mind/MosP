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
 * 部下一覧の情報を格納する。
 */
public class SubordinateListVo extends TimeSettingVo {
	
	private static final long	serialVersionUID	= -2092258650689243780L;
	
	private String				pltSearchRequestYear;
	private String				pltSearchRequestMonth;
	private String				txtSearchEmployeeCode;
	private String				txtSearchEmployeeName;
	private String				pltSearchWorkPlace;
	private String				pltSearchEmployment;
	private String				pltSearchSection;
	private String				pltSearchPosition;
	private String				pltSearchApproval;
	private String				pltSearchCalc;
	// 申請者・部下検索プルダウン
	private String				pltSearchHumanType;
	
	private String[]			aryLblEmployeeCode;
	private String[]			aryLblEmployeeName;
	private String[]			aryLblSection;
	private String[]			aryLblWorkDate;
	private String[]			aryLblWorkTime;
	private String[]			aryLblRestTime;
	private String[]			aryLblPrivateTime;
	private String[]			aryLblLateTime;
	private String[]			aryLblLeaveEarlyTime;
	private String[]			aryLblLateLeaveEarlyTime;
	private String[]			aryLblOverTimeIn;
	private String[]			aryLblOverTimeOut;
	private String[]			aryLblWorkOnHolidayTime;
	private String[]			aryLblLateNightTime;
	private String[]			aryLblPaidHoliday;
	private String[]			aryLblAllHoliday;
	private String[]			aryLblAbsence;
	private String[]			aryLblApploval;
	private String[]			aryLblCalc;
	private String[]			aryLblCorrection;
	private String[]			aryOvertimeOutStyle;
	
	private String[][]			aryPltRequestYear;
	private String[][]			aryPltRequestMonth;
	private String[][]			aryPltWorkPlace;
	private String[][]			aryPltEmployment;
	private String[][]			aryPltSection;
	private String[][]			aryPltPosition;
	private String[][]			aryPltApproval;
	private String[][]			aryPltCalc;
	private String[][]			aryPltHumanType;
	
	private String[]			claApploval;
	private String[]			claCalc;
	
	private boolean				jsSearchConditionRequired;
	
	// 未承認検索項目：前日チェックボックス
	private String				ckbYesterday;
	
	/**
	 * 表示コマンド。<br>
	 * 上司モード(部下一覧)か承認者モード(社員別勤怠承認)かを
	 * 判別するのに用いる。<br>
	 */
	private String				showCommand;
	
	
	/**
	 * @return pltSearchRequestYear
	 */
	public String getPltSearchRequestYear() {
		return pltSearchRequestYear;
	}
	
	/**
	 * @return pltSearchRequestMonth
	 */
	public String getPltSearchRequestMonth() {
		return pltSearchRequestMonth;
	}
	
	/**
	 * @return txtSearchEmployeeCode
	 */
	@Override
	public String getTxtSearchEmployeeCode() {
		return txtSearchEmployeeCode;
	}
	
	/**
	 * @return txtSearchEmployeeName
	 */
	public String getTxtSearchEmployeeName() {
		return txtSearchEmployeeName;
	}
	
	/**
	 * @return pltSearchWorkPlace
	 */
	public String getPltSearchWorkPlace() {
		return pltSearchWorkPlace;
	}
	
	/**
	 * @return pltSearchEmployment
	 */
	public String getPltSearchEmployment() {
		return pltSearchEmployment;
	}
	
	/**
	 * @return pltSearchSection
	 */
	public String getPltSearchSection() {
		return pltSearchSection;
	}
	
	/**
	 * @return pltSearchPosition
	 */
	public String getPltSearchPosition() {
		return pltSearchPosition;
	}
	
	/**
	 * @return pltSearchApproval
	 */
	public String getPltSearchApproval() {
		return pltSearchApproval;
	}
	
	/**
	 * @return pltSearchCalc
	 */
	public String getPltSearchCalc() {
		return pltSearchCalc;
	}
	
	/**
	 * @return pltSearchHumanType
	 */
	public String getPltSearchHumanType() {
		return pltSearchHumanType;
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
	 * @param idx インデックス
	 * @return aryLblEmployeeName
	 */
	public String getAryLblEmployeeName(int idx) {
		return aryLblEmployeeName[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblSection
	 */
	public String getAryLblSection(int idx) {
		return aryLblSection[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkDate
	 */
	public String getAryLblWorkDate(int idx) {
		return aryLblWorkDate[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkTime
	 */
	public String getAryLblWorkTime(int idx) {
		return aryLblWorkTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblRestTime(int idx) {
		return aryLblRestTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblPrivateTime
	 */
	public String getAryLblPrivateTime(int idx) {
		return aryLblPrivateTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateTime
	 */
	public String getAryLblLateTime(int idx) {
		return aryLblLateTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLeaveEarlyTime
	 */
	public String getAryLblLeaveEarlyTime(int idx) {
		return aryLblLeaveEarlyTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateLeaveEarlyTime
	 */
	public String getAryLblLateLeaveEarlyTime(int idx) {
		return aryLblLateLeaveEarlyTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblOverTimeIn
	 */
	public String getAryLblOverTimeIn(int idx) {
		return aryLblOverTimeIn[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblOverTimeOut
	 */
	public String getAryLblOverTimeOut(int idx) {
		return aryLblOverTimeOut[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkOnHolidayTime
	 */
	public String getAryLblWorkOnHolidayTime(int idx) {
		return aryLblWorkOnHolidayTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateNightTime
	 */
	public String getAryLblLateNightTime(int idx) {
		return aryLblLateNightTime[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblPaidHoliday
	 */
	public String getAryLblPaidHoliday(int idx) {
		return aryLblPaidHoliday[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblAllHoliday
	 */
	public String getAryLblAllHoliday(int idx) {
		return aryLblAllHoliday[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblAbsence
	 */
	public String getAryLblAbsence(int idx) {
		return aryLblAbsence[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblApploval
	 */
	public String getAryLblApploval(int idx) {
		return aryLblApploval[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblCalc
	 */
	public String getAryLblCalc(int idx) {
		return aryLblCalc[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblCorrection
	 */
	public String getAryLblCorrection(int idx) {
		return aryLblCorrection[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return aryOvertimeOutStyle
	 */
	public String getAryOvertimeOutStyle(int idx) {
		return aryOvertimeOutStyle[idx];
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
	 * @return aryPltApproval
	 */
	public String[][] getAryPltApproval() {
		return getStringArrayClone(aryPltApproval);
	}
	
	/**
	 * @return aryPltCalc
	 */
	public String[][] getAryPltCalc() {
		return getStringArrayClone(aryPltCalc);
	}
	
	/**
	 * @return aryPltHumanType
	 */
	public String[][] getAryPltHumanType() {
		return getStringArrayClone(aryPltHumanType);
	}
	
	/**
	 * @param pltSearchRequestYear セットする pltSearchRequestYear
	 */
	public void setPltSearchRequestYear(String pltSearchRequestYear) {
		this.pltSearchRequestYear = pltSearchRequestYear;
	}
	
	/**
	 * @param pltSearchRequestMonth セットする pltSearchRequestMonth
	 */
	public void setPltSearchRequestMonth(String pltSearchRequestMonth) {
		this.pltSearchRequestMonth = pltSearchRequestMonth;
	}
	
	/**
	 * @param txtSearchEmployeeCode セットする txtSearchEmployeeCode
	 */
	@Override
	public void setTxtSearchEmployeeCode(String txtSearchEmployeeCode) {
		this.txtSearchEmployeeCode = txtSearchEmployeeCode;
	}
	
	/**
	 * @param txtSearchEmployeeName セットする txtSearchEmployeeName
	 */
	public void setTxtSearchEmployeeName(String txtSearchEmployeeName) {
		this.txtSearchEmployeeName = txtSearchEmployeeName;
	}
	
	/**
	 * @param pltSearchWorkPlace セットする pltSearchWorkPlace
	 */
	public void setPltSearchWorkPlace(String pltSearchWorkPlace) {
		this.pltSearchWorkPlace = pltSearchWorkPlace;
	}
	
	/**
	 * @param pltSearchEmployment セットする pltSearchEmployment
	 */
	public void setPltSearchEmployment(String pltSearchEmployment) {
		this.pltSearchEmployment = pltSearchEmployment;
	}
	
	/**
	 * @param pltSearchSection セットする pltSearchSection
	 */
	public void setPltSearchSection(String pltSearchSection) {
		this.pltSearchSection = pltSearchSection;
	}
	
	/**
	 * @param pltSearchPosition セットする pltSearchPosition
	 */
	public void setPltSearchPosition(String pltSearchPosition) {
		this.pltSearchPosition = pltSearchPosition;
	}
	
	/**
	 * @param pltSearchApproval セットする pltSearchApproval
	 */
	public void setPltSearchApproval(String pltSearchApproval) {
		this.pltSearchApproval = pltSearchApproval;
	}
	
	/**
	 * @param pltSearchCalc セットする pltSearchCalc
	 */
	public void setPltSearchCalc(String pltSearchCalc) {
		this.pltSearchCalc = pltSearchCalc;
	}
	
	/**
	 * @param pltSearchHumanType セットする pltSearchHumanType
	 */
	public void setPltSearchHumanType(String pltSearchHumanType) {
		this.pltSearchHumanType = pltSearchHumanType;
	}
	
	/**
	 * @param aryLblEmployeeCode セットする aryLblEmployeeCode
	 */
	public void setAryLblEmployeeCode(String[] aryLblEmployeeCode) {
		this.aryLblEmployeeCode = getStringArrayClone(aryLblEmployeeCode);
	}
	
	/**
	 * @param aryLblEmployeeName セットする aryLblEmployeeName
	 */
	public void setAryLblEmployeeName(String[] aryLblEmployeeName) {
		this.aryLblEmployeeName = getStringArrayClone(aryLblEmployeeName);
	}
	
	/**
	 * @param aryLblSection セットする aryLblSection
	 */
	public void setAryLblSection(String[] aryLblSection) {
		this.aryLblSection = getStringArrayClone(aryLblSection);
	}
	
	/**
	 * @param aryLblWorkDate セットする aryLblWorkDate
	 */
	public void setAryLblWorkDate(String[] aryLblWorkDate) {
		this.aryLblWorkDate = getStringArrayClone(aryLblWorkDate);
	}
	
	/**
	 * @param aryLblWorkTime セットする aryLblWorkTime
	 */
	public void setAryLblWorkTime(String[] aryLblWorkTime) {
		this.aryLblWorkTime = getStringArrayClone(aryLblWorkTime);
	}
	
	/**
	 * @param aryLblRestTime セットする aryLblRestTime
	 */
	public void setAryLblRestTime(String[] aryLblRestTime) {
		this.aryLblRestTime = getStringArrayClone(aryLblRestTime);
	}
	
	/**
	 * @param aryLblPrivateTime セットする aryLblPrivateTime
	 */
	public void setAryLblPrivateTime(String[] aryLblPrivateTime) {
		this.aryLblPrivateTime = getStringArrayClone(aryLblPrivateTime);
	}
	
	/**
	 * @param aryLblLateTime セットする aryLblLateTime
	 */
	public void setAryLblLateTime(String[] aryLblLateTime) {
		this.aryLblLateTime = getStringArrayClone(aryLblLateTime);
	}
	
	/**
	 * @param aryLblLeaveEarlyTime セットする aryLblLeaveEarlyTime
	 */
	public void setAryLblLeaveEarlyTime(String[] aryLblLeaveEarlyTime) {
		this.aryLblLeaveEarlyTime = getStringArrayClone(aryLblLeaveEarlyTime);
	}
	
	/**
	 * @param aryLblLateLeaveEarlyTime セットする aryLblLateLeaveEarlyTime
	 */
	public void setAryLblLateLeaveEarlyTime(String[] aryLblLateLeaveEarlyTime) {
		this.aryLblLateLeaveEarlyTime = getStringArrayClone(aryLblLateLeaveEarlyTime);
	}
	
	/**
	 * @param aryLblOverTimeIn セットする aryLblOverTimeIn
	 */
	public void setAryLblOverTimeIn(String[] aryLblOverTimeIn) {
		this.aryLblOverTimeIn = getStringArrayClone(aryLblOverTimeIn);
	}
	
	/**
	 * @param aryLblOverTimeOut セットする aryLblOverTimeOut
	 */
	public void setAryLblOverTimeOut(String[] aryLblOverTimeOut) {
		this.aryLblOverTimeOut = getStringArrayClone(aryLblOverTimeOut);
	}
	
	/**
	 * @param aryLblWorkOnHolidayTime セットする aryLblWorkOnHolidayTime
	 */
	public void setAryLblWorkOnHolidayTime(String[] aryLblWorkOnHolidayTime) {
		this.aryLblWorkOnHolidayTime = getStringArrayClone(aryLblWorkOnHolidayTime);
	}
	
	/**
	 * @param aryLblLateNightTime セットする aryLblLateNightTime
	 */
	public void setAryLblLateNightTime(String[] aryLblLateNightTime) {
		this.aryLblLateNightTime = getStringArrayClone(aryLblLateNightTime);
	}
	
	/**
	 * @param aryLblPaidHoliday セットする aryLblPaidHoliday
	 */
	public void setAryLblPaidHoliday(String[] aryLblPaidHoliday) {
		this.aryLblPaidHoliday = getStringArrayClone(aryLblPaidHoliday);
	}
	
	/**
	 * @param aryLblAllHoliday セットする aryLblAllHoliday
	 */
	public void setAryLblAllHoliday(String[] aryLblAllHoliday) {
		this.aryLblAllHoliday = getStringArrayClone(aryLblAllHoliday);
	}
	
	/**
	 * @param aryLblAbsence セットする aryLblAbsence
	 */
	public void setAryLblAbsence(String[] aryLblAbsence) {
		this.aryLblAbsence = getStringArrayClone(aryLblAbsence);
	}
	
	/**
	 * @param aryLblApploval セットする aryLblApploval
	 */
	public void setAryLblApploval(String[] aryLblApploval) {
		this.aryLblApploval = getStringArrayClone(aryLblApploval);
	}
	
	/**
	 * @param aryLblCalc セットする aryLblCalc
	 */
	public void setAryLblCalc(String[] aryLblCalc) {
		this.aryLblCalc = getStringArrayClone(aryLblCalc);
	}
	
	/**
	 * @param aryLblCorrection セットする aryLblCorrection
	 */
	public void setAryLblCorrection(String[] aryLblCorrection) {
		this.aryLblCorrection = getStringArrayClone(aryLblCorrection);
	}
	
	/**
	 * @param aryOvertimeOutStyle セットする aryOvertimeOutStyle
	 */
	public void setAryOvertimeOutStyle(String[] aryOvertimeOutStyle) {
		this.aryOvertimeOutStyle = getStringArrayClone(aryOvertimeOutStyle);
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
	 * @param aryPltApproval セットする aryPltApproval
	 */
	public void setAryPltApproval(String[][] aryPltApproval) {
		this.aryPltApproval = getStringArrayClone(aryPltApproval);
	}
	
	/**
	 * @param aryPltCalc セットする aryPltCalc
	 */
	public void setAryPltCalc(String[][] aryPltCalc) {
		this.aryPltCalc = getStringArrayClone(aryPltCalc);
	}
	
	/**
	 * @param aryPltHumanType セットする aryPltHumanType
	 */
	public void setAryPltHumanType(String[][] aryPltHumanType) {
		this.aryPltHumanType = getStringArrayClone(aryPltHumanType);
	}
	
	/**
	 * @param idx インデックス
	 * @return claApploval
	 */
	public String getClaApploval(int idx) {
		return claApploval[idx];
	}
	
	/**
	 * @param idx インデックス
	 * @return claCalc
	 */
	public String getClaCalc(int idx) {
		return claCalc[idx];
	}
	
	/**
	 * @param claApploval セットする claApploval
	 */
	public void setClaApploval(String[] claApploval) {
		this.claApploval = getStringArrayClone(claApploval);
	}
	
	/**
	 * @param claCalc セットする claCalc
	 */
	public void setClaCalc(String[] claCalc) {
		this.claCalc = getStringArrayClone(claCalc);
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
	 * @return showCommand
	 */
	public String getShowCommand() {
		return showCommand;
	}
	
	/**
	 * @param showCommand セットする showCommand
	 */
	public void setShowCommand(String showCommand) {
		this.showCommand = showCommand;
	}
	
	/**
	 * @return ckbYesterday
	 */
	public String getCkbYesterday() {
		return ckbYesterday;
	}
	
	/**
	 * @param ckbYesterday セットする ckbYesterday
	 */
	public void setCkbYesterday(String ckbYesterday) {
		this.ckbYesterday = ckbYesterday;
	}
	
}
