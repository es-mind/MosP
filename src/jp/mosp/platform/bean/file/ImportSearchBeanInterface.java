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

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.file.ImportDtoInterface;

/**
 * インポートマスタ検索インターフェース。
 */
public interface ImportSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件からインポートマスタリストを取得する。<br><br>
	 * 設定された条件で、検索を行う。
	 * @return インポートマスタリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<ImportDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param code セットするインポートコード
	 */
	void setCode(String code);
	
	/**
	 * @param name セットするインポート名称
	 */
	void setName(String name);
	
	/**
	 * @param table セットするデータ区分
	 */
	void setTable(String table);
	
	/**
	 * @param type セットするデータ型
	 */
	void setType(String type);
	
	/**
	 * @param header セットするヘッダ
	 */
	void setHeader(String header);
	
	/**
	 * @param inactivateFlag セットする有効無効フラグ
	 */
	void setInactivateFlag(String inactivateFlag);
	
	/**
	 * @param tableTypeArray セットするテーブル区分配列
	 */
	void setTableTypeArray(String[][] tableTypeArray);
	
}
