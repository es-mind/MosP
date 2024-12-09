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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.TotalOtherVacationDaoInterface;
import jp.mosp.time.dto.settings.TotalOtherVacationDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTotalOtherVacationDto;

/**
 * その他休暇集計データDTOクラス。
 */
public class TmdTotalOtherVacationDao extends PlatformDao implements TotalOtherVacationDaoInterface {
	
	/**
	 * その他休暇集計データ。
	 */
	public static final String	TABLE							= "tmd_total_other_vacation";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_TOTAL_OTHER_VACATION_ID	= "tmd_total_other_vacation_id";
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID					= "personal_id";
	/**
	 * 年。
	 */
	public static final String	COL_CALCULATION_YEAR			= "calculation_year";
	/**
	 * 月。
	 */
	public static final String	COL_CALCULATION_MONTH			= "calculation_month";
	/**
	 * 休暇コード。
	 */
	public static final String	COL_HOLIDAY_CODE				= "holiday_code";
	/**
	 * 特別休暇日数。
	 */
	public static final String	COL_TIMES						= "times";
	/**
	 * 特別休暇時間数。
	 */
	public static final String	COL_HOURS						= "hours";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1							= COL_TMD_TOTAL_OTHER_VACATION_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdTotalOtherVacationDao() {
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdTotalOtherVacationDto dto = new TmdTotalOtherVacationDto();
		dto.setTmdTotalOtherVacationId(getLong(COL_TMD_TOTAL_OTHER_VACATION_ID));
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
	public List<TotalOtherVacationDtoInterface> mappingAll() throws MospException {
		List<TotalOtherVacationDtoInterface> all = new ArrayList<TotalOtherVacationDtoInterface>();
		while (next()) {
			all.add((TotalOtherVacationDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			TotalOtherVacationDtoInterface dto = (TotalOtherVacationDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalOtherVacationId());
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
			TotalOtherVacationDtoInterface dto = (TotalOtherVacationDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalOtherVacationId());
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
		TotalOtherVacationDtoInterface dto = (TotalOtherVacationDtoInterface)baseDto;
		setParam(index++, dto.getTmdTotalOtherVacationId());
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
	public TotalOtherVacationDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
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
			TotalOtherVacationDtoInterface dto = null;
			if (next()) {
				dto = (TotalOtherVacationDtoInterface)mapping();
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
	public List<TotalOtherVacationDtoInterface> findForList(String personalId, int calculationYear,
			int calculationMonth) throws MospException {
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
