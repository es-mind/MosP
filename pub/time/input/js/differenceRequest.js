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
 * 時差出勤変更確認メッセージ。
 */
var MSG_FLEXTIME_CHANGE_CHECK	= "TMW0220";

/**
 * 登録確認メッセージ。
 */
var MSG_DIFFERENCE_REQUIRED_CHECK = "TMW0281";

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	setOnChangeHandler("pltEditDifferenceType", checkEditDifferenceType);
	checkEditDifferenceType();
	setOnChangeHandler("pltEditRequestHour", checkEditRequestTime);
	setOnChangeHandler("pltEditRequestMinute", checkEditRequestTime);
	checkEditRequestTime();
	setOnClickHandler("ckbEndDate", checkCkbEndDate);
	checkCkbEndDate();
	setDisabled("lblEndTimeHour", true);
	setDisabled("lblEndTimeMinute", true);
	// 有効日モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		setDisabled("pltEditRequestYear", true);
		setDisabled("pltEditRequestMonth", true);
		setDisabled("pltEditRequestDay", true);
		setDisabled("pltEditDifferenceType", false);
	} else {
		setDisabled("pltEditDifferenceType", true);
		setDisabled("ckbEndDate", true);
	}
	if (jsSearchActivateDateMode == MODE_ACTIVATE_DATE_FIXED) {
		setDisabled("pltSearchRequestYear", true);
		setDisabled("pltSearchRequestMonth", true);
		setDisabled("pltSearchWorkType", false);
		setReadOnly("btnSearch", false);
	} else {
		setDisabled("pltSearchRequestYear", false);
		setDisabled("pltSearchRequestMonth", false);
		setDisabled("pltSearchWorkType", true);
		setReadOnly("btnSearch", true);
	}
	// 期間終了日カレンダボタンクリックイベントを設定
	setOnClickHandler(getCalendarButtonFromInput("pltEditEnd"), clickEndCalendar);
}

/**
 * 追加チェックを行う。<br>
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
 * 申請関連のリクエストを送信する。<br>
 * 入力チェックを行った後、更新系確認メッセージを出し、リクエストを送信する。<br>
 * @param event イベントオブジェクト
 * @param cmd   コマンド
 * @return 無し
 */
function submitApplication(event, cmd) {
	var validateTarget = "divEdit";
	var objExtraCheck = checkEndDateExtra;
	if (getFormValue("pltEditDifferenceType") != "s") {
		// 時差出勤区分がSでない場合
		return submitRegist(event, validateTarget, objExtraCheck, cmd);
	}
	// 時差出勤区分がSの場合
	var startTime = getFormValue("pltEditRequestHour") * 60 + parseIntDecimal(getFormValue("pltEditRequestMinute"));
	var endTime = getFormValue("lblEndTimeHour") * 60 + parseIntDecimal(getFormValue("lblEndTimeMinute"));
	if (startTime >= 5 * 60 && startTime <= 22 * 60 && endTime >= 5 * 60 && endTime <= 22 * 60) {
		// 入力チェック
		if (validate(validateTarget, objExtraCheck, event)) {
			// 更新系確認メッセージ
			if (confirm(getMessage(MSG_DIFFERENCE_REQUIRED_CHECK, null))) {
				// リクエスト送信
				doSubmit(document.form, cmd);
			}
		}
	} else {
		// 時差出勤時刻が深夜時間帯の場合
		submitRegist(event, validateTarget, objExtraCheck, cmd);
	}
}

/**
 * 下書時の追加チェックを行う。<br>
 * 期間終了日の入力チェックを行う。<br>
 */
function checkDraftExtra(aryMessage, event) {
	checkEndDateExtra(aryMessage, event);
}

/**
 * 時差出勤区分のチェック処理
 * @param 無し
 * @return 無し
 */
function checkEditDifferenceType() {
	var differenceType = getFormValue("pltEditDifferenceType");
	if (
		"a" == differenceType
		|| "b" == differenceType
		|| "c" == differenceType
		|| "d" == differenceType
	) {
		setDisabled("pltEditStartDate", true);
		setDisabled("pltEditRequestHour", true);
		setDisabled("pltEditRequestMinute", true);
		setDisabled("ckbEndDate", false);
	} else if("s" == differenceType) {
		if(jsEditDifferenceTypeMode != MODE_ACTIVATE_DATE_FIXED) {
			// 更新系確認メッセージ
			if (confirm(getMessage(MSG_FLEXTIME_CHANGE_CHECK, null))) {
				setDisabled("pltEditStartDate", false);
				setDisabled("pltEditRequestHour", false);
				setDisabled("pltEditRequestMinute", false);
				setDisabled("ckbEndDate", true);
				setDisabled("pltEditEndYear", true);
				setDisabled("pltEditEndMonth", true);
				setDisabled("pltEditEndDay", true);
			} else {
				setFormValue("pltEditDifferenceType", "a");
				// 問合中フラグ更新
				inquiring = false;
				doSubmit(document.form, "TM1006");
			}
		} else {
			setDisabled("ckbEndDate", true);
			setDisabled("pltEditEndYear", true);
			setDisabled("pltEditEndMonth", true);
			setDisabled("pltEditEndDay", true);
		}
	}
	doSubmit(document.form, "TM1006");
}

/**
 * 時差出勤開始時刻のチェック処理
 * @param 無し
 * @return 無し
 */
function checkEditRequestTime() {
	doSubmit(document.form, "TM1006");
}

/**
 * 終了日指定チェックボックスのチェック処理
 * @param 無し
 * @return 無し
 */
function checkCkbEndDate() {
	if (isCheckableChecked("ckbEndDate")) {
		setDisabled("pltEditEndYear", false);
		setDisabled("pltEditEndMonth", false);
		setDisabled("pltEditEndDay", false);
	} else {
		setDisabled("pltEditEndYear", true);
		setDisabled("pltEditEndMonth", true);
		setDisabled("pltEditEndDay", true);
	}
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
 * 追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkDateExtra(aryMessage, event) {
	checkDate("pltEditRequestYear", "pltEditRequestMonth", "pltEditRequestDay", aryMessage);
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
		var aElements = getElementsByTagName(tdElements.item(5), TAG_A);
		// A要素内SPAN要素群取得
		var spanElements = getElementsByTagName(aElements.item(0), "SPAN");
		if (spanElements.item(0).firstChild.nodeValue != status) {
			var rep = [trimAll(getInnerHtml(getSrcElement(event))), status];
			aryMessage.push(getMessage(MSG_CHECKBOX_SELECT_ERROR, rep));
			return;
		}
	}
}

function parseIntDecimal(s) {
	return parseInt(s, 10);
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

