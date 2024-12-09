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
package jp.mosp.platform.dto.system.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.IcCardDtoInterface;

/**
 * ICカードマスタDTO。
 */
public class PfmIcCardDto extends BaseDto implements IcCardDtoInterface {
	
	private static final long	serialVersionUID	= 1579984291030491019L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmIcCardId;
	
	/**
	 * カードID。
	 */
	private String				icCardId;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getPfmIcCardId() {
		return pfmIcCardId;
	}
	
	@Override
	public String getIcCardId() {
		return icCardId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
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
	public void setPfmIcCardId(long pfmIcCardId) {
		this.pfmIcCardId = pfmIcCardId;
	}
	
	@Override
	public void setIcCardId(String icCardId) {
		this.icCardId = icCardId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
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
