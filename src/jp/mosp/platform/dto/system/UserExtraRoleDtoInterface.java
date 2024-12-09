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
package jp.mosp.platform.dto.system;

import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.platform.base.ActivateDtoInterface;

/**
 * ユーザ追加ロール情報DTOインターフェース。<br>
 */
public interface UserExtraRoleDtoInterface extends BaseDtoInterface, ActivateDtoInterface {
	
	/**
	 * @return レコード識別ID
	 */
	long getPfaUserExtraRoleId();
	
	/**
	 * @param pfaUserExtraRoleId レコード識別ID
	 */
	void setPfaUserExtraRoleId(long pfaUserExtraRoleId);
	
	/**
	 * @return ユーザID
	 */
	String getUserId();
	
	/**
	 * @param userId ユーザID
	 */
	void setUserId(String userId);
	
	/**
	 * @return ロール区分
	 */
	String getRoleType();
	
	/**
	 * @param roleType ロール区分
	 */
	void setRoleType(String roleType);
	
	/**
	 * @return ロールコード
	 */
	String getRoleCode();
	
	/**
	 * @param roleCode ロールコード
	 */
	void setRoleCode(String roleCode);
	
}
