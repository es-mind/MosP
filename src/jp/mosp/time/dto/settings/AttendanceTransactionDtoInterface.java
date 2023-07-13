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

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;

/**
 * 勤怠トランザクションDTOインターフェース
 */
public interface AttendanceTransactionDtoInterface extends BaseDtoInterface, PersonalIdDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmtAttendanceId();
	
	/**
	 * @return 勤務日。
	 */
	Date getWorkDate();
	
	/**
	 * @return 出勤区分。
	 */
	String getAttendanceType();
	
	/**
	 * @return 出勤率算定分子。
	 */
	int getNumerator();
	
	/**
	 * @return 出勤率算定分母。
	 */
	int getDenominator();
	
	/**
	 * @param tmtAttendanceId セットする レコード識別ID。
	 */
	void setTmtAttendanceId(long tmtAttendanceId);
	
	/**
	 * @param workDate セットする 勤務日。
	 */
	void setWorkDate(Date workDate);
	
	/**
	 * @param attendanceType セットする 出勤区分。
	 */
	void setAttendanceType(String attendanceType);
	
	/**
	 * @param numerator セットする 出勤率算定分子。
	 */
	void setNumerator(int numerator);
	
	/**
	 * @param denominator セットする 出勤率算定分母。
	 */
	void setDenominator(int denominator);
	
}
