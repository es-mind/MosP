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
import jp.mosp.time.dto.settings.ScheduleDtoInterface;

/**
 * カレンダマスタDTO
 */
public class TmmScheduleDto extends BaseDto implements ScheduleDtoInterface {
	
	private static final long	serialVersionUID	= 7769672532513580136L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmScheduleId;
	/**
	 * カレンダコード。
	 */
	private String				scheduleCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * カレンダ名称。
	 */
	private String				scheduleName;
	/**
	 * カレンダ略称。
	 */
	private String				scheduleAbbr;
	/**
	 * 年度。
	 */
	private int					fiscalYear;
	/**
	 * パターンコード。
	 */
	private String				patternCode;
	/**
	 * 勤務形態変更フラグ。
	 */
	private int					workTypeChangeFlag;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getTmmScheduleId() {
		return tmmScheduleId;
	}
	
	@Override
	public String getScheduleCode() {
		return scheduleCode;
	}
	
	@Override
	public String getScheduleName() {
		return scheduleName;
	}
	
	@Override
	public String getScheduleAbbr() {
		return scheduleAbbr;
	}
	
	@Override
	public int getFiscalYear() {
		return fiscalYear;
	}
	
	@Override
	public String getPatternCode() {
		return patternCode;
	}
	
	@Override
	public int getWorkTypeChangeFlag() {
		return workTypeChangeFlag;
	}
	
	@Override
	public void setTmmScheduleId(long tmmScheduleId) {
		this.tmmScheduleId = tmmScheduleId;
	}
	
	@Override
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	
	@Override
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	
	@Override
	public void setScheduleAbbr(String scheduleAbbr) {
		this.scheduleAbbr = scheduleAbbr;
	}
	
	@Override
	public void setFiscalYear(int fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
	@Override
	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}
	
	@Override
	public void setWorkTypeChangeFlag(int workTypeChangeFlag) {
		this.workTypeChangeFlag = workTypeChangeFlag;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
