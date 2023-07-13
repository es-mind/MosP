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

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * ワークフローコメントDTOインターフェース
 */
public interface WorkflowCommentDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPftWorkflowCommentId();
	
	/**
	 * @return ワークフロー番号。
	 */
	long getWorkflow();
	
	/**
	 * @return 段階。
	 */
	int getWorkflowStage();
	
	/**
	 * @return 状況。
	 */
	String getWorkflowStatus();
	
	/**
	 * @return 個人ID。
	 */
	String getPersonalId();
	
	/**
	 * @return コメント。
	 */
	String getWorkflowComment();
	
	/**
	 * @return 日時。
	 */
	Date getWorkflowDate();
	
	/**
	 * @param pftWorkflowCommentId セットする レコード識別ID。
	 */
	void setPftWorkflowCommentId(long pftWorkflowCommentId);
	
	/**
	 * @param workflow セットする ワークフロー番号。
	 */
	void setWorkflow(long workflow);
	
	/**
	 * @param workflowStage セットする 段階。
	 */
	void setWorkflowStage(int workflowStage);
	
	/**
	 * @param workflowStatus セットする 状況。
	 */
	void setWorkflowStatus(String workflowStatus);
	
	/**
	 * @param personalId セットする 個人ID。
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param workflowComment セットする コメント。
	 */
	void setWorkflowComment(String workflowComment);
	
	/**
	 * @param workflowDate セットする 日時。
	 */
	void setWorkflowDate(Date workflowDate);
	
}
