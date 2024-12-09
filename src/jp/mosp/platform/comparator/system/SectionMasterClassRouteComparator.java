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
/**
 * 
 */
package jp.mosp.platform.comparator.system;

import java.util.Comparator;

import jp.mosp.platform.dto.system.SectionDtoInterface;

/**
 * 比較クラス。<br>
 */
public class SectionMasterClassRouteComparator implements Comparator<SectionDtoInterface> {
	
	@Override
	public int compare(SectionDtoInterface dto1, SectionDtoInterface dto2) {
		String classRoute1 = dto1.getClassRoute() + dto1.getSectionCode();
		String classRoute2 = dto2.getClassRoute() + dto2.getSectionCode();
		return classRoute1.compareTo(classRoute2);
	}
	
}
