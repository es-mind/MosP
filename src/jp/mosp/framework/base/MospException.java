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
package jp.mosp.framework.base;

import jp.mosp.framework.constant.ExceptionConst;

/**
 * MosPが扱う処理で発生した例外を扱う。<br><br>
 */
public class MospException extends Exception {
	
	private static final long	serialVersionUID	= -6517271438333843982L;
	
	/**
	 * MosP例外ID。
	 */
	private String				exceptionId;
	
	/**
	 * MosP例外メッセージ置換文字列。
	 */
	private String[]			replaceStrings;
	
	
	/**
	 * MosP例外IDを設定する。<br>
	 * @param exceptionId MosP例外ID
	 */
	public MospException(String exceptionId) {
		super();
		this.exceptionId = exceptionId;
	}
	
	/**
	 * MosP例外ID及びMosP例外メッセージ置換文字列を設定する。<br>
	 * @param exceptionId    MosP例外ID
	 * @param replaceStrings MosP例外メッセージ置換文字列
	 */
	public MospException(String exceptionId, String[] replaceStrings) {
		super();
		this.exceptionId = exceptionId;
		if (replaceStrings != null) {
			this.replaceStrings = replaceStrings.clone();
		}
	}
	
	/**
	 * MosP例外ID及びMosP例外メッセージ置換文字列を設定する。<br>
	 * @param exceptionId   MosP例外ID
	 * @param replaceString MosP例外メッセージ置換文字列
	 */
	public MospException(String exceptionId, String replaceString) {
		super();
		this.exceptionId = exceptionId;
		if (replaceString != null) {
			replaceStrings = new String[1];
			replaceStrings[0] = replaceString;
		}
	}
	
	/**
	 * 実行時例外からMosP例外を作成する。<br>
	 * @param throwable 例外インスタンス
	 */
	public MospException(Throwable throwable) {
		super(throwable);
		// 例外コード設定
		exceptionId = ExceptionConst.EX_RUNTIME;
	}
	
	/**
	 * 実行時例外からMosP例外を作成する。<br>
	 * MosP例外ID及びMosP例外メッセージ置換文字列を設定する。<br>
	 * @param throwable     例外インスタンス
	 * @param exceptionId   MosP例外ID
	 * @param replaceString MosP例外メッセージ置換文字列
	 */
	public MospException(Throwable throwable, String exceptionId, String replaceString) {
		super(throwable);
		this.exceptionId = exceptionId;
		if (replaceString != null) {
			replaceStrings = new String[1];
			replaceStrings[0] = replaceString;
		}
	}
	
	/**
	 * MosP例外IDを取得する。
	 * @return MosP例外ID
	 */
	public String getExceptionId() {
		return exceptionId;
	}
	
	/**
	 * MosP例外メッセージ置換文字列を取得する。
	 * @return MosP例外メッセージ置換文字列
	 */
	public String[] getReplaceStrings() {
		if (replaceStrings == null) {
			return new String[0];
		}
		return replaceStrings.clone();
	}
	
}
