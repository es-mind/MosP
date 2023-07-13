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
import java.util.Optional;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.bean.PaidHolidayDataReferenceBeanInterface;
import jp.mosp.time.dao.settings.PaidHolidayDataDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;

/**
 * 有給休暇情報参照処理。<br>
 */
public class PaidHolidayDataReferenceBean extends PlatformBean implements PaidHolidayDataReferenceBeanInterface {
	
	/**
	 * 有給休暇情報DAO。
	 */
	private PaidHolidayDataDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public PaidHolidayDataReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(PaidHolidayDataDaoInterface.class);
	}
	
	@Override
	public PaidHolidayDataDtoInterface findForKey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto == null) {
			return null;
		}
		return (PaidHolidayDataDtoInterface)dto;
	}
	
	@Override
	public PaidHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException {
		return dao.findForKey(personalId, activateDate, acquisitionDate);
	}
	
	@Override
	public PaidHolidayDataDtoInterface getPaidHolidayDataInfo(String personalId, Date targetDate, Date acquisitionDate)
			throws MospException {
		// 取得日で有給休暇情報リストを取得し対象日以前で最新の有効な情報を取得
		return PlatformUtility.getLatestActiveDto(dao.findForHistory(personalId, acquisitionDate), targetDate);
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> getPaidHolidayDatas(String personalId, Date targetDate,
			Date acquisitionDate, Date limitDate) throws MospException {
		// 有給休暇情報リストを取得
		return dao.findForList(personalId, targetDate, acquisitionDate, limitDate);
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> getPaidHolidayDataForDate(String personalId, Date targetDate)
			throws MospException {
		// 有給休暇情報リストを取得
		return dao.findForList(personalId, targetDate, targetDate, targetDate);
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> getPaidHolidayDataForTerm(Date firstDate, Date lastDate)
			throws MospException {
		// 有給休暇情報リストを取得(個人ID：指定なし、対象日：期間最終日)
		return dao.findForList(null, lastDate, lastDate, firstDate);
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> getPaidHolidayDataForLimit(String personalId, Date limitDate)
			throws MospException {
		return dao.findForLimit(personalId, limitDate);
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> findForNextInfoList(String personalId, Date targetDate)
			throws MospException {
		return dao.findForNextInfoList(personalId, targetDate);
	}
	
	@Override
	public Optional<PaidHolidayDataDtoInterface> getPaidHolidayDataForExpiration(String personalId, Date targetDate)
			throws MospException {
		return Optional.ofNullable(dao.findForExpirationDateInfo(personalId, targetDate));
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> getExpiredPaidHolidayDatas(String personalId, Date limitDate)
			throws MospException {
		return dao.findForExpirationDateList(personalId, limitDate);
	}
	
}
