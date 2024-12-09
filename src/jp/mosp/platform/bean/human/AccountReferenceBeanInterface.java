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
package jp.mosp.platform.bean.human;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.AccountDtoInterface;

/**
 * 口座情報参照インターフェース。<br>
 */
public interface AccountReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 対象個人IDの給与メイン振込口座情報リスト(有効日昇順)を取得する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @return 口座情報リスト(有効日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AccountDtoInterface> getPayMainAccountList(String personalId) throws MospException;
	
	/**
	 * 対象個人IDの給与サブ振込口座情報リスト(有効日昇順)を取得する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @return 口座情報リスト(有効日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AccountDtoInterface> getPaySubAccountList(String personalId) throws MospException;
	
	/**
	 * ワークフロー番号が合致する口座情報を取得する。<br>
	 * <br>
	 * 情報が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param workflow ワークフロー番号
	 * @return 口座情報
	 * @throws MospException SQLの作成に失敗した場合或いはSQL例外が発生した場合
	 */
	AccountDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 対象個人IDの銀行情報が存在するか確認する。
	 * @param personalId 個人ID
	 * @return 確認結果(true：銀行情報が存在する、false：銀行情報が存在しない)
	 * @throws MospException  SQLの作成に失敗した場合或いはSQL例外が発生した場合
	 */
	boolean isExistAccountInfo(String personalId) throws MospException;
	
}
