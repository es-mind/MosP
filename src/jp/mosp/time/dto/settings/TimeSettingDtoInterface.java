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
package jp.mosp.time.dto.settings;

import java.util.Date;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 勤怠設定管理DTOインターフェース
 */
public interface TimeSettingDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmTimeSettingId();
	
	/**
	 * @return 勤怠設定コード。
	 */
	String getWorkSettingCode();
	
	/**
	 * @return 勤怠設定名称。
	 */
	String getWorkSettingName();
	
	/**
	 * @return 勤怠設定略称。
	 */
	String getWorkSettingAbbr();
	
	/**
	 * @return 締日コード。
	 */
	String getCutoffCode();
	
	/**
	 * @return 勤怠管理対象フラグ。
	 */
	int getTimeManagementFlag();
	
	/**
	 * @return 日々申請対象フラグ。
	 */
	int getDailyApprovalFlag();
	
	/**
	 * @return 勤務前残業フラグ。
	 */
	int getBeforeOvertimeFlag();
	
	/**
	 * @return 所定休日取扱。
	 */
	int getSpecificHolidayHandling();
	
	/**
	 * @return 日出勤丸め単位。
	 */
	int getRoundDailyStartUnit();
	
	/**
	 * @return 日出勤丸め。
	 */
	int getRoundDailyStart();
	
	/**
	 * @return 日退勤丸め単位。
	 */
	int getRoundDailyEndUnit();
	
	/**
	 * @return 日退勤丸め。
	 */
	int getRoundDailyEnd();
	
	/**
	 * @return 日勤務時間丸め単位。
	 */
	int getRoundDailyTimeWork();
	
	/**
	 * @return 日勤務時間丸め。
	 */
	int getRoundDailyWork();
	
	/**
	 * @return 日休憩入丸め単位。
	 */
	int getRoundDailyRestStartUnit();
	
	/**
	 * @return 日休憩入丸め。
	 */
	int getRoundDailyRestStart();
	
	/**
	 * @return 日休憩戻丸め単位。
	 */
	int getRoundDailyRestEndUnit();
	
	/**
	 * @return 日休憩戻丸め。
	 */
	int getRoundDailyRestEnd();
	
	/**
	 * @return 日休憩時間丸め単位。
	 */
	int getRoundDailyRestTimeUnit();
	
	/**
	 * @return 日休憩時間丸め。
	 */
	int getRoundDailyRestTime();
	
	/**
	 * @return 日遅刻丸め単位。
	 */
	int getRoundDailyLateUnit();
	
	/**
	 * @return 日遅刻丸め。
	 */
	int getRoundDailyLate();
	
	/**
	 * @return 日早退丸め単位。
	 */
	int getRoundDailyLeaveEarlyUnit();
	
	/**
	 * @return 日早退丸め。
	 */
	int getRoundDailyLeaveEarly();
	
	/**
	 * @return 日私用外出入丸め単位。
	 */
	int getRoundDailyPrivateStartUnit();
	
	/**
	 * @return 日私用外出入丸め。
	 */
	int getRoundDailyPrivateStart();
	
	/**
	 * @return 日私用外出戻丸め単位。
	 */
	int getRoundDailyPrivateEndUnit();
	
	/**
	 * @return 日私用外出戻丸め。
	 */
	int getRoundDailyPrivateEnd();
	
	/**
	 * @return 日公用外出入り丸め単位。
	 */
	int getRoundDailyPublicStartUnit();
	
	/**
	 * @return 日公用外出入り丸め。
	 */
	int getRoundDailyPublicStart();
	
	/**
	 * @return 日公用外出戻り丸め単位。
	 */
	int getRoundDailyPublicEndUnit();
	
	/**
	 * @return 日公用外出戻丸め。
	 */
	int getRoundDailyPublicEnd();
	
	/**
	 * @return 日減額対象丸め単位。
	 */
	int getRoundDailyDecreaseTimeUnit();
	
	/**
	 * @return 日減額対象時間丸め。
	 */
	int getRoundDailyDecreaseTime();
	
	/**
	 * @return 月勤務時間丸め単位。
	 */
	int getRoundMonthlyWorkUnit();
	
	/**
	 * @return 月勤務時間丸め。
	 */
	int getRoundMonthlyWork();
	
	/**
	 * @return 月休憩時間丸め単位。
	 */
	int getRoundMonthlyRestUnit();
	
	/**
	 * @return 月休憩時間丸め。
	 */
	int getRoundMonthlyRest();
	
	/**
	 * @return 月遅刻丸め単位。
	 */
	int getRoundMonthlyLateUnit();
	
	/**
	 * @return 月遅刻時間丸め。
	 */
	int getRoundMonthlyLate();
	
	/**
	 * @return 月早退丸め単位。
	 */
	int getRoundMonthlyEarlyUnit();
	
	/**
	 * @return 月早退丸め。
	 */
	int getRoundMonthlyEarly();
	
	/**
	 * @return 月私用外出丸め単位。
	 */
	int getRoundMonthlyPrivateTime();
	
	/**
	 * @return 月私用外出時間丸め。
	 */
	int getRoundMonthlyPrivate();
	
	/**
	 * @return 月公用外出丸め単位。
	 */
	int getRoundMonthlyPublicTime();
	
	/**
	 * @return 月公用外出時間丸め。
	 */
	int getRoundMonthlyPublic();
	
	/**
	 * @return 月減額対象丸め単位。
	 */
	int getRoundMonthlyDecreaseTime();
	
	/**
	 * @return 月減額対象時間丸め。
	 */
	int getRoundMonthlyDecrease();
	
	/**
	 * @return 週の起算曜日。
	 */
	int getStartWeek();
	
	/**
	 * @return 月の起算日。
	 */
	int getStartMonth();
	
	/**
	 * @return 年の起算月。
	 */
	int getStartYear();
	
	/**
	 * @return 所定労働時間。
	 */
	//Time getGeneralWorkTime();
	Date getGeneralWorkTime();
	
	/**
	 * @return 一日の起算時。
	 */
	//Time getStartDayTime();
	Date getStartDayTime();
	
	/**
	 * @return 遅刻早退限度時間(全日)。
	 */
	//Time getLateEarlyFull();
	Date getLateEarlyFull();
	
	/**
	 * @return 遅刻早退限度時間(半日)。
	 */
	//Time getLateEarlyHalf();
	Date getLateEarlyHalf();
	
	/**
	 * @return 振休取得期限月(休出前)。
	 */
	int getTransferAheadLimitMonth();
	
	/**
	 * @return 振休取得期限日(休出前)。
	 */
	int getTransferAheadLimitDate();
	
	/**
	 * @return 振休取得期限月(休出後)。
	 */
	int getTransferLaterLimitMonth();
	
	/**
	 * @return 振休取得期限日(休出後)。
	 */
	int getTransferLaterLimitDate();
	
	/**
	 * @return 代休取得期限月。
	 */
	int getSubHolidayLimitMonth();
	
	/**
	 * @return 代休取得期限日。
	 */
	int getSubHolidayLimitDate();
	
	/**
	 * @return 半休入替取得(振休)。
	 */
	int getTransferExchange();
	
	/**
	 * @return 半休入替取得(代休)。
	 */
	int getSubHolidayExchange();
	
	/**
	 * @return 代休基準時間(全休)。
	 */
	Date getSubHolidayAllNorm();
	
	/**
	 * @return 代休基準時間(半休)。
	 */
	Date getSubHolidayHalfNorm();
	
	/**
	 * @return 60時間超割増機能。
	 */
	int getSixtyHourFunctionFlag();
	
	/**
	 * @return 60時間超代替休暇。
	 */
	int getSixtyHourAlternativeFlag();
	
	/**
	 * @return 月60時間超割増。
	 */
	int getMonthSixtyHourSurcharge();
	
	/**
	 * @return 平日残業割増。
	 */
	int getWeekdayOver();
	
	/**
	 * @return 代替休暇平日。
	 */
	int getWeekdayAlternative();
	
	/**
	 * @return 代替休暇放棄。
	 */
	int getAlternativeCancel();
	
	/**
	 * @return 代替休暇所定休日。
	 */
	int getAlternativeSpecific();
	
	/**
	 * @return 代替休暇法定休日。
	 */
	int getAlternativeLegal();
	
	/**
	 * @return 所定休日割増率。
	 */
	int getSpecificHoliday();
	
	/**
	 * @return 法定休日割増率。
	 */
	int getLegalHoliday();
	
	/**
	 * @return 見込月。
	 */
	String getProspectsMonths();
	
	/**
	 * @return 締日略称。
	 */
	String getCutoffAbbr();
	
	/**
	 * @param tmmTimeSettingId セットする レコード識別ID。
	 */
	void setTmmTimeSettingId(long tmmTimeSettingId);
	
	/**
	 * @param workSettingCode セットする 勤怠設定コード。
	 */
	void setWorkSettingCode(String workSettingCode);
	
	/**
	 * @param workSettingName セットする 勤怠設定名称。
	 */
	void setWorkSettingName(String workSettingName);
	
	/**
	 * @param workSettingAbbr セットする 勤怠設定略称。
	 */
	void setWorkSettingAbbr(String workSettingAbbr);
	
	/**
	 * @param cutoffCode セットする 締日コード。
	 */
	void setCutoffCode(String cutoffCode);
	
	/**
	 * @param timeManagementFlag セットする 勤怠管理対象フラグ。
	 */
	void setTimeManagementFlag(int timeManagementFlag);
	
	/**
	 * @param dailyApprovalFlag セットする 日々申請対象フラグ。
	 */
	void setDailyApprovalFlag(int dailyApprovalFlag);
	
	/**
	 * @param beforeOvertimeFlag セットする 勤務前残業フラグ。
	 */
	void setBeforeOvertimeFlag(int beforeOvertimeFlag);
	
	/**
	 * @param specificHolidayHandling セットする 所定休日取扱。
	 */
	void setSpecificHolidayHandling(int specificHolidayHandling);
	
	/**
	 * @param roundDailyStartUnit セットする 日出勤丸め単位。
	 */
	void setRoundDailyStartUnit(int roundDailyStartUnit);
	
	/**
	 * @param roundDailyStart セットする 日出勤丸め。
	 */
	void setRoundDailyStart(int roundDailyStart);
	
	/**
	 * @param roundDailyEndUnit セットする 日退勤丸め単位。
	 */
	void setRoundDailyEndUnit(int roundDailyEndUnit);
	
	/**
	 * @param roundDailyEnd セットする 日退勤丸め。
	 */
	void setRoundDailyEnd(int roundDailyEnd);
	
	/**
	 * @param roundDailyTimeWork セットする 日勤務時間丸め単位。
	 */
	void setRoundDailyTimeWork(int roundDailyTimeWork);
	
	/**
	 * @param roundDailyWork セットする 日勤務時間丸め。
	 */
	void setRoundDailyWork(int roundDailyWork);
	
	/**
	 * @param roundDailyRestStartUnit セットする 日休憩入丸め単位。
	 */
	void setRoundDailyRestStartUnit(int roundDailyRestStartUnit);
	
	/**
	 * @param roundDailyRestStart セットする 日休憩入丸め。
	 */
	void setRoundDailyRestStart(int roundDailyRestStart);
	
	/**
	 * @param roundDailyRestEndUnit セットする 日休憩戻丸め単位。
	 */
	void setRoundDailyRestEndUnit(int roundDailyRestEndUnit);
	
	/**
	 * @param roundDailyRestEnd セットする 日休憩戻丸め。
	 */
	void setRoundDailyRestEnd(int roundDailyRestEnd);
	
	/**
	 * @param roundDailyRestTimeUnit セットする 日休憩時間丸め単位。
	 */
	void setRoundDailyRestTimeUnit(int roundDailyRestTimeUnit);
	
	/**
	 * @param roundDailyRestTime セットする 日休憩時間丸め。
	 */
	void setRoundDailyRestTime(int roundDailyRestTime);
	
	/**
	 * @param roundDailyLateUnit セットする 日遅刻丸め単位。
	 */
	void setRoundDailyLateUnit(int roundDailyLateUnit);
	
	/**
	 * @param roundDailyLate セットする 日遅刻丸め。
	 */
	void setRoundDailyLate(int roundDailyLate);
	
	/**
	 * @param roundDailyLeaveEarlyUnit セットする 日早退丸め単位。
	 */
	void setRoundDailyLeaveEarlyUnit(int roundDailyLeaveEarlyUnit);
	
	/**
	 * @param roundDailyLeaveEarly セットする 日早退丸め。
	 */
	void setRoundDailyLeaveEarly(int roundDailyLeaveEarly);
	
	/**
	 * @param roundDailyPrivateStartUnit セットする 日私用外出入丸め単位。
	 */
	void setRoundDailyPrivateStartUnit(int roundDailyPrivateStartUnit);
	
	/**
	 * @param roundDailyPrivateStart セットする 日私用外出入丸め。
	 */
	void setRoundDailyPrivateStart(int roundDailyPrivateStart);
	
	/**
	 * @param roundDailyPrivateTimeEndUnit セットする 日私用外出戻丸め単位。
	 */
	void setRoundDailyPrivateEndUnit(int roundDailyPrivateTimeEndUnit);
	
	/**
	 * @param roundDailyPrivateEnd セットする 日私用外出戻丸め。
	 */
	void setRoundDailyPrivateEnd(int roundDailyPrivateEnd);
	
	/**
	 * @param roundDailyPublicStartUnit セットする 日公用外出入丸め単位。
	 */
	void setRoundDailyPublicStartUnit(int roundDailyPublicStartUnit);
	
	/**
	 * @param roundDailyPublicStart セットする 日公用外出入丸め。
	 */
	void setRoundDailyPublicStart(int roundDailyPublicStart);
	
	/**
	 * @param roundDailyPublicEndUnit セットする 日公用外出戻丸め単位。
	 */
	void setRoundDailyPublicEndUnit(int roundDailyPublicEndUnit);
	
	/**
	 * @param roundDailyPublicEnd セットする 日公用外出戻丸め。
	 */
	void setRoundDailyPublicEnd(int roundDailyPublicEnd);
	
	/**
	 * @param roundDailyDecreaseTimeUnit セットする 日減額対象丸め単位。
	 */
	void setRoundDailyDecreaseTimeUnit(int roundDailyDecreaseTimeUnit);
	
	/**
	 * @param roundDailyDecreaseTime セットする 日減額対象時間丸め。
	 */
	void setRoundDailyDecreaseTime(int roundDailyDecreaseTime);
	
	/**
	 * @param roundMonthlyWorkUnit セットする 月勤務時間丸め単位。
	 */
	void setRoundMonthlyWorkUnit(int roundMonthlyWorkUnit);
	
	/**
	 * @param roundMonthlyWork セットする 月勤務時間丸め。
	 */
	void setRoundMonthlyWork(int roundMonthlyWork);
	
	/**
	 * @param roundMonthlyRestUnit セットする 月休憩時間丸め単位。
	 */
	void setRoundMonthlyRestUnit(int roundMonthlyRestUnit);
	
	/**
	 * @param roundMonthlyRest セットする 月休憩時間丸め。
	 */
	void setRoundMonthlyRest(int roundMonthlyRest);
	
	/**
	 * @param roundMonthlyLateUnit セットする 月遅刻丸め単位。
	 */
	void setRoundMonthlyLateUnit(int roundMonthlyLateUnit);
	
	/**
	 * @param roundMonthlyLate セットする 月遅刻時間丸め。
	 */
	void setRoundMonthlyLate(int roundMonthlyLate);
	
	/**
	 * @param roundMonthlyEarlyUnit セットする 月早退丸め単位。
	 */
	void setRoundMonthlyEarlyUnit(int roundMonthlyEarlyUnit);
	
	/**
	 * @param roundMonthlyEarly セットする  月早退丸め。
	 */
	void setRoundMonthlyEarly(int roundMonthlyEarly);
	
	/**
	 * @param roundMonthlyPrivateTime セットする 月私用外出丸め単位。
	 */
	void setRoundMonthlyPrivateTime(int roundMonthlyPrivateTime);
	
	/**
	 * @param roundMonthlyPrivate セットする 月私用外出時間丸め。
	 */
	void setRoundMonthlyPrivate(int roundMonthlyPrivate);
	
	/**
	 * @param roundMonthlyPublicTime セットする 月公用外出丸め単位。
	 */
	void setRoundMonthlyPublicTime(int roundMonthlyPublicTime);
	
	/**
	 * @param roundMonthlyPublic セットする 月公用外出時間丸め。
	 */
	void setRoundMonthlyPublic(int roundMonthlyPublic);
	
	/**
	 * @param roundMonthlyDecreaseTime セットする 月減額対象丸め単位。
	 */
	void setRoundMonthlyDecreaseTime(int roundMonthlyDecreaseTime);
	
	/**
	 * @param roundMonthlyDecrease セットする 月減額対象時間丸め。
	 */
	void setRoundMonthlyDecrease(int roundMonthlyDecrease);
	
	/**
	 * @param startWeek セットする 週の起算曜日。
	 */
	void setStartWeek(int startWeek);
	
	/**
	 * @param startMonth セットする 月の起算日。
	 */
	void setStartMonth(int startMonth);
	
	/**
	 * @param startYear セットする 年の起算月。
	 */
	void setStartYear(int startYear);
	
	/**
	 * @param generalWorkTime セットする 所定労働時間。
	 */
	//void setGeneralWorkTime(Time generalWorkTime);
	void setGeneralWorkTime(Date generalWorkTime);
	
	/**
	 * @param startDayTime セットする 一日の起算時。
	 */
	//void setStartDayTime(Time startDayTime);
	void setStartDayTime(Date startDayTime);
	
	/**
	 * @param lateEarlyFull セットする 遅刻早退限度時間(全日)。
	 */
	//void setLateEarlyFull(Time lateEarlyFull);
	void setLateEarlyFull(Date lateEarlyFull);
	
	/**
	 * @param lateEarlyHalf セットする 遅刻早退限度時間(半日)。
	 */
	//void setLateEarlyHalf(Time lateEarlyHalf);
	void setLateEarlyHalf(Date lateEarlyHalf);
	
	/**
	 * @param transferAheadLimitMonth セットする 振休取得期限月(休出前)。
	 */
	void setTransferAheadLimitMonth(int transferAheadLimitMonth);
	
	/**
	 * @param transferAheadLimitDate セットする 振休取得期限日(休出前)。
	 */
	void setTransferAheadLimitDate(int transferAheadLimitDate);
	
	/**
	 * @param transferLaterLimitMonth セットする 振休取得期限月(休出後)。
	 */
	void setTransferLaterLimitMonth(int transferLaterLimitMonth);
	
	/**
	 * @param transferLaterLimitDate セットする 振休取得期限日(休出後)。
	 */
	void setTransferLaterLimitDate(int transferLaterLimitDate);
	
	/**
	 * @param subHolidayLimitMonth セットする 代休取得期限月。
	 */
	void setSubHolidayLimitMonth(int subHolidayLimitMonth);
	
	/**
	 * @param subHolidayLimitDate セットする 代休取得期限日。
	 */
	void setSubHolidayLimitDate(int subHolidayLimitDate);
	
	/**
	 * @param transferExchange セットする 半休入替取得(振休)。
	 */
	void setTransferExchange(int transferExchange);
	
	/**
	 * @param subHolidayExchange セットする 半休入替取得(代休)。
	 */
	void setSubHolidayExchange(int subHolidayExchange);
	
	/**
	 * @param subHolidayAllNorm セットする 代休基準時間(全休)。
	 */
	void setSubHolidayAllNorm(Date subHolidayAllNorm);
	
	/**
	 * @param subHolidayHalfNorm セットする 代休基準時間(半休)。
	 */
	void setSubHolidayHalfNorm(Date subHolidayHalfNorm);
	
	/**
	 * @param sixtyHourFunctionFlag セットする 60時間超割増機能。
	 */
	void setSixtyHourFunctionFlag(int sixtyHourFunctionFlag);
	
	/**
	 * @param sixtyHourAlternativeFlag セットする 60時間超代替休暇。
	 */
	void setSixtyHourAlternativeFlag(int sixtyHourAlternativeFlag);
	
	/**
	 * @param monthSixtyHourSurcharge セットする 月60時間超割増。
	 */
	void setMonthSixtyHourSurcharge(int monthSixtyHourSurcharge);
	
	/**
	 * @param weekdayOver セットする 平日残業割増。
	 */
	void setWeekdayOver(int weekdayOver);
	
	/**
	 * @param weekdayAlternative セットする 代替休暇平日。
	 */
	void setWeekdayAlternative(int weekdayAlternative);
	
	/**
	 * @param alternativeCancel セットする 代替休暇放棄。
	 */
	void setAlternativeCancel(int alternativeCancel);
	
	/**
	 * @param alternativeSpecific セットする 代替休暇所定休日。
	 */
	void setAlternativeSpecific(int alternativeSpecific);
	
	/**
	 * @param alternativeLegal セットする 代替休暇法定休日。
	 */
	void setAlternativeLegal(int alternativeLegal);
	
	/**
	 * @param specificHoliday セットする 所定休日割増率。
	 */
	void setSpecificHoliday(int specificHoliday);
	
	/**
	 * @param legalHoliday セットする 法定休日割増率。
	 */
	void setLegalHoliday(int legalHoliday);
	
	/**
	 * @param prospectsMonths セットする 見込月。
	 */
	void setProspectsMonths(String prospectsMonths);
	
	/**
	 * @param cutoffAbbr セットする 締日略称。
	 */
	void setCutoffAbbr(String cutoffAbbr);
	
	/**
	 * @return 日残業時間丸め単位。
	 */
	int getRoundDailyOvertimeUnit();
	
	/**
	 * @return 日残業時間丸め。
	 */
	int getRoundDailyOvertime();
	
	/**
	 * @return 月残業時間丸め単位。
	 */
	int getRoundMonthlyOvertimeUnit();
	
	/**
	 * @return 月残業時間丸め。
	 */
	int getRoundMonthlyOvertime();
	
	/**
	 * @param roundDailyOvertimeUnit セットする 日残業時間丸め単位
	 */
	void setRoundDailyOvertimeUnit(int roundDailyOvertimeUnit);
	
	/**
	 * @param roundDailyOvertime セットする 日残業時間丸め
	 */
	void setRoundDailyOvertime(int roundDailyOvertime);
	
	/**
	 * @param roundMonthlyOvertimeUnit セットする 月残業時間丸め単位
	 */
	void setRoundMonthlyOvertimeUnit(int roundMonthlyOvertimeUnit);
	
	/**
	 * @param roundMonthlyOvertime セットする 月残業時間丸め
	 */
	void setRoundMonthlyOvertime(int roundMonthlyOvertime);
	
	/**
	 * @return 日無給時短時間丸め単位
	 */
	int getRoundDailyShortUnpaidUnit();
	
	/**
	 * @param roundDailyShortUnpaidUnit セットする 日無給時短時間丸め単位
	 */
	void setRoundDailyShortUnpaidUnit(int roundDailyShortUnpaidUnit);
	
	/**
	 * @return 日無給時短時間丸め
	 */
	int getRoundDailyShortUnpaid();
	
	/**
	 * @param roundDailyShortUnpaid セットする 日無給時短時間丸め
	 */
	void setRoundDailyShortUnpaid(int roundDailyShortUnpaid);
	
	/**
	 * @return 月無給時短時間丸め単位
	 */
	int getRoundMonthlyShortUnpaidUnit();
	
	/**
	 * @param roundMonthlyShortUnpaidUnit セットする 月無給時短時間丸め単位
	 */
	void setRoundMonthlyShortUnpaidUnit(int roundMonthlyShortUnpaidUnit);
	
	/**
	 * @return 月無給時短時間丸め
	 */
	int getRoundMonthlyShortUnpaid();
	
	/**
	 * @param roundMonthlyShortUnpaid セットする 月無給時短時間丸め
	 */
	void setRoundMonthlyShortUnpaid(int roundMonthlyShortUnpaid);
	
	/**
	 * @return portalTimeButtons
	 */
	int getPortalTimeButtons();
	
	/**
	 * @param portalTimeButtons セットする portalTimeButtons
	 */
	void setPortalTimeButtons(int portalTimeButtons);
	
	/**
	 * @return portalRestButtons
	 */
	int getPortalRestButtons();
	
	/**
	 * @param portalRestButtons セットする portalRestButtons
	 */
	void setPortalRestButtons(int portalRestButtons);
	
	/**
	 * @return useScheduledTime
	 */
	int getUseScheduledTime();
	
	/**
	 * @param useScheduledTime セットする useScheduledTime
	 */
	void setUseScheduledTime(int useScheduledTime);
	
}
