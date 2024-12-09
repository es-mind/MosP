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
package jp.mosp.framework.constant;

/**
 * MosPフレームワークで用いる定数を宣言する。<br><br>
 */
public class MospConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MospConst() {
		// 処理無し
	}
	
	
	/**
	 * 文字列(空文字)。<br>
	 */
	public static final String	STR_EMPTY				= "";
	
	/**
	 * 文字列(半角空白)。
	 */
	public static final String	STR_SB_SPACE			= " ";
	
	/**
	 * 文字列(全角空白)。
	 */
	public static final String	STR_DB_SPACE			= "　";
	
	/**
	 * 文字列(Carriage Return)。
	 */
	public static final String	STR_CARRIAGE_RETURN		= "\r";
	
	/**
	 * 文字列(Line Feed)。
	 */
	public static final String	STR_LINE_FEED			= "\n";
	
	/**
	 * 無効フラグ(OFF)。<br>
	 */
	public static final int		INACTIVATE_FLAG_OFF		= 0;
	
	/**
	 * 無効フラグ(ON)。<br>
	 */
	public static final int		INACTIVATE_FLAG_ON		= 1;
	
	/**
	 * 削除フラグ(OFF)。<br>
	 */
	public static final int		DELETE_FLAG_OFF			= 0;
	
	/**
	 * 削除フラグ(ON)。<br>
	 */
	public static final int		DELETE_FLAG_ON			= 1;
	
	/**
	 * 表示フラグ(表示)。<br>
	 */
	public static final int		VIEW_ON					= 0;
	
	/**
	 * 表示フラグ(非表示)。
	 */
	public static final int		VIEW_OFF				= 1;
	
	/**
	 * 汎用フラグ(OFF)。<br>
	 */
	public static final int		FLAG_OFF				= 0;
	
	/**
	 * 汎用フラグ(ON)。<br>
	 */
	public static final int		FLAG_ON					= 1;
	
	/**
	 * チェックボックス値(ON)。<br>
	 * value属性に設定しておくと、チェックされていた場合にこの値が送信される。<br>
	 */
	public static final String	CHECKBOX_ON				= "1";
	
	/**
	 * チェックボックス値(OFF)。<br>
	 * 値が送信されなかった場合に、この値が付加される。<br>
	 */
	public static final String	CHECKBOX_OFF			= "0";
	
	// パラメーター名(request.getParameter)
	/**
	 * パラメータID(コマンド)。<br>
	 * 処理Actionを判断させる。<br>
	 */
	public static final String	PRM_CMD					= "cmd";
	
	/**
	 * パラメータID(APIBeanのモデルキー)。<br>
	 */
	public static final String	PRM_API					= "api";
	
	/**
	 * パラメータID(ページ選択インデックス)。<br>
	 */
	public static final String	PRM_SELECT_INDEX		= "selectIndex";
	
	/**
	 * パラメータID(初期変数)。<br>
	 */
	public static final String	PRM_INITIAL_ARG			= "arg";
	
	/**
	 * MosP属性名(MosPパラメータ)。<br>
	 */
	public static final String	ATT_MOSP_PARAMS			= "mospParams";
	
	/**
	 * MosP属性名(マルチパートデータのリスト)。<br>
	 */
	public static final String	ATT_MULTIPART_LIST		= "multipartList";
	
	// 属性名(request.getHeader)
	/**
	 * MosP属性名(USER-AGENT)。<br>
	 */
	public static final String	ATT_USER_AGENT			= "USER-AGENT";
	
	/**
	 * MosP属性名(REMOTE-ADDR)。<br>
	 */
	public static final String	ATT_REMOTE_ADDR			= "REMOTE-ADDR";
	
	/**
	 * MosP属性名(REQUEST-METHOD)。<br>
	 */
	public static final String	ATT_HTTP_METHOD			= "HTTP-METHOD";
	
	/**
	 * MosP属性名(REQUEST-URL)。<br>
	 */
	public static final String	ATT_REQUEST_URL			= "REQUEST-URL";
	
	/**
	 * MosP属性名(REQUEST-QUERY)。<br>
	 */
	public static final String	ATT_REQUEST_QUERY		= "REQUEST-QUERY";
	
	/**
	 * MosP属性名(CONTENT-TYPE)。<br>
	 */
	public static final String	ATT_CONTENT_TYPE		= "CONTENT-TYPE";
	
	/**
	 * MosP属性名(AUTHORIZATION)。<br>
	 */
	public static final String	ATT_AUTHORIZATION		= "Authorization";
	
	/**
	 * MosP属性名(REFERER)。<br>
	 */
	public static final String	REFERER					= "Referer";
	
	// プロパティ名(public)
	/**
	 * MosPアプリケーション設定キー(アプリケーションルートの絶対パス)。<br>
	 */
	public static final String	APP_DOCBASE				= "Docbase";
	
	/**
	 * MosPアプリケーション設定キー(設定ファイル読込時間)。<br>
	 * JavaScriptファイルやCSSファイルへのリンクに用いられる(キャッシュ対策)。<br>
	 */
	public static final String	APP_PROPERTY_TIME		= "PropertyTime";
	
	/**
	 * MosPアプリケーション設定キー(文字コード)
	 */
	public static final String	APP_CHARACTER_ENCODING	= "CharacterEncoding";
	
	/**
	 * MosPアプリケーション設定キー(追加メタ情報)
	 */
	public static final String	APP_EXTRA_HTML_META		= "ExtraHtmlMeta";
	
	/**
	 * MosPアプリケーション設定セパレータ。
	 */
	public static final String	APP_PROPERTY_SEPARATOR	= ",";
	
	/**
	 * MosPアプリケーション設定キー(favicon)。<br>
	 */
	public static final String	APP_FAVICON_IMAGE		= "FaviconImage";
	
	/**
	 * MosPアプリケーション設定キー(タイトル接頭辞)。<br>
	 * HTMLのtitleタグ(template.jsp)で用いられる。<br>
	 */
	public static final String	APP_TITLE_PREFIX		= "TitlePrefix";
	
	/**
	 * MosPアプリケーション設定キー(アプリケーションのタイトル)。<br>
	 * HTMLのtitleタグ(template.jsp)で用いられる。<br>
	 * ヘッダ(header.jsp)で用いられる。<br>
	 */
	public static final String	APP_TITLE				= "Title";
	
	/**
	 * MosPアプリケーション設定キー(アプリケーションのバージョン)。<br>
	 * HTMLのtitleタグ(template.jsp)で用いられる。<br>
	 */
	public static final String	APP_VERSION				= "Version";
	
	/**
	 * MosPアプリケーション設定キー(ログアウトボタン非表示)。<br>
	 */
	public static final String	APP_DISABLE_LOGOUT_BTN	= "DisableLogoutButton";
	
	/**
	 * 操作区分(参照)。
	 */
	public static final String	OPERATION_TYPE_REFER	= "1";
	
	/**
	 * 範囲設定の範囲(自身)。<br>
	 */
	public static final String	RANGE_MYSELF			= "RangeMyself";
	
	// URL
	/**
	 *  サーブレットのパス。<br>
	 */
	public static final String	URL_SRV					= "/srv/";
	
	/**
	 *  公開ディレクトリのパス。<br>
	 */
	public static final String	URL_PUB					= "../pub/";
	
	/**
	 * HTTPメソッド(GET)。
	 */
	public static final String	HTTP_METHOD_GET			= "GET";
	
	/**
	 * HTTPメソッド(POST)。
	 */
	public static final String	HTTP_METHOD_POST		= "POST";
	
	// 入力制限
	/**
	 * 入力制限用定数(電話番号)。<br>
	 */
	public static final String	REG_PHONE				= "[0-9-]*";
	
	/**
	 * 入力制限用定数(数値1)。<br>
	 */
	public static final String	REG_DECIMAL_PRE			= "^(([1-9]\\d{0,";
	
	/**
	 * 入力制限用定数(数値2)。<br>
	 */
	public static final String	REG_DECIMAL_MID			= "})|0)(\\.\\d{1,";
	
	/**
	 * 入力制限用定数(数値3)。<br>
	 */
	public static final String	REG_DECIMAL_AFT			= "})?$";
	
	// その他
	/**
	 * デフォルト年。<br>
	 * Date型で月日や時間だけを扱いたい場合に用いる。<br>
	 */
	public static final int		DEFAULT_YEAR			= 1970;
	
	/**
	 * ラインセパレート用の定数。<br>
	 */
	public static final String	LINE_SEPARATOR			= System.getProperty("line.separator");
	
	/**
	 * エラーメッセージ用括弧。<br>
	 * エラーメッセージの後ろに「(エラーメッセージコード)」が付く。<br>
	 */
	public static final String	MESSAGE_L_PARENTHSIS	= "(";
	
	/**
	 * エラーメッセージ用括弧。<br>
	 * エラーメッセージの後ろに「(エラーメッセージコード)」が付く。<br>
	 */
	public static final String	MESSAGE_R_PARENTHSIS	= ")";
	
	/**
	 * 処理バイト数。<br>
	 */
	public static final int		PROCESS_BYTES			= 1024;
	
	/**
	 * 回数(少数：1回)
	 */
	public static final float	COUNT_FLOAT_ONE			= 1F;
	
	/**
	 * 回数(少数：0.5回)
	 */
	public static final float	COUNT_FLOAT_HALF		= 0.5F;
	
	/**
	 * 回数(数値：1回)
	 */
	public static final int		COUNT_INT_ONE			= 1;
	
	/**
	 * 回数(数値：0回)
	 */
	public static final int		COUNT_INT_ZERO			= 0;
	
	/**
	 * コンテントタイプ(JSON)。<br>
	 */
	public static final String	CONTENT_TYPE_JSON		= "application/json";
	
	/**
	 * バイナリファイル拡張子コードキー(gif)。<br>
	 */
	public static final String	CODE_BINARY_FILE_GIF	= "0";
	
	/**
	 * バイナリファイル拡張子コードキー(jpeg)。
	 */
	public static final String	CODE_BINARY_FILE_JPEG	= "1";
	
	/**
	 * バイナリファイル拡張子コードキー(png)。
	 */
	public static final String	CODE_BINARY_FILE_PNG	= "2";
	
	/**
	 * バイナリファイル拡張子コードキー(その他ファイル)。
	 */
	public static final String	CODE_BINARY_FILE_FILE	= "3";
	
}
