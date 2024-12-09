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
package jp.mosp.platform.system.constant;

/**
 * MosPプラットフォーム基本設定機能で用いる定数を宣言する。<br>
 */
public class PlatformSystemConst {
	
	/**
	 * 他クラスからのインスタンス化を防止する。<br>
	 */
	private PlatformSystemConst() {
		// 処理無し
	}
	
	
	/**
	 * ファイルパス(MosPプラットフォーム基本設定編集領域ヘッダJSP)。
	 */
	public static final String	PATH_SYSTEM_EDIT_HEADER_JSP	= "/jsp/platform/system/systemEditHeader.jsp";
	
	/**
	 * 検索条件区分(所属のみ)。<br>
	 * 所属マスタ画面等で用いる。<br>
	 */
	public static final String	SEARCH_SECTION_CODE			= "code";
	
	/**
	 * 検索条件区分(階層含む)。
	 * 所属マスタ画面等で用いる。<br>
	 */
	public static final String	SEARCH_SECTION_ROUTE		= "route";
	
	/**
	 * 所属経路区切文字。<br>
	 * DBに登録される経路の区切文字。<br>
	 */
	public static final String	SEPARATOR_CLASS_ROUTE		= ",";
	
}
