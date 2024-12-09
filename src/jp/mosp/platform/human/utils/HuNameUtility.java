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
package jp.mosp.platform.human.utils;

import jp.mosp.framework.base.MospParams;

/**
 * 名称に関するユーティリティクラス。<br>
 * 
 * 人事管理において作成される名称は、
 * 全てこのクラスを通じて作成される。<br>
 * <br>
 */
public class HuNameUtility {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private HuNameUtility() {
		// 処理無し
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ファイル名
	 */
	public static String fileName(MospParams mospParams) {
		return mospParams.getName("HuFileName");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return ファイル備考
	 */
	public static String fileRemarks(MospParams mospParams) {
		return mospParams.getName("HuFileRemarks");
	}
	
	/**
	 * @param mospParams MosP処理情報
	 * @return 登録済バイナリファイル
	 */
	public static String registeredFile(MospParams mospParams) {
		return mospParams.getName("HuRegisteredFile");
	}
	
}
