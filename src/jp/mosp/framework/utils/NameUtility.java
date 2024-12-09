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

import jp.mosp.framework.base.MospParams;

/**
 * 名称に関するユーティリティクラス。<br>
 * 
 * フレームワークにおいて作成される名称は、
 * 全てこのクラスを通じて作成される。<br>
 * <br>
 */
public class NameUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private NameUtility() {
		// 処理無し
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 行
	 */
	public static String row(MospParams mospParams) {
		return mospParams.getName("Row");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ：
	 */
	public static String colon(MospParams mospParams) {
		return mospParams.getName("Colon");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ●
	 */
	public static String selected(MospParams mospParams) {
		return mospParams.getName("Selected");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return へ
	 */
	public static String to(MospParams mospParams) {
		return mospParams.getName("To");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return レ
	 */
	public static String checked(MospParams mospParams) {
		return mospParams.getName("Checked");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 他
	 */
	public static String other(MospParams mospParams) {
		return mospParams.getName("Other");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ～
	 */
	public static String wave(MospParams mospParams) {
		return mospParams.getName("Wave");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 件
	 */
	public static String count(MospParams mospParams) {
		return mospParams.getName("Count");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param count 件数
	 * @return count件
	 */
	public static String count(MospParams mospParams, int count) {
		StringBuilder sb = new StringBuilder();
		sb.append(count);
		sb.append(mospParams.getName("Count"));
		return sb.toString();
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @param enclosed   カッコ内の文字列
	 * @return 【enclosed】
	 */
	public static String cornerParentheses(MospParams mospParams, String enclosed) {
		StringBuilder sb = new StringBuilder();
		sb.append(mospParams.getName("FrontWithCornerParentheses"));
		sb.append(enclosed);
		sb.append(mospParams.getName("BackWithCornerParentheses"));
		return sb.toString();
	}
	
	/**
	 * 名称を取得する。<br>
	 * @param mospParams MosP処理情報
	 * @param key キー
	 * @return 名称
	 */
	public static String getName(MospParams mospParams, String key) {
		return mospParams.getName(key);
	}
	
}
