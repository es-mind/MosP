/*
 * MosP - Mind Open Source Project
 * Copyright (C) esMind, LLC  https://www.e-s-mind.com/
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
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.time.dao.settings.WorkTypeItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypeItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmmWorkTypeItemDto;

/**
 * 勤務形態項目マスタDAOクラス。
 */
public class TmmWorkTypeItemDao extends PlatformDao implements WorkTypeItemDaoInterface {
	
	/**
	 * 勤務形態項目マスタ。
	 */
	public static final String	TABLE						= "tmm_work_type_item";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMM_WORK_TYPE_ITEM_ID	= "tmm_work_type_item_id";
	
	/**
	 * 勤務形態コード。
	 */
	public static final String	COL_WORK_TYPE_CODE			= "work_type_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE			= "activate_date";
	
	/**
	 * 勤務形態項目コード。
	 */
	public static final String	COL_WORK_TYPE_ITEM_CODE		= "work_type_item_code";
	
	/**
	 * 勤務形態項目値。
	 */
	public static final String	COL_WORK_TYPE_ITEM_VALUE	= "work_type_item_value";
	
	/**
	 * 勤務形態項目値(予備)。
	 */
	public static final String	COL_PRELIMINARY				= "preliminary";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG			= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1						= COL_TMM_WORK_TYPE_ITEM_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmmWorkTypeItemDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmmWorkTypeItemDto dto = new TmmWorkTypeItemDto();
		dto.setTmmWorkTypeItemId(getLong(COL_TMM_WORK_TYPE_ITEM_ID));
		dto.setWorkTypeCode(getString(COL_WORK_TYPE_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setWorkTypeItemCode(getString(COL_WORK_TYPE_ITEM_CODE));
		dto.setWorkTypeItemValue(getTimestamp(COL_WORK_TYPE_ITEM_VALUE));
		dto.setPreliminary(getString(COL_PRELIMINARY));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<WorkTypeItemDtoInterface> mappingAll() throws MospException {
		List<WorkTypeItemDtoInterface> all = new ArrayList<WorkTypeItemDtoInterface>();
		while (next()) {
			all.add((WorkTypeItemDtoInterface)mapping());
		}
		return all;
	}
	
	@Override
	public List<WorkTypeItemDtoInterface> findForHistory(String workTypeCode, String workTypeItemCode)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_CODE));
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_ITEM_CODE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workTypeCode);
			setParam(index++, workTypeItemCode);
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
	public WorkTypeItemDtoInterface findForKey(String workTypeCode, Date activateDate, String workTypeItemCode)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_ITEM_CODE));
			prepareStatement(sb.toString());
			setParam(index++, workTypeCode);
			setParam(index++, activateDate);
			setParam(index++, workTypeItemCode);
			executeQuery();
			WorkTypeItemDtoInterface dto = null;
			if (next()) {
				dto = (WorkTypeItemDtoInterface)mapping();
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
	public WorkTypeItemDtoInterface findForInfo(String workTypeCode, Date activateDate, String workTypeItemCode)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_CODE));
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_ITEM_CODE));
			sb.append(and());
			sb.append(COL_ACTIVATE_DATE);
			sb.append(" <= ? ");
			sb.append(getOrderByColumnDescLimit1(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workTypeCode);
			setParam(index++, workTypeItemCode);
			setParam(index++, activateDate);
			executeQuery();
			WorkTypeItemDtoInterface dto = null;
			if (next()) {
				dto = (WorkTypeItemDtoInterface)mapping();
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
	public List<WorkTypeItemDtoInterface> findForWorkType(String workTypeCode, Date activateDate) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			prepareStatement(sb.toString());
			setParam(index++, workTypeCode);
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
	public List<WorkTypeItemDtoInterface> findForWorkTypeItem(Date activateDate, String workTypeItemCode,
			Date workTypeItemValue) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			if (activateDate != null) {
				sb.append(getQueryForMaxActivateDate(TABLE, COL_WORK_TYPE_CODE, COL_ACTIVATE_DATE));
			}
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(inactivateFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_ITEM_CODE));
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_ITEM_VALUE));
			prepareStatement(sb.toString());
			if (activateDate != null) {
				index = setParamsForMaxActivateDate(index, activateDate, ps);
			}
			setParam(index++, workTypeItemCode);
			setParam(index++, workTypeItemValue);
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
	public List<WorkTypeItemDtoInterface> findForWorkTypeItem(Date activateDate, String workTypeItemCode,
			String preliminary) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			if (activateDate != null) {
				sb.append(getQueryForMaxActivateDate(TABLE, COL_WORK_TYPE_CODE, COL_ACTIVATE_DATE));
			}
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(inactivateFlagOff());
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_ITEM_CODE));
			sb.append(and());
			sb.append(equal(COL_PRELIMINARY));
			prepareStatement(sb.toString());
			if (activateDate != null) {
				index = setParamsForMaxActivateDate(index, activateDate, ps);
			}
			setParam(index++, workTypeItemCode);
			setParam(index++, preliminary);
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
			WorkTypeItemDtoInterface dto = (WorkTypeItemDtoInterface)baseDto;
			setParam(index++, dto.getTmmWorkTypeItemId());
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
			WorkTypeItemDtoInterface dto = (WorkTypeItemDtoInterface)baseDto;
			setParam(index++, dto.getTmmWorkTypeItemId());
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
		WorkTypeItemDtoInterface dto = (WorkTypeItemDtoInterface)baseDto;
		setParam(index++, dto.getTmmWorkTypeItemId());
		setParam(index++, dto.getWorkTypeCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getWorkTypeItemCode());
		setParam(index++, dto.getWorkTypeItemValue(), true);
		setParam(index++, dto.getPreliminary());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
}
