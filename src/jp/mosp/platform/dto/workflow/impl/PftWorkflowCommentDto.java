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
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;

/**
 * ワークフローコメントDTO
 */
public class PftWorkflowCommentDto extends BaseDto implements WorkflowCommentDtoInterface {
	
	/**
	 * 
	 */
	private static final long	serialVersionUID	= -3357546392795256516L;
	
	/**
	 * レコード識別ID。
	 */
	private long				pftWorkflowCommentId;
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
	 * 個人ID。
	 */
	private String				personalId;
	/**
	 * コメント。
	 */
	private String				workflowComment;
	
	private Date				workflowDate;
	
	
	@Override
	public String getWorkflowComment() {
		return workflowComment;
	}
	
	@Override
	public String getPersonalId() {
		return personalId;
	}
	
	@Override
	public long getPftWorkflowCommentId() {
		return pftWorkflowCommentId;
	}
	
	@Override
	public long getWorkflow() {
		return workflow;
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
	public Date getWorkflowDate() {
		return getDateClone(workflowDate);
	}
	
	@Override
	public void setWorkflowComment(String workflowComment) {
		this.workflowComment = workflowComment;
	}
	
	@Override
	public void setPersonalId(String personalId) {
		this.personalId = personalId;
	}
	
	@Override
	public void setPftWorkflowCommentId(long pftWorkflowCommentId) {
		this.pftWorkflowCommentId = pftWorkflowCommentId;
	}
	
	@Override
	public void setWorkflow(long workflow) {
		this.workflow = workflow;
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
	public void setWorkflowDate(Date workflowDate) {
		this.workflowDate = getDateClone(workflowDate);
	}
	
}
