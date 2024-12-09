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
/**
 * 
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
import jp.mosp.time.dao.settings.OvertimeRequestDaoInterface;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdOvertimeRequestDto;

/**
 * 残業申請DAOクラス。
 */
public class TmdOvertimeRequestDao extends PlatformDao implements OvertimeRequestDaoInterface {
	
	/**
	 * 残業申請。
	 */
	public static final String		TABLE						= "tmd_overtime_request";
	
	/**
	 * レコード識別ID。
	 */
	public static final String		COL_TMD_OVERTIME_REQUEST_ID	= "tmd_overtime_request_id";
	
	/**
	 * 個人ID。
	 */
	public static final String		COL_PERSONAL_ID				= "personal_id";
	
	/**
	 * 申請日。
	 */
	public static final String		COL_REQUEST_DATE			= "request_date";
	
	/**
	 * 勤務回数。
	 */
	public static final String		COL_TIMES_WORK				= "times_work";
	
	/**
	 * 残業区分。
	 */
	public static final String		COL_OVERTIME_TYPE			= "overtime_type";
	
	/**
	 * 申請時間。
	 */
	public static final String		COL_REQUEST_TIME			= "request_time";
	
	/**
	 * 理由。
	 */
	public static final String		COL_REQUEST_REASON			= "request_reason";
	
	/**
	 * ワークフロー番号。
	 */
	public static final String		COL_WORKFLOW				= "workflow";
	
	/**
	 * キー。
	 */
	public static final String		KEY_1						= COL_TMD_OVERTIME_REQUEST_ID;
	
	private WorkflowDaoInterface	workflowDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdOvertimeRequestDao() {
	}
	
	@Override
	public void initDao() throws MospException {
		workflowDao = (WorkflowDaoInterface)loadDao(WorkflowDaoInterface.class);
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdOvertimeRequestDto dto = new TmdOvertimeRequestDto();
		dto.setTmdOvertimeRequestId(getLong(COL_TMD_OVERTIME_REQUEST_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setRequestDate(getDate(COL_REQUEST_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setOvertimeType(getInt(COL_OVERTIME_TYPE));
		dto.setRequestTime(getInt(COL_REQUEST_TIME));
		dto.setRequestReason(getString(COL_REQUEST_REASON));
		dto.setWorkflow(getLong(COL_WORKFLOW));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<OvertimeRequestDtoInterface> mappingAll() throws MospException {
		List<OvertimeRequestDtoInterface> all = new ArrayList<OvertimeRequestDtoInterface>();
		while (next()) {
			all.add((OvertimeRequestDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<OvertimeRequestDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			String personalId = String.valueOf(param.get("personalId"));
			String overtimeType = String.valueOf(param.get("overtimeType"));
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
			if (!overtimeType.equals("")) {
				sb.append(and());
				sb.append(equal(COL_OVERTIME_TYPE));
			}
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, requestStartDate);
			setParam(index++, requestEndDate);
			if (!overtimeType.equals("")) {
				setParam(index++, Integer.parseInt(overtimeType));
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
	public List<OvertimeRequestDtoInterface> findForList(String personalId, Date requestDate) throws MospException {
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
	public List<OvertimeRequestDtoInterface> findForList(String personalId, Date startDate, Date endDate)
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
	public List<OvertimeRequestDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate)
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
			sb.append(getOrderByColumn(COL_REQUEST_DATE, COL_OVERTIME_TYPE));
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
	public List<OvertimeRequestDtoInterface> findForPersonalIds(Collection<String> personalIds, Date requestDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(in(COL_PERSONAL_ID, personalIds.size()));
			sb.append(and());
			sb.append(equal(COL_REQUEST_DATE));
			sb.append(getOrderByColumn(COL_REQUEST_DATE, COL_OVERTIME_TYPE));
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
			OvertimeRequestDtoInterface dto = (OvertimeRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdOvertimeRequestId());
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
			OvertimeRequestDtoInterface dto = (OvertimeRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdOvertimeRequestId());
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
		OvertimeRequestDtoInterface dto = (OvertimeRequestDtoInterface)baseDto;
		setParam(index++, dto.getTmdOvertimeRequestId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getRequestDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getOvertimeType());
		setParam(index++, dto.getRequestTime());
		setParam(index++, dto.getRequestReason());
		setParam(index++, dto.getWorkflow());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public OvertimeRequestDtoInterface findForWorkflow(long workflow) throws MospException {
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
			OvertimeRequestDtoInterface dto = null;
			if (next()) {
				dto = (OvertimeRequestDtoInterface)mapping();
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
	public OvertimeRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate, int overtimeType)
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
			sb.append(equal(COL_OVERTIME_TYPE));
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualWithdrawn());
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, requestDate);
			setParam(index++, overtimeType);
			executeQuery();
			OvertimeRequestDtoInterface dto = null;
			if (next()) {
				dto = (OvertimeRequestDtoInterface)mapping();
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
