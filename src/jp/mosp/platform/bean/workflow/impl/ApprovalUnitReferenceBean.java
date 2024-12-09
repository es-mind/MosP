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

import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.ApprovalUnitReferenceBeanInterface;
import jp.mosp.platform.dao.workflow.ApprovalUnitDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;

/**
 * 承認ユニットマスタ参照クラス。
 */
public class ApprovalUnitReferenceBean extends PlatformBean implements ApprovalUnitReferenceBeanInterface {
	
	/**
	 * 承認ユニットマスタDAO。
	 */
	protected ApprovalUnitDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ApprovalUnitReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(ApprovalUnitDaoInterface.class);
	}
	
	@Override
	public ApprovalUnitDtoInterface findForKey(String unitCode, Date activateDate) throws MospException {
		return dao.findForKey(unitCode, activateDate);
		
	}
	
	@Override
	public ApprovalUnitDtoInterface getApprovalUnitInfo(String unitCode, Date targetDate) throws MospException {
		return dao.findForInfo(unitCode, targetDate);
		
	}
	
	@Override
	public String[][] getCodedSelectArray(Date targetDate, boolean needBlank) throws MospException {
		// 一覧取得
		List<ApprovalUnitDtoInterface> list = dao.findForActivateDate(targetDate);
		// 一覧件数確認
		if (list.size() == 0) {
			// 対象データ無し
			return getNoObjectDataPulldown();
		}
		// プルダウン用配列及びインデックス準備
		String[][] array = prepareSelectArray(list.size(), needBlank);
		int idx = needBlank ? 1 : 0;
		// コードの最大文字数確認
		int codeLength = 0;
		for (ApprovalUnitDtoInterface dto : list) {
			if (dto.getUnitCode().length() > codeLength) {
				codeLength = dto.getUnitCode().length();
			}
		}
		// 配列作成
		for (ApprovalUnitDtoInterface dto : list) {
			array[idx][0] = dto.getUnitCode();
			array[idx][1] = getCodedName(dto.getUnitCode(), dto.getUnitName(), codeLength);
			idx++;
		}
		return array;
	}
	
	@Override
	public String getUnitName(String unitCode, Date targetDate) throws MospException {
		ApprovalUnitDtoInterface dto = getApprovalUnitInfo(unitCode, targetDate);
		if (dto == null) {
			return unitCode;
		}
		return dto.getUnitName();
	}
	
	@Override
	public List<ApprovalUnitDtoInterface> getApprovalUnitHistory(String unitCode) throws MospException {
		return dao.findForHistory(unitCode);
	}
	
	@Override
	public Set<String> getUnitSetForPersonalId(String personalId, Date targetDate) throws MospException {
		return getUnitSet(dao.findForApproverPersonalId(personalId, targetDate));
	}
	
	@Override
	public Set<String> getUnitSetForMaster(String sectionCode, String positionCode, Date targetDate)
			throws MospException {
		return getUnitSet(dao.findForApproverSection(sectionCode, positionCode, targetDate));
	}
	
	/**
	 * 承認ユニットコード群を取得する。<br>
	 * @param list 承認ユニット情報リスト
	 * @return 承認ユニットコード群
	 */
	protected Set<String> getUnitSet(List<ApprovalUnitDtoInterface> list) {
		// 承認ユニットコード群を準備
		Set<String> set = new HashSet<String>();
		// 承認者個人IDから承認ユニットマスタリストを取得
		for (ApprovalUnitDtoInterface dto : list) {
			set.add(dto.getUnitCode());
		}
		return set;
	}
	
	@Override
	public boolean hasPersonalUnit(String personalId, Date startDate, Date endDate) throws MospException {
		// 個人IDが設定されている、有効日の範囲内で情報を取得
		List<ApprovalUnitDtoInterface> list = dao.findPersonTerm(personalId, startDate, endDate);
		// リスト確認
		if (list.isEmpty()) {
			return false;
		}
		// 期間内に個人で設定されているユニットが存在したら
		return true;
	}
	
}
