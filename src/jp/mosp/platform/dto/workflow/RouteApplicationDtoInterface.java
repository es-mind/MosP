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
package jp.mosp.platform.dto.workflow;

import jp.mosp.platform.base.PlatformDtoInterface;
import jp.mosp.platform.dto.base.ApplicationMasterDtoInterface;
import jp.mosp.platform.dto.base.WorkflowTypeDtoInterface;

/**
 * ルート適用マスタDTOインターフェース。
 */
public interface RouteApplicationDtoInterface
		extends PlatformDtoInterface, ApplicationMasterDtoInterface, WorkflowTypeDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPfmRouteApplicationId();
	
	/**
	 * @param pfmRouteApplicationId セットする レコード識別ID。
	 */
	void setPfmRouteApplicationId(long pfmRouteApplicationId);
	
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
	 * @return ルート適用区分。
	 */
	int getRouteApplicationType();
	
	/**
	 * @param routeApplicationType セットする ルート適用区分。
	 */
	void setRouteApplicationType(int routeApplicationType);
	
}
