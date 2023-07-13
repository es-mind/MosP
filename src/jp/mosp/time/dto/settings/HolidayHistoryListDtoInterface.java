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
 * 休暇付与一覧DTOインターフェース。
 */
public interface HolidayHistoryListDtoInterface
		extends PlatformDtoInterface, EmployeeNameDtoInterface, SectionCodeDtoInterface, EmployeeCodeDtoInterface {
	
	/**
	 * @return 休暇種別。
	 */
	String getHolidayCode();
	
	/**
	 * @param holidayCode セットする 休暇種別。
	 */
	void setHolidayCode(String holidayCode);
	
	/**
	 * @return 付与日数。
	 */
	double getHolidayGiving();
	
	/**
	 * @param holidayGiving セットする 付与日数。
	 */
	void setHolidayGiving(double holidayGiving);
	
	/**
	 * @return 取得期限。
	 */
	Date getHolidayLimit();
	
	/**
	 * @param holidayLimit セットする 取得期限。
	 */
	void setHolidayLimit(Date holidayLimit);
	
}
