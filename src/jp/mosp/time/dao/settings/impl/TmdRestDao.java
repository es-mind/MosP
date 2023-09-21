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
/**
 * 
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
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.RestDaoInterface;
import jp.mosp.time.dto.settings.RestDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdRestDto;

/**
 * 休憩データDAOクラス。
 */
public class TmdRestDao extends PlatformDao implements RestDaoInterface {
	
	/**
	 * 休憩データ。
	 */
	public static final String	TABLE			= "tmd_rest";
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_REST_ID	= "tmd_rest_id";
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID	= "personal_id";
	/**
	 * 勤務日。
	 */
	public static final String	COL_WORK_DATE	= "work_date";
	/**
	 * 勤務回数。
	 */
	public static final String	COL_TIMES_WORK	= "times_work";
	/**
	 * 休憩回数。
	 */
	public static final String	COL_REST		= "rest";
	/**
	 * 休憩開始時刻。
	 */
	public static final String	COL_REST_START	= "rest_start";
	/**
	 * 休憩終了時刻。
	 */
	public static final String	COL_REST_END	= "rest_end";
	/**
	 * 休憩時間。
	 */
	public static final String	COL_REST_TIME	= "rest_time";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1			= COL_TMD_REST_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdRestDao() {
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdRestDto dto = new TmdRestDto();
		dto.setTmdRestId(getLong(COL_TMD_REST_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setWorkDate(getDate(COL_WORK_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setRest(getInt(COL_REST));
		dto.setRestStart(getTimestamp(COL_REST_START));
		dto.setRestEnd(getTimestamp(COL_REST_END));
		dto.setRestTime(getInt(COL_REST_TIME));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<RestDtoInterface> mappingAll() throws MospException {
		List<RestDtoInterface> all = new ArrayList<RestDtoInterface>();
		while (next()) {
			all.add((RestDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public RestDtoInterface findForKey(String personalId, Date workDate, int timesWork, int rest) throws MospException {
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
		sb.append(equal(COL_REST));
		prepareStatement(sb.toString());
		setParam(index++, personalId);
		setParam(index++, workDate);
		setParam(index++, timesWork);
		setParam(index++, rest);
		executeQuery();
		RestDtoInterface dto = null;
		if (next()) {
			dto = (RestDtoInterface)mapping();
		}
		return dto;
	}
	
	@Override
	public List<RestDtoInterface> findForList(String personalId, Date workDate, int timesWork) throws MospException {
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
			sb.append(getOrderByColumn(COL_REST));
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
			RestDtoInterface dto = (RestDtoInterface)baseDto;
			setParam(index++, dto.getTmdRestId());
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
			RestDtoInterface dto = (RestDtoInterface)baseDto;
			setParam(index++, dto.getTmdRestId());
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
		RestDtoInterface dto = (RestDtoInterface)baseDto;
		setParam(index++, dto.getTmdRestId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getWorkDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getRest());
		setParam(index++, dto.getRestStart(), true);
		setParam(index++, dto.getRestEnd(), true);
		setParam(index++, dto.getRestTime());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public List<RestDtoInterface> findForHistory(String personalId, Date workDate, int rest, Date restStart,
			Date restEnd) throws MospException {
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
			sb.append(equal(COL_REST));
			sb.append(and());
			sb.append(equal(COL_REST_START));
			sb.append(and());
			sb.append(equal(COL_REST_END));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, rest);
			setParam(index++, restStart, true);
			setParam(index++, restEnd, true);
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
