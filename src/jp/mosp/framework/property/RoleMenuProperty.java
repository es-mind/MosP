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
package jp.mosp.framework.property;

import java.util.Map;

import jp.mosp.framework.base.IndexedDtoInterface;

/**
 * MosPロールメニュー設定情報を扱う。
 */
public class RoleMenuProperty implements IndexedDtoInterface {
	
	/**
	 * キー。
	 */
	private String						key;
	
	/**
	 * インデックス。
	 */
	private Integer						index;
	
	/**
	 * 範囲設定情報群。
	 */
	private Map<String, RangeProperty>	rangeMap;
	
	
	/**
	 * MosPロールメニュー情報を生成する。
	 * @param key      キー
	 * @param index    インデックス
	 * @param rangeMap 範囲設定情報群
	 */
	public RoleMenuProperty(String key, Integer index, Map<String, RangeProperty> rangeMap) {
		this.key = key;
		this.index = index;
		this.rangeMap = rangeMap;
	}
	
	/**
	 * キーを取得する。
	 * @return キー
	 */
	public String getKey() {
		return key;
	}
	
	@Override
	public int getIndex() {
		return index == null ? 0 : index.intValue();
	}
	
	/**
	 * 範囲設定情報群を取得する。
	 * @return 範囲設定情報群
	 */
	public Map<String, RangeProperty> getRangeMap() {
		return rangeMap;
	}
	
}
