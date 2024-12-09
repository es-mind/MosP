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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.WorkTypePatternItemDtoInterface;

/**
 * 勤務形態パターン項目DTOクラス
 */
public class TmaWorkTypePatternItemDto extends BaseDto implements WorkTypePatternItemDtoInterface {
	
	private static final long	serialVersionUID	= 2551469028884552705L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmaWorkTypePatternItemId;
	/**
	 * パターンコード。
	 */
	private String				patternCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 勤務形態コード。
	 */
	private String				workTypeCode;
	
	/**
	 * 項目順序。
	 */
	private int					itemOrder;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getTmaWorkTypePatternItemId() {
		return tmaWorkTypePatternItemId;
	}
	
	@Override
	public String getPatternCode() {
		return patternCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public String getWorkTypeCode() {
		return workTypeCode;
	}
	
	@Override
	public int getItemOrder() {
		return itemOrder;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setTmaWorkTypePatternItemId(long tmaWorkTypePatternItemId) {
		this.tmaWorkTypePatternItemId = tmaWorkTypePatternItemId;
	}
	
	@Override
	public void setPatternCode(String patternCode) {
		this.patternCode = patternCode;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	@Override
	public void setItemOrder(int itemOrder) {
		this.itemOrder = itemOrder;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
