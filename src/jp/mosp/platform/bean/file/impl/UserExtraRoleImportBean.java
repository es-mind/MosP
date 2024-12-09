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
import java.util.Collections;
import java.util.Date;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.bean.file.UserExtraRoleImportBeanInterface;
import jp.mosp.platform.bean.system.UserAccountRegistBeanInterface;
import jp.mosp.platform.bean.system.UserExtraRoleRegistBeanInterface;
import jp.mosp.platform.bean.system.UserMasterReferenceBeanInterface;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.system.UserExtraRoleDtoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.utils.PlatformUtility;

/**
 * ユーザ追加ロール情報インポート処理。<br>
 */
public class UserExtraRoleImportBean extends PlatformFileBean implements UserExtraRoleImportBeanInterface {
	
	/**
	 * ユーザ追加ロール情報登録処理。<br>
	 */
	protected UserExtraRoleRegistBeanInterface	regist;
	
	/**
	 * ユーザアカウント情報参照処理。<br>
	 */
	protected UserMasterReferenceBeanInterface	userRefer;
	
	/**
	 * ユーザアカウント情報登録処理。<br>
	 */
	protected UserAccountRegistBeanInterface	accountRegist;
	
	
	/**
	 * {@link PlatformFileBean#PlatformFileBean()}を実行する。<br>
	 */
	public UserExtraRoleImportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Beanを準備
		regist = createBeanInstance(UserExtraRoleRegistBeanInterface.class);
		userRefer = createBeanInstance(UserMasterReferenceBeanInterface.class);
		accountRegist = createBeanInstance(UserAccountRegistBeanInterface.class);
	}
	
	@Override
	public int importFile(ImportDtoInterface importDto, InputStream requestedFile) throws MospException {
		// アップロードファイルを登録情報リストに変換
		List<String[]> dataList = getDataList(importDto, requestedFile);
		// 登録情報リストからユーザ追加ロール情報群(キー：ユーザID)に変換
		Map<String, Map<Date, Set<UserExtraRoleDtoInterface>>> map = getTargetDtos(importDto, dataList);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// ユーザ追加ロール情報群(キー：有効日)毎に処理
		for (Map<Date, Set<UserExtraRoleDtoInterface>> activateDateMap : map.values()) {
			// ユーザ追加ロール情報群毎に処理
			for (Set<UserExtraRoleDtoInterface> dtos : activateDateMap.values()) {
				// ユーザ追加ロール情報を登録
				regist.regist(dtos);
			}
		}
		// ユーザアカウント情報登録後の確認
		accountRegist.checkAfterRegist();
		if (mospParams.hasErrorMessage()) {
			return 0;
		}
		// 登録件数取得
		return dataList.size();
	}
	
	/**
	 * ユーザ追加ロール情報群を取得する。<br>
	 * インポートマスタ情報に従い、登録情報リストからユーザ追加ロール情報群に変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * @param importDto インポートマスタ情報
	 * @param dataList  登録情報リスト
	 * @return ユーザ追加ロール情報群(キー：ユーザID、キー：有効日、値：ユーザ追加ロール情報群)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected Map<String, Map<Date, Set<UserExtraRoleDtoInterface>>> getTargetDtos(ImportDtoInterface importDto,
			List<String[]> dataList) throws MospException {
		// ユーザ追加ロール情報群を準備
		Map<String, Map<Date, Set<UserExtraRoleDtoInterface>>> map = new LinkedHashMap<String, Map<Date, Set<UserExtraRoleDtoInterface>>>();
		// インポートフィールド情報取得
		List<ImportFieldDtoInterface> fieldList = getImportFieldList(importDto.getImportCode());
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了(登録情報リスト内の各登録情報長が不正である場合)
			return map;
		}
		// 登録情報リスト毎に処理
		for (int i = 0; i < dataList.size(); i++) {
			// 登録情報取得
			String[] data = dataList.get(i);
			// ユーザ追加ロール情報を取得
			UserExtraRoleDtoInterface dto = getDto(fieldList, data, i);
			// ユーザ追加ロール情報が取得できなかった場合
			if (dto == null) {
				// 処理無し
				continue;
			}
			// ユーザID及び有効日を取得
			String userId = dto.getUserId();
			Date activateDate = dto.getActivateDate();
			// ユーザ追加ロール情報群(キー：有効日)を取得
			Map<Date, Set<UserExtraRoleDtoInterface>> activateDateMap = map.get(userId);
			// ユーザ追加ロール情報群(キー：有効日)を取得できなかった場合
			if (activateDateMap == null) {
				// ユーザ追加ロール情報群(キー：有効日)を作成
				activateDateMap = new LinkedHashMap<Date, Set<UserExtraRoleDtoInterface>>();
				// ユーザ追加ロール情報群(キー：有効日)を設定
				map.put(userId, activateDateMap);
			}
			// ユーザ追加ロール情報群を取得
			Set<UserExtraRoleDtoInterface> dtos = activateDateMap.get(activateDate);
			// ユーザ追加ロール情報群を取得できなかった場合
			if (dtos == null) {
				// ユーザ追加ロール情報群を作成
				dtos = new LinkedHashSet<UserExtraRoleDtoInterface>();
				// ユーザ追加ロール情報群を設定
				activateDateMap.put(activateDate, dtos);
			}
			// ユーザ追加ロール情報群にユーザ追加ロール情報を追加
			dtos.add(dto);
		}
		// ユーザ追加ロール情報群を取得
		return map;
	}
	
	/**
	 * ユーザ追加ロール情報を取得する。<br>
	 * インポートフィールド情報リストに従い、登録情報リストからユーザ追加ロール情報に変換する。<br>
	 * インポート不能登録情報が存在した場合は、MosP処理情報にエラーメッセージを残す。<br>
	 * インポート不能登録情報の場合は、nullを返す。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @param row       行インデックス
	 * @return ユーザ情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected UserExtraRoleDtoInterface getDto(List<ImportFieldDtoInterface> fieldList, String[] data, int row)
			throws MospException {
		// 登録情報の内容を取得(登録情報に含まれない場合はnull)
		String userId = getFieldValue(PlatformFileConst.FIELD_USER_ID, fieldList, data);
		Date activateDate = getDateFieldValue(PlatformFileConst.FIELD_ACTIVATE_DATE, fieldList, data);
		String roleType = getFieldValue(PlatformFileConst.FIELD_USER_ROLE_TYPE, fieldList, data);
		String roleCode = getFieldValue(PlatformFileConst.FIELD_USER_ROLE_CODE, fieldList, data);
		// 初期ユーザ追加ロール情報を取得
		UserExtraRoleDtoInterface dto = regist.getInitDto();
		// ユーザ追加ロール情報に登録情報の内容を設定
		dto.setUserId(userId);
		dto.setActivateDate(activateDate);
		dto.setRoleType(roleType);
		dto.setRoleCode(roleCode);
		// 必要に応じてユーザ情報を作成
		createUser(userId, activateDate);
		// ユーザ追加ロール情報の妥当性を確認
		regist.validate(dto, row);
		// ユーザ追加ロール情報を取得
		return dto;
	}
	
	/**
	 * 必要に応じてユーザ情報を作成する。<br>
	 * ユーザID及び有効日でユーザ情報が存在する場合は、作成しない。<br>
	 * 有効日以前で対象ユーザの有効なユーザ情報が存在する場合、
	 * 有効日以前で最新の有効なユーザ情報を有効日のユーザ情報として登録する。<br>
	 * @param userId       ユーザ情報
	 * @param activateDate 有効日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void createUser(String userId, Date activateDate) throws MospException {
		// ユーザ情報群(履歴)を取得
		List<UserMasterDtoInterface> users = userRefer.getUserHistory(userId);
		// ユーザID及び有効日でユーザ情報が存在する場合
		if (MospUtility.isEmpty(PlatformUtility.getDto(users, activateDate)) == false) {
			// 処理無し
			return;
		}
		// 有効日以前で最新の有効なユーザ情報を取得
		UserMasterDtoInterface dto = PlatformUtility.getLatestActiveDto(users, activateDate);
		// 有効日以前で最新の有効なユーザ情報が存在しない場合
		if (MospUtility.isEmpty(dto)) {
			// 処理無し
			return;
		}
		// 有効日以前で最新の有効なユーザ情報の有効日を取得
		Date latestActivateDate = dto.getActivateDate();
		// ユーザ情報の有効日を変更
		dto.setActivateDate(activateDate);
		// ユーザ情報を履歴追加(ユーザ追加ロール情報群は別途コピー)
		accountRegist.add(dto, Collections.emptySet());
		// ユーザ追加ロール情報をコピー(履歴追加)
		regist.copy(userId, latestActivateDate, activateDate);
	}
	
}
