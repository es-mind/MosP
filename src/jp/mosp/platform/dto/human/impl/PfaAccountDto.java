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
package jp.mosp.platform.dto.human.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.human.AccountDtoInterface;

/**
 * 口座情報DTO。<br>
 */
public class PfaAccountDto extends BaseDto implements AccountDtoInterface {
	
	private static final long	serialVersionUID	= 3114406979112720614L;
	
	/**
	 * レコード識別ID。<br>
	 */
	private long				pfaAccountId;
	
	/**
	 * 保持者ID。<br>
	 */
	private String				holderId;
	
	/**
	 * 口座区分。<br>
	 */
	private String				accountType;
	
	/**
	 * 有効日。<br>
	 */
	private Date				activateDate;
	
	/**
	 * 銀行コード。<br>
	 */
	private String				bankCode;
	
	/**
	 * 銀行名。<br>
	 */
	private String				bankName;
	
	/**
	 * 支店コード。<br>
	 */
	private String				branchCode;
	
	/**
	 * 支店名。<br>
	 */
	private String				branchName;
	
	/**
	 * 口座種別(普通/当座)。<br>
	 */
	private String				accountClass;
	
	/**
	 * 口座番号。<br>
	 */
	private String				accountNumber;
	
	/**
	 * 口座名義。<br>
	 */
	private String				accountHolder;
	
	/**
	 * 定額(給与)。<br>
	 */
	private int					fixedPayment;
	
	/**
	 * 定額(賞与)。<br>
	 */
	private int					fixedBonus;
	
	/**
	 * 申請区分。<br>
	 */
	private String				requestType;
	
	/**
	 * ワークフロー番号。<br>
	 */
	private long				workflow;
	
	
	@Override
	public long getPfaAccountId() {
		return pfaAccountId;
	}
	
	@Override
	public void setPfaAccountId(long pfaAccountId) {
		this.pfaAccountId = pfaAccountId;
	}
	
	@Override
	public String getHolderId() {
		return holderId;
	}
	
	@Override
	public void setHolderId(String holderId) {
		this.holderId = holderId;
	}
	
	@Override
	public String getAccountType() {
		return accountType;
	}
	
	@Override
	public void setAccountType(String accountType) {
		this.accountType = accountType;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public String getBankCode() {
		return bankCode;
	}
	
	@Override
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Override
	public String getBranchCode() {
		return branchCode;
	}
	
	@Override
	public void setBranchCode(String branchCode) {
		this.branchCode = branchCode;
	}
	
	@Override
	public String getAccountClass() {
		return accountClass;
	}
	
	@Override
	public void setAccountClass(String accountClass) {
		this.accountClass = accountClass;
	}
	
	@Override
	public String getAccountNumber() {
		return accountNumber;
	}
	
	@Override
	public void setAccountNumber(String accountNumber) {
		this.accountNumber = accountNumber;
	}
	
	@Override
	public String getAccountHolder() {
		return accountHolder;
	}
	
	@Override
	public void setAccountHolder(String accountHolder) {
		this.accountHolder = accountHolder;
	}
	
	@Override
	public int getFixedPayment() {
		return fixedPayment;
	}
	
	@Override
	public void setFixedPayment(int fixedPayment) {
		this.fixedPayment = fixedPayment;
	}
	
	@Override
	public int getFixedBonus() {
		return fixedBonus;
	}
	
	@Override
	public void setFixedBonus(int fixedBonus) {
		this.fixedBonus = fixedBonus;
	}
	
	@Override
	public String getRequestType() {
		return requestType;
	}
	
	@Override
	public void setRequestType(String requestType) {
		this.requestType = requestType;
	}
	
	@Override
	public long getWorkflow() {
		return workflow;
	}
	
	@Override
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
	@Override
	public String getBankName() {
		return bankName;
	}
	
	@Override
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Override
	public String getBranchName() {
		return branchName;
	}
	
	@Override
	public void setBranchName(String branchName) {
		this.branchName = branchName;
	}
	
}
