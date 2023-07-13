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
package jp.mosp.platform.bean.human.impl;

import jp.mosp.platform.bean.human.HumanInfoExtraBeanInterface;
import jp.mosp.platform.bean.human.base.PlatformHumanBean;
import jp.mosp.platform.human.action.HumanInfoAction;
import jp.mosp.platform.human.vo.HumanInfoVo;

/**
 * 人事情報一覧画面追加情報用Beanの基本となる機能を提供する。<br>
 */
public abstract class HumanInfoExtraBean extends PlatformHumanBean implements HumanInfoExtraBeanInterface {
	
	/**
	 * {@link PlatformHumanBean#PlatformHumanBean()}を実行する。<br>
	 */
	public HumanInfoExtraBean() {
		super();
	}
	
	/**
	 * MosP処理情報から{@link HumanInfoVo}を取得する。<br>
	 * {@link HumanInfoExtraBean}は{@link HumanInfoAction}からパラメータを受け取れないため、
	 * {@link HumanInfoVo}を取得してパラメータの設定及び取得を行う。
	 * @return {@link HumanInfoVo}
	 */
	protected HumanInfoVo getHumanInfoVo() {
		return (HumanInfoVo)mospParams.getStoredVo(HumanInfoVo.class.getName());
	}
	
	/**
	 * 人事情報一覧画面追加情報パラメータを設定する。<br>
	 * @param key    キー
	 * @param values 値
	 */
	protected void putExtraParameters(String key, String[] values) {
		getHumanInfoVo().putExtraParameters(key, values);
	}
	
	/**
	 * 人事情報一覧画面追加情報パラメータを設定する。<br>
	 * @param key   キー
	 * @param value 値
	 */
	protected void putExtraParameter(String key, String value) {
		getHumanInfoVo().putExtraParameters(key, value);
	}
	
	/**
	 * 人事情報一覧画面追加情報パラメータを取得する。<br>
	 * @param key キー
	 * @return 人事情報一覧画面追加情報パラメータ
	 */
	protected String[] getExtraParameters(String key) {
		return getHumanInfoVo().getExtraParameters(key);
	}
	
	/**
	 * 人事情報一覧画面追加情報パラメータを取得する。<br>
	 * @param key キー
	 * @return 人事情報一覧画面追加情報パラメータ
	 */
	protected String getExtraParameter(String key) {
		return getHumanInfoVo().getExtraParameter(key);
	}
	
	/**
	 * 人事情報一覧画面追加情報JSPリストを追加する。<br>
	 * @param view JSPパス
	 */
	protected void addExtraViewList(String view) {
		getHumanInfoVo().addExtraViewList(view);
	}
	
	/**
	 * VOから対象個人IDを取得する。<br>
	 * <br>
	 * @return 対象個人ID
	 */
	protected String getPersonalId() {
		return getHumanInfoVo().getPersonalId();
	}
	
}
