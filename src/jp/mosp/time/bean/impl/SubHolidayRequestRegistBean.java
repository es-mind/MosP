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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdSubHolidayRequestDto;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;

/**
 * 代休申請登録クラス。
 */
public class SubHolidayRequestRegistBean extends TimeBean implements SubHolidayRequestRegistBeanInterface {
	
	/**
	 * 代休申請DAOクラス。<br>
	 */
	protected SubHolidayRequestDaoInterface					dao;
	
	/**
	 * 代休申請参照インターフェース。<br>
	 */
	protected SubHolidayRequestReferenceBeanInterface		subHolidayReference;
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	protected WorkflowDaoInterface							workflowDao;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface				workflowIntegrate;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	protected WorkflowRegistBeanInterface					workflowRegist;
	
	/**
	 * ワークフローコメント登録クラス。<br>
	 */
	protected WorkflowCommentRegistBeanInterface			workflowCommentRegist;
	
	/**
	 * 勤怠データ登録インターフェース。
	 */
	protected AttendanceRegistBeanInterface					attendanceRegist;
	
	/**
	 * 勤怠関連申請承認クラス。<br>
	 */
	protected TimeApprovalBeanInterface						timeApproval;
	
	/**
	 * 承認情報参照クラス。<br>
	 */
	protected ApprovalInfoReferenceBeanInterface			approvalInfoReference;
	
	/**
	 * 締日ユーティリティ。<br>
	 */
	protected CutoffUtilBeanInterface						cutoffUtil;
	
	/**
	 * 申請ユーティリティ。
	 */
	protected RequestUtilBeanInterface						requestUtil;
	
	/**
	 * カレンダユーティリティ。
	 */
	protected ScheduleUtilBeanInterface						scheduleUtil;
	
	/**
	 * 代休情報DAOクラス。<br>
	 */
	protected SubHolidayDaoInterface						subHolidayDao;
	
	/**
	 * 勤怠情報DAOクラス。<br>
	 */
	protected AttendanceDaoInterface						attendanceDao;
	
	/**
	 * 人事休職情報参照クラス。<br>
	 */
	protected SuspensionReferenceBeanInterface				suspensionReference;
	
	/**
	 * 人事退職情報参照クラス。<br>
	 */
	protected RetirementReferenceBeanInterface				retirementReference;
	
	/**
	 * 勤怠トランザクション登録クラス。<br>
	 */
	protected AttendanceTransactionRegistBeanInterface		attendanceTransactionRegist;
	
	/**
	 * 勤務形態変更申請情報参照インターフェース
	 */
	protected WorkTypeChangeRequestReferenceBeanInterface	workTypeChangeReference;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface						timeMaster;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public SubHolidayRequestRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(SubHolidayRequestDaoInterface.class);
		workflowDao = createDaoInstance(WorkflowDaoInterface.class);
		subHolidayDao = createDaoInstance(SubHolidayDaoInterface.class);
		attendanceDao = createDaoInstance(AttendanceDaoInterface.class);
		// Beanを準備
		subHolidayReference = createBeanInstance(SubHolidayRequestReferenceBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		workflowRegist = createBeanInstance(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = createBeanInstance(WorkflowCommentRegistBeanInterface.class);
		attendanceRegist = createBeanInstance(AttendanceRegistBeanInterface.class);
		approvalInfoReference = createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
		cutoffUtil = createBeanInstance(CutoffUtilBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		suspensionReference = createBeanInstance(SuspensionReferenceBeanInterface.class);
		retirementReference = createBeanInstance(RetirementReferenceBeanInterface.class);
		attendanceTransactionRegist = createBeanInstance(AttendanceTransactionRegistBeanInterface.class);
		workTypeChangeReference = createBeanInstance(WorkTypeChangeRequestReferenceBeanInterface.class);
		// 勤怠関連マスタ参照処理を設定
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		setTimeMaster(timeMaster);
	}
	
	@Override
	public SubHolidayRequestDtoInterface getInitDto() {
		return new TmdSubHolidayRequestDto();
	}
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert(SubHolidayRequestDtoInterface dto) throws MospException {
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
		dto.setTmdSubHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public boolean update(long[] idArray) throws MospException {
		// レコード識別ID配列の妥当性確認
		validateAryId(idArray);
		if (mospParams.hasErrorMessage()) {
			return false;
		}
		boolean containsHalfHoliday = false;
		// Bean初期化
		workflowRegist = (WorkflowRegistBeanInterface)createBean(WorkflowRegistBeanInterface.class);
		// 代休申請情報レコード識別ID毎に処理
		for (long id : idArray) {
			// DTOの準備
			BaseDtoInterface baseDto = findForKey(dao, id, true);
			checkExclusive(baseDto);
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			SubHolidayRequestDtoInterface dto = (SubHolidayRequestDtoInterface)baseDto;
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
			// ワークフロー情報が存在する場合
			if (workflowDto != null) {
				// 勤怠削除
				deleteAttendance(dto);
				// 勤怠下書
				draftAttendance(dto);
				// 勤怠トランザクション登録
				attendanceTransactionRegist.regist(dto);
				// 午前休又は午後休の場合
				containsHalfHoliday = true;
			}
		}
		return containsHalfHoliday;
	}
	
	@Override
	public void regist(SubHolidayRequestDtoInterface dto) throws MospException {
		if (dao.findForKey(dto.getTmdSubHolidayRequestId(), false) == null) {
			// 新規登録
			insert(dto);
		} else {
			// 履歴追加
			add(dto);
		}
	}
	
	/**
	 * 履歴追加を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void add(SubHolidayRequestDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getTmdSubHolidayRequestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdSubHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(SubHolidayRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdSubHolidayRequestId());
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
			SubHolidayRequestDtoInterface dto = (SubHolidayRequestDtoInterface)baseDto;
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
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkInsert(SubHolidayRequestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(
				dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestDate(), dto.getHolidayRange()));
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkAdd(SubHolidayRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdSubHolidayRequestId());
	}
	
	@Override
	public void validate(SubHolidayRequestDtoInterface dto) throws MospException {
		// 基本情報のチェック
		subHolidayReference.chkBasicInfo(dto.getPersonalId(), dto.getRequestDate());
	}
	
	@Override
	public void checkSetRequestDate(SubHolidayRequestDtoInterface dto) throws MospException {
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
		// 申請ユーティリティ
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
		localRequestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
		// 勤務形態チェック
		checkWorkType(dto, localRequestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 代休申請・休暇申請・振替休日の重複チェック
		checkDuplicate(dto, localRequestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 他の申請チェック
		checkAttendance(dto);
	}
	
	@Override
	public void checkDraft(SubHolidayRequestDtoInterface dto) throws MospException {
		// 申請日決定時と同様の処理を行う。
		checkSetRequestDate(dto);
	}
	
	@Override
	public void checkAppli(SubHolidayRequestDtoInterface dto) throws MospException {
		// 下書き同様の処理を行う。
		checkDraft(dto);
		// 代休日数チェック。
		checkDay(dto);
		// 勤務形態変更申請チェック
		checkWorkTypeChange(dto);
	}
	
	@Override
	public void checkCancelAppli(SubHolidayRequestDtoInterface dto) throws MospException {
		checkTemporaryClosingFinal(dto);
		if (approvalInfoReference.isExistAttendanceTargetDate(dto.getPersonalId(), dto.getRequestDate())) {
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkManage"));
		}
	}
	
	@Override
	public void checkWithdrawn(SubHolidayRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	@Override
	public void checkApproval(SubHolidayRequestDtoInterface dto) throws MospException {
		// 申請時と同様の処理を行う
		checkAppli(dto);
	}
	
	@Override
	public void checkCancelApproval(SubHolidayRequestDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を行う
		checkCancelAppli(dto);
	}
	
	@Override
	public void checkCancel(SubHolidayRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	/**
	 * 休暇申請・代休申請・振替休日の重複チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDuplicate(SubHolidayRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		boolean subHolidayRangeAm = false;
		boolean subHolidayRangePm = false;
		boolean holidayRangeAm = false;
		boolean holidayRangePm = false;
		boolean holidayRangeTime = false;
		boolean substituteRangeAm = false;
		boolean substituteRangePm = false;
		// 代休申請チェック
		List<SubHolidayRequestDtoInterface> list = dao.findForList(dto.getPersonalId(), dto.getRequestDate());
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : list) {
			long workflow = subHolidayRequestDto.getWorkflow();
			if (workflowIntegrate.isWithDrawn(workflow)) {
				// 取下の場合
				continue;
			}
			if (dto.getWorkflow() == workflow) {
				// ワークフロー番号が同じ場合は同じ申請
				continue;
			}
			int subHolidayRange = subHolidayRequestDto.getHolidayRange();
			if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				addSubHolidayTargetDateSubHolidayRequestErrorMessage();
				return;
			} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				subHolidayRangeAm = true;
			} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				subHolidayRangePm = true;
			}
		}
		if (subHolidayRangeAm && subHolidayRangePm) {
			// 前半休及び後半休の場合
			addSubHolidayTargetDateSubHolidayRequestErrorMessage();
			return;
		}
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			if (subHolidayRangeAm || subHolidayRangePm) {
				// 前半休・後半休と重複している場合
				addSubHolidayTargetDateSubHolidayRequestErrorMessage();
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (subHolidayRangeAm) {
				// 前半休と重複している場合
				addSubHolidayTargetDateSubHolidayRequestErrorMessage();
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (subHolidayRangePm) {
				// 後半休と重複している場合
				addSubHolidayTargetDateSubHolidayRequestErrorMessage();
				return;
			}
		}
		// 休暇申請情報群を取得
		List<HolidayRequestDtoInterface> holidays = localRequestUtil.getHolidayList(false);
		// 休暇申請情報群に全休(前半休+後半休も含む)がある場合
		if (TimeRequestUtility.isAllRangeHoliday(holidays)) {
			// エラーメッセージを設定
			addOthersRequestErrorMessage(dto.getRequestDate(), TimeNamingUtility.holiday(mospParams));
			return;
		}
		// 範囲毎の休暇申請有無を取得
		holidayRangeAm = TimeRequestUtility.hasHolidayRangeAm(holidays);
		holidayRangePm = TimeRequestUtility.hasHolidayRangePm(holidays);
		holidayRangeTime = TimeRequestUtility.hasHolidayRangeHour(holidays);
		// 振替休日チェック
		int substituteRange = localRequestUtil.checkHolidayRangeSubstitute(localRequestUtil.getSubstituteList(false));
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 全休・前半休及び後半休の場合
			addSubstituteErrorMessage(dto.getRequestDate());
			return;
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			substituteRangeAm = true;
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			substituteRangePm = true;
		}
		if ((subHolidayRangeAm || holidayRangeAm || substituteRangeAm)
				&& (subHolidayRangePm || holidayRangePm || substituteRangePm)) {
			// 前半休及び後半休を組み合わせて全休となる場合
			addSubHolidayTargetDateSubHolidayRequestErrorMessage();
			return;
		}
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			if (holidayRangeAm || holidayRangePm) {
				// 前半休・後半休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Holiday"));
				return;
			} else if (holidayRangeTime) {
				// 時間休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("HolidayTime"));
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (holidayRangeAm) {
				// 前半休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Holiday"));
				return;
			} else if (holidayRangeTime) {
				// 時間休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("HolidayTime"));
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (holidayRangePm) {
				// 後半休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("Holiday"));
				return;
			} else if (holidayRangeTime) {
				// 時間休と重複している場合
				addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("HolidayTime"));
				return;
			}
		}
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			if (substituteRangeAm || substituteRangePm) {
				// 前半休・後半休と重複している場合
				addSubstituteErrorMessage(dto.getRequestDate());
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (substituteRangeAm) {
				// 前半休と重複している場合
				addSubstituteErrorMessage(dto.getRequestDate());
				return;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (substituteRangePm) {
				// 後半休と重複している場合
				addSubstituteErrorMessage(dto.getRequestDate());
				return;
			}
		}
		int range = dto.getHolidayRange();
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (subHolidayRangePm || holidayRangePm || substituteRangePm) {
				// 後半休と重複している場合
				range = TimeConst.CODE_HOLIDAY_RANGE_ALL;
			}
		} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (subHolidayRangeAm || holidayRangeAm || substituteRangeAm) {
				// 前半休と重複している場合
				range = TimeConst.CODE_HOLIDAY_RANGE_ALL;
			}
		}
		// 残業申請チェック
		checkOvertimeWorkRequest(dto, localRequestUtil, range);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替出勤チェック
		checkSubstituteWorkRequest(localRequestUtil, range);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態変更申請チェック
		checkWorkTypeChangeRequest(localRequestUtil, range);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時差出勤申請チェック
		checkDifferenceRequest(localRequestUtil, range);
	}
	
	/**
	 * 残業申請チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkOvertimeWorkRequest(SubHolidayRequestDtoInterface dto,
			RequestUtilBeanInterface localRequestUtil, int holidayRange) throws MospException {
		if (holidayRange != TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休でない場合
			return;
		}
		// 全休である場合
		if (localRequestUtil.getOverTimeList(false).isEmpty()) {
			// 残業申請されていない場合
			return;
		}
		// 残業申請されている場合
		addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("OvertimeWork"));
	}
	
	/**
	 * 振替休日申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkSubstituteWorkRequest(RequestUtilBeanInterface localRequestUtil, int holidayRange)
			throws MospException {
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = localRequestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto == null) {
			return;
		}
		// 振出・休出申請が承認済である場合
		int substitute = workOnHolidayRequestDto.getSubstitute();
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
			// 休日出勤の場合
			return;
		} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON) {
			// [全日]振替出勤（勤務形態変更なし）の場合
			return;
		} else if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
			// [全日]振替出勤（勤務形態変更あり）の場合
			return;
		}
		// 半日振替出勤日は休暇申請を不可とする
		addOthersRequestErrorMessage(workOnHolidayRequestDto.getRequestDate(),
				mospParams.getName("HalfDay", "Transfer", "GoingWork"));
	}
	
	/**
	 * 勤務形態変更申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkWorkTypeChangeRequest(RequestUtilBeanInterface localRequestUtil, int holidayRange)
			throws MospException {
		if (holidayRange != TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休でない場合
			return;
		}
		// 全休である場合
		WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto = localRequestUtil.getWorkTypeChangeDto(false);
		if (workTypeChangeRequestDto == null) {
			// 勤務形態変更申請されていない場合
			return;
		}
		// 勤務形態変更申請されている場合
		addOthersRequestErrorMessage(workTypeChangeRequestDto.getRequestDate(),
				mospParams.getName("Work", "Form", "Change"));
	}
	
	/**
	 * 時差出勤申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDifferenceRequest(RequestUtilBeanInterface localRequestUtil, int holidayRange)
			throws MospException {
		if (holidayRange != TimeConst.CODE_HOLIDAY_RANGE_ALL && holidayRange != TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 全休でなく且つ前半休でない場合
			return;
		}
		// 全休又は前半休である場合
		DifferenceRequestDtoInterface differenceRequestDto = localRequestUtil.getDifferenceDto(false);
		if (differenceRequestDto == null) {
			// 時差出勤申請されていない場合
			return;
		}
		// 時差出勤申請されている場合
		addOthersRequestErrorMessage(differenceRequestDto.getRequestDate(),
				mospParams.getName("TimeDifference", "GoingWork"));
	}
	
	/**
	 * 申請時の入力チェック。勤怠の申請チェック。<br>
	 * 勤怠が申請されている場合、エラーメッセージを設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkAttendance(SubHolidayRequestDtoInterface dto) throws MospException {
		// 勤怠申請情報取得
		AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(dto.getPersonalId(), dto.getRequestDate(), 1);
		if (attendanceDto == null) {
			return;
		}
		// 勤怠申請のワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(attendanceDto.getWorkflow());
		if (workflowDto == null) {
			return;
		}
		if (WorkflowUtility.isWithDrawn(workflowDto)) {
			// 勤怠が取下の場合
			return;
		}
		if (WorkflowUtility.isDraft(workflowDto)) {
			// 勤怠が下書の場合
			return;
		}
		if (WorkflowUtility.isFirstReverted(workflowDto)) {
			// 勤怠が1次戻の場合
			return;
		}
		// （日付）は既に勤怠の申請が行われています。（申請区分毎の日付名称）を選択し直してください。
		addSubHolidayTargetWorkDateAttendanceRequestErrorMessage(dto.getRequestDate());
	}
	
	/**
	 * 申請時の入力チェック。代休残数チェック。<br>
	 * 代休残数が申請数より少ない場合、エラーメッセージを設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDay(SubHolidayRequestDtoInterface dto) throws MospException {
		SubHolidayDtoInterface subHolidayDto = subHolidayDao.findForKey(dto.getPersonalId(), dto.getWorkDate(),
				dto.getTimesWork(), dto.getWorkDateSubHolidayType());
		double subHolidayDays = subHolidayDto.getSubHolidayDays();
		List<SubHolidayRequestDtoInterface> list = dao.findForList(dto.getPersonalId(), dto.getWorkDate(),
				dto.getTimesWork(), dto.getWorkDateSubHolidayType());
		for (SubHolidayRequestDtoInterface requestDto : list) {
			WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(requestDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (PlatformConst.CODE_STATUS_DRAFT.equals(workflowDto.getWorkflowStatus())
					|| PlatformConst.CODE_STATUS_WITHDRAWN.equals(workflowDto.getWorkflowStatus())) {
				// 下書又は取下の場合
				continue;
			}
			if (dto.getWorkflow() == workflowDto.getWorkflow()) {
				// ワークフロー番号が同じ場合は同じ申請
				continue;
			}
			int holidayRange = requestDto.getHolidayRange();
			if (holidayRange == 1) {
				subHolidayDays--;
			} else if (holidayRange == 2 || holidayRange == 3) {
				subHolidayDays -= TimeConst.HOLIDAY_TIMES_HALF;
			}
		}
		int holidayRange = dto.getHolidayRange();
		String errorMes1 = "";
		if (holidayRange == 1) {
			// 全休の場合
			if (subHolidayDays < 1) {
				// 代休日数が1未満の場合
				errorMes1 = "1";
				// 表示例 代休日数が1未満の場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_SUBHOLIDAY_DAY_CHECK, errorMes1);
			}
		} else if (holidayRange == 2 || holidayRange == 3) {
			// 午前休又は午後休の場合
			if (subHolidayDays < TimeConst.HOLIDAY_TIMES_HALF) {
				errorMes1 = "0.5";
				// 代休日数が0.5未満の場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_SUBHOLIDAY_DAY_CHECK, errorMes1);
			}
		}
	}
	
	/**
	 * 入社しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkEntered(SubHolidayRequestDtoInterface dto) throws MospException {
		if (!isEntered(dto.getPersonalId(), dto.getRequestDate())) {
			// 出勤日時点で入社していない場合
			PfMessageUtility.addErrorEmployeeNotJoin(mospParams);
		}
	}
	
	/**
	 * 退職しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkRetired(SubHolidayRequestDtoInterface dto) throws MospException {
		// 出勤日時点で退職している場合
		if (retirementReference.isRetired(dto.getPersonalId(), dto.getRequestDate())) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeRetired(mospParams);
		}
	}
	
	/**
	 * 休職しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkSuspended(SubHolidayRequestDtoInterface dto) throws MospException {
		// 出勤日時点で休職している場合
		if (suspensionReference.isSuspended(dto.getPersonalId(), dto.getRequestDate())) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeSuspended(mospParams);
		}
	}
	
	/**
	 * 申請、下書き時の入力チェック。仮締チェック。<br>
	 * 仮締されている場合、エラーメッセージを設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkTemporaryClosingFinal(SubHolidayRequestDtoInterface dto) throws MospException {
		// 対象個人IDにつき対象日付が未締であるかの確認
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestDate(), TimeNamingUtility.subHolidayDay(mospParams));
	}
	
	/**
	 * 勤務形態チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkWorkType(SubHolidayRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil)
			throws MospException {
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(dto.getPersonalId(), dto.getRequestDate(),
				localRequestUtil);
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			// 出勤日でない場合
			addSubHolidayTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
			return;
		} else if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 法定休日・所定休日の場合
			addSubHolidayTargetWorkDateHolidayErrorMessage(dto.getRequestDate());
			return;
		} else if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 法定休日労働・所定休日労働の場合
			addOthersRequestErrorMessage(dto.getRequestDate(), mospParams.getName("WorkingHoliday"));
		}
	}
	
	@Override
	public void deleteAttendance(SubHolidayRequestDtoInterface dto) throws MospException {
		int range = dto.getHolidayRange();
		boolean holidayAm = range == TimeConst.CODE_HOLIDAY_RANGE_AM;
		boolean holidayPm = range == TimeConst.CODE_HOLIDAY_RANGE_PM;
		if (range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			attendanceRegist.delete(dto.getPersonalId(), dto.getRequestDate());
			return;
		} else if (holidayAm || holidayPm) {
			// 半休の場合
			// 各種申請情報取得
			requestUtil.setRequests(dto.getPersonalId(), dto.getRequestDate());
			// 代休申請情報リスト取得
			List<SubHolidayRequestDtoInterface> list = requestUtil.getSubHolidayList(false);
			// 代休申請情報毎に処理
			for (SubHolidayRequestDtoInterface subHolidayRequestDto : list) {
				if ((holidayAm && subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
						|| (holidayPm && subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
					// 午前代休申請+午後代休申請となる場合勤怠を削除
					attendanceRegist.delete(dto.getPersonalId(), dto.getRequestDate());
					return;
				}
			}
			// 休暇申請情報リスト取得
			List<HolidayRequestDtoInterface> holidayRequestList = requestUtil.getHolidayList(false);
			// 休暇申請情報ごとに処理
			for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
				if ((holidayAm && holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
						|| (holidayPm && holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
					// 午前休暇申請+午後代休申請、または午前代休申請+午後休暇申請となる場合勤怠を削除
					attendanceRegist.delete(dto.getPersonalId(), dto.getRequestDate());
					break;
				}
			}
			// 振替休日申請情報リスト取得
			List<SubstituteDtoInterface> substituteList = requestUtil.getSubstituteList(false);
			// 振替休日申請情報毎に処理
			for (SubstituteDtoInterface substituteDto : substituteList) {
				if ((holidayAm && substituteDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
						|| (holidayPm && substituteDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
					// 午前代休申請+午後振替休日、または午前振替休日+午後代休申請となる場合勤怠を削除
					attendanceRegist.delete(dto.getPersonalId(), dto.getRequestDate());
					break;
				}
			}
		}
	}
	
	@Override
	public void draftAttendance(SubHolidayRequestDtoInterface dto) throws MospException {
		if (!workflowIntegrate.isCompleted(dto.getWorkflow())) {
			// 承認済でない場合
			return;
		}
		// Bean初期化
		timeApproval = (TimeApprovalBeanInterface)createBean(TimeApprovalBeanInterface.class);
		boolean deleteRest = false;
		int holidayRange = dto.getHolidayRange();
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 午前休又は午後休の場合
			deleteRest = true;
		}
		// 勤怠再下書
		timeApproval.reDraft(dto.getPersonalId(), dto.getRequestDate(), deleteRest, false, false);
	}
	
	/**
	 * 勤務形態変更申請の有無を確認する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkWorkTypeChange(SubHolidayRequestDtoInterface dto) throws MospException {
		// 休暇申請日の勤務形態変更申請情報を取得する。
		WorkTypeChangeRequestDtoInterface workTypeChangeDto = workTypeChangeReference
			.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestDate());
		if (workTypeChangeDto == null) {
			// 勤務形態変更申請がされていない場合
			return;
		}
		// 勤務形態変更申請のワークフロー情報取得
		WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(workTypeChangeDto.getWorkflow());
		// 承認状況確認
		if (workflowIntegrate.isCompleted(workflowDto.getWorkflow())
				|| workflowIntegrate.isWithDrawn(workflowDto.getWorkflow())
				|| workflowIntegrate.isDraft(workflowDto.getWorkflow())) {
			// 承認状況が承認済・取下・下書の場合
			return;
		}
		// 承認可能な勤務形態変更申請がある場合
		String requestName = mospParams.getName("Work", "Form", "Change");
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE, getStringDate(dto.getRequestDate()),
				requestName);
	}
	
	/**
	 * 振替休日である場合のエラーメッセージを設定する。<br>
	 * @param date 対象日
	 */
	protected void addSubstituteErrorMessage(Date date) {
		addOthersRequestErrorMessage(date, mospParams.getName("Transfer", "DayOff"));
	}
	
	@Override
	public void setTimeMaster(TimeMasterBeanInterface timeMaster) {
		// 勤怠関連マスタ参照処理を設定
		this.timeMaster = timeMaster;
		// 勤怠関連マスタ参照処理をBeanに設定
		scheduleUtil.setTimeMaster(timeMaster);
		requestUtil.setTimeMaster(timeMaster);
		cutoffUtil.setTimeMaster(timeMaster);
		attendanceRegist.setTimeMaster(timeMaster);
		attendanceTransactionRegist.setTimeMaster(timeMaster);
	}
	
}
