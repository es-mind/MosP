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
package jp.mosp.time.comparator.settings;

import java.util.Comparator;

import jp.mosp.time.dto.settings.HolidayRequestListDtoInterface;

/**
 * 休暇申請一覧情報比較クラス(申請日)。<br>
 */
public class HolidayRequestRequestDateComparator implements Comparator<HolidayRequestListDtoInterface> {
	
	@Override
	public int compare(HolidayRequestListDtoInterface dto1, HolidayRequestListDtoInterface dto2) {
		int compareStartDate = dto1.getRequestStartDate().compareTo(dto2.getRequestStartDate());
		if (compareStartDate == 0) {
			int compareEndDate = dto1.getRequestEndDate().compareTo(dto2.getRequestEndDate());
			if (compareEndDate == 0) {
				// 申請時間差分
				int compareEndTime = dto1.getStartTime().compareTo(dto2.getStartTime());
				if (compareEndTime == 0) {
					// 休暇範囲差分
					return dto1.getHolidayRange() - dto2.getHolidayRange();
				}
				return compareEndTime;
			}
			return compareEndDate;
		}
		return compareStartDate;
	}
	
}
