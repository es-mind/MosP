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
package jp.mosp.platform.bean.portal.impl;

import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.RetirementReferenceBeanInterface;
import jp.mosp.platform.bean.human.SuspensionReferenceBeanInterface;
import jp.mosp.platform.bean.portal.UserCheckBeanInterface;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * ユーザ確認クラス。<br>
 * <br>
 * 認証時、アカウントメンテナンス時等に、ユーザが妥当であるかの確認を行う。<br>
 */
public class UserCheckBean extends PlatformBean implements UserCheckBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(認証時ユーザ確認)。
	 */
	protected static final String			APP_CHECK_USER			= "CheckUser";
	
	/**
	 * 認証時ユーザ確認設定(入社確認)。
	 */
	protected static final String			USER_CHECK_ENTRANCE		= "Entrance";
	
	/**
	 * 認証時ユーザ確認設定(退職確認)。
	 */
	protected static final String			USER_CHECK_RETIREMENT	= "Retirement";
	
	/**
	 * 認証時ユーザ確認設定(休職確認)。
	 */
	protected static final String			USER_CHECK_SUSPENSION	= "Suspension";
	
	/**
	 * ユーザマスタDAO。
	 */
	private UserMasterDaoInterface			userMasterDao;
	
	/**
	 * ロール参照処理。<br>
	 */
	protected RoleReferenceBeanInterface	roleRefer;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public UserCheckBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		userMasterDao = createDaoInstance(UserMasterDaoInterface.class);
		// Beanを準備
		roleRefer = createBeanInstance(RoleReferenceBeanInterface.class);
	}
	
	@Override
	public void checkUserExist(String userId, Integer row) throws MospException {
		// ユーザ履歴一覧を取得し件数を確認
		if (userMasterDao.findForHistory(userId).isEmpty()) {
			// エラーメッセージ追加
			PfMessageUtility.addErrorSelectedUserIdNotExist(mospParams, userId, row);
		}
	}
	
	@Override
	public void checkUserExist(String userId, Date activateDate, Integer row) throws MospException {
		// ユーザ履歴一覧を取得し件数を確認
		if (userMasterDao.findForKey(userId, activateDate) == null) {
			// エラーメッセージ追加
			PfMessageUtility.addErrorUserHistoryNotExist(mospParams, activateDate, row);
		}
	}
	
	@Override
	public UserMasterDtoInterface checkUserExist(String userId, Date targetDate) throws MospException {
		// 対象日におけるユーザ情報を取得
		UserMasterDtoInterface dto = userMasterDao.findForInfo(userId, targetDate);
		// ユーザ存在確認
		if (dto == null || dto.getInactivateFlag() == MospConst.INACTIVATE_FLAG_ON) {
			// メッセージを設定
			PfMessageUtility.addErrorNoUser(mospParams);
		}
		// 対象日におけるユーザ情報を取得
		return dto;
	}
	
	@Override
	public void checkUserEmployeeForUserId(String userId, Date targetDate) throws MospException {
		// ユーザ存在確認(対象日におけるユーザ情報を取得)
		UserMasterDtoInterface dto = checkUserExist(userId, targetDate);
		// 対象日における有効なユーザが存在しない場合
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// ユーザ社員妥当性確認
		checkUserEmployee(dto.getPersonalId(), targetDate);
	}
	
	@Override
	public void checkUserEmployee(String personalId, Date targetDate) throws MospException {
		// ユーザ確認プロパティ値毎に確認を実施
		for (String userCheck : mospParams.getApplicationProperties(APP_CHECK_USER)) {
			if (userCheck.equals(USER_CHECK_ENTRANCE)) {
				// 入社情報確認
				checkEntrance(personalId, targetDate);
			} else if (userCheck.equals(USER_CHECK_RETIREMENT)) {
				// 退職情報確認
				checkRetire(personalId, targetDate);
			} else if (userCheck.equals(USER_CHECK_SUSPENSION)) {
				// 休職情報確認
				checkSuspension(personalId, targetDate);
			}
		}
	}
	
	@Override
	public void checkUserRole(String userId, Date targetDate) throws MospException {
		// ユーザ存在確認(対象日におけるユーザ情報を取得)
		UserMasterDtoInterface dto = checkUserExist(userId, targetDate);
		// 対象日における有効なユーザが存在しない場合
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// ロール存在確認
		checkRoleExist(dto.getRoleCode(), targetDate);
	}
	
	@Override
	public void checkRoleExist(String roleCode, Date targetDate, Integer row) throws MospException {
		// 利用可能ロールコード群を取得
		Set<String> availableRoleCodes = roleRefer.getAvailabeRoleCodes(targetDate);
		// 利用可能ロールコード群にロールコードが含まれない場合
		if (availableRoleCodes.contains(roleCode) == false) {
			// メッセージを設定
			PfMessageUtility.addErrorNoRole(mospParams, row);
		}
	}
	
	@Override
	public void checkRoleExist(String roleCode, Date targetDate) throws MospException {
		// ユーザロールの存在確認
		checkRoleExist(roleCode, targetDate, null);
	}
	
	@Override
	public void checkRoleExist(String roleType, String roleCode, Date targetDate, Integer row) throws MospException {
		// 利用可能ロールコード群を取得
		Set<String> availableRoleCodes = roleRefer.getAvailabeRoleCodes(targetDate, roleType);
		// 利用可能ロールコード群にロールコードが含まれない場合
		if (availableRoleCodes.contains(roleCode) == false) {
			// メッセージを設定
			PfMessageUtility.addErrorNoRole(mospParams, row);
		}
	}
	
	@Override
	public void checkRoleTypeExist(String roleType, Date targetDate, Integer row) throws MospException {
		// 利用可能ロール区分リストを取得
		List<String> list = roleRefer.getAvailableRoleTypes(targetDate);
		// 利用可能ロール区分に対象ロール区分が含まれない場合
		if (list.contains(roleType) == false) {
			// メッセージを設定
			PfMessageUtility.addErrorNoRoleType(mospParams, row);
		}
	}
	
	/**
	 * 入社確認を行う。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEntrance(String personalId, Date targetDate) throws MospException {
		// 入社情報確認
		if (isEntered(personalId, targetDate) == false) {
			// 人事マスタ参照処理を準備
			HumanReferenceBeanInterface humanRefer = (HumanReferenceBeanInterface)createBean(
					HumanReferenceBeanInterface.class);
			// 社員コードを取得
			String employeeCode = humanRefer.getEmployeeCode(personalId, targetDate);
			// メッセージを設定
			PfMessageUtility.addErrorEmployeeNotJoinForAccount(mospParams, employeeCode);
		}
	}
	
	/**
	 * 退職確認を行う。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRetire(String personalId, Date targetDate) throws MospException {
		// 人事退職情報参照クラス取得
		RetirementReferenceBeanInterface reference;
		reference = (RetirementReferenceBeanInterface)createBean(RetirementReferenceBeanInterface.class);
		// 退職情報確認
		if (reference.isRetired(personalId, targetDate)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeRetired(mospParams);
		}
	}
	
	/**
	 * 休職確認を行う。<br>
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSuspension(String personalId, Date targetDate) throws MospException {
		// 人事休職情報参照クラス取得
		SuspensionReferenceBeanInterface reference;
		reference = (SuspensionReferenceBeanInterface)createBean(SuspensionReferenceBeanInterface.class);
		// 休職情報確認
		if (reference.isSuspended(personalId, targetDate)) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorEmployeeSuspended(mospParams);
		}
	}
	
}
