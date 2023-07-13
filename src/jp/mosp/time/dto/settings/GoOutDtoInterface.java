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

/**
 * 外出データDTOインターフェース
 */
public interface GoOutDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdGoOutId();
	
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
	 * @return 外出区分。
	 */
	int getGoOutType();
	
	/**
	 * @return 外出回数。
	 */
	int getTimesGoOut();
	
	/**
	 * @return 外出開始時刻。
	 */
	Date getGoOutStart();
	
	/**
	 * @return 外出終了時刻。
	 */
	Date getGoOutEnd();
	
	/**
	 * @return 外出時間。
	 */
	int getGoOutTime();
	
	/**
	 * @param tmdGoOutId セットする レコード識別ID。
	 */
	void setTmdGoOutId(long tmdGoOutId);
	
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
	 * @param goOutType セットする 外出区分。
	 */
	void setGoOutType(int goOutType);
	
	/**
	 * @param timesGoOut セットする 外出回数。
	 */
	void setTimesGoOut(int timesGoOut);
	
	/**
	 * @param goOutStart セットする 外出開始時刻。
	 */
	void setGoOutStart(Date goOutStart);
	
	/**
	 * @param goOutEnd セットする 外出終了時刻。
	 */
	void setGoOutEnd(Date goOutEnd);
	
	/**
	 * @param goOutTime セットする 外出時間。
	 */
	void setGoOutTime(int goOutTime);
}
