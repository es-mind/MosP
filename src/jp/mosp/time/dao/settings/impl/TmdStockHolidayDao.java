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
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.StockHolidayDataDaoInterface;
import jp.mosp.time.dto.settings.StockHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdStockHolidayDto;

/**
 * ストック休暇データDAOクラス。
 */
public class TmdStockHolidayDao extends PlatformDao implements StockHolidayDataDaoInterface {
	
	/**
	 * ストック休暇データ。
	 */
	public static final String	TABLE						= "tmd_stock_holiday";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_STOCK_HOLIDAY_ID	= "tmd_stock_holiday_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID				= "personal_id";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE			= "activate_date";
	
	/**
	 * 取得日。
	 */
	public static final String	COL_ACQUISITION_DATE		= "acquisition_date";
	
	/**
	 * 期限日。
	 */
	public static final String	COL_LIMIT_DATE				= "limit_date";
	
	/**
	 * 保有日数。
	 */
	public static final String	COL_HOLD_DAY				= "hold_day";
	
	/**
	 * 付与日数。
	 */
	public static final String	COL_GIVING_DAY				= "giving_day";
	
	/**
	 * 廃棄日数。
	 */
	public static final String	COL_CANCEL_DAY				= "cancel_day";
	
	/**
	 * 使用日数。
	 */
	public static final String	COL_USE_DAY					= "use_day";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG			= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_TMD_STOCK_HOLIDAY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdStockHolidayDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdStockHolidayDto dto = new TmdStockHolidayDto();
		dto.setTmdStockHolidayId(getLong(COL_TMD_STOCK_HOLIDAY_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setAcquisitionDate(getDate(COL_ACQUISITION_DATE));
		dto.setLimitDate(getDate(COL_LIMIT_DATE));
		dto.setHoldDay(getDouble(COL_HOLD_DAY));
		dto.setGivingDay(getDouble(COL_GIVING_DAY));
		dto.setCancelDay(getDouble(COL_CANCEL_DAY));
		dto.setUseDay(getDouble(COL_USE_DAY));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<StockHolidayDataDtoInterface> mappingAll() throws MospException {
		List<StockHolidayDataDtoInterface> all = new ArrayList<StockHolidayDataDtoInterface>();
		while (next()) {
			all.add((StockHolidayDataDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public StockHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_ACQUISITION_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, activateDate);
			setParam(index++, acquisitionDate);
			executeQuery();
			StockHolidayDataDtoInterface dto = null;
			if (next()) {
				dto = (StockHolidayDataDtoInterface)mapping();
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
	public StockHolidayDataDtoInterface findForInfo(String personalId, Date activateDate, Date acquisitionDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_ACQUISITION_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, acquisitionDate);
			setParam(index++, activateDate);
			executeQuery();
			StockHolidayDataDtoInterface dto = null;
			if (next()) {
				dto = (StockHolidayDataDtoInterface)mapping();
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
	public List<StockHolidayDataDtoInterface> findForList(String personalId, Date targetDate, Date acquisitionDate,
			Date limitDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ACTIVATE_DATE, COL_PERSONAL_ID, COL_ACQUISITION_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(inactivateFlagOff());
			if (MospUtility.isEmpty(personalId) == false) {
				sb.append(and());
				sb.append(equal(COL_PERSONAL_ID));
			}
			if (MospUtility.isEmpty(acquisitionDate) == false) {
				sb.append(and());
				sb.append(lessEqual(COL_ACQUISITION_DATE));
			}
			if (MospUtility.isEmpty(limitDate) == false) {
				sb.append(and());
				sb.append(greaterEqual(COL_LIMIT_DATE));
			}
			sb.append(getOrderByColumns(COL_PERSONAL_ID, COL_ACQUISITION_DATE));
			prepareStatement(sb.toString());
			setParam(index++, targetDate);
			if (MospUtility.isEmpty(personalId) == false) {
				setParam(index++, personalId);
			}
			if (MospUtility.isEmpty(acquisitionDate) == false) {
				setParam(index++, acquisitionDate);
			}
			if (MospUtility.isEmpty(limitDate) == false) {
				setParam(index++, limitDate);
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
			StockHolidayDataDtoInterface dto = (StockHolidayDataDtoInterface)baseDto;
			setParam(index++, dto.getTmdStockHolidayId());
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
			StockHolidayDataDtoInterface dto = (StockHolidayDataDtoInterface)baseDto;
			setParam(index++, dto.getTmdStockHolidayId());
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
		StockHolidayDataDtoInterface dto = (StockHolidayDataDtoInterface)baseDto;
		setParam(index++, dto.getTmdStockHolidayId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getAcquisitionDate());
		setParam(index++, dto.getLimitDate());
		setParam(index++, dto.getHoldDay());
		setParam(index++, dto.getGivingDay());
		setParam(index++, dto.getCancelDay());
		setParam(index++, dto.getUseDay());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
}
