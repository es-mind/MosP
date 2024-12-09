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

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;

/**
 * 社員勤怠集計管理DTOインターフェース。
 */
public interface TotalTimeEmployeeDtoInterface extends BaseDtoInterface, PersonalIdDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmtTotalTimeEmployeeId();
	
	/**
	 * @param tmtTotalTimeEmployeeId セットする レコード識別ID。
	 */
	void setTmtTotalTimeEmployeeId(long tmtTotalTimeEmployeeId);
	
	/**
	 * @return 集計年。
	 */
	int getCalculationYear();
	
	/**
	 * @param calculationYear セットする 集計年。
	 */
	void setCalculationYear(int calculationYear);
	
	/**
	 * @return 集計月。
	 */
	int getCalculationMonth();
	
	/**
	 * @param calculationMonth セットする 集計月。
	 */
	void setCalculationMonth(int calculationMonth);
	
	/**
	 * @return 締日コード。
	 */
	String getCutoffCode();
	
	/**
	 * @param cutoffCode セットする 締日コード。
	 */
	void setCutoffCode(String cutoffCode);
	
	/**
	 * @return 集計日。
	 */
	Date getCalculationDate();
	
	/**
	 * @param calculationDate セットする 集計日。
	 */
	void setCalculationDate(Date calculationDate);
	
	/**
	 * @return 締状態。
	 */
	int getCutoffState();
	
	/**
	 * @param cutoffState セットする 締状態。
	 */
	void setCutoffState(int cutoffState);
	
}
