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
package jp.mosp.time.utils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.utils.ValidateUtility;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.utils.WorkflowUtility;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.entity.WorkTypeEntityInterface;

/**
 * 勤怠集計における有用なメソッドを提供する。<br><br>
 */
public class TotalTimeUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TotalTimeUtility() {
		// 処理無し
	}
	
	/**
	 * 勤怠情報リストから対象日の勤怠情報を取得する。<br>
	 * <br>
	 * 勤怠情報には取下状態が無いため、ワークフロー情報は用いない。<br>
	 * <br>
	 * @param list       勤怠情報リスト
	 * @param targetDate 対象日
	 * @return 勤怠情報
	 */
	public static AttendanceDtoInterface getAttendanceDto(List<AttendanceDtoInterface> list, Date targetDate) {
		// 勤怠情報毎に処理
		for (AttendanceDtoInterface dto : list) {
			// 対象日が勤務日と同じ場合
			if (targetDate.compareTo(dto.getWorkDate()) == 0) {
				// 勤怠情報を取得
				return dto;
			}
		}
		// 勤怠情報が取得できなかった場合
		return null;
	}
	
	/**
	 * 休日出勤申請リストから対象日の休日出勤申請情報を取得する。<br>
	 * <br>
	 * 但し、取下の申請は、対象外とする。<br>
	 * <br>
	 * @param list        休日出勤申請リスト
	 * @param workflowMap ワークフロー情報群
	 * @param targetDate  対象日
	 * @return 休日出勤申請情報
	 */
	public static WorkOnHolidayRequestDtoInterface getWorkOnHolidayRequestDto(
			List<WorkOnHolidayRequestDtoInterface> list, Map<Long, WorkflowDtoInterface> workflowMap, Date targetDate) {
		// 休日出勤申請情報毎に処理
		for (WorkOnHolidayRequestDtoInterface dto : list) {
			// 申請が取下である場合
			if (WorkflowUtility.isWithDrawn(workflowMap.get(dto.getWorkflow()))) {
				// 対象外と判断
				continue;
			}
			// 対象日が申請日と同じで取下でない場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 休日出勤申請情報を取得
				return dto;
			}
		}
		// 休日出勤申請情報が取得できなかった場合
		return null;
	}
	
	/**
	 * 休暇申請リストから対象日の休暇申請リストを取得する。<br>
	 * <br>
	 * 但し、取下の申請は、対象外とする。<br>
	 * <br>
	 * @param list        休暇申請リスト
	 * @param workflowMap ワークフロー情報群
	 * @param targetDate  対象日
	 * @return 休暇申請リスト
	 */
	public static List<HolidayRequestDtoInterface> getHolidayRequestList(List<HolidayRequestDtoInterface> list,
			Map<Long, WorkflowDtoInterface> workflowMap, Date targetDate) {
		// 休暇申請リストを準備
		List<HolidayRequestDtoInterface> holidayRequests = new ArrayList<HolidayRequestDtoInterface>();
		// 休暇申請情報毎に処理
		for (HolidayRequestDtoInterface dto : list) {
			// 申請が取下である場合
			if (WorkflowUtility.isWithDrawn(workflowMap.get(dto.getWorkflow()))) {
				// 対象外と判断
				continue;
			}
			// 対象日が申請開始日と申請終了日に含まれる場合
			if (ValidateUtility.chkTerm(targetDate, dto.getRequestStartDate(), dto.getRequestEndDate())) {
				// 休暇申請リストに追加
				holidayRequests.add(dto);
			}
		}
		// 休暇申請リストを取得
		return holidayRequests;
	}
	
	/**
	 * 代休申請リストから対象日の代休申請リストを取得する。<br>
	 * <br>
	 * 但し、取下の申請は、対象外とする。<br>
	 * <br>
	 * @param list        代休申請リスト
	 * @param workflowMap ワークフロー情報群
	 * @param targetDate  対象日
	 * @return 代休申請リスト
	 */
	public static List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(List<SubHolidayRequestDtoInterface> list,
			Map<Long, WorkflowDtoInterface> workflowMap, Date targetDate) {
		// 代休申請リストを準備
		List<SubHolidayRequestDtoInterface> subHolidayRequests = new ArrayList<SubHolidayRequestDtoInterface>();
		// 代休申請情報毎に処理
		for (SubHolidayRequestDtoInterface dto : list) {
			// 申請が取下である場合
			if (WorkflowUtility.isWithDrawn(workflowMap.get(dto.getWorkflow()))) {
				// 対象外と判断
				continue;
			}
			// 対象日が申請日と同じ場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 代休申請リストに追加
				subHolidayRequests.add(dto);
			}
		}
		// 代休申請リストを取得
		return subHolidayRequests;
	}
	
	/**
	 * 残業申請リストから対象日の残業申請リストを取得する。<br>
	 * <br>
	 * 但し、取下の申請は、対象外とする。<br>
	 * <br>
	 * @param list        残業申請リスト
	 * @param workflowMap ワークフロー情報群
	 * @param targetDate  対象日
	 * @return 残業申請リスト
	 */
	public static List<OvertimeRequestDtoInterface> getOvertimeRequestList(List<OvertimeRequestDtoInterface> list,
			Map<Long, WorkflowDtoInterface> workflowMap, Date targetDate) {
		// 残業申請リストを準備
		List<OvertimeRequestDtoInterface> overtimeRequests = new ArrayList<OvertimeRequestDtoInterface>();
		// 残業申請情報毎に処理
		for (OvertimeRequestDtoInterface dto : list) {
			// 申請が取下である場合
			if (WorkflowUtility.isWithDrawn(workflowMap.get(dto.getWorkflow()))) {
				// 対象外と判断
				continue;
			}
			// 対象日が申請日と同じ場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 残業申請リストに追加
				overtimeRequests.add(dto);
			}
		}
		// 残業申請リストを取得
		return overtimeRequests;
	}
	
	/**
	 * 勤務形態変更申請リストから対象日の勤務形態変更申請情報を取得する。<br>
	 * <br>
	 * 但し、取下の申請は、対象外とする。<br>
	 * <br>
	 * @param list        勤務形態変更申請リスト
	 * @param workflowMap ワークフロー情報群
	 * @param targetDate  対象日
	 * @return 勤務形態変更申請情報
	 */
	public static WorkTypeChangeRequestDtoInterface getWorkTypeChangeRequestList(
			List<WorkTypeChangeRequestDtoInterface> list, Map<Long, WorkflowDtoInterface> workflowMap,
			Date targetDate) {
		//  勤務形態変更申請情報毎に処理
		for (WorkTypeChangeRequestDtoInterface dto : list) {
			// 申請が取下である場合
			if (WorkflowUtility.isWithDrawn(workflowMap.get(dto.getWorkflow()))) {
				// 対象外と判断
				continue;
			}
			// 対象日が申請日と同じ場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 時差出勤申請情報を取得
				return dto;
			}
		}
		//  勤務形態変更申請が取得できなかった場合
		return null;
	}
	
	/**
	 * 時差出勤申請リストから対象日の時差出勤申請情報を取得する。<br>
	 * <br>
	 * 但し、取下の申請は、対象外とする。<br>
	 * <br>
	 * @param list        時差出勤申請リスト
	 * @param workflowMap ワークフロー情報群
	 * @param targetDate  対象日
	 * @return 時差出勤申請情報
	 */
	public static DifferenceRequestDtoInterface getDifferenceRequestDto(List<DifferenceRequestDtoInterface> list,
			Map<Long, WorkflowDtoInterface> workflowMap, Date targetDate) {
		// 時差出勤申請情報毎に処理
		for (DifferenceRequestDtoInterface dto : list) {
			// 申請が取下である場合
			if (WorkflowUtility.isWithDrawn(workflowMap.get(dto.getWorkflow()))) {
				// 対象外と判断
				continue;
			}
			// 対象日が申請日と同じ場合
			if (targetDate.compareTo(dto.getRequestDate()) == 0) {
				// 時差出勤申請情報を取得
				return dto;
			}
		}
		// 時差出勤申請情報が取得できなかった場合
		return null;
	}
	
	/**
	 * 振替情報リストから対象日の振替情報リストを取得する。<br>
	 * <br>
	 * 但し、取下の申請は、対象外とする。<br>
	 * <br>
	 * @param list        振替情報リスト
	 * @param workflowMap ワークフロー情報群
	 * @param targetDate  対象日
	 * @return 振替情報リスト
	 */
	public static List<SubstituteDtoInterface> getSubstitutList(List<SubstituteDtoInterface> list,
			Map<Long, WorkflowDtoInterface> workflowMap, Date targetDate) {
		// 残業申請リストを準備
		List<SubstituteDtoInterface> substituts = new ArrayList<SubstituteDtoInterface>();
		// 残業申請情報毎に処理
		for (SubstituteDtoInterface dto : list) {
			// 申請が取下である場合
			if (WorkflowUtility.isWithDrawn(workflowMap.get(dto.getWorkflow()))) {
				// 対象外と判断
				continue;
			}
			// 対象日が振替日と同じ場合
			if (targetDate.compareTo(dto.getSubstituteDate()) == 0) {
				// 残業申請リストに追加
				substituts.add(dto);
			}
		}
		// 残業申請リストを取得
		return substituts;
	}
	
	/**
	 * 勤務形態エンティティを取得する。<br>
	 * <br>
	 * 勤務形態コードから勤務形態エンティティ履歴を取得し、
	 * 対象日以前で最新の情報を取得する<br>
	 * <br>
	 * @param map          勤務形態エンティティ群(キー：勤務形態コード)
	 * @param workTypeCode 勤務形態コード
	 * @param targetDate   対象日
	 * @return 勤務形態エンティティ
	 */
	public static WorkTypeEntityInterface getLatestWorkTypeEntity(Map<String, List<WorkTypeEntityInterface>> map,
			String workTypeCode, Date targetDate) {
		// 勤務形態エンティティ履歴を取得
		List<WorkTypeEntityInterface> history = map.get(workTypeCode);
		// 勤務形態エンティティ履歴が取得できない場合
		if (history == null || targetDate == null) {
			return null;
		}
		// 対象日以前で最新の情報を格納する変数を準備
		WorkTypeEntityInterface latestEntity = null;
		// 対象情報毎に処理
		for (WorkTypeEntityInterface entity : history) {
			// 有効日が対象日以前である場合
			if (!targetDate.before(entity.getActivateDate())) {
				latestEntity = entity;
			}
		}
		// 対象日以前で最新の有効日を取得
		return latestEntity;
	}
	
}
