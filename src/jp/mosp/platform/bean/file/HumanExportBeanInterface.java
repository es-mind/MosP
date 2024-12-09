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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * 人事マスタエクスポートインターフェース。
 */
public interface HumanExportBeanInterface extends ExportBeanInterface, BaseBeanInterface {
	
	/**
	 * フィールド名称（前方一致）存在確認
	 * @param exportCode エクスポートコード
	 * @param aryFieldName フィールド名称配列
	 * @return データ存在確認（true:データ有り、false:データ無し）
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	boolean isExistLikeFieldName(String exportCode, String[] aryFieldName) throws MospException;
}
