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

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Locale;

import jp.mosp.framework.base.MospException;
import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;

/**
 * MosP上でバイナリ情報を扱う上で有用なメソッドを提供する。<br>
 * <br>
 */
public class BinaryUtility {
	
	/**
	 * 拡張子セパレータ。<br>
	 */
	public static final String	STR_EXTENSION_SEPARATOR	= ".";
	
	/**
	 * 拡張子(gif)。<br>
	 */
	public static final String	STR_EXTENSION_GIF		= "gif";
	
	/**
	 * 拡張子(png)。<br>
	 */
	public static final String	STR_EXTENSION_PNG		= "png";
	
	/**
	 * 拡張子(jpeg)。<br>
	 */
	public static final String	STR_EXTENSION_JPEG		= "jpeg";
	
	/**
	 * 拡張子(jpg)。<br>
	 */
	public static final String	STR_EXTENSION_JPG		= "jpg";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private BinaryUtility() {
		// 処理無し
	}
	
	/**
	 * バイナリデータをバイトの配列に変換し取得する。
	 * @param requestedFile リクエストされたファイル 
	 * @return バイトの配列に変換されたバイナリデータ
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	public static byte[] getBinaryData(InputStream requestedFile) throws MospException {
		ByteArrayOutputStream bout = new ByteArrayOutputStream();
		byte[] buffer = new byte[MospConst.PROCESS_BYTES];
		// バイト配列に変換し、出力
		try {
			while (true) {
				int len = requestedFile.read(buffer);
				if (len < 0) {
					break;
				}
				bout.write(buffer, 0, len);
			}
		} catch (IOException e) {
			throw new MospException(e, ExceptionConst.EX_FAIL_OUTPUT_FILE, null);
		}
		return bout.toByteArray();
	}
	
	/**
	 * ファイル名から拡張子を取得する。<br>
	 * ファイル名の最後「.」以降の文字列を取得する。<br>
	 * 拡張子は、小文字に変換される。
	 * 取得に失敗した場合は、nullを返す。<br>
	 * @param fileName ファイル名
	 * @return 拡張子
	 */
	public static String getExtension(String fileName) {
		if (fileName == null) {
			return null;
		}
		int index = fileName.lastIndexOf(STR_EXTENSION_SEPARATOR);
		if (index == -1) {
			return null;
		}
		String extension = fileName.substring(++index);
		return extension.toLowerCase(Locale.ENGLISH);
	}
	
	/**
	 * ファイル名の拡張子がgifであるかを確認する。<br>
	 * @param fileName ファイル名
	 * @return 確認結果(true：gifである、false：gifでない)
	 */
	public static boolean isExtensionGif(String fileName) {
		String extension = getExtension(fileName);
		return extension != null && extension.equals(STR_EXTENSION_GIF);
	}
	
	/**
	 * ファイル名の拡張子がpngであるかを確認する。<br>
	 * @param fileName ファイル名
	 * @return 確認結果(true：pngである、false：pngでない)
	 */
	public static boolean isExtensionPng(String fileName) {
		String extension = getExtension(fileName);
		return extension != null && extension.equals(STR_EXTENSION_PNG);
	}
	
	/**
	 * ファイル名の拡張子がjpg/jpegであるかを確認する。<br>
	 * @param fileName ファイル名
	 * @return 確認結果(true：jpg/jpegである、false：jpg/jpegでない)
	 */
	public static boolean isExtensionJpg(String fileName) {
		String extension = getExtension(fileName);
		if (extension == null) {
			return false;
		}
		return extension.equals(STR_EXTENSION_JPG) || extension.equals(STR_EXTENSION_JPEG);
	}
	
}
