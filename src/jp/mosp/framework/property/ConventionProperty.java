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
package jp.mosp.framework.property;

import java.io.Serializable;
import java.util.HashMap;
import java.util.Map;

import jp.mosp.framework.xml.CssProperty;
import jp.mosp.framework.xml.InputProperty;
import jp.mosp.framework.xml.ItemProperty;
import jp.mosp.framework.xml.LabelProperty;

/**
 * 人事汎用項目区分設定情報を格納する。
 */
public class ConventionProperty implements BaseProperty, Serializable {
	
	private static final long			serialVersionUID	= -1716999191932056781L;
	
	/**
	 * 人事汎用項目区分。<br>
	 * 人事汎用管理区分と同じキーを持つ。
	 */
	private String						key;
	
	/**
	 * 人事汎用項目情報群
	 */
	private Map<String, BaseProperty>	item;
	
	/**
	 * 人事汎用入力形式情報群。
	 */
	private Map<String, BaseProperty>	input;
	
	/**
	 * 人事汎用表示形式情報群。
	 */
	private Map<String, BaseProperty>	label;
	
	// TODO
	private Map<String, BaseProperty>	css;
	
	// TODO
	private Map<String, BaseProperty>	mapping;
	
	
	/**
	 * 人事汎用項目区分設定情報を生成する。
	 * @param key 人事汎用項目区分
	 */
	public ConventionProperty(String key) {
		this.key = key;
		item = new HashMap<String, BaseProperty>();
		input = new HashMap<String, BaseProperty>();
		label = new HashMap<String, BaseProperty>();
		css = new HashMap<String, BaseProperty>();
		mapping = new HashMap<String, BaseProperty>();
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * 人事汎用項目情報又は人事汎用入力形式情報、
	 * 人事汎用表示形式情報などを設定する。
	 * @param property 人事汎用項目情報又は人事汎用入力形式情報、人事汎用表示形式情報など
	 */
	public void put(BaseProperty property) {
		if (property instanceof ItemProperty) {
			item.put(property.getKey(), property);
		}
		if (property instanceof InputProperty) {
			input.put(property.getKey(), property);
		}
		if (property instanceof LabelProperty) {
			label.put(property.getKey(), property);
		}
		if (property instanceof CssProperty) {
			css.put(property.getKey(), property);
		}
		if (property instanceof MappingProperty) {
			mapping.put(property.getKey(), property);
		}
	}
	
	/**
	 * 人事汎用項目設定情報を取得する。
	 * @param key 人事汎用項目区分
	 * @return item 人事汎用項目情報
	 */
	public ItemProperty getItem(String key) {
		BaseProperty value = item.get(key);
		return value != null ? (ItemProperty)value : null;
	}
	
	/**
	 * @param key 人事汎用項目区分
	 * @return input 人事汎用入力形式情報
	 */
	public InputProperty getInput(String key) {
		BaseProperty value = input.get(key);
		return value != null ? (InputProperty)value : null;
	}
	
	/**
	 * @param key 人事汎用項目区分
	 * @return label 人事汎用表示形式情報
	 */
	public LabelProperty getLabel(String key) {
		BaseProperty value = label.get(key);
		return value != null ? (LabelProperty)value : null;
	}
	
	/**
	 * @param key 人事汎用項目区分
	 * @return css
	 */
	public CssProperty getCss(String key) {
		BaseProperty value = css.get(key);
		return value != null ? (CssProperty)value : null;
	}
	
	/**
	 * @param key 人事汎用項目区分
	 * @return mapping
	 */
	public MappingProperty getMapping(String key) {
		BaseProperty value = mapping.get(key);
		return value != null ? (MappingProperty)value : null;
	}
	
}
