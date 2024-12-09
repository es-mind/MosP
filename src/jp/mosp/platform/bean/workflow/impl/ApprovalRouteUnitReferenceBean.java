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
package jp.mosp.platform.bean.workflow.impl;

import java.util.ArrayList;
import java.util.Date;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.ApprovalRouteReferenceBeanInterface;
import jp.mosp.platform.bean.workflow.ApprovalRouteUnitReferenceBeanInterface;
import jp.mosp.platform.dao.workflow.ApprovalRouteUnitDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteUnitDtoInterface;

/**
 * 承認ルートユニットマスタ参照クラス。<br>
 */
public class ApprovalRouteUnitReferenceBean extends PlatformBean implements ApprovalRouteUnitReferenceBeanInterface {
	
	/**
	 * 承認ルートユニットマスタDAO。<br>
	 */
	protected ApprovalRouteUnitDaoInterface			dao;
	
	/**
	 * 承認ルートマスタ参照クラス。<br>
	 */
	protected ApprovalRouteReferenceBeanInterface	approvalRouteReference;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ApprovalRouteUnitReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(ApprovalRouteUnitDaoInterface.class);
		// Beanを準備
		approvalRouteReference = createBeanInstance(ApprovalRouteReferenceBeanInterface.class);
	}
	
	@Override
	public ApprovalRouteUnitDtoInterface findForKey(String routeCode, Date activateDate, int routeStage)
			throws MospException {
		return dao.findForKey(routeCode, activateDate, routeStage);
		
	}
	
	@Override
	public ApprovalRouteUnitDtoInterface getApprovalRouteUnitInfo(String routeCode, Date targetDate, int routeStage)
			throws MospException {
		return dao.findForInfo(routeCode, targetDate, routeStage);
		
	}
	
	@Override
	public List<ApprovalRouteUnitDtoInterface> getApprovalRouteUnitList(String routeCode, Date targetDate)
			throws MospException {
		// 承認ルートマスタ取得
		ApprovalRouteDtoInterface routeDto = approvalRouteReference.getApprovalRouteInfo(routeCode, targetDate);
		if (routeDto == null) {
			return new ArrayList<ApprovalRouteUnitDtoInterface>();
		}
		return dao.findForRouteList(routeCode, routeDto.getActivateDate());
	}
	
	@Override
	public Set<String> getRouteSetForUnit(String unitCode, Date targetDate) throws MospException {
		// 承認ルートコード群を準備
		Set<String> set = new HashSet<String>();
		for (ApprovalRouteUnitDtoInterface dto : dao.findForApprovalUnit(unitCode, targetDate)) {
			set.add(dto.getRouteCode());
		}
		return set;
	}
	
}
