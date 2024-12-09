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

import java.util.Date;
import java.util.List;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.platform.dto.workflow.SubApproverDtoInterface;

/**
 * 代理承認者テーブル参照インターフェース。
 */
public interface SubApproverReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 代理承認者登録Noから代理承認者情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param subApproverNo 代理承認者登録No
	 * @return 代理承認者テーブルDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	SubApproverDtoInterface findForKey(String subApproverNo) throws MospException;
	
	/**
	 * 代理承認者個人ID、フロー区分で、対象期間内における代理承認者情報を取得する。<br>
	 * 無効な情報は取得対象外とする。<br>
	 * @param subApproverId 代理承認者個人ID
	 * @param workflowType  フロー区分
	 * @param termStart     期間開始日
	 * @param termEnd       期間終了日
	 * @return 代理承認者リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubApproverDtoInterface> findForSubApproverId(String subApproverId, int workflowType, Date termStart,
			Date termEnd) throws MospException;
	
	/**
	 * 期間内に適用されている設定が存在するか確認する。<br>
	 * @param startDate 期間開始日
	 * @param endDate 期間終了日
	 * @param personalId 対象個人ID
	 * @param workflowType  フロー区分
	 * @return isExist (true：存在する、false：存在しない)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	boolean hasSubApprover(String personalId, Date startDate, Date endDate, int workflowType) throws MospException;
}
