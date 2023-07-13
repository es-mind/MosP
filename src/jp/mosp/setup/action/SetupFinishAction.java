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
import jp.mosp.setup.vo.DbCreateVo;
import jp.mosp.setup.vo.FirstUserVo;
import jp.mosp.setup.vo.SetupFinishVo;

/**
 * 
 * セットアップ完了画面。<br>
 * 作成したDB名・MOSPユーザ名・PWを表示。<br>
 * MOSPへ遷移するボタン設置。<br>
 *
 */
public class SetupFinishAction extends PlatformAction {
	
	/**
	 *  表示コマンド。<br>
	 *  初期表示を行う。<br>
	 */
	public static final String CMD_SHOW = "SU4000";
	
	
	/**
	 * コマンド毎処理。<br>
	 */
	@Override
	public void action() throws MospException {
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		}
	}
	
	@Override
	protected SetupFinishVo getSpecificVo() {
		return new SetupFinishVo();
	}
	
	/**
	 * 画面で表示する値を入手。<br>
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	public void show() throws MospException {
		// ２画面目のVOを呼ぶ
		DbCreateVo createVo = (DbCreateVo)mospParams.getStoredVo(DbCreateVo.class.getName());
		// ４画面目のVOを呼ぶ
		SetupFinishVo setupVo = (SetupFinishVo)mospParams.getVo();
		// DB名を画面へつめる
		setupVo.setLblDataBase(createVo.getTxtDbName());
		// ロール名を画面につめる
		setupVo.setLblRoleName(createVo.getTxtRoleName());
		// ロールパスワードを画面につめる
		setupVo.setLblRolePass(createVo.getTxtRolePw());
		// ３画面のVOを呼ぶ
		FirstUserVo userVo = (FirstUserVo)mospParams.getStoredVo(FirstUserVo.class.getName());
		// MosPユーザ名を画面につめる
		setupVo.setLblMospUser(userVo.getTxtUserId());
		// MosPパスワードを画面につめる
		setupVo.setLblMospPass(userVo.getTxtUserId());
	}
	
}
