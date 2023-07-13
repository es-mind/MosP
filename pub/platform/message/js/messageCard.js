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
 * 複製確認時のメッセージコード。<br>
 * platform.jsのものを上書きする。<br>
 * メッセージはNoを自動採番するためメッセージが異なる。<br>
 */
MSG_REPLICATION_CONFIRMATION = "PFQ1005";

/**
 * 画面読込時追加処理を行う。
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 新規登録モード
	if (modeCardEdit == MODE_CARD_EDIT_INSERT){
		// 無効フラグ編集不可
		setReadOnly("pltEditInactivate", true);
		// 削除ボタン利用不可
		setReadOnly("btnDelete", true);
		// メール送信ボタン利用不可
		setReadOnly("btnSendMail", true);
	}
	// 有効日決定状態
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可
		setReadOnly("txtEditActivateYear", true);
		setReadOnly("txtEditActivateMonth", true);
		setReadOnly("txtEditActivateDay", true);
	}
	// 有効日変更状態
	if (modeActivateDate == MODE_ACTIVATE_DATE_CHANGING) {
		// 登録ボタン利用不可
		setReadOnly("btnRegist", true);
	}
	// 個人テキストボックス変更イベント設定
	setOnChangeHandler("txtEmployeeCode", onChangeEmployeeCode);
	// 設定適用区分ラジオボタンイベント設定
	setOnClickHandler("radMaster", onClickRadAppliacationType);
	setOnClickHandler("radEmployeeCode", onClickRadAppliacationType);
	// 設定適用区分確認
	onClickRadAppliacationType(null);
	// フォーカス設定(入力ガイド用)
	setFocus("txtMessageTitle");
	setFocus("txtMessage");
	setFocus("btnStartDate");
}

/**
 * 設定適用区分ラジオボタンクリック時の処理を行う。<br>
 * @param event イベントオブジェクト
 */
function onClickRadAppliacationType(event) {
	// チェック確認
	if (isCheckableChecked("radMaster")) {
		setDisabled("pltWorkPlace", false);
		setDisabled("pltEmployment", false);
		setDisabled("pltSection", false);
		setDisabled("pltPosition", false);
		setDisabled("txtEmployeeCode", true);
	} else {
		setDisabled("pltWorkPlace", true);
		setDisabled("pltEmployment", true);
		setDisabled("pltSection", true);
		setDisabled("pltPosition", true);
		setDisabled("txtEmployeeCode", false);
	}
}

/**
 * 個人変更時の処理を行う。<br>
 * @param event イベントオブジェクト
 */
function onChangeEmployeeCode(event) {
	// フィールド変更時処理実施
	onChangeFields(event);
	// 適用対象者氏名消去
	setInnerHtml("lblEmployeeName", "");
}

/**
 * 勤怠設定適用区分に応じて、社員コード必須確認を行う。<br>
 * 登録時の追加チェックとして、用いられる。<br>
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkExtra(aryMessage, event) {
	// 勤怠設定適用区分確認
	if (isCheckableChecked("radEmployeeCode")) {
		// 社員コード必須確認
		checkRequired("txtEmployeeCode", aryMessage);
	}
	// タイトル及びメッセージ必須確認
	setFocus("txtMessageTitle");
	checkRequired("txtMessageTitle", aryMessage);
	setFocus("txtMessage");
	checkRequired("txtMessage", aryMessage);
}

/**
 * テキストエリア桁数設定。
 */
function isMaxlength(obj){  
	var mlength = obj.getAttribute ? parseInt(obj.getAttribute("maxlength")) : "";  
	if (obj.getAttribute && obj.value.length > mlength)  
		obj.value = obj.value.substring(0,mlength);  
}

/**
 * テキストボックスに入力ガイドを表示する。
 * @param target 対象オブジェクト
 * @param guide  入力ガイドメッセージ
 */
function showFormGuide(target, guide) {
	// 入力値確認
	if (getFormValue(target) != "") {
		return;
	}
	// 入力ガイド表示
	setFormValue(target, guide);
	// 文字色変更
	setColor(target, "#808080");
}

/**
 * テキストボックスから入力ガイドを消す。
 * @param target 対象オブジェクト
 * @param guide  入力ガイドメッセージ
 */
function hideFormGuide(target, guide) {
	// 入力値確認
	if (getFormValue(target) != guide) {
		return;
	}
	// 入力ガイド消去
	setFormValue(target, "");
	// 文字色変更
	setColor(target, COLOR_FONT_NORMAL);
}
