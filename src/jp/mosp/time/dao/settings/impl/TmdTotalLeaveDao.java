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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.TotalLeaveDaoInterface;
import jp.mosp.time.dto.settings.TotalLeaveDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTotalLeaveDto;

/**
 * 特別休暇集計データDAOクラス。
 */
public class TmdTotalLeaveDao extends PlatformDao implements TotalLeaveDaoInterface {
	
	/**
	 * 勤怠データ修正。
	 */
	public static final String	TABLE					= "tmd_total_leave";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_TOTAL_LEAVE_ID	= "tmd_total_leave_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID			= "personal_id";
	
	/**
	 * 年。
	 */
	public static final String	COL_CALCULATION_YEAR	= "calculation_year";
	
	/**
	 * 月。
	 */
	public static final String	COL_CALCULATION_MONTH	= "calculation_month";
	
	/**
	 * 休暇コード。
	 */
	public static final String	COL_HOLIDAY_CODE		= "holiday_code";
	
	/**
	 * 特別休暇日数。
	 */
	public static final String	COL_TIMES				= "times";
	
	/**
	 * 特別休暇時間数。
	 */
	public static final String	COL_HOURS				= "hours";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_TMD_TOTAL_LEAVE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdTotalLeaveDao() {
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdTotalLeaveDto dto = new TmdTotalLeaveDto();
		dto.setTmdTotalLeaveId(getLong(COL_TMD_TOTAL_LEAVE_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setCalculationYear(getInt(COL_CALCULATION_YEAR));
		dto.setCalculationMonth(getInt(COL_CALCULATION_MONTH));
		dto.setHolidayCode(getString(COL_HOLIDAY_CODE));
		dto.setTimes(getDouble(COL_TIMES));
		dto.setHours(getInt(COL_HOURS));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<TotalLeaveDtoInterface> mappingAll() throws MospException {
		List<TotalLeaveDtoInterface> all = new ArrayList<TotalLeaveDtoInterface>();
		while (next()) {
			all.add((TotalLeaveDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			TotalLeaveDtoInterface dto = (TotalLeaveDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalLeaveId());
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
			TotalLeaveDtoInterface dto = (TotalLeaveDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalLeaveId());
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
		TotalLeaveDtoInterface dto = (TotalLeaveDtoInterface)baseDto;
		setParam(index++, dto.getTmdTotalLeaveId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getCalculationYear());
		setParam(index++, dto.getCalculationMonth());
		setParam(index++, dto.getHolidayCode());
		setParam(index++, dto.getTimes());
		setParam(index++, dto.getHours());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public TotalLeaveDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
			String holidayCode) throws MospException {
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
			sb.append(equal(COL_HOLIDAY_CODE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, calculationYear);
			setParam(index++, calculationMonth);
			setParam(index++, holidayCode);
			executeQuery();
			TotalLeaveDtoInterface dto = null;
			if (next()) {
				dto = (TotalLeaveDtoInterface)mapping();
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
	public List<TotalLeaveDtoInterface> findForList(String personalId, int calculationYear, int calculationMonth)
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
			sb.append(getOrderByColumn(COL_HOLIDAY_CODE));
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
