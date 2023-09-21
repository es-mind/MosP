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
/**
 * 
 */
package jp.mosp.platform.comparator.system;

import java.util.Comparator;

import jp.mosp.platform.dto.system.SectionDtoInterface;

/**
 * @author j_koike
 *
 */
public class SectionMasterSectionNameComparator implements Comparator<Object> {
	
	@Override
	public int compare(Object o1, Object o2) {
		SectionDtoInterface dto1 = (SectionDtoInterface)o1;
		SectionDtoInterface dto2 = (SectionDtoInterface)o2;
		return dto1.getSectionName().compareTo(dto2.getSectionName());
	}
	
}
