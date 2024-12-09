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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.WorkTypePatternDaoInterface;
import jp.mosp.time.dto.settings.WorkTypePatternDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmWorkTypePatternDto;

/**
 * 勤務形態パターンマスタDAOクラス。
 */
public class TmmWorkTypePatternDao extends PlatformDao implements WorkTypePatternDaoInterface {
	
	/**
	 * 勤務形態パターンマスタ。
	 */
	public static final String	TABLE							= "tmm_work_type_pattern";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_WORK_TYPE_PATTERN_ID	= "tmm_work_type_pattern_id";
	
	/**
	 * パターンコード。
	 */
	public static final String	COL_PATTERN_CODE				= "pattern_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE				= "activate_date";
	
	/**
	 * パターン名称。
	 */
	public static final String	COL_PATTERN_NAME				= "pattern_name";
	
	/**
	 * パターン略称。
	 */
	public static final String	COL_PATTERN_ABBR				= "pattern_abbr";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG				= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1							= COL_TMM_WORK_TYPE_PATTERN_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmWorkTypePatternDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmWorkTypePatternDto dto = new TmmWorkTypePatternDto();
		dto.setTmmWorkTypePatternId(getLong(COL_TMM_WORK_TYPE_PATTERN_ID));
		dto.setPatternCode(getString(COL_PATTERN_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setPatternName(getString(COL_PATTERN_NAME));
		dto.setPatternAbbr(getString(COL_PATTERN_ABBR));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<WorkTypePatternDtoInterface> mappingAll() throws MospException {
		List<WorkTypePatternDtoInterface> list = new ArrayList<WorkTypePatternDtoInterface>();
		while (next()) {
			list.add((WorkTypePatternDtoInterface)mapping());
		}
		return list;
	}
	
	@Override
	public List<WorkTypePatternDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_PATTERN_CODE, COL_ACTIVATE_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.INACTIVATE_FLAG_OFF));
			sb.append(getOrderByColumn(COL_PATTERN_CODE));
			prepareStatement(sb.toString());
			index = setParamsForMaxActivateDate(index, activateDate, ps);
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
	public List<WorkTypePatternDtoInterface> findForHistory(String patternCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PATTERN_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, patternCode);
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
	public WorkTypePatternDtoInterface findForKey(String patternCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PATTERN_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, patternCode);
			setParam(index++, activateDate);
			executeQuery();
			WorkTypePatternDtoInterface dto = null;
			if (next()) {
				dto = (WorkTypePatternDtoInterface)mapping();
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
	public WorkTypePatternDtoInterface findForInfo(String patternCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PATTERN_CODE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, patternCode);
			setParam(index++, activateDate);
			executeQuery();
			WorkTypePatternDtoInterface dto = null;
			if (next()) {
				dto = (WorkTypePatternDtoInterface)mapping();
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
	public List<WorkTypePatternDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			Date activateDate = (Date)param.get("activateDate");
			String patternCode = String.valueOf(param.get("patternCode"));
			String patternName = String.valueOf(param.get("patternName"));
			String patternAbbr = String.valueOf(param.get("patternAbbr"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			if (activateDate != null) {
				sb.append(getQueryForMaxActivateDate(TABLE, COL_PATTERN_CODE, COL_ACTIVATE_DATE));
			}
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_PATTERN_CODE));
			sb.append(and());
			sb.append(like(COL_PATTERN_NAME));
			sb.append(and());
			sb.append(like(COL_PATTERN_ABBR));
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			prepareStatement(sb.toString());
			if (activateDate != null) {
				index = setParamsForMaxActivateDate(index, activateDate, ps);
			}
			setParam(index++, startWithParam(patternCode));
			setParam(index++, containsParam(patternName));
			setParam(index++, containsParam(patternAbbr));
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
			WorkTypePatternDtoInterface dto = (WorkTypePatternDtoInterface)baseDto;
			setParam(index++, dto.getTmmWorkTypePatternId());
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
			WorkTypePatternDtoInterface dto = (WorkTypePatternDtoInterface)baseDto;
			setParam(index++, dto.getTmmWorkTypePatternId());
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
		WorkTypePatternDtoInterface dto = (WorkTypePatternDtoInterface)baseDto;
		setParam(index++, dto.getTmmWorkTypePatternId());
		setParam(index++, dto.getPatternCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getPatternName());
		setParam(index++, dto.getPatternAbbr());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
}
