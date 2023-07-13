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
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.PaidHolidayGrantDaoInterface;
import jp.mosp.time.dto.settings.PaidHolidayGrantDtoInterface;
import jp.mosp.time.dto.settings.impl.TmtPaidHolidayGrantDto;

/**
 * 有給休暇付与DAO
 */
public class TmtPaidHolidayGrantDao extends PlatformDao implements PaidHolidayGrantDaoInterface {
	
	/**
	 * 有給休暇付与。
	 */
	public static final String	TABLE							= "tmt_paid_holiday_grant";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMT_PAID_HOLIDAY_GRANT_ID	= "tmt_paid_holiday_grant_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID					= "personal_id";
	
	/**
	 * 付与日
	 */
	public static final String	COL_GRANT_DATE					= "grant_date";
	
	/**
	 * 付与状態
	 */
	public static final String	COL_GRANT_STATUS				= "grant_status";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1							= COL_TMT_PAID_HOLIDAY_GRANT_ID;
	
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmtPaidHolidayGrantDto dto = new TmtPaidHolidayGrantDto();
		dto.setTmtPaidHolidayGrantId(getLong(COL_TMT_PAID_HOLIDAY_GRANT_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setGrantDate(getDate(COL_GRANT_DATE));
		dto.setGrantStatus(getInt(COL_GRANT_STATUS));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<PaidHolidayGrantDtoInterface> mappingAll() throws MospException {
		List<PaidHolidayGrantDtoInterface> all = new ArrayList<PaidHolidayGrantDtoInterface>();
		while (next()) {
			all.add((PaidHolidayGrantDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			PaidHolidayGrantDtoInterface dto = (PaidHolidayGrantDtoInterface)baseDto;
			setParam(index++, dto.getTmtPaidHolidayGrantId());
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
			PaidHolidayGrantDtoInterface dto = (PaidHolidayGrantDtoInterface)baseDto;
			setParam(index++, dto.getTmtPaidHolidayGrantId());
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
		PaidHolidayGrantDtoInterface dto = (PaidHolidayGrantDtoInterface)baseDto;
		setParam(index++, dto.getTmtPaidHolidayGrantId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getGrantDate());
		setParam(index++, dto.getGrantStatus());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public PaidHolidayGrantDtoInterface findForKey(String personalId, Date grantDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_GRANT_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, grantDate);
			executeQuery();
			PaidHolidayGrantDtoInterface dto = null;
			if (next()) {
				dto = (PaidHolidayGrantDtoInterface)mapping();
			}
			return dto;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
