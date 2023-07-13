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

import java.util.Map;

import jp.mosp.framework.property.AddonProperty;
import jp.mosp.framework.property.ApplicationProperty;
import jp.mosp.framework.property.BaseProperty;
import jp.mosp.framework.property.CodeProperty;
import jp.mosp.framework.property.CommandProperty;
import jp.mosp.framework.property.ConventionProperty;
import jp.mosp.framework.property.MainMenuProperty;
import jp.mosp.framework.property.MessageProperty;
import jp.mosp.framework.property.ModelProperty;
import jp.mosp.framework.property.NamingProperty;
import jp.mosp.framework.property.RoleProperty;
import jp.mosp.framework.property.ViewConfigProperty;

/**
 * MosP設定情報変換結果インターフェース。<br>
 */
interface ConvertResultInterface {
	
	/**
	 * MosP設定情報を取得する。<br>
	 * @param key キー
	 * @return MosP設定情報
	 */
	public <T extends BaseProperty> Map<String, T> get(String key);
	
	/**
	 * 人事汎用項目区分設定情報を取得する。<br>
	 * @return 人事汎用項目区分設定情報
	 */
	public Map<String, ConventionProperty> getConvention();
	
	/**
	 * MosP設定情報(ロール)を取得する。<br>
	 * @return MosP設定情報(ロール)
	 */
	public Map<String, RoleProperty> getRole();
	
	/**
	 * MosP設定情報(メインメニュー)を取得する。<br>
	 * @return MosP設定情報(メインメニュー)
	 */
	public Map<String, MainMenuProperty> getMainMenu();
	
	/**
	 * MosP設定情報(アドオン)を取得する。<br>
	 * @return MosP設定情報(アドオン)
	 */
	public Map<String, AddonProperty> getAddon();
	
	/**
	 * MosP設定情報(コード)を取得する。<br>
	 * @return MosP設定情報(コード)
	 */
	public Map<String, CodeProperty> getCode();
	
	/**
	 * MosP設定情報(名称)を取得する。<br>
	 * @return MosP設定情報(名称)
	 */
	public Map<String, NamingProperty> getNaming();
	
	/**
	 * MosP設定情報(メッセージ)を取得する。<br>
	 * @return MosP設定情報(メッセージ)
	 */
	public Map<String, MessageProperty> getMessage();
	
	/**
	 * MosP設定情報(コマンド)を取得する。<br>
	 * @return MosP設定情報(コマンド)
	 */
	public Map<String, CommandProperty> getController();
	
	/**
	 * MosP設定情報(アプリケーション)を取得する。<br>
	 * @return MosP設定情報(アプリケーション)
	 */
	public Map<String, ApplicationProperty> getApplication();
	
	/**
	 * 人事汎用管理区分設定情報を取得する。<br>
	 * @return 人事汎用管理区分設定情報を
	 */
	public Map<String, ViewConfigProperty> getViewConfig();
	
	/**
	 * MosP設定情報(モデル)を取得する。<br>
	 * @return MosP設定情報(モデル)
	 */
	public Map<String, ModelProperty> getModel();
	
}
