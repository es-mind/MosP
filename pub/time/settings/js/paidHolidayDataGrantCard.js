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
 * 画面読込時追加処理。
 */
function onLoadExtra() {
	// 処理無し
}

/**
 * 登録時の確認処理を行う。
 * @param aryMessage メッセージ配列
 */
function checkExtra(aryMessage) {
	// 付与日数群を取得
	var elements = document.getElementsByName("aryTxtGrantDays");
	// 付与日数毎に処理
	var elementsLength = elements.length;
	for (var i = 0; i < elementsLength; i++) {
		// 付与日数を確認
		checkGiving(aryMessage, elements.item(i));
	}
}


/**
 * 削除確認メッセージ。
 */
function confirmDelete(event) {
	// 確認メッセージを表示
	return confirm(getMessage(MSG_REGIST_CONFIRMATION, trimAll(getInnerHtml(getSrcElement(event)))));
}
