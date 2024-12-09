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
/**
 * 
 */
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.LimitStandardDtoInterface;

/**
 * 限度基準マスタDTO
 */
public class TmmLimitStandardDto extends BaseDto implements LimitStandardDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 238741646937899491L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmLimitStandardId;
	/**
	 * 勤怠設定コード。
	 */
	private String				workSettingCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 期間。
	 */
	private String				term;
	/**
	 * 時間外限度時間。
	 */
	private int					limitTime;
	/**
	 * 時間外注意時間。
	 */
	private int					attentionTime;
	/**
	 * 時間外警告時間
	 */
	private int					warningTime;
	
	
	@Override
	public int getAttentionTime() {
		return attentionTime;
	}
	
	@Override
	public int getLimitTime() {
		return limitTime;
	}
	
	@Override
	public String getTerm() {
		return term;
	}
	
	@Override
	public long getTmmLimitStandardId() {
		return tmmLimitStandardId;
	}
	
	@Override
	public int getWarningTime() {
		return warningTime;
	}
	
	@Override
	public String getWorkSettingCode() {
		return workSettingCode;
	}
	
	@Override
	public void setAttentionTime(int attentionTime) {
		this.attentionTime = attentionTime;
	}
	
	@Override
	public void setLimitTime(int limitTime) {
		this.limitTime = limitTime;
	}
	
	@Override
	public void setTerm(String term) {
		this.term = term;
	}
	
	@Override
	public void setTmmLimitStandardId(long tmmLimitStandardId) {
		this.tmmLimitStandardId = tmmLimitStandardId;
	}
	
	@Override
	public void setWarningTime(int warningTime) {
		this.warningTime = warningTime;
	}
	
	@Override
	public void setWorkSettingCode(String workSettingCode) {
		this.workSettingCode = workSettingCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
}
