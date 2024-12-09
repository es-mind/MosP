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
package jp.mosp.platform.dto.system;

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * 銀行支店マスタDTOインターフェース。<br>
 */
public interface BankBranchDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmBankBranchId();
	
	/**
	 * @return 銀行コード。
	 */
	String getBankCode();
	
	/**
	 * @return branchCode 支店コード
	 */
	public String getBranchCode();
	
	/**
	 * @return branchName 支店名漢字
	 */
	public String getBranchName();
	
	/**
	 * @return branchKana 支店名ｶﾅ
	 */
	public String getBranchKana();
	
	/**
	 * @return branchCode 支店所在地
	 */
	public String getBranchAddressName();
	
	/**
	 * @return branchCode 支店所在地カナ
	 */
	public String getBranchAddressKana();
	
	/**
	 * @return branchCode 支店電話番号
	 */
	public String getBranchPhone();
	
	/**
	 * 無効フラグを取得する。
	 * @return 無効フラグ
	 */
	int getInactivateFlag();
	
	/**
	 * @param pfmBankBranchId セットする レコード識別ID。
	 */
	void setPfmBankBranchId(long pfmBankBranchId);
	
	/**
	 * @param bankCode セットする 銀行コード。
	 */
	void setBankCode(String bankCode);
	
	/**
	 * 支店コードを設定する
	 * 
	 * @param branchCode 支店コード
	 */
	public void setBranchCode(String branchCode);
	
	/**
	 * @param branchName 支店名
	 */
	public void setBranchName(String branchName);
	
	/**
	 * @param branchKana 支店名カナ
	 */
	public void setBranchKana(String branchKana);
	
	/**
	 * @param branchAddressName 支店所在地
	 */
	public void setBranchAddressName(String branchAddressName);
	
	/**
	 * @param branchAddressKana 支店所在地ｶﾅ
	 */
	public void setBranchAddressKana(String branchAddressKana);
	
	/**
	 * @param branchPhone 支店電話番号
	 */
	public void setBranchPhone(String branchPhone);
	
	/**
	 * 無効フラグを設定する。
	 * @param inactivateFlag セットする無効フラグ
	 */
	void setInactivateFlag(int inactivateFlag);
	
}
