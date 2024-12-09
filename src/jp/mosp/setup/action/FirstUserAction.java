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

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospUser;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.portal.action.IndexAction;
import jp.mosp.platform.utils.PfMessageUtility;
import jp.mosp.setup.bean.impl.DbSetUpManagement;
import jp.mosp.setup.dto.DbSetUpParameterInterface;
import jp.mosp.setup.vo.DbCreateVo;
import jp.mosp.setup.vo.FirstUserVo;

/**
 * 最初のユーザの新規登録を行う。<br>
 * 社員コード・有効日・姓名・姓名カナ・ログインユーザ名・入社日。<br>
 * 初ログインはこのログインユーザを使う。<br>
 * パスワードはログインユーザと同一。<br>
 * ログインするとパスワード変更画面へ遷移。<br>
 * ログインユーザは後に変更可。<br>
 */

public class FirstUserAction extends PlatformAction {
	
	/**
	 *  表示コマンド。<br>
	 *  初期表示を行う。<br>
	 */
	public static final String	CMD_SHOW	= "SU3000";
	
	/**
	 *  登録コマンド。<br>
	 *  新規ユーザを登録する。<br>
	 */
	public static final String	CMD_REGIST	= "SU3001";
	
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_REGIST)) {
			// 新規登録
			prepareVo();
			insert();
		} else {
			throwInvalidCommandException();
		}
	}
	
	@Override
	protected FirstUserVo getSpecificVo() {
		return new FirstUserVo();
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 * 有効日をリアルタイムにあらかじめ表示する。<br>
	 * @throws MospException 表示できなかった場合
	 */
	protected void show() throws MospException {
		// DB接続画面VO存在確認
		if (hasDbCreateVo() == false) {
			// 繋がったらMosPへ
			mospParams.setNextCommand(IndexAction.CMD_SHOW);
			return;
		}
		// VO取得
		FirstUserVo vo = (FirstUserVo)mospParams.getVo();
		// システム日付取得
		Date date = getSystemDate();
		// 有効日設定
		vo.setTxtActivateYear(getStringYear(date));
		vo.setTxtActivateMonth(getStringMonth(date));
		vo.setTxtActivateDay(getStringDay(date));
		// 有効日モード設定
		vo.setModeActivateDate(PlatformConst.MODE_ACTIVATE_DATE_CHANGING);
	}
	
	/**
	 * 新規登録処理を行う。<br>
	 * 新規ユーザを登録する。<br>
	 * 自動的にユーザマスタで登録する。<br>
	 * @throws MospException 登録できなかった場合
	 */
	protected void insert() throws MospException {
		// DB接続画面VO存在確認
		if (hasDbCreateVo() == false) {
			// 繋がったらMosPへ
			mospParams.setNextCommand(IndexAction.CMD_SHOW);
			return;
		}
		// VO取得
		FirstUserVo vo = (FirstUserVo)mospParams.getVo();
		// MosPUserのセット。
		MospUser mospUser = new MospUser();
		mospUser.setUserId(vo.getTxtUserId());
		mospParams.setUser(mospUser);
		// パラメータ
		DbSetUpParameterInterface parameter = DbSetUpManagement.initParameter(mospParams);
		// VOの値をセット
		parameter.setUserId(vo.getTxtUserId());
		parameter.setEmployeeCode(vo.getTxtEmployeeCode());
		parameter.setLastName(vo.getTxtLastName());
		parameter.setFirstName(vo.getTxtFirstName());
		parameter.setLastKana(vo.getTxtLastKana());
		parameter.setFirstKana(vo.getTxtFirstKana());
		parameter.setEntranceDate(getDate(vo.getTxtEntranceYear(), vo.getTxtEntranceMonth(), vo.getTxtEntranceDay()));
		Date activateDate = getDate(vo.getTxtActivateYear(), vo.getTxtActivateMonth(), vo.getTxtActivateDay());
		parameter.setActivateDate(activateDate);
		// 初期アカウント登録
		DbSetUpManagement.getInstance(mospParams, parameter).initialize(true);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 消去
		mospParams.setUser(null);
		// 登録成功メッセージを設定
		PfMessageUtility.addMessageNewInsertSucceed(mospParams);
		// 人事情報一覧遷移用社員コード及び有効日設定
		vo.setTxtEmployeeCode(vo.getTxtEmployeeCode());
		vo.setModeActivateDate(getStringDate(activateDate));
		// 次画面へ
		mospParams.setNextCommand(SetupFinishAction.CMD_SHOW);
	}
	
	/**
	 * セキュリティチェックを行う。<br>
	 * この画面から登録だけしていないかなどを確認する。<br>
	 * DB接続画面のVOが存在しているか確認する。<br>
	 * @return (true：存在する、false：存在しない)
	 */
	protected boolean hasDbCreateVo() {
		// DB作成画面のVOを呼び出す
		return mospParams.getStoredVo(DbCreateVo.class.getName()) != null;
	}
	
}
