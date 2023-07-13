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
package jp.mosp.time.dao.settings;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;

/**
 * 代休申請DAOインターフェース。<br>
 */
public interface SubHolidayRequestDaoInterface extends BaseDaoInterface {
	
	/**
	 * ワークフロー番号から代休申請情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 代休申請DTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	SubHolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 個人IDと代休日から代休申請リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param requestDate 代休日
	 * @return 代休申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubHolidayRequestDtoInterface> findForList(String personalId, Date requestDate) throws MospException;
	
	/**
	 * 代休申請のリストの取得。
	 * <p>
	 * 個人IDと開始日と終了日から代休申請リストを取得。
	 * </p>
	 * @param personalId 個人ID
	 * @param startDate 開始日
	 * @param endDate 終了日
	 * @return 代休申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubHolidayRequestDtoInterface> findForList(String personalId, Date startDate, Date endDate)
			throws MospException;
	
	/**
	 * 個人IDと出勤日と勤務回数と代休種別から代休申請データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate 出勤日
	 * @param timesWork 勤務回数
	 * @param workDateSubHolidayType 代休種別
	 * @return 代休申請データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubHolidayRequestDtoInterface> findForList(String personalId, Date workDate, int timesWork,
			int workDateSubHolidayType) throws MospException;
	
	/**
	 * 個人IDと代休日から代休申請情報リストを取得する。<br>
	 * @param personalIds 個人ID群
	 * @param requestDate 代休日
	 * @return 代休申請情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubHolidayRequestDtoInterface> findForPersonalIds(Collection<String> personalIds, Date requestDate)
			throws MospException;
	
	/**
	 * 個人IDと対象期間から代休申請情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 代休申請情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubHolidayRequestDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 個人IDと代休日と休暇範囲から代休申請情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * ワークフローの状態が取下げであるものは除く。<br>
	 * @param personalId 個人ID
	 * @param requestDate 代休日
	 * @param holidayRange 休暇範囲
	 * @return 代休申請情報
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	SubHolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate, int holidayRange)
			throws MospException;
	
	/**
	 * 条件による検索。
	 * <p>
	 * 検索条件から代休申請リストを取得する。
	 * </p>
	 * @param param 検索条件
	 * @return 代休申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubHolidayRequestDtoInterface> findForSearch(Map<String, Object> param) throws MospException;
	
	/**
	 * 個人IDと休日出勤日から代休申請情報を取得する。
	 * @param personalId 個人ID
	 * @param workDate 休日出勤日
	 * @return 代休申請リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubHolidayRequestDtoInterface> findForWorkDate(String personalId, Date workDate) throws MospException;
	
	/**
	 * 検索条件取得。
	 * @return 代休申請検索条件マップ
	 */
	Map<String, Object> getParamsMap();
}
