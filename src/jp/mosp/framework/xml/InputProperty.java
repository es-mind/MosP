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

import jp.mosp.framework.property.BaseProperty;

/**
 * 人事汎用入力形式設定情報を格納する。<br>
 */
public class InputProperty implements BaseProperty {
	
	/**
	 * 人事汎用入力形式。<br>
	 */
	private final String	key;
	
	/**
	 * 区分。<br>
	 */
	private final String	type;
	
	/**
	 * 項目長。<br>
	 */
	private final int		maxlength;
	
	/**
	 * CSS。<br>
	 * 入力域のCSSを指定する。<br>
	 */
	private final String	css;
	
	/**
	 * MosPコードキー。<br>
	 * 値が設定されている場合、MosPコード設定情報から
	 * プルダウン用配列を取得する。<br>
	 */
	private final String	codeKey;
	
	/**
	 * 名称区分。<br>
	 * 値が設定されている場合、名称区分マスタから
	 * プルダウン用配列を取得する。<br>
	 */
	private final String	namingKey;
	
	
	/**
	 * 人事汎用入力形式設定情報を生成する。
	 * @param key 人事汎用入力形式
	 * @param type 区分
	 */
	public InputProperty(String key, String type) {
		this.key = key;
		this.type = type;
		maxlength = -1;
		css = null;
		codeKey = null;
		namingKey = null;
	}
	
	/**
	 * 
	 * @param key 人事汎用入力形式
	 * @param type 区分
	 * @param maxlength 項目長
	 * @param css       CSS
	 * @param codeKey   MosPコードキー
	 * @param namingKey 名称区分
	 */
	public InputProperty(String key, String type, int maxlength, String css, String codeKey, String namingKey) {
		this.key = key;
		this.type = type;
		this.maxlength = maxlength;
		this.css = css;
		this.codeKey = codeKey;
		this.namingKey = namingKey;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * @return type
	 */
	public String getType() {
		return type;
	}
	
	/**
	 * @return maxlength
	 */
	public int getMaxlength() {
		return maxlength;
	}
	
	/**
	 * CSSを取得する。<br>
	 * @return CSS
	 */
	public String getCss() {
		return css;
	}
	
	/**
	 * @return codeKey
	 */
	public String getCodeKey() {
		return codeKey;
	}
	
	/**
	 * @return namingKey
	 */
	public String getNamingKey() {
		return namingKey;
	}
	
}
