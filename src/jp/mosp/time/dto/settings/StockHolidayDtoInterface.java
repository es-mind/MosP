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
package jp.mosp.time.dto.settings;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * ストック休暇管理DTOインターフェース
 */
public interface StockHolidayDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmStockHolidayId();
	
	/**
	 * @return 有休コード。
	 */
	String getPaidHolidayCode();
	
	/**
	 * @return 最大年間積立日数。
	 */
	int getStockYearAmount();
	
	/**
	 * @return 最大合計積立日数。
	 */
	int getStockTotalAmount();
	
	/**
	 * @return 有効期限。
	 */
	int getStockLimitDate();
	
	/**
	 * @param tmmStockHolidayId セットする レコード識別ID。
	 */
	void setTmmStockHolidayId(long tmmStockHolidayId);
	
	/**
	 * @param paidHolidayCode セットする 有休コード。
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
	/**
	 * @param stockYearAmount セットする 最大年間積立日数。
	 */
	void setStockYearAmount(int stockYearAmount);
	
	/**
	 * @param stockTotalAmount セットする 最大合計積立日数。
	 */
	void setStockTotalAmount(int stockTotalAmount);
	
	/**
	 * @param stockLimitDate セットする 有効期限。
	 */
	void setStockLimitDate(int stockLimitDate);
}
