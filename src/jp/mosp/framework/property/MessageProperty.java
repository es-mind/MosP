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
package jp.mosp.framework.property;

/**
 * MosP設定情報(メッセージ)。<br>
 */
public class MessageProperty implements BaseProperty {
	
	/**
	 * キー(メッセージID)。
	 */
	private String	key;
	
	/**
	 * メッセージ本体。
	 */
	private String	messageBody;
	
	/**
	 * クライアント利用可否。
	 */
	private boolean	clientAvailable;
	
	
	/**
	 * メッセージ情報を生成する。<br>
	 * @param key             キー(メッセージID)
	 * @param messageBody     メッセージ本体
	 * @param clientAvailable クライアント利用可否
	 */
	public MessageProperty(String key, String messageBody, boolean clientAvailable) {
		this.key = key;
		this.messageBody = messageBody;
		this.clientAvailable = clientAvailable;
	}
	
	/**
	 * キー(メッセージID)を取得する。
	 * @return キー(メッセージID)
	 */
	@Override
	public String getKey() {
		return key;
	}
	
	/**
	 * メッセージ本体を取得する。
	 * @return メッセージ本体
	 */
	public String getMessageBody() {
		return messageBody;
	}
	
	/**
	 * クライアント利用可否を取得する。
	 * @return クライアント利用可否
	 */
	public boolean getClientAvailable() {
		return clientAvailable;
	}
	
}
