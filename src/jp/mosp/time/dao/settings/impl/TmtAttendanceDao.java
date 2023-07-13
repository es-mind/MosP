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
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.AttendanceTransactionDaoInterface;
import jp.mosp.time.dto.settings.AttendanceTransactionDtoInterface;
import jp.mosp.time.dto.settings.impl.TmtAttendanceDto;

/**
 * 勤怠トランザクションDAOクラス
 */
public class TmtAttendanceDao extends PlatformDao implements AttendanceTransactionDaoInterface {
	
	/**
	 * 勤怠トランザクション。
	 */
	public static final String	TABLE					= "tmt_attendance";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMT_ATTENDANCE_ID	= "tmt_attendance_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID			= "personal_id";
	
	/**
	 * 勤務日。
	 */
	public static final String	COL_WORK_DATE			= "work_date";
	
	/**
	 * 出勤区分。
	 */
	public static final String	COL_ATTENDANCE_TYPE		= "attendance_type";
	
	/**
	 * 出勤率算定分子。
	 */
	public static final String	COL_NUMERATOR			= "numerator";
	
	/**
	 * 出勤率算定分母。
	 */
	public static final String	COL_DENOMINATOR			= "denominator";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_TMT_ATTENDANCE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmtAttendanceDao() {
	}
	
	@Override
	public void initDao() {
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmtAttendanceDto dto = new TmtAttendanceDto();
		dto.setTmtAttendanceId(getLong(COL_TMT_ATTENDANCE_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setWorkDate(getDate(COL_WORK_DATE));
		dto.setAttendanceType(getString(COL_ATTENDANCE_TYPE));
		dto.setNumerator(getInt(COL_NUMERATOR));
		dto.setDenominator(getInt(COL_DENOMINATOR));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<AttendanceTransactionDtoInterface> mappingAll() throws MospException {
		List<AttendanceTransactionDtoInterface> all = new ArrayList<AttendanceTransactionDtoInterface>();
		while (next()) {
			all.add((AttendanceTransactionDtoInterface)mapping());
		}
		return all;
	}
	
	/**
	 * ResultSetの内容を、DTOにマッピングし、マップとして返す。<br>
	 * キーはDate。
	 * @return 検索結果(DTOのMap)。
	 * @throws MospException SQL例外が発生した場合
	 */
	protected Map<Date, AttendanceTransactionDtoInterface> mappingAllMap() throws MospException {
		Map<Date, AttendanceTransactionDtoInterface> map = new HashMap<Date, AttendanceTransactionDtoInterface>();
		while (next()) {
			AttendanceTransactionDtoInterface dto = (AttendanceTransactionDtoInterface)mapping();
			map.put(dto.getWorkDate(), dto);
		}
		return map;
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			AttendanceTransactionDtoInterface dto = (AttendanceTransactionDtoInterface)baseDto;
			setParam(index++, dto.getTmtAttendanceId());
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
	public int delete(BaseDtoInterface baseDto) {
		// 処理無し
		return 0;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		AttendanceTransactionDtoInterface dto = (AttendanceTransactionDtoInterface)baseDto;
		setParam(index++, dto.getTmtAttendanceId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getWorkDate());
		setParam(index++, dto.getAttendanceType());
		setParam(index++, dto.getNumerator());
		setParam(index++, dto.getDenominator());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public AttendanceTransactionDtoInterface findForKey(String personalId, Date workDate) throws MospException {
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
			if (next()) {
				return (AttendanceTransactionDtoInterface)mapping();
			}
			return null;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public Map<Date, AttendanceTransactionDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_WORK_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_WORK_DATE));
			sb.append(getOrderByColumn(COL_PERSONAL_ID, COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, lastDate);
			setParam(index++, firstDate);
			executeQuery();
			return mappingAllMap();
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public AttendanceTransactionDtoInterface sum(String personalId, Date firstDate, Date lastDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = new StringBuffer();
			sb.append(select());
			sb.append(0);
			sb.append(as(COL_TMT_ATTENDANCE_ID));
			sb.append(comma());
			sb.append(COL_PERSONAL_ID);
			sb.append(comma());
			sb.append("NULL");
			sb.append(as(COL_WORK_DATE));
			sb.append(comma());
			sb.append("''");
			sb.append(as(COL_ATTENDANCE_TYPE));
			sb.append(comma());
			sb.append(sum(COL_NUMERATOR, COL_NUMERATOR));
			sb.append(comma());
			sb.append(sum(COL_DENOMINATOR, COL_DENOMINATOR));
			sb.append(comma());
			sb.append(0);
			sb.append(as(colDeleteFlag));
			sb.append(comma());
			sb.append("LOCALTIMESTAMP");
			sb.append(as(colInsertDate));
			sb.append(comma());
			sb.append("''");
			sb.append(as(colInsertUser));
			sb.append(comma());
			sb.append("LOCALTIMESTAMP");
			sb.append(as(colUpdateDate));
			sb.append(comma());
			sb.append("''");
			sb.append(as(colUpdateUser));
			sb.append(from(TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_WORK_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_WORK_DATE));
			sb.append(groupBy(COL_PERSONAL_ID));
			sb.append(getOrderByColumn(COL_PERSONAL_ID, COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, lastDate);
			setParam(index++, firstDate);
			executeQuery();
			if (next()) {
				return (AttendanceTransactionDtoInterface)mapping();
			}
			return null;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
	@Override
	public Set<Long> findForMilliseconds(String personalId, Date firstDate, Date lastDate) throws MospException {
		try {
			index = 1;
			List<String> columnList = new ArrayList<String>();
			columnList.add(COL_WORK_DATE);
			StringBuffer sb = new StringBuffer();
			sb.append(getSelectStatement(columnList));
			sb.append(from(TABLE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(lessEqual(COL_WORK_DATE));
			sb.append(and());
			sb.append(greaterEqual(COL_WORK_DATE));
			sb.append(getOrderByColumn(COL_PERSONAL_ID, COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, lastDate);
			setParam(index++, firstDate);
			executeQuery();
			Set<Long> set = new HashSet<Long>();
			while (next()) {
				set.add(getDate(COL_WORK_DATE).getTime());
			}
			return set;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
