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
import jp.mosp.platform.dto.workflow.ApprovalUnitDtoInterface;

/**
 * 承認ユニットマスタDTO。
 */
public class PfmApprovalUnitDto extends BaseDto implements ApprovalUnitDtoInterface {
	
	private static final long	serialVersionUID	= -5977571941379207710L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmApprovalUnitId;
	/**
	 * ユニットコード。
	 */
	private String				unitCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * ユニット名称。
	 */
	private String				unitName;
	/**
	 * ユニット区分。
	 */
	private String				unitType;
	/**
	 * 承認者個人ID。
	 */
	private String				approverPersonalId;
	/**
	 * 承認者所属コード。
	 */
	private String				approverSectionCode;
	/**
	 * 承認者職位コード。
	 */
	private String				approverPositionCode;
	/**
	 * 承認者職位等級範囲。
	 */
	private String				approverPositionGrade;
	/**
	 * 複数決済。
	 */
	private int					routeStage;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getPfmApprovalUnitId() {
		return pfmApprovalUnitId;
	}
	
	@Override
	public void setPfmApprovalUnitId(long pfmApprovalUnitId) {
		this.pfmApprovalUnitId = pfmApprovalUnitId;
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
	public Date getActivateDate() {
		return getDateClone(activateDate);
	}
	
	@Override
	public void setActivateDate(Date activateDate) {
		this.activateDate = getDateClone(activateDate);
	}
	
	@Override
	public String getUnitName() {
		return unitName;
	}
	
	@Override
	public void setUnitName(String unitName) {
		this.unitName = unitName;
	}
	
	@Override
	public String getUnitType() {
		return unitType;
	}
	
	@Override
	public void setUnitType(String unitType) {
		this.unitType = unitType;
	}
	
	@Override
	public String getApproverPersonalId() {
		return approverPersonalId;
	}
	
	@Override
	public void setApproverPersonalId(String approverPersonalId) {
		this.approverPersonalId = approverPersonalId;
	}
	
	@Override
	public String getApproverSectionCode() {
		return approverSectionCode;
	}
	
	@Override
	public void setApproverSectionCode(String approverSectionCode) {
		this.approverSectionCode = approverSectionCode;
	}
	
	@Override
	public String getApproverPositionCode() {
		return approverPositionCode;
	}
	
	@Override
	public void setApproverPositionCode(String approverPositionCode) {
		this.approverPositionCode = approverPositionCode;
	}
	
	@Override
	public String getApproverPositionGrade() {
		return approverPositionGrade;
	}
	
	@Override
	public void setApproverPositionGrade(String approverPositionGrade) {
		this.approverPositionGrade = approverPositionGrade;
	}
	
	@Override
	public int getRouteStage() {
		return routeStage;
	}
	
	@Override
	public void setRouteStage(int routeStage) {
		this.routeStage = routeStage;
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
