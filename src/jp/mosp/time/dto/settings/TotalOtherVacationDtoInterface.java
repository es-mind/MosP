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
 * その他休暇集計データDTOインターフェース
 */
public interface TotalOtherVacationDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdTotalOtherVacationId();
	
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
	 * @return 休暇コード。
	 */
	String getHolidayCode();
	
	/**
	 * @return その他休暇日数。
	 */
	double getTimes();
	
	/**
	 * @return その他休暇時間数。
	 */
	int getHours();
	
	/**
	 * @param tmdTotalOtherVacationId セットする レコード識別ID。
	 */
	void setTmdTotalOtherVacationId(long tmdTotalOtherVacationId);
	
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
	 * @param holidayCode セットする 休暇コード。
	 */
	void setHolidayCode(String holidayCode);
	
	/**
	 * @param times セットする その他休暇日数。
	 */
	void setTimes(double times);
	
	/**
	 * @param hours セットする その他休暇時間数。
	 */
	void setHours(int hours);
}
