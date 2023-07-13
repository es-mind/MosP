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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;

/**
 * 勤怠データDTO
 */
public class TmdAttendanceDto extends BaseDto implements AttendanceDtoInterface {
	
	private static final long	serialVersionUID	= 5391839079668229639L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdAttendanceId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 勤務日。
	 */
	private Date				workDate;
	/**
	 * 勤務回数。
	 */
	private int					timesWork;
	/**
	 * 勤務形態コード。
	 */
	private String				workTypeCode;
	/**
	 * 直行。
	 */
	private int					directStart;
	/**
	 * 直帰。
	 */
	private int					directEnd;
	/**
	 * 始業忘れ。
	 */
	private int					forgotRecordWorkStart;
	/**
	 * その他の始業できなかった場合。
	 */
	private int					notRecordWorkStart;
	/**
	 * 始業時刻。
	 */
	private Date				startTime;
	/**
	 * 実始業時刻。
	 */
	private Date				actualStartTime;
	/**
	 * 終業時刻。
	 */
	private Date				endTime;
	/**
	 * 実終業時刻。
	 */
	private Date				actualEndTime;
	/**
	 * 遅刻日数。
	 */
	private int					lateDays;
	/**
	 * 遅刻30分以上日数。
	 */
	private int					lateThirtyMinutesOrMore;
	/**
	 * 遅刻30分未満日数。
	 */
	private int					lateLessThanThirtyMinutes;
	/**
	 * 遅刻時間。
	 */
	private int					lateTime;
	/**
	 * 実遅刻時間。
	 */
	private int					actualLateTime;
	/**
	 * 遅刻30分以上時間。
	 */
	private int					lateThirtyMinutesOrMoreTime;
	/**
	 * 遅刻30分未満時間。
	 */
	private int					lateLessThanThirtyMinutesTime;
	/**
	 * 遅刻理由。
	 */
	private String				lateReason;
	/**
	 * 遅刻証明書。
	 */
	private String				lateCertificate;
	/**
	 * 遅刻コメント。
	 */
	private String				lateComment;
	/**
	 * 早退日数。
	 */
	private int					leaveEarlyDays;
	/**
	 * 早退30分以上日数。
	 */
	private int					leaveEarlyThirtyMinutesOrMore;
	/**
	 * 早退30分未満日数。
	 */
	private int					leaveEarlyLessThanThirtyMinutes;
	/**
	 * 早退時間。
	 */
	private int					leaveEarlyTime;
	/**
	 * 実早退時間。
	 */
	private int					actualLeaveEarlyTime;
	/**
	 * 早退30分以上時間。
	 */
	private int					leaveEarlyThirtyMinutesOrMoreTime;
	/**
	 * 早退30分未満時間。
	 */
	private int					leaveEarlyLessThanThirtyMinutesTime;
	/**
	 * 早退理由。
	 */
	private String				leaveEarlyReason;
	/**
	 * 早退証明書。
	 */
	private String				leaveEarlyCertificate;
	/**
	 * 早退コメント。
	 */
	private String				leaveEarlyComment;
	/**
	 * 勤務時間。
	 */
	private int					workTime;
	/**
	 * 所定労働時間。
	 */
	private int					generalWorkTime;
	/**
	 * 所定労働時間内労働時間。
	 */
	private int					workTimeWithinPrescribedWorkTime;
	/**
	 * 契約勤務時間。
	 */
	private int					contractWorkTime;
	/**
	 * 無給時短時間。
	 */
	private int					shortUnpaid;
	/**
	 * 休憩時間。
	 */
	private int					restTime;
	/**
	 * 法定外休憩時間。
	 */
	private int					overRestTime;
	/**
	 * 深夜休憩時間。
	 */
	private int					nightRestTime;
	/**
	 * 法定休出休憩時間。
	 */
	private int					legalHolidayRestTime;
	/**
	 * 所定休出休憩時間。
	 */
	private int					prescribedHolidayRestTime;
	/**
	 * 公用外出時間。
	 */
	private int					publicTime;
	/**
	 * 私用外出時間。
	 */
	private int					privateTime;
	/**
	 * 分単位休暇A時間。
	 */
	private int					minutelyHolidayATime;
	/**
	 * 分単位休暇B時間。
	 */
	private int					minutelyHolidayBTime;
	/**
	 * 分単位休暇A全休。
	 */
	private int					minutelyHolidayA;
	/**
	 * 分単位休暇B全休。
	 */
	private int					minutelyHolidayB;
	/**
	 * 残業時間。
	 */
	private int					overtime;
	/**
	 * 残業回数。
	 */
	private int					timesOvertime;
	/**
	 * 前残業時間。
	 */
	private int					overtimeBefore;
	/**
	 * 後残業時間。
	 */
	private int					overtimeAfter;
	/**
	 * 法定内残業時間。
	 */
	private int					overtimeIn;
	/**
	 * 法定外残業時間。
	 */
	private int					overtimeOut;
	/**
	 * 平日法定時間内残業時間。
	 */
	private int					workdayOvertimeIn;
	/**
	 * 平日法定時間外残業時間。
	 */
	private int					workdayOvertimeOut;
	/**
	 * 所定休日法定時間内残業時間。
	 */
	private int					prescribedHolidayOvertimeIn;
	/**
	 * 所定休日法定時間外残業時間。
	 */
	private int					prescribedHolidayOvertimeOut;
	/**
	 * 深夜勤務時間。
	 */
	private int					lateNightTime;
	/**
	 * 深夜所定労働時間内時間。
	 */
	private int					nightWorkWithinPrescribedWork;
	/**
	 * 深夜時間外時間。
	 */
	private int					nightOvertimeWork;
	/**
	 * 深夜休日労働時間。
	 */
	private int					nightWorkOnHoliday;
	/**
	 * 所定休日勤務時間。
	 */
	private int					specificWorkTime;
	/**
	 * 法定休日勤務時間。
	 */
	private int					legalWorkTime;
	/**
	 * 減額対象時間。
	 */
	private int					decreaseTime;
	/**
	 * 勤怠コメント。
	 */
	private String				timeComment;
	/**
	 * 備考。
	 */
	private String				remarks;
	/**
	 * 出勤日数。
	 */
	private double				workDays;
	/**
	 * 有給休暇用出勤日数。
	 */
	private int					workDaysForPaidLeave;
	/**
	 * 有給休暇用全労働日。
	 */
	private int					totalWorkDaysForPaidLeave;
	/**
	 * 休日出勤回数。
	 */
	private int					timesHolidayWork;
	/**
	 * 法定休日出勤回数。
	 */
	private int					timesLegalHolidayWork;
	/**
	 * 所定休日出勤回数。
	 */
	private int					timesPrescribedHolidayWork;
	/**
	 * 有給休暇日数。
	 */
	private double				paidLeaveDays;
	/**
	 * 有給休暇時間数。
	 */
	private int					paidLeaveHours;
	/**
	 * ストック休暇日数。
	 */
	private double				stockLeaveDays;
	/**
	 * 代休日数。
	 */
	private double				compensationDays;
	/**
	 * 法定代休日数。
	 */
	private double				legalCompensationDays;
	/**
	 * 所定代休日数。
	 */
	private double				prescribedCompensationDays;
	/**
	 * 深夜代休日数。
	 */
	private double				nightCompensationDays;
	/**
	 * 特別休暇日数。
	 */
	private double				specialLeaveDays;
	/**
	 * 特別休暇時間数。
	 */
	private int					specialLeaveHours;
	/**
	 * その他休暇日数。
	 */
	private double				otherLeaveDays;
	/**
	 * その他休暇時間数。
	 */
	private int					otherLeaveHours;
	/**
	 * 欠勤日数。
	 */
	private double				absenceDays;
	/**
	 * 欠勤時間数。
	 */
	private int					absenceHours;
	/**
	 * 法定代休発生日数。
	 */
	private double				grantedLegalCompensationDays;
	/**
	 * 所定代休発生日数。
	 */
	private double				grantedPrescribedCompensationDays;
	/**
	 * 深夜代休発生日数。
	 */
	private double				grantedNightCompensationDays;
	/**
	 * 法定休出時間(代休あり)。
	 */
	private int					legalHolidayWorkTimeWithCompensationDay;
	/**
	 * 法定休出時間(代休なし)。
	 */
	private int					legalHolidayWorkTimeWithoutCompensationDay;
	/**
	 * 所定休出時間(代休あり)。
	 */
	private int					prescribedHolidayWorkTimeWithCompensationDay;
	/**
	 * 所定休出時間(代休なし)。
	 */
	private int					prescribedHolidayWorkTimeWithoutCompensationDay;
	/**
	 * 法定労働時間内残業時間(代休あり)。
	 */
	private int					overtimeInWithCompensationDay;
	/**
	 * 法定労働時間内残業時間(代休なし)。
	 */
	private int					overtimeInWithoutCompensationDay;
	/**
	 * 法定外残業時間(代休あり)。
	 */
	private int					overtimeOutWithCompensationDay;
	/**
	 * 法定労働時間外残業時間(代休なし)。
	 */
	private int					overtimeOutWithoutCompensationDay;
	/**
	 * 所定労働時間内法定休日労働時間。
	 */
	private int					statutoryHolidayWorkTimeIn;
	/**
	 * 所定労働時間外法定休日労働時間。
	 */
	private int					statutoryHolidayWorkTimeOut;
	/**
	 * 所定労働時間内所定休日労働時間。
	 */
	private int					prescribedHolidayWorkTimeIn;
	/**
	 * 所定労働時間外所定休日労働時間。
	 */
	private int					prescribedHolidayWorkTimeOut;
	/**
	 * ワークフロー番号。
	 */
	private long				workflow;
	
	
	@Override
	public int getDecreaseTime() {
		return decreaseTime;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getEndTime() {
		return getDateClone(endTime);
	}
	
	@Override
	public Date getActualEndTime() {
		return getDateClone(actualEndTime);
	}
	
	@Override
	public int getLateDays() {
		return lateDays;
	}
	
	@Override
	public int getLateThirtyMinutesOrMore() {
		return lateThirtyMinutesOrMore;
	}
	
	@Override
	public int getLateLessThanThirtyMinutes() {
		return lateLessThanThirtyMinutes;
	}
	
	@Override
	public String getLateCertificate() {
		return lateCertificate;
	}
	
	@Override
	public String getLateComment() {
		return lateComment;
	}
	
	@Override
	public int getLeaveEarlyDays() {
		return leaveEarlyDays;
	}
	
	@Override
	public int getLeaveEarlyThirtyMinutesOrMore() {
		return leaveEarlyThirtyMinutesOrMore;
	}
	
	@Override
	public int getLeaveEarlyLessThanThirtyMinutes() {
		return leaveEarlyLessThanThirtyMinutes;
	}
	
	@Override
	public int getLateNightTime() {
		return lateNightTime;
	}
	
	@Override
	public int getNightWorkWithinPrescribedWork() {
		return nightWorkWithinPrescribedWork;
	}
	
	@Override
	public int getNightOvertimeWork() {
		return nightOvertimeWork;
	}
	
	@Override
	public int getNightWorkOnHoliday() {
		return nightWorkOnHoliday;
	}
	
	@Override
	public String getLateReason() {
		return lateReason;
	}
	
	@Override
	public int getLateTime() {
		return lateTime;
	}
	
	@Override
	public int getActualLateTime() {
		return actualLateTime;
	}
	
	@Override
	public int getLateThirtyMinutesOrMoreTime() {
		return lateThirtyMinutesOrMoreTime;
	}
	
	@Override
	public int getLateLessThanThirtyMinutesTime() {
		return lateLessThanThirtyMinutesTime;
	}
	
	@Override
	public String getLeaveEarlyCertificate() {
		return leaveEarlyCertificate;
	}
	
	@Override
	public String getLeaveEarlyComment() {
		return leaveEarlyComment;
	}
	
	@Override
	public String getLeaveEarlyReason() {
		return leaveEarlyReason;
	}
	
	@Override
	public int getLeaveEarlyTime() {
		return leaveEarlyTime;
	}
	
	@Override
	public int getActualLeaveEarlyTime() {
		return actualLeaveEarlyTime;
	}
	
	@Override
	public int getLeaveEarlyThirtyMinutesOrMoreTime() {
		return leaveEarlyThirtyMinutesOrMoreTime;
	}
	
	@Override
	public int getLeaveEarlyLessThanThirtyMinutesTime() {
		return leaveEarlyLessThanThirtyMinutesTime;
	}
	
	@Override
	public int getLegalWorkTime() {
		return legalWorkTime;
	}
	
	@Override
	public int getNightRestTime() {
		return nightRestTime;
	}
	
	@Override
	public int getLegalHolidayRestTime() {
		return legalHolidayRestTime;
	}
	
	@Override
	public int getPrescribedHolidayRestTime() {
		return prescribedHolidayRestTime;
	}
	
	@Override
	public int getOverRestTime() {
		return overRestTime;
	}
	
	@Override
	public int getOvertime() {
		return overtime;
	}
	
	@Override
	public int getOvertimeOut() {
		return overtimeOut;
	}
	
	@Override
	public int getWorkdayOvertimeIn() {
		return workdayOvertimeIn;
	}
	
	@Override
	public int getWorkdayOvertimeOut() {
		return workdayOvertimeOut;
	}
	
	@Override
	public int getPrescribedHolidayOvertimeIn() {
		return prescribedHolidayOvertimeIn;
	}
	
	@Override
	public int getPrescribedHolidayOvertimeOut() {
		return prescribedHolidayOvertimeOut;
	}
	
	@Override
	public int getPrivateTime() {
		return privateTime;
	}
	
	@Override
	public int getTimesOvertime() {
		return timesOvertime;
	}
	
	@Override
	public int getPublicTime() {
		return publicTime;
	}
	
	@Override
	public int getMinutelyHolidayATime() {
		return minutelyHolidayATime;
	}
	
	@Override
	public int getMinutelyHolidayBTime() {
		return minutelyHolidayBTime;
	}
	
	@Override
	public int getMinutelyHolidayA() {
		return minutelyHolidayA;
	}
	
	@Override
	public int getMinutelyHolidayB() {
		return minutelyHolidayB;
	}
	
	@Override
	public int getRestTime() {
		return restTime;
	}
	
	@Override
	public int getSpecificWorkTime() {
		return specificWorkTime;
	}
	
	@Override
	public Date getStartTime() {
		return getDateClone(startTime);
	}
	
	@Override
	public Date getActualStartTime() {
		return getDateClone(actualStartTime);
	}
	
	@Override
	public long getTmdAttendanceId() {
		return tmdAttendanceId;
	}
	
	@Override
	public Date getWorkDate() {
		return getDateClone(workDate);
	}
	
	@Override
	public int getWorkTime() {
		return workTime;
	}
	
	@Override
	public String getWorkTypeCode() {
		return workTypeCode;
	}
	
	@Override
	public long getWorkflow() {
		return workflow;
	}
	
	@Override
	public String getTimeComment() {
		return timeComment;
	}
	
	@Override
	public double getWorkDays() {
		return workDays;
	}
	
	@Override
	public int getWorkDaysForPaidLeave() {
		return workDaysForPaidLeave;
	}
	
	@Override
	public int getTotalWorkDaysForPaidLeave() {
		return totalWorkDaysForPaidLeave;
	}
	
	@Override
	public int getTimesHolidayWork() {
		return timesHolidayWork;
	}
	
	@Override
	public int getTimesLegalHolidayWork() {
		return timesLegalHolidayWork;
	}
	
	@Override
	public int getTimesPrescribedHolidayWork() {
		return timesPrescribedHolidayWork;
	}
	
	@Override
	public double getPaidLeaveDays() {
		return paidLeaveDays;
	}
	
	@Override
	public int getPaidLeaveHours() {
		return paidLeaveHours;
	}
	
	@Override
	public double getStockLeaveDays() {
		return stockLeaveDays;
	}
	
	@Override
	public double getCompensationDays() {
		return compensationDays;
	}
	
	@Override
	public double getLegalCompensationDays() {
		return legalCompensationDays;
	}
	
	@Override
	public double getPrescribedCompensationDays() {
		return prescribedCompensationDays;
	}
	
	@Override
	public double getNightCompensationDays() {
		return nightCompensationDays;
	}
	
	@Override
	public double getSpecialLeaveDays() {
		return specialLeaveDays;
	}
	
	@Override
	public int getSpecialLeaveHours() {
		return specialLeaveHours;
	}
	
	@Override
	public double getOtherLeaveDays() {
		return otherLeaveDays;
	}
	
	@Override
	public int getOtherLeaveHours() {
		return otherLeaveHours;
	}
	
	@Override
	public double getAbsenceDays() {
		return absenceDays;
	}
	
	@Override
	public int getAbsenceHours() {
		return absenceHours;
	}
	
	@Override
	public double getGrantedLegalCompensationDays() {
		return grantedLegalCompensationDays;
	}
	
	@Override
	public double getGrantedPrescribedCompensationDays() {
		return grantedPrescribedCompensationDays;
	}
	
	@Override
	public double getGrantedNightCompensationDays() {
		return grantedNightCompensationDays;
	}
	
	@Override
	public int getLegalHolidayWorkTimeWithCompensationDay() {
		return legalHolidayWorkTimeWithCompensationDay;
	}
	
	@Override
	public int getLegalHolidayWorkTimeWithoutCompensationDay() {
		return legalHolidayWorkTimeWithoutCompensationDay;
	}
	
	@Override
	public int getPrescribedHolidayWorkTimeWithCompensationDay() {
		return prescribedHolidayWorkTimeWithCompensationDay;
	}
	
	@Override
	public int getPrescribedHolidayWorkTimeWithoutCompensationDay() {
		return prescribedHolidayWorkTimeWithoutCompensationDay;
	}
	
	@Override
	public int getOvertimeInWithCompensationDay() {
		return overtimeInWithCompensationDay;
	}
	
	@Override
	public int getOvertimeInWithoutCompensationDay() {
		return overtimeInWithoutCompensationDay;
	}
	
	@Override
	public int getOvertimeOutWithCompensationDay() {
		return overtimeOutWithCompensationDay;
	}
	
	@Override
	public int getOvertimeOutWithoutCompensationDay() {
		return overtimeOutWithoutCompensationDay;
	}
	
	@Override
	public int getStatutoryHolidayWorkTimeIn() {
		return statutoryHolidayWorkTimeIn;
	}
	
	@Override
	public int getStatutoryHolidayWorkTimeOut() {
		return statutoryHolidayWorkTimeOut;
	}
	
	@Override
	public int getPrescribedHolidayWorkTimeIn() {
		return prescribedHolidayWorkTimeIn;
	}
	
	@Override
	public int getPrescribedHolidayWorkTimeOut() {
		return prescribedHolidayWorkTimeOut;
	}
	
	@Override
	public int getTimesWork() {
		return timesWork;
	}
	
	@Override
	public void setDecreaseTime(int decreaseTime) {
		this.decreaseTime = decreaseTime;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setEndTime(Date endTime) {
		this.endTime = getDateClone(endTime);
	}
	
	@Override
	public void setActualEndTime(Date actualEndTime) {
		this.actualEndTime = getDateClone(actualEndTime);
	}
	
	@Override
	public void setLateDays(int lateDays) {
		this.lateDays = lateDays;
	}
	
	@Override
	public void setLateThirtyMinutesOrMore(int lateThirtyMinutesOrMore) {
		this.lateThirtyMinutesOrMore = lateThirtyMinutesOrMore;
	}
	
	@Override
	public void setLateLessThanThirtyMinutes(int lateLessThanThirtyMinutes) {
		this.lateLessThanThirtyMinutes = lateLessThanThirtyMinutes;
	}
	
	@Override
	public void setLateCertificate(String lateCertificate) {
		this.lateCertificate = lateCertificate;
	}
	
	@Override
	public void setLateComment(String lateComment) {
		this.lateComment = lateComment;
	}
	
	@Override
	public void setLeaveEarlyDays(int leaveEarlyDays) {
		this.leaveEarlyDays = leaveEarlyDays;
	}
	
	@Override
	public void setLeaveEarlyThirtyMinutesOrMore(int leaveEarlyThirtyMinutesOrMore) {
		this.leaveEarlyThirtyMinutesOrMore = leaveEarlyThirtyMinutesOrMore;
	}
	
	@Override
	public void setLeaveEarlyLessThanThirtyMinutes(int leaveEarlyLessThanThirtyMinutes) {
		this.leaveEarlyLessThanThirtyMinutes = leaveEarlyLessThanThirtyMinutes;
	}
	
	@Override
	public void setLateNightTime(int lateNightTime) {
		this.lateNightTime = lateNightTime;
	}
	
	@Override
	public void setNightWorkWithinPrescribedWork(int nightWorkWithinPrescribedWork) {
		this.nightWorkWithinPrescribedWork = nightWorkWithinPrescribedWork;
	}
	
	@Override
	public void setNightOvertimeWork(int nightOvertimeWork) {
		this.nightOvertimeWork = nightOvertimeWork;
	}
	
	@Override
	public void setNightWorkOnHoliday(int nightWorkOnHoliday) {
		this.nightWorkOnHoliday = nightWorkOnHoliday;
	}
	
	@Override
	public void setLateReason(String lateReason) {
		this.lateReason = lateReason;
	}
	
	@Override
	public void setLateTime(int lateTime) {
		this.lateTime = lateTime;
	}
	
	@Override
	public void setActualLateTime(int actualLateTime) {
		this.actualLateTime = actualLateTime;
	}
	
	@Override
	public void setLateThirtyMinutesOrMoreTime(int lateThirtyMinutesOrMoreTime) {
		this.lateThirtyMinutesOrMoreTime = lateThirtyMinutesOrMoreTime;
	}
	
	@Override
	public void setLateLessThanThirtyMinutesTime(int lateLessThanThirtyMinutesTime) {
		this.lateLessThanThirtyMinutesTime = lateLessThanThirtyMinutesTime;
	}
	
	@Override
	public void setLeaveEarlyCertificate(String leaveEarlyCertificate) {
		this.leaveEarlyCertificate = leaveEarlyCertificate;
	}
	
	@Override
	public void setLeaveEarlyComment(String leaveEarlyComment) {
		this.leaveEarlyComment = leaveEarlyComment;
	}
	
	@Override
	public void setLeaveEarlyReason(String leaveEarlyReason) {
		this.leaveEarlyReason = leaveEarlyReason;
	}
	
	@Override
	public void setLeaveEarlyTime(int leaveEarlyTime) {
		this.leaveEarlyTime = leaveEarlyTime;
	}
	
	@Override
	public void setActualLeaveEarlyTime(int actualLeaveEarlyTime) {
		this.actualLeaveEarlyTime = actualLeaveEarlyTime;
	}
	
	@Override
	public void setLeaveEarlyThirtyMinutesOrMoreTime(int leaveEarlyThirtyMinutesOrMoreTime) {
		this.leaveEarlyThirtyMinutesOrMoreTime = leaveEarlyThirtyMinutesOrMoreTime;
	}
	
	@Override
	public void setLeaveEarlyLessThanThirtyMinutesTime(int leaveEarlyLessThanThirtyMinutesTime) {
		this.leaveEarlyLessThanThirtyMinutesTime = leaveEarlyLessThanThirtyMinutesTime;
	}
	
	@Override
	public void setLegalWorkTime(int legalWorkTime) {
		this.legalWorkTime = legalWorkTime;
	}
	
	@Override
	public void setNightRestTime(int nightRestTime) {
		this.nightRestTime = nightRestTime;
	}
	
	@Override
	public void setLegalHolidayRestTime(int legalHolidayRestTime) {
		this.legalHolidayRestTime = legalHolidayRestTime;
	}
	
	@Override
	public void setPrescribedHolidayRestTime(int prescribedHolidayRestTime) {
		this.prescribedHolidayRestTime = prescribedHolidayRestTime;
	}
	
	@Override
	public void setOverRestTime(int overRestTime) {
		this.overRestTime = overRestTime;
	}
	
	@Override
	public void setOvertime(int overtime) {
		this.overtime = overtime;
	}
	
	@Override
	public void setOvertimeOut(int overtimeOut) {
		this.overtimeOut = overtimeOut;
	}
	
	@Override
	public void setWorkdayOvertimeIn(int workdayOvertimeIn) {
		this.workdayOvertimeIn = workdayOvertimeIn;
	}
	
	@Override
	public void setWorkdayOvertimeOut(int workdayOvertimeOut) {
		this.workdayOvertimeOut = workdayOvertimeOut;
	}
	
	@Override
	public void setPrescribedHolidayOvertimeIn(int prescribedHolidayOvertimeIn) {
		this.prescribedHolidayOvertimeIn = prescribedHolidayOvertimeIn;
	}
	
	@Override
	public void setPrescribedHolidayOvertimeOut(int prescribedHolidayOvertimeOut) {
		this.prescribedHolidayOvertimeOut = prescribedHolidayOvertimeOut;
	}
	
	@Override
	public void setPrivateTime(int privateTime) {
		this.privateTime = privateTime;
	}
	
	@Override
	public void setTimesOvertime(int timesOvertime) {
		this.timesOvertime = timesOvertime;
	}
	
	@Override
	public void setPublicTime(int publicTime) {
		this.publicTime = publicTime;
	}
	
	@Override
	public void setMinutelyHolidayATime(int minutelyHolidayATime) {
		this.minutelyHolidayATime = minutelyHolidayATime;
	}
	
	@Override
	public void setMinutelyHolidayBTime(int minutelyHolidayBTime) {
		this.minutelyHolidayBTime = minutelyHolidayBTime;
	}
	
	@Override
	public void setMinutelyHolidayA(int minutelyHolidayA) {
		this.minutelyHolidayA = minutelyHolidayA;
	}
	
	@Override
	public void setMinutelyHolidayB(int minutelyHolidayB) {
		this.minutelyHolidayB = minutelyHolidayB;
	}
	
	@Override
	public void setRestTime(int restTime) {
		this.restTime = restTime;
	}
	
	@Override
	public void setSpecificWorkTime(int specificWorkTime) {
		this.specificWorkTime = specificWorkTime;
	}
	
	@Override
	public void setStartTime(Date startTime) {
		this.startTime = getDateClone(startTime);
	}
	
	@Override
	public void setActualStartTime(Date actualStartTime) {
		this.actualStartTime = getDateClone(actualStartTime);
	}
	
	@Override
	public void setTmdAttendanceId(long tmdAttendanceId) {
		this.tmdAttendanceId = tmdAttendanceId;
	}
	
	@Override
	public void setWorkDate(Date workDate) {
		this.workDate = getDateClone(workDate);
	}
	
	@Override
	public void setTimesWork(int timesWork) {
		this.timesWork = timesWork;
	}
	
	@Override
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	@Override
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
	@Override
	public void setTimeComment(String timeComment) {
		this.timeComment = timeComment;
	}
	
	@Override
	public void setWorkDays(double workDays) {
		this.workDays = workDays;
	}
	
	@Override
	public void setWorkDaysForPaidLeave(int workDaysForPaidLeave) {
		this.workDaysForPaidLeave = workDaysForPaidLeave;
	}
	
	@Override
	public void setTotalWorkDaysForPaidLeave(int totalWorkDaysForPaidLeave) {
		this.totalWorkDaysForPaidLeave = totalWorkDaysForPaidLeave;
	}
	
	@Override
	public void setTimesHolidayWork(int timesHolidayWork) {
		this.timesHolidayWork = timesHolidayWork;
	}
	
	@Override
	public void setTimesLegalHolidayWork(int timesLegalHolidayWork) {
		this.timesLegalHolidayWork = timesLegalHolidayWork;
	}
	
	@Override
	public void setTimesPrescribedHolidayWork(int timesPrescribedHolidayWork) {
		this.timesPrescribedHolidayWork = timesPrescribedHolidayWork;
	}
	
	@Override
	public void setPaidLeaveDays(double paidLeaveDays) {
		this.paidLeaveDays = paidLeaveDays;
	}
	
	@Override
	public void setPaidLeaveHours(int paidLeaveHours) {
		this.paidLeaveHours = paidLeaveHours;
	}
	
	@Override
	public void setStockLeaveDays(double stockLeaveDays) {
		this.stockLeaveDays = stockLeaveDays;
	}
	
	@Override
	public void setCompensationDays(double compensationDays) {
		this.compensationDays = compensationDays;
	}
	
	@Override
	public void setLegalCompensationDays(double legalCompensationDays) {
		this.legalCompensationDays = legalCompensationDays;
	}
	
	@Override
	public void setPrescribedCompensationDays(double prescribedCompensationDays) {
		this.prescribedCompensationDays = prescribedCompensationDays;
	}
	
	@Override
	public void setNightCompensationDays(double nightCompensationDays) {
		this.nightCompensationDays = nightCompensationDays;
	}
	
	@Override
	public void setSpecialLeaveDays(double specialLeaveDays) {
		this.specialLeaveDays = specialLeaveDays;
	}
	
	@Override
	public void setSpecialLeaveHours(int specialLeaveHours) {
		this.specialLeaveHours = specialLeaveHours;
	}
	
	@Override
	public void setOtherLeaveDays(double otherLeaveDays) {
		this.otherLeaveDays = otherLeaveDays;
	}
	
	@Override
	public void setOtherLeaveHours(int otherLeaveHours) {
		this.otherLeaveHours = otherLeaveHours;
	}
	
	@Override
	public void setAbsenceDays(double absenceDays) {
		this.absenceDays = absenceDays;
	}
	
	@Override
	public void setAbsenceHours(int absenceHours) {
		this.absenceHours = absenceHours;
	}
	
	@Override
	public void setGrantedLegalCompensationDays(double grantedLegalCompensationDays) {
		this.grantedLegalCompensationDays = grantedLegalCompensationDays;
	}
	
	@Override
	public void setGrantedPrescribedCompensationDays(double grantedPrescribedCompensationDays) {
		this.grantedPrescribedCompensationDays = grantedPrescribedCompensationDays;
	}
	
	@Override
	public void setGrantedNightCompensationDays(double grantedNightCompensationDays) {
		this.grantedNightCompensationDays = grantedNightCompensationDays;
	}
	
	@Override
	public void setLegalHolidayWorkTimeWithCompensationDay(int legalHolidayWorkTimeWithCompensationDay) {
		this.legalHolidayWorkTimeWithCompensationDay = legalHolidayWorkTimeWithCompensationDay;
	}
	
	@Override
	public void setLegalHolidayWorkTimeWithoutCompensationDay(int legalHolidayWorkTimeWithoutCompensationDay) {
		this.legalHolidayWorkTimeWithoutCompensationDay = legalHolidayWorkTimeWithoutCompensationDay;
	}
	
	@Override
	public void setPrescribedHolidayWorkTimeWithCompensationDay(int prescribedHolidayWorkTimeWithCompensationDay) {
		this.prescribedHolidayWorkTimeWithCompensationDay = prescribedHolidayWorkTimeWithCompensationDay;
	}
	
	@Override
	public void setPrescribedHolidayWorkTimeWithoutCompensationDay(
			int prescribedHolidayWorkTimeWithoutCompensationDay) {
		this.prescribedHolidayWorkTimeWithoutCompensationDay = prescribedHolidayWorkTimeWithoutCompensationDay;
	}
	
	@Override
	public void setOvertimeInWithCompensationDay(int overtimeInWithCompensationDay) {
		this.overtimeInWithCompensationDay = overtimeInWithCompensationDay;
	}
	
	@Override
	public void setOvertimeInWithoutCompensationDay(int overtimeInWithoutCompensationDay) {
		this.overtimeInWithoutCompensationDay = overtimeInWithoutCompensationDay;
	}
	
	@Override
	public void setOvertimeOutWithCompensationDay(int overtimeOutWithCompensationDay) {
		this.overtimeOutWithCompensationDay = overtimeOutWithCompensationDay;
	}
	
	@Override
	public void setOvertimeOutWithoutCompensationDay(int overtimeOutWithoutCompensationDay) {
		this.overtimeOutWithoutCompensationDay = overtimeOutWithoutCompensationDay;
	}
	
	@Override
	public void setStatutoryHolidayWorkTimeIn(int statutoryHolidayWorkTimeIn) {
		this.statutoryHolidayWorkTimeIn = statutoryHolidayWorkTimeIn;
	}
	
	@Override
	public void setStatutoryHolidayWorkTimeOut(int statutoryHolidayWorkTimeOut) {
		this.statutoryHolidayWorkTimeOut = statutoryHolidayWorkTimeOut;
	}
	
	@Override
	public void setPrescribedHolidayWorkTimeIn(int prescribedHolidayWorkTimeIn) {
		this.prescribedHolidayWorkTimeIn = prescribedHolidayWorkTimeIn;
	}
	
	@Override
	public void setPrescribedHolidayWorkTimeOut(int prescribedHolidayWorkTimeOut) {
		this.prescribedHolidayWorkTimeOut = prescribedHolidayWorkTimeOut;
	}
	
	@Override
	public void setWorkTime(int workTime) {
		this.workTime = workTime;
	}
	
	@Override
	public int getDirectEnd() {
		return directEnd;
	}
	
	@Override
	public int getDirectStart() {
		return directStart;
	}
	
	@Override
	public void setDirectStart(int directStart) {
		this.directStart = directStart;
	}
	
	@Override
	public void setDirectEnd(int directEnd) {
		this.directEnd = directEnd;
	}
	
	@Override
	public int getGeneralWorkTime() {
		return generalWorkTime;
	}
	
	@Override
	public int getWorkTimeWithinPrescribedWorkTime() {
		return workTimeWithinPrescribedWorkTime;
	}
	
	@Override
	public int getContractWorkTime() {
		return contractWorkTime;
	}
	
	@Override
	public int getOvertimeIn() {
		return overtimeIn;
	}
	
	@Override
	public void setGeneralWorkTime(int generalWorkTime) {
		this.generalWorkTime = generalWorkTime;
	}
	
	@Override
	public void setWorkTimeWithinPrescribedWorkTime(int workTimeWithinPrescribedWorkTime) {
		this.workTimeWithinPrescribedWorkTime = workTimeWithinPrescribedWorkTime;
	}
	
	@Override
	public void setContractWorkTime(int contractWorkTime) {
		this.contractWorkTime = contractWorkTime;
	}
	
	@Override
	public void setOvertimeIn(int overtimeIn) {
		this.overtimeIn = overtimeIn;
	}
	
	@Override
	public int getOvertimeAfter() {
		return overtimeAfter;
	}
	
	@Override
	public int getOvertimeBefore() {
		return overtimeBefore;
	}
	
	@Override
	public void setOvertimeAfter(int overtimeAfter) {
		this.overtimeAfter = overtimeAfter;
	}
	
	@Override
	public void setOvertimeBefore(int overtimeBefore) {
		this.overtimeBefore = overtimeBefore;
	}
	
	@Override
	public int getShortUnpaid() {
		return shortUnpaid;
	}
	
	@Override
	public void setShortUnpaid(int shortUnpaid) {
		this.shortUnpaid = shortUnpaid;
	}
	
	@Override
	public int getForgotRecordWorkStart() {
		return forgotRecordWorkStart;
	}
	
	@Override
	public int getNotRecordWorkStart() {
		return notRecordWorkStart;
	}
	
	@Override
	public String getRemarks() {
		return remarks;
	}
	
	@Override
	public void setForgotRecordWorkStart(int forgotRecordWorkStart) {
		this.forgotRecordWorkStart = forgotRecordWorkStart;
	}
	
	@Override
	public void setNotRecordWorkStart(int notRecordWorkStart) {
		this.notRecordWorkStart = notRecordWorkStart;
	}
	
	@Override
	public void setRemarks(String remarks) {
		this.remarks = remarks;
	}
	
	@Override
	public Date getRequestDate() {
		// 勤務日を取得
		return getWorkDate();
	}
	
	@Override
	public void setRequestDate(Date requestDate) {
		// 勤務日を設定
		setWorkDate(requestDate);
	}
	
}
