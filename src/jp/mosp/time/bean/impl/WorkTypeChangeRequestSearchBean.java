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
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.workflow.WorkflowIntegrateBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.WorkTypeChangeRequestSearchBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.WorkTypeChangeRequestDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestListDtoInterface;
import jp.mosp.time.dto.settings.impl.WorkTypeChangeRequestListDto;

/**
 * 勤務形態変更申請検索クラス。
 */
public class WorkTypeChangeRequestSearchBean extends TimeBean implements WorkTypeChangeRequestSearchBeanInterface {
	
	/**
	 * 勤務形態変更申請DAOクラス。<br>
	 */
	protected WorkTypeChangeRequestDaoInterface		dao;
	
	/**
	 * ワークフロー統括クラス。<br>
	 */
	protected WorkflowIntegrateBeanInterface		workflowIntegrate;
	
	/**
	 * 承認情報参照クラス。<br>
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
	 * 勤務形態コード。
	 */
	private String									workTypeCode;
	
	/**
	 * 表示開始日。
	 */
	private Date									requestStartDate;
	
	/**
	 * 表示終了日。
	 */
	private Date									requestEndDate;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public WorkTypeChangeRequestSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(WorkTypeChangeRequestDaoInterface.class);
		workflowIntegrate = createBeanInstance(WorkflowIntegrateBeanInterface.class);
		approvalInfoReference = createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
	}
	
	@Override
	public List<WorkTypeChangeRequestListDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = dao.getParamsMap();
		param.put("personalId", personalId);
		param.put("workflowStatus", workflowStatus);
		param.put("workTypeCode", workTypeCode);
		param.put("requestStartDate", requestStartDate);
		param.put("requestEndDate", requestEndDate);
		// 検索条件から勤務形態変更申請リストを取得
		List<WorkTypeChangeRequestDtoInterface> requestList = dao.findForSearch(param);
		// 勤務形態変更申請一覧の準備
		List<WorkTypeChangeRequestListDtoInterface> list = new ArrayList<WorkTypeChangeRequestListDtoInterface>();
		for (WorkTypeChangeRequestDtoInterface workTypeChangeRequestDto : requestList) {
			WorkTypeChangeRequestListDtoInterface dto = new WorkTypeChangeRequestListDto();
			WorkflowDtoInterface workflowDto = workflowIntegrate
				.getLatestWorkflowInfo(workTypeChangeRequestDto.getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			if (!isStatus(workflowDto)) {
				continue;
			}
			// レコード識別ID
			dto.setTmdWorkTypeChangeRequestId(workTypeChangeRequestDto.getTmdWorkTypeChangeRequestId());
			// 出勤日
			dto.setRequestDate(workTypeChangeRequestDto.getRequestDate());
			// 勤務形態コード
			dto.setWorkTypeCode(workTypeChangeRequestDto.getWorkTypeCode());
			// 理由
			dto.setRequestReason(workTypeChangeRequestDto.getRequestReason());
			// ワークフロー
			dto.setWorkflow(workTypeChangeRequestDto.getWorkflow());
			// 承認状況
			approvalInfoReference.setWorkflowInfo(dto, workflowDto);
			// 追加
			list.add(dto);
		}
		return list;
	}
	
	/**
	 * ワークフローの状況を確認する。<br>
	 * @param dto 対象DTO
	 * @return 検索条件に一致する場合true、そうでない場合false
	 */
	protected boolean isStatus(WorkflowDtoInterface dto) {
		if (workflowStatus.isEmpty()) {
			return true;
		} else if (TimeConst.CODE_SEARCH_STATUS_DRAFT.equals(workflowStatus)) {
			// 下書の場合
			return workflowIntegrate.isDraft(dto);
		} else if (TimeConst.CODE_SEARCH_STATUS_APPLY.equals(workflowStatus)) {
			// 未承認の場合
			return PlatformConst.CODE_STATUS_APPLY.equals(dto.getWorkflowStatus())
					|| PlatformConst.CODE_STATUS_APPROVED.equals(dto.getWorkflowStatus());
		} else if (TimeConst.CODE_SEARCH_STATUS_COMPLETE.equals(workflowStatus)) {
			// 承認済の場合
			return workflowIntegrate.isCompleted(dto);
		} else if (TimeConst.CODE_SEARCH_STATUS_REVERT.equals(workflowStatus)) {
			// 差戻の場合
			return PlatformConst.CODE_STATUS_REVERT.equals(dto.getWorkflowStatus())
					|| PlatformConst.CODE_STATUS_CANCEL.equals(dto.getWorkflowStatus());
		}
		return false;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	@Override
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	@Override
	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = getDateClone(requestStartDate);
	}
	
	@Override
	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = getDateClone(requestEndDate);
	}
	
}
