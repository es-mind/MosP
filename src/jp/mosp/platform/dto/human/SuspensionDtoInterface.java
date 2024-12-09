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
package jp.mosp.platform.dto.human;

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;

/**
 * 人事休職情報DTOインターフェース
 */
public interface SuspensionDtoInterface extends BaseDtoInterface, PersonalIdDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfaHumanSuspensionId();
	
	/**
	 * @return 開始日。
	 */
	Date getStartDate();
	
	/**
	 * @return 終了日。
	 */
	Date getEndDate();
	
	/**
	 * @return 終了予定日。
	 */
	Date getScheduleEndDate();
	
	/**
	 * @return 給与区分。
	 */
	String getAllowanceType();
	
	/**
	 * @return 休職理由。
	 */
	String getSuspensionReason();
	
	/**
	 * @param pfaHumanSuspensionId セットする レコード識別ID。
	 */
	void setPfaHumanSuspensionId(long pfaHumanSuspensionId);
	
	/**
	 * @param startDate セットする 開始日。
	 */
	void setStartDate(Date startDate);
	
	/**
	 * @param endDate セットする 終了日。
	 */
	void setEndDate(Date endDate);
	
	/**
	 * @param scheduleEndDate セットする 終了予定日。
	 */
	void setScheduleEndDate(Date scheduleEndDate);
	
	/**
	 * @param allowanceType セットする 給与区分。
	 */
	void setAllowanceType(String allowanceType);
	
	/**
	 * @param suspensionReason セットする 休職理由。
	 */
	void setSuspensionReason(String suspensionReason);
	
}
