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

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 有給休暇比例付与情報インターフェース。<br>
 */
public interface PaidHolidayProportionallyDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID
	 */
	long getTmmPaidHolidayProportionallyId();
	
	/**
	 * @return 有休コード
	 */
	String getPaidHolidayCode();
	
	/**
	 * @return 週所定労働日数
	 */
	int getPrescribedWeeklyWorkingDays();
	
	/**
	 * @return 雇入れの日から起算した継続勤務期間
	 */
	int getContinuousServiceTermsCountingFromTheEmploymentDay();
	
	/**
	 * @return 日数
	 */
	int getDays();
	
	/**
	 * @param tmmPaidHolidayProportionallyId レコード識別ID
	 */
	void setTmmPaidHolidayProportionallyId(long tmmPaidHolidayProportionallyId);
	
	/**
	 * @param paidHolidayCode 有休コード
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
	/**
	 * @param prescribedWeeklyWorkingDays 週所定労働日数
	 */
	void setPrescribedWeeklyWorkingDays(int prescribedWeeklyWorkingDays);
	
	/**
	 * @param continuousServiceTermsCountingFromTheEmploymentDay 雇入れの日から起算した継続勤務期間
	 */
	void setContinuousServiceTermsCountingFromTheEmploymentDay(int continuousServiceTermsCountingFromTheEmploymentDay);
	
	/**
	 * @param days 日数
	 */
	void setDays(int days);
	
}
