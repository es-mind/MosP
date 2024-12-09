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

import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.property.MainMenuProperty;
import jp.mosp.framework.property.MenuProperty;

/**
 * メニューの操作に有用なメソッドを提供する。<br><br>
 */
public class MenuUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MenuUtility() {
		// 処理無し
	}
	
	/**
	 * メニューキーからメインメニュー設定情報を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param menuKey    メニューキー
	 * @return メインメニュー設定情報
	 */
	public static MainMenuProperty getMainMenu(MospParams mospParams, String menuKey) {
		// メインメニュー情報群を取得
		Map<String, MainMenuProperty> mainMenuProperties = mospParams.getProperties().getMainMenuProperties();
		// メインメニュー情報毎に内容を確認
		for (Entry<String, MainMenuProperty> mainMenu : mainMenuProperties.entrySet()) {
			// メインメニュー情報を取得
			MainMenuProperty mainMenuProperty = mainMenu.getValue();
			// メインメニュー内メニュー情報確認
			for (Entry<String, MenuProperty> menu : mainMenuProperty.getMenuMap().entrySet()) {
				// メニューキー比較
				if (menuKey.equals(menu.getKey())) {
					// メインメニュー設定情報を取得
					return mainMenuProperty;
				}
			}
		}
		return null;
	}
	
	/**
	 * メニュー設定情報を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param menuKey    メニューキー
	 * @return メニュー設定情報
	 */
	public static MenuProperty getMenuProperty(MospParams mospParams, String menuKey) {
		// メインメニュー情報群を取得
		Map<String, MainMenuProperty> mainMenuProperties = mospParams.getProperties().getMainMenuProperties();
		// メインメニュー情報毎に内容を確認
		for (Entry<String, MainMenuProperty> mainMenu : mainMenuProperties.entrySet()) {
			// メインメニュー情報を取得
			MainMenuProperty mainMenuProperty = mainMenu.getValue();
			// メインメニュー内メニュー情報確認
			for (Entry<String, MenuProperty> menu : mainMenuProperty.getMenuMap().entrySet()) {
				// メニュー情報を取得
				MenuProperty menuProperty = menu.getValue();
				// メニューキー比較
				if (menuKey.equals(menuProperty.getKey())) {
					// メニュー設定情報を取得
					return menuProperty;
				}
			}
		}
		return null;
	}
	
	/**
	 * メニューキーからメインメニューキーを取得する。<br>
	 * メインメニューからパンくずが進行しているのかを判断するのに用いることができる。<br>
	 * @param mospParams MosP処理情報
	 * @param menuKey    メニューキー
	 * @return メインメニューキー
	 */
	public static String getMainMenuKey(MospParams mospParams, String menuKey) {
		// メインメニュー設定情報を取得
		MainMenuProperty mainMenuProperty = getMainMenu(mospParams, menuKey);
		// メインメニュー設定情報確認
		if (mainMenuProperty == null) {
			return "";
		}
		// メインメニューキーを取得
		return mainMenuProperty.getKey();
	}
	
	/**
	 * 対象メニューキーのメニューが有効であるかを確認する。<br>
	 * メインメニューについては考慮しない。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 * @param menuKey    メニューキー
	 * @return 確認結果(true：有効、false：無効)
	 */
	public static boolean isTheMenuValid(MospParams mospParams, String menuKey) {
		// 対象メニューキーからメニュー設定情報を取得
		MenuProperty menuProperty = getMenuProperty(mospParams, menuKey);
		// メニュー設定情報が存在しない場合
		if (menuProperty == null) {
			// 無効であると判断
			return false;
		}
		// メニュー有効フラグを確認
		return menuProperty.isMenuValid();
	}
	
	/**
	 * 対象メニューキーのメニューが有効であるかを確認する。<br>
	 * MosP処理情報中のメインメニュー設定情報群にあるメニュー設定で、判断する。<br>
	 * <br>
	 * @param mospParams  MosP処理情報
	 * @param mainMenuKey メインメニューキー
	 * @param menuKey     メニューキー
	 * @return 確認結果(true：有効、false：無効)
	 */
	public static boolean isTheMenuValid(MospParams mospParams, String mainMenuKey, String menuKey) {
		// メインメニューキーからメインメニュー情報を取得
		MainMenuProperty mainMenu = mospParams.getProperties().getMainMenuProperties().get(mainMenuKey);
		// メインメニュー情報が取得できなかった場合
		if (mainMenu == null) {
			// 無効であると判断
			return false;
		}
		// メインメニュー情報からメニュー情報を取得
		MenuProperty menu = mainMenu.getMenuMap().get(menuKey);
		// メインメニュー情報からメニュー情報を取得できなかった場合
		if (menu == null) {
			// 無効であると判断
			return false;
		}
		// メニュー有効フラグを確認
		return menu.isMenuValid();
	}
	
	/**
	 * 対象メニューキーのメニューが利用可能であるかを確認する。<br>
	 * 対象メニューが有効であり、
	 * ログインユーザが対象メニューを利用できるかで、判断する。<br>
	 * <br>
	 * @param mospParams  MosP処理情報
	 * @param mainMenuKey メインメニューキー
	 * @param menuKey     メニューキー
	 * @return 確認結果(true：利用可能、false：利用不可)
	 */
	public static boolean isTheMenuAvailable(MospParams mospParams, String mainMenuKey, String menuKey) {
		// 対象メニューキーのメニューが有効でない場合
		if (isTheMenuValid(mospParams, mainMenuKey, menuKey) == false) {
			// 利用不可であると判断
			return false;
		}
		// ログインユーザのメニューキー群(インデックス昇順)を取得
		Set<String> menuKeys = RoleUtility.getUserMenuKeys(mospParams);
		// 対象メニューキーがログインユーザのメニューキー群に含まれるかを確認
		return menuKeys.contains(menuKey);
	}
	
	/**
	 * ログインユーザのメニューとして設定されている最初のメインメニューキーを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return 最初のメインメニューキー
	 */
	public static String getUserFirstMainMenu(MospParams mospParams) {
		// ログインユーザのメニューキー群(インデックス昇順)を取得
		Set<String> menuKeys = RoleUtility.getUserMenuKeys(mospParams);
		// メニューキー毎に処理
		for (String menuKey : menuKeys) {
			// メニューが無効である場合
			if (isTheMenuValid(mospParams, menuKey) == false) {
				// 処理無し
				continue;
			}
			// メニューキーからメインメニュー設定情報を取得
			MainMenuProperty mainMenu = getMainMenu(mospParams, menuKey);
			// メインメニューが取得できなかった場合
			if (mainMenu == null) {
				// 処理無し
				continue;
			}
			// 最初に見つかったメインメニューのキーを取得
			return mainMenu.getKey();
		}
		// 空文字を取得(メインメニューを取得できなかった場合)
		return "";
	}
	
}
