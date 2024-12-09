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

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 新規登録モード
	if (modeCardEdit == MODE_CARD_EDIT_INSERT){
		// 登録ボタン押下不可
		setReadOnly("btnRegist", true);
	} else {
		// 登録ボタン押下可
		setReadOnly("btnRegist", false);
	}
	// 締状態が確定である場合
	if (isTightened) {
		// 更新ボタン押下不可
		setReadOnly("btnSelect", true);
		// 再表示ボタン押下不可
		setReadOnly("btnReset", true);
	}
}
