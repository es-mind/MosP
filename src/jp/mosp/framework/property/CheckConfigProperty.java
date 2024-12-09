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
package jp.mosp.framework.property;

import java.io.Serializable;
import java.util.LinkedHashMap;
import java.util.List;

/**
 * 人事汎用管理区分設定情報を格納する。
 */
public class CheckConfigProperty implements BaseProperty, Serializable {
	
	private static final long					serialVersionUID	= 1179191540418935336L;
	
	/**
	 * 人事汎用管理区分。
	 */
	private String								key;
	
	private List<LinkedHashMap<String, String>>	checkItem;
	
	private List<String>						extraCheckList;
	
	
	/**
	 * 人事汎用管理区分設定情報を生成する。
	 * @param key 人事汎用管理情報キー項目
	 * @param checkItemList 人事汎用管理チェック情報マップ
	 * @param list 人事汎用管理チェック情報リスト
	 * 
	 */
	public CheckConfigProperty(String key, List<LinkedHashMap<String, String>> checkItemList, List<String> list) {
		this.key = key;
		checkItem = checkItemList;
		extraCheckList = list;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * 人事汎用管理チェック項目を取得する
	 * @return chekItem
	 */
	public List<LinkedHashMap<String, String>> getCheckItem() {
		return checkItem;
	}
	
	/**
	 * @param chekItem セットする chekItem
	 */
	public void setCheckItem(List<LinkedHashMap<String, String>> chekItem) {
		checkItem = chekItem;
	}
	
	/**
	 * @return extraCheckList
	 */
	public List<String> getExtraCheckList() {
		return extraCheckList;
	}
	
	/**
	 * @param extraCheckList セットする extraCheckList
	 */
	public void setExtraCheckList(List<String> extraCheckList) {
		this.extraCheckList = extraCheckList;
	}
	
}
