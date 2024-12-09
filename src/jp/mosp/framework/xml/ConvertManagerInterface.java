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

/**
 * MosP設定情報変換インターフェース。<br>
 */
public interface ConvertManagerInterface {
	
	/**
	 * @return MosP設定情報変換クラス
	 */
	public ConvertResultInterface init();
	
	/**
	 * MosPで管理しない設定情報であるかを確認する。<br>
	 * @param tagName タグ名
	 * @return 確認結果(true：MosPで管理しない設定情報である、false：そうでない)
	 */
	public boolean isUnknown(String tagName);
	
	/**
	 * ノードをMosP設定情報に変換する。<br>
	 * @param properties MosP設定情報群
	 * @param wrapper    ノード
	 */
	public void convert(Map<String, BaseProperty> properties, NodeWrapper wrapper);
}
