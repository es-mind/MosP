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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;

/**
 * 社員勤怠集計管理DTO。
 */
public class TmtTotalTimeEmployeeDto extends BaseDto implements TotalTimeEmployeeDtoInterface {
	
	private static final long	serialVersionUID	= 3945092482102870087L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmtTotalTimeEmployeeId;
	/**
	 * 社員コード。
	 */
	private String				personalId;
	/**
	 * 集計年。
	 */
	private int					calculationYear;
	/**
	 * 集計月。
	 */
	private int					calculationMonth;
	/**
	 * 締日コード。
	 */
	private String				cutoffCode;
	/**
	 * 集計日。
	 */
	private Date				calculationDate;
	/**
	 * 締状態。
	 */
	private int					cutoffState;
	
	
	@Override
	public long getTmtTotalTimeEmployeeId() {
		return tmtTotalTimeEmployeeId;
	}
	
	@Override
	public void setTmtTotalTimeEmployeeId(long tmtTotalTimeEmployeeId) {
		this.tmtTotalTimeEmployeeId = tmtTotalTimeEmployeeId;
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
	public String getCutoffCode() {
		return cutoffCode;
	}
	
	@Override
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
	@Override
	public Date getCalculationDate() {
		return getDateClone(calculationDate);
	}
	
	@Override
	public void setCalculationDate(Date calculationDate) {
		this.calculationDate = getDateClone(calculationDate);
	}
	
	@Override
	public int getCutoffState() {
		return cutoffState;
	}
	
	@Override
	public void setCutoffState(int cutoffState) {
		this.cutoffState = cutoffState;
	}
	
}
