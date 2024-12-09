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
import java.util.Date;

import jp.mosp.time.dto.settings.PaidHolidayDataGrantListDtoInterface;

/**
 * 
 */
public class PaidHolidayDataGrantListGrantDateComparator implements Comparator<PaidHolidayDataGrantListDtoInterface> {
	
	@Override
	public int compare(PaidHolidayDataGrantListDtoInterface dto1, PaidHolidayDataGrantListDtoInterface dto2) {
		return compare(dto1.getGrantDate(), dto2.getGrantDate());
	}
	
	private int compare(Date date1, Date date2) {
		if (date1 == null && date2 == null) {
			return 0;
		}
		if (date1 == null) {
			return -1;
		}
		if (date2 == null) {
			return 1;
		}
		return date1.compareTo(date2);
	}
}
