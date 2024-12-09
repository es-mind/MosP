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
package jp.mosp.platform.dto.file;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * インポート管理DTOインターフェース。<br>
 */
public interface ImportDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmImportId();
	
	/**
	 * @return インポートコード。
	 */
	String getImportCode();
	
	/**
	 * @return インポート名称。
	 */
	String getImportName();
	
	/**
	 * @return データ区分。
	 */
	String getImportTable();
	
	/**
	 * @return データ型。
	 */
	String getType();
	
	/**
	 * @return ヘッダ有無。
	 */
	int getHeader();
	
	/**
	 * @param tmmImportId セットする レコード識別ID。
	 */
	void setPfmImportId(long tmmImportId);
	
	/**
	 * @param importCode セットする インポートコード。
	 */
	void setImportCode(String importCode);
	
	/**
	 * @param importName セットする インポート名称。
	 */
	void setImportName(String importName);
	
	/**
	 * @param importTable セットする データ区分。
	 */
	void setImportTable(String importTable);
	
	/**
	 * @param type セットする データ型。
	 */
	void setType(String type);
	
	/**
	 * @param header セットする ヘッダ有無。
	 */
	void setHeader(int header);
}
