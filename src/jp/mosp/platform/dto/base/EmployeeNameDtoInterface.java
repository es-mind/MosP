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
 * 社員氏名を有するDTOのインターフェース。<br>
 */
public interface EmployeeNameDtoInterface {
	
	/**
	 * 社員氏名(姓)を取得する。
	 * @return 社員氏名(姓)
	 */
	String getLastName();
	
	/**
	 * 社員氏名(名)を取得する。
	 * @return 社員氏名(名)
	 */
	String getFirstName();
	
	/**
	 * 社員氏名(姓)を設定する。
	 * @param lastName セットする 社員氏名(姓)
	 */
	void setLastName(String lastName);
	
	/**
	 * 社員氏名(名)を設定する。
	 * @param firstName セットする 社員氏名(名)
	 */
	void setFirstName(String firstName);
	
}
