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
package jp.mosp.platform.dto.system.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.NamingDtoInterface;

/**
 * 名称区分マスタDTO。<br>
 */
public class PfmNamingDto extends BaseDto implements NamingDtoInterface {
	
	private static final long	serialVersionUID	= 1490247200020812464L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmNamingId;
	
	/**
	 * 名称区分コード。
	 */
	private String				namingType;
	
	/**
	 * 名称項目コード。
	 */
	private String				namingItemCode;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 名称項目名称。
	 */
	private String				namingItemName;
	
	/**
	 * 名称項目略称。
	 */
	private String				namingItemAbbr;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getPfmNamingId() {
		return pfmNamingId;
	}
	
	@Override
	public String getNamingType() {
		return namingType;
	}
	
	@Override
	public String getNamingItemCode() {
		return namingItemCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public String getNamingItemName() {
		return namingItemName;
	}
	
	@Override
	public String getNamingItemAbbr() {
		return namingItemAbbr;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setPfmNamingId(long pfmNamingId) {
		this.pfmNamingId = pfmNamingId;
	}
	
	@Override
	public void setNamingType(String namingType) {
		this.namingType = namingType;
	}
	
	@Override
	public void setNamingItemCode(String namingItemCode) {
		this.namingItemCode = namingItemCode;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setNamingItemName(String namingItemName) {
		this.namingItemName = namingItemName;
	}
	
	@Override
	public void setNamingItemAbbr(String namingItemAbbr) {
		this.namingItemAbbr = namingItemAbbr;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
