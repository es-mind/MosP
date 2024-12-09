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
 * 登録/削除確認メッセージ。
 */
var MSG_REGIST_CONFIRMATION = "PFQ1001";

/**
 * 削除チェックメッセージ。
 */
var MSG_DELETE_CONFIRMATION = "PFQ1002";

/**
 * 編集内容破棄確認時のメッセージコード。<br>
 */
var MSG_EDIT_ANNULMENT = "PFQ1003";

/**
 * 複製確認時のメッセージコード。<br>
 */
var MSG_REPLICATION_CONFIRMATION = "PFQ1004";

/**
 * 編集確認時のメッセージコード。<br>
 */
var MSG_EDIT_CONFIRMATION = "PFQ1007";

/**
 * MosP内容領域要素ID。
 */
var DIV_BODY = "divBody";

/**
 * メインメニューID。
 */
var ID_MAIN_MENU = "trMainMenu";

/**
 * サブメニューID。
 */
var ID_SUB_MENU = "divSubMenu";

/**
 * 半角英数字又は記号("_"、"."、"-"、"@")チェックエラーメッセージ。
 */
var MSG_ALP_SIGN_NUM_CHECK_ERROR = "PFW0124";

/**
 * 半角英数字又は記号("_"、"."、"-"、"@")チェックエラーメッセージ。
 */
var MSG_ALP_SIGN_NUM_CHECK_ERROR_AMP = "PFW0125";

/**
 * メールアドレスの形式不正チェックエラーメッセージ。
 */
var MSG_MAIL_ADDRESS_CHECK_ERROR = "PFW0131";

/**
 * パスワード文字種の妥当性が確認できなかった場合のメッセージコード。
 */
var MSG_CHAR_PASSWORD_ERR = "PFW9116";

/**
 * メニュー配列。
 * メニューの大項目の配列がこの配列に格納される。
 */
var ARY_MENU = new Array();

/**
 * 遷移時の画面の大項目メニュー。
 */
var SELECT_MENU;

/**
 * メニュー大項目表示個数。
 */
var LENGTH_MENU = 9;

/**
 * 明細行数限度(リスト編集用)。
 */
var MAX_DETAILS_COUNT = 20;

/**
 * 明細行数限度(リスト編集用)。
 */
var MIN_DETAILS_COUNT = 1;

/**
 * フォームのデフォルト値。<br>
 * 画面表示時の値を保持しておく。
 */
var ARY_FORM_VALUE_DEFAULT = new Array();

/**
 * 画面背景反転色。
 */
var COLOR_MAINMENU_REVERS = "lightgrey";

/**
 * イベント種別(keydown)。
 */
var EVENT_TYPE_KEYDOWN = "keydown";

/**
 * キーコード(Backspace)。
 */
var KEY_CODE_BACK_SPACE = 8;

/**
 * キーコード(←)。
 */
var KEY_CODE_LEFT = 37;

/**
 * キーコード(F5)。
 */
var KEY_CODE_F5 = 116;

/**
 * 画面読込イベントハンドラ。
 * @param e 画面読込イベント
 */
function onLoad(e) {
	try {
		// キーダウンイベントを追加
		if (window.document.addEventListener) {
			window.document.addEventListener(EVENT_TYPE_KEYDOWN, keyDown);
		} else if (window.document.attachEvent) {
			window.document.attachEvent("onkeydown", keyDown);
		}
		// 一覧背景色設定
		setTableColor("list");
		// 項目長設定
		setMaxLength("CompanyCodeRequiredTextBox", 50);
		setMaxLength("LoginIdTextBox", 50);
		setMaxLength("LoginPassTextBox", 50);
		setMaxLength("PassChangeUserIdTextBox", 50);
		setMaxLength("PassChangeMailAddressTextBox", 50);
		setMaxLength("UserId50RequiredTextBox", 50);
		setMaxLength("UserId50TextBox", 50);
		setMaxLength("Code2RequiredTextBox", 2);
		setMaxLength("Code2TextBox", 2);
		setMaxLength("Code10RequiredTextBox", 10);
		setMaxLength("Code10TextBox", 10);
		setMaxLength("Code11RequiredTextBox", 11);
		setMaxLength("Code11TextBox", 11);
		setMaxLength("Code50RequiredTextBox", 50);
		setMaxLength("Code50TextBox", 50);
		setMaxLength("Name3RequiredTextBox", 3);
		setMaxLength("Name3TextBox", 3);
		setMaxLength("Name5RequiredTextBox", 5);
		setMaxLength("Name5TextBox", 5);
		setMaxLength("Name6RequiredTextBox", 6);
		setMaxLength("Name6TextBox", 6);
		setMaxLength("Name7TextBox", 7);
		setMaxLength("Name8TextBox", 8);
		setMaxLength("Name11RequiredTextBox", 11);
		setMaxLength("Name11TextBox", 11);

		setMaxLength("Name10RequiredTextBox", 10);
		setMaxLength("Name10TextBox", 10);
		setMaxLength("Name10-30RequiredTextBox", 30);
		setMaxLength("Name10-30TextBox", 30);
		setMaxLength("Name12RequiredTextBox", 12);
		setMaxLength("Name12TextBox", 12);
		setMaxLength("Name14TextBox", 14);
		setMaxLength("Name15RequiredTextBox", 15);
		setMaxLength("Name15TextBox", 15);
		setMaxLength("Name16RequiredTextBox", 16);
		setMaxLength("Name21TextBox", 21);
		setMaxLength("Name15-30RequiredTextBox", 30);
		setMaxLength("Name15-30TextBox", 30);
		setMaxLength("Name15-40RequiredTextBox", 40);
		setMaxLength("Name15-40TextBox", 40);
		setMaxLength("Name10-50TextBox", 50);
		setMaxLength("Name20RequiredTextBox", 20);
		setMaxLength("Name20TextBox", 20);
		setMaxLength("Name21-50RequiredTextBox", 50);
		setMaxLength("Name21-50TextBox", 50);
		setMaxLength("Name25-50RequiredTextBox", 50);
		setMaxLength("Name25-50TextBox", 50);
		setMaxLength("Name25RequiredTextBox", 25);
		setMaxLength("Name25TextBox", 25);
		setMaxLength("Name30RequiredTextBox", 30);
		setMaxLength("Name30TextBox", 30);
		setMaxLength("Name32RequiredTextBox", 32);
		setMaxLength("Name33RequiredTextBox", 33);
		setMaxLength("Name40RequiredTextBox", 40);
		setMaxLength("Name40TextBox", 40);
		setMaxLength("Name50RequiredTextBox", 50);
		setMaxLength("Name50TextBox", 50);
		setMaxLength("Name60RequiredTextBox", 60);
		setMaxLength("Name60TextBox", 60);
		setMaxLength("Name64TextBox", 64);
		setMaxLength("CodeCsvTextBox", 2000);
		setMaxLength("Kana10RequiredTextBox", 10);
		setMaxLength("Kana10TextBox", 10);
		setMaxLength("Kana15RequiredTextBox", 15);
		setMaxLength("Kana15TextBox", 15);
		setMaxLength("Byte6RequiredTextBox", 6);
		setMaxLength("Byte6TextBox", 6);
		setMaxLength("Number1RequiredTextBox", 1);
		setMaxLength("Number1TextBox", 1);
		setMaxLength("Number2RequiredTextBox", 2);
		setMaxLength("Number2TextBox", 2);
		setMaxLength("Number3RequiredTextBox", 3);
		setMaxLength("Number3TextBox", 3);
		setMaxLength("Number4RequiredTextBox", 4);
		setMaxLength("Number4TextBox", 4);
		setMaxLength("Number5RequiredTextBox", 5);
		setMaxLength("Number5TextBox", 5);
		setMaxLength("Number6RequiredTextBox", 6);
		setMaxLength("Number6TextBox", 6);
		setMaxLength("Number7RequiredTextBox", 7);
		setMaxLength("Number8RequiredTextBox", 8);
		setMaxLength("Number8TextBox", 8);
		setMaxLength("Number9TextBox", 9);
		setMaxLength("Number10TextBox", 10);
		setMaxLength("Numeric4RequiredTextBox", 4);
		setMaxLength("Numeric5TextBox", 5);
		setMaxLength("ImeOff6TextBox", 6);
		setMaxLength("Name100TextArea", 100);
		setMaxLength("Name120TextArea", 120);
		setMaxLength("MailAddressTextBox", 60);
		setMaxLength("Calendar4RequiredTextBox", 4);
		setMaxLength("Calendar7RequiredTextBox", 7);
		setMaxLength("Calendar10RequiredTextBox", 10);
		setMaxLength("Calendar4TextBox", 4);
		setMaxLength("Calendar7TextBox", 7);
		setMaxLength("Calendar10TextBox", 10);
		// フィールドにイベントを設定
		setFieldsEvent(DIV_BODY);
		// メニュー設定
		setMenu(SELECT_MENU);
		// スクロール
		if (typeof(jsScrollTo) != "undefined" && jsScrollTo != "") {
			scrollToTarget(jsScrollTo);
		}
		// 画面読込時モジュール追加処理
		onLoadModuleExtra();
		// 画面読込時処理
		onLoadExtra();
		// 画面読込時追加処理
		onLoadAddonExtra();
		// 画面読込時追加処理(配列)
		onLoadArray();
		// 問合フラグ戻し
		inquiring = false;
	} catch (e) {
		handleException(e);
	}
}

/**
 * 画面読込時モジュール追加処理。
 */
function onLoadModuleExtra(){}

/**
 * 画面読込時処理。
 */
function onLoadExtra(){}

/**
 * 画面読込時追加処理。
 */
function onLoadAddonExtra(){}

/**
 * 画面読込時追加処理(配列)。
 */
function onLoadArray() {
	// 画面読込時追加処理(配列)が定義されていない場合
	if(typeof(aryOnLoadName) == "undefined") {
		// 処理無し
		return;
	}
	// 画面読込時追加処理(配列)を実行
	exexuteFunctions(aryOnLoadName);
}

/**
 * 関数を実行する。
 * @param functions 配列(関数名)
 */
function exexuteFunctions(functions) {
	// 引数が配列でない場合
	if (Array.isArray(functions) == false) {
		// 処理無し
		return;
	}
	// 配列長を取得
	var aryLength = functions.length;
	// 関数名毎に処理
	for (var i = 0; i < aryLength; i++) {
		// 関数を実施
		exexuteFunctionByName(functions[i]);
	}
}

/**
 * 関数を実行する。
 * @param functionName 関数名
 */
function exexuteFunctionByName(functionName) {
	// Windowオブジェクトに関数名の要素が存在する場合
	if (typeof window[functionName] === 'function') {
		// 関数を取得
		var functionInstance = window[functionName];
		// 関数に引数が無い場合
		if (functionInstance.length == 0) {
			// 関数を実行
			functionInstance.call();
		}
	}
}

/**
 * 追加チェック処理(配列)を実行する。
 * @param functions  配列(関数)
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function executeCheckExtras(functions, aryMessage, event) {
	// 追加チェック処理(配列)長を取得
	var aryLength = functions.length;
	// 追加チェック処理(配列)毎に処理
	for (var i = 0; i < aryLength; i++) {
		// 追加チェック処理を取得
		var checkExtraFunction = functions[i];
		// 追加チェック処理を実施
		checkExtraFunction(aryMessage, event);
	}
}

/**
 * キー押下イベントハンドラ。
 * @param e キー押下イベント
 */
function keyDown(e) {
	// Backspaceが押された場合
	if (e.keyCode == KEY_CODE_BACK_SPACE) {
		// 押された際にフォーカスのあったオブジェクトを取得
		var objTarget = getSrcElement(e);
		// オブジェクトのタグ名を取得
		var nodeName = objTarget.nodeName;
		// タグ名がINPUTの場合
		if (nodeName == TAG_INPUT) {
			// タイプを取得
			var type = objTarget.type;
			// テキストボックスかパスワードの場合
			if (type == INPUT_TYPE_TEXT || type == INPUT_TYPE_PASSWORD) {
				// 処理無し(Backspaceは有効)
				return;
			}
		}
		// タグ名がTEXTAREAの場合
		if (nodeName == TAG_TEXTAREA) {
			// 処理無し(Backspaceは有効)
			return;
		}
		// イベントのデフォルトアクションをキャンセル
		priventEventDefault(e);
	}
	// ←が押された場合
	if (e.keyCode == KEY_CODE_LEFT) {
		// ALTキーが押されている場合
		if (e.altKey) {
			// イベントのデフォルトアクションをキャンセル
			priventEventDefault(e);
		}
	}
	// F5が押された場合
	if (e.keyCode == KEY_CODE_F5) {
		// イベントのデフォルトアクションをキャンセル
		priventEventDefault(e);
		e.keyCode = null;
	}
}

/**
 * イベントのデフォルトアクションをキャンセルする。<br>
 * @param e イベント
 */
function priventEventDefault(e) {
	// preventDefaultが使える場合
	if (e.preventDefault) {
		// preventDefaultでイベントのデフォルトアクションをキャンセル
		e.preventDefault();
		return;
	}
	// イベントのデフォルトアクションをキャンセル
	e.returnValue = false;
}

/**
 * 変更箇所チェックを行った後、パラメータを付加して、リクエストを送信する。<br>
 * 画面遷移時、ソート時等に用いる。<br>
 * 追加処理関数がfalseを返した場合、処理が中断される。<br>
 * @param event          イベントオブジェクト
 * @param checkTarget    変更チェック対象(null：チェックを行わない、""：全体をチェック)
 * @param objExtraFunc   追加処理関数オブジェクト(null：追加処理無し)
 * @param transferParams パラメータ配列(一次元：パラメータ, 値, パラメータ, 値, ・・・)
 * @param cmd            コマンド
 * @return 無し
 */
function submitTransfer(event, checkTarget, objExtraFunc, transferParams, cmd) {
	// 変更箇所チェック
	if (checkEdit(checkTarget) == false) {
		if (confirm(getMessage(MSG_EDIT_ANNULMENT, null)) == false) {
			return;
		}
	}
	// 追加処理確認
	if (objExtraFunc != null) {
		// 追加処理実施
		if (objExtraFunc(event) == false) {
			return;
		}
	}
	// パラメータ追加
	if (transferParams instanceof Array) {
		var transferParamsLength = transferParams.length;
		for (var i = 0; i < transferParamsLength; i++) {
			addParameter(document.form, transferParams[i], transferParams[++i]);
		}
	}
	// リクエスト送信
	doSubmit(document.form, cmd);
}

/**
 * 入力チェックを行った後、リクエストを送信する。<br>
 * 検索、有効日決定時等に用いる。<br>
 * @param event          イベントオブジェクト
 * @param validateTarget 入力チェック対象(null：チェックを行わない、""：全体をチェック)
 * @param objExtraCheck  追加チェック関数オブジェクト(null：追加チェック無し)
 * @param cmd            コマンド
 * @return 無し
 */
function submitForm(event, validateTarget, objExtraCheck, cmd) {
	// 入力チェック
	if (validate(validateTarget, objExtraCheck, event)) {
		// リクエスト送信
		doSubmit(document.form, cmd);
	}
}

/**
 * 入力チェックを行った後、更新系確認メッセージを出し、リクエストを送信する。<br>
 * データ登録、更新時等に用いる。<br>
 * @param event          イベントオブジェクト
 * @param validateTarget 入力チェック対象(null：チェックを行わない、""：全体をチェック)
 * @param objExtraCheck  追加チェック関数オブジェクト(null：追加チェック無し)
 * @param cmd            コマンド
 * @return 無し
 */
function submitRegist(event, validateTarget, objExtraCheck, cmd) {
	// 入力チェック
	if (validate(validateTarget, objExtraCheck, event)) {
		// 更新系確認メッセージ
		if (confirm(getMessage(MSG_REGIST_CONFIRMATION, trimAll(getInnerHtml(getSrcElement(event)))))) {
			// リクエスト送信
			doSubmit(document.form, cmd);
		}
	}
}

/**
 * 入力チェックを行った後、更新系確認メッセージを出し、リクエストを送信する。<br>
 * データ登録、更新時等に用いる。<br>
 * @param event          イベントオブジェクト
 * @param validateTarget 入力チェック対象(null：チェックを行わない、""：全体をチェック)
 * @param objExtraCheck  追加チェック関数オブジェクト(null：追加チェック無し)
 * @param objExtraFunc   追加処理関数オブジェクト(null：追加処理無し)
 * @param cmd            コマンド
 * @return 無し
 */
function submitRegistExtra(event, validateTarget, objExtraCheck, objExtraFunc, cmd) {
	// 入力チェック
	if (!validate(validateTarget, objExtraCheck, event)) {
		return;
	}
	// 追加処理確認
	if (objExtraFunc != null) {
		// 追加処理実施
		if (objExtraFunc(event) == false) {
			return;
		}
	}
	// 更新系確認メッセージ
	if (confirm(getMessage(MSG_REGIST_CONFIRMATION, trimAll(getInnerHtml(getSrcElement(event)))))) {
		// リクエスト送信
		doSubmit(document.form, cmd);
	}
}

/**
 * 入力チェックを行った後、削除系確認メッセージを出し、リクエストを送信する。<br>
 * データ削除時等に用いる。<br>
 * @param event          イベントオブジェクト
 * @param validateTarget 入力チェック対象(null：チェックを行わない、""：全体をチェック)
 * @param objExtraCheck  追加チェック関数オブジェクト(null：追加チェック無し)
 * @param cmd            コマンド
 * @return 無し
 */
function submitDelete(event, validateTarget, objExtraCheck, cmd) {
	// 入力チェック
	if (validate(validateTarget, objExtraCheck, event)) {
		// 更新系確認メッセージ
		if (confirm(getMessage(MSG_DELETE_CONFIRMATION, trimAll(getInnerHtml(getSrcElement(event)))))) {
			// リクエスト送信
			doSubmit(document.form, cmd);
		}
	}
}

/**
 * パラメータを付加して、リクエストを送信する。<br>
 * ファイル出力時等に用いる。<br>
 * 追加処理関数がfalseを返した場合、処理が中断される。<br>
 * @param event          イベントオブジェクト
 * @param objExtraFunc   追加処理関数オブジェクト(null：追加処理無し)
 * @param transferParams パラメータ配列(一次元：パラメータ, 値, パラメータ, 値, ・・・)
 * @param cmd            コマンド
 * @return 無し
 */
function submitFile(event, objExtraFunc, transferParams, cmd) {
	// 追加処理確認
	if (objExtraFunc != null) {
		// 追加処理実施
		if (objExtraFunc(event) == false) {
			return;
		}
	}
	// パラメータ追加
	if (transferParams instanceof Array) {
		var transferParamsLength = transferParams.length;
		for (var i = 0; i < transferParamsLength; i++) {
			addParameter(document.form, transferParams[i], transferParams[++i]);
		}
	}
	// リクエスト送信
	doSubmitForFile(document.form, cmd);
}

/**
 * 入力チェックを行った後、リクエストを送信する。<br>
 * ファイルアップロード時等に用いる。<br>
 * @param event          イベントオブジェクト
 * @param validateTarget 入力チェック対象(null：チェックを行わない、""：全体をチェック)
 * @param objExtraCheck  追加チェック関数オブジェクト(null：追加チェック無し)
 * @param cmd            コマンド
 * @return 無し
 */
function submitFormMulti(event, validateTarget, objExtraCheck, cmd) {
	// 入力チェック
	if (validate(validateTarget, objExtraCheck, event)) {
		// 更新系確認メッセージ
		if (confirm(getMessage(MSG_REGIST_CONFIRMATION, trimAll(getInnerHtml(getSrcElement(event)))))) {
			// リクエスト送信
			doSubmitForMulti(document.form, cmd);
		}
	}
}

/**
 * 妥当性の確認を行う。
 * @param target        対象範囲(null：チェックを行わない、""：全体をチェック)
 * @param objExtraCheck 追加チェック関数オブジェクト(null：追加チェック無し)
 * @param event         イベントオブジェクト
 * @return チェック結果(true：OK、false：NG)
 */
function validate(target, objExtraCheck, event) {
	// メッセージ配列及び対象範囲要素準備
	var aryMessage = new Array();
	var objTarget = null;
	// 対象範囲確認
	if (target == null) {
		// 対象範囲要素無し
	} else if (target == "") {
		// 対象範囲要素取得(全体)
		objTarget = document.form;
	} else {
		// 対象範囲要素取得
		objTarget = getObject(target);
	}
	// 入力チェック
	inputCheck(objTarget, aryMessage);
	// 追加チェック確認
	if (objExtraCheck != null) {
		// 追加チェック実施
		objExtraCheck(aryMessage, event);
	}
	if (aryMessage.length == 0) {
		return true;
	} else {
		showMessage(aryMessage);
		return false;
	}
}

/**
 * 入力値の確認を行う。<br>
 * フィールド背景色の初期化、テキストボックス前後空白除去、
 * クラスによる入力チェック、及び日付妥当性チェックを行う。<br>
 * 入力チェックでエラーがあった場合、メッセージ配列にメッセージが追加される。<br>
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage メッセージ配列
 */
function inputCheck(target, aryMessage) {
	// 対象範囲要素取得
	var objTarget = getObject(target);
	// 対象範囲確認
	if (objTarget == null) {
		return
	}
	// テキストボックス前後空白除去
	trimTextValue(objTarget);
	// フィールド背景色初期化
	resetFieldsBgColor(objTarget);
	// クラスによる入力チェック
	inputCheckForClass(objTarget, aryMessage);
	// 追加クラスによる入力チェック
	inputCheckForExtraClass(objTarget, aryMessage);
	// 日付チェック
	checkDates(objTarget, aryMessage);
}

/**
 * 確認対象内の入力項目に対して、クラスによる入力チェックを行う。<br>
 * 入力チェックでエラーがあった場合、メッセージ配列にメッセージが追加される。<br>
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage メッセージ配列
 */
function inputCheckForClass(target, aryMessage) {
	// 確認対象要素群取得
	var elements = getElementsByTagName(target, "*");
	var elementsLength = elements.length;
	// クラスによる入力チェック
	for (var i = 0; i < elementsLength; i++) {
		switch (elements.item(i).className) {
		case "CompanyCodeRequiredTextBox":
		case "LoginIdTextBox":
			checkRequired(elements.item(i), aryMessage);
			checkUserId(elements.item(i), aryMessage);
			break;
		case "LoginPassTextBox":
			checkRequired(elements.item(i), aryMessage);
			break;
		case "PassChangeUserIdTextBox":
			checkUserId(elements.item(i), aryMessage);
			break;
		case "PassChangeMailAddressTextBox":
			checkMailAddress(elements.item(i), aryMessage);
			break;
		case "UserId50RequiredTextBox":
			checkRequired(elements.item(i), aryMessage);
		case "UserId50TextBox":
			checkUserId(elements.item(i), aryMessage);
			break;
		case "Code2RequiredTextBox":
		case "Code10RequiredTextBox":
		case "Code11RequiredTextBox":
		case "Code50RequiredTextBox":
			checkRequired(elements.item(i), aryMessage);
		case "Code2TextBox":
		case "Code10TextBox":
		case "Code11TextBox":
		case "Code50TextBox":
			checkCode(elements.item(i), aryMessage);
			break;
		case "Name3RequiredTextBox":
		case "Name5RequiredTextBox":
		case "Name6RequiredTextBox":
		case "Name10RequiredTextBox":
		case "Name11RequiredTextBox":
		case "Name12RequiredTextBox":
		case "Name15RequiredTextBox":
		case "Name20RequiredTextBox":
		case "Name25RequiredTextBox":
		case "Name10-30RequiredTextBox":
		case "Name15-30RequiredTextBox":
		case "Name30RequiredTextBox":
		case "Name15-40RequiredTextBox":
		case "Name21-50RequiredTextBox":
		case "Name25-50RequiredTextBox":
		case "Name32RequiredTextBox":
		case "Name33RequiredTextBox":
		case "Name40RequiredTextBox":
		case "Name50RequiredTextBox":
		case "Name60RequiredTextBox":
		case "InactiveteRequiredPullDown":
		case "StateRequiredPullDown":
		case "ConditionPullDown":
		case "RangePullDown":
			checkRequired(elements.item(i), aryMessage);
			break;
		case "Kana10RequiredTextBox":
		case "Kana15RequiredTextBox":
			checkRequired(elements.item(i), aryMessage);
		case "Kana10TextBox":
		case "Kana15TextBox":
			convSbSpace(elements.item(i));
			convSbKana(elements.item(i));
			checkKana(elements.item(i), aryMessage);
			break;
		case "Number1RequiredTextBox":
		case "Number2RequiredTextBox":
		case "Number3RequiredTextBox":
		case "Number4RequiredTextBox":
		case "Number5RequiredTextBox":
		case "Number6RequiredTextBox":
		case "Number7RequiredTextBox":
		case "Number8RequiredTextBox":
			checkRequired(elements.item(i), aryMessage);
		case "Number1TextBox":
		case "Number2TextBox":
		case "Number3TextBox":
		case "Number4TextBox":
		case "Number5TextBox":
		case "Number6TextBox":
		case "Number8TextBox":
		case "Number9TextBox":
		case "Number10TextBox":
			checkNumber(elements.item(i), aryMessage);
			break;
		case "Numeric4RequiredTextBox":
			checkRequired(elements.item(i), aryMessage);
			checkNumeric(elements.item(i), aryMessage);
			break;
		case "Numeric5TextBox":
			checkNumeric(elements.item(i), aryMessage);
			break;
		case "Byte6RequiredTextBox":
			checkRequired(elements.item(i), aryMessage);
		case "Byte6TextBox":
			checkByteLength(elements.item(i), 6, aryMessage);
			break;
		case "Name120TextArea":
			checkMaxLength(elements.item(i), 120, aryMessage);
			break;
		case "MailAddressTextBox":
			checkMaxLength(elements.item(i), 60, aryMessage);
			checkMailAddress(elements.item(i), aryMessage);
			break;
		case "Calendar4RequiredTextBox":
			checkRequired(elements.item(i), aryMessage);
		case "Calendar4TextBox":
			checkDateYear(elements.item(i), aryMessage);
			break;
		case "Calendar7RequiredTextBox":
			checkRequired(elements.item(i), aryMessage);
		case "Calendar7TextBox":
			checkCalendar(elements.item(i), true, aryMessage)
			break;
		case "Calendar10RequiredTextBox":
			checkRequired(elements.item(i), aryMessage);
		case "Calendar10TextBox":
			checkCalendar(elements.item(i), false, aryMessage)
			break;
		default:
			break;
		}
	}
}

/**
 * 文字列タイプ確認(半角英数字 + "_"、"."、"-"、"@")し、
 * メッセージを出力する。<br>
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage エラーメッセージ格納配列
 * @return 無し
 * @throws 実行時例外
 */
function checkUserId(target, aryMessage) {
	var rep = getLabel(target);
	if (!checkUserIdNoMsg(target)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		if (rep == "") {
			aryMessage.push(getMessage(MSG_ALP_SIGN_NUM_CHECK_ERROR, null));
		} else {
			aryMessage.push(getMessage(MSG_ALP_SIGN_NUM_CHECK_ERROR_AMP, rep));
		}
	}
}

/**
 * 文字列タイプ確認(半角英数字+ "_"、"."、"-"、"@")
 * @param target 確認対象(StringあるいはObject)
 * @return 確認結果(true：OK、false：NG)
 * @throws 実行時例外
 */
function checkUserIdNoMsg(target) {
	if (getFormValue(target).match(/[^-_.@A-Za-z0-9]+/)) {
		return false;
	} else {
		return true;
	}
}

/**
 * 文字列タイプ確認(半角英数字 + "_"、"."、"-"、@は必須)
 * メッセージを出力する。
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage エラーメッセージ格納配列
 * @return 無し
 * @throws 実行時例外
 */
function checkMailAddress(target, aryMessage) {
	var rep = getLabel(target);
	if (!checkMailAddressNoMsg(target)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		if (rep == "") {
			// エラーメッセージ追加
			aryMessage.push(getMessage(MSG_MAIL_ADDRESS_CHECK_ERROR, null));
		} else {
			aryMessage.push(getMessage(MSG_MAIL_ADDRESS_CHECK_ERROR, null));
		}
	}
}

/**
 * 文字列タイプ確認(半角英数字+ "_"、"."、"-"、@は必須)(メッセージ無し)
 * @param target 確認対象(StringあるいはObject)
 * @return 確認結果(true：OK、false：NG)
 * @throws 実行時例外
 */
function checkMailAddressNoMsg(target) {
	if (getFormValue(target) == "") {
		return true;
	}
	if (getFormValue(target).match(/[!#-9A-~]+@+[a-z0-9]+.+[^.]$/i)) {
		return true;
	} else {
		return false;
	}
}

/**
 * パスワードの確認を行う。
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage エラーメッセージ格納配列
 */
function checkPassword(target, aryMessage) {
	// 最低文字数確認
	if (typeof(minPassword) != "undefined" && minPassword != "") {
		checkMinLength(target, minPassword, aryMessage);
	}
	// 文字種確認
	if (typeof(charPassword) != "undefined" && charPassword != "") {
		checkCharPassword(target, charPassword, aryMessage);
	}
}

/**
 * 文字形式の確認(パスワード用)を行う。
 * @param target 確認対象(StringあるいはObject)
 * @param reg    正規表現
 * @param aryMessage メッセージ配列
 */
function checkCharPassword(target, reg, aryMessage) {
	if (!checkRegexNoMsg(target, reg)) {
		if (aryMessage.length == 0) {
			setFocus(target);
		}
		setBgColor(target, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_CHAR_PASSWORD_ERR , ""));
	}
}

/**
 * 追加クラスによる入力チェックを行う。<br>
 * モジュールのJavaScriptファイルで上書きして
 * 利用することを、想定している。<br>
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage メッセージ配列
 */
function inputCheckForExtraClass(target, aryMessage) {
	// 処理無し
}

/**
 * ノード内のテキストボックスの値に対し、前後の空白を除く。<br>
 * @param target 確認対象(StringあるいはObject)
 */
function trimTextValue(target) {
	// INPUT要素群取得
	var inputNodeList = getElementsByTagName(target, TAG_INPUT);
	var inputNodeListLength = inputNodeList.length;
	for (var i = 0; i < inputNodeListLength; i++) {
		// 要素取得
		var element = inputNodeList.item(i);
		// 要素確認
		if (element.type != INPUT_TYPE_TEXT) {
			continue;
		}
		// 前後の空白除去
		element.value = trim(element.value);
	}
}

/**
 * ノード内の日付チェックを行う。<br>
 * 日付は、idで判断する。<br>
 * 〇〇Yearが存在した場合、〇〇Month、〇〇Dayを検索する。<br>
 * 全て存在しており、一つでも入力されていたら日付の妥当性確認を行う。<br>
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage メッセージ配列
 */
function checkDates(target, aryMessage) {
	// 検索対象文字列宣言
	var YEAR_NAME  = "Year";
	var MONTH_NAME = "Month";
	var DAY_NAME   = "Day";
	// INPUT要素群取得
	var inputNodeList = getElementsByTagName(target, TAG_INPUT);
	var inputNodeListLength = inputNodeList.length;
	for (var i = 0; i < inputNodeListLength; i++) {
		// 要素取得
		var element = inputNodeList.item(i);
		var elementName = new String(element.name);
		// 要素name確認
		if (elementName.lastIndexOf(YEAR_NAME) != elementName.length - YEAR_NAME.length) {
			continue;
		}
		// 月、日name作成
		var monthName = elementName.replace(YEAR_NAME, MONTH_NAME);
		var dayName   = elementName.replace(YEAR_NAME, DAY_NAME);
		// 月、日要素準備
		var monthElement = getElementByName(element.parentNode, TAG_INPUT, monthName);
		var dayElement   = getElementByName(element.parentNode, TAG_INPUT, dayName);
		// 月、日要素確認
		if (monthElement == null || dayElement == null) {
			continue;
		}
		// 入力確認
		if (getFormValue(element) == "" && getFormValue(monthElement) == "" && getFormValue(dayElement) == "") {
			continue;
		}
		// 日付チェック
		checkDate(element, monthElement, dayElement, aryMessage);
	}
}

/**
 * 背景色を変更する。
 * @param target 対象
 */
function changeFieldBgColor(target) {
	var objTarget = getObject(target);
	if (ARY_FORM_VALUE_DEFAULT[objTarget.id] == getFormValue(objTarget)) {
		// 背景色変更
		setBgColor(objTarget, COLOR_FIELD_NORMAL);
	} else {
		// 背景色変更
		setBgColor(objTarget, COLOR_FIELD_CHANGE);
	}
}

/**
 * 背景色をリセットする。
 * @param target 初期化対象(StringあるいはObject)
 */
function resetFieldsBgColor(target) {
	// INPUT要素群取得
	var inputElements = getElementsByTagName(target, TAG_INPUT);
	var inputElementsLength = inputElements.length;
	// 背景色リセット
	for (var i = 0; i < inputElementsLength; i++) {
		// type="text"でない場合
		if (inputElements.item(i).type != INPUT_TYPE_TEXT) {
			continue;
		}
		// idが設定されていない場合
		if (inputElements.item(i).id == "") {
			continue;
		}
		// disabledである場合
		if (inputElements.item(i).disabled) {
			continue;
		}
		// 背景色変更
		if (ARY_FORM_VALUE_DEFAULT[inputElements.item(i).id] != getFormValue(inputElements.item(i))) {
			setBgColor(inputElements.item(i), COLOR_FIELD_CHANGE);
		} else {
			setBgColor(inputElements.item(i), COLOR_FIELD_NORMAL);
		}
	}
	// TEXTAREA要素群取得
	var textareaElements = getElementsByTagName(target, TAG_TEXTAREA);
	var textareaElementsLength = textareaElements.length;
	// 背景色リセット
	for (var i = 0; i < textareaElementsLength; i++) {
		// idが設定されていない場合
		if (textareaElements.item(i).id == "") {
			continue;
		}
		// disabledである場合
		if (textareaElements.item(i).disabled) {
			continue;
		}
		// 背景色変更
		if (ARY_FORM_VALUE_DEFAULT[textareaElements.item(i).id] != getFormValue(textareaElements.item(i))) {
			setBgColor(textareaElements.item(i), COLOR_FIELD_CHANGE);
		} else {
			setBgColor(textareaElements.item(i), COLOR_FIELD_NORMAL);
		}
	}
	// SELECT要素群取得
	var selectElements = getElementsByTagName(target, TAG_SELECT);
	var selectElementsLength = selectElements.length;
	// 背景色リセット
	for (var i = 0; i < selectElementsLength; i++) {
		// idが設定されていない場合
		if (selectElements.item(i).id == "") {
			continue;
		}
		// 背景色変更
		if (ARY_FORM_VALUE_DEFAULT[selectElements.item(i).id] != getFormValue(selectElements.item(i))) {
			setBgColor(selectElements.item(i), COLOR_FIELD_CHANGE);
		} else {
			setBgColor(selectElements.item(i), COLOR_FIELD_NORMAL);
		}
	}
}

/**
 * 背景色を通常にする。
 * @param target 初期化対象(StringあるいはObject)
 */
function normalFieldsBgColor(target) {
	// INPUT要素群取得
	var inputElements = getElementsByTagName(target, TAG_INPUT);
	var inputElementsLength = inputElements.length;
	// 背景色リセット
	for (var i = 0; i < inputElementsLength; i++) {
		// type="text"でない場合
		if (inputElements.item(i).type != INPUT_TYPE_TEXT) {
			continue;
		}
		// disabledである場合
		if (inputElements.item(i).disabled) {
			continue;
		}
		// 背景色変更
		setBgColor(inputElements.item(i), COLOR_FIELD_NORMAL);
	}
	// TEXTAREA要素群取得
	var textareaElements = getElementsByTagName(target, TAG_TEXTAREA);
	var textareaElementsLength = textareaElements.length;
	// 背景色リセット
	for (var i = 0; i < textareaElementsLength; i++) {
		// disabledである場合
		if (textareaElements.item(i).disabled) {
			continue;
		}
		// 背景色変更
		setBgColor(textareaElements.item(i), COLOR_FIELD_NORMAL);
	}
	// SELECT要素群取得
	var selectElements = getElementsByTagName(target, TAG_SELECT);
	var selectElementsLength = selectElements.length;
	// 背景色リセット
	for (var i = 0; i < selectElementsLength; i++) {
		// 背景色変更
		setBgColor(selectElements.item(i), COLOR_FIELD_NORMAL);
	}
}

/**
 * 編集有無チェックを行う。
 * @param target 対象範囲(null：チェックを行わない、""：全体をチェック)
 * @return チェック結果(true：編集無し、false：編集有り)
 */
function checkEdit(target) {
	var objTarget = null;
	// 対象範囲確認
	if (target == null) {
		return true;
	} else if (target == "") {
		objTarget = document.form;
	} else {
		objTarget = getObject(target);
	}
	// 対象オブジェクト確認
	if (objTarget == null) {
		return true;
	}
	// INPUT要素群取得
	var inputElements = getElementsByTagName(objTarget, TAG_INPUT);
	var inputElementsLength = inputElements.length;
	// 編集有無確認
	for (var i = 0; i < inputElementsLength; i++) {
		if (inputElements.item(i).type != INPUT_TYPE_TEXT) {
			continue;
		}
		if (ARY_FORM_VALUE_DEFAULT[inputElements.item(i).id] != getFormValue(inputElements.item(i))) {
			return false;
		}
	}
	// TEXTAREA要素群取得
	var textareaElements = getElementsByTagName(objTarget, TAG_TEXTAREA);
	var textareaElementsLength = textareaElements.length;
	// 編集有無確認
	for (var i = 0; i < textareaElementsLength; i++) {
		if (ARY_FORM_VALUE_DEFAULT[textareaElements.item(i).id] != getFormValue(textareaElements.item(i))) {
			return false;
		}
	}
	// SELECT要素群取得
	var selectElements = getElementsByTagName(objTarget, TAG_SELECT);
	var selectElementsLength = selectElements.length;
	// 編集有無確認
	for (var i = 0; i < selectElementsLength; i++) {
		if (ARY_FORM_VALUE_DEFAULT[selectElements.item(i).id] != getFormValue(selectElements.item(i))) {
			return false;
		}
	}
	return true;
}

/**
 * フィールドにイベントを設定する。<br>
 * また、編集確認用に初期値を設定する。<br>
 * @param target 確認対象(StringあるいはObject)
 */
function setFieldsEvent(target) {
	// INPUT要素群取得
	var inputElements = getElementsByTagName(target, TAG_INPUT);
	var inputElementsLength = inputElements.length;
	// イベント及び初期値設定
	for (var i = 0; i < inputElementsLength; i++) {
		if (inputElements.item(i).type == INPUT_TYPE_TEXT) {
			ARY_FORM_VALUE_DEFAULT[inputElements.item(i).id] = getFormValue(inputElements.item(i));
			setOnChangeHandler(inputElements.item(i), onChangeFields);
		}
	}
	// TEXTAREA要素群取得
	var textareaElements = getElementsByTagName(target, TAG_TEXTAREA);
	var textareaElementsLength = textareaElements.length;
	// イベント及び初期値設定
	for (var i = 0; i < textareaElementsLength; i++) {
		ARY_FORM_VALUE_DEFAULT[textareaElements.item(i).id] = getFormValue(textareaElements.item(i));
		setOnChangeHandler(textareaElements.item(i), onChangeFields);
	}
	// SELECT要素群取得
	var selectElements = getElementsByTagName(target, TAG_SELECT);
	var selectElementsLength = selectElements.length;
	// イベント及び初期値設定
	for (var i = 0; i < selectElementsLength; i++) {
		ARY_FORM_VALUE_DEFAULT[selectElements.item(i).id] = getFormValue(selectElements.item(i));
		setOnChangeHandler(selectElements.item(i), onChangeFields);
	}
	return true;
}

/**
 * 複製モード遷移時の確認を行う。<br>
 * @param イベントオブジェクト
 * @return 複製モード遷移可否
 */
function confirmReplicationMode(event) {
	return confirm(getMessage(MSG_REPLICATION_CONFIRMATION, null));
}

/**
 * 編集モード遷移時の確認を行う。
 * @param event イベントオブジェクト
 * @param rep   変換文字列
 * @return 編集モード遷移可否
 */
function confirmEditMode(event, rep) {
	return confirm(getMessage(MSG_EDIT_CONFIRMATION, rep));
}

/**
 * 選択元セレクトボックスのオプションを設定する。<br>
 * エクスポートマスタ編集等で用いる。<br>
 * 選択項目セレクトボックスオプション配列から選択セレクトボックスの
 * 内容を除いたものを設定する。<br>
 * @param targetItem   選択元セレクトボックス(StringあるいはObject)
 * @param selectedItem 選択項目セレクトボックス(StringあるいはObject)
 * @param optionArray  選択元セレクトボックスオプション配列
 */
function setTargetItemOptions(targetItem, selectedItem, optionArray) {
	// 選択項目セレクトボックス設定
	setSelectOptions(targetItem, optionArray);
	// セレクトボックスのオプションを取得
	var targetOptions = getSelectOptions(targetItem);
	var selectedOptions = getSelectOptions(selectedItem);
	var selectedOptionsLength = selectedOptions.length;
	// 選択セレクトボックスの内容を選択項目セレクトボックスから除去
	for (var i = 0; i < selectedOptionsLength; i++) {
		var targetOptionsLength = targetOptions.length;
		for (var j = 0; j < targetOptionsLength; j++) {
			if (selectedOptions[i].value == targetOptions[j].value) {
				targetOptions[j] = null;
				break;
			}
		}
	}
}

/**
 * 選択項目セレクトボックスのオプションを設定する。<br>
 * エクスポートマスタ編集等で用いる。<br>
 * 選択項目セレクトボックスオプション配列から選択セレクトボックスの
 * 内容を除いたものを設定する。<br>
 * @param targetItem    選択元セレクトボックス(StringあるいはObject)
 * @param selectedItem  選択項目セレクトボックス(StringあるいはObject)
 * @param optionArray   選択元セレクトボックスオプション配列
 * @param selectedArray 選択項目セレクトボックス選択値配列(一次元)
 */
function setSelectedItemOptions(targetItem, selectedItem, optionArray, selectedArray) {
	// 選択元セレクトボックス設定
	setSelectOptions(targetItem, optionArray);
	var selectedArrayLength = selectedArray.length;
	// 選択項目セレクトボックス選択値を選択し移動
	for (var i = 0; i < selectedArrayLength; i++) {
		// 選択項目セレクトボックス選択値を選択
		selectOption(targetItem, selectedArray[i]);
		// セレクトボックスのオプションを移動
		moveSelectOptions(targetItem, selectedItem);
	}
}

/**
 * 選択元セレクトボックスの選択された項目を選択項目セレクトボックスへ移動する。<br>
 * エクスポートマスタ編集等で用いる。<br>
 * @param targetItem   選択元セレクトボックス(StringあるいはObject)
 * @param selectedItem 選択項目セレクトボックス(StringあるいはObject)
 */
function selectItems(targetItem, selectedItem) {
	// セレクトボックスのオプションを移動
	moveSelectOptions(targetItem, selectedItem);
}

/**
 * 選択元セレクトボックスの全項目を選択項目セレクトボックスへ移動する。<br>
 * エクスポートマスタ編集等で用いる。<br>
 * @param targetItem   選択元セレクトボックス(StringあるいはObject)
 * @param selectedItem 選択項目セレクトボックス(StringあるいはObject)
 */
function selectAllItems(targetItem, selectedItem) {
	// 全選択
	selectAllOptions(targetItem);
	// セレクトボックスのオプションを移動
	moveSelectOptions(targetItem, selectedItem);
}

/**
 * 選択項目セレクトボックスの選択された項目を除く。<br>
 * エクスポートマスタ編集等で用いる。<br>
 * @param targetItem   選択元セレクトボックス(StringあるいはObject)
 * @param selectedItem 選択項目セレクトボックス(StringあるいはObject)
 * @param optionArray  選択元セレクトボックスオプション配列
 */
function removeItems(targetItem, selectedItem, optionArray) {
	// 選択項目セレクトボックスから選択されたオプションを除去
	removeSelectedOption(selectedItem)
	// 選択元セレクトボックス設定
	setTargetItemOptions(targetItem, selectedItem, optionArray);
}

/**
 * 選択項目セレクトボックスの全項目を除く。<br>
 * エクスポートマスタ編集等で用いる。<br>
 * @param targetItem   選択元セレクトボックス(StringあるいはObject)
 * @param selectedItem 選択項目セレクトボックス(StringあるいはObject)
 * @param optionArray  選択元セレクトボックスオプション配列
 */
function removeAllItems(targetItem, selectedItem, optionArray) {
	// 選択項目セレクトボックス設定
	setSelectOptions(selectedItem, null);
	// 選択元セレクトボックス設定
	setSelectOptions(targetItem, optionArray);
}

/**
 * フィールドオンチェンジ時の処理。
 * @param event イベントオブジェクト
 * @return 無し
 */
function onChangeFields(event) {
	// 対象オブジェクト取得
	var objTarget = getSrcElement(event);
	changeFieldBgColor(objTarget);
}

/**
 * メインメニューのオブジェクト配列を取得する。
 */
function getMainMenuArray() {
	// 対象ノードリスト取得
	var objNodeList = getElementsByTagName(ID_MAIN_MENU, TAG_TD);
	if (objNodeList.length == 0) {
		return new Array(0);
	}
	// 配列に変換(最終列は含めない)
	var aryMainMenu = new Array(objNodeList.length - 1);
	var objNodeListLength = objNodeList.length - 1;
	for (var i = 0; i < objNodeListLength; i++) {
		aryMainMenu[i] = objNodeList.item(i);
	}
	return aryMainMenu;
}

/**
 * メインメニューマウスオーバー時の処理。
 * @param event イベントオブジェクト
 */
function onMouseOverTab(event) {
	// 対象オブジェクト取得
	var objTarget = getSrcElement(event);
	// 選択状態確認
	if (isMainMenuSelected(objTarget)) {
		return;
	}
	// 背景色変更
	setBgColor(objTarget, COLOR_MAINMENU);
}

/**
 * メインメニューマウスアウト時の処理。
 * @param event イベントオブジェクト
 */
function onMouseOutTab(event) {
	// 対象オブジェクト取得
	var objTarget = getSrcElement(event);
	// 選択状態確認
	if (isMainMenuSelected(objTarget)) {
		return;
	}
	// 背景色変更
	setBgColor(objTarget, COLOR_MAINMENU_REVERS);
}

/**
 * メインメニュー選択時の処理を行う。
 * @param mainMenuKey 対象メインメニューキー
 */
function selectMainMenu(mainMenuKey) {
	// メインメニューリセット
	resetMainMenu();
	// 選択メニュースタイル設定
	setMainMenuSelected(mainMenuKey);
	// サブメニュー設定
	setSubMenu(mainMenuKey);
}

/**
 * メインメニューのスタイルをリセットする。
 */
function resetMainMenu() {
	// メインメニューオブジェクト配列取得
	var aryMainMenu = getMainMenuArray();
	// スタイル設定
	for (var i in aryMainMenu) {
		setBorderBottomWidth(aryMainMenu[i], "1px");
		setBgColor(aryMainMenu[i], COLOR_MAINMENU_REVERS);
	}
}

/**
 * メインメニューを選択状態にする。
 * @param mainMenuKey 対象メインメニューキー
 */
function setMainMenuSelected(mainMenuKey) {
	// スタイル設定
	setBorderBottomWidth(mainMenuKey, "0px");
	setBgColor(mainMenuKey, COLOR_MAINMENU);
}

/**
 * メインメニュー選択状態を確認する。
 * @return メインメニュー選択状態(true：選択状態、false：非選択状態)
 */
function isMainMenuSelected(objMainMenu) {
	if (getBorderBottomWidth(objMainMenu) == "0px") {
		return true;
	}
	return false;
}

/**
 * サブメニューを設定する。<br>
 * サブメニュー配列の内容は、以下の通り。<br>
 * 0列目：メニューキー<br>
 * 1列目：コマンド<br>
 * 2列目：メニュータイトル<br>
 * @param mainMenuKey 対象メインメニューキー
 */
function setSubMenu(mainMenuKey) {
	// サブメニュー配列取得
	var aryMenu = ARY_MENU[getMenuIndex(mainMenuKey)][2];
	// サブメニュー削除
	deleteLowerObjects(ID_SUB_MENU, TAG_A);
	// サブメニュー作成
	for (var i in aryMenu) {
		// オブジェクト作成
		var objNode = createLowerObjects(ID_SUB_MENU, TAG_A);
		// イベントハンドラ作成
		var onclick = new Function("submitTransfer(null, null, null, new Array(\"transferredMenuKey\", \"" + aryMenu[i][0] + "\"),\"" + aryMenu[i][1] + "\")");
		// イベントハンドラ設定
		setOnClickHandler(objNode, onclick);
		// メニュータイトル設定
		setInnerHtml(objNode, aryMenu[i][2]);
	}
}

/**
 * メインメニューを設定する。<br>
 * サブメニュー配列の内容は、以下の通り。<br>
 * 0列目：メインメニューキー<br>
 * 1列目：メインメニュータイトル<br>
 * 2列目：サブメニュー配列<br>
 * @param mainMenuKey 表示対象メインメニューキー
 */
function setMainMenu(mainMenuKey) {
	// メインメニュー削除
	deleteLowerObjects(ID_MAIN_MENU, TAG_TD);
	// 対象メインメニューが含まれる頁を取得
	var menuPage = Math.floor(getMenuIndex(mainMenuKey) / LENGTH_MENU);
	// メニュー表示範囲設定
	var menuStart = menuPage * LENGTH_MENU;
	// メインメニュー移動TD(<<)追加
	if (menuStart > 0) {
		// オブジェクト作成
		var objNode = createLowerObjects(ID_MAIN_MENU, TAG_TD);
		// イベントハンドラ作成
		var onclick = new Function("setMenu(\"" +getMainMenuKey(menuStart - LENGTH_MENU) + "\")");
		// イベントハンドラ設定
		setOnClickHandler(objNode, onclick);
		// メニュータイトル設定
		setInnerHtml(objNode, "&lt;&lt;");
		// クラス名設定
		objNode.className = "ScrollTd";
	}
	// メインメニュー作成
	for (var i = menuStart; i < menuStart + LENGTH_MENU; i++) {
		// メニュー配列確認
		if (i == ARY_MENU.length) {
			break;
		}
		// オブジェクト作成
		var objNode = createLowerObjects(ID_MAIN_MENU, TAG_TD);
		// イベントハンドラ設定
		setOnMouseOverHandler(objNode, onMouseOverTab);
		setOnMouseOutHandler (objNode, onMouseOutTab );
		// イベントハンドラ作成
		var onclick = new Function("selectMainMenu(\""+ ARY_MENU[i][0] + "\")");
		// イベントハンドラ設定
		setOnClickHandler(objNode, onclick);
		// メニュータイトル設定
		setInnerHtml(objNode, ARY_MENU[i][1]);
		// メニューキー設定
		objNode.id = ARY_MENU[i][0];
	}
	// メインメニュー移動TD(>>)追加
	if (ARY_MENU.length > menuStart + LENGTH_MENU) {
		// オブジェクト作成
		var objNode = createLowerObjects(ID_MAIN_MENU, TAG_TD);
		// イベントハンドラ作成
		var onclick = new Function("setMenu(\"" + getMainMenuKey(menuStart + LENGTH_MENU) + "\")");
		// イベントハンドラ設定
		setOnClickHandler(objNode, onclick);
		// メニュータイトル設定
		setInnerHtml(objNode, "&gt;&gt;");
		// クラス名設定
		objNode.className = "ScrollTd";
	}
	// 調整用最終TD追加
	var objNode = createLowerObjects(ID_MAIN_MENU, TAG_TD);
	objNode.className = "LastTd";
}

/**
 * メインメニューキーを取得する。<br>
 * 表示するメニュー内に選択メニューが存在する場合は選択メニューを、
 * そうでない場合は表示するメニュー内の先頭のメインメニューキーを返す。<br>
 * @param menuStart メニュー開始インデックス
 * @return メインメニューキー
 */
function getMainMenuKey(menuStart) {
	for (var i = menuStart; i < menuStart + LENGTH_MENU - 1; i++) {
		if (i < ARY_MENU.length && ARY_MENU[i][0] == SELECT_MENU) {
			return SELECT_MENU;
		}
	}
	return ARY_MENU[menuStart][0];
}


/**
 * メニューを設定する。
 */
function setMenu(mainMenuKey) {
	// メニュー確認
	if (getObject(ID_MAIN_MENU) == null) {
		return;
	}
	// メインメニュー設定
	setMainMenu(mainMenuKey);
	// メインメニューリセット
	resetMainMenu();
	// 選択メニュースタイル設定
	setMainMenuSelected(mainMenuKey);
	// サブメニュー設定
	setSubMenu(mainMenuKey);
}

/**
 * 対象メインメニューのARY_MENUにおけるインデックスを取得する。
 * @param mainMenuKey 対象メインメニューキー
 * @return インデックス
 */
function getMenuIndex(mainMenuKey) {
	for (var i in ARY_MENU) {
		if (ARY_MENU[i][0] == mainMenuKey) {
			return i;
		}
	}
	return 0;
}

/**
 * ページの先頭へ。
 * @param 無し
 * @return 無し
 * @throws 無し
 */
function pageToTop() {
	window.scrollTo(Math.max(document.documentElement.scrollLeft, document.body.scrollLeft), 0);
}

/**
 * スクロール先へ自動でスクロールさせる。<br>
 * @param target スクロール先(StringあるいはObject)
 */
function scrollToTarget(target) {
	// 対象オブジェクト取得
	var objTarget = getObject(target);
	// 対象オブジェクト確認
	if (objTarget == null) {
		return;
	}
	// スクロール
	objTarget.scrollIntoView();
}

/**
 * チェックボックスの一括選択・解除を行う。<br>
 * @param obj 一括選択・解除チェックボックス(Object)
 */
function doAllBoxChecked(obj) {
	// チェックボックスエレメント取得
	var elements = document.getElementsByName("ckbSelect");
	var elementsLength = elements.length;
	// チェック操作
	for (i = 0; i < elementsLength; i++) {
		checkableCheck(elements[i], obj.checked);
	}
}

/**
 * チェックボックス(ラジオボタン)の一括解除を行う。<br>
 * @param name 対象チェックボックス(ラジオボタン)名
 */
function removeAllChecks(name) {
	// チェックボックス(ラジオボタン)要素を取得
	var elements = document.getElementsByName(name);
	// 要素長を取得
	var elementsLength = elements.length;
	// 要素毎に処理
	for (i = 0; i < elementsLength; i++) {
		// チェックを解除
		checkableCheck(elements[i], false);
	}
}

/**
 * クラスに対してフィールド長を設定する。
 * @param className 設定対象クラス名
 * @param numLength フィールド長
 */
function setMaxLength(className, numLength) {
	var elements = getElementsByClass(className);
	var elementsLength = elements.length;
	for (var i = 0; i < elementsLength; i++) {
		setMaxLengthNumber(elements[i], numLength);
	}
}

/**
 * ノード内の日付チェックを行う。<br>
 * 日付は、idで判断する。<br>
 * 〇〇Yearが存在した場合、〇〇Monthを検索する。<br>
 * 全て存在しており、一つでも入力されていたら日付の妥当性確認を行う。<br>
 * @param aryMessage メッセージ配列
 * @param target     確認対象(StringあるいはObject)
 */
function checkDatesYearMonth(aryMessage, event) {
	// 検索対象文字列宣言
	var YEAR_NAME  = "Year";
	var MONTH_NAME = "Month";
	// INPUT要素群取得
	objTarget = document.form;
	var inputNodeList = objTarget.getElementsByTagName("input");
	var inputNodeListLength = inputNodeList.length;
	for (var i = 0; i < inputNodeListLength; i++) {
		// 要素取得
		var element = inputNodeList.item(i);
		var elementName = new String(element.name);
		// 要素name確認
		if (elementName.lastIndexOf(YEAR_NAME) != elementName.length - YEAR_NAME.length) {
			continue;
		}
		// 月name作成
		var monthName = elementName.replace(YEAR_NAME, MONTH_NAME);
		// 月要素準備
		var monthElement = document.getElementById(monthName);
		// 月要素確認
		if (monthElement == null) {
			continue;
		}
		// 入力確認
		if (getFormValue(element) == "" && getFormValue(monthElement) == "" ) {
			continue;
		}
		// 日付チェック
		return checkDateYearMonth(element, monthElement, aryMessage);
	}
}


/**
 * 年月のチェックを行う。<br>
 * 必須確認及び数値確認は、共通チェックで行うため、ここでは行わない。<br>
 * @param targetYear  確認対象年(StringあるいはObject)
 * @param targetMonth 確認対象月(StringあるいはObject)
 * @param aryMessage  エラーメッセージ格納配列
 * @return 無し
 */
function checkDateYearMonth(targetYear, targetMonth, aryMessage) {
	// 範囲宣言
	var MIN_YEAR  = 1000;
	var MIN_MONTH = 1;
	var MAX_MONTH = 12;
	// 年月を取得
	var year  = getFormValue(targetYear );
	var month = getFormValue(targetMonth);
	// 年確認
	if (year < MIN_YEAR) {
		if (aryMessage.length == 0) {
			setFocus(targetYear);
		}
		setBgColor(targetYear, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_YEAR_CHECK_ERROR, MIN_YEAR));
		return false;
	}
	// 月確認
	if (month < MIN_MONTH || month > MAX_MONTH) {
		if (aryMessage.length == 0) {
			setFocus(targetMonth);
		}
		setBgColor(targetMonth, COLOR_FIELD_ERROR);
		aryMessage.push(getMessage(MSG_MONTH_CHECK_ERROR, null));
		return false;
	}
}

/**
 * 年プルダウンの内容を選択年を基準として再設定する。
 * イベントの発行元を年プルダウンオブジェクトとする。
 * @param event イベントオブジェクト
 */
function onChangeYearPulldown(event) {
	// フィールド変更時の処理
	onChangeFields(event);
	// イベント発生元オブジェクトを取得
	var objSelect = getSrcElement(event);
	// 年プルダウンの内容を選択年を基準として再設定
	resetYearOption(objSelect);
}

/**
 * 年プルダウンの内容を選択年を基準として再設定する。
 * @param target 設定対象(StringあるいはObject)
 */
function resetYearOption(target) {
	// 対象年プルダウンを取得
	var objSelect = getObject(target);
	// 対象年プルダウンを取得できなかった場合
	if (objSelect == null) {
		// 処理無し
		return;
	}
	// プルダウン要素を取得
	var objOption = getSelectOptions(objSelect);
	// プルダウン要素を取得できなかった場合
	if (objOption == null || objOption.selectedIndex == null) {
		// 処理無し
		return;
	}
	// プルダウン要素長を取得
	var optionLength = getSelectOptionLength(objSelect);
	// 選択年を取得
	var selectedYear = getFormValue(objSelect);
	// プルダウンのオプションを削除
	removeAllOptions(objSelect);
	// プルダウンを生成
	for (var i = 0; i < optionLength; i++) {
		// 年を準備
		var year = parseInt(selectedYear) - optionLength + 2 + i;
		// オプションを追加
		objOption[i] = new Option(String(year), String(year));
	}
	// 選択検索年を再設定
	setFormValue(objSelect, selectedYear);
}

