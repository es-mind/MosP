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
import jp.mosp.platform.dto.system.EmploymentContractDtoInterface;

/**
 * 雇用契約マスタ検索インターフェース。
 */
public interface EmploymentContractSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件から雇用契約マスタリストを取得する。<br><br>
	 * {@link #setActivateDate(Date)}等で設定された条件で、検索を行う。
	 * @return 雇用契約マスタリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<EmploymentContractDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param activateDate セットする 有効日。
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param employmentCode セットする 雇用契約コード。
	 */
	void setEmploymentCode(String employmentCode);
	
	/**
	 * @param employmentName セットする 雇用契約名称。
	 */
	void setEmploymentName(String employmentName);
	
	/**
	 * @param employmentAbbr セットする 雇契(略称)。
	 */
	void setEmploymentAbbr(String employmentAbbr);
	
	/**
	 * @param inactivateFlag セットする 有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
	
}
