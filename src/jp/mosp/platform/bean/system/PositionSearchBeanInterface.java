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
package jp.mosp.platform.bean.system;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.system.PositionDtoInterface;

/**
 * 職位マスタ検索インターフェース。
 */
public interface PositionSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * @return 有効日。
	 */
	Date getActivateDate();
	
	/**
	 * @return 職位コード。 
	 * 
	 */
	String getPositionCode();
	
	/**
	 * @return 職位名称。 
	 * 
	 */
	String getPositionName();
	
	/**
	 * @return 職位名称（略称）。 
	 * 
	 */
	String getPositionAbbr();
	
	/**
	 * @return 等級。 
	 * 
	 */
	String getPositionGrade();
	
	/**
	 * @return 号数。
	 */
	String getPositionLevel();
	
	/**
	 * @return 有効無効。
	 */
	String getInactivateFlag();
	
	/**
	 * @param activateDate セットする 有効日。
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param positionCode セットする 職位コード。
	 */
	void setPositionCode(String positionCode);
	
	/**
	 * @param positionName セットする 職位名称。
	 */
	void setPositionName(String positionName);
	
	/**
	 * @param positionAbbr セットする 職位名称（略称）。
	 */
	void setPositionAbbr(String positionAbbr);
	
	/**
	 * @param positionGrade セットする 等級。
	 */
	void setPositionGrade(String positionGrade);
	
	/**
	 * @param positionLevel セットする 号数。
	 */
	void setPositionLevel(String positionLevel);
	
	/**
	 * @param inactivateFlag セットする 有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から職位マスタリストを取得。
	 * </p>
	 * @param searchParams 職位マスタ検索条件
	 * @return 職位マスタリスト
	 * @throws MospException 例外処理が発生した場合。
	 */
	List<PositionDtoInterface> getSearchList(PositionSearchBeanInterface searchParams) throws MospException;
	
}
