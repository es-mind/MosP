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
 * 画面読込時の追加処理を行う。
 */
function onLoadExtra() {
	// 新規登録モード
	if (modeCardEdit == MODE_CARD_EDIT_INSERT){
		// 無効フラグ編集不可
		setDisabled("pltEditInactivate", true);
		// 削除ボタン利用不可
		setReadOnly("btnDelete", true);
	}
	// 編集モード確認
	if (modeCardEdit == MODE_CARD_EDIT_EDIT) {
		// コード編集不可
		setDisabled("txtEditCode", true);
		// データ区分変更不可
		setReadOnly("btnSelectTable", true);
	}
	// 有効日決定状態
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// データ区分編集不可
		setDisabled("pltEditTable", true);
	}
	// 有効日変更状態
	if (modeActivateDate == MODE_ACTIVATE_DATE_CHANGING) {
		// 登録ボタン利用不可
		setReadOnly("btnRegist", true);
	}
	// プルダウン操作
	setSelectedItemOptions("pltSelectTable", "jsPltSelectSelected", jsPltSelectTable, jsPltSelectSelected);
	// 選択元セレクトボックス及び選択項目セレクトボックスから変更イベントを除去
	setOnChangeHandler("pltSelectTable", null);
	setOnChangeHandler("jsPltSelectSelected", null);
}

/**
 * 選択項目セレクトボックスを全て選択する。<br>
 * また、選択項目に項目が存在するか確認する。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event      イベントオブジェクト
 */
function checkExtra(aryMessage, event) {
	// 選択項目セレクトボックスID宣言
	var target = "jsPltSelectSelected";
	// 選択項目セレクトボックスを全て選択
	selectAllOptions(target);
	// 背景色変更
	setBgColor(target, COLOR_FIELD_NORMAL);
	// 選択項目に項目が存在するか確認
	checkRequired(target, aryMessage);
}
