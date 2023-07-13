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
import jp.mosp.time.dto.settings.ApplicationReferenceDtoInterface;

/**
 * 設定適用参照検索マスタDTO。
 *
 */
public class ApplicationReferenceDto extends BaseDto implements ApplicationReferenceDtoInterface {
	
	private static final long	serialVersionUID	= -3179466813337292518L;
	
	/**
	 * 設定適用の有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 個人ID
	 */
	private String				personalId;
	
	/**
	 * 社員コード
	 */
	private String				employeeCode;
	
	/**
	 * 社員名
	 */
	private String				employeeName;
	
	/**
	 * 設定適用コード
	 */
	private String				applicationCode;
	
	/**
	 * 設定適用名称
	 */
	private String				applicationName;
	
	/**
	 * 設定適用略称
	 */
	private String				applicationAbbr;
	
	/**
	 * 設定適用設定(略称+コード) 
	 */
	private String				application;
	
	/**
	 * 勤怠設定コード
	 */
	private String				timeSettingCode;
	
	/**
	 * 勤怠設定名称
	 */
	private String				timeSettingName;
	
	/**
	 * 勤怠設定(略称+コード) 
	 */
	private String				timeSetting;
	
	/**
	 * 勤怠設定略称
	 */
	private String				timeSettingAbbr;
	
	/**
	 * 締日(略称+コード) 
	 */
	private String				cutoff;
	
	/**
	 * 締日略称 
	 */
	private String				cutoffAbbr;
	
	/**
	 * 締日名称 
	 */
	private String				cutoffName;
	
	/**
	 * 締日コード 
	 */
	private String				cutoffCode;
	
	/**
	 * カレンダ(略称+コード) 
	 */
	private String				schedule;
	
	/**
	 * カレンダ名称 
	 */
	private String				scheduleName;
	
	/**
	 * カレンダコード 
	 */
	private String				scheduleCode;
	
	/**
	 * カレンダ略称
	 */
	private String				scheduleAbbr;
	
	/**
	 * 有休設定(略称+コード) 
	 */
	private String				paidHoliday;
	
	/**
	 * 有休設定名称 
	 */
	private String				paidHolidayName;
	
	/**
	 * 有休設定コード 
	 */
	private String				paidHolidayCode;
	
	/**
	 * 有休設定略称 
	 */
	private String				paidHolidayAbbr;
	
	private String				firstName;
	private String				lastName;
	private String				sectionCode;
	private String				workPlaceCode;
	private String				positionCode;
	private String				employmentContractCode;
	
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public String getApplication() {
		return application;
	}
	
	@Override
	public String getApplicationAbbr() {
		return applicationAbbr;
	}
	
	@Override
	public String getApplicationCode() {
		return applicationCode;
	}
	
	@Override
	public String getApplicationName() {
		return applicationName;
	}
	
	@Override
	public String getCutoff() {
		return cutoff;
	}
	
	@Override
	public String getCutoffAbbr() {
		return cutoffAbbr;
	}
	
	@Override
	public String getCutoffCode() {
		return cutoffCode;
	}
	
	@Override
	public String getCutoffName() {
		return cutoffName;
	}
	
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public String getEmployeeName() {
		return employeeName;
	}
	
	@Override
	public String getPaidHoliday() {
		return paidHoliday;
	}
	
	@Override
	public String getPaidHolidayAbbr() {
		return paidHolidayAbbr;
	}
	
	@Override
	public String getPaidHolidayCode() {
		return paidHolidayCode;
	}
	
	@Override
	public String getPaidHolidayName() {
		return paidHolidayName;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public String getSchedule() {
		return schedule;
	}
	
	@Override
	public String getScheduleAbbr() {
		return scheduleAbbr;
	}
	
	@Override
	public String getScheduleCode() {
		return scheduleCode;
	}
	
	@Override
	public String getScheduleName() {
		return scheduleName;
	}
	
	@Override
	public String getTimeSetting() {
		return timeSetting;
	}
	
	@Override
	public String getTimeSettingCode() {
		return timeSettingCode;
	}
	
	@Override
	public String getTimeSettingName() {
		return timeSettingName;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setApplication(String application) {
		this.application = application;
	}
	
	@Override
	public void setApplicationAbbr(String applicationAbbr) {
		this.applicationAbbr = applicationAbbr;
	}
	
	@Override
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
	@Override
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	@Override
	public void setCutoff(String cutoff) {
		this.cutoff = cutoff;
	}
	
	@Override
	public void setCutoffAbbr(String cutoffAbbr) {
		this.cutoffAbbr = cutoffAbbr;
	}
	
	@Override
	public void setCutoffCode(String cutoffCode) {
		this.cutoffCode = cutoffCode;
	}
	
	@Override
	public void setCutoffName(String cutoffName) {
		this.cutoffName = cutoffName;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
	}
	
	@Override
	public void setPaidHoliday(String paidHoliday) {
		this.paidHoliday = paidHoliday;
	}
	
	@Override
	public void setPaidHolidayAbbr(String paidHolidayAbbr) {
		this.paidHolidayAbbr = paidHolidayAbbr;
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
	@Override
	public void setPaidHolidayName(String paidHolidayName) {
		this.paidHolidayName = paidHolidayName;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setSchedule(String schedule) {
		this.schedule = schedule;
	}
	
	@Override
	public void setScheduleAbbr(String scheduleAbbr) {
		this.scheduleAbbr = scheduleAbbr;
	}
	
	@Override
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	
	@Override
	public void setScheduleName(String scheduleName) {
		this.scheduleName = scheduleName;
	}
	
	@Override
	public void setTimeSetting(String timeSetting) {
		this.timeSetting = timeSetting;
	}
	
	@Override
	public void setTimeSettingAbbr(String timeSettingAbbr) {
		this.timeSettingAbbr = timeSettingAbbr;
	}
	
	@Override
	public void setTimeSettingCode(String timeSettingCode) {
		this.timeSettingCode = timeSettingCode;
	}
	
	@Override
	public void setTimeSettingName(String timeSettingName) {
		this.timeSettingName = timeSettingName;
	}
	
	@Override
	public String getTimeSettingAbbr() {
		return timeSettingAbbr;
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
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public String getPositionCode() {
		return positionCode;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public String getEmploymentContractCode() {
		return employmentContractCode;
	}
	
	@Override
	public void setEmploymentContractCode(String employmentContractCode) {
		this.employmentContractCode = employmentContractCode;
	}
	
	@Override
	public int getInactivateFlag() {
		// 処理なし
		return 0;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		// 処理なし
		
	}
	
}
