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
package jp.mosp.platform.comparator.base;

import java.util.Comparator;
import java.util.Date;

import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.ActivateDtoInterface;

/**
 * 有効日による比較クラス。<br>
 */
public class ActivateDateComparator implements Comparator<ActivateDtoInterface> {
	
	@Override
	public int compare(ActivateDtoInterface dto1, ActivateDtoInterface dto2) {
		// 有効日を取得
		Date date1 = dto1.getActivateDate();
		Date date2 = dto2.getActivateDate();
		// 日付を比較
		return MospUtility.compare(date1, date2);
	}
	
}
