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

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;

/**
 * UserAgent(クライアントのブラウザ)に関するメソッドを提供する。<br>
 */
public class UserAgentUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private UserAgentUtility() {
		// 処理無し
	}
	
	/**
	 * UserAgentを取得する。<br>
	 * UserAgentがMosP処理情報に設定されていない場合は、空文字列を返す。<br>
	 * @param mospParams MosP処理情報
	 * @return UserAgent
	 */
	public static String getUserAgent(MospParams mospParams) {
		// UserAgent取得
		Object userAgent = mospParams.getGeneralParam(MospConst.ATT_USER_AGENT);
		if (userAgent == null) {
			return "";
		}
		return String.valueOf(userAgent);
	}
	
	/**
	 * UserAgentがIEであるかを確認する。<br>
	 * @param userAgent UserAgent
	 * @return 確認結果(true：IEである、false：IEでない)
	 */
	public static boolean isIE(String userAgent) {
		Pattern pattern = Pattern.compile(".*((MSIE)+ [0-9]+\\.[0-9]+).*");
		Matcher matcher = pattern.matcher(userAgent);
		return matcher.matches();
	}
	
	/**
	 * UserAgentがIE11であるかを確認する。<br>
	 * @param userAgent UserAgent
	 * @return 確認結果(true：IE11である、false：IE11でない)
	 */
	public static boolean isIE11(String userAgent) {
		Pattern pattern = Pattern.compile(".*((Trident/)+[0-9]+\\.[0-9]+\\.?[0-9]*).*");
		Matcher matcher = pattern.matcher(userAgent);
		// 「MSIE」が含まれる場合はIE(10以下)と判断
		return isIE(userAgent) == false && matcher.matches();
	}
	
	/**
	 * UserAgentがEdgeであるかを確認する。<br>
	 * @param userAgent UserAgent
	 * @return 確認結果(true：Edgeである、false：Edgeでない)
	 */
	public static boolean isEdge(String userAgent) {
		Pattern pattern = Pattern.compile(".*((Edge/)+[0-9]+\\.[0-9]+\\.?[0-9]*).*");
		Matcher matcher = pattern.matcher(userAgent);
		return matcher.matches();
	}
	
	/**
	 * UserAgentがFirefoxであるかを確認する。<br>
	 * @param userAgent UserAgent
	 * @return 確認結果(true：Firefoxである、false：Firefoxでない)
	 */
	public static boolean isFirefox(String userAgent) {
		Pattern pattern = Pattern.compile(".*((Firefox/)+[0-9]+\\.[0-9]+\\.?[0-9]*).*");
		Matcher matcher = pattern.matcher(userAgent);
		return matcher.matches();
	}
	
	/**
	 * UserAgentがChromeであるかを確認する。<br>
	 * @param userAgent UserAgent
	 * @return 確認結果(true：Chromeである、false：Chromeでない)
	 */
	public static boolean isChrome(String userAgent) {
		Pattern pattern = Pattern.compile(".*((Chrome)+/?[0-9]+\\.?[0-9]*).*");
		Matcher matcher = pattern.matcher(userAgent);
		// 「Edge」が含まれる場合はEdgeと判断
		return isEdge(userAgent) == false && matcher.matches();
	}
	
	/**
	 * UserAgentがSafariであるかを確認する。<br>
	 * @param userAgent UserAgent
	 * @return 確認結果(true：Safariである、false：Safariでない)
	 */
	public static boolean isSafari(String userAgent) {
		Pattern pattern = Pattern.compile(".*((Version/)+[0-9]+\\.?[0-9]*\\.?[0-9]* Safari).*");
		Matcher matcher = pattern.matcher(userAgent);
		return matcher.matches();
	}
	
	/**
	 * UserAgentがOperaであるかを確認する。<br>
	 * @param userAgent UserAgent
	 * @return 確認結果(true：Operaである、false：Operaでない)
	 */
	public static boolean isOpera(String userAgent) {
		Pattern pattern = Pattern.compile(".*((Opera)+/? ?[0-9]+\\.[0-9]*).*");
		Matcher matcher = pattern.matcher(userAgent);
		return matcher.matches();
	}
	
	/**
	 * 送出ファイル名を取得する。<br>
	 * 日本語のファイル名を用いるため、UserAgentに応じて
	 * MosP処理情報に設定されたファイル名をエンコードする。<br>
	 * @param mospParams MosP処理情報
	 * @return 送出ファイル名
	 * @throws MospException ファイル名のエンコードに失敗した場合
	 */
	public static String getExportFileName(MospParams mospParams) throws MospException {
		// UserAgent取得
		String userAgent = getUserAgent(mospParams);
		// ファイル名取得
		String fileName = mospParams.getFileName();
		// ファイル名確認
		if (mospParams.getFileName() == null) {
			return "";
		}
		// ブラウザ毎に処理
		try {
			// IEかIE11の場合
			if (isIE(userAgent) || isIE11(userAgent) || isEdge(userAgent)) {
				return URLEncoder.encode(fileName, MospUtility.CHARACTER_ENCODING);
			}
			// FirefoxかChromeかSafariかOperaの場合
			if (isFirefox(userAgent) || isChrome(userAgent) || isSafari(userAgent) || isOpera(userAgent)) {
				return new String(MospUtility.getBytes(fileName), "ISO-8859-1");
			}
		} catch (UnsupportedEncodingException e) {
			throw new MospException(e);
		}
		return fileName;
	}
	
}
