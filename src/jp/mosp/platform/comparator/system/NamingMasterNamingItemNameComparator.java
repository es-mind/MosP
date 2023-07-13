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
package jp.mosp.platform.comparator.system;

import java.util.Comparator;

import jp.mosp.platform.dto.system.NamingDtoInterface;

/**
 * 名称区分マスタ
 * 名称項目名の比較クラス。
 */
public class NamingMasterNamingItemNameComparator implements Comparator<Object> {
	
	@Override
	public int compare(Object o1, Object o2) {
		NamingDtoInterface dto1 = (NamingDtoInterface)o1;
		NamingDtoInterface dto2 = (NamingDtoInterface)o2;
		return dto1.getNamingItemName().compareTo(dto2.getNamingItemName());
	}
}
