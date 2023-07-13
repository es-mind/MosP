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
package jp.mosp.platform.bean.workflow.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.SubApproverReferenceBeanInterface;
import jp.mosp.platform.dao.workflow.SubApproverDaoInterface;
import jp.mosp.platform.dto.workflow.SubApproverDtoInterface;

/**
 * 代理承認者テーブル参照クラス。
 */
public class SubApproverReferenceBean extends PlatformBean implements SubApproverReferenceBeanInterface {
	
	/**
	 * 代理承認者テーブルDAO。
	 */
	protected SubApproverDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SubApproverReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(SubApproverDaoInterface.class);
	}
	
	@Override
	public SubApproverDtoInterface findForKey(String subApproverNo) throws MospException {
		return dao.findForKey(subApproverNo);
	}
	
	@Override
	public List<SubApproverDtoInterface> findForSubApproverId(String subApproverId, int workflowType, Date termStart,
			Date termEnd) throws MospException {
		return dao.findForSubApproverId(subApproverId, workflowType, termStart, termEnd);
	}
	
	@Override
	public boolean hasSubApprover(String personalId, Date startDate, Date endDate, int workflowType)
			throws MospException {
		// 個人IDが設定されている、有効日の範囲内で情報を取得
		List<SubApproverDtoInterface> list = dao.findSubApproverForTerm(personalId, startDate, endDate, workflowType);
		// リスト確認
		if (list.isEmpty()) {
			return false;
		}
		// 期間内全て適用されていたら
		return true;
	}
}
