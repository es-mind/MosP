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
	// 新規登録
	if (modeCardEdit == MODE_CARD_EDIT_INSERT){
		setReadOnly("pltEditInactivate", true);
	}
	// 履歴編集
	if (modeCardEdit == MODE_CARD_EDIT_EDIT){
		setReadOnly("txtEditActivateYear", true);
		setReadOnly("txtEditActivateMonth", true);
		setReadOnly("txtEditActivateDay", true);
		setDisabled("txtUnitCode", true);
		// 決定ボタン押下不可
		setReadOnly("btnActivateDate", true);
		// ラジオボタン押下不可
		setReadOnly("radioSectionPosition", true);
		setReadOnly("radioEmployeeCode", true);
	}
	// 履歴追加
	if (modeCardEdit == MODE_CARD_EDIT_ADD){
		setDisabled("txtUnitCode", true);
		// ラジオボタン押下不可
		setReadOnly("radioSectionPosition", true);
		setReadOnly("radioEmployeeCode", true);
	}
	// 有効日(編集)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可
		setReadOnly("txtEditActivateYear", true);
		setReadOnly("txtEditActivateMonth", true);
		setReadOnly("txtEditActivateDay", true);
	} else {
		// 登録ボタン利用不可
		setReadOnly("btnRegist", true);
		// ラジオボタン押下不可
		setReadOnly("radioSectionPosition", true);
		setReadOnly("radioEmployeeCode", true);
	}
	// ユニット区分にオンチェンジイベントハンドラを設定
	setOnChangeHandler("radioSectionPosition", onChangeUnitType);
	setOnChangeHandler("radioEmployeeCode", onChangeUnitType);
	// 承認者設定入力切替
	onChangeUnitType(null);
	// ユニット承認者設定変更イベントハンドラ設定
	setOnChangeHandler("pltSectionMaster", onChangeApploval);
	setOnChangeHandler("pltPositionMaster", onChangeApploval);
	setOnChangeHandler("pltPositionGradeRange", onChangeApploval);
	setOnChangeHandler("txtEmployeeCode", onChangeApploval);
}

/**
 * 追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkExtra(aryMessage, event) {
	// 承認者設定入力切替
	if (isCheckableChecked("radioSectionPosition")) {
		// 所属の場合
		inputCheckForSection("pltSectionMaster", aryMessage);
		inputCheckForSection("pltPositionMaster", aryMessage);
	} else {
		// 個人の場合
		inputCheckForPerson("txtEmployeeCode", aryMessage);
	}
}

/**
 * ユニット区分変更時の処理を行う。<br>
 * @param event イベントオブジェクト
 */
function onChangeUnitType(event) {
	if (event != null) {
		onChangeFields(event);
	}
	// 有効日(編集)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 新規登録モードの場合、所属または職位のプルダウンにデータが
		// セットされていなければ所属指定はできないものとする。
		if (modeCardEdit == MODE_CARD_EDIT_INSERT){
			if (!checkPulldownItemExist()) {
				setReadOnly("radioSectionPosition" , true);
				setReadOnly("radioEmployeeCode" , true);
			}else{
				setReadOnly("radioSectionPosition" , false);
				setReadOnly("radioEmployeeCode" , false);
			}
		}
		// 承認者設定入力切替
		if (isCheckableChecked("radioSectionPosition")) {
			// 所属の場合
			setDisabled("pltSectionMaster" , false);
			setDisabled("pltPositionMaster", false);
			setDisabled("pltPositionGradeRange", false);
			setDisabled("txtEmployeeCode", true);
		} else {
			// 個人の場合
			setDisabled("pltSectionMaster" , true);
			setDisabled("pltPositionMaster", true);
			setDisabled("pltPositionGradeRange", true);
			setDisabled("txtEmployeeCode", false);
		}
	} else {
		setDisabled("pltSectionMaster" , true);
		setDisabled("pltPositionMaster", true);
		setDisabled("pltPositionGradeRange", true);
		setDisabled("txtEmployeeCode", true);
	}
}

/**
 * 所属指定時の入力値の確認を行う。<br>
 * フィールド背景色の初期化、入力チェックを行う。<br>
 * 入力チェックでエラーがあった場合、メッセージ配列にメッセージが追加される。<br>
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage メッセージ配列
 */
function inputCheckForSection(target, aryMessage) {
	// 対象範囲要素取得
	var objTarget = getObject(target);
	// フィールド背景色初期化
	resetFieldsBgColor(objTarget);
	// 入力チェック
	checkRequired(objTarget, aryMessage);
}

/**
 * 個人指定時の入力値の確認を行う。<br>
 * フィールド背景色の初期化、テキストボックス前後空白除去、
 * クラスによる入力チェックを行う。<br>
 * 入力チェックでエラーがあった場合、メッセージ配列にメッセージが追加される。<br>
 * @param target     確認対象(StringあるいはObject)
 * @param aryMessage メッセージ配列
 */
function inputCheckForPerson(target, aryMessage) {
	// 対象範囲要素取得
	var objTarget = getObject(target);
	// テキストボックス前後空白除去
	trimTextValue(objTarget);
	// フィールド背景色初期化
	resetFieldsBgColor(objTarget);
	// 入力チェック
	checkRequired(objTarget, aryMessage)
}

/**
 * 所属または職位のプルダウンに有効なデータがあるか確認を行う。<br>
 * @return チェック結果(true：有効データあり、false：有効データなし)
 */
function checkPulldownItemExist() {
	if (getSelectOptionLength("pltSectionMaster") == 1 || getSelectOptionLength("pltPositionMaster") == 1) {
		return false;
	} else {
		return true;
	}
}

/**
 * ユニット承認者設定変更時の処理を行う。<br>
 * @param event イベントオブジェクト
 */
function onChangeApploval(event) {
	// フィールド変更時処理実施
	onChangeFields(event);
	// 適用対象者氏名消去
	setInnerHtml("lblEmployeeName", "");
}
