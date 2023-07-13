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
package jp.mosp.framework.base;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.log.LoggerInterface;
import jp.mosp.framework.property.CommandProperty;
import jp.mosp.framework.property.MospProperties;
import jp.mosp.framework.utils.LogUtility;
import jp.mosp.framework.utils.MessageUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.framework.utils.RoleUtility;
import net.arnx.jsonic.JSON;

/**
 * MosPフレームワークのFrontController。<br><br>
 * 
 * このサーブレットがMosPのコントローラとしての役割を果たす。<br>
 * 設定ファイル読込、例外処理の他に、以下の流れでアプリケーションを制御する機能を有する。<br>
 * <ul>
 * <li>リクエストの受付、及び共通前処理</li>
 * <li>アクションインスタンスの生成、及び処理実行</li>
 * <li>ビューへの処理委譲、或いはレスポンスの生成</li>
 * </ul>
 * 処理を実行するアクションクラスは、リクエストに含まれるコマンドによって決定される。<br>
 * アクションとコマンドは、設定ファイルによって関連付けられる。<br>
 * <br>
 * リクエストには{@link MospParams}が格納され、アクションとは{@link MospParams}を介して
 * パラメータのやり取りを行う。<br>
 * <br>
 * レスポンス形式は、{@link MospParams#getFile()}により決定される。<br>
 * 但し、例外発生時やアクションで意図的に変更した場合等は、この限りではない。<br>
 * {@link MospParams#getFile()}がnullであれば、{@link MospParams}に設定されたURLに
 * リクエストがフォワードされ、HTMLでレスポンスが送出される。<br>
 * {@link MospParams#getFile()}がnullでなければ、設定されていたオブジェクトを
 * ファイルとして送出する。<br>
 */
public class Controller extends HttpServlet {
	
	private static final long				serialVersionUID			= 5986464246296477656L;
	
	/**
	 * MosP設定情報。
	 */
	protected transient MospProperties		ppt;
	
	/**
	 * ログ出力クラス群。
	 */
	protected Map<String, LoggerInterface>	loggers						= new ConcurrentHashMap<String, LoggerInterface>();
	
	/**
	 * セッション属性名(セッション保持情報)。
	 */
	protected static final String			ATT_STORED_INFO				= "storedInfo";
	
	/**
	 * セッション属性名(処理シーケンス)。
	 */
	protected static final String			ATT_PROC_SEQ				= "procSeq";
	
	/**
	 * MosPアプリケーション設定キー(初期表示URL)。
	 */
	protected static final String			APP_URL_INDEX				= "UrlIndex";
	
	/**
	 * MosPアプリケーション設定キー(初期表示コマンド)。
	 */
	protected static final String			APP_COMMAND_INDEX			= "CommandIndex";
	
	/**
	 * MosPアプリケーション設定キー(ログアウトコマンド)。
	 */
	protected static final String			APP_COMMAND_LOGOUT			= "CommandLogout";
	
	/**
	 * MosPアプリケーション設定キー(セッション保持時間)。
	 */
	protected static final String			APP_SESSION_INTERVAL		= "SessionInterval";
	
	/**
	 * MosPアプリケーション設定キー(レスポンスヘッダ：リソースの有効期限)。
	 */
	protected static final String			APP_RESPONSE_EXPIRES		= "Expires";
	
	/**
	 * MosPアプリケーション設定キー(レスポンスヘッダ：リソースのキャッシュ設定)。
	 */
	protected static final String			APP_RESPONSE_PRAGMA			= "Pragma";
	
	/**
	 * MosPアプリケーション設定キー(レスポンスヘッダ：リソースのキャッシュ操作)。
	 */
	protected static final String			APP_RESPONSE_CACHE_CTRL		= "Cache-Control";
	
	/**
	 * MosPアプリケーション設定キー(レスポンスヘッダ：許可するアクセス元)。
	 */
	protected static final String			APP_RESPONSE_ALLOW_ORIGIN	= "Access-Control-Allow-Origin";
	
	/**
	 * MosPアプリケーション設定キー(レスポンスコンテンツタイプ：HTML)。
	 */
	protected static final String			APP_CONTENT_TYPE_HTML		= "ContentType-Html";
	
	/**
	 * MosPアプリケーション設定キープレフィックス(ファイル送出クラス)。
	 */
	protected static final String			APP_EXPORTER_PREFIX			= "Exporter-";
	
	/**
	 * HTTPセッション要否(要)。
	 * 但し、ユーザ情報の確認は行わない。
	 */
	protected static final String			SESSION_NECESSARY			= "necessary";
	
	/**
	 * HTTPセッション要否(不要)。
	 */
	protected static final String			SESSION_UNNECESSARY			= "unnecessary";
	
	/**
	 * リクエストパラメータ名(処理シーケンス)。
	 */
	protected static final String			PRM_PROC_SEQ				= "procSeq";
	
	/**
	 * 処理シーケンス発行要否(不要)。
	 */
	protected static final String			PROC_SEQ_UNNECESSARY		= "unnecessary";
	
	/**
	 * 処理シーケンス発行要否(保持処理シーケンス0時通常判定)。
	 */
	protected static final String			PROC_SEQ_ZERO_ACCEPT		= "zeroAcceptable";
	
	/**
	 * 処理シーケンス発行要否(処理シーケンス無視)。
	 */
	protected static final String			PROC_SEQ_IGNORE				= "ignore";
	
	/**
	 * API用コマンド。
	 */
	protected static final String			CMD_API						= "api";
	
	/**
	 * APIパラメータ区切文字。
	 */
	protected static final String			API_SEPARATOR				= "/";
	
	
	/**
	 * 初期化処理。<br>
	 * サーブレットコンテキスト取得、及び設定ファイル読込を行う。<br>
	 */
	@Override
	public void init(ServletConfig config) {
		try {
			// 初期化
			super.init(config);
			// プロパティファイル読込
			String docBase = getServletContext().getRealPath("");
			ppt = parseMospProperties(docBase);
			// メッセージファイル(JavaScript)出力
			MospUtility.outputMessageJs(ppt);
			// MosPコントローラ初期化ログ出力
			LogUtility.controllerInit(new MospParams(ppt, loggers), toString());
		} catch (ServletException e) {
			e.printStackTrace();
		} catch (MospException e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * Getメソッドリクエスト取得処理。<br>
	 * Controllerのメインとなる処理を行い、アプリケーションを制御する。<br>
	 * クライアントは、このメソッドが実行されるようにHTTPリクエストを投げる必要がある。<br>
	 */
	@Override
	public void doGet(HttpServletRequest request, HttpServletResponse response) {
		try {
			// リクエストに対する処理を実行
			doProcess(request, response);
		} catch (Exception e) {
			handleException(e, request, response);
		} catch (Error e) {
			handleException(e, request, response);
		}
	}
	
	/**
	 * Postメソッドリクエスト取得処理。<br>
	 * Controllerのメインとなる処理を行い、アプリケーションを制御する。<br>
	 * クライアントは、このメソッドが実行されるようにHTTPリクエストを投げる必要がある。<br>
	 */
	@Override
	public void doPost(HttpServletRequest request, HttpServletResponse response) {
		try {
			// リクエストに対する処理を実行
			doProcess(request, response);
		} catch (Exception e) {
			handleException(e, request, response);
		} catch (Error e) {
			handleException(e, request, response);
		}
	}
	
	/**
	 * リクエストに対する処理を行う。<br>
	 * Controllerのメインとなる処理を行い、アプリケーションを制御する。<br>
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void doProcess(HttpServletRequest request, HttpServletResponse response) throws MospException {
		// サーブレット前処理
		doCommonPreProcess(request);
		// MosPパラメータ取得
		MospParams mospParams = getMospParams(request);
		// アクセスログ出力
		LogUtility.access(mospParams);
		// パラメータログ出力
		LogUtility.parameter(mospParams);
		// コマンドが取得できなかった場合
		if (mospParams.getCommand() == null || mospParams.getCommand().isEmpty()) {
			// 初期URLへフォワード(以降の処理が不能)
			forward(request, response, ppt.getApplicationProperty(APP_URL_INDEX));
			return;
		}
		// アクションクラス名取得
		String actionClassName = mospParams.getCommandProperty().getActionClass();
		// アクションクラスの取得及び初期化
		ActionInterface action = (ActionInterface)InstanceFactory.loadInstance(actionClassName);
		action.init(mospParams);
		// アクション実行
		action.doAction();
		// 処理シーケンス発行
		issueProcSeq(request);
		// フォワード
		forward(request, response);
	}
	
	/**
	 * フォワード処理を行う。<br><br>
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void forward(HttpServletRequest request, HttpServletResponse response) throws MospException {
		// MosPパラメータ取得
		MospParams mospParams = getMospParams(request);
		// URL確認(エラーURLが設定されている場合)
		if (mospParams.isUrlErrorView()) {
			// レスポンスヘッダ情報設定
			setResponseHeader(request, response);
			// フォワード
			forward(request, response, mospParams.getUrl());
			return;
		}
		// 連続実行コマンド確認
		if (mospParams.getNextCommand() != null) {
			// フォワード
			forward(request, response, MospConst.URL_SRV);
			return;
		}
		// 送出ファイル確認
		if (mospParams.getFile() != null) {
			// ファイル送出
			output(request, response);
			return;
		}
		// リダイレクト確認
		if (mospParams.getRedirect() != null) {
			// リダイレクト
			redirect(request, response);
			return;
		}
		// レスポンスヘッダ情報設定
		setResponseHeader(request, response);
		// MosPパラメータからURLを取得しフォワード
		forward(request, response, mospParams.getUrl());
	}
	
	/**
	 * フォワード処理を行う。<br><br>
	 * @param request  リクエスト
	 * @param response レスポンス
	 * @param url      フォワード先URL
	 * @throws MospException フォワード処理に失敗した場合
	 */
	protected void forward(HttpServletRequest request, HttpServletResponse response, String url) throws MospException {
		try {
			getServletContext().getRequestDispatcher(url).forward(request, response);
		} catch (ServletException e) {
			// 例外発行(エラー画面へ)
			throw new MospException(e, ExceptionConst.EX_FAIL_FORWARD, url);
		} catch (IOException e) {
			// 例外発行(エラー画面へ)
			throw new MospException(e, ExceptionConst.EX_FAIL_FORWARD, url);
		}
	}
	
	/**
	 * レスポンスとしてファイルを送出する。<br>
	 * @param request リクエスト
	 * @param response レスポンス
	 * @throws MospException ファイルの送出に失敗した場合
	 */
	protected void output(HttpServletRequest request, HttpServletResponse response) throws MospException {
		// MosPパラメータ取得
		MospParams mospParams = getMospParams(request);
		// 送出クラス名取得
		String key = mospParams.getApplicationProperty(APP_EXPORTER_PREFIX + mospParams.getFile().getClass().getName());
		// Exporter取得
		MospExporterInterface exporter = (MospExporterInterface)InstanceFactory.loadInstance(key);
		// ファイル送出
		exporter.export(mospParams, response);
	}
	
	/**
	 * リダイレクト処理を行う。<br><br>
	 * @param request  リクエスト
	 * @param response レスポンス
	 * @throws MospException リダイレクト処理に失敗した場合
	 */
	protected void redirect(HttpServletRequest request, HttpServletResponse response) throws MospException {
		// リダイレクトURLを取得
		String redirect = getMospParams(request).getRedirect();
		try {
			// リダイレクト
			response.sendRedirect(redirect);
		} catch (IOException e) {
			// 例外発行(エラー画面へ)
			throw new MospException(e, ExceptionConst.EX_FAIL_FORWARD, redirect);
		}
	}
	
	/**
	 * MosP設定情報からレスポンスヘッダを取得し、レスポンスに設定する。<br><br>
	 * @param request  リクエスト
	 * @param response レスポンス
	 */
	protected void setResponseHeader(HttpServletRequest request, HttpServletResponse response) {
		// MosP設定情報取得
		MospParams mospParams = getMospParams(request);
		// リソースのキャッシュ設定を設定
		for (String property : mospParams.getApplicationProperties(APP_RESPONSE_PRAGMA)) {
			response.addHeader(APP_RESPONSE_PRAGMA, property);
		}
		// リソースのキャッシュ操作を設定
		for (String property : mospParams.getApplicationProperties(APP_RESPONSE_CACHE_CTRL)) {
			response.addHeader(APP_RESPONSE_CACHE_CTRL, property);
		}
		// 許可するアクセス元を設定
		for (String property : mospParams.getApplicationProperties(APP_RESPONSE_ALLOW_ORIGIN)) {
			response.addHeader(APP_RESPONSE_ALLOW_ORIGIN, property);
		}
		// リソースの有効期限を設定
		response.addDateHeader(APP_RESPONSE_EXPIRES, mospParams.getApplicationProperty(APP_RESPONSE_EXPIRES, 0));
		// レスポンスコンテンツタイプ設定(HTML)
		response.setContentType(mospParams.getApplicationProperty(APP_CONTENT_TYPE_HTML));
		
	}
	
	/**
	 * 共通前処理を行う。<br>
	 * <br>
	 * <ul><li>
	 * {@link MospParams}の生成及び設定
	 * <br></li><li>
	 * パラメータの引き継ぎ(連続実行コマンドが設定されている場合)
	 * <br></li><li>
	 * リクエスト文字列文字コード指定
	 * <br></li><li>
	 * リクエストパラメータ取得
	 * <br></li><li>
	 * レスポンスタイプ設定
	 * <br></li><li>
	 * コマンド設定及びコマンド設定情報取得
	 * <br></li><li>
	 * セッション保持情報取得及び設定
	 * <br></li></ul>
	 * @param request リクエスト
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void doCommonPreProcess(HttpServletRequest request) throws MospException {
		// MosP処理情報生成
		MospParams mospParams = new MospParams(ppt, loggers);
		// セッション保持情報取得
		MospStoredInfo storedInfo = getMospStoredInfo(request);
		// MosP処理情報にセッション保持情報を設定
		if (storedInfo != null) {
			mospParams.setStoredInfo(storedInfo);
		}
		// 連続実行時パラメータ情報受取(連続実行コマンド設定)
		if (getMospParams(request) != null) {
			// リクエストからMosPパラメータ情報受取(連続実行コマンド設定)
			mospParams.inheritParams(getMospParams(request));
		}
		// MosPパラメータ設定
		request.setAttribute(MospConst.ATT_MOSP_PARAMS, mospParams);
		// リクエスト文字列文字コード指定
		setRequestCharEncoding(request);
		// リクエストパラメータ取得
		MospRequestParser.parseRequestParams(request, getMospParams(request));
		// コマンド設定
		if (mospParams.getCommand() == null) {
			mospParams.setCommand(mospParams.getRequestParam(MospConst.PRM_CMD));
		}
		// リクエスト情報設定
		mospParams.addGeneralParam(MospConst.ATT_USER_AGENT, request.getHeader(MospConst.ATT_USER_AGENT));
		mospParams.addGeneralParam(MospConst.REFERER, request.getHeader(MospConst.REFERER));
		mospParams.addGeneralParam(MospConst.ATT_REMOTE_ADDR, request.getRemoteAddr());
		mospParams.addGeneralParam(MospConst.ATT_HTTP_METHOD, request.getMethod());
		mospParams.addGeneralParam(MospConst.ATT_REQUEST_URL, request.getRequestURL());
		mospParams.addGeneralParam(MospConst.ATT_REQUEST_QUERY, request.getQueryString());
		mospParams.addGeneralParam(MospConst.ATT_AUTHORIZATION, request.getHeader(MospConst.ATT_AUTHORIZATION));
		mospParams.addGeneralParam(MospConst.ATT_CONTENT_TYPE, request.getHeader(MospConst.ATT_CONTENT_TYPE));
		// APIパラメータ及びAPIコマンドを設定(APIが利用できる場合)
		setApiParams(request);
		// コマンドが取得できなかった場合
		if (mospParams.getCommand() == null || mospParams.getCommand().isEmpty()) {
			// 処理終了(doProcessで初期URLへフォワード)
			return;
		}
		// HTTPメソッド確認
		checkHttpMethod(request);
		// セッション確認
		checkHttpSession(request);
		// 処理シーケンス確認
		checkProcSeq(request);
	}
	
	/**
	 * リクエスト文字列文字コードを指定する。<br>
	 * @param request リクエスト
	 * @throws MospException 文字エンコーディングがサポートされていない場合
	 */
	protected void setRequestCharEncoding(HttpServletRequest request) throws MospException {
		// 文字エンコーディング取得
		String encoding = ppt.getApplicationProperty(MospConst.APP_CHARACTER_ENCODING);
		try {
			// リクエスト文字列文字コード指定
			request.setCharacterEncoding(encoding);
		} catch (UnsupportedEncodingException e) {
			// 例外発行(エラー画面へ)
			getMospParams(request).setErrorViewUrl();
			throw new MospException(e, ExceptionConst.EX_INVALID_ENCODING, encoding);
		}
	}
	
	/**
	 * 例外処理を行う。<br>
	 * <br>
	 * 引数であるthrowableと{@link MospParams}から情報を取得し、例外処理を行う。<br>
	 * <ul><li>
	 * リクエストから{@link MospParams}を取得できなかった場合<br>
	 * セッションを無効にする。<br>
	 * スタックトレースを標準エラーストリームに出力する。<br>
	 * エラー画面へ遷移する。<br>
	 * <br></li><li>
	 * throwableが{@link MospException}ではなかった場合<br>
	 * セッションを無効にする。<br>
	 * エラーログを出力する。<br>
	 * エラー画面へ遷移する。<br>
	 * <br></li><li>
	 * throwableが{@link MospException}で実行時例外を原因とする場合<br>
	 * セッションを無効にする。<br>
	 * MosP例外メッセージをログに出力する。<br>
	 * エラーログ(原因のスタックトレース)を出力する。<br>
	 * エラー画面へ遷移する。<br>
	 * <br></li><li>
	 * {@link MospParams}の遷移先URLにエラー画面が設定されていた場合<br>
	 * セッションを無効にする。<br>
	 * MosP例外メッセージをログに出力する。<br>
	 * エラーログを出力する。<br>
	 * エラー画面へ遷移する。<br>
	 * <br></li><li>
	 * それ以外の場合(MosPにおける軽微な例外)<br>
	 * MosP例外メッセージをログに出力する(DEBUG)。<br>
	 * {@link MospParams}に設定されているURLにフォワード。<br>
	 * <br></li></ul>
	 * @param throwable 例外あるいはエラーオブジェクト
	 * @param request   リクエスト
	 * @param response  レスポンス
	 */
	protected void handleException(Throwable throwable, HttpServletRequest request, HttpServletResponse response) {
		try {
			// MosPパラメータ取得
			MospParams mospParams = getMospParams(request);
			// MosPパラメータが取得できなかった場合
			if (mospParams == null) {
				// セッション無効化
				invalidateSession(request);
				// スタックトレース出力
				throwable.printStackTrace();
				// MosPパラメータ生成
				mospParams = new MospParams(ppt, loggers);
				// MosPパラメータ設定
				request.setAttribute(MospConst.ATT_MOSP_PARAMS, mospParams);
				// エラー画面へフォワード
				mospParams.setErrorViewUrl();
				forward(request, response);
				return;
			}
			// MosP例外以外の場合
			if (throwable instanceof MospException == false) {
				// セッション無効化
				invalidateSession(request);
				// エラーログ出力
				LogUtility.error(mospParams, throwable);
				// エラー画面へフォワード
				mospParams.setErrorViewUrl();
				forward(request, response);
				return;
			}
			// MosP例外取得
			MospException mospException = (MospException)throwable;
			// 実行時例外或いはエラー画面を表示する場合
			if (mospException.getCause() != null || mospParams.isUrlErrorView()) {
				// セッション無効化
				invalidateSession(request);
				// エラーログ出力
				LogUtility.error(mospParams, mospException);
				// エラーメッセージ設定
				mospParams.addErrorMessage(mospException.getExceptionId(), mospException.getReplaceStrings());
				// エラー画面へフォワード
				mospParams.setErrorViewUrl();
				forward(request, response);
				return;
			}
			// MosP例外メッセージ出力(DEBUG)
			LogUtility.debug(mospParams,
					mospParams.getCodedMessage(mospException.getExceptionId(), mospException.getReplaceStrings()));
			// 処理シーケンス発行
			issueProcSeq(request);
			// フォワード
			forward(request, response);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	/**
	 * セッションを無効にする。
	 * @param request リクエスト
	 */
	protected void invalidateSession(HttpServletRequest request) {
		// セッション取得
		HttpSession session = request.getSession(false);
		// セッション無効
		if (session != null) {
			session.invalidate();
		}
	}
	
	/**
	 * HTTPセッションの取得、確認をする。<br><br>
	 * {@link CommandProperty#getNeedSession()}で取得した値により、処理を変える。<br>
	 * @param request リクエスト
	 * @throws MospException セッションが必要であるのに取得できなかった場合(セッションタイムアウト等)
	 */
	protected void checkHttpSession(HttpServletRequest request) throws MospException {
		// リクエストからMosP処理情報を取得
		MospParams mospParams = getMospParams(request);
		// MosPコマンド設定情報取得
		CommandProperty commandProperty = mospParams.getCommandProperty();
		// 現在のセッションを取得
		HttpSession session = request.getSession(false);
		// MosP処理情報からセッション保持情報取得
		MospStoredInfo storedInfo = mospParams.getStoredInfo();
		// MosPコマンド設定情報からセッション要否を取得
		if (commandProperty.getNeedSession() == null) {
			// セッションが取得できなかった場合(セッションタイムアウト等)
			if (session == null) {
				// エラーメッセージを設定
				MessageUtility.addErrorSessionTimeout(mospParams);
				// 連続実行コマンド設定
				mospParams.setNextCommand(ppt.getApplicationProperty(APP_COMMAND_INDEX));
				// 例外発行
				throw new MospException(ExceptionConst.EX_SESSION_TIMEOUT);
			}
			// セッション保持情報からユーザ情報を取得し確認
			if (storedInfo.getUser() == null) {
				// 連続実行コマンド設定
				mospParams.setNextCommand(ppt.getApplicationProperty(APP_COMMAND_INDEX));
				// 例外発行
				throw new MospException(ExceptionConst.EX_INVALID_SESSION);
			}
			// ログインユーザのロール実行可能コマンドを確認
			if (RoleUtility.hasAuthority(mospParams, commandProperty.getCommand()) == false) {
				// メッセージ設定
				mospParams.addErrorMessage(ExceptionConst.EX_NO_AUTHORITY);
				// 連続実行コマンド設定
				mospParams.setNextCommand(ppt.getApplicationProperty(APP_COMMAND_INDEX));
				// 例外発行
				throw new MospException(ExceptionConst.EX_NO_AUTHORITY);
			}
		} else if (commandProperty.getNeedSession().equals(SESSION_NECESSARY)) {
			// セッションが取得できなかった場合(セッションタイムアウト等)
			if (session == null) {
				// エラーメッセージを設定
				MessageUtility.addErrorSessionTimeout(mospParams);
				// 連続実行コマンド設定
				mospParams.setNextCommand(ppt.getApplicationProperty(APP_COMMAND_INDEX));
				// 例外発行
				throw new MospException(ExceptionConst.EX_SESSION_TIMEOUT);
			}
		} else if (commandProperty.getNeedSession().equals(SESSION_UNNECESSARY)) {
			// セッション確認
			if (session == null) {
				// セッション再取得
				session = request.getSession(true);
			}
		}
		// セッション保持情報をセッションに設定
		session.setAttribute(ATT_STORED_INFO, storedInfo);
		// セッション保持時間(秒)設定
		session.setMaxInactiveInterval(ppt.getApplicationProperty(APP_SESSION_INTERVAL, 0));
		// 処理シーケンス設定
		if (session.getAttribute(ATT_PROC_SEQ) == null) {
			session.setAttribute(ATT_PROC_SEQ, String.valueOf(0));
		}
	}
	
	/**
	 * HTTPメソッドの確認をする。<br><br>
	 * {@link CommandProperty#getAcceptMethods()}で取得した値を基に確認する。<br>
	 * GETメソッドであり、コマンドが設定されていない場合、初期コマンドを設定する。
	 * @param request リクエスト
	 * @throws MospException 許可しないHTTPメソッドのリクエストを受け取った場合
	 */
	protected void checkHttpMethod(HttpServletRequest request) throws MospException {
		// HTTPメソッド取得
		String httpMethod = (String)getMospParams(request).getGeneralParam(MospConst.ATT_HTTP_METHOD);
		// 許可HTTPメソッド取得
		String[] acceptMethods = getMospParams(request).getCommandProperty().getAcceptMethods();
		// POST確認
		if (acceptMethods.length == 0 && httpMethod.equals(MospConst.HTTP_METHOD_POST)) {
			// 設定が無くHTTPメソッドがPOSTの場合
			return;
		}
		// 許可メソッド確認
		for (String acceptMethod : acceptMethods) {
			// HTTPメソッドと許可メソッドを比較
			if (acceptMethod.equals(httpMethod)) {
				return;
			}
		}
		// 例外発行(エラー画面へ)
		getMospParams(request).setErrorViewUrl();
		throw new MospException(ExceptionConst.EX_INVALID_METHOD, getMospParams(request).getCommand());
	}
	
	/**
	 * 処理シーケンスを発行する。<br>
	 * {@link CommandProperty#getNeedProcSeq()}で取得した値により、処理を変える。<br>
	 * @param request リクエスト
	 * @throws MospException MosPコマンド設定情報が取得できなかった場合
	 */
	protected void issueProcSeq(HttpServletRequest request) throws MospException {
		// 保持処理シーケンス取得
		int procSeq = getStoredProcSeq(request);
		// 処理シーケンス要否取得
		String needProcSeq = getMospParams(request).getCommandProperty().getNeedProcSeq();
		// 処理シーケンス発行確認
		if (needProcSeq == null) {
			// 処理シーケンス発行(加算)
			procSeq++;
		}
		// 処理シーケンス設定
		setStoredProcSeq(request, procSeq);
		getMospParams(request).setProcSeq(procSeq);
	}
	
	/**
	 * 処理シーケンスの確認を行う。<br><br>
	 * 但し、連続実行の場合は確認を行わない。<br>
	 * 処理シーケンスが保持されたものとリクエストとで異なる場合、例外を発行する。<br>
	 * @param request リクエスト
	 * @throws MospException 不正な処理シーケンスを受け取った場合
	 */
	protected void checkProcSeq(HttpServletRequest request) throws MospException {
		// 連続実行確認
		if (getMospParams(request).getNextCount() > 0) {
			return;
		}
		// 処理シーケンス要否取得
		String needProcSeq = getMospParams(request).getCommandProperty().getNeedProcSeq();
		// 処理シーケンス無視確認
		if (needProcSeq != null && needProcSeq.equals(PROC_SEQ_IGNORE)) {
			return;
		}
		// セッション保持処理シーケンス取得
		int storedProcSeq = getStoredProcSeq(request);
		// リクエストされた処理シーケンス取得
		int requestedProcSeq = Integer.parseInt(getMospParams(request).getRequestParam(PRM_PROC_SEQ));
		// 処理シーケンス確認(通常)
		if (storedProcSeq == requestedProcSeq) {
			return;
		}
		// 保持処理シーケンス0時通常判定確認
		if (needProcSeq != null && needProcSeq.equals(PROC_SEQ_ZERO_ACCEPT) && storedProcSeq == 0) {
			return;
		}
		// パンくずリスト取得
		List<TopicPath> topicPathList = getMospParams(request).getTopicPathList();
		// 処理シーケンス確認(戻るボタン)
		if (storedProcSeq > requestedProcSeq) {
			// パンくずリスト確認
			if (topicPathList.size() > 1) {
				// パンくずリストからコマンドを取得
				getMospParams(request).setNextCommand(topicPathList.get(topicPathList.size() - 2).getCommand());
				// 例外発行
				throw new MospException(ExceptionConst.EX_PROC_SEQ_INVALID);
			} else if (topicPathList.size() == 1) {
				// ログアウト処理
				getMospParams(request).setNextCommand(ppt.getApplicationProperty(APP_COMMAND_LOGOUT));
				// 例外発行
				throw new MospException(ExceptionConst.EX_PROC_SEQ_INVALID);
			}
		}
		// 連続実行コマンド設定(初期表示コマンド)
		getMospParams(request).setNextCommand(ppt.getApplicationProperty(APP_COMMAND_INDEX));
		// 例外発行
		throw new MospException(ExceptionConst.EX_PROC_SEQ_INVALID);
	}
	
	/**
	 * リクエストからMosPパラメータを取得する。
	 * @param request リクエスト
	 * @return MosPパラメータ
	 */
	protected MospParams getMospParams(HttpServletRequest request) {
		return (MospParams)request.getAttribute(MospConst.ATT_MOSP_PARAMS);
	}
	
	/**
	 * セッションからMosPセッション保持情報を取得する。
	 * @param request リクエスト
	 * @return MosPセッション保持情報
	 */
	protected MospStoredInfo getMospStoredInfo(HttpServletRequest request) {
		// セッションを取得
		HttpSession session = request.getSession(false);
		// セッション確認
		if (session == null) {
			return null;
		}
		return (MospStoredInfo)session.getAttribute(ATT_STORED_INFO);
	}
	
	/**
	 * セッションから処理シーケンスを取得する。
	 * @param request リクエスト
	 * @return セッション保持処理シーケンス
	 */
	protected int getStoredProcSeq(HttpServletRequest request) {
		// セッションを取得
		HttpSession session = request.getSession(false);
		if (session == null) {
			return 0;
		}
		// 処理シーケンス取得
		String procSeq = (String)session.getAttribute(ATT_PROC_SEQ);
		if (procSeq == null) {
			return 0;
		}
		return Integer.parseInt(procSeq);
	}
	
	/**
	 * セッションに処理シーケンスを設定する。
	 * @param request リクエスト
	 * @param procSeq 処理シーケンス
	 */
	protected void setStoredProcSeq(HttpServletRequest request, int procSeq) {
		// セッションを取得
		HttpSession session = request.getSession(false);
		// セッション確認
		if (session == null) {
			return;
		}
		// 処理シーケンス設定
		session.setAttribute(ATT_PROC_SEQ, String.valueOf(procSeq));
	}
	
	/**
	 * MosP設定情報を生成する。
	 * @param docBase MosPアプリケーションが配置されている実際のパス
	 * @return MosP設定情報
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected MospProperties parseMospProperties(String docBase) throws MospException {
		return MospPropertiesParser.parseMospProperties(docBase);
	}
	
	/**
	 * APIパラメータ及びAPIコマンドを設定(APIが利用できる場合)する。
	 * @param request リクエスト
	 * @throws MospException 実行時例外が発生した場合
	 */
	protected void setApiParams(HttpServletRequest request) throws MospException {
		// MosP処理情報を取得
		MospParams mospParams = getMospParams(request);
		// MosP設定情報(APIコマンド)が取得できない場合
		if (mospParams.getProperties().getCommandProperty(CMD_API) == null) {
			// 処理無し(API利用不可)
			return;
		}
		// URLからAPIパラメータを取得
		List<String> apiParams = getApiParams(request);
		// APIパラメータをMosP処理情報に設定
		mospParams.addApiParams(apiParams);
		// コマンドが設定されていない場合
		if (mospParams.getCommand() == null) {
			// APIBeanのモデルキーが取得できた場合
			if (mospParams.getRequestParam(MospConst.PRM_API) != null || apiParams.isEmpty() == false) {
				// APIコマンドを設定
				mospParams.setCommand(CMD_API);
			}
		}
		// リクエストのコンテンツタイプがJSONである場合
		if (MospUtility.isContentTypeContain(mospParams, MospConst.CONTENT_TYPE_JSON)) {
			// APIパラメータの最後尾にリクエストのメッセージボディ(JSON)を追加
			mospParams.getApiParams().add(getMessageBody(request));
		}
	}
	
	/**
	 * URLからAPIパラメータを取得する。<br>
	 * <br>
	 * APIパラメータは、URLの「/srv」より後ろを「/」で区切った
	 * 文字列のリストとして取得する。<br>
	 * <br>
	 * @param request リクエスト
	 * @return APIパラメータ
	 */
	protected List<String> getApiParams(HttpServletRequest request) {
		// URIを取得
		String uri = request.getRequestURI();
		// サーブレットパスを取得
		String srv = request.getServletPath() + API_SEPARATOR;
		// サーブレットパスより後ろの文字列を取得
		String strParams = uri.substring(uri.indexOf(srv) + srv.length());
		// /で区切りリストに変換
		return MospUtility.asList(MospUtility.split(strParams, API_SEPARATOR));
	}
	
	/**
	 * リクエストのメッセージボディ(JSON)を取得する。<br>
	 * @param request リクエスト
	 * @return メッセージボディ(JSON)
	 * @throws MospException メッセージボディの取得に失敗した場合
	 */
	protected String getMessageBody(HttpServletRequest request) throws MospException {
		// 入力ストリームを準備
		BufferedReader reader = null;
		try {
			// リクエストのメッセージボディを取得
			reader = request.getReader();
			// リクエストのメッセージボディ(JSON)を取得
			return JSON.encode(JSON.decode(reader));
		} catch (Throwable t) {
			// 例外を発行
			throw new MospException(t);
		} finally {
			// 入力ストリームを解放
			if (reader != null) {
				try {
					reader.close();
				} catch (Throwable t) {
					// 例外を発行
					throw new MospException(t);
				}
			}
			
		}
	}
	
}
