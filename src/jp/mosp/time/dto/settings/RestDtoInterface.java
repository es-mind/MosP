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

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * 休憩データDTOインターフェース
 */
public interface RestDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdRestId();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @return 勤務日。
	 */
	Date getWorkDate();
	
	/**
	 * @return 勤務回数。
	 */
	int getTimesWork();
	
	/**
	 * @return 休憩回数。
	 */
	int getRest();
	
	/**
	 * @return 休憩開始時刻。
	 */
	Date getRestStart();
	
	/**
	 * @return 休憩終了時刻。
	 */
	Date getRestEnd();
	
	/**
	 * @return 休憩時間。
	 */
	int getRestTime();
	
	/**
	 * @param tmdRestId セットする レコード識別ID。
	 */
	void setTmdRestId(long tmdRestId);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param workDate セットする 勤務日。
	 */
	void setWorkDate(Date workDate);
	
	/**
	 * @param timesWork セットする 勤務回数。
	 */
	void setTimesWork(int timesWork);
	
	/**
	 * @param rest セットする 休憩回数。
	 */
	void setRest(int rest);
	
	/**
	 * @param restStart セットする 休憩開始時刻。
	 */
	void setRestStart(Date restStart);
	
	/**
	 * @param restEnd セットする 休憩終了時刻。
	 */
	void setRestEnd(Date restEnd);
	
	/**
	 * @param restTime セットする 休憩時間。
	 */
	void setRestTime(int restTime);
}
