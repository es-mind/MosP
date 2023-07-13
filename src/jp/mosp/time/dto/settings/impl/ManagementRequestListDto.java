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
package jp.mosp.time.dto.settings.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.time.dto.settings.ManagementRequestListDtoInterface;

/**
 * 申請情報確認一覧DTO
 */
public class ManagementRequestListDto extends BaseDto implements ManagementRequestListDtoInterface {
	
	private static final long	serialVersionUID	= -3081802468165846421L;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * 社員コード。
	 */
	private String				employeeCode;
	/**
	 * 社員氏名(姓)。
	 */
	private String				firstName;
	/**
	 * 社員氏名(名)。
	 */
	private String				lastName;
	/**
	 * 所属コード。
	 */
	private String				sectionCode;
	/**
	 * 申請カテゴリ。
	 */
	private String				requestType;
	/**
	 * 申請日付。
	 */
	private Date				requestDate;
	/**
	 * 申請情報詳細。
	 */
	private String				requestInfo;
	/**
	 * 承認状況。
	 */
	private String				state;
	/**
	 * 承認者。
	 */
	private String				approverName;
	/**
	 * ワークフロー番号。
	 */
	private long				workflow;
	/**
	 * 承認段階。
	 */
	private int					stage;
	
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public Date getRequestDate() {
		return getDateClone(requestDate);
	}
	
	@Override
	public String getRequestInfo() {
		return requestInfo;
	}
	
	@Override
	public String getRequestType() {
		return requestType;
	}
	
	@Override
	public String getState() {
		return state;
	}
	
	@Override
	public String getApproverName() {
		return approverName;
	}
	
	@Override
	public long getWorkflow() {
		return workflow;
	}
	
	@Override
	public int getStage() {
		return stage;
	}
	
	@Override
	public void setRequestDate(Date requestDate) {
		this.requestDate = getDateClone(requestDate);
	}
	
	@Override
	public void setRequestInfo(String requestInfo) {
		this.requestInfo = requestInfo;
	}
	
	@Override
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	@Override
	public void setState(String state) {
		this.state = state;
	}
	
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
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
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	
	@Override
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
	@Override
	public void setStage(int stage) {
		this.stage = stage;
	}
	
}
