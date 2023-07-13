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
import jp.mosp.time.dto.settings.RestDtoInterface;

/**
 * 休憩データDTO
 */
public class TmdRestDto extends BaseDto implements RestDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2640982675285314250L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdRestId;
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
	 * 休憩回数。
	 */
	private int					rest;
	/**
	 * 休憩開始時刻。
	 */
	private Date				restStart;
	/**
	 * 休憩終了時刻。
	 */
	private Date				restEnd;
	/**
	 * 休憩時間。
	 */
	private int					restTime;
	
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public int getRest() {
		return rest;
	}
	
	@Override
	public Date getRestEnd() {
		return getDateClone(restEnd);
	}
	
	@Override
	public Date getRestStart() {
		return getDateClone(restStart);
	}
	
	@Override
	public int getRestTime() {
		return restTime;
	}
	
	@Override
	public long getTmdRestId() {
		return tmdRestId;
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
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setRest(int rest) {
		this.rest = rest;
	}
	
	@Override
	public void setRestEnd(Date restEnd) {
		this.restEnd = getDateClone(restEnd);
	}
	
	@Override
	public void setRestStart(Date restStart) {
		this.restStart = getDateClone(restStart);
	}
	
	@Override
	public void setRestTime(int restTime) {
		this.restTime = restTime;
	}
	
	@Override
	public void setTmdRestId(long tmdRestId) {
		this.tmdRestId = tmdRestId;
	}
	
	@Override
	public void setWorkDate(Date workDate) {
		this.workDate = getDateClone(workDate);
	}
	
	@Override
	public void setTimesWork(int timesWork) {
		this.timesWork = timesWork;
	}
	
}
