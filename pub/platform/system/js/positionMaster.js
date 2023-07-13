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
	// 編集モード確認
	if (modeCardEdit == MODE_CARD_EDIT_INSERT){
		setDisabled("pltEditInactivate", true);
	}
	if (modeCardEdit == MODE_CARD_EDIT_ADD){
		setDisabled("txtEditPositionCode", true);
	}
	if (modeCardEdit == MODE_CARD_EDIT_EDIT){
		setDisabled("txtEditActivateYear", true);
		setDisabled("txtEditActivateMonth", true);
		setDisabled("txtEditActivateDay", true);
		setDisabled("txtEditPositionCode", true);
	}
}

/**
 * 追加チェック
 * @param aryMessage エラーメッセージ格納配列
 * @return 無し
 */
function checkExtra(aryMessage, event) {
	checkBoxRequired("ckbSelect", aryMessage);
}
