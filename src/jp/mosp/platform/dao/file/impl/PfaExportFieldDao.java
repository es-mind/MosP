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
package jp.mosp.platform.dao.file.impl;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.file.ExportFieldDaoInterface;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;
import jp.mosp.platform.dto.file.impl.PfaExportFieldDto;

/**
 * エクスポートフィールドマスタDAOクラス。<br>
 */
public class PfaExportFieldDao extends PlatformDao implements ExportFieldDaoInterface {
	
	/**
	 * エクスポートフィールドマスタ。
	 */
	public static final String	TABLE					= "pfa_export_field";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFA_EXPORT_FIELD_ID	= "pfa_export_field_id";
	
	/**
	 * エクスポートコード。
	 */
	public static final String	COL_EXPORT_CODE			= "export_code";
	
	/**
	 * フィールド名称。
	 */
	public static final String	COL_FIELD_NAME			= "field_name";
	
	/**
	 * フィールド順序。
	 */
	public static final String	COL_FIELD_ORDER			= "field_order";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG		= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1					= COL_PFA_EXPORT_FIELD_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaExportFieldDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
		
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaExportFieldDto dto = new PfaExportFieldDto();
		dto.setPfaExportFieldId(getLong(COL_PFA_EXPORT_FIELD_ID));
		dto.setExportCode(getString(COL_EXPORT_CODE));
		dto.setFieldName(getString(COL_FIELD_NAME));
		dto.setFieldOrder(getInt(COL_FIELD_ORDER));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<ExportFieldDtoInterface> mappingAll() throws MospException {
		List<ExportFieldDtoInterface> all = new ArrayList<ExportFieldDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public ExportFieldDtoInterface findForKey(String exportCode, String fieldName) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_EXPORT_CODE));
			sb.append(and());
			sb.append(equal(COL_FIELD_NAME));
			prepareStatement(sb.toString());
			setParam(index++, exportCode);
			setParam(index++, fieldName);
			executeQuery();
			ExportFieldDtoInterface dto = null;
			if (next()) {
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
	public List<ExportFieldDtoInterface> findForList(String exportCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_EXPORT_CODE));
			sb.append(getOrderByColumn(COL_FIELD_ORDER));
			prepareStatement(sb.toString());
			setParam(index++, exportCode);
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
	public List<ExportFieldDtoInterface> findLikeStartNameList(String exportCode, String[] aryFieldName)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_EXPORT_CODE));
			// データ区分制限条件SQL追加
			for (int i = 0; i < aryFieldName.length; i++) {
				if (i == 0) {
					sb.append(and());
					sb.append(leftParenthesis());
				}
				sb.append(like(COL_FIELD_NAME));
				if (i == aryFieldName.length - 1) {
					sb.append(rightParenthesis());
				} else {
					sb.append(or());
				}
			}
			
			sb.append(getOrderByColumn(COL_FIELD_ORDER));
			prepareStatement(sb.toString());
			setParam(index++, exportCode);
			for (String element : aryFieldName) {
				setParam(index++, startWithParam(element));
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
	public List<String> getFieldNameList(String exportCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_EXPORT_CODE));
			sb.append(getOrderByColumn(COL_FIELD_ORDER));
			prepareStatement(sb.toString());
			setParam(index++, exportCode);
			executeQuery();
			List<String> all = new ArrayList<String>();
			while (next()) {
				all.add(getString(COL_FIELD_NAME));
			}
			return all;
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
			ExportFieldDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaExportFieldId());
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
			ExportFieldDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaExportFieldId());
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
		ExportFieldDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaExportFieldId());
		setParam(index++, dto.getExportCode());
		setParam(index++, dto.getFieldName());
		setParam(index++, dto.getFieldOrder());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected ExportFieldDtoInterface castDto(BaseDtoInterface baseDto) {
		return (ExportFieldDtoInterface)baseDto;
	}
	
}
