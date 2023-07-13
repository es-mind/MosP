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
 * エクスポートマスタDTOインターフェース。<br>
 */
public interface ExportDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmExportId();
	
	/**
	 * @return エクスポートコード。
	 */
	String getExportCode();
	
	/**
	 * @return エクスポート名称。
	 */
	String getExportName();
	
	/**
	 * @return データ区分。
	 */
	String getExportTable();
	
	/**
	 * @return データ型。
	 */
	String getType();
	
	/**
	 * @return ヘッダ有無。
	 */
	int getHeader();
	
	/**
	 * @param pfmExportId セットする レコード識別ID。
	 */
	void setPfmExportId(long pfmExportId);
	
	/**
	 * @param exportCode セットする エクスポートコード。
	 */
	void setExportCode(String exportCode);
	
	/**
	 * @param exportName セットする エクスポート名称。
	 */
	void setExportName(String exportName);
	
	/**
	 * @param exportTable セットする データ区分。
	 */
	void setExportTable(String exportTable);
	
	/**
	 * @param type セットする データ型。
	 */
	void setType(String type);
	
	/**
	 * @param header セットする ヘッダ有無。
	 */
	void setHeader(int header);
	
}
