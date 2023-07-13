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
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.TransStringUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.bean.ScheduleUtilBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.comparator.report.AppliReasonDataComparator;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.DifferenceRequestDaoInterface;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeChangeRequestDaoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeRequestUtility;
import jp.mosp.time.utils.TimeUtility;

/**
 * 各種申請理由データエクスポート処理。<br>
 */
public class AppliReasonDataExportBean extends TimeBaseExportBean {
	
	/**
	 * カレンダーユーティリティクラス。<br>
	 */
	protected ScheduleUtilBeanInterface					scheduleUtil;
	
	/**
	 * 残業申請DAOクラス。<br>
	 */
	protected OvertimeRequestDaoInterface				overtimeReqDao;
	
	/**
	 * 休暇申請DAOクラス。<br>
	 */
	protected HolidayRequestDaoInterface				holidayReqDao;
	
	/**
	 * 休日出勤申請DAOクラス。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface			workOnHolidayReqDao;
	
	/**
	 * 振替休日申請DAOクラス。<br>
	 */
	protected SubstituteDaoInterface					substituteDao;
	
	/**
	 * 勤務形態変更申請DAOクラス。<br>
	 */
	protected WorkTypeChangeRequestDaoInterface			workTypeChangeReqDao;
	
	/**
	 * 勤務形態情報参照クラス。<br>
	 */
	WorkTypeReferenceBeanInterface						workTypeRefer;
	
	/**
	 * 時差出勤申請データDAOクラス。<br>
	 */
	protected DifferenceRequestDaoInterface				differenceReqDao;
	
	/**
	 * ワークフロー参照処理。<br>
	 */
	protected WorkflowReferenceBeanInterface			workflowRefer;
	
	/**
	 * 残業申請(承認済)リスト。<br>
	 */
	protected List<OvertimeRequestDtoInterface>			overtimeRequestList;
	
	/**
	 * 休暇申請(承認済)リスト。<br>
	 */
	protected List<HolidayRequestDtoInterface>			holidayRequestList;
	
	/**
	 * 振出・休出申請(承認済)リスト。<br>
	 */
	protected List<WorkOnHolidayRequestDtoInterface>	workOnHolidayList;
	
	/**
	 * 時差出勤申請(承認済)リスト。<br>
	 */
	protected List<DifferenceRequestDtoInterface>		differenceRequestList;
	
	/**
	 * 勤務形態変更申請(承認済)リスト。<br>
	 */
	protected List<WorkTypeChangeRequestDtoInterface>	workTypeChangeRequestList;
	
	
	/**
	 * {@link TimeBaseExportBean#TimeBaseExportBean()}を実行する。<br>
	 */
	public AppliReasonDataExportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// DAO及びBeanを準備
		substituteDao = createDaoInstance(SubstituteDaoInterface.class);
		overtimeReqDao = createDaoInstance(OvertimeRequestDaoInterface.class);
		holidayReqDao = createDaoInstance(HolidayRequestDaoInterface.class);
		workOnHolidayReqDao = createDaoInstance(WorkOnHolidayRequestDaoInterface.class);
		workTypeChangeReqDao = createDaoInstance(WorkTypeChangeRequestDaoInterface.class);
		differenceReqDao = createDaoInstance(DifferenceRequestDaoInterface.class);
		workTypeRefer = createBeanInstance(WorkTypeReferenceBeanInterface.class);
		workflowRefer = createBeanInstance(WorkflowReferenceBeanInterface.class);
		scheduleUtil = createBeanInstance(ScheduleUtilBeanInterface.class);
		scheduleUtil.setTimeMaster(timeMaster);
	}
	
	@Override
	protected List<String[]> makeCsvDataList(List<String> fieldList, Date firstDate, Date lastDate, String cutoffCode,
			String workPlaceCode, String employmentContractCode, String sectionCode, boolean needLowerSection,
			String positionCode) throws MospException {
		// CSVデータリストを準備
		List<String[]> csvDataList = new ArrayList<String[]>();
		// 人事情報群を取得
		List<HumanDtoInterface> humanList = getHumanList(firstDate, lastDate, cutoffCode, workPlaceCode,
				employmentContractCode, sectionCode, needLowerSection, positionCode);
		// 社員毎に処理
		for (HumanDtoInterface humanDto : humanList) {
			// 各種申請リスト(承認済)取得
			getAppliedRequestList(humanDto.getPersonalId(), firstDate, lastDate);
			// 残業申請理由をCSVデータリストに設定
			addOverTimeRequestData(humanDto, csvDataList, fieldList, overtimeRequestList, lastDate);
			// 休暇申請理由をCSVデータリストに設定
			addHolidayRequestData(humanDto, csvDataList, fieldList, holidayRequestList, firstDate, lastDate);
			// 休日出勤申請理由をCSVデータリストに設定
			addWorkOnHolidayRequestData(humanDto, csvDataList, fieldList, workOnHolidayList, lastDate);
			// 時差出勤申請理由をCSVデータリストに設定
			addDifferenceRequestData(humanDto, csvDataList, fieldList, differenceRequestList, lastDate);
			// 勤務形態変更申請理由をCSVデータリストに設定
			addWorkTypeChangeRequestData(humanDto, csvDataList, fieldList, workTypeChangeRequestList, lastDate);
		}
		// ソート
		Collections.sort(csvDataList, new AppliReasonDataComparator());
		// CSVデータリストを取得
		return csvDataList;
	}
	
	/**
	 * 対象期間の承認済み申請リストを取得する。<br>
	 * 残業申請、休暇申請、休日出勤申請、時差出勤申請、勤務形態変更申請。<br>
	 * @param personalId 個人ID
	 * @param firstDate  出力期間初日
	 * @param lastDate   出力期間最終日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void getAppliedRequestList(String personalId, Date firstDate, Date lastDate) throws MospException {
		// 残業申請リスト取得
		List<OvertimeRequestDtoInterface> overtimes = overtimeReqDao.findForList(personalId, firstDate, lastDate);
		// 休暇申請リスト取得
		List<HolidayRequestDtoInterface> holidays = holidayReqDao.findForTerm(personalId, firstDate, lastDate);
		// 休日出勤申請リスト取得
		List<WorkOnHolidayRequestDtoInterface> works = workOnHolidayReqDao.findForList(personalId, firstDate, lastDate);
		// 時差出勤申請リスト取得
		List<DifferenceRequestDtoInterface> differences = differenceReqDao.findForList(personalId, firstDate, lastDate);
		// 勤務形態変更申請リスト取得
		List<WorkTypeChangeRequestDtoInterface> workTypes = workTypeChangeReqDao.findForTerm(personalId, firstDate,
				lastDate);
		// ワークフロー番号群を取得
		Set<Long> workflowSet = WorkflowUtility.getWorkflowSet(overtimes, holidays, works, differences, workTypes);
		// ワークフロー情報群(キー：ワークフロー番号)を取得
		Map<Long, WorkflowDtoInterface> workflows = workflowRefer.getWorkflows(workflowSet);
		// 承認済承認状況群を取得
		Set<String> statuses = WorkflowUtility.getCompletedStatuses();
		// DTO群から対象となる承認状況であるDTO群を取得
		overtimeRequestList = WorkflowUtility.getStatusMatchedList(overtimes, workflows, statuses);
		holidayRequestList = WorkflowUtility.getStatusMatchedList(holidays, workflows, statuses);
		workOnHolidayList = WorkflowUtility.getStatusMatchedList(works, workflows, statuses);
		differenceRequestList = WorkflowUtility.getStatusMatchedList(differences, workflows, statuses);
		workTypeChangeRequestList = WorkflowUtility.getStatusMatchedList(workTypes, workflows, statuses);
	}
	
	/**
	 * 残業申請理由をCSVデータリストに設定する。<br>
	 * @param humanDto    人事情報
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 * @param dtos        残業申請群
	 * @param targetDate  対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addOverTimeRequestData(HumanDtoInterface humanDto, List<String[]> csvDataList,
			List<String> fieldList, Collection<OvertimeRequestDtoInterface> dtos, Date targetDate)
			throws MospException {
		// 残業申請情報毎に処理
		for (OvertimeRequestDtoInterface dto : dtos) {
			// 残業申請理由をCSVデータリストに設定
			csvDataList.add(getOverTimeRequestData(humanDto, fieldList, dto, targetDate));
		}
	}
	
	/**
	 * 残業申請理由CSVデータを取得する。<br>
	 * @param humanDto   人事情報
	 * @param fieldList  エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param dto        残業申請情報
	 * @param targetDate 対象日
	 * @return 残業申請理由CSVデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getOverTimeRequestData(HumanDtoInterface humanDto, List<String> fieldList,
			OvertimeRequestDtoInterface dto, Date targetDate) throws MospException {
		// 残業申請理由CSVデータを準備
		String[] csvData = getHumanCsvData(fieldList, humanDto, targetDate);
		// 勤務日と申請区分を取得
		String workDate = DateUtility.getStringDateAndDay(dto.getRequestDate());
		String appliType = TimeNamingUtility.getOvertimeWork(mospParams);
		// 詳細1(残業区分)を取得
		String detail1 = getCodeName(dto.getOvertimeType(), TimeConst.CODE_OVER_TIME_TYPE);
		// 詳細2(申請時間)を取得
		String detail2 = TimeUtility.getStringJpTime(mospParams, dto.getRequestTime());
		// 申請理由を取得
		String appliReason = dto.getRequestReason();
		// CSVデータに値を設定
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_WORK_DATE, workDate);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_TYPE, appliType);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_1, detail1);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_2, detail2);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_REASON, appliReason);
		// 残業申請理由CSVデータを取得
		return csvData;
	}
	
	/**
	 * 休暇申請理由をCSVデータリストに付加する。<br>
	 * @param humanDto    人事情報
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 * @param dtos        休暇申請群
	 * @param firstDate   出力期間初日
	 * @param lastDate    出力期間最終日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addHolidayRequestData(HumanDtoInterface humanDto, List<String[]> csvDataList, List<String> fieldList,
			Collection<HolidayRequestDtoInterface> dtos, Date firstDate, Date lastDate) throws MospException {
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : dtos) {
			// 申請日リストを取得
			List<Date> dateList = TimeUtility.getDateList(dto.getRequestStartDate(), dto.getRequestEndDate());
			// 申請日毎に処理
			for (Date requestDate : dateList) {
				// 個人IDを取得
				String personalId = humanDto.getPersonalId();
				// 申請日における予定勤務形態コードを取得
				String workTypeCode = scheduleUtil.getScheduledWorkTypeCode(personalId, requestDate, true);
				// 休日か休日出勤である場合
				if (TimeRequestUtility.isNotHolidayForConsecutiveHolidays(workTypeCode)) {
					// 次の申請日へ
					continue;
				}
				// 期間開始日より前の場合
				if (requestDate.compareTo(firstDate) < 0) {
					// 次の申請日へ
					continue;
				}
				// 期間終了日より後の場合
				if (requestDate.compareTo(lastDate) > 0) {
					// 次の申請日へ
					continue;
				}
				// 休暇申請理由CSVデータをCSVデータリストに設定
				csvDataList.add(getHolidayRequestData(humanDto, fieldList, dto, requestDate, lastDate));
			}
		}
	}
	
	/**
	 * 休暇申請理由CSVデータを取得する。<br>
	 * @param humanDto    人事情報
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param dto         休暇申請情報
	 * @param requestDate 申請日
	 * @param targetDate  対象日(所属名称や休暇略称の取得に用いる)
	 * @return 休暇申請理由CSVデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getHolidayRequestData(HumanDtoInterface humanDto, List<String> fieldList,
			HolidayRequestDtoInterface dto, Date requestDate, Date targetDate) throws MospException {
		// 休暇申請理由CSVデータを準備
		String[] csvData = getHumanCsvData(fieldList, humanDto, targetDate);
		// 勤務日と申請区分を取得
		String workDate = DateUtility.getStringDateAndDay(requestDate);
		String appliType = TimeNamingUtility.getVacation(mospParams);
		// 詳細1(休暇申請区分)を取得
		String detail1 = getCodeName(dto.getHolidayType1(), TimeConst.CODE_HOLIDAY_TYPE);
		// 詳細2(休暇略称)を取得
		String detail2 = getCodeName(dto.getHolidayType2(), TimeConst.CODE_HOLIDAY_TYPE2_WITHPAY);
		// 休暇申請情報が特別休暇かその他休暇か欠勤である場合
		if (TimeRequestUtility.isSpecialHoliday(dto) || TimeRequestUtility.isOtherHoliday(dto)
				|| TimeRequestUtility.isAbsenece(dto)) {
			// 詳細2(休暇略称)を再取得
			detail2 = timeMaster.getHolidayAbbr(dto.getHolidayType2(), dto.getHolidayType1(), targetDate);
		}
		// 詳細3(休暇範囲)を取得
		String detail3 = getCodeName(dto.getHolidayRange(), TimeConst.CODE_HOLIDAY_TYPE3_RANGE1);
		// 詳細4(時間単位休暇)を取得
		String detail4 = MospConst.STR_EMPTY;
		// 時間単位休暇である場合
		if (TimeRequestUtility.isHolidayRangeHour(dto)) {
			// 詳細4(時間単位休暇)を再取得
			detail4 = getTimeWaveFormat(dto.getStartTime(), dto.getEndTime(), dto.getRequestStartDate());
		}
		// 申請理由を取得
		String appliReason = dto.getRequestReason();
		// CSVデータに値を設定
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_WORK_DATE, workDate);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_TYPE, appliType);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_1, detail1);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_2, detail2);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_3, detail3);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_4, detail4);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_REASON, appliReason);
		// 休暇申請理由CSVデータを取得
		return csvData;
	}
	
	/**
	 * 振出・休出申請理由をCSVデータリストに設定する。<br>
	 * @param humanDto    人事情報
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 * @param dtos        振出・休出申請群
	 * @param targetDate  対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addWorkOnHolidayRequestData(HumanDtoInterface humanDto, List<String[]> csvDataList,
			List<String> fieldList, Collection<WorkOnHolidayRequestDtoInterface> dtos, Date targetDate)
			throws MospException {
		// 振出・休出申請データ毎に処理
		for (WorkOnHolidayRequestDtoInterface dto : dtos) {
			// 振出・休出申請理由をCSVデータリストに設定
			csvDataList.add(getWorkOnHolidayRequestData(humanDto, fieldList, dto, targetDate));
		}
	}
	
	/**
	 * 振出・休出申請理由CSVデータを取得する。<br>
	 * @param humanDto    人事情報
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param dto         振出・休出申請情報
	 * @param targetDate  対象日(所属名称の取得に用いる)
	 * @return 休暇申請理由CSVデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getWorkOnHolidayRequestData(HumanDtoInterface humanDto, List<String> fieldList,
			WorkOnHolidayRequestDtoInterface dto, Date targetDate) throws MospException {
		// 振出・休出申請理由CSVデータを準備
		String[] csvData = getHumanCsvData(fieldList, humanDto, targetDate);
		// 勤務日と申請区分を取得
		String workDate = DateUtility.getStringDateAndDay(dto.getRequestDate());
		String appliType = TimeNamingUtility.workOnHoliday(mospParams);
		// 詳細1(振替申請区分)を取得
		String detail1 = getCodeName(dto.getSubstitute(), TimeConst.CODE_WORKONHOLIDAY_SUBSTITUTE);
		// 振出・休出申請が前半日振替出勤である場合
		if (TimeRequestUtility.isWorkOnHolidayAnteSubstitute(dto)) {
			// 詳細1(振替申請区分)を再設定
			detail1 = TimeNamingUtility.anteMeridiem(mospParams);
		}
		// 振出・休出申請が後半日振替出勤である場合
		if (TimeRequestUtility.isWorkOnHolidayPostSubstitute(dto)) {
			// 詳細1(振替申請区分)を再設定
			detail1 = TimeNamingUtility.postMeridiem(mospParams);
		}
		// 振替休日情報リストを取得
		List<SubstituteDtoInterface> substitutes = substituteDao.findForWorkflow(dto.getWorkflow());
		// 詳細2(振替日か休出予定時刻)を取得
		String detail2 = getWorkOnHolidayRequestDetail2(dto, substitutes);
		// 詳細3(振替日の範囲)を取得
		String detail3 = getWorkOnHolidayRequestDetail3(substitutes);
		// 詳細4(勤務形態変更ありの場合の勤務形態コード)を取得
		String detail4 = dto.getWorkTypeCode();
		// 申請理由を取得
		String appliReason = dto.getRequestReason();
		// CSVデータに値を設定
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_WORK_DATE, workDate);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_TYPE, appliType);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_1, detail1);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_2, detail2);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_3, detail3);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_4, detail4);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_REASON, appliReason);
		// 振出・休出申請理由CSVデータを取得
		return csvData;
	}
	
	/**
	 * 振替日か休出予定時刻の文字列を取得する。<br>
	 * @param dto         振出・休出申請情報
	 * @param substitutes 振替休日情報リスト
	 * @return 振替日か休出予定時刻の文字列
	 */
	protected String getWorkOnHolidayRequestDetail2(WorkOnHolidayRequestDtoInterface dto,
			List<SubstituteDtoInterface> substitutes) {
		// 振出・休出申請が休日出勤(振替申請しない)である場合
		if (TimeRequestUtility.isWorkOnHolidaySubstituteOff(dto)) {
			// 休出予定時刻の文字列を取得
			return getTimeWaveFormat(dto.getStartTime(), dto.getEndTime(), dto.getRequestDate());
		}
		// 振替日文字列を準備
		List<String> substituteDate = new ArrayList<String>();
		// 振替休日情報毎に処理
		for (SubstituteDtoInterface substitute : substitutes) {
			// 振替休日範囲文字列を設定
			substituteDate.add(DateUtility.getStringDateAndDay(substitute.getSubstituteDate()));
		}
		// 振替休日範囲文字列を取得
		return MospUtility.concat(MospUtility.toArray(substituteDate));
	}
	
	/**
	 * 振替休日範囲文字列を取得する。<br>
	 * @param substitutes 振替休日情報リスト
	 * @return 振替休日範囲文字列
	 */
	protected String getWorkOnHolidayRequestDetail3(List<SubstituteDtoInterface> substitutes) {
		// 振替休日範囲文字列を準備
		List<String> substituteRange = new ArrayList<String>();
		// 振替休日情報毎に処理
		for (SubstituteDtoInterface substitute : substitutes) {
			// 振替休日範囲文字列を設定
			substituteRange.add(TimeRequestUtility.getSubstituteRangeName(substitute, mospParams));
		}
		// 振替休日範囲文字列を取得
		return MospUtility.concat(MospUtility.toArray(substituteRange));
	}
	
	/**
	 * 時差出勤申請理由をCSVデータリストに設定する。<br>
	 * @param humanDto    人事情報
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 * @param dtos        時差出勤申請群
	 * @param targetDate  対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addDifferenceRequestData(HumanDtoInterface humanDto, List<String[]> csvDataList,
			List<String> fieldList, Collection<DifferenceRequestDtoInterface> dtos, Date targetDate)
			throws MospException {
		// 時差出勤申請データ毎に処理
		for (DifferenceRequestDtoInterface dto : dtos) {
			// 時差出勤申請理由をCSVデータリストに設定
			csvDataList.add(getDifferenceRequestData(humanDto, fieldList, dto, targetDate));
		}
	}
	
	/**
	 * 時差出勤申請理由CSVデータを取得する。<br>
	 * @param humanDto    人事情報
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param dto         時差出勤申請情報
	 * @param targetDate  対象日(所属名称の取得に用いる)
	 * @return 時差出勤申請理由CSVデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getDifferenceRequestData(HumanDtoInterface humanDto, List<String> fieldList,
			DifferenceRequestDtoInterface dto, Date targetDate) throws MospException {
		// 時差出勤申請理由CSVデータを準備
		String[] csvData = getHumanCsvData(fieldList, humanDto, targetDate);
		// 勤務日と申請区分を取得
		String workDate = DateUtility.getStringDateAndDay(dto.getRequestDate());
		String appliType = TimeNamingUtility.getTimeDifference(mospParams);
		// 詳細1(時差出勤区分)を取得
		String detail1 = TimeRequestUtility.getDifferenceTypeAbbr(dto, mospParams);
		// 詳細2(時差出勤時刻)を取得
		String detail2 = getTimeWaveFormat(dto.getRequestStart(), dto.getRequestEnd(), dto.getRequestDate());
		// 申請理由を取得
		String appliReason = dto.getRequestReason();
		// CSVデータに値を設定
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_WORK_DATE, workDate);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_TYPE, appliType);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_1, detail1);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_2, detail2);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_REASON, appliReason);
		// 時差出勤申請理由CSVデータを取得
		return csvData;
	}
	
	/**
	 * 勤務形態変更申請理由をCSVデータリストに設定する。<br>
	 * @param humanDto    人事情報
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド情報リスト
	 * @param dtos        勤務形態変更申請群
	 * @param targetDate  対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addWorkTypeChangeRequestData(HumanDtoInterface humanDto, List<String[]> csvDataList,
			List<String> fieldList, Collection<WorkTypeChangeRequestDtoInterface> dtos, Date targetDate)
			throws MospException {
		// 勤務形態変更申請データ毎に処理
		for (WorkTypeChangeRequestDtoInterface dto : dtos) {
			// 勤務形態変更申請理由をCSVデータリストに設定
			csvDataList.add(getWorkTypeChangeRequestData(humanDto, fieldList, dto, targetDate));
		}
	}
	
	/**
	 * 勤務形態変更申請理由CSVデータを取得する。<br>
	 * @param humanDto    人事情報
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param dto         勤務形態変更申請情報
	 * @param targetDate  対象日(所属名称の取得に用いる)
	 * @return 勤務形態変更申請理由CSVデータ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String[] getWorkTypeChangeRequestData(HumanDtoInterface humanDto, List<String> fieldList,
			WorkTypeChangeRequestDtoInterface dto, Date targetDate) throws MospException {
		// 勤務形態変更申請理由CSVデータを準備
		String[] csvData = getHumanCsvData(fieldList, humanDto, targetDate);
		// 勤務日と申請区分を取得
		String workDate = DateUtility.getStringDateAndDay(dto.getRequestDate());
		String appliType = TimeNamingUtility.workTypeChangeShort(mospParams);
		// 詳細1(勤務形態略称)を取得
		String detail1 = workTypeRefer.getWorkTypeAbbrAndTime(dto.getWorkTypeCode(), dto.getRequestDate());
		// 詳細2(勤務形態コード)を取得
		String detail2 = dto.getWorkTypeCode();
		// 申請理由を取得
		String appliReason = dto.getRequestReason();
		// CSVデータに値を設定
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_WORK_DATE, workDate);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_TYPE, appliType);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_1, detail1);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_DETAIL_2, detail2);
		setCsvValue(csvData, fieldList, TimeFileConst.FIELD_APPLI_REASON, appliReason);
		// 勤務形態変更申請理由CSVデータを取得
		return csvData;
	}
	
	/**
	 * 時分形式で出力する。
	 * @param date1 前時間
	 * @param date2 後時間
	 * @param standardDate 基準日付
	 * @return hh:mm～hh:mm
	 */
	protected String getTimeWaveFormat(Date date1, Date date2, Date standardDate) {
		// 日付(期間)を文字列(HH:mm～HH:mm)に変換
		return TransStringUtility.getHourColonMinuteTerm(mospParams, date1, date2, standardDate);
	}
	
}
