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
package jp.mosp.setup.action;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.setup.bean.impl.DbSetUpManagement;
import jp.mosp.setup.constant.SetUpConst;
import jp.mosp.setup.constant.SetUpStatus;
import jp.mosp.setup.dto.DbSetUpParameterInterface;
import jp.mosp.setup.vo.DbConfirmVo;
import jp.mosp.setup.vo.DbCreateVo;

/**
 * 作成するDB名・ロール名を決定。<br>
 * 作成前に同じものが存在しないか確認をし、<br>
 * ロール名・パスワードを決定。<br>
 * 3つを作成終了時エラーがなければ、<br>
 * XMLファイルを作成し、SQLを流す。<br>
 */
public class DbCreateAction extends PlatformAction {
	
	/**
	 *  表示コマンド。<br>
	 *  初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW	= "SU2000";
	
	/**
	 *  確認コマンド。<br>
	 *  接続の確認、全ての登録を行う。<br>
	 */
	public static final String	CMD_CHECK	= "SU2001";
	
	
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
	protected DbCreateVo getSpecificVo() {
		return new DbCreateVo();
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * @throws MospException 表示できなかった場合
	 */
	protected void show() throws MospException {
		// VOを呼び出す
		DbCreateVo vo = (DbCreateVo)mospParams.getVo();
		// あらかじめ項目に表示させる
		vo.setTxtDbName(mospParams.getApplicationProperty("DefaultDbName"));
		vo.setTxtRoleName(mospParams.getApplicationProperty("DefaultDbUser"));
		vo.setTxtRolePw(mospParams.getApplicationProperty("DefaultDbPassword"));
	}
	
	/**
	 * DBに接続し、DB・ロールを作成。<br>
	 * SQLを流し、設定ファイルを作成、設定を上書き。<br>
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	protected void create() throws MospException {
		// VO取得
		BaseVo baseVo = mospParams.getStoredVo(DbConfirmVo.class.getName());
		// セッションがきれている場合
		if (baseVo == null) {
			// メッセージ設定
			mospParams.addErrorMessage(SetUpConst.MSG_SESSION);
			// 連続実行コマンド設定
			mospParams.setNextCommand(DbConfirmAction.CMD_SHOW);
			// MosPセッション保持情報を初期化
			mospParams.getStoredInfo().initStoredInfo();
			return;
		}
		DbConfirmVo confirmVo = (DbConfirmVo)baseVo;
		
		DbSetUpParameterInterface parameter = DbSetUpManagement.initParameter(mospParams);
		// 接続確認のための値を代入
		parameter.setServerName(confirmVo.getTxtServer());
		parameter.setPort(getInt(confirmVo.getTxtPort()));
		parameter.setSuperPassword(confirmVo.getTxtPostgresPass());
		// 接続確認
		SetUpStatus status = DbSetUpManagement.getInstance(mospParams, parameter).confirm();
		if (SetUpStatus.ERROR.equals(status)) {
			return;
		}
		// VO取得
		DbCreateVo createVo = (DbCreateVo)mospParams.getVo();
		
		parameter.setDbName(createVo.getTxtDbName());
		parameter.setUserName(createVo.getTxtRoleName());
		parameter.setUserPassword(createVo.getTxtRolePw());
		
		// DB生成
		DbSetUpManagement.getInstance(mospParams, parameter).createDataBase();
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 次の画面へ
		mospParams.setNextCommand(FirstUserAction.CMD_SHOW);
	}
	
}
