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
package jp.mosp.platform.bean.human.impl;

import java.util.Date;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.human.HumanCheckBeanInterface;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * 人事情報確認処理。<br>
 */
public class HumanCheckBean extends PlatformBean implements HumanCheckBeanInterface {
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public HumanCheckBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 処理無し
	}
	
	@Override
	public void checkHumanExist(String personalId, Date targetDate, Integer row) throws MospException {
		// 対象個人IDの人事情報が対象日時点で存在しない場合
		if (getHumanInfo(personalId, targetDate) == null) {
			// MosP処理情報にエラーメッセージを設定
			PfMessageUtility.addErrorNoHuman(mospParams, row);
		}
	}
	
}
