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
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;

/**
 * 代休申請DTO
 */
public class TmdSubHolidayRequestDto extends BaseDto implements SubHolidayRequestDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8074101324784272846L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdSubHolidayRequestId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 代休日。
	 */
	private Date				requestDate;
	/**
	 * 休暇範囲。
	 */
	private int					holidayRange;
	/**
	 * 出勤日。
	 */
	private Date				workDate;
	/**
	 * 勤務回数。
	 */
	private int					timesWork;
	/**
	 * 代休種別。
	 */
	private int					workDateSubHolidayType;
	/**
	 * ワークフロー番号。
	 */
	private long				workflow;
	
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public int getHolidayRange() {
		return holidayRange;
	}
	
	@Override
	public Date getRequestDate() {
		return getDateClone(requestDate);
	}
	
	@Override
	public long getTmdSubHolidayRequestId() {
		return tmdSubHolidayRequestId;
	}
	
	@Override
	public Date getWorkDate() {
		return getDateClone(workDate);
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
	public void setHolidayRange(int holidayRange) {
		this.holidayRange = holidayRange;
	}
	
	@Override
	public void setRequestDate(Date requestDate) {
		this.requestDate = getDateClone(requestDate);
	}
	
	@Override
	public void setTmdSubHolidayRequestId(long tmdSubHolidayRequestId) {
		this.tmdSubHolidayRequestId = tmdSubHolidayRequestId;
	}
	
	@Override
	public void setWorkDate(Date workDate) {
		this.workDate = getDateClone(workDate);
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
	public int getWorkDateSubHolidayType() {
		return workDateSubHolidayType;
	}
	
	@Override
	public void setWorkDateSubHolidayType(int workDateSubHolidayType) {
		this.workDateSubHolidayType = workDateSubHolidayType;
	}
	
}
