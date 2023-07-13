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

import jp.mosp.framework.constant.MospConst;

/**
 * MosP設定情報(コマンド)。<br>
 */
public class CommandProperty implements BaseProperty {
	
	/**
	 * MosPコマンド。
	 */
	private String	command;
	
	/**
	 * アクションクラス。
	 */
	private String	actionClass;
	
	/**
	 * HTTPセッション要否。<br>
	 * <br>
	 * 設定値の種類及び意味は、下記の通り。<br>
	 * <ul><li>
	 * 設定無し(null)<br>
	 * セッションを取得する。<br>
	 * セッションタイムアウト等でセッションが取得できなかった場合は、例外を発行する。<br>
	 * MosPセッション保持情報の確認を行う。<br>
	 * </li><li>
	 * unnecessary<br>
	 * セッションを取得する。<br>
	 * 取得に失敗したときは、新規作成する。<br>
	 * MosPセッション保持情報の確認を行わない。<br>
	 * </li></ul>
	 */
	private String	needSession;
	
	/**
	 * 処理シーケンス要否。<br>
	 * <br>
	 * 設定値の種類及び意味は、下記の通り。<br>
	 * <ul><li>
	 * 設定無し(null)<br>
	 * 処理シーケンスの発行を行う。<br>
	 * </li><li>
	 * zeroAcceptable<br>
	 * 処理シーケンスの発行を行わない。<br>
	 * 処理シーケンス確認時に、保持処理シーケンス0でも通常処理とする。<br>
	 * 認証処理等で設定する。<br>
	 * </li><li>
	 * unnecessary<br>
	 * 処理シーケンスの発行を行わない。<br>
	 * ファイルダウンロード処理等で設定する。<br>
	 * </li><li>
	 * ignore<br>
	 * 処理シーケンスの発行を行わない。<br>
	 * 処理シーケンスの確認を行わない。<br>
	 * 連携機能等で設定する。<br>
	 * </li></ul>
	 */
	private String	needProcSeq;
	
	/**
	 * 許可メソッド。<br>
	 * <br>
	 * 設定無し(null)の場合は、POSTのみを受け付ける。<br>
	 * 設定がある場合(GET等)は、設定されているメソッドのみを受け付ける。<br>
	 * 許可メソッドは、カンマ区切で複数設定することができる。<br>
	 */
	private String	acceptMethod;
	
	
	/**
	 * MosPコマンド情報を生成する。<br>
	 * @param command      MosPコマンド
	 * @param actionClass  アクションクラス
	 * @param needSession  HTTPセッション要否
	 * @param needProcSeq  処理シーケンス要否
	 * @param acceptMethod 許可メソッド
	 */
	public CommandProperty(String command, String actionClass, String needSession, String needProcSeq,
			String acceptMethod) {
		this.command = command;
		this.actionClass = actionClass;
		this.needSession = needSession;
		this.needProcSeq = needProcSeq;
		this.acceptMethod = acceptMethod;
	}
	
	/**
	 * MosPコマンドを取得する。
	 * @return MosPコマンド
	 */
	public String getCommand() {
		return command;
	}
	
	/**
	 * アクションクラスを取得する。
	 * @return アクションクラス
	 */
	public String getActionClass() {
		return actionClass;
	}
	
	/**
	 * HTTPセッション要否を取得する。
	 * @return HTTPセッション要否
	 */
	public String getNeedSession() {
		return needSession;
	}
	
	/**
	 * 処理シーケンス発行要否を取得する。
	 * @return needProcSeq 処理シーケンス発行要否
	 */
	public String getNeedProcSeq() {
		return needProcSeq;
	}
	
	/**
	 * 許可メソッド配列を取得する。
	 * カンマで配列に分割する。<br>
	 * @return acceptMethod 許可メソッド配列
	 */
	public String[] getAcceptMethods() {
		// プロパティ確認
		if (acceptMethod == null || acceptMethod.isEmpty()) {
			return new String[0];
		}
		// カンマで分割
		return acceptMethod.split(MospConst.APP_PROPERTY_SEPARATOR);
	}
	
	@Override
	public String getKey() {
		return command;
	}
	
}
