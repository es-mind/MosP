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
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.xml.ViewProperty;
import jp.mosp.framework.xml.ViewTableProperty;

/**
 * 人事汎用管理区分設定情報を格納する。
 */
public class ViewConfigProperty implements BaseProperty, Serializable {
	
	private static final long			serialVersionUID	= 1179191540418935336L;
	
	/**
	 * 人事汎用管理区分。
	 */
	private String						key;
	
	/**
	 * 人事汎用管理形式。
	 * 通常(Normal)、履歴(History)、一覧(Array)。
	 */
	private String						type;
	
	/**
	 * 人事汎用管理表示区分情報群。
	 */
	private Map<String, BaseProperty>	view;
	
	/**
	 * 人事汎用管理表示テーブル情報群。
	 */
	private Map<String, BaseProperty>	viewTable;
	
	/**
	 * 人事汎用管理チェック情報群。
	 */
	private Map<String, BaseProperty>	checkConfig;
	
	/**
	 * 有効日日付制限確認フラグ。
	 * 履歴タイプ登録時に個人履歴有効日より
	 * 前の日付で登録できるか確認する。
	 */
	private boolean						isHumanExist;
	
	
	/**
	 * 人事汎用管理区分設定情報を生成する。
	 * @param key 人事汎用管理区分
	 */
	public ViewConfigProperty(String key) {
		this.key = key;
		view = new HashMap<String, BaseProperty>();
		viewTable = new HashMap<String, BaseProperty>();
		checkConfig = new HashMap<String, BaseProperty>();
		isHumanExist = false;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * 人事汎用管理形式を取得する。
	 * @return 人事汎用管理形式
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * 人事汎用管理形式を設定する。
	 * @param type 人事汎用管理形式
	 */
	public void setType(String type) {
		this.type = type;
	}
	
	/**
	 * 人事汎用管理表示区分情報又は
	 * 人事汎用管理表示テーブル情報を設定する。
	 * @param property 人事汎用管理表示区分情報又は人事汎用管理表示テーブル情報
	 */
	public void put(BaseProperty property) {
		if (property instanceof ViewProperty) {
			view.put(property.getKey(), property);
		}
		if (property instanceof ViewTableProperty) {
			viewTable.put(property.getKey(), property);
		}
		
		if (property instanceof CheckConfigProperty) {
			checkConfig.put(property.getKey(), property);
		}
	}
	
	/**
	 * 人事汎用管理表示区分情報を取得する。
	 * @param key 人事汎用管理表示区分
	 * @return 人事汎用管理表示区分情報
	 */
	public ViewProperty getView(String key) {
		BaseProperty property = view.get(key);
		return property != null ? (ViewProperty)property : null;
	}
	
	/**
	 * 人事汎用管理表示テーブル情報を取得する。
	 * @param key 人事汎用管理表示テーブル
	 * @return 人事汎用管理表示テーブル情報
	 */
	public ViewTableProperty getViewTable(String key) {
		BaseProperty property = viewTable.get(key);
		return property != null ? (ViewTableProperty)property : null;
	}
	
	/**
	 * 人事汎用管理チェック情報を取得する。
	 * @param key 人事汎用管理チェック情報
	 * @return 人事汎用管理チェック情報
	 */
	public CheckConfigProperty getCheckConfig(String key) {
		BaseProperty property = checkConfig.get(key);
		return property != null ? (CheckConfigProperty)property : null;
	}
	
	/**
	 * 人事汎用管理表示区分情報から
	 * 人事汎用管理表示テーブルのリストを取得する。
	 * @param key 人事汎用管理表示区分
	 * @return 人事汎用管理表示テーブルのリスト
	 */
	public List<ViewTableProperty> getViewTableList(String key) {
		List<ViewTableProperty> list = new ArrayList<ViewTableProperty>();
		ViewProperty property = getView(key);
		if (property == null) {
			return list;
		}
		for (String viewTableKey : property.getViewTableKeys()) {
			ViewTableProperty tableProperty = getViewTable(viewTableKey);
			if (tableProperty == null) {
				continue;
			}
			list.add(tableProperty);
		}
		return list;
	}
	
	/**
	 * @return isHumanExist
	 */
	public boolean isHumanExist() {
		return isHumanExist;
	}
	
	/**
	 * @param isHumanExist セットする isHumanExist
	 */
	public void setHumanExist(boolean isHumanExist) {
		this.isHumanExist = isHumanExist;
	}
	
}
