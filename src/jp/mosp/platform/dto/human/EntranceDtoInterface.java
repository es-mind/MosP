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
package jp.mosp.platform.dto.human;

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * 人事入社情報DTOインターフェース
 */
public interface EntranceDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfaHumanEntranceId();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @return 入社日。
	 */
	Date getEntranceDate();
	
	/**
	 * @param pfaHumanEntranceId セットする レコード識別ID。
	 */
	void setPfaHumanEntranceId(long pfaHumanEntranceId);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param entranceDate セットする 入社日。
	 */
	void setEntranceDate(Date entranceDate);
	
}
