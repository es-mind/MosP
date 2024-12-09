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
import jp.mosp.platform.portal.vo.LoginVo;

/**
 * 初期処理機能を提供する。<br><br>
 * 初期ページを決定する。<br>
 * <br>
 */
public class IndexAction extends PlatformAction {
	
	/**
	 * 表示コマンド。<br>
	 * <br>
	 * 初期表示を行う。<br>
	 */
	public static final String CMD_SHOW = "PF0010";
	
	
	/**
	 * {@link PlatformAction#PlatformAction()}を実行する。<br>
	 */
	public IndexAction() {
		super();
	}
	
	@Override
	protected BaseVo getSpecificVo() {
		return new LoginVo();
	}
	
	@Override
	public void action() throws MospException {
		// VO準備
		prepareVo(false, false);
		// MosPセッション保持情報初期化
		mospParams.getStoredInfo().initStoredInfo();
	}
	
}
