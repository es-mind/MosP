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
package jp.mosp.platform.constant;

/**
 * MosPプラットフォームで用いる定数を宣言する。<br>
 * <br>
 */
public class PlatformConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PlatformConst() {
		// 処理無し
	}
	
	
	/**
	 * MosPアプリケーション設定キー(ログインイメージパス)。
	 */
	public static final String	APP_LOGIN_IMAGE_PATH					= "LoginImagePath";
	
	/**
	 * MosPアプリケーション設定キー(ログイン画面パスワード初期化利用可否)。
	 */
	public static final String	APP_LOGIN_INITIALIZE_PASSWORD			= "LoginInitializePassword";
	
	/**
	 * MosPアプリケーション設定キー(QAリンク非表示設定)。
	 */
	public static final String	APP_FORUM_LINK_DISABLED					= "ForumLinkDisabled";
	
	/**
	 * MosPアプリケーション設定キー(パスワード変更可否)。
	 */
	public static final String	APP_INIT_PASSWORD_IMPOSSIBLE			= "InitPasswordImpossible";
	
	/**
	 * MosPアプリケーション設定キー(個人基本履歴削除ボタン表示可否)。
	 */
	public static final String	APP_HISTORY_BASIC_DELETE_BUTTON			= "HistoryBasicDeleteButton";
	
	/**
	 * MosPアプリケーション設定キー(社員コード変更機能)。
	 */
	public static final String	APP_CHANGE_EMPLOY_CODE					= "ChangeEmployCode";
	
	/**
	 * MosPアプリケーション設定キー(検索条件必須設定)。
	 */
	public static final String	APP_SEARCH_CONDITION_REQUIRED			= "SearchConditionRequired";
	
	/**
	 * MosPアプリケーション設定キー(役職(個人基本情報)利用可否)。
	 */
	public static final String	APP_ADD_USE_POST						= "UsePost";
	
	/**
	 * MosPアプリケーション設定キー(人事汎用管理利用区分)。
	 */
	public static final String	APP_HUMAN_GENERAL_DIVISIONS				= "HumanGeneralDivisions";
	
	/**
	 * MosPアプリケーション設定キー(人事汎用管理利用区分)。
	 */
	public static final String	APP_HUMAN_GENERAL_TABLE_ITEM			= "TableItem";
	
	/**
	 * MosPアプリケーション設定キー(人事情報一覧画面追加情報用Beanクラス群)。
	 */
	public static final String	APP_HUMAN_INFO_EXTRA_BEANS				= "HumanInfoExtraBeans";
	
	/**
	 * 有効日モード(決定)。<br>
	 */
	public static final String	MODE_ACTIVATE_DATE_FIXED				= "fixed";
	
	/**
	 * 有効日モード(変更)。<br>
	 */
	public static final String	MODE_ACTIVATE_DATE_CHANGING				= "chaning";
	
	/**
	 * 編集モード(新規登録モード)。<br>
	 */
	public static final String	MODE_CARD_EDIT_INSERT					= "insert";
	
	/**
	 * 編集モード(履歴追加モード)。<br>
	 */
	public static final String	MODE_CARD_EDIT_ADD						= "add";
	
	/**
	 * 編集モード(履歴編集モード)。<br>
	 */
	public static final String	MODE_CARD_EDIT_EDIT						= "edit";
	
	/**
	 * パラメータID(メニュー情報)。
	 */
	public static final String	PRM_TRANSFERRED_MENU_KEY				= "transferredMenuKey";
	
	/**
	 * パラメータID(ソートキー)。<br>
	 */
	public static final String	PRM_TRANSFERRED_SORT_KEY				= "transferredSortKey";
	
	/**
	 * パラメータID(譲渡汎用コード)。
	 */
	public static final String	PRM_TRANSFERRED_CODE					= "transferredCode";
	
	/**
	 * パラメータID(譲渡ワークフロー番号)。
	 */
	public static final String	PRM_TRANSFERRED_WORKFLOW				= "transferredWorkflow";
	
	/**
	 * パラメータID(譲渡レコード識別ID)。
	 */
	public static final String	PRM_TRANSFERRED_RECORD_ID				= "transferredRecordId";
	
	/**
	 * パラメータID(譲渡有効日)。
	 */
	public static final String	PRM_TRANSFERRED_ACTIVATE_DATE			= "transferredActivateDate";
	
	/**
	 * パラメータID(譲渡汎用区分)。
	 */
	public static final String	PRM_TRANSFERRED_TYPE					= "transferredType";
	
	/**
	 * パラメータID(譲渡汎用コマンド)。
	 */
	public static final String	PRM_TRANSFERRED_COMMAND					= "transferredCommand";
	
	/**
	 * コードキー(有効/無効)。
	 */
	public static final String	CODE_KEY_INACTIVATE_FLAG				= "InactivateFlag";
	
	/**
	 * コードキー(退職理由)。
	 */
	public static final String	CODE_KEY_RETIREMENT						= "Retirement";
	
	/**
	 * コードキー(都道府県)。
	 */
	public static final String	CODE_KEY_PREFECTURE						= "Prefecture";
	
	/**
	 * コードキー(名称区分)。
	 */
	public static final String	CODE_KEY_NAMING_TYPE					= "NamingType";
	
	/**
	 * コードキー(バイナリデータファイル拡張子)。
	 */
	public static final String	CODE_KEY_BINARY_FILE_TYPE				= "BinaryFileType";
	
	/**
	 * コードキー(ヘッダリンク)。<br>
	 */
	public static final String	CODE_KEY_HEADER_LINK					= "HeaderLink";
	
	/**
	 * 名称区分(役職)。
	 */
	public static final String	NAMING_TYPE_POST						= "post";
	
	/**
	 * コードキー(検索条件区分)。
	 */
	public static final String	CODE_KEY_SEARCH_TYPE					= "SearchType";
	
	/**
	 * 検索条件区分(前方一致)。
	 */
	public static final String	SEARCH_FORWARD_MATCH					= "forward";
	
	/**
	 * 検索条件区分(部分一致)。
	 */
	public static final String	SEARCH_BROAD_MATCH						= "broad";
	
	/**
	 * 検索条件区分(完全一致)。
	 */
	public static final String	SEARCH_EXACT_MATCH						= "exact";
	
	/**
	 * コードキー(休退職情報)。
	 */
	public static final String	CODE_KEY_EMPLOYEE_STATE					= "EmployeeState";
	
	/**
	 * 休退職情報(在職)。
	 */
	public static final String	EMPLOYEE_STATE_PRESENCE					= "presence";
	
	/**
	 * 休退職情報(休職)。
	 */
	public static final String	EMPLOYEE_STATE_SUSPEND					= "suspend";
	
	/**
	 * 休退職情報(退職)。
	 */
	public static final String	EMPLOYEE_STATE_RETIRE					= "retire";
	
	/**
	 * コードキー(フリーワード検索区分)。
	 */
	public static final String	CODE_KEY_FREE_WORD_TYPE					= "FreeWordType";
	
	/**
	 * フリーワード検索区分(個人基本情報)。
	 */
	public static final String	FREE_WORD_HUMAN							= "human";
	
	/**
	 * フリーワード検索区分(休職情報)。
	 */
	public static final String	FREE_WORD_SUSPEND						= "suspend";
	
	/**
	 * フリーワード検索区分(退職情報)。
	 */
	public static final String	FREE_WORD_RETIRE						= "retire";
	
	/**
	 * フリーワード検索区分(兼務情報)。
	 */
	public static final String	FREE_WORD_CONCUR						= "concur";
	
	/**
	 * コードキー(所属マスタの検索条件)。
	 */
	public static final String	CODE_KEY_SEARCH_SECTION_TYPE			= "SearchSectionType";
	
	/**
	 * コードキー(ユニット区分)。
	 */
	public static final String	CODE_KEY_UNIT_TYPE						= "UnitType";
	
	/**
	 * コードキー(ユニット職位等級範囲)。
	 */
	public static final String	CODE_KEY_UNIT_POSITION_GRADE_RANGE		= "UnitPositionGradeRange";
	
	/**
	 * ユニット区分(所属)。
	 */
	public static final String	UNIT_TYPE_SECTION						= "section";
	
	/**
	 * ユニット区分(個人)。
	 */
	public static final String	UNIT_TYPE_PERSON						= "person";
	
	/**
	 * コードキー(階層数)。
	 */
	public static final String	CODE_KEY_APPROVAL_COUNT					= "ApprovalCount";
	
	/**
	 * コードキー(メッセージ区分)。
	 */
	public static final String	CODE_KEY_MESSAGE_TYPE					= "MessageType";
	
	/**
	 * コードキー(メッセージ重要度)。
	 */
	public static final String	CODE_KEY_MESSAGE_IMPORTANCE				= "MessageImportance";
	
	/**
	 * コードキー(メッセージ重要度記号)。
	 */
	public static final String	CODE_KEY_MESSAGE_IMPORTANCE_MARK		= "MessageImportanceMark";
	
	/**
	 * 適用範囲区分(マスタ組み合わせ指定)。
	 */
	public static final String	APPLICATION_TYPE_MASTER					= "0";
	
	/**
	 * 適用範囲区分(個人指定)。
	 */
	public static final String	APPLICATION_TYPE_PERSON					= "1";
	
	/**
	 * コードキー(ヘッダー有無区分)。
	 */
	public static final String	CODE_KEY_HEADER_TYPE					= "HeaderType";
	
	/**
	 * コードキー(ファイル区分)。
	 */
	public static final String	CODE_KEY_FILE_TYPE						= "FileType";
	
	/**
	 * コードキー(フロー区分)。
	 */
	public static final String	CODE_KEY_WORKFLOW_TYPE					= "WorkflowType";
	
	/**
	 * フロー区分(勤怠)。
	 */
	public static final int		WORKFLOW_TYPE_TIME						= 1;
	
	/**
	 * フロー区分(人事)。
	 */
	public static final int		WORKFLOW_TYPE_HUMAN						= 2;
	
	/**
	 * MosPアプリケーション設定キー(等級優劣関係)。
	 */
	public static final String	APP_POSITION_GRADE_ADVANTAGE			= "PositionGradeAdvantage";
	
	/**
	 * 等級優劣関係(等級の小さい方が優)。
	 */
	public static final String	POSITION_GRADE_ADVANTAGE_LOW			= "Low";
	
	/**
	 * MosPアプリケーション設定キー(承認者：等級)。
	 */
	public static final String	APP_APPROVER_POSITION_GRADE				= "ApproverPositionGrade";
	
	/**
	 * ルートコード(自己承認)。
	 */
	public static final String	APPROVAL_ROUTE_SELF						= "SELF_APPRO";
	
	/**
	 * 承認状況(下書)。
	 */
	public static final String	CODE_STATUS_DRAFT						= "0";
	
	/**
	 * 承認状況(未承認)。
	 */
	public static final String	CODE_STATUS_APPLY						= "1";
	
	/**
	 * 承認状況(承認)。
	 */
	public static final String	CODE_STATUS_APPROVED					= "2";
	
	/**
	 * 承認状況(差戻)。
	 */
	public static final String	CODE_STATUS_REVERT						= "3";
	
	/**
	 * 承認状況(承認解除)。
	 */
	public static final String	CODE_STATUS_CANCEL						= "4";
	
	/**
	 * 承認状況(取下)。
	 */
	public static final String	CODE_STATUS_WITHDRAWN					= "5";
	
	/**
	 * 承認状況(解除申)。
	 */
	public static final String	CODE_STATUS_CANCEL_APPLY				= "6";
	
	/**
	 * 承認状況(解除申(取下希望))。
	 */
	public static final String	CODE_STATUS_CANCEL_WITHDRAWN_APPLY		= "7";
	
	/**
	 * 承認状況(承認済)。
	 */
	public static final String	CODE_STATUS_COMPLETE					= "9";
	
	/**
	 * ワークフロー段階0(申請者)。
	 */
	public static final int		WORKFLOW_STAGE_ZERO						= 0;
	
	/**
	 * ワークフロー段階1(1次承認者)。
	 */
	public static final int		WORKFLOW_STAGE_FIRST					= 1;
	
	/**
	 * パラメータID(対象個人ID)。
	 */
	public static final String	PRM_TARGET_PERSONAL_ID					= "prmTargetPersonalId";
	
	/**
	 * パラメータID(対象日)。
	 */
	public static final String	PRM_TARGET_DATE							= "prmTargetDate";
	
	/**
	 * パラメータID(譲渡Actionクラス名)(連続実行コマンド判定用)。
	 */
	public static final String	PRM_TRANSFERRED_ACTION					= "transferredAction";
	
	/**
	 * パラメータID(譲渡インデックス)。
	 */
	public static final String	PRM_TRANSFERRED_INDEX					= "transferredIndex";
	
	/**
	 * プレフィックス(JavaScript変数出力)。
	 */
	public static final String	PREFIX_DIRECT_JS						= "js";
	
	/**
	 * MosPアプリケーション設定キー(一度に取得する個人情報の最大数)。<br>
	 * SQLのプレースホルダの最大数を超えることによるエラーを防ぐために設定が必要。<br>
	 */
	public static final String	APP_PERSONAL_IDS_MAX_INDEX				= "PersonalIdsMaxIndex";
	
	/**
	 * 一度に取得する個人情報の最大数の規定値。
	 */
	public static final int		DEFAULT_PERSONAL_IDS_MAX_INDEX_VALUE	= 5000;
	
	/**
	 * スタイル文字列(赤)。<br>
	 */
	public static final String	STYLE_RED								= "style=\"color: red\"";
	
	/**
	 * スタイル文字列(青)。<br>
	 */
	public static final String	STYLE_BLUE								= "style=\"color: blue\"";
	
	/**
	 * スタイル文字列(黄)。<br>
	 */
	public static final String	STYLE_YELLOW							= "style=\"color: darkorange\"";
	
	/**
	 * スタイル文字列(灰)。
	 */
	public static final String	STYLE_GRAY								= "style=\"color: gray\"";
	
	/**
	 * スタイル文字列(赤太字)。<br>
	 */
	public static final String	STYLE_RED_BOLD							= "style=\"font-weight: bold; color: red;\"";
	
	/**
	 * スタイル文字列(背景：黄)。<br>
	 */
	public static final String	STYLE_BACKGROUND_YELLOW					= "style=\"background-color: yellow\"";
	
	/**
	 * クラス(CSS)文字列(フォント：青)。<br>
	 */
	public static final String	CLS_FONT_BLUE							= "FontBlue";
	
	/**
	 * クラス(CSS)文字列(フォント：赤)。<br>
	 */
	public static final String	CLS_FONT_RED							= "FontRed";
	
	/**
	 * クラス(CSS)文字列(太字)。<br>
	 */
	public static final String	CLS_BOLD								= "Bold";
	
}
