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

import jp.mosp.framework.property.BaseProperty;

/**
 * 人事汎用項目設定情報を格納する。
 */
public class ItemProperty implements BaseProperty {
	
	/**
	 * 人事汎用項目キー。
	 */
	private final String	key;
	
	/**
	 * 人事汎用項目形式。
	 */
	private final String	type;
	
	/**
	 * CSS。<br>
	 * 人事汎用項目のCSSを指定する。<br>
	 */
	private String			css;
	
	/**
	 * 空白指定<br>
	 * 人事汎用項目形式がselect、radioの場合に用いる。<br>
	 * デフォルトは表示
	 */
	private boolean			isNeedSpace;
	
	/**
	 * 固定値。<br>
	 * 人事汎用項目形式がlabelの場合に用いる。<br>
	 * 表示したい固定値を格納する。<br>
	 */
	private String			fixedValue;
	
	/**
	 * MosPコードキー。<br>
	 * 人事汎用項目形式がselect、labelの場合に用いる。<br>
	 * 値が設定されている場合、MosPコード設定情報から
	 * プルダウン用配列を取得する。<br>
	 */
	private String			codeKey;
	
	/**
	 * 名称区分。<br>
	 * 人事汎用項目形式がselect、labelの場合に用いる。<br>
	 * 値が設定されている場合、名称区分マスタから
	 * プルダウン用配列を取得する。<br>
	 */
	private String			namingKey;
	
	/**
	 * 人事汎用項目フォーマット。<br>
	 * 人事汎用項目形式がlabelの場合に用いる。<br>
	 * 当項目のフォーマットを指定する。<br>
	 */
	private String			format;
	
	/**
	 * 人事汎用項目データ型。<br>
	 */
	private String			dataType;
	
	
	/**
	 * 人事汎用項目設定情報を生成する。
	 * @param key  人事汎用項目キー
	 * @param type 人事汎用項目形式
	 */
	public ItemProperty(String key, String type) {
		this.key = key;
		this.type = type;
		isNeedSpace = true;
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
	 * 固定値を取得する。<br>
	 * @return 固定値
	 */
	public String getFixedValue() {
		return fixedValue;
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
	
	/**
	 * @return isNeedSpace
	 */
	public boolean isNeedSpace() {
		return isNeedSpace;
	}
	
	/**
	 * @param css セットする css
	 */
	public void setCss(String css) {
		this.css = css;
	}
	
	/**
	 * @param fixedValue セットする fixedValue
	 */
	public void setFixedValue(String fixedValue) {
		this.fixedValue = fixedValue;
	}
	
	/**
	 * @param codeKey セットする codeKey
	 */
	public void setCodeKey(String codeKey) {
		this.codeKey = codeKey;
	}
	
	/**
	 * @param namingKey セットする namingKey
	 */
	public void setNamingKey(String namingKey) {
		this.namingKey = namingKey;
	}
	
	/**
	 * @return format
	 */
	public String getFormat() {
		return format;
	}
	
	/**
	 * @param format セットする format
	 */
	public void setFormat(String format) {
		this.format = format;
	}
	
	/**
	 * @return dataType
	 */
	public String getDataType() {
		return dataType;
	}
	
	/**
	 * @param dataType セットする dataType
	 */
	public void setDataType(String dataType) {
		this.dataType = dataType;
	}
	
	/**
	 * @param isNeedSpace セットする isNeedSpace
	 */
	public void setNeedSpace(boolean isNeedSpace) {
		this.isNeedSpace = isNeedSpace;
	}
	
}
