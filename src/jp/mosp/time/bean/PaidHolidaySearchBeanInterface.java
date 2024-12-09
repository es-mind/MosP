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
/**
 * 
 */
package jp.mosp.time.bean;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.PaidHolidayDtoInterface;

/**
 * 有給休暇設定検索インターフェース。
 */
public interface PaidHolidaySearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 検索条件から有給休暇設定リストを取得する。<br><br>
	 * {@link #setActivateDate(Date)}等で設定された条件で、検索を行う。
	 * @return 有給休暇設定リスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<PaidHolidayDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param activateDate セットする 有効日。
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param paidHolidayCode セットする 有休コード
	 */
	void setPaidHolidayCode(String paidHolidayCode);
	
	/**
	 * @param paidHolidayName セットする 有休名称
	 */
	void setPaidHolidayName(String paidHolidayName);
	
	/**
	 * @param paidHolidayAbbr セットする 有休略称
	 */
	void setPaidHolidayAbbr(String paidHolidayAbbr);
	
	/**
	 * @param paidHolidayType セットする 付与区分
	 */
	void setPaidHolidayType(String paidHolidayType);
	
	/**
	 * @param inactivateFlag セットする 有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
	
}
