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
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.CutoffUtilBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteRegistBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeItemReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeMessageConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.DifferenceRequestDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdSubstituteDto;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.entity.RequestEntityInterface;
import jp.mosp.time.utils.TimeMessageUtility;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 振替休日データ登録クラス。
 */
public class SubstituteRegistBean extends TimeBean implements SubstituteRegistBeanInterface {
	
	/**
	 * 振替休日データDAOクラス。<br>
	 */
	protected SubstituteDaoInterface						dao;
	
	/**
	 * 振替休日データ参照インターフェース。<br>
	 */
	protected SubstituteReferenceBeanInterface				substituteReference;
	
	/**
	 * 休出申請データDAOクラス。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface				workOnHolidayRequestDao;
	
	/**
	 * 休出申請参照クラス。<br>
	 */
	protected WorkOnHolidayRequestReferenceBeanInterface	workOnHolidayRefer;
	
	/**
	 * 休暇申請データDAOクラス。<br>
	 */
	protected HolidayRequestDaoInterface					holidayRequestDao;
	
	/**
	 * 代休申請データDAOクラス。<br>
	 */
	protected SubHolidayRequestDaoInterface					subHolidayRequestDao;
	
	/**
	 * 残業申請データDAOクラス。<br>
	 */
	protected OvertimeRequestDaoInterface					overtimeRequestDao;
	
	/**
	 * 時差出勤申請データDAOクラス。<br>
	 */
	protected DifferenceRequestDaoInterface					differenceRequestDao;
	
	/**
	 * 勤怠データDAOクラス。<br>
	 */
	protected AttendanceDaoInterface						attendanceDao;
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	protected WorkflowDaoInterface							workflowDao;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface				workflowIntegrate;
	
	/**
	 * 人事休職情報参照クラス。
	 */
	protected SuspensionReferenceBeanInterface				suspensionReference;
	
	/**
	 * 人事退職情報参照クラス。
	 */
	protected RetirementReferenceBeanInterface				retirementReference;
	
	/**
	 * 締日ユーティリティ。
	 */
	protected CutoffUtilBeanInterface						cutoffUtil;
	
	/**
	 * カレンダユーティリティ。
	 */
	protected ScheduleUtilBeanInterface						scheduleUtil;
	
	/**
	 * 勤務形態情報参照クラス。
	 */
	protected WorkTypeReferenceBeanInterface				workTypeRefer;
	
	/**
	 * 勤務形態項目情報参照クラス。
	 */
	protected WorkTypeItemReferenceBeanInterface			workTypeItemRefer;
	
	/**
	 * ワークフロー情報参照クラス。<br>
	 */
	protected WorkflowReferenceBeanInterface				workflowReference;
	
	/**
	 * ワークフロー情報参照クラス。<br>
	 */
	protected WorkTypeChangeRequestReferenceBeanInterface	workTypeChangeReference;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface						timeMaster;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public SubstituteRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(SubstituteDaoInterface.class);
		workOnHolidayRequestDao = createDaoInstance(WorkOnHolidayRequestDaoInterface.class);
		holidayRequestDao = createDaoInstance(HolidayRequestDaoInterface.class);
		subHolidayRequestDao = createDaoInstance(SubHolidayRequestDaoInterface.class);
		overtimeRequestDao = createDaoInstance(OvertimeRequestDaoInterface.class);
		differenceRequestDao = createDaoInstance(DifferenceRequestDaoInterface.class);
		attendanceDao = createDaoInstance(AttendanceDaoInterface.class);
		workflowDao = createDaoInstance(WorkflowDaoInterface.class);
		// Beanを準備
		substituteReference = createBeanInstance(SubstituteReferenceBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		suspensionReference = createBeanInstance(SuspensionReferenceBeanInterface.class);
		retirementReference = createBeanInstance(RetirementReferenceBeanInterface.class);
		cutoffUtil = createBeanInstance(CutoffUtilBeanInterface.class);
		workTypeRefer = createBeanInstance(WorkTypeReferenceBeanInterface.class);
		workTypeItemRefer = createBeanInstance(WorkTypeItemReferenceBeanInterface.class);
		workOnHolidayRefer = createBeanInstance(WorkOnHolidayRequestReferenceBeanInterface.class);
		workflowReference = createBeanInstance(WorkflowReferenceBeanInterface.class);
		workTypeChangeReference = createBeanInstance(WorkTypeChangeRequestReferenceBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		// 勤怠関連マスタ参照処理を設定
		scheduleUtil.setTimeMaster(timeMaster);
		cutoffUtil.setTimeMaster(timeMaster);
	}
	
	@Override
	public SubstituteDtoInterface getInitDto() {
		return new TmdSubstituteDto();
	}
	
	@Override
	public void insert(SubstituteDtoInterface dto) throws MospException {
		// DTO妥当性確認
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
		dto.setTmdSubstituteId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(long workflow) throws MospException {
		List<SubstituteDtoInterface> list = dao.findForWorkflow(workflow);
		for (SubstituteDtoInterface dto : list) {
			// DTO妥当性確認
			validate(dto);
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 確認
			checkDelete(dto);
			if (mospParams.hasErrorMessage()) {
				// エラーが存在したら履歴削除処理をしない
				continue;
			}
			// 論理削除
			logicalDelete(dao, dto.getTmdSubstituteId());
		}
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(SubstituteDtoInterface dto) throws MospException {
		// 対象レコードが重複していないかを確認
		checkDuplicateInsert(dao.findForKeyOnWorkflow(dto.getPersonalId(), dto.getSubstituteDate(),
				dto.getSubstituteRange(), dto.getWorkDate(), dto.getTimesWork()));
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(SubstituteDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getTmdSubstituteId());
	}
	
	@Override
	public void validate(SubstituteDtoInterface dto) throws MospException {
		// 基本情報のチェック
		substituteReference.chkBasicInfo(dto.getPersonalId(), dto.getSubstituteDate());
	}
	
	@Override
	public void checkValidate(SubstituteDtoInterface dto) {
		// 必須確認(振替日)
		checkRequired(dto.getSubstituteDate(), mospParams.getName("Transfer", "Day"), null);
		return;
	}
	
	@Override
	public void checkRegist(SubstituteDtoInterface dto) throws MospException {
		// 振替日の休職チェック
		checkSuspension(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		
		// 振替日の退職チェック
		checkRetirement(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替日の妥当性チェック
		checkTargetTransferDate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替日の休日チェック
		checkHolidayDate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替日の範囲チェック
		checkTransferDateRange(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替日の他の申請チェック
		checkRequest(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替日の勤怠の申請チェック
		checkAttendance(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替日の仮締チェック
		checkTighten(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替日の残業申請チェック
		checkOverTime(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替日の勤務形態変更申請チェック
		checkWorkTypeChange(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 半振替の申請チェック
		checkHalfRequest(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
	}
	
	@Override
	public void checkImport(SubstituteDtoInterface dto) throws MospException {
		// 入力チェック
		checkValidate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 入社チェック
		checkEntered(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 退職チェック
		checkRetirement(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休職チェック
		checkSuspension(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 仮締チェック
		checkTighten(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 休日労働チェック
		checkHolidayDate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 範囲チェック
		checkTransferDateRange(dto);
		// 申請ユーティリティ
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(dto.getPersonalId(), dto.getSubstituteDate());
		// 勤務形態チェック
		checkWorkType(dto, requestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替休日・休暇申請・代休申請等の重複チェック
		checkDuplicate(dto, requestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 他の申請チェック
		checkRequest(requestUtil);
	}
	
	/**
	 * 勤務形態チェック。
	 * 半振替の場合、勤務形態に午前休が設定されていないとエラーにする。<br>
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkType(SubstituteDtoInterface dto, RequestUtilBeanInterface requestUtil)
			throws MospException {
		// 個人ID・対象日取得
		String personalId = dto.getPersonalId();
		Date targetDate = dto.getSubstituteDate();
		// 勤務形態コード取得
		String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(personalId, targetDate, requestUtil);
		
		if (workTypeCode == null || workTypeCode.isEmpty()) {
			// カレンダが設定されていない場合
			addSubstituteDateErrorMessage(dto.getSubstituteDate());
			return;
		} else if (isLegalDayOff(workTypeCode) || isPrescribedDayOff(workTypeCode)) {
			// 法定休日・所定休日の場合
			addSubstituteDateErrorMessage(dto.getSubstituteDate());
			return;
		} else if (TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY.equals(workTypeCode)
				|| TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY.equals(workTypeCode)) {
			// 法定休日労働・所定休日労働の場合
			addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("WorkingHoliday"));
		}
	}
	
	/**
	 * 振替休日・休暇申請・代休申請等の重複チェック。
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDuplicate(SubstituteDtoInterface dto, RequestUtilBeanInterface requestUtil)
			throws MospException {
		boolean substituteRangeAm = false;
		boolean substituteRangePm = false;
		boolean holidayRangeAm = false;
		boolean holidayRangePm = false;
		boolean holidayRangeTime = false;
		boolean subHolidayRangeAm = false;
		boolean subHolidayRangePm = false;
		// 個人IDと振替日から振替休日データリストを取得
		List<SubstituteDtoInterface> substituteList = dao.findForList(dto.getPersonalId(), dto.getSubstituteDate());
		// 振替休日データリスト毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			long workflow = substituteDto.getWorkflow();
			if (workflowIntegrate.isWithDrawn(workflow)) {
				// 取下の場合
				continue;
			}
			if (dto.getWorkflow() == workflow) {
				// ワークフロー番号が同じ場合
				continue;
			}
			if (isLegalDayOff(substituteDto.getSubstituteType())
					|| isPrescribedDayOff(substituteDto.getSubstituteType())) {
				// 法定休日・所定休日の場合
				int range = substituteDto.getSubstituteRange();
				if (range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
					// 全休の場合
					addSubstituteDateErrorMessage(dto.getSubstituteDate());
					return;
				} else if (range == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 前半休の場合
					substituteRangeAm = true;
				} else if (range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 後半休の場合
					substituteRangePm = true;
				}
			}
		}
		if (substituteRangeAm && substituteRangePm) {
			// 前半休及び後半休の場合
			addSubstituteDateErrorMessage(dto.getSubstituteDate());
			return;
		}
		int substituteRange = dto.getSubstituteRange();
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			if (substituteRangeAm || substituteRangePm) {
				// 前半休及び後半休の場合
				addSubstituteDateErrorMessage(dto.getSubstituteDate());
				return;
			}
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (subHolidayRangeAm) {
				// 前半休と重複している場合
				addSubstituteDateErrorMessage(dto.getSubstituteDate());
				return;
			}
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (substituteRangePm) {
				// 後半休と重複している場合
				addSubstituteDateErrorMessage(dto.getSubstituteDate());
				return;
			}
		}
		// 休暇申請のチェック
		int holidayRange = requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(false));
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 全休・前半休及び後半休の場合
			addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("Holiday"));
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
		// 代休申請のチェック
		int subHolidayRange = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(false));
		if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
				|| subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM + TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 全休・前半休及び後半休の場合
			addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("CompensatoryHoliday"));
			return;
		} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			subHolidayRangeAm = true;
		} else if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			subHolidayRangePm = true;
		}
		if ((substituteRangeAm || holidayRangeAm || subHolidayRangeAm)
				&& (substituteRangePm || holidayRangePm || subHolidayRangePm)) {
			// 前半休及び後半休を組み合わせて全休となる場合
			addSubstituteDateErrorMessage(dto.getSubstituteDate());
			return;
		}
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			if (holidayRangeAm || holidayRangePm) {
				// 前半休・後半休と重複している場合
				addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("Holiday"));
				return;
			} else if (holidayRangeTime) {
				// 時間休と重複している場合
				addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("HolidayTime"));
				return;
			}
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (holidayRangeAm) {
				// 前半休と重複している場合
				addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("Holiday"));
				return;
			} else if (holidayRangeTime) {
				// 時間休と重複している場合
				addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("HolidayTime"));
				return;
			}
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (holidayRangePm) {
				// 後半休と重複している場合
				addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("Holiday"));
				return;
			} else if (holidayRangeTime) {
				// 時間休と重複している場合
				addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("HolidayTime"));
				return;
			}
		}
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休の場合
			if (subHolidayRangeAm || subHolidayRangePm) {
				// 前半休・後半休と重複している場合
				addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("CompensatoryHoliday"));
				return;
			}
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (subHolidayRangeAm) {
				// 前半休と重複している場合
				addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("CompensatoryHoliday"));
				return;
			}
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (subHolidayRangePm) {
				// 後半休と重複している場合
				addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("CompensatoryHoliday"));
				return;
			}
		}
		int range = dto.getSubstituteRange();
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 前半休の場合
			if (substituteRangePm || holidayRangePm || subHolidayRangePm) {
				// 後半休と重複している場合
				range = TimeConst.CODE_HOLIDAY_RANGE_ALL;
			}
		} else if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 後半休の場合
			if (substituteRangeAm || holidayRangeAm || subHolidayRangeAm) {
				// 前半休と重複している場合
				range = TimeConst.CODE_HOLIDAY_RANGE_ALL;
			}
		}
		// 残業申請チェック
		checkOvertimeWorkRequest(dto, requestUtil, range);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤務形態変更申請チェック
		checkWorkTypeChangeRequest(requestUtil, range);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 時差出勤申請チェック
		checkDifferenceRequest(requestUtil, range);
	}
	
	@Override
	public void checkTargetTransferDate(SubstituteDtoInterface dto) throws MospException {
		if (MospUtility.isEmpty(dto)) {
			return;
		}
		String personalId = dto.getPersonalId();
		Date substituteDate = dto.getSubstituteDate();
		int substituteRange = dto.getSubstituteRange();
		boolean substituteFlag = false;
		// 個人IDと振替日から振替休日データリストを取得
		List<SubstituteDtoInterface> substituteList = dao.findForList(personalId, substituteDate);
		// 振替休日データリスト毎に処理
		for (SubstituteDtoInterface substituteDto : substituteList) {
			// ワークフロー番号が同じ場合
			if (dto.getWorkflow() == substituteDto.getWorkflow()) {
				continue;
			}
			// ワークフローの取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(substituteDto.getWorkflow());
			if (MospUtility.isEmpty(workflowDto)) {
				continue;
			}
			// 下書でなく且つ取下でない場合
			if (!WorkflowUtility.isDraft(workflowDto) && !WorkflowUtility.isWithDrawn(workflowDto)) {
				// 振替フラグ準備
				substituteFlag = true;
				// 法定休日か所定休日の場合
				if (TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(substituteDto.getSubstituteType())
						|| TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(substituteDto.getSubstituteType())) {
					if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL
							|| substituteRange == substituteDto.getSubstituteRange()) {
						String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()), getNameGoingWorkDay(),
							getNameSubstituteDay() };
						mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
						return;
					}
				}
				break;
			}
		}
		if (!substituteFlag) {
			// カレンダコードと有効日と日からカレンダ日情報を取得
			ScheduleDateDtoInterface scheduleDateDto = scheduleUtil.getScheduleDate(personalId, substituteDate);
			if (MospUtility.isEmpty(scheduleDateDto)) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorNoItem(mospParams, TimeNamingUtility.calendarData(mospParams));
				return;
			}
			if (scheduleDateDto.getWorkTypeCode() == null || scheduleDateDto.getWorkTypeCode().isEmpty()
					|| isLegalDayOff(scheduleDateDto.getWorkTypeCode())
					|| isPrescribedDayOff(scheduleDateDto.getWorkTypeCode())) {
				// 法定休日、所定休日又は勤務形態が登録されていない場合
				String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()), getNameGoingWorkDay(),
					getNameSubstituteDay() };
				mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
				return;
			}
		}
		// 振替休日のチェック
		for (SubstituteDtoInterface substituteDto : substituteList) {
			if (dto.getWorkflow() == substituteDto.getWorkflow()) {
				continue;
			}
			// ワークフロー情報を取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(substituteDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// 下書又は取下の場合
			if (WorkflowUtility.isDraft(workflowDto) || WorkflowUtility.isWithDrawn(workflowDto)) {
				continue;
			}
			// 振替範囲取得
			int range = substituteDto.getSubstituteRange();
			if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL || range == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9,
						new String[]{ DateUtility.getStringDate(dto.getSubstituteDate()), getNameSubstituteDay() });
				return;
			} else if (range == TimeConst.CODE_HOLIDAY_RANGE_AM || range == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休又は午後休の場合
				if (range == substituteRange) {
					// 範囲が同じ場合
					String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()), getNameGoingWorkDay(),
						getNameSubstituteDay() };
					mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
					return;
				}
			}
		}
		// 休暇申請のチェック
		// 個人IDと申請日から休暇申請マスタリストを取得
		List<HolidayRequestDtoInterface> holidayRequestList = holidayRequestDao.findForList(personalId, substituteDate);
		// 休暇申請マスタリスト毎に処理
		for (HolidayRequestDtoInterface holidayRequestDto : holidayRequestList) {
			// ワークフロー番号からワークフロー情報を取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(holidayRequestDto.getWorkflow());
			// 下書又は取下の場合
			if (WorkflowUtility.isDraft(workflowDto) || WorkflowUtility.isWithDrawn(workflowDto)) {
				continue;
			}
			// 休暇範囲取得
			int holidayRange = holidayRequestDto.getHolidayRange();
			// 休暇範囲が全休の場合
			if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
					|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9,
						new String[]{ DateUtility.getStringDate(dto.getSubstituteDate()), getNameSubstituteDay() });
				return;
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
					|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休又は午後休の場合
				if (holidayRange == substituteRange) {
					// 範囲が同じ場合
					String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()), getNameGoingWorkDay(),
						getNameSubstituteDay() };
					mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
					return;
				}
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
				// 時休の場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9,
						new String[]{ DateUtility.getStringDate(dto.getSubstituteDate()), getNameSubstituteDay() });
				return;
			}
		}
		// 代休申請のチェック
		List<SubHolidayRequestDtoInterface> subHolidayRequestList = subHolidayRequestDao.findForList(personalId,
				substituteDate);
		for (SubHolidayRequestDtoInterface subHolidayRequestDto : subHolidayRequestList) {
			// ワークフロー情報取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(subHolidayRequestDto.getWorkflow());
			// 下書又は取下の場合
			if (WorkflowUtility.isDraft(workflowDto) || WorkflowUtility.isWithDrawn(workflowDto)) {
				continue;
			}
			// 休暇範囲取得
			int holidayRange = subHolidayRequestDto.getHolidayRange();
			if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_ALL
					|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
				// 全休の場合
				mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9,
						new String[]{ DateUtility.getStringDate(dto.getSubstituteDate()), getNameSubstituteDay() });
				return;
			} else if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM
					|| holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
				// 午前休又は午後休の場合
				if (holidayRange == substituteRange) {
					// 範囲が同じ場合
					String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()), getNameGoingWorkDay(),
						getNameSubstituteDay() };
					mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3, aryRep);
					return;
				}
			}
		}
	}
	
	/**
	 * 残業申請チェック。
	 * @param dto 対象DTO
	 * @param requestUtil 申請ユーティリティ
	 * @param range 範囲
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkOvertimeWorkRequest(SubstituteDtoInterface dto, RequestUtilBeanInterface requestUtil, int range)
			throws MospException {
		if (range != TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休でない場合
			return;
		}
		// 全休である場合
		if (requestUtil.getOverTimeList(false).isEmpty()) {
			// 残業申請されていない場合
			return;
		}
		// 残業申請されている場合
		addOthersRequestErrorMessage(dto.getSubstituteDate(), mospParams.getName("OvertimeWork"));
	}
	
	/**
	 * 勤務形態変更申請チェック。
	 * @param requestUtil 申請ユーティリティ
	 * @param range 範囲
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkTypeChangeRequest(RequestUtilBeanInterface requestUtil, int range) throws MospException {
		if (range != TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 全休でない場合
			return;
		}
		// 全休である場合
		WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto = requestUtil.getWorkTypeChangeDto(false);
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
	 * @param requestUtil 申請ユーティリティ
	 * @param range 範囲
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkDifferenceRequest(RequestUtilBeanInterface requestUtil, int range) throws MospException {
		if (range != TimeConst.CODE_HOLIDAY_RANGE_ALL && range != TimeConst.CODE_HOLIDAY_RANGE_AM) {
			// 全休でなく且つ前半休でない場合
			return;
		}
		// 全休又は前半休である場合
		DifferenceRequestDtoInterface differenceRequestDto = requestUtil.getDifferenceDto(false);
		if (differenceRequestDto == null) {
			// 時差出勤申請されていない場合
			return;
		}
		// 時差出勤申請されている場合
		addOthersRequestErrorMessage(differenceRequestDto.getRequestDate(),
				mospParams.getName("TimeDifference", "GoingWork"));
	}
	
	@Override
	public void checkTransferDateRange(SubstituteDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		Date workDate = dto.getWorkDate();
		// 設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(dto.getPersonalId(), workDate);
		// 設定適用エンティティが有効でない場合
		if (application.isValid(mospParams) == false) {
			// 処理終了
			return;
		}
		// 勤怠設定管理DTOの取得
		TimeSettingDtoInterface timeSettingDto = application.getTimeSettingDto();
		// 期間開始日および期間終了日取得
		Date beforeDate = DateUtility.addDay(
				DateUtility.addMonth(workDate, -timeSettingDto.getTransferAheadLimitMonth()),
				-timeSettingDto.getTransferAheadLimitDate());
		Date afterDate = DateUtility.addDay(DateUtility.addMonth(workDate, timeSettingDto.getTransferLaterLimitMonth()),
				timeSettingDto.getTransferLaterLimitDate());
		// 振替日取得
		Date substituteDate = dto.getSubstituteDate();
		if (!substituteDate.after(beforeDate) || !substituteDate.before(afterDate)) {
			mospParams.addErrorMessage(TimeMessageConst.MSG_TRANSFER_DAY_EXCESS, getStringDate(dto.getSubstituteDate()),
					null);
		}
	}
	
	/**
	 * 他の申請チェック。
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRequest(RequestUtilBeanInterface requestUtil) throws MospException {
		// 振出・休出申請チェック
		checkWorkOnHolidayRequest(requestUtil);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 勤怠申請チェック
		checkAttendance(requestUtil);
	}
	
	@Override
	public void checkRequest(SubstituteDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		// 個人ID・振替日取得
		String personalId = dto.getPersonalId();
		Date substituteDate = dto.getSubstituteDate();
		// 振替日の休出申請取得
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = workOnHolidayRequestDao
			.findForKeyOnWorkflow(personalId, substituteDate);
		if (workOnHolidayRequestDto != null) {
			// ワークフローの取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(workOnHolidayRequestDto.getWorkflow());
			if (workflowDto != null) {
				if (workflowDto.getWorkflowStage() != 0 && !WorkflowUtility.isDraft(workflowDto)
						&& !WorkflowUtility.isWithDrawn(workflowDto)) {
					String[] aryRep = { DateUtility.getStringDate(dto.getSubstituteDate()), getNameSubstituteDay() };
					mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9, aryRep);
				}
			}
		}
		// 全休の場合
		if (dto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// クラス準備
			RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
			requestUtil.setRequests(personalId, dto.getSubstituteDate());
			// 振替日の残業申請取得
			List<OvertimeRequestDtoInterface> list = overtimeRequestDao.findForList(personalId, substituteDate);
			// 残業申請リスト毎に処理
			for (OvertimeRequestDtoInterface requestDto : list) {
				// ワークフローの取得
				WorkflowDtoInterface workflowDto = workflowDao.findForKey(requestDto.getWorkflow());
				if (workflowDto == null) {
					continue;
				}
				// 下書でなく且つ取下でない場合
				if (!WorkflowUtility.isDraft(workflowDto) && !WorkflowUtility.isWithDrawn(workflowDto)) {
					// エラーメッセージ追加
					String[] aryRep = { getStringDate(dto.getSubstituteDate()), getNameSubstituteDay() };
					mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9, aryRep);
					return;
				}
			}
			// 振替日の勤務形態変更申請
			if (requestUtil.getWorkTypeChangeDto(false) != null) {
				// エラーメッセージ追加
				mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9,
						DateUtility.getStringDate(dto.getSubstituteDate()), getNameSubstituteDay());
			}
			
			// 振替日の時差出勤申請取得
			DifferenceRequestDtoInterface differenceRequestDto = differenceRequestDao.findForKeyOnWorkflow(personalId,
					substituteDate);
			if (differenceRequestDto == null) {
				return;
			}
			// ワークフローの取得
			WorkflowDtoInterface workflowDto = workflowDao.findForKey(differenceRequestDto.getWorkflow());
			// 下書でなく且つ取下でない場合
			if (!WorkflowUtility.isDraft(workflowDto) && !WorkflowUtility.isWithDrawn(workflowDto)) {
				// エラーメッセージ追加
				String[] aryRep = { getStringDate(dto.getSubstituteDate()), getNameSubstituteDay() };
				mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_9, aryRep);
			}
		}
	}
	
	/**
	 * 振出・休出申請チェック
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkOnHolidayRequest(RequestUtilBeanInterface requestUtil) throws MospException {
		WorkOnHolidayRequestDtoInterface workOnHolidayRequestDto = requestUtil.getWorkOnHolidayDto(true);
		if (workOnHolidayRequestDto == null) {
			return;
		}
		addSubstituteDateErrorMessage(workOnHolidayRequestDto.getRequestDate());
	}
	
	/**
	 * 勤怠チェック。
	 * @param requestUtil 申請ユーティリティ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkAttendance(RequestUtilBeanInterface requestUtil) throws MospException {
		// 勤怠申請情報(取下、下書、1次戻以外)を取得
		AttendanceDtoInterface attendanceDto = requestUtil.getApplicatedAttendance();
		if (attendanceDto == null) {
			// 勤怠申請情報(1次戻)設定
			attendanceDto = requestUtil.getFirstRevertedAttendance();
		}
		if (attendanceDto == null) {
			return;
		}
		addOthersRequestErrorMessage(attendanceDto.getWorkDate(), mospParams.getName("WorkManage"));
	}
	
	@Override
	public void checkAttendance(SubstituteDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		// 個人IDと勤務日と勤務回数から勤怠情報を取得
		AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(dto.getPersonalId(), dto.getSubstituteDate(),
				1);
		if (attendanceDto == null) {
			return;
		}
		// ワークフロー番号からワークフロー情報を取得
		WorkflowDtoInterface workflowDto = workflowDao.findForKey(attendanceDto.getWorkflow());
		// 下書又は取下又は1次戻の場合
		if (WorkflowUtility.isDraft(workflowDto) || WorkflowUtility.isWithDrawn(workflowDto)
				|| WorkflowUtility.isFirstReverted(workflowDto)) {
			return;
		}
		// 申請時のエラーメッセージ
		String[] aryRep = { getStringDate(dto.getSubstituteDate()), mospParams.getName("WorkManage"),
			getNameSubstituteDay() };
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_1, aryRep);
	}
	
	/**
	 * 半日振替時の申請チェックを行う。<br>
	 * ・勤務形態：午前休・午後休設定<br>
	 * ・振替の振替時は同じ勤務形態<br>
	 * @param dto 振替休日データ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkHalfRequest(SubstituteDtoInterface dto) throws MospException {
		// 半日振替でない場合
		if (!workOnHolidayRefer.useHalfSubstitute()) {
			// 処理終了
			return;
		}
		// 個人IDと出勤日と振替日を取得
		String personalId = dto.getPersonalId();
		Date workDate = dto.getWorkDate();
		Date substituteDate = dto.getSubstituteDate();
		// 申請ユーティリティ(出勤日)を準備
		RequestUtilBeanInterface requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, workDate);
		// 出勤日の振替休日情報(承認済)リストを取得(振替の振替である場合に存在する)
		List<SubstituteDtoInterface> beforeList = requestUtil.getSubstituteList(true);
		// 「振替の振替であり振替範囲が午前」または「振替の振替であり振替範囲が午後」である場合
		if (MospUtility.isEmpty(beforeList) == false && TimeRequestUtility.isHolidayRangeAm(dto)
				|| MospUtility.isEmpty(beforeList) == false && TimeRequestUtility.isHolidayRangePm(dto)) {
			// 申請ユーティリティを準備
			RequestUtilBeanInterface requestUtil2 = createBeanInstance(RequestUtilBeanInterface.class);
			// 対象日を準備
			Date targetDate = substituteDate;
			// 申請が2つ(半振休/半振休)の場合
			if (beforeList.size() == 2) {
				// 出勤日の振替休日情報(承認済)毎に処理
				for (SubstituteDtoInterface beforeDto : beforeList) {
					// 出勤日の振替休日情報と振替範囲が違う場合
					if (beforeDto.getSubstituteRange() != dto.getSubstituteRange()) {
						// 次の出勤日の振替休日情報(承認済)へ
						continue;
					}
					// 対象日を設定(出勤日の振替休日情報と振替範囲が同じ場合)
					targetDate = beforeDto.getWorkDate();
					// 申請ユーティリティ設定
					requestUtil2.setRequests(personalId, targetDate);
				}
			} else {
				// 申請ユーティリティ設定
				requestUtil2.setRequests(personalId, targetDate);
			}
			// 申請エンティティを取得
			RequestEntityInterface entity1 = requestUtil.getRequestEntity(personalId, workDate);
			RequestEntityInterface entity2 = requestUtil2.getRequestEntity(personalId, targetDate);
			// 勤務形態コード取得
			String workTypeCode1 = entity1.getWorkType(false);
			String workTypeCode2 = entity2.getWorkType(false);
			// 勤務形態変更申請の取得
			WorkTypeChangeRequestDtoInterface workTypeChangeDto1 = requestUtil.getWorkTypeChangeDto(false);
			WorkTypeChangeRequestDtoInterface workTypeChangeDto2 = requestUtil2.getWorkTypeChangeDto(false);
			// 勤務形態変更申請のワークフロー情報取得
			WorkflowDtoInterface workflowDto1 = null;
			WorkflowDtoInterface workflowDto2 = null;
			if (workTypeChangeDto1 != null) {
				// 勤務形態変更申請が存在する場合
				workflowDto1 = workflowReference.getLatestWorkflowInfo(workTypeChangeDto1.getWorkflow());
			}
			if (workTypeChangeDto2 != null) {
				// 勤務形態変更申請が存在する場合
				workflowDto2 = workflowReference.getLatestWorkflowInfo(workTypeChangeDto2.getWorkflow());
			}
			// 勤務形態変更申請の承認状況確認
			String requestName = mospParams.getName("Work", "Form", "Change", "Application");
			if (workflowDto1 != null && WorkflowUtility.isNotApproved(workflowDto1)) {
				// [出勤日]は勤務形態変更申請が完了していません。下書を行うか承認がされてから申請を行ってください。
				mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE, getStringDate(workDate),
						requestName);
			}
			if (workflowDto2 != null && WorkflowUtility.isNotApproved(workflowDto2)) {
				// [振替日]は勤務形態変更申請が完了していません。下書を行うか承認がされてから申請を行ってください。
				mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_REQUEST_STATE_COMPLETE,
						getStringDate(substituteDate), requestName);
			}
			if (mospParams.hasErrorMessage()) {
				return;
			}
			// 同じ勤務形態でない場合
			if (!workTypeCode1.equals(workTypeCode2)) {
				// [振替日]は勤務形態が異なるため申請できません。勤怠詳細ではなく、勤務形態変更申請より出勤日と振替日の勤務形態を同一にしてください。
				mospParams.addErrorMessage(TimeMessageConst.MSG_HALF_HOLIDAY_WORK_TYPE_ERROR,
						getStringDate(substituteDate));
				return;
			}
		}
		// 勤務形態チェック
		checkWorkTypeInfo(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
	}
	
	/**
	 * 振替日の勤務形態を確認する。<br>
	 * 半振替の場合、午前休・午後休が設定していないとエラー。<br>
	 * @param dto 振替休日情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkWorkTypeInfo(SubstituteDtoInterface dto) throws MospException {
		// 個人ID・対象日取得
		String personalId = dto.getPersonalId();
		Date targetDate = dto.getSubstituteDate();
		// クラス準備
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		// 取得
		requestUtil.setRequests(personalId, targetDate);
		// 勤怠エンティティ取得
		RequestEntityInterface entity = requestUtil.getRequestEntity(personalId, targetDate);
		// 勤務形態コード取得
		String workTypeCode = entity.getWorkType(false);
		// 午前又は午後の場合
		if (dto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_AM
				|| dto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 勤務形態情報取得
			WorkTypeDtoInterface workTypeDto = workTypeRefer.findForInfo(workTypeCode, targetDate);
			if (workTypeDto != null) {
				// 午前休時刻取得
				WorkTypeItemDtoInterface frontStart = workTypeItemRefer.getWorkTypeItemInfo(workTypeCode, targetDate,
						TimeConst.CODE_FRONTSTART);
				WorkTypeItemDtoInterface frontEnd = workTypeItemRefer.getWorkTypeItemInfo(workTypeCode, targetDate,
						TimeConst.CODE_FRONTEND);
				// 午後休時刻取得
				WorkTypeItemDtoInterface backStart = workTypeItemRefer.getWorkTypeItemInfo(workTypeCode, targetDate,
						TimeConst.CODE_BACKSTART);
				WorkTypeItemDtoInterface backEnd = workTypeItemRefer.getWorkTypeItemInfo(workTypeCode, targetDate,
						TimeConst.CODE_BACKEND);
				// エラーメッセージ文字列
				String[] rep = { mospParams.getName("Code", "SingleColon") + workTypeCode,
					mospParams.getName("AmRest", "Or", "PmRest"), mospParams.getName("HalfDay", "Transfer") };
				// 開始時刻・終業時刻がない場合
				if (frontStart == null || frontEnd == null || backStart == null || backEnd == null) {
					// エラーメッセージ追加
					mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_WORK_TYPE_INFO, rep);
					return;
				}
				// 午前休がない場合
				if (DateUtility.getDefaultTime().compareTo(frontStart.getWorkTypeItemValue()) == 0
						&& DateUtility.getDefaultTime().compareTo(frontEnd.getWorkTypeItemValue()) == 0) {
					// エラーメッセージ追加
					mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_WORK_TYPE_INFO, rep);
					return;
				}
				// 午後休がない場合
				if (DateUtility.getDefaultTime().compareTo(backStart.getWorkTypeItemValue()) == 0
						&& DateUtility.getDefaultTime().compareTo(backEnd.getWorkTypeItemValue()) == 0) {
					// エラーメッセージ追加
					mospParams.addErrorMessage(TimeMessageConst.MSG_NOT_WORK_TYPE_INFO, rep);
					return;
				}
			}
		}
	}
	
	/**
	 * 休日チェック。<br>
	 * 法定休日でなく且つ所定休日でない場合、エラーメッセージを設定する。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkHolidayDate(SubstituteDtoInterface dto) throws MospException {
		// 振替休日情報が存在しない場合
		if (dto == null) {
			return;
		}
		// 法定休日でなく且つ所定休日でない場合
		if (TimeUtility.isHoliday(dto.getSubstituteType()) == false) {
			// MosP処理情報にエラーメッセージを設定
			TimeMessageUtility.addErrorWorkOnNotHoliday(mospParams);
		}
		// 半日法定振替出勤が可能である場合
		if (mospParams.getApplicationPropertyBool(TimeConst.APP_HALF_LEGAL_SUBSTITUTE)) {
			// 確認不要
			return;
		}
		// 全休の場合
		if (dto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_ALL) {
			// 確認不要
			return;
		}
		// 半日法定振替出勤である場合
		if (TimeUtility.isLegalHoliday(dto.getSubstituteType())) {
			// MosP処理情報にエラーメッセージを設定
			TimeMessageUtility.addErrorSubstituteHalfLegalHoliday(mospParams);
		}
	}
	
	/**
	 * 入社チェック。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEntered(SubstituteDtoInterface dto) throws MospException {
		if (isEntered(dto.getPersonalId(), dto.getSubstituteDate())) {
			// 入社している場合
			return;
		}
		// 入社していない場合
		PfMessageUtility.addErrorEmployeeNotJoin(mospParams);
	}
	
	@Override
	public void checkSuspension(SubstituteDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		// 個人IDと対象年月日から休職判断
		if (suspensionReference.isSuspended(dto.getPersonalId(), dto.getSubstituteDate())) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeSuspended(mospParams);
		}
	}
	
	@Override
	public void checkRetirement(SubstituteDtoInterface dto) throws MospException {
		if (dto == null) {
			return;
		}
		// 個人IDと対象年月日から退職判断
		if (retirementReference.isRetired(dto.getPersonalId(), dto.getSubstituteDate())) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeRetired(mospParams);
		}
	}
	
	/**
	 * 残業申請の確認を行う。
	 * @param dto 振替休日申請DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void checkOverTime(SubstituteDtoInterface dto) throws MospException {
		// 個人ID・対象日取得
		String personalId = dto.getPersonalId();
		Date substituteDate = dto.getSubstituteDate();
		// 振替日が半休か判定する
		float holidayTimes = 0F;
		holidayTimes = checkHolidayRange(dto, holidayTimes, personalId, substituteDate);
		if (Float.compare(holidayTimes, TimeConst.HOLIDAY_TIMES_HALF) == 0) {
			// 振替日に半休がされている場合
			// 判定用リスト
			List<OvertimeRequestDtoInterface> overTimeList = new ArrayList<OvertimeRequestDtoInterface>();
			// 残業申請の有無を確認する
			List<OvertimeRequestDtoInterface> overTimeRequestList = overtimeRequestDao.findForList(personalId,
					substituteDate);
			// 残業申請情報毎に処理
			for (OvertimeRequestDtoInterface overTimeDto : overTimeRequestList) {
				// ワークフロー情報取得
				WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(overTimeDto.getWorkflow());
				if (workflowDto != null
						&& (!WorkflowUtility.isDraft(workflowDto) && !WorkflowUtility.isWithDrawn(workflowDto))) {
					// 状態が下書き、取下以外の場合
					overTimeList.add(overTimeDto);
				}
			}
			if (!overTimeList.isEmpty()) {
				// 残業申請されている場合
				addOthersRequestErrorMessage(substituteDate, mospParams.getName("OvertimeWork"));
			}
		}
	}
	
	/**
	 * 振替日に半休申請が行われているか確認する。
	 * @param dto 振替休日申請DTO
	 * @param holidayTimes 休暇回数
	 * @param personalId 個人ID
	 * @param substituteDate 振替休日日
	 * @return 休暇回数(半休)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public float checkHolidayRange(SubstituteDtoInterface dto, float holidayTimes, String personalId,
			Date substituteDate) throws MospException {
		// 申請ユーティリティクラス準備
		RequestUtilBeanInterface requestUtil = (RequestUtilBeanInterface)createBean(RequestUtilBeanInterface.class);
		requestUtil.setRequests(personalId, substituteDate);
		// 振替日の休暇範囲確認
		int holidayRange = requestUtil.checkHolidayRangeHoliday(requestUtil.getHolidayList(false));
		if (holidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || holidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 休暇範囲が午前休か午後休の場合、半休判定
			return holidayTimes + TimeConst.HOLIDAY_TIMES_HALF;
		}
		// 振替日の代休範囲確認
		int subHolidayRange = requestUtil.checkHolidayRangeSubHoliday(requestUtil.getSubHolidayList(false));
		if (subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_AM || subHolidayRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 休暇範囲が午前休か午後休の場合、半休判定
			return holidayTimes + TimeConst.HOLIDAY_TIMES_HALF;
		}
		// 振替休日申請リスト取得
		List<SubstituteDtoInterface> substituteDtoList = requestUtil.getSubstituteList(false);
		// 休暇範囲確認用リストを用意
		List<SubstituteDtoInterface> substituteList = new ArrayList<SubstituteDtoInterface>();
		// 振替休日申請情報ごとに処理
		for (SubstituteDtoInterface substituteDto : substituteDtoList) {
			if (dto.getWorkflow() != substituteDto.getWorkflow()) {
				// 申請対象と異なるワークフローの振替休日申請情報を休暇範囲確認用リストに追加
				substituteList.add(substituteDto);
			}
		}
		// 振替日の振替休日範囲確認
		int substituteRange = requestUtil.checkHolidayRangeSubstitute(substituteList);
		if (substituteRange == TimeConst.CODE_HOLIDAY_RANGE_AM || substituteRange == TimeConst.CODE_HOLIDAY_RANGE_PM) {
			// 休暇範囲が午前休か午後休の場合、半休判定
			return holidayTimes + TimeConst.HOLIDAY_TIMES_HALF;
		}
		return holidayRange;
	}
	
	/**
	 * 勤務形態変更申請の確認を行う。
	 * @param dto 振替休日申請DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void checkWorkTypeChange(SubstituteDtoInterface dto) throws MospException {
		// 個人ID・対象日取得
		String personalId = dto.getPersonalId();
		Date substituteDate = dto.getSubstituteDate();
		// 振替日が半休か判定する
		float holidayTimes = 0F;
		holidayTimes = checkHolidayRange(dto, holidayTimes, personalId, substituteDate);
		if (Float.compare(holidayTimes, TimeConst.HOLIDAY_TIMES_HALF) == 0) {
			// 勤務形態変更申請の有無の確認
			WorkTypeChangeRequestDtoInterface workTypeChangeDto = workTypeChangeReference
				.findForKeyOnWorkflow(personalId, substituteDate);
			if (workTypeChangeDto != null) {
				// 勤務形態変更申請がある場合
				WorkflowDtoInterface workflowDto = workflowReference
					.getLatestWorkflowInfo(workTypeChangeDto.getWorkflow());
				if (workflowDto != null
						&& (!WorkflowUtility.isDraft(workflowDto) && !WorkflowUtility.isWithDrawn(workflowDto))) {
					// 勤務形態変更申請されている場合
					addOthersRequestErrorMessage(substituteDate, mospParams.getName("Work", "Form", "Change"));
				}
			}
		}
	}
	
	/**
	 * 仮締チェック。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkTighten(SubstituteDtoInterface dto) throws MospException {
		cutoffUtil.checkTighten(dto.getPersonalId(), dto.getSubstituteDate(), getNameSubstituteDay());
	}
	
	/**
	 * 法定休日かどうか確認する。
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：法定休日、false：法定休日でない)
	 */
	protected boolean isLegalDayOff(String workTypeCode) {
		return TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 所定休日かどうか確認する。
	 * @param workTypeCode 勤務形態コード
	 * @return 確認結果(true：所定休日、false：所定休日でない)
	 */
	protected boolean isPrescribedDayOff(String workTypeCode) {
		return TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY.equals(workTypeCode);
	}
	
	/**
	 * 振替日エラーメッセージを追加する。
	 * @param date 対象日
	 */
	protected void addSubstituteDateErrorMessage(Date date) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_3,
				new String[]{ DateUtility.getStringDate(date), getNameGoingWorkDay(), getNameSubstituteDay() });
	}
	
	/**
	 * 振替日に既に別の申請が行われている場合、エラーメッセージを追加する。<br>
	 * @param date 対象日
	 * @param requestTitle 対象申請名称
	 */
	@Override
	protected void addOthersRequestErrorMessage(Date date, String requestTitle) {
		mospParams.addErrorMessage(TimeMessageConst.MSG_REQUEST_CHECK_12,
				new String[]{ getStringDate(date), requestTitle });
	}
	
	/**
	 * 出勤日名称を取得する。
	 * @return 出勤日名称
	 */
	protected String getNameGoingWorkDay() {
		return PfNameUtility.goingWorkDay(mospParams);
	}
	
	/**
	 * 振替日名称を取得する。
	 * @return 振替日名称
	 */
	protected String getNameSubstituteDay() {
		return TimeNamingUtility.substituteDay(mospParams);
	}
	
}
