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
	// 人事汎用機能参照権限の場合
	if (jsIsReferenceDivision) {
		// 可視設定
		setVisibility("btnRegist", false);
		setVisibility("btnDelete", false);
		// 確認対象要素群取得（INPUT）
		setReadOnlyForTag(TAG_INPUT,true);
		// 確認対象要素群取得（SELECT）
		setReadOnlyForTag(TAG_SELECT,true);
		// 確認対象要素群取得（TAG_TEXTAREA）
		setReadOnlyForTag(TAG_TEXTAREA,true);
		return;
	}
}

/**
 * 対象ファイルが指定されているかを確認する。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event      イベントオブジェクト
 */
function checkExtra(aryMessage, event) {
	// 対象ファイルID宣言
	var target = "fileBinaryNormal";
	// 対象ファイルが設定されているか確認
	checkRequired(target, aryMessage);
}
