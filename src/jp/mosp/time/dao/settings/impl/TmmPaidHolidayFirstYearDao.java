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
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.PaidHolidayFirstYearDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayFirstYearDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmPaidHolidayFirstYearDto;

/**
 * 有給休暇初年度付与マスタDAOクラス。
 */
public class TmmPaidHolidayFirstYearDao extends PlatformDao implements PaidHolidayFirstYearDaoInterface {
	
	/**
	 * 有給休暇初年度付与マスタ。
	 */
	public static final String	TABLE								= "tmm_paid_holiday_first_year";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_PAID_HOLIDAY_FIRST_YEAR_ID	= "tmm_paid_holiday_first_year_id";
	
	/**
	 * 有休コード。
	 */
	public static final String	COL_PAID_HOLIDAY_CODE				= "paid_holiday_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE					= "activate_date";
	
	/**
	 * 入社月。
	 */
	public static final String	COL_ENTRANCE_MONTH					= "entrance_month";
	
	/**
	 * 付与月。
	 */
	public static final String	COL_GIVING_MONTH					= "giving_month";
	
	/**
	 * 付与日数。
	 */
	public static final String	COL_GIVING_AMOUNT					= "giving_amount";
	
	/**
	 * 利用期限。
	 */
	public static final String	COL_GIVING_LIMIT					= "giving_limit";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG					= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1								= COL_TMM_PAID_HOLIDAY_FIRST_YEAR_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmPaidHolidayFirstYearDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmPaidHolidayFirstYearDto dto = new TmmPaidHolidayFirstYearDto();
		dto.setTmmPaidHolidayFirstYearId(getLong(COL_TMM_PAID_HOLIDAY_FIRST_YEAR_ID));
		dto.setPaidHolidayCode(getString(COL_PAID_HOLIDAY_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setEntranceMonth(getInt(COL_ENTRANCE_MONTH));
		dto.setGivingMonth(getInt(COL_GIVING_MONTH));
		dto.setGivingAmount(getInt(COL_GIVING_AMOUNT));
		dto.setGivingLimit(getInt(COL_GIVING_LIMIT));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<PaidHolidayFirstYearDtoInterface> mappingAll() throws MospException {
		List<PaidHolidayFirstYearDtoInterface> all = new ArrayList<PaidHolidayFirstYearDtoInterface>();
		while (next()) {
			all.add((PaidHolidayFirstYearDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<PaidHolidayFirstYearDtoInterface> findForHistory(String paidHolidayCode, int entranceMonth)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_ENTRANCE_MONTH));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, entranceMonth);
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
	public PaidHolidayFirstYearDtoInterface findForKey(String paidHolidayCode, Date activateDate, int entranceMonth)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_ENTRANCE_MONTH));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, activateDate);
			setParam(index++, entranceMonth);
			executeQuery();
			PaidHolidayFirstYearDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayFirstYearDtoInterface)mapping();
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
	public PaidHolidayFirstYearDtoInterface findForInfo(String paidHolidayCode, Date activateDate, int entranceMonth)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_ENTRANCE_MONTH));
			sb.append(and());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ? ");
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, entranceMonth);
			setParam(index++, activateDate);
			executeQuery();
			PaidHolidayFirstYearDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayFirstYearDtoInterface)mapping();
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
			PaidHolidayFirstYearDtoInterface dto = (PaidHolidayFirstYearDtoInterface)baseDto;
			setParam(index++, dto.getTmmPaidHolidayFirstYearId());
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
			PaidHolidayFirstYearDtoInterface dto = (PaidHolidayFirstYearDtoInterface)baseDto;
			setParam(index++, dto.getTmmPaidHolidayFirstYearId());
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
		PaidHolidayFirstYearDtoInterface dto = (PaidHolidayFirstYearDtoInterface)baseDto;
		setParam(index++, dto.getTmmPaidHolidayFirstYearId());
		setParam(index++, dto.getPaidHolidayCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getEntranceMonth());
		setParam(index++, dto.getGivingMonth());
		setParam(index++, dto.getGivingAmount());
		setParam(index++, dto.getGivingLimit());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
}
