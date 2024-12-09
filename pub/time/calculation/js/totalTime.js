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

var MSG_CONFIRM_CUTOFF = "TMQ3001";

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
		setReadOnly("pltEditRequestYear", true);
		setReadOnly("pltEditRequestMonth", true);
		setReadOnly("btnSearch", false);
	} else {
		setReadOnly("pltEditRequestYear", false);
		setReadOnly("pltEditRequestMonth", false);
		setReadOnly("btnSearch", true);
		setReadOnly("pltEditCutoffDate", true);
	}
}

/**
 * 仮締及び確定時の
 * 確認メッセージを表示する。
 * @param event イベントオブジェクト
 * @return 確認結果
 */
function confirmCutoff(event) {
	var cutoffYear =  getFormValue("pltEditRequestYear")
	var cutoffMonth	= getFormValue("pltEditRequestMonth")
	 // 問合中フラグ確認
	if (inquiring) {
		return false;
	}
  	 return (confirm(getMessage(MSG_CONFIRM_CUTOFF, new Array(cutoffYear, cutoffMonth, getInnerHtml(getSrcElement(event)))))) 
 
}
