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
import jp.mosp.time.dto.settings.WorkOnHolidayRequestListDtoInterface;

/**
 * 休日出勤一覧DTO
 */
public class WorkOnHolidayRequestListDto extends BaseDto implements WorkOnHolidayRequestListDtoInterface {
	
	private static final long	serialVersionUID	= -1542854401511065150L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdWorkOnHolidayRequestId;
	/**
	 * 出勤日。
	 */
	private Date				requestDate;
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
	 * 振替日。
	 */
	private Date				substituteDate;
	/**
	 * 振替範囲。
	 */
	private int					substituteRange;
	/**
	 * 振替申請。（区分）
	 */
	private int					substitute;
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
	public Date getSubstituteDate() {
		return getDateClone(substituteDate);
	}
	
	@Override
	public int getSubstituteRange() {
		return substituteRange;
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
	public void setSubstituteDate(Date substituteDate) {
		this.substituteDate = getDateClone(substituteDate);
	}
	
	@Override
	public void setSubstituteRange(int substituteRange) {
		this.substituteRange = substituteRange;
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
	public Date getEndTime() {
		return getDateClone(endTime);
	}
	
	@Override
	public Date getStartTime() {
		return getDateClone(startTime);
	}
	
	@Override
	public void setEndTime(Date endTime) {
		this.endTime = getDateClone(endTime);
	}
	
	@Override
	public void setStartTime(Date startTime) {
		this.startTime = getDateClone(startTime);
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
