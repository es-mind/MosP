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
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.TimeRecordBeanInterface;
import jp.mosp.time.bean.TimeRecordReferenceBeanInterface;
import jp.mosp.time.bean.TimeRecordRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.entity.TimeSettingEntityInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.input.action.AttendanceCardAction;
import jp.mosp.time.portal.bean.impl.PortalTimeCardBean;
import jp.mosp.time.utils.AttendanceUtility;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 打刻処理。<br>
 */
public class TimeRecordBean extends AttendanceListRegistBean implements TimeRecordBeanInterface {
	
	/**
	 * 最大休憩回数。<br>
	 */
	protected static final int					MAX_REST_TIMES					= 6;
	
	/**
	 * 最大外出回数。<br>
	 */
	protected static final int					MAX_GO_OUT_TIMES				= 2;
	
	/**
	 * 区分(打刻時設定：終業打刻時承認状態)。<br>
	 * 限度基準情報の期間に設定される。<br>
	 */
	public static final String					TYPE_RS_END_WORK_APPLI_STATUS	= "rs_end_work_application_status";
	
	/**
	 * 打刻時承認状態(申請)。<br>
	 */
	public static final int						TYPE_RECORD_APPLY				= 1;
	
	/**
	 * 打刻時承認状態(下書)。<br>
	 */
	public static final int						TYPE_RECORD_DRAFT				= 2;
	
	/**
	 * 打刻情報参照処理。<br>
	 */
	protected TimeRecordReferenceBeanInterface	timeRecordReference;
	
	/**
	 * 打刻情報登録処理。<br>
	 */
	protected TimeRecordRegistBeanInterface		timeRecordRegist;
	
	/**
	 * 申請ユーティリティ。<br>
	 */
	protected RequestUtilBeanInterface			requestUtil;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public TimeRecordBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承元クラスのメソッドを実行
		super.initBean();
		// Beanを準備
		timeRecordReference = createBeanInstance(TimeRecordReferenceBeanInterface.class);
		timeRecordRegist = createBeanInstance(TimeRecordRegistBeanInterface.class);
		requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		// 勤怠関連マスタ参照処理を設定
		requestUtil.setTimeMaster(timeMaster);
	}
	
	@Override
	public Date recordStartWork(String personalId, Date recordTime) throws MospException {
		// 追加業務ロジック処理(打刻前追加処理)
		doStoredLogic(TimeConst.CODE_KEY_BEFORE_RECORD_TIME_ADDONS, personalId, recordTime);
		// 対象個人ID及び対象日(打刻時刻から)を設定
		this.personalId = personalId;
		targetDate = getTargetDate(recordTime);
		// ポータル時刻を打刻
		recordPortalTime(personalId, targetDate, recordTime, PortalTimeCardBean.RECODE_START_WORK);
		// 勤怠データ取得
		AttendanceDtoInterface dto = attendanceReference.findForKey(this.personalId, targetDate);
		// 勤怠情報確認
		if (dto != null) {
			// エラーメッセージ設定
			mospParams.addErrorMessage(TimeMessageUtility.MSG_ALREADY_RECORDED, DateUtility.getStringDate(targetDate),
					mospParams.getName("WorkManage"), mospParams.getName("RecordTime"));
			return null;
		}
		// 設定適用エンティティを取得
		ApplicationEntity application = applicationReference.getApplicationEntity(personalId, targetDate);
		// 申請エンティティを取得
		RequestEntityInterface request = requestUtil.getRequestEntity(personalId, targetDate);
		// 勤務形態コードを取得
		String workTypeCode = request.getWorkType();
		// 勤務形態エンティティを取得(申請エンティティから勤務形態コードを取得)
		WorkTypeEntityInterface workType = workTypeReference.getWorkTypeEntity(workTypeCode, targetDate);
		// 勤務対象勤務形態であるかを確認
		if (workType.isWorkTypeForWork() == false) {
			// エラーメッセージ設定
			addNotWorkDateErrorMessage(targetDate);
			return null;
		}
		// 始業時刻取得
		Date startTime = AttendanceUtility.getStartTimeForTimeRecord(application, request, workType, recordTime);
		// 勤怠データ作成
		dto = getAttendanceDto(startTime, startTime, null, false, false, false, false, false);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態情報が保持している直行直帰を勤怠データに設定
		setDirectStartEnd(dto, workType);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 始業・終業必須チェック
		attendanceRegist.checkTimeExist(dto);
		// 妥当性チェック
		attendanceRegist.checkValidate(dto);
		// 申請の相関チェック
		attendanceRegist.checkDraft(dto);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフロー番号設定
		draft(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態に登録されている休憩情報のリストを取得
		List<RestDtoInterface> restList = registRest(startTime, workType.getEndTime(request));
		// 休憩情報毎に処理
		for (RestDtoInterface restDto : restList) {
			// 休憩時間登録
			restRegist.regist(restDto);
		}
		// 勤怠データ登録
		attendanceRegist.regist(dto);
		// 追加処理
		doAdditionalLogic(TimeConst.CODE_KEY_PORTAL_ATTENDANCE_CARD_ADDONS,
				TimeConst.CODE_KEY_ADD_ATTENDANCE_REGIST_REGIST_ADDON_TABLE, targetDate);
		return recordTime;
	}
	
	@Override
	public Date recordEndWork(String personalId, Date recordTime) throws MospException {
		// 追加業務ロジック処理(打刻前追加処理)
		doStoredLogic(TimeConst.CODE_KEY_BEFORE_RECORD_TIME_ADDONS, personalId, recordTime);
		// 対象個人ID設定
		this.personalId = personalId;
		// 対象日設定及び対象日の勤怠情報取得
		AttendanceDtoInterface dto = setTargetDate(recordTime, TimeMessageUtility.getNameEndWork(mospParams));
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ポータル時刻を打刻
		recordPortalTime(personalId, targetDate, recordTime, PortalTimeCardBean.RECODE_END_WORK);
		// 休憩戻確認
		checkRestEnd();
		// ワークフロー番号設定
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 設定適用エンティティを取得
		ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
		// 勤怠設定エンティティを取得
		TimeSettingEntityInterface timeSetting = timeSettingReference.getEntity(applicationEntity.getTimeSettingDto());
		// 終業打刻時承認状態設定を取得(デフォルト：申請)
		int endWorkAppliStatus = timeSetting.getLimit(TYPE_RS_END_WORK_APPLI_STATUS, TYPE_RECORD_APPLY);
		// 終業打刻時承認状態設定が申請である場合
		if (endWorkAppliStatus == TYPE_RECORD_APPLY) {
			// 申請
			apply(dto);
		}
		// 終業打刻時承認状態設定が下書である場合
		if (endWorkAppliStatus == TYPE_RECORD_DRAFT) {
			// 下書
			draft(dto);
		}
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 申請エンティティを取得
		RequestEntityInterface requestEntity = requestUtil.getRequestEntity(personalId, targetDate);
		// 勤怠情報の勤務形態コードから勤務形態エンティティを取得
		WorkTypeEntityInterface workTypeEntity = workTypeReference.getWorkTypeEntity(dto.getWorkTypeCode(), targetDate);
		// 終業時刻取得
		Date endTime = getEndTime(applicationEntity, requestEntity, workTypeEntity, recordTime);
		// 登録用勤怠データを取得(各種自動計算実施)
		dto = getAttendanceDto(dto.getStartTime(), dto.getActualStartTime(), endTime, false, true, false, false, false);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態情報が保持している直行直帰を勤怠データに設定
		setDirectStartEnd(dto, workTypeEntity);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 始業・終業必須チェック
		attendanceRegist.checkTimeExist(dto);
		// 妥当性チェック
		attendanceRegist.checkValidate(dto);
		// 申請の相関チェック
		attendanceRegist.checkAppli(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態が所定休出もしくは法定休出の場合、休憩時間チェックを行う
		checkRestTime(dto, personalId, recordTime, targetDate);
		// 勤怠データ登録
		attendanceRegist.regist(dto);
		// 勤怠トランザクション登録
		attendanceTransactionRegist.regist(dto);
		// 代休登録
		registSubHoliday(dto);
		// 終業打刻時承認状態設定が申請である場合
		if (endWorkAppliStatus == TYPE_RECORD_APPLY) {
			// 勤怠申請後処理群を実行
			afterApplyAttendance(dto);
		}
		// 追加処理
		doAdditionalLogic(TimeConst.CODE_KEY_PORTAL_ATTENDANCE_CARD_ADDONS,
				TimeConst.CODE_KEY_ADD_ATTENDANCE_REGIST_REGIST_ADDON_TABLE, targetDate);
		return recordTime;
	}
	
	/**
	 * 終業時刻を取得する。<br>
	 * <br>
	 * 1.直帰の場合：終業予定時刻<br>
	 * 2.早退の場合：<br>
	 *   勤務予定時間表示設定が有効である場合：勤怠設定で丸めた打刻時刻<br>
	 *   勤務予定時間表示設定が無効である場合：勤怠設定で丸めた実打刻時刻<br>
	 * 3.勤務予定時間表示設定が有効である場合：勤怠設定で丸めた打刻時刻<br>
	 * 4.その他の場合：勤怠設定で丸めた実打刻時刻<br>
	 * <br>
	 * @param applicationEntity 設定適用エンティティ
	 * @param requestEntity     申請エンティティ
	 * @param workTypeEntity    勤務形態エンティティ
	 * @param recordTime        打刻時刻
	 * @return 始業時刻
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected Date getEndTime(ApplicationEntity applicationEntity, RequestEntityInterface requestEntity,
			WorkTypeEntityInterface workTypeEntity, Date recordTime) throws MospException {
		// 終業予定時刻を取得(勤務形態エンティティ及び申請エンティティから)
		Date scheduledTime = workTypeEntity.getEndTime(requestEntity);
		// 勤怠設定情報を取得
		TimeSettingDtoInterface timeSettingDto = applicationEntity.getTimeSettingDto();
		// 1.直帰の場合(勤務形態の直帰設定を確認)
		if (workTypeEntity.isDirectEnd()) {
			// 終業予定時刻を取得
			return scheduledTime;
		}
		// 2.早退の場合
		if (recordTime.before(scheduledTime)) {
			// 勤務予定時間表示設定が有効である場合
			if (applicationEntity.useScheduledTime()) {
				// 勤怠設定で丸めた打刻時刻を取得
				return AttendanceUtility.getRoundedEndTimeForTimeRecord(recordTime, timeSettingDto);
			}
			// 勤怠設定で丸めた実打刻時刻を取得
			return AttendanceUtility.getRoundedActualEndTimeForTimeRecord(recordTime, timeSettingDto);
		}
		// 3.勤務予定時間表示設定が有効である場合(勤怠設定の勤務予定時間表示設定を確認)
		if (applicationEntity.useScheduledTime()) {
			// 勤怠設定で丸めた打刻時刻を取得
			return AttendanceUtility.getRoundedEndTimeForTimeRecord(recordTime, timeSettingDto);
		}
		// 4.その他(勤怠設定で丸めた実打刻時刻を取得)
		return AttendanceUtility.getRoundedActualEndTimeForTimeRecord(recordTime, timeSettingDto);
	}
	
	@Override
	public Date recordStartRest(String personalId, Date recordTime) throws MospException {
		// 追加業務ロジック処理(打刻前追加処理)
		doStoredLogic(TimeConst.CODE_KEY_BEFORE_RECORD_TIME_ADDONS, personalId, recordTime);
		// 対象個人IDを設定
		this.personalId = personalId;
		// 打刻名称(エラーメッセージ用)を取得
		String recordName = TimeNamingUtility.startRest(mospParams);
		// 対象日を設定
		setTargetDate(recordTime, recordName);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 休憩情報リストを取得
		List<RestDtoInterface> rests = restDao.findForList(this.personalId, targetDate, TIMES_WORK_DEFAULT);
		// 公用外出リストを取得
		List<GoOutDtoInterface> publics = goOutReference.getPublicGoOutList(personalId, targetDate);
		// 私用外出リストを取得
		List<GoOutDtoInterface> privates = goOutReference.getPrivateGoOutList(personalId, targetDate);
		// 打刻時刻が休憩時間に含まれているかを確認
		checkRestDuplicate(recordTime, rests, recordName);
		// 打刻時刻が公用外出時間に含まれているかを確認
		checkGoOutDuplicate(recordTime, publics, recordName, TimeNamingUtility.getPublicGoOut(mospParams));
		// 打刻時刻が私用外出時間に含まれているかを確認
		checkGoOutDuplicate(recordTime, privates, recordName, TimeNamingUtility.getPrivateGoOut(mospParams));
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return null;
		}
		// 処理対象情報を準備
		RestDtoInterface dto = null;
		// 休憩情報毎に処理
		for (RestDtoInterface rest : rests) {
			// 開始及び終了時刻を取得
			Date startTime = rest.getRestStart();
			Date endTime = rest.getRestEnd();
			// 開始及び終了時刻が基準時刻でない(未打刻である)場合
			if (DateUtility.isSame(startTime, targetDate) && DateUtility.isSame(endTime, targetDate)) {
				// 処理対象情報を設定
				dto = rest;
				// 次の処理へ
				break;
			}
			// 休憩終了時刻が基準日時である場合(連続で入打刻をした場合)
			if (DateUtility.isSame(endTime, targetDate)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorStartRestAlreadyRecorded(mospParams, targetDate);
				// 処理終了
				return null;
			}
		}
		// 処理対象情報が取得できなかった場合(最大休憩回数以上)
		if (MospUtility.isEmpty(dto) && MAX_REST_TIMES <= rests.size()) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorRestOverLimit(mospParams, targetDate);
			// 処理終了
			return null;
		}
		// 処理対象情報を取得できなかった場合(最大休憩回数未満)
		if (MospUtility.isEmpty(dto)) {
			// 処理対象情報を作成
			dto = restRegist.getInitDto();
			dto.setPersonalId(this.personalId);
			dto.setWorkDate(targetDate);
			dto.setTimesWork(TIMES_WORK_DEFAULT);
			dto.setRest(rests.size() + 1);
			dto.setRestEnd(targetDate);
			dto.setRestTime(0);
		}
		// 設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
		// 勤怠設定情報を取得
		TimeSettingDtoInterface timeSettingDto = application.getTimeSettingDto();
		// 休憩開始時刻を設定
		dto.setRestStart(AttendanceUtility.getRoundedRestStartTimeForTimeRecord(recordTime, timeSettingDto));
		// 休憩情報を登録
		restRegist.regist(dto);
		// 追加処理
		doAdditionalLogic(TimeConst.CODE_KEY_PORTAL_ATTENDANCE_CARD_ADDONS,
				TimeConst.CODE_KEY_ADD_ATTENDANCE_REGIST_REGIST_ADDON_TABLE, targetDate);
		// 打刻日時を取得
		return recordTime;
	}
	
	@Override
	public Date recordEndRest(String personalId, Date recordTime) throws MospException {
		// 追加業務ロジック処理(打刻前追加処理)
		doStoredLogic(TimeConst.CODE_KEY_BEFORE_RECORD_TIME_ADDONS, personalId, recordTime);
		// 対象個人IDを設定
		this.personalId = personalId;
		// 打刻名称(エラーメッセージ用)を取得
		String recordName = TimeNamingUtility.endRest(mospParams);
		// 対象日を
		setTargetDate(recordTime, recordName);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 休憩情報リストを取得
		List<RestDtoInterface> rests = restDao.findForList(this.personalId, targetDate, TIMES_WORK_DEFAULT);
		// 公用外出リストを取得
		List<GoOutDtoInterface> publics = goOutReference.getPublicGoOutList(personalId, targetDate);
		// 私用外出リストを取得
		List<GoOutDtoInterface> privates = goOutReference.getPrivateGoOutList(personalId, targetDate);
		// 打刻時刻が休憩時間に含まれているかを確認
		checkRestDuplicate(recordTime, rests, recordName);
		// 打刻時刻が公用外出時間に含まれているかを確認
		checkGoOutDuplicate(recordTime, publics, recordName, TimeNamingUtility.getPublicGoOut(mospParams));
		// 打刻時刻が私用外出時間に含まれているかを確認
		checkGoOutDuplicate(recordTime, privates, recordName, TimeNamingUtility.getPrivateGoOut(mospParams));
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return null;
		}
		// 処理対象情報を準備
		RestDtoInterface dto = null;
		// 休憩情報毎に処理
		for (RestDtoInterface rest : rests) {
			// 開始及び終了時刻を取得
			Date startTime = rest.getRestStart();
			Date endTime = rest.getRestEnd();
			// 開始時刻が基準時刻でなく終了時刻が基準時刻(開始打刻済終了未打刻)である場合
			if (DateUtility.isSame(startTime, targetDate) == false && DateUtility.isSame(endTime, targetDate)) {
				// 設定適用エンティティを取得
				ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
				// 勤怠設定情報を取得
				TimeSettingDtoInterface timeSettingDto = application.getTimeSettingDto();
				// 処理対象情報を設定
				dto = rest;
				// 休憩終了時刻を設定
				dto.setRestEnd(AttendanceUtility.getRoundedRestEndTimeForTimeRecord(recordTime, timeSettingDto));
				// 休憩時間を計算
				dto.setRestTime(restRegist.getCalcRestTime(startTime, endTime, timeSettingDto));
				// 次の処理へ
				break;
			}
		}
		// 処理対象情報を取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// エラーメッセージ設定
			TimeMessageUtility.addErrorStartRestNotRecorded(mospParams, targetDate);
			// 処理終了
			return null;
		}
		// 休憩情報を登録
		restRegist.regist(dto);
		// 打刻日時を取得
		return recordTime;
	}
	
	@Override
	public Date recordStartPrivate(String personalId, Date recordTime) throws MospException {
		// 追加業務ロジック処理(打刻前追加処理)
		doStoredLogic(TimeConst.CODE_KEY_BEFORE_RECORD_TIME_ADDONS, personalId, recordTime);
		// 対象個人IDを設定
		this.personalId = personalId;
		// 打刻名称(エラーメッセージ用)を取得
		String recordName = TimeNamingUtility.startPrivateGoOut(mospParams);
		// 対象日を設定
		setTargetDate(recordTime, recordName);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 休憩情報リストを取得
		List<RestDtoInterface> rests = restDao.findForList(this.personalId, targetDate, TIMES_WORK_DEFAULT);
		// 公用外出リストを取得
		List<GoOutDtoInterface> publics = goOutReference.getPublicGoOutList(personalId, targetDate);
		// 私用外出リストを取得
		List<GoOutDtoInterface> privates = goOutReference.getPrivateGoOutList(personalId, targetDate);
		// 打刻時刻が休憩時間に含まれているかを確認
		checkRestDuplicate(recordTime, rests, recordName);
		// 打刻時刻が公用外出時間に含まれているかを確認
		checkGoOutDuplicate(recordTime, publics, recordName, TimeNamingUtility.getPublicGoOut(mospParams));
		// 打刻時刻が私用外出時間に含まれているかを確認
		checkGoOutDuplicate(recordTime, privates, recordName, TimeNamingUtility.getPrivateGoOut(mospParams));
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return null;
		}
		// 処理対象情報を準備
		GoOutDtoInterface dto = null;
		// 外出情報(私用)毎に処理
		for (GoOutDtoInterface goOut : privates) {
			// 開始及び終了時刻を取得
			Date startTime = goOut.getGoOutStart();
			Date endTime = goOut.getGoOutEnd();
			// 開始及び終了時刻が基準時刻でない(未打刻である)場合
			if (DateUtility.isSame(startTime, targetDate) && DateUtility.isSame(endTime, targetDate)) {
				// 処理対象情報を設定
				dto = goOut;
				// 次の処理へ
				break;
			}
			// 終了時刻が基準日時である場合(連続で入打刻をした場合)
			if (DateUtility.isSame(endTime, targetDate)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorStartPrivateAlreadyRecorded(mospParams, targetDate);
				// 処理終了
				return null;
			}
		}
		// 処理対象情報が取得できなかった場合(最大外出回数以上)
		if (MospUtility.isEmpty(dto) && MAX_GO_OUT_TIMES <= privates.size()) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorPrivateOverLimit(mospParams, targetDate);
			// 処理終了
			return null;
		}
		// 処理対象情報を取得できなかった場合(最大休憩回数未満)
		if (MospUtility.isEmpty(dto)) {
			// 処理対象情報を作成
			dto = goOutRegist.getInitDto();
			dto.setPersonalId(this.personalId);
			dto.setWorkDate(targetDate);
			dto.setTimesWork(TIMES_WORK_DEFAULT);
			dto.setGoOutType(TimeConst.CODE_GO_OUT_PRIVATE);
			dto.setTimesGoOut(privates.size() + 1);
			dto.setGoOutEnd(targetDate);
			dto.setGoOutTime(0);
		}
		// 設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
		// 勤怠設定情報を取得
		TimeSettingDtoInterface timeSettingDto = application.getTimeSettingDto();
		// 外出開始時刻を設定
		dto.setGoOutStart(AttendanceUtility.getRoundedPrivateStartTimeForTimeRecord(recordTime, timeSettingDto));
		// 休憩情報を登録
		goOutRegist.regist(dto);
		// 打刻日時を取得
		return recordTime;
	}
	
	@Override
	public Date recordEndPrivate(String personalId, Date recordTime) throws MospException {
		// 追加業務ロジック処理(打刻前追加処理)
		doStoredLogic(TimeConst.CODE_KEY_BEFORE_RECORD_TIME_ADDONS, personalId, recordTime);
		// 対象個人IDを設定
		this.personalId = personalId;
		// 打刻名称(エラーメッセージ用)を取得
		String recordName = TimeNamingUtility.endRest(mospParams);
		// 対象日を設定
		setTargetDate(recordTime, recordName);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 休憩情報リストを取得
		List<RestDtoInterface> rests = restDao.findForList(this.personalId, targetDate, TIMES_WORK_DEFAULT);
		// 公用外出リストを取得
		List<GoOutDtoInterface> publics = goOutReference.getPublicGoOutList(personalId, targetDate);
		// 私用外出リストを取得
		List<GoOutDtoInterface> privates = goOutReference.getPrivateGoOutList(personalId, targetDate);
		// 打刻時刻が休憩時間に含まれているかを確認
		checkRestDuplicate(recordTime, rests, recordName);
		// 打刻時刻が公用外出時間に含まれているかを確認
		checkGoOutDuplicate(recordTime, publics, recordName, TimeNamingUtility.getPublicGoOut(mospParams));
		// 打刻時刻が私用外出時間に含まれているかを確認
		checkGoOutDuplicate(recordTime, privates, recordName, TimeNamingUtility.getPrivateGoOut(mospParams));
		// 処理結果を確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return null;
		}
		// 処理対象情報を準備
		GoOutDtoInterface dto = null;
		// 外出情報(私用)毎に処理
		for (GoOutDtoInterface goOut : privates) {
			// 開始及び終了時刻を取得
			Date startTime = goOut.getGoOutStart();
			Date endTime = goOut.getGoOutEnd();
			// 開始時刻が基準時刻でなく終了時刻が基準時刻(開始打刻済終了未打刻)である場合
			if (DateUtility.isSame(startTime, targetDate) == false && DateUtility.isSame(endTime, targetDate)) {
				// 設定適用エンティティを取得
				ApplicationEntity application = timeMaster.getApplicationEntity(personalId, targetDate);
				// 勤怠設定情報を取得
				TimeSettingDtoInterface timeSettingDto = application.getTimeSettingDto();
				// 処理対象情報を設定
				dto = goOut;
				// 外出終了時刻を設定
				dto.setGoOutEnd(AttendanceUtility.getRoundedPrivateEndTimeForTimeRecord(recordTime, timeSettingDto));
				// 休憩時間を計算
				dto.setGoOutTime(goOutRegist.getCalcPrivateGoOutTime(startTime, endTime, timeSettingDto));
				// 次の処理へ
				break;
			}
		}
		// 処理対象情報を取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// エラーメッセージ設定
			TimeMessageUtility.addErrorStartPrivateNotRecorded(mospParams, targetDate);
			// 処理終了
			return null;
		}
		// 外出情報を登録
		goOutRegist.regist(dto);
		// 打刻日時を取得
		return recordTime;
	}
	
	@Override
	public Date recordRegularEnd(String personalId, Date recordTime) throws MospException {
		// 追加業務ロジック処理(打刻前追加処理)
		doStoredLogic(TimeConst.CODE_KEY_BEFORE_RECORD_TIME_ADDONS, personalId, recordTime);
		// 対象個人ID設定
		this.personalId = personalId;
		// 対象日設定及び対象日の勤怠情報取得
		AttendanceDtoInterface dto = setTargetDate(recordTime, TimeMessageUtility.getNameRegularEnd(mospParams));
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ポータル時刻を打刻
		recordPortalTime(personalId, targetDate, recordTime, PortalTimeCardBean.RECODE_END_WORK);
		// 休憩戻確認
		checkRestEnd();
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフロー番号設定(自己承認)
		applyAndApprove(dto);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 申請エンティティを取得
		RequestEntityInterface requestEntity = requestUtil.getRequestEntity(personalId, targetDate);
		// 勤怠情報の勤務形態コードから勤務形態エンティティを取得
		WorkTypeEntityInterface workTypeEntity = workTypeReference.getWorkTypeEntity(dto.getWorkTypeCode(), targetDate);
		// 遅刻及び早退を確認
		if (isLate(requestEntity, workTypeEntity, dto.getStartTime())
				|| isLeaveEarly(requestEntity, workTypeEntity, recordTime)) {
			// 設定適用エンティティを取得
			ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
			// 勤怠設定情報を取得
			TimeSettingDtoInterface timeSettingDto = applicationEntity.getTimeSettingDto();
			// 終業時刻(丸め)を取得
			Date targetTime = AttendanceUtility.getRoundedEndTimeForTimeRecord(recordTime, timeSettingDto);
			// MosP処理情報に勤怠詳細画面へ遷移するためのパラメータを設定
			setNextCommandForAttendanceCard(personalId, targetDate);
			// MosP処理情報に終業時刻(丸め)を設定
			mospParams.addGeneralParam(TimeConst.PRM_TARGET_TIME, targetTime);
			// エラーメッセージ設定
			TimeMessageUtility.addErrorSelfApproveFailed(mospParams);
			return null;
		}
		// 勤務形態の終業時刻を取得
		Date endTime = workTypeEntity.getEndTime(requestEntity);
		// 振出・休出申請(休出申請)がある場合
		if (requestEntity.getWorkOnHolidayStartTime(false) != null) {
			// 設定適用エンティティ取得
			ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
			// 打刻時刻から終業時刻を取得
			endTime = getEndTime(applicationEntity, requestEntity, workTypeEntity, recordTime);
		}
		// 登録用勤怠データを取得(各種自動計算実施)
		dto = getAttendanceDto(dto.getStartTime(), dto.getActualStartTime(), endTime, false, true, false, false, false);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態情報が保持している直行直帰を勤怠データに設定
		setDirectStartEnd(dto, workTypeEntity);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 始業・終業必須チェック
		attendanceRegist.checkTimeExist(dto);
		// 妥当性チェック
		attendanceRegist.checkValidate(dto);
		// 申請の相関チェック
		attendanceRegist.checkAppli(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態が所定休出もしくは法定休出の場合、休憩時間チェックを行う
		checkRestTime(dto, personalId, recordTime, targetDate);
		// 勤怠データ登録
		attendanceRegist.regist(dto);
		// 勤怠トランザクション登録
		attendanceTransactionRegist.regist(dto);
		// 代休登録
		registSubHoliday(dto);
		// 勤怠申請後処理群を実行
		afterApplyAttendance(dto);
		// 追加処理
		doAdditionalLogic(TimeConst.CODE_KEY_PORTAL_ATTENDANCE_CARD_ADDONS,
				TimeConst.CODE_KEY_ADD_ATTENDANCE_REGIST_REGIST_ADDON_TABLE, targetDate);
		return recordTime;
	}
	
	/**
	 * 遅刻であるかを確認する。<br>
	 * @param requestEntity  申請エンティティ
	 * @param workTypeEntity 勤務形態エンティティ
	 * @param startTime      始業時刻
	 * @return 確認結果(true：遅刻である、false：そうでない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected boolean isLate(RequestEntityInterface requestEntity, WorkTypeEntityInterface workTypeEntity,
			Date startTime) throws MospException {
		// 始業時刻が設定されていない場合
		if (MospUtility.isEmpty(startTime)) {
			// 遅刻ではないと判断(始業が設定されていないと遅刻の判断ができない)
			return false;
		}
		// 勤怠情報及び各種申請から始業時刻を取得
		Date scheduledStartTime = workTypeEntity.getStartTime(requestEntity);
		// 遅刻確認
		boolean isLate = scheduledStartTime.before(startTime);
		// 直行(勤怠データ)を確認
		if (requestEntity.isAttendanceDirectStart()) {
			isLate = false;
		}
		// 直行(勤務形態)を確認
		if (workTypeEntity.isDirectStart()) {
			isLate = false;
		}
		// 休日出勤申請を確認
		if (requestEntity.getWorkOnHolidayStartTime(false) != null) {
			isLate = false;
		}
		// 時短時間1(有給)が設定されている場合
		if (workTypeEntity.isShort1TimeSet() && workTypeEntity.isShort1TypePay()) {
			// 時短時間1(有給)終了時刻を取得(勤務日時刻に調整)
			Date short1EndTime = TimeUtility.getDateTime(targetDate, workTypeEntity.getShort1EndTime());
			// 終業時刻が時短時間1終了時刻以前の場合
			if (startTime.after(short1EndTime) == false) {
				isLate = false;
			}
		}
		return isLate;
	}
	
	/**
	 * 早退であるかを確認する。<br>
	 * @param requestEntity  申請エンティティ
	 * @param workTypeEntity 勤務形態エンティティ
	 * @param endTime        終業時刻
	 * @return 確認結果(true：早退である、false：そうでない)
	 * @throws MospException 日付の変換に失敗した場合
	 */
	protected boolean isLeaveEarly(RequestEntityInterface requestEntity, WorkTypeEntityInterface workTypeEntity,
			Date endTime) throws MospException {
		// 勤怠情報及び各種申請から終業時刻を取得
		Date scheduledEndTime = workTypeEntity.getEndTime(requestEntity);
		// 早退確認
		boolean isLeaveEarly = scheduledEndTime.after(endTime);
		// 直帰(勤怠データ)を確認
		if (requestEntity.isAttendanceDirectEnd()) {
			isLeaveEarly = false;
		}
		// 直帰(勤務形態)を確認
		if (workTypeEntity.isDirectEnd()) {
			isLeaveEarly = false;
		}
		// 休日出勤申請を確認
		if (requestEntity.getWorkOnHolidayStartTime(false) != null) {
			isLeaveEarly = false;
		}
		// 時短時間2(有給)が設定されている場合
		if (workTypeEntity.isShort2TimeSet() && workTypeEntity.isShort2TypePay()) {
			// 時短時間2(有給)開始時刻を取得(勤務日時刻に調整)
			Date short2StartTime = TimeUtility.getDateTime(targetDate, workTypeEntity.getShort2StartTime());
			// 終業時刻が時短時間2開始時刻以後の場合
			if (endTime.before(short2StartTime) == false) {
				isLeaveEarly = false;
			}
		}
		return isLeaveEarly;
	}
	
	@Override
	public void recordOverEnd(String personalId, Date recordTime) throws MospException {
		// 追加業務ロジック処理(打刻前追加処理)
		doStoredLogic(TimeConst.CODE_KEY_BEFORE_RECORD_TIME_ADDONS, personalId, recordTime);
		// 対象個人ID設定
		this.personalId = personalId;
		// 対象日設定及び対象日の勤怠情報取得
		AttendanceDtoInterface dto = setTargetDate(recordTime, TimeMessageUtility.getNameOverEnd(mospParams));
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 勤怠データが有り終業が設定されている場合等
			return;
		}
		// ポータル時刻を打刻
		recordPortalTime(personalId, targetDate, recordTime, PortalTimeCardBean.RECODE_END_WORK);
		// 設定適用エンティティを取得
		ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
		// 勤怠設定情報を取得
		TimeSettingDtoInterface timeSettingDto = applicationEntity.getTimeSettingDto();
		// 終業時刻(丸め)を取得
		Date targetTime = AttendanceUtility.getRoundedEndTimeForTimeRecord(recordTime, timeSettingDto);
		// MosP処理情報に勤怠詳細画面へ遷移するためのパラメータを設定
		setNextCommandForAttendanceCard(personalId, targetDate);
		// MosP処理情報に終業時刻(丸め)を設定
		mospParams.addGeneralParam(TimeConst.PRM_TARGET_TIME, targetTime);
		// 勤怠情報がある場合
		if (dto != null && dto.getWorkTypeCode() != null) {
			// 勤務形態コードを取得
			String workTypeCode = dto.getWorkTypeCode();
			// 勤怠情報の勤務形態コードから勤務形態エンティティを取得
			WorkTypeEntityInterface workTypeEntity = workTypeReference.getWorkTypeEntity(workTypeCode, targetDate);
			// MosP処理情報に前出を設定
			if (workTypeEntity.isDirectStart()) {
				// 前出フラグを設定
				mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_DIRECT_START, MospConst.CHECKBOX_ON);
			}
			// 勤務形態の後出設定を確認
			if (workTypeEntity.isDirectEnd()) {
				// 後出フラグを設定
				mospParams.addGeneralParam(TimeConst.PRM_TRANSFERRED_DIRECT_END, MospConst.CHECKBOX_ON);
			}
		}
		// 追加処理
		doAdditionalLogic(TimeConst.CODE_KEY_PORTAL_ATTENDANCE_CARD_ADDONS,
				TimeConst.CODE_KEY_ADD_ATTENDANCE_REGIST_REGIST_ADDON_TABLE, targetDate);
	}
	
	@Override
	public Date recordRegularWork(String personalId, Date recordTime) throws MospException {
		// 追加業務ロジック処理(打刻前追加処理)
		doStoredLogic(TimeConst.CODE_KEY_BEFORE_RECORD_TIME_ADDONS, personalId, recordTime);
		// 対象個人ID設定
		this.personalId = personalId;
		// 対象日設定(打刻時刻の日付)
		targetDate = getTargetDate(recordTime);
		// ポータル時刻を打刻
		recordPortalTime(personalId, targetDate, recordTime, PortalTimeCardBean.RECODE_START_WORK);
		// 勤怠データ取得
		AttendanceDtoInterface dto = attendanceReference.findForKey(this.personalId, targetDate);
		// 勤怠情報確認
		if (dto != null) {
			// エラーメッセージ設定
			TimeMessageUtility.addErrorStartWorkAlreadyRecorded(mospParams, targetDate);
			return null;
		}
		// 申請エンティティを取得
		RequestEntityInterface requestEntity = requestUtil.getRequestEntity(personalId, targetDate);
		// 勤務形態コードを取得
		String workTypeCode = requestEntity.getWorkType();
		// 勤務形態エンティティを取得(申請エンティティから勤務形態コードを取得)
		WorkTypeEntityInterface workTypeEntity = workTypeReference.getWorkTypeEntity(workTypeCode, targetDate);
		// 勤務対象勤務形態であるかを確認
		if (workTypeEntity.isWorkTypeForWork() == false) {
			// エラーメッセージ設定
			addNotWorkDateErrorMessage(targetDate);
			return null;
		}
		// 始業時刻取得
		Date startTime = workTypeEntity.getStartTime(requestEntity);
		// 終業時刻取得
		Date endTime = workTypeEntity.getEndTime(requestEntity);
		// 勤怠データ作成
		dto = getAttendanceDto(startTime, startTime, endTime, false, true, false, false, false);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態情報が保持している直行直帰を勤怠データに設定
		setDirectStartEnd(dto, workTypeEntity);
		// エラーメッセージ確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 始業・終業必須チェック
		attendanceRegist.checkTimeExist(dto);
		// 妥当性チェック
		attendanceRegist.checkValidate(dto);
		// 申請の相関チェック
		attendanceRegist.checkAppli(dto);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// ワークフロー番号設定(自己承認)
		applyAndApprove(dto);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 勤務形態に登録されている休憩情報のリストを取得
		List<RestDtoInterface> restList = registRest(startTime, endTime);
		// 休憩情報毎に処理
		for (RestDtoInterface restDto : restList) {
			// 休憩時間登録
			restRegist.regist(restDto);
		}
		// 勤務形態が所定休出もしくは法定休出の場合、休憩時間チェックを行う
		checkRestTime(dto, personalId, recordTime, targetDate);
		// 勤怠データ登録
		attendanceRegist.regist(dto);
		// 勤怠トランザクション登録
		attendanceTransactionRegist.regist(dto);
		// 代休登録
		registSubHoliday(dto);
		// 追加処理
		doAdditionalLogic(TimeConst.CODE_KEY_PORTAL_ATTENDANCE_CARD_ADDONS,
				TimeConst.CODE_KEY_ADD_ATTENDANCE_REGIST_REGIST_ADDON_TABLE, targetDate);
		return recordTime;
	}
	
	@Override
	public Date recordStartWork() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordStartWork(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public Date recordEndWork() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordEndWork(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public Date recordStartRest() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordStartRest(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public Date recordEndRest() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordEndRest(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public Date recordRegularEnd() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordRegularEnd(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public void recordOverEnd() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		recordOverEnd(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	@Override
	public Date recordRegularWork() throws MospException {
		// 対象個人ID(ログインユーザの個人ID)及び打刻日時(システム日時)を取得し打刻
		return recordRegularWork(mospParams.getUser().getPersonalId(), getSystemTimeAndSecond());
	}
	
	/**
	 * 打刻対象日を設定する。<br>
	 * <br>
	 * 打刻した日の勤怠情報が存在し終業時間が登録されていない場合は、
	 * 打刻した日が打刻対象日となる。<br>
	 * <br>
	 * 打刻した日の勤怠情報が存在しない場合、前日の勤怠情報を確認する。<br>
	 * 前日の勤怠情報が存在し終業時間が登録されていない場合は、
	 * 前日が打刻対象日となる。<br>
	 * <br>
	 * 上記の場合以外は、打刻対象日を設定しない。<br>
	 * <br>
	 * @param recordTime 打刻日時
	 * @param process    打刻対象処理
	 * @return 打刻対象日の勤怠情報
	 * @throws MospException 勤怠情報の取得に失敗した場合
	 */
	protected AttendanceDtoInterface setTargetDate(Date recordTime, String process) throws MospException {
		// 勤怠データ取得
		AttendanceDtoInterface dto = attendanceReference.findForKey(personalId, recordTime);
		// 打刻した日の勤怠情報が存在しない場合
		if (dto == null) {
			// 前日の勤怠を取得
			Date beforeDate = addDay(recordTime, -1);
			dto = attendanceReference.findForKey(personalId, beforeDate);
			// 前日の勤怠を確認
			if (dto != null && dto.getEndTime() == null) {
				// 対象日として前日を設定(深夜に日付を超えて打刻した場合等)
				targetDate = beforeDate;
			} else {
				// エラーメッセージ設定
				TimeMessageUtility.addErrorStratWorkNotRecorded(mospParams, recordTime, process);
			}
		} else if (dto.getEndTime() != null) {
			// エラーメッセージ設定(終業時刻が既に登録されている場合)
			TimeMessageUtility.addErrorEndWorkAlreadyRecorded(mospParams, recordTime);
		} else {
			targetDate = DateUtility.getDate(recordTime);
		}
		return dto;
	}
	
	/**
	 * 打刻対象日を取得する。<br>
	 * <br>
	 * @param recordTime 打刻日時
	 * @return 打刻対象日
	 * @throws MospException 勤怠情報の取得に失敗した場合。
	 */
	protected Date getTargetDate(Date recordTime) throws MospException {
		targetDate = DateUtility.getDate(recordTime);
		if (doAdditionalLogic(TimeConst.CODE_KEY_PORTAL_ATTENDANCE_CARD_ADDONS,
				TimeConst.CODE_KEY_ADD_ATTENDANCE_GET_TARGET_DATE_ON_START_WORK, targetDate, recordTime, personalId)) {
			// 追加処理が処理した場合は、決定された対象日を取得
			targetDate = (Date)mospParams.getGeneralParam(PlatformConst.PRM_TARGET_DATE);
		}
		return targetDate;
	}
	
	/**
	 * 勤務形態情報が保持している直行直帰を勤怠データに設定する。<br>
	 * @param dto            勤怠データ
	 * @param workTypeEntity 勤務形態エンティティ
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void setDirectStartEnd(AttendanceDtoInterface dto, WorkTypeEntityInterface workTypeEntity)
			throws MospException {
		// 勤務形態の直行設定を確認
		if (workTypeEntity.isDirectStart()) {
			// 勤怠データに直行フラグを設定
			dto.setDirectStart(MospUtility.getInt(MospConst.CHECKBOX_ON));
		}
		// 勤務形態の直帰設定を確認
		if (workTypeEntity.isDirectEnd()) {
			// 勤怠データに直帰フラグを設定
			dto.setDirectEnd(MospUtility.getInt(MospConst.CHECKBOX_ON));
		}
	}
	
	/**
	 * 打刻時刻が外出時間に含まれているかを確認する。<br>
	 * 含まれている場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param recordTime 打刻時刻
	 * @param dtos       外出情報リスト
	 * @param recordName 打刻名称(エラーメッセージ用)
	 * @param timeName   重複時間名称(エラーメッセージ用)
	 */
	protected void checkGoOutDuplicate(Date recordTime, List<GoOutDtoInterface> dtos, String recordName,
			String timeName) {
		// 外出情報毎に処理
		for (GoOutDtoInterface dto : dtos) {
			// 必要な情報を取得
			Date startTime = dto.getGoOutStart();
			Date endTime = dto.getGoOutEnd();
			// エラーメッセージ用の必要な情報(回数)を取得
			int times = dto.getGoOutTime();
			// 打刻時刻が開始時刻及び終了時刻に含まれているかを確認
			checkDuplicate(recordTime, startTime, endTime, times, recordName, timeName);
		}
	}
	
	/**
	 * 打刻時刻が休憩時間に含まれているかを確認する。<br>
	 * 含まれている場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param recordTime 打刻時刻
	 * @param dtos       外出情報リスト
	 * @param recordName 打刻名称(エラーメッセージ用)
	 */
	protected void checkRestDuplicate(Date recordTime, List<RestDtoInterface> dtos, String recordName) {
		// エラーメッセージ用の必要な情報(重複時間名称)を取得
		String timeName = TimeNamingUtility.rest(mospParams);
		// 休憩情報毎に処理
		for (RestDtoInterface dto : dtos) {
			// 必要な情報を取得
			Date startTime = dto.getRestStart();
			Date endTime = dto.getRestEnd();
			// エラーメッセージ用の必要な情報(回数)を取得
			int times = dto.getRestTime();
			// 打刻時刻が開始時刻及び終了時刻に含まれているかを確認
			checkDuplicate(recordTime, startTime, endTime, times, recordName, timeName);
		}
	}
	
	/**
	 * 打刻時刻が開始時刻及び終了時刻に含まれているかを確認する。<br>
	 * 含まれている場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * 但し、開始時刻か終了時刻が基準時刻(対象日)と同じである場合である場合は、含まれていないと判断する。<br>
	 * @param recordTime  打刻時刻
	 * @param startTime   開始時刻
	 * @param endTime     終了時刻
	 * @param times       回数(エラーメッセージ用)
	 * @param recordName  打刻名称(エラーメッセージ用)
	 * @param timeName    重複時間名称(エラーメッセージ用)
	 */
	protected void checkDuplicate(Date recordTime, Date startTime, Date endTime, int times, String recordName,
			String timeName) {
		// 終了時刻が基準時刻と同じ(未打刻か入のみ打刻)である場合
		if (DateUtility.isSame(endTime, targetDate)) {
			// 打刻時刻が開始時刻及び終了時刻に含まれていないと判断
			return;
		}
		// 打刻時刻が開始及び終了時刻に含まれる場合
		if (DateUtility.isTermContain(recordTime, startTime, endTime)) {
			// 重複時間名称に回数を設定
			StringBuilder sb = new StringBuilder(timeName).append(times);
			// エラーメッセージを設定
			TimeMessageUtility.addErrorOverlap1(mospParams, recordTime, recordName, sb.toString());
		}
	}
	
	/**
	 * 休憩開始時刻重複エラーメッセージを追加する。<br>
	 * @param stringTagetDate 表示用対象日付
	 * @param name 置換文字列
	 */
	protected void addStartRestDuplicationErrorMessage(String stringTagetDate, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("RestTime"));
		sb.append(mospParams.getName("Into"));
		String[] rep = { stringTagetDate, sb.toString(), name };
		mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
	}
	
	/**
	 * 休憩終了時刻重複エラーメッセージを追加する。<br>
	 * @param stringTagetDate 表示用対象日付
	 * @param name 置換文字列
	 */
	protected void addEndRestDuplicationErrorMessage(String stringTagetDate, String name) {
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("RestTime"));
		sb.append(mospParams.getName("Return"));
		String[] rep = { stringTagetDate, sb.toString(), name };
		mospParams.addErrorMessage(TimeMessageConst.MSG_TIME_DUPLICATION_CHECK, rep);
	}
	
	/**
	 * ポータル時刻を打刻する。<br>
	 * @param personalId 個人ID
	 * @param workDate 勤務日
	 * @param recordType 打刻区分
	 * @param recordTime 打刻時刻
	 * @throws MospException インスタンスの取得及びSQL実行に失敗した場合
	 */
	protected void recordPortalTime(String personalId, Date workDate, Date recordTime, String recordType)
			throws MospException {
		// 既に打刻されている場合
		if (timeRecordReference.findForKey(personalId, workDate, recordType) != null) {
			// 処理終了
			return;
		}
		// 打刻情報を作成
		TimeRecordDtoInterface dto = timeRecordRegist.getInitDto();
		dto.setPersonalId(personalId);
		dto.setWorkDate(workDate);
		dto.setTimesWork(TIMES_WORK_DEFAULT);
		dto.setRecordType(recordType);
		dto.setRecordTime(recordTime);
		// 新規登録
		timeRecordRegist.insert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// コミット
		timeRecordRegist.commit();
	}
	
	/**
	 * 休憩の確認を行う。<br>
	 * <br>
	 * 休憩入のみが登録されていて休憩戻が登録されていない場合、
	 * エラーメッセージを設定する。
	 * <br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkRestEnd() throws MospException {
		// 休憩情報取得(0も登録されている)
		List<RestDtoInterface> rests = restDao.findForList(personalId, targetDate, TIMES_WORK_DEFAULT);
		// 休憩情報毎に処理
		for (RestDtoInterface rest : rests) {
			// 休憩戻り確認
			if (DateUtility.isSame(rest.getRestStart(), targetDate) == false
					&& DateUtility.isSame(rest.getRestEnd(), targetDate)) {
				// エラーメッセージ設定
				TimeMessageUtility.addErrorEndRestNotRecorded(mospParams, targetDate);
				return;
			}
		}
		// 私用外出リストを取得
		List<GoOutDtoInterface> privates = goOutReference.getPrivateGoOutList(personalId, targetDate);
		// 外出情報毎に処理
		for (GoOutDtoInterface goOut : privates) {
			// 外出戻り確認
			if (DateUtility.isSame(goOut.getGoOutStart(), targetDate) == false
					&& DateUtility.isSame(goOut.getGoOutEnd(), targetDate)) {
				// エラーメッセージ設定
				TimeMessageUtility.addErrorEndRestNotRecorded(mospParams, targetDate);
				return;
			}
		}
	}
	
	/**
	 * 対象勤怠データ情報に対し、
	 * ワークフロー(自己承認)を作成して、ワークフロー番号を設定する。<br>
	 * @param dto 対象勤怠データ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void applyAndApprove(AttendanceDtoInterface dto) throws MospException {
		// ワークフロー情報準備
		WorkflowDtoInterface workflowDto = workflowRegist.getInitDto();
		// ワークフロー情報確認
		if (dto.getWorkflow() != 0) {
			// ワークフロー情報取得(新規でない場合)
			workflowDto = workflow.getLatestWorkflowInfo(dto.getWorkflow());
		}
		// 承認者を設定
		workflowDto.setApproverId(PlatformConst.APPROVAL_ROUTE_SELF);
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
	
	/**
	 * 休日出勤の場合、休憩時間が0分であればエラーメッセージを格納する。<br>
	 * @param dto	勤怠データDTO
	 * @param personalId	対象個人ID
	 * @param recordTime	打刻日時
	 * @param targetDate	対象日
	 * @throws MospException	インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkRestTime(AttendanceDtoInterface dto, String personalId, Date recordTime, Date targetDate)
			throws MospException {
		// 勤務形態が所定休出もしくは法定休出の場合、休憩時間チェックを行う
		if (TimeUtility.isWorkOnLegalOrPrescribedHoliday(dto.getWorkTypeCode())) {
			//勤務時間が6時間を超えるかつ休憩時間が0分の場合
			if (dto.getWorkTime() > TimeConst.TIME_WORK_TIME_6 && dto.getRestTime() == 0) {
				// 設定適用エンティティを取得
				ApplicationEntity applicationEntity = applicationReference.getApplicationEntity(personalId, targetDate);
				// 勤怠設定情報を取得
				TimeSettingDtoInterface timeSettingDto = applicationEntity.getTimeSettingDto();
				// 終業時刻(丸め)を取得
				Date targetTime = AttendanceUtility.getRoundedEndTimeForTimeRecord(recordTime, timeSettingDto);
				// MosP処理情報に勤怠詳細画面へ遷移するためのパラメータを設定
				setNextCommandForAttendanceCard(personalId, targetDate);
				// MosP処理情報に終業時刻(丸め)を設定
				mospParams.addGeneralParam(TimeConst.PRM_TARGET_TIME, targetTime);
				// エラーメッセージ設定
				TimeMessageUtility.addErrorShortRestTimeForWorkOnHoliday(mospParams);
			}
		}
	}
	
	/**
	 * 勤怠詳細画面に遷移するためのパラメータを設定する。<br>
	 * @param personalId	対象個人ID
	 * @param targetDate 	対象日
	 * @throws MospException	インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setNextCommandForAttendanceCard(String personalId, Date targetDate) throws MospException {
		// MosP処理情報に対象個人IDを設定
		mospParams.addGeneralParam(PlatformConst.PRM_TARGET_PERSONAL_ID, personalId);
		// MosP処理情報に対象日を設定
		mospParams.addGeneralParam(PlatformConst.PRM_TARGET_DATE, targetDate);
		// 勤怠詳細画面へ遷移(連続実行コマンド設定)
		mospParams.setNextCommand(AttendanceCardAction.CMD_SELECT_SHOW_FROM_PORTAL);
	}
	
}
