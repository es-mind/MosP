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

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.SubordinateFiscalListDtoInterface;

/**
 * 部下年度一覧DTO。
 */
public class SubordinateFiscalListDto extends BaseDto implements SubordinateFiscalListDtoInterface {
	
	private static final long	serialVersionUID	= 1196620813400130781L;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 表示年度。<br>
	 */
	private int					fiscalYear;
	
	/**
	 * 対象年。<br>
	 */
	private int					targetYear;
	
	/**
	 * 対象月。<br>
	 */
	private int					targetMonth;
	
	/**
	 * 社員コード。
	 */
	private String				employeeCode;
	/**
	 * 社員氏名(姓)。
	 */
	private String				lastName;
	/**
	 * 社員氏名(名)。
	 */
	private String				firstName;
	/**
	 * 所属コード。
	 */
	private String				sectionCode;
	
	/**
	 * 残業時間。
	 */
	private int					overTime;
	
	/**
	 * 普休始(日)。
	 */
	private double				paidHolidayDays;
	
	/**
	 * 普休始(時間)。
	 */
	private int					paidHolidayTime;
	
	/**
	 * 普休残(日)。
	 */
	private double				paidHolidayRestDays;
	
	/**
	 * 普休残(時間)。
	 */
	private int					paidHolidayRestTime;
	
	/**
	 * 保休始。
	 */
	private double				stockHolidayDays;
	
	/**
	 * 保休残。
	 */
	private double				stockHolidayRestDays;
	
	/**
	 * 季休始。
	 */
	private double				seasonHolidayDays;
	
	/**
	 * 季休残日数。
	 */
	private double				seasonHolidayRestDays;
	
	/**
	 * 季休残時間数。
	 */
	private int					seasonHolidayRestHours;
	
	/**
	 * 季休残分数。<br>
	 */
	private int					seasonHolidayRestMinutes;
	
	
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
	public String getFirstName() {
		return firstName;
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
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public int getFiscalYear() {
		return fiscalYear;
	}
	
	@Override
	public int getTargetYear() {
		return targetYear;
	}
	
	@Override
	public int getTargetMonth() {
		return targetMonth;
	}
	
	@Override
	public int getOverTime() {
		return overTime;
	}
	
	@Override
	public double getPaidHolidayDays() {
		return paidHolidayDays;
	}
	
	@Override
	public double getPaidHolidayRestDays() {
		return paidHolidayRestDays;
	}
	
	@Override
	public double getStockHolidayDays() {
		return stockHolidayDays;
	}
	
	@Override
	public double getStockHolidayRestDays() {
		return stockHolidayRestDays;
	}
	
	@Override
	public double getSeasonHolidayDays() {
		return seasonHolidayDays;
	}
	
	@Override
	public double getSeasonHolidayRestDays() {
		return seasonHolidayRestDays;
	}
	
	@Override
	public int getSeasonHolidayRestHours() {
		return seasonHolidayRestHours;
	}
	
	@Override
	public void setFiscalYear(int fiscalYear) {
		this.fiscalYear = fiscalYear;
	}
	
	@Override
	public void setTargetYear(int targetYear) {
		this.targetYear = targetYear;
	}
	
	@Override
	public void setTargetMonth(int targetMonth) {
		this.targetMonth = targetMonth;
	}
	
	@Override
	public void setOverTime(int overTime) {
		this.overTime = overTime;
	}
	
	@Override
	public void setPaidHolidayDays(double paidHolidayDays) {
		this.paidHolidayDays = paidHolidayDays;
	}
	
	@Override
	public void setPaidHolidayRestDays(double paidHolidayRestDays) {
		this.paidHolidayRestDays = paidHolidayRestDays;
	}
	
	@Override
	public void setStockHolidayDays(double stockHolidayDays) {
		this.stockHolidayDays = stockHolidayDays;
	}
	
	@Override
	public void setStockHolidayRestDays(double stockHolidayRestDays) {
		this.stockHolidayRestDays = stockHolidayRestDays;
	}
	
	@Override
	public void setSeasonHolidayDays(double seasonHolidayDays) {
		this.seasonHolidayDays = seasonHolidayDays;
	}
	
	@Override
	public void setSeasonHolidayRestDays(double seasonHolidayRestDays) {
		this.seasonHolidayRestDays = seasonHolidayRestDays;
	}
	
	@Override
	public int getPaidHolidayTime() {
		return paidHolidayTime;
	}
	
	@Override
	public int getPaidHolidayRestTime() {
		return paidHolidayRestTime;
	}
	
	@Override
	public void setPaidHolidayTime(int paidHolidayTime) {
		this.paidHolidayTime = paidHolidayTime;
	}
	
	@Override
	public void setPaidHolidayRestTime(int paidHolidayRestTime) {
		this.paidHolidayRestTime = paidHolidayRestTime;
	}
	
	@Override
	public void setSeasonHolidayRestHours(int seasonHolidayRestHours) {
		this.seasonHolidayRestHours = seasonHolidayRestHours;
	}
	
	@Override
	public int getSeasonHolidayRestMinutes() {
		return seasonHolidayRestMinutes;
	}
	
	@Override
	public void setSeasonHolidayRestMinutes(int seasonHolidayRestMinutes) {
		this.seasonHolidayRestMinutes = seasonHolidayRestMinutes;
	}
	
}
