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
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;

/**
 * 有給休暇設定情報。<br>
 */
public class TmmPaidHolidayDto extends BaseDto implements PaidHolidayDtoInterface {
	
	private static final long	serialVersionUID	= -5031610009978139473L;
	
	/**
	 * レコード識別ID。
	 */
	private long				tmmPaidHolidayId;
	/**
	 * 有休コード。
	 */
	private String				paidHolidayCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 有休名称。
	 */
	private String				paidHolidayName;
	/**
	 * 有休略称。
	 */
	private String				paidHolidayAbbr;
	/**
	 * 付与区分。
	 */
	private int					paidHolidayType;
	/**
	 * 仮付与日。
	 */
	private int					scheduleGiving;
	/**
	 * 出勤率。
	 */
	private int					workRatio;
	/**
	 * 時間単位有休機能。
	 */
	private int					timelyPaidHolidayFlag;
	/**
	 * 有休単位時間。
	 */
	private int					timelyPaidHolidayTime;
	/**
	 * 有休時間取得限度日数。
	 */
	private int					timeAcquisitionLimitDays;
	/**
	 * 有休時間取得限度時間。
	 */
	private int					timeAcquisitionLimitTimes;
	/**
	 * 申請時間間隔。
	 */
	private int					appliTimeInterval;
	/**
	 * 最大繰越日数。
	 */
	private int					maxCarryOverAmount;
	/**
	 * 合計最大保有日数。
	 */
	private int					totalMaxAmount;
	/**
	 * 最大繰越年数。
	 */
	private int					maxCarryOverYear;
	/**
	 * 時間単位繰越。
	 */
	private int					maxCarryOverTimes;
	/**
	 * 半日単位取得。
	 */
	private int					halfDayUnit;
	/**
	 * 休日出勤取扱。
	 */
	private int					workOnHolidayCalc;
	/**
	 * 基準日(月)。
	 */
	private int					pointDateMonth;
	/**
	 * 基準日(日)。
	 */
	private int					pointDateDay;
	/**
	 * 登録情報超過後(基準日)。
	 */
	private int					generalPointAmount;
	/**
	 * 登録情報超過後(月)。
	 */
	private int					generalJoiningMonth;
	/**
	 * 登録情報超過後(日)。
	 */
	private int					generalJoiningAmount;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public int getGeneralJoiningAmount() {
		return generalJoiningAmount;
	}
	
	@Override
	public int getGeneralJoiningMonth() {
		return generalJoiningMonth;
	}
	
	@Override
	public int getMaxCarryOverAmount() {
		return maxCarryOverAmount;
	}
	
	@Override
	public int getAppliTimeInterval() {
		return appliTimeInterval;
	}
	
	@Override
	public int getMaxCarryOverYear() {
		return maxCarryOverYear;
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
	public int getPaidHolidayType() {
		return paidHolidayType;
	}
	
	@Override
	public int getPointDateDay() {
		return pointDateDay;
	}
	
	@Override
	public int getPointDateMonth() {
		return pointDateMonth;
	}
	
	@Override
	public long getTmmPaidHolidayId() {
		return tmmPaidHolidayId;
	}
	
	@Override
	public int getTotalMaxAmount() {
		return totalMaxAmount;
	}
	
	@Override
	public int getWorkRatio() {
		return workRatio;
	}
	
	@Override
	public void setGeneralJoiningAmount(int generalJoiningAmount) {
		this.generalJoiningAmount = generalJoiningAmount;
	}
	
	@Override
	public void setGeneralJoiningMonth(int generalJoiningMonth) {
		this.generalJoiningMonth = generalJoiningMonth;
	}
	
	@Override
	public void setAppliTimeInterval(int appliTimeInterval) {
		this.appliTimeInterval = appliTimeInterval;
	}
	
	@Override
	public void setMaxCarryOverAmount(int maxCarryOverAmount) {
		this.maxCarryOverAmount = maxCarryOverAmount;
	}
	
	@Override
	public void setMaxCarryOverYear(int maxCarryOverYear) {
		this.maxCarryOverYear = maxCarryOverYear;
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
	public void setPaidHolidayType(int paidHolidayType) {
		this.paidHolidayType = paidHolidayType;
	}
	
	@Override
	public void setPointDateDay(int pointDateDay) {
		this.pointDateDay = pointDateDay;
	}
	
	@Override
	public void setPointDateMonth(int pointDateMonth) {
		this.pointDateMonth = pointDateMonth;
	}
	
	@Override
	public void setTmmPaidHolidayId(long tmmPaidHolidayId) {
		this.tmmPaidHolidayId = tmmPaidHolidayId;
	}
	
	@Override
	public void setTotalMaxAmount(int totalMaxAmount) {
		this.totalMaxAmount = totalMaxAmount;
	}
	
	@Override
	public void setWorkRatio(int workRatio) {
		this.workRatio = workRatio;
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
	public int getGeneralPointAmount() {
		return generalPointAmount;
	}
	
	@Override
	public void setGeneralPointAmount(int generalPointAmount) {
		this.generalPointAmount = generalPointAmount;
	}
	
	@Override
	public int getScheduleGiving() {
		return scheduleGiving;
	}
	
	@Override
	public void setScheduleGiving(int scheduleGiving) {
		this.scheduleGiving = scheduleGiving;
	}
	
	@Override
	public int getTimelyPaidHolidayFlag() {
		return timelyPaidHolidayFlag;
	}
	
	@Override
	public int getTimelyPaidHolidayTime() {
		return timelyPaidHolidayTime;
	}
	
	@Override
	public void setTimelyPaidHolidayFlag(int timelyPaidHolidayFlag) {
		this.timelyPaidHolidayFlag = timelyPaidHolidayFlag;
	}
	
	@Override
	public void setTimelyPaidHolidayTime(int timelyPaidHolidayTime) {
		this.timelyPaidHolidayTime = timelyPaidHolidayTime;
	}
	
	@Override
	public int getHalfDayUnit() {
		return halfDayUnit;
	}
	
	@Override
	public int getMaxCarryOverTimes() {
		return maxCarryOverTimes;
	}
	
	@Override
	public int getTimeAcquisitionLimitDays() {
		return timeAcquisitionLimitDays;
	}
	
	@Override
	public int getTimeAcquisitionLimitTimes() {
		return timeAcquisitionLimitTimes;
	}
	
	@Override
	public void setHalfDayUnit(int halfDayUnit) {
		this.halfDayUnit = halfDayUnit;
	}
	
	@Override
	public void setMaxCarryOverTimes(int maxCarryOverTimes) {
		this.maxCarryOverTimes = maxCarryOverTimes;
	}
	
	@Override
	public void setTimeAcquisitionLimitDays(int timeAcquisitionLimitDays) {
		this.timeAcquisitionLimitDays = timeAcquisitionLimitDays;
	}
	
	@Override
	public void setTimeAcquisitionLimitTimes(int timeAcquisitionLimitTimes) {
		this.timeAcquisitionLimitTimes = timeAcquisitionLimitTimes;
	}
	
	@Override
	public int getWorkOnHolidayCalc() {
		return workOnHolidayCalc;
	}
	
	@Override
	public void setWorkOnHolidayCalc(int workOnHolidayCalc) {
		this.workOnHolidayCalc = workOnHolidayCalc;
	}
	
}
