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
package jp.mosp.platform.dao.human;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.AccountDtoInterface;

/**
 * 口座情報DAOインターフェース。<br>
 */
public interface AccountDaoInterface extends BaseDaoInterface {
	
	/**
	 * 口座情報を取得する。<br>
	 * <br>
	 * 保持者ID、口座区分及び有効日が合致する情報が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param holderId     保持者ID
	 * @param accountType  口座区分
	 * @param activateDate 有効日
	 * @return 口座情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AccountDtoInterface findForKey(String holderId, String accountType, Date activateDate) throws MospException;
	
	/**
	 * 口座情報を取得する。<br>
	 * <br>
	 * 保持者ID及び口座区分が合致する対象日以前で最新の情報を取得する。<br>
	 * 情報が存在しない場合は、nullを返す。<br>
	 * <br>
	 * @param holderId    保持者ID
	 * @param accountType 口座区分
	 * @param targetDate  対象日
	 * @return 口座情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	AccountDtoInterface findForInfo(String holderId, String accountType, Date targetDate) throws MospException;
	
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
	 * 口座情報リスト(有効日昇順)を取得する。<br>
	 * @param holderId    保持者ID
	 * @param accountType 口座区分
	 * @return 口座情報リスト(有効日昇順)
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<AccountDtoInterface> findForHolder(String holderId, String accountType) throws MospException;
	
	/**
	 * 対象個人IDの口座情報リストを全て取得する。<br>
	 * @param personalId 個人ID
	 * @return 全口座情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<AccountDtoInterface> findForAll(String personalId) throws MospException;
}
