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
package jp.mosp.platform.portal.action;

import java.util.ArrayList;
import java.util.List;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.portal.PortalBeanInterface;
import jp.mosp.platform.portal.vo.PortalVo;

/**
 * ポータル画面における処理を行う。<br>
 */
public class PortalAction extends PlatformAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String		CMD_SHOW					= "PF0040";
	
	/**
	 * 認証処理後用表示コマンド。<br>
	 * <br>
	 * XMLファイルで、処理シーケンス発行不要設定が施されている。<br>
	 */
	public static final String		CMD_AFTER_AUTH				= "PF0041";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * パンくず等で戻って来る際にリクエストされる。<br>
	 */
	public static final String		CMD_RE_SHOW					= "PF0043";
	
	/**
	 * 登録コマンド。<br>
	 * <br>
	 * 登録処理を行う。<br>
	 */
	public static final String		CMD_REGIST					= "PF0047";
	
	/**
	 * リクエストパラメータ名(ポータルBeanクラス名)。
	 */
	public static final String		PRM_PORTAL_BEAN_CLASS_NAME	= "portalBeanClassName";
	
	/**
	 * MosPアプリケーション設定キー(ポータル用Beanクラス群)。
	 */
	protected static final String	APP_AFTER_AUTH_PORTAL_BEANS	= "AfterAuthPortalBeans";
	
	/**
	 * MosPアプリケーション設定キー(ポータル用Beanクラス群)。
	 */
	protected static final String	APP_PORTAL_BEANS			= "PortalBeans";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public PortalAction() {
		super();
		// パンくずリスト用コマンド設定
		topicPathCommand = CMD_RE_SHOW;
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new PortalVo();
	}
	
	/**
	 * {@link PlatformAction}のprepareVoを実行した後、パラメータマップを{@link PortalVo}に設定する。<br>
	 * {@link PortalVo}ではMapでパラメータを保持するため、パラメータマッピングを別途行う。
	 */
	@Override
	protected PortalVo prepareVo(boolean useStoredVo, boolean useParametersMapper) throws MospException {
		// PlatformActionのprepareVoを実行
		PortalVo portalVo = (PortalVo)super.prepareVo(useStoredVo, useParametersMapper);
		// パラメータマッピング
		if (useParametersMapper) {
			portalVo.setPortalParameters(mospParams.getRequestParamsMap());
		}
		return portalVo;
	}
	
	/**
	 * ポータルではメニューキーはnullとする。<br>
	 * メニュー選択時にエラー等でポータル画面に帰ってくる場合に
	 * パンくずや範囲設定が不正に設定されることを防ぐ。<br>
	 */
	@Override
	protected String getTransferredMenuKey() {
		return null;
	}
	
	@Override
	public void action() throws MospException {
		if (mospParams.getCommand().equals(CMD_AFTER_AUTH)) {
			// パンくずリスト初期化
			mospParams.getTopicPathList().clear();
			// VO準備
			prepareVo(false, false);
			// 表示
			afterAuthShow();
		} else if (mospParams.getCommand().equals(CMD_SHOW)) {
			// パンくずリスト初期化
			mospParams.getTopicPathList().clear();
			// VO準備
			prepareVo(false, false);
			// 表示
			show();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// VO準備
			prepareVo(true, false);
			// 表示
			show();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// VO準備
			prepareVo(true, true);
			// 登録等
			regist();
		} else {
			throwInvalidCommandException();
		}
	}
	
	/**
	 * ログイン後の表示処理を行う。
	 * ログイン後ポータルBeanクラス群(クラス名)に対して、クラス毎に処理を行う。
	 * @throws MospException MosP例外が発生した場合
	 */
	protected void afterAuthShow() throws MospException {
		// ログイン後ポータルBeanクラス群(クラス名)に対して、クラス毎に処理
		showPortalBeans(getAfterAuthPortalBeansClassNames());
		// 処理結果確認
		if (mospParams.hasErrorMessage() == false) {
			// コミット
			commit();
		}
		// 表示処理
		show();
	}
	
	/**
	 * 表示処理を行う。<br>
	 * MosP設定情報からポータルBeanクラス群(クラス名)を取得し、クラス毎に表示処理を行う。
	 * @throws MospException MosP例外が発生した場合
	 */
	protected void show() throws MospException {
		// ポータルBeanクラス群(クラス名)に対して、クラス毎に処理
		showPortalBeans(getPortalBeansClassNames());
	}
	
	/**
	 * ポータルBeanクラス群(クラス名)に対して、クラス毎に処理を行う。
	 * @param portalBeansClassNames ポータルBeanクラス群(クラス名)
	 * @throws MospException MosP例外が発生した場合
	 */
	protected void showPortalBeans(String[] portalBeansClassNames) throws MospException {
		// 操作範囲初期化
		if (mospParams.getStoredInfo().getRangeMap() != null) {
			mospParams.getStoredInfo().setRangeMap(null);
		}
		// ポータルBeanクラス毎に処理を行う
		for (String className : portalBeansClassNames) {
			// ポータルBeanクラス取得
			PortalBeanInterface portalBean = platform().portal(className);
			// エラーメッセージ取得
			List<String> errorMessageList = mospParams.getErrorMessageList();
			// エラーメッセージコピー
			List<String> copyList = new ArrayList<String>(errorMessageList);
			// エラーメッセージクリア
			errorMessageList.clear();
			// 表示処理
			portalBean.show();
			// エラーメッセージ戻し
			errorMessageList.addAll(0, copyList);
		}
	}
	
	/**
	 * 登録等の処理を行う。<br>
	 * ポータルパラメータから処理を実施するポータルBeanクラス名を取得し、登録等の処理を行う。
	 * @throws MospException MosP例外が発生した場合
	 */
	protected void regist() throws MospException {
		// VO取得
		PortalVo vo = (PortalVo)mospParams.getVo();
		// クラス名取得
		String className = vo.getPortalParameter(PRM_PORTAL_BEAN_CLASS_NAME);
		// ポータルBeanクラス取得
		PortalBeanInterface portalBean = platform().portal(className);
		// 登録等の処理
		portalBean.regist();
		// 処理結果確認
		if (mospParams.hasErrorMessage() == false) {
			// コミット
			commit();
		}
		// ロールバック
		rollback();
		// 表示処理
		show();
	}
	
	/**
	 * MosP設定情報からポータルBeanクラス群(クラス名)を取得する。<br>
	 * @return ポータルBeanクラス群(クラス名)
	 */
	protected String[] getPortalBeansClassNames() {
		// ポータルBeanクラス群(クラス名)取得
		String portalBeans = mospParams.getApplicationProperty(APP_PORTAL_BEANS);
		// 分割
		return MospUtility.split(portalBeans, MospConst.APP_PROPERTY_SEPARATOR);
	}
	
	/**
	 * MosP設定情報からポータルBeanクラス群(クラス名)を取得する。<br>
	 * @return ポータルBeanクラス群(クラス名)
	 */
	protected String[] getAfterAuthPortalBeansClassNames() {
		// ポータルBeanクラス群(クラス名)取得
		String afterAuthPortalBeans = mospParams.getApplicationProperty(APP_AFTER_AUTH_PORTAL_BEANS);
		// 分割
		return MospUtility.split(afterAuthPortalBeans, MospConst.APP_PROPERTY_SEPARATOR);
	}
	
}
