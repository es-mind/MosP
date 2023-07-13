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
 * MosP設定情報(アドオン)。<br>
 */
public class AddonProperty implements BaseProperty {
	
	/**
	 * キー。
	 */
	private String	key;
	
	/**
	 * アドオン名称。
	 */
	private String	addonName;
	
	/**
	 * アドオン有効フラグ。
	 */
	private boolean	addonValid;
	
	
	/**
	 * MosPアドオン情報を生成する。
	 * @param key        キー
	 * @param addonName  アドオン名称
	 * @param addonValid アドオン有効フラグ
	 */
	public AddonProperty(String key, String addonName, boolean addonValid) {
		this.key = key;
		this.addonName = addonName;
		this.addonValid = addonValid;
	}
	
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * アドオン名称を取得する。
	 * @return アドオン名称
	 */
	public String getAddonName() {
		return addonName;
	}
	
	/**
	 * アドオン有効フラグを取得する。
	 * @return アドオン有効フラグ
	 */
	public boolean isAddonValid() {
		return addonValid;
	}
	
}
