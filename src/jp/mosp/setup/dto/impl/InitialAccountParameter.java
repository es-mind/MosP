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

import java.io.Serializable;
import java.util.Date;

import jp.mosp.framework.utils.CapsuleUtility;
import jp.mosp.setup.dto.InitialAccountParameterInterface;

/**
 * 初期アカウント登録パラメータクラス。
 */
public class InitialAccountParameter implements Serializable, InitialAccountParameterInterface {
	
	private static final long	serialVersionUID	= 4452984768286447824L;
	
	private String				userId;
	
	private String				employeeCode;
	
	private String				lastName;
	
	private String				firstName;
	
	private String				lastKana;
	
	private String				firstKana;
	
	private Date				entranceDate;
	
	private Date				activateDate;
	
	private String				roleCode;
	
	
	@Override
	public String getUserId() {
		return userId;
	}
	
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public String getLastName() {
		return lastName;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public String getLastKana() {
		return lastKana;
	}
	
	@Override
	public String getFirstKana() {
		return firstKana;
	}
	
	@Override
	public Date getEntranceDate() {
		return CapsuleUtility.getDateClone(entranceDate);
	}
	
	@Override
	public Date getActivateDate() {
		return CapsuleUtility.getDateClone(activateDate);
	}
	
	@Override
	public String getRoleCode() {
		return roleCode;
	}
	
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public void setLastKana(String lastKana) {
		this.lastKana = lastKana;
	}
	
	@Override
	public void setFirstKana(String firstKana) {
		this.firstKana = firstKana;
	}
	
	@Override
	public void setEntranceDate(Date entranceDate) {
		this.entranceDate = CapsuleUtility.getDateClone(entranceDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = CapsuleUtility.getDateClone(activateDate);
	}
	
	@Override
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}
