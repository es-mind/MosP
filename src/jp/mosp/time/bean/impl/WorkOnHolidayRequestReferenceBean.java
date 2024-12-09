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

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.WorkOnHolidayRequestReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;

/**
 * 振出・休出申請参照処理。<br>
 */
public class WorkOnHolidayRequestReferenceBean extends TimeBean implements WorkOnHolidayRequestReferenceBeanInterface {
	
	/**
	 * MosPアプリケーション設定キー(半日振替出勤利用設定)。
	 */
	protected static final String				APP_USE_HALF_SUBSTITUTE	= "UseHalfSubstitute";
	
	/**
	 * 振出・休出申請DAOクラス。<br>
	 */
	protected WorkOnHolidayRequestDaoInterface	dao;
	
	
	/**
	 * {@link TimeBean#TimeBean()}を実行する。<br>
	 */
	public WorkOnHolidayRequestReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(WorkOnHolidayRequestDaoInterface.class);
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate)
			throws MospException {
		return dao.findForKeyOnWorkflow(personalId, requestDate);
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface findForKey(long id) throws MospException {
		BaseDto dto = findForKey(dao, id, false);
		if (dto != null) {
			return (WorkOnHolidayRequestDtoInterface)dto;
		}
		return null;
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public List<WorkOnHolidayRequestDtoInterface> getWorkOnHolidayRequestList(String personalId, Date firstDate,
			Date lastDate) throws MospException {
		return dao.findForList(personalId, firstDate, lastDate);
	}
	
	@Override
	public List<WorkOnHolidayRequestDtoInterface> getWorkOnHolidayRequests(Collection<String> personalIds,
			Date requestDate) throws MospException {
		return dao.findForPersonalIds(personalIds, requestDate);
	}
	
	@Override
	public void chkBasicInfo(String personalId, Date targetDate) throws MospException {
		// 勤怠基本情報確認
		initial(personalId, targetDate, TimeConst.CODE_FUNCTION_WORK_HOLIDAY);
	}
	
	@Override
	public boolean useHalfSubstitute() {
		// 半日振替出勤利用設定取得
		return mospParams.getApplicationPropertyBool(APP_USE_HALF_SUBSTITUTE);
	}
}
