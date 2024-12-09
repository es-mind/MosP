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
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowCommentReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.bean.DifferenceRequestReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.bean.RequestUtilBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.SubstituteReferenceBeanInterface;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.ManagementRequestListDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.RequestListDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.impl.ManagementRequestListDto;
import jp.mosp.time.utils.TimeUtility;

/**
 * 承認情報参照処理。<br>
 */
public class ApprovalInfoReferenceBean extends PlatformBean implements ApprovalInfoReferenceBeanInterface {
	
	/**
	 * 区切文字(申請情報詳細)。
	 */
	public static final String								SEPARATOR_REQUEST_INFO	= " ";
	
	/**
	 * 休暇種別管理DAO
	 */
	protected HolidayDaoInterface							holidayDao;
	
	/**
	 * 人事マスタ参照
	 */
	protected HumanReferenceBeanInterface					humanReference;
	
	/**
	 * 勤務形態マスタ参照
	 */
	protected WorkTypeReferenceBeanInterface				workTypeReference;
	
	/**
	 * 勤怠データ参照
	 */
	protected AttendanceReferenceBeanInterface				attendanceReference;
	
	/**
	 * 残業申請参照
	 */
	protected OvertimeRequestReferenceBeanInterface			overtimeRequest;
	
	/**
	 * 休暇申請参照
	 */
	protected HolidayRequestReferenceBeanInterface			holidayRequest;
	
	/**
	 * 休日出勤申請参照
	 */
	protected WorkOnHolidayRequestReferenceBeanInterface	workOnHolidayRequest;
	
	/**
	 * 代休申請参照
	 */
	protected SubHolidayRequestReferenceBeanInterface		subHolidayRequest;
	
	/**
	 * 勤務形態変更申請参照
	 */
	protected WorkTypeChangeRequestReferenceBeanInterface	workTypeChangeRequest;
	
	/**
	 * 時差出勤申請参照
	 */
	protected DifferenceRequestReferenceBeanInterface		differenceRequest;
	
	/**
	 * ワークフローコメント参照
	 */
	protected WorkflowCommentReferenceBeanInterface			workflowCommentReference;
	
	/**
	 * ワークフロー統括
	 */
	protected WorkflowIntegrateBeanInterface				workflowIntegrate;
	
	/**
	 * 振替休日データ参照
	 */
	protected SubstituteReferenceBeanInterface				substituteReference;
	
	/**
	 * 申請ユーティリティクラス
	 */
	protected RequestUtilBeanInterface						requestUtil;
	
	/**
	 * MosPアプリケーション設定キー(年月指定時の基準日)。<br>
	 */
	public static final String								APP_ATTENDANCE_INFO		= "AttendanceInfo";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ApprovalInfoReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		holidayDao = createDaoInstance(HolidayDaoInterface.class);
		// Beanを準備
		humanReference = createBeanInstance(HumanReferenceBeanInterface.class);
		workTypeReference = createBeanInstance(WorkTypeReferenceBeanInterface.class);
		attendanceReference = createBeanInstance(AttendanceReferenceBeanInterface.class);
		overtimeRequest = createBeanInstance(OvertimeRequestReferenceBeanInterface.class);
		holidayRequest = createBeanInstance(HolidayRequestReferenceBeanInterface.class);
		workOnHolidayRequest = createBeanInstance(WorkOnHolidayRequestReferenceBeanInterface.class);
		subHolidayRequest = createBeanInstance(SubHolidayRequestReferenceBeanInterface.class);
		workTypeChangeRequest = createBeanInstance(WorkTypeChangeRequestReferenceBeanInterface.class);
		differenceRequest = createBeanInstance(DifferenceRequestReferenceBeanInterface.class);
		workflowCommentReference = createBeanInstance(WorkflowCommentReferenceBeanInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		substituteReference = createBeanInstance(SubstituteReferenceBeanInterface.class);
		requestUtil = createBeanInstance(RequestUtilBeanInterface.class);
	}
	
	@Override
	public Map<String, Map<Long, WorkflowDtoInterface>> getApprovableMap(String personalId) throws MospException {
		// 承認可能勤怠申請情報群取得
		return workflowIntegrate.getApprovableMap(personalId, TimeUtility.getTimeFunctionSet());
	}
	
	@Override
	public Map<String, Map<Long, WorkflowDtoInterface>> getSubApprovableMap(String personalId,
			Map<String, Map<Long, WorkflowDtoInterface>> approvableMap) throws MospException {
		// 代理承認可能勤怠申請情報群取得
		return workflowIntegrate.getSubApprovableMap(personalId, TimeUtility.getTimeFunctionSet(),
				PlatformConst.WORKFLOW_TYPE_TIME, approvableMap);
	}
	
	@Override
	public Map<String, Map<Long, WorkflowDtoInterface>> getCancelableMap(String personalId) throws MospException {
		// 解除承認可能勤怠申請情報群取得
		return workflowIntegrate.getCancelableMap(personalId, TimeUtility.getTimeFunctionSet());
	}
	
	@Override
	public Map<String, Map<Long, WorkflowDtoInterface>> getSubCancelableMap(String personalId,
			Map<String, Map<Long, WorkflowDtoInterface>> approvableMap) throws MospException {
		// 代理解除承認可能勤怠申請情報群取得
		return workflowIntegrate.getSubCancelableMap(personalId, TimeUtility.getTimeFunctionSet(),
				PlatformConst.WORKFLOW_TYPE_TIME, approvableMap);
	}
	
	@Override
	public List<ManagementRequestListDtoInterface> getApprovableList(
			Map<String, Map<Long, WorkflowDtoInterface>> approvableMap,
			Map<String, Map<Long, WorkflowDtoInterface>> subApprovableMap, String functionCode) throws MospException {
		// 承認可能勤怠申請情報リスト取得
		List<ManagementRequestListDtoInterface> list = getManagementRequestList(approvableMap, functionCode, false);
		// 代理承認可能勤怠申請情報リスト追加
		list.addAll(getManagementRequestList(subApprovableMap, functionCode, true));
		return list;
	}
	
	/**
	 * ワークフロー情報マップから勤怠申請情報リストを取得する。<br>
	 * 対象機能コードが指定されている場合は、対象機能コードのワークフローのみを抽出する。<br>
	 * @param workflowMap  ワークフロー情報マップ(勤怠)
	 * @param functionCode 対象機能コード
	 * @param isSubApprove 代理承認フラグ(true：代理承認、false：代理承認でない)
	 * @return 勤怠申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<ManagementRequestListDtoInterface> getManagementRequestList(
			Map<String, Map<Long, WorkflowDtoInterface>> workflowMap, String functionCode, boolean isSubApprove)
			throws MospException {
		// 勤怠申請情報リスト準備
		List<ManagementRequestListDtoInterface> list = new ArrayList<ManagementRequestListDtoInterface>();
		// ワークフロー情報リスト準備
		List<WorkflowDtoInterface> workflowList = new ArrayList<WorkflowDtoInterface>();
		// ワークフロー情報マップをリストに追加
		if (functionCode.isEmpty()) {
			// ワークフロー情報リストを取得
			for (Map<Long, WorkflowDtoInterface> functionMap : workflowMap.values()) {
				workflowList.addAll(functionMap.values());
			}
		} else {
			// 指定機能コードのワークフロー情報リストを取得
			workflowList.addAll(workflowMap.get(functionCode).values());
		}
		// ワークフロー情報毎に処理
		for (WorkflowDtoInterface workflowDto : workflowList) {
			ManagementRequestListDtoInterface dto = getManagementRequestListDto(workflowDto, isSubApprove);
			if (dto != null) {
				// 勤怠申請一覧情報を取得しリストに追加
				list.add(dto);
			}
		}
		return list;
	}
	
	@Override
	public List<ManagementRequestListDtoInterface> getEffectiveList(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet, String state, Set<String> personalIdSet, Set<String> subordinateIdSet)
			throws MospException {
		// 承認可能勤怠申請情報リスト準備
		List<ManagementRequestListDtoInterface> list = new ArrayList<ManagementRequestListDtoInterface>();
		// 有効ワークフロー情報リスト取得
		List<WorkflowDtoInterface> effectiveList = workflowIntegrate.getEffectiveList(personalId, fromDate, toDate,
				functionCodeSet, state, personalIdSet, subordinateIdSet);
		// 有効ワークフロー情報毎に処理
		for (WorkflowDtoInterface workflowDto : effectiveList) {
			ManagementRequestListDtoInterface dto = getManagementRequestListDto(workflowDto, false);
			if (dto != null) {
				// 勤怠申請一覧情報を取得しリストに追加
				list.add(dto);
			}
		}
		return list;
	}
	
	@Override
	public List<ManagementRequestListDtoInterface> getCompletedList(String personalId, Date fromDate, Date toDate,
			Set<String> functionCodeSet) throws MospException {
		// 承認可能勤怠申請情報リスト準備
		List<ManagementRequestListDtoInterface> list = new ArrayList<ManagementRequestListDtoInterface>();
		// 有効ワークフロー情報リスト取得
		List<WorkflowDtoInterface> effectiveList = workflowIntegrate.getCompletedList(personalId, fromDate, toDate,
				functionCodeSet);
		// 有効ワークフロー情報毎に処理
		for (WorkflowDtoInterface workflowDto : effectiveList) {
			ManagementRequestListDtoInterface dto = getManagementRequestListDto(workflowDto, false);
			if (dto != null) {
				// 勤怠申請一覧情報を取得しリストに追加
				list.add(dto);
			}
		}
		return list;
	}
	
	@Override
	public ManagementRequestListDtoInterface getManagementRequestListDto(WorkflowDtoInterface workflowDto,
			boolean isSubApprove) throws MospException {
		// 勤怠申請一覧情報準備
		ManagementRequestListDtoInterface dto = new ManagementRequestListDto();
		// ワークフロー情報取得
		long workflow = workflowDto.getWorkflow();
		String personalId = workflowDto.getPersonalId();
		Date workflowDate = workflowDto.getWorkflowDate();
		String functionCode = workflowDto.getFunctionCode();
		// ワークフロー情報を勤怠申請一覧情報に設定
		dto.setPersonalId(personalId);
		dto.setRequestType(functionCode);
		dto.setRequestDate(workflowDto.getWorkflowDate());
		dto.setWorkflow(workflow);
		dto.setState(workflowDto.getWorkflowStatus());
		dto.setStage(workflowDto.getWorkflowStage());
		setWorkflowInfo(dto, workflowDto);
		// 勤怠申請一覧情報設定値準備
		String employeeCode = "";
		String lastName = "";
		String firstName = "";
		String sectionCode = "";
		// 人事基本情報取得
		HumanDtoInterface humanDto = humanReference.getHumanInfo(personalId, workflowDate);
		// 人事基本情報確認
		if (humanDto != null) {
			// 勤怠申請一覧情報に設定
			employeeCode = humanDto.getEmployeeCode();
			lastName = humanDto.getLastName();
			firstName = humanDto.getFirstName();
			sectionCode = humanDto.getSectionCode();
		}
		// 勤怠申請一覧情報設定値を勤怠申請一覧情報に設定
		dto.setEmployeeCode(employeeCode);
		dto.setLastName(lastName);
		dto.setFirstName(firstName);
		dto.setSectionCode(sectionCode);
		// 追加業務ロジックコードキーを準備
		String logicCodeKey = TimeConst.CODE_KEY_ADD_APPROVALINFOREFERENCEBEAN_GETMANAGEMENTREQUESTLISTDTO;
		// 機能コード確認
		if (TimeUtility.isAttendance(workflowDto)) {
			// 勤怠申請情報取得
			AttendanceDtoInterface attendanceDto = attendanceReference.findForWorkflow(workflow);
			// 勤怠申請情報が存在しない場合
			if (attendanceDto == null) {
				return null;
			}
			// 勤怠申請が存在する場合
			dto.setRequestInfo(getAttendanceInfo(attendanceDto));
			// 追加業務ロジック処理
			doStoredLogic(logicCodeKey, dto, workflowDto, attendanceDto);
		} else if (functionCode.equals(TimeConst.CODE_FUNCTION_OVER_WORK)) {
			// 残業申請情報取得
			OvertimeRequestDtoInterface overtimeDto = overtimeRequest.findForWorkflow(workflow);
			dto.setRequestInfo(getOvertimeRequestInfo(overtimeDto));
		} else if (functionCode.equals(TimeConst.CODE_FUNCTION_VACATION)) {
			// 休暇申請情報取得
			HolidayRequestDtoInterface holidayDto = holidayRequest.findForWorkflow(workflow);
			dto.setRequestInfo(getHolidayRequestInfo(holidayDto));
		} else if (functionCode.equals(TimeConst.CODE_FUNCTION_WORK_HOLIDAY)) {
			// 振出休出申請情報取得
			WorkOnHolidayRequestDtoInterface workOnHolidayDto = workOnHolidayRequest.findForWorkflow(workflow);
			dto.setRequestInfo(getWorkOnHolidayRequestInfo(workOnHolidayDto));
		} else if (functionCode.equals(TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY)) {
			// 代休申請情報取得
			SubHolidayRequestDtoInterface subHolidayDto = subHolidayRequest.findForWorkflow(workflow);
			dto.setRequestInfo(getSubHolidayRequestInfo(subHolidayDto));
		} else if (functionCode.equals(TimeConst.CODE_FUNCTION_DIFFERENCE)) {
			// 時差出勤申請情報取得
			DifferenceRequestDtoInterface differenceDto = differenceRequest.findForWorkflow(workflow);
			dto.setRequestInfo(getDifferenceRequestInfo(differenceDto));
		} else if (functionCode.equals(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE)) {
			// 勤務形態変更申請情報取得
			WorkTypeChangeRequestDtoInterface workTypeChangeDto = workTypeChangeRequest.findForWorkflow(workflow);
			dto.setRequestInfo(getWorkTypeChangeRequestInfo(workTypeChangeDto));
		}
		if (PlatformConst.CODE_STATUS_CANCEL_APPLY.equals(workflowDto.getWorkflowStatus())
				|| PlatformConst.CODE_STATUS_CANCEL_WITHDRAWN_APPLY.equals(workflowDto.getWorkflowStatus())) {
			// 承認解除申請の場合
			dto.setRequestInfo(workflowCommentReference.getLatestWorkflowCommentInfo(workflow).getWorkflowComment());
		}
		// 代理情報設定
		if (isSubApprove) {
			dto.setRequestInfo(mospParams.getName("Substitution") + SEPARATOR_REQUEST_INFO + dto.getRequestInfo());
		}
		// 勤怠申請一覧情報を取得
		return dto;
	}
	
	/**
	 * 申請情報詳細の設定
	 * @param dto 勤怠情報DTO
	 * @return 申請情報詳細
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected String getAttendanceInfo(AttendanceDtoInterface dto) throws MospException {
		String returnString = "";
		// Map作成
		HashMap<String, String> map = getAttendanceInfoMap(dto);
		// 文字列生成
		returnString = getAttendanceInfoText(map);
		return returnString;
	}
	
	/**
	 * 申請情報詳細設定用MAP作成
	 * @param dto 勤怠情報DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 * @return 申請情報詳細保持MAP
	 */
	protected HashMap<String, String> getAttendanceInfoMap(AttendanceDtoInterface dto) throws MospException {
		// 始業・終業・直行・直帰・遅刻・早退・残業等必要な情報を表示する
		StringBuffer sb = new StringBuffer();
		HashMap<String, String> map = new HashMap<String, String>();
		DifferenceRequestDtoInterface differenceRequestDto = differenceRequest.findForKeyOnWorkflow(dto.getPersonalId(),
				dto.getWorkDate());
		if (differenceRequestDto == null) {
			WorkTypeDtoInterface workTypeDto = workTypeReference.findForInfo(dto.getWorkTypeCode(), dto.getWorkDate());
			if (workTypeDto != null) {
				// 勤務形態
				map.put("WorkTypeAbbr", workTypeDto.getWorkTypeAbbr());
			}
		} else {
			WorkflowDtoInterface workflowDto = workflowIntegrate
				.getLatestWorkflowInfo(differenceRequestDto.getWorkflow());
			if (workflowDto != null && PlatformConst.CODE_STATUS_COMPLETE.equals(workflowDto.getWorkflowStatus())) {
				// 承認済の場合
				map.put("DifferenceType",
						differenceRequest.getDifferenceAbbr(differenceRequestDto.getDifferenceType()));
			}
		}
		if (dto.getDirectStart() == 1) {
			// 直行
			map.put("DirectStart", getDirectStartNaming());
		}
		if (dto.getDirectEnd() == 1) {
			// 直帰
			map.put("DirectEnd", getDirectEndNaming());
		}
		String startWorkAndEndWork = getAttendanceWorkInfo(dto);
		if (!startWorkAndEndWork.isEmpty()) {
			// 始業・終業時刻
			map.put("StartWorkAndEndWork", startWorkAndEndWork);
		}
		if (dto.getStartTime() != null) {
			// 始業
			sb = new StringBuffer();
			sb.append(mospParams.getName("StartWork"));
			sb.append(mospParams.getName("Colon"));
			sb.append(DateUtility.getStringTime(dto.getStartTime(), dto.getWorkDate()));
			map.put("StartWork", sb.toString());
		}
		if (dto.getEndTime() != null) {
			// 終業
			sb = new StringBuffer();
			sb.append(mospParams.getName("EndWork"));
			sb.append(mospParams.getName("Colon"));
			sb.append(DateUtility.getStringTime(dto.getEndTime(), dto.getWorkDate()));
			map.put("EndWork", sb.toString());
		}
		if (dto.getPrivateTime() > 0) {
			// 私用外出
			sb = new StringBuffer();
			sb.append(mospParams.getName("PrivateGoingOut"));
			sb.append(mospParams.getName("Colon"));
			sb.append(TimeUtility.getStringPeriodTime(mospParams, dto.getPrivateTime()));
			map.put("PrivateGoingOut", sb.toString());
		}
		if (!dto.getLateReason().isEmpty()) {
			// 遅刻
			sb = new StringBuffer();
			sb.append(mospParams.getName("Tardiness"));
			sb.append(mospParams.getName("Colon"));
			sb.append(TimeUtility.getStringPeriodTime(mospParams, dto.getLateTime()));
			map.put("Tardiness", sb.toString());
		}
		if (!dto.getLeaveEarlyReason().isEmpty()) {
			// 早退
			sb = new StringBuffer();
			sb.append(mospParams.getName("LeaveEarly"));
			sb.append(mospParams.getName("Colon"));
			sb.append(TimeUtility.getStringPeriodTime(mospParams, dto.getLeaveEarlyTime()));
			map.put("LeaveEarly", sb.toString());
		}
		// 休暇リスト取得
		List<HolidayRequestDtoInterface> holidayList = holidayRequest
			.getHolidayRequestListOnWorkflow(dto.getPersonalId(), dto.getWorkDate());
		if (holidayList.isEmpty() == false) {
			for (HolidayRequestDtoInterface holidayDto : holidayList) {
				// 時間休の場合
				if (holidayDto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
					map.put("HolidayTime", mospParams.getName("HolidayTime"));
					break;
				}
			}
		}
		// 残業
		String overtime = getAttendanceOvertimeInfo(dto);
		if (!overtime.isEmpty()) {
			map.put("Overtime", overtime);
		}
		if (dto.getLateNightTime() > 0) {
			// 深夜
			sb = new StringBuffer();
			sb.append(mospParams.getName("Midnight"));
			sb.append(mospParams.getName("Colon"));
			sb.append(TimeUtility.getStringPeriodTime(mospParams, dto.getLateNightTime()));
			map.put("Midnight", sb.toString());
		}
		if (!dto.getTimeComment().isEmpty()) {
			// 勤怠コメント
			map.put("TimeComment", dto.getTimeComment());
		}
		if (!dto.getRemarks().isEmpty()) {
			// 備考
			map.put("Remarks", dto.getRemarks());
		}
		return map;
	}
	
	/**
	 * 申請情報詳細文字列取得
	 * @param map 申請情報保持MAP
	 * @return 申請情報詳細文字列
	 */
	protected String getAttendanceInfoText(HashMap<String, String> map) {
		StringBuffer sb = new StringBuffer();
		String[] listOrder = mospParams.getApplicationProperties(APP_ATTENDANCE_INFO);
		for (String key : listOrder) {
			String targetValue = map.get(key);
			if (targetValue == null) {
				continue;
			}
			sb.append(targetValue);
			sb.append(SEPARATOR_REQUEST_INFO);
		}
		return sb.toString();
	}
	
	/**
	 * 申請情報詳細に表示する始終業時刻項目を取得する。<br>
	 * @param dto 対象DTO
	 * @return 始終業時刻項目
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getAttendanceWorkInfo(AttendanceDtoInterface dto) throws MospException {
		StringBuffer sb = new StringBuffer();
		if (dto.getStartTime() == null && dto.getEndTime() == null) {
			return sb.toString();
		}
		if (dto.getStartTime() != null) {
			// 始業
			sb.append(DateUtility.getStringTime(dto.getStartTime(), dto.getWorkDate()));
		}
		sb.append(mospParams.getName("Wave"));
		if (dto.getEndTime() != null) {
			// 終業
			sb.append(DateUtility.getStringTime(dto.getEndTime(), dto.getWorkDate()));
		}
		return sb.toString();
	}
	
	/**
	 * 申請情報詳細に表示する残業時間項目を取得する。<br>
	 * @param dto 対象DTO
	 * @return 残業時間項目
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected String getAttendanceOvertimeInfo(AttendanceDtoInterface dto) throws MospException {
		if (dto == null) {
			return "";
		}
		if (dto.getOvertime() > 0) {
			// 残業時間が0より大きい場合
			StringBuffer sb = new StringBuffer();
			sb.append(mospParams.getName("OvertimeWork"));
			sb.append(mospParams.getName("Colon"));
			sb.append(TimeUtility.getStringPeriodTime(mospParams, dto.getOvertime()));
			return sb.toString();
		}
		return "";
	}
	
	/**
	 * 申請情報詳細の設定
	 * @param dto 残業申請DTO
	 * @return 申請情報詳細
	 */
	protected String getOvertimeRequestInfo(OvertimeRequestDtoInterface dto) {
		// 申請が削除されている場合
		if (dto == null) {
			return "";
		}
		// 表示例 予定:2時間30分 残業区分：勤務後残業 理由:顧客サポート
		StringBuffer sb = new StringBuffer();
		// 予定
		sb.append(mospParams.getName("Schedule") + mospParams.getName("Colon"));
		sb.append((dto.getRequestTime() / TimeConst.CODE_DEFINITION_HOUR) + mospParams.getName("Time"));
		sb.append((dto.getRequestTime() % TimeConst.CODE_DEFINITION_HOUR) + mospParams.getName("Minutes"));
		// 空欄
		sb.append(SEPARATOR_REQUEST_INFO);
		// 残業区分
		sb.append(mospParams.getName("Type") + mospParams.getName("Colon"));
		sb.append(getCodeName(dto.getOvertimeType(), TimeConst.CODE_OVER_TIME_TYPE));
		// 空欄
		sb.append(SEPARATOR_REQUEST_INFO);
		// 理由
		sb.append(mospParams.getName("Reason") + mospParams.getName("Colon") + dto.getRequestReason());
		return sb.toString();
	}
	
	/**
	 * 申請情報詳細の設定
	 * @param dto 休暇申請DTO
	 * @return 申請情報詳細
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected String getHolidayRequestInfo(HolidayRequestDtoInterface dto) throws MospException {
		// 申請が削除されている場合
		if (dto == null) {
			return "";
		}
		int holidayType1 = dto.getHolidayType1();
		// 表示例 結婚休暇
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("Wave"));
		sb.append(DateUtility.getStringDateAndDay(dto.getRequestEndDate()));
		sb.append(SEPARATOR_REQUEST_INFO);
		if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_HOLIDAY) {
			if (Integer.toString(1).equals(dto.getHolidayType2())) {
				// 有給休暇
				sb.append(mospParams.getName("Salaried"));
				sb.append(mospParams.getName("Holiday"));
			} else if (Integer.toString(2).equals(dto.getHolidayType2())) {
				// ストック休暇
				sb.append(mospParams.getName("Stock"));
				sb.append(mospParams.getName("Holiday"));
			}
		} else if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_SPECIAL) {
			// 特別休暇
			sb.append(mospParams.getName("Specially"));
			sb.append(mospParams.getName("Holiday"));
		} else if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_OTHER) {
			// その他休暇
			sb.append(mospParams.getName("Others"));
		} else if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 欠勤
			sb.append(mospParams.getName("Absence"));
		}
		if (holidayType1 == TimeConst.CODE_HOLIDAYTYPE_SPECIAL || holidayType1 == TimeConst.CODE_HOLIDAYTYPE_OTHER
				|| holidayType1 == TimeConst.CODE_HOLIDAYTYPE_ABSENCE) {
			// 特別休暇、その他休暇又は欠勤
			HolidayDtoInterface holidayDto = holidayDao.findForInfo(dto.getHolidayType2(), dto.getRequestStartDate(),
					holidayType1);
			if (holidayDto != null) {
				sb.append(SEPARATOR_REQUEST_INFO);
				sb.append(holidayDto.getHolidayAbbr());
			}
		}
		sb.append(SEPARATOR_REQUEST_INFO);
		// 休暇範囲設定
		sb.append(mospParams.getProperties().getCodeItemName(TimeConst.CODE_HOLIDAY_TYPE3_RANGE1,
				String.valueOf(dto.getHolidayRange())));
		// 時間休の場合
		if (dto.getHolidayRange() == TimeConst.CODE_HOLIDAY_RANGE_TIME) {
			sb.append(DateUtility.getStringTime(dto.getStartTime(), dto.getRequestStartDate()));
			sb.append(mospParams.getName("Wave"));
			sb.append(DateUtility.getStringTime(dto.getEndTime(), dto.getRequestStartDate()));
		}
		sb.append(SEPARATOR_REQUEST_INFO);
		// 休暇理由
		sb.append(mospParams.getName("Holiday"));
		sb.append(mospParams.getName("Reason"));
		sb.append(mospParams.getName("Colon"));
		sb.append(dto.getRequestReason());
		return sb.toString();
	}
	
	/**
	 * 申請情報詳細の設定
	 * @param dto 振出休出申請DTO
	 * @return 申請情報詳細
	 * @throws MospException 例外処理発生時
	 */
	protected String getWorkOnHolidayRequestInfo(WorkOnHolidayRequestDtoInterface dto) throws MospException {
		// 申請が削除されている場合
		if (dto == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		String startTime = DateUtility.getStringTime(dto.getStartTime(), dto.getRequestDate());
		String endTime = DateUtility.getStringTime(dto.getEndTime(), dto.getRequestDate());
		if (!startTime.isEmpty() && !endTime.isEmpty()) {
			// 勤務予定
			sb.append(mospParams.getName("Work"));
			sb.append(mospParams.getName("Schedule"));
			sb.append(mospParams.getName("Colon"));
			sb.append(startTime);
			sb.append(mospParams.getName("Wave"));
			sb.append(endTime);
			// 空欄
			sb.append(" ");
		}
		int substitute = dto.getSubstitute();
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE) {
			// 振替出勤(勤務形態変更あり)の場合
			WorkTypeDtoInterface workTypeDto = workTypeReference.findForInfo(dto.getWorkTypeCode(),
					dto.getRequestDate());
			if (workTypeDto != null) {
				sb.append(mospParams.getName("Work", "Form", "Colon"));
				sb.append(workTypeDto.getWorkTypeAbbr());
				sb.append(SEPARATOR_REQUEST_INFO);
			}
		}
		if (substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_ON_WORK_TYPE_CHANGE
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_AM
				|| substitute == TimeConst.CODE_WORK_ON_HOLIDAY_SUBSTITUTE_PM) {
			// 振替出勤(勤務形態変更なし)・
			// 振替出勤(勤務形態変更あり)・
			// 振替出勤(午前)・
			// 振替出勤(午後)の場合
			List<SubstituteDtoInterface> list = substituteReference.getSubstituteList(dto.getWorkflow());
			for (SubstituteDtoInterface substituteDto : list) {
				// 振替休日
				sb.append(mospParams.getName("Transfer"));
				sb.append(mospParams.getName("DayOff"));
				sb.append(mospParams.getName("Colon"));
				sb.append(DateUtility.getStringDateAndDay(substituteDto.getSubstituteDate()));
				if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_AM) {
					// 午前の場合
					sb.append(mospParams.getName("AnteMeridiem"));
				} else if (substituteDto.getSubstituteRange() == TimeConst.CODE_HOLIDAY_RANGE_PM) {
					// 午後の場合
					sb.append(mospParams.getName("PostMeridiem"));
				}
				// 空欄
				sb.append(" ");
				break;
			}
		}
		// 申請理由
		sb.append(mospParams.getName("Application"));
		sb.append(mospParams.getName("Reason"));
		sb.append(mospParams.getName("Colon"));
		sb.append(dto.getRequestReason());
		return sb.toString();
	}
	
	/**
	 * 申請情報詳細の設定
	 * @param dto 代休申請DTO
	 * @return 申請情報詳細
	 * @throws MospException 例外処理発生時
	 */
	protected String getSubHolidayRequestInfo(SubHolidayRequestDtoInterface dto) throws MospException {
		// 申請が削除されている場合
		if (dto == null) {
			return "";
		}
		// 表示例 休出:2010/12/1 勤務7時間00分
		StringBuffer sb = new StringBuffer();
		int type = dto.getWorkDateSubHolidayType();
		if (type == TimeConst.CODE_PRESCRIBED_SUBHOLIDAY_CODE || type == TimeConst.CODE_LEGAL_SUBHOLIDAY_CODE) {
			// 休出
			sb.append(mospParams.getName("WorkingHoliday"));
		} else if (type == TimeConst.CODE_MIDNIGHT_SUBHOLIDAY_CODE) {
			// 深夜
			sb.append(mospParams.getName("Midnight"));
		}
		sb.append(mospParams.getName("Colon"));
		sb.append(DateUtility.getStringDateAndDay(dto.getWorkDate()));
		// 空欄
		sb.append(SEPARATOR_REQUEST_INFO);
		sb.append(mospParams.getName("Work"));
		sb.append(mospParams.getName("Colon"));
		AttendanceDtoInterface attendanceDto = attendanceReference.findForKey(dto.getPersonalId(), dto.getWorkDate());
		if (attendanceDto == null) {
			sb.append(TimeUtility.getStringJpTime(mospParams, 0));
		} else {
			sb.append(TimeUtility.getStringJpTime(mospParams, attendanceDto.getWorkTime()));
		}
		// 休暇範囲設定
		sb.append(SEPARATOR_REQUEST_INFO);
		sb.append(mospParams.getProperties().getCodeItemName(TimeConst.CODE_HOLIDAY_TYPE3_RANGE1,
				String.valueOf(dto.getHolidayRange())));
		return sb.toString();
	}
	
	/**
	 * 申請情報詳細の設定
	 * @param dto 勤務形態変更申請DTO
	 * @return 申請情報詳細
	 * @throws MospException 例外処理発生時
	 */
	protected String getWorkTypeChangeRequestInfo(WorkTypeChangeRequestDtoInterface dto) throws MospException {
		// 申請が削除されている場合
		if (dto == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		// 勤務形態
		sb.append(workTypeReference.getWorkTypeAbbrAndTime(dto.getWorkTypeCode(), dto.getRequestDate()));
		sb.append(SEPARATOR_REQUEST_INFO);
		// 理由
		sb.append(mospParams.getName("Reason"));
		sb.append(mospParams.getName("Colon"));
		sb.append(dto.getRequestReason());
		return sb.toString();
	}
	
	/**
	 * 申請情報詳細の設定
	 * @param dto 時差出勤申請DTO
	 * @return 申請情報詳細
	 */
	protected String getDifferenceRequestInfo(DifferenceRequestDtoInterface dto) {
		// 申請が削除されている場合
		if (dto == null) {
			return "";
		}
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getName("Application"));
		sb.append(mospParams.getName("Colon"));
		sb.append(differenceRequest.getDifferenceTime(dto));
		sb.append(SEPARATOR_REQUEST_INFO);
		sb.append(mospParams.getName("Reason"));
		sb.append(mospParams.getName("Colon"));
		sb.append(dto.getRequestReason());
		return sb.toString();
	}
	
	@Override
	public BaseDtoInterface getRequestDtoForWorkflow(long workflow, boolean isApproval) throws MospException {
		WorkflowDtoInterface dto = workflowIntegrate.getLatestWorkflowInfo(workflow);
		if (dto != null) {
			// 下書きを除く
			if (isApproval && PlatformConst.CODE_STATUS_DRAFT.equals(dto.getWorkflowStatus())) {
				return null;
			}
			// 勤怠データ
			if (TimeConst.CODE_FUNCTION_WORK_MANGE.equals(dto.getFunctionCode())) {
				return attendanceReference.findForWorkflow(workflow);
			}
			// 残業申請
			if (TimeConst.CODE_FUNCTION_OVER_WORK.equals(dto.getFunctionCode())) {
				return overtimeRequest.findForWorkflow(workflow);
			}
			// 休暇申請
			if (TimeConst.CODE_FUNCTION_VACATION.equals(dto.getFunctionCode())) {
				return holidayRequest.findForWorkflow(workflow);
			}
			// 休日出勤
			if (TimeConst.CODE_FUNCTION_WORK_HOLIDAY.equals(dto.getFunctionCode())) {
				return workOnHolidayRequest.findForWorkflow(workflow);
			}
			// 代休申請
			if (TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY.equals(dto.getFunctionCode())) {
				return subHolidayRequest.findForWorkflow(workflow);
			}
			// 勤務形態変更
			if (TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE.equals(dto.getFunctionCode())) {
				return workTypeChangeRequest.findForWorkflow(workflow);
			}
			// 時差出勤
			if (TimeConst.CODE_FUNCTION_DIFFERENCE.equals(dto.getFunctionCode())) {
				return differenceRequest.findForWorkflow(workflow);
			}
		}
		return null;
	}
	
	@Override
	public void setWorkflowInfo(RequestListDtoInterface dto, WorkflowDtoInterface workflowDto) throws MospException {
		if (dto != null && workflowDto != null) {
			WorkflowCommentDtoInterface commentDto = workflowCommentReference
				.getLatestWorkflowCommentInfo(workflowDto.getWorkflow());
			if (commentDto != null) {
				// 承認段階
				dto.setStage(workflowDto.getWorkflowStage());
				// 承認状況
				dto.setState(workflowDto.getWorkflowStatus());
				String approverName = humanReference.getHumanName(commentDto.getPersonalId(),
						commentDto.getWorkflowDate());
				// 未承認かn次済の場合
				if (PlatformConst.CODE_STATUS_APPLY.equals(workflowDto.getWorkflowStatus())
						|| PlatformConst.CODE_STATUS_APPROVED.equals(workflowDto.getWorkflowStatus())) {
					// 次の承認者の個人IDを取得。
					approverName = getNextApprover(workflowDto);
				}
				// 承認者名
				dto.setApproverName(approverName);
			}
		}
	}
	
	@Override
	public String getNextApprover(WorkflowDtoInterface workflowDto) throws MospException {
		// 勤務日・承認状況取得
		Date workDate = workflowDto.getWorkflowDate();
		String status = workflowDto.getWorkflowStatus();
		// 承認個人ID
		if (workflowDto.getApproverId().isEmpty()) {
			return "";
		}
		// 承認者個人ID配列取得
		String[] approvalPersonalId = MospUtility.split(workflowDto.getApproverId(), MospConst.APP_PROPERTY_SEPARATOR);
		// 未承認の場合
		if (PlatformConst.CODE_STATUS_APPLY.equals(status)) {
			// 自己承認の場合
			if (PlatformConst.APPROVAL_ROUTE_SELF.equals(status)) {
				return humanReference.getHumanName(workflowDto.getPersonalId(), workDate);
			}
			// 承認予定者
			return humanReference.getHumanName(approvalPersonalId[0], workDate);
		}
		// 次承認予定者
		return humanReference.getHumanName(approvalPersonalId[workflowDto.getWorkflowStage() - 1], workDate);
	}
	
	@Override
	public boolean isExistAttendanceTargetDate(String personalId, Date targetDate) throws MospException {
		// 各種申請情報取得
		requestUtil.setRequests(personalId, targetDate);
		// 勤怠申請情報(取下、下書、1次戻以外)を取得
		AttendanceDtoInterface attendanceDto = requestUtil.getApplicatedAttendance();
		if (attendanceDto == null) {
			return false;
		}
		return true;
	}
	
	/**
	 * 直行名称を取得する。<br>
	 * @return 直行名称
	 */
	protected String getDirectStartNaming() {
		return mospParams.getName("DirectStart");
	}
	
	/**
	 * 直帰名称を取得する。<br>
	 * @return 直帰名称
	 */
	protected String getDirectEndNaming() {
		return mospParams.getName("DirectEnd");
	}
	
}
