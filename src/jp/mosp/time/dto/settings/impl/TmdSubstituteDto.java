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
import jp.mosp.time.dto.settings.SubstituteDtoInterface;

/**
 * 振替休日データDTO。<br>
 */
public class TmdSubstituteDto extends BaseDto implements SubstituteDtoInterface {
	
	private static final long	serialVersionUID	= -9048225489525887964L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdSubstituteId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 振替日。
	 */
	private Date				substituteDate;
	/**
	 * 振替種別。
	 */
	private String				substituteType;
	/**
	 * 振替範囲。
	 */
	private int					substituteRange;
	/**
	 * 出勤日。
	 */
	private Date				workDate;
	/**
	 * 勤務回数。
	 */
	private int					timesWork;
	/**
	 * ワークフロー番号。
	 */
	private long				workflow;
	
	/**
	 * 移行フラグ。
	 */
	private int					transitionFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdSubstituteDto() {
		// 処理無し
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
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
	public String getSubstituteType() {
		return substituteType;
	}
	
	@Override
	public int getTimesWork() {
		return timesWork;
	}
	
	@Override
	public long getTmdSubstituteId() {
		return tmdSubstituteId;
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
	public int getTransitionFlag() {
		return transitionFlag;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
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
	public void setSubstituteType(String substituteType) {
		this.substituteType = substituteType;
	}
	
	@Override
	public void setTimesWork(int timesWork) {
		this.timesWork = timesWork;
	}
	
	@Override
	public void setTmdSubstituteId(long tmdSubstituteId) {
		this.tmdSubstituteId = tmdSubstituteId;
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
	public void setTransitionFlag(int transitionFlag) {
		this.transitionFlag = transitionFlag;
	}
	
	@Override
	public int getHolidayRange() {
		return getSubstituteRange();
	}
	
	@Override
	public void setHolidayRange(int holidayRange) {
		setSubstituteRange(holidayRange);
	}
}
