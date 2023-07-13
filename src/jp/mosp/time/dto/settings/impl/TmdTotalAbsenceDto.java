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

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.TotalAbsenceDtoInterface;

/**
 * 欠勤集計データDTO。
 */
public class TmdTotalAbsenceDto extends BaseDto implements TotalAbsenceDtoInterface {
	
	private static final long	serialVersionUID	= -1357124245176431255L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdTotalAbsenceId;
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
	 * 欠勤コード。
	 */
	private String				absenceCode;
	/**
	 * 欠勤日数。
	 */
	private double				times;
	/**
	 * 欠勤時間数。
	 */
	private int					hours;
	
	
	@Override
	public long getTmdTotalAbsenceId() {
		return tmdTotalAbsenceId;
	}
	
	@Override
	public void setTmdTotalAbsenceId(long tmdTotalAbsenceId) {
		this.tmdTotalAbsenceId = tmdTotalAbsenceId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public int getCalculationYear() {
		return calculationYear;
	}
	
	@Override
	public void setCalculationYear(int calculationYear) {
		this.calculationYear = calculationYear;
	}
	
	@Override
	public int getCalculationMonth() {
		return calculationMonth;
	}
	
	@Override
	public void setCalculationMonth(int calculationMonth) {
		this.calculationMonth = calculationMonth;
	}
	
	@Override
	public String getAbsenceCode() {
		return absenceCode;
	}
	
	@Override
	public void setAbsenceCode(String absenceCode) {
		this.absenceCode = absenceCode;
	}
	
	@Override
	public double getTimes() {
		return times;
	}
	
	@Override
	public void setTimes(double times) {
		this.times = times;
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
