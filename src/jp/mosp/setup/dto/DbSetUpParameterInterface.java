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
package jp.mosp.setup.dto;

import java.io.Serializable;
import java.util.Date;

import jp.mosp.setup.constant.Command;

/**
 * データベースセットアップパラメータインターフェース。
 */
public interface DbSetUpParameterInterface extends Serializable {
	
	/**
	 * @return userId
	 */
	public String getUserId();
	
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(String userId);
	
	/**
	 * @return employeeCode
	 */
	public String getEmployeeCode();
	
	/**
	 * @param employeeCode セットする employeeCode
	 */
	public void setEmployeeCode(String employeeCode);
	
	/**
	 * @return lastName
	 */
	public String getLastName();
	
	/**
	 * @param lastName セットする lastName
	 */
	public void setLastName(String lastName);
	
	/**
	 * @return firstName
	 */
	public String getFirstName();
	
	/**
	 * @param firstName セットする firstName
	 */
	public void setFirstName(String firstName);
	
	/**
	 * @return lastKana
	 */
	public String getLastKana();
	
	/**
	 * @param lastKana セットする lastKana
	 */
	public void setLastKana(String lastKana);
	
	/**
	 * @return firstKana
	 */
	public String getFirstKana();
	
	/**
	 * @param firstKana セットする firstKana
	 */
	public void setFirstKana(String firstKana);
	
	/**
	 * @return entranceDate
	 */
	public Date getEntranceDate();
	
	/**
	 * @param entranceDate セットする entranceDate
	 */
	public void setEntranceDate(Date entranceDate);
	
	/**
	 * @return activateDate
	 */
	public Date getActivateDate();
	
	/**
	 * @param activateDate セットする activateDate
	 */
	public void setActivateDate(Date activateDate);
	
	/**
	 * @return roleCode
	 */
	public String getRoleCode();
	
	/**
	 * @param roleCode セットする roleCode
	 */
	public void setRoleCode(String roleCode);
	
	/**
	 * @return serverName
	 */
	public String getServerName();
	
	/**
	 * @param serverName セットする serverName
	 */
	public void setServerName(String serverName);
	
	/**
	 * @return port
	 */
	public int getPort();
	
	/**
	 * @param port セットする port
	 */
	public void setPort(int port);
	
	/**
	 * @return postgresDb
	 */
	public String getPostgresDb();
	
	/**
	 * @return superUser
	 */
	public String getSuperUser();
	
	/**
	 * @param superUser セットする superUser
	 */
	public void setSuperUser(String superUser);
	
	/**
	 * @return superPassword
	 */
	public String getSuperPassword();
	
	/**
	 * @param superPassword セットする superPassword
	 */
	public void setSuperPassword(String superPassword);
	
	/**
	 * @return defaultDbUser
	 */
	public String getDefaultDbUser();
	
	/**
	 * @return dirs
	 */
	public String[] getDirs();
	
	/**
	 * @param dirs セットする dirs
	 */
	public void setDirs(String[] dirs);
	
	/**
	 * @return command
	 */
	public Command getCommand();
	
	/**
	 * @param command セットする command
	 */
	public void setCommand(Command command);
	
	/**
	 * @return dbName
	 */
	public String getDbName();
	
	/**
	 * @param dbName セットする dbName
	 */
	public void setDbName(String dbName);
	
	/**
	 * @return userName
	 */
	public String getUserName();
	
	/**
	 * @param userName セットする userName
	 */
	public void setUserName(String userName);
	
	/**
	 * @return userPassword
	 */
	public String getUserPassword();
	
	/**
	 * @param userPassword セットする userPassword
	 */
	public void setUserPassword(String userPassword);
	
}
