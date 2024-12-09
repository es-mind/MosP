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
import jp.mosp.time.dto.settings.PaidHolidayHistoryListDtoInterface;

/**
 * 有給休暇手動付与一覧DTO。
 */
public class PaidHolidayHistoryListDto extends BaseDto implements PaidHolidayHistoryListDtoInterface {
	
	private static final long	serialVersionUID	= 2207143178411034586L;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 個人ID。
	 */
	private String				personalId;
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
	 * 前年日数。
	 */
	private double				formerDate;
	/**
	 * 前年時間。
	 */
	private int					formerTime;
	/**
	 * 今年日数。
	 */
	private double				date;
	/**
	 * 今年時間。
	 */
	private int					time;
	/**
	 * ストック日数。
	 */
	private double				stockDate;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	/**
	 * レコード識別ID。
	 */
	private long				tmdPaidHolidayHistoryListId;
	
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
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
	public double getFormerDate() {
		return formerDate;
	}
	
	@Override
	public void setFormerDate(double formerDate) {
		this.formerDate = formerDate;
	}
	
	@Override
	public int getFormerTime() {
		return formerTime;
	}
	
	@Override
	public void setFormerTime(int formerTime) {
		this.formerTime = formerTime;
	}
	
	@Override
	public double getDate() {
		return date;
	}
	
	@Override
	public void setDate(double date) {
		this.date = date;
	}
	
	@Override
	public int getTime() {
		return time;
	}
	
	@Override
	public void setTime(int time) {
		this.time = time;
	}
	
	@Override
	public double getStockDate() {
		return stockDate;
	}
	
	@Override
	public void setStockDate(double stockDate) {
		this.stockDate = stockDate;
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
	public long getTmdPaidHolidayHistoryListId() {
		return tmdPaidHolidayHistoryListId;
	}
	
	@Override
	public void setTmdPaidHolidayHistoryListId(long tmdPaidHolidayHistoryListId) {
		this.tmdPaidHolidayHistoryListId = tmdPaidHolidayHistoryListId;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
}
