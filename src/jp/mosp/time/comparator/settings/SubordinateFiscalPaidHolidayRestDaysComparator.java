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

import jp.mosp.time.dto.settings.SubordinateFiscalListDtoInterface;

/**
 * 有休残比較クラス。<br>
 */
public class SubordinateFiscalPaidHolidayRestDaysComparator implements Comparator<SubordinateFiscalListDtoInterface> {
	
	@Override
	public int compare(SubordinateFiscalListDtoInterface dto1, SubordinateFiscalListDtoInterface dto2) {
		// 有給休暇残日数を比較
		int compare = Double.compare(dto1.getPaidHolidayRestDays(), dto2.getPaidHolidayRestDays());
		// 有給休暇残日数が同じである場合
		if (compare == 0) {
			// 有給休暇残時間数を比較
			compare = dto1.getPaidHolidayRestTime() - dto2.getPaidHolidayRestTime();
		}
		// 比較結果を取得
		return compare;
	}
	
}
