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
package jp.mosp.platform.dto.system.impl;

import java.util.Date;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.AccountInfoDtoInterface;

/**
 * アカウントマスタDTO。<br>
 */
public class AccountInfoDto extends BaseDto implements AccountInfoDtoInterface {
	
	private static final long	serialVersionUID	= -8343465718435583254L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmUserId;
	
	/**
	 * ユーザID。
	 */
	private String				userId;
	
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 個人ID。
	 */
	private String				personalId;
	
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	/**
	 * 社員コード。
	 */
	private String				employeeCode;
	
	/**
	 * 姓。
	 */
	private String				lastName;
	
	/**
	 * 名。
	 */
	private String				firstName;
	
	/**
	 * 	ロールコード。
	 */
	private String				roleCode;
	
	/**
	 * ユーザ追加ロールコード群(キー：ロール区分、値：ロールコード)。<br>
	 */
	private Map<String, String>	extraRoles;
	
	/**
	 * 	ロール名称。
	 */
	private String				roleName;
	
	
	/**
	 * コンストラクタ。
	 */
	public AccountInfoDto() {
		// 処理無し
	}
	
	/**
	 * @return pfmUserId
	 */
	@Override
	public long getPfmUserId() {
		return pfmUserId;
	}
	
	/**
	 * @param pfmUserId セットする pfmUserId
	 */
	@Override
	public void setPfmUserId(long pfmUserId) {
		this.pfmUserId = pfmUserId;
	}
	
	/**
	 * @return userId
	 */
	@Override
	public String getUserId() {
		return userId;
	}
	
	/**
	 * @param userId セットする userId
	 */
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	/**
	 * @return activateDate
	 */
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	/**
	 * @param activateDate セットする activateDate
	 */
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	/**
	 * @return personalId
	 */
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	/**
	 * @param personalId セットする personalId
	 */
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	/**
	 * @return inactivateFlag
	 */
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	/**
	 * @param inactivateFlag セットする inactivateFlag
	 */
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	/**
	 * @return employeeCode
	 */
	@Override
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	/**
	 * @param employeeCode セットする employeeCode
	 */
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	/**
	 * @return lastName
	 */
	@Override
	public String getLastName() {
		return lastName;
	}
	
	/**
	 * @param lastName セットする lastName
	 */
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	/**
	 * @return firstName
	 */
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	/**
	 * @param firstName セットする firstName
	 */
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	/**
	 * @return roleCode
	 */
	@Override
	public String getRoleCode() {
		return roleCode;
	}
	
	/**
	 * @param roleCode セットする roleCode
	 */
	@Override
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
	@Override
	public Map<String, String> getExtraRoles() {
		return extraRoles;
	}
	
	@Override
	public void setExtraRoles(Map<String, String> extraRoles) {
		this.extraRoles = extraRoles;
	}
	
	/**
	 * @return roleName
	 */
	@Override
	public String getRoleName() {
		return roleName;
	}
	
	/**
	 * @param roleName セットする roleName
	 */
	@Override
	public void setRoleName(String roleName) {
		this.roleName = roleName;
	}
	
}
