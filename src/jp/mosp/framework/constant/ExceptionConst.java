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
package jp.mosp.framework.constant;

/**
 * MosPフレームワークで用いる例外IDを宣言する。<br><br>
 */
public class ExceptionConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private ExceptionConst() {
		// 処理無し
	}
	
	
	/**
	 * 実行時例外が発生した場合。<br>
	 */
	public static final String	EX_RUNTIME			= "FWE9999";
	
	/**
	 * インスタンス生成時にクラス名が無い場合。<br>
	 */
	public static final String	EX_NO_CLASS_NAME	= "FWE9101";
	
	/**
	 * インスタンス生成時にクラスが無い場合。<br>
	 */
	public static final String	EX_NO_CLASS			= "FWE9102";
	
	/**
	 * インスタンス生成に失敗した場合。<br>
	 */
	public static final String	EX_FAIL_INSTANTIATE	= "FWE9103";
	
	/**
	 * インスタンス生成時にクラス名を取得できなかった場合。<br>
	 */
	public static final String	EX_FAIL_CLASS_NAME	= "FWE9104";
	
	/**
	 * セッションに必要な情報が含まれてない場合。<br>
	 */
	public static final String	EX_INVALID_SESSION	= "FWE9105";
	
	/**
	 * 実装されていないコマンドを受け取った場合。<br>
	 */
	public static final String	EX_INVALID_COMMAND	= "FWE9111";
	
	/**
	 * コマンド設定情報が存在しない場合。<br>
	 */
	public static final String	EX_COMMAND_NONE		= "FWE9112";
	
	/**
	 * 許可しないHTTPメソッドのリクエストを受け取った場合。<br>
	 */
	public static final String	EX_INVALID_METHOD	= "FWE9113";
	
	/**
	 * ファイルの送出に失敗した場合。<br>
	 */
	public static final String	EX_FAIL_OUTPUT_FILE	= "FWE9121";
	
	/**
	 * ファイルの入力に失敗した場合。<br>
	 */
	public static final String	EX_FAIL_INPUT_FILE	= "FWE9122";
	
	/**
	 * MultiPartフォームデータの解析に失敗した場合。
	 */
	public static final String	EX_FAIL_PARSE_MULTI	= "FWE9123";
	
	/**
	 * サポートされない文字エンコーディングを指定した場合。
	 */
	public static final String	EX_INVALID_ENCODING	= "FWE9124";
	
	/**
	 * フォワード処理に失敗した場合。
	 */
	public static final String	EX_FAIL_FORWARD		= "FWE9125";
	
	/**
	 * 対応していないRDBMSに接続している場合。<br>
	 */
	public static final String	EX_UNKNOWN_RDBMS	= "FWE9211";
	
	/**
	 * DBコネクションの取得に失敗した場合。<br>
	 */
	public static final String	EX_FAIL_DB_CONNECT	= "FWE9212";
	
	/**
	 * データソースの取得に失敗した場合。<br>
	 */
	public static final String	EX_FAIL_DATA_SOURCE	= "FWE9213";
	
	/**
	 * 挿入処理に失敗した場合。<br>
	 */
	public static final String	EX_FAIL_INSERT		= "FWE9221";
	
	/**
	 * 更新処理に失敗した場合。<br>
	 */
	public static final String	EX_FAIL_UPDATE		= "FWE9222";
	
	/**
	 * 削除処理に失敗した場合。<br>
	 */
	public static final String	EX_FAIL_DELETE		= "FWE9223";
	
	/**
	 * セッションがタイムアウトした場合。<br>
	 */
	public static final String	EX_SESSION_TIMEOUT	= "FWW0001";
	
	/**
	 * 処理シーケンスが異なる場合。<br>
	 */
	public static final String	EX_PROC_SEQ_INVALID	= "FWW0011";
	
	/**
	 * 対象データが存在しない場合。<br>
	 */
	public static final String	EX_NO_DATA			= "FWW0101";
	
	/**
	 * 操作権限が無い場合。<br>
	 */
	public static final String	EX_NO_AUTHORITY		= "FWW0102";
	
}
