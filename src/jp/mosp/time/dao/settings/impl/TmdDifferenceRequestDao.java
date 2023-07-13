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

import java.sql.Time;
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
import jp.mosp.time.dao.settings.DifferenceRequestDaoInterface;
import jp.mosp.time.dto.settings.DifferenceRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdDifferenceRequestDto;

/**
 * 時差出勤申請DAOクラス。
 */
public class TmdDifferenceRequestDao extends PlatformDao implements DifferenceRequestDaoInterface {
	
	/**
	 * 時差出勤申請。
	 */
	public static final String		TABLE							= "tmd_difference_request";
	
	/**
	 * レコード識別ID。
	 */
	public static final String		COL_TMD_DIFFERENCE_REQUEST_ID	= "tmd_difference_request_id";
	
	/**
	 * 個人ID。
	 */
	public static final String		COL_PERSONAL_ID					= "personal_id";
	
	/**
	 * 時差出勤日。
	 */
	public static final String		COL_REQUEST_DATE				= "request_date";
	
	/**
	 * 勤務回数。
	 */
	public static final String		COL_TIMES_WORK					= "times_work";
	
	/**
	 * 時差出勤区分。
	 */
	public static final String		COL_DIFFERENCE_TYPE				= "difference_type";
	
	/**
	 * 勤務形態コード。
	 */
	public static final String		COL_WORK_TYPE_CODE				= "work_type_code";
	
	/**
	 * 開始日。
	 */
	public static final String		COL_START_DATE					= "start_date";
	
	/**
	 * 時差出勤開始時刻。
	 */
	public static final String		COL_REQUEST_START				= "request_start";
	
	/**
	 * 時差出勤終了時刻。
	 */
	public static final String		COL_REQUEST_END					= "request_end";
	
	/**
	 * 理由。
	 */
	public static final String		COL_REQUEST_REASON				= "request_reason";
	
	/**
	 * ワークフロー番号。
	 */
	public static final String		COL_WORKFLOW					= "workflow";
	
	/**
	 * キー。
	 */
	public static final String		KEY_1							= COL_TMD_DIFFERENCE_REQUEST_ID;
	
	private WorkflowDaoInterface	workflowDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdDifferenceRequestDao() {
	}
	
	@Override
	public void initDao() throws MospException {
		workflowDao = (WorkflowDaoInterface)loadDao(WorkflowDaoInterface.class);
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdDifferenceRequestDto dto = new TmdDifferenceRequestDto();
		dto.setTmdDifferenceRequestId(getLong(COL_TMD_DIFFERENCE_REQUEST_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setRequestDate(getDate(COL_REQUEST_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setDifferenceType(getString(COL_DIFFERENCE_TYPE));
		dto.setWorkTypeCode(getString(COL_WORK_TYPE_CODE));
		dto.setStartDate(getInt(COL_START_DATE));
		dto.setRequestStart(getTimestamp(COL_REQUEST_START));
		dto.setRequestEnd(getTimestamp(COL_REQUEST_END));
		dto.setRequestReason(getString(COL_REQUEST_REASON));
		dto.setWorkflow(getLong(COL_WORKFLOW));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<DifferenceRequestDtoInterface> mappingAll() throws MospException {
		List<DifferenceRequestDtoInterface> all = new ArrayList<DifferenceRequestDtoInterface>();
		while (next()) {
			all.add((DifferenceRequestDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<DifferenceRequestDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			String personalId = String.valueOf(param.get("personalId"));
			String workTypeCode = String.valueOf(param.get("workTypeCode"));
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
			if (!workTypeCode.equals("")) {
				sb.append(and());
				sb.append(equal(COL_WORK_TYPE_CODE));
			}
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, requestStartDate);
			setParam(index++, requestEndDate);
			if (!workTypeCode.equals("")) {
				setParam(index++, workTypeCode);
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			DifferenceRequestDtoInterface dto = (DifferenceRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdDifferenceRequestId());
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
			DifferenceRequestDtoInterface dto = (DifferenceRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdDifferenceRequestId());
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
		DifferenceRequestDtoInterface dto = (DifferenceRequestDtoInterface)baseDto;
		setParam(index++, dto.getTmdDifferenceRequestId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getRequestDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getDifferenceType());
		setParam(index++, dto.getWorkTypeCode());
		setParam(index++, dto.getStartDate());
		setParam(index++, new Time(dto.getRequestStart().getTime()), true);
		setParam(index++, new Time(dto.getRequestEnd().getTime()), true);
		setParam(index++, dto.getRequestReason());
		setParam(index++, dto.getWorkflow());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public List<DifferenceRequestDtoInterface> findForList(String personalId, Date startDate, Date endDate)
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
	public DifferenceRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate)
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
			DifferenceRequestDtoInterface dto = null;
			if (next()) {
				dto = (DifferenceRequestDtoInterface)mapping();
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
	public DifferenceRequestDtoInterface findForWorkflow(long workflow) throws MospException {
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
			DifferenceRequestDtoInterface dto = null;
			if (next()) {
				dto = (DifferenceRequestDtoInterface)mapping();
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
	public List<DifferenceRequestDtoInterface> findForPersonalIds(Collection<String> personalIds, Date workDate)
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
