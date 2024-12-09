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

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;

import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.base.WorkflowNumberDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.base.HolidayRangeDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 申請エンティティクラス。<br>
 * <br>
 * 対象個人IDの対象日における申請情報を保持するクラス。<br>
 * <br>
 */
public class RequestEntity implements RequestEntityInterface {
	
	// TODO RequestUtilBeanの機能を置き換える。
	
	/**
	 * 対象個人ID。<br>
	 */
	protected String								personalId;
	
	/**
	 * 対象日。<br>
	 */
	protected Date									targetDate;
	
	/**
	 * 休暇申請情報リスト。<br>
	 */
	protected List<HolidayRequestDtoInterface>		holidayRequestList;
	
	/**
	 * 残業申請情報リスト。<br>
	 */
	protected List<OvertimeRequestDtoInterface>		overtimeRequestList;
	
	/**
	 * 振替休日情報リスト。<br>
	 */
	protected List<SubstituteDtoInterface>			substituteList;
	
	/**
	 * 代休申請情報リスト。<br>
	 */
	protected List<SubHolidayRequestDtoInterface>	subHolidayRequestList;
	
	/**
	 * 休日出勤申請情報。<br>
	 */
	protected WorkOnHolidayRequestDtoInterface		workOnHolidayRequestDto;
	
	/**
	 * 時差出勤申請情報。<br>
	 */
	protected DifferenceRequestDtoInterface			differenceRequestDto;
	
	/**
	 * 勤務形態変更申請情報。<br>
	 */
	protected WorkTypeChangeRequestDtoInterface		workTypeChangeRequestDto;
	
	/**
	 * 勤怠情報。<br>
	 */
	protected AttendanceDtoInterface				attendanceDto;
	
	/**
	 * ワークフロー情報群。<br>
	 */
	protected Map<Long, WorkflowDtoInterface>		workflowMap;
	
	/**
	 * 予定勤務形態コード。<br>
	 * 対象個人IDの対象日における予定勤務形態コード。<br>
	 * <br>
	 * 振休・休出申請(振替出勤)、勤務形態変更申請等は考慮せず、
	 * カレンダに登録されている勤務形態を保持する。<br>
	 * <br>
	 */
	protected String								scheduledWorkTypeCode;
	
	/**
	 * 振出・休出勤務形態コード。<br>
	 * 振出・休出申請により出勤する日の予定勤務形態コード。<br>
	 */
	protected String								substitutedWorkTypeCode;
	
	
	/**
	 * コンストラクタ。<br>
	 * <br>
	 * 各種申請情報は、setterで設定する。
	 */
	public RequestEntity() {
		// 処理無し
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setTargetDate(Date targetDate) {
		this.targetDate = CapsuleUtility.getDateClone(targetDate);
	}
	
	@Override
	public boolean hasAttendance() {
		return attendanceDto != null;
	}
	
	@Override
	public boolean isAttendanceApplied() {
		// 勤怠データが存在しない場合
		if (hasAttendance() == false) {
			// 勤怠申請がされていないと判断
			return false;
		}
		// 勤怠申請がされているかを確認
		return WorkflowUtility.isApplied(workflowMap.get(attendanceDto.getWorkflow()));
	}
	
	@Override
	public boolean isAttendanceDirectStart() {
		// 勤怠データが存在するかを確認
		if (hasAttendance() == false) {
			return false;
		}
		// 直行が設定されているかを確認
		return MospUtility.isChecked(attendanceDto.getDirectStart());
	}
	
	@Override
	public boolean isAttendanceDirectEnd() {
		// 勤怠データが存在するかを確認
		if (hasAttendance() == false) {
			return false;
		}
		// 直帰が設定されているかを確認
		return MospUtility.isChecked(attendanceDto.getDirectEnd());
	}
	
	@Override
	public String getWorkType(boolean isAttendanceConsidered, Set<String> statuses) {
		// 1.勤怠申請が存在する場合
		if (attendanceDto != null && isAttendanceConsidered) {
			// 勤怠申請に設定されている勤務形態
			return attendanceDto.getWorkTypeCode();
		}
		// 2.全休の場合
		if (isAllHoliday(statuses)) {
			// 全日休暇の勤務形態を取得(振替休日や連続休暇でない場合は空文字)
			return getHolidayWorkType(statuses);
		}
		// 3.勤務形態変更申請がある場合
		if (getWorkTypeChangeRequestDto(statuses) != null) {
			// 変更勤務形態を取得
			return getChangeWorkType(statuses);
		}
		// 4.振出・休出申請がある場合
		if (getWorkOnHolidayRequestDto(statuses) != null) {
			// 振出・休出申請の勤務形態を取得
			return getWorkOnHolidayWorkType(statuses);
		}
		// 5.それ以外の場合
		// 予定勤務形態コード(カレンダで登録されている勤務形態)を取得
		return scheduledWorkTypeCode;
	}
	
	@Override
	public String getWorkType(boolean isAttendanceConsidered, boolean isCompleted) {
		// 勤務形態を取得
		return getWorkType(isAttendanceConsidered, getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public String getWorkType(boolean isAttendanceConsidered) {
		// 勤務形態を取得
		return getWorkType(isAttendanceConsidered, false);
	}
	
	@Override
	public String getWorkType() {
		return getWorkType(true);
	}
	
	@Override
	public boolean isWorkDay() {
		// 承認済フラグ準備(false：申請済申請を考慮)
		boolean isCompleted = false;
		// 1.全休の場合
		if (isAllHoliday(isCompleted)) {
			// 勤務日でないと判断
			return false;
		}
		// 2.振出・休出申請がある場合(承認済申請のみ)
		if (getWorkOnHolidayRequestDto(true) != null) {
			// 勤務日であると判断
			return true;
		}
		// 3.予定勤務形態コードが法定休日か所定休日である場合
		if (TimeUtility.isHoliday(scheduledWorkTypeCode)) {
			// 勤務日でないと判断
			return false;
		}
		// 4.それ以外の場合(勤務日であると判断)
		return true;
	}
	
	/**
	 * 振出・休出申請の勤務形態を取得する。<br>
	 * 振出・休出申請(休日出勤及び勤務形態変更有)の予定勤務形態が無い場合は、
	 * 振替休日の予定勤務形態を取得する。<br>
	 * @param statuses 対象となる承認状況群
	 * @return 振出・休出申請の勤務形態
	 */
	protected String getWorkOnHolidayWorkType(Set<String> statuses) {
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(statuses);
		// 振出・休出申請情報が取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 振出・休出申請(休日出勤及び勤務形態変更有)の予定勤務形態を取得
		String workType = TimeUtility.getWorkOnHolidayWorkType(dto);
		// 振替出勤(勤務形態変更無し)の場合
		if (MospUtility.isEmpty(workType)) {
			// 振替休日の予定勤務形態を取得
			workType = MospUtility.getString(substitutedWorkTypeCode);
		}
		// 振出・休出申請の勤務形態コードを取得
		return workType;
	}
	
	@Override
	public Date getWorkOnHolidayStartTime(Set<String> statuses) {
		// 振出・休出申請情報取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(statuses);
		// 振出・休出申請情報確認
		if (dto == null) {
			return null;
		}
		// 振替出勤の場合
		if (dto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			return null;
		}
		// 休日出勤の場合
		return dto.getStartTime();
	}
	
	@Override
	public Date getWorkOnHolidayStartTime(boolean isCompleted) {
		// 振出・休出申請(休日出勤)の出勤予定時刻を取得
		return getWorkOnHolidayStartTime(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public Date getWorkOnHolidayEndTime(Set<String> statuses) {
		// 振出・休出申請情報取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(statuses);
		// 振出・休出申請情報確認
		if (dto == null) {
			return null;
		}
		// 振替出勤の場合
		if (dto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			return null;
		}
		// 休日出勤の場合
		return dto.getEndTime();
	}
	
	@Override
	public TimeDuration getWorkOnHolidayTime(boolean isCompleted) {
		// 振出・休出申請(休日出勤)が存在しない場合
		if (isWorkOnHolidaySubstituteOff(isCompleted) == false) {
			// 0-0の(妥当でない)時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 振出・休出申請(休日出勤)の休出予定時間(0:00からの分)を取得
		int startTime = TimeUtility.getMinutes(dto.getStartTime(), dto.getRequestDate());
		int endTime = TimeUtility.getMinutes(dto.getEndTime(), dto.getRequestDate());
		return TimeDuration.getInstance(startTime, endTime);
	}
	
	@Override
	public int getOvertimeMinutesBeforeWork(Set<String> statuses) {
		// 残業申請情報取得
		List<OvertimeRequestDtoInterface> list = getOvertimeRequestList(statuses);
		// 残業申請毎に処理
		for (OvertimeRequestDtoInterface dto : list) {
			// 勤務前残業申請確認
			if (dto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_BEFORE) {
				// 申請時間を取得
				return dto.getRequestTime();
			}
		}
		// 残業申請(勤務前残業)が無い場合
		return 0;
	}
	
	@Override
	public int getOvertimeMinutesBeforeWork(boolean isCompleted) {
		// 残業申請(勤務前残業)の申請時間(分)を取得
		return getOvertimeMinutesBeforeWork(WorkflowUtility.getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public int getOvertimeMinutesAfterWork(Set<String> statuses) {
		// 残業申請情報取得
		List<OvertimeRequestDtoInterface> list = getOvertimeRequestList(statuses);
		// 残業申請毎に処理
		for (OvertimeRequestDtoInterface dto : list) {
			// 勤務前残業申請確認
			if (dto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_AFTER) {
				// 申請時間を取得
				return dto.getRequestTime();
			}
		}
		// 残業申請(勤務前残業)が無い場合
		return 0;
	}
	
	@Override
	public int getOvertimeMinutesAfterWork(boolean isCompleted) {
		// 残業申請(勤務後残業)の申請時間(分)を取得
		return getOvertimeMinutesAfterWork(WorkflowUtility.getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public boolean isAllHoliday(Set<String> statuses) {
		// 全休があるかを確認
		boolean hasAllHoliday = hasAllHoliday(statuses);
		// 前半休を確認
		boolean hasAmHoliday = hasAmHoliday(statuses);
		// 後半休があるかを確認
		boolean hasPmHoliday = hasPmHoliday(statuses);
		// 全休であるかを確認
		return hasAllHoliday || (hasAmHoliday && hasPmHoliday);
	}
	
	@Override
	public boolean isAllHoliday(boolean isCompleted) {
		// 対象日が全休であるかを確認
		return isAllHoliday(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public boolean isAmHoliday(Set<String> statuses) {
		// 全休があるかを確認
		if (hasAllHoliday(statuses)) {
			return false;
		}
		// 後半休があるかを確認
		if (hasPmHoliday(statuses)) {
			return false;
		}
		// 前半休を確認
		return hasAmHoliday(statuses);
	}
	
	@Override
	public boolean isAmHoliday(boolean isCompleted) {
		// 対象日が前半休であるかを確認
		return isAmHoliday(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public boolean isPmHoliday(Set<String> statuses) {
		// 全休があるかを確認
		if (hasAllHoliday(statuses)) {
			return false;
		}
		// 前半休があるかを確認
		if (hasAmHoliday(statuses)) {
			return false;
		}
		// 後半休を確認
		return hasPmHoliday(statuses);
	}
	
	@Override
	public boolean isPmHoliday(boolean isCompleted) {
		// 対象日が後半休であるかを確認
		return isPmHoliday(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public boolean isHalfHoliday(Set<String> statuses) {
		// 対象日が前半休か後半休であるかを確認
		return isAmHoliday(statuses) || isPmHoliday(statuses);
	}
	
	/**
	 * 全休があるかを確認する。<br>
	 * 休暇申請、代休申請、振替休日情報に、全休があるかを確認する。<br>
	 * <br>
	 * 但し、振替休日情報に全日があったとしても、振出・休出申請情報があった場合は
	 * 振替の振替とみなし、全休でないとする。<br>
	 * <br>
	 * また、休暇申請情報に全休があったとしても、振出・休出申請情報
	 * (振替申請しない：休日出勤)があった場合は休日出勤とみなし、全休でないとする。<br>
	 * <br>
	 * 補足：<br>
	 * 休暇申請を期間で行うと、その期間中でカレンダー上休日である日に対して、
	 * 振出・休出申請(振替申請しない：休日出勤)をすることができる。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：全休が有る、false：全休が無い)
	 */
	protected boolean hasAllHoliday(Set<String> statuses) {
		// 休暇申請情報確認
		if (hasAllHoliday(getHolidayRequestList(statuses)) && isWorkOnHolidaySubstituteOff(statuses) == false) {
			return true;
		}
		// 代休申請情報確認
		if (hasAllHoliday(getSubHolidayRequestList(statuses))) {
			return true;
		}
		// 振替休日情報(全日)があり振出・休出申請情報が無い場合
		if (hasAllHoliday(getSubstituteList(statuses)) && hasWorkOnHoliday(statuses) == false) {
			return true;
		}
		return false;
	}
	
	/**
	 * 前半休があるかを確認する。<br>
	 * 休暇申請、代休申請、振替休日情報に、前半休があるかを確認する。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：前半休が有る、false：前半休が無い)
	 */
	protected boolean hasAmHoliday(Set<String> statuses) {
		// 休暇申請情報確認
		if (hasAmHoliday(getHolidayRequestList(statuses))) {
			return true;
		}
		// 代休申請情報確認
		if (hasAmHoliday(getSubHolidayRequestList(statuses))) {
			return true;
		}
		// 振替休日情報確認
		if (hasAmHoliday(getSubstituteList(statuses))) {
			// 振替出勤申請が午前でない場合(半日振替の振替でない場合)
			if (hasAmWorkOnHoliday(statuses) == false) {
				return true;
			}
		}
		// 振出・休出申請情報確認
		if (hasPmWorkOnHoliday(statuses)) {
			// 振替休日が午後でない場合(半日振替の振替でない場合)
			if (hasPmHoliday(getSubstituteList(statuses)) == false) {
				return true;
			}
		}
		return false;
	}
	
	/**
	 * 後半休があるかを確認する。<br>
	 * 休暇申請、代休申請、振替休日情報に、午後休があるかを確認する。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 確認結果(true：後半休が有る、false：後半休が無い)
	 */
	protected boolean hasPmHoliday(Set<String> statuses) {
		// 休暇申請情報確認
		if (hasPmHoliday(getHolidayRequestList(statuses))) {
			return true;
		}
		// 代休申請情報確認
		if (hasPmHoliday(getSubHolidayRequestList(statuses))) {
			return true;
		}
		// 振替休日情報確認
		if (hasPmHoliday(getSubstituteList(statuses))) {
			// 振替出勤申請が午後の場合(半日振替の振替でない場合)
			if (hasPmWorkOnHoliday(statuses) == false) {
				return true;
			}
		}
		// 振出・休出申請情報確認
		if (hasAmWorkOnHoliday(statuses)) {
			// 振替休日が午前の場合(半日振替の振替でない場合)
			if (hasAmHoliday(getSubstituteList(statuses)) == false) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean hasWorkOnHoliday(Set<String> statuses) {
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = getWorkOnHolidayRequestDto(statuses);
		// 振出・休出申請情報が有るかを確認
		return MospUtility.isEmpty(workOnHolidayRequestDto) == false;
	}
	
	@Override
	public boolean hasWorkOnHoliday(boolean isCompleted) {
		// 振出・休出申請情報が有るかを確認
		return hasWorkOnHoliday(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public boolean hasAmWorkOnHoliday(boolean isCompleted) {
		// 振替出勤(午前)があるかを確認
		return hasAmWorkOnHoliday(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public boolean hasAmWorkOnHoliday(Set<String> statuses) {
		// 振出・休出申請情報が無い場合
		if (hasWorkOnHoliday(statuses) == false) {
			return false;
		}
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(statuses);
		// 振替出勤(午前)を確認
		return dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM;
	}
	
	@Override
	public boolean hasPmWorkOnHoliday(boolean isCompleted) {
		// 振替出勤(午後)があるかを確認
		return hasPmWorkOnHoliday(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public boolean hasPmWorkOnHoliday(Set<String> statuses) {
		// 振出・休出申請情報が無い場合
		if (hasWorkOnHoliday(statuses) == false) {
			return false;
		}
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(statuses);
		// 振替出勤(午前)を確認
		return dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM;
	}
	
	@Override
	public boolean hasWorkOnHolidayNotHalf(Set<String> statuses) {
		// 振出・休出申請情報(半日振替以外)があるかを確認
		return hasWorkOnHoliday(statuses) && hasAmWorkOnHoliday(statuses) == false
				&& hasPmWorkOnHoliday(statuses) == false;
	}
	
	@Override
	public boolean hasSubstitute(Set<String> statuses) {
		// 振替休日情報を取得
		List<SubstituteDtoInterface> substitutes = getSubstituteList(statuses);
		// 振替休日情報があるかを確認
		return MospUtility.isEmpty(substitutes) == false;
	}
	
	@Override
	public boolean isWorkOnLegal(boolean isCompleted) {
		// 休日出勤(振替申請しない)でない場合
		if (isWorkOnHolidaySubstituteOff(isCompleted) == false) {
			// 法定休日出勤でないと判断
			return false;
		}
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 法定休日出勤であるかを確認
		return dto.getWorkOnHolidayType().equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY);
	}
	
	@Override
	public boolean isWorkOnPrescribed(boolean isCompleted) {
		// 休日出勤(振替申請しない)でない場合
		if (isWorkOnHolidaySubstituteOff(isCompleted) == false) {
			// 所定休日出勤でないと判断
			return false;
		}
		// 振出・休出申請情報を取得
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(isCompleted);
		// 所定休日出勤であるかを確認
		return dto.getWorkOnHolidayType().equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY);
	}
	
	@Override
	public boolean isWorkOnHolidaySubstitute(boolean isCompleted) {
		// 振出・休出申請情報が無い場合
		if (hasWorkOnHoliday(isCompleted) == false) {
			return false;
		}
		// 休日出勤(振替申請しない)でないかを確認
		return isWorkOnHolidaySubstituteOff(isCompleted) == false;
	}
	
	@Override
	public boolean isWorkOnHolidaySubstituteOff(boolean isCompleted) {
		// 休日出勤(振替申請しない)であるかを確認
		return isWorkOnHolidaySubstituteOff(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public boolean isWorkOnHolidaySubstituteOff(Set<String> statuses) {
		// 振出・休出申請が休日出勤(振替申請しない)であるかを確認
		return TimeRequestUtility.isWorkOnHolidaySubstituteOff(getWorkOnHolidayRequestDto(statuses));
	}
	
	@Override
	public WorkflowDtoInterface getHolidayWorkflow(Set<String> statuses) {
		// 全休のワークフロー情報を取得
		WorkflowDtoInterface dto = getTheHolidayRangeWorkflow(statuses, TimeConst.CODE_HOLIDAY_RANGE_ALL);
		// 全休のワークフロー情報を取得できた場合
		if (MospUtility.isEmpty(dto) == false) {
			// 全休のワークフロー情報を取得
			return dto;
		}
		// 前半休のワークフロー情報を取得
		WorkflowDtoInterface am = getTheHolidayRangeWorkflow(statuses, TimeConst.CODE_HOLIDAY_RANGE_AM);
		// 後半休のワークフロー情報を取得
		WorkflowDtoInterface pm = getTheHolidayRangeWorkflow(statuses, TimeConst.CODE_HOLIDAY_RANGE_PM);
		// 承認状態が最も低いワークフロー情報を取得
		return WorkflowUtility.getLowestWorkflow(am, pm);
	}
	
	/**
	 * 対象休暇範囲のワークフロー情報を取得する。<br>
	 * 但し、複数ある場合は、最初に見つかった休暇(範囲)情報のものを取得する。<br>
	 * また、存在しない場合は、nullを取得する。<br>
	 * 休暇申請情報、代休申請情報、振替休日情報の順で確認する。<br>
	 * @param statuses     対象となる承認状況群
	 * @param holidayRange 休暇範囲
	 * @return 対象休暇範囲の休暇(範囲)情報のワークフロー番号
	 */
	protected <T extends HolidayRangeDtoInterface & WorkflowNumberDtoInterface> WorkflowDtoInterface getTheHolidayRangeWorkflow(
			Set<String> statuses, int holidayRange) {
		// 対象休暇範囲の休暇がない場合
		if (hasTheRangeHoliday(statuses, holidayRange) == false) {
			// nullを取得
			return null;
		}
		// 対象休暇範囲の休暇申請情報のワークフロー情報を取得
		WorkflowDtoInterface dto = getTheHolidayRangeWorkflow(getHolidayRequestList(statuses), holidayRange);
		// 対象休暇範囲の休暇申請情報のワークフロー情報を取得できた場合
		if (MospUtility.isEmpty(dto) == false) {
			// 対象休暇範囲の休暇申請情報のワークフロー情報を取得
			return dto;
		}
		// 対象休暇範囲の代休申請情報のワークフロー情報を取得
		dto = getTheHolidayRangeWorkflow(getSubHolidayRequestList(statuses), holidayRange);
		// 対象休暇範囲の代休申請情報のワークフロー情報を取得できた場合
		if (MospUtility.isEmpty(dto) == false) {
			// 対象休暇範囲の代休申請情報のワークフロー情報を取得
			return dto;
		}
		// 対象休暇範囲の振替休日情報のワークフロー情報を取得
		return getTheHolidayRangeWorkflow(getSubstituteList(statuses), holidayRange);
	}
	
	/**
	 * 対象休暇範囲の休暇(範囲)情報のワークフロー情報を取得する。<br>
	 * 但し、複数ある場合は、最初に見つかった休暇(範囲)情報のものを取得する。<br>
	 * また、存在しない場合は、nullを取得する。<br>
	 * @param dtos         休暇(範囲)情報群
	 * @param holidayRange 休暇範囲
	 * @return 対象休暇範囲の休暇(範囲)情報のワークフロー番号
	 */
	protected <T extends HolidayRangeDtoInterface & WorkflowNumberDtoInterface> WorkflowDtoInterface getTheHolidayRangeWorkflow(
			Collection<T> dtos, int holidayRange) {
		// 対象休暇範囲の休暇(範囲)情報のワークフロー情報を取得
		return workflowMap.get(TimeRequestUtility.getTheHolidayRangeWorkflow(dtos, holidayRange));
	}
	
	/**
	 * 対象休暇範囲の休暇があるかを確認する。<br>
	 * 休暇申請、代休申請、振替休日情報に、全休があるかを確認する。<br>
	 * 但し、時間単位休暇は考慮しない。<br>
	 * @param statuses     対象となる承認状況群
	 * @param holidayRange 休暇範囲
	 * @return 確認結果(true：対象休暇範囲の休暇がある、false：対象休暇範囲の休暇が無い)
	 */
	protected boolean hasTheRangeHoliday(Set<String> statuses, int holidayRange) {
		// 休暇範囲毎に処理
		switch (holidayRange) {
			// 全休の場合
			case TimeConst.CODE_HOLIDAY_RANGE_ALL:
				// 全休があるかを確認
				return hasAllHoliday(statuses);
			// 前半休の場合
			case TimeConst.CODE_HOLIDAY_RANGE_AM:
				// 前半休があるかを確認
				return hasAmHoliday(statuses);
			// 後半休の場合
			case TimeConst.CODE_HOLIDAY_RANGE_PM:
				// 後半休があるかを確認
				return hasPmHoliday(statuses);
			// デフォルト
			default:
				return false;
		}
	}
	
	@Override
	public List<Date> getHourlyHolidayFirstSequenceTimes(Set<String> statuses) {
		// リストの準備
		List<Date> hourlyHolidayFirstSequenceTimes = new ArrayList<Date>();
		// 休暇申請情報(時間休)群を取得
		Map<Date, HolidayRequestDtoInterface> map = getHourlyHolidayMap(statuses);
		// 休暇申請情報(時間休)群を確認
		if (map.isEmpty()) {
			return hourlyHolidayFirstSequenceTimes;
		}
		// 時間休開始時刻リスト取得
		List<Date> startTimeList = new ArrayList<Date>(map.keySet());
		// ソート
		Collections.sort(startTimeList);
		// 開始時間をリストに設定
		hourlyHolidayFirstSequenceTimes.add(new Date(startTimeList.get(0).getTime()));
		// 連続終了時刻準備
		Date endTime = null;
		// 休暇申請情報(時間休)毎に処理
		for (Date startTime : startTimeList) {
			// 連続確認
			if (endTime != null && startTime.compareTo(endTime) != 0) {
				break;
			}
			// 休暇申請情報(時間休)情報取得
			HolidayRequestDtoInterface dto = map.get(startTime);
			// 終了時刻取得
			endTime = dto.getEndTime();
		}
		// 連続終了時刻をリストに設定
		hourlyHolidayFirstSequenceTimes.add(new Date(endTime.getTime()));
		return hourlyHolidayFirstSequenceTimes;
	}
	
	@Override
	public List<Date> getHourlyHolidayFirstSequenceTimes() {
		// 初回連続時間休時刻(開始時刻及び終了時刻)を取得
		return getHourlyHolidayFirstSequenceTimes(WorkflowUtility.getAppliedStatuses());
	}
	
	@Override
	public List<Integer> getHourlyHolidayFirstSequenceMinutes() {
		// 初回連続時間休時刻(開始時刻及び終了時刻)を取得
		List<Date> holidayTimeList = getHourlyHolidayFirstSequenceTimes();
		// 対象日からの分数として取得
		return getHourlyHolidaySequenceMinutes(holidayTimeList);
	}
	
	@Override
	public TimeDuration getHourlyHolidayFirstSequence() {
		// 初回連続時間休時刻(開始時刻及び終了時刻)を取得
		List<Integer> list = getHourlyHolidayFirstSequenceMinutes();
		// 時間休が無い場合
		if (list.isEmpty()) {
			// 妥当でない時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 初回連続時間休時間間隔を取得
		return TimeDuration.getInstance(list.get(0), list.get(1));
	}
	
	@Override
	public List<Date> getHourlyHolidayLastSequenceTimes(Set<String> statuses) {
		// リストの準備
		List<Date> hourlyHolidayFirstSequenceTimes = new ArrayList<Date>();
		// 休暇申請情報(時間休)群を取得
		Map<Date, HolidayRequestDtoInterface> map = getHourlyHolidayMap(statuses);
		// 休暇申請情報(時間休)群を確認
		if (map.isEmpty()) {
			return hourlyHolidayFirstSequenceTimes;
		}
		// 時間休開始時刻リスト取得
		List<Date> startTimeList = new ArrayList<Date>(map.keySet());
		// ソート(逆順)
		Collections.sort(startTimeList);
		Collections.reverse(startTimeList);
		// 終了時間をリストに設定
		hourlyHolidayFirstSequenceTimes.add(new Date(map.get(startTimeList.get(0)).getEndTime().getTime()));
		// 連続開始時刻準備
		Date sequenceStartTime = null;
		// 休暇申請情報(時間休)毎に処理
		for (Date startTime : startTimeList) {
			// 休暇申請情報(時間休)情報取得
			HolidayRequestDtoInterface dto = map.get(startTime);
			// 連続確認
			if (sequenceStartTime != null && dto.getEndTime().compareTo(sequenceStartTime) != 0) {
				break;
			}
			// 連続開始時刻設定
			sequenceStartTime = startTime;
		}
		// 連続終了時刻をリストに設定
		hourlyHolidayFirstSequenceTimes.add(0, new Date(sequenceStartTime.getTime()));
		return hourlyHolidayFirstSequenceTimes;
	}
	
	@Override
	public List<Date> getHourlyHolidayLastSequenceTimes() {
		// 最終連続時間休時刻(開始時刻及び終了時刻)を取得
		return getHourlyHolidayLastSequenceTimes(WorkflowUtility.getAppliedStatuses());
	}
	
	@Override
	public List<Integer> getHourlyHolidayLastSequenceMinutes() {
		// 最終連続時間休時刻(開始時刻及び終了時刻)を取得
		List<Date> holidayTimeList = getHourlyHolidayLastSequenceTimes();
		// 対象日からの分数として取得
		return getHourlyHolidaySequenceMinutes(holidayTimeList);
	}
	
	@Override
	public TimeDuration getHourlyHolidayLastSequence() {
		// 最終連続時間休時刻(開始時刻及び終了時刻)を取得
		List<Integer> list = getHourlyHolidayLastSequenceMinutes();
		// 時間休が無い場合
		if (list.isEmpty()) {
			// 妥当でない時間間隔を取得
			return TimeDuration.getInvalid();
		}
		// 最終連続時間休時間間隔を取得
		return TimeDuration.getInstance(list.get(0), list.get(1));
	}
	
	@Override
	public List<Integer> getHourlyHolidaySequenceMinutes(List<Date> holidayTimeList) {
		// 連続時間休時刻(開始時刻及び終了時刻)(分)を準備
		List<Integer> holidayMinuteList = new ArrayList<Integer>();
		// 時間単位有給休暇が存在しない場合
		if (holidayTimeList.isEmpty()) {
			// 空のリストを取得
			return holidayMinuteList;
		}
		// 最終連続時間休時刻の開始時刻及び終了時刻(分)を取得
		holidayMinuteList.add(TimeUtility.getMinutes(holidayTimeList.get(0), targetDate));
		holidayMinuteList.add(TimeUtility.getMinutes(holidayTimeList.get(1), targetDate));
		// 連続時間休時刻(開始時刻及び終了時刻)(分)を取得
		return holidayMinuteList;
	}
	
	/**
	 * 休暇申請情報(時間休)(時休開始時刻順)群を取得する。<br>
	 * 時休開始時刻をキー、休暇申請情報を値とする。<br>
	 * 休暇申請情報(時間休)が存在しない場合は、空のマップを返す。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 休暇申請情報(時間休)(時休開始時刻順)群
	 */
	protected Map<Date, HolidayRequestDtoInterface> getHourlyHolidayMap(Set<String> statuses) {
		// 休暇申請情報群を準備
		Map<Date, HolidayRequestDtoInterface> map = new TreeMap<Date, HolidayRequestDtoInterface>();
		// 休暇申請情報リストを取得
		List<HolidayRequestDtoInterface> list = getHolidayRequestList(statuses);
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : list) {
			// 休暇申請情報が時間休でない場合
			if (TimeRequestUtility.isHolidayRangeHour(dto) == false) {
				continue;
			}
			// 休暇申請情報群に設定
			map.put(dto.getStartTime(), dto);
		}
		return map;
	}
	
	@Override
	public Map<Date, HolidayRequestDtoInterface> getHourlyHolidayMap(boolean isCompleted) {
		// 休暇申請情報(時間休)(時休開始時刻順)群を取得
		return getHourlyHolidayMap(WorkflowUtility.getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public Map<Integer, TimeDuration> getHourlyHolidayTimes(boolean isCompleted) {
		// 時間単位休暇時間間隔群を準備
		Map<Integer, TimeDuration> hourlyHolidays = new LinkedHashMap<Integer, TimeDuration>();
		// 休暇申請情報(時間休)(時休開始時刻順)毎に処理
		for (HolidayRequestDtoInterface dto : getHourlyHolidayMap(isCompleted).values()) {
			// 開始時刻及び終了時刻を準備
			int startTime = TimeUtility.getMinutes(dto.getStartTime(), dto.getRequestStartDate());
			int endTime = TimeUtility.getMinutes(dto.getEndTime(), dto.getRequestEndDate());
			// 時間間隔を取得し時間単位休暇時間間隔群に統合
			hourlyHolidays = TimeUtility.mergeDurations(hourlyHolidays, TimeDuration.getInstance(startTime, endTime));
		}
		// 時間単位休暇時間間隔群(キー：開始時刻(キー順))を取得
		return TimeUtility.combineDurations(hourlyHolidays);
	}
	
	@Override
	public int getHourlyHolidayMinutes(boolean isCompleted) {
		// 時間単位休暇時間(分)を準備
		int minutes = 0;
		// 時間単位休暇時間間隔毎に処理
		for (TimeDuration duration : getHourlyHolidayTimes(isCompleted).values()) {
			// 時間単位休暇時間(分)を加算
			minutes += duration.getMinutes();
		}
		// 時間単位休暇時間(分)を取得
		return minutes;
	}
	
	@Override
	public String getSubstituteType(boolean isCompleted) {
		// 振替休日情報(全休or前半休+後半休)の休暇種別(法定休日、所定休日)を取得
		return getSubstituteType(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public String getSubstituteType(Set<String> statuses) {
		// 振替休日情報リストを取得
		List<SubstituteDtoInterface> list = getSubstituteList(statuses);
		// 振替休日情報毎に処理
		for (SubstituteDtoInterface dto : list) {
			// 全休確認
			if (TimeRequestUtility.isHolidayRangeAll(dto)) {
				return dto.getSubstituteType();
			}
		}
		// 前半休+後半休の場合
		if (hasAmHoliday(list) && hasPmHoliday(list)) {
			// 振替休日情報毎に処理
			for (SubstituteDtoInterface dto : list) {
				// 振替種別が法定休日である場合(前半か後半のどちらかが法定休日である場合)
				if (TimeUtility.isLegalHoliday(dto.getSubstituteType())) {
					// 法定休日を取得(半日法定振替出勤を可能とした場合にあり得る)
					return TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY;
				}
			}
			// 所定休日を取得
			return TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY;
		}
		// 振替休日情報(全休or前半休+後半休)が存在しない場合
		return MospConst.STR_EMPTY;
	}
	
	/**
	 * 全日休暇の勤務形態を取得する。<br>
	 * 連続休暇である休暇申請が存在しない場合は、空文字(全休の振替休日情報がある場合はその振替種別)を取得する。<br>
	 * 連続休暇である休暇申請が存在する場合は、次の通り。
	 * 1.休日出勤(振替申請しない)である振出・休出申請が存在する場合：振出・休出申請の勤務形態
	 * 2.振出・休出申請(休日出勤以外)が存在する場合                ：空文字
	 * 3.予定勤務形態コードが法定休日か所定休日である場合          ：予定勤務形態コード
	 * 4.それ以外の場合：空文字(全休の振替休日情報がある場合はその振替種別)
	 * <br>
	 * 補足：連続休暇中の振替出勤申請は、休暇申請が優先されて休暇となる。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 全日休暇の勤務形態
	 */
	protected String getHolidayWorkType(Set<String> statuses) {
		// 連続休暇である休暇申請が存在しない場合
		if (TimeRequestUtility.isConsecutiveHolidaysExist(getHolidayRequestList(statuses)) == false) {
			// 空文字(全休の振替休日情報がある場合はその振替種別)を取得
			return getSubstituteType(statuses);
		}
		// 振出・休出申請を取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequest = getWorkOnHolidayRequestDto(statuses);
		// 1.休日出勤(振替申請しない)である振出・休出申請が存在する場合
		if (TimeRequestUtility.isWorkOnHolidaySubstituteOff(workOnHolidayRequest)) {
			// 振出・休出申請の勤務形態を取得
			return getWorkOnHolidayWorkType(statuses);
		}
		// 2.振出・休出申請(休日出勤以外)が存在する場合：空文字を取得
		if (MospUtility.isEmpty(workOnHolidayRequest) == false) {
			return MospConst.STR_EMPTY;
		}
		// 3.予定勤務形態コードが法定休日か所定休日である場合
		if (TimeUtility.isHoliday(scheduledWorkTypeCode)) {
			// 予定勤務形態コードを取得
			return scheduledWorkTypeCode;
		}
		// 4.それ以外の場合：空文字(全休の振替休日情報がある場合はその振替種別)
		return getSubstituteType(statuses);
	}
	
	/**
	 * 変更勤務形態を取得する。<br>
	 * <br>
	 * 勤務形態変更申請が存在しない場合は、空文字を返す。<br>
	 * <br>
	 * @param statuses 対象となる承認状況群
	 * @return 変更勤務形態
	 */
	protected String getChangeWorkType(Set<String> statuses) {
		// 勤務形態変更申請を取得
		WorkTypeChangeRequestDtoInterface dto = getWorkTypeChangeRequestDto(statuses);
		// 勤務形態変更申請を確認
		if (dto == null) {
			return MospConst.STR_EMPTY;
		}
		// 変更勤務形態を取得
		return dto.getWorkTypeCode();
	}
	
	@Override
	public boolean isAmPmHalfSubstitute(boolean isCompleted) {
		// 半日振休＋半日振休があるか判断
		return isAmPmHalfSubstitute(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public boolean isAmPmHalfSubstitute(Set<String> statuses) {
		// 振替休日リスト取得
		List<SubstituteDtoInterface> list = getSubstituteList(statuses);
		// 半日振休＋半日振休があるか判断
		return hasAmHoliday(list) && hasPmHoliday(list);
	}
	
	@Override
	public boolean isHalfPostpone(boolean isCompleted) {
		// 半日振替出勤申請がない場合
		if (hasAmWorkOnHoliday(isCompleted) == false && hasPmWorkOnHoliday(isCompleted) == false) {
			return false;
		}
		// 振替休日リスト取得
		List<SubstituteDtoInterface> list = getSubstituteList(isCompleted);
		// 半日振替休日がない場合
		if (hasAmHoliday(list) == false && hasPmHoliday(list) == false) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isAttendanceAppliable() {
		// 勤務日でない場合
		if (!isWorkDay()) {
			// 未申請でないと判断
			return false;
		}
		// 勤怠申請がされている場合
		if (isAttendanceApplied()) {
			// 勤怠未申請でない
			return false;
		}
		// 残業申請がされている場合
		if (isOvertimeApplied(false)) {
			return false;
		}
		// 休暇申請がされている場合
		if (isHolidayApplied(false)) {
			return false;
		}
		// 代休申請がされている場合
		if (isSubHolidayApplied(false)) {
			return false;
		}
		// 休日出勤申請がされている場合
		if (isWorkOnHolidayHolidayApplied(false)) {
			return false;
		}
		// 振替休日申請がされている場合
		if (isSubstituteApplied(false)) {
			return false;
		}
		// 時差出勤申請がされている場合
		if (isDifferenceApplied(false)) {
			return false;
		}
		// 勤務形態変更申請がされている場合
		if (isWorkTypeChangeApplied(false)) {
			return false;
		}
		return true;
	}
	
	@Override
	public boolean isOvertimeApplied(boolean isContainCompleted) {
		// 残業申請毎に処理
		for (OvertimeRequestDtoInterface dto : overtimeRequestList) {
			// ワークフロー取得
			WorkflowDtoInterface workflowDto = workflowMap.get(dto.getWorkflow());
			// ワークフローが承認済で承認済を含む場合
			if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
				return true;
			}
			// 解除申請である場合
			if (WorkflowUtility.isCancelApply(workflowDto)) {
				return true;
			}
			// 解除申請(取下希望)である場合
			if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
				return true;
			}
			// 承認可能である場合
			if (WorkflowUtility.isApprovable(workflowDto)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isHolidayApplied(boolean isContainCompleted) {
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface dto : holidayRequestList) {
			// ワークフロー取得
			WorkflowDtoInterface workflowDto = workflowMap.get(dto.getWorkflow());
			// ワークフローが承認済で承認済を含む場合
			if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
				return true;
			}
			// 解除申請である場合
			if (WorkflowUtility.isCancelApply(workflowDto)) {
				return true;
			}
			// 解除申請(取下希望)である場合
			if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
				return true;
			}
			// 承認可能である場合
			if (WorkflowUtility.isApprovable(workflowDto)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isSubHolidayApplied(boolean isContainCompleted) {
		// 代休申請毎に処理
		for (SubHolidayRequestDtoInterface dto : subHolidayRequestList) {
			// ワークフロー取得
			WorkflowDtoInterface workflowDto = workflowMap.get(dto.getWorkflow());
			// ワークフローが承認済で承認済を含む場合
			if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
				return true;
			}
			// 解除申請である場合
			if (WorkflowUtility.isCancelApply(workflowDto)) {
				return true;
			}
			// 解除申請(取下希望)である場合
			if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
				return true;
			}
			// 承認可能である場合
			if (WorkflowUtility.isApprovable(workflowDto)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isWorkOnHolidayHolidayApplied(boolean isContainCompleted) {
		// 休日出勤申請有無
		if (workOnHolidayRequestDto == null) {
			return false;
		}
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = workflowMap.get(workOnHolidayRequestDto.getWorkflow());
		// ワークフローが承認済で承認済を含む場合
		if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
			return true;
		}
		// 解除申請である場合
		if (WorkflowUtility.isCancelApply(workflowDto)) {
			return true;
		}
		// 解除申請(取下希望)である場合
		if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
			return true;
		}
		// 承認可能である場合
		if (WorkflowUtility.isApprovable(workflowDto)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isSubstituteApplied(boolean isContainCompleted) {
		// 振替休日情報毎に処理
		for (SubstituteDtoInterface dto : substituteList) {
			// ワークフロー取得
			WorkflowDtoInterface workflowDto = workflowMap.get(dto.getWorkflow());
			// ワークフローが承認済で承認済を含む場合
			if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
				return true;
			}
			// 解除申請である場合
			if (WorkflowUtility.isCancelApply(workflowDto)) {
				return true;
			}
			// 解除申請(取下希望)である場合
			if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
				return true;
			}
			// 承認可能である場合
			if (WorkflowUtility.isApprovable(workflowDto)) {
				return true;
			}
		}
		return false;
	}
	
	@Override
	public boolean isDifferenceApplied(boolean isContainCompleted) {
		// 時差出勤申請有無
		if (differenceRequestDto == null) {
			return false;
		}
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = workflowMap.get(differenceRequestDto.getWorkflow());
		// ワークフローが承認済で承認済を含む場合
		if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
			return true;
		}
		// 解除申請である場合
		if (WorkflowUtility.isCancelApply(workflowDto)) {
			return true;
		}
		// 解除申請(取下希望)である場合
		if (WorkflowUtility.isCancelWithDrawnApply(workflowDto)) {
			return true;
		}
		// 承認可能である場合
		if (WorkflowUtility.isApprovable(workflowDto)) {
			return true;
		}
		return false;
	}
	
	@Override
	public boolean isWorkTypeChangeApplied(boolean isContainCompleted) {
		// 勤務形態変更申請有無
		if (workTypeChangeRequestDto == null) {
			return false;
		}
		// ワークフロー取得
		WorkflowDtoInterface workflowDto = workflowMap.get(workTypeChangeRequestDto.getWorkflow());
		// ワークフローが承認済で承認済を含む場合
		if (WorkflowUtility.isCompleted(workflowDto) && isContainCompleted) {
			return true;
		}
		// 解除申請である場合
		if (WorkflowUtility.isCancelApply(workflowDto)) {
			return true;
		}
		// 承認可能である場合
		if (WorkflowUtility.isApprovable(workflowDto)) {
			return true;
		}
		return false;
	}
	
	@Override
	public AttendanceDtoInterface getAttendanceDto(Set<String> statuses) {
		return getRequestDto(attendanceDto, statuses);
	}
	
	@Override
	public List<HolidayRequestDtoInterface> getHolidayRequestList(boolean isCompleted) {
		return getHolidayRequestList(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public List<HolidayRequestDtoInterface> getHolidayRequestList(Set<String> statuses) {
		return WorkflowUtility.getStatusMatchedList(holidayRequestList, workflowMap, statuses);
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(boolean isCompleted) {
		return getSubHolidayRequestList(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(Set<String> statuses) {
		return WorkflowUtility.getStatusMatchedList(subHolidayRequestList, workflowMap, statuses);
	}
	
	@Override
	public List<SubstituteDtoInterface> getSubstituteList(boolean isCompleted) {
		return getSubstituteList(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public List<SubstituteDtoInterface> getSubstituteList(Set<String> statuses) {
		return WorkflowUtility.getStatusMatchedList(substituteList, workflowMap, statuses);
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface getWorkOnHolidayRequestDto(boolean isCompleted) {
		return getWorkOnHolidayRequestDto(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface getWorkOnHolidayRequestDto(Set<String> statuses) {
		return getRequestDto(workOnHolidayRequestDto, statuses);
	}
	
	@Override
	public List<OvertimeRequestDtoInterface> getOvertimeRequestList(boolean isCompleted) {
		return getOvertimeRequestList(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public List<OvertimeRequestDtoInterface> getOvertimeRequestList(Set<String> statuses) {
		return WorkflowUtility.getStatusMatchedList(overtimeRequestList, workflowMap, statuses);
	}
	
	@Override
	public WorkTypeChangeRequestDtoInterface getWorkTypeChangeRequestDto(boolean isCompleted) {
		return getWorkTypeChangeRequestDto(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public WorkTypeChangeRequestDtoInterface getWorkTypeChangeRequestDto(Set<String> statuses) {
		return getRequestDto(workTypeChangeRequestDto, statuses);
	}
	
	@Override
	public DifferenceRequestDtoInterface getDifferenceRequestDto(boolean isCompleted) {
		return getDifferenceRequestDto(getCompletedOrAppliedStatuses(isCompleted));
	}
	
	@Override
	public DifferenceRequestDtoInterface getDifferenceRequestDto(Set<String> statuses) {
		return getRequestDto(differenceRequestDto, statuses);
	}
	
	/**
	 * 対象申請情報から、申請情報を取得する。<br>
	 * 対象申請情報が対象となる承認状況群に合致しない場合はnullを返す。<br>
	 * <br>
	 * @param dto      抽出対象申請情報
	 * @param statuses 対象となる承認状況群
	 * @return 申請情報
	 */
	protected <T extends WorkflowNumberDtoInterface> T getRequestDto(T dto, Set<String> statuses) {
		// 抽出対象申請情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// nullを取得
			return null;
		}
		// 対象ワークフローの承認状況が承認状況群に含まれる場合
		if (WorkflowUtility.isMatch(workflowMap.get(dto.getWorkflow()), statuses)) {
			// 申請情報を取得
			return dto;
		}
		// nullを取得(対象申請情報が対象となる承認状況群に合致しない場合)
		return null;
	}
	
	@Override
	public float calcWorkDays(boolean isCompleted) {
		// 全休の場合
		if (isAllHoliday(isCompleted)) {
			// 0を取得
			return 0F;
		}
		// 半休の場合
		if (isAmHoliday(isCompleted) || isPmHoliday(isCompleted)) {
			// 0.5を取得
			return TimeConst.HOLIDAY_TIMES_HALF;
		}
		// 1を取得(それ以外の場合)
		return 1F;
	}
	
	@Override
	public int calcWorkDaysForPaidHoliday(boolean isCompleted) {
		// 振出・休出申請(休日出勤)が存在する場合
		if (isWorkOnHolidaySubstituteOff(isCompleted)) {
			// 0を取得
			return 0;
		}
		// 1を取得(休日出勤でない場合)
		return 1;
	}
	
	@Override
	public int calcWorkOnHolidayTimes(boolean isCompleted) {
		// 振出・休出申請(休日出勤)が存在する場合
		if (isWorkOnHolidaySubstituteOff(isCompleted)) {
			// 1を取得
			return 1;
		}
		// 0を取得(休日出勤でない場合)
		return 0;
	}
	
	@Override
	public int calcWorkOnLegalHolidayTimes(boolean isCompleted) {
		// 法定休日出勤である場合
		if (isWorkOnLegal(isCompleted)) {
			// 1を取得
			return 1;
		}
		// 0を取得(法定休日出勤でない場合)
		return 0;
	}
	
	@Override
	public int calcWorkOnPrescribedHolidayTimes(boolean isCompleted) {
		// 所定休日出勤である場合
		if (isWorkOnPrescribed(isCompleted)) {
			// 1を取得
			return 1;
		}
		// 0を取得(所定休日出勤でない場合)
		return 0;
	}
	
	@Override
	public float calcPaidHolidayDays(boolean isCompleted) {
		// 休日出勤(振替申請しない)である場合
		if (isWorkOnHolidaySubstituteOff(isCompleted)) {
			// 0を取得(休暇申請(期間)と振出・休出申請(振替日なし)が同日にあり得る)
			return 0;
		}
		// 有給休暇日数を計算
		return TimeRequestUtility.totalPaidHolidayDays(getHolidayRequestList(isCompleted));
	}
	
	@Override
	public int calcPaidHolidayHours(boolean isCompleted) {
		// 有給休暇時間数(時間)を計算
		return TimeRequestUtility.totalPaidHolidayHours(getHolidayRequestList(isCompleted));
	}
	
	@Override
	public float calcStockHolidayDays(boolean isCompleted) {
		// 休日出勤(振替申請しない)である場合
		if (isWorkOnHolidaySubstituteOff(isCompleted)) {
			// 0を取得(休暇申請(期間)と振出・休出申請(振替日なし)が同日にあり得る)
			return 0;
		}
		// ストック休暇日数を計算
		return TimeRequestUtility.totalStockHolidayDays(getHolidayRequestList(isCompleted));
	}
	
	@Override
	public float calcSubHolidayDays(boolean isCompleted) {
		// 代休日数を計算
		return TimeRequestUtility.totalSubHolidayDays(getSubHolidayRequestList(isCompleted));
	}
	
	@Override
	public float calcLegalSubHolidayDays(boolean isCompleted) {
		// 法定代休日数を計算
		return TimeRequestUtility.totalLegalSubHolidayDays(getSubHolidayRequestList(isCompleted));
	}
	
	@Override
	public float calcPrescribedSubHolidayDays(boolean isCompleted) {
		// 所定代休日数を計算
		return TimeRequestUtility.totalPrescribedSubHolidayDays(getSubHolidayRequestList(isCompleted));
	}
	
	@Override
	public float calcNightSubHolidayDays(boolean isCompleted) {
		// 深夜代休日数を計算
		return TimeRequestUtility.totalNightSubHolidayDays(getSubHolidayRequestList(isCompleted));
	}
	
	@Override
	public float calcSpecialHolidayDays(boolean isCompleted) {
		// 休日出勤(振替申請しない)である場合
		if (isWorkOnHolidaySubstituteOff(isCompleted)) {
			// 0を取得(休暇申請(期間)と振出・休出申請(振替日なし)が同日にあり得る)
			return 0;
		}
		// 特別休暇日数を計算
		return TimeRequestUtility.totalSpecialHolidayDays(getHolidayRequestList(isCompleted));
	}
	
	@Override
	public int calcSpecialHolidayHours(boolean isCompleted) {
		// 特別休暇時間数(時間)を計算
		return TimeRequestUtility.totalSpecialHolidayHours(getHolidayRequestList(isCompleted));
	}
	
	@Override
	public float calcOtherHolidayDays(boolean isCompleted) {
		// 休日出勤(振替申請しない)である場合
		if (isWorkOnHolidaySubstituteOff(isCompleted)) {
			// 0を取得(休暇申請(期間)と振出・休出申請(振替日なし)が同日にあり得る)
			return 0;
		}
		// その他休暇日数を計算
		return TimeRequestUtility.totalOtherHolidayDays(getHolidayRequestList(isCompleted));
	}
	
	@Override
	public int calcOtherHolidayHours(boolean isCompleted) {
		// その他休暇時間数(時間)を計算
		return TimeRequestUtility.totalOtherHolidayHours(getHolidayRequestList(isCompleted));
	}
	
	@Override
	public float calcAbsenceDays(boolean isCompleted) {
		// 休日出勤(振替申請しない)である場合
		if (isWorkOnHolidaySubstituteOff(isCompleted)) {
			// 0を取得(休暇申請(期間)と振出・休出申請(振替日なし)が同日にあり得る)
			return 0;
		}
		// 欠勤日数を計算
		return TimeRequestUtility.totalAbsenceDays(getHolidayRequestList(isCompleted));
	}
	
	@Override
	public int calcAbsenceHours(boolean isCompleted) {
		// 欠勤時間数(時間)を計算
		return TimeRequestUtility.totalAbsenceHolidayHours(getHolidayRequestList(isCompleted));
	}
	
	/**
	 * 休暇(範囲)情報群に全休があるかを確認する。<br>
	 * @param dtos 休暇(範囲)情報群
	 * @return 確認結果(true：全休がある、false：ない)
	 */
	protected boolean hasAllHoliday(Collection<? extends HolidayRangeDtoInterface> dtos) {
		// 休暇(範囲)情報群に全休があるかを確認
		return TimeRequestUtility.hasHolidayRangeAll(dtos);
	}
	
	/**
	 * 休暇(範囲)情報群に前半休があるかを確認する。<br>
	 * @param dtos 休暇(範囲)情報群
	 * @return 確認結果(true：前半休がある、false：ない)
	 */
	protected boolean hasAmHoliday(Collection<? extends HolidayRangeDtoInterface> dtos) {
		// 休暇(範囲)情報群に前半休があるかを確認
		return TimeRequestUtility.hasHolidayRangeAm(dtos);
	}
	
	/**
	 * 休暇(範囲)情報群に後半休があるかを確認する。<br>
	 * @param dtos 休暇(範囲)情報群
	 * @return 確認結果(true：後半休がある、false：ない)
	 */
	protected boolean hasPmHoliday(Collection<? extends HolidayRangeDtoInterface> dtos) {
		// 休暇(範囲)情報群に後半休があるかを確認
		return TimeRequestUtility.hasHolidayRangePm(dtos);
	}
	
	/**
	 * 承認状況群を取得する。<br>
	 * @param isCompleted 承認済フラグ(true：承認済承認状況群を取得、false：申請済承認状況群を取得)
	 * @return 承認状況群
	 */
	protected Set<String> getCompletedOrAppliedStatuses(boolean isCompleted) {
		// 承認状況群を取得
		return WorkflowUtility.getCompletedOrAppliedStatuses(isCompleted);
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getTargetDate() {
		return CapsuleUtility.getDateClone(targetDate);
	}
	
	@Override
	public void setHolidayRequestList(List<HolidayRequestDtoInterface> holidayRequestList) {
		this.holidayRequestList = holidayRequestList;
	}
	
	@Override
	public void setOverTimeRequestList(List<OvertimeRequestDtoInterface> overtimeRequestList) {
		this.overtimeRequestList = overtimeRequestList;
	}
	
	@Override
	public void setSubstituteList(List<SubstituteDtoInterface> substituteList) {
		this.substituteList = substituteList;
	}
	
	@Override
	public void setSubHolidayRequestList(List<SubHolidayRequestDtoInterface> subHolidayRequestList) {
		this.subHolidayRequestList = subHolidayRequestList;
	}
	
	@Override
	public void setWorkOnHolidayRequestDto(WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto) {
		this.workOnHolidayRequestDto = workOnHolidayRequestDto;
	}
	
	@Override
	public void setDifferenceRequestDto(DifferenceRequestDtoInterface differenceRequestDto) {
		this.differenceRequestDto = differenceRequestDto;
	}
	
	@Override
	public void setWorkTypeChangeRequestDto(WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto) {
		this.workTypeChangeRequestDto = workTypeChangeRequestDto;
	}
	
	@Override
	public AttendanceDtoInterface getAttendanceDto() {
		return attendanceDto;
	}
	
	@Override
	public void setAttendanceDto(AttendanceDtoInterface attendanceDto) {
		this.attendanceDto = attendanceDto;
	}
	
	@Override
	public Map<Long, WorkflowDtoInterface> getWorkflowMap() {
		return workflowMap;
	}
	
	@Override
	public void setWorkflowMap(Map<Long, WorkflowDtoInterface> workflowMap) {
		this.workflowMap = workflowMap;
	}
	
	@Override
	public void setScheduledWorkTypeCode(String scheduledWorkTypeCode) {
		this.scheduledWorkTypeCode = scheduledWorkTypeCode;
	}
	
	@Override
	public void setSubstitutedWorkTypeCode(String substitutedWorkTypeCode) {
		this.substitutedWorkTypeCode = substitutedWorkTypeCode;
	}
	
}
