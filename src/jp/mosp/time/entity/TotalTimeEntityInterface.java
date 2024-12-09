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
package jp.mosp.time.entity;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.ApplicationDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;

/**
 * 勤怠集計エンティティインターフェース
 */
public interface TotalTimeEntityInterface {
	
	/**
	 * 勤怠集計を行う。<br>
	 * <br>
	 * 設定された情報を基に勤怠集計を行う。<br>
	 * 集計値はフィールドに設定される。<br>
	 * <br>
	 * @throws MospException 日付の成形に失敗した場合
	 */
	void total() throws MospException;
	
	/**
	 * 残業時間(45時間超と60時間超)を計算する。<br>
	 * {@link TotalTimeEntityInterface#total()}でも実行されるが、
	 * アドオン等で残業時間を調整した場合等に再計算することを想定する。<br>
	 */
	void calcExtraOverTime();
	
	/**
	 * 対象日の予定勤務形態コード(振出・休出、振替休日含む)を取得する。<br>
	 * <br>
	 * 予定勤務形態コード群、振出・休出勤務形態コード群、振替休日リストから、
	 * 勤務形態コードを取得する。<br>
	 * <br>
	 * ここでは、勤務形態変更申請は、考慮しない。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 予定勤務形態コード(振出・休出、振替休日含む)
	 */
	String getScheduledWorkTypeCode(Date targetDate);
	
	/**
	 * @return 計算対象年
	 */
	int getCalculationYear();
	
	/**
	 * @param calculationYear 計算対象年
	 */
	void setCalculationYear(int calculationYear);
	
	/**
	 * @return 計算対象月
	 */
	int getCalculationMonth();
	
	/**
	 * @param calculationMonth 計算対象月
	 */
	void setCalculationMonth(int calculationMonth);
	
	/**
	 * @return 休暇種別情報群
	 * 
	 */
	Set<HolidayDtoInterface> getHolidaySet();
	
	/**
	 * @param holidaySet 休暇種別情報群
	 */
	void setHolidaySet(Set<HolidayDtoInterface> holidaySet);
	
	/**
	 * @return 締日コード
	 */
	String getCutoffCode();
	
	/**
	 * @param cutoffCode 締日コード
	 */
	void setCutoffCode(String cutoffCode);
	
	/**
	 * MosP処理情報を設定する。<br>
	 * @param mospParams MosP処理情報
	 */
	void setMospParams(MospParams mospParams);
	
	/**------------- 個別情報 -------------**/
	
	/**
	 * @return personalId
	 */
	String getPersonalId();
	
	/**
	 * @param personalId セットする personalId
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @return 締期間初日
	 */
	Date getCutoffFirstDate();
	
	/**
	 * @param cutoffFirstDate 締期間初日
	 */
	void setCutoffFirstDate(Date cutoffFirstDate);
	
	/**
	 * @return 締期間最終日
	 */
	Date getCutoffLastDate();
	
	/**
	 * @param cutoffLastDate 締期間最終日
	 */
	void setCutoffLastDate(Date cutoffLastDate);
	
	/**
	 * @return 締期間(個人)対象日リスト
	 */
	List<Date> getTargetDateList();
	
	/**
	 * @param targetDateList 締期間(個人)対象日リスト
	 */
	void setTargetDateList(List<Date> targetDateList);
	
	/**
	 * @return 休職情報リスト
	 */
	List<SuspensionDtoInterface> getSuspensionList();
	
	/**
	 * @param suspensionList 休職情報リスト
	 */
	void setSuspensionList(List<SuspensionDtoInterface> suspensionList);
	
	/**
	 * @return 設定適用情報群
	 */
	Map<Date, ApplicationDtoInterface> getApplicationMap();
	
	/**
	 * @param applicationMap 設定適用情報群
	 */
	void setApplicationMap(Map<Date, ApplicationDtoInterface> applicationMap);
	
	/**
	 * @return 勤怠設定情報群
	 */
	Map<Date, TimeSettingDtoInterface> getTimeSettingMap();
	
	/**
	 * @param timeSettingMap 勤怠設定情報群
	 */
	void setTimeSettingMap(Map<Date, TimeSettingDtoInterface> timeSettingMap);
	
	/**
	 * @return カレンダ日情報群
	 */
	Map<Date, String> getScheduleMap();
	
	/**
	 * @param scheduleMap カレンダ日情報群
	 */
	void setScheduleMap(Map<Date, String> scheduleMap);
	
	/**
	 * @return 振出・休出勤務形態コード群
	 */
	Map<Date, String> getSubstitutedMap();
	
	/**
	 * @param substitutedMap 振出・休出勤務形態コード群
	 */
	void setSubstitutedMap(Map<Date, String> substitutedMap);
	
	/**
	 * @param attendanceList 勤怠申請リスト
	 */
	void setAttendanceList(List<AttendanceDtoInterface> attendanceList);
	
	/**
	 * @return 勤怠申請リスト
	 */
	List<AttendanceDtoInterface> getAttendanceList();
	
	/**
	 * @return 休日出勤申請リスト
	 */
	List<WorkOnHolidayRequestDtoInterface> getWorkOnHolidayRequestList();
	
	/**
	 * @param workOnHolidayRequestList 休日出勤申請リスト
	 */
	void setWorkOnHolidayRequestList(List<WorkOnHolidayRequestDtoInterface> workOnHolidayRequestList);
	
	/***
	 * @return 休暇申請リスト
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestList();
	
	/**
	 * @param holidayRequestList 休暇申請リスト
	 */
	void setHolidayRequestList(List<HolidayRequestDtoInterface> holidayRequestList);
	
	/**
	 * @return 代休申請リスト
	 */
	List<SubHolidayRequestDtoInterface> getSubHolidayRequestList();
	
	/**
	 * @param subHolidayRequestList 代休申請リスト
	 */
	void setSubHolidayRequestList(List<SubHolidayRequestDtoInterface> subHolidayRequestList);
	
	/**
	 * @return 残業申請リスト
	 */
	List<OvertimeRequestDtoInterface> getOvertimeRequestList();
	
	/**
	 * @param overtimeRequestList 残業申請リスト
	 */
	void setOvertimeRequestList(List<OvertimeRequestDtoInterface> overtimeRequestList);
	
	/**
	 * @return 勤務形態変更申請リスト
	 */
	List<WorkTypeChangeRequestDtoInterface> getWorkTypeChangeRequestList();
	
	/**
	 * @param workTypeChangeRequestList 勤務形態変更申請リスト
	 */
	void setWorkTypeChangeRequestList(List<WorkTypeChangeRequestDtoInterface> workTypeChangeRequestList);
	
	/**
	 * @return 時差出勤申請リスト
	 */
	List<DifferenceRequestDtoInterface> getDifferenceRequestList();
	
	/**
	 * @param differenceRequestList 時差出勤申請リスト
	 */
	void setDifferenceRequestList(List<DifferenceRequestDtoInterface> differenceRequestList);
	
	/**
	 * @return 振替休日情報リスト
	 */
	List<SubstituteDtoInterface> getSubstitubeList();
	
	/**
	 * @param substitubeList 振替休日情報リスト
	 */
	void setSubstitubeList(List<SubstituteDtoInterface> substitubeList);
	
	/**
	 * @return 代休情報リスト
	 */
	List<SubHolidayDtoInterface> getSubHolidayList();
	
	/**
	 * @param subHolidayList 代休情報リスト
	 */
	void setSubHolidayList(List<SubHolidayDtoInterface> subHolidayList);
	
	/**
	 * @return 代休休日出勤勤怠設定情報リスト
	 */
	Map<Date, TimeSettingDtoInterface> getSubHolidayTimeSettingMap();
	
	/**
	 * @param subHolidayTimeSettingMap 代休情報リスト
	 */
	void setSubHolidayTimeSettingMap(Map<Date, TimeSettingDtoInterface> subHolidayTimeSettingMap);
	
	/**
	 * @return ワークフロー情報群
	 */
	Map<Long, WorkflowDtoInterface> getWorkflowMap();
	
	/**
	 * @param workflowMap ワークフロー情報群
	 */
	void setWorkflowMap(Map<Long, WorkflowDtoInterface> workflowMap);
	
	/**
	 * @return 勤怠トランザクション情報リスト
	 */
	Set<AttendanceTransactionDtoInterface> getAttendanceTransactionSet();
	
	/**
	 * @param attendanceTransactionSet 勤怠トランザクション情報リスト
	 */
	void setAttendanceTransactionSet(Set<AttendanceTransactionDtoInterface> attendanceTransactionSet);
	
	/**
	 * ワークフロー情報取得
	 * @param workflow ワークフロー番号
	 * @return ワークフローDTO
	 */
	WorkflowDtoInterface getWorkflowDto(long workflow);
	
	/**
	 * @return 勤務形態エンティティ群
	 */
	Map<String, List<WorkTypeEntityInterface>> getWorkTypeEntityMap();
	
	/**
	 * @param workTypeEntityMap 勤務形態エンティティ群
	 */
	void setWorkTypeEntityMap(Map<String, List<WorkTypeEntityInterface>> workTypeEntityMap);
	
	/**
	 * @return 勤怠トランザクション登録判定情報群
	 */
	Map<Date, String> getAttendanceTransactionMap();
	
	/**
	 * 申請エンティティを取得する。<br>
	 * <br>
	 * フィールドに設定されている情報から対象日のものを抜き出し、
	 * 申請エンティティを作成する。<br>
	 * そのためフィールドに設定されていない情報は、作成された申請エンティティには含まれない。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 申請エンティティ
	 */
	RequestEntityInterface getRequestEntity(Date targetDate);
	
	/**------------- 変数(勤怠集計後) -------------**/
	
	/**
	 * @return 勤務時間
	 */
	int getWorkTime();
	
	/**
	 * @return 契約勤務時間
	 */
	int getContractWorkTime();
	
	/**
	 * @return 出勤日数
	 */
	double getTimesWorkDate();
	
	/**
	 * @return 出勤回数
	 */
	int getTimesWork();
	
	/**
	 * @return 法定休日出勤日数
	 */
	double getLegalWorkOnHoliday();
	
	/**
	 * @return 所定休日出勤日数
	 */
	double getSpecificWorkOnHoliday();
	
	/**
	 * @return 出勤実績日数
	 */
	int getTimesAchievement();
	
	/**
	 * @return 出勤対象日数
	 */
	int getTimesTotalWorkDate();
	
	/**
	 * @return 直行回数
	 */
	int getDirectStart();
	
	/**
	 * @return 直帰回数
	 */
	int getDirectEnd();
	
	/**
	 * @return 休憩時間
	 */
	int getRestTime();
	
	/**
	 * @return 深夜休憩時間
	 */
	int getRestLateNight();
	
	/**
	 * @return 所定休出休憩時間
	 */
	int getRestWorkOnSpecificHoliday();
	
	/**
	 * @return 法定休出休憩時間
	 */
	int getRestWorkOnHoliday();
	
	/**
	 * @return 公用外出時間
	 */
	int getPublicTime();
	
	/**
	 * @return 私用外出時間
	 */
	int getPrivateTime();
	
	/**
	 * @return 分単位休暇A時間
	 */
	int getMinutelyHolidayATime();
	
	/**
	 * @return 分単位休暇B時間
	 */
	int getMinutelyHolidayBTime();
	
	/**
	 * @return 残業時間
	 */
	int getOvertime();
	
	/**
	 * @return 法定内残業時間
	 */
	int getOvertimeIn();
	
	/**
	 * @return 法定外残業時間
	 */
	int getOvertimeOut();
	
	/**
	 * @return 深夜時間
	 */
	int getLateNight();
	
	/**
	 * @return 深夜所定労働時間内時間
	 */
	int getNightWorkWithinPrescribedWork();
	
	/**
	 * @return 深夜時間外時間
	 */
	int getNightOvertimeWork();
	
	/**
	 * @return 深夜休日労働時間
	 */
	int getNightWorkOnHoliday();
	
	/**
	 * @return 所定休出時間
	 */
	int getWorkOnSpecificHoliday();
	
	/**
	 * @return 法定休出時間
	 */
	int getWorkOnHoliday();
	
	/**
	 * @return 減額対象時間
	 */
	int getDecreaseTime();
	
	/**
	 * @return 45時間超残業時間
	 */
	int getFortyFiveHourOvertime();
	
	/**
	 * @return 残業回数
	 */
	int getTimesOvertime();
	
	/**
	 * @return 休日出勤回数
	 */
	int getTimesWorkingHoliday();
	
	/**
	 * @return 合計遅刻日数
	 */
	int getLateDays();
	
	/**
	 * @return 遅刻30分以上日数
	 */
	int getLateThirtyMinutesOrMore();
	
	/**
	 * @return 遅刻30分未満日数
	 */
	int getLateLessThanThirtyMinutes();
	
	/**
	 * @return 合計遅刻時間
	 */
	int getLateTime();
	
	/**
	 * @return 遅刻30分以上時間
	 */
	int getLateThirtyMinutesOrMoreTime();
	
	/**
	 * @return 遅刻30分未満時間
	 */
	int getLateLessThanThirtyMinutesTime();
	
	/**
	 * @return 合計遅刻回数
	 */
	int getTimesLate();
	
	/**
	 * @return 合計早退日数
	 */
	int getLeaveEarlyDays();
	
	/**
	 * @return 早退30分以上日数
	 */
	int getLeaveEarlyThirtyMinutesOrMore();
	
	/**
	 * @return 早退30分未満日数
	 */
	int getLeaveEarlyLessThanThirtyMinutes();
	
	/**
	 * @return  合計早退時間
	 */
	int getLeaveEarlyTime();
	
	/**
	 * @return 早退30分以上時間
	 */
	int getLeaveEarlyThirtyMinutesOrMoreTime();
	
	/**
	 * @return 早退30分未満時間
	 */
	int getLeaveEarlyLessThanThirtyMinutesTime();
	
	/**
	 * @return 合計早退回数
	 */
	int getTimesLeaveEarly();
	
	/**
	 * @return 休日日数
	 */
	int getTimesHoliday();
	
	/**
	 * @return 法定休日日数
	 */
	int getTimesLegalHoliday();
	
	/**
	 * @return 所定休日日数
	 */
	int getTimesSpecificHoliday();
	
	/**
	 * @return 有給休暇日数
	 */
	double getTimesPaidHoliday();
	
	/**
	 * @return 有給休暇時間
	 */
	int getPaidHolidayHour();
	
	/**
	 * @return ストック休暇日数
	 */
	double getTimesStockHoliday();
	
	/**
	 * @return 代休日数
	 */
	double getTimesCompensation();
	
	/**
	 * @return 法定代休日数
	 */
	double getTimesLegalCompensation();
	
	/**
	 * @return 所定代休日数
	 */
	double getTimesSpecificCompensation();
	
	/**
	 * @return 深夜代休日数
	 */
	double getTimesLateCompensation();
	
	/**
	 * @return 振替休日日数
	 */
	double getTimesHolidaySubstitute();
	
	/**
	 * @return 法定振替休日日数
	 */
	double getTimesLegalHolidaySubstitute();
	
	/**
	 * @return 所定振替休日日数
	 */
	double getTimesSpecificHolidaySubstitute();
	
	/**
	 * @return 特別休暇合計日数
	 */
	double getTotalSpecialHoliday();
	
	/**
	 * @return 特別休暇時間数
	 */
	int getSpecialHolidayHour();
	
	/**
	 * @return その他休暇合計日数
	 */
	double getTotalOtherHoliday();
	
	/**
	 * @return その他休暇時間数
	 */
	int getOtherHolidayHour();
	
	/**
	 * @return 欠勤合計日数
	 */
	double getTotalAbsence();
	
	/**
	 * @return 欠勤時間数
	 */
	int getAbsenceHour();
	
	/**
	 * @return 手当合計
	 */
	int getTotalAllowance();
	
	/**
	 * @return 60時間超残業時間
	 */
	int getSixtyHourOvertime();
	
	/**
	 * @return 平日時間内時間(平日法定労働時間内残業時間)
	 */
	int getWorkdayOvertimeIn();
	
	/**
	 * @return 所定休日時間内時間(所定休日法定労働時間内残業時間)
	 */
	int getPrescribedHolidayOvertimeIn();
	
	/**
	 * @return 平日時間外時間(平日法定労働時間外残業時間)
	 */
	int getWorkdayOvertimeOut();
	
	/**
	 * @return 所定休日時間外時間(所定休日法定労働時間外残業時間)
	 */
	int getPrescribedOvertimeOut();
	
	/**
	 * @return 代替休暇日数
	 */
	double getTimesAlternative();
	
	/**
	 * @return 法定代休発生日数
	 */
	double getLegalCompensationOccurred();
	
	/**
	 * @return 所定代休発生日数
	 */
	double getSpecificCompensationOccurred();
	
	/**
	 * @return 深夜代休発生日数
	 */
	double getLateCompensationOccurred();
	
	/**
	 * @return 法定代休未使用日数
	 */
	double getLegalCompensationUnused();
	
	/**
	 * @return 所定代休未使用日数
	 */
	double getSpecificCompensationUnused();
	
	/**
	 * @return 深夜代休未使用日数
	 */
	double getLateCompensationUnused();
	
	/**
	 * @return 所定労働時間内法定休日労働時間
	 */
	int getStatutoryHolidayWorkTimeIn();
	
	/**
	 * @return 所定労働時間外法定休日労働時間
	 */
	int getStatutoryHolidayWorkTimeOut();
	
	/**
	 * @return 所定労働時間内所定休日労働時間
	 */
	int getPrescribedHolidayWorkTimeIn();
	
	/**
	 * @return 所定労働時間外所定休日労働時間
	 */
	int getPrescribedHolidayWorkTimeOut();
	
	/**
	 * @return 無給時短時間
	 */
	int getShortUnpaid();
	
	/**
	 * @return 週40時間超勤務時間
	 */
	int getWeeklyOverFortyHourWorkTime();
	
	/**
	 * @return 法定内残業時間(週40時間超除く)
	 */
	int getOvertimeInNoWeeklyForty();
	
	/**
	 * @return 法定外残業時間(週40時間超除く)
	 */
	int getOvertimeOutNoWeeklyForty();
	
	/**
	 * @return 平日時間内時間(週40時間超除く)
	 */
	int getWeekDayOvertimeInNoWeeklyForty();
	
	/**
	 * @return 平日時間外時間(週40時間超除く)
	 */
	int getWeekDayOvertimeOutNoWeeklyForty();
	
	/**
	 * @return 汎用項目1(数値)
	 */
	int getGeneralIntItem1();
	
	/**
	 * @return 汎用項目2(数値)
	 */
	int getGeneralIntItem2();
	
	/**
	 * @return 汎用項目3(数値)
	 */
	int getGeneralIntItem3();
	
	/**
	 * @return 汎用項目4(数値)
	 */
	int getGeneralIntItem4();
	
	/**
	 * @return 汎用項目5(数値)
	 */
	int getGeneralIntItem5();
	
	/**
	 * @return 汎用項目1(浮動小数点数)
	 */
	double getGeneralDoubleItem1();
	
	/**
	 * @return 汎用項目2(浮動小数点数)
	 */
	double getGeneralDoubleItem2();
	
	/**
	 * @return 汎用項目3(浮動小数点数)
	 */
	double getGeneralDoubleItem3();
	
	/**
	 * @return 汎用項目4(浮動小数点数)
	 */
	double getGeneralDoubleItem4();
	
	/**
	 * @return 汎用項目5(浮動小数点数)
	 */
	double getGeneralDoubleItem5();
	
	/**
	 * @return 特別休暇日数群(キー：休暇コード)
	 */
	Map<String, Float> getSpecialHolidayDays();
	
	/**
	 * @return 特別休暇時間数群(キー：休暇コード)
	 */
	Map<String, Integer> getSpecialHolidayHours();
	
	/**
	 * @return その他休暇日数群(キー：休暇コード)
	 */
	Map<String, Float> getOtherHolidayDays();
	
	/**
	 * @return その他休暇時間数群(キー：休暇コード)
	 */
	Map<String, Integer> getOtherHolidayHours();
	
	/**
	 * @return 欠勤日数群(キー：休暇コード)
	 */
	Map<String, Float> getAbsenceDays();
	
	/**
	 * @return 欠勤時間数群(キー：休暇コード)
	 */
	Map<String, Integer> getAbsenceHours();
	
	/**
	 * 汎用項目群の取得<br>
	 * 汎用コードは、add-onまたはoptionを示すprefixをつけること。<br>
	 * 例：	フレックスタイム用不足契約勤務時間ならば、"ftDeficientContractWorkTime"
	 * <br>
	 * @return 汎用項目群(キー：汎用コード)
	 */
	Map<String, Object> getGeneralValues();
	
	/**
	 * 勤務時間を設定します。
	 * @param workTime 勤務時間
	 */
	void setWorkTime(int workTime);
	
	/**
	 * 契約勤務時間を設定します。
	 * @param contractWorkTime 契約勤務時間
	 */
	void setContractWorkTime(int contractWorkTime);
	
	/**
	 * 出勤日数を設定します。
	 * @param timesWorkDate 出勤日数
	 */
	void setTimesWorkDate(double timesWorkDate);
	
	/**
	 * 出勤回数を設定します。
	 * @param timesWork 出勤回数
	 */
	void setTimesWork(int timesWork);
	
	/**
	 * 法定休日出勤日数を設定します。
	 * @param legalWorkOnHoliday 法定休日出勤日数
	 */
	void setLegalWorkOnHoliday(double legalWorkOnHoliday);
	
	/**
	 * 所定休日出勤日数を設定します。
	 * @param specificWorkOnHoliday 所定休日出勤日数
	 */
	void setSpecificWorkOnHoliday(double specificWorkOnHoliday);
	
	/**
	 * 出勤実績日数を設定します。
	 * @param timesAchievement 出勤実績日数
	 */
	void setTimesAchievement(int timesAchievement);
	
	/**
	 * 出勤対象日数を設定します。
	 * @param timesTotalWorkDate 出勤対象日数
	 */
	void setTimesTotalWorkDate(int timesTotalWorkDate);
	
	/**
	 * 直行回数を設定します。
	 * @param directStart 直行回数
	 */
	void setDirectStart(int directStart);
	
	/**
	 * 直帰回数を設定します。
	 * @param directEnd 直帰回数
	 */
	void setDirectEnd(int directEnd);
	
	/**
	 * 休憩時間を設定します。
	 * @param restTime 休憩時間
	 */
	void setRestTime(int restTime);
	
	/**
	 * 深夜休憩時間を設定します。
	 * @param restLateNight 深夜休憩時間
	 */
	void setRestLateNight(int restLateNight);
	
	/**
	 * 所定休出休憩時間を設定します。
	 * @param restWorkOnSpecificHoliday 所定休出休憩時間
	 */
	void setRestWorkOnSpecificHoliday(int restWorkOnSpecificHoliday);
	
	/**
	 * 法定休出休憩時間を設定します。
	 * @param restWorkOnHoliday 法定休出休憩時間
	 */
	void setRestWorkOnHoliday(int restWorkOnHoliday);
	
	/**
	 * 公用外出時間を設定します。
	 * @param publicTime 公用外出時間
	 */
	void setPublicTime(int publicTime);
	
	/**
	 * 私用外出時間を設定します。
	 * @param privateTime 私用外出時間
	 */
	void setPrivateTime(int privateTime);
	
	/**
	 * 分単位休暇A時間を設定します。
	 * @param minutelyHolidayATime 分単位休暇A時間
	 */
	void setMinutelyHolidayATime(int minutelyHolidayATime);
	
	/**
	 * 分単位休暇B時間を設定します。
	 * @param minutelyHolidayBTime 分単位休暇B時間
	 */
	void setMinutelyHolidayBTime(int minutelyHolidayBTime);
	
	/**
	 * 残業時間を設定します。
	 * @param overtime 残業時間
	 */
	void setOvertime(int overtime);
	
	/**
	 * 法定内残業時間を設定します。
	 * @param overtimeIn 法定内残業時間
	 */
	void setOvertimeIn(int overtimeIn);
	
	/**
	 * 法定外残業時間を設定します。
	 * @param overtimeOut 法定外残業時間
	 */
	void setOvertimeOut(int overtimeOut);
	
	/**
	 * 深夜時間を設定します。
	 * @param lateNight 深夜時間
	 */
	void setLateNight(int lateNight);
	
	/**
	 * 深夜所定労働時間内時間を設定します。
	 * @param nightWorkWithinPrescribedWork 深夜所定労働時間内時間
	 */
	void setNightWorkWithinPrescribedWork(int nightWorkWithinPrescribedWork);
	
	/**
	 * 深夜時間外時間を設定します。
	 * @param nightOvertimeWork 深夜時間外時間
	 */
	void setNightOvertimeWork(int nightOvertimeWork);
	
	/**
	 * 深夜休日労働時間を設定します。
	 * @param nightWorkOnHoliday 深夜休日労働時間
	 */
	void setNightWorkOnHoliday(int nightWorkOnHoliday);
	
	/**
	 * 所定休出時間を設定します。
	 * @param workOnSpecificHoliday 所定休出時間
	 */
	void setWorkOnSpecificHoliday(int workOnSpecificHoliday);
	
	/**
	 * 法定休出時間を設定します。
	 * @param workOnHoliday 法定休出時間
	 */
	void setWorkOnHoliday(int workOnHoliday);
	
	/**
	 * 減額対象時間を設定します。
	 * @param decreaseTime 減額対象時間
	 */
	void setDecreaseTime(int decreaseTime);
	
	/**
	 * 45時間超残業時間を設定します。
	 * @param fortyFiveHourOvertime 45時間超残業時間
	 */
	void setFortyFiveHourOvertime(int fortyFiveHourOvertime);
	
	/**
	 * 残業回数を設定します。
	 * @param timesOvertime 残業回数
	 */
	void setTimesOvertime(int timesOvertime);
	
	/**
	 * 休日出勤回数を設定します。
	 * @param timesWorkingHoliday 休日出勤回数
	 */
	void setTimesWorkingHoliday(int timesWorkingHoliday);
	
	/**
	 * 合計遅刻日数を設定します。
	 * @param lateDays 合計遅刻日数
	 */
	void setLateDays(int lateDays);
	
	/**
	 * 遅刻30分以上日数を設定します。
	 * @param lateThirtyMinutesOrMore 遅刻30分以上日数
	 */
	void setLateThirtyMinutesOrMore(int lateThirtyMinutesOrMore);
	
	/**
	 * 遅刻30分未満日数を設定します。
	 * @param lateLessThanThirtyMinutes 遅刻30分未満日数
	 */
	void setLateLessThanThirtyMinutes(int lateLessThanThirtyMinutes);
	
	/**
	 * 合計遅刻時間を設定します。
	 * @param lateTime 合計遅刻時間
	 */
	void setLateTime(int lateTime);
	
	/**
	 * 遅刻30分以上時間を設定します。
	 * @param lateThirtyMinutesOrMoreTime 遅刻30分以上時間
	 */
	void setLateThirtyMinutesOrMoreTime(int lateThirtyMinutesOrMoreTime);
	
	/**
	 * 遅刻30分未満時間を設定します。
	 * @param lateLessThanThirtyMinutesTime 遅刻30分未満時間
	 */
	void setLateLessThanThirtyMinutesTime(int lateLessThanThirtyMinutesTime);
	
	/**
	 * 合計遅刻回数を設定します。
	 * @param timesLate 合計遅刻回数
	 */
	void setTimesLate(int timesLate);
	
	/**
	 * 合計早退日数を設定します。
	 * @param leaveEarlyDays 合計早退日数
	 */
	void setLeaveEarlyDays(int leaveEarlyDays);
	
	/**
	 * 早退30分以上日数を設定します。
	 * @param leaveEarlyThirtyMinutesOrMore 早退30分以上日数
	 */
	void setLeaveEarlyThirtyMinutesOrMore(int leaveEarlyThirtyMinutesOrMore);
	
	/**
	 * 早退30分未満日数を設定します。
	 * @param leaveEarlyLessThanThirtyMinutes 早退30分未満日数
	 */
	void setLeaveEarlyLessThanThirtyMinutes(int leaveEarlyLessThanThirtyMinutes);
	
	/**
	 * 合計早退時間を設定します。
	 * @param leaveEarlyTime 合計早退時間
	 */
	void setLeaveEarlyTime(int leaveEarlyTime);
	
	/**
	 * 早退30分以上時間を設定します。
	 * @param leaveEarlyThirtyMinutesOrMoreTime 早退30分以上時間
	 */
	void setLeaveEarlyThirtyMinutesOrMoreTime(int leaveEarlyThirtyMinutesOrMoreTime);
	
	/**
	 * 早退30分未満時間を設定します。
	 * @param leaveEarlyLessThanThirtyMinutesTime 早退30分未満時間
	 */
	void setLeaveEarlyLessThanThirtyMinutesTime(int leaveEarlyLessThanThirtyMinutesTime);
	
	/**
	 * 合計早退回数を設定します。
	 * @param timesLeaveEarly 合計早退回数
	 */
	void setTimesLeaveEarly(int timesLeaveEarly);
	
	/**
	 * 休日日数を設定します。
	 * @param timesHoliday 休日日数
	 */
	void setTimesHoliday(int timesHoliday);
	
	/**
	 * 法定休日日数を設定します。
	 * @param timesLegalHoliday 法定休日日数
	 */
	void setTimesLegalHoliday(int timesLegalHoliday);
	
	/**
	 * 所定休日日数を設定します。
	 * @param timesSpecificHoliday 所定休日日数
	 */
	void setTimesSpecificHoliday(int timesSpecificHoliday);
	
	/**
	 * 有給休暇日数を設定します。
	 * @param timesPaidHoliday 有給休暇日数
	 */
	void setTimesPaidHoliday(double timesPaidHoliday);
	
	/**
	 * 有給休暇時間を設定します。
	 * @param paidHolidayHour 有給休暇時間
	 */
	void setPaidHolidayHour(int paidHolidayHour);
	
	/**
	 * ストック休暇日数を設定します。
	 * @param timesStockHoliday ストック休暇日数
	 */
	void setTimesStockHoliday(double timesStockHoliday);
	
	/**
	 * 代休日数を設定します。
	 * @param timesCompensation 代休日数
	 */
	void setTimesCompensation(double timesCompensation);
	
	/**
	 * 法定代休日数を設定します。
	 * @param timesLegalCompensation 法定代休日数
	 */
	void setTimesLegalCompensation(double timesLegalCompensation);
	
	/**
	 * 所定代休日数を設定します。
	 * @param timesSpecificCompensation 所定代休日数
	 */
	void setTimesSpecificCompensation(double timesSpecificCompensation);
	
	/**
	 * 深夜代休日数を設定します。
	 * @param timesLateCompensation 深夜代休日数
	 */
	void setTimesLateCompensation(double timesLateCompensation);
	
	/**
	 * 振替休日日数を設定します。
	 * @param timesHolidaySubstitute 振替休日日数
	 */
	void setTimesHolidaySubstitute(double timesHolidaySubstitute);
	
	/**
	 * 法定振替休日日数を設定します。
	 * @param timesLegalHolidaySubstitute 法定振替休日日数
	 */
	void setTimesLegalHolidaySubstitute(double timesLegalHolidaySubstitute);
	
	/**
	 * 所定振替休日日数を設定します。
	 * @param timesSpecificHolidaySubstitute 所定振替休日日数
	 */
	void setTimesSpecificHolidaySubstitute(double timesSpecificHolidaySubstitute);
	
	/**
	 * 特別休暇合計日数。<br>を設定します。
	 * @param totalSpecialHoliday 特別休暇合計日数。<br>
	 */
	void setTotalSpecialHoliday(double totalSpecialHoliday);
	
	/**
	 * 特別休暇時間。<br>を設定します。
	 * @param specialHolidayHour 特別休暇時間。<br>
	 */
	void setSpecialHolidayHour(int specialHolidayHour);
	
	/**
	 * その他休暇合計日数。<br>を設定します。
	 * @param totalOtherHoliday その他休暇合計日数。<br>
	 */
	void setTotalOtherHoliday(double totalOtherHoliday);
	
	/**
	 * その他休暇時間。<br>を設定します。
	 * @param otherHolidayHour その他休暇時間。<br>
	 */
	void setOtherHolidayHour(int otherHolidayHour);
	
	/**
	 * 欠勤合計日数。<br>を設定します。
	 * @param totalAbsence 欠勤合計日数。<br>
	 */
	void setTotalAbsence(double totalAbsence);
	
	/**
	 * 欠勤時間。<br>を設定します。
	 * @param absenceHour 欠勤時間。<br>
	 */
	void setAbsenceHour(int absenceHour);
	
	/**
	 * 手当合計を設定します。
	 * @param totalAllowance 手当合計
	 */
	void setTotalAllowance(int totalAllowance);
	
	/**
	 * 60時間超残業時間を設定します。
	 * @param sixtyHourOvertime 60時間超残業時間
	 */
	void setSixtyHourOvertime(int sixtyHourOvertime);
	
	/**
	 * 平日時間内時間(平日法定労働時間内残業時間)を設定します。
	 * @param workdayOvertimeIn 平日時間内時間(平日法定労働時間内残業時間)
	 */
	void setWorkdayOvertimeIn(int workdayOvertimeIn);
	
	/**
	 * 所定休日時間内時間(所定休日法定労働時間内残業時間)を設定します。
	 * @param prescribedHolidayOvertimeIn 所定休日時間内時間(所定休日法定労働時間内残業時間)
	 */
	void setPrescribedHolidayOvertimeIn(int prescribedHolidayOvertimeIn);
	
	/**
	 * 平日時間外時間(平日法定労働時間外残業時間)を設定します。
	 * @param workdayOvertimeOut 平日時間外時間(平日法定労働時間外残業時間)
	 */
	void setWorkdayOvertimeOut(int workdayOvertimeOut);
	
	/**
	 * 所定休日時間外時間(所定休日法定労働時間外残業時間)を設定します。
	 * @param prescribedOvertimeOut 所定休日時間外時間(所定休日法定労働時間外残業時間)
	 */
	void setPrescribedOvertimeOut(int prescribedOvertimeOut);
	
	/**
	 * 代替休暇日数を設定します。
	 * @param timesAlternative 代替休暇日数
	 */
	void setTimesAlternative(double timesAlternative);
	
	/**
	 * 法定代休発生日数を設定します。
	 * @param legalCompensationOccurred 法定代休発生日数
	 */
	void setLegalCompensationOccurred(double legalCompensationOccurred);
	
	/**
	 * 所定代休発生日数を設定します。
	 * @param specificCompensationOccurred 所定代休発生日数
	 */
	void setSpecificCompensationOccurred(double specificCompensationOccurred);
	
	/**
	 * 深夜代休発生日数を設定します。
	 * @param lateCompensationOccurred 深夜代休発生日数
	 */
	void setLateCompensationOccurred(double lateCompensationOccurred);
	
	/**
	 * 法定代休未使用日数を設定します。
	 * @param legalCompensationUnused 法定代休未使用日数
	 */
	void setLegalCompensationUnused(double legalCompensationUnused);
	
	/**
	 * 所定代休未使用日数を設定します。
	 * @param specificCompensationUnused 所定代休未使用日数
	 */
	void setSpecificCompensationUnused(double specificCompensationUnused);
	
	/**
	 * 深夜代休未使用日数を設定します。
	 * @param lateCompensationUnused 深夜代休未使用日数
	 */
	void setLateCompensationUnused(double lateCompensationUnused);
	
	/**
	 * 所定労働時間内法定休日労働時間を設定します。
	 * @param statutoryHolidayWorkTimeIn 所定労働時間内法定休日労働時間
	 */
	void setStatutoryHolidayWorkTimeIn(int statutoryHolidayWorkTimeIn);
	
	/**
	 * 所定労働時間外法定休日労働時間を設定します。
	 * @param statutoryHolidayWorkTimeOut 所定労働時間外法定休日労働時間
	 */
	void setStatutoryHolidayWorkTimeOut(int statutoryHolidayWorkTimeOut);
	
	/**
	 * 所定労働時間内所定休日労働時間を設定します。
	 * @param prescribedHolidayWorkTimeIn 所定労働時間内所定休日労働時間
	 */
	void setPrescribedHolidayWorkTimeIn(int prescribedHolidayWorkTimeIn);
	
	/**
	 * 所定労働時間外所定休日労働時間を設定します。
	 * @param prescribedHolidayWorkTimeOut 所定労働時間外所定休日労働時間
	 */
	void setPrescribedHolidayWorkTimeOut(int prescribedHolidayWorkTimeOut);
	
	/**
	 * 無給時短時間を設定します。
	 * @param shortUnpaid 無給時短時間
	 */
	void setShortUnpaid(int shortUnpaid);
	
	/**
	 * 週40時間超勤務時間を設定します。
	 * @param weeklyOverFortyHourWorkTime 週40時間超勤務時間
	 */
	void setWeeklyOverFortyHourWorkTime(int weeklyOverFortyHourWorkTime);
	
	/**
	 * 法定内残業時間(週40時間超除く)を設定します。
	 * @param overtimeInNoWeeklyForty 法定内残業時間(週40時間超除く)
	 */
	void setOvertimeInNoWeeklyForty(int overtimeInNoWeeklyForty);
	
	/**
	 * 法定外残業時間(週40時間超除く)を設定します。
	 * @param overtimeOutNoWeeklyForty 法定外残業時間(週40時間超除く)
	 */
	void setOvertimeOutNoWeeklyForty(int overtimeOutNoWeeklyForty);
	
	/**
	 * 平日時間内時間(週40時間超除く)を設定します。
	 * @param weekDayOvertimeInNoWeeklyForty 平日時間内時間(週40時間超除く)
	 */
	void setWeekDayOvertimeInNoWeeklyForty(int weekDayOvertimeInNoWeeklyForty);
	
	/**
	 * 平日時間外時間(週40時間超除く)を設定します。
	 * @param weekDayOvertimeOutNoWeeklyForty 平日時間外時間(週40時間超除く)
	 */
	void setWeekDayOvertimeOutNoWeeklyForty(int weekDayOvertimeOutNoWeeklyForty);
	
	/**
	 * 汎用項目1(数値)を設定します。
	 * @param generalIntItem1 汎用項目1(数値)
	 */
	void setGeneralIntItem1(int generalIntItem1);
	
	/**
	 * 汎用項目2(数値)を設定します。
	 * @param generalIntItem2 汎用項目2(数値)
	 */
	void setGeneralIntItem2(int generalIntItem2);
	
	/**
	 * 汎用項目3(数値)を設定します。
	 * @param generalIntItem3 汎用項目3(数値)
	 */
	void setGeneralIntItem3(int generalIntItem3);
	
	/**
	 * 汎用項目4(数値)を設定します。
	 * @param generalIntItem4 汎用項目4(数値)
	 */
	void setGeneralIntItem4(int generalIntItem4);
	
	/**
	 * 汎用項目5(数値)を設定します。
	 * @param generalIntItem5 汎用項目5(数値)
	 */
	void setGeneralIntItem5(int generalIntItem5);
	
	/**
	 * 汎用項目1(浮動小数点数)を設定します。
	 * @param generalDoubleItem1 汎用項目1(浮動小数点数)
	 */
	void setGeneralDoubleItem1(double generalDoubleItem1);
	
	/**
	 * 汎用項目2(浮動小数点数)を設定します。
	 * @param generalDoubleItem2 汎用項目2(浮動小数点数)
	 */
	void setGeneralDoubleItem2(double generalDoubleItem2);
	
	/**
	 * 汎用項目3(浮動小数点数)を設定します。
	 * @param generalDoubleItem3 汎用項目3(浮動小数点数)
	 */
	void setGeneralDoubleItem3(double generalDoubleItem3);
	
	/**
	 * 汎用項目4(浮動小数点数)を設定します。
	 * @param generalDoubleItem4 汎用項目4(浮動小数点数)
	 */
	void setGeneralDoubleItem4(double generalDoubleItem4);
	
	/**
	 * 汎用項目5(浮動小数点数)を設定します。
	 * @param generalDoubleItem5 汎用項目5(浮動小数点数)
	 */
	void setGeneralDoubleItem5(double generalDoubleItem5);
	
	/**
	 * 特別休暇日数群(キー：休暇コード)。<br>を設定します。
	 * @param specialHolidayDays 特別休暇日数群(キー：休暇コード)。<br>
	 */
	void setSpecialHolidayDays(Map<String, Float> specialHolidayDays);
	
	/**
	 * 特別休暇時間数群(キー：休暇コード)。<br>を設定します。
	 * @param specialHolidayHours 特別休暇時間数群(キー：休暇コード)。<br>
	 */
	void setSpecialHolidayHours(Map<String, Integer> specialHolidayHours);
	
	/**
	 * その他休暇日数群(キー：休暇コード)。<br>を設定します。
	 * @param otherHolidayDays その他休暇日数群(キー：休暇コード)。<br>
	 */
	void setOtherHolidayDays(Map<String, Float> otherHolidayDays);
	
	/**
	 * その他休暇時間数群(キー：休暇コード)。<br>を設定します。
	 * @param otherHolidayHours その他休暇時間数群(キー：休暇コード)。<br>
	 */
	void setOtherHolidayHours(Map<String, Integer> otherHolidayHours);
	
	/**
	 * 欠勤日数群(キー：休暇コード)。<br>を設定します。
	 * @param absenceDays 欠勤日数群(キー：休暇コード)。<br>
	 */
	void setAbsenceDays(Map<String, Float> absenceDays);
	
	/**
	 * 欠勤時間数群(キー：休暇コード)。<br>を設定します。
	 * @param absenceHours 欠勤時間数群(キー：休暇コード)。<br>
	 */
	void setAbsenceHours(Map<String, Integer> absenceHours);
	
	/**
	 * 汎用項目群(キー：汎用コード)。<br>を設定します。
	 * @param generalMap 汎用項目群(キー：汎用コード)。<br>
	 */
	void setGeneralValues(Map<String, Object> generalMap);
}
