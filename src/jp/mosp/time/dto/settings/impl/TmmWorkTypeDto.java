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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;

/**
 * 勤務形態管理DTO
 */
public class TmmWorkTypeDto extends BaseDto implements WorkTypeDtoInterface {
	
	private static final long	serialVersionUID	= 4025082116565113034L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmWorkTypeId;
	/**
	 * 勤務形態コード。
	 */
	private String				workTypeCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 勤務形態名称。
	 */
	private String				workTypeName;
	/**
	 * 勤務形態略称。
	 */
	private String				workTypeAbbr;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getTmmWorkTypeId() {
		return tmmWorkTypeId;
	}
	
	@Override
	public String getWorkTypeAbbr() {
		return workTypeAbbr;
	}
	
	@Override
	public String getWorkTypeCode() {
		return workTypeCode;
	}
	
	@Override
	public String getWorkTypeName() {
		return workTypeName;
	}
	
	@Override
	public void setTmmWorkTypeId(long tmmWorkTypeId) {
		this.tmmWorkTypeId = tmmWorkTypeId;
	}
	
	@Override
	public void setWorkTypeAbbr(String workTypeAbbr) {
		this.workTypeAbbr = workTypeAbbr;
	}
	
	@Override
	public void setWorkTypeCode(String workTypeCode) {
		this.workTypeCode = workTypeCode;
	}
	
	@Override
	public void setWorkTypeName(String workTypeName) {
		this.workTypeName = workTypeName;
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
