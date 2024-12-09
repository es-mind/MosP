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
var MSG_DB_CHECK = "SUW001";



/**
 * 画面読込時追加処理。
 */
function onLoadExtra(){
	// フォーカス設定
	if (getFormValue("txtDbName") == "") {
		setFocus("txtDbName");
	} else {
		setFocus("txtRoleName");
	}
}
/**
 * 追加の入力チェックを行う。
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベントオブジェクト
 */
function checkExtra(aryMessage, event) {
	// DB名確認
	if (checkRegexNoMsg("txtDbName", /^[^A-Za-z]/)) {
		// 先頭の1文字が英字でない場合
		aryMessage.push(getMessage(MSG_DB_CHECK,getLabel("txtDbName")));
	}
	// ロール名確認
	if (checkRegexNoMsg("txtRoleName", /^[^A-Za-z]/)) {
		// 先頭の1文字が英字でない場合
		aryMessage.push(getMessage(MSG_DB_CHECK,getLabel("txtRoleName")));
	}
}
