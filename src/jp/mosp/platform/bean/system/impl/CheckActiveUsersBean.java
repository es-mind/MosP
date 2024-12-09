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

import java.util.Collections;
import java.util.Date;
import java.util.Map;
import java.util.Map.Entry;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.CheckAfterRegistUserBeanInterface;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.dao.system.UserCountDaoInterface;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 有効ユーザ数確認処理。<br>
 * ユーザアカウント情報登録後確認処理の一つ。<br>
 */
public abstract class CheckActiveUsersBean extends PlatformBean implements CheckAfterRegistUserBeanInterface {
	
	/**
	 * ユーザ数カウントDAO。<br>
	 */
	protected UserCountDaoInterface			userCountDao;
	
	/**
	 * ロール参照処理。<br>
	 */
	protected RoleReferenceBeanInterface	roleRefer;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public CheckActiveUsersBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		userCountDao = createDaoInstance(UserCountDaoInterface.class);
		// Beanを準備
		roleRefer = createBeanInstance(RoleReferenceBeanInterface.class);
	}
	
	@Override
	public void check() throws MospException {
		// 有効日(システム日付より後である有効日+システム日付)毎に処理
		for (Date targetDate : userCountDao.getActivateDates()) {
			// ユーザアカウント情報登録後の確認
			check(targetDate);
		}
	}
	
	/**
	 * ユーザアカウント情報登録後の確認を行う。<br>
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void check(Date targetDate) throws MospException {
		// ユーザ数上限値群を取得しロール区分毎に処理
		for (Entry<String, Map<String, Integer>> entry : getLimits().entrySet()) {
			// ロール区分及びユーザ数上限値群を取得
			String roleType = entry.getKey();
			Map<String, Integer> limits = entry.getValue();
			// ユーザアカウント情報登録後の確認
			check(roleType, limits, targetDate);
		}
	}
	
	/**
	 * ユーザアカウント情報登録後の確認を行う。<br>
	 * @param roleType   ロール区分
	 * @param limits     ユーザ数上限値群(キー：ロールコード、値：上限値)
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void check(String roleType, Map<String, Integer> limits, Date targetDate) throws MospException {
		// ロールコード毎に処理
		for (Entry<String, Integer> entry : limits.entrySet()) {
			// ロールコード及び上限値を取得
			String roleCode = entry.getKey();
			int limit = entry.getValue();
			// ロール区分及びロールコードに対するユーザ数上限値の確認
			check(roleType, roleCode, limit, targetDate);
		}
	}
	
	/**
	 * ロール区分及びロールコードに対するユーザ数上限値の確認を行う。<br>
	 * @param roleType   ロール区分
	 * @param roleCode   ロールコード
	 * @param limit      ユーザ数上限値
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void check(String roleType, String roleCode, int limit, Date targetDate) throws MospException {
		// ユーザ数を取得
		int count = userCountDao.count(roleType, roleCode, targetDate);
		// ユーザ数が上限値を超える場合
		if (limit < count) {
			// エラーメッセージを追加
			addErrorMessage(mospParams, roleType, roleCode, limit, targetDate);
		}
	}
	
	/**
	 * エラーメッセージを追加する。<br>
	 * 継承先で上書することを想定している。<br>
	 * @param mospParams MosP処理情報
	 * @param roleType   ロール区分
	 * @param roleCode   ロールコード
	 * @param limit      上限値
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void addErrorMessage(MospParams mospParams, String roleType, String roleCode, int limit, Date targetDate)
			throws MospException {
		// ロール区分配列を取得
		String[][] roleTypes = roleRefer.getRoleTypeSelectArray(targetDate);
		// ロール名称を取得
		String roleName = MospUtility.getCodeName(roleType, roleTypes);
		// エラーメッセージを追加
		PfMessageUtility.addErrorExceedUsers(mospParams, roleName, limit);
	}
	
	/**
	 * ユーザ数上限値群を取得する。<br>
	 * 継承先で上書することを想定している。<br>
	 * @return ユーザ数上限値群(キー：ロール区分、ロールコード、値：上限値)
	 */
	protected Map<String, Map<String, Integer>> getLimits() {
		// 空のユーザ数上限値群を取得(継承先で実装)
		return Collections.emptyMap();
	}
	
}
