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
package jp.mosp.platform.bean.system;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * ユーザマスタ参照インターフェース
 */
public interface UserMasterReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * ユーザマスタからレコードを取得する。<br>
	 * ユーザID、有効日で合致するレコードが無い場合、nullを返す。<br>
	 * @param userId       ユーザID
	 * @param activateDate 有効日
	 * @return ユーザマスタDTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	UserMasterDtoInterface findForKey(String userId, Date activateDate) throws MospException;
	
	/**
	 * ユーザマスタ取得。
	 * <p>
	 * ユーザIDと対象年月日からユーザマスタを取得。
	 * </p>
	 * @param userId ユーザID
	 * @param targetDate 対象年月日
	 * @return ユーザマスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	UserMasterDtoInterface getUserInfo(String userId, Date targetDate) throws MospException;
	
	/**
	 * ユーザマスタから履歴一覧を取得する。<br>
	 * 対象ユーザIDの履歴を取得する。<br>
	 * @param userId ユーザID
	 * @return ユーザマスタリスト(有効日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<UserMasterDtoInterface> getUserHistory(String userId) throws MospException;
	
	/**
	 * ユーザマスタから履歴一覧を取得する。<br>
	 * 対象個人IDの履歴を取得する。<br>
	 * @param personalId 個人ID
	 * @return ユーザマスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<UserMasterDtoInterface> getUserHistoryForPersonalId(String personalId) throws MospException;
	
	/**
	 * ユーザマスタから、対象日における対象者のユーザマスタリストを取得する。<br>
	 * <br>
	 * 一人に対して2つのユーザIDを付加した場合、リストには2件のユーザ情報が含まれる。<br>
	 * <br>
	 * @param personalId 対象者個人ID
	 * @param targetDate 対象年月日
	 * @return ユーザマスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<UserMasterDtoInterface> getUserListForPersonalId(String personalId, Date targetDate) throws MospException;
	
	/**
	 * ユーザマスタ取得。
	 * <p>
	 * レコード識別IDからユーザマスタリストを取得。
	 * </p>
	 * @param id レコード識別ID
	 * @return ユーザマスタ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	UserMasterDtoInterface findForkey(long id) throws MospException;
	
}
