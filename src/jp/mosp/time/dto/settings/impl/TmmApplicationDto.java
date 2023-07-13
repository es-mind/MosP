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
import jp.mosp.time.dto.settings.ApplicationDtoInterface;

/**
 * 設定適用マスタDTO。
 */
public class TmmApplicationDto extends BaseDto implements ApplicationDtoInterface {
	
	private static final long	serialVersionUID	= 3626672182915980445L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmApplicationId;
	/**
	 * 設定適用コード。
	 */
	private String				applicationCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 設定適用区分。
	 */
	private int					applicationType;
	/**
	 * 設定適用名称。
	 */
	private String				applicationName;
	/**
	 * 設定適用略称。
	 */
	private String				applicationAbbr;
	/**
	 * 勤怠設定コード。
	 */
	private String				workSettingCode;
	/**
	 * カレンダコード。
	 */
	private String				scheduleCode;
	/**
	 * 有休コード。
	 */
	private String				paidHolidayCode;
	/**
	 * 勤務地コード。
	 */
	private String				workPlaceCode;
	/**
	 * 雇用契約コード。
	 */
	private String				employmentContractCode;
	/**
	 * 所属コード。
	 */
	private String				sectionCode;
	/**
	 * 職位コード。
	 */
	private String				positionCode;
	/**
	 * 個人ID。
	 */
	private String				personalIds;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public String getApplicationCode() {
		return applicationCode;
	}
	
	@Override
	public int getApplicationType() {
		return applicationType;
	}
	
	@Override
	public String getEmploymentContractCode() {
		return employmentContractCode;
	}
	
	@Override
	public String getPaidHolidayCode() {
		return paidHolidayCode;
	}
	
	@Override
	public String getPositionCode() {
		return positionCode;
	}
	
	@Override
	public String getScheduleCode() {
		return scheduleCode;
	}
	
	@Override
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public long getTmmApplicationId() {
		return tmmApplicationId;
	}
	
	@Override
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}
	
	@Override
	public String getWorkSettingCode() {
		return workSettingCode;
	}
	
	@Override
	public String getPersonalIds() {
		return personalIds;
	}
	
	@Override
	public void setApplicationCode(String applicationCode) {
		this.applicationCode = applicationCode;
	}
	
	@Override
	public void setEmploymentContractCode(String employmentContractCode) {
		this.employmentContractCode = employmentContractCode;
	}
	
	@Override
	public void setPaidHolidayCode(String paidHolidayCode) {
		this.paidHolidayCode = paidHolidayCode;
	}
	
	@Override
	public void setApplicationType(int applicationType) {
		this.applicationType = applicationType;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public void setScheduleCode(String scheduleCode) {
		this.scheduleCode = scheduleCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public void setTmmApplicationId(long tmmApplicationId) {
		this.tmmApplicationId = tmmApplicationId;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public void setWorkSettingCode(String workSettingCode) {
		this.workSettingCode = workSettingCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public String getApplicationName() {
		return applicationName;
	}
	
	@Override
	public void setApplicationName(String applicationName) {
		this.applicationName = applicationName;
	}
	
	@Override
	public String getApplicationAbbr() {
		return applicationAbbr;
	}
	
	@Override
	public void setApplicationAbbr(String applicationAbbr) {
		this.applicationAbbr = applicationAbbr;
	}
	
	@Override
	public void setPersonalIds(String personalIds) {
		this.personalIds = personalIds;
	}
	
}
