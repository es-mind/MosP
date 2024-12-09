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

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.portal.AuthBeanInterface;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.dao.system.UserPasswordDaoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 認証クラス。<br>
 * <br>
 * 詳細は、{@link AuthBeanInterface}を参照。
 */
public class AuthBean extends PlatformBean implements AuthBeanInterface {
	
	/**
	 * ユーザパスワード情報DAO。<br>
	 */
	protected UserPasswordDaoInterface		dao;
	
	/**
	 * パスワード確認処理。<br
	 */
	protected PasswordCheckBeanInterface	passwordCheck;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AuthBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(UserPasswordDaoInterface.class);
		// Beanを準備
		passwordCheck = createBeanInstance(PasswordCheckBeanInterface.class);
	}
	
	@Override
	public void authenticate(String userId, String pass) throws MospException {
		// ユーザIDでユーザパスワード情報を取得
		UserPasswordDtoInterface dto = dao.findForInfo(userId);
		// ユーザパスワード情報存在確認
		if (dto == null) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorAuthFailed(mospParams);
			return;
		}
		// パスワードを暗号化
		String encrypted = passwordCheck.encrypt(pass);
		// パスワード妥当性確認
		if (encrypted.equals(dto.getPassword()) == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorAuthFailed(mospParams);
		}
	}
	
}
