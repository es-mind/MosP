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
package jp.mosp.platform.bean.system.impl;

import java.util.Date;
import java.util.HashMap;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserExtraRoleReferenceBeanInterface;
import jp.mosp.platform.dao.system.UserExtraRoleDaoInterface;
import jp.mosp.platform.dto.system.UserExtraRoleDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * ユーザ追加ロール情報参照処理。<br>
 */
public class UserExtraRoleReferenceBean extends PlatformBean implements UserExtraRoleReferenceBeanInterface {
	
	/**
	 * ユーザ追加ロールDAO。<br>
	 */
	protected UserExtraRoleDaoInterface		dao;
	
	/**
	 * ロール参照処理。<br>
	 */
	protected RoleReferenceBeanInterface	roleRefer;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public UserExtraRoleReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(UserExtraRoleDaoInterface.class);
		// Beanを準備
		roleRefer = createBeanInstance(RoleReferenceBeanInterface.class);
	}
	
	@Override
	public Map<String, String> getUserExtraRoleMap(String userId, Date activateDate) throws MospException {
		// ユーザ追加ロールコード群(順序有)を準備
		Map<String, String> map = new LinkedHashMap<String, String>();
		// ユーザID及び有効日が合致するユーザ追加ロール情報群を取得
		Map<String, UserExtraRoleDtoInterface> dtos = getUserExtraRolesAsMap(userId, activateDate);
		// 利用可能ロール区分リスト(インデックス昇順)を取得
		List<String> roleTypes = roleRefer.getAvailableRoleTypes(activateDate);
		// 利用可能ロール区分毎に処理
		for (String roleType : roleTypes) {
			// ユーザ追加ロール情報群からユーザ追加ロール情報を取得
			UserExtraRoleDtoInterface dto = dtos.get(roleType);
			// ユーザ追加ロール情報が取得できなかった場合
			if (dto == null) {
				// 処理無し
				continue;
			}
			// ユーザ追加ロールコード群に追加
			map.put(roleType, dto.getRoleCode());
		}
		// ユーザ追加ロールコード群を取得
		return map;
	}
	
	@Override
	public Set<String> getUserExtraRoles(String userId, Date activateDate) throws MospException {
		// ユーザ追加ロールコード群を取得
		return new HashSet<String>(getUserExtraRoleMap(userId, activateDate).values());
	}
	
	@Override
	public boolean isTheRoleCodeSet(UserMasterDtoInterface dto, String roleCode) throws MospException {
		// 対象ユーザのロールコード群を取得
		Set<String> userRoles = getUserRoles(dto);
		// 対象ユーザのロールに対象のロールコードが設定されているかを確認
		return userRoles.contains(roleCode);
	}
	
	@Override
	public boolean isTheRoleCodeSet(UserMasterDtoInterface dto, Set<String> roleCodes) throws MospException {
		// ロールコード群が指定されていない場合
		if (roleCodes == null || roleCodes.isEmpty()) {
			// 設定されていないと判断
			return false;
		}
		// 対象ユーザのロールコード群を取得
		Set<String> userRoles = getUserRoles(dto);
		// 対象ユーザのロールコード毎に処理
		for (String userRole : userRoles) {
			// ロールコード群に対象ユーザのロールコードが含まれる場合
			if (roleCodes.contains(userRole)) {
				// 設定されていると判断
				return true;
			}
		}
		// 設定されていないと判断
		return false;
	}
	
	/**
	 * ユーザIDと有効日が合致するユーザ追加ロール情報群を取得する。<br>
	 * 順序及び利用可能ロール区分は、考慮しない。<br>
	 * <br>
	 * @param userId       ユーザID
	 * @param activateDate 有効日
	 * @return ユーザ追加ロール情報群(キー：ロール区分、値：ユーザ追加ロール情報)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, UserExtraRoleDtoInterface> getUserExtraRolesAsMap(String userId, Date activateDate)
			throws MospException {
		// ユーザ追加ロールコード群を準備
		Map<String, UserExtraRoleDtoInterface> map = new HashMap<String, UserExtraRoleDtoInterface>();
		// ユーザID及び有効日が合致するユーザ追加ロール情報リストを取得
		List<UserExtraRoleDtoInterface> dtos = dao.findForUser(userId, activateDate);
		// ユーザ追加ロール情報毎に処理
		for (UserExtraRoleDtoInterface dto : dtos) {
			// ユーザ追加ロールコード群に追加
			map.put(dto.getRoleType(), dto);
		}
		// ユーザ追加ロールコード群を取得
		return map;
	}
	
	/**
	 * 対象ユーザのロールコード群を取得する。<br>
	 * 追加ロールがあれば、それらのロール追加情報も考慮する。<br>
	 * <br>
	 * 最初の要素は、対象ユーザのメインロール。<br>
	 * <br>
	 * @param dto ユーザマスタ情報
	 * @return ロールコード群
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Set<String> getUserRoles(UserMasterDtoInterface dto) throws MospException {
		// ロールコード群(順序有)を準備
		Set<String> set = new LinkedHashSet<String>();
		// ユーザマスタ情報が存在しない場合
		if (dto == null) {
			// 処理終了
			return set;
		}
		// 対象ユーザのメインロールを設定
		set.add(dto.getRoleCode());
		// 対象ユーザの追加ロールを取得し設定
		set.addAll(getUserExtraRoles(dto.getUserId(), dto.getActivateDate()));
		// ロールコード群を取得
		return set;
	}
	
}
