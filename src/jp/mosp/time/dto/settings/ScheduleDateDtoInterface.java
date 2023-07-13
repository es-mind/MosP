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

import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.time.dto.base.WorkTypeCodeDtoInterface;

/**
 * カレンダ日マスタDTOインターフェース
 */
public interface ScheduleDateDtoInterface extends PlatformDtoInterface, WorkTypeCodeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmScheduleDateId();
	
	/**
	 * @return カレンダコード。
	 */
	String getScheduleCode();
	
	/**
	 * @return 日。
	 */
	Date getScheduleDate();
	
	/**
	 * @return 勤務回数。
	 */
	int getWorks();
	
	/**
	 * @return 備考。
	 */
	String getRemark();
	
	/**
	 * @param tmmScheduleDateId セットする レコード識別ID。
	 */
	void setTmmScheduleDateId(long tmmScheduleDateId);
	
	/**
	 * @param scheduleCode セットする カレンダコード。
	 */
	void setScheduleCode(String scheduleCode);
	
	/**
	 * @param scheduleDate セットする 日。
	 */
	void setScheduleDate(Date scheduleDate);
	
	/**
	 * @param works セットする 勤務回数。
	 */
	void setWorks(int works);
	
	/**
	 * @param remark セットする 備考。
	 */
	void setRemark(String remark);
}
