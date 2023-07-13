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
package jp.mosp.platform.dao.workflow;

import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;

/**
 * ワークフローコメントDAOインターフェース。<br>
 */
public interface WorkflowCommentDaoInterface extends BaseDaoInterface {
	
	/**
	 * ワークフロー番号からワークフローコメント情報群を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、空のマップを返す。<br>
	 * @param workflowSet ワークフロー番号のセット
	 * @return ワークフローコメント情報群
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	Map<Long, WorkflowCommentDtoInterface> findForInKey(Set<Long> workflowSet) throws MospException;
	
	/**
	 * ワークフロー番号から最新のワークフローコメント情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return ワークフローコメントDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	WorkflowCommentDtoInterface findForLatestCommentInfo(long workflow) throws MospException;
	
	/**
	 * ワークフロー番号からワークフローコメント情報一覧を取得する。<br>
	 * ワークフロー日時の降順で並べ替えられた一覧を取得する。<br>
	 * 但し、ワークフロー状況が下書の情報は一覧から省く。<br>
	 * @param workflow ワークフロー番号
	 * @return ワークフローコメントDTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowCommentDtoInterface> findForHistory(long workflow) throws MospException;
	
	/**
	 * ワークフロー番号からワークフローコメント情報一覧を取得する。<br>
	 * ワークフロー日時の降順で並べ替えられた一覧を取得する。<br>
	 * @param workflow ワークフロー番号
	 * @return ワークフローコメントDTOリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<WorkflowCommentDtoInterface> findForList(long workflow) throws MospException;
	
}
