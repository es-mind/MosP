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
package jp.mosp.platform.dao.human.impl;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.human.HumanBinaryArrayDaoInterface;
import jp.mosp.platform.dto.human.HumanBinaryArrayDtoInterface;
import jp.mosp.platform.dto.human.impl.PfaHumanBinaryArrayDto;

/**
 * 人事汎用バイナリ一覧情報DAOクラス。
 */
public class PfaHumanBinaryArrayDao extends PlatformDao implements HumanBinaryArrayDaoInterface {
	
	/**
	 * 人事汎用履歴情報。
	 */
	public static final String	TABLE							= "pfa_human_binary_array";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFA_HUMAN_BINARY_ARRAY_ID	= "pfa_human_binary_array_id";
	
	/**
	 * 個人ID。
	 */
	public static final String	COL_PERSONAL_ID					= "personal_id";
	
	/**
	 * 人事項目区分。
	 */
	public static final String	COL_HUMAN_ITEM_TYPE				= "human_item_type";
	
	/**
	 * 人事項目区分。
	 */
	public static final String	COL_ROW_ID						= "row_id";
	
	/**
	 * 有効日。
	 */
	public static final String	COL_ACTIVATE_DATE				= "activate_date";
	
	/**
	 * 人事項目バイナリ値。
	 */
	public static final String	COL_HUMAN_ITEM_BINARY			= "human_item_binary";
	
	/**
	 * ファイル拡張子。
	 */
	public static final String	COL_FILE_TYPE					= "file_type";
	
	/**
	 * ファイル名。
	 */
	public static final String	COL_FILE_NAME					= "file_name";
	
	/**
	 * ファイル備考。
	 */
	public static final String	COL_FILE_REMARK					= "file_remark";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG				= "inactivate_flag";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	KEY_1							= COL_PFA_HUMAN_BINARY_ARRAY_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaHumanBinaryArrayDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaHumanBinaryArrayDto dto = new PfaHumanBinaryArrayDto();
		dto.setPfaHumanBinaryArrayId(getLong(COL_PFA_HUMAN_BINARY_ARRAY_ID));
		dto.setPersonalId(getString(COL_PERSONAL_ID));
		dto.setHumanItemType(getString(COL_HUMAN_ITEM_TYPE));
		dto.setHumanRowId(getInt(COL_ROW_ID));
		dto.setActivateDate(getDate(COL_ACTIVATE_DATE));
		dto.setHumanItemBinary(getBytes(COL_HUMAN_ITEM_BINARY));
		dto.setFileType(getString(COL_FILE_TYPE));
		dto.setFileName(getString(COL_FILE_NAME));
		dto.setFileRemark(getString(COL_FILE_REMARK));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<HumanBinaryArrayDtoInterface> mappingAll() throws MospException {
		List<HumanBinaryArrayDtoInterface> all = new ArrayList<HumanBinaryArrayDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public void setParams(BaseDtoInterface baseDto, boolean isInsert) throws MospException {
		HumanBinaryArrayDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaHumanBinaryArrayId());
		setParam(index++, dto.getPersonalId());
		setParam(index++, dto.getHumanItemType());
		setParam(index++, dto.getHumanRowId());
		setParam(index++, dto.getActivateDate());
		setParam(index++, dto.getHumanItemBinary());
		setParam(index++, dto.getFileType());
		setParam(index++, dto.getFileName());
		setParam(index++, dto.getFileRemark());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			HumanBinaryArrayDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanBinaryArrayId());
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
			HumanBinaryArrayDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaHumanBinaryArrayId());
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
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected HumanBinaryArrayDtoInterface castDto(BaseDtoInterface baseDto) {
		return (HumanBinaryArrayDtoInterface)baseDto;
	}
	
	@Override
	public List<HumanBinaryArrayDtoInterface> findForItemType(String personalId, String humanItemType)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_HUMAN_ITEM_TYPE));
			sb.append(getOrderByColumn(COL_ACTIVATE_DATE, COL_ROW_ID));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, humanItemType);
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
	public List<HumanBinaryArrayDtoInterface> findForInfoNotIn(List<String> itemNames) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(notIn(COL_HUMAN_ITEM_TYPE, itemNames.size()));
			prepareStatement(sb.toString());
			setParamsIn(MospUtility.toArray(itemNames));
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
	public HumanBinaryArrayDtoInterface findForKey(String personalId, String humanItemType, int rowId)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_PERSONAL_ID));
			sb.append(and());
			sb.append(equal(COL_HUMAN_ITEM_TYPE));
			sb.append(and());
			sb.append(equal(COL_ROW_ID));
			prepareStatement(sb.toString());
			setParam(index++, personalId);
			setParam(index++, humanItemType);
			setParam(index++, rowId);
			executeQuery();
			HumanBinaryArrayDtoInterface dto = null;
			if (rs.next()) {
				dto = castDto(mapping());
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
	public int findForMaxRowId() throws MospException {
		try {
			int max = 0;
			StringBuffer sb = new StringBuffer();
			sb.append(selectMax(COL_ROW_ID));
			sb.append(from(TABLE));
			prepareStatement(sb.toString());
			executeQuery();
			if (rs.next()) {
				max = rs.getInt(1);
			}
			return max;
		} catch (Throwable e) {
			throw new MospException(e);
		} finally {
			releaseResultSet();
			releasePreparedStatement();
		}
	}
	
}
