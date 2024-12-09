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

import java.util.Date;
import java.util.Map;

import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.property.ModelProperty;
import jp.mosp.framework.utils.DateUtility;

import org.w3c.dom.Element;
import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * MosP設定情報(Model)を作成する。<br>
 */
public class ModelTagConverter implements TagConverterInterface {
	
	/**
	 * モデル要素の下位要素名(モデル有効日)。
	 */
	private static final String	TAG_MODEL_ACTIVATE_DATE	= "ModelActivateDate";
	
	/**
	 * モデル要素の下位要素名(モデルクラス)。
	 */
	private static final String	TAG_MODEL_CLASS			= "ModelClass";
	
	
	@Override
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		// ノード情報取得
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
		// モデル設定情報取得
		ModelProperty modelProperty = (ModelProperty)properties.get(key);
		// モデル設定情報確認
		if (modelProperty == null) {
			// モデル設定情報追加
			modelProperty = new ModelProperty(key);
			properties.put(key, modelProperty);
		}
		// モデル有効日(モデル設定情報)準備
		Date modelActivateDate = null;
		// モデルクラス(モデル設定情報)準備
		String modelClass = null;
		
		// 追加モデル設定情報取得
		NodeList nodeList = node.getChildNodes();
		for (int i = 0; i < nodeList.getLength(); i++) {
			// ノード取得
			Node item = nodeList.item(i);
			// ノード確認
			if (item.hasChildNodes() == false) {
				continue;
			}
			if (item instanceof Element == false) {
				continue;
			}
			// 下位要素取得
			Element lowerElement = (Element)item;
			if (TagUtility.isTag(item, TAG_MODEL_ACTIVATE_DATE)) {
				modelActivateDate = DateUtility.getDate(TagUtility.trimText(lowerElement));
				continue;
			}
			if (TagUtility.isTag(item, TAG_MODEL_CLASS)) {
				modelClass = TagUtility.trimText(lowerElement);
				continue;
			}
		}
		// モデルクラス要素が無い場合
		if (modelClass == null) {
			modelClass = TagUtility.trimText(node);
		}
		// モデル設定情報追加確認
		if (modelClass == null) {
			// エラーログ出力
			TagUtility.invalidItemMassage(path, node, TAG_MODEL_CLASS, index);
			return;
		}
		// モデル有効日確認
		if (modelActivateDate == null) {
			// モデルクラス名に設定(モデル有効日が無い場合)
			modelProperty.setModelClass(modelClass);
		} else {
			// モデルクラス名群に配置(モデル有効日が有る場合)
			modelProperty.getModelClassMap().put(modelActivateDate, modelClass);
		}
	}
	
}
