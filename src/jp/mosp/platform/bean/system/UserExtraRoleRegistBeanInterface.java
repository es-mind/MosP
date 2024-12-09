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

import java.util.Collection;
import java.util.Date;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.UserExtraRoleDtoInterface;

/**
 * ユーザ追加ロール情報登録処理インターフェース。<br>
 */
public interface UserExtraRoleRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 初期ユーザ追加ロール情報を取得する。<br>
	 * @return 初期ユーザ追加ロール情報
	 */
	UserExtraRoleDtoInterface getInitDto();
	
	/**
	 * 初期ユーザ追加ロール情報群を取得する。<br>
	 * @param number 個数
	 * @return 初期ユーザ追加ロール情報群
	 */
	Set<UserExtraRoleDtoInterface> getInitDtos(int number);
	
	/**
	 * ユーザ追加ロール情報群を登録する。<br>
	 * 同一ユーザID、有効日のレコードがあった場合は、論理削除の上、登録する。<br>
	 * @param dtos ユーザ追加ロール情報群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist(Collection<UserExtraRoleDtoInterface> dtos) throws MospException;
	
	/**
	 * ユーザ追加ロール情報を論理削除する。<br>
	 * @param userId       ユーザID
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void delete(String userId, Date activateDate) throws MospException;
	
	/**
	 * 対象ロール区分のユーザ追加ロール情報を論理削除する。<br>
	 * @param roleType ロール区分
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void deleteForRoleType(String roleType) throws MospException;
	
	/**
	 * ユーザ追加ロール情報をコピー(履歴追加)する。<br>
	 * 対象ユーザIDにつき、有効日(From)のユーザ追加ロール情報を、
	 * 有効日(To)の情報として履歴追加除する。<br>
	 * <br>
	 * アカウントの一括更新時等に用いることを想定している。<br>
	 * <br>
	 * @param userId           ユーザID
	 * @param fromActivateDate 有効日(From)
	 * @param toActivateDate   有効日(To)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void copy(String userId, Date fromActivateDate, Date toActivateDate) throws MospException;
	
	/**
	 * ユーザ追加ロール情報の妥当性を確認する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param dto ユーザ追加ロール情報
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void validate(UserExtraRoleDtoInterface dto, Integer row) throws MospException;
	
	/**
	 * ユーザ追加ロール情報の妥当性を確認する。<br>
	 * @param dto ユーザ追加ロール情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void validate(UserExtraRoleDtoInterface dto) throws MospException;
	
}
