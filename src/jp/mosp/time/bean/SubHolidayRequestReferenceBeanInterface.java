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
package jp.mosp.time.bean;

import java.util.Collection;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Set;

import jp.mosp.framework.base.BaseBeanInterface;
import jp.mosp.framework.base.MospException;
import jp.mosp.time.dto.settings.SubHolidayRequestDtoInterface;

/**
 * 代休申請参照処理インターフェース。<br>
 */
public interface SubHolidayRequestReferenceBeanInterface extends BaseBeanInterface {
	
	/**
	 * 代休申請一覧取得。
	 * <p>
	 * 社員コードと申請日から代休申請リストを取得。
	 * </p>
	 * @param personalId 個人ID
	 * @param requestDate 申請日
	 * @return 代休申請リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(String personalId, Date requestDate)
			throws MospException;
	
	/**
	 * 代休申請からレコードを取得する。<br>
	 * 社員コード、代休日、休暇範囲で合致するレコードが無い場合、nullを返す。<br>
	 * @param personalId 個人ID
	 * @param requestDate 代休日
	 * @param holidayRange 代休範囲
	 * @return 代休申請DTO
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SubHolidayRequestDtoInterface findForKeyOnWorkflow(String personalId, Date requestDate, int holidayRange)
			throws MospException;
	
	/**
	 * 代休申請データ取得。
	 * <p>
	 * レコード識別IDから代休申請データを取得。
	 * </p>
	 * @param id レコード識別ID
	 * @return 代休申請データ
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SubHolidayRequestDtoInterface findForKey(long id) throws MospException;
	
	/**
	 * 代休申請からレコードを取得する。<br>
	 * ワークフロー番号で合致するレコードが無い場合、nullを返す。<br>
	 * @param workflow ワークフロー番号
	 * @return 代休申請
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	SubHolidayRequestDtoInterface findForWorkflow(long workflow) throws MospException;
	
	/**
	 * 代休申請情報リストを取得する。<br>
	 * @param personalId 個人ID
	 * @param workDate 出勤日
	 * @param timesWork 勤務回数
	 * @param workDateSubHolidayType 代休種別
	 * @return 代休申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(String personalId, Date workDate, int timesWork,
			int workDateSubHolidayType) throws MospException;
	
	/**
	 * 代休申請情報リストを取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 代休申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SubHolidayRequestDtoInterface> getSubHolidayRequestList(String personalId, Date firstDate, Date lastDate)
			throws MospException;
	
	/**
	 * 代休申請情報群(キー：個人ID)を取得する。<br>
	 * 対象個人IDの代休日における申請を取得する。<br>
	 * @param personalIds 個人ID群
	 * @param requestDate 代休日
	 * @return 代休申請情報群(キー：個人ID)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<String, Set<SubHolidayRequestDtoInterface>> getSubHolidayRequests(Collection<String> personalIds,
			Date requestDate) throws MospException;
	
	/**
	 * 代休申請情報群(キー：申請日)を取得する。<br>
	 * 対象個人IDの対象期間における申請を取得する。<br>
	 * @param personalId 対象個人ID
	 * @param firstDate  対象期間初日
	 * @param lastDate   対象期間最終日
	 * @return 代休申請情報群(キー：申請日)
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	Map<Date, List<SubHolidayRequestDtoInterface>> getSubHolidayRequests(String personalId, Date firstDate,
			Date lastDate) throws MospException;
	
	/**
	 * 基本情報チェック
	 * @param personalId 個人ID
	 * @param targetDate 対象日
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	void chkBasicInfo(String personalId, Date targetDate) throws MospException;
	
	/**
	 * @param personalId 個人ID
	 * @param workDate 休日出勤日
	 * @return 代休申請情報リスト
	 * @throws MospException インスタンスの取得或いはSQL実行に失敗した場合
	 */
	List<SubHolidayRequestDtoInterface> findForWorkDate(String personalId, Date workDate) throws MospException;
}
