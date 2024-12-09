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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;

/**
 * 勤怠設定管理DTO
 */
public class TmmTimeSettingDto extends BaseDto implements TimeSettingDtoInterface {
	
	private static final long	serialVersionUID	= -7211513036575477929L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmTimeSettingId;
	/**
	 * 勤怠設定コード。
	 */
	private String				workSettingCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 勤怠設定名称。
	 */
	private String				workSettingName;
	/**
	 * 勤怠設定略称。
	 */
	private String				workSettingAbbr;
	/**
	 * 締日コード。
	 */
	private String				cutoffCode;
	/**
	 * 勤怠管理対象フラグ。
	 */
	private int					timeManagementFlag;
	/**
	 * 日々申請対象フラグ。
	 */
	private int					dailyApprovalFlag;
	/**
	 * 勤務前残業フラグ。
	 */
	private int					beforeOvertimeFlag;
	/**
	 * 所定休日取扱。
	 */
	private int					specificHolidayHandling;
	
	/**
	 * ポータル出退勤ボタン表示。
	 */
	private int					portalTimeButtons;
	
	/**
	 * ポータル休憩ボタン表示。
	 */
	private int					portalRestButtons;
	
	/**
	 * 勤務予定時間表示。
	 */
	private int					useScheduledTime;
	
	/**
	 * 日出勤丸め単位。
	 */
	private int					roundDailyStartUnit;
	/**
	 * 日出勤丸め。
	 */
	private int					roundDailyStart;
	/**
	 * 日退勤丸め単位。
	 */
	private int					roundDailyEndUnit;
	/**
	 * 日退勤丸め。
	 */
	private int					roundDailyEnd;
	/**
	 * 日勤務時間丸め単位。
	 */
	private int					roundDailyTimeWork;
	/**
	 * 日勤務時間丸め。
	 */
	private int					roundDailyWork;
	/**
	 * 日休憩入丸め単位。
	 */
	private int					roundDailyRestStartUnit;
	/**
	 * 日休憩入丸め。
	 */
	private int					roundDailyRestStart;
	/**
	 * 日休憩戻丸め単位。
	 */
	private int					roundDailyRestEndUnit;
	/**
	 * 日休憩戻丸め。
	 */
	private int					roundDailyRestEnd;
	/**
	 * 日休憩時間丸め単位。
	 */
	private int					roundDailyRestTimeUnit;
	/**
	 * 日休憩時間丸め。
	 */
	private int					roundDailyRestTime;
	/**
	 * 日遅刻丸め単位。
	 */
	private int					roundDailyLateUnit;
	/**
	 * 日遅刻丸め。
	 */
	private int					roundDailyLate;
	/**
	 * 日早退丸め単位。
	 */
	private int					roundDailyLeaveEarlyUnit;
	/**
	 * 日早退丸め。
	 */
	private int					roundDailyLeaveEarly;
	/**
	 * 日私用外出入丸め単位。
	 */
	private int					roundDailyPrivateStartUnit;
	/**
	 * 日私用外出入丸め。
	 */
	private int					roundDailyPrivateStart;
	/**
	 * 日私用外出戻丸め単位。
	 */
	private int					roundDailyPrivateEndUnit;
	/**
	 * 日私用外出戻丸め。
	 */
	private int					roundDailyPrivateEnd;
	/**
	 * 日公用外出入丸め単位。
	 */
	private int					roundDailyPublicStartUnit;
	/**
	 * 日公用外出入丸め。
	 */
	private int					roundDailyPublicStart;
	/**
	 * 日公用外出戻丸め単位。
	 */
	private int					roundDailyPublicEndUnit;
	/**
	 * 日公用外出戻丸め。
	 */
	private int					roundDailyPublicEnd;
	/**
	 * 日減額対象丸め単位。
	 */
	private int					roundDailyDecreaseTimeUnit;
	/**
	 * 日減額対象時間丸め。
	 */
	private int					roundDailyDecreaseTime;
	/**
	 * 日残業時間丸め単位。
	 */
	private int					roundDailyOvertimeUnit;
	/**
	 * 日残業時間丸め。
	 */
	private int					roundDailyOvertime;
	
	/**
	 * 日無給時短時間丸め単位。
	 */
	private int					roundDailyShortUnpaidUnit;
	/**
	 * 日無給時短時間丸め。
	 */
	private int					roundDailyShortUnpaid;
	/**
	 * 月勤務時間丸め単位。
	 */
	private int					roundMonthlyWorkUnit;
	/**
	 * 月勤務時間丸め。
	 */
	private int					roundMonthlyWork;
	/**
	 * 月休憩時間丸め単位。
	 */
	private int					roundMonthlyRestUnit;
	/**
	 * 月休憩時間丸め。
	 */
	private int					roundMonthlyRest;
	/**
	 * 月遅刻丸め単位。
	 */
	private int					roundMonthlyLateUnit;
	/**
	 * 月遅刻時間丸め。
	 */
	private int					roundMonthlyLate;
	/**
	 * 月早退丸め単位。
	 */
	private int					roundMonthlyEarlyUnit;
	/**
	 * 月早退丸め。
	 */
	private int					roundMonthlyEarly;
	/**
	 * 月私用外出丸め単位。
	 */
	private int					roundMonthlyPrivateTime;
	/**
	 * 月私用外出時間丸め。
	 */
	private int					roundMonthlyPrivate;
	/**
	 * 月公用外出丸め単位。
	 */
	private int					roundMonthlyPublicTime;
	/**
	 * 月公用外出時間丸め。
	 */
	private int					roundMonthlyPublic;
	/**
	 * 月減額対象丸め単位。
	 */
	private int					roundMonthlyDecreaseTime;
	/**
	 * 月減額対象時間丸め。
	 */
	private int					roundMonthlyDecrease;
	/**
	 * 月残業時間丸め単位。
	 */
	private int					roundMonthlyOvertimeUnit;
	/**
	 * 月残業時間丸め。
	 */
	private int					roundMonthlyOvertime;
	/**
	 * 月無給時短時間丸め単位
	 */
	private int					roundMonthlyShortUnpaidUnit;
	/**
	 * 月無給時短時間丸め
	 */
	private int					roundMonthlyShortUnpaid;
	/**
	 * 週の起算曜日。
	 */
	private int					startWeek;
	/**
	 * 月の起算日。
	 */
	private int					startMonth;
	/**
	 * 年の起算月。
	 */
	private int					startYear;
	/**
	 * 所定労働時間。
	 */
	//private Time				generalWorkTime;
	private Date				generalWorkTime;
	/**
	 * 一日の起算時。
	 */
	//private Time				startDayTime;
	private Date				startDayTime;
	/**
	 * 遅刻早退限度時間(全日)。
	 */
	//private Time				lateEarlyFull;
	private Date				lateEarlyFull;
	/**
	 * 遅刻早退限度時間(半日)。
	 */
	//private Time				lateEarlyHalf;
	private Date				lateEarlyHalf;
	/**
	 * 振休取得期限月(休出前)。
	 */
	private int					transferAheadLimitMonth;
	/**
	 * 振休取得期限日(休出前)。
	 */
	private int					transferAheadLimitDate;
	/**
	 * 振休取得期限月(休出後)。
	 */
	private int					transferLaterLimitMonth;
	/**
	 * 振休取得期限日(休出後)。
	 */
	private int					transferLaterLimitDate;
	/**
	 * 代休取得期限月。
	 */
	private int					subHolidayLimitMonth;
	/**
	 * 代休取得期限日。
	 */
	private int					subHolidayLimitDate;
	/**
	 * 半休入替取得(振休)。
	 */
	private int					transferExchange;
	/**
	 * 半休入替取得(代休)。
	 */
	private int					subHolidayExchange;
	/**
	 * 代休基準時間(全休)。
	 */
	private Date				subHolidayAllNorm;
	/**
	 * 代休基準時間(半休)。
	 */
	private Date				subHolidayHalfNorm;
	
	/**
	 * 60時間超割増機能。
	 */
	private int					sixtyHourFunctionFlag;
	/**
	 * 60時間超代替休暇。
	 */
	private int					sixtyHourAlternativeFlag;
	/**
	 * 月60時間超割増。
	 */
	private int					monthSixtyHourSurcharge;
	/**
	 * 平日残業割増。
	 */
	private int					weekdayOver;
	/**
	 * 代替休暇平日。
	 */
	private int					weekdayAlternative;
	/**
	 * 代替休暇放棄。
	 */
	private int					alternativeCancel;
	/**
	 * 代替休暇所定休日。
	 */
	private int					alternativeSpecific;
	/**
	 * 代替休暇法定休日。
	 */
	private int					alternativeLegal;
	/**
	 * 所定休日割増率。
	 */
	private int					specificHoliday;
	/**
	 * 法定休日割増率。
	 */
	private int					legalHoliday;
	
	/**
	 * 見込月
	 */
	private String				prospectsMonths;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	/**
	 * 締日略称。
	 */
	private String				cutoffAbbr;
	
	
	@Override
	public int getAlternativeCancel() {
		return alternativeCancel;
	}
	
	@Override
	public int getAlternativeLegal() {
		return alternativeLegal;
	}
	
	@Override
	public int getAlternativeSpecific() {
		return alternativeSpecific;
	}
	
	@Override
	public int getBeforeOvertimeFlag() {
		return beforeOvertimeFlag;
	}
	
	@Override
	public String getCutoffCode() {
		return cutoffCode;
	}
	
	@Override
	public int getDailyApprovalFlag() {
		return dailyApprovalFlag;
	}
	
	@Override
	//public Time getGeneralWorkTime() {
	public Date getGeneralWorkTime() {
		return getDateClone(generalWorkTime);
	}
	
	@Override
	//public Time getLateEarlyFull() {
	public Date getLateEarlyFull() {
		return getDateClone(lateEarlyFull);
	}
	
	@Override
	//public Time getLateEarlyHalf() {
	public Date getLateEarlyHalf() {
		return getDateClone(lateEarlyHalf);
	}
	
	@Override
	public int getLegalHoliday() {
		return legalHoliday;
	}
	
	@Override
	public int getMonthSixtyHourSurcharge() {
		return monthSixtyHourSurcharge;
	}
	
	@Override
	public int getRoundDailyDecreaseTime() {
		return roundDailyDecreaseTime;
	}
	
	@Override
	public int getRoundDailyDecreaseTimeUnit() {
		return roundDailyDecreaseTimeUnit;
	}
	
	@Override
	public int getRoundDailyEnd() {
		return roundDailyEnd;
	}
	
	@Override
	public int getRoundDailyLate() {
		return roundDailyLate;
	}
	
	@Override
	public int getRoundDailyLeaveEarly() {
		return roundDailyLeaveEarly;
	}
	
	@Override
	public int getRoundDailyPrivateStart() {
		return roundDailyPrivateStart;
	}
	
	@Override
	public int getRoundDailyPrivateEnd() {
		return roundDailyPrivateEnd;
	}
	
	@Override
	public int getRoundDailyPublicStart() {
		return roundDailyPublicStart;
	}
	
	@Override
	public int getRoundDailyPublicEnd() {
		return roundDailyPublicEnd;
	}
	
	@Override
	public int getRoundDailyRestEnd() {
		return roundDailyRestEnd;
	}
	
	@Override
	public int getRoundDailyRestStart() {
		return roundDailyRestStart;
	}
	
	@Override
	public int getRoundDailyRestTime() {
		return roundDailyRestTime;
	}
	
	@Override
	public int getRoundDailyRestTimeUnit() {
		return roundDailyRestTimeUnit;
	}
	
	@Override
	public int getRoundDailyStart() {
		return roundDailyStart;
	}
	
	@Override
	public int getRoundDailyTimeWork() {
		return roundDailyTimeWork;
	}
	
	@Override
	public int getRoundDailyWork() {
		return roundDailyWork;
	}
	
	@Override
	public int getRoundMonthlyDecrease() {
		return roundMonthlyDecrease;
	}
	
	@Override
	public int getRoundMonthlyDecreaseTime() {
		return roundMonthlyDecreaseTime;
	}
	
	@Override
	public int getRoundMonthlyPrivate() {
		return roundMonthlyPrivate;
	}
	
	@Override
	public int getRoundMonthlyPrivateTime() {
		return roundMonthlyPrivateTime;
	}
	
	@Override
	public int getRoundMonthlyPublic() {
		return roundMonthlyPublic;
	}
	
	@Override
	public int getRoundMonthlyPublicTime() {
		return roundMonthlyPublicTime;
	}
	
	@Override
	public int getRoundMonthlyRest() {
		return roundMonthlyRest;
	}
	
	@Override
	public int getRoundMonthlyRestUnit() {
		return roundMonthlyRestUnit;
	}
	
	@Override
	public int getRoundMonthlyWork() {
		return roundMonthlyWork;
	}
	
	@Override
	public int getRoundMonthlyWorkUnit() {
		return roundMonthlyWorkUnit;
	}
	
	@Override
	public int getSixtyHourAlternativeFlag() {
		return sixtyHourAlternativeFlag;
	}
	
	@Override
	public int getSixtyHourFunctionFlag() {
		return sixtyHourFunctionFlag;
	}
	
	@Override
	public int getSpecificHoliday() {
		return specificHoliday;
	}
	
	@Override
	//public Time getStartDayTime() {
	public Date getStartDayTime() {
		return getDateClone(startDayTime);
	}
	
	@Override
	public int getStartMonth() {
		return startMonth;
	}
	
	@Override
	public int getStartWeek() {
		return startWeek;
	}
	
	@Override
	public int getStartYear() {
		return startYear;
	}
	
	@Override
	public int getTimeManagementFlag() {
		return timeManagementFlag;
	}
	
	@Override
	public String getWorkSettingAbbr() {
		return workSettingAbbr;
	}
	
	@Override
	public String getWorkSettingCode() {
		return workSettingCode;
	}
	
	@Override
	public String getWorkSettingName() {
		return workSettingName;
	}
	
	@Override
	public long getTmmTimeSettingId() {
		return tmmTimeSettingId;
	}
	
	@Override
	public int getWeekdayAlternative() {
		return weekdayAlternative;
	}
	
	@Override
	public int getWeekdayOver() {
		return weekdayOver;
	}
	
	@Override
	public void setAlternativeCancel(int alternativeCancel) {
		this.alternativeCancel = alternativeCancel;
	}
	
	@Override
	public void setAlternativeLegal(int alternativeLegal) {
		this.alternativeLegal = alternativeLegal;
	}
	
	@Override
	public void setAlternativeSpecific(int alternativeSpecific) {
		this.alternativeSpecific = alternativeSpecific;
	}
	
	@Override
	public void setBeforeOvertimeFlag(int beforeOvertimeFlag) {
		this.beforeOvertimeFlag = beforeOvertimeFlag;
	}
	
	@Override
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
	@Override
	public void setDailyApprovalFlag(int dailyApprovalFlag) {
		this.dailyApprovalFlag = dailyApprovalFlag;
	}
	
	@Override
	//public void setGeneralWorkTime(Time generalWorkTime) {
	public void setGeneralWorkTime(Date generalWorkTime) {
		this.generalWorkTime = getDateClone(generalWorkTime);
	}
	
	@Override
	//public void setLateEarlyFull(Time lateEarlyFull) {
	public void setLateEarlyFull(Date lateEarlyFull) {
		this.lateEarlyFull = getDateClone(lateEarlyFull);
	}
	
	@Override
	//public void setLateEarlyHalf(Time lateEarlyHalf) {
	public void setLateEarlyHalf(Date lateEarlyHalf) {
		this.lateEarlyHalf = getDateClone(lateEarlyHalf);
	}
	
	@Override
	public void setLegalHoliday(int legalHoliday) {
		this.legalHoliday = legalHoliday;
	}
	
	@Override
	public void setMonthSixtyHourSurcharge(int monthSixtyHourSurcharge) {
		this.monthSixtyHourSurcharge = monthSixtyHourSurcharge;
	}
	
	@Override
	public void setRoundDailyDecreaseTime(int roundDailyDecreaseTime) {
		this.roundDailyDecreaseTime = roundDailyDecreaseTime;
	}
	
	@Override
	public void setRoundDailyDecreaseTimeUnit(int roundDailyDecreaseTimeUnit) {
		this.roundDailyDecreaseTimeUnit = roundDailyDecreaseTimeUnit;
	}
	
	@Override
	public void setRoundDailyEnd(int roundDailyEnd) {
		this.roundDailyEnd = roundDailyEnd;
	}
	
	@Override
	public void setRoundDailyLate(int roundDailyLate) {
		this.roundDailyLate = roundDailyLate;
	}
	
	@Override
	public void setRoundDailyLeaveEarly(int roundDailyLeaveEarly) {
		this.roundDailyLeaveEarly = roundDailyLeaveEarly;
	}
	
	@Override
	public void setRoundDailyPrivateStart(int roundDailyPrivateStart) {
		this.roundDailyPrivateStart = roundDailyPrivateStart;
	}
	
	@Override
	public void setRoundDailyPrivateEnd(int roundDailyPrivateEnd) {
		this.roundDailyPrivateEnd = roundDailyPrivateEnd;
	}
	
	@Override
	public void setRoundDailyPublicStart(int roundDailyPublicStart) {
		this.roundDailyPublicStart = roundDailyPublicStart;
	}
	
	@Override
	public void setRoundDailyPublicEnd(int roundDailyPublicEnd) {
		this.roundDailyPublicEnd = roundDailyPublicEnd;
	}
	
	@Override
	public void setRoundDailyRestEnd(int roundDailyRestEnd) {
		this.roundDailyRestEnd = roundDailyRestEnd;
	}
	
	@Override
	public void setRoundDailyRestStart(int roundDailyRestStart) {
		this.roundDailyRestStart = roundDailyRestStart;
	}
	
	@Override
	public void setRoundDailyRestTime(int roundDailyRestTime) {
		this.roundDailyRestTime = roundDailyRestTime;
	}
	
	@Override
	public void setRoundDailyRestTimeUnit(int roundDailyRestTimeUnit) {
		this.roundDailyRestTimeUnit = roundDailyRestTimeUnit;
	}
	
	@Override
	public void setRoundDailyStart(int roundDailyStart) {
		this.roundDailyStart = roundDailyStart;
	}
	
	@Override
	public void setRoundDailyTimeWork(int roundDailyTimeWork) {
		this.roundDailyTimeWork = roundDailyTimeWork;
	}
	
	@Override
	public void setRoundDailyWork(int roundDailyWork) {
		this.roundDailyWork = roundDailyWork;
	}
	
	@Override
	public void setRoundMonthlyDecrease(int roundMonthlyDecrease) {
		this.roundMonthlyDecrease = roundMonthlyDecrease;
	}
	
	@Override
	public void setRoundMonthlyDecreaseTime(int roundMonthlyDecreaseTime) {
		this.roundMonthlyDecreaseTime = roundMonthlyDecreaseTime;
	}
	
	@Override
	public void setRoundMonthlyPrivate(int roundMonthlyPrivate) {
		this.roundMonthlyPrivate = roundMonthlyPrivate;
	}
	
	@Override
	public void setRoundMonthlyPrivateTime(int roundMonthlyPrivateTime) {
		this.roundMonthlyPrivateTime = roundMonthlyPrivateTime;
	}
	
	@Override
	public void setRoundMonthlyPublic(int roundMonthlyPublic) {
		this.roundMonthlyPublic = roundMonthlyPublic;
	}
	
	@Override
	public void setRoundMonthlyPublicTime(int roundMonthlyPublicTime) {
		this.roundMonthlyPublicTime = roundMonthlyPublicTime;
	}
	
	@Override
	public void setRoundMonthlyRest(int roundMonthlyRest) {
		this.roundMonthlyRest = roundMonthlyRest;
	}
	
	@Override
	public void setRoundMonthlyRestUnit(int roundMonthlyRestUnit) {
		this.roundMonthlyRestUnit = roundMonthlyRestUnit;
	}
	
	@Override
	public void setRoundMonthlyWork(int roundMonthlyWork) {
		this.roundMonthlyWork = roundMonthlyWork;
	}
	
	@Override
	public void setRoundMonthlyWorkUnit(int roundMonthlyWorkUnit) {
		this.roundMonthlyWorkUnit = roundMonthlyWorkUnit;
	}
	
	@Override
	public void setSixtyHourAlternativeFlag(int sixtyHourAlternativeFlag) {
		this.sixtyHourAlternativeFlag = sixtyHourAlternativeFlag;
	}
	
	@Override
	public void setSixtyHourFunctionFlag(int sixtyHourFunctionFlag) {
		this.sixtyHourFunctionFlag = sixtyHourFunctionFlag;
	}
	
	@Override
	public void setSpecificHoliday(int specificHoliday) {
		this.specificHoliday = specificHoliday;
	}
	
	@Override
	//public void setStartDayTime(Time startDayTime) {
	public void setStartDayTime(Date startDayTime) {
		this.startDayTime = getDateClone(startDayTime);
	}
	
	@Override
	public void setStartMonth(int startMonth) {
		this.startMonth = startMonth;
	}
	
	@Override
	public void setStartWeek(int startWeek) {
		this.startWeek = startWeek;
	}
	
	@Override
	public void setStartYear(int startYear) {
		this.startYear = startYear;
	}
	
	@Override
	public void setTimeManagementFlag(int timeManagementFlag) {
		this.timeManagementFlag = timeManagementFlag;
	}
	
	@Override
	public void setWorkSettingAbbr(String workSettingAbbr) {
		this.workSettingAbbr = workSettingAbbr;
	}
	
	@Override
	public void setWorkSettingCode(String workSettingCode) {
		this.workSettingCode = workSettingCode;
	}
	
	@Override
	public void setWorkSettingName(String workSettingName) {
		this.workSettingName = workSettingName;
	}
	
	@Override
	public void setTmmTimeSettingId(long tmmTimeSettingId) {
		this.tmmTimeSettingId = tmmTimeSettingId;
	}
	
	@Override
	public void setWeekdayAlternative(int weekdayAlternative) {
		this.weekdayAlternative = weekdayAlternative;
	}
	
	@Override
	public void setWeekdayOver(int weekdayOver) {
		this.weekdayOver = weekdayOver;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public String getProspectsMonths() {
		return prospectsMonths;
	}
	
	@Override
	public void setProspectsMonths(String prospectsMonths) {
		this.prospectsMonths = prospectsMonths;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public int getRoundDailyEndUnit() {
		return roundDailyEndUnit;
	}
	
	@Override
	public int getRoundDailyLateUnit() {
		return roundDailyLateUnit;
	}
	
	@Override
	public int getRoundDailyLeaveEarlyUnit() {
		return roundDailyLeaveEarlyUnit;
	}
	
	@Override
	public int getRoundDailyRestEndUnit() {
		return roundDailyRestEndUnit;
	}
	
	@Override
	public int getRoundDailyRestStartUnit() {
		return roundDailyRestStartUnit;
	}
	
	@Override
	public int getRoundDailyStartUnit() {
		return roundDailyStartUnit;
	}
	
	@Override
	public int getRoundMonthlyEarly() {
		return roundMonthlyEarly;
	}
	
	@Override
	public int getRoundMonthlyEarlyUnit() {
		return roundMonthlyEarlyUnit;
	}
	
	@Override
	public int getRoundMonthlyLate() {
		return roundMonthlyLate;
	}
	
	@Override
	public int getRoundMonthlyLateUnit() {
		return roundMonthlyLateUnit;
	}
	
	@Override
	public int getSpecificHolidayHandling() {
		return specificHolidayHandling;
	}
	
	@Override
	public Date getSubHolidayAllNorm() {
		return getDateClone(subHolidayAllNorm);
	}
	
	@Override
	public int getSubHolidayExchange() {
		return subHolidayExchange;
	}
	
	@Override
	public Date getSubHolidayHalfNorm() {
		return getDateClone(subHolidayHalfNorm);
	}
	
	@Override
	public int getSubHolidayLimitDate() {
		return subHolidayLimitDate;
	}
	
	@Override
	public int getSubHolidayLimitMonth() {
		return subHolidayLimitMonth;
	}
	
	@Override
	public int getTransferAheadLimitDate() {
		return transferAheadLimitDate;
	}
	
	@Override
	public int getTransferAheadLimitMonth() {
		return transferAheadLimitMonth;
	}
	
	@Override
	public int getTransferExchange() {
		return transferExchange;
	}
	
	@Override
	public int getTransferLaterLimitDate() {
		return transferLaterLimitDate;
	}
	
	@Override
	public int getTransferLaterLimitMonth() {
		return transferLaterLimitMonth;
	}
	
	@Override
	public void setRoundDailyEndUnit(int roundDailyEndUnit) {
		this.roundDailyEndUnit = roundDailyEndUnit;
	}
	
	@Override
	public void setRoundDailyLateUnit(int roundDailyLateUnit) {
		this.roundDailyLateUnit = roundDailyLateUnit;
	}
	
	@Override
	public void setRoundDailyLeaveEarlyUnit(int roundDailyLeaveEarlyUnit) {
		this.roundDailyLeaveEarlyUnit = roundDailyLeaveEarlyUnit;
	}
	
	@Override
	public void setRoundDailyRestEndUnit(int roundDailyRestEndUnit) {
		this.roundDailyRestEndUnit = roundDailyRestEndUnit;
	}
	
	@Override
	public void setRoundDailyRestStartUnit(int roundDailyRestStartUnit) {
		this.roundDailyRestStartUnit = roundDailyRestStartUnit;
	}
	
	@Override
	public void setRoundDailyStartUnit(int roundDailyStartUnit) {
		this.roundDailyStartUnit = roundDailyStartUnit;
	}
	
	@Override
	public void setRoundMonthlyEarly(int roundMonthlyEarly) {
		this.roundMonthlyEarly = roundMonthlyEarly;
	}
	
	@Override
	public void setRoundMonthlyEarlyUnit(int roundMonthlyEarlyUnit) {
		this.roundMonthlyEarlyUnit = roundMonthlyEarlyUnit;
	}
	
	@Override
	public void setRoundMonthlyLate(int roundMonthlyLate) {
		this.roundMonthlyLate = roundMonthlyLate;
	}
	
	@Override
	public void setRoundMonthlyLateUnit(int roundMonthlyLateUnit) {
		this.roundMonthlyLateUnit = roundMonthlyLateUnit;
	}
	
	@Override
	public void setSpecificHolidayHandling(int specificHolidayHandling) {
		this.specificHolidayHandling = specificHolidayHandling;
	}
	
	@Override
	public void setSubHolidayAllNorm(Date subHolidayAllNorm) {
		this.subHolidayAllNorm = getDateClone(subHolidayAllNorm);
	}
	
	@Override
	public void setSubHolidayExchange(int subHolidayExchange) {
		this.subHolidayExchange = subHolidayExchange;
	}
	
	@Override
	public void setSubHolidayHalfNorm(Date subHolidayHalfNorm) {
		this.subHolidayHalfNorm = getDateClone(subHolidayHalfNorm);
	}
	
	@Override
	public void setSubHolidayLimitDate(int subHolidayLimitDate) {
		this.subHolidayLimitDate = subHolidayLimitDate;
	}
	
	@Override
	public void setSubHolidayLimitMonth(int subHolidayLimitMonth) {
		this.subHolidayLimitMonth = subHolidayLimitMonth;
	}
	
	@Override
	public void setTransferAheadLimitDate(int transferAheadLimitDate) {
		this.transferAheadLimitDate = transferAheadLimitDate;
	}
	
	@Override
	public void setTransferAheadLimitMonth(int transferAheadLimitMonth) {
		this.transferAheadLimitMonth = transferAheadLimitMonth;
	}
	
	@Override
	public void setTransferExchange(int transferExchange) {
		this.transferExchange = transferExchange;
	}
	
	@Override
	public void setTransferLaterLimitDate(int transferLaterLimitDate) {
		this.transferLaterLimitDate = transferLaterLimitDate;
	}
	
	@Override
	public void setTransferLaterLimitMonth(int transferLaterLimitMonth) {
		this.transferLaterLimitMonth = transferLaterLimitMonth;
	}
	
	@Override
	public String getCutoffAbbr() {
		return cutoffAbbr;
	}
	
	@Override
	public void setCutoffAbbr(String cutoffAbbr) {
		this.cutoffAbbr = cutoffAbbr;
	}
	
	@Override
	public int getRoundDailyPrivateEndUnit() {
		return roundDailyPrivateEndUnit;
	}
	
	@Override
	public int getRoundDailyPrivateStartUnit() {
		return roundDailyPrivateStartUnit;
	}
	
	@Override
	public void setRoundDailyPrivateEndUnit(int roundDailyPrivateEndUnit) {
		this.roundDailyPrivateEndUnit = roundDailyPrivateEndUnit;
	}
	
	@Override
	public void setRoundDailyPrivateStartUnit(int roundDailyPrivateStartUnit) {
		this.roundDailyPrivateStartUnit = roundDailyPrivateStartUnit;
	}
	
	@Override
	public void setRoundDailyPublicEndUnit(int roundDailyPublicEndUnit) {
		this.roundDailyPublicEndUnit = roundDailyPublicEndUnit;
	}
	
	@Override
	public void setRoundDailyPublicStartUnit(int roundDailyPublicStartUnit) {
		this.roundDailyPublicStartUnit = roundDailyPublicStartUnit;
	}
	
	@Override
	public int getRoundDailyPublicEndUnit() {
		return roundDailyPublicEndUnit;
	}
	
	@Override
	public int getRoundDailyPublicStartUnit() {
		return roundDailyPublicStartUnit;
	}
	
	@Override
	public int getRoundDailyOvertimeUnit() {
		return roundDailyOvertimeUnit;
	}
	
	@Override
	public int getRoundDailyOvertime() {
		return roundDailyOvertime;
	}
	
	@Override
	public int getRoundMonthlyOvertimeUnit() {
		return roundMonthlyOvertimeUnit;
	}
	
	@Override
	public int getRoundMonthlyOvertime() {
		return roundMonthlyOvertime;
	}
	
	@Override
	public void setRoundDailyOvertimeUnit(int roundDailyOvertimeUnit) {
		this.roundDailyOvertimeUnit = roundDailyOvertimeUnit;
	}
	
	@Override
	public void setRoundDailyOvertime(int roundDailyOvertime) {
		this.roundDailyOvertime = roundDailyOvertime;
	}
	
	@Override
	public void setRoundMonthlyOvertimeUnit(int roundMonthlyOvertimeUnit) {
		this.roundMonthlyOvertimeUnit = roundMonthlyOvertimeUnit;
	}
	
	@Override
	public void setRoundMonthlyOvertime(int roundMonthlyOvertime) {
		this.roundMonthlyOvertime = roundMonthlyOvertime;
	}
	
	@Override
	public int getRoundDailyShortUnpaidUnit() {
		return roundDailyShortUnpaidUnit;
	}
	
	@Override
	public void setRoundDailyShortUnpaidUnit(int roundDailyShortUnpaidUnit) {
		this.roundDailyShortUnpaidUnit = roundDailyShortUnpaidUnit;
	}
	
	@Override
	public int getRoundDailyShortUnpaid() {
		return roundDailyShortUnpaid;
	}
	
	@Override
	public void setRoundDailyShortUnpaid(int roundDailyShortUnpaid) {
		this.roundDailyShortUnpaid = roundDailyShortUnpaid;
	}
	
	@Override
	public int getRoundMonthlyShortUnpaidUnit() {
		return roundMonthlyShortUnpaidUnit;
	}
	
	@Override
	public void setRoundMonthlyShortUnpaidUnit(int roundMonthlyShortUnpaidUnit) {
		this.roundMonthlyShortUnpaidUnit = roundMonthlyShortUnpaidUnit;
	}
	
	@Override
	public int getRoundMonthlyShortUnpaid() {
		return roundMonthlyShortUnpaid;
	}
	
	@Override
	public void setRoundMonthlyShortUnpaid(int roundMonthlyShortUnpaid) {
		this.roundMonthlyShortUnpaid = roundMonthlyShortUnpaid;
	}
	
	@Override
	public int getPortalTimeButtons() {
		return portalTimeButtons;
	}
	
	@Override
	public void setPortalTimeButtons(int portalTimeButtons) {
		this.portalTimeButtons = portalTimeButtons;
	}
	
	@Override
	public int getPortalRestButtons() {
		return portalRestButtons;
	}
	
	@Override
	public void setPortalRestButtons(int portalRestButtons) {
		this.portalRestButtons = portalRestButtons;
	}
	
	@Override
	public int getUseScheduledTime() {
		return useScheduledTime;
	}
	
	@Override
	public void setUseScheduledTime(int useScheduledTime) {
		this.useScheduledTime = useScheduledTime;
	}
	
}
