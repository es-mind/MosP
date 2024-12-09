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
import java.util.Map;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.ValidateUtility;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeApplicationBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceRegistBeanInterface;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayHourlyWorkTypeCheckBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestRegistBeanInterface;
import jp.mosp.time.bean.PaidHolidayInfoReferenceBeanInterface;
import jp.mosp.time.bean.PaidHolidayRemainBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.TimeApprovalBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdHolidayRequestDto;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 休暇申請登録処理。<br>
 */
public class HolidayRequestRegistBean extends TimeApplicationBean implements HolidayRequestRegistBeanInterface {
	
	/**
	 * 利用可能配列(休暇範囲)。<br>
	 */
	private static final int[]								AVAILABLE_HOLIDAY_RANGES	= {
		TimeConst.CODE_HOLIDAY_RANGE_ALL, TimeConst.CODE_HOLIDAY_RANGE_AM, TimeConst.CODE_HOLIDAY_RANGE_PM,
		TimeConst.CODE_HOLIDAY_RANGE_TIME };
	
	/**
	 * 休暇申請DAOクラス。<br>
	 */
	protected HolidayRequestDaoInterface					dao;
	
	/**
	 * 休暇申請参照処理。<br>
	 */
	protected HolidayRequestReferenceBeanInterface			holidayRequestRefer;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	protected WorkflowRegistBeanInterface					workflowRegist;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface				workflowIntegrate;
	
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
	private ApprovalInfoReferenceBeanInterface				approvalInfoReference;
	
	/**
	 * 勤務形態マスタ参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface				workTypeReference;
	
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
	 * 申請ユーティリティ。
	 */
	protected RequestUtilBeanInterface						requestUtil;
	
	/**
	 * 有給休暇情報参照クラス。
	 */
	protected PaidHolidayInfoReferenceBeanInterface			paidHolidayInfoReference;
	
	/**
	 * 有給休暇残数取得処理。<br>
	 */
	protected PaidHolidayRemainBeanInterface				paidHolidayRemain;
	
	/**
	 * 勤怠情報参照インターフェース。<br>
	 */
	protected AttendanceReferenceBeanInterface				attendanceReference;
	
	/**
	 * 勤務形態変更申請情報参照インターフェース
	 */
	protected WorkTypeChangeRequestReferenceBeanInterface	workTypeChangeReference;
	
	/**
	 * 時差出勤申請参照インターフェース
	 */
	protected DifferenceRequestReferenceBeanInterface		differenceRequestReference;
	
	/**
	 * 時間単位休暇と勤務形態の確認処理。<br>
	 */
	protected HolidayHourlyWorkTypeCheckBeanInterface		holidayHourlyWorkTypeCheck;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface						timeMaster;
	
	
	/**
	 * {@link TimeApplicationBean#TimeApplicationBean()}を実行する。<br>
	 */
	public HolidayRequestRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(HolidayRequestDaoInterface.class);
		// Beanを準備
		holidayRequestRefer = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		workflowRegist = createBeanInstance(WorkflowRegistBeanInterface.class);
		workflowCommentRegist = createBeanInstance(WorkflowCommentRegistBeanInterface.class);
		attendanceRegist = createBeanInstance(AttendanceRegistBeanInterface.class);
		approvalInfoReference = createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
		workTypeReference = createBeanInstance(WorkTypeReferenceBeanInterface.class);
		suspensionReference = createBeanInstance(SuspensionReferenceBeanInterface.class);
		retirementReference = createBeanInstance(RetirementReferenceBeanInterface.class);
		cutoffUtil = createBeanInstance(CutoffUtilBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		paidHolidayInfoReference = createBeanInstance(PaidHolidayInfoReferenceBeanInterface.class);
		paidHolidayRemain = createBeanInstance(PaidHolidayRemainBeanInterface.class);
		attendanceReference = createBeanInstance(AttendanceReferenceBeanInterface.class);
		workTypeChangeReference = createBeanInstance(WorkTypeChangeRequestReferenceBeanInterface.class);
		differenceRequestReference = createBeanInstance(DifferenceRequestReferenceBeanInterface.class);
		holidayHourlyWorkTypeCheck = createBeanInstance(HolidayHourlyWorkTypeCheckBeanInterface.class);
		// 勤怠関連マスタ参照処理を準備(準備したBeanにも設定するため最後に実施)
		setTimeMaster(createBeanInstance(TimeMasterBeanInterface.class));
	}
	
	@Override
	public HolidayRequestDtoInterface getInitDto() {
		return new TmdHolidayRequestDto();
	}
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void insert(HolidayRequestDtoInterface dto, Integer row) throws MospException {
		// DTOの妥当性確認
		validate(dto, row);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 更新を行う。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void update(HolidayRequestDtoInterface dto, Integer row) throws MospException {
		// DTOの妥当性確認
		validate(dto, row);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdHolidayRequestId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setTmdHolidayRequestId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void regist(HolidayRequestDtoInterface dto) throws MospException {
		// 登録
		regist(dto, null);
	}
	
	@Override
	public void regist(HolidayRequestDtoInterface dto, Integer row) throws MospException {
		if (dao.findForKey(dto.getTmdHolidayRequestId(), false) == null) {
			// 新規登録
			insert(dto, row);
		} else {
			// 更新
			update(dto, row);
		}
	}
	
	@Override
	public void delete(HolidayRequestDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getTmdHolidayRequestId());
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
			HolidayRequestDtoInterface dto = (HolidayRequestDtoInterface)baseDto;
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
	protected void checkInsert(HolidayRequestDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateInsert(dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestStartDate(),
				dto.getHolidayType1(), dto.getHolidayType2(), dto.getHolidayRange(), dto.getStartTime()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(HolidayRequestDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdHolidayRequestId());
	}
	
	/**
	 * 登録情報の妥当性を確認確認する。
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void validate(HolidayRequestDtoInterface dto, Integer row) throws MospException {
		// 基本情報のチェック
		holidayRequestRefer.chkBasicInfo(dto.getPersonalId(), dto.getRequestStartDate());
		// 値及び名称を準備
		int holidayRange = dto.getHolidayRange();
		String holidayRangeName = TimeNamingUtility.holidayRange(mospParams);
		// 休暇範囲のチェック
		checkAvailableInt(holidayRange, AVAILABLE_HOLIDAY_RANGES, holidayRangeName, row);
	}
	
	@Override
	public void checkSetRequestDate(HolidayRequestDtoInterface dto) throws MospException {
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
		// 日リスト取得
		List<Date> list = TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate());
		// 休職チェック
		checkSuspended(dto, list);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 仮締チェック
		checkTemporaryClosingFinal(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 日毎のチェック
		checkDailyForSetRequestDate(dto, list);
	}
	
	@Override
	public void checkDraft(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇申請が申請として妥当であるかを確認
		checkForRequest(dto, false, null);
	}
	
	@Override
	public void checkAppli(HolidayRequestDtoInterface dto, Integer row) throws MospException {
		// 休暇申請が申請として妥当であるかを確認
		checkForRequest(dto, false, row);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態変更申請チェック
		checkWorkTypeChange(dto);
		// 時差出勤申請チェック
		checkDifference(dto);
	}
	
	@Override
	public void checkAppli(HolidayRequestDtoInterface dto) throws MospException {
		// 申請時の確認
		checkAppli(dto, null);
	}
	
	@Override
	public void checkApproval(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇申請が申請として妥当であるかを確認
		checkForRequest(dto, true, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態変更申請チェック
		checkWorkTypeChange(dto);
		// 時差出勤申請チェック
		checkDifference(dto);
	}
	
	/**
	 * 休暇申請が申請として妥当であるかを確認する。<br>
	 * @param dto         休暇申請情報
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @param row         行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkForRequest(HolidayRequestDtoInterface dto, Boolean isCompleted, Integer row)
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
		// 日リスト取得
		List<Date> list = TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate());
		// 休職チェック
		checkSuspended(dto, list);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 仮締チェック
		checkTemporaryClosingFinal(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 有給休暇設定チェック
		checkPaidHolidayMaster(dto, row);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時間休の限度チェック
		checkTimeHolidayLimit(dto, isCompleted, row);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休暇種別チェック
		checkHolidayMaster(dto, row);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 日毎のチェック
		checkDailyForDraft(dto, list, row);
		// 時間休申請範囲チェック
		checkTimeHoliday(dto, row);
	}
	
	@Override
	public void checkCancelAppli(HolidayRequestDtoInterface dto) throws MospException {
		checkTemporaryClosingFinal(dto);
		if (dto.getRequestStartDate().equals(dto.getRequestEndDate())
				&& approvalInfoReference.isExistAttendanceTargetDate(dto.getPersonalId(), dto.getRequestStartDate())) {
			addOthersRequestErrorMessage(dto.getRequestStartDate(), mospParams.getName("WorkManage"));
		}
	}
	
	@Override
	public void checkWithdrawn(HolidayRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	@Override
	public void checkCancelApproval(HolidayRequestDtoInterface dto) throws MospException {
		// 解除申請時と同様の処理を行う
		checkCancelAppli(dto);
	}
	
	@Override
	public void checkCancel(HolidayRequestDtoInterface dto) {
		// 現在処理無し。処理が必要になった場合追加される予定。
	}
	
	/**
	 * 申請日設定時の入力チェック。日毎のチェック。
	 * @param dto 対象DTO
	 * @param list リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDailyForSetRequestDate(HolidayRequestDtoInterface dto, List<Date> list) throws MospException {
		for (Date date : list) {
			checkDailyForSetRequestDate(dto, date);
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 申請日設定時の入力チェック。日毎のチェック。
	 * @param dto 対象DTO
	 * @param date 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDailyForSetRequestDate(HolidayRequestDtoInterface dto, Date date) throws MospException {
		RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(
				RequestUtilBeanInterface.class);
		// 個人IDを取得
		String personalId = dto.getPersonalId();
		// 申請ユーティリティを取得
		localRequestUtil.setRequests(personalId, date);
		// 勤務形態コードを取得
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(personalId, date, localRequestUtil);
		// 勤務形態チェック(行インデックス指定無し)
		checkWorkType(dto, date, workTypeCode, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalHoliday(workTypeCode)
				|| TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
			// 法定休日・所定休日・法定休日労働・所定休日労働の場合
			return;
		}
		// 休暇申請・代休申請・振替休日の重複チェック
		checkDuplicate(dto, date, localRequestUtil, false);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠申請チェック
		checkAttendance(dto, date);
	}
	
	/**
	 * 下書時の入力チェック。日毎のチェック。
	 * @param dto  対象DTO
	 * @param list リスト
	 * @param row  行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDailyForDraft(HolidayRequestDtoInterface dto, List<Date> list, Integer row)
			throws MospException {
		for (Date date : list) {
			// 休暇申請(下書)時の確認
			checkDailyForDraft(dto, date, row);
			if (mospParams.hasErrorMessage()) {
				return;
			}
		}
	}
	
	/**
	 * 下書時の入力チェック。日毎のチェック。
	 * @param dto  対象DTO
	 * @param date 対象日
	 * @param row  行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDailyForDraft(HolidayRequestDtoInterface dto, Date date, Integer row) throws MospException {
		RequestUtilBeanInterface localRequestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		// 個人IDを取得
		String personalId = dto.getPersonalId();
		// 申請ユーティリティを取得
		localRequestUtil.setRequests(personalId, date);
		// 勤務形態コードを取得
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(personalId, date, localRequestUtil);
		// 勤務形態チェック
		checkWorkType(dto, date, workTypeCode, row);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalHoliday(workTypeCode)
				|| TimeUtility.isWorkOnPrescribedHoliday(workTypeCode)) {
			// 法定休日・所定休日・法定休日労働・所定休日労働の場合
			return;
		}
		// 休暇申請・代休申請の重複チェック
		checkDuplicate(dto, date, localRequestUtil, true);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時短時間との重複チェック
		checkShortTime(dto, localRequestUtil, workTypeCode, date);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠申請チェック
		checkAttendance(dto, date);
	}
	
	/**
	 * 休暇申請・代休申請・振替休日の重複チェック。
	 * @param dto 対象DTO
	 * @param targetDate 対象日
	 * @param localRequestUtil 申請ユーティリティ
	 * @param isDraft 下書・申請の場合true、そうでない場合false
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDuplicate(HolidayRequestDtoInterface dto, Date targetDate,
			RequestUtilBeanInterface localRequestUtil, boolean isDraft) throws MospException {
		boolean holidayRangeAm = false;
		boolean holidayRangePm = false;
		boolean holidayRangeTime = false;
		boolean subHolidayRangeAm = false;
		boolean subHolidayRangePm = false;
		boolean substituteRangeAm = false;
		boolean substituteRangePm = false;
		// 休暇申請チェック
		List<HolidayRequestDtoInterface> list = dao.findForTermOnWorkflow(dto.getPersonalId(), targetDate, targetDate);
		for (HolidayRequestDtoInterface holidayRequestDto : list) {
			long workflow = holidayRequestDto.getWorkflow();
			if (workflowIntegrate.isWithDrawn(workflow)) {
				// 取下の場合
				continue;
			}
			if (dto.getWorkflow() == workflow) {
				// ワークフロー番号が同じ場合は同じ申請
				continue;
			}
			int holidayRange = holidayRequestDto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				addHolidayOverlapRange1ErrorMessage();
				return;
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				holidayRangeAm = true;
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				holidayRangePm = true;
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休の場合
				holidayRangeTime = true;
			}
		}
		if (holidayRangeAm && holidayRangePm) {
			// 前半休及び後半休の場合
			addHolidayOverlapRange1ErrorMessage();
			return;
		}
		if (isDraft) {
			// 下書時・申請時の場合
			int holidayRange = dto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				if (holidayRangeAm || holidayRangePm || holidayRangeTime) {
					// 前半休・後半休・時間休と重複している場合
					addHolidayOverlapRange1ErrorMessage();
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				if (holidayRangeAm) {
					// 前半休と重複している場合
					addHolidayOverlapRange2ErrorMessage();
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				if (holidayRangePm) {
					// 後半休と重複している場合
					addHolidayOverlapRange2ErrorMessage();
					return;
				}
			}
		} else {
			// 申請日設定時の場合
			if (!dto.getRequestStartDate().equals(dto.getRequestEndDate())
					&& (holidayRangeAm || holidayRangePm || holidayRangeTime)) {
				// 休暇開始日が休暇終了日でなく
				// 且つ前半休・後半休・時間休と重複している場合
				addHolidayOverlapRange1ErrorMessage();
				return;
			}
		}
		// 代休申請チェック
		int subHolidayRange = localRequestUtil.checkHolidayRangeSubHoliday(localRequestUtil.getSubHolidayList(false));
		if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 全休・前半休及び後半休の場合
			addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
			return;
		} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			subHolidayRangeAm = true;
		} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			subHolidayRangePm = true;
		}
		// 振替休日チェック
		int substituteRange = localRequestUtil.checkHolidayRangeSubstitute(localRequestUtil.getSubstituteList(false));
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 全休・前半休及び後半休の場合
			addSubstituteErrorMessage(targetDate);
			return;
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			substituteRangeAm = true;
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			substituteRangePm = true;
		}
		if ((holidayRangeAm || subHolidayRangeAm || substituteRangeAm)
				&& (holidayRangePm || subHolidayRangePm || substituteRangePm)) {
			// 前半休及び後半休を組み合わせて全休となる場合
			addHolidayOverlapRange1ErrorMessage();
			return;
		}
		if (isDraft) {
			// 下書時・申請時の場合
			int holidayRange = dto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				if (subHolidayRangeAm || subHolidayRangePm) {
					// 前半休・後半休と重複している場合
					addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				if (subHolidayRangeAm) {
					// 前半休と重複している場合
					addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				if (subHolidayRangePm) {
					// 後半休と重複している場合
					addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休の場合
				if (subHolidayRangeAm || subHolidayRangePm) {
					// 前半休・後半休と重複している場合
					addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
					return;
				}
			}
		} else {
			// 申請日設定時の場合
			if (!dto.getRequestStartDate().equals(dto.getRequestEndDate())
					&& (subHolidayRangeAm || subHolidayRangePm)) {
				// 休暇開始日が休暇終了日でなく
				// 且つ前半休・後半休と重複している場合
				addOthersRequestErrorMessage(targetDate, mospParams.getName("CompensatoryHoliday"));
				return;
			}
		}
		if (isDraft) {
			// 下書時・申請時の場合
			int holidayRange = dto.getHolidayRange();
			if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				if (substituteRangeAm || substituteRangePm) {
					// 前半休・後半休と重複している場合
					addSubstituteErrorMessage(targetDate);
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				if (substituteRangeAm) {
					// 前半休と重複している場合
					addSubstituteErrorMessage(targetDate);
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				if (substituteRangePm) {
					// 後半休と重複している場合
					addSubstituteErrorMessage(targetDate);
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時間休の場合
				if (substituteRangeAm || substituteRangePm) {
					// 前半休・後半休と重複している場合
					addSubstituteErrorMessage(targetDate);
					return;
				}
			}
		} else {
			// 申請日設定時の場合
			if (!dto.getRequestStartDate().equals(dto.getRequestEndDate())
					&& (substituteRangeAm || substituteRangePm)) {
				// 休暇開始日が休暇終了日でなく
				// 且つ前半休・後半休と重複している場合
				addSubstituteErrorMessage(targetDate);
				return;
			}
		}
		int holidayRange = TimeConst.CODE_HOLIDAY_RANGE_ALL;
		if (isDraft) {
			// 下書時・申請時の場合
			holidayRange = dto.getHolidayRange();
			if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
				// 前半休の場合
				if (holidayRangePm || subHolidayRangePm || substituteRangePm) {
					// 後半休と重複している場合
					holidayRange = TimeConst.CODE_HOLIDAY_RANGE_ALL;
				}
			} else if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 後半休の場合
				if (holidayRangeAm || subHolidayRangeAm || substituteRangeAm) {
					// 前半休と重複している場合
					holidayRange = TimeConst.CODE_HOLIDAY_RANGE_ALL;
				}
			}
		} else {
			// 申請日設定時の場合
			if (dto.getRequestStartDate().equals(dto.getRequestEndDate())) {
				// 休暇開始日が休暇終了日の場合
				return;
			}
		}
		// 残業申請チェック
		checkOvertimeWorkRequest(localRequestUtil, targetDate, holidayRange);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替出勤チェック
		checkSubstituteWorkRequest(localRequestUtil, targetDate, holidayRange);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態変更申請チェック
		checkWorkTypeChangeRequest(localRequestUtil, holidayRange);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時差出勤申請チェック
		checkDifferenceRequest(localRequestUtil, holidayRange);
	}
	
	/**
	 * 時短時間との重複チェック。
	 * @param dto 対象DTO
	 * @param localRequestUtil 申請ユーティリティ
	 * @param workTypeCode 勤務形態コード
	 * @param date 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkShortTime(HolidayRequestDtoInterface dto, RequestUtilBeanInterface localRequestUtil,
			String workTypeCode, Date date) throws MospException {
		DifferenceRequestDtoInterface differenceRequestDto = localRequestUtil.getDifferenceDto(true);
		if (differenceRequestDto != null) {
			// 時差出勤申請が承認されている場合
			return;
		}
		// 時差出勤申請が承認されていない場合
		WorkTypeEntityInterface workTypeEntity = workTypeReference.getWorkTypeEntity(workTypeCode, date);
		if (workTypeEntity == null) {
			return;
		}
		boolean isShort1TimeSet = workTypeEntity.isShort1TimeSet();
		Date short1StartTime = null;
		Date short1EndTime = null;
		if (isShort1TimeSet) {
			// 時短時間1が設定されている場合
			short1StartTime = getTime(workTypeEntity.getShort1StartTime(), date);
			short1EndTime = getTime(workTypeEntity.getShort1EndTime(), date);
		}
		boolean isShort2TimeSet = workTypeEntity.isShort2TimeSet();
		Date short2StartTime = null;
		Date short2EndTime = null;
		if (isShort2TimeSet) {
			// 時短時間2が設定されている場合
			short2StartTime = getTime(workTypeEntity.getShort2StartTime(), date);
			short2EndTime = getTime(workTypeEntity.getShort2EndTime(), date);
		}
		if (!isShort1TimeSet && !isShort2TimeSet) {
			// 時短時間が設定されていない場合
			return;
		}
		// 時短時間が設定されている場合
		if (dto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			// 時間休でない場合
			return;
		}
		// 時間休である場合
		if (isShort1TimeSet
				&& checkDuplicationTimeZone(dto.getStartTime(), dto.getEndTime(), short1StartTime, short1EndTime)) {
			// 時短時間1と重複している場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_SHORT_TIME_DUPLICATION_CHECK,
					DateUtility.getStringTime(short1StartTime), DateUtility.getStringTime(short1EndTime));
			return;
		}
		if (isShort2TimeSet
				&& checkDuplicationTimeZone(dto.getStartTime(), dto.getEndTime(), short2StartTime, short2EndTime)) {
			// 時短時間2と重複している場合
			mospParams.addErrorMessage(TimeMessageConst.MSG_SHORT_TIME_DUPLICATION_CHECK,
					DateUtility.getStringTime(short2StartTime), DateUtility.getStringTime(short2EndTime));
		}
	}
	
	/**
	 * 残業申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @param date 対象日
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkOvertimeWorkRequest(RequestUtilBeanInterface localRequestUtil, Date date, int holidayRange)
			throws MospException {
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
		addOthersRequestErrorMessage(date, mospParams.getName("OvertimeWork"));
	}
	
	/**
	 * 振替休日申請チェック。
	 * @param localRequestUtil 申請ユーティリティ
	 * @param date 対象日
	 * @param holidayRange 休暇範囲
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkSubstituteWorkRequest(RequestUtilBeanInterface localRequestUtil, Date date, int holidayRange)
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
		addOthersRequestErrorMessage(date, mospParams.getName("HalfDay", "Transfer", "GoingWork"));
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
	 * 勤怠申請チェック。
	 * @param dto 休暇申請DTO
	 * @param date 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkAttendance(HolidayRequestDtoInterface dto, Date date) throws MospException {
		// 勤怠申請情報取得
		AttendanceDtoInterface attendanceDto = attendanceReference.findForKey(dto.getPersonalId(), date);
		if (attendanceDto == null) {
			// 勤怠申請がない場合
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
		addHolidayTargetWorkDateAttendanceRequestErrorMessage(date);
	}
	
	@Override
	public void deleteAttendance(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇申請の休暇範囲取得
		int range = dto.getHolidayRange();
		boolean holidayAm = range == TimeConst.CODE_HOLIDAY_RANGE_AM;
		boolean holidayPm = range == TimeConst.CODE_HOLIDAY_RANGE_PM;
		// 休暇申請の対象日付リストを取得
		List<Date> dateList = TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate());
		// 休暇申請対象日毎に処理
		for (Date date : dateList) {
			RequestUtilBeanInterface localRequestUtil = (RequestUtilBeanInterface)createBean(
					RequestUtilBeanInterface.class);
			// 休暇申請対象日の各種申請情報取得
			localRequestUtil.setRequests(dto.getPersonalId(), date);
			WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = localRequestUtil.getWorkOnHolidayDto(false);
			if (workOnHolidayRequestDto != null
					&& workOnHolidayRequestDto.getSubstitute() == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 休出である場合
				continue;
			}
			// 休出でない場合
			if (range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				attendanceRegist.delete(dto.getPersonalId(), date);
				continue;
			} else if (holidayAm || holidayPm) {
				// 半休申請の場合
				// 休暇申請情報リスト取得
				List<HolidayRequestDtoInterface> list = localRequestUtil.getHolidayList(false);
				// 休暇申請情報ごとに処理
				for (HolidayRequestDtoInterface holidayRequestDto : list) {
					if ((holidayAm && holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
							|| (holidayPm && holidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
						// 午前休暇申請+午後休暇申請となる場合勤怠を削除
						attendanceRegist.delete(dto.getPersonalId(), date);
						break;
					}
				}
				// 代休申請情報リスト取得
				List<SubHolidayRequestDtoInterface> subHolidayRequestList = localRequestUtil.getSubHolidayList(false);
				// 代休申請情報毎に処理
				for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
					if ((holidayAm && subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
							|| (holidayPm
									&& subHolidayRequestDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
						// 午前休暇申請+午後代休申請、または午前代休申請+午後休暇申請となる場合勤怠を削除
						attendanceRegist.delete(dto.getPersonalId(), date);
						break;
					}
				}
				// 振替休日申請情報リスト取得
				List<SubstituteDtoInterface> substituteList = localRequestUtil.getSubstituteList(false);
				// 振替休日申請情報毎に処理
				for (SubstituteDtoInterface substituteDto : substituteList) {
					if ((holidayAm && substituteDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_PM)
							|| (holidayPm && substituteDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_AM)) {
						// 午前休暇申請+午後振替休日、または午前振替休日+午後休暇申請となる場合勤怠を削除
						attendanceRegist.delete(dto.getPersonalId(), date);
						break;
					}
				}
			}
		}
	}
	
	@Override
	public void draftAttendance(HolidayRequestDtoInterface dto) throws MospException {
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
		timeApproval.reDraft(dto.getPersonalId(), dto.getRequestStartDate(), deleteRest, false, false);
	}
	
	/**
	 * 入社しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEntered(HolidayRequestDtoInterface dto) throws MospException {
		if (!isEntered(dto.getPersonalId(), dto.getRequestStartDate())) {
			// 休暇開始日時点で入社していない場合
			PfMessageUtility.addErrorEmployeeNotJoin(mospParams);
		}
	}
	
	/**
	 * 退職しているか確認する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRetired(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇終了日時点で退職している場合
		if (retirementReference.isRetired(dto.getPersonalId(), dto.getRequestEndDate())) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeRetired(mospParams);
		}
	}
	
	/**
	 * 休職しているか確認する。<br>
	 * @param dto 対象DTO
	 * @param list リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSuspended(HolidayRequestDtoInterface dto, List<Date> list) throws MospException {
		// 個人IDを取得
		String personalId = dto.getPersonalId();
		// 対象日毎に処理
		for (Date date : list) {
			// 休職している場合
			if (suspensionReference.isSuspended(personalId, date)) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorEmployeeSuspended(mospParams);
				// 処理終了
				return;
			}
		}
	}
	
	/**
	 * 申請、下書き時の入力チェック。仮締チェック。<br>
	 * 仮締されている場合、エラーメッセージを設定する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkTemporaryClosingFinal(HolidayRequestDtoInterface dto) throws MospException {
		// 対象個人IDにつき対象日付が未締であるかの確認
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getRequestStartDate(),
				TimeNamingUtility.holidayDate(mospParams));
	}
	
	/**
	 * 有給休暇及びストック休暇の設定を確認する。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkPaidHolidayMaster(HolidayRequestDtoInterface dto, Integer row) throws MospException {
		// 有給休暇でもストック休暇でもない場合
		if (TimeRequestUtility.isPaidHoliday(dto) == false && TimeRequestUtility.isStockHoliday(dto) == false) {
			// 処理終了(確認不要)
			return;
		}
		// 個人IDと申請開始日を取得
		String personalId = dto.getPersonalId();
		Date targetDate = dto.getRequestStartDate();
		// 有給休暇設定情報を取得
		PaidHolidayDtoInterface paidHolidayDto = timeMaster.getPaidHolidayForPersonalId(personalId, targetDate)
			.orElse(null);
		// 有給休暇設定情報を取得できなかった場合
		if (MospUtility.isEmpty(dto)) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorPaidHolidayDefect(mospParams, targetDate, row);
			// 処理終了
			return;
		}
		// 有給休暇申請理由が必須である場合
		if (holidayRequestRefer.isPaidHolidayReasonRequired()) {
			// 休暇理由が無い場合
			if (MospUtility.isEmpty(dto.getRequestReason())) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorRequired(mospParams, TimeNamingUtility.holidayReason(mospParams), row);
			}
		}
		// 時間休である場合
		if (TimeRequestUtility.isHolidayRangeHour(dto)) {
			// ストック休暇である場合
			if (TimeRequestUtility.isStockHoliday(dto)) {
				// エラーメッセージを設定(時間単位でストック休暇申請は不可)
				TimeMessageUtility.addErrorShortPaidHolidayRemainDays(mospParams, row);
				// 処理終了
				return;
			}
			// 有給休暇設定の時間単位取得が無効である場合(有給休暇である場合)
			if (PlatformUtility.isInactivate(paidHolidayDto.getTimelyPaidHolidayFlag())) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorUnsetHorlyPaidHoliday(mospParams, row);
				// 処理終了
				return;
			}
			// 時休開始時刻の分を取得
			int startMinute = DateUtility.getMinute(dto.getStartTime());
			// 休暇申請の時休開始時刻(分)が有給休暇設定の申請時間間隔で割り切れない場合
			if (ValidateUtility.chkIndivisible(startMinute, paidHolidayDto.getAppliTimeInterval()) == false) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorInputValueInvalid(mospParams,
						TimeNamingUtility.hourlyHolidayRequestTime(mospParams), row);
				// 処理終了
				return;
			}
		}
	}
	
	/**
	 * 特別休暇・その他休暇・欠勤の休暇種別チェックを行う。<br>
	 * 特別・その他・欠勤の確認を行う。<br>
	 * 時間単位取得の確認を行う。<br>
	 * 半休申請の確認を行う。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkHolidayMaster(HolidayRequestDtoInterface dto, Integer row) throws MospException {
		// 特別休暇・その他休暇・欠勤でない場合
		if (TimeRequestUtility.isSpecialHoliday(dto) == false && TimeRequestUtility.isOtherHoliday(dto) == false
				&& TimeRequestUtility.isAbsenece(dto) == false) {
			// 処理なし
			return;
		}
		// 休暇種別と申請開始日を取得
		int holidayType1 = dto.getHolidayType1();
		String holidayType2 = dto.getHolidayType2();
		Date requestStartDate = dto.getRequestStartDate();
		// 休暇種別情報を取得
		HolidayDtoInterface holidayDto = timeMaster.getHoliday(holidayType2, holidayType1, requestStartDate);
		// 有効な休暇種別情報を取得できなかった場合
		if (PlatformUtility.isDtoActivate(holidayDto) == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorNoItem(mospParams, TimeNamingUtility.holidayType(mospParams), row);
			// 処理終了
			return;
		}
		// 時間休である場合
		if (TimeRequestUtility.isHolidayRangeHour(dto)) {
			// 時間単位が無効の場合
			if (PlatformUtility.isInactivate(holidayDto.getTimelyHolidayFlag())) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorHourlyHolidayInvalid(mospParams, holidayDto.getHolidayName());
			}
		}
		// 半休である場合
		if (TimeRequestUtility.isHolidayRangeHalf(dto)) {
			// 半休申請が無効の場合
			if (PlatformUtility.isInactivate(holidayDto.getHalfHolidayRequest())) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorHalfHolidayInvalid(mospParams, holidayDto.getHolidayName());
			}
		}
		// 理由入力が必須である場合
		if (holidayDto.getReasonType() == TimeConst.CODE_REASON_TYPE_REQUIRED) {
			// 休暇理由が無い場合
			if (MospUtility.isEmpty(dto.getRequestReason())) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorRequired(mospParams, TimeNamingUtility.holidayReason(mospParams));
			}
		}
	}
	
	/**
	 * 時間休の限度チェックを行う。<br>
	 * 有給休暇の時間休の場合のみ、
	 * 限度時間の確認を行う。<br>
	 * @param dto         対象DTO
	 * @param isCompleted 承認済フラグ(true：承認済申請のみ、false：申請済申請含む)
	 * @param row         行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkTimeHolidayLimit(HolidayRequestDtoInterface dto, Boolean isCompleted, Integer row)
			throws MospException {
		// 追加業務ロジック処理を行った場合
		if (doAdditionalLogic(TimeConst.CODE_KEY_ADD_HOLIDAYREQUESTREGISTBEAN_CHECKTIMEHOLIDAYLIMIT, dto,
				isCompleted)) {
			// 通常の処理は行わない
			return;
		}
		// 有給休暇でない場合
		if (TimeRequestUtility.isPaidHoliday(dto) == false) {
			// 処理終了(時間休の限度を確認する必要がないため)
			return;
		}
		// 時間休でない場合
		if (TimeRequestUtility.isHolidayRangeHour(dto) == false) {
			// 処理終了(時間休の限度を確認する必要がないため)
			return;
		}
		// 個人ID・申請日取得
		String personalId = dto.getPersonalId();
		Date requestDate = dto.getRequestStartDate();
		// 限度時間取得
		int[] limit = paidHolidayInfoReference.getHolidayTimeUnitLimit(personalId, requestDate, isCompleted, dto);
		// 0日0時間の場合
		if (limit[0] <= 0 && limit[1] <= 0) {
			addHolidayNumDaysExcessErrorMessage(mospParams.getName("HolidayTime", "Of", "Years"),
					mospParams.getName("Time"));
			return;
		}
		// 有給休暇時間承認状態別休数回数マップ取得
		Map<String, Integer> timeHoliday = holidayRequestRefer.getTimeHolidayStatusTimesMap(personalId, requestDate,
				dto);
		// マップが空の場合
		if (timeHoliday.isEmpty()) {
			return;
		}
		// 取下・下書以外合算
		int holidayTimes = timeHoliday.get(mospParams.getName("Finish"))
				+ timeHoliday.get(mospParams.getName("Register")) + timeHoliday.get(mospParams.getName("Back"));
		// マップがあり、時休の1日の限度時間以上取得している場合
		if (timeHoliday.isEmpty() == false && holidayTimes >= getPrescribedWorkHour(dto)) {
			// 時間休の1日の申請可能な時間数を超過しています。休暇年月日または休暇種別を選択し直してください。(TMW0254)
			addHolidayNumDaysExcessErrorMessage(mospParams.getName("HolidayTime", "Of", "No1", "Day"),
					mospParams.getName("Time"));
		}
	}
	
	/**
	 * 勤務形態コードを確認する。<br>
	 * @param dto          休暇申請情報
	 * @param targetDate   対象日
	 * @param workTypeCode 勤務形態コード
	 * @param row          行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkWorkType(HolidayRequestDtoInterface dto, Date targetDate, String workTypeCode, Integer row)
			throws MospException {
		// 申請開始日と申請終了日を取得
		Date requestStartDate = dto.getRequestStartDate();
		Date requestEndDate = dto.getRequestEndDate();
		// 勤務形態コードが取得できなかった場合
		if (MospUtility.isEmpty(workTypeCode)) {
			// エラーメッセージを設定
			TimeMessageUtility.addErrorNotWorkDayForHolidayRequest(mospParams, targetDate, row);
			// 処理終了
			return;
		}
		// 法定休日・所定休日・法定休日出勤・所定休日出勤の場合
		if (TimeUtility.isHoliday(workTypeCode) || TimeUtility.isWorkOnLegalOrPrescribedHoliday(workTypeCode)) {
			// 申請開始日か申請終了日である場合
			if (DateUtility.isSame(targetDate, requestStartDate) || DateUtility.isSame(targetDate, requestEndDate)) {
				// エラーメッセージを設定
				TimeMessageUtility.addErrorNotWorkDayForHolidayRequest(mospParams, targetDate, row);
				// 処理終了
				return;
			}
		}
	}
	
	/**
	 * 所定労働時間を時間単位で取得する。<br>
	 * 時間未満は切り捨てる。<br>
	 * @param dto 対象DTO
	 * @return 所定労働時間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getPrescribedWorkHour(HolidayRequestDtoInterface dto) throws MospException {
		// 個人IDと対象日(申請開始日)を取得
		String personalId = dto.getPersonalId();
		Date targetDate = dto.getRequestStartDate();
		// 勤務形態エンティティを取得
		WorkTypeEntityInterface workType = requestUtil.getWorkTypeEntity(personalId, targetDate);
		// 勤務時間(分)を取得を取得
		int workTime = workType.getWorkTime();
		// 時間(分)から時(時間(分)/60の商)を取得(分は切捨)
		return TimeUtility.getHours(workTime);
	}
	
	/**
	 * 時間休を確認する。<br>
	 * 時間休の時間が勤務形態の始終業時刻内であるかを確認する。<br>
	 * 時間休が重複していないかを確認する。<br>
	 * エラーがある場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param dto 休暇申請情報
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkTimeHoliday(HolidayRequestDtoInterface dto, Integer row) throws MospException {
		// 業務ロジックを実行
		doStoredLogic(TimeConst.CODE_KEY_ADD_HOLIDAYREQUESTREGISTBEAN_CHECKTIMEHOLIDAY, dto, row);
	}
	
	/**
	 * 勤務形態変更申請の有無を確認する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkWorkTypeChange(HolidayRequestDtoInterface dto) throws MospException {
		// 休暇申請日の勤務形態変更申請情報を取得する。
		WorkTypeChangeRequestDtoInterface workTypeChangeDto = workTypeChangeReference
			.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestStartDate());
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
		mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE,
				getStringDate(dto.getRequestStartDate()), requestName);
	}
	
	/**
	 * 時差出勤申請の有無を確認する。
	 * 時間休を申請する場合、時差出勤申請は承認済のみ。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void checkDifference(HolidayRequestDtoInterface dto) throws MospException {
		// 時間休時のみチェック
		if (dto.getHolidayRange() != TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			return;
		}
		// 時差出勤申請取得（時間休は期間で申請できない）
		DifferenceRequestDtoInterface differenceDto = differenceRequestReference
			.findForKeyOnWorkflow(dto.getPersonalId(), dto.getRequestStartDate());
		if (differenceDto == null) {
			// チェックなし
			return;
		}
		// ワークフロー情報取得
		WorkflowDtoInterface differenceWorkflow = workflowIntegrate.getLatestWorkflowInfo(differenceDto.getWorkflow());
		// 承認状況が取下でなく、下書でなく、承認済でない場合
		if (!WorkflowUtility.isWithDrawn(differenceWorkflow) && !WorkflowUtility.isDraft(differenceWorkflow)
				&& !WorkflowUtility.isCompleted(differenceWorkflow)) {
			// 時差出勤申請のメッセージ追加
			String requestName = mospParams.getName("TimeDifference", "GoingWork", "Application");
			mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE,
					getStringDate(dto.getRequestStartDate()), requestName);
		}
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
		attendanceRegist.setTimeMaster(timeMaster);
		scheduleUtil.setTimeMaster(timeMaster);
		requestUtil.setTimeMaster(timeMaster);
		paidHolidayRemain.setTimeMaster(timeMaster);
		cutoffUtil.setTimeMaster(timeMaster);
	}
	
}
