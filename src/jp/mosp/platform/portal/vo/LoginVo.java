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
package jp.mosp.platform.portal.vo;

import jp.mosp.platform.base.PlatformVo;

/**
 * ログイン画面の情報を格納する。<br>
 */
public class LoginVo extends PlatformVo {
	
	private static final long	serialVersionUID	= 2332876410987406537L;
	
	/**
	 * ユーザID。<br>
	 */
	private String				txtUserId;
	
	/**
	 * パスワード。<br>
	 */
	private String				txtPassWord;
	
	/**
	 * パスワード変更用ユーザID。
	 */
	private String				txtPassChangeUserId;
	
	/**
	 * メールアドレス。<br>
	 */
	private String				txtMailAddress;
	
	/**
	 * 追加ログインボタンパス。<br>
	 */
	private String				loginExtraButtoun;
	
	/**
	 * パスワード初期化。
	 */
	private boolean				useInitializePassword;
	
	
	/**
	 * @param txtPassWord セットする txtPassWord
	 */
	public void setTxtPassWord(String txtPassWord) {
		this.txtPassWord = txtPassWord;
	}
	
	/**
	 * @return txtPassWord
	 */
	public String getTxtPassWord() {
		return txtPassWord;
	}
	
	/**
	 * @return loginExtraButtoun
	 */
	public String getLoginExtraButtoun() {
		return loginExtraButtoun;
	}
	
	/**
	 * @param txtUserId セットする txtUserId
	 */
	public void setTxtUserId(String txtUserId) {
		this.txtUserId = txtUserId;
	}
	
	/**
	 * @return txtUserId
	 */
	public String getTxtUserId() {
		return txtUserId;
	}
	
	/**
	 * @return txtPassChangeUserId
	 */
	public String getTxtPassChangeUserId() {
		return txtPassChangeUserId;
	}
	
	/**
	 * @param txtPassChangeUserId セットする txtPassChangeUserId
	 */
	public void setTxtPassChangeUserId(String txtPassChangeUserId) {
		this.txtPassChangeUserId = txtPassChangeUserId;
	}
	
	/**
	 * @return txtMailAddress
	 */
	public String getTxtMailAddress() {
		return txtMailAddress;
	}
	
	/**
	 * @param txtMailAddress セットする txtMailAddress
	 */
	public void setTxtMailAddress(String txtMailAddress) {
		this.txtMailAddress = txtMailAddress;
	}
	
	/**
	 * @param loginExtraButtoun セットする loginExtraButtoun
	 */
	public void setLoginExtraButtoun(String loginExtraButtoun) {
		this.loginExtraButtoun = loginExtraButtoun;
	}
	
	/**
	 * @return useInitializePassword
	 */
	public boolean isUseInitializePassword() {
		return useInitializePassword;
	}
	
	/**
	 * @param useInitializePassword セットする useInitializePassword
	 */
	public void setUseInitializePassword(boolean useInitializePassword) {
		this.useInitializePassword = useInitializePassword;
	}
	
}
