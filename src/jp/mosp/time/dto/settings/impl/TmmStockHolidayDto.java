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
import jp.mosp.time.dto.settings.StockHolidayDtoInterface;

/**
 * ストック休暇管理DTO
 */
public class TmmStockHolidayDto extends BaseDto implements StockHolidayDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -7024069878747582881L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmStockHolidayId;
	/**
	 * 有休コード。
	 */
	private String				paidHolidayCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 最大年間積立日数。
	 */
	private int					stockYearAmount;
	/**
	 * 最大合計積立日数。
	 */
	private int					stockTotalAmount;
	/**
	 * 有効期限。
	 */
	private int					stockLimitDate;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public String getPaidHolidayCode() {
		return paidHolidayCode;
	}
	
	@Override
	public int getStockLimitDate() {
		return stockLimitDate;
	}
	
	@Override
	public int getStockTotalAmount() {
		return stockTotalAmount;
	}
	
	@Override
	public int getStockYearAmount() {
		return stockYearAmount;
	}
	
	@Override
	public long getTmmStockHolidayId() {
		return tmmStockHolidayId;
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
	@Override
	public void setStockLimitDate(int stockLimitDate) {
		this.stockLimitDate = stockLimitDate;
	}
	
	@Override
	public void setStockTotalAmount(int stockTotalAmount) {
		this.stockTotalAmount = stockTotalAmount;
	}
	
	@Override
	public void setStockYearAmount(int stockYearAmount) {
		this.stockYearAmount = stockYearAmount;
	}
	
	@Override
	public void setTmmStockHolidayId(long tmmStockHolidayId) {
		this.tmmStockHolidayId = tmmStockHolidayId;
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
