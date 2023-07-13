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
package jp.mosp.time.dto.settings;

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.WorkflowNumberDtoInterface;
import jp.mosp.time.dto.base.RequestDateDtoInterface;
import jp.mosp.time.dto.base.WorkTypeCodeDtoInterface;

/**
 * 勤怠データDTOインターフェース。<br>
 */
public interface AttendanceDtoInterface extends BaseDtoInterface, PersonalIdDtoInterface, WorkflowNumberDtoInterface,
		RequestDateDtoInterface, WorkTypeCodeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdAttendanceId();
	
	/**
	 * @return 勤務日。
	 */
	Date getWorkDate();
	
	/**
	 * @return 勤務回数。
	 */
	int getTimesWork();
	
	/**
	 * @return 直行。
	 */
	int getDirectStart();
	
	/**
	 * @return 直帰。
	 */
	int getDirectEnd();
	
	/**
	 * @return 始業忘れ。
	 */
	int getForgotRecordWorkStart();
	
	/**
	 * @return その他の始業できなかった場合。
	 */
	int getNotRecordWorkStart();
	
	/**
	 * @return 始業時刻。
	 */
	Date getStartTime();
	
	/**
	 * @return 実始業時刻。
	 */
	Date getActualStartTime();
	
	/**
	 * @return 終業時刻。
	 */
	Date getEndTime();
	
	/**
	 * @return 実終業時刻。
	 */
	Date getActualEndTime();
	
	/**
	 * @return 遅刻日数。
	 */
	int getLateDays();
	
	/**
	 * @return 遅刻30分以上日数。
	 */
	int getLateThirtyMinutesOrMore();
	
	/**
	 * @return 遅刻30分未満日数。
	 */
	int getLateLessThanThirtyMinutes();
	
	/**
	 * @return 遅刻時間。
	 */
	int getLateTime();
	
	/**
	 * @return 実遅刻時間。
	 */
	int getActualLateTime();
	
	/**
	 * @return 遅刻30分以上時間。
	 */
	int getLateThirtyMinutesOrMoreTime();
	
	/**
	 * @return 遅刻30分未満時間。
	 */
	int getLateLessThanThirtyMinutesTime();
	
	/**
	 * @return 遅刻理由。
	 */
	String getLateReason();
	
	/**
	 * @return 遅刻証明書。
	 */
	String getLateCertificate();
	
	/**
	 * @return 遅刻コメント。
	 */
	String getLateComment();
	
	/**
	 * @return 早退日数。
	 */
	int getLeaveEarlyDays();
	
	/**
	 * @return 早退30分以上日数。
	 */
	int getLeaveEarlyThirtyMinutesOrMore();
	
	/**
	 * @return 早退30分未満日数。
	 */
	int getLeaveEarlyLessThanThirtyMinutes();
	
	/**
	 * @return 早退時間。
	 */
	int getLeaveEarlyTime();
	
	/**
	 * @return 実早退時間。
	 */
	int getActualLeaveEarlyTime();
	
	/**
	 * @return 早退30分以上時間。
	 */
	int getLeaveEarlyThirtyMinutesOrMoreTime();
	
	/**
	 * @return 早退30分未満時間。
	 */
	int getLeaveEarlyLessThanThirtyMinutesTime();
	
	/**
	 * @return 早退理由。
	 */
	String getLeaveEarlyReason();
	
	/**
	 * @return 早退証明書。
	 */
	String getLeaveEarlyCertificate();
	
	/**
	 * @return 早退コメント。
	 */
	String getLeaveEarlyComment();
	
	/**
	 * @return 勤務時間。
	 */
	int getWorkTime();
	
	/**
	 * @return 所定労働時間。
	 */
	int getGeneralWorkTime();
	
	/**
	 * @return 所定労働時間内労働時間。
	 */
	int getWorkTimeWithinPrescribedWorkTime();
	
	/**
	 * @return 契約勤務時間。
	 */
	int getContractWorkTime();
	
	/**
	 * @return 無給時短時間。
	 */
	int getShortUnpaid();
	
	/**
	 * @return 休憩時間。
	 */
	int getRestTime();
	
	/**
	 * @return 法定外休憩時間。
	 */
	int getOverRestTime();
	
	/**
	 * @return 深夜休憩時間。
	 */
	int getNightRestTime();
	
	/**
	 * @return 法定休出休憩時間。
	 */
	int getLegalHolidayRestTime();
	
	/**
	 * @return 所定休出休憩時間。
	 */
	int getPrescribedHolidayRestTime();
	
	/**
	 * @return 公用外出時間。
	 */
	int getPublicTime();
	
	/**
	 * @return 私用外出時間。
	 */
	int getPrivateTime();
	
	/**
	 * @return 分単位休暇A時間。
	 */
	int getMinutelyHolidayATime();
	
	/**
	 * @return 分単位休暇B時間。
	 */
	int getMinutelyHolidayBTime();
	
	/**
	 * @return 分単位休暇A全休。
	 */
	int getMinutelyHolidayA();
	
	/**
	 * @return 分単位休暇B全休。
	 */
	int getMinutelyHolidayB();
	
	/**
	 * @return 残業回数。
	 */
	int getTimesOvertime();
	
	/**
	 * @return 残業時間。
	 */
	int getOvertime();
	
	/**
	 * @return 前残業時間。
	 */
	int getOvertimeBefore();
	
	/**
	 * @return 後残業時間。
	 */
	int getOvertimeAfter();
	
	/**
	 * @return 法定内残業時間。
	 */
	int getOvertimeIn();
	
	/**
	 * @return 法定外残業時間。
	 */
	int getOvertimeOut();
	
	/**
	 * @return 平日法定時間内残業時間。
	 */
	int getWorkdayOvertimeIn();
	
	/**
	 * @return 平日法定時間外残業時間。
	 */
	int getWorkdayOvertimeOut();
	
	/**
	 * @return 所定休日法定時間内残業時間。
	 */
	int getPrescribedHolidayOvertimeIn();
	
	/**
	 * @return 所定休日法定時間外残業時間。
	 */
	int getPrescribedHolidayOvertimeOut();
	
	/**
	 * @return 深夜勤務時間。
	 */
	int getLateNightTime();
	
	/**
	 * @return 深夜所定労働時間内時間。
	 */
	int getNightWorkWithinPrescribedWork();
	
	/**
	 * @return 深夜時間外時間。
	 */
	int getNightOvertimeWork();
	
	/**
	 * @return 深夜休日労働時間。
	 */
	int getNightWorkOnHoliday();
	
	/**
	 * @return 所定休日勤務時間。
	 */
	int getSpecificWorkTime();
	
	/**
	 * @return 法定休日勤務時間。
	 */
	int getLegalWorkTime();
	
	/**
	 * @return 減額対象時間。
	 */
	int getDecreaseTime();
	
	/**
	 * @return 勤怠コメント。
	 */
	String getTimeComment();
	
	/**
	 * @return 備考。
	 */
	String getRemarks();
	
	/**
	 * @return 出勤日数。
	 */
	double getWorkDays();
	
	/**
	 * @return 有給休暇用出勤日数。
	 */
	int getWorkDaysForPaidLeave();
	
	/**
	 * @return 有給休暇用全労働日。
	 */
	int getTotalWorkDaysForPaidLeave();
	
	/**
	 * @return 休日出勤回数。
	 */
	int getTimesHolidayWork();
	
	/**
	 * @return 法定休日出勤回数。
	 */
	int getTimesLegalHolidayWork();
	
	/**
	 * @return 所定休日出勤回数。
	 */
	int getTimesPrescribedHolidayWork();
	
	/**
	 * @return 有給休暇日数。
	 */
	double getPaidLeaveDays();
	
	/**
	 * @return 有給休暇時間数。
	 */
	int getPaidLeaveHours();
	
	/**
	 * @return ストック休暇日数。
	 */
	double getStockLeaveDays();
	
	/**
	 * @return 代休日数。
	 */
	double getCompensationDays();
	
	/**
	 * @return 法定代休日数。
	 */
	double getLegalCompensationDays();
	
	/**
	 * @return 所定代休日数。
	 */
	double getPrescribedCompensationDays();
	
	/**
	 * @return 深夜代休日数。
	 */
	double getNightCompensationDays();
	
	/**
	 * @return 特別休暇日数。
	 */
	double getSpecialLeaveDays();
	
	/**
	 * @return 特別休暇時間数。
	 */
	int getSpecialLeaveHours();
	
	/**
	 * @return その他休暇日数。
	 */
	double getOtherLeaveDays();
	
	/**
	 * @return その他休暇時間数。
	 */
	int getOtherLeaveHours();
	
	/**
	 * @return 欠勤日数。
	 */
	double getAbsenceDays();
	
	/**
	 * @return 欠勤時間数。
	 */
	int getAbsenceHours();
	
	/**
	 * @return 法定代休発生日数。
	 */
	double getGrantedLegalCompensationDays();
	
	/**
	 * @return 所定代休発生日数。
	 */
	double getGrantedPrescribedCompensationDays();
	
	/**
	 * @return 深夜代休発生日数。
	 */
	double getGrantedNightCompensationDays();
	
	/**
	 * @return 法定休出時間(代休あり)。
	 */
	int getLegalHolidayWorkTimeWithCompensationDay();
	
	/**
	 * @return 法定休出時間(代休なし)。
	 */
	int getLegalHolidayWorkTimeWithoutCompensationDay();
	
	/**
	 * @return 所定休出時間(代休あり)。
	 */
	int getPrescribedHolidayWorkTimeWithCompensationDay();
	
	/**
	 * @return 所定休出時間(代休なし)。
	 */
	int getPrescribedHolidayWorkTimeWithoutCompensationDay();
	
	/**
	 * @return 法定労働時間内残業時間(代休あり)。
	 */
	int getOvertimeInWithCompensationDay();
	
	/**
	 * @return 法定労働時間内残業時間(代休なし)。
	 */
	int getOvertimeInWithoutCompensationDay();
	
	/**
	 * @return 法定労働時間外残業時間(代休あり)。
	 */
	int getOvertimeOutWithCompensationDay();
	
	/**
	 * @return 法定労働時間外残業時間(代休なし)。
	 */
	int getOvertimeOutWithoutCompensationDay();
	
	/**
	 * @return 所定労働時間内法定休日労働時間。
	 */
	int getStatutoryHolidayWorkTimeIn();
	
	/**
	 * @return 所定労働時間外法定休日労働時間。
	 */
	int getStatutoryHolidayWorkTimeOut();
	
	/**
	 * @return 所定労働時間内所定休日労働時間。
	 */
	int getPrescribedHolidayWorkTimeIn();
	
	/**
	 * @return 所定労働時間外所定休日労働時間。
	 */
	int getPrescribedHolidayWorkTimeOut();
	
	/**
	 * @param tmdAttendanceId セットする レコード識別ID。
	 */
	void setTmdAttendanceId(long tmdAttendanceId);
	
	/**
	 * @param workDate セットする 勤務日。
	 */
	void setWorkDate(Date workDate);
	
	/**
	 * @param timesWork セットする 勤務回数。
	 */
	void setTimesWork(int timesWork);
	
	/**
	 * @param directStart セットする 直行。
	 */
	void setDirectStart(int directStart);
	
	/**
	 * @param directEnd セットする 直帰。
	 */
	void setDirectEnd(int directEnd);
	
	/**
	 * @param forgotRecordWorkStart セットする 始業忘れ。
	 */
	void setForgotRecordWorkStart(int forgotRecordWorkStart);
	
	/**
	 * @param notRecordWorkStart セットする その他の始業できなかった場合。
	 */
	void setNotRecordWorkStart(int notRecordWorkStart);
	
	/**
	 * @param startTime セットする 始業時刻。
	 */
	void setStartTime(Date startTime);
	
	/**
	 * @param actualStartTime セットする 実始業時刻。
	 */
	void setActualStartTime(Date actualStartTime);
	
	/**
	 * @param endTime セットする 終業時刻。
	 */
	void setEndTime(Date endTime);
	
	/**
	 * @param actualEndTime セットする 実終業時刻。
	 */
	void setActualEndTime(Date actualEndTime);
	
	/**
	 * @param lateDays セットする 遅刻日数。
	 */
	void setLateDays(int lateDays);
	
	/**
	 * @param lateThirtyMinutesOrMore セットする 遅刻30分以上日数。
	 */
	void setLateThirtyMinutesOrMore(int lateThirtyMinutesOrMore);
	
	/**
	 * @param lateLessThanThirtyMinutes セットする 遅刻30分未満日数。
	 */
	void setLateLessThanThirtyMinutes(int lateLessThanThirtyMinutes);
	
	/**
	 * @param lateTime セットする 遅刻時間。
	 */
	void setLateTime(int lateTime);
	
	/**
	 * @param actualLateTime セットする 実遅刻時間。
	 */
	void setActualLateTime(int actualLateTime);
	
	/**
	 * @param lateThirtyMinutesOrMoreTime セットする 遅刻30分以上時間。
	 */
	void setLateThirtyMinutesOrMoreTime(int lateThirtyMinutesOrMoreTime);
	
	/**
	 * @param lateLessThanThirtyMinutesTime セットする 遅刻30分未満時間。
	 */
	void setLateLessThanThirtyMinutesTime(int lateLessThanThirtyMinutesTime);
	
	/**
	 * @param lateReason セットする 遅刻理由。
	 */
	void setLateReason(String lateReason);
	
	/**
	 * @param lateCertificate セットする 遅刻証明書。
	 */
	void setLateCertificate(String lateCertificate);
	
	/**
	 * @param lateComment セットする 遅刻コメント。
	 */
	void setLateComment(String lateComment);
	
	/**
	 * @param leaveEarlyDays セットする 早退日数。
	 */
	void setLeaveEarlyDays(int leaveEarlyDays);
	
	/**
	 * @param leaveEarlyThirtyMinutesOrMore セットする 早退30分以上日数。
	 */
	void setLeaveEarlyThirtyMinutesOrMore(int leaveEarlyThirtyMinutesOrMore);
	
	/**
	 * @param leaveEarlyLessThanThirtyMinutes セットする 早退30分未満日数。
	 */
	void setLeaveEarlyLessThanThirtyMinutes(int leaveEarlyLessThanThirtyMinutes);
	
	/**
	 * @param leaveEarlyTime セットする 早退時間。
	 */
	void setLeaveEarlyTime(int leaveEarlyTime);
	
	/**
	 * @param actualLeaveEarlyTime セットする 実早退時間。
	 */
	void setActualLeaveEarlyTime(int actualLeaveEarlyTime);
	
	/**
	 * @param leaveEarlyThirtyMinutesOrMoreTime セットする 早退30分以上時間。
	 */
	void setLeaveEarlyThirtyMinutesOrMoreTime(int leaveEarlyThirtyMinutesOrMoreTime);
	
	/**
	 * @param leaveEarlyLessThanThirtyMinutesTime セットする 早退30分未満時間。
	 */
	void setLeaveEarlyLessThanThirtyMinutesTime(int leaveEarlyLessThanThirtyMinutesTime);
	
	/**
	 * @param leaveEarlyReason セットする 早退理由。
	 */
	void setLeaveEarlyReason(String leaveEarlyReason);
	
	/**
	 * @param leaveEarlyCertificate セットする 早退証明書。
	 */
	void setLeaveEarlyCertificate(String leaveEarlyCertificate);
	
	/**
	 * @param leaveEarlyComment セットする 早退コメント。
	 */
	void setLeaveEarlyComment(String leaveEarlyComment);
	
	/**
	 * @param workTime セットする 勤務時間。
	 */
	void setWorkTime(int workTime);
	
	/**
	 * @param generalWorkTime セットする 所定労働時間。
	 */
	void setGeneralWorkTime(int generalWorkTime);
	
	/**
	 * @param workTimeWithinPrescribedWorkTime セットする 所定労働時間内労働時間。
	 */
	void setWorkTimeWithinPrescribedWorkTime(int workTimeWithinPrescribedWorkTime);
	
	/**
	 * @param contractWorkTime セットする 契約勤務時間。
	 */
	void setContractWorkTime(int contractWorkTime);
	
	/**
	 * @param shortUnpaid セットする 無給時短時間。
	 */
	void setShortUnpaid(int shortUnpaid);
	
	/**
	 * @param restTime セットする 休憩時間。
	 */
	void setRestTime(int restTime);
	
	/**
	 * @param overtimeIn セットする 法定内残業時間。
	 */
	void setOvertimeIn(int overtimeIn);
	
	/**
	 * @param overRestTime セットする 法定外休憩時間。
	 */
	void setOverRestTime(int overRestTime);
	
	/**
	 * @param nightRestTime セットする 深夜休憩時間。
	 */
	void setNightRestTime(int nightRestTime);
	
	/**
	 * @param legalHolidayRestTime セットする 法定休出休憩時間。
	 */
	void setLegalHolidayRestTime(int legalHolidayRestTime);
	
	/**
	 * @param prescribedHolidayRestTime セットする 所定休出休憩時間。
	 */
	void setPrescribedHolidayRestTime(int prescribedHolidayRestTime);
	
	/**
	 * @param publicTime セットする 公用外出時間。
	 */
	void setPublicTime(int publicTime);
	
	/**
	 * @param privateTime セットする 私用外出時間。
	 */
	void setPrivateTime(int privateTime);
	
	/**
	 * @param minutelyHolidayATime セットする 分単位休暇A時間。
	 */
	void setMinutelyHolidayATime(int minutelyHolidayATime);
	
	/**
	 * @param minutelyHolidayBTime セットする 分単位休暇B時間。
	 */
	void setMinutelyHolidayBTime(int minutelyHolidayBTime);
	
	/**
	 * @param minutelyHolidayA セットする 分単位休暇A全休。
	 */
	void setMinutelyHolidayA(int minutelyHolidayA);
	
	/**
	 * @param minutelyHolidayB セットする 分単位休暇B全休。
	 */
	void setMinutelyHolidayB(int minutelyHolidayB);
	
	/**
	 * @param timesOvertime セットする 残業回数。
	 */
	void setTimesOvertime(int timesOvertime);
	
	/**
	 * @param overtime セットする 残業時間。
	 */
	void setOvertime(int overtime);
	
	/**
	 * @param overtimeBefore セットする 前残業時間。
	 */
	void setOvertimeBefore(int overtimeBefore);
	
	/**
	 * @param overtimeAfter セットする 後残業時間。
	 */
	void setOvertimeAfter(int overtimeAfter);
	
	/**
	 * @param overtimeOut セットする 法定外残業時間。
	 */
	void setOvertimeOut(int overtimeOut);
	
	/**
	 * @param workdayOvertimeIn セットする 平日法定時間内残業時間。
	 */
	void setWorkdayOvertimeIn(int workdayOvertimeIn);
	
	/**
	 * @param workdayOvertimeOut セットする 平日法定時間外残業時間。
	 */
	void setWorkdayOvertimeOut(int workdayOvertimeOut);
	
	/**
	 * @param prescribedHolidayOvertimeIn セットする 所定休日法定時間内残業時間。
	 */
	void setPrescribedHolidayOvertimeIn(int prescribedHolidayOvertimeIn);
	
	/**
	 * @param prescribedHolidayOvertimeOut セットする 所定休日法定時間外残業時間。
	 */
	void setPrescribedHolidayOvertimeOut(int prescribedHolidayOvertimeOut);
	
	/**
	 * @param lateNightTime セットする 深夜勤務時間。
	 */
	void setLateNightTime(int lateNightTime);
	
	/**
	 * @param nightWorkWithinPrescribedWork セットする 深夜所定労働時間内時間。
	 */
	void setNightWorkWithinPrescribedWork(int nightWorkWithinPrescribedWork);
	
	/**
	 * @param nightOvertimeWork セットする 深夜時間外時間。
	 */
	void setNightOvertimeWork(int nightOvertimeWork);
	
	/**
	 * @param nightWorkOnHoliday セットする 深夜休日労働時間。
	 */
	void setNightWorkOnHoliday(int nightWorkOnHoliday);
	
	/**
	 * @param specificWorkTime セットする 所定休日勤務時間。
	 */
	void setSpecificWorkTime(int specificWorkTime);
	
	/**
	 * @param legalWorkTime セットする 法定休日勤務時間。
	 */
	void setLegalWorkTime(int legalWorkTime);
	
	/**
	 * @param decreaseTime セットする 減額対象時間。
	 */
	void setDecreaseTime(int decreaseTime);
	
	/**
	 * @param timeComment セットする 勤怠コメント。
	 */
	void setTimeComment(String timeComment);
	
	/**
	 * @param remarks セットする 備考。
	 */
	void setRemarks(String remarks);
	
	/**
	 * @param workDays セットする 出勤日数。
	 */
	void setWorkDays(double workDays);
	
	/**
	 * @param workDaysForPaidLeave セットする 有給休暇用出勤日数。
	 */
	void setWorkDaysForPaidLeave(int workDaysForPaidLeave);
	
	/**
	 * @param totalWorkDaysForPaidLeave セットする 有給休暇用全労働日。
	 */
	void setTotalWorkDaysForPaidLeave(int totalWorkDaysForPaidLeave);
	
	/**
	 * @param timesHolidayWork セットする 休日出勤回数。
	 */
	void setTimesHolidayWork(int timesHolidayWork);
	
	/**
	 * @param timesLegalHolidayWork セットする 法定休日出勤回数。
	 */
	void setTimesLegalHolidayWork(int timesLegalHolidayWork);
	
	/**
	 * @param timesPrescribedHolidayWork セットする 所定休日出勤回数。
	 */
	void setTimesPrescribedHolidayWork(int timesPrescribedHolidayWork);
	
	/**
	 * @param paidLeaveDays セットする 有給休暇日数。
	 */
	void setPaidLeaveDays(double paidLeaveDays);
	
	/**
	 * @param paidLeaveHours セットする 有給休暇時間数。
	 */
	void setPaidLeaveHours(int paidLeaveHours);
	
	/**
	 * @param stockLeaveDays セットする ストック休暇日数。
	 */
	void setStockLeaveDays(double stockLeaveDays);
	
	/**
	 * @param compensationDays セットする 代休日数。
	 */
	void setCompensationDays(double compensationDays);
	
	/**
	 * @param legalCompensationDays セットする 法定代休日数。
	 */
	void setLegalCompensationDays(double legalCompensationDays);
	
	/**
	 * @param prescribedCompensationDays セットする 所定代休日数。
	 */
	void setPrescribedCompensationDays(double prescribedCompensationDays);
	
	/**
	 * @param nightCompensationDays セットする 深夜代休日数。
	 */
	void setNightCompensationDays(double nightCompensationDays);
	
	/**
	 * @param specialLeaveDays セットする 特別休暇日数。
	 */
	void setSpecialLeaveDays(double specialLeaveDays);
	
	/**
	 * @param specialLeaveHours セットする 特別休暇時間数。
	 */
	void setSpecialLeaveHours(int specialLeaveHours);
	
	/**
	 * @param otherLeaveDays セットする その他休暇日数。
	 */
	void setOtherLeaveDays(double otherLeaveDays);
	
	/**
	 * @param otherLeaveHours セットする その他休暇時間数。
	 */
	void setOtherLeaveHours(int otherLeaveHours);
	
	/**
	 * @param absenceDays セットする 欠勤日数。
	 */
	void setAbsenceDays(double absenceDays);
	
	/**
	 * @param absenceHours セットする 欠勤時間数。
	 */
	void setAbsenceHours(int absenceHours);
	
	/**
	 * @param grantedLegalCompensationDays セットする 法定代休発生日数。
	 */
	void setGrantedLegalCompensationDays(double grantedLegalCompensationDays);
	
	/**
	 * @param grantedPrescribedCompensationDays セットする 所定代休発生日数。
	 */
	void setGrantedPrescribedCompensationDays(double grantedPrescribedCompensationDays);
	
	/**
	 * @param grantedNightCompensationDays セットする 深夜代休発生日数。
	 */
	void setGrantedNightCompensationDays(double grantedNightCompensationDays);
	
	/**
	 * @param legalHolidayWorkTimeWithCompensationDay セットする 法定休出時間(代休あり)。
	 */
	void setLegalHolidayWorkTimeWithCompensationDay(int legalHolidayWorkTimeWithCompensationDay);
	
	/**
	 * @param legalHolidayWorkTimeWithoutCompensationDay セットする 法定休出時間(代休なし)。
	 */
	void setLegalHolidayWorkTimeWithoutCompensationDay(int legalHolidayWorkTimeWithoutCompensationDay);
	
	/**
	 * @param prescribedHolidayWorkTimeWithCompensationDay セットする 所定休出時間(代休あり)。
	 */
	void setPrescribedHolidayWorkTimeWithCompensationDay(int prescribedHolidayWorkTimeWithCompensationDay);
	
	/**
	 * @param prescribedHolidayWorkTimeWithoutCompensationDay セットする 所定休出時間(代休なし)。
	 */
	void setPrescribedHolidayWorkTimeWithoutCompensationDay(int prescribedHolidayWorkTimeWithoutCompensationDay);
	
	/**
	 * @param overtimeInWithCompensationDay セットする 法定労働時間内残業時間(代休あり)。
	 */
	void setOvertimeInWithCompensationDay(int overtimeInWithCompensationDay);
	
	/**
	 * @param overtimeInWithoutCompensationDay セットする 法定労働時間内残業時間(代休なし)。
	 */
	void setOvertimeInWithoutCompensationDay(int overtimeInWithoutCompensationDay);
	
	/**
	 * @param overtimeOutWithCompensationDay セットする 法定労働時間外残業時間(代休あり)。
	 */
	void setOvertimeOutWithCompensationDay(int overtimeOutWithCompensationDay);
	
	/**
	 * @param overtimeOutWithoutCompensationDay セットする 法定労働時間外残業時間(代休なし)。
	 */
	void setOvertimeOutWithoutCompensationDay(int overtimeOutWithoutCompensationDay);
	
	/**
	 * @param statutoryHolidayWorkTimeIn セットする 所定労働時間内法定休日労働時間。
	 */
	void setStatutoryHolidayWorkTimeIn(int statutoryHolidayWorkTimeIn);
	
	/**
	 * @param statutoryHolidayWorkTimeOut セットする 所定労働時間外法定休日労働時間。
	 */
	void setStatutoryHolidayWorkTimeOut(int statutoryHolidayWorkTimeOut);
	
	/**
	 * @param prescribedHolidayWorkTimeIn セットする 所定労働時間内所定休日労働時間。
	 */
	void setPrescribedHolidayWorkTimeIn(int prescribedHolidayWorkTimeIn);
	
	/**
	 * @param prescribedHolidayWorkTimeOut セットする 所定労働時間外所定休日労働時間。
	 */
	void setPrescribedHolidayWorkTimeOut(int prescribedHolidayWorkTimeOut);
	
}
