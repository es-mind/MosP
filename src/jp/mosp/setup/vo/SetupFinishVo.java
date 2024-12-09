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
package jp.mosp.setup.vo;

import jp.mosp.platform.base.PlatformVo;

/**
 * セットアップ完了画面の情報を格納する。<br>
 */

public class SetupFinishVo extends PlatformVo {
	
	private static final long	serialVersionUID	= 1L;
	
	/**
	 * データベース名。<br>
	 */
	private String				lblDataBase;
	
	/**
	 * ロール名。<br>
	 */
	private String				lblRoleName;
	
	/**
	 * ロールパスワード。<br>
	 */
	private String				lblRolePass;
	
	/**
	 * MosPユーザ名。<br>
	 */
	private String				lblMospUser;
	
	/**
	 * MosPパスワード。<br>
	 */
	private String				lblMospPass;
	
	
	/**
	 * @return lblDataBase
	 */
	public String getLblDataBase() {
		return lblDataBase;
	}
	
	/**
	 * @param lblDataBase セットする lblDataBase
	 */
	public void setLblDataBase(String lblDataBase) {
		this.lblDataBase = lblDataBase;
	}
	
	/**
	 * @return lblRoleName
	 */
	public String getLblRoleName() {
		return lblRoleName;
	}
	
	/**
	 * @param lblRoleName セットする lblRoleName
	 */
	public void setLblRoleName(String lblRoleName) {
		this.lblRoleName = lblRoleName;
	}
	
	/**
	 * @return lblRolePass
	 */
	public String getLblRolePass() {
		return lblRolePass;
	}
	
	/**
	 * @param lblRolePass セットする lblRolePass
	 */
	public void setLblRolePass(String lblRolePass) {
		this.lblRolePass = lblRolePass;
	}
	
	/**
	 * @return lblMospUser
	 */
	public String getLblMospUser() {
		return lblMospUser;
	}
	
	/**
	 * @param lblMospUser セットする lblMospUser
	 */
	public void setLblMospUser(String lblMospUser) {
		this.lblMospUser = lblMospUser;
	}
	
	/**
	 * @return lblMospPass
	 */
	public String getLblMospPass() {
		return lblMospPass;
	}
	
	/**
	 * @param lblMospPass セットする lblMospPass
	 */
	public void setLblMospPass(String lblMospPass) {
		this.lblMospPass = lblMospPass;
	}
	
}
