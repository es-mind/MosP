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

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.SubHolidayRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 代休申請参照処理。<br>
 */
public class SubHolidayRequestReferenceBean extends TimeBean implements SubHolidayRequestReferenceBeanInterface {
	
	/**
	 * 代休申請DAOクラス。<br>
	 */
	protected SubHolidayRequestDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public SubHolidayRequestReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(SubHolidayRequestDaoInterface.class);
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(String personalId, Date requestDate)
			throws MospException {
		return dao.findForList(personalId, requestDate);
	}
	
	@Override
	public SubHolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate, int holidayRange)
			throws MospException {
		return dao.findForKeyOnWorkflow(personalId, requestDate, holidayRange);
	}
	
	@Override
	public SubHolidayRequestDtoInterface findForKey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto != null) {
			return (SubHolidayRequestDtoInterface)dto;
		}
		return null;
	}
	
	@Override
	public SubHolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(String personalId, Date workDate, int timesWork,
			int workDateSubHolidayType) throws MospException {
		return dao.findForList(personalId, workDate, timesWork, workDateSubHolidayType);
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(String personalId, Date firstDate,
			Date lastDate) throws MospException {
		return dao.findForTerm(personalId, firstDate, lastDate);
	}
	
	@Override
	public Map<String, Set<SubHolidayRequestDtoInterface>> getSubHolidayRequests(Collection<String> personalIds,
			Date requestDate) throws MospException {
		return PlatformUtility.getPersonalIdMap(dao.findForPersonalIds(personalIds, requestDate));
	}
	
	@Override
	public Map<Date, List<SubHolidayRequestDtoInterface>> getSubHolidayRequests(String personalId, Date firstDate,
			Date lastDate) throws MospException {
		return TimeUtility.getRequestDatesMap(dao.findForTerm(personalId, firstDate, lastDate));
	}
	
	@Override
	public void chkBasicInfo(String personalId, Date targetDate) throws MospException {
		// 勤怠基本情報確認
		initial(personalId, targetDate, TimeConst.CODE_FUNCTION_COMPENSATORY_HOLIDAY);
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> findForWorkDate(String personalId, Date workDate) throws MospException {
		return dao.findForWorkDate(personalId, workDate);
	}
	
}
