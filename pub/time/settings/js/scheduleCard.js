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
	// 一覧背景色設定
	setTableColor("list2");
	// 無効フラグ編集可
	setDisabled("pltEditInactivate", false);
	// コード編集可
	setReadOnly("txtScheduleCode", false);
	// 年度編集可
	setReadOnly("pltFiscalYear", false);
	// 決定ボタン押下可
	setReadOnly("btnActivateDateSet", false);
	// 新規登録
	if (modeCardEdit == MODE_CARD_EDIT_INSERT) {
		// 無効フラグ編集不可
		setDisabled("pltEditInactivate", true);
	// 履歴追加
	} else if (modeCardEdit == MODE_CARD_EDIT_ADD) {
		// コード編集不可
		setReadOnly("txtScheduleCode", true);
	// 履歴編集
	} else if (modeCardEdit == MODE_CARD_EDIT_EDIT) {
		// コード編集不可
		setReadOnly("txtScheduleCode", true);
		// 年度編集不可
		setReadOnly("pltFiscalYear", true);
		// 決定ボタン押下不可
		setReadOnly("btnActivateDateSet", true);
		setReadOnly("btnPatternSet", true);
	}
	// 有効日(編集)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 年度編集不可
		setReadOnly("pltFiscalYear", true);
		// 登録ボタン利用可
		setReadOnly("btnRegist", false);
		// 月ボタン利用可
		setReadOnly("btnApril", false);
		setReadOnly("btnMay", false);
		setReadOnly("btnJune", false);
		setReadOnly("btnJuly", false);
		setReadOnly("btnAugust", false);
		setReadOnly("btnSeptember", false);
		setReadOnly("btnOctorber", false);
		setReadOnly("btnNovember", false);
		setReadOnly("btnDecember", false);
		setReadOnly("btnJanuary", false);
		setReadOnly("btnFebruary", false);
		setReadOnly("btnMarch", false);
	} else {
		// 年度編集可
		setReadOnly("pltFiscalYear", false);
		// パターン決定ボタン利用不可
		setReadOnly("btnPatternSet", true);
		// 登録ボタン利用不可
		setReadOnly("btnRegist", true);
		// 月ボタン利用可
		setReadOnly("btnApril", true);
		setReadOnly("btnMay", true);
		setReadOnly("btnJune", true);
		setReadOnly("btnJuly", true);
		setReadOnly("btnAugust", true);
		setReadOnly("btnSeptember", true);
		setReadOnly("btnOctorber", true);
		setReadOnly("btnNovember", true);
		setReadOnly("btnDecember", true);
		setReadOnly("btnJanuary", true);
		setReadOnly("btnFebruary", true);
		setReadOnly("btnMarch", true);
	}
	// パターンモード確認
	if (modePattern == MODE_ACTIVATE_DATE_FIXED) {
		// 編集不可
		// 有効日決定ボタン利用不可
		setReadOnly("btnActivateDateSet", true);
		// パターン編集不可
		setReadOnly("pltPattern", true);
	} else {
		// 複製ボタン利用不可
		setReadOnly("btnCopySet", true);
	}
	// コピーモード確認
	if (jsCopyModeEdit == "TM5474") {
		// 決定ボタン押下不可
		setReadOnly("btnActivateDateSet", true);
	}
	// 勤務形態指定ラジオボタンイベント設定
	setOnClickHandler("radioWeek", onClickRadioSelect);
	setOnClickHandler("radioPeriod", onClickRadioSelect);
	setOnClickHandler("radioCheck", onClickRadioSelect);
	// イベントハンドラ設定
	setOnChangeHandler("pltFiscalYear", onChangeYearPulldown);
	// 勤務形態指定確認
	onClickRadioSelect(null);
}

/**
 * 勤務形態指定ラジオボタンクリック時の処理を行う。<br>
 * @param event イベントオブジェクト
 */
function onClickRadioSelect(event) {
	// チェック確認
	if(isCheckableChecked("radioWeek")) {
		setDisabled("ckbMonday", false);
		setDisabled("ckbTuesday", false);
		setDisabled("ckbWednesday", false);
		setDisabled("ckbThursday", false);
		setDisabled("ckbFriday", false);
		setDisabled("ckbSatureday", false);
		setDisabled("ckbSunday", false);
		setDisabled("ckbNationalHoliday", false);
		setDisabled("pltScheduleStartDay", true);
		setDisabled("pltScheduleEndDay", true);
		doAllChecked("ckbSelect", true);
	} else if(isCheckableChecked("radioPeriod")) {
		setDisabled("ckbMonday", true);
		setDisabled("ckbTuesday", true);
		setDisabled("ckbWednesday", true);
		setDisabled("ckbThursday", true);
		setDisabled("ckbFriday", true);
		setDisabled("ckbSatureday", true);
		setDisabled("ckbSunday", true);
		setDisabled("ckbNationalHoliday", true);
		setDisabled("pltScheduleStartDay", false);
		setDisabled("pltScheduleEndDay", false);
		doAllChecked("ckbSelect", true);
	} else if(isCheckableChecked("radioCheck")) {
		setDisabled("ckbMonday", true);
		setDisabled("ckbTuesday", true);
		setDisabled("ckbWednesday", true);
		setDisabled("ckbThursday", true);
		setDisabled("ckbFriday", true);
		setDisabled("ckbSatureday", true);
		setDisabled("ckbSunday", true);
		setDisabled("ckbNationalHoliday", true);
		setDisabled("pltScheduleStartDay", true);
		setDisabled("pltScheduleEndDay", true);
		doAllChecked("ckbSelect", false);
	} else {
		setDisabled("ckbMonday", true);
		setDisabled("ckbTuesday", true);
		setDisabled("ckbWednesday", true);
		setDisabled("ckbThursday", true);
		setDisabled("ckbFriday", true);
		setDisabled("ckbSatureday", true);
		setDisabled("ckbSunday", true);
		setDisabled("ckbNationalHoliday", true);
		setDisabled("pltScheduleStartDay", true);
		setDisabled("pltScheduleEndDay", true);
		doAllChecked("ckbSelect", true);
	}
}

/**
 * 指定されたname領域の一括読取・解除を行う。<br>
 * @param obj 一括選択・解除チェックボックス(Object)
 */
function doAllChecked(name, flg) {
	// チェックボックスエレメント取得
	var elements = document.getElementsByName(name);
	var elementsLength = elements.length;
	// チェック操作
	for (i = 0; i < elementsLength; i++) {
		var objTarget = getObject(elements[i]);
		objTarget.disabled = flg;
	}
}

