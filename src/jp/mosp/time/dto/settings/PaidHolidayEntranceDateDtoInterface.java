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
 * 有給休暇入社日管理DTOインターフェース
 */
public interface PaidHolidayEntranceDateDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmPaidHolidayEntranceDateId();
	
	/**
	 * @return 有休コード。
	 */
	String getPaidHolidayCode();
	
	/**
	 * @return 勤続勤務月数。
	 */
	int getWorkMonth();
	
	/**
	 * @return 付与日数。
	 */
	int getJoiningDateAmount();
	
	/**
	 * @param tmmPaidHolidayEntranceDateId セットする レコード識別ID。
	 */
	void setTmmPaidHolidayEntranceDateId(long tmmPaidHolidayEntranceDateId);
	
	/**
	 * @param paidHolidayCode セットする 有休コード。
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
	/**
	 * @param workMonth セットする 勤続勤務月数。
	 */
	void setWorkMonth(int workMonth);
	
	/**
	 * @param joiningDateAmount セットする 付与日数。
	 */
	void setJoiningDateAmount(int joiningDateAmount);
}
