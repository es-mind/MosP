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
 * 行NO(譲渡行NO)。
 */
var PRM_TRANSFERRED_ROW_NO = "transferredRowNo";

/**
 * 要素ID(編集テーブル内容)。
 */
var EID_EDIT_TABLE_BODY = "addLeaveBody";

/**
 * 要素名(レコードID)。
 */
var ENM_RECORD_ID = "aryHidPfaHumanConcurrentId";

/**
 * 要素名(有効日モード)。
 */
var ENM_MODE_ACTIVATE_DATE = "modeActivateDateArray";

/**
 * 要素名(開始日(年))。
 */
var ENM_START_YEAR = "aryTxtConcurrentStartYear";

/**
 * 要素名(開始日(月))。
 */
var ENM_START_MONTH = "aryTxtConcurrentStartMonth";

/**
 * 要素名(開始日(日))。
 */
var ENM_START_DAY = "aryTxtConcurrentStartDay";

/**
 * 画面読込時追加処理を行う。
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 対象オブジェクト内のTR要素群を取得
	var trElements = getElementsByTagName(EID_EDIT_TABLE_BODY, TAG_TR);
	var trElementsLength = trElements.length;
	for (var i = 0; i < trElementsLength; i++) {
		// 有効日編集可否設定
		setActivateDateReadOnly(trElements[i]);
	}
	// 削除ボタン利用可否設定
	setDeleteButtonDisabled();
	// 行追加ボタン利用可否設定
	setAddRowButtonDisabled();
	// 選択チェックボックス値(0～)設定
	setSequenceValue("ckbSelect");
	// 行番号設定(1～)
	setRowIndex("RowIndex");
}

/**
 * 対象行の有効日モードを確認し、有効日編集可否を設定する。<br>
 * @param target 対象行TR要素
 */
function setActivateDateReadOnly(target) {
	// 有効日モード取得
	var activateMode = getFormValue(getElementByName(target, TAG_INPUT, ENM_MODE_ACTIVATE_DATE));
	// 設定対象取得
	var year  = getElementByName(target, TAG_INPUT, ENM_START_YEAR );
	var month = getElementByName(target, TAG_INPUT, ENM_START_MONTH);
	var day   = getElementByName(target, TAG_INPUT, ENM_START_DAY  );
	// 有効日モード確認
	if (activateMode == MODE_ACTIVATE_DATE_CHANGING) {
		// 有効日編集可設定
		setReadOnly(year , false);
		setReadOnly(month, false);
		setReadOnly(day  , false);
	} else {
		// 有効日編集不可設定
		setReadOnly(year , true);
		setReadOnly(month, true);
		setReadOnly(day , true);
	}
}

/**
 * 削除ボタン利用可否設定を行う。<br>
 * 選択チェックボックスが一件でもチェックされていれば、削除ボタンが利用可能となる。<br>
 */
function setDeleteButtonDisabled() {
	// チェックボックス確認(削除ボタン利用可否設定)
	if (isCheckBoxChecked("ckbSelect")) {
		// 一行以上、或いは登録済データが存在している場合
		if (getTagCount(EID_EDIT_TABLE_BODY, TAG_TR) > 1 || hasRegistedData()) {
			// 削除ボタン利用可
			setReadOnly("btnDelete", false);
			return true;
		}
	}
	setReadOnly("btnDelete", true);
}

/**
 * 行追加ボタン利用可否設定を行う。<br>
 * 行数が最大行数より少なければ、行追加ボタンが利用可能となる。<br>
 */
function setAddRowButtonDisabled() {
	// 行数確認(行追加ボタン利用可否設定)
	if (getTagCount(EID_EDIT_TABLE_BODY, TAG_TR) < MAX_DETAILS_COUNT) {
		setReadOnly("btnAddRow", false);
	} else {
		setReadOnly("btnAddRow", true);
	}
}

/**
 * 行追加を行う。<br>
 */
function addConcurrentRow() {
	// 行追加
	var trElement = addTableRow(EID_EDIT_TABLE_BODY, null);
	// 行初期化
	initInputValue(trElement);
	// ID等設定
	setListItem(trElement, TAG_INPUT , ENM_START_YEAR );
	setListItem(trElement, TAG_INPUT , ENM_START_MONTH);
	setListItem(trElement, TAG_INPUT , ENM_START_DAY  );
	setListItem(trElement, TAG_INPUT , "aryTxtConcurrentEndYear");
	setListItem(trElement, TAG_INPUT , "aryTxtConcurrentEndMonth");
	setListItem(trElement, TAG_INPUT , "aryTxtConcurrentEndDay");
	setListItem(trElement, TAG_SELECT, "arySectionAbbr");
	setListItem(trElement, TAG_SELECT, "aryPosition");
	setListItem(trElement, TAG_INPUT , "aryTxtRemark");
	// 背景色再設定
	resetFieldsBgColor(trElement);
	// 有効日ボタン取得
	var btnActivateDate = getElementByName(trElement, "button", "btnActivateDate");
	// 有効日ボタンタイトル変更
	setInnerHtml(btnActivateDate, jsActivateDateButtonName);
	// 有効日モード
	var hdnModeActivateDate = getElementByName(trElement, TAG_INPUT, ENM_MODE_ACTIVATE_DATE);
	// 有効日モード設定
	setFormValue(hdnModeActivateDate, MODE_ACTIVATE_DATE_CHANGING);
	// 有効日編集可否設定
	setActivateDateReadOnly(trElement);
	// プルダウン設定
	var pltSection  = getElementByName(trElement, TAG_SELECT, "arySectionAbbr");
	var pltPosition = getElementByName(trElement, TAG_SELECT, "aryPosition"   );
	setSelectOptions(pltSection , new Array(new Array("",jsDefaultPulldown )));
	setSelectOptions(pltPosition, new Array(new Array("",jsDefaultPulldown )));
	// 行番号更新(1～)
	setRowIndex("RowIndex");
	// 選択チェックボックス値設定(0～)
	setSequenceValue("ckbSelect");
	// 行追加ボタン利用可否設定
	setAddRowButtonDisabled();
}

/**
 * リスト内の入力オブジェクトに対してID等を付与する。<br>
 * IDは、name+行番号(0～)。
 * @param target  対象行TR要素
 * @param tagName 取得対象タグ
 * @param name    取得対象名
 */
function setListItem(target, tagName, name) {
	// 設定対象要素取得
	var element = getElementByName(target, tagName, name);
	// 設定対象インデックス取得
	var rowIndex = getIndex(target);
	// ID設定
	element.id = element.name + rowIndex;
	// 変更イベントハンドラ設定
	setOnChangeHandler(element, onChangeFields);
	// 変更確認用初期値設定
	ARY_FORM_VALUE_DEFAULT[element.id] = getFormValue(element);
	// LABEL要素設定
	var labelElements = getElementsByTagName(target, TAG_LABEL);
	var labelElementsLength = labelElements.length;
	for (var i = 0; i < labelElementsLength; i++) {
		var value = labelElements.item(i).getAttribute(ATT_FOR);
		if(value == null){
			continue;
		}
		if (value.indexOf(element.name) != -1) {
			labelElements.item(i).setAttribute(ATT_FOR, element.id);
		}
	}
}

/**
 * 有効日ボタン押下時の追加チェックを行う。<br>
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkExtraForActivate(aryMessage, event) {
	// イベント発生要素取得
	var srcElement = getSrcElement(event);
	// 上位ノード(TD要素)取得
	var tdElement = srcElement.parentNode;
	// 行番号取得(0～)
	var rowIndex = getIndex(tdElement.parentNode);
	// 入力値確認
	inputCheck(tdElement, aryMessage);
	// パラメータ追加
	addParameter(document.form, PRM_TRANSFERRED_ROW_NO, rowIndex);
}

/**
 * 登録ボタン押下時の追加チェックを行う。<br>
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkExtraForRegist(aryMessage, event) {
	// 有効日モード要素群取得
	var elements = document.getElementsByName(ENM_MODE_ACTIVATE_DATE);
	var elementsLength = elements.length;
	for (var i = 0; i < elementsLength; i++) {
		// 有効日モード確認
		if (getFormValue(elements.item(i)) == MODE_ACTIVATE_DATE_CHANGING) {
			// メッセージ追加
			aryMessage.push(jsDefaultPulldown);
			// 有効日要素取得
			var yearElements  = document.getElementsByName(ENM_START_YEAR );
			var monthElements = document.getElementsByName(ENM_START_MONTH);
			var dayElements   = document.getElementsByName(ENM_START_DAY  );
			// 対象有効日着色
			setBgColor(yearElements .item(i), COLOR_FIELD_ERROR);
			setBgColor(monthElements.item(i), COLOR_FIELD_ERROR);
			setBgColor(dayElements  .item(i), COLOR_FIELD_ERROR);
			return false;
		}
	}
}

/**
 * 登録済データ存在確認を行う。<br>
 * @return 確認結果(true：登録済データ有り、false：無し)
 */
function hasRegistedData() {
	// レコード識別ID要素群取得
	var elements = document.getElementsByName(ENM_RECORD_ID);
	var elementsLength = elements.length;
	for (var i = 0; i < elementsLength; i++) {
		// 新規追加行確認
		if (isRecordIdValid(elements.item(i))) {
			// 登録済データ存在
			return true;
		}
	}
	return false;
}

/**
 * 新規追加であり空白である行の削除を行う。<br>
 */
function removeBlankRows() {
	// 行要素群取得
	var rows = getElementsByTagName(EID_EDIT_TABLE_BODY, TAG_TR);
	for (var i = rows.length - 1; i > 0; i--) {
		// レコード識別ID確認(新規追加行なら0か空白)
		if (isRecordIdValid(getElementByName(rows.item(i), TAG_INPUT, ENM_RECORD_ID))) {
			continue;
		}
		// 空白行確認
		if (isElementBlank(rows.item(i)) == false) {
			continue;
		}
		// 行削除
		removeElement(rows.item(i));
	}
}

