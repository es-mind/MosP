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
package jp.mosp.platform.bean.file.impl;

import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.file.ImportSearchBeanInterface;
import jp.mosp.platform.dao.file.ExportDaoInterface;
import jp.mosp.platform.dao.file.ImportDaoInterface;
import jp.mosp.platform.dto.file.ImportDtoInterface;

/**
 * インポートマスタ検索クラス。
 */
public class ImportSearchBean extends PlatformBean implements ImportSearchBeanInterface {
	
	/**
	 * インポートマスタDAO。
	 */
	private ImportDaoInterface	importDao;
	
	/**
	 * インポートコード。
	 */
	private String				code;
	
	/**
	 * インポート名称。
	 */
	private String				name;
	
	/**
	 * データ区分。
	 */
	private String				table;
	
	/**
	 * データ型。
	 */
	private String				type;
	
	/**
	 * ヘッダ。
	 */
	private String				header;
	
	/**
	 * 有効無効フラグ。
	 */
	private String				inactivateFlag;
	
	/**
	 * テーブル区分配列。
	 */
	private String[][]			tableTypeArray;
	
	
	/**
	 * コンストラクタ。
	 */
	public ImportSearchBean() {
		// 処理無し
	}
	
	@Override
	public void initBean() throws MospException {
		// インポートマスタDAO取得
		importDao = createDaoInstance(ImportDaoInterface.class);
	}
	
	@Override
	public List<ImportDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = importDao.getParamsMap();
		param.put(ExportDaoInterface.SEARCH_EXPORT_CODE, code);
		param.put(ExportDaoInterface.SEARCH_EXPORT_NAME, name);
		param.put(ExportDaoInterface.SEARCH_TABLE_TYPE, table);
		param.put(ExportDaoInterface.SEARCH_FILE_TYPE, type);
		param.put(ExportDaoInterface.SEARCH_HEADER_TYPE, header);
		param.put(ExportDaoInterface.SEARCH_INACTIVATE_FLAG, inactivateFlag);
		param.put(ExportDaoInterface.SEARCH_TABLE_TYPE_ARRAY, tableTypeArray);
		// 検索
		return importDao.findForSearch(param);
	}
	
	@Override
	public void setCode(String code) {
		this.code = code;
	}
	
	@Override
	public void setName(String name) {
		this.name = name;
	}
	
	@Override
	public void setTable(String table) {
		this.table = table;
	}
	
	@Override
	public void setType(String type) {
		this.type = type;
	}
	
	@Override
	public void setHeader(String header) {
		this.header = header;
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
	@Override
	public void setTableTypeArray(String[][] tableTypeArray) {
		this.tableTypeArray = getStringArrayClone(tableTypeArray);
	}
	
}
