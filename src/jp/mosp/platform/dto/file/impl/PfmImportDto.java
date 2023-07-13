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
package jp.mosp.platform.dto.file.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.file.ImportDtoInterface;

/**
 * インポート管理DTO。<br>
 */
public class PfmImportDto extends BaseDto implements ImportDtoInterface {
	
	private static final long	serialVersionUID	= -2594173809749457416L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmImportId;
	
	/**
	 * インポートコード。
	 */
	private String				importCode;
	
	/**
	 * インポート名称。
	 */
	private String				importName;
	
	/**
	 * データ区分。
	 */
	private String				importTable;
	
	/**
	 * データ型。
	 */
	private String				type;
	
	/**
	 * ヘッダ有無。
	 */
	private int					header;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public int getHeader() {
		return header;
	}
	
	@Override
	public String getImportCode() {
		return importCode;
	}
	
	@Override
	public String getImportName() {
		return importName;
	}
	
	@Override
	public String getImportTable() {
		return importTable;
	}
	
	@Override
	public long getPfmImportId() {
		return pfmImportId;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public void setHeader(int header) {
		this.header = header;
	}
	
	@Override
	public void setImportCode(String importCode) {
		this.importCode = importCode;
	}
	
	@Override
	public void setImportName(String importName) {
		this.importName = importName;
	}
	
	@Override
	public void setImportTable(String importTable) {
		this.importTable = importTable;
	}
	
	@Override
	public void setPfmImportId(long tmmImportId) {
		pfmImportId = tmmImportId;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public Date getActivateDate() {
		// 処理無し
		return null;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		// 処理無し
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
