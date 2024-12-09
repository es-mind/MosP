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
/**
 * 
 */
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.GoOutDtoInterface;

/**
 * 外出データDTO
 */
public class TmdGoOutDto extends BaseDto implements GoOutDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7455238323919390128L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdGoOutId;
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
	 * 外出区分。
	 */
	private int					goOutType;
	/**
	 * 外出回数。
	 */
	private int					timesGoOut;
	/**
	 * 外出開始時刻。
	 */
	private Date				goOutStart;
	/**
	 * 外出終了時刻。
	 */
	private Date				goOutEnd;
	/**
	 * 外出時間。
	 */
	private int					goOutTime;
	
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getGoOutEnd() {
		return getDateClone(goOutEnd);
	}
	
	@Override
	public Date getGoOutStart() {
		return getDateClone(goOutStart);
	}
	
	@Override
	public int getGoOutTime() {
		return goOutTime;
	}
	
	@Override
	public int getGoOutType() {
		return goOutType;
	}
	
	@Override
	public int getTimesGoOut() {
		return timesGoOut;
	}
	
	@Override
	public int getTimesWork() {
		return timesWork;
	}
	
	@Override
	public long getTmdGoOutId() {
		return tmdGoOutId;
	}
	
	@Override
	public Date getWorkDate() {
		return getDateClone(workDate);
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setGoOutEnd(Date goOutEnd) {
		this.goOutEnd = getDateClone(goOutEnd);
	}
	
	@Override
	public void setGoOutStart(Date goOutStart) {
		this.goOutStart = getDateClone(goOutStart);
	}
	
	@Override
	public void setGoOutTime(int goOutTime) {
		this.goOutTime = goOutTime;
	}
	
	@Override
	public void setGoOutType(int goOutType) {
		this.goOutType = goOutType;
	}
	
	@Override
	public void setTimesGoOut(int timesGoOut) {
		this.timesGoOut = timesGoOut;
	}
	
	@Override
	public void setTimesWork(int timesWork) {
		this.timesWork = timesWork;
	}
	
	@Override
	public void setTmdGoOutId(long tmdGoOutId) {
		this.tmdGoOutId = tmdGoOutId;
	}
	
	@Override
	public void setWorkDate(Date workDate) {
		this.workDate = getDateClone(workDate);
	}
	
}
