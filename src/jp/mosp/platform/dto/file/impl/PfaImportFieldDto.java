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
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;

/**
 * インポートフィールド管理DTO。<br>
 */
public class PfaImportFieldDto extends BaseDto implements ImportFieldDtoInterface {
	
	private static final long	serialVersionUID	= 4510432657295242828L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaImportFieldId;
	
	/**
	 * インポートコード。
	 */
	private String				importCode;
	
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
	public String getFieldName() {
		return fieldName;
	}
	
	@Override
	public int getFieldOrder() {
		return fieldOrder;
	}
	
	@Override
	public String getImportCode() {
		return importCode;
	}
	
	@Override
	public long getPfaImportFieldId() {
		return pfaImportFieldId;
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
	public void setImportCode(String importCode) {
		this.importCode = importCode;
	}
	
	@Override
	public void setPfaImportFieldId(long tmmImportFieldId) {
		pfaImportFieldId = tmmImportFieldId;
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
