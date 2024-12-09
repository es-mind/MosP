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
package jp.mosp.platform.bean.human.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.bean.human.HumanBinaryHistoryReferenceBeanInterface;
import jp.mosp.platform.dao.human.HumanBinaryHistoryDaoInterface;
import jp.mosp.platform.dto.human.HumanBinaryHistoryDtoInterface;

/**
 * 人事汎用バイナリ履歴情報参照クラス。
 */
public class HumanBinaryHistoryReferenceBean extends HumanGeneralBean
		implements HumanBinaryHistoryReferenceBeanInterface {
	
	/**
	 * 人事汎用履歴情報DAO。
	 */
	protected HumanBinaryHistoryDaoInterface dao;
	
	
	/**
	 * {@link HumanGeneralBean#HumanGeneralBean()}を実行する。<br>
	 */
	public HumanBinaryHistoryReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 継承基の処理を実行
		super.initBean();
		// DAOを準備
		dao = createDaoInstance(HumanBinaryHistoryDaoInterface.class);
	}
	
	@Override
	public List<HumanBinaryHistoryDtoInterface> findForHistory(String personalId, String humanItemType)
			throws MospException {
		return dao.findForHistory(personalId, humanItemType);
	}
	
	@Override
	public HumanBinaryHistoryDtoInterface findForInfo(String personalId, String humanItemType, Date targetDate)
			throws MospException {
		return dao.findForInfo(personalId, humanItemType, targetDate);
	}
	
	@Override
	public HumanBinaryHistoryDtoInterface findForKey(String personalId, String humanItemType, Date activateDate)
			throws MospException {
		return dao.findForKey(personalId, humanItemType, activateDate);
	}
	
	@Override
	public HumanBinaryHistoryDtoInterface findForKey(Long id, boolean isUpdate) throws MospException {
		return (HumanBinaryHistoryDtoInterface)dao.findForKey(id, isUpdate);
	}
	
	@Override
	public List<HumanBinaryHistoryDtoInterface> findForActivateDate(String personalId, Date activateDate)
			throws MospException {
		return dao.findForActivateDate(personalId, activateDate);
	}
	
}
