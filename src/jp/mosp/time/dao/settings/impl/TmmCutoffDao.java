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
import jp.mosp.time.dao.settings.CutoffDaoInterface;
import jp.mosp.time.dto.settings.CutoffDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmCutoffDto;

/**
 * 締日マスタDAOクラス。
 */
public class TmmCutoffDao extends PlatformDao implements CutoffDaoInterface {
	
	/**
	 * 締日マスタ。
	 */
	public static final String	TABLE				= "tmm_cutoff";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_CUTOFF_ID	= "tmm_cutoff_id";
	
	/**
	 * 締日コード。
	 */
	public static final String	COL_CUTOFF_CODE		= "cutoff_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE	= "activate_date";
	
	/**
	 * 締日名称。
	 */
	public static final String	COL_CUTOFF_NAME		= "cutoff_name";
	
	/**
	 * 締日略称。
	 */
	public static final String	COL_CUTOFF_ABBR		= "cutoff_abbr";
	
	/**
	 * 締日。
	 */
	public static final String	COL_CUTOFF_DATE		= "cutoff_date";
	
	/**
	 * 締区分。
	 */
	public static final String	COL_CUTOFF_TYPE		= "cutoff_type";
	
	/**
	 * 未承認仮締。
	 */
	public static final String	COL_NO_APPROVAL		= "no_approval";
	
	/**
	 * 自己月締。
	 */
	public static final String	COL_SELF_TIGHTENING	= "self_tightening";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG	= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1				= COL_TMM_CUTOFF_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmCutoffDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmCutoffDto dto = new TmmCutoffDto();
		dto.setTmmCutoffId(getLong(COL_TMM_CUTOFF_ID));
		dto.setCutoffCode(getString(COL_CUTOFF_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setCutoffName(getString(COL_CUTOFF_NAME));
		dto.setCutoffAbbr(getString(COL_CUTOFF_ABBR));
		dto.setCutoffDate(getInt(COL_CUTOFF_DATE));
		dto.setCutoffType(getString(COL_CUTOFF_TYPE));
		dto.setNoApproval(getInt(COL_NO_APPROVAL));
		dto.setSelfTightening(getInt(COL_SELF_TIGHTENING));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<CutoffDtoInterface> mappingAll() throws MospException {
		List<CutoffDtoInterface> all = new ArrayList<CutoffDtoInterface>();
		while (next()) {
			all.add((CutoffDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<CutoffDtoInterface> findForActivateDate(Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(COL_INACTIVATE_FLAG);
			sb.append(" = 0 ");
			sb.append(and());
			sb.append(getQueryForMaxActivateDate());
			sb.append(getOrderByColumn(COL_CUTOFF_CODE));
			prepareStatement(sb.toString());
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
	public List<CutoffDtoInterface> findForHistory(String cutoffCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_CUTOFF_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, cutoffCode);
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
	public CutoffDtoInterface findForKey(String cutoffCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_CUTOFF_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, cutoffCode);
			setParam(index++, activateDate);
			executeQuery();
			CutoffDtoInterface dto = null;
			if (next()) {
				dto = (CutoffDtoInterface)mapping();
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
	public CutoffDtoInterface findForInfo(String cutoffCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			if (!cutoffCode.isEmpty()) {
				sb.append(equal(COL_CUTOFF_CODE));
				sb.append(and());
			}
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ? ");
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			if (!cutoffCode.isEmpty()) {
				setParam(index++, cutoffCode);
			}
			setParam(index++, activateDate);
			executeQuery();
			CutoffDtoInterface dto = null;
			if (next()) {
				dto = (CutoffDtoInterface)mapping();
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
	public List<CutoffDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			Date activateDate = (Date)param.get(SEARCH_TARGET_DATE);
			String cutoffCode = String.valueOf(param.get(SEARCH_CUTOFF_CODE));
			String cutoffName = String.valueOf(param.get(SEARCH_CUTOFF_NAME));
			String cutoffAbbr = String.valueOf(param.get(SEARCH_CUTOFF_ABBR));
			String cutoffDate = String.valueOf(param.get(SEARCH_CUTOFF_DATE));
			String noApproval = String.valueOf(param.get(SEARCH_NO_APPROVAL));
			String selfTightening = String.valueOf(param.get(SEARCH_SELF_TIGHTENING));
			String inactivateFlag = String.valueOf(param.get(SEARCH_INACTIVATE_FLAG));
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_CUTOFF_CODE));
			sb.append(and());
			sb.append(like(COL_CUTOFF_NAME));
			sb.append(and());
			sb.append(like(COL_CUTOFF_ABBR));
			if (cutoffDate.isEmpty() == false) {
				sb.append(and());
				sb.append(equal(COL_CUTOFF_DATE));
			}
			if (noApproval.isEmpty() == false) {
				sb.append(and());
				sb.append(equal(COL_NO_APPROVAL));
			}
			if (selfTightening.isEmpty() == false) {
				sb.append(and());
				sb.append(equal(COL_SELF_TIGHTENING));
			}
			if (inactivateFlag.isEmpty() == false) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			if (activateDate != null) {
				sb.append(and());
				sb.append(getQueryForMaxActivateDate());
			}
			prepareStatement(sb.toString());
			setParam(index++, startWithParam(cutoffCode));
			setParam(index++, containsParam(cutoffName));
			setParam(index++, containsParam(cutoffAbbr));
			if (cutoffDate.isEmpty() == false) {
				setParam(index++, Integer.parseInt(cutoffDate));
			}
			if (noApproval.isEmpty() == false) {
				setParam(index++, Integer.parseInt(noApproval));
			}
			if (selfTightening.isEmpty() == false) {
				setParam(index++, Integer.parseInt(selfTightening));
			}
			if (inactivateFlag.isEmpty() == false) {
				setParam(index++, Integer.parseInt(inactivateFlag));
			}
			if (activateDate != null) {
				setParam(index++, activateDate);
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
			CutoffDtoInterface dto = (CutoffDtoInterface)baseDto;
			setParam(index++, dto.getTmmCutoffId());
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
			CutoffDtoInterface dto = (CutoffDtoInterface)baseDto;
			setParam(index++, dto.getTmmCutoffId());
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
		CutoffDtoInterface dto = (CutoffDtoInterface)baseDto;
		setParam(index++, dto.getTmmCutoffId());
		setParam(index++, dto.getCutoffCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getCutoffName());
		setParam(index++, dto.getCutoffAbbr());
		setParam(index++, dto.getCutoffDate());
		setParam(index++, dto.getCutoffType());
		setParam(index++, dto.getNoApproval());
		setParam(index++, dto.getSelfTightening());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	@Override
	public StringBuffer getQueryForMaxActivateDate() {
		StringBuffer sb = new StringBuffer();
		sb.append(COL_ACTIVATE_DATE);
		sb.append(in());
		sb.append("(SELECT MAX(");
		sb.append(COL_ACTIVATE_DATE);
		sb.append(")");
		sb.append(from(TABLE));
		sb.append("AS A ");
		sb.append(where());
		sb.append(deleteFlagOff());
		sb.append(and());
		sb.append(TABLE + "." + COL_CUTOFF_CODE);
		sb.append(" = A." + COL_CUTOFF_CODE);
		sb.append(and());
		sb.append(COL_ACTIVATE_DATE);
		sb.append(" <= ?)");
		return sb;
	}
}
