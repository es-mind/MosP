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
package jp.mosp.platform.dto.workflow;

import jp.mosp.platform.base.PlatformDtoInterface;

/**
 * 承認ユニットマスタDTOインターフェース。
 */
public interface ApprovalUnitDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmApprovalUnitId();
	
	/**
	 * @param pfmApprovalUnitId セットする レコード識別ID。
	 */
	void setPfmApprovalUnitId(long pfmApprovalUnitId);
	
	/**
	 * @return ユニットコード。
	 */
	String getUnitCode();
	
	/**
	 * @param unitCode セットする ユニットコード。
	 */
	void setUnitCode(String unitCode);
	
	/**
	 * @return 承認者個人ID。
	 */
	String getApproverPersonalId();
	
	/**
	 * @param approverPersonalId セットする 承認者個人ID。
	 */
	void setApproverPersonalId(String approverPersonalId);
	
	/**
	 * @return 承認者所属コード。
	 */
	String getApproverSectionCode();
	
	/**
	 * @param approverSectionCode セットする 承認者所属コード。
	 */
	void setApproverSectionCode(String approverSectionCode);
	
	/**
	 * @return 承認者職位コード。
	 */
	String getApproverPositionCode();
	
	/**
	 * @param approverPositionCode セットする 承認者職位コード。
	 */
	void setApproverPositionCode(String approverPositionCode);
	
	/**
	 * @return 承認者職位等級範囲。
	 */
	String getApproverPositionGrade();
	
	/**
	 * @param approverPositionGrade セットする 承認者職位等級範囲。
	 */
	void setApproverPositionGrade(String approverPositionGrade);
	
	/**
	 * @return ユニット名称。
	 */
	String getUnitName();
	
	/**
	 * @param unitName セットする ユニット名称。
	 */
	void setUnitName(String unitName);
	
	/**
	 * @return ユニット区分。
	 */
	String getUnitType();
	
	/**
	 * @param unitType セットする ユニット区分。
	 */
	void setUnitType(String unitType);
	
	/**
	 * @return 複数決済。
	 */
	int getRouteStage();
	
	/**
	 * @param routeStage セットする 複数決済。
	 */
	void setRouteStage(int routeStage);
	
}
