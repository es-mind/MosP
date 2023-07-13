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

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 有効日モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_CHANGING) {
		// 登録ボタン利用不可(変更状態の場合)
		setReadOnly("btnRegist", true);
	} else {
		// 有効日編集不可(決定状態の場合)
		setReadOnly("txtActivateYear", true);
		setReadOnly("txtActivateMonth", true);
		setReadOnly("txtActivateDay", true);
	}
	// 人事情報一覧遷移ボタン利用可否確認
	if (typeof(jsToHumanInfo) == "undefined") {
		// 人事情報一覧遷移ボタン利用不可
		setReadOnly("btnHumenInfo", true);
	}
}
