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
package jp.mosp.platform.dao.system.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.system.GeneralDaoInterface;
import jp.mosp.platform.dto.system.GeneralDtoInterface;
import jp.mosp.platform.dto.system.impl.GeneralDto;

/**
 * 汎用テーブルDAOクラス。
 *
 */
public class PfgGeneralDao extends PlatformDao implements GeneralDaoInterface {
	
	/**
	 * MosP汎用テーブル。
	 */
	public static final String	TABLE				= "pfg_general";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFG_GENERAL_ID	= "pfg_general_id";
	
	/**
	 * 汎用区分。
	 */
	public static final String	COL_GENERAL_TYPE	= "general_type";
	
	/**
	 * 汎用コード。
	 */
	public static final String	COL_GENERAL_CODE	= "general_code";
	
	/**
	 * 汎用日付。
	 */
	public static final String	COL_GENERAL_DATE	= "general_date";
	
	/**
	 * 汎用文字列1。
	 */
	public static final String	COL_GENERAL_CHAR1	= "general_char1";
	
	/**
	 * 汎用文字列2。
	 */
	public static final String	COL_GENERAL_CHAR2	= "general_char2";
	
	/**
	 * 汎用数値。
	 */
	public static final String	COL_GENERAL_NUMERIC	= "general_numeric";
	
	/**
	 * 汎用日時。
	 */
	public static final String	COL_GENERAL_TIME	= "general_time";
	
	/**
	 * 汎用バイナリ。
	 */
	public static final String	COL_GENERAL_BINARY	= "general_binary";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1				= COL_PFG_GENERAL_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfgGeneralDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			GeneralDtoInterface dto = cast(baseDto);
			setParam(index++, dto.getPfgGeneralId());
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
	public BaseDto mapping() throws MospException {
		GeneralDto dto = new GeneralDto();
		dto.setPfgGeneralId(getLong(COL_PFG_GENERAL_ID));
		dto.setGeneralType(getString(COL_GENERAL_TYPE));
		dto.setGeneralCode(getString(COL_GENERAL_CODE));
		dto.setGeneralDate(getDate(COL_GENERAL_DATE));
		dto.setGeneralChar1(getString(COL_GENERAL_CHAR1));
		dto.setGeneralChar2(getString(COL_GENERAL_CHAR2));
		dto.setGeneralNumeric(getDouble(COL_GENERAL_NUMERIC));
		dto.setGeneralTime(getTimestamp(COL_GENERAL_TIME));
		dto.setGeneralBinary(getBytes(COL_GENERAL_BINARY));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<GeneralDtoInterface> mappingAll() throws MospException {
		List<GeneralDtoInterface> all = new ArrayList<GeneralDtoInterface>();
		while (next()) {
			all.add((GeneralDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		GeneralDtoInterface dto = cast(baseDto);
		setParam(index++, dto.getPfgGeneralId());
		setParam(index++, dto.getGeneralType());
		setParam(index++, dto.getGeneralCode());
		setParam(index++, dto.getGeneralDate());
		setParam(index++, dto.getGeneralChar1());
		setParam(index++, dto.getGeneralChar2());
		setParam(index++, dto.getGeneralNumeric());
		setParam(index++, dto.getGeneralTime(), true);
		setParam(index++, dto.getGeneralBinary());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			GeneralDtoInterface dto = cast(baseDto);
			setParam(index++, dto.getPfgGeneralId());
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
	public GeneralDtoInterface findForKey(String generalType, String generalCode, Date generalDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_GENERAL_TYPE));
			sb.append(and());
			sb.append(equal(COL_GENERAL_CODE));
			sb.append(and());
			sb.append(equal(COL_GENERAL_DATE));
			prepareStatement(sb.toString());
			setParam(index++, generalType);
			setParam(index++, generalCode);
			setParam(index++, generalDate);
			executeQuery();
			GeneralDtoInterface dto = null;
			if (rs.next()) {
				dto = (GeneralDtoInterface)mapping();
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
	public List<GeneralDtoInterface> findForTerm(String generalType, String generalCode, Date firstDate, Date lastDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_GENERAL_TYPE));
			sb.append(and());
			sb.append(equal(COL_GENERAL_CODE));
			sb.append(and());
			sb.append(greaterEqual(COL_GENERAL_DATE));
			sb.append(and());
			sb.append(lessEqual(COL_GENERAL_DATE));
			prepareStatement(sb.toString());
			setParam(index++, generalType);
			setParam(index++, generalCode);
			setParam(index++, firstDate);
			setParam(index++, lastDate);
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
	public List<GeneralDtoInterface> findForHistory(String generalType, String generalCode, Date generalDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_GENERAL_TYPE));
			sb.append(and());
			sb.append(equal(COL_GENERAL_CODE));
			sb.append(and());
			sb.append(equal(COL_GENERAL_DATE));
			prepareStatement(sb.toString());
			setParam(index++, generalType);
			setParam(index++, generalCode);
			setParam(index++, generalDate);
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
	public GeneralDtoInterface findForInfo(String generalType, String generalCode, Date generalDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_GENERAL_TYPE));
			sb.append(and());
			sb.append(equal(COL_GENERAL_CODE));
			sb.append(and());
			sb.append(lessEqual(COL_GENERAL_DATE));
			prepareStatement(sb.toString());
			setParam(index++, generalType);
			setParam(index++, generalCode);
			setParam(index++, generalDate);
			executeQuery();
			GeneralDtoInterface dto = null;
			if (rs.next()) {
				dto = (GeneralDtoInterface)mapping();
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
