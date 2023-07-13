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

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.LogUtility;
import jp.mosp.framework.utils.MessageUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * ログアウト処理を行う。<br>
 */
public class LogoutAction extends PlatformAction {
	
	/**
	 * ログアウトコマンド。<br>
	 * ログイン中に保持すべき情報を破棄し、ログアウトを行う。<br>
	 * コマンド実行後、ログイン画面へ遷移する。<br>
	 */
	public static final String CMD_LOGOUT = "PF0030";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public LogoutAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_LOGOUT)) {
			// ログアウト
			logout();
		} else {
			throwInvalidCommandException();
		}
	}
	
	/**
	 * {@link #CMD_LOGOUT}参照。
	 */
	protected void logout() {
		// ログアウト処理(MosPセッション保持情報初期化)
		mospParams.getStoredInfo().initStoredInfo();
		// ログイン画面表示コマンド設定
		mospParams.setNextCommand(IndexAction.CMD_SHOW);
		// ログアウトメッセージを設定
		MessageUtility.addMessageLogout(mospParams);
		// ログ出力
		LogUtility.application(mospParams, PfNameUtility.logout(mospParams));
	}
	
}
