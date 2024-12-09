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

import jp.mosp.platform.dto.base.WorkPlaceCodeDtoInterface;

/**
 * 勤務地コードによる比較クラス。<br>
 */
public class WorkPlaceCodeComparator implements Comparator<WorkPlaceCodeDtoInterface> {
	
	@Override
	public int compare(WorkPlaceCodeDtoInterface dto1, WorkPlaceCodeDtoInterface dto2) {
		return dto1.getWorkPlaceCode().compareTo(dto2.getWorkPlaceCode());
	}
	
}
