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

import jp.mosp.framework.js.DirectJs;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.time.base.TotalTimeBaseVo;

/**
 * 勤怠修正の情報を格納する。
 */
public class TotalTimeCardVo extends TotalTimeBaseVo {
	
	private static final long	serialVersionUID	= 2948705885220041246L;
	
	private String				lblMonth;
	private String				lblCorrectionHistory;
	private String				txtCorrectionReason;
	
	private String				txtWorkTimeHour;
	private String				txtWorkTimeMinute;
	private String				txtSpecificWorkTimeHour;
	private String				txtSpecificWorkTimeMinute;
	private String				txtTimesWorkDate;
	private String				txtTimesWork;
	private String				txtLegalWorkOnHoliday;
	private String				txtSpecificWorkOnHoliday;
	private String				txtUnpaidShortTimeHour;
	private String				txtUnpaidShortTimeMinute;
	
	private String				txtTimesNonstop;
	private String				txtTimesNoreturn;
	
	private String				txtRestTimeHour;
	private String				txtRestTimeMinute;
	private String				txtRestLateNightHour;
	private String				txtRestLateNightMinute;
	private String				txtRestWorkOnSpecificHour;
	private String				txtRestWorkOnSpecificMinute;
	private String				txtRestWorkOnLegalHour;
	private String				txtRestWorkOnLegalMinute;
	private String				txtPrivateHour;
	private String				txtPrivateMinute;
	private String				txtPublicHour;
	private String				txtPublicMinute;
	
	private String				txtOverTimeHour;
	private String				txtOverTimeMinute;
	private String				txtOverTimeInHour;
	private String				txtOverTimeInMinute;
	private String				txtOverTimeOutHour;
	private String				txtOverTimeOutMinute;
	private String				txtLateNightHour;
	private String				txtLateNightMinute;
	private String				txtWorkSpecificOnHolidayHour;
	private String				txtWorkSpecificOnHolidayMinute;
	private String				txtWorkOnHolidayHour;
	private String				txtWorkOnHolidayMinute;
	private String				txt45HourOverTimeHour;
	private String				txt45HourOverTimeMinute;
	private String				txtDecreaseTimeHour;
	private String				txtDecreaseTimeMinute;
	
	private String				txtLateDays;
	private String				txtLateThirtyMinutesOrMore;
	private String				txtLateLessThanThirtyMinutes;
	private String				txtLateTimeHour;
	private String				txtLateTimeMinute;
	private String				txtLateThirtyMinutesOrMoreTimeHour;
	private String				txtLateThirtyMinutesOrMoreTimeMinute;
	private String				txtLateLessThanThirtyMinutesTimeHour;
	private String				txtLateLessThanThirtyMinutesTimeMinute;
	private String				txtLeaveEarlyDays;
	private String				txtLeaveEarlyThirtyMinutesOrMore;
	private String				txtLeaveEarlyLessThanThirtyMinutes;
	private String				txtLeaveEarlyTimeHour;
	private String				txtLeaveEarlyTimeMinute;
	private String				txtLeaveEarlyThirtyMinutesOrMoreTimeHour;
	private String				txtLeaveEarlyThirtyMinutesOrMoreTimeMinute;
	private String				txtLeaveEarlyLessThanThirtyMinutesTimeHour;
	private String				txtLeaveEarlyLessThanThirtyMinutesTimeMinute;
	
	private String				txtTimesHoliday;
	private String				txtTimesLegalHoliday;
	private String				txtTimesSpecificHoliday;
	private String				txtTimesSubstitute;
	private String				txtTimesPaidHoliday;
	private String				txtPaidholidayHour;
	private String				txtTimesStockHoliday;
	private String				txtTimesCompensation;
	private String				txtTimesLegalCompensation;
	private String				txtTimesSpecificCompensation;
	private String				txtTimesLateCompensation;
	private String				txtTimesSpecificHolidaySubstitute;
	
	private String				txtLegalCompensationOccurred;
	private String				txtSpecificCompensationOccurred;
	private String				txtLateCompensationOccurred;
	
	private String				txtLegalCompensationUnused;
	private String				txtSpecificCompensationUnused;
	private String				txtLateCompensationUnused;
	
	private String[]			aryTxtTimesSpecialLeave;
	private String[]			aryTxtTimesSpecialHour;
	private String[]			aryTxtTimesSpecialLeaveTitle;
	private String[]			aryTxtTimesSpecialLeaveCode;
	private long[]				aryTimesSpecialLeaveId;
	private String				txtTotalSpecialHoliday;
	private String				txtSpecialHolidayHour;
	
	private String[]			aryTxtTimesOtherVacation;
	private String[]			aryTxtTimesOtherVacationHour;
	private String[]			aryTxtTimesOtherVacationTitle;
	private String[]			aryTxtTimesOtherVacationCode;
	private long[]				aryTimesOtherVacationId;
	private String				txtTotalOtherHoliday;
	private String				txtOtherHolidayHour;
	
	private String[]			aryTxtDeduction;
	private String[]			aryTxtDeductionHour;
	private String[]			aryTxtDeductionTitle;
	private String[]			aryTxtDeductionCode;
	private long[]				aryDeductionId;
	private String				txtTotalDeduction;
	private String				txtDeductionHour;
	
	private String				txt60HourOverTimeHour;
	private String				txt60HourOverTimeMinute;
	private String				txtWeekDayOverTimeHour;
	private String				txtWeekDayOverTimeMinute;
	private String				txtSpecificOverTimeHour;
	private String				txtSpecificOverTimeMiunte;
	private String				txtTimesAlternative;
	
	private String				lblEmployeeCode;
	private String				lblEmployeeName;
	private String				lblSection;
	
	private String				txtTimesLegalHolidaySubstitute;
	
	private long				tmdTotalTimeId;
	private long				tmdOvertimeRequestId;
	
	private String				txtTimesAchievement;
	private String				txtTimesTotalWorkDate;
	private String				txtTimesOvertime;
	private String				txtTimesWorkingHoliday;
	
	/**
	 * 締状態フラグ(true：確定、false：確定以外)。<br>
	 */
	@DirectJs
	private boolean				isTightened;
	
	
	/**
	 * 表示モードであるかを確認する。<br>
	 * @return 確認結果(true：表示モードである、false：修正モードである)
	 */
	public boolean isViewMode() {
		return MospUtility.isEqual(getModeCardEdit(), PlatformConst.MODE_CARD_EDIT_INSERT);
	}
	
	/**
	 * @return txtCorrectionReason
	 */
	public String getTxtCorrectionReason() {
		return txtCorrectionReason;
	}
	
	/**
	 * @param txtCorrectionReason セットする txtCorrectionReason
	 */
	public void setTxtCorrectionReason(String txtCorrectionReason) {
		this.txtCorrectionReason = txtCorrectionReason;
	}
	
	/**
	 * @return txtWorkTimeHour
	 */
	public String getTxtWorkTimeHour() {
		return txtWorkTimeHour;
	}
	
	/**
	 * @param txtWorkTimeHour セットする txtWorkTimeHour
	 */
	public void setTxtWorkTimeHour(String txtWorkTimeHour) {
		this.txtWorkTimeHour = txtWorkTimeHour;
	}
	
	/**
	 * @return txtWorkTimeMinute
	 */
	public String getTxtWorkTimeMinute() {
		return txtWorkTimeMinute;
	}
	
	/**
	 * @param txtWorkTimeMinute セットする txtWorkTimeMinute
	 */
	public void setTxtWorkTimeMinute(String txtWorkTimeMinute) {
		this.txtWorkTimeMinute = txtWorkTimeMinute;
	}
	
	/**
	 * @return txtSpecificWorkTimeHour
	 */
	public String getTxtSpecificWorkTimeHour() {
		return txtSpecificWorkTimeHour;
	}
	
	/**
	 * @param txtSpecificWorkTimeHour セットする txtSpecificWorkTimeHour
	 */
	public void setTxtSpecificWorkTimeHour(String txtSpecificWorkTimeHour) {
		this.txtSpecificWorkTimeHour = txtSpecificWorkTimeHour;
	}
	
	/**
	 * @return txtSpecificWorkTimeMinute
	 */
	public String getTxtSpecificWorkTimeMinute() {
		return txtSpecificWorkTimeMinute;
	}
	
	/**
	 * @param txtSpecificWorkTimeMinute セットする txtSpecificWorkTimeMinute
	 */
	public void setTxtSpecificWorkTimeMinute(String txtSpecificWorkTimeMinute) {
		this.txtSpecificWorkTimeMinute = txtSpecificWorkTimeMinute;
	}
	
	/**
	 * @return txtTimesWorkDate
	 */
	public String getTxtTimesWorkDate() {
		return txtTimesWorkDate;
	}
	
	/**
	 * @param txtTimesWorkDate セットする txtTimesWorkDate
	 */
	public void setTxtTimesWorkDate(String txtTimesWorkDate) {
		this.txtTimesWorkDate = txtTimesWorkDate;
	}
	
	/**
	 * @return txtTimesWork
	 */
	public String getTxtTimesWork() {
		return txtTimesWork;
	}
	
	/**
	 * @param txtTimesWork セットする txtTimesWork
	 */
	public void setTxtTimesWork(String txtTimesWork) {
		this.txtTimesWork = txtTimesWork;
	}
	
	/**
	 * @return txtLegalWorkOnHoliday
	 */
	public String getTxtLegalWorkOnHoliday() {
		return txtLegalWorkOnHoliday;
	}
	
	/**
	 * @param txtLegalWorkOnHoliday セットする txtLegalWorkOnHoliday
	 */
	public void setTxtLegalWorkOnHoliday(String txtLegalWorkOnHoliday) {
		this.txtLegalWorkOnHoliday = txtLegalWorkOnHoliday;
	}
	
	/**
	 * @return txtSpecificWorkOnHoliday
	 */
	public String getTxtSpecificWorkOnHoliday() {
		return txtSpecificWorkOnHoliday;
	}
	
	/**
	 * @param txtSpecificWorkOnHoliday セットする txtSpecificWorkOnHoliday
	 */
	public void setTxtSpecificWorkOnHoliday(String txtSpecificWorkOnHoliday) {
		this.txtSpecificWorkOnHoliday = txtSpecificWorkOnHoliday;
	}
	
	/**
	 * @return txtUnpaidShortTimeHour
	 */
	public String getTxtUnpaidShortTimeHour() {
		return txtUnpaidShortTimeHour;
	}
	
	/**
	 * @param txtUnpaidShortTimeHour セットする txtUnpaidShortTimeHour
	 */
	public void setTxtUnpaidShortTimeHour(String txtUnpaidShortTimeHour) {
		this.txtUnpaidShortTimeHour = txtUnpaidShortTimeHour;
	}
	
	/**
	 * @return txtUnpaidShortTimeMinute
	 */
	public String getTxtUnpaidShortTimeMinute() {
		return txtUnpaidShortTimeMinute;
	}
	
	/**
	 * @param txtUnpaidShortTimeMinute セットする txtUnpaidShortTimeMinute
	 */
	public void setTxtUnpaidShortTimeMinute(String txtUnpaidShortTimeMinute) {
		this.txtUnpaidShortTimeMinute = txtUnpaidShortTimeMinute;
	}
	
	/**
	 * @return txtTimesNonstop
	 */
	public String getTxtTimesNonstop() {
		return txtTimesNonstop;
	}
	
	/**
	 * @param txtTimesNonstop セットする txtTimesNonstop
	 */
	public void setTxtTimesNonstop(String txtTimesNonstop) {
		this.txtTimesNonstop = txtTimesNonstop;
	}
	
	/**
	 * @return txtTimesNoreturn
	 */
	public String getTxtTimesNoreturn() {
		return txtTimesNoreturn;
	}
	
	/**
	 * @param txtTimesNoreturn セットする txtTimesNoreturn
	 */
	public void setTxtTimesNoreturn(String txtTimesNoreturn) {
		this.txtTimesNoreturn = txtTimesNoreturn;
	}
	
	/**
	 * @return txtRestTimeHour
	 */
	public String getTxtRestTimeHour() {
		return txtRestTimeHour;
	}
	
	/**
	 * @param txtRestTimeHour セットする txtRestTimeHour
	 */
	public void setTxtRestTimeHour(String txtRestTimeHour) {
		this.txtRestTimeHour = txtRestTimeHour;
	}
	
	/**
	 * @return txtRestTimeMinute
	 */
	public String getTxtRestTimeMinute() {
		return txtRestTimeMinute;
	}
	
	/**
	 * @param txtRestTimeMinute セットする txtRestTimeMinute
	 */
	public void setTxtRestTimeMinute(String txtRestTimeMinute) {
		this.txtRestTimeMinute = txtRestTimeMinute;
	}
	
	/**
	 * @return txtRestLateNightHour
	 */
	public String getTxtRestLateNightHour() {
		return txtRestLateNightHour;
	}
	
	/**
	 * @param txtRestLateNightHour セットする txtRestLateNightHour
	 */
	public void setTxtRestLateNightHour(String txtRestLateNightHour) {
		this.txtRestLateNightHour = txtRestLateNightHour;
	}
	
	/**
	 * @return txtRestLateNightMinute
	 */
	public String getTxtRestLateNightMinute() {
		return txtRestLateNightMinute;
	}
	
	/**
	 * @param txtRestLateNightMinute セットする txtRestLateNightMinute
	 */
	public void setTxtRestLateNightMinute(String txtRestLateNightMinute) {
		this.txtRestLateNightMinute = txtRestLateNightMinute;
	}
	
	/**
	 * @return txtRestWorkOnSpecificHour
	 */
	public String getTxtRestWorkOnSpecificHour() {
		return txtRestWorkOnSpecificHour;
	}
	
	/**
	 * @param txtRestWorkOnSpecificHour セットする txtRestWorkOnSpecificHour
	 */
	public void setTxtRestWorkOnSpecificHour(String txtRestWorkOnSpecificHour) {
		this.txtRestWorkOnSpecificHour = txtRestWorkOnSpecificHour;
	}
	
	/**
	 * @return txtRestWorkOnSpecificMinute
	 */
	public String getTxtRestWorkOnSpecificMinute() {
		return txtRestWorkOnSpecificMinute;
	}
	
	/**
	 * @param txtRestWorkOnSpecificMinute セットする txtRestWorkOnSpecificMinute
	 */
	public void setTxtRestWorkOnSpecificMinute(String txtRestWorkOnSpecificMinute) {
		this.txtRestWorkOnSpecificMinute = txtRestWorkOnSpecificMinute;
	}
	
	/**
	 * @return txtRestWorkOnLegalHour
	 */
	public String getTxtRestWorkOnLegalHour() {
		return txtRestWorkOnLegalHour;
	}
	
	/**
	 * @param txtRestWorkOnLegalHour セットする txtRestWorkOnLegalHour
	 */
	public void setTxtRestWorkOnLegalHour(String txtRestWorkOnLegalHour) {
		this.txtRestWorkOnLegalHour = txtRestWorkOnLegalHour;
	}
	
	/**
	 * @return txtRestWorkOnLegalMinute
	 */
	public String getTxtRestWorkOnLegalMinute() {
		return txtRestWorkOnLegalMinute;
	}
	
	/**
	 * @param txtRestWorkOnLegalMinute セットする txtRestWorkOnLegalMinute
	 */
	public void setTxtRestWorkOnLegalMinute(String txtRestWorkOnLegalMinute) {
		this.txtRestWorkOnLegalMinute = txtRestWorkOnLegalMinute;
	}
	
	/**
	 * @return txtPrivateHour
	 */
	public String getTxtPrivateHour() {
		return txtPrivateHour;
	}
	
	/**
	 * @param txtPrivateHour セットする txtPrivateHour
	 */
	public void setTxtPrivateHour(String txtPrivateHour) {
		this.txtPrivateHour = txtPrivateHour;
	}
	
	/**
	 * @return txtPrivateMinute
	 */
	public String getTxtPrivateMinute() {
		return txtPrivateMinute;
	}
	
	/**
	 * @param txtPrivateMinute セットする txtPrivateMinute
	 */
	public void setTxtPrivateMinute(String txtPrivateMinute) {
		this.txtPrivateMinute = txtPrivateMinute;
	}
	
	/**
	 * @return txtPublicHour
	 */
	public String getTxtPublicHour() {
		return txtPublicHour;
	}
	
	/**
	 * @param txtPublicHour セットする txtPublicHour
	 */
	public void setTxtPublicHour(String txtPublicHour) {
		this.txtPublicHour = txtPublicHour;
	}
	
	/**
	 * @return txtPublicMinute
	 */
	public String getTxtPublicMinute() {
		return txtPublicMinute;
	}
	
	/**
	 * @param txtPublicMinute セットする txtPublicMinute
	 */
	public void setTxtPublicMinute(String txtPublicMinute) {
		this.txtPublicMinute = txtPublicMinute;
	}
	
	/**
	 * @return txtOverTimeHour
	 */
	public String getTxtOverTimeHour() {
		return txtOverTimeHour;
	}
	
	/**
	 * @param txtOverTimeHour セットする txtOverTimeHour
	 */
	public void setTxtOverTimeHour(String txtOverTimeHour) {
		this.txtOverTimeHour = txtOverTimeHour;
	}
	
	/**
	 * @return txtOverTimeMinute
	 */
	public String getTxtOverTimeMinute() {
		return txtOverTimeMinute;
	}
	
	/**
	 * @param txtOverTimeMinute セットする txtOverTimeMinute
	 */
	public void setTxtOverTimeMinute(String txtOverTimeMinute) {
		this.txtOverTimeMinute = txtOverTimeMinute;
	}
	
	/**
	 * @return txtOverTimeInHour
	 */
	public String getTxtOverTimeInHour() {
		return txtOverTimeInHour;
	}
	
	/**
	 * @param txtOverTimeInHour セットする txtOverTimeInHour
	 */
	public void setTxtOverTimeInHour(String txtOverTimeInHour) {
		this.txtOverTimeInHour = txtOverTimeInHour;
	}
	
	/**
	 * @return txtOverTimeInMinute
	 */
	public String getTxtOverTimeInMinute() {
		return txtOverTimeInMinute;
	}
	
	/**
	 * @param txtOverTimeInMinute セットする txtOverTimeInMinute
	 */
	public void setTxtOverTimeInMinute(String txtOverTimeInMinute) {
		this.txtOverTimeInMinute = txtOverTimeInMinute;
	}
	
	/**
	 * @return txtOverTimeOutHour
	 */
	public String getTxtOverTimeOutHour() {
		return txtOverTimeOutHour;
	}
	
	/**
	 * @param txtOverTimeOutHour セットする txtOverTimeOutHour
	 */
	public void setTxtOverTimeOutHour(String txtOverTimeOutHour) {
		this.txtOverTimeOutHour = txtOverTimeOutHour;
	}
	
	/**
	 * @return txtOverTimeOutMinute
	 */
	public String getTxtOverTimeOutMinute() {
		return txtOverTimeOutMinute;
	}
	
	/**
	 * @param txtOverTimeOutMinute セットする txtOverTimeOutMinute
	 */
	public void setTxtOverTimeOutMinute(String txtOverTimeOutMinute) {
		this.txtOverTimeOutMinute = txtOverTimeOutMinute;
	}
	
	/**
	 * @return txtLateNightHour
	 */
	public String getTxtLateNightHour() {
		return txtLateNightHour;
	}
	
	/**
	 * @param txtLateNightHour セットする txtLateNightHour
	 */
	public void setTxtLateNightHour(String txtLateNightHour) {
		this.txtLateNightHour = txtLateNightHour;
	}
	
	/**
	 * @return txtLateNightMinute
	 */
	public String getTxtLateNightMinute() {
		return txtLateNightMinute;
	}
	
	/**
	 * @param txtLateNightMinute セットする txtLateNightMinute
	 */
	public void setTxtLateNightMinute(String txtLateNightMinute) {
		this.txtLateNightMinute = txtLateNightMinute;
	}
	
	/**
	 * @return txtWorkSpecificOnHolidayHour
	 */
	public String getTxtWorkSpecificOnHolidayHour() {
		return txtWorkSpecificOnHolidayHour;
	}
	
	/**
	 * @param txtWorkSpecificOnHolidayHour セットする txtWorkSpecificOnHolidayHour
	 */
	public void setTxtWorkSpecificOnHolidayHour(String txtWorkSpecificOnHolidayHour) {
		this.txtWorkSpecificOnHolidayHour = txtWorkSpecificOnHolidayHour;
	}
	
	/**
	 * @return txtWorkSpecificOnHolidayMinute
	 */
	public String getTxtWorkSpecificOnHolidayMinute() {
		return txtWorkSpecificOnHolidayMinute;
	}
	
	/**
	 * @param txtWorkSpecificOnHolidayMinute セットする txtWorkSpecificOnHolidayMinute
	 */
	public void setTxtWorkSpecificOnHolidayMinute(String txtWorkSpecificOnHolidayMinute) {
		this.txtWorkSpecificOnHolidayMinute = txtWorkSpecificOnHolidayMinute;
	}
	
	/**
	 * @return txtWorkOnHolidayHour
	 */
	public String getTxtWorkOnHolidayHour() {
		return txtWorkOnHolidayHour;
	}
	
	/**
	 * @param txtWorkOnHolidayHour セットする txtWorkOnHolidayHour
	 */
	public void setTxtWorkOnHolidayHour(String txtWorkOnHolidayHour) {
		this.txtWorkOnHolidayHour = txtWorkOnHolidayHour;
	}
	
	/**
	 * @return txtWorkOnHolidayMinute
	 */
	public String getTxtWorkOnHolidayMinute() {
		return txtWorkOnHolidayMinute;
	}
	
	/**
	 * @param txtWorkOnHolidayMinute セットする txtWorkOnHolidayMinute
	 */
	public void setTxtWorkOnHolidayMinute(String txtWorkOnHolidayMinute) {
		this.txtWorkOnHolidayMinute = txtWorkOnHolidayMinute;
	}
	
	/**
	 * @return txt45HourOverTimeHour
	 */
	public String getTxt45HourOverTimeHour() {
		return txt45HourOverTimeHour;
	}
	
	/**
	 * @param txt45HourOverTimeHour セットする txt45HourOverTimeHour
	 */
	public void setTxt45HourOverTimeHour(String txt45HourOverTimeHour) {
		this.txt45HourOverTimeHour = txt45HourOverTimeHour;
	}
	
	/**
	 * @return txt45HourOverTimeMinute
	 */
	public String getTxt45HourOverTimeMinute() {
		return txt45HourOverTimeMinute;
	}
	
	/**
	 * @param txt45HourOverTimeMinute セットする txt45HourOverTimeMinute
	 */
	public void setTxt45HourOverTimeMinute(String txt45HourOverTimeMinute) {
		this.txt45HourOverTimeMinute = txt45HourOverTimeMinute;
	}
	
	/**
	 * @return txtDecreaseTimeHour
	 */
	public String getTxtDecreaseTimeHour() {
		return txtDecreaseTimeHour;
	}
	
	/**
	 * @param txtDecreaseTimeHour セットする txtDecreaseTimeHour
	 */
	public void setTxtDecreaseTimeHour(String txtDecreaseTimeHour) {
		this.txtDecreaseTimeHour = txtDecreaseTimeHour;
	}
	
	/**
	 * @return txtDecreaseTimeMinute
	 */
	public String getTxtDecreaseTimeMinute() {
		return txtDecreaseTimeMinute;
	}
	
	/**
	 * @param txtDecreaseTimeMinute セットする txtDecreaseTimeMinute
	 */
	public void setTxtDecreaseTimeMinute(String txtDecreaseTimeMinute) {
		this.txtDecreaseTimeMinute = txtDecreaseTimeMinute;
	}
	
	/**
	 * @return txtLateDays
	 */
	public String getTxtLateDays() {
		return txtLateDays;
	}
	
	/**
	 * @param txtLateDays セットする txtLateDays
	 */
	public void setTxtLateDays(String txtLateDays) {
		this.txtLateDays = txtLateDays;
	}
	
	/**
	 * @return txtLateThirtyMinutesOrMore
	 */
	public String getTxtLateThirtyMinutesOrMore() {
		return txtLateThirtyMinutesOrMore;
	}
	
	/**
	 * @param txtLateThirtyMinutesOrMore セットする txtLateThirtyMinutesOrMore
	 */
	public void setTxtLateThirtyMinutesOrMore(String txtLateThirtyMinutesOrMore) {
		this.txtLateThirtyMinutesOrMore = txtLateThirtyMinutesOrMore;
	}
	
	/**
	 * @return txtLateLessThanThirtyMinutes
	 */
	public String getTxtLateLessThanThirtyMinutes() {
		return txtLateLessThanThirtyMinutes;
	}
	
	/**
	 * @param txtLateLessThanThirtyMinutes セットする txtLateLessThanThirtyMinutes
	 */
	public void setTxtLateLessThanThirtyMinutes(String txtLateLessThanThirtyMinutes) {
		this.txtLateLessThanThirtyMinutes = txtLateLessThanThirtyMinutes;
	}
	
	/**
	 * @return txtLateTimeHour
	 */
	public String getTxtLateTimeHour() {
		return txtLateTimeHour;
	}
	
	/**
	 * @param txtLateTimeHour セットする txtLateTimeHour
	 */
	public void setTxtLateTimeHour(String txtLateTimeHour) {
		this.txtLateTimeHour = txtLateTimeHour;
	}
	
	/**
	 * @return txtLateTimeMinute
	 */
	public String getTxtLateTimeMinute() {
		return txtLateTimeMinute;
	}
	
	/**
	 * @param txtLateTimeMinute セットする txtLateTimeMinute
	 */
	public void setTxtLateTimeMinute(String txtLateTimeMinute) {
		this.txtLateTimeMinute = txtLateTimeMinute;
	}
	
	/**
	 * @return txtLateThirtyMinutesOrMoreTimeHour
	 */
	public String getTxtLateThirtyMinutesOrMoreTimeHour() {
		return txtLateThirtyMinutesOrMoreTimeHour;
	}
	
	/**
	 * @param txtLateThirtyMinutesOrMoreTimeHour セットする txtLateThirtyMinutesOrMoreTimeHour
	 */
	public void setTxtLateThirtyMinutesOrMoreTimeHour(String txtLateThirtyMinutesOrMoreTimeHour) {
		this.txtLateThirtyMinutesOrMoreTimeHour = txtLateThirtyMinutesOrMoreTimeHour;
	}
	
	/**
	 * @return txtLateThirtyMinutesOrMoreTimeMinute
	 */
	public String getTxtLateThirtyMinutesOrMoreTimeMinute() {
		return txtLateThirtyMinutesOrMoreTimeMinute;
	}
	
	/**
	 * @param txtLateThirtyMinutesOrMoreTimeMinute セットする txtLateThirtyMinutesOrMoreTimeMinute
	 */
	public void setTxtLateThirtyMinutesOrMoreTimeMinute(String txtLateThirtyMinutesOrMoreTimeMinute) {
		this.txtLateThirtyMinutesOrMoreTimeMinute = txtLateThirtyMinutesOrMoreTimeMinute;
	}
	
	/**
	 * @return txtLateLessThanThirtyMinutesTimeHour
	 */
	public String getTxtLateLessThanThirtyMinutesTimeHour() {
		return txtLateLessThanThirtyMinutesTimeHour;
	}
	
	/**
	 * @param txtLateLessThanThirtyMinutesTimeHour セットする txtLateLessThanThirtyMinutesTimeHour
	 */
	public void setTxtLateLessThanThirtyMinutesTimeHour(String txtLateLessThanThirtyMinutesTimeHour) {
		this.txtLateLessThanThirtyMinutesTimeHour = txtLateLessThanThirtyMinutesTimeHour;
	}
	
	/**
	 * @return txtLateLessThanThirtyMinutesTimeMinute
	 */
	public String getTxtLateLessThanThirtyMinutesTimeMinute() {
		return txtLateLessThanThirtyMinutesTimeMinute;
	}
	
	/**
	 * @param txtLateLessThanThirtyMinutesTimeMinute セットする txtLateLessThanThirtyMinutesTimeMinute
	 */
	public void setTxtLateLessThanThirtyMinutesTimeMinute(String txtLateLessThanThirtyMinutesTimeMinute) {
		this.txtLateLessThanThirtyMinutesTimeMinute = txtLateLessThanThirtyMinutesTimeMinute;
	}
	
	/**
	 * @return txtLeaveEarlyDays
	 */
	public String getTxtLeaveEarlyDays() {
		return txtLeaveEarlyDays;
	}
	
	/**
	 * @param txtLeaveEarlyDays セットする txtLeaveEarlyDays
	 */
	public void setTxtLeaveEarlyDays(String txtLeaveEarlyDays) {
		this.txtLeaveEarlyDays = txtLeaveEarlyDays;
	}
	
	/**
	 * @return txtLeaveEarlyThirtyMinutesOrMore
	 */
	public String getTxtLeaveEarlyThirtyMinutesOrMore() {
		return txtLeaveEarlyThirtyMinutesOrMore;
	}
	
	/**
	 * @param txtLeaveEarlyThirtyMinutesOrMore セットする txtLeaveEarlyThirtyMinutesOrMore
	 */
	public void setTxtLeaveEarlyThirtyMinutesOrMore(String txtLeaveEarlyThirtyMinutesOrMore) {
		this.txtLeaveEarlyThirtyMinutesOrMore = txtLeaveEarlyThirtyMinutesOrMore;
	}
	
	/**
	 * @return txtLeaveEarlyLessThanThirtyMinutes
	 */
	public String getTxtLeaveEarlyLessThanThirtyMinutes() {
		return txtLeaveEarlyLessThanThirtyMinutes;
	}
	
	/**
	 * @param txtLeaveEarlyLessThanThirtyMinutes セットする txtLeaveEarlyLessThanThirtyMinutes
	 */
	public void setTxtLeaveEarlyLessThanThirtyMinutes(String txtLeaveEarlyLessThanThirtyMinutes) {
		this.txtLeaveEarlyLessThanThirtyMinutes = txtLeaveEarlyLessThanThirtyMinutes;
	}
	
	/**
	 * @return txtLeaveEarlyTimeHour
	 */
	public String getTxtLeaveEarlyTimeHour() {
		return txtLeaveEarlyTimeHour;
	}
	
	/**
	 * @param txtLeaveEarlyTimeHour セットする txtLeaveEarlyTimeHour
	 */
	public void setTxtLeaveEarlyTimeHour(String txtLeaveEarlyTimeHour) {
		this.txtLeaveEarlyTimeHour = txtLeaveEarlyTimeHour;
	}
	
	/**
	 * @return txtLeaveEarlyTimeMinute
	 */
	public String getTxtLeaveEarlyTimeMinute() {
		return txtLeaveEarlyTimeMinute;
	}
	
	/**
	 * @param txtLeaveEarlyTimeMinute セットする txtLeaveEarlyTimeMinute
	 */
	public void setTxtLeaveEarlyTimeMinute(String txtLeaveEarlyTimeMinute) {
		this.txtLeaveEarlyTimeMinute = txtLeaveEarlyTimeMinute;
	}
	
	/**
	 * @return txtLeaveEarlyThirtyMinutesOrMoreTimeHour
	 */
	public String getTxtLeaveEarlyThirtyMinutesOrMoreTimeHour() {
		return txtLeaveEarlyThirtyMinutesOrMoreTimeHour;
	}
	
	/**
	 * @param txtLeaveEarlyThirtyMinutesOrMoreTimeHour セットする txtLeaveEarlyThirtyMinutesOrMoreTimeHour
	 */
	public void setTxtLeaveEarlyThirtyMinutesOrMoreTimeHour(String txtLeaveEarlyThirtyMinutesOrMoreTimeHour) {
		this.txtLeaveEarlyThirtyMinutesOrMoreTimeHour = txtLeaveEarlyThirtyMinutesOrMoreTimeHour;
	}
	
	/**
	 * @return txtLeaveEarlyThirtyMinutesOrMoreTimeMinute
	 */
	public String getTxtLeaveEarlyThirtyMinutesOrMoreTimeMinute() {
		return txtLeaveEarlyThirtyMinutesOrMoreTimeMinute;
	}
	
	/**
	 * @param txtLeaveEarlyThirtyMinutesOrMoreTimeMinute セットする txtLeaveEarlyThirtyMinutesOrMoreTimeMinute
	 */
	public void setTxtLeaveEarlyThirtyMinutesOrMoreTimeMinute(String txtLeaveEarlyThirtyMinutesOrMoreTimeMinute) {
		this.txtLeaveEarlyThirtyMinutesOrMoreTimeMinute = txtLeaveEarlyThirtyMinutesOrMoreTimeMinute;
	}
	
	/**
	 * @return txtLeaveEarlyLessThanThirtyMinutesTimeHour
	 */
	public String getTxtLeaveEarlyLessThanThirtyMinutesTimeHour() {
		return txtLeaveEarlyLessThanThirtyMinutesTimeHour;
	}
	
	/**
	 * @param txtLeaveEarlyLessThanThirtyMinutesTimeHour セットする txtLeaveEarlyLessThanThirtyMinutesTimeHour
	 */
	public void setTxtLeaveEarlyLessThanThirtyMinutesTimeHour(String txtLeaveEarlyLessThanThirtyMinutesTimeHour) {
		this.txtLeaveEarlyLessThanThirtyMinutesTimeHour = txtLeaveEarlyLessThanThirtyMinutesTimeHour;
	}
	
	/**
	 * @return txtLeaveEarlyLessThanThirtyMinutesTimeMinute
	 */
	public String getTxtLeaveEarlyLessThanThirtyMinutesTimeMinute() {
		return txtLeaveEarlyLessThanThirtyMinutesTimeMinute;
	}
	
	/**
	 * @param txtLeaveEarlyLessThanThirtyMinutesTimeMinute セットする txtLeaveEarlyLessThanThirtyMinutesTimeMinute
	 */
	public void setTxtLeaveEarlyLessThanThirtyMinutesTimeMinute(String txtLeaveEarlyLessThanThirtyMinutesTimeMinute) {
		this.txtLeaveEarlyLessThanThirtyMinutesTimeMinute = txtLeaveEarlyLessThanThirtyMinutesTimeMinute;
	}
	
	/**
	 * @return txtTimesHoliday
	 */
	public String getTxtTimesHoliday() {
		return txtTimesHoliday;
	}
	
	/**
	 * @param txtTimesHoliday セットする txtTimesHoliday
	 */
	public void setTxtTimesHoliday(String txtTimesHoliday) {
		this.txtTimesHoliday = txtTimesHoliday;
	}
	
	/**
	 * @return txtTimesLegalHoliday
	 */
	public String getTxtTimesLegalHoliday() {
		return txtTimesLegalHoliday;
	}
	
	/**
	 * @param txtTimesLegalHoliday セットする txtTimesLegalHoliday
	 */
	public void setTxtTimesLegalHoliday(String txtTimesLegalHoliday) {
		this.txtTimesLegalHoliday = txtTimesLegalHoliday;
	}
	
	/**
	 * @return txtTimesSpecificHoliday
	 */
	public String getTxtTimesSpecificHoliday() {
		return txtTimesSpecificHoliday;
	}
	
	/**
	 * @param txtTimesSpecificHoliday セットする txtTimesSpecificHoliday
	 */
	public void setTxtTimesSpecificHoliday(String txtTimesSpecificHoliday) {
		this.txtTimesSpecificHoliday = txtTimesSpecificHoliday;
	}
	
	/**
	 * @return txtTimesSubstitute
	 */
	public String getTxtTimesSubstitute() {
		return txtTimesSubstitute;
	}
	
	/**
	 * @param txtTimesSubstitute セットする txtTimesSubstitute
	 */
	public void setTxtTimesSubstitute(String txtTimesSubstitute) {
		this.txtTimesSubstitute = txtTimesSubstitute;
	}
	
	/**
	 * @return txtTimesPaidHoliday
	 */
	public String getTxtTimesPaidHoliday() {
		return txtTimesPaidHoliday;
	}
	
	/**
	 * @param txtTimesPaidHoliday セットする txtTimesPaidHoliday
	 */
	public void setTxtTimesPaidHoliday(String txtTimesPaidHoliday) {
		this.txtTimesPaidHoliday = txtTimesPaidHoliday;
	}
	
	/**
	 * @return txtPaidholidayHour
	 */
	public String getTxtPaidholidayHour() {
		return txtPaidholidayHour;
	}
	
	/**
	 * @param txtPaidholidayHour セットする txtPaidholidayHour
	 */
	public void setTxtPaidholidayHour(String txtPaidholidayHour) {
		this.txtPaidholidayHour = txtPaidholidayHour;
	}
	
	/**
	 * @return txtTimesStockHoliday
	 */
	public String getTxtTimesStockHoliday() {
		return txtTimesStockHoliday;
	}
	
	/**
	 * @param txtTimesStockHoliday セットする txtTimesStockHoliday
	 */
	public void setTxtTimesStockHoliday(String txtTimesStockHoliday) {
		this.txtTimesStockHoliday = txtTimesStockHoliday;
	}
	
	/**
	 * @return txtTimesCompensation
	 */
	public String getTxtTimesCompensation() {
		return txtTimesCompensation;
	}
	
	/**
	 * @param txtTimesCompensation セットする txtTimesCompensation
	 */
	public void setTxtTimesCompensation(String txtTimesCompensation) {
		this.txtTimesCompensation = txtTimesCompensation;
	}
	
	/**
	 * @return txtTimesLegalCompensation
	 */
	public String getTxtTimesLegalCompensation() {
		return txtTimesLegalCompensation;
	}
	
	/**
	 * @param txtTimesLegalCompensation セットする txtTimesLegalCompensation
	 */
	public void setTxtTimesLegalCompensation(String txtTimesLegalCompensation) {
		this.txtTimesLegalCompensation = txtTimesLegalCompensation;
	}
	
	/**
	 * @return txtTimesSpecificCompensation
	 */
	public String getTxtTimesSpecificCompensation() {
		return txtTimesSpecificCompensation;
	}
	
	/**
	 * @param txtTimesSpecificCompensation セットする txtTimesSpecificCompensation
	 */
	public void setTxtTimesSpecificCompensation(String txtTimesSpecificCompensation) {
		this.txtTimesSpecificCompensation = txtTimesSpecificCompensation;
	}
	
	/**
	 * @return txtTimesLateCompensation
	 */
	public String getTxtTimesLateCompensation() {
		return txtTimesLateCompensation;
	}
	
	/**
	 * @param txtTimesLateCompensation セットする txtTimesLateCompensation
	 */
	public void setTxtTimesLateCompensation(String txtTimesLateCompensation) {
		this.txtTimesLateCompensation = txtTimesLateCompensation;
	}
	
	/**
	 * @return txtTimesSpecificHolidaySubstitute
	 */
	public String getTxtTimesSpecificHolidaySubstitute() {
		return txtTimesSpecificHolidaySubstitute;
	}
	
	/**
	 * @param txtTimesSpecificHolidaySubstitute セットする txtTimesSpecificHolidaySubstitute
	 */
	public void setTxtTimesSpecificHolidaySubstitute(String txtTimesSpecificHolidaySubstitute) {
		this.txtTimesSpecificHolidaySubstitute = txtTimesSpecificHolidaySubstitute;
	}
	
	/**
	 * @return txtLegalCompensationOccurred
	 */
	public String getTxtLegalCompensationOccurred() {
		return txtLegalCompensationOccurred;
	}
	
	/**
	 * @param txtLegalCompensationOccurred セットする txtLegalCompensationOccurred
	 */
	public void setTxtLegalCompensationOccurred(String txtLegalCompensationOccurred) {
		this.txtLegalCompensationOccurred = txtLegalCompensationOccurred;
	}
	
	/**
	 * @return txtSpecificCompensationOccurred
	 */
	public String getTxtSpecificCompensationOccurred() {
		return txtSpecificCompensationOccurred;
	}
	
	/**
	 * @param txtSpecificCompensationOccurred セットする txtSpecificCompensationOccurred
	 */
	public void setTxtSpecificCompensationOccurred(String txtSpecificCompensationOccurred) {
		this.txtSpecificCompensationOccurred = txtSpecificCompensationOccurred;
	}
	
	/**
	 * @return txtLateCompensationOccurred
	 */
	public String getTxtLateCompensationOccurred() {
		return txtLateCompensationOccurred;
	}
	
	/**
	 * @param txtLateCompensationOccurred セットする txtLateCompensationOccurred
	 */
	public void setTxtLateCompensationOccurred(String txtLateCompensationOccurred) {
		this.txtLateCompensationOccurred = txtLateCompensationOccurred;
	}
	
	/**
	 * @return txtLegalCompensationUnused
	 */
	public String getTxtLegalCompensationUnused() {
		return txtLegalCompensationUnused;
	}
	
	/**
	 * @param txtLegalCompensationUnused セットする txtLegalCompensationUnused
	 */
	public void setTxtLegalCompensationUnused(String txtLegalCompensationUnused) {
		this.txtLegalCompensationUnused = txtLegalCompensationUnused;
	}
	
	/**
	 * @return txtSpecificCompensationUnused
	 */
	public String getTxtSpecificCompensationUnused() {
		return txtSpecificCompensationUnused;
	}
	
	/**
	 * @param txtSpecificCompensationUnused セットする txtSpecificCompensationUnused
	 */
	public void setTxtSpecificCompensationUnused(String txtSpecificCompensationUnused) {
		this.txtSpecificCompensationUnused = txtSpecificCompensationUnused;
	}
	
	/**
	 * @return txtLateCompensationUnused
	 */
	public String getTxtLateCompensationUnused() {
		return txtLateCompensationUnused;
	}
	
	/**
	 * @param txtLateCompensationUnused セットする txtLateCompensationUnused
	 */
	public void setTxtLateCompensationUnused(String txtLateCompensationUnused) {
		this.txtLateCompensationUnused = txtLateCompensationUnused;
	}
	
	/**
	 * @return txtTotalSpecialHoliday
	 */
	public String getTxtTotalSpecialHoliday() {
		return txtTotalSpecialHoliday;
	}
	
	/**
	 * @param txtTotalSpecialHoliday セットする txtTotalSpecialHoliday
	 */
	public void setTxtTotalSpecialHoliday(String txtTotalSpecialHoliday) {
		this.txtTotalSpecialHoliday = txtTotalSpecialHoliday;
	}
	
	/**
	 * @return txtTotalOtherHoliday
	 */
	public String getTxtTotalOtherHoliday() {
		return txtTotalOtherHoliday;
	}
	
	/**
	 * @param txtTotalOtherHoliday セットする txtTotalOtherHoliday
	 */
	public void setTxtTotalOtherHoliday(String txtTotalOtherHoliday) {
		this.txtTotalOtherHoliday = txtTotalOtherHoliday;
	}
	
	/**
	 * @return txtTotalDeduction
	 */
	public String getTxtTotalDeduction() {
		return txtTotalDeduction;
	}
	
	/**
	 * @param txtTotalDeduction セットする txtTotalDeduction
	 */
	public void setTxtTotalDeduction(String txtTotalDeduction) {
		this.txtTotalDeduction = txtTotalDeduction;
	}
	
	/**
	 * @return txt60HourOverTimeHour
	 */
	public String getTxt60HourOverTimeHour() {
		return txt60HourOverTimeHour;
	}
	
	/**
	 * @param txt60HourOverTimeHour セットする txt60HourOverTimeHour
	 */
	public void setTxt60HourOverTimeHour(String txt60HourOverTimeHour) {
		this.txt60HourOverTimeHour = txt60HourOverTimeHour;
	}
	
	/**
	 * @return txt60HourOverTimeMinute
	 */
	public String getTxt60HourOverTimeMinute() {
		return txt60HourOverTimeMinute;
	}
	
	/**
	 * @param txt60HourOverTimeMinute セットする txt60HourOverTimeMinute
	 */
	public void setTxt60HourOverTimeMinute(String txt60HourOverTimeMinute) {
		this.txt60HourOverTimeMinute = txt60HourOverTimeMinute;
	}
	
	/**
	 * @return txtWeekDayOverTimeHour
	 */
	public String getTxtWeekDayOverTimeHour() {
		return txtWeekDayOverTimeHour;
	}
	
	/**
	 * @param txtWeekDayOverTimeHour セットする txtWeekDayOverTimeHour
	 */
	public void setTxtWeekDayOverTimeHour(String txtWeekDayOverTimeHour) {
		this.txtWeekDayOverTimeHour = txtWeekDayOverTimeHour;
	}
	
	/**
	 * @return txtWeekDayOverTimeMinute
	 */
	public String getTxtWeekDayOverTimeMinute() {
		return txtWeekDayOverTimeMinute;
	}
	
	/**
	 * @param txtWeekDayOverTimeMinute セットする txtWeekDayOverTimeMinute
	 */
	public void setTxtWeekDayOverTimeMinute(String txtWeekDayOverTimeMinute) {
		this.txtWeekDayOverTimeMinute = txtWeekDayOverTimeMinute;
	}
	
	/**
	 * @return txtSpecificOverTimeHour
	 */
	public String getTxtSpecificOverTimeHour() {
		return txtSpecificOverTimeHour;
	}
	
	/**
	 * @param txtSpecificOverTimeHour セットする txtSpecificOverTimeHour
	 */
	public void setTxtSpecificOverTimeHour(String txtSpecificOverTimeHour) {
		this.txtSpecificOverTimeHour = txtSpecificOverTimeHour;
	}
	
	/**
	 * @return txtSpecificOverTimeMiunte
	 */
	public String getTxtSpecificOverTimeMiunte() {
		return txtSpecificOverTimeMiunte;
	}
	
	/**
	 * @param txtSpecificOverTimeMiunte セットする txtSpecificOverTimeMiunte
	 */
	public void setTxtSpecificOverTimeMiunte(String txtSpecificOverTimeMiunte) {
		this.txtSpecificOverTimeMiunte = txtSpecificOverTimeMiunte;
	}
	
	/**
	 * @return txtTimesAlternative
	 */
	public String getTxtTimesAlternative() {
		return txtTimesAlternative;
	}
	
	/**
	 * @param txtTimesAlternative セットする txtTimesAlternative
	 */
	public void setTxtTimesAlternative(String txtTimesAlternative) {
		this.txtTimesAlternative = txtTimesAlternative;
	}
	
	/**
	 * @return lblEmployeeCode
	 */
	@Override
	public String getLblEmployeeCode() {
		return lblEmployeeCode;
	}
	
	/**
	 * @param lblEmployeeCode セットする lblEmployeeCode
	 */
	@Override
	public void setLblEmployeeCode(String lblEmployeeCode) {
		this.lblEmployeeCode = lblEmployeeCode;
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
	 * @return lblSection
	 */
	public String getLblSection() {
		return lblSection;
	}
	
	/**
	 * @param lblSection セットする lblSection
	 */
	public void setLblSection(String lblSection) {
		this.lblSection = lblSection;
	}
	
	/**
	 * @return lblMonth
	 */
	public String getLblMonth() {
		return lblMonth;
	}
	
	/**
	 * @param lblMonth セットする lblMonth
	 */
	public void setLblMonth(String lblMonth) {
		this.lblMonth = lblMonth;
	}
	
	/**
	 * @return lblCorrectionHistory
	 */
	public String getLblCorrectionHistory() {
		return lblCorrectionHistory;
	}
	
	/**
	 * @param lblCorrectionHistory セットする lblCorrectionHistory
	 */
	public void setLblCorrectionHistory(String lblCorrectionHistory) {
		this.lblCorrectionHistory = lblCorrectionHistory;
	}
	
	/**
	 * @return txtTimesLegalHolidaySubstitute
	 */
	public String getTxtTimesLegalHolidaySubstitute() {
		return txtTimesLegalHolidaySubstitute;
	}
	
	/**
	 * @param txtTimesLegalHolidaySubstitute セットする txtTimesLegalHolidaySubstitute
	 */
	public void setTxtTimesLegalHolidaySubstitute(String txtTimesLegalHolidaySubstitute) {
		this.txtTimesLegalHolidaySubstitute = txtTimesLegalHolidaySubstitute;
	}
	
	/**
	 * @return tmdTotalTimeId
	 */
	public long getTmdTotalTimeId() {
		return tmdTotalTimeId;
	}
	
	/**
	 * @param tmdTotalTimeId セットする tmdTotalTimeId
	 */
	public void setTmdTotalTimeId(long tmdTotalTimeId) {
		this.tmdTotalTimeId = tmdTotalTimeId;
	}
	
	/**
	 * @return tmdOvertimeRequestId
	 */
	public long getTmdOvertimeRequestId() {
		return tmdOvertimeRequestId;
	}
	
	/**
	 * @param tmdOvertimeRequestId セットする tmdOvertimeRequestId
	 */
	public void setTmdOvertimeRequestId(long tmdOvertimeRequestId) {
		this.tmdOvertimeRequestId = tmdOvertimeRequestId;
	}
	
	/**
	 * @return txtTimesAchievement
	 */
	public String getTxtTimesAchievement() {
		return txtTimesAchievement;
	}
	
	/**
	 * @param txtTimesAchievement セットする txtTimesAchievement
	 */
	public void setTxtTimesAchievement(String txtTimesAchievement) {
		this.txtTimesAchievement = txtTimesAchievement;
	}
	
	/**
	 * @return txtTimesTotalWorkDate
	 */
	public String getTxtTimesTotalWorkDate() {
		return txtTimesTotalWorkDate;
	}
	
	/**
	 * @param txtTimesTotalWorkDate セットする txtTimesTotalWorkDate
	 */
	public void setTxtTimesTotalWorkDate(String txtTimesTotalWorkDate) {
		this.txtTimesTotalWorkDate = txtTimesTotalWorkDate;
	}
	
	/**
	 * @return txtTimesOvertime
	 */
	public String getTxtTimesOvertime() {
		return txtTimesOvertime;
	}
	
	/**
	 * @param txtTimesOvertime セットする txtTimesOvertime
	 */
	public void setTxtTimesOvertime(String txtTimesOvertime) {
		this.txtTimesOvertime = txtTimesOvertime;
	}
	
	/**
	 * @return txtTimesWorkingHoliday
	 */
	public String getTxtTimesWorkingHoliday() {
		return txtTimesWorkingHoliday;
	}
	
	/**
	 * @param txtTimesWorkingHoliday セットする txtTimesWorkingHoliday
	 */
	public void setTxtTimesWorkingHoliday(String txtTimesWorkingHoliday) {
		this.txtTimesWorkingHoliday = txtTimesWorkingHoliday;
	}
	
	/**
	 * @return aryTxtTimesSpecialLeave
	 */
	public String[] getAryTxtTimesSpecialLeave() {
		return getStringArrayClone(aryTxtTimesSpecialLeave);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryTxtTimesSpecialLeave
	 */
	public String getAryTxtTimesSpecialLeave(int idx) {
		return aryTxtTimesSpecialLeave[idx];
	}
	
	/**
	 * @param aryTxtTimesSpecialLeave セットする aryTxtTimesSpecialLeave
	 */
	public void setAryTxtTimesSpecialLeave(String[] aryTxtTimesSpecialLeave) {
		this.aryTxtTimesSpecialLeave = getStringArrayClone(aryTxtTimesSpecialLeave);
	}
	
	/**
	 * @return aryTxtTimesOtherVacation
	 */
	public String[] getAryTxtTimesOtherVacation() {
		return getStringArrayClone(aryTxtTimesOtherVacation);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryTxtTimesOtherVacation
	 */
	public String getAryTxtTimesOtherVacation(int idx) {
		return aryTxtTimesOtherVacation[idx];
	}
	
	/**
	 * @param aryTxtTimesOtherVacation セットする aryTxtTimesOtherVacation
	 */
	public void setAryTxtTimesOtherVacation(String[] aryTxtTimesOtherVacation) {
		this.aryTxtTimesOtherVacation = getStringArrayClone(aryTxtTimesOtherVacation);
	}
	
	/**
	 * @return aryTxtDeduction
	 */
	public String[] getAryTxtDeduction() {
		return getStringArrayClone(aryTxtDeduction);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryTxtDeduction
	 */
	public String getAryTxtDeduction(int idx) {
		return aryTxtDeduction[idx];
	}
	
	/**
	 * @param aryTxtDeduction セットする aryTxtDeduction
	 */
	public void setAryTxtDeduction(String[] aryTxtDeduction) {
		this.aryTxtDeduction = getStringArrayClone(aryTxtDeduction);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryTxtTimesSpecialLeaveTitle
	 */
	public String getAryTxtTimesSpecialLeaveTitle(int idx) {
		return aryTxtTimesSpecialLeaveTitle[idx];
	}
	
	/**
	 * @param aryTxtTimesSpecialLeaveTitle セットする aryTxtTimesSpecialLeaveTitle
	 */
	public void setAryTxtTimesSpecialLeaveTitle(String[] aryTxtTimesSpecialLeaveTitle) {
		this.aryTxtTimesSpecialLeaveTitle = getStringArrayClone(aryTxtTimesSpecialLeaveTitle);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryTxtTimesOtherVacationTitle
	 */
	public String getAryTxtTimesOtherVacationTitle(int idx) {
		return aryTxtTimesOtherVacationTitle[idx];
	}
	
	/**
	 * @param aryTxtTimesOtherVacationTitle セットする aryTxtTimesOtherVacationTitle
	 */
	public void setAryTxtTimesOtherVacationTitle(String[] aryTxtTimesOtherVacationTitle) {
		this.aryTxtTimesOtherVacationTitle = getStringArrayClone(aryTxtTimesOtherVacationTitle);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryTxtDeductionTitle
	 */
	public String getAryTxtDeductionTitle(int idx) {
		return aryTxtDeductionTitle[idx];
	}
	
	/**
	 * @param aryTxtDeductionTitle セットする aryTxtDeductionTitle
	 */
	public void setAryTxtDeductionTitle(String[] aryTxtDeductionTitle) {
		this.aryTxtDeductionTitle = getStringArrayClone(aryTxtDeductionTitle);
	}
	
	/**
	 * @return aryTxtTimesSpecialLeaveCode
	 */
	public String[] getAryTxtTimesSpecialLeaveCode() {
		return getStringArrayClone(aryTxtTimesSpecialLeaveCode);
	}
	
	/**
	 * @param aryTxtTimesSpecialLeaveCode セットする aryTxtTimesSpecialLeaveCode
	 */
	public void setAryTxtTimesSpecialLeaveCode(String[] aryTxtTimesSpecialLeaveCode) {
		this.aryTxtTimesSpecialLeaveCode = getStringArrayClone(aryTxtTimesSpecialLeaveCode);
	}
	
	/**
	 * @return aryTxtTimesOtherVacationCode
	 */
	public String[] getAryTxtTimesOtherVacationCode() {
		return getStringArrayClone(aryTxtTimesOtherVacationCode);
	}
	
	/**
	 * @param aryTxtTimesOtherVacationCode セットする aryTxtTimesOtherVacationCode
	 */
	public void setAryTxtTimesOtherVacationCode(String[] aryTxtTimesOtherVacationCode) {
		this.aryTxtTimesOtherVacationCode = getStringArrayClone(aryTxtTimesOtherVacationCode);
	}
	
	/**
	 * @return aryTxtDeductionCode
	 */
	public String[] getAryTxtDeductionCode() {
		return getStringArrayClone(aryTxtDeductionCode);
	}
	
	/**
	 * @param aryTxtDeductionCode セットする aryTxtDeductionCode
	 */
	public void setAryTxtDeductionCode(String[] aryTxtDeductionCode) {
		this.aryTxtDeductionCode = getStringArrayClone(aryTxtDeductionCode);
	}
	
	/**
	 * @return aryTimesSpecialLeaveId
	 */
	public long[] getAryTimesSpecialLeaveId() {
		return getLongArrayClone(aryTimesSpecialLeaveId);
	}
	
	/**
	 * @param aryTimesSpecialLeaveId セットする aryTimesSpecialLeaveId
	 */
	public void setAryTimesSpecialLeaveId(long[] aryTimesSpecialLeaveId) {
		this.aryTimesSpecialLeaveId = getLongArrayClone(aryTimesSpecialLeaveId);
	}
	
	/**
	 * @return aryTimesOtherVacation
	 */
	public long[] getAryTimesOtherVacationId() {
		return getLongArrayClone(aryTimesOtherVacationId);
	}
	
	/**
	 * @param aryTimesOtherVacationId セットする aryTimesOtherVacationId
	 */
	public void setAryTimesOtherVacationId(long[] aryTimesOtherVacationId) {
		this.aryTimesOtherVacationId = getLongArrayClone(aryTimesOtherVacationId);
	}
	
	/**
	 * @return aryDeduction
	 */
	public long[] getAryDeductionId() {
		return getLongArrayClone(aryDeductionId);
	}
	
	/**
	 * @param aryDeductionId セットする aryDeductionId
	 */
	public void setAryDeductionId(long[] aryDeductionId) {
		this.aryDeductionId = getLongArrayClone(aryDeductionId);
	}
	
	/**
	 * @return txtSpecialHolidayHour
	 */
	public String getTxtSpecialHolidayHour() {
		return txtSpecialHolidayHour;
	}
	
	/**
	 * @param txtSpecialHolidayHour セットする txtSpecialHolidayHour
	 */
	public void setTxtSpecialHolidayHour(String txtSpecialHolidayHour) {
		this.txtSpecialHolidayHour = txtSpecialHolidayHour;
	}
	
	/**
	 * @return txtOtherHolidayHour
	 */
	public String getTxtOtherHolidayHour() {
		return txtOtherHolidayHour;
	}
	
	/**
	 * @param txtOtherHolidayHour セットする txtOtherHolidayHour
	 */
	public void setTxtOtherHolidayHour(String txtOtherHolidayHour) {
		this.txtOtherHolidayHour = txtOtherHolidayHour;
	}
	
	/**
	 * @return txtDeductionHour
	 */
	public String getTxtDeductionHour() {
		return txtDeductionHour;
	}
	
	/**
	 * @param txtDeductionHour セットする txtDeductionHour
	 */
	public void setTxtDeductionHour(String txtDeductionHour) {
		this.txtDeductionHour = txtDeductionHour;
	}
	
	/**
	 * @param idx インデックス
	 * @return aryTxtTimesSpecialHour
	 */
	public String getAryTxtTimesSpecialHour(int idx) {
		return aryTxtTimesSpecialHour[idx];
	}
	
	/**
	 * @param aryTxtTimesSpecialHour セットする aryTxtTimesSpecialHour
	 */
	public void setAryTxtTimesSpecialHour(String[] aryTxtTimesSpecialHour) {
		this.aryTxtTimesSpecialHour = getStringArrayClone(aryTxtTimesSpecialHour);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryTxtTimesOtherVacationHour
	 */
	public String getAryTxtTimesOtherVacationHour(int idx) {
		return aryTxtTimesOtherVacationHour[idx];
	}
	
	/**
	 * @param aryTxtTimesOtherVacationHour セットする aryTxtTimesOtherVacationHour
	 */
	public void setAryTxtTimesOtherVacationHour(String[] aryTxtTimesOtherVacationHour) {
		this.aryTxtTimesOtherVacationHour = getStringArrayClone(aryTxtTimesOtherVacationHour);
	}
	
	/**
	 * @param idx インデックス
	 * @return aryTxtDeductionHour
	 */
	public String getAryTxtDeductionHour(int idx) {
		return aryTxtDeductionHour[idx];
	}
	
	/**
	 * @param aryTxtDeductionHour セットする aryTxtDeductionHour
	 */
	public void setAryTxtDeductionHour(String[] aryTxtDeductionHour) {
		this.aryTxtDeductionHour = getStringArrayClone(aryTxtDeductionHour);
	}
	
	/**
	 * @param isTightened セットする isTightened
	 */
	public void setTightened(boolean isTightened) {
		this.isTightened = isTightened;
	}
	
}
