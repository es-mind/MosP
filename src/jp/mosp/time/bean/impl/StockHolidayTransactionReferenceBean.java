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
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.StockHolidayTransactionReferenceBeanInterface;
import jp.mosp.time.dao.settings.StockHolidayTransactionDaoInterface;
import jp.mosp.time.dto.settings.StockHolidayTransactionDtoInterface;

/**
 * ストック休暇手動付与情報参照処理。
 */
public class StockHolidayTransactionReferenceBean extends PlatformBean
		implements StockHolidayTransactionReferenceBeanInterface {
	
	/**
	 * ストック休暇トランザクションDAO。
	 */
	private StockHolidayTransactionDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public StockHolidayTransactionReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(StockHolidayTransactionDaoInterface.class);
	}
	
	@Override
	public StockHolidayTransactionDtoInterface getStockHolidayTransactionInfo(String personalId, Date targetDate,
			Date acquisitionDate) throws MospException {
		return dao.findForInfo(personalId, targetDate, acquisitionDate);
	}
	
	@Override
	public StockHolidayTransactionDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException {
		return dao.findForKey(personalId, activateDate, acquisitionDate);
	}
	
	@Override
	public List<StockHolidayTransactionDtoInterface> findForList(String personalId, Date acquisitionDate,
			Date startDate, Date endDate) throws MospException {
		return dao.findForList(personalId, acquisitionDate, startDate, endDate);
	}
	
}
