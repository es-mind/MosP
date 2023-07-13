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
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;

/**
 * ストック休暇データDTO
 */
public class TmdStockHolidayDto extends BaseDto implements StockHolidayDataDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 2892707441915750181L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdStockHolidayId;
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
	 * 期限日。
	 */
	private Date				limitDate;
	/**
	 * 保有日数。
	 */
	private double				holdDay;
	/**
	 * 付与日数。
	 */
	private double				givingDay;
	/**
	 * 廃棄日数。
	 */
	private double				cancelDay;
	/**
	 * 使用日数。
	 */
	private double				useDay;
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
	public double getHoldDay() {
		return holdDay;
	}
	
	@Override
	public long getTmdStockHolidayId() {
		return tmdStockHolidayId;
	}
	
	@Override
	public double getUseDay() {
		return useDay;
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
	public void setHoldDay(double holdDay) {
		this.holdDay = holdDay;
	}
	
	@Override
	public void setTmdStockHolidayId(long tmdStockHolidayId) {
		this.tmdStockHolidayId = tmdStockHolidayId;
	}
	
	@Override
	public void setUseDay(double useDay) {
		this.useDay = useDay;
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
	public Date getLimitDate() {
		return getDateClone(limitDate);
	}
	
	@Override
	public void setLimitDate(Date limitDate) {
		this.limitDate = getDateClone(limitDate);
	}
	
}
