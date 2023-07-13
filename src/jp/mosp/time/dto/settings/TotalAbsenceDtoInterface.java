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
package jp.mosp.time.dto.settings;

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * 欠勤集計データDTOインターフェース。
 */
public interface TotalAbsenceDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdTotalAbsenceId();
	
	/**
	 * @param tmdTotalAbsenceId セットする レコード識別ID。
	 */
	void setTmdTotalAbsenceId(long tmdTotalAbsenceId);
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @return 年。
	 */
	int getCalculationYear();
	
	/**
	 * @param calculationYear セットする 年。
	 */
	void setCalculationYear(int calculationYear);
	
	/**
	 * @return 月。
	 */
	int getCalculationMonth();
	
	/**
	 * @param calculationMonth セットする 月。
	 */
	void setCalculationMonth(int calculationMonth);
	
	/**
	 * @return 欠勤コード。
	 */
	String getAbsenceCode();
	
	/**
	 * @param absenceCode セットする 欠勤コード。
	 */
	void setAbsenceCode(String absenceCode);
	
	/**
	 * @return 欠勤日数。
	 */
	double getTimes();
	
	/**
	 * @param times セットする 欠勤日数。
	 */
	void setTimes(double times);
	
	/**
	 * @return 欠勤時間数。
	 */
	int getHours();
	
	/**
	 * @param hours セットする 欠勤時間数。
	 */
	void setHours(int hours);
}
