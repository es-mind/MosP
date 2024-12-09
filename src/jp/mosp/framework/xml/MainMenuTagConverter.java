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

import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.property.MainMenuProperty;
import jp.mosp.framework.property.MenuProperty;

import org.w3c.dom.Node;
import org.w3c.dom.NodeList;

/**
 * MosP設定情報(アプリケーション)を作成する。<br>
 */
public class MainMenuTagConverter implements TagConverterInterface {
	
	/**
	 * メインメニュー要素の下位要素名(メニュー要素)。
	 */
	private static final String	TAG_MENU		= "Menu";
	
	/**
	 * メニュー要素の下位要素名(コマンド)。
	 */
	private static final String	TAG_COMMAND		= "Command";
	
	/**
	 * メニュー要素の下位要素名(VOクラス)。
	 */
	private static final String	TAG_VO_CLASS	= "VoClass";
	
	/**
	 * メニュー要素の下位要素名(メニュー有効フラグ)。
	 */
	private static final String	TAG_MENU_VALID	= "MenuValid";
	
	
	@Override
	public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		// MainMenu
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
		NodeList menuList = TagUtility.getElements(TAG_MENU, node);
		if (menuList.getLength() == 0) {
			// エラーログ出力
			TagUtility.invalidMassage(path, node);
			return;
		}
		// メインメニュー設定情報取得
		BaseProperty baseProperty = properties.get(key);
		// メインメニュー設定情報確認
		if (baseProperty == null) {
			// メインメニュー設定情報追加
			baseProperty = new MainMenuProperty(key);
		}
		MainMenuProperty property = (MainMenuProperty)baseProperty;
		// メニュー設定情報群取得
		Map<String, MenuProperty> menuMap = property.getMenuMap();
		
		// メニュー項目設定情報追加
		int itemIndex = 0;
		int length = menuList.getLength();
		while (itemIndex < length) {
			Node item = menuList.item(itemIndex);
			MenuProperty menu = toMenuProperty(item);
			if (menu == null) {
				// エラーログ出力
				TagUtility.invalidItemMassage(path, node, TAG_MENU, itemIndex);
			} else {
				menuMap.put(menu.getKey(), menu);
			}
			itemIndex++;
		}
		// メニュー設定情報追加
		properties.put(key, property);
	}
	
	/**
	 * MosP設定情報(メニュー)を取得する。<br>
	 * @param item ノード
	 * @return MosP設定情報(メニュー)
	 */
	protected MenuProperty toMenuProperty(Node item) {
		// キー情報取得
		String key = TagUtility.getKey(item);
		// キー情報確認
		if (key.isEmpty()) {
			return null;
		}
		// 情報取得準備
		String command = null;
		String voClass = null;
		// メニュー有効フラグ準備(デフォルト：有効)
		boolean menuValid = true;
		
		NodeList list = item.getChildNodes();
		int index = 0;
		int length = list.getLength();
		while (index < length) {
			// ノード取得
			Node menuItem = list.item(index);
			// コマンド取得
			if (TagUtility.isTag(menuItem, TAG_COMMAND)) {
				command = TagUtility.trimText(menuItem);
			}
			// VOクラス取得
			if (TagUtility.isTag(menuItem, TAG_VO_CLASS)) {
				voClass = TagUtility.trimText(menuItem);
			}
			// アドオン有効フラグ取得
			if (TagUtility.isTag(menuItem, TAG_MENU_VALID)) {
				menuValid = Boolean.parseBoolean(TagUtility.trimText(menuItem));
			}
			index++;
		}
		
		// メニュー設定情報確認
		if (menuValid && (command == null || voClass == null)) {
			return null;
		}
		return new MenuProperty(key, command, voClass, menuValid);
	}
	
}
