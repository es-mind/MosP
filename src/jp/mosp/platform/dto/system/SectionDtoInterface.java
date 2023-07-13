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
package jp.mosp.platform.dto.system;

import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.dto.base.ClassRouteDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;

/**
 * 所属マスタDTOインターフェース
 */
public interface SectionDtoInterface extends PlatformDtoInterface, SectionCodeDtoInterface, ClassRouteDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmSectionId();
	
	/**
	 * @return 所属名称。
	 */
	String getSectionName();
	
	/**
	 * @return 所属略称。
	 */
	String getSectionAbbr();
	
	/**
	 * @return 所属表示名称。
	 */
	String getSectionDisplay();
	
	/**
	 * @return 閉鎖フラグ。
	 */
	int getCloseFlag();
	
	/**
	 * @param pfmSectionId セットする レコード識別ID。
	 */
	void setPfmSectionId(long pfmSectionId);
	
	/**
	 * @param sectionName セットする 所属名称。
	 */
	void setSectionName(String sectionName);
	
	/**
	 * @param sectionAbbr セットする 所属略称。
	 */
	void setSectionAbbr(String sectionAbbr);
	
	/**
	 * @param sectionDisplay セットする 所属表示名称。
	 */
	void setSectionDisplay(String sectionDisplay);
	
	/**
	 * @param closeFlag セットする 閉鎖フラグ。
	 */
	void setCloseFlag(int closeFlag);
	
}
