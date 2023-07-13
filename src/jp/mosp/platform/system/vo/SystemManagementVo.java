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
package jp.mosp.platform.system.vo;

import java.util.Map;
import java.util.Set;

import jp.mosp.platform.system.base.PlatformSystemVo;

/**
 * システム管理画面の情報を保持する。<br>
 */
public class SystemManagementVo extends PlatformSystemVo {
	
	private static final long	serialVersionUID	= 1431376100719402641L;
	
	/**
	 * アプリケーション設定値群(キー：アプリケーション設定キー)。<br>
	 */
	private Map<String, String>	appProperties;
	
	/**
	 * アプリケーション設定名称群(キー：アプリケーション設定キー)。<br>
	 */
	private Map<String, String>	appPropertyNames;
	
	/**
	 * アプリケーション設定型群(キー：アプリケーション設定キー)。<br>
	 */
	private Map<String, String>	appPropertyTypes;
	
	/**
	 * アプリケーション設定説明群(キー：アプリケーション設定キー)。<br>
	 */
	private Map<String, String>	appPropertyDescriptions;
	
	
	/**
	 * アプリケーション設定キー群を取得する。<br>
	 * アプリケーション設定キーは、リクエストパラメータとしても使われる。<br>
	 * @return アプリケーション設定キー群
	 */
	public Set<String> getAppKeys() {
		return appPropertyNames.keySet();
	}
	
	/**
	 * @return appProperties
	 */
	public Map<String, String> getAppProperties() {
		return appProperties;
	}
	
	/**
	 * @param appKey アプリケーション設定キー
	 * @return アプリケーション設定値
	 */
	public String getAppProperty(String appKey) {
		return appProperties.get(appKey);
	}
	
	/**
	 * @param appProperties セットする appProperties
	 */
	public void setAppProperties(Map<String, String> appProperties) {
		this.appProperties = appProperties;
	}
	
	/**
	 * @param appKey アプリケーション設定キー
	 * @return アプリケーション設定名称
	 */
	public String getAppPropertyName(String appKey) {
		return appPropertyNames.get(appKey);
	}
	
	/**
	 * @param appPropertyNames セットする appPropertyNames
	 */
	public void setAppPropertyNames(Map<String, String> appPropertyNames) {
		this.appPropertyNames = appPropertyNames;
	}
	
	/**
	 * @param appKey アプリケーション設定キー
	 * @return アプリケーション設定型
	 */
	public String getAppPropertyType(String appKey) {
		return appPropertyTypes.get(appKey);
	}
	
	/**
	 * @param appPropertyTypes セットする appPropertyTypes
	 */
	public void setAppPropertyTypes(Map<String, String> appPropertyTypes) {
		this.appPropertyTypes = appPropertyTypes;
	}
	
	/**
	 * @param appKey アプリケーション設定キー
	 * @return アプリケーション設定説明
	 */
	public String getAppPropertyDescriptions(String appKey) {
		return appPropertyDescriptions.get(appKey);
	}
	
	/**
	 * @param appPropertyDescriptions セットする appPropertyDescriptions
	 */
	public void setAppPropertyDescriptions(Map<String, String> appPropertyDescriptions) {
		this.appPropertyDescriptions = appPropertyDescriptions;
	}
	
}
