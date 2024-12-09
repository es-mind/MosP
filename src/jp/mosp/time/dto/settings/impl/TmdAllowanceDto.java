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
import jp.mosp.time.dto.settings.AllowanceDtoInterface;

/**
 * 手当データDTO
 */
public class TmdAllowanceDto extends BaseDto implements AllowanceDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 1463454931637535771L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdAllowanceId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 勤務日。
	 */
	private Date				workDate;
	/**
	 * 勤務回数。
	 */
	private int					works;
	/**
	 * 手当コード。
	 */
	private String				allowanceCode;
	/**
	 * 手当。
	 */
	private int					allowance;
	
	
	@Override
	public int getAllowance() {
		return allowance;
	}
	
	@Override
	public String getAllowanceCode() {
		return allowanceCode;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public long getTmdAllowanceId() {
		return tmdAllowanceId;
	}
	
	@Override
	public Date getWorkDate() {
		return getDateClone(workDate);
	}
	
	@Override
	public int getWorks() {
		return works;
	}
	
	@Override
	public void setAllowance(int allowance) {
		this.allowance = allowance;
	}
	
	@Override
	public void setAllowanceCode(String allowanceCode) {
		this.allowanceCode = allowanceCode;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setTmdAllowanceId(long tmdAllowanceId) {
		this.tmdAllowanceId = tmdAllowanceId;
	}
	
	@Override
	public void setWorkDate(Date workDate) {
		this.workDate = getDateClone(workDate);
	}
	
	@Override
	public void setWorks(int works) {
		this.works = works;
	}
	
}
