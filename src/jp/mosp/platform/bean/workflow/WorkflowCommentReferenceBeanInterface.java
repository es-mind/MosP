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
package jp.mosp.platform.bean.workflow;

import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.WorkflowCommentDtoInterface;

/**
 * ワークフローコメント参照インターフェース。
 */
public interface WorkflowCommentReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 最新のワークフローコメント取得。
	 * <p>
	 * ワークフロー番号から最新のワークフローコメントを取得。
	 * </p>
	 * @param workflow ワークフロー番号
	 * @return ワークフローコメント
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	WorkflowCommentDtoInterface getLatestWorkflowCommentInfo(long workflow) throws MospException;
	
	/**
	 * 履歴一覧取得。
	 * <p>
	 * ワークフロー番号からワークフローコメントリストを取得。<br>
	 * 但し、ワークフロー状況が下書の情報は一覧から省く。
	 * </p>
	 * @param workflow ワークフロー番号
	 * @return ワークフローコメントリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkflowCommentDtoInterface> getWorkflowCommentHistory(long workflow) throws MospException;
	
	/**
	 * ワークフロー番号からワークフローコメントリストを取得。
	 * @param workflow ワークフロー番号
	 * @return ワークフローコメントリスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<WorkflowCommentDtoInterface> getWorkflowCommentList(long workflow) throws MospException;
	
}
