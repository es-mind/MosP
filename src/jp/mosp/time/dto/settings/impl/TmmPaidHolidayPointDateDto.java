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
import jp.mosp.time.dto.settings.PaidHolidayPointDateDtoInterface;

/**
 * 有給休暇基準日管理DTO
 */
public class TmmPaidHolidayPointDateDto extends BaseDto implements PaidHolidayPointDateDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4737188090998936759L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmPaidHolidayPointDateId;
	/**
	 * 有休コード。
	 */
	private String				paidHolidayCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 基準日経過回数。
	 */
	private int					timesPointDate;
	/**
	 * 付与日数。
	 */
	private int					pointDateAmount;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public String getPaidHolidayCode() {
		return paidHolidayCode;
	}
	
	@Override
	public int getPointDateAmount() {
		return pointDateAmount;
	}
	
	@Override
	public int getTimesPointDate() {
		return timesPointDate;
	}
	
	@Override
	public long getTmmPaidHolidayPointDateId() {
		return tmmPaidHolidayPointDateId;
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
	@Override
	public void setPointDateAmount(int pointDateAmount) {
		this.pointDateAmount = pointDateAmount;
	}
	
	@Override
	public void setTimesPointDate(int timesPointDate) {
		this.timesPointDate = timesPointDate;
	}
	
	@Override
	public void setTmmPaidHolidayPointDateId(long tmmPaidHolidayPointDateId) {
		this.tmmPaidHolidayPointDateId = tmmPaidHolidayPointDateId;
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
