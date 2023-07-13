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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.ScheduleDaoInterface;
import jp.mosp.time.dto.settings.ScheduleDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmScheduleDto;

/**
 * カレンダマスタDAOクラス。
 */
public class TmmScheduleDao extends PlatformDao implements ScheduleDaoInterface {
	
	/**
	 * カレンダマスタ。
	 */
	public static final String	TABLE						= "tmm_schedule";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_SCHEDULE_ID			= "tmm_schedule_id";
	
	/**
	 * カレンダコード。
	 */
	public static final String	COL_SCHEDULE_CODE			= "schedule_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE			= "activate_date";
	
	/**
	 * カレンダ名称。
	 */
	public static final String	COL_SCHEDULE_NAME			= "schedule_name";
	
	/**
	 * カレンダ略称。
	 */
	public static final String	COL_SCHEDULE_ABBR			= "schedule_abbr";
	
	/**
	 * 年度。
	 */
	public static final String	COL_FISCAL_YEAR				= "fiscal_year";
	
	/**
	 * パターンコード。
	 */
	public static final String	COL_PATTERN_CODE			= "pattern_code";
	
	/**
	 * 勤務形態変更フラグ。
	 */
	public static final String	COL_WORK_TYPE_CHANGE_FLAG	= "work_type_change_flag";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG			= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_TMM_SCHEDULE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmScheduleDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmScheduleDto dto = new TmmScheduleDto();
		dto.setTmmScheduleId(getLong(COL_TMM_SCHEDULE_ID));
		dto.setScheduleCode(getString(COL_SCHEDULE_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setScheduleName(getString(COL_SCHEDULE_NAME));
		dto.setScheduleAbbr(getString(COL_SCHEDULE_ABBR));
		dto.setFiscalYear(getInt(COL_FISCAL_YEAR));
		dto.setPatternCode(getString(COL_PATTERN_CODE));
		dto.setWorkTypeChangeFlag(getInt(COL_WORK_TYPE_CHANGE_FLAG));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<ScheduleDtoInterface> mappingAll() throws MospException {
		List<ScheduleDtoInterface> all = new ArrayList<ScheduleDtoInterface>();
		while (next()) {
			all.add((ScheduleDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<ScheduleDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(COL_INACTIVATE_FLAG);
			sb.append(" = ");
			sb.append(MospConst.DELETE_FLAG_OFF);
			sb.append(" ");
			sb.append(and());
			sb.append(getQueryForMaxActivateDate());
			sb.append(getOrderByColumn(COL_SCHEDULE_CODE));
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
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
	public List<ScheduleDtoInterface> findForHistory(String scheduleCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_SCHEDULE_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, scheduleCode);
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
	public ScheduleDtoInterface findForKey(String scheduleCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_SCHEDULE_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, scheduleCode);
			setParam(index++, activateDate);
			executeQuery();
			ScheduleDtoInterface dto = null;
			if (next()) {
				dto = (ScheduleDtoInterface)mapping();
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
	public ScheduleDtoInterface findForInfo(String scheduleCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_SCHEDULE_CODE));
			sb.append(and());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ? ");
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, scheduleCode);
			setParam(index++, activateDate);
			executeQuery();
			ScheduleDtoInterface dto = null;
			if (next()) {
				dto = (ScheduleDtoInterface)mapping();
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
	public List<ScheduleDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			Date activateDate = (Date)param.get("activateDate");
			String scheduleCode = String.valueOf(param.get("scheduleCode"));
			String scheduleName = String.valueOf(param.get("scheduleName"));
			String scheduleAbbr = String.valueOf(param.get("scheduleAbbr"));
			String fiscalYear = String.valueOf(param.get("fiscalYear"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_SCHEDULE_CODE));
			sb.append(and());
			sb.append(like(COL_SCHEDULE_NAME));
			sb.append(and());
			sb.append(like(COL_SCHEDULE_ABBR));
			if (activateDate != null) {
				sb.append(and());
				sb.append(getQueryForMaxActivateDate());
			}
			if (!fiscalYear.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_FISCAL_YEAR));
			}
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			prepareStatement(sb.toString());
			setParam(index++, startWithParam(scheduleCode));
			setParam(index++, containsParam(scheduleName));
			setParam(index++, containsParam(scheduleAbbr));
			if (activateDate != null) {
				setParam(index++, activateDate);
			}
			if (!fiscalYear.isEmpty()) {
				setParam(index++, Integer.parseInt(fiscalYear));
			}
			if (!inactivateFlag.isEmpty()) {
				setParam(index++, Integer.parseInt(inactivateFlag));
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
			ScheduleDtoInterface dto = (ScheduleDtoInterface)baseDto;
			setParam(index++, dto.getTmmScheduleId());
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
			ScheduleDtoInterface dto = (ScheduleDtoInterface)baseDto;
			setParam(index++, dto.getTmmScheduleId());
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
		ScheduleDtoInterface dto = (ScheduleDtoInterface)baseDto;
		setParam(index++, dto.getTmmScheduleId());
		setParam(index++, dto.getScheduleCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getScheduleName());
		setParam(index++, dto.getScheduleAbbr());
		setParam(index++, dto.getFiscalYear());
		setParam(index++, dto.getPatternCode());
		setParam(index++, dto.getWorkTypeChangeFlag());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public StringBuffer getQueryForMaxActivateDate() {
		StringBuffer sb = new StringBuffer();
		sb.append(COL_ACTIVATE_DATE);
		sb.append(" IN (SELECT ");
		sb.append("MAX(" + COL_ACTIVATE_DATE + ")");
		sb.append(from(TABLE) + " AS A ");
		sb.append(where());
		sb.append(TABLE + "." + COL_SCHEDULE_CODE);
		sb.append(" = A." + COL_SCHEDULE_CODE);
		sb.append(and());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(COL_ACTIVATE_DATE);
		sb.append(" <= ?");
		sb.append(")");
		return sb;
	}
	
}
