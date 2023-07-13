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

import java.io.InputStream;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.file.PlatformFileBean;
import jp.mosp.platform.bean.file.UserPasswordImportBeanInterface;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.bean.system.UserPasswordRegistBeanInterface;
import jp.mosp.platform.constant.PlatformFileConst;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;

/**
 * ユーザパスワード情報インポートクラス。
 */
public class UserPasswordImportBean extends PlatformFileBean implements UserPasswordImportBeanInterface {
	
	/**
	 * ユーザパスワード情報登録クラス。<br>
	 */
	protected UserPasswordRegistBeanInterface	passwordRegist;
	
	/**
	 * パスワード確認処理。<br>
	 */
	protected PasswordCheckBeanInterface		passwordCheck;
	
	
	/**
	 * {@link PlatformFileBean#PlatformFileBean()}を実行する。<br>
	 */
	public UserPasswordImportBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// Bean準備
		passwordRegist = createBeanInstance(UserPasswordRegistBeanInterface.class);
		passwordCheck = createBeanInstance(PasswordCheckBeanInterface.class);
	}
	
	@Override
	public int importFile(ImportDtoInterface importDto, InputStream requestedFile) throws MospException {
		// アップロードファイルを登録情報リストに変換
		List<String[]> dataList = getDataList(importDto, requestedFile);
		// インポートフィールド情報リストを取得
		List<ImportFieldDtoInterface> fieldList = getImportFieldList(importDto.getImportCode());
		// インポート処理
		return importFile(fieldList, dataList);
	}
	
	@Override
	public int importFile(List<ImportFieldDtoInterface> fieldList, List<String[]> dataList) throws MospException {
		// 登録情報リストをユーザパスワード情報(DTO)リストに変換
		List<UserPasswordDtoInterface> targetList = getTargetList(fieldList, dataList);
		// 行インデックス宣言
		int row = 0;
		// ユーザパスワード情報(DTO)情報毎に登録
		for (UserPasswordDtoInterface dto : targetList) {
			// ユーザパスワード情報妥当性確認(ユーザ存在確認含む)
			passwordRegist.validate(dto, row++);
			// エラー確認
			if (mospParams.hasErrorMessage()) {
				continue;
			}
			// ユーザパスワード情報登録
			passwordRegist.regist(dto);
		}
		// 登録件数取得
		return targetList.size();
	}
	
	/**
	 * ユーザパスワード情報(DTO)リストを取得する。<br>
	 * インポートマスタ情報に従い、登録情報リストの内容をDTOに変換する。<br>
	 * <br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param dataList  登録情報リスト
	 * @return ユーザパスワード情報(DTO)リスト
	 * @throws MospException パスワードの作成に失敗した場合
	 */
	protected List<UserPasswordDtoInterface> getTargetList(List<ImportFieldDtoInterface> fieldList,
			List<String[]> dataList) throws MospException {
		// ユーザパスワード情報(DTO)リスト準備
		List<UserPasswordDtoInterface> targetList = new ArrayList<UserPasswordDtoInterface>();
		// 登録情報リスト内の各登録情報長を確認
		checkCsvLength(fieldList, dataList);
		// エラー確認
		if (mospParams.hasErrorMessage()) {
			return targetList;
		}
		// 登録情報リスト毎に処理
		for (int i = 0; i < dataList.size(); i++) {
			// 登録情報取得
			String[] data = dataList.get(i);
			// ユーザパスワード情報(DTO)取得及び確認
			UserPasswordDtoInterface dto = getUserPasswordDto(fieldList, data);
			// ユーザパスワード情報(DTO)リストに追加
			targetList.add(dto);
		}
		return targetList;
	}
	
	/**
	 * インポートフィールド情報リストに従い、登録情報の内容をユーザパスワード情報(DTO)のフィールドに設定する。<br>
	 * @param fieldList インポートフィールド情報リスト
	 * @param data      登録情報
	 * @return ユーザパスワード情報(DTO)
	 * @throws MospException パスワードの作成に失敗した場合
	 */
	protected UserPasswordDtoInterface getUserPasswordDto(List<ImportFieldDtoInterface> fieldList, String[] data)
			throws MospException {
		// ユーザパスワード情報(DTO)準備
		UserPasswordDtoInterface dto = getInitDto();
		// 登録情報の内容を取得(登録情報に含まれない場合はnull)
		String userId = getFieldValue(PlatformFileConst.FIELD_USER_ID, fieldList, data);
		Date changeDate = getDateFieldValue(PlatformFileConst.FIELD_CHANGE_DATE, fieldList, data);
		String password = getFieldValue(PlatformFileConst.FIELD_PASSWORD, fieldList, data);
		// ユーザID確認
		userId = userId == null ? "" : userId;
		// パスワード作成
		password = MospUtility.isEmpty(password) ? "" : passwordCheck.encryptPlain(password);
		// ユーザパスワード情報(DTO)に登録情報の内容を設定
		dto.setUserId(userId);
		dto.setChangeDate(changeDate);
		dto.setPassword(password);
		return dto;
	}
	
	/**
	 * ユーザパスワード情報(DTO)を取得する。<br>
	 * @return ユーザパスワード情報(DTO)
	 */
	protected UserPasswordDtoInterface getInitDto() {
		// ユーザパスワード情報(DTO)取得
		return passwordRegist.getInitDto();
	}
	
}
