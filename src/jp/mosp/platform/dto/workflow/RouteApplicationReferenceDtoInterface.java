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
import jp.mosp.platform.dto.base.EmployeeCodeDtoInterface;
import jp.mosp.platform.dto.base.EmployeeNameDtoInterface;
import jp.mosp.platform.dto.base.EmploymentContractCodeDtoInterface;
import jp.mosp.platform.dto.base.PersonalIdDtoInterface;
import jp.mosp.platform.dto.base.PositionCodeDtoInterface;
import jp.mosp.platform.dto.base.SectionCodeDtoInterface;
import jp.mosp.platform.dto.base.WorkPlaceCodeDtoInterface;
import jp.mosp.platform.dto.base.WorkflowTypeDtoInterface;

/**
 * ルート適用参照DTOインターフェース。
 */
public interface RouteApplicationReferenceDtoInterface extends PersonalIdDtoInterface, EmployeeCodeDtoInterface,
		EmployeeNameDtoInterface, SectionCodeDtoInterface, WorkPlaceCodeDtoInterface, PositionCodeDtoInterface,
		PlatformDtoInterface, EmploymentContractCodeDtoInterface, WorkflowTypeDtoInterface {
	
	/**
	 * @return 社員名
	 */
	String getEmployeeName();
	
	/**
	 * @param employeeName セットする 社員名。
	 */
	void setEmployeeName(String employeeName);
	
	/**
	 * @return ルート適用コード。
	 */
	String getRouteApplicationCode();
	
	/**
	 * @param routeApplicationCode セットする ルート適用コード。
	 */
	void setRouteApplicationCode(String routeApplicationCode);
	
	/**
	 * @return ルート適用名称。
	 */
	String getRouteApplicationName();
	
	/**
	 * @param routeApplicationName セットする ルート適用名称。
	 */
	void setRouteApplicationName(String routeApplicationName);
	
	/**
	 * @return ルートコード。
	 */
	String getRouteCode();
	
	/**
	 * @param routeCode セットする ルートコード。
	 */
	void setRouteCode(String routeCode);
	
	/**
	 * @return ルート名称。
	 */
	String getRouteName();
	
	/**
	 * @param routeName セットする ルート名称。
	 */
	void setRouteName(String routeName);
	
	/**
	 * @return 承認者社員コード。
	 */
	String getApproverCode();
	
	/**
	 * @param approverCode セットする 承認者社員コード。
	 */
	void setApproverCode(String approverCode);
	
	/**
	 * @return 。
	 */
	String getApproverName();
	
	/**
	 * @param approverName セットする 承認階層。
	 */
	void setApproverName(String approverName);
	
	/**
	 * @return 承認階層。
	 */
	String getRouteStage();
	
	/**
	 * @param routeStage セットする 承認階層。
	 */
	void setRouteStage(String routeStage);
	
	/**
	 * @return 一次承認者。
	 */
	String getFirstApprovalName();
	
	/**
	 * @param firstApprovalName セットする 一次承認者。
	 */
	void setFirstApprovalName(String firstApprovalName);
	
	/**
	 * @return 最終承認者。
	 */
	String getEndApprovalName();
	
	/**
	 * @param endApprovalName セットする 一次承認者。
	 */
	void setEndApprovalName(String endApprovalName);
	
}
