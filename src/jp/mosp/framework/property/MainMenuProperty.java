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
package jp.mosp.framework.property;

import java.util.HashMap;
import java.util.Map;

/**
 * MosP設定情報(メインメニュー)。<br>
 */
public class MainMenuProperty implements BaseProperty {
	
	/**
	 * キー。<br>
	 */
	private String						key;
	
	/**
	 * メニュー設定情報群。<br>
	 */
	private Map<String, MenuProperty>	menuMap;
	
	
	/**
	 * MosPメインメニュー情報を生成する。<br>
	 * @param key キー
	 */
	public MainMenuProperty(String key) {
		this.key = key;
		menuMap = new HashMap<String, MenuProperty>();
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * メニュー設定情報群を取得する。<br>
	 * @return メニュー設定情報群
	 */
	public Map<String, MenuProperty> getMenuMap() {
		return menuMap;
	}
	
}
