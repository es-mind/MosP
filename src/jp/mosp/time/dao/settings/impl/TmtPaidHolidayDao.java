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
import jp.mosp.time.dao.settings.PaidHolidayTransactionDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayTransactionDtoInterface;
import jp.mosp.time.dto.settings.impl.TmtPaidHolidayTransactionDto;

/**
 * 有給休暇トランザクションDAOクラス。
 */
public class TmtPaidHolidayDao extends PlatformDao implements PaidHolidayTransactionDaoInterface {
	
	/**
	 * 有給休暇トランザクション。
	 */
	public static final String	TABLE					= "tmt_paid_holiday";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMT_PAID_HOLIDAY_ID	= "tmt_paid_holiday_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID			= "personal_id";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE		= "activate_date";
	
	/**
	 * 取得日。
	 */
	public static final String	COL_ACQUISITION_DATE	= "acquisition_date";
	
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
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG		= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_TMT_PAID_HOLIDAY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmtPaidHolidayDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmtPaidHolidayTransactionDto dto = new TmtPaidHolidayTransactionDto();
		dto.setTmtPaidHolidayId(getLong(COL_TMT_PAID_HOLIDAY_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setAcquisitionDate(getDate(COL_ACQUISITION_DATE));
		dto.setGivingDay(getDouble(COL_GIVING_DAY));
		dto.setGivingHour(getInt(COL_GIVING_HOUR));
		dto.setCancelDay(getDouble(COL_CANCEL_DAY));
		dto.setCancelHour(getInt(COL_CANCEL_HOUR));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<PaidHolidayTransactionDtoInterface> mappingAll() throws MospException {
		List<PaidHolidayTransactionDtoInterface> all = new ArrayList<PaidHolidayTransactionDtoInterface>();
		while (next()) {
			all.add((PaidHolidayTransactionDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<PaidHolidayTransactionDtoInterface> findForList(String personalId, Date acquisitionDate, Date startDate,
			Date endDate) throws MospException {
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
	public PaidHolidayTransactionDtoInterface findForKey(String personalId, Date activateDate, Date acquisitionDate)
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
			PaidHolidayTransactionDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayTransactionDtoInterface)mapping();
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
	public List<PaidHolidayTransactionDtoInterface> findPersonTerm(String personalId, Date startDate, Date endDate)
			throws MospException {
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
			prepareStatement(sb.toString());
			if (startDate != null) {
				setParam(index++, startDate);
			}
			if (endDate != null) {
				setParam(index++, endDate);
			}
			setParam(index++, personalId);
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
	public List<PaidHolidayTransactionDtoInterface> findForList(String personalId, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(COL_INACTIVATE_FLAG);
			sb.append(" = ");
			sb.append(MospConst.DELETE_FLAG_OFF);
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumn(COL_ACQUISITION_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
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
	public List<PaidHolidayTransactionDtoInterface> findForNextGiving(String personalId, Date targetDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(greater(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE, COL_ACQUISITION_DATE));
			prepareStatement(sb.toString());
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
	public List<PaidHolidayTransactionDtoInterface> findForInfoList(String personalId, Date activateDate,
			String inactivateFlag) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" = (");
			sb.append(select());
			sb.append("MAX(");
			sb.append(COL_ACTIVATE_DATE);
			sb.append(")");
			sb.append(from(TABLE));
			sb.append("AS A ");
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(TABLE);
			sb.append(".");
			sb.append(COL_PERSONAL_ID);
			sb.append(" = A.");
			sb.append(COL_PERSONAL_ID);
			sb.append(and());
			sb.append(lessEqual(COL_ACTIVATE_DATE));
			sb.append(")");
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			sb.append(getOrderByColumn(COL_ACQUISITION_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			PaidHolidayTransactionDtoInterface dto = (PaidHolidayTransactionDtoInterface)baseDto;
			setParam(index++, dto.getTmtPaidHolidayId());
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
			PaidHolidayTransactionDtoInterface dto = (PaidHolidayTransactionDtoInterface)baseDto;
			setParam(index++, dto.getTmtPaidHolidayId());
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
		PaidHolidayTransactionDtoInterface dto = (PaidHolidayTransactionDtoInterface)baseDto;
		setParam(index++, dto.getTmtPaidHolidayId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getAcquisitionDate());
		setParam(index++, dto.getGivingDay());
		setParam(index++, dto.getGivingHour());
		setParam(index++, dto.getCancelDay());
		setParam(index++, dto.getCancelHour());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public List<PaidHolidayTransactionDtoInterface> findForHistoryList(String personalId) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
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
