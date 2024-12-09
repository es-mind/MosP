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
package jp.mosp.framework.xml;

import java.util.Map;

import jp.mosp.framework.property.AddonProperty;
import jp.mosp.framework.property.BaseProperty;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * MosP設定情報(アドオン)を作成する。<br>
 */
public class AddonTagConverter implements TagConverterInterface {
	
	/**
	 * アドオン要素の下位要素名(アドオン名称)。
	 */
	private static final String	TAG_ADDON_NAME	= "AddonName";
	
	/**
	 * アドオン要素の下位要素名(アドオン有効フラグ)。
	 */
	private static final String	TAG_ADDON_VALID	= "AddonValid";
	
	
	@Override
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		// アドオン要素を取得
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
		// 情報取得準備
		String addonName = null;
		// アドオン有効フラグ準備(デフォルト：無効)
		boolean addonValid = false;
		
		NodeList list = node.getChildNodes();
		int itemIndex = 0;
		int length = list.getLength();
		while (itemIndex < length) {
			// ノード取得
			Node item = list.item(itemIndex);
			if (TagUtility.isTag(item, TAG_ADDON_NAME)) {
				// アドオン名称取得
				addonName = TagUtility.trimText(item);
			}
			if (TagUtility.isTag(item, TAG_ADDON_VALID)) {
				// アドオン有効フラグ取得
				addonValid = Boolean.parseBoolean(TagUtility.trimText(item));
			}
			itemIndex++;
		}
		// アドオン設定情報追加
		properties.put(key, new AddonProperty(key, addonName, addonValid));
	}
	
}
