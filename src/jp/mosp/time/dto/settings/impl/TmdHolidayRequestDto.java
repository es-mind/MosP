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
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;

/**
 * 休暇申請情報。<br>
 */
public class TmdHolidayRequestDto extends BaseDto implements HolidayRequestDtoInterface {
	
	private static final long	serialVersionUID	= -8708564307800682059L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdHolidayRequestId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
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
	 * 休暇取得日。
	 */
	private Date				holidayAcquisitionDate;
	/**
	 * 使用日数。
	 */
	private double				useDay;
	/**
	 * 使用時間数。
	 */
	private int					useHour;
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
	public int getHolidayRange() {
		return holidayRange;
	}
	
	@Override
	public int getHolidayType1() {
		return holidayType1;
	}
	
	@Override
	public String getHolidayType2() {
		return holidayType2;
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
	public Date getStartTime() {
		return getDateClone(startTime);
	}
	
	@Override
	public long getTmdHolidayRequestId() {
		return tmdHolidayRequestId;
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
	public void setHolidayRange(int holidayRange) {
		this.holidayRange = holidayRange;
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
	public void setRequestStartDate(Date requestDate) {
		requestStartDate = getDateClone(requestDate);
	}
	
	@Override
	public void setRequestReason(String requestReason) {
		this.requestReason = requestReason;
	}
	
	@Override
	public void setStartTime(Date startTime) {
		this.startTime = getDateClone(startTime);
	}
	
	@Override
	public void setTmdHolidayRequestId(long tmdHolidayRequestId) {
		this.tmdHolidayRequestId = tmdHolidayRequestId;
	}
	
	@Override
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
	@Override
	public Date getHolidayAcquisitionDate() {
		return getDateClone(holidayAcquisitionDate);
	}
	
	@Override
	public void setHolidayAcquisitionDate(Date holidayAcquisitionDate) {
		this.holidayAcquisitionDate = getDateClone(holidayAcquisitionDate);
	}
	
	@Override
	public Date getRequestEndDate() {
		return getDateClone(requestEndDate);
	}
	
	@Override
	public void setRequestEndDate(Date requestEndDate) {
		this.requestEndDate = getDateClone(requestEndDate);
	}
	
	@Override
	public double getUseDay() {
		return useDay;
	}
	
	@Override
	public int getUseHour() {
		return useHour;
	}
	
	@Override
	public void setUseDay(double useDay) {
		this.useDay = useDay;
	}
	
	@Override
	public void setUseHour(int useHour) {
		this.useHour = useHour;
	}
	
	@Override
	public long getRecordId() {
		return getTmdHolidayRequestId();
	}
	
}
