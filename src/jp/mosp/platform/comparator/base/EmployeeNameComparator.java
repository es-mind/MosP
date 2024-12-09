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
package jp.mosp.platform.comparator.base;

import java.util.Comparator;

import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;

/**
 * 社員氏名による比較クラス。<br>
 */
public class EmployeeNameComparator implements Comparator<EmployeeNameDtoInterface> {
	
	@Override
	public int compare(EmployeeNameDtoInterface dto1, EmployeeNameDtoInterface dto2) {
		// 社員氏名を準備
		String name1 = MospUtility.getHumansName(dto1.getFirstName(), dto1.getLastName());
		String name2 = MospUtility.getHumansName(dto2.getFirstName(), dto2.getLastName());
		// 社員氏名による比較
		return MospUtility.compare(name1, name2);
	}
	
}
