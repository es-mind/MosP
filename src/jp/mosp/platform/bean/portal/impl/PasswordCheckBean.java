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
package jp.mosp.platform.bean.portal.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.property.MospProperties;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.SeUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.dao.system.UserPasswordDaoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * パスワード確認処理。<br>
 * <br>
 * 認証時、パスワード変更時等で、用いる。<br>
 */
public class PasswordCheckBean extends PlatformBean implements PasswordCheckBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(パスワード有効期間(日))。<br>
	 */
	protected static final String			APP_PASSWORD_PERIOD		= "PasswordPeriod";
	
	/**
	 * MosPアプリケーション設定キー(パスワード最低文字数)。<br>
	 */
	protected static final String			APP_MIN_PASSWORD		= "MinPassword";
	
	/**
	 * MosPアプリケーション設定キー(パスワード文字種)。<br>
	 */
	protected static final String			APP_CHAR_PASSWORD		= "CharPassword";
	
	/**
	 * MosPアプリケーション設定キー(パスワード確認)。<br>
	 */
	protected static final String			APP_CHECK_PASSWORD		= "CheckPassword";
	
	/**
	 * MosPアプリケーション設定キー(初期パスワード)。<br>
	 */
	protected static final String			APP_INITIAL_PASSWORD	= "InitialPassword";
	
	/**
	 * 初期パスワード設定(ユーザID)。<br>
	 */
	protected static final String			INITIAL_PASS_USER_ID	= "UserId";
	
	/**
	 * パスワード確認設定(初期パスワード不可)。<br>
	 */
	protected static final String			PASS_CHECK_INIT_INVALID	= "initPasswordInvalid";
	
	/**
	 * ユーザパスワード情報DAO。<br>
	 */
	protected UserPasswordDaoInterface		dao;
	
	/**
	 * プラットフォームマスタ参照処理。<br>
	 */
	protected PlatformMasterBeanInterface	master;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PasswordCheckBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(UserPasswordDaoInterface.class);
		// Beanを準備
		master = createBeanInstance(PlatformMasterBeanInterface.class);
	}
	
	@Override
	public String encrypt(String pass) throws MospException {
		return SeUtility.encrypt(pass);
	}
	
	@Override
	public String encryptPlain(String pass) throws MospException {
		return encrypt(SeUtility.encrypt(pass));
	}
	
	@Override
	public void checkPasswordPeriod(String userId, Date targetDate) throws MospException {
		// パスワード有効期間を取得
		int expiration = getPasswordExpiration();
		// 基準日を取得
		Date referenceDate = getReferenceDate(userId);
		// 対象日が有効期限を超えている場合
		if (DateUtility.isExpireDay(targetDate, referenceDate, expiration)) {
			// メッセージ追加
			PfMessageUtility.addErrorPasswordExpire(mospParams);
		}
	}
	
	@Override
	public void checkPasswordStrength(String userId, String pass) throws MospException {
		// パスワード確認プロパティ値群を取得
		Set<String> passwordChecks = getPasswordChecks();
		// パスワード確認プロパティ値群に初期パスワード可否が含まれる場合
		if (passwordChecks.contains(PASS_CHECK_INIT_INVALID)) {
			// 初期パスワード不可確認
			checkInitInvalid(userId, pass);
		}
	}
	
	@Override
	public void checkPasswordStrength(String userId) throws MospException {
		// 現在のパスワード(暗号化済)を取得
		String currentPassword = getCurrentPassword(userId);
		// パスワード堅牢性の確認
		checkPasswordStrength(userId, currentPassword);
	}
	
	@Override
	public void checkPasswordChange(String userId, String oldPass, String newPass) throws MospException {
		// 現在のパスワードと新しいパスワード比較
		if (oldPass.equals(newPass)) {
			// パスワードが変更されていなければメッセージ追加
			PfMessageUtility.addErrorPasswordUnchanged(mospParams);
			return;
		}
		// 現在のパスワードを取得
		String currentPassword = getCurrentPassword(userId);
		// 現在のパスワードを確認
		if (currentPassword.equals(oldPass) == false) {
			// 現在のパスワードが異なっていればメッセージ追加
			PfMessageUtility.addErrorOldPassword(mospParams);
			return;
		}
	}
	
	@Override
	public void checkPasswordChange(String newPass, String confirmPass) {
		// 新しいパスワードとパスワード入力確認が異なる場合
		if (confirmPass.equals(newPass) == false) {
			// メッセージを追加
			PfMessageUtility.addErrorNewPassword(mospParams);
		}
	}
	
	@Override
	public String getEncryptedInitialPassword(String userId) throws MospException {
		// 初期パスワードを取得
		String initinalPassword = getInitialPassword(userId);
		// 初期パスワード(暗号化済)を取得
		return encryptPlain(initinalPassword);
	}
	
	@Override
	public String getMinPassword() {
		return mospParams.getApplicationProperty(APP_MIN_PASSWORD);
	}
	
	@Override
	public String getCharPassword() {
		return mospParams.getApplicationProperty(APP_CHAR_PASSWORD);
	}
	
	@Override
	public List<String> getAttentionList() {
		// 注意書きリストを準備
		List<String> attentionList = new ArrayList<String>();
		// パスワード確認プロパティ値群を取得
		Set<String> passwordChecks = getPasswordChecks();
		// パスワード確認プロパティ値群に初期パスワード可否が含まれる場合
		if (passwordChecks.contains(PASS_CHECK_INIT_INVALID)) {
			// 注意書き設定
			attentionList.add(PfMessageUtility.getWarningPasswordInit(mospParams));
		}
		// パスワード最低文字数取得
		String minPassword = getMinPassword();
		// パスワード最低文字数が設定されている場合
		if (MospUtility.isEmpty(minPassword) == false) {
			// 注意書き設定
			attentionList.add(PfMessageUtility.getWarningPasswordMin(mospParams, minPassword));
		}
		// パスワード文字種を取得
		String charPassword = getCharPassword();
		// パスワード文字種が設定されている場合
		if (MospUtility.isEmpty(charPassword) == false) {
			// 注意書き設定
			attentionList.add(PfMessageUtility.getWarningPasswordChar(mospParams));
		}
		// 注意書きリストを取得
		return attentionList;
	}
	
	/**
	 * 初期パスワード不可確認を行う。<br>
	 * @param userId ユーザID
	 * @param pass   パスワード
	 * @throws MospException SQL実行或いは暗号化に失敗した場合
	 */
	protected void checkInitInvalid(String userId, String pass) throws MospException {
		// 初期パスワード(暗号化済)を取得
		String initialPassword = getEncryptedInitialPassword(userId);
		// ユーザパスワード情報と初期パスワードが同じである場合
		if (initialPassword.equals(pass)) {
			// メッセージ追加
			PfMessageUtility.addErrorPasswordInit(mospParams);
		}
	}
	
	/**
	 * 現在のパスワード(暗号化済)を取得する。<br>
	 * @param userId ユーザID
	 * @return 現在のパスワード(暗号化済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getCurrentPassword(String userId) throws MospException {
		// ユーザパスワード情報の取得
		UserPasswordDtoInterface dto = dao.findForInfo(userId);
		// ユーザパスワード情報が取得できなかった場合
		if (dto == null) {
			return "";
		}
		// 現在のパスワード(暗号化済)を取得
		return dto.getPassword();
	}
	
	/**
	 * パスワード有効期限基準日時を取得する。<br>
	 * @param userId ユーザID
	 * @return パスワード有効期限基準日時
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Date getReferenceDate(String userId) throws MospException {
		// ユーザパスワード情報の取得
		UserPasswordDtoInterface dto = dao.findForInfo(userId);
		// ユーザパスワード情報が取得できなかった場合
		if (dto == null) {
			// システム日付を取得
			return getSystemDate();
		}
		// 変更日を取得
		return dto.getChangeDate();
	}
	
	/**
	 * パスワード有効期間を取得する。<br>
	 * @return パスワード有効期間
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected int getPasswordExpiration() throws MospException {
		// パスワード有効期間を取得
		int expiration = master.getAppPropertyInt(APP_PASSWORD_PERIOD);
		// パスワード有効期間が0である場合
		if (expiration == 0) {
			// 約5883517年後に設定
			expiration = Integer.MAX_VALUE;
		}
		// パスワード有効期間を取得
		return expiration;
	}
	
	/**
	 * 初期パスワードを取得する。<br>
	 * @param userId ユーザID
	 * @return 初期パスワード
	 */
	protected String getInitialPassword(String userId) {
		// アプリケーション設定取得
		String initialPassword = mospParams.getApplicationProperty(APP_INITIAL_PASSWORD);
		// 初期パスワード設定を確認
		if (MospUtility.isEmpty(initialPassword) || initialPassword.equals(INITIAL_PASS_USER_ID)) {
			// ユーザIDを取得
			return userId;
		}
		// 初期パスワード設定を取得
		return initialPassword;
	}
	
	/**
	 * パスワード確認プロパティ値群を取得する。<br>
	 * {@link MospProperties#getApplicationProperty(String)}から取得する。<br>
	 * @return パスワード確認プロパティ値群
	 */
	protected Set<String> getPasswordChecks() {
		// パスワード確認プロパティ値群を取得
		return new LinkedHashSet<String>(MospUtility.asList(mospParams.getApplicationProperties(APP_CHECK_PASSWORD)));
	}
	
}
