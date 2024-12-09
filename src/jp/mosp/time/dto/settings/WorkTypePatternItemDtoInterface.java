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
package jp.mosp.time.dto.settings;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 勤務形態パターン項目DTOインターフェース
 */
public interface WorkTypePatternItemDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmaWorkTypePatternItemId();
	
	/**
	 * @return パターンコード。
	 */
	String getPatternCode();
	
	/**
	 * @return 勤務形態コード。
	 */
	String getWorkTypeCode();
	
	/**
	 * @return 項目順序。
	 */
	int getItemOrder();
	
	/**
	 * @param tmmWorkTypePatternItemId セットする レコード識別ID。
	 */
	void setTmaWorkTypePatternItemId(long tmmWorkTypePatternItemId);
	
	/**
	 * @param patternCode セットする パターンコード。
	 */
	void setPatternCode(String patternCode);
	
	/**
	 * @param workTypeCode セットする 勤務形態コード。
	 */
	void setWorkTypeCode(String workTypeCode);
	
	/**
	 * @param itemOrder セットする項目順序。
	 */
	void setItemOrder(int itemOrder);
	
}
