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
package jp.mosp.platform.bean.workflow.impl;

import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.ApprovalRouteSearchBeanInterface;
import jp.mosp.platform.dao.workflow.ApprovalRouteDaoInterface;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;

/**
 * 承認ルートマスタ検索クラス。
 */
public class ApprovalRouteSearchBean extends PlatformBean implements ApprovalRouteSearchBeanInterface {
	
	/**
	 * 承認ルートマスタDAO
	 */
	protected ApprovalRouteDaoInterface	approvalRouteDao;
	
	/**
	 * 有効日。
	 */
	private Date						activateDate;
	
	/**
	 * 階層数。
	 */
	private String						approvalCount;
	
	/**
	 * ルートコード。
	 */
	private String						routeCode;
	
	/**
	 * ルート名称。
	 */
	private String						routeName;
	
	/**
	 * ユニットコード
	 */
	private String						unitCode;
	
	/**
	 * ユニット名称。
	 */
	private String						unitName;
	
	/**
	 * 有効無効フラグ。
	 */
	private String						inactivateFlag;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public ApprovalRouteSearchBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// 承認ルートマスタDAO取得
		approvalRouteDao = createDaoInstance(ApprovalRouteDaoInterface.class);
	}
	
	@Override
	public List<ApprovalRouteDtoInterface> getSearchList() throws MospException {
		// Mapに検索条件を設定
		Map<String, Object> param = approvalRouteDao.getParamsMap();
		param.put("activateDate", activateDate);
		param.put("approvalCount", approvalCount);
		param.put("inactivateFlag", inactivateFlag);
		param.put("routeCode", routeCode);
		param.put("routeName", routeName);
		param.put("unitCode", unitCode);
		param.put("unitName", unitName);
		// 検索
		return approvalRouteDao.findForSearch(param);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
		
	}
	
	@Override
	public void setInactivateFlag(String inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
		
	}
	
	@Override
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
		
	}
	
	@Override
	public void setRouteName(String routeName) {
		this.routeName = routeName;
		
	}
	
	@Override
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
		
	}
	
	@Override
	public void setUnitName(String unitName) {
		this.unitName = unitName;
		
	}
	
	@Override
	public void setApprovalCount(String approvalCount) {
		this.approvalCount = approvalCount;
		
	}
	
}
