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
package jp.mosp.platform.system.action;

import jp.mosp.framework.base.BaseVo;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformAction;
import jp.mosp.platform.constant.PlatformConst;
import jp.mosp.platform.system.vo.NamingTypeListVo;

/**
 * 名称区分マスタ対象項目をXMLファイルから取得し表示する。<br>
 * <br>
 * 以下のコマンドを扱う。<br>
 * <ul><li>
 * {@link #CMD_SHOW}
 * </li><li>
 * {@link #CMD_RE_SHOW}
 * </li><li>
 * {@link #CMD_TRANSFER}
 */
public class NamingTypeListAction extends PlatformAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 名称区分マスタ対象項目をXMLファイルから取得し表示する。<br>
	 */
	public static final String	CMD_SHOW		= "PF2610";
	
	/**
	 * 再表示コマンド。<br>
	 * <br>
	 * パンくずリスト等を用いてこれよりも奥の階層の画面から改めて遷移した場合、<br>
	 * ページ数が更新された状態で再表示を行う。<br>
	 */
	public static final String	CMD_RE_SHOW		= "PF2611";
	
	/**
	 * 画面遷移コマンド。<br>
	 * <br>
	 * 必要な情報をMosP処理情報に設定して、連続実行コマンドを設定する。<br>
	 */
	public static final String	CMD_TRANSFER	= "PF2616";
	
	
	@Override
	public void action() throws MospException {
		// コマンド毎の処理
		if (mospParams.getCommand().equals(CMD_SHOW)) {
			// 表示
			prepareVo(false, false);
			show();
		} else if (mospParams.getCommand().equals(CMD_RE_SHOW)) {
			// 再表示処理
			prepareVo();
			reShow();
			
		} else if (mospParams.getCommand().equals(CMD_TRANSFER)) {
			// 画面遷移
			prepareVo(true, false);
			transfer();
		}
	}
	
	/**
	 * 初期表示処理を行う。<br>
	 */
	private void show() {
		// 名称区分一覧情報を設定
		setVoList();
	}
	
	/**
	 * 再表示処理を行う。<br>
	 */
	private void reShow() {
		setVoList();
	}
	
	/**
	 * 対象個人ID、対象日等をMosP処理情報に設定し、
	 * 譲渡Actionクラス名に応じて連続実行コマンドを設定する。<br>
	 */
	private void transfer() {
		// 譲渡Actionクラス名毎に処理
		mospParams.setNextCommand(NamingMasterAction.CMD_SHOW);
	}
	
	/**
	 * 名称区分一覧情報をコードから取得しVOに設定する。<br>
	 */
	protected void setVoList() {
		// VO取得
		NamingTypeListVo vo = (NamingTypeListVo)mospParams.getVo();
		String[][] namingType = getCodeArray(PlatformConst.CODE_KEY_NAMING_TYPE, false);
		// VOに設定
		vo.setAryLblNamingTypeName(namingType);
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new NamingTypeListVo();
	}
}
