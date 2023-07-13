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

import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.property.CodeItemProperty;
import jp.mosp.framework.property.CodeProperty;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * MosP設定情報(コード)を作成する。<br>
 */
public class CodeTagConverter implements TagConverterInterface {
	
	/**
	 * コード要素の下位要素名(コード項目)。
	 */
	private static final String	TAG_CODE_ITEM	= "CodeItem";
	
	/**
	 * コード項目要素の下位要素名(コード項目名)。
	 */
	private static final String	TAG_ITEM_NAME	= "ItemName";
	
	/**
	 * 表示順要素名。
	 */
	private static final String	TAG_VIEW_INDEX	= "ViewIndex";
	
	/**
	 * コード項目要素の下位要素名(コード項目表示フラグ)。
	 */
	private static final String	TAG_VIEW_FLAG	= "ViewFlag";
	
	
	@Override
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		// コード要素を取得
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
		// 要素数確認
		NodeList codeItemList = TagUtility.getElements(TAG_CODE_ITEM, node);
		if (codeItemList.getLength() == 0) {
			// エラーログ出力
			TagUtility.invalidMassage(path, node);
			return;
		}
		BaseProperty baseProperty = properties.get(key);
		if (baseProperty == null) {
			// コード設定情報追加
			baseProperty = new CodeProperty(key);
		}
		// コード設定情報取得
		CodeProperty property = (CodeProperty)baseProperty;
		// コード項目設定情報群取得
		Map<String, CodeItemProperty> codeItemMap = property.getCodeItemMap();
		// コード項目設定情報追加
		int itemIndex = 0;
		int length = codeItemList.getLength();
		while (itemIndex < length) {
			// ノードを取得
			Node item = codeItemList.item(itemIndex);
			// MosP設定情報(コード項目)を取得
			CodeItemProperty codeItem = toCodeItemProperty(item);
			if (codeItem == null) {
				// エラーログ出力
				TagUtility.invalidItemMassage(path, node, TAG_CODE_ITEM, itemIndex);
			} else {
				codeItemMap.put(codeItem.getKey(), codeItem);
			}
			itemIndex++;
		}
		// コード設定情報追加
		properties.put(key, property);
	}
	
	/**
	 * MosP設定情報(コード項目)を取得する。<br>
	 * @param item ノード
	 * @return MosP設定情報(コード項目)
	 */
	protected CodeItemProperty toCodeItemProperty(Node item) {
		// キー情報取得
		String key = TagUtility.getKey(item);
		// キー情報確認
		if (key.isEmpty()) {
			return null;
		}
		NodeList list = item.getChildNodes();
		int index = 0;
		int length = list.getLength();
		String itemName = "";
		int viewIndex = 0;
		int viewFlag = 0;
		while (index < length) {
			// ノード取得
			Node codeItem = list.item(index);
			// ItemName
			if (TagUtility.isTag(codeItem, TAG_ITEM_NAME)) {
				itemName = TagUtility.trimText(codeItem);
			}
			if (TagUtility.isTag(codeItem, TAG_VIEW_INDEX)) {
				// ViewIndexが有り且つ数字の場合
				try {
					viewIndex = Integer.parseInt(TagUtility.trimText(codeItem));
				} catch (NumberFormatException e) {
					return null;
				}
			}
			if (TagUtility.isTag(codeItem, TAG_VIEW_FLAG)) {
				// ViewFlagが有り且つ数字の場合
				try {
					viewFlag = Integer.parseInt(TagUtility.trimText(codeItem));
				} catch (NumberFormatException e) {
					return null;
				}
			}
			index++;
		}
		return new CodeItemProperty(key, itemName, viewIndex, viewFlag);
	}
}
