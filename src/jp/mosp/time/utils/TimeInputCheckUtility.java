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
package jp.mosp.time.utils;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.utils.ValidateUtility;
import jp.mosp.time.constant.TimeConst;

/**
 * 入力チェックに有効な共通機能(勤怠)を提供する。<br>
 */
public class TimeInputCheckUtility {
	
	/**
	 * 対象値が休暇回数である(0.5で割り切れる)ことを確認する。<br>
	 * 妥当でない場合は、MosP処理情報にエラーメッセージが加えられる。<br>
	 * @param mospParams   MosP処理情報
	 * @param value        確認対象値
	 * @param fieldName    確認対象フィールド名
	 * @param row          行インデックス
	 */
	public static void checHolidayTimes(MospParams mospParams, double value, String fieldName, Integer row) {
		// 対象値が休暇回数である場合
		if (ValidateUtility.chkIndivisible(value, TimeConst.HOLIDAY_TIMES_HALF)) {
			// 処理終了
			return;
		}
		// エラーメッセージを設定(対象値が休暇回数でない場合)
		TimeMessageUtility.addErrorIndivisible(mospParams, fieldName, row);
	}
	
}
