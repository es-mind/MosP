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
import jp.mosp.time.dto.settings.SubHolidayRequestListDtoInterface;

/**
 * 代休申請一覧DTO
 */

public class SubHolidayRequestListDto extends BaseDto implements SubHolidayRequestListDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -448637847464921847L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdSubHolidayRequestId;
	/**
	 * 代休日。
	 */
	private Date				requestDate;
	/**
	 * 代休日範囲。
	 */
	private String				subHolidayRange;
	/**
	 * 出勤日休日区分。
	 */
	private int					workDateHolidayType;
	/**
	 * 出勤日。
	 */
	private Date				workDate;
	/**
	 * 出勤日範囲。
	 */
	private String				workDateRange;
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
	 * ワークフロー番号。
	 */
	private long				workflow;
	
	
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
	public String getApproverName() {
		return approverName;
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
	public void setApproverName(String approverName) {
		this.approverName = approverName;
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
	public String getSubHolidayRange() {
		return subHolidayRange;
	}
	
	@Override
	public int getWorkDateHolidayType() {
		return workDateHolidayType;
	}
	
	@Override
	public String getWorkDateRange() {
		return workDateRange;
	}
	
	@Override
	public void setSubHolidayRange(String subHolidayRange) {
		this.subHolidayRange = subHolidayRange;
	}
	
	@Override
	public void setWorkDateHolidayType(int workDateHolidayType) {
		this.workDateHolidayType = workDateHolidayType;
	}
	
	@Override
	public void setWorkDateRange(String workDateRange) {
		this.workDateRange = workDateRange;
	}
	
	@Override
	public long getWorkflow() {
		return workflow;
	}
	
	@Override
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
}
