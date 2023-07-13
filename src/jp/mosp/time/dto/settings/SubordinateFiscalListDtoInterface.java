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
import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;

/**
 * 統計情報一覧DTOインターフェース
 */
public interface SubordinateFiscalListDtoInterface extends PersonalIdDtoInterface, EmployeeCodeDtoInterface,
		EmployeeNameDtoInterface, SectionCodeDtoInterface, BaseDtoInterface {
	
	/**
	 * @return 表示年度
	 */
	int getFiscalYear();
	
	/**
	 * @return 対象年
	 */
	int getTargetYear();
	
	/**
	 * @return 対象月
	 */
	int getTargetMonth();
	
	/**
	 * @return 残業時間。
	 */
	int getOverTime();
	
	/**
	 * @return 普休始(日)。
	 */
	double getPaidHolidayDays();
	
	/**
	 * @return 普休始(時間)。
	 */
	int getPaidHolidayTime();
	
	/**
	 * @return 普休残(日)。
	 */
	double getPaidHolidayRestDays();
	
	/**
	 * @return 普休残(時間)。
	 */
	int getPaidHolidayRestTime();
	
	/**
	 * @return 保休始。
	 */
	double getStockHolidayDays();
	
	/**
	 * @return 保休残。
	 */
	double getStockHolidayRestDays();
	
	/**
	 * @return 季休始。
	 */
	double getSeasonHolidayDays();
	
	/**
	 * @return 季休残日数
	 */
	double getSeasonHolidayRestDays();
	
	/**
	 * @return 季休残時間数
	 */
	int getSeasonHolidayRestHours();
	
	/**
	 * @return 季休残分数
	 */
	int getSeasonHolidayRestMinutes();
	
	/**
	 * @param fiscalYear 表示年度
	 */
	void setFiscalYear(int fiscalYear);
	
	/**
	 * @param targetYear 対象年
	 */
	void setTargetYear(int targetYear);
	
	/**
	 * @param targetMonth 対象月
	 */
	void setTargetMonth(int targetMonth);
	
	/**
	 * @param overTime 残業時間
	 */
	void setOverTime(int overTime);
	
	/**
	 * @param paidHolidayDays 普休始(日)。
	 */
	void setPaidHolidayDays(double paidHolidayDays);
	
	/**
	 * @param paidHolidayTime 普休始(時間)。
	 */
	void setPaidHolidayTime(int paidHolidayTime);
	
	/**
	 * @param paidHolidayRestDays 普休残(日)。
	 */
	void setPaidHolidayRestDays(double paidHolidayRestDays);
	
	/**
	 * @param paidHolidayRestTime 普休残(時間)。
	 */
	void setPaidHolidayRestTime(int paidHolidayRestTime);
	
	/**
	 * @param stockHolidayDays 保休始。
	 */
	void setStockHolidayDays(double stockHolidayDays);
	
	/**
	 * @param stockHolidayRestDays 保休残。
	 */
	void setStockHolidayRestDays(double stockHolidayRestDays);
	
	/**
	 * @param seasonHolidayDays 季休始。
	 */
	void setSeasonHolidayDays(double seasonHolidayDays);
	
	/**
	 * @param seasonHolidayRestDays 季休残日数
	 */
	void setSeasonHolidayRestDays(double seasonHolidayRestDays);
	
	/**
	 * @param seasonHolidayRestHours 季休残時間数
	 */
	void setSeasonHolidayRestHours(int seasonHolidayRestHours);
	
	/**
	 * @param seasonHolidayRestMinutes 季休残分数
	 */
	void setSeasonHolidayRestMinutes(int seasonHolidayRestMinutes);
	
}
