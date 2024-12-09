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

import jp.mosp.framework.utils.CapsuleUtility;

/**
 * 休暇残情報。<br>
 * 休暇情報及び休暇申請情報等を元に作成される。<br>
 * BaseDtoを継承していないため、セッションに保持しないようにすること。<br>
 */
public class HolidayRemainDto {
	
	/**
	 * 休暇コード。<br>
	 */
	private String	holidayCode;
	
	/**
	 * 休暇取得日。<br>
	 */
	private Date	acquisitionDate;
	
	/**
	 * 休暇区分。<br>
	 */
	private int		holidayType;
	
	/**
	 * 取得期限。<br>
	 */
	private Date	holidayLimitDate;
	
	/**
	 * 付与日数。<br>
	 */
	private double	givenDays;
	
	/**
	 * 付与時間数。<br>
	 */
	private int		givenHours;
	
	/**
	 * 残日数。<br>
	 */
	private double	remainDays;
	
	/**
	 * 残時間数。<br>
	 */
	private int		remainHours;
	
	/**
	 * 残分数。<br>
	 */
	private int		remainMinutes;
	
	/**
	 * 休暇名称。<br>
	 */
	private String	holidayName;
	
	/**
	 * 休暇略称。<br>
	 */
	private String	holidayAbbr;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	public HolidayRemainDto() {
		// 処理無し
	}
	
	/**
	 * @return holidayCode
	 */
	public String getHolidayCode() {
		return holidayCode;
	}
	
	/**
	 * @param holidayCode セットする holidayCode
	 */
	public void setHolidayCode(String holidayCode) {
		this.holidayCode = holidayCode;
	}
	
	/**
	 * @return acquisitionDate
	 */
	public Date getAcquisitionDate() {
		return CapsuleUtility.getDateClone(acquisitionDate);
	}
	
	/**
	 * @param acquisitionDate セットする acquisitionDate
	 */
	public void setAcquisitionDate(Date acquisitionDate) {
		this.acquisitionDate = CapsuleUtility.getDateClone(acquisitionDate);
	}
	
	/**
	 * @return holidayType
	 */
	public int getHolidayType() {
		return holidayType;
	}
	
	/**
	 * @param holidayType セットする holidayType
	 */
	public void setHolidayType(int holidayType) {
		this.holidayType = holidayType;
	}
	
	/**
	 * @return holidayLimitDate
	 */
	public Date getHolidayLimitDate() {
		return CapsuleUtility.getDateClone(holidayLimitDate);
	}
	
	/**
	 * @param holidayLimitDate セットする holidayLimitDate
	 */
	public void setHolidayLimitDate(Date holidayLimitDate) {
		this.holidayLimitDate = CapsuleUtility.getDateClone(holidayLimitDate);
	}
	
	/**
	 * @return givenDays
	 */
	public double getGivenDays() {
		return givenDays;
	}
	
	/**
	 * @param givenDays セットする givenDays
	 */
	public void setGivenDays(double givenDays) {
		this.givenDays = givenDays;
	}
	
	/**
	 * @return givenHours
	 */
	public int getGivenHours() {
		return givenHours;
	}
	
	/**
	 * @param givenHours セットする givenHours
	 */
	public void setGivenHours(int givenHours) {
		this.givenHours = givenHours;
	}
	
	/**
	 * @return remainDays
	 */
	public double getRemainDays() {
		return remainDays;
	}
	
	/**
	 * @param remainDays セットする remainDays
	 */
	public void setRemainDays(double remainDays) {
		this.remainDays = remainDays;
	}
	
	/**
	 * @return remainHours
	 */
	public int getRemainHours() {
		return remainHours;
	}
	
	/**
	 * @param remainHours セットする remainHours
	 */
	public void setRemainHours(int remainHours) {
		this.remainHours = remainHours;
	}
	
	/**
	 * @return remainMinutes
	 */
	public int getRemainMinutes() {
		return remainMinutes;
	}
	
	/**
	 * @param remainMinutes セットする remainMinutes
	 */
	public void setRemainMinutes(int remainMinutes) {
		this.remainMinutes = remainMinutes;
	}
	
	/**
	 * @return holidayName
	 */
	public String getHolidayName() {
		return holidayName;
	}
	
	/**
	 * @param holidayName セットする holidayName
	 */
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	
	/**
	 * @return holidayAbbr
	 */
	public String getHolidayAbbr() {
		return holidayAbbr;
	}
	
	/**
	 * @param holidayAbbr セットする holidayAbbr
	 */
	public void setHolidayAbbr(String holidayAbbr) {
		this.holidayAbbr = holidayAbbr;
	}
	
}
