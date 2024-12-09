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
package jp.mosp.platform.bean.workflow;

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;

/**
 * 承認ユニットマスタ検索インターフェース。
 */
public interface ApprovalUnitSearchBeanInterface extends BaseBeanInterface {
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から承認ユニットマスタリストを取得。
	 * </p>
	 * @return 承認ユニットマスタリスト
	 * @throws MospException インスタンスの取得、SQLの作成及び実行に失敗した場合
	 */
	List<ApprovalUnitDtoInterface> getSearchList() throws MospException;
	
	/**
	 * @param activateDate セットする 有効日。
	 */
	void setActivateDate(Date activateDate);
	
	/**
	 * @param unitCode セットする ユニットコード。
	 */
	void setUnitCode(String unitCode);
	
	/**
	 * @param unitName セットする ユニット名称。
	 */
	void setUnitName(String unitName);
	
	/**
	 * @param unitType セットする ユニット区分。
	 */
	void setUnitType(String unitType);
	
	/**
	 * @param sectionName セットする 承認者所属名称。
	 */
	void setSectionName(String sectionName);
	
	/**
	 * @param positionName セットする 承認者職位名称。
	 */
	void setPositionName(String positionName);
	
	/**
	 * @param employeeCode セットする 承認者社員コード。
	 */
	void setEmployeeCode(String employeeCode);
	
	/**
	 * @param approver セットする 承認者氏名。
	 */
	void setApprover(String approver);
	
	/**
	 * @param inactivateFlag セットする 有効無効フラグ。
	 */
	void setInactivateFlag(String inactivateFlag);
	
}
