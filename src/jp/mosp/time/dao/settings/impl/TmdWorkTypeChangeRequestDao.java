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

import jp.mosp.framework.base.BaseDao;
import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.time.dao.settings.WorkTypeChangeRequestDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeChangeRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdWorkTypeChangeRequestDto;

/**
 * 勤務形態変更申請DAOクラス。
 */
public class TmdWorkTypeChangeRequestDao extends BaseDao implements WorkTypeChangeRequestDaoInterface {
	
	/**
	 * 勤務形態変更申請。
	 */
	public static final String		TABLE								= "tmd_work_type_change_request";
	
	/**
	 * レコード識別ID。
	 */
	public static final String		COL_TMD_WORK_TYPE_CHANGE_REQUEST_ID	= "tmd_work_type_change_request_id";
	
	/**
	 * 個人ID。
	 */
	public static final String		COL_PERSONAL_ID						= "personal_id";
	
	/**
	 * 出勤日。
	 */
	public static final String		COL_REQUEST_DATE					= "request_date";
	
	/**
	 * 勤務回数。
	 */
	public static final String		COL_TIMES_WORK						= "times_work";
	
	/**
	 * 勤務形態コード。
	 */
	public static final String		COL_WORK_TYPE_CODE					= "work_type_code";
	
	/**
	 * 理由。
	 */
	public static final String		COL_REQUEST_REASON					= "request_reason";
	
	/**
	 * ワークフロー番号。
	 */
	public static final String		COL_WORKFLOW						= "workflow";
	
	/**
	 * キー。
	 */
	public static final String		KEY_1								= COL_TMD_WORK_TYPE_CHANGE_REQUEST_ID;
	
	private WorkflowDaoInterface	workflowDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdWorkTypeChangeRequestDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() throws MospException {
		workflowDao = (WorkflowDaoInterface)loadDao(WorkflowDaoInterface.class);
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdWorkTypeChangeRequestDto dto = new TmdWorkTypeChangeRequestDto();
		dto.setTmdWorkTypeChangeRequestId(getLong(COL_TMD_WORK_TYPE_CHANGE_REQUEST_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setRequestDate(getDate(COL_REQUEST_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setWorkTypeCode(getString(COL_WORK_TYPE_CODE));
		dto.setRequestReason(getString(COL_REQUEST_REASON));
		dto.setWorkflow(getLong(COL_WORKFLOW));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<WorkTypeChangeRequestDtoInterface> mappingAll() throws MospException {
		List<WorkTypeChangeRequestDtoInterface> all = new ArrayList<WorkTypeChangeRequestDtoInterface>();
		while (next()) {
			all.add((WorkTypeChangeRequestDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<WorkTypeChangeRequestDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
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
			if (!workTypeCode.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_WORK_TYPE_CODE));
			}
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, requestStartDate);
			setParam(index++, requestEndDate);
			if (!workTypeCode.isEmpty()) {
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
	public WorkTypeChangeRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate)
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
			WorkTypeChangeRequestDtoInterface dto = null;
			if (next()) {
				dto = (WorkTypeChangeRequestDtoInterface)mapping();
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
	public WorkTypeChangeRequestDtoInterface findForWorkflow(long workflow) throws MospException {
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
			WorkTypeChangeRequestDtoInterface dto = null;
			if (next()) {
				dto = (WorkTypeChangeRequestDtoInterface)mapping();
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
	public List<WorkTypeChangeRequestDtoInterface> findForPersonalIds(Collection<String> personalIds, Date workDate)
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
	
	@Override
	public List<WorkTypeChangeRequestDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate)
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
			WorkTypeChangeRequestDtoInterface dto = (WorkTypeChangeRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdWorkTypeChangeRequestId());
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
			WorkTypeChangeRequestDtoInterface dto = (WorkTypeChangeRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdWorkTypeChangeRequestId());
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
		WorkTypeChangeRequestDtoInterface dto = (WorkTypeChangeRequestDtoInterface)baseDto;
		setParam(index++, dto.getTmdWorkTypeChangeRequestId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getRequestDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getWorkTypeCode());
		setParam(index++, dto.getRequestReason());
		setParam(index++, dto.getWorkflow());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
}
