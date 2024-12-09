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
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.framework.base.BaseDtoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformDao;
import jp.mosp.platform.dao.file.ImportDaoInterface;
import jp.mosp.platform.dto.file.ImportDtoInterface;
import jp.mosp.platform.dto.file.impl.PfmImportDto;

/**
 * インポートマスタDAOクラス。
 */
public class PfmImportDao extends PlatformDao implements ImportDaoInterface {
	
	/**
	 * インポートマスタ。
	 */
	public static final String	TABLE				= "pfm_import";
	
	/**
	 * レコード識別ID。
	 */
	public static final String	COL_PFM_IMPORT_ID	= "pfm_import_id";
	
	/**
	 * インポートコード。
	 */
	public static final String	COL_IMPORT_CODE		= "import_code";
	
	/**
	 * インポート名称。
	 */
	public static final String	COL_IMPORT_NAME		= "import_name";
	
	/**
	 * データ区分。
	 */
	public static final String	COL_IMPORT_TABLE	= "import_table";
	
	/**
	 * データ型。
	 */
	public static final String	COL_TYPE			= "type";
	
	/**
	 * ヘッダ有無。
	 */
	public static final String	COL_HEADER			= "header";
	
	/**
	 * 無効フラグ。
	 */
	public static final String	COL_INACTIVATE_FLAG	= "inactivate_flag";
	
	/**
	 * キー。
	 */
	public static final String	KEY_1				= COL_PFM_IMPORT_ID;
	
	
	/**
	 * コンストラクタ。
	 */
	public PfmImportDao() {
		// 処理無し
	}
	
	@Override
	public void initDao() {
		// 処理無し
	}
	
	@Override
	public BaseDto mapping() throws MospException {
		PfmImportDto dto = new PfmImportDto();
		dto.setPfmImportId(getLong(COL_PFM_IMPORT_ID));
		dto.setImportCode(getString(COL_IMPORT_CODE));
		dto.setImportName(getString(COL_IMPORT_NAME));
		dto.setImportTable(getString(COL_IMPORT_TABLE));
		dto.setType(getString(COL_TYPE));
		dto.setHeader(getInt(COL_HEADER));
		dto.setInactivateFlag(getInt(COL_INACTIVATE_FLAG));
		mappingCommonInfo(dto);
		return dto;
	}
	
	@Override
	public List<ImportDtoInterface> mappingAll() throws MospException {
		List<ImportDtoInterface> all = new ArrayList<ImportDtoInterface>();
		while (next()) {
			all.add(castDto(mapping()));
		}
		return all;
	}
	
	@Override
	public ImportDtoInterface findForKey(String importCode) throws MospException {
		try {
			index = 1;
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(equal(COL_IMPORT_CODE));
			prepareStatement(sb.toString());
			setParam(index++, importCode);
			executeQuery();
			ImportDtoInterface dto = null;
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
	public List<ImportDtoInterface> findForSearch(Map<String, Object> param) throws MospException {
		try {
			// パラメータインデックス準備
			index = 1;
			// 検索条件準備
			String importCode = getSearchParam(param, SEARCH_EXPORT_CODE);
			String importName = getSearchParam(param, SEARCH_EXPORT_NAME);
			String tableType = getSearchParam(param, SEARCH_TABLE_TYPE);
			String fileType = getSearchParam(param, SEARCH_FILE_TYPE);
			String headerType = getSearchParam(param, SEARCH_HEADER_TYPE);
			String inactivateFlag = getSearchParam(param, SEARCH_INACTIVATE_FLAG);
			String[][] tableTypeArray = (String[][])param.get(SEARCH_TABLE_TYPE_ARRAY);
			// データ区分制限数取得
			int tableTypeCount = tableTypeArray == null ? 0 : tableTypeArray.length;
			// SQL準備
			StringBuffer sb = getSelectQuery(getClass());
			sb.append(where());
			sb.append(deleteFlagOff());
			sb.append(and());
			sb.append(like(COL_IMPORT_CODE));
			sb.append(and());
			sb.append(like(COL_IMPORT_NAME));
			if (tableType.isEmpty() == false) {
				sb.append(and());
				sb.append(equal(COL_IMPORT_TABLE));
			}
			if (fileType.isEmpty() == false) {
				sb.append(and());
				sb.append(equal(COL_TYPE));
			}
			if (headerType.isEmpty() == false) {
				sb.append(and());
				sb.append(equal(COL_HEADER));
			}
			if (inactivateFlag.isEmpty() == false) {
				sb.append(and());
				sb.append(equal(COL_INACTIVATE_FLAG));
			}
			// データ区分制限条件SQL追加
			for (int i = 0; i < tableTypeCount; i++) {
				if (i == 0) {
					sb.append(and());
					sb.append(leftParenthesis());
				}
				sb.append(equal(COL_IMPORT_TABLE));
				if (i == tableTypeCount - 1) {
					sb.append(rightParenthesis());
				} else {
					sb.append(or());
				}
			}
			// ステートメント生成
			prepareStatement(sb.toString());
			// パラメータ設定
			setParam(index++, startWithParam(importCode));
			setParam(index++, containsParam(importName));
			if (tableType.isEmpty() == false) {
				setParam(index++, tableType);
			}
			if (fileType.isEmpty() == false) {
				setParam(index++, fileType);
			}
			if (headerType.isEmpty() == false) {
				setParam(index++, Integer.parseInt(headerType));
			}
			if (inactivateFlag.isEmpty() == false) {
				setParam(index++, Integer.parseInt(inactivateFlag));
			}
			// データ区分制限検索条件設定
			for (int i = 0; i < tableTypeCount; i++) {
				setParam(index++, tableTypeArray[i][0]);
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
			ImportDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmImportId());
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
			ImportDtoInterface dto = castDto(baseDto);
			setParam(index++, dto.getPfmImportId());
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
		ImportDtoInterface dto = castDto(baseDto);
		setParam(index++, dto.getPfmImportId());
		setParam(index++, dto.getImportCode());
		setParam(index++, dto.getImportName());
		setParam(index++, dto.getImportTable());
		setParam(index++, dto.getType());
		setParam(index++, dto.getHeader());
		setParam(index++, dto.getInactivateFlag());
		setCommonParams(baseDto, isInsert);
	}
	
	@Override
	public Map<String, Object> getParamsMap() {
		return new HashMap<String, Object>();
	}
	
	/**
	 * DTOインスタンスのキャストを行う。<br>
	 * @param baseDto 対象DTO
	 * @return キャストされたDTO
	 */
	protected ImportDtoInterface castDto(BaseDtoInterface baseDto) {
		return (ImportDtoInterface)baseDto;
	}
	
}
