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
package jp.mosp.platform.dto.human;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 人事汎用履歴情報DTOインターフェース。
 */
public interface HumanHistoryDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfaHumanHistoryId();
	
	/**
	 * @return 個人ID
	 */
	String getPersonalId();
	
	/**
	 * @return 人事項目区分。
	 */
	String getHumanItemType();
	
	/**
	 * @return 人事項目値。
	 */
	String getHumanItemValue();
	
	/**
	 * @param pfaHumanHistoryId セットする レコード識別ID。
	 */
	void setPfaHumanHistoryId(long pfaHumanHistoryId);
	
	/**
	 * @param personalId セットする 個人ID
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param humanItemType セットする 人事項目区分。
	 */
	void setHumanItemType(String humanItemType);
	
	/**
	 * @param humanItemValue セットする 人事項目値。
	 */
	void setHumanItemValue(String humanItemValue);
	
}
