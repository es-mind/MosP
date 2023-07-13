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
 * 要素ID(編集テーブル内容)。
 */
var EID_EDIT_TABLE_BODY = "addLeaveBody";

/**
 * 要素名(レコードID)。
 */
var ENM_RECORD_ID = "aryHidPfaHumanSuspension";

/**
 * 画面読込時追加処理を行う。
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
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
 * 行追加を行う。<br>
 */
function addSuspensionRow() {
	// 行追加
	var trElement = addTableRow(EID_EDIT_TABLE_BODY, null);
	// 行初期化
	initInputValue(trElement);
	// ID設定
	setListItem(trElement, TAG_INPUT , "aryTxtSuspensionStartYear");
	setListItem(trElement, TAG_INPUT , "aryTxtSuspensionStartMonth");
	setListItem(trElement, TAG_INPUT , "aryTxtSuspensionStartDay");
	setListItem(trElement, TAG_INPUT , "aryTxtSuspensionEndYear");
	setListItem(trElement, TAG_INPUT , "aryTxtSuspensionEndMonth");
	setListItem(trElement, TAG_INPUT , "aryTxtSuspensionEndDay");
	setListItem(trElement, TAG_INPUT , "aryTxtSuspensionScheduleEndYear");
	setListItem(trElement, TAG_INPUT , "aryTxtSuspensionScheduleEndMonth");
	setListItem(trElement, TAG_INPUT , "aryTxtSuspensionScheduleEndDay");
	setListItem(trElement, TAG_INPUT , "aryTxtSuspensionReason");
	// 背景色再設定
	resetFieldsBgColor(trElement);
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
	element.id = element.name + i;
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
			return;
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
