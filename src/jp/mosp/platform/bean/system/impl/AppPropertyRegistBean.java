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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.system.AppPropertyRegistBeanInterface;
import jp.mosp.platform.dao.system.AppPropertyDaoInterface;
import jp.mosp.platform.dto.system.AppPropertyDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmAppPropertyDto;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * アプリケーション設定情報登録処理。<br>
 */
public class AppPropertyRegistBean extends PlatformBean implements AppPropertyRegistBeanInterface {
	
	/**
	 * アプリケーション設定マスタDAO。<br>
	 */
	protected AppPropertyDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AppPropertyRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(AppPropertyDaoInterface.class);
	}
	
	@Override
	public void regist(String appKey, String appValue) throws MospException {
		// アプリケーション設定情報を作成
		AppPropertyDtoInterface dto = new PfmAppPropertyDto();
		// 値を設定
		dto.setAppKey(appKey);
		dto.setAppValue(appValue);
		// 登録情報の妥当性を確認
		validate(dto, null);
		// 登録情報が妥当でない場合
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// アプリケーション設定情報キーからアプリケーション設定情報を取得
		AppPropertyDtoInterface current = dao.findForKey(appKey).orElse(null);
		// アプリケーション設定情報が取得できた場合
		if (current != null) {
			// アプリケーション設定値が同じである場合
			if (MospUtility.isEqual(appValue, current.getAppValue())) {
				// 処理不要
				return;
			}
			// 論理削除
			logicalDelete(dao, current.getRecordId());
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmAppPropertyId(dao.nextRecordId());
		// 登録
		dao.insert(dto);
	}
	
	@Override
	public void delete(String appKey) throws MospException {
		// 論理削除
		logicalDelete(dao, dao.findForKey(appKey).map(dto -> dto.getRecordId()).orElse(Long.MIN_VALUE));
	}
	
	/**
	 * 登録情報の妥当性を確認する。
	 * @param dto アプリケーション設定情報
	 * @param row 対象行インデックス
	 */
	protected void validate(AppPropertyDtoInterface dto, Integer row) {
		// DTOの値を取得
		String appKey = dto.getAppKey();
		int deleteFlag = dto.getDeleteFlag();
		// 名称を取得
		String nameAppKey = PfNameUtility.appKey(mospParams);
		// 必須確認(アプリケーション設定キー)
		checkRequired(appKey, nameAppKey, row);
		// 型確認(削除フラグ)
		checkDeleteFlag(deleteFlag, row);
	}
	
}
