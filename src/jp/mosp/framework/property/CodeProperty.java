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

import java.util.HashMap;
import java.util.Map;

/**
 * MosP設定情報(コード)。<br>
 */
public class CodeProperty implements BaseProperty {
	
	/**
	 * キー。
	 */
	private String							key;
	
	/**
	 * コード項目設定情報群。
	 */
	private Map<String, CodeItemProperty>	codeItemMap;
	
	
	/**
	 * MosPコード設定情報を生成する。
	 * @param key キー
	 */
	public CodeProperty(String key) {
		this.key = key;
		codeItemMap = new HashMap<String, CodeItemProperty>();
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * コード項目設定情報群を取得する。
	 * @return コード項目設定情報群
	 */
	public Map<String, CodeItemProperty> getCodeItemMap() {
		return codeItemMap;
	}
	
}
