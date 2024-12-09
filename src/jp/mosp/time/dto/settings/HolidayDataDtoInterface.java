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

import java.util.Date;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 休暇データDTOインターフェース
 */
public interface HolidayDataDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdHolidayId();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @return 休暇コード。
	 */
	String getHolidayCode();
	
	/**
	 * @return 休暇区分。
	 */
	int getHolidayType();
	
	/**
	 * @return 付与日数。
	 */
	double getGivingDay();
	
	/**
	 * @return 付与時間数。
	 */
	int getGivingHour();
	
	/**
	 * @return 廃棄日数。
	 */
	double getCancelDay();
	
	/**
	 * @return 廃棄時間数。
	 */
	int getCancelHour();
	
	/**
	 * @return 取得期限。
	 */
	Date getHolidayLimitDate();
	
	/**
	 * @return 取得期限(月)。
	 */
	int getHolidayLimitMonth();
	
	/**
	 * @return 取得期限(日)。
	 */
	int getHolidayLimitDay();
	
	/**
	 * @param tmdHolidayId セットする レコード識別ID。
	 */
	void setTmdHolidayId(long tmdHolidayId);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param holidayCode セットする 休暇コード。
	 */
	void setHolidayCode(String holidayCode);
	
	/**
	 * @param holidayType セットする 休暇区分。
	 */
	void setHolidayType(int holidayType);
	
	/**
	 * @param givingDay セットする 付与日数。
	 */
	void setGivingDay(double givingDay);
	
	/**
	 * @param givingHour セットする 付与時間数。
	 */
	void setGivingHour(int givingHour);
	
	/**
	 * @param cancelDay セットする 廃棄日数。
	 */
	void setCancelDay(double cancelDay);
	
	/**
	 * @param cancelHour セットする 廃棄時間数。
	 */
	void setCancelHour(int cancelHour);
	
	/**
	 * @param holidayLimitDate セットする 取得期限。
	 */
	void setHolidayLimitDate(Date holidayLimitDate);
	
	/**
	 * @param holidayLimitMonth セットする 取得期限(月)。
	 */
	void setHolidayLimitMonth(int holidayLimitMonth);
	
	/**
	 * @param holidayLimitDay セットする 取得期限(日)。
	 */
	void setHolidayLimitDay(int holidayLimitDay);
	
}
