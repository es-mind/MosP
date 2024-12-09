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
package jp.mosp.platform.bean.file.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.file.UserExtraRoleExportBeanInterface;
import jp.mosp.platform.bean.system.UserExtraRoleReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * ユーザ追加ロール情報エクスポート処理。<br>
 */
public class UserExtraRoleExportBean extends BaseExportBean implements UserExtraRoleExportBeanInterface {
	
	/**
	 * アカウント情報DAO。<br>
	 */
	protected UserMasterDaoInterface				userMasterDao;
	
	/**
	 * ユーザ追加ロール情報参照処理。<br>
	 */
	protected UserExtraRoleReferenceBeanInterface	userExtraRoleRefer;
	
	
	/**
	 * {@link BaseExportBean#BaseExportBean()}を実行する。<br>
	 */
	public UserExtraRoleExportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// DAOを準備
		userMasterDao = createDaoInstance(UserMasterDaoInterface.class);
		// Beanを準備
		userExtraRoleRefer = createBeanInstance(UserExtraRoleReferenceBeanInterface.class);
	}
	
	@Override
	protected List<String[]> makeCsvDataList(List<String> fieldList, Date targetDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
		// CSVデータリストを準備
		List<String[]> csvDataList = new ArrayList<String[]>();
		// 人事情報を検索
		List<HumanDtoInterface> humans = searchHumanList(targetDate, workPlaceCode, employmentContractCode, sectionCode,
				positionCode);
		// 人事情報毎に処理
		for (HumanDtoInterface human : humans) {
			// 個人IDと有効日からユーザ情報リスト(1個人IDにつき複数のユーザIDを持てる)を取得
			List<UserMasterDtoInterface> userList = userMasterDao.findForPersonalId(human.getPersonalId(), targetDate);
			// ユーザ情報毎に処理
			for (UserMasterDtoInterface user : userList) {
				csvDataList.addAll(getCsvDataList(user, fieldList));
			}
		}
		// CSVデータリストを取得
		return csvDataList;
	}
	
	/**
	 * CSVデータリストを取得する。<br>
	 * 1ユーザに複数の追加ロールが設定されている場合があるため、リストを返す。<br>
	 * <br>
	 * @param user      ユーザ情報
	 * @param fieldList エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @return CSVデータリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected List<String[]> getCsvDataList(UserMasterDtoInterface user, List<String> fieldList) throws MospException {
		// CSVデータリストを準備
		List<String[]> csvDataList = new ArrayList<String[]>();
		// ユーザID及び有効日を取得
		String userId = user.getUserId();
		Date activateDate = user.getActivateDate();
		// ユーザIDと有効日が合致するユーザ追加ロール情報群を取得
		Map<String, String> extraRoles = userExtraRoleRefer.getUserExtraRoleMap(userId, activateDate);
		// ユーザ追加ロール情報毎に処理
		for (Entry<String, String> extraRole : extraRoles.entrySet()) {
			// CSVデータを取得しCSVデータリストに設定
			csvDataList.add(getCsvData(userId, activateDate, extraRole.getKey(), extraRole.getValue(), fieldList));
		}
		// CSVデータリストを取得
		return csvDataList;
	}
	
	/**
	 * CSVデータを取得する。<br>
	 * @param userId       ユーザID
	 * @param activateDate 有効日
	 * @param roleType     ロール区分
	 * @param roleCode     ロールコード
	 * @param fieldList    エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @return CSVデータ
	 */
	protected String[] getCsvData(String userId, Date activateDate, String roleType, String roleCode,
			List<String> fieldList) {
		// CSVデータを準備
		List<String> csvData = new ArrayList<String>();
		// フィールド名称(インデックス自然順序付け)毎に処理
		for (String field : fieldList) {
			// フィールド名称を確認
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_USER_ID)) {
				// ユーザIDの場合
				csvData.add(userId);
			} else if (MospUtility.isEqual(field, PlatformFileConst.FIELD_ACTIVATE_DATE)) {
				// 有効日の場合
				csvData.add(getStringDate(activateDate));
			} else if (MospUtility.isEqual(field, PlatformFileConst.FIELD_USER_ROLE_TYPE)) {
				// ロール区分の場合
				csvData.add(roleType);
			} else if (MospUtility.isEqual(field, PlatformFileConst.FIELD_USER_ROLE_CODE)) {
				// ロールコードの場合
				csvData.add(roleCode);
			}
		}
		// CSVデータを取得
		return MospUtility.toArray(csvData);
	}
	
}
