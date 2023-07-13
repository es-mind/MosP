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
	if (modeCardEdit == MODE_CARD_EDIT_INSERT) {
		setReadOnly("btnRegist", true);
	}
}

var MSG_RELEASE = "承認解除申請" ;

/**
 * 入力チェックを行った後、更新系確認メッセージを出し、リクエストを送信する。<br>
 * データ登録、更新時等に用いる。<br>
 * @param event          イベントオブジェクト
 * @param validateTarget 入力チェック対象(null：チェックを行わない、""：全体をチェック)
 * @param objExtraCheck  追加チェック関数オブジェクト(null：追加チェック無し)
 * @param cmd            コマンド
 * @return 無し
 */
function submitRegist(event, validateTarget, objExtraCheck, cmd) {
	// 入力チェック
	if (validate(validateTarget, objExtraCheck, event)) {
		// 更新系確認メッセージ
		if (confirm(getMessage(MSG_REGIST_CONFIRMATION, MSG_RELEASE))) {
			// リクエスト送信
			doSubmit(document.form, cmd);
		}
	}
}

/**
 * 追加チェック
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkExtra(aryMessage, event) {
	if (getFormValue("pltSearchRequestDay") != "") {
		checkDate("pltSearchRequestYear", "pltSearchRequestMonth", "pltSearchRequestDay", aryMessage);
	}
}
