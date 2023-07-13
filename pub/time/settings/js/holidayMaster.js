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
 * 付与日数値チェックエラーメッセージ。
 */
var MSG_GIVINGDAY_VALUE_CHECK = "TMW0219";

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// イベントハンドラ設定
	setOnChangeHandler("pltEditHolidayType", checkHolidayType);
	setOnChangeHandler("pltEditHourlyHoliday", changeHourlyHoliday);
	setOnClickHandler("ckbNoLimit", checkNoLimit);
	// 休暇区分確認
	checkHolidayType();
	// 編集モード確認
	if (modeCardEdit == MODE_CARD_EDIT_INSERT){
		setReadOnly("pltEditInactivate", true);
	}
	if (modeCardEdit == MODE_CARD_EDIT_ADD){
		setReadOnly("pltEditHolidayType", true);
		setReadOnly("txtEditHolidayCode", true);
	}
	if (modeCardEdit == MODE_CARD_EDIT_EDIT){
		setReadOnly("txtEditActivateYear", true);
		setReadOnly("txtEditActivateMonth", true);
		setReadOnly("txtEditActivateDay", true);
		setReadOnly("pltEditHolidayType", true);
		setReadOnly("txtEditHolidayCode", true);
	}
}

/**
 * 追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
}

/**
 * 休暇区分変更時の処理を行う。<br>
 * @param event イベントオブジェクト
 */
function checkHolidayType(event) {
	// フィールド変更確認
	if (event != null) {
		onChangeFields(event);
	}
	// 休暇区分確認
	if (getFormValue("pltEditHolidayType") == TYPE_HOLIDAY_ABSENCE) {
		// 欠勤の場合
		checkableCheck("ckbNoLimit", true);
		setReadOnly("ckbNoLimit", true);
		setFormValue("pltEditSalary", TYPE_SALARY_PAY_NONE);
		setReadOnly("pltEditSalary", true);
	} else {
		// 欠勤以外の場合
		setReadOnly("ckbNoLimit", false);
		setReadOnly("pltEditSalary", false);
		setReadOnly("pltEditContinue", false);
	}
	// 無制限確認
	checkNoLimit(null);
}

/**
 * 無制限クリック時の処理を行う。<br>
 * @param イベントオブジェクト
 */
function checkNoLimit(event) {
	// 無制限チェック確認
	if (isCheckableChecked("ckbNoLimit")) {
		// チェックされている場合
		setReadOnly("txtEditHolidayGiving", true);
		setFormValue("txtEditHolidayGiving", "0.0");
		setReadOnly("txtEditHolidayLimitMonth", true);
		setFormValue("txtEditHolidayLimitMonth", "0");
		setReadOnly("txtEditHolidayLimitDay", true);
		setFormValue("txtEditHolidayLimitDay", "0");
	} else {
		// チェックされていない場合
		setReadOnly("txtEditHolidayGiving", false);
		setReadOnly("txtEditHolidayLimitMonth", false);
		setReadOnly("txtEditHolidayLimitDay", false);
	}
	// 連続取得の値及び読取専用を設定
	setHourlyHoliday();
}

/**
 * 標準付与日数チェック処理。
 * @param aryMessage メッセージ配列
 */
function checkHolidayGiving(aryMessage) {
	// 標準付与日数を確認
	checkGiving(aryMessage, "txtEditHolidayGiving");
}

/**
 * 時間単位取得変更時の処理を行う。
 * @param event イベントオブジェクト
 */
function changeHourlyHoliday(event) {
	// イベントオブジェクトが存在する場合
	if (event != null) {
		// フィールド変更時の処理を実施
		onChangeFields(event);
	}
	// 連続取得の値及び読取専用を設定
	setHourlyHoliday();
}

/**
 * 連続取得の値及び読取専用を設定する。
 */
function setHourlyHoliday() {
	// 連続取得の読取専用を解除
	setReadOnly("pltEditContinue", false);
	// 時間単位取得の値を取得
	var hourlyHoliday = getFormValue("pltEditHourlyHoliday");
	// 時間単位取得が有効であるか無制限がチェックされている場合
	if (hourlyHoliday == FLAG_HOURLY_VALID || isCheckableChecked("ckbNoLimit")) {
		// 連続取得を不要に設定
		setFormValue("pltEditContinue", TYPE_CONTINUOUS_UNNECESSARY);
		// 連続取得を読取専用に設定
		setReadOnly("pltEditContinue", true);
	}
}
