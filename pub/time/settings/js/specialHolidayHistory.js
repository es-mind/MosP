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
	// 休暇種別が有効なら付与日数と取得期限を設定する
	setOnChangeHandler("pltEditHolidayType", checkHolidayType);
	// 編集項目
	if (jsEditActivateDate == "fixed"){
		setDisabled("txtEditActivateYear", true);
		setDisabled("txtEditActivateMonth", true);
		setDisabled("txtEditActivateDay", true);
		setDisabled("txtEditEmployeeCode", true);
		setReadOnly("btnRegist", false);
	} else {
		setDisabled("txtEditEmployeeCode", false);
		setReadOnly("btnRegist", true);
	}

	if (modeCardEdit == MODE_CARD_EDIT_EDIT) {
		setDisabled("pltEditHolidayType", true);
		if(jsEditActivateDate == "fixed"){
			setReadOnly("btnActivateDate", true);
		} else {
			setReadOnly("btnActivateDate", false);
		}
	} else {
		setDisabled("pltEditHolidayType", false);
		setDisabled("pltEditInactivate", true);
		setReadOnly("btnActivateDate", false);
	}
	
	// 検索項目
	if (jsSearchActivateDate  == "fixed"){
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
	// 休暇種別選択後の付与日数、取得期限。
	if (jsEditNoLimit){
		setDisabled("txtEditHolidayGiving", true);
		setDisabled("txtEditHolidayLimitMonth", true);
		setDisabled("txtEditHolidayLimitDay", true);
	} else {
		setDisabled("txtEditHolidayGiving", false);
		setDisabled("txtEditHolidayLimitMonth", false);
		setDisabled("txtEditHolidayLimitDay", false);
	}

}

/**
 * 休暇種別のチェック処理
 * @param 無し
 * @return 無し
 */
function checkHolidayType() {
	// リクエスト送信
	doSubmit(document.form, 'TM4226');
}

/**
 * 標準付与日数チェック処理。
 * @param aryMessage メッセージ配列
 */
function checkHolidayGiving(aryMessage) {
	checkGiving(aryMessage, "txtEditHolidayGiving");
}
