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
 * 勤務形態パターンマスタDTOインターフェース
 */
public interface WorkTypePatternDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmWorkTypePatternId();
	
	/**
	 * @return パターンコード。
	 */
	String getPatternCode();
	
	/**
	 * @return パターン名称。
	 */
	String getPatternName();
	
	/**
	 * @return パターン略称。
	 */
	String getPatternAbbr();
	
	/**
	 * @param tmmWorkTypePatternId セットする レコード識別ID。
	 */
	void setTmmWorkTypePatternId(long tmmWorkTypePatternId);
	
	/**
	 * @param patternCode セットする パターンコード。
	 */
	void setPatternCode(String patternCode);
	
	/**
	 * @param patternName セットする パターン名称。
	 */
	void setPatternName(String patternName);
	
	/**
	 * @param patternAbbr セットする パターン略称。
	 */
	void setPatternAbbr(String patternAbbr);
	
}
