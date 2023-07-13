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
package jp.mosp.time.dto.settings;

import java.util.Date;

/**
 * 休日出勤一覧DTOインターフェース
 */
public interface WorkOnHolidayRequestListDtoInterface extends RequestListDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdWorkOnHolidayRequestId();
	
	/**
	 * @return 申請日。
	 */
	Date getRequestDate();
	
	/**
	 * @return 勤務形態コード。
	 */
	String getWorkTypeCode();
	
	/**
	 * @return 出勤予定時刻。
	 */
	Date getStartTime();
	
	/**
	 * @return 退勤予定時刻。
	 */
	Date getEndTime();
	
	/**
	 * @return 理由。
	 */
	String getRequestReason();
	
	/**
	 * @return 振替日。
	 */
	Date getSubstituteDate();
	
	/**
	 * 1：全日
	 * 2：午前
	 * 3：午後
	 * @return 振替範囲。
	 */
	int getSubstituteRange();
	
	/**
	 * 1：全日振出
	 * 2：休出
	 * 3：半振出午前
	 * 4：半振出午後
	 * 5：勤務形態変更有(全日)
	 * @return 振替申請。（区分）
	 */
	int getSubstitute();
	
	/**
	 * @param tmdWorkOnHolidayRequestId セットする レコード識別ID。
	 */
	void setTmdWorkOnHolidayRequestId(long tmdWorkOnHolidayRequestId);
	
	/**
	 * @param requestDate セットする 申請日。
	 */
	void setRequestDate(Date requestDate);
	
	/**
	 * @param workTypeCode セットする 勤務形態コード。
	 */
	void setWorkTypeCode(String workTypeCode);
	
	/**
	 * @param startTime セットする 出勤予定時刻。
	 */
	void setStartTime(Date startTime);
	
	/**
	 * @param endTime セットする 退勤予定時刻。
	 */
	void setEndTime(Date endTime);
	
	/**
	 * @param requestReason セットする 理由。
	 */
	void setRequestReason(String requestReason);
	
	/**
	 * @param substituteDate セットする 振替日。
	 */
	void setSubstituteDate(Date substituteDate);
	
	/**
	 * 1：全日
	 * 2：午前
	 * 3：午後
	 * @param substituteRange セットする 振替範囲。
	 */
	void setSubstituteRange(int substituteRange);
	
	/**
	 * 1：全日振出
	 * 2：休出
	 * 3：半振出午前
	 * 4：半振出午後
	 * 5：勤務形態変更有(全日)
	 * @param substitute セットする 振替申請。（区分）
	 */
	void setSubstitute(int substitute);
	
}
