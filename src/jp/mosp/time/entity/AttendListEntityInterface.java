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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;

/**
 * 勤怠一覧エンティティインターフェース。<br>
 */
public interface AttendListEntityInterface {
	
	/**
	 * 勤怠一覧情報リストに値を設定する。<br>
	 * 勤怠一覧区分で、設定内容を判断する。<br>
	 * @param listType 勤怠一覧区分
	 * @throws MospException 日付の変換に失敗した場合
	 */
	void fillInAttendList(int listType) throws MospException;
	
	/**
	 * 勤怠一覧情報リストを集計する。<br>
	 * 集計結果は、リストの最終レコードに設定される。<br>
	 * アドオン等で値を変更した後に再集計する際に利用する。<br>
	 */
	void totalAttendanceList();
	
	/**
	 * 勤怠一覧情報に表示用文字列(日付、時刻、時間等)を設定する。<br>
	 * アドオン等で値を変更した後に再設定する際に利用する。<br>
	 * @throws MospException 日付操作に失敗した場合
	 */
	void setStringFields() throws MospException;
	
	/**
	 * MosP処理情報を設定する。<br>
	 * @param mospParams MosP処理情報
	 */
	void setMospParams(MospParams mospParams);
	
	/**
	 * 勤怠一覧情報リストを取得する。<br>
	 * @return 勤怠一覧情報リスト
	 */
	List<AttendanceListDto> getAttendList();
	
	/**
	 * 勤怠一覧情報リストを設定する。<br>
	 * @param attendList 勤怠一覧情報リスト
	 */
	void setAttendList(List<AttendanceListDto> attendList);
	
	/**
	 * 申請エンティティを取得する。<br>
	 * <br>
	 * フィールドに設定された各種申請を基に、対象日の申請を抽出する。<br>
	 * <br>
	 * @param dto 勤怠一覧情報
	 * @return 申請エンティティ
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	RequestEntityInterface getRequestEntity(AttendanceListDto dto) throws MospException;
	
	/**
	 * 勤務日に退職か休職をしているかを確認する。<br>
	 * @param workDate 勤務日
	 * @return 確認結果(true：勤務日に退職か休職をしている、false：勤務日に退職も休職もしていない)
	 */
	boolean isRetireOrSusoension(Date workDate);
	
	/**
	 * 退職日を設定する。<br>
	 * @param retireDate 退職日
	 */
	void setRetireDate(Date retireDate);
	
	/**
	 * 休職情報群を取得する。<br>
	 * @return 休職情報群
	 */
	Collection<SuspensionDtoInterface> getSuspensions();
	
	/**
	 * 休職情報群を設定する。<br>
	 * @param suspensions 休職情報群
	 */
	void setSuspensions(Collection<SuspensionDtoInterface> suspensions);
	
	/**
	 * カレンダ日情報を取得する。<br>
	 * @param workDate 勤務日
	 * @return カレンダ日情報
	 */
	ScheduleDateDtoInterface getScheduleDate(Date workDate);
	
	/**
	 * カレンダ日情報群(キー：日)を設定する。<br>
	 * @param scheduleDates
	 */
	void setScheduleDates(Map<Date, ScheduleDateDtoInterface> scheduleDates);
	
	/**
	 * 勤務形態エンティティ群(キー：勤務形態コード)を設定する。<br>
	 * @param workTypeEntities 勤務形態エンティティ群(キー：勤務形態コード)
	 */
	void setWorkTypeEntities(Map<String, List<WorkTypeEntityInterface>> workTypeEntities);
	
	/**
	 * 勤怠(日々)情報を取得する。<br>
	 * @param workDate 勤務日
	 * @return 勤怠(日々)情報
	 */
	AttendanceDtoInterface getAttendance(Date workDate);
	
	/**
	 * 勤怠(日々)情報群(キー：勤務日)を設定する。<br>
	 * @param attendances 勤怠(日々)情報群(キー：勤務日)
	 */
	void setAttendances(Map<Date, AttendanceDtoInterface> attendances);
	
	/**
	 * 残業申請情報群を設定する。<br>
	 * @param overtimeRequests 残業申請情報群
	 */
	void setOvertimeRequests(Map<Date, List<OvertimeRequestDtoInterface>> overtimeRequests);
	
	/**
	 * 休暇申請情報群を設定する。<br>
	 * @param holidayRequests 休暇申請情報群
	 */
	void setHolidayRequests(Map<Date, List<HolidayRequestDtoInterface>> holidayRequests);
	
	/**
	 * 休出申請情報群を設定する。<br>
	 * @param workOnHolidayRequests 休出申請情報群
	 */
	void setWorkOnHolidayRequests(Map<Date, WorkOnHolidayRequestDtoInterface> workOnHolidayRequests);
	
	/**
	 * 振り替えられたカレンダ日情報群(キー：勤務日)を設定する。<br>
	 * @param substitutedSchedules 振り替えられたカレンダ日情報群(キー：勤務日)
	 */
	void setSubstitutedSchedules(Map<Date, ScheduleDateDtoInterface> substitutedSchedules);
	
	/**
	 * 振替休日情報群(キー：振替日)を設定する。<br>
	 * @param substitutes 振替休日情報群(キー：振替日)
	 */
	void setSubstitutes(Map<Date, List<SubstituteDtoInterface>> substitutes);
	
	/**
	 * 代休申請情報群を設定する。<br>
	 * @param subHolidayRequests 代休申請情報群
	 */
	void setSubHolidayRequests(Map<Date, List<SubHolidayRequestDtoInterface>> subHolidayRequests);
	
	/**
	 * 代休情報群(キー：代休種別)を設定する。<br>
	 * @param subHolidays 代休情報群(キー：代休種別)
	 */
	void setSubHolidays(Map<Integer, List<SubHolidayDtoInterface>> subHolidays);
	
	/**
	 * 勤務形態変更申請情報群を設定する。<br>
	 * @param workTypeChangeRequests 勤務形態変更申請情報群
	 */
	void setWorkTypeChangeRequests(Map<Date, WorkTypeChangeRequestDtoInterface> workTypeChangeRequests);
	
	/**
	 * 時差出勤申請情報群を設定する。<br>
	 * @param differenceRequests 時差出勤申請情報群
	 */
	void setDifferenceRequests(Map<Date, DifferenceRequestDtoInterface> differenceRequests);
	
	/**
	 * ワークフロー情報群を設定する。<br>
	 * @param workflows ワークフロー情報群
	 */
	void setWorkflows(Map<Long, WorkflowDtoInterface> workflows);
	
	/**
	 * 勤怠修正情報群(キー：勤務日)を設定する。<br>
	 * @param corrections 勤怠修正情報群(キー：勤務日)
	 */
	void setCorrections(Map<Date, List<AttendanceCorrectionDtoInterface>> corrections);
	
	/**
	 * 休暇種別情報群を取得する。<br>
	 * @return 休暇種別情報群
	 */
	Set<HolidayDtoInterface> getHolidays();
	
	/**
	 * 休暇種別情報群を設定する。<br>
	 * @param holidays 休暇種別情報群
	 */
	void setHolidays(Set<HolidayDtoInterface> holidays);
	
}
