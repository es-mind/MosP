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
 * 未入力時のメッセージコード。
 */
var MSG_UNIT_DUPLICATED = "PFW0221"
var REP_LABEL			= "ユニット"

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
		// 有効日編集不可
		setReadOnly("txtEditActivateYear", true);
		setReadOnly("txtEditActivateMonth", true);
		setReadOnly("txtEditActivateDay", true);
		setDisabled("txtRouteCode", true);
		// 階層数選択不可
		setReadOnly("pltRouteStage", true);
		// 決定ボタン押下不可
		setReadOnly("btnActivateDate", true);
	}
	// 履歴追加
	if (modeCardEdit == MODE_CARD_EDIT_ADD){
		setDisabled("txtRouteCode", true);
	}
	// 有効日(編集)モード確認
	if (modeActivateDate == MODE_ACTIVATE_DATE_FIXED) {
		// 有効日編集不可
		setReadOnly("txtEditActivateYear", true);
		setReadOnly("txtEditActivateMonth", true);
		setReadOnly("txtEditActivateDay", true);
		// 階層数選択不可
		setReadOnly("pltRouteStage", true);
	} else {
		// 登録ボタン利用不可
		setReadOnly("btnRegist", true);
	}
	// ユニット承認者設定変更イベントハンドラ設定
	var routeStage = getObject("pltRouteStage");
	var approvalCount = getFormValue(routeStage);
	setOnChangeHandler("pltUnitStage1", onChangeUnit);
	if (approvalCount > 1) {
		setOnChangeHandler("pltUnitStage2", onChangeUnit);
	}
	if (approvalCount > 2) {
		setOnChangeHandler("pltUnitStage3", onChangeUnit);
	}
	if (approvalCount > 3) {
		setOnChangeHandler("pltUnitStage4", onChangeUnit);
	}
	if (approvalCount > 4) {
		setOnChangeHandler("pltUnitStage5", onChangeUnit);
	}
	if (approvalCount > 5) {
		setOnChangeHandler("pltUnitStage6", onChangeUnit);
	}
	if (approvalCount > 6) {
		setOnChangeHandler("pltUnitStage7", onChangeUnit);
	}
	if (approvalCount > 7) {
		setOnChangeHandler("pltUnitStage8", onChangeUnit);
	}
	if (approvalCount > 8) {
		setOnChangeHandler("pltUnitStage9", onChangeUnit);
	}
	if (approvalCount > 9) {
		setOnChangeHandler("pltUnitStage10", onChangeUnit);
	}

}

/**
 * 追加チェックを行う。<br>
 * @param aryMessage エラーメッセージ格納配列
 * @param event イベント
 * @return 無し
 */
function checkExtra(aryMessage, event) {

	var routeStage = getObject("pltRouteStage");
	var approvalCount = getFormValue(routeStage);
	var aryUnitStage = new Array("pltUnitStage1", "pltUnitStage2", "pltUnitStage3", "pltUnitStage4","pltUnitStage5","pltUnitStage6", "pltUnitStage7", "pltUnitStage8", "pltUnitStage9","pltUnitStage10");
	
	// 階層数分のユニットが選択されているかチェックを行う。
	for( var i=0 ; i<approvalCount ; i++ ){
		// 対象範囲要素取得
		var unitStageObj = getObject(aryUnitStage[i]);
		// フィールド背景色初期化
		resetFieldsBgColor(unitStageObj);
		// 入力チェック
		checkRequired(unitStageObj, aryMessage);
		// 入力エラー時は処理を終了する。
		if (aryMessage.length != 0) {
			return;
		}
	}
	
	// ユニットが重複して選択されていないかチェックを行う。
	for( var i=0 ; i<approvalCount ; i++ ){
		// 対象範囲要素取得
		var unitStageObj = getObject(aryUnitStage[i]);
		// 選択されたユニットコード参照
		var unitStage = getFormValue(unitStageObj);
		// 他の選択ユニットと比較する。
		for( var j=0 ; j<approvalCount ; j++ ){
			if (j != i) {
				// 比較対象オブジェクト取得
				var unitStageObjCompare = getObject(aryUnitStage[j]);
				// ユニットコード参照
				var unitStageCompare = getFormValue(unitStageObjCompare);
				if (unitStage == unitStageCompare) {
					// ユニットコードが同一の場合はエラーメッセージ設定。
					aryMessage.push(getMessage(MSG_UNIT_DUPLICATED , REP_LABEL));
					return;
				}
			}
		}
	}
	
}

/**
 * ユニット変更時の承認者名欄の表示制御を行う。<br>
 * @param event イベントオブジェクト
 * @return 無し
 */
function onChangeUnit(event) {

	// フィールド変更時処理実施
	onChangeFields(event);
	// 承認者名欄クリア
	var objTarget = getSrcElement(event);
	clearEmployeeName(objTarget);
	
}

/**
 * 承認者名欄をクリアする。<br>
 * @param target 対象オブジェクト(StringあるいはObject)
 * @return 無し
 */
function clearEmployeeName(target) {
	// 対象オブジェクト取得
	var objTarget = getObject(target);
	// 適用対象者氏名消去
	switch (objTarget.id){
	case "pltUnitStage1":
		setInnerHtml("tdEmployeeName1", "");
		break;
	case "pltUnitStage2":
		setInnerHtml("tdEmployeeName2", "");
		break;
	case "pltUnitStage3":
		setInnerHtml("tdEmployeeName3", "");
		break;
	case "pltUnitStage4":
		setInnerHtml("tdEmployeeName4", "");
		break;
	case "pltUnitStage5":
		setInnerHtml("tdEmployeeName5", "");
		break;
	case "pltUnitStage6":
		setInnerHtml("tdEmployeeName6", "");
		break;
	case "pltUnitStage7":
		setInnerHtml("tdEmployeeName7", "");
		break;
	case "pltUnitStage8":
		setInnerHtml("tdEmployeeName8", "");
		break;
	case "pltUnitStage9":
		setInnerHtml("tdEmployeeName9", "");
		break;
	case "pltUnitStage10":
		setInnerHtml("tdEmployeeName10", "");
		break;
	}

}
