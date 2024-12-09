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
package jp.mosp.platform.dao.file;

import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.file.ImportDtoInterface;

/**
 * インポート管理DAOインターフェース。<br>
 */
public interface ImportDaoInterface extends BaseDaoInterface {
	
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
	 * インポートコードからインポート情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param importCode インポートコード
	 * @return インポートマスタDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ImportDtoInterface findForKey(String importCode) throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件からインポートマスタリストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return インポートマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ImportDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return インポート管理検索条件マップ
	 */
	Map<String, Object> getParamsMap();
}
