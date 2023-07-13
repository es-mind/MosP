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
 * 検索条件チェックエラーメッセージ
 */
var MSG_SEARCH_CONDITION = "PFW0234";

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		setReadOnly("txtActivateYear", true);
		setReadOnly("txtActivateMonth", true);
		setReadOnly("txtActivateDay", true);
	} else {
		setDisabled("SearchButton", true);
	}
	if (modeSearchExpansion == "show") {
		show("hidden", "HumanSearchChangeLower", "HumanSearchChangeUpper");
	} else {
		hide("hidden", "HumanSearchChangeLower", "HumanSearchChangeUpper");
	}
}

/**
 * オブジェクト表示
 * @param state 状態
 * @param target1 初期表示オブジェクト
 * @param target2 初期非表示オブジェクト
 * @return 無し
 * @throws 実行時例外
 */
function show(state, target1, target2) { 
	document.getElementById(state).style.display = "block";
	document.getElementById(target1).style.display = "none";
	document.getElementById(target2).style.display = "inline";
	setFormValue("modeSearchExpansion", "show");
}

/**
 * オブジェクト非表示
 * @param state 状態
 * @param target1 初期表示オブジェクト
 * @param target2 初期非表示オブジェクト
 * @return 無し
 * @throws 実行時例外
 */
function hide(state, target1, target2 ){
	document.getElementById(state).style.display = "none";
	document.getElementById(target1).style.display = "inline";
	document.getElementById(target2).style.display = "none";
	setFormValue("modeSearchExpansion", "hidden");
}

/**
 * 検索ボタン押下時の追加チェックを行う。<br>
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkExtra(aryMessage, event) {
	checkSearchCondition(aryMessage);
	// フリーワードの全角スペース→半角スペース変換
	convSbSpace("txtSearchWord");
}

/**
 * 検索条件を確認する
 * @param aryMessage メッセージ配列
 */
function checkSearchCondition(aryMessage) {
	if (!jsSearchConditionRequired) {
		// 検索条件が必須でない場合
		return;
	}
	// 検索条件が必須の場合
	if (hasSearchCondition()) {
		// 検索条件が1つでもある場合
		return;
	}
	// 検索条件がない場合
	aryMessage.push(getMessage(MSG_SEARCH_CONDITION, null));
}

/**
 * 検索条件の有無を確認する
 * @return 検索条件が1つでもある場合true、そうでない場合false
 */
function hasSearchCondition() {
	return !isAllFormEmpty(
			"txtEmployeeCode",
			"txtLastName",
			"pltWorkPlaceAbbr",
			"pltEmploymentName",
			"pltSectionAbbr",
			"pltPositionName",
			"pltState",
			"txtFirstName",
			"txtLastKana",
			"txtFirstKana",
			"txtSearchWord"
	);
}

