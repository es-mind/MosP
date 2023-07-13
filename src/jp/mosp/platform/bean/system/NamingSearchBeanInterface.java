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
package jp.mosp.platform.bean.system;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.NamingDtoInterface;

/**
 * 名称区分マスタ検索インターフェース。
 */
public interface NamingSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件から名称区分マスタリストを取得する。<br><br>
	 * {@link #setActivateDate(Date)}等で設定された条件で、検索を行う。
	 * @return 名称区分マスタリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<NamingDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param activateDate セットする 有効日。
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param namingType セットする 名称区分。
	 */
	void setNamingType(String namingType);
	
	/**
	 * @param namingItemCode セットする 名称項目コード。
	 */
	void setNamingItemCode(String namingItemCode);
	
	/**
	 * @param namingItemName セットする 名称項目名称。
	 */
	void setNamingItemName(String namingItemName);
	
	/**
	 * @param namingItemAbbr セットする 名称項目略称。
	 */
	void setNamingItemAbbr(String namingItemAbbr);
	
	/**
	 * @param inactivateFlag セットする 有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
}
