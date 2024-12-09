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
 * 終業時刻チェックエラーメッセージ。
 */
var MSG_END_TIME_CHECK = "TMW0236";

/**
 * 下書時エラーメッセージ。
 */
var MSG_DRAFT_TIME_EMPTY_CHECK = "TMW0268";

/**
 * 集計時確認メッセージ。
 */
var MSG_CONFIRM_TOTAL = "TMQ3002";

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 勤怠一覧用当日背景色設定
	setToDayTableColor("list")
	// 勤怠年イベントハンドラ設定
	setOnChangeHandler("pltSelectYear", changeYearMonth);
	// 勤怠月イベントハンドラ設定
	setOnChangeHandler("pltSelectMonth", changeYearMonth);
	// チェックボックス確認
	var elements = document.getElementsByName("ckbSelect");
	var elementsLength = elements.length;
	for (var i = 0; i < elementsLength; i++) {
		checkChangeBox(elements.item(i), false);
	}
}

/**
 * 年月プルダウン変更時の処理を行う。<br>
 * @param event イベントオブジェクト
 */
function changeYearMonth(event) {
	// リクエスト送信
	submitTransfer(event, null, null, null, getFormValue("hdnSearchCommand"));
}

/**
 * 追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkDraftExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
	// 始業時刻
	var startTimes = document.getElementsByName("txtStartTime");
	// 終業時刻
	var endTimes = document.getElementsByName("txtEndTime");
	var startTimesLength = startTimes.length;
	for (var i = 0; i < startTimesLength; i++) {
		checkDraftTime(startTimes.item(i), endTimes.item(i), aryMessage)
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
	// 始業時刻
	var startTimes = document.getElementsByName("txtStartTime");
	// 終業時刻
	var endTimes = document.getElementsByName("txtEndTime");
	var startTimesLength = startTimes.length;
	for (var i = 0; i < startTimesLength; i++) {
		checkRegistTime(startTimes.item(i), endTimes.item(i), aryMessage)
	}
}

/**
 * 集計ボタン押下自の追加処理行う。<br>
 * @param event イベント
 * @return 無し
 */
function checkTotal(event) {
	// 年月取得
	var elm = getFormValue("pltSelectYear");
	var elm2 = getFormValue("pltSelectMonth");
	// 勤怠別承認一覧の場合
	if(elm == null && elm2 == null){
		elm = document.getElementById("pltSelectYear").innerText;
		elm2 = document.getElementById("pltSelectMonth").innerText;
	}
	var rep = [elm,elm2 ];
	return confirm(getMessage(MSG_CONFIRM_TOTAL, rep));
}

/**
 * チェックボックスの一括選択・解除を行う。<br>
 * @param obj 一括選択・解除チェックボックス(Object)
 */
function allBoxChecked(obj) {
	// チェックボックス一括処理
	doAllBoxChecked(obj);
	// チェックボックス確認
	var elements = document.getElementsByName("ckbSelect");
	var elementsLength = elements.length;
	for (var i = 0; i < elementsLength; i++) {
		checkChangeBox(elements.item(i), false);
	}
}

/**
 * 始業、終業テキストボックスの追加、除去を行う。<br>
 * @param target    対象TR要素
 * @param needFocus テキストボックス追加時フォーカス要否(true：要、false：不要)
 */
function checkChangeBox(target, needFocus) {
	// 申請ボタン存在確認
	if (getObject("btnRegist") == null) {
		// 処理無し(申請ボタンが存在しない場合)
		return;
	}
	// チェックボックスが含まれるTR要素取得
	var trElement = target.parentNode.parentNode;
	// TR要素内SPAN要素群取得
	var spanElements = getElementsByTagName(trElement, TAG_SPAN );
	// TR要素内INPUT要素群取得(確認用)
	var inputElements = getElementsByTagName(trElement, TAG_INPUT);
	// チェックボックス確認
	if (isCheckableChecked(target)) {
		// TR要素内テキストボックス存在確認(チェックボックスの他にINPUT要素があるかどうか)
		if (inputElements.length > 1) {
			return;
		}
		// 始業、終業のテキストボックスを追加
		var startTime = addTextBoxes(spanElements.item(1), "txtStartTime");
		var endTime = addTextBoxes(spanElements.item(2), "txtEndTime");
		// フォーカス設定
		if (needFocus) {
			setFocus(startTime);
		}
		if (getFormValue(startTime) != "" && getFormValue(endTime) == "") {
			setFocus(endTime);
		}
	} else {
		// TR要素内テキストボックス存在確認(チェックボックスの他にINPUT要素があるかどうか)
		if (inputElements.length == 1) {
			return;
		}
		// 始業、終業のテキストボックスを除去
		removeTextBoxes(spanElements.item(1))
		removeTextBoxes(spanElements.item(2))
	}
}

/**
 * 始業、終業のテキストボックスを追加する。<br>
 * @param targetTdElement 対象TD要素
 * @param elementName     追加要素名
 * @return テキストボックス要素
 */
function addTextBoxes(targetTdElement, elementName) {
	// INPUT要素生成
	var element = document.createElement(TAG_INPUT);
	element.type = INPUT_TYPE_TEXT;
	element.name = elementName;
	element.className = "Number4RequiredTextBox";
	element.value = removeColon(getInnerHtml(targetTdElement));
	element.tabIndex = 1;
	setMaxLengthNumber(element, 4);
	// 対象TD要素の値を消去
	setInnerHtml(targetTdElement, "");
	// 対象TD要素にINPUT要素を追加
	targetTdElement.appendChild(element);
	return element;
}

/**
 * 始業、終業のテキストボックスを除去する。<br>
 * @param targetTdElement 対象TD要素
 */
function removeTextBoxes(targetTdElement) {
	// 対象TD要素内INPUT要素取得
	var elements = getElementsByTagName(targetTdElement, TAG_INPUT);
	// INPUT要素確認
	if (elements.length == 0) {
		return;
	}
	// INPUT要素取得
	var inputElement = elements.item(0);
	// INPUT要素値取得
	var inputValue = addColon(inputElement);
	// INPUT要素除去
	targetTdElement.removeChild(inputElement);
	// 対象TD要素に値を設定
	setInnerHtml(targetTdElement, inputValue);
}

/**
 * 文字列タイプ(時間)を確認する。
 * @param target 確認対象(StringあるいはObject)
 * @param aryMessage エラーメッセージ格納配列
 */
function checkTime(target, aryMessage) {
	var rep = getLabel(target);
	if (!checkTimeNoMsg(target, aryMessage)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		if (rep == "") {
			aryMessage.push(getMessage(MSG_INPUT_FORM_ERROR, null));
		} else {
			aryMessage.push(getMessage(MSG_INPUT_FORM_ERROR_AMP , rep));
		}
	}
}

/**
 * 下書時の時刻を確認する。
 * @param startTime 始業時刻
 * @param endTime 終業時刻
 * @param aryMessage エラーメッセージ格納配列
 */
function checkDraftTime(startTime, endTime, aryMessage) {
	var inputStartTime = getFormValue(startTime) != "";
	var inputEndTime = getFormValue(endTime) != "";
	if (inputStartTime) {
		// 始業時刻が入力されている場合
		checkTime(startTime, aryMessage);
	}
	if (inputEndTime) {
		// 終業時刻が入力されている場合
		checkTime(endTime, aryMessage);
	}
	checkEndTime(startTime, endTime, aryMessage);
}

/**
 * 申請時の時刻を確認する。
 * @param startTime 始業時刻
 * @param endTime 終業時刻
 * @param aryMessage エラーメッセージ格納配列
 */
function checkRegistTime(startTime, endTime, aryMessage) {
	checkTime(startTime, aryMessage);
	checkTime(endTime, aryMessage);
	checkEndTime(startTime, endTime, aryMessage);
}

/**
 * 終業時刻を確認する。
 * @param startTime 始業時刻
 * @param endTime 終業時刻
 * @param aryMessage エラーメッセージ格納配列
 */
function checkEndTime(startTime, endTime, aryMessage) {
	if (!checkTimeNoMsg(startTime) || !checkTimeNoMsg(endTime)) {
		return
	}
	if (getFormValue(startTime) <= getFormValue(endTime)) {
		return;
	}
	if (aryMessage.length == 0) {
		setFocus(endTime);
	}
	setBgColor(endTime, COLOR_FIELD_ERROR);
	aryMessage.push(getMessage(MSG_END_TIME_CHECK, null));
}

