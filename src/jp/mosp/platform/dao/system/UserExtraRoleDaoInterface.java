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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.UserExtraRoleDtoInterface;

/**
 * ユーザ追加ロール情報DAOインターフェース。<br>
 */
public interface UserExtraRoleDaoInterface extends BaseDaoInterface {
	
	/**
	 * ユーザID及び有効日が合致するユーザ追加ロール情報リストを取得する。<br>
	 * @param userId       ユーザID
	 * @param activateDate 有効日
	 * @return ユーザ追加ロール情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<UserExtraRoleDtoInterface> findForUser(String userId, Date activateDate) throws MospException;
	
	/**
	 * ロール区分が合致するユーザ追加ロール情報リストを取得する。<br>
	 * @param roleType ロール区分
	 * @return ユーザ追加ロール情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<UserExtraRoleDtoInterface> findForRoleType(String roleType) throws MospException;
	
	/**
	 * ユーザマスタ(pfa_user)に追加ロールコードを付加するSQLを取得する。<br>
	 * @param userIdColumn       ユーザID列名
	 * @param activateDateColumn 有効日列名
	 * @return ユーザマスタ(pfa_user)に追加ロールコードを付加するSQL
	 */
	String getQueryForJoinUser(String userIdColumn, String activateDateColumn);
	
	/**
	 * ユーザマスタ(pfa_user)に追加ロールコードを付加するSQLの列名(role_type)を取得する。<br>
	 * @return SQLの条件文用列名
	 */
	String getRoleTypeColumnForJoinUser();
	
	/**
	 * ユーザマスタ(pfa_user)に追加ロールコードを付加するSQLの列名(role_code)を取得する。<br>
	 * @return SQLの条件文用列名
	 */
	String getRoleCodeColumnForJoinUser();
	
}
