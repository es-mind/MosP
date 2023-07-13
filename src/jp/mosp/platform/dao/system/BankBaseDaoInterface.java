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
package jp.mosp.platform.dao.system;

import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.BankBaseDtoInterface;

/**
 * 銀行マスタDAOインターフェース。<br>
 */
public interface BankBaseDaoInterface extends BaseDaoInterface {
	
	/**
	 * 検索値からコードか名称で合致する銀行情報リスト取得。
	 * @param value 検索値
	 * @return 検索結果銀行情報リスト
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	List<BankBaseDtoInterface> searchBankBaseInfo(String value) throws MospException;
	
	/**
	 * コードから銀行情報を取得する。
	 * @param code コード
	 * @return 銀行情報
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	BankBaseDtoInterface findForKey(String code) throws MospException;
}
