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
package jp.mosp.platform.bean.mail;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * メール送信(STARTTLS)処理インターフェース。<br>
 */
public interface MailStartTlsBeanInterface extends BaseBeanInterface {
	
	/**
	 * メールを送信(STARTTLS)する。<br>
	 * 送信処理で例外が発生した場合でも、MosP処理情報にエラーメッセージを設定しない。<br>
	 * @param recipient    受信者メールアドレス
	 * @param subject      メール題目
	 * @param templatePath テンプレートファイルパス
	 * @param dto          メールテンプレート情報
	 * @return 例外発生有無(true：送信処理で例外が発生した場合、false：送信処理で例外が発生しなかった場合)
	 * @throws MospException メール本文の作成に失敗した場合
	 */
	boolean sendAndIsExceptionOccurred(String recipient, String subject, String templatePath, Object dto)
			throws MospException;
	
	/**
	 * メールを送信(STARTTLS)する。<br>
	 * 送信処理で例外が発生した場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param recipient   受信者メールアドレス
	 * @param subject     メール題目
	 * @param appTemplate MosPアプリケーション設定キー(テンプレートファイルパス)
	 * @param dto         メールテンプレート情報
	 * @throws MospException メール本文の作成に失敗した場合
	 */
	void send(String recipient, String subject, String appTemplate, Object dto) throws MospException;
	
	/**
	 * メールを送信(STARTTLS)する。<br>
	 * 送信処理で例外が発生した場合は、MosP処理情報にエラーメッセージを設定する。<br>
	 * @param recipients   受信者メールアドレスリスト
	 * @param subject      メール題目
	 * @param text         メール本文
	 */
	void send(List<String> recipients, String subject, String text);
	
}
