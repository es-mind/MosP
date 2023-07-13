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

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * 手当集計データDTOインターフェース
 */
public interface TotalAllowanceDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdTotalAllowanceId();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @return 年。
	 */
	int getCalculationYear();
	
	/**
	 * @return 月。
	 */
	int getCalculationMonth();
	
	/**
	 * @return 手当コード。
	 */
	String getAllowanceCode();
	
	/**
	 * @return 手当回数。
	 */
	int getTimes();
	
	/**
	 * @param tmdTotalAllowanceId セットする レコード識別ID。
	 */
	void setTmdTotalAllowanceId(long tmdTotalAllowanceId);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param calculationYear セットする 年。
	 */
	void setCalculationYear(int calculationYear);
	
	/**
	 * @param calculationMonth セットする 月。
	 */
	void setCalculationMonth(int calculationMonth);
	
	/**
	 * @param allowanceCode セットする 手当コード。
	 */
	void setAllowanceCode(String allowanceCode);
	
	/**
	 * @param times セットする 手当回数。
	 */
	void setTimes(int times);
}
