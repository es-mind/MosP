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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;

/**
 * 残業申請DTO
 */
public class TmdOvertimeRequestDto extends BaseDto implements OvertimeRequestDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 6209390758266163227L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdOvertimeRequestId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 申請日。
	 */
	private Date				requestDate;
	/**
	 * 勤務回数。
	 */
	private int					timesWork;
	/**
	 * 申請時間。
	 */
	private int					requestTime;
	/**
	 * 残業区分。
	 */
	private int					overtimeType;
	/**
	 * 理由。
	 */
	private String				requestReason;
	/**
	 * ワークフロー番号。
	 */
	private long				workflow;
	
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public int getOvertimeType() {
		return overtimeType;
	}
	
	@Override
	public Date getRequestDate() {
		return getDateClone(requestDate);
	}
	
	@Override
	public String getRequestReason() {
		return requestReason;
	}
	
	@Override
	public int getRequestTime() {
		return requestTime;
	}
	
	@Override
	public long getTmdOvertimeRequestId() {
		return tmdOvertimeRequestId;
	}
	
	@Override
	public long getWorkflow() {
		return workflow;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setOvertimeType(int overtimeType) {
		this.overtimeType = overtimeType;
	}
	
	@Override
	public void setRequestDate(Date requestDate) {
		this.requestDate = getDateClone(requestDate);
	}
	
	@Override
	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}
	
	@Override
	public void setRequestTime(int requestTime) {
		this.requestTime = requestTime;
	}
	
	@Override
	public void setTmdOvertimeRequestId(long tmdOvertimeRequestId) {
		this.tmdOvertimeRequestId = tmdOvertimeRequestId;
	}
	
	@Override
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
	@Override
	public int getTimesWork() {
		return timesWork;
	}
	
	@Override
	public void setTimesWork(int timesWork) {
		this.timesWork = timesWork;
	}
	
}
