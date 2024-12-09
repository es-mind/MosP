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
package jp.mosp.platform.dto.base;

/**
 * 個人IDを有するDTOのインターフェース。<br>
 */
public interface PersonalIdDtoInterface {
	
	/**
	 * 個人IDを取得する。
	 * @return 個人ID
	 */
	String getPersonalId();
	
	/**
	 * 個人IDを設定する。
	 * @param personalId セットする個人ID
	 */
	void setPersonalId(String personalId);
	
}
