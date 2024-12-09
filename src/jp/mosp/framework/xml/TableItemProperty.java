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
import jp.mosp.framework.utils.CapsuleUtility;

/**
 * 人事汎用表示テーブル項目設定情報を格納する。
 * 人事汎用管理機能で表示する項目のこと。
 */
public class TableItemProperty implements BaseProperty {
	
	/**
	 * 人事汎用表示テーブル項目。
	 */
	private final String	key;
	
	/**
	 * 人事汎用項目キー。<br>
	 * 人事汎用項目設定(Convention)の人事汎用項目を特定する。<br>
	 */
	private String[]		itemKeys;
	
	/**
	 * 人事汎用項目名。<br>
	 * DBに登録される人事項目区分として用いられる。<br>
	 * また、RequestParameterのnameとして用いられる。<br>
	 */
	private String[]		itemNames;
	
	/**
	 * 人事汎用表示項目キー。<br>
	 * 人事汎用項目キーで得られる設定(Convention)の人事汎用項目データ型が
	 * Concatenate或いはFormatである場合に、人事汎用項目を特定するために用いられる。<br>
	 */
	private String[]		labelKeys;
	
	/**
	 * colspan(HTML)。
	 */
	private int				colspan;
	
	/**
	 * rowspan(HTML)。
	 */
	private int				rowspan;
	
	/**
	 * 必須マーク要否フラグ。
	 */
	private boolean			isRequired;
	
	
	/**
	 * 人事汎用表示テーブル項目設定情報を生成する。
	 * @param key    人事汎用表示テーブル項目
	 */
	public TableItemProperty(String key) {
		this.key = key;
		itemKeys = new String[0];
		itemNames = new String[0];
		labelKeys = new String[0];
		isRequired = false;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * 人事汎用項目キーを取得する。
	 * @return 人事汎用項目キー
	 */
	public String[] getItemKeys() {
		return itemKeys.clone();
	}
	
	/**
	 * 人事汎用項目名を取得する。
	 * @return 人事汎用項目名
	 */
	public String[] getItemNames() {
		return itemNames.clone();
	}
	
	/**
	 * @return colspan
	 */
	public int getColspan() {
		return colspan;
	}
	
	/**
	 * @return rowspan
	 */
	public int getRowspan() {
		return rowspan;
	}
	
	/**
	 * 人事汎用項目キーを設定する。
	 * @param itemKeys 人事汎用項目キー
	 */
	public void setItemKeys(String[] itemKeys) {
		this.itemKeys = itemKeys.clone();
	}
	
	/**
	 * 人事汎用項目名を設定する。
	 * @param itemNames 人事汎用項目名
	 */
	public void setItemNames(String[] itemNames) {
		this.itemNames = itemNames.clone();
	}
	
	/**
	 * @param colspan セットする colspan
	 */
	public void setColspan(int colspan) {
		this.colspan = colspan;
	}
	
	/**
	 * @param rowspan セットする rowspan
	 */
	public void setRowspan(int rowspan) {
		this.rowspan = rowspan;
	}
	
	/**
	 * @return isRequired
	 */
	public boolean isRequired() {
		return isRequired;
	}
	
	/**
	 * @param isRequired セットする isRequired
	 */
	public void setRequired(boolean isRequired) {
		this.isRequired = isRequired;
	}
	
	/**
	 * @return labelKeys
	 */
	public String[] getLabelKeys() {
		return CapsuleUtility.getStringArrayClone(labelKeys);
	}
	
	/**
	 * @param labelKeys セットする labelKeys
	 */
	public void setLabelKeys(String[] labelKeys) {
		this.labelKeys = CapsuleUtility.getStringArrayClone(labelKeys);
	}
	
}
