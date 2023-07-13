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
import jp.mosp.platform.dto.workflow.RouteApplicationReferenceDtoInterface;

/**
 * ルート適用参照DTOクラス。
 */
public class RouteApplicationReferenceDto extends BaseDto implements RouteApplicationReferenceDtoInterface {
	
	private static final long	serialVersionUID	= -5110600845994952787L;
	
	/**
	 * 設定適用の有効日。
	 */
	private Date				activateDate;
	
	/**
	 * 個人ID
	 */
	private String				personalId;
	
	/**
	 * 社員コード。
	 */
	private String				employeeCode;
	
	/**
	 * 氏名。
	 */
	private String				employeeName;
	
	/**
	 * ワークフロー区分
	 */
	private int					workflowType;
	
	/**
	 * ルート適用コード。
	 */
	private String				routeApplicationCode;
	
	/**
	 * ルート適用名称。
	 */
	private String				routeApplicationName;
	
	/**
	 * ルートコード。
	 */
	private String				routeCode;
	
	/**
	 * ルート名称。
	 */
	private String				routeName;
	
	/**
	 * 承認者社員コード。
	 */
	private String				approverCode;
	
	/**
	 * 承認者氏名
	 */
	private String				approverName;
	
	/**
	 * 承認階層。
	 */
	private String				routeStage;
	
	/**
	 * 一次承認者。
	 */
	private String				firstApprovalName;
	
	/**
	 * 最終承認者。
	 */
	private String				endApprovalName;
	
	private String				firstName;
	private String				lastName;
	private String				sectionCode;
	private String				workPlaceCode;
	private String				positionCode;
	private String				employmentContractCode;
	
	
	@Override
	public void setFirstName(String firstName) {
		this.firstName = firstName;
	}
	
	@Override
	public String getFirstName() {
		return firstName;
	}
	
	@Override
	public void setLastName(String lastName) {
		this.lastName = lastName;
	}
	
	@Override
	public String getLastName() {
		return lastName;
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
	public String getWorkPlaceCode() {
		return workPlaceCode;
	}
	
	@Override
	public void setWorkPlaceCode(String workPlaceCode) {
		this.workPlaceCode = workPlaceCode;
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
	public String getEmployeeCode() {
		return employeeCode;
	}
	
	@Override
	public void setEmployeeCode(String employeeCode) {
		this.employeeCode = employeeCode;
	}
	
	@Override
	public String getEmployeeName() {
		return employeeName;
	}
	
	@Override
	public void setEmployeeName(String employeeName) {
		this.employeeName = employeeName;
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
	public String getRouteName() {
		return routeName;
	}
	
	@Override
	public void setRouteName(String routeName) {
		this.routeName = routeName;
	}
	
	@Override
	public String getApproverCode() {
		return approverCode;
	}
	
	@Override
	public void setApproverCode(String approverCode) {
		this.approverCode = approverCode;
	}
	
	@Override
	public String getApproverName() {
		return approverName;
	}
	
	@Override
	public void setApproverName(String approverName) {
		this.approverName = approverName;
	}
	
	@Override
	public String getRouteStage() {
		return routeStage;
	}
	
	@Override
	public void setRouteStage(String routeStage) {
		this.routeStage = routeStage;
	}
	
	@Override
	public String getFirstApprovalName() {
		return firstApprovalName;
	}
	
	@Override
	public void setFirstApprovalName(String firstApprovalName) {
		this.firstApprovalName = firstApprovalName;
	}
	
	@Override
	public String getEndApprovalName() {
		return endApprovalName;
	}
	
	@Override
	public void setEndApprovalName(String endApprovalName) {
		this.endApprovalName = endApprovalName;
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
	public int getInactivateFlag() {
		// 処理なし
		return 0;
	}
	
	@Override
	public void setInactivateFlag(int inactivateFlag) {
		// 処理なし
		
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
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
	public String getRouteApplicationCode() {
		return routeApplicationCode;
	}
	
	@Override
	public String getRouteApplicationName() {
		return routeApplicationName;
	}
	
	@Override
	public void setRouteApplicationCode(String routeApplicationCode) {
		this.routeApplicationCode = routeApplicationCode;
	}
	
	@Override
	public void setRouteApplicationName(String routeApplicationName) {
		this.routeApplicationName = routeApplicationName;
	}
	
	@Override
	public int getWorkflowType() {
		return workflowType;
	}
	
	@Override
	public void setWorkflowType(int workflowType) {
		this.workflowType = workflowType;
	}
	
}
