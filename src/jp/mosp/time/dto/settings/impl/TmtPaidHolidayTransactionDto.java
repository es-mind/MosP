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
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;

/**
 * 有給休暇トランザクションDTO
 */
public class TmtPaidHolidayTransactionDto extends BaseDto implements PaidHolidayTransactionDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5665683586253329948L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmtPaidHolidayId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 取得日。
	 */
	private Date				acquisitionDate;
	/**
	 * 付与日数。
	 */
	private double				givingDay;
	/**
	 * 付与時間。
	 */
	private int					givingHour;
	/**
	 * 廃棄日数。
	 */
	private double				cancelDay;
	/**
	 * 廃棄時間数。
	 */
	private int					cancelHour;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public double getCancelDay() {
		return cancelDay;
	}
	
	@Override
	public int getCancelHour() {
		return cancelHour;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public double getGivingDay() {
		return givingDay;
	}
	
	@Override
	public int getGivingHour() {
		return givingHour;
	}
	
	@Override
	public long getTmtPaidHolidayId() {
		return tmtPaidHolidayId;
	}
	
	@Override
	public void setCancelDay(double cancelDay) {
		this.cancelDay = cancelDay;
	}
	
	@Override
	public void setCancelHour(int cancelHour) {
		this.cancelHour = cancelHour;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setGivingDay(double givingDay) {
		this.givingDay = givingDay;
	}
	
	@Override
	public void setGivingHour(int givingHour) {
		this.givingHour = givingHour;
	}
	
	@Override
	public void setTmtPaidHolidayId(long tmtPaidHolidayId) {
		this.tmtPaidHolidayId = tmtPaidHolidayId;
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
	public Date getAcquisitionDate() {
		return getDateClone(acquisitionDate);
	}
	
	@Override
	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = getDateClone(acquisitionDate);
	}
	
}
