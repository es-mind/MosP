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
package jp.mosp.platform.bean.workflow.impl;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowCommentReferenceBeanInterface;
import jp.mosp.platform.dao.workflow.WorkflowCommentDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;

/**
 * ワークフローコメント参照クラス。
 */
public class WorkflowCommentReferenceBean extends PlatformBean implements WorkflowCommentReferenceBeanInterface {
	
	/**
	 * ワークフローコメントDAO。<br>
	 */
	protected WorkflowCommentDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkflowCommentReferenceBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(WorkflowCommentDaoInterface.class);
	}
	
	@Override
	public WorkflowCommentDtoInterface getLatestWorkflowCommentInfo(long workflow) throws MospException {
		return dao.findForLatestCommentInfo(workflow);
	}
	
	@Override
	public List<WorkflowCommentDtoInterface> getWorkflowCommentHistory(long workflow) throws MospException {
		return dao.findForHistory(workflow);
	}
	
	@Override
	public List<WorkflowCommentDtoInterface> getWorkflowCommentList(long workflow) throws MospException {
		return dao.findForList(workflow);
	}
	
}
