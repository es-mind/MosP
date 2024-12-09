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
 * 画面背景色。
 */
var COLOR_TABLE_TODAY = "#ccffcc";

/**
 * 1時間あたりの分数。
 */
var NUM_DEFINITION_HOUR = 60;

/**
 * ポータル画面用日付曜日時刻表示
 */
function time(){
	var now = new Date();
	var weeks = new Array("日","月","火","水","木","金","土") ;

	var year = now.getFullYear() ;
	var month = now.getMonth() + 1 ;
	var date = now.getDate() ;
	var week = weeks[now.getDay()] ;
	
	var hour = now.getHours(); // 時
	var min = now.getMinutes(); // 分
	var sec = now.getSeconds(); // 秒

	// 数値が1桁の場合、頭に0を付けて2桁で表示する指定
	if(hour < 10) { hour = "0" + hour; }
	if(min < 10) { min = "0" + min; }
	if(sec < 10) { sec = "0" + sec; }

	// フォーマットを指定（不要な行を削除する）
	var watch1 = year + '年' + month + '月' + date + '日（' + week + '）' + hour + ':' + min + ':' + sec;

	// テキストフィールドにデータを渡す処理（不要な行を削除する）
	document.form.clock.value = watch1;

	setTimeout("time()", 1000);
}

/**
 * 勤怠一覧用当日背景色設定
 * @param strTargetTable 設定対象テーブルオブジェクトID
 * @return 無し
 * @throws 実行時例外
 */
function setToDayTableColor(strTargetTable){
	
	var objTable = getObject(strTargetTable);
	if (objTable == null) {
		return;
	}
	var objTarget = null;
	var numHeader = 0;
	var numFooter = 0;
	if (objTable.tHead) {
		numHeader = objTable.tHead.rows.length;
	}
	if (objTable.tFoot) {
		numFooter = objTable.tFoot.rows.length;
	}

	var trElements = getElementsByTagName(objTable, TAG_TR);
	
	// システム日付取得
	var now = new Date() ;
	
	// システム日付(時間)初期化
	now.setHours(0) ;
	// システム日付(分)初期化
	now.setMinutes(0) ;
	// システム日付(秒)初期化
	now.setSeconds(0) ;
	// システム日付(ミリ秒)初期化
	now.setMilliseconds(0) ;
	
	// システム日付をミリ秒単位に変換(Date → long)
	var targetDate = now.getTime();
	
	var rowslength = objTable.rows.length - numFooter;
	for (var i = numHeader; i < rowslength; i ++) {
		var trElement = trElements.item(i);
		var tdElements = getElementsByTagName(trElement, TAG_TD);
		if (tdElements.length > 0 && tdElements.item(1) != null) {
			// 日付をミリ秒単位に変換(String → long)
			var chackDate = Date.parse( tdElements.item(1).title );
			objTable.rows[i].bgColor = chackDate == targetDate ?  COLOR_TABLE_TODAY : objTable.rows[i].bgColor ;
			objTable.rows[i].onmouseout = function() { this.bgColor = Date.parse(getElementsByTagName(trElements.item(this.rowIndex), TAG_TD).item(1).title) == targetDate ? COLOR_TABLE_TODAY: this.rowIndex % 2 == 0 ? COLOR_TABLE_ROW2 : COLOR_TABLE_ROW1;};
		}
	}
}

/**
 * フォーム入力欄から数値を取得する。
 * 数値を取得できなかった場合は、0を返す。
 * @param target 取得対象(StringあるいはObject)
 * @return 数値
 */
function getNumber(target) {
	// 数値を取得
	var number = parseInt(getFormValue(target), 10);
	// 数値を取得できなかった場合
	if (isNaN(number)) {
		// 0を取得
		return 0;
	}
	// 数値を取得
	return number;
}

/**
 * フォーム入力欄(時間と分)から時刻(分)を取得する。
 * @param targetHours   取得対象(時間)(StringあるいはObject)
 * @param targetMinutes 取得対象(分)(StringあるいはObject)
 * @return 時刻(分)
 */
function getTimeAsMinutes(targetHours, targetMinutes) {
	// 時間を取得
	var hours = getNumber(targetHours);
	// 分を取得
	var minutes = getNumber(targetMinutes);
	// 時刻(分)を取得
	return hours * NUM_DEFINITION_HOUR + minutes;
}
