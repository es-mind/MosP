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
 * 承認ルートユニットマスタDTOインターフェース
 */
public interface ApprovalRouteUnitDtoInterface extends PlatformDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfaApprovalRouteUnitId();
	
	/**
	 * @param pfaApprovalRouteUnitId セットする レコード識別ID。
	 */
	void setPfaApprovalRouteUnitId(long pfaApprovalRouteUnitId);
	
	/**
	 * @return ルートコード。
	 */
	String getRouteCode();
	
	/**
	 * @param routeCode セットする ルートコード。
	 */
	void setRouteCode(String routeCode);
	
	/**
	 * @return 承認段階。
	 */
	int getApprovalStage();
	
	/**
	 * @param approvalStage セットする 承認段階。
	 */
	void setApprovalStage(int approvalStage);
	
	/**
	 * @return ユニットコード。
	 */
	String getUnitCode();
	
	/**
	 * @param unitCode セットする ユニットコード。
	 */
	void setUnitCode(String unitCode);
	
}
