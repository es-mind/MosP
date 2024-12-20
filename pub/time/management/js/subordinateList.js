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
 * 検索条件チェックエラーメッセージ
 */
var MSG_SEARCH_CONDITION = "PFW0234";

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 有効日(編集)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可
		setReadOnly("pltSearchRequestYear", true);
		setReadOnly("pltSearchRequestMonth", true);
	} else {
		setReadOnly("btnSearch", true);
	}
	// イベントハンドラ設定
	setOnChangeHandler("pltSearchRequestYear", onChangeYearPulldown);
}

/**
 * 検索時追加チェック
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 */
function checkSearch(aryMessage, event) {
	checkSearchCondition(aryMessage);
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
 * 出勤簿、予定簿出力時のチェックを行う。
 * @param event イベント
 * @return 無し
 */
function checkExtraForFile(event) {
	return validate(null, checkExtra, event);
}

/**
 * 検索条件を確認する。
 * @param aryMessage メッセージ配列
 */
function checkSearchCondition(aryMessage) {
	if (!jsSearchConditionRequired) {
		// 検索条件が必須でない場合
		return;
	}
	// 検索条件が必須の場合
	if (hasSearchCondition()) {
		// 検索条件が1つでもある場合
		return;
	}
	// 検索条件がない場合
	aryMessage.push(getMessage(MSG_SEARCH_CONDITION, null));
}

/**
 * 検索条件の有無を確認する
 * @return 検索条件が1つでもある場合true、そうでない場合false
 */
function hasSearchCondition() {
	return !isAllFormEmpty(
			"txtSearchEmployeeCode",
			"txtSearchEmployeeName",
			"pltSearchWorkPlace",
			"pltSearchEmployment",
			"pltSearchSection",
			"pltSearchPosition",
			"pltSearchApproval",
			"pltSearchCalc",
			"pltSearchHumanType"
	);
}

