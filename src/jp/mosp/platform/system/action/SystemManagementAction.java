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
package jp.mosp.platform.system.action;

import java.util.LinkedHashMap;
import java.util.Map;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.system.AppPropertyRegistBeanInterface;
import jp.mosp.platform.bean.system.PlatformMasterBeanInterface;
import jp.mosp.platform.system.vo.SystemManagementVo;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * システム管理画面の処理を行う。<br>
 */
public class SystemManagementAction extends PlatformAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String		CMD_SHOW			= "PFSM2900";
	
	/**
	 * 登録コマンド。<br>
	 *  <br>
	 * 各種入力項目に入力されている内容を元に登録処理を行う。<br>
	 */
	public static final String		CMD_REGIST			= "PFSM2904";
	
	/**
	 * コードキー(アプリケーション設定名称)。<br>
	 */
	protected static final String	CODE_KEY_APP_NAMES	= "AppPropertyNames";
	
	/**
	 * コードキー(アプリケーション設定型)。<br>
	 */
	protected static final String	CODE_KEY_APP_TYPES	= "AppPropertyTypes";
	
	/**
	 * コードキー(アプリケーション設定説明)。<br>
	 */
	protected static final String	CODE_KEY_APP_DESCS	= "AppPropertyDescriptions";
	
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示コマンド
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 登録コマンド
			prepareVo();
			regist();
		}
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new SystemManagementVo();
	}
	
	@Override
	protected BaseVo prepareVo(boolean useStoredVo, boolean useParametersMapper) throws MospException {
		// 継承基のメソッドを実行
		SystemManagementVo vo = (SystemManagementVo)super.prepareVo(useStoredVo, useParametersMapper);
		// パラメータマッピングを行わない場合
		if (useParametersMapper == false) {
			// VOを取得
			return vo;
		}
		// アプリケーション設定値群を取得
		Map<String, String> appProperties = vo.getAppProperties();
		// アプリケーション設定キー毎に処理
		for (String appKey : vo.getAppKeys()) {
			// アプリケーション設定値群に値を設定
			appProperties.put(appKey, mospParams.getRequestParam(appKey));
		}
		// VOを取得
		return vo;
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void show() throws MospException {
		// VOを準備
		SystemManagementVo vo = (SystemManagementVo)mospParams.getVo();
		// アプリケーション設定名称群を設定
		vo.setAppPropertyNames(MospUtility.getCodeMap(mospParams, CODE_KEY_APP_NAMES));
		// アプリケーション設定型群を設定
		vo.setAppPropertyTypes(MospUtility.getCodeMap(mospParams, CODE_KEY_APP_TYPES));
		// アプリケーション設定説明群を設定
		vo.setAppPropertyDescriptions(MospUtility.getCodeMap(mospParams, CODE_KEY_APP_DESCS));
		// VOに値を設定
		setDefaultValues();
	}
	
	/**
	 * 更新処理を行う。<br>
	 * VOにアプリケーション設定名称群が設定されている必要がある。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist() throws MospException {
		// VOを準備
		SystemManagementVo vo = (SystemManagementVo)mospParams.getVo();
		// 登録クラスを準備
		AppPropertyRegistBeanInterface regist = platform().appPropertyRegist();
		// アプリケーション設定キー毎に処理
		for (String appKey : vo.getAppKeys()) {
			// 登録
			regist.regist(appKey, vo.getAppProperty(appKey));
		}
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 再表示
		setDefaultValues();
	}
	
	/**
	 * 初期値を設定する。<br>
	 * VOにアプリケーション設定名称群が設定されている必要がある。<br>
	 * <br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	public void setDefaultValues() throws MospException {
		// VOを準備
		SystemManagementVo vo = (SystemManagementVo)mospParams.getVo();
		// アプリケーション設定値群(キー：アプリケーション設定キー)を準備
		Map<String, String> appProperties = new LinkedHashMap<String, String>();
		// アプリケーション設定値群(キー：アプリケーション設定キー)をVOに設定
		vo.setAppProperties(appProperties);
		// プラットフォームマスタ参照処理を取得
		PlatformMasterBeanInterface master = reference().master();
		// アプリケーション設定キー毎に処理
		for (String appKey : vo.getAppKeys()) {
			// アプリケーション設定値群に値を設定
			appProperties.put(appKey, master.getAppProperty(appKey));
		}
	}
	
}
