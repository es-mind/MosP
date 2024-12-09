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
package jp.mosp.platform.dao.file.impl;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.file.ImportFieldDaoInterface;
import jp.mosp.platform.dto.file.ImportFieldDtoInterface;
import jp.mosp.platform.dto.file.impl.PfaImportFieldDto;

/**
 * インポートフィールドマスタDAOクラス。
 */
public class PfaImportFieldDao extends PlatformDao implements ImportFieldDaoInterface {
	
	/**
	 * インポートフィールドマスタ。
	 */
	public static final String	TABLE					= "pfa_import_field";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFA_IMPORT_FIELD_ID	= "pfa_import_field_id";
	
	/**
	 * インポートコード。
	 */
	public static final String	COL_IMPORT_CODE			= "import_code";
	
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
	public static final String	KEY_1					= COL_PFA_IMPORT_FIELD_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfaImportFieldDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfaImportFieldDto dto = new PfaImportFieldDto();
		dto.setPfaImportFieldId(getLong(COL_PFA_IMPORT_FIELD_ID));
		dto.setImportCode(getString(COL_IMPORT_CODE));
		dto.setFieldName(getString(COL_FIELD_NAME));
		dto.setFieldOrder(getInt(COL_FIELD_ORDER));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<ImportFieldDtoInterface> mappingAll() throws MospException {
		List<ImportFieldDtoInterface> all = new ArrayList<ImportFieldDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public ImportFieldDtoInterface findForKey(String importCode, String fieldName) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_IMPORT_CODE));
			sb.append(and());
			sb.append(equal(COL_FIELD_NAME));
			prepareStatement(sb.toString());
			setParam(index++, importCode);
			setParam(index++, fieldName);
			executeQuery();
			ImportFieldDtoInterface dto = null;
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
	public List<ImportFieldDtoInterface> findForList(String importCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_IMPORT_CODE));
			sb.append(getOrderByColumn(COL_FIELD_ORDER));
			prepareStatement(sb.toString());
			setParam(index++, importCode);
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
	public List<ImportFieldDtoInterface> findLikeStartNameList(String importCode, String[] aryFieldName)
			throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_IMPORT_CODE));
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
			setParam(index++, importCode);
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
	public int update(BaseDtoInterface baseDto) throws MospException {
		try {
			index = 1;
			prepareStatement(getUpdateQuery(getClass()));
			setParams(baseDto, false);
			ImportFieldDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaImportFieldId());
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
			ImportFieldDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfaImportFieldId());
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
		ImportFieldDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfaImportFieldId());
		setParam(index++, dto.getImportCode());
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
	protected ImportFieldDtoInterface castDto(BaseDtoInterface baseDto) {
		return (ImportFieldDtoInterface)baseDto;
	}
	
}
