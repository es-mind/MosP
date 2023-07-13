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

import java.util.Map;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.property.ConventionProperty;
import jp.mosp.framework.property.MappingProperty;

/**
 * MosP設定情報(人事汎用項目区分)を作成する。<br>
 */
public class ConventionTagConverter implements TagConverterInterface {
	
	@Override
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		// Convention
		Node node = wrapper.getNode();
		int index = wrapper.index;
		String path = wrapper.path;
		// キー情報取得
		String key = TagUtility.getKey(node);
		// キー情報確認
		if (key.isEmpty()) {
			// エラーログ出力
			TagUtility.noElementKeyMessage(path, node, index);
			return;
		}
		BaseProperty baseProperty = properties.get(key);
		if (baseProperty == null) {
			baseProperty = new ConventionProperty(key);
		}
		ConventionProperty property = (ConventionProperty)baseProperty;
		
		NodeList list = TagUtility.getElements("./*", node);
		int itemIndex = 0;
		int length = list.getLength();
		while (itemIndex < length) {
			// ノード取得
			Node item = list.item(itemIndex);
			BaseProperty element = null;
			switch (Convention.get(item.getNodeName())) {
				case ITEM:
					element = toItem(item);
					break;
				case INPUT:
					element = toInput(item);
					break;
				case LABEL:
					element = toLabel(item);
					break;
				case CSS:
					element = toCss(item);
					break;
				case MAPPING:
					element = toMapping(item);
					break;
				default:
					break;
			}
			if (element == null) {
				TagUtility.invalidItemMassage(path, node, item.getNodeName(), itemIndex);
			} else {
				property.put(element);
			}
			itemIndex++;
		}
		// 規約情報追加
		properties.put(key, property);
	}
	
	private ItemProperty toItem(Node item) {
		// 人事汎用項目キー情報取得
		String key = TagUtility.getKey(item);
		// 人事汎用項目キー情報確認
		if (key.isEmpty()) {
			return null;
		}
		// 人事汎用項目形式取得
		String type = TagUtility.getString("@type", item);
		// 下位要素準備
		String css = null;
		String fixedValue = null;
		String codeKey = null;
		String namingKey = null;
		String format = null;
		String dataType = null;
		boolean isNeedSpace = true;
		// 下位要素取得
		NodeList list = item.getChildNodes();
		int index = 0;
		int length = list.getLength();
		while (index < length) {
			Node node = list.item(index);
			if (TagUtility.isTag(node, "Css")) {
				css = TagUtility.trimText(node);
			}
			if (TagUtility.isTag(node, "FixedValue")) {
				fixedValue = TagUtility.trimText(node);
			}
			if (TagUtility.isTag(node, "CodeKey")) {
				codeKey = TagUtility.trimText(node);
			}
			if (TagUtility.isTag(node, "NamingKey")) {
				namingKey = TagUtility.trimText(node);
			}
			if (TagUtility.isTag(node, "Format")) {
				format = TagUtility.trimText(node);
			}
			if (TagUtility.isTag(node, "DataType")) {
				dataType = TagUtility.trimText(node);
			}
			if (TagUtility.isTag(node, "IsNeedSpace")) {
				isNeedSpace = Boolean.parseBoolean(TagUtility.trimText(node));
			}
			index++;
		}
		// 人事汎用項目設定情報作成
		ItemProperty itemProperty = new ItemProperty(key, type);
		// 人事汎用項目設定情報に下位要素を設定
		itemProperty.setCss(css);
		itemProperty.setFixedValue(fixedValue);
		itemProperty.setCodeKey(codeKey);
		itemProperty.setNamingKey(namingKey);
		itemProperty.setFormat(format);
		itemProperty.setDataType(dataType);
		itemProperty.setNeedSpace(isNeedSpace);
		return itemProperty;
	}
	
	private InputProperty toInput(Node item) {
		// キー情報取得
		String key = TagUtility.getKey(item);
		// キー情報確認
		if (key.isEmpty()) {
			return null;
		}
		String type = TagUtility.getString("@type", item);
		if (type.isEmpty()) {
			return null;
		}
		// CSS属性取得
		String css = TagUtility.getString("@css", item);
		if (item.hasChildNodes()) {
			int maxlength = -1;
			String codeKey = null;
			String namingKey = null;
			NodeList list = item.getChildNodes();
			int index = 0;
			int length = list.getLength();
			while (index < length) {
				Node inputItem = list.item(index);
				if (TagUtility.isTag(inputItem, "MaxLength")) {
					try {
						maxlength = Integer.parseInt(TagUtility.trimText(inputItem));
					} catch (NumberFormatException e) {
						return null;
					}
				}
				if (TagUtility.isTag(inputItem, "CodeKey")) {
					codeKey = TagUtility.trimText(inputItem);
				}
				if (TagUtility.isTag(inputItem, "NamingKey")) {
					namingKey = TagUtility.trimText(inputItem);
				}
				index++;
			}
			return new InputProperty(key, type, maxlength, css, codeKey, namingKey);
		}
		return new InputProperty(key, type);
	}
	
	private LabelProperty toLabel(Node item) {
		// キー情報取得
		String key = TagUtility.getKey(item);
		// キー情報確認
		if (key.isEmpty()) {
			return null;
		}
		String format = null;
		String codeKey = null;
		String namingKey = null;
		// CSS属性取得
		String css = TagUtility.getString("@css", item);
		NodeList list = item.getChildNodes();
		int index = 0;
		int length = list.getLength();
		while (index < length) {
			Node inputItem = list.item(index);
			if (TagUtility.isTag(inputItem, "Format")) {
				format = TagUtility.trimText(inputItem);
			}
			if (TagUtility.isTag(inputItem, "CodeKey")) {
				codeKey = TagUtility.trimText(inputItem);
			}
			if (TagUtility.isTag(inputItem, "NamingKey")) {
				namingKey = TagUtility.trimText(inputItem);
			}
			index++;
		}
		if (format == null && codeKey == null && namingKey == null) {
			return null;
		}
		return new LabelProperty(key, format, css, codeKey, namingKey);
	}
	
	private CssProperty toCss(Node item) {
		// キー情報取得
		String key = TagUtility.getKey(item);
		// キー情報確認
		if (key.isEmpty()) {
			return null;
		}
		String name = TagUtility.getString("@name", item);
		if (name.isEmpty()) {
			return null;
		}
		return new CssProperty(key, name);
	}
	
	private MappingProperty toMapping(Node item) {
		// キー情報取得
		String key = TagUtility.getKey(item);
		// キー情報確認
		if (key.isEmpty()) {
			return null;
		}
		String dao = TagUtility.getString("@dao", item);
		if (dao.isEmpty()) {
			return null;
		}
		return new MappingProperty(key, dao);
	}
	
	
	/**
	 * 人事汎用項目区分。<br>
	 */
	enum Convention {
		
		/**
		 * 人事汎用項目設定。<br>
		 */
		ITEM {
			
			@Override
			public String getName() {
				return "Item";
			}
		},
		
		/**
		 * 人事汎用入力形式設定。<br>
		 */
		INPUT {
			
			@Override
			public String getName() {
				return "Input";
			}
		},
		
		/**
		 * 人事汎用表示形式設定。<br>
		 */
		LABEL {
			
			@Override
			public String getName() {
				return "Label";
			}
		},
		
		/**
		 * CSS情報。<br>
		 */
		CSS {
			
			@Override
			public String getName() {
				return "Css";
			}
		},
		
		/**
		 * MosP設定情報(DAO)。<br>
		 */
		MAPPING {
			
			@Override
			public String getName() {
				return "Mapping";
			}
		},
		
		/**
		 * その他。<br>
		 */
		UNKNOWN {
			
			@Override
			public String getName() {
				return "";
			}
		};
		
		/**
		 * 人事汎用項目区分タグ名を取得する。<br>
		 * @return 人事汎用項目区分タグ名
		 */
		public abstract String getName();
		
		/**
		 * 人事汎用項目区分を取得する。<br>
		 * @param tagName タグ名
		 * @return 人事汎用項目区分
		 */
		public static Convention get(String tagName) {
			for (Convention convention : Convention.values()) {
				if (convention.getName().equals(tagName)) {
					return convention;
				}
			}
			return UNKNOWN;
		}
		
	}
	
}
