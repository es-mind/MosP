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

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.bean.workflow.impl.WorkflowReferenceBean;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteRegistBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdWorkOnHolidayRequestDto;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 振出・休出申請登録処理。<br>
 */
public class WorkOnHolidayRequestRegistBean extends TimeBean implements WorkOnHolidayRequestRegistBeanInterface {
	
	/**
	 * 振出・休出申請DAOクラス。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface				dao;
	
	/**
	 * 振出・休出申請参照インターフェース。<br>
	 */
	protected WorkOnHolidayRequestReferenceBeanInterface	workOnHolidayReference;
	
	/**
	 * ワークフロー参照クラス。<br>
	 */
	protected WorkflowReferenceBeanInterface				workflowReference;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface				workflowIntegrate;
	
	/**
	 * ワークフロー登録クラス
	 */
	protected WorkflowRegistBeanInterface					workflowRegist;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	protected WorkflowCommentRegistBeanInterface			workflowCommentRegist;
	
	/**
	 * 振替休日データDAOインターフェース。
	 */
	protected SubstituteDaoInterface						substituteDao;
	
	/**
	 * 振替休日データ参照インターフェース。
	 */
	protected SubstituteReferenceBeanInterface				substituteReference;
	
	/**
	 * 振替休日データ登録インターフェース。
	 */
	protected SubstituteRegistBeanInterface					substituteRegist;
	
	/**
	 * 勤怠データ登録インターフェース。
	 */
	protected AttendanceRegistBeanInterface					attendanceRegist;
	
	/**
	 * 勤怠データ参照インターフェース。
	 */
	protected AttendanceReferenceBeanInterface				attendanceReference;
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	protected ApprovalInfoReferenceBeanInterface			approvalInfoReference;
	
	/**
	 * 人事休職情報参照クラス。<br>
	 */
	protected SuspensionReferenceBeanInterface				suspensionReference;
	
	/**
	 * 人事退職情報参照クラス。<br>
	 */
	protected RetirementReferenceBeanInterface				retirementReference;
	
	/**
	 * カレンダユーティリティ処理。<br>
	 */
	protected ScheduleUtilBeanInterface						scheduleUtil;
	
	/**
	 * 勤務形態マスタ参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface				workTypeReference;
	
	/**
	 * 締日ユーティリティ。<br>
	 */
	protected CutoffUtilBeanInterface						cutoffUtil;
	
	/**
	 * 勤怠トランザクション
	 */
	protected AttendanceTransactionRegistBeanInterface		attendanceTransactionRegist;
	
	/**
	 * 代休申請情報参照インターフェース。
	 */
	protected SubHolidayRequestReferenceBeanInterface		subholidayRequestReference;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface						timeMaster;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public WorkOnHolidayRequestRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO及びBeanを準備
		dao = createDaoInstance(WorkOnHolidayRequestDaoInterface.class);
		workOnHolidayReference = createBeanInstance(WorkOnHolidayRequestReferenceBeanInterface.class);
		workflowReference = createBeanInstance(WorkflowReferenceBean.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		workflowRegist = createBeanInstance(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = createBeanInstance(WorkflowCommentRegistBeanInterface.class);
		substituteDao = createDaoInstance(SubstituteDaoInterface.class);
		substituteReference = createBeanInstance(SubstituteReferenceBeanInterface.class);
		substituteRegist = createBeanInstance(SubstituteRegistBeanInterface.class);
		attendanceRegist = createBeanInstance(AttendanceRegistBeanInterface.class);
		attendanceReference = createBeanInstance(AttendanceReferenceBeanInterface.class);
		approvalInfoReference = createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
		suspensionReference = createBeanInstance(SuspensionReferenceBeanInterface.class);
		retirementReference = createBeanInstance(RetirementReferenceBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		workTypeReference = createBeanInstance(WorkTypeReferenceBeanInterface.class);
		cutoffUtil = createBeanInstance(CutoffUtilBeanInterface.class);
		attendanceTransactionRegist = createBeanInstance(AttendanceTransactionRegistBeanInterface.class);
		subholidayRequestReference = createBeanInstance(SubHolidayRequestReferenceBeanInterface.class);
		// 勤怠関連マスタ参照処理を設定
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		setTimeMaster(timeMaster);
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface getInitDto() {
		return new TmdWorkOnHolidayRequestDto();
	}
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 振出・休出申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdWorkOnHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public List<WorkflowDtoInterface> update(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return null;
		}
		// 処理ワークフロー情報リスト準備
		List<WorkflowDtoInterface> workflowList = new ArrayList<WorkflowDtoInterface>();
		for (long id : idArray) {
			// DTOの準備
			BaseDtoInterface baseDto = findForKey(dao, id, true);
			// 排他確認
			checkExclusive(baseDto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			WorkOnHolidayRequestDtoInterface dto = (WorkOnHolidayRequestDtoInterface)baseDto;
			// 申請時の確認
			checkAppli(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ワークフロー番号から振替休日データを取得
			List<SubstituteDtoInterface> substituteList = substituteDao.findForWorkflow(dto.getWorkflow());
			int size = substituteList.size();
			if (size == 1) {
				SubstituteDtoInterface substituteDto = substituteList.get(0);
				substituteRegist.checkRegist(substituteDto);
				if (mospParams.hasErrorMessage()) {
					continue;
				}
				// 勤怠を削除
				attendanceRegist.delete(substituteDto.getPersonalId(), substituteDto.getSubstituteDate());
			}
			// ワークフローDTOの準備
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(dto.getWorkflow());
			// 申請
			workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
					PlatformConst.WORKFLOW_TYPE_TIME, null);
			if (!substituteList.isEmpty()) {
				// 勤怠トランザクション登録
				attendanceTransactionRegist.regist(substituteList.get(0));
			}
			// 処理ワークフロー情報リストへ追加
			if (workflowDto != null) {
				workflowList.add(workflowDto);
			}
		}
		return workflowList;
	}
	
	@Override
	public void regist(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getTmdWorkOnHolidayRequestId(), false) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 履歴追加
			add(dto);
		}
	}
	
	/**
	 * 履歴追加を行う。<br>
	 * @param dto 振出・休出申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdWorkOnHolidayRequestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdWorkOnHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdWorkOnHolidayRequestId());
	}
	
	@Override
	public void withdrawn(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		for (long id : idArray) {
			// DTOの準備
			BaseDtoInterface baseDto = findForKey(dao, id, true);
			checkExclusive(baseDto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			WorkOnHolidayRequestDtoInterface dto = (WorkOnHolidayRequestDtoInterface)baseDto;
			// 取下時の確認
			checkWithdrawn(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ワークフローDTOの準備
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
			// 取下
			workflowDto = workflowRegist.withdrawn(workflowDto);
			if (workflowDto != null) {
				// ワークフローコメント登録
				workflowCommentRegist.addComment(workflowDto, mospParams.getUser().getPersonalId(),
						PfMessageUtility.getWithdrawSucceed(mospParams));
			}
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestDate()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdWorkOnHolidayRequestId());
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdWorkOnHolidayRequestId());
	}
	
	@Override
	public void validate(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 基本情報のチェック
		workOnHolidayReference.chkBasicInfo(dto.getPersonalId(), dto.getRequestDate());
	}
	
	@Override
	public void checkValidate(WorkOnHolidayRequestDtoInterface dto) {
		// 必須確認(休日出勤日)
		checkRequired(dto.getRequestDate(), PfNameUtility.workDay(mospParams), null);
	}
	
	@Override
	public void checkSetRequestDate(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 申請エンティティ(出勤日)を取得
		RequestUtilBeanInterface requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		RequestEntityInterface request = requestUtil.getRequestEntity(dto.getPersonalId(), dto.getRequestDate());
		// 申請日設定時の確認処理
		checkSetRequestDate(dto, request);
	}
	
	/**
	 * 申請日設定時の確認処理を行う。<br>
	 * @param dto     振出・休出申請情報
	 * @param request 申請エンティティ(出勤日で作成したもの)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkSetRequestDate(WorkOnHolidayRequestDtoInterface dto, RequestEntityInterface request)
			throws MospException {
		// 入社チェック
		checkEntered(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 退職チェック
		checkRetired(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休職チェック
		checkSuspended(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 仮締チェック
		checkTightenForSetRequestDate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 重複チェック
		checkWorkOnHolidayOverlap(dto, request);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休日チェック
		checkHolidayDate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠チェック
		checkAttendance(dto, request);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休暇申請チェック
		checkHolidayRequest(dto, request);
	}
	
	@Override
	public void checkDraft(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 申請エンティティ(出勤日)を取得
		RequestUtilBeanInterface requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		RequestEntityInterface request = requestUtil.getRequestEntity(dto.getPersonalId(), dto.getRequestDate());
		// 下書時の確認処理
		checkDraft(dto, request);
	}
	
	/**
	 * 下書時の確認処理を行う。<br>
	 * @param dto     振出・休出申請情報
	 * @param request 申請エンティティ(出勤日で作成したもの)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDraft(WorkOnHolidayRequestDtoInterface dto, RequestEntityInterface request)
			throws MospException {
		// 申請日設定時の確認処理
		checkSetRequestDate(dto, request);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替出勤チェック
		checkLegalHolidayDate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態チェック
		checkWorkTypeCode(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		checkTemporaryClosingFinal(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加業務ロジック処理(振出・休出申請登録追加処理)
		doStoredLogic(TimeConst.CODE_KEY_WORK_ON_HOLIDAY_REQUEST_REGIST_ADDONS, dto, request);
	}
	
	@Override
	public void checkAppli(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 申請エンティティ(出勤日)を取得
		RequestUtilBeanInterface requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		RequestEntityInterface request = requestUtil.getRequestEntity(dto.getPersonalId(), dto.getRequestDate());
		// 下書時の確認処理
		checkDraft(dto, request);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休日出勤申請の項目の必須チェック。
		checkRequired(dto);
		// 半日振替時のチェック
		checkHalfSubstitute(dto, request);
		// 勤怠の下書きチェック
		checkAttendanceDraft(dto, request);
	}
	
	/**
	 * 半日振替時のチェックを行う。<br>
	 * ・半日振替→振替申請(全日)、休出申請禁止<br>
	 * ・半日/半日→振替申請(全日)、休出申請禁止<br>
	 * ・出勤日の範囲(午前/午後)確認<br>
	 * @param dto     振出・休出申請情報
	 * @param request 申請エンティティ(出勤日で作成したもの)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkHalfSubstitute(WorkOnHolidayRequestDtoInterface dto, RequestEntityInterface request)
			throws MospException {
		// 半日振替でない場合
		if (!workOnHolidayReference.useHalfSubstitute()) {
			return;
		}
		// 個人ID・対象日取得
		Date workDate = dto.getRequestDate();
		int substitute = dto.getSubstitute();
		// 勤務日が振替休日の振替申請情報承認済リストを取得
		List<SubstituteDtoInterface> beforeList = request.getSubstituteList(WorkflowUtility.getCompletedStatuses());
		if (beforeList.isEmpty()) {
			return;
		}
		// 振替の振替の場合
		// 出勤日（振替休日）が半振休フラグ準備
		boolean isHalfSubstitute = false;
		// 出勤日（振替休日）振替申請休暇範囲取得
		int beforeHolidayRange = beforeList.get(0).getHolidayRange();
		// 出勤日（振替休日）が半振休か確認
		isHalfSubstitute = beforeHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
				|| beforeHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM;
		// 申請日が半振休の振替であり、午前、午後でない場合
		if (isHalfSubstitute && substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				&& substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// [勤務日]は全日の振替休日ではないため、全日の振替及び休日出勤は出来ません。申請区分または範囲を選択し直してください。
			mospParams.addErrorMessage(TimeMessageConst.MSG_ALL_DAYS_APPLICATION_IN_HALF_HOLIDAY,
					getStringDate(workDate));
			return;
		}
		// 半振時の休暇範囲チェック
		halfHolidayRangeCheck(dto, beforeList);
	}
	
	/**
	 * 半日振替出勤日に勤怠申請があるか確認する。<br>
	 * 勤怠の下書きがある場合は下書きの削除を行う。<br>
	 * @param dto     振出・休出申請情報
	 * @param request 申請エンティティ(出勤日で作成したもの)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkAttendanceDraft(WorkOnHolidayRequestDtoInterface dto, RequestEntityInterface request)
			throws MospException {
		// 半日振替申請でない場合
		int substitute = dto.getSubstitute();
		// 振替出勤(午前)でなく且つ振替出勤(午後)でない場合
		if (substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				&& substitute != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 処理なし
			return;
		}
		// 出勤日に勤怠申請が有り申請済でない(下書か一次戻である)場合
		if (request.hasAttendance() && request.isAttendanceApplied() == false) {
			// 出勤日の勤怠情報を削除
			attendanceRegist.delete(dto.getPersonalId(), dto.getRequestDate());
		}
	}
	
	/**
	 * 半振時の休暇範囲確認チェック
	 * @param dto 対象休出申請情報
	 * @param beforeList 申請日が振替休日になる振替申請情報(承認済)
	 */
	protected void halfHolidayRangeCheck(WorkOnHolidayRequestDtoInterface dto,
			List<SubstituteDtoInterface> beforeList) {
		// 休暇範囲準備
		int range = dto.getSubstitute();
		// 休暇範囲設定
		if (range == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM) {
			range = TimeConst.CODE_HOLIDAY_RANGE_AM;
		} else if (range == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			range = TimeConst.CODE_HOLIDAY_RANGE_PM;
		} else {
			// 半振休以外処理なし
			return;
		}
		// 振替：休暇範囲取得
		int substituteRange = beforeList.get(0).getSubstituteRange();
		// 全休の場合
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			return;
		}
		// 振替申請が1つかつ休暇範囲が違う場合
		if (beforeList.size() == 1 && range != substituteRange) {
			// エラーメッセージ
			String rep[] = { getStringDate(dto.getRequestDate()), TimeNamingUtility.substituteHolidayRange(mospParams),
				PfNameUtility.application(mospParams) };
			// [勤務日]は振替休日範囲が異なるため申請できません。
			mospParams.addErrorMessage(TimeMessageConst.MSG_REASON_NOT_ACTION, rep);
			return;
		}
	}
	
	@Override
	public void checkCancelAppli(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 仮締確認
		checkTemporaryClosingFinal(dto);
		// 取消時の確認処理
		checkCancel(dto);
	}
	
	@Override
	public void checkWithdrawn(WorkOnHolidayRequestDtoInterface dto) {
		// 処理無し
	}
	
	@Override
	public void checkApproval(WorkOnHolidayRequestDtoInterface dto) {
		// 処理無し
	}
	
	@Override
	public void checkCancelApproval(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を実行
		checkCancelAppli(dto);
	}
	
	@Override
	public void checkCancel(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 個人ID取得
		String personalId = dto.getPersonalId();
		// 休日出勤日取得
		Date workDate = dto.getRequestDate();
		// 休日出勤申請である場合
		if (dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 代休申請確認
			checkSubHolidayRequest(personalId, workDate);
			return;
		}
		// 休日出勤日確認
		checkWorkDate(personalId, workDate);
		// 振替出勤情報準備
		SubstituteDtoInterface substituteDto;
		// 承認解除対象振替出勤情報取得
		substituteDto = substituteReference.getSubstituteDto(personalId, workDate);
		// 承認解除対象の振替日取得
		Date substituteDate = substituteDto.getSubstituteDate();
		// 振替休日確認
		checkSubstituteDate(personalId, substituteDate);
		// 承認解除対象の振替日で更に振替がされている情報リスト取得
		substituteDto = substituteReference.getSubstituteDto(personalId, substituteDate);
		// 再振替情報がある場合
		if (substituteDto != null) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(substituteDto.getWorkflow());
			// 取下又は下書又は一次戻の場合
			if (WorkflowUtility.isWithDrawn(workflowDto) || WorkflowUtility.isDraft(workflowDto)
					|| WorkflowUtility.isFirstReverted(workflowDto)) {
				return;
			}
			// エラーメッセージ（承認解除できない）追加
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER);
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER_2);
		} else {
			// 再振替申請されていない場合
			// 休日出勤、残業処理がされている情報取得
			WorkOnHolidayRequestDtoInterface workOnHolidayDto = workOnHolidayReference.findForKeyOnWorkflow(personalId,
					substituteDate);
			// 情報が存在しない場合
			if (workOnHolidayDto == null) {
				return;
			}
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(workOnHolidayDto.getWorkflow());
			// 取下・下書・一次戻の場合
			if (WorkflowUtility.isWithDrawn(workflowDto) || WorkflowUtility.isDraft(workflowDto)
					|| WorkflowUtility.isFirstReverted(workflowDto)) {
				// 処理なし
				return;
			}
			// エラーメッセージ（他承認状態の場合は承認解除できない）追加
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER);
			mospParams.addErrorMessage(TimeMessageConst.MSG_WORK_ON_HOLIDAY_NOT_APPROVER_2);
		}
		return;
	}
	
	/**
	 * 休日出勤日に各種申請が存在するか確認する。<br>
	 * 休日出勤日に勤怠申請、休暇申請、代休申請が存在する場合エラーとする。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * @param personalId 個人ID
	 * @param workDate 休日出勤日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 * 
	 */
	protected void checkWorkDate(String personalId, Date workDate) throws MospException {
		// 休日出勤日に勤怠申請がある場合
		if (approvalInfoReference.isExistAttendanceTargetDate(personalId, workDate)) {
			// エラーメッセージを設定
			addOthersRequestErrorMessage(workDate, TimeNamingUtility.getWorkManage(mospParams));
		}
		// 申請ユーティリティを準備
		RequestUtilBeanInterface requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, workDate);
		// 出勤日に休暇申請が存在するかを確認
		checkHolidayRequest(requestUtil, workDate);
		// 出勤日に代休申請が存在するかを確認
		checkSubHolidayRequest(requestUtil, workDate);
		// 出勤日に勤務形態変更申請が存在するかを確認
		checkWorkTypeChangeRequest(requestUtil, workDate);
		// 出勤日に残業申請が存在するかを確認
		checkOverTimeRequest(requestUtil, workDate);
		// 出勤日に時差出勤申請が存在するかを確認
		checkDifferenceRequest(requestUtil, workDate);
	}
	
	/**
	 * 振替休日に各種申請が存在するか確認する。<br>
	 * 振替休日に勤怠申請、休暇申請が存在する場合エラーとする。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * @param personalId 個人ID
	 * @param substituteDate 振替休日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 * 
	 */
	protected void checkSubstituteDate(String personalId, Date substituteDate) throws MospException {
		// 勤怠申請チェック
		checkSubstituteAttendance(personalId, substituteDate);
		// 申請ユーティリティクラス準備
		RequestUtilBeanInterface requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, substituteDate);
		// 振替休日に休暇申請が存在するかを確認
		checkHolidayRequest(requestUtil, substituteDate);
		// 振替休日に代休申請が存在するかを確認
		checkSubHolidayRequest(requestUtil, substituteDate);
	}
	
	/**
	 * 休暇申請が存在するかを確認する。<br>
	 * 対象日に休暇申請が存在する場合、MosP処理情報にエラーメッセージを設定する。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @param targetDate  対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkHolidayRequest(RequestUtilBeanInterface requestUtil, Date targetDate) throws MospException {
		// 休暇申請リストを取得
		List<HolidayRequestDtoInterface> holidayList = requestUtil.getHolidayList(false);
		// 休暇申請が存在する場合
		if (MospUtility.isEmpty(holidayList) == false) {
			// エラーメッセージ追加
			addOthersRequestErrorMessage(targetDate, TimeNamingUtility.getVacation(mospParams));
		}
	}
	
	/**
	 * 代休申請が存在するかを確認する。<br>
	 * 対象日に代休申請が存在する場合、MosP処理情報にエラーメッセージを設定する。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @param targetDate  対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkSubHolidayRequest(RequestUtilBeanInterface requestUtil, Date targetDate) throws MospException {
		// 代休申請リストを取得
		List<SubHolidayRequestDtoInterface> subHolidayList = requestUtil.getSubHolidayList(false);
		// 代休申請が存在する場合
		if (MospUtility.isEmpty(subHolidayList) == false) {
			// エラーメッセージを設定
			addOthersRequestErrorMessage(targetDate, TimeNamingUtility.getSubHoliday(mospParams));
		}
	}
	
	/**
	 * 振替休日に勤怠申請が存在するか確認する。<br>
	 * 振替休日に勤怠申請が存在する場合エラーとする。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * @param personalId 個人ID
	 * @param substituteDate 振替休日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 * 
	 */
	protected void checkSubstituteAttendance(String personalId, Date substituteDate) throws MospException {
		// 承認解除対象の振替日の勤怠を取得
		AttendanceDtoInterface attendanceDto = attendanceReference.findForKey(personalId, substituteDate);
		if (attendanceDto != null) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
			// 申請済である場合
			if (WorkflowUtility.isApplied(workflowDto)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorCancelAfterWithdrawForSubstituteDay(mospParams,
						TimeNamingUtility.attendanceRequest(mospParams));
			}
		}
	}
	
	/**
	 * 申請時の入力チェック。休日出勤申請の重複チェック。<br>
	 * 同日に休日出勤申請がされている場合、エラーメッセージを設定する。<br>
	 * @param dto     振出・休出申請情報
	 * @param request 申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkWorkOnHolidayOverlap(WorkOnHolidayRequestDtoInterface dto, RequestEntityInterface request)
			throws MospException {
		// 登録済の振出・休出申請情報(取下以外)を取得
		WorkOnHolidayRequestDtoInterface registered = request
			.getWorkOnHolidayRequestDto(WorkflowUtility.getStatusesExceptWithDrawn());
		// 登録済の振出・休出申請情報(取下以外)が存在しない場合
		if (MospUtility.isEmpty(registered)) {
			// 処理終了
			return;
		}
		// ワークフロー番号が同じ場合
		if (dto.getWorkflow() == registered.getWorkflow()) {
			// 処理終了(同じ申請と判断)
			return;
		}
		// エラーメッセージを設定
		TimeMessageUtility.addErrorAlreadyApplyWorkOnHolidayForWorkOnHoliday(mospParams, dto.getRequestDate());
	}
	
	@Override
	public void checkHolidayDate(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		if (dto.getWorkOnHolidayType() == null || dto.getWorkOnHolidayType().isEmpty()) {
			// 勤務形態がない場合
			addWorkTypeNotExistErrorMessage(dto.getRequestDate());
			return;
		} else if (!TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(dto.getWorkOnHolidayType())
				&& !TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(dto.getWorkOnHolidayType())) {
			// 法定休日でなく且つ所定休日でない場合
			addWorkOnHolidayTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
		}
	}
	
	/**
	 * 勤怠申請チェック。対象日に勤怠申請が行われているか確認する。<br>
	 * @param dto     振出・休出申請情報
	 * @param request 申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkAttendance(WorkOnHolidayRequestDtoInterface dto, RequestEntityInterface request)
			throws MospException {
		// 勤怠申請がされている場合
		if (request.isAttendanceApplied()) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorAlreadyApplyWorkForWorkOnHoliday(mospParams, dto.getRequestDate());
		}
	}
	
	/**
	 * 振替出勤チェック。
	 * @param dto 振出・休出申請情報
	 */
	protected void checkLegalHolidayDate(WorkOnHolidayRequestDtoInterface dto) {
		// 半日法定振替出勤が可能である場合
		if (mospParams.getApplicationPropertyBool(TimeConst.APP_HALF_LEGAL_SUBSTITUTE)) {
			// 確認不要
			return;
		}
		// 振替出勤(午前)でなく且つ振替出勤(午後)でない場合
		if (TimeUtility.isTheSubstitute(dto, TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM,
				TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) == false) {
			// 確認不要
			return;
		}
		// 振替出勤(午前)又は振替出勤(午後)であり法定休日(半日法定振替出勤)である場合
		if (TimeUtility.isLegalHoliday(dto.getWorkOnHolidayType())) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorWorkOnHalfLegalHoliday(mospParams);
		}
	}
	
	/**
	 * 申請時(及び下書及び出勤日決定時)の入力チェック。休暇申請チェック。<br>
	 * @param dto     振出・休出申請情報
	 * @param request 申請エンティティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkHolidayRequest(WorkOnHolidayRequestDtoInterface dto, RequestEntityInterface request)
			throws MospException {
		// 休暇申請リスト(有効な承認状況(下書と取下以外))を取得
		List<HolidayRequestDtoInterface> list = request.getHolidayRequestList(WorkflowUtility.getEffectiveStatuses());
		// 振出・休出申請が振替出勤である場合
		if (TimeRequestUtility.isWorkOnHolidaySubstitute(dto)) {
			// 休暇申請が無い場合
			if (MospUtility.isEmpty(list)) {
				// 処理終了
				return;
			}
			// エラーメッセージを設定(休暇申請が有る場合)
			TimeMessageUtility.addErrorSubstituteDuplicateHolidays(mospParams);
			// 処理終了
			return;
		}
		// 出勤日を準備
		Date requestDate = dto.getRequestDate();
		// 休暇申請毎に処理(振替出勤でない場合(及び出勤日決定時))
		for (HolidayRequestDtoInterface holiday : list) {
			// 休暇申請の初日か末日が出勤日と同じ場合(休暇申請後にカレンダを編集したりした場合等に起こり得る)
			if (DateUtility.isSame(holiday.getRequestStartDate(), requestDate)
					|| DateUtility.isSame(holiday.getRequestEndDate(), requestDate)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorWorkOnHolidayOtherRequest(mospParams, requestDate);
				// 処理終了
				return;
			}
		}
	}
	
	/**
	 * 申請時の入力チェック。勤務形態チェック。
	 * @param dto 振出・休出申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkWorkTypeCode(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		if (dto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
			// 振替出勤(勤務形態変更あり)でない場合
			return;
		}
		// 振替出勤(勤務形態変更あり)である場合
		WorkTypeDtoInterface workTypeDto = workTypeReference.findForInfo(dto.getWorkTypeCode(), dto.getRequestDate());
		if (workTypeDto == null || workTypeDto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			addWorkTypeNotExistErrorMessage(dto.getRequestDate());
		}
	}
	
	/**
	 * 申請時の入力チェック。休日出勤申請の項目の必須チェック。<br>
	 * 必須の項目が入力されていない場合、エラーメッセージを設定する。<br>
	 * @param dto 振出・休出申請情報
	 */
	protected void checkRequired(WorkOnHolidayRequestDtoInterface dto) {
		// 申請理由が空の場合
		if (dto.getRequestReason().isEmpty()) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, TimeNamingUtility.applicationReason(mospParams));
		}
	}
	
	/**
	 * 入社しているか確認する。<br>
	 * @param dto 振出・休出申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkEntered(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		if (!isEntered(dto.getPersonalId(), dto.getRequestDate())) {
			// 出勤日時点で入社していない場合
			PfMessageUtility.addErrorEmployeeNotJoin(mospParams);
		}
	}
	
	/**
	 * 退職しているか確認する。<br>
	 * @param dto 振出・休出申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkRetired(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 出勤日時点で退職している場合
		if (retirementReference.isRetired(dto.getPersonalId(), dto.getRequestDate())) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeRetired(mospParams);
		}
	}
	
	/**
	 * 休職しているか確認する。<br>
	 * @param dto 振出・休出申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkSuspended(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 出勤日時点で休職している場合
		if (suspensionReference.isSuspended(dto.getPersonalId(), dto.getRequestDate())) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeSuspended(mospParams);
		}
	}
	
	/**
	 * 申請時の入力チェック。仮締チェック。
	 * @param dto 振出・休出申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkTightenForSetRequestDate(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestDate(), getNameGoingWorkDay());
	}
	
	/**
	 * 申請、下書き時の入力チェック。仮締チェック。<br>
	 * 仮締されている場合、エラーメッセージを設定する。<br>
	 * @param dto 振出・休出申請情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkTemporaryClosingFinal(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 対象個人IDにつき対象日付が未締であるかの確認
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestDate(), getNameGoingWorkDay());
		// 振替申請区分確認
		if (dto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 休日出勤(振替申請しない)の場合
			return;
		}
		// 振替休日情報リスト取得
		List<SubstituteDtoInterface> substituteList = substituteReference.getSubstituteList(dto.getWorkflow());
		// 振替情報毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			// 振替日が未締であるかの確認
			cutoffUtil.checkTighten(substituteDto.getPersonalId(), substituteDto.getSubstituteDate(),
					TimeNamingUtility.substituteDay(mospParams));
		}
	}
	
	@Override
	public String getScheduledWorkTypeCode(String personalId, Date targetDate) throws MospException {
		// 申請ユーティリティ
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, targetDate);
		return getScheduledWorkTypeCode(personalId, targetDate, requestUtil);
	}
	
	/**
	 * カレンダ勤務形態コードを取得する。
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @param requestUtil 申請ユーティリティ
	 * @return 勤務形態コード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getScheduledWorkTypeCode(String personalId, Date targetDate, RequestUtilBeanInterface requestUtil)
			throws MospException {
		// 振替休日データリストを取得
		List<SubstituteDtoInterface> list = requestUtil.getSubstituteList(true);
		// 振替休日データリスト毎に処理
		for (SubstituteDtoInterface substituteDto : list) {
			if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				return substituteDto.getSubstituteType();
			}
		}
		// 勤務形態コードを取得
		return scheduleUtil.getScheduledWorkTypeCode(personalId, targetDate);
	}
	
	/**
	 * 代休申請が行われているか確認する。<br>
	 * 休日出勤によって発生した代休が使われているかを確認する。<br>
	 * また、休日出勤日の勤怠申請が存在するかを確認する。<br>
	 * @param personalId 個人ID
	 * @param workDate   出勤日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkSubHolidayRequest(String personalId, Date workDate) throws MospException {
		// 代休申請リストの取得
		List<SubHolidayRequestDtoInterface> subholidayRequestList = subholidayRequestReference
			.findForWorkDate(personalId, workDate);
		// 代休申請が存在するなら
		if (MospUtility.isEmpty(subholidayRequestList) == false) {
			for (SubHolidayRequestDtoInterface subholidayRequestDto : subholidayRequestList) {
				// 対象代休申請のワークフロー情報取得
				WorkflowDtoInterface workflowDto = workflowReference
					.getLatestWorkflowInfo(subholidayRequestDto.getWorkflow());
				// 申請済でない場合
				if (WorkflowUtility.isApplied(workflowDto) == false) {
					// 次の代休申請情報へ
					continue;
				}
				// エラーメッセージを設定(代休申請が申請済である場合)
				TimeMessageUtility.addErrorCancelAfterWithdraw(mospParams,
						DateUtility.getStringDate(subholidayRequestDto.getRequestDate()),
						TimeNamingUtility.subHolidayRequest(mospParams));
			}
		}
		// 休日出勤日に勤怠申請があるか確認
		if (approvalInfoReference.isExistAttendanceTargetDate(personalId, workDate)) {
			// エラーメッセージ
			mospParams.addErrorMessage(TimeMessageConst.MSG_ATTENDANCE_DELETE, DateUtility.getStringDate(workDate));
		}
	}
	
	/**
	 * 出勤日に勤務形態変更申請が存在するかを確認する。<br>
	 * 出勤日に勤務形態変更申請が存在する場合、MosP処理情報にエラーメッセージを設定する。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @param workDate    出勤日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkWorkTypeChangeRequest(RequestUtilBeanInterface requestUtil, Date workDate)
			throws MospException {
		// 勤務形態変更申請を取得
		WorkTypeChangeRequestDtoInterface workTypeChangeDto = requestUtil.getWorkTypeChangeDto(false);
		// 勤務形態変更申請が存在する場合
		if (MospUtility.isEmpty(workTypeChangeDto) == false) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorCancelAfterWithdrawForWorkDay(mospParams,
					TimeNamingUtility.workTypeChangeRequest(mospParams));
		}
	}
	
	/**
	 * 出勤日に残業申請が存在するかを確認する。<br>
	 * 出勤日に残業申請が存在する場合、MosP処理情報にエラーメッセージを設定する。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * @param requestUtil 申請ユーティリティ
	 * @param workDate    出勤日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkOverTimeRequest(RequestUtilBeanInterface requestUtil, Date workDate) throws MospException {
		// 勤務形態変更申請を取得
		List<OvertimeRequestDtoInterface> overtimeList = requestUtil.getOverTimeList(false);
		// 勤務形態変更申請が存在する場合
		if (MospUtility.isEmpty(overtimeList) == false) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorCancelAfterWithdrawForWorkDay(mospParams,
					TimeNamingUtility.overtimeRequest(mospParams));
		}
	}
	
	/**
	 * 出勤日に時差出勤申請が存在するかを確認する。<br>
	 * 出勤日に時差出勤申請が存在する場合、MosP処理情報にエラーメッセージを設定する。<br>
	 * 承認解除時や、承認解除申請時のチェックで利用する。<br>
	 * が行われているか確認する。
	 * @param requestUtil 申請ユーティリティ
	 * @param workDate    出勤日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDifferenceRequest(RequestUtilBeanInterface requestUtil, Date workDate) throws MospException {
		// 時差出勤申請を取得
		DifferenceRequestDtoInterface differenceDto = requestUtil.getDifferenceDto(false);
		// 時差出勤申請が存在する場合
		if (MospUtility.isEmpty(differenceDto) == false) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorCancelAfterWithdrawForWorkDay(mospParams,
					TimeNamingUtility.differenceRequest(mospParams));
		}
	}
	
	/**
	 * 出勤日名称を取得する。
	 * @return 出勤日名称
	 */
	protected String getNameGoingWorkDay() {
		return PfNameUtility.goingWorkDay(mospParams);
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		// 勤怠関連マスタ参照処理を設定
		this.timeMaster = timeMaster;
		// 勤怠関連マスタ参照処理をBeanに設定
		scheduleUtil.setTimeMaster(timeMaster);
		cutoffUtil.setTimeMaster(timeMaster);
		attendanceRegist.setTimeMaster(timeMaster);
		attendanceTransactionRegist.setTimeMaster(timeMaster);
	}
	
}
