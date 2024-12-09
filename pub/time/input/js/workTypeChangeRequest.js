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
 * チェックボックス選択エラーメッセージ。
 */
var MSG_CHECKBOX_SELECT_ERROR = "PFW0233";

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 */
function onLoadExtra() {
	setOnClickHandler("ckbEndDate", setDisabledEndDate);
	setDisabledEndDate();
	// 有効日モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		setDisabled("pltEditRequestYear", true);
		setDisabled("pltEditRequestMonth", true);
		setDisabled("pltEditRequestDay", true);
	} else {
		setDisabled("pltEditEndYear", true);
		setDisabled("pltEditEndMonth", true);
		setDisabled("pltEditEndDay", true);
		setDisabled("pltEditWorkType", true);
		setDisabled("ckbEndDate", true);
	}
	if (modeSearchActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		setDisabled("pltSearchRequestYear", true);
		setDisabled("pltSearchRequestMonth", true);
	} else {
		setDisabled("pltSearchWorkType", true);
		setReadOnly("btnSearch", true);
	}
	// 期間終了日カレンダボタンクリックイベントを設定
	setOnClickHandler(getCalendarButtonFromInput("pltEditEnd"), clickEndCalendar);
}

/**
 * 有効/無効設定
 * @param 無し
 * @return 無し
 */
function setDisabledEndDate() {
	if (isCheckableChecked("ckbEndDate")) {
		// チェックされている場合
		setDisabled("pltEditEndYear", false);
		setDisabled("pltEditEndMonth", false);
		setDisabled("pltEditEndDay", false);
		return;
	}
	// チェックされていない場合
	setDisabled("pltEditEndYear", true);
	setDisabled("pltEditEndMonth", true);
	setDisabled("pltEditEndDay", true);
}

/**
 * 申請関連のリクエストを送信する。<br>
 * 入力チェックを行った後、更新系確認メッセージを出し、リクエストを送信する。<br>
 * @param event イベント
 * @param cmd コマンド
 * @return 無し
 */
function submitApplication(event, cmd) {
	submitRegist(event, "divEdit", checkEndDateExtra, cmd);
}

/**
 * 出勤日決定時追加処理
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkDateExtra(aryMessage, event) {
	checkDate("pltEditRequestYear", "pltEditRequestMonth", "pltEditRequestDay", aryMessage);
}

/**
 * 下書時追加処理
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkDraftExtra(aryMessage, event) {
	checkEndDateExtra(aryMessage, event);
}

/**
 * 一括申請時追加処理
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkBatchUpdateExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
	checkStatus(aryMessage, event, "下書");
}

/**
 * 一括取下時追加処理
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkBatchWithdrawnExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
	checkStatus(aryMessage, event, "未承認");
}

/**
 * 期間終了日追加処理
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkEndDateExtra(aryMessage, event) {
	if (isCheckableChecked("ckbEndDate")) {
		checkDate("pltEditEndYear", "pltEditEndMonth", "pltEditEndDay", aryMessage);
	}
}

/**
 * 状態確認。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @param status 状態
 * @return 無し
 */
function checkStatus(aryMessage, event, status) {
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
		// TD要素内A要素群取得
		var aElements = getElementsByTagName(tdElements.item(4), TAG_A);
		// A要素内SPAN要素群取得
		var spanElements = getElementsByTagName(aElements.item(0), "SPAN");
		if (spanElements.item(0).firstChild.nodeValue != status) {
			var rep = [trimAll(getInnerHtml(getSrcElement(event))), status];
			aryMessage.push(getMessage(MSG_CHECKBOX_SELECT_ERROR, rep));
			return;
		}
	}
}

/**
 * 期間終了日カレンダクリック時の処理を行う。
 * @param event イベントオブジェクト
 */
function clickEndCalendar(event) {
	// 期間指定のチェックが付いていない場合
	if (isCheckableChecked("ckbEndDate") == false) {
		// 処理無し
		return;
	}
	// カレンダを表示
	showCalendar(event, TYPE_CALENDAR_DAY);
}

