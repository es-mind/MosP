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

import java.util.Collections;
import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.dao.human.EntranceDaoInterface;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.TotalTimeEntityReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.AttendanceTransactionDaoInterface;
import jp.mosp.time.dao.settings.DifferenceRequestDaoInterface;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.TimeSettingDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeChangeRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.TimeSettingDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.entity.CutoffEntityInterface;
import jp.mosp.time.entity.RequestDetectEntityInterface;
import jp.mosp.time.entity.TotalTimeEntityInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠集計関連エンティティ取得処理。<br>
 */
public class TotalTimeEntityReferenceBean extends TimeBean implements TotalTimeEntityReferenceBeanInterface {
	
	/**
	 * 勤怠情報取得対象となる締期間初日以前の日数。<br>
	 * <br>
	 * 締期間初日が週の最終日だった場合の6日と、その前日。<br>
	 * 前日が法定休日出勤だった場合に、7日前が必要になる。<br>
	 * <br>
	 */
	public static final int						DAYS_FORMER_ATTENDANCE	= -7;
	
	/**
	 * 入社情報DAOクラス。<br>
	 */
	protected EntranceDaoInterface				entranceDao;
	
	/**
	 * 休職情報参照クラス。<br>
	 */
	protected SuspensionReferenceBeanInterface	suspentionReference;
	
	/**
	 * 退社情報DAOクラス。<br>
	 */
	protected RetirementDaoInterface			retirementDao;
	
	/**
	 * 休暇種別管理DAOクラス。<br>
	 */
	protected HolidayDaoInterface				holidayDao;
	
	/**
	 * 勤怠データDAOクラス。<br>
	 */
	protected AttendanceDaoInterface			attendanceDao;
	
	/**
	 * 残業申請データDAOクラス。<br>
	 */
	protected OvertimeRequestDaoInterface		overtimeRequestDao;
	
	/**
	 * 休暇申請データDAOクラス。<br>
	 */
	protected HolidayRequestDaoInterface		holidayRequestDao;
	
	/**
	 * 休日出勤申請データDAOクラス。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface	workOnHolidayRequestDao;
	
	/**
	 * 代休申請データDAOクラス。<br>
	 */
	protected SubHolidayRequestDaoInterface		subHolidayRequestDao;
	
	/**
	 * 勤務形態変更申請DAOクラス。<br>
	 */
	protected WorkTypeChangeRequestDaoInterface	workTypeChangeRequestDao;
	
	/**
	 * 時差出勤申請データDAOクラス。<br>
	 */
	protected DifferenceRequestDaoInterface		differenceRequestDao;
	
	/**
	 * 代休データDAOクラス。<br>
	 */
	protected SubHolidayDaoInterface			subHolidayDao;
	
	/**
	 * ワークフローDAOクラス。<br>
	 */
	protected WorkflowDaoInterface				workflowDao;
	
	/**
	 * 勤怠トランザクションDAO。<br>
	 */
	protected AttendanceTransactionDaoInterface	attendanceTransactionDao;
	
	/**
	 * 勤怠設定管理DAOクラス。<br>
	 */
	protected TimeSettingDaoInterface			timeSettingDao;
	
	/**
	 * 振替休日参照処理。<br>
	 */
	protected SubstituteReferenceBeanInterface	substituteRefer;
	
	/**
	 * 勤務形態マスタ参照クラス。<br>
	 */
	protected WorkTypeReferenceBeanInterface	workTypeReference;
	
	/**
	 * カレンダユーティリティ処理。<br>
	 */
	protected ScheduleUtilBeanInterface			scheduleUtil;
	
	/**
	 * プラットフォームマスタ参照クラス。<br>
	 */
	protected PlatformMasterBeanInterface		platformMaster;
	
	/**
	 * 勤怠関連マスタ参照処理。<br>
	 */
	protected TimeMasterBeanInterface			timeMaster;
	
	
	@Override
	public void initBean() throws MospException {
		// Bean及びDAO準備
		entranceDao = createDaoInstance(EntranceDaoInterface.class);
		retirementDao = createDaoInstance(RetirementDaoInterface.class);
		holidayDao = createDaoInstance(HolidayDaoInterface.class);
		attendanceDao = createDaoInstance(AttendanceDaoInterface.class);
		overtimeRequestDao = createDaoInstance(OvertimeRequestDaoInterface.class);
		holidayRequestDao = createDaoInstance(HolidayRequestDaoInterface.class);
		workOnHolidayRequestDao = createDaoInstance(WorkOnHolidayRequestDaoInterface.class);
		subHolidayRequestDao = createDaoInstance(SubHolidayRequestDaoInterface.class);
		workTypeChangeRequestDao = createDaoInstance(WorkTypeChangeRequestDaoInterface.class);
		differenceRequestDao = createDaoInstance(DifferenceRequestDaoInterface.class);
		workflowDao = createDaoInstance(WorkflowDaoInterface.class);
		timeSettingDao = createDaoInstance(TimeSettingDaoInterface.class);
		attendanceTransactionDao = createDaoInstance(AttendanceTransactionDaoInterface.class);
		subHolidayDao = createDaoInstance(SubHolidayDaoInterface.class);
		substituteRefer = createBeanInstance(SubstituteReferenceBeanInterface.class);
		suspentionReference = createBeanInstance(SuspensionReferenceBeanInterface.class);
		workTypeReference = createBeanInstance(WorkTypeReferenceBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
	}
	
	@Override
	public TotalTimeEntityInterface getTotalTimeEntity(String personalId, int targetYear, int targetMonth,
			CutoffEntityInterface cutoff) throws MospException {
		// 勤怠集計エンティティを準備
		TotalTimeEntityInterface entity = (TotalTimeEntityInterface)createObject(TotalTimeEntityInterface.class);
		// MosP処理情報を設定
		entity.setMospParams(mospParams);
		// 個人ID設定
		entity.setPersonalId(personalId);
		// 対象年月設定
		entity.setCalculationYear(targetYear);
		entity.setCalculationMonth(targetMonth);
		// 締日コード設定
		entity.setCutoffCode(cutoff.getCode());
		// 締日情報が取得できない場合
		if (cutoff.isExist() == false) {
			return entity;
		}
		// 締日を取得
		int cutoffDate = cutoff.getCutoffDate();
		// 締期間基準日を取得
		Date cutoffTermTargetDate = cutoff.getCutoffTermTargetDate(targetYear, targetMonth, mospParams);
		// 締期間初日を取得
		Date firstDate = cutoff.getCutoffFirstDate(targetYear, targetMonth, mospParams);
		// 締期間最終日を取得
		Date lastDate = cutoff.getCutoffLastDate(targetYear, targetMonth, mospParams);
		// 締期間初日(個人)を取得
		Date personalFirstDate = getPersonalFirstDate(personalId, targetYear, targetMonth, cutoffDate);
		// 締期間最終日(個人)を取得
		Date personalLastDate = getPersonalLastDate(personalId, targetYear, targetMonth, cutoffDate);
		// 締期間(個人)が取得できなかった場合
		if (personalFirstDate == null || personalLastDate == null) {
			// 計算対象外
			return entity;
		}
		// 締期間初日の7日前を取得(週40時間計算用に勤怠申請情報を取得するため)
		Date attendanceFirstDate = DateUtility.addDay(firstDate, DAYS_FORMER_ATTENDANCE);
		// 休暇種別情報群取得
		entity.setHolidaySet(timeMaster.getHolidaySet(cutoffTermTargetDate));
		// 締期間初日設定
		entity.setCutoffFirstDate(firstDate);
		// 締期間最終日設定
		entity.setCutoffLastDate(lastDate);
		// 休職情報リスト設定
		entity.setSuspensionList(suspentionReference.getSuspentionList(personalId));
		// 締期間(個人)対象日リスト設定
		entity.setTargetDateList(TimeUtility.getDateList(personalFirstDate, personalLastDate));
		// 設定適用情報群設定(締期間(個人))
		entity.setApplicationMap(timeMaster.getApplicationMap(personalId, attendanceFirstDate, personalLastDate));
		// 勤怠設定情報群設定(締期間(個人))
		entity.setTimeSettingMap(timeMaster.getTimeSettingMap(entity.getApplicationMap()));
		// カレンダ日情報群設定(締期間(個人))
		entity.setScheduleMap(scheduleUtil.getScheduledWorkTypeCodes(personalId, personalFirstDate, personalLastDate));
		// 勤怠申請リスト取得
		entity.setAttendanceList(attendanceDao.findForList(personalId, attendanceFirstDate, lastDate));
		// 休暇申請リスト取得
		entity.setHolidayRequestList(holidayRequestDao.findForTerm(personalId, firstDate, lastDate));
		// 休日出勤申請リスト取得
		entity.setWorkOnHolidayRequestList(workOnHolidayRequestDao.findForList(personalId, firstDate, lastDate));
		// 残業申請リスト取得
		entity.setOvertimeRequestList(overtimeRequestDao.findForList(personalId, firstDate, lastDate));
		// 勤務形態変更申請リスト取得
		entity.setWorkTypeChangeRequestList(workTypeChangeRequestDao.findForTerm(personalId, firstDate, lastDate));
		// 時差出勤申請リスト取得
		entity.setDifferenceRequestList(differenceRequestDao.findForList(personalId, firstDate, lastDate));
		// 振替休日データ取得
		entity.setSubstitubeList(substituteRefer.getSubstituteList(personalId, firstDate, lastDate));
		// 締期間初日(個人)から代休取得期限だけ遡った日付を取得
		Date subHolidayFirstDate = getDateOnTimeSetteingDto(entity);
		// 代休データリスト取得
		entity.setSubHolidayList(subHolidayDao.findSubHolidayList(personalId, subHolidayFirstDate, lastDate));
		// 代休勤怠設定マップ取得
		entity.setSubHolidayTimeSettingMap(getSubHolidayTimeSettingMap(entity.getSubHolidayList()));
		// 代休申請リスト取得
		entity.setSubHolidayRequestList(subHolidayRequestDao.findForList(personalId, subHolidayFirstDate, lastDate));
		// ワークフロー日付範囲を取得
		Date workflowFirstDate = getRequestStartDateForWorkflow(entity);
		Date workflowLastDate = getRequestEndDateForWorkflow(entity);
		// ワークフロー情報群取得
		entity.setWorkflowMap(workflowDao.findForCondition(personalId, workflowFirstDate, workflowLastDate));
		entity.setAttendanceTransactionSet(getAttendanceTransactionSet(personalId, firstDate, lastDate));
		// 振替勤務形態コード群設定
		entity.setSubstitutedMap(getSubstitutedMap(entity));
		// 勤務形態エンティティ群を準備
		Map<String, List<WorkTypeEntityInterface>> workTypeEntityMap = Collections.emptyMap();
		// 時短時間機能利用可の場合
		if (mospParams.getApplicationPropertyBool(TimeConst.APP_ADD_USE_SHORT_UNPAID)) {
			// 勤務形態エンティティ群を取得
			workTypeEntityMap = getWorkTypeEntityMap(entity);
		}
		// 勤務形態エンティティ群を設定
		entity.setWorkTypeEntityMap(workTypeEntityMap);
		// 追加業務ロジック処理
		doAdditionalLogic(TimeConst.CODE_KEY_ADD_TOTALTIMEENTITYREFERENCEBEAN_GETTOTALTIMEENTITY, entity);
		// 勤怠集計エンティティを取得
		return entity;
	}
	
	/**
	 * 締期間初日(個人)を取得する。<br>
	 * 対象個人IDの最も古い人事基本情報の有効日が締期間初日よりも後の場合は、
	 * 最も古い人事基本情報の有効日を取得する。<br>
	 * 対象個人IDの入社日が締期間初日よりも後の場合は、入社日を取得する。<br>
	 * 対象個人IDが入社していない場合
	 * 及び人事基本情報が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param personalId  対象個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param cutoffDate  締日
	 * @return 締期間初日(個人)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getPersonalFirstDate(String personalId, int targetYear, int targetMonth, int cutoffDate)
			throws MospException {
		// 入社日取得
		EntranceDtoInterface entranceDto = entranceDao.findForInfo(personalId);
		// 入社日確認
		if (entranceDto == null) {
			return null;
		}
		// 人事基本情報履歴を取得
		List<HumanDtoInterface> humanList = platformMaster.getHumanHistory(personalId);
		// 人事基本情報が存在しない場合
		if (humanList.isEmpty()) {
			return null;
		}
		// 締期間初日を取得
		Date cutoffFirstDate = TimeUtility.getCutoffFirstDate(cutoffDate, targetYear, targetMonth, mospParams);
		// 締期間初日が最も古い人事基本情報の有効日よりも前の場合
		if (cutoffFirstDate.before(humanList.get(0).getActivateDate())) {
			// 最も古い人事基本情報の有効日を取得
			return humanList.get(0).getActivateDate();
		}
		// 締期間初日が入社日よりも前の場合
		if (cutoffFirstDate.before(entranceDto.getEntranceDate())) {
			// 入社日を取得
			return entranceDto.getEntranceDate();
		}
		// 締期間初日を取得
		return cutoffFirstDate;
	}
	
	/**
	 * 締期間最終日(個人)を取得する。<br>
	 * 対象個人IDの退社日が締期間最終日よりも前の場合は、退社日を取得する。<br>
	 * <br>
	 * @param personalId  対象個人ID
	 * @param targetYear  対象年
	 * @param targetMonth 対象月
	 * @param cutoffDate  締日
	 * @return 締期間最終日(個人)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Date getPersonalLastDate(String personalId, int targetYear, int targetMonth, int cutoffDate)
			throws MospException {
		// 締期間最終日を取得
		Date cutoffLastDate = TimeUtility.getCutoffLastDate(cutoffDate, targetYear, targetMonth, mospParams);
		// 退社日取得
		RetirementDtoInterface retirementDto = retirementDao.findForInfo(personalId);
		// 退社日確認
		if (retirementDto == null) {
			return cutoffLastDate;
		}
		// 締期間最終日が退社日よりも後の場合
		if (cutoffLastDate.after(retirementDto.getRetirementDate())) {
			// 退社日を取得
			return retirementDto.getRetirementDate();
		}
		// 締期間最終日を取得
		return cutoffLastDate;
	}
	
	/**
	 * 締期間初日から代休取得期限だけ遡った日付を取得する。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 締期間初日(個人)から代休取得期限だけ遡った日付
	 */
	protected Date getDateOnTimeSetteingDto(TotalTimeEntityInterface entity) {
		// 締期間初日を取得
		Date targetDate = entity.getCutoffFirstDate();
		// 勤怠設定取得
		TimeSettingDtoInterface timeSettingDto = entity.getTimeSettingMap().get(targetDate);
		if (timeSettingDto == null) {
			return entity.getCutoffFirstDate();
		}
		Date date = addDay(DateUtility.addMonth(targetDate, -timeSettingDto.getSubHolidayLimitMonth()),
				-timeSettingDto.getSubHolidayLimitDate());
		return date;
	}
	
	/**
	 * 代休出勤日の勤怠設定情報マップを取得する。<br>
	 * @param subHolidayList 代休リスト
	 * @return 代休出勤日の勤怠設定情報マップ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected Map<Date, TimeSettingDtoInterface> getSubHolidayTimeSettingMap(
			List<SubHolidayDtoInterface> subHolidayList) throws MospException {
		// マップ準備
		Map<Date, TimeSettingDtoInterface> map = new HashMap<Date, TimeSettingDtoInterface>();
		// 代休情報毎に処理
		for (SubHolidayDtoInterface subHolidayDto : subHolidayList) {
			// 休日出勤日取得
			Date workDate = subHolidayDto.getWorkDate();
			// 勤怠設定情報取得
			TimeSettingDtoInterface dto = map.get(workDate);
			if (dto != null) {
				continue;
			}
			// 勤怠設定情報マップ取得
			Map<Date, TimeSettingDtoInterface> tmpMap = timeMaster
				.getTimeSettingMap(timeMaster.getApplicationMap(subHolidayDto.getPersonalId(), workDate, workDate));
			if (tmpMap == null) {
				continue;
			}
			map.putAll(tmpMap);
		}
		return map;
	}
	
	/**
	 * 申請初日(期間内)を取得する。<br>
	 * <br>
	 * ワークフロー情報を取得するのに用いる。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 申請初日(期間内)
	 */
	protected Date getRequestStartDateForWorkflow(TotalTimeEntityInterface entity) {
		// 申請初日を準備(締期間初日)
		Date startDate = entity.getCutoffFirstDate();
		// 勤怠申請リスト
		for (AttendanceDtoInterface dto : entity.getAttendanceList()) {
			if (startDate.compareTo(dto.getWorkDate()) > 0) {
				startDate = dto.getWorkDate();
				continue;
			}
		}
		// 	休暇申請リスト
		for (HolidayRequestDtoInterface dto : entity.getHolidayRequestList()) {
			if (startDate.compareTo(dto.getRequestStartDate()) > 0) {
				startDate = dto.getRequestStartDate();
				continue;
			}
		}
		// 休日出勤申請リスト
		for (WorkOnHolidayRequestDtoInterface dto : entity.getWorkOnHolidayRequestList()) {
			if (startDate.compareTo(dto.getRequestDate()) > 0) {
				startDate = dto.getRequestDate();
				continue;
			}
		}
		// 振替休日データリスト
		for (SubstituteDtoInterface dto : entity.getSubstitubeList()) {
			if (startDate.compareTo(dto.getWorkDate()) > 0) {
				startDate = dto.getWorkDate();
				continue;
			}
		}
		// 代休申請リスト
		for (SubHolidayRequestDtoInterface dto : entity.getSubHolidayRequestList()) {
			if (startDate.compareTo(dto.getRequestDate()) > 0) {
				startDate = dto.getRequestDate();
				continue;
			}
		}
		// 残業申請リスト
		for (OvertimeRequestDtoInterface dto : entity.getOvertimeRequestList()) {
			if (startDate.compareTo(dto.getRequestDate()) > 0) {
				startDate = dto.getRequestDate();
				continue;
			}
		}
		// 勤務形態変更申請リスト
		for (WorkTypeChangeRequestDtoInterface dto : entity.getWorkTypeChangeRequestList()) {
			if (startDate.compareTo(dto.getRequestDate()) > 0) {
				startDate = dto.getRequestDate();
				continue;
			}
		}
		// 時差出勤申請リスト
		for (DifferenceRequestDtoInterface dto : entity.getDifferenceRequestList()) {
			if (startDate.compareTo(dto.getRequestDate()) > 0) {
				startDate = dto.getRequestDate();
				continue;
			}
		}
		return startDate;
	}
	
	/**
	 * 申請最終日(期間内)を取得する。<br>
	 * <br>
	 * ワークフロー情報を取得するのに用いる。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 申請最終日(期間内)
	 */
	protected Date getRequestEndDateForWorkflow(TotalTimeEntityInterface entity) {
		// 締期間最終日取得
		Date endDate = entity.getCutoffLastDate();
		// 振替休日データリスト
		for (SubstituteDtoInterface dto : entity.getSubstitubeList()) {
			if (endDate.compareTo(dto.getWorkDate()) < 0) {
				endDate = dto.getWorkDate();
				continue;
			}
		}
		return endDate;
	}
	
	/**
	 * 勤怠トランザクション群を取得する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間末日
	 * @return 勤怠トランザクション群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<AttendanceTransactionDtoInterface> getAttendanceTransactionSet(String personalId, Date firstDate,
			Date lastDate) throws MospException {
		// 勤怠トランザクションマップを取得
		Map<Date, AttendanceTransactionDtoInterface> map = attendanceTransactionDao.findForTerm(personalId, firstDate,
				lastDate);
		// セットに変換
		return new HashSet<AttendanceTransactionDtoInterface>(map.values());
	}
	
	@Override
	public RequestDetectEntityInterface getRequestDetectEntity(TotalTimeEntityInterface totalTimeEntity)
			throws MospException {
		// 申請確認エンティティを準備
		RequestDetectEntityInterface entity = (RequestDetectEntityInterface)createObject(
				RequestDetectEntityInterface.class);
		// 勤怠集計エンティティから必要な情報を取得
		entity.setTargetDateList(totalTimeEntity.getTargetDateList());
		entity.setSuspensionList(totalTimeEntity.getSuspensionList());
		entity.setScheduleMap(totalTimeEntity.getScheduleMap());
		entity.setAttendanceList(totalTimeEntity.getAttendanceList());
		entity.setWorkOnHolidayRequestList(totalTimeEntity.getWorkOnHolidayRequestList());
		entity.setHolidayRequestList(totalTimeEntity.getHolidayRequestList());
		entity.setSubHolidayRequestList(totalTimeEntity.getSubHolidayRequestList());
		entity.setOvertimeRequestList(totalTimeEntity.getOvertimeRequestList());
		entity.setWorkTypeChangeRequestList(totalTimeEntity.getWorkTypeChangeRequestList());
		entity.setDifferenceRequestList(totalTimeEntity.getDifferenceRequestList());
		entity.setSubstituteList(totalTimeEntity.getSubstitubeList());
		entity.setWorkflowMap(totalTimeEntity.getWorkflowMap());
		// 申請確認エンティティを取得
		return entity;
	}
	
	@Override
	public RequestDetectEntityInterface getRequestDetectEntity(String personalId, int targetYear, int targetMonth,
			int cutoffDate) throws MospException {
		// 申請検出エンティティを準備
		RequestDetectEntityInterface entity = (RequestDetectEntityInterface)createObject(
				RequestDetectEntityInterface.class);
		// 締期間初日及び最終日(個人)を取得
		Date firstDate = getPersonalFirstDate(personalId, targetYear, targetMonth, cutoffDate);
		Date lastDate = getPersonalLastDate(personalId, targetYear, targetMonth, cutoffDate);
		// 申請検出エンティティに個人IDを設定
		entity.setPersonalId(personalId);
		// 申請検出エンティティに対象日リスト(締期間(個人)対象日リスト)を設定
		entity.setTargetDateList(TimeUtility.getDateList(firstDate, lastDate));
		// 申請検出エンティティに予定勤務形態コード群を設定
		entity.setScheduleMap(scheduleUtil.getScheduledWorkTypeCodes(personalId, firstDate, lastDate));
		// 休職情報を設定
		entity.setSuspensionList(suspentionReference.getSuspentionList(personalId));
		// 締期間(個人)における勤怠申請リスト取得
		List<AttendanceDtoInterface> attendances = attendanceDao.findForList(personalId,
				DateUtility.addDay(firstDate, -6), lastDate);
		entity.setAttendanceList(attendances);
		// 休暇申請リスト取得
		List<HolidayRequestDtoInterface> holidayRequests = holidayRequestDao.findForTerm(personalId, firstDate,
				lastDate);
		entity.setHolidayRequestList(holidayRequests);
		// 休日出勤申請リスト取得
		List<WorkOnHolidayRequestDtoInterface> workOnHolidayRequests = workOnHolidayRequestDao.findForList(personalId,
				firstDate, lastDate);
		entity.setWorkOnHolidayRequestList(workOnHolidayRequests);
		// 残業申請リスト取得
		List<OvertimeRequestDtoInterface> overtimeRequests = overtimeRequestDao.findForList(personalId, firstDate,
				lastDate);
		entity.setOvertimeRequestList(overtimeRequests);
		// 勤務形態変更申請リスト取得
		List<WorkTypeChangeRequestDtoInterface> workTypeChangeRequests = workTypeChangeRequestDao
			.findForTerm(personalId, firstDate, lastDate);
		entity.setWorkTypeChangeRequestList(workTypeChangeRequests);
		// 時差出勤申請リスト取得
		List<DifferenceRequestDtoInterface> differenceRequests = differenceRequestDao.findForList(personalId, firstDate,
				lastDate);
		entity.setDifferenceRequestList(differenceRequests);
		// 代休申請リスト取得
		List<SubHolidayRequestDtoInterface> subHolidayRequests = subHolidayRequestDao.findForList(personalId, firstDate,
				lastDate);
		entity.setSubHolidayRequestList(subHolidayRequests);
		// 振替休日リスト取得
		List<SubstituteDtoInterface> substitutes = substituteRefer.getSubstituteList(personalId, firstDate, lastDate);
		entity.setSubstituteList(substitutes);
		// ワークフロー番号群を取得
		Set<Long> workflowSet = WorkflowUtility.getWorkflowSet(attendances, overtimeRequests, holidayRequests,
				workOnHolidayRequests, substitutes, subHolidayRequests, workTypeChangeRequests, differenceRequests);
		// ワークフロー情報群を取得
		Map<Long, WorkflowDtoInterface> workflows = workflowDao.findForInKey(workflowSet);
		// ワークフロー情報群を設定
		entity.setWorkflowMap(workflows);
		// 勤怠集計エンティティ参照申請検出エンティティ取得追加処理を実行
		doStoredLogic(TimeConst.CODE_KEY_TOTAL_TIME_ENT_REF_DETECT_ADDONS, personalId, firstDate, lastDate, workflows);
		// 申請検出エンティティを取得
		return entity;
	}
	
	/**
	 * 振出・休出勤務形態コード群を取得する。<br>
	 * <br>
	 * 振出・休出申請により出勤する日の予定勤務形態コード群を取得する。<br>
	 * 但し、承認済でない振出・休出申請は、考慮しない。<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 振替勤務形態コード群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<Date, String> getSubstitutedMap(TotalTimeEntityInterface entity) throws MospException {
		// 振替勤務形態コード群を準備
		Map<Date, String> substitutedMap = new HashMap<Date, String>();
		// 対象日リスト内の日毎に処理
		for (Date targetDate : entity.getTargetDateList()) {
			// 振替勤務形態コード群に空白を設定
			substitutedMap.put(targetDate, "");
		}
		// 個人IDを取得
		String personalId = entity.getPersonalId();
		// 振出・休出申請情報群(キー：申請日)(承認済)を取得
		Map<Date, WorkOnHolidayRequestDtoInterface> workOnHolidayRequests = TimeUtility.getRequestDateMap(
				entity.getWorkOnHolidayRequestList(), entity.getWorkflowMap(), WorkflowUtility.getCompletedStatuses());
		// 振出・休出申請情報(振替申請(勤務形態変更なし)のみ)(下書及び取下以外)から出勤日群を取得
		Set<Date> subWorkDates = TimeUtility.getSubstituteWorkDates(workOnHolidayRequests, entity.getWorkflowMap());
		// 振替日群(キー：出勤日)を取得
		Map<Date, Date> substituteDates = substituteRefer.getSubstituteDates(personalId, subWorkDates);
		// 振り替えられたカレンダ日情報群(キー：勤務日)を取得
		Map<Date, ScheduleDateDtoInterface> substitutedSchedules = scheduleUtil.getSubstitutedSchedules(personalId,
				substituteDates);
		// 振出・休出申請毎に処理
		for (Entry<Date, WorkOnHolidayRequestDtoInterface> entry : workOnHolidayRequests.entrySet()) {
			// 振出・休出申請を取得
			WorkOnHolidayRequestDtoInterface dto = entry.getValue();
			// 申請日(振出・休出日)を取得
			Date requestDate = dto.getRequestDate();
			// 振替申請フラグと休出種別確認を取得
			int substitute = dto.getSubstitute();
			String workOnHolidayType = dto.getWorkOnHolidayType();
			// 休日出勤の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF) {
				// 法定休出の場合
				if (workOnHolidayType.equals(TimeConst.CODE_HOLIDAY_LEGAL_HOLIDAY)) {
					// 法定休日出勤を設定
					substitutedMap.put(requestDate, TimeConst.CODE_WORK_ON_LEGAL_HOLIDAY);
				}
				// 所定休出の場合
				if (workOnHolidayType.equals(TimeConst.CODE_HOLIDAY_PRESCRIBED_HOLIDAY)) {
					// 所定休日出勤を設定
					substitutedMap.put(requestDate, TimeConst.CODE_WORK_ON_PRESCRIBED_HOLIDAY);
				}
				continue;
			}
			// 振替出勤(勤務形態変更)の場合
			if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
				// 振替勤務形態コードを設定
				substitutedMap.put(requestDate, dto.getWorkTypeCode());
				continue;
			}
			// 振り替えられたカレンダ日情報を取得(振替出勤(全休か午前か午後)の場合)
			ScheduleDateDtoInterface scheduleDateDto = substitutedSchedules.get(requestDate);
			// カレンダ日情報が存在しない場合
			if (MospUtility.isEmpty(scheduleDateDto)) {
				// 処理無し
				continue;
			}
			// 振替日の予定勤務形態を設定
			substitutedMap.put(requestDate, scheduleDateDto.getWorkTypeCode());
		}
		// 振替勤務形態コード群を取得
		return substitutedMap;
	}
	
	/**
	 * 勤務形態エンティティ群を取得する。<br>
	 * <br>
	 * 勤怠集計エンティティに設定されてる次の勤務形態につき、
	 * 情報を取得する。<br>
	 * ・カレンダ日情報群<br>
	 * ・振替勤務形態コード群<br>
	 * ・振出・休出申請(勤務形態変更)<br>
	 * ・勤務形態変更申請<br>
	 * <br>
	 * @param entity 勤怠集計エンティティ
	 * @return 振替勤務形態コード群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, List<WorkTypeEntityInterface>> getWorkTypeEntityMap(TotalTimeEntityInterface entity)
			throws MospException {
		// 勤務形態エンティティ群を準備
		Map<String, List<WorkTypeEntityInterface>> map = new HashMap<String, List<WorkTypeEntityInterface>>();
		// カレンダ日情報群の勤務形態コード毎に処理
		for (String workTypeCode : entity.getScheduleMap().values()) {
			// 勤務形態エンティティ履歴を取得し設定
			map.put(workTypeCode, timeMaster.getWorkTypeEntityHistory(workTypeCode));
		}
		// 振替勤務形態コード群
		for (String workTypeCode : entity.getSubstitutedMap().values()) {
			// 勤務形態エンティティ履歴を取得し設定
			map.put(workTypeCode, timeMaster.getWorkTypeEntityHistory(workTypeCode));
		}
		// 振出・休出申請申請
		for (WorkOnHolidayRequestDtoInterface dto : entity.getWorkOnHolidayRequestList()) {
			// 振替出勤(勤務形態変更)でない場合
			if (dto.getSubstitute() != TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
				// 処理無し
				continue;
			}
			// 勤務形態コードを取得
			String workTypeCode = dto.getWorkTypeCode();
			// 勤務形態エンティティ履歴を取得し設定
			map.put(workTypeCode, timeMaster.getWorkTypeEntityHistory(workTypeCode));
		}
		// 勤務形態変更申請
		for (WorkTypeChangeRequestDtoInterface dto : entity.getWorkTypeChangeRequestList()) {
			// 勤務形態コードを取得
			String workTypeCode = dto.getWorkTypeCode();
			// 勤務形態エンティティ履歴を取得し設定
			map.put(workTypeCode, timeMaster.getWorkTypeEntityHistory(workTypeCode));
		}
		// 勤務形態エンティティ群を取得
		return map;
	}
	
	@Override
	public void setTimeMasterBean(TimeMasterBeanInterface timeMaster) {
		this.timeMaster = timeMaster;
		scheduleUtil.setTimeMaster(timeMaster);
	}
	
	@Override
	public void setPlatformMasterBean(PlatformMasterBeanInterface platformMaster) {
		this.platformMaster = platformMaster;
	}
	
}
