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
package jp.mosp.platform.bean.file.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.UserExportBeanInterface;
import jp.mosp.platform.bean.system.SectionReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dto.human.HumanDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;

/**
 * ユーザマスタエクスポートクラス。
 */
public class UserExportBean extends BaseExportBean implements UserExportBeanInterface {
	
	/**
	 * アカウント情報DAO。<br>
	 */
	protected UserMasterDaoInterface		userMasterDao;
	
	/**
	 * 所属マスタ参照クラス。<br>
	 */
	protected SectionReferenceBeanInterface	sectionReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public UserExportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基のメソッドを実行
		super.initBean();
		// DAO及びBeanの準備
		userMasterDao = createDaoInstance(UserMasterDaoInterface.class);
		sectionReference = createBeanInstance(SectionReferenceBeanInterface.class);
	}
	
	@Override
	protected List<String[]> makeCsvDataList(List<String> fieldList, Date targetDate, String workPlaceCode,
			String employmentContractCode, String sectionCode, String positionCode) throws MospException {
		// CSVデータリストを準備
		List<String[]> csvDataList = new ArrayList<String[]>();
		// 人事情報を検索
		List<HumanDtoInterface> humans = searchHumanList(targetDate, workPlaceCode, employmentContractCode, sectionCode,
				positionCode);
		// アカウント情報付加
		addUserMasterData(csvDataList, fieldList, targetDate, humans);
		// CSVデータリストを取得
		return csvDataList;
	}
	
	/**
	 * CSVデータリストにアカウント情報を付加する。<br>
	 * @param csvDataList CSVデータリスト
	 * @param fieldList   エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @param targetDate  対象日
	 * @param humans      人事情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void addUserMasterData(List<String[]> csvDataList, List<String> fieldList, Date targetDate,
			List<HumanDtoInterface> humans) throws MospException {
		// アカウント情報フィールド情報準備
		Integer userIdIndex = null;
		Integer activateDateIndex = null;
		Integer employeeCodeIndex = null;
		Integer employeeNameIndex = null;
		Integer sectionNameIndex = null;
		Integer sectionDisplayIndex = null;
		Integer roleCodeIndex = null;
		// エクスポートフィールド名称毎に処理
		for (int i = 0; i < fieldList.size(); i++) {
			// エクスポートフィールド名称を取得
			String field = fieldList.get(i);
			// エクスポートフィールド名称毎にインデックスを取得
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_USER_ID)) {
				userIdIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_ACTIVATE_DATE)) {
				activateDateIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_EMPLOYEE_CODE)) {
				employeeCodeIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_FULL_NAME)) {
				employeeNameIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_USER_ROLE_CODE)) {
				roleCodeIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_SECTION_NAME)) {
				sectionNameIndex = Integer.valueOf(i);
			}
			if (MospUtility.isEqual(field, PlatformFileConst.FIELD_SECTION_DISPLAY)) {
				sectionDisplayIndex = Integer.valueOf(i);
			}
		}
		// CSVデータリスト毎に情報を付加
		for (int i = 0; i < humans.size(); i++) {
			// 人事情報取得
			HumanDtoInterface human = humans.get(i);
			// アカウント情報取得及び確認
			List<UserMasterDtoInterface> userMasterList = userMasterDao.findForPersonalId(human.getPersonalId(),
					targetDate);
			for (UserMasterDtoInterface userMaster : userMasterList) {
				// CSVデータ準備
				String[] csvData = new String[fieldList.size()];
				// アカウント情報設定
				if (userIdIndex != null) {
					csvData[userIdIndex.intValue()] = userMaster.getUserId();
				}
				if (activateDateIndex != null) {
					csvData[activateDateIndex.intValue()] = getStringDate(userMaster.getActivateDate());
				}
				if (employeeCodeIndex != null) {
					csvData[employeeCodeIndex.intValue()] = human.getEmployeeCode();
				}
				if (employeeNameIndex != null) {
					csvData[employeeNameIndex.intValue()] = MospUtility.getHumansName(human.getFirstName(),
							human.getLastName());
				}
				if (roleCodeIndex != null) {
					csvData[roleCodeIndex.intValue()] = userMaster.getRoleCode();
				}
				if (sectionNameIndex != null) {
					csvData[sectionNameIndex.intValue()] = sectionReference.getSectionName(human.getSectionCode(),
							targetDate);
				}
				if (sectionDisplayIndex != null) {
					csvData[sectionDisplayIndex.intValue()] = sectionReference.getSectionDisplay(human.getSectionCode(),
							targetDate);
				}
				// CSVデータをCSVデータリストに追加
				csvDataList.add(csvData);
			}
		}
	}
	
}
