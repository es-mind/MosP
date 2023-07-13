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
package jp.mosp.time.dao.settings;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Set;

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.SubstituteDtoInterface;

/**
 * 振替休日データDAOインターフェース。<br>
 */
public interface SubstituteDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと振替日と振替範囲と出勤日と勤務回数から振替休日情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * ワークフローの状態が取下げであるものは除く。<br>
	 * @param personalId 個人ID
	 * @param substituteDate 振替日
	 * @param substituteRange 振替範囲
	 * @param workDate 出勤日
	 * @param timesWork 勤務回数
	 * @return 振替休日データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	SubstituteDtoInterface findForKeyOnWorkflow(String personalId, Date substituteDate, int substituteRange,
			Date workDate, int timesWork) throws MospException;
	
	/**
	 * 個人IDと出勤日と勤務回数から振替休日データを取得する。<br>
	 * 取下状態も存在する。<br>
	 * @param personalId 個人ID
	 * @param workDate 出勤日
	 * @return 振替休日データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubstituteDtoInterface> findForWorkDate(String personalId, Date workDate) throws MospException;
	
	/**
	 * 個人IDと振替日から振替休日データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param substituteDate 振替日
	 * @return 振替休日データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubstituteDtoInterface> findForList(String personalId, Date substituteDate) throws MospException;
	
	/**
	 * ワークフロー番号から振替休日データを取得する。<br>
	 * @param workflow ワークフロー番号
	 * @return 振替休日データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubstituteDtoInterface> findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 個人IDと振替日から振替休日情報リストを取得する。<br>
	 * @param personalIds    個人ID群
	 * @param substituteDate 振替日
	 * @return 振替休日情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubstituteDtoInterface> findForPersonalIds(Collection<String> personalIds, Date substituteDate)
			throws MospException;
	
	/**
	 * 個人IDと対象期間から振替休日情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 振替休日情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubstituteDtoInterface> findForTerm(String personalId, Date firstDate, Date lastDate) throws MospException;
	
	/**
	 * 個人IDと出勤日から振替休日情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param workDates  出勤日群
	 * @return 振替休日情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubstituteDtoInterface> findForWorkDates(String personalId, Set<Date> workDates) throws MospException;
	
	/**
	 * 個人IDと振替日から振替休日データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param substituteDate 振替日
	 * @return 振替休日データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	SubstituteDtoInterface findForDate(String personalId, Date substituteDate) throws MospException;
}
