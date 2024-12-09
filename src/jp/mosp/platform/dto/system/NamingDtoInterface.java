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
package jp.mosp.platform.dto.system;

import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.dto.base.NamingItemCodeDtoInterface;

/**
 * 名称区分マスタDTOインターフェース。
 */
public interface NamingDtoInterface extends PlatformDtoInterface, NamingItemCodeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmNamingId();
	
	/**
	 * @return 名称区分コード。
	 */
	String getNamingType();
	
	/**
	 * @return 名称項目名称。
	 */
	String getNamingItemName();
	
	/**
	 * @return 名称項目略称。
	 */
	String getNamingItemAbbr();
	
	/**
	 * @param pfmNamingId セットする レコード識別ID。
	 */
	void setPfmNamingId(long pfmNamingId);
	
	/**
	 * @param namingType セットする 名称区分コード。
	 */
	void setNamingType(String namingType);
	
	/**
	 * @param namingItemName セットする 名称項目名称。
	 */
	void setNamingItemName(String namingItemName);
	
	/**
	 * @param namingItemAbbr セットする 名称項目略称。
	 */
	void setNamingItemAbbr(String namingItemAbbr);
	
}
