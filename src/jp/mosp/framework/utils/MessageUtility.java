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

import java.util.List;

import jp.mosp.framework.base.MospParams;
import jp.mosp.framework.constant.MospConst;

/**
 * メッセージに関するユーティリティクラス。<br>
 * <br>
 * フレームワークにおいてサーバ側プログラムで作成されるメッセージは、
 * 全てこのクラスを通じて作成される(予定)。<br>
 * <br>
 */
public class MessageUtility {
	
	/**
	 * メッセージコード(ログアウト)。<br>
	 * ログアウトしました。<br>
	 */
	protected static final String	MSG_I_LOGOUT			= "FWI0001";
	
	/**
	 * メッセージコード(セッションタイムアウト)。<br>
	 * セッションがタイムアウトしました。<br>
	 */
	public static final String		MSG_W_SESSION_TIMEOUT	= "FWW0001";
	
	/**
	 * メッセージコード(更新処理失敗)。<br>
	 * DBへの更新処理に失敗しました。<br>
	 */
	protected static final String	MSG_E_DB_UPDATE			= "FWE9222";
	
	/**
	 * メッセージコード(削除処理失敗)。<br>
	 * DBへの削除処理に失敗しました。<br>
	 */
	protected static final String	MSG_E_DB_DELETE			= "FWE9223";
	
	/**
	 * メッセージコード(メール送信失敗)。<br>
	 * メールの送信に失敗しました。メールサーバの設定を確認してください。<br>
	 */
	protected static final String	MSG_E_SEND_MAIL_FAILED	= "FWE9251";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MessageUtility() {
		// 処理無し
	}
	
	/**
	 * ログアウトしました。<br>
	 * @param mospParams   MosP処理情報
	 */
	public static void addMessageLogout(MospParams mospParams) {
		MessageUtility.addMessage(mospParams, MSG_I_LOGOUT);
	}
	
	/**
	 * セッションがタイムアウトしました。(FWW0001)<br>
	 * @param mospParams   MosP処理情報
	 */
	public static void addErrorSessionTimeout(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_W_SESSION_TIMEOUT);
	}
	
	/**
	 * DBへの更新処理に失敗しました。(FWE9222)<br>
	 * @param mospParams   MosP処理情報
	 */
	public static void addErrorDbUpdate(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_E_DB_UPDATE);
	}
	
	/**
	 * DBへの削除処理に失敗しました。(FWE9223)<br>
	 * @param mospParams   MosP処理情報
	 */
	public static void addErrorDbDelete(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_E_DB_DELETE);
	}
	
	/**
	 * メールの送信に失敗しました。メールサーバの設定を確認してください。(FWE9251)<br>
	 * @param mospParams MosP処理情報
	 */
	public static void addErrorSendMailFailed(MospParams mospParams) {
		MessageUtility.addErrorMessage(mospParams, MSG_E_SEND_MAIL_FAILED);
	}
	
	/**
	 * 行番号が付加されたフィールド名を取得する。<br>
	 * 行インデックスがnullでない場合、エラーメッセージに行番号が加えられる。<br>
	 * @param mospParams MosP処理情報
	 * @param fieldName 対象フィールド名
	 * @param row       対象行インデックス
	 * @return 行番号が付加されたフィールド名
	 */
	public static String getRowedFieldName(MospParams mospParams, String fieldName, Integer row) {
		// 対象行インデックスが存在しない場合
		if (MospUtility.isEmpty(row)) {
			// 対象フィールド名を取得
			return fieldName;
		}
		// 行番号が付加されたフィールド名を取得を準備
		StringBuilder sb = new StringBuilder();
		sb.append(NameUtility.row(mospParams));
		sb.append(row + 1);
		sb.append(NameUtility.colon(mospParams));
		sb.append(fieldName);
		// 行番号が付加されたフィールド名を取得
		return sb.toString();
	}
	
	/**
	 * MosP処理情報にメッセージを追加する。<br>
	 * @param mospParams   MosP処理情報
	 * @param isError      エラーメッセージフラグ(true：エラーメッセージ、false：そうでない)
	 * @param key          メッセージ設定キー
	 * @param row          行番号
	 * @param replacements 置換文字列
	 */
	protected static void addMessage(MospParams mospParams, boolean isError, String key, Integer row,
			String... replacements) {
		// メッセージリストを準備
		List<String> messageList = mospParams.getMessageList();
		// MosP処理情報からーメッセージを取得し作成
		String message = mospParams.getMessage(key, replacements);
		// エラーメッセージである場合
		if (isError) {
			// エラーメッセージリストを取得
			messageList = mospParams.getErrorMessageList();
			// MosP処理情報からエラーメッセージを取得し作成
			message = mospParams.getCodedMessage(key, replacements);
		}
		// 行番号(nullの場合は空白)を取得
		StringBuilder sb = new StringBuilder(getRowedFieldName(mospParams, MospConst.STR_EMPTY, row));
		// メッセージに行番号を付加
		message = sb.append(message).toString();
		// メッセージリストに含まれていない場合
		if (messageList.contains(message) == false) {
			// メッセージリストに追加
			messageList.add(message);
		}
	}
	
	/**
	 * MosP処理情報にメッセージを追加する。<br>
	 * @param mospParams   MosP処理情報
	 * @param key          メッセージ設定キー
	 * @param row          行番号
	 * @param replacements 置換文字列
	 */
	public static void addMessage(MospParams mospParams, String key, Integer row, String... replacements) {
		// MosP処理情報にメッセージを追加
		addMessage(mospParams, false, key, row, replacements);
	}
	
	/**
	 * MosP処理情報にメッセージを追加する。<br>
	 * @param mospParams   MosP処理情報
	 * @param key          メッセージ設定キー
	 * @param replacements 置換文字列
	 */
	public static void addMessage(MospParams mospParams, String key, String... replacements) {
		// MosP処理情報にエラーメッセージを追加
		addMessage(mospParams, key, null, replacements);
	}
	
	/**
	 * MosP処理情報にエラーメッセージを追加する。<br>
	 * @param mospParams   MosP処理情報
	 * @param key          メッセージ設定キー
	 * @param row          行番号
	 * @param replacements 置換文字列
	 */
	public static void addErrorMessage(MospParams mospParams, String key, Integer row, String... replacements) {
		// MosP処理情報にエラーメッセージを追加
		addMessage(mospParams, true, key, row, replacements);
	}
	
	/**
	 * MosP処理情報にエラーメッセージを追加する。<br>
	 * @param mospParams   MosP処理情報
	 * @param key          メッセージ設定キー
	 * @param replacements 置換文字列
	 */
	public static void addErrorMessage(MospParams mospParams, String key, String... replacements) {
		// MosP処理情報にエラーメッセージを追加
		addErrorMessage(mospParams, key, null, replacements);
	}
	
}
