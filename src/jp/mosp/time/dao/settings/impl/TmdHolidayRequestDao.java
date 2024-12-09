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
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.platform.dao.workflow.impl.PftWorkflowDao;
import jp.mosp.time.dao.settings.HolidayRequestDaoInterface;
import jp.mosp.time.dto.settings.HolidayRequestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdHolidayRequestDto;

/**
 * 休暇申請データDAOクラス。
 */
public class TmdHolidayRequestDao extends PlatformDao implements HolidayRequestDaoInterface {
	
	/**
	 * 休暇申請データ。
	 */
	public static final String		TABLE							= "tmd_holiday_request";
	
	/**
	 * レコード識別ID。
	 */
	public static final String		COL_TMD_HOLIDAY_REQUEST_ID		= "tmd_holiday_request_id";
	
	/**
	 * 個人ID。
	 */
	public static final String		COL_PERSONAL_ID					= "personal_id";
	
	/**
	 * 申請開始日。
	 */
	public static final String		COL_REQUEST_START_DATE			= "request_start_date";
	
	/**
	 * 申請終了日。
	 */
	public static final String		COL_REQUEST_END_DATE			= "request_end_date";
	
	/**
	 * 休暇種別1。
	 */
	public static final String		COL_HOLIDAY_TYPE1				= "holiday_type1";
	
	/**
	 * 休暇種別2。
	 */
	public static final String		COL_HOLIDAY_TYPE2				= "holiday_type2";
	
	/**
	 * 休暇範囲。
	 */
	public static final String		COL_HOLIDAY_RANGE				= "holiday_range";
	
	/**
	 * 時休開始時刻。
	 */
	public static final String		COL_START_TIME					= "start_time";
	
	/**
	 * 時休終了時刻。
	 */
	public static final String		COL_END_TIME					= "end_time";
	
	/**
	 * 休暇取得日。
	 */
	public static final String		COL_HOLIDAY_ACQUISITION_DATE	= "holiday_acquisition_date";
	
	/**
	 * 使用日数。
	 */
	public static final String		COL_USE_DAY						= "use_day";
	
	/**
	 * 使用時間数。
	 */
	public static final String		COL_USE_HOUR					= "use_hour";
	
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
	public static final String		KEY_1							= COL_TMD_HOLIDAY_REQUEST_ID;
	
	/**
	 * ワークフローDAO。
	 * サブクエリ作成用
	 */
	protected WorkflowDaoInterface	workflowDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdHolidayRequestDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() throws MospException {
		workflowDao = (WorkflowDaoInterface)loadDao(WorkflowDaoInterface.class);
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdHolidayRequestDto dto = new TmdHolidayRequestDto();
		dto.setTmdHolidayRequestId(getLong(COL_TMD_HOLIDAY_REQUEST_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setRequestStartDate(getDate(COL_REQUEST_START_DATE));
		dto.setRequestEndDate(getDate(COL_REQUEST_END_DATE));
		dto.setHolidayType1(getInt(COL_HOLIDAY_TYPE1));
		dto.setHolidayType2(getString(COL_HOLIDAY_TYPE2));
		dto.setHolidayRange(getInt(COL_HOLIDAY_RANGE));
		dto.setStartTime(getTimestamp(COL_START_TIME));
		dto.setEndTime(getTimestamp(COL_END_TIME));
		dto.setHolidayAcquisitionDate(getDate(COL_HOLIDAY_ACQUISITION_DATE));
		dto.setUseDay(getDouble(COL_USE_DAY));
		dto.setUseHour(getInt(COL_USE_HOUR));
		dto.setRequestReason(getString(COL_REQUEST_REASON));
		dto.setWorkflow(getLong(COL_WORKFLOW));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<HolidayRequestDtoInterface> mappingAll() throws MospException {
		List<HolidayRequestDtoInterface> all = new ArrayList<HolidayRequestDtoInterface>();
		while (next()) {
			all.add((HolidayRequestDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<HolidayRequestDtoInterface> findForApprovedList(String personalId, Date acquisitionDate,
			int holidayType1, String holidayType2, Date requestDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_ACQUISITION_DATE));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE1));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE2));
			sb.append(and());
			sb.append(lessEqual(COL_REQUEST_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_REQUEST_END_DATE));
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(select());
			sb.append(PftWorkflowDao.COL_WORKFLOW);
			sb.append(from(PftWorkflowDao.TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(PftWorkflowDao.COL_WORKFLOW_STATUS);
			sb.append(" = '");
			sb.append(PlatformConst.CODE_STATUS_COMPLETE);
			sb.append("'");
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, acquisitionDate);
			setParam(index++, holidayType1);
			setParam(index++, holidayType2);
			setParam(index++, requestDate);
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
	public List<HolidayRequestDtoInterface> findForApprovedList(String personalId, Date acquisitionDate,
			int holidayType1, String holidayType2, Date requestStartDate, Date requestEndDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			if (MospUtility.isEmpty(acquisitionDate) == false) {
				sb.append(and());
				sb.append(equal(COL_HOLIDAY_ACQUISITION_DATE));
			}
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE1));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE2));
			sb.append(and());
			sb.append(lessEqual(COL_REQUEST_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_REQUEST_END_DATE));
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(select());
			sb.append(PftWorkflowDao.COL_WORKFLOW);
			sb.append(from(PftWorkflowDao.TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(PftWorkflowDao.COL_WORKFLOW_STATUS);
			sb.append(" = '");
			sb.append(PlatformConst.CODE_STATUS_COMPLETE);
			sb.append("'");
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			if (MospUtility.isEmpty(acquisitionDate) == false) {
				setParam(index++, acquisitionDate);
			}
			setParam(index++, holidayType1);
			setParam(index++, holidayType2);
			setParam(index++, requestEndDate);
			setParam(index++, requestStartDate);
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
	public List<HolidayRequestDtoInterface> findForRequestList(String personalId, Date acquisitionDate,
			int holidayType1, String holidayType2, Date startDate, Date endDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_ACQUISITION_DATE));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE1));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE2));
			sb.append(and());
			sb.append(lessEqual(COL_REQUEST_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_REQUEST_END_DATE));
			sb.append(and());
			sb.append(workflowDao.getSubQueryForApplied(COL_WORKFLOW));
			sb.append(
					getOrderByColumns(COL_REQUEST_START_DATE, COL_REQUEST_END_DATE, COL_START_TIME, COL_HOLIDAY_RANGE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, acquisitionDate);
			setParam(index++, holidayType1);
			setParam(index++, holidayType2);
			setParam(index++, endDate);
			setParam(index++, startDate);
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
	public List<HolidayRequestDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		String personalId = String.valueOf(param.get("personalId"));
		String holidayType1 = String.valueOf(param.get("holidayType1"));
		String holidayType2 = String.valueOf(param.get("holidayType2"));
		String holidayLength = String.valueOf(param.get("holidayLength"));
		Date requestStartDate = (Date)param.get("requestStartDate");
		Date requestEndDate = (Date)param.get("requestEndDate");
		index = 1;
		StringBuffer sb = getSelectQuery(getClass());
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(equal(COL_PERSONAL_ID));
		sb.append(and());
		sb.append(lessEqual(COL_REQUEST_START_DATE));
		sb.append(and());
		sb.append(greaterEqual(COL_REQUEST_END_DATE));
		if (!holidayType1.equals("")) {
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE1));
		}
		if (!holidayType2.equals("")) {
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE2));
		}
		if (!holidayLength.equals("")) {
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_RANGE));
		}
		prepareStatement(sb.toString());
		setParam(index++, personalId);
		setParam(index++, requestEndDate);
		setParam(index++, requestStartDate);
		if (!holidayType1.equals("")) {
			setParam(index++, Integer.parseInt(holidayType1));
		}
		if (!holidayType2.equals("")) {
			setParam(index++, holidayType2);
		}
		if (!holidayLength.equals("")) {
			setParam(index++, Integer.parseInt(holidayLength));
		}
		executeQuery();
		return mappingAll();
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			HolidayRequestDtoInterface dto = (HolidayRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdHolidayRequestId());
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
			HolidayRequestDtoInterface dto = (HolidayRequestDtoInterface)baseDto;
			setParam(index++, dto.getTmdHolidayRequestId());
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
		HolidayRequestDtoInterface dto = (HolidayRequestDtoInterface)baseDto;
		setParam(index++, dto.getTmdHolidayRequestId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getRequestStartDate());
		setParam(index++, dto.getRequestEndDate());
		setParam(index++, dto.getHolidayType1());
		setParam(index++, dto.getHolidayType2());
		setParam(index++, dto.getHolidayRange());
		setParam(index++, dto.getStartTime(), true);
		setParam(index++, dto.getEndTime(), true);
		setParam(index++, dto.getHolidayAcquisitionDate());
		setParam(index++, dto.getUseDay());
		setParam(index++, dto.getUseHour());
		setParam(index++, dto.getRequestReason());
		setParam(index++, dto.getWorkflow());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public HolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException {
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
			HolidayRequestDtoInterface dto = null;
			if (next()) {
				dto = (HolidayRequestDtoInterface)mapping();
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
	public List<HolidayRequestDtoInterface> findForList(String personalId, Date requestDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_REQUEST_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_REQUEST_END_DATE));
			sb.append(getOrderByColumns(COL_REQUEST_START_DATE, COL_REQUEST_END_DATE, COL_START_TIME));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, requestDate);
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
	public HolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestStartDate, int holidayType1,
			String holidayType2, int holidayRange, Date startTime) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_REQUEST_START_DATE));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE1));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE2));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_RANGE));
			sb.append(and());
			sb.append(equal(COL_START_TIME));
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualWithdrawn());
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, requestStartDate);
			setParam(index++, holidayType1);
			setParam(index++, holidayType2);
			setParam(index++, holidayRange);
			setParam(index++, startTime, true);
			executeQuery();
			HolidayRequestDtoInterface dto = null;
			if (next()) {
				dto = (HolidayRequestDtoInterface)mapping();
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
	public List<HolidayRequestDtoInterface> findForPersonalIds(Collection<String> personalIds, Date requestDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(in(COL_PERSONAL_ID, personalIds.size()));
			sb.append(and());
			sb.append(lessEqual(COL_REQUEST_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_REQUEST_END_DATE));
			sb.append(getOrderByColumn(COL_REQUEST_START_DATE, COL_REQUEST_END_DATE));
			prepareStatement(sb.toString());
			setParamsStringIn(personalIds);
			setParam(index++, requestDate);
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
	public List<HolidayRequestDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_REQUEST_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_REQUEST_END_DATE));
			sb.append(getOrderByColumn(COL_REQUEST_START_DATE, COL_REQUEST_END_DATE));
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
	public List<HolidayRequestDtoInterface> findForAppliedList(String personalId, Date acquisitionDate, Date firstDate,
			Date lastDate, int holidayType1, String holidayType2) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			if (MospUtility.isEmpty(personalId) == false) {
				sb.append(and());
				sb.append(equal(COL_PERSONAL_ID));
			}
			if (MospUtility.isEmpty(acquisitionDate) == false) {
				sb.append(and());
				sb.append(equal(COL_HOLIDAY_ACQUISITION_DATE));
			}
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE1));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE2));
			sb.append(and());
			sb.append(lessEqual(COL_REQUEST_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_REQUEST_END_DATE));
			sb.append(and());
			sb.append(workflowDao.getSubQueryForApplied(COL_WORKFLOW));
			sb.append(getOrderByColumns(COL_REQUEST_START_DATE, COL_REQUEST_END_DATE, COL_HOLIDAY_RANGE));
			prepareStatement(sb.toString());
			if (MospUtility.isEmpty(personalId) == false) {
				setParam(index++, personalId);
			}
			if (MospUtility.isEmpty(acquisitionDate) == false) {
				setParam(index++, acquisitionDate);
			}
			setParam(index++, holidayType1);
			setParam(index++, holidayType2);
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
	public List<HolidayRequestDtoInterface> findForRequestList(int holidayType1, String holidayType2, Date startDate,
			Date endDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE1));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE2));
			// 対象日による条件を設定
			if (startDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_REQUEST_END_DATE));
			}
			if (endDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_REQUEST_START_DATE));
			}
			sb.append(getOrderByColumn(COL_REQUEST_START_DATE, COL_REQUEST_END_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holidayType1);
			setParam(index++, holidayType2);
			// 検索条件パラメータ設定
			if (startDate != null) {
				setParam(index++, startDate, false);
			}
			if (endDate != null) {
				setParam(index++, endDate, false);
			}
			// SQL実行
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
	public List<HolidayRequestDtoInterface> findForTermOnWorkflow(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_REQUEST_START_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_REQUEST_END_DATE));
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualWithdrawn());
			sb.append(rightParenthesis());
			sb.append(getOrderByColumn(COL_REQUEST_START_DATE, COL_REQUEST_END_DATE));
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
	public List<HolidayRequestDtoInterface> findForAcquisitionList(String personalId, int holidayType1,
			String holidayType2, Date acquisitionDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE1));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE2));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_ACQUISITION_DATE));
			sb.append(getOrderByColumn(COL_REQUEST_START_DATE, COL_START_TIME));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, holidayType1);
			setParam(index++, holidayType2);
			setParam(index++, acquisitionDate);
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
