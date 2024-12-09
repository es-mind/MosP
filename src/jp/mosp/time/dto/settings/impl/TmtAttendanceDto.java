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
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;

/**
 * 勤怠トランザクションDTO
 */
public class TmtAttendanceDto extends BaseDto implements AttendanceTransactionDtoInterface {
	
	private static final long	serialVersionUID	= 5565898449794041543L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmtAttendanceId;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 勤務日。
	 */
	private Date				workDate;
	
	/**
	 * 出勤区分。
	 */
	private String				attendanceType;
	
	/**
	 * 出勤率算定分子。
	 */
	private int					numerator;
	
	/**
	 * 出勤率算定分母。
	 */
	private int					denominator;
	
	
	@Override
	public long getTmtAttendanceId() {
		return tmtAttendanceId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public Date getWorkDate() {
		return getDateClone(workDate);
	}
	
	@Override
	public String getAttendanceType() {
		return attendanceType;
	}
	
	@Override
	public int getNumerator() {
		return numerator;
	}
	
	@Override
	public int getDenominator() {
		return denominator;
	}
	
	@Override
	public void setTmtAttendanceId(long tmtAttendanceId) {
		this.tmtAttendanceId = tmtAttendanceId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setWorkDate(Date workDate) {
		this.workDate = getDateClone(workDate);
	}
	
	@Override
	public void setAttendanceType(String attendanceType) {
		this.attendanceType = attendanceType;
	}
	
	@Override
	public void setNumerator(int numerator) {
		this.numerator = numerator;
	}
	
	@Override
	public void setDenominator(int denominator) {
		this.denominator = denominator;
	}
	
}
