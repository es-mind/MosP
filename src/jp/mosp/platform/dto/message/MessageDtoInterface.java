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
package jp.mosp.platform.dto.message;

import java.util.Date;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * メッセージテーブルDTOインターフェース。<br>
 */
public interface MessageDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPftMessageId();
	
	/**
	 * @return メッセージNo。
	 */
	String getMessageNo();
	
	/**
	 * @return 公開開始日。
	 */
	Date getStartDate();
	
	/**
	 * @return 公開終了日。	
	 */
	Date getEndDate();
	
	/**
	 * @return メッセージ区分。
	 */
	int getMessageType();
	
	/**
	 * @return 重要度。
	 */
	int getMessageImportance();
	
	/**
	 * @return メッセージタイトル。
	 */
	String getMessageTitle();
	
	/**
	 * @return メッセージ本文。
	 */
	String getMessageBody();
	
	/**
	 * @return メッセージ適用範囲区分。
	 */
	int getApplicationType();
	
	/**
	 * @return 勤務地コード。
	 */
	String getWorkPlaceCode();
	
	/**
	 * @return 雇用契約コード。
	 */
	String getEmploymentContractCode();
	
	/**
	 * @return 所属コード。
	 */
	String getSectionCode();
	
	/**
	 * @return 職位コード。
	 */
	String getPositionCode();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @param pftMessageId セットする レコード識別ID。
	 */
	void setPftMessageId(long pftMessageId);
	
	/**
	 * @param messageNo セットする メッセージNo。
	 */
	void setMessageNo(String messageNo);
	
	/**
	 * @param startDate セットする 公開開始日。
	 */
	void setStartDate(Date startDate);
	
	/**
	 * @param endDate セットする 公開終了日。
	 */
	void setEndDate(Date endDate);
	
	/**
	 * @param messageType セットする メッセージ区分。
	 */
	void setMessageType(int messageType);
	
	/**
	 * @param messageImportance セットする 重要度。
	 */
	void setMessageImportance(int messageImportance);
	
	/**
	 * @param messageTitle セットする メッセージタイトル。
	 */
	void setMessageTitle(String messageTitle);
	
	/**
	 * @param messageBody セットする メッセージ本文。
	 */
	void setMessageBody(String messageBody);
	
	/**
	 * @param applicationType セットする メッセージ適用範囲区分。
	 */
	void setApplicationType(int applicationType);
	
	/**
	 * @param workPlaceCode セットする 勤務地コード。
	 */
	void setWorkPlaceCode(String workPlaceCode);
	
	/**
	 * @param employmentContractCode セットする 雇用契約コード。
	 */
	void setEmploymentContractCode(String employmentContractCode);
	
	/**
	 * @param sectionCode セットする 所属コード。
	 */
	void setSectionCode(String sectionCode);
	
	/**
	 * @param positionCode セットする 職位コード。
	 */
	void setPositionCode(String positionCode);
	
	/**
	 * @param personalIds セットする 個人ID。
	 */
	void setPersonalId(String personalIds);
	
}
