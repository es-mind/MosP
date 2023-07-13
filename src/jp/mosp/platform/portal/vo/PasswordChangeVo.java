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
package jp.mosp.platform.portal.vo;

import java.util.List;

import jp.mosp.framework.js.DirectJs;
import jp.mosp.platform.base.PlatformVo;

/**
 * パスワード変更画面の情報を格納する。
 */
public class PasswordChangeVo extends PlatformVo {
	
	private static final long	serialVersionUID	= 6225791835087433850L;
	
	/**
	 * 現在のパスワード。
	 */
	private String				hdnOldPassword;
	
	/**
	 * 新しいパスワード。
	 */
	private String				hdnNewPassword;
	
	/**
	 * パスワード入力確認。
	 */
	private String				hdnConfirmPassword;
	
	/**
	 * 強制変更フラグ。
	 */
	private boolean				isForced;
	
	/**
	 * パスワード最低文字数。<br>
	 */
	@DirectJs
	private String				minPassword;
	
	/**
	 * パスワード文字種。<br>
	 */
	@DirectJs
	private String				charPassword;
	
	/**
	 * 注意書きリスト。
	 */
	private List<String>		attentionList;
	
	
	/**
	 * {@link PlatformVo#PlatformVo()}を実行する。
	 */
	public PasswordChangeVo() {
		super();
	}
	
	/**
	 * @return hdnOldPassword
	 */
	public String getHdnOldPassword() {
		return hdnOldPassword;
	}
	
	/**
	 * @param hdnOldPassword セットする hdnOldPassword
	 */
	public void setHdnOldPassword(String hdnOldPassword) {
		this.hdnOldPassword = hdnOldPassword;
	}
	
	/**
	 * @return hdnNewPassword
	 */
	public String getHdnNewPassword() {
		return hdnNewPassword;
	}
	
	/**
	 * @param hdnNewPassword セットする hdnNewPassword
	 */
	public void setHdnNewPassword(String hdnNewPassword) {
		this.hdnNewPassword = hdnNewPassword;
	}
	
	/**
	 * @return hdnConfirmPassword
	 */
	public String getHdnConfirmPassword() {
		return hdnConfirmPassword;
	}
	
	/**
	 * @param hdnConfirmPassword セットする hdnConfirmPassword
	 */
	public void setHdnConfirmPassword(String hdnConfirmPassword) {
		this.hdnConfirmPassword = hdnConfirmPassword;
	}
	
	/**
	 * @return isForced
	 */
	public boolean isForced() {
		return isForced;
	}
	
	/**
	 * @param isForced セットする isForced
	 */
	public void setForced(boolean isForced) {
		this.isForced = isForced;
	}
	
	/**
	 * @param minPassword セットする minPassword
	 */
	public void setMinPassword(String minPassword) {
		this.minPassword = minPassword;
	}
	
	/**
	 * @param charPassword セットする charPassword
	 */
	public void setCharPassword(String charPassword) {
		this.charPassword = charPassword;
	}
	
	/**
	 * @return attentionList
	 */
	public List<String> getAttentionList() {
		return attentionList;
	}
	
	/**
	 * @param attentionList セットする attentionList
	 */
	public void setAttentionList(List<String> attentionList) {
		this.attentionList = attentionList;
	}
	
}
