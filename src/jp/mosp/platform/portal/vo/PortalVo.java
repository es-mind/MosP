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
package jp.mosp.platform.portal.vo;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import jp.mosp.platform.base.PlatformVo;

/**
 * ポータル画面の情報を格納する。<br>
 */
public class PortalVo extends PlatformVo {
	
	private static final long		serialVersionUID	= -2676055754943613670L;
	
	/**
	 * ポータルパラメータマップ。<br>
	 * ポータルでは複数のポータルBeanがVOに値を設定するため、Mapの形でパラメータを保持する。<br>
	 */
	private Map<String, String[]>	portalParameters;
	
	/**
	 * ポータルJSPリスト。<br>
	 * portal.jspでインクルードするJSPのリスト。<br>
	 */
	private List<String>			portalViewList;
	
	
	/**
	 * VOの初期設定を行う。<br>
	 */
	public PortalVo() {
		super();
		// ポータルパラメータマップ、ポータルJSPリストを初期化
		portalParameters = new HashMap<String, String[]>();
		portalViewList = new ArrayList<String>();
	}
	
	/**
	 * ポータルパラメータを設定する。<br>
	 * @param key    キー
	 * @param values 値
	 */
	public void putPortalParameters(String key, String[] values) {
		portalParameters.put(key, values);
	}
	
	/**
	 * ポータルパラメータを設定する。<br>
	 * @param key   キー
	 * @param value 値
	 */
	public void putPortalParameters(String key, String value) {
		String[] values = { value };
		portalParameters.put(key, values);
	}
	
	/**
	 * ポータルパラメータを取得する。<br>
	 * ポータルパラメータを取得できない場合は、空の配列を返す。<br>
	 * @param key キー
	 * @return ポータルパラメータ
	 */
	public String[] getPortalParameters(String key) {
		// ポータルパラメータを取得
		String[] parameters = portalParameters.get(key);
		// ポータルパラメータを取得できなかった場合
		if (parameters == null) {
			// 空の配列を取得
			parameters = new String[0];
		}
		// ポータルパラメータを取得
		return parameters;
	}
	
	/**
	 * ポータルパラメータを取得する。<br>
	 * @param key キー
	 * @return ポータルパラメータ
	 */
	public String getPortalParameter(String key) {
		String[] portalParameter = portalParameters.get(key);
		if (portalParameter == null || portalParameter.length == 0) {
			return null;
		}
		return portalParameter[0];
	}
	
	/**
	 * ポータルJSPリストを追加する。<br>
	 * @param view JSPパス
	 */
	public void addPortalViewList(String view) {
		if (portalViewList.contains(view) == false) {
			portalViewList.add(view);
		}
	}
	
	/**
	 * @return portalParameters
	 */
	public Map<String, String[]> getPortalParameters() {
		return portalParameters;
	}
	
	/**
	 * @param portalParameters セットする portalParameters
	 */
	public void setPortalParameters(Map<String, String[]> portalParameters) {
		this.portalParameters = portalParameters;
	}
	
	/**
	 * @return portalViewList
	 */
	public List<String> getPortalViewList() {
		return portalViewList;
	}
	
	/**
	 * @param portalViewList セットする portalViewList
	 */
	public void setPortalViewList(List<String> portalViewList) {
		this.portalViewList = portalViewList;
	}
	
}
