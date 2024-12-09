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
package jp.mosp.platform.dto.system.impl;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.BankBaseDtoInterface;

/**
 * 銀行マスタDTOクラス。<br>
 */
public class PfmBankBaseDto extends BaseDto implements BankBaseDtoInterface {
	
	private static final long	serialVersionUID	= 618051835189221576L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmBankBaseId;
	/**
	 * 銀行コード。
	 */
	private String				bankCode;
	/**
	 * 銀行名。
	 */
	private String				bankName;
	/**
	 * 銀行名カナ。
	 */
	private String				bankNameKana;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getPfmBankBaseId() {
		return pfmBankBaseId;
	}
	
	@Override
	public String getBankCode() {
		return bankCode;
	}
	
	@Override
	public String getBankName() {
		return bankName;
	}
	
	@Override
	public String getBankNameKana() {
		return bankNameKana;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setPfmBankBaseId(long pfmBankBaseId) {
		this.pfmBankBaseId = pfmBankBaseId;
	}
	
	@Override
	public void setBankCode(String bankCode) {
		this.bankCode = bankCode;
	}
	
	@Override
	public void setBankName(String bankName) {
		this.bankName = bankName;
	}
	
	@Override
	public void setBankNameKana(String bankNameKana) {
		this.bankNameKana = bankNameKana;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
