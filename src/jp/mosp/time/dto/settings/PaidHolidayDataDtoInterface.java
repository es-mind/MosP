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
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;

/**
 * 有給休暇データDTOインターフェース
 */
public interface PaidHolidayDataDtoInterface extends PlatformDtoInterface, PersonalIdDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdPaidHolidayId();
	
	/**
	 * @return 取得日。
	 */
	Date getAcquisitionDate();
	
	/**
	 * @return 期限日。
	 */
	Date getLimitDate();
	
	/**
	 * @return 保有日数。
	 */
	double getHoldDay();
	
	/**
	 * @return 保有時間数。
	 */
	int getHoldHour();
	
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
	 * @return 使用日数。
	 */
	double getUseDay();
	
	/**
	 * @return 使用時間数。
	 */
	int getUseHour();
	
	/**
	 * @return 時間変換日分母。
	 */
	int getDenominatorDayHour();
	
	/**
	 * @return 仮付与フラグ。
	 */
	int getTemporaryFlag();
	
	/**
	 * @param tmdPaidHolidayId セットする レコード識別ID。
	 */
	void setTmdPaidHolidayId(long tmdPaidHolidayId);
	
	/**
	 * @param acquisitionDate セットする 取得日。
	 */
	void setAcquisitionDate(Date acquisitionDate);
	
	/**
	 * @param limitDate セットする 期限日。
	 */
	void setLimitDate(Date limitDate);
	
	/**
	 * @param holdDay セットする 保有日数。
	 */
	void setHoldDay(double holdDay);
	
	/**
	 * @param holdHour セットする 保有時間数。
	 */
	void setHoldHour(int holdHour);
	
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
	 * @param useDay セットする 使用日数。
	 */
	void setUseDay(double useDay);
	
	/**
	 * @param useHour セットする 使用時間数。
	 */
	void setUseHour(int useHour);
	
	/**
	 * @param denominatorDayHour 時間変換日分母。
	 */
	void setDenominatorDayHour(int denominatorDayHour);
	
	/**
	 * @param temporaryFlag セットする 仮付与。
	 */
	void setTemporaryFlag(int temporaryFlag);
	
}
