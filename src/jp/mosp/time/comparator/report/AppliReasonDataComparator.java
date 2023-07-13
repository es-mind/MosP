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
package jp.mosp.time.comparator.report;

import java.util.Comparator;

/**
 * 申請理由データ比較クラス。<br>
 */
public class AppliReasonDataComparator implements Comparator<String[]> {
	
	@Override
	public int compare(String[] o1, String[] o2) {
		
		// 1.社員コード、2.申請区分、3.日付、4.詳細1、5.詳細2、6.詳細3、7.詳細4
		int compareTo = 0;
		compareTo = o1[0].compareTo(o2[0]);
		if (compareTo == 0) {
			compareTo = o1[5].compareTo(o2[5]);
			if (compareTo == 0) {
				compareTo = o1[4].compareTo(o2[4]);
				if (compareTo == 0) {
					compareTo = o1[6].compareTo(o2[6]);
					if (compareTo == 0) {
						compareTo = o1[7].compareTo(o2[7]);
						if (compareTo == 0) {
							compareTo = o1[8].compareTo(o2[8]);
							if (compareTo == 0) {
								compareTo = o1[9].compareTo(o2[9]);
							}
						}
					}
				}
			}
		}
		
		return compareTo;
	}
	
}
