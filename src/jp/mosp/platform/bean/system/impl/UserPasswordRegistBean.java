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

import java.util.Date;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.bean.portal.UserCheckBeanInterface;
import jp.mosp.platform.bean.system.UserPasswordRegistBeanInterface;
import jp.mosp.platform.dao.system.UserPasswordDaoInterface;
import jp.mosp.platform.dto.system.UserPasswordDtoInterface;
import jp.mosp.platform.dto.system.impl.PfaUserPasswordDto;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * ユーザパスワード情報登録クラス。
 */
public class UserPasswordRegistBean extends PlatformBean implements UserPasswordRegistBeanInterface {
	
	/**
	 * パスワード項目長(DBフィールド)。<br>
	 */
	protected static final int				LEN_PASSWORD	= 50;
	
	/**
	 * ユーザパスワード情報DAOクラス。<br>
	 */
	protected UserPasswordDaoInterface		dao;
	
	/**
	 * ユーザ確認処理。<br>
	 */
	protected UserCheckBeanInterface		userCheck;
	
	/**
	 * パスワード確認処理。<br>
	 */
	protected PasswordCheckBeanInterface	passwordCheck;
	
	
	/**
	 * コンストラクタ。
	 */
	public UserPasswordRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(UserPasswordDaoInterface.class);
		// Beanを準備
		userCheck = createBeanInstance(UserCheckBeanInterface.class);
		passwordCheck = createBeanInstance(PasswordCheckBeanInterface.class);
	}
	
	@Override
	public UserPasswordDtoInterface getInitDto() {
		return new PfaUserPasswordDto();
	}
	
	@Override
	public void regist(UserPasswordDtoInterface dto) throws MospException {
		// 論理削除
		delete(dto.getUserId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaUserPasswordId(dao.nextRecordId());
		// 登録
		dao.insert(dto);
	}
	
	@Override
	public void regist(String userId, Date changeDate, String password) throws MospException {
		// ユーザパスワード情報を準備
		UserPasswordDtoInterface dto = getInitDto();
		// ユーザパスワード情報に値を設定
		dto.setUserId(userId);
		dto.setChangeDate(changeDate);
		dto.setPassword(password);
		// 登録
		regist(dto);
	}
	
	@Override
	public void delete(String userId) throws MospException {
		// 現在のユーザパスワード情報取得
		UserPasswordDtoInterface dto = dao.findForInfo(userId);
		// 存在確認
		if (dto != null) {
			// 論理削除
			logicalDelete(dao, dto.getPfaUserPasswordId());
		}
	}
	
	@Override
	public void initPassword(Set<String> userIdList) throws MospException {
		// 変更日(システム日付)を準備
		Date changeDate = getSystemDate();
		// 更新処理
		for (String userId : userIdList) {
			// パスワードを初期化
			initPassword(userId, changeDate);
		}
	}
	
	@Override
	public void initPassword(String userId, Date changeDate) throws MospException {
		// ユーザパスワード情報を準備
		UserPasswordDtoInterface dto = getInitDto();
		// ユーザID及び変更日を設定
		dto.setUserId(userId);
		dto.setChangeDate(changeDate);
		// 初期パスワードを設定
		dto.setPassword(getInitialPassword(userId));
		// 登録
		regist(dto);
	}
	
	/**
	 * 初期パスワードを取得する。<br>
	 * @param userId ユーザID
	 * @return 初期パスワード
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected String getInitialPassword(String userId) throws MospException {
		// 初期パスワードを取得
		return passwordCheck.getEncryptedInitialPassword(userId);
	}
	
	@Override
	public void validate(UserPasswordDtoInterface dto, Integer row) throws MospException {
		// エラーメッセージ用の名称を取得
		String nameUserId = PfNameUtility.userId(mospParams);
		String nameChangeDate = PfNameUtility.changeDate(mospParams);
		String namePassword = PfNameUtility.password(mospParams);
		// 必須確認(ユーザID)
		checkRequired(dto.getUserId(), nameUserId, row);
		// 必須確認(変更日)
		checkRequired(dto.getChangeDate(), nameChangeDate, row);
		// 必須確認(パスワード)
		checkRequired(dto.getPassword(), namePassword, row);
		// 桁数確認(パスワード)
		checkLength(dto.getPassword(), LEN_PASSWORD, namePassword, row);
		// ユーザ存在確認
		userCheck.checkUserExist(dto.getUserId(), row);
	}
	
}
