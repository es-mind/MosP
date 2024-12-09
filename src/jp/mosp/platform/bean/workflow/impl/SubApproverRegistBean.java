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
package jp.mosp.platform.bean.workflow.impl;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.bean.workflow.SubApproverRegistBeanInterface;
import jp.mosp.platform.dao.workflow.SubApproverDaoInterface;
import jp.mosp.platform.dto.workflow.SubApproverDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PftSubApproverDto;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 代理承認者テーブル登録クラス。
 */
public class SubApproverRegistBean extends PlatformHumanBean implements SubApproverRegistBeanInterface {
	
	/**
	 * 代理承認者テーブルDAO。
	 */
	protected SubApproverDaoInterface	dao;
	
	/**
	 * メッセージNoフォーマット。
	 */
	protected static final String		FORMAT_MESSAGE_NO	= "0000000000";
	
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public SubApproverRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(SubApproverDaoInterface.class);
	}
	
	@Override
	public SubApproverDtoInterface getInitDto() {
		return new PftSubApproverDto();
	}
	
	@Override
	public void insert(SubApproverDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		checkInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 代理承認者登録No発行
		dto.setSubApproverNo(issueSequenceNo(dao.getMaxMessageNo(), FORMAT_MESSAGE_NO));
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPftSubApproverId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	@Override
	public void update(SubApproverDtoInterface dto) throws MospException {
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
		logicalDelete(dao, dto.getPftSubApproverId());
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPftSubApproverId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkInsert(SubApproverDtoInterface dto) throws MospException {
		// 代理設定重複確認
		checkSubApproverDuplicate(dto);
	}
	
	/**
	 * 履歴更新時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkUpdate(SubApproverDtoInterface dto) throws MospException {
		// 対象レコード識別IDのデータが削除されていないかを確認
		checkExclusive(dao, dto.getPftSubApproverId());
		// 代理設定重複確認
		checkSubApproverDuplicate(dto);
	}
	
	/**
	 * 代理設定重複確認を行う。<br>
	 * 個人IDで同一期間内に同じワークフロー区分のレコードが2つあってはならない。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void checkSubApproverDuplicate(SubApproverDtoInterface dto) throws MospException {
		// 期間内における代理設定情報リストを取得
		List<SubApproverDtoInterface> list = dao.findForTerm(dto.getPersonalId(), dto.getWorkflowType(),
				dto.getStartDate(), dto.getEndDate());
		// 情報の存在を確認
		if (list.size() == 0) {
			return;
		}
		// 自情報確認(履歴更新時の自情報は重複対象外)
		if (list.size() == 1 && list.get(0).getSubApproverNo().equals(dto.getSubApproverNo())) {
			return;
		}
		// エラーメッセージを設定
		PfMessageUtility.addErrorTermOverlap(mospParams, PfNameUtility.substitute(mospParams));
	}
	
	/**
	 * 登録情報の妥当性を確認確認する。
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	protected void validate(SubApproverDtoInterface dto) throws MospException {
		// 開始日及び終了日の前後確認
		if (checkDateOrder(dto.getStartDate(), dto.getEndDate(), true) == false) {
			// エラーメッセージを設定
			PfMessageUtility.addErrorOrderInvalid(mospParams, PfNameUtility.substituteEndDate(mospParams),
					PfNameUtility.substituteStartDate(mospParams));
		}
	}
	
}
