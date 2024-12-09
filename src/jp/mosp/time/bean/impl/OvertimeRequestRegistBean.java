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
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
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
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestRegistAddonBeanInterface;
import jp.mosp.time.bean.OvertimeRequestRegistBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdOvertimeRequestDto;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 残業申請登録クラス。
 */
public class OvertimeRequestRegistBean extends TimeBean implements OvertimeRequestRegistBeanInterface {
	
	/**
	 * 残業申請マスタDAOクラス。<br>
	 */
	protected OvertimeRequestDaoInterface					dao;
	
	/**
	 * 残業申請マスタ参照クラス。<br>
	 */
	protected OvertimeRequestReferenceBeanInterface			overtimeReference;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	protected WorkflowRegistBeanInterface					workflowRegist;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface				workflowIntegrate;
	
	/**
	 * ワークフロー参照クラス。<br>
	 */
	protected WorkflowReferenceBeanInterface				workflowReference;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	protected WorkflowCommentRegistBeanInterface			workflowCommentRegist;
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	private ApprovalInfoReferenceBeanInterface				approvalInfoReference;
	
	/**
	 * 人事休職情報参照クラス。<br>
	 */
	protected SuspensionReferenceBeanInterface				suspensionReference;
	
	/**
	 * 人事退職情報参照クラス。<br>
	 */
	protected RetirementReferenceBeanInterface				retirementReference;
	
	/**
	 * 締日ユーティリティ。<br>
	 */
	protected CutoffUtilBeanInterface						cutoffUtil;
	
	/**
	 * カレンダユーティリティ。
	 */
	protected ScheduleUtilBeanInterface						scheduleUtil;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface						timeMaster;
	
	/**
	 * 残業申請登録追加処理リスト。<br>
	 */
	protected List<OvertimeRequestRegistAddonBeanInterface>	addonBeans;
	
	/**
	 * コードキー(残業申請登録追加処理)。<br>
	 */
	protected static final String							CODE_KEY_ADDONS	= "OvertimeRequestRegistAddons";
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public OvertimeRequestRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(OvertimeRequestDaoInterface.class);
		overtimeReference = createBeanInstance(OvertimeRequestReferenceBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		workflowReference = createBeanInstance(WorkflowReferenceBean.class);
		workflowRegist = createBeanInstance(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = createBeanInstance(WorkflowCommentRegistBeanInterface.class);
		approvalInfoReference = createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
		suspensionReference = createBeanInstance(SuspensionReferenceBeanInterface.class);
		retirementReference = createBeanInstance(RetirementReferenceBeanInterface.class);
		cutoffUtil = createBeanInstance(CutoffUtilBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		addonBeans = getAddonBeans();
		// 勤怠関連マスタ参照処理を設定
		setTimeMaster(createBeanInstance(TimeMasterBeanInterface.class));
	}
	
	@Override
	public OvertimeRequestDtoInterface getInitDto() {
		OvertimeRequestDtoInterface dto = new TmdOvertimeRequestDto();
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による初期化DTO作成処理
			dto = addonBean.getInitDto(dto);
		}
		return dto;
	}
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert(OvertimeRequestDtoInterface dto) throws MospException {
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
		dto.setTmdOvertimeRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
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
		// 残業申請情報レコード識別ID毎に処理
		for (long id : idArray) {
			// DTOの準備
			BaseDtoInterface baseDto = findForKey(dao, id, true);
			checkExclusive(baseDto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			OvertimeRequestDtoInterface dto = (OvertimeRequestDtoInterface)baseDto;
			// 申請時の確認
			checkAppli(dto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ワークフローDTOの準備
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(dto.getWorkflow());
			// 申請
			workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
					PlatformConst.WORKFLOW_TYPE_TIME, null);
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による一括更新処理
			addonBean.update(idArray);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void regist(OvertimeRequestDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getTmdOvertimeRequestId(), false) == null) {
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
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による登録処理
			addonBean.regist(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 履歴追加を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add(OvertimeRequestDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdOvertimeRequestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdOvertimeRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による履歴追加処理
			addonBean.add(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void delete(OvertimeRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdOvertimeRequestId());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
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
			OvertimeRequestDtoInterface dto = (OvertimeRequestDtoInterface)baseDto;
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
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
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
	protected void checkInsert(OvertimeRequestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(
				dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestDate(), dto.getOvertimeType()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(OvertimeRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdOvertimeRequestId());
	}
	
	@Override
	public void validate(OvertimeRequestDtoInterface dto) throws MospException {
		// 基本情報のチェック
		overtimeReference.chkBasicInfo(dto.getPersonalId(), dto.getRequestDate());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による登録情報の妥当性確認処理
			addonBean.validate(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkSetRequestDate(OvertimeRequestDtoInterface dto) throws MospException {
		// 申請ユーティリティ
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		checkSetRequestDate(dto, localRequestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による申請日決定時の確認処理
			addonBean.checkSetRequestDate(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 申請日決定時の確認処理を行う。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSetRequestDate(OvertimeRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
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
		checkTemporaryClosingFinal(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態チェック
		checkWorkType(dto, localRequestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 他の申請チェック
		checkRequest(dto, localRequestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		
		// 重複チェック
		checkOvertimeOverlap(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠申請チェック
		checkAttendance(dto, localRequestUtil);
	}
	
	@Override
	public void checkDraft(OvertimeRequestDtoInterface dto) throws MospException {
		// 申請ユーティリティ
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		// 申請日決定時と同様の処理を行う。
		checkSetRequestDate(dto, localRequestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による下書時の確認処理
			addonBean.checkDraft(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkAppli(OvertimeRequestDtoInterface dto) throws MospException {
		// 下書き同様の処理を行う。
		checkDraft(dto);
		// 残業申請の申請期間チェック。
		checkPeriod(dto);
		// 残業申請の項目の必須チェック。
		checkRequired(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による申請時の確認処理
			addonBean.checkAppli(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkCancelAppli(OvertimeRequestDtoInterface dto) throws MospException {
		checkTemporaryClosingFinal(dto);
		if (approvalInfoReference.isExistAttendanceTargetDate(dto.getPersonalId(), dto.getRequestDate())) {
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkManage"));
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による解除申請時の確認処理
			addonBean.checkCancelAppli(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkWithdrawn(OvertimeRequestDtoInterface dto) throws MospException {
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による取下時の確認処理
			addonBean.checkWithdrawn(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkApproval(OvertimeRequestDtoInterface dto) throws MospException {
		// 申請時と同様の処理を行う
		checkAppli(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による承認時の確認処理
			addonBean.checkApproval(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkCancelApproval(OvertimeRequestDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を行う
		checkCancelAppli(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による承認解除時の確認処理
			addonBean.checkCancelApproval(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkCancel(OvertimeRequestDtoInterface dto) throws MospException {
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による取消時の確認処理
			addonBean.checkCancel(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkRequired(OvertimeRequestDtoInterface dto) throws MospException {
		if (dto.getRequestTime() == 0) {
			// エラーメッセージを設定
			String fieldName = TimeNamingUtility.applicationTime(mospParams);
			String type = PfNameUtility.time(mospParams);
			PfMessageUtility.addErrorCheckGeneral(mospParams, fieldName, type);
		}
		if (dto.getRequestReason().isEmpty()) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, TimeNamingUtility.overtimeReason(mospParams));
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による残業申請項目の必須確認処理
			addonBean.checkRequired(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 他の申請チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRequest(OvertimeRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		if (localRequestUtil.isHolidayAllDay(false)) {
			// 全休の場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9,
					DateUtility.getStringDate(dto.getRequestDate()), getNameApplicationDay());
		}
	}
	
	@Override
	public void checkOvertimeOverlap(OvertimeRequestDtoInterface dto) throws MospException {
		// 残業申請リスト取得
		List<OvertimeRequestDtoInterface> list = dao.findForList(dto.getPersonalId(), dto.getRequestDate());
		for (OvertimeRequestDtoInterface requestDto : list) {
			// ワークフローの取得
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 取下の場合
				continue;
			}
			if (dto.getWorkflow() == workflowDto.getWorkflow()) {
				// ワークフロー番号が同じ場合は同じ申請
				continue;
			}
			if (dto.getOvertimeType() == requestDto.getOvertimeType()) {
				// 表示例 yyyy/MM/ddは既に残業が申請されています。残業年月日または残業区分を選択し直してください。"
				addOvertimeTargetDateOvertimeErrorMessage(dto.getRequestDate());
				break;
			}
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による申請時の残業申請の重複確認処理
			addonBean.checkOvertimeOverlap(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	@Override
	public void checkPeriod(OvertimeRequestDtoInterface dto) throws MospException {
		if (dto.getRequestDate().after(DateUtility.addMonth(getSystemDate(), 1))) {
			addOvertimePeriodErrorMessage();
		}
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による申請時の申請期間確認処理
			addonBean.checkPeriod(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 勤怠の申請チェック。<br>
	 * 未承認仮締：無効(残業事前申請のみ)で<br>
	 * 且つ勤怠申請(未承認以上)が存在した場合にエラー。<br>
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkAttendance(OvertimeRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		// 勤怠申請情報(取下、下書、1次戻以外)を取得
		AttendanceDtoInterface attendanceDto = localRequestUtil.getApplicatedAttendance();
		if (attendanceDto == null) {
			return;
		}
		// 締日情報を取得
		CutoffEntityInterface cutoff = timeMaster.getCutoffForPersonalId(dto.getPersonalId(), dto.getRequestDate());
		// 未承認仮締：無効(残業事前申請のみ)以外は、勤怠申請の事前申請を許可する為、処理中断
		if (cutoff.getNoApproval() != TimeConst.CODE_NO_APPROVAL_BEFORE_OVER_REQ) {
			return;
		}
		// （日付）は既に勤怠の申請が行われています。（申請区分毎の日付名称）を選択し直してください。
		addOvertimeTargetWorkDateAttendanceRequestErrorMessage(dto.getRequestDate());
	}
	
	/**
	 * 勤務形態チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkType(OvertimeRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), dto.getRequestDate(),
				localRequestUtil);
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			// 出勤日でない場合
			addOvertimeTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
			return;
		} else if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 法定休日・所定休日の場合
			addOvertimeTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
			return;
		} else if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 法定休日労働・所定休日労働の場合
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkingHoliday"));
		}
	}
	
	/**
	 * 入社しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEntered(OvertimeRequestDtoInterface dto) throws MospException {
		if (!isEntered(dto.getPersonalId(), dto.getRequestDate())) {
			// 出勤日時点で入社していない場合
			PfMessageUtility.addErrorEmployeeNotJoin(mospParams);
		}
	}
	
	/**
	 * 退職しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRetired(OvertimeRequestDtoInterface dto) throws MospException {
		// 出勤日時点で退職している場合
		if (retirementReference.isRetired(dto.getPersonalId(), dto.getRequestDate())) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeRetired(mospParams);
		}
	}
	
	/**
	 * 休職しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSuspended(OvertimeRequestDtoInterface dto) throws MospException {
		// 出勤日時点で休職している場合
		if (suspensionReference.isSuspended(dto.getPersonalId(), dto.getRequestDate())) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeSuspended(mospParams);
		}
	}
	
	@Override
	public void checkTemporaryClosingFinal(OvertimeRequestDtoInterface dto) throws MospException {
		// 対象個人IDにつき対象日付が未締であるかの確認
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestDate(), getNameApplicationDay());
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 追加処理群による処理
		for (OvertimeRequestRegistAddonBeanInterface addonBean : addonBeans) {
			// 追加処理による仮締の確認処理
			addonBean.checkTemporaryClosingFinal(dto);
			// エラーが発生したら、そこで処理は終了
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 残業年月日名称を取得する。
	 * @return 残業年月日名称
	 */
	protected String getNameApplicationDay() {
		return mospParams.getName("OvertimeWork") + mospParams.getName("Year") + mospParams.getName("Month")
				+ mospParams.getName("Day");
	}
	
	/**
	 * 残業申請登録追加処理リストを取得する。<br>
	 * @return 残業申請登録追加処理リスト
	 * @throws MospException Beanインスタンスの生成及び初期化に失敗した場合
	 */
	protected List<OvertimeRequestRegistAddonBeanInterface> getAddonBeans() throws MospException {
		// 勤怠設定追加処理リストを準備
		List<OvertimeRequestRegistAddonBeanInterface> addonBeans = new ArrayList<OvertimeRequestRegistAddonBeanInterface>();
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
			OvertimeRequestRegistAddonBeanInterface bean = (OvertimeRequestRegistAddonBeanInterface)createBean(
					addonBean);
			// 勤怠設定追加処理リストに値を追加
			addonBeans.add(bean);
		}
		// 勤怠設定追加処理リストを取得
		return addonBeans;
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		// 勤怠関連マスタ参照処理を設定
		this.timeMaster = timeMaster;
		// 勤怠関連マスタ参照処理をBeanに設定
		scheduleUtil.setTimeMaster(timeMaster);
		cutoffUtil.setTimeMaster(timeMaster);
	}
	
}
