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
import jp.mosp.time.dao.settings.WorkTypePatternItemDaoInterface;
import jp.mosp.time.dto.settings.WorkTypePatternItemDtoInterface;
import jp.mosp.time.dto.settings.impl.TmaWorkTypePatternItemDto;

/**
 * 勤務形態パターン項目DAOクラス。
 */
public class TmaWorkTypePatternItemDao extends PlatformDao implements WorkTypePatternItemDaoInterface {
	
	/**
	 * 勤務形態パターン項目。
	 */
	public static final String	TABLE								= "tma_work_type_pattern_item";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_TMA_WORK_TYPE_PATTERN_ITEM_ID	= "tma_work_type_pattern_item_id";
	
	/**
	 * パターンコード。
	 */
	public static final String	COL_PATTERN_CODE					= "pattern_code";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE					= "activate_date";
	
	/**
	 * 勤務形態コード。
	 */
	public static final String	COL_WORK_TYPE_CODE					= "work_type_code";
	
	/**
	 * 項目順序。
	 */
	public static final String	COL_ITEM_ORDER						= "item_order";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG					= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1								= COL_TMA_WORK_TYPE_PATTERN_ITEM_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public TmaWorkTypePatternItemDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		TmaWorkTypePatternItemDto dto = new TmaWorkTypePatternItemDto();
		dto.setTmaWorkTypePatternItemId(getLong(COL_TMA_WORK_TYPE_PATTERN_ITEM_ID));
		dto.setPatternCode(getString(COL_PATTERN_CODE));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setWorkTypeCode(getString(COL_WORK_TYPE_CODE));
		dto.setItemOrder(getInt(COL_ITEM_ORDER));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<WorkTypePatternItemDtoInterface> mappingAll() throws MospException {
		List<WorkTypePatternItemDtoInterface> list = new ArrayList<WorkTypePatternItemDtoInterface>();
		while (next()) {
			list.add((WorkTypePatternItemDtoInterface)mapping());
		}
		return list;
	}
	
	@Override
	public WorkTypePatternItemDtoInterface findForKey(String patternCode, Date activateDate, String workTypeCode)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PATTERN_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(and());
			sb.append(equal(COL_WORK_TYPE_CODE));
			prepareStatement(sb.toString());
			setParam(index++, patternCode);
			setParam(index++, activateDate);
			setParam(index++, workTypeCode);
			executeQuery();
			WorkTypePatternItemDtoInterface dto = null;
			if (next()) {
				dto = (WorkTypePatternItemDtoInterface)mapping();
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
	public List<WorkTypePatternItemDtoInterface> findForList(String patternCode, Date activateDate)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PATTERN_CODE));
			sb.append(and());
			sb.append(equal(COL_ACTIVATE_DATE));
			sb.append(getOrderByColumn(COL_ITEM_ORDER));
			prepareStatement(sb.toString());
			setParam(index++, patternCode);
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			WorkTypePatternItemDtoInterface dto = (WorkTypePatternItemDtoInterface)baseDto;
			setParam(index++, dto.getTmaWorkTypePatternItemId());
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
			WorkTypePatternItemDtoInterface dto = (WorkTypePatternItemDtoInterface)baseDto;
			setParam(index++, dto.getTmaWorkTypePatternItemId());
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
		WorkTypePatternItemDtoInterface dto = (WorkTypePatternItemDtoInterface)baseDto;
		setParam(index++, dto.getTmaWorkTypePatternItemId());
		setParam(index++, dto.getPatternCode());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getWorkTypeCode());
		setParam(index++, dto.getItemOrder());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
}
