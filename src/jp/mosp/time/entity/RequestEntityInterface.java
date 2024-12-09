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

import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;

/**
 * 申請エンティティインターフェース。<br>
 */
public interface RequestEntityInterface {
	
	/**
	 * 個人IDの設定。<br>
	 * <br>
	 * @param personalId セットする個人ID
	 */
	void setPersonalId(String personalId);
	
	/**
	 * 対象日の設定。<br>
	 * <br>
	 * @param targetDate セットする対象日
	 */
	void setTargetDate(Date targetDate);
	
	/**
	 * 勤怠データが存在するかを確認する。<br>
	 * <br>
	 * 承認状況が下書以上の勤怠データが存在すれば、trueを返す。<br>
	 * (勤怠データには取下状態がない。)
	 * <br>
	 * @return 確認結果(true：勤怠データが存在する、false：存在しない)
	 */
	boolean hasAttendance();
	
	/**
	 * 勤怠申請がされているかを確認する。<br>
	 * <br>
	 * {@link WorkflowUtility#isApplied(WorkflowDtoInterface)}
	 * で確認する。<br>
	 * <br>
	 * @return 確認結果(true：勤怠申請がされていいる、false：勤怠申請がされていない)
	 */
	boolean isAttendanceApplied();
	
	/**
	 * 勤怠データに直行が設定されているかを確認する。<br>
	 * 勤怠データが存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：勤怠データに直行が設定されている、false：されていない)
	 */
	boolean isAttendanceDirectStart();
	
	/**
	 * 勤怠データに直帰が設定されているかを確認する。<br>
	 * 勤怠データが存在しない場合は、falseを返す。<br>
	 * <br>
	 * @return 確認結果(true：勤怠データに直帰が設定されている、false：されていない)
	 */
	boolean isAttendanceDirectEnd();
	
	/**
	 * 勤務形態を取得する。<br>
	 * <br>
	 * カレンダで登録されている勤務形態及び各種申請情報から、取るべき勤務形態を割り出す。
	 * <br>
	 * 各種申請につき、承認済フラグによって考慮する申請を決める。<br>
	 * 但し、勤怠申請については、承認済フラグにかかわらず、
	 * 下書、1次戻を含めて申請済申請も承認済申請も考慮に入れる。<br>
	 * <br>
	 * 勤務形態は、次の方法で決定する。<br>
	 * <br>
	 * 1.勤怠申請が存在する場合：<br>
	 * 勤怠申請に設定されている勤務形態を返す。<br>
	 * <br>
	 * 2.全休の場合：<br>
	 * 空文字を返す。<br>
	 * 但し、全休の振替休日情報がある場合は、その振替種別を返す。<br>
	 * <br>
	 * 3.勤務形態変更申請がある場合：
	 * 変更勤務形態(勤務形態変更申請の勤務形態)を返す。<br>
	 * 振替出勤申請の場合に勤務形態変更申請が可能なため、
	 * 振出・休出申請の勤務形態を取得する前に確認をする。<br>
	 * <br>
	 * 4.振出・休出申請がある場合：<br>
	 * 振出・休出申請の勤務形態を取得する。<br>
	 * <br>
	 * 5.それ以外の場合：
	 * 予定勤務形態コード(カレンダで登録されている勤務形態)を返す。<br>
	 * <br>
	 * @param isAttendanceConsidered 勤怠申請考慮フラグ(true：考慮する、false：しない)
	 * @param statuses               対象となる承認状況群
	 * @return 対象個人IDが対象日に取るべき勤務形態
	 */
	String getWorkType(boolean isAttendanceConsidered, Set<String> statuses);
	
	/**
	 * 勤務形態を取得する。<br>
	 * <br>
	 * {@link #getWorkType(boolean, Set) }を参照。<br>
	 * <br>
	 * @param isAttendanceConsidered 勤怠申請考慮フラグ(true：考慮する、false：しない)
	 * @param isCompleted            承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 対象個人IDが対象日に取るべき勤務形態
	 */
	String getWorkType(boolean isAttendanceConsidered, boolean isCompleted);
	
	/**
	 * 勤務形態を取得する。<br>
	 * <br>
	 * {@link #getWorkType(boolean, boolean) }を参照。<br>
	 * <br>
	 * 承認済フラグはfalseとする。<br>
	 * <br>
	 * @param isAttendanceConsidered 勤怠申請考慮フラグ(true：考慮する、false：しない)
	 * @return 対象個人IDが対象日に取るべき勤務形態
	 */
	String getWorkType(boolean isAttendanceConsidered);
	
	/**
	 * 勤務形態を取得する。<br>
	 * <br>
	 * {@link #getWorkType(boolean, boolean) }を参照。<br>
	 * <br>
	 * 勤怠申請考慮フラグはtrue(勤怠申請を考慮する)とする。<br>
	 * 承認済フラグはfalse(申請済申請を考慮)とする。<br>
	 * <br>
	 * @return 対象個人IDが対象日に取るべき勤務形態
	 */
	String getWorkType();
	
	/**
	 * 勤務日であるかを確認する。<br>
	 * <br>
	 * カレンダで登録されている勤務形態及び各種申請情報から、確認する。
	 * <br>
	 * 各種申請につき、ワークフローが下書、1次戻、取下の申請は、考慮に入れない。<br>
	 * <br>
	 * 勤務日であるかは、次の方法で確認する。<br>
	 * <br>
	 * 1.全休の場合：<br>
	 * 勤務日でないと判断。<br>
	 * <br>
	 * 2.振出・休出申請がある場合：<br>
	 * 勤務日であると判断。<br>
	 * <br>
	 * 3.予定勤務形態コードが法定休日か所定休日である場合：<br>
	 * 勤務日でないと判断。<br>
	 * <br>
	 * 4.それ以外の場合：<br>
	 * 勤務日であると判断。<br>
	 * <br>
	 * @return 確認結果(true：勤務日である、false：勤務日でない)
	 */
	boolean isWorkDay();
	
	/**
	 * 振出・休出申請(休日出勤)の休出予定時間(0:00からの分)を取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振出・休出申請(休出申請)の休出予定時間(0:00からの分)
	 */
	TimeDuration getWorkOnHolidayTime(boolean isCompleted);
	
	/**
	 * 振出・休出申請(休日出勤)の出勤予定時刻を取得する。<br>
	 * <br>
	 * 振出・休出申請(休日出勤)の出勤予定時刻が無い場合は、nullを返す。<br>
	 * 振出・休出申請(振替出勤)の場合は、nullを返す。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 振出・休出申請(休日出勤)の出勤予定時刻
	 */
	Date getWorkOnHolidayStartTime(Set<String> statuses);
	
	/**
	 * 振出・休出申請(休日出勤)の出勤予定時刻を取得する。<br>
	 * <br>
	 * {@link #getWorkOnHolidayStartTime(Set) }を参照。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振出・休出申請(休日出勤)の出勤予定時刻
	 */
	Date getWorkOnHolidayStartTime(boolean isCompleted);
	
	/**
	 * 振出・休出申請(休日出勤)の退勤予定時刻を取得する。<br>
	 * <br>
	 * 振出・休出申請(休日出勤)の退勤予定時刻が無い場合は、nullを返す。<br>
	 * 振出・休出申請(振替出勤)の場合は、nullを返す。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 振出・休出申請(休日出勤)の退勤予定時刻
	 */
	Date getWorkOnHolidayEndTime(Set<String> statuses);
	
	/**
	 * 残業申請(勤務前残業)の申請時間(分)を取得する。<br>
	 * 残業申請(勤務前残業)が無い場合は、0を返す。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 残業申請(勤務前残業)の申請時間(分)
	 */
	int getOvertimeMinutesBeforeWork(Set<String> statuses);
	
	/**
	 * 残業申請(勤務前残業)の申請時間(分)を取得する。<br>
	 * {@link #getOvertimeMinutesBeforeWork(Set) }を参照。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 残業申請(勤務前残業)の申請時間(分)
	 */
	int getOvertimeMinutesBeforeWork(boolean isCompleted);
	
	/**
	 * 残業申請(勤務後残業)の申請時間(分)を取得する。
	 * <br>
	 * 残業申請(勤務後残業)が無い場合は、0を返す。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 残業申請(勤務後残業)の申請時間(分)
	 */
	int getOvertimeMinutesAfterWork(Set<String> statuses);
	
	/**
	 * 残業申請(勤務後残業)の申請時間(分)を取得する。
	 * {@link #getOvertimeMinutesAfterWork(Set) }を参照。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 残業申請(勤務後残業)の申請時間(分)
	 */
	int getOvertimeMinutesAfterWork(boolean isCompleted);
	
	/**
	 * 対象日が全休であるかを確認する。<br>
	 * 全休、或いは前半休+後半休であれば、全休と判断する。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：全休である、false：そうでない)
	 */
	boolean isAllHoliday(Set<String> statuses);
	
	/**
	 * 対象日が全休であるかを確認する。<br>
	 * <br>
	 * {@link #isAllHoliday(Set) }を参照。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：全休である、false：そうでない)
	 */
	boolean isAllHoliday(boolean isCompleted);
	
	/**
	 * 対象日が前半休であるかを確認する。<br>
	 * 全休がなく、後半休がなく、前半休があれば、前半休と判断する。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：前半休である、false：そうでない)
	 */
	boolean isAmHoliday(Set<String> statuses);
	
	/**
	 * 対象日が前半休であるかを確認する。<br>
	 * <br>
	 * {@link #isAmHoliday(Set) }を参照。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：前半休である、false：そうでない)
	 */
	boolean isAmHoliday(boolean isCompleted);
	
	/**
	 * 対象日が後半休であるかを確認する。<br>
	 * 全休がなく、前半休がなく、後半休があれば、後半休と判断する。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：後半休である、false：そうでない)
	 */
	boolean isPmHoliday(Set<String> statuses);
	
	/**
	 * 対象日が後半休であるかを確認する。<br>
	 * <br>
	 * {@link #isPmHoliday(Set) }を参照。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：後半休である、false：そうでない)
	 */
	boolean isPmHoliday(boolean isCompleted);
	
	/**
	 * 対象日が半休(前半休か後半休)であるかを確認する。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：半休(前半休か後半休)である、false：そうでない)
	 */
	boolean isHalfHoliday(Set<String> statuses);
	
	/**
	 * 振出・休出申請情報が有るかを確認する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：振出・休出申請情報が有る、false：無い)
	 */
	boolean hasWorkOnHoliday(Set<String> statuses);
	
	/**
	 * 振出・休出申請情報が有るかを確認する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：振出・休出申請情報が有る、false：無い)
	 */
	boolean hasWorkOnHoliday(boolean isCompleted);
	
	/**
	 * 振替出勤(午前)があるかを確認する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：振替出勤(午前)が有る、false：振替出勤(午前)が無い)
	 */
	boolean hasAmWorkOnHoliday(boolean isCompleted);
	
	/**
	 * 振替出勤(午前)があるかを確認する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：振替出勤(午前)が有る、false：振替出勤(午前)が無い)
	 */
	boolean hasAmWorkOnHoliday(Set<String> statuses);
	
	/**
	 * 振替出勤(午後)があるかを確認する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：振替出勤(午前)が有る、false：振替出勤(午前)が無い)
	 */
	boolean hasPmWorkOnHoliday(boolean isCompleted);
	
	/**
	 * 振替出勤(午後)があるかを確認する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：振替出勤(午前)が有る、false：振替出勤(午前)が無い)
	 */
	boolean hasPmWorkOnHoliday(Set<String> statuses);
	
	/**
	 * 振出・休出申請情報(半日振替以外)があるかを確認する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：振出・休出申請情報(半日振替以外)が有る、false：無い)
	 */
	boolean hasWorkOnHolidayNotHalf(Set<String> statuses);
	
	/**
	 * 振替休日情報があるかを確認する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：振替休日情報が有る、false：無い)
	 */
	boolean hasSubstitute(Set<String> statuses);
	
	/**
	 * 法定休日出勤(振替申請しない)であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：法定休日出勤である、false：そうでない)
	 */
	boolean isWorkOnLegal(boolean isCompleted);
	
	/**
	 * 所定休日出勤(振替申請しない)であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：所定休日出勤である、false：そうでない)
	 */
	boolean isWorkOnPrescribed(boolean isCompleted);
	
	/**
	 * 振替出勤であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：振替出勤である、false：そうでない)
	 */
	boolean isWorkOnHolidaySubstitute(boolean isCompleted);
	
	/**
	 * 休日出勤(振替申請しない)であるかを確認する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：休日出勤(振替申請しない)である、false：そうでない)
	 */
	boolean isWorkOnHolidaySubstituteOff(Set<String> statuses);
	
	/**
	 * 休日出勤(振替申請しない)であるかを確認する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 確認結果(true：休日出勤(振替申請しない)である、false：そうでない)
	 */
	boolean isWorkOnHolidaySubstituteOff(boolean isCompleted);
	
	/**
	 * 休暇のワークフロー情報を取得する。<br>
	 * 休暇でない場合は、nullを返す。<br>
	 * 休暇申請情報と代休申請情報と振替休日情報を対象とする。<br>
	 * 半休+半休の場合は、承認状態が低い方のワークフロー情報を取得する。<br>
	 * 勤怠一覧で承認状態を設定するため等に、用いられる。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 休暇のワークフロー情報
	 */
	WorkflowDtoInterface getHolidayWorkflow(Set<String> statuses);
	
	/**
	 * 初回連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻、2つ目に終了時刻の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 初回連続時間休時刻(開始時刻及び終了時刻)
	 */
	List<Date> getHourlyHolidayFirstSequenceTimes(Set<String> statuses);
	
	/**
	 * 初回連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * 申請済申請を考慮する。<br>
	 * {@link #getHourlyHolidayFirstSequenceTimes(Set) }を参照。<br>
	 * <br>
	 * @return 初回連続時間休時刻(開始時刻及び終了時刻)
	 */
	List<Date> getHourlyHolidayFirstSequenceTimes();
	
	/**
	 * 初回連続時間休時刻(開始時刻(分)及び終了時刻(分))を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻(分)、2つ目に終了時刻(分)の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 初回連続時間休時刻(開始時刻及び終了時刻)
	 */
	List<Integer> getHourlyHolidayFirstSequenceMinutes();
	
	/**
	 * 初回連続時間休時間間隔を取得する。<br>
	 * <br>
	 * 時間休が無い場合は、妥当でない時間間隔が返る。<br>
	 * <br>
	 * @return 初回連続時間休時間間隔
	 */
	TimeDuration getHourlyHolidayFirstSequence();
	
	/**
	 * 最終連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻、2つ目に終了時刻の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 最終連続時間休時刻(開始時刻及び終了時刻)
	 */
	List<Date> getHourlyHolidayLastSequenceTimes(Set<String> statuses);
	
	/**
	 * 最終連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 申請済申請を考慮する。<br>
	 * {@link #getHourlyHolidayLastSequenceTimes(Set) }を参照。<br>
	 * <br>
	 * @return 最終連続時間休時刻(開始時刻及び終了時刻)
	 */
	List<Date> getHourlyHolidayLastSequenceTimes();
	
	/**
	 * 最終連続時間休時刻(開始時刻及び終了時刻)を取得する。<br>
	 * <br>
	 * 1つ目に開始時刻(分)、2つ目に終了時刻(分)の入ったリストを返す。<br>
	 * 時間休が無い場合は、空のリストが返る。<br>
	 * <br>
	 * @return 最終連続時間休時刻(開始時刻及び終了時刻)
	 */
	List<Integer> getHourlyHolidayLastSequenceMinutes();
	
	/**
	 * 最終連続時間休時間間隔を取得する。<br>
	 * <br>
	 * 時間休が無い場合は、妥当でない時間間隔が返る。<br>
	 * <br>
	 * @return 最終連続時間休時間間隔
	 */
	TimeDuration getHourlyHolidayLastSequence();
	
	/**
	 * 連続時間休時刻(開始時刻及び終了時刻)を対象日からの分数として取得する。<br>
	 * <br>
	 * @param holidayTimeList 連続時間休時刻(開始時刻及び終了時刻)
	 * @return 連続時間休時刻(開始時刻及び終了時刻)(分)
	 */
	List<Integer> getHourlyHolidaySequenceMinutes(List<Date> holidayTimeList);
	
	/**
	 * 休暇申請情報(時間休)(時休開始時刻順)群を取得する。<br>
	 * 時休開始時刻をキー、休暇申請情報を値とする。<br>
	 * 休暇申請情報(時間休)が存在しない場合は、空のマップを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 休暇申請情報(時間休)(時休開始時刻順)群
	 */
	Map<Date, HolidayRequestDtoInterface> getHourlyHolidayMap(boolean isCompleted);
	
	/**
	 * 時間単位休暇時間間隔群(キー:開始時刻(キー順))を取得する。<br>
	 * 休暇申請情報(時間休)が存在しない場合は、空のMapを返す。<br>
	 * 開始時刻と終了時刻が接している時間単位休暇は一つの時間間隔として扱う。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 時間単位休暇時間間隔群(キー:開始時刻(キー順))
	 */
	Map<Integer, TimeDuration> getHourlyHolidayTimes(boolean isCompleted);
	
	/**
	 * 時間単位休暇時間(分)を取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 時間単位休暇時間(分)
	 */
	int getHourlyHolidayMinutes(boolean isCompleted);
	
	/**
	 * 振替休日情報(全休or前半休+後半休)の休暇種別(法定休日、所定休日)を取得する。<br>
	 * 振替休日情報(全休or前半休+後半休)が存在しない場合は、空文字を返す。<br>
	 * 半日の振替は所定休日のみが認められている。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ考慮、false：申請済申請を考慮)
	 * @return 振替休日情報(全休or前半休+後半休)の休暇種別(法定休日、所定休日)
	 */
	String getSubstituteType(boolean isCompleted);
	
	/**
	 * 振替休日情報(全休or前半休+後半休)の休暇種別(法定休日、所定休日)を取得する。<br>
	 * 振替休日情報(全休or前半休+後半休)が存在しない場合は、空文字を返す。<br>
	 * 半日の振替は所定休日のみが認められている。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 振替休日情報(全休or前半休+後半休)の休暇種別(法定休日、所定休日)
	 */
	String getSubstituteType(Set<String> statuses);
	
	/**
	 * 半日振休＋半日振休があるか判断する。
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 確認結果(true：半日振休＋半日振休、false：そうでない)
	 */
	boolean isAmPmHalfSubstitute(boolean isCompleted);
	
	/**
	 * 半日振休＋半日振休があるか判断する。
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：半日振休＋半日振休、false：そうでない)
	 */
	boolean isAmPmHalfSubstitute(Set<String> statuses);
	
	/**
	 * 半日振替の振替かどうか判断する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 確認結果(true：半日振替の振替、false：そうでない)
	 */
	boolean isHalfPostpone(boolean isCompleted);
	
	/**
	 * 勤怠の申請が可能か確認する。<br>
	 * 次の条件を満たす時、勤怠の申請が可能と判断する。<br>
	 * ・勤怠が未申請である。<br>
	 * ・申請済であり未承認の残業申請がない。<br>
	 * <br>
	 * @return 確認結果(true：勤怠申請可能、false：勤怠申請不可)
	 */
	boolean isAttendanceAppliable();
	
	/**
	 * 残業申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	boolean isOvertimeApplied(boolean isContainCompleted);
	
	/**
	 * 休暇申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	boolean isHolidayApplied(boolean isContainCompleted);
	
	/**
	 * 代休申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	boolean isSubHolidayApplied(boolean isContainCompleted);
	
	/**
	 * 休日出勤申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	boolean isWorkOnHolidayHolidayApplied(boolean isContainCompleted);
	
	/**
	 * 振替休日申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	boolean isSubstituteApplied(boolean isContainCompleted);
	
	/**
	 * 時差出勤申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	boolean isDifferenceApplied(boolean isContainCompleted);
	
	/**
	 * 勤務形態変更申請が申請されているかを確認。<br>
	 * @param isContainCompleted 承認済含むフラグ(true：承認済を含む、false:承認済を含まない)
	 * @return 確認結果(true：未承認、false：未承認でない)
	 */
	boolean isWorkTypeChangeApplied(boolean isContainCompleted);
	
	/**
	 * 勤怠情報を取得する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 勤怠情報
	 */
	AttendanceDtoInterface getAttendanceDto(Set<String> statuses);
	
	/**
	 * 休暇申請情報リストを取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 休暇申請情報リスト
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestList(boolean isCompleted);
	
	/**
	 * 休暇申請情報リストを取得する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 休暇申請情報リスト
	 */
	List<HolidayRequestDtoInterface> getHolidayRequestList(Set<String> statuses);
	
	/**
	 * 代休申請情報リストを取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 代休申請情報リスト
	 */
	List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(boolean isCompleted);
	
	/**
	 * 代休申請情報リストを取得する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 代休申請情報リスト
	 */
	List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(Set<String> statuses);
	
	/**
	 * 振替休日情報リストを取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 代休申請情報リスト
	 */
	List<SubstituteDtoInterface> getSubstituteList(boolean isCompleted);
	
	/**
	 * 振替休日情報リストを取得する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 振替休日情報リスト
	 */
	List<SubstituteDtoInterface> getSubstituteList(Set<String> statuses);
	
	/**
	 * 振出・休出申請情報を取得する。<br>
	 * 対象が存在しない場合は、nullを返す。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 振出・休出申請情報
	 */
	WorkOnHolidayRequestDtoInterface getWorkOnHolidayRequestDto(boolean isCompleted);
	
	/**
	 * 振出・休出申請情報を取得する。<br>
	 * 対象が存在しない場合は、nullを返す。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 振出・休出申請情報
	 */
	WorkOnHolidayRequestDtoInterface getWorkOnHolidayRequestDto(Set<String> statuses);
	
	/**
	 * 残業申請情報リストを取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 休暇申請情報リスト
	 */
	List<OvertimeRequestDtoInterface> getOvertimeRequestList(boolean isCompleted);
	
	/**
	 * 残業申請情報リストを取得する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 残業申請情報リスト
	 */
	List<OvertimeRequestDtoInterface> getOvertimeRequestList(Set<String> statuses);
	
	/**
	 * 勤務形態変更申請情報を取得する。<br>
	 * 対象が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 振出・休出申請情報
	 */
	WorkTypeChangeRequestDtoInterface getWorkTypeChangeRequestDto(boolean isCompleted);
	
	/**
	 * 勤務形態変更申請情報を取得する。<br>
	 * 対象が存在しない場合は、nullを返す。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 勤務形態変更申請情報
	 */
	WorkTypeChangeRequestDtoInterface getWorkTypeChangeRequestDto(Set<String> statuses);
	
	/**
	 * 時差出勤申請情報を取得する。<br>
	 * 対象が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 時差出勤申請情報
	 */
	DifferenceRequestDtoInterface getDifferenceRequestDto(boolean isCompleted);
	
	/**
	 * 時差出勤申請情報を取得する。<br>
	 * 対象が存在しない場合は、nullを返す。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 勤務形態変更申請情報
	 */
	DifferenceRequestDtoInterface getDifferenceRequestDto(Set<String> statuses);
	
	/**
	 * 出勤日数を計算する。<br>
	 * 勤怠情報(日々)の出勤日数(work_days)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 出勤日数
	 */
	float calcWorkDays(boolean isCompleted);
	
	/**
	 * 有給休暇用出勤日数を計算する。<br>
	 * 勤怠情報(日々)の有給休暇用出勤日数(work_days_for_paid_leave)及び
	 * 有給休暇用全労働日(total_work_days_for_paid_leave)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 有給休暇用出勤日数
	 */
	int calcWorkDaysForPaidHoliday(boolean isCompleted);
	
	/**
	 * 休日出勤回数を計算する。<br>
	 * 勤怠情報(日々)の休日出勤回数(times_holiday_work)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 休日出勤回数
	 */
	int calcWorkOnHolidayTimes(boolean isCompleted);
	
	/**
	 * 法定休日出勤回数を計算する。<br>
	 * 勤怠情報(日々)の法定休日出勤回数(times_legal_holiday_work)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 法定休日出勤回数
	 */
	int calcWorkOnLegalHolidayTimes(boolean isCompleted);
	
	/**
	 * 所定休日出勤回数を計算する。<br>
	 * 勤怠情報(日々)の所定休日出勤回数(times_prescribed_holiday_work)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 所定休日出勤回数
	 */
	int calcWorkOnPrescribedHolidayTimes(boolean isCompleted);
	
	/**
	 * 有給休暇日数を計算する。<br>
	 * 勤怠情報(日々)の有給休暇日数(paid_leave_days)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 有給休暇日数
	 */
	float calcPaidHolidayDays(boolean isCompleted);
	
	/**
	 * 有給休暇時間数(時間)を計算する。<br>
	 * 勤怠情報(日々)の有給休暇時間数(paid_leave_hours)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 有給休暇時間数(時間)
	 */
	int calcPaidHolidayHours(boolean isCompleted);
	
	/**
	 * ストック休暇日数を計算する。<br>
	 * 勤怠情報(日々)のストック休暇日数(stock_leave_days)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return ストック休暇日数
	 */
	float calcStockHolidayDays(boolean isCompleted);
	
	/**
	 * 代休日数を計算する。<br>
	 * 勤怠情報(日々)の代休日数(compensation_days)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 代休日数
	 */
	float calcSubHolidayDays(boolean isCompleted);
	
	/**
	 * 法定代休日数を計算する。<br>
	 * 勤怠情報(日々)の法定代休日数(legal_compensation_days)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 法定代休日数
	 */
	float calcLegalSubHolidayDays(boolean isCompleted);
	
	/**
	 * 所定代休日数を計算する。<br>
	 * 勤怠情報(日々)の所定代休日数(prescribed_compensation_days)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 所定代休日数
	 */
	float calcPrescribedSubHolidayDays(boolean isCompleted);
	
	/**
	 * 深夜代休日数を計算する。<br>
	 * 勤怠情報(日々)の深夜代休日数(night_compensation_days)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 深夜代休日数
	 */
	float calcNightSubHolidayDays(boolean isCompleted);
	
	/**
	 * 特別休暇日数を計算する。<br>
	 * 勤怠情報(日々)の特別休暇日数(special_leave_days)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 特別休暇日数
	 */
	float calcSpecialHolidayDays(boolean isCompleted);
	
	/**
	 * 特別休暇時間数(時間)を計算する。<br>
	 * 勤怠情報(日々)の特別休暇時間数(special_leave_hours)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 特別休暇時間数(時間)
	 */
	int calcSpecialHolidayHours(boolean isCompleted);
	
	/**
	 * その他休暇日数を計算する。<br>
	 * 勤怠情報(日々)のその他休暇日数(other_leave_days)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return その他休暇日数
	 */
	float calcOtherHolidayDays(boolean isCompleted);
	
	/**
	 * その他休暇時間数(時間)を計算する。<br>
	 * 勤怠情報(日々)のその他休暇時間数(other_leave_hours)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return その他休暇時間数(時間)
	 */
	int calcOtherHolidayHours(boolean isCompleted);
	
	/**
	 * 欠勤日数を計算する。<br>
	 * 勤怠情報(日々)の欠勤日数(absence_days)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 欠勤日数
	 */
	float calcAbsenceDays(boolean isCompleted);
	
	/**
	 * 欠勤時間数(時間)を計算する。<br>
	 * 勤怠情報(日々)の欠勤時間数(absence_hours)に設定される値を計算する。<br>
	 * <br>
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ取得、false：申請済申請を取得)
	 * @return 欠勤時間数(時間)
	 */
	int calcAbsenceHours(boolean isCompleted);
	
	/**
	 * @return personalId
	 */
	String getPersonalId();
	
	/**
	 * @return targetDate
	 */
	Date getTargetDate();
	
	/**
	 * @param holidayRequestList セットする holidayRequestList
	 */
	void setHolidayRequestList(List<HolidayRequestDtoInterface> holidayRequestList);
	
	/**
	 * @param overtimeRequestList セットする overTimeRequestList
	 */
	void setOverTimeRequestList(List<OvertimeRequestDtoInterface> overtimeRequestList);
	
	/**
	 * @param substituteList セットする substituteList
	 */
	void setSubstituteList(List<SubstituteDtoInterface> substituteList);
	
	/**
	 * @param subHolidayRequestList セットする subHolidayRequestList
	 */
	void setSubHolidayRequestList(List<SubHolidayRequestDtoInterface> subHolidayRequestList);
	
	/**
	 * @param workOnHolidayRequestDto セットする workOnHolidayRequestDto
	 */
	void setWorkOnHolidayRequestDto(WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto);
	
	/**
	 * @param differenceRequestDto セットする differenceRequestDto
	 */
	void setDifferenceRequestDto(DifferenceRequestDtoInterface differenceRequestDto);
	
	/**
	 * @param workTypeChangeRequestDto セットする workTypeChangeRequestDto
	 */
	void setWorkTypeChangeRequestDto(WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto);
	
	/**
	 * @return attendanceDto
	 */
	AttendanceDtoInterface getAttendanceDto();
	
	/**
	 * @param attendanceDto セットする attendanceDto
	 */
	void setAttendanceDto(AttendanceDtoInterface attendanceDto);
	
	/**
	 * @return workflowMap
	 */
	Map<Long, WorkflowDtoInterface> getWorkflowMap();
	
	/**
	 * @param workflowMap セットする workflowMap
	 */
	void setWorkflowMap(Map<Long, WorkflowDtoInterface> workflowMap);
	
	/**
	 * @param scheduledWorkTypeCode セットする scheduledWorkTypeCode
	 */
	void setScheduledWorkTypeCode(String scheduledWorkTypeCode);
	
	/**
	 * @param substitutedWorkTypeCode セットする substitutedWorkTypeCode
	 */
	void setSubstitutedWorkTypeCode(String substitutedWorkTypeCode);
	
}
