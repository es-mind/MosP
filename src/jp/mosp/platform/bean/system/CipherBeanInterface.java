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
package jp.mosp.platform.bean.system;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;

/**
 * 暗号化処理インターフェース。
 */
public interface CipherBeanInterface extends BaseBeanInterface {
	
	/**
	 * 暗号化した文字列を取得する。
	 * @param source 暗号化対象文字列
	 * @return 暗号化した文字列
	 * @throws MospException 暗号化に失敗した場合
	 */
	String encrypt(String source) throws MospException;
	
	/**
	 * 暗号化文字列を元の文字列に復元する
	 * @param source 復号化対象文字列
	 * @return 復号化した文字列
	 * @throws MospException 復号化に失敗した場合
	 */
	String decrypt(String source) throws MospException;
	
	/**
	 * 認証文字列(Basic認証)を認証文字列リスト(平文)として取得する。<br>
	 * 認証文字列が正しくない場合、空のリストを返す。<br>
	 * <br>
	 * 認証文字列リスト(平文)の内容は、次の通り。<br>
	 * 1.認証の種類(Basic)<br>
	 * 2.ユーザID<br>
	 * 3.パスワード<br>
	 * <br>
	 * @param source 認証文字列(ヘッダ：Authorization)
	 * @return 認証文字列リスト(平文)
	 * @throws MospException 認証文字列(Basic認証)の解析に失敗した場合
	 */
	public List<String> decodeBasicAuthHeader(String source) throws MospException;
	
}
