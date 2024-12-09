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

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.TotalLeaveDtoInterface;

/**
 * 特別休暇集計データDTO
 */
public class TmdTotalLeaveDto extends BaseDto implements TotalLeaveDtoInterface {
	
	private static final long	serialVersionUID	= -5733445653598336200L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdTotalLeaveId;
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 年。
	 */
	private int					calculationYear;
	/**
	 * 月。
	 */
	private int					calculationMonth;
	/**
	 * 休暇コード。
	 */
	private String				holidayCode;
	/**
	 * 特別休暇日数。
	 */
	private double				times;
	/**
	 * 特別休暇時間数。
	 */
	private int					hours;
	
	
	@Override
	public int getCalculationMonth() {
		return calculationMonth;
	}
	
	@Override
	public int getCalculationYear() {
		return calculationYear;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public String getHolidayCode() {
		return holidayCode;
	}
	
	@Override
	public double getTimes() {
		return times;
	}
	
	@Override
	public long getTmdTotalLeaveId() {
		return tmdTotalLeaveId;
	}
	
	@Override
	public void setCalculationMonth(int calculationMonth) {
		this.calculationMonth = calculationMonth;
	}
	
	@Override
	public void setCalculationYear(int calculationYear) {
		this.calculationYear = calculationYear;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setHolidayCode(String holidayCode) {
		this.holidayCode = holidayCode;
	}
	
	@Override
	public void setTimes(double times) {
		this.times = times;
	}
	
	@Override
	public void setTmdTotalLeaveId(long tmdTotalLeaveId) {
		this.tmdTotalLeaveId = tmdTotalLeaveId;
	}
	
	@Override
	public int getHours() {
		return hours;
	}
	
	@Override
	public void setHours(int hours) {
		this.hours = hours;
	}
	
}
