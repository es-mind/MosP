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
/**
 * 
 */
package jp.mosp.time.dto.settings;

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;

/**
 * 勤怠修正情報DTOインターフェース。<br>
 */
public interface AttendanceCorrectionDtoInterface extends BaseDtoInterface, PersonalIdDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getTmdAttendanceCorrectionId();
	
	/**
	 * @return 勤務日。
	 */
	Date getWorkDate();
	
	/**
	 * @return 勤務回数。
	 */
	int getWorks();
	
	/**
	 * @return 修正番号。
	 */
	int getCorrectionTimes();
	
	/**
	 * @return 修正日時。
	 */
	Date getCorrectionDate();
	
	/**
	 * @return 修正個人ID。
	 */
	String getCorrectionPersonalId();
	
	/**
	 * @return 修正箇所。
	 */
	String getCorrectionType();
	
	/**
	 * @return 修正前。
	 */
	String getCorrectionBefore();
	
	/**
	 * @return 修正後。
	 */
	String getCorrectionAfter();
	
	/**
	 * @return 修正理由。
	 */
	String getCorrectionReason();
	
	/**
	 * @param tmdAttendanceCorrectionId セットする レコード識別ID。
	 */
	void setTmdAttendanceCorrectionId(long tmdAttendanceCorrectionId);
	
	/**
	 * @param workDate セットする 勤務日。
	 */
	void setWorkDate(Date workDate);
	
	/**
	 * @param works セットする 勤務回数。
	 */
	void setWorks(int works);
	
	/**
	 * @param correctionTimes セットする 修正番号。
	 */
	void setCorrectionTimes(int correctionTimes);
	
	/**
	 * @param correctionDate セットする 修正日時。
	 */
	void setCorrectionDate(Date correctionDate);
	
	/**
	 * @param correctionPersonalId セットする 修正個人ID。
	 */
	void setCorrectionPersonalId(String correctionPersonalId);
	
	/**
	 * @param correctionType セットする 修正箇所。
	 */
	void setCorrectionType(String correctionType);
	
	/**
	 * @param correctionBefore セットする 修正前。
	 */
	void setCorrectionBefore(String correctionBefore);
	
	/**
	 * @param correctionAfter セットする 修正後。
	 */
	void setCorrectionAfter(String correctionAfter);
	
	/**
	 * @param correctionReason セットする 修正理由。
	 */
	void setCorrectionReason(String correctionReason);
	
}
