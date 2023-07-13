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
 * チェックボックス選択エラーメッセージ。
 */
var MSG_CHECKBOX_SELECT_ERROR = "PFW0233";

/**
 * 登録/削除確認メッセージ。
 */
var MSG_DIFFERENCE_REQUEST_CHECK	= "TMW0230";
var MSG_HOLIDAY_REQUIRED_CHECK		= "TMW0275";
var MSG_HOLIDAY_WARNING_CHECK		= "TMW0276";

/**
 * 画面読込時追加処理
 * @param 無し
 * @return 無し
 * @throws 実行時例外
 */
function onLoadExtra() {
	setOnChangeHandler("pltEditStartYear", changeEndDate);
	setOnChangeHandler("pltEditStartMonth", changeEndDate);
	setOnChangeHandler("pltEditStartDay", changeEndDate);
	setOnChangeHandler("pltEditHolidayType", checkEditHolidayType);
	setOnChangeHandler("pltEditStatusWithPay", checkEditHolidayType);
	checkEditHolidayType();
	setOnChangeHandler("pltEditHolidayRange1", changeEditTimePulldown);
	changeEditTimePulldown();
	setOnChangeHandler("pltEditStatusSpecial", changeHolidayType);
	setOnChangeHandler("pltEditSpecialOther", changeHolidayType);
	setOnChangeHandler("pltSearchHolidayType", checkSearchHolidayType);
	setOnChangeHandler("pltEditHolidayRange2", changeEditTimePulldown);
	changeEditTimePulldown();
	checkSearchHolidayType();
	// 有効日(編集)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可
		setDisabled("pltEditStartYear", true);
		setDisabled("pltEditStartMonth", true);
		setDisabled("pltEditStartDay", true);
		setDisabled("pltEditEndYear", true);
		setDisabled("pltEditEndMonth", true);
		setDisabled("pltEditEndDay", true);
		// 休暇種別編集可
		setDisabled("pltEditHolidayType", false);
		setDisabled("pltEditStatusWithPay", false);
		setDisabled("pltEditStatusSpecial", false);
		setDisabled("pltEditSpecialOther", false);
		setDisabled("pltEditSpecialAbsence", false);
		// 
		var startDate = getDateObject(getFormValue("pltEditStartYear"), getFormValue("pltEditStartMonth"), getFormValue("pltEditStartDay"));
		var endDate = getDateObject(getFormValue("pltEditEndYear"), getFormValue("pltEditEndMonth"), getFormValue("pltEditEndDay"));
		if(startDate.getTime() != endDate.getTime()) {
			setDisabled("pltEditHolidayRange1", true);
			setDisabled("pltEditHolidayRange2", true);
		} else {
			setDisabled("pltEditHolidayRange1", false);
			setDisabled("pltEditHolidayRange2", false);
		}
	} else {
		// 有効日編集可
		setDisabled("pltEditStartYear", false);
		setDisabled("pltEditStartMonth", false);
		setDisabled("pltEditStartDay", false);
		setDisabled("pltEditEndYear", false);
		setDisabled("pltEditEndMonth", false);
		setDisabled("pltEditEndDay", false);
		// 休暇種別編集不可
		setDisabled("pltEditHolidayType", true);
		setDisabled("pltEditStatusWithPay", true);
		setDisabled("pltEditStatusSpecial", true);
		setDisabled("pltEditSpecialOther", true);
		setDisabled("pltEditSpecialAbsence", true);
		setDisabled("pltEditHolidayRange1", true);
		setDisabled("pltEditHolidayRange2", true);
	}
	// 有効日(検索)モード確認
	if (jsSearchModeActivateDate  == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可
		setDisabled("pltSearchYear", true);
		setDisabled("pltSearchMonth", true);
		// 休暇種別編集可
		setDisabled("pltSearchHolidayType", false);
		setDisabled("pltSearchStatus", false);
		setDisabled("pltSearchStatusWithPay", false);
		setDisabled("pltSearchStatusSpecial", false);
		setDisabled("pltSearchSpecialOther", false);
		setDisabled("pltSearchSpecialAbsence", false);
		setDisabled("pltSearchHolidayRange", false);
		setDisabled("pltSearchHolidayRange1", false);
		// 検索ボタン利用可
		setReadOnly("btnSearch", false);
	} else {
		// 有効日編集可
		setDisabled("pltSearchYear", false);
		setDisabled("pltSearchMonth", false);
		// 休暇種別編集不可
		setDisabled("pltSearchHolidayType", true);
		setDisabled("pltSearchStatus", true);
		setDisabled("pltSearchStatusWithPay", true);
		setDisabled("pltSearchStatusSpecial", true);
		setDisabled("pltSearchSpecialOther", true);
		setDisabled("pltSearchSpecialAbsence", true);
		setDisabled("pltSearchHolidayRange", true);
		setDisabled("pltSearchHolidayRange1", true);
		// 検索ボタン利用不可
		setReadOnly("btnSearch", true);
	}
}

/**
 * 付与区分のチェック処理
 * @param 無し
 * @return 無し
 */
function checkEditHolidayType() {
	// 時間指定プルダウン利用不可
	setDisabled("pltEditStartHour", true);
	setDisabled("pltEditStartMinute", true);
	setDisabled("pltEditEndTime", true);
	var holidayType = getFormValue("pltEditHolidayType");
	if(holidayType == 1) {
		setObjectVisibility("pltEditStatusWithPay", true);
		setObjectVisibility("pltEditStatusSpecial", false);
		setObjectVisibility("pltEditSpecialOther", false);
		setObjectVisibility("pltEditSpecialAbsence", false);
		var holidayType2 = getFormValue("pltEditStatusWithPay");
		if (holidayType2 == 1) {
			// 有給休暇の場合
			setObjectVisibility("pltEditHolidayRange1", true);
			setObjectVisibility("pltEditHolidayRange2", false);
			// 時間休プルダウン設定
			changeEditTimePulldown();
		} else if (holidayType2 == 2) {
			// ストック休暇の場合
			setObjectVisibility("pltEditHolidayRange1", false);
			setObjectVisibility("pltEditHolidayRange2", true);
		}
	} else if(holidayType == 2) {
		setObjectVisibility("pltEditStatusWithPay", false);
		setObjectVisibility("pltEditStatusSpecial", true);
		setObjectVisibility("pltEditSpecialOther", false);
		setObjectVisibility("pltEditSpecialAbsence", false);
		setObjectVisibility("pltEditHolidayRange1", false);
		setObjectVisibility("pltEditHolidayRange2", true);
		// 時間休プルダウン設定
		changeEditTimePulldown();
	} else if(holidayType == 3) {
		setObjectVisibility("pltEditStatusWithPay", false);
		setObjectVisibility("pltEditStatusSpecial", false);
		setObjectVisibility("pltEditSpecialOther", true);
		setObjectVisibility("pltEditSpecialAbsence", false);
		setObjectVisibility("pltEditHolidayRange1", false);
		setObjectVisibility("pltEditHolidayRange2", true);
		// 時間休プルダウン設定
		changeEditTimePulldown();
	} else if(holidayType == 4) {
		setObjectVisibility("pltEditStatusWithPay", false);
		setObjectVisibility("pltEditStatusSpecial", false);
		setObjectVisibility("pltEditSpecialOther", false);
		setObjectVisibility("pltEditSpecialAbsence", true);
		setObjectVisibility("pltEditHolidayRange1", false);
		setObjectVisibility("pltEditHolidayRange2", true);
		// 時間休プルダウン設定
		changeEditTimePulldown();
	}
	changeHolidayType();
}

/**
 * 付与区分のチェック処理
 * @param 無し
 * @return 無し
 */
function checkSearchHolidayType() {
	var holidayType = getFormValue("pltSearchHolidayType");
	if(holidayType == 1) {
		setObjectVisibility("pltSearchStatus", false);
		setObjectVisibility("pltSearchStatusWithPay", true);
		setObjectVisibility("pltSearchStatusSpecial", false);
		setObjectVisibility("pltSearchSpecialOther", false);
		setObjectVisibility("pltSearchSpecialAbsence", false);
		setObjectVisibility("pltSearchHolidayRange", false);
		setObjectVisibility("pltSearchHolidayRange1", true);
	} else if(holidayType == 2) {
		setObjectVisibility("pltSearchStatus", false);
		setObjectVisibility("pltSearchStatusWithPay", false);
		setObjectVisibility("pltSearchStatusSpecial", true);
		setObjectVisibility("pltSearchSpecialOther", false);
		setObjectVisibility("pltSearchSpecialAbsence", false);
		setObjectVisibility("pltSearchHolidayRange", false);
		setObjectVisibility("pltSearchHolidayRange1", true);
	} else if(holidayType == 3) {
		setObjectVisibility("pltSearchStatus", false);
		setObjectVisibility("pltSearchStatusWithPay", false);
		setObjectVisibility("pltSearchStatusSpecial", false);
		setObjectVisibility("pltSearchSpecialOther", true);
		setObjectVisibility("pltSearchSpecialAbsence", false);
		setObjectVisibility("pltSearchHolidayRange", false);
		setObjectVisibility("pltSearchHolidayRange1", true);
	} else if(holidayType == 4) {
		setObjectVisibility("pltSearchStatus", false);
		setObjectVisibility("pltSearchStatusWithPay", false);
		setObjectVisibility("pltSearchStatusSpecial", false);
		setObjectVisibility("pltSearchSpecialOther", false);
		setObjectVisibility("pltSearchSpecialAbsence", true);
		setObjectVisibility("pltSearchHolidayRange", false);
		setObjectVisibility("pltSearchHolidayRange1", true);
	} else {
		setObjectVisibility("pltSearchStatus", true);
		setObjectVisibility("pltSearchStatusWithPay", false);
		setObjectVisibility("pltSearchStatusSpecial", false);
		setObjectVisibility("pltSearchSpecialOther", false);
		setObjectVisibility("pltSearchSpecialAbsence", false);
		setObjectVisibility("pltSearchHolidayRange", true);
		setObjectVisibility("pltSearchHolidayRange1", false);
	}
}

/**
 * 下書時の追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkDraftExtra(aryMessage, event) {
	// 処理無し
}

/**
 * 申請時の追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkRegistExtra(aryMessage, event) {
	checkDraftExtra(aryMessage, event);
	checkRequestReason(aryMessage, event);
}

/**
 * 時間休プルダウンの有効/無効設定を行う。<br>
 * @param 無し
 * @return 無し
 */
function changeEditTimePulldown() {
	// 時間休無効
	setDisabled("pltEditStartHour", true);
	setDisabled("pltEditStartMinute", true);
	setDisabled("pltEditEndTime", true);
	// 休暇範囲取得
	var holidayRange = getFormValue("pltEditHolidayRange1");
	var holidayRange2 = getFormValue("pltEditHolidayRange2");
	// 時間休の場合
	if(holidayRange == 4 || holidayRange2 == 4) {
		// 時間休有効
		setDisabled("pltEditStartHour", false);
		setDisabled("pltEditStartMinute", false);
		setDisabled("pltEditEndTime", false);
	}
}

/**
 * 休暇年月日(開始)変更時の処理を行う。
 * @param event イベントオブジェクト
 */
function changeEndDate(event) {
	var endYearId = "pltEditEndYear";
	var endMonthId = "pltEditEndMonth";
	var endDayId = "pltEditEndDay";
	// イベント発生元オブジェクトが休暇年月日(開始)年である場合
	if (getSrcElement(event).id == "pltEditStartYear") {
		// 休暇年月日(開始)年のオプションを休暇年月日(終了)年にコピー
		copySelectOptions("pltEditStartYear", endYearId);
	}
	setFormValue(endYearId, getFormValue("pltEditStartYear"));
	setFormValue(endMonthId, getFormValue("pltEditStartMonth"));
	setFormValue(endDayId, getFormValue("pltEditStartDay"));
	onChangeFields(event);
	var aryEndDate = [endYearId, endMonthId, endDayId];
	var aryEndDateLength = aryEndDate.length;
	for (var i = 0; i < aryEndDateLength; i++) {
		changeFieldBgColor(aryEndDate[i]);
	}
}

function changeHolidayType() {
	// リクエスト送信
	doSubmit(document.form, "TM1598");
}

/**
 * 一括申請時追加処理
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkBatchUpdateExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
	checkStatus(aryMessage, event, "下書");
}

/**
 * 一括取下時追加処理
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkBatchWithdrawnExtra(aryMessage, event) {
	// チェックボックス必須確認
	checkBoxRequired("ckbSelect", aryMessage);
	checkStatus(aryMessage, event, "未承認");
}

/**
 * オブジェクトの表示・非表示
 * @param テーブルid
 * @param  num
 * @return 無し
 * @throws 実行時例外
 */
function setObjectVisibility(target , isVisible) {
	var objTarget = getObject(target);
	if (objTarget == null) {
		return;
	}
	if (isVisible) {
		objTarget.style.display = "inline";
	} else {
		objTarget.style.display = "none";
	}
}

/**
 * 申請関連のリクエストを送信する。<br>
 * 入力チェックを行った後、更新系確認メッセージを出し、リクエストを送信する。<br>
 * 連続取得設定を確認し、警告メッセージを表示する。<br>
 * @param event イベントオブジェクト
 * @param cmd   コマンド
 * @return 無し
 */
function submitApplication(event, cmd) {
	// 連続取得警告メッセージ準備
	var continueMessage = "";
	// 使用日数よりも残数が多い場合(使用日数と残数が同じ場合は連続取得警告メッセージを設定しない)
	if (parseFloat(jsHolidayTerm) < parseFloat(jsHolidayRemainDay)) {
		// 連続取得が必須である場合
		if (jsHolidayContinue == "0") {
			// 連続取得警告メッセージを設定
			continueMessage = getMessage(MSG_HOLIDAY_REQUIRED_CHECK, "");
		}
		// 連続取得が警告である場合
		if (jsHolidayContinue == "1") {
			// 連続取得警告メッセージを設定
			continueMessage = getMessage(MSG_HOLIDAY_WARNING_CHECK, "");
		}
	}
	// 入力チェック対象準備
	var validateTarget = "divEdit";
	// メッセージ確認
	if (continueMessage.length != 0) {
		// 入力チェック
		if (validate(validateTarget, checkRegistExtra, event)) {
			// 更新系確認メッセージ
			if (confirm(continueMessage)) {
				// リクエスト送信
				doSubmit(document.form, cmd);
			}
		}
	} else {
		submitRegist(event, validateTarget, checkRegistExtra, cmd);
	}
}

/**
 * 追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkDateExtra(aryMessage, event) {
	checkDate("pltEditStartYear", "pltEditStartMonth", "pltEditStartDay", aryMessage);
	checkDate("pltEditEndYear", "pltEditEndMonth", "pltEditEndDay", aryMessage);
}

/**
 * 追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkRequestReason(aryMessage, event) {
	var holidayType = getFormValue("pltEditHolidayType");
	if (holidayType != "1") {
		// 有給休暇でない場合
		return;
	}
	// 有給休暇である場合
	if (jsPaidHolidayReasonRequired) {
		// 申請理由が必須である場合
		checkRequired("txtEditRequestReason", aryMessage);
	}
}

/**
 * 状態確認。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @param status 状態
 * @return 無し
 */
function checkStatus(aryMessage, event, status) {
	var checkbox = document.getElementsByName("ckbSelect");
	var checkboxLength = checkbox.length;
	for (var i = 0; i < checkboxLength; i++) {
		if (!isCheckableChecked(checkbox[i])) {
			// チェックボックス確認
			continue;
		}
		// チェックボックスが含まれるTR要素取得
		var trElement = checkbox[i].parentNode.parentNode;
		// TR要素内TD要素群取得
		var tdElements = getElementsByTagName(trElement, TAG_TD);
		// TD要素内A要素群取得
		var aElements = getElementsByTagName(tdElements.item(5), TAG_A);
		// A要素内SPAN要素群取得
		var spanElements = getElementsByTagName(aElements.item(0), "SPAN");
		if (spanElements.item(0).firstChild.nodeValue != status) {
			var rep = [trimAll(getInnerHtml(getSrcElement(event))), status];
			aryMessage.push(getMessage(MSG_CHECKBOX_SELECT_ERROR, rep));
			return;
		}
	}
}

