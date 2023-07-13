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

import jp.mosp.framework.property.BaseProperty;

/**
 * 人事汎用管理表示区分設定情報を格納する。
 */
public class ViewProperty implements BaseProperty {
	
	/**
	 * 人事汎用管理表示区分。
	 * 人事情報一覧画面(HumanInfo)、人事汎用管理区分(通常)編集画面(NormalCard)等。
	 */
	private final String	key;
	
	/**
	 * 人事汎用管理表示テーブルキーの配列。
	 */
	private final String[]	viewTableKeys;
	
	/**
	 * 人事汎用管理表示テーブルタイトルの配列。
	 */
	private final String[]	viewTableTitles;
	
	
	/**
	 * 人事汎用管理表示区分情報を生成する。
	 * @param key 人事汎用管理表示区分
	 * @param viewTableKeys 人事汎用管理表示テーブル配列
	 * @param viewTableTitles 人事汎用管理表示テーブルタイトル配列
	 */
	public ViewProperty(String key, String[] viewTableKeys, String[] viewTableTitles) {
		this.key = key;
		this.viewTableKeys = viewTableKeys == null ? new String[0] : viewTableKeys;
		this.viewTableTitles = viewTableTitles == null ? new String[0] : viewTableTitles;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * 人事汎用管理表示テーブルの配列を取得する。
	 * @return 人事汎用管理表示テーブルの配列
	 */
	public String[] getViewTableKeys() {
		return viewTableKeys.clone();
	}
	
	/**
	 * 人事汎用管理表示テーブルタイトルの配列を取得する。
	 * @return 人事汎用管理表示テーブルタイトルの配列
	 */
	public String[] getViewTableTitles() {
		return viewTableTitles.clone();
	}
	
}
