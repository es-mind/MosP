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
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.HolidayDataDaoInterface;
import jp.mosp.time.dto.settings.HolidayDataDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdHolidayDataDto;

/**
 * 休暇データDAOクラス。
 */
public class TmdHolidayDataDao extends PlatformDao implements HolidayDataDaoInterface {
	
	/**
	 * 休暇データ。
	 */
	public static final String	TABLE					= "tmd_holiday";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_HOLIDAY_ID		= "tmd_holiday_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID			= "personal_id";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE		= "activate_date";
	
	/**
	 * 休暇コード。
	 */
	public static final String	COL_HOLIDAY_CODE		= "holiday_code";
	
	/**
	 * 休暇区分。
	 */
	public static final String	COL_HOLIDAY_TYPE		= "holiday_type";
	
	/**
	 * 付与日数。
	 */
	public static final String	COL_GIVING_DAY			= "giving_day";
	
	/**
	 * 付与時間数。
	 */
	public static final String	COL_GIVING_HOUR			= "giving_hour";
	
	/**
	 * 廃棄日数。
	 */
	public static final String	COL_CANCEL_DAY			= "cancel_day";
	
	/**
	 * 廃棄時間数。
	 */
	public static final String	COL_CANCEL_HOUR			= "cancel_hour";
	
	/**
	 * 取得期限。
	 */
	public static final String	COL_HOLIDAY_LIMIT_DATE	= "holiday_limit_date";
	
	/**
	 * 取得期限(月)。
	 */
	public static final String	COL_HOLIDAY_LIMIT_MONTH	= "holiday_limit_month";
	
	/**
	 * 取得期限(日)。
	 */
	public static final String	COL_HOLIDAY_LIMIT_DAY	= "holiday_limit_day";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG		= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_TMD_HOLIDAY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdHolidayDataDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdHolidayDataDto dto = new TmdHolidayDataDto();
		dto.setTmdHolidayId(getLong(COL_TMD_HOLIDAY_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setHolidayCode(getString(COL_HOLIDAY_CODE));
		dto.setHolidayType(getInt(COL_HOLIDAY_TYPE));
		dto.setGivingDay(getDouble(COL_GIVING_DAY));
		dto.setGivingHour(getInt(COL_GIVING_HOUR));
		dto.setCancelDay(getDouble(COL_CANCEL_DAY));
		dto.setCancelHour(getInt(COL_CANCEL_HOUR));
		dto.setHolidayLimitDate(getDate(COL_HOLIDAY_LIMIT_DATE));
		dto.setHolidayLimitMonth(getInt(COL_HOLIDAY_LIMIT_MONTH));
		dto.setHolidayLimitDay(getInt(COL_HOLIDAY_LIMIT_DAY));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<HolidayDataDtoInterface> mappingAll() throws MospException {
		List<HolidayDataDtoInterface> all = new ArrayList<HolidayDataDtoInterface>();
		while (next()) {
			all.add((HolidayDataDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public HolidayDataDtoInterface findForKey(String personalId, Date activateDate, String holidayCode, int holidayType)
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
			sb.append(equal(COL_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, activateDate);
			setParam(index++, holidayCode);
			setParam(index++, holidayType);
			executeQuery();
			HolidayDataDtoInterface dto = null;
			if (next()) {
				dto = (HolidayDataDtoInterface)mapping();
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
	public List<HolidayDataDtoInterface> findForInfoList(String personalId, Date activateDate, String inactivateFlag,
			int holidayType) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_HOLIDAY_LIMIT_DATE));
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, holidayType);
			setParam(index++, activateDate);
			setParam(index++, activateDate);
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
	public List<HolidayDataDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate,
			int holidayType) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			if (startDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_ACTIVATE_DATE));
			}
			if (endDate != null) {
				sb.append(and());
				sb.append(lessEqual(COL_ACTIVATE_DATE));
			}
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE));
			prepareStatement(sb.toString());
			if (startDate != null) {
				setParam(index++, startDate);
			}
			if (endDate != null) {
				setParam(index++, endDate);
			}
			setParam(index++, personalId);
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
	public List<HolidayDataDtoInterface> findForEarliestList(String personalId, Date activateDate, String holidayCode,
			int holidayType) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_INACTIVATE_FLAG, MospConst.DELETE_FLAG_OFF));
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_CODE));
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE));
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_HOLIDAY_LIMIT_DATE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, holidayCode);
			setParam(index++, holidayType);
			setParam(index++, activateDate);
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
			HolidayDataDtoInterface dto = (HolidayDataDtoInterface)baseDto;
			setParam(index++, dto.getTmdHolidayId());
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
			HolidayDataDtoInterface dto = (HolidayDataDtoInterface)baseDto;
			setParam(index++, dto.getTmdHolidayId());
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
		HolidayDataDtoInterface dto = (HolidayDataDtoInterface)baseDto;
		setParam(index++, dto.getTmdHolidayId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getHolidayCode());
		setParam(index++, dto.getHolidayType());
		setParam(index++, dto.getGivingDay());
		setParam(index++, dto.getGivingHour());
		setParam(index++, dto.getCancelDay());
		setParam(index++, dto.getCancelHour());
		setParam(index++, dto.getHolidayLimitDate());
		setParam(index++, dto.getHolidayLimitMonth());
		setParam(index++, dto.getHolidayLimitDay());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public List<HolidayDataDtoInterface> findForTerm(Date fromActivateDate, Date toActivateDate, String holidayCode,
			int holidayType) throws MospException {
		try {
			index = 1;
			// SELECT文追加
			StringBuffer sb = getSelectQuery(getClass());
			// WHERE句追加
			sb.append(where());
			// 削除されていない情報を取得
			sb.append(deleteFlagOff());
			// 有効日範囲による条件
			if (fromActivateDate != null) {
				sb.append(and());
				sb.append(greaterEqual(COL_HOLIDAY_LIMIT_DATE));
			}
			if (toActivateDate != null) {
				sb.append(and());
				sb.append(less(COL_ACTIVATE_DATE));
			}
			// 休暇コード
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_CODE));
			// 休暇区分
			sb.append(and());
			sb.append(equal(COL_HOLIDAY_TYPE));
			// ソート
			sb.append(getOrderByColumns(COL_HOLIDAY_TYPE, COL_HOLIDAY_CODE, COL_ACTIVATE_DATE, COL_HOLIDAY_LIMIT_DATE));
			// ステートメント準備
			prepareStatement(sb.toString());
			// パラメータ設定(有効日範囲による条件)
			if (fromActivateDate != null) {
				setParam(index++, fromActivateDate);
			}
			if (toActivateDate != null) {
				setParam(index++, toActivateDate);
			}
			setParam(index++, holidayCode);
			setParam(index++, holidayType);
			// SQL実行
			executeQuery();
			// 結果取得
			return mappingAll();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
