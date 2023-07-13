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
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;

/**
 * 有給休暇取得状況確認情報。<br>
 */
public class PaidHolidayUsageDto extends BaseDto
		implements PersonalIdDtoInterface, EmployeeCodeDtoInterface, EmployeeNameDtoInterface, SectionCodeDtoInterface {
	
	private static final long	serialVersionUID	= 4811516960488397076L;
	
	/**
	 * 個人ID。<br>
	 */
	private String				personalId;
	
	/**
	 * 社員コード。<br>
	 */
	private String				employeeCode;
	
	/**
	 * 姓。<br>
	 */
	private String				lastName;
	
	/**
	 * 名。<br>
	 */
	private String				firstName;
	
	/**
	 * 所属コード。<br>
	 */
	private String				sectionCode;
	
	/**
	 * 所属名称。<br>
	 */
	private String				sectionName;
	
	/**
	 * 対象期間(From)。<br>
	 */
	private Date				usageFromDate;
	
	/**
	 * 対象期間(To)。<br>
	 */
	private Date				usageToDate;
	
	/**
	 *  取得日。<br>
	 */
	private Date				acquisitionDate;
	
	/**
	 * 付与日数。<br>
	 */
	private double				givingDay;
	
	/**
	 * 申請日数。<br>
	 */
	private float				useDay;
	
	/**
	 * 申請日(キー：申請日、値：休暇範囲)。<br>
	 * 前半休+後半休は、全休として保持する。<br>
	 * 申請日昇順。<br>
	 */
	private Map<Date, Integer>	useDates;
	
	/**
	 * 未消化日数(合算)。<br>
	 */
	private float				shortDay;
	
	/**
	 * 備考。<br>
	 */
	private String				remark;
	
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	/**
	 * @return 所属名称
	 */
	public String getSectionName() {
		return sectionName;
	}
	
	/**
	 * @param sectionName 所属名称
	 */
	public void setSectionName(String sectionName) {
		this.sectionName = sectionName;
	}
	
	/**
	 * @return 対象期間(From)
	 */
	public Date getUsageFromDate() {
		return getDateClone(usageFromDate);
	}
	
	/**
	 * @param usageFromDate 対象期間(From)
	 */
	public void setUsageFromDate(Date usageFromDate) {
		this.usageFromDate = getDateClone(usageFromDate);
	}
	
	/**
	 * @return 対象期間(To)
	 */
	public Date getUsageToDate() {
		return getDateClone(usageToDate);
	}
	
	/**
	 * @param usageToDate 対象期間(To)
	 */
	public void setUsageToDate(Date usageToDate) {
		this.usageToDate = getDateClone(usageToDate);
	}
	
	/**
	 * @return 取得日
	 */
	public Date getAcquisitionDate() {
		return getDateClone(acquisitionDate);
	}
	
	/**
	 * @param acquisitionDate 取得日
	 */
	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = getDateClone(acquisitionDate);
	}
	
	/**
	 * @return 付与日数
	 */
	public double getGivingDay() {
		return givingDay;
	}
	
	/**
	 * @param givingDay 付与日数
	 */
	public void setGivingDay(double givingDay) {
		this.givingDay = givingDay;
	}
	
	/**
	 * @return 申請日数
	 */
	public float getUseDay() {
		return useDay;
	}
	
	/**
	 * @param useDay 申請日数
	 */
	public void setUseDay(float useDay) {
		this.useDay = useDay;
	}
	
	/**
	 * @return 申請日(キー：申請日、値：休暇範囲)
	 */
	public Map<Date, Integer> getUseDates() {
		return useDates;
	}
	
	/**
	 * @param useDates 申請日(キー：申請日、値：休暇範囲)
	 */
	public void setUseDates(Map<Date, Integer> useDates) {
		this.useDates = useDates;
	}
	
	/**
	 * @return 未消化日数(合算)
	 */
	public float getShortDay() {
		return shortDay;
	}
	
	/**
	 * @param shortDay 未消化日数(合算)
	 */
	public void setShortDay(float shortDay) {
		this.shortDay = shortDay;
	}
	
	/**
	 * @return 備考
	 */
	public String getRemark() {
		return remark;
	}
	
	/**
	 * @param remark 備考
	 */
	public void setRemark(String remark) {
		this.remark = remark;
	}
	
}
