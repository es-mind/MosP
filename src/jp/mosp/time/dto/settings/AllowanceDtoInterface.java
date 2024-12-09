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
 * 手当データDTOインターフェース
 */
public interface AllowanceDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdAllowanceId();
	
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
	int getWorks();
	
	/**
	 * @return 手当コード。
	 */
	String getAllowanceCode();
	
	/**
	 * @return 手当。
	 */
	int getAllowance();
	
	/**
	 * @param tmdAllowanceId セットする レコード識別ID。
	 */
	void setTmdAllowanceId(long tmdAllowanceId);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param workDate セットする 勤務日。
	 */
	void setWorkDate(Date workDate);
	
	/**
	 * @param works セットする 勤務回数。
	 */
	void setWorks(int works);
	
	/**
	 * @param allowanceCode セットする 手当コード。
	 */
	void setAllowanceCode(String allowanceCode);
	
	/**
	 * @param allowance セットする 手当。
	 */
	void setAllowance(int allowance);
}
