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
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.PaidHolidayDataDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayDataDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdPaidHolidayDataDto;

/**
 * 有給休暇データDAOクラス。
 */
public class TmdPaidHolidayDao extends PlatformDao implements PaidHolidayDataDaoInterface {
	
	/**
	 * 有給休暇データ。
	 */
	public static final String	TABLE						= "tmd_paid_holiday";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_PAID_HOLIDAY_ID		= "tmd_paid_holiday_id";
	
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
	 * 保有時間数。
	 */
	public static final String	COL_HOLD_HOUR				= "hold_hour";
	
	/**
	 * 付与日数。
	 */
	public static final String	COL_GIVING_DAY				= "giving_day";
	
	/**
	 * 付与時間数。
	 */
	public static final String	COL_GIVING_HOUR				= "giving_hour";
	
	/**
	 * 廃棄日数。
	 */
	public static final String	COL_CANCEL_DAY				= "cancel_day";
	
	/**
	 * 廃棄時間数。
	 */
	public static final String	COL_CANCEL_HOUR				= "cancel_hour";
	
	/**
	 * 使用日数。
	 */
	public static final String	COL_USE_DAY					= "use_day";
	
	/**
	 * 使用時間数。
	 */
	public static final String	COL_USE_HOUR				= "use_hour";
	
	/**
	 * 時間変換日分母。
	 */
	public static final String	COL_DENOMINATOR_DAY_HOUR	= "denominator_day_hour";
	
	/**
	 * 仮付与フラグ。
	 */
	public static final String	COL_TEMPORARY_FLAG			= "temporary_flag";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG			= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_TMD_PAID_HOLIDAY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdPaidHolidayDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdPaidHolidayDataDto dto = new TmdPaidHolidayDataDto();
		dto.setTmdPaidHolidayId(getLong(COL_TMD_PAID_HOLIDAY_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setAcquisitionDate(getDate(COL_ACQUISITION_DATE));
		dto.setLimitDate(getDate(COL_LIMIT_DATE));
		dto.setHoldDay(getDouble(COL_HOLD_DAY));
		dto.setHoldHour(getInt(COL_HOLD_HOUR));
		dto.setGivingDay(getDouble(COL_GIVING_DAY));
		dto.setGivingHour(getInt(COL_GIVING_HOUR));
		dto.setCancelDay(getDouble(COL_CANCEL_DAY));
		dto.setCancelHour(getInt(COL_CANCEL_HOUR));
		dto.setUseDay(getDouble(COL_USE_DAY));
		dto.setUseHour(getInt(COL_USE_HOUR));
		dto.setDenominatorDayHour(getInt(COL_DENOMINATOR_DAY_HOUR));
		dto.setTemporaryFlag(getInt(COL_TEMPORARY_FLAG));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<PaidHolidayDataDtoInterface> mappingAll() throws MospException {
		List<PaidHolidayDataDtoInterface> all = new ArrayList<PaidHolidayDataDtoInterface>();
		while (next()) {
			all.add((PaidHolidayDataDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public PaidHolidayDataDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
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
			PaidHolidayDataDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayDataDtoInterface)mapping();
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
	public List<PaidHolidayDataDtoInterface> findForHistory(String personalId, Date acquisitionDate)
			throws MospException {
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
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, acquisitionDate);
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
	public List<PaidHolidayDataDtoInterface> findForLimit(String personalId, Date limitDate) throws MospException {
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
			sb.append(greaterEqual(COL_LIMIT_DATE));
			sb.append(getOrderByColumn(COL_ACQUISITION_DATE, COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, limitDate);
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
	public List<PaidHolidayDataDtoInterface> findForList(String personalId, Date targetDate, Date acquisitionDate,
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
	public List<PaidHolidayDataDtoInterface> findForNextInfoList(String personalId, Date targetDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMinActivateDate());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(inactivateFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(greater(COL_ACQUISITION_DATE));
			sb.append(getOrderByColumn(COL_ACQUISITION_DATE));
			prepareStatement(sb.toString());
			setParam(index++, targetDate);
			setParam(index++, personalId);
			setParam(index++, targetDate);
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
	public PaidHolidayDataDtoInterface findForExpirationDateInfo(String personalId, Date targetDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ACTIVATE_DATE, COL_PERSONAL_ID, COL_ACQUISITION_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(inactivateFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_ACQUISITION_DATE));
			sb.append(and());
			sb.append(less(COL_LIMIT_DATE));
			sb.append(getOrderByColumnDescLimit1(COL_LIMIT_DATE));
			prepareStatement(sb.toString());
			setParam(index++, targetDate);
			setParam(index++, personalId);
			setParam(index++, targetDate);
			setParam(index++, targetDate);
			executeQuery();
			PaidHolidayDataDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayDataDtoInterface)mapping();
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
	public List<PaidHolidayDataDtoInterface> findForExpirationDateList(String personalId, Date limitDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ACTIVATE_DATE, COL_PERSONAL_ID, COL_ACQUISITION_DATE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(inactivateFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_ACQUISITION_DATE));
			sb.append(and());
			sb.append(equal(COL_LIMIT_DATE));
			sb.append(getOrderByColumn(COL_ACQUISITION_DATE));
			prepareStatement(sb.toString());
			setParam(index++, limitDate);
			setParam(index++, personalId);
			setParam(index++, limitDate);
			setParam(index++, limitDate);
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
			PaidHolidayDataDtoInterface dto = (PaidHolidayDataDtoInterface)baseDto;
			setParam(index++, dto.getTmdPaidHolidayId());
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
			PaidHolidayDataDtoInterface dto = (PaidHolidayDataDtoInterface)baseDto;
			setParam(index++, dto.getTmdPaidHolidayId());
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
		PaidHolidayDataDtoInterface dto = (PaidHolidayDataDtoInterface)baseDto;
		setParam(index++, dto.getTmdPaidHolidayId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getAcquisitionDate());
		setParam(index++, dto.getLimitDate());
		setParam(index++, dto.getHoldDay());
		setParam(index++, dto.getHoldHour());
		setParam(index++, dto.getGivingDay());
		setParam(index++, dto.getGivingHour());
		setParam(index++, dto.getCancelDay());
		setParam(index++, dto.getCancelHour());
		setParam(index++, dto.getUseDay());
		setParam(index++, dto.getUseHour());
		setParam(index++, dto.getDenominatorDayHour());
		setParam(index++, dto.getTemporaryFlag());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	/**
	 * 対象日より後で最も古い情報を抽出する条件SQLを取得する。<br>
	 * @return 対象日より後で最も古い情報を抽出する条件SQL
	 */
	protected String getQueryForMinActivateDate() {
		// SQL作成準備
		StringBuffer sb = new StringBuffer();
		// 有効日における最新の情報を抽出する条件SQLを追加
		sb.append(join());
		sb.append(leftParenthesis());
		sb.append(select());
		sb.append(COL_PERSONAL_ID);
		sb.append(as(getTmpColumn(COL_PERSONAL_ID)));
		sb.append(comma());
		sb.append(COL_ACQUISITION_DATE);
		sb.append(as(getTmpColumn(COL_ACQUISITION_DATE)));
		sb.append(comma());
		sb.append(minAs(COL_ACTIVATE_DATE));
		sb.append(from(TABLE));
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(greater(COL_ACQUISITION_DATE));
		sb.append(and());
		sb.append(COL_ACTIVATE_DATE);
		sb.append(greaterEqual());
		sb.append(COL_ACQUISITION_DATE);
		sb.append(groupBy(COL_PERSONAL_ID, COL_ACQUISITION_DATE));
		sb.append(rightParenthesis());
		sb.append(asTmpTable(TABLE));
		sb.append(on());
		sb.append(COL_PERSONAL_ID);
		sb.append(equal());
		sb.append(getExplicitTableColumn(getTmpTable(TABLE), getTmpColumn(COL_PERSONAL_ID)));
		sb.append(and());
		sb.append(COL_ACQUISITION_DATE);
		sb.append(equal());
		sb.append(getExplicitTableColumn(getTmpTable(TABLE), getTmpColumn(COL_ACQUISITION_DATE)));
		sb.append(and());
		sb.append(COL_ACTIVATE_DATE);
		sb.append(equal());
		sb.append(getExplicitTableColumn(getTmpTable(TABLE), getMinColumn(COL_ACTIVATE_DATE)));
		return sb.toString();
	}
	
	/**
	 * 
	 * @param column 対象カラム
	 * @return min(column) AS min_column
	 */
	protected static String minAs(String column) {
		StringBuffer sb = new StringBuffer();
		sb.append(min(column));
		sb.append(as(getMinColumn(column)));
		return sb.toString();
	}
	
	/**
	 * 
	 * @param column 対象カラム
	 * @return min(column)
	 */
	protected static String min(String column) {
		StringBuffer sb = new StringBuffer();
		sb.append("min(");
		sb.append(column);
		sb.append(") ");
		return sb.toString();
	}
	
	/**
	 * 一時カラム名取得。
	 * @param column 対象カラム
	 * @return min_対象カラム
	 */
	protected static String getMinColumn(String column) {
		return "min_" + column;
	}
	
}
