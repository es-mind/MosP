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
package jp.mosp.platform.dto.system.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.UserExtraRoleDtoInterface;

/**
 * ユーザ追加ロール情報DTOクラス。<br>
 */
public class PfaUserExtraRoleDto extends BaseDto implements UserExtraRoleDtoInterface {
	
	private static final long	serialVersionUID	= -3211214426666897536L;
	
	/**
	 * レコード識別ID。<br>
	 */
	private long				pfaUserExtraRoleId;
	
	/**
	 * ユーザID。<br>
	 */
	private String				userId;
	
	/**
	 * 有効日。<br>
	 */
	private Date				activateDate;
	
	/**
	 * ロール区分。<br>
	 */
	private String				roleType;
	
	/**
	 * ロールコード。<br>
	 */
	private String				roleCode;
	
	
	@Override
	public long getPfaUserExtraRoleId() {
		return pfaUserExtraRoleId;
	}
	
	@Override
	public void setPfaUserExtraRoleId(long pfaUserExtraRoleId) {
		this.pfaUserExtraRoleId = pfaUserExtraRoleId;
	}
	
	@Override
	public String getUserId() {
		return userId;
	}
	
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public String getRoleType() {
		return roleType;
	}
	
	@Override
	public void setRoleType(String roleType) {
		this.roleType = roleType;
	}
	
	@Override
	public String getRoleCode() {
		return roleCode;
	}
	
	@Override
	public void setRoleCode(String roleCode) {
		this.roleCode = roleCode;
	}
	
}
