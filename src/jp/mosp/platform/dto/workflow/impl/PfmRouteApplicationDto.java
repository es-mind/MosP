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
import jp.mosp.platform.dto.workflow.RouteApplicationDtoInterface;

/**
 * ルート適用マスタDTO。
 */
public class PfmRouteApplicationDto extends BaseDto implements RouteApplicationDtoInterface {
	
	private static final long	serialVersionUID	= -3974572312294411612L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pfmRouteApplicationId;
	/**
	 * ルート適用コード。
	 */
	private String				routeApplicationCode;
	/**
	 * 有効日。
	 */
	private Date				activateDate;
	/**
	 * ルート適用名称。
	 */
	private String				routeApplicationName;
	/**
	 * ルートコード。
	 */
	private String				routeCode;
	/**
	 * フロー区分。
	 */
	private int					workflowType;
	/**
	 * ルート適用区分。
	 */
	private int					routeApplicationType;
	/**
	 * 勤務地コード。
	 */
	private String				workPlaceCode;
	/**
	 * 雇用契約コード。
	 */
	private String				employmentContractCode;
	/**
	 * 所属コード。
	 */
	private String				sectionCode;
	/**
	 * 職位コード。
	 */
	private String				positionCode;
	/**
	 * 個人ID。
	 */
	private String				personalIds;
	/**
	 * 無効フラグ。
	 */
	private int					inactivateFlag;
	
	
	@Override
	public long getPfmRouteApplicationId() {
		return pfmRouteApplicationId;
	}
	
	@Override
	public void setPfmRouteApplicationId(long pfmRouteApplicationId) {
		this.pfmRouteApplicationId = pfmRouteApplicationId;
	}
	
	@Override
	public String getRouteApplicationCode() {
		return routeApplicationCode;
	}
	
	@Override
	public void setRouteApplicationCode(String routeApplicationCode) {
		this.routeApplicationCode = routeApplicationCode;
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
	public String getRouteApplicationName() {
		return routeApplicationName;
	}
	
	@Override
	public void setRouteApplicationName(String routeApplicationName) {
		this.routeApplicationName = routeApplicationName;
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
	public int getWorkflowType() {
		return workflowType;
	}
	
	@Override
	public void setWorkflowType(int workflowType) {
		this.workflowType = workflowType;
	}
	
	@Override
	public int getRouteApplicationType() {
		return routeApplicationType;
	}
	
	@Override
	public void setRouteApplicationType(int routeApplicationType) {
		this.routeApplicationType = routeApplicationType;
	}
	
	@Override
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
	}
	
	@Override
	public String getEmploymentContractCode() {
		return employmentContractCode;
	}
	
	@Override
	public void setEmploymentContractCode(String employmentContractCode) {
		this.employmentContractCode = employmentContractCode;
	}
	
	@Override
	public String getSectionCode() {
		return sectionCode;
	}
	
	@Override
	public void setSectionCode(String sectionCode) {
		this.sectionCode = sectionCode;
	}
	
	@Override
	public String getPositionCode() {
		return positionCode;
	}
	
	@Override
	public void setPositionCode(String positionCode) {
		this.positionCode = positionCode;
	}
	
	@Override
	public String getPersonalIds() {
		return personalIds;
	}
	
	@Override
	public void setPersonalIds(String personalIds) {
		this.personalIds = personalIds;
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
