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

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.TotalTimeDtoInterface;

/**
 * 勤怠集計管理DTO
 */
public class TmtTotalTimeDto extends BaseDto implements TotalTimeDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= 5268375189747247945L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmtTotalTimeId;
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
	public Date getCalculationDate() {
		return getDateClone(calculationDate);
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
	public String getCutoffCode() {
		return cutoffCode;
	}
	
	@Override
	public int getCutoffState() {
		return cutoffState;
	}
	
	@Override
	public long getTmtTotalTimeId() {
		return tmtTotalTimeId;
	}
	
	@Override
	public void setCalculationDate(Date calculationDate) {
		this.calculationDate = getDateClone(calculationDate);
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
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
	@Override
	public void setCutoffState(int cutoffState) {
		this.cutoffState = cutoffState;
	}
	
	@Override
	public void setTmtTotalTimeId(long tmtTotalTimeId) {
		this.tmtTotalTimeId = tmtTotalTimeId;
	}
	
}
