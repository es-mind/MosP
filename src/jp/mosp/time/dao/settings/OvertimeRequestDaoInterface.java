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
package jp.mosp.time.dao.settings;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.OvertimeRequestDtoInterface;

/**
 * 残業申請DAOインターフェース。<br>
 */
public interface OvertimeRequestDaoInterface extends BaseDaoInterface {
	
	/**
	 * ワークフロー番号から残業申請情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 残業申請DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	OvertimeRequestDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 個人IDと申請日から残業申請リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @return 残業申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<OvertimeRequestDtoInterface> findForList(String personalId, Date requestDate) throws MospException;
	
	/**
	 * 個人IDと開始日と終了日から残業申請リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 残業申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<OvertimeRequestDtoInterface> findForList(String personalId, Date startDate, Date endDate) throws MospException;
	
	/**
	 * 個人IDと対象期間から残業申請情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 残業申請情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<OvertimeRequestDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 個人IDと申請日と残業区分から残業申請情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * ワークフローの状態が取下げであるものは除く。<br>
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @param overtimeType 残業区分
	 * @return 残業申請情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	OvertimeRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate, int overtimeType)
			throws MospException;
	
	/**
	 * 個人IDと勤務日で残業申請情報リストを取得する。<br>
	 * @param personalIds 個人ID群
	 * @param requestDate 勤務日
	 * @return 残業申請情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<OvertimeRequestDtoInterface> findForPersonalIds(Collection<String> personalIds, Date requestDate)
			throws MospException;
	
	/**
	 * 条件による検索。<br>
	 * 検索条件から残業申請リストを取得する。<br>
	 * @param param 検索条件
	 * @return 残業申請情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<OvertimeRequestDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 検索条件取得。<br>
	 * @return 残業申請検索条件マップ
	 */
	Map<String, Object> getParamsMap();
}
