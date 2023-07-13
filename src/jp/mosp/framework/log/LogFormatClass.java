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

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.logging.Formatter;
import java.util.logging.Handler;
import java.util.logging.LogRecord;

import jp.mosp.framework.constant.MospConst;

/**
 * ログのフォーマットを形成する。<br><br>
 * MosPフレームワーク標準のログフォーマットを形成する。<br>
 * セパレータ等を変更することができる。<br>
 */
public final class LogFormatClass extends Formatter {
	
	/**
	 * 日付フォーマット設定用定数。<br>
	 */
	public static final String	DATE_FORMAT	= "yyyy/MM/dd";
	
	/**
	 * 時間フォーマット設定用定数。<br>
	 */
	public static final String	TIME_FORMAT	= "HH:mm:ss";
	
	/**
	 * 日にちフォーマット設定用定数。<br>
	 */
	public static final String	DAY_FORMAT	= "E";
	
	private String				separator;
	
	
	/**
	 * コンストラクタ。<br>
	 * ログの区切り文字を設定する。
	 * @param separator ログセパレータ
	 */
	public LogFormatClass(String separator) {
		this.separator = separator != null ? separator : "|";
	}
	
	/**
	 * ログのフォーマットを形成する。<br>
	 * 日付、レベル、メッセージをセパレータで区切る。<br>
	 */
	@Override
	public String format(LogRecord rec) {
		SimpleDateFormat sdf = new SimpleDateFormat(TIME_FORMAT + separator + DATE_FORMAT + separator + DAY_FORMAT);
		StringBuffer sb = new StringBuffer();
		sb.append(sdf.format(new Date()));
		sb.append(separator);
		sb.append(rec.getLevel());
		sb.append(separator);
		sb.append(formatMessage(rec));
		sb.append(MospConst.LINE_SEPARATOR);
		return sb.toString();
	}
	
	/**
	 * フォーマットされたレコードのセットのヘッダ文字列を返す。<br>
	 */
	@Override
	public String getHead(Handler h) {
		return "";
	}
	
	/**
	 * フォーマットされたレコードセットの末尾の文字列を返す。<br>
	 */
	@Override
	public String getTail(Handler h) {
		return "";
	}
	
}
