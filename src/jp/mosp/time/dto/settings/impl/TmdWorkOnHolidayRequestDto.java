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
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;

/**
 * 振出・休出申請DTO。<br>
 */
public class TmdWorkOnHolidayRequestDto extends BaseDto implements WorkOnHolidayRequestDtoInterface {
	
	private static final long	serialVersionUID	= -6948437067693731485L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdWorkOnHolidayRequestId;
	
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
	 * 休出種別。
	 */
	private String				workOnHolidayType;
	
	/**
	 * 振替申請。
	 */
	private int					substitute;
	
	/**
	 * 勤務形態コード。
	 */
	private String				workTypeCode;
	
	/**
	 * 出勤予定時刻。
	 */
	private Date				startTime;
	
	/**
	 * 退勤予定時刻。
	 */
	private Date				endTime;
	
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
	public Date getEndTime() {
		return getDateClone(endTime);
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
	public String getWorkTypeCode() {
		return workTypeCode;
	}
	
	@Override
	public Date getStartTime() {
		return getDateClone(startTime);
	}
	
	@Override
	public int getSubstitute() {
		return substitute;
	}
	
	@Override
	public long getTmdWorkOnHolidayRequestId() {
		return tmdWorkOnHolidayRequestId;
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
	public void setEndTime(Date endTime) {
		this.endTime = getDateClone(endTime);
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
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	@Override
	public void setStartTime(Date startTime) {
		this.startTime = getDateClone(startTime);
	}
	
	@Override
	public void setSubstitute(int substitute) {
		this.substitute = substitute;
	}
	
	@Override
	public void setTmdWorkOnHolidayRequestId(long tmdWorkOnHolidayRequestId) {
		this.tmdWorkOnHolidayRequestId = tmdWorkOnHolidayRequestId;
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
	public String getWorkOnHolidayType() {
		return workOnHolidayType;
	}
	
	@Override
	public void setWorkOnHolidayType(String workOnHolidayType) {
		this.workOnHolidayType = workOnHolidayType;
	}
	
}
