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

import java.io.InputStream;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.platform.bean.file.ImportBeanInterface;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendanceTransactionRegistBeanInterface;
import jp.mosp.time.bean.SubstituteRegistBeanInterface;
import jp.mosp.time.bean.TimeMasterBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestRegistBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.constant.TimeFileConst;
import jp.mosp.time.dao.settings.impl.TmdSubstituteDao;
import jp.mosp.time.dao.settings.impl.TmdWorkOnHolidayRequestDao;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.entity.ApplicationEntity;
import jp.mosp.time.utils.TimeNamingUtility;

/**
 * 振出・休出申請インポート処理。<br>
 */
public class WorkOnHolidayRequestImportBean extends PlatformFileBean implements ImportBeanInterface {
	
	/**
	 * 振出・休出申請登録クラス。<br>
	 */
	protected WorkOnHolidayRequestRegistBeanInterface	workOnHolidayRequestRegist;
	
	/**
	 * 振替休日データ登録クラス。<br>
	 */
	protected SubstituteRegistBeanInterface				substituteRegist;
	
	/**
	 * 勤怠トランザクション登録クラス。<br>
	 */
	protected AttendanceTransactionRegistBeanInterface	attendanceTransactionRegist;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface			workflowIntegrate;
	
	/**
	 * ワークフロー登録クラス。<br>
	 */
	protected WorkflowRegistBeanInterface				workflowRegist;
	
	/**
	 * 勤怠関連マスタ参照クラス。<br>
	 */
	protected TimeMasterBeanInterface					timeMaster;
	
	
	/**
	 * {@link PlatformFileBean#PlatformFileBean()}を実行する。<br>
	 */
	public WorkOnHolidayRequestImportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		workOnHolidayRequestRegist = createBeanInstance(WorkOnHolidayRequestRegistBeanInterface.class);
		substituteRegist = createBeanInstance(SubstituteRegistBeanInterface.class);
		attendanceTransactionRegist = createBeanInstance(AttendanceTransactionRegistBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		workflowRegist = createBeanInstance(WorkflowRegistBeanInterface.class);
		timeMaster = createBeanInstance(TimeMasterBeanInterface.class);
		// 勤怠関連マスタ参照処理をBeanに設定
		workOnHolidayRequestRegist.setTimeMaster(timeMaster);
	}
	
	@Override
	public int importFile(ImportDtoInterface importDto, InputStream requestedFile) throws MospException {
		// アップロードファイルを登録情報リストに変換
		List<String[]> dataList = getDataList(importDto, requestedFile);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// インポートフィールド情報リストを取得
		List<ImportFieldDtoInterface> fieldList = getImportFieldList(importDto.getImportCode());
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 社員コードを個人IDに変換
		convertEmployeeCodeIntoPersonalId(fieldList, dataList, TmdWorkOnHolidayRequestDao.COL_REQUEST_DATE,
				getWorkOnHolidayName());
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// インポート処理
		if (TimeFileConst.CODE_IMPORT_TYPE_TMD_WORK_ON_HOLIDAY_REQUEST_SUBSTITUTE_OFF
			.equals(importDto.getImportTable())) {
			return importFileSubstituteOff(fieldList, dataList);
		} else {
			return importFile(fieldList, dataList);
		}
	}
	
	/**
	 * インポート処理を行う。<br>
	 * 登録情報リストを、インポートフィールド情報リストに基づき
	 * 振出・休出情報、振替休日情報、ワークフロー情報に変換し、登録を行う。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param dataList 登録情報リスト
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int importFile(List<ImportFieldDtoInterface> fieldList, List<String[]> dataList) throws MospException {
		// データ毎に処理
		for (String[] data : dataList) {
			// インポート
			importData(fieldList, data);
		}
		return dataList.size();
	}
	
	/**
	 * インポート処理を行う。<br>
	 * 登録情報を、インポートフィールド情報リストに基づき
	 * 振出・休出申請情報、振替休日情報、ワークフロー情報に変換し、登録を行う。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void importData(List<ImportFieldDtoInterface> fieldList, String[] data) throws MospException {
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestDto(fieldList, data);
		SubstituteDtoInterface substituteDto = getSubstituteDto(fieldList, data);
		// 出勤日の入力チェック
		workOnHolidayRequestRegist.checkValidate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 振替休日の相関チェック
		substituteRegist.checkImport(substituteDto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 申請の相関チェック
		workOnHolidayRequestRegist.checkAppli(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_WORK_HOLIDAY);
		}
		// 自己承認設定
		workflowRegist.setSelfApproval(workflowDto);
		// 登録後ワークフローの取得
		workflowDto = workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
				PlatformConst.WORKFLOW_TYPE_TIME, null);
		if (workflowDto != null) {
			long workflow = workflowDto.getWorkflow();
			// ワークフロー番号セット
			dto.setWorkflow(workflow);
			substituteDto.setWorkflow(workflow);
			// 振出・休出申請
			workOnHolidayRequestRegist.regist(dto);
			// 振替休日データ登録
			substituteRegist.insert(substituteDto);
			// 勤怠トランザクション登録
			attendanceTransactionRegist.regist(substituteDto);
		}
	}
	
	/**
	 * インポートフィールド情報リストに従い、
	 * 登録情報の内容を振出・休出申請情報(DTO)のフィールドに設定する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @return 振出・休出申請情報(DTO)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected WorkOnHolidayRequestDtoInterface getWorkOnHolidayRequestDto(List<ImportFieldDtoInterface> fieldList,
			String[] data) throws MospException {
		// 休日出勤申請DTO準備
		WorkOnHolidayRequestDtoInterface dto = workOnHolidayRequestRegist.getInitDto();
		// 個人ID・出勤日取得
		String personalId = getFieldValue(TmdWorkOnHolidayRequestDao.COL_PERSONAL_ID, fieldList, data);
		Date requestDate = getDateFieldValue(TmdWorkOnHolidayRequestDao.COL_REQUEST_DATE, fieldList, data);
		// 振替申請取得
		int substitute = TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON;
		// 勤務形態コード取得
		String workTypeCode = getFieldValue(TmdWorkOnHolidayRequestDao.COL_WORK_TYPE_CODE, fieldList, data);
		// 勤務形態コードがある場合
		if (!workTypeCode.isEmpty()) {
			// 振替申請設定
			substitute = TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE;
		}
		// 登録情報の内容を取得し設定
		dto.setPersonalId(personalId);
		dto.setRequestDate(requestDate);
		dto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
		dto.setWorkOnHolidayType(workOnHolidayRequestRegist.getScheduledWorkTypeCode(personalId, requestDate));
		dto.setWorkTypeCode(workTypeCode);
		dto.setSubstitute(substitute);
		dto.setStartTime(null);
		dto.setEndTime(null);
		dto.setRequestReason(getFieldValue(TmdWorkOnHolidayRequestDao.COL_REQUEST_REASON, fieldList, data));
		return dto;
	}
	
	/**
	 * インポートフィールド情報リストに従い、
	 * 登録情報の内容を振替休日情報(DTO)のフィールドに設定する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @return 振替休日情報(DTO)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected SubstituteDtoInterface getSubstituteDto(List<ImportFieldDtoInterface> fieldList, String[] data)
			throws MospException {
		String personalId = getFieldValue(TmdWorkOnHolidayRequestDao.COL_PERSONAL_ID, fieldList, data);
		Date workDate = getDateFieldValue(TmdWorkOnHolidayRequestDao.COL_REQUEST_DATE, fieldList, data);
		SubstituteDtoInterface dto = substituteRegist.getInitDto();
		// 登録情報の内容を取得し設定
		dto.setPersonalId(personalId);
		dto.setSubstituteDate(getDateFieldValue(TmdSubstituteDao.COL_SUBSTITUTE_DATE, fieldList, data));
		dto.setSubstituteType(workOnHolidayRequestRegist.getScheduledWorkTypeCode(personalId, workDate));
		dto.setSubstituteRange(1);
		dto.setWorkDate(workDate);
		dto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
		return dto;
	}
	
	/**
	 * インポート処理を行う。<br>
	 * 登録情報リストを、インポートフィールド情報リストに基づき
	 * 振出・休出情報、振替休日情報、ワークフロー情報に変換し、登録を行う。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param dataList 登録情報リスト
	 * @return 登録件数
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected int importFileSubstituteOff(List<ImportFieldDtoInterface> fieldList, List<String[]> dataList)
			throws MospException {
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		for (String[] data : dataList) {
			// インポート
			importDataSubstituteOff(fieldList, data);
		}
		return dataList.size();
	}
	
	/**
	 * インポート処理を行う。<br>
	 * 登録情報を、インポートフィールド情報リストに基づき
	 * 振出・休出申請情報、振替休日情報、ワークフロー情報に変換し、登録を行う。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void importDataSubstituteOff(List<ImportFieldDtoInterface> fieldList, String[] data)
			throws MospException {
		WorkOnHolidayRequestDtoInterface dto = getWorkOnHolidayRequestSubstituteOffDto(fieldList, data);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 申請の相関チェック
		workOnHolidayRequestRegist.checkAppli(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		WorkflowDtoInterface workflowDto = workflowIntegrate.getLatestWorkflowInfo(dto.getWorkflow());
		if (workflowDto == null) {
			workflowDto = workflowRegist.getInitDto();
			workflowDto.setFunctionCode(TimeConst.CODE_FUNCTION_WORK_HOLIDAY);
		}
		// 自己承認設定
		workflowRegist.setSelfApproval(workflowDto);
		// 登録後ワークフローの取得
		workflowDto = workflowRegist.appli(workflowDto, dto.getPersonalId(), dto.getRequestDate(),
				PlatformConst.WORKFLOW_TYPE_TIME, null);
		if (workflowDto != null) {
			long workflow = workflowDto.getWorkflow();
			// ワークフロー番号セット
			dto.setWorkflow(workflow);
			// 休出申請
			workOnHolidayRequestRegist.regist(dto);
		}
	}
	
	/**
	 * インポートフィールド情報リストに従い、
	 * 登録情報の内容を振出・休出申請情報(DTO)のフィールドに設定する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data 登録情報
	 * @return 振出・休出申請情報(DTO)
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected WorkOnHolidayRequestDtoInterface getWorkOnHolidayRequestSubstituteOffDto(
			List<ImportFieldDtoInterface> fieldList, String[] data) throws MospException {
		// 休日出勤申請DTO準備
		WorkOnHolidayRequestDtoInterface dto = workOnHolidayRequestRegist.getInitDto();
		// 個人ID・出勤日取得
		String personalId = getFieldValue(TmdWorkOnHolidayRequestDao.COL_PERSONAL_ID, fieldList, data);
		Date requestDate = getDateFieldValue(TmdWorkOnHolidayRequestDao.COL_REQUEST_DATE, fieldList, data);
		// 出勤予定時刻取得
		Date startDate = getDateTimeFieldValue(TmdWorkOnHolidayRequestDao.COL_START_TIME, fieldList, data);
		// 出勤予定時刻が取得できない場合エラーメッセージを追加
		if (startDate == null) {
			PfMessageUtility.addErrorCheckForm(mospParams, TimeNamingUtility.scheduledStartWork(mospParams));
			return dto;
			// 出勤予定時刻の日付が勤務日と異なる場合エラーメッセージ追加
		} else if (!DateUtility.getStringDate(requestDate).equals(DateUtility.getStringDate(startDate))) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, TimeNamingUtility.scheduledStartWork(mospParams));
			return dto;
		}
		// 設定適用エンティティを取得
		ApplicationEntity application = timeMaster.getApplicationEntity(personalId, requestDate);
		// 設定適用エンティティが有効でない場合
		if (application.isValid(mospParams) == false) {
			// 空の休日出勤申請情報を取得
			return dto;
		}
		// 起算時刻取得
		Date startDayTime = application.getTimeSettingDto().getStartDayTime();
		Date limitTime = DateUtility.addDay(requestDate, 1);
		limitTime = DateUtility.addHour(limitTime, DateUtility.getHour(startDayTime));
		limitTime = DateUtility.addMinute(limitTime, DateUtility.getMinute(startDayTime));
		// 退勤予定時刻取得
		Date endDate = getDateTimeFieldValue(TmdWorkOnHolidayRequestDao.COL_END_TIME, fieldList, data);
		// 退勤予定時刻が取得できない場合エラーメッセージ追加
		if (endDate == null) {
			PfMessageUtility.addErrorCheckForm(mospParams, TimeNamingUtility.scheduledEndWork(mospParams));
			return dto;
			// 退勤予定時刻が出勤予定時刻より前の場合、起算時刻をこえている場合
		} else if (DateUtility.getStringDateAndTime(startDate)
			.compareTo(DateUtility.getStringDateAndTime(endDate)) != -1
				|| DateUtility.getStringDateAndTime(endDate)
					.compareTo(DateUtility.getStringDateAndTime(limitTime)) != -1) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorRequired(mospParams, TimeNamingUtility.scheduledEndWork(mospParams));
			return dto;
		}
		// 登録情報の内容を取得し設定
		dto.setPersonalId(personalId);
		dto.setRequestDate(requestDate);
		dto.setTimesWork(TimeBean.TIMES_WORK_DEFAULT);
		dto.setWorkTypeCode("");
		dto.setWorkOnHolidayType(workOnHolidayRequestRegist.getScheduledWorkTypeCode(personalId, requestDate));
		dto.setSubstitute(TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_OFF);
		dto.setStartTime(startDate);
		dto.setEndTime(endDate);
		dto.setRequestReason(getFieldValue(TmdWorkOnHolidayRequestDao.COL_REQUEST_REASON, fieldList, data));
		return dto;
	}
	
	/**
	 * 登録情報からフィールド(日付)に対応する値を取得する。<br>
	 * 取得できなかった場合、nullを返す。<br>
	 * @param fieldName フィールド名
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @return 値(日付)
	 */
	protected Date getDateTimeFieldValue(String fieldName, List<ImportFieldDtoInterface> fieldList, String[] data) {
		// 登録情報から日付文字列を取得
		String date = getFieldValue(fieldName, fieldList, data);
		// 日付文字列確認
		if (date == null || date.isEmpty()) {
			return null;
		}
		// 日付取得
		return DateUtility.getDate(date, "yyyyMMdd HH:mm");
	}
	
	/**
	 * "出勤日"名称を取得する。<br>
	 * @return 出勤日
	 */
	protected String getWorkOnHolidayName() {
		return mospParams.getName("GoingWork", "Day");
	}
}
