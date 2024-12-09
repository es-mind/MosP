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
/**
 * 
 */
package jp.mosp.platform.bean.workflow;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;
import jp.mosp.platform.dto.workflow.WorkflowDtoInterface;

/**
 * ワークフローコメント登録インターフェース。<br>
 */
public interface WorkflowCommentRegistBeanInterface extends BaseBeanInterface {
	
	/**
	 * 登録用DTOを取得する。<br>
	 * @return 初期DTO
	 */
	WorkflowCommentDtoInterface getInitDto();
	
	/**
	 * 新規登録を行う。<br>
	 * @param dto 対象DTO
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void insert(WorkflowCommentDtoInterface dto) throws MospException;
	
	/**
	 * コメント登録を行う。<br>
	 * @param dto ワークフローDTO
	 * @param personalId 個人ID
	 * @param workflowComment 対象コメント
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void addComment(WorkflowDtoInterface dto, String personalId, String workflowComment) throws MospException;
	
	/**
	 * 削除を行う。<br>
	 * @param list 対象DTOリスト
	 * @throws MospException インスタンスの取得、或いはSQL実行に失敗した場合
	 */
	void deleteList(List<WorkflowCommentDtoInterface> list) throws MospException;
}
