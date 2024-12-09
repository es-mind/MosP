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
import jp.mosp.time.dto.settings.PaidHolidayEntranceDateDtoInterface;

/**
 * 有給休暇入社日管理DTO
 */
public class TmmPaidHolidayEntranceDateDto extends BaseDto implements PaidHolidayEntranceDateDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1178403967801103791L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmPaidHolidayEntranceDateId;
	/**
	 * 有休コード。
	 */
	private String				paidHolidayCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 勤続勤務月数。
	 */
	private int					workMonth;
	/**
	 * 付与日数。
	 */
	private int					joiningDateAmount;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public int getJoiningDateAmount() {
		return joiningDateAmount;
	}
	
	@Override
	public String getPaidHolidayCode() {
		return paidHolidayCode;
	}
	
	@Override
	public long getTmmPaidHolidayEntranceDateId() {
		return tmmPaidHolidayEntranceDateId;
	}
	
	@Override
	public int getWorkMonth() {
		return workMonth;
	}
	
	@Override
	public void setJoiningDateAmount(int joiningDateAmount) {
		this.joiningDateAmount = joiningDateAmount;
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
	@Override
	public void setTmmPaidHolidayEntranceDateId(long tmmPaidHolidayEntranceDateId) {
		this.tmmPaidHolidayEntranceDateId = tmmPaidHolidayEntranceDateId;
	}
	
	@Override
	public void setWorkMonth(int workMonth) {
		this.workMonth = workMonth;
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
