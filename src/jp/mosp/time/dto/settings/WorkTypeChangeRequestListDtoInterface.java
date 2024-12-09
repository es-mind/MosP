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

import java.util.Date;

/**
 * 勤務形態変更申請一覧DTOインターフェース。
 */
public interface WorkTypeChangeRequestListDtoInterface extends RequestListDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdWorkTypeChangeRequestId();
	
	/**
	 * @return 出勤日。
	 */
	Date getRequestDate();
	
	/**
	 * @return 勤務形態コード。
	 */
	String getWorkTypeCode();
	
	/**
	 * @return 理由。
	 */
	String getRequestReason();
	
	/**
	 * @param tmdWorkTypeChangeRequestId セットする レコード識別ID。
	 */
	void setTmdWorkTypeChangeRequestId(long tmdWorkTypeChangeRequestId);
	
	/**
	 * @param requestDate セットする 出勤日。
	 */
	void setRequestDate(Date requestDate);
	
	/**
	 * @param workTypeCode セットする 勤務形態コード。
	 */
	void setWorkTypeCode(String workTypeCode);
	
	/**
	 * @param requestReason セットする 理由。
	 */
	void setRequestReason(String requestReason);
	
}
