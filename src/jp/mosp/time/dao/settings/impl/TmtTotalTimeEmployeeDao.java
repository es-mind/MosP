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
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.TotalTimeEmployeeDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeEmployeeDtoInterface;
import jp.mosp.time.dto.settings.impl.TmtTotalTimeEmployeeDto;

/**
 * 社員勤怠集計管理トランザクションDAOクラス。
 */
public class TmtTotalTimeEmployeeDao extends PlatformDao implements TotalTimeEmployeeDaoInterface {
	
	/**
	 * 社員勤怠集計管理マスタ。
	 */
	public static final String	TABLE							= "tmt_total_time_employee";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMT_TOTAL_TIME_EMPLOYEE_ID	= "tmt_total_time_employee_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID					= "personal_id";
	
	/**
	 * 集計年。
	 */
	public static final String	COL_CALCULATION_YEAR			= "calculation_year";
	
	/**
	 * 集計月。
	 */
	public static final String	COL_CALCULATION_MONTH			= "calculation_month";
	
	/**
	 * 締日コード。
	 */
	public static final String	COL_CUTOFF_CODE					= "cutoff_code";
	
	/**
	 * 集計日。
	 */
	public static final String	COL_CALCULATION_DATE			= "calculation_date";
	
	/**
	 * 締状態。
	 */
	public static final String	COL_CUTOFF_STATE				= "cutoff_state";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1							= COL_TMT_TOTAL_TIME_EMPLOYEE_ID;
	
	
	/**
	 * 
	 */
	public TmtTotalTimeEmployeeDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmtTotalTimeEmployeeDto dto = new TmtTotalTimeEmployeeDto();
		dto.setTmtTotalTimeEmployeeId(getLong(COL_TMT_TOTAL_TIME_EMPLOYEE_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setCalculationYear(getInt(COL_CALCULATION_YEAR));
		dto.setCalculationMonth(getInt(COL_CALCULATION_MONTH));
		dto.setCutoffCode(getString(COL_CUTOFF_CODE));
		dto.setCalculationDate(getDate(COL_CALCULATION_DATE));
		dto.setCutoffState(getInt(COL_CUTOFF_STATE));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<TotalTimeEmployeeDtoInterface> mappingAll() throws MospException {
		List<TotalTimeEmployeeDtoInterface> all = new ArrayList<TotalTimeEmployeeDtoInterface>();
		while (next()) {
			all.add((TotalTimeEmployeeDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<TotalTimeEmployeeDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			String requestYear = String.valueOf(param.get("requestYear"));
			String requestMonth = String.valueOf(param.get("requestMonth"));
			String cutoffCode = String.valueOf(param.get("cutoffCode"));
			
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_CALCULATION_YEAR));
			sb.append(and());
			sb.append(equal(COL_CALCULATION_MONTH));
			sb.append(and());
			sb.append(equal(COL_CUTOFF_CODE));
			
			prepareStatement(sb.toString());
			setParam(index++, requestYear);
			setParam(index++, requestMonth);
			setParam(index++, cutoffCode);
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
	public TotalTimeEmployeeDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_CALCULATION_YEAR));
			sb.append(and());
			sb.append(equal(COL_CALCULATION_MONTH));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, calculationYear);
			setParam(index++, calculationMonth);
			executeQuery();
			TotalTimeEmployeeDtoInterface dto = null;
			if (next()) {
				dto = (TotalTimeEmployeeDtoInterface)mapping();
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
	public List<TotalTimeEmployeeDtoInterface> findForMonth(int calculationYear, int calculationMonth)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_CALCULATION_YEAR));
			sb.append(and());
			sb.append(equal(COL_CALCULATION_MONTH));
			prepareStatement(sb.toString());
			setParam(index++, calculationYear);
			setParam(index++, calculationMonth);
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
	public List<TotalTimeEmployeeDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			if (startDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_CALCULATION_DATE));
			}
			if (endDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_CALCULATION_DATE));
			}
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			prepareStatement(sb.toString());
			if (startDate != null) {
				setParam(index++, startDate);
			}
			if (endDate != null) {
				setParam(index++, endDate);
			}
			setParam(index++, personalId);
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
	public List<TotalTimeEmployeeDtoInterface> findCalcDataTerm(String cutoffCode, Date startDate, Date endDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			if (startDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_CALCULATION_DATE));
			}
			if (endDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_CALCULATION_DATE));
			}
			sb.append(and());
			sb.append(equal(COL_CUTOFF_CODE));
			prepareStatement(sb.toString());
			if (startDate != null) {
				setParam(index++, startDate);
			}
			if (endDate != null) {
				setParam(index++, endDate);
			}
			setParam(index++, cutoffCode);
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
	public List<TotalTimeEmployeeDtoInterface> findForPersonalList(String personalId, int cutoffState)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_CUTOFF_STATE));
			sb.append(getOrderByColumn(COL_CALCULATION_YEAR, COL_CALCULATION_MONTH));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, cutoffState);
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
			TotalTimeEmployeeDtoInterface dto = (TotalTimeEmployeeDtoInterface)baseDto;
			setParam(index++, dto.getTmtTotalTimeEmployeeId());
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
			TotalTimeEmployeeDtoInterface dto = (TotalTimeEmployeeDtoInterface)baseDto;
			setParam(index++, dto.getTmtTotalTimeEmployeeId());
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
		TotalTimeEmployeeDtoInterface dto = (TotalTimeEmployeeDtoInterface)baseDto;
		setParam(index++, dto.getTmtTotalTimeEmployeeId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getCalculationYear());
		setParam(index++, dto.getCalculationMonth());
		setParam(index++, dto.getCutoffCode());
		setParam(index++, dto.getCalculationDate());
		setParam(index++, Integer.valueOf(dto.getCutoffState()));
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
}
