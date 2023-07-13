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
package jp.mosp.setup.vo;

import jp.mosp.platform.base.PlatformVo;

/**
 * 
 * DB作成画面の情報を格納する。<br>
 * 
 */
public class DbCreateVo extends PlatformVo {
	
	private static final long	serialVersionUID	= -124436459537218618L;
	
	/**
	 * データベース名。<br>
	 */
	private String				txtDbName;
	
	/**
	 * ロール名。<br>
	 */
	private String				txtRoleName;
	
	/**
	 * ロールパスワード。<br>
	 */
	private String				txtRolePw;
	
	
	/**
	 * @return txtDbName<br>
	 */
	public String getTxtDbName() {
		return txtDbName;
	}
	
	/**
	 * @param txtDbName セットする txtDbName<br>
	 */
	public void setTxtDbName(String txtDbName) {
		this.txtDbName = txtDbName;
	}
	
	/**
	 * @return txtRoleName<br>
	 */
	public String getTxtRoleName() {
		return txtRoleName;
	}
	
	/**
	 * @param txtRoleName セットする txtRoleName<br>
	 */
	public void setTxtRoleName(String txtRoleName) {
		this.txtRoleName = txtRoleName;
	}
	
	/**
	 * @return txtRolePw<br>
	 */
	public String getTxtRolePw() {
		return txtRolePw;
	}
	
	/**
	 * @param txtRolePw セットする txtRolePw<br>
	 */
	public void setTxtRolePw(String txtRolePw) {
		this.txtRolePw = txtRolePw;
	}
	
}
