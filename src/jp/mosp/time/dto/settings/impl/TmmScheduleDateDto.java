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
import jp.mosp.time.dto.settings.ScheduleDateDtoInterface;

/**
 * カレンダ日マスタDTO
 */
public class TmmScheduleDateDto extends BaseDto implements ScheduleDateDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6604946279710949372L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmScheduleDateId;
	/**
	 * カレンダコード。
	 */
	private String				scheduleCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 日。
	 */
	private Date				scheduleDate;
	/**
	 * 勤務回数。
	 */
	private int					works;
	/**
	 * 勤務形態コード。
	 */
	private String				workTypeCode;
	/**
	 * 備考。
	 */
	private String				remark;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public String getRemark() {
		return remark;
	}
	
	@Override
	public String getScheduleCode() {
		return scheduleCode;
	}
	
	@Override
	public Date getScheduleDate() {
		return getDateClone(scheduleDate);
	}
	
	@Override
	public long getTmmScheduleDateId() {
		return tmmScheduleDateId;
	}
	
	@Override
	public String getWorkTypeCode() {
		return workTypeCode;
	}
	
	@Override
	public int getWorks() {
		return works;
	}
	
	@Override
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
	@Override
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	
	@Override
	public void setScheduleDate(Date scheduleDate) {
		this.scheduleDate = getDateClone(scheduleDate);
	}
	
	@Override
	public void setTmmScheduleDateId(long tmmScheduleDateId) {
		this.tmmScheduleDateId = tmmScheduleDateId;
	}
	
	@Override
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	@Override
	public void setWorks(int works) {
		this.works = works;
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
