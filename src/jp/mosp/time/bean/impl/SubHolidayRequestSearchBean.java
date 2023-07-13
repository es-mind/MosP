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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.SubHolidayRequestSearchBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestListDtoInterface;
import jp.mosp.time.dto.settings.impl.SubHolidayRequestListDto;

/**
 * 代休申請検索クラス。
 */
public class SubHolidayRequestSearchBean extends PlatformBean implements SubHolidayRequestSearchBeanInterface {
	
	/**
	 * 代休DAO
	 */
	protected SubHolidayRequestDaoInterface			subHolidayDao;
	
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
	 * 代休表示開始日。
	 */
	private Date									requestStartDate;
	
	/**
	 * 代休表示終了日。
	 */
	private Date									requestEndDate;
	
	/**
	 * 表示開始日。
	 */
	private Date									workStartDate;
	
	/**
	 * 表示終了日。
	 */
	private Date									workEndDate;
	
	/**
	 * 状態。
	 */
	private String									workflowStatus;
	
	
	/**
	 * コンストラクタ。
	 */
	public SubHolidayRequestSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		subHolidayDao = createDaoInstance(SubHolidayRequestDaoInterface.class);
		approvalInfoReference = createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
	}
	
	@Override
	public List<SubHolidayRequestListDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = subHolidayDao.getParamsMap();
		param.put("personalId", personalId);
		param.put("requestStartDate", requestStartDate);
		param.put("requestEndDate", requestEndDate);
		param.put("workStartDate", workStartDate);
		param.put("workEndDate", workEndDate);
		param.put("workflowStatus", workflowStatus);
		// Bean初期化
		workflowReference = createBeanInstance(WorkflowReferenceBeanInterface.class);
		// 検索条件から休暇申請マスタリストを取得
		List<SubHolidayRequestDtoInterface> requestList = subHolidayDao.findForSearch(param);
		// 代休申請一覧DTOの準備
		List<SubHolidayRequestListDtoInterface> list = new ArrayList<SubHolidayRequestListDtoInterface>();
		// addLsitに設定するためのフラグ
		boolean flag = false;
		// list作成
		for (int i = 0; i < requestList.size(); i++) {
			// 代休申請一覧DTOの初期化
			SubHolidayRequestListDtoInterface dto = new SubHolidayRequestListDto();
			// ワークフローの設定
			WorkflowDtoInterface workflowDto = workflowReference
				.getLatestWorkflowInfo(requestList.get(i).getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// レコード識別ID
			dto.setTmdSubHolidayRequestId(requestList.get(i).getTmdSubHolidayRequestId());
			// 代休日
			dto.setRequestDate(requestList.get(i).getRequestDate());
			// 代休日範囲
			dto.setSubHolidayRange(String.valueOf(requestList.get(i).getHolidayRange()));
			// 出勤日
			dto.setWorkDate(requestList.get(i).getWorkDate());
			// 出勤日範囲
			dto.setWorkDateHolidayType(requestList.get(i).getWorkDateSubHolidayType());
			// ワークフロー
			dto.setWorkflow(requestList.get(i).getWorkflow());
			// 承認状況
			approvalInfoReference.setWorkflowInfo(dto, workflowDto);
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
	
	@Override
	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = getDateClone(requestStartDate);
	}
	
	@Override
	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = getDateClone(requestEndDate);
	}
	
	@Override
	public void setWorkStartDate(Date workStartDate) {
		this.workStartDate = getDateClone(workStartDate);
	}
	
	@Override
	public void setWorkEndDate(Date workEndDate) {
		this.workEndDate = getDateClone(workEndDate);
	}
	
	@Override
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
}
