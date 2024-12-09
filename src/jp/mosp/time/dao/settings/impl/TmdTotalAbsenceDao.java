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
import jp.mosp.time.dao.settings.TotalAbsenceDaoInterface;
import jp.mosp.time.dto.settings.TotalAbsenceDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTotalAbsenceDto;

/**
 * 欠勤集計データDTOクラス。
 */
public class TmdTotalAbsenceDao extends PlatformDao implements TotalAbsenceDaoInterface {
	
	/**
	 * 欠勤集計データ。
	 */
	public static final String	TABLE						= "tmd_total_absence";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_TOTAL_ABSENCE_ID	= "tmd_total_absence_id";
	
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
	 * 欠勤コード。
	 */
	public static final String	COL_ABSENCE_CODE			= "absence_code";
	
	/**
	 * 欠勤日数。
	 */
	public static final String	COL_TIMES					= "times";
	
	/**
	 * 欠勤時間数。
	 */
	public static final String	COL_HOURS					= "hours";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_TMD_TOTAL_ABSENCE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdTotalAbsenceDao() {
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdTotalAbsenceDto dto = new TmdTotalAbsenceDto();
		dto.setTmdTotalAbsenceId(getLong(COL_TMD_TOTAL_ABSENCE_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setCalculationYear(getInt(COL_CALCULATION_YEAR));
		dto.setCalculationMonth(getInt(COL_CALCULATION_MONTH));
		dto.setAbsenceCode(getString(COL_ABSENCE_CODE));
		dto.setTimes(getDouble(COL_TIMES));
		dto.setHours(getInt(COL_HOURS));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<TotalAbsenceDtoInterface> mappingAll() throws MospException {
		List<TotalAbsenceDtoInterface> all = new ArrayList<TotalAbsenceDtoInterface>();
		while (next()) {
			all.add((TotalAbsenceDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			TotalAbsenceDtoInterface dto = (TotalAbsenceDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalAbsenceId());
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
			TotalAbsenceDtoInterface dto = (TotalAbsenceDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalAbsenceId());
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
		TotalAbsenceDtoInterface dto = (TotalAbsenceDtoInterface)baseDto;
		setParam(index++, dto.getTmdTotalAbsenceId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getCalculationYear());
		setParam(index++, dto.getCalculationMonth());
		setParam(index++, dto.getAbsenceCode());
		setParam(index++, dto.getTimes());
		setParam(index++, dto.getHours());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public TotalAbsenceDtoInterface findForKey(String personalId, int calculationYear, int calculationMonth,
			String absenceCode) throws MospException {
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
			sb.append(equal(COL_ABSENCE_CODE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, calculationYear);
			setParam(index++, calculationMonth);
			setParam(index++, absenceCode);
			executeQuery();
			TotalAbsenceDtoInterface dto = null;
			if (next()) {
				dto = (TotalAbsenceDtoInterface)mapping();
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
	public List<TotalAbsenceDtoInterface> findForList(String personalId, int calculationYear, int calculationMonth)
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
			sb.append(getOrderByColumn(COL_ABSENCE_CODE));
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
