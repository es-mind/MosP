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
 * 残業申請一覧DTOインターフェース
 */
public interface OvertimeRequestListDtoInterface extends RequestListDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdOvertimeRequestId();
	
	/**
	 * @return 申請日。
	 */
	Date getRequestDate();
	
	/**
	 * @return 残業区分。
	 */
	int getOvertimeType();
	
	/**
	 * @return 申請時間。
	 */
	int getRequestTime();
	
	/**
	 * @return 実績時間。
	 */
	String getResultsTime();
	
	/**
	 * @return 理由。
	 */
	String getRequestReason();
	
	/**
	 * @param tmdOvertimeRequestId セットする レコード識別ID。
	 */
	void setTmdOvertimeRequestId(long tmdOvertimeRequestId);
	
	/**
	 * @param requestDate セットする 申請日。
	 */
	void setRequestDate(Date requestDate);
	
	/**
	 * @param overtimeType セットする 残業区分。
	 */
	void setOvertimeType(int overtimeType);
	
	/**
	 * @param requestTime セットする 申請時間。
	 */
	void setRequestTime(int requestTime);
	
	/**
	 * @param resultsTime セットする 実績時間。
	 */
	void setResultsTime(String resultsTime);
	
	/**
	 * @param requestReason セットする 理由。
	 */
	void setRequestReason(String requestReason);
	
}
