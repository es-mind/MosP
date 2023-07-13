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
import jp.mosp.time.dto.settings.WorkTypePatternDtoInterface;

/**
 * 勤務形態パターンマスタDTO
 */
public class TmmWorkTypePatternDto extends BaseDto implements WorkTypePatternDtoInterface {
	
	private static final long	serialVersionUID	= 9192339565154404030L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmWorkTypePatternId;
	/**
	 * パターンコード。
	 */
	private String				patternCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * パターン名称。
	 */
	private String				patternName;
	/**
	 * パターン略称。
	 */
	private String				patternAbbr;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getTmmWorkTypePatternId() {
		return tmmWorkTypePatternId;
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
	public String getPatternName() {
		return patternName;
	}
	
	@Override
	public String getPatternAbbr() {
		return patternAbbr;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setTmmWorkTypePatternId(long tmmWorkTypePatternId) {
		this.tmmWorkTypePatternId = tmmWorkTypePatternId;
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
	public void setPatternName(String patternName) {
		this.patternName = patternName;
	}
	
	@Override
	public void setPatternAbbr(String patternAbbr) {
		this.patternAbbr = patternAbbr;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
