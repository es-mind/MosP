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
package jp.mosp.framework.utils;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import jp.mosp.framework.base.MospException;

/**
 * 有用なメソッドを提供する。<br><br>
 */
public class SeUtility {
	
	/**
	 * メッセージダイジェストアルゴリズム。<br>
	 */
	protected static final String	MESSAGE_DIGEST_ALGORITHM	= "MD5";
	
	/**
	 * 暗号化処理時に追加する数。
	 */
	protected static final int		PLUS_BYTES					= 256;
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private SeUtility() {
		// 処理無し
	}
	
	/**
	 * 暗号化。<br>
	 * @param value 暗号対象文字列
	 * @return 暗号処理後文字列
	 * @throws MospException 暗号アルゴリズムが使用可能でない場合
	 */
	public static String encrypt(String value) throws MospException {
		try {
			StringBuffer digest = new StringBuffer();
			MessageDigest md = MessageDigest.getInstance(MESSAGE_DIGEST_ALGORITHM);
			byte[] bytes = MospUtility.getBytes(value);
			md.update(bytes);
			bytes = md.digest();
			for (byte c : bytes) {
				int b = c;
				if (b < 0) {
					b += PLUS_BYTES;
				}
				if (b < 16) {
					digest.append(0);
				}
				digest.append(Integer.toString(b, 16));
			}
			return digest.toString();
		} catch (NoSuchAlgorithmException e) {
			throw new MospException(e);
		}
	}
}
