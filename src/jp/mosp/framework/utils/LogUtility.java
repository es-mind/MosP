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
package jp.mosp.framework.utils;

import java.util.HashMap;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.instance.InstanceFactory;
import jp.mosp.framework.log.LoggerInterface;
import jp.mosp.framework.log.MospLogger;
import net.arnx.jsonic.JSON;

/**
 * ログ出力に有用なメソッドを提供する。<br>
 * <br>
 * {@link LoggerInterface}を実装するクラスを用いて、ログを出力する。<br>
 * ログ出力クラス名は、ログタイプに応じて、MosP設定情報から取得する<br>
 * ログレベルは、ログタイプに応じて、MosP設定情報から取得する。<br>
 */
public class LogUtility {
	
	/**
	 * MosPアプリケーション設定キー(ログ区切文字)。
	 */
	public static final String		APP_LOG_SEPARATOR				= "LogSeparator";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：コントローラ初期化)。
	 */
	protected static final String	APP_LOG_TYPE_CONTROLLER_INIT	= "LogTypeControllerInit";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：アクセス)。
	 */
	protected static final String	APP_LOG_TYPE_ACCESS				= "LogTypeAccess";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：パラメータ)。
	 */
	protected static final String	APP_LOG_TYPE_PARAMETER			= "LogTypeParameter";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：アクション開始)。
	 */
	protected static final String	APP_LOG_TYPE_ACTION_START		= "LogTypeActionStart";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：アクション終了)。
	 */
	protected static final String	APP_LOG_TYPE_ACTION_END			= "LogTypeActionEnd";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：DB接続)。
	 */
	protected static final String	APP_LOG_TYPE_DB_CONNECT			= "LogTypeDbConnect";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：参照SQL実行)。
	 */
	protected static final String	APP_LOG_TYPE_SQL_SELECT			= "LogTypeSqlSelect";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：登録SQL実行)。
	 */
	protected static final String	APP_LOG_TYPE_SQL_REGIST			= "LogTypeSqlRegist";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：内部統制)。
	 */
	protected static final String	APP_LOG_TYPE_INTERNAL_CONTROL	= "LogTypeInternalControl";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：エラー)。
	 */
	protected static final String	APP_LOG_TYPE_ERROR				= "LogTypeError";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：アプリケーション)。
	 */
	protected static final String	APP_LOG_TYPE_APPLICATION		= "LogTypeApplication";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：デバッグ)。
	 */
	protected static final String	APP_LOG_TYPE_DEBUG				= "LogTypeDebug";
	
	/**
	 * MosPアプリケーション設定キー(ログタイプ：汎用)。
	 */
	protected static final String	APP_LOG_TYPE_GENERAL			= "LogTypeGeneral";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：コントローラ初期化)。
	 */
	protected static final String	APP_LOG_LEVEL_CONTROLLER_INIT	= "LogLevelControllerInit";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：アクセス)。
	 */
	protected static final String	APP_LOG_LEVEL_ACCESS			= "LogLevelAccess";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：パラメータ)。
	 */
	protected static final String	APP_LOG_LEVEL_PARAMETER			= "LogLevelParameter";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：アクション開始)。
	 */
	protected static final String	APP_LOG_LEVEL_ACTION_START		= "LogLevelActionStart";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：アクション終了)。
	 */
	protected static final String	APP_LOG_LEVEL_ACTION_END		= "LogLevelActionEnd";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：DB接続)。
	 */
	protected static final String	APP_LOG_LEVEL_DB_CONNECT		= "LogLevelDbConnect";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：参照SQL実行)。
	 */
	protected static final String	APP_LOG_LEVEL_SQL_SELECT		= "LogLevelSqlSelect";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：登録SQL実行)。
	 */
	protected static final String	APP_LOG_LEVEL_SQL_REGIST		= "LogLevelSqlRegist";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：内部統制)。
	 */
	protected static final String	APP_LOG_LEVEL_INTERNAL_CONTROL	= "LogLevelInternalControl";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：エラー)。
	 */
	protected static final String	APP_LOG_LEVEL_ERROR				= "LogLevelError";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：アプリケーション)。
	 */
	protected static final String	APP_LOG_LEVEL_APPLICATION		= "LogLevelApplication";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：デバッグ)。
	 */
	protected static final String	APP_LOG_LEVEL_DEBUG				= "LogLevelDebug";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力対象外パラメータ)。<br>
	 * パラメータログを出力時に用いられる。<br>
	 */
	protected static final String	APP_LOG_EXCLUDE_PARAMS			= "LogExcludeParams";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private LogUtility() {
		// 処理無し
	}
	
	/**
	 * ログ出力クラスを取得する。
	 * @param loggerName ログ出力クラス名
	 * @param mospParams MosP設定情報
	 * @return ログ出力クラス
	 */
	protected static LoggerInterface loadLogger(String loggerName, MospParams mospParams) {
		// MosP処理情報からログ出力クラス取得
		LoggerInterface logger = mospParams.getLogger(loggerName);
		// ログ出力クラス確認
		if (logger != null) {
			return logger;
		}
		// ログ出力クラス生成
		try {
			// ログ出力クラスの取得及び設定
			logger = (LoggerInterface)InstanceFactory.loadInstance(loggerName);
			logger.setLogger(mospParams);
		} catch (MospException e) {
			// スタックトレース出力
			e.printStackTrace();
			// MosPログ出力クラス取得及び設定(ログ出力クラスの取得に失敗した場合)
			logger = new MospLogger();
			logger.setLogger(mospParams);
		}
		// ログ出力クラスの配置
		mospParams.putLogger(loggerName, logger);
		return logger;
	}
	
	/**
	 * ログメッセージを作成する。<br>
	 * @param mospParams MosP設定情報
	 * @param logType    ログタイプ
	 * @param message    ログメッセージ
	 * @return ログメッセージ
	 */
	protected static String getLogMessage(MospParams mospParams, String logType, String message) {
		// ログメッセージ作成
		StringBuffer log = new StringBuffer();
		// ログタイプ
		log.append(logType);
		log.append(mospParams.getApplicationProperty(APP_LOG_SEPARATOR));
		// ASPユーザID
		log.append(mospParams.getUser() == null ? "" : mospParams.getUser().getAspUserId());
		log.append(mospParams.getApplicationProperty(APP_LOG_SEPARATOR));
		// ユーザID
		log.append(mospParams.getUser() == null ? "" : mospParams.getUser().getUserId());
		log.append(mospParams.getApplicationProperty(APP_LOG_SEPARATOR));
		// コマンド
		log.append(mospParams.getCommand() == null ? "" : mospParams.getCommand());
		log.append(mospParams.getApplicationProperty(APP_LOG_SEPARATOR));
		// メッセージ
		log.append(message);
		return log.toString();
	}
	
	/**
	 * コントローラ初期化ログを出力する。
	 * @param mospParams MosP処理情報
	 * @param message    ログメッセージ
	 */
	public static void controllerInit(MospParams mospParams, String message) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_CONTROLLER_INIT, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_CONTROLLER_INIT, message);
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_CONTROLLER_INIT)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
	/**
	 * アクセスログを出力する。
	 * @param mospParams MosP処理情報
	 */
	public static void access(MospParams mospParams) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_ACCESS, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// アクセス情報作成
		StringBuffer sb = new StringBuffer();
		sb.append(mospParams.getGeneralParam(MospConst.ATT_REMOTE_ADDR));
		sb.append(mospParams.getApplicationProperty(APP_LOG_SEPARATOR));
		sb.append(mospParams.getGeneralParam(MospConst.ATT_USER_AGENT));
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_ACCESS, sb.toString());
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_ACCESS)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
	/**
	 * パラメータログを出力する。<br>
	 * <br>
	 * ログ出力対象外パラメータに設定されているパラメータが含まれる場合、<br>
	 * 
	 * <br>
	 * バイナリはファイル名のみが出力される。<br>
	 * <br>
	 * @param mospParams MosP処理情報
	 */
	public static void parameter(MospParams mospParams) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_PARAMETER, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_PARAMETER, getParameterLog(mospParams));
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_PARAMETER)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
	/**
	 * ログ用パラメータ情報を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @return パラメータ情報
	 */
	protected static String getParameterLog(MospParams mospParams) {
		// ログ用パラメータ情報作成
		StringBuffer sb = new StringBuffer();
		// パラメータ情報の複製を作成
		Map<String, String[]> map = new HashMap<String, String[]>(mospParams.getRequestParamsMap());
		// ログ出力対象外パラメータ毎に処理
		for (String exclude : mospParams.getApplicationProperties(APP_LOG_EXCLUDE_PARAMS)) {
			// ログ出力対象外パラメータを除去
			map.remove(exclude);
		}
		// ログ用パラメータ情報を作成
		sb.append(JSON.encode(map));
		// ログ用パラメータ情報を取得
		return sb.toString();
	}
	
	/**
	 * アクション開始ログを出力する。
	 * @param mospParams MosP処理情報
	 * @param message    ログメッセージ
	 */
	public static void actionStart(MospParams mospParams, String message) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_ACTION_START, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_ACTION_START, message);
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_ACTION_START)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
	/**
	 * アクション終了ログを出力する。
	 * @param mospParams MosP処理情報
	 * @param message    ログメッセージ
	 */
	public static void actionEnd(MospParams mospParams, String message) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_ACTION_END, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_ACTION_END, message);
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_ACTION_END)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
	/**
	 * DB接続ログを出力する。
	 * @param mospParams MosP処理情報
	 * @param message    ログメッセージ
	 */
	public static void dbConnect(MospParams mospParams, String message) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_DB_CONNECT, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_DB_CONNECT, message);
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_DB_CONNECT)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
	/**
	 * 参照SQL実行ログを出力する。
	 * @param mospParams MosP処理情報
	 * @param message    ログメッセージ
	 */
	public static void sqlSelect(MospParams mospParams, String message) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_SQL_SELECT, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_SQL_SELECT, message);
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_SQL_SELECT)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
	/**
	 * 登録SQL実行ログを出力する。
	 * @param mospParams MosP処理情報
	 * @param message    ログメッセージ
	 */
	public static void sqlRegist(MospParams mospParams, String message) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_SQL_REGIST, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_SQL_REGIST, message);
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_SQL_REGIST)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
	/**
	 * 内部統制ログを出力する。
	 * @param mospParams MosP処理情報
	 * @param message    ログメッセージ
	 */
	public static void internalControl(MospParams mospParams, String message) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_INTERNAL_CONTROL, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_INTERNAL_CONTROL, message);
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_INTERNAL_CONTROL)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
	/**
	 * エラーログを出力する。
	 * @param mospParams MosP処理情報
	 * @param thrown     スローされたオブジェクト
	 */
	public static void error(MospParams mospParams, Throwable thrown) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_ERROR, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// スタックトレースを取得
		String message = MospUtility.getStackTrace(thrown);
		// エラーログメッセージ作成
		message = getLogMessage(mospParams, APP_LOG_TYPE_ERROR, message);
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_ERROR)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, message);
		}
	}
	
	/**
	 * アプリケーションログを出力する。
	 * @param mospParams MosP処理情報
	 * @param message    ログメッセージ
	 */
	public static void application(MospParams mospParams, String message) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_APPLICATION, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_APPLICATION, message);
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_APPLICATION)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
	/**
	 * デバッグログを出力する。
	 * @param mospParams MosP処理情報
	 * @param message    ログメッセージ
	 */
	public static void debug(MospParams mospParams, String message) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_DEBUG, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_DEBUG, message);
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_DEBUG)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
	/**
	 * ログを出力する。
	 * @param mospParams MosP処理情報
	 * @param level      ログレベル
	 * @param message    ログメッセージ
	 */
	public static void log(MospParams mospParams, int level, String message) {
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログメッセージ作成
		String msg = getLogMessage(mospParams, APP_LOG_TYPE_GENERAL, message);
		// ログ出力クラス毎にログを出力
		for (String loggerName : mospParams.getApplicationProperties(APP_LOG_TYPE_GENERAL)) {
			// ログ出力
			loadLogger(loggerName, mospParams).log(level, msg);
		}
	}
	
}
