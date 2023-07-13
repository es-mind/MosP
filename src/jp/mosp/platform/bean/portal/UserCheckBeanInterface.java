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
package jp.mosp.platform.bean.portal;

import java.util.Date;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * ユーザ確認インターフェース。<br>
 */
public interface UserCheckBeanInterface extends BaseBeanInterface {
	
	/**
	 * ユーザ存在確認を行う。<br>
	 * 有効日及び有効/無効は不問とし、対象ユーザIDが存在するかのみを確認する。<br>
	 * ユーザパスワード情報登録時等に用いられる。<br>
	 * @param userId ユーザID
	 * @param row    行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkUserExist(String userId, Integer row) throws MospException;
	
	/**
	 * ユーザ存在確認を行う。<br>
	 * 有効/無効は不問とし、
	 * ユーザID及び有効日が合致する情報が存在するかのみを確認する。<br>
	 * ユーザ追加ロール情報登録時等に用いられる。<br>
	 * @param userId       ユーザID
	 * @param activateDate 有効日
	 * @param row          行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkUserExist(String userId, Date activateDate, Integer row) throws MospException;
	
	/**
	 * ユーザ存在確認を行う。<br>
	 * 対象日における有効なユーザが存在することを確認する。<br>
	 * <br>
	 * 後処理で用いるため、存在確認したユーザ情報を返す。<br>
	 * <br>
	 * @param userId     ユーザID
	 * @param targetDate 対象日
	 * @return 対象日におけるユーザ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	UserMasterDtoInterface checkUserExist(String userId, Date targetDate) throws MospException;
	
	/**
	 * ユーザ社員の確認を行う。<br>
	 * ユーザIDから個人IDを取得し、
	 * {@link #checkUserEmployee(String, Date)}を行う。<br>
	 * 認証時等に用いられる。<br>
	 * @param userId     ユーザID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkUserEmployeeForUserId(String userId, Date targetDate) throws MospException;
	
	/**
	 * ユーザ妥当性の確認を行う。<br>
	 * <ul><li>
	 * 入社確認
	 * </li><li>
	 * 休職確認
	 * </li><li>
	 * 退職確認
	 * </li></ul>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkUserEmployee(String personalId, Date targetDate) throws MospException;
	
	/**
	 * ユーザロールの存在確認を行う。<br>
	 * @param roleCode   ロールコード
	 * @param targetDate 対象日
	 * @param row        行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkRoleExist(String roleCode, Date targetDate, Integer row) throws MospException;
	
	/**
	 * ユーザロールの存在確認を行う。<br>
	 * @param roleCode   ロールコード
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkRoleExist(String roleCode, Date targetDate) throws MospException;
	
	/**
	 * ユーザロールの存在確認を行う。<br>
	 * ロール自体は存在しても、ロール区分が異なる場合は存在しないと判断する。<br>
	 * <br>
	 * @param roleType   ロール区分
	 * @param roleCode   ロールコード
	 * @param targetDate 対象日
	 * @param row        行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkRoleExist(String roleType, String roleCode, Date targetDate, Integer row) throws MospException;
	
	/**
	 * ユーザロールの存在確認を行う。<br>
	 * ユーザIDからロールコードを取得し、
	 * {@link #checkRoleExist(String, Date)}を行う。<br>
	 * @param userId     ユーザID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkUserRole(String userId, Date targetDate) throws MospException;
	
	/**
	 * ロール区分存在確認を行う。<br>
	 * ユーザ追加ロール情報登録時等に用いられる。<br>
	 * @param roleType   ロール区分
	 * @param targetDate 対象日
	 * @param row        行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkRoleTypeExist(String roleType, Date targetDate, Integer row) throws MospException;
	
}
