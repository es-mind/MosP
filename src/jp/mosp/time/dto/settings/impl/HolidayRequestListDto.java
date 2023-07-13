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
import jp.mosp.time.dto.settings.HolidayRequestListDtoInterface;

/**
 * 休暇申請一覧DTO
 */
public class HolidayRequestListDto extends BaseDto implements HolidayRequestListDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 4026626608961218538L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdHolidayRequestId;
	/**
	 * 申請開始日。
	 */
	private Date				requestStartDate;
	/**
	 * 申請終了日。
	 */
	private Date				requestEndDate;
	/**
	 * 休暇種別1。
	 */
	private int					holidayType1;
	/**
	 * 休暇種別2。
	 */
	private String				holidayType2;
	/**
	 * 休暇範囲。
	 */
	private int					holidayRange;
	/**
	 * 時休開始時刻。
	 */
	private Date				startTime;
	/**
	 * 時休終了時刻。
	 */
	private Date				endTime;
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
	 * ワークフロー番号。
	 */
	private long				workflow;
	
	
	@Override
	public int getHolidayType1() {
		return holidayType1;
	}
	
	@Override
	public String getHolidayType2() {
		return holidayType2;
	}
	
	@Override
	public int getHolidayRange() {
		return holidayRange;
	}
	
	@Override
	public Date getRequestStartDate() {
		return getDateClone(requestStartDate);
	}
	
	@Override
	public String getRequestReason() {
		return requestReason;
	}
	
	@Override
	public long getTmdHolidayRequestId() {
		return tmdHolidayRequestId;
	}
	
	@Override
	public void setHolidayType1(int holidayType1) {
		this.holidayType1 = holidayType1;
	}
	
	@Override
	public void setHolidayType2(String holidayType2) {
		this.holidayType2 = holidayType2;
	}
	
	@Override
	public void setHolidayRange(int holidayRange) {
		this.holidayRange = holidayRange;
	}
	
	@Override
	public void setRequestStartDate(Date requestStartDate) {
		this.requestStartDate = getDateClone(requestStartDate);
	}
	
	@Override
	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}
	
	@Override
	public void setTmdHolidayRequestId(long tmdHolidayRequestId) {
		this.tmdHolidayRequestId = tmdHolidayRequestId;
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
	
	@Override
	public Date getRequestEndDate() {
		return getDateClone(requestEndDate);
	}
	
	@Override
	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = getDateClone(requestEndDate);
	}
}
