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
package jp.mosp.platform.bean.system.impl;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.CheckAfterRegistUserBeanInterface;
import jp.mosp.platform.dao.system.UserCountDaoInterface;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 必須ロール確認処理。<br>
 * ユーザアカウント情報登録後確認処理の一つ。<br>
 * 必須ロールコードを有する有効なユーザが存在するかを確認する。<br>
 * 必須ロールコードが存在せずログインできない状態にならないようにする。<br>
 */
public class CheckNeededRoleBean extends PlatformBean implements CheckAfterRegistUserBeanInterface {
	
	/**
	 * ユーザ数カウントDAO。<br>
	 */
	protected UserCountDaoInterface userCountDao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public CheckNeededRoleBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		userCountDao = createDaoInstance(UserCountDaoInterface.class);
	}
	
	@Override
	public void check() throws MospException {
		// 必須ロールコードを取得
		String roleCode = RoleUtility.getNeededRole(mospParams);
		// 有効日(システム日付より後である有効日+システム日付)毎に処理
		for (Date targetDate : userCountDao.getActivateDates()) {
			// メインロールに必須ロールコードが設定されているユーザが存在しない場合
			if (userCountDao.count("", roleCode, targetDate) == 0) {
				// エラーメッセージを追加
				PfMessageUtility.addErrorNoNeededRole(mospParams, targetDate);
			}
		}
	}
	
}
