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

import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

import jp.mosp.framework.utils.DateUtility;
import jp.mosp.framework.utils.ValidateUtility;

/**
 * 特定のクラスのメソッドを実行するクラス。<br>
 */
public class MospMethodInvoker {
	
	/**
	 * セパレータ(クラス名+メソッド名)。<br>
	 */
	protected static final String SEPARATOR_CLASS_METHOD = ".";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MospMethodInvoker() {
		// 処理無し
	}
	
	/**
	 * メソッドを実行する。<br>
	 * <br>
	 * 対象となるメソッドは、次のルールに則り作成する必要がある。<br>
	 * ・同一クラスに同一名称のメソッドが無いこと。<br>
	 * ・引数に配列(或いは可変長変数)は一つ(最後の引数)のみ。<br>
	 * ・引数に配列を用いる場合は文字列の配列のみ。<br>
	 * <br>
	 * MosP処理情報は、引数として用いない場合はnullでも構わない。<br>
	 * <br>
	 * @param name       クラス名+メソッド名
	 * @param mospParams MosP処理情報
	 * @param args       引数
	 * @throws MospException メソッドの実行に失敗した場合
	 */
	public static void invokeStaticMethod(String name, MospParams mospParams, String... args) throws MospException {
		// メソッドを取得
		Method method = getMethod(name);
		// 引数オブジェクト配列(実行用)を取得
		Object[] argObjects = getArgObjects(method, mospParams, args);
		
		// メソッドを実行
		try {
			method.invoke(null, argObjects);
		} catch (Exception e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * メソッドを取得する。<br>
	 * @param name クラス名+メソッド名
	 * @return メソッド
	 * @throws MospException クラスが見つからない場合
	 */
	public static Method getMethod(String name) throws MospException {
		try {
			// クラス名とメソッド名に分割
			String className = name.substring(0, name.lastIndexOf(SEPARATOR_CLASS_METHOD));
			String methodName = name.substring(name.lastIndexOf(SEPARATOR_CLASS_METHOD) + 1);
			// 対象クラスのメソッド毎に処理
			for (Method method : Class.forName(className).getMethods()) {
				// メソッド名が同じである場合
				if (method.getName().equals(methodName)) {
					// メソッドを取得
					return method;
				}
			}
			return null;
		} catch (ClassNotFoundException e) {
			throw new MospException(e);
		}
	}
	
	/**
	 * 引数オブジェクト配列(実行用)を取得する。<br>
	 * <br>
	 * @param method     メソッド
	 * @param mospParams MosP処理情報
	 * @param args       引数配列
	 * @return 引数配列
	 * @throws MospException クラスが見つからない場合
	 */
	protected static Object[] getArgObjects(Method method, MospParams mospParams, String... args) throws MospException {
		// 引数オブジェクトリストを準備
		List<Object> objects = new ArrayList<Object>();
		// 引数配列用インデックス準備
		int idx = 0;
		// メソッドの引数型毎に処理
		for (Class<?> type : method.getParameterTypes()) {
			// MosP処理情報の場合
			if (type.getName().equals(MospParams.class.getName())) {
				// 引数オブジェクトリストにMosP処理情報を設定
				objects.add(mospParams);
				continue;
			}
			// 文字列の場合
			if (type.getName().equals(String.class.getName())) {
				// 引数オブジェクトリストに引数を設定
				objects.add(args[idx++]);
				continue;
			}
			// 数値の場合
			if (type.getName().equals(int.class.getName())) {
				int value = 0;
				if (args[idx] == null) {
					objects.add(args[idx]);
					idx++;
					continue;
				}
				if (ValidateUtility.chkRegex("\\d*", args[idx])) {
					value = Integer.parseInt(args[idx]);
				}
				objects.add(value);
				idx++;
				continue;
			}
			// 日付の場合
			if (type.getName().equals(Date.class.getName())) {
				if (args[idx] != null) {
					objects.add(DateUtility.getDate(args[idx]));
				} else {
					objects.add(null);
				}
				idx++;
				continue;
			}
			// 数値の場合
			if (type.getName().equals(Integer.class.getName())) {
				Integer value = 0;
				
				if (args[idx] == null) {
					objects.add(args[idx]);
					idx++;
					continue;
				}
				
				if (ValidateUtility.chkRegex("\\d*", args[idx])) {
					value = Integer.parseInt(args[idx]);
				}
				
				objects.add(value);
				idx++;
				continue;
			}
			
			// オブジェクトの場合
			if (type.getName().equals(Object.class.getName())) {
				// 引数オブジェクトリストに引数を設定
				objects.add(args[idx++]);
				continue;
			}
			// 文字列配列の場合
			if (type.getName().equals(String[].class.getName())) {
				// 引数オブジェクトリストに残りの引数を設定
				objects.add(Arrays.copyOfRange(args, idx, args.length));
				break;
			}
			
			// 上記以外の場合
			objects.add(null);
		}
		// 引数オブジェクト配列(実行用)を取得
		return objects.toArray();
	}
	
}
