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
import jp.mosp.framework.property.MessageProperty;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * MosP設定情報(メッセージ)を作成する。<br>
 *
 */
public class MessageTagConverter implements TagConverterInterface {
	
	/**
	 * メッセージ要素の下位要素名(メッセージ本体)。
	 */
	private static final String	TAG_MESSAGE_BODY		= "MessageBody";
	
	/**
	 * メッセージ要素の下位要素名(クライアント利用可否)。
	 */
	private static final String	TAG_CLIENT_AVAILABLE	= "ClientAvailable";
	
	
	@Override
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		// Message
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
		// メッセージ(メッセージ設定情報)準備
		String messageBody = null;
		// クライアント利用可否(メッセージ設定情報)準備(default:false)
		boolean clientAvailable = false;
		
		NodeList list = node.getChildNodes();
		int itemIndex = 0;
		int length = list.getLength();
		while (itemIndex < length) {
			// ノード取得
			Node item = list.item(itemIndex);
			// メッセージ(メッセージ設定情報)
			if (TagUtility.isTag(item, TAG_MESSAGE_BODY)) {
				messageBody = TagUtility.trimText(item);
			}
			// クライアント利用可否(メッセージ設定情報)準備
			if (TagUtility.isTag(item, TAG_CLIENT_AVAILABLE)) {
				clientAvailable = Boolean.parseBoolean(TagUtility.trimText(item));
			}
			itemIndex++;
		}
		// メッセージ設定情報追加確認
		if (messageBody == null) {
			// エラーログ出力
			TagUtility.invalidMassage(path, node);
			return;
		}
		// メッセージ設定情報追加
		properties.put(key, new MessageProperty(key, messageBody, clientAvailable));
	}
	
}
