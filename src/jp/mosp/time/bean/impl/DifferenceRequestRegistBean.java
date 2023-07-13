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
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestRegistAddonBeanInterface;
import jp.mosp.time.bean.DifferenceRequestRegistBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.DifferenceRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdDifferenceRequestDto;

/**
 * 時差出勤申請登録クラス。
 */
public class DifferenceRequestRegistBean extends TimeBean implements DifferenceRequestRegistBeanInterface {
	
	/**
	 * 時差出勤申請DAOクラス。<br>
	 */
	protected DifferenceRequestDaoInterface						dao;
	
	/**
	 * 時差出勤申請参照インターフェース。<br>
	 */
	protected DifferenceRequestReferenceBeanInterface			differenceReference;
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	protected WorkflowDaoInterface								workflowDao;
	
	/**
	 * ワークフロー参照クラス。<br>
	 */
	protected WorkflowReferenceBeanInterface					workflowReference;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface					workflowIntegrate;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	protected WorkflowRegistBeanInterface						workflowRegist;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	protected WorkflowCommentRegistBeanInterface				workflowCommentRegist;
	
	/**
	 * 勤怠情報DAO。<br>
	 */
	protected AttendanceDaoInterface							attendanceDao;
	
	/**
	 * 勤怠関連申請承認クラス。<br>
	 */
	protected TimeApprovalBeanInterface							timeApproval;
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	protected ApprovalInfoReferenceBeanInterface				approvalInfoReference;
	
	/**
	 * 締日ユーティリティ。<br>
	 */
	protected CutoffUtilBeanInterface							cutoffUtil;
	
	/**
	 * カレンダユーティリティ。
	 */
	protected ScheduleUtilBeanInterface							scheduleUtil;
	
	/**
	 * 申請ユーティリティ。
	 */
	protected RequestUtilBeanInterface							requestUtil;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface							timeMaster;
	
	/**
	 * 時差出勤申請登録追加処理リスト。<br>
	 */
	protected List<DifferenceRequestRegistAddonBeanInterface>	addonBeans;
	
	/**
	 * コードキー(時差出勤申請登録追加処理)。<br>
	 */
	protected static final String								CODE_KEY_ADDONS	= "DifferenceRequestRegistAddons";
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public DifferenceRequestRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO及びBeanを準備
		dao = createDaoInstance(DifferenceRequestDaoInterface.class);
		differenceReference = createBeanInstance(DifferenceRequestReferenceBeanInterface.class);
		workflowDao = createDaoInstance(WorkflowDaoInterface.class);
		workflowReference = createBeanInstance(WorkflowReferenceBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		workflowRegist = createBeanInstance(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = createBeanInstance(WorkflowCommentRegistBeanInterface.class);
		attendanceDao = createDaoInstance(AttendanceDaoInterface.class);
		approvalInfoReference = createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
		cutoffUtil = createBeanInstance(CutoffUtilBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		addonBeans = getAddonBeans();
		// 勤怠関連マスタ参照処理を設定
		scheduleUtil.setTimeMaster(timeMaster);
		requestUtil.setTimeMaster(timeMaster);
		cutoffUtil.setTimeMaster(timeMaster);
	}
	
	@Override
	public DifferenceRequestDtoInterface getInitDto() {
		DifferenceRequestDtoInterface dto = new TmdDifferenceRequestDto();
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による初期化DTO作成処理
			dto = addonBean.getInitDto(dto);
		}
		return dto;
	}
	
	@Override
	public void insert(DifferenceRequestDtoInterface dto) throws MospException {
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
		dto.setTmdDifferenceRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による新規登録処理
			addonBean.insert(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void update(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// Bean初期化
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		// 時差出勤申請情報レコード識別ID毎に処理
		for (long id : idArray) {
			// DTOの準備
			BaseDtoInterface baseDto = findForKey(dao, id, true);
			checkExclusive(baseDto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			DifferenceRequestDtoInterface dto = (DifferenceRequestDtoInterface)baseDto;
			// 申請時の確認
			checkAppli(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ワークフローDTOの準備
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(dto.getWorkflow());
			// 申請
			workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
					PlatformConst.WORKFLOW_TYPE_TIME, null);
			// 処理ワークフロー情報が存在する場合
			if (workflowDto != null) {
				// 勤怠下書
				draftAttendance(dto);
			}
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による一括更新処理
			addonBean.update(idArray);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void regist(DifferenceRequestDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getTmdDifferenceRequestId(), false) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 履歴追加
			add(dto);
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による登録処理
			addonBean.regist(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void add(DifferenceRequestDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdDifferenceRequestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdDifferenceRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による履歴追加処理
			addonBean.add(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void delete(DifferenceRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdDifferenceRequestId());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による削除処理
			addonBean.delete(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
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
			DifferenceRequestDtoInterface dto = (DifferenceRequestDtoInterface)baseDto;
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
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による一括取下処理
			addonBean.withdrawn(idArray);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(DifferenceRequestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestDate()));
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による新規登録時の確認処理
			addonBean.checkInsert(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(DifferenceRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdDifferenceRequestId());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による履歴追加時の確認処理
			addonBean.checkAdd(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void validate(DifferenceRequestDtoInterface dto) throws MospException {
		// 基本情報のチェック
		differenceReference.chkBasicInfo(dto.getPersonalId(), dto.getRequestDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による登録情報の妥当性確認処理
			addonBean.validate(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkDraft(DifferenceRequestDtoInterface dto) throws MospException {
		checkTemporaryClosingFinal(dto);
		// 他の申請チェック。
		checkRequest(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による下書時の確認処理
			addonBean.checkDraft(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkAppli(DifferenceRequestDtoInterface dto) throws MospException {
		// 各種申請情報取得
		requestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		// 下書き同様の処理を行う。
		checkDraft(dto);
		// 時差出勤申請の申請期間チェック。
		checkPeriod(dto);
		// 時差出勤申請の重複チェック。
		checkDifferenceOverlap(dto);
		// 勤怠の申請チェック。
		checkAttendance(dto);
		// 時差出勤申請の項目の必須チェック。
		checkRequired(dto);
		// 休暇申請チェック。
		for (HolidayRequestDtoInterface holidayRequestDto : requestUtil.getHolidayList(false)) {
			if (holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休の場合
				continue;
			}
			// 休暇申請がある場合
			addDifferenceTargetDateRequestErrorMessage(dto.getRequestDate());
			return;
		}
		// 代休申請チェック。
		if (requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(false)) != 0) {
			// 代休申請がある場合
			addDifferenceTargetDateRequestErrorMessage(dto.getRequestDate());
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による申請時の確認処理
			addonBean.checkAppli(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkCancelAppli(DifferenceRequestDtoInterface dto) throws MospException {
		// 承認解除時のチェック
		checkCancel(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による解除申請時の確認処理
			addonBean.checkCancelAppli(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkWithdrawn(DifferenceRequestDtoInterface dto) throws MospException {
		// 現在処理無し。処理が必要になった場合追加される予定。
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による取下時の確認処理
			addonBean.checkWithdrawn(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkApproval(DifferenceRequestDtoInterface dto) throws MospException {
		// 下書き同様の処理を行う。
		checkDraft(dto);
		// 時差出勤申請の申請期間チェック。
		checkPeriod(dto);
		// 時差出勤申請の重複チェック。
		checkDifferenceOverlap(dto);
		// 勤怠の申請チェック。
		checkAttendance(dto);
		// 時差出勤申請の項目の必須チェック。
		checkRequired(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による承認時の確認処理
			addonBean.checkApproval(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkCancelApproval(DifferenceRequestDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を行う
		checkCancelAppli(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による承認解除時の確認処理
			addonBean.checkCancelApproval(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkCancel(DifferenceRequestDtoInterface dto) throws MospException {
		// 締処理確認
		checkTemporaryClosingFinal(dto);
		// 勤怠申請確認
		if (approvalInfoReference.isExistAttendanceTargetDate(dto.getPersonalId(), dto.getRequestDate())) {
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkManage"));
		}
		// 各種申請情報取得
		requestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		// 時差出勤申請（取下、下書以外）取得
		List<HolidayRequestDtoInterface> holidayList = requestUtil.getHolidayList(false);
		// 休暇申請毎に処理
		for (HolidayRequestDtoInterface holidayDto : holidayList) {
			// 時間休でない場合
			if (holidayDto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 処理なし
				continue;
			}
			// エラーメッセージ追加
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Holiday"));
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による取消時の確認処理
			addonBean.checkCancel(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkRequest(DifferenceRequestDtoInterface dto) throws MospException {
		// 各種申請情報取得
		requestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		// 勤務形態コードを取得
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), dto.getRequestDate(),
				requestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態が法定休日又は所定休日の場合
		if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			addDifferenceTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
			return;
		}
		if (TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)) {
			// 所定休出、法定休出の場合
			addDifferenceTargetDateRequestErrorMessage(dto.getRequestDate());
			return;
		}
		if (requestUtil.isHolidayAllDay(false)) {
			// 全休の場合
			addDifferenceTargetDateRequestErrorMessage(dto.getRequestDate());
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による申請時の他の申請確認処理
			addonBean.checkRequest(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkDifferenceOverlap(DifferenceRequestDtoInterface dto) throws MospException {
		// 時差出勤申請取得
		DifferenceRequestDtoInterface requestDto = dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestDate());
		if (requestDto == null) {
			return;
		}
		// ワークフローの取得
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
		if (workflowDto == null) {
			return;
		}
		if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			// 取下の場合
			return;
		}
		if (dto.getWorkflow() == workflowDto.getWorkflow()) {
			// ワークフロー番号が同じ場合は同じ申請
			return;
		}
		// 表示例 yyyy/MM/ddは既に時差出勤が申請されています。時差出勤日を選択し直してください。
		addDifferenceTargetDateDifferenceRequestErrorMessage(dto.getRequestDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による申請時の時差出勤申請の重複確認処理
			addonBean.checkDifferenceOverlap(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkPeriod(DifferenceRequestDtoInterface dto) throws MospException {
		if (dto.getRequestDate().after(DateUtility.addMonth(getSystemDate(), 1))) {
			// 表示例 時差出勤申請は1ヶ月よりも先の申請を行うことはできません。時差出勤日を選択し直してください。
			addDifferencePeriodErrorMessage();
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による申請時の申請期間確認処理
			addonBean.checkPeriod(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkAttendance(DifferenceRequestDtoInterface dto) throws MospException {
		AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(dto.getPersonalId(), dto.getRequestDate(), 1);
		if (attendanceDto == null) {
			return;
		}
		WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(attendanceDto.getWorkflow());
		if (workflowDto == null) {
			return;
		}
		if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
			// 取下の場合
			return;
		}
		if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())) {
			// 下書の場合
			return;
		}
		if (workflowDto.getWorkflowStage() == 0
				&& PlatformConst.CODE_STATUS_REVERT.equals(workflowDto.getWorkflowStatus())) {
			// 1次戻の場合
			return;
		}
		// （日付）は既に勤怠の申請が行われています。（申請区分毎の日付名称）を選択し直してください。
		addDifferenceTargetWorkDateAttendanceRequestErrorMessage(dto.getRequestDate());
	}
	
	@Override
	public void checkRequired(DifferenceRequestDtoInterface dto) throws MospException {
		if (dto.getRequestReason().isEmpty()) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, PfNameUtility.reason(mospParams));
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による申請の残業申請の項目の必須確認処理
			addonBean.checkRequired(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkTemporaryClosingFinal(DifferenceRequestDtoInterface dto) throws MospException {
		// 対象個人IDにつき対象日付が未締であるかの確認
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestDate(), getTimeDifferenceDay());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による仮締の確認処理
			addonBean.checkTemporaryClosingFinal(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void draftAttendance(DifferenceRequestDtoInterface dto) throws MospException {
		if (!workflowIntegrate.isCompleted(dto.getWorkflow())) {
			// 承認済でない場合
			return;
		}
		// Bean初期化
		timeApproval = (TimeApprovalBeanInterface)createBean(TimeApprovalBeanInterface.class);
		// 勤怠再下書
		timeApproval.reDraft(dto.getPersonalId(), dto.getRequestDate(), false, false, false);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (DifferenceRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による勤怠下書処理
			addonBean.draftAttendance(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 時差出勤日名称を取得する。
	 * @return 時差出勤日名称
	 */
	protected String getTimeDifferenceDay() {
		return mospParams.getName("TimeDifference") + mospParams.getName("GoingWork") + mospParams.getName("Day");
	}
	
	/**
	 * 時差出勤申請登録追加処理リストを取得する。<br>
	 * @return 時差出勤申請登録追加処理リスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected List<DifferenceRequestRegistAddonBeanInterface> getAddonBeans() throws MospException {
		// 勤怠設定追加処理リストを準備
		List<DifferenceRequestRegistAddonBeanInterface> addonBeans = new ArrayList<DifferenceRequestRegistAddonBeanInterface>();
		// 勤怠設定追加処理配列毎に処理
		for (String[] addon : mospParams.getProperties().getCodeArray(CODE_KEY_ADDONS, false)) {
			// 勤怠設定追加処理を取得
			String addonBean = addon[0];
			// 勤怠設定追加処理が設定されていない場合
			if (MospUtility.isEmpty(addonBean)) {
				// 次の勤怠設定追加処理へ
				continue;
			}
			// 勤怠設定追加処理を取得
			DifferenceRequestRegistAddonBeanInterface bean = (DifferenceRequestRegistAddonBeanInterface)createBean(
					addonBean);
			// 勤怠設定追加処理リストに値を追加
			addonBeans.add(bean);
		}
		// 勤怠設定追加処理リストを取得
		return addonBeans;
	}
	
}
