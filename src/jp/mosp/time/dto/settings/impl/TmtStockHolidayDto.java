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
import jp.mosp.time.dto.settings.StockHolidayTransactionDtoInterface;

/**
 * ストック休暇トランザクションDTOインターフェース
 */
public class TmtStockHolidayDto extends BaseDto implements StockHolidayTransactionDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -6044564780026782784L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmtStockHolidayId;
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
	 * 廃棄日数。
	 */
	private double				cancelDay;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public Date getAcquisitionDate() {
		return getDateClone(acquisitionDate);
	}
	
	@Override
	public double getCancelDay() {
		return cancelDay;
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
	public long getTmtStockHolidayId() {
		return tmtStockHolidayId;
	}
	
	@Override
	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = getDateClone(acquisitionDate);
	}
	
	@Override
	public void setCancelDay(double cancelDay) {
		this.cancelDay = cancelDay;
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
	public void setTmtStockHolidayId(long tmtStockHolidayId) {
		this.tmtStockHolidayId = tmtStockHolidayId;
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
