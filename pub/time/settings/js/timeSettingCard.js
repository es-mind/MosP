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
 * 時刻(時)チェックエラーメッセージ。
 */
var MSG_HOUR_CHECK = "TMW0202";

/**
 * 時刻(分)チェックエラーメッセージ。
 */
var MSG_MINUTE_CHECK = "TMW0203";

/**
 * 時間チェックエラーメッセージ。
 */
var MSG_TIME_CHECK = "TMW0206";

/**
 * 追加チェック処理配列。
 */
var aryCheckExtra = new Array();


/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 新規登録
	if (modeCardEdit == MODE_CARD_EDIT_INSERT) {
		// 無効フラグ編集不可
		setDisabled("pltEditInactivate", true);
	}
	// 履歴追加
	if (modeCardEdit == MODE_CARD_EDIT_ADD) {
		// コード編集不可
		setReadOnly("txtSettingCode", true);
	}
	// 履歴編集
	if (modeCardEdit == MODE_CARD_EDIT_EDIT) {
		// コード編集不可
		setReadOnly("txtSettingCode", true);
		// 決定ボタン押下不可
		setReadOnly("btnActivateDate", true);
	}
	// 有効日(編集)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可
		setReadOnly("txtEditActivateYear", true);
		setReadOnly("txtEditActivateMonth", true);
		setReadOnly("txtEditActivateDay", true);
	} else {
		// 登録ボタン利用不可
		setReadOnly("btnRegist", true);
	}
	// 所定休日取扱
	setFormValue("pltSpecificHoliday", 1);
	setDisabled("pltSpecificHoliday", true);
}

/**
 * 追加チェックを行う。<br>
 * @param aryMessage メッセージ配列
 * @param event イベントオブジェクト
 * @return
 */
function checkExtra(aryMessage, event) {
	checkTimes(aryMessage);
	var startDayHour = "txtStartDayHour";
	if (getFormValue(startDayHour) > 23) {
		if (aryMessage.length == 0) {
			setFocus(startDayHour);
		}
		setBgColor(startDayHour, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_HOUR_CHECK, 23));
	}
	var aryHour = ["txtGeneralWorkTimeHour", "txtLateEarlyHalfHour", "txtSubHolidayHalfNormHour", "txtSubHolidayAllNormHour"];
	var aryHourLength = aryHour.length;
	for (var i = 0; i < aryHourLength; i++) {
		if (getFormValue(aryHour[i]) > 23) {
			if (aryMessage.length == 0) {
				setFocus(aryHour[i]);
			}
			setBgColor(aryHour[i], COLOR_FIELD_ERROR);
			aryMessage.push(getMessage(MSG_TIME_CHECK, 23));
		}
	}
	// 追加チェック処理を実行
	executeCheckExtras(aryCheckExtra, aryMessage, event);
}

function checkTime(targetMinute, aryMessage) {
	// 範囲宣言
	var MIN_MINUTE = 0;
	var MAX_MINUTE = 59;
	// 時分を取得
	var minute = getFormValue(targetMinute);
	// 未入力チェック
	if(minute == "") {
		if (aryMessage.length == 0) {
			setFocus(targetMinute);
		}
		setBgColor(targetMinute, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_MINUTE_CHECK, null));
		return;
	}
	// 分確認
	if (minute < MIN_MINUTE || minute > MAX_MINUTE) {
		if (aryMessage.length == 0) {
			setFocus(targetMinute);
		}
		setBgColor(targetMinute, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_MINUTE_CHECK, null));
		return;
	}
}

/**
 * ノード内の時刻チェックを行う。<br>
 * 時刻は、idで判断する。<br>
 * 〇〇Hourが存在した場合、〇〇Minuteを検索する。<br>
 * 全て存在しており、一つでも入力されていたら時刻の妥当性確認を行う。<br>
 * @param aryMessage メッセージ配列
 */
function checkTimes(aryMessage) {
	// 検索対象文字列宣言
	var MINUTE_ID = "Minute";
	// input要素取得
	objTarget = document.form;
	var inputNodeList = objTarget.getElementsByTagName("input");
	var inputNodeListLength = inputNodeList.length;
	for (var i = 0; i < inputNodeListLength; i++) {
		// 要素取得
		var element = inputNodeList.item(i);
		var elementId = new String(element.id);
		// 要素ID確認
		if (elementId.lastIndexOf(MINUTE_ID) != elementId.length - MINUTE_ID.length) {
			continue;
		}
		// 入力確認
		if (getFormValue(element) == "") {
			continue;
		}
		// 時刻チェック
		checkTime(element, aryMessage);
	}
}
