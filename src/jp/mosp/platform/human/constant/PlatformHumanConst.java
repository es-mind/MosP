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
package jp.mosp.platform.human.constant;

/**
 * MosPプラットフォーム人事管理機能で用いる定数を宣言する。<br>
 */
public class PlatformHumanConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PlatformHumanConst() {
		// 処理無し
	}
	
	
	/**
	 * MosPアプリケーション設定キー(MosPプラットフォーム人事管理共通JSPファイルパス)。<br>
	 */
	public static final String	APP_HUMAN_COMMON_INFO_JSP				= "HumanCommonInfoJsp";
	
	/**
	 * ファイルパス(人事汎用管理区分共通JSP)。
	 */
	public static final String	PATH_HUMAN_VIEW_CONFIG_JSP				= "/jsp/platform/human/general/view.jsp";
	
	/**
	 * ファイルパス(人事汎用管理区分共通JSP)。
	 */
	public static final String	PATH_HUMAN_VIEW_TABLE_JSP				= "/jsp/platform/human/general/viewTable.jsp";
	
	/**
	 * ファイルパス(人事汎用管理項目入力形式JSP)。
	 */
	public static final String	PATH_HUMAN_ITEM_JSP						= "/jsp/platform/human/general/item.jsp";
	
	/**
	 * ファイルパス(人事汎用管理計四区共通JSP：通常)。
	 */
	public static final String	PATH_HUMAN_NORMAL_JSP					= "/jsp/platform/human/humanNormalCard.jsp";
	
	/**
	 * ファイルパス(人事汎用管理計四区共通JSP：履歴)。
	 */
	public static final String	PATH_HUMAN_HISTORY_JSP					= "/jsp/platform/human/humanHistoryCard.jsp";
	
	/**
	 * ファイルパス(人事汎用管理計四区共通JSP：履歴一覧)。
	 */
	public static final String	PATH_HUMAN_HISTORY_LIST_JSP				= "/jsp/platform/human/humanHistoryList.jsp";
	
	/**
	 * ファイルパス(人事汎用管理計四区共通JSP：一覧)。
	 */
	public static final String	PATH_HUMAN_ARRAY_JSP					= "/jsp/platform/human/arrayCard.jsp";
	
	/**
	 * 人事管理共通情報表示モード(全て表示)。
	 */
	public static final String	MODE_HUMAN_SHOW_ALL						= "all";
	
	/**
	 * 人事管理共通情報表示モード(社員コード、氏名のみ表示)。
	 */
	public static final String	MODE_HUMAN_CODE_AND_NAME				= "codeAndName";
	
	/**
	 * 人事管理共通情報表示モード(再表示なし)。
	 */
	public static final String	MODE_HUMAN_NO_ACTIVATE_DATE				= "noActiveDate";
	
	/**
	 * パラメータID(譲渡社員コード)。
	 */
	public static final String	PRM_TRANSFERRED_EMPLOYEE_CODE			= "transferredEmployeeCode";
	
	/**
	 * パラメータID(譲渡有効日)。
	 */
	public static final String	PRM_TRANSFERRED_ACTIVATE_DATE			= "transferredActivateDate";
	
	/**
	 * 検索モード(社員コード)。
	 */
	public static final String	SEARCH_EMPLOYEE_CODE					= "searchEmployeeCode";
	
	/**
	 * 検索モード(対象日)。
	 */
	public static final String	SEARCH_TARGET_DATE						= "searchTargetDate";
	
	/**
	 * 検索モード(個人ID[前])。
	 */
	public static final String	SEARCH_BACK								= "searchBack";
	
	/**
	 * 検索モード(個人ID[後])。
	 */
	public static final String	SEARCH_NEXT								= "searchNext";
	
	/**
	 * 検索モード。
	 */
	public static final String	PRM_TRANSFER_SEARCH_MODE				= "transferSearch";
	
	/**
	 * パラメータID(再表示有効日(年))。<br>
	 */
	public static final String	PRM_TXT_ACTIVATE_YEAR					= "txtSearchActivateYear";
	
	/**
	 * パラメータID(再表示有効日(月))。<br>
	 */
	public static final String	PRM_TXT_ACTIVATE_MONTH					= "txtSearchActivateMonth";
	
	/**
	 * パラメータID(再表示有効日(日))。<br>
	 */
	public static final String	PRM_TXT_ACTIVATE_DAY					= "txtSearchActivateDay";
	
	/**
	 * パラメータID(検索社員コード)。<br>
	 */
	public static final String	PRM_TXT_EMPLOYEE_CODE					= "txtSearchEmployeeCode";
	
	/**
	 * 人事汎用管理機能用パラメータID(人事汎用管理区分)。<br>
	 */
	public static final String	PRM_HUMAN_DIVISION						= "humanDivision";
	
	/**
	 * 人事汎用管理機能用パラメータID(表示区分)。<br>
	 */
	public static final String	PRM_HUMAN_VIEW							= "humanView";
	
	/**
	 * 人事汎用管理機能用パラメータID(表示テーブル)。<br>
	 */
	public static final String	PRM_HUMAN_VIEW_TABLE					= "humanViewTable";
	
	/**
	 * 人事汎用管理機能用区分パラメータID(通常)。<br>
	 */
	public static final String	PRM_HUMAN_DIVISION_TYPE_NORMAL			= "Normal";
	
	/**
	 * 人事汎用管理機能用区分パラメータID(履歴)。<br>
	 */
	public static final String	PRM_HUMAN_DIVISION_TYPE_HISTORY			= "History";
	
	/**
	 * 人事汎用管理機能用区分パラメータID(一覧)。<br>
	 */
	public static final String	PRM_HUMAN_DIVISION_TYPE_ARRAY			= "Array";
	
	/**
	 * 人事汎用管理機能用区分パラメータID(バイナリ通常)。<br>
	 */
	public static final String	PRM_HUMAN_DIVISION_TYPE_BINARY_NORMAL	= "BinaryNormal";
	
	/**
	 * 人事汎用管理機能用区分パラメータID(バイナリ履歴)。<br>
	 */
	public static final String	PRM_HUMAN_DIVISION_TYPE_BINARY_HISTORY	= "BinaryHistory";
	
	/**
	 * 人事汎用管理機能用区分パラメータID(バイナリ一覧)。<br>
	 */
	public static final String	PRM_HUMAN_DIVISION_TYPE_BINARY_ARRAY	= "BinaryArray";
	
	/**
	 * 人事汎用管理機能用パラメータID(人事汎用項目)。<br>
	 */
	public static final String	PRM_HUMAN_TABLE_ITEM					= "humanTableItem";
	
	/**
	 * 人事汎用管理機能用パラメータID(人事汎用項目情報)。<br>
	 */
	public static final String	PRM_HUMAN_ITEM_PROPERTY					= "tableItemProperty";
	
	/**
	 * 人事汎用管理項目パラメータID(itemKey)。<br>
	 */
	public static final String	PRM_HUMAN_ITEM_ITEM_KEY					= "itemKey";
	
	/**
	 * 人事汎用管理項目パラメータID(itemName)。<br>
	 */
	public static final String	PRM_HUMAN_ITEM_ITEM_NAME				= "itemName";
	
	/**
	 * 人事汎用一覧行IDパラメータID。<br>
	 */
	public static final String	PRM_HUMAN_ARRAY_ROW_ID					= "ArrayRowId";
	
	/**
	 * 人事汎用管理パラメータID(固定値要否)。<br>
	 */
	public static final String	PRM_HUMAN_NEED_FIXED_VALUE				= "needFixedValue";
	
	/**
	 * 人事汎用管理パラメータID(有効日名称)。
	 */
	public static final String	PRM_HUMAN_DATE_NAME						= "dateName";
	
	/**
	 * 人事汎用管理パラメータID(人事汎用一覧情報有効日)。
	 */
	public static final String	PRM_HUMAN_ARRAY_DATE					= "activeDate";
	
	/**
	 * 人事汎用入力形式(text)。<br>
	 */
	public static final String	KEY_HUMAN_ITEM_TYPE_TEXT				= "text";
	
	/**
	 * 人事汎用入力形式(textarea)。<br>
	 */
	public static final String	KEY_HUMAN_ITEM_TYPE_TEXTAREA			= "textarea";
	
	/**
	 * 人事汎用入力形式(select)。<br>
	 */
	public static final String	KEY_HUMAN_ITEM_TYPE_SELECT				= "select";
	
	/**
	 * 人事汎用入力形式(label)。<br>
	 */
	public static final String	KEY_HUMAN_ITEM_TYPE_LABEL				= "label";
	
	/**
	 * 人事汎用入力形式(concatenatedLabel)。<br>
	 */
	public static final String	KEY_HUMAN_ITEM_TYPE_CONCATENATED_LABEL	= "concatenatedLabel";
	
	/**
	 * 人事汎用入力形式(checkbox)。<br>
	 */
	public static final String	KEY_HUMAN_ITEM_TYPE_CHECK_BOX			= "checkbox";
	
	/**
	 * 人事汎用入力形式(radio)。<br>
	 */
	public static final String	KEY_HUMAN_ITEM_TYPE_RADIO				= "radio";
	
	/**
	 * 人事汎用表示テーブル形式(書類テーブル)。<br>
	 */
	public static final String	TYPE_VIEW_TABLE_CARD					= "CardTable";
	
	/**
	 * 人事汎用表示テーブル形式(一覧テーブル)。<br>
	 */
	public static final String	TYPE_VIEW_TABLE_ARRAY					= "ArrayTable";
	
	/**
	 * 人事汎用項目区分キー(デフォルト)。
	 */
	public static final String	KEY_DEFAULT_CONVENTION					= "Default";
	
	/**
	 * 人事汎用日付フォーマット(yyyy/MM/dd)。
	 */
	public static final String	FORMAT_HUMAN_DATE_1						= "yyyy/MM/dd";
	
	/**
	 * 人事汎用日付フォーマット(年月日)。
	 */
	public static final String	FORMAT_HUMAN_DATE_2						= "JapaneseDate";
	
	/**
	 * 人事汎用電話フォーマット(%1%-%2%-%3%)。
	 */
	public static final String	FORMAT_HUMAN_PHONE						= "%1%-%2%-%3%";
	
	/**
	 * インポート用人事汎用履歴・人事汎用一覧有効日。
	 */
	public static final String	PRM_IMPORT_HISTORY_ARRAY_ACTIVATE_DATE	= "ActivateDate";
	
	/**
	 * 項目長(ファイル名称)。
	 */
	public static final int		LEN_FILE_NAME							= 50;
	
	/**
	 * 人事汎用項目名：メールアドレス
	 */
	public static final String	ITEM_NAME_HUMAN_MAIL					= "mailAddress";
}
