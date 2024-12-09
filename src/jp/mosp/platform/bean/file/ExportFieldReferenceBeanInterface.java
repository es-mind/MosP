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
package jp.mosp.platform.bean.file;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.file.ExportFieldDtoInterface;

/**
 * エクスポートフィールドマスタ参照インターフェース。
 */
public interface ExportFieldReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * エクスポートフィールドリスト取得。
	 * <p>
	 * エクスポートコードからエクスポートフィールドリストを取得。
	 * </p>
	 * @param exportCode エクスポートコード
	 * @return エクスポートフィールドリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ExportFieldDtoInterface> getExportFieldList(String exportCode) throws MospException;
	
	/**
	 * エクスポートフィールド名称リスト(フィールド順序昇順)を取得する。<br>
	 * @param exportCode エクスポートコード
	 * @return エクスポートフィールド名称リスト(フィールド順序昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<String> getExportFieldNameList(String exportCode) throws MospException;
	
	/**
	 * エクスポートコードとフィールド名称（前方一致）でエクスポートマスタリストを取得する。<br>
	 * @param exportCode エクスポートコード
	 * @param aryFieldName フィールド名称
	 * @return エクスポートマスタリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<ExportFieldDtoInterface> getLikeStartNameList(String exportCode, String[] aryFieldName) throws MospException;
	
}
