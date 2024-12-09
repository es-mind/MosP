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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;

/**
 * 勤務形態変更申請DTOクラス。
 */
public class TmdWorkTypeChangeRequestDto extends BaseDto implements WorkTypeChangeRequestDtoInterface {
	
	private static final long	serialVersionUID	= -1623518599237699480L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdWorkTypeChangeRequestId;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 出勤日。
	 */
	private Date				requestDate;
	
	/**
	 * 勤務回数。
	 */
	private int					timesWork;
	
	/**
	 * 勤務形態コード。
	 */
	private String				workTypeCode;
	
	/**
	 * 理由。
	 */
	private String				requestReason;
	
	/**
	 * ワークフロー番号。
	 */
	private long				workflow;
	
	
	@Override
	public long getTmdWorkTypeChangeRequestId() {
		return tmdWorkTypeChangeRequestId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getRequestDate() {
		return getDateClone(requestDate);
	}
	
	@Override
	public int getTimesWork() {
		return timesWork;
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
	public long getWorkflow() {
		return workflow;
	}
	
	@Override
	public void setTmdWorkTypeChangeRequestId(long tmdWorkTypeChangeRequestId) {
		this.tmdWorkTypeChangeRequestId = tmdWorkTypeChangeRequestId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setRequestDate(Date requestDate) {
		this.requestDate = getDateClone(requestDate);
	}
	
	@Override
	public void setTimesWork(int timesWork) {
		this.timesWork = timesWork;
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
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
}
