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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.TotalAllowanceDaoInterface;
import jp.mosp.time.dto.settings.TotalAllowanceDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTotalAllowanceDto;

/**
 * 手当集計データDAOクラス。
 */
public class TmdTotalAllowanceDao extends PlatformDao implements TotalAllowanceDaoInterface {
	
	/**
	 * 休憩データ。
	 */
	public static final String	TABLE						= "tmd_total_allowance";
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_TOTAL_ALLOWANCE_ID	= "tmd_total_allowance_id";
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID				= "personal_id";
	/**
	 * 年。
	 */
	public static final String	COL_CALCULATION_YEAR		= "calculation_year";
	/**
	 * 月。
	 */
	public static final String	COL_CALCULATION_MONTH		= "calculation_month";
	/**
	 * 手当コード。
	 */
	public static final String	COL_ALLOWANCE_CODE			= "allowance_code";
	/**
	 * 手当回数。
	 */
	public static final String	COL_TIMES					= "times";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_TMD_TOTAL_ALLOWANCE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdTotalAllowanceDao() {
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdTotalAllowanceDto dto = new TmdTotalAllowanceDto();
		dto.setTmdTotalAllowanceId(getLong(COL_TMD_TOTAL_ALLOWANCE_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setCalculationYear(getInt(COL_CALCULATION_YEAR));
		dto.setCalculationMonth(getInt(COL_CALCULATION_MONTH));
		dto.setAllowanceCode(getString(COL_ALLOWANCE_CODE));
		dto.setTimes(getInt(COL_TIMES));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<TotalAllowanceDtoInterface> mappingAll() throws MospException {
		List<TotalAllowanceDtoInterface> all = new ArrayList<TotalAllowanceDtoInterface>();
		while (next()) {
			all.add((TotalAllowanceDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<TotalAllowanceDtoInterface> findForHistory(String personalId, int calculationYear, int calculationMonth,
			String allowanceCode) throws MospException {
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
			sb.append(and());
			sb.append(equal(COL_ALLOWANCE_CODE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, calculationYear);
			setParam(index++, calculationMonth);
			setParam(index++, allowanceCode);
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
			TotalAllowanceDtoInterface dto = (TotalAllowanceDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalAllowanceId());
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
			TotalAllowanceDtoInterface dto = (TotalAllowanceDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalAllowanceId());
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
		TmdTotalAllowanceDto dto = (TmdTotalAllowanceDto)baseDto;
		setParam(index++, dto.getTmdTotalAllowanceId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getCalculationYear());
		setParam(index++, dto.getCalculationMonth());
		setParam(index++, dto.getAllowanceCode());
		setParam(index++, dto.getTimes());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public TotalAllowanceDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
			String allowanceCode) throws MospException {
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
			sb.append(and());
			sb.append(equal(COL_ALLOWANCE_CODE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, calculationYear);
			setParam(index++, calculationMonth);
			setParam(index++, allowanceCode);
			executeQuery();
			TotalAllowanceDtoInterface dto = null;
			if (next()) {
				dto = (TotalAllowanceDtoInterface)mapping();
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
	public List<TotalAllowanceDtoInterface> findForList(String personalId, int calculationYear, int calculationMonth)
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
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
