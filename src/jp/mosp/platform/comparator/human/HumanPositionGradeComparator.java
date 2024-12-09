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
package jp.mosp.platform.comparator.human;

import java.util.Comparator;
import java.util.Map;

import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.PositionDtoInterface;

/**
 * 職位等級による比較クラス。<br>
 * 比較職位マップから職位情報を取得し、比較する。<br>
 */
public class HumanPositionGradeComparator implements Comparator<HumanDtoInterface> {
	
	/**
	 * 比較職位マスタマップ。<br>
	 * キーは職位コード。<br>
	 */
	protected Map<String, PositionDtoInterface>	positionMap;
	
	/**
	 * 等級優劣(true：等級の小さい方が優、false：等級の大きい方が優)。<br>
	 */
	protected boolean							hasLowGradeAdvantage;
	
	
	/**
	 * 比較職位マスタマップを設定する。<br>
	 * @param positionMap          比較職位マップ
	 * @param hasLowGradeAdvantage 等級優劣(true：等級の小さい方が優、false：等級の大きい方が優)
	 */
	public HumanPositionGradeComparator(Map<String, PositionDtoInterface> positionMap, boolean hasLowGradeAdvantage) {
		this.positionMap = positionMap;
	}
	
	@Override
	public int compare(HumanDtoInterface dto1, HumanDtoInterface dto2) {
		// 等級準備
		int grade1 = 0;
		int grade2 = 0;
		// 職位を取得
		PositionDtoInterface position1 = positionMap.get(dto1.getPositionCode());
		PositionDtoInterface position2 = positionMap.get(dto2.getPositionCode());
		// 等級設定
		if (position1 != null) {
			grade1 = position1.getPositionGrade();
		}
		if (position2 != null) {
			grade2 = position2.getPositionGrade();
		}
		// 比較
		if (hasLowGradeAdvantage) {
			return grade1 - grade2;
		}
		return grade2 - grade1;
	}
	
}
