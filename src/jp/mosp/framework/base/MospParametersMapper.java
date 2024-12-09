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

import java.lang.reflect.Field;
import java.util.Map;

import jp.mosp.framework.constant.MospConst;

/**
 * パラメータをマッピングする。<br>
 * <br>
 * リクエストパラメータ群をVOに設定する際に、用いる。<br>
 * java.lang.reflectパッケージを用いているため、扱いに注意すること。<br>
 */
public class MospParametersMapper {
	
	/**
	 * チェックボックスパラメータ接頭辞。
	 */
	protected static final String PREFIX_CHECK_BOX = "ckb";
	
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private MospParametersMapper() {
		// 処理無し
	}
	
	/**
	 * パラメータをマッピングする。<br>
	 * @param obj        マッピング先オブジェクト
	 * @param parameters パラメータ群
	 * @throws MospException パラメータがマッピングできなかった場合
	 */
	public static void mapParameters(Object obj, Map<String, String[]> parameters) throws MospException {
		try {
			// クラス取得
			Class<?> cls = obj.getClass();
			while (cls != null) {
				// フィールド群取得
				Field[] fields = cls.getDeclaredFields();
				// フィールド群確認
				if (fields == null) {
					continue;
				}
				// フィールド毎処理
				for (Field field : fields) {
					// フィールドアクセス設定
					field.setAccessible(true);
					// フィールド名取得
					String fieldName = field.getName();
					// フィールド型取得
					Class<?> type = field.getType();
					// フィールドと同名のパラメータを取得
					String[] values = parameters.get(fieldName);
					// フィールド型確認
					if (type.isAssignableFrom(String[].class)) {
						// マッピング
						if (values != null) {
							field.set(obj, parameters.get(fieldName));
						}
						// チェックボックスの場合(値が送信されない場合は空の配列を設定)
						if (values == null || values.length == 0) {
							if (fieldName.startsWith(PREFIX_CHECK_BOX)) {
								field.set(obj, new String[0]);
							}
						}
					} else if (type.isAssignableFrom(String.class)) {
						// マッピング
						if (values == null || values.length == 0) {
							// チェックボックスの場合(値が送信されない場合は0を設定)
							if (fieldName.startsWith(PREFIX_CHECK_BOX)) {
								field.set(obj, MospConst.CHECKBOX_OFF);
							}
						} else {
							field.set(obj, values[0]);
						}
					}
				}
				// スーパークラス取得
				cls = cls.getSuperclass();
			}
		} catch (SecurityException e) {
			throw new MospException(e);
		} catch (IllegalArgumentException e) {
			throw new MospException(e);
		} catch (IllegalAccessException e) {
			throw new MospException(e);
		}
	}
	
}
