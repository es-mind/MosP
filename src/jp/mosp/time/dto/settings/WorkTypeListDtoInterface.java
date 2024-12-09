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

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 勤務形態一覧DTOインターフェース
 */
public interface WorkTypeListDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmWorkTypeId();
	
	/**
	 * @return 勤務形態コード。
	 */
	String getWorkTypeCode();
	
	/**
	 * @return 勤務形態名称。
	 */
	String getWorkTypeName();
	
	/**
	 * @return 勤務形態略称。
	 */
	String getWorkTypeAbbr();
	
	/**
	 * @return 出勤時刻。
	 */
	Date getStartTime();
	
	/**
	 * @return 退勤時刻。
	 */
	Date getEndTime();
	
	/**
	 * @return 勤務時間。
	 */
	Date getWorkTime();
	
	/**
	 * @return 休憩時間。
	 */
	Date getRestTime();
	
	/**
	 * @return 前半休時間。
	 */
	Date getFrontTime();
	
	/**
	 * @return 後半休時間。
	 */
	Date getBackTime();
	
	/**
	 * @param tmmWorkTypeId セットする レコード識別ID。
	 */
	void setTmmWorkTypeId(long tmmWorkTypeId);
	
	/**
	 * @param workTypeCode セットする 勤務形態コード。
	 */
	void setWorkTypeCode(String workTypeCode);
	
	/**
	 * @param workTypeName セットする 勤務形態名称。
	 */
	void setWorkTypeName(String workTypeName);
	
	/**
	 * @param workTypeAbbr セットする 勤務形態略称。
	 */
	void setWorkTypeAbbr(String workTypeAbbr);
	
	/**
	 * @param startTime セットする 出勤時刻。
	 */
	void setStartTime(Date startTime);
	
	/**
	 * @param endTime セットする 退勤時刻。
	 */
	void setEndTime(Date endTime);
	
	/**
	 * @param workTime セットする 勤務時間。
	 */
	void setWorkTime(Date workTime);
	
	/**
	 * @param restTime セットする 休憩時間。
	 */
	void setRestTime(Date restTime);
	
	/**
	 * @param frontTime セットする 前半休時間。
	 */
	void setFrontTime(Date frontTime);
	
	/**
	 * @param backTime セットする 後半休時間。
	 */
	void setBackTime(Date backTime);
}
