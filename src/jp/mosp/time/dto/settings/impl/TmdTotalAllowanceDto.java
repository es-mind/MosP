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
import jp.mosp.time.dto.settings.TotalAllowanceDtoInterface;

/**
 * 手当集計データDTO
 */
public class TmdTotalAllowanceDto extends BaseDto implements TotalAllowanceDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -4187504974638749877L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmdTotalAllowanceId;
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
	 * 手当コード。
	 */
	private String				allowanceCode;
	/**
	 * 手当回数。
	 */
	private int					times;
	
	
	@Override
	public String getAllowanceCode() {
		return allowanceCode;
	}
	
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
	public int getTimes() {
		return times;
	}
	
	@Override
	public long getTmdTotalAllowanceId() {
		return tmdTotalAllowanceId;
	}
	
	@Override
	public void setAllowanceCode(String allowanceCode) {
		this.allowanceCode = allowanceCode;
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
	public void setTimes(int times) {
		this.times = times;
	}
	
	@Override
	public void setTmdTotalAllowanceId(long tmdTotalAllowanceId) {
		this.tmdTotalAllowanceId = tmdTotalAllowanceId;
	}
	
}
