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
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.entity.RequestEntity;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.utils.TimeRequestUtility;

/**
 * 申請ユーティリティ。<br>
 */
public class RequestUtilBean extends TimeBean implements RequestUtilBeanInterface {
	
	/**
	 * 休暇申請参照クラス。
	 */
	protected HolidayRequestReferenceBeanInterface			holidayRequestRefer;
	
	/**
	 * 残業申請参照クラス。
	 */
	protected OvertimeRequestReferenceBeanInterface			overtimeRequestRefer;
	
	/**
	 * 休日出勤申請参照クラス。
	 */
	protected WorkOnHolidayRequestReferenceBeanInterface	workOnHolidayRefer;
	
	/**
	 * 振替休日申請参照クラス。
	 */
	protected SubstituteReferenceBeanInterface				substituteRefer;
	
	/**
	 * 代休申請参照クラス。
	 */
	protected SubHolidayRequestReferenceBeanInterface		subHolidayRequestRefer;
	
	/**
	 * 時差出勤申請参照クラス。
	 */
	protected DifferenceRequestReferenceBeanInterface		differenceRequestRefer;
	
	/**
	 * 勤務形態変更申請参照クラス。
	 */
	protected WorkTypeChangeRequestReferenceBeanInterface	workTypeChangeRequestRefer;
	
	/**
	 * 勤怠データ参照クラス。<br>
	 */
	protected AttendanceReferenceBeanInterface				attendanceReference;
	
	/**
	 * ワークフロー情報参照クラス。
	 */
	protected WorkflowReferenceBeanInterface				workflowReference;
	
	/**
	 * ワークフロー統合クラス。
	 */
	protected WorkflowIntegrateBeanInterface				workflowIntegrate;
	
	/**
	 * カレンダユーティリティクラス。
	 */
	protected ScheduleUtilBeanInterface						scheduleUtil;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface						timeMaster;
	
	/**
	 * 休暇申請情報リスト。
	 */
	protected List<HolidayRequestDtoInterface>				holidayRequestList;
	
	/**
	 * 残業申請情報リスト。
	 */
	protected List<OvertimeRequestDtoInterface>				overTimeRequestList;
	
	/**
	 * 振替休日情報リスト。
	 */
	protected List<SubstituteDtoInterface>					substituteList;
	
	/**
	 * 代休申請情報リスト。
	 */
	protected List<SubHolidayRequestDtoInterface>			subHolidayRequestList;
	
	/**
	 * 休日出勤情報。
	 */
	protected WorkOnHolidayRequestDtoInterface				workOnHolidayDto;
	
	/**
	 * 時差出勤情報。
	 */
	protected DifferenceRequestDtoInterface					differenceDto;
	
	/**
	 * 勤務形態変更情報。
	 */
	protected WorkTypeChangeRequestDtoInterface				workTypeChangeDto;
	
	/**
	 * 勤怠情報。
	 */
	protected AttendanceDtoInterface						attendanceDto;
	
	/**
	 * ワークフローマップ
	 */
	protected Map<Long, WorkflowDtoInterface>				workflowMap;
	
	/**
	 * 振替日の予定勤務形態。
	 */
	protected String										substitutedWorkTypeCode;
	
	/**
	 * カレンダの予定勤務形態。
	 */
	protected String										scheduledWorkTypeCode;
	
	/**
	 * 休暇範囲(午前休かつ午後休)。
	 */
	protected static final int								CODE_HOLIDAY_RANGE_AM_PM	= 5;
	
	
	/**
	 * {@link RequestUtilBean}を生成する。<br>
	 */
	public RequestUtilBean() {
		// 継承基の処理を実行
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 各種クラスの取得
		holidayRequestRefer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		workOnHolidayRefer = createBeanInstance(WorkOnHolidayRequestReferenceBeanInterface.class);
		substituteRefer = createBeanInstance(SubstituteReferenceBeanInterface.class);
		overtimeRequestRefer = createBeanInstance(OvertimeRequestReferenceBeanInterface.class);
		differenceRequestRefer = createBeanInstance(DifferenceRequestReferenceBeanInterface.class);
		workTypeChangeRequestRefer = createBeanInstance(WorkTypeChangeRequestReferenceBeanInterface.class);
		attendanceReference = createBeanInstance(AttendanceReferenceBeanInterface.class);
		workflowReference = createBeanInstance(WorkflowReferenceBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		subHolidayRequestRefer = createBeanInstance(SubHolidayRequestReferenceBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
	}
	
	@Override
	public void setRequests(String personalId, Date targetDate) throws MospException {
		// 休暇申請リスト取得
		holidayRequestList = holidayRequestRefer.getHolidayRequestList(personalId, targetDate);
		// 休日出勤申請情報取得
		workOnHolidayDto = workOnHolidayRefer.findForKeyOnWorkflow(personalId, targetDate);
		// 振替休日データリスト取得
		substituteList = substituteRefer.getSubstituteList(personalId, targetDate);
		// 残業申請情報リスト取得
		overTimeRequestList = overtimeRequestRefer.getOvertimeRequestList(personalId, targetDate, targetDate);
		// 代休申請情報取得
		subHolidayRequestList = subHolidayRequestRefer.getSubHolidayRequestList(personalId, targetDate);
		// 時差出勤申請情報取得
		differenceDto = differenceRequestRefer.findForKeyOnWorkflow(personalId, targetDate);
		// 勤務形態変更申請情報取得
		workTypeChangeDto = workTypeChangeRequestRefer.findForKeyOnWorkflow(personalId, targetDate);
		// 勤怠情報取得
		attendanceDto = attendanceReference.findForKey(personalId, targetDate);
		// ワークフロー情報初期化
		workflowMap = new HashMap<Long, WorkflowDtoInterface>();
		// カレンダの予定勤務形態を初期化
		scheduledWorkTypeCode = null;
	}
	
	@Override
	public List<SubstituteDtoInterface> getSubstituteList(boolean status) throws MospException {
		// リスト準備
		List<SubstituteDtoInterface> completedSubstitute = new ArrayList<SubstituteDtoInterface>();
		// 情報リストがない場合
		if (substituteList == null || substituteList.isEmpty()) {
			return completedSubstitute;
		}
		// 振替申請リスト毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(substituteDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// 振替申請が承認済の場合
			if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedSubstitute.add(substituteDto);
			}
			// 下書でなく取下でない場合
			if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedSubstitute.add(substituteDto);
			}
		}
		// 承認済の振替申請リスト
		return completedSubstitute;
	}
	
	@Override
	public List<OvertimeRequestDtoInterface> getOverTimeList(boolean status) throws MospException {
		// リスト準備
		List<OvertimeRequestDtoInterface> completedOvertime = new ArrayList<OvertimeRequestDtoInterface>();
		// 情報リストがない場合
		if (overTimeRequestList == null || overTimeRequestList.isEmpty()) {
			return completedOvertime;
		}
		// 残業申請リスト毎に処理
		for (OvertimeRequestDtoInterface overtimeDto : overTimeRequestList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(overtimeDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// 残業申請が承認済の場合
			if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedOvertime.add(overtimeDto);
			}
			// 下書でなく取下でない場合
			if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedOvertime.add(overtimeDto);
			}
		}
		// 承認済の残業申請リスト
		return completedOvertime;
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> getSubHolidayList(boolean status) throws MospException {
		// リスト準備
		List<SubHolidayRequestDtoInterface> completedsubHoliday = new ArrayList<SubHolidayRequestDtoInterface>();
		// 情報リストがない場合
		if (subHolidayRequestList == null || subHolidayRequestList.isEmpty()) {
			return completedsubHoliday;
		}
		// 代休申請リスト毎に処理
		for (SubHolidayRequestDtoInterface subHolidayDto : subHolidayRequestList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(subHolidayDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// 代休申請が承認済の場合
			if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedsubHoliday.add(subHolidayDto);
			}
			// 下書でなく取下でない場合
			if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedsubHoliday.add(subHolidayDto);
			}
		}
		// 承認済の代休申請リスト
		return completedsubHoliday;
	}
	
	@Override
	public List<HolidayRequestDtoInterface> getHolidayList(boolean status) throws MospException {
		// リスト準備
		List<HolidayRequestDtoInterface> completedHoliday = new ArrayList<HolidayRequestDtoInterface>();
		// 情報リストがない場合
		if (holidayRequestList == null || holidayRequestList.isEmpty()) {
			return completedHoliday;
		}
		// 休暇申請リスト毎に処理
		for (HolidayRequestDtoInterface holidayDto : holidayRequestList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(holidayDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// 休暇申請が承認済の場合
			if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedHoliday.add(holidayDto);
			}
			// 下書でなく取下でない場合
			if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// リストに追加
				completedHoliday.add(holidayDto);
			}
		}
		// 承認済の休暇申請リスト
		return completedHoliday;
	}
	
	@Override
	public DifferenceRequestDtoInterface getDifferenceDto(boolean status) throws MospException {
		// 時差出勤申請情報がない場合
		if (differenceDto == null) {
			return null;
		}
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(differenceDto.getWorkflow());
		if (workflowDto == null) {
			return null;
		}
		// 承認済の場合
		if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
			return differenceDto;
		}
		// 下書でなく取下でない場合
		if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
				&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			return differenceDto;
		}
		return null;
	}
	
	@Override
	public WorkTypeChangeRequestDtoInterface getWorkTypeChangeDto(boolean status) throws MospException {
		// 勤務形態変更申請情報がない場合
		if (workTypeChangeDto == null) {
			return null;
		}
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(workTypeChangeDto.getWorkflow());
		if (workflowDto == null) {
			return null;
		}
		// 承認済の場合
		if (status && workflowIntegrate.isCompleted(workflowDto)) {
			return workTypeChangeDto;
		}
		// 下書でなく取下でない場合
		if (!status && !workflowIntegrate.isDraft(workflowDto) && !workflowIntegrate.isWithDrawn(workflowDto)) {
			return workTypeChangeDto;
		}
		return null;
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface getWorkOnHolidayDto(boolean status) throws MospException {
		// 休日出勤申請情報がない場合
		if (workOnHolidayDto == null) {
			return null;
		}
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(workOnHolidayDto.getWorkflow());
		if (workflowDto == null) {
			return null;
		}
		// 承認済の場合
		if (status && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
			return workOnHolidayDto;
		}
		// 下書でなく取下でない場合
		if (!status && !PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
				&& !PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			return workOnHolidayDto;
		}
		return null;
	}
	
	@Override
	public AttendanceDtoInterface getDraftAttendance() throws MospException {
		// 勤怠情報がない場合
		if (attendanceDto == null) {
			return null;
		}
		// 下書の場合
		if (workflowIntegrate.isDraft(attendanceDto.getWorkflow())) {
			return attendanceDto;
		}
		return null;
	}
	
	@Override
	public AttendanceDtoInterface getFirstRevertedAttendance() throws MospException {
		// 勤怠情報がない場合
		if (attendanceDto == null) {
			return null;
		}
		// 1次戻の場合
		if (workflowIntegrate.isFirstReverted(attendanceDto.getWorkflow())) {
			return attendanceDto;
		}
		return null;
	}
	
	@Override
	public AttendanceDtoInterface getApplicatedAttendance() throws MospException {
		// 勤怠情報がない場合
		if (attendanceDto == null) {
			return null;
		}
		// ワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(attendanceDto.getWorkflow());
		// 取下の場合
		if (workflowIntegrate.isWithDrawn(workflowDto)) {
			return null;
		}
		// 下書の場合
		if (workflowIntegrate.isDraft(workflowDto)) {
			return null;
		}
		// 1次戻の場合
		if (workflowIntegrate.isFirstReverted(workflowDto)) {
			return null;
		}
		return attendanceDto;
	}
	
	@Override
	public AttendanceDtoInterface getAttendance() {
		// 勤怠申請情報を取得
		return attendanceDto;
	}
	
	@Override
	public HolidayRequestDtoInterface getCompletedHolidayRangeAll() throws MospException {
		// 承認済休暇情報取得
		List<HolidayRequestDtoInterface> getCompletedHolidayList = getHolidayList(true);
		// 情報が存在しない場合
		if (getCompletedHolidayList.isEmpty()) {
			return null;
		}
		// 承認済休暇情報毎に処理
		for (HolidayRequestDtoInterface holidayDto : getCompletedHolidayList) {
			// 全休の場合
			if (holidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				return holidayDto;
			}
		}
		return null;
	}
	
	@Override
	public SubHolidayRequestDtoInterface getCompletedSubHolidayRangeAll() throws MospException {
		// 承認済休暇情報取得
		List<SubHolidayRequestDtoInterface> getCompletedSubHolidayList = getSubHolidayList(true);
		// 情報が存在しない場合
		if (getCompletedSubHolidayList.isEmpty()) {
			return null;
		}
		// 承認済休暇情報毎に処理
		for (SubHolidayRequestDtoInterface subHolidayDto : getCompletedSubHolidayList) {
			// 全休の場合
			if (subHolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				return subHolidayDto;
			}
		}
		return null;
	}
	
	@Override
	public SubstituteDtoInterface getCompletedSubstituteRangeAll() throws MospException {
		// 承認済休暇情報取得
		List<SubstituteDtoInterface> getCompletedSubstituteList = getSubstituteList(true);
		// 情報が存在しない場合
		if (getCompletedSubstituteList.isEmpty()) {
			return null;
		}
		// 承認済休暇情報毎に処理
		for (SubstituteDtoInterface substituteDto : getCompletedSubstituteList) {
			// 承認済休日出勤申請がない場合
			if (getWorkOnHolidayDto(true) == null) {
				// 全休の場合
				if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					return substituteDto;
				}
			}
		}
		return null;
	}
	
	@Override
	public boolean isHolidayAllDay(boolean status) throws MospException {
		// 午前休・午後休フラグ準備
		boolean amFlag = false;
		boolean pmFlag = false;
		// 休暇申請情報群を取得
		List<HolidayRequestDtoInterface> holidays = getHolidayList(status);
		// 休暇申請情報群に全休(前半休+後半休も含む)がある場合
		if (TimeRequestUtility.isAllRangeHoliday(holidays)) {
			// 処理終了
			return true;
		}
		// 範囲毎の休暇申請有無を取得
		amFlag = TimeRequestUtility.hasHolidayRangeAm(holidays);
		pmFlag = TimeRequestUtility.hasHolidayRangePm(holidays);
		// 振替申請休暇範囲取得
		List<SubstituteDtoInterface> statusSubstituteList = getSubstituteList(status);
		int rangeSubstitute = checkHolidayRangeSubstitute(statusSubstituteList);
		// 対象日が振替日の場合
		if (rangeSubstitute != 0) {
			// 全休又は午前休午後休の場合
			if (rangeSubstitute == TimeConst.CODE_HOLIDAY_RANGE_ALL || rangeSubstitute == CODE_HOLIDAY_RANGE_AM_PM) {
				return true;
			}
			// 午前休の場合
			if (rangeSubstitute == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				amFlag = true;
			}
			// 午後休の場合
			if (rangeSubstitute == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				pmFlag = true;
			}
		}
		// 代休申請休暇範囲取得
		List<SubHolidayRequestDtoInterface> statusSubHolidayList = getSubHolidayList(status);
		int rangeSubHoliday = checkHolidayRangeSubHoliday(statusSubHolidayList);
		// 全休又は午前休午後休の場合
		if (rangeSubHoliday == TimeConst.CODE_HOLIDAY_RANGE_ALL || rangeSubHoliday == CODE_HOLIDAY_RANGE_AM_PM) {
			return true;
		}
		// 午前休の場合
		if (rangeSubHoliday == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			amFlag = true;
		}
		// 午後休の場合
		if (rangeSubHoliday == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			pmFlag = true;
		}
		// 振替出勤申請休日範囲取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = getWorkOnHolidayDto(status);
		if (workOnHolidayRequestDto != null) {
			int substitute = workOnHolidayRequestDto.getSubstitute();
			// 休日出勤の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				return false;
			}
			// 半日振替出勤(午前)の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
				pmFlag = true;
			}
			// 半日振替出勤(午後)の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
				amFlag = true;
			}
		}
		// 全休の場合
		if (amFlag && pmFlag) {
			return true;
		}
		return false;
	}
	
	/**
	 * 半休と時間単位休暇を同日に取得した場合に時間単位休暇であると判断される。<br>
	 * そのため、当メソッドは一部のユーザモジュールでのみ用いられる。<br>
	 */
	@Override
	public int checkHolidayRangeHoliday(List<HolidayRequestDtoInterface> holidayRequestList) {
		// 休暇範囲準備
		int range = 0;
		// 情報リストがない場合
		if (holidayRequestList.isEmpty()) {
			return range;
		}
		// 休暇申請情報リスト毎に処理
		for (HolidayRequestDtoInterface HolidayDto : holidayRequestList) {
			// 全休の場合
			if (HolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				return TimeConst.CODE_HOLIDAY_RANGE_ALL;
			}
			// 午前休の場合
			if (HolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午前休かつ午後休
					return CODE_HOLIDAY_RANGE_AM_PM;
				}
				// 午前休
				range = TimeConst.CODE_HOLIDAY_RANGE_AM;
				continue;
			}
			// 午後休の場合
			if (HolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休かつ午後休
					return CODE_HOLIDAY_RANGE_AM_PM;
				}
				// 午後休
				range = TimeConst.CODE_HOLIDAY_RANGE_PM;
			}
			// 時間休の場合
			if (HolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				return TimeConst.CODE_HOLIDAY_RANGE_TIME;
			}
		}
		return range;
	}
	
	@Override
	public int checkHolidayRangeSubstitute(List<SubstituteDtoInterface> substituteList) throws MospException {
		// 休暇範囲準備
		int range = 0;
		// 承認済リストがない場合
		if (substituteList.isEmpty()) {
			return range;
		}
		
		// 情報リスト毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			// 全休の場合
			if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				range = TimeConst.CODE_HOLIDAY_RANGE_ALL;
				break;
			}
			// 午前休の場合
			if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午前休かつ午後休
					range = CODE_HOLIDAY_RANGE_AM_PM;
					break;
				}
				// 午前休
				range = TimeConst.CODE_HOLIDAY_RANGE_AM;
				continue;
			}
			// 午後休の場合
			if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休かつ午後休
					range = CODE_HOLIDAY_RANGE_AM_PM;
					break;
				}
				// 午後休
				range = TimeConst.CODE_HOLIDAY_RANGE_PM;
			}
		}
		// 更に承認済の振替又は休日出勤がされている場合
		// 休日出勤申請情報で対象承認状況の情報を取得
		WorkOnHolidayRequestDtoInterface workOnHolidayDto = getWorkOnHolidayDto(true);
		// 振替の振替でない場合
		if (workOnHolidayDto == null) {
			return range;
		}
		// 振替の振替の範囲
		int poneRange = workOnHolidayDto.getSubstitute();
		// 休日出勤申請があり、振替(全日)又は休日出勤の場合
		if (poneRange == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
				|| poneRange == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF
				|| poneRange == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
			return 0;
		}
		// 振替出勤（午前）の場合
		if (poneRange == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
			switch (range) {
				// 全休の場合
				
				case TimeConst.CODE_HOLIDAY_RANGE_ALL:
				case CODE_HOLIDAY_RANGE_AM_PM:
					// 午後休
					range = TimeConst.CODE_HOLIDAY_RANGE_PM;
					break;
				// 午前休の場合
				case TimeConst.CODE_HOLIDAY_RANGE_AM:
					// 休みなし
					range = 0;
					break;
				default:
					break;
			}
		}
		// 振替出勤（午後）の場合
		if (poneRange == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			switch (range) {
				// 全休の場合
				case TimeConst.CODE_HOLIDAY_RANGE_ALL:
				case CODE_HOLIDAY_RANGE_AM_PM:
					// 午前休
					range = TimeConst.CODE_HOLIDAY_RANGE_AM;
					break;
				// 午後休の場合
				case TimeConst.CODE_HOLIDAY_RANGE_PM:
					// 休みなし
					range = 0;
					break;
				default:
					break;
			}
		}
		
		return range;
	}
	
	@Override
	public int checkHolidayRangeSubHoliday(List<SubHolidayRequestDtoInterface> subHolidayList) {
		// 休暇範囲準備
		int range = 0;
		// 承認済リストがない場合
		if (subHolidayList.isEmpty()) {
			return range;
		}
		// 代休申請情報リスト毎に処理
		for (SubHolidayRequestDtoInterface subHolidayDto : subHolidayList) {
			// 全休の場合
			if (subHolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				return TimeConst.CODE_HOLIDAY_RANGE_ALL;
			}
			// 午前休の場合
			if (subHolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午前休かつ午後休
					return CODE_HOLIDAY_RANGE_AM_PM;
				}
				// 午前休
				range = TimeConst.CODE_HOLIDAY_RANGE_AM;
				continue;
			}
			if (subHolidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休かつ午後休も申請されている場合
				if (range == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前休かつ午後休
					return CODE_HOLIDAY_RANGE_AM_PM;
				}
				// 午後休
				range = TimeConst.CODE_HOLIDAY_RANGE_PM;
			}
		}
		return range;
	}
	
	@Override
	public RequestEntityInterface getRequestEntity(String personalId, Date targetDate) throws MospException {
		// 申請エンティティを作成
		RequestEntity requestEntity = (RequestEntity)createRequestEntity(personalId, targetDate);
		// 申請日の予定勤務形態を取得及び設定
		requestEntity.setScheduledWorkTypeCode(getScheduledWorkTypeCode(personalId, targetDate));
		// 申請エンティティを取得
		return requestEntity;
	}
	
	/**
	 * 対象個人IDと対象日の申請エンティティを作成する。<br>
	 * <br>
	 * 但し、カレンダの予定勤務形態は、設定されていない。<br>
	 * <br>
	 * @param personalId 対象個人ID
	 * @param targetDate 対象日
	 * @return 申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected RequestEntityInterface createRequestEntity(String personalId, Date targetDate) throws MospException {
		// 申請エンティティを準備
		RequestEntityInterface requestEntity = createObject(RequestEntityInterface.class);
		requestEntity.setPersonalId(personalId);
		requestEntity.setTargetDate(targetDate);
		// 申請情報取得
		setRequests(personalId, targetDate);
		// 申請情報設定
		requestEntity.setHolidayRequestList(holidayRequestList);
		requestEntity.setWorkOnHolidayRequestDto(workOnHolidayDto);
		requestEntity.setSubstituteList(substituteList);
		requestEntity.setOverTimeRequestList(overTimeRequestList);
		requestEntity.setSubHolidayRequestList(subHolidayRequestList);
		requestEntity.setDifferenceRequestDto(differenceDto);
		requestEntity.setWorkTypeChangeRequestDto(workTypeChangeDto);
		requestEntity.setAttendanceDto(attendanceDto);
		// ワークフロー情報取得
		requestEntity.setWorkflowMap(getWorkflowMap());
		// 振替出勤日の予定勤務形態を取得及び設定
		requestEntity.setSubstitutedWorkTypeCode(getSubstitutedWorkTypeCode());
		// 申請エンティティを取得
		return requestEntity;
	}
	
	/**
	 * 申請日の予定勤務形態を取得及び設定を行う。<br>
	 * 申請日の予定勤務形態を取得できない場合は、空白を取得する。<br>
	 * その際、エラーメッセージは付加しない。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @return 申請日の予定勤務形態
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException {
		// 存在する場合
		if (scheduledWorkTypeCode != null) {
			return scheduledWorkTypeCode;
		}
		// 勤務形態コードを取得
		scheduledWorkTypeCode = scheduleUtil.getScheduledWorkTypeCodeNoMessage(personalId, targetDate);
		return scheduledWorkTypeCode;
	}
	
	/**
	 * 振替休日の予定勤務形態を取得する。<br>
	 * <br>
	 * 振出・休出申請(振替申請：全日か午前か午後)により
	 * 振り替えた出勤日の予定勤務形態コードを取得する。<br>
	 * <br>
	 * 振出・休出申請情報が存在しない場合は、空文字を返す。<br>
	 * <br>
	 * @return 振替出勤日の予定勤務形態
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getSubstitutedWorkTypeCode() throws MospException {
		// 存在する場合
		if (substitutedWorkTypeCode != null) {
			return substitutedWorkTypeCode;
		}
		// 初期化
		substitutedWorkTypeCode = "";
		// 振出・休出申請が存在しない場合
		if (workOnHolidayDto == null) {
			// 空文字を取得
			return substitutedWorkTypeCode;
		}
		// 振替申請フラグと休出種別確認を取得
		int substitute = workOnHolidayDto.getSubstitute();
		String workOnHolidayType = workOnHolidayDto.getWorkOnHolidayType();
		// 休日出勤の場合
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 法定休出の場合
			if (workOnHolidayType.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
				// 法定休日出勤を取得
				substitutedWorkTypeCode = TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY;
				return substitutedWorkTypeCode;
			}
			// 所定休出の場合
			if (workOnHolidayType.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
				// 所定休日出勤を取得
				substitutedWorkTypeCode = TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY;
				return substitutedWorkTypeCode;
			}
		}
		// 振替出勤(勤務形態変更)の場合
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
			// 振替勤務形態コードを取得
			substitutedWorkTypeCode = workOnHolidayDto.getWorkTypeCode();
			return substitutedWorkTypeCode;
		}
		// 振替出勤(全休か午前か午後)の場合
		// 個人ID取得
		String personalId = workOnHolidayDto.getPersonalId();
		// 振替出勤日取得
		Date workDate = workOnHolidayDto.getRequestDate();
		// 振替出勤日で振替休日情報取得
		SubstituteDtoInterface substituteDto = substituteRefer.getSubstituteDto(personalId, workDate);
		// 振替休日情報確認
		if (substituteDto != null) {
			// 振替日を取得
			Date substituteDate = substituteDto.getSubstituteDate();
			// 振替日の予定勤務形態を取得
			substitutedWorkTypeCode = scheduleUtil.getScheduledWorkTypeCode(personalId, substituteDate);
			return substitutedWorkTypeCode;
		}
		return substitutedWorkTypeCode;
	}
	
	/**
	 * ワークフロー情報群を取得する。<br>
	 * 設定されている申請情報のワークフロー番号につき、ワークフロー情報を取得する。<br>
	 * <br>
	 * @return ワークフロー情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<Long, WorkflowDtoInterface> getWorkflowMap() throws MospException {
		// 存在する場合
		if (workflowMap != null && !workflowMap.isEmpty()) {
			return workflowMap;
		}
		// ワークフロー情報群の準備
		workflowMap = new HashMap<Long, WorkflowDtoInterface>();
		// 申請毎にワークフロー情報を取得してワークフロー情報群に設定
		// 休暇申請リスト取得
		for (HolidayRequestDtoInterface dto : holidayRequestList) {
			long workflow = dto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 休日出勤申請情報取得
		if (workOnHolidayDto != null) {
			long workflow = workOnHolidayDto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 振替休日データリスト取得
		for (SubstituteDtoInterface dto : substituteList) {
			long workflow = dto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 残業申請情報リスト取得
		for (OvertimeRequestDtoInterface dto : overTimeRequestList) {
			long workflow = dto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 代休申請情報取得
		for (SubHolidayRequestDtoInterface dto : subHolidayRequestList) {
			long workflow = dto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 時差出勤申請情報取得
		if (differenceDto != null) {
			long workflow = differenceDto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 勤務形態変更申請情報取得
		if (workTypeChangeDto != null) {
			long workflow = workTypeChangeDto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		// 勤怠情報取得
		if (attendanceDto != null) {
			long workflow = attendanceDto.getWorkflow();
			workflowMap.put(workflow, workflowReference.getLatestWorkflowInfo(workflow));
		}
		return workflowMap;
	}
	
	@Override
	public WorkTypeEntityInterface getWorkTypeEntity(String personalId, Date targetDate) throws MospException {
		// 申請エンティティを取得
		RequestEntityInterface request = getRequestEntity(personalId, targetDate);
		// 承認済承認状況群を準備
		Set<String> statuses = WorkflowUtility.getCompletedStatuses();
		// 予定される勤務形態を取得(承認済の申請を考慮)
		String workTypeCode = request.getWorkType(true, statuses);
		// 時差出勤申請情報を取得(承認済)
		DifferenceRequestDtoInterface differenceRequest = request.getDifferenceRequestDto(statuses);
		// 勤務形態エンティティを取得
		return timeMaster.getWorkTypeEntity(workTypeCode, targetDate, differenceRequest);
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		// 勤怠関連マスタ参照処理を設定
		timeMaster = this.timeMaster;
		// カレンダユーティリティに勤怠関連マスタ参照処理を設定
		scheduleUtil.setTimeMaster(timeMaster);
	}
	
}
