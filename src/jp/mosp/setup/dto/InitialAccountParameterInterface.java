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
package jp.mosp.setup.dto;

import java.util.Date;

/**
 * 初期アカウント登録パラメータインターフェース。
 */
public interface InitialAccountParameterInterface {
	
	/**
	 * @return userId
	 */
	public String getUserId();
	
	/**
	 * @return employeeCode
	 */
	public String getEmployeeCode();
	
	/**
	 * @return lastName
	 */
	public String getLastName();
	
	/**
	 * @return firstName
	 */
	public String getFirstName();
	
	/**
	 * @return lastKana
	 */
	public String getLastKana();
	
	/**
	 * @return firstKana
	 */
	public String getFirstKana();
	
	/**
	 * @return entranceDate
	 */
	public Date getEntranceDate();
	
	/**
	 * @return activateDate
	 */
	public Date getActivateDate();
	
	/**
	 * @return roleCode
	 */
	public String getRoleCode();
	
	/**
	 * @param userId セットする userId
	 */
	public void setUserId(String userId);
	
	/**
	 * @param employeeCode セットする employeeCode
	 */
	public void setEmployeeCode(String employeeCode);
	
	/**
	 * @param lastName セットする lastName
	 */
	public void setLastName(String lastName);
	
	/**
	 * @param firstName セットする firstName
	 */
	public void setFirstName(String firstName);
	
	/**
	 * @param lastKana セットする lastKana
	 */
	public void setLastKana(String lastKana);
	
	/**
	 * @param firstKana セットする firstKana
	 */
	public void setFirstKana(String firstKana);
	
	/**
	 * @param entranceDate セットする entranceDate
	 */
	public void setEntranceDate(Date entranceDate);
	
	/**
	 * @param activateDate セットする activateDate
	 */
	public void setActivateDate(Date activateDate);
	
	/**
	 * @param roleCode セットする roleCode
	 */
	public void setRoleCode(String roleCode);
	
}
