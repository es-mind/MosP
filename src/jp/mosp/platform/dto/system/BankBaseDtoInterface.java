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
package jp.mosp.platform.dto.system;

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * 銀行マスタDTOインターフェース。<br>
 */
public interface BankBaseDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmBankBaseId();
	
	/**
	 * @return 銀行コード。
	 */
	String getBankCode();
	
	/**
	 * @return 銀行名称。
	 */
	String getBankName();
	
	/**
	 * @return 銀行名称カナ。
	 */
	String getBankNameKana();
	
	/**
	 * 無効フラグを取得する。
	 * @return 無効フラグ
	 */
	int getInactivateFlag();
	
	/**
	 * @param pfmBankBaseId セットする レコード識別ID。
	 */
	void setPfmBankBaseId(long pfmBankBaseId);
	
	/**
	 * @param bankCode セットする 銀行コード。
	 */
	void setBankCode(String bankCode);
	
	/**
	 * @param bankName セットする 銀行名称。
	 */
	void setBankName(String bankName);
	
	/**
	 * @param bankNameKana セットする 銀行名称カナ。
	 */
	void setBankNameKana(String bankNameKana);
	
	/**
	 * 無効フラグを設定する。
	 * @param inactivateFlag セットする無効フラグ
	 */
	void setInactivateFlag(int inactivateFlag);
}
