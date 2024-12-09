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
import java.util.Collection;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.SubHolidayDaoInterface;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdSubHolidayDto;

/**
 * 代休データDAOクラス。
 */
public class TmdSubHolidayDao extends PlatformDao implements SubHolidayDaoInterface {
	
	/**
	 * 代休データ。
	 */
	public static final String	TABLE					= "tmd_sub_holiday";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_SUB_HOLIDAY_ID	= "tmd_sub_holiday_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID			= "personal_id";
	
	/**
	 * 出勤日。
	 */
	public static final String	COL_WORK_DATE			= "work_date";
	
	/**
	 * 勤務回数。
	 */
	public static final String	COL_TIMES_WORK			= "times_work";
	
	/**
	 * 代休種別。
	 */
	public static final String	COL_SUB_HOLIDAY_TYPE	= "sub_holiday_type";
	
	/**
	 * 代休日数。
	 */
	public static final String	COL_SUB_HOLIDAY_DAYS	= "sub_holiday_days";
	
	/**
	 * 移行フラグ。
	 */
	public static final String	COL_TRANSITION_FLAG		= "transition_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_TMD_SUB_HOLIDAY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdSubHolidayDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdSubHolidayDto dto = new TmdSubHolidayDto();
		dto.setTmdSubHolidayId(getLong(COL_TMD_SUB_HOLIDAY_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setWorkDate(getDate(COL_WORK_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setSubHolidayType(getInt(COL_SUB_HOLIDAY_TYPE));
		dto.setSubHolidayDays(getDouble(COL_SUB_HOLIDAY_DAYS));
		dto.setTransitionFlag(getInt(COL_TRANSITION_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<SubHolidayDtoInterface> mappingAll() throws MospException {
		List<SubHolidayDtoInterface> all = new ArrayList<SubHolidayDtoInterface>();
		while (next()) {
			all.add((SubHolidayDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public SubHolidayDtoInterface findForKey(String personalId, Date workDate, int timesWork, int subHolidayType)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			sb.append(and());
			sb.append(equal(COL_TIMES_WORK));
			sb.append(and());
			sb.append(equal(COL_SUB_HOLIDAY_TYPE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, timesWork);
			setParam(index++, subHolidayType);
			executeQuery();
			SubHolidayDtoInterface dto = null;
			if (next()) {
				dto = (SubHolidayDtoInterface)mapping();
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
	public List<SubHolidayDtoInterface> findForList(String personalId, Date workDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
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
	public List<SubHolidayDtoInterface> getSubHolidayList(String personalId, Date startDate, Date endDate,
			double subHolidayDays) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(greaterEqual(COL_WORK_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_WORK_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_SUB_HOLIDAY_DAYS));
			sb.append(getOrderByColumn(COL_WORK_DATE, COL_SUB_HOLIDAY_TYPE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, startDate);
			setParam(index++, endDate);
			setParam(index++, subHolidayDays);
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
			SubHolidayDtoInterface dto = (SubHolidayDtoInterface)baseDto;
			setParam(index++, dto.getTmdSubHolidayId());
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
			SubHolidayDtoInterface dto = (SubHolidayDtoInterface)baseDto;
			setParam(index++, dto.getTmdSubHolidayId());
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
		SubHolidayDtoInterface dto = (SubHolidayDtoInterface)baseDto;
		setParam(index++, dto.getTmdSubHolidayId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getWorkDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getSubHolidayType());
		setParam(index++, dto.getSubHolidayDays());
		setParam(index++, dto.getTransitionFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public List<SubHolidayDtoInterface> findForPersonalIds(Collection<String> personalIds, Date workDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(in(COL_PERSONAL_ID, personalIds.size()));
			sb.append(and());
			sb.append(equal(COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParamsStringIn(personalIds);
			setParam(index++, workDate);
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
	public List<SubHolidayDtoInterface> findSubHolidayList(String personalId, Date startDate, Date endDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(greaterEqual(COL_WORK_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, startDate);
			setParam(index++, endDate);
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
