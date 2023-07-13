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
 * 画面読込時追加処理。
 */
function onLoadExtra() {
	// 勤怠一覧用当日背景色設定
	setToDayTableColor("list")
	// 勤怠年変更イベント設定
	setOnChangeHandler("pltSelectYear", changeSelect);
	// 勤怠年変更イベント設定
	setOnChangeHandler("pltSelectMonth", changeSelect);
}

/**
 * 年月変更時の処理。
 * @param event イベントオブジェクト
 */
function changeSelect(event) {
	// リクエスト送信
	submitForm(event, null, null, getFormValue("hdnSearchCommand"));
}

