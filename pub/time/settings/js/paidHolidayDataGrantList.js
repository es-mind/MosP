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
 * チェックボックス選択エラーメッセージ。
 */
var MSG_CHECKBOX_GRANT_ERROR ="TMW0329";

/**
 * チェックボックス選択エラーメッセージ。
 */
var MSG_CHECKBOX_SELECT_ERROR = "PFW0233";

/**
 * 検索条件チェックエラーメッセージ
 */
var MSG_SEARCH_CONDITION = "PFW0234";


/**
 * 画面読込時追加処理
 */
function onLoadExtra() {
	// 有効日編集不可
	setReadOnly("txtSearchActivateYear", true);
	setReadOnly("txtSearchActivateMonth", true);
	setReadOnly("txtSearchActivateDay", true);
	// 検索ボタン利用不可
	setReadOnly("btnSearch", true);
	// 有効日(編集)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 検索ボタン利用可
		setReadOnly("btnSearch", false);
		return;
	}
	// 有効日編集可
	setReadOnly("txtSearchActivateYear", false);
	setReadOnly("txtSearchActivateMonth", false);
	setReadOnly("txtSearchActivateDay", false);
}



/**
 * 追加チェック
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkExtra(aryMessage, event) {
	var year = getFormValue("txtSearchEntrance");
	var month = getFormValue("txtSearchEntranceMonth");
	var day = getFormValue("txtSearchEntranceDay");
	checkSearchDate("txtSearchEntrance", "txtSearchEntranceMonth", "txtSearchEntranceDay", aryMessage);
	// 検索項目必須時のチェック
	checkSearchCondition(aryMessage);
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
			"txtSearchEntrance",
			"txtSearchEntranceMonth",
			"txtSearchEntranceDay",
			"txtSearchEmployeeCode",
			"txtSearchEmployeeName",
			"pltSearchPaidHoliday",
			"pltSearchGrant",
			"pltSearchWorkPlace",
			"pltSearchEmployment",
			"pltSearchSection",
			"pltSearchPosition"

	);
}

/**
 * 有給一括付与時の追加チェック
 * 出勤率が計算していない場合、エラーメッセージ出力
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 */
function checkCalcAttendanceRate(aryMessage, event) {
	// 出勤率の計算をしていない場合
	if(jsCalcAttendanceRate != "true"){
		var rep = ["有給休暇一括付与ボタン", "出勤率","押下"];
		aryMessage.push(getMessage(MSG_CHECKBOX_GRANT_ERROR, rep));
	}
}

/**
 * 計算時追加処理
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 */
function checkCalcExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
}

/**
 * 付与時追加処理
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 */
function checkGrantExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
	checkAttendanceRate(aryMessage, event, "-")
	checkAccomplish(aryMessage, event, "達成");
}

/**
 * 出勤率計算確認。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @param attendanceRate 出勤率
 * @return 無し
 */
function checkAttendanceRate(aryMessage, event, attendanceRate) {
	var checkbox = document.getElementsByName("ckbSelect");
	var checkboxLength = checkbox.length;
	for (var i = 0; i < checkboxLength; i++) {
		if (!isCheckableChecked(checkbox[i])) {
			// チェックボックス確認
			continue;
		}
		// チェックボックスが含まれるTR要素取得
		var trElement = checkbox[i].parentNode.parentNode;
		// TR要素内TD要素群取得
		var tdElements = getElementsByTagName(trElement, TAG_TD);
		if (tdElements.item(3).firstChild.nodeValue == attendanceRate) {
			var rep = [trimAll(getInnerHtml(getSrcElement(event))), "出勤率","選択"];
			aryMessage.push(getMessage(MSG_CHECKBOX_GRANT_ERROR, rep));
			return;
		}
	}
}

/**
 * 出勤率基準確認。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @param accomplish 出勤率基準
 * @return 無し
 */
function checkAccomplish(aryMessage, event, accomplish) {
	var checkbox = document.getElementsByName("ckbSelect");
	var checkboxLength = checkbox.length;
	for (var i = 0; i < checkboxLength; i++) {
		if (!isCheckableChecked(checkbox[i])) {
			// チェックボックス確認
			continue;
		}
		// チェックボックスが含まれるTR要素取得
		var trElement = checkbox[i].parentNode.parentNode;
		// TR要素内TD要素群取得
		var tdElements = getElementsByTagName(trElement, TAG_TD);
		if (tdElements.item(4).firstChild.nodeValue != accomplish) {
			var rep = [trimAll(getInnerHtml(getSrcElement(event))), accomplish];
			aryMessage.push(getMessage(MSG_CHECKBOX_SELECT_ERROR, rep));
			return;
		}
	}
}
