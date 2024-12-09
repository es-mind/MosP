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
package jp.mosp.time.calculation.vo;

import jp.mosp.time.base.TotalTimeBaseVo;

/**
 * 勤怠集計結果一覧の情報を格納する。
 */
public class TotalTimeListVo extends TotalTimeBaseVo {
	
	private static final long	serialVersionUID	= 5688539242169607126L;
	
	// 社員コード(from)
	private String				txtEditFromEmployeeCode;
	// 社員コード(to)
	private String				txtEditToEmployeeCode;
	private String				txtEditEmployeeName;
	private String				pltEditWorkPlace;
	private String				pltEditEmployment;
	private String				pltEditSection;
	private String				pltEditPosition;
	private String				pltEditApproval;
	private String				pltEditCalc;
	private String[]			aryPersonalId;
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
	private String[]			aryLblApploval;
	private String[]			aryLblCalc;
	private String[]			aryLblPaidHoliday;
	private String[]			aryLblAllHoliday;
	private String[]			aryLblAbsence;
	private String[]			aryLblCorrection;
	
	private String[][]			aryPltEditWorkPlace;
	private String[][]			aryPltEditEmployment;
	private String[][]			aryPltEditSection;
	private String[][]			aryPltEditPosition;
	private String[][]			aryPltEditApproval;
	private String[][]			aryPltEditCalc;
	
	private String[]			claApploval;
	private String[]			claCalc;
	
	private boolean[]			aryNeedDetail;
	
	private String				jsCutoffState;
	
	private boolean				jsSearchConditionRequired;
	
	// 未承認検索項目：前日チェックボックス
	private String				ckbYesterday;
	
	
	/**
	 * @return txtEditFromEmployeeCode
	 */
	public String getTxtEditFromEmployeeCode() {
		return txtEditFromEmployeeCode;
	}
	
	/**
	 * @param txtEditFromEmployeeCode セットする txtEditFromEmployeeCode
	 */
	public void setTxtEditFromEmployeeCode(String txtEditFromEmployeeCode) {
		this.txtEditFromEmployeeCode = txtEditFromEmployeeCode;
	}
	
	/**
	 * @return txtEditToEmployeeCode
	 */
	public String getTxtEditToEmployeeCode() {
		return txtEditToEmployeeCode;
	}
	
	/**
	 * @param txtEditToEmployeeCode セットする txtEditToEmployeeCode
	 */
	public void setTxtEditToEmployeeCode(String txtEditToEmployeeCode) {
		this.txtEditToEmployeeCode = txtEditToEmployeeCode;
	}
	
	/**
	 * @return txtEditEmployeeName
	 */
	public String getTxtEditEmployeeName() {
		return txtEditEmployeeName;
	}
	
	/**
	 * @param txtEditEmployeeName セットする txtEditEmployeeName
	 */
	public void setTxtEditEmployeeName(String txtEditEmployeeName) {
		this.txtEditEmployeeName = txtEditEmployeeName;
	}
	
	/**
	 * @return pltEditWorkPlace
	 */
	public String getPltEditWorkPlace() {
		return pltEditWorkPlace;
	}
	
	/**
	 * @param pltEditWorkPlace セットする pltEditWorkPlace
	 */
	public void setPltEditWorkPlace(String pltEditWorkPlace) {
		this.pltEditWorkPlace = pltEditWorkPlace;
	}
	
	/**
	 * @return pltEditEmployment
	 */
	public String getPltEditEmployment() {
		return pltEditEmployment;
	}
	
	/**
	 * @param pltEditEmployment セットする pltEditEmployment
	 */
	public void setPltEditEmployment(String pltEditEmployment) {
		this.pltEditEmployment = pltEditEmployment;
	}
	
	/**
	 * @return pltEditSection
	 */
	public String getPltEditSection() {
		return pltEditSection;
	}
	
	/**
	 * @param pltEditSection セットする pltEditSection
	 */
	public void setPltEditSection(String pltEditSection) {
		this.pltEditSection = pltEditSection;
	}
	
	/**
	 * @return pltEditPosition
	 */
	public String getPltEditPosition() {
		return pltEditPosition;
	}
	
	/**
	 * @param pltEditPosition セットする pltEditPosition
	 */
	public void setPltEditPosition(String pltEditPosition) {
		this.pltEditPosition = pltEditPosition;
	}
	
	/**
	 * @return pltEditApproval
	 */
	public String getPltEditApproval() {
		return pltEditApproval;
	}
	
	/**
	 * @param pltEditApproval セットする pltEditApproval
	 */
	public void setPltEditApproval(String pltEditApproval) {
		this.pltEditApproval = pltEditApproval;
	}
	
	/**
	 * @return pltEditCalc
	 */
	public String getPltEditCalc() {
		return pltEditCalc;
	}
	
	/**
	 * @param pltEditCalc セットする pltEditCalc
	 */
	public void setPltEditCalc(String pltEditCalc) {
		this.pltEditCalc = pltEditCalc;
	}
	
	/**
	 * @param idx インデックス
	 * @return personalId
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
	 * @param idx インデックス
	 * @return aryLblSection
	 */
	public String getAryLblSection(int idx) {
		return aryLblSection[idx];
	}
	
	/**
	 * @return aryLblWorkDate
	 */
	public String[] getAryLblWorkDate() {
		return getStringArrayClone(aryLblWorkDate);
	}
	
	/**
	 * @param aryLblWorkDate セットする aryLblWorkDate
	 */
	public void setAryLblWorkDate(String[] aryLblWorkDate) {
		this.aryLblWorkDate = getStringArrayClone(aryLblWorkDate);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkDate
	 */
	public String getAryLblWorkDate(int idx) {
		return aryLblWorkDate[idx];
	}
	
	/**
	 * @return aryLblWorkTime
	 */
	public String[] getAryLblWorkTime() {
		return getStringArrayClone(aryLblWorkTime);
	}
	
	/**
	 * @param aryLblWorkTime セットする aryLblWorkTime
	 */
	public void setAryLblWorkTime(String[] aryLblWorkTime) {
		this.aryLblWorkTime = getStringArrayClone(aryLblWorkTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblWorkTime
	 */
	public String getAryLblWorkTime(int idx) {
		return aryLblWorkTime[idx];
	}
	
	/**
	 * @return aryLblRestTime
	 */
	public String[] getAryLblRestTime() {
		return getStringArrayClone(aryLblRestTime);
	}
	
	/**
	 * @param aryLblRestTime セットする aryLblRestTime
	 */
	public void setAryLblRestTime(String[] aryLblRestTime) {
		this.aryLblRestTime = getStringArrayClone(aryLblRestTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblRestTime(int idx) {
		return aryLblRestTime[idx];
	}
	
	/**
	 * @param aryLblPrivateTime セットする aryLblPrivateTime
	 */
	public void setAryLblPrivateTime(String[] aryLblPrivateTime) {
		this.aryLblPrivateTime = getStringArrayClone(aryLblPrivateTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblPrivateTime
	 */
	public String getAryLblPrivateTime(int idx) {
		return aryLblPrivateTime[idx];
	}
	
	/**
	 * @return aryLblLateTime
	 */
	public String[] getAryLblLateTime() {
		return getStringArrayClone(aryLblLateTime);
	}
	
	/**
	 * @param aryLblLateTime セットする aryLblLateTime
	 */
	public void setAryLblLateTime(String[] aryLblLateTime) {
		this.aryLblLateTime = getStringArrayClone(aryLblLateTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblLateTime(int idx) {
		return aryLblLateTime[idx];
	}
	
	/**
	 * @return aryLblLeaveEarlyTime
	 */
	public String[] getAryLblLeaveEarlyTime() {
		return getStringArrayClone(aryLblLeaveEarlyTime);
	}
	
	/**
	 * @param aryLblLeaveEarlyTime セットする aryLblLeaveEarlyTime
	 */
	public void setAryLblLeaveEarlyTime(String[] aryLblLeaveEarlyTime) {
		this.aryLblLeaveEarlyTime = getStringArrayClone(aryLblLeaveEarlyTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblLeaveEarlyTime(int idx) {
		return aryLblLeaveEarlyTime[idx];
	}
	
	/**
	 * @param aryLblLateLeaveEarlyTime セットする aryLblLateLeaveEarlyTime
	 */
	public void setAryLblLateLeaveEarlyTime(String[] aryLblLateLeaveEarlyTime) {
		this.aryLblLateLeaveEarlyTime = getStringArrayClone(aryLblLateLeaveEarlyTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblLateLeaveEarlyTime
	 */
	public String getAryLblLateLeaveEarlyTime(int idx) {
		return aryLblLateLeaveEarlyTime[idx];
	}
	
	/**
	 * @return aryLblOverTimeIn
	 */
	public String[] getAryLblOverTimeIn() {
		return getStringArrayClone(aryLblOverTimeIn);
	}
	
	/**
	 * @param aryLblOverTimeIn セットする aryLblOverTimeIn
	 */
	public void setAryLblOverTimeIn(String[] aryLblOverTimeIn) {
		this.aryLblOverTimeIn = getStringArrayClone(aryLblOverTimeIn);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblOverTimeIn(int idx) {
		return aryLblOverTimeIn[idx];
	}
	
	/**
	 * @return aryLblOverTimeOut
	 */
	public String[] getAryLblOverTimeOut() {
		return getStringArrayClone(aryLblOverTimeOut);
	}
	
	/**
	 * @param aryLblOverTimeOut セットする aryLblOverTimeOut
	 */
	public void setAryLblOverTimeOut(String[] aryLblOverTimeOut) {
		this.aryLblOverTimeOut = getStringArrayClone(aryLblOverTimeOut);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblOverTimeOut(int idx) {
		return aryLblOverTimeOut[idx];
	}
	
	/**
	 * @return aryLblWorkOnHolidayTime
	 */
	public String[] getAryLblWorkOnHolidayTime() {
		return getStringArrayClone(aryLblWorkOnHolidayTime);
	}
	
	/**
	 * @param aryLblWorkOnHolidayTime セットする aryLblWorkOnHolidayTime
	 */
	public void setAryLblWorkOnHolidayTime(String[] aryLblWorkOnHolidayTime) {
		this.aryLblWorkOnHolidayTime = getStringArrayClone(aryLblWorkOnHolidayTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblWorkOnHolidayTime(int idx) {
		return aryLblWorkOnHolidayTime[idx];
	}
	
	/**
	 * @return aryLblLateNightTime
	 */
	public String[] getAryLblLateNightTime() {
		return getStringArrayClone(aryLblLateNightTime);
	}
	
	/**
	 * @param aryLblLateNightTime セットする aryLblLateNightTime
	 */
	public void setAryLblLateNightTime(String[] aryLblLateNightTime) {
		this.aryLblLateNightTime = getStringArrayClone(aryLblLateNightTime);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblLateNightTime(int idx) {
		return aryLblLateNightTime[idx];
	}
	
	/**
	 * @return aryLblApploval
	 */
	public String[] getAryLblApploval() {
		return getStringArrayClone(aryLblApploval);
	}
	
	/**
	 * @param aryLblApploval セットする aryLblApploval
	 */
	public void setAryLblApploval(String[] aryLblApploval) {
		this.aryLblApploval = getStringArrayClone(aryLblApploval);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblApploval(int idx) {
		return aryLblApploval[idx];
	}
	
	/**
	 * @return aryLblCalc
	 */
	public String[] getAryLblCalc() {
		return getStringArrayClone(aryLblCalc);
	}
	
	/**
	 * @param aryLblCalc セットする aryLblCalc
	 */
	public void setAryLblCalc(String[] aryLblCalc) {
		this.aryLblCalc = getStringArrayClone(aryLblCalc);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblRestTime
	 */
	public String getAryLblCalc(int idx) {
		return aryLblCalc[idx];
	}
	
	/**
	 * @return aryPltEditWorkPlace
	 */
	public String[][] getAryPltEditWorkPlace() {
		return getStringArrayClone(aryPltEditWorkPlace);
	}
	
	/**
	 * @param aryPltEditWorkPlace セットする aryPltEditWorkPlace
	 */
	public void setAryPltEditWorkPlace(String[][] aryPltEditWorkPlace) {
		this.aryPltEditWorkPlace = getStringArrayClone(aryPltEditWorkPlace);
	}
	
	/**
	 * @return aryPltEditEmployment
	 */
	public String[][] getAryPltEditEmployment() {
		return getStringArrayClone(aryPltEditEmployment);
	}
	
	/**
	 * @param aryPltEditEmployment セットする aryPltEditEmployment
	 */
	public void setAryPltEditEmployment(String[][] aryPltEditEmployment) {
		this.aryPltEditEmployment = getStringArrayClone(aryPltEditEmployment);
	}
	
	/**
	 * @return aryPltEditSection
	 */
	public String[][] getAryPltEditSection() {
		return getStringArrayClone(aryPltEditSection);
	}
	
	/**
	 * @param aryPltEditSection セットする aryPltEditSection
	 */
	public void setAryPltEditSection(String[][] aryPltEditSection) {
		this.aryPltEditSection = getStringArrayClone(aryPltEditSection);
	}
	
	/**
	 * @return aryPltEditPosition
	 */
	public String[][] getAryPltEditPosition() {
		return getStringArrayClone(aryPltEditPosition);
	}
	
	/**
	 * @param aryPltEditPosition セットする aryPltEditPosition
	 */
	public void setAryPltEditPosition(String[][] aryPltEditPosition) {
		this.aryPltEditPosition = getStringArrayClone(aryPltEditPosition);
	}
	
	/**
	 * @return aryPltEditApproval
	 */
	public String[][] getAryPltEditApproval() {
		return getStringArrayClone(aryPltEditApproval);
	}
	
	/**
	 * @param aryPltEditApproval セットする aryPltEditApproval
	 */
	public void setAryPltEditApproval(String[][] aryPltEditApproval) {
		this.aryPltEditApproval = getStringArrayClone(aryPltEditApproval);
	}
	
	/**
	 * @return aryPltEditCalc
	 */
	public String[][] getAryPltEditCalc() {
		return getStringArrayClone(aryPltEditCalc);
	}
	
	/**
	 * @param aryPltEditCalc セットする aryPltEditCalc
	 */
	public void setAryPltEditCalc(String[][] aryPltEditCalc) {
		this.aryPltEditCalc = getStringArrayClone(aryPltEditCalc);
	}
	
	/**
	 * @return aryLblPaidHoliday
	 */
	public String[] getAryLblPaidHoliday() {
		return getStringArrayClone(aryLblPaidHoliday);
	}
	
	/**
	 * @return aryLblAllHoliday
	 */
	public String[] getAryLblAllHoliday() {
		return getStringArrayClone(aryLblAllHoliday);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblAllHoliday
	 */
	public String getAryLblAllHoliday(int idx) {
		return aryLblAllHoliday[idx];
	}
	
	/**
	 * @return aryLblAbsence
	 */
	public String[] getAryLblAbsence() {
		return getStringArrayClone(aryLblAbsence);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryLblAbsence
	 */
	public String getAryLblAbsence(int idx) {
		return aryLblAbsence[idx];
	}
	
	/**
	 * @return aryLblCorrection
	 */
	public String[] getAryLblCorrection() {
		return getStringArrayClone(aryLblCorrection);
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
	 * @param aryLblCorrection セットする aryLblCorrection
	 */
	public void setAryLblCorrection(String[] aryLblCorrection) {
		this.aryLblCorrection = getStringArrayClone(aryLblCorrection);
	}
	
	/**
	 * @return claApploval
	 */
	public String[] getClaApploval() {
		return getStringArrayClone(claApploval);
	}
	
	/**
	 * @return claCalc
	 */
	public String[] getClaCalc() {
		return getStringArrayClone(claCalc);
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
	 * @return jsCutoffState
	 */
	public String getJsCutoffState() {
		return jsCutoffState;
	}
	
	/**
	 * @param jsCutoffState セットする jsCutoffState
	 */
	public void setJsCutoffState(String jsCutoffState) {
		this.jsCutoffState = jsCutoffState;
	}
	
	/**
	 * @param aryNeedDetail セットする aryNeedDetail
	 */
	public void setAryNeedDetail(boolean[] aryNeedDetail) {
		this.aryNeedDetail = getBooleanArrayClone(aryNeedDetail);
	}
	
	/**
	 * @return aryNeedDetail
	 */
	public boolean[] getAryNeedDetail() {
		return getBooleanArrayClone(aryNeedDetail);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryNeedDetail
	 */
	public boolean getAryNeedDetail(int idx) {
		return aryNeedDetail[idx];
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
