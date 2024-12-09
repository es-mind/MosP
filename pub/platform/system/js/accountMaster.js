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
 * 一括更新区分。
 */
var batchUpdateType;

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 編集モード確認
	if (modeCardEdit == MODE_CARD_EDIT_INSERT) {
		// 無効フラグ編集不可
		setReadOnly("pltEditInactivate", true);
	}
	if (modeCardEdit == MODE_CARD_EDIT_ADD) {
		// コード編集不可
		setReadOnly("txtEditUserId", true);
	}
	if (modeCardEdit == MODE_CARD_EDIT_EDIT){
		// コード編集不可
		setReadOnly("txtEditUserId", true);
		// 社員コード編集不可
		setReadOnly("btnEditEmployeeCode", true);
	}
	// 社員選択モード確認
	if (modeEditEmployee == MODE_ACTIVATE_DATE_FIXED) {
		// 社員コード編集不可
		setReadOnly("txtEditEmployeeCode", true);
		// 有効日変更不可
		setReadOnly("txtEditActivateYear", true);
		setReadOnly("txtEditActivateMonth", true);
		setReadOnly("txtEditActivateDay", true);
	}
	// 登録ボタン利用確認
	if (modeEditEmployee != MODE_ACTIVATE_DATE_FIXED) {
		// 登録ボタン利用不可(社員変更状態の場合)
		setReadOnly("btnRegist", true);
	}
	// 一括更新領域存在確認
	if (getObject("divUpdate") == null) {
		return;
	}
	// 一括更新モード設定
	batchUpdateType = getRadioSelectedValue("radBatchUpdateType");
	// 一括更新領域設定
	if (batchUpdateType == "role") {
		// ロールの場合
		setReadOnly("pltUpdateRoleCode", false);
		setReadOnly("pltUpdateInactivate", true);
	} else {
		// 有効/無効の場合
		setReadOnly("pltUpdateRoleCode", true);
		setReadOnly("pltUpdateInactivate", false);
	}
}

/**
 * 追加チェック
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkExtra(aryMessage, event) {
	checkBoxRequired("ckbSelect", aryMessage);
}

/**
 * 一括更新区分を確認する。<br>
 * イベント発生オブジェクト値と、保持された一括更新区分を比較する。<br>
 * @param event イベントオブジェクト
 * @return 確認結果(true：変更有り、false：変更無し)
 */
function checkBatchUpdateType(event) {
	if (batchUpdateType == getSrcElement(event).value) {
		return false;
	}
	return true;
}
