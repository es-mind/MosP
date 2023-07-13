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

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.WorkflowNumberDtoInterface;
import jp.mosp.time.dto.base.RequestDateDtoInterface;
import jp.mosp.time.dto.base.WorkTypeCodeDtoInterface;

/**
 * 振出・休出申請DTOインターフェース。<br>
 */
public interface WorkOnHolidayRequestDtoInterface extends BaseDtoInterface, PersonalIdDtoInterface,
		WorkflowNumberDtoInterface, RequestDateDtoInterface, WorkTypeCodeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdWorkOnHolidayRequestId();
	
	/**
	 * @return 勤務回数。
	 */
	int getTimesWork();
	
	/**
	 * @return 休出種別。
	 */
	String getWorkOnHolidayType();
	
	/**
	 * @return 振替申請。
	 */
	int getSubstitute();
	
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
	 * @param tmdWorkOnHolidayRequestId セットする レコード識別ID。
	 */
	void setTmdWorkOnHolidayRequestId(long tmdWorkOnHolidayRequestId);
	
	/**
	 * @param timesWork セットする 勤務回数。
	 */
	void setTimesWork(int timesWork);
	
	/**
	 * @param workOnHolidayType セットする 休出種別。
	 */
	void setWorkOnHolidayType(String workOnHolidayType);
	
	/**
	 * @param substitute セットする 振替申請。
	 */
	void setSubstitute(int substitute);
	
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
	
}
