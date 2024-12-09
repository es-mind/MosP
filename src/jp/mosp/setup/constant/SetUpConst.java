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
package jp.mosp.setup.constant;

/**
 * セットアップに関する定数クラス。
 */
public final class SetUpConst {
	
	private SetUpConst() {
	}
	
	
	/**
	 * 接続失敗時のメッセージコード。<br>
	 */
	public static final String	MSG_CONECTION				= "SUE001";
	
	/**
	 * SQL文を流す時のメッセージコード。<br>
	 */
	public static final String	MSG_SQL						= "SUE002";
	
	/**
	 * DB作成失敗時のメッセージコード。<br>
	 */
	public static final String	MSG_CREATEDB				= "SUE003";
	
	/**
	 * ロール名入力チェック。<br>
	 */
	public static final String	MSG_INPUT_ROLE				= "SUE004";
	
	/**
	 * 既に存在時のメッセージコード。<br>
	 */
	public static final String	MSG_YET						= "SUE005";
	
	/**
	 * セッションが取得不可時のメッセージコード。
	 */
	public static final String	MSG_SESSION					= "SUE006";
	
	/**
	 * ファイルのエンコーディング指定。<br>
	 */
	public static final String	ENCODE_UTF_8				= "UTF-8";
	
	/**
	 * SQLファイルディレレクトリーパス。<br>
	 */
	public static final String	SQL_DIR						= "sql";
	
	/**
	 * SQLファイルの拡張子。<br>
	 */
	public static final String	SUFFIX_SQL_FILE				= ".sql";
	
	/**
	 * GRANTSQLファイルに含まれる文字。<br>
	 */
	public static final String	FILE_GRANT					= "grant";
	
	/**
	 * GRANT文に元々あるロール名。<br>
	 */
	public static final String	DEFAULT_ROLL_NAME			= "usermosp";
	
	/**
	 * DB接続設定XMLファイル。<br>
	 */
	public static final String	PATH_XML_FILE				= "WEB-INF/xml/user/user_connection.xml";
	
	/**
	 * PostgresSQL文字列。<br>
	 */
	public static final String	NAME_POSTGRES				= "postgres";
	
	/**
	 * Applicationキー:DefaultServerName
	 */
	public static final String	APP_DEFAULT_SERVER_NAME		= "DefaultServerName";
	
	/**
	 * Applicationキー:DefaultPort
	 */
	public static final String	APP_DEFAULT_PORT			= "DefaultPort";
	
	/**
	 * Applicationキー:DatabaseUrlPattern
	 */
	public static final String	APP_DATABASE_URL_PATTERN	= "DatabaseUrlPattern";
	
	/**
	 * Applicationキー:SetUpDatabase
	 */
	public static final String	APP_SETUP_DATABASE			= "SetUpDatabase";
	
	/**
	 * Applicationキー:InitUserRoleCode
	 */
	public static final String	APP_INIT_USER_ROLE_CODE		= "InitUserRoleCode";
	
	/**
	 * アプリケーション設定キー(postgresDB名)。
	 */
	public static final String	APP_POSTGRES_DATA_BASE		= "PostgresDataBase";
	
	/**
	 * アプリケーション設定キー(スーパーユーザ名)。
	 */
	public static final String	APP_SUPER_USER_NAME			= "SuperUserName";
	
	/**
	 * アプリケーション設定キー(スーパーユーザPW)。
	 */
	public static final String	APP_SUPER_USER_PASS			= "SuperUserPassword";
	
	/**
	 * アプリケーション設定キー(初期DBユーザ)。
	 */
	public static final String	APP_DEFAULT_DB_USER			= "DefaultDbUser";
	
}
