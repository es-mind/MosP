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

import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;

/**
 * 休暇確認一覧DTOインターフェース。
 */
public interface HolidayManagementListDtoInterface
		extends PlatformDtoInterface, EmployeeCodeDtoInterface, EmployeeNameDtoInterface, SectionCodeDtoInterface {
	
	/**
	 * @return 休暇種別。
	 */
	String getHolidayCode();
	
	/**
	 * @param holidayCode セットする 休暇種別。
	 */
	void setHolidayCode(String holidayCode);
	
	/**
	 * @return 残日数。
	 */
	double getHolidayRemainder();
	
	/**
	 * @param holidayRemainder セットする 残日数。
	 */
	void setHolidayRemainder(double holidayRemainder);
	
	/**
	 * @return 残時間
	 */
	int getHolidayRemaindHours();
	
	/**
	 * @param holidayRemaindHours 残時間
	 */
	void setHolidayRemaindHours(int holidayRemaindHours);
	
	/**
	 * @return 残分数
	 */
	int getHolidayRemaindMinutes();
	
	/**
	 * @param holidayRemaindMinutes 残分数
	 */
	void setHolidayRemaindMinutes(int holidayRemaindMinutes);
	
	/**
	 * @return 取得期限。
	 */
	Date getHolidayLimit();
	
	/**
	 * @param holidayLimit セットする 取得期限。
	 */
	void setHolidayLimit(Date holidayLimit);
	
}
