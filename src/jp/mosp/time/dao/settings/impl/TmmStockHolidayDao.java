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
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.StockHolidayDaoInterface;
import jp.mosp.time.dto.settings.StockHolidayDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmStockHolidayDto;

/**
 * ストック休暇管理マスタDAOクラス。
 */
public class TmmStockHolidayDao extends PlatformDao implements StockHolidayDaoInterface {
	
	/**
	 * ストック休暇管理マスタ。
	 */
	public static final String	TABLE						= "tmm_stock_holiday";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_STOCK_HOLIDAY_ID	= "tmm_stock_holiday_id";
	
	/**
	 * 有休コード。
	 */
	public static final String	COL_PAID_HOLIDAY_CODE		= "paid_holiday_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE			= "activate_date";
	
	/**
	 * 最大年間積立日数。
	 */
	public static final String	COL_STOCK_YEAR_AMOUNT		= "stock_year_amount";
	
	/**
	 * 最大合計積立日数。
	 */
	public static final String	COL_STOCK_TOTAL_AMOUNT		= "stock_total_amount";
	
	/**
	 * 有効期限。
	 */
	public static final String	COL_STOCK_LIMIT_DATE		= "stock_limit_date";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG			= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_TMM_STOCK_HOLIDAY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmStockHolidayDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmStockHolidayDto dto = new TmmStockHolidayDto();
		dto.setTmmStockHolidayId(getLong(COL_TMM_STOCK_HOLIDAY_ID));
		dto.setPaidHolidayCode(getString(COL_PAID_HOLIDAY_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setStockYearAmount(getInt(COL_STOCK_YEAR_AMOUNT));
		dto.setStockTotalAmount(getInt(COL_STOCK_TOTAL_AMOUNT));
		dto.setStockLimitDate(getInt(COL_STOCK_LIMIT_DATE));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<StockHolidayDtoInterface> mappingAll() throws MospException {
		List<StockHolidayDtoInterface> all = new ArrayList<StockHolidayDtoInterface>();
		while (next()) {
			all.add((StockHolidayDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<StockHolidayDtoInterface> findForHistory(String paidHolidayCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
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
	public StockHolidayDtoInterface findForKey(String paidHolidayCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, activateDate);
			executeQuery();
			StockHolidayDtoInterface dto = null;
			if (next()) {
				dto = (StockHolidayDtoInterface)mapping();
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
	public StockHolidayDtoInterface findForInfo(String paidHolidayCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ? ");
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, activateDate);
			executeQuery();
			StockHolidayDtoInterface dto = null;
			if (next()) {
				dto = (StockHolidayDtoInterface)mapping();
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
			StockHolidayDtoInterface dto = (StockHolidayDtoInterface)baseDto;
			setParam(index++, dto.getTmmStockHolidayId());
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
			StockHolidayDtoInterface dto = (StockHolidayDtoInterface)baseDto;
			setParam(index++, dto.getTmmStockHolidayId());
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
		StockHolidayDtoInterface dto = (StockHolidayDtoInterface)baseDto;
		setParam(index++, dto.getTmmStockHolidayId());
		setParam(index++, dto.getPaidHolidayCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getStockYearAmount());
		setParam(index++, dto.getStockTotalAmount());
		setParam(index++, dto.getStockLimitDate());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
}
