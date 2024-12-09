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
import javax.naming.NamingEnumeration;
import javax.naming.directory.DirContext;
import javax.naming.directory.InitialDirContext;
import javax.naming.directory.SearchControls;
import javax.naming.directory.SearchResult;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.utils.LogUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.portal.AuthBeanInterface;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.bean.portal.impl.AuthBean;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 認証クラス。<br>
 * <br>
 * LDAP(ActiveDirectory)を用いて認証処理を行う。<br>
 * LDAPでユーザが見つからなかった場合は、MosPで認証処理を行う。<br>
 */
public class ActiveDirectoryAuthBean extends PlatformBean implements AuthBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(LDAPプロバイダURL)。
	 */
	public static final String		APP_LDAP_PROVIDER_URL	= "LdapProviderUrl";
	
	/**
	 * MosPアプリケーション設定キー(LDAPディレクトリ名)。
	 */
	public static final String		APP_LDAP_DIR_NAME		= "LdapDirName";
	
	/**
	 * MosPアプリケーション設定キー(LDAPバインドDN)。
	 */
	public static final String		APP_LDAP_BIND_DN		= "LdapBindDN";
	
	/**
	 * MosPアプリケーション設定キー(LDAP検索用ユーザID)。
	 */
	public static final String		APP_LDAP_SEARCH_USER	= "LdapSearchUser";
	
	/**
	 * MosPアプリケーション設定キー(LDAP検索用パスワード)。
	 */
	public static final String		APP_LDAP_SEARCH_PASS	= "LdapSearchPass";
	
	/**
	 * LDAPバインドDN置換文字。
	 */
	protected static final String	REPLACE_CHARACTER		= "%";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ActiveDirectoryAuthBean() {
		super();
	}
	
	@Override
	public void initBean() {
		// 処理無し
	}
	
	@Override
	public void authenticate(String userId, String password) throws MospException {
		// LDAPユーザ存在確認
		boolean isUserExist = false;
		try {
			isUserExist = isUserExist(userId);
		} catch (MospException e) {
			// エラーメッセージ追加(ActiveDirectoryへの接続及び問合せに失敗した場合)
			mospParams.addErrorMessage(ExceptionConst.EX_FAIL_DB_CONNECT);
			return;
		}
		// LDAPユーザ存在確認
		if (isUserExist) {
			// LDAPによるパスワード妥当性確認
			if (isPasswordValid(userId, password) == false) {
				// エラーメッセージを設定(LDAPによる認証失敗)
				PfMessageUtility.addErrorAuthFailed(mospParams);
			}
			return;
		}
		// MosP認証クラス生成(MosP標準の認証処理)
		AuthBean mospAuth = (AuthBean)createBean(AuthBean.class.getCanonicalName());
		// パスワード確認処理を生成
		PasswordCheckBeanInterface check = (PasswordCheckBeanInterface)createBean(PasswordCheckBeanInterface.class);
		// MosPによる認証(ActiveDirectoryにユーザが存在しない場合)
		mospAuth.authenticate(userId, check.encrypt(password));
	}
	
	/**
	 * ユーザの存在確認を行う。<br>
	 * <br>
	 * ActiveDirectoryではAnonymous(匿名)接続がデフォルトで許可されていないため、
	 * まず特定のユーザでActiveDirectoryにアクセスする。<br>
	 * <br>
	 * @param userId ユーザID
	 * @return 確認結果(true：存在する、false：存在しない)
	 * @throws MospException ActiveDirectoryへの接続及び問合せに失敗した場合
	 */
	protected boolean isUserExist(String userId) throws MospException {
		// ディレクトリサービスインターフェース宣言
		DirContext dirContext = null;
		// MosP設定情報取得
		String ldapProviderUrl = mospParams.getApplicationProperty(APP_LDAP_PROVIDER_URL);
		String ldapDirName = mospParams.getApplicationProperty(APP_LDAP_DIR_NAME);
		String ldapBindDN = mospParams.getApplicationProperty(APP_LDAP_BIND_DN);
		String ldapSearchUser = mospParams.getApplicationProperty(APP_LDAP_SEARCH_USER);
		String ldapSearchPass = mospParams.getApplicationProperty(APP_LDAP_SEARCH_PASS);
		// 認証情報取得
		ldapBindDN = ldapBindDN.replaceAll(REPLACE_CHARACTER, ldapSearchUser);
		// 接続情報準備
		Properties env = new Properties();
		env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapProviderUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(DirContext.SECURITY_PRINCIPAL, ldapBindDN);
		env.put(DirContext.SECURITY_CREDENTIALS, ldapSearchPass);
		try {
			// バインド認証接続
			dirContext = new InitialDirContext(env);
			// 検索準備
			SearchControls ctrl = new SearchControls();
			ctrl.setSearchScope(SearchControls.SUBTREE_SCOPE);
			String filter = "sAMAccountName=" + userId;
			// ユーザIDで検索
			NamingEnumeration<SearchResult> result = dirContext.search(ldapDirName, filter, ctrl);
			// 検索結果確認
			if (result.hasMoreElements()) {
				return true;
			}
			return false;
		} catch (Throwable t) {
			// エラーログ出力
			LogUtility.error(mospParams, t);
			throw new MospException(t);
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
	 * @return 確認結果(true：妥当である、false：妥当でない)
	 */
	protected boolean isPasswordValid(String userId, String password) {
		// ディレクトリサービスインターフェース宣言
		DirContext dirContext = null;
		// MosP設定情報取得
		String ldapProviderUrl = mospParams.getApplicationProperty(APP_LDAP_PROVIDER_URL);
		String ldapBindDN = mospParams.getApplicationProperty(APP_LDAP_BIND_DN);
		// 認証情報取得
		ldapBindDN = ldapBindDN.replaceAll(REPLACE_CHARACTER, userId);
		// 接続情報準備
		Properties env = new Properties();
		env.setProperty(Context.INITIAL_CONTEXT_FACTORY, "com.sun.jndi.ldap.LdapCtxFactory");
		env.put(Context.PROVIDER_URL, ldapProviderUrl);
		env.put(Context.SECURITY_AUTHENTICATION, "simple");
		env.put(DirContext.SECURITY_PRINCIPAL, ldapBindDN);
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
