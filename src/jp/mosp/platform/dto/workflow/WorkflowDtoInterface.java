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
package jp.mosp.platform.dto.workflow;

import java.util.Date;

import jp.mosp.framework.base.BaseDtoInterface;

/**
 * ワークフローDTOインターフェース
 */
public interface WorkflowDtoInterface extends BaseDtoInterface {
	
	/**
	 * @return レコード識別ID。
	 */
	long getPftWorkflowId();
	
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
	 * @return 申請者個人ID
	 */
	String getPersonalId();
	
	/**
	 * @return ワークフロー対象日
	 */
	Date getWorkflowDate();
	
	/**
	 * @return ルートコード。
	 */
	String getRouteCode();
	
	/**
	 * @return 機能コード。
	 */
	String getFunctionCode();
	
	/**
	 * @return 承認者個人ID。
	 */
	String getApproverId();
	
	/**
	 * @param pftWorkflowId セットする レコード識別ID。
	 */
	void setPftWorkflowId(long pftWorkflowId);
	
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
	 * @param personalId セットする申請者個人ID
	 */
	void setPersonalId(String personalId);
	
	/**
	 * @param workflowDate セットするワークフロー対象日
	 */
	void setWorkflowDate(Date workflowDate);
	
	/**
	 * @param routeCode セットする ルートコード。
	 */
	void setRouteCode(String routeCode);
	
	/**
	 * @param functionCode セットする 機能コード。
	 */
	void setFunctionCode(String functionCode);
	
	/**
	 * @param approverId セットする 承認者個人ID。
	 */
	void setApproverId(String approverId);
	
}
