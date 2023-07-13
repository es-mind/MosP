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

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.utils.PlatformUtility;
import jp.mosp.time.base.TimeBean;
import jp.mosp.time.bean.AttendanceReferenceBeanInterface;
import jp.mosp.time.constant.TimeConst;
import jp.mosp.time.dao.settings.AttendanceDaoInterface;
import jp.mosp.time.dto.settings.AttendanceDtoInterface;
import jp.mosp.time.utils.TimeUtility;

/**
 * 勤怠情報参照処理。<br>
 */
public class AttendanceReferenceBean extends TimeBean implements AttendanceReferenceBeanInterface {
	
	/**
	 * 勤怠データDAOクラス。<br>
	 */
	protected AttendanceDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public AttendanceReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		dao = createDaoInstance(AttendanceDaoInterface.class);
	}
	
	@Override
	public List<AttendanceDtoInterface> getAttendanceList(String personalId, Date startDate, Date endDate)
			throws MospException {
		return dao.findForList(personalId, startDate, endDate);
	}
	
	@Override
	public Map<Date, AttendanceDtoInterface> getAttendances(String personalId, Date startDate, Date endDate)
			throws MospException {
		return TimeUtility.getRequestDateMap(dao.findForList(personalId, startDate, endDate));
	}
	
	@Override
	public Map<String, AttendanceDtoInterface> getAttendances(Collection<String> personalIds, Date workDate)
			throws MospException {
		return PlatformUtility.getPersonalIdDtoMap(dao.findForPersonalIds(personalIds, workDate));
	}
	
	@Override
	public AttendanceDtoInterface findForKey(String personalId, Date workDate) throws MospException {
		return dao.findForKey(personalId, workDate, TimeBean.TIMES_WORK_DEFAULT);
	}
	
	@Override
	public AttendanceDtoInterface findForWorkflow(long workflow) throws MospException {
		return dao.findForWorkflow(workflow);
	}
	
	@Override
	public Map<Date, AttendanceDtoInterface> getAppliedForWorkDates(String personalId, Set<Date> workDates)
			throws MospException {
		// 勤務日群に含まれている申請済以上の勤怠情報群(キー：勤務日)(キー昇順)を取得
		return TimeUtility.getRequestDateMap(dao.findForWorkDates(personalId, workDates, false));
	}
	
	@Override
	public Map<Date, AttendanceDtoInterface> getCompletedForWorkDates(String personalId, Set<Date> workDates)
			throws MospException {
		// 勤務日群に含まれている承認済以上の勤怠情報群(キー：勤務日)(キー昇順)を取得
		return TimeUtility.getRequestDateMap(dao.findForWorkDates(personalId, workDates, true));
	}
	
	@Override
	public void chkBasicInfo(String personalId, Date targetDate) throws MospException {
		// 勤怠基本情報確認
		initial(personalId, targetDate, TimeConst.CODE_FUNCTION_WORK_MANGE);
	}
	
}
