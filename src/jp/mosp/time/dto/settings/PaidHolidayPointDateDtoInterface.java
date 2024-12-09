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
 * 有給休暇基準日管理DTOインターフェース
 */
public interface PaidHolidayPointDateDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmPaidHolidayPointDateId();
	
	/**
	 * @return 有休コード。
	 */
	String getPaidHolidayCode();
	
	/**
	 * @return 基準日経過回数。
	 */
	int getTimesPointDate();
	
	/**
	 * @return 付与日数。
	 */
	int getPointDateAmount();
	
	/**
	 * @param tmmPaidHolidayPointDateId セットする レコード識別ID。
	 */
	void setTmmPaidHolidayPointDateId(long tmmPaidHolidayPointDateId);
	
	/**
	 * @param paidHolidayCode セットする 有休コード。
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
	/**
	 * @param timesPointDate セットする 基準日経過回数。
	 */
	void setTimesPointDate(int timesPointDate);
	
	/**
	 * @param pointDateAmount セットする 付与日数。
	 */
	void setPointDateAmount(int pointDateAmount);
}
