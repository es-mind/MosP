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
package jp.mosp.platform.bean.human;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.human.AddressDtoInterface;

/**
 * 住所情報参照インターフェース。<br>
 */
public interface AddressReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 個人IDと有効日で個人住所情報を取得する。<br>
	 * <br>
	 * 個人IDと有効日が合致する個人住所情報が無い場合、nullを返す<br>
	 * <br>
	 * @param personalId   個人ID
	 * @param activateDate 有効日
	 * @return 住所情報
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	AddressDtoInterface getPersonalAddress(String personalId, Date activateDate) throws MospException;
	
	/**
	 * 対象個人IDの個人住所情報リスト(有効日昇順)を取得する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @return 住所情報リスト(有効日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AddressDtoInterface> getPersonalAddressList(String personalId) throws MospException;
	
	/**
	 * 対象個人IDの住民票住所情報リスト(有効日昇順)を取得する。<br>
	 * <br>
	 * @param personalId 個人ID
	 * @return 住所情報リスト(有効日昇順)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<AddressDtoInterface> getLegalAddressList(String personalId) throws MospException;
	
}
