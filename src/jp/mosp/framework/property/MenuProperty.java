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

/**
 * MosP設定情報(メニュー)。<br>
 */
public class MenuProperty {
	
	/**
	 * キー
	 */
	private String	key;
	
	/**
	 * コマンド
	 */
	private String	command;
	
	/**
	 * VOクラス
	 */
	private String	voClass;
	
	/**
	 * メニュー有効フラグ。
	 */
	private boolean	menuValid;
	
	
	/**
	 * MosPメニュー情報を生成する。<br>
	 * @param key       キー
	 * @param command   コマンド
	 * @param voClass   VOクラス
	 * @param menuValid メニュー有効フラグ
	 */
	public MenuProperty(String key, String command, String voClass, boolean menuValid) {
		this.key = key;
		this.command = command;
		this.voClass = voClass;
		this.menuValid = menuValid;
	}
	
	/**
	 * キーを取得する。
	 * @return キー
	 */
	public String getKey() {
		return key;
	}
	
	/**
	 * コマンドを取得する。
	 * @return コマンド
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * VOクラスを取得する。
	 * @return VOクラス
	 */
	public String getVoClass() {
		return voClass;
	}
	
	/**
	 * メニュー有効フラグを取得する。
	 * @return メニュー有効フラグ
	 */
	public boolean isMenuValid() {
		return menuValid;
	}
	
}
