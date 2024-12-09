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
package jp.mosp.time.dto.settings;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 有給休暇設定情報インターフェース。<br>
 */
public interface PaidHolidayDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmPaidHolidayId();
	
	/**
	 * @return 有休コード。
	 */
	String getPaidHolidayCode();
	
	/**
	 * @return 有休名称。
	 */
	String getPaidHolidayName();
	
	/**
	 * @return 有休略称。
	 */
	String getPaidHolidayAbbr();
	
	/**
	 * @return 付与区分。
	 */
	int getPaidHolidayType();
	
	/**
	 * @return 出勤率。
	 */
	int getWorkRatio();
	
	/**
	 * @return 仮付与日。
	 */
	int getScheduleGiving();
	
	/**
	 * @return 時間単位有休機能。
	 */
	int getTimelyPaidHolidayFlag();
	
	/**
	 * @return 有休単位時間。
	 */
	int getTimelyPaidHolidayTime();
	
	/**
	 * @return 有休時間取得限度日数。
	 */
	int getTimeAcquisitionLimitDays();
	
	/**
	 * @return 有休時間取得限度時間。
	 */
	int getTimeAcquisitionLimitTimes();
	
	/**
	 * @return 有休申請時間間隔。
	 */
	int getAppliTimeInterval();
	
	/**
	 * @return 最大繰越日数。
	 */
	int getMaxCarryOverAmount();
	
	/**
	 * @return 合計最大保有日数。
	 */
	int getTotalMaxAmount();
	
	/**
	 * @return 有休繰越。
	 */
	int getMaxCarryOverYear();
	
	/**
	 * @return 時間単位繰越。
	 */
	int getMaxCarryOverTimes();
	
	/**
	 * @return 半日単位取得。
	 */
	int getHalfDayUnit();
	
	/**
	 * @return 休日出勤取扱。
	 */
	int getWorkOnHolidayCalc();
	
	/**
	 * @return 基準日(月)。
	 */
	int getPointDateMonth();
	
	/**
	 * @return 基準日(日)。
	 */
	int getPointDateDay();
	
	/**
	 * @return 登録情報超過後(基準日)。
	 */
	int getGeneralPointAmount();
	
	/**
	 * @return 登録情報超過後(月)。
	 */
	int getGeneralJoiningMonth();
	
	/**
	 * @return 登録情報超過後(日)。
	 */
	int getGeneralJoiningAmount();
	
	/**
	 * @param tmmPaidHolidayId セットする レコード識別ID。
	 */
	void setTmmPaidHolidayId(long tmmPaidHolidayId);
	
	/**
	 * @param paidHolidayCode セットする 有休コード。
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
	/**
	 * @param paidHolidayName セットする 有休名称。
	 */
	void setPaidHolidayName(String paidHolidayName);
	
	/**
	 * @param paidHolidayAbbr セットする 有休略称。
	 */
	void setPaidHolidayAbbr(String paidHolidayAbbr);
	
	/**
	 * @param paidHolidayType セットする 付与区分。
	 */
	void setPaidHolidayType(int paidHolidayType);
	
	/**
	 * @param workRatio セットする 出勤率。
	 */
	void setWorkRatio(int workRatio);
	
	/**
	 * @param scheduleGiving セットする 仮付与日。
	 */
	void setScheduleGiving(int scheduleGiving);
	
	/**
	 * @param timelyPaidHolidayFlag セットする 時間単位有休機能。
	 */
	void setTimelyPaidHolidayFlag(int timelyPaidHolidayFlag);
	
	/**
	 * @param timelyPaidHolidayTime セットする 有休単位時間。
	 */
	void setTimelyPaidHolidayTime(int timelyPaidHolidayTime);
	
	/**
	 * @param timeAcquisitionLimitDays セットする 有休時間取得限度日数。
	 */
	void setTimeAcquisitionLimitDays(int timeAcquisitionLimitDays);
	
	/**
	 * @param timeAcquisitionLimitTimes セットする 有休時間取得限度時間。
	 */
	void setTimeAcquisitionLimitTimes(int timeAcquisitionLimitTimes);
	
	/**
	 * @param appliTimeInterval セットする 申請時間間隔。
	 */
	void setAppliTimeInterval(int appliTimeInterval);
	
	/**
	 * @param maxCarryOverAmount セットする 最大繰越日数。
	 */
	void setMaxCarryOverAmount(int maxCarryOverAmount);
	
	/**
	 * @param totalMaxAmount セットする 合計最大保有日数。
	 */
	void setTotalMaxAmount(int totalMaxAmount);
	
	/**
	 * @param maxCarryOverYear セットする 最大繰越年数。
	 */
	void setMaxCarryOverYear(int maxCarryOverYear);
	
	/**
	 * @param maxCarryOverTimes セットする 時間単位繰越。
	 */
	void setMaxCarryOverTimes(int maxCarryOverTimes);
	
	/**
	 * @param halfDayUnit セットする 半日単位取得。
	 */
	void setHalfDayUnit(int halfDayUnit);
	
	/**
	 * @param workOnHolidayCalc セットする 休日出勤取扱。
	 */
	void setWorkOnHolidayCalc(int workOnHolidayCalc);
	
	/**
	 * @param pointDateMonth セットする 基準日(月)。
	 */
	void setPointDateMonth(int pointDateMonth);
	
	/**
	 * @param pointDateDay セットする 基準日(日)。
	 */
	void setPointDateDay(int pointDateDay);
	
	/**
	 * @param generalPointAmount セットする 登録情報超過後(基準日)。
	 */
	void setGeneralPointAmount(int generalPointAmount);
	
	/**
	 * @param generalJoiningMonth セットする 登録情報超過後(月)。
	 */
	void setGeneralJoiningMonth(int generalJoiningMonth);
	
	/**
	 * @param generalJoiningAmount セットする 登録情報超過後(日)。
	 */
	void setGeneralJoiningAmount(int generalJoiningAmount);
}
