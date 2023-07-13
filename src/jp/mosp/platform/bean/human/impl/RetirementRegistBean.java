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
package jp.mosp.platform.bean.human.impl;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.RetirementRegistBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.bean.system.UserAccountRegistBeanInterface;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.human.EntranceDaoInterface;
import jp.mosp.platform.dao.human.RetirementDaoInterface;
import jp.mosp.platform.dto.human.EntranceDtoInterface;
import jp.mosp.platform.dto.human.RetirementDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanRetirementDto;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 人事退職情報登録クラス。
 */
public class RetirementRegistBean extends PlatformHumanBean implements RetirementRegistBeanInterface {
	
	/**
	 * 退職理由詳細項目長。<br>
	 */
	protected static final int					LEN_RETIREMENT_DETAIL	= 120;
	
	/**
	 * 人事退職情報DAOクラス。<br>
	 */
	protected RetirementDaoInterface			dao;
	
	/**
	 * ユーザアカウント情報登録処理。<br>
	 */
	protected UserAccountRegistBeanInterface	accountRegist;
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public RetirementRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(RetirementDaoInterface.class);
		// Beanを準備
		accountRegist = createBeanInstance(UserAccountRegistBeanInterface.class);
	}
	
	@Override
	public RetirementDtoInterface getInitDto() {
		return new PfaHumanRetirementDto();
	}
	
	@Override
	public void regist(RetirementDtoInterface dto) throws MospException {
		// レコード識別ID確認
		if (dto.getPfaHumanRetirementId() == 0) {
			// 新規登録
			insert(dto);
		} else {
			// 更新
			update(dto);
		}
		// ユーザアカウント情報登録後の確認
		accountRegist.checkAfterRegist();
	}
	
	@Override
	public void delete(RetirementDtoInterface dto) throws MospException {
		// 削除情報の検証
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanRetirementId());
		// ユーザアカウント情報登録後の確認
		accountRegist.checkAfterRegist();
	}
	
	@Override
	public void validate(RetirementDtoInterface dto, Integer row) {
		// 必須確認(退職日)
		checkRequired(dto.getRetirementDate(), PfNameUtility.retirementDate(mospParams), row);
		// 桁数チェック(退職理由詳細)
		checkLength(dto.getRetirementDetail(), LEN_RETIREMENT_DETAIL,
				PfNameUtility.retirementReasonDetail(mospParams), row);
		// 退職理由存在確認
		checkRetirementReason(dto.getRetirementReason(), row);
	}
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void insert(RetirementDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaHumanRetirementId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 更新を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public void update(RetirementDtoInterface dto) throws MospException {
		// DTOの妥当性確認
		validate(dto, null);
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPfaHumanRetirementId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPfaHumanRetirementId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(RetirementDtoInterface dto) throws MospException {
		// 対象レコードが重複していないかを確認
		checkDuplicateInsert(dao.findForInfo(dto.getPersonalId()));
		// 社員妥当性確認
		checkEmployee(dto);
	}
	
	/**
	 * 更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(RetirementDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanRetirementId());
		// 社員妥当性確認
		checkEmployee(dto);
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(RetirementDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPfaHumanRetirementId());
	}
	
	/**
	 * 社員妥当性確認を行う。<br>
	 * 入社情報の確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkEmployee(RetirementDtoInterface dto) throws MospException {
		// 入社情報DAO準備
		EntranceDaoInterface entranceDao = createDaoInstance(EntranceDaoInterface.class);
		// 入社情報取得
		EntranceDtoInterface entranceDto = entranceDao.findForInfo(dto.getPersonalId());
		// 入社情報確認(入社情報が存在しない、或いは退職より後に入社)
		if (entranceDto == null || entranceDto.getEntranceDate().after(dto.getRetirementDate())) {
			// 社員が入社していない場合のメッセージを追加
			PfMessageUtility.addErrorEmployeeNotJoin(mospParams);
		}
	}
	
	/**
	 * 退職理由存在確認を行う。<br>
	 * @param retirementReason 退職理由コード
	 * @param row              行インデックス
	 */
	protected void checkRetirementReason(String retirementReason, Integer row) {
		// 退職理由配列取得
		String[][] aryReason = mospParams.getProperties().getCodeArray(PlatformConst.CODE_KEY_RETIREMENT, false);
		// 退職理由存在確認
		for (String[] reason : aryReason) {
			if (reason[0].equals(retirementReason)) {
				return;
			}
		}
		// 退職理由が存在しない場合のメッセージを追加
		PfMessageUtility.addErrorNotExist(mospParams, PfNameUtility.retirementReason(mospParams),
				retirementReason, row);
	}
	
}
