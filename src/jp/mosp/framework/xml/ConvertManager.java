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

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

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
 * MosP設定情報の変換を行う。<br>
 */
public class ConvertManager implements ConvertManagerInterface {
	
	@Override
	public ConvertResultInterface init() {
		return new ConvertResultInterface() {
			
			private ConcurrentHashMap<String, Map<String, ? extends BaseProperty>> map = new ConcurrentHashMap<String, Map<String, ? extends BaseProperty>>();
			
			
			@Override
			public Map<String, ApplicationProperty> getApplication() {
				return get(PropertyTag.APPLICATION.getName());
			}
			
			@Override
			public Map<String, CommandProperty> getController() {
				return get(PropertyTag.CONTROLLER.getName());
			}
			
			@Override
			public Map<String, MessageProperty> getMessage() {
				return get(PropertyTag.MESSAGE.getName());
			}
			
			@Override
			public Map<String, NamingProperty> getNaming() {
				return get(PropertyTag.NAMING.getName());
			}
			
			@Override
			public Map<String, CodeProperty> getCode() {
				return get(PropertyTag.CODE.getName());
			}
			
			@Override
			public Map<String, AddonProperty> getAddon() {
				return get(PropertyTag.ADD_ON.getName());
			}
			
			@Override
			public Map<String, MainMenuProperty> getMainMenu() {
				return get(PropertyTag.MAIN_MENU.getName());
			}
			
			@Override
			public Map<String, RoleProperty> getRole() {
				return get(PropertyTag.ROLE.getName());
			}
			
			@Override
			public Map<String, ConventionProperty> getConvention() {
				return get(PropertyTag.CONVENTION.getName());
			}
			
			@Override
			public Map<String, ViewConfigProperty> getViewConfig() {
				return get(PropertyTag.VIEW_CONFIG.getName());
			}
			
			@Override
			public Map<String, ModelProperty> getModel() {
				return get(PropertyTag.MODEL.getName());
			}
			
			@SuppressWarnings("unchecked")
			@Override
			public <T extends BaseProperty> Map<String, T> get(String key) {
				if (map.isEmpty()) {
					// アプリケーション設定情報群準備
					map.put(PropertyTag.APPLICATION.getName(), new HashMap<String, ApplicationProperty>());
					// コントローラー設定情報群準備
					map.put(PropertyTag.CONTROLLER.getName(), new HashMap<String, CommandProperty>());
					// メッセージ設定情報群準備
					map.put(PropertyTag.MESSAGE.getName(), new HashMap<String, MessageProperty>());
					// 文言設定情報群準備
					map.put(PropertyTag.NAMING.getName(), new HashMap<String, NamingProperty>());
					// コード設定情報群準備
					map.put(PropertyTag.CODE.getName(), new HashMap<String, CodeProperty>());
					// アドオン設定情報群準備
					map.put(PropertyTag.ADD_ON.getName(), new HashMap<String, AddonProperty>());
					// メインメニュー設定情報群準備
					map.put(PropertyTag.MAIN_MENU.getName(), new HashMap<String, MainMenuProperty>());
					// ロール設定情報群準備
					map.put(PropertyTag.ROLE.getName(), new HashMap<String, RoleProperty>());
					// 規約情報群準備
					map.put(PropertyTag.CONVENTION.getName(), new HashMap<String, ConventionProperty>());
					// 表示設定情報群準備
					map.put(PropertyTag.VIEW_CONFIG.getName(), new HashMap<String, ViewConfigProperty>());
					// モデル設定情報群準備
					map.put(PropertyTag.MODEL.getName(), new HashMap<String, ModelProperty>());
				}
				return (Map<String, T>)map.get(key);
			}
		};
	}
	
	@Override
	public boolean isUnknown(String tagName) {
		return PropertyTag.UNKNOWN.equals(PropertyTag.get(tagName));
	}
	
	@Override
	public void convert(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
		getConverter(wrapper.getNode().getNodeName()).put(properties, wrapper);
	}
	
	
	/**
	 * MosP設定情報作成クラス群。<br>
	 */
	protected HashMap<String, TagConverterInterface> holder = new HashMap<String, TagConverterInterface>();
	
	
	/**
	 * MosP設定情報作成クラスを取得する。<br>
	 * @param tagName タグ名
	 * @return MosP設定情報作成クラス
	 */
	protected TagConverterInterface getConverter(String tagName) {
		TagConverterInterface converter = holder.get(tagName);
		if (converter == null) {
			switch (PropertyTag.get(tagName)) {
				case ADD_ON:
					converter = new AddonTagConverter();
					break;
				case APPLICATION:
					converter = new ApplicationTagConverter();
					break;
				case CODE:
					converter = new CodeTagConverter();
					break;
				case CONTROLLER:
					converter = new ControllerTagConverter();
					break;
				case MAIN_MENU:
					converter = new MainMenuTagConverter();
					break;
				case MESSAGE:
					converter = new MessageTagConverter();
					break;
				case NAMING:
					converter = new NamingTagConverter();
					break;
				case ROLE:
					converter = new RoleTagConverter();
					break;
				case CONVENTION:
					converter = new ConventionTagConverter();
					break;
				case VIEW_CONFIG:
					converter = new ViewConfigTagConverter();
					break;
				case MODEL:
					converter = new ModelTagConverter();
					break;
				default:
					converter = new TagConverterInterface() {
						
						@Override
						public void put(Map<String, BaseProperty> properties, NodeWrapper wrapper) {
							// 何もしない。
						}
					};
					break;
			}
			holder.put(tagName, converter);
		}
		return converter;
	}
	
}
