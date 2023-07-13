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
package jp.mosp.time.utils;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.comparator.settings.CorrectionDateComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.AttendanceListDto;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;

/**
 * 勤怠登録における有用なメソッドを提供する。<br><br>
 */
public class AttendanceUtility {
	
	/**
	 * 勤怠一覧区分(勤怠)。<br>
	 */
	public static final int			TYPE_LIST_ATTENDANCE		= 1;
	
	/**
	 * 勤怠一覧区分(実績)。<br>
	 */
	public static final int			TYPE_LIST_ACTUAL			= 2;
	
	/**
	 * 勤怠一覧区分(予定)。<br>
	 */
	public static final int			TYPE_LIST_SCHEDULE			= 3;
	
	/**
	 * 勤怠一覧区分(承認)。<br>
	 */
	public static final int			TYPE_LIST_APPROVAL			= 4;
	
	/**
	 * MosPアプリケーション設定キー(予定一覧申請適用)。<br>
	 */
	protected static final String	APP_SCHEDULE_APPLY_REQUEST	= "ScheduleApplyRequest";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private AttendanceUtility() {
		// 処理無し
	}
	
	/**
	 * 始業打刻から勤怠情報に登録する始業時刻(始業打刻用)を取得する。<br>
	 * <br>
	 * 1.直行の場合：始業予定時刻<br>
	 * 2.遅刻の場合：<br>
	 *   勤務予定時間表示設定が有効である場合：勤怠設定で丸めた打刻時刻<br>
	 *   勤務予定時間表示設定が無効である場合：勤怠設定で丸めた実打刻時刻<br>
	 * 3.勤務予定時間表示設定が有効である場合：勤務前残業を考慮した始業予定時刻<br>
	 * 4.その他の場合：勤怠設定で丸めた実打刻時刻<br>
	 * <br>
	 * @param applicationEntity 設定適用エンティティ
	 * @param requestEntity     申請エンティティ
	 * @param workTypeEntity    勤務形態エンティティ
	 * @param recordTime        打刻時刻
	 * @return 始業時刻(始業打刻用)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	public static Date getStartTimeForTimeRecord(ApplicationEntity applicationEntity,
			RequestEntityInterface requestEntity, WorkTypeEntityInterface workTypeEntity, Date recordTime)
			throws MospException {
		// 始業予定時刻を取得(勤務形態エンティティ及び申請エンティティから)
		Date scheduledTime = workTypeEntity.getStartTime(requestEntity);
		// 勤務前残業自動申請設定(有効の場合TRUE)
		boolean isAutoBeforeOverwork = false;
		
		WorkTypeItemDtoInterface workTypeItemDto = workTypeEntity.getWorkTypeItem(TimeConst.CODE_AUTO_BEFORE_OVERWORK);
		if (workTypeItemDto != null) {
			isAutoBeforeOverwork = workTypeItemDto.getPreliminary()
				.equals(String.valueOf(String.valueOf(MospConst.INACTIVATE_FLAG_OFF)));
		}
		// 残業申請(勤務前残業)の申請時間(分)を取得
		int overtimeMinutesBeforeWork = requestEntity.getOvertimeMinutesBeforeWork(false);
		// 勤務前残業自動申請が有効である場合
		if (isAutoBeforeOverwork) {
			// 勤怠申請情報で前残業申請情報設定
			// 勤務形態の始業時刻-startTimeIntを引いた時間を設定
			int beforeOvertimeInt = DateUtility.getHour(recordTime) * TimeConst.CODE_DEFINITION_HOUR;
			int scheduledTimeInt = DateUtility.getHour(scheduledTime) * TimeConst.CODE_DEFINITION_HOUR;
			overtimeMinutesBeforeWork = scheduledTimeInt - beforeOvertimeInt;
		}
		// 勤務前残業を考慮した始業予定時刻を取得
		Date overScheduledTime = DateUtility.addMinute(scheduledTime, -overtimeMinutesBeforeWork);
		// 勤怠設定情報を取得
		TimeSettingDtoInterface timeSettingDto = applicationEntity.getTimeSettingDto();
		// 1.直行の場合(勤務形態の直行設定を確認)
		if (workTypeEntity.isDirectStart()) {
			// 始業予定時刻を取得
			return scheduledTime;
		}
		// 2.遅刻の場合
		if (recordTime.compareTo(overScheduledTime) > 0) {
			// 勤務予定時間表示設定が有効で且つ、勤務前残業自動申請が無効の場合
			if (applicationEntity.useScheduledTime() && !isAutoBeforeOverwork) {
				// 勤怠設定で丸めた打刻時刻を取得
				return getRoundedStartTimeForTimeRecord(recordTime, timeSettingDto);
			}
			// 勤怠設定で丸めた実打刻時刻を取得
			return getRoundedActualStartTimeForTimeRecord(recordTime, timeSettingDto);
		}
		// 3.勤務予定時間表示設定が有効である場合、勤務前残業自動申請が無効の場合(勤怠設定の勤務予定時間表示設定を確認)
		if (applicationEntity.useScheduledTime() && !isAutoBeforeOverwork) {
			// 勤務前残業を考慮した始業予定時刻を取得
			return overScheduledTime;
		}
		// 4.その他(勤怠設定で丸めた実打刻時刻を取得)
		return getRoundedActualStartTimeForTimeRecord(recordTime, timeSettingDto);
	}
	
	/**
	 * 始業時刻(打刻丸め)を取得する。<br>
	 * 対象時刻を、日出勤丸め設定で丸める。<br>
	 * @param time  対象時刻
	 * @param dto   勤怠設定情報
	 * @return 始業時刻(打刻丸め)
	 */
	public static Date getRoundedStartTimeForTimeRecord(Date time, TimeSettingDtoInterface dto) {
		// 勤怠設定情報が存在しない場合
		if (dto == null) {
			// 1分単位切捨で丸め
			return getRoundedTimeForTimeRecord(time, 1, TimeConst.TYPE_ROUND_DOWN);
		}
		// 日退勤丸め設定で丸めた時刻(打刻用)を取得
		return getRoundedTimeForTimeRecord(time, dto.getRoundDailyStartUnit(), dto.getRoundDailyStart());
	}
	
	/**
	 * 実始業時刻(打刻丸め)を取得する。<br>
	 * 対象時刻を、日出勤丸め設定(分単位)で丸める。<br>
	 * @param time  対象時刻
	 * @param dto   勤怠設定情報
	 * @return 実始業時刻(打刻丸め)
	 */
	public static Date getRoundedActualStartTimeForTimeRecord(Date time, TimeSettingDtoInterface dto) {
		// 勤怠設定情報が存在しない場合
		if (dto == null) {
			// 1分単位切捨で丸め
			return getRoundedTimeForTimeRecord(time, 1, TimeConst.TYPE_ROUND_DOWN);
		}
		// 分単位に丸めた時刻(打刻用)を取得
		return getMinuteRoundedTimeForTimeRecord(time, dto.getRoundDailyStartUnit(), dto.getRoundDailyStart());
	}
	
	/**
	 * 終業時刻(打刻丸め)を取得する。<br>
	 * 対象時刻を、日退勤丸め設定で丸める。<br>
	 * @param time  対象時刻
	 * @param dto   勤怠設定情報
	 * @return 終業時刻(打刻丸め)
	 */
	public static Date getRoundedEndTimeForTimeRecord(Date time, TimeSettingDtoInterface dto) {
		// 勤怠設定情報が存在しない場合
		if (dto == null) {
			// 1分単位切捨で丸め
			return getRoundedTimeForTimeRecord(time, 1, TimeConst.TYPE_ROUND_DOWN);
		}
		// 日退勤丸め設定で丸めた時刻(打刻用)を取得
		return getRoundedTimeForTimeRecord(time, dto.getRoundDailyEndUnit(), dto.getRoundDailyEnd());
	}
	
	/**
	 * 実終業時刻(打刻丸め)を取得する。<br>
	 * 対象時刻を、日退勤丸め設定(分単位)で丸める。<br>
	 * @param time  対象時刻
	 * @param dto   勤怠設定情報
	 * @return 実終業時刻(打刻丸め)
	 */
	public static Date getRoundedActualEndTimeForTimeRecord(Date time, TimeSettingDtoInterface dto) {
		// 勤怠設定情報が存在しない場合
		if (dto == null) {
			// 1分単位切捨で丸め
			return getRoundedTimeForTimeRecord(time, 1, TimeConst.TYPE_ROUND_DOWN);
		}
		// 分単位に丸めた時刻(打刻用)を取得
		return getMinuteRoundedTimeForTimeRecord(time, dto.getRoundDailyEndUnit(), dto.getRoundDailyEnd());
	}
	
	/**
	 * 休憩入時刻(打刻丸め)を取得する。<br>
	 * @param time  対象時刻
	 * @param dto   勤怠設定情報
	 * @return 休憩入時刻(打刻丸め)
	 */
	public static Date getRoundedRestStartTimeForTimeRecord(Date time, TimeSettingDtoInterface dto) {
		// 勤怠設定情報が存在しない場合
		if (dto == null) {
			// 1分単位切捨で丸め
			return getRoundedTimeForTimeRecord(time, 1, TimeConst.TYPE_ROUND_DOWN);
		}
		// 分単位に丸めた時刻(打刻用)を取得
		return getMinuteRoundedTimeForTimeRecord(time, dto.getRoundDailyRestStartUnit(), dto.getRoundDailyRestStart());
	}
	
	/**
	 * 休憩戻時刻(打刻丸め)を取得する。<br>
	 * @param time  対象時刻
	 * @param dto   勤怠設定情報
	 * @return 休憩戻時刻(打刻丸め)
	 */
	public static Date getRoundedRestEndTimeForTimeRecord(Date time, TimeSettingDtoInterface dto) {
		// 勤怠設定情報が存在しない場合
		if (dto == null) {
			// 1分単位切捨で丸め
			return getRoundedTimeForTimeRecord(time, 1, TimeConst.TYPE_ROUND_DOWN);
		}
		// 分単位に丸めた時刻(打刻用)を取得
		return getMinuteRoundedTimeForTimeRecord(time, dto.getRoundDailyRestEndUnit(), dto.getRoundDailyRestEnd());
	}
	
	/**
	 * 私用外出入時刻(打刻丸め)を取得する。<br>
	 * @param time  対象時刻
	 * @param dto   勤怠設定情報
	 * @return 私用外出入時刻(打刻丸め)
	 */
	public static Date getRoundedPrivateStartTimeForTimeRecord(Date time, TimeSettingDtoInterface dto) {
		// 勤怠設定情報が存在しない場合
		if (dto == null) {
			// 1分単位切捨で丸め
			return getRoundedTimeForTimeRecord(time, 1, TimeConst.TYPE_ROUND_DOWN);
		}
		// 分単位に丸めた時刻(打刻用)を取得
		return getMinuteRoundedTimeForTimeRecord(time, dto.getRoundDailyPrivateStartUnit(),
				dto.getRoundDailyPrivateStart());
	}
	
	/**
	 * 私用外出戻時刻(打刻丸め)を取得する。<br>
	 * @param time  対象時刻
	 * @param dto   勤怠設定情報
	 * @return 私用外出戻時刻(打刻丸め)
	 */
	public static Date getRoundedPrivateEndTimeForTimeRecord(Date time, TimeSettingDtoInterface dto) {
		// 勤怠設定情報が存在しない場合
		if (dto == null) {
			// 1分単位切捨で丸め
			return getRoundedTimeForTimeRecord(time, 1, TimeConst.TYPE_ROUND_DOWN);
		}
		// 分単位に丸めた時刻(打刻用)を取得
		return getMinuteRoundedTimeForTimeRecord(time, dto.getRoundDailyPrivateEndUnit(),
				dto.getRoundDailyPrivateEnd());
	}
	
	/**
	 * 分単位に丸めた時刻(打刻用)を取得する。<br>
	 * 1分単位切上以外の場合は、全て1分単位切捨として扱う。<br>
	 * @param targetTime 対象時刻
	 * @param roundUnit  丸め単位
	 * @param roundType  丸め区分
	 * @return 分単位に丸めた時刻
	 */
	protected static Date getMinuteRoundedTimeForTimeRecord(Date targetTime, int roundUnit, int roundType) {
		// 1分単位切上の場合
		if (roundUnit == 1 && roundType == TimeConst.TYPE_ROUND_UP) {
			// 1分単位切上で丸め
			return getRoundedTimeForTimeRecord(targetTime, roundUnit, roundType);
		}
		// 1分単位切捨で丸め(1分単位切上でない場合)
		return getRoundedTimeForTimeRecord(targetTime, 1, TimeConst.TYPE_ROUND_DOWN);
	}
	
	/**
	 * 丸めた時刻(打刻用)を取得する。<br>
	 * 打刻用に1分未満の端数の扱いを考慮している。<br>
	 * @param targetTime 対象時刻
	 * @param roundUnit  丸め単位
	 * @param roundType  丸め区分
	 * @return 丸めた時刻
	 */
	protected static Date getRoundedTimeForTimeRecord(Date targetTime, int roundUnit, int roundType) {
		if (targetTime == null) {
			return null;
		}
		long milliseconds = targetTime.getTime();
		if (milliseconds <= 0) {
			return targetTime;
		}
		// 0ミリ秒より大きい場合
		int oneMinute = TimeConst.CODE_DEFINITION_HOUR * 1000;
		// 1分未満の端数
		long fraction = milliseconds % oneMinute;
		// 端数を切り捨てる
		milliseconds -= fraction;
		if (fraction > 0 && roundType == TimeConst.TYPE_ROUND_UP && roundUnit == 1) {
			// 端数が0ミリ秒より大きく且つ1分単位切上げの場合
			milliseconds += oneMinute;
		}
		if (roundType == TimeConst.TYPE_ROUND_NONE || roundUnit <= 0) {
			// 丸めた時刻を取得
			return new Date(milliseconds);
		}
		// 丸め単位を分単位からミリ秒単位に変換
		int millisecondsUnit = roundUnit * TimeConst.CODE_DEFINITION_HOUR * 1000;
		// 丸めた値(切捨て)を取得
		long rounded = milliseconds - (milliseconds % millisecondsUnit);
		// 切上げの場合
		if (roundType == TimeConst.TYPE_ROUND_UP && milliseconds % millisecondsUnit > 0) {
			// 切捨て値+丸め単位
			rounded += millisecondsUnit;
		}
		// 丸めた時刻を取得
		return new Date(rounded);
	}
	
	/**
	 * 勤怠データDTOの複製を作成する。
	 * @param dstDto 複製先勤怠データDTO、nullの場合は、関数内でインスタンスを作成
	 * @param srcDto 複製元勤怠データDTO
	 * @return 複製先勤怠データDTO
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	public static AttendanceDtoInterface getAttendanceDtoClone(AttendanceDtoInterface dstDto,
			AttendanceDtoInterface srcDto) throws MospException {
		// 複製元がnullの場合は、nullを返す
		if (srcDto == null) {
			return null;
		}
		// 複製先が用意されていなければインスタンスを作成
		if (dstDto == null) {
			dstDto = InstanceFactory.<AttendanceDtoInterface> simplifiedInstance(srcDto.getClass().getName());
		}
		// 継承元を複製
		getBaseDtoClone(dstDto, srcDto);
		// 全項目を複製
		dstDto.setTmdAttendanceId(srcDto.getTmdAttendanceId());
		dstDto.setPersonalId(srcDto.getPersonalId());
		dstDto.setWorkDate(srcDto.getWorkDate());
		dstDto.setTimesWork(srcDto.getTimesWork());
		dstDto.setWorkTypeCode(srcDto.getWorkTypeCode());
		dstDto.setDirectStart(srcDto.getDirectStart());
		dstDto.setDirectEnd(srcDto.getDirectEnd());
		dstDto.setForgotRecordWorkStart(srcDto.getForgotRecordWorkStart());
		dstDto.setNotRecordWorkStart(srcDto.getNotRecordWorkStart());
		dstDto.setStartTime(srcDto.getStartTime());
		dstDto.setActualStartTime(srcDto.getActualStartTime());
		dstDto.setEndTime(srcDto.getEndTime());
		dstDto.setActualEndTime(srcDto.getActualEndTime());
		dstDto.setLateDays(srcDto.getLateDays());
		dstDto.setLateThirtyMinutesOrMore(srcDto.getLateThirtyMinutesOrMore());
		dstDto.setLateLessThanThirtyMinutes(srcDto.getLateLessThanThirtyMinutes());
		dstDto.setLateTime(srcDto.getLateTime());
		dstDto.setActualLateTime(srcDto.getActualLateTime());
		dstDto.setLateThirtyMinutesOrMoreTime(srcDto.getLateThirtyMinutesOrMoreTime());
		dstDto.setLateLessThanThirtyMinutesTime(srcDto.getLateLessThanThirtyMinutesTime());
		dstDto.setLateReason(srcDto.getLateReason());
		dstDto.setLateCertificate(srcDto.getLateCertificate());
		dstDto.setLateComment(srcDto.getLateComment());
		dstDto.setLeaveEarlyDays(srcDto.getLeaveEarlyDays());
		dstDto.setLeaveEarlyThirtyMinutesOrMore(srcDto.getLeaveEarlyThirtyMinutesOrMore());
		dstDto.setLeaveEarlyLessThanThirtyMinutes(srcDto.getLeaveEarlyLessThanThirtyMinutes());
		dstDto.setLeaveEarlyTime(srcDto.getLeaveEarlyTime());
		dstDto.setActualLeaveEarlyTime(srcDto.getActualLeaveEarlyTime());
		dstDto.setLeaveEarlyThirtyMinutesOrMoreTime(srcDto.getLeaveEarlyThirtyMinutesOrMoreTime());
		dstDto.setLeaveEarlyLessThanThirtyMinutesTime(srcDto.getLeaveEarlyLessThanThirtyMinutesTime());
		dstDto.setLeaveEarlyReason(srcDto.getLeaveEarlyReason());
		dstDto.setLeaveEarlyCertificate(srcDto.getLeaveEarlyCertificate());
		dstDto.setLeaveEarlyComment(srcDto.getLeaveEarlyComment());
		dstDto.setWorkTime(srcDto.getWorkTime());
		dstDto.setGeneralWorkTime(srcDto.getGeneralWorkTime());
		dstDto.setWorkTimeWithinPrescribedWorkTime(srcDto.getWorkTimeWithinPrescribedWorkTime());
		dstDto.setContractWorkTime(srcDto.getContractWorkTime());
		dstDto.setShortUnpaid(srcDto.getShortUnpaid());
		dstDto.setRestTime(srcDto.getRestTime());
		dstDto.setOverRestTime(srcDto.getOverRestTime());
		dstDto.setNightRestTime(srcDto.getNightRestTime());
		dstDto.setLegalHolidayRestTime(srcDto.getLegalHolidayRestTime());
		dstDto.setPrescribedHolidayRestTime(srcDto.getPrescribedHolidayRestTime());
		dstDto.setPublicTime(srcDto.getPublicTime());
		dstDto.setPrivateTime(srcDto.getPrivateTime());
		dstDto.setMinutelyHolidayATime(srcDto.getMinutelyHolidayATime());
		dstDto.setMinutelyHolidayBTime(srcDto.getMinutelyHolidayBTime());
		dstDto.setMinutelyHolidayA(srcDto.getMinutelyHolidayA());
		dstDto.setMinutelyHolidayB(srcDto.getMinutelyHolidayB());
		dstDto.setTimesOvertime(srcDto.getTimesOvertime());
		dstDto.setOvertime(srcDto.getOvertime());
		dstDto.setOvertimeBefore(srcDto.getOvertimeBefore());
		dstDto.setOvertimeAfter(srcDto.getOvertimeAfter());
		dstDto.setOvertimeIn(srcDto.getOvertimeIn());
		dstDto.setOvertimeOut(srcDto.getOvertimeOut());
		dstDto.setWorkdayOvertimeIn(srcDto.getWorkdayOvertimeIn());
		dstDto.setWorkdayOvertimeOut(srcDto.getWorkdayOvertimeOut());
		dstDto.setPrescribedHolidayOvertimeIn(srcDto.getPrescribedHolidayOvertimeIn());
		dstDto.setPrescribedHolidayOvertimeOut(srcDto.getPrescribedHolidayOvertimeOut());
		dstDto.setLateNightTime(srcDto.getLateNightTime());
		dstDto.setNightWorkWithinPrescribedWork(srcDto.getNightWorkWithinPrescribedWork());
		dstDto.setNightOvertimeWork(srcDto.getNightOvertimeWork());
		dstDto.setNightWorkOnHoliday(srcDto.getNightWorkOnHoliday());
		dstDto.setSpecificWorkTime(srcDto.getSpecificWorkTime());
		dstDto.setLegalWorkTime(srcDto.getLegalWorkTime());
		dstDto.setDecreaseTime(srcDto.getDecreaseTime());
		dstDto.setTimeComment(srcDto.getTimeComment());
		dstDto.setRemarks(srcDto.getRemarks());
		dstDto.setWorkDays(srcDto.getWorkDays());
		dstDto.setWorkDaysForPaidLeave(srcDto.getWorkDaysForPaidLeave());
		dstDto.setTotalWorkDaysForPaidLeave(srcDto.getTotalWorkDaysForPaidLeave());
		dstDto.setTimesHolidayWork(srcDto.getTimesHolidayWork());
		dstDto.setTimesLegalHolidayWork(srcDto.getTimesLegalHolidayWork());
		dstDto.setTimesPrescribedHolidayWork(srcDto.getTimesPrescribedHolidayWork());
		dstDto.setPaidLeaveDays(srcDto.getPaidLeaveDays());
		dstDto.setPaidLeaveHours(srcDto.getPaidLeaveHours());
		dstDto.setStockLeaveDays(srcDto.getStockLeaveDays());
		dstDto.setCompensationDays(srcDto.getCompensationDays());
		dstDto.setLegalCompensationDays(srcDto.getLegalCompensationDays());
		dstDto.setPrescribedCompensationDays(srcDto.getPrescribedCompensationDays());
		dstDto.setNightCompensationDays(srcDto.getNightCompensationDays());
		dstDto.setSpecialLeaveDays(srcDto.getSpecialLeaveDays());
		dstDto.setSpecialLeaveHours(srcDto.getSpecialLeaveHours());
		dstDto.setOtherLeaveDays(srcDto.getOtherLeaveDays());
		dstDto.setOtherLeaveHours(srcDto.getOtherLeaveHours());
		dstDto.setAbsenceDays(srcDto.getAbsenceDays());
		dstDto.setAbsenceHours(srcDto.getAbsenceHours());
		dstDto.setGrantedLegalCompensationDays(srcDto.getGrantedLegalCompensationDays());
		dstDto.setGrantedPrescribedCompensationDays(srcDto.getGrantedPrescribedCompensationDays());
		dstDto.setGrantedNightCompensationDays(srcDto.getGrantedNightCompensationDays());
		dstDto.setLegalHolidayWorkTimeWithCompensationDay(srcDto.getLegalHolidayWorkTimeWithCompensationDay());
		dstDto.setLegalHolidayWorkTimeWithoutCompensationDay(srcDto.getLegalHolidayWorkTimeWithoutCompensationDay());
		dstDto
			.setPrescribedHolidayWorkTimeWithCompensationDay(srcDto.getPrescribedHolidayWorkTimeWithCompensationDay());
		dstDto.setPrescribedHolidayWorkTimeWithoutCompensationDay(
				srcDto.getPrescribedHolidayWorkTimeWithoutCompensationDay());
		dstDto.setOvertimeInWithCompensationDay(srcDto.getOvertimeInWithCompensationDay());
		dstDto.setOvertimeInWithoutCompensationDay(srcDto.getOvertimeInWithoutCompensationDay());
		dstDto.setOvertimeOutWithCompensationDay(srcDto.getOvertimeOutWithCompensationDay());
		dstDto.setOvertimeOutWithoutCompensationDay(srcDto.getOvertimeOutWithoutCompensationDay());
		dstDto.setStatutoryHolidayWorkTimeIn(srcDto.getStatutoryHolidayWorkTimeIn());
		dstDto.setStatutoryHolidayWorkTimeOut(srcDto.getStatutoryHolidayWorkTimeOut());
		dstDto.setPrescribedHolidayWorkTimeIn(srcDto.getPrescribedHolidayWorkTimeIn());
		dstDto.setPrescribedHolidayWorkTimeOut(srcDto.getPrescribedHolidayWorkTimeOut());
		dstDto.setWorkflow(srcDto.getWorkflow());
		
		return dstDto;
	}
	
	/**
	 * 休憩データDTOの複製を作成する。
	 * @param dstDto 複製先休憩データDTO、nullの場合は、関数内でインスタンスを作成
	 * @param srcDto 複製元休憩データDTO
	 * @return 複製先休憩データDTO
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	public static RestDtoInterface getRestDtoClone(RestDtoInterface dstDto, RestDtoInterface srcDto)
			throws MospException {
		// 複製元がnullの場合は、nullを返す
		if (srcDto == null) {
			return null;
		}
		// 複製先が用意されていなければインスタンスを作成
		if (dstDto == null) {
			dstDto = InstanceFactory.<RestDtoInterface> simplifiedInstance(srcDto.getClass().getName());
		}
		// 継承元を複製
		getBaseDtoClone(dstDto, srcDto);
		// 全項目を複製
		dstDto.setTmdRestId(srcDto.getTmdRestId());
		dstDto.setPersonalId(srcDto.getPersonalId());
		dstDto.setWorkDate(srcDto.getWorkDate());
		dstDto.setTimesWork(srcDto.getTimesWork());
		dstDto.setRest(srcDto.getRest());
		dstDto.setRestStart(srcDto.getRestStart());
		dstDto.setRestEnd(srcDto.getRestEnd());
		dstDto.setRestTime(srcDto.getRestTime());
		
		return dstDto;
	}
	
	/**
	 * 外出データDTOの複製を作成する。
	 * @param dstDto 複製先外出データDTO、nullの場合は、関数内でインスタンスを作成
	 * @param srcDto 複製元外出データDTO
	 * @return 複製先外出データDTO
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	public static GoOutDtoInterface getGoOutDtoClone(GoOutDtoInterface dstDto, GoOutDtoInterface srcDto)
			throws MospException {
		// 複製元がnullの場合は、nullを返す
		if (srcDto == null) {
			return null;
		}
		// 複製先が用意されていなければインスタンスを作成
		if (dstDto == null) {
			dstDto = InstanceFactory.<GoOutDtoInterface> simplifiedInstance(srcDto.getClass().getName());
		}
		// 継承元を複製
		getBaseDtoClone(dstDto, srcDto);
		// 全項目を複製
		dstDto.setTmdGoOutId(srcDto.getTmdGoOutId());
		dstDto.setPersonalId(srcDto.getPersonalId());
		dstDto.setWorkDate(srcDto.getWorkDate());
		dstDto.setTimesWork(srcDto.getTimesWork());
		dstDto.setGoOutType(srcDto.getGoOutType());
		dstDto.setTimesGoOut(srcDto.getTimesGoOut());
		dstDto.setGoOutStart(srcDto.getGoOutStart());
		dstDto.setGoOutEnd(srcDto.getGoOutEnd());
		dstDto.setGoOutTime(srcDto.getGoOutTime());
		
		return dstDto;
	}
	
	/**
	 * 共通DTOの複製を作成する。
	 * @param dstDto 複製先共通DTO、nullの場合は、関数内でインスタンスを作成
	 * @param srcDto 複製元共通DTO
	 * @return 複製先共通DTO
	 * @throws MospException インスタンスの生成に失敗した場合
	 */
	public static BaseDtoInterface getBaseDtoClone(BaseDtoInterface dstDto, BaseDtoInterface srcDto)
			throws MospException {
		// 複製元がnullの場合は、nullを返す
		if (srcDto == null) {
			return null;
		}
		// 複製先が用意されていなければインスタンスを作成
		if (dstDto == null) {
			dstDto = InstanceFactory.<BaseDtoInterface> simplifiedInstance(srcDto.getClass().getName());
		}
		// 全項目を複製
		dstDto.setDeleteFlag(srcDto.getDeleteFlag());
		dstDto.setInsertDate(srcDto.getInsertDate());
		dstDto.setInsertUser(srcDto.getInsertUser());
		dstDto.setUpdateDate(srcDto.getUpdateDate());
		dstDto.setUpdateUser(srcDto.getUpdateUser());
		
		return dstDto;
	}
	
	/**
	 * 勤怠一覧情報群から対象日の勤怠一覧情報を取得する。<br>
	 * @param dtos       勤怠一覧情報群
	 * @param targetDate 対象日
	 * @return 勤怠一覧情報
	 */
	public static AttendanceListDto getAttendanceListDto(Collection<AttendanceListDto> dtos, Date targetDate) {
		// 勤怠一覧情報リストから対象日の勤怠一覧情報を取得
		for (AttendanceListDto dto : dtos) {
			// 勤務日確認
			if (targetDate.equals(dto.getWorkDate())) {
				return dto;
			}
		}
		return null;
	}
	
	/**
	 * 勤怠一覧情報に備考を追加する。<br>
	 * @param dto    追加対象勤怠一覧情報
	 * @param remark 追加備考
	 */
	public static void addRemark(AttendanceListDto dto, String remark) {
		// 備考が空である場合
		if (MospUtility.isEmpty(remark)) {
			// 処理終了
			return;
		}
		// 追加備考重複確認
		if (dto.getRemark() != null && dto.getRemark().indexOf(remark) >= 0) {
			// 追加する備考が既に設定されている場合
			return;
		}
		// 勤怠一覧情報の備考に追加
		dto.setRemark(MospUtility.concat(dto.getRemark(), remark));
	}
	
	/**
	 * 勤務予定時間表示に合わせて始終業時刻(表示用)を再設定する。<br>
	 * @param mospParams  MosP処理情報
	 * @param attendList  勤怠一覧情報リスト
	 * @param timeSetting 勤怠設定エンティティ
	 */
	public static void resetStartEndTimeString(MospParams mospParams, List<AttendanceListDto> attendList,
			TimeSettingEntityInterface timeSetting) {
		// 勤務予定時間表示が有効である場合
		if (timeSetting.isScheduledTimeAvailable()) {
			// 処理無し
			return;
		}
		// 勤怠一覧区分を取得
		int listType = MospUtility.getLastValue(attendList).getListType();
		// 勤怠一覧区分が勤怠でない場合
		if (AttendanceUtility.isListTypeAttendance(listType) == false) {
			// 処理無し
			return;
		}
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendList) {
			// 始終業時刻の色が設定されていない場合(状態が予定ではない場合)
			if (MospUtility.isEmpty(dto.getStartTimeStyle())) {
				// 次の勤怠一覧情報へ
				continue;
			}
			// 始終業時刻(表示用)を再設定(状態が予定である場合)
			dto.setStartTimeString(PfNameUtility.hyphen(mospParams));
			dto.setEndTimeString(PfNameUtility.hyphen(mospParams));
		}
	}
	
	/**
	 * 修正日時が最新である勤怠修正情報を取得する。<br>
	 * 勤怠修正情報リストは修正日時でソートされる。<br>
	 * 同じ修正日時の勤怠修正情報が複数ある場合でも、最初の勤怠修正情報のみを取得する。<br>
	 * <br>
	 * @param dtos 勤怠修正情報リスト
	 * @return 修正日時が最新である勤怠修正情報
	 * @throws MospException クラスのインスタンスの生成に失敗した場合
	 */
	public static AttendanceCorrectionDtoInterface getLatestCorrection(
			Collection<AttendanceCorrectionDtoInterface> dtos) throws MospException {
		// 修正日時が最新である勤怠修正情報を取得
		return MospUtility.getFirstValue(dtos, CorrectionDateComparator.class, true);
	}
	
	/**
	 * 勤怠修正マーク(空文字か本か他)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param dtos       勤怠修正情報群
	 * @return 勤怠修正マーク(空文字か本か他)
	 * @throws MospException クラスのインスタンスの生成に失敗した場合
	 */
	public static String getCorrectionMark(MospParams mospParams, List<AttendanceCorrectionDtoInterface> dtos)
			throws MospException {
		// 修正日時が最新である勤怠修正情報を取得し勤怠修正マーク(空文字か本か他)を取得
		return getCorrectionMark(mospParams, getLatestCorrection(dtos));
	}
	
	/**
	 * 勤怠修正マーク(空文字か本か他)を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param dto        勤怠修正情報
	 * @return 勤怠修正マーク(空文字か本か他)
	 */
	public static String getCorrectionMark(MospParams mospParams, AttendanceCorrectionDtoInterface dto) {
		// 勤怠修正情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 空文字を取得
			return MospConst.STR_EMPTY;
		}
		// 勤怠修正者が本人である場合
		if (MospUtility.isEqual(dto.getPersonalId(), dto.getCorrectionPersonalId())) {
			// 本を取得
			return TimeNamingUtility.selfCorrectAbbr(mospParams);
		}
		// 他を取得
		return TimeNamingUtility.otherCorrectAbbr(mospParams);
	}
	
	/**
	 * 勤怠未申請日付リストを取得する。<br>
	 * 勤怠一覧情報の勤怠入力用チェックボックス要否で、勤怠未申請かどうかを判断する。<br>
	 * @param attendList 勤怠一覧情報リスト
	 * @return 勤怠未申請日付リスト
	 */
	public static List<Date> getAttendanceAppliableDates(List<AttendanceListDto> attendList) {
		// 勤怠未申請日付リストを準備
		List<Date> dates = new ArrayList<Date>();
		// 勤怠一覧情報毎に処理
		for (AttendanceListDto dto : attendList) {
			// 勤怠入力用チェックボックス有りの場合
			if (dto.isNeedCheckbox()) {
				// 勤怠未申請日付リストに設定(勤怠未申請と判断)
				dates.add(dto.getWorkDate());
			}
		}
		// 勤怠未申請日付リストを取得
		return dates;
	}
	
	/**
	 * 対象となる承認状況群を取得する。<br>
	 * @param listType 勤怠一覧区分
	 * @return 対象となる承認状況群
	 */
	public static Set<String> getStatusesForListType(int listType) {
		// 勤怠一覧区分毎に処理
		switch (listType) {
			// 勤怠及び承認の場合
			case TYPE_LIST_ATTENDANCE:
			case TYPE_LIST_APPROVAL:
				// 有効な承認状況群(下書と取下以外)を取得
				return WorkflowUtility.getEffectiveStatuses();
			// 実績及び予定の場合
			case TYPE_LIST_ACTUAL:
			case TYPE_LIST_SCHEDULE:
				// 承認済承認状況群を取得
				return WorkflowUtility.getCompletedStatuses();
			// それ以外の場合
			default:
				// 空の承認状況群を取得
				return Collections.emptySet();
		}
	}
	
	/**
	 * 勤怠一覧情報作成時に勤怠情報を考慮するかを確認する。<br>
	 * @param dto      勤怠一覧情報
	 * @param workflow ワークフロー情報(勤怠情報)
	 * @return 確認結果(true：勤怠一覧情報作成時に勤怠情報を考慮する、false：考慮しない)
	 */
	public static boolean isAttendanceConsideredForAttendList(AttendanceListDto dto, WorkflowDtoInterface workflow) {
		// 勤怠一覧区分が予定である場合
		if (isTheListType(dto, TYPE_LIST_SCHEDULE)) {
			// 勤怠一覧情報作成時に勤怠情報を考慮しないと判断
			return false;
		}
		// 勤怠一覧区分が実績で承認済でない場合(実績は承認済のみ)
		if (isTheListType(dto, TYPE_LIST_ACTUAL) && WorkflowUtility.isCompleted(workflow) == false) {
			// 勤怠一覧情報作成時に勤怠情報を考慮しないと判断
			return false;
		}
		// 勤怠一覧区分が承認で下書である場合(承認は下書以外)
		if (isTheListType(dto, TYPE_LIST_APPROVAL) && WorkflowUtility.isDraft(workflow)) {
			// 勤怠一覧情報作成時に勤怠情報を考慮しないと判断
			return false;
		}
		// 勤怠一覧情報作成時に勤怠情報を考慮すると判断
		return true;
	}
	
	/**
	 * 勤怠一覧区分が勤怠一覧区分(配列)のいずれかであるかを確認する。<br>
	 * 勤怠一覧情報に設定されている勤怠一覧区分で、判断する。<br>
	 * @param dto       勤怠一覧情報
	 * @param listTypes 勤怠一覧区分(配列)
	 * @return 確認結果(true：勤怠一覧区分が勤怠一覧区分配列のいずれかである、false：そうでない)
	 */
	public static boolean isTheListType(AttendanceListDto dto, int... listTypes) {
		// 勤怠一覧情報か勤怠一覧区分(配列)がnullである場合
		if (MospUtility.isEmpty(dto, listTypes)) {
			// 勤怠一覧区分が勤怠一覧区分配列のいずれかでないと判断
			return false;
		}
		// 勤怠一覧区分毎に処理
		for (int listType : listTypes) {
			// 勤怠一覧区分が同じである場合
			if (dto.getListType() == listType) {
				// 勤怠一覧区分が勤怠一覧区分配列のいずれかであると判断
				return true;
			}
		}
		// 勤怠一覧区分が勤怠一覧区分配列のいずれかでないと判断
		return false;
	}
	
	/**
	 * 勤怠一覧区分が勤怠であるかを確認する。<br>
	 * @param listType 勤怠一覧区分
	 * @return 確認結果(true：勤怠である、false：勤怠でない)
	 */
	public static boolean isListTypeAttendance(int listType) {
		// 勤怠一覧区分が勤怠であるかを確認
		return listType == TYPE_LIST_ATTENDANCE;
	}
	
	/**
	 * 勤怠一覧区分が実績であるかを確認する。<br>
	 * @param listType 勤怠一覧区分
	 * @return 確認結果(true：実績である、false：実績でない)
	 */
	public static boolean isListTypeActual(int listType) {
		// 勤怠一覧区分が実績であるかを確認
		return listType == TYPE_LIST_ACTUAL;
	}
	
	/**
	 * 勤怠一覧区分が予定であるかを確認する。<br>
	 * @param listType 勤怠一覧区分
	 * @return 確認結果(true：予定である、false：予定でない)
	 */
	public static boolean isListTypeSchedule(int listType) {
		// 勤怠一覧区分が予定であるかを確認
		return listType == TYPE_LIST_SCHEDULE;
	}
	
	/**
	 * 勤怠一覧区分が承認であるかを確認する。<br>
	 * @param listType 勤怠一覧区分
	 * @return 確認結果(true：承認である、false：承認でない)
	 */
	public static boolean isListTypeApproval(int listType) {
		// 勤怠一覧区分が予定であるかを確認
		return listType == TYPE_LIST_APPROVAL;
	}
	
	/**
	 * 予定一覧表示に申請情報を適用するか否かを確認する。<br>
	 * 設定がされていない場合は、falseを返す。<br>
	 * @param mospParams MosP処理情報
	 * @return 確認結果(true：予定一覧表示に申請情報を適用する、false：予定一覧表示に申請情報を適用しない)
	 */
	public static boolean isScheduleApplyRequest(MospParams mospParams) {
		// 予定一覧表示に申請情報を適用するか否かを確認
		return mospParams.getApplicationPropertyBool(APP_SCHEDULE_APPLY_REQUEST);
	}
	
	/**
	 * ポータルパラメータキー(ポータル画面勤怠一覧アドオン列項目名)を取得する。<br>
	 * @param className 勤怠一覧追加処理クラス名
	 * @return ポータルパラメータキー(ポータル画面勤怠一覧アドオン列項目名)
	 */
	public static String getPortalParamAddonColumnNameKey(String className) {
		// ポータルパラメータキー(ポータル画面勤怠一覧アドオン列項目名)を取得
		return new StringBuilder(className).append(TimeConst.PORTAL_PARAM_KEY_COLUMN_NAME).toString();
	}
	
	/**
	 * ポータルパラメータキー(ポータル画面勤怠一覧アドオン列項目値)を取得する。<br>
	 * @param className 勤怠一覧追加処理クラス名
	 * @return ポータルパラメータキー(ポータル画面勤怠一覧アドオン列項目値)
	 */
	public static String getPortalParamAddonColumnValueKey(String className) {
		// ポータルパラメータキー(ポータル画面勤怠一覧アドオン列項目値)を取得
		return new StringBuilder(className).append(TimeConst.PORTAL_PARAM_KEY_COLUMN_VALUE).toString();
	}
	
	/**
	 * ポータルパラメータキー(ポータル画面勤怠一覧アドオン列項目クラス文字)を取得する。<br>
	 * @param className 勤怠一覧追加処理クラス名
	 * @return ポータルパラメータキー(ポータル画面勤怠一覧アドオン列項目クラス文字)
	 */
	public static String getPortalParamAddonColumnClassKey(String className) {
		// ポータルパラメータキー(ポータル画面勤怠一覧アドオン列項目クラス文字)を取得
		return new StringBuilder(className).append(TimeConst.PORTAL_PARAM_KEY_COLUMN_CLASS).toString();
	}
	
}
