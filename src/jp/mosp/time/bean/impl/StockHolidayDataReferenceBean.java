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
package jp.mosp.time.bean.impl;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.time.bean.StockHolidayDataReferenceBeanInterface;
import jp.mosp.time.dao.settings.StockHolidayDataDaoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;

/**
 * ストック休暇情報参照処理。<br>
 */
public class StockHolidayDataReferenceBean extends PlatformBean implements StockHolidayDataReferenceBeanInterface {
	
	/**
	 * ストック休暇情報DAO。
	 */
	protected StockHolidayDataDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public StockHolidayDataReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(StockHolidayDataDaoInterface.class);
	}
	
	@Override
	public StockHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException {
		return dao.findForKey(personalId, activateDate, acquisitionDate);
	}
	
	@Override
	public StockHolidayDataDtoInterface getStockHolidayDataInfo(String personalId, Date targetDate,
			Date acquisitionDate) throws MospException {
		return dao.findForInfo(personalId, targetDate, acquisitionDate);
	}
	
	@Override
	public List<StockHolidayDataDtoInterface> getStockHolidayDatas(String personalId, Date targetDate,
			Date acquisitionDate, Date limitDate) throws MospException {
		// ストック休暇情報リストを取得
		return dao.findForList(personalId, targetDate, acquisitionDate, limitDate);
	}
	
	@Override
	public List<StockHolidayDataDtoInterface> getStockHolidayDataForDate(String personalId, Date targetDate)
			throws MospException {
		// ストック休暇情報リストを取得
		return dao.findForList(personalId, targetDate, targetDate, targetDate);
	}
	
}
