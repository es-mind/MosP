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
import jp.mosp.time.dao.settings.AllowanceDaoInterface;
import jp.mosp.time.dto.settings.AllowanceDtoInterface;
import jp.mosp.time.dto.settings.impl.TmdAllowanceDto;

/**
 * 手当データDAOクラス。
 */
public class TmdAllowanceDao extends PlatformDao implements AllowanceDaoInterface {
	
	/**
	 * 手当データ。
	 */
	public static final String	TABLE					= "tmd_allowance";
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMD_ALLOWANCE_ID	= "tmd_allowance_id";
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
	public static final String	COL_WORKS				= "works";
	/**
	 * 手当コード。
	 */
	public static final String	COL_ALLOWANCE_CODE		= "allowance_code";
	/**
	 * 手当。
	 */
	public static final String	COL_ALLOWANCE			= "allowance";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_TMD_ALLOWANCE_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmdAllowanceDao() {
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmdAllowanceDto dto = new TmdAllowanceDto();
		dto.setTmdAllowanceId(getLong(COL_TMD_ALLOWANCE_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setWorkDate(getDate(COL_WORK_DATE));
		dto.setWorks(getInt(COL_WORKS));
		dto.setAllowanceCode(getString(COL_ALLOWANCE_CODE));
		dto.setAllowance(getInt(COL_ALLOWANCE));
		return dto;
	}
	
	@Override
	public List<AllowanceDtoInterface> mappingAll() throws MospException {
		List<AllowanceDtoInterface> all = new ArrayList<AllowanceDtoInterface>();
		while (next()) {
			all.add((AllowanceDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public AllowanceDtoInterface findForKey(String personalId, Date workDate, int works, String allowanceCode)
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
			sb.append(equal(COL_WORKS));
			sb.append(and());
			sb.append(equal(COL_ALLOWANCE_CODE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, works);
			setParam(index++, allowanceCode);
			executeQuery();
			AllowanceDtoInterface dto = null;
			if (next()) {
				dto = (AllowanceDtoInterface)mapping();
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			AllowanceDtoInterface dto = (AllowanceDtoInterface)baseDto;
			setParam(index++, dto.getTmdAllowanceId());
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
			AllowanceDtoInterface dto = (AllowanceDtoInterface)baseDto;
			setParam(index++, dto.getTmdAllowanceId());
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
		AllowanceDtoInterface dto = (AllowanceDtoInterface)baseDto;
		setParam(index++, dto.getTmdAllowanceId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getWorkDate());
		setParam(index++, dto.getWorks());
		setParam(index++, dto.getAllowanceCode());
		setParam(index++, dto.getAllowance());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public List<AllowanceDtoInterface> findForHistory(String personalId, Date workDate, int works, String allowanceCode,
			int allowance) throws MospException {
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
			sb.append(equal(COL_WORKS));
			sb.append(and());
			sb.append(equal(COL_ALLOWANCE_CODE));
			sb.append(and());
			sb.append(equal(COL_ALLOWANCE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, workDate);
			setParam(index++, works);
			setParam(index++, allowanceCode);
			setParam(index++, allowance);
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
	public List<AllowanceDtoInterface> findForList(String personalId, String allowanceCode, Date startDate,
			Date endDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_ALLOWANCE_CODE));
			sb.append(and());
			sb.append(greaterEqual(COL_WORK_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_WORK_DATE));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, allowanceCode);
			setParam(index++, startDate);
			setParam(index++, endDate);
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
