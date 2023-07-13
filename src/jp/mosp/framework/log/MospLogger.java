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
package jp.mosp.framework.log;

import java.io.IOException;
import java.util.logging.FileHandler;
import java.util.logging.Level;
import java.util.logging.Logger;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.LogUtility;

/**
 * MosP用Loggerクラス。
 */
public class MospLogger implements LoggerInterface {
	
	private static final long		serialVersionUID	= -3220473796559670965L;
	
	/**
	 *  MosPアプリケーション設定キー(ログエンコーディング)。<br>
	 */
	protected static final String	APP_LOG_ENCODING	= "LogEncoding";
	
	/**
	 *  MosPアプリケーション設定キー(ログファイル名パターン)。<br>
	 */
	protected static final String	APP_LOG_PATTERN		= "LogPattern";
	
	/**
	 *  MosPアプリケーション設定キー(ログファイル最大バイト数)。<br>
	 */
	protected static final String	APP_LOG_LIMIT		= "LogLimit";
	
	/**
	 *  MosPアプリケーション設定キー(ログファイルローテーション数)。<br>
	 */
	protected static final String	APP_LOG_COUNT		= "LogCount";
	
	/**
	 *  MosPアプリケーション設定キー(ログ出力レベル)。<br>
	 */
	protected static final String	APP_LOG_LEVEL		= "LogLevel";
	
	/**
	 *  ログレベル(FINEST)。<br>
	 */
	protected static final int		LOG_LEVEL_FINEST	= 300;
	
	/**
	 *  ログレベル(FINER)。<br>
	 */
	protected static final int		LOG_LEVEL_FINER		= 400;
	
	/**
	 *  ログレベル(FINE)。<br>
	 */
	protected static final int		LOG_LEVEL_FINE		= 500;
	
	/**
	 *  ログレベル(CONFIG)。<br>
	 */
	protected static final int		LOG_LEVEL_CONFIG	= 700;
	
	/**
	 *  ログレベル(INFO)。<br>
	 */
	protected static final int		LOG_LEVEL_INFO		= 800;
	
	/**
	 *  ログレベル(WARNING)。<br>
	 */
	protected static final int		LOG_LEVEL_WARNING	= 900;
	
	/**
	 *  ログレベル(ALL)。<br>
	 */
	protected static final int		LOG_LEVEL_ALL		= 0;
	
	/**
	 * ログ出力クラス(Serialize対象外)。
	 */
	protected transient Logger		logger;
	
	
	/**
	 * ログレベルを取得する。<br>
	 * @param level ログレベル(数値)
	 * @return ログレベル
	 */
	protected Level getLevel(int level) {
		switch (level) {
			case 0:
				return Level.ALL;
			case LOG_LEVEL_FINEST:
				return Level.FINEST;
			case LOG_LEVEL_FINER:
				return Level.FINER;
			case LOG_LEVEL_FINE:
				return Level.FINE;
			case LOG_LEVEL_CONFIG:
				return Level.CONFIG;
			case LOG_LEVEL_INFO:
				return Level.INFO;
			case LOG_LEVEL_WARNING:
				return Level.WARNING;
			default:
				return Level.SEVERE;
		}
	}
	
	@Override
	public void log(int level, String message) {
		try {
			// ログ出力
			logger.log(getLevel(level), message);
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
	
	@Override
	public void setLogger(MospParams mospParams) {
		try {
			// ログ出力クラス確認
			if (logger != null) {
				// ログ出力クラスが存在していれば処理不要
				return;
			}
			// ログ出力クラス名生成(DocBase+クラス名)
			String loggerName = mospParams.getApplicationProperty(MospConst.APP_DOCBASE) + getClass().getName();
			// ログ出力クラス取得
			logger = Logger.getLogger(loggerName);
			// ハンドラ確認
			if (logger.getHandlers().length > 0) {
				// ハンドラが設定されていれば処理不要
				return;
			}
			// ログファイルパターン取得
			String pattern = mospParams.getApplicationProperty(MospConst.APP_DOCBASE)
					+ mospParams.getApplicationProperty(APP_LOG_PATTERN);
			// ファイル最大バイト数取得
			int limit = mospParams.getApplicationProperty(APP_LOG_LIMIT, 0);
			// ローテーション数取得
			int count = mospParams.getApplicationProperty(APP_LOG_COUNT, 0);
			// ハンドラ生成
			FileHandler fh = new FileHandler(pattern, limit, count, true);
			// Formatter設定
			fh.setFormatter(new LogFormatClass(mospParams.getApplicationProperty(LogUtility.APP_LOG_SEPARATOR)));
			// エンコーディング設定
			fh.setEncoding(mospParams.getApplicationProperty(APP_LOG_ENCODING));
			// ログ出力クラスにハンドラを追加
			logger.addHandler(fh);
			// ログの出力レベルを設定
			logger.setLevel(getLevel(mospParams.getApplicationProperty(APP_LOG_LEVEL, 0)));
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
	
}
