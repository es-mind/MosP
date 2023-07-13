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

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.TotalOtherVacationDtoInterface;

/**
 * その他休暇集計データDTO
 */
public class TmdTotalOtherVacationDto extends BaseDto implements TotalOtherVacationDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -5925684300299226377L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdTotalOtherVacationId;
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
	 * その他休暇日数。
	 */
	private double				times;
	/**
	 * その他休暇時間数。
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
	public long getTmdTotalOtherVacationId() {
		return tmdTotalOtherVacationId;
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
	public void setTmdTotalOtherVacationId(long tmdTotalOtherVacationId) {
		this.tmdTotalOtherVacationId = tmdTotalOtherVacationId;
	}
	
	/**
	 * @return hours
	 */
	@Override
	public int getHours() {
		return hours;
	}
	
	/**
	 * @param hours セットする hours
	 */
	@Override
	public void setHours(int hours) {
		this.hours = hours;
	}
	
}
