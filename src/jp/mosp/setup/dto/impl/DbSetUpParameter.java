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
package jp.mosp.setup.dto.impl;

import java.util.Date;

import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.setup.constant.Command;
import jp.mosp.setup.dto.DbSetUpParameterInterface;

/**
 * データベースセットアップパラメータクラス。
 */
public class DbSetUpParameter implements DbSetUpParameterInterface {
	
	private static final long	serialVersionUID	= 7949200534431360799L;
	
	private String				userId;
	
	private String				employeeCode;
	
	private String				lastName;
	
	private String				firstName;
	
	private String				lastKana;
	
	private String				firstKana;
	
	private Date				entranceDate;
	
	private Date				activateDate;
	
	private String				roleCode;
	
	private String				serverName;
	
	private int					port;
	
	private String				postgresDb;
	
	private String				superUser;
	
	private String				superPassword;
	
	private String				defaultDbUser;
	
	private String[]			dirs;
	
	private Command				command;
	
	private String				dbName;
	
	private String				userName;
	
	private String				userPassword;
	
	
	@Override
	public String getUserId() {
		return userId;
	}
	
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public String getLastKana() {
		return lastKana;
	}
	
	@Override
	public void setLastKana(String lastKana) {
		this.lastKana = lastKana;
	}
	
	@Override
	public String getFirstKana() {
		return firstKana;
	}
	
	@Override
	public void setFirstKana(String firstKana) {
		this.firstKana = firstKana;
	}
	
	@Override
	public Date getEntranceDate() {
		return CapsuleUtility.getDateClone(entranceDate);
	}
	
	@Override
	public void setEntranceDate(Date entranceDate) {
		this.entranceDate = CapsuleUtility.getDateClone(entranceDate);
	}
	
	@Override
	public Date getActivateDate() {
		return CapsuleUtility.getDateClone(activateDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = CapsuleUtility.getDateClone(activateDate);
	}
	
	@Override
	public String getRoleCode() {
		return roleCode;
	}
	
	@Override
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	@Override
	public String getServerName() {
		return serverName;
	}
	
	@Override
	public void setServerName(String serverName) {
		this.serverName = serverName;
	}
	
	@Override
	public int getPort() {
		return port;
	}
	
	@Override
	public void setPort(int port) {
		this.port = port;
	}
	
	@Override
	public String getPostgresDb() {
		return postgresDb;
	}
	
	/**
	 * @param postgresDb セットする postgresDb
	 */
	public void setPostgresDb(String postgresDb) {
		this.postgresDb = postgresDb;
	}
	
	@Override
	public String getSuperUser() {
		return superUser;
	}
	
	@Override
	public void setSuperUser(String superUser) {
		this.superUser = superUser;
	}
	
	@Override
	public String getSuperPassword() {
		return superPassword;
	}
	
	@Override
	public void setSuperPassword(String superPassword) {
		this.superPassword = superPassword;
	}
	
	@Override
	public String getDefaultDbUser() {
		return defaultDbUser;
	}
	
	/**
	 * @param defaultDbUser セットする defaultDbUser
	 */
	public void setDefaultDbUser(String defaultDbUser) {
		this.defaultDbUser = defaultDbUser;
	}
	
	@Override
	public String[] getDirs() {
		return CapsuleUtility.getStringArrayClone(dirs);
	}
	
	@Override
	public void setDirs(String[] dirs) {
		this.dirs = CapsuleUtility.getStringArrayClone(dirs);
	}
	
	@Override
	public Command getCommand() {
		return command;
	}
	
	@Override
	public void setCommand(Command command) {
		this.command = command;
	}
	
	@Override
	public String getDbName() {
		return dbName;
	}
	
	@Override
	public void setDbName(String dbName) {
		this.dbName = dbName;
	}
	
	@Override
	public String getUserName() {
		return userName;
	}
	
	@Override
	public void setUserName(String userName) {
		this.userName = userName;
	}
	
	@Override
	public String getUserPassword() {
		return userPassword;
	}
	
	@Override
	public void setUserPassword(String userPassword) {
		this.userPassword = userPassword;
	}
	
}
