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

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.comparator.IndexComparator;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;

/**
 * MosP設定情報を扱う。<br>
 */
public class MospProperties {
	
	/**
	 * アプリケーション設定情報群。
	 */
	private Map<String, ApplicationProperty>	applicationProperties;
	
	/**
	 * MosPコマンド設定情報群。
	 */
	private Map<String, CommandProperty>		commandProperties;
	
	/**
	 * モデル設定情報群。
	 */
	private Map<String, ModelProperty>			modelProperties;
	
	/**
	 * メッセージ設定情報群。
	 */
	private Map<String, MessageProperty>		messageProperties;
	
	/**
	 * 文言設定情報群。
	 */
	private Map<String, NamingProperty>			namingProperties;
	
	/**
	 * コード設定情報群。
	 */
	private Map<String, CodeProperty>			codeProperties;
	
	/**
	 * アドオン設定情報群。
	 */
	private Map<String, AddonProperty>			addonProperties;
	
	/**
	 * メインメニュー設定情報群。
	 */
	private Map<String, MainMenuProperty>		mainMenuProperties;
	
	/**
	 * ロール設定情報群。
	 */
	private Map<String, RoleProperty>			roleProperties;
	
	/**
	 * 人事管理項目設定情報群。
	 */
	private Map<String, ConventionProperty>		conventionProperties;
	
	/**
	 * 人事管理表示設定情報群。
	 */
	private Map<String, ViewConfigProperty>		viewConfigProperties;
	
	
	/**
	 * MosP設定情報を生成する。<br>
	 */
	public MospProperties() {
		// 処理無し
	}
	
	/**
	 * MosPコマンド設定情報を取得する。
	 * @param command コマンド
	 * @return MosPコマンド設定情報
	 */
	public CommandProperty getCommandProperty(String command) {
		// コマンドでコントローラー設定情報取得
		CommandProperty property = commandProperties.get(command);
		// コントローラー設定情報が存在する場合
		if (property != null) {
			return property;
		}
		// コマンド確認
		if (command == null || command.isEmpty()) {
			return property;
		}
		// コマンド末尾をワイルドカード化して再検索
		property = commandProperties.get(MospUtility.getWildCardCommand(command));
		// コントローラー設定情報が存在する場合
		if (property != null) {
			return property;
		}
		return property;
	}
	
	/**
	 * アプリケーション設定情報を取得する。
	 * @param key 対象キー
	 * @return 設定情報
	 */
	public String getApplicationProperty(String key) {
		ApplicationProperty property = applicationProperties.get(key);
		if (property == null) {
			return null;
		}
		return property.getValue();
	}
	
	/**
	 * アプリケーション設定情報を数値で取得する。
	 * @param key          対象キー
	 * @param defaultValue 取得できなかった場合の値
	 * @return 設定情報
	 */
	public int getApplicationProperty(String key, int defaultValue) {
		try {
			return Integer.parseInt(getApplicationProperty(key));
		} catch (NumberFormatException e) {
			return defaultValue;
		}
	}
	
	/**
	 * アプリケーション設定情報を真偽値で取得する。
	 * @param key 対象キー
	 * @return 設定情報
	 */
	public boolean getApplicationPropertyBool(String key) {
		return Boolean.parseBoolean(getApplicationProperty(key));
	}
	
	/**
	 * アプリケーション設定情報配列を取得する。<br>
	 * カンマで配列に分割する。<br>
	 * @param key 対象キー
	 * @return 設定情報配列
	 */
	public String[] getApplicationProperties(String key) {
		String property = getApplicationProperty(key);
		// プロパティ確認
		if (property == null || property.isEmpty()) {
			return new String[0];
		}
		// 改行コードとタブを消去
		String value = property.replaceAll("\r\n", "\n").replaceAll("[\n|\r|\t]", "");
		// カンマで分割
		return value.split(MospConst.APP_PROPERTY_SEPARATOR);
	}
	
	/**
	 * アプリケーション設定情報設定。
	 * @param key 対象キー
	 * @param value 対象設定情報
	 */
	public void setApplicationProperty(String key, String value) {
		applicationProperties.put(key, new ApplicationProperty(key, value));
	}
	
	/**
	 * モデル設定情報群を取得する。
	 * @return モデル設定情報群
	 */
	public Map<String, ModelProperty> getModelProperties() {
		return modelProperties;
	}
	
	/**
	 * メッセージ設定情報群を取得する。
	 * @return メッセージ設定情報群
	 */
	public Map<String, MessageProperty> getMessageProperties() {
		return messageProperties;
	}
	
	/**
	 * メニュー設定情報を取得する。
	 * @return メニュー設定情報
	 */
	public Map<String, MainMenuProperty> getMainMenuProperties() {
		return mainMenuProperties;
	}
	
	/**
	 * ロール設定情報を取得する。
	 * @return ロール設定情報
	 */
	public Map<String, RoleProperty> getRoleProperties() {
		return roleProperties;
	}
	
	/**
	 * アドオン設定情報を取得する。
	 * @return アドオン設定情報
	 */
	public Map<String, AddonProperty> getAddonProperties() {
		return addonProperties;
	}
	
	/**
	 * 人事管理項目設定情報を取得する。
	 * @return 人事管理項目設定情報
	 */
	public Map<String, ConventionProperty> getConventionProperties() {
		return conventionProperties;
	}
	
	/**
	 * 人事管理表示設定情報を取得する。
	 * @return 人事管理表示設定情報
	 */
	public Map<String, ViewConfigProperty> getViewConfigProperties() {
		return viewConfigProperties;
	}
	
	/**
	 * コード設定情報群を取得する。
	 * @return コード設定情報群
	 */
	public Map<String, CodeProperty> getCodeProperty() {
		return codeProperties;
	}
	
	/**
	 * 文言を取得する。
	 * @param key 対象キー
	 * @return 文言
	 */
	public String getName(String key) {
		NamingProperty property = namingProperties.get(key);
		if (property == null) {
			return null;
		}
		return property.getValue();
	}
	
	/**
	 * メッセージを取得する。
	 * @param key 対象キー
	 * @param rep 置換文字列
	 * @return メッセージ
	 */
	public String getMessage(String key, String[] rep) {
		MessageProperty messageProperty = messageProperties.get(key);
		if (messageProperty == null) {
			return "";
		}
		String message = messageProperty.getMessageBody();
		if (message == null) {
			return "";
		}
		if (rep == null) {
			return message;
		}
		for (int i = 0; i < rep.length; i++) {
			message = message.replaceAll("%" + String.valueOf(i + 1) + "%", rep[i]);
		}
		return message;
	}
	
	/**
	 * コード項目名を取得する。
	 * @param codeKey     コードキー
	 * @param codeItemKey コード項目キー
	 * @return コード項目名
	 */
	public String getCodeItemName(String codeKey, String codeItemKey) {
		CodeProperty codeProperty = codeProperties.get(codeKey);
		if (codeProperty == null) {
			return codeKey;
		}
		CodeItemProperty codeItemProperty = codeProperty.getCodeItemMap().get(codeItemKey);
		if (codeItemProperty == null) {
			return codeItemKey;
		}
		return codeItemProperty.getItemName();
	}
	
	/**
	 * コード配列を取得する。
	 * @param codeKey   コードキー
	 * @param needBlank 空白行要否(true：空白行要、false：空白行不要)
	 * @return コード配列
	 */
	public String[][] getCodeArray(String codeKey, boolean needBlank) {
		// コード設定情報取得
		CodeProperty codeProperty = codeProperties.get(codeKey);
		// コード設定情報確認
		if (codeProperty == null) {
			// コード設定情報無し
			return new String[0][0];
		}
		// コード項目リスト取得
		List<CodeItemProperty> list = new ArrayList<CodeItemProperty>(codeProperty.getCodeItemMap().values());
		// コード項目リストソート
		Collections.sort(list, new IndexComparator());
		// 非表示項目除去
		for (int i = list.size() - 1; i >= 0; i--) {
			CodeItemProperty codeItemProperty = list.get(i);
			if (codeItemProperty.getViewFlag() == MospConst.VIEW_OFF) {
				list.remove(i);
			}
		}
		// 配列及びインデックス宣言
		String[][] array;
		int idx = 0;
		// 空白行設定
		if (needBlank) {
			array = new String[list.size() + 1][2];
			array[0][0] = "";
			array[0][1] = "";
			idx++;
		} else {
			array = new String[list.size()][2];
		}
		// 配列作成
		for (CodeItemProperty codeItemProperty : list) {
			array[idx][0] = codeItemProperty.getKey();
			array[idx][1] = codeItemProperty.getItemName();
			idx++;
		}
		return array;
	}
	
	/**
	 * アプリケーション設定情報群を設定する。
	 * @param applicationProperties アプリケーション設定情報群
	 */
	public void setApplicationProperties(Map<String, ApplicationProperty> applicationProperties) {
		this.applicationProperties = applicationProperties;
	}
	
	/**
	 * コントローラー設定情報群を設定する。
	 * @param commandProperties コントローラー設定情報群
	 */
	public void setCommandProperties(Map<String, CommandProperty> commandProperties) {
		this.commandProperties = commandProperties;
	}
	
	/**
	 * モデル設定情報群を設定する。
	 * @param modelProperties モデル設定情報群
	 */
	public void setModelProperties(Map<String, ModelProperty> modelProperties) {
		this.modelProperties = modelProperties;
	}
	
	/**
	 * メッセージ設定情報群を設定する。
	 * @param messageProperties メッセージ設定情報群
	 */
	public void setMessageProperties(Map<String, MessageProperty> messageProperties) {
		this.messageProperties = messageProperties;
	}
	
	/**
	 * 文言設定情報群を設定する。
	 * @param namingProperties 文言設定情報群
	 */
	public void setNamingProperties(Map<String, NamingProperty> namingProperties) {
		this.namingProperties = namingProperties;
	}
	
	/**
	 * コード設定情報群文言設定情報群を設定する。
	 * @param codeProperties コード設定情報群
	 */
	public void setCodeProperties(Map<String, CodeProperty> codeProperties) {
		this.codeProperties = codeProperties;
	}
	
	/**
	 * アドオン設定情報群を設定する。
	 * @param addonProperties アドオン設定情報群
	 */
	public void setAddonProperties(Map<String, AddonProperty> addonProperties) {
		this.addonProperties = addonProperties;
	}
	
	/**
	 * メインメニュー設定情報群を設定する。
	 * @param mainMenuProperties メインメニュー設定情報群
	 */
	public void setMainMenuProperties(Map<String, MainMenuProperty> mainMenuProperties) {
		this.mainMenuProperties = mainMenuProperties;
	}
	
	/**
	 * 人事管理項目設定情報群を設定する。
	 * @param conventionProperties 人事管理項目設定情報群
	 */
	public void setConventionProperties(Map<String, ConventionProperty> conventionProperties) {
		this.conventionProperties = conventionProperties;
	}
	
	/**
	 * 人事管理表示設定情報群を設定する。
	 * @param viewConfigProperties 人事管理表示設定情報群
	 */
	public void setViewConfigProperties(Map<String, ViewConfigProperty> viewConfigProperties) {
		this.viewConfigProperties = viewConfigProperties;
	}
	
	/**
	 * ロール設定情報群を設定する。
	 * @param roleProperties ロール設定情報群
	 */
	public void setRoleProperties(Map<String, RoleProperty> roleProperties) {
		this.roleProperties = roleProperties;
	}
	
}
