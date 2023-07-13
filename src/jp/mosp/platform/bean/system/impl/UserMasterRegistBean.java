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
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.portal.UserCheckBeanInterface;
import jp.mosp.platform.bean.system.RoleReferenceBeanInterface;
import jp.mosp.platform.bean.system.UserMasterRegistBeanInterface;
import jp.mosp.platform.dao.system.UserMasterDaoInterface;
import jp.mosp.platform.dto.system.UserMasterDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmUserDto;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * ユーザマスタ登録クラス。
 */
public class UserMasterRegistBean extends PlatformBean implements UserMasterRegistBeanInterface {
	
	/**
	 * ユーザID項目長。<br>
	 */
	protected static final int				LEN_USER_ID	= 50;
	
	/**
	 * ユーザマスタDAOクラス。<br>
	 */
	protected UserMasterDaoInterface		dao;
	
	/**
	 * ロール参照クラス。<br>
	 */
	protected RoleReferenceBeanInterface	roleRefer;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public UserMasterRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAO準備
		dao = createDaoInstance(UserMasterDaoInterface.class);
		// Bean準備
		roleRefer = createBeanInstance(RoleReferenceBeanInterface.class);
	}
	
	@Override
	public UserMasterDtoInterface getInitDto() {
		return new PfmUserDto();
	}
	
	@Override
	public void insert(UserMasterDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmUserId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void add(UserMasterDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴追加情報の検証
		checkAdd(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmUserId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(UserMasterDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfmUserId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfmUserId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(UserMasterDtoInterface dto) throws MospException {
		// 削除情報の検証
		checkDelete(dto);
		// 削除できない場合
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfmUserId());
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(UserMasterDtoInterface dto) throws MospException {
		// ユーザIDを取得
		String userId = dto.getUserId();
		// ユーザIDでユーザ情報を取得できる場合
		if (dao.findForHistory(dto.getUserId()).isEmpty() == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorUserIdExist(mospParams, userId);
		}
	}
	
	/**
	 * 履歴追加時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkAdd(UserMasterDtoInterface dto) throws MospException {
		// 対象レコードの有効日が重複していないかを確認
		checkDuplicateAdd(dao.findForKey(dto.getUserId(), dto.getActivateDate()));
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(UserMasterDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmUserId());
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 削除対象ユーザを設定している社員がいないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(UserMasterDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfmUserId());
	}
	
	/**
	 * ユーザ妥当性確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUser(UserMasterDtoInterface dto) throws MospException {
		// ユーザ確認クラス取得
		UserCheckBeanInterface userCheck = (UserCheckBeanInterface)createBean(UserCheckBeanInterface.class);
		// ロール存在確認
		userCheck.checkRoleExist(dto.getRoleCode(), dto.getActivateDate());
		// ユーザ社員確認
		userCheck.checkUserEmployee(dto.getPersonalId(), dto.getActivateDate());
	}
	
	@Override
	public void validate(UserMasterDtoInterface dto, Integer row) throws MospException {
		// エラーメッセージ用の名称を取得
		String nameUserId = PfNameUtility.userId(mospParams);
		// 無効フラグ確認
		if (isDtoActivate(dto)) {
			// ユーザ妥当性確認
			checkUser(dto);
		}
		// 必須確認(ユーザID)
		checkRequired(dto.getUserId(), nameUserId, row);
		// コード + 記号確認
		checkUserId(dto.getUserId(), nameUserId, row);
		// 必須確認(有効日)
		checkRequired(dto.getActivateDate(), PfNameUtility.activateDate(mospParams), row);
		// 桁数確認(ユーザID)
		checkLength(dto.getUserId(), LEN_USER_ID, nameUserId, row);
		// ロール存在確認
		checkRole(dto.getRoleCode(), dto.getActivateDate(), row);
	}
	
	/**
	 * ロール存在確認を行う。<br>
	 * @param roleCode   ロールコード
	 * @param targetDate 対象日
	 * @param row        行インデックス
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkRole(String roleCode, Date targetDate, Integer row) throws MospException {
		// ロール配列取得
		String[][] aryRole = roleRefer.getSelectArray(targetDate, false);
		// ロール存在確認
		for (String[] role : aryRole) {
			if (role[0].equals(roleCode)) {
				return;
			}
		}
		// ロールが存在しない場合のメッセージを追加
		PfMessageUtility.addErrorRoleCodeNotExist(mospParams, roleCode, row);
	}
	
}
