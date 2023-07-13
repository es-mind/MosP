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
 * 半休取得時休憩相関チェックエラーメッセージ。
 */
var MSG_HALFDAY_ACQUISITION	= "TMW0212";

/**
 * 未入力時のメッセージコード。
 */
var MSG_REQUIRED = "PFW0102"
	
/**
 * 入力時間超過時チェックエラーメッセージ。
 */
var MSG_INVALID_TIME = "TMW0310"
	
/**
 * 追加チェック処理配列。
 */
var aryCheckExtra = new Array();


/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	// 勤務/休憩時間欄を入力不可にする（現状では出力のみの項目）
	setDisabled("txtWorkTimeHour", true);
	setDisabled("txtWorkTimeMinute", true);
	setDisabled("txtRestTimeHour", true);
	setDisabled("txtRestTimeMinute", true);
	// 新規登録モード
	if (modeCardEdit == MODE_CARD_EDIT_INSERT){
		setDisabled("pltEditInactivate", true);
	}
	// 履歴追加モード
	if (modeCardEdit == MODE_CARD_EDIT_ADD){
		setDisabled("txtWorkTypeCode", true);
	}
	// 履歴編集モード
	if (modeCardEdit == MODE_CARD_EDIT_EDIT){
		setDisabled("txtEditActivateYear", true);
		setDisabled("txtEditActivateMonth", true);
		setDisabled("txtWorkTypeCode", true);
	}
}


/**
 * 画面読込時追加処理
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 * @throws 実行時例外
 */
function checkExtra(aryMessage, event) {
	// 半休休憩時間の相関チェック
	checkHalfTimes(aryMessage, event);
	// 残業休憩時間大小チェック
	checkInvalidTimeOver(aryMessage, event);
	// 時短勤務チェック
	checkShortTime(aryMessage, event);
	// 追加チェック処理を実行
	executeCheckExtras(aryCheckExtra, aryMessage, event);
}

/**
 * 半休休憩時間の相関チェック後、休憩時間チェックのcheckRestTimeと
 * time.jsのcheckTimesを呼び出す。<br>
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkHalfTimes(aryMessage, event) {
	if(checkDatesYearMonth(aryMessage, event) == false){
		return;
	}
	// 半休休憩時間の相関チェック
	var halfRestHour  = getFormValue("txtHalfRestHour");
	var halfRestMinute  = getFormValue("txtHalfRestMinute");
	var halfRestStartHour  = getFormValue("txtHalfRestStartHour");
	var halfRestStartMinute  = getFormValue("txtHalfRestStartMinute");
	var halfRestEndHour  = getFormValue("txtHalfRestEndHour");
	var halfRestEndMinute  = getFormValue("txtHalfRestEndMinute");
	// どれか一つでも入力されていたら相関チェックを行う
	if( halfRestHour != "" || halfRestMinute != "" || halfRestStartHour != "" ||
			halfRestStartMinute != "" || halfRestEndHour != "" || halfRestEndMinute != "" ){
		if( halfRestHour == "" || halfRestMinute == "" || halfRestStartHour == "" ||
				halfRestStartMinute == "" || halfRestEndHour == "" || halfRestEndMinute == "" ){
			if (aryMessage.length == 0) {
				setFocus("txtHalfRestHour");
			}
			setBgColor("txtHalfRestHour", COLOR_FIELD_ERROR);
			aryMessage.push(getMessage(MSG_HALFDAY_ACQUISITION, null));
			return;
		}
	}
	// 休憩1～4のチェック
	if(checkRestTime(aryMessage, event) == false ){
		return;
	}
	// 時刻チェック
	checkTimes(aryMessage, event);
}

/**
 * 休憩1～4の相関チェックを行う。<br>
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkRestTime(aryMessage, event) {
	// 休憩1確認
	checkAllOrNone("txtRestStart1Hour", "txtRestStart1Minute", "txtRestEnd1Hour", "txtRestEnd1Minute", aryMessage);
	// 休憩2確認
	checkAllOrNone("txtRestStart2Hour", "txtRestStart2Minute", "txtRestEnd2Hour", "txtRestEnd2Minute", aryMessage);
	// 休憩3確認
	checkAllOrNone("txtRestStart3Hour", "txtRestStart3Minute", "txtRestEnd3Hour", "txtRestEnd3Minute", aryMessage);
	// 休憩4確認
	checkAllOrNone("txtRestStart4Hour", "txtRestStart4Minute", "txtRestEnd4Hour", "txtRestEnd4Minute", aryMessage);
}

/**
 * 設定時間大小チェック<br>
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkInvalidTimeOver(aryMessage, event) {
	// 引数取得(数値に変換)
	var overPerHour    = Number(getFormValue("txtOverPerHour"));
	var overPerMinute  = Number(getFormValue("txtOverPerMinute"));
	var overRestHour   = Number(getFormValue("txtOverRestHour"));
	var overRestMinute = Number(getFormValue("txtOverRestMinute"));
	
	// 時間を分に戻して加算
	var overPerTime  = (overPerHour * 60) +  overPerMinute;
	var overRestTime = (overRestHour * 60) +  overRestMinute;
	
	// 大小チェック
	if (overPerTime < overRestTime) {
		// フォーカス設定
		setFocus("txtOverRestHour");
		// 背景色変更
		setBgColor("txtOverPerHour"   , COLOR_FIELD_ERROR);
		setBgColor("txtOverPerMinute" , COLOR_FIELD_ERROR);
		setBgColor("txtOverRestHour"  , COLOR_FIELD_ERROR);
		setBgColor("txtOverRestMinute", COLOR_FIELD_ERROR);
		// メッセージ設定
		aryMessage.push(getMessage(MSG_INVALID_TIME , new Array(getLabel("txtRestTimeHour"), getLabel("txtOverPerHour"))));
		return false;
	}
	
	return true;
}

/**
 * 時短勤務の入力チェックを行う。
 * @param aryMessage メッセージ配列
 * @param event      イベントオブジェクト
 */
function checkShortTime(aryMessage, event) {
	// 時短勤務1確認
	checkAllOrNone("txtShort1StartHour", "txtShort1StartMinute", "txtShort1EndHour", "txtShort1EndMinute", aryMessage);
	// 時短勤務2確認
	checkAllOrNone("txtShort2StartHour", "txtShort2StartMinute", "txtShort2EndHour", "txtShort2EndMinute", aryMessage);
}

/**
 * 全ての値が空白、又は全ての値が空白でないことを確認する。
 * @param id1        入力項目1ID
 * @param id2        入力項目2ID
 * @param id3        入力項目3ID
 * @param id4        入力項目4ID
 * @param aryMessage メッセージ配列
 */
function checkAllOrNone(id1, id2, id3, id4, aryMessage) {
	// 入力値取得
	var val1 = getFormValue(id1);
	var val2 = getFormValue(id2);
	var val3 = getFormValue(id3);
	var val4 = getFormValue(id4);
	// 全ての値が空白の場合
	if (val1 == "" && val2 == "" && val3 == "" && val4 == "") {
		// エラー無し
		return;
	}
	// 空白の値が無い場合
	if (val1 != "" && val2 != "" && val3 != "" && val4 != "") {
		// エラー無し
		return;
	}
	// フォーカス設定
	if (aryMessage.length == 0) {
		setFocus(id1);
	}
	// 背景色設定
	setBgColor(id1, COLOR_FIELD_ERROR);
	// エラーメッセージ設定
	aryMessage.push(getMessage(MSG_REQUIRED , getLabel(id1)));
}

