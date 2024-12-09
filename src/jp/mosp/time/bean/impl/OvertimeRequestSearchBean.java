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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.OvertimeRequestSearchBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestListDtoInterface;
import jp.mosp.time.dto.settings.impl.OvertimeRequestListDto;
import jp.mosp.time.utils.TimeUtility;

/**
 * 残業申請検索クラス。
 */
public class OvertimeRequestSearchBean extends PlatformBean implements OvertimeRequestSearchBeanInterface {
	
	/**
	 * 残業申請DAO。<br>
	 */
	protected OvertimeRequestDaoInterface			overtimeRequestDao;
	
	/**
	 * 勤怠データDAO。<br>
	 */
	protected AttendanceDaoInterface				attendanceDao;
	
	/**
	 * ワークフロー参照クラス。<br>
	 */
	protected WorkflowReferenceBeanInterface		workflowReference;
	
	/**
	 * 承認情報参照処理。<br>
	 */
	protected ApprovalInfoReferenceBeanInterface	approvalInfoReference;
	
	/**
	 * 個人ID。
	 */
	private String									personalId;
	
	/**
	 * 状態。
	 */
	private String									workflowStatus;
	
	/**
	 * 予定超過。
	 */
	private String									scheduleOver;
	
	/**
	 * 残業区分。
	 */
	private String									overtimeType;
	
	/**
	 * 表示開始日。
	 */
	private Date									requestStartDate;
	
	/**
	 * 表示終了日。
	 */
	private Date									requestEndDate;
	
	
	/**
	 * コンストラクタ。
	 */
	public OvertimeRequestSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 残業申請マスタDAO取得
		overtimeRequestDao = createDaoInstance(OvertimeRequestDaoInterface.class);
		attendanceDao = createDaoInstance(AttendanceDaoInterface.class);
		approvalInfoReference = createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
		workflowReference = createBeanInstance(WorkflowReferenceBeanInterface.class);
	}
	
	@Override
	public List<OvertimeRequestListDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = overtimeRequestDao.getParamsMap();
		param.put("personalId", personalId);
		param.put("workflowStatus", workflowStatus);
		param.put("scheduleOver", scheduleOver);
		param.put("overtimeType", overtimeType);
		param.put("requestStartDate", requestStartDate);
		param.put("requestEndDate", requestEndDate);
		// 検索
		List<OvertimeRequestDtoInterface> requestList = overtimeRequestDao.findForSearch(param);
		// 残業申請一覧DTOの準備
		List<OvertimeRequestListDtoInterface> list = new ArrayList<OvertimeRequestListDtoInterface>();
		// addLsitに設定するためのフラグ
		boolean flag = false;
		// list作成
		for (OvertimeRequestDtoInterface requestDto : requestList) {
			OvertimeRequestListDtoInterface dto = new OvertimeRequestListDto();
			// ワークフローの設定
			WorkflowDtoInterface workflowDto = workflowReference.getLatestWorkflowInfo(requestDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// レコード識別ID。
			dto.setTmdOvertimeRequestId(requestDto.getTmdOvertimeRequestId());
			// 勤怠データの設定
			AttendanceDtoInterface attendanceDto = attendanceDao.findForKey(requestDto.getPersonalId(),
					requestDto.getRequestDate(), 1);
			// 予定超過チェック
			if (scheduleOver.equals("")) {
				// 空欄の場合
				// 勤怠の実績チェック
				if (attendanceDto == null
						|| !workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_COMPLETE)) {
					// 実績が存在しない場合、-を返す
					dto.setResultsTime(mospParams.getName("Hyphen"));
				} else {
					// 実績が存在する場合、実績の残業時間を返す
					if (requestDto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_BEFORE) {
						dto.setResultsTime(getTimeDotFormat(attendanceDto.getOvertimeBefore()));
					} else {
						dto.setResultsTime(getTimeDotFormat(attendanceDto.getOvertimeAfter()));
					}
				}
			} else if (scheduleOver.equals(TimeConst.SCHEDULE_OVER_FLAG_ON)) {
				// 有りの場合
				// 勤怠の実績チェック
				if (attendanceDto == null
						|| !workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_COMPLETE)) {
					// 実績が存在しない場合、forの頭に返す
					continue;
				} else {
					// 区分によって検索する対象を変える
					if (requestDto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_BEFORE) {
						// 実績が存在する場合、予定と実績を比べ実績の方が高いデータのみ表示
						if (requestDto.getRequestTime() < attendanceDto.getOvertimeBefore()) {
							dto.setResultsTime(getTimeDotFormat(attendanceDto.getOvertimeBefore()));
						} else {
							// 予定の方が大きい場合、forの頭に返す
							continue;
						}
					} else {
						// 実績が存在する場合、予定と実績を比べ実績の方が高いデータのみ表示
						if (requestDto.getRequestTime() < attendanceDto.getOvertimeAfter()) {
							dto.setResultsTime(getTimeDotFormat(attendanceDto.getOvertimeAfter()));
						} else {
							// 予定の方が大きい場合、forの頭に返す
							continue;
						}
					}
				}
			} else if (scheduleOver.equals(TimeConst.SCHEDULE_OVER_FLAG_OFF)) {
				// 無しの場合
				// 勤怠の実績チェック
				if (attendanceDto == null
						|| !workflowDto.getWorkflowStatus().equals(PlatformConst.CODE_STATUS_COMPLETE)) {
					// 実績が存在しない場合、実績を-として表示する。
					dto.setResultsTime(mospParams.getName("Hyphen"));
				} else {
					// 区分によって検索する対象を変える
					if (requestDto.getOvertimeType() == TimeConst.CODE_OVERTIME_WORK_BEFORE) {
						// 実績が存在する場合、予定と実績を比べ予定の方が高いデータのみ表示
						if (requestDto.getRequestTime() > attendanceDto.getOvertimeBefore()) {
							dto.setResultsTime(getTimeDotFormat(attendanceDto.getOvertimeBefore()));
						} else {
							// 実績の方が大きい場合、forの頭に返す
							continue;
						}
					} else {
						// 実績が存在する場合、予定と実績を比べ予定の方が高いデータのみ表示
						if (requestDto.getRequestTime() > attendanceDto.getOvertimeAfter()) {
							dto.setResultsTime(getTimeDotFormat(attendanceDto.getOvertimeAfter()));
						} else {
							// 実績の方が大きい場合、forの頭に返す
							continue;
						}
					}
				}
			}
			// 申請日
			dto.setRequestDate(requestDto.getRequestDate());
			// 残業時間
			dto.setOvertimeType(requestDto.getOvertimeType());
			// 申請時間
			dto.setRequestTime(requestDto.getRequestTime());
			// 理由
			dto.setRequestReason(requestDto.getRequestReason());
			// 承認情報
			approvalInfoReference.setWorkflowInfo(dto, workflowDto);
			// ワークフロー
			dto.setWorkflow(requestDto.getWorkflow());
			// フラグの初期化
			flag = false;
			// 検索状態の処理によって取得する内容を変更
			if (TimeConst.CODE_SEARCH_STATUS_DRAFT.equals(workflowStatus)) {
				// 下書き
				if (PlatformConst.CODE_STATUS_DRAFT.equals(dto.getState())) {
					// ステータスが下書の場合、フラグを立てる
					flag = true;
				}
			} else if (TimeConst.CODE_SEARCH_STATUS_APPLY.equals(workflowStatus)) {
				// 未承認
				if (PlatformConst.CODE_STATUS_APPLY.equals(dto.getState())
						|| PlatformConst.CODE_STATUS_APPROVED.equals(dto.getState())) {
					// ステータスが未承認、1～9次済の場合、フラグを立てる
					flag = true;
				}
			} else if (TimeConst.CODE_SEARCH_STATUS_COMPLETE.equals(workflowStatus)) {
				// 承認済
				if (PlatformConst.CODE_STATUS_COMPLETE.equals(dto.getState())) {
					// ステータスが承認済の場合、フラグを立てる
					flag = true;
				}
			} else if (TimeConst.CODE_SEARCH_STATUS_REVERT.equals(workflowStatus)) {
				// 差戻
				if (PlatformConst.CODE_STATUS_REVERT.equals(dto.getState())
						|| PlatformConst.CODE_STATUS_CANCEL.equals(dto.getState())) {
					// ステータスが2～9次戻、差戻済、承認解除の場合、フラグを立てる
					flag = true;
				}
			} else {
				flag = true;
			}
			if (flag) {
				list.add(dto);
			}
		}
		return list;
	}
	
	/**
	 * 一覧時分形式で出力する。
	 * @param time 分
	 * @return HH.MM
	 */
	protected String getTimeDotFormat(int time) {
		// 時間文字列を取得
		return TimeUtility.getStringPeriodTime(mospParams, time);
	}
	
	@Override
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	@Override
	public void setScheduleOver(String scheduleOver) {
		this.scheduleOver = scheduleOver;
	}
	
	@Override
	public void setOvertimeType(String overtimeType) {
		this.overtimeType = overtimeType;
	}
	
	@Override
	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = CapsuleUtility.getDateClone(requestStartDate);
	}
	
	@Override
	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = CapsuleUtility.getDateClone(requestEndDate);
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
}
