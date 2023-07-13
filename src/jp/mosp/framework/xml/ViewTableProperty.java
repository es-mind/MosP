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
package jp.mosp.framework.xml;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import jp.mosp.framework.property.BaseProperty;

/**
 * 人事汎用管理表示テーブル(VIEW_TABLE)設定情報を格納する。<br>
 * 人事汎用管理機能で表示するテーブルのことであり、
 * 後述の人事汎用表示テーブル項目を纏めるくくりのこと。<br>
 */
public class ViewTableProperty implements BaseProperty {
	
	/**
	 * 人事汎用管理表示テーブル。
	 */
	private final String					key;
	
	/**
	 * 人事汎用表示テーブル形式。
	 */
	private final String					type;
	
	/**
	 * 1行に表示する項目数。
	 */
	private final int						pair;
	
	/**
	 * 有効日名称。<br>
	 * 人事汎用管理形式が、履歴(History)、一覧(Array)の場合に用いられる。<br>
	 * 固定で表示される有効日の名称を設定する。<br>
	 * nullの場合は「有効日」と表示される。<br>
	 */
	private final String					dateName;
	
	/**
	 * TableItemの項目リスト。
	 */
	private final List<TableItemProperty>	tableItemList;
	
	
	/**
	 * 人事汎用管理表示テーブル(VIEW_TABLE)情報を生成する。
	 * @param key      人事汎用管理表示テーブル
	 * @param type     人事汎用表示テーブル形式
	 * @param pair     1行に表示する項目数
	 * @param dateName 有効日名称
	 * @param tableItemList 人事汎用管理表示テーブル(VIEW_TABLE)情報リスト
	 */
	public ViewTableProperty(String key, String type, int pair, String dateName,
			List<TableItemProperty> tableItemList) {
		this.key = key;
		this.type = type;
		this.pair = pair;
		this.dateName = dateName;
		this.tableItemList = tableItemList == null ? new ArrayList<TableItemProperty>()
				: Collections.unmodifiableList(tableItemList);
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * 人事汎用表示テーブル形式を取得する。
	 * @return 人事汎用表示テーブル形式
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return pair
	 */
	public int getPair() {
		return pair;
	}
	
	/**
	 * @return dateName
	 */
	public String getDateName() {
		return dateName;
	}
	
	/**
	 * 人事汎用表示テーブル項目情報リストを取得する。
	 * @return tableItem 人事汎用表示テーブル項目情報
	 */
	public List<TableItemProperty> getTableItem() {
		return tableItemList;
	}
	
}
