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
package jp.mosp.platform.bean.mail.impl;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.Properties;

import javax.mail.Address;
import javax.mail.Message;
import javax.mail.Session;
import javax.mail.Transport;
import javax.mail.internet.AddressException;
import javax.mail.internet.InternetAddress;
import javax.mail.internet.MimeMessage;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.LogUtility;
import jp.mosp.framework.utils.MessageUtility;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.mail.MailStartTlsBeanInterface;
import jp.mosp.platform.constant.PlatformMailConst;
import jp.mosp.platform.utils.MailTemplateUtility;
import jp.mosp.platform.utils.PfMessageUtility;

/**
 * メール送信(STARTTLS)処理。<br>
 */
public class MailStartTlsBean extends PlatformBean implements MailStartTlsBeanInterface {
	
	/**
	 * SMTP設定(認証)。<br>
	 */
	protected static final String	SMTP_AUTH					= "true";
	
	/**
	 * SMTP設定(ポート番号)。<br>
	 */
	protected static final int		SMTP_PORT					= 587;
	
	/**
	 * SMTP設定(STARTTLS設定)。<br>
	 */
	protected static final String	SMTP_ATT_STARTTLS_ENABLE	= "mail.smtp.starttls.enable";
	
	/**
	 * SMTP設定(STARTTLS)。<br>
	 */
	protected static final String	SMTP_STARTTLS_ENABLE		= "true";
	
	/**
	 * SMTP設定(STARTTLS設定)。<br>
	 */
	protected static final String	SMTP_ATT_STARTTLS_REQUIRED	= "mail.smtp.starttls.required";
	
	/**
	 * SMTP設定(STARTTLS)。<br>
	 */
	protected static final String	SMTP_STARTTLS_REQUIRED		= "true";
	
	/**
	 * MosPアプリケーション設定キー(ログ出力レベル：メール送信)。<br>
	 */
	protected static final String	APP_LOG_LEVEL_MAIL			= "LogLevelMail";
	
	/**
	 * 区切文字(メールアドレス)。<br>
	 */
	protected static final String	SEPARATOR_ADDRESS			= "@";
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public MailStartTlsBean() {
		super();
	}
	
	@Override
	public void initBean() {
		// 処理無し
	}
	
	@Override
	public boolean sendAndIsExceptionOccurred(String recipient, String subject, String templatePath, Object dto)
			throws MospException {
		// 受信者メールアドレスリストを準備
		List<String> recipients = new ArrayList<String>();
		recipients.add(recipient);
		// メール本文を取得
		String text = makeText(templatePath, dto);
		// メールを送信(STARTTLS)
		return sendAndIsExceptionOccurred(recipients, subject, text, false);
	}
	
	@Override
	public void send(String recipient, String subject, String appTemplate, Object dto) throws MospException {
		// 受信者メールアドレスリストを準備
		List<String> recipients = new ArrayList<String>();
		recipients.add(recipient);
		// メール本文を取得
		String text = makeText(mospParams.getApplicationProperty(appTemplate), dto);
		// メールを送信(STARTTLS)
		sendAndIsExceptionOccurred(recipients, subject, text, true);
	}
	
	@Override
	public void send(List<String> recipients, String subject, String text) {
		// メールを送信(STARTTLS)
		sendAndIsExceptionOccurred(recipients, subject, text, true);
	}
	
	/**
	 * メールを送信(STARTTLS)する。<br>
	 * @param recipients 受信者メールアドレスリスト
	 * @param subject    メール題目
	 * @param text       メール本文
	 * @param isErrNecessary エラーメッセージ要否(true：要、false：不要)
	 * @return 例外発生有無(true：送信処理で例外が発生した場合、false：送信処理で例外が発生しなかった場合)
	 */
	protected boolean sendAndIsExceptionOccurred(List<String> recipients, String subject, String text,
			boolean isErrNecessary) {
		// 例外発生有無(true：送信処理で例外が発生した場合、false：送信処理で例外が発生しなかった場合)を準備
		boolean isExceptionOccurred = false;
		// 受信者メールアドレスリストから空文字を除去
		MospUtility.removeEmpty(recipients);
		// 受信者メールアドレスリストが空である場合
		if (MospUtility.isEmpty(recipients)) {
			// 処理不要
			return isExceptionOccurred;
		}
		// メールアドレス配列を準備
		Address[] addresses = makeAddresses(recipients, isErrNecessary);
		// 送信元メールアドレスを準備
		Optional<Address> transmitter = makeAddress(getTransmitter(), getTransmitterName(), isErrNecessary);
		// プロパティ群(SMTP用)を作成
		Properties props = makeProperties();
		// セッションを取得
		Session session = Session.getInstance(props);
		// メッセージを準備
		MimeMessage mimeMessage = new MimeMessage(session);
		// トランスポートを準備
		Transport transport = null;
		try {
			// 送信元メールアドレスを設定
			mimeMessage.setFrom(transmitter.get());
			// 送信先メールアドレスを設定
			mimeMessage.setRecipients(Message.RecipientType.TO, addresses);
			// メールのタイトルを設定
			mimeMessage.setSubject(subject, PlatformMailConst.UTF_8);
			// メールの内容を指定
			mimeMessage.setText(text, PlatformMailConst.UTF_8);
			// 送信日付を指定
			mimeMessage.setSentDate(getSystemTime());
			// トランスポートを取得
			transport = session.getTransport(PlatformMailConst.SMTP);
			// メールサーバと接続
			transport.connect(getSmtpHost(), getSmtpUser(), getSmtpPass());
			// メール送信
			transport.sendMessage(mimeMessage, mimeMessage.getAllRecipients());
			// ログ出力
			outputSendLog(recipients);
		} catch (Throwable t) {
			// エラーログを出力
			LogUtility.error(mospParams, t);
			// MosP処理情報にエラーメッセージを設定
			addErrorSendMailFailed(isErrNecessary);
			// 例外発生有無を設定
			isExceptionOccurred = true;
		} finally {
			try {
				// トランスポートが存在する場合
				if (transport != null) {
					// トランスポートをクローズ
					transport.close();
				}
			} catch (Throwable t) {
				// エラーログを出力
				LogUtility.error(mospParams, t);
			}
		}
		// 例外発生有無を取得
		return isExceptionOccurred;
	}
	
	/**
	 * プロパティ群(SMTP用)を作成する。<br>
	 * @return プロパティ群(SMTP用)
	 */
	protected Properties makeProperties() {
		// プロパティを準備
		Properties props = new Properties();
		// 値を設定
		props.put(PlatformMailConst.SMTP_PORT, String.valueOf(getSmtpPort()));
		props.put(PlatformMailConst.SMTP_AUTH, SMTP_AUTH);
		props.put(PlatformMailConst.CONNETCTION_TIME_OUT, PlatformMailConst.TIME_OUT);
		props.put(PlatformMailConst.SMTP_TIME_OUT, PlatformMailConst.TIME_OUT);
		props.put(SMTP_ATT_STARTTLS_ENABLE, SMTP_STARTTLS_ENABLE);
		props.put(SMTP_ATT_STARTTLS_REQUIRED, SMTP_STARTTLS_REQUIRED);
		// 送信者メールアドレスを@で分割
		String[] address = MospUtility.split(getTransmitter(), SEPARATOR_ADDRESS);
		// 分割できた場合
		if (address.length > 1) {
			// メールユーザとホストを設定
			props.put(PlatformMailConst.MAIL_USER, address[0]);
			props.put(PlatformMailConst.MAIL_HOST, address[1]);
		}
		// プロパティを取得
		return props;
	}
	
	/**
	 * アドレス配列を作成する。<br>
	 * @param recipients     受信者メールアドレスリスト
	 * @param isErrNecessary エラーメッセージ要否(true：要、false：不要)
	 * @return アドレス配列
	 */
	protected Address[] makeAddresses(List<String> recipients, boolean isErrNecessary) {
		// アドレスリストを準備
		List<Address> list = new ArrayList<Address>();
		// 受信者メールアドレス毎に処理
		for (String recipient : recipients) {
			// アドレスを作成
			Optional<Address> address = makeAddress(recipient, isErrNecessary);
			// アドレスをリストに追加
			address.ifPresent(a -> list.add(a));
		}
		// アドレス配列を作成
		return list.toArray(new Address[list.size()]);
	}
	
	/**
	 * アドレスを作成する。<br>
	 * アドレスが作成できない場合、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param address        メールアドレス
	 * @param isErrNecessary エラーメッセージ要否(true：要、false：不要)
	 * @return アドレス
	 */
	protected Optional<Address> makeAddress(String address, boolean isErrNecessary) {
		try {
			// アドレスを作成
			return Optional.of(new InternetAddress(address));
		} catch (AddressException e) {
			// エラーログを出力
			LogUtility.error(mospParams, e);
			// MosP処理情報にエラーメッセージを設定
			addErrorAddressInvalid(address, isErrNecessary);
			// nullを取得
			return Optional.ofNullable(null);
		}
	}
	
	/**
	 * アドレスを作成する。<br>
	 * @param address        メールアドレス
	 * @param personal       名前
	 * @param isErrNecessary エラーメッセージ要否(true：要、false：不要)
	 * @return アドレス
	 */
	protected Optional<Address> makeAddress(String address, String personal, boolean isErrNecessary) {
		try {
			// アドレスを作成
			return Optional.of(new InternetAddress(address, personal));
		} catch (Exception e) {
			// エラーログを出力
			LogUtility.error(mospParams, e);
			// MosP処理情報にエラーメッセージを設定
			addErrorAddressInvalid(address, isErrNecessary);
			// nullを取得
			return Optional.ofNullable(null);
		}
	}
	
	/**
	 * テンプレートからメール本文を作成する。<br>
	 * @param templatePath テンプレートファイルパス
	 * @param dto         メールテンプレート情報
	 * @return メール本文
	 * @throws MospException メール本文の作成に失敗した場合
	 */
	protected String makeText(String templatePath, Object dto) throws MospException {
		// テンプレートと情報から文字列を作成
		return MailTemplateUtility.makeText(mospParams, templatePath, dto);
	}
	
	/**
	 * SMTPホストを取得する。<br>
	 * @return SMTPホスト
	 */
	protected String getSmtpHost() {
		return mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_HOST);
	}
	
	/**
	 * SMTPポートを取得する。<br>
	 * @return SMTPポート
	 */
	protected int getSmtpPort() {
		return mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_PORT, SMTP_PORT);
	}
	
	/**
	 * SMTPユーザを取得する。<br>
	 * @return SMTPユーザ
	 */
	protected String getSmtpUser() {
		return mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_USER_NAME);
	}
	
	/**
	 * SMTPユーザパスワードを取得する。<br>
	 * @return SMTPユーザパスワード
	 */
	protected String getSmtpPass() {
		return mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_PASSWORD);
	}
	
	/**
	 * SMTP送信者メールアドレスを取得する。<br>
	 * @return SMTP送信者メールアドレス
	 */
	protected String getTransmitter() {
		return mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_ADDRESS);
	}
	
	/**
	 * SMTP送信者名を取得する。<br>
	 * @return SMTP送信者名
	 */
	protected String getTransmitterName() {
		return mospParams.getApplicationProperty(PlatformMailConst.APP_MAIL_PERSONAL);
	}
	
	/**
	 * メール送信ログを出力する。<br>
	 * @param recipients 受信者メールアドレスリスト
	 */
	protected void outputSendLog(List<String> recipients) {
		// ログレベル取得
		int level = mospParams.getApplicationProperty(APP_LOG_LEVEL_MAIL, 0);
		// ログレベルが0の場合
		if (level == 0) {
			// ログ出力無し
			return;
		}
		// ログを出力
		LogUtility.log(mospParams, level, makeMessage(recipients, PlatformMailConst.MSG_MAIL_SEND_SUCCESS));
	}
	
	/**
	 * メール送信メッセージを作成する。<br>
	 * @param recipients 受信者メールアドレスリスト
	 * @param message    メッセージ
	 * @return メール送信メッセージ
	 */
	protected String makeMessage(List<String> recipients, String message) {
		// メッセージを作成
		StringBuilder sb = new StringBuilder();
		sb.append(MospUtility.toSeparatedString(recipients, MospConst.APP_PROPERTY_SEPARATOR));
		sb.append(message);
		// メッセージを取得
		return sb.toString();
	}
	
	/**
	 * エラーメッセージ(メールアドレス不正)を設定する。<br>
	 * @param address        メールアドレス
	 * @param isErrNecessary エラーメッセージ要否(true：要、false：不要)
	 */
	protected void addErrorAddressInvalid(String address, boolean isErrNecessary) {
		// エラーメッセージが不要である場合
		if (isErrNecessary == false) {
			// 処理無し
			return;
		}
		// エラーメッセージを設定
		PfMessageUtility.addErrorInvalidMailAddresss(mospParams, address);
	}
	
	/**
	 * エラーメッセージ(メール送信エラー)を設定する。<br>
	 * @param isErrNecessary エラーメッセージ要否(true：要、false：不要)
	 */
	protected void addErrorSendMailFailed(boolean isErrNecessary) {
		// エラーメッセージが不要である場合
		if (isErrNecessary == false) {
			// 処理無し
			return;
		}
		// エラーメッセージを設定
		MessageUtility.addErrorSendMailFailed(mospParams);
	}
	
}
