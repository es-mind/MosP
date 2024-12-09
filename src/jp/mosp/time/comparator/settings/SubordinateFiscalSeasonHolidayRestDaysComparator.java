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
 * 休暇残比較クラス。<br>
 */
public class SubordinateFiscalSeasonHolidayRestDaysComparator implements Comparator<SubordinateFiscalListDtoInterface> {
	
	@Override
	public int compare(SubordinateFiscalListDtoInterface dto1, SubordinateFiscalListDtoInterface dto2) {
		// 季休残日数を比較
		int compare = Double.compare(dto1.getSeasonHolidayRestDays(), dto2.getSeasonHolidayRestDays());
		// 季休残日数が同じである場合
		if (compare == 0) {
			// 季休残時間数を比較
			compare = dto1.getSeasonHolidayRestHours() - dto2.getSeasonHolidayRestHours();
		}
		// 比較結果を取得
		return compare;
	}
	
}
