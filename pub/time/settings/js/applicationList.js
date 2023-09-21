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
	// 有効日(編集)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可
		setReadOnly("txtSearchActivateYear", true);
		setReadOnly("txtSearchActivateMonth", true);
		setReadOnly("txtSearchActivateDay", true);
		setReadOnly("txtSearchEmployeeCode", false);
		setReadOnly("radMaster", false);
		setReadOnly("radEmployeeCode", false);
		setReadOnly("btnSearch", false);
	} else {
		setReadOnly("txtSearchActivateYear", false);
		setReadOnly("txtSearchActivateMonth", false);
		setReadOnly("txtSearchActivateDay", false);
		setReadOnly("txtSearchEmployeeCode", true);
		setReadOnly("radMaster", true);
		setReadOnly("radEmployeeCode", true);
		setReadOnly("btnSearch", true);
	}
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
		setReadOnly("pltSearchWorkPlaceMaster", false);
		setReadOnly("pltSearchEmploymentMaster", false);
		setReadOnly("pltSearchSectionMaster", false);
		setReadOnly("pltSearchPositionMaster", false);
		setReadOnly("txtSearchEmployeeCode", true);
	} else {
		setReadOnly("pltSearchWorkPlaceMaster", true);
		setReadOnly("pltSearchEmploymentMaster", true);
		setReadOnly("pltSearchSectionMaster", true);
		setReadOnly("pltSearchPositionMaster", true);
		setReadOnly("txtSearchEmployeeCode", false);
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
