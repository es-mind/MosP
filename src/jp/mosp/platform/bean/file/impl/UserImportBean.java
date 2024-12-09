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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.bean.file.UserImportBeanInterface;
import jp.mosp.platform.bean.human.HumanReferenceBeanInterface;
import jp.mosp.platform.bean.human.impl.HumanRegistBean;
import jp.mosp.platform.bean.system.UserAccountRegistBeanInterface;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * ユーザマスタインポートクラス。
 */
public class UserImportBean extends PlatformFileBean implements UserImportBeanInterface {
	
	/**
	 * ユーザ情報参照クラス。<br>
	 */
	protected UserMasterReferenceBeanInterface	userMasterRefer;
	
	/**
	 * ユーザ情報登録クラス。<br>
	 */
	protected UserAccountRegistBeanInterface	userAccountRegist;
	
	/**
	 * 人事情報参照クラス。<br>
	 */
	protected HumanReferenceBeanInterface		humanRefer;
	
	
	/**
	 * {@link HumanRegistBean#HumanRegistBean()}を実行する。<br>
	 */
	public UserImportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Bean準備
		userMasterRefer = createBeanInstance(UserMasterReferenceBeanInterface.class);
		humanRefer = createBeanInstance(HumanReferenceBeanInterface.class);
		userAccountRegist = createBeanInstance(UserAccountRegistBeanInterface.class);
	}
	
	@Override
	public int importFile(ImportDtoInterface importDto, InputStream requestedFile) throws MospException {
		// アップロードファイルを登録情報リストに変換
		List<String[]> dataList = getDataList(importDto, requestedFile);
		// ユーザ情報リストを取得
		Collection<UserMasterDtoInterface> userDtos = getTargetLists(importDto, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// ユーザ情報群を登録
		userAccountRegist.regist(userDtos);
		// 登録件数取得
		return userDtos.size();
	}
	
	/**
	 * ユーザ情報リストを取得する。<br>
	 * インポートマスタ情報に従い、登録情報リストからユーザマスタ情報リストに変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * @param importDto インポートマスタ情報
	 * @param dataList  登録情報リスト
	 * @return ユーザ情報リスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected List<UserMasterDtoInterface> getTargetLists(ImportDtoInterface importDto, List<String[]> dataList)
			throws MospException {
		// ユーザ情報リストを準備
		List<UserMasterDtoInterface> userList = new ArrayList<UserMasterDtoInterface>();
		// インポートフィールド情報取得
		List<ImportFieldDtoInterface> fieldList = getImportFieldList(importDto.getImportCode());
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return userList;
		}
		// 登録情報リスト毎に処理
		for (int i = 0; i < dataList.size(); i++) {
			// 登録情報取得
			String[] data = dataList.get(i);
			// ユーザ情報取得及び確認
			UserMasterDtoInterface userMasterDto = getUserMasterDto(fieldList, data, i);
			if (userMasterDto != null) {
				// ユーザ情報リストに追加
				userList.add(userMasterDto);
			} else {
				// ユーザ情報が取得できなかった場合
				continue;
			}
		}
		// ユーザ情報リストを取得
		return userList;
	}
	
	/**
	 * ユーザ情報を取得する。<br>
	 * インポートフィールド情報リストに従い、登録情報リストからユーザ情報に変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * インポート不能登録情報の場合は、nullを返す。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @param row       行インデックス
	 * @return ユーザ情報
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected UserMasterDtoInterface getUserMasterDto(List<ImportFieldDtoInterface> fieldList, String[] data, int row)
			throws MospException {
		// 登録情報の内容を取得(登録情報に含まれない場合はnull)
		String userId = getFieldValue(PlatformFileConst.FIELD_USER_ID, fieldList, data);
		Date activateDate = getDateFieldValue(PlatformFileConst.FIELD_ACTIVATE_DATE, fieldList, data);
		String employeeCode = getFieldValue(PlatformFileConst.FIELD_EMPLOYEE_CODE, fieldList, data);
		String roleCode = getFieldValue(PlatformFileConst.FIELD_USER_ROLE_CODE, fieldList, data);
		// ユーザID確認
		if (userId == null || userId.isEmpty()) {
			// エラーメッセージ追加
			PfMessageUtility.addErrorRequired(mospParams, PfNameUtility.userId(mospParams), row);
			return null;
		}
		// 有効日確認
		if (activateDate == null) {
			// エラーメッセージ追加
			PfMessageUtility.addErrorActivateDateRequired(mospParams, row);
			return null;
		}
		// ユーザ情報準備
		UserMasterDtoInterface userMaster = userMasterRefer.getUserInfo(userId, activateDate);
		// ユーザ情報確認
		if (userMaster == null) {
			userMaster = userAccountRegist.getInitUserDto();
		}
		// ユーザ情報に登録情報の内容を設定
		userMaster.setUserId(userId);
		userMaster.setActivateDate(activateDate);
		if (roleCode != null) {
			userMaster.setRoleCode(roleCode);
		} else if (userMaster.getRoleCode() == null) {
			userMaster.setRoleCode("");
		}
		// 社員コード確認
		if (employeeCode != null) {
			// 個人ID取得
			String personalId = humanRefer.getPersonalId(employeeCode, activateDate);
			// 個人ID確認
			if (userMaster.getPersonalId() != null && userMaster.getPersonalId().equals(personalId) == false) {
				// エラーメッセージを設定
				PfMessageUtility.addErrorUserIdExist(mospParams, userId);
				return null;
			}
			// 個人ID設定
			userMaster.setPersonalId(personalId);
		}
		// 入力チェック
		userAccountRegist.validate(userMaster, row);
		return userMaster;
	}
	
}
