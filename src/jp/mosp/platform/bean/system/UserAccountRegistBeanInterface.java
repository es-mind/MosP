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
package jp.mosp.platform.bean.system;

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.UserExtraRoleDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * ユーザアカウント情報登録処理インターフェース。<br>
 */
public interface UserAccountRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 初期ユーザ情報を取得する。<br>
	 * @return 初期ユーザ情報
	 */
	UserMasterDtoInterface getInitUserDto();
	
	/**
	 * 初期ユーザ追加ロール情報群を取得する。<br>
	 * @param number 個数
	 * @return 初期ユーザ追加ロール情報群
	 */
	Set<UserExtraRoleDtoInterface> getInitExtraRoleDtos(int number);
	
	/**
	 * 初期ユーザ追加ロール情報を取得する。<br>
	 * @return 初期ユーザ追加ロール情報
	 */
	UserExtraRoleDtoInterface getInitExtraRoleDto();
	
	/**
	 * 新規登録を行う。<br>
	 * パスワードは、初期パスワードが設定される。<br>
	 * デフォルトユーザ追加ロール情報を登録するかを、指定することができる。<br>
	 * @param userDto        ユーザ情報
	 * @param needExtraRoles デフォルトユーザ追加ロール情報要否
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void insert(UserMasterDtoInterface userDto, boolean needExtraRoles) throws MospException;
	
	/**
	 * 新規登録を行う。<br>
	 * パスワードは、初期パスワードが設定される。<br>
	 * @param userDto       ユーザ情報
	 * @param extraRoleDtos ユーザ追加ロール情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void insert(UserMasterDtoInterface userDto, Set<UserExtraRoleDtoInterface> extraRoleDtos) throws MospException;
	
	/**
	 * 新規登録を行う。<br>
	 * @param userDto       ユーザ情報
	 * @param extraRoleDtos ユーザ追加ロール情報群
	 * @param password      パスワード(暗号化済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void insert(UserMasterDtoInterface userDto, Set<UserExtraRoleDtoInterface> extraRoleDtos, String password)
			throws MospException;
	
	/**
	 * 履歴追加を行う。<br>
	 * @param userDto       ユーザ情報
	 * @param extraRoleDtos ユーザ追加ロール情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void add(UserMasterDtoInterface userDto, Set<UserExtraRoleDtoInterface> extraRoleDtos) throws MospException;
	
	/**
	 * 履歴更新を行う。<br>
	 * @param userDto       ユーザ情報
	 * @param extraRoleDtos ユーザ追加ロール情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void update(UserMasterDtoInterface userDto, Set<UserExtraRoleDtoInterface> extraRoleDtos) throws MospException;
	
	/**
	 * 一括更新処理(有効/無効)を行う。<br>
	 * @param idArray        対象レコード識別ID配列
	 * @param activateDate   有効日
	 * @param inactivateFlag 無効フラグ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void update(long[] idArray, Date activateDate, int inactivateFlag) throws MospException;
	
	/**
	 * 一括更新処理(ロール)を行う。<br>
	 * @param idArray      対象レコード識別ID配列
	 * @param activateDate 有効日
	 * @param roleCode     ロールコード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void update(long[] idArray, Date activateDate, String roleCode) throws MospException;
	
	/**
	 * 論理削除(履歴)を行う。<br>
	 * レコード識別IDで取得される履歴に対して削除を行う。<br>
	 * @param idArray 対象レコード識別ID配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void delete(long[] idArray) throws MospException;
	
	/**
	 * 論理削除(履歴)を行う。<br>
	 * 個人IDで取得される履歴に対して削除を行う。<br>
	 * @param personalId 個人ID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void delete(String personalId) throws MospException;
	
	/**
	 * ユーザ情報群を登録する。<br>
	 * @param userDtos ユーザ情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist(Collection<UserMasterDtoInterface> userDtos) throws MospException;
	
	/**
	 * パスワードの初期化を行う。<br>
	 * @param idArray ユーザ情報レコード識別ID配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void initPassword(long[] idArray) throws MospException;
	
	/**
	 * パスワードの変更を行う。<br>
	 * @param userId   ユーザID
	 * @param password パスワード(暗号化済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void updatePassword(String userId, String password) throws MospException;
	
	/**
	 * ユーザ情報の妥当性を確認する。<br>
	 * @param userDto ユーザ情報
	 * @param row 行インデックス
	 * @throws MospException SQLの作成に失敗した場合或いはSQL例外が発生した場合
	 */
	void validate(UserMasterDtoInterface userDto, Integer row) throws MospException;
	
	/**
	 * ユーザアカウント情報登録後の確認を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkAfterRegist() throws MospException;
	
}
