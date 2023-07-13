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
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.GoOutDaoInterface;
import jp.mosp.time.dto.settings.GoOutDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdGoOutDto;

/**
 * 外出データDAOクラス。
 */
public class TmdGoOutDao extends PlatformDao implements GoOutDaoInterface {
	
	/**
	 * 外出データ。
	 */
	public static final String	TABLE				= "tmd_go_out";
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_GO_OUT_ID	= "tmd_go_out_id";
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID		= "personal_id";
	/**
	 * 勤務日。
	 */
	public static final String	COL_WORK_DATE		= "work_date";
	/**
	 * 勤務回数。
	 */
	public static final String	COL_TIMES_WORK		= "times_work";
	/**
	 * 外出区分。
	 */
	public static final String	COL_GO_OUT_TYPE		= "go_out_type";
	/**
	 * 外出回数。
	 */
	public static final String	COL_TIMES_GO_OUT	= "times_go_out";
	/**
	 * 外出開始時刻。
	 */
	public static final String	COL_GO_OUT_START	= "go_out_start";
	/**
	 * 外出終了時刻。
	 */
	public static final String	COL_GO_OUT_END		= "go_out_end";
	/**
	 * 外出時間。
	 */
	public static final String	COL_GO_OUT_TIME		= "go_out_time";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1				= COL_TMD_GO_OUT_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdGoOutDao() {
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdGoOutDto dto = new TmdGoOutDto();
		dto.setTmdGoOutId(getLong(COL_TMD_GO_OUT_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setWorkDate(getDate(COL_WORK_DATE));
		dto.setTimesWork(getInt(COL_TIMES_WORK));
		dto.setGoOutType(getInt(COL_GO_OUT_TYPE));
		dto.setTimesGoOut(getInt(COL_TIMES_GO_OUT));
		dto.setGoOutStart(getTimestamp(COL_GO_OUT_START));
		dto.setGoOutEnd(getTimestamp(COL_GO_OUT_END));
		dto.setGoOutTime(getInt(COL_GO_OUT_TIME));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<GoOutDtoInterface> mappingAll() throws MospException {
		List<GoOutDtoInterface> all = new ArrayList<GoOutDtoInterface>();
		while (next()) {
			all.add((GoOutDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<GoOutDtoInterface> findForList(String personalId, Date workDate, int timesWork) throws MospException {
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
			GoOutDtoInterface dto = (GoOutDtoInterface)baseDto;
			setParam(index++, dto.getTmdGoOutId());
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
			GoOutDtoInterface dto = (GoOutDtoInterface)baseDto;
			setParam(index++, dto.getTmdGoOutId());
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
		GoOutDtoInterface dto = (GoOutDtoInterface)baseDto;
		setParam(index++, dto.getTmdGoOutId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getWorkDate());
		setParam(index++, dto.getTimesWork());
		setParam(index++, dto.getGoOutType());
		setParam(index++, dto.getTimesGoOut());
		setParam(index++, dto.getGoOutStart(), true);
		setParam(index++, dto.getGoOutEnd(), true);
		setParam(index++, dto.getGoOutTime());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public GoOutDtoInterface findForKey(String personalId, Date workDate, int timesWork, int type, int times)
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
			sb.append(equal(COL_GO_OUT_TYPE));
			sb.append(and());
			sb.append(equal(COL_TIMES_GO_OUT));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, timesWork);
			setParam(index++, type);
			setParam(index++, times);
			executeQuery();
			GoOutDtoInterface dto = null;
			if (next()) {
				dto = (GoOutDtoInterface)mapping();
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
	public List<GoOutDtoInterface> findForHistory(String personalId, Date workDate, int goOutType, int timesGoOut,
			Date restStart, Date restEnd) throws MospException {
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
			sb.append(equal(COL_GO_OUT_TYPE));
			sb.append(and());
			sb.append(equal(COL_TIMES_GO_OUT));
			sb.append(and());
			sb.append(equal(COL_GO_OUT_START));
			sb.append(and());
			sb.append(equal(COL_GO_OUT_END));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, goOutType);
			setParam(index++, timesGoOut);
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
	
	@Override
	public List<GoOutDtoInterface> findForHistoryList(String personalId, Date workDate, int goOutType)
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
			sb.append(equal(COL_GO_OUT_TYPE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, goOutType);
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
	public List<GoOutDtoInterface> findForList(String personalId, Date workDate, int timesWork, int goOutType)
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
			sb.append(equal(COL_GO_OUT_TYPE));
			sb.append(getOrderByColumn(COL_GO_OUT_TYPE, COL_TIMES_GO_OUT));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, timesWork);
			setParam(index++, goOutType);
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
