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
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.workflow.WorkflowDaoInterface;
import jp.mosp.time.dao.settings.SubstituteDaoInterface;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdSubstituteDto;

/**
 * 振替休日データDAOクラス。
 */
public class TmdSubstituteDao extends PlatformDao implements SubstituteDaoInterface {
	
	/**
	 * 振替休日データ。
	 */
	public static final String		TABLE					= "tmd_substitute";
	
	/**
	 * レコード識別ID。
	 */
	public static final String		COL_TMD_SUBSTITUTE_ID	= "tmd_substitute_id";
	
	/**
	 * 個人ID。
	 */
	public static final String		COL_PERSONAL_ID			= "personal_id";
	
	/**
	 * 振替日。
	 */
	public static final String		COL_SUBSTITUTE_DATE		= "substitute_date";
	
	/**
	 * 振替種別。
	 */
	public static final String		COL_SUBSTITUTE_TYPE		= "substitute_type";
	
	/**
	 * 振替範囲。
	 */
	public static final String		COL_SUBSTITUTE_RANGE	= "substitute_range";
	
	/**
	 * 出勤日。
	 */
	public static final String		COL_WORK_DATE			= "work_date";
	
	/**
	 * 勤務回数。
	 */
	public static final String		COL_TIMES_WORK			= "times_work";
	
	/**
	 * ワークフロー番号
	 */
	public static final String		COL_WORKFLOW			= "workflow";
	
	/**
	 * 移行フラグ。
	 */
	public static final String		COL_TRANSITION_FLAG		= "transition_flag";
	
	/**
	 * キー。
	 */
	public static final String		KEY_1					= COL_TMD_SUBSTITUTE_ID;
	
	private WorkflowDaoInterface	workflowDao;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdSubstituteDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() throws MospException {
		workflowDao = (WorkflowDaoInterface)loadDao(WorkflowDaoInterface.class);
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdSubstituteDto dto = new TmdSubstituteDto();
		dto.setTmdSubstituteId(getLong(COL_TMD_SUBSTITUTE_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setSubstituteDate(getDate(COL_SUBSTITUTE_DATE));
		dto.setSubstituteType(getString(COL_SUBSTITUTE_TYPE));
		dto.setSubstituteRange(getInt(COL_SUBSTITUTE_RANGE));
		dto.setWorkDate(getDate(COL_WORK_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setWorkflow(getLong(COL_WORKFLOW));
		dto.setTransitionFlag(getInt(COL_TRANSITION_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<SubstituteDtoInterface> mappingAll() throws MospException {
		List<SubstituteDtoInterface> all = new ArrayList<SubstituteDtoInterface>();
		while (next()) {
			all.add((SubstituteDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public SubstituteDtoInterface findForKeyOnWorkflow(String personalId, Date substituteDate, int substituteRange,
			Date workDate, int timesWork) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_SUBSTITUTE_DATE));
			sb.append(and());
			sb.append(equal(COL_SUBSTITUTE_RANGE));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			sb.append(and());
			sb.append(equal(COL_TIMES_WORK));
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualWithdrawn());
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, substituteDate);
			setParam(index++, substituteRange);
			setParam(index++, workDate);
			setParam(index++, timesWork);
			executeQuery();
			SubstituteDtoInterface dto = null;
			if (next()) {
				dto = (SubstituteDtoInterface)mapping();
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
	public List<SubstituteDtoInterface> findForWorkDate(String personalId, Date workDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			sb.append(getOrderByColumn(COL_SUBSTITUTE_DATE, COL_SUBSTITUTE_RANGE));
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
	
	@Override
	public List<SubstituteDtoInterface> findForList(String personalId, Date substituteDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_SUBSTITUTE_DATE));
			sb.append(getOrderByColumn(COL_SUBSTITUTE_DATE, COL_SUBSTITUTE_RANGE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, substituteDate);
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
	public List<SubstituteDtoInterface> findForPersonalIds(Collection<String> personalIds, Date substituteDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(in(COL_PERSONAL_ID, personalIds.size()));
			sb.append(and());
			sb.append(equal(COL_SUBSTITUTE_DATE));
			sb.append(getOrderByColumn(COL_SUBSTITUTE_RANGE));
			prepareStatement(sb.toString());
			setParamsStringIn(personalIds);
			setParam(index++, substituteDate);
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
	public List<SubstituteDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_SUBSTITUTE_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_SUBSTITUTE_DATE));
			sb.append(getOrderByColumn(COL_SUBSTITUTE_DATE));
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
	public List<SubstituteDtoInterface> findForWorkflow(long workflow) throws MospException {
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
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<SubstituteDtoInterface> findForWorkDates(String personalId, Set<Date> workDates) throws MospException {
		// 出勤日群が空である場合
		if (MospUtility.isEmpty(workDates)) {
			// 空のリストを取得
			return new ArrayList<SubstituteDtoInterface>();
		}
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(in(COL_WORK_DATE, workDates.size()));
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualWithdrawn());
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParamsDateIn(workDates);
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
			SubstituteDtoInterface dto = (SubstituteDtoInterface)baseDto;
			setParam(index++, dto.getTmdSubstituteId());
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
			SubstituteDtoInterface dto = (SubstituteDtoInterface)baseDto;
			setParam(index++, dto.getTmdSubstituteId());
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
		SubstituteDtoInterface dto = (SubstituteDtoInterface)baseDto;
		setParam(index++, dto.getTmdSubstituteId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getSubstituteDate());
		setParam(index++, dto.getSubstituteType());
		setParam(index++, dto.getSubstituteRange());
		setParam(index++, dto.getWorkDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getWorkflow());
		setParam(index++, dto.getTransitionFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected SubstituteDtoInterface castDto(BaseDtoInterface baseDto) {
		return (SubstituteDtoInterface)baseDto;
	}
	
	@Override
	public SubstituteDtoInterface findForDate(String personalId, Date substituteDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_SUBSTITUTE_DATE));
			sb.append(and());
			sb.append(COL_WORKFLOW);
			sb.append(in());
			sb.append(leftParenthesis());
			sb.append(workflowDao.getSubQueryForNotEqualWithdrawn());
			sb.append(rightParenthesis());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, substituteDate);
			executeQuery();
			SubstituteDtoInterface dto = null;
			if (next()) {
				dto = (SubstituteDtoInterface)mapping();
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
