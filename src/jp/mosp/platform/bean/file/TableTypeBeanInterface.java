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
package jp.mosp.platform.bean.file;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * テーブル区分配列(インポート及びエクスポート)取得処理インターフェース。<br>
 */
public interface TableTypeBeanInterface extends BaseBeanInterface {
	
	/**
	 * テーブル区分配列(インポート及びエクスポート)を取得する。<br>
	 * @param tableTypeCodeKey コードキー(テーブル区分)
	 * @param needBlank        空白行要否(true：空白行要、false：空白行不要)
	 * @return テーブル区分配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getTableTypeArray(String tableTypeCodeKey, boolean needBlank) throws MospException;
	
}
