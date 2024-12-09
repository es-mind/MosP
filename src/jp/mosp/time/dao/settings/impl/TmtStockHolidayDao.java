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
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.StockHolidayTransactionDaoInterface;
import jp.mosp.time.dto.settings.StockHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.impl.TmtStockHolidayDto;

/**
 * ストック休暇トランザクションDAO。
 */
public class TmtStockHolidayDao extends PlatformDao implements StockHolidayTransactionDaoInterface {
	
	/**
	 * ストック休暇トランザクション。
	 */
	public static final String	TABLE						= "tmt_stock_holiday";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMT_STOCK_HOLIDAY_ID	= "tmt_stock_holiday_id";
	
	/**
	 * 社員コード。
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
	 * 付与日数。
	 */
	public static final String	COL_GIVING_DAY				= "giving_day";
	
	/**
	 * 廃棄日数。
	 */
	public static final String	COL_CANCEL_DAY				= "cancel_day";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG			= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_TMT_STOCK_HOLIDAY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmtStockHolidayDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmtStockHolidayDto dto = new TmtStockHolidayDto();
		dto.setTmtStockHolidayId(getLong(COL_TMT_STOCK_HOLIDAY_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setAcquisitionDate(getDate(COL_ACQUISITION_DATE));
		dto.setGivingDay(getDouble(COL_GIVING_DAY));
		dto.setCancelDay(getDouble(COL_CANCEL_DAY));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<StockHolidayTransactionDtoInterface> mappingAll() throws MospException {
		List<StockHolidayTransactionDtoInterface> all = new ArrayList<StockHolidayTransactionDtoInterface>();
		while (next()) {
			all.add((StockHolidayTransactionDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public StockHolidayTransactionDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
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
			StockHolidayTransactionDtoInterface dto = null;
			if (next()) {
				dto = (StockHolidayTransactionDtoInterface)mapping();
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
	public StockHolidayTransactionDtoInterface findForInfo(String personalId, Date activateDate, Date acquisitionDate)
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
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ? ");
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, acquisitionDate);
			setParam(index++, activateDate);
			executeQuery();
			StockHolidayTransactionDtoInterface dto = null;
			if (next()) {
				dto = (StockHolidayTransactionDtoInterface)mapping();
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
	public List<StockHolidayTransactionDtoInterface> findForList(String personalId, Date acquisitionDate,
			Date startDate, Date endDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(inactivateFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_ACQUISITION_DATE));
			if (startDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_ACTIVATE_DATE));
			}
			if (endDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_ACTIVATE_DATE));
			}
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, acquisitionDate);
			if (startDate != null) {
				setParam(index++, startDate);
			}
			if (endDate != null) {
				setParam(index++, endDate);
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
	public StockHolidayTransactionDtoInterface findForKey(String personalId, Date activateDate, String inactivateFlag)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			if (activateDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_ACTIVATE_DATE));
			}
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			sb.append(getOrderBy()).append(COL_ACTIVATE_DATE).append(getDescLimit1());
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			if (activateDate != null) {
				setParam(index++, activateDate);
			}
			if (!inactivateFlag.isEmpty()) {
				setParam(index++, Integer.parseInt(inactivateFlag));
			}
			executeQuery();
			StockHolidayTransactionDtoInterface dto = null;
			if (next()) {
				dto = (StockHolidayTransactionDtoInterface)mapping();
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
			StockHolidayTransactionDtoInterface dto = (StockHolidayTransactionDtoInterface)baseDto;
			setParam(index++, dto.getTmtStockHolidayId());
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
			StockHolidayTransactionDtoInterface dto = (StockHolidayTransactionDtoInterface)baseDto;
			setParam(index++, dto.getTmtStockHolidayId());
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
		StockHolidayTransactionDtoInterface dto = (StockHolidayTransactionDtoInterface)baseDto;
		setParam(index++, dto.getTmtStockHolidayId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getAcquisitionDate());
		setParam(index++, dto.getGivingDay());
		setParam(index++, dto.getCancelDay());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
}
