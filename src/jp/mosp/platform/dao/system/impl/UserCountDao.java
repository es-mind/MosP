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
package jp.mosp.platform.dao.system.impl;

import java.util.Date;
import java.util.Set;
import java.util.TreeSet;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dao.system.UserCountDaoInterface;

/**
 * ユーザ数カウントDAO。<br>
 */
public class UserCountDao extends PfmUserDao implements UserCountDaoInterface {
	
	/**
	 * 人事退職情報DAO(サブクエリ等取得用)。<br>
	 */
	protected RetirementDaoInterface retirementDao;
	
	
	@Override
	protected void setDaoInstances() throws MospException {
		// 継承基のメソッドを実行
		super.setDaoInstances();
		// インスタンス生成(サブクエリ等を取得するためのDAOクラス生成)
		retirementDao = (RetirementDaoInterface)loadDao(RetirementDaoInterface.class);
	}
	
	@Override
	public int count(String roleType, String roleCode, Date targetDate) throws MospException {
		try {
			// サブクエリ等を取得するためのDAOクラスを設定
			setDaoInstances();
			// パラメータインデックスを準備
			index = 1;
			// SQLを作成
			StringBuilder sb = new StringBuilder(getSelectCountQuery(getClass()));
			// 有効日以前で最新の情報を取得
			sb.append(getQueryForMaxActivateDate(TABLE, COL_USER_ID, COL_ACTIVATE_DATE));
			// 人事退職情報を付加するSQLを追加
			sb.append(retirementDao.getQueryForJoinUser(COL_PERSONAL_ID));
			// ロール区分が空白でない場合
			if (MospUtility.isEmpty(roleType) == false) {
				// 追加ロールコードを付加するSQLを追加
				sb.append(userExtraRoleDao.getQueryForJoinUser(COL_USER_ID, COL_ACTIVATE_DATE));
			}
			// 条件文を追加
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(inactivateFlagOff());
			sb.append(and());
			// 退職日を付加するSQLを追加
			sb.append(leftParenthesis());
			sb.append(isNull(retirementDao.getRetirementDateColumnForJoinUser()));
			sb.append(or());
			sb.append(greaterEqual(retirementDao.getRetirementDateColumnForJoinUser()));
			sb.append(rightParenthesis());
			sb.append(and());
			// ロール区分が空白でない場合
			if (MospUtility.isEmpty(roleType) == false) {
				// 追加ロール区分を指定
				sb.append(equal(userExtraRoleDao.getRoleTypeColumnForJoinUser()));
				// ロールコードが空白でない場合
				if (MospUtility.isEmpty(roleCode) == false) {
					// 追加ロールコードを指定
					sb.append(and());
					sb.append(equal(userExtraRoleDao.getRoleCodeColumnForJoinUser()));
				}
			}
			// ロール区分が空白である場合
			if (MospUtility.isEmpty(roleType)) {
				// メインロールのロールコードを指定
				sb.append(equal(COL_ROLE_CODE));
			}
			// ステートメントを生成
			prepareStatement(sb.toString());
			// パラメータを設定
			setParam(index++, targetDate);
			setParam(index++, targetDate);
			// ロール区分が空白でない場合
			if (MospUtility.isEmpty(roleType) == false) {
				// 追加ロール区分を指定
				setParam(index++, roleType);
				// ロールコードが空白でない場合
				if (MospUtility.isEmpty(roleCode) == false) {
					// 追加ロールコードを指定
					setParam(index++, roleCode);
				}
			}
			// ロール区分が空白である場合
			if (MospUtility.isEmpty(roleType)) {
				// メインロールのロールコードを指定
				setParam(index++, roleCode);
			}
			// 実行
			executeQuery();
			// 検索結果取得
			int count = 0;
			if (next()) {
				count = rs.getInt(1);
			}
			// 件数を取得
			return count;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public Set<Date> getActivateDates() throws MospException {
		try {
			// システム日付を取得
			Date systemDate = DateUtility.getSystemDate();
			// パラメータインデックスを準備
			index = 1;
			// SQLを作成
			StringBuilder sb = new StringBuilder(select());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(from(TABLE));
			// 条件文を追加
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(greater(COL_ACTIVATE_DATE));
			sb.append(groupBy(COL_ACTIVATE_DATE));
			// ステートメントを生成
			prepareStatement(sb.toString());
			
			// パラメータを設定
			setParam(index++, systemDate);
			// 実行
			executeQuery();
			// 検索結果を取得
			Set<Date> set = new TreeSet<Date>();
			if (next()) {
				set.add(rs.getDate(1));
			}
			// 検索結果にシステム日付を追加
			set.add(systemDate);
			// 有効日群を取得
			return set;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
