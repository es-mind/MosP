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

/**
 * 時差出勤申請一覧DTOインターフェース
 */
public interface DifferenceRequestListDtoInterface extends RequestListDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdDifferenceRequestId();
	
	/**
	 * @return 時差出勤日。
	 */
	Date getRequestDate();
	
	/**
	 * @return 区分。
	 */
	String getAroundType();
	
	/**
	 * @return 時差出勤開始時刻。
	 */
	Date getStartTime();
	
	/**
	 * @return 時差出勤終了時刻。
	 */
	Date getEndTime();
	
	/**
	 * @return 理由。
	 */
	String getRequestReason();
	
	/**
	 * @param tmdDifferenceRequestId セットする レコード識別ID。
	 */
	void setTmdDifferenceRequestId(long tmdDifferenceRequestId);
	
	/**
	 * @param requestDate セットする 時差出勤日。
	 */
	void setRequestDate(Date requestDate);
	
	/**
	 * @param aroundType セットする 区分。
	 */
	void setAroundType(String aroundType);
	
	/**
	 * @param startTime セットする 時差出勤開始時刻。
	 */
	void setStartTime(Date startTime);
	
	/**
	 * @param endTime セットする 時差出勤終了時刻。
	 */
	void setEndTime(Date endTime);
	
	/**
	 * @param requestReason セットする 理由。
	 */
	void setRequestReason(String requestReason);
	
}
