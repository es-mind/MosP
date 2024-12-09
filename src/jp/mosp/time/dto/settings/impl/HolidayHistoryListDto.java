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
import jp.mosp.time.dto.settings.HolidayHistoryListDtoInterface;

/**
 * 休暇付与一覧DTO。
 */
public class HolidayHistoryListDto extends BaseDto implements HolidayHistoryListDtoInterface {
	
	private static final long	serialVersionUID	= -5178437174174482350L;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 社員コード
	 */
	private String				employeeCode;
	
	/**
	 * 社員氏名(姓)。
	 */
	private String				firstName;
	/**
	 * 社員氏名(名)。
	 */
	private String				lastName;
	/**
	 * 所属。
	 */
	private String				sectionCode;
	/**
	 * 休暇種別。
	 */
	private String				holidayCode;
	/**
	 * 付与日数。
	 */
	private double				holidayGiving;
	/**
	 * 取得期限。
	 */
	private Date				holidayLimit;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public String getHolidayCode() {
		return holidayCode;
	}
	
	@Override
	public void setHolidayCode(String holidayCode) {
		this.holidayCode = holidayCode;
	}
	
	@Override
	public double getHolidayGiving() {
		return holidayGiving;
	}
	
	@Override
	public void setHolidayGiving(double holidayGiving) {
		this.holidayGiving = holidayGiving;
	}
	
	@Override
	public Date getHolidayLimit() {
		return getDateClone(holidayLimit);
	}
	
	@Override
	public void setHolidayLimit(Date holidayLimit) {
		this.holidayLimit = getDateClone(holidayLimit);
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
}
