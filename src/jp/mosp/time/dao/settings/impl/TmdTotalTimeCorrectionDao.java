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
import jp.mosp.time.dao.settings.TotalTimeCorrectionDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeCorrectionDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTotalTimeCorrectionDto;

/**
 * 勤怠集計修正情報参照DAOクラス。
 */
public class TmdTotalTimeCorrectionDao extends PlatformDao implements TotalTimeCorrectionDaoInterface {
	
	/**
	 * 勤怠データ修正。
	 */
	public static final String	TABLE								= "tmd_total_time_correction";
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_TOTAL_TIME_CORRECTION_ID	= "tmd_total_time_correction_id";
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID						= "personal_id";
	/**
	 * 年。
	 */
	public static final String	COL_CALCULATION_YEAR				= "calculation_year";
	/**
	 * 月。
	 */
	public static final String	COL_CALCULATION_MONTH				= "calculation_month";
	/**
	 * 修正番号。
	 */
	public static final String	COL_CORRECTION_TIMES				= "correction_times";
	/**
	 * 修正日時。
	 */
	public static final String	COL_CORRECTION_DATE					= "correction_date";
	/**
	 * 修正社員コード。
	 */
	public static final String	COL_CORRECTION_PERSONAL_ID			= "correction_personal_id";
	/**
	 * 修正箇所。
	 */
	public static final String	COL_CORRECTION_TYPE					= "correction_type";
	/**
	 * 修正前。
	 */
	public static final String	COL_CORRECTION_BEFORE				= "correction_before";
	/**
	 * 修正後。
	 */
	public static final String	COL_CORRECTION_AFTER				= "correction_after";
	/**
	 * 修正理由。
	 */
	public static final String	COL_CORRECTION_REASON				= "correction_reason";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1								= COL_TMD_TOTAL_TIME_CORRECTION_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdTotalTimeCorrectionDao() {
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdTotalTimeCorrectionDto dto = new TmdTotalTimeCorrectionDto();
		dto.setTmdTotalTimeCorrectionId(getLong(COL_TMD_TOTAL_TIME_CORRECTION_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setCalculationYear(getInt(COL_CALCULATION_YEAR));
		dto.setCalculationMonth(getInt(COL_CALCULATION_MONTH));
		dto.setCorrectionTimes(getInt(COL_CORRECTION_TIMES));
		dto.setCorrectionDate(getTimestamp(COL_CORRECTION_DATE));
		dto.setCorrectionPersonalId(getString(COL_CORRECTION_PERSONAL_ID));
		dto.setCorrectionType(getString(COL_CORRECTION_TYPE));
		dto.setCorrectionBefore(getString(COL_CORRECTION_BEFORE));
		dto.setCorrectionAfter(getString(COL_CORRECTION_AFTER));
		dto.setCorrectionReason(getString(COL_CORRECTION_REASON));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<TotalTimeCorrectionDtoInterface> mappingAll() throws MospException {
		List<TotalTimeCorrectionDtoInterface> all = new ArrayList<TotalTimeCorrectionDtoInterface>();
		while (next()) {
			all.add((TotalTimeCorrectionDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public TotalTimeCorrectionDtoInterface findForLatestInfo(String personalId, int calculationYear,
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
			sb.append(getOrderByColumnDescLimit1(COL_CORRECTION_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, calculationYear);
			setParam(index++, calculationMonth);
			executeQuery();
			TotalTimeCorrectionDtoInterface dto = null;
			if (next()) {
				dto = (TotalTimeCorrectionDtoInterface)mapping();
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
	public List<TotalTimeCorrectionDtoInterface> findForHistory(String personalId, int calculationYear,
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
			sb.append(getOrderByColumn(COL_CORRECTION_DATE, COL_TMD_TOTAL_TIME_CORRECTION_ID));
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
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			TotalTimeCorrectionDtoInterface dto = (TotalTimeCorrectionDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalTimeCorrectionId());
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
			TotalTimeCorrectionDtoInterface dto = (TotalTimeCorrectionDtoInterface)baseDto;
			setParam(index++, dto.getTmdTotalTimeCorrectionId());
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
		TotalTimeCorrectionDtoInterface dto = (TotalTimeCorrectionDtoInterface)baseDto;
		setParam(index++, dto.getTmdTotalTimeCorrectionId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getCalculationYear());
		setParam(index++, dto.getCalculationMonth());
		setParam(index++, dto.getCorrectionTimes());
		setParam(index++, dto.getCorrectionDate(), true);
		setParam(index++, dto.getCorrectionPersonalId());
		setParam(index++, dto.getCorrectionType());
		setParam(index++, dto.getCorrectionBefore());
		setParam(index++, dto.getCorrectionAfter());
		setParam(index++, dto.getCorrectionReason());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
}
