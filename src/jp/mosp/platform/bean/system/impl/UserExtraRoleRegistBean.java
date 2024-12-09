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

import java.util.Collection;
import java.util.Date;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.portal.UserCheckBeanInterface;
import jp.mosp.platform.bean.system.UserExtraRoleRegistBeanInterface;
import jp.mosp.platform.dao.system.UserExtraRoleDaoInterface;
import jp.mosp.platform.dto.system.UserExtraRoleDtoInterface;
import jp.mosp.platform.dto.system.impl.PfaUserExtraRoleDto;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * ユーザ追加ロール情報登録処理。<br>
 */
public class UserExtraRoleRegistBean extends PlatformBean implements UserExtraRoleRegistBeanInterface {
	
	/**
	 * ユーザ追加ロール情報DAO。<br>
	 */
	protected UserExtraRoleDaoInterface	dao;
	
	/**
	 * ユーザ確認処理。<br>
	 */
	protected UserCheckBeanInterface	userCheck;
	
	
	/**
	 * コンストラクタ。
	 */
	public UserExtraRoleRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(UserExtraRoleDaoInterface.class);
		// Beanを準備
		userCheck = createBeanInstance(UserCheckBeanInterface.class);
	}
	
	@Override
	public UserExtraRoleDtoInterface getInitDto() {
		// 初期ユーザ追加ロール情報を作成
		return new PfaUserExtraRoleDto();
	}
	
	@Override
	public Set<UserExtraRoleDtoInterface> getInitDtos(int number) {
		// 初期ユーザ追加ロール情報群を準備
		Set<UserExtraRoleDtoInterface> set = new LinkedHashSet<UserExtraRoleDtoInterface>();
		// 個数分処理
		for (int i = 0; i < number; i++) {
			// 初期ユーザ追加ロール情報を作成
			set.add(new PfaUserExtraRoleDto());
		}
		// 初期ユーザ追加ロール情報群を取得
		return set;
	}
	
	@Override
	public void regist(Collection<UserExtraRoleDtoInterface> dtos) throws MospException {
		// ユーザ追加ロール情報が空である場合
		if (dtos.isEmpty()) {
			// 処理無し
			return;
		}
		// 最初の要素を取得
		UserExtraRoleDtoInterface firstDto = dtos.iterator().next();
		// 登録されているユーザ追加ロール情報を論理削除
		delete(firstDto.getUserId(), firstDto.getActivateDate());
		// ユーザ追加ロール情報毎に処理
		for (UserExtraRoleDtoInterface dto : dtos) {
			// ロールコードを取得
			String roleCode = dto.getRoleCode();
			// ロールコードが設定されていない場合
			if (roleCode == null || roleCode.isEmpty()) {
				// 処理無し
				continue;
			}
			// 登録
			regist(dto);
		}
	}
	
	@Override
	public void delete(String userId, Date activateDate) throws MospException {
		// 登録されているユーザ追加ロール情報リストを取得
		List<UserExtraRoleDtoInterface> dtos = dao.findForUser(userId, activateDate);
		// ユーザ追加ロール情報毎に処理
		for (UserExtraRoleDtoInterface dto : dtos) {
			// 論理削除
			delete(dto);
		}
	}
	
	@Override
	public void deleteForRoleType(String roleType) throws MospException {
		// 登録されているユーザ追加ロール情報リストを取得
		List<UserExtraRoleDtoInterface> dtos = dao.findForRoleType(roleType);
		// ユーザ追加ロール情報毎に処理
		for (UserExtraRoleDtoInterface dto : dtos) {
			// 論理削除
			delete(dto);
		}
	}
	
	@Override
	public void copy(String userId, Date fromActivateDate, Date toActivateDate) throws MospException {
		// 有効日(From)のユーザ追加ロール情報を取得
		List<UserExtraRoleDtoInterface> dtos = dao.findForUser(userId, fromActivateDate);
		// ユーザ追加ロール情報毎に処理
		for (UserExtraRoleDtoInterface dto : dtos) {
			// 有効日(To)を設定
			dto.setActivateDate(toActivateDate);
		}
		// ユーザ追加ロール情報群を登録
		regist(dtos);
	}
	
	/**
	 * ユーザ追加ロール情報を登録する。<br>
	 * @param dto ユーザ追加ロール情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist(UserExtraRoleDtoInterface dto) throws MospException {
		// ユーザ追加ロール情報の妥当性を確認
		validate(dto);
		// ユーザ追加ロール情報が妥当でない場合
		if (mospParams.hasErrorMessage()) {
			// 処理無し
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaUserExtraRoleId(dao.nextRecordId());
		// 登録
		dao.insert(dto);
	}
	
	/**
	 * ユーザ追加ロール情報を論理削除する。<br>
	 * @param dto ユーザ追加ロール情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void delete(UserExtraRoleDtoInterface dto) throws MospException {
		// 論理削除
		logicalDelete(dao, dto.getPfaUserExtraRoleId());
	}
	
	@Override
	public void validate(UserExtraRoleDtoInterface dto, Integer row) throws MospException {
		// エラーメッセージ用の名称を取得
		String nameUserId = PfNameUtility.userId(mospParams);
		String nameActivateDate = PfNameUtility.activateDate(mospParams);
		String nameRoleType = PfNameUtility.roleType(mospParams);
		String nameRoleCode = PfNameUtility.roleCode(mospParams);
		// DTOの値を取得
		String userId = dto.getUserId();
		Date activateDate = dto.getActivateDate();
		String roleType = dto.getRoleType();
		String roleCode = dto.getRoleCode();
		// 必須確認(ユーザID)
		checkRequired(userId, nameUserId, row);
		// 必須確認(有効日)
		checkRequired(activateDate, nameActivateDate, row);
		// 必須確認(ロール区分)
		checkRequired(dto.getRoleType(), nameRoleType, row);
		// 必須確認(ロールコード)
		checkRequired(dto.getRoleCode(), nameRoleCode, row);
		// ユーザ存在確認
		userCheck.checkUserExist(userId, activateDate, row);
		// ロール区分存在確認
		userCheck.checkRoleTypeExist(roleType, activateDate, row);
		// ロールコード存在確認
		userCheck.checkRoleExist(roleType, roleCode, activateDate, row);
	}
	
	@Override
	public void validate(UserExtraRoleDtoInterface dto) throws MospException {
		// ユーザ追加ロール情報の妥当性を確認(行インデックス無し)
		validate(dto, null);
	}
	
}
