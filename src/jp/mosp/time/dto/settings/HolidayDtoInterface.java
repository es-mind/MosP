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
package jp.mosp.time.dto.settings;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 休暇種別管理DTOインターフェース
 */
public interface HolidayDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmHolidayId();
	
	/**
	 * @return 休暇コード。
	 */
	String getHolidayCode();
	
	/**
	 * @return 休暇区分。
	 */
	int getHolidayType();
	
	/**
	 * @return 休暇名称。
	 */
	String getHolidayName();
	
	/**
	 * @return 休暇略称。
	 */
	String getHolidayAbbr();
	
	/**
	 * @return 標準付与日数。
	 */
	double getHolidayGiving();
	
	/**
	 * @return 付与日数無制限。
	 */
	int getNoLimit();
	
	/**
	 * @return 取得期限(月)。
	 */
	int getHolidayLimitMonth();
	
	/**
	 * @return 取得期限(日)。
	 */
	int getHolidayLimitDay();
	
	/**
	 * @return 半休申請。
	 */
	int getHalfHolidayRequest();
	
	/**
	 * @return 連続取得。
	 */
	int getContinuousAcquisition();
	
	/**
	 * @return 時間単位休暇機能。
	 */
	int getTimelyHolidayFlag();
	
	/**
	 * @return 出勤率計算。
	 */
	int getPaidHolidayCalc();
	
	/**
	 * @return 有給/無給。
	 */
	int getSalary();
	
	/**
	 * @return 理由種別。
	 */
	int getReasonType();
	
	/**
	 * @param tmmHolidayId セットする レコード識別ID。
	 */
	void setTmmHolidayId(long tmmHolidayId);
	
	/**
	 * @param holidayCode セットする 休暇コード。
	 */
	void setHolidayCode(String holidayCode);
	
	/**
	 * @param holidayType セットする 休暇区分。
	 */
	void setHolidayType(int holidayType);
	
	/**
	 * @param holidayName セットする 休暇名称。
	 */
	void setHolidayName(String holidayName);
	
	/**
	 * @param holidayAbbr セットする 休暇略称。
	 */
	void setHolidayAbbr(String holidayAbbr);
	
	/**
	 * @param holidayGiving セットする 標準付与日数。
	 */
	void setHolidayGiving(double holidayGiving);
	
	/**
	 * @param noLimit セットする 付与日数無制限。
	 */
	void setNoLimit(int noLimit);
	
	/**
	 * @param holidayLimitMonth セットする 取得期限(月)。
	 */
	void setHolidayLimitMonth(int holidayLimitMonth);
	
	/**
	 * @param holidayLimitDay セットする 取得期限(日)。
	 */
	void setHolidayLimitDay(int holidayLimitDay);
	
	/**
	 * @param halfHolidayRequest セットする 半休申請。
	 */
	void setHalfHolidayRequest(int halfHolidayRequest);
	
	/**
	 * @param continuousAcquisition セットする 連続取得。
	 */
	void setContinuousAcquisition(int continuousAcquisition);
	
	/**
	 * @param timelyHolidayFlag セットする 時間単位休暇機能。
	 */
	void setTimelyHolidayFlag(int timelyHolidayFlag);
	
	/**
	 * @param paidHolidayCalc セットする 出勤率計算。
	 */
	void setPaidHolidayCalc(int paidHolidayCalc);
	
	/**
	 * @param salary セットする 有給/無給。
	 */
	void setSalary(int salary);
	
	/**
	 * @param reasonType セットする 理由種別。
	 */
	void setReasonType(int reasonType);
	
}
