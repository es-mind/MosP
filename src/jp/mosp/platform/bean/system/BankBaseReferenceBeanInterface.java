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

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.BankBaseDtoInterface;

/**
 * 銀行マスタ参照インターフェース。<br>
 */
public interface BankBaseReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索値から合致するプルダウン用配列を取得する。<br>
	 * 表示内容は、コード＋名称。<br>
	 * @param value 検索値
	 * @param needBlank     空白行要否(true：空白行要、false：空白行不要)
	 * @return プルダウン用配列
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	String[][] getSelectArray(String value, boolean needBlank) throws MospException;
	
	/**
	 * コードから銀行情報を取得する。
	 * @param code コード
	 * @return 銀行情報
	 * @throws MospException インスタンスの取得に失敗した場合
	 */
	BankBaseDtoInterface findForKey(String code) throws MospException;
	
}
