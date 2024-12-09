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
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;

/**
 * 代休データDTO。
 */
public class TmdSubHolidayDto extends BaseDto implements SubHolidayDtoInterface {
	
	private static final long	serialVersionUID	= 5176118943191171099L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdSubHolidayId;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
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
	private int					subHolidayType;
	
	/**
	 * 代休日数。
	 */
	private double				subHolidayDays;
	
	/**
	 * 移行フラグ。
	 */
	private int					transitionFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdSubHolidayDto() {
		// 処理無し
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public double getSubHolidayDays() {
		return subHolidayDays;
	}
	
	@Override
	public int getSubHolidayType() {
		return subHolidayType;
	}
	
	@Override
	public int getTimesWork() {
		return timesWork;
	}
	
	@Override
	public long getTmdSubHolidayId() {
		return tmdSubHolidayId;
	}
	
	@Override
	public Date getWorkDate() {
		return getDateClone(workDate);
	}
	
	@Override
	public int getTransitionFlag() {
		return transitionFlag;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setSubHolidayDays(double subHolidayDays) {
		this.subHolidayDays = subHolidayDays;
	}
	
	@Override
	public void setSubHolidayType(int subHolidayType) {
		this.subHolidayType = subHolidayType;
	}
	
	@Override
	public void setTimesWork(int timesWork) {
		this.timesWork = timesWork;
	}
	
	@Override
	public void setTmdSubHolidayId(long tmdSubHolidayId) {
		this.tmdSubHolidayId = tmdSubHolidayId;
	}
	
	@Override
	public void setWorkDate(Date workDate) {
		this.workDate = getDateClone(workDate);
	}
	
	@Override
	public void setTransitionFlag(int transitionFlag) {
		this.transitionFlag = transitionFlag;
	}
}
