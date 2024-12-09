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
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 編集モード確認
	if (modeCardEdit == MODE_CARD_EDIT_EDIT) {
		// 有効日編集不可
		setDisabled("txtEditActivateYear", true);
		setDisabled("txtEditActivateMonth", true);
		setDisabled("txtEditActivateDay", true);
		setDisabled("txtEditEmployeeCode", true);
		setReadOnly("btnEmployeeCode", true);
		setReadOnly("btnRegist", false);
	} else {
		// 社員コードモード確認
		if (jsModeEmployeeCode == MODE_ACTIVATE_DATE_FIXED) {
			// 有効日編集不可
			setDisabled("txtEditActivateYear", true);
			setDisabled("txtEditActivateMonth", true);
			setDisabled("txtEditActivateDay", true);
			setDisabled("txtEditEmployeeCode", true);
			setReadOnly("btnEmployeeCode", false);
			setReadOnly("btnRegist", false);
		} else {
			setDisabled("txtEditActivateYear", false);
			setDisabled("txtEditActivateMonth", false);
			setDisabled("txtEditActivateDay", false);
			setDisabled("txtEditEmployeeCode", false);
			setReadOnly("btnEmployeeCode", false);
			setReadOnly("btnRegist", true);
		}
	}
	// 有効日(検索)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可
		setDisabled("txtSearchActivateYear", true);
		setDisabled("txtSearchActivateMonth", true);
		setDisabled("txtSearchActivateDay", true);
		setReadOnly("btnSearch", false);
	} else {
		setDisabled("txtSearchActivateYear", false);
		setDisabled("txtSearchActivateMonth", false);
		setDisabled("txtSearchActivateDay", false);
		setReadOnly("btnSearch", true);
	}
	// 付与時間有効無効判定
	if(jsModeGivingtime == false) {
		// 時間編集欄変更不可
		setDisabled("txtEditFormerGivingTime", true);
		setDisabled("txtEditGivingTime", true);
	} else {
		// 時間編集欄変更可
		setDisabled("txtEditFormerGivingTime", false);
		setDisabled("txtEditGivingTime", false);
	}
	// 前年度日数変更イベント設定
	setOnChangeHandler("txtEditFormerGivingDay", onChangeGiving);
	// 前年度時間変更イベント設定
	setOnChangeHandler("txtEditFormerGivingTime", onChangeGiving);
	// 今年度日数変更イベント設定
	setOnChangeHandler("txtEditGivingDay", onChangeGiving);
	// 今年度時間変更イベント設定
	setOnChangeHandler("txtEditGivingTime", onChangeGiving);
	// ストック日数変更イベント設定
	setOnChangeHandler("txtEditGivingStockDay", onChangeGiving);
	// 前年度付与/破棄プルダウン変更イベント設定
	setOnChangeHandler("pltEditFormerGivingType", onChangeGiving);
	// 今年度付与/破棄プルダウン変更イベント設定
	setOnChangeHandler("pltEditGivingType", onChangeGiving);
	// ストック付与/破棄プルダウン変更イベント設定
	setOnChangeHandler("pltEditStockType", onChangeGiving);
}

/**
 * 付与日数チェック処理。
 * @param aryMessage メッセージ配列
 */
function checkPaidHolidayGiving(aryMessage) {
	// 前年度付与日数チェック
	checkGiving(aryMessage, "txtEditFormerGivingDay");
	// 今年度付与日数チェック
	checkGiving(aryMessage, "txtEditGivingDay");
	// ストック付与日数チェック
	checkGiving(aryMessage, "txtEditGivingStockDay");
}

/**
 * 付与日数/時間変更時の処理を行う。
 */
function onChangeGiving() {
	var aryMessage= new Array();
	checkGiving(aryMessage, "txtEditFormerGivingDay");
	checkNumber("txtEditFormerGivingTime", aryMessage);
	checkGiving(aryMessage, "txtEditGivingDay");
	checkNumber("txtEditGivingTime", aryMessage);
	checkGiving(aryMessage, "txtEditGivingStockDay");
	if (aryMessage.length != 0) {
		return showMessage(aryMessage);
	}
	// リクエスト送信
	doSubmit(document.form, 'TM4426');
}
