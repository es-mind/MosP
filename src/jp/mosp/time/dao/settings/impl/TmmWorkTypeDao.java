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
/**
 * 
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
import jp.mosp.time.dao.settings.WorkTypeDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmWorkTypeDto;

/**
 * 勤務形態マスタDAOクラス。
 */
public class TmmWorkTypeDao extends PlatformDao implements WorkTypeDaoInterface {
	
	/**
	 * 勤務形態マスタ。
	 */
	public static final String	TABLE					= "tmm_work_type";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_WORK_TYPE_ID	= "tmm_work_type_id";
	
	/**
	 * 勤務形態コード。
	 */
	public static final String	COL_WORK_TYPE_CODE		= "work_type_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE		= "activate_date";
	
	/**
	 * 勤務形態名称。
	 */
	public static final String	COL_WORK_TYPE_NAME		= "work_type_name";
	
	/**
	 * 勤務形態略称。
	 */
	public static final String	COL_WORK_TYPE_ABBR		= "work_type_abbr";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG		= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_TMM_WORK_TYPE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmWorkTypeDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmWorkTypeDto dto = new TmmWorkTypeDto();
		dto.setTmmWorkTypeId(getLong(COL_TMM_WORK_TYPE_ID));
		dto.setWorkTypeCode(getString(COL_WORK_TYPE_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setWorkTypeName(getString(COL_WORK_TYPE_NAME));
		dto.setWorkTypeAbbr(getString(COL_WORK_TYPE_ABBR));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<WorkTypeDtoInterface> mappingAll() throws MospException {
		List<WorkTypeDtoInterface> all = new ArrayList<WorkTypeDtoInterface>();
		while (next()) {
			all.add((WorkTypeDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<WorkTypeDtoInterface> findForActivateDate(Date activateDate) throws MospException {
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
			sb.append(getOrderByColumn(COL_WORK_TYPE_CODE));
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
	public List<WorkTypeDtoInterface> findForHistory(String workTypeCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workTypeCode);
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
	public WorkTypeDtoInterface findForKey(String workTypeCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workTypeCode);
			setParam(index++, activateDate);
			executeQuery();
			WorkTypeDtoInterface dto = null;
			if (next()) {
				dto = (WorkTypeDtoInterface)mapping();
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
	public WorkTypeDtoInterface findForInfo(String workTypeCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_CODE));
			sb.append(and());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ? ");
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workTypeCode);
			setParam(index++, activateDate);
			executeQuery();
			WorkTypeDtoInterface dto = null;
			if (next()) {
				dto = (WorkTypeDtoInterface)mapping();
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
	public List<WorkTypeDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			Date activateDate = (Date)param.get("activateDate");
			String workTypeCode = String.valueOf(param.get("workTypeCode"));
			String workTypeName = String.valueOf(param.get("workTypeName"));
			String workTypeAbbr = String.valueOf(param.get("workTypeAbbr"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_WORK_TYPE_CODE));
			sb.append(and());
			sb.append(like(COL_WORK_TYPE_NAME));
			sb.append(and());
			sb.append(like(COL_WORK_TYPE_ABBR));
			if (activateDate != null) {
				sb.append(and());
				sb.append(getQueryForMaxActivateDate());
			}
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			prepareStatement(sb.toString());
			setParam(index++, startWithParam(workTypeCode));
			setParam(index++, containsParam(workTypeName));
			setParam(index++, containsParam(workTypeAbbr));
			if (activateDate != null) {
				setParam(index++, activateDate);
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
			WorkTypeDtoInterface dto = (WorkTypeDtoInterface)baseDto;
			setParam(index++, dto.getTmmWorkTypeId());
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
			WorkTypeDtoInterface dto = (WorkTypeDtoInterface)baseDto;
			setParam(index++, dto.getTmmWorkTypeId());
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
		WorkTypeDtoInterface dto = (WorkTypeDtoInterface)baseDto;
		setParam(index++, dto.getTmmWorkTypeId());
		setParam(index++, dto.getWorkTypeCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getWorkTypeName());
		setParam(index++, dto.getWorkTypeAbbr());
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
		sb.append(" IN (");
		sb.append("SELECT ");
		sb.append("MAX(" + COL_ACTIVATE_DATE + ")");
		sb.append(from(TABLE) + " AS A ");
		sb.append(where());
		sb.append(TABLE + "." + COL_WORK_TYPE_CODE);
		sb.append(" = A." + COL_WORK_TYPE_CODE);
		sb.append(and());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(COL_ACTIVATE_DATE);
		sb.append(" <= ?");
		sb.append(")");
		return sb;
	}
	
}
