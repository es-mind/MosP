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

import java.util.Date;

import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;

/**
 * 有給休暇データ一覧DTOインターフェース
 */
public interface PaidHolidayDataGrantListDtoInterface
		extends PlatformDtoInterface, PersonalIdDtoInterface, EmployeeCodeDtoInterface, EmployeeNameDtoInterface {
	
	/**
	 * @return 付与日
	 */
	Date getGrantDate();
	
	/**
	 * @param grantDate セットする 付与日
	 */
	void setGrantDate(Date grantDate);
	
	/**
	 * @return 開始日
	 */
	Date getFirstDate();
	
	/**
	 * @param firstDate セットする 開始日
	 */
	void setFirstDate(Date firstDate);
	
	/**
	 * @return 終了日
	 */
	Date getLastDate();
	
	/**
	 * @param lastDate セットする 終了日
	 */
	void setLastDate(Date lastDate);
	
	/**
	 * @return 労働日数
	 */
	Integer getWorkDays();
	
	/**
	 * @param workDays セットする 労働日数
	 */
	void setWorkDays(Integer workDays);
	
	/**
	 * @return 全労働日数
	 */
	Integer getTotalWorkDays();
	
	/**
	 * @param totalWorkDays セットする 全労働日数
	 */
	void setTotalWorkDays(Integer totalWorkDays);
	
	/**
	 * @return 出勤率
	 */
	Double getAttendanceRate();
	
	/**
	 * @param attendanceRate セットする 出勤率
	 */
	void setAttendanceRate(Double attendanceRate);
	
	/**
	 * @return 出勤率基準
	 */
	String getAccomplish();
	
	/**
	 * @param accomplish セットする 出勤率基準
	 */
	void setAccomplish(String accomplish);
	
	/**
	 * @return 付与状態
	 */
	String getGrant();
	
	/**
	 * @param grant セットする 付与状態
	 */
	void setGrant(String grant);
	
	/**
	 * @return 付与日数
	 */
	Double getGrantDays();
	
	/**
	 * @param grantDays セットする 付与日数
	 */
	void setGrantDays(Double grantDays);
	
	/**
	 * @return 警告
	 */
	boolean isWarning();
	
	/**
	 * @param warning セットする 警告
	 */
	void setWarning(boolean warning);
	
	/**
	 * @return エラー
	 */
	String getError();
	
	/**
	 * @param error セットする エラー
	 */
	void setError(String error);
	
}
