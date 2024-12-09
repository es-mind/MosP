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
 * カレンダマスタDTOインターフェース
 */
public interface ScheduleDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmmScheduleId();
	
	/**
	 * @return カレンダコード。
	 */
	String getScheduleCode();
	
	/**
	 * @return カレンダ名称。
	 */
	String getScheduleName();
	
	/**
	 * @return カレンダ略称。
	 */
	String getScheduleAbbr();
	
	/**
	 * @return 年度。
	 */
	int getFiscalYear();
	
	/**
	 * @return パターンコード。
	 */
	String getPatternCode();
	
	/**
	 * @return 勤務形態変更フラグ。
	 */
	int getWorkTypeChangeFlag();
	
	/**
	 * @param tmmScheduleId セットする レコード識別ID。
	 */
	void setTmmScheduleId(long tmmScheduleId);
	
	/**
	 * @param scheduleCode セットする カレンダコード。
	 */
	void setScheduleCode(String scheduleCode);
	
	/**
	 * @param scheduleName セットする カレンダ名称。
	 */
	void setScheduleName(String scheduleName);
	
	/**
	 * @param scheduleAbbr セットする カレンダ略称。
	 */
	void setScheduleAbbr(String scheduleAbbr);
	
	/**
	 * @param fiscalYear セットする 年度。
	 */
	void setFiscalYear(int fiscalYear);
	
	/**
	 * @param patternCode セットする パターンコード。
	 */
	void setPatternCode(String patternCode);
	
	/**
	 * @param workTypeChangeFlag セットする 勤務形態変更フラグ。
	 */
	void setWorkTypeChangeFlag(int workTypeChangeFlag);
	
}
