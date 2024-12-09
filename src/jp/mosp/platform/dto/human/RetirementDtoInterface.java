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
 * 人事退職情報DTOインターフェース。
 */
public interface RetirementDtoInterface extends BaseDtoInterface, PersonalIdDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfaHumanRetirementId();
	
	/**
	 * @return 退職日。
	 */
	Date getRetirementDate();
	
	/**
	 * @return 退職理由。
	 */
	String getRetirementReason();
	
	/**
	 * @return 詳細。
	 */
	String getRetirementDetail();
	
	/**
	 * @param pfaHumanRetirementId セットする レコード識別ID。
	 */
	void setPfaHumanRetirementId(long pfaHumanRetirementId);
	
	/**
	 * @param retirementDate セットする 退職日。
	 */
	void setRetirementDate(Date retirementDate);
	
	/**
	 * @param retirementReason セットする 退職理由。
	 */
	void setRetirementReason(String retirementReason);
	
	/**
	 * @param retirementDetail セットする 詳細。
	 */
	void setRetirementDetail(String retirementDetail);
	
}
