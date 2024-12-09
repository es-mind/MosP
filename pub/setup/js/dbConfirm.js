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
 * DB・ROLE名不正時の確認メッセージ。
 */
var MSG_SERVER_CHECK = "SUW002";


/**
 * 画面読込時追加処理。
 */
function onLoadExtra(){
	// テキストボックス文字数
	setMaxLengthNumber("txtServer", 255);
	// フォーカス設定
	if (getFormValue("txtServer") == "") {
		setFocus("txtServer");
	} else {
		setFocus("txtPsqlPass");
	}
}


/**
 * 文字列タイプ(サーバ名)を確認する。<br>
 * 入力可能文字：半角英数、"-" | "_" | "." | "!" | "~" | "*" | "'" | "(" | ")"
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベントオブジェクト
 */
function checkServer(aryMessage, event) {
	// サーバ名確認
	if (getFormValue("txtServer").match(/[^a-zA-Z0-9-_!~*.'()]/)) {
		if (aryMessage.length == 0) {
			setFocus("txtServer");
		}
		setBgColor("txtServer", COLOR_FIELD_ERROR);
		// 半角英数字(A-Za-z0-9)と半角記号(-_.!~*'())が以外が含まれていればエラー
		aryMessage.push(getMessage(MSG_SERVER_CHECK,getLabel("txtServer")));
	}
}





