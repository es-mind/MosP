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
package jp.mosp.framework.base;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;

/**
 * MosPユーザー情報を扱う。<br>
 */
public class MospUser implements Serializable {
	
	private static final long	serialVersionUID	= 8544895877748481588L;
	
	/**
	 * ASPユーザーID。<br>
	 */
	private String				aspUserId;
	
	/**
	 * JDBCドライバー名。<br>
	 */
	private String				dbDriver;
	
	/**
	 * DBURL。<br>
	 */
	private String				dbUrl;
	
	/**
	 * DBユーザーID。<br>
	 */
	private String				dbUser;
	
	/**
	 * DBパスワード。<br>
	 */
	private String				dbPass;
	
	/**
	 * ユーザID。<br>
	 */
	private String				userId;
	
	/**
	 * ロール。<br>
	 */
	private String				role;
	
	/**
	 * 追加ロール群。<br>
	 * メインのロール{@link MospUser#role}に加えて、追加のロールを設定できる。<br>
	 * <br>
	 * 例えば、メインのロールはプラットフォームのロール、
	 * 追加のロールにパッケージのロールとアドオン独自のロールを設定することで、
	 * ロールの数を抑えながら柔軟に権限を与えることができる。<br>
	 * <br>
	 * 追加ロールを設定した場合、ユーザ単位で見ると、
	 * ロールの次の要素は全てのロールの要素を足し合わせたものになる。<br>
	 * ・roleExtra              ロール追加情報<br>
	 * ・roleMenuMap            ロールメニュー設定情報群<br>
	 * ・acceptCmdList          ロール実行可能コマンドリスト<br>
	 * ・rejectCmdList          ロール実行不能コマンドリスト<br>
	 * ・hiddenDivisionsList    人事汎用管理区分非表示リスト<br>
	 * ・referenceDivisionsList 人事汎用管理区分参照リスト<br>
	 * <br>
	 * ロール実行可能コマンドリストに設定されているコマンドであっても、
	 * 他の追加ロールでロール実行不能コマンドリストに設定されていた場合、
	 * そのユーザは当該コマンドを実行することはできない。<br>
	 * <br>
	 */
	private Set<String>			extraRoles;
	
	/**
	 * 個人ID。<br>
	 */
	private String				personalId;
	
	/**
	 * ユーザ名。<br>
	 */
	private String				userName;
	
	
	/**
	 * コンストラクタ。<br>
	 */
	public MospUser() {
		extraRoles = new HashSet<String>();
	}
	
	/**
	 * ASPユーザーIDを取得する。<br>
	 * @return ASPユーザーID
	 */
	public String getAspUserId() {
		return aspUserId;
	}
	
	/**
	 * ASPユーザーIDを設定する。<br>
	 * @param aspUserId セットするASPユーザーID
	 */
	public void setAspUserId(String aspUserId) {
		this.aspUserId = aspUserId;
	}
	
	/**
	 * ユーザIDを取得する。<br>
	 * @return ユーザID
	 */
	public String getUserId() {
		return userId;
	}
	
	/**
	 * ユーザIDを設定する。<br>
	 * @param userId セットするユーザID
	 */
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * 個人IDを取得する。<br>
	 * @return personalId
	 */
	public String getPersonalId() {
		return personalId;
	}
	
	/**
	 * 個人IDを設定する。<br>
	 * @param personalId セットする個人ID
	 */
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	/**
	 * ユーザ名を取得する。<br>
	 * @return userName
	 */
	public String getUserName() {
		return userName;
	}
	
	/**
	 * ユーザ名を設定する。<br>
	 * @param userName セットするユーザ名
	 */
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	/**
	 * @return dbDriver
	 */
	public String getDbDriver() {
		return dbDriver;
	}
	
	/**
	 * @return dbUrl
	 */
	public String getDbUrl() {
		return dbUrl;
	}
	
	/**
	 * @return dbUser
	 */
	public String getDbUser() {
		return dbUser;
	}
	
	/**
	 * @return dbPass
	 */
	public String getDbPass() {
		return dbPass;
	}
	
	/**
	 * @param dbDriver セットする dbDriver
	 */
	public void setDbDriver(String dbDriver) {
		this.dbDriver = dbDriver;
	}
	
	/**
	 * @param dbUrl セットする dbUrl
	 */
	public void setDbUrl(String dbUrl) {
		this.dbUrl = dbUrl;
	}
	
	/**
	 * @param dbUser セットする dbUser
	 */
	public void setDbUser(String dbUser) {
		this.dbUser = dbUser;
	}
	
	/**
	 * @param dbPass セットする dbPass
	 */
	public void setDbPass(String dbPass) {
		this.dbPass = dbPass;
	}
	
	/**
	 * @return role
	 */
	public String getRole() {
		return role;
	}
	
	/**
	 * @param role セットする role
	 */
	public void setRole(String role) {
		this.role = role;
	}
	
	/**
	 * @return extraRoles
	 */
	public Set<String> getExtraRoles() {
		return extraRoles;
	}
	
	/**
	 * @param extraRoles セットする extraRoles
	 */
	public void setExtraRoles(Set<String> extraRoles) {
		this.extraRoles = extraRoles;
	}
	
}
