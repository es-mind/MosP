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
package jp.mosp.time.entity;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.AdditionalLogicExecutor;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.human.SuspensionDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.human.utils.HumanUtility;
import jp.mosp.platform.utils.PfNameUtility;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.CutoffErrorListDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.CutoffErrorListDto;
import jp.mosp.time.utils.TimeNamingUtility;
import jp.mosp.time.utils.TimeUtility;
import jp.mosp.time.utils.TotalTimeUtility;

/**
 * 申請検出エンティティクラス。<br>
 * <br>
 * 勤怠関連申請の未承認や未申請を検出する。<br>
 * <br>
 */
public class RequestDetectEntity extends AdditionalLogicExecutor implements RequestDetectEntityInterface {
	
	/**
	 * 対象個人ID。<br>
	 */
	protected String									personalId;
	
	/**
	 * 検出対象日リスト(日付昇順)。<br>
	 */
	protected List<Date>								targetDateList;
	
	/**
	 * 休職情報リスト。<br>
	 */
	protected List<SuspensionDtoInterface>				suspensionList;
	
	/**
	 * 予定勤務形態コード群。<br>
	 * 検出対象期間における日毎の勤務形態コードを格納する。<br>
	 * 勤務形態コードはカレンダ日情報から取得する。<br>
	 * カレンダ日情報が無い場合は空白が設定される。<br>
	 */
	protected Map<Date, String>							scheduleMap;
	
	/**
	 * 勤怠情報リスト。<br>
	 */
	protected List<AttendanceDtoInterface>				attendanceList;
	
	/**
	 * 休日出勤申請リスト。<br>
	 */
	protected List<WorkOnHolidayRequestDtoInterface>	workOnHolidayRequestList;
	
	/**
	 * 休暇申請リスト。<br>
	 */
	protected List<HolidayRequestDtoInterface>			holidayRequestList;
	
	/**
	 * 代休申請リスト。<br>
	 */
	protected List<SubHolidayRequestDtoInterface>		subHolidayRequestList;
	
	/**
	 * 残業申請リスト。<br>
	 */
	protected List<OvertimeRequestDtoInterface>			overtimeRequestList;
	
	/**
	 * 勤務形態変更申請リスト。<br>
	 */
	protected List<WorkTypeChangeRequestDtoInterface>	workTypeChangeRequestList;
	
	/**
	 * 時差出勤申請リスト。<br>
	 */
	protected List<DifferenceRequestDtoInterface>		differenceRequestList;
	
	/**
	 * 振替休日情報群。<br>
	 * 検出対象期間における承認済振替休日情報を格納する。<br>
	 */
	protected List<SubstituteDtoInterface>				substituteList;
	
	/**
	 * ワークフロー情報群。<br>
	 */
	protected Map<Long, WorkflowDtoInterface>			workflowMap;
	
	/**
	 * 未承認ワークフロー情報リスト。<br>
	 * <br>
	 * {@link RequestDetectEntity#isApprovableExist(MospParams, boolean)}
	 * により、設定される。<br>
	 * <br>
	 */
	protected List<WorkflowDtoInterface>				approvableList;
	
	/**
	 * 勤怠未申請日リスト。<br>
	 * <br>
	 * {@link RequestDetectEntity#isAppliableExist(boolean)}
	 * により、設定される。<br>
	 * <br>
	 */
	protected List<Date>								appliableList;
	
	/**
	 * 残業未申請日リスト。<br>
	 * <br>
	 * {@link RequestDetectEntity#isOvertimeNotAppliedExist(boolean)}
	 * により、設定される。<br>
	 * <br>
	 */
	protected List<Date>								overtimeNotAppliedList;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	public RequestDetectEntity() {
		// 未承認ワークフロー情報リストを準備
		approvableList = new ArrayList<WorkflowDtoInterface>();
		// 勤怠未申請日リストを準備
		appliableList = new ArrayList<Date>();
		// 残業未申請日リストを準備
		overtimeNotAppliedList = new ArrayList<Date>();
	}
	
	/**
	 * 未承認の判断は、次のメソッドによる。<br>
	 * {@link WorkflowUtility#isNotApproved(WorkflowDtoInterface)}<br>
	 * {@link WorkflowUtility#isFirstReverted(WorkflowDtoInterface)}<br>
	 * <br>
	 */
	@Override
	public boolean isApprovableExist(MospParams mospParamsm, boolean isImmediately) throws MospException {
		// 検出対象日リストが空白である場合
		if (targetDateList.isEmpty()) {
			// 未承認無と判断
			return false;
		}
		// 検出対象期間初日及び最終日を取得
		Date firstDate = MospUtility.getFirstValue(targetDateList);
		Date lastDate = MospUtility.getLastValue(targetDateList);
		// 申請されたワークフロー情報毎に処理
		for (WorkflowDtoInterface dto : getRequestedWorkflows(mospParamsm)) {
			// 未承認でない場合
			if (isApprovable(dto.getWorkflowDate(), firstDate, lastDate, dto) == false) {
				// 次のワークフロー情報へ
				continue;
			}
			// 未承認ワークフロー情報リストにワークフロー情報を追加(未承認有り)
			approvableList.add(dto);
			// 即時の場合
			if (isImmediately) {
				// 処理終了
				return true;
			}
		}
		// 未承認ワークフロー情報リストを確認
		return approvableList.isEmpty() == false;
	}
	
	@Override
	public boolean isAppliableExist(boolean isImmediately) {
		// 検出対象日リストが空白である場合
		if (targetDateList == null || targetDateList.isEmpty()) {
			// 未申請無と判断
			return false;
		}
		List<Date> list = new ArrayList<Date>();
		// 検出対象日リストを追加
		list.addAll(targetDateList);
		// 順序を反転
		Collections.reverse(list);
		// 対象日毎に処理
		for (Date targetDate : list) {
			// 対象日が休職中である場合
			if (HumanUtility.isSuspension(suspensionList, targetDate)) {
				// 未申請確認対象外
				continue;
			}
			// 対象日が未申請である場合
			if (isAppliable(targetDate)) {
				// 勤怠未申請日リストに対象日を追加(勤怠未申請有)
				appliableList.add(targetDate);
				// 即時の場合
				if (isImmediately) {
					// 勤怠未申請有と判断
					return true;
				}
			}
		}
		// 勤怠未申請無と判断
		return appliableList.isEmpty() == false;
	}
	
	@Override
	public boolean isAppliableExistContainRequests() {
		// 検出対象日リストが空白である場合
		if (targetDateList == null || targetDateList.isEmpty()) {
			// 未承認無と判断
			return false;
		}
		// 対象日リスト準備
		List<Date> list = new ArrayList<Date>();
		// 検出対象日リストを追加
		list.addAll(targetDateList);
		// 順序を反転
		Collections.reverse(list);
		// 対象日毎に処理
		for (Date targetDate : list) {
			// 対象日が休職中である場合
			if (HumanUtility.isSuspension(suspensionList, targetDate)) {
				// 未申請確認対象外
				continue;
			}
			// 日毎の申請エンティティ取得
			RequestEntity requestEntity = getRequestEntity(targetDate);
			// 勤怠申請が可能である場合
			if (requestEntity.isAttendanceAppliable()) {
				// 勤怠未申請日リストに対象日を追加(勤怠未申請有)
				appliableList.add(targetDate);
			}
		}
		// 勤怠未申請無と判断
		return appliableList.isEmpty() == false;
	}
	
	@Override
	public boolean isOvertimeNotAppliedExist(boolean isImmediately) {
		// 勤怠情報毎に処理
		for (AttendanceDtoInterface attendanceDto : attendanceList) {
			// 対象勤怠情報が残業未申請でない場合
			if (isOvertimeNotApplied(attendanceDto) == false) {
				// 次の勤怠情報へ
				continue;
			}
			// 残業未申請日リストに対象日を追加(残業未申請有)
			overtimeNotAppliedList.add(attendanceDto.getWorkDate());
			// 即時の場合
			if (isImmediately) {
				// 確認結果を取得
				return true;
			}
		}
		// 確認結果を取得
		return overtimeNotAppliedList.isEmpty() == false;
	}
	
	@Override
	public void setBeforeDay(Date targetDate) {
		// 検出対象日リストが空白である場合
		if (targetDateList.isEmpty()) {
			// 処理無し
			return;
		}
		// 検出対象期間初日及び最終日を取得
		Date firstDate = MospUtility.getFirstValue(targetDateList);
		Date lastDate = MospUtility.getLastValue(targetDateList);
		// 空の検出対象日リストを作成(締初日だと前日が前月になるため検出対象日は無し)
		if (DateUtility.isSame(targetDate, firstDate)) {
			// 空の検出対象日リストを設定
			setTargetDateList(TimeUtility.getDateList(null, null));
			// 処理終了
			return;
		}
		// 対象日前日を取得
		Date beforeDay = DateUtility.addDay(targetDate, -1);
		// 対象日前日が検出対象期間に含まれない場合
		if (DateUtility.isTermContain(beforeDay, firstDate, lastDate) == false) {
			// 処理無し
			return;
		}
		// 対象日前日までの検出対象日リストを設定
		setTargetDateList(TimeUtility.getDateList(firstDate, beforeDay));
	}
	
	/**
	 * 各種申請リストから申請されたワークフロー情報リストを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 申請されたワークフロー情報リスト
	 * @throws MospException 業務処理で実行時例外が発生した場合
	 */
	protected Collection<WorkflowDtoInterface> getRequestedWorkflows(MospParams mospParams) throws MospException {
		// 申請されたワークフロー情報群を準備
		Set<WorkflowDtoInterface> workflows = new LinkedHashSet<WorkflowDtoInterface>();
		// 勤怠データ毎に処理
		for (AttendanceDtoInterface dto : attendanceList) {
			// ワークフロー情報を取得しリストに追加
			workflows.add(workflowMap.get(dto.getWorkflow()));
		}
		// 残業申請毎に処理
		for (OvertimeRequestDtoInterface dto : overtimeRequestList) {
			// ワークフロー情報を取得しリストに追加
			workflows.add(workflowMap.get(dto.getWorkflow()));
		}
		// 休暇申請毎に処置
		for (HolidayRequestDtoInterface dto : holidayRequestList) {
			// ワークフロー情報を取得しリストに追加
			workflows.add(workflowMap.get(dto.getWorkflow()));
		}
		// 休日出勤申請毎に処理
		for (WorkOnHolidayRequestDtoInterface dto : workOnHolidayRequestList) {
			// ワークフロー情報を取得しリストに追加
			workflows.add(workflowMap.get(dto.getWorkflow()));
		}
		// 代休申請毎に処理
		for (SubHolidayRequestDtoInterface dto : subHolidayRequestList) {
			// ワークフロー情報を取得しリストに追加
			workflows.add(workflowMap.get(dto.getWorkflow()));
		}
		// 勤務形態変更申請毎に処理
		for (WorkTypeChangeRequestDtoInterface dto : workTypeChangeRequestList) {
			// ワークフロー情報を取得しリストに追加
			workflows.add(workflowMap.get(dto.getWorkflow()));
		}
		// 時差出勤申請毎に処理
		for (DifferenceRequestDtoInterface dto : differenceRequestList) {
			// ワークフロー情報を取得しリストに追加
			workflows.add(workflowMap.get(dto.getWorkflow()));
		}
		// nullを除去(週40時間計算用の勤怠申請等ワークフローを持ってきていない申請があるため)
		workflows.remove(null);
		// 申請検出エンティティワークフロー取得追加処理を実行
		doStoredLogics(mospParams, TimeConst.CODE_KEY_REQUEST_DETECT_GET_WORKFLOWS_ADDONS, workflowMap, workflows);
		// 申請されたワークフロー情報リストを取得
		return workflows;
	}
	
	/**
	 * 対象勤怠情報が残業未申請であるかを確認する。<br>
	 * 対象勤怠情報に前残業か後残業があるのに
	 * 対応する残業申請がなされていない場合に、残業未申請と判断する。<br>
	 * 但し、休日出勤の場合は、残業申請は不要とする。<br>
	 * @param attendanceDto 勤怠(日々)情報
	 * @return 確認結果(true：残業未申請、false：残業未申請でない)
	 */
	protected boolean isOvertimeNotApplied(AttendanceDtoInterface attendanceDto) {
		// 対象日(勤務)を取得
		Date targetDate = attendanceDto.getWorkDate();
		// 検出対象日でない場合
		if (targetDateList.contains(targetDate) == false) {
			// 残業未申請でないと判断
			return false;
		}
		// 申請エンティティを取得
		RequestEntity entity = getRequestEntity(targetDate);
		// 勤怠申請がされていない場合
		if (entity.isAttendanceApplied() == false) {
			// 残業未申請でないと判断
			return false;
		}
		// 休日出勤である場合
		if (entity.isWorkOnHolidaySubstituteOff(false)) {
			// 残業未申請でないと判断
			return false;
		}
		// 前残業がある場合
		if (attendanceDto.getOvertimeBefore() > 0) {
			// 前残業申請が申請済でない場合
			if (isOvertimeApplied(targetDate, TimeConst.CODE_OVERTIME_WORK_BEFORE, workflowMap) == false) {
				// 残業未申請であると判断
				return true;
			}
		}
		// 後残業がある場合
		if (attendanceDto.getOvertimeAfter() > 0) {
			// 後残業申請が申請済でない場合
			if (isOvertimeApplied(targetDate, TimeConst.CODE_OVERTIME_WORK_AFTER, workflowMap) == false) {
				// 残業未申請であると判断
				return true;
			}
		}
		// 残業未申請でないと判断
		return false;
	}
	
	/**
	 * 残業申請が申請済であるかを確認する。<br>
	 * <br>
	 * @param targetDate   対象日
	 * @param overtimeType 残業区分
	 * @param workflowMap  ワークフロー情報群
	 * @return 確認結果(true：残業申請が申請済である、false：そうでない)
	 */
	protected boolean isOvertimeApplied(Date targetDate, int overtimeType,
			Map<Long, WorkflowDtoInterface> workflowMap) {
		// 対象日の残業申請リストを取得
		List<OvertimeRequestDtoInterface> list = TotalTimeUtility.getOvertimeRequestList(overtimeRequestList,
				workflowMap, targetDate);
		// 残業申請毎に処理
		for (OvertimeRequestDtoInterface dto : list) {
			// 残業区分が異なる場合
			if (overtimeType != dto.getOvertimeType()) {
				continue;
			}
			// 残業申請が申請済である場合
			if (WorkflowUtility.isApplied(workflowMap.get(dto.getWorkflow()))) {
				// 残業申請が申請済であると判断
				return true;
			}
		}
		// 業申請が申請済でないと判断
		return false;
	}
	
	/**
	 * 対象申請情報が未承認であるかを確認する。<br>
	 * <br>
	 * 但し、対象日が検出期間外である場合は、未承認でないとする。<br>
	 * <br>
	 * ここでは、一次戻も未承認であると判断する。<br>
	 * <br>
	 * @param targetDate  対象日
	 * @param firstDate   検出期間初日
	 * @param lastDate    検出期間最終日
	 * @param workflowDto ワークフロー情報
	 * @return 確認結果(true：未承認である、false：未承認でない)
	 */
	protected boolean isApprovable(Date targetDate, Date firstDate, Date lastDate, WorkflowDtoInterface workflowDto) {
		// 対象日が検出対象期間に含まれない場合
		if (DateUtility.isTermContain(targetDate, firstDate, lastDate) == false) {
			// 未承認でない
			return false;
		}
		// 未承認(一次戻含む)であるかを確認
		return WorkflowUtility.isNotApproved(workflowDto) || WorkflowUtility.isFirstReverted(workflowDto);
	}
	
	/**
	 * 対象日が未申請であるかを確認する。<br>
	 * <br>
	 * 申請エンティティを用いるが、勤務形態変更申請は設定(考慮)しない。<br>
	 * 未承認、未申請の検出には影響しないため。<br>
	 * <br>
	 * 勤務日でない場合は、未申請でないと判断する。<br>
	 * <br>
	 * 勤務日である場合、勤怠申請(下書か一次戻以外)の有無で判断する。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 確認結果(true：未申請である、false：未申請でない)
	 */
	protected boolean isAppliable(Date targetDate) {
		// 申請エンティティを取得
		RequestEntity entity = getRequestEntity(targetDate);
		// 勤務日でない場合
		if (entity.isWorkDay() == false) {
			// 未申請でないと判断
			return false;
		}
		// 勤怠申請(下書か一次戻以外)の有無で判断
		return entity.isAttendanceApplied() == false;
	}
	
	/**
	 * 申請エンティティを取得する。<br>
	 * <br>
	 * フィールドに設定されている情報から対象日のものを抜き出し、
	 * 申請エンティティを作成する。<br>
	 * <br>
	 * 但し、務形態変更申請はフィールドにも無いため設定しない。<br>
	 * <br>
	 * @param targetDate 対象日
	 * @return 申請エンティティ
	 */
	protected RequestEntity getRequestEntity(Date targetDate) {
		// 申請エンティティを取得
		RequestEntity entity = new RequestEntity();
		entity.setPersonalId(personalId);
		entity.setTargetDate(targetDate);
		// 申請エンティティに各種情報を設定
		entity.setAttendanceDto(TotalTimeUtility.getAttendanceDto(attendanceList, targetDate));
		entity.setWorkOnHolidayRequestDto(
				TotalTimeUtility.getWorkOnHolidayRequestDto(workOnHolidayRequestList, workflowMap, targetDate));
		entity
			.setHolidayRequestList(TotalTimeUtility.getHolidayRequestList(holidayRequestList, workflowMap, targetDate));
		entity.setSubHolidayRequestList(
				TotalTimeUtility.getSubHolidayRequestList(subHolidayRequestList, workflowMap, targetDate));
		entity.setOverTimeRequestList(
				TotalTimeUtility.getOvertimeRequestList(overtimeRequestList, workflowMap, targetDate));
		entity.setDifferenceRequestDto(
				TotalTimeUtility.getDifferenceRequestDto(differenceRequestList, workflowMap, targetDate));
		entity.setSubstituteList(TotalTimeUtility.getSubstitutList(substituteList, workflowMap, targetDate));
		entity.setWorkflowMap(workflowMap);
		entity.setScheduledWorkTypeCode(scheduleMap.get(targetDate));
		// 申請エンティティを取得
		return entity;
	}
	
	@Override
	public List<CutoffErrorListDtoInterface> getCutoffErrorList(MospParams mospParams, HumanDtoInterface humanDto) {
		// 集計時エラー内容情報リストを準備
		List<CutoffErrorListDtoInterface> list = new ArrayList<CutoffErrorListDtoInterface>();
		// 未承認ワークフロー情報毎に処理
		for (WorkflowDtoInterface workflowDto : approvableList) {
			// 集計時エラー内容情報リスト準備
			CutoffErrorListDtoInterface dto = getInitCutoffErrorListDto(workflowDto.getWorkflowDate(), humanDto);
			// 機能コードを取得
			String functionCode = workflowDto.getFunctionCode();
			// 機能コードから申請名称を取得し設定
			dto.setType(getRequestName(mospParams, functionCode));
			dto.setState(TimeNamingUtility.getNotApproved(mospParams));
			// 一次戻の場合
			if (WorkflowUtility.isFirstReverted(workflowDto)) {
				// 機能コードが勤怠の場合
				if (functionCode.equals(TimeConst.CODE_FUNCTION_WORK_MANGE)) {
					// 処理無し(未申請があるため集計時エラー内容情報リストから省略)
					continue;
				}
				// 状態を1次戻に設定
				dto.setState(TimeNamingUtility.getFirstReverted(mospParams));
			}
			// 集計時エラー内容情報リストに追加
			list.add(dto);
		}
		// 勤怠未申請日リストを昇順にソート
		Collections.sort(appliableList);
		// 勤怠未申請日毎に処理
		for (Date targetDate : appliableList) {
			// 集計時エラー内容情報リスト準備
			CutoffErrorListDtoInterface dto = getInitCutoffErrorListDto(targetDate, humanDto);
			dto.setType(TimeNamingUtility.getWorkManage(mospParams));
			dto.setState(TimeNamingUtility.getNotApplied(mospParams));
			// 集計時エラー内容情報リストに追加
			list.add(dto);
		}
		// 残業未申請日毎に処理
		for (Date targetDate : overtimeNotAppliedList) {
			// 集計時エラー内容情報リスト準備
			CutoffErrorListDtoInterface dto = getInitCutoffErrorListDto(targetDate, humanDto);
			dto.setType(TimeNamingUtility.getOvertimeWork(mospParams));
			dto.setState(TimeNamingUtility.getNotApplied(mospParams));
			// 集計時エラー内容情報リストに追加
			list.add(dto);
		}
		// 集計時エラー内容情報リストを取得
		return list;
	}
	
	/**
	 * 集計時エラー内容参照情報を初期化し取得する。<br>
	 * @param targetDate 対象日付
	 * @param humanDto   人事情報
	 * @return 集計時エラー内容参照情報
	 */
	protected CutoffErrorListDtoInterface getInitCutoffErrorListDto(Date targetDate, HumanDtoInterface humanDto) {
		CutoffErrorListDtoInterface dto = new CutoffErrorListDto();
		dto.setDate(targetDate);
		dto.setEmployeeCode(humanDto.getEmployeeCode());
		dto.setPersonalId(humanDto.getPersonalId());
		dto.setLastName(humanDto.getLastName());
		dto.setFirstName(humanDto.getFirstName());
		dto.setWorkPlaceCode(humanDto.getWorkPlaceCode());
		dto.setEmploymentCode(humanDto.getEmploymentContractCode());
		dto.setSectionCode(humanDto.getSectionCode());
		dto.setPositionCode(humanDto.getPositionCode());
		return dto;
	}
	
	/**
	 * 機能コードから申請名称を取得する。<br>
	 * <br>
	 * {@link TimeUtility#getFunctionName(MospParams, String)}
	 * との違い「申請」の文字が含まれない。<br>
	 * <br>
	 * @param mospParams   MosP処理情報
	 * @param functionCode 機能コード(ワークフロー)
	 * @return 申請名称
	 */
	protected String getRequestName(MospParams mospParams, String functionCode) {
		// 勤怠の場合
		if (functionCode.equals(TimeConst.CODE_FUNCTION_WORK_MANGE)) {
			// 勤怠
			return TimeNamingUtility.getWorkManage(mospParams);
		}
		// 勤務形態変更の場合
		if (functionCode.equals(TimeConst.CODE_FUNCTION_WORK_TYPE_CHANGE)) {
			// 勤務形態
			return TimeNamingUtility.workType(mospParams);
		}
		// 時差出勤の場合
		if (functionCode.equals(TimeConst.CODE_FUNCTION_DIFFERENCE)) {
			// 時差
			return TimeNamingUtility.getTimeDifference(mospParams);
		}
		// それ以外の場合
		String requestName = TimeUtility.getFunctionName(mospParams, functionCode);
		// 「申請」の文字を除去して取得
		return requestName.replaceAll(PfNameUtility.application(mospParams), MospConst.STR_EMPTY);
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setTargetDateList(List<Date> targetDateList) {
		this.targetDateList = targetDateList;
	}
	
	@Override
	public void setSuspensionList(List<SuspensionDtoInterface> suspensionList) {
		this.suspensionList = suspensionList;
	}
	
	@Override
	public void setScheduleMap(Map<Date, String> scheduleMap) {
		this.scheduleMap = scheduleMap;
	}
	
	@Override
	public void setAttendanceList(List<AttendanceDtoInterface> attendanceList) {
		this.attendanceList = attendanceList;
	}
	
	@Override
	public void setWorkOnHolidayRequestList(List<WorkOnHolidayRequestDtoInterface> workOnHolidayRequestList) {
		this.workOnHolidayRequestList = workOnHolidayRequestList;
	}
	
	@Override
	public void setHolidayRequestList(List<HolidayRequestDtoInterface> holidayRequestList) {
		this.holidayRequestList = holidayRequestList;
	}
	
	@Override
	public void setSubHolidayRequestList(List<SubHolidayRequestDtoInterface> subHolidayRequestList) {
		this.subHolidayRequestList = subHolidayRequestList;
	}
	
	@Override
	public void setOvertimeRequestList(List<OvertimeRequestDtoInterface> overtimeRequestList) {
		this.overtimeRequestList = overtimeRequestList;
	}
	
	@Override
	public void setWorkTypeChangeRequestList(List<WorkTypeChangeRequestDtoInterface> workTypeChangeRequestList) {
		this.workTypeChangeRequestList = workTypeChangeRequestList;
	}
	
	@Override
	public void setDifferenceRequestList(List<DifferenceRequestDtoInterface> differenceRequestList) {
		this.differenceRequestList = differenceRequestList;
	}
	
	@Override
	public void setSubstituteList(List<SubstituteDtoInterface> substituteList) {
		this.substituteList = substituteList;
	}
	
	@Override
	public void setWorkflowMap(Map<Long, WorkflowDtoInterface> workflowMap) {
		this.workflowMap = workflowMap;
	}
	
}
