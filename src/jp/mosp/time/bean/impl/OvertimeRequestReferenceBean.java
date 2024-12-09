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
import jp.mosp.time.bean.OvertimeRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 残業申請参照処理。<br>
 */
public class OvertimeRequestReferenceBean extends TimeBean implements OvertimeRequestReferenceBeanInterface {
	
	/**
	 * 残業申請DAOクラス。<br>
	 */
	protected OvertimeRequestDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public OvertimeRequestReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(OvertimeRequestDaoInterface.class);
	}
	
	@Override
	public List<OvertimeRequestDtoInterface> getOvertimeRequestList(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		return dao.findForTerm(personalId, firstDate, lastDate);
	}
	
	@Override
	public Map<String, Set<OvertimeRequestDtoInterface>> getOvertimeRequests(Collection<String> personalIds,
			Date requestDate) throws MospException {
		return PlatformUtility.getPersonalIdMap(dao.findForPersonalIds(personalIds, requestDate));
	}
	
	@Override
	public Map<Date, List<OvertimeRequestDtoInterface>> getOvertimeRequests(String personalId, Date firstDate,
			Date lastDate) throws MospException {
		return TimeUtility.getRequestDatesMap(dao.findForTerm(personalId, firstDate, lastDate));
	}
	
	@Override
	public OvertimeRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate, int overtimeType)
			throws MospException {
		return dao.findForKeyOnWorkflow(personalId, requestDate, overtimeType);
	}
	
	@Override
	public OvertimeRequestDtoInterface findForWorkflow(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public OvertimeRequestDtoInterface findForKey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto == null) {
			return null;
		}
		return (OvertimeRequestDtoInterface)dto;
	}
	
	@Override
	public void chkBasicInfo(String personalId, Date targetDate) throws MospException {
		// 勤怠基本情報確認
		initial(personalId, targetDate, TimeConst.CODE_FUNCTION_OVER_WORK);
	}
	
}
