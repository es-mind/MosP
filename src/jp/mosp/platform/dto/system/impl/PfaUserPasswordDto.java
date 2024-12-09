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

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;

/**
 * ユーザパスワード情報DTOクラス。
 */
public class PfaUserPasswordDto extends BaseDto implements UserPasswordDtoInterface {
	
	private static final long	serialVersionUID	= 3364591891064347194L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaUserPasswordId;
	
	/**
	 * ユーザID。
	 */
	private String				userId;
	
	/**
	 * 変更日。
	 */
	private Date				changeDate;
	
	/**
	 * パスワード。
	 */
	private String				password;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaUserPasswordDto() {
		// 処理無し
	}
	
	@Override
	public long getPfaUserPasswordId() {
		return pfaUserPasswordId;
	}
	
	@Override
	public String getUserId() {
		return userId;
	}
	
	@Override
	public Date getChangeDate() {
		return getDateClone(changeDate);
	}
	
	@Override
	public String getPassword() {
		return password;
	}
	
	@Override
	public void setPfaUserPasswordId(long pfaUserPasswordId) {
		this.pfaUserPasswordId = pfaUserPasswordId;
	}
	
	@Override
	public void setUserId(String userId) {
		this.userId = userId;
	}
	
	@Override
	public void setChangeDate(Date changeDate) {
		this.changeDate = getDateClone(changeDate);
	}
	
	@Override
	public void setPassword(String password) {
		this.password = password;
	}
	
}
