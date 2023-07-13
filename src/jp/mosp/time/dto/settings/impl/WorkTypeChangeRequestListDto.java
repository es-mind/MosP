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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestListDtoInterface;

/**
 * 勤務形態変更申請一覧DTOクラス。
 */
public class WorkTypeChangeRequestListDto extends BaseDto implements WorkTypeChangeRequestListDtoInterface {
	
	private static final long	serialVersionUID	= 4544777138214246109L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdWorkTypeChangeRequestId;
	
	/**
	 * 出勤日。
	 */
	private Date				requestDate;
	
	/**
	 * 勤務形態コード。
	 */
	private String				workTypeCode;
	
	/**
	 * 理由。
	 */
	private String				requestReason;
	
	/**
	 * 承認段階。
	 */
	private int					stage;
	
	/**
	 * 承認状況。
	 */
	private String				state;
	
	/**
	 * 承認者。
	 */
	private String				approverName;
	
	/**
	 * ワークフロー。
	 */
	private long				workflow;
	
	
	@Override
	public long getTmdWorkTypeChangeRequestId() {
		return tmdWorkTypeChangeRequestId;
	}
	
	@Override
	public Date getRequestDate() {
		return getDateClone(requestDate);
	}
	
	@Override
	public String getWorkTypeCode() {
		return workTypeCode;
	}
	
	@Override
	public String getRequestReason() {
		return requestReason;
	}
	
	@Override
	public int getStage() {
		return stage;
	}
	
	@Override
	public String getState() {
		return state;
	}
	
	@Override
	public String getApproverName() {
		return approverName;
	}
	
	@Override
	public long getWorkflow() {
		return workflow;
	}
	
	@Override
	public void setTmdWorkTypeChangeRequestId(long tmdWorkTypeChangeRequestId) {
		this.tmdWorkTypeChangeRequestId = tmdWorkTypeChangeRequestId;
	}
	
	@Override
	public void setRequestDate(Date requestDate) {
		this.requestDate = getDateClone(requestDate);
	}
	
	@Override
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	@Override
	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}
	
	@Override
	public void setStage(int stage) {
		this.stage = stage;
	}
	
	@Override
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	
	@Override
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
}
