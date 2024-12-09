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
package jp.mosp.time.comparator.report;

import java.util.Comparator;
import java.util.Date;

import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.time.dto.settings.impl.PaidHolidayUsageDto;

/**
 * 有給休暇取得状況確認情報比較処理。<br>
 */
public class PaidHolidayUsageComparator implements Comparator<PaidHolidayUsageDto> {
	
	@Override
	public int compare(PaidHolidayUsageDto dto1, PaidHolidayUsageDto dto2) {
		// 対象期間(To)を取得
		Date usageToDate1 = dto1.getUsageToDate();
		Date usageToDate2 = dto2.getUsageToDate();
		// 未消化日数(合算)を取得
		float shortDay1 = dto1.getShortDay();
		float shortDay2 = dto2.getShortDay();
		// 未消化日数(合算)が共に0である場合
		if (shortDay1 == 0F && shortDay2 == 0F) {
			// 対象期間(To)で比較
			return MospUtility.compare(usageToDate1, usageToDate2);
		}
		// 未消化日数(合算)1が0である場合
		if (Float.compare(shortDay1, 0F) == 0) {
			// 1の方が小さい(昇順で上にくる)と判断
			return 1;
		}
		// 未消化日数(合算)2が0である場合
		if (Float.compare(shortDay2, 0F) == 0) {
			// 1の方が大きい(昇順で下にくる)と判断
			return -1;
		}
		// 対象期間(To)が同じである場合
		if (DateUtility.isSame(usageToDate1, usageToDate2)) {
			// 未消化日数(合算)で比較(降順)
			return MospUtility.compare(shortDay2, shortDay1);
		}
		// 対象期間(To)で比較
		return MospUtility.compare(usageToDate1, usageToDate2);
	}
	
}
