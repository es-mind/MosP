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
package jp.mosp.platform.dto.human;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.base.ActivateDtoInterface;
import jp.mosp.platform.dto.base.WorkflowNumberDtoInterface;

/**
 * 口座情報DTOインターフェース。<br>
 */
public interface AccountDtoInterface extends BaseDtoInterface, ActivateDtoInterface, WorkflowNumberDtoInterface {
	
	/**
	 * @return pfaAccountId
	 */
	long getPfaAccountId();
	
	/**
	 * @param pfaAccountId セットする pfaAccountId
	 */
	void setPfaAccountId(long pfaAccountId);
	
	/**
	 * @return holderId
	 */
	String getHolderId();
	
	/**
	 * @param holderId セットする holderId
	 */
	void setHolderId(String holderId);
	
	/**
	 * @return accountType
	 */
	String getAccountType();
	
	/**
	 * @param accountType セットする accountType
	 */
	void setAccountType(String accountType);
	
	/**
	 * @return bankCode
	 */
	String getBankCode();
	
	/**
	 * @param bankCode セットする bankCode
	 */
	void setBankCode(String bankCode);
	
	/**
	 * @return bankName
	 */
	String getBankName();
	
	/**
	 * @param bankName セットする bankName
	 */
	void setBankName(String bankName);
	
	/**
	 * @return branchCode
	 */
	String getBranchCode();
	
	/**
	 * @param branchCode セットする branchCode
	 */
	void setBranchCode(String branchCode);
	
	/**
	 * @return branchName
	 */
	String getBranchName();
	
	/**
	 * @param branchName セットする branchName
	 */
	void setBranchName(String branchName);
	
	/**
	 * @return accountClass
	 */
	String getAccountClass();
	
	/**
	 * @param accountClass セットする accountClass
	 */
	void setAccountClass(String accountClass);
	
	/**
	 * @return accountNumber
	 */
	String getAccountNumber();
	
	/**
	 * @param accountNumber セットする accountNumber
	 */
	void setAccountNumber(String accountNumber);
	
	/**
	 * @return accountHolder
	 */
	String getAccountHolder();
	
	/**
	 * @param accountHolder セットする accountHolder
	 */
	void setAccountHolder(String accountHolder);
	
	/**
	 * @return fixedPayment
	 */
	int getFixedPayment();
	
	/**
	 * @param fixedPayment セットする fixedPayment
	 */
	void setFixedPayment(int fixedPayment);
	
	/**
	 * @return fixedBonus
	 */
	int getFixedBonus();
	
	/**
	 * @param fixedBonus セットする fixedBonus
	 */
	void setFixedBonus(int fixedBonus);
	
	/**
	 * @return requestType
	 */
	String getRequestType();
	
	/**
	 * @param requestType セットする requestType
	 */
	void setRequestType(String requestType);
	
}
