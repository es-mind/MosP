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

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;

/**
 * エクスポートフィールドマスタDAOインターフェース。<br>
 */
public interface ExportFieldDaoInterface extends BaseDaoInterface {
	
	/**
	 * エクスポートコードとフィールド名称からエクスポートフィールド情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param exportCode エクスポートコード
	 * @param fieldName フィールド名称
	 * @return エクスポートフィールド
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	ExportFieldDtoInterface findForKey(String exportCode, String fieldName) throws MospException;
	
	/**
	 * エクスポートコードからエクスポートフィールドマスタリストを取得する。<br>
	 * @param exportCode エクスポートコード
	 * @return エクスポートフィールドマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ExportFieldDtoInterface> findForList(String exportCode) throws MospException;
	
	/**
	 * @param exportCode エクスポートコード
	 * @return フィールド名称リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	public List<String> getFieldNameList(String exportCode) throws MospException;
	
	/**
	 * エクスポートコードとフィールド名称（前方一致）でエクスポートマスタリストを取得する
	 * @param exportCode エクスポートコード
	 * @param aryFieldName フィールド名称
	 * @return エクスポートマスタリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<ExportFieldDtoInterface> findLikeStartNameList(String exportCode, String[] aryFieldName) throws MospException;
	
}
