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

import java.io.Serializable;
import java.util.Date;

import jp.mosp.framework.constant.MospConst;
import jp.mosp.framework.utils.CapsuleUtility;

/**
 * VOの基本機能を提供する。<br>
 * <br>
 * MosPフレームワークでは、HTTPリクエストによって送られたパラメータを
 * VOインスタンスに設定し、ビジネスロジックを実行した後、UIに表示したい
 * 内容をVOに設定してビューに処理を委譲することを想定している。<br>
 * つまり、UIとビジネスロジックとの橋渡しをするのがVOの役割となる。<br>
 * <br>
 * 各アプリケーションにおいて、当クラスを拡張してVOクラスを
 * 作成することで、MosPフレームワークを有効に利用できる。<br>
 * <br>
 * VOは、基本的にフィールド及びアクセサメソッドを有する値保持用の
 * クラスとして定義する。<br>
 * <br>
 * BaseVoは、MosPフレームワークにおいて共通で用いられるフィールド及び
 * そのアクセサメソッド等を実装する。<br>
 */
public class BaseVo implements Serializable {
	
	private static final long serialVersionUID = -5856596970807014781L;
	
	
	/**
	 * クラス名を取得する。<br>
	 * @return クラス名
	 */
	public String getClassName() {
		return getClass().getName();
	}
	
	/**
	 * 日付オブジェクトの複製を取得する。
	 * @param date 対象日付オブジェクト
	 * @return 複製日付オブジェクト
	 */
	protected Date getDateClone(Date date) {
		return CapsuleUtility.getDateClone(date);
	}
	
	/**
	 * int配列の複製を取得する。
	 * @param array 対象int配列
	 * @return 複製int配列
	 */
	protected int[] getIntArrayClone(int[] array) {
		return CapsuleUtility.getIntArrayClone(array);
	}
	
	/**
	 * Integer配列の複製を取得する。
	 * @param array 対象Integer配列
	 * @return 複製Integer配列
	 */
	protected Integer[] getIntegerArrayClone(Integer[] array) {
		return CapsuleUtility.getIntegerArrayClone(array);
	}
	
	/**
	 * long配列の複製を取得する。
	 * @param array 対象long配列
	 * @return 複製long配列
	 */
	protected long[] getLongArrayClone(long[] array) {
		return CapsuleUtility.getLongArrayClone(array);
	}
	
	/**
	 * byte配列の複製を取得する。
	 * @param array 対象byte配列
	 * @return 複製byte配列
	 */
	protected byte[] getByteArrayClone(byte[] array) {
		return CapsuleUtility.getByteArrayClone(array);
	}
	
	/**
	 * boolean配列の複製を取得する。
	 * @param array 対象boolean配列
	 * @return 複製boolean配列
	 */
	protected boolean[] getBooleanArrayClone(boolean[] array) {
		return CapsuleUtility.getBooleanArrayClone(array);
	}
	
	/**
	 * BaseDtoInterface配列の複製を取得する。
	 * @param array 対象BaseDtoInterface配列
	 * @return 複製BaseDtoInterface配列
	 */
	protected BaseDtoInterface[] getDtoArrayClone(BaseDtoInterface[] array) {
		return CapsuleUtility.getDtoArrayClone(array);
	}
	
	/**
	 * 日付列配列の複製を取得する。
	 * @param array 対象日付列配列
	 * @return 複製日付列配列
	 */
	protected Date[] getDateArrayClone(Date[] array) {
		return CapsuleUtility.getDateArrayClone(array);
	}
	
	/**
	 * 文字列配列の複製を取得する。
	 * @param array 対象文字列配列
	 * @return 複製文字列配列
	 */
	protected String[] getStringArrayClone(String[] array) {
		return CapsuleUtility.getStringArrayClone(array);
	}
	
	/**
	 * 文字列配列の複製を取得する。
	 * @param array 対象文字列配列
	 * @return 複製文字列配列
	 */
	protected String[][] getStringArrayClone(String[][] array) {
		return CapsuleUtility.getStringArrayClone(array);
	}
	
	/**
	 * 文字列配列の複製を取得する。
	 * @param array 対象文字列配列
	 * @return 複製文字列配列
	 */
	protected String[][][] getStringArrayClone(String[][][] array) {
		return CapsuleUtility.getStringArrayClone(array);
	}
	
	/**
	 * 文字列配列中の文字列を取得する。<br>
	 * @param array 文字列配列
	 * @param idx   取得文字列インデックス
	 * @return 文字列配列中の文字列
	 */
	protected String getStringFromArray(String[] array, int idx) {
		// 文字列配列が空であるか長さが十分でない場合
		if (array == null || array.length <= idx) {
			// 空文字列を取得
			return MospConst.STR_EMPTY;
		}
		// 文字列配列中の文字列を取得
		return array[idx];
	}
	
}
