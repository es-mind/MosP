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
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.HolidayDaoInterface;
import jp.mosp.time.dto.settings.HolidayDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmHolidayDto;

/**
 * 休暇種別管理DAOクラス。
 */
public class TmmHolidayDao extends PlatformDao implements HolidayDaoInterface {
	
	/**
	 * 休暇種別マスタ。
	 */
	public static final String	TABLE						= "tmm_holiday";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_HOLIDAY_ID			= "tmm_holiday_id";
	
	/**
	 * 休暇コード。
	 */
	public static final String	COL_HOLIDAY_CODE			= "holiday_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE			= "activate_date";
	
	/**
	 * 休暇区分。
	 */
	public static final String	COL_HOLIDAY_TYPE			= "holiday_type";
	
	/**
	 * 休暇名称。
	 */
	public static final String	COL_HOLIDAY_NAME			= "holiday_name";
	
	/**
	 * 休暇略称。
	 */
	public static final String	COL_HOLIDAY_ABBR			= "holiday_abbr";
	
	/**
	 * 標準付与日数。
	 */
	public static final String	COL_HOLIDAY_GIVING			= "holiday_giving";
	
	/**
	 * 付与日数無制限。
	 */
	public static final String	COL_NO_LIMIT				= "no_limit";
	
	/**
	 * 取得期限(月)。
	 */
	public static final String	COL_HOLIDAY_LIMIT_MONTH		= "holiday_limit_month";
	
	/**
	 * 取得期限(日)。
	 */
	public static final String	COL_HOLIDAY_LIMIT_DAY		= "holiday_limit_day";
	
	/**
	 * 半休申請。
	 */
	public static final String	COL_HALF_HOLIDAY_REQUEST	= "half_holiday_request";
	
	/**
	 * 連続取得。
	 */
	public static final String	COL_CONTINUOUS_ACQUISITION	= "continuous_acquisition";
	
	/**
	 * 時間単位休暇機能。
	 */
	public static final String	COL_TIMELY_HOLIDAY_FLAG		= "timely_holiday_flag";
	
	/**
	 * 出勤率計算。
	 */
	public static final String	COL_PAID_HOLIDAY_CALC		= "paid_holiday_calc";
	
	/**
	 * 有給/無給。
	 */
	public static final String	COL_SALARY					= "salary";
	
	/**
	 * 理由種別。
	 */
	public static final String	COL_REASON_TYPE				= "reason_type";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG			= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_TMM_HOLIDAY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmHolidayDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmHolidayDto dto = new TmmHolidayDto();
		dto.setTmmHolidayId(getLong(COL_TMM_HOLIDAY_ID));
		dto.setHolidayCode(getString(COL_HOLIDAY_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setHolidayType(getInt(COL_HOLIDAY_TYPE));
		dto.setHolidayName(getString(COL_HOLIDAY_NAME));
		dto.setHolidayAbbr(getString(COL_HOLIDAY_ABBR));
		dto.setHolidayGiving(getDouble(COL_HOLIDAY_GIVING));
		dto.setNoLimit(getInt(COL_NO_LIMIT));
		dto.setHolidayLimitMonth(getInt(COL_HOLIDAY_LIMIT_MONTH));
		dto.setHolidayLimitDay(getInt(COL_HOLIDAY_LIMIT_DAY));
		dto.setHalfHolidayRequest(getInt(COL_HALF_HOLIDAY_REQUEST));
		dto.setContinuousAcquisition(getInt(COL_CONTINUOUS_ACQUISITION));
		dto.setTimelyHolidayFlag(getInt(COL_TIMELY_HOLIDAY_FLAG));
		dto.setPaidHolidayCalc(getInt(COL_PAID_HOLIDAY_CALC));
		dto.setSalary(getInt(COL_SALARY));
		dto.setReasonType(getInt(COL_REASON_TYPE));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<HolidayDtoInterface> mappingAll() throws MospException {
		List<HolidayDtoInterface> all = new ArrayList<HolidayDtoInterface>();
		while (next()) {
			all.add((HolidayDtoInterface)mapping());
		}
		return all;
	}
	
	/**
	 * ResultSetの内容を、休暇種別情報群として取得する。<br>
	 * @return 休暇種別情報群
	 * @throws MospException SQL例外が発生した場合
	 */
	protected Set<HolidayDtoInterface> mappingAllSet() throws MospException {
		Set<HolidayDtoInterface> all = new HashSet<HolidayDtoInterface>();
		while (next()) {
			all.add((HolidayDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<HolidayDtoInterface> findForActivateDate(Date activateDate, int holidayType) throws MospException {
		try {
			index = 1;
			// SELECT部追加
			StringBuffer sb = getSelectQuery(getClass());
			// 対象日以前で削除されていない最新の情報を取得
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ACTIVATE_DATE, COL_HOLIDAY_CODE, COL_HOLIDAY_TYPE));
			// WHERE部追加
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(COL_INACTIVATE_FLAG);
			sb.append(" = ");
			sb.append(MospConst.INACTIVATE_FLAG_OFF);
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE));
			sb.append(getOrderByColumn(COL_HOLIDAY_CODE));
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			setParam(index++, holidayType);
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
	public Set<HolidayDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			// SELECT部追加
			StringBuffer sb = getSelectQuery(getClass());
			// 対象日以前で削除されていない最新の情報を取得
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ACTIVATE_DATE, COL_HOLIDAY_CODE, COL_HOLIDAY_TYPE));
			// WHERE部追加
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(inactivateFlagOff());
			// ステートメント準備
			prepareStatement(sb.toString());
			// パラメータ設定
			setParam(index++, activateDate);
			// SQL実行
			executeQuery();
			// 結果取得
			return mappingAllSet();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public List<HolidayDtoInterface> findForHistory(String holidayCode, int holidayType) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holidayCode);
			setParam(index++, holidayType);
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
	public HolidayDtoInterface findForKey(String holidayCode, Date activateDate, int holidayType) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE));
			prepareStatement(sb.toString());
			setParam(index++, holidayCode);
			setParam(index++, activateDate);
			setParam(index++, holidayType);
			executeQuery();
			HolidayDtoInterface dto = null;
			if (next()) {
				dto = (HolidayDtoInterface)mapping();
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
	public HolidayDtoInterface findForInfo(String holidayCode, Date activateDate, int holidayType)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_CODE));
			sb.append(and());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ? ");
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE));
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, holidayCode);
			setParam(index++, activateDate);
			setParam(index++, holidayType);
			executeQuery();
			HolidayDtoInterface dto = null;
			if (next()) {
				dto = (HolidayDtoInterface)mapping();
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
	public List<HolidayDtoInterface> findForExport(Date activateDate, int holidayType) throws MospException {
		try {
			index = 1;
			// SELECT部追加
			StringBuffer sb = getSelectQuery(getClass());
			// 対象日以前で削除されていない最新の情報を取得
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ACTIVATE_DATE, COL_HOLIDAY_CODE, COL_HOLIDAY_TYPE));
			// WHERE部追加
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE));
			sb.append(getOrderByColumn(COL_HOLIDAY_CODE));
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			setParam(index++, holidayType);
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
	public List<HolidayDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			Date activateDate = (Date)param.get("activateDate");
			String holidayCode = String.valueOf(param.get("holidayCode"));
			String holidayType = String.valueOf(param.get("holidayType"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			index = 1;
			// SELECT部追加
			StringBuffer sb = getSelectQuery(getClass());
			// 対象日以前で削除されていない最新の情報を取得
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ACTIVATE_DATE, COL_HOLIDAY_CODE, COL_HOLIDAY_TYPE));
			// WHERE部追加
			sb.append(where());
			sb.append(deleteFlagOff());
			// 休暇コード条件
			if (!holidayCode.isEmpty()) {
				sb.append(and());
				sb.append(like(COL_HOLIDAY_CODE));
			}
			// 休暇区分条件
			if (!holidayType.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_HOLIDAY_TYPE));
			}
			// 無効フラグ条件
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			prepareStatement(sb.toString());
			setParam(index++, activateDate);
			if (!holidayCode.isEmpty()) {
				setParam(index++, startWithParam(holidayCode));
			}
			if (!holidayType.isEmpty()) {
				setParam(index++, Integer.parseInt(holidayType));
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
			HolidayDtoInterface dto = (HolidayDtoInterface)baseDto;
			setParam(index++, dto.getTmmHolidayId());
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
			HolidayDtoInterface dto = (HolidayDtoInterface)baseDto;
			setParam(index++, dto.getTmmHolidayId());
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
		HolidayDtoInterface dto = (HolidayDtoInterface)baseDto;
		setParam(index++, dto.getTmmHolidayId());
		setParam(index++, dto.getHolidayCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getHolidayType());
		setParam(index++, dto.getHolidayName());
		setParam(index++, dto.getHolidayAbbr());
		setParam(index++, dto.getHolidayGiving());
		setParam(index++, dto.getNoLimit());
		setParam(index++, dto.getHolidayLimitMonth());
		setParam(index++, dto.getHolidayLimitDay());
		setParam(index++, dto.getHalfHolidayRequest());
		setParam(index++, dto.getContinuousAcquisition());
		setParam(index++, dto.getTimelyHolidayFlag());
		setParam(index++, dto.getPaidHolidayCalc());
		setParam(index++, dto.getSalary());
		setParam(index++, dto.getReasonType());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
}
