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
package jp.mosp.time.dao.settings.impl;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.time.dao.settings.SubHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdSubHolidayRequestDto;

/**
 * 代休申請DAOクラス。
 */
public class TmdSubHolidayRequestDao extends PlatformDao implements SubHolidayRequestDaoInterface {
	
	/**
	 * 代休申請。
	 */
	public static final String		TABLE							= "tmd_sub_holiday_request";
	
	/**
	 * レコード識別ID。
	 */
	public static final String		COL_TMD_SUB_HOLIDAY_REQUEST_ID	= "tmd_sub_holiday_request_id";
	
	/**
	 * 個人ID。
	 */
	public static final String		COL_PERSONAL_ID					= "personal_id";
	
	/**
	 * 代休日。
	 */
	public static final String		COL_REQUEST_DATE				= "request_date";
	
	/**
	 * 休暇範囲。
	 */
	public static final String		COL_HOLIDAY_RANGE				= "holiday_range";
	
	/**
	 * 出勤日。
	 */
	public static final String		COL_WORK_DATE					= "work_date";
	
	/**
	 * 勤務回数。
	 */
	public static final String		COL_TIMES_WORK					= "times_work";
	
	/**
	 * 代休種別。
	 */
	public static final String		COL_WORK_DATE_SUB_HOLIDAY_TYPE	= "work_date_sub_holiday_type";
	
	/**
	 * ワークフロー番号。
	 */
	public static final String		COL_WORKFLOW					= "workflow";
	
	/**
	 * キー。
	 */
	public static final String		KEY_1							= COL_TMD_SUB_HOLIDAY_REQUEST_ID;
	
	private WorkflowDaoInterface	workflowDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdSubHolidayRequestDao() {
	}
	
	@Override
	public void initDao() throws MospException {
		workflowDao = (WorkflowDaoInterface)loadDao(WorkflowDaoInterface.class);
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdSubHolidayRequestDto dto = new TmdSubHolidayRequestDto();
		dto.setTmdSubHolidayRequestId(getLong(COL_TMD_SUB_HOLIDAY_REQUEST_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setRequestDate(getDate(COL_REQUEST_DATE));
		dto.setHolidayRange(getInt(COL_HOLIDAY_RANGE));
		dto.setWorkDate(getDate(COL_WORK_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setWorkDateSubHolidayType(getInt(COL_WORK_DATE_SUB_HOLIDAY_TYPE));
		dto.setWorkflow(getLong(COL_WORKFLOW));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> mappingAll() throws MospException {
		List<SubHolidayRequestDtoInterface> all = new ArrayList<SubHolidayRequestDtoInterface>();
		while (next()) {
			all.add((SubHolidayRequestDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			String personalId = String.valueOf(param.get("personalId"));
			Date requestStartDate = (Date)param.get("requestStartDate");
			Date requestEndDate = (Date)param.get("requestEndDate");
			Date workStartDate = (Date)param.get("workStartDate");
			Date workEndDate = (Date)param.get("workEndDate");
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(greaterEqual(COL_REQUEST_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_REQUEST_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_WORK_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, requestStartDate);
			setParam(index++, requestEndDate);
			setParam(index++, workStartDate);
			setParam(index++, workEndDate);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public SubHolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORKFLOW));
			prepareStatement(sb.toString());
			setParam(index++, workflow);
			executeQuery();
			SubHolidayRequestDtoInterface dto = null;
			if (next()) {
				dto = (SubHolidayRequestDtoInterface)mapping();
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> findForList(String personalId, Date requestDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_REQUEST_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, requestDate);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> findForList(String personalId, Date startDate, Date endDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(greaterEqual(COL_REQUEST_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_REQUEST_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, startDate);
			setParam(index++, endDate);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> findForList(String personalId, Date workDate, int timesWork,
			int workDateSubHolidayType) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			sb.append(and());
			sb.append(equal(COL_TIMES_WORK));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE_SUB_HOLIDAY_TYPE));
			sb.append(getOrderByColumn(COL_REQUEST_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, timesWork);
			setParam(index++, workDateSubHolidayType);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> findForPersonalIds(Collection<String> personalIds, Date requestDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(in(COL_PERSONAL_ID, personalIds.size()));
			sb.append(and());
			sb.append(equal(COL_REQUEST_DATE));
			prepareStatement(sb.toString());
			setParamsStringIn(personalIds);
			setParam(index++, requestDate);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_REQUEST_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_REQUEST_DATE));
			sb.append(getOrderByColumn(COL_REQUEST_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, lastDate, false);
			setParam(index++, firstDate, false);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			SubHolidayRequestDtoInterface dto = (SubHolidayRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdSubHolidayRequestId());
			executeUpdate();
			chkUpdate(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			SubHolidayRequestDtoInterface dto = (SubHolidayRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdSubHolidayRequestId());
			executeUpdate();
			chkDelete(1);
			return cnt;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		SubHolidayRequestDtoInterface dto = (SubHolidayRequestDtoInterface)baseDto;
		setParam(index++, dto.getTmdSubHolidayRequestId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getRequestDate());
		setParam(index++, dto.getHolidayRange());
		setParam(index++, dto.getWorkDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getWorkDateSubHolidayType());
		setParam(index++, dto.getWorkflow());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public SubHolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate, int holidayRange)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_REQUEST_DATE));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_RANGE));
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualWithdrawn());
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, requestDate);
			setParam(index++, holidayRange);
			executeQuery();
			SubHolidayRequestDtoInterface dto = null;
			if (next()) {
				dto = (SubHolidayRequestDtoInterface)mapping();
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<SubHolidayRequestDtoInterface> findForWorkDate(String personalId, Date workDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			executeQuery();
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
