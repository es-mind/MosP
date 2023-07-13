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
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;

/**
 * 代休データDTOインターフェース。<br>
 */
public interface SubHolidayDtoInterface extends BaseDtoInterface, PersonalIdDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdSubHolidayId();
	
	/**
	 * @return 出勤日。
	 */
	Date getWorkDate();
	
	/**
	 * @return 勤務回数。
	 */
	int getTimesWork();
	
	/**
	 * @return 代休種別。
	 */
	int getSubHolidayType();
	
	/**
	 * @return 代休日数。
	 */
	double getSubHolidayDays();
	
	/**
	 * @return 移行フラグ。
	 */
	int getTransitionFlag();
	
	/**
	 * @param tmdSubHolidayId セットする レコード識別ID。
	 */
	void setTmdSubHolidayId(long tmdSubHolidayId);
	
	/**
	 * @param workDate セットする 出勤日。
	 */
	void setWorkDate(Date workDate);
	
	/**
	 * @param timesWork セットする 勤務回数。
	 */
	void setTimesWork(int timesWork);
	
	/**
	 * @param subHolidayType セットする 代休種別。
	 */
	void setSubHolidayType(int subHolidayType);
	
	/**
	 * @param subHolidayDays セットする 代休日数。
	 */
	void setSubHolidayDays(double subHolidayDays);
	
	/**
	 * @param transitionFlag セットする 移行フラグ。
	 */
	void setTransitionFlag(int transitionFlag);
	
}
