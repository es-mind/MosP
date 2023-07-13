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
import jp.mosp.platform.dto.file.ExportDtoInterface;

/**
 * エクスポートマスタDTO。<br>
 */
public class PfmExportDto extends BaseDto implements ExportDtoInterface {
	
	private static final long	serialVersionUID	= -3961351360826795928L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmExportId;
	
	/**
	 * エクスポートコード
	 */
	private String				exportCode;
	
	/**
	 * エクスポート名称。
	 */
	private String				exportName;
	
	/**
	 * データ区分。
	 */
	private String				exportTable;
	
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
	public String getExportName() {
		return exportName;
	}
	
	@Override
	public String getExportTable() {
		return exportTable;
	}
	
	@Override
	public int getHeader() {
		return header;
	}
	
	@Override
	public long getPfmExportId() {
		return pfmExportId;
	}
	
	@Override
	public String getType() {
		return type;
	}
	
	@Override
	public String getExportCode() {
		return exportCode;
	}
	
	@Override
	public void setExportName(String exportName) {
		this.exportName = exportName;
	}
	
	@Override
	public void setExportTable(String exportTable) {
		this.exportTable = exportTable;
	}
	
	@Override
	public void setHeader(int header) {
		this.header = header;
	}
	
	@Override
	public void setPfmExportId(long tmmExportId) {
		pfmExportId = tmmExportId;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public void setExportCode(String exportCode) {
		this.exportCode = exportCode;
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
