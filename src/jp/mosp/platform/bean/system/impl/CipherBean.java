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
package jp.mosp.platform.bean.system.impl;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.apache.commons.codec.binary.Base64;

import jp.mosp.framework.base.BaseBean;
import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.MospUtility;
import jp.mosp.platform.bean.system.CipherBeanInterface;

/**
 * 暗号化処理クラス。
 */
public class CipherBean extends BaseBean implements CipherBeanInterface {
	
	/**
	 * 区切文字(Basic認証情報)。<br>
	 */
	protected static final String STR_SEPARATOR_CREDENTIAL = ":";
	
	
	@Override
	public void initBean() {
		// 処理無し
	}
	
	/**
	 * Base64でエンコードしているだけで、暗号化はしていない。
	 */
	@Override
	public String encrypt(String source) throws MospException {
		// 暗号化対象文字列をバイト配列に変換
		byte[] sourceBytes = MospUtility.getBytes(source);
		// Base64でエンコーディングした文字列を取得
		return encode(sourceBytes);
	}
	
	/**
	 * Base64でデコードしているだけで、復号化はしていない。
	 */
	@Override
	public String decrypt(String source) throws MospException {
		// Base64でデコードした文字列を取得
		byte[] sourceBytes = decode(source);
		// 復号化した文字列を取得
		return MospUtility.newString(sourceBytes);
	}
	
	/**
	 * Base64でエンコードされた文字列を取得する。
	 * @param source 対象バイト配列
	 * @return Base64でエンコードされた文字列
	 * @throws MospException エンコードに失敗した場合
	 */
	protected String encode(byte[] source) throws MospException {
		try {
			// Base64でエンコード
			return Base64.encodeBase64String(source);
		} catch (Throwable t) {
			// エンコードに失敗した場合
			throw new MospException(t);
		}
	}
	
	/**
	 * Base64でデコードされたバイト配列を取得する。
	 * @param source 対象文字列
	 * @return Base64でデコードされたバイト配列
	 * @throws MospException デコードに失敗した場合
	 */
	protected byte[] decode(String source) throws MospException {
		try {
			// Base64でデコード
			return Base64.decodeBase64(source);
		} catch (Throwable t) {
			// デコードに失敗した場合
			throw new MospException(t);
		}
	}
	
	@Override
	public List<String> decodeBasicAuthHeader(String source) throws MospException {
		// 認証文字列リスト(平文)を準備
		List<String> list = new ArrayList<String>();
		// 認証文字列を半角スペースで分割
		String[] array = MospUtility.split(source, MospConst.STR_SB_SPACE);
		// 2つに分割できなかった場合
		if (array.length != 2) {
			// 空のリストを取得
			return Collections.emptyList();
		}
		// 
		// Basic認証情報をBase64でデコードした文字列を取得
		String decoded = MospUtility.newString(decode(array[1]));
		// Basic認証情報をBase64でデコードした文字列を「:」で分割
		String[] credentials = MospUtility.split(decoded, STR_SEPARATOR_CREDENTIAL);
		// 2つに分割できなかった場合
		if (credentials.length != 2) {
			// 空のリストを取得
			return Collections.emptyList();
		}
		// 認証文字列リスト(平文)に値を設定
		list.add(array[0]);
		list.addAll(MospUtility.asList(credentials));
		// 認証文字列毎に処理
		for (String value : list) {
			// 認証文字列が一つでも空白である場合
			if (MospUtility.isEmpty(value)) {
				// 空のリストを取得
				return Collections.emptyList();
			}
		}
		// 認証文字列リスト(平文)を取得
		return list;
	}
	
}
