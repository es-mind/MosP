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
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * パスワード確認処理インターフェース。
 */
public interface PasswordCheckBeanInterface extends BaseBeanInterface {
	
	/**
	 * パスワードを暗号化する。<br>
	 * クライアントで暗号化されたものがサーバ側で更に暗号化される。<br>
	 * @param pass パスワード(クライアント暗号化済)
	 * @return パスワード(暗号化済)
	 * @throws MospException 暗号化に失敗した場合
	 */
	String encrypt(String pass) throws MospException;
	
	/**
	 * パスワード(平文)を暗号化する。<br>
	 * @param pass パスワード(平文)
	 * @return パスワード(暗号化済)
	 * @throws MospException 暗号化に失敗した場合
	 */
	String encryptPlain(String pass) throws MospException;
	
	/**
	 * パスワード有効期間の確認を行う。<br>
	 * @param userId     ユーザID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkPasswordPeriod(String userId, Date targetDate) throws MospException;
	
	/**
	 * パスワード堅牢性の確認を行う。<br>
	 * @param userId ユーザID
	 * @param pass   パスワード(暗号化済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkPasswordStrength(String userId, String pass) throws MospException;
	
	/**
	 * パスワード堅牢性の確認を行う。<br>
	 * DBから現在のパスワードを取得して確認する。<br>
	 * @param userId ユーザID
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkPasswordStrength(String userId) throws MospException;
	
	/**
	 * パスワード変更可否の確認を行う。<br>
	 * @param userId      ユーザID
	 * @param oldPass     旧パスワード(暗号化済)
	 * @param newPass     新パスワード(暗号化済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void checkPasswordChange(String userId, String oldPass, String newPass) throws MospException;
	
	/**
	 * パスワード変更可否の確認を行う。<br>
	 * @param newPass     新パスワード(暗号化済)
	 * @param confirmPass 確認パスワード(暗号化済)
	 */
	void checkPasswordChange(String newPass, String confirmPass);
	
	/**
	 * 初期パスワード(暗号化済)を取得する。<br>
	 * @param userId ユーザID
	 * @return 初期パスワード(暗号化済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String getEncryptedInitialPassword(String userId) throws MospException;
	
	/**
	 * パスワード最低文字数を取得する。<br>
	 * @return パスワード最低文字数
	 */
	String getMinPassword();
	
	/**
	 * パスワード文字種を取得する。<br>
	 * @return パスワード文字種
	 */
	String getCharPassword();
	
	/**
	 * パスワード設定注意書きリストを取得する。<br>
	 * @return パスワード設定注意書きリスト
	 */
	List<String> getAttentionList();
	
}
