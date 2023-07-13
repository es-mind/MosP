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

/**
 * 代休申請一覧DTOインターフェース
 */
public interface SubHolidayRequestListDtoInterface extends RequestListDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdSubHolidayRequestId();
	
	/**
	 * @return 代休日。
	 */
	Date getRequestDate();
	
	/**
	 * @return 代休日範囲。
	 */
	String getSubHolidayRange();
	
	/**
	 * @return 出勤日休日区分。
	 */
	int getWorkDateHolidayType();
	
	/**
	 * @return 出勤日。
	 */
	Date getWorkDate();
	
	/**
	 * @return 出勤日範囲。
	 */
	String getWorkDateRange();
	
	/**
	 * @param tmdSubHolidayRequestId セットする レコード識別ID。
	 */
	void setTmdSubHolidayRequestId(long tmdSubHolidayRequestId);
	
	/**
	 * @param requestDate セットする 代休日。
	 */
	void setRequestDate(Date requestDate);
	
	/**
	 * @param subHolidayRange セットする 代休日範囲。
	 */
	void setSubHolidayRange(String subHolidayRange);
	
	/**
	 * @param workDateHolidayType セットする 出勤日休日区分。
	 */
	void setWorkDateHolidayType(int workDateHolidayType);
	
	/**
	 * @param workDate セットする 出勤日。
	 */
	void setWorkDate(Date workDate);
	
	/**
	 * @param workDateRange セットする 出勤日範囲。
	 */
	void setWorkDateRange(String workDateRange);
	
}
