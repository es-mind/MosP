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

/**
 * 人事兼務情報DTOインターフェース。
 */
public interface ConcurrentDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfaHumanConcurrentId();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @return 開始日。
	 */
	Date getStartDate();
	
	/**
	 * @return 終了日。
	 */
	Date getEndDate();
	
	/**
	 * @return 所属コード。
	 */
	String getSectionCode();
	
	/**
	 * @return 職位コード。
	 */
	String getPositionCode();
	
	/**
	 * @return 備考。
	 */
	String getConcurrentRemark();
	
	/**
	 * @param pfaHumanConcurrentId セットする レコード識別ID。
	 */
	void setPfaHumanConcurrentId(long pfaHumanConcurrentId);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param startDate セットする 開始日。
	 */
	void setStartDate(Date startDate);
	
	/**
	 * @param endDate セットする 終了日。
	 */
	void setEndDate(Date endDate);
	
	/**
	 * @param sectionCode セットする 所属コード。
	 */
	void setSectionCode(String sectionCode);
	
	/**
	 * @param positionCode セットする 職位コード。
	 */
	void setPositionCode(String positionCode);
	
	/**
	 * @param concurrentRemark セットする 備考。
	 */
	void setConcurrentRemark(String concurrentRemark);
	
}
