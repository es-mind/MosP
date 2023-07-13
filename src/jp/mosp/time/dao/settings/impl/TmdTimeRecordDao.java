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

import jp.mosp.framework.base.BaseDao;
import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dao.settings.TimeRecordDaoInterface;
import jp.mosp.time.dto.settings.TimeRecordDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdTimeRecordDto;

/**
 * 打刻データDAOクラス。
 */
public class TmdTimeRecordDao extends BaseDao implements TimeRecordDaoInterface {
	
	/**
	 * 打刻データ。
	 */
	public static final String	TABLE					= "tmd_time_record";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_TIME_RECORD_ID	= "tmd_time_record_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID			= "personal_id";
	
	/**
	 * 勤務日。
	 */
	public static final String	COL_WORK_DATE			= "work_date";
	
	/**
	 * 勤務回数。
	 */
	public static final String	COL_TIMES_WORK			= "times_work";
	
	/**
	 * 打刻区分。
	 */
	public static final String	COL_RECORD_TYPE			= "record_type";
	
	/**
	 * 打刻時刻。
	 */
	public static final String	COL_RECORD_TIME			= "record_time";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_TMD_TIME_RECORD_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdTimeRecordDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdTimeRecordDto dto = new TmdTimeRecordDto();
		dto.setTmdTimeRecordId(getLong(COL_TMD_TIME_RECORD_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setWorkDate(getDate(COL_WORK_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setRecordType(getString(COL_RECORD_TYPE));
		dto.setRecordTime(getTimestamp(COL_RECORD_TIME));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<TimeRecordDtoInterface> mappingAll() throws MospException {
		List<TimeRecordDtoInterface> list = new ArrayList<TimeRecordDtoInterface>();
		while (next()) {
			list.add((TimeRecordDtoInterface)mapping());
		}
		return list;
	}
	
	@Override
	public TimeRecordDtoInterface findForKey(String personalId, Date workDate, int timesWork, String recordType)
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
			sb.append(equal(COL_RECORD_TYPE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, timesWork);
			setParam(index++, recordType);
			executeQuery();
			TimeRecordDtoInterface dto = null;
			if (next()) {
				dto = (TimeRecordDtoInterface)mapping();
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
	public List<TimeRecordDtoInterface> findForPersonAndDay(String personalId, Date workDate, int timesWork)
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
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, timesWork);
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
			TimeRecordDtoInterface dto = (TimeRecordDtoInterface)baseDto;
			setParam(index++, dto.getTmdTimeRecordId());
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
			TimeRecordDtoInterface dto = (TimeRecordDtoInterface)baseDto;
			setParam(index++, dto.getTmdTimeRecordId());
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
		TimeRecordDtoInterface dto = (TimeRecordDtoInterface)baseDto;
		setParam(index++, dto.getTmdTimeRecordId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getWorkDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getRecordType());
		setParam(index++, dto.getRecordTime(), true);
		setCommonParams(baseDto, isInsert);
	}
	
}
