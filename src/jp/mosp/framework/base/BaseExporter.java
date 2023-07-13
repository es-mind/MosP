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

import javax.servlet.http.HttpServletResponse;

import jp.mosp.framework.utils.UserAgentUtility;

/**
 * ファイル送出クラスの基本機能を提供する。<br>
 */
public class BaseExporter {
	
	/**
	 * MosPアプリケーション設定キー(レスポンスコンテンツタイプ：FILE)。
	 */
	protected static final String	APP_CONTENT_TYPE_FILE		= "ContentType-File";
	
	/**
	 * MosPアプリケーション設定キープレフィックス(レスポンスコンテンツタイプ)。
	 */
	protected static final String	APP_CONTENT_TYPE_PREFIX		= "ContentType-";
	
	/**
	 * レスポンスのヘッダー(FILE)。<br>
	 */
	protected static final String	APP_RESPONSE_DISPOSITION	= "Content-Disposition";
	
	/**
	 * ClientAbortExceptionの文字列。<br>
	 * 例外判定に用いる。<br>
	 */
	protected static final String	STR_CLIENT_ABORT_EXCEPTION	= "ClientAbortException";
	
	
	/**
	 * 送出クラス名でMosP設定情報からコンテンツタイプを取得し、設定する。
	 * @param mospParams MosPパラメータ
	 * @param response レスポンス
	 */
	protected void setContentType(MospParams mospParams, HttpServletResponse response) {
		// MosPアプリケーション設定キー準備(プレフィックス+クラス名)
		String key = APP_CONTENT_TYPE_PREFIX + mospParams.getFile().getClass().getName();
		// コンテンツタイプ取得
		String contentType = mospParams.getApplicationProperty(key);
		// コンテンツタイプ設定
		response.setContentType(contentType);
	}
	
	/**
	 * レスポンスコンテンツタイプ(ファイル)を設定する。<br>
	 * @param mospParams MosPパラメータ
	 * @param response レスポンス
	 */
	protected void setFileContentType(MospParams mospParams, HttpServletResponse response) {
		// コンテンツタイプ取得
		String contentType = mospParams.getApplicationProperty(APP_CONTENT_TYPE_FILE);
		// コンテンツタイプ設定
		response.setContentType(contentType);
	}
	
	/**
	 * 送出ファイル名を設定する。<br>
	 * @param mospParams MosPパラメータ
	 * @param response レスポンス
	 * @throws MospException ファイル名の取得に失敗した場合
	 */
	protected void setFileName(MospParams mospParams, HttpServletResponse response) throws MospException {
		// コンテンツファイル名取得
		String fileName = UserAgentUtility.getExportFileName(mospParams);
		// コンテンツファイル名設定準備
		String disposition = mospParams.getApplicationProperty(APP_RESPONSE_DISPOSITION);
		// コンテンツのファイル名を設定
		response.addHeader(APP_RESPONSE_DISPOSITION, disposition + fileName);
	}
	
	/**
	 * 対象例外がClientAbortExceptionであるかを確認する。<br>
	 * Tomcat以外のAPPサーバでは、この方法で判別できない場合がある。<br>
	 * @param e 対象例外
	 * @return 確認結果(true：ClientAbortExceptionである、false：ClientAbortExceptionでない)
	 */
	protected boolean isClientAbortException(Exception e) {
		// 対象例外のtoString()にClientAbortExceptionが含まれているかを確認
		return e.toString().contains(STR_CLIENT_ABORT_EXCEPTION);
	}
	
}
