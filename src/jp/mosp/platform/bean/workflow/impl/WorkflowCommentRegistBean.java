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
package jp.mosp.platform.bean.workflow.impl;

import java.util.List;

import jp.mosp.framework.base.MospException;
import jp.mosp.platform.base.PlatformBean;
import jp.mosp.platform.bean.workflow.WorkflowCommentRegistBeanInterface;
import jp.mosp.platform.dao.workflow.WorkflowCommentDaoInterface;
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;
import jp.mosp.platform.dto.workflow.impl.PftWorkflowCommentDto;

/**
 * ワークフローコメント登録クラス。
 */
public class WorkflowCommentRegistBean extends PlatformBean implements WorkflowCommentRegistBeanInterface {
	
	/**
	 * ワークフローコメントDAO。<br>
	 */
	protected WorkflowCommentDaoInterface dao;
	
	
	/**
	 * {@link PlatformBean#PlatformBean()}を実行する。<br>
	 */
	public WorkflowCommentRegistBean() {
		super();
	}
	
	@Override
	public void initBean() throws MospException {
		// DAOを準備
		dao = createDaoInstance(WorkflowCommentDaoInterface.class);
	}
	
	@Override
	public WorkflowCommentDtoInterface getInitDto() {
		return new PftWorkflowCommentDto();
	}
	
	@Override
	public void insert(WorkflowCommentDtoInterface dto) throws MospException {
		// DTO妥当性確認
		validate(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// 新規登録情報の検証
		validateInsert(dto);
		if (mospParams.hasErrorMessage()) {
			return;
		}
		// レコード識別ID最大値をインクリメントしてDTOに設定
		dto.setPftWorkflowCommentId(dao.nextRecordId());
		// 登録処理
		dao.insert(dto);
	}
	
	/**
	 * 新規登録時の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	protected void validateInsert(WorkflowCommentDtoInterface dto) {
		// 処理無し
	}
	
	/**
	 * 妥当性の確認処理を行う。<br>
	 * @param dto 対象DTO
	 */
	protected void validate(WorkflowCommentDtoInterface dto) {
		// 処理無し
	}
	
	@Override
	public void addComment(WorkflowDtoInterface dto, String personalId, String workflowComment) throws MospException {
		if (dto != null) {
			WorkflowCommentDtoInterface commentDto = getInitDto();
			commentDto.setPersonalId(personalId);
			commentDto.setWorkflow(dto.getWorkflow());
			commentDto.setWorkflowStage(dto.getWorkflowStage());
			commentDto.setWorkflowStatus(dto.getWorkflowStatus());
			commentDto.setWorkflowComment(workflowComment);
			commentDto.setWorkflowDate(getSystemTimeAndSecond());
			// 登録
			insert(commentDto);
		}
	}
	
	@Override
	public void deleteList(List<WorkflowCommentDtoInterface> list) throws MospException {
		for (WorkflowCommentDtoInterface dto : list) {
			// 論理削除
			logicalDelete(dao, dto.getPftWorkflowCommentId());
		}
	}
}
