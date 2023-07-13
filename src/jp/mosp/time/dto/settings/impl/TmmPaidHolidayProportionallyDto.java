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

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.PaidHolidayProportionallyDtoInterface;

/**
 * 有給休暇比例付与情報。<br>
 */
public class TmmPaidHolidayProportionallyDto extends BaseDto implements PaidHolidayProportionallyDtoInterface {
	
	private static final long	serialVersionUID	= 6371812231643052610L;
	
	/**
	 * レコード識別ID。<br>
	 */
	private long				tmmPaidHolidayProportionallyId;
	
	/**
	 * 有休コード。<br>
	 */
	private String				paidHolidayCode;
	
	/**
	 * 有効日。<br>
	 */
	private Date				activateDate;
	
	/**
	 * 週所定労働日数。<br>
	 */
	private int					prescribedWeeklyWorkingDays;
	
	/**
	 * 雇入れの日から起算した継続勤務期間。<br>
	 */
	private int					continuousServiceTermsCountingFromTheEmploymentDay;
	
	/**
	 * 日数。<br>
	 */
	private int					days;
	
	/**
	 * 無効フラグ。<br>
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getTmmPaidHolidayProportionallyId() {
		return tmmPaidHolidayProportionallyId;
	}
	
	@Override
	public String getPaidHolidayCode() {
		return paidHolidayCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public int getPrescribedWeeklyWorkingDays() {
		return prescribedWeeklyWorkingDays;
	}
	
	@Override
	public int getContinuousServiceTermsCountingFromTheEmploymentDay() {
		return continuousServiceTermsCountingFromTheEmploymentDay;
	}
	
	@Override
	public int getDays() {
		return days;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setTmmPaidHolidayProportionallyId(long tmmPaidHolidayProportionallyId) {
		this.tmmPaidHolidayProportionallyId = tmmPaidHolidayProportionallyId;
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setPrescribedWeeklyWorkingDays(int prescribedWeeklyWorkingDays) {
		this.prescribedWeeklyWorkingDays = prescribedWeeklyWorkingDays;
	}
	
	@Override
	public void setContinuousServiceTermsCountingFromTheEmploymentDay(
			int continuousServiceTermsCountingFromTheEmploymentDay) {
		this.continuousServiceTermsCountingFromTheEmploymentDay = continuousServiceTermsCountingFromTheEmploymentDay;
	}
	
	@Override
	public void setDays(int days) {
		this.days = days;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
