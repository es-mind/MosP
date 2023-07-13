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
import jp.mosp.platform.dao.system.NamingDaoInterface;
import jp.mosp.platform.dto.system.NamingDtoInterface;
import jp.mosp.platform.dto.system.impl.PfmNamingDto;

/**
 * 名称区分マスタDAOクラス。
 */
public class PfmNamingDao extends PlatformDao implements NamingDaoInterface {
	
	/**
	 * 名称区分マスタ。
	 */
	public static final String	TABLE					= "pfm_naming";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_NAMING_ID		= "pfm_naming_id";
	
	/**
	 * 名称区分コード。
	 */
	public static final String	COL_NAMING_TYPE			= "naming_type";
	
	/**
	 * 名称区分項目コード。
	 */
	public static final String	COL_NAMING_ITEM_CODE	= "naming_item_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE		= "activate_date";
	
	/**
	 * 名称項目名。
	 */
	public static final String	COL_NAMING_ITEM_NAME	= "naming_item_name";
	
	/**
	 * 名称項目略称。
	 */
	public static final String	COL_NAMING_ABBR			= "naming_item_abbr";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG		= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_PFM_NAMING_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmNamingDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfmNamingDto dto = new PfmNamingDto();
		dto.setPfmNamingId(getLong(COL_PFM_NAMING_ID));
		dto.setNamingType(getString(COL_NAMING_TYPE));
		dto.setNamingItemCode(getString(COL_NAMING_ITEM_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setNamingItemName(getString(COL_NAMING_ITEM_NAME));
		dto.setNamingItemAbbr(getString(COL_NAMING_ABBR));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public int delete(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getDeleteQuery(getClass()));
			NamingDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmNamingId());
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
	public List<NamingDtoInterface> mappingAll() throws MospException {
		// DTOのリストを準備
		List<NamingDtoInterface> all = new ArrayList<NamingDtoInterface>();
		// ResultSetの中身をDTOに設定しリストに追加
		while (next()) {
			all.add((NamingDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		NamingDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmNamingId());
		setParam(index++, dto.getNamingType());
		setParam(index++, dto.getNamingItemCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getNamingItemName());
		setParam(index++, dto.getNamingItemAbbr());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			NamingDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmNamingId());
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
	public Map<String, Object> getParamsMap() {
		Map<String, Object> map = new HashMap<String, Object>();
		return map;
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected NamingDtoInterface castDto(BaseDtoInterface baseDto) {
		return (NamingDtoInterface)baseDto;
	}
	
	@Override
	public NamingDtoInterface findForInfo(String namingType, String namingItemCode, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_NAMING_TYPE));
			sb.append(and());
			sb.append(equal(COL_NAMING_ITEM_CODE));
			sb.append(and());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ? ");
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, namingType);
			setParam(index++, namingItemCode);
			setParam(index++, activateDate);
			executeQuery();
			NamingDtoInterface dto = null;
			if (next()) {
				dto = (NamingDtoInterface)mapping();
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
	public NamingDtoInterface findForKey(String namingType, String namingItemCode, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_NAMING_TYPE));
			sb.append(and());
			sb.append(equal(COL_NAMING_ITEM_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, namingType);
			setParam(index++, namingItemCode);
			setParam(index++, activateDate);
			executeQuery();
			NamingDtoInterface dto = null;
			if (next()) {
				dto = (NamingDtoInterface)mapping();
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
	public List<NamingDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			Date activateDate = (Date)param.get("activateDate");
			String namingType = String.valueOf(param.get("namingType"));
			String namingItemCode = String.valueOf(param.get("namingItemCode"));
			String namingItemName = String.valueOf(param.get("namingItemName"));
			String namingItemAbbr = String.valueOf(param.get("namingItemAbbr"));
			String inactivateFlag = String.valueOf(param.get("inactivateFlag"));
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			if (activateDate != null) {
				// 有効日における最新の情報を抽出する条件SQLを追加
				sb.append(getQueryForMaxActivateDate(TABLE, COL_ACTIVATE_DATE, COL_NAMING_TYPE, COL_NAMING_ITEM_CODE));
			}
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_NAMING_TYPE));
			sb.append(and());
			sb.append(like(COL_NAMING_ITEM_CODE));
			sb.append(and());
			sb.append(like(COL_NAMING_ITEM_NAME));
			sb.append(and());
			sb.append(like(COL_NAMING_ABBR));
			if (!inactivateFlag.isEmpty()) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			prepareStatement(sb.toString());
			if (activateDate != null) {
				// 有効日における最新の情報を抽出する条件のパラメータを設定
				index = setParamsForMaxActivateDate(index, activateDate, ps);
			}
			setParam(index++, namingType);
			setParam(index++, startWithParam(namingItemCode));
			setParam(index++, containsParam(namingItemName));
			setParam(index++, containsParam(namingItemAbbr));
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
	public List<NamingDtoInterface> findForHistory(String namingType, String namingItemCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_NAMING_TYPE));
			sb.append(and());
			sb.append(equal(COL_NAMING_ITEM_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, namingType);
			setParam(index++, namingItemCode);
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
	public List<NamingDtoInterface> findForList(String namingType, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(getQueryForMaxActivateDate(TABLE, COL_ACTIVATE_DATE, COL_NAMING_TYPE, COL_NAMING_ITEM_CODE));
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_NAMING_TYPE));
			sb.append(getOrderByColumn(COL_NAMING_ITEM_CODE));
			prepareStatement(sb.toString());
			// 有効日における最新の情報を抽出する条件のパラメータを設定
			index = setParamsForMaxActivateDate(index, activateDate, ps);
			setParam(index++, namingType);
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
