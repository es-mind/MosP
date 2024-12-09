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
package jp.mosp.time.comparator.settings;

import java.util.Comparator;

import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dto.settings.HolidayDtoInterface;

/**
 *
 */
public class HolidayMasterHolidayLimitMonthComparator implements Comparator<HolidayDtoInterface> {
	
	@Override
	public int compare(HolidayDtoInterface dto1, HolidayDtoInterface dto2) {
		// 月と日の入力が独立しているため各dtoの取得期限を計算する
		int holidayLimitday1 = (dto1.getHolidayLimitMonth() * TimeConst.DATE_MONTH_MAX_DAY) + dto1.getHolidayLimitDay();
		int holidayLimitday2 = (dto2.getHolidayLimitMonth() * TimeConst.DATE_MONTH_MAX_DAY) + dto2.getHolidayLimitDay();
		return holidayLimitday2 - holidayLimitday1;
	}
	
}
