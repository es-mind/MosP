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
 * 画面読込時追加処理
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
	}
	// 履歴追加モード
	if (modeCardEdit == MODE_CARD_EDIT_ADD) {
		// コード編集不可
		setDisabled("txtEditApplicationCode", true);
		// ラジオボタン編集不可
		setReadOnly("radMaster", true);
		setReadOnly("radEmployeeCode", true);
		// 削除ボタン利用不可
		setReadOnly("btnDelete", true);
	}
	// 履歴編集モード
	if (modeCardEdit == MODE_CARD_EDIT_EDIT){
		// コード編集不可
		setDisabled("txtEditApplicationCode", true);
		// 有効日編集不可
		setReadOnly("btnEditActivateDate", true);
		// ラジオボタン編集不可
		setReadOnly("radMaster", true);
		setReadOnly("radEmployeeCode", true);
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
	setOnChangeHandler("txtEditEmployeeCode", onChangeEmployeeCode);
	// 設定適用区分ラジオボタンイベント設定
	setOnClickHandler("radMaster", onClickRadAppliacationType);
	setOnClickHandler("radEmployeeCode", onClickRadAppliacationType);
	// 設定適用区分確認
	onClickRadAppliacationType(null);
}

/**
 * 設定適用区分ラジオボタンクリック時の処理を行う。<br>
 * @param event イベントオブジェクト
 */
function onClickRadAppliacationType(event) {
	// チェック確認
	if (isCheckableChecked("radMaster")) {
		setDisabled("pltEditWorkPlaceMaster", false);
		setDisabled("pltEditEmploymentMaster", false);
		setDisabled("pltEditSectionMaster", false);
		setDisabled("pltEditPositionMaster", false);
		setDisabled("txtEditEmployeeCode", true);
	} else {
		setDisabled("pltEditWorkPlaceMaster", true);
		setDisabled("pltEditEmploymentMaster", true);
		setDisabled("pltEditSectionMaster", true);
		setDisabled("pltEditPositionMaster", true);
		setDisabled("txtEditEmployeeCode", false);
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
function checkEmployeeCode(aryMessage, event) {
	// 勤怠設定適用区分確認
	if (isCheckableChecked("radEmployeeCode")) {
		// 社員コード必須確認
		checkRequired("txtEditEmployeeCode", aryMessage);
	}
}

