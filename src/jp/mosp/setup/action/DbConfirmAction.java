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
package jp.mosp.setup.action;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.portal.action.IndexAction;
import jp.mosp.setup.bean.impl.DbSetUpManagement;
import jp.mosp.setup.constant.SetUpConst;
import jp.mosp.setup.constant.SetUpStatus;
import jp.mosp.setup.dto.DbSetUpParameterInterface;
import jp.mosp.setup.vo.DbConfirmVo;

/**
 * 
 * DBの接続先とポート番号を取得。<br>
 * DBと接続する。<br>
 */
public class DbConfirmAction extends PlatformAction {
	
	/**
	 *  表示コマンド。<br>
	 *  <br>
	 *  初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW	= "SU1000";
	
	/**
	 *  確認コマンド。<br>
	 *  <br>
	 *  DBと接続が可能か確認を行う。<br>
	 */
	public static final String	CMD_CHECK	= "SU1001";
	
	
	/**
	 * 各コマンドの処理を実行する。<br>
	 * 
	 */
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_CHECK)) {
			// 接続確認
			prepareVo();
			connect();
		}
	}
	
	@Override
	protected DbConfirmVo getSpecificVo() {
		return new DbConfirmVo();
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 表示に失敗した場合
	 */
	protected void show() throws MospException {
		SetUpStatus status = DbSetUpManagement.getInstance(mospParams, null).confirm();
		// 完了している場合
		if (SetUpStatus.ALREADY.equals(status)) {
			// MosPへ
			mospParams.setNextCommand(IndexAction.CMD_SHOW);
			return;
		}
		// 初期表示でエラーメッセージがある場合削除
		mospParams.getErrorMessageList().clear();
		// VOを呼び出す
		DbConfirmVo vo = (DbConfirmVo)mospParams.getVo();
		// あらかじめ項目に表示させる
		vo.setTxtServer(mospParams.getApplicationProperty(SetUpConst.APP_DEFAULT_SERVER_NAME));
		vo.setTxtPort(mospParams.getApplicationProperty(SetUpConst.APP_DEFAULT_PORT));
	}
	
	/**
	 * DBに接続できるか確認し、接続する。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void connect() throws MospException {
		// 接続確認
		SetUpStatus status = DbSetUpManagement.getInstance(mospParams, null).confirm();
		// 完了している場合
		if (SetUpStatus.ALREADY.equals(status)) {
			// MosPへ
			mospParams.setNextCommand(IndexAction.CMD_SHOW);
			return;
		}
		// エラーメッセージを除去
		mospParams.getErrorMessageList().clear();
		// VOを呼び出す
		DbConfirmVo vo = (DbConfirmVo)mospParams.getVo();
		DbSetUpParameterInterface parameter = DbSetUpManagement.initParameter(mospParams);
		// 接続確認のための値を代入
		parameter.setServerName(vo.getTxtServer());
		parameter.setPort(getInt(vo.getTxtPort()));
		parameter.setSuperPassword(vo.getTxtPostgresPass());
		// 接続確認
		status = DbSetUpManagement.getInstance(mospParams, parameter).confirm();
		if (SetUpStatus.NULL.equals(status)) {
			// 次画面へ
			mospParams.setNextCommand(DbCreateAction.CMD_SHOW);
		}
	}
	
}
