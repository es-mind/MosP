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
package jp.mosp.addon.ldap.bean.impl;

import java.util.Properties;

import javax.naming.Context;
import javax.naming.NameNotFoundException;
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.LogUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.portal.AuthBeanInterface;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 認証クラス。<br>
 * <br>
 * LDAPを用いて認証処理を行う。<br>
 */
public class LdapAuthBean extends PlatformBean implements AuthBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(ユーザID接尾辞)。
	 */
	public static final String		APP_SUFFIX_USER_ID		= "SuffixUserId";
	
	/**
	 * MosPアプリケーション設定キー(LDAPプロバイダURL)。
	 */
	public static final String		APP_LDAP_PROVIDER_URL	= "LdapProviderUrl";
	
	/**
	 * MosPアプリケーション設定キー(LDAPディレクトリ名)。
	 */
	public static final String		APP_LDAP_DIR_NAME		= "LdapDirName";
	
	/**
	 * LDAPディレクトリ名置換文字。
	 */
	protected static final String	REPLACE_CHARACTER		= "%";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public LdapAuthBean() {
		super();
	}
	
	@Override
	public void initBean() {
		// 処理無し
	}
	
	@Override
	public void authenticate(String userId, String password) throws MospException {
		// 接尾辞付きユーザID取得
		String suffixedUserId = userId + mospParams.getApplicationProperty(APP_SUFFIX_USER_ID);
		for (String providerUrl : mospParams.getApplicationProperties(APP_LDAP_PROVIDER_URL)) {
			// ユーザ存在確認・パスワード妥当性確認
			if (isUserExist(suffixedUserId, providerUrl) && isPasswordValid(suffixedUserId, password, providerUrl)) {
				return;
			}
		}
		// エラーメッセージを設定
		PfMessageUtility.addErrorAuthFailed(mospParams);
	}
	
	/**
	 * ユーザの存在確認を行う。<br>
	 * @param userId ユーザID
	 * @param providerUrl プロバイダURL
	 * @return 確認結果(true：存在する、false：存在しない)
	 */
	protected boolean isUserExist(String userId, String providerUrl) {
		// ディレクトリサービスインターフェース宣言
		DirContext dirContext = null;
		// 接続情報準備
		Properties env = new Properties();
		env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, providerUrl);
		try {
			// Anonymous(匿名)接続
			dirContext = new InitialDirContext(env);
			// 検索準備
			SearchControls ctrl = new SearchControls();
			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			// ディレクトリ名作成
			String dirName = mospParams.getApplicationProperty(APP_LDAP_DIR_NAME);
			dirName = dirName.replaceAll(REPLACE_CHARACTER, userId);
			// ユーザIDで検索
			NamingEnumeration<SearchResult> result = dirContext.search(dirName, "objectclass=*", ctrl);
			// 検索結果確認
			if (result.hasMoreElements()) {
				return true;
			}
			return false;
		} catch (NameNotFoundException e) {
			// ユーザが存在しない場合
			return false;
		} catch (Throwable t) {
			// エラーログ出力
			LogUtility.error(mospParams, t);
			return false;
		} finally {
			if (dirContext != null) {
				try {
					dirContext.close();
				} catch (Throwable t) {
					LogUtility.error(mospParams, t);
				}
			}
		}
	}
	
	/**
	 * パスワードの妥当性確認を行う。<br>
	 * @param userId   ユーザID
	 * @param password パスワード
	 * @param providerUrl プロバイダURL
	 * @return 確認結果(true：妥当である、false：妥当でない)
	 */
	protected boolean isPasswordValid(String userId, String password, String providerUrl) {
		// ディレクトリサービスインターフェース宣言
		DirContext dirContext = null;
		// ディレクトリ名作成
		String dirName = mospParams.getApplicationProperty(APP_LDAP_DIR_NAME);
		dirName = dirName.replaceAll(REPLACE_CHARACTER, userId);
		// 接続情報準備
		Properties env = new Properties();
		env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, providerUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(DirContext.SECURITY_PRINCIPAL, dirName);
		env.put(DirContext.SECURITY_CREDENTIALS, password);
		try {
			// バインド認証接続
			dirContext = new InitialDirContext(env);
			return true;
		} catch (Throwable t) {
			// 認証失敗ログ出力
			LogUtility.application(mospParams, getLdapAuthFailedMessage());
			return false;
		} finally {
			if (dirContext != null) {
				try {
					dirContext.close();
				} catch (Throwable t) {
					LogUtility.error(mospParams, t);
				}
			}
		}
	}
	
	/**
	 * LDAP認証失敗時のログメッセージを取得する。<br>
	 * @return LDAP認証失敗時のログメッセージ
	 */
	protected String getLdapAuthFailedMessage() {
		return mospParams.getName("ldapAuthFailed");
	}
}
