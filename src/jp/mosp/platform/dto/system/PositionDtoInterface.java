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
import jp.mosp.platform.dto.base.PositionCodeDtoInterface;

/**
 * 職位マスタDTOインターフェース
 */
public interface PositionDtoInterface extends PlatformDtoInterface, PositionCodeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmPositionId();
	
	/**
	 * @return 職位名称。
	 */
	String getPositionName();
	
	/**
	 * @return 職位略称。
	 */
	String getPositionAbbr();
	
	/**
	 * @return 等級。
	 */
	int getPositionGrade();
	
	/**
	 * @return 号数。
	 */
	int getPositionLevel();
	
	/**
	 * @param pfmPositionId セットする レコード識別ID。
	 */
	void setPfmPositionId(long pfmPositionId);
	
	/**
	 * @param positionName セットする 職位名称。
	 */
	void setPositionName(String positionName);
	
	/**
	 * @param positionAbbr セットする 職位略称。
	 */
	void setPositionAbbr(String positionAbbr);
	
	/**
	 * @param positionGrade セットする 等級。
	 */
	void setPositionGrade(int positionGrade);
	
	/**
	 * @param positionLevel セットする 号数。
	 */
	void setPositionLevel(int positionLevel);
	
}
