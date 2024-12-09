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

import jp.mosp.framework.base.IndexedDtoInterface;

/**
 * MosP設定情報(コード項目)。<br>
 */
public class CodeItemProperty implements IndexedDtoInterface {
	
	/**
	 * キー。
	 */
	private String	key;
	
	/**
	 * コード項目名称。
	 */
	private String	itemName;
	
	/**
	 * コード項目表示順。
	 */
	private int		viewIndex;
	
	/**
	 * コード項目表示フラグ。
	 */
	private int		viewFlag;
	
	
	/**
	 * MosPコード項目設定情報を生成する。
	 * @param key       キー
	 * @param itemName  コード項目名称
	 * @param viewIndex コード項目表示順
	 * @param viewFlag  コード項目表示フラグ
	 */
	public CodeItemProperty(String key, String itemName, int viewIndex, int viewFlag) {
		this.key = key;
		this.itemName = itemName;
		this.viewIndex = viewIndex;
		this.viewFlag = viewFlag;
	}
	
	/**
	 * キーを取得する。
	 * @return キー
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * コード項目を取得する。
	 * @return コード項目
	 */
	public String getItemName() {
		return itemName;
	}
	
	/**
	 * コード項目表示順を取得する。
	 * @return コード項目表示順
	 */
	@Override
	public int getIndex() {
		return viewIndex;
	}
	
	/**
	 * コード項目表示フラグを取得する。
	 * @return コード項目表示フラグ
	 */
	public int getViewFlag() {
		return viewFlag;
	}
	
}
