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

import java.util.List;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.base.TopicPath;

/**
 * パンくずリストの操作に有用なメソッドを提供する。<br><br>
 */
public class TopicPathUtility {
	
	/**
	 * メインメニュータイトル接尾辞(文言キー)。<br>
	 */
	public static final String NMA_SUFFIX_MAIN_MENU_TITLE = "Hyphen";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private TopicPathUtility() {
		// 処理無し
	}
	
	/**
	 * IDで表されるパンくずのパンくずリストにおけるインデックスを取得する。<br>
	 * パンくずリストに存在しない場合は-1を返す。<br>
	 * @param list パンくずリスト
	 * @param id   パンくずID(VOのクラス名)
	 * @return パンくずリストにおけるインデックス
	 */
	public static int getTopicPathIndex(List<TopicPath> list, String id) {
		// パンくずリスト確認(操作対象のパンくずが存在するか)
		for (int i = 0; i < list.size(); i++) {
			// パンくずIDを比較
			if (id.equals(list.get(i).getId())) {
				return i;
			}
		}
		return -1;
	}
	
	/**
	 * パンくずリストからメニューキーを取得する。<br>
	 * メニューキーは、メニューからの遷移時に設定される。<br>
	 * @param list パンくずリスト
	 * @return メニューキー
	 */
	public static String getMenuKey(List<TopicPath> list) {
		// パンくずリスト確認
		for (TopicPath topicPath : list) {
			// メニューキー存在確認
			if (topicPath.getMenuKey() != null && topicPath.getMenuKey().isEmpty() == false) {
				return topicPath.getMenuKey();
			}
		}
		// メニューキーが見つからなかった場合
		return "";
	}
	
	/**
	 * パンくず表示名称を設定する。<br>
	 * @param mospParams MosP処理情報
	 * @param id         パンくずID(VOのクラス名)
	 * @param name       設定するパンくず表示名称
	 */
	public static void setTopicPathName(MospParams mospParams, String id, String name) {
		// パンくずリスト取得
		List<TopicPath> topicPathList = mospParams.getTopicPathList();
		// 操作対象のパンくずリストにおけるインデックスを取得
		int idx = TopicPathUtility.getTopicPathIndex(topicPathList, id);
		// インデックス確認
		if (idx >= 0) {
			// パンくず操作対象の名称を上書き
			topicPathList.get(idx).setName(name);
		}
	}
	
	/**
	 * パンくずリストからメインメニューキーを取得する。<br>
	 * メインメニューからパンくずが進行しているのかを判断するのに用いることができる。<br>
	 * @param mospParams MosP処理情報
	 * @return メインメニューキー
	 */
	public static String getMainMenuKey(MospParams mospParams) {
		return MenuUtility.getMainMenuKey(mospParams, getMenuKey(mospParams.getTopicPathList()));
	}
	
	/**
	 * パンくず用画面タイトルを取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param topicPath  パンくず
	 * @return パンくず用画面タイトル
	 */
	public static String getTopicPathTitle(MospParams mospParams, TopicPath topicPath) {
		// 名称取得準備
		StringBuffer sb = new StringBuffer();
		// メニューキー取得及び確認
		if (topicPath.getMenuKey() != null && topicPath.getMenuKey().isEmpty() == false) {
			// メインメニューキー取得
			String key = MenuUtility.getMainMenuKey(mospParams, topicPath.getMenuKey());
			// メインメニューのタイトルを付加
			sb.append(NameUtility.getName(mospParams, key));
			sb.append(NameUtility.getName(mospParams, NMA_SUFFIX_MAIN_MENU_TITLE));
		}
		sb.append(topicPath.getName());
		return sb.toString();
	}
}
