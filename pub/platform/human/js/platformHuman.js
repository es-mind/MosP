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
 * 人事管理共通情報社員検索領域要素ID。
 */
var DIV_HUMAN_SEARCH = "divHumanSearch";

/**
 * 人事管理共通情報社員表示領域要素ID。
 */
var DIV_HUMAN_LABEL = "divHumanLabel";

/**
 * 人事管理共通情報における検索社員コードの入力チェックを行う。
 * @param event イベント
 */
function checkCommonSearchEmployee(event) {
	// 入力確認
	return validate(DIV_HUMAN_SEARCH, null);
}

/**
 * 人事管理共通情報における再表示ボタンの入力チェックを行う。
 * @param event イベント
 */
function checkCommonSearchActivateDate(event) {
	// 入力確認
	return validate(DIV_HUMAN_LABEL, null);
}

/**
 * レコード識別IDの妥当性確認を行う。<br>
 * 0または空白の場合にfalseを返す。<br>
 * 兼務、休職情報編集画面で新規追加行を確認する際等に用いる。<br>
 * @param target 対象レコード識別ID要素(StringあるいはObject)
 * @return 確認結果
 */
function isRecordIdValid(target) {
	// レコード識別ID取得
	var recordId = getFormValue(target);
	// レコード識別ID確認
	if (recordId == 0 || recordId == "") {
		return false;
	}
	return true;
}

/**
 * 対象要素内の入力領域が空白かどうかを確認する。<br>
 * 但し、hidden及びcheckboxの要素については確認対象外とする。<br>
 * @param target 対象要素
 * @return 確認結果(true：空白である、false：空白でない)
 */
function isElementBlank(target) {
	// INPUT要素群取得
	var inputElements = getElementsByTagName(target, TAG_INPUT);
	var inputElementsLength = inputElements.length;
	for (var i = 0; i < inputElementsLength; i++) {
		// hidden要素は対象外
		if (inputElements.item(i).type == INPUT_TYPE_HIDDEN) {
			continue;
		}
		// checkbox要素は対象外
		if (inputElements.item(i).type == INPUT_TYPE_CHECK_BOX) {
			continue;
		}
		// 空白確認
		if (getFormValue(inputElements.item(i)) != "") {
			return false;
		}
	}
	// TEXTAREA要素群取得
	var textareaElements = getElementsByTagName(target, TAG_TEXTAREA);
	var textareaElementsLength = textareaElements.length;
	for (var i = 0; i < textareaElementsLength; i++) {
		// 空白確認
		if (getFormValue(textareaElements.item(i)) != "") {
			return false;
		}
	}
	// SELECT要素群取得
	var selectElements = getElementsByTagName(target, TAG_SELECT);
	var selectElementsLength = selectElements.length;
	for (var i = 0; i < selectElementsLength; i++) {
		// 空白確認
		if (getFormValue(selectElements.item(i)) != "") {
			return false;
		}
	}
	return true;
}

/**
 * 取得対象内にある対象タグ名の要素数を取得する。<br>
 * TableBody内のTR要素数を取得することで、行数が分かる。<br>
 * @param target  取得対象(StringあるいはObject)
 * @param tagName 対象タグ名
 * @return 対象タグ名の要素数
 */
function getTagCount(target, tagName) {
	// 対象オブジェクト内のTR要素群を取得
	var elements = getElementsByTagName(target, tagName);
	// 行数取得
	return elements.length;
}

/**
 * 行番号を設定する。<br>
 * クラス名の要素に対して、innerHtmlに1～の行番号を設定する。<br>
 * @param className 設定対象クラス名
 */
function setRowIndex(className) {
	// 設定対象要素群取得
	var elements = getElementsByClass(className);
	// 行番号付加
	var index = 0;
	var elementsLength = elements.length;
	for (var i = 0; i < elementsLength; i++) {
		setInnerHtml(elements[i], ++index);
	}
}

/**
 * 対象入力要素に値(連番0～)を設定する。<br>
 * @param name 対象入力要素名
 */
function setSequenceValue(name) {
	// 対象要素取得
	var elements = document.getElementsByName(name);
	var elementsLength = elements.length;
	// 値設定
	for (var i = 0; i < elementsLength; i++) {
		elements.item(i).value = i
	}
}

/**
 * 順番を取得する。<br>
 * 取得対象の上位要素から、取得対象と同じタグ名で要素を取得し、
 * 取得対象が何番目にあたるのかを返す。<br>
 * @param target 取得対象要素
 * @return 順番
 */
function getIndex(target) {
	// 上位要素からタグ名で要素を取得
	var elements = getElementsByTagName(target.parentNode, target.tagName);
	var elementsLength = elements.length;
	// 要素確認
	for (var i = 0; i < elementsLength; i++) {
		if (elements.item(i) == target) {
			return i;
		}
	}
	return 0;
}

/**
 * チェックボックスがチェックされているかを確認する。<br>
 * 一つでもチェックされていればtrue、
 * 一つもチェックされてなければfalseを返す。
 * @param name 対象チェックボックス要素名
 * @return 確認結果
 */
function isCheckBoxChecked(name) {
	// チェックボックス要素取得
	var elements = document.getElementsByName(name);
	var elementsLength = elements.length;
	// チェック確認
	for (var i = 0; i < elementsLength; i++) {
		if (elements.item(i).checked == true) {
			return true;
		}
	}
	return false;
}
