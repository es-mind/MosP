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

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.base.RecordDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.WorkflowNumberDtoInterface;
import jp.mosp.time.dto.base.HolidayRangeDtoInterface;

/**
 * 休暇申請DTOインターフェース
 */
public interface HolidayRequestDtoInterface extends BaseDtoInterface, RecordDtoInterface, PersonalIdDtoInterface,
		WorkflowNumberDtoInterface, HolidayRangeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdHolidayRequestId();
	
	/**
	 * @return 申請開始日。
	 */
	Date getRequestStartDate();
	
	/**
	 * @return 申請終了日。
	 */
	Date getRequestEndDate();
	
	/**
	 * @return 休暇種別1。
	 */
	int getHolidayType1();
	
	/**
	 * @return 休暇種別2。
	 */
	String getHolidayType2();
	
	/**
	 * @return 時休開始時刻。
	 */
	Date getStartTime();
	
	/**
	 * @return 時休終了時刻。
	 */
	Date getEndTime();
	
	/**
	 * @return 休暇取得日。
	 */
	Date getHolidayAcquisitionDate();
	
	/**
	 * @return 使用日数。
	 */
	double getUseDay();
	
	/**
	 * @return 使用時間数。
	 */
	int getUseHour();
	
	/**
	 * @return 理由。
	 */
	String getRequestReason();
	
	/**
	 * @param tmdHolidayRequestId セットする レコード識別ID。
	 */
	void setTmdHolidayRequestId(long tmdHolidayRequestId);
	
	/**
	 * @param requestStartDate セットする 申請開始日。
	 */
	void setRequestStartDate(Date requestStartDate);
	
	/**
	 * @param requestEndDate セットする 申請終了日。
	 */
	void setRequestEndDate(Date requestEndDate);
	
	/**
	 * @param holidayType1 セットする 休暇種別1。
	 */
	void setHolidayType1(int holidayType1);
	
	/**
	 * @param holidayType2 セットする 休暇種別2。
	 */
	void setHolidayType2(String holidayType2);
	
	/**
	 * @param startTime セットする 時休開始時刻。
	 */
	void setStartTime(Date startTime);
	
	/**
	 * @param endTime セットする 時休終了時刻。
	 */
	void setEndTime(Date endTime);
	
	/**
	 * @param holidayAcquisitionDate セットする 休暇取得日。
	 */
	void setHolidayAcquisitionDate(Date holidayAcquisitionDate);
	
	/**
	 * @param useDay セットする 使用日数。
	 */
	void setUseDay(double useDay);
	
	/**
	 * @param useHour セットする 使用時間数。
	 */
	void setUseHour(int useHour);
	
	/**
	 * @param requestReason セットする 理由。
	 */
	void setRequestReason(String requestReason);
	
}
