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

import jp.mosp.framework.base.BaseDaoInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.SubHolidayDtoInterface;

/**
 * 代休データDAOインターフェース。<br>
 */
public interface SubHolidayDaoInterface extends BaseDaoInterface {
	
	/**
	 * 個人IDと出勤日と勤務回数と代休種別から代休情報を取得する。<br>
	 * 条件と合致する情報が存在しない場合は、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param workDate 出勤日
	 * @param timesWork 勤務回数
	 * @param subHolidayType 代休種別
	 * @return 代休データDTO
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	SubHolidayDtoInterface findForKey(String personalId, Date workDate, int timesWork, int subHolidayType)
			throws MospException;
	
	/**
	 * 個人IDと出勤日から代休データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate 出勤日
	 * @return 代休データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubHolidayDtoInterface> findForList(String personalId, Date workDate) throws MospException;
	
	/**
	 * 個人IDと開始年月日と終了年月日と代休日数から代休データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param startDate 開始年月日
	 * @param endDate 終了年月日
	 * @param subHolidayDays 代休日数
	 * @return 代休データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubHolidayDtoInterface> getSubHolidayList(String personalId, Date startDate, Date endDate,
			double subHolidayDays) throws MospException;
	
	/**
	 * 個人ID及び出勤日に発生した代休情報リストを取得する。<br>
	 * @param personalIds 個人ID群
	 * @param workDate    出勤日
	 * @return 代休情報リスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubHolidayDtoInterface> findForPersonalIds(Collection<String> personalIds, Date workDate) throws MospException;
	
	/**
	 * 個人IDで対象開始日から対象終了日の期間に発生した代休データリストを取得する。<br>
	 * @param personalId 個人ID
	 * @param startDate 開始年月日
	 * @param endDate 終了年月日
	 * @return 代休データリスト
	 * @throws MospException SQLの作成に失敗した場合、或いはSQL例外が発生した場合
	 */
	List<SubHolidayDtoInterface> findSubHolidayList(String personalId, Date startDate, Date endDate)
			throws MospException;
	
}
