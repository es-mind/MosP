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
 * 画面読込時の追加処理を行う。
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// ラジオボタンの選択状態確認
	checkRadioSelect();
}

/**
 * ラジオボタンの選択状態を確認し、ボタン押下可否を設定する。
 * @param 無し
 * @return 無し
 */
function checkRadioSelect() {
	// ラジオボタン選択値取得
	var selectedValue = getRadioSelectedValue("radSelect");
	// ラジオボタン選択値確認
	if (selectedValue == "") {
		// テンプレートボタン押下不可
		setReadOnly("btnTemplate", true);
		// 実行ボタン押下不可
		setReadOnly("btnExecute", true);
	} else {
		// テンプレートボタン押下可
		setReadOnly("btnTemplate", false);
		// 実行ボタン押下可
		setReadOnly("btnExecute", false);
	}
}

/**
 * 対象ファイルが指定されているかを確認する。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event      イベントオブジェクト
 */
function checkExtra(aryMessage, event) {
	// 対象ファイルID宣言
	var target = "filImport";
	// 対象ファイルが設定されているか確認
	checkRequired(target, aryMessage);
}
