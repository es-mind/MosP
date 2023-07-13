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
package jp.mosp.platform.dto.file;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * インポートフィールド管理DTOインターフェース。<br>
 */
public interface ImportFieldDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfaImportFieldId();
	
	/**
	 * @return インポートコード。
	 */
	String getImportCode();
	
	/**
	 * @return フィールド名称。
	 */
	String getFieldName();
	
	/**
	 * @return フィールド順序。
	 */
	int getFieldOrder();
	
	/**
	 * @param tmmImportFieldId セットする レコード識別ID。
	 */
	void setPfaImportFieldId(long tmmImportFieldId);
	
	/**
	 * @param importCode セットする インポートコード。
	 */
	void setImportCode(String importCode);
	
	/**
	 * @param fieldName セットする フィールド名称。
	 */
	void setFieldName(String fieldName);
	
	/**
	 * @param fieldOrder セットする フィールド順序。
	 */
	void setFieldOrder(int fieldOrder);
}
