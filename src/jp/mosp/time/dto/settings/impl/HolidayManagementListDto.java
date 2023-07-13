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
import jp.mosp.time.dto.settings.HolidayManagementListDtoInterface;

/**
 * 特別休暇確認一覧DTO。
 */
public class HolidayManagementListDto extends BaseDto implements HolidayManagementListDtoInterface {
	
	private static final long	serialVersionUID	= -3452150776748301374L;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 社員コード。
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
	 * 残日数。
	 */
	private double				holidayRemainder;
	
	/**
	 * 残時間。<br>
	 */
	private int					holidayRemaindHours;
	
	/**
	 * 残分数。<br>
	 */
	private int					holidayRemaindMinutes;
	
	/**
	 * 取得期限。
	 */
	private Date				holidayLimit;
	
	
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
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public void setHolidayCode(String holidayCode) {
		this.holidayCode = holidayCode;
	}
	
	@Override
	public double getHolidayRemainder() {
		return holidayRemainder;
	}
	
	@Override
	public void setHolidayRemainder(double holidayRemainder) {
		this.holidayRemainder = holidayRemainder;
	}
	
	@Override
	public int getHolidayRemaindHours() {
		return holidayRemaindHours;
	}
	
	@Override
	public void setHolidayRemaindHours(int holidayRemaindHours) {
		this.holidayRemaindHours = holidayRemaindHours;
	}
	
	@Override
	public int getHolidayRemaindMinutes() {
		return holidayRemaindMinutes;
	}
	
	@Override
	public void setHolidayRemaindMinutes(int holidayRemaindMinutes) {
		this.holidayRemaindMinutes = holidayRemaindMinutes;
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
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public int getInactivateFlag() {
		// 処理なし
		return 0;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		// 処理無し
	}
	
}
