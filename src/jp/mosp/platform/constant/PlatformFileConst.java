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
 * MosPプラットフォームのインポート、エクスポートで用いる定数を宣言する。<br>
 * <br>
 */
public class PlatformFileConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PlatformFileConst() {
		// 処理無し
	}
	
	
	/**
	 * ファイル区分(CSV)。
	 */
	public static final String	FILE_TYPE_CSV						= "1";
	
	/**
	 * 拡張子(エクスポートファイル)。<br>
	 */
	public static final String	EXT_EXPORT_FILE						= ".csv";
	
	/**
	 * ヘッダ有無(無)。
	 */
	public static final int		HEADER_TYPE_NONE					= 0;
	
	/**
	 * コードキー(人事情報インポートデータ区分)。
	 */
	public static final String	CODE_KEY_HUMAN_IMPORT_TABLE_TYPE	= "HumanImportTableType";
	
	/**
	 * コードキー(人事情報インポートデータ区分)。
	 */
	public static final String	CODE_KEY_HUMAN_EXPORT_TABLE_TYPE	= "HumanExportTableType";
	
	/**
	 * コードキー(データ区分(人事情報))。
	 */
	public static final String	CODE_KEY_TABLE_TYPE_HUMAN			= "pfm_human";
	
	/**
	 * コードキー(データ区分(ユーザ情報))。
	 */
	public static final String	CODE_KEY_TABLE_TYPE_USER			= "pfm_user";
	
	/**
	 * コードキー(データ区分(ユーザ追加ロール情報))。
	 */
	public static final String	CODE_KEY_TABLE_TYPE_USER_EXTRA_ROLE	= "pfa_user_extra_role";
	
	/**
	 * コードキー(データ区分(所属情報))。
	 */
	public static final String	CODE_KEY_TABLE_TYPE_SECTION			= "pfm_section";
	
	/**
	 * コードキー(データ区分(職位情報))。
	 */
	public static final String	CODE_KEY_TABLE_TYPE_POSITION		= "pfm_position";
	
	/**
	 * コードキー(データ区分(パスワード情報))。
	 */
	public static final String	CODE_KEY_TABLE_TYPE_PASSWORD		= "pfa_user_password";
	
	/**
	 * コードキー(データ区分(ユニット情報(所属)))。
	 */
	public static final String	CODE_KEY_TABLE_TYPE_UNIT_SECTION	= "unit_section";
	
	/**
	 * コードキー(データ区分(ユニット情報(個人)))。
	 */
	public static final String	CODE_KEY_TABLE_TYPE_UNIT_PERSON		= "unit_person";
	
	/**
	 * フィールド(社員コード)。
	 */
	public static final String	FIELD_EMPLOYEE_CODE					= "employee_code";
	
	/**
	 * フィールド(有効日)。
	 */
	public static final String	FIELD_ACTIVATE_DATE					= "activate_date";
	
	/**
	 * フィールド(姓)。
	 */
	public static final String	FIELD_LAST_NAME						= "last_name";
	
	/**
	 * フィールド(名)。
	 */
	public static final String	FIELD_FIRST_NAME					= "first_name";
	
	/**
	 * フィールド(カナ姓)。
	 */
	public static final String	FIELD_LAST_KANA						= "last_kana";
	
	/**
	 * フィールド(カナ名)。
	 */
	public static final String	FIELD_FIRST_KANA					= "first_kana";
	
	/**
	 * フィールド(勤務地コード)。
	 */
	public static final String	FIELD_WORK_PLACE_CODE				= "work_place_code";
	
	/**
	 * フィールド(雇用契約コード)。
	 */
	public static final String	FIELD_EMPLOYMENT_CONTRACT_CODE		= "employment_contract_code";
	
	/**
	 * フィールド(所属コード)。
	 */
	public static final String	FIELD_SECTION_CODE					= "section_code";
	
	/**
	 * フィールド(職位コード)。
	 */
	public static final String	FIELD_POSITION_CODE					= "position_code";
	
	/**
	 * フィールド(入社日(入社情報))
	 */
	public static final String	FIELD_ENTRANCE_DATE					= "entrance_date";
	
	/**
	 * フィールド(ユーザID)
	 */
	public static final String	FIELD_USER_ID						= "user_id";
	
	/**
	 * フィールド(ロール区分)。<br>
	 */
	public static final String	FIELD_USER_ROLE_TYPE				= "role_type";
	
	/**
	 * フィールド(ロールコード)
	 */
	public static final String	FIELD_USER_ROLE_CODE				= "role_code";
	
	/**
	 * フィールド(退職日(退職情報))
	 */
	public static final String	FIELD_RETIREMENT_DATE				= "retirement_date";
	
	/**
	 * フィールド(退職理由(退職情報))
	 */
	public static final String	FIELD_RETIREMENT_REASON				= "retirement_reason";
	
	/**
	 * フィールド(退職理由詳細(退職情報))
	 */
	public static final String	FIELD_RETIREMENT_DETAIL				= "retirement_detail";
	
	/**
	 * フィールド(氏名)。
	 */
	public static final String	FIELD_FULL_NAME						= "full_name";
	
	/**
	 * フィールド(勤務地名称)。
	 */
	public static final String	FIELD_WORK_PLACE_NAME				= "work_place_name";
	
	/**
	 * フィールド(雇用契約名称)。
	 */
	public static final String	FIELD_EMPLOYMENT_CONTRACT_NAME		= "employment_contract_name";
	
	/**
	 * フィールド(所属名称)。
	 */
	public static final String	FIELD_SECTION_NAME					= "section_name";
	
	/**
	 * フィールド(所属表示名称)。
	 */
	public static final String	FIELD_SECTION_DISPLAY				= "section_display";
	
	/**
	 * フィールド(職位名称)。
	 */
	public static final String	FIELD_POSITION_NAME					= "position_name";
	
	/**
	 * フィールド(上位所属)
	 */
	public static final String	FIELD_UPPER_SECTION_CODE			= "upper_section_code";
	
	/**
	 * フィールド(変更日)
	 */
	public static final String	FIELD_CHANGE_DATE					= "change_date";
	
	/**
	 * フィールド(パスワード)
	 */
	public static final String	FIELD_PASSWORD						= "password";
	
}
