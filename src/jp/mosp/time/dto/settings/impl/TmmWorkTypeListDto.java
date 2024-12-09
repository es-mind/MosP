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
import jp.mosp.time.dto.settings.WorkTypeListDtoInterface;

/**
 * 勤務形態一覧DTO
 */
public class TmmWorkTypeListDto extends BaseDto implements WorkTypeListDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 3957113565044874199L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmWorkTypeId;
	
	/**
	 * 勤務形態コード。
	 */
	private String				workTypeCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 勤務形態名称。
	 */
	private String				workTypeName;
	/**
	 * 勤務形態略称。
	 */
	private String				workTypeAbbr;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	/**
	 * 出勤時刻。
	 */
	private Date				startTime;
	/**
	 * 退勤時刻。
	 */
	private Date				endTime;
	/**
	 * 勤務時間。
	 */
	private Date				workTime;
	/**
	 * 休憩時間。
	 */
	private Date				restTime;
	/**
	 * 前半休時間。
	 */
	private Date				frontTime;
	/**
	 * 後半休時間。
	 */
	private Date				backTime;
	
	
	@Override
	public long getTmmWorkTypeId() {
		return tmmWorkTypeId;
	}
	
	@Override
	public Date getEndTime() {
		return getDateClone(endTime);
	}
	
	@Override
	public Date getRestTime() {
		return getDateClone(restTime);
	}
	
	@Override
	public Date getStartTime() {
		return getDateClone(startTime);
	}
	
	@Override
	public Date getWorkTime() {
		return getDateClone(workTime);
	}
	
	@Override
	public String getWorkTypeAbbr() {
		return workTypeAbbr;
	}
	
	@Override
	public String getWorkTypeCode() {
		return workTypeCode;
	}
	
	@Override
	public String getWorkTypeName() {
		return workTypeName;
	}
	
	@Override
	public void setTmmWorkTypeId(long tmmWorkTypeId) {
		this.tmmWorkTypeId = tmmWorkTypeId;
	}
	
	@Override
	public void setEndTime(Date endTime) {
		this.endTime = getDateClone(endTime);
	}
	
	@Override
	public void setRestTime(Date restTime) {
		this.restTime = getDateClone(restTime);
	}
	
	@Override
	public void setStartTime(Date startTime) {
		this.startTime = getDateClone(startTime);
	}
	
	@Override
	public void setWorkTime(Date workTime) {
		this.workTime = getDateClone(workTime);
	}
	
	@Override
	public void setWorkTypeAbbr(String workTypeAbbr) {
		this.workTypeAbbr = workTypeAbbr;
	}
	
	@Override
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	@Override
	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
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
	
	@Override
	public Date getBackTime() {
		return getDateClone(backTime);
	}
	
	@Override
	public Date getFrontTime() {
		return getDateClone(frontTime);
	}
	
	@Override
	public void setBackTime(Date backTime) {
		this.backTime = getDateClone(backTime);
	}
	
	@Override
	public void setFrontTime(Date frontTime) {
		this.frontTime = getDateClone(frontTime);
	}
	
}
