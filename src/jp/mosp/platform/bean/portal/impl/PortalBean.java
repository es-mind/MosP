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
package jp.mosp.platform.bean.portal.impl;

import java.util.Set;

import jp.mosp.framework.utils.RoleUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.portal.action.PortalAction;
import jp.mosp.platform.portal.vo.PortalVo;

/**
 * ポータル用Beanの基本となる機能を提供する。<br>
 */
public abstract class PortalBean extends PlatformBean {
	
	/**
	 * {@link PortalBean}を生成する。<br>
	 */
	public PortalBean() {
		// 処理無し
	}
	
	/**
	 * MosP処理情報から{@link PortalVo}を取得する。<br>
	 * {@link PortalBean}は{@link PortalAction}からパラメータを受け取れないため、
	 * {@link PortalVo}を取得してパラメータの設定及び取得を行う。
	 * @return {@link PortalVo}
	 */
	protected PortalVo getPortalVo() {
		return (PortalVo)mospParams.getStoredVo(PortalVo.class.getName());
	}
	
	/**
	 * ポータルパラメータを設定する。<br>
	 * @param key    キー
	 * @param values 値
	 */
	protected void putPortalParameters(String key, String[] values) {
		getPortalVo().putPortalParameters(key, values);
	}
	
	/**
	 * ポータルパラメータを設定する。<br>
	 * @param key   キー
	 * @param value 値
	 */
	protected void putPortalParameter(String key, String value) {
		getPortalVo().putPortalParameters(key, value);
	}
	
	/**
	 * ポータルパラメータを取得する。<br>
	 * @param key キー
	 * @return ポータルパラメータ
	 */
	protected String[] getPortalParameters(String key) {
		return getPortalVo().getPortalParameters(key);
	}
	
	/**
	 * ポータルパラメータを取得する。<br>
	 * @param key キー
	 * @return ポータルパラメータ
	 */
	protected String getPortalParameter(String key) {
		return getPortalVo().getPortalParameter(key);
	}
	
	/**
	 * ポータルJSPリストを追加する。<br>
	 * @param view JSPパス
	 */
	protected void addPortalViewList(String view) {
		getPortalVo().addPortalViewList(view);
	}
	
	/**
	 * 範囲設定を設定する。<br>
	 * ログインユーザからロールを取得し、メニューキーで範囲情報を取得する。<br>
	 * @param menuKey メニューキー
	 */
	protected void setRangeMap(String menuKey) {
		// 範囲設定を除去
		removeRangeMap();
		// ログインユーザのメニューキー群(インデックス昇順)を取得
		Set<String> menuKeys = RoleUtility.getUserMenuKeys(mospParams);
		// 対象メニューキーがログインユーザのメニューキー群に含まれる場合
		if (menuKeys.contains(menuKey)) {
			// ログインユーザのロールから操作範囲設定情報群(キー：操作区分)を取得し設定
			mospParams.getStoredInfo().setRangeMap(RoleUtility.getUserRanges(mospParams, menuKey));
		}
	}
	
	/**
	 * 範囲設定を除去する。<br>
	 */
	protected void removeRangeMap() {
		mospParams.getStoredInfo().setRangeMap(null);
	}
	
}
