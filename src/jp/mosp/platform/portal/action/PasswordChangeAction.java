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
package jp.mosp.platform.portal.action;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.bean.portal.PasswordCheckBeanInterface;
import jp.mosp.platform.portal.vo.PasswordChangeVo;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 初回ログイン時や有効期限切れ、または任意のタイミングでログインパスワードの変更を行う。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_SELECT}
 * </li><li>
 * {@link #CMD_UPDATE}
 * </li></ul>
 */
public class PasswordChangeAction extends PlatformAction {
	
	/**
	 * 自動表示コマンド。<br>
	 * <br>
	 * ログイン時にユーザＩＤとログインパスワードが同一のものである初回ログイン時、
	 * またはログインパスワードの有効期限が切れていると判断された際に自動で画面遷移する。<br>
	 * ログインしようとしたユーザの個人ＩＤを元に該当ユーザのパスワード変更画面を表示する。<br>
	 */
	public static final String	CMD_SHOW	= "PF9110";
	
	/**
	 * 選択表示コマンド。<br>
	 * <br>
	 * 現在ログイン中の個人IDを基にパスワード変更画面を表示する。<br>
	 */
	public static final String	CMD_SELECT	= "PF9116";
	
	/**
	 * 更新コマンド。<br>
	 * <br>
	 * 情報入力欄に入力された新しいパスワードを基にユーザパスワーソ情報テーブルの更新を行う。<br>
	 * 現在のパスワード、新しいパスワード、パスワードの入力確認の入力欄が全て入力されて
	 * いない場合はエラーメッセージにて通知。<br>
	 * また、現在パスワードが誤っている場合や新しいパスワードとパスワードの入力確認の各欄の
	 * 入力内容が異なっている場合も同様にエラーメッセージにて通知。<br>
	 * 更新コマンド実行後はメニューバー、パンくずリストを通常通り表示する。<br>
	 */
	public static final String	CMD_UPDATE	= "PF9118";
	
	
	@Override
	protected BaseVo getSpecificVo() {
		return new PasswordChangeVo();
	}
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_SELECT)) {
			// 選択表示
			prepareVo(false, false);
			select();
		} else if (mospParams.getCommand().equals(CMD_UPDATE)) {
			// 更新
			prepareVo();
			update();
		}
	}
	
	/**
	 * 表示処理を行う。
	 * @throws MospException 注意書きリストの設定に失敗した場合
	 */
	protected void show() throws MospException {
		// VO準備
		PasswordChangeVo vo = (PasswordChangeVo)mospParams.getVo();
		// 強制変更フラグ設定
		vo.setForced(true);
		// メニューバー及びパンくずリスト表示設定
		setNaviUrl();
		// パスワード確認条件をVOに設定
		setCheckConditions();
	}
	
	/**
	 * 選択表示処理を行う。
	 * @throws MospException 注意書きリストの設定に失敗した場合
	 */
	protected void select() throws MospException {
		// VO準備
		PasswordChangeVo vo = (PasswordChangeVo)mospParams.getVo();
		// 強制変更フラグ設定
		vo.setForced(false);
		// パスワード確認条件をVOに設定
		setCheckConditions();
	}
	
	/**
	 * 更新処理を行う。
	 * @throws MospException 暗号化に失敗した場合、或いはパスワード変更に失敗した場合
	 */
	protected void update() throws MospException {
		// VO取得
		PasswordChangeVo vo = (PasswordChangeVo)mospParams.getVo();
		// ログインユーザIDを取得
		String userId = mospParams.getUser().getUserId();
		// パスワード確認処理を取得
		PasswordCheckBeanInterface check = platform().passwordCheck();
		// パスワードを暗号化
		String newPass = check.encrypt(vo.getHdnNewPassword());
		String oldPass = check.encrypt(vo.getHdnOldPassword());
		String confirmPass = check.encrypt(vo.getHdnConfirmPassword());
		// パスワード変更検証
		check.checkPasswordChange(newPass, confirmPass);
		check.checkPasswordChange(userId, oldPass, newPass);
		// パスワード堅牢性確認
		check.checkPasswordStrength(userId, newPass);
		// 処理結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			// メニューバー及びパンくずリスト表示設定
			setNaviUrl();
			return;
		}
		// ユーザパスワード情報を更新
		regist(userId, newPass);
		// 更新結果確認
		if (mospParams.hasErrorMessage()) {
			// 更新失敗メッセージを設定
			PfMessageUtility.addMessageUpdateFailed(mospParams);
			// メニューバー及びパンくずリスト表示設定
			setNaviUrl();
			return;
		}
		// コミット
		commit();
		// 更新成功メッセージを設定
		PfMessageUtility.addMessageUpdatetSucceed(mospParams);
		// 強制変更フラグ確認
		if (vo.isForced()) {
			// 連続実行コマンド設定(ポータル画面へ)
			mospParams.setNextCommand(PortalAction.CMD_SHOW);
		}
	}
	
	/**
	 * ユーザパスワード情報を更新する。<br>
	 * @param userId   ユーザID
	 * @param password パスワード(暗号化済)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void regist(String userId, String password) throws MospException {
		// ユーザパスワード情報を更新
		platform().userAccountRegist().updatePassword(userId, password);
	}
	
	/**
	 * パスワード確認条件をVOに設定する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void setCheckConditions() throws MospException {
		// VOを準備
		PasswordChangeVo vo = (PasswordChangeVo)mospParams.getVo();
		// パスワード確認処理を取得
		PasswordCheckBeanInterface check = platform().passwordCheck();
		// パスワード最低文字数設定
		vo.setMinPassword(check.getMinPassword());
		// パスワード文字種設定
		vo.setCharPassword(check.getCharPassword());
		// 注意書きリスト設定
		vo.setAttentionList(check.getAttentionList());
	}
	
	/**
	 * メニューバー及びパンくずリスト表示設定を行う。
	 */
	protected void setNaviUrl() {
		// VO取得
		PasswordChangeVo vo = (PasswordChangeVo)mospParams.getVo();
		// 強制変更フラグ確認
		if (vo.isForced()) {
			// メニューバー及びパンくずリスト非表示
			mospParams.setNaviUrl(null);
		}
	}
	
}
