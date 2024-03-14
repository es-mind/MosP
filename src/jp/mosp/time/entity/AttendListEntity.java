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
package jp.mosp.time.entity;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.constant.TimeConst;
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
import jp.mosp.time.utils.AttendanceUtility;
import jp.mosp.time.utils.DifferenceUtility;
import jp.mosp.time.utils.HolidayUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;
import jp.mosp.time.utils.WorkTypeUtility;

/**
 * 勤怠一覧エンティティ。<br>
 */
public class AttendListEntity implements AttendListEntityInterface {
	
	/**
	 * 勤怠一覧区分(勤怠)。<br>
	 */
	public static final int										TYPE_LIST_ATTENDANCE	= AttendanceUtility.TYPE_LIST_ATTENDANCE;
	
	/**
	 * 勤怠一覧区分(実績)。<br>
	 */
	public static final int										TYPE_LIST_ACTUAL		= AttendanceUtility.TYPE_LIST_ACTUAL;
	
	/**
	 * 勤怠一覧区分(予定)。<br>
	 */
	public static final int										TYPE_LIST_SCHEDULE		= AttendanceUtility.TYPE_LIST_SCHEDULE;
	
	/**
	 * 勤怠一覧区分(承認)。<br>
	 */
	public static final int										TYPE_LIST_APPROVAL		= AttendanceUtility.TYPE_LIST_APPROVAL;
	
	/**
	 * MosP処理情報。<br>
	 */
	protected MospParams										mospParams;
	
	/**
	 * 勤怠一覧情報リスト。<br>
	 * 各メソッドは、ここに値を設定していく。<br>
	 */
	protected List<AttendanceListDto>							attendList;
	
	/**
	 * 退職日。<br>
	 * 退職していない場合はnull。<br>
	 */
	protected Date												retireDate;
	
	/**
	 * 休職情報群。<br>
	 */
	protected Collection<SuspensionDtoInterface>				suspensions;
	
	/**
	 * カレンダ日情報群(キー：日)。<br>
	 * 設定適用が取得できない日(人事情報の最初の有効日より前の日等)の情報は、設定されていない。<br>
	 */
	protected Map<Date, ScheduleDateDtoInterface>				scheduleDates;
	
	/**
	 * 勤務形態エンティティ群(キー：勤務形態コード)。<br>
	 * 値は勤務形態エンティティ履歴(有効日昇順)。<br>
	 */
	protected Map<String, List<WorkTypeEntityInterface>>		workTypeEntities;
	
	/**
	 * 勤怠(日々)情報群(キー：勤務日)。<br>
	 */
	protected Map<Date, AttendanceDtoInterface>					attendances;
	
	/**
	 * 残業申請情報群。<br>
	 */
	protected Map<Date, List<OvertimeRequestDtoInterface>>		overtimeRequests;
	
	/**
	 * 休暇申請情報群。<br>
	 */
	protected Map<Date, List<HolidayRequestDtoInterface>>		holidayRequests;
	
	/**
	 * 休出申請情報群。<br>
	 */
	protected Map<Date, WorkOnHolidayRequestDtoInterface>		workOnHolidayRequests;
	
	/**
	 * 振り替えられたカレンダ日情報群(キー：勤務日)。<br>
	 * 承認済の休日出勤申請情報(振替申請(勤務形態変更なし)のみ)の振替元のカレンダ日情報を保持する。<br>
	 */
	protected Map<Date, ScheduleDateDtoInterface>				substitutedSchedules;
	
	/**
	 * 振替休日情報群(キー：振替日)。<br>
	 * 半日振替をした場合に、同一振替日で2つ振替休日情報が作成されることがある。<br>
	 */
	protected Map<Date, List<SubstituteDtoInterface>>			substitutes;
	
	/**
	 * 代休申請情報群。<br>
	 */
	protected Map<Date, List<SubHolidayRequestDtoInterface>>	subHolidayRequests;
	
	/**
	 * 代休情報群(キー：代休種別)。<br>
	 */
	protected Map<Integer, List<SubHolidayDtoInterface>>		subHolidays;
	
	/**
	 * 勤務形態変更申請情報群。<br>
	 */
	protected Map<Date, WorkTypeChangeRequestDtoInterface>		workTypeChangeRequests;
	
	/**
	 * 時差出勤申請情報群。<br>
	 */
	protected Map<Date, DifferenceRequestDtoInterface>			differenceRequests;
	
	/**
	 * ワークフロー情報群。<br>
	 */
	protected Map<Long, WorkflowDtoInterface>					workflows;
	
	/**
	 * 勤怠修正情報群。<br>
	 */
	protected Map<Date, List<AttendanceCorrectionDtoInterface>>	corrections;
	
	/**
	 * 休暇種別情報群。<br>
	 */
	protected Set<HolidayDtoInterface>							holidays;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	public AttendListEntity() {
		// 処理無し
	}
	
	@Override
	public void fillInAttendList(int listType) throws MospException {
		// 対象となる承認状況群を取得
		Set<String> statuses = AttendanceUtility.getStatusesForListType(listType);
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendList) {
			// 勤怠一覧区分を設定
			dto.setListType(listType);
			// 予定される勤務形態の内容を勤怠一覧情報に設定
			addScheduleAndRequests(dto, statuses);
			// 勤怠情報を勤怠一覧情報に設定
			addAttendance(dto, statuses);
			// ワークフロー情報等を勤怠一覧情報に設定
			addWorkflow(dto);
		}
		// 休暇等を集計
		totalHolidays(statuses);
		// 勤怠一覧情報リストを集計
		totalAttendanceList();
		// 勤怠一覧情報に表示用文字列を設定
		setStringFields();
	}
	
	/**
	 * 予定される勤務形態の内容を勤怠一覧情報に設定する。<br>
	 * カレンダ日情報及び各種申請情報を基に予定される勤務形態を特定する。<br>
	 * <br>
	 * 但し、勤怠一覧区分が予定(予定一覧表示に申請情報を適用しない)である場合は、
	 * 各種申請情報は考慮しない。<br>
	 * <br>
	 * 勤怠一覧区分が実績か承認の場合は、休日及び全休の情報のみを設定する。<br>
	 * 勤怠一覧区分が予定の場合のみ、予定の勤務時間及び休憩時間を設定する。<br>
	 * <br>
	 * @param dto      勤怠一覧情報
	 * @param statuses 対象となる承認状況群
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected void addScheduleAndRequests(AttendanceListDto dto, Set<String> statuses) throws MospException {
		// 申請エンティティを取得
		RequestEntityInterface requestEntity = getRequestEntity(dto);
		// 勤務日と予定される勤務形態コードを取得
		Date workDate = dto.getWorkDate();
		String workTypeCode = requestEntity.getWorkType(false, statuses);
		// 全休である場合
		if (requestEntity.isAllHoliday(statuses)) {
			// 勤務形態(空白)を設定
			dto.setWorkTypeCode(workTypeCode);
			// 勤務形態略称(休暇略称)を設定
			dto.setWorkTypeAbbr(getWorkTypeAbbr(dto, statuses));
			// 処理終了
			return;
		}
		// 勤務日に退職か休職をしているか予定される勤務形態コードが設定されていない場合
		if (isRetireOrSusoension(workDate) || MospUtility.isEmpty(workTypeCode)) {
			// 処理不要
			return;
		}
		// 勤務形態コードが所定休日又は法定休日である場合
		if (TimeUtility.isHoliday(workTypeCode)) {
			// 勤務形態を設定
			dto.setWorkTypeCode(workTypeCode);
			// 勤務形態略称(法休日か所休日)を設定
			dto.setWorkTypeAbbr(getWorkTypeAbbr(dto, statuses));
			// 処理終了
			return;
		}
		// 勤怠一覧区分が実績か承認の場合
		if (isTheListType(dto, TYPE_LIST_ACTUAL, TYPE_LIST_APPROVAL)) {
			// 処理終了
			return;
		}
		// 勤務形態を設定
		dto.setWorkTypeCode(workTypeCode);
		// 勤務形態略称を設定
		dto.setWorkTypeAbbr(getWorkTypeAbbr(dto, statuses));
		// 勤務形態エンティティを取得
		WorkTypeEntityInterface workTypeEntity = getWorkTypeEntity(workTypeCode, workDate);
		// 始業時間を設定
		dto.setStartTime(workTypeEntity.getStartTime(requestEntity, statuses));
		// 終業時間を設定
		dto.setEndTime(workTypeEntity.getEndTime(requestEntity, statuses));
		// 勤怠一覧区分が予定である場合
		if (isTheListType(dto, TYPE_LIST_SCHEDULE)) {
			// 始終業時間を取得
			Date startTime = dto.getStartTime();
			Date endTime = dto.getEndTime();
			// 始終業時間を取得
			TimeDuration duration = TimeUtility.getDuration(startTime, endTime);
			// 規定休憩時間間隔群(キー：開始時刻)(キー順)を取得
			Map<Integer, TimeDuration> rests = workTypeEntity.getRestTimes(duration.getStartTime(),
					duration.getEndTime(), requestEntity, statuses);
			// 規定休憩時間を取得
			int restMinutes = TimeUtility.getMinutes(rests);
			// 勤務時間を設定(間に取った時間単位休暇の分は引かれない)
			dto.setWorkTime(duration.getMinutes() - restMinutes);
			// 休憩時間を設定
			dto.setRestTime(restMinutes);
		}
	}
	
	/**
	 * 勤怠情報を勤怠一覧情報に設定する。<br>
	 * @param dto      勤怠一覧情報
	 * @param statuses 対象となる承認状況群を取得
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected void addAttendance(AttendanceListDto dto, Set<String> statuses) throws MospException {
		// 勤務日を取得
		Date workDate = dto.getWorkDate();
		// 勤怠情報を取得
		AttendanceDtoInterface attendance = getAttendance(workDate);
		// 勤怠情報が存在しない場合
		if (MospUtility.isEmpty(attendance)) {
			// 処理終了
			return;
		}
		// ワークフロー情報を取得
		WorkflowDtoInterface workflow = workflows.get(attendance.getWorkflow());
		// 勤怠一覧情報作成時に勤怠情報を考慮しない場合
		if (AttendanceUtility.isAttendanceConsideredForAttendList(dto, workflow) == false) {
			// 処理終了
			return;
		}
		// 勤怠一覧情報に勤務形態の内容を設定
		addAttendance(dto, attendance, statuses);
		// 修正情報
		dto.setCorrectionInfo(AttendanceUtility.getCorrectionMark(mospParams, corrections.get(workDate)));
	}
	
	/**
	 * ワークフロー情報等を勤怠一覧情報に設定する。<br>
	 * @param dto 勤怠一覧情報
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	protected void addWorkflow(AttendanceListDto dto) throws MospException {
		// 初期値を設定
		dto.setWorkflow(0L);
		dto.setApplicationInfo(MospConst.STR_EMPTY);
		dto.setNeedStatusLink(false);
		dto.setStartTimeStyle(MospConst.STR_EMPTY);
		dto.setEndTimeStyle(MospConst.STR_EMPTY);
		// 申請エンティティを取得
		RequestEntityInterface requestEntity = getRequestEntity(dto);
		// 対象となる承認状況群(有効な承認状況群(下書と取下以外))を取得
		Set<String> statuses = WorkflowUtility.getEffectiveStatuses();
		// 勤務日と予定される勤務形態コードを取得
		Date workDate = dto.getWorkDate();
		String workTypeCode = requestEntity.getWorkType(false, statuses);
		// ワークフロー情報を準備
		WorkflowDtoInterface workflow = null;
		// 勤怠申請が存在する場合
		if (requestEntity.hasAttendance()) {
			// 勤怠申請のワークフロー情報を取得
			workflow = workflows.get(requestEntity.getAttendanceDto().getWorkflow());
			// 勤怠一覧区分が承認で下書である場合(承認は下書以外)
			if (isTheListType(dto, TYPE_LIST_APPROVAL) && WorkflowUtility.isDraft(workflow)) {
				// 処理終了(状態は空白)
				return;
			}
			// 状態リンク要否を設定(下書でなければ要)
			dto.setNeedStatusLink(WorkflowUtility.isDraft(workflow) == false);
		}
		// 全休(半休+半休も含む)であり予定される勤務形態コードが休日でない場合(連続休暇中の休日を考慮)
		if (requestEntity.isAllHoliday(statuses) && TimeUtility.isHoliday(workTypeCode) == false) {
			// 休暇のワークフロー情報を取得
			workflow = requestEntity.getHolidayWorkflow(statuses);
		}
		// 勤怠申請が存在するか全休(半休+半休も含む)であった場合(ワークフロー情報を取得している場合)
		if (MospUtility.isEmpty(workflow) == false) {
			// ワークフロー番号を設定
			dto.setWorkflow(workflow.getWorkflow());
			// 状態を設定
			dto.setApplicationInfo(WorkflowUtility.getWorkflowStatus(mospParams, workflow));
			// 処理終了
			return;
		}
		// 勤怠申請か休暇申請か振替休日情報がない場合(ワークフロー情報を取得していない場合)
		// 勤務日に退職か休職をしているか予定される勤務形態コードが設定されていない場合
		if (isRetireOrSusoension(workDate) || MospUtility.isEmpty(workTypeCode)) {
			// 状態を設定(空白)
			dto.setApplicationInfo(MospConst.STR_EMPTY);
			// 処理終了
			return;
		}
		// 勤務形態コードが所定休日又は法定休日である場合
		if (TimeUtility.isHoliday(workTypeCode)) {
			// 状態を設定(承認済)
			dto.setApplicationInfo(PfNameUtility.completed(mospParams));
			// 処理終了
			return;
		}
		// 勤怠一覧区分が承認である場合
		if (isTheListType(dto, TYPE_LIST_APPROVAL)) {
			// 処理終了(予定に関する状態は設定不要)
			return;
		}
		// 状態を設定(予定)
		dto.setApplicationInfo(PfNameUtility.schedule(mospParams));
		// 始終業時刻の色を設定(灰色)
		dto.setStartTimeStyle(PlatformConst.STYLE_GRAY);
		dto.setEndTimeStyle(PlatformConst.STYLE_GRAY);
	}
	
	/**
	 * 勤怠一覧情報リストを集計する。<br>
	 * 集計結果は、リストの最終レコードに設定される。<br>
	 */
	@Override
	public void totalAttendanceList() {
		// 集計値準備
		// 勤務時間(分)
		int workTimeTotal = 0;
		// 休憩時間(分)
		int restTimeTotal = 0;
		// 私用外出時間(分)
		int privateTimeTotal = 0;
		// 公用外出時間(分)
		int publicTimeTotal = 0;
		// 分単位休暇A時間(分)
		int minutelyHolidayATimeTotal = 0;
		// 分単位休暇B時間(分)
		int minutelyHolidayBTimeTotal = 0;
		// 遅刻時間(分)
		int lateTimeTotal = 0;
		// 早退時間(分)
		int leaveEarlyTimeTotal = 0;
		// 遅刻早退時間(分)
		int lateLeaveEarlyTimeTotal = 0;
		// 残業時間(分)
		int overtimeTotal = 0;
		// 内残時間(分)
		int overtimeInTotal = 0;
		// 外残時間(分)
		int overtimeOutTotal = 0;
		// 休出時間(分)
		int holidayWorkTimeTotal = 0;
		// 無休時短時間(分)
		int shortUnpaidTotal = 0;
		// 深夜時間(分)
		int lateNightTimeTotal = 0;
		// 出勤回数
		int workDays = 0;
		// 遅刻回数
		int lateDays = 0;
		// 早退回数
		int leaveEarlyDays = 0;
		// 残業回数
		int overtimeDays = 0;
		// 深夜回数
		int lateNightDays = 0;
		// 勤怠一覧情報毎に集計
		for (AttendanceListDto dto : attendList) {
			// 勤務時間
			workTimeTotal += MospUtility.getInt(dto.getWorkTime());
			// 休憩時間
			restTimeTotal += MospUtility.getInt(dto.getRestTime());
			// 私用外出時間
			privateTimeTotal += MospUtility.getInt(dto.getPrivateTime());
			// 公用外出時間
			publicTimeTotal += MospUtility.getInt(dto.getPublicTime());
			// 分単位休暇A時間
			minutelyHolidayATimeTotal += MospUtility.getInt(dto.getMinutelyHolidayATime());
			// 分単位休暇B時間
			minutelyHolidayBTimeTotal += MospUtility.getInt(dto.getMinutelyHolidayBTime());
			// 遅刻時間
			lateTimeTotal += MospUtility.getInt(dto.getLateTime());
			// 早退時間
			leaveEarlyTimeTotal += MospUtility.getInt(dto.getLeaveEarlyTime());
			// 遅刻早退時間
			lateLeaveEarlyTimeTotal += MospUtility.getInt(dto.getLateLeaveEarlyTime());
			// 残業時間
			overtimeTotal += MospUtility.getInt(dto.getOvertime());
			// 内残時間
			overtimeInTotal += MospUtility.getInt(dto.getOvertimeIn());
			// 外残時間
			overtimeOutTotal += MospUtility.getInt(dto.getOvertimeOut());
			// 休出時間
			holidayWorkTimeTotal += MospUtility.getInt(dto.getHolidayWorkTime());
			// 無休時短時間
			shortUnpaidTotal += MospUtility.getInt(dto.getShortUnpaid());
			// 深夜時間
			lateNightTimeTotal += MospUtility.getInt(dto.getLateNightTime());
			// 出勤回数
			workDays += dto.getGoingWork();
			// 遅刻回数
			lateDays += count(dto.getLateTime());
			// 早退回数
			leaveEarlyDays += count(dto.getLeaveEarlyTime());
			// 残業回数
			overtimeDays += count(dto.getOvertime());
			// 深夜回数
			lateNightDays += count(dto.getLateNightTime());
		}
		// 最終レコードを取得
		AttendanceListDto dto = MospUtility.getLastValue(attendList);
		// 集計値を設定
		dto.setWorkTimeTotal(workTimeTotal);
		dto.setRestTimeTotal(restTimeTotal);
		dto.setPrivateTimeTotal(privateTimeTotal);
		dto.setPublicTimeTotal(publicTimeTotal);
		dto.setMinutelyHolidayATimeTotal(minutelyHolidayATimeTotal);
		dto.setMinutelyHolidayBTimeTotal(minutelyHolidayBTimeTotal);
		dto.setLateTimeTotal(lateTimeTotal);
		dto.setLeaveEarlyTimeTotal(leaveEarlyTimeTotal);
		dto.setLateLeaveEarlyTimeTotal(lateLeaveEarlyTimeTotal);
		dto.setOvertimeTotal(overtimeTotal);
		dto.setOvertimeInTotal(overtimeInTotal);
		dto.setOvertimeOutTotal(overtimeOutTotal);
		dto.setHolidayWorkTimeTotal(holidayWorkTimeTotal);
		dto.setShortUnpaidTotal(shortUnpaidTotal);
		dto.setLateNightTimeTotal(lateNightTimeTotal);
		dto.setWorkDays(workDays);
		dto.setLateDays(lateDays);
		dto.setLeaveEarlyDays(leaveEarlyDays);
		dto.setOvertimeDays(overtimeDays);
		dto.setLateNightDays(lateNightDays);
	}
	
	/**
	 * 休暇等を集計する。<br>
	 * 集計結果は、リストの最終レコードに設定される。<br>
	 * 休暇申請情報、代休申請情報、振替休日情報と休出、所定法定休日を集計する。<br>
	 * @param statuses 対象となる承認状況群
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	protected void totalHolidays(Set<String> statuses) throws MospException {
		// 有給休暇回数
		float paidHolidays = 0F;
		// 有給時間
		float paidHolidayTime = 0F;
		// 特別休暇回数
		float specialHolidays = 0F;
		// 特別休暇時間数
		float specialHolidayTimes = 0F;
		// その他休暇回数
		float otherHolidays = 0F;
		// その他休暇時間数
		float otherHolidayTimes = 0F;
		// 欠勤回数
		float absenceDays = 0F;
		// 欠勤時間数
		float absenceTimes = 0F;
		// 代休回数
		float subHolidayDays = 0F;
		// 振替休日回数
		float substituteDays = 0F;
		// 休出回数
		int holidayWorkDays = 0;
		// 所定休日回数
		int prescribedHolidays = 0;
		// 法定休日回数
		int legalHolidays = 0;
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendList) {
			// 申請エンティティを取得
			RequestEntityInterface requestEntity = getRequestEntity(dto);
			// 勤務形態コードを取得
			String workTypeCode = dto.getWorkTypeCode();
			// 休暇申請情報毎に処理
			for (HolidayRequestDtoInterface holiday : requestEntity.getHolidayRequestList(statuses)) {
				// 連続休暇であり勤務形態コードが設定されている(連続休暇中の休日等)場合
				if (TimeRequestUtility.isConsecutiveHolidays(holiday) && MospUtility.isEmpty(workTypeCode) == false) {
					// 処理不要
					break;
				}
				// 使用日数を取得
				double useDay = holiday.getUseDay();
				// 連続休暇である場合
				if (TimeRequestUtility.isConsecutiveHolidays(holiday)) {
					// 使用日数を再設定(連続休暇中は全日休暇となるため)
					useDay = 1D;
				}
				// 休暇種別毎に処理
				switch (holiday.getHolidayType1()) {
					// 有給休暇の場合
					case TimeConst.CODE_HOLIDAYTYPE_HOLIDAY:
						// 有給休暇回数及び時間を設定
						paidHolidays += useDay;
						paidHolidayTime += holiday.getUseHour();
						break;
					// 特別休暇の場合
					case TimeConst.CODE_HOLIDAYTYPE_SPECIAL:
						// 特別休暇回数及び時間を設定
						specialHolidays += useDay;
						specialHolidayTimes += holiday.getUseHour();
						break;
					// その他休暇の場合
					case TimeConst.CODE_HOLIDAYTYPE_OTHER:
						// その他休暇回数及び時間を設定
						otherHolidays += useDay;
						otherHolidayTimes += holiday.getUseHour();
						break;
					// 欠勤の場合
					case TimeConst.CODE_HOLIDAYTYPE_ABSENCE:
						// 欠勤回数及び時間を設定
						absenceDays += useDay;
						absenceTimes += holiday.getUseHour();
						break;
					// それ以外の場合
					default:
						// 処理無し
				}
			}
			// 代休申請情報毎に処理
			for (SubHolidayRequestDtoInterface subHoliday : requestEntity.getSubHolidayRequestList(statuses)) {
				// 代休回数を設定
				subHolidayDays += TimeUtility.getHolidayTimes(subHoliday.getHolidayRange());
			}
			// 勤務形態コードが法定休日出勤か所定休日出勤である場合
			if (TimeUtility.isWorkOnLegalOrPrescribedHoliday(workTypeCode)) {
				// 休出回数を設定
				holidayWorkDays++;
			}
			// 振出・休出申請がある場合
			if (requestEntity.hasWorkOnHoliday(statuses)) {
				// 次の勤怠一覧情報へ(振替休日のカウントは不要：振替の振替)
				continue;
			}
			// 振替休日情報リストを取得
			List<SubstituteDtoInterface> substitutes = requestEntity.getSubstituteList(statuses);
			// 振替休日情報毎に処理
			for (SubstituteDtoInterface substitute : substitutes) {
				// 振替休日回数を設定
				substituteDays += TimeUtility.getHolidayTimes(substitute.getHolidayRange());
			}
			// 振替休日情報がある場合
			if (MospUtility.isEmpty(substitutes) == false) {
				// 次の勤怠一覧情報へ(休日のカウントは不要)
				continue;
			}
			// 勤務形態コードが所定休日である場合
			if (TimeUtility.isPrescribedHoliday(workTypeCode)) {
				// 所定休日回数を設定
				prescribedHolidays++;
			}
			// 勤務形態コードが法定休日である場合
			if (TimeUtility.isLegalHoliday(workTypeCode)) {
				// 法定休日回数を設定
				legalHolidays++;
			}
		}
		// 最終レコードを取得
		AttendanceListDto dto = MospUtility.getLastValue(attendList);
		// 集計値を設定
		dto.setPaidHolidays(paidHolidays);
		dto.setPaidHolidayTime(paidHolidayTime);
		dto.setSpecialHolidays(specialHolidays);
		dto.setSpecialHolidayHours(specialHolidayTimes);
		dto.setOtherHolidays(otherHolidays);
		dto.setOtherHolidayHours(otherHolidayTimes);
		dto.setAbsenceDays(absenceDays);
		dto.setAbsenceHours(absenceTimes);
		dto.setSubHolidays(subHolidayDays);
		dto.setSubstituteHolidays(substituteDays);
		dto.setHolidayWorkDays(holidayWorkDays);
		dto.setPrescribedHolidays(prescribedHolidays);
		dto.setLegalHolidays(legalHolidays);
		dto.setHolidays(prescribedHolidays + legalHolidays);
		// 代休発生日数を設定
		dto.setBirthPrescribedSubHoliday(getSubHolidayDays(TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE));
		dto.setBirthLegalSubHoliday(getSubHolidayDays(TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE));
		dto.setBirthMidnightSubHolidaydays(getSubHolidayDays(TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE));
	}
	
	/**
	 * 勤怠一覧情報に表示用文字列(日付、時刻、時間等)を設定する。<br>
	 * @throws MospException 日付操作に失敗した場合
	 */
	@Override
	public void setStringFields() throws MospException {
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendList) {
			// 勤怠一覧情報に表示用文字列を設定
			setStringFields(dto);
		}
	}
	
	/**
	 * 勤怠一覧情報に表示用文字列(日付、時刻、時間等)を設定する。<br>
	 * @param dto 設定対象勤怠一覧情報
	 * @throws MospException 日付操作に失敗した場合
	 */
	protected void setStringFields(AttendanceListDto dto) throws MospException {
		// 基準日付取得(勤務日)
		Date standardDate = dto.getWorkDate();
		// 対象日取得(勤務日)
		Date targetDate = dto.getWorkDate();
		// 勤務日
		dto.setWorkDateString(DateUtility.getStringMonthAndDate(targetDate));
		// 勤務曜日
		dto.setWorkDayOfWeek(DateUtility.getStringDayOfWeek(targetDate));
		// 曜日配色設定
		dto.setWorkDayOfWeekStyle(HolidayUtility.getWorkDayOfWeekStyle(targetDate, mospParams));
		// 始業時間
		dto.setStartTimeString(getHourColonMinute(dto.getStartTime(), standardDate));
		// 終業時間
		dto.setEndTimeString(getHourColonMinute(dto.getEndTime(), standardDate));
		// 始業打刻
		dto.setStartRecordTimeString(getHourColonMinute(dto.getStartRecordTime(), standardDate));
		// 終業打刻
		dto.setEndRecordTimeString(getHourColonMinute(dto.getEndRecordTime(), standardDate));
		// 勤務時間
		dto.setWorkTimeString(getStringHours(dto.getWorkTime(), true));
		// 休憩時間
		dto.setRestTimeString(getStringHours(dto.getRestTime(), true));
		// 私用外出時間
		dto.setPrivateTimeString(getStringHours(dto.getPrivateTime(), true));
		// 公用外出時間
		dto.setPublicTimeString(getStringHours(dto.getPublicTime(), true));
		// 遅刻時間
		dto.setLateTimeString(getStringHours(dto.getLateTime(), true));
		// 早退時間
		dto.setLeaveEarlyTimeString(getStringHours(dto.getLeaveEarlyTime(), true));
		// 遅刻早退時間
		dto.setLateLeaveEarlyTimeString(getStringHours(dto.getLateLeaveEarlyTime(), true));
		// 残業時間
		dto.setOvertimeString(getStringHours(dto.getOvertime(), true));
		// 内残時間
		dto.setOvertimeInString(getStringHours(dto.getOvertimeIn(), true));
		// 外残時間
		dto.setOvertimeOutString(getStringHours(dto.getOvertimeOut(), true));
		// 休出時間
		dto.setHolidayWorkTimeString(getStringHours(dto.getHolidayWorkTime(), true));
		// 深夜時間
		dto.setLateNightTimeString(getStringHours(dto.getLateNightTime(), true));
		// 無休時短時間
		dto.setShortUnpaidString(getStringHours(dto.getShortUnpaid(), true));
		// 勤務時間合計
		dto.setWorkTimeTotalString(getStringHours(dto.getWorkTimeTotal(), false));
		// 休憩時間合計
		dto.setRestTimeTotalString(getStringHours(dto.getRestTimeTotal(), false));
		// 私用外出時間合計
		dto.setPrivateTimeTotalString(getStringHours(dto.getPrivateTimeTotal(), false));
		// 公用外出時間合計
		dto.setPublicTimeTotalString(getStringHours(dto.getPublicTimeTotal(), false));
		// 遅刻時間
		dto.setLateTimeTotalString(getStringHours(dto.getLateTimeTotal(), false));
		// 早退時間
		dto.setLeaveEarlyTimeTotalString(getStringHours(dto.getLeaveEarlyTimeTotal(), false));
		// 遅刻早退時間
		dto.setLateLeaveEarlyTimeTotalString(getStringHours(dto.getLateLeaveEarlyTimeTotal(), false));
		// 残業時間
		dto.setOvertimeTotalString(getStringHours(dto.getOvertimeTotal(), false));
		// 内残時間
		dto.setOvertimeInTotalString(getStringHours(dto.getOvertimeInTotal(), false));
		// 外残時間
		dto.setOvertimeOutTotalString(getStringHours(dto.getOvertimeOutTotal(), false));
		// 休出時間
		dto.setHolidayWorkTimeTotalString(getStringHours(dto.getHolidayWorkTimeTotal(), false));
		// 深夜時間
		dto.setLateNightTimeTotalString(getStringHours(dto.getLateNightTimeTotal(), false));
		// 無休時短時間
		dto.setShortUnpaidTotalString(getStringHours(dto.getShortUnpaidTotal(), false));
		// 出勤回数
		dto.setWorkDaysString(getIntegerTimes(dto.getWorkDays()));
		// 遅刻回数
		dto.setLateDaysString(getIntegerTimes(dto.getLateDays()));
		// 早退回数
		dto.setLeaveEarlyDaysString(getIntegerTimes(dto.getLeaveEarlyDays()));
		// 残業回数
		dto.setOvertimeDaysString(getIntegerTimes(dto.getOvertimeDays()));
		// 休出回数
		dto.setHolidayWorkDaysString(getIntegerTimes(dto.getHolidayWorkDays()));
		// 代休発生回数設定
		dto.setBirthPrescribedSubHolidayString(getFloatTimes(dto.getBirthPrescribedSubHoliday(), true));
		dto.setBirthLegalSubHolidayString(getFloatTimes(dto.getBirthLegalSubHoliday(), true));
		dto.setBirthMidnightSubHolidayString(getFloatTimes(dto.getBirthMidnightSubHoliday(), true));
		// 深夜回数
		dto.setLateNightDaysString(getIntegerTimes(dto.getLateNightDays()));
		// 所定休日回数
		dto.setPrescribedHolidaysString(getIntegerTimes(dto.getPrescribedHolidays()));
		// 法定休日回数
		dto.setLegalHolidaysString(getIntegerTimes(dto.getLegalHolidays()));
		// 休日回数
		dto.setHolidayString(getIntegerTimes(dto.getHolidays()));
		// 振替休日回数
		dto.setSubstituteHolidaysString(getFloatTimes(dto.getSubstituteHolidays(), false));
		// 有給休暇回数
		dto.setPaidHolidaysString(getFloatTimes(dto.getPaidHolidays(), false));
		// 有給時間
		dto.setPaidHolidayTimeString(getFloatTimes(dto.getPaidHolidayTime(), false));
		// 特別休暇回数
		dto.setSpecialHolidaysString(getDaysAndHours(dto.getSpecialHolidays(), dto.getSpecialHolidayHours()));
		// その他休暇回数
		dto.setOtherHolidaysString(getDaysAndHours(dto.getOtherHolidays(), dto.getOtherHolidayHours()));
		// 代休回数
		dto.setSubHolidaysString(getFloatTimes(dto.getSubHolidays(), false));
		// 欠勤回数
		dto.setAbsenceDaysString(getDaysAndHours(dto.getAbsenceDays(), dto.getAbsenceHours()));
		// 分単位休暇A時間
		dto.setMinutelyHolidayATimeString(getStringHours(dto.getMinutelyHolidayATimeTotal(), false));
		// 分単位休暇B時間
		dto.setMinutelyHolidayBTimeString(getStringHours(dto.getMinutelyHolidayBTimeTotal(), false));
		// 社員コード項目タイトル設定
		dto.setEmployeeCodeTitle(PfNameUtility.employeeCode(mospParams));
		// 帳票残業項目タイトル設定
		dto.setOvertimeTitle(TimeNamingUtility.overtimeOutAbbr(mospParams));
		// 時間休項目有無設定
		dto.setHourlyPaidHolidayValid(true);
		// 特別休暇項目タイトル設定
		dto.setSpecialHolidaysTitle(TimeNamingUtility.specialHolidayAbbr(mospParams));
	}
	
	/**
	 * 勤怠一覧情報に勤怠情報の内容を設定する。<br>
	 * @param dto        設定対象勤怠一覧情報
	 * @param attendance 勤怠情報
	 * @param statuses   対象となる承認状況群を取得
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addAttendance(AttendanceListDto dto, AttendanceDtoInterface attendance, Set<String> statuses)
			throws MospException {
		// 出勤回数設定
		dto.setGoingWork(TimeBean.TIMES_WORK_DEFAULT);
		// 勤務形態
		dto.setWorkTypeCode(attendance.getWorkTypeCode());
		// 勤務形態略称を設定
		dto.setWorkTypeAbbr(getWorkTypeAbbr(dto, statuses));
		// 始業時間
		dto.setStartTime(attendance.getActualStartTime());
		// 終業時間
		dto.setEndTime(attendance.getActualEndTime());
		// 勤務時間
		dto.setWorkTime(attendance.getWorkTime());
		// 休憩時間
		dto.setRestTime(attendance.getRestTime());
		// 私用外出時間
		dto.setPrivateTime(attendance.getPrivateTime());
		// 公用外出時間
		dto.setPublicTime(attendance.getPublicTime());
		// 分単位休暇A時間
		dto.setMinutelyHolidayATime(attendance.getMinutelyHolidayATime());
		// 分単位休暇A全休
		dto.setMinutelyHolidayA(attendance.getMinutelyHolidayA());
		// 分単位休暇B時間
		dto.setMinutelyHolidayBTime(attendance.getMinutelyHolidayBTime());
		// 分単位休暇B全休
		dto.setMinutelyHolidayB(attendance.getMinutelyHolidayB());
		// 遅刻時間
		dto.setLateTime(attendance.getLateTime());
		// 早退時間
		dto.setLeaveEarlyTime(attendance.getLeaveEarlyTime());
		// 遅刻早退時間
		dto.setLateLeaveEarlyTime(attendance.getLateTime() + attendance.getLeaveEarlyTime());
		// 残業時間
		dto.setOvertime(attendance.getOvertime());
		// 内残時間
		dto.setOvertimeIn(attendance.getOvertimeIn());
		// 外残時間
		dto.setOvertimeOut(attendance.getOvertimeOut());
		// 休出時間
		dto.setHolidayWorkTime(attendance.getLegalWorkTime());
		// 深夜時間
		dto.setLateNightTime(attendance.getLateNightTime());
		// 無休時短時間
		dto.setShortUnpaid(attendance.getShortUnpaid());
	}
	
	/**
	 * 勤務形態エンティティ(勤務日以前で最新)を取得する。<br>
	 * 時差出勤申請がある場合は、時差出勤申請の内容を考慮する。<br>
	 * @param workTypeCode 対象勤務形態コード
	 * @param workDate     勤務日
	 * @return 勤務形態エンティティ
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected WorkTypeEntityInterface getWorkTypeEntity(String workTypeCode, Date workDate) throws MospException {
		// 勤務形態エンティティ(勤務日以前で最新)を取得
		WorkTypeEntityInterface workType = PlatformUtility.getLatestDto(workTypeEntities.get(workTypeCode), workDate);
		// 時差出勤申請情報を取得
		DifferenceRequestDtoInterface differenceRequest = differenceRequests.get(workDate);
		// 時差出勤申請情報を取得できた場合
		if (MospUtility.isEmpty(differenceRequest) == false) {
			// 時差出勤申請から勤務形態コードを取得
			String differenceWorkTypeCode = differenceRequest.getWorkTypeCode();
			// 時差出勤申請の勤務形態で勤務形態エンティティ(勤務日以前で最新)を再取得
			workType = PlatformUtility.getLatestDto(workTypeEntities.get(differenceWorkTypeCode), workDate);
			// 時差出勤申請を取得し勤務形態に設定
			workType = DifferenceUtility.makeDifferenceWorkType(mospParams, differenceRequest, workType);
		}
		// 勤務形態エンティティが取得できなかった場合
		if (MospUtility.isEmpty(workType)) {
			// 空の勤務形態エンティティを取得
			workType = WorkTypeUtility.emptyWorkType(mospParams);
		}
		// 勤務形態エンティティを取得
		return workType;
	}
	
	/**
	 * 代休発生日数を取得する。<br>
	 * @param subHolidayType 代休種別
	 * @return 代休発生日数
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	protected float getSubHolidayDays(int subHolidayType) throws MospException {
		// 代休発生日数を準備
		float subHolidayDays = 0F;
		// マップからキーで値(List)を取得
		List<SubHolidayDtoInterface> dtos = MospUtility.getListValue(subHolidays, subHolidayType);
		// 代休情報毎に処理
		for (SubHolidayDtoInterface dto : dtos) {
			// 勤務日の勤怠申請が承認済でない場合
			if (isAttendCompleted(dto.getWorkDate()) == false) {
				// 次の代休情報へ
				continue;
			}
			// 代休発生日数を加算
			subHolidayDays += dto.getSubHolidayDays();
		}
		// 代休発生日数を取得
		return subHolidayDays;
	}
	
	/**
	 * 勤務日の勤怠申請が承認済であるかを確認する。<br>
	 * 勤務日に勤怠申請が無い場合は、承認済でないと判断する。<br>
	 * @param workDate 勤務日
	 * @return 確認結果(true：勤務日の勤怠申請が承認済である、false：そうでない)
	 */
	protected boolean isAttendCompleted(Date workDate) {
		// 勤務日の勤怠情報を取得
		AttendanceDtoInterface dto = getAttendance(workDate);
		// 勤務日に勤怠申請が無い場合
		if (MospUtility.isEmpty(dto)) {
			// 承認済でないと判断
			return false;
		}
		// 勤務日の勤怠申請が承認済であるかを確認
		return WorkflowUtility.isCompleted(workflows.get(dto.getWorkflow()));
	}
	
	/**
	 * 対象日のカレンダ日情報の勤務形態コードを取得する。<br>
	 * 設定適用が取得できない日(人事情報の最初の有効日より前の日等)の場合は、空文字を取得する。<br>
	 * @param workDate 勤務日
	 * @return カレンダ日情報の勤務形態コード
	 */
	protected String getScheduledWorkTypeCode(Date workDate) {
		// 対象日のカレンダ日情報を取得
		ScheduleDateDtoInterface scheduleDateDto = scheduleDates.get(workDate);
		// カレンダ日情報が存在しない場合
		if (MospUtility.isEmpty(scheduleDateDto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 勤務形態コードを取得
		return scheduleDateDto.getWorkTypeCode();
	}
	
	@Override
	public RequestEntityInterface getRequestEntity(AttendanceListDto dto) throws MospException {
		// 勤務日を取得
		Date targetDate = dto.getWorkDate();
		// 申請エンティティを準備
		RequestEntity requestEntity = MospUtility.createObject(RequestEntityInterface.class, mospParams);
		requestEntity.setTargetDate(targetDate);
		// 申請エンティティにカレンダ日情報の勤務形態コードを設定
		requestEntity.setScheduledWorkTypeCode(getScheduledWorkTypeCode(targetDate));
		// 勤怠一覧区分が予定であり予定に申請情報を適用しない場合
		if (isTheListType(dto, TYPE_LIST_SCHEDULE) && isScheduleApplyRequest() == false) {
			// 申請エンティティを取得(各種申請は不要)
			return requestEntity;
		}
		// 勤怠一覧区分が予定でない場合
		if (isTheListType(dto, TYPE_LIST_SCHEDULE) == false) {
			// 勤怠申請情報を設定
			requestEntity.setAttendanceDto(getAttendance(targetDate));
		}
		// 対象申請情報を設定
		requestEntity.setOverTimeRequestList(MospUtility.getListValue(overtimeRequests, targetDate));
		requestEntity.setHolidayRequestList(MospUtility.getListValue(holidayRequests, targetDate));
		requestEntity.setWorkOnHolidayRequestDto(workOnHolidayRequests.get(targetDate));
		requestEntity.setSubstituteList(MospUtility.getListValue(substitutes, targetDate));
		requestEntity.setSubHolidayRequestList(MospUtility.getListValue(subHolidayRequests, targetDate));
		requestEntity.setWorkTypeChangeRequestDto(workTypeChangeRequests.get(targetDate));
		requestEntity.setDifferenceRequestDto(differenceRequests.get(targetDate));
		requestEntity.setSubstitutedWorkTypeCode(getSubstitutedWorkTypeCode(targetDate, substitutedSchedules));
		// ワークフロー情報群を設定
		requestEntity.setWorkflowMap(workflows);
		return requestEntity;
	}
	
	/**
	 * 振り替えられた勤務形態コードを取得する。<br>
	 * @param workDate             勤務日
	 * @param substitutedSchedules 振り替えられたカレンダ日情報群(キー：勤務日)
	 * @return 振り替えられた勤務形態コード
	 */
	protected String getSubstitutedWorkTypeCode(Date workDate,
			Map<Date, ScheduleDateDtoInterface> substitutedSchedules) {
		// 振り替えられたカレンダ日情報を取得
		ScheduleDateDtoInterface dto = substitutedSchedules.get(workDate);
		// 振り替えられたカレンダ日情報を取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 振り替えられた勤務形態コードを取得
		return dto.getWorkTypeCode();
	}
	
	/**
	 * 勤務形態略称を取得する。<br>
	 * <br>
	 * 次の順番で確認し、勤務形態略称を確定する。<br>
	 * ・1.休暇申請(全日)であり連続休暇申請中の休暇でない場合：休暇略称<br>
	 * ・2.代休申請(全日)である場合                          ：所代休or法代休<br>
	 * ・3.振替出勤申請(半日振替以外)であり半休でない場合    ：勤務形態略称<br>
	 * ・4.振替出勤(午前)か振替出勤(午後)である場合          ：以下の処理により確定<br>
	 *   4-1.振替休日がある場合(半日振替の半日振替である場合)：勤務形態略称<br>
	 *   4-2.振替休日がない場合                              ：半振出<br>
	 * ・5.振替休日(全休)である場合(振替出勤をしていない場合)：所振休or法振休<br>
	 * ・6.振替休日(前半休+後半休)である場合                 ：振休<br>
	 * ・7.それ以外の場合                    ：以下の処理により確定<br>
	 *   7-1.休暇申請(半日)がある場合        ：半日(前後)名称に休暇略称(最初の文字)を設定
	 *   7-2.代休申請(半日)がある場合        ：半日(前後)名称に代を設定
	 *   7-3.振替休日(半日)がある場合        ：半日(前後)名称が設定されていない場合→半振休を取得
	 *                                         半日(前後)名称が設定されている場合  →半日(前後)名称に振△or△振を設定
	 *   7-4.半日の名称が設定されていない場合：勤務形態略称を取得
	 *   7-5.半日の名称が設定されている場合  ：半日(前)/半日(後)を取得(どちらかが空の場合は空文字を取得)
	 * <br>
	 * 勤務形態略称のパターンは、次の通り。<br>
	 * ・所休日<br>
	 * ・法休日<br>
	 * ・勤務形態略称(通常、勤務形態変更、振替出勤、振替出勤(勤務形態変更))<br>
	 * ・所振休<br>
	 * ・法振休<br>
	 * ・半振休<br>
	 * ・振休(前半休+後半休)<br>
	 * ・半振出<br>
	 * ・所休出<br>
	 * ・法休出<br>
	 * ・振△/○(半振休+半日休暇申請or半日代休申請)<br>
	 * ・○/振△(半日休暇申請or半日代休申請+半振休)<br>
	 * ・○/○(半日休暇申請or半日代休申請or勤務形態略称)<br>
	 * <br>
	 * @param dto      勤怠一覧情報
	 * @param statuses 対象となる承認状況群
	 * @return 勤務形態略称
	 * @throws MospException オブジェクトの生成に失敗した場合
	 */
	protected String getWorkTypeAbbr(AttendanceListDto dto, Set<String> statuses) throws MospException {
		// 勤務形態及び勤務日を取得
		String workTypeCode = MospUtility.getString(dto.getWorkTypeCode());
		Date workDate = dto.getWorkDate();
		// 申請エンティティを取得
		RequestEntityInterface requestEntity = getRequestEntity(dto);
		// 休暇申請情報群を取得
		List<HolidayRequestDtoInterface> holidayRequests = requestEntity.getHolidayRequestList(statuses);
		// 代休申請情報群を取得
		List<SubHolidayRequestDtoInterface> subHolidayRequests = requestEntity.getSubHolidayRequestList(statuses);
		// 振替休日情報群を取得
		List<SubstituteDtoInterface> substitutes = requestEntity.getSubstituteList(statuses);
		// 1.休暇申請(全日)であり連続休暇申請中の休暇でない場合
		if (TimeRequestUtility.hasHolidayRangeAll(holidayRequests)
				&& isHolidayInSequenceRequest(holidayRequests, workTypeCode) == false) {
			// 全休の休暇略称を取得
			return TimeRequestUtility.getAllHolidayAbbr(holidayRequests, holidays, mospParams);
		}
		// 2.代休(全日)である場合
		if (TimeRequestUtility.hasHolidayRangeAll(subHolidayRequests)) {
			// 全日の代休略称を取得
			return TimeRequestUtility.getAllSubHolidayAbbr(subHolidayRequests, mospParams);
		}
		// 3.振替出勤申請(半日振替以外)であり半休でない場合
		if (requestEntity.hasWorkOnHolidayNotHalf(statuses) && requestEntity.isHalfHoliday(statuses) == false) {
			// 勤務形態略称を取得
			return getWorkTypeEntity(workTypeCode, workDate).getWorkTypeAbbr();
		}
		// 4.振替出勤(午前)か振替出勤(午後)である場合
		if (requestEntity.hasAmWorkOnHoliday(statuses) || requestEntity.hasPmWorkOnHoliday(statuses)) {
			// 4-1.振替休日がある場合(半日振替の半日振替である場合)
			if (requestEntity.hasSubstitute(statuses)) {
				// 半日振替出勤の略称を取得
				return getWorkTypeEntity(workTypeCode, workDate).getWorkTypeAbbr();
			}
			// 半振出(4-2.振替休日が無い場合)
			return TimeNamingUtility.halfSubstituteWorkAbbr(mospParams);
		}
		// 5.振替休日(全休)である場合
		if (TimeRequestUtility.hasHolidayRangeAll(substitutes)) {
			// 振替休日(全休)に振替出勤(半日振替以外)をしていない場合
			if (requestEntity.hasWorkOnHolidayNotHalf(statuses) == false) {
				// 振替休日(全休)の略称を取得
				return TimeRequestUtility.getSubstituteAbbr(requestEntity.getSubstituteType(statuses), mospParams);
			}
		}
		// 6.振替休日(前半休+後半休)である場合
		if (requestEntity.isAmPmHalfSubstitute(statuses)) {
			// 振休を取得
			return TimeNamingUtility.substituteHolidayAbbr(mospParams);
		}
		// 7.それ以外の場合
		// 半日(前)及び後半の名称を準備
		String ante = MospConst.STR_EMPTY;
		String post = MospConst.STR_EMPTY;
		// 7-1.休暇申請(前半休)がある場合
		if (TimeRequestUtility.hasHolidayRangeAm(holidayRequests)) {
			// 半日(前)の名称(休暇略称)を設定
			ante = getHalfWorkTypeAbbr(TimeRequestUtility.getAmHolidayAbbr(holidayRequests, holidays, mospParams));
		}
		// 7-1.休暇申請(後半休)がある場合
		if (TimeRequestUtility.hasHolidayRangePm(holidayRequests)) {
			// 半日(後)の名称(休暇略称)を設定
			post = getHalfWorkTypeAbbr(TimeRequestUtility.getPmHolidayAbbr(holidayRequests, holidays, mospParams));
		}
		// 7-2.代休申請(前半休)がある場合
		if (TimeRequestUtility.hasHolidayRangeAm(subHolidayRequests)) {
			// 半日(前)の名称(代)を設定
			ante = getHalfWorkTypeAbbr(TimeNamingUtility.getSubHoliday(mospParams));
		}
		// 7-2.代休申請(後半休)がある場合
		if (TimeRequestUtility.hasHolidayRangePm(subHolidayRequests)) {
			// 半日(後)の名称(代)を設定
			post = getHalfWorkTypeAbbr(TimeNamingUtility.getSubHoliday(mospParams));
		}
		// 7-3.振替休日(前半休)がある場合
		if (TimeRequestUtility.hasHolidayRangeAm(substitutes)) {
			// 後半の名称が設定されていない場合
			if (MospUtility.isEmpty(post)) {
				// 半振休を取得
				return TimeNamingUtility.halfSubstituteHolidayAbbr(mospParams);
			}
			// 半日(前)の名称(振△)を設定
			ante = TimeNamingUtility.anteSubstituteHolidayAbbr(mospParams);
		}
		// 7-3.振替休日(後半休)がある場合
		if (TimeRequestUtility.hasHolidayRangePm(substitutes)) {
			// 半日(前)の名称が設定されていない場合
			if (MospUtility.isEmpty(ante)) {
				// 半振休を取得
				return TimeNamingUtility.halfSubstituteHolidayAbbr(mospParams);
			}
			// 半日(後)の名称(△振)を設定
			post = TimeNamingUtility.postSubstituteHolidayAbbr(mospParams);
		}
		// 勤務形態略称を取得
		String workTypeAbbr = getWorkTypeEntity(workTypeCode, workDate).getWorkTypeAbbr();
		// 7-4.半日(前後)の名称が設定されていない場合
		if (MospUtility.isAllEmpty(ante, post)) {
			// 勤務形態略称を取得
			return workTypeAbbr;
		}
		// 7-5.半日の名称が設定されている場合
		// 半日(前)の名称が設定されていない場合
		if (MospUtility.isEmpty(ante)) {
			// 半日(前)の名称(勤務形態略称)を設定
			ante = getHalfWorkTypeAbbr(workTypeAbbr);
		}
		// 半日(後)の名称が設定されていない場合
		if (MospUtility.isEmpty(post)) {
			// 半日(後)の名称(勤務形態略称)を設定
			post = getHalfWorkTypeAbbr(workTypeAbbr);
		}
		// 半日(前)の名称か半日(後)の名称が設定されていない場合
		if (MospUtility.isEmpty(ante, post)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 半日(前)/半日(後)を取得
		return MospUtility.concat(PfNameUtility.slash(mospParams).charAt(0), ante, post);
	}
	
	/**
	 * 連続休暇申請中の休暇であるかを確認する。<br>
	 * @param holidayRequests 休暇申請群
	 * @param workType        勤務形態コード
	 * @return 確認結果(true：連続休暇申請中の休暇である、false：そうでない)
	 */
	protected boolean isHolidayInSequenceRequest(List<HolidayRequestDtoInterface> holidayRequests, String workType) {
		// 連続休暇申請である場合
		if (TimeUtility.getHolidayUseDays(holidayRequests) > TimeConst.HOLIDAY_TIMES_ALL) {
			// 勤務形態コードが設定されているかを確認
			return MospUtility.isEmpty(workType) == false;
		}
		// 連続休暇申請中の休暇でないと判断
		return false;
	}
	
	/**
	 * 勤務形態略称(半日用)を取得する。<br>
	 * @param workTypeAbbr 勤務形態略称
	 * @return 勤務形態略称(半日用)
	 */
	protected String getHalfWorkTypeAbbr(String workTypeAbbr) {
		// 勤務形態略称(半日用)を取得
		return MospUtility.substring(workTypeAbbr, 1);
	}
	
	/**
	 * 回数を取得する。<br>
	 * 時間がnull、或いは0の場合は0を返す。<br>
	 * それ以外の場合は1を返す。<br>
	 * @param minutes 時間(分)
	 * @return 回数
	 */
	protected int count(Integer minutes) {
		// 回数を取得
		return TimeUtility.count(minutes);
	}
	
	/**
	 * 時間文字列を取得する(Integer→String)。<br>
	 * 時間を文字列(小数点以下2桁)で表す。<br>
	 * 小数点以下2桁は、分を表す。<br>
	 * @param minutes    対象時間(分)
	 * @param needHyphen ゼロ時ハイフン表示要否(true：ゼロ時ハイフン、false：ゼロ時はゼロ)
	 * @return 時間文字列(0.00)
	 */
	protected String getStringHours(Integer minutes, boolean needHyphen) {
		// 時間文字列を取得
		return TimeUtility.getStringPeriodTimeOrHyphen(mospParams, minutes, needHyphen);
	}
	
	/**
	 * 日付を文字列(HH:mm)に変換する。<br>
	 * 基準日付との差を文字列にして取得する。<br>
	 * <br>
	 * @param date         対象日付
	 * @param standardDate 基準日付
	 * @return 日付文字列(HH:mm)
	 */
	protected String getHourColonMinute(Date date, Date standardDate) {
		// 日付を文字列(HH:mm)に変換
		return TransStringUtility.getHourColonMinute(mospParams, date, standardDate, true);
	}
	
	/**
	 * 数値オブジェクト(回数等)を文字列に変換する。<br>
	 * 回数がnullである場合は、ハイフンを取得する。<br>
	 * @param times 回数
	 * @return 数値オブジェクト(回数等)文字列
	 */
	protected String getIntegerTimes(Integer times) {
		// 数値オブジェクト(回数)を文字列に変換
		return TransStringUtility.getIntegerTimes(mospParams, times, true);
	}
	
	/**
	 * 数値オブジェクト(回数等)を文字列に変換する。<br>
	 * 回数がnullである場合は、ハイフンを取得する。<br>
	 * @param times     回数
	 * @param isDecimal 小数点要否(true：小数点要(1の場合に1.0となる)、false：小数点不要)
	 * @return 数値オブジェクト(回数等)文字列
	 */
	protected String getFloatTimes(Float times, boolean isDecimal) {
		// 数値オブジェクト(回数)を文字列(#.#)に変換
		return TransStringUtility.getFloatTimes(mospParams, times, true, isDecimal);
	}
	
	/**
	 * 日数/時間数文字列を取得する。<br>
	 * @param days  日数
	 * @param hours 時間数
	 * @return 日数/時間数文字列
	 */
	protected String getDaysAndHours(Float days, Float hours) {
		// 日数/時間数文字列を取得
		return MospUtility.concat(PfNameUtility.slash(mospParams),
				new String[]{ TransStringUtility.getFloatTimes(mospParams, days, true, false),
					TransStringUtility.getFloatTimes(mospParams, hours, true, false) });
	}
	
	/**
	 * 勤怠一覧区分が勤怠一覧区分(配列)のいずれかであるかを確認する。<br>
	 * 勤怠一覧情報に設定されている勤怠一覧区分で、判断する。<br>
	 * @param dto       勤怠一覧情報
	 * @param listTypes 勤怠一覧区分(配列)
	 * @return 確認結果(true：勤怠一覧区分が勤怠一覧区分配列のいずれかである、false：そうでない)
	 */
	protected boolean isTheListType(AttendanceListDto dto, int... listTypes) {
		// 勤怠一覧区分が勤怠一覧区分(配列)のいずれかであるかを確認
		return AttendanceUtility.isTheListType(dto, listTypes);
	}
	
	/**
	 * 予定に申請情報を適用するか否かを確認する。<br>
	 * 設定がされていない場合は、falseを返す。<br>
	 * @return 確認結果(true：予定に申請情報を適用する、false：予定に申請情報を適用しない)
	 */
	protected boolean isScheduleApplyRequest() {
		// 予定一覧表示に申請情報を適用するか否かを確認
		return AttendanceUtility.isScheduleApplyRequest(mospParams);
	}
	
	@Override
	public boolean isRetireOrSusoension(Date workDate) {
		// 勤務日に退職している場合
		if (HumanUtility.isRetired(retireDate, workDate)) {
			// 勤務日に退職か休職をしていると判断
			return true;
		}
		// 休職中(勤務日に休職している)である場合
		if (HumanUtility.isSuspension(suspensions, workDate)) {
			// 勤務日に退職か休職をしていると判断
			return true;
		}
		// 勤務日に退職も休職もしていないと判断
		return false;
	}
	
	@Override
	public void setMospParams(MospParams mospParams) {
		this.mospParams = mospParams;
	}
	
	@Override
	public List<AttendanceListDto> getAttendList() {
		return attendList;
	}
	
	@Override
	public void setAttendList(List<AttendanceListDto> attendList) {
		this.attendList = attendList;
	}
	
	@Override
	public void setRetireDate(Date retireDate) {
		this.retireDate = CapsuleUtility.getDateClone(retireDate);
	}
	
	@Override
	public Collection<SuspensionDtoInterface> getSuspensions() {
		return suspensions;
	}
	
	@Override
	public void setSuspensions(Collection<SuspensionDtoInterface> suspensions) {
		this.suspensions = suspensions;
	}
	
	@Override
	public ScheduleDateDtoInterface getScheduleDate(Date workDate) {
		return scheduleDates.get(workDate);
	}
	
	@Override
	public void setScheduleDates(Map<Date, ScheduleDateDtoInterface> scheduleDates) {
		
		this.scheduleDates = scheduleDates;
	}
	
	@Override
	public void setWorkTypeEntities(Map<String, List<WorkTypeEntityInterface>> workTypeEntities) {
		this.workTypeEntities = workTypeEntities;
	}
	
	@Override
	public AttendanceDtoInterface getAttendance(Date workDate) {
		return attendances.get(workDate);
	}
	
	@Override
	public void setAttendances(Map<Date, AttendanceDtoInterface> attendances) {
		this.attendances = attendances;
	}
	
	@Override
	public void setOvertimeRequests(Map<Date, List<OvertimeRequestDtoInterface>> overtimeRequests) {
		this.overtimeRequests = overtimeRequests;
	}
	
	@Override
	public void setHolidayRequests(Map<Date, List<HolidayRequestDtoInterface>> holidayRequests) {
		this.holidayRequests = holidayRequests;
	}
	
	@Override
	public void setWorkOnHolidayRequests(Map<Date, WorkOnHolidayRequestDtoInterface> workOnHolidayRequests) {
		this.workOnHolidayRequests = workOnHolidayRequests;
	}
	
	@Override
	public void setSubstitutedSchedules(Map<Date, ScheduleDateDtoInterface> substitutedSchedules) {
		this.substitutedSchedules = substitutedSchedules;
	}
	
	@Override
	public void setSubstitutes(Map<Date, List<SubstituteDtoInterface>> substitutes) {
		this.substitutes = substitutes;
	}
	
	@Override
	public void setSubHolidayRequests(Map<Date, List<SubHolidayRequestDtoInterface>> subHolidayRequests) {
		this.subHolidayRequests = subHolidayRequests;
	}
	
	@Override
	public void setSubHolidays(Map<Integer, List<SubHolidayDtoInterface>> subHolidays) {
		this.subHolidays = subHolidays;
	}
	
	@Override
	public void setWorkTypeChangeRequests(Map<Date, WorkTypeChangeRequestDtoInterface> workTypeChangeRequests) {
		this.workTypeChangeRequests = workTypeChangeRequests;
	}
	
	@Override
	public void setDifferenceRequests(Map<Date, DifferenceRequestDtoInterface> differenceRequests) {
		this.differenceRequests = differenceRequests;
	}
	
	@Override
	public void setWorkflows(Map<Long, WorkflowDtoInterface> workflows) {
		this.workflows = workflows;
	}
	
	@Override
	public void setCorrections(Map<Date, List<AttendanceCorrectionDtoInterface>> corrections) {
		this.corrections = corrections;
	}
	
	@Override
	public Set<HolidayDtoInterface> getHolidays() {
		return holidays;
	}
	
	@Override
	public void setHolidays(Set<HolidayDtoInterface> holidays) {
		this.holidays = holidays;
	}
	
}
