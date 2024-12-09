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
package jp.mosp.platform.dto.workflow.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.workflow.ApprovalRouteUnitDtoInterface;

/**
 * 承認ルートユニットマスタDTO
 */
public class PfaApprovalRouteUnitDto extends BaseDto implements ApprovalRouteUnitDtoInterface {
	
	private static final long	serialVersionUID	= -9153931962806472139L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfaApprovalRouteUnitId;
	/**
	 * ルートコード。
	 */
	private String				routeCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * 承認段階。
	 */
	private int					approvalStage;
	/**
	 * ユニットコード。
	 */
	private String				unitCode;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getPfaApprovalRouteUnitId() {
		return pfaApprovalRouteUnitId;
	}
	
	@Override
	public void setPfaApprovalRouteUnitId(long pfaApprovalRouteUnitId) {
		this.pfaApprovalRouteUnitId = pfaApprovalRouteUnitId;
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
	public int getApprovalStage() {
		return approvalStage;
	}
	
	@Override
	public void setApprovalStage(int approvalStage) {
		this.approvalStage = approvalStage;
	}
	
	@Override
	public String getUnitCode() {
		return unitCode;
	}
	
	@Override
	public void setUnitCode(String unitCode) {
		this.unitCode = unitCode;
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
