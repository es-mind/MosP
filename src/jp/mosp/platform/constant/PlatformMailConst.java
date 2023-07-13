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
 * メール送信で用いる定数を宣言する。<br>
 */
public class PlatformMailConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PlatformMailConst() {
		// 処理無し
	}
	
	
	/**
	 * MosPアプリケーション設定キー(メール送信機能）。<br>
	 */
	public static final String	APP_USE_MAIL				= "UseMail";
	
	/**
	 * メールサーバ情報キー(mail.user)。<br>
	 */
	public static final String	MAIL_USER					= "mail.user";
	
	/**
	 * メールサーバ情報キー(mail.host)。<br>
	 */
	public static final String	MAIL_HOST					= "mail.host";
	
	/**
	 * メールサーバ情報キー(mail.smtp.auth)。<br>
	 */
	public static final String	SMTP_AUTH					= "mail.smtp.auth";
	
	/**
	 * メールサーバ情報キー(mail.smtp.port)。<br>
	 */
	public static final String	SMTP_PORT					= "mail.smtp.port";
	
	/**
	 * メールサーバ情報キー(mail.smtp.connectiontimeout)。<br>
	 */
	public static final String	CONNETCTION_TIME_OUT		= "mail.smtp.connectiontimeout";
	
	/**
	 * メールサーバ情報キー(mail.smtp.timeout)。<br>
	 */
	public static final String	SMTP_TIME_OUT				= "mail.smtp.timeout";
	
	/**
	 * 文字コード(タイトル及び本文)。<br>
	 */
	public static final String	UTF_8						= "UTF-8";
	
	/**
	 * メール送信プロトコル(smtp)。<br>
	 */
	public static final String	SMTP						= "smtp";
	
	/**
	 * タイムアウト値。<br>
	 */
	public static final String	TIME_OUT					= "60000";
	
	/**
	 * MosPアプリケーション設定キー(メールアドレス)。<br>
	 */
	public static final String	APP_MAIL_ADDRESS			= "MailAddress";
	
	/**
	 * MosPアプリケーション設定キー(メール個人名)。<br>
	 */
	public static final String	APP_MAIL_PERSONAL			= "MailPersonal";
	
	/**
	 * MosPアプリケーション設定キー(メールホスト)。<br>
	 */
	public static final String	APP_MAIL_HOST				= "MailHost";
	
	/**
	 * MosPアプリケーション設定キー(メールポート)。<br>
	 */
	public static final String	APP_MAIL_PORT				= "MailPort";
	
	/**
	 * MosPアプリケーション設定キー(メールユーザ名)。<br>
	 */
	public static final String	APP_MAIL_USER_NAME			= "MailUserName";
	
	/**
	 * MosPアプリケーション設定キー(メールパスワード)。<br>
	 */
	public static final String	APP_MAIL_PASSWORD			= "MailPassword";
	
	/**
	 * メッセージ(ログ出力用)。<br>
	 */
	public static final String	MSG_MAIL_SEND_SUCCESS		= "へメールを送信しました。";
	
	/**
	 * テンプレート情報(runtime.log.logsystem.class)。<br>
	 */
	public static final String	RUNTIME_LOG_LOGSYSTEM_CLASS	= "runtime.log.logsystem.class";
	
	/**
	 * テンプレート情報(file)。<br>
	 */
	public static final String	FILE						= "file";
	
	/**
	 * テンプレート情報(resource.loader)。<br>
	 */
	public static final String	RESOURCE_LOADER				= "resource.loader";
	
	/**
	 * テンプレート情報(file.resource.loader.description)。<br>
	 */
	public static final String	RESOURCE_LOADER_DESCRIPTION	= FILE + ".resource.loader.description";
	
	/**
	 * テンプレート情報(file.resource.loader.class)。<br>
	 */
	public static final String	RESOURCE_LOADER_CLASS		= FILE + ".resource.loader.class";
	
	/**
	 * テンプレート情報(file.resource.loader.path)。<br>
	 */
	public static final String	RESOURCE_LOADER_PATH		= FILE + ".resource.loader.path";
	
	/**
	 * テンプレート情報(file.resource.loader.cache)。<br>
	 */
	public static final String	RESOURCE_LOADER_CACHE		= FILE + ".resource.loader.cache";
	
	/**
	 * テンプレート情報(file.resource.loader.modificationCheckInterval)。<br>
	 */
	public static final String	RESOURCE_LOADER_INTERVAL	= FILE + ".resource.loader.modificationCheckInterval";
	
	/**
	 * テンプレート情報(input.encoding)。<br>
	 */
	public static final String	INPUT_ENCODING				= "input.encoding";
	
	/**
	 * テンプレート情報(output.encoding)。<br>
	 */
	public static final String	OUTPUT_ENCODING				= "output.encoding";
	
	/**
	 * 文字コード(テンプレート)。<br>
	 */
	public static final String	ENCODING					= "Windows-31J";
	
	/**
	 * テンプレート情報(メール項目情報)。<br>
	 */
	public static final String	KEY_DTO						= "dto";
	
}
