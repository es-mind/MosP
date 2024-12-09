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

import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;

import jp.mosp.framework.constant.ExceptionConst;
import jp.mosp.framework.constant.MospConst;

import org.apache.commons.fileupload.FileItem;
import org.apache.commons.fileupload.FileUploadException;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;

/**
 * HTTPサーブレットリクエストを解析する。<br>
 * <br>
 */
public class MospRequestParser {
	
	/**
	 * リクエスト文字エンコーディング。
	 */
	private static String encoding;
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MospRequestParser() {
		// 処理無し
	}
	
	/**
	 * リクエストパラメータを解析し、MosP処理情報に設定する。<br>
	 * @param request    リクエスト
	 * @param mospParams MosP処理情報
	 * @throws MospException MultiPartフォームデータの解析に失敗した場合
	 */
	public static void parseRequestParams(HttpServletRequest request, MospParams mospParams) throws MospException {
		// MultiPart確認
		if (ServletFileUpload.isMultipartContent(request) == false) {
			// パラメータマップを設定
			mospParams.setRequestParamsMap(request.getParameterMap());
			return;
		}
		// リクエスト文字エンコーディング設定
		encoding = mospParams.getApplicationProperty(MospConst.APP_CHARACTER_ENCODING);
		// MultiPartフィールドリスト取得
		List<FileItem> multipartFieldList = getMultiPartFieldList(request);
		// パラメータマップ及びファイルマップ準備
		Map<String, String[]> parameterMap = new HashMap<String, String[]>();
		Map<String, InputStream> fileMap = new HashMap<String, InputStream>();
		// MultiPartフィールドリスト解析
		for (FileItem item : multipartFieldList) {
			// フォームデータ確認
			if (item.isFormField()) {
				// MultiPartフィールド設定
				putMultiPartField(item, parameterMap);
			} else {
				// MultiPartファイル設定
				putMultiPartFile(item, parameterMap, fileMap);
			}
		}
		// パラメータマップ及びファイルマップをMosP処理情報に設定
		mospParams.setRequestParamsMap(parameterMap);
		mospParams.setRequestFilesMap(fileMap);
	}
	
	/**
	 * MultiPartフィールドリストを取得する。<br>
	 * @param request リクエスト
	 * @return MultiPartフィールドリスト
	 * @throws MospException MultiPartフォームデータの解析に失敗した場合
	 */
	protected static List<FileItem> getMultiPartFieldList(HttpServletRequest request) throws MospException {
		// MultiPartフィールドリスト取得準備
		ServletFileUpload sfu = new ServletFileUpload(new DiskFileItemFactory());
		// MultiPartフィールドリスト取得
		List<?> multipartFieldList = null;
		try {
			multipartFieldList = sfu.parseRequest(request);
		} catch (FileUploadException e) {
			throw new MospException(e, ExceptionConst.EX_FAIL_PARSE_MULTI, null);
		}
		// MultiPartフィールドリスト再設定準備
		List<FileItem> list = new ArrayList<FileItem>();
		// MultiPartフィールドリスト再設定
		for (Object obj : multipartFieldList) {
			list.add((FileItem)obj);
		}
		return list;
	}
	
	/**
	 * MultiPartファイルフィールドを設定する。<br>
	 * ファイルフィールドは、{@link InputStream}として設定され、
	 * ここではファイルの文字コードの指定は行わない。<br>
	 * パラメータマップには、ファイル名が設定される。<br>
	 * @param item         フィールドアイテム
	 * @param parameterMap パラメータマップ
	 * @param fileMap パラメータファイルマップ
	 * @throws MospException MultiPartフォームデータの解析に失敗した場合
	 */
	protected static void putMultiPartFile(FileItem item, Map<String, String[]> parameterMap,
			Map<String, InputStream> fileMap) throws MospException {
		try {
			// パラメータ設定
			fileMap.put(item.getFieldName(), item.getInputStream());
			// ファイル名取得及び設定 ※ブラウザによってはフルパスを取得してしまうことを考慮
			String[] fileName = { (new File(item.getName())).getName() };
			parameterMap.put(item.getFieldName(), fileName);
		} catch (IOException e) {
			throw new MospException(e, ExceptionConst.EX_FAIL_PARSE_MULTI, null);
		}
	}
	
	/**
	 * MultiPartフィールドを設定する。<br>
	 * @param item フィールドアイテム
	 * @param map  パラメータマップ
	 * @throws MospException 文字エンコーディングがサポートされていない場合
	 */
	protected static void putMultiPartField(FileItem item, Map<String, String[]> map) throws MospException {
		// パラメータ名取得
		String name = item.getFieldName();
		// パラメータ値取得
		String value = null;
		try {
			value = item.getString(encoding);
		} catch (UnsupportedEncodingException e) {
			throw new MospException(e, ExceptionConst.EX_INVALID_ENCODING, encoding);
		}
		// 設定パラメータ取得
		String[] params = map.get(name);
		// パラメータ存在確認
		if (params == null) {
			// パラメータ設定
			String[] values = { value };
			map.put(name, values);
		} else {
			// パラメータ追加
			map.put(name, addParams(params, value));
		}
	}
	
	/**
	 * パラメータ配列に値を追加する。
	 * @param params パラメータ配列
	 * @param value  値
	 * @return パラメータ配列
	 */
	protected static String[] addParams(String[] params, String value) {
		String[] values = new String[params.length + 1];
		int idx = 0;
		for (String param : params) {
			values[idx++] = param;
		}
		values[idx] = value;
		return values;
	}
	
}
