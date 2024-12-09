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
import jp.mosp.platform.dto.human.EntranceDtoInterface;

/**
 * 人事入社情報DTO
 */
public class PfaHumanEntranceDto extends BaseDto implements EntranceDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 8748123475422632850L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaHumanEntranceId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 入社日。
	 */
	private Date				entranceDate;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanEntranceDto() {
		// 初期化
	}
	
	@Override
	public long getPfaHumanEntranceId() {
		return pfaHumanEntranceId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getEntranceDate() {
		return getDateClone(entranceDate);
	}
	
	@Override
	public void setPfaHumanEntranceId(long pfaHumanEntranceId) {
		this.pfaHumanEntranceId = pfaHumanEntranceId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setEntranceDate(Date entranceDate) {
		this.entranceDate = getDateClone(entranceDate);
	}
	
}
