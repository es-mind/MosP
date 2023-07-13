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
package jp.mosp.platform.dto.system;

import java.util.Map;

import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;

/**
 * アカウント一覧DTOのインターフェース。<br>
 */
public interface AccountInfoDtoInterface
		extends PlatformDtoInterface, EmployeeCodeDtoInterface, EmployeeNameDtoInterface {
	
	/**
	 * @return pfmUserId
	 */
	long getPfmUserId();
	
	/**
	 * @param pfmUserId セットする pfmUserId
	 */
	void setPfmUserId(long pfmUserId);
	
	/**
	 * @return userId
	 */
	String getUserId();
	
	/**
	 * @param userId セットする userId
	 */
	void setUserId(String userId);
	
	/**
	 * @return personalId
	 */
	String getPersonalId();
	
	/**
	 * @param personalId セットする personalId
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @return roleCode
	 */
	String getRoleCode();
	
	/**
	 * @param roleCode セットする roleCode
	 */
	void setRoleCode(String roleCode);
	
	/**
	 * @return extraRoles
	 */
	Map<String, String> getExtraRoles();
	
	/**
	 * @param extraRoles セットする extraRoles
	 */
	void setExtraRoles(Map<String, String> extraRoles);
	
	/**
	 * @return roleName
	 */
	String getRoleName();
	
	/**
	 * @param roleName セットする roleName
	 */
	void setRoleName(String roleName);
	
}
