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
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.PaidHolidayPointDateDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayPointDateDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmPaidHolidayPointDateDto;

/**
 * 有給休暇自動付与(基準日)マスタDAOクラス。
 */
public class TmmPaidHolidayPointDateDao extends PlatformDao implements PaidHolidayPointDateDaoInterface {
	
	/**
	 * 有給休暇自動付与(基準日)マスタ。
	 */
	public static final String	TABLE								= "tmm_paid_holiday_point_date";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_PAID_HOLIDAY_POINT_DATE_ID	= "tmm_paid_holiday_point_date_id";
	
	/**
	 * 有休コード。
	 */
	public static final String	COL_PAID_HOLIDAY_CODE				= "paid_holiday_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE					= "activate_date";
	
	/**
	 * 基準日経過回数。
	 */
	public static final String	COL_TIMES_POINT_DATE				= "times_point_date";
	
	/**
	 * 付与日数。
	 */
	public static final String	COL_POINT_DATE_AMOUNT				= "point_date_amount";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG					= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1								= COL_TMM_PAID_HOLIDAY_POINT_DATE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmPaidHolidayPointDateDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmPaidHolidayPointDateDto dto = new TmmPaidHolidayPointDateDto();
		dto.setTmmPaidHolidayPointDateId(getLong(COL_TMM_PAID_HOLIDAY_POINT_DATE_ID));
		dto.setPaidHolidayCode(getString(COL_PAID_HOLIDAY_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setTimesPointDate(getInt(COL_TIMES_POINT_DATE));
		dto.setPointDateAmount(getInt(COL_POINT_DATE_AMOUNT));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<PaidHolidayPointDateDtoInterface> mappingAll() throws MospException {
		List<PaidHolidayPointDateDtoInterface> all = new ArrayList<PaidHolidayPointDateDtoInterface>();
		while (next()) {
			all.add((PaidHolidayPointDateDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<PaidHolidayPointDateDtoInterface> findForHistory(String paidHolidayCode, int timesPointDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_TIMES_POINT_DATE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, timesPointDate);
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
	public PaidHolidayPointDateDtoInterface findForKey(String paidHolidayCode, Date activateDate, int timesPointDate)
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
			sb.append(equal(COL_TIMES_POINT_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, activateDate);
			setParam(index++, timesPointDate);
			executeQuery();
			PaidHolidayPointDateDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayPointDateDtoInterface)mapping();
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
	public List<PaidHolidayPointDateDtoInterface> findForList(String paidHolidayCode, Date activateDate)
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
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, activateDate);
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
			PaidHolidayPointDateDtoInterface dto = (PaidHolidayPointDateDtoInterface)baseDto;
			setParam(index++, dto.getTmmPaidHolidayPointDateId());
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
			PaidHolidayPointDateDtoInterface dto = (PaidHolidayPointDateDtoInterface)baseDto;
			setParam(index++, dto.getTmmPaidHolidayPointDateId());
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
		PaidHolidayPointDateDtoInterface dto = (PaidHolidayPointDateDtoInterface)baseDto;
		setParam(index++, dto.getTmmPaidHolidayPointDateId());
		setParam(index++, dto.getPaidHolidayCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getTimesPointDate());
		setParam(index++, dto.getPointDateAmount());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
}
