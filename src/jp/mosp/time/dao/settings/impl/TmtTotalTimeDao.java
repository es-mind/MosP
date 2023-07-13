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
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.TotalTimeDaoInterface;
import jp.mosp.time.dto.settings.TotalTimeDtoInterface;
import jp.mosp.time.dto.settings.impl.TmtTotalTimeDto;

/**
 * 勤怠集計管理トランザクションDAOクラス。
 */
public class TmtTotalTimeDao extends PlatformDao implements TotalTimeDaoInterface {
	
	/**
	 * 勤怠集計管理トランザクション。
	 */
	public static final String	TABLE					= "tmt_total_time";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMT_TOTAL_TIME_ID	= "tmt_total_time_id";
	
	/**
	 * 集計年。
	 */
	public static final String	COL_CALCULATION_YEAR	= "calculation_year";
	
	/**
	 * 集計月。
	 */
	public static final String	COL_CALCULATION_MONTH	= "calculation_month";
	
	/**
	 * 締日コード。
	 */
	public static final String	COL_CUTOFF_CODE			= "cutoff_code";
	
	/**
	 * 集計日。
	 */
	public static final String	COL_CALCULATION_DATE	= "calculation_date";
	
	/**
	 * 締状態。
	 */
	public static final String	COL_CUTOFF_STATE		= "cutoff_state";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_TMT_TOTAL_TIME_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmtTotalTimeDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmtTotalTimeDto dto = new TmtTotalTimeDto();
		dto.setTmtTotalTimeId(getLong(COL_TMT_TOTAL_TIME_ID));
		dto.setCalculationYear(getInt(COL_CALCULATION_YEAR));
		dto.setCalculationMonth(getInt(COL_CALCULATION_MONTH));
		dto.setCutoffCode(getString(COL_CUTOFF_CODE));
		dto.setCalculationDate(getDate(COL_CALCULATION_DATE));
		dto.setCutoffState(getInt(COL_CUTOFF_STATE));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<TotalTimeDtoInterface> mappingAll() throws MospException {
		List<TotalTimeDtoInterface> all = new ArrayList<TotalTimeDtoInterface>();
		while (next()) {
			all.add((TotalTimeDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public TotalTimeDtoInterface findForKey(int calculationYear, int calculationMonth, String cutoffCode)
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
			sb.append(and());
			sb.append(equal(COL_CUTOFF_CODE));
			prepareStatement(sb.toString());
			setParam(index++, calculationYear);
			setParam(index++, calculationMonth);
			setParam(index++, cutoffCode);
			executeQuery();
			TotalTimeDtoInterface dto = null;
			if (next()) {
				dto = (TotalTimeDtoInterface)mapping();
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			TotalTimeDtoInterface dto = (TotalTimeDtoInterface)baseDto;
			setParam(index++, dto.getTmtTotalTimeId());
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
			TotalTimeDtoInterface dto = (TotalTimeDtoInterface)baseDto;
			setParam(index++, dto.getTmtTotalTimeId());
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
		TotalTimeDtoInterface dto = (TotalTimeDtoInterface)baseDto;
		setParam(index++, dto.getTmtTotalTimeId());
		setParam(index++, dto.getCalculationYear());
		setParam(index++, dto.getCalculationMonth());
		setParam(index++, dto.getCutoffCode());
		setParam(index++, dto.getCalculationDate());
		setParam(index++, dto.getCutoffState());
		setCommonParams(baseDto, isInsert);
	}
	
}
