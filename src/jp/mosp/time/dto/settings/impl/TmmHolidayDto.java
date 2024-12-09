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
import jp.mosp.time.dto.settings.HolidayDtoInterface;

/**
 * 休暇種別管理DTO
 */
public class TmmHolidayDto extends BaseDto implements HolidayDtoInterface {
	
	private static final long	serialVersionUID	= -3540918101722446078L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmHolidayId;
	/**
	 * 休暇コード。
	 */
	private String				holidayCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 休暇区分。
	 */
	private int					holidayType;
	/**
	 * 休暇名称。
	 */
	private String				holidayName;
	/**
	 * 休暇略称。
	 */
	private String				holidayAbbr;
	/**
	 * 標準付与日数。
	 */
	private double				holidayGiving;
	/**
	 * 付与日数無制限。
	 */
	private int					noLimit;
	/**
	 * 取得期限(月)。
	 */
	private int					holidayLimitMonth;
	/**
	 * 取得期限(日)。
	 */
	private int					holidayLimitDay;
	/**
	 * 半休申請。
	 */
	private int					halfHolidayRequest;
	/**
	 * 連続取得。
	 */
	private int					continuousAcquisition;
	/**
	 * 時間単位休暇機能。
	 */
	private int					timelyHolidayFlag;
	/**
	 * 出勤率計算。
	 */
	private int					paidHolidayCalc;
	/**
	 * 有給/無給。
	 */
	private int					salary;
	/**
	 * 理由種別。
	 */
	private int					reasonType;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmHolidayDto() {
		// 初期化
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public int getContinuousAcquisition() {
		return continuousAcquisition;
	}
	
	@Override
	public int getTimelyHolidayFlag() {
		return timelyHolidayFlag;
	}
	
	@Override
	public String getHolidayAbbr() {
		return holidayAbbr;
	}
	
	@Override
	public String getHolidayCode() {
		return holidayCode;
	}
	
	@Override
	public double getHolidayGiving() {
		return holidayGiving;
	}
	
	@Override
	public int getHolidayLimitMonth() {
		return holidayLimitMonth;
	}
	
	@Override
	public int getHolidayLimitDay() {
		return holidayLimitDay;
	}
	
	@Override
	public int getHalfHolidayRequest() {
		return halfHolidayRequest;
	}
	
	@Override
	public String getHolidayName() {
		return holidayName;
	}
	
	@Override
	public int getHolidayType() {
		return holidayType;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public int getSalary() {
		return salary;
	}
	
	@Override
	public int getReasonType() {
		return reasonType;
	}
	
	@Override
	public long getTmmHolidayId() {
		return tmmHolidayId;
	}
	
	@Override
	public int getPaidHolidayCalc() {
		return paidHolidayCalc;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public void setContinuousAcquisition(int continuousAcquisition) {
		this.continuousAcquisition = continuousAcquisition;
	}
	
	@Override
	public void setTimelyHolidayFlag(int timelyHolidayFlag) {
		this.timelyHolidayFlag = timelyHolidayFlag;
	}
	
	@Override
	public void setHolidayAbbr(String holidayAbbr) {
		this.holidayAbbr = holidayAbbr;
	}
	
	@Override
	public void setHolidayCode(String holidayCode) {
		this.holidayCode = holidayCode;
	}
	
	@Override
	public void setHolidayGiving(double holidayGiving) {
		this.holidayGiving = holidayGiving;
	}
	
	@Override
	public void setHolidayLimitMonth(int holidayLimitMonth) {
		this.holidayLimitMonth = holidayLimitMonth;
	}
	
	@Override
	public void setHolidayLimitDay(int holidayLimitDay) {
		this.holidayLimitDay = holidayLimitDay;
	}
	
	@Override
	public void setHalfHolidayRequest(int halfHolidayRequest) {
		this.halfHolidayRequest = halfHolidayRequest;
	}
	
	@Override
	public void setHolidayName(String holidayName) {
		this.holidayName = holidayName;
	}
	
	@Override
	public void setHolidayType(int holidayType) {
		this.holidayType = holidayType;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	@Override
	public void setReasonType(int reasonType) {
		this.reasonType = reasonType;
	}
	
	@Override
	public void setTmmHolidayId(long tmmHolidayId) {
		this.tmmHolidayId = tmmHolidayId;
	}
	
	@Override
	public int getNoLimit() {
		return noLimit;
	}
	
	@Override
	public void setNoLimit(int noLimit) {
		this.noLimit = noLimit;
	}
	
	@Override
	public void setPaidHolidayCalc(int paidHolidayCalc) {
		this.paidHolidayCalc = paidHolidayCalc;
	}
	
}
