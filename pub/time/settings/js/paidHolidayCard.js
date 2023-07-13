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
 * データ重複エラーメッセージ。
 */
var MSG_REPETITION_CHECK		= "TMW0204";
/**
 * 入社日/月データ重複エラーメッセージ。
 */
var MSG_REPETITION_JOIN_CHECK	= "TMW0208";
/**
 * 入社日/月データ有無エラーメッセージ。
 */
var MSG_EXISTENCE_JOIN_CHECK	= "TMW0209";
/**
 * 自動付与欄（入社日/入社月）相関チェックエラーメッセージ。
 */
var MSG_JOIN_CORRELATION		= "TMW0211";
/**
 * 日付（月）チェックエラーメッセージ。
 */
var MSG_MONTH_CHECK				= "PFW0118";
/**
 * 日付（日）チェック範囲外エラーメッセージ。
 */
var MSG_DAY_CHECK_OUTRANGE		= "PFW0119";

/**
 * 出勤率範囲チェックエラーメッセージ。
 */
var MSG_WORK_RATE_CHECK			= "TMW0215";

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 付与区分確認
	setOnChangeHandler("pltPaidHolidayType", changeHolidayType);
	checkHolidayType();
	// 時間単位有休機能確認
	setOnChangeHandler("pltTimelyPaidHoliday", changeTimelyPaidHoliday);
	checkTimelyPaidHoliday();
	setDisabled("pltMaxCarryOverTimes", true);
	setDisabled("pltHalfDayUnit", true);
	// 新規登録
	if (modeCardEdit == MODE_CARD_EDIT_INSERT) {
		setDisabled("pltEditInactivate", true);
	}
	// 履歴追加
	if (modeCardEdit == MODE_CARD_EDIT_ADD) {
		setDisabled("txtPaidHolidayCode", true);
	}
	// 編集モード確認
	if (modeCardEdit == MODE_CARD_EDIT_EDIT) {
		setDisabled("txtEditActivateYear", true);
		setDisabled("txtEditActivateMonth", true);
		setDisabled("txtEditActivateDay", true);
		setDisabled("txtPaidHolidayCode", true);
	}
}

/**
 * 有給休暇詳細チェック処理
 * @param 無し
 * @return 無し
 */
function checkPaidHolidayCard(aryMessage, event) {
	var holidayType = getFormValue("pltPaidHolidayType");
	var workRatio = getFormValue("txtWorkRatio");
	// 範囲宣言
	var MIN_WORKRATID = 0;
	var MAX_WORKRATID = 100;
	// 出勤率の範囲チェック
	if (workRatio < MIN_WORKRATID || workRatio > MAX_WORKRATID) {
		if (aryMessage.length == 0) {
			setFocus("txtWorkRatio");
		}
		setBgColor("txtWorkRatio", COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_WORK_RATE_CHECK, null));
		return;
	}
	if(holidayType == 0) {
		// 基準月日のチェック
		if (checkPointDate(aryMessage, event) == false) {
			return;
		}
		// 自動付与（基準日）の重複チェック
		if (checkTimesPointDate(aryMessage, event) == false) {
			return;
		}
	} else if (holidayType == 3) {
		// 対象外なのでチェックなし
	} else if (holidayType == 4) {
		// 比例
		return;
	} else {
		// 自動付与（入社日/月）の重複チェック
		if (checkWorkDate(aryMessage, event) == false) {
			return;
		}
		// 自動付与（入社日/月）のレコードチェック
		if (checkWorkRecordDate(aryMessage, event) == false) {
			return;
		}
	}
}

/**
 * 基準月日のチェック処理
 * @param 無し
 * @return エラー時、falseを返す
 */
function checkPointDate(aryMessage, event) {
	// 検索対象文字列宣言
	var MONTH_ID = "txtPointDateMonth";
	var DAY_ID = "txtPointDateDay";
	// 範囲宣言
	var MIN_MONTH = 1;
	var MAX_MONTH = 12;
	var MIN_DAY = 1;
	// 各月最終日宣言
	var MAX_DAY = new Array(31, 28, 31, 30, 31, 30, 31, 31, 30, 31, 30, 31);
	// input要素取得
	objTarget = document.form;
	var inputNodeList = objTarget.getElementsByTagName("input");
	var inputNodeListLength = inputNodeList.length;
	for (var i = 0; i < inputNodeListLength; i++) {
		// 要素取得
		var element = inputNodeList.item(i);
		var elementId = new String(element.id);
		// 要素ID確認
		if (elementId.indexOf(MONTH_ID) != 0) {
			continue;
		}
		// 基準日ID作成
		var dayId = elementId.replace(MONTH_ID, DAY_ID);
		// 基準日要素取得
		var dayElement = document.getElementById(dayId);
		// 基準日要素確認
		if (dayElement == null) {
			continue;
		}
		// 入力確認
		var month = getFormValue(element);
		var day = getFormValue(dayElement);
		if (month == "" && day == "" ) {
			continue;
		}
		// 月確認
		if (month < MIN_MONTH || month > MAX_MONTH) {
			if (aryMessage.length == 0) {
				setFocus(element);
			}
			setBgColor(element, COLOR_FIELD_ERROR);
			aryMessage.push(getMessage(MSG_MONTH_CHECK, null));
			return;
		}
		// 日確認
		var maxDay = MAX_DAY;
		if (day < MIN_DAY || day > maxDay[month - 1]) {
			if (aryMessage.length == 0) {
				setFocus(dayElement);
			}
			setBgColor(dayElement, COLOR_FIELD_ERROR);
			aryMessage.push(getMessage(MSG_DAY_CHECK_OUTRANGE, maxDay[month - 1]));
		}
	}
}

/**
 * 自動付与（基準日）の重複チェック処理
 * @param 無し
 * @return エラー時、falseを返す
 */
function checkTimesPointDate(aryMessage, event) {
	// 検索対象文字列宣言
	var POINT_ID = "TimesPointDate";
	var aryPoint = [];
	var aryElement = [];
	// input要素取得
	objTarget = document.form;
	var inputNodeList = objTarget.getElementsByTagName("input");
	var checkNum = 0;
	var pointNum = 0;
	var inputNodeListLength = inputNodeList.length;
	for (var i = 0; i < inputNodeListLength; i++) {
		// 要素取得
		var element = inputNodeList.item(i);
		var elementId = new String(element.id);
		// 要素ID確認
		if (elementId.indexOf(POINT_ID) <= 0) {
			continue;
		}
		if(elementId.length < 19) {
			checkNum = 1;
		} else {
			checkNum = 2;
		}
		if (elementId.indexOf(POINT_ID) != elementId.length - POINT_ID.length - checkNum) {
			continue;
		}
		// 入力確認
		if (getFormValue(element) == "") {
			continue;
		}
		aryPoint[pointNum] = getFormValue(element);
		aryElement[pointNum] = element;
		pointNum++;
	}
	var aryPointLength = aryPoint.length;
	for (var i = 0; i < aryPointLength; i++) {
		for (var j = i+1; j < aryPointLength; j++) {
			if(aryPoint[i] == aryPoint[j]) {
				if (aryMessage.length == 0) {
					setFocus(aryElement[i]);
				}
				setBgColor(aryElement[i], COLOR_FIELD_ERROR);
				aryMessage.push(getMessage(MSG_REPETITION_CHECK, null));
				return false;
			}
		}
	}
}

/**
 * 付与区分のチェック処理
 * @param 無し
 * @return 無し
 */
function checkHolidayType() {
	var holidayType = getFormValue("pltPaidHolidayType");
	// 最初に全項目を表示する
	setObjectVisibility("FirstYearGiving", true);
	setObjectVisibility("AutomaticGivingNorm", true);
	setObjectVisibility("AutomaticGivingJoined", true);
	setObjectVisibility("tblStock", true);
	setDisabled("txtWorkRatio", false);
	setDisabled("txtPointDateMonth", false);
	setDisabled("txtPointDateDay", false);
	setDisabled("txtGeneralPointAmount", false);
	setDisabled("txtStockYearAmount", false);
	setDisabled("txtStockTotalAmount", false);
	setDisabled("txtStockLimitDate", false);
	setDisabled("pltTimelyPaidHoliday", false);
	setDisabled("pltTimelyPaidHolidayTime", false);
	setDisabled("pltTimeUnitPaidHoliday", false);
	setDisabled("pltMaxCarryOverYear", false);
	if (modeCardEdit == MODE_CARD_EDIT_INSERT) {
		setDisabled("pltEditInactivate", true);
	} else {
		setDisabled("pltEditInactivate", false);
	}
	if(holidayType == 0) {
		// 基準日
		setObjectVisibility("Proportionally", false);
		setObjectVisibility("AutomaticGivingNorm", true);
		setObjectVisibility("AutomaticGivingJoined", false);
	} else if(holidayType == 3) {
		// 対象外
		// 必要のない項目を全て非表示にする
		setObjectVisibility("Proportionally", false);
		setObjectVisibility("FirstYearGiving", false);
		setObjectVisibility("AutomaticGivingNorm", false);
		setObjectVisibility("AutomaticGivingJoined", false);
		setObjectVisibility("tblStock", false);
		// 各項目にデフォルト値を設定
		setFormValue("txtWorkRatio","0");
		setFormValue("txtPointDateMonth","1");
		setFormValue("txtPointDateDay","1");
		setFormValue("txtGeneralPointAmount","0");
		setFormValue("txtStockYearAmount","0");
		setFormValue("txtStockTotalAmount","0");
		setFormValue("txtStockLimitDate","0");
		// 必要のない項目を全て読み取り専用にする
		setDisabled("txtWorkRatio", true);
		setDisabled("txtPointDateMonth", true);
		setDisabled("txtPointDateDay", true);
		setDisabled("txtGeneralPointAmount", true);
		setDisabled("txtStockYearAmount", true);
		setDisabled("txtStockTotalAmount", true);
		setDisabled("txtStockLimitDate", true);
		setDisabled("pltTimelyPaidHolidayTime", true);
		setDisabled("pltTimeUnitPaidHoliday", true);
		setDisabled("pltMaxCarryOverYear", true);
		setDisabled("pltEditInactivate", true);
	} else if (holidayType == 4) {
		// 比例
		setObjectVisibility("Proportionally", true);
		setObjectVisibility("FirstYearGiving", false);
		setObjectVisibility("AutomaticGivingNorm", false);
		setObjectVisibility("AutomaticGivingJoined", false);
	} else {
		setObjectVisibility("AutomaticGivingNorm", false);
		setObjectVisibility("AutomaticGivingJoined", true);
		if (modeCardEdit == MODE_CARD_EDIT_INSERT) {
			setFormValue("txtPointDateMonth","0");
			setFormValue("txtPointDateDay","0");
		}
	}
}

function changeHolidayType(event) {
	checkHolidayType();
	onChangeFields(event);
}

/**
 * オブジェクトの表示・非表示
 * @param テーブルid
 * @param num
 * @return 無し
 * @throws 実行時例外
 */
function setObjectVisibility(target , isVisible) {
	var objTarget = getObject(target);
	if (objTarget == null) {
		return;
	}
	if (isVisible) {
		objTarget.style.display = "block";
	} else {
		objTarget.style.display = "none";
	}
}

/**
 * 時間単位有休機能のチェック処理
 * @param 無し
 * @return 無し
 */
function checkTimelyPaidHoliday() {
	var timelyPaidHoliday = getFormValue("pltTimelyPaidHoliday");
	if(timelyPaidHoliday == 1) {
		setDisabled("pltTimelyPaidHolidayTime", true);
		setDisabled("pltTimeUnitPaidHoliday", true);
	} else {
		setDisabled("pltTimelyPaidHolidayTime", false);
		setDisabled("pltTimeUnitPaidHoliday", false);
	}
}

function changeTimelyPaidHoliday(event) {
	checkTimelyPaidHoliday();
	onChangeFields(event);
}

/**
 * 自動付与（入社日/月）の重複チェック処理
 * @param 無し
 * @return エラー時、falseを返す
 */
function checkWorkDate(aryMessage, event) {
	// 検索対象文字列宣言
	var WORK_YEAR_ID = "WorkYear";
	var WORK_MONTH_ID = "WorkMonth";
	var aryYear = [];
	var aryMonth = [];
	var aryElement = [];
	// input要素取得
	objTarget = document.form;
	var inputNodeList = objTarget.getElementsByTagName("input");
	var checkNum = 0;
	var workNum = 0;
	var inputNodeListLength = inputNodeList.length;
	for (var i = 0; i < inputNodeListLength; i++) {
		// 要素取得
		var element = inputNodeList.item(i);
		var elementId = new String(element.id);
		// 要素ID確認
		if (elementId.indexOf(WORK_YEAR_ID) <= 0) {
			continue;
		}
		if(elementId.length < 13) {
			checkNum = 1;
		} else {
			checkNum = 2;
		}
		if (elementId.indexOf(WORK_YEAR_ID) != elementId.length - WORK_YEAR_ID.length - checkNum) {
			continue;
		}
		// 入力確認
		if (getFormValue(element) == "") {
			continue;
		}
		// 継続勤務年数(月)ID作成
		var monthId = elementId.replace(WORK_YEAR_ID, WORK_MONTH_ID);
		// 継続勤務年数(月)要素取得
		var monthElement = document.getElementById(monthId);
		// 継続勤務年数(月)要素確認
		if (monthElement == null) {
			continue;
		}
		aryYear[workNum] = getFormValue(element);
		aryMonth[workNum] = getFormValue(monthElement);
		aryElement[workNum] = element;
		workNum++;
	}
	var aryYearLength = aryYear.length;
	for (var i = 0; i < aryYearLength; i++) {
		for (var j = i+1; j < aryYearLength; j++) {
			if(aryYear[i] == aryYear[j] && aryMonth[i] == aryMonth[j]) {
				if (aryMessage.length == 0) {
					setFocus(aryElement[i]);
				}
				setBgColor(aryElement[i], COLOR_FIELD_ERROR);
				aryMessage.push(getMessage(MSG_REPETITION_JOIN_CHECK, null));
				return false;
			}
		}
	}
}

/**
 * 自動付与（入社日/月）のレコードチェック処理
 * @param 無し
 * @return エラー時、falseを返す
 */
function checkWorkRecordDate(aryMessage, event) {
	// 検索対象文字列宣言
	var WORK_YEAR_ID = "WorkYear";
	var WORK_MONTH_ID = "WorkMonth";
	var JOIN_DATE_ID = "JoiningDateAmount";
	// input要素取得
	objTarget = document.form;
	var inputNodeList = objTarget.getElementsByTagName("input");
	var checkNum = 0;
	var workNum = 0;
	var inputFlg = 0;
	var errorElement;
	var inputNodeListLength = inputNodeList.length;
	for (var i = 0; i < inputNodeListLength; i++) {
		// 要素取得
		var element = inputNodeList.item(i);
		var elementId = new String(element.id);
		// 要素ID確認
		if (elementId.indexOf(WORK_YEAR_ID) <= 0) {
			continue;
		}
		if(elementId.length < 13) {
			checkNum = 1;
		} else {
			checkNum = 2;
		}
		if (elementId.indexOf(WORK_YEAR_ID) != elementId.length - WORK_YEAR_ID.length - checkNum) {
			continue;
		}
		// 勤続勤務年数（月）ID作成
		var workMonthId = elementId.replace(WORK_YEAR_ID, WORK_MONTH_ID);
		// 勤続勤務年数（月）要素取得
		var workMonthElement = document.getElementById(workMonthId);
		// 勤続勤務年数（月）要素確認
		if (workMonthElement == null) {
			continue;
		}
		// 付与日数ID作成
		var joinDateId = elementId.replace(WORK_MONTH_ID, JOIN_DATE_ID);
		// 付与日数要素取得
		var joinDateElement = document.getElementById(joinDateId);
		// 付与日数要素確認
		if (joinDateElement == null) {
			continue;
		}
		// 入力確認
		if (getFormValue(element) == "" && getFormValue(workMonthElement) == "" && getFormValue(joinDateElement) == "") {
			continue;
		}
		// 継続勤務年数（年/月）、付与日数のどれか一つだけ入力されていた場合はエラーとする
		if (getFormValue(element) == "" || getFormValue(workMonthElement) == "" || getFormValue(joinDateElement) == "") {
			if (getFormValue(element) != "") {
				if (aryMessage.length == 0) {
					setFocus(element);
				}
				setBgColor(element, COLOR_FIELD_ERROR);
				aryMessage.push(getMessage(MSG_JOIN_CORRELATION, null));
				return false;
			}
			if (getFormValue(workMonthElement) != "") {
				if (aryMessage.length == 0) {
					setFocus(workMonthElement);
				}
				setBgColor(workMonthElement, COLOR_FIELD_ERROR);
				aryMessage.push(getMessage(MSG_JOIN_CORRELATION, null));
				return false;
			}
			if (getFormValue(joinDateElement) != "") {
				if (aryMessage.length == 0) {
					setFocus(joinDateElement);
				}
				setBgColor(joinDateElement, COLOR_FIELD_ERROR);
				aryMessage.push(getMessage(MSG_JOIN_CORRELATION, null));
				return false;
			}
		}
		// 一件以上入力されているのでフラグに1を設定する
		inputFlg = 1;
		errorElement = element;
		workNum++;
	}
	if(inputFlg == 0) {
		if (aryMessage.length == 0) {
			setFocus(errorElement);
		}
		setBgColor(errorElement, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_EXISTENCE_JOIN_CHECK, null));
		return false;
	}
}
