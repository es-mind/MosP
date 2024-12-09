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
package jp.mosp.platform.dto.file.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;

/**
 * エクスポートフィールドマスタDTO。<br>
 */
public class PfaExportFieldDto extends BaseDto implements ExportFieldDtoInterface {
	
	private static final long	serialVersionUID	= 8232617714444380096L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaExportFieldId;
	
	/**
	 * エクスポートコード。
	 */
	private String				exportCode;
	
	/**
	 * フィールド名称。
	 */
	private String				fieldName;
	
	/**
	 * フィールド順序。
	 */
	private int					fieldOrder;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public String getExportCode() {
		return exportCode;
	}
	
	@Override
	public String getFieldName() {
		return fieldName;
	}
	
	@Override
	public int getFieldOrder() {
		return fieldOrder;
	}
	
	@Override
	public void setExportCode(String exportCode) {
		this.exportCode = exportCode;
	}
	
	@Override
	public void setFieldName(String fieldName) {
		this.fieldName = fieldName;
	}
	
	@Override
	public void setFieldOrder(int fieldOrder) {
		this.fieldOrder = fieldOrder;
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
	
	@Override
	public long getPfaExportFieldId() {
		return pfaExportFieldId;
	}
	
	@Override
	public void setPfaExportFieldId(long pfaExportFieldId) {
		this.pfaExportFieldId = pfaExportFieldId;
	}
	
}
