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
 * 画面読込時追加処理を行う。
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// ユニット区分にオンチェンジイベントハンドラを設定する。
	setOnChangeHandler("pltSearchUnitType", onChangeUnitType);
	// ユニット区分により検索項目の入力制御を行う。
	onChangeUnitType(null);
}

/**
 * ユニット区分変更時の処理を行う。<br>
 * @param event イベントオブジェクト
 */
function onChangeUnitType(event) {
	if (event != null) {
		onChangeFields(event);
	}
	// ユニット区分確認
	switch (getFormValue("pltSearchUnitType")) {
		// 所属の場合
		case "section":
			setDisabled("txtSearchSectionName" , false);
			setDisabled("txtSearchPositoinName", false);
			setDisabled("txtSearchEmployeeCode", true );
			setDisabled("txtSearchApprover"    , true );
			break;
		// 個人の場合
		case "person":
			setDisabled("txtSearchSectionName" , true );
			setDisabled("txtSearchPositoinName", true );
			setDisabled("txtSearchEmployeeCode", false);
			setDisabled("txtSearchApprover"    , false);
			break;
		default:
	}
}

/**
 * 追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
}
