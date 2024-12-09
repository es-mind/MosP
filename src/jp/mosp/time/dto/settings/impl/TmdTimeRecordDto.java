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
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;

/**
 * 打刻データDTO
 */
public class TmdTimeRecordDto extends BaseDto implements TimeRecordDtoInterface {
	
	private static final long	serialVersionUID	= -829822809693706909L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdTimeRecordId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 勤務日。
	 */
	private Date				workDate;
	/**
	 * 勤務回数。
	 */
	private int					timesWork;
	/**
	 * 打刻区分。
	 */
	private String				recordType;
	/**
	 * 打刻時刻。
	 */
	private Date				recordTime;
	
	
	@Override
	public long getTmdTimeRecordId() {
		return tmdTimeRecordId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getWorkDate() {
		return getDateClone(workDate);
	}
	
	@Override
	public int getTimesWork() {
		return timesWork;
	}
	
	@Override
	public String getRecordType() {
		return recordType;
	}
	
	@Override
	public Date getRecordTime() {
		return getDateClone(recordTime);
	}
	
	@Override
	public void setTmdTimeRecordId(long tmdTimeRecordId) {
		this.tmdTimeRecordId = tmdTimeRecordId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setWorkDate(Date workDate) {
		this.workDate = getDateClone(workDate);
	}
	
	@Override
	public void setTimesWork(int timesWork) {
		this.timesWork = timesWork;
	}
	
	@Override
	public void setRecordType(String recordType) {
		this.recordType = recordType;
	}
	
	@Override
	public void setRecordTime(Date recordTime) {
		this.recordTime = getDateClone(recordTime);
	}
	
}
