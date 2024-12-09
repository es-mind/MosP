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
package jp.mosp.platform.dao.system;

import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.BankBranchDtoInterface;

/**
 * 銀行支店マスタDAOインターフェース。<br>
 */
public interface BankBranchDaoInterface extends BaseDaoInterface {
	
	/**
	 * 銀行コードと検索値からコードか名称で合致する銀行支店情報リスト取得。
	 * @param bankCode 銀行コード
	 * @param value 検索値
	 * @return 検索結果銀行支店情報リスト
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	List<BankBranchDtoInterface> searchBankBranchInfo(String bankCode, String value) throws MospException;
	
	/**
	 * 銀行コードと支店コードから銀行支店情報を取得する。
	 * @param bankCode 銀行コード
	 * @param branchCode 支店コード
	 * @return 銀行支店情報
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	BankBranchDtoInterface findForKey(String bankCode, String branchCode) throws MospException;
}
