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
package jp.mosp.time.dao.settings.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.PaidHolidayDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmPaidHolidayDto;

/**
 * 有給休暇設定DAOクラス。
 */
public class TmmPaidHolidayDao extends PlatformDao implements PaidHolidayDaoInterface {
	
	/**
	 * 有給休暇設定。
	 */
	public static final String	TABLE								= "tmm_paid_holiday";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_PAID_HOLIDAY_ID				= "tmm_paid_holiday_id";
	
	/**
	 * 有休コード。
	 */
	public static final String	COL_PAID_HOLIDAY_CODE				= "paid_holiday_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE					= "activate_date";
	
	/**
	 * 有休名称。
	 */
	public static final String	COL_PAID_HOLIDAY_NAME				= "paid_holiday_name";
	
	/**
	 * 有休略称。
	 */
	public static final String	COL_PAID_HOLIDAY_ABBR				= "paid_holiday_abbr";
	
	/**
	 * 付与区分。
	 */
	public static final String	COL_PAID_HOLIDAY_TYPE				= "paid_holiday_type";
	
	/**
	 * 出勤率。
	 */
	public static final String	COL_WORK_RATIO						= "work_ratio";
	
	/**
	 * 仮付与日。
	 */
	public static final String	COL_SCHEDULE_GIVING					= "schedule_giving";
	
	/**
	 * 時間単位有休機能。
	 */
	public static final String	COL_TIMELY_PAID_HOLIDAY_FLAG		= "timely_paid_holiday_flag";
	
	/**
	 * 有休単位時間。
	 */
	public static final String	COL_TIMELY_PAID_HOLIDAY_TIME		= "timely_paid_holiday_time";
	
	/**
	 * 有休時間取得限度日数。
	 */
	public static final String	COL_TIME_ACQUISITION_LIMIT_DAYS		= "time_acquisition_limit_days";
	
	/**
	 * 有休時間取得限度時間。
	 */
	public static final String	COL_TIME_ACQUISITION_LIMIT_TIMES	= "time_acquisition_limit_times";
	
	/**
	 * 申請時間間隔。
	 */
	public static final String	COL_APPLI_TIME_INTERVAL				= "appli_time_interval";
	
	/**
	 * 最大繰越日数。
	 */
	public static final String	COL_MAX_CARRY_OVER_AMOUNT			= "max_carry_over_amount";
	
	/**
	 * 合計最大保有日数。
	 */
	public static final String	COL_TOTAL_MAX_AMOUNT				= "total_max_amount";
	
	/**
	 * 最大繰越年数。
	 */
	public static final String	COL_MAX_CARRY_OVER_YEAR				= "max_carry_over_year";
	
	/**
	 * 時間単位繰越。
	 */
	public static final String	COL_MAX_CARRY_OVER_TIMES			= "max_carry_over_times";
	
	/**
	 * 半日単位取得。
	 */
	public static final String	COL_HALF_DAY_UNIT_FLAG				= "half_day_unit_flag";
	
	/**
	 * 休日出勤取扱。
	 */
	public static final String	COL_WORK_ON_HOLIDAY_CALC			= "work_on_holiday_calc";
	
	/**
	 * 基準日(月)。
	 */
	public static final String	COL_POINT_DATE_MONTH				= "point_date_month";
	
	/**
	 * 基準日(日)。
	 */
	public static final String	COL_POINT_DATE_DAY					= "point_date_day";
	
	/**
	 * 登録情報超過後(基準日)。
	 */
	public static final String	COL_GENERAL_POINT_AMOUNT			= "general_point_amount";
	
	/**
	 * 登録情報超過後(月)。
	 */
	public static final String	COL_GENERAL_JOINING_MONTH			= "general_joining_month";
	
	/**
	 * 登録情報超過後(日)。
	 */
	public static final String	COL_GENERAL_JOINING_AMOUNT			= "general_joining_amount";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG					= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1								= COL_TMM_PAID_HOLIDAY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmPaidHolidayDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmPaidHolidayDto dto = new TmmPaidHolidayDto();
		dto.setTmmPaidHolidayId(getLong(COL_TMM_PAID_HOLIDAY_ID));
		dto.setPaidHolidayCode(getString(COL_PAID_HOLIDAY_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setPaidHolidayName(getString(COL_PAID_HOLIDAY_NAME));
		dto.setPaidHolidayAbbr(getString(COL_PAID_HOLIDAY_ABBR));
		dto.setPaidHolidayType(getInt(COL_PAID_HOLIDAY_TYPE));
		dto.setWorkRatio(getInt(COL_WORK_RATIO));
		dto.setScheduleGiving(getInt(COL_SCHEDULE_GIVING));
		dto.setTimelyPaidHolidayFlag(getInt(COL_TIMELY_PAID_HOLIDAY_FLAG));
		dto.setTimelyPaidHolidayTime(getInt(COL_TIMELY_PAID_HOLIDAY_TIME));
		dto.setTimeAcquisitionLimitDays(getInt(COL_TIME_ACQUISITION_LIMIT_DAYS));
		dto.setTimeAcquisitionLimitTimes(getInt(COL_TIME_ACQUISITION_LIMIT_TIMES));
		dto.setAppliTimeInterval(getInt(COL_APPLI_TIME_INTERVAL));
		dto.setMaxCarryOverAmount(getInt(COL_MAX_CARRY_OVER_AMOUNT));
		dto.setTotalMaxAmount(getInt(COL_TOTAL_MAX_AMOUNT));
		dto.setMaxCarryOverYear(getInt(COL_MAX_CARRY_OVER_YEAR));
		dto.setMaxCarryOverTimes(getInt(COL_MAX_CARRY_OVER_TIMES));
		dto.setHalfDayUnit(getInt(COL_HALF_DAY_UNIT_FLAG));
		dto.setWorkOnHolidayCalc(getInt(COL_WORK_ON_HOLIDAY_CALC));
		dto.setPointDateMonth(getInt(COL_POINT_DATE_MONTH));
		dto.setPointDateDay(getInt(COL_POINT_DATE_DAY));
		dto.setGeneralPointAmount(getInt(COL_GENERAL_POINT_AMOUNT));
		dto.setGeneralJoiningMonth(getInt(COL_GENERAL_JOINING_MONTH));
		dto.setGeneralJoiningAmount(getInt(COL_GENERAL_JOINING_AMOUNT));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<PaidHolidayDtoInterface> mappingAll() throws MospException {
		List<PaidHolidayDtoInterface> all = new ArrayList<PaidHolidayDtoInterface>();
		while (next()) {
			all.add((PaidHolidayDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<PaidHolidayDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(COL_INACTIVATE_FLAG);
			sb.append(" = ");
			sb.append(MospConst.DELETE_FLAG_OFF);
			sb.append(" ");
			sb.append(and());
			sb.append(getQueryForMaxActivateDate());
			sb.append(getOrderByColumn(COL_PAID_HOLIDAY_CODE));
			prepareStatement(sb.toString());
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
	public List<PaidHolidayDtoInterface> findForHistory(String paidHolidayCode) throws MospException {
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
	public PaidHolidayDtoInterface findForKey(String paidHolidayCode, Date activateDate) throws MospException {
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
			PaidHolidayDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayDtoInterface)mapping();
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
	public PaidHolidayDtoInterface findForInfo(String paidHolidayCode, Date activateDate) throws MospException {
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
			PaidHolidayDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayDtoInterface)mapping();
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
	public List<PaidHolidayDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			Date activateDate = (Date)param.get("activateDate");
			String paidHolidayCode = String.valueOf(param.get("paidHolidayCode"));
			String paidHolidayName = String.valueOf(param.get("paidHolidayName"));
			String paidHolidayAbbr = String.valueOf(param.get("paidHolidayAbbr"));
			String paidHolidayType = String.valueOf(param.get("paidHolidayType"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_PAID_HOLIDAY_CODE));
			sb.append(and());
			sb.append(like(COL_PAID_HOLIDAY_NAME));
			sb.append(and());
			sb.append(like(COL_PAID_HOLIDAY_ABBR));
			if (!paidHolidayType.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_PAID_HOLIDAY_TYPE));
			}
			if (activateDate != null) {
				sb.append(and());
				sb.append(getQueryForMaxActivateDate());
			}
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			prepareStatement(sb.toString());
			setParam(index++, startWithParam(paidHolidayCode));
			setParam(index++, containsParam(paidHolidayName));
			setParam(index++, containsParam(paidHolidayAbbr));
			if (!paidHolidayType.isEmpty()) {
				setParam(index++, Integer.parseInt(paidHolidayType));
			}
			if (activateDate != null) {
				setParam(index++, activateDate);
			}
			if (!inactivateFlag.isEmpty()) {
				setParam(index++, Integer.parseInt(inactivateFlag));
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
			PaidHolidayDtoInterface dto = (PaidHolidayDtoInterface)baseDto;
			setParam(index++, dto.getTmmPaidHolidayId());
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
			PaidHolidayDtoInterface dto = (PaidHolidayDtoInterface)baseDto;
			setParam(index++, dto.getTmmPaidHolidayId());
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
		PaidHolidayDtoInterface dto = (PaidHolidayDtoInterface)baseDto;
		setParam(index++, dto.getTmmPaidHolidayId());
		setParam(index++, dto.getPaidHolidayCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getPaidHolidayName());
		setParam(index++, dto.getPaidHolidayAbbr());
		setParam(index++, dto.getPaidHolidayType());
		setParam(index++, dto.getWorkRatio());
		setParam(index++, dto.getScheduleGiving());
		setParam(index++, dto.getTimelyPaidHolidayFlag());
		setParam(index++, dto.getTimelyPaidHolidayTime());
		setParam(index++, dto.getTimeAcquisitionLimitDays());
		setParam(index++, dto.getTimeAcquisitionLimitTimes());
		setParam(index++, dto.getAppliTimeInterval());
		setParam(index++, dto.getMaxCarryOverAmount());
		setParam(index++, dto.getTotalMaxAmount());
		setParam(index++, dto.getMaxCarryOverYear());
		setParam(index++, dto.getMaxCarryOverTimes());
		setParam(index++, dto.getHalfDayUnit());
		setParam(index++, dto.getWorkOnHolidayCalc());
		setParam(index++, dto.getPointDateMonth());
		setParam(index++, dto.getPointDateDay());
		setParam(index++, dto.getGeneralPointAmount());
		setParam(index++, dto.getGeneralJoiningMonth());
		setParam(index++, dto.getGeneralJoiningAmount());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public StringBuffer getQueryForMaxActivateDate() {
		StringBuffer sb = new StringBuffer();
		sb.append(COL_ACTIVATE_DATE);
		sb.append(" IN (SELECT ");
		sb.append("MAX(" + COL_ACTIVATE_DATE + ")");
		sb.append(from(TABLE) + " AS A ");
		sb.append(where());
		sb.append(TABLE + "." + COL_PAID_HOLIDAY_CODE);
		sb.append(" = A." + COL_PAID_HOLIDAY_CODE);
		sb.append(and());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(COL_ACTIVATE_DATE);
		sb.append(" <= ?)");
		return sb;
	}
	
}
