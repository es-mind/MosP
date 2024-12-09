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
 * 画面読込時の追加処理を行う。
 */
function onLoadExtra() {
	// 選択状態確認(選択されていない場合)
	if (getRadioSelectedValue("radSelect") == "") {
		// 有効日編集不可
		setDisabled("txtActivateYear", true);
		setDisabled("txtActivateMonth", true);
		setDisabled("txtActivateDay", true);
		setReadOnly("btnActivateDate", true);
		// 条件編集不可
		setDisabled("pltWorkPlace", true);
		setDisabled("pltEmployment", true);
		setDisabled("pltSection", true);
		setDisabled("pltPosition", true);
		// 出力ボタン利用不可
		setReadOnly("btnExecute", true);
	}
	// 有効日決定状態
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可
		setDisabled("txtActivateYear", true);
		setDisabled("txtActivateMonth", true);
		setDisabled("txtActivateDay", true);
	}
	// 有効日変更状態
	if (modeActivateDate == MODE_ACTIVATE_DATE_CHANGING) {
		// 出力ボタン利用不可
		setReadOnly("btnExecute", true);
	}
}
