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
 * カレンダ区分(年月日)。
 */
var	TYPE_CALENDAR_DAY	= "day";

/**
 * カレンダ区分(年月)。
 */
var	TYPE_CALENDAR_MONTH	= "month";

/**
 * カレンダ区分(年)。
 */
var	TYPE_CALENDAR_YEAR	= "year";

/**
 * タグ名(BUTTON)。
 */
var TAG_BUTTON = "BUTTON";

/**
 * フォーマット(年月日)。
 */
var FORMAT_CALENDAR_DATE = "YYYY/MM/DD";

/**
 * フォーマット(年月)。
 */
var FORMAT_CALENDAR_MONTH = "YYYY/MM";

/**
 * フォーマット(年月)。
 */
var FORMAT_CALENDAR_JP_MONTH = "YYYY年MM月";

/**
 * フォーマット(年)。
 */
var FORMAT_CALENDAR_JP_YEAR = "YYYY年";

/**
 * カレンダ区分。
 */ 
var strCalendarType;

/**
 * カレンダ要素(DIV)。
 */
var objCelendarDiv;

/**
 * カレンダ表示日付(日付オブジェクト)。
 */
var dateCalendar;

/**
 * カレンダ表示領域マウス有無フラグ(true：カレンダ表示領域にマウスが有る、false：無い)
 */
var isMouseOverCalendar = false;

/**
 * カレンダを表示する。
 * @param event   イベントオブジェクト
 * @param calType カレンダ区分
 */
function showCalendar(event, calType) {
	// 既にカレンダ要素(DIV)要素が設定されている(カレンダが表示されている)場合
	if (objCelendarDiv != null) {
		// カレンダを除去
		closeCalendar();
		// 処理終了
		return;
	}
	// 問合中フラグ確認
	if (inquiring) {
		// 処理終了
		return;
	}
	// イベント発生元オブジェクトを取得
	var srcElement = getSrcElement(event);
	// カレンダ要素(DIV)を取得し設定
	objCelendarDiv = getUpperElementByTagName(srcElement, TAG_DIV);
	// カレンダ区分を設定
	strCalendarType = calType;
	// カレンダBUTTON要素を取得
	var calendarButton = getCalendarButton();
	// カレンダ表示領域要素(DIV)を取得
	var calendarDisplay = getCalendarDisplay();
	// カレンダBUTTON要素の座標を取得
	var rect = calendarButton.getBoundingClientRect();
	// カレンダ表示領域要素(DIV)の位置を設定
	calendarDisplay.style.left= new String(Math.floor(rect.left)) + "px";
	calendarDisplay.style.top= new String(Math.floor(rect.top) + Math.floor(rect.height) + Math.floor(window.pageYOffset)) + "px";
	// フォーカス移動イベントを設定(カレンダボタン要素)
	calendarButton.onblur = function(e) { onFocusOutCalendar(e) };
	// フォーカスを設定(カレンダボタン要素)
	setFocus(calendarButton);
	// カレンダ表示日付(日付オブジェクト)を初期化
	dateCalendar = getCalendarInputDate();
	// カレンダテーブル要素を作成
	createCalendarTable();
}

/**
 * カレンダを除去する。
 */
function closeCalendar() {
	// カレンダテーブル要素を除去
	removeCalendarTable();
	// フォーカス移動イベントを初期化
	getCalendarButton().onblur = null;
	// カレンダ区分を初期化
	strCalendarType = "";
	// カレンダ要素(DIV)を初期化
	objCelendarDiv = null;
	// カレンダ表示日付(日付オブジェクト)を初期化
	dateCalendar = null;
	// カレンダ表示領域マウス有無フラグを初期化
	isMouseOverCalendar = false;
}

/**
 * カレンダボタン要素を取得する。
 * @return {Object} カレンダボタン要素
 */
function getCalendarButton() {
	// カレンダ要素(DIV)内のBUTTONタグを取得
	return getElementsByTagName(objCelendarDiv, TAG_BUTTON).item(0);
}

/**
 * カレンダINPUT要素からカレンダボタン要素を取得する。
 * @param target カレンダINPUT要素(StringあるいはObject)
 * @return {Object} カレンダボタン要素
 */
function getCalendarButtonFromInput(target) {
	// カレンダ要素(DIV)を取得
	var calendarDiv = getUpperElementByTagName(getObject(target), TAG_DIV);
	// カレンダ要素(DIV)内のBUTTONタグを取得
	return getElementsByTagName(calendarDiv, TAG_BUTTON).item(0);
}

/**
 * カレンダINPUT要素(textかhidden)を取得する。
 * @return {Object} カレンダINPUT要素(textかhidden)
 */
function getCalendarInput() {
	// カレンダ要素(DIV)内のINPUTタグを取得
	return getElementsByTagName(objCelendarDiv, TAG_INPUT).item(0);
}

/**
 * 年月日別カレンダ入力の年要素(IDはカレンダINPUT要素のID + Year)を取得する。
 * @return {Object} 入力要素(年)
 */
function getCalendarInputYear() {
	// 年月日別カレンダ入力の年要素を取得
	return getObject(getCalendarInput().id + "Year");
}

/**
 * 年月日別カレンダ入力の月要素(IDはカレンダINPUT要素のID + Month)を取得する。
 * @return {Object} 入力要素(月)
 */
function getCalendarInputMonth() {
	// 年月日別カレンダ入力の月要素を取得
	return getObject(getCalendarInput().id + "Month");
}

/**
 * 年月日別カレンダ入力の日要素(IDはカレンダINPUT要素のID + Day)を取得する。
 * @return {Object} 入力要素(日)
 */
function getCalendarInputDay() {
	// 年月日別カレンダ入力の日要素を取得
	return getObject(getCalendarInput().id + "Day");
}


/**
 * カレンダ表示領域要素(DIV)を取得する。
 * @return {Object} カレンダ表示領域要素(DIV)
 */
function getCalendarDisplay() {
	// カレンダ要素(DIV)内のDIVタグを取得
	return getElementsByTagName(objCelendarDiv, TAG_DIV).item(0);
}

/**
 * カレンダテーブル要素を取得する。
 * 取得できなかった場合は、nullを返す。
 * @return {Object} カレンダテーブル要素
 */
function getCalendarTable() {
	// カレンダテーブル要素を取得
	var calendarTables = getElementsByTagName(objCelendarDiv, TAG_TABLE);
	// カレンダテーブル要素を取得できなかった場合
	if (calendarTables.length == 0) {
		// nullを取得
		return null;
	}
	// カレンダテーブル要素を取得
	return calendarTables.item(0);
}

/**
 * カレンダテーブル要素を除去する。
 */
function removeCalendarTable() {
	// カレンダテーブル要素を取得
	var calendarTable = getCalendarTable();
	// カレンダテーブル要素を取得できなかった場合
	if (calendarTable == null) {
		// 処理無し
		return;
	}
	// カレンダ表示領域要素からテーブル要素を除去
	getCalendarDisplay().removeChild(calendarTable);
}

/**
 * 年月日別カレンダ入力であるかを確認する。
 * カレンダINPUT要素とは別に、年月日(あるいは年月)の
 * オブジェクト(IDはカレンダINPUT要素のID + Year or Month or Day)を
 * 準備しているかどうかを確認する。
 * 
 * カレンダINPUT要素をhiddenとし、年月日(あるいは年月)の入力欄を分けて使う
 * ことを想定している。
 * 
 * @return {Boolean} 確認結果(true：年月日別カレンダ入力である、false：そうでない)
 */
function isSplitCalendar() {
	// 年月日別カレンダ入力の年要素と月要素が存在するかどうかを確認
	return getCalendarInputYear() != null && getCalendarInputMonth() != null;
}

/**
 * カレンダ入力欄の日付を取得する。
 * 
 * 1.年月日別カレンダ入力である場合は、それらから取得する。
 * 2.カレンダINPUT要素の値が入力されている場合は、そこから取得する。
 * 3.1及び2で有効な日付が得られなかった場合は、クライアント日付を取得する。
 * 
 * @return {Date} 日付オブジェクト
 */
function getCalendarInputDate() {
	// 日付を準備
	var date = null;
	// 年月日別カレンダ入力である場合
	if (isSplitCalendar()) {
		// 年月日文字列を取得
		var strYear = getFormValue(getCalendarInputYear());
		var strMonth = getFormValue(getCalendarInputMonth());
		var strDay = getFormValue(getCalendarInputDay());
		// 文字列から日付オブジェクトを取得
		date = getDateObject(strYear, strMonth, strDay);
	}
	// 有効な日付が取得できた場合
	if (isValidDate(date)) {
		// 日付オブジェクトを取得
		return date;
	}
	// カレンダINPUT要素の値を取得
	var strDate = getFormValue(getCalendarInput());
	// 文字列から日付オブジェクトを取得
	var date = makeDateOfString(strDate, strCalendarType == TYPE_CALENDAR_MONTH);
	// 有効な日付が取得できた場合
	if (isValidDate(date)) {
		// 日付オブジェクトを取得
		return date;
	}
	// クライアント日付を設定
	date = getClientDate();
	// 有効な日付が取得できた場合
	if (isValidDate(date)) {
		// 日付オブジェクトを取得
		return date;
	}
	// カレンダの最小値(1000/01/01)を取得
	return getDateObject(MIN_DATE_YEAR, "", "");
}

/**
 * カレンダ入力欄にカレンダ表示日付を設定する。
 * カレンダINPUT要素にカレンダ表示日付を設定する。
 * 年月日別カレンダ入力である場合は、それらにもカレンダ表示日付を設定する。
 */
function setCalenderInputValue() {
	// カレンダINPUT要素(textかhidden)に設定する値を準備
	var formValue = "";
	// カレンダ区分毎に処理
	switch (strCalendarType) {
		// カレンダ区分が年月日である場合
		case TYPE_CALENDAR_DAY:
			// カレンダINPUT要素(textかhidden)に設定する値を作成
			formValue = formatCalendar(dateCalendar, FORMAT_CALENDAR_DATE);
			break;
		// カレンダ区分が年月である場合
		case TYPE_CALENDAR_MONTH:
			// カレンダINPUT要素(textかhidden)に設定する値を作成
			formValue = formatCalendar(dateCalendar, FORMAT_CALENDAR_MONTH);
			break;
		// カレンダ区分が年である場合
		case TYPE_CALENDAR_YEAR:
			// カレンダINPUT要素(textかhidden)に設定する値を作成
			formValue = dateCalendar.getFullYear();
			break;
		default:
			// 処理無し
	}
	// カレンダINPUT要素(textかhidden)に値を設定
	setFormValue(getCalendarInput(), formValue);
	// 年月日別カレンダ入力である場合
	if (isSplitCalendar()) {
		// 年月日別カレンダ入力の要素にカレンダ表示日付を設定
		setSplitCalenderInputValue();
	}
}

/**
 * 年月日別カレンダ入力の要素にカレンダ表示日付を設定する。
 * 年月日別カレンダ入力用の特殊な処理。
 */
function setSplitCalenderInputValue() {
	// 年月日別カレンダ入力の要素を取得
	var objYear = getCalendarInputYear();
	var objMonth = getCalendarInputMonth();
	var objDay = getCalendarInputDay();
	// カレンダ表示日付の年月日を取得
	var year = dateCalendar.getFullYear();
	var month = dateCalendar.getMonth() + 1;
	var day = dateCalendar.getDate();
	// 年月日別カレンダ入力の年要素にカレンダ表示日付の年を追加
	addSelectOption(objYear, year, year);
	// カレンダ表示日付を年月日に設定
	setFormValue(objYear, year);
	setFormValue(objMonth, month);
	setFormValue(objDay, day);
	// 変更イベントを発行
	dispatchEvent(objYear, EVENT_TYPE_CHANGE);
	dispatchEvent(objMonth, EVENT_TYPE_CHANGE);
	dispatchEvent(objDay, EVENT_TYPE_CHANGE);
}

/**
 * カレンダテーブル要素を作成する。
 */
function createCalendarTable() {
	// カレンダテーブル要素を除去
	removeCalendarTable();
	// カレンダテーブル要素を作成
	var calendarTable = document.createElement("TABLE");
	// カレンダテーブル要素にヘッダ行を設定
	createTitleCalendar(calendarTable);
	// カレンダテーブル要素にカレンダ行を設定
	createBodyCalendar(calendarTable);
	// マウス関連イベントを設定
	calendarTable.onmouseover = function(e) { isMouseOverCalendar = true; };
	calendarTable.onmouseout  = function(e) { isMouseOverCalendar = false; };
	// カレンダテーブル要素を追加
	getCalendarDisplay().appendChild(calendarTable);
}

/**
 * カレンダテーブル要素にヘッダ行を設定する。
 * @param calendarTable カレンダテーブル要素
 */
function createTitleCalendar(calendarTable) {
	// カレンダテーブル要素にヘッダ行要素を追加し取得
	var rowElement = calendarTable.insertRow(0);
	// タイトル行要素にクラスを設定
	rowElement.setAttribute("class", "CalendarHeaderTr");
	// タイトル行要素にセル要素を追加し取得
	var previousCell = rowElement.insertCell(0);
	var titleCell = rowElement.insertCell(1);
	var nextCell = rowElement.insertCell(2);
	// 前セル要素に表示内容とクリックイベントハンドラを設定
	setInnerHtml(previousCell, "<<");
	previousCell.onclick = function() { previousCalendar() };
	// タイトルセル要素に表示内容とクリックイベントハンドラを設定
	setInnerHtml(titleCell, getCalendarTitle());
	titleCell.onclick = function() { todayCalendar() };
	titleCell.colSpan = strCalendarType == TYPE_CALENDAR_DAY ? 5 : 2;
	// 次セル要素に表示内容とクリックイベントハンドラを設定
	setInnerHtml(nextCell, ">>");
	nextCell.onclick = function() { nextCalendar() };
}

/**
 * 前セル要素をクリックした際の処理を行う。
 * カレンダ表示日付を前に変更し、カレンダテーブル要素を再作成する。
 */
function previousCalendar() {
	// カレンダ区分毎に処理
	switch (strCalendarType) {
		// カレンダ区分が年月日である場合
		case TYPE_CALENDAR_DAY:
			// 1月減算
			addCalendarMonth(dateCalendar, -1);
			break;
		// カレンダ区分が年月である場合
		case TYPE_CALENDAR_MONTH:
			// 1年減算
			addCalendarYear(dateCalendar, -1);
			break;
		// カレンダ区分が年である場合
		case TYPE_CALENDAR_YEAR:
			// 10年減算
			addCalendarYear(dateCalendar, -10);
			break;
		default:
			// 処理無し
	}
	// カレンダテーブル要素を再作成
	createCalendarTable();
}

/**
 * 次セル要素をクリックした際の処理を行う。
 * カレンダ表示日付を次に変更し、カレンダテーブル要素を再作成する。
 */
function nextCalendar() {
	// カレンダ区分毎に処理
	switch (strCalendarType) {
		// カレンダ区分が年月日である場合
		case TYPE_CALENDAR_DAY:
			// 1月加算
			addCalendarMonth(dateCalendar, 1);
			break;
		// カレンダ区分が年月である場合
		case TYPE_CALENDAR_MONTH:
			// 1年加算
			addCalendarYear(dateCalendar, 1);
			break;
		// カレンダ区分が年である場合
		case TYPE_CALENDAR_YEAR:
			// 10年加算
			addCalendarYear(dateCalendar, 10);
			break;
		default:
			// 処理無し
	}
	// カレンダテーブル要素を再作成
	createCalendarTable();
}

/**
 * タイトルセル要素をクリックした際の処理を行う。
 * カレンダ表示日付をクライアント日付に変更し、カレンダテーブル要素を再作成する。
 */
function todayCalendar() {
	// カレンダ表示日付をクライアント日付に変更
	dateCalendar = getClientDate();
	// カレンダテーブル要素を再作成
	createCalendarTable();
}

/**
 * カレンダテーブル要素に日付行を設定する。
 * @param calendarTable カレンダテーブル要素
 */
function createBodyCalendar(calendarTable) {
	// カレンダ区分毎に処理
	switch (strCalendarType) {
		// カレンダ区分が年月日である場合
		case TYPE_CALENDAR_DAY:
			// カレンダテーブル要素に日付行(年月日)を設定
			createBodyCalendarDay(calendarTable);
			break;
		// カレンダ区分が年月である場合
		case TYPE_CALENDAR_MONTH:
			// カレンダテーブル要素に日付行(年月)を設定
			createBodyCalendarMonth(calendarTable);
			break;
		// カレンダ区分が年である場合
		case TYPE_CALENDAR_YEAR:
			// カレンダテーブル要素に日付行(年)を設定
			createBodyCalendarYear(calendarTable);
			break;
		default:
			// 処理無し
	}
}

/**
 * カレンダテーブル要素に日付行(年月日)を設定する。
 * @param calendarTable カレンダテーブル要素
 */
function createBodyCalendarDay(calendarTable) {
	// 行数及び列数を準備
	var rowCount = 6;
	var colCount = 7;
	// 日インデックスを準備
	var dayIndex = new Number(1);
	// 曜日を準備
	var aryJpWeek = new Array("日", "月", "火", "水", "木", "金", "土");
	// 対象月初日の曜日を取得
	var firstDayOfWeek = getCalendarFirstDateOfMonth(dateCalendar).getDay();
	// 対象月最終日取得
	var lastDay = getCalendarLastDateOfMonth(dateCalendar).getDate();
	// 現在の日付を取得
	var currentDate = getCalendarInputDate();
	// 現在の日を取得
	var currentDay = currentDate.getDate();
	// カレンダ表示日付と現在の日付が同じ年月であるかを確認
	var isSameMonth = currentDate.getFullYear() == dateCalendar.getFullYear();
	isSmaeMonth = isSameMonth && currentDate.getMonth() == dateCalendar.getMonth();
	// カレンダテーブル要素の末尾に日付行要素を追加し取得
	var rowElement = calendarTable.insertRow(-1);
	// 日付行要素にクラスを設定
	rowElement.setAttribute("class", "CalendarWeekTr");
	// 列毎に処理
	for (var i = 0; i < colCount; i++) {
		// 日付行要素にセル要素を追加し取得
		var cellElement = rowElement.insertCell(i);
		// 日付セル要素に表示内容を設定
		setInnerHtml(cellElement, aryJpWeek[i]);
		// 休日の日付セル要素の文字色を設定
		setCalendarHolidayColor(cellElement, i);
	}
	// 行毎に処理
	for (var i = 0; i < rowCount; i++) {
		// 行が空であるか判別する
		var isLineEmpty = true;
		// カレンダテーブル要素の末尾に日付行要素を追加し取得
		var rowElement = calendarTable.insertRow(-1);
		// 日付行要素にクラスを設定
		rowElement.setAttribute("class", "CalendarBodyTr");
		// 列毎に処理
		for (var j = 0; j < colCount; j++) {
			// 日付行要素にセル要素を追加し取得
			var cellElement = rowElement.insertCell(j);
			// 最初の行で対象月初日の曜日よりも前であるか最終日より後である場合
			if ((i == 0 && j < firstDayOfWeek) || dayIndex > lastDay) {
				// 日付セル要素にカーソルを設定
				cellElement.style.cursor = "default";
				// 次の日付セル要素へ
				continue;
			}
			// 日付セル要素に属性値を設定
			setCalendarAttribute(cellElement, dayIndex);
			// 日インデックスと現在の日が同じ(年月も同じ)である場合
			if (dayIndex == currentDay && isSmaeMonth) {
				// 背景色を設定
				setBgColor(cellElement, COLOR_FIELD_ERROR);
			}
			// 休日の日付セル要素の文字色を設定
			setCalendarHolidayColor(cellElement, j);
			// 日付セル要素に表示内容とクリックイベントハンドラを設定
			setInnerHtml(cellElement, dayIndex++);
			cellElement.onclick = function(e) { onClickCalendar(e); };
			// 行に日付が入ったためfalseに設定
			isLineEmpty = false;
		}
		// 行が空の場合
		if(isLineEmpty){
			// ヘッダ行を含めた最後の行を削除する
			calendarTable.deleteRow(i + 2);
			return;
		}
	}
}

/**
 * 休日の日付セル要素の文字色を設定する。
 * @param cellElement 日付セル要素
 * @param dayOfWeek   曜日(0～6)
 */
function setCalendarHolidayColor(cellElement, dayOfWeek) {
	// 土曜日の場合
	if (dayOfWeek == 6) {
		// 日付セル要素の文字色を変更にクラスを設定
		setColor(cellElement, "blue");
	}
	// 日曜日の場合
	if (dayOfWeek == 0) {
		// 日付セル要素にクラスを設定
		setColor(cellElement, "red");
	}
}

/**
 * カレンダテーブル要素に日付行(年月日)を設定する。
 * @param calendarTable カレンダテーブル要素
 */
function createBodyCalendarMonth(calendarTable) {
	// 行数及び列数を準備
	var rowCount = 3;
	var colCount = 4;
	// 月インデックスを準備
	var monthIndex = new Number(0);
	// 現在の日付を取得
	var currentDate = getCalendarInputDate();
	// 現在の月を取得
	var currentMonth = currentDate.getMonth();
	// カレンダ表示日付と現在の日付が同じ年であるかを確認
	var isSameYear = currentDate.getFullYear() == dateCalendar.getFullYear();
	// 行毎に処理
	for (var i = 0; i < rowCount; i++) {
		// カレンダテーブル要素の末尾に日付行要素を追加し取得
		var rowElement = calendarTable.insertRow(-1);
		// 日付行要素にクラスを設定
		rowElement.setAttribute("class", "CalendarBodyTr");
		// 列毎に処理
		for (var j = 0; j < colCount; j++) {
			// 日付行要素にセル要素を追加し取得
			var cellElement = rowElement.insertCell(j);
			// 日付セル要素に属性値を設定
			setCalendarAttribute(cellElement, monthIndex);
			// 月インデックスと現在の月が同じ(年も同じ)である場合
			if (monthIndex == currentMonth && isSameYear) {
				// 背景色を設定
				setBgColor(cellElement, COLOR_FIELD_ERROR);
			}
			// 日付セル要素に表示内容とクリックイベントハンドラを設定
			setInnerHtml(cellElement, ++monthIndex + "月");
			cellElement.onclick = function(e) { onClickCalendar(e); };
		}
	}
}

/**
 * カレンダテーブル要素に日付行(年月日)を設定する。
 * @param calendarTable カレンダテーブル要素
 */
function createBodyCalendarYear(calendarTable) {
	// 行数及び列数を準備
	var rowCount = 3;
	var colCount = 4;
	// 現在の年を取得
	var currentYear = getCalendarInputDate().getFullYear();
	// 年を準備(年(カレンダ区分)における最初の表示年を取得)
	var year = getCalendarFirstYear();
	// 行毎に処理
	for (var i = 0; i < rowCount; i++) {
		// カレンダテーブル要素の末尾に日付行要素を追加し取得
		var rowElement = calendarTable.insertRow(-1);
		// 日付行要素にクラスを設定
		rowElement.setAttribute("class", "CalendarBodyTr");
		// 列毎に処理
		for (var j = 0; j < colCount; j++) {
			// 日付行要素にセル要素を追加し取得
			var cellElement = rowElement.insertCell(j);
			// 日付セル要素に属性値を設定
			setCalendarAttribute(cellElement, year);
			// 年とカレンダ表示日付の月が同じである場合
			if (year == currentYear) {
				// 背景色を設定
				setBgColor(cellElement, COLOR_FIELD_ERROR);
			}
			// 日付セル要素に表示内容とクリックイベントハンドラを設定
			setInnerHtml(cellElement, year++);
			cellElement.onclick = function(e) { onClickCalendar(e); };
		}
	}
}

/**
 * タイトルセル要素に表示する内容を取得する。
 * @return {String} タイトルセル要素に表示する内容
 */
function getCalendarTitle() {
	// タイトルセル要素に表示する内容を準備
	var title = "";
	// カレンダ区分毎に処理
	switch (strCalendarType) {
		// カレンダ区分が年月日である場合
		case TYPE_CALENDAR_DAY:
			// タイトルセル要素に表示する内容を設定
			title = formatCalendar(dateCalendar, FORMAT_CALENDAR_JP_MONTH);
			break;
		// カレンダ区分が年月である場合
		case TYPE_CALENDAR_MONTH:
			// タイトルセル要素に表示する内容を設定
			title = formatCalendar(dateCalendar, FORMAT_CALENDAR_JP_YEAR);
			break;
		// カレンダ区分が年である場合
		case TYPE_CALENDAR_YEAR:
			// カレンダテーブル要素に日付行(年)を設定
			title = getCalendarFirstYear() + "年～";
			break;
		default:
			// 処理無し
	}
	// タイトルセル要素に表示する内容を取得
	return title;
}

/**
 * 年(カレンダ区分)における最初の表示年を取得する。
 * @return {Number} 最初の表示年
 */
function getCalendarFirstYear() {
	// 年(カレンダ区分)における最初の表示年を取得
	return Math.floor(dateCalendar.getFullYear() * 0.1) * 10;
}

/**
 * カレンダ日付要素をクリックした際の処理を行う。
 * @param event イベントオブジェクト
 */
function onClickCalendar(event) {
	// イベント発生元日付要素から属性値を取得
	var value = getCalendarAttribute(getSrcElement(event));
	// カレンダ区分毎に処理
	switch (strCalendarType) {
		// カレンダ区分が年月日である場合
		case TYPE_CALENDAR_DAY:
			// カレンダ表示日付の日を設定
			dateCalendar.setDate(value);
			break;
		// カレンダ区分が年月である場合
		case TYPE_CALENDAR_MONTH:
			// カレンダ表示日付の月を設定
			dateCalendar.setMonth(value);
			break;
		// カレンダ区分が年である場合
		case TYPE_CALENDAR_YEAR:
			// カレンダ表示日付の年を設定
			dateCalendar.setFullYear(value);
			break;
		default:
			// 処理無し
	}
	// カレンダ入力欄にカレンダ表示日付を設定
	setCalenderInputValue();
	// 追加処理
	onClickCalendarExtra(event);
	// テーブルエレメントを削除
	closeCalendar();
}

/**
 * カレンダ日付要素をクリックした際の追加処理を行う。
 * @param event イベントオブジェクト
 */
function onClickCalendarExtra(event) {
	// 処理無し(必要に応じて各画面で実装)
}

/**
 * カレンダー日付要素に属性値を設定する。
 * @param cellElement カレンダー日付要素
 * @param value       属性値
 */
function setCalendarAttribute(cellElement, value) {
	// カレンダー日付要素に属性値を設定
	cellElement.setAttribute("calendar", value);
}

/**
 * カレンダー日付要素から属性値を取得する。
 * @param cellElement カレンダー日付要素
 * @return {String} 属性値
 */
function getCalendarAttribute(cellElement) {
	// カレンダー日付要素から属性値を取得
	return cellElement.getAttribute("calendar");
}

/**
 * カレンダボタン要素からフォーカスが外れた際の処理を行う。
 * フォーカスの再設定には、イベントハンドラ内では再設定できないため、
 * タイマーを用いている。
 */
function onFocusOutCalendar(event) {
	// カレンダーにマウスが乗っている場合
	if (isMouseOverCalendar) {
		// フォーカスを再設定(カレンダボタン要素)
		setTimeout(function() { setFocus(getCalendarButton()); }, 1); 
		// 処理終了
		return;
	}
	// カレンダー除去
	closeCalendar();
}

/**
 * 月初日を取得する。
 * @param date 日付オブジェクト
 * @return {Date} 月初日日付オブジェクト
 */
function getCalendarFirstDateOfMonth(date) {
	// 月初日を取得
	return new Date(date.getFullYear(), date.getMonth(), 1, 0, 0, 0, 0);
}

/**
 * 月最終日を取得する。
 * @param date 日付オブジェクト
 * @return {Date} 月最終日日付オブジェクト
 */
function getCalendarLastDateOfMonth(date) {
	// 月最終日を取得
	return new Date(date.getFullYear(), date.getMonth() + 1, 0, 0, 0, 0, 0);
}

/**
 * 日付オブジェクトの年を加算する。
 * @param date  日付オブジェクト
 * @param count 加算数
 */
function addCalendarYear(date, count) {
	// 日付オブジェクトが存在しない場合
	if (date == null) {
		// 処理無し
		return;
	}
	// 年を設定
	date.setFullYear(date.getFullYear() + count);
	// 日付として有効でない場合
	if (isValidDate(date) == false) {
		// 年を減算(元に戻す)
		date.setFullYear(date.getFullYear() - count);
	}
}

/**
 * 日付オブジェクトの月を加算する。
 * @param date  日付オブジェクト
 * @param count 加算数
 */
function addCalendarMonth(date, count) {
	// 日付オブジェクトが存在しない場合
	if (date == null) {
		// 処理無し
		return;
	}
	// 月を加算するために1日に設定
	date.setDate(1);
	// 月を加算
	date.setMonth(date.getMonth() + count);
	// 日付として有効でない場合
	if (isValidDate(date) == false) {
		// 月を減算(元に戻す)
		date.setMonth(date.getMonth() - count);
	}
}

/**
 * 日付オブジェクトの日を加算する。
 * @param date  日付オブジェクト
 * @param count 加算数
 */
function addCalendarDay(date, count) {
	// 日付オブジェクトが存在しない場合
	if (date == null) {
		// 処理無し
		return;
	}
	// 月を加算
	date.setDate(date.getDate() + count);
}

/**
 * 日付オブジェクトを文字列で取得する。
 * フォーマット文字列には、YYYY/MM/DD、YYYY年MM月DD日等を指定する。
 * ミリ秒は考慮しない。
 * @param  date   日付オブジェクト
 * @param  format フォーマット文字列
 * @return {String} 日付文字列
 */
function formatCalendar(date, format) {
	// 日付オブジェクトが存在しない場合
	if (date == null) {
		// 空白を取得
		return "";
	}
	// 日付文字列を準備
	var strDate = format;
	// 年月日をそれぞれ置換
	strDate = strDate.replace(/YYYY/g, date.getFullYear());
	strDate = strDate.replace(/MM/g, ("0" + (date.getMonth() + 1)).slice(-2));
	strDate = strDate.replace(/DD/g, ("0" + date.getDate()).slice(-2));
	strDate = strDate.replace(/hh/g, ('0' + date.getHours()).slice(-2));
	strDate = strDate.replace(/mm/g, ('0' + date.getMinutes()).slice(-2));
	strDate = strDate.replace(/ss/g, ('0' + date.getSeconds()).slice(-2));
	return strDate;
}
