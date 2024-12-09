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
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.time.bean.ApprovalInfoReferenceBeanInterface;
import jp.mosp.time.bean.HolidayRequestSearchBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.HolidayRequestListDtoInterface;
import jp.mosp.time.dto.settings.impl.HolidayRequestListDto;

/**
 * 休暇申請検索クラス。
 */
public class HolidayRequestSearchBean extends PlatformBean implements HolidayRequestSearchBeanInterface {
	
	/**
	 * 休暇申請DAO。<br>
	 */
	protected HolidayRequestDaoInterface			holidayRequestDao;
	
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
	 * 休暇種別1。
	 */
	private String									holidayType1;
	
	/**
	 * 休暇種別2。
	 */
	private String									holidayType2;
	
	/**
	 * 休暇範囲。
	 */
	private String									holidayLength;
	
	/**
	 * 状態。
	 */
	private String									workflowStatus;
	
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
	public HolidayRequestSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		holidayRequestDao = createDaoInstance(HolidayRequestDaoInterface.class);
		// Beanを準備
		workflowReference = createBeanInstance(WorkflowReferenceBeanInterface.class);
		approvalInfoReference = createBeanInstance(ApprovalInfoReferenceBeanInterface.class);
	}
	
	@Override
	public List<HolidayRequestListDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = holidayRequestDao.getParamsMap();
		param.put("personalId", personalId);
		param.put("holidayType1", holidayType1);
		param.put("holidayType2", holidayType2);
		param.put("holidayLength", holidayLength);
		param.put("workflowStatus", workflowStatus);
		param.put("requestStartDate", requestStartDate);
		param.put("requestEndDate", requestEndDate);
		// 検索条件から休暇申請マスタリストを取得
		List<HolidayRequestDtoInterface> requestList = holidayRequestDao.findForSearch(param);
		// 休暇申請一覧DTOの準備
		List<HolidayRequestListDtoInterface> list = new ArrayList<HolidayRequestListDtoInterface>();
		// addLsitに設定するためのフラグ
		boolean flag = false;
		// list作成
		for (int i = 0; i < requestList.size(); i++) {
			// 休暇申請一覧DTOの初期化
			HolidayRequestListDtoInterface dto = new HolidayRequestListDto();
			// ワークフローの設定
			WorkflowDtoInterface workflowDto = workflowReference
				.getLatestWorkflowInfo(requestList.get(i).getWorkflow());
			if (workflowDto == null) {
				continue;
			}
			// レコード識別ID
			dto.setTmdHolidayRequestId(requestList.get(i).getTmdHolidayRequestId());
			// 申請開始日
			dto.setRequestStartDate(requestList.get(i).getRequestStartDate());
			// 申請終了日
			dto.setRequestEndDate(requestList.get(i).getRequestEndDate());
			// 休暇種別1
			dto.setHolidayType1(requestList.get(i).getHolidayType1());
			// 休暇種別2
			dto.setHolidayType2(requestList.get(i).getHolidayType2());
			// 休暇範囲
			dto.setHolidayRange(requestList.get(i).getHolidayRange());
			// 時休開始時刻
			dto.setStartTime(requestList.get(i).getStartTime());
			// 時休終了時刻
			dto.setEndTime(requestList.get(i).getEndTime());
			// 理由
			dto.setRequestReason(requestList.get(i).getRequestReason());
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
	public void setHolidayType1(String holidayType1) {
		this.holidayType1 = holidayType1;
	}
	
	@Override
	public void setHolidayType2(String holidayType2) {
		this.holidayType2 = holidayType2;
	}
	
	@Override
	public void setHolidayLength(String holidayLength) {
		this.holidayLength = holidayLength;
	}
	
	@Override
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
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
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
}
