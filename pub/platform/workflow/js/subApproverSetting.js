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
	}
	// 履歴編集モード
	if (modeCardEdit == MODE_CARD_EDIT_EDIT){
		// フロー区分編集不可
		setDisabled("pltEditWorkflowType", true);
		// 有効日編集不可
		setReadOnly("btnEditActivateDate", true);
	}
	// 有効日決定状態
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 代理開始日編集不可
		setReadOnly("txtEditActivateYear", true);
		setReadOnly("txtEditActivateMonth", true);
		setReadOnly("txtEditActivateDay", true);
	}
	// 有効日変更状態
	if (modeActivateDate == MODE_ACTIVATE_DATE_CHANGING) {
		// 登録ボタン利用不可
		setReadOnly("btnRegist", true);
		// 社員検索ボタン利用不可
		setReadOnly("btnEmployeeSearch", true);
	}
	// 代理人確認
	if (getFormValue("pltEditEmployeeName") == "") {
		// 登録ボタン利用不可
		setReadOnly("btnRegist", true);
	}
}
