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
package jp.mosp.time.dto.settings;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 有給休暇初年度DTOインターフェース
 */
public interface PaidHolidayFirstYearDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmPaidHolidayFirstYearId();
	
	/**
	 * @return 有休コード。
	 */
	String getPaidHolidayCode();
	
	/**
	 * @return 入社月。
	 */
	int getEntranceMonth();
	
	/**
	 * @return 付与月。
	 */
	int getGivingMonth();
	
	/**
	 * @return 付与日数。
	 */
	int getGivingAmount();
	
	/**
	 * @return 利用期限。
	 */
	int getGivingLimit();
	
	/**
	 * @param tmmPaidHolidayFirstYearId セットする レコード識別ID。
	 */
	void setTmmPaidHolidayFirstYearId(long tmmPaidHolidayFirstYearId);
	
	/**
	 * @param paidHolidayCode セットする 有休コード。
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
	/**
	 * @param entranceMonth セットする 入社月。
	 */
	void setEntranceMonth(int entranceMonth);
	
	/**
	 * @param givingMonth セットする 付与月。
	 */
	void setGivingMonth(int givingMonth);
	
	/**
	 * @param givingAmount セットする 付与日数。
	 */
	void setGivingAmount(int givingAmount);
	
	/**
	 * @param givingLimit セットする 利用期限。
	 */
	void setGivingLimit(int givingLimit);
}
