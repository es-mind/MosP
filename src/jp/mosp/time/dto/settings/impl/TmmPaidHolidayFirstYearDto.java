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
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;

/**
 * 有給休暇初年度DTO
 */
public class TmmPaidHolidayFirstYearDto extends BaseDto implements PaidHolidayFirstYearDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5460545673302322841L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmPaidHolidayFirstYearId;
	/**
	 * 有休コード。
	 */
	private String				paidHolidayCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 入社月。
	 */
	private int					entranceMonth;
	/**
	 * 付与月。
	 */
	private int					givingMonth;
	/**
	 * 付与日数。
	 */
	private int					givingAmount;
	/**
	 * 利用期限。
	 */
	private int					givingLimit;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public int getEntranceMonth() {
		return entranceMonth;
	}
	
	@Override
	public int getGivingAmount() {
		return givingAmount;
	}
	
	@Override
	public int getGivingLimit() {
		return givingLimit;
	}
	
	@Override
	public int getGivingMonth() {
		return givingMonth;
	}
	
	@Override
	public String getPaidHolidayCode() {
		return paidHolidayCode;
	}
	
	@Override
	public long getTmmPaidHolidayFirstYearId() {
		return tmmPaidHolidayFirstYearId;
	}
	
	@Override
	public void setEntranceMonth(int entranceMonth) {
		this.entranceMonth = entranceMonth;
	}
	
	@Override
	public void setGivingAmount(int givingAmount) {
		this.givingAmount = givingAmount;
	}
	
	@Override
	public void setGivingLimit(int givingLimit) {
		this.givingLimit = givingLimit;
	}
	
	@Override
	public void setGivingMonth(int givingMonth) {
		this.givingMonth = givingMonth;
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
	@Override
	public void setTmmPaidHolidayFirstYearId(long tmmPaidHolidayFirstYearId) {
		this.tmmPaidHolidayFirstYearId = tmmPaidHolidayFirstYearId;
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
