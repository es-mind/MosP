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
/**
 * 
 */
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.PaidHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayTransactionDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;

/**
 * 有給休暇手動付与情報参照処理。<br>
 */
public class PaidHolidayTransactionReferenceBean extends PlatformBean
		implements PaidHolidayTransactionReferenceBeanInterface {
	
	/**
	 * 有給休暇トランザクションDAO。
	 */
	private PaidHolidayTransactionDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayTransactionReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(PaidHolidayTransactionDaoInterface.class);
	}
	
	@Override
	public List<PaidHolidayTransactionDtoInterface> findForList(String personalId, Date activateDate)
			throws MospException {
		return dao.findForList(personalId, activateDate);
	}
	
	@Override
	public List<PaidHolidayTransactionDtoInterface> findForInfoList(String personalId, Date activateDate,
			String inactivateFlag) throws MospException {
		return dao.findForInfoList(personalId, activateDate, inactivateFlag);
	}
	
	@Override
	public List<PaidHolidayTransactionDtoInterface> findForList(String personalId, Date acquisitionDate, Date startDate,
			Date endDate) throws MospException {
		return dao.findForList(personalId, acquisitionDate, startDate, endDate);
	}
	
	@Override
	public boolean hasPersonalApplication(String personalId, Date startDate, Date endDate) throws MospException {
		// 個人IDが設定されている、有効日の範囲内で情報を取得
		List<PaidHolidayTransactionDtoInterface> list = dao.findPersonTerm(personalId, startDate, endDate);
		// リスト確認
		if (list.isEmpty()) {
			return false;
		}
		// 期間内全て適用されていたら
		return true;
	}
	
	@Override
	public List<PaidHolidayTransactionDtoInterface> findForHistoryList(String personalId) throws MospException {
		return dao.findForHistoryList(personalId);
	}
	
	@Override
	public List<PaidHolidayTransactionDtoInterface> findForAcquisitionList(String personalId, Date acquisitionDate)
			throws MospException {
		return dao.findForList(personalId, acquisitionDate, null, null);
	}
	
}
