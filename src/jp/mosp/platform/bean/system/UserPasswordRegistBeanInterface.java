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
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;

/**
 * ユーザパスワード情報登録インターフェース
 */
public interface UserPasswordRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTO取得
	 * @return 初期DTO
	 */
	UserPasswordDtoInterface getInitDto();
	
	/**
	 * ユーザパスワード情報を論理削除する。<br>
	 * @param userId ユーザID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void delete(String userId) throws MospException;
	
	/**
	 * ユーザパスワード登録処理を行う。<br>
	 * 同一ユーザID、変更日のレコードがあった場合は、論理削除の上、登録する。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist(UserPasswordDtoInterface dto) throws MospException;
	
	/**
	 * ユーザパスワード登録処理を行う。<br>
	 * 同一ユーザID、変更日のレコードがあった場合は、論理削除の上、登録する。<br>
	 * @param userId     ユーザID
	 * @param changeDate 変更日
	 * @param password   パスワード(暗号化済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void regist(String userId, Date changeDate, String password) throws MospException;
	
	/**
	 * パスワードの初期化を行う。<br>
	 * @param userId     ユーザID
	 * @param changeDate 変更日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void initPassword(String userId, Date changeDate) throws MospException;
	
	/**
	 * パスワードの初期化を行う。<br>
	 * 変更日には、システム日付を設定する。<br>
	 * @param userIdList ユーザIDリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void initPassword(Set<String> userIdList) throws MospException;
	
	/**
	 * 登録情報の妥当性を確認確認する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param dto 対象DTO
	 * @param row 行インデックス
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void validate(UserPasswordDtoInterface dto, Integer row) throws MospException;
	
}
