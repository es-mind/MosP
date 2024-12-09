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
package jp.mosp.time.dto.settings;

import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;

/**
 * 有給休暇手動付与一覧DTOインターフェース。
 */
public interface PaidHolidayHistoryListDtoInterface
		extends PlatformDtoInterface, EmployeeCodeDtoInterface, EmployeeNameDtoInterface, SectionCodeDtoInterface {
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @return 前年日数
	 */
	double getFormerDate();
	
	/**
	 * @param formerDate セットする 前年日数
	 */
	void setFormerDate(double formerDate);
	
	/**
	 * @return 前年時間
	 */
	int getFormerTime();
	
	/**
	 * @param formerTime セットする 前年時間
	 */
	void setFormerTime(int formerTime);
	
	/**
	 * @return 今年日数
	 */
	double getDate();
	
	/**
	 * @param date セットする 今年日数
	 */
	void setDate(double date);
	
	/**
	 * @return 今年時間
	 */
	int getTime();
	
	/**
	 * @param time セットする 今年時間
	 */
	void setTime(int time);
	
	/**
	 * @return ストック日数
	 */
	double getStockDate();
	
	/**
	 * @param stockDate セットする ストック日数
	 */
	void setStockDate(double stockDate);
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdPaidHolidayHistoryListId();
	
	/**
	 * @param tmdPaidHolidayHistoryListId セットする レコード識別ID。
	 */
	void setTmdPaidHolidayHistoryListId(long tmdPaidHolidayHistoryListId);
	
}
