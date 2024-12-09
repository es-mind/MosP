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
import jp.mosp.time.dto.settings.PaidHolidayDataGrantListDtoInterface;

/**
 * 有給休暇データ一覧DTO
 */
public class PaidHolidayDataGrantListDto extends BaseDto implements PaidHolidayDataGrantListDtoInterface {
	
	private static final long	serialVersionUID	= -6123107592828007532L;
	
	private Date				activateDate;
	private int					inactivateFlag;
	private String				personalId;
	private String				employeeCode;
	private String				lastName;
	private String				firstName;
	private Date				grantDate;
	private Date				startDate;
	private Date				endDate;
	private Integer				workDays;
	private Integer				totalWorkDays;
	private Double				attendanceRate;
	private String				accomplish;
	private String				grant;
	private Double				grantDays;
	private boolean				warning;
	private String				error;
	
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Deprecated
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Deprecated
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
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
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
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
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public Date getGrantDate() {
		return getDateClone(grantDate);
	}
	
	@Override
	public void setGrantDate(Date grantDate) {
		this.grantDate = getDateClone(grantDate);
	}
	
	@Override
	public Date getFirstDate() {
		return getDateClone(startDate);
	}
	
	@Override
	public void setFirstDate(Date startDate) {
		this.startDate = getDateClone(startDate);
	}
	
	@Override
	public Date getLastDate() {
		return getDateClone(endDate);
	}
	
	@Override
	public void setLastDate(Date endDate) {
		this.endDate = getDateClone(endDate);
	}
	
	@Override
	public Integer getWorkDays() {
		return workDays;
	}
	
	@Override
	public void setWorkDays(Integer workDays) {
		this.workDays = workDays;
	}
	
	@Override
	public Integer getTotalWorkDays() {
		return totalWorkDays;
	}
	
	@Override
	public void setTotalWorkDays(Integer totalWorkDays) {
		this.totalWorkDays = totalWorkDays;
	}
	
	@Override
	public Double getAttendanceRate() {
		return attendanceRate;
	}
	
	@Override
	public void setAttendanceRate(Double attendanceRate) {
		this.attendanceRate = attendanceRate;
	}
	
	@Override
	public String getAccomplish() {
		return accomplish;
	}
	
	@Override
	public void setAccomplish(String accomplish) {
		this.accomplish = accomplish;
	}
	
	@Override
	public String getGrant() {
		return grant;
	}
	
	@Override
	public void setGrant(String grant) {
		this.grant = grant;
	}
	
	@Override
	public Double getGrantDays() {
		return grantDays;
	}
	
	@Override
	public void setGrantDays(Double grantDays) {
		this.grantDays = grantDays;
	}
	
	@Override
	public boolean isWarning() {
		return warning;
	}
	
	@Override
	public void setWarning(boolean warning) {
		this.warning = warning;
	}
	
	@Override
	public String getError() {
		return error;
	}
	
	@Override
	public void setError(String error) {
		this.error = error;
	}
	
}
