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
import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.EmploymentContractCodeDtoInterface;
import jp.mosp.platform.dto.base.PositionCodeDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;
import jp.mosp.platform.dto.base.WorkPlaceCodeDtoInterface;

/**
 * 設定適用マスタDTOインターフェース。
 */
public interface ApplicationReferenceDtoInterface
		extends EmployeeCodeDtoInterface, EmployeeNameDtoInterface, SectionCodeDtoInterface, WorkPlaceCodeDtoInterface,
		PositionCodeDtoInterface, PlatformDtoInterface, EmploymentContractCodeDtoInterface {
	
	/**
	 * @return 個人ID
	 */
	String getPersonalId();
	
	/**
	 * @return 社員名
	 */
	String getEmployeeName();
	
	/**
	 * @return 設定適用コード
	 */
	String getApplicationCode();
	
	/**
	 * @return 設定適用名称
	 */
	String getApplicationName();
	
	/**
	 * @return 設定適用略称
	 */
	String getApplicationAbbr();
	
	/**
	 * @return 設定適用設定(略称+コード) 
	 */
	String getApplication();
	
	/**
	 * @return 勤怠設定コード
	 */
	String getTimeSettingCode();
	
	/**
	 * @return 勤怠設定名称
	 */
	String getTimeSettingName();
	
	/**
	 * @return 勤怠設定略称 
	 */
	String getTimeSettingAbbr();
	
	/**
	 * @return 勤怠設定(略称+コード) 
	 */
	String getTimeSetting();
	
	/**
	 * @return 締日(略称+コード) 
	 */
	String getCutoff();
	
	/**
	 * @return 締日略称 
	 */
	String getCutoffAbbr();
	
	/**
	 * @return 締日名称 
	 */
	String getCutoffName();
	
	/**
	 * @return 締日コード 
	 */
	String getCutoffCode();
	
	/**
	 * @return カレンダ(略称+コード) 
	 */
	String getSchedule();
	
	/**
	 * @return カレンダ名称 
	 */
	String getScheduleName();
	
	/**
	 * @return カレンダコード 
	 */
	String getScheduleCode();
	
	/**
	 * @return カレンダ略称
	 */
	String getScheduleAbbr();
	
	/**
	 * @return 有休設定(略称+コード) 
	 */
	String getPaidHoliday();
	
	/**
	 * @return 有休設定名称 
	 */
	String getPaidHolidayName();
	
	/**
	 * @return 有休設定コード 
	 */
	String getPaidHolidayCode();
	
	/**
	 * @return 有休設定略称 
	 */
	String getPaidHolidayAbbr();
	
	/**
	 * @param getPersonalId セットする 個人ID。
	 */
	void setPersonalId(String getPersonalId);
	
	/**
	 * @param employeeName セットする 社員名。
	 */
	void setEmployeeName(String employeeName);
	
	/**
	 * @param applicationCode セットする 設定適用コード
	 */
	void setApplicationCode(String applicationCode);
	
	/**
	 * @param applicationName セットする 設定適用名称
	 */
	void setApplicationName(String applicationName);
	
	/**
	 * @param application セットする 設定適用名称(略称+コード) 
	 */
	void setApplication(String application);
	
	/**
	 * @param applicationAbbr セットする 設定適用略称
	 */
	void setApplicationAbbr(String applicationAbbr);
	
	/**
	 * @param timeSetting 勤怠設定名称(略称+コード) 
	 */
	void setTimeSetting(String timeSetting);
	
	/**
	 * @param timeSettingCode 勤怠設定コード
	 */
	void setTimeSettingCode(String timeSettingCode);
	
	/**
	 * @param timeSettingName 勤怠設定名称
	 */
	void setTimeSettingName(String timeSettingName);
	
	/**
	 * @param timeSettingAbbr 勤怠設定略称
	 */
	void setTimeSettingAbbr(String timeSettingAbbr);
	
	/**
	 * @param cutoff 締日(略称+コード) 
	 */
	void setCutoff(String cutoff);
	
	/**
	 * @param cutoffCode 締日コード
	 */
	void setCutoffCode(String cutoffCode);
	
	/**
	 * @param cutoffAbbr 締日略称
	 */
	void setCutoffAbbr(String cutoffAbbr);
	
	/**
	 * @param cutoffName 締日名称
	 */
	void setCutoffName(String cutoffName);
	
	/**
	 * @param schedule  カレンダ(略称+コード) 
	 */
	void setSchedule(String schedule);
	
	/**
	 * @param scheduleCode  カレンダコード
	 */
	void setScheduleCode(String scheduleCode);
	
	/**
	 * @param scheduleName  カレンダ名称
	 */
	void setScheduleName(String scheduleName);
	
	/**
	 * @param scheduleAbbr  カレンダコード略称
	 */
	void setScheduleAbbr(String scheduleAbbr);
	
	/**
	 * @param paidHoliday セットする 有休(略称+コード) 
	 */
	void setPaidHoliday(String paidHoliday);
	
	/**
	 * @param paidHolidayName セットする 有休名称
	 */
	void setPaidHolidayName(String paidHolidayName);
	
	/**
	 * @param paidHolidayCode セットする 有休コード
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
	/**
	 * @param paidHolidayAbbr セットする 有休略称
	 */
	void setPaidHolidayAbbr(String paidHolidayAbbr);
	
}
