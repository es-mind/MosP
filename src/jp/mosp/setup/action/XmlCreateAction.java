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
import jp.mosp.setup.constant.Command;
import jp.mosp.setup.constant.SetUpStatus;
import jp.mosp.setup.dto.DbSetUpParameterInterface;
import jp.mosp.setup.vo.XmlCreateVo;

/**
 * XMLファイルの作成が必要か確認し、
 * 必要であれば作成する。
 */
public class XmlCreateAction extends PlatformAction {
	
	/**
	 *  表示コマンド。<br>
	 *  初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW	= "SU5000";
	
	/**
	 *  確認コマンド。<br>
	 *  接続の確認、XMLの作成を行う。<br>
	 */
	public static final String	CMD_CHECK	= "SU5001";
	
	
	/**
	 * コマンド毎処理。<br>
	 */
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_CHECK)) {
			// 新規登録
			prepareVo();
			create();
		}
	}
	
	@Override
	protected XmlCreateVo getSpecificVo() {
		return new XmlCreateVo();
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 表示できなかった場合
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
		XmlCreateVo vo = (XmlCreateVo)mospParams.getVo();
		// あらかじめ項目に表示させる
		vo.setTxtServer(mospParams.getApplicationProperty("DefaultServerName"));
		vo.setTxtPort(mospParams.getApplicationProperty("DefaultPort"));
		vo.setTxtDbName(mospParams.getApplicationProperty("DefaultDbName"));
		vo.setTxtRoleName(mospParams.getApplicationProperty("DefaultDbUser"));
		vo.setTxtRolePw(mospParams.getApplicationProperty("DefaultDbPassword"));
	}
	
	/**
	 * DBに接続し、DB・ロールを確認。<br>
	 * 設定ファイルを作成、設定を上書き。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void create() throws MospException {
		SetUpStatus status = DbSetUpManagement.getInstance(mospParams, null).confirm();
		// エラーメッセージ削除
		mospParams.getErrorMessageList().clear();
		// 完了している場合
		if (SetUpStatus.ALREADY.equals(status)) {
			// MosPへ
			mospParams.setNextCommand(IndexAction.CMD_SHOW);
			return;
		}
		DbSetUpParameterInterface parameter = DbSetUpManagement.initParameter(mospParams);
		// VOを呼び出す
		XmlCreateVo vo = (XmlCreateVo)mospParams.getStoredVo(XmlCreateVo.class.getName());
		// 接続確認のための値を代入
		parameter.setServerName(vo.getTxtServer());
		parameter.setPort(getInt(vo.getTxtPort()));
		parameter.setDbName(vo.getTxtDbName());
		parameter.setUserName(vo.getTxtRoleName());
		parameter.setUserPassword(vo.getTxtRolePw());
		parameter.setCommand(Command.AS_USER);
		DbSetUpManagement management = DbSetUpManagement.getInstance(mospParams, parameter);
		// 接続確認
		if (SetUpStatus.ERROR.equals(management.confirm())) {
			return;
		}
		// XMLの作成
		management.createXml();
		if (mospParams.hasErrorMessage() == false) {
			// 次の画面へ
			mospParams.setNextCommand(IndexAction.CMD_SHOW);
		}
	}
	
}
