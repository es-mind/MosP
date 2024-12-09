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
 * 
 * DB接続確認画面の情報を格納する。
 * 
 */
public class DbConfirmVo extends PlatformVo {
	
	private static final long	serialVersionUID	= -4533229239002308246L;
	
	/**
	 * サーバ名。<br>
	 */
	private String				txtServer;
	
	/**
	 * ポート番号。<br>
	 */
	private String				txtPort;
	
	/**
	 * postgresパスワード。<br>
	 */
	private String				txtPostgresPass;
	
	
	/**
	 * @return txtServer<br>
	 */
	public String getTxtServer() {
		return txtServer;
	}
	
	/**
	 * @param txtServer セットする txtServer<br>
	 */
	public void setTxtServer(String txtServer) {
		this.txtServer = txtServer;
	}
	
	/**
	 * @return txtPort<br>
	 */
	public String getTxtPort() {
		return txtPort;
	}
	
	/**
	 * @param txtPort セットする txtPort<br>
	 */
	public void setTxtPort(String txtPort) {
		this.txtPort = txtPort;
	}
	
	/**
	 * @return txtPostgresPass<br>
	 */
	public String getTxtPostgresPass() {
		return txtPostgresPass;
	}
	
	/**
	 * @param txtPostgresPass セットする txtPostgresPass<br>
	 */
	public void setTxtPostgresPass(String txtPostgresPass) {
		this.txtPostgresPass = txtPostgresPass;
	}
	
}
