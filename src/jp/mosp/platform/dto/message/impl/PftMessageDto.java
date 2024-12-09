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
package jp.mosp.platform.dto.message.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.base.EndDateDtoInterface;
import jp.mosp.platform.dto.message.MessageDtoInterface;

/**
 * メッセージテーブルDTO。<br>
 */
public class PftMessageDto extends BaseDto implements MessageDtoInterface, EndDateDtoInterface {
	
	private static final long	serialVersionUID	= 5683675629161867972L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pftMessageId;
	
	/**
	 * メッセージNo。
	 */
	private String				messageNo;
	
	/**
	 * 公開開始日。
	 */
	private Date				startDate;
	
	/**
	 * 公開終了日。
	 */
	private Date				endDate;
	
	/**
	 * メッセージ区分。
	 */
	private int					messageType;
	
	/**
	 * 重要度。
	 */
	private int					messageImportance;
	
	/**
	 * メッセージタイトル。
	 */
	private String				messageTitle;
	
	/**
	 * メッセージ本文。
	 */
	private String				messageBody;
	
	/**
	 * メッセージ適用範囲区分。
	 */
	private int					applicationType;
	
	/**
	 * 勤務地コード。
	 */
	private String				workPlaceCode;
	
	/**
	 * 雇用契約コード。
	 */
	private String				employmentContractCode;
	
	/**
	 * 所属コード。
	 */
	private String				sectionCode;
	
	/**
	 * 職位コード。
	 */
	private String				positionCode;
	
	/**
	 * 個人ID。
	 */
	private String				personalIds;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	/**
	 * コンストラクタ。
	 */
	public PftMessageDto() {
		// 処理無し
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(startDate);
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		startDate = getDateClone(activateDate);
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public long getPftMessageId() {
		return pftMessageId;
	}
	
	@Override
	public void setPftMessageId(long pftMessageId) {
		this.pftMessageId = pftMessageId;
	}
	
	@Override
	public String getMessageNo() {
		return messageNo;
	}
	
	@Override
	public void setMessageNo(String messageNo) {
		this.messageNo = messageNo;
	}
	
	@Override
	public Date getStartDate() {
		return getDateClone(startDate);
	}
	
	@Override
	public void setStartDate(Date startDate) {
		this.startDate = getDateClone(startDate);
	}
	
	@Override
	public Date getEndDate() {
		return getDateClone(endDate);
	}
	
	@Override
	public void setEndDate(Date endDate) {
		this.endDate = getDateClone(endDate);
	}
	
	@Override
	public int getMessageType() {
		return messageType;
	}
	
	@Override
	public void setMessageType(int messageType) {
		this.messageType = messageType;
	}
	
	@Override
	public int getMessageImportance() {
		return messageImportance;
	}
	
	@Override
	public void setMessageImportance(int messageImportance) {
		this.messageImportance = messageImportance;
	}
	
	@Override
	public String getMessageTitle() {
		return messageTitle;
	}
	
	@Override
	public void setMessageTitle(String messageTitle) {
		this.messageTitle = messageTitle;
	}
	
	@Override
	public String getMessageBody() {
		return messageBody;
	}
	
	@Override
	public void setMessageBody(String messageBody) {
		this.messageBody = messageBody;
	}
	
	@Override
	public int getApplicationType() {
		return applicationType;
	}
	
	@Override
	public void setApplicationType(int applicationType) {
		this.applicationType = applicationType;
	}
	
	@Override
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public String getEmploymentContractCode() {
		return employmentContractCode;
	}
	
	@Override
	public void setEmploymentContractCode(String employmentContractCode) {
		this.employmentContractCode = employmentContractCode;
	}
	
	@Override
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public String getPositionCode() {
		return positionCode;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public String getPersonalId() {
		return personalIds;
	}
	
	@Override
	public void setPersonalId(String personalIds) {
		this.personalIds = personalIds;
	}
	
}
