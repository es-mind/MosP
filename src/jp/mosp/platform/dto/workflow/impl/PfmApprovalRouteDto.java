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
package jp.mosp.platform.dto.workflow.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.workflow.ApprovalRouteDtoInterface;

/**
 * 承認ルートマスタDTO
 */
public class PfmApprovalRouteDto extends BaseDto implements ApprovalRouteDtoInterface {
	
	private static final long	serialVersionUID	= -6403270134514223248L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmApprovalRouteId;
	/**
	 * ルートコード。
	 */
	private String				routeCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * ルート名称。
	 */
	private String				routeName;
	/**
	 * 承認階層。
	 */
	private int					approvalCount;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getPfmApprovalRouteId() {
		return pfmApprovalRouteId;
	}
	
	@Override
	public void setPfmApprovalRouteId(long pfmApprovalRouteId) {
		this.pfmApprovalRouteId = pfmApprovalRouteId;
	}
	
	@Override
	public String getRouteCode() {
		return routeCode;
	}
	
	@Override
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	
	@Override
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public String getRouteName() {
		return routeName;
	}
	
	@Override
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
	@Override
	public int getApprovalCount() {
		return approvalCount;
	}
	
	@Override
	public void setApprovalCount(int approvalCount) {
		this.approvalCount = approvalCount;
	}
	
	@Override
	public int getInactivateFlag() {
		return inactivateFlag;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		this.inactivateFlag = inactivateFlag;
	}
	
}
