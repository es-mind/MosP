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

/**
 * MosP設定情報(名称)。<br>
 */
public class NamingProperty implements BaseProperty {
	
	/**
	 * キー。<br>
	 */
	private String	key;
	
	/**
	 * 値。<br>
	 */
	private String	value;
	
	
	/**
	 * コンストラクタ。<br>
	 * @param key   キー
	 * @param value 値
	 */
	public NamingProperty(String key, String value) {
		this.key = key;
		this.value = value;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * @return value
	 */
	public String getValue() {
		return value;
	}
	
}
