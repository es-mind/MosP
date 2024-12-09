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
package jp.mosp.platform.bean.message.impl;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.message.MessageRegistBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterCheckBeanInterface;
import jp.mosp.platform.dao.message.MessageDaoInterface;
import jp.mosp.platform.dto.message.MessageDtoInterface;
import jp.mosp.platform.dto.message.impl.PftMessageDto;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * メッセージテーブル登録クラス。
 */
public class MessageRegistBean extends PlatformBean implements MessageRegistBeanInterface {
	
	/**
	 * メッセージテーブルDAO。
	 */
	protected MessageDaoInterface				dao;
	
	/**
	 * 所属・雇用契約・職位・勤務地マスタに関連する整合性確認インターフェース。
	 */
	protected PlatformMasterCheckBeanInterface	masterCheck;
	
	/**
	 * メッセージNoフォーマット。
	 */
	protected static final String				FORMAT_MESSAGE_NO	= "0000000000";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public MessageRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(MessageDaoInterface.class);
		masterCheck = createBeanInstance(PlatformMasterCheckBeanInterface.class);
	}
	
	@Override
	public MessageDtoInterface getInitDto() {
		return new PftMessageDto();
	}
	
	@Override
	public void insert(MessageDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// メッセージNo発行
		dto.setMessageNo(issueSequenceNo(dao.getMaxMessageNo(), FORMAT_MESSAGE_NO));
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPftMessageId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(MessageDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 履歴更新情報の検証
		checkUpdate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPftMessageId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPftMessageId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void delete(MessageDtoInterface dto) throws MospException {
		// 削除情報の検証
		checkDelete(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 論理削除
		logicalDelete(dao, dto.getPftMessageId());
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * 適用範囲の所属・職位・雇用契約区分・勤務地情報が、<br>
	 * 公開期間の間存在するか確認。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkInsert(MessageDtoInterface dto) throws MospException {
		// マスタの存在確認
		masterCheck.isCheckEmploymentContract(dto.getEmploymentContractCode(), dto.getStartDate(), dto.getEndDate());
		masterCheck.isCheckPosition(dto.getPositionCode(), dto.getStartDate(), dto.getEndDate());
		masterCheck.isCheckSection(dto.getSectionCode(), dto.getStartDate(), dto.getEndDate());
		masterCheck.isCheckWorkPlace(dto.getWorkPlaceCode(), dto.getStartDate(), dto.getEndDate());
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * 適用範囲の所属・職位・雇用契約区分・勤務地情報が、<br>
	 * 公開期間の間存在するか確認。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkUpdate(MessageDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPftMessageId());
		// マスタの存在確認
		masterCheck.isCheckEmploymentContract(dto.getEmploymentContractCode(), dto.getStartDate(), dto.getEndDate());
		masterCheck.isCheckPosition(dto.getPositionCode(), dto.getStartDate(), dto.getEndDate());
		masterCheck.isCheckSection(dto.getSectionCode(), dto.getStartDate(), dto.getEndDate());
		masterCheck.isCheckWorkPlace(dto.getWorkPlaceCode(), dto.getStartDate(), dto.getEndDate());
	}
	
	/**
	 * 削除時の確認処理を行う。<br>
	 * 対象レコード識別IDのデータが削除されていないかの確認を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	protected void checkDelete(MessageDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPftMessageId());
	}
	
	/**
	 * 登録情報の妥当性を確認確認する。
	 * @param dto 対象DTO
	 */
	protected void validate(MessageDtoInterface dto) {
		// 公開開始日及び公開終了日の前後確認
		if (checkDateOrder(dto.getStartDate(), dto.getEndDate(), true) == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorOrderInvalid(mospParams, PfNameUtility.publishEndDate(mospParams),
					PfNameUtility.publishStartDate(mospParams));
		}
	}
	
}
