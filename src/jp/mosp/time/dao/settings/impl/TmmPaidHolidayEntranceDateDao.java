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
import jp.mosp.time.dao.settings.PaidHolidayEntranceDateDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayEntranceDateDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmPaidHolidayEntranceDateDto;

/**
 * 有給休暇自動付与(入社日)マスタDAOクラス。
 */
public class TmmPaidHolidayEntranceDateDao extends PlatformDao implements PaidHolidayEntranceDateDaoInterface {
	
	/**
	 * 有給休暇自動付与(入社日)マスタ。
	 */
	public static final String	TABLE									= "tmm_paid_holiday_entrance_date";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_PAID_HOLIDAY_ENTRANCE_DATE_ID	= "tmm_paid_holiday_entrance_date_id";
	
	/**
	 * 有休コード。
	 */
	public static final String	COL_PAID_HOLIDAY_CODE					= "paid_holiday_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE						= "activate_date";
	
	/**
	 * 勤続勤務月数。
	 */
	public static final String	COL_WORK_MONTH							= "work_month";
	
	/**
	 * 付与日数。
	 */
	public static final String	COL_JOINING_DATE_AMOUNT					= "joining_date_amount";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG						= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1									= COL_TMM_PAID_HOLIDAY_ENTRANCE_DATE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmPaidHolidayEntranceDateDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmPaidHolidayEntranceDateDto dto = new TmmPaidHolidayEntranceDateDto();
		dto.setTmmPaidHolidayEntranceDateId(getLong(COL_TMM_PAID_HOLIDAY_ENTRANCE_DATE_ID));
		dto.setPaidHolidayCode(getString(COL_PAID_HOLIDAY_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setWorkMonth(getInt(COL_WORK_MONTH));
		dto.setJoiningDateAmount(getInt(COL_JOINING_DATE_AMOUNT));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<PaidHolidayEntranceDateDtoInterface> mappingAll() throws MospException {
		List<PaidHolidayEntranceDateDtoInterface> all = new ArrayList<PaidHolidayEntranceDateDtoInterface>();
		while (next()) {
			all.add((PaidHolidayEntranceDateDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<PaidHolidayEntranceDateDtoInterface> findForHistory(String paidHolidayCode, int workMonth)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_WORK_MONTH));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, workMonth);
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
	public PaidHolidayEntranceDateDtoInterface findForKey(String paidHolidayCode, Date activateDate, int workMonth)
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
			sb.append(equal(COL_WORK_MONTH));
			prepareStatement(sb.toString());
			setParam(index++, paidHolidayCode);
			setParam(index++, activateDate);
			setParam(index++, workMonth);
			executeQuery();
			PaidHolidayEntranceDateDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayEntranceDateDtoInterface)mapping();
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
	public List<PaidHolidayEntranceDateDtoInterface> findForList(String paidHolidayCode, Date activateDate)
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
			PaidHolidayEntranceDateDtoInterface dto = (PaidHolidayEntranceDateDtoInterface)baseDto;
			setParam(index++, dto.getTmmPaidHolidayEntranceDateId());
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
			PaidHolidayEntranceDateDtoInterface dto = (PaidHolidayEntranceDateDtoInterface)baseDto;
			setParam(index++, dto.getTmmPaidHolidayEntranceDateId());
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
		PaidHolidayEntranceDateDtoInterface dto = (PaidHolidayEntranceDateDtoInterface)baseDto;
		setParam(index++, dto.getTmmPaidHolidayEntranceDateId());
		setParam(index++, dto.getPaidHolidayCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getWorkMonth());
		setParam(index++, dto.getJoiningDateAmount());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
}
