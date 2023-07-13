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
 * 時刻（時）チェックエラーメッセージ。
 */
var MSG_HOUR_CHECK = "TMW0202";

/**
 * 時刻（分）チェックエラーメッセージ。
 */
var MSG_MINUTE_CHECK = "TMW0203";

/**
 * 付与日数値チェックエラーメッセージ。
 */
var MSG_GIVINGDAY_VALUE_CHECK = "TMW0219";

/**
 * 付与日数値チェック用数値(整数部)。
 */
var GIVING_CHECK_INTEGER = 100;

/**
 * 付与日数値チェック用数値(少数部)。
 */
var GIVING_CHECK_DECIMAL = 0.5;



function checkTime(targetHour, targetMinute, aryMessage) {
	// 範囲宣言
	var MIN_HOUR  = 0;
	var MAX_HOUR  = 47;
	var MIN_MINUTE = 0;
	var MAX_MINUTE = 59;
	// 時分を取得
	var hour  = getFormValue(targetHour);
	var minute = getFormValue(targetMinute);
	// 未入力チェック
	if( hour == "" ){
		if (aryMessage.length == 0) {
			setFocus(targetHour);
		}
		setBgColor(targetHour, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_HOUR_CHECK, 47));
		return;
	}
	if( minute == "" ){
		if (aryMessage.length == 0) {
			setFocus(targetMinute);
		}
		setBgColor(targetMinute, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_MINUTE_CHECK, null));
		return;
	}
	// 時間確認
	if (hour < MIN_HOUR || hour > MAX_HOUR) {
		if (aryMessage.length == 0) {
			setFocus(targetHour);
		}
		setBgColor(targetHour, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_HOUR_CHECK, 47));
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
 * @param event      イベントオブジェクト
 */
function checkTimes(aryMessage, event) {
	// 検索対象文字列宣言
	var HOUR_ID  = "Hour";
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
		if (elementId.lastIndexOf(HOUR_ID) != elementId.length - HOUR_ID.length) {
			continue;
		}
		// 分ID作成
		var minuteId = elementId.replace(HOUR_ID, MINUTE_ID);
		// 分要素取得
		var minuteElement = document.getElementById(minuteId);
		// 分要素確認
		if (minuteElement == null) {
			continue;
		}
		// 入力確認
		if (getFormValue(element) == "" && getFormValue(minuteElement) == "" ) {
			continue;
		}
		// 時刻チェック
		checkTime(element, minuteElement, aryMessage);
	}
}

/**
 * 付与日数チェック処理。
 * @param aryMessage メッセージ配列
 * @param target     対象要素(StringあるいはObject)
 * @return 確認結果(true：エラー無し、false：エラー有り)
 */
function checkGiving(aryMessage, target) {
	// メッセージ配列(付与日数チェック用)を準備
	var aryGivingMessage = new Array();
	// 付与日数を取得
	var givingDays = getFormValue(target);
	// 小数部が0.5で割り切れない場合
	if (givingDays % GIVING_CHECK_DECIMAL != 0) {
		// メッセージ配列(付与日数チェック用)にメッセージを設定
		aryGivingMessage.push(getMessage(MSG_GIVINGDAY_VALUE_CHECK, getLabel(target)));
	}
	// 整数部が100以上である場合
	if (GIVING_CHECK_INTEGER <= givingDays) {
		// メッセージ配列(付与日数チェック用)にメッセージを設定
		var rep = [getLabel(target), GIVING_CHECK_INTEGER];
		aryGivingMessage.push(getMessage(MSG_OVER_LIMIT_ERR, rep));
	}
	// メッセージ配列(付与日数チェック用)にメッセージが設定されていない場合
	if (aryGivingMessage.length == 0) {
		// 処理終了(エラー無しと判断)
		return true;
	}
	// メッセージ配列にメッセージが設定されていない場合
	if (aryMessage.length == 0) {
		// フォーカスを設定(見つかった最初のエラーであると判断)
		setFocus(target);
	}
	// 対象要素の背景色を変更
	setBgColor(target, COLOR_FIELD_ERROR);
	// メッセージ配列にメッセージ配列(付与日数チェック用)を設定
	Array.prototype.push.apply(aryMessage, aryGivingMessage);
	// エラー有りと判断
	return false;
}

/**
 * 対象要素の値に「:」を追加して取得する。<br>
 * 但し、対象要素の値が時間として妥当である場合のみ追加する。<br>
 * @param target 対象要素(StringあるいはObject)
 * @return 「:」を追加した対象要素値
 */
function addColon(target) {
	// 対象要素値確認
	if (getFormValue(target) == "") {
		// 「-」を取得
		return "-";
	}
	// 対象要素値確認
	if (checkTimeNoMsg(target) == false) {
		// 対象要素値を取得
		return getFormValue(target);
	}
	// 「:」追加
	return getFormValue(target).replace(/(\d\d)(\d\d)/, "$1:$2");
}

/**
 * 対象文字列から「:」あるいは「-」を除く。
 * @param targetString 対象文字列
 * @return 「:」あるいは「-」を除いた文字列
 */
function removeColon(targetString) {
	return targetString.replace(/:|-/g, "");
}

/**
 * 文字列タイプ(時間)を確認する。
 * @param target 確認対象(StringあるいはObject)
 * @return 確認結果(true：OK、false：NG)
 */
function checkTimeNoMsg(target) {
	return checkRegexNoMsg(target, /([0-3][0-9]|[4][0-7])[0-5][0-9]/);
}
