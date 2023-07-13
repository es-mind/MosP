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
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.AttendanceBean;
import jp.mosp.time.bean.AttendanceCalcBeanInterface;
import jp.mosp.time.bean.AttendanceCorrectionReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceCorrectionRegistBeanInterface;
import jp.mosp.time.bean.AttendanceListRegistBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.GoOutReferenceBeanInterface;
import jp.mosp.time.bean.GoOutRegistBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.RestReferenceBeanInterface;
import jp.mosp.time.bean.RestRegistBeanInterface;
import jp.mosp.time.bean.SubHolidayRegistBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.RestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceCorrectionDtoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.input.action.AttendanceCardAction;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠一覧登録処理。<br>
 */
public class AttendanceListRegistBean extends AttendanceBean implements AttendanceListRegistBeanInterface {
	
	/**
	 * 勤怠データ登録クラス。
	 */
	protected AttendanceRegistBeanInterface					attendanceRegist;
	
	/**
	 * 勤怠修正データ参照クラス。
	 */
	protected AttendanceCorrectionReferenceBeanInterface	attendanceCorrectionReference;
	
	/**
	 * 勤怠修正データ登録クラス。
	 */
	protected AttendanceCorrectionRegistBeanInterface		attendanceCorrectionRegist;
	
	/**
	 * 休憩データ登録クラス。
	 */
	protected RestRegistBeanInterface						restRegist;
	
	/**
	 * 休憩データDAOインターフェース
	 */
	protected RestDaoInterface								restDao;
	
	/**
	 * 外出データ登録クラス。
	 */
	protected GoOutRegistBeanInterface						goOutRegist;
	
	/**
	 * 代休データ登録クラス。
	 */
	protected SubHolidayRegistBeanInterface					subHolidayRegist;
	
	/**
	 * 時差出勤申請DTOインターフェース
	 */
	protected DifferenceRequestDtoInterface					differenceDto;
	
	/**
	 * ワークフロー登録クラス。
	 */
	protected WorkflowRegistBeanInterface					workflowRegist;
	
	/**
	 * 対象個人ID。<br>
	 * インターフェースに定義されたメソッドの最初で設定される。<br>
	 */
	protected String										personalId;
	
	/**
	 * 対象日。<br>
	 * インターフェースに定義されたメソッドにおける日毎処理の最初で設定される。<br>
	 */
	protected Date											targetDate;
	
	/**
	 * 設定適用エンティティ。<br>
	 * 勤怠データ新規作成時に取得及び設定をされる。<br>
	 */
	protected ApplicationEntity								application;
	
	/**
	 * カレンダ日情報。<br>
	 * 勤怠データ新規作成時に取得及び設定をされる。<br>
	 */
	protected ScheduleDateDtoInterface						scheduleDateDto;
	
	/**
	 * 勤務形態マスタ情報。<br>
	 * 勤怠データ新規作成時に取得及び設定をされる。<br>
	 */
	protected WorkTypeDtoInterface							workTypeDto;
	
	/**
	 * 勤怠データ休憩情報参照インターフェース。
	 */
	protected RestReferenceBeanInterface					restReference;
	
	/**
	 * 勤怠データ外出情報参照インターフェース。
	 */
	protected GoOutReferenceBeanInterface					goOutReference;
	
	/**
	 * 勤怠トランザクション登録クラス。
	 */
	protected AttendanceTransactionRegistBeanInterface		attendanceTransactionRegist;
	
	/**
	 * 勤怠関連マスタ参照クラス。<br>
	 */
	protected TimeMasterBeanInterface						timeMaster;
	
	
	@Override
	public void initBean() throws MospException {
		// 継承元クラスのメソッドを実行
		super.initBean();
		// 各種クラス準備
		attendanceRegist = createBeanInstance(AttendanceRegistBeanInterface.class);
		attendanceCorrectionReference = createBeanInstance(AttendanceCorrectionReferenceBeanInterface.class);
		attendanceCorrectionRegist = createBeanInstance(AttendanceCorrectionRegistBeanInterface.class);
		restRegist = createBeanInstance(RestRegistBeanInterface.class);
		restDao = createDaoInstance(RestDaoInterface.class);
		goOutRegist = createBeanInstance(GoOutRegistBeanInterface.class);
		subHolidayRegist = createBeanInstance(SubHolidayRegistBeanInterface.class);
		workflowRegist = createBeanInstance(WorkflowRegistBeanInterface.class);
		restReference = createBeanInstance(RestReferenceBeanInterface.class);
		goOutReference = createBeanInstance(GoOutReferenceBeanInterface.class);
		attendanceTransactionRegist = createBeanInstance(AttendanceTransactionRegistBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		// 勤怠関連マスタ参照処理を設定
		attendanceRegist.setTimeMaster(timeMaster);
		scheduleUtil.setTimeMaster(timeMaster);
		attendanceTransactionRegist.setTimeMaster(timeMaster);
	}
	
	@Override
	public void draft(String personalId, String[] targetDates, String[] startTimes, String[] endTimes,
			boolean deleteRest, boolean useWorkTypeChangeRequest, boolean useSchedule) throws MospException {
		// 対象個人ID設定
		this.personalId = personalId;
		// 日付リスト取得
		List<Date> targetDateList = getDateList(targetDates);
		// 始業時刻リスト取得
		List<Date> startTimeList = getAttendanceTimeList(targetDateList, startTimes);
		// 終業時刻リスト取得
		List<Date> endTimeList = getAttendanceTimeList(targetDateList, endTimes);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// エラーメッセージリスト
		List<String> errorMessageList = new ArrayList<String>();
		// 対象日毎に勤怠情報を作成して登録
		for (int i = 0; i < targetDateList.size(); i++) {
			// 対象日付設定
			targetDate = targetDateList.get(i);
			// 勤怠データ取得
			AttendanceDtoInterface dto = getAttendanceDto(startTimeList.get(i), startTimeList.get(i),
					endTimeList.get(i), deleteRest, false, true, useWorkTypeChangeRequest, useSchedule);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				errorMessageList.addAll(mospParams.getErrorMessageList());
				mospParams.getErrorMessageList().clear();
				continue;
			}
			// 妥当性チェック
			attendanceRegist.checkValidate(dto);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				errorMessageList.addAll(mospParams.getErrorMessageList());
				mospParams.getErrorMessageList().clear();
				continue;
			}
			
			// 申請の相関チェック
			attendanceRegist.checkDraft(dto);
			
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				errorMessageList.addAll(mospParams.getErrorMessageList());
				mospParams.getErrorMessageList().clear();
				continue;
			}
			// 勤怠データ登録
			attendanceRegist.regist(dto);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				errorMessageList.addAll(mospParams.getErrorMessageList());
				mospParams.getErrorMessageList().clear();
				continue;
			}
		}
		if (!errorMessageList.isEmpty()) {
			mospParams.getErrorMessageList().addAll(errorMessageList);
		}
	}
	
	@Override
	public void apply(String personalId, String[] targetDates, String[] startTimes, String[] endTimes)
			throws MospException {
		// 対象個人ID設定
		this.personalId = personalId;
		// 日付リスト取得
		List<Date> targetDateList = getDateList(targetDates);
		// 始業時刻リスト取得
		List<Date> startTimeList = getAttendanceTimeList(targetDateList, startTimes);
		// 終業時刻リスト取得
		List<Date> endTimeList = getAttendanceTimeList(targetDateList, endTimes);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠情報を準備
		AttendanceDtoInterface dto = null;
		// エラーメッセージリスト
		List<String> errorMessageList = new ArrayList<String>();
		// 対象日毎に勤怠情報を作成して登録
		for (int i = 0; i < targetDateList.size(); i++) {
			// 対象日付設定
			targetDate = targetDateList.get(i);
			// 始業時刻及び終業時刻を取得
			Date startTime = startTimeList.get(i);
			Date endTime = endTimeList.get(i);
			// 勤怠データ取得
			dto = getAttendanceDto(startTime, startTime, endTime, false, true, true, false, false);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				errorMessageList.addAll(mospParams.getErrorMessageList());
				mospParams.getErrorMessageList().clear();
				continue;
			}
			// 勤怠登録確認チェック
			attendanceRegist.checkValidate(dto);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				errorMessageList.addAll(mospParams.getErrorMessageList());
				mospParams.getErrorMessageList().clear();
				continue;
			}
			// 申請の相関チェック
			attendanceRegist.checkAppli(dto);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				errorMessageList.addAll(mospParams.getErrorMessageList());
				mospParams.getErrorMessageList().clear();
				continue;
			}
			// 始業・終業必須チェック
			attendanceRegist.checkTimeExist(dto);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				errorMessageList.addAll(mospParams.getErrorMessageList());
				mospParams.getErrorMessageList().clear();
				continue;
			}
			// 休日出勤時の休憩時間を確認
			checkRestForWornOnHoliday(dto);
			// 勤怠データ登録
			attendanceRegist.regist(dto);
			// 代休データを登録する。
			registSubHoliday(dto);
			// 勤怠トランザクション登録
			attendanceTransactionRegist.regist(dto);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				errorMessageList.addAll(mospParams.getErrorMessageList());
				mospParams.getErrorMessageList().clear();
				continue;
			}
		}
		if (!errorMessageList.isEmpty()) {
			mospParams.getErrorMessageList().addAll(errorMessageList);
		}
		// 勤怠申請後処理群を実行
		afterApplyAttendance(dto);
	}
	
	/**
	 * 休日出勤時の休憩時間を確認する。<br>
	 * @param dto
	 */
	protected void checkRestForWornOnHoliday(AttendanceDtoInterface dto) {
		// 勤務形態が所定休出もしくは法定休出の場合、休憩時間チェックを行う
		if (TimeUtility.isWorkOnLegalOrPrescribedHoliday(dto.getWorkTypeCode()) == false) {
			// 確認不要
			return;
		}
		//勤務時間が6時間を超えるかつ休憩時間が0分の場合
		if (dto.getWorkTime() > TimeConst.TIME_WORK_TIME_6 && dto.getRestTime() == 0) {
			// 既に連続実行コマンドが設定されている場合
			if (MospUtility.isEmpty(mospParams.getNextCommand()) == false) {
				// 連続実行コマンド及びメッセージ設定不要
				return;
			}
			// MosP処理情報に対象個人IDを設定
			mospParams.addGeneralParam(PlatformConst.PRM_TARGET_PERSONAL_ID, personalId);
			// MosP処理情報に対象日を設定
			mospParams.addGeneralParam(PlatformConst.PRM_TARGET_DATE, targetDate);
			// 勤怠詳細画面へ遷移(連続実行コマンド設定)
			mospParams.setNextCommand(AttendanceCardAction.CMD_SELECT_SHOW_FROM_PORTAL);
			// エラーメッセージを設定
			TimeMessageUtility.addErrorShortRestTimeForWorkOnHoliday(mospParams);
		}
	}
	
	/**
	 * 登録用勤怠データを取得する。<br>
	 * 対象日における勤怠データが存在する場合は、始業時刻及び終業時刻を設定して返す。<br>
	 * <br>
	 * 存在しない場合は、新規に勤怠データを作成し、始業時刻及び終業時刻を設定する。<br>
	 * 勤怠データは、設定適用から各種情報を取得し、作成する。<br>
	 * 勤怠休憩情報、勤怠外出情報が勤務形態に設定されている場合は、これらを登録する。<br>
	 * <br>
	 * 設定適用情報等が存在せず勤怠データを作成できない場合は、nullを返す。<br>
	 * <br>
	 * @param startTime 始業時刻
	 * @param actualStartTime 実始業時刻
	 * @param endTime   終業時刻
	 * @param deleteRest 休憩を削除する場合はtrue、しない場合はfalse
	 * @param isAppli 申請の場合はtrue、下書の場合はfalse
	 * @param registWorkflow ワークフロー登録する場合はtrue、しない場合はfalse
	 * @param useWorkTypeChangeRequest 勤務形態変更申請を利用する場合true、そうでない場合false
	 * @param useSchedule カレンダを利用する場合true、そうでない場合false
	 * @return 登録用勤怠データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected AttendanceDtoInterface getAttendanceDto(Date startTime, Date actualStartTime, Date endTime,
			boolean deleteRest, boolean isAppli, boolean registWorkflow, boolean useWorkTypeChangeRequest,
			boolean useSchedule) throws MospException {
		AttendanceCalcBeanInterface attendanceCalc = (AttendanceCalcBeanInterface)createBean(
				AttendanceCalcBeanInterface.class, targetDate);
		// 勤怠データ新規作成に必要な情報を取得及び設定
		setTimeDtos(useWorkTypeChangeRequest, useSchedule);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤怠データ取得(個人ID及び日付で取得)
		AttendanceDtoInterface oldDto = attendanceReference.findForKey(personalId, targetDate);
		if (oldDto == null) {
			oldDto = attendanceRegist.getInitDto();
		}
		AttendanceDtoInterface dto = attendanceReference.findForKey(personalId, targetDate);
		if (dto == null) {
			// 勤怠データを新規に取得
			dto = attendanceRegist.getInitDto();
			// 個人ID設定
			dto.setPersonalId(personalId);
			// 勤務日設定
			dto.setWorkDate(targetDate);
			// 勤務回数設定
			dto.setTimesWork(TIMES_WORK_DEFAULT);
			int checkboxOff = Integer.parseInt(MospConst.CHECKBOX_OFF);
			// その他項目の初期化
			dto.setDirectStart(checkboxOff);
			dto.setDirectEnd(checkboxOff);
			dto.setForgotRecordWorkStart(checkboxOff);
			dto.setNotRecordWorkStart(checkboxOff);
			dto.setTimeComment("");
			dto.setRemarks("");
			dto.setLateReason("");
			dto.setLateCertificate("");
			dto.setLateComment("");
			dto.setLeaveEarlyReason("");
			dto.setLeaveEarlyCertificate("");
			dto.setLeaveEarlyComment("");
		}
		// 勤務形態設定
		dto.setWorkTypeCode(workTypeDto.getWorkTypeCode());
		if (differenceDto != null) {
			dto.setWorkTypeCode(differenceDto.getDifferenceType());
		}
		// 始業時刻設定
		dto.setStartTime(startTime);
		// 実始業時刻設定
		dto.setActualStartTime(actualStartTime);
		// 終業時刻設定
		dto.setEndTime(endTime);
		// 実終業時刻設定
		dto.setActualEndTime(endTime);
		// 始業終業時刻自動計算
		attendanceCalc.calcStartEndTime(dto, true);
		boolean isWorkflowDraft = false;
		if (dto.getWorkflow() != 0) {
			// 既にワークフロー番号が設定されている場合
			WorkflowDtoInterface workflowDto = workflow.getLatestWorkflowInfo(dto.getWorkflow());
			if (workflowDto == null || workflow.isDraft(workflowDto)) {
				isWorkflowDraft = true;
			}
		}
		if (registWorkflow) {
			// ワークフロー登録
			if (isAppli) {
				// 申請の場合
				apply(dto);
			} else {
				// 下書の場合
				draft(dto);
			}
		}
		// 休憩時間
		List<RestDtoInterface> oldRestList = restReference.getRestList(personalId, targetDate, dto.getTimesWork());
		// 公用外出時間
		List<GoOutDtoInterface> oldPublicGoOutList = goOutReference.getPublicGoOutList(personalId, targetDate);
		// 私用外出時間
		List<GoOutDtoInterface> oldPrivateGoOutList = goOutReference.getPrivateGoOutList(personalId, targetDate);
		// 休憩時間登録
		if (deleteRest) {
			// 削除
			restRegist.delete(personalId, targetDate, TIMES_WORK_DEFAULT);
		} else {
			// 登録
			registRest(dto.getStartTime(), dto.getEndTime(), oldRestList);
		}
		// 公用外出時間登録
		registPublicGoOut(dto.getStartTime(), dto.getEndTime());
		// 私用外出時間登録
		registPrivateGoOut(dto.getStartTime(), dto.getEndTime());
		// 勤怠データ自動計算
		attendanceCalc.attendanceCalc(dto);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		if (isAppli) {
			List<RestDtoInterface> restList = restReference.getRestList(personalId, targetDate, dto.getTimesWork());
			List<GoOutDtoInterface> publicList = goOutReference.getPublicGoOutList(personalId, targetDate);
			List<GoOutDtoInterface> privateList = goOutReference.getPrivateGoOutList(personalId, targetDate);
			// 休暇申請時間休確認
			attendanceRegist.checkHolidayTime(personalId, dto.getWorkDate(), restList, publicList, privateList);
			// 私用外出チェック
			attendanceRegist.checkPrivateGoOut(dto, restList, privateList);
			if (mospParams.hasErrorMessage()) {
				return null;
			}
			// 修正履歴を登録
			if (isWorkflowDraft) {
				registCorrection(dto, attendanceRegist.getInitDto(), new ArrayList<RestDtoInterface>(),
						new ArrayList<GoOutDtoInterface>(), new ArrayList<GoOutDtoInterface>());
			} else {
				registCorrection(dto, oldDto, oldRestList, oldPublicGoOutList, oldPrivateGoOutList);
			}
		}
		return dto;
	}
	
	/**
	 * 対象個人IDの対象日における勤怠データを新規作成するのに必要な
	 * 各種情報を、取得及び設定する。<br>
	 * @param useWorkTypeChangeRequest 勤務形態変更申請を利用する場合true、そうでない場合false
	 * @param useSchedule カレンダを利用する場合true、そうでない場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setTimeDtos(boolean useWorkTypeChangeRequest, boolean useSchedule) throws MospException {
		String changeWorkTypeCode = "";
		String scheduleWorkTypeCode = "";
		// 設定適用エンティティを取得
		application = timeMaster.getApplicationEntity(personalId, targetDate);
		// 設定適用エンティティが有効でない場合
		if (application.isValid(mospParams) == false) {
			// 処理終了
			return;
		}
		// カレンダ日情報を取得
		scheduleDateDto = scheduleUtil.getScheduleDate(personalId, targetDate);
		// カレンダ日情報を取得できなかった場合
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態コード取得
		String workTypeCode = scheduleDateDto.getWorkTypeCode();
		scheduleWorkTypeCode = workTypeCode;
		// 休日出勤情報取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHoliday.findForKeyOnWorkflow(personalId,
				targetDate);
		// 休日出勤申請確認
		if (workOnHolidayRequestDto != null) {
			// 最新のワークフロー取得
			WorkflowDtoInterface workflowDto = workflow.getLatestWorkflowInfo(workOnHolidayRequestDto.getWorkflow());
			if (workflowDto != null) {
				if (workflow.isApprovable(workflowDto) || workflow.isCompleted(workflowDto)
						|| workflow.isCancelApprovable(workflowDto)
						|| workflow.isCancelWithDrawnApprovable(workflowDto)) {
					// 承認可能又は承認済の場合
					// 休日出勤申請情報から休日出勤時の予定勤務形態を取得
					workTypeCode = getWorkOnHolidayWorkType(workOnHolidayRequestDto);
					scheduleWorkTypeCode = workTypeCode;
				}
			}
		}
		// 申請ユーティリティ準備
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		// 各種申請情報取得
		requestUtil.setRequests(personalId, targetDate);
		// 勤務形態変更申請取得
		WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto = requestUtil.getWorkTypeChangeDto(true);
		if (workTypeChangeRequestDto != null) {
			workTypeCode = workTypeChangeRequestDto.getWorkTypeCode();
			changeWorkTypeCode = workTypeCode;
		}
		// 勤怠データ取得
		AttendanceDtoInterface attendanceDto = attendanceReference.findForKey(personalId, targetDate);
		// 勤怠データ確認
		if (attendanceDto != null && !attendanceDto.getWorkTypeCode().isEmpty()
				&& !TimeConst.CODE_DIFFERENCE_TYPE_A.equals(attendanceDto.getWorkTypeCode())
				&& !TimeConst.CODE_DIFFERENCE_TYPE_B.equals(attendanceDto.getWorkTypeCode())
				&& !TimeConst.CODE_DIFFERENCE_TYPE_C.equals(attendanceDto.getWorkTypeCode())
				&& !TimeConst.CODE_DIFFERENCE_TYPE_D.equals(attendanceDto.getWorkTypeCode())
				&& !TimeConst.CODE_DIFFERENCE_TYPE_S.equals(attendanceDto.getWorkTypeCode())) {
			// 勤怠データの勤務形態を取得
			workTypeCode = attendanceDto.getWorkTypeCode();
		}
		if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 法定休日又は所定休日の場合
			// エラーメッセージ設定
			addNotWorkDateErrorMessage(targetDate);
			return;
		}
		// 勤務形態情報取得
		workTypeDto = workTypeReference.getWorkTypeInfo(workTypeCode, targetDate);
		if (useSchedule) {
			// カレンダを利用する場合
			workTypeDto = workTypeReference.getWorkTypeInfo(scheduleWorkTypeCode, targetDate);
		} else if (useWorkTypeChangeRequest) {
			// 勤務形態変更申請を利用する場合
			workTypeDto = workTypeReference.getWorkTypeInfo(changeWorkTypeCode, targetDate);
		}
		// 確認
		if (workTypeDto == null) {
			// エラーメッセージ設定
			addWorkTypeNotExistErrorMessage(targetDate);
			return;
		}
		// 時差出勤初期化
		differenceDto = null;
		// 時差出勤
		DifferenceRequestDtoInterface differenceRequestDto = requestUtil.getDifferenceDto(true);
		if (differenceRequestDto == null) {
			return;
		}
		differenceDto = differenceRequestDto;
	}
	
	@Override
	public List<RestDtoInterface> getRestList(AttendanceDtoInterface dto) throws MospException {
		// 勤怠関連情報を取得
		personalId = dto.getPersonalId();
		targetDate = dto.getWorkDate();
		setTimeDtos(false, false);
		if (differenceDto == null) {
			// 勤務形態情報取得
			workTypeDto = workTypeReference.getWorkTypeInfo(dto.getWorkTypeCode(), targetDate);
		}
		return registRest(dto.getStartTime(), dto.getEndTime());
	}
	
	/**
	 * 設定されている勤務形態情報から休憩情報を取得し、登録する。<br>
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param list 休憩リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void registRest(Date startTime, Date endTime, List<RestDtoInterface> list) throws MospException {
		// 対象日デフォルト時間取得
		Date initTime = DateUtility.getDateTime(DateUtility.getYear(targetDate), DateUtility.getMonth(targetDate),
				DateUtility.getDay(targetDate), 0, 0);
		// 勤怠データ休憩情報リスト取得
		List<RestDtoInterface> newList = restReference.getRestList(personalId, targetDate, TIMES_WORK_DEFAULT);
		// 勤怠データ休憩情報リスト毎に処理
		for (RestDtoInterface dto : newList) {
			// 休憩開始・終了時刻を登録していない場合
			if (initTime.equals(dto.getRestStart()) && initTime.equals(dto.getRestEnd())) {
				continue;
			}
			// 始業時刻がない又は終業時刻がない又は休憩開始が終業の後又は休憩終了が始業の前の場合
			if (startTime == null || endTime == null || dto.getRestStart().after(endTime)
					|| dto.getRestEnd().before(startTime)) {
				// 初期化
				dto.setRestStart(initTime);
				dto.setRestEnd(initTime);
				dto.setRestTime(0);
				// 登録
				restRegist.regist(dto);
				continue;
			}
			// 休憩開始が始業の後の場合
			if (startTime.after(dto.getRestStart())) {
				dto.setRestStart(startTime);
				dto.setRestEnd(dto.getRestEnd());
				dto.setRestTime(
						restRegist.getCalcRestTime(startTime, dto.getRestEnd(), application.getTimeSettingDto()));
				// 登録
				restRegist.update(dto);
				continue;
			}
			// 終業時刻が休憩終了の前の場合
			if (endTime.before(dto.getRestEnd())) {
				dto.setRestStart(dto.getRestStart());
				dto.setRestEnd(endTime);
				dto.setRestTime(
						restRegist.getCalcRestTime(dto.getRestStart(), endTime, application.getTimeSettingDto()));
				// 登録
				restRegist.update(dto);
				continue;
			}
		}
		// 始業又は終業がない場合
		if (startTime == null || endTime == null) {
			return;
		}
		// 休憩リスト毎に処理
		for (RestDtoInterface dto : list) {
			// 休憩開始時刻がデフォルトでない又は休憩終了時刻がデフォルトでない場合
			if (!initTime.equals(dto.getRestStart()) || !initTime.equals(dto.getRestEnd())) {
				return;
			}
		}
		// 設定されている勤務形態情報から休憩情報を取得
		List<RestDtoInterface> restList = registRest(startTime, endTime);
		// 勤務形態休憩情報リスト毎に処理
		for (RestDtoInterface dto : restList) {
			// 休憩時間登録
			restRegist.regist(dto);
		}
	}
	
	/**
	 * 設定されている勤務形態情報から休憩情報を取得する。<br>
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @return 休憩リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<RestDtoInterface> registRest(Date startTime, Date endTime) throws MospException {
		Date initTime = DateUtility.getDateTime(DateUtility.getYear(targetDate), DateUtility.getMonth(targetDate),
				DateUtility.getDay(targetDate), 0, 0);
		List<RestDtoInterface> list = new ArrayList<RestDtoInterface>();
		// 勤務形態マスタ項目情報リスト準備
		List<WorkTypeItemDtoInterface> startList = new ArrayList<WorkTypeItemDtoInterface>();
		List<WorkTypeItemDtoInterface> endList = new ArrayList<WorkTypeItemDtoInterface>();
		createRestTimeList(startTime, endTime, startList, endList);
		// 勤務形態マスタ項目毎に処理
		for (int i = 0; i < startList.size(); i++) {
			Date restStartTime = initTime;
			Date restEndTime = initTime;
			// 休憩情報存在確認
			if (startList.get(i) != null) {
				// 勤務形態マスタ項目情報から休憩開始時刻を取得
				restStartTime = getTime(startList.get(i).getWorkTypeItemValue(), targetDate);
			}
			if (endList.get(i) != null) {
				// 勤務形態マスタ項目情報から休憩終了時刻を取得
				restEndTime = getTime(endList.get(i).getWorkTypeItemValue(), targetDate);
			}
			if (endTime != null && restStartTime.before(endTime) && startTime != null && restEndTime.after(startTime)) {
				// 休憩開始時刻が終業時刻より前且つ
				// 休憩終了時刻が始業時刻より後の場合
				if (restStartTime.before(startTime)) {
					// 休憩開始時刻が始業時刻より前の場合は
					// 始業時刻を休憩開始時刻とする
					restStartTime = startTime;
				}
				if (restEndTime.after(endTime)) {
					// 休憩終了時刻が終業時刻より後の場合は
					// 終業時刻を休憩終了時刻とする
					restEndTime = endTime;
				}
			} else {
				restStartTime = initTime;
				restEndTime = initTime;
			}
			// 休憩情報準備(休憩回数はi + 1)
			RestDtoInterface dto = restRegist.getInitDto();
			RestDtoInterface restDto = restDao.findForKey(personalId, targetDate, TIMES_WORK_DEFAULT, i + 1);
			if (restDto != null) {
				dto.setTmdRestId(restDto.getTmdRestId());
			}
			dto.setPersonalId(personalId);
			dto.setWorkDate(targetDate);
			dto.setTimesWork(TIMES_WORK_DEFAULT);
			dto.setRest(i + 1);
			dto.setRestStart(restStartTime);
			dto.setRestEnd(restEndTime);
			// 休憩時間を丸めた時刻を取得
			dto.setRestTime(restRegist.getCalcRestTime(restStartTime, restEndTime, application.getTimeSettingDto()));
			list.add(dto);
		}
		return list;
	}
	
	/**
	 * 休憩時刻リスト作成。<br>
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @param startList 休憩開始時刻リスト
	 * @param endList 休憩終了時刻リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void createRestTimeList(Date startTime, Date endTime, List<WorkTypeItemDtoInterface> startList,
			List<WorkTypeItemDtoInterface> endList) throws MospException {
		// 申請ユーティリティ準備
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		// 各種申請情報取得
		requestUtil.setRequests(personalId, targetDate);
		// 申請エンティティ取得
		RequestEntityInterface entity = requestUtil.getRequestEntity(personalId, targetDate);
		// 休憩リスト1～6の初期化
		startList.add(null);
		startList.add(null);
		startList.add(null);
		startList.add(null);
		startList.add(null);
		startList.add(null);
		endList.add(null);
		endList.add(null);
		endList.add(null);
		endList.add(null);
		endList.add(null);
		endList.add(null);
		// 勤務形態コード及び勤務形態有効日取得
		String workTypeCode = workTypeDto.getWorkTypeCode();
		Date activateDate = workTypeDto.getActivateDate();
		if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 処理なし
			return;
		}
		if (entity.isAmHoliday(true)) {
			// 前半休の場合
			WorkTypeItemDtoInterface halfRestDto = workTypeItemReference.findForKey(workTypeCode, activateDate,
					TimeConst.CODE_HALFREST);
			if (endTime != null && halfRestDto != null
					&& DateUtility.getHour(endTime, targetDate) * TimeConst.CODE_DEFINITION_HOUR
							+ DateUtility.getMinute(endTime) >= DateUtility.getHour(halfRestDto.getWorkTypeItemValue(),
									getDefaultStandardDate()) * TimeConst.CODE_DEFINITION_HOUR
									+ DateUtility.getMinute(halfRestDto.getWorkTypeItemValue())) {
				startList.set(0,
						workTypeItemReference.findForKey(workTypeCode, activateDate, TimeConst.CODE_HALFRESTSTART));
				endList.set(0,
						workTypeItemReference.findForKey(workTypeCode, activateDate, TimeConst.CODE_HALFRESTEND));
			}
			return;
		} else if (entity.isPmHoliday(true)) {
			// 処理なし
			return;
		} else {
			startList.set(0, workTypeItemReference.findForKey(workTypeCode, activateDate, TimeConst.CODE_RESTSTART1));
			startList.set(1, workTypeItemReference.findForKey(workTypeCode, activateDate, TimeConst.CODE_RESTSTART2));
			startList.set(2, workTypeItemReference.findForKey(workTypeCode, activateDate, TimeConst.CODE_RESTSTART3));
			startList.set(3, workTypeItemReference.findForKey(workTypeCode, activateDate, TimeConst.CODE_RESTSTART4));
			endList.set(0, workTypeItemReference.findForKey(workTypeCode, activateDate, TimeConst.CODE_RESTEND1));
			endList.set(1, workTypeItemReference.findForKey(workTypeCode, activateDate, TimeConst.CODE_RESTEND2));
			endList.set(2, workTypeItemReference.findForKey(workTypeCode, activateDate, TimeConst.CODE_RESTEND3));
			endList.set(3, workTypeItemReference.findForKey(workTypeCode, activateDate, TimeConst.CODE_RESTEND4));
		}
	}
	
	/**
	 * 公用外出時間を登録する。<br>
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void registPublicGoOut(Date startTime, Date endTime) throws MospException {
		// デフォルト日付取得
		Date initTime = DateUtility.getDateTime(DateUtility.getYear(targetDate), DateUtility.getMonth(targetDate),
				DateUtility.getDay(targetDate), 0, 0);
		// 勤怠設定情報を取得
		TimeSettingDtoInterface timeSettingDto = application.getTimeSettingDto();
		// 勤怠データ公用外出情報リストを取得
		List<GoOutDtoInterface> list = goOutReference.getPublicGoOutList(personalId, targetDate);
		// 公用外出情報リスト毎に処理
		for (GoOutDtoInterface dto : list) {
			// 公用外出開始・終了がデフォルトの場合
			if (initTime.equals(dto.getGoOutStart()) && initTime.equals(dto.getGoOutEnd())) {
				continue;
			}
			// 始業がない又は終業がない又は公用外出開始が終業の後又は公用外出終了が始業の前の場合
			if (startTime == null || endTime == null || dto.getGoOutStart().after(endTime)
					|| dto.getGoOutEnd().before(startTime)) {
				dto.setGoOutStart(initTime);
				dto.setGoOutEnd(initTime);
				dto.setGoOutTime(0);
				// 登録
				goOutRegist.regist(dto);
				continue;
			}
			// 公用外出開始が始業の前の場合
			if (dto.getGoOutStart().before(startTime)) {
				// 公用外出開始を始業に設定
				dto.setGoOutStart(startTime);
				dto.setGoOutEnd(dto.getGoOutEnd());
				dto.setGoOutTime(goOutRegist.getCalcPublicGoOutTime(startTime, dto.getGoOutEnd(), timeSettingDto));
				// 登録
				goOutRegist.regist(dto);
				continue;
			}
			// 公用外出終了が終業の後の場合
			if (dto.getGoOutEnd().after(endTime)) {
				dto.setGoOutStart(dto.getGoOutStart());
				// 公用外出終業を終業に設定
				dto.setGoOutEnd(endTime);
				dto.setGoOutTime(goOutRegist.getCalcPublicGoOutTime(dto.getGoOutStart(), endTime, timeSettingDto));
				// 登録
				goOutRegist.regist(dto);
				continue;
			}
		}
	}
	
	/**
	 * 私用外出時間を登録する。<br>
	 * @param startTime 始業時刻
	 * @param endTime 終業時刻
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void registPrivateGoOut(Date startTime, Date endTime) throws MospException {
		// デフォルト日付取得
		Date initTime = DateUtility.getDateTime(DateUtility.getYear(targetDate), DateUtility.getMonth(targetDate),
				DateUtility.getDay(targetDate), 0, 0);
		// 勤怠設定情報を取得
		TimeSettingDtoInterface timeSettingDto = application.getTimeSettingDto();
		// 勤怠データ私用外出情報リストを取得
		List<GoOutDtoInterface> list = goOutReference.getPrivateGoOutList(personalId, targetDate);
		// 勤怠データ私用外出情報リスト毎に処理
		for (GoOutDtoInterface dto : list) {
			// 私用外出開始・終了時刻がデフォルトの場合
			if (initTime.equals(dto.getGoOutStart()) && initTime.equals(dto.getGoOutEnd())) {
				continue;
			}
			// 始業がない又は終業がない又は私用外出開始が終業の後又は私用外出終了が始業の前の場合
			if (startTime == null || endTime == null || dto.getGoOutStart().after(endTime)
					|| dto.getGoOutEnd().before(startTime)) {
				dto.setGoOutStart(initTime);
				dto.setGoOutEnd(initTime);
				dto.setGoOutTime(0);
				// 登録
				goOutRegist.regist(dto);
				continue;
			}
			// 私用外出開始が始業の前の場合
			if (dto.getGoOutStart().before(startTime)) {
				dto.setGoOutStart(startTime);
				dto.setGoOutEnd(dto.getGoOutEnd());
				dto.setGoOutTime(goOutRegist.getCalcPrivateGoOutTime(startTime, dto.getGoOutEnd(), timeSettingDto));
				// 登録
				goOutRegist.regist(dto);
				continue;
			}
			// 私用外出終了が退勤の後の場合
			if (dto.getGoOutEnd().after(endTime)) {
				dto.setGoOutStart(dto.getGoOutStart());
				dto.setGoOutEnd(endTime);
				dto.setGoOutTime(goOutRegist.getCalcPrivateGoOutTime(dto.getGoOutStart(), endTime, timeSettingDto));
				// 登録
				goOutRegist.regist(dto);
				continue;
			}
		}
	}
	
	@Override
	public void registSubHoliday(AttendanceDtoInterface dto) throws MospException {
		// 代休データを削除する
		subHolidayRegist.delete(personalId, targetDate);
		SubHolidayDtoInterface subHolidayDto = subHolidayRegist.getInitDto();
		subHolidayDto.setPersonalId(dto.getPersonalId());
		subHolidayDto.setWorkDate(dto.getWorkDate());
		subHolidayDto.setTimesWork(dto.getTimesWork());
		if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(dto.getWorkTypeCode())) {
			// 法定休日労働の場合
			subHolidayDto.setSubHolidayType(TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE);
			if (dto.getGrantedLegalCompensationDays() == TimeConst.HOLIDAY_TIMES_ALL) {
				// 法定代休発生日数が1日の場合
				subHolidayDto.setSubHolidayDays(TimeConst.HOLIDAY_TIMES_ALL);
			} else if (TimeUtility.isHolidayTimesHalf(dto.getGrantedLegalCompensationDays())) {
				// 法定代休発生日数が0.5日の場合
				subHolidayDto.setSubHolidayDays(TimeConst.HOLIDAY_TIMES_HALF);
			} else {
				return;
			}
			// 登録
			subHolidayRegist.insert(subHolidayDto);
		} else if (TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(dto.getWorkTypeCode())) {
			// 所定休日労働の場合
			subHolidayDto.setSubHolidayType(TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE);
			if (dto.getGrantedPrescribedCompensationDays() == TimeConst.HOLIDAY_TIMES_ALL) {
				// 所定代休発生日数が1日の場合
				subHolidayDto.setSubHolidayDays(TimeConst.HOLIDAY_TIMES_ALL);
			} else if (TimeUtility.isHolidayTimesHalf(dto.getGrantedPrescribedCompensationDays())) {
				// 所定代休発生日数が0.5日の場合
				subHolidayDto.setSubHolidayDays(TimeConst.HOLIDAY_TIMES_HALF);
			} else {
				return;
			}
			// 登録
			subHolidayRegist.insert(subHolidayDto);
		}
	}
	
	@Override
	public void registCorrection(AttendanceDtoInterface dto, AttendanceDtoInterface oldDto,
			List<RestDtoInterface> oldRestList, List<GoOutDtoInterface> oldPublicList,
			List<GoOutDtoInterface> oldPrivateList) throws MospException {
		// 勤怠関連情報を取得
		personalId = dto.getPersonalId();
		targetDate = dto.getWorkDate();
		// DTOの準備
		AttendanceCorrectionDtoInterface attendanceCorrectionDto = attendanceCorrectionRegist.getInitDto();
		// 個人ID
		attendanceCorrectionDto.setPersonalId(personalId);
		// 勤務日。
		attendanceCorrectionDto.setWorkDate(targetDate);
		// 勤務回数
		attendanceCorrectionDto.setWorks(dto.getTimesWork());
		// 修正番号
		AttendanceCorrectionDtoInterface latestDto = attendanceCorrectionReference
			.getLatestAttendanceCorrectionInfo(personalId, targetDate, dto.getTimesWork());
		int correctionTimes = 1;
		if (latestDto != null) {
			correctionTimes += latestDto.getCorrectionTimes();
		}
		attendanceCorrectionDto.setCorrectionTimes(correctionTimes);
		// 修正日時
		attendanceCorrectionDto.setCorrectionDate(getSystemTimeAndSecond());
		// 修正個人ID
		attendanceCorrectionDto.setCorrectionPersonalId(mospParams.getUser().getPersonalId());
		// 修正理由
		attendanceCorrectionDto.setCorrectionReason("");
		// 勤怠修正データ登録
		registAttendanceCorrection(attendanceCorrectionDto, oldDto, dto);
		// 休憩修正データ登録
		registRestCorrection(attendanceCorrectionDto, oldRestList,
				restReference.getRestList(personalId, targetDate, dto.getTimesWork()));
		// 公用外出修正データ登録
		registGoOutCorrection(attendanceCorrectionDto, oldPublicList,
				goOutReference.getPublicGoOutList(personalId, targetDate));
		// 私用外出修正データ登録
		registGoOutCorrection(attendanceCorrectionDto, oldPrivateList,
				goOutReference.getPrivateGoOutList(personalId, targetDate));
	}
	
	/**
	 * 勤怠修正データを登録する。
	 * @param dto 勤怠修正データ情報
	 * @param oldDto 旧勤怠情報リスト
	 * @param newDto 新勤怠情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void registAttendanceCorrection(AttendanceCorrectionDtoInterface dto, AttendanceDtoInterface oldDto,
			AttendanceDtoInterface newDto) throws MospException {
		attendanceCorrectionRegist.insertAttendance(dto, oldDto, newDto);
	}
	
	/**
	 * 休憩修正データを登録する。
	 * @param dto 勤怠修正データ情報
	 * @param oldList 旧休憩情報リスト
	 * @param newList 新休憩情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void registRestCorrection(AttendanceCorrectionDtoInterface dto, List<RestDtoInterface> oldList,
			List<RestDtoInterface> newList) throws MospException {
		for (int i = 0; i < newList.size(); i++) {
			if (i < oldList.size()) {
				if (attendanceCorrectionReference.chkRest(newList.get(i), oldList.get(i))) {
					attendanceCorrectionRegist.insertRest(dto, oldList.get(i), newList.get(i));
				}
			} else {
				if (attendanceCorrectionReference.chkRest(newList.get(i), restRegist.getInitDto())) {
					attendanceCorrectionRegist.insertRest(dto, restRegist.getInitDto(), newList.get(i));
				}
			}
		}
	}
	
	/**
	 * 外出修正データを登録する。
	 * @param dto 勤怠修正データ情報
	 * @param oldList 旧外出情報リスト
	 * @param newList 新外出情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	private void registGoOutCorrection(AttendanceCorrectionDtoInterface dto, List<GoOutDtoInterface> oldList,
			List<GoOutDtoInterface> newList) throws MospException {
		for (int i = 0; i < newList.size(); i++) {
			if (i < oldList.size()) {
				if (attendanceCorrectionReference.chkGoOut(newList.get(i), oldList.get(i))) {
					attendanceCorrectionRegist.insertGoOut(dto, oldList.get(i), newList.get(i));
				}
			} else {
				if (attendanceCorrectionReference.chkGoOut(newList.get(i), goOutRegist.getInitDto())) {
					attendanceCorrectionRegist.insertGoOut(dto, goOutRegist.getInitDto(), newList.get(i));
				}
			}
		}
	}
	
	@Override
	public void draft(AttendanceDtoInterface dto) throws MospException {
		WorkflowDtoInterface workflowDto = null;
		// ワークフロー番号確認
		if (dto.getWorkflow() != 0) {
			// 既にワークフロー番号が設定されている場合
			// 最新のワークフロー取得
			workflowDto = workflow.getLatestWorkflowInfo(dto.getWorkflow());
			if (!PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
				// 下書でない場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_DRAFT,
						DateUtility.getStringDate(dto.getWorkDate()));
				return;
			}
		}
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_WORK_MANGE);
		}
		// 勤怠関連情報を取得
		personalId = dto.getPersonalId();
		targetDate = dto.getWorkDate();
		// ルートコード或いは承認者を設定
		setRouteApprover(workflowDto);
		// ワークフロー登録(下書)
		workflowDto = workflowRegist.draft(workflowDto, personalId, targetDate, PlatformConst.WORKFLOW_TYPE_TIME);
		// ワークフロー申請確認
		if (workflowDto == null) {
			return;
		}
		// ワークフロー番号を勤怠データに設定
		dto.setWorkflow(workflowDto.getWorkflow());
	}
	
	/**
	 * 対象勤怠データ情報に対し、
	 * ワークフロー(申請)を作成して、ワークフロー番号を設定する。<br>
	 * @param dto 対象勤怠データ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void apply(AttendanceDtoInterface dto) throws MospException {
		// ワークフロー情報準備
		WorkflowDtoInterface workflowDto = workflowRegist.getInitDto();
		// ワークフロー情報確認
		if (dto.getWorkflow() != 0) {
			// ワークフロー情報取得(新規でない場合)
			workflowDto = workflow.getLatestWorkflowInfo(dto.getWorkflow());
		}
		// ルートコード或いは承認者を設定
		setRouteApprover(workflowDto);
		// ワークフロー情報機能コード設定
		workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_WORK_MANGE);
		// ワークフロー申請
		workflowDto = workflowRegist.appli(workflowDto, personalId, targetDate, PlatformConst.WORKFLOW_TYPE_TIME, null);
		// ワークフロー申請確認
		if (workflowDto == null) {
			return;
		}
		// ワークフロー番号を勤怠データに設定
		dto.setWorkflow(workflowDto.getWorkflow());
	}
	
	@Override
	public void setRouteApprover(WorkflowDtoInterface dto, String personalId, Date targetDate) throws MospException {
		this.personalId = personalId;
		this.targetDate = CapsuleUtility.getDateClone(targetDate);
		setRouteApprover(dto);
	}
	
	/**
	 * ワークフロー情報に、承認者を設定する。<br>
	 * 一つの階層に複数の承認者が設定されている場合、
	 * 先頭の(職位ランクの最も優れた)承認者を設定する。<br>
	 * @param workflowDto ワークフロー情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setRouteApprover(WorkflowDtoInterface workflowDto) throws MospException {
		// 承認者リスト取得
		List<List<String[]>> routeApproverList = workflow.getRouteApproverList(personalId, targetDate,
				PlatformConst.WORKFLOW_TYPE_TIME);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// デフォルト承認者リスト作成
		List<String> defaultApproverList = new ArrayList<String>();
		// 承認者リスト毎に処理
		for (List<String[]> stageApproverList : routeApproverList) {
			// デフォルト承認者設定
			defaultApproverList.add(stageApproverList.get(0)[0]);
		}
		// ユニット承認者使用
		if (mospParams.isTargetApprovalUnit()) {
			// 承認者個人IDを空に設定
			workflowDto.setApproverId("");
		} else {
			// ワークフロー情報に設定
			workflowDto.setApproverId(toSeparatedString(defaultApproverList, PlatformBean.SEPARATOR_DATA));
			// 承認者を指定するためルートコードは不要
			workflowDto.setRouteCode("");
		}
	}
	
	/**
	 * 日付文字列配列を日付オブジェクトリストに変換して取得する。<br>
	 * @param dates 日付文字列配列
	 * @return 日付オブジェクトリスト
	 */
	protected List<Date> getDateList(String[] dates) {
		// リスト準備
		List<Date> list = new ArrayList<Date>();
		// 変換して追加
		for (String date : dates) {
			list.add(DateUtility.getDate(date));
		}
		return list;
	}
	
	/**
	 * 時刻文字列配列を日付オブジジェクトリスト(時刻)に変換して取得する。<br>
	 * 変換の際に、日付リストの日付が基準となる。<br>
	 * @param dateList 日付リスト
	 * @param times 時刻文字列配列
	 * @return 日付オブジェクトリスト(時刻)
	 */
	protected List<Date> getAttendanceTimeList(List<Date> dateList, String[] times) {
		// リスト準備
		List<Date> list = new ArrayList<Date>();
		// 変換して追加
		for (int i = 0; i < times.length; i++) {
			list.add(!times[i].isEmpty() ? TimeUtility.getAttendanceTime(dateList.get(i), times[i], mospParams) : null);
		}
		return list;
	}
	
	@Override
	public void checkOvertime(AttendanceDtoInterface attendanceDto) throws MospException {
		// 勤怠申請
		// 勤怠データの前残業時間かつ後残業時間が0である
		if (attendanceDto.getOvertimeBefore() == 0 && attendanceDto.getOvertimeAfter() == 0) {
			// メッセージ不要
			return;
		}
		// 勤怠関連情報を取得
		personalId = attendanceDto.getPersonalId();
		targetDate = attendanceDto.getWorkDate();
		setTimeDtos(false, false);
		// エラーチェック
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 各種申請情報取得
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, targetDate);
		// 未承認仮締を取得
		int noApproval = application.getNoApproval();
		// エラーチェック
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 未承認仮締が有効・無効(残業事前申請のみ)・無効(残業申請なし)の場合
		if (noApproval != 1) {
			// メッセージ不要
			return;
		}
		// 振出・休出申請を取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		// 振出・休出申請が承認されていて振り替えない場合
		if (workOnHolidayRequestDto != null
				&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			return;
		}
		// 前残業未申請フラグ
		boolean beforeFlag = attendanceDto.getOvertimeBefore() != 0;
		// 後残業未申請フラグ
		boolean afterFlag = attendanceDto.getOvertimeAfter() != 0;
		// 取下、下書以外の残業申請リスト
		List<OvertimeRequestDtoInterface> overtimeRequestList = requestUtil.getOverTimeList(false);
		// 取下、下書以外の残業申請毎に処理
		for (OvertimeRequestDtoInterface overtimeRequestDto : overtimeRequestList) {
			// 勤務後残業申請がある場合
			if (overtimeRequestDto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_AFTER) {
				afterFlag = false;
			} else if (overtimeRequestDto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_BEFORE) {
				beforeFlag = false;
			}
		}
		if (beforeFlag || afterFlag) {
			// メッセージ追加
			mospParams.addMessage(TimeMessageConst.MSG_REQUEST_CHECK_11, getStringDate(targetDate),
					mospParams.getName("OvertimeWork"), mospParams.getName("OvertimeWork"));
		}
	}
	
	@Override
	public void checkOvertime(String personalId, String[] targetDates) throws MospException {
		// 日付リスト取得
		List<Date> targetDateList = getDateList(targetDates);
		// 勤怠一覧情報参照クラス取得
		AttendanceReferenceBean referenceBean = (AttendanceReferenceBean)createBean(AttendanceReferenceBean.class);
		// 対象日毎に勤怠情報を作成して登録
		for (Date targetDate : targetDateList) {
			// 残業申請督促確認
			checkOvertime(referenceBean.findForKey(personalId, targetDate));
		}
	}
	
}
