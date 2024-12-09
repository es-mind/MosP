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
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.WorkflowNumberDtoInterface;
import jp.mosp.time.dto.base.HolidayRangeDtoInterface;

/**
 * 振替休日データDTOインターフェース。<br>
 */
public interface SubstituteDtoInterface
		extends BaseDtoInterface, PersonalIdDtoInterface, WorkflowNumberDtoInterface, HolidayRangeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdSubstituteId();
	
	/**
	 * @return 振替日。
	 */
	Date getSubstituteDate();
	
	/**
	 * @return 振替種別。
	 */
	String getSubstituteType();
	
	/**
	 * @return 振替範囲。
	 */
	int getSubstituteRange();
	
	/**
	 * @return 出勤日。
	 */
	Date getWorkDate();
	
	/**
	 * @return 勤務回数。
	 */
	int getTimesWork();
	
	/**
	 * @return 移行フラグ。
	 */
	int getTransitionFlag();
	
	/**
	 * @param tmdSubstituteId セットする レコード識別ID。
	 */
	void setTmdSubstituteId(long tmdSubstituteId);
	
	/**
	 * @param substituteDate セットする 振替日。
	 */
	void setSubstituteDate(Date substituteDate);
	
	/**
	 * @param substituteType セットする 振替種別。
	 */
	void setSubstituteType(String substituteType);
	
	/**
	 * @param substituteRange セットする 振替範囲。
	 */
	void setSubstituteRange(int substituteRange);
	
	/**
	 * @param workDate セットする 出勤日。
	 */
	void setWorkDate(Date workDate);
	
	/**
	 * @param timesWork セットする 勤務回数。
	 */
	void setTimesWork(int timesWork);
	
	/**
	 * @param transitionFlag セットする 移行フラグ。
	 */
	void setTransitionFlag(int transitionFlag);
}
