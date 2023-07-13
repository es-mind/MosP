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
package jp.mosp.platform.dao.file;

import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.file.ExportDtoInterface;

/**
 * エクスポートマスタDAOインターフェース。<br>
 */
public interface ExportDaoInterface extends BaseDaoInterface {
	
	/**
	 * 検索条件(エクスポートコード)。
	 */
	String	SEARCH_EXPORT_CODE		= "exportCode";
	
	/**
	 * 検索条件(エクスポート名称)。
	 */
	String	SEARCH_EXPORT_NAME		= "exportName";
	
	/**
	 * 検索条件(テーブル区分)。
	 */
	String	SEARCH_TABLE_TYPE		= "tableType";
	
	/**
	 * 検索条件(ファイル区分)。
	 */
	String	SEARCH_FILE_TYPE		= "fileType";
	
	/**
	 * 検索条件(ヘッダー有無区分)。
	 */
	String	SEARCH_HEADER_TYPE		= "headerType";
	
	/**
	 * 検索条件(有効無効フラグ)。
	 */
	String	SEARCH_INACTIVATE_FLAG	= "inactivateFlag";
	
	/**
	 * 検索条件(テーブル区分配列)。
	 */
	String	SEARCH_TABLE_TYPE_ARRAY	= "tableTypeArray";
	
	
	/**
	 * エクスポートコードからエクスポート情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param exportCode エクスポートコード
	 * @return エクスポートマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ExportDtoInterface findForKey(String exportCode) throws MospException;
	
	/**
	 * 検索条件からエクスポートマスタリストを取得する。<br>
	 * @param param 検索条件
	 * @return エクスポートマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ExportDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return エクスポート管理検索条件マップ
	 */
	Map<String, Object> getParamsMap();
	
}
