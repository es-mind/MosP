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
 * 登録/削除確認メッセージ。
 */
var MSG_ALL_DELETE	= "PFQ1006";


/**
 * 履歴削除ボタン押下時チェック
 * <br>
 * @param event イベントオブジェクト
 */
function checkExtra(event) {
	// 削除系確認メッセージ
	if (!confirm(getMessage(MSG_DELETE_CONFIRMATION, getInnerHtml(getSrcElement(event))))) {
		return false;
	}
	// 最終履歴フラグがOFFの場合
	if (jsIsLastHistory == false) {
		return true;
	}
	// 履歴が1件しか存在しない場合は、削除再確認
	//return confirm(getMessage(MSG_ALL_DELETE, null));
}
