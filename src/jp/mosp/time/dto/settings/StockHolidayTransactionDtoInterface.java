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

import java.util.Date;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * ストック休暇トランザクションDTOインターフェース
 */
public interface StockHolidayTransactionDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmtStockHolidayId();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @return 取得日。
	 */
	Date getAcquisitionDate();
	
	/**
	 * @return 付与日数。
	 */
	double getGivingDay();
	
	/**
	 * @return 廃棄日数。
	 */
	double getCancelDay();
	
	/**
	 * @param tmtStockHolidayId セットする レコード識別ID。
	 */
	void setTmtStockHolidayId(long tmtStockHolidayId);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param acquisitionDate セットする 取得日。
	 */
	void setAcquisitionDate(Date acquisitionDate);
	
	/**
	 * @param givingDay セットする 付与日数。
	 */
	void setGivingDay(double givingDay);
	
	/**
	 * @param cancelDay セットする 廃棄日数。
	 */
	void setCancelDay(double cancelDay);
}
