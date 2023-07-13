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
import jp.mosp.time.dao.settings.WorkOnHolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.WorkOnHolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdWorkOnHolidayRequestDto;

/**
 * 振出・休出申請DAOクラス。<br>
 */
public class TmdWorkOnHolidayRequestDao extends PlatformDao implements WorkOnHolidayRequestDaoInterface {
	
	/**
	 * 休日出勤申請。
	 */
	public static final String		TABLE								= "tmd_work_on_holiday_request";
	/**
	 * レコード識別ID
	 */
	public static final String		COL_TMD_WORK_ON_HOLIDAY_REQUEST_ID	= "tmd_work_on_holiday_request_id";
	/**
	 * 個人ID
	 */
	public static final String		COL_PERSONAL_ID						= "personal_id";
	/**
	 * 出勤日
	 */
	public static final String		COL_REQUEST_DATE					= "request_date";
	/**
	 * 勤務回数
	 */
	public static final String		COL_TIMES_WORK						= "times_work";
	/**
	 * 休出種別
	 */
	public static final String		COL_WORK_ON_HOLIDAY_TYPE			= "work_on_holiday_type";
	/**
	 * 振替申請
	 */
	public static final String		COL_SUBSTITUTE						= "substitute";
	/**
	 * 勤務形態コード
	 */
	public static final String		COL_WORK_TYPE_CODE					= "work_type_code";
	/**
	 * 出勤予定時刻
	 */
	public static final String		COL_START_TIME						= "start_time";
	/**
	 * 退勤予定時刻
	 */
	public static final String		COL_END_TIME						= "end_time";
	/**
	 * 理由
	 */
	public static final String		COL_REQUEST_REASON					= "request_reason";
	/**
	 * ワークフロー番号
	 */
	public static final String		COL_WORKFLOW						= "workflow";
	
	/**
	 * キー。
	 */
	public static final String		KEY_1								= COL_TMD_WORK_ON_HOLIDAY_REQUEST_ID;
	
	private WorkflowDaoInterface	workflowDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdWorkOnHolidayRequestDao() {
	}
	
	@Override
	public void initDao() throws MospException {
		workflowDao = (WorkflowDaoInterface)loadDao(WorkflowDaoInterface.class);
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdWorkOnHolidayRequestDto dto = new TmdWorkOnHolidayRequestDto();
		dto.setTmdWorkOnHolidayRequestId(getLong(COL_TMD_WORK_ON_HOLIDAY_REQUEST_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setRequestDate(getDate(COL_REQUEST_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setWorkOnHolidayType(getString(COL_WORK_ON_HOLIDAY_TYPE));
		dto.setSubstitute(getInt(COL_SUBSTITUTE));
		dto.setWorkTypeCode(getString(COL_WORK_TYPE_CODE));
		dto.setStartTime(getTimestamp(COL_START_TIME));
		dto.setEndTime(getTimestamp(COL_END_TIME));
		dto.setRequestReason(getString(COL_REQUEST_REASON));
		dto.setWorkflow(getLong(COL_WORKFLOW));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<WorkOnHolidayRequestDtoInterface> mappingAll() throws MospException {
		List<WorkOnHolidayRequestDtoInterface> all = new ArrayList<WorkOnHolidayRequestDtoInterface>();
		while (next()) {
			all.add((WorkOnHolidayRequestDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<WorkOnHolidayRequestDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			String personalId = String.valueOf(param.get("personalId"));
			String substitute = String.valueOf(param.get("substitute"));
			Date requestStartDate = (Date)param.get("requestStartDate");
			Date requestEndDate = (Date)param.get("requestEndDate");
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
			if (!substitute.equals("")) {
				sb.append(and());
				sb.append(equal(COL_SUBSTITUTE));
			}
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, requestStartDate);
			setParam(index++, requestEndDate);
			if (!substitute.equals("")) {
				setParam(index++, Integer.parseInt(substitute));
			}
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
	public List<WorkOnHolidayRequestDtoInterface> findForPersonalIds(Collection<String> personalIds, Date requestDate)
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			WorkOnHolidayRequestDtoInterface dto = (WorkOnHolidayRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdWorkOnHolidayRequestId());
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
			WorkOnHolidayRequestDtoInterface dto = (WorkOnHolidayRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdWorkOnHolidayRequestId());
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
		WorkOnHolidayRequestDtoInterface dto = (WorkOnHolidayRequestDtoInterface)baseDto;
		setParam(index++, dto.getTmdWorkOnHolidayRequestId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getRequestDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getWorkOnHolidayType());
		setParam(index++, dto.getSubstitute());
		setParam(index++, dto.getWorkTypeCode());
		setParam(index++, dto.getStartTime(), true);
		setParam(index++, dto.getEndTime(), true);
		setParam(index++, dto.getRequestReason());
		setParam(index++, dto.getWorkflow());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public WorkOnHolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate)
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
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualWithdrawn());
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, requestDate);
			executeQuery();
			WorkOnHolidayRequestDtoInterface dto = null;
			if (next()) {
				dto = (WorkOnHolidayRequestDtoInterface)mapping();
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
	public WorkOnHolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException {
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
			WorkOnHolidayRequestDtoInterface dto = null;
			if (next()) {
				dto = (WorkOnHolidayRequestDtoInterface)mapping();
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
	public List<WorkOnHolidayRequestDtoInterface> findForList(String personalId, Date startDate, Date endDate)
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
	
}
