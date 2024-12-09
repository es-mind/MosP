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
package jp.mosp.framework.utils;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.property.MenuProperty;
import net.arnx.jsonic.JSON;

/**
 * メニュー用JS文字列を取得するユーティリティクラス。<br>
 */
public class MenuJsUtility {
	
	/**
	 * JavaScript変数名(メニュー配列)。<br>
	 */
	protected static final String	VAR_ARY_MENU	= "ARY_MENU = ";
	
	/**
	 * デリミタ文字列(JavaScript)。<br>
	 */
	protected static final String	DEL_JAVA_SCRIPT	= ";";
	
	
	/**
	 * メニュー用JS文字列を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return メニュー用JS文字列
	 */
	public static String getMenuJs(MospParams mospParams) {
		// メニュー用JS文字列リストを準備
		List<List<Object>> list = new ArrayList<List<Object>>();
		// サブメニューリスト群(キー：メインメニューキー)(インデックス昇順)を取得
		Map<String, List<String[]>> menuLists = getMenuLists(mospParams);
		// サブメニューリスト毎に処理
		for (Entry<String, List<String[]>> entry : menuLists.entrySet()) {
			// メインメニュー情報(メインメニューキー：メインメニュー名：メニューリスト)を準備
			List<Object> mainMenu = new ArrayList<Object>();
			// メインメニューキーを取得
			String mainMenuKey = entry.getKey();
			// メインメニュー情報(メインメニューキー：メインメニュー名：メニューリスト)に追加
			mainMenu.add(mainMenuKey);
			mainMenu.add(NameUtility.getName(mospParams, mainMenuKey));
			mainMenu.add(entry.getValue());
			// メニュー用JS文字列リストにメインメニュー情報を追加
			list.add(mainMenu);
		}
		// メニュー用JS文字列リストを取得
		return new StringBuilder(VAR_ARY_MENU).append(JSON.escapeScript(list)).append(DEL_JAVA_SCRIPT).toString();
	}
	
	/**
	 * サブメニューリスト群(キー：メインメニューキー)(インデックス昇順)を取得する。<br>
	 * <br>
	 * 値のサブメニューリストは、次の値を持つ。<br>
	 * ・メニューキー<br>
	 * ・コマンド<br>
	 * ・メニュー名<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @return サブメニューリスト群(キー：メインメニューキー)(インデックス昇順)
	 */
	protected static Map<String, List<String[]>> getMenuLists(MospParams mospParams) {
		// サブメニューリスト群(キー：メインメニューキー)(インデックス昇順)を準備
		Map<String, List<String[]>> map = new LinkedHashMap<String, List<String[]>>();
		// ログインユーザのメニューキー群(インデックス昇順)を取得
		Set<String> menuKeys = RoleUtility.getUserMenuKeys(mospParams);
		// メニューキー毎に処理
		for (String menuKey : menuKeys) {
			// メニュー設定情報を取得
			MenuProperty menu = MenuUtility.getMenuProperty(mospParams, menuKey);
			// メニューが有効でない場合
			if (menu.isMenuValid() == false) {
				// 処理無し
				continue;
			}
			// ロールメニューからメインメニューキーを取得
			String mainMenuKey = MenuUtility.getMainMenuKey(mospParams, menuKey);
			// サブメニューリスト群からサブメニューリストを取得
			List<String[]> subMenuList = map.get(mainMenuKey);
			// サブメニューリストが取得できなかった場合
			if (subMenuList == null) {
				// サブメニューリストを作成
				subMenuList = new ArrayList<String[]>();
				// サブメニューリスト群に設定
				map.put(mainMenuKey, subMenuList);
			}
			// サブメニューリストにメニュー情報を追加
			subMenuList.add(new String[]{ menu.getKey(), menu.getCommand(),
				NameUtility.getName(mospParams, menu.getVoClass()) });
		}
		// サブメニューリスト群(キー：メインメニューキー)(インデックス昇順)を取得
		return map;
	}
	
	/**
	 * 選択メニュー(大項目)を取得する。<br>
	 * 画面遷移時に選択しているメニュー(大項目)の配列名を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 選択メニュー
	 */
	public static String getSelectMenu(MospParams mospParams) {
		// メインメニューキー取得
		String mainMenuKey = TopicPathUtility.getMainMenuKey(mospParams);
		// メインメニューキー確認
		if (mainMenuKey != null && mainMenuKey.isEmpty() == false) {
			// 選択メニュー(大項目)取得
			return mainMenuKey;
		}
		// 最初のメインメニューキーを取得
		return MenuUtility.getUserFirstMainMenu(mospParams);
	}
	
}
