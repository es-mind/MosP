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
/**
 * 
 */
package jp.mosp.platform.dto.workflow.impl;

import java.util.Date;

import jp.mosp.framework.base.BaseDto;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;

/**
 * ワークフローDTO。
 */
public class PftWorkflowDto extends BaseDto implements WorkflowDtoInterface {
	
	private static final long	serialVersionUID	= 3876178267162071624L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pftWorkflowId;
	
	/**
	 * ワークフロー番号。
	 */
	private long				workflow;
	
	/**
	 * 段階。
	 */
	private int					workflowStage;
	
	/**
	 * 状況。
	 */
	private String				workflowStatus;
	
	/**
	 * 申請者個人ID。
	 */
	private String				personalId;
	
	/**
	 * ワークフロー対象日。
	 */
	private Date				workflowDate;
	
	/**
	 * ルートコード。
	 */
	private String				routeCode;
	
	/**
	 * 機能コード。
	 */
	private String				functionCode;
	
	/**
	 * 承認者個人ID。
	 */
	private String				approverId;
	
	
	@Override
	public String getFunctionCode() {
		return functionCode;
	}
	
	@Override
	public String getRouteCode() {
		return routeCode;
	}
	
	@Override
	public long getWorkflow() {
		return workflow;
	}
	
	@Override
	public long getPftWorkflowId() {
		return pftWorkflowId;
	}
	
	@Override
	public int getWorkflowStage() {
		return workflowStage;
	}
	
	@Override
	public String getWorkflowStatus() {
		return workflowStatus;
	}
	
	@Override
	public String getApproverId() {
		return approverId;
	}
	
	@Override
	public void setFunctionCode(String functionCode) {
		this.functionCode = functionCode;
	}
	
	@Override
	public void setRouteCode(String routeCode) {
		this.routeCode = routeCode;
	}
	
	@Override
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
	}
	
	@Override
	public void setPftWorkflowId(long pftWorkflowId) {
		this.pftWorkflowId = pftWorkflowId;
	}
	
	@Override
	public void setWorkflowStage(int workflowStage) {
		this.workflowStage = workflowStage;
	}
	
	@Override
	public void setWorkflowStatus(String workflowStatus) {
		this.workflowStatus = workflowStatus;
	}
	
	@Override
	public void setApproverId(String approverId) {
		this.approverId = approverId;
	}
	
	@Override
	public Date getWorkflowDate() {
		return getDateClone(workflowDate);
	}
	
	@Override
	public void setWorkflowDate(Date workflowDate) {
		this.workflowDate = getDateClone(workflowDate);
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
}
