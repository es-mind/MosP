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
/**
 * 
 */
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;

/**
 * 勤務形態項目管理DTO
 */
public class TmmWorkTypeItemDto extends BaseDto implements WorkTypeItemDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -1020148845628506100L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmWorkTypeItemId;
	/**
	 * 勤務形態コード。
	 */
	private String				workTypeCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 勤務形態項目コード。
	 */
	private String				workTypeItemCode;
	/**
	 * 勤務形態項目値。
	 */
	private Date				workTypeItemValue;
	/**
	 * 勤務形態項目値(予備)。
	 */
	private String				preliminary;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public String getPreliminary() {
		return preliminary;
	}
	
	@Override
	public long getTmmWorkTypeItemId() {
		return tmmWorkTypeItemId;
	}
	
	@Override
	public String getWorkTypeCode() {
		return workTypeCode;
	}
	
	@Override
	public String getWorkTypeItemCode() {
		return workTypeItemCode;
	}
	
	@Override
	public Date getWorkTypeItemValue() {
		return getDateClone(workTypeItemValue);
	}
	
	@Override
	public void setPreliminary(String preliminary) {
		this.preliminary = preliminary;
	}
	
	@Override
	public void setTmmWorkTypeItemId(long tmmWorkTypeItemId) {
		this.tmmWorkTypeItemId = tmmWorkTypeItemId;
	}
	
	@Override
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	@Override
	public void setWorkTypeItemCode(String workTypeItemCode) {
		this.workTypeItemCode = workTypeItemCode;
	}
	
	@Override
	public void setWorkTypeItemValue(Date workTypeItemValue) {
		this.workTypeItemValue = getDateClone(workTypeItemValue);
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
