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

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.utils.LogUtility;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.portal.vo.LoginVo;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.platform.utils.PfNameUtility;

/**
 * 認証処理を行う。<br>
 */
public class AuthAction extends PlatformAction {
	
	/**
	 * 認証処理を行う。
	 */
	public static final String	CMD_AUTHENTICATE	= "PF0020";
	
	/**
	 * メール送信処理を行う。
	 */
	public static final String	CMD_SEND_MAIL		= "PF0021";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public AuthAction() {
		super();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_AUTHENTICATE)) {
			// 認証
			prepareVo();
			auth();
		} else if (mospParams.getCommand().equals(CMD_SEND_MAIL)) {
			// メール送信
			prepareVo();
			sendMail();
		} else {
			throwInvalidCommandException();
		}
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new LoginVo();
	}
	
	/**
	 * 認証処理を行う。
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void auth() throws MospException {
		// VO取得
		LoginVo vo = (LoginVo)mospParams.getVo();
		// ユーザID及びパスワード(クライアント暗号化済み)を取得
		String userId = vo.getTxtUserId();
		String pass = vo.getTxtPassWord();
		// 認証及びMosPユーザの設定
		authAndSetMospUser(userId, pass);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 処理終了
			return;
		}
		// パスワード確認(堅牢性)
		platform().passwordCheck().checkPasswordStrength(userId);
		// パスワード確認結果確認
		if (mospParams.hasErrorMessage()) {
			// パスワード変更画面へ
			mospParams.setNextCommand(PasswordChangeAction.CMD_SHOW);
			return;
		}
		// パスワード確認(有効期間)
		platform().passwordCheck().checkPasswordPeriod(userId, getSystemDate());
		// パスワード確認結果確認
		if (mospParams.hasErrorMessage()) {
			// パスワード変更画面へ
			mospParams.setNextCommand(PasswordChangeAction.CMD_SHOW);
			return;
		}
		// 認証成功(連続実行コマンドを設定)
		mospParams.setNextCommand(PortalAction.CMD_AFTER_AUTH);
		// ログ出力
		LogUtility.application(mospParams, PfNameUtility.login(mospParams));
	}
	
	/**
	 * 認証及びMosPユーザの設定を行う。<br>
	 * @param userId ユーザID
	 * @param pass   パスワード(クライアント暗号化済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void authAndSetMospUser(String userId, String pass) throws MospException {
		// ユーザID及びパスワードによる認証
		platform().auth().authenticate(userId, pass);
		// 認証結果確認
		if (mospParams.hasErrorMessage()) {
			// 認証失敗メッセージ設定及びMosPセッション保持情報初期化
			addAuthFailedMessage();
			return;
		}
		// MosPユーザ情報を設定
		platform().mospUser().setMospUser(userId);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 認証失敗メッセージ設定及びMosPセッション保持情報初期化
			addAuthFailedMessage();
			return;
		}
	}
	
	/**
	 * メール送信処理を行う。
	 * @throws MospException 継承先のメソッドで例外が発生した場合
	 */
	protected void sendMail() throws MospException {
		// 処理無し
	}
	
	/**
	 * 認証失敗時のメッセージを追加する。<br>
	 * MosPセッション保持情報の初期化も、併せて行う。<br>
	 */
	protected void addAuthFailedMessage() {
		// 認証失敗メッセージを設定
		PfMessageUtility.addMessageFailed(mospParams, PfNameUtility.login(mospParams));
		// MosPセッション保持情報初期化
		mospParams.getStoredInfo().initStoredInfo();
	}
	
}
