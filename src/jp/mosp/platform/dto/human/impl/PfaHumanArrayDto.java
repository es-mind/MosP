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
package jp.mosp.platform.dto.human.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.human.HumanArrayDtoInterface;

/**
 * 人事汎用一覧情報DTO。
 */
public class PfaHumanArrayDto extends BaseDto implements HumanArrayDtoInterface {
	
	private static final long	serialVersionUID	= 5626623850311645656L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaHumanArrayId;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 人事項目区分。
	 */
	private String				humanItemType;
	
	/**
	 * 人事項目値。
	 */
	private String				humanItemValue;
	
	/**
	 * 行ID。
	 */
	private int					humanRowId;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanArrayDto() {
		// 処理無し
	}
	
	@Override
	public long getPfaHumanArrayId() {
		return pfaHumanArrayId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public String getHumanItemType() {
		return humanItemType;
	}
	
	@Override
	public String getHumanItemValue() {
		return humanItemValue;
	}
	
	@Override
	public int getHumanRowId() {
		return humanRowId;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setPfaHumanArrayId(long pfaHumanArrayId) {
		this.pfaHumanArrayId = pfaHumanArrayId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public void setHumanItemType(String humanItemType) {
		this.humanItemType = humanItemType;
	}
	
	@Override
	public void setHumanItemValue(String humanItemValue) {
		this.humanItemValue = humanItemValue;
	}
	
	@Override
	public void setHumanRowId(int humanRowId) {
		this.humanRowId = humanRowId;
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
