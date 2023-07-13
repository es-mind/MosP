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
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;

/**
 * 
 */
public class TmdDifferenceRequestDto extends BaseDto implements DifferenceRequestDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -699997587171436661L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdDifferenceRequestId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 時差出勤日。
	 */
	private Date				requestDate;
	/**
	 * 勤務回数。
	 */
	private int					timesWork;
	/**
	 * 時差出勤区分。
	 */
	private String				differenceType;
	/**
	 * 勤務形態コード。
	 */
	private String				workTypeCode;
	/**
	 * 開始日。
	 */
	private int					startDate;
	/**
	 * 時差出勤開始時刻。
	 */
	private Date				requestStart;
	/**
	 * 時差出勤終了時刻。
	 */
	private Date				requestEnd;
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
	public Date getRequestDate() {
		return getDateClone(requestDate);
	}
	
	@Override
	public String getRequestReason() {
		return requestReason;
	}
	
	@Override
	public long getTmdDifferenceRequestId() {
		return tmdDifferenceRequestId;
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
	public void setRequestDate(Date requestDate) {
		this.requestDate = getDateClone(requestDate);
	}
	
	@Override
	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}
	
	@Override
	public void setTmdDifferenceRequestId(long tmdDifferenceRequestId) {
		this.tmdDifferenceRequestId = tmdDifferenceRequestId;
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
	
	@Override
	public String getDifferenceType() {
		return differenceType;
	}
	
	@Override
	public void setDifferenceType(String differenceType) {
		this.differenceType = differenceType;
	}
	
	@Override
	public String getWorkTypeCode() {
		return workTypeCode;
	}
	
	@Override
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	@Override
	public Date getRequestEnd() {
		return getDateClone(requestEnd);
	}
	
	@Override
	public Date getRequestStart() {
		return getDateClone(requestStart);
	}
	
	@Override
	public int getStartDate() {
		return startDate;
	}
	
	@Override
	public void setRequestEnd(Date requestEnd) {
		this.requestEnd = getDateClone(requestEnd);
	}
	
	@Override
	public void setRequestStart(Date requestStart) {
		this.requestStart = getDateClone(requestStart);
	}
	
	@Override
	public void setStartDate(int startDate) {
		this.startDate = startDate;
	}
}
